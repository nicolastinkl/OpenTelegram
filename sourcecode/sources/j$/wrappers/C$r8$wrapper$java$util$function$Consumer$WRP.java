package j$.wrappers;

import java.util.function.Consumer;

/* renamed from: j$.wrappers.$r8$wrapper$java$util$function$Consumer$-WRP, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class C$r8$wrapper$java$util$function$Consumer$WRP implements Consumer {
    final /* synthetic */ j$.util.function.Consumer a;

    private /* synthetic */ C$r8$wrapper$java$util$function$Consumer$WRP(j$.util.function.Consumer consumer) {
        this.a = consumer;
    }

    public static /* synthetic */ Consumer convert(j$.util.function.Consumer consumer) {
        if (consumer == null) {
            return null;
        }
        return consumer instanceof C0307v ? ((C0307v) consumer).a : new C$r8$wrapper$java$util$function$Consumer$WRP(consumer);
    }

    @Override // java.util.function.Consumer
    public /* synthetic */ void accept(Object obj) {
        this.a.accept(obj);
    }

    @Override // java.util.function.Consumer
    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return convert(this.a.andThen(C0307v.b(consumer)));
    }
}
