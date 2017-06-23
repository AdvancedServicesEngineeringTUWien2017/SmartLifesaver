# Android app

This app is a communication component with a user. The user can see the most recent values measured by sensors in this app. If the analytics services at backend predict a possibility of a heart disease, then the patient(user) is notified per this app as well.

## Setup and install

Prerequisites:

1. Running backend
2. Followed the steps in AWS_IoT/README.md
3. Setup Firebase for notifications as described here: https://firebase.google.com/docs/android/setup#manually_add_firebase

If you install this app for a first time at a device, it's automatically registered at backend passing the user information, thing_id and registration token(this is later used to push notifications to the device). All these data (except the registration token) are hardcoded in /model/PatientData.java. You can change it how you want, but be aware of the thing_id, which identifies the sensor associated with the user. You can find more information about thing_id in AWS_IoT/README.md

Change the /service/BackendURLs/URL, such that the app can connect to your backend.

Now everything should be ready to run the app.
