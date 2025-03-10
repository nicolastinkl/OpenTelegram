package org.aspectj.lang;

/* loaded from: classes3.dex */
public interface JoinPoint {

    public interface StaticPart {
        String toString();
    }

    Object getTarget();
}
