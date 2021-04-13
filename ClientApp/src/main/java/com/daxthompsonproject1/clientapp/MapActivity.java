package com.daxthompsonproject1.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.Image;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;

import org.w3c.dom.Text;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private MarkerView userMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_map);



        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }

                        LocationComponent locationComponent = mapboxMap.getLocationComponent();
                        locationComponent.activateLocationComponent(
                                LocationComponentActivationOptions.builder(MapActivity.this, style)
                                        .useSpecializedLocationLayer(true)
                                        .build()
                        );

                        locationComponent.setLocationComponentEnabled(true);
                        locationComponent.setCameraMode(CameraMode.TRACKING_GPS_NORTH);
                        locationComponent.zoomWhileTracking(17, 100);

//                        MarkerViewManager markerViewManager = new MarkerViewManager(mapView, mapboxMap);

//                        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//                        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, (location) -> {
//                            if(userMarker == null) {
//                                TextView here = new TextView(MapActivity.this);
//                                here.setText("You are here");
//                                userMarker = new MarkerView(new LatLng(location.getLatitude(), location.getLongitude()), here);
//                                markerViewManager.addMarker(userMarker);
//                            }
//                            else{
//                                userMarker.setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
//                            }
//                        });

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}