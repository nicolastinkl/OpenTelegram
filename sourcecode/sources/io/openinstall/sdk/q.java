package io.openinstall.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.fm.openinstall.Configuration;

/* loaded from: classes.dex */
public class q extends ez {
    private final Configuration a;
    private final Context b;

    public q(Context context, Configuration configuration) {
        this.b = context;
        this.a = configuration;
    }

    @Override // io.openinstall.sdk.ez
    public boolean a() {
        return true;
    }

    @Override // io.openinstall.sdk.ez
    protected String b() {
        return "oa";
    }

    @Override // io.openinstall.sdk.ez
    protected String c() {
        return "effj";
    }

    @Override // io.openinstall.sdk.ez
    protected String d() {
        String str;
        if (Configuration.isPresent(this.a.getOaid())) {
            str = this.a.getOaid();
        } else {
            try {
                w wVar = new w();
                str = wVar.a() ? wVar.a(this.b) : ab.a(this.b).a(this.b);
            } catch (Exception e) {
                if (ga.a) {
                    ga.c("getOAID throw exception : %s", e.getMessage());
                }
                str = null;
            }
        }
        if (!TextUtils.isEmpty(str) && !str.equals("0000000000000000") && !str.equals("00000000-0000-0000-0000-000000000000") && !str.equals("00000000000000000000000000000000") && !str.equals("0000000000000000000000000000000000000000000000000000000000000000")) {
            return str;
        }
        if (ga.a) {
            ga.b("input oaid is invalid", new Object[0]);
        }
        return null;
    }
}
