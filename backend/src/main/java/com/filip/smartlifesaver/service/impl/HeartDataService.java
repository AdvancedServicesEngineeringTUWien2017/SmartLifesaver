package com.filip.smartlifesaver.service.impl;

import com.filip.smartlifesaver.ml.IClassifier;
import com.filip.smartlifesaver.model.HeartData;
import com.filip.smartlifesaver.model.Patient;
import com.filip.smartlifesaver.repository.HeartDataRepository;
import com.filip.smartlifesaver.service.IHeartDataService;
import com.filip.smartlifesaver.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HeartDataService implements IHeartDataService {

    private static final Logger logger = LoggerFactory.getLogger(HeartDataService.class);

    @Autowired
    private HeartDataRepository heartDataRepository;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IClassifier classifier;

    @Scheduled(initialDelay = 60 * 60 * 1000, fixedRate = 2 * 60 * 60 * 1000)
    @Override
    public void processPendingHeartData() {

        logger.info("Processing pending heart data triggered...");

        List<HeartData> heartDatas = findAll();

        logger.info("Pushing pending heart data to classifier...");

        for (HeartData heartData: heartDatas) {

            Patient patient = patientService.findOneByThingId(heartData.getThingId());
            logger.info("Updating heart data of patient "+patient.getId());

            patient.setLastMeasuredMuchFBS(heartData.isMuchFBS());
            patient.setLastMeasuredThalach(heartData.getThalach());
            patient.setLastMeasuredTestBPS(heartData.getTestBPS());
            patient.setLastMeasuredCholesterol(heartData.getCholesterol());

            patientService.update(patient);

            remove(heartData.getId());
        }

        classifier.predictHeartDiseases(heartDatas);

    }

    @Override
    public HeartData create(HeartData heartData) {
        return heartDataRepository.save(heartData);
    }

    @Override
    public List<HeartData> findAll() {

        List<HeartData> heartDatas = new ArrayList<>();

        Iterator<HeartData> heartDataIterator = heartDataRepository.findAll().iterator();
        while (heartDataIterator.hasNext()) {
            heartDatas.add(heartDataIterator.next());
        }
        return heartDatas;
    }

    @Override
    public boolean remove(String id) {
        heartDataRepository.delete(id);
        return true;
    }
}
