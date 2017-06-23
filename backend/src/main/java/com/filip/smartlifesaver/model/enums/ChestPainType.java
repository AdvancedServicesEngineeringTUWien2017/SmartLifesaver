package com.filip.smartlifesaver.model.enums;

/**
 * cp: chest pain type
 -- Value 1: typical angina
 -- Value 2: atypical angina
 -- Value 3: non-anginal pain
 -- Value 4: asymptomatic
 */
public enum ChestPainType {

    TYPICAL_ANGINA(1), ATYPICAL_ANGINA(2), NON_ANGINAL_PAIN(3), ASYMPTOMATIC(4);


    private int value;

    ChestPainType(int value) {
        this.value = value;
    }

    public static ChestPainType fromInt(int i) {
        for (ChestPainType cp: ChestPainType.values()) {
            if (cp.value == i) {
                return cp;
            }
        }
        return null;
    }


}
