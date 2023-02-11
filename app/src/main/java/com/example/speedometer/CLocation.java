package com.example.speedometer;

import android.location.Location;

import androidx.annotation.NonNull;

class CLocation extends Location {

    private boolean bUseMetricUnits = false;

    public CLocation(Location location) {
        this(location, true);
    }

    public CLocation(Location location, boolean bUseMetricUnits) {
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }

    public boolean getUseMetricUnits() {
        return this.bUseMetricUnits;
    }

    public void setbUseMetricUnits(boolean bUseMetricUnits) {
        this.bUseMetricUnits = bUseMetricUnits;
    }

    @Override
    public float distanceTo(@NonNull Location dest) {
        float nDistance = super.distanceTo(dest);

        if (!this.getUseMetricUnits()) {
            nDistance = nDistance * 3.28083989501312f;
        }
        return nDistance;
    }

    @Override
    public float getAccuracy() {
        float nAccuracy = super.getAccuracy();

        if (!this.getUseMetricUnits()) {
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
    }

    @Override
    public double getAltitude() {
        double nAltitude = super.getAltitude();

        if (!this.getUseMetricUnits()) {
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        float nSpeed = super.getSpeed() * 3.6f;

        if (!this.getUseMetricUnits()) {
            nSpeed = nSpeed * 2.23693629f;
        }
        return nSpeed;
    }
}
