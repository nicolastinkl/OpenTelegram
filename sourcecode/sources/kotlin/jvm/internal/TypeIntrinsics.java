package kotlin.jvm.internal;

import java.util.List;
import kotlin.jvm.internal.markers.KMappedMarker;

/* loaded from: classes.dex */
public class TypeIntrinsics {
    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return (T) Intrinsics.sanitizeStackTrace(t, TypeIntrinsics.class.getName());
    }

    public static void throwCce(Object obj, String str) {
        throwCce((obj == null ? "null" : obj.getClass().getName()) + " cannot be cast to " + str);
    }

    public static void throwCce(String str) {
        throw throwCce(new ClassCastException(str));
    }

    public static ClassCastException throwCce(ClassCastException classCastException) {
        throw ((ClassCastException) sanitizeStackTrace(classCastException));
    }

    public static List asMutableList(Object obj) {
        if (obj instanceof KMappedMarker) {
            throwCce(obj, "kotlin.collections.MutableList");
        }
        return castToList(obj);
    }

    public static List castToList(Object obj) {
        try {
            return (List) obj;
        } catch (ClassCastException e) {
            throw throwCce(e);
        }
    }
}
