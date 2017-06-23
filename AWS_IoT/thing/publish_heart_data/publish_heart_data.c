

/**
 * @file publish_heart_data.c
 * @brief simple MQTT publish on the topic
 *
 * This example takes the parameters from the aws_iot_config.h file and establishes a connection to the AWS IoT MQTT Platform.
 * It publishes to the topic - "HeartDataSensorReceiver"
 *
 * The application takes in the certificate path, host name , port and the publish should happen.
 *
 */
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>

#include <signal.h>
#include <memory.h>
#include <sys/time.h>
#include <limits.h>

#include "aws_iot_log.h"
#include "aws_iot_version.h"
#include "aws_iot_mqtt_interface.h"
#include "aws_iot_config.h"

#define TOPIC_NAME "HeartDataSensorReceiver"
#define THING_ID 12345

void disconnectCallbackHandler(void) {
	WARN("MQTT Disconnect");
	IoT_Error_t rc = NONE_ERROR;
	if(aws_iot_is_autoreconnect_enabled()){
		INFO("Auto Reconnect is enabled, Reconnecting attempt will start now");
	}else{
		WARN("Auto Reconnect not enabled. Starting manual reconnect...");
		rc = aws_iot_mqtt_attempt_reconnect();
		if(RECONNECT_SUCCESSFUL == rc){
			WARN("Manual Reconnect Successful");
		}else{
			WARN("Manual Reconnect Failed - %d", rc);
		}
	}
}

/**
 * @brief Default cert location
 */
char certDirectory[PATH_MAX + 1] = "../../certs";

/**
 * @brief Default MQTT HOST URL is pulled from the aws_iot_config.h
 */
char HostAddress[255] = AWS_IOT_MQTT_HOST;

/**
 * @brief Default MQTT port is pulled from the aws_iot_config.h
 */
uint32_t port = AWS_IOT_MQTT_PORT;

int main(int argc, char** argv) {

	srand(time(NULL));

	IoT_Error_t rc = NONE_ERROR;

	char rootCA[PATH_MAX + 1];
	char clientCRT[PATH_MAX + 1];
	char clientKey[PATH_MAX + 1];
	char CurrentWD[PATH_MAX + 1];
	char cafileName[] = AWS_IOT_ROOT_CA_FILENAME;
	char clientCRTName[] = AWS_IOT_CERTIFICATE_FILENAME;
	char clientKeyName[] = AWS_IOT_PRIVATE_KEY_FILENAME;

	//parseInputArgsForConnectParams(argc, argv);

	INFO("\nAWS IoT SDK Version %d.%d.%d-%s\n", VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH, VERSION_TAG);

	getcwd(CurrentWD, sizeof(CurrentWD));
	sprintf(rootCA, "%s/%s/%s", CurrentWD, certDirectory, cafileName);
	sprintf(clientCRT, "%s/%s/%s", CurrentWD, certDirectory, clientCRTName);
	sprintf(clientKey, "%s/%s/%s", CurrentWD, certDirectory, clientKeyName);

	DEBUG("rootCA %s", rootCA);
	DEBUG("clientCRT %s", clientCRT);
	DEBUG("clientKey %s", clientKey);

	MQTTConnectParams connectParams = MQTTConnectParamsDefault;

	connectParams.KeepAliveInterval_sec = 10;
	connectParams.isCleansession = true;
	connectParams.MQTTVersion = MQTT_3_1_1;
	connectParams.pClientID = "CSDK-test-device";
	connectParams.pHostURL = HostAddress;
	connectParams.port = port;
	connectParams.isWillMsgPresent = false;
	connectParams.pRootCALocation = rootCA;
	connectParams.pDeviceCertLocation = clientCRT;
	connectParams.pDevicePrivateKeyLocation = clientKey;
	connectParams.mqttCommandTimeout_ms = 2000;
	connectParams.tlsHandshakeTimeout_ms = 5000;
	connectParams.isSSLHostnameVerify = true; // ensure this is set to true for production
	connectParams.disconnectHandler = disconnectCallbackHandler;

	INFO("Connecting...");
	rc = aws_iot_mqtt_connect(&connectParams);
	if (NONE_ERROR != rc) {
		ERROR("Error(%d) connecting to %s:%d", rc, connectParams.pHostURL, connectParams.port);
	}
	/*
	 * Enable Auto Reconnect functionality. Minimum and Maximum time of Exponential backoff are set in aws_iot_config.h
	 *  #AWS_IOT_MQTT_MIN_RECONNECT_WAIT_INTERVAL
	 *  #AWS_IOT_MQTT_MAX_RECONNECT_WAIT_INTERVAL
	 */
	rc = aws_iot_mqtt_autoreconnect_set_status(true);
	if (NONE_ERROR != rc) {
		ERROR("Unable to set Auto Reconnect to true - %d", rc);
		return rc;
	}

	MQTTMessageParams Msg = MQTTMessageParamsDefault;
	Msg.qos = QOS_0;
	char cPayload[700];
	Msg.pPayload = (void *) cPayload;

	MQTTPublishParams Params = MQTTPublishParamsDefault;
	Params.pTopic = TOPIC_NAME;

	if (NETWORK_ATTEMPTING_RECONNECT == rc || RECONNECT_SUCCESSFUL == rc || NONE_ERROR == rc) {

		rc = aws_iot_mqtt_yield(700);
		while(NETWORK_ATTEMPTING_RECONNECT == rc){
			rc = aws_iot_mqtt_yield(700);
			INFO("-->reconnecting");
			sleep(1);
		}

		int id_int = rand() % 10000;	

        /**
        * MOCKING THE SENSOR DATA
        */
		char id[15];//if of this measurement
		sprintf(id, "%d", id_int);

		char thingId[] = THING_ID;//the thing_id representing this device
		int age = 25;
		bool isMale = true;

		//For the meaning as well as the allowed values of these values, refer to /backend/model/HeartData.java
		char chestPainType[] = "TYPICAL_ANGINA";
		int testBPS = 12;
		int cholesterol = 34;
		bool muchFBS = true;
		char restECG[] = "STT_WAVE_ABNORMALITY";
		int thalach = 120;
		bool exInducedAng = true;
		float oldPeak = 12.0;
		char slopePeakExercise[] = "FLAT";
		int majorVesselsCount = 2;
		char thal[] = "NORMAL";
		int diagnosisOfHeartDisease = 2;
            
            
		

		
		INFO("-->publishing data to topic %s" , TOPIC_NAME);
		sleep(1);

		sprintf(cPayload, "{\"id\": \"%s\", \"thingId\": \"%s\", \"age\": %d, \"isMale\": %s, \"chestPainType\": \"%s\", \"testBPS\": %d, \"cholesterol\": %d, \"muchFBS\": %s, \"restECG\": \"%s\", \"thalach\": %d, \"exInducedAng\": %s, \"oldPeak\": %.2f, \"slopePeakExercise\": \"%s\", \"majorVesselsCount\": %d, \"thal\": \"%s\", \"diagnosisOfHeartDisease\": %d }", id, thingId, age, isMale ? "true" : "false", chestPainType, testBPS, cholesterol, muchFBS ? "true" : "false", restECG, thalach, exInducedAng ? "true" : "false", oldPeak, slopePeakExercise, majorVesselsCount, thal, diagnosisOfHeartDisease );

		Msg.PayloadLen = strlen(cPayload) + 1;
		Params.MessageParams = Msg;
		rc = aws_iot_mqtt_publish(&Params);


	}

	if (NONE_ERROR != rc) {
		ERROR("An error occurred during publishing.\n");
	} else {
		INFO("Publish done\n");
	}

	return rc;
}

