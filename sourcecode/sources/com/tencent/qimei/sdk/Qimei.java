package com.tencent.qimei.sdk;

import android.text.TextUtils;
import com.tencent.qimei.v.d;

/* loaded from: classes.dex */
public final class Qimei {
    public String a;
    public String b;
    public String c;

    public Qimei() {
        this("", "");
    }

    public String a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    public String getQimei16() {
        return !d.a(this.a).i() ? "" : this.b;
    }

    public String getQimei36() {
        return !d.a(this.a).G() ? "" : this.c;
    }

    public boolean isEmpty() {
        String str;
        String str2 = this.b;
        return (str2 == null || str2.isEmpty()) && ((str = this.c) == null || str.isEmpty());
    }

    public void setAppKey(String str) {
        this.a = str;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Q16:");
        sb.append(this.b);
        if (TextUtils.isEmpty(this.c)) {
            str = "";
        } else {
            str = "\nQ36:" + this.c;
        }
        sb.append(str);
        return sb.toString();
    }

    public Qimei(String str, String str2) {
        this.b = str == null ? "" : str;
        this.c = str2 == null ? "" : str2;
    }

    @Deprecated
    public void a(String str) {
        this.b = str;
    }

    public void b(String str) {
        this.c = str;
    }
}
