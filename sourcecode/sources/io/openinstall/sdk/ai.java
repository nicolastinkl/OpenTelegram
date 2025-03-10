package io.openinstall.sdk;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class ai implements aa {
    @Override // io.openinstall.sdk.aa
    public String a(Context context) {
        Bundle call;
        Uri parse = Uri.parse("content://cn.nubia.identity/identity");
        int i = Build.VERSION.SDK_INT;
        ContentResolver contentResolver = context.getContentResolver();
        if (i >= 17) {
            ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(parse);
            if (acquireContentProviderClient != null) {
                try {
                    call = acquireContentProviderClient.call("getOAID", null, null);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    call = null;
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    acquireContentProviderClient.close();
                } else {
                    acquireContentProviderClient.release();
                }
            } else {
                call = null;
            }
        } else {
            call = contentResolver.call(parse, "getOAID", (String) null, (Bundle) null);
        }
        if (call == null || call.getInt("code", -1) != 0) {
            return null;
        }
        return call.getString("id", "");
    }
}
