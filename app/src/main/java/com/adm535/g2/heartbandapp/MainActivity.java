package com.adm535.g2.heartbandapp;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new ECGFragment();
    }
}
