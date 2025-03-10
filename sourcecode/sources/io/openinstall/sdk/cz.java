package io.openinstall.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
abstract class cz extends bq {
    protected List<byte[]> e;

    protected cz() {
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bh bhVar) throws IOException {
        this.e = new ArrayList(2);
        while (bhVar.b() > 0) {
            this.e.add(bhVar.j());
        }
    }

    @Override // io.openinstall.sdk.bq
    protected void a(bi biVar, be beVar, boolean z) {
        Iterator<byte[]> it = this.e.iterator();
        while (it.hasNext()) {
            biVar.b(it.next());
        }
    }

    @Override // io.openinstall.sdk.bq
    protected String b() {
        if (this.e.isEmpty()) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<byte[]> it = this.e.iterator();
        while (it.hasNext()) {
            sb.append(bq.a(it.next(), true));
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }
}
