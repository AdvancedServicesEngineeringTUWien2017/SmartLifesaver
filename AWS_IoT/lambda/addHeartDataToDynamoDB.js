
var AWS = require('aws-sdk');
var dynamo = new AWS.DynamoDB.DocumentClient();
var table = 'HeartData';


exports.handler = function(event, context) {

    var params = {
        TableName: table,
        Item: {
            "id": event.id,
            "thingId": event.thingId,
            "age": event.age,
            "isMale": event.isMale,
            "chestPainType": event.chestPainType,
            "testBPS": event.testBPS,
            "cholesterol": event.cholesterol,
            "muchFBS": event.muchFBS,
            "restECG": event.restECG,
            "thalach": event.thalach,
            "exInducedAng": event.exInducedAng,
            "oldPeak": event.oldPeak,
            "slopePeakExercise": event.slopePeakExercise,
            "majorVesselsCount": event.majorVesselsCount,
            "thal": event.thal,
            "diagnosisOfHeartDisease": event.diagnosisOfHeartDisease
        }
    }

    console.log('Adding received HeartData from Sensor to DynamoDB');

    dynamo.put(params, function(err, data) {

        if (err) {

            console.error('Unable to add HeartData to DynamoDB');
            context.fail();

        } else {

            console.log('HeartData adeed to DynamoDB');
            context.succeed();

        }

    });


}