package com.test.mosun.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.utility.OnSingleClickListener;
import com.test.mosun.R;

public class CoursePopupActivity extends AppCompatActivity {
    Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_popup);

        //닫힘 버튼 이벤트
        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                AppManager.getInstance().setQRPopUpActivity(null);  // 액티비티 닫음
                finish();
            }
        });

        //checkin, scanner 선언
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.setVisibility(View.VISIBLE);
        FrameLayout selectCourse = findViewById(R.id.selectCourse);
        FrameLayout recommenedCourse = findViewById(R.id.recommenedCourse);

        //selectCourse 클릭 이벤트
        selectCourse.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
                Intent intent = new Intent(thisActivity, MainActivity.class);
                startActivity(intent);

            }
        });

        //recommenedCourse 클릭 이벤트
        recommenedCourse.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
//                Intent intent = new Intent(thisActivity, ScanQR.class);
//                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        AppManager.getInstance().setQRPopUpActivity(null);  // 액티비티 닫음
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

}