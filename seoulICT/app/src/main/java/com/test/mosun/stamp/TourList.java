package com.test.mosun.stamp;

import android.location.Location;
import android.view.View;

import com.test.mosun.R;

public class TourList {

    private View view;

    private String tourTitle; // 여행 제목
    private String tourDescription; // 여행 설명
    private double distance; // 거리
    private double latitude; // 위도
    private double longitude; // 경도
    private float pridictionNumber; // 예상 수치값
    private float todayNumber; // 오늘 방문객 수
    private int imageNumericalValueID; // 수치값에 따른 이미지 id
    private int image; // 원래 이미지
    private Location locationA;
    private Location locationB;
    private int icon;

    private boolean isCollected = false;//스탬프 여부
    private boolean isClick = true;

    // 기본 생성자 초기화
    public TourList(){

        tourTitle = "0";
        tourDescription = "0";
        distance = 0;
        latitude = 0;
        longitude = 0;
        image = 0;
        pridictionNumber = 0;
        todayNumber = 0;
        imageNumericalValueID = 0;
        icon = 0;
    }

    public TourList(String tourTitle, String tourDescription, double latitude, double longitude, float pridictionNumber,float todayNumber, int image, int icon)
    {
        this.tourTitle = tourTitle;
        this.tourDescription = tourDescription;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pridictionNumber = pridictionNumber;
        this.todayNumber = todayNumber;
        this.image = image;
        this.imageNumericalValueID =image;
        this.icon = icon;
        locationA = new Location("point A");
        locationA.setLatitude(36.3224792); // 현재위치 x
        locationA.setLongitude(127.4245268); // 현재위치 y
        locationB = new Location("point B");
        locationB.setLatitude(latitude);
        locationB.setLongitude(longitude);
        distance = 0.0;
    }

    public boolean isCollected() {
        return isCollected;
    }
    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean getIsClick() {
        return isClick;
    }
    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }


    public void setLocationB(){
        locationB.setLatitude(latitude);
        locationB.setLongitude(longitude);
    }

    public Location getLocationB(){
        return locationB;
    }

    public int getImageNumericalValueID() {
        return imageNumericalValueID;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public void setTourTitle(String data) { tourTitle = data; }

    public String getTourDescription() {
        return tourDescription;
    }

    public void setTourDescription(String tourDescription) {
        this.tourDescription = tourDescription;
    }

    public double getDistance() { return distance; }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getPridictionNumber() {
        return pridictionNumber;
    }


    public float getTodayNumber() {
        return todayNumber;
    }
    public void setTodayNumber(float todayNumber) {
        this.todayNumber = todayNumber;

    }

    public void setImageNumericalValueID(int imageNumericalValueID) {
        this.imageNumericalValueID = imageNumericalValueID;
    }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public int  setImage(){
        if(isCollected)
            return R.drawable.area_0;
        if(pridictionNumber >= 2000){
            return R.drawable.red;
        } else if(pridictionNumber <2000 && pridictionNumber > 500)
        {
            return R.drawable.yellow;
        }
        return R.drawable.blue;

    }

    public int getIcon(){
        return this.icon;
    }

}