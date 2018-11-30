package com.adm535.g2.heartbandapp.models;

public class HeartBandData {
    private float[] volts;
    private float[] seconds;
    private float latitude;
    private float longitude;

    public HeartBandData() {
        this(null, null, 0.0f, 0.0f);
    }

    public HeartBandData(float[] volts, float[] seconds, float latitude, float longitude) {
        this.volts = volts;
        this.seconds = seconds;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float[] getVolts() {
        return volts;
    }

    public void setVolts(float[] volts) {
        this.volts = volts;
    }

    public float[] getSeconds() {
        return seconds;
    }

    public void setSeconds(float[] seconds) {
        this.seconds = seconds;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
