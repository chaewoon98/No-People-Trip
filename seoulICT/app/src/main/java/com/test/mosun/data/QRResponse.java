package com.test.mosun.data;

import com.google.gson.annotations.SerializedName;

public class QRResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("qr_id")
    private String qr_id;

    @SerializedName("qr_num")
    private String qr_num;

    @SerializedName("qr_name")
    private String qr_name;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getQRID() {
        return qr_id;
    }

    public String getQRNum() {
        return qr_num;
    }
    public String getQRName() {
        return qr_name;
    }
}
