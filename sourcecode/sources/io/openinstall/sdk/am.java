package io.openinstall.sdk;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class am implements aa {
    @Override // io.openinstall.sdk.aa
    public String a(Context context) {
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            Object invoke = cls.getMethod("getOAID", Context.class).invoke(cls.newInstance(), context);
            if (invoke != null) {
                return (String) invoke;
            }
            return null;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
            return null;
        }
    }
}
