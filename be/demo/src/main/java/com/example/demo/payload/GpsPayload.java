package com.example.demo.payload;

public class GpsPayload {

    // commit을 위한 주석 추가 application.properties 위치 변경 완료료

    private double lat;
    private double lng;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


}
