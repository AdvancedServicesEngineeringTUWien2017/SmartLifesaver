package com.filip.smartlifesaver.ml.impl;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EvaluationWrapper implements Comparable<EvaluationWrapper> {

    private double accuracy;
    private double precision;
    private double recall;
    private double f1score;

    @Override
    public int compareTo(EvaluationWrapper o) {
        return Double.compare(f1score, o.f1score);
    }
}
