package io.openinstall.sdk;

import java.util.HashMap;
import org.telegram.messenger.NotificationBadge;

/* loaded from: classes.dex */
public final class bv {
    private static final a a;

    private static class a extends bm {
        private final HashMap<Integer, dc<bq>> a;

        public a() {
            super("Type", 2);
            a("TYPE");
            b(65535);
            this.a = new HashMap<>();
        }

        @Override // io.openinstall.sdk.bm
        public void a(int i) {
            bv.a(i);
        }

        public void a(int i, String str, dc<bq> dcVar) {
            super.a(i, str);
            this.a.put(Integer.valueOf(i), dcVar);
        }

        public dc<bq> d(int i) {
            a(i);
            return this.a.get(Integer.valueOf(i));
        }
    }

    static {
        a aVar = new a();
        a = aVar;
        aVar.a(1, "A", new bw());
        aVar.a(5, NotificationBadge.AdwHomeBadger.CLASSNAME, new bx());
        aVar.a(16, "TXT", new by());
    }

    public static void a(int i) {
        if (i < 0 || i > 65535) {
            throw new cb(i);
        }
    }

    public static String b(int i) {
        return a.c(i);
    }

    static dc<bq> c(int i) {
        return a.d(i);
    }
}
