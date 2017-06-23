package com.filip.smartlifesaver.model.enums;

/**
 * thal: 3 = normal; 6 = fixed defect; 7 = reversable defect
 */
public enum Thal {

    NORMAL(3), FIXED_DEFECT(6), REVERSABLE_DEFECT(7);

    private int value;

    Thal(int value) {
        this.value = value;
    }

    public static Thal fromInt(int i) {
        for (Thal cp: Thal.values()) {
            if (cp.value == i) {
                return cp;
            }
        }
        return null;
    }


}
