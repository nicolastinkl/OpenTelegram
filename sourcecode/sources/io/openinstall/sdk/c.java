package io.openinstall.sdk;

import com.fm.openinstall.listener.AppInstallListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import io.openinstall.sdk.ew;
import org.json.JSONException;

/* loaded from: classes.dex */
class c implements fb {
    final /* synthetic */ AppInstallListener a;
    final /* synthetic */ a b;

    c(a aVar, AppInstallListener appInstallListener) {
        this.b = aVar;
        this.a = appInstallListener;
    }

    @Override // io.openinstall.sdk.fb
    public void a(ew ewVar) {
        AppData a;
        if (ewVar.c() != null) {
            if (ga.a) {
                ga.c("decodeInstall fail : %s", ewVar.c());
            }
            AppInstallListener appInstallListener = this.a;
            if (appInstallListener != null) {
                appInstallListener.onInstallFinish(null, Error.fromInner(ewVar.c()));
                return;
            }
            return;
        }
        if (ga.a) {
            ga.a("decodeInstall success : %s", ewVar.b());
        }
        try {
            a = this.b.a(ewVar.b());
            AppInstallListener appInstallListener2 = this.a;
            if (appInstallListener2 != null) {
                appInstallListener2.onInstallFinish(a, null);
            }
        } catch (JSONException e) {
            if (ga.a) {
                ga.c("decodeInstall error : %s", e.toString());
            }
            AppInstallListener appInstallListener3 = this.a;
            if (appInstallListener3 != null) {
                appInstallListener3.onInstallFinish(null, Error.fromInner(ew.a.REQUEST_EXCEPTION));
            }
        }
    }
}
