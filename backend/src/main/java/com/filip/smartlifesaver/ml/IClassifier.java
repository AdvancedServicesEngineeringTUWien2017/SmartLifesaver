package com.filip.smartlifesaver.ml;


import com.filip.smartlifesaver.model.HeartData;

import java.util.List;

public interface IClassifier {

    void trainAndSaveBestModel();

    void loadBestTrainedModel();

    HeartData predictHearthDisease(HeartData heartData);

    List<HeartData> predictHeartDiseases(List<HeartData> heartDatas);


}
