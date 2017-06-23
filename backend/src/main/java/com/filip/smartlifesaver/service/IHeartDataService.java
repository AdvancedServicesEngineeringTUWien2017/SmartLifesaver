package com.filip.smartlifesaver.service;


import com.filip.smartlifesaver.model.HeartData;

import java.util.List;

public interface IHeartDataService {

    public void processPendingHeartData();

    public HeartData create(HeartData heartData);

    public List<HeartData> findAll();

    public boolean remove(String id);

}
