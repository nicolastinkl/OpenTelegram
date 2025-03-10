package com.shubao.xinstall.a.c;

import java.io.File;

/* loaded from: classes.dex */
public final class c {
    public static String a(File file) {
        try {
            return g.a(file);
        } catch (Exception unused) {
            System.out.println("APK : " + file.getAbsolutePath() + " not have channel info from Zip Comment");
            return null;
        }
    }
}
