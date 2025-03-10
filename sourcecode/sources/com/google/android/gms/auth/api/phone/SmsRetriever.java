package com.google.android.gms.auth.api.phone;

import android.content.Context;
import com.google.android.gms.internal.p001authapiphone.zzab;

/* compiled from: com.google.android.gms:play-services-auth-api-phone@@17.5.1 */
/* loaded from: classes.dex */
public final class SmsRetriever {
    public static SmsRetrieverClient getClient(Context context) {
        return new zzab(context);
    }
}
