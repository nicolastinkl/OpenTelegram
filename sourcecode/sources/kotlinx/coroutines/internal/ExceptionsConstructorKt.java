package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CopyableThrowable;

/* compiled from: ExceptionsConstructor.kt */
/* loaded from: classes3.dex */
public final class ExceptionsConstructorKt {
    private static final CtorCache ctorCache;
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    static {
        CtorCache ctorCache2;
        try {
            ctorCache2 = FastServiceLoaderKt.getANDROID_DETECTED() ? WeakMapCtorCache.INSTANCE : ClassValueCtorCache.INSTANCE;
        } catch (Throwable unused) {
            ctorCache2 = WeakMapCtorCache.INSTANCE;
        }
        ctorCache = ctorCache2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <E extends Throwable> E tryCopyException(E e) {
        Object m164constructorimpl;
        if (e instanceof CopyableThrowable) {
            try {
                Result.Companion companion = Result.Companion;
                m164constructorimpl = Result.m164constructorimpl(((CopyableThrowable) e).createCopy());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m166isFailureimpl(m164constructorimpl)) {
                m164constructorimpl = null;
            }
            return (E) m164constructorimpl;
        }
        return (E) ctorCache.get(e.getClass()).invoke(e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <E extends Throwable> Function1<Throwable, Throwable> createConstructor(Class<E> cls) {
        List sortedWith;
        ExceptionsConstructorKt$createConstructor$nullResult$1 exceptionsConstructorKt$createConstructor$nullResult$1 = new Function1() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$nullResult$1
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(Throwable th) {
                return null;
            }
        };
        if (throwableFields != fieldsCountOrDefault(cls, 0)) {
            return exceptionsConstructorKt$createConstructor$nullResult$1;
        }
        sortedWith = ArraysKt___ArraysKt.sortedWith(cls.getConstructors(), new Comparator() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createConstructor$$inlined$sortedByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int compareValues;
                compareValues = ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(((Constructor) t2).getParameterTypes().length), Integer.valueOf(((Constructor) t).getParameterTypes().length));
                return compareValues;
            }
        });
        Iterator it = sortedWith.iterator();
        while (it.hasNext()) {
            Function1<Throwable, Throwable> createSafeConstructor = createSafeConstructor((Constructor) it.next());
            if (createSafeConstructor != null) {
                return createSafeConstructor;
            }
        }
        return exceptionsConstructorKt$createConstructor$nullResult$1;
    }

    private static final Function1<Throwable, Throwable> createSafeConstructor(final Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        int length = parameterTypes.length;
        if (length == 0) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Throwable invoke(Throwable th) {
                    Object m164constructorimpl;
                    Object newInstance;
                    try {
                        Result.Companion companion = Result.Companion;
                        newInstance = constructor.newInstance(new Object[0]);
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.Companion;
                        m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (newInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    Throwable th3 = (Throwable) newInstance;
                    th3.initCause(th);
                    m164constructorimpl = Result.m164constructorimpl(th3);
                    if (Result.m166isFailureimpl(m164constructorimpl)) {
                        m164constructorimpl = null;
                    }
                    return (Throwable) m164constructorimpl;
                }
            };
        }
        if (length != 1) {
            if (length == 2 && Intrinsics.areEqual(parameterTypes[0], String.class) && Intrinsics.areEqual(parameterTypes[1], Throwable.class)) {
                return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Throwable invoke(Throwable th) {
                        Object m164constructorimpl;
                        Object newInstance;
                        try {
                            Result.Companion companion = Result.Companion;
                            newInstance = constructor.newInstance(th.getMessage(), th);
                        } catch (Throwable th2) {
                            Result.Companion companion2 = Result.Companion;
                            m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th2));
                        }
                        if (newInstance == null) {
                            throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                        }
                        m164constructorimpl = Result.m164constructorimpl((Throwable) newInstance);
                        if (Result.m166isFailureimpl(m164constructorimpl)) {
                            m164constructorimpl = null;
                        }
                        return (Throwable) m164constructorimpl;
                    }
                };
            }
            return null;
        }
        Class<?> cls = parameterTypes[0];
        if (Intrinsics.areEqual(cls, Throwable.class)) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Throwable invoke(Throwable th) {
                    Object m164constructorimpl;
                    Object newInstance;
                    try {
                        Result.Companion companion = Result.Companion;
                        newInstance = constructor.newInstance(th);
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.Companion;
                        m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (newInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    m164constructorimpl = Result.m164constructorimpl((Throwable) newInstance);
                    if (Result.m166isFailureimpl(m164constructorimpl)) {
                        m164constructorimpl = null;
                    }
                    return (Throwable) m164constructorimpl;
                }
            };
        }
        if (Intrinsics.areEqual(cls, String.class)) {
            return new Function1<Throwable, Throwable>() { // from class: kotlinx.coroutines.internal.ExceptionsConstructorKt$createSafeConstructor$$inlined$safeCtor$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Throwable invoke(Throwable th) {
                    Object m164constructorimpl;
                    Object newInstance;
                    try {
                        Result.Companion companion = Result.Companion;
                        newInstance = constructor.newInstance(th.getMessage());
                    } catch (Throwable th2) {
                        Result.Companion companion2 = Result.Companion;
                        m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th2));
                    }
                    if (newInstance == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.Throwable");
                    }
                    Throwable th3 = (Throwable) newInstance;
                    th3.initCause(th);
                    m164constructorimpl = Result.m164constructorimpl(th3);
                    if (Result.m166isFailureimpl(m164constructorimpl)) {
                        m164constructorimpl = null;
                    }
                    return (Throwable) m164constructorimpl;
                }
            };
        }
        return null;
    }

    private static final int fieldsCountOrDefault(Class<?> cls, int i) {
        Object m164constructorimpl;
        JvmClassMappingKt.getKotlinClass(cls);
        try {
            Result.Companion companion = Result.Companion;
            m164constructorimpl = Result.m164constructorimpl(Integer.valueOf(fieldsCount$default(cls, 0, 1, null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            m164constructorimpl = Result.m164constructorimpl(ResultKt.createFailure(th));
        }
        Integer valueOf = Integer.valueOf(i);
        if (Result.m166isFailureimpl(m164constructorimpl)) {
            m164constructorimpl = valueOf;
        }
        return ((Number) m164constructorimpl).intValue();
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }

    private static final int fieldsCount(Class<?> cls, int i) {
        do {
            Field[] declaredFields = cls.getDeclaredFields();
            int length = declaredFields.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                Field field = declaredFields[i2];
                i2++;
                if (!Modifier.isStatic(field.getModifiers())) {
                    i3++;
                }
            }
            i += i3;
            cls = cls.getSuperclass();
        } while (cls != null);
        return i;
    }
}
