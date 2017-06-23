package com.filip.smartlifesaver.notification.impl;

import com.filip.smartlifesaver.model.FirebaseDataMessageDTO;
import com.filip.smartlifesaver.model.Patient;
import com.filip.smartlifesaver.notification.INotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.amazonaws.util.json.JSONObject;



@Service
public class FirebaseNotificationService implements INotificationService {


    @Value("${firebase.api_key}")
    private String apiKey;


    @Override
    public void pushNotificationToPatient(Patient patient, String message) {


        if (patient.getDeviceID() == null) {
            return;
        }

        FirebaseDataMessageDTO firebaseDataMessageDTO = new FirebaseDataMessageDTO();
        firebaseDataMessageDTO.setTo(patient.getDeviceID());

        FirebaseDataMessageDTO.FirebaseDataContainer firebaseDataContainer = new FirebaseDataMessageDTO.FirebaseDataContainer();
        firebaseDataContainer.setNotificationMsg(message);

        firebaseDataMessageDTO.setData(firebaseDataContainer);


        String url = "https://fcm.googleapis.com/fcm/send";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "application/json");
        httpHeaders.set("Authorization", "key=" + apiKey);


        HttpEntity<FirebaseDataMessageDTO> httpEntity = new HttpEntity<>(firebaseDataMessageDTO, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        restTemplate.exchange(url, HttpMethod.POST, httpEntity, JSONObject.class);


    }
}
