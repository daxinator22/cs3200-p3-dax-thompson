package com.daxthompsonproject1.clientapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.daxthompsonproject1.api.models.ClientData;
import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.viewmodels.ClientViewModel;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.OnLocationCameraTransitionListener;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.Image;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private ClientViewModel viewModel;
    private ArrayList<Point> managerLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_map);

        this.managerLocations = new ArrayList<>();
        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        //On change in managers
        this.viewModel.getManagers().observe(this, managers -> {
            managerLocations.clear();

            //Gets all the geo data for managers
            for(ManagerData manager : managers){
                this.managerLocations.add(Point.fromLngLat(Double.parseDouble(manager.lon), Double.parseDouble(manager.lat)));
            }
        });

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
                        locationComponent.setCameraMode(CameraMode.TRACKING_GPS_NORTH, new OnLocationCameraTransitionListener() {
                            @Override
                            public void onLocationCameraTransitionFinished(int cameraMode) {
                                locationComponent.zoomWhileTracking(15, 100);
                            }

                            @Override
                            public void onLocationCameraTransitionCanceled(int cameraMode) {

                            }
                        });

                        style.addSource(new GeoJsonSource("manager_locations",
                                    FeatureCollection.fromFeatures(new Feature[]{
                                            Feature.fromGeometry(LineString.fromLngLats(managerLocations))
                                    })
                        ));

                        style.addLayer(new LineLayer("line", "manager_locations"));
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