package j$.wrappers;

import j$.util.function.Consumer;

/* renamed from: j$.wrappers.v, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public final /* synthetic */ class C0307v implements Consumer {
    final /* synthetic */ java.util.function.Consumer a;

    private /* synthetic */ C0307v(java.util.function.Consumer consumer) {
        this.a = consumer;
    }

    public static /* synthetic */ Consumer b(java.util.function.Consumer consumer) {
        if (consumer == null) {
            return null;
        }
        return consumer instanceof C$r8$wrapper$java$util$function$Consumer$WRP ? ((C$r8$wrapper$java$util$function$Consumer$WRP) consumer).a : new C0307v(consumer);
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ void accept(Object obj) {
        this.a.accept(obj);
    }

    @Override // j$.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return b(this.a.andThen(C$r8$wrapper$java$util$function$Consumer$WRP.convert(consumer)));
    }
}
