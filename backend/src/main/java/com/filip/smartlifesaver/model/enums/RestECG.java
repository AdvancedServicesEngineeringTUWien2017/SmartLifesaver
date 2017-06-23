package com.filip.smartlifesaver.model.enums;


/**
 * restecg: resting electrocardiographic results
 -- Value 0: normal
 -- Value 1: having ST-T wave abnormality (T wave inversions and/or ST elevation or depression of > 0.05 mV)
 -- Value 2: showing probable or definite left ventricular hypertrophy by Estes' criteria
 */
public enum RestECG {

    NORMAL(0), STT_WAVE_ABNORMALITY(1), LEFT_VENTICULAR_HYPERTROPHY(2) ;

    private int value;

    RestECG(int value) {
        this.value = value;
    }


    public static RestECG fromInt(int i) {
        for (RestECG cp: RestECG.values()) {
            if (cp.value == i) {
                return cp;
            }
        }
        return null;
    }

}
