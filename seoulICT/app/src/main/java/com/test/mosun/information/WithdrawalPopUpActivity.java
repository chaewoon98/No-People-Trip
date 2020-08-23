package com.test.mosun.information;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.mosun.AppManager;
import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.camera.CameraActivity;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.utility.OnSingleClickListener;

public class WithdrawalPopUpActivity extends AppCompatActivity {
    Activity thisActivity = this;
    MainActivity mainActivity = (MainActivity)MainActivity.mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 타이틀바 없애기
        setContentView(R.layout.activity_withdrawal_popup);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        mainLayout.setVisibility(View.VISIBLE);

        //닫힘 버튼 이벤트
        ImageView closeBtn = findViewById(R.id.close);
        closeBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                AppManager.getInstance().setQRPopUpActivity(null);  // 액티비티 닫음
                finish();
            }
        });


        Button button_yes = findViewById(R.id.button_yes);
        button_yes.setOnClickListener(new OnSingleClickListener(){

            @Override
            public void onSingleClick(View v) {
                //마지막 기록 서버에 보내기


                //sharedpreference삭제
                onclearData();

                //연동 해제//앱 로그인 화면으로
                finish();

                Intent intent = new Intent(WithdrawalPopUpActivity.this, LoginActivity.class);
                intent.putExtra("withdrawal","naver");
                startActivity(intent);
                mainActivity.finish();


                //서버 기록은 15일 후에 삭제된다는 것을 공지
                Toast.makeText(thisActivity, "사용자 기록은 15일 후에 자동으로 삭제됩니다.", Toast.LENGTH_SHORT).show();


            }
        });

        Button button_no = findViewById(R.id.button_no);
        button_no.setOnClickListener(new OnSingleClickListener(){

            @Override
            public void onSingleClick(View v) {
                finish();
                Toast.makeText(thisActivity, "탈퇴를 취소합니다", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {

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
    /**
     * 모든 저장 데이터 삭제
     */
    public void onclearData() {

        Log.i("모은","데이터 삭제 완료");
        SharedPreferences sp = getSharedPreferences("NPT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

    }
}
