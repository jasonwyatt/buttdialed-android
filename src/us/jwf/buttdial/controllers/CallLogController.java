package us.jwf.buttdial.controllers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import us.jwf.buttdial.App;
import us.jwf.buttdial.models.Call;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for accessing the call log.
 */
public final class CallLogController {

    private final App app;
    private static String[] columns = {
        CallLog.Calls.CACHED_NAME,
        CallLog.Calls.NUMBER,
        CallLog.Calls.DATE,
        CallLog.Calls.DURATION,
        CallLog.Calls.TYPE
    };
    private static int LIMIT = 5;

    public CallLogController(App app) {
        this.app = app;
    }

    public List<Call> getRecentCalls() {
        Uri contacts = CallLog.Calls.CONTENT_URI;
        ContentResolver cr = app.getContentResolver();
        Cursor cursor = cr.query(
                contacts,
                columns,
                CallLog.Calls.TYPE + "= ?",
                new String[]{CallLog.Calls.OUTGOING_TYPE+""},
                CallLog.Calls.DATE + " DESC"
        );

        ArrayList<Call> calls = new ArrayList<Call>();

        int i = 0;
        while (cursor.moveToNext() && i++ < LIMIT) {
            Call call = new Call(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getLong(2),
                cursor.getLong(3)
            );
            calls.add(call);
        }

        return calls;
    }

}
