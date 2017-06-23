package com.filip.smartlifesaver.ml.impl;


import com.filip.smartlifesaver.model.HeartData;
import lombok.Getter;

import java.util.List;

abstract class AbsClassifier {


    /**
     * The evaluation wrapper of last run of trainModel() method.
     */
    @Getter
    protected EvaluationWrapper evaluationWrapper;


    protected abstract void trainModel();


    protected abstract HeartData predictHearthDisease(HeartData heartData);

    protected abstract List<HeartData> predictHeartDiseases(List<HeartData> heartDatas);

    protected abstract boolean saveTrainedModel(String path);


    protected abstract void loadTrainedModel(String path);

}
