package com.test.mosun.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.test.mosun.R;
import com.test.mosun.data.QRData;
import com.test.mosun.data.QRResponse;
import com.test.mosun.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


////하하하하
public class QRNetworkActivity extends AppCompatActivity {

    public QRData data;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_network);

        Intent intent = getIntent(); //데이터 수신
        QRData data = (QRData)intent.getSerializableExtra("qrdata");//클래스로 받기
        String qr_id = data.getQRID();
        checkAndUploadQRNum(new QRData(qr_id));

    }
    private void checkAndUploadQRNum(QRData data) {

        service.qrScan(data).enqueue(new Callback<QRResponse>() {
            @Override
            public void onResponse(Call<QRResponse> call, Response<QRResponse> response) {
                QRResponse result = response.body();
                //Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("qr_id",result.getQRID());
                // Log.i("startLogin","존재하지 않는 계정");


            }


            @Override
            public void onFailure(Call<QRResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("qr스캔 에러 발생", t.getMessage());

            }
        });
    }
}