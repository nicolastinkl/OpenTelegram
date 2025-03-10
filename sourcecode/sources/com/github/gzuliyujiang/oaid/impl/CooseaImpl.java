package com.github.gzuliyujiang.oaid.impl;

import android.app.KeyguardManager;
import android.content.Context;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import java.util.Objects;

/* loaded from: classes.dex */
public class CooseaImpl implements IOAID {
    private final Context context;
    private final KeyguardManager keyguardManager;

    public CooseaImpl(Context context) {
        this.context = context;
        this.keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        KeyguardManager keyguardManager;
        if (this.context == null || (keyguardManager = this.keyguardManager) == null) {
            return false;
        }
        try {
            Object invoke = keyguardManager.getClass().getDeclaredMethod("isSupported", new Class[0]).invoke(this.keyguardManager, new Object[0]);
            Objects.requireNonNull(invoke);
            return ((Boolean) invoke).booleanValue();
        } catch (Exception e) {
            OAIDLog.print(e);
            return false;
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        KeyguardManager keyguardManager = this.keyguardManager;
        if (keyguardManager == null) {
            iGetter.onOAIDGetError(new OAIDException("KeyguardManager not found"));
            return;
        }
        try {
            Object invoke = keyguardManager.getClass().getDeclaredMethod("obtainOaid", new Class[0]).invoke(this.keyguardManager, new Object[0]);
            if (invoke == null) {
                throw new OAIDException("OAID obtain failed");
            }
            String obj = invoke.toString();
            OAIDLog.print("OAID obtain success: " + obj);
            iGetter.onOAIDGetComplete(obj);
        } catch (Exception e) {
            OAIDLog.print(e);
        }
    }
}
