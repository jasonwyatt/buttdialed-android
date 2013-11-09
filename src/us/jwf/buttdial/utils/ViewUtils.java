package us.jwf.buttdial.utils;

import android.content.Context;
import android.content.Intent;
import us.jwf.buttdial.App;

/**
 *
 */
public class ViewUtils {
    App app;

    public ViewUtils(App app) {
        this.app = app;
    }

    public void startActivity(Context context, Class targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(intent);
    }
}
