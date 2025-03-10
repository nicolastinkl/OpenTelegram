package androidx.constraintlayout.motion.utils;

/* loaded from: classes.dex */
public class Easing {
    public static String[] NAMED_EASING;
    String str = "identity";

    static {
        new Easing();
        NAMED_EASING = new String[]{"standard", "accelerate", "decelerate", "linear"};
    }

    public String toString() {
        return this.str;
    }
}
