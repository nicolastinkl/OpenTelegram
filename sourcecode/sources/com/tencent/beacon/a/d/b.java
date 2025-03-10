package com.tencent.beacon.a.d;

import java.util.ArrayList;
import java.util.List;

/* compiled from: DBConst.java */
/* loaded from: classes.dex */
public final class b {
    public static final List<String> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add("CREATE TABLE t_r_e ( _id INTEGER PRIMARY KEY AUTOINCREMENT, _appKey text ,_time int ,_length int ,_data  blob)");
        arrayList.add("CREATE TABLE t_n_e ( _id INTEGER PRIMARY KEY AUTOINCREMENT, _appKey text ,_time int ,_length int ,_data  blob)");
    }
}
