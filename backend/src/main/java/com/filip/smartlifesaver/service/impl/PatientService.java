package com.filip.smartlifesaver.service.impl;


import com.filip.smartlifesaver.model.HeartData;
import com.filip.smartlifesaver.model.Patient;
import com.filip.smartlifesaver.notification.INotificationService;
import com.filip.smartlifesaver.repository.PatientRepository;
import com.filip.smartlifesaver.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PatientService implements IPatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    private static final int WARNINGS_COUNT_THRESHOLD_FOR_PATIENT = 3;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private INotificationService notificationService;


    @Override
    public Patient create(Patient patient) {

        logger.info("Creating or updating patient " + patient.getUsername());
        logger.info("Device token: " + patient.getDeviceID());

        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> findAll() {

        List<Patient> patientList = new ArrayList<>();

        Iterator<Patient> heartDataIterator = patientRepository.findAll().iterator();
        while (heartDataIterator.hasNext()) {
            patientList.add(heartDataIterator.next());
        }
        return patientList;

    }

    @Override
    public Patient findOneById(String id) {
        return patientRepository.findOne(id);
    }

    @Override
    public Patient findOneByThingId(String thingId) {
        return patientRepository.findOneByThingId(thingId);
    }

    @Override
    public Patient update(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public boolean remove(String id) {
        patientRepository.delete(id);
        return true;
    }

    @Override
    public void incrementWarnCount(HeartData heartData) {

        Patient patient = findOneByThingId(heartData.getThingId());
        patient.setWarningsCount(patient.getWarningsCount() + 1);

        if (patient.getWarningsCount() >= WARNINGS_COUNT_THRESHOLD_FOR_PATIENT) {

            notificationService.pushNotificationToPatient(patient, "The analysis of your heart data has recently shown a possibility of a heart disease, consider visiting your cardiologist");
            patient.setWarningsCount(0);

        }

        update(patient);


    }

    @Override
    public void resetWarnCount(HeartData heartData) {

        Patient patient = findOneByThingId(heartData.getThingId());
        patient.setWarningsCount(0);
        update(patient);

    }
}
