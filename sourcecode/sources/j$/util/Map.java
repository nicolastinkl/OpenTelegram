package j$.util;

import j$.util.function.BiConsumer;
import j$.util.function.BiFunction;
import j$.util.function.Function;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes2.dex */
public interface Map<K, V> {

    /* renamed from: j$.util.Map$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        /* JADX WARN: Multi-variable type inference failed */
        public static Object $default$compute(java.util.Map map, Object obj, BiFunction biFunction) {
            Objects.requireNonNull(biFunction);
            Object obj2 = map.get(obj);
            Object apply = biFunction.apply(obj, obj2);
            if (apply != null) {
                map.put(obj, apply);
                return apply;
            }
            if (obj2 == null && !map.containsKey(obj)) {
                return null;
            }
            map.remove(obj);
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static Object $default$computeIfAbsent(java.util.Map map, Object obj, Function function) {
            Object apply;
            Objects.requireNonNull(function);
            Object obj2 = map.get(obj);
            if (obj2 != null || (apply = function.apply(obj)) == null) {
                return obj2;
            }
            map.put(obj, apply);
            return apply;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static Object $default$computeIfPresent(java.util.Map map, Object obj, BiFunction biFunction) {
            Objects.requireNonNull(biFunction);
            Object obj2 = map.get(obj);
            if (obj2 != null) {
                Object apply = biFunction.apply(obj, obj2);
                if (apply != null) {
                    map.put(obj, apply);
                    return apply;
                }
                map.remove(obj);
            }
            return null;
        }

        public static void $default$forEach(java.util.Map map, BiConsumer biConsumer) {
            Objects.requireNonNull(biConsumer);
            for (Map.Entry<K, V> entry : map.entrySet()) {
                try {
                    biConsumer.accept(entry.getKey(), entry.getValue());
                } catch (IllegalStateException e) {
                    throw new ConcurrentModificationException(e);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static Object $default$merge(java.util.Map map, Object obj, Object obj2, BiFunction biFunction) {
            Objects.requireNonNull(biFunction);
            Objects.requireNonNull(obj2);
            Object obj3 = map.get(obj);
            if (obj3 != null) {
                obj2 = biFunction.apply(obj3, obj2);
            }
            if (obj2 == null) {
                map.remove(obj);
            } else {
                map.put(obj, obj2);
            }
            return obj2;
        }

        public static Object $default$putIfAbsent(java.util.Map map, Object obj, Object obj2) {
            Object obj3 = map.get(obj);
            return obj3 == null ? map.put(obj, obj2) : obj3;
        }

        public static boolean $default$remove(java.util.Map map, Object obj, Object obj2) {
            Object obj3 = map.get(obj);
            if (!AbstractC0112a.u(obj3, obj2)) {
                return false;
            }
            if (obj3 == null && !map.containsKey(obj)) {
                return false;
            }
            map.remove(obj);
            return true;
        }

        public static Object $default$replace(java.util.Map map, Object obj, Object obj2) {
            Object obj3 = map.get(obj);
            return (obj3 != null || map.containsKey(obj)) ? map.put(obj, obj2) : obj3;
        }

        public static boolean $default$replace(java.util.Map map, Object obj, Object obj2, Object obj3) {
            Object obj4 = map.get(obj);
            if (!AbstractC0112a.u(obj4, obj2)) {
                return false;
            }
            if (obj4 == null && !map.containsKey(obj)) {
                return false;
            }
            map.put(obj, obj3);
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static void $default$replaceAll(java.util.Map map, BiFunction biFunction) {
            Objects.requireNonNull(biFunction);
            for (Map.Entry<K, V> entry : map.entrySet()) {
                try {
                    try {
                        entry.setValue(biFunction.apply(entry.getKey(), entry.getValue()));
                    } catch (IllegalStateException e) {
                        throw new ConcurrentModificationException(e);
                    }
                } catch (IllegalStateException e2) {
                    throw new ConcurrentModificationException(e2);
                }
            }
        }
    }

    /* renamed from: j$.util.Map$-EL, reason: invalid class name */
    public final /* synthetic */ class EL {
        /* JADX WARN: Multi-variable type inference failed */
        public static Object computeIfAbsent(java.util.Map map, Object obj, Function function) {
            Object apply;
            if (map instanceof Map) {
                return ((Map) map).computeIfAbsent(obj, function);
            }
            if (!(map instanceof ConcurrentMap)) {
                return CC.$default$computeIfAbsent(map, obj, function);
            }
            ConcurrentMap concurrentMap = (ConcurrentMap) map;
            Objects.requireNonNull(function);
            Object obj2 = concurrentMap.get(obj);
            return (obj2 == null && (apply = function.apply(obj)) != null && (obj2 = concurrentMap.putIfAbsent(obj, apply)) == null) ? apply : obj2;
        }

        public static /* synthetic */ void forEach(java.util.Map map, BiConsumer biConsumer) {
            if (map instanceof Map) {
                ((Map) map).forEach(biConsumer);
            } else if (map instanceof ConcurrentMap) {
                j$.lang.d.a((ConcurrentMap) map, biConsumer);
            } else {
                CC.$default$forEach(map, biConsumer);
            }
        }

        public static Object getOrDefault(java.util.Map map, Object obj, Object obj2) {
            if (map instanceof Map) {
                return ((Map) map).getOrDefault(obj, obj2);
            }
            if (map instanceof ConcurrentMap) {
                Object obj3 = ((ConcurrentMap) map).get(obj);
                return obj3 != null ? obj3 : obj2;
            }
            Object obj4 = map.get(obj);
            return (obj4 != null || map.containsKey(obj)) ? obj4 : obj2;
        }

        public static /* synthetic */ Object putIfAbsent(java.util.Map map, Object obj, Object obj2) {
            return map instanceof Map ? ((Map) map).putIfAbsent(obj, obj2) : CC.$default$putIfAbsent(map, obj, obj2);
        }
    }

    public interface Entry<K, V> {
        boolean equals(Object obj);

        K getKey();

        V getValue();

        int hashCode();

        V setValue(V v);
    }

    void clear();

    V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction);

    V computeIfAbsent(K k, Function<? super K, ? extends V> function);

    V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction);

    boolean containsKey(Object obj);

    boolean containsValue(Object obj);

    Set<Map.Entry<K, V>> entrySet();

    boolean equals(Object obj);

    void forEach(BiConsumer<? super K, ? super V> biConsumer);

    V get(Object obj);

    V getOrDefault(Object obj, V v);

    int hashCode();

    boolean isEmpty();

    Set<K> keySet();

    V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction);

    V put(K k, V v);

    void putAll(java.util.Map<? extends K, ? extends V> map);

    V putIfAbsent(K k, V v);

    V remove(Object obj);

    boolean remove(Object obj, Object obj2);

    V replace(K k, V v);

    boolean replace(K k, V v, V v2);

    void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction);

    int size();

    Collection<V> values();
}
