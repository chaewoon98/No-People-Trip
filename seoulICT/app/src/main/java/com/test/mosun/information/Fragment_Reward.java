package com.test.mosun.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.qrcode.CreateQR;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;


public class Fragment_Reward extends Fragment {

    ProgressBar progressBar;

    public Fragment_Reward(){

    }

    public static Fragment_Reward newInstance() {
        Fragment_Reward fragment = new Fragment_Reward();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_reward, container, false);

        TextView nameText = (TextView)view.findViewById(R.id.name_text);
        String userName = AppManager.getInstance().getUserName();
        nameText.setText("'"+userName+"' 님 ");

        TextView levelText = (TextView)view.findViewById(R.id.level_text);
        int AllCount = AppManager.getInstance().stampCount+ AppManager.getInstance().maskCount;
        if(AllCount <10)
            levelText.setText("Level.1");
        else if(AllCount >=10 && AllCount <20)
            levelText.setText("Level.2");
        else
            levelText.setText("Level.3");


        TextView levelDescriptionText = (TextView)view.findViewById(R.id.level_description_text);
        int leftStamp = 0;
        String level = (String) levelText.getText();
        switch (level)
        {
            case "Level.1":
                leftStamp = 10-AllCount;
                levelDescriptionText.setText("다음 레벨까지 " +leftStamp +" 스탬프 남았어요!");
                break;
            case "Level.2":
                leftStamp = 20-AllCount;
                levelDescriptionText.setText("다음 레벨까지 " +leftStamp +" 스탬프 남았어요!");
            case "Level.3":
                leftStamp = 30-AllCount;
                levelDescriptionText.setText("보상까지 " +leftStamp +" 남았어요!");
                break;
        }

        //프로그래스바 채우기
        progressBar = view.findViewById(R.id.total_progress_bar);
        progressBar.setProgress(AllCount);//관광지에 따른 스탬프 카운트로 바꾸기 > 새로고침 할 수 있게 바꾸기

        //버튼 클릭 이벤트 생성
        Button total_stamp_button = view.findViewById(R.id.total_stamp_button);
        total_stamp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_MyStamp.newInstance());
            }
        });

//        0811 추가

        Button mask_stamp_button = view.findViewById(R.id.mask_stamp_button);

        mask_stamp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Dialog fragment_dialog = new Fragment_Dialog();
                fragment_dialog.show(getActivity().getSupportFragmentManager(),"MyFragment");
            }
        });
        // 여기까지



        Button logout_button = view.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //sharedPreference 삭제
                ((MainActivity)getActivity()).removeKey("user_id");
                ((MainActivity)getActivity()).removeKey("userSns");

                //서버에 데이터 저장




                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.putExtra("logout",AppManager.getInstance().getuserSns());
                startActivity(intent);
                getActivity().finish();
            }
        });

        Button withdraw_button = view.findViewById(R.id.withdraw_button);
        withdraw_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WithdrawalPopUpActivity.class);
                startActivity(intent);


            }
        });

        return view;
    }


}
