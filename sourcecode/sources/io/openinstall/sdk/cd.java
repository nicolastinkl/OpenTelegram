package io.openinstall.sdk;

/* loaded from: classes.dex */
public class cd extends IllegalArgumentException {
    public cd(bn bnVar) {
        super("'" + bnVar + "' is not an absolute name");
    }
}
