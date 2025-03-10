package org.slf4j.helpers;

/* loaded from: classes3.dex */
public abstract class MarkerIgnoringBase extends NamedLoggerBase {
    @Override // org.slf4j.Logger
    public abstract /* bridge */ /* synthetic */ String getName();

    public String toString() {
        return getClass().getName() + "(" + getName() + ")";
    }
}
