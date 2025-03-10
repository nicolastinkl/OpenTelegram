package io.openinstall.sdk;

/* loaded from: classes.dex */
public class ei extends Exception {
    int a;
    String b;

    public ei(int i, String str) {
        super(i + " - " + str);
        this.a = i;
        this.b = str;
    }
}
