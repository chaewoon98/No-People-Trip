package com.test.mosun.home.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.test.mosun.R;

public class page_2 extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout=(LinearLayout)inflater.inflate(R.layout.view_pager,container,false);
        ConstraintLayout background=(ConstraintLayout)linearLayout.findViewById(R.id.background2);

        return linearLayout;
    }
}
