package io.openinstall.sdk;

/* loaded from: classes.dex */
public class eq implements es {
    private final Exception a;

    public eq(Exception exc) {
        this.a = exc;
    }

    @Override // io.openinstall.sdk.es
    public boolean e() {
        return true;
    }

    @Override // io.openinstall.sdk.es
    public String f() {
        return this.a.getMessage();
    }
}
