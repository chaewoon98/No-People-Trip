package com.test.mosun.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QRData implements Serializable {

    @SerializedName("qr_id")
    String qr_id;


    //id,age,gender,email,name,birthday
    public QRData(String qr_id) {
        this.qr_id = qr_id;
    }
        public String getQRID() {
        return qr_id;
    }
}
