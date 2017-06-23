package com.filip.smartlifesaver.model.enums;


public enum SlopePeakExercise {

    UPSLOPPING(1), FLAT(2), DOWNSLOPPING(3);


    private int value;

    SlopePeakExercise(int value) {
        this.value = value;
    }


    public static SlopePeakExercise fromInt(int i) {
        for (SlopePeakExercise cp: SlopePeakExercise.values()) {
            if (cp.value == i) {
                return cp;
            }
        }
        return null;
    }

}
