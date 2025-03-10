package io.openinstall.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class di {
    private final File a;
    private final File b;
    private int c;

    public di(Context context, String str) {
        File file = new File(context.getFilesDir(), str);
        this.a = file;
        File file2 = new File(context.getFilesDir(), str + ".t");
        this.b = file2;
        this.c = a(file) + a(file2);
    }

    private int a(File file) {
        String b = dn.b(file);
        int i = 0;
        int i2 = 0;
        while (true) {
            int indexOf = b.indexOf(";", i);
            if (indexOf == -1) {
                return i2;
            }
            i2++;
            i = indexOf + 1;
        }
    }

    private int a(String str, int i) {
        int i2 = 0;
        int i3 = 0;
        do {
            int indexOf = str.indexOf(";", i2);
            if (indexOf == -1) {
                break;
            }
            i3++;
            i2 = indexOf + 1;
        } while (i3 < i);
        return i2;
    }

    private void a(String str) {
        dn.a(this.a, str, true);
        this.a.length();
    }

    private String b(de deVar) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(deVar.d())) {
            sb.append(deVar.d());
            sb.append(",");
        }
        if (deVar.e() != null) {
            sb.append(deVar.e());
            sb.append(",");
        }
        if (deVar.f() != null) {
            sb.append(deVar.f());
            sb.append(",");
        }
        if (deVar.g() != null && deVar.g().size() > 0) {
            try {
                JSONObject jSONObject = new JSONObject();
                for (Map.Entry<String, String> entry : deVar.g().entrySet()) {
                    jSONObject.put(entry.getKey(), entry.getValue());
                }
                sb.append(fv.c(jSONObject.toString()));
                sb.append(",");
            } catch (JSONException unused) {
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
            sb.append(";");
        }
        String sb2 = sb.toString();
        int length = sb2.getBytes(ds.c).length;
        return sb2;
    }

    public void a(de deVar) {
        a(b(deVar));
        this.c++;
    }

    public boolean a() {
        return this.c <= 0;
    }

    public boolean b() {
        return this.c >= 100;
    }

    public void c() {
        int a = a(this.b);
        dn.a(this.b, "", false);
        this.c -= a;
    }

    public void d() {
        this.a.delete();
        this.b.delete();
        this.c = 0;
    }

    public String e() {
        int a = a(this.b);
        String b = dn.b(this.b);
        if (a > 50) {
            return b;
        }
        String b2 = dn.b(this.a);
        int a2 = a(b2, 100 - a);
        String str = b + b2.substring(0, a2);
        dn.a(this.b, str, false);
        dn.a(this.a, b2.substring(a2), false);
        return str;
    }
}
