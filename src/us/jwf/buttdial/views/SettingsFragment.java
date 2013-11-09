package us.jwf.buttdial.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import us.jwf.buttdial.R;
import us.jwf.buttdial.controllers.SettingsController;

/**
 *
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                getActivity().getApplicationContext()
        ).edit();
        if (key.equals(SettingsController.PREF_USE_DEFAULT_SMS)){
            editor.putBoolean(key, sharedPreferences.getBoolean(key, true));
        }
        editor.commit();
    }
}
