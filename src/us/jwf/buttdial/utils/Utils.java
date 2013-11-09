package us.jwf.buttdial.utils;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import us.jwf.buttdial.R;

/**
 *
 */
public class Utils {
    public static long DAY = 24*60*60*1000;
    public static long HOUR = 60*60*1000;
    public static long MINUTE = 60*1000;
    public static long SECOND = 1000;

    public static String relativeTime(long time){
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - time;

        if (diff > DAY * 2) {
            return String.format("%d days", diff / DAY);
        } else if (diff > DAY) {
            return "1 day";
        } else if (diff > HOUR * 2) {
            return (diff / HOUR) + " hours";
        } else if (diff > HOUR) {
            return "1 hour";
        } else if (diff > MINUTE * 2) {
            return (diff / MINUTE) + " minutes";
        } else if (diff > MINUTE) {
            return "1 minute";
        } else {
            return "just now";
        }
    }

    public static String callDuration(long duration) {
        Logger.i("Converting "+duration);
        duration = duration * 1000;
        long hours = duration / HOUR;
        long minutes = (duration % HOUR) / MINUTE;
        long seconds = (duration % HOUR % MINUTE) / SECOND;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String phoneNumber(String rawNumber) {
        return PhoneNumberUtils.formatNumber(rawNumber);
    }

    public static String buildConfirmMessage(Context context, String toNumber, String toName) {
        String response = context.getResources().getString(R.string.confirm_without_name);
        if (toName != null && !toName.equals("")) {
            String template = context.getResources().getString(R.string.confirm_with_name);
            response = template.replaceAll("\\{\\{name\\}\\}", toName);
        }
        response = response.replaceAll("\\{\\{number\\}\\}", phoneNumber(toNumber));
        return response;
    }
}
