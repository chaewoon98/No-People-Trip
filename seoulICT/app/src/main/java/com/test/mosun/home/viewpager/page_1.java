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

import com.test.mosun.AppManager;
import com.test.mosun.R;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;

public class page_1 extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<TourList> tourList = AppManager.getInstance().getTourList();

        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.view_pager,container,false);
        ConstraintLayout background=(ConstraintLayout)linearLayout.findViewById(R.id.background2);

        ImageView imageView = (ImageView)linearLayout.findViewById(R.id.image_view);
        TextView titleText = (TextView)linearLayout.findViewById(R.id.title_text);
        TextView congestionText = (TextView)linearLayout.findViewById(R.id.congestion_text);

        //tourList

        imageView.setImageResource(R.drawable.viewpager_icon2);

        //titleText.setText();

        return linearLayout;
    }
}
