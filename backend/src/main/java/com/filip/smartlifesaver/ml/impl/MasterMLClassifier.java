package com.filip.smartlifesaver.ml.impl;


import com.filip.smartlifesaver.model.HeartData;
import com.filip.smartlifesaver.ml.IClassifier;
import com.filip.smartlifesaver.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MasterMLClassifier implements IClassifier {


    private static final Logger logger = LoggerFactory.getLogger(MasterMLClassifier.class);


    @Autowired
    private CrossValidationClassifier classifier;

    @Autowired
    private IPatientService patientService;


    @Value("${best_model.filename}")
    private String savedBasedModelPath;


    @Override
    public void trainAndSaveBestModel() {

        logger.info("Training the best model started");

        classifier.trainModel();

        logger.info("Training finished, persisting the best model now");

        classifier.saveTrainedModel(savedBasedModelPath);
    }


    @Override
    public void loadBestTrainedModel() {
        classifier.loadTrainedModel(savedBasedModelPath);
    }

    @Override
    public HeartData predictHearthDisease(HeartData heartData) {
        return classifier.predictHearthDisease(heartData);
    }

    @Override
    public List<HeartData> predictHeartDiseases(List<HeartData> heartDatas) {

        heartDatas = classifier.predictHeartDiseases(heartDatas);

        for (HeartData heartData: heartDatas) {
            if (heartData.getDiagnosisOfHeartDisease() > 2) {
                patientService.incrementWarnCount(heartData);
            } else {
                patientService.resetWarnCount(heartData);
            }
        }


        return heartDatas;
    }
}
