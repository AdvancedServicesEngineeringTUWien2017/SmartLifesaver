package com.filip.smartlifesaver.service;


import com.filip.smartlifesaver.model.HeartData;
import com.filip.smartlifesaver.model.Patient;

import java.util.List;

public interface IPatientService {

    public Patient create(Patient patient);

    public List<Patient> findAll();

    public Patient findOneById(String id);

    public Patient findOneByThingId(String thingId);

    public Patient update(Patient patient);

    public boolean remove(String id);

    /**
     * Increments the warnings pending for a patient. If the warning's count is above a threshold, then patient will be notified.
     * @param heartData
     */
    public void incrementWarnCount(HeartData heartData);

    public void resetWarnCount(HeartData heartData);

}
