package com.filip.smartlifesaver.repository;

import com.filip.smartlifesaver.model.Patient;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface PatientRepository extends CrudRepository<Patient, String> {

    public Patient findOneByThingId(String thingId);


}
