package io.openinstall.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class bl implements Cloneable {
    private static final bq[] d = new bq[0];
    private bk a;
    private List<bq>[] b;
    private int c;

    public bl() {
        this(new bk());
    }

    bl(bh bhVar) throws IOException {
        this(new bk(bhVar));
        boolean z = this.a.c() == 5;
        boolean b = this.a.b(6);
        for (int i = 0; i < 4; i++) {
            try {
                int e = this.a.e(i);
                if (e > 0) {
                    this.b[i] = new ArrayList(e);
                }
                for (int i2 = 0; i2 < e; i2++) {
                    bhVar.a();
                    this.b[i].add(bq.a(bhVar, i, z));
                }
            } catch (cf e2) {
                if (!b) {
                    throw e2;
                }
            }
        }
        this.c = bhVar.a();
    }

    private bl(bk bkVar) {
        this.b = new List[4];
        this.a = bkVar;
    }

    public bl(byte[] bArr) throws IOException {
        this(new bh(bArr));
    }

    private int a(bi biVar, int i, be beVar, int i2) {
        int size = this.b[i].size();
        int a = biVar.a();
        int i3 = 0;
        bq bqVar = null;
        int i4 = 0;
        int i5 = 0;
        while (i3 < size) {
            bq bqVar2 = this.b[i].get(i3);
            if (bqVar != null && !a(bqVar2, bqVar)) {
                a = biVar.a();
                i5 = i4;
            }
            bqVar2.a(biVar, i, beVar);
            if (biVar.a() > i2) {
                biVar.a(a);
                return size - i5;
            }
            i4++;
            i3++;
            bqVar = bqVar2;
        }
        return size - i4;
    }

    public static bl a(bq bqVar) {
        bl blVar = new bl();
        blVar.a.c(0);
        blVar.a.a(7);
        blVar.a(bqVar, 0);
        return blVar;
    }

    private void a(bi biVar, int i) {
        if (i < 12) {
            return;
        }
        int a = biVar.a();
        this.a.a(biVar);
        be beVar = new be();
        int d2 = this.a.d();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= 4) {
                break;
            }
            if (this.b[i2] != null) {
                int a2 = a(biVar, i2, beVar, i);
                if (a2 != 0 && i2 != 3) {
                    d2 = bk.a(d2, 6, true);
                    int e = this.a.e(i2) - a2;
                    int i4 = a + 4;
                    biVar.a(e, (i2 * 2) + i4);
                    for (int i5 = i2 + 1; i5 < 3; i5++) {
                        biVar.a(0, (i5 * 2) + i4);
                    }
                } else if (i2 == 3) {
                    i3 = this.a.e(i2) - a2;
                }
            }
            i2++;
        }
        if (d2 != this.a.d()) {
            biVar.a(d2, a + 2);
        }
        if (i3 != this.a.e(3)) {
            biVar.a(i3, a + 10);
        }
    }

    private void a(StringBuilder sb, int i) {
        if (i > 3) {
            return;
        }
        for (bq bqVar : a(i)) {
            if (i == 0) {
                sb.append(";;\t");
                sb.append(bqVar.a);
                sb.append(", type = ");
                sb.append(bv.b(bqVar.b));
                sb.append(", class = ");
                sb.append(bg.b(bqVar.c));
            } else {
                sb.append(bqVar);
            }
            sb.append("\n");
        }
    }

    private static boolean a(bq bqVar, bq bqVar2) {
        return bqVar.e() == bqVar2.e() && bqVar.f() == bqVar2.f() && bqVar.d().equals(bqVar2.d());
    }

    public bk a() {
        return this.a;
    }

    public List<bq> a(int i) {
        List<bq>[] listArr = this.b;
        return listArr[i] == null ? Collections.emptyList() : Collections.unmodifiableList(listArr[i]);
    }

    public void a(bq bqVar, int i) {
        List<bq>[] listArr = this.b;
        if (listArr[i] == null) {
            listArr[i] = new LinkedList();
        }
        this.a.d(i);
        this.b[i].add(bqVar);
    }

    public int b() {
        return this.a.b();
    }

    public byte[] b(int i) {
        bi biVar = new bi();
        a(biVar, i);
        this.c = biVar.a();
        return biVar.b();
    }

    public int c() {
        return this.c;
    }

    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public bl clone() throws CloneNotSupportedException {
        bl blVar = (bl) super.clone();
        blVar.b = new List[this.b.length];
        int i = 0;
        while (true) {
            List<bq>[] listArr = this.b;
            if (i >= listArr.length) {
                blVar.a = this.a.clone();
                return blVar;
            }
            if (listArr[i] != null) {
                blVar.b[i] = new LinkedList(this.b[i]);
            }
            i++;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append('\n');
        for (int i = 0; i < 4; i++) {
            int c = this.a.c();
            sb.append(";; ");
            sb.append(c != 5 ? br.b(i) : br.c(i));
            sb.append(":\n");
            a(sb, i);
            sb.append("\n");
        }
        sb.append(";; Message size: ");
        sb.append(c());
        sb.append(" bytes");
        return sb.toString();
    }
}
