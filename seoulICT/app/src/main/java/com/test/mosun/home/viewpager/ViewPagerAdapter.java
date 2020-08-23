package com.test.mosun.home.viewpager;

import android.util.Log;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.test.mosun.AppManager;
import com.test.mosun.loading.SortListByPredictNumber;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private LayoutInflater mInflater;
    //    Context mContex
    int MAX_PAGE = 3;
    Fragment cur_fragment = new Fragment();
    Fragment[] fragments = new Fragment[3];

    ArrayList<TourList> tourList;
    int[] random = null;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        tourList = AppManager.getInstance().getTourList();
        filterPrediction();

        if(random == null) {
            this.random = randomNumber();
            Log.d("random#", String.valueOf(random[0]));
        }


    }

    @Override
    public int getCount() {
        return MAX_PAGE;

    }



    public Fragment getItem(int position) {
        if (position < 0 || MAX_PAGE <= position)
            return null;

        switch (position) {
            case 0:
//                cur_fragment = new page_1(tourList, random[0]);
                cur_fragment = new page_1(tourList, random[0]);
                break;
            case 1:
                cur_fragment = new page_2(tourList, random[1]);  //page_2
                break;
            case 2:
                cur_fragment = new page_3(tourList, random[2]);  //page_3
                break;
        }
        return cur_fragment;
    }



    public void filterPrediction(){
        Collections.sort(tourList, new SortListByPredictNumber());
    }

    public int[] randomNumber(){
        int[] random = new int[3];
        Random r = new Random();


        for(int i=0;i<3;i++){
            random[i] = (int)(Math.random()*10) % 5;

            for(int j=0;j<i;j++){
                if(random[i] == random[j]){
                    i--;
                }
            }
        }

        return random;
    }
}
