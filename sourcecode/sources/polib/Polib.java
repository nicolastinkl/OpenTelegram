package polib;

import go.Seq;

/* loaded from: classes4.dex */
public abstract class Polib {
    private static native void _init();

    public static native void init(String str);

    public static native String leadIp(String str);

    public static native void start();

    public static native void stop();

    public static void touch() {
    }

    static {
        Seq.touch();
        _init();
    }

    private Polib() {
    }
}
