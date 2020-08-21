package com.test.mosun;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.test.mosun.home.areaItem;
import com.test.mosun.login.LoginActivity;
import com.test.mosun.map.LocationList;
import com.test.mosun.qrcode.QRPopupActivity;
import com.test.mosun.stamp.TourList;

import java.util.ArrayList;
import java.util.List;

public class AppManager {
    private static AppManager instance = null;
    private List<areaItem> arealist;
    private ArrayList<TourList> tourList;
    private ArrayList<TourList> tourJeonjuList;
    private ArrayList<TourList> tourSunchangList;
    private ArrayList<LocationList> locationList;
    public int stampCount = 0; //스탬프 개수
    public int maskCount = 0; // 마스크 개수


    private AppManager() {
    }

    public static AppManager getInstance() {
        if (instance == null)
            instance = new AppManager();
        return instance;
    }


    public void setMaskCount(int maskCount){ this.maskCount = maskCount; }
    public int getMaskCount(){ return maskCount;}

    public void setStampCount(int stampCount){ this.stampCount = this.stampCount; }
    public int getStampCount(){ return stampCount;}
    public List<areaItem> getAreaList() {
        return arealist;
    }
    public void setAreaList(List<areaItem> arealist) {
        this.arealist = arealist;
    }

    public ArrayList<TourList> getTourList() {
        return tourList;
    }
    public ArrayList<TourList> getJeonjuList() {
        return tourJeonjuList;
    }
    public ArrayList<TourList> getSunchangList() {
        return tourSunchangList;
    }


    public void setTourList(ArrayList<TourList> tourList) {
        this.tourList = tourList;
    }

    public void setJeonjuList(ArrayList<TourList> tourList) {
        this.tourJeonjuList = tourList;
    }

    public void setSunchangList(ArrayList<TourList> tourList) {
        this.tourSunchangList = tourList;
    }

    public ArrayList<LocationList> getLocationList() { return locationList; }
    public void setLocationList(ArrayList<LocationList> locationList) { this.locationList = locationList; }

    private String userId = null;
    public void setUserID(String userID) {this.userId = userID;}
    public String getUserId() { return userId;}

    private String userName = null;
    public void setUserName(String userName) {this.userName = userName;}
    public String getUserName() { return userName;}

    private FloatingActionButton floatingActionButton;
    public void setMenuFloatingActionButton (FloatingActionButton floatingActionButton) {this.floatingActionButton = floatingActionButton;}
    public FloatingActionButton getMenuFloatingActionButton() { return floatingActionButton; }

    private LoginActivity loginActivity;
    public void setLoginActivity(LoginActivity loginActivity) {this.loginActivity = loginActivity; }
    public LoginActivity getLoginActivity(){ return  loginActivity; }

    private MainActivity mainActivity;
    public void setMainActivity(MainActivity mainActivity) {this.mainActivity = mainActivity; }
    public MainActivity getMainActivity(){ return  mainActivity;}

    private QRPopupActivity qrPopUpActivity;
    public void setQRPopUpActivity (QRPopupActivity qrPopUpActivity) {this.qrPopUpActivity = qrPopUpActivity; }
    public QRPopupActivity getQRPopUpActivity() { return  qrPopUpActivity; }

}
