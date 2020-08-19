package com.test.mosun.stamp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.data.QRData;
import com.test.mosun.data.QRResponse;
import com.test.mosun.home.Fragment_Home;
import com.test.mosun.home.Fragment_selectArea;
import com.test.mosun.map.GpsInfo;
import com.test.mosun.network.RetrofitClient;
import com.test.mosun.network.ServiceApi;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Stamp extends Fragment {

    private static final long MIN_CLICK_INTERVAL=600;
    private long mLastClickTime;
    private int abc;
    MainActivity mainActivity;
    Spinner spinner;
    StampExpandableGridView stampGridView;
    StampAdapter stampAdapter;
    ArrayList<TourList> tourList, searchList;
    ProgressBar progressBar;
    String searchword;

    GpsInfo gpsInfo;
    Location currentLocation;
    double distance;
    private static String selectedArea = "서울";
    private ServiceApi service;


    public Fragment_Stamp(){

    }

    public static Fragment_Stamp newInstance() {
        Fragment_Stamp fragment = new Fragment_Stamp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        tourList = AppManager.getInstance().getTourList();
//        Bundle bundle = getArguments();
//
//        if(bundle.getString("area") != null){
//            String area = bundle.getString("area"); //Name 받기.
//            selectedArea = area;
//            Log.i("bundle",area);
//            if(area == "서울"){
//                tourList = AppManager.getInstance().getTourList();
//            } else if(area == "전주"){
//                tourList = AppManager.getInstance().getJeonjuList();
//            } else if(area == "순창"){
//                tourList = AppManager.getInstance().getSunchangList();
//            }
//        }
//        else
//        {
//            Toast.makeText(getContext(),"홈화면의 지역선택부터 해주세요",Toast.LENGTH_SHORT).show();
//            ((MainActivity)getActivity()).replaceFragment(Fragment_selectArea.newInstance());
//        }

        searchList = new ArrayList<>();
        searchList.addAll(tourList);
        mainActivity = (MainActivity) getActivity();
        searchword = "";

        //서울 값 받기


        //현재위치에서 관광지까지 거리 계산
        gpsInfo = new GpsInfo(getContext());
        currentLocation = gpsInfo.getLocation();
        Log.i("모은",currentLocation.toString());
        for(TourList item : searchList){
            Log.i("모은",item.getLocationB().toString());
            distance = currentLocation.distanceTo(item.getLocationB());
            item.setDistance(distance);
        }

        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        for(int i=0;i<list.size();i++) {
            getQRNum(new QRData(list.get(i).getTourTitle()),i);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stamp, container, false);

//        for(TourList item :searchList){
//            LinearLayout layout = view.findViewById(R.id.square_linear_layout);
//
//
//            if ( item.isCollected()) {
//                layout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rectangle_iscollected));
//            }
//            if (item.getPridictionNumber() >= 2000) {
//                layout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rectangle_bad));
//            } else if (item.getPridictionNumber() < 2000 && item.getPridictionNumber() > 500) {
//                layout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rectangle_soso));
//            } else {
//                layout.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.rectangle_good));
//            }
//        }




//        stampAdapter.updateAdpater(AppManager.getInstance().getTourList());
        Log.d("onCreateView", "stamp 갱신");

        stampGridView = view.findViewById(R.id.gridview_stamp);
        stampAdapter = new StampAdapter(getContext(),
                R.layout.item_stamp, searchList);
        stampGridView.setAdapter(stampAdapter); // 어댑터를 그리드 뷰에 적용
        stampGridView.setOnItemClickListener(itemClickListener);



        TextView sortByDistance = view.findViewById(R.id.sort_location);
        TextView sortByPredictionNumber = view.findViewById(R.id.sort_predit);

        sortByDistance.setOnClickListener(v -> stampAdapter.filterDistance());
        sortByPredictionNumber.setOnClickListener(v -> stampAdapter.filterPrediction());

        EditText editTextFilter = (EditText)view.findViewById(R.id.search_edit_text) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                if (filterText.length() >= 0) {
                    stampAdapter.filter(filterText);
                } else {

                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });




        //뒤로가기 선택 시
        ImageButton back_btn = (ImageButton)view.findViewById(R.id.back_btn2);
        back_btn.setOnClickListener(new View.OnClickListener() {  //뒤로가기 버튼 누르면 마이페이지(이전 페이지)로 돌아간다.

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_Home.newInstance());
            }
        });




        //qr_num 가져오기
        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        for(int i=0;i<list.size();i++) {
            getQRNum(new QRData(list.get(i).getTourTitle()),i);

        }
        return view;
    }


    public void init(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private GridView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//            String qr_name = AppManager.getInstance().getTourList().get(i).getTourTitle();
//            getQRNum(new QRData(qr_name),i);
//
//            Log.i("모은 qr_num", Double.toString(AppManager.getInstance().getTourList().get(i).getTodayNumber()));

            Object vo = (Object)adapterView.getAdapter().getItem(i);
            String tourTitle = null;
            String predictionNumber = null;
            String todayNumber = null;
            String distance = null;


            for (Field field : vo.getClass().getDeclaredFields()) {

                field.setAccessible(true);
                Object value = null; // 필드에 해당하는 값을 가져옵니다.
                try {
                    value = field.get(vo);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(String.valueOf(field.getName()).equals("tourTitle"))
                {
                    tourTitle = String.valueOf(value);
                }
                if(String.valueOf(field.getName()).equals("todayNumber")){
                    todayNumber = String.valueOf(value);
                }
                if(String.valueOf(field.getName()).equals("pridictionNumber")){
                    predictionNumber = String.valueOf(value);
                }
                if(String.valueOf(field.getName()).equals("distance")){
                    distance = String.valueOf(value);
                }
            }

            long currentClickTime= SystemClock.uptimeMillis();
            long elapsedTime=currentClickTime-mLastClickTime;
            mLastClickTime=currentClickTime;



            // 중복 클릭인 경우
            if(elapsedTime<=MIN_CLICK_INTERVAL){
//                 아이템 클릭시 상세 페이지로 넘어감 > 더블 클릭시
                Intent intent = new Intent(getContext(), DetailPopUpActivity.class);
                intent.putExtra("key", tourTitle);
                intent.putExtra("todayNumber", todayNumber);
                intent.putExtra("predictionNumber", predictionNumber);
                intent.putExtra("distance", distance);

                startActivity(intent);
                return;
            }

            if (AppManager.getInstance().getTourList().get(i).getIsClick()) {
                Log.i("모은 isclick :  true  ", AppManager.getInstance().getTourList().get(i).getTourTitle());
                Log.i("모은 isCollected", Boolean.toString(AppManager.getInstance().getTourList().get(i).isCollected()));
                LinearLayout layout = view.findViewById(R.id.stamp_linear_layout);

                if ( AppManager.getInstance().getTourList().get(i).isCollected()) {
                    layout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                }
                else if (Double.parseDouble(predictionNumber) >= 2000) {
                    layout.setBackgroundColor(Color.parseColor("#c82f3d"));
                }else if (Double.parseDouble(predictionNumber) < 2000 && Double.parseDouble(predictionNumber) > 500) {
                    layout.setBackgroundColor(Color.parseColor("#f0e68c"));
                } else {
                    layout.setBackgroundColor(Color.parseColor("#badefb"));
                }
                layout.setVisibility(View.VISIBLE);
                Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_layout);
                layout.startAnimation(slideUpAnimation);


//                TextView qr_num = view.findViewById(R.id.todayNumber);
//                qr_num.setText("현재까지 관광객 약 :" + String.valueOf(AppManager.getInstance().getTourList().get(i).getTodayNumber()) + "명");
                AppManager.getInstance().getTourList().get(i).setIsClick(false);
                Log.i("모은 isclick :  true?  ", Boolean.toString(AppManager.getInstance().getTourList().get(i).getIsClick()));
//                isClick = false;

            } else {
                Log.i("모은 isclick :  false", AppManager.getInstance().getTourList().get(i).getTourTitle());
                ((LinearLayout) view.findViewById(R.id.stamp_linear_layout)).setVisibility(View.GONE);
                Animation slideDownAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down_layout);
                ((LinearLayout) view.findViewById(R.id.stamp_linear_layout)).startAnimation(slideDownAnimation);
                AppManager.getInstance().getTourList().get(i).setIsClick(true);

//                isClick = true;
            }


        }


    };



    protected int getQRNum(QRData data, int i) {

        final int[] qr_num = new int[1];
        service.qrNum(data).enqueue(new Callback<QRResponse>() {
            @Override
            public void onResponse(Call<QRResponse> call, Response<QRResponse> response) {
                QRResponse result = response.body();


                Log.i("qr코드 num 값 가져옴(message)",result.getMessage() );
                Log.i("qr코드 num 값 가져옴(qr_num)",result.getQRNum() );

                //Log.i("qr코드 확인",result.getQRName());

                qr_num[0] = Integer.parseInt(result.getQRNum());
                AppManager.getInstance().getTourList().get(i).setTodayNumber(qr_num[0]);
                Log.i("qr코드 (getTodayNumber)", Double.toString(AppManager.getInstance().getTourList().get(i).getTodayNumber() ));
                stampAdapter.updateAdpater(AppManager.getInstance().getTourList());


            }


            @Override
            public void onFailure(Call<QRResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("qr_num 가져오기 에러 발생", t.getMessage());

            }
        });
        return qr_num[0];
    }

}