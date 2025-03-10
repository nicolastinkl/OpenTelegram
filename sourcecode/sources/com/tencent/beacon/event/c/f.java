package com.tencent.beacon.event.c;

import com.tencent.beacon.event.open.EventType;

/* compiled from: LogIDGenerator.java */
/* loaded from: classes.dex */
/* synthetic */ class f {
    static final /* synthetic */ int[] a;

    static {
        int[] iArr = new int[EventType.values().length];
        a = iArr;
        try {
            iArr[EventType.NORMAL.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            a[EventType.DT_NORMAL.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            a[EventType.REALTIME.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            a[EventType.DT_REALTIME.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            a[EventType.IMMEDIATE_MSF.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[EventType.IMMEDIATE_WNS.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
