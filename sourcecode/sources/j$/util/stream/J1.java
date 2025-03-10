package j$.util.stream;

import j$.util.Collection$EL;
import j$.util.function.Consumer;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes2.dex */
final class J1 implements F1 {
    private final Collection a;

    J1(Collection collection) {
        this.a = collection;
    }

    @Override // j$.util.stream.F1
    public F1 b(int i) {
        throw new IndexOutOfBoundsException();
    }

    @Override // j$.util.stream.F1
    public long count() {
        return this.a.size();
    }

    @Override // j$.util.stream.F1
    public void forEach(Consumer consumer) {
        Collection$EL.a(this.a, consumer);
    }

    @Override // j$.util.stream.F1
    public void i(Object[] objArr, int i) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ int p() {
        return 0;
    }

    @Override // j$.util.stream.F1
    public Object[] q(j$.util.function.n nVar) {
        Collection collection = this.a;
        return collection.toArray((Object[]) nVar.apply(collection.size()));
    }

    @Override // j$.util.stream.F1
    public /* synthetic */ F1 r(long j, long j2, j$.util.function.n nVar) {
        return AbstractC0238t1.q(this, j, j2, nVar);
    }

    @Override // j$.util.stream.F1
    public j$.util.r spliterator() {
        return Collection$EL.stream(this.a).spliterator();
    }

    public String toString() {
        return String.format("CollectionNode[%d][%s]", Integer.valueOf(this.a.size()), this.a);
    }
}
