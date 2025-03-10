package com.hihonor.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class AdvertisingIdClient {

    public static final class Info {
        public String id;
        public boolean isLimit;
    }

    public static Info getAdvertisingIdInfo(Context context) {
        a aVar = new a();
        aVar.b = context;
        if (!aVar.a(context)) {
            throw new IOException("Service not found or advertisingId not available");
        }
        try {
            String string = Settings.Global.getString(context.getContentResolver(), "oaid_limit_state");
            String string2 = Settings.Global.getString(context.getContentResolver(), "oaid");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                boolean parseBoolean = Boolean.parseBoolean(string);
                Info info = new Info();
                info.isLimit = parseBoolean;
                info.id = string2;
                Log.i("AdvertisingIdPlatform", "getAdvertisingIdInfo cache isLimit=" + parseBoolean + "id " + string2);
                return info;
            }
        } catch (Throwable th) {
            Log.e("AdvertisingIdPlatform", "getAdvertisingIdInfo cache error=" + th);
        }
        try {
            try {
                Log.i("AdvertisingIdPlatform", "bindService start");
                Intent intent = new Intent();
                intent.setAction("com.hihonor.id.HnOaIdService");
                intent.setPackage("com.hihonor.id");
                context.bindService(intent, aVar, 1);
                aVar.e.await(2000L, TimeUnit.MILLISECONDS);
                aVar.a();
                return aVar.a;
            } catch (Exception e) {
                Log.e("AdvertisingIdPlatform", "getAdvertisingIdInfo error=" + e.getMessage());
                aVar.a();
                return null;
            }
        } catch (Throwable th2) {
            aVar.a();
            throw th2;
        }
    }

    public static boolean isAdvertisingIdAvailable(Context context) {
        return new a().a(context);
    }
}
