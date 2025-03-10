package io.openinstall.sdk;

import android.content.ClipData;
import android.os.Build;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class dw {
    private String a;
    private String b;
    private int c = 0;
    private boolean d;

    public static dw a(ClipData clipData) {
        String str;
        boolean d;
        ClipData.Item itemAt;
        if (clipData == null) {
            return null;
        }
        dw dwVar = new dw();
        if (clipData.getItemCount() <= 0 || (itemAt = clipData.getItemAt(0)) == null) {
            str = null;
        } else {
            String htmlText = Build.VERSION.SDK_INT >= 16 ? itemAt.getHtmlText() : null;
            str = itemAt.getText() != null ? itemAt.getText().toString() : null;
            r0 = htmlText;
        }
        if (r0 != null) {
            if (r0.contains(fw.d)) {
                dwVar.b(r0);
                dwVar.b(2);
            }
            dwVar.a(d(r0));
        }
        if (str != null) {
            String str2 = fw.d;
            if (str.contains(str2)) {
                dwVar.a(str);
                dwVar.b(1);
                d = d(str);
            } else {
                String b = fv.b(str);
                if (b.contains(str2)) {
                    dwVar.a(str);
                    dwVar.b(1);
                }
                d = d(b);
            }
            dwVar.a(d);
        }
        return dwVar;
    }

    public static dw c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        dw dwVar = new dw();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("pbText")) {
                dwVar.a(jSONObject.optString("pbText"));
            }
            if (jSONObject.has("pbHtml")) {
                dwVar.b(jSONObject.optString("pbHtml"));
            }
            if (jSONObject.has("pbType")) {
                dwVar.a(jSONObject.optInt("pbType"));
            }
            return dwVar;
        } catch (JSONException unused) {
            return null;
        }
    }

    private static boolean d(String str) {
        String str2 = fw.e;
        if (!str.contains(str2)) {
            return false;
        }
        long j = 0;
        try {
            int indexOf = str.indexOf(str2) + str2.length();
            j = Long.parseLong(str.substring(indexOf, str.indexOf("-", indexOf)));
        } catch (Exception unused) {
        }
        return System.currentTimeMillis() < j;
    }

    public String a() {
        return this.a;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(String str) {
        this.a = str;
    }

    public void a(boolean z) {
        this.d = z;
    }

    public String b() {
        return this.b;
    }

    public void b(int i) {
        this.c = i | this.c;
    }

    public void b(String str) {
        this.b = str;
    }

    public int c() {
        return this.c;
    }

    public boolean c(int i) {
        return (i & this.c) != 0;
    }

    public String d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pbText", this.a);
            jSONObject.put("pbHtml", this.b);
            jSONObject.put("pbType", this.c);
        } catch (JSONException unused) {
        }
        return jSONObject.toString();
    }
}
