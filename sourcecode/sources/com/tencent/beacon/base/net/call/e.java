package com.tencent.beacon.base.net.call;

import android.text.TextUtils;
import com.tencent.beacon.base.net.BodyType;
import com.tencent.beacon.base.net.HttpMethod;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: HttpRequestEntity.java */
/* loaded from: classes.dex */
public class e {
    private final String a;
    private final HttpMethod b;
    private final Map<String, String> c;
    private final Map<String, String> d;
    private final String e;
    private BodyType f;
    private String g;
    private byte[] h;

    /* synthetic */ e(HttpMethod httpMethod, String str, Map map, BodyType bodyType, String str2, Map map2, byte[] bArr, String str3, d dVar) {
        this(httpMethod, str, map, bodyType, str2, map2, bArr, str3);
    }

    public static a b() {
        return new a();
    }

    public BodyType a() {
        return this.f;
    }

    public byte[] c() {
        return this.h;
    }

    public Map<String, String> d() {
        return this.d;
    }

    public Map<String, String> e() {
        return this.c;
    }

    public String f() {
        return this.g;
    }

    public HttpMethod g() {
        return this.b;
    }

    public String h() {
        return this.e;
    }

    public String i() {
        return this.a;
    }

    public String toString() {
        return "HttpRequestEntity{url='" + this.a + "', method=" + this.b + ", headers=" + this.c + ", formParams=" + this.d + ", bodyType=" + this.f + ", json='" + this.g + "', tag='" + this.e + "'}";
    }

    private e(HttpMethod httpMethod, String str, Map<String, String> map, BodyType bodyType, String str2, Map<String, String> map2, byte[] bArr, String str3) {
        this.b = httpMethod;
        this.a = str;
        this.c = map;
        this.f = bodyType;
        this.g = str2;
        this.d = map2;
        this.h = bArr;
        this.e = str3;
    }

    /* compiled from: HttpRequestEntity.java */
    public static class a {
        private HttpMethod a;
        private String b;
        private String c;
        private Map<String, String> d = new HashMap(3);
        private Map<String, String> e = new HashMap(3);
        private String f;
        private BodyType g;
        private byte[] h;

        private void a(BodyType bodyType) {
            if (this.g == null) {
                this.g = bodyType;
            }
            if (this.g != bodyType) {
                throw new IllegalStateException("bodyType already set!");
            }
        }

        public a b(String str) {
            this.b = str;
            return this;
        }

        public a a(HttpMethod httpMethod) {
            this.a = httpMethod;
            return this;
        }

        public a a(Map<String, String> map) {
            a(BodyType.FORM);
            this.d.putAll(map);
            return this;
        }

        public a a(String str) {
            this.c = str;
            return this;
        }

        public e a() {
            Objects.requireNonNull(this.a, "request method == null");
            if (!TextUtils.isEmpty(this.b)) {
                BodyType bodyType = this.g;
                Objects.requireNonNull(bodyType, "bodyType == null");
                int i = d.a[bodyType.ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            Objects.requireNonNull(this.h, "data request body == null");
                        }
                    } else if (this.d.isEmpty()) {
                        throw new NullPointerException("form request body == null");
                    }
                } else if (TextUtils.isEmpty(this.f)) {
                    throw new NullPointerException("json request body == null");
                }
                return new e(this.a, this.b, this.e, this.g, this.f, this.d, this.h, this.c, null);
            }
            throw new NullPointerException("request url == null!");
        }
    }
}
