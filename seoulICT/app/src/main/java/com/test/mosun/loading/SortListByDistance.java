package com.test.mosun.loading;

import com.test.mosun.stamp.TourList;

import java.util.Comparator;

public class SortListByDistance implements Comparator<TourList> {
    @Override
    public int compare(TourList a, TourList b){
        return Double.compare(a.getDistance(), b.getDistance());
    }
}
