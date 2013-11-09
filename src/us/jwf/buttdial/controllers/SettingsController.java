package us.jwf.buttdial.controllers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SettingsController {
    public static String PREF_USE_DEFAULT_SMS = "use_default_sms_app";
    public static String PREF_MESSAGE_OPTIONS = "message_options";
    public static String PREF_DEFAULT_MESSAGE = "default_message";

    private final App app;
    private SharedPreferences prefs;

    public SettingsController(App app) {
        this.app = app;
        prefs = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public boolean useDefaultSMS() {
        return prefs.getBoolean(PREF_USE_DEFAULT_SMS, true);
    }

    public void setUseDefaultSMS(boolean useDefault) {
        prefs.edit().putBoolean(PREF_USE_DEFAULT_SMS, useDefault).commit();
    }

    public Set<String> getMessageOptions() {
        return prefs.getStringSet(PREF_MESSAGE_OPTIONS, getDefaultMessageOptionsSet());
    }

    public String[] getMessageOptionsArray() {
        Set<String> options = getMessageOptions();
        return options.toArray(new String[options.size()]);
    }

    public void setMessageOptions(String[] messageOptions) {
        HashSet<String> optionSet = new HashSet<String>(messageOptions.length);
        for (String message : messageOptions) {
            optionSet.add(message);
        }
        setMessageOptions(optionSet);
    }

    public void setMessageOptions(Set<String> messageOptions) {
        prefs.edit().putStringSet(PREF_USE_DEFAULT_SMS, messageOptions).commit();
    }

    public void addMessageOption(String message) {
        Set<String> messageOptions = getMessageOptions();
        messageOptions.add(message);
        setMessageOptions(messageOptions);
    }

    public void removeMessageOption(String message) {
        Set<String> options = getMessageOptions();
        options.remove(message);
        setMessageOptions(options);
    }

    public String getDefaultMessage() {
        return prefs.getString(PREF_DEFAULT_MESSAGE, app.getString(R.string.apology_content));
    }

    public void setDefaultMessage(String message) {
        prefs.edit().putString(PREF_DEFAULT_MESSAGE, message).commit();
    }

    private Set<String> getDefaultMessageOptionsSet() {
        HashSet<String> defaultOptions = new HashSet<String>();
        defaultOptions.add(app.getString(R.string.apology_content));
        return defaultOptions;
    }

}
