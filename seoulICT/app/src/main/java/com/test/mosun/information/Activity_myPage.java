package com.test.mosun.information;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.R;

public class Activity_myPage extends AppCompatActivity {
//    public Activity_myPage(){
//
//        onCreate
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.fragment_my_information);
    }


//    public static Activity_myPage newInstance() {
//        Activity_myPage fragment = new Activity_myPage();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public View onCreateView(){
//
//    }
}
