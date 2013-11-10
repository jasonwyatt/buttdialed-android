package us.jwf.buttdial.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import us.jwf.buttdial.App;
import us.jwf.buttdial.models.Call;
import us.jwf.buttdial.utils.Logger;

import java.util.List;

/**
 *
 */
public class Receiver extends BroadcastReceiver {
    public static String ACTION_APOLOGIZE_TO_LAST = "us.jwf.actions.APOLOGIZE_TO_LAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, ButtDialDetectorService.class));
        }
    }
}
