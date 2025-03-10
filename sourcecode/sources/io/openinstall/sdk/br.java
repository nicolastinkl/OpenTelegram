package io.openinstall.sdk;

/* loaded from: classes.dex */
public class br {
    private static final bm a;
    private static final String[] b;
    private static final String[] c;

    static {
        bm bmVar = new bm("Message Section", 3);
        a = bmVar;
        b = new String[]{"QUESTIONS", "ANSWERS", "AUTHORITY RECORDS", "ADDITIONAL RECORDS"};
        c = new String[]{"ZONE", "PREREQUISITES", "UPDATE RECORDS", "ADDITIONAL RECORDS"};
        bmVar.b(3);
        bmVar.a(true);
        bmVar.a(0, "qd");
        bmVar.a(1, "an");
        bmVar.a(2, "au");
        bmVar.a(3, "ad");
    }

    public static String a(int i) {
        return a.c(i);
    }

    public static String b(int i) {
        a.a(i);
        return b[i];
    }

    public static String c(int i) {
        a.a(i);
        return c[i];
    }
}
