package j$.util.stream;

import j$.util.AbstractC0112a;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.Consumer;
import java.util.Comparator;

/* renamed from: j$.util.stream.r4, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
final class C0229r4 implements j$.util.r, Consumer {
    private static final Object d = new Object();
    private final j$.util.r a;
    private final ConcurrentHashMap b;
    private Object c;

    C0229r4(j$.util.r rVar) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        this.a = rVar;
        this.b = concurrentHashMap;
    }

    private C0229r4(j$.util.r rVar, ConcurrentHashMap concurrentHashMap) {
        this.a = rVar;
        this.b = concurrentHashMap;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        this.c = obj;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.r
    public boolean b(Consumer consumer) {
        while (this.a.b(this)) {
            ConcurrentHashMap concurrentHashMap = this.b;
            Object obj = this.c;
            if (obj == null) {
                obj = d;
            }
            if (concurrentHashMap.putIfAbsent(obj, Boolean.TRUE) == null) {
                consumer.accept(this.c);
                this.c = null;
                return true;
            }
        }
        return false;
    }

    @Override // j$.util.r
    public int characteristics() {
        return (this.a.characteristics() & (-16469)) | 1;
    }

    @Override // j$.util.r
    public long estimateSize() {
        return this.a.estimateSize();
    }

    public void f(Consumer consumer, Object obj) {
        if (this.b.putIfAbsent(obj != null ? obj : d, Boolean.TRUE) == null) {
            consumer.accept(obj);
        }
    }

    @Override // j$.util.r
    public void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(new C0236t(this, consumer));
    }

    @Override // j$.util.r
    public Comparator getComparator() {
        return this.a.getComparator();
    }

    @Override // j$.util.r
    public /* synthetic */ long getExactSizeIfKnown() {
        return AbstractC0112a.e(this);
    }

    @Override // j$.util.r
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return AbstractC0112a.f(this, i);
    }

    @Override // j$.util.r
    public j$.util.r trySplit() {
        j$.util.r trySplit = this.a.trySplit();
        if (trySplit != null) {
            return new C0229r4(trySplit, this.b);
        }
        return null;
    }
}
