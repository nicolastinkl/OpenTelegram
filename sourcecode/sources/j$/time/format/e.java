package j$.time.format;

/* loaded from: classes2.dex */
abstract /* synthetic */ class e {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[A.values().length];
        a = iArr;
        try {
            iArr[A.EXCEEDS_PAD.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[A.ALWAYS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[A.NORMAL.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[A.NOT_NEGATIVE.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
    }
}
