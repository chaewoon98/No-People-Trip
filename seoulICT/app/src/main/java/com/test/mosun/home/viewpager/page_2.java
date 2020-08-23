package com.test.mosun.home.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.test.mosun.R;
import com.test.mosun.loading.SortListByPredictNumber;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;
import java.util.Collections;

public class page_2 extends Fragment {
    ArrayList<TourList> predictionList;
    int index;

    public page_2(ArrayList<TourList> tourList, int index){
        this.predictionList = tourList;
        this.index = index;

        filterPrediction();  //predictionList를 예상관광객수 순서로 sort
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        predictionList = AppManager.getInstance().getTourList();
//        filterPrediction();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TourList tour = new TourList();
//        ArrayList<TourList> tourList = AppManager.getInstance().getTourList();

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.view_pager,container,false);
        ConstraintLayout background=(ConstraintLayout)linearLayout.findViewById(R.id.background2);

        ImageView imageView = (ImageView)linearLayout.findViewById(R.id.image_view);
        TextView titleText = (TextView)linearLayout.findViewById(R.id.title_text);
        TextView congestionText = (TextView)linearLayout.findViewById(R.id.congestion_text);

//        for(TourList item: tourList){
//            if(item.getTourTitle().equals("경복궁")){
//                tour = item;
//            }
//        }

        imageView.setImageResource(predictionList.get(index).getIcon());
        titleText.setText(predictionList.get(index).getTourTitle());
//        congestionText.setText("예상: " + String.format("%.2f", predictionList.get(index).getPridictionNumber()) + "명 / " + "현재: " + String.format("%.2f",predictionList.get(0).getTodayNumber()) + "명");
        if(Double.valueOf(predictionList.get(index).getPridictionNumber())>0)
        {
            congestionText.setText("예상: " + String.format("%.2f", predictionList.get(index).getPridictionNumber()) + "명 / " + "현재: " + String.format("%.2f",predictionList.get(0).getTodayNumber()) + "명");
        }
        else{
            congestionText.setText("코로나 위험!! 오늘은 비추!!");
        }
        return linearLayout;
    }

    public void filterPrediction(){
        Collections.sort(predictionList, new SortListByPredictNumber());
    }
}
