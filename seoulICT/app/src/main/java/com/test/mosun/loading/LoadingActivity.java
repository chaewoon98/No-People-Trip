package com.test.mosun.loading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.data.LoginData;
import com.test.mosun.data.LoginResponse;
import com.test.mosun.data.modelResponse;
import com.test.mosun.home.areaItem;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.network.NetworkActivity;
import com.test.mosun.network.NetworkStatus;
import com.test.mosun.network.RetrofitClient;
import com.test.mosun.network.ServiceApi;
import com.test.mosun.stamp.TourList;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoadingActivity extends AppCompatActivity {
    // 텐서 플로우 라이트에 넘겨주는 데이터
    // 인공지능 정보 넣어줄 정보들
    // 여기 다 넣어주세요
    private float avgTemp;
    private float minTemp;
    private float maxTemp;
    private float confirmedCorona = -1;

    private Interpreter kungbokgoungInterpreter = null;
    private Interpreter ducksugoungInterpreter = null;
    private Interpreter changkunggoungInterpreter = null;
    private Interpreter changduckgoungInterpreter = null;

    private Interpreter sunrungInterpreter;
    private Interpreter jeongrungInterpreter;
    private Interpreter taerungInterpreter;
    private Interpreter uirungInterpreter;
    private Interpreter hunrungInterpreter;
    private Interpreter younghwiwonInterpreter;

    // 왕릉
    private float sunrungPridictionNumber;
    private float jeongrungPridictionNumber;
    private float taerungPridictionNumber;
    private float uirungPridictionNumber;
    private float hunrungPridictionNumber;
    private float younghwiwonPridictionNumber;

    private float kungbokgoungPridictionNumber;
    private float ducksugoungPridictionNumber;
    private float changkunggoungPridictionNumber;
    private float changduckgoungPridictionNumber;

    ArrayList<TourList> tourList = null;
    private ServiceApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        //onclearData(); //데이터 지우기
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(this::getCoronaInfoData);
        es.execute(this::getWeatherInfoData);
        es.execute(new Runnable() {
            @Override
            public void run() {
                if(confirmedCorona == -1)
                {
                    try {
                        Thread.sleep(500);
                        initTensorflow();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    initTensorflow();
                }
            }
        });
        es.execute(this::checkData);
//        es.execute(this::getNetworkConnection);
        es.shutdown();
//        initTensorflow();
//        checkData();
        getNetworkConnection(); //네트워크 연결 확인


    }


    /***** 네트워크 연결상태 확인하기 *****/
    private void getNetworkConnection() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if (status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
            getLoginData();
        } else {
            finish();
            Intent intent = new Intent(getApplicationContext(), NetworkActivity.class);
            startActivity(intent);
        }
    }

    /***** 기존 정보 가져오기 *****/
       private void getLoginData() {
        SharedPreferences sp = getSharedPreferences("NPT", MODE_PRIVATE);
        String loginId = sp.getString("user_id", null);

        if (loginId != null) {
            Log.i("모은 loginId", loginId);
            startLogin(new LoginData(loginId));
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Log.i("모은 loginId", "null");
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }


    /**
     * 모든 저장 데이터 삭제
     */
    public void onclearData() {

        Log.i("모은", "데이터 삭제 완료");
        SharedPreferences sp = getSharedPreferences("NPT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

    }


    /******Loading New Data********/
    /*** 데이터 베이스에서 가져와서 추가 + 로컬에 일단 저장해놓고 변동되면 수정 하게 만들기**/



    public void onSaveTourListData() {
        Log.i("onSaveTourListData", "onSaveTourListData");

        ArrayList<TourList> listSeoul;

        //서울
        listSeoul = new ArrayList<>();


        listSeoul.add(new TourList("경복궁", "설명", 37.5792642, 126.9778535, kungbokgoungPridictionNumber, 0, R.drawable.kgoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("덕수궁", "설명", 37.5657008, 126.9740246, ducksugoungPridictionNumber, 0, R.drawable.dgoung, R.drawable.ic_ducksu));
        listSeoul.add(new TourList("창경궁", "설명", 37.5787708, 126.9926811, changkunggoungPridictionNumber, 0, R.drawable.ckgoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("창덕궁", "설명", 37.5808977, 126.9898217, changduckgoungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("선릉", "설명", 37.5029079, 127.0168782, sunrungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));
        listSeoul.add(new TourList("정릉", "설명", 37.4603954, 126.9269338, jeongrungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));
        listSeoul.add(new TourList("헌릉", "설명", 37.4604794, 127.0495097, hunrungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));
        listSeoul.add(new TourList("태릉", "설명", 37.6038265, 127.0225271, taerungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));
        listSeoul.add(new TourList("의릉", "설명", 37.6038317, 127.0553579, uirungPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));
        listSeoul.add(new TourList("영휘원", "설명", 37.5885055, 127.0414405, younghwiwonPridictionNumber, 0, R.drawable.cdkoung, R.drawable.ic_neung));

        Collections.sort(listSeoul, new SortListByPredictNumber());


        AppManager.getInstance().setTourList(listSeoul);


    }

    private void checkData() {
        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);
        AppManager.getInstance().setuserSns(prefs.getString("userSns", ""));
        Log.i("모은", "userSns(main) " + AppManager.getInstance().getuserSns());
        if (prefs.contains("경복궁")) {

            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(() -> onSaveTourListData());
            es.submit(() -> loadData());
//            es.submit(()->ccc());
            es.shutdown();
//            onSaveTourListData();
//            loadData();
            Log.i("모은 checkData", "o");
        } else {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(() -> onSaveTourListData());
            es.submit(() -> saveData());
            es.shutdown();
//            onSaveTourListData();
//            saveData();
            Log.i("모은 checkData", "x");

        }

    }

    private void saveData() {
        Log.i("모은 saveData", "saveData");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();

        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (TourList item : list) {
            editor.putString(item.getTourTitle(), Boolean.toString(item.isCollected()));
        }

        editor.commit();
    }

    @SuppressLint("LongLogTag")
    private void loadData() {
        Log.i("모은 loadData(loading)", "loadData");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);

        for (TourList item : list) {
            String isCollected = prefs.getString(item.getTourTitle(), "");
            Log.i("모은 loadData(loading)", isCollected);
            item.setCollected(Boolean.valueOf(isCollected));

        }


        AppManager.getInstance().setTourList(list);

        AppManager.getInstance().setStampCount(Integer.parseInt(prefs.getString("stampCount", "")));
        AppManager.getInstance().setMaskCount(Integer.parseInt(prefs.getString("maskCount", "")));
        Log.i("모은", "stampCount(loading) " + AppManager.getInstance().getStampCount());


    }


    private void initTensorflow() {
        try {

            kungbokgoungInterpreter = new Interpreter(loadModel(getAssets(), "kgoung.tflite", kungbokgoungInterpreter));
            ducksugoungInterpreter = new Interpreter(loadModel(getAssets(), "dgoung.tflite", ducksugoungInterpreter));
            changkunggoungInterpreter = new Interpreter(loadModel(getAssets(), "ckgoung.tflite", changkunggoungInterpreter));
            changduckgoungInterpreter = new Interpreter(loadModel(getAssets(), "cdgoung.tflite", changduckgoungInterpreter));


            sunrungInterpreter = new Interpreter(loadModel(getAssets(), "sunrung.tflite", changduckgoungInterpreter));
            jeongrungInterpreter = new Interpreter(loadModel(getAssets(), "jeongrung.tflite", changduckgoungInterpreter));
            taerungInterpreter = new Interpreter(loadModel(getAssets(), "taerung.tflite", changduckgoungInterpreter));
            uirungInterpreter = new Interpreter(loadModel(getAssets(), "uirung.tflite", changduckgoungInterpreter));
            hunrungInterpreter = new Interpreter(loadModel(getAssets(), "hunrung.tflite", changduckgoungInterpreter));
            younghwiwonInterpreter = new Interpreter(loadModel(getAssets(), "youngHwiWon.tflite", changduckgoungInterpreter));

            float[] inputVal = new float[4];
            inputVal[0] = avgTemp;
            inputVal[1] = minTemp;
            inputVal[2] = maxTemp;
            inputVal[3] = confirmedCorona;

            Log.i("모은","inputVal[0] : "+inputVal[0]);
            Log.i("모은","inputVal[1] : "+inputVal[1]);
            Log.i("모은","inputVal[2] : "+inputVal[2]);
            Log.i("모은","inputVal[3] : "+inputVal[3]);

            float[][] outputVal = new float[1][1];
            kungbokgoungInterpreter.run(inputVal, outputVal);
            kungbokgoungPridictionNumber = outputVal[0][0];
            ducksugoungInterpreter.run(inputVal, outputVal);
            ducksugoungPridictionNumber = outputVal[0][0];
            changkunggoungInterpreter.run(inputVal, outputVal);
            changkunggoungPridictionNumber = outputVal[0][0];
            changduckgoungInterpreter.run(inputVal, outputVal);
            changduckgoungPridictionNumber = outputVal[0][0];

            sunrungInterpreter.run(inputVal, outputVal);
            sunrungPridictionNumber = outputVal[0][0];

            hunrungInterpreter.run(inputVal, outputVal);
            hunrungPridictionNumber = outputVal[0][0];

            taerungInterpreter.run(inputVal, outputVal);
            taerungPridictionNumber = outputVal[0][0];

            jeongrungInterpreter.run(inputVal, outputVal);
            jeongrungPridictionNumber = outputVal[0][0];

            uirungInterpreter.run(inputVal, outputVal);
            uirungPridictionNumber = outputVal[0][0];

            younghwiwonInterpreter.run(inputVal, outputVal);
            younghwiwonPridictionNumber = outputVal[0][0];

            sunrungInterpreter.run(inputVal, outputVal);
            sunrungPridictionNumber = outputVal[0][0];

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 인공지능 파일 연결함수
    MappedByteBuffer loadModel(AssetManager assest, String modelFile, Interpreter interpreter) throws IOException {
        AssetFileDescriptor fileDescriptor = assest.openFd(modelFile);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        interpreter = new Interpreter(buffer);
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    protected void startLogin(LoginData data) {

        service.getUserData(data).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse result = response.body();


                Log.i("모은", "startLogin 들어옴 + " + result.getUserName());
                AppManager.getInstance().setUserName(result.getUserName());

//
////                onSaveLoginData();
////                goToNextActivity();
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("모은 ", " 로그인 에러 발생" + t.getMessage());

            }
        });
    }

    protected void getCoronaInfoData() {


        service.getCoronaInfoData().enqueue(new Callback<modelResponse>() {
            @Override
            public void onResponse(Call<modelResponse> call, Response<modelResponse> response) {
                modelResponse result = response.body();
                Log.i("모은", "getCoronaInfoData : " + result.getCoronaNum());
                confirmedCorona = (float) Double.parseDouble(result.getCoronaNum());
            }

            @Override
            public void onFailure(Call<modelResponse> call, Throwable t) {
                Log.e("모은 ", " 코로나 에러 발생" + t.getMessage());
            }
        });

    }

    protected void getWeatherInfoData() {
        service.getWeatherInfoData().enqueue(new Callback<modelResponse>() {
            @Override
            public void onResponse(Call<modelResponse> call, Response<modelResponse> response) {
                modelResponse result = response.body();
                Log.i("모은", "getWeatherInfoData(temp_min) : " + result.getTempMin());
                Log.i("모은", "getWeatherInfoData(temp_max) : " + result.getTempMax());
                Log.i("모은", "getWeatherInfoData(temp_avg) : " + result.getTempAvg());
                minTemp = (float) result.getTempMin();
                maxTemp = (float) result.getTempMax();
                avgTemp = (float) result.getTempAvg();

            }

            @Override
            public void onFailure(Call<modelResponse> call, Throwable t) {
                Log.e("모은 ", " 날씨 에러 발생" + t.getMessage());
            }
        });

    }
}

