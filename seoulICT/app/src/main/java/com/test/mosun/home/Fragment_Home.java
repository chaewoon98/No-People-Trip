package com.test.mosun.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.test.mosun.MainActivity;
import com.test.mosun.R;
import com.test.mosun.camera.CameraActivity;
import com.test.mosun.home.viewpager.ViewPagerAdapter;
import com.test.mosun.stamp.Fragment_Stamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Home extends Fragment {

    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;

    List<areaItem> list;
    areaItem item;

    ViewPager viewPager;
    Timer timer;
    final int Max_Page = 3;  // 이미지의 총 갯수
    final long DELAY_MS = 3000;           // 오토 플립용 타이머 시작 후 해당 시간에 작동(초기 웨이팅 타임) ex) 앱 로딩 후 3초 뒤 플립됨.
    final long PERIOD_MS = 3000;          // 5초 주기로 작동
    int currentPage = 0;

    public Fragment_Home() {
    }

    public static Fragment_Home newInstance() {
        Fragment_Home fragment = new Fragment_Home();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("home","home");


        View view = inflater.inflate(R.layout.fragment_second_home, container, false);

        viewPager = (ViewPager)view.findViewById(R.id.viewpager);


        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager()));




        TextView todayDate = (TextView)view.findViewById(R.id.todayDate);
        String today = new SimpleDateFormat("MM월 dd일").format(new Date());
        todayDate.setText(today+" 기준");


        ImageView maskStampImageView = (ImageView)view.findViewById(R.id.home_mask_stamp);
        ImageView untactStampImageView = (ImageView)view.findViewById(R.id.home_untact_stamp);
        maskStampImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CameraActivity.class);
                startActivity(intent);
            }
        });
        untactStampImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).replaceFragment(Fragment_Stamp.newInstance());    // 새로 불러올 Fragment의 Instance를 Main으로 전달


            }
        });






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Adapter 세팅 후 타이머 실행
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                currentPage = viewPager.getCurrentItem();
                int nextPage = currentPage + 1;

                if (nextPage >= Max_Page) {
                    nextPage = 0;
                }
                viewPager.setCurrentItem(nextPage, true);
                currentPage = nextPage;
            }
        };

        timer = new Timer(); // thread에 작업용 thread 추가
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    public void onPause() {
        super.onPause();
        // 다른 액티비티나 프레그먼트 실행시 타이머 제거
        // 현재 페이지의 번호는 변수에 저장되어 있으니 취소해도 상관없음
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}


