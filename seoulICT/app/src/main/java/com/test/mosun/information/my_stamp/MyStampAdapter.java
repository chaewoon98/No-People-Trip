package com.test.mosun.information.my_stamp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test.mosun.stamp.TourList;

import java.util.ArrayList;

public class MyStampAdapter extends BaseAdapter {
    Context context;
    ArrayList<TourList> tourList = new ArrayList<TourList>();

    public MyStampAdapter(Context context, ArrayList<TourList> tourList){
        this.context = context;
        this.tourList = tourList;
    }

    @Override
    public int getCount() {
        return tourList.size();
    }

    @Override
    public Object getItem(int position) {
        return tourList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new MyStampGridItem(context);
        }
        ((MyStampGridItem)convertView).setData(tourList.get(position));
        return convertView;
    }
    public void updateAdpater(ArrayList<TourList> list) {
        this.tourList = list;
        this.notifyDataSetChanged();
    }
}
