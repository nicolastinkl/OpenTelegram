package com.tencent.qimei.h;

import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: BaseParser.java */
/* loaded from: classes.dex */
public abstract class a<T> {
    public com.tencent.qimei.f.a<?>[] a;

    public a(com.tencent.qimei.f.a<?>... aVarArr) {
        this.a = aVarArr;
    }

    public abstract T a(com.tencent.qimei.f.a<T> aVar, String str);

    public com.tencent.qimei.f.a<?>[] a(com.tencent.qimei.f.a<?> aVar) {
        com.tencent.qimei.f.a<?>[] aVarArr = this.a;
        int length = aVarArr.length + 1;
        com.tencent.qimei.f.a<?>[] aVarArr2 = new com.tencent.qimei.f.a[length];
        System.arraycopy(aVarArr, 0, aVarArr2, 0, aVarArr.length);
        aVarArr2[length - 1] = aVar;
        return aVarArr2;
    }

    public String a(String str, com.tencent.qimei.f.a<?>... aVarArr) {
        if (str != null && !str.isEmpty() && aVarArr.length >= 1) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                for (int i = 0; i < aVarArr.length && jSONObject != null; i++) {
                    com.tencent.qimei.f.a<?> aVar = aVarArr[i];
                    if (aVar == aVarArr[aVarArr.length - 1]) {
                        return jSONObject.optString(aVar.b());
                    }
                    jSONObject = jSONObject.optJSONObject(aVar.b());
                }
                return "";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
