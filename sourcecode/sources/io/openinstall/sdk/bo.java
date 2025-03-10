package io.openinstall.sdk;

/* loaded from: classes.dex */
public class bo {
    private static final bm a;

    static {
        bm bmVar = new bm("DNS Opcode", 2);
        a = bmVar;
        bmVar.b(15);
        bmVar.a("RESERVED");
        bmVar.a(true);
        bmVar.a(0, "QUERY");
        bmVar.a(1, "IQUERY");
        bmVar.a(2, "STATUS");
        bmVar.a(4, "NOTIFY");
        bmVar.a(5, "UPDATE");
        bmVar.a(6, "DSO");
    }

    public static String a(int i) {
        return a.c(i);
    }
}
