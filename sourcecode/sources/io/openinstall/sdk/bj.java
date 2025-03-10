package io.openinstall.sdk;

/* loaded from: classes.dex */
public class bj {
    private static final bm a;

    static {
        bm bmVar = new bm("DNS Header Flag", 3);
        a = bmVar;
        bmVar.b(15);
        bmVar.a("FLAG");
        bmVar.a(true);
        bmVar.a(0, "qr");
        bmVar.a(5, "aa");
        bmVar.a(6, "tc");
        bmVar.a(7, "rd");
        bmVar.a(8, "ra");
        bmVar.a(10, "ad");
        bmVar.a(11, "cd");
    }

    public static String a(int i) {
        return a.c(i);
    }

    public static boolean b(int i) {
        a.a(i);
        return (i < 1 || i > 4) && i < 12;
    }
}
