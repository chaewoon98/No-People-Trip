
package com.test.mosun.stamp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.test.mosun.AppManager;
import com.test.mosun.R;
import com.test.mosun.data.QRData;
import com.test.mosun.data.QRResponse;
import com.test.mosun.loading.SortListByDistance;
import com.test.mosun.loading.SortListByPredictNumber;
import com.test.mosun.network.RetrofitClient;
import com.test.mosun.network.ServiceApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import jp.wasabeef.glide.transformations.ColorFilterTransformation;



public class StampAdapter extends BaseAdapter{
    Context context;
    int layout;
    List<TourList> list;  // item 목록
    List<TourList> potionList = null;
    ArrayList<TourList> arrayList;
    LayoutInflater layoutInflater;
    Congestion congestion = new Congestion();
    String congestionColor;

    TourList item;     // item 정보 객체

    String distanceText = "0";
    private ServiceApi service = RetrofitClient.getClient().create(ServiceApi .class);

    public StampAdapter(Context context, int layout, List<TourList> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.arrayList = new ArrayList<TourList>();
        this.arrayList.addAll(list);
        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateAdpater(List<TourList> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = layoutInflater.inflate(layout, null);

        item = list.get(i);


        //서버
//        String qr_name = AppManager.getInstance().getTourList().get(i).getTourTitle();
//        item.setTodayNumber(getQRNum(new QRData(qr_name)));

        Log.i("모은 todayNumber", Double.toString(item.getTodayNumber()));



        TextView distance = view.findViewById(R.id.distance); // 수정 0803



        TextView landmarkNameView = view.findViewById(R.id.landmark_name);
        // 예상 관광객 수, 오늘 관광객 수, 이미지 추가
        TextView pridictNumber = view.findViewById(R.id.predictionNumber);
        TextView todayNumber = view.findViewById(R.id.todayNumber);
        ImageView image = view.findViewById(R.id.image_thumb);

        image.setImageResource(item.getImageNumericalValueID());
        float pn = item.getPridictionNumber();
        String predictNumber = String.format("%.2f", pn);

        pn = item.getTodayNumber();
        String numberToday = String.format("%.2f", pn);
        pridictNumber.setText("예상 관광객 약 : " + predictNumber + "명");
        todayNumber.setText("현재 관광객 약 :" + numberToday + "명");
        landmarkNameView.setText(item.getTourTitle());
        //distance.setText("관광지까지의 거리 :"+String.valueOf(item.getDistance())); // 수정 0803

        double predictionNumber = item.getPridictionNumber();

        LinearLayout layout = view.findViewById(R.id.square_linear_layout);
        ((LinearLayout)view.findViewById(R.id.stamp_linear_layout)).setVisibility(View.GONE);

        if ( item.isCollected()) {
            layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_iscollected));
        } else {

            congestionColor = congestion.congestAnalysis(item.getTourTitle(), item.getPridictionNumber());

            if(congestionColor.equals("red")){
                layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_bad));
            } else if(congestionColor.equals("yellow")){
                layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_soso));
            } else{
                layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_good));
            }


        }
        /*else if (predictionNumber >= 2000) {
            layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_bad));
        } else if (predictionNumber < 2000 && predictionNumber > 500) {
            layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_soso));
        } else {
            layout.setBackground(ContextCompat.getDrawable(context,R.drawable.rectangle_good));
        }*/

//        TextView landmarkName = (TextView)view.findViewById(R.id.landmark_name);
//        ((LinearLayout)view.findViewById(R.id.stamp_linear_layout)).setVisibility(View.GONE);
//        if ( item.isCollected()) {
//            landmarkName.setBackgroundColor(Color.parseColor("#DEDEDE"));
//        }
//        if (predictionNumber >= 2000) {
//            landmarkName.setBackgroundColor(Color.parseColor("#c82f3d"));
//        }else if (predictionNumber < 2000 && predictionNumber > 500) {
//            landmarkName.setBackgroundColor(Color.parseColor("#f0e68c"));
//        } else {
//            landmarkName.setBackgroundColor(Color.parseColor("#67c9d2"));
//        }


        //현재위치에서 관광지까지 거리 출력
        if(item.getDistance()>1000) {
            distanceText = String.format("%.2f", item.getDistance()/1000);
            distance.setText("관광지까지의 거리 :"+distanceText + "km");
        }else{
            distanceText = String.format("%.2f", item.getDistance());
            distance.setText("관광지까지의 거리 :"+distanceText + "m");
        }

        //gps,predit_image

        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        Log.d("박태순","들어옴");
        Log.d("검색", String.valueOf(charText.length()));
        if (charText.length() == 0) {
            list.addAll(AppManager.getInstance().getTourList());
            filterPrediction();
            Log.d("검색1", String.valueOf(charText.length()));
        } else {
            list.clear();
            Log.d("검색2", String.valueOf(charText.length()));
            for (TourList st : arrayList) {
                Log.d("박태순",st.getTourTitle());
                if (st.getTourTitle().toLowerCase().contains(charText)) {
                    list.add(st);
                }
            }
        }

        notifyDataSetChanged();
    }

    public void filterDistance(){
        Collections.sort(list, new SortListByDistance());
        notifyDataSetChanged();
    }

    public void filterPrediction(){
        Collections.sort(list, new SortListByPredictNumber());
        notifyDataSetChanged();
    }

    protected void getQRNum(QRData data) {

        final int[] qr_num = new int[1];
        service.qrNum(data).enqueue(new Callback<QRResponse>() {
            @Override
            public void onResponse(Call<QRResponse> call, Response<QRResponse> response) {
                QRResponse result = response.body();


                Log.i("qr코드 num 값 가져옴",result.getMessage() );
                Log.i("qr코드 num 값 가져옴",result.getQRNum() );

                //Log.i("qr코드 확인",result.getQRName());

                qr_num[0] = Integer.parseInt(result.getQRNum());
                //스탬프를 오늘 찍었는지 확인

            }


            @Override
            public void onFailure(Call<QRResponse> call, Throwable t) {
                //Toast.makeText(LoginActivity.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("qr_num 가져오기 에러 발생", t.getMessage());

            }
        });

    }

}