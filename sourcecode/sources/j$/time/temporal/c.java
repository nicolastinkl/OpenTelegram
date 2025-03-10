package j$.time.temporal;

/* loaded from: classes2.dex */
abstract /* synthetic */ class c {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[i.values().length];
        a = iArr;
        try {
            iArr[i.WEEK_BASED_YEARS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[i.QUARTER_YEARS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
    }
}
