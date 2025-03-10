package com.tencent.qmsp.oaid2;

import android.content.Context;
import android.os.Build;

/* loaded from: classes.dex */
public class VendorManager implements IVendorCallback {
    public b vendorInfo = null;
    public IVendorCallback userCallback = null;

    public static /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[d.values().length];
            a = iArr;
            try {
                iArr[d.XIAOMI.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[d.BLACKSHARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[d.VIVO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[d.HUA_WEI.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[d.OPPO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[d.ONEPLUS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[d.MOTO.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                a[d.LENOVO.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                a[d.ASUS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                a[d.SAMSUNG.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                a[d.MEIZU.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                a[d.ALPS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                a[d.NUBIA.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                a[d.ZTE.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                a[d.FREEMEOS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                a[d.SSUIOS.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
        }
    }

    public int getVendorInfo(Context context, IVendorCallback iVendorCallback) {
        this.userCallback = iVendorCallback;
        d a2 = d.a(Build.MANUFACTURER);
        if (a2 == d.UNSUPPORT) {
            onResult(false, "null", "null");
            return com.tencent.qmsp.oaid2.a.a;
        }
        switch (a.a[a2.ordinal()]) {
            case 1:
            case 2:
                this.vendorInfo = new n0();
                break;
            case 3:
                this.vendorInfo = new m0();
                break;
            case 4:
                this.vendorInfo = new l();
                break;
            case 5:
            case 6:
                this.vendorInfo = new f0();
                break;
            case 7:
            case 8:
                this.vendorInfo = new o();
                break;
            case 9:
                this.vendorInfo = new h();
                break;
            case 10:
                this.vendorInfo = new k0();
                break;
            case 11:
            case 12:
                this.vendorInfo = new a0();
                break;
            case 13:
                this.vendorInfo = new b0();
                break;
            case 14:
            case 15:
            case 16:
                this.vendorInfo = new u();
                break;
        }
        if (this.vendorInfo == null) {
            onResult(false, "null", "null");
            return com.tencent.qmsp.oaid2.a.b;
        }
        c.b("init");
        try {
            this.vendorInfo.a(context, this);
            if (this.vendorInfo.k()) {
                c.b("sync");
                try {
                    onResult(this.vendorInfo.e(), this.vendorInfo.d(), this.vendorInfo.a());
                } catch (Exception unused) {
                    onResult(false, "null", "null");
                }
            } else {
                try {
                    this.vendorInfo.j();
                } catch (Exception unused2) {
                    onResult(false, "null", "null");
                    return com.tencent.qmsp.oaid2.a.c;
                }
            }
            return 0;
        } catch (Exception unused3) {
            onResult(false, "null", "null");
            return com.tencent.qmsp.oaid2.a.c;
        }
    }

    @Override // com.tencent.qmsp.oaid2.IVendorCallback
    public void onResult(boolean z, String str, String str2) {
        c.c("vm onResult " + z);
        IVendorCallback iVendorCallback = this.userCallback;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(z, str, str2);
        }
        b bVar = this.vendorInfo;
        if (bVar != null) {
            bVar.l();
        }
    }
}
