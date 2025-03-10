package io.openinstall.sdk;

/* loaded from: classes.dex */
public class dz implements Cloneable {
    private final ee a;
    private final ed b;

    public dz(ed edVar, ee eeVar) {
        this.a = eeVar;
        this.b = edVar;
    }

    public dz(ee eeVar) {
        this(null, eeVar);
    }

    public ee a() {
        return this.a;
    }

    public void a(byte[] bArr) {
        ed edVar = this.b;
        if (edVar != null) {
            edVar.a(bArr);
        } else {
            this.a.a(bArr);
        }
    }

    public ed b() {
        return this.b;
    }

    public byte[] c() {
        ed edVar = this.b;
        return edVar != null ? edVar.d() : this.a.g;
    }

    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public dz clone() {
        ed edVar = this.b;
        return new dz(edVar == null ? null : edVar.clone(), this.a.clone());
    }
}
