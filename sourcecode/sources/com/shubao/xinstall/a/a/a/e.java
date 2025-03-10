package com.shubao.xinstall.a.a.a;

import android.content.SharedPreferences;
import com.shubao.xinstall.a.f.o;
import com.shubao.xinstall.a.f.v;
import com.xinstall.listener.XInstallListener;
import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;
import org.json.JSONException;

/* loaded from: classes.dex */
public final class e implements com.shubao.xinstall.a.d.b {
    private XInstallListener a;
    private com.shubao.xinstall.a.a.g b;
    private com.shubao.xinstall.a.a.b c;

    public e(XInstallListener xInstallListener, com.shubao.xinstall.a.a.b bVar) {
        this.a = xInstallListener;
        this.b = bVar.f();
        this.c = bVar;
    }

    @Override // com.shubao.xinstall.a.d.b
    public final void a(com.shubao.xinstall.a.b.d dVar) {
        if (!dVar.b.equals("0000")) {
            if (o.a) {
                o.c("获取安装传参失败:" + dVar.d);
            }
            if (this.a != null) {
                this.a.onInstallFinish(null, new XAppError(dVar.b, dVar.d));
                return;
            }
            return;
        }
        try {
            if (this.c.d().c().booleanValue()) {
                if (this.a != null) {
                    this.a.onInstallFinish(null, new XAppError(dVar.b, dVar.d));
                    return;
                }
                return;
            }
            if (o.a) {
                o.a("获取安装传参成功:" + dVar.c);
            }
            XAppData a = v.a(dVar.c);
            if (this.a != null) {
                a.setFirstFetch(this.b.b());
                try {
                    SharedPreferences.Editor edit = this.b.a.edit();
                    edit.putBoolean("first_fetch", false);
                    edit.apply();
                } catch (Exception unused) {
                }
                this.a.onInstallFinish(a, null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            if (this.a != null) {
                this.a.onInstallFinish(null, new XAppError(XAppError.JSON_EXCEPTION, e.getMessage()));
            }
        }
    }
}
