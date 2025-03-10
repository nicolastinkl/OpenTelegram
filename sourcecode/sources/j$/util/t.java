package j$.util;

import j$.util.function.Consumer;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
class t implements java.util.Iterator, Consumer {
    boolean a = false;
    Object b;
    final /* synthetic */ r c;

    t(r rVar) {
        this.c = rVar;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        this.a = true;
        this.b = obj;
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        if (!this.a) {
            this.c.b(this);
        }
        return this.a;
    }

    @Override // java.util.Iterator
    public Object next() {
        if (!this.a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.a = false;
        return this.b;
    }
}
