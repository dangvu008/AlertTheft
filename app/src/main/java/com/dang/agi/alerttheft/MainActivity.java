package com.dang.agi.alerttheft;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.dang.agi.alerttheft.preferences.Pref_Setting;
import com.dang.agi.alerttheft.preferences.PreferencesSetting;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Spinner spinner, spinnerDelay;
    CheckBox checkBoxVibrate, checkBoxGumshoe;
    Switch switchAlertCharging, switchAlerttouch, switchAlertLurcher, switchAlertRob, switchAlertSim;
    PowerManager pm;
    private Intent intentPower;
    Alert alert;
    public static final String KEY_VIBRATE = "VIBRATE_STATE";
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        alert = Pref_Setting.getSetting(this);
        addControls();
        loadToSpinner();
        loadToSpinnerDelayTime();
        enableAlertCharing();
        loadValueSetting();
        loadEnableFunction();
        addEvents();

        if (media!=null)
            media.stop();

    }

    private void loadEnableFunction() {
        //AlertCharging
        if (PreferencesSetting.getSetting(this,getResources().getString(R.string.key_charging)))
            switchAlertCharging.setChecked(true);
        else
            switchAlertCharging.setChecked(false);
        //AlertLurcher
        if (PreferencesSetting.getSetting(this,getResources().getString(R.string.key_lurcher)))
            switchAlertLurcher.setChecked(true);
        else
            switchAlertLurcher.setChecked(false);
        //Alerttouch
        if (PreferencesSetting.getSetting(this,getResources().getString(R.string.key_touch)))
            switchAlerttouch.setChecked(true);
        else
            switchAlerttouch.setChecked(false);
        //AlertRob
        if (PreferencesSetting.getSetting(this,getResources().getString(R.string.key_rob)))
            switchAlertRob.setChecked(true);
        else
            switchAlertRob.setChecked(false);
        // alert change sim
        if (PreferencesSetting.getSetting(this,getResources().getString(R.string.key_sim)))
            switchAlertSim.setChecked(true);
        else
            switchAlertSim.setChecked(false);
    }

    private void loadValueSetting() {
        if (alert!=null){
            //ringtone
            int ringtone = alert.getRingtone();
            if (ringtone==R.raw.alert_sound1)
                spinner.setSelection(0,true);
             else if (ringtone==R.raw.alert_sound2)
                spinner.setSelection(1,true);
            else
                spinner.setSelection(2,true);
            // time delay
            int delay = alert.getTimeDelay();
            if (delay==1000)
                spinnerDelay.setSelection(0,true);
             else if(delay==3000)
                spinnerDelay.setSelection(1,true);
            else
                spinnerDelay.setSelection(2,true);
            // gumshoe
            if (alert.isGumshoe())
                checkBoxGumshoe.setChecked(true);
            else
                checkBoxGumshoe.setChecked(false);
            //  vibrate
            if (alert.isVibrator())
                checkBoxVibrate.setChecked(true);
            else
                checkBoxVibrate.setChecked(false);
        }
    }

    private void loadToSpinnerDelayTime() {
        spinnerDelay.setPrompt("choose time delay !");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.delayTime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerDelay.setAdapter(adapter);
    }


    private void loadToSpinner() {
        spinner.setPrompt("choose ringtone !");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ringtones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);

    }

    public void enableAlertCharing() {
        if (!checkCharingConnected()) {
            PreferencesSetting.setting(this,getResources().getString(R.string.key_charging),false);
            switchAlertCharging.setVisibility(View.GONE);
        }
        else {
            switchAlertCharging.setVisibility(View.VISIBLE);
        }

    }

    private void addEvents() {
        switchAlertCharging.setOnCheckedChangeListener(this);
        switchAlertLurcher.setOnCheckedChangeListener(this);
        switchAlertRob.setOnCheckedChangeListener(this);
        switchAlertSim.setOnCheckedChangeListener(this);
        switchAlerttouch.setOnCheckedChangeListener(this);
        checkBoxVibrate.setOnCheckedChangeListener(this);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeRingtone(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerDelay.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                changeDelayTime(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (media!=null)
                    media.stop();
            }
        });

    }

    public boolean checkCharingConnected() {
        boolean state = false;
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
       /* int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;*/

        if (isCharging) {
            state = true;
        } else {
            state = false;
        }
        return state;
    }

    private void addControls() {
        checkBoxVibrate = (CheckBox) findViewById(R.id.checkBoxVibrate);
        checkBoxGumshoe = (CheckBox) findViewById(R.id.checkboxgumshoe);
        spinner = (Spinner) findViewById(R.id.spinnerRingtone);
        switchAlertCharging = (Switch) findViewById(R.id.switchAlertCharging);
        switchAlerttouch = (Switch) findViewById(R.id.switchAlerttouch);
        switchAlertLurcher = (Switch) findViewById(R.id.switchAlertLurcher);
        switchAlertRob = (Switch) findViewById(R.id.switchAlertRob);
        switchAlertSim = (Switch) findViewById(R.id.switchAlertSim);
        spinnerDelay = (Spinner) findViewById(R.id.spinnerDelay);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_main_setting: {
                toConfiguration();
            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void toConfiguration() {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.switchAlertCharging: {
                checkCharging(b);
            }
            break;
            case R.id.switchAlertLurcher: {
               setting(R.string.key_lurcher,b);
            }
            break;
            case R.id.switchAlertRob: {
                setting(R.string.key_rob,b);
            }
            break;
            case R.id.switchAlerttouch: {
               setting(R.string.key_touch,b);
            }
            break;
            case R.id.switchAlertSim: {
               setting(R.string.key_sim,b);
            }
            break;
            case R.id.checkBoxVibrate: {
                enableVibrate(b);
            }
            case R.id.checkboxgumshoe:{
                enablegumshoe(b);
            }
            break;
            default:
                break;
        }
    }

    private void enablegumshoe(boolean b) {
        if (alert == null)
            alert = new Alert();
        if (b)
            alert.setGumshoe(true);
        else
            alert.setGumshoe(false);
        Pref_Setting.addSetting(this, alert);
    }

    private void enableVibrate(boolean b) {
        if (alert == null)
            alert = new Alert();
        if (b)
            alert.setVibrator(true);
        else
            alert.setVibrator(false);
        Pref_Setting.addSetting(this, alert);
    }

    public void visibleAlertCharging(boolean visible) {
        new visbleSwichCharging().execute(visible);
    }
    
    private void checkCharging(boolean isChecked) {
        if (!isChecked && !checkCharingConnected()) {
            Toast.makeText(this, "để bật chức năng này bạn cần cắm sạc !", Toast.LENGTH_LONG).show();
            PreferencesSetting.setting(this,getResources().getString(R.string.key_charging),false);
            new visbleSwichCharging().execute(false);
        } else{
            if (isChecked && checkCharingConnected())
                PreferencesSetting.setting(this,getResources().getString(R.string.key_charging),true);
        }
    }
    private void setting(int keyId,boolean value){
        if (value)
            PreferencesSetting.setting(this,getResources().getString(keyId),true);
        else
            PreferencesSetting.setting(this,getResources().getString(keyId),false);
    }
    private void changeDelayTime(int position) {
        int time = 0;
        if (position == 0) {
            time = 1000;
        } else if (position == 1) {
            time = 3000;
        } else if (position == 2) {
            time = 5000;
        }
        if (alert == null) {
            alert = new Alert();
        }
        alert.setTimeDelay(time);
        Pref_Setting.addSetting(this, alert);
    }

    private void changeRingtone(int position) {
        int iRingtone = 0;
        if (position == 0) {
            iRingtone = R.raw.alert_sound1;
        } else if (position == 1) {
            iRingtone = R.raw.alert_sound2;
        } else if (position == 2) {
            iRingtone = R.raw.alert_sound3;
        }
         media = MediaPlayer.create(this, iRingtone);
        media.start();
        if (alert == null) {
            alert = new Alert();
        }
        alert.setRingtone(iRingtone);
        Pref_Setting.addSetting(this, alert);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (media!=null)
            media.stop();
    }

    class visbleSwichCharging extends AsyncTask<Boolean, Boolean, Boolean> {

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            boolean visible = booleen[0];
            publishProgress(visible);
            return visible;
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            if (!values[0] && !checkCharingConnected()) {
                switchAlertCharging.setVisibility(View.GONE);
            } else {
                switchAlertCharging.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Boolean booleen) {
            super.onPostExecute(booleen);
        }
    }
}
