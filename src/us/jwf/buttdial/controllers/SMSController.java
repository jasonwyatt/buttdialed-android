package us.jwf.buttdial.controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.widget.Toast;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;
import us.jwf.buttdial.utils.Logger;
import us.jwf.buttdial.utils.Utils;

/**
 *
 */
public class SMSController {
    private final App app;

    public SMSController(App app) {
        this.app = app;
    }

    public void sendApologySMS(final Context context, final String toNumber, final String toName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(R.string.sms_confirm_title);
        builder.setMessage(Utils.buildConfirmMessage(context, toNumber, toName));
        builder.setNegativeButton(R.string.cancel_apology, null);
        builder.setPositiveButton(R.string.send_apology, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (app.settings().useDefaultSMS()) {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(toNumber, null, app.getResources().getString(R.string.apology_content), null, null);
                    Toast.makeText(app.getBaseContext(), R.string.apology_sent_toast, Toast.LENGTH_LONG).show();
                } else {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+toNumber));
                    smsIntent.putExtra("sms_body", app.getResources().getString(R.string.apology_content));
                    context.startActivity(Intent.createChooser(smsIntent, app.getResources().getString(R.string.sms_app_chooser_title)));
                }
            }
        });
        builder.show();
    }
}
