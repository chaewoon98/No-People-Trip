package com.test.mosun.information.my_stamp;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.AppManager;
import com.test.mosun.R;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;

public class MyStampGridView extends AppCompatActivity {

    GridView gridView;
    MyStampAdapter myStampAdapter;
    ArrayList<TourList> tourList = AppManager.getInstance().getTourList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_my_stamp_grid);
        setTitle("My Stamp");

        gridView = (GridView) findViewById(R.id.gridview_my_stamp);
        myStampAdapter = new MyStampAdapter(this, tourList);
        gridView.setAdapter(myStampAdapter);
    }
}