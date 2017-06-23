# Thing setup

## Lambda Setup

Create a lambda function called addHeartDataToDynamoDB (see addHeartDataToDynamoDB.js) which is triggered by AWS IoT, if a message arrives at topic 'HeartDataSensorReceiver'. This can be simply done by creating a custom rule with the following SQL statement. 
```
'SELECT * FROM 'HeartDataSensorReceiver''
```

## IoT Setup
For the purpose of this lab and for simplicity, the "thing" will be simulated by a Ubuntu OS running in a virtual machine. But all steps mentioned here are almost the same as we would use any thing e.g. Raspberry Pi Model B. 

1. Create a VM and install Ubuntu on it.
2. Follow this tutorial to register thing (http://docs.aws.amazon.com/iot/latest/developerguide/iot-sdk-setup.html).
3. Setup your Runtime Environment for the AWS IoT Embedded C SDK in the VM as described at: http://docs.aws.amazon.com/iot/latest/developerguide/iot-embedded-c-sdk.html
4. Follow step 1 in Sample App Configuration at http://docs.aws.amazon.com/iot/latest/developerguide/iot-embedded-c-sdk.html
5. Put the content of AWS_Iot/thing into deviceSDK/sample_apps
6. Change the corresponding constants in deviceSDK/sample_apps/publish_heart_data/aws_iot_config.h as described in Sample App Configuration step 3 at http://docs.aws.amazon.com/iot/latest/developerguide/iot-embedded-c-sdk.html
7. You can change the values, mocking the received data from sensors in publish_heart_data.c  

**Thing ID**
Note: Each thing is defined by a unique ID. This ID is assigned to the thing during the manufacture process. You can change this value in thing/publish_heart_data/publish_heart_data.c THING_ID constant. 

## DynamoDB setup
1. Create a table called 'Patient' having a key called 'id'.
2. Create a table called 'HeartData' having a key called 'id'.


Now you should have configured the 'thing' component of this app correctly.