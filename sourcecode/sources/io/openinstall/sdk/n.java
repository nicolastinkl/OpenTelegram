package io.openinstall.sdk;

import com.fm.openinstall.Configuration;

/* loaded from: classes.dex */
public class n extends ez {
    private final Configuration a;
    private final s b;

    public n(Configuration configuration, s sVar) {
        this.a = configuration;
        this.b = sVar;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return false;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "im";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "xefb";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        if (Configuration.isPresent(this.a.getImei())) {
            return this.a.getImei();
        }
        if (this.a.isImeiDisabled()) {
            return null;
        }
        return this.b.b();
    }
}
