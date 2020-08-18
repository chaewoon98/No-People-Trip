package com.test.mosun.map;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.mosun.R;
import androidx.fragment.app.Fragment;

import net.daum.mf.map.api.MapView;

public class Fragment_Map extends Fragment {

    MapView mapView;
    ViewGroup mapViewContainer;

    public Fragment_Map(){

    }

    public static Fragment_Map newInstance() {
        Fragment_Map fragment = new Fragment_Map();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //Toast.makeText(getContext(), "지도를 탭하면 전체화면으로 볼 수 있습니다.", Toast.LENGTH_LONG).show();


        mapView = new MapView(getActivity());
        //ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);

        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        mapViewContainer.addView(mapView);



        return view;
    }



}

