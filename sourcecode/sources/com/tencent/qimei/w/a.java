package com.tencent.qimei.w;

import com.tencent.qimei.v.g;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'a' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* compiled from: StrategyProtocol.java */
/* loaded from: classes.dex */
public final class a implements com.tencent.qimei.f.a<Boolean> {
    public static final a a;
    public static final a b;
    public static final a c;
    public static final a d;
    public static final a e;
    public static final a f;
    public static final a g;
    public static final a h;
    public static final a i;
    public static final a j;
    public static final a k;
    public static final a l;
    public static final a m;
    public static final a n;
    public static final a o;
    public static final a p;

    /* renamed from: q, reason: collision with root package name */
    public static final com.tencent.qimei.h.a<Boolean> f21q;
    public static final /* synthetic */ a[] r;
    public final String s;
    public final boolean t;

    static {
        String a2 = com.tencent.qimei.a.b.a(0);
        com.tencent.qimei.v.b bVar = com.tencent.qimei.v.d.a;
        a aVar = new a("KEY_DATA_ENABLE_Q16", 0, a2, bVar.i());
        a = aVar;
        a aVar2 = new a("KEY_DATA_ENABLE_Q36", 1, com.tencent.qimei.a.b.a(1), bVar.G());
        b = aVar2;
        a aVar3 = new a("KEY_DATA_ENABLE_OD", 2, com.tencent.qimei.a.b.a(5), bVar.h());
        c = aVar3;
        a aVar4 = new a("KEY_DATA_ENABLE_USERID", 3, "userId", bVar.b());
        d = aVar4;
        a aVar5 = new a("KEY_DATA_ENABLE_EI", 4, com.tencent.qimei.a.b.a(6), bVar.E());
        e = aVar5;
        a aVar6 = new a("KEY_DATA_ENABLE_SI", 5, com.tencent.qimei.a.b.a(7), bVar.F());
        f = aVar6;
        a aVar7 = new a("KEY_DATA_ENABLE_AD", 6, com.tencent.qimei.a.b.a(11), bVar.y());
        g = aVar7;
        a aVar8 = new a("KEY_DATA_ENABLE_MC", 7, com.tencent.qimei.a.b.a(8), bVar.q());
        h = aVar8;
        a aVar9 = new a("KEY_DATA_ENABLE_CD", 8, com.tencent.qimei.a.b.a(9), bVar.n());
        i = aVar9;
        a aVar10 = new a("KEY_DATA_ENABLE_PROCESS_INFO", 9, "processInfo", bVar.z());
        j = aVar10;
        a aVar11 = new a("KEY_DATA_ENABLE_AUDIT", 10, "audit", bVar.v());
        k = aVar11;
        a aVar12 = new a("KEY_DATA_FORCE_UPDATE_QM", 11, com.tencent.qimei.a.b.a(10), bVar.l());
        l = aVar12;
        a aVar13 = new a("KEY_DATA_ENABLE_REPORT", 12, "report", bVar.B());
        m = aVar13;
        a aVar14 = new a("KEY_DATA_ENABLE_BEACON_ID", 13, "isBidEnable", bVar.t());
        n = aVar14;
        a aVar15 = new a("KEY_DATA_ENABLE_OZ", 14, "oz", bVar.f());
        o = aVar15;
        a aVar16 = new a("KEY_DATA_ENABLE_OO", 15, "oo", bVar.w());
        p = aVar16;
        r = new a[]{aVar, aVar2, aVar3, aVar4, aVar5, aVar6, aVar7, aVar8, aVar9, aVar10, aVar11, aVar12, aVar13, aVar14, aVar15, aVar16};
        final com.tencent.qimei.f.a[] aVarArr = new com.tencent.qimei.f.a[0];
        f21q = new com.tencent.qimei.h.a<Boolean>(aVarArr) { // from class: com.tencent.qimei.h.b
            @Override // com.tencent.qimei.h.a
            public Boolean a(com.tencent.qimei.f.a<Boolean> aVar17, String str) {
                String a3 = a(str, a(aVar17));
                return (a3 == null || a3.isEmpty()) ? aVar17.a() : Boolean.valueOf("1".equals(a3));
            }
        };
    }

    public a(String str, int i2, String str2, boolean z) {
        this.s = str2;
        this.t = z;
    }

    public static a valueOf(String str) {
        return (a) Enum.valueOf(a.class, str);
    }

    public static a[] values() {
        return (a[]) r.clone();
    }

    @Override // com.tencent.qimei.f.a
    public Boolean a() {
        return Boolean.valueOf(this.t);
    }

    @Override // com.tencent.qimei.f.a
    public String b() {
        return this.s;
    }

    public Boolean a(String str) {
        return f21q.a(this, g.a(str));
    }
}
