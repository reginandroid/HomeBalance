package com.example.buh.homebalance;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class PrefActivity extends PreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    Dialog_ChoosePeriod dcp;
    SharedPreferences prefs;
    String from, to;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_pref);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        dcp = (Dialog_ChoosePeriod) findPreference("period_from_to");
        from = sp.getString("date_from", "");
        to = sp.getString("date_to", "");
        dcp.setSummary("You choose period from " + from + " to " + to);
    }
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        s = "period_from_to";
        from = sharedPreferences.getString("date_from", "");
        to = sharedPreferences.getString("date_to", "");
        Toast.makeText(getApplication(), "You choose data from " + from + " to " + to, Toast.LENGTH_SHORT).show();
        Preference pref = findPreference(s);
        if (pref instanceof Dialog_ChoosePeriod) {
            Dialog_ChoosePeriod dcp = (Dialog_ChoosePeriod) pref;
            pref.setSummary("You choose data from " + from + " to " + to);
        }
    }
}




