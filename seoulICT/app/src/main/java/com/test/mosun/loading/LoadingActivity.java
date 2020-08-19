package com.test.mosun.loading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.home.areaItem;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.network.NetworkActivity;
import com.test.mosun.network.NetworkStatus;
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


public class LoadingActivity extends AppCompatActivity {
    // 텐서 플로우 라이트에 넘겨주는 데이터
    // 인공지능 정보 넣어줄 정보들
    private float avgTemp =27;
    private float minTemp=24;
    private float maxTemp=29;
    private float rainFall=5;
    private float holiday=0;
    private float jan=0;
    private float feb=0;
    private float mar=0;
    private float apr=0;
    private float may=0;
    private float jun=0;
    private float jul=1;
    private float aug=0;
    private float sep=0;
    private float oct=0;
    private float nov=0;
    private float dec=0;

    private Interpreter kungbokgoungInterpreter = null;
    private Interpreter ducksugoungInterpreter = null;
    private Interpreter changkunggoungInterpreter = null;
    private Interpreter changduckgoungInterpreter = null;
    private Interpreter seoulSkyInterpreter = null;
    private Interpreter acuariumInterpreter = null;
    private Interpreter seodaemunPrisonHistoryMuseumInterpreter = null;
    private Interpreter jongmyoInterpreter = null;
    private Interpreter nationalMuseumInterpreter = null;
    private Interpreter kingSejongInterpreter = null;
    private Interpreter seoulMuseumOfArtInterpreter = null;
    private Interpreter seoulParkInterpreter = null;
    private Interpreter namsangolHanokVillageInterpreter = null;

    private Interpreter sunrungInterpreter;
    private Interpreter jeongrungInterpreter;
    private Interpreter taerungInterpreter;
    private Interpreter uirungInterpreter;
    private Interpreter hunrungInterpreter;
    private Interpreter younghwiwonInterpreter;

    private Interpreter gyeonggijeonInterpreter = null;
    private Interpreter nationalMuseumInjeonjuInterpreter = null;
    private Interpreter sparacuaInterpreter = null;
    private Interpreter zooInJeonjuInterpreter = null;
    private Interpreter hanbyukInterpreter = null;
    private Interpreter railBikeInterpreter = null;
    private Interpreter arboretumInterpreter = null;

    private Interpreter kangcheonMountainInterpreter = null;
    private Interpreter fruitVilageInterpreter = null;
    private Interpreter pepperVilageInterpreter = null;
    private Interpreter jangruInterpreter = null;
    private Interpreter mountainMuseumInterpreter = null;
    private Interpreter fermentationSauceInterpreter = null;


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

    private float seoulSkyPridictionNumber;
    private float acuariumPridictionNumber;
    private float seodaemunPrisonHistoryMuseumPridictionNumber;
    private float jongmyoPridictionNumber;
    private float seoulParkPridictionNumber;
    private float nationalMuseumPridictionNumber;
    private float kingSejongPridictionNumber;
    private float seoulMuseumOfArtPridictionNumber;
    private float namsangolHanokVillagePridictionNumber;

    private float gyeonggijeonPridictionNumber;
    private float nationalMuseumInjeonjuPridictionNumber;
    private float sparacuaPridictionNumber;
    private float zooInJeonjuPridictionNumber;
    private float hanbyukPridictionNumber;
    private float railBikePridictionNumber;
    private float arboretumPridictionNumber;

    private float kangcheonMountainPridictionNumber;
    private float fruitVilagePridictionNumber;
    private float pepperVilagePridictionNumber;
    private float jangruPridictionNumber;
    private float mountainMuseumPridictionNumber;
    private float fermentationSaucePridictionNumber;

