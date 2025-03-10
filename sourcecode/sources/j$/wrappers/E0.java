package j$.wrappers;

import j$.util.stream.BaseStream;
import java.util.Iterator;

/* loaded from: classes2.dex */
public final /* synthetic */ class E0 implements BaseStream {
    final /* synthetic */ java.util.stream.BaseStream a;

    private /* synthetic */ E0(java.util.stream.BaseStream baseStream) {
        this.a = baseStream;
    }

    public static /* synthetic */ BaseStream k0(java.util.stream.BaseStream baseStream) {
        if (baseStream == null) {
            return null;
        }
        return baseStream instanceof F0 ? ((F0) baseStream).a : new E0(baseStream);
    }

    @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
    public /* synthetic */ void close() {
        this.a.close();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream onClose(Runnable runnable) {
        return k0(this.a.onClose(runnable));
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream parallel() {
        return k0(this.a.parallel());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream sequential() {
        return k0(this.a.sequential());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ j$.util.r spliterator() {
        return C0286g.a(this.a.spliterator());
    }

    @Override // j$.util.stream.BaseStream
    public /* synthetic */ BaseStream unordered() {
        return k0(this.a.unordered());
    }
}
