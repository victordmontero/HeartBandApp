package com.adm535.g2.heartbandapp;

import com.adm535.g2.heartbandapp.models.HeartBandData;

import java.util.ArrayList;

public class QueryUtils {

    public static HeartBandData getHeartBandData() {

        return new HeartBandData(
                new float[]{2.0f, 3.0f},
                new float[]{1.0f, 2.0f},
                18.473956f,
                -69.913072f);
    }

    public static String ConvertToDMS(double value, String format) {
        value = Math.abs(value);
        int degree = (int) value;
        int minutes = (int) ((value - degree) * 60);
        int seconds = (int) (((value - degree) * 60) - minutes) * 60;
        return String.format(format, degree, minutes, seconds);
    }

    public static String ConvertToDMS(double value) {
        return ConvertToDMS(value, "%d°%d\'%d\"");
    }

}
