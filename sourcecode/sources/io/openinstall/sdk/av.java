package io.openinstall.sdk;

/* loaded from: classes.dex */
public final class av {
    public static final av a = new av(1, "未初始化");
    public static final av b = new av(2, "正在初始化");
    public static final av c = new av(-1, "初始化失败");
    public static final av d = new av(0, "初始化成功");
    public static final av e = new av(-2, "初始化错误");
    public static final av f = new av(-3, "初始化被禁止");
    private final int g;
    private final String h;

    av(int i, String str) {
        this.g = i;
        this.h = str;
    }

    public static av a(int i) {
        return i != -3 ? i != -2 ? i != -1 ? i != 0 ? i != 2 ? a : b : d : c : e : f;
    }

    public int a() {
        return this.g;
    }
}
