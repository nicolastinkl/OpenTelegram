package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzi implements Callable<String> {
    private final /* synthetic */ SharedPreferences zzo;
    private final /* synthetic */ String zzp;
    private final /* synthetic */ String zzt;

    zzi(SharedPreferences sharedPreferences, String str, String str2) {
        this.zzo = sharedPreferences;
        this.zzp = str;
        this.zzt = str2;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ String call() throws Exception {
        return this.zzo.getString(this.zzp, this.zzt);
    }
}
