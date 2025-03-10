package com.tencent.qmsp.sdk.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.qmsp.sdk.c.a;
import com.tencent.qmsp.sdk.c.g;
import com.tencent.qmsp.sdk.c.j;
import com.tencent.qmsp.sdk.d.d;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class k {
    private static final byte[][] i = {new byte[]{44, 116}, new byte[]{49, 105, -93, 69}, new byte[]{35, 124, -78, 71}, new byte[]{40, Byte.MAX_VALUE, -73, 69}, new byte[]{51, 117, -95}, new byte[]{20, 65, -125, 82, 33, 47, 114, -2, 49, 62, -126, 125, -96, 80}, new byte[]{20, 125, -96, 80, 29, AbstractJceStruct.STRUCT_END}};
    private static k j;
    private com.tencent.qmsp.sdk.c.g d;
    private boolean f = false;
    private boolean g = false;
    private CopyOnWriteArrayList<e> h = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<f> b = new CopyOnWriteArrayList<>();
    private j c = new j();
    private ConcurrentHashMap<Integer, f> a = new ConcurrentHashMap<>();
    private Handler e = new g(com.tencent.qmsp.sdk.app.b.e().c());

    class a implements g.c {
        a() {
        }

        @Override // com.tencent.qmsp.sdk.c.g.c
        public void a(List<Pair<Integer, Integer>> list) {
            Message obtainMessage = k.this.e.obtainMessage(2);
            obtainMessage.obj = list;
            k.this.e.sendMessage(obtainMessage);
        }
    }

    final class b implements a.InterfaceC0032a {
        public f a;

        b(k kVar) {
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void a() {
            this.a.d = 26;
        }

        @Override // com.tencent.qmsp.sdk.c.a.InterfaceC0032a
        public void run() {
            f fVar;
            int i;
            String str = this.a.h;
            if (str == null || str.contains("..")) {
                fVar = this.a;
                i = 15;
            } else {
                File file = new File(this.a.h);
                if (file.exists()) {
                    this.a.i = new o();
                    int i2 = this.a.b;
                    if ((i2 == 2 || i2 == 1) && !com.tencent.qmsp.sdk.d.e.b(file, null)) {
                        this.a.d = 10;
                        return;
                    }
                    Object[] objArr = new Object[1];
                    f fVar2 = this.a;
                    fVar2.d = com.tencent.qmsp.sdk.c.f.a(2L, fVar2.b, fVar2.a, 0L, fVar2.h, null, null, objArr);
                    if (objArr[0] == null || !(objArr[0] instanceof Integer)) {
                        return;
                    }
                    this.a.f = ((Integer) objArr[0]).intValue();
                    return;
                }
                fVar = this.a;
                i = 12;
            }
            fVar.d = i;
        }
    }

    class c extends i {
        c() {
            super(k.this, null);
        }

        @Override // com.tencent.qmsp.sdk.c.k.i
        public void a() {
            if (this.a) {
                k.this.c.a();
            }
        }

        @Override // com.tencent.qmsp.sdk.c.k.i
        public void a(String str, String str2, int i, int i2, int i3, int i4) {
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("visitQSecSFUItem libPath = %s libVer = %s libId = %d", str, str2, Integer.valueOf(i)));
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            if (i4 != 1 && i4 != 2) {
                com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Invalid mode: %d", Integer.valueOf(i4)));
                return;
            }
            j.a a = k.this.c.a(i);
            if (a != null) {
                k.this.c.a(a.a, false);
            } else {
                a = new j.a();
            }
            a.a = i;
            a.b = i2;
            a.c = i3;
            a.e = str;
            a.d = str2;
            k.this.c.a(a, false);
            this.a = true;
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("visitQSecSFUItem libId = %d", Integer.valueOf(i)));
            if (i4 == 1 && k.this.d.a(i) == 1) {
                f fVar = (f) k.this.a.get(Integer.valueOf(i));
                if (fVar == null) {
                    k.this.c(k.this.a(a));
                    return;
                }
                k.this.a(fVar, str2, str);
                if (fVar.d != 0) {
                    k.this.a.remove(Integer.valueOf(fVar.a));
                }
            }
        }
    }

    class d implements Runnable {
        d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            k.this.i();
        }
    }

    public interface e {
        void a(int i, int i2);
    }

    private static class f {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public String g;
        public String h;
        public o i;

        private f() {
            this.d = -1;
            this.g = "";
            this.h = "";
        }

        /* synthetic */ f(a aVar) {
            this();
        }
    }

    private class g extends Handler {
        public g(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Object obj;
            int i = message.what;
            if (i == 1) {
                k.this.g();
                return;
            }
            if (i == 2) {
                Object obj2 = message.obj;
                if (obj2 != null) {
                    k.this.b((List<Pair<Integer, Integer>>) obj2);
                    return;
                }
                return;
            }
            if (i == 3) {
                k.this.h();
                return;
            }
            if (i == 4) {
                k.this.i();
            } else if (i == 5 && (obj = message.obj) != null) {
                k.this.c((e) obj);
            }
        }
    }

    private static class h {
        private i a;

        public h(i iVar) {
            this.a = iVar;
        }

        public void a(int i) {
            i iVar;
            List<d.a> list;
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("QSecSFUReader read, bid = %d ", Integer.valueOf(i)));
            try {
                try {
                    List<d.b> a = new com.tencent.qmsp.sdk.d.b().a(1L);
                    if (a != null) {
                        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("QSecSFUReader read updateSections count = %d ", Integer.valueOf(a.size())));
                        for (d.b bVar : a) {
                            if (bVar.b == i && (list = bVar.o) != null) {
                                for (d.a aVar : list) {
                                    if (!TextUtils.isEmpty(aVar.i)) {
                                        String str = aVar.f;
                                        if (!TextUtils.isEmpty(aVar.g)) {
                                            str = aVar.g;
                                        }
                                        if (str != null) {
                                            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("ExtraInfo: %s path: %s", aVar.i, str));
                                            JSONObject jSONObject = new JSONObject(aVar.i);
                                            int i2 = jSONObject.getInt(k.b(0));
                                            int i3 = jSONObject.getInt(k.b(1));
                                            int i4 = jSONObject.getInt(k.b(2));
                                            int i5 = jSONObject.getInt(k.b(3));
                                            String string = jSONObject.getString(k.b(4));
                                            i iVar2 = this.a;
                                            if (iVar2 != null) {
                                                iVar2.a(str, string, i2, i3, i4, i5);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    iVar = this.a;
                    if (iVar == null) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    iVar = this.a;
                    if (iVar == null) {
                        return;
                    }
                }
                iVar.a();
            } catch (Throwable th) {
                i iVar3 = this.a;
                if (iVar3 != null) {
                    iVar3.a();
                }
                throw th;
            }
        }
    }

    private class i {
        protected boolean a;

        private i() {
            this.a = false;
        }

        /* synthetic */ i(k kVar, a aVar) {
            this();
        }

        public void a() {
            if (this.a) {
                k.this.c.a();
            }
        }

        public void a(String str, String str2, int i, int i2, int i3, int i4) {
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("visitQSecSFUItem libPath = %s libVer = %s libId = %d", str, str2, Integer.valueOf(i)));
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            j.a a = k.this.c.a(i);
            if (a != null && str.equals(a.e) && str2.equals(a.d) && i2 == a.b && i3 == a.c) {
                return;
            }
            if (a == null) {
                a = new j.a();
            } else {
                k.this.c.a(i, false);
                com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Database info mismatch for lib: %d", Integer.valueOf(i)));
            }
            a.a = i;
            a.b = i2;
            a.c = i3;
            a.e = str;
            a.d = str2;
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Add lost lib: %d,%d,%d,%s", Integer.valueOf(i), Integer.valueOf(a.b), Integer.valueOf(a.c), a.e));
            k.this.c.a(a, false);
            this.a = true;
        }
    }

    private k() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public f a(j.a aVar) {
        f fVar = new f(null);
        fVar.a = aVar.a;
        fVar.b = aVar.b;
        fVar.c = aVar.c;
        fVar.h = aVar.e;
        fVar.g = aVar.d;
        fVar.e = 4;
        return fVar;
    }

    private void a(int i2, int i3) {
        Iterator<e> it = this.h.iterator();
        while (it.hasNext()) {
            e next = it.next();
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Notify listener [%d:%d]", Integer.valueOf(i2), Integer.valueOf(i3)));
            next.a(i2, i3);
        }
    }

    private void a(f fVar) {
        this.b.add(fVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(f fVar, String str, String str2) {
        e(fVar);
        if (fVar.e == 4) {
            String str3 = fVar.h;
            if (str3 != null && !str3.equals(str2)) {
                com.tencent.qmsp.sdk.f.d.a(fVar.h, false);
                fVar.h = str2;
            }
            String str4 = fVar.g;
            if (str4 != null && !str4.equals(str)) {
                fVar.g = str;
            }
            fVar.f = 0;
            d(fVar);
            a(fVar);
        }
    }

    private void a(List<j.a> list) {
        for (j.a aVar : list) {
            this.c.a(aVar.a, false);
            String str = aVar.e;
            if (str != null) {
                com.tencent.qmsp.sdk.f.d.a(str, false);
            }
        }
        this.c.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(int i2) {
        return com.tencent.qmsp.sdk.f.h.a(i[i2]);
    }

    private void b(f fVar) {
        try {
            com.tencent.qmsp.sdk.a.g gVar = new com.tencent.qmsp.sdk.a.g();
            gVar.a(fVar.a).a(fVar.g).a(fVar.d);
            com.tencent.qmsp.sdk.a.f.a(gVar.toString(), 3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(List<Pair<Integer, Integer>> list) {
        if (list == null || !this.f) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (Pair<Integer, Integer> pair : list) {
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("cb changed: id(%d), cb(%d)", pair.first, pair.second));
            f fVar = this.a.get(pair.first);
            if (fVar == null) {
                j.a a2 = this.c.a(((Integer) pair.first).intValue());
                if (a2 != null) {
                    if (((Integer) pair.second).intValue() == 1) {
                        c(a(a2));
                    } else if (((Integer) pair.second).intValue() == 2) {
                        linkedList.add(a2);
                    }
                }
            } else if (((Integer) pair.second).intValue() == 1) {
                if (fVar.e == 4) {
                    d(fVar);
                    if (fVar.d != 0) {
                        this.a.remove(Integer.valueOf(fVar.a));
                    }
                    a(fVar);
                }
            } else if (((Integer) pair.second).intValue() == 2 && fVar.e == 4) {
                this.a.remove(pair.first);
                j.a a3 = this.c.a(((Integer) pair.first).intValue());
                if (a3 != null) {
                    linkedList.add(a3);
                }
            }
        }
        if (linkedList.isEmpty()) {
            return;
        }
        a(linkedList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(e eVar) {
        for (Map.Entry<Integer, f> entry : this.a.entrySet()) {
            if (entry.getValue().e == 1) {
                com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Notify listener [%d:%d]", 1, entry.getKey()));
                eVar.a(1, entry.getKey().intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(f fVar) {
        d(fVar);
        if (fVar.d == 0) {
            this.a.put(Integer.valueOf(fVar.a), fVar);
            a(1, fVar.a);
        }
        a(fVar);
    }

    private void d() {
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, "addLostUpgradeLibs");
        new h(new i(this, null)).a(1);
    }

    private void d(f fVar) {
        fVar.e = 2;
        b bVar = new b(this);
        bVar.a = fVar;
        new com.tencent.qmsp.sdk.c.a(String.format("Lib%d_%s", Integer.valueOf(fVar.a), fVar.g), 43200000L).a(bVar);
        Object[] objArr = new Object[3];
        String str = fVar.h;
        if (str == null) {
            str = "null";
        }
        objArr[0] = str;
        objArr[1] = com.tencent.qmsp.sdk.a.c.a(fVar.f);
        objArr[2] = Integer.valueOf(fVar.d);
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("load: %s ver: %s error: %08X", objArr));
        fVar.e = fVar.d != 0 ? 4 : 1;
    }

    public static k e() {
        if (j == null) {
            synchronized (k.class) {
                if (j == null) {
                    j = new k();
                }
            }
        }
        return j;
    }

    private void e(f fVar) {
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Prepare to unload: %d,%d,%d,%d,%s,%s", Integer.valueOf(fVar.a), Integer.valueOf(fVar.b), Integer.valueOf(fVar.c), Integer.valueOf(fVar.e), fVar.g, fVar.h));
        if ((fVar.c & 1) != 0 && fVar.e == 1) {
            fVar.e = 3;
            fVar.i.c();
            int a2 = com.tencent.qmsp.sdk.c.f.a(3L, fVar.a, 0L, 0L, null, null, null, null);
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Unload ret: %d", Integer.valueOf(a2)));
            if (a2 == 0) {
                fVar.e = 4;
                a(2, fVar.a);
            } else {
                fVar.e = 5;
                fVar.d = a2;
            }
        }
    }

    private void f() {
        List<j.a> b2 = this.c.b();
        if (b2 == null || b2.isEmpty()) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (j.a aVar : b2) {
            int a2 = this.d.a(aVar.a);
            Object[] objArr = new Object[6];
            objArr[0] = Integer.valueOf(aVar.a);
            objArr[1] = Integer.valueOf(aVar.b);
            objArr[2] = Integer.valueOf(aVar.c);
            objArr[3] = aVar.d;
            String str = aVar.e;
            if (str == null) {
                str = "null";
            }
            objArr[4] = str;
            objArr[5] = Integer.valueOf(a2);
            com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("%d,%d,%d,%s,%s,%d", objArr));
            if (a2 != 0 && -1 != a2) {
                if (2 == a2) {
                    linkedList.add(aVar);
                } else if (1 == a2) {
                    f a3 = a(aVar);
                    c(a3);
                    a(a3);
                }
                if (!linkedList.isEmpty()) {
                    a(linkedList);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void g() {
        if (!this.f) {
            this.g = true;
        } else {
            this.g = false;
            new h(new c()).a(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, "onLoadLocalLibs");
        if (this.f) {
            return;
        }
        this.f = true;
        d();
        f();
        if (this.g) {
            b();
        }
        j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        Context context;
        long j2;
        long j3;
        String uid;
        String uid2;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(com.tencent.qmsp.sdk.c.b.a + b(6), 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        try {
            Iterator<f> it = this.b.iterator();
            j3 = 28800000;
            while (it.hasNext()) {
                try {
                    f next = it.next();
                    String str = next.g;
                    int i2 = next.f;
                    if (i2 != 0) {
                        str = com.tencent.qmsp.sdk.a.c.a(i2);
                    }
                    uid = com.tencent.qmsp.sdk.app.a.getUid();
                    String format = String.format("Lib%d_%s_%s", Integer.valueOf(next.a), uid, str);
                    uid2 = com.tencent.qmsp.sdk.app.a.getUid();
                    String format2 = String.format("Lib%d_%s_%s_lpt", Integer.valueOf(next.a), uid2, str);
                    int i3 = sharedPreferences.getInt(format, -1);
                    long currentTimeMillis = System.currentTimeMillis() - sharedPreferences.getLong(format2, 0L);
                    if (currentTimeMillis >= 28800000 || next.d != i3) {
                        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Rp: %d,%d,%s", Integer.valueOf(next.a), Integer.valueOf(next.d), str));
                        edit.putInt(format, next.d);
                        edit.putLong(format2, System.currentTimeMillis());
                        b(next);
                        this.b.remove(next);
                    } else {
                        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("Ignore rp for: %d,%08X,%s", Integer.valueOf(next.a), Integer.valueOf(next.d), str));
                        long j4 = 28800000 - currentTimeMillis;
                        if (j3 > j4) {
                            j3 = j4;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    j2 = j3;
                    e.printStackTrace();
                    j3 = j2;
                    edit.commit();
                    com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, "next rp interval: " + j3);
                    com.tencent.qmsp.sdk.c.f.i().c().postDelayed(new d(), j3);
                }
            }
        } catch (Exception e3) {
            e = e3;
            j2 = 28800000;
        }
        edit.commit();
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, "next rp interval: " + j3);
        com.tencent.qmsp.sdk.c.f.i().c().postDelayed(new d(), j3);
    }

    private void j() {
        Handler handler = this.e;
        handler.sendMessage(handler.obtainMessage(4));
    }

    public void a() {
        try {
            ConcurrentHashMap<Integer, f> concurrentHashMap = this.a;
            if (concurrentHashMap != null) {
                for (Map.Entry<Integer, f> entry : concurrentHashMap.entrySet()) {
                    entry.getValue().c = 1;
                    e(entry.getValue());
                }
            }
            if (j != null) {
                j = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(com.tencent.qmsp.sdk.c.g gVar) {
        this.d = gVar;
        gVar.a(new a());
    }

    public void a(e eVar) {
        this.h.add(eVar);
        Handler handler = this.e;
        handler.sendMessage(handler.obtainMessage(5, eVar));
    }

    public void b() {
        Handler handler = this.e;
        handler.sendMessage(handler.obtainMessage(1));
    }

    public void b(e eVar) {
        this.h.remove(eVar);
    }

    public void c() {
        com.tencent.qmsp.sdk.f.g.a("Qp.QLM", 1, String.format("OnEveryLogin mHasLoadLocal = %b", Boolean.valueOf(this.f)));
        if (this.f) {
            return;
        }
        Handler handler = this.e;
        handler.sendMessage(handler.obtainMessage(3));
    }
}
