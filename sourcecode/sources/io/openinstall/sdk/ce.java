package io.openinstall.sdk;

import java.io.IOException;

/* loaded from: classes.dex */
public class ce extends IOException {
    public ce() {
    }

    public ce(String str) {
        super(str);
    }

    public ce(String str, String str2) {
        super("'" + str + "': " + str2);
    }

    public ce(String str, String str2, Exception exc) {
        super("'" + str + "': " + str2, exc);
    }
}
