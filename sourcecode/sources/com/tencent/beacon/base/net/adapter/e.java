package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BodyType;

/* compiled from: OkHttpAdapter.java */
/* loaded from: classes.dex */
/* synthetic */ class e {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[BodyType.values().length];
        a = iArr;
        try {
            iArr[BodyType.FORM.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[BodyType.JSON.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[BodyType.DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
