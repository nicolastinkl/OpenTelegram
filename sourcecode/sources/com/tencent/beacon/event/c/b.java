package com.tencent.beacon.event.c;

import com.tencent.beacon.base.net.a.c;
import com.tencent.beacon.event.EventBean;

/* compiled from: EventConverterFactory.java */
/* loaded from: classes.dex */
public final class b extends c.a<EventBean, com.tencent.beacon.event.a.b> {
    private final C0019b a = new C0019b();
    private final a b = new a();

    /* compiled from: EventConverterFactory.java */
    static final class a implements com.tencent.beacon.base.net.a.c<EventBean, com.tencent.beacon.event.a.b> {
        a() {
        }

        @Override // com.tencent.beacon.base.net.a.c
        public com.tencent.beacon.event.a.b a(EventBean eventBean) {
            com.tencent.beacon.event.a.b bVar = new com.tencent.beacon.event.a.b();
            bVar.b = eventBean.getEventTime();
            bVar.d = eventBean.getAppKey();
            byte[] a = com.tencent.beacon.base.util.b.a(eventBean);
            bVar.e = a;
            if (a != null) {
                bVar.c = a.length;
            }
            return bVar;
        }
    }

    /* compiled from: EventConverterFactory.java */
    /* renamed from: com.tencent.beacon.event.c.b$b, reason: collision with other inner class name */
    static final class C0019b implements com.tencent.beacon.base.net.a.c<com.tencent.beacon.event.a.b, EventBean> {
        C0019b() {
        }

        @Override // com.tencent.beacon.base.net.a.c
        public EventBean a(com.tencent.beacon.event.a.b bVar) {
            Object a = com.tencent.beacon.base.util.b.a(bVar.e);
            if (a == null || !(a instanceof EventBean)) {
                return null;
            }
            EventBean eventBean = (EventBean) a;
            eventBean.setCid(bVar.a);
            return eventBean;
        }
    }

    private b() {
    }

    public static b a() {
        return new b();
    }

    public com.tencent.beacon.base.net.a.c<EventBean, com.tencent.beacon.event.a.b> b() {
        return this.b;
    }

    public com.tencent.beacon.base.net.a.c<com.tencent.beacon.event.a.b, EventBean> c() {
        return this.a;
    }
}
