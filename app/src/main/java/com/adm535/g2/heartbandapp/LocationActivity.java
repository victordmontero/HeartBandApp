package com.adm535.g2.heartbandapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        final TextView latitudeText = findViewById(R.id.latitud);
        final TextView longitudeText = findViewById(R.id.longitud);
        final TextView address = findViewById(R.id.address);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng pos = QueryUtils.getHeartBandData().toLatLng();

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                map.addMarker(new MarkerOptions().position(pos));

                latitudeText.setText(QueryUtils.ConvertToDMS(pos.latitude));
                longitudeText.setText(QueryUtils.ConvertToDMS(pos.longitude));

                Geocoder geocoder = new Geocoder(LocationActivity.this);

                try {
                    List<Address> list = geocoder.getFromLocation(pos.latitude, pos.longitude, 3);
                    Address addr = list.get(0);
                    address.setText(String.format("%s %s,%s,%s", addr.getThoroughfare(),
                            addr.getSubThoroughfare() == null ? "" : addr.getSubThoroughfare(),
                            addr.getSubLocality(), addr.getAdminArea()));
                } catch (IOException iox) {
                    address.setText("No Address");
                } catch (Exception ex) {
                    Log.d("GEOCODER_ERROR", "onMapReady: Error: " + ex.getMessage());
                    address.setText("No Address");
                }
            }
        });
    }
}
