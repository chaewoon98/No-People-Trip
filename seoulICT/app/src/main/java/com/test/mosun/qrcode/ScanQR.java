package com.test.mosun.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.test.mosun.AppManager;
import com.test.mosun.R;
import com.test.mosun.data.QRData;
import com.test.mosun.data.QRResponse;
import com.test.mosun.network.RetrofitClient;
import com.test.mosun.network.ServiceApi;
import com.test.mosun.stamp.StampAdapter;
import com.test.mosun.stamp.TourList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//하하하하하
public class ScanQR extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private ServiceApi service;
    private Activity thisActivity = this;
    private StampAdapter stampAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        service = RetrofitClient.getClient().create(ServiceApi.class);
        qrScan = new IntentIntegrator(this);
        qrScan.setBeepEnabled(false);
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("관광지의 QR코드를 스캔하세요.");
        qrScan.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                Log.i("scan_qr : " ," 취소");
                finish();
                // todo
            } else {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(result.getContents());
                    String qr_id = obj.getString("qr_id");
                    String qr_name = obj.getString("qr_name");
                    Double qr_longitude = obj.getDouble("qr_longitude");
                    Double qr_latitude = obj.getDouble("qr_latitude");

                    Log.i("qr_log(qr코드에서 받은 정보)","");
                    Log.i("qr_id",qr_id);
                    Log.i("qr_name",qr_name);
                    Log.i("qr_longitude",Double.toString(qr_longitude));
                    Log.i("qr_latitude",Double.toString(qr_latitude));

                    finish();//화면 종료
                    compareWithStampAndQR(qr_id,qr_name,qr_longitude,qr_latitude); //스탬프 찍혔는지 확인



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                // todo
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void compareWithStampAndQR(String qr_id, String qr_name, Double qr_longitude, Double qr_latitude)
    {
        //지역에 따라 리스트 다르게 받아와야함
        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        for(TourList item : list){

            if(item.getTourTitle().equals(qr_name))
            {
                Log.d("scanQR","들어옴");
                Log.d("scanQR",item.getTourTitle());
                Log.d("scanQR",qr_name);
                if(item.isCollected())
                {
                    //이미 스탬프가 찍혔다면
                    Toast.makeText(getApplicationContext(), "이미 스탬프를 찍으셨습니다", Toast.LENGTH_SHORT).show();

                }
                else{
                    //찍히지 않았다면
                    AppManager.getInstance().stampCount++;
                    Log.d("qr_name",qr_name);
                    item.setCollected(true);
                    Toast.makeText(getApplicationContext(), "스탬프를 찍었습니다", Toast.LENGTH_SHORT).show();

                    //item.setImageNumericalValueID(R.drawable.area_0);
                    checkAndUploadQRNum(new QRData(qr_id));
                    // 0811 dialog
                    goToActivity();
                }
                break;
            }

        }
    }

    private void goToActivity()
    {
        Intent intent = new Intent(thisActivity, MaskPopupActivity.class);
        startActivity(intent);
    }


    private void checkAndUploadQRNum(QRData data) {

        service.qrScan(data).enqueue(new Callback<QRResponse>() {
            @Override
            public void onResponse(Call<QRResponse> call, Response<QRResponse> response) {
                QRResponse result = response.body();
                //Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.i("qr_num",result.getQRNum());

                Log.i("qr_log","(qr코드(서버)에서 받은 정보)");
                Log.i("qr코드 num 값 올림",result.getMessage() );
                //Log.i("qr코드 확인",result.getQRName());

//                if(result.getCode()==200)
//                {
////                    stampAdapter.updateAdpater(AppManager.getInstance().getTourList());
//                }
                //스탬프를 오늘 찍었는지 확인

            }


            @Override
            public void onFailure(Call<QRResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("qr스캔 에러 발생", t.getMessage());

            }
        });
    }

}