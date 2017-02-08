package com.dang.agi.alerttheft;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by DANG on 2/8/2017.
 */

public class Alert implements Serializable {
    private Uri uriRington;
    private boolean vibrator;
    private String location;

    public Uri getUriRington() {
        return uriRington;
    }

    public void setUriRington(Uri uriRington) {
        this.uriRington = uriRington;
    }

    public boolean isVibrator() {
        return vibrator;
    }

    public void setVibrator(boolean vibrator) {
        this.vibrator = vibrator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
