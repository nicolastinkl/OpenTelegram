package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BodyType;

/* compiled from: HttpAdapter.java */
/* loaded from: classes.dex */
/* synthetic */ class a {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[BodyType.values().length];
        a = iArr;
        try {
            iArr[BodyType.DATA.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[BodyType.FORM.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[BodyType.JSON.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
