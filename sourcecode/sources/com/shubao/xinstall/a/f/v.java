package com.shubao.xinstall.a.f;

import android.text.TextUtils;
import com.xinstall.model.XAppData;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class v {
    public static XAppData a(String str) {
        XAppData xAppData = new XAppData();
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("ccb")) {
                xAppData.setCci(jSONObject.optString("ccb"));
            }
            if (jSONObject.has("cci")) {
                xAppData.setCci(jSONObject.optString("cci"));
            }
            if (jSONObject.has("uo")) {
                xAppData.setUo(jSONObject.optString("uo"));
            }
            if (jSONObject.has("co")) {
                xAppData.setCo(jSONObject.optString("co"));
            }
            if (jSONObject.has("timeSpan")) {
                xAppData.setTimeSpan(jSONObject.optString("timeSpan"));
            }
            if (jSONObject.has("ak")) {
                xAppData.setAk(jSONObject.optString("ak"));
            }
        }
        return xAppData;
    }
}
