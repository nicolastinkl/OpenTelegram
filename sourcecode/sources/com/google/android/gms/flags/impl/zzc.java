package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
final class zzc implements Callable<Boolean> {
    private final /* synthetic */ SharedPreferences zzo;
    private final /* synthetic */ String zzp;
    private final /* synthetic */ Boolean zzq;

    zzc(SharedPreferences sharedPreferences, String str, Boolean bool) {
        this.zzo = sharedPreferences;
        this.zzp = str;
        this.zzq = bool;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ Boolean call() throws Exception {
        return Boolean.valueOf(this.zzo.getBoolean(this.zzp, this.zzq.booleanValue()));
    }
}
