package io.openinstall.sdk;

import android.net.Uri;
import com.fm.openinstall.listener.AppWakeUpListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import io.openinstall.sdk.ew;
import org.json.JSONException;

/* loaded from: classes.dex */
class b implements fb {
    final /* synthetic */ AppWakeUpListener a;
    final /* synthetic */ Uri b;
    final /* synthetic */ a c;

    b(a aVar, AppWakeUpListener appWakeUpListener, Uri uri) {
        this.c = aVar;
        this.a = appWakeUpListener;
        this.b = uri;
    }

    @Override // io.openinstall.sdk.fb
    public void a(ew ewVar) {
        AppData a;
        if (ewVar.c() != null) {
            if (ga.a) {
                ga.c("decodeWakeUp fail : %s", ewVar.c());
            }
            AppWakeUpListener appWakeUpListener = this.a;
            if (appWakeUpListener != null) {
                appWakeUpListener.onWakeUpFinish(null, Error.fromInner(ewVar.c()));
                return;
            }
            return;
        }
        String b = ewVar.b();
        if (ga.a) {
            ga.a("decodeWakeUp success : %s", b);
        }
        try {
            a = this.c.a(b);
            AppWakeUpListener appWakeUpListener2 = this.a;
            if (appWakeUpListener2 != null) {
                appWakeUpListener2.onWakeUpFinish(a, null);
            }
            if (a.isEmpty()) {
                return;
            }
            this.c.a(this.b);
        } catch (JSONException e) {
            if (ga.a) {
                ga.c("decodeWakeUp error : %s", e.toString());
            }
            AppWakeUpListener appWakeUpListener3 = this.a;
            if (appWakeUpListener3 != null) {
                appWakeUpListener3.onWakeUpFinish(null, Error.fromInner(ew.a.REQUEST_EXCEPTION));
            }
        }
    }
}
