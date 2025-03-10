package com.tencent.qmsp.sdk.g.b;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qmsp.sdk.base.IVendorCallback;
import com.tencent.qmsp.sdk.base.f;
import com.tencent.qmsp.sdk.g.b.a;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class c implements com.tencent.qmsp.sdk.base.b {
    private Context a;
    private IVendorCallback b;
    private String c = null;
    private boolean d = false;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                a.C0039a a = com.tencent.qmsp.sdk.g.b.a.a(c.this.a);
                c.this.c = a.a();
                a.b();
                if (!TextUtils.isEmpty(c.this.c)) {
                    c.this.d = true;
                }
                if (c.this.b != null) {
                    IVendorCallback iVendorCallback = c.this.b;
                    boolean z = c.this.d;
                    c cVar = c.this;
                    iVendorCallback.onResult(z, cVar.a(f.a(cVar.a)), c.this.c);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (TextUtils.isEmpty(c.this.c)) {
                    c.this.d = false;
                }
                if (c.this.b != null) {
                    IVendorCallback iVendorCallback2 = c.this.b;
                    boolean z2 = c.this.d;
                    c cVar2 = c.this;
                    iVendorCallback2.onResult(z2, cVar2.a(f.a(cVar2.a)), c.this.c);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("0x1008611");
            sb.append(str);
            sb.append("0xdzfdweiwu");
            return b(sb.toString());
        } catch (Exception unused) {
            return "";
        }
    }

    private String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            String str2 = "";
            for (byte b : MessageDigest.getInstance("MD5").digest(str.getBytes())) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append(hexString);
                    hexString = sb.toString();
                }
                str2 = str2 + hexString;
            }
            return str2;
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String a() {
        return this.c;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
        this.b = iVendorCallback;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public String b() {
        return a(f.a(this.a));
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void c() {
        new Thread(new a()).start();
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean d() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public boolean e() {
        return false;
    }

    @Override // com.tencent.qmsp.sdk.base.b
    public void f() {
    }
}
