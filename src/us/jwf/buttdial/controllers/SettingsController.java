package us.jwf.buttdial.controllers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import us.jwf.buttdial.App;

/**
 *
 */
public class SettingsController {
    public static String PREF_USE_DEFAULT_SMS = "use_default_sms_app";
    private final App app;

    public SettingsController(App app) {
        this.app = app;
    }

    public boolean useDefaultSMS() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(app);
        return prefs.getBoolean(PREF_USE_DEFAULT_SMS, true);
    }
}
