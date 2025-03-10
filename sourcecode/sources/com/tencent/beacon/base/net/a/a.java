package com.tencent.beacon.base.net.a;

import com.tencent.beacon.base.net.a.c;
import com.tencent.beacon.e.h;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.RequestPackage;
import com.tencent.beacon.pack.ResponsePackage;

/* compiled from: ByteConverterFactory.java */
/* loaded from: classes.dex */
public final class a extends c.a<byte[], AbstractJceStruct> {
    private final C0017a a = new C0017a();
    private final b b = new b();

    /* compiled from: ByteConverterFactory.java */
    /* renamed from: com.tencent.beacon.base.net.a.a$a, reason: collision with other inner class name */
    static final class C0017a implements c<RequestPackage, byte[]> {
        C0017a() {
        }

        private byte[] b(RequestPackage requestPackage) {
            com.tencent.beacon.pack.c cVar = new com.tencent.beacon.pack.c();
            cVar.a(1);
            cVar.b("test");
            cVar.a("test");
            cVar.b("detail", requestPackage);
            return cVar.a();
        }

        @Override // com.tencent.beacon.base.net.a.c
        public byte[] a(RequestPackage requestPackage) {
            if (requestPackage == null) {
                return null;
            }
            com.tencent.beacon.base.util.c.a("[BeaconNet]", "RequestPackage: " + requestPackage.toString(), new Object[0]);
            byte[] a = a(b(requestPackage));
            if (a != null) {
                com.tencent.beacon.base.util.c.a("[BeaconNet]", "request package after processing size: " + a.length, new Object[0]);
            }
            return a;
        }

        private byte[] a(byte[] bArr) {
            h b = h.b();
            return b != null ? com.tencent.beacon.base.util.b.b(bArr, 2, 3, b.a()) : bArr;
        }
    }

    /* compiled from: ByteConverterFactory.java */
    static final class b implements c<byte[], ResponsePackage> {
        b() {
        }

        private ResponsePackage b(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            try {
                if (bArr.length <= 0) {
                    return null;
                }
                com.tencent.beacon.pack.c cVar = new com.tencent.beacon.pack.c();
                cVar.a(bArr);
                return (ResponsePackage) cVar.a("detail", (String) new ResponsePackage());
            } catch (Throwable unused) {
                return null;
            }
        }

        private byte[] c(byte[] bArr) {
            return com.tencent.beacon.base.util.b.a(bArr, 2, 3, h.b().a());
        }

        @Override // com.tencent.beacon.base.net.a.c
        public ResponsePackage a(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            return b(c(bArr));
        }
    }

    public static a a() {
        return new a();
    }

    public c<byte[], ResponsePackage> b() {
        return this.b;
    }

    public c<RequestPackage, byte[]> c() {
        return this.a;
    }
}
