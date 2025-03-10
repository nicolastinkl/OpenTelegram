package com.tencent.beacon.a.c;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* compiled from: DeviceInfo.java */
/* loaded from: classes.dex */
class d implements FileFilter {
    final /* synthetic */ e a;

    d(e eVar) {
        this.a = eVar;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return Pattern.matches("cpu[0-9]", file.getName());
    }
}
