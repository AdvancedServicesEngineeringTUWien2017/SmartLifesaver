package com.filip.smartlifesaver.model;


import com.filip.smartlifesaver.model.enums.ChestPainType;
import com.filip.smartlifesaver.model.enums.RestECG;
import com.filip.smartlifesaver.model.enums.SlopePeakExercise;
import com.filip.smartlifesaver.model.enums.Thal;
import lombok.Getter;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;

public class HeartDataLibSVMData {

    private int age;

    private int sexEquals1;

    private int cpEquals1;

    private int cpEquals2;

    private int cpEquals3;

    private int cpEquals4;

    private int trestbps;

    private int chol;

    private int fbsEquals1;

    private int restEcg0;

    private int restEcg1;

    private int restEcg2;

    private int thalach;

    private int exangEquals1;

    private float oldpeak;

    private int slopeEquals1;

    private int slopeEquals2;

    private int slopeEquals3;

    private int caEquals0;

    private int caEquals1;

    private int caEquals2;

    private int caEquals3;

    private int thalEquals3;

    private int thalEquals6;

    private int thalEquals7;

    @Getter
    private int num;


    public HeartDataLibSVMData(HeartData heartData) {

        this.age = heartData.getAge();
        this.sexEquals1 = heartData.isMale() ? 1 : 0;
        this.cpEquals1 = heartData.getChestPainType() == ChestPainType.TYPICAL_ANGINA ? 1 : 0;
        this.cpEquals2 = heartData.getChestPainType() == ChestPainType.ATYPICAL_ANGINA ? 1 : 0;
        this.cpEquals3 = heartData.getChestPainType() == ChestPainType.NON_ANGINAL_PAIN ? 1 : 0;
        this.cpEquals4 = heartData.getChestPainType() == ChestPainType.ASYMPTOMATIC ? 1 : 0;
        this.trestbps = heartData.getTestBPS();
        this.chol = heartData.getCholesterol();
        this.fbsEquals1 = heartData.isMuchFBS() ? 1 : 0;
        this.restEcg0 = heartData.getRestECG() == RestECG.NORMAL ? 1 : 0;
        this.restEcg1 = heartData.getRestECG() == RestECG.STT_WAVE_ABNORMALITY ? 1 : 0;
        this.restEcg2 = heartData.getRestECG() == RestECG.LEFT_VENTICULAR_HYPERTROPHY ? 1 : 0;
        this.thalach = heartData.getThalach();
        this.exangEquals1 = heartData.isExInducedAng() ? 1 : 0;
        this.oldpeak = heartData.getOldPeak();
        this.slopeEquals1 = heartData.getSlopePeakExercise() == SlopePeakExercise.UPSLOPPING ? 1 : 0;
        this.slopeEquals2 = heartData.getSlopePeakExercise() == SlopePeakExercise.FLAT ? 1 : 0;
        this.slopeEquals3 = heartData.getSlopePeakExercise() == SlopePeakExercise.DOWNSLOPPING ? 1 : 0;
        this.caEquals0 = heartData.getMajorVesselsCount() == 0 ? 1 : 0;
        this.caEquals1 = heartData.getMajorVesselsCount() == 1 ? 1 : 0;
        this.caEquals2 = heartData.getMajorVesselsCount() == 2 ? 1 : 0;
        this.caEquals3 = heartData.getMajorVesselsCount() == 3 ? 1 : 0;
        this.thalEquals3 = heartData.getThal() == Thal.NORMAL ? 1 : 0;
        this.thalEquals6 = heartData.getThal() == Thal.FIXED_DEFECT ? 1 : 0;
        this.thalEquals7 = heartData.getThal() == Thal.REVERSABLE_DEFECT ? 1 : 0;

        this.num = heartData.getDiagnosisOfHeartDisease();
    }

    public Vector createVector() {

        return  Vectors.dense(this.age, this.sexEquals1, this.cpEquals1, this.cpEquals2, this.cpEquals3, this.cpEquals4,
                this.trestbps, this.chol, this.fbsEquals1, this.restEcg0, this.restEcg1, this.restEcg2, this.thalach,
                this.exangEquals1, this.oldpeak, this.slopeEquals1, this.slopeEquals2, this.slopeEquals3,
                this.caEquals0, this.caEquals1, this.caEquals2, this.caEquals3, this.thalEquals3, this.thalEquals6, this.thalEquals7);


    }


    @Override
    public String toString() {
        return num +
                " 1:" + age +
                " 2:" + sexEquals1 +
                " 3:" + cpEquals1 +
                " 4:" + cpEquals2 +
                " 5:" + cpEquals3 +
                " 6:" + cpEquals4 +
                " 7:" + trestbps +
                " 8:" + chol +
                " 9:" + fbsEquals1 +
                " 10:" + restEcg0 +
                " 11:" + restEcg1 +
                " 12:" + restEcg2 +
                " 13:" + thalach +
                " 14:" + exangEquals1 +
                " 15:" + oldpeak +
                " 16:" + slopeEquals1 +
                " 17:" + slopeEquals2 +
                " 18:" + slopeEquals3 +
                " 19:" + caEquals0 +
                " 20:" + caEquals1 +
                " 21:" + caEquals2 +
                " 22:" + caEquals3 +
                " 23:" + thalEquals3 +
                " 24:" + thalEquals6 +
                " 25:" + thalEquals7;
    }
}
