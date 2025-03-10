package j$.util.concurrent;

import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Consumer;
import j$.util.function.Function;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public final /* synthetic */ class a implements BiConsumer, BiFunction, Consumer {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Object b;
    public final /* synthetic */ Object c;

    public /* synthetic */ a(BiConsumer biConsumer, BiConsumer biConsumer2) {
        this.b = biConsumer;
        this.c = biConsumer2;
    }

    @Override // j$.util.function.Consumer
    public void accept(Object obj) {
        Consumer consumer = (Consumer) this.b;
        Consumer consumer2 = (Consumer) this.c;
        consumer.accept(obj);
        consumer2.accept(obj);
    }

    @Override // j$.util.function.BiConsumer
    public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
        switch (this.a) {
        }
        return BiConsumer.CC.$default$andThen(this, biConsumer);
    }

    @Override // j$.util.function.BiFunction
    public /* synthetic */ BiFunction andThen(Function function) {
        return BiFunction.CC.$default$andThen(this, function);
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer.CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.function.BiFunction
    public Object apply(Object obj, Object obj2) {
        return ((Function) this.b).apply(((BiFunction) this.c).apply(obj, obj2));
    }

    public /* synthetic */ a(BiFunction biFunction, Function function) {
        this.c = biFunction;
        this.b = function;
    }

    @Override // j$.util.function.BiConsumer
    public void accept(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                ConcurrentMap concurrentMap = (ConcurrentMap) this.b;
                BiFunction biFunction = (BiFunction) this.c;
                while (!concurrentMap.replace(obj, obj2, biFunction.apply(obj, obj2)) && (obj2 = concurrentMap.get(obj)) != null) {
                }
            default:
                BiConsumer biConsumer = (BiConsumer) this.b;
                BiConsumer biConsumer2 = (BiConsumer) this.c;
                biConsumer.accept(obj, obj2);
                biConsumer2.accept(obj, obj2);
                break;
        }
    }

    public /* synthetic */ a(Consumer consumer, Consumer consumer2) {
        this.b = consumer;
        this.c = consumer2;
    }

    public /* synthetic */ a(ConcurrentMap concurrentMap, BiFunction biFunction) {
        this.b = concurrentMap;
        this.c = biFunction;
    }
}
