package com.test.mosun.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.AppManager;
import com.test.mosun.R;
import com.test.mosun.camera.CameraActivity;
import com.test.mosun.utility.OnSingleClickListener;

public class MaskPopupActivity extends AppCompatActivity {
    Activity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_mask_popup);

        //닫힘 버튼 이벤트
        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                AppManager.getInstance().setQRPopUpActivity(null);  // 액티비티 닫음
                finish();
            }
        });

        //checkin, scanner 선언
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.setVisibility(View.VISIBLE);
        FrameLayout QRCheckin = findViewById(R.id.mask_chekin);

        //qrcheckin 클릭 이벤트
        QRCheckin.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(thisActivity, CameraActivity.class);
                startActivity(intent);
                AppManager.getInstance().setQRPopUpActivity(null);  // 액티비티 닫음
                finish();
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