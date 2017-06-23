package com.filip.smartlifesaver.endpoint;


import com.filip.smartlifesaver.model.Patient;
import com.filip.smartlifesaver.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/patient")
public class PatientEndpoint {


    @Autowired
    private IPatientService patientService;

    @RequestMapping(method = RequestMethod.POST)
    public Patient create(@RequestBody Patient patient) {

        return patientService.create(patient);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Patient findById(@PathVariable String id) {
        return patientService.findOneById(id);
    }

}
