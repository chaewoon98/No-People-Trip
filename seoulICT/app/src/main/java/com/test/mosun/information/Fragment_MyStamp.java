package com.test.mosun.information;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.information.my_stamp.MyStampAdapter;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;

public class Fragment_MyStamp extends Fragment {
    GridView gridView;
    MyStampAdapter myStampAdapter;
    ArrayList<TourList> tourList;
    ArrayList<TourList> stampList;

    public static Fragment_MyStamp newInstance() {
        Fragment_MyStamp fragment = new Fragment_MyStamp();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tourList = AppManager.getInstance().getTourList();
        stampList = new ArrayList<TourList>();  //스탬프 리스트 초기화

        for(TourList item : tourList) {
            if (item.isCollected())
            {
                //전체 관광지 리스트중에서 스탬프 찍은 곳만 스탬프 리스트에 저장
                stampList.add(item);
                Log.d("###", "스탬프 리스트에 추가");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_stamp, container, false);

        gridView = view.findViewById(R.id.gridview_my_stamp);

        if(stampList !=null){
            myStampAdapter = new MyStampAdapter(getContext(), stampList);
            gridView.setAdapter(myStampAdapter);
        }

        Button back_button = view.findViewById(R.id.back_button2);
        back_button.setOnClickListener(new View.OnClickListener() {  //뒤로가기 버튼 누르면 마이페이지(이전 페이지)로 돌아간다.

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_Reward.newInstance());
            }
        });

        return view;
    }
}
