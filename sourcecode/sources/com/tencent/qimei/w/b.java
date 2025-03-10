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
public final class b implements com.tencent.qimei.f.a<Integer> {
    public static final b a;
    public static final b b;
    public static final b c;
    public static final b d;
    public static final b e;
    public static final com.tencent.qimei.h.a<Integer> f;
    public static final /* synthetic */ b[] g;
    public final String h;
    public final int i;

    static {
        com.tencent.qimei.v.b bVar = com.tencent.qimei.v.d.a;
        b bVar2 = new b("KEY_DATA_QM_REPORT_RATE", 0, "reportRate", bVar.C());
        a = bVar2;
        b bVar3 = new b("KEY_DATA_QM_JS_TIME", 1, "jsTime", bVar.c());
        b = bVar3;
        b bVar4 = new b("KEY_DATA_QM_X5_TIME", 2, "x5Time", bVar.k());
        c = bVar4;
        b bVar5 = new b("KEY_DATA_QM_MIN_HID_RUN", 3, "minHr", bVar.x());
        d = bVar5;
        b bVar6 = new b("KEY_DATA_QM_ARDT", 4, "ardt", bVar.s());
        e = bVar6;
        g = new b[]{bVar2, bVar3, bVar4, bVar5, bVar6};
        final com.tencent.qimei.f.a[] aVarArr = new com.tencent.qimei.f.a[0];
        f = new com.tencent.qimei.h.a<Integer>(aVarArr) { // from class: com.tencent.qimei.h.c
            @Override // com.tencent.qimei.h.a
            public Integer a(com.tencent.qimei.f.a<Integer> aVar, String str) {
                String a2 = a(str, a(aVar));
                if (a2 == null || a2.isEmpty()) {
                    return aVar.a();
                }
                try {
                    return Integer.valueOf(Integer.parseInt(a2));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return aVar.a();
                }
            }
        };
    }

    public b(String str, int i, String str2, int i2) {
        this.h = str2;
        this.i = i2;
    }

    public static b valueOf(String str) {
        return (b) Enum.valueOf(b.class, str);
    }

    public static b[] values() {
        return (b[]) g.clone();
    }

    @Override // com.tencent.qimei.f.a
    public Integer a() {
        return Integer.valueOf(this.i);
    }

    @Override // com.tencent.qimei.f.a
    public String b() {
        return this.h;
    }

    public Integer a(String str) {
        return f.a(this, g.a(str));
    }
}
