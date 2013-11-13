package us.jwf.buttdial.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import us.jwf.buttdial.App;
import us.jwf.buttdial.R;
import us.jwf.buttdial.utils.Logger;

/**
 *
 */
public class ButtDialDetectorService extends Service {
    public static int NOTIFICATION_ID = 1;
    private App app;
    private TelephonyManager telephonyManager;
    private boolean wasIncoming;
    private PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Logger.i("ButtDialDetectorService.listener.onCallStateChanged(" + state + ", " + incomingNumber + ")");
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    wasIncoming = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    wasIncoming = false;
                    if (incomingNumber == null || incomingNumber.isEmpty()) handleOutgoingCallEnd();
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (incomingNumber == null || incomingNumber.isEmpty()) handleOutgoingCallBegin();
                    break;
            }
        }
    };

    private boolean callStarted;
    private NotificationManager notificationManager;
    private Notification notification;
    private long callStartTime;
    private PendingIntent notificationPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i("ButtDialDetectorService.onCreate()");
        app = (App) getApplicationContext();
        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(Receiver.ACTION_APOLOGIZE_TO_LAST);
        notificationPendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setAutoCancel(true);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setContentText(getString(R.string.notification_text));
        builder.setContentIntent(notificationPendingIntent);

        notification = builder.build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Logger.i("ButtDialDetectorService.onStartCommand("+intent+", "+flags+", "+startId+")");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logger.i("ButtDialDetectorService.onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i("ButtDialDetectorService.onBind("+intent.getAction()+")");
        return new ButtDialDetectorBinder();
    }

    public void handleOutgoingCallBegin() {
        Logger.i("ButtDialDetectorService.handleOutgoingCallBegin()");
        callStarted = !wasIncoming;
        callStartTime = System.currentTimeMillis();
    }

    public void handleOutgoingCallEnd() {
        Logger.i("ButtDialDetectorService.handleOutgoingCallEnd()");
        if (!callStarted) {
            return;
        }
        callStarted = false;
        notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public class ButtDialDetectorBinder extends Binder {
        ButtDialDetectorService getService() {
            return ButtDialDetectorService.this;
        }
    }
}
