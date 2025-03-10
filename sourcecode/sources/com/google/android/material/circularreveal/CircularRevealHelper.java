package com.google.android.material.circularreveal;

import android.os.Build;

/* loaded from: classes.dex */
public class CircularRevealHelper {
    public static final int STRATEGY;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            STRATEGY = 2;
        } else if (i >= 18) {
            STRATEGY = 1;
        } else {
            STRATEGY = 0;
        }
    }
}
