package com.test.mosun.network;

import com.test.mosun.data.LoginData;
import com.test.mosun.data.LoginResponse;
import com.test.mosun.data.QRData;
import com.test.mosun.data.QRResponse;
import com.test.mosun.data.modelResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    //user 정보
    @POST("/user/getUserData")
    Call<LoginResponse> getUserData(@Body LoginData data);
    @POST("/user/login")
    Call<LoginResponse> userLogin(@Body LoginData data);


    //qr 정보
    @POST("/user/qrscan")
    Call<QRResponse> qrScan(@Body QRData data);
    @POST("/user/qr_num")
    Call<QRResponse> qrNum(@Body QRData data);

    //model 정보
    @GET("/coronaInfo")
    Call<modelResponse> getCoronaInfoData();
    @GET("/weatherInfo")
    Call<modelResponse> getWeatherInfoData();
}