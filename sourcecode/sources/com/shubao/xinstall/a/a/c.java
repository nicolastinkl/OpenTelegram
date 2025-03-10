package com.shubao.xinstall.a.a;

/* loaded from: classes.dex */
public final class c {
    public static final c a = new c(1, "初始化成功");
    public static final c b = new c(2, "未初始化");
    public static final c c = new c(3, "正在初始化");
    public static final c d = new c(4, "初始化失败");
    public static final c e = new c(5, "未初始化，因为网络不可用");
    int f;
    private String g;

    private c(int i, String str) {
        this.f = i;
        this.g = str;
    }

    public static c a(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? b : e : d : c : b : a;
    }
}
