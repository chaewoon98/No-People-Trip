package com.test.mosun.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.test.mosun.R;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateQR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_create_qr);


        ImageView qrCode = (ImageView)findViewById(R.id.qrcode);
        String url = "https://park-duck.tistory.com";


        JSONObject userData = new JSONObject();

        //사용자 정보 주기 > userTable 정보 다 주기 + 위치
        try {
            userData.put("name","모은");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonStr = userData.toString();
        Log.i("jsonStringData", jsonStr);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            //BitMatrix.encode 인자 : contents(원하는 내용), format(바코드 포맷형식),width(가로), height(세로)
            BitMatrix bitMatrix = multiFormatWriter.encode(jsonStr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        }catch (Exception e){}
    }
}