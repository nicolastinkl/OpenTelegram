package io.openinstall.sdk;

import android.content.Context;
import com.fm.openinstall.Configuration;
import io.openinstall.sdk.t;

/* loaded from: classes.dex */
public class m extends ez {
    private final Context a;
    private final Configuration b;

    public m(Context context, Configuration configuration) {
        this.a = context;
        this.b = configuration;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return true;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "ga";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "feem";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        if (Configuration.isPresent(this.b.getGaid())) {
            return this.b.getGaid();
        }
        t.a a = t.a(this.a);
        if (a == null || a.b()) {
            return null;
        }
        return a.a();
    }
}
