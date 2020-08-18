package com.test.mosun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.mosun.home.Fragment_Home;
import com.test.mosun.information.Fragment_Reward;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.map.Fragment_GoogleMap;
import com.test.mosun.qrcode.QRPopupActivity;
import com.test.mosun.stamp.Fragment_Stamp;
import com.test.mosun.stamp.TourList;
import com.test.mosun.utility.CurveBottomBar;
import com.test.mosun.utility.PermissionCheck;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Fragment_Home fragment_Home= Fragment_Home.newInstance();
    private Fragment_GoogleMap fragment_Map= Fragment_GoogleMap.newInstance();
    private Fragment_Stamp fragment_Stamp= Fragment_Stamp.newInstance();
    private Fragment_Reward fragment_Reward= Fragment_Reward.newInstance();

    private CurveBottomBar curveBottomBar;
    FloatingActionButton floatingActionButton;
    BackPressCloseHandler backPressCloseHandler;
    private PermissionCheck permission;

    private LoginActivity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressCloseHandler = new BackPressCloseHandler(this);
        setContentView(R.layout.activity_main);

        //loginActivity.finish();

        //상태 바 색 바꿔줌
        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.parseColor("#FAFAFA"));
        setContentView(R.layout.activity_main);
        AppManager.getInstance().setMainActivity(this);

        // 초기 프래그먼트 설정
        fragment_Home = Fragment_Home.newInstance();
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment_Home).commit();


        // 하단 메뉴 설정
        floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(floatingButtonClick);
        AppManager.getInstance().setMenuFloatingActionButton(floatingActionButton);

        curveBottomBar = findViewById(R.id.customBottomBar);
        curveBottomBar.inflateMenu(R.menu.navigation);
        curveBottomBar.setOnNavigationItemSelectedListener(new ItemSelectedListener());



        //permissionCheck();
    }


    private void permissionCheck(){
        if(Build.VERSION.SDK_INT>=23){
            permission = new PermissionCheck(this,this);

            if(!permission.checkPermission()){
                permission.requestPermission();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]grantResults){
        boolean isQuit = false;
        if(!permission.permissionResult(requestCode, permissions, grantResults)){

            for (String pm : permissions) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this, pm)) { // 거부된 적이 있으면 해당 권한을 사용할 때 상세 내용을 설명. 거부한 적 있으면 true 리턴.
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"" + pm + "\"권한이 필요합니다. 계속 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        permission.requestPermission();

                                    }
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Log.i("모은","기능 취소");
                                    try {
                                        toastMessage();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }).create().show();
                }

            }



            //permission.requestPermission();
        }
    }

    private void toastMessage() throws InterruptedException {
        Toast.makeText(this, "권한설정을 취소합니다.\n설정에 가셔서 권한을 설정 후 앱을 실행시켜주세요", Toast.LENGTH_LONG).show();
        Thread.sleep(500);
        finish();

    }

    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();

    }

    @Override
    protected void onStop() {
        // 강제 종료시 onDestroy() 호출이 안됨
        // 서버에 저장하는 함수 만들기
        Log.d("테스트", "mainActivity onStop");
        saveData();
        super.onStop();
    }
    private void saveData()
    {
        Log.i("모은 saveData","saveData- MainActivity");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();

        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for(TourList item : list)
        {
            editor.putString(item.getTourTitle(),Boolean.toString(item.isCollected()));
            editor.commit();
        }




    }

    // 하단 메뉴 선택 리스너
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            transaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {

                case R.id.home:
                    replaceFragment(fragment_Home);
                    break;

                case R.id.map:
                    replaceFragment(fragment_Map);
                    break;

                case R.id.stamp_book:
                    replaceFragment(fragment_Stamp);
                    break;

                case R.id.reward:
                    replaceFragment(fragment_Reward);
            }
            return true;
        }
    }

    private View.OnClickListener floatingButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, QRPopupActivity.class);
            startActivity(intent);

        }
    };

    //fragment 전환하는 메소드
    public void replaceFragment(Fragment fragment){
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment).commit();
    }

    public void replaceAndGetBundleFragment(Fragment fragment, Fragment bundleFragment)
    {

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment).commit();  // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.

    }
}

class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;
    private LoginActivity loginActivity;
    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }

}