package j$.util;

import j$.util.Map;
import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Consumer;
import j$.util.function.Function;
import j$.util.function.Predicate;
import j$.wrappers.C0303q;
import j$.wrappers.C0304s;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public class DesugarCollections {
    public static final Class a;
    static final Class b;
    private static final Field c;
    private static final Field d;
    private static final Constructor e;
    private static final Constructor f;

    static {
        Field field;
        Field field2;
        Constructor<?> constructor;
        Class<?> cls = Collections.synchronizedCollection(new ArrayList()).getClass();
        a = cls;
        b = Collections.synchronizedList(new LinkedList()).getClass();
        Constructor<?> constructor2 = null;
        try {
            field = cls.getDeclaredField("mutex");
        } catch (NoSuchFieldException unused) {
            field = null;
        }
        c = field;
        if (field != null) {
            field.setAccessible(true);
        }
        try {
            field2 = cls.getDeclaredField(com.tencent.qimei.j.c.a);
        } catch (NoSuchFieldException unused2) {
            field2 = null;
        }
        d = field2;
        if (field2 != null) {
            field2.setAccessible(true);
        }
        try {
            constructor = Collections.synchronizedSet(new HashSet()).getClass().getDeclaredConstructor(Set.class, Object.class);
        } catch (NoSuchMethodException unused3) {
            constructor = null;
        }
        f = constructor;
        if (constructor != null) {
            constructor.setAccessible(true);
        }
        try {
            constructor2 = cls.getDeclaredConstructor(Collection.class, Object.class);
        } catch (NoSuchMethodException unused4) {
        }
        e = constructor2;
        if (constructor2 != null) {
            constructor2.setAccessible(true);
        }
    }

    public static void c(Iterable iterable, Consumer consumer) {
        Field field = c;
        if (field == null) {
            try {
                Collection$EL.a((Collection) d.get(iterable), consumer);
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized collection forEach fall-back.", e2);
            }
        } else {
            try {
                synchronized (field.get(iterable)) {
                    Collection$EL.a((Collection) d.get(iterable), consumer);
                }
            } catch (IllegalAccessException e3) {
                throw new Error("Runtime illegal access in synchronized collection forEach.", e3);
            }
        }
    }

    static boolean d(Collection collection, Predicate predicate) {
        boolean removeIf;
        Field field = c;
        if (field == null) {
            try {
                return Collection$EL.removeIf((Collection) d.get(collection), predicate);
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized collection removeIf fall-back.", e2);
            }
        }
        try {
            synchronized (field.get(collection)) {
                removeIf = Collection$EL.removeIf((Collection) d.get(collection), predicate);
            }
            return removeIf;
        } catch (IllegalAccessException e3) {
            throw new Error("Runtime illegal access in synchronized collection removeIf.", e3);
        }
    }

    static void e(List list, java.util.Comparator comparator) {
        Field field = c;
        if (field == null) {
            try {
                AbstractC0112a.v((List) d.get(list), comparator);
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized collection sort fall-back.", e2);
            }
        } else {
            try {
                synchronized (field.get(list)) {
                    AbstractC0112a.v((List) d.get(list), comparator);
                }
            } catch (IllegalAccessException e3) {
                throw new Error("Runtime illegal access in synchronized list sort.", e3);
            }
        }
    }

    public static <K, V> java.util.Map<K, V> synchronizedMap(java.util.Map<K, V> map) {
        return new a(map);
    }

    private static class a implements java.util.Map, Serializable, Map {
        private final java.util.Map a;
        final Object b;
        private transient Set c;
        private transient Set d;
        private transient Collection e;

        a(java.util.Map map) {
            Objects.requireNonNull(map);
            this.a = map;
            this.b = this;
        }

        private Set a(Set set, Object obj) {
            if (DesugarCollections.f == null) {
                return Collections.synchronizedSet(set);
            }
            try {
                return (Set) DesugarCollections.f.newInstance(set, obj);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new Error("Unable to instantiate a synchronized list.", e);
            }
        }

        @Override // java.util.Map, j$.util.Map
        public void clear() {
            synchronized (this.b) {
                this.a.clear();
            }
        }

        @Override // j$.util.Map
        public Object compute(Object obj, BiFunction biFunction) {
            Object $default$compute;
            Object apply;
            synchronized (this.b) {
                try {
                    java.util.Map map = this.a;
                    if (map instanceof Map) {
                        $default$compute = ((Map) map).compute(obj, biFunction);
                    } else if (map instanceof ConcurrentMap) {
                        ConcurrentMap concurrentMap = (ConcurrentMap) map;
                        Objects.requireNonNull(biFunction);
                        loop0: while (true) {
                            Object obj2 = concurrentMap.get(obj);
                            while (true) {
                                apply = biFunction.apply(obj, obj2);
                                if (apply == null) {
                                    apply = null;
                                    if (obj2 == null && !concurrentMap.containsKey(obj)) {
                                        break;
                                    }
                                    if (concurrentMap.remove(obj, obj2)) {
                                        break;
                                    }
                                } else if (obj2 == null) {
                                    obj2 = concurrentMap.putIfAbsent(obj, apply);
                                    if (obj2 == null) {
                                        break loop0;
                                    }
                                } else if (concurrentMap.replace(obj, obj2, apply)) {
                                    break;
                                }
                            }
                        }
                        $default$compute = apply;
                    } else {
                        $default$compute = Map.CC.$default$compute(map, obj, biFunction);
                    }
                } finally {
                }
            }
            return $default$compute;
        }

        @Override // java.util.Map
        public /* synthetic */ Object compute(Object obj, java.util.function.BiFunction biFunction) {
            return compute(obj, C0304s.a(biFunction));
        }

        @Override // j$.util.Map
        public Object computeIfAbsent(Object obj, Function function) {
            Object computeIfAbsent;
            synchronized (this.b) {
                computeIfAbsent = Map.EL.computeIfAbsent(this.a, obj, function);
            }
            return computeIfAbsent;
        }

        @Override // java.util.Map
        public Object computeIfAbsent(Object obj, java.util.function.Function function) {
            Object computeIfAbsent;
            Function a = j$.wrappers.K.a(function);
            synchronized (this.b) {
                computeIfAbsent = Map.EL.computeIfAbsent(this.a, obj, a);
            }
            return computeIfAbsent;
        }

        @Override // j$.util.Map
        public Object computeIfPresent(Object obj, BiFunction biFunction) {
            java.util.Map map;
            Object $default$computeIfPresent;
            synchronized (this.b) {
                try {
                    map = this.a;
                } finally {
                }
                if (map instanceof Map) {
                    $default$computeIfPresent = ((Map) map).computeIfPresent(obj, biFunction);
                } else {
                    if (map instanceof ConcurrentMap) {
                        ConcurrentMap concurrentMap = (ConcurrentMap) map;
                        Objects.requireNonNull(biFunction);
                        while (true) {
                            Object obj2 = concurrentMap.get(obj);
                            if (obj2 == null) {
                                $default$computeIfPresent = obj2;
                                break;
                            }
                            Object apply = biFunction.apply(obj, obj2);
                            if (apply != null) {
                                if (concurrentMap.replace(obj, obj2, apply)) {
                                    $default$computeIfPresent = apply;
                                    break;
                                }
                            } else if (concurrentMap.remove(obj, obj2)) {
                                $default$computeIfPresent = null;
                                break;
                            }
                        }
                    }
                    $default$computeIfPresent = Map.CC.$default$computeIfPresent(map, obj, biFunction);
                }
            }
            return $default$computeIfPresent;
        }

        @Override // java.util.Map
        public /* synthetic */ Object computeIfPresent(Object obj, java.util.function.BiFunction biFunction) {
            return computeIfPresent(obj, C0304s.a(biFunction));
        }

        @Override // java.util.Map, j$.util.Map
        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.b) {
                containsKey = this.a.containsKey(obj);
            }
            return containsKey;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.b) {
                containsValue = this.a.containsValue(obj);
            }
            return containsValue;
        }

        @Override // java.util.Map, j$.util.Map
        public Set entrySet() {
            Set set;
            synchronized (this.b) {
                if (this.d == null) {
                    this.d = a(this.a.entrySet(), this.b);
                }
                set = this.d;
            }
            return set;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean equals(Object obj) {
            boolean equals;
            if (this == obj) {
                return true;
            }
            synchronized (this.b) {
                equals = this.a.equals(obj);
            }
            return equals;
        }

        @Override // j$.util.Map
        public void forEach(BiConsumer biConsumer) {
            synchronized (this.b) {
                Map.EL.forEach(this.a, biConsumer);
            }
        }

        @Override // java.util.Map
        public void forEach(java.util.function.BiConsumer biConsumer) {
            BiConsumer a = C0303q.a(biConsumer);
            synchronized (this.b) {
                Map.EL.forEach(this.a, a);
            }
        }

        @Override // java.util.Map, j$.util.Map
        public Object get(Object obj) {
            Object obj2;
            synchronized (this.b) {
                obj2 = this.a.get(obj);
            }
            return obj2;
        }

        @Override // java.util.Map, j$.util.Map
        public Object getOrDefault(Object obj, Object obj2) {
            Object orDefault;
            synchronized (this.b) {
                orDefault = Map.EL.getOrDefault(this.a, obj, obj2);
            }
            return orDefault;
        }

        @Override // java.util.Map, j$.util.Map
        public int hashCode() {
            int hashCode;
            synchronized (this.b) {
                hashCode = this.a.hashCode();
            }
            return hashCode;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.b) {
                isEmpty = this.a.isEmpty();
            }
            return isEmpty;
        }

        @Override // java.util.Map, j$.util.Map
        public Set keySet() {
            Set set;
            synchronized (this.b) {
                if (this.c == null) {
                    this.c = a(this.a.keySet(), this.b);
                }
                set = this.c;
            }
            return set;
        }

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0022, code lost:
        
            r3 = r7.apply(r2, r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0026, code lost:
        
            if (r3 == null) goto L32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0036, code lost:
        
            if (r1.remove(r5, r2) == false) goto L38;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0038, code lost:
        
            r6 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x002c, code lost:
        
            if (r1.replace(r5, r2, r3) == false) goto L39;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x002e, code lost:
        
            r6 = r3;
         */
        @Override // j$.util.Map
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.lang.Object merge(java.lang.Object r5, java.lang.Object r6, j$.util.function.BiFunction r7) {
            /*
                r4 = this;
                java.lang.Object r0 = r4.b
                monitor-enter(r0)
                java.util.Map r1 = r4.a     // Catch: java.lang.Throwable -> L30
                boolean r2 = r1 instanceof j$.util.Map     // Catch: java.lang.Throwable -> L30
                if (r2 == 0) goto L10
                j$.util.Map r1 = (j$.util.Map) r1     // Catch: java.lang.Throwable -> L30
                java.lang.Object r5 = r1.merge(r5, r6, r7)     // Catch: java.lang.Throwable -> L30
                goto L46
            L10:
                boolean r2 = r1 instanceof java.util.concurrent.ConcurrentMap     // Catch: java.lang.Throwable -> L30
                if (r2 == 0) goto L42
                java.util.concurrent.ConcurrentMap r1 = (java.util.concurrent.ConcurrentMap) r1     // Catch: java.lang.Throwable -> L30
                java.util.Objects.requireNonNull(r7)     // Catch: java.lang.Throwable -> L30
                java.util.Objects.requireNonNull(r6)     // Catch: java.lang.Throwable -> L30
            L1c:
                java.lang.Object r2 = r1.get(r5)     // Catch: java.lang.Throwable -> L30
            L20:
                if (r2 == 0) goto L3a
                java.lang.Object r3 = r7.apply(r2, r6)     // Catch: java.lang.Throwable -> L30
                if (r3 == 0) goto L32
                boolean r2 = r1.replace(r5, r2, r3)     // Catch: java.lang.Throwable -> L30
                if (r2 == 0) goto L1c
                r6 = r3
                goto L40
            L30:
                r5 = move-exception
                goto L48
            L32:
                boolean r2 = r1.remove(r5, r2)     // Catch: java.lang.Throwable -> L30
                if (r2 == 0) goto L1c
                r6 = 0
                goto L40
            L3a:
                java.lang.Object r2 = r1.putIfAbsent(r5, r6)     // Catch: java.lang.Throwable -> L30
                if (r2 != 0) goto L20
            L40:
                r5 = r6
                goto L46
            L42:
                java.lang.Object r5 = j$.util.Map.CC.$default$merge(r1, r5, r6, r7)     // Catch: java.lang.Throwable -> L30
            L46:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L30
                return r5
            L48:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L30
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.DesugarCollections.a.merge(java.lang.Object, java.lang.Object, j$.util.function.BiFunction):java.lang.Object");
        }

        @Override // java.util.Map
        public /* synthetic */ Object merge(Object obj, Object obj2, java.util.function.BiFunction biFunction) {
            return merge(obj, obj2, C0304s.a(biFunction));
        }

        @Override // java.util.Map, j$.util.Map
        public Object put(Object obj, Object obj2) {
            Object put;
            synchronized (this.b) {
                put = this.a.put(obj, obj2);
            }
            return put;
        }

        @Override // java.util.Map, j$.util.Map
        public void putAll(java.util.Map map) {
            synchronized (this.b) {
                this.a.putAll(map);
            }
        }

        @Override // java.util.Map, j$.util.Map
        public Object putIfAbsent(Object obj, Object obj2) {
            Object putIfAbsent;
            synchronized (this.b) {
                putIfAbsent = Map.EL.putIfAbsent(this.a, obj, obj2);
            }
            return putIfAbsent;
        }

        @Override // java.util.Map, j$.util.Map
        public Object remove(Object obj) {
            Object remove;
            synchronized (this.b) {
                remove = this.a.remove(obj);
            }
            return remove;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean remove(Object obj, Object obj2) {
            boolean remove;
            synchronized (this.b) {
                try {
                    java.util.Map map = this.a;
                    remove = map instanceof Map ? ((Map) map).remove(obj, obj2) : Map.CC.$default$remove(map, obj, obj2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return remove;
        }

        @Override // java.util.Map, j$.util.Map
        public Object replace(Object obj, Object obj2) {
            Object replace;
            synchronized (this.b) {
                try {
                    java.util.Map map = this.a;
                    replace = map instanceof Map ? ((Map) map).replace(obj, obj2) : Map.CC.$default$replace(map, obj, obj2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return replace;
        }

        @Override // j$.util.Map
        public void replaceAll(BiFunction biFunction) {
            synchronized (this.b) {
                try {
                    java.util.Map map = this.a;
                    if (map instanceof Map) {
                        ((Map) map).replaceAll(biFunction);
                    } else if (map instanceof ConcurrentMap) {
                        ConcurrentMap concurrentMap = (ConcurrentMap) map;
                        Objects.requireNonNull(biFunction);
                        j$.util.concurrent.a aVar = new j$.util.concurrent.a(concurrentMap, biFunction);
                        if (concurrentMap instanceof j$.util.concurrent.b) {
                            ((j$.util.concurrent.b) concurrentMap).forEach(aVar);
                        } else {
                            j$.lang.d.a(concurrentMap, aVar);
                        }
                    } else {
                        Map.CC.$default$replaceAll(map, biFunction);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // java.util.Map
        public /* synthetic */ void replaceAll(java.util.function.BiFunction biFunction) {
            replaceAll(C0304s.a(biFunction));
        }

        @Override // java.util.Map, j$.util.Map
        public int size() {
            int size;
            synchronized (this.b) {
                size = this.a.size();
            }
            return size;
        }

        public String toString() {
            String obj;
            synchronized (this.b) {
                obj = this.a.toString();
            }
            return obj;
        }

        @Override // java.util.Map, j$.util.Map
        public Collection values() {
            Collection collection;
            Collection collection2;
            synchronized (this.b) {
                try {
                    if (this.e == null) {
                        Collection values = this.a.values();
                        Object obj = this.b;
                        if (DesugarCollections.e == null) {
                            collection2 = Collections.synchronizedCollection(values);
                        } else {
                            try {
                                collection2 = (Collection) DesugarCollections.e.newInstance(values, obj);
                            } catch (IllegalAccessException e) {
                                e = e;
                                throw new Error("Unable to instantiate a synchronized list.", e);
                            } catch (InstantiationException e2) {
                                e = e2;
                                throw new Error("Unable to instantiate a synchronized list.", e);
                            } catch (InvocationTargetException e3) {
                                e = e3;
                                throw new Error("Unable to instantiate a synchronized list.", e);
                            }
                        }
                        this.e = collection2;
                    }
                    collection = this.e;
                } finally {
                }
            }
            return collection;
        }

        @Override // java.util.Map, j$.util.Map
        public boolean replace(Object obj, Object obj2, Object obj3) {
            boolean replace;
            synchronized (this.b) {
                try {
                    java.util.Map map = this.a;
                    replace = map instanceof Map ? ((Map) map).replace(obj, obj2, obj3) : Map.CC.$default$replace(map, obj, obj2, obj3);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return replace;
        }
    }
}