    ArrayList<TourList> tourList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        initTensorflow();
        checkData();
        getNetworkConnection(); //네트워크 연결 확인




    }


    /***** 네트워크 연결상태 확인하기 *****/
    private void getNetworkConnection()
    {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI){
//            textView.setText("모바일로 연결됨");

            onSaveAreaData();
            //onSaveTourListData();

            getLoginData();
        }else if (status == NetworkStatus.TYPE_WIFI){
//            textView.setText("무선랜으로 연결됨");
        }else {
            finish();
            Intent intent = new Intent(getApplicationContext(), NetworkActivity.class);
            startActivity(intent);
//            textView.setText("연결 안됨.");
        }
    }
    /***** 기존 정보 가져오기 *****/
    private void getLoginData()
    {
        SharedPreferences sp = getSharedPreferences("NPT", MODE_PRIVATE);
        String loginId = sp.getString("user_id",null);

        if(loginId != null )
        {
            Log.i("모은 loginId",loginId);
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Log.i("모은 loginId","null");
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }



    /**
     * 모든 저장 데이터 삭제
     */
    public void onclearData() {

        SharedPreferences sp = getSharedPreferences("NPT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

    }


    /******Loading New Data********/
    /*** 데이터 베이스에서 가져와서 추가 + 로컬에 일단 저장해놓고 변동되면 수정 하게 만들기**/

    public void onSaveAreaData() {
        ArrayList<areaItem> list;
        list = new ArrayList<>();
        list.add(new areaItem(0, "area_0", "서울","한국의 궁과 능을 둘러보세요"));
        list.add(new areaItem(0, "area_1", "전주","더 늦기 전에 지금 전주를 즐겨보세요"));
        list.add(new areaItem(0, "area_2", "순창","순창의 골목골목을 즐겨보세요"));
        list.add(new areaItem(0, "area_2", "고창","맛과 멋, 풍류 역사의 숨결을 느껴봐요"));
        list.add(new areaItem(0, "area_2", "나주","이천년 시간 여행을 떠나보아요"));
        list.add(new areaItem(0, "area_2", "단양","몸과 마음이 치유되는 단양으로 놀러오세요"));

        AppManager.getInstance().setAreaList(list);
    }

    public void onSaveTourListData() {
        Log.i("onSaveTourListData","onSaveTourListData");

        ArrayList<TourList> listSeoul;
        ArrayList<TourList> listJeonju;
        ArrayList<TourList> listSunchang;
        //서울
        listSeoul = new ArrayList<>();
        listJeonju = new ArrayList<>();
        listSunchang = new ArrayList<>();

        listSeoul.add(new TourList("경복궁", "설명", 37.5792642,126.9778535, kungbokgoungPridictionNumber,0, R.drawable.kgoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("덕수궁", "설명", 37.5657008,126.9740246, ducksugoungPridictionNumber,0, R.drawable.dgoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("창경궁", "설명", 37.5787708,126.9926811, changkunggoungPridictionNumber,0, R.drawable.ckgoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("창덕궁", "설명", 37.5808977,126.9898217, changduckgoungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("서울스카이", "설명",37.5126108,127.1005055 , seoulSkyPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("아쿠아리움", "설명", 37.513131,127.056094, acuariumPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("서대문형무소역사관", "설명", 37.574271,126.9538823, seodaemunPrisonHistoryMuseumPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("서울대공원", "설명", 37.4277758,127.0088695,seoulParkPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("종묘", "설명", 37.574583,126.9919543, jongmyoPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("국립중앙박물관", "설명", 37.5238879,126.9786427, nationalMuseumPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("서울시립미술관", "설명", 37.5907778,127.0085263, seoulMuseumOfArtPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("세종대왕기념관", "설명", 37.590783,127.0413571, kingSejongPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("남산골한옥마을", "설명", 37.559315,126.9922883, namsangolHanokVillagePridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("선릉", "설명", 37.5029079,127.0168782, sunrungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("정릉", "설명", 37.4603954,126.9269338, jeongrungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("헌릉", "설명", 37.4604794,127.0495097, hunrungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("태릉", "설명", 37.6038265,127.0225271, taerungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("의릉", "설명", 37.6038317,127.0553579, uirungPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));
        listSeoul.add(new TourList("영희원", "설명", 37.5885055,127.0414405, younghwiwonPridictionNumber,0, R.drawable.cdkoung, R.drawable.viewpager_icon2));

        Collections.sort(listSeoul, new SortListByPredictNumber());


        AppManager.getInstance().setTourList(listSeoul);




    }
    private void checkData()
    {
        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);
        if(prefs.contains("경복궁"))
        {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(() -> onSaveTourListData());
            es.submit(() -> loadData());
//            es.submit(()->ccc());
            es.shutdown();
//            onSaveTourListData();
//            loadData();
            Log.i("모은 checkData","o");
        }
        else
        {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(() -> onSaveTourListData());
            es.submit(() -> saveData());
            es.shutdown();
//            onSaveTourListData();
//            saveData();
            Log.i("모은 checkData","x");

        }

    }

    private void saveData()
    {
        Log.i("모은 saveData","saveData");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();

        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(TourList item : list)
        {
            editor.putString(item.getTourTitle(),Boolean.toString(item.isCollected()));
        }

        editor.commit();
    }

    @SuppressLint("LongLogTag")
    private void loadData()
    {
        Log.i("모은 loadData(loading)","loadData");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);

        for(TourList item : list) {
            String isCollected = prefs.getString(item.getTourTitle(), "");
            Log.i("모은 loadData(loading)", isCollected);
            item.setCollected(Boolean.valueOf(isCollected));

        }
        AppManager.getInstance().setTourList(list);




    }


    void initTensorflow(){
        try {
            kungbokgoungInterpreter = new Interpreter(loadModel(getAssets(), "kgoung.tflite",kungbokgoungInterpreter));
            ducksugoungInterpreter = new Interpreter(loadModel(getAssets(), "dgoung.tflite",ducksugoungInterpreter));
            changkunggoungInterpreter = new Interpreter(loadModel(getAssets(), "ckgoung.tflite",changkunggoungInterpreter));
            changduckgoungInterpreter = new Interpreter(loadModel(getAssets(), "cdgoung.tflite", changduckgoungInterpreter));

            seoulSkyInterpreter = new Interpreter(loadModel(getAssets(), "seoultower.tflite", changduckgoungInterpreter));
            acuariumInterpreter = new Interpreter(loadModel(getAssets(), "cuarium.tflite", changduckgoungInterpreter));
            seodaemunPrisonHistoryMuseumInterpreter = new Interpreter(loadModel(getAssets(), "seodaemunMuseum.tflite", changduckgoungInterpreter));
            jongmyoInterpreter = new Interpreter(loadModel(getAssets(), "jongmyo.tflite", changduckgoungInterpreter));
            nationalMuseumInterpreter = new Interpreter(loadModel(getAssets(), "museuminseoul.tflite", changduckgoungInterpreter));
            kingSejongInterpreter = new Interpreter(loadModel(getAssets(), "kingsejongmuseum.tflite", changduckgoungInterpreter));
            seoulParkInterpreter = new Interpreter(loadModel(getAssets(), "parkseoul.tflite", changduckgoungInterpreter));
            seoulMuseumOfArtInterpreter = new Interpreter(loadModel(getAssets(), "museumart.tflite", changduckgoungInterpreter));
            namsangolHanokVillageInterpreter = new Interpreter(loadModel(getAssets(), "hanokcity.tflite", changduckgoungInterpreter));

            sunrungInterpreter = new Interpreter(loadModel(getAssets(), "sunrung.tflite", changduckgoungInterpreter));
            jeongrungInterpreter = new Interpreter(loadModel(getAssets(), "jeongrung.tflite", changduckgoungInterpreter));
            taerungInterpreter = new Interpreter(loadModel(getAssets(), "taerung.tflite", changduckgoungInterpreter));;
            uirungInterpreter = new Interpreter(loadModel(getAssets(), "uirung.tflite", changduckgoungInterpreter));;
            hunrungInterpreter = new Interpreter(loadModel(getAssets(), "hunrung.tflite", changduckgoungInterpreter));;
            younghwiwonInterpreter = new Interpreter(loadModel(getAssets(), "youngHwiWon.tflite", changduckgoungInterpreter));;

            gyeonggijeonInterpreter = new Interpreter(loadModel(getAssets(), "jeonjujun.tflite", changduckgoungInterpreter));
            nationalMuseumInjeonjuInterpreter = new Interpreter(loadModel(getAssets(), "junjumuseum.tflite", changduckgoungInterpreter));
            sparacuaInterpreter = new Interpreter(loadModel(getAssets(), "jeonjuspa.tflite", changduckgoungInterpreter));
            zooInJeonjuInterpreter = new Interpreter(loadModel(getAssets(), "junjuzoo.tflite", changduckgoungInterpreter));
            hanbyukInterpreter = new Interpreter(loadModel(getAssets(), "jeonjuhanbyeok.tflite", changduckgoungInterpreter));
            railBikeInterpreter = new Interpreter(loadModel(getAssets(), "jeonjubike.tflite", changduckgoungInterpreter));
            arboretumInterpreter = new Interpreter(loadModel(getAssets(), "jeonjuarboretum.tflite", changduckgoungInterpreter));

            kangcheonMountainInterpreter = new Interpreter(loadModel(getAssets(), "sunchangmountain.tflite", changduckgoungInterpreter));
            fruitVilageInterpreter = new Interpreter(loadModel(getAssets(), "sunchangvilage.tflite", changduckgoungInterpreter));
            pepperVilageInterpreter = new Interpreter(loadModel(getAssets(), "sunchangpepper.tflite", changduckgoungInterpreter));
            jangruInterpreter = new Interpreter(loadModel(getAssets(), "sunchangjangru.tflite", changduckgoungInterpreter));
            mountainMuseumInterpreter = new Interpreter(loadModel(getAssets(), "sunchangmuseum.tflite", changduckgoungInterpreter));
            fermentationSauceInterpreter = new Interpreter(loadModel(getAssets(), "sunchangsource.tflite", changduckgoungInterpreter));

            float[] inputVal = new float[17];
            inputVal[0] = avgTemp;
            inputVal[1] = minTemp;
            inputVal[2] = maxTemp;
            inputVal[3] = rainFall;
            inputVal[4] = holiday;
            inputVal[5] = jan;
            inputVal[6] = feb;
            inputVal[7] = mar;
            inputVal[8] = apr;
            inputVal[9] = may;
            inputVal[10] = jun;
            inputVal[11] = jul;
            inputVal[12] = aug;
            inputVal[13] = sep;
            inputVal[14] = oct;
            inputVal[15] = nov;
            inputVal[16] = dec;
            float[][] outputVal = new float[1][1];
            kungbokgoungInterpreter.run(inputVal, outputVal);
            kungbokgoungPridictionNumber = outputVal[0][0];
            ducksugoungInterpreter.run(inputVal, outputVal);
            ducksugoungPridictionNumber = outputVal[0][0];
            changkunggoungInterpreter.run(inputVal,outputVal);
            changkunggoungPridictionNumber = outputVal[0][0];
            changduckgoungInterpreter.run(inputVal,outputVal);
            changduckgoungPridictionNumber = outputVal[0][0];

            seoulSkyInterpreter.run(inputVal,outputVal);
            seoulSkyPridictionNumber = outputVal[0][0];

            acuariumInterpreter.run(inputVal,outputVal);
            acuariumPridictionNumber = outputVal[0][0];

            seodaemunPrisonHistoryMuseumInterpreter.run(inputVal,outputVal);
            seodaemunPrisonHistoryMuseumPridictionNumber = outputVal[0][0];
            jongmyoInterpreter.run(inputVal,outputVal);
            jongmyoPridictionNumber = outputVal[0][0];
            seoulParkInterpreter.run(inputVal, outputVal);
            seoulParkPridictionNumber = outputVal[0][0];
            nationalMuseumInterpreter.run(inputVal,outputVal);
            nationalMuseumPridictionNumber = outputVal[0][0];
            kingSejongInterpreter.run(inputVal,outputVal);
            kingSejongPridictionNumber = outputVal[0][0];
            seoulMuseumOfArtInterpreter.run(inputVal,outputVal);
            seoulMuseumOfArtPridictionNumber = outputVal[0][0];
            namsangolHanokVillageInterpreter.run(inputVal,outputVal);
            namsangolHanokVillagePridictionNumber = outputVal[0][0];
            sunrungInterpreter.run(inputVal,outputVal);
            sunrungPridictionNumber = outputVal[0][0];

            hunrungInterpreter.run(inputVal,outputVal);
            hunrungPridictionNumber = outputVal[0][0];

            taerungInterpreter.run(inputVal,outputVal);
            taerungPridictionNumber = outputVal[0][0];

            jeongrungInterpreter.run(inputVal,outputVal);
            jeongrungPridictionNumber = outputVal[0][0];

            uirungInterpreter.run(inputVal,outputVal);
            uirungPridictionNumber = outputVal[0][0];

            younghwiwonInterpreter.run(inputVal,outputVal);
            younghwiwonPridictionNumber = outputVal[0][0];

            sunrungInterpreter.run(inputVal,outputVal);
            sunrungPridictionNumber = outputVal[0][0];

            gyeonggijeonInterpreter.run(inputVal,outputVal);
            gyeonggijeonPridictionNumber = outputVal[0][0];
            nationalMuseumInjeonjuInterpreter.run(inputVal,outputVal);
            nationalMuseumInjeonjuPridictionNumber = outputVal[0][0];
            sparacuaInterpreter.run(inputVal,outputVal);
            sparacuaPridictionNumber = outputVal[0][0];
            zooInJeonjuInterpreter.run(inputVal,outputVal);
            zooInJeonjuPridictionNumber = outputVal[0][0];
            hanbyukInterpreter.run(inputVal,outputVal);
            hanbyukPridictionNumber = outputVal[0][0];
            railBikeInterpreter.run(inputVal,outputVal);
            railBikePridictionNumber =outputVal[0][0];
            arboretumInterpreter.run(inputVal,outputVal);
            arboretumPridictionNumber = outputVal[0][0];
            kangcheonMountainInterpreter.run(inputVal,outputVal);
            kangcheonMountainPridictionNumber = outputVal[0][0];
            fruitVilageInterpreter.run(inputVal,outputVal);
            fruitVilagePridictionNumber = outputVal[0][0];
            pepperVilageInterpreter.run(inputVal,outputVal);
            pepperVilagePridictionNumber = outputVal[0][0];
            jangruInterpreter.run(inputVal,outputVal);
            jangruPridictionNumber = outputVal[0][0];
            mountainMuseumInterpreter.run(inputVal,outputVal);
            mountainMuseumPridictionNumber = outputVal[0][0];
            fermentationSauceInterpreter.run(inputVal,outputVal);
            fermentationSaucePridictionNumber = outputVal[0][0];

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
}

