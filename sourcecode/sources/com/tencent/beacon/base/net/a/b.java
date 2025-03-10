package com.tencent.beacon.base.net.a;

import com.tencent.beacon.base.net.a.c;
import com.tencent.beacon.e.h;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.beacon.pack.RequestPackageV2;
import com.tencent.beacon.pack.ResponsePackageV2;

/* compiled from: ByteV2ConverterFactory.java */
/* loaded from: classes.dex */
public class b extends c.a<byte[], AbstractJceStruct> {
    private final a a = new a();
    private final C0018b b = new C0018b();

    /* compiled from: ByteV2ConverterFactory.java */
    static final class a implements c<RequestPackageV2, byte[]> {
        a() {
        }

        @Override // com.tencent.beacon.base.net.a.c
        public byte[] a(RequestPackageV2 requestPackageV2) {
            if (requestPackageV2 == null) {
                return null;
            }
            com.tencent.beacon.base.util.c.a("[BeaconNet]", "RequestPackageV2: " + requestPackageV2.toString(), new Object[0]);
            com.tencent.beacon.pack.b bVar = new com.tencent.beacon.pack.b();
            requestPackageV2.writeTo(bVar);
            byte[] a = a(bVar.b());
            if (a != null) {
                com.tencent.beacon.base.util.c.a("[BeaconNet]", "request package after processing size: " + a.length, new Object[0]);
            }
            return a;
        }

        private byte[] a(byte[] bArr) {
            h b = h.b();
            if (b == null) {
                return bArr;
            }
            if (com.tencent.beacon.e.b.a().m()) {
                return com.tencent.beacon.base.util.b.b(bArr, 2, 3, b.a());
            }
            return com.tencent.beacon.base.util.b.b(bArr, 2);
        }
    }

    /* compiled from: ByteV2ConverterFactory.java */
    /* renamed from: com.tencent.beacon.base.net.a.b$b, reason: collision with other inner class name */
    static final class C0018b implements c<byte[], ResponsePackageV2> {
        C0018b() {
        }

        private byte[] b(byte[] bArr) {
            return com.tencent.beacon.e.b.a().m() ? com.tencent.beacon.base.util.b.a(bArr, 2, 3, h.b().a()) : com.tencent.beacon.base.util.b.a(bArr, 2);
        }

        @Override // com.tencent.beacon.base.net.a.c
        public ResponsePackageV2 a(byte[] bArr) {
            if (bArr == null) {
                return null;
            }
            byte[] b = b(bArr);
            ResponsePackageV2 responsePackageV2 = new ResponsePackageV2();
            responsePackageV2.readFrom(new com.tencent.beacon.pack.a(b));
            return responsePackageV2;
        }
    }

    public static b a() {
        return new b();
    }

    public c<byte[], ResponsePackageV2> b() {
        return this.b;
    }

    public c<RequestPackageV2, byte[]> c() {
        return this.a;
    }
}
