package com.shubao.xinstall.a.a.a;

import android.net.Uri;
import com.shubao.xinstall.a.f.o;
import com.shubao.xinstall.a.f.v;
import com.xinstall.listener.XWakeUpListener;
import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;
import org.json.JSONException;

/* loaded from: classes.dex */
public final class i implements com.shubao.xinstall.a.d.b {
    private XWakeUpListener a;
    private Uri b;
    private com.shubao.xinstall.a.a.b c;

    public i(XWakeUpListener xWakeUpListener, Uri uri, com.shubao.xinstall.a.a.b bVar) {
        this.a = xWakeUpListener;
        this.b = uri;
        this.c = bVar;
    }

    @Override // com.shubao.xinstall.a.d.b
    public final void a(com.shubao.xinstall.a.b.d dVar) {
        if (dVar == null) {
            if (this.a != null) {
                this.a.onWakeUpFinish(null, new XAppError(XAppError.ERROR_GETWAKEUPPARAMS_REQUEST_FAIL, dVar.d));
                return;
            }
            return;
        }
        com.shubao.xinstall.a.e.b bVar = dVar.a;
        if (bVar == com.shubao.xinstall.a.e.b.SUCCESS) {
            if (o.a) {
                o.a("获取唤醒参数成功");
            }
            try {
                XAppData a = v.a(dVar.c);
                XWakeUpListener xWakeUpListener = this.a;
                if (xWakeUpListener != null) {
                    xWakeUpListener.onWakeUpFinish(a, null);
                    return;
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                if (this.a != null) {
                    this.a.onWakeUpFinish(null, new XAppError(XAppError.JSON_EXCEPTION, e.getMessage()));
                    return;
                }
                return;
            }
        }
        if (bVar == com.shubao.xinstall.a.e.b.RESP588) {
            o.c("获取唤醒参数失败: " + dVar.d);
            this.c.j().a();
            return;
        }
        if (o.a) {
            o.c("获取唤醒参数失败:" + dVar.d);
        }
        if (this.a != null) {
            if (dVar.b.equals(XAppError.NO_PERMISSION)) {
                this.a.onWakeUpFinish(null, new XAppError(XAppError.NO_PERMISSION, dVar.d));
            } else {
                this.a.onWakeUpFinish(null, new XAppError(XAppError.ERROR_GETWAKEUPPARAMS_REQUEST_FAIL, dVar.d));
            }
        }
    }
}
