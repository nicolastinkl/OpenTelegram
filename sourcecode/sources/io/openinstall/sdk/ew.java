package io.openinstall.sdk;

/* loaded from: classes.dex */
public class ew {
    private String a;
    private a b;

    public enum a {
        NOT_INIT(-8, "未调用初始化", "initialize is not called"),
        INIT_ERROR(-12, "初始化时错误", "Initialization returned an error"),
        REQUEST_FAIL(-1, "请求失败", "request failed"),
        REQUEST_EXCEPTION(-1, "请求异常", "request exception"),
        REQUEST_ERROR(-2, "请求错误", "request returned an error"),
        REQUEST_TIMEOUT(-4, "请求超时，请重试", "request timeout. Please try again"),
        INVALID_INTENT(-7, "无效的 intent ", "invalid intent"),
        INVALID_DATA(-7, "无效的 data ", "invalid data");

        public final int i;
        public final String j;
        public final String k;
        public String l;

        a(int i, String str, String str2) {
            this.i = i;
            this.j = str;
            this.k = str2;
        }

        public ew a() {
            return new ew(this);
        }

        public ew a(String str) {
            this.l = str;
            return new ew(this);
        }
    }

    private ew(a aVar) {
        this.b = aVar;
    }

    private ew(String str) {
        this.a = str;
    }

    public static ew a() {
        return a("");
    }

    public static ew a(es esVar) {
        if (!(esVar instanceof ep)) {
            return esVar instanceof eq ? a.REQUEST_EXCEPTION.a(esVar.f()) : esVar instanceof er ? a.REQUEST_FAIL.a(esVar.f()) : a();
        }
        ep epVar = (ep) esVar;
        return epVar.a() == 0 ? a(epVar.c()) : a.REQUEST_ERROR.a(esVar.f());
    }

    public static ew a(String str) {
        return new ew(str);
    }

    public String b() {
        return this.a;
    }

    public a c() {
        return this.b;
    }
}
