package com.google.mlkit.common.sdkinternal;

import java.util.ArrayDeque;
import java.util.Deque;

/* compiled from: com.google.mlkit:common@@17.0.0 */
/* loaded from: classes.dex */
final class zzj extends ThreadLocal<Deque<Runnable>> {
    zzj() {
    }

    @Override // java.lang.ThreadLocal
    protected final /* synthetic */ Deque<Runnable> initialValue() {
        return new ArrayDeque();
    }
}
