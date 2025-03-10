package io.openinstall.sdk;

/* loaded from: classes.dex */
public final class bg {
    private static final bm a;

    private static class a extends bm {
        public a() {
            super("DClass", 2);
            a("CLASS");
        }

        @Override // io.openinstall.sdk.bm
        public void a(int i) {
            bg.a(i);
        }
    }

    static {
        a aVar = new a();
        a = aVar;
        aVar.a(1, "IN");
        aVar.a(3, "CH");
        aVar.b(3, "CHAOS");
        aVar.a(4, "HS");
        aVar.b(4, "HESIOD");
        aVar.a(254, "NONE");
        aVar.a(255, "ANY");
    }

    public static void a(int i) {
        if (i < 0 || i > 65535) {
            throw new bz(i);
        }
    }

    public static String b(int i) {
        return a.c(i);
    }
}
