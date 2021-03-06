package com.test.mosun.stamp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.test.mosun.utility.CustomEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    InputMethodManager inputMethodManager;
    ProgressBar progressBar;
    String searchword;

    GpsInfo gpsInfo;
    Location currentLocation;
    double distance;
    private static String selectedArea = "??????";
    private ServiceApi service;
    static CustomEditText editTextFilter;
    String congestionColor;
    Congestion congestion = new Congestion();

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

        searchList = new ArrayList<>();
        searchList.addAll(tourList);
        mainActivity = (MainActivity) getActivity();
        searchword = "";

        //?????? ??? ??????


        //?????????????????? ??????????????? ?????? ??????
        gpsInfo = new GpsInfo(getContext());
        currentLocation = gpsInfo.getLocation();
        Log.i("??????",currentLocation.toString());
        for(TourList item : searchList){
            Log.i("??????",item.getLocationB().toString());
            distance = currentLocation.distanceTo(item.getLocationB());
            item.setDistance(distance);
        }

//        ArrayList<TourList> list = AppManager.getInstance().getTourList();
//        for(int i=0;i<list.size();i++) {
//            getQRNum(new QRData(list.get(i).getTourTitle()),i);
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stamp, container, false);

        Log.d("onCreateView", "stamp ??????");

        stampGridView = view.findViewById(R.id.gridview_stamp);
        stampAdapter = new StampAdapter(getContext(), R.layout.item_stamp, searchList);
        stampGridView.setAdapter(stampAdapter); // ???????????? ????????? ?????? ??????
        stampGridView.setOnItemClickListener(itemClickListener);



        TextView sortByDistance = view.findViewById(R.id.sort_location);
        TextView sortByPredictionNumber = view.findViewById(R.id.sort_predit);

        sortByDistance.setOnClickListener(v -> stampAdapter.filterDistance());
        sortByPredictionNumber.setOnClickListener(v -> stampAdapter.filterPrediction());

        editTextFilter = (CustomEditText) view.findViewById(R.id.search_edit_text);
        editTextFilter.requestFocus();






        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        editTextFilter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.i("?????? keyevent",keyEvent.toString());
                if (i == EditorInfo.IME_ACTION_SEARCH ) {
                    inputMethodManager.hideSoftInputFromWindow(editTextFilter.getWindowToken(), 0);
                    editTextFilter.setShowSoftInputOnFocus(true);
                    //((MainActivity)getActivity()).showCurveBottomBar();//??? ????????? ?????? ????????????
                }


                return false;
            }
        });

        editTextFilter.setOnClickListener(new View.OnClickListener() {  //editTextFilter click event
            @Override
            public void onClick(View v) {

                Log.i("?????? ","??????????????? ?????? ?????????");
                //((MainActivity)getActivity()).hideCurveBottomBar();//??? ????????? ?????? ????????????
                editTextFilter.setShowSoftInputOnFocus(true);

            }
        });
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



        editTextFilter.setOnBackPressListener(onBackPressListener);







        //???????????? ?????? ???
        ImageButton back_btn = (ImageButton)view.findViewById(R.id.back_btn2);
        back_btn.setOnClickListener(new View.OnClickListener() {  //???????????? ?????? ????????? ???????????????(?????? ?????????)??? ????????????.

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_Home.newInstance());
            }
        });




        //qr_num ????????????
        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        for(int i=0;i<list.size();i++) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if(list.get(i).isCollected())
                getQRNum(new QRData(list.get(i).getTourTitle()),i);

        }
        return view;
    }


    private CustomEditText.OnBackPressListener onBackPressListener = new CustomEditText.OnBackPressListener()
    {
        @Override
        public void onBackPress()
        {

            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(() ->  inputMethodManager.hideSoftInputFromWindow(editTextFilter.getWindowToken(), 0));
            //es.submit(() ->   ((MainActivity)getActivity()).showCurveBottomBar());//??? ????????? ?????? ????????????
            es.submit(() ->  Log.i("?????? ","back button click  im"));
            es.shutdown();
            return;



        }
    };


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


            Object vo = (Object)adapterView.getAdapter().getItem(i);
            String tourTitle = null;
            String predictionNumber = null;
            String todayNumber = null;
            String distance = null;


            for (Field field : vo.getClass().getDeclaredFields()) {

                field.setAccessible(true);
                Object value = null; // ????????? ???????????? ?????? ???????????????.
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



            // ?????? ????????? ??????
            if(elapsedTime<=MIN_CLICK_INTERVAL){
//                 ????????? ????????? ?????? ???????????? ????????? > ?????? ?????????
                Intent intent = new Intent(getContext(), DetailPopUpActivity.class);
                intent.putExtra("key", tourTitle);
                intent.putExtra("todayNumber", todayNumber);
                intent.putExtra("predictionNumber", predictionNumber);
                intent.putExtra("distance", distance);

                startActivity(intent);
                return;
            }

            if (AppManager.getInstance().getTourList().get(i).getIsClick()) {
                Log.i("?????? isclick :  true  ", AppManager.getInstance().getTourList().get(i).getTourTitle());
                Log.i("?????? isCollected", Boolean.toString(AppManager.getInstance().getTourList().get(i).isCollected()));
                LinearLayout layout = view.findViewById(R.id.stamp_linear_layout);

                // ????????? ?????? ????????? ?????? ??????
                if ( AppManager.getInstance().getTourList().get(i).isCollected()) {
                    layout.setBackgroundColor(Color.parseColor("#DEDEDE"));
                } else{
                    congestionColor = congestion.congestAnalysis(AppManager.getInstance().getTourList().get(i).getTourTitle(), Float.parseFloat(predictionNumber));

                    Log.d("?????? ??????", String.valueOf(congestionColor));

                    if(congestionColor.equals("red")){
                        layout.setBackgroundColor(Color.parseColor("#c82f3d"));
                    } else if(congestionColor.equals("yellow")){
                        layout.setBackgroundColor(Color.parseColor("#f0e68c"));
                    } else{
                        layout.setBackgroundColor(Color.parseColor("#badefb"));
                    }

                }


                /*else if (Double.parseDouble(predictionNumber) >= 2000) {
                    layout.setBackgroundColor(Color.parseColor("#c82f3d"));
                }else if (Double.parseDouble(predictionNumber) < 2000 && Double.parseDouble(predictionNumber) > 500) {
                    layout.setBackgroundColor(Color.parseColor("#f0e68c"));
                } else {
                    layout.setBackgroundColor(Color.parseColor("#badefb"));
                }*/
                layout.setVisibility(View.VISIBLE);
                Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_layout);
                layout.startAnimation(slideUpAnimation);


//                TextView qr_num = view.findViewById(R.id.todayNumber);
//                qr_num.setText("???????????? ????????? ??? :" + String.valueOf(AppManager.getInstance().getTourList().get(i).getTodayNumber()) + "???");
                AppManager.getInstance().getTourList().get(i).setIsClick(false);
                Log.i("?????? isclick :  true?  ", Boolean.toString(AppManager.getInstance().getTourList().get(i).getIsClick()));
//                isClick = false;

            } else {
                Log.i("?????? isclick :  false", AppManager.getInstance().getTourList().get(i).getTourTitle());
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


                Log.i("qr?????? num ??? ?????????(message)",result.getMessage() );
                Log.i("qr?????? num ??? ?????????(qr_num)",result.getQRNum() );

                //Log.i("qr?????? ??????",result.getQRName());

                qr_num[0] = Integer.parseInt(result.getQRNum());
                AppManager.getInstance().getTourList().get(i).setTodayNumber(qr_num[0]);
                Log.i("qr?????? (getTodayNumber)", Double.toString(AppManager.getInstance().getTourList().get(i).getTodayNumber() ));
                stampAdapter.notifyDataSetChanged();
                //stampAdapter.updateAdpater(AppManager.getInstance().getTourList());


            }


            @Override
            public void onFailure(Call<QRResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                Log.e("qr_num ???????????? ?????? ??????", t.getMessage());

            }
        });
        return qr_num[0];
    }



}




