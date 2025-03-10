package j$.time.format;

import j$.time.ZoneId;

/* loaded from: classes2.dex */
class s implements h {
    private final j$.time.temporal.u a;
    private final String b;

    s(j$.time.temporal.u uVar, String str) {
        this.a = uVar;
        this.b = str;
    }

    @Override // j$.time.format.h
    public boolean a(v vVar, StringBuilder sb) {
        ZoneId zoneId = (ZoneId) vVar.f(this.a);
        if (zoneId == null) {
            return false;
        }
        sb.append(zoneId.l());
        return true;
    }

    public String toString() {
        return this.b;
    }
}
