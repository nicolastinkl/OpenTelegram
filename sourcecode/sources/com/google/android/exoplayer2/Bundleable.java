package com.google.android.exoplayer2;

import android.os.Bundle;

/* loaded from: classes.dex */
public interface Bundleable {

    public interface Creator<T extends Bundleable> {
        T fromBundle(Bundle bundle);
    }

    Bundle toBundle();
}
