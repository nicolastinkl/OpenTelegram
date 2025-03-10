package com.tencent.qmsp.sdk.c;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.qmsp.sdk.c.a;
import com.tencent.qmsp.sdk.c.k;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;

/* loaded from: classes.dex */
public final class f {
    private static boolean j = false;
    private l e;
    private k f;
    private g h;
    private static final byte[][] i = {new byte[]{52, 125, -93}, new byte[]{41, 121, -79, 113, 35, 43, 57, -18, 42}};
    private static ConcurrentHashMap<Long, InterfaceC0033f> k = new ConcurrentHashMap<>();
    private static Handler l = null;
    private static volatile f m = null;
    private int a = -1;
    private boolean b = false;
    private boolean c = false;
    private com.tencent.qmsp.sdk.d.c g = null;

    class a implements a.InterfaceC0032a {
        a(f fVar) {
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void a() {
            com.tencent.qmsp.sdk.f.g.a("Qp.QFW", 1, "Something wrong when load native so.");
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void run() {
            if (f.j) {
                return;
            }
            try {
                if (!f.k()) {
                    System.loadLibrary(f.c(0));
                }
                boolean unused = f.j = true;
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
            }
        }
    }

    class b implements a.InterfaceC0032a {
        b() {
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void a() {
            com.tencent.qmsp.sdk.f.g.a("Qp.QFW", 1, "Something wrong when init native.");
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void run() {
            Context context;
            if (!f.j || f.this.b) {
                return;
            }
            try {
                Object[] objArr = new Object[1];
                long g = com.tencent.qmsp.sdk.a.c.g();
                l lVar = f.this.e;
                context = com.tencent.qmsp.sdk.app.a.getContext();
                if (f.a(1L, 512L, g, 0L, lVar, context, null, objArr) == 0 && objArr[0] != null && (objArr[0] instanceof Integer)) {
                    f.this.a = ((Integer) objArr[0]).intValue();
                    int unused = f.this.a;
                    f.this.b = true;
                }
                com.tencent.qmsp.sdk.f.g.a("Qp.QFW", 1, String.format("Native ver: %d(%s)", Integer.valueOf(f.this.a), com.tencent.qmsp.sdk.a.c.a(f.this.a)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class c extends Handler {
        c(f fVar, Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                long parseLong = Long.parseLong((String) message.obj);
                if (message.what != 1 || parseLong == 0) {
                    return;
                }
                com.tencent.qmsp.sdk.f.g.a("Qp.QFW", 1, String.format("handle native msg for cookie: %08X", Long.valueOf(parseLong)));
                f.a(6L, parseLong, 0L, 0L, null, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class d implements InterfaceC0033f {
        d(f fVar) {
        }
    }

    class e implements k.e {
        e() {
        }

        @Override // com.tencent.qmsp.sdk.c.k.e
        public void a(int i, int i2) {
            f.this.f.b(this);
        }
    }

    /* renamed from: com.tencent.qmsp.sdk.c.f$f, reason: collision with other inner class name */
    public interface InterfaceC0033f {
    }

    private f() {
        Context context;
        this.e = null;
        this.f = null;
        this.h = null;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        if (context == null) {
            return;
        }
        j();
        l lVar = new l();
        this.e = lVar;
        lVar.a(n.b());
        new com.tencent.qmsp.sdk.c.a(c(0), 86400000L).a(new b());
        this.h = g.d();
        k e2 = k.e();
        this.f = e2;
        e2.a(this.h);
        new c(this, com.tencent.qmsp.sdk.app.b.e().c());
        a(2L, new d(this));
    }

    public static int a(long j2, long j3, long j4, long j5, Object obj, Object obj2, Object[] objArr, Object[] objArr2) {
        if (!j) {
            return 27;
        }
        try {
            return goingDownInternal(j2, j3, j4, j5, obj, obj2, objArr, objArr2);
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            return 29;
        }
    }

    public static String a(int i2, int i3, int i4, int i5, Object obj, Object obj2) {
        if ((obj instanceof String) && obj != null && j) {
            try {
                return goingDownInternal(i2, i3, i4, i5, obj, obj2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static void a(long j2, InterfaceC0033f interfaceC0033f) {
        if (interfaceC0033f != null) {
            k.put(Long.valueOf(j2), interfaceC0033f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String c(int i2) {
        return com.tencent.qmsp.sdk.f.h.a(i[i2]);
    }

    private static native int goingDownInternal(long j2, long j3, long j4, long j5, Object obj, Object obj2, Object[] objArr, Object[] objArr2);

    private static native String goingDownInternal(long j2, long j3, long j4, long j5, Object obj, Object obj2);

    public static f i() {
        if (m == null) {
            synchronized (f.class) {
                if (m == null) {
                    m = new f();
                }
            }
        }
        return m;
    }

    private void j() {
        new com.tencent.qmsp.sdk.c.a(c(0), 86400000L).a(new a(this));
        l = new Handler(com.tencent.qmsp.sdk.app.b.e().c());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean k() {
        if (j) {
            return true;
        }
        String str = com.tencent.qmsp.sdk.a.b.b() + File.separator + c(1);
        File file = new File(str);
        if (!file.exists() || !com.tencent.qmsp.sdk.d.e.b(file, null)) {
            return false;
        }
        try {
            System.load(str);
            return true;
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public Boolean a(int i2) {
        g gVar = this.h;
        int a2 = gVar != null ? gVar.a(i2) : -1;
        boolean z = true;
        if (1 != a2 && com.tencent.qmsp.sdk.c.b.b) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public void a() {
        g gVar = this.h;
        if (gVar != null) {
            gVar.a();
        }
        k kVar = this.f;
        if (kVar != null) {
            kVar.a();
        }
        if (m != null) {
            m = null;
        }
    }

    public void a(com.tencent.qmsp.sdk.d.c cVar) {
        this.g = cVar;
    }

    public g b() {
        return this.h;
    }

    public Handler c() {
        return l;
    }

    public com.tencent.qmsp.sdk.d.c d() {
        return this.g;
    }

    public void e() {
        this.f.b();
    }

    public void f() {
        if (!this.c) {
            this.c = true;
        }
        if (com.tencent.qmsp.sdk.c.b.b) {
            com.tencent.qmsp.sdk.e.a.a();
            com.tencent.qmsp.sdk.e.c.a();
        }
        com.tencent.qmsp.sdk.e.b.a();
        if (com.tencent.qmsp.sdk.c.b.b) {
            this.f.a(new e());
            this.f.c();
        }
    }
}
