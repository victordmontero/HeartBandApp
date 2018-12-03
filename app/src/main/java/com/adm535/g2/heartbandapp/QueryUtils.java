package com.adm535.g2.heartbandapp;

import com.adm535.g2.heartbandapp.models.HeartBandData;

import java.util.ArrayList;

public class QueryUtils {

    public static ArrayList<HeartBandData> getHeartBandData() {
        ArrayList<HeartBandData> data = new ArrayList<>();

        data.add(new HeartBandData(
                new float[]{2.0f, 3.0f},
                new float[]{1.0f, 2.0f},
                18.6000f,
                -63.5432f
        ));
        data.add(new HeartBandData(
                new float[]{6.0f, 5.0f},
                new float[]{3.0f, 4.0f},
                18.6000f,
                -63.5432f
        ));
        data.add(new HeartBandData(
                new float[]{8.0f, 6.0f},
                new float[]{10.0f, 8.0f},
                18.6000f,
                -63.5432f
        ));

        return data;
    }

}
