package com.filip.smartlifesaver.notification;


import com.filip.smartlifesaver.model.Patient;

public interface INotificationService {

    public void pushNotificationToPatient(Patient patient, String message);

}
