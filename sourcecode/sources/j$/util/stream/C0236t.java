package j$.util.stream;

import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.BiConsumer;
import j$.util.function.Consumer;
import j$.util.function.Predicate;
import j$.util.function.Supplier;
import j$.wrappers.C0287g0;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: j$.util.stream.t, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0236t implements Consumer, Supplier {
    public final /* synthetic */ int a = 5;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ C0236t(BiConsumer biConsumer, Object obj) {
        this.b = biConsumer;
        this.c = obj;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        switch (this.a) {
            case 0:
                AtomicBoolean atomicBoolean = (AtomicBoolean) this.b;
                ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) this.c;
                if (obj != null) {
                    concurrentHashMap.putIfAbsent(obj, Boolean.TRUE);
                    break;
                } else {
                    atomicBoolean.set(true);
                    break;
                }
            case 5:
                ((BiConsumer) this.b).accept(this.c, obj);
                break;
            default:
                ((C0229r4) this.b).f((Consumer) this.c, obj);
                break;
        }
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 0:
                break;
            case 5:
                break;
        }
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.Supplier
    public Object get() {
        switch (this.a) {
            case 1:
                return new C0203n1((EnumC0215p1) this.b, (j$.wrappers.C) this.c);
            case 2:
                return new C0191l1((EnumC0215p1) this.b, (j$.wrappers.S) this.c);
            case 3:
                return new C0197m1((EnumC0215p1) this.b, (C0287g0) this.c);
            default:
                return new C0185k1((EnumC0215p1) this.b, (Predicate) this.c);
        }
    }

    public /* synthetic */ C0236t(EnumC0215p1 enumC0215p1, Predicate predicate) {
        this.b = enumC0215p1;
        this.c = predicate;
    }

    public /* synthetic */ C0236t(EnumC0215p1 enumC0215p1, j$.wrappers.C c) {
        this.b = enumC0215p1;
        this.c = c;
    }

    public /* synthetic */ C0236t(EnumC0215p1 enumC0215p1, j$.wrappers.S s) {
        this.b = enumC0215p1;
        this.c = s;
    }

    public /* synthetic */ C0236t(EnumC0215p1 enumC0215p1, C0287g0 c0287g0) {
        this.b = enumC0215p1;
        this.c = c0287g0;
    }

    public /* synthetic */ C0236t(C0229r4 c0229r4, Consumer consumer) {
        this.b = c0229r4;
        this.c = consumer;
    }

    public /* synthetic */ C0236t(AtomicBoolean atomicBoolean, ConcurrentHashMap concurrentHashMap) {
        this.b = atomicBoolean;
        this.c = concurrentHashMap;
    }
}
