package com.test.mosun;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    boolean isQuit = false;
    private LoginActivity loginActivity;
    private static Activity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressCloseHandler = new BackPressCloseHandler(this);
        setContentView(R.layout.activity_main);
        mainActivity= this;
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
        permissionCheck();
    }

        private void permissionCheck(){
        if(Build.VERSION.SDK_INT>=23){
            permission = new PermissionCheck(this,this);

            if(!permission.checkPermission()){
                permission.requestPermission();
            }
        }
    }


    public void setCurveBottomBarVisibility()
    {Log.i("모은 ","네비게이션 visibility");

        if (curveBottomBar.isShown()) {
            curveBottomBar.setVisibility(View.GONE);
            floatingActionButton.hide();
        } else {
            curveBottomBar.setVisibility(View.VISIBLE);
            floatingActionButton.show();
        }


    }

    public void hideCurveBottomBar()
    {
        Log.i("모은 ","네비게이션 hide");

            curveBottomBar.setVisibility(View.GONE);
        floatingActionButton.hide();
    }
    public void showCurveBottomBar()
    {
        Log.i("모은 ","네비게이션 show");

        curveBottomBar.setVisibility(View.VISIBLE);
        floatingActionButton.show();
    }



    //사용자에게 권한요청 요구를 위한 다이어로그를 생성
    public void CALLDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("앱 권한 설정");
        alertDialog.setMessage("해당 앱의 원할한 기능을 이용하시려면 애플리케이션 정보>권한> 에서 권한을 허용해 주십시오\n['카메라' 와 '위치' 권한 필수]");

        // 권한설정 클릭시 이벤트 발생
        alertDialog.setPositiveButton("권한설정",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                        startActivityForResult(intent,0);
                        //startActivity(intent);
                        dialog.cancel();
                    }
                });
        //취소
        alertDialog.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "권한설정이 되지 않아 앱을 종료합니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        alertDialog.show();
    }//end of CALLDialog(


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("모은","설정 갔다가 백");
        switch (requestCode){
            case 0:

                permissionCheck();
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]grantResults){
        Log.i("모은 ","onRequestPermissionsResult(mainActivity)");
        int result;
        if(!permission.permissionResult(requestCode, permissions, grantResults)){

            for(String pm : permissions)
            {
                result = ContextCompat.checkSelfPermission(this, pm);
                if(result == PackageManager.PERMISSION_GRANTED)
                    continue;
                else
                {
                    CALLDialog();
                    break;
                }
            }

        }
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
        editor.putString("stampCount",Integer.toString(AppManager.getInstance().getStampCount()));
        editor.commit();
        editor.putString("maskCount",Integer.toString(AppManager.getInstance().getMaskCount()));
        editor.commit();
        Log.i("모은","saveData(Main)-stampCount : "+AppManager.getInstance().getStampCount());




    }
    @SuppressLint("LongLogTag")
    private void loadUserSnsData()
    {
        Log.i("모은 loadData(main_activity)","loadData");

        ArrayList<TourList> list = AppManager.getInstance().getTourList();
        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);

        AppManager.getInstance().setStampCount(Integer.parseInt(prefs.getString("userSns", "")));
        Log.i("모은","userSns(main) "+AppManager.getInstance().getuserSns());

    }

    public void removeKey(String key) {

        SharedPreferences prefs = getSharedPreferences("NPT", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = prefs.edit();

        edit.remove(key);

        edit.commit();

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
    private MainActivity mainActivity;
    InputMethodManager inputMethodManager;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {

        Log.i("모은 ","main 백 버튼");
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