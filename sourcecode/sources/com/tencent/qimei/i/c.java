package com.tencent.qimei.i;

/* compiled from: PropertiesFile.java */
/* loaded from: classes.dex */
public class c implements Runnable {
    public final /* synthetic */ e a;

    public c(e eVar) {
        this.a = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            synchronized (this.a.g) {
                byte[] a = this.a.a(this.a.c.toString().getBytes("ISO8859-1"));
                if (a == null) {
                    return;
                }
                long length = a.length + 10;
                e eVar = this.a;
                if (length > eVar.e) {
                    long length2 = a.length + 10;
                    eVar.e = length2;
                    eVar.a(length2);
                }
                this.a.d.putInt(0, a.length);
                this.a.d.position(10);
                this.a.d.put(a);
                this.a.d.force();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
