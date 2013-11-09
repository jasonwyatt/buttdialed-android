package us.jwf.buttdial;

import android.app.Application;
import us.jwf.buttdial.controllers.CallLogController;
import us.jwf.buttdial.controllers.SMSController;
import us.jwf.buttdial.controllers.SettingsController;
import us.jwf.buttdial.utils.Logger;
import us.jwf.buttdial.utils.ViewUtils;

/**
 * Main Application file.
 */
public class App extends Application {
    private ViewUtils views;
    private CallLogController calls;
    private SMSController sms;
    private SettingsController settings;

    public App() {
        super();
        Logger.i("App Initialized.");
    }

    @Override
    public void onCreate() {
        Logger.i("App onCreate()");

        views = new ViewUtils(this);
        calls = new CallLogController(this);
        sms = new SMSController(this);
        settings = new SettingsController(this);
    }

    public ViewUtils views() {
        return views;
    }

    public CallLogController calls() {
        return calls;
    }

    public SMSController sms() {
        return sms;
    }

    public SettingsController settings() {
        return settings;
    }
}
