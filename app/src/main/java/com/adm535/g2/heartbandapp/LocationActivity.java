package com.adm535.g2.heartbandapp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LocationActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new LocationFragment();
    }
}
