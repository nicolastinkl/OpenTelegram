package io.openinstall.sdk;

import com.fm.openinstall.Configuration;

/* loaded from: classes.dex */
public class p extends ez {
    private final Configuration a;
    private final s b;

    public p(Configuration configuration, s sVar) {
        this.a = configuration;
        this.b = sVar;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return false;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "ma";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "dajg";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        if (Configuration.isPresent(this.a.getMacAddress())) {
            return this.a.getMacAddress();
        }
        if (this.a.isMacDisabled()) {
            return null;
        }
        return this.b.a();
    }
}
