package com.adm535.g2.heartbandapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.adm535.g2.heartbandapp.models.HeartBandData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TextView latitudeText = findViewById(R.id.latitud);
        final TextView longitudeText = findViewById(R.id.longitud);
        final TextView address = findViewById(R.id.address);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                ArrayList<HeartBandData> results = QueryUtils.getHeartBandData();
                LatLng pos = results.get(0).toLatLng();

                map.moveCamera(CameraUpdateFactory.newLatLng(pos));
                map.addMarker(new MarkerOptions().position(pos));

                latitudeText.setText(Double.toString(pos.latitude));
                longitudeText.setText(Double.toString(pos.longitude));

            }
        });
    }
}
