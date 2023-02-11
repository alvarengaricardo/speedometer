package com.example.speedometer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    SwitchCompat sw_metric;
    TextView tv_speed, tv_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sw_metric = findViewById(R.id.sw_metric);
        tv_speed = findViewById(R.id.tv_speed);
        tv_label = findViewById(R.id.tv_label);

        // check for GPS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
            // start program if permission granted
            doStuff();
        }
        this.updateSpeed(null);

        sw_metric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.this.updateSpeed(null);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void doStuff() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this, "Waiting for GPS...", Toast.LENGTH_SHORT).show();
    }

    private void updateSpeed(CLocation location) {
        float nCurrentSpeed = 0;

        if (location != null) {
            location.setbUseMetricUnits(this.useMetricUnits());
            nCurrentSpeed = location.getSpeed();
        }

        Formatter fmt = new Formatter(new StringBuilder());
        fmt.format(Locale.US, "%5.0f", nCurrentSpeed);
        //String strCurrentSpeed = fmt.toString();
        //strCurrentSpeed = strCurrentSpeed.replace(" ", " ");

        if (this.useMetricUnits()) {
            tv_label.setText("km/h");
        } else {
            tv_label.setText("miles/h");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            CLocation myLocation = new CLocation(location, this.useMetricUnits());
            this.updateSpeed(myLocation);
        }
    }

    private boolean useMetricUnits() {
        return sw_metric.isChecked();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}