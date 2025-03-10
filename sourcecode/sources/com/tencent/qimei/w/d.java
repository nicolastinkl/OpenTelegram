package com.tencent.qimei.w;

import com.tencent.qimei.v.g;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'b' uses external variables
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
public final class d implements com.tencent.qimei.f.a<String> {
    public static final d a;
    public static final d b;
    public static final d c;
    public static final com.tencent.qimei.h.a<String> d;
    public static final /* synthetic */ d[] e;
    public final String f;
    public final String g;

    static {
        d dVar = new d("KEY_DATA_VERSION", 0, "version", "");
        a = dVar;
        com.tencent.qimei.v.b bVar = com.tencent.qimei.v.d.a;
        d dVar2 = new d("KEY_DATA_QM_REQUEST_URL", 1, "url", bVar.u());
        b = dVar2;
        d dVar3 = new d("KEY_DATA_PEAK_TIME", 2, "peakTime", bVar.g());
        c = dVar3;
        e = new d[]{dVar, dVar2, dVar3};
        d = new com.tencent.qimei.h.d(new com.tencent.qimei.f.a[0]);
    }

    public d(String str, int i, String str2, String str3) {
        this.f = str2;
        this.g = str3;
    }

    public static d valueOf(String str) {
        return (d) Enum.valueOf(d.class, str);
    }

    public static d[] values() {
        return (d[]) e.clone();
    }

    @Override // com.tencent.qimei.f.a
    public String a() {
        return this.g;
    }

    @Override // com.tencent.qimei.f.a
    public String b() {
        return this.f;
    }

    public String a(String str) {
        return d.a(this, g.a(str));
    }
}
