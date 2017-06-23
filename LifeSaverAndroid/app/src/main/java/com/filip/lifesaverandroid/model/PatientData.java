package com.filip.lifesaverandroid.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientData {

    /**
     * These static data will be passed to backend to register user when the app is started for the first time.
     */
    public static final String ID = "123";
    public static final String USERNAME = "Filip";
    public static final String EMAIL = "filip@some.email.com";
    public static final String THING_ID = "12345";

    private String id;

    private String username;

    private String email;

    private String deviceID;

    private String thingId;

    /**
     * resting blood pressure (in mm Hg)
     */
    private int lastMeasuredTestBPS;

    /**
     * serum cholestoral in mg/dl
     */
    private int lastMeasuredCholesterol;

    /**
     * lastMeasuredThalach: maximum heart rate achieved
     */
    private int lastMeasuredThalach;


    /**
     * (fasting blood sugar > 120 mg/dl) (1 = true; 0 = false)
     */
    private boolean lastMeasuredMuchFBS;


    public PatientData() {
    }


    public int getLastMeasuredTestBPS() {
        return lastMeasuredTestBPS;
    }

    public void setLastMeasuredTestBPS(int lastMeasuredTestBPS) {
        this.lastMeasuredTestBPS = lastMeasuredTestBPS;
    }

    public int getLastMeasuredCholesterol() {
        return lastMeasuredCholesterol;
    }

    public void setLastMeasuredCholesterol(int lastMeasuredCholesterol) {
        this.lastMeasuredCholesterol = lastMeasuredCholesterol;
    }

    public int getLastMeasuredThalach() {
        return lastMeasuredThalach;
    }

    public void setLastMeasuredThalach(int lastMeasuredThalach) {
        this.lastMeasuredThalach = lastMeasuredThalach;
    }

    public boolean isLastMeasuredMuchFBS() {
        return lastMeasuredMuchFBS;
    }

    public void setLastMeasuredMuchFBS(boolean lastMeasuredMuchFBS) {
        this.lastMeasuredMuchFBS = lastMeasuredMuchFBS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getThingId() {
        return thingId;
    }

    public void setThingId(String thingId) {
        this.thingId = thingId;
    }
}
