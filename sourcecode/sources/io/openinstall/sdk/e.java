package io.openinstall.sdk;

import com.fm.openinstall.listener.ResultCallback;
import com.fm.openinstall.model.Error;
import java.io.File;

/* loaded from: classes.dex */
class e implements fb {
    final /* synthetic */ ResultCallback a;

    e(a aVar, ResultCallback resultCallback) {
        this.a = resultCallback;
    }

    @Override // io.openinstall.sdk.fb
    public void a(ew ewVar) {
        ResultCallback resultCallback = this.a;
        if (resultCallback != null) {
            resultCallback.onResult(new File(ewVar.b()), Error.fromInner(ewVar.c()));
        }
    }
}
