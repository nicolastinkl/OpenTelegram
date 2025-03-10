package com.tencent.beacon.base.net.call;

import android.text.TextUtils;
import com.tencent.beacon.base.net.RequestType;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.RequestPackageV2;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class JceRequestEntity {
    private static final String TAG = "JceRequestEntity";
    private final String appKey;
    private final byte[] content;
    private final String domain;
    private final Map<String, String> header;
    private final int port;
    private final int requestCmd;
    private final int responseCmd;
    private final RequestType type;
    private final String url;

    public static final class a {
        private String a;
        private int b;
        private String c;
        private int d;
        private int e;
        private RequestType f;
        private String g;
        private Map<String, String> h = new ConcurrentHashMap(5);
        private Map<String, String> i = new LinkedHashMap(10);
        private byte[] j;
        private AbstractJceStruct k;

        public a a(RequestType requestType) {
            this.f = requestType;
            return this;
        }

        public a b(String str) {
            this.a = str;
            return this;
        }

        public a a(String str, int i) {
            this.g = str;
            this.b = i;
            return this;
        }

        public a b(int i) {
            this.e = i;
            return this;
        }

        public a b(String str, String str2) {
            if (str2 == null) {
                str2 = "";
            }
            this.i.put(str, str2);
            return this;
        }

        public a a(String str, String str2) {
            this.h.put(str, str2);
            return this;
        }

        public a a(int i) {
            this.d = i;
            return this;
        }

        public a a(Map<String, String> map) {
            if (map != null) {
                this.i.putAll(map);
            }
            return this;
        }

        public a a(String str) {
            this.c = str;
            return this;
        }

        public a a(AbstractJceStruct abstractJceStruct) {
            this.k = abstractJceStruct;
            return this;
        }

        public JceRequestEntity a() {
            if (TextUtils.isEmpty(this.a) && TextUtils.isEmpty(this.g)) {
                throw new IllegalArgumentException("url || domain == null");
            }
            if (!TextUtils.isEmpty(this.c)) {
                com.tencent.beacon.base.net.c c = com.tencent.beacon.base.net.c.c();
                this.h.putAll(com.tencent.beacon.base.net.b.d.a());
                if (this.f == RequestType.EVENT) {
                    this.j = c.e.c().a((RequestPackageV2) this.k);
                } else {
                    AbstractJceStruct abstractJceStruct = this.k;
                    this.j = c.d.c().a(com.tencent.beacon.base.net.b.d.a(this.d, abstractJceStruct == null ? "".getBytes() : abstractJceStruct.toByteArray(), this.i, this.c));
                }
                return new JceRequestEntity(this.f, this.a, this.g, this.b, this.c, this.j, this.h, this.d, this.e);
            }
            throw new IllegalArgumentException("appKey == null");
        }
    }

    public static a builder() {
        return new a();
    }

    public String getAppKey() {
        return this.appKey;
    }

    public byte[] getContent() {
        return this.content;
    }

    public String getDomain() {
        return this.domain;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public int getPort() {
        return this.port;
    }

    public int getRequestCmd() {
        return this.requestCmd;
    }

    public int getResponseCmd() {
        return this.responseCmd;
    }

    public RequestType getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return "JceRequestEntity{type=" + this.type + ", url='" + this.url + "', domain='" + this.domain + "', port=" + this.port + ", appKey='" + this.appKey + "', content.length=" + this.content.length + ", header=" + this.header + ", requestCmd=" + this.requestCmd + ", responseCmd=" + this.responseCmd + '}';
    }

    private JceRequestEntity(RequestType requestType, String str, String str2, int i, String str3, byte[] bArr, Map<String, String> map, int i2, int i3) {
        this.type = requestType;
        this.url = str;
        this.domain = str2;
        this.port = i;
        this.appKey = str3;
        this.content = bArr;
        this.header = map;
        this.requestCmd = i2;
        this.responseCmd = i3;
    }
}
