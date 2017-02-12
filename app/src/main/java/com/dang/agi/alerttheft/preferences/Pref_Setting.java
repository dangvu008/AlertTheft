package com.dang.agi.alerttheft.preferences;

import android.content.Context;

import com.dang.agi.alerttheft.Alert;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by DANG on 2/8/2017.
 */

public class Pref_Setting {
     public static void addSetting(Context context, Alert alert){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context,"SETTING",MODE_PRIVATE);
         complexPreferences.putObject("SETTING",alert);
         complexPreferences.commit();
     }
    public  static Alert getSetting(Context context){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(context,"SETTING",MODE_PRIVATE);
        Alert  alert = complexPreferences.getObject("SETTING",Alert.class);
        return alert;
    }


}
