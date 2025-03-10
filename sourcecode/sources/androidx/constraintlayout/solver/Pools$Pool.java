package androidx.constraintlayout.solver;

/* loaded from: classes.dex */
interface Pools$Pool<T> {
    T acquire();

    boolean release(T t);

    void releaseAll(T[] tArr, int i);
}
