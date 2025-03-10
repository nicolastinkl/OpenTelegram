package io.openinstall.sdk;

/* loaded from: classes.dex */
public class be {
    private final a[] a = new a[17];

    private static class a {
        bn a;
        int b;
        a c;

        private a() {
        }
    }

    public int a(bn bnVar) {
        int i = -1;
        for (a aVar = this.a[(bnVar.hashCode() & Integer.MAX_VALUE) % 17]; aVar != null; aVar = aVar.c) {
            if (aVar.a.equals(bnVar)) {
                i = aVar.b;
            }
        }
        return i;
    }

    public void a(int i, bn bnVar) {
        if (i > 16383) {
            return;
        }
        int hashCode = (bnVar.hashCode() & Integer.MAX_VALUE) % 17;
        a aVar = new a();
        aVar.a = bnVar;
        aVar.b = i;
        a[] aVarArr = this.a;
        aVar.c = aVarArr[hashCode];
        aVarArr[hashCode] = aVar;
    }
}
