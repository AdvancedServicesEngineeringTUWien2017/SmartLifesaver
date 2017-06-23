# SmartLifeSaver

This project was developed as a lab part of Advanced Services Engineering course with the aim to demostrate the usage of IoT on a chosen scenario. Author: Filip Rydzi, 1226452


## Scenario
The aim of this scenario is to warn the users, if they have a risk of a heart disease.
In my scenario the users are expected to wear sensor on their bodies, these are sending the collected data to backend, where the data are analyzed. In case the analysis is continuously showing some risk of a heart disease, then the user is notified per an app to visit a cardiologist. 
 

## Technologies
- Apache Spark is used to perform the analysis
- AWS IoT as a connection endpoint for the sensors
- AWS Lambda: to pass the data sent to AWS IoT from sensors to AWS DynamoDB
- AWS DynamoDB: to store data about users
- Spring Boot: platform for backend
- Android: a client app for the users
- Firebase: to push notification to the user's app


## Architecture
The architecture of this system is to find under /doc.

## Components
The main components of this system are:
- AWS IoT (/AWS_IoT)
- backend (/backend)
- Android app (/LifeSaverAndroid)

You can find the instructions how to run all of these components in the READMEs in their directories.

