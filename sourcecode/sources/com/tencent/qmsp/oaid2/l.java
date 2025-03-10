package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qmsp.oaid2.j;
import java.security.MessageDigest;

/* loaded from: classes.dex */
public class l implements b {
    public Context a;
    public IVendorCallback b;
    public String c = null;
    public boolean d = false;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                j.a a = j.a(l.this.a);
                l.this.c = a.a();
                a.b();
                if (!TextUtils.isEmpty(l.this.c)) {
                    l.this.d = true;
                }
                if (l.this.b != null) {
                    IVendorCallback iVendorCallback = l.this.b;
                    boolean z = l.this.d;
                    l lVar = l.this;
                    iVendorCallback.onResult(z, lVar.a(e.a(lVar.a)), l.this.c);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (TextUtils.isEmpty(l.this.c)) {
                    l.this.d = false;
                }
                if (l.this.b != null) {
                    IVendorCallback iVendorCallback2 = l.this.b;
                    boolean z2 = l.this.d;
                    l lVar2 = l.this;
                    iVendorCallback2.onResult(z2, lVar2.a(e.a(lVar2.a)), l.this.c);
                }
            }
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean e() {
        return false;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void j() {
        new Thread(new a()).start();
    }

    @Override // com.tencent.qmsp.oaid2.b
    public boolean k() {
        return false;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void l() {
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String d() {
        return a(e.a(this.a));
    }

    public final String b(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            String str2 = "";
            for (byte b : MessageDigest.getInstance("MD5").digest(str.getBytes())) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                str2 = str2 + hexString;
            }
            return str2;
        } catch (Exception unused) {
            return "";
        }
    }

    public final String a(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            return b("0x1008611" + str + "0xdzfdweiwu");
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.tencent.qmsp.oaid2.b
    public void a(Context context, IVendorCallback iVendorCallback) {
        this.a = context;
        this.b = iVendorCallback;
    }

    @Override // com.tencent.qmsp.oaid2.b
    public String a() {
        return this.c;
    }
}
