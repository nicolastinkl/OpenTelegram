package com.tencent.beacon.pack;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: PacketUtil.java */
/* loaded from: classes.dex */
public class c {
    private static HashMap<String, byte[]> a;
    public final RequestPacket b = new RequestPacket();
    public HashMap<String, byte[]> c = new HashMap<>();
    public String d = "GBK";
    a e = new a();

    static {
        HashMap<String, byte[]> hashMap = new HashMap<>();
        a = hashMap;
        hashMap.put("", new byte[0]);
    }

    public void a(int i) {
        this.b.iRequestId = i;
    }

    public void b(String str) {
        this.b.sServantName = str;
    }

    public void a(String str) {
        this.b.sFuncName = str;
    }

    public <T> void b(String str, T t) {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        b bVar = new b();
        bVar.a(this.d);
        bVar.a(t, 0);
        this.c.put(str, a(bVar.a()));
    }

    public byte[] a() {
        b bVar = new b(0);
        bVar.a(this.d);
        bVar.a((Map) this.c, 0);
        RequestPacket requestPacket = this.b;
        requestPacket.iVersion = (short) 3;
        requestPacket.sBuffer = a(bVar.a());
        b bVar2 = new b(0);
        bVar2.a(this.d);
        this.b.writeTo(bVar2);
        byte[] a2 = a(bVar2.a());
        int length = a2.length + 4;
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putInt(length).put(a2).flip();
        return allocate.array();
    }

    private void b() {
        a aVar = new a(this.b.sBuffer);
        aVar.a(this.d);
        this.c = aVar.a((Map) a, 0, false);
    }

    public void a(byte[] bArr) {
        if (bArr.length >= 4) {
            try {
                a aVar = new a(bArr, 4);
                aVar.a(this.d);
                this.b.readFrom(aVar);
                b();
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("decode package must include size head");
    }

    public <T> T a(String str, T t) throws Exception {
        if (!this.c.containsKey(str)) {
            return null;
        }
        try {
            return (T) a(this.c.get(str), t);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private Object a(byte[] bArr, Object obj) {
        this.e.a(bArr);
        this.e.a(this.d);
        return this.e.a((a) obj, 0, true);
    }

    private byte[] a(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        byte[] bArr = new byte[position];
        System.arraycopy(byteBuffer.array(), 0, bArr, 0, position);
        return bArr;
    }
}
