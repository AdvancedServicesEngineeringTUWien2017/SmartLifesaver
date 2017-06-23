package com.filip.lifesaverandroid.service;


import android.os.AsyncTask;
import android.util.Log;

import com.filip.lifesaverandroid.model.PatientData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }



    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

        PatientData patientData = new PatientData();
        patientData.setId(PatientData.ID);
        patientData.setThingId(PatientData.THING_ID);
        patientData.setUsername(PatientData.USERNAME);
        patientData.setEmail(PatientData.EMAIL);
        patientData.setDeviceID(token);

        PatientRegistrationTask patientRegistrationTask = new PatientRegistrationTask(patientData);
        patientRegistrationTask.execute((Void) null);

    }


    /**
     * This asynctask registers the user, or updates his/her info when token is refreshed.
     */
    class PatientRegistrationTask extends AsyncTask<Void, Void, PatientData> {

        private PatientData patientData;

        public PatientRegistrationTask(PatientData patientData) {
            this.patientData = patientData;
        }

        @Override
        protected PatientData doInBackground(Void... params) {

            Log.d(TAG, "Persising user information on backend....");

            RestTemplate restTemplate = RestTemplateConfig.buildRestTemplate();

            HttpEntity<PatientData> request = new HttpEntity<>(patientData);
            try {
                patientData = restTemplate.postForObject(BackendURLs.PATIENT, request, PatientData.class);
                return patientData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(PatientData patientData) {
            super.onPostExecute(patientData);

            if (patientData != null) {
                Log.d(TAG, "User data persisted on backend....");
            } else {
                Log.d(TAG, "Some error occured, user data not persisted....");
            }

        }
    }


}
