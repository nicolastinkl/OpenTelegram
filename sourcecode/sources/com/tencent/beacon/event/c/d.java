package com.tencent.beacon.event.c;

import android.os.Build;
import android.text.TextUtils;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.event.EventBean;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.pack.EventRecordV2;
import com.tencent.beacon.pack.RequestPackageV2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: EventUtils.java */
/* loaded from: classes.dex */
public final class d {
    public static String a(String str) {
        if (str != null && str.trim().length() != 0) {
            if (g(str.trim())) {
                String trim = str.trim();
                return trim.length() > 16 ? trim.substring(0, 16) : trim;
            }
            com.tencent.beacon.base.util.c.e("[core] channelID should be Numeric! channelID:" + str, new Object[0]);
        }
        return "unknown";
    }

    public static boolean a(int i) {
        return i != 0;
    }

    private static EventRecordV2 b(EventBean eventBean) {
        if (eventBean == null) {
            return null;
        }
        EventRecordV2 eventRecordV2 = new EventRecordV2();
        eventRecordV2.appKey = eventBean.getAppKey();
        eventRecordV2.apn = eventBean.getApn() != null ? eventBean.getApn() : "";
        eventRecordV2.srcIp = eventBean.getSrcIp() != null ? eventBean.getSrcIp() : "";
        eventRecordV2.eventCode = eventBean.getEventCode();
        eventRecordV2.valueType = eventBean.getValueType();
        eventRecordV2.mapValue = eventBean.getEventValue();
        eventRecordV2.byteValue = eventBean.getByteValue();
        eventRecordV2.eventTime = eventBean.getEventTime();
        eventRecordV2.eventResult = eventBean.getEventResult();
        eventRecordV2.eventType = eventBean.getEventType();
        eventRecordV2.reserved = eventBean.getReserved();
        return eventRecordV2;
    }

    public static String c(String str) {
        return (str == null || str.length() == 0) ? "" : str;
    }

    public static String d(String str) {
        return (str == null || str.length() == 0) ? "DefaultPageID" : str.length() > 50 ? str.substring(0, 50) : str;
    }

    public static String e(String str) {
        if (str == null || str.length() == 0) {
            return "10000";
        }
        String trim = str.replace('|', '_').trim();
        if (g(trim)) {
            if (trim.length() < 5) {
                com.tencent.beacon.base.util.c.e("[core] userID length should < 5!", new Object[0]);
            }
            return trim.length() > 128 ? trim.substring(0, 128) : trim;
        }
        com.tencent.beacon.base.util.c.e("[core] userID should be ASCII code in 32-126! userID:" + str, new Object[0]);
        return "10000";
    }

    public static BeaconModule f(String str) throws Exception {
        return (BeaconModule) Class.forName(str).newInstance();
    }

    private static boolean g(String str) {
        int length = str.length();
        boolean z = true;
        while (true) {
            length--;
            if (length < 0) {
                return z;
            }
            char charAt = str.charAt(length);
            if (charAt < ' ' || charAt >= 127) {
                z = false;
            }
        }
    }

    public static RequestPackageV2 a(EventBean eventBean) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(eventBean);
        return a(arrayList);
    }

    public static RequestPackageV2 a(List<EventBean> list) {
        RequestPackageV2 requestPackageV2 = new RequestPackageV2();
        requestPackageV2.appVersion = com.tencent.beacon.a.c.b.a();
        requestPackageV2.common = a();
        ArrayList<EventRecordV2> arrayList = new ArrayList<>();
        Iterator<EventBean> it = list.iterator();
        while (it.hasNext()) {
            EventRecordV2 b = b(it.next());
            if (b != null) {
                arrayList.add(b);
            }
        }
        requestPackageV2.events = arrayList;
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        requestPackageV2.mainAppKey = d.f();
        requestPackageV2.model = com.tencent.beacon.a.c.f.e().h();
        requestPackageV2.osVersion = com.tencent.beacon.a.c.e.l().s();
        requestPackageV2.packageName = com.tencent.beacon.a.c.b.b();
        requestPackageV2.platformId = d.h();
        requestPackageV2.sdkId = d.i();
        requestPackageV2.sdkVersion = d.j();
        requestPackageV2.reserved = "";
        return requestPackageV2;
    }

    public static String b(String str) {
        if (TextUtils.isEmpty(str)) {
            if (com.tencent.beacon.base.util.e.a.get()) {
                com.tencent.beacon.a.b.g.e().a("101", "eventCode == null");
                com.tencent.beacon.base.util.e.a("eventCode == null");
            }
            return "";
        }
        String trim = str.replace('|', '_').trim();
        if (g(trim)) {
            if (trim.length() <= 128) {
                return trim;
            }
            String str2 = str + " length > 128.";
            com.tencent.beacon.a.b.g.e().a("101", str2);
            com.tencent.beacon.base.util.e.a(str2);
            return trim.substring(0, 128);
        }
        String str3 = str + " is not ASCII";
        com.tencent.beacon.a.b.g.e().a("101", str3);
        com.tencent.beacon.base.util.e.a(str3);
        return "";
    }

    private static Map<String, String> a() {
        HashMap hashMap = new HashMap();
        com.tencent.beacon.a.c.e l = com.tencent.beacon.a.c.e.l();
        com.tencent.beacon.a.c.c d = com.tencent.beacon.a.c.c.d();
        hashMap.put("A31", "" + l.p());
        hashMap.put("A67", com.tencent.beacon.a.c.b.c(d.c()));
        hashMap.put("A76", com.tencent.beacon.a.c.b.d());
        hashMap.put("A89", l.a(d.c()));
        hashMap.put("A52", String.valueOf(l.y()));
        hashMap.put("A58", l.m() ? "Y" : "N");
        hashMap.put("A12", l.n());
        hashMap.put("A17", l.u());
        com.tencent.beacon.a.c.f e = com.tencent.beacon.a.c.f.e();
        hashMap.put("A10", e.h());
        hashMap.put("A2", "" + e.b());
        hashMap.put("A4", e.d());
        hashMap.put("A6", e.f());
        hashMap.put("A7", e.a());
        hashMap.put("A20", e.j());
        hashMap.put("A69", e.k());
        hashMap.put("A9", Build.BRAND);
        hashMap.put("A158", l.d());
        return hashMap;
    }

    public static int a(EventType eventType) {
        int i = c.a[eventType.ordinal()];
        if (i == 3 || i == 4) {
            return 1;
        }
        if (i != 5) {
            return i != 6 ? 0 : 3;
        }
        return 2;
    }

    public static JceRequestEntity a(List<EventBean> list, boolean z) {
        return JceRequestEntity.builder().a(RequestType.EVENT).a(z ? 2 : 1).b(com.tencent.beacon.base.net.b.b.a(false)).a(com.tencent.beacon.base.net.b.b.a(true), 8081).a(com.tencent.beacon.a.c.c.d().f()).a("version", "v2").a(a(list)).a();
    }
}
