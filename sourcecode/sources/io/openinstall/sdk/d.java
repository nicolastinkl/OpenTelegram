package io.openinstall.sdk;

import com.fm.openinstall.listener.ResultCallback;
import com.fm.openinstall.model.Error;

/* loaded from: classes.dex */
class d implements fb {
    final /* synthetic */ ResultCallback a;

    d(a aVar, ResultCallback resultCallback) {
        this.a = resultCallback;
    }

    @Override // io.openinstall.sdk.fb
    public void a(ew ewVar) {
        ResultCallback resultCallback = this.a;
        if (resultCallback != null) {
            resultCallback.onResult(null, Error.fromInner(ewVar.c()));
        }
    }
}
