package com.tencent.beacon.event;

import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/* compiled from: EventReportCallback.java */
/* loaded from: classes.dex */
final class f implements Callback<byte[]> {
    private final Set<Long> a;
    private final String b;
    private final String c;
    private long d = new Date().getTime();
    private g e;
    private String f;
    private com.tencent.beacon.event.a.a g;

    f(g gVar, String str, com.tencent.beacon.event.a.a aVar, Set<Long> set, String str2) {
        this.e = gVar;
        this.f = str;
        this.g = aVar;
        this.a = new HashSet(set);
        this.b = "[EventReport(" + str + ")]";
        this.c = str2;
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    public void onFailure(com.tencent.beacon.base.net.d dVar) {
        com.tencent.beacon.base.util.c.a(this.b, 3, "send failure reason: %s. LogID: %s.", dVar.toString(), this.c);
        this.e.a(this.a);
    }

    @Override // com.tencent.beacon.base.net.call.Callback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onResponse(byte[] bArr) {
        com.tencent.beacon.base.util.c.a(this.b, 3, "report success! sendingID will delete this time's IDs. offer task: %s! ", Boolean.valueOf(((EventModule) com.tencent.beacon.a.c.c.d().a(ModuleName.EVENT)).d().a(new e(this, new Date().getTime() - this.d))));
        if (this.a.size() >= this.e.a()) {
            com.tencent.beacon.a.b.a.a().a(this.e);
        }
    }
}
