package com.google.android.gms.location;

import com.google.android.gms.common.api.CommonStatusCodes;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* loaded from: classes.dex */
public final class GeofenceStatusCodes extends CommonStatusCodes {
    public static int zza(int i) {
        if (i == 0) {
            return i;
        }
        if (i < 1000 || i >= 1006) {
            return 13;
        }
        return i;
    }
}
