package com.tencent.beacon.a.d;

import java.nio.MappedByteBuffer;
import org.json.JSONObject;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
class e implements Runnable {
    final /* synthetic */ g a;

    e(g gVar) {
        this.a = gVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        Object obj;
        JSONObject jSONObject;
        long j;
        MappedByteBuffer mappedByteBuffer;
        MappedByteBuffer mappedByteBuffer2;
        MappedByteBuffer mappedByteBuffer3;
        MappedByteBuffer mappedByteBuffer4;
        long j2;
        try {
            obj = this.a.a;
            synchronized (obj) {
                jSONObject = this.a.c;
                byte[] a = g.a(jSONObject.toString().getBytes("ISO8859-1"));
                if (a == null) {
                    return;
                }
                long length = a.length + 10;
                j = this.a.e;
                if (length > j) {
                    this.a.e = a.length + 10;
                    g gVar = this.a;
                    j2 = gVar.e;
                    gVar.a(j2);
                }
                mappedByteBuffer = this.a.d;
                mappedByteBuffer.putInt(0, a.length);
                mappedByteBuffer2 = this.a.d;
                mappedByteBuffer2.position(10);
                mappedByteBuffer3 = this.a.d;
                mappedByteBuffer3.put(a);
                mappedByteBuffer4 = this.a.d;
                mappedByteBuffer4.force();
            }
        } catch (Exception e) {
            com.tencent.beacon.a.b.g.e().a("504", "[properties] write to file error!", e);
        }
    }
}
