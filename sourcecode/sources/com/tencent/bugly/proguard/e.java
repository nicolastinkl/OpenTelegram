package com.tencent.bugly.proguard;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
/* loaded from: classes.dex */
public final class e extends d {
    static HashMap<String, byte[]> h;
    static HashMap<String, HashMap<String, byte[]>> i;
    protected g g;
    private int j;

    public e() {
        g gVar = new g();
        this.g = gVar;
        this.j = 0;
        gVar.a = (short) 2;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final <T> void a(String str, T t) {
        if (str.startsWith(".")) {
            throw new IllegalArgumentException("put name can not startwith . , now is ".concat(str));
        }
        super.a(str, (String) t);
    }

    @Override // com.tencent.bugly.proguard.d
    public final void b() {
        super.b();
        this.g.a = (short) 3;
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final byte[] a() {
        g gVar = this.g;
        if (gVar.a == 2) {
            if (gVar.e.equals("")) {
                throw new IllegalArgumentException("servantName can not is null");
            }
            if (this.g.f.equals("")) {
                throw new IllegalArgumentException("funcName can not is null");
            }
        } else {
            if (gVar.e == null) {
                gVar.e = "";
            }
            if (gVar.f == null) {
                gVar.f = "";
            }
        }
        l lVar = new l(0);
        lVar.a(this.c);
        if (this.g.a == 2) {
            lVar.a((Map) this.a, 0);
        } else {
            lVar.a((Map) ((d) this).e, 0);
        }
        this.g.g = n.a(lVar.a);
        l lVar2 = new l(0);
        lVar2.a(this.c);
        this.g.a(lVar2);
        byte[] a = n.a(lVar2.a);
        int length = a.length + 4;
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putInt(length).put(a).flip();
        return allocate.array();
    }

    @Override // com.tencent.bugly.proguard.d, com.tencent.bugly.proguard.c
    public final void a(byte[] bArr) {
        if (bArr.length < 4) {
            throw new IllegalArgumentException("decode package must include size head");
        }
        try {
            k kVar = new k(bArr, (byte) 0);
            kVar.a(this.c);
            this.g.a(kVar);
            g gVar = this.g;
            if (gVar.a == 3) {
                k kVar2 = new k(gVar.g);
                kVar2.a(this.c);
                if (h == null) {
                    HashMap<String, byte[]> hashMap = new HashMap<>();
                    h = hashMap;
                    hashMap.put("", new byte[0]);
                }
                ((d) this).e = kVar2.a((Map) h, 0, false);
                return;
            }
            k kVar3 = new k(gVar.g);
            kVar3.a(this.c);
            if (i == null) {
                i = new HashMap<>();
                HashMap<String, byte[]> hashMap2 = new HashMap<>();
                hashMap2.put("", new byte[0]);
                i.put("", hashMap2);
            }
            this.a = kVar3.a((Map) i, 0, false);
            this.b = new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final void b(String str) {
        this.g.e = str;
    }

    public final void c(String str) {
        this.g.f = str;
    }

    public final void c() {
        this.g.d = 1;
    }
}
