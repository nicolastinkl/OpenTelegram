package com.xinstall.model;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class XAppData implements Serializable {
    private boolean firstFetch;
    private Map<String, String> data = new HashMap();
    private String cci = "";
    private String uo = "";
    private String co = "";
    private String ak = "";
    private String timeSpan = "0";

    private boolean isCoEmpty() {
        return TextUtils.isEmpty(this.co.trim()) || this.co.trim().equals("{}");
    }

    private boolean isUoEmpty() {
        return TextUtils.isEmpty(this.uo.trim()) || this.uo.trim().equals("{}");
    }

    public String getChannelCode() {
        return this.cci;
    }

    public Map<String, String> getExtraData() {
        this.data.clear();
        this.data.put("uo", this.uo);
        this.data.put("co", this.co);
        return this.data;
    }

    public String getTimeSpan() {
        return this.timeSpan;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.cci) && isCoEmpty() && isUoEmpty();
    }

    public boolean isFirstFetch() {
        return this.firstFetch;
    }

    public void setAk(String str) {
        this.ak = str;
    }

    public void setCci(String str) {
        this.cci = str;
    }

    public void setCo(String str) {
        this.co = str;
    }

    public void setFirstFetch(boolean z) {
        this.firstFetch = z;
    }

    public void setTimeSpan(String str) {
        this.timeSpan = str;
    }

    public void setUo(String str) {
        this.uo = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004d A[Catch: JSONException -> 0x006a, TRY_ENTER, TryCatch #0 {JSONException -> 0x006a, blocks: (B:6:0x000c, B:9:0x002e, B:10:0x0033, B:11:0x0045, B:14:0x004d, B:15:0x0052, B:16:0x0064, B:22:0x0061, B:26:0x0042, B:24:0x0037, B:20:0x0056), top: B:5:0x000c, inners: #1, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0056 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject toJsonObject() {
        /*
            r5 = this;
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto Lc
            return r0
        Lc:
            java.lang.String r1 = "channelCode"
            java.lang.String r2 = r5.cci     // Catch: org.json.JSONException -> L6a
            r0.put(r1, r2)     // Catch: org.json.JSONException -> L6a
            java.lang.String r1 = "timeSpan"
            java.lang.String r2 = r5.timeSpan     // Catch: org.json.JSONException -> L6a
            r0.put(r1, r2)     // Catch: org.json.JSONException -> L6a
            java.lang.String r1 = "isFirstFetch"
            boolean r2 = r5.firstFetch     // Catch: org.json.JSONException -> L6a
            r0.put(r1, r2)     // Catch: org.json.JSONException -> L6a
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: org.json.JSONException -> L6a
            r1.<init>()     // Catch: org.json.JSONException -> L6a
            boolean r2 = r5.isUoEmpty()     // Catch: org.json.JSONException -> L6a
            java.lang.String r3 = "uo"
            if (r2 == 0) goto L37
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: org.json.JSONException -> L6a
            r2.<init>()     // Catch: org.json.JSONException -> L6a
        L33:
            r1.put(r3, r2)     // Catch: org.json.JSONException -> L6a
            goto L45
        L37:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: java.lang.Exception -> L42
            java.lang.String r4 = r5.uo     // Catch: java.lang.Exception -> L42
            r2.<init>(r4)     // Catch: java.lang.Exception -> L42
            r1.put(r3, r2)     // Catch: java.lang.Exception -> L42
            goto L45
        L42:
            java.lang.String r2 = r5.uo     // Catch: org.json.JSONException -> L6a
            goto L33
        L45:
            boolean r2 = r5.isCoEmpty()     // Catch: org.json.JSONException -> L6a
            java.lang.String r3 = "co"
            if (r2 == 0) goto L56
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: org.json.JSONException -> L6a
            r2.<init>()     // Catch: org.json.JSONException -> L6a
        L52:
            r1.put(r3, r2)     // Catch: org.json.JSONException -> L6a
            goto L64
        L56:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: java.lang.Exception -> L61
            java.lang.String r4 = r5.co     // Catch: java.lang.Exception -> L61
            r2.<init>(r4)     // Catch: java.lang.Exception -> L61
            r1.put(r3, r2)     // Catch: java.lang.Exception -> L61
            goto L64
        L61:
            java.lang.String r2 = r5.co     // Catch: org.json.JSONException -> L6a
            goto L52
        L64:
            java.lang.String r2 = "data"
            r0.put(r2, r1)     // Catch: org.json.JSONException -> L6a
            goto L6e
        L6a:
            r1 = move-exception
            r1.printStackTrace()
        L6e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xinstall.model.XAppData.toJsonObject():org.json.JSONObject");
    }
}
