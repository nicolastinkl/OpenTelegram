package com.tencent.qmsp.sdk.c;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.beacon.pack.AbstractJceStruct;

/* loaded from: classes.dex */
public class a {
    private static final byte[] d = {6, 98, -78, 83, 38, AbstractJceStruct.STRUCT_END, 101, -14, 22, 96};
    private String a;
    private long b;
    private boolean c;

    /* renamed from: com.tencent.qmsp.sdk.c.a$a, reason: collision with other inner class name */
    public interface InterfaceC0032a {
        void a();

        void run();
    }

    public a(String str, long j) {
        this.a = str;
        this.b = j;
    }

    private boolean a() {
        Context context;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        boolean z = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(b.a + a(d), 0);
        this.c = true;
        try {
            long j = sharedPreferences.getLong(this.a, 0L);
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = currentTimeMillis - j;
            if (j == 0 || j2 >= this.b || j2 <= 0) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putLong(this.a, currentTimeMillis);
                edit.commit();
                return false;
            }
            try {
                this.c = false;
                return true;
            } catch (Exception e) {
                e = e;
                z = true;
                e.printStackTrace();
                return z;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private void b() {
        Context context;
        if (this.c) {
            context = com.tencent.qmsp.sdk.app.a.getContext();
            try {
                SharedPreferences.Editor edit = context.getSharedPreferences(b.a + a(d), 0).edit();
                edit.remove(this.a);
                edit.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String a(byte[] bArr) {
        return com.tencent.qmsp.sdk.f.h.a(bArr);
    }

    public void a(InterfaceC0032a interfaceC0032a) {
        if (interfaceC0032a != null) {
            if (a()) {
                interfaceC0032a.a();
            } else {
                interfaceC0032a.run();
            }
            b();
        }
    }
}
