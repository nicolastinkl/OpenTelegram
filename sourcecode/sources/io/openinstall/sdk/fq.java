package io.openinstall.sdk;

import android.content.Context;
import io.openinstall.sdk.ew;
import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class fq extends fp {
    public fq(az azVar, fb fbVar) {
        super(azVar, fbVar);
    }

    @Override // io.openinstall.sdk.et
    protected String k() {
        return "apk";
    }

    @Override // io.openinstall.sdk.fp
    protected ew o() {
        Context c = aw.a().c();
        String str = c.getApplicationInfo().sourceDir;
        String str2 = c.getFilesDir() + File.separator + c.getPackageName() + ".apk";
        try {
            ea.a((byte[]) null, new File(str), new File(str2));
            return ew.a(str2);
        } catch (IOException e) {
            if (ga.a) {
                e.printStackTrace();
            }
            return ew.a.REQUEST_FAIL.a(e.getMessage());
        }
    }
}
