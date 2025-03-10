package j$.util.stream;

import j$.util.Map;
import java.util.EnumMap;
import java.util.Map;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DISTINCT' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* renamed from: j$.util.stream.i4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class EnumC0176i4 {
    public static final EnumC0176i4 DISTINCT;
    public static final EnumC0176i4 ORDERED;
    public static final EnumC0176i4 SHORT_CIRCUIT;
    public static final EnumC0176i4 SIZED;
    public static final EnumC0176i4 SORTED;
    static final int f;
    static final int g;
    static final int h;
    private static final int i;
    private static final int j;
    private static final int k;
    static final int l;
    static final int m;
    static final int n;
    static final int o;
    static final int p;

    /* renamed from: q, reason: collision with root package name */
    static final int f27q;
    static final int r;
    static final int s;
    static final int t;
    static final int u;
    private static final /* synthetic */ EnumC0176i4[] v;
    private final Map a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    static {
        EnumC0170h4 enumC0170h4 = EnumC0170h4.SPLITERATOR;
        C0164g4 f2 = f(enumC0170h4);
        EnumC0170h4 enumC0170h42 = EnumC0170h4.STREAM;
        f2.b(enumC0170h42);
        EnumC0170h4 enumC0170h43 = EnumC0170h4.OP;
        f2.c(enumC0170h43);
        EnumC0176i4 enumC0176i4 = new EnumC0176i4("DISTINCT", 0, 0, f2);
        DISTINCT = enumC0176i4;
        C0164g4 f3 = f(enumC0170h4);
        f3.b(enumC0170h42);
        f3.c(enumC0170h43);
        EnumC0176i4 enumC0176i42 = new EnumC0176i4("SORTED", 1, 1, f3);
        SORTED = enumC0176i42;
        C0164g4 f4 = f(enumC0170h4);
        f4.b(enumC0170h42);
        f4.c(enumC0170h43);
        EnumC0170h4 enumC0170h44 = EnumC0170h4.TERMINAL_OP;
        f4.a(enumC0170h44);
        EnumC0170h4 enumC0170h45 = EnumC0170h4.UPSTREAM_TERMINAL_OP;
        f4.a(enumC0170h45);
        EnumC0176i4 enumC0176i43 = new EnumC0176i4("ORDERED", 2, 2, f4);
        ORDERED = enumC0176i43;
        C0164g4 f5 = f(enumC0170h4);
        f5.b(enumC0170h42);
        f5.a(enumC0170h43);
        EnumC0176i4 enumC0176i44 = new EnumC0176i4("SIZED", 3, 3, f5);
        SIZED = enumC0176i44;
        C0164g4 f6 = f(enumC0170h43);
        f6.b(enumC0170h44);
        EnumC0176i4 enumC0176i45 = new EnumC0176i4("SHORT_CIRCUIT", 4, 12, f6);
        SHORT_CIRCUIT = enumC0176i45;
        v = new EnumC0176i4[]{enumC0176i4, enumC0176i42, enumC0176i43, enumC0176i44, enumC0176i45};
        f = b(enumC0170h4);
        int b = b(enumC0170h42);
        g = b;
        h = b(enumC0170h43);
        b(enumC0170h44);
        b(enumC0170h45);
        int i2 = 0;
        for (EnumC0176i4 enumC0176i46 : values()) {
            i2 |= enumC0176i46.e;
        }
        i = i2;
        j = b;
        int i3 = b << 1;
        k = i3;
        l = b | i3;
        m = enumC0176i4.c;
        n = enumC0176i4.d;
        o = enumC0176i42.c;
        p = enumC0176i42.d;
        f27q = enumC0176i43.c;
        r = enumC0176i43.d;
        s = enumC0176i44.c;
        t = enumC0176i44.d;
        u = enumC0176i45.c;
    }

    private EnumC0176i4(String str, int i2, int i3, C0164g4 c0164g4) {
        for (EnumC0170h4 enumC0170h4 : EnumC0170h4.values()) {
            Map.EL.putIfAbsent(c0164g4.a, enumC0170h4, 0);
        }
        this.a = c0164g4.a;
        int i4 = i3 * 2;
        this.b = i4;
        this.c = 1 << i4;
        this.d = 2 << i4;
        this.e = 3 << i4;
    }

    static int a(int i2, int i3) {
        return i2 | (i3 & (i2 == 0 ? i : ~(((j & i2) << 1) | i2 | ((k & i2) >> 1))));
    }

    private static int b(EnumC0170h4 enumC0170h4) {
        int i2 = 0;
        for (EnumC0176i4 enumC0176i4 : values()) {
            i2 |= ((Integer) enumC0176i4.a.get(enumC0170h4)).intValue() << enumC0176i4.b;
        }
        return i2;
    }

    static int c(j$.util.r rVar) {
        int characteristics = rVar.characteristics();
        return ((characteristics & 4) == 0 || rVar.getComparator() == null) ? f & characteristics : f & characteristics & (-5);
    }

    private static C0164g4 f(EnumC0170h4 enumC0170h4) {
        EnumMap enumMap = new EnumMap(EnumC0170h4.class);
        C0164g4 c0164g4 = new C0164g4(enumMap);
        enumMap.put((EnumMap) enumC0170h4, (EnumC0170h4) 1);
        return c0164g4;
    }

    static int g(int i2) {
        return i2 & ((~i2) >> 1) & j;
    }

    public static EnumC0176i4 valueOf(String str) {
        return (EnumC0176i4) Enum.valueOf(EnumC0176i4.class, str);
    }

    public static EnumC0176i4[] values() {
        return (EnumC0176i4[]) v.clone();
    }

    boolean d(int i2) {
        return (i2 & this.e) == this.c;
    }

    boolean e(int i2) {
        int i3 = this.e;
        return (i2 & i3) == i3;
    }
}
