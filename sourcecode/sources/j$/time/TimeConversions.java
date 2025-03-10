package j$.time;

/* loaded from: classes2.dex */
public class TimeConversions {
    public static Instant convert(java.time.Instant instant) {
        if (instant == null) {
            return null;
        }
        return Instant.p(instant.getEpochSecond(), instant.getNano());
    }
}
