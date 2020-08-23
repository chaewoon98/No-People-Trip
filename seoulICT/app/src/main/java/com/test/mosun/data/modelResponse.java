package com.test.mosun.data;

import com.google.gson.annotations.SerializedName;

public class modelResponse {
    @SerializedName("coronaNum")
    private String coronaNum;

    @SerializedName("temp_min")
    private double temp_min;
    @SerializedName("temp_max")
    private double temp_max;
    @SerializedName("temp_avg")
    private double temp_avg;


    public String getCoronaNum() {
        return coronaNum;
    }
    public double getTempMin() {
        return temp_min;
    }
    public double getTempMax() {
        return temp_max;
    }
    public double getTempAvg() {
        return temp_avg;
    }

}
