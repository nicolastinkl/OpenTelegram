package com.google.gson;

/* loaded from: classes.dex */
public interface ReflectionAccessFilter {

    public enum FilterResult {
        ALLOW,
        INDECISIVE,
        BLOCK_INACCESSIBLE,
        BLOCK_ALL
    }

    FilterResult check(Class<?> cls);
}
