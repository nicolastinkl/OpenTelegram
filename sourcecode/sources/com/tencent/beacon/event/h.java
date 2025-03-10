package com.tencent.beacon.event;

import com.tencent.beacon.event.open.EventResult;

/* compiled from: IEventManager.java */
/* loaded from: classes.dex */
public interface h {
    EventResult a(String str, EventBean eventBean);

    void a();

    void a(boolean z);

    boolean a(Runnable runnable);

    EventResult b(String str, EventBean eventBean);

    void b();

    void c();
}
