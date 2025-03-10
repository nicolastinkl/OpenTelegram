package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.cos.xml.common.RequestMethod;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* compiled from: HttpAdapter.java */
/* loaded from: classes.dex */
public class b extends AbstractNetAdapter {
    private BResponse a(String str, String str2, Map<String, String> map, byte[] bArr) throws IOException {
        HttpURLConnection a = a(str, str2, map);
        a.connect();
        OutputStream outputStream = a.getOutputStream();
        if (outputStream != null && bArr != null) {
            outputStream.write(bArr);
            outputStream.close();
        }
        return new BResponse(a.getHeaderFields(), a.getResponseCode(), a.getResponseMessage(), a(a.getInputStream()));
    }

    private byte[] buildBody(com.tencent.beacon.base.net.call.e eVar) throws UnsupportedEncodingException {
        int i = a.a[eVar.a().ordinal()];
        if (i == 1) {
            return eVar.c();
        }
        if (i == 2) {
            return com.tencent.beacon.base.net.b.d.b(eVar.d()).getBytes("UTF-8");
        }
        if (i != 3) {
            return null;
        }
        return eVar.f().getBytes("UTF-8");
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        String name = jceRequestEntity.getType().name();
        try {
            BResponse a = a(jceRequestEntity.getUrl(), RequestMethod.POST, jceRequestEntity.getHeader(), jceRequestEntity.getContent());
            int i = a.code;
            if (i != 200) {
                StringBuilder sb = new StringBuilder();
                sb.append("response status code != 2XX. msg: ");
                sb.append(a.msg);
                callback.onFailure(new com.tencent.beacon.base.net.d(name, "452", i, sb.toString()));
                return;
            }
            if (com.tencent.beacon.base.net.b.d.a(a.headers)) {
                callback.onResponse(a.body);
            } else {
                callback.onFailure(new com.tencent.beacon.base.net.d(name, "454", a.code, "server encrypt-status error!"));
            }
        } catch (ConnectException e) {
            com.tencent.beacon.base.util.c.a(e);
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "451", -1, "https connect timeout: " + e.getMessage(), e));
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            callback.onFailure(new com.tencent.beacon.base.net.d(name, "499", -1, "https connect error: " + th.getMessage(), th));
        }
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Failed to calculate best type for var: r1v1 ??
    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.arrayPutListener(TypeUpdate.java:609)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:145)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:123)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:101)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:101)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
     */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0045: MOVE (r7 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:22:0x0045 */
    private byte[] a(java.io.InputStream r9) throws java.io.IOException {
        /*
            r8 = this;
            r0 = 1
            r1 = 2
            r2 = 0
            r3 = 0
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r4.<init>()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            r3 = 2048(0x800, float:2.87E-42)
            byte[] r3 = new byte[r3]     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
        Ld:
            int r5 = r9.read(r3)     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            r6 = -1
            if (r5 == r6) goto L18
            r4.write(r3, r2, r5)     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            goto Ld
        L18:
            r4.flush()     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            byte[] r3 = r4.toByteArray()     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            r4.close()     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            r9.close()     // Catch: java.io.IOException -> L2f java.lang.Throwable -> L44
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r1[r2] = r9
            r1[r0] = r4
            com.tencent.beacon.base.util.b.a(r1)
            return r3
        L2f:
            r3 = move-exception
            goto L37
        L31:
            r4 = move-exception
            goto L48
        L33:
            r4 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
        L37:
            com.tencent.beacon.base.util.c.a(r3)     // Catch: java.lang.Throwable -> L44
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r1[r2] = r9
            r1[r0] = r4
            com.tencent.beacon.base.util.b.a(r1)
            throw r3
        L44:
            r3 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
        L48:
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r1[r2] = r9
            r1[r0] = r3
            com.tencent.beacon.base.util.b.a(r1)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.base.net.adapter.b.a(java.io.InputStream):byte[]");
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(com.tencent.beacon.base.net.call.e eVar, Callback<BResponse> callback) {
        String h = eVar.h();
        try {
            BResponse a = a(eVar.i(), eVar.g().name(), eVar.e(), buildBody(eVar));
            int i = a.code;
            if (i != 200) {
                StringBuilder sb = new StringBuilder();
                sb.append("response status code != 2XX. msg: ");
                sb.append(a.msg);
                callback.onFailure(new com.tencent.beacon.base.net.d(h, "452", i, sb.toString()));
            } else {
                callback.onResponse(a);
            }
        } catch (ConnectException e) {
            com.tencent.beacon.base.util.c.a(e);
            callback.onFailure(new com.tencent.beacon.base.net.d(h, "451", -1, "https connect timeout: " + e.getMessage(), e));
        } catch (Throwable th) {
            com.tencent.beacon.base.util.c.a(th);
            callback.onFailure(new com.tencent.beacon.base.net.d(h, "499", -1, "https connect error: " + th.getMessage(), th));
        }
    }

    private HttpURLConnection a(String str, String str2, Map<String, String> map) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setInstanceFollowRedirects(false);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return httpURLConnection;
    }
}
