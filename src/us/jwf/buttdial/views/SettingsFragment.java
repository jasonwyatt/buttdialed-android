package us.jwf.buttdial.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;
import us.jwf.buttdial.controllers.SettingsController;

/**
 *
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference defaultMessage;
    private App app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        app = (App) getActivity().getApplicationContext();

        defaultMessage = (ListPreference)findPreference(SettingsController.PREF_DEFAULT_MESSAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        updatePrefs();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    protected void updatePrefs() {
        defaultMessage.setEntries(app.settings().getMessageOptionsArray());
        defaultMessage.setEntryValues(app.settings().getMessageOptionsArray());
        defaultMessage.setSummary("\""+ app.settings().getDefaultMessage() + "\"");
        defaultMessage.setValue(app.settings().getDefaultMessage());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(SettingsController.PREF_USE_DEFAULT_SMS)){
            app.settings().setUseDefaultSMS(sharedPreferences.getBoolean(key, true));
        }

        updatePrefs();
    }
}
