package us.jwf.buttdial.models;

/**
 *
 * CallLog.Calls.CACHED_NAME,
 * CallLog.Calls.NUMBER,
 * CallLog.Calls.DATE,
 * CallLog.Calls.DURATION
 */
public class Call {
    public String name;
    public String number;
    public long date;
    public long duration;

    public Call(String name, String number, long date, long duration) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.duration = duration;
    }
}
