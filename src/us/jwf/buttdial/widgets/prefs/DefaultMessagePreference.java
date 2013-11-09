package us.jwf.buttdial.widgets.prefs;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import us.jwf.buttdial.R;

/**
 *
 */
public class DefaultMessagePreference extends Preference {
    public DefaultMessagePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    public DefaultMessagePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DefaultMessagePreference(Context context) {
        super(context);
        setup();
    }

    protected void setup() {
        setLayoutResource(R.layout.pref_default_message);
    }
}
