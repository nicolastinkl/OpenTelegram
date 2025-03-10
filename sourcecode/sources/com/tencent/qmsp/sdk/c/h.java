package com.tencent.qmsp.sdk.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.tencent.qmsp.sdk.a.c;

/* loaded from: classes.dex */
public class h {
    private static SharedPreferences a;
    private static final int b;

    static class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            h.b();
        }
    }

    static class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            h.b();
        }
    }

    static {
        b = Build.VERSION.SDK_INT < 23 ? 9 : 23;
    }

    private static void a() {
        String str;
        try {
            int i = b;
            String[] strArr = new String[i];
            f.a(10L, 0L, 0L, 0L, null, null, null, strArr);
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (int i2 = 0; i2 < i; i2++) {
                strArr[i2] = com.tencent.qmsp.sdk.f.i.a(strArr[i2]);
                sb.append(strArr[i2]);
                sb.append(",");
                if (i2 == 0) {
                    sb2.append(String.format("k%d:", Integer.valueOf(i2 + 1)));
                    str = strArr[i2];
                } else {
                    sb2.append(String.format(";k%d:", Integer.valueOf(i2 + 1)));
                    str = strArr[i2];
                }
                sb2.append(str);
            }
            sb2.append(String.format(";k%d:", Integer.valueOf(i + 1)));
            sb2.append("4.1");
            sb.append(sb2.toString());
            sb.append(",");
            sb.append(c.a(sb2.toString()));
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Java -- cbid: ");
            sb3.append(sb.toString());
            sb3.append(" bidMd5: ");
            sb3.append(c.a(sb2.toString()));
            com.tencent.qmsp.sdk.f.g.b("cbid", 0, sb3.toString());
            com.tencent.qmsp.sdk.a.f.a(sb.toString(), 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(long j) {
        f.i().c().postDelayed(new b(), j);
    }

    public static void a(Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(com.tencent.qmsp.sdk.c.b.a);
            sb.append("qmsp_cbid_time");
            a = context.getSharedPreferences(sb.toString(), 0);
            b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void b() {
        boolean taskStatus;
        try {
            taskStatus = com.tencent.qmsp.sdk.app.a.getTaskStatus();
            if (!taskStatus) {
                com.tencent.qmsp.sdk.f.g.a("cbid", 1, "Cbid Task Finish！");
                return;
            }
            if (a != null) {
                long j = 28800000;
                if (!f.i().a(1002).booleanValue()) {
                    a(28800000L);
                    return;
                }
                long j2 = 0;
                long j3 = a.getLong("cbid_last_time", 0L);
                long currentTimeMillis = System.currentTimeMillis();
                long j4 = currentTimeMillis - j3;
                if (j4 >= 0) {
                    j2 = j4;
                }
                if (j2 > 28800000) {
                    a();
                    SharedPreferences.Editor edit = a.edit();
                    edit.putLong("cbid_last_time", currentTimeMillis);
                    edit.commit();
                } else {
                    j = 28800000 - j2;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("cbid rpt after: ");
                sb.append(j);
                com.tencent.qmsp.sdk.f.g.a("cbid", 1, sb.toString());
                f.i().c().postDelayed(new a(), j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
