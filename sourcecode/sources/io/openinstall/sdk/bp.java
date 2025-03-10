package io.openinstall.sdk;

/* loaded from: classes.dex */
public class bp {
    private static final bm a;

    static {
        bm bmVar = new bm("DNS Rcode", 2);
        a = bmVar;
        bmVar.b(4095);
        bmVar.a("RESERVED");
        bmVar.a(true);
        bmVar.a(0, "NOERROR");
        bmVar.a(1, "FORMERR");
        bmVar.a(2, "SERVFAIL");
        bmVar.a(3, "NXDOMAIN");
        bmVar.a(4, "NOTIMP");
        bmVar.b(4, "NOTIMPL");
        bmVar.a(5, "REFUSED");
        bmVar.a(6, "YXDOMAIN");
        bmVar.a(7, "YXRRSET");
        bmVar.a(8, "NXRRSET");
        bmVar.a(9, "NOTAUTH");
        bmVar.a(10, "NOTZONE");
        bmVar.a(16, "BADVERS");
        bmVar.a(17, "BADKEY");
        bmVar.a(18, "BADTIME");
        bmVar.a(19, "BADMODE");
        bmVar.a(20, "BADNAME");
        bmVar.a(21, "BADALG");
        bmVar.a(22, "BADTRUNC");
        bmVar.a(23, "BADCOOKIE");
    }

    public static String a(int i) {
        return a.c(i);
    }
}
