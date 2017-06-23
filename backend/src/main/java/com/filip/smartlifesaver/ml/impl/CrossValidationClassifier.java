package com.filip.smartlifesaver.ml.impl;


import com.filip.smartlifesaver.model.HeartData;
import com.filip.smartlifesaver.model.HeartDataLibSVMData;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.tuning.CrossValidator;
import org.apache.spark.ml.tuning.CrossValidatorModel;
import org.apache.spark.ml.tuning.ParamGridBuilder;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ML Tuning: https://spark.apache.org/docs/2.1.0/ml-tuning.html
 */

@Service
public class CrossValidationClassifier extends AbsClassifier {


    private static final Logger logger = LoggerFactory.getLogger(CrossValidationClassifier.class);


    @Autowired
    private SparkSession sparkSession;

    @Value("${input.data.training}")
    private String trainingDataFile;

    private StructType schema;

    private CrossValidatorModel cvModel;

    @PostConstruct
    private void init() {

        schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });

    }

    @Override
    protected void trainModel() {


        logger.info("Tuning the LogisticRegressionModel started...");

        // Load training data
        Dataset<Row> trainingData = sparkSession
                .read()
                .format("libsvm")
                .load(trainingDataFile);

        LogisticRegression lr = new LogisticRegression().setMaxIter(10).setRegParam(0.01);


        // Configure an ML pipeline, which consists of one stage: lr.
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{lr});

        //configuration of cross validation
        ParamMap[] paramGrid = new ParamGridBuilder()
                .addGrid(lr.regParam(), new double[]{0.7, 0.3, 0.1, 0.01})
                .addGrid(lr.elasticNetParam(), new double[]{0.8, 0.4, 0.1})
                .build();

        //this evaluator will be used to pick the best model
        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator().setMetricName("f1");


        CrossValidator cv = new CrossValidator()
                .setEstimator(pipeline)
                .setEvaluator(evaluator)
                .setEstimatorParamMaps(paramGrid).setNumFolds(3);


        cvModel = cv.fit(trainingData);

        logger.info("Tuning the LogisticRegressionModel finished");
    }

    @Override
    protected HeartData predictHearthDisease(HeartData heartData) {

        checkCVModel();


        List<HeartData> heartDatas = predictHeartDiseases(Arrays.asList(heartData));

        if (heartDatas != null && heartDatas.size() != 0) {
            return null;
        }

        return heartDatas.get(0);
    }

    @Override
    protected List<HeartData> predictHeartDiseases(List<HeartData> heartDatas) {

        if (heartDatas == null || heartDatas.size() == 0) {
            return heartDatas;
        }

        checkCVModel();

        logger.info("Prediciting the labels of new instances, count = " + heartDatas.size());

        //storing the instances to predict here
        List<Row> dataToPredict = new ArrayList<>();

        for (HeartData heartData: heartDatas) {

            HeartDataLibSVMData svmData = new HeartDataLibSVMData(heartData);

            //creating row containing label svmData.getNum() and vector of features svmData.createVector()
            dataToPredict.add(RowFactory.create(svmData.getNum(), svmData.createVector()));
        }


        //created data frame with instances, we want to predict labels on.
        Dataset<Row> instancesToPredictDataFrame = sparkSession.createDataFrame(dataToPredict, schema);

        //predictions
        Dataset<Row> predictions = cvModel.transform(instancesToPredictDataFrame);

        predictions.select("features", "label", "prediction")
                .show(false);


        //selecting the prediction column
        List<Row> predictedLabels = predictions.select("prediction").collectAsList();

        int i = 0;
        for (Row predictedLabelRow: predictedLabels) {
            Double predictionValue = (Double) predictedLabelRow.get(0);
            double tmp = predictionValue;
            int predictionValueInt = (int) tmp;//since this is a classification problem with 3 classes, we can do the casting here

            logger.info("For heart data: " + heartDatas.get(i).toString());
            logger.info("We predicted value = " + predictionValueInt);

            heartDatas.get(i).setDiagnosisOfHeartDisease(predictionValueInt);
            i++;
        }


        return heartDatas;
    }

    @Override
    protected boolean saveTrainedModel(String path) {

        checkCVModel();

        logger.info("Persisting best trained model");

        //deleting the old model
        File modelFile = new File(path);
        if (modelFile.exists()) {
            logger.info("There is a old model saved, removing it!");
            modelFile.delete();
        }

        try {
            cvModel.write().overwrite().save(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


        return true;
    }

    @Override
    protected void loadTrainedModel(String path) {

        logger.info("Loading best trained model");
        cvModel = CrossValidatorModel.load(path);

    }

    private void checkCVModel() {
        if (cvModel == null) {
            throw new IllegalStateException("Train or load a model before saving it!");
        }
    }

}
