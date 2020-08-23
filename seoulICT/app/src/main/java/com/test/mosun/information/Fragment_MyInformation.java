package com.test.mosun.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.mosun.MainActivity;
import com.test.mosun.R;

public class Fragment_MyInformation extends Fragment {
    public Fragment_MyInformation(){
//        LoginData loginData = new LoginData();

    }

    public static Fragment_MyInformation newInstance() {
        Fragment_MyInformation fragment = new Fragment_MyInformation();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_information, container, false);

        TextView name_text = view.findViewById(R.id.textView3);
        TextView id_text = view.findViewById(R.id.textView4);
        TextView email_text = view.findViewById(R.id.textView5);
        TextView birth_text = view.findViewById(R.id.textView7);

        Button back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {  //뒤로가기 버튼 누르면 마이페이지(이전 페이지)로 돌아간다.

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).replaceFragment(Fragment_Reward.newInstance());
            }
        });

        Button modify_button = view.findViewById(R.id.modify_button);
        modify_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        //마이페이지의 '내 정보'에 유저정보 입력
//        name_text.setText();
//        id_text.setText();
//        email_text.setText();
//        birth_text.setText();

        return view;
    }
}
