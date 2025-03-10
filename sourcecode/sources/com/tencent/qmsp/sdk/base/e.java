package com.tencent.qmsp.sdk.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

/* loaded from: classes.dex */
public class e implements IVendorCallback {
    private static boolean c = false;
    private static String d = com.tencent.qmsp.sdk.c.b.a + "b2FpZA";
    private static String e = com.tencent.qmsp.sdk.c.b.a + "b2FpZA";
    private static Context f = null;
    private b a = null;
    private IVendorCallback b = null;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

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

    public static String a(Context context, String str, int i) {
        if (context == null) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(d, 0);
            String string = sharedPreferences.getString(e, null);
            String str2 = !TextUtils.isEmpty(string) ? new String(Base64.decode(string.getBytes("UTF-8"), 0)) : null;
            if (i == 1) {
                try {
                    if (c) {
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString(e, TextUtils.isEmpty(str) ? null : Base64.encodeToString(str.getBytes("UTF-8"), 0));
                        edit.commit();
                    }
                } catch (Exception e2) {
                    e = e2;
                    r0 = str2;
                    e.printStackTrace();
                    return r0;
                }
            }
            return str2;
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static void a(Context context, boolean z, boolean z2) {
        c = z;
        f = context;
        c.a(z2);
    }

    public int a(IVendorCallback iVendorCallback) {
        b aVar;
        this.b = iVendorCallback;
        d a2 = d.a(Build.MANUFACTURER);
        if (a2 == d.UNSUPPORT) {
            onResult(false, null, null);
            return com.tencent.qmsp.sdk.base.a.a;
        }
        switch (a.a[a2.ordinal()]) {
            case 1:
            case 2:
                aVar = new com.tencent.qmsp.sdk.g.j.a();
                break;
            case 3:
                aVar = new com.tencent.qmsp.sdk.g.i.b();
                break;
            case 4:
                aVar = new com.tencent.qmsp.sdk.g.b.c();
                break;
            case 5:
            case 6:
                aVar = new com.tencent.qmsp.sdk.g.g.c();
                break;
            case 7:
            case 8:
                aVar = new com.tencent.qmsp.sdk.g.c.b();
                break;
            case 9:
                aVar = new com.tencent.qmsp.sdk.g.a.c();
                break;
            case 10:
                aVar = new com.tencent.qmsp.sdk.g.h.c();
                break;
            case 11:
            case 12:
                aVar = new com.tencent.qmsp.sdk.g.e.f();
                break;
            case 13:
                aVar = new com.tencent.qmsp.sdk.g.f.a();
                break;
            case 14:
            case 15:
            case 16:
                aVar = new com.tencent.qmsp.sdk.g.d.e();
                break;
        }
        this.a = aVar;
        if (this.a == null) {
            c.b("vendorInfo == null");
            onResult(false, null, null);
            return com.tencent.qmsp.sdk.base.a.b;
        }
        c.b("init");
        try {
            this.a.a(f, this);
            if (this.a.d()) {
                c.b("sync");
                try {
                    onResult(this.a.e(), this.a.b(), this.a.a());
                } catch (Exception unused) {
                    onResult(false, null, null);
                }
            } else {
                try {
                    this.a.c();
                } catch (Exception unused2) {
                    onResult(false, null, null);
                    return com.tencent.qmsp.sdk.base.a.c;
                }
            }
            return 0;
        } catch (Exception unused3) {
            onResult(false, null, null);
            return com.tencent.qmsp.sdk.base.a.c;
        }
    }

    @Override // com.tencent.qmsp.sdk.base.IVendorCallback
    public void onResult(boolean z, String str, String str2) {
        c.c("vm onResult " + z);
        if (TextUtils.isEmpty(str2)) {
            str2 = a(f, (String) null, 0);
        } else {
            a(f, str2, 1);
        }
        IVendorCallback iVendorCallback = this.b;
        if (iVendorCallback != null) {
            iVendorCallback.onResult(z, str, str2);
        }
        b bVar = this.a;
        if (bVar != null) {
            bVar.f();
        }
    }
}
