package com.test.mosun.information.my_stamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.mosun.R;
import com.test.mosun.stamp.TourList;

public class MyStampGridItem extends LinearLayout {
    ImageView thumb_image;
    TextView tour_title;
    GridView gridView;
    TextView scan_time;


    public MyStampGridItem(Context context) {

        super(context);
        init(context);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_stamp_grid, this);
        tour_title = (TextView)findViewById(R.id.tour_title);
        thumb_image = (ImageView)findViewById(R.id.thumb_image);
        scan_time = (TextView)findViewById(R.id.scan_time);
    }

    public void setData(TourList tourList){
        tour_title.setText(tourList.getTourTitle());
        thumb_image.setImageResource(tourList.getImageNumericalValueID());
        scan_time.setText(tourList.getScanTime());
    }
}
