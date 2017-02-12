package com.dang.agi.alerttheft;


import java.io.Serializable;

/**
 * Created by DANG on 2/8/2017.
 */

public class Alert implements Serializable {
    private int ringtone;
    private boolean vibrator;
    private String location;
    private int timeDelay;
    private boolean gumshoe;

    public int getRingtone() {
        return ringtone;
    }

    public void setRingtone(int ringtone) {
        this.ringtone = ringtone;
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

    public int getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(int timeDelay) {
        this.timeDelay = timeDelay;
    }

    public boolean isGumshoe() {
        return gumshoe;
    }

    public void setGumshoe(boolean gumshoe) {
        this.gumshoe = gumshoe;
    }
}
