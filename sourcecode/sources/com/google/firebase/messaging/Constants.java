package com.google.firebase.messaging;

import android.os.Bundle;
import androidx.collection.ArrayMap;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-messaging@@22.0.0 */
/* loaded from: classes.dex */
public final class Constants {
    public static final long WAKE_LOCK_ACQUIRE_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(3);

    /* compiled from: com.google.firebase:firebase-messaging@@22.0.0 */
    public static final class MessagePayloadKeys {
        public static ArrayMap<String, String> extractDeveloperDefinedPayload(Bundle bundle) {
            ArrayMap<String, String> arrayMap = new ArrayMap<>();
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj instanceof String) {
                    String str2 = (String) obj;
                    if (!str.startsWith("google.") && !str.startsWith("gcm.") && !str.equals("from") && !str.equals("message_type") && !str.equals("collapse_key")) {
                        arrayMap.put(str, str2);
                    }
                }
            }
            return arrayMap;
        }
    }
}
