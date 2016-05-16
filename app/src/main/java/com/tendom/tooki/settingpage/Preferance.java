package com.tendom.tooki.settingpage;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.tendom.tooki.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Preferance extends PreferenceFragment implements Preference.OnPreferenceClickListener{
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);


    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }
}

