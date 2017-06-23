package com.filip.smartlifesaver.endpoint;


import com.filip.smartlifesaver.service.IHeartDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/classifier")
public class ClassifierEndpoint {


    @Autowired
    private IHeartDataService heartDataService;

    @RequestMapping(method = RequestMethod.GET)
    public void triggerProcessingPendingHeartData() {

        heartDataService.processPendingHeartData();

    }


}
