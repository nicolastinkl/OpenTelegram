package com.tencent.beacon.base.net.call;

import com.tencent.beacon.base.net.BodyType;

/* compiled from: HttpRequestEntity.java */
/* loaded from: classes.dex */
/* synthetic */ class d {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[BodyType.values().length];
        a = iArr;
        try {
            iArr[BodyType.JSON.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[BodyType.FORM.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[BodyType.DATA.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
