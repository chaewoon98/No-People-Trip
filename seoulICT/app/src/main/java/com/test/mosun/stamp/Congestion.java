package com.test.mosun.stamp;

import android.util.Log;
import android.widget.Switch;

public class Congestion {

    public Congestion(){

    }

    // 혼잡도 분석
    public String congestAnalysis(String name, float predictNumber){

        if(name.equals("경복궁")){
            return congestKgoung(predictNumber);
        } else if(name.equals("덕수궁")){
            return congestDgoung(predictNumber);
        } else if(name.equals("창덕궁")){
            return congestCDgoung(predictNumber);
        } else if(name.equals("창경궁")){
            return congestCKgoung(predictNumber);
        } else if(name.equals("태릉")){
            return congestTaerung(predictNumber);
        } else if(name.equals("선릉")){
            return congestSunrung(predictNumber);
        } else if(name.equals("정릉")){
            return congestJungrung(predictNumber);
        } else if(name.equals("헌릉")){
            return congestHunrung(predictNumber);
        } else if(name.equals("의릉")){
            return congestUwirung(predictNumber);
        } else if(name.equals("영휘원")){
            return congestYoungHuiwon(predictNumber);
        }

        return "err";
    }


    public String congestKgoung(float predictNumber){
        if(predictNumber >= 630){
            return "red";
        } else if(predictNumber >= 315) {
            return "yellow";
        }

        return "green";
    }


    public String congestDgoung(float predictNumber){

        if(predictNumber >= 630 && predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 315) {
            return "yellow";
        }

        return "green";
    }

    public String congestCDgoung(float predictNumber){

        if(predictNumber >= 5509 && predictNumber <= 0 ){
            return "red";
        } else if(predictNumber >= 2754) {
            return "yellow";
        }

        return "green";
    }

    public String congestCKgoung(float predictNumber){
        if(predictNumber >= 2181 && predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 1090) {
            return "yellow";
        }

        return "green";
    }

    public String congestTaerung(float predictNumber){
        if(predictNumber >= 16390 && predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 8190) {
            return "yellow";
        }

        return "green";
    }

    public String congestSunrung(float predictNumber){
        if(predictNumber >= 1988 && predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 994) {
            return "yellow";
        }

        return "green";
    }

    public String congestJungrung(float predictNumber){
        if(predictNumber >= 2995&& predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 1497) {
            return "yellow";
        }

        return "green";
    }

    public String congestHunrung(float predictNumber){
        if(predictNumber >= 11930&& predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 5965) {
            return "yellow";
        }

        return "green";
    }

    public String congestUwirung(float predictNumber){
        if(predictNumber >= 4386&& predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 2193) {
            return "yellow";
        }

        return "green";
    }

    public String congestYoungHuiwon(float predictNumber){
        if(predictNumber >= 550&& predictNumber <= 0){
            return "red";
        } else if(predictNumber >= 275) {
            return "yellow";
        }

        return "green";
    }

}
