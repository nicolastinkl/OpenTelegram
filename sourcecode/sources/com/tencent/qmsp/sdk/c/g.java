package com.tencent.qmsp.sdk.c;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import com.tencent.beacon.pack.AbstractJceStruct;
import com.tencent.qmsp.sdk.a.d;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.XiaomiUtilities;

/* loaded from: classes.dex */
public class g {
    private static final byte[] f = {51, 117, -95};
    private static final byte[] g = {38, 114, -96};
    private static final byte[] h = {20, 125, -96, 80, AbstractJceStruct.SIMPLE_LIST, 57, 57, -7, 36, 100};
    private static final byte[] i = {20, 125, -96, 80, AbstractJceStruct.SIMPLE_LIST, 57, 91, -20, 49};
    private static final byte[] j = {52, 100};
    private static final byte[] k = {20, 125, -96, 80, 96, 24, 117};
    private static final byte[][] l = {new byte[]{54, 100}, new byte[]{49, 99, -70}, new byte[]{54, 115}};
    private static g m;
    private ConcurrentHashMap<Integer, Integer> a = new ConcurrentHashMap<>();
    private CopyOnWriteArrayList<c> b = new CopyOnWriteArrayList<>();
    private SharedPreferences c;
    private long d;
    private int e;

    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            g.this.a(false);
        }
    }

    class b implements com.tencent.qmsp.sdk.b.e {
        b() {
        }

        @Override // com.tencent.qmsp.sdk.b.e
        public void a(int i, JSONObject jSONObject) {
            if (i != 161 || jSONObject == null) {
                return;
            }
            g.this.a(jSONObject);
        }
    }

    public interface c {
        void a(List<Pair<Integer, Integer>> list);
    }

    private g() {
        AtomicInteger atomCbTimeout;
        Context context;
        this.c = null;
        atomCbTimeout = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
        this.d = atomCbTimeout.get();
        this.e = 0;
        context = com.tencent.qmsp.sdk.app.a.getContext();
        this.c = context.getSharedPreferences(com.tencent.qmsp.sdk.c.b.a + a(i), 0);
        b();
    }

    private int a(int i2, int i3) {
        return a(new Pair<>(Integer.valueOf(i2), Integer.valueOf(i3)));
    }

    private int a(Pair<Integer, Integer> pair) {
        AtomicInteger atomConnTimeOut;
        AtomicInteger atomConnTimeOut2;
        AtomicInteger atomConnTimeOut3;
        AtomicInteger atomReadTimeOut;
        AtomicInteger atomReadTimeOut2;
        AtomicInteger atomReadTimeOut3;
        AtomicInteger atomCbTimeout;
        AtomicInteger atomCbTimeout2;
        AtomicInteger atomCbTimeout3;
        AtomicInteger atomUpdateInterval;
        AtomicInteger atomUpdateInterval2;
        AtomicInteger atomUpdateInterval3;
        if (pair == null) {
            return -1;
        }
        switch (((Integer) pair.first).intValue()) {
            case 10000:
                atomConnTimeOut = com.tencent.qmsp.sdk.app.a.getAtomConnTimeOut();
                if (atomConnTimeOut.get() != ((Integer) pair.second).intValue() && ((Integer) pair.second).intValue() > 5000) {
                    atomConnTimeOut2 = com.tencent.qmsp.sdk.app.a.getAtomConnTimeOut();
                    atomConnTimeOut2.set(((Integer) pair.second).intValue());
                    String a2 = a(k);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[CB-CYC] Socket Conn TimeOut: ");
                    atomConnTimeOut3 = com.tencent.qmsp.sdk.app.a.getAtomConnTimeOut();
                    sb.append(atomConnTimeOut3.get());
                    com.tencent.qmsp.sdk.f.g.a(a2, 1, sb.toString());
                    break;
                }
                break;
            case XiaomiUtilities.OP_WIFI_CHANGE /* 10001 */:
                atomReadTimeOut = com.tencent.qmsp.sdk.app.a.getAtomReadTimeOut();
                if (atomReadTimeOut.get() != ((Integer) pair.second).intValue() && ((Integer) pair.second).intValue() > 5000) {
                    atomReadTimeOut2 = com.tencent.qmsp.sdk.app.a.getAtomReadTimeOut();
                    atomReadTimeOut2.set(((Integer) pair.second).intValue());
                    String a3 = a(k);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[CB-CYC] Socket Read TimeOut: ");
                    atomReadTimeOut3 = com.tencent.qmsp.sdk.app.a.getAtomReadTimeOut();
                    sb2.append(atomReadTimeOut3.get());
                    com.tencent.qmsp.sdk.f.g.a(a3, 1, sb2.toString());
                    break;
                }
                break;
            case XiaomiUtilities.OP_BLUETOOTH_CHANGE /* 10002 */:
                atomCbTimeout = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
                if (atomCbTimeout.get() != ((Integer) pair.second).intValue() && ((Integer) pair.second).intValue() > 3600000) {
                    atomCbTimeout2 = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
                    atomCbTimeout2.set(((Integer) pair.second).intValue());
                    String a4 = a(k);
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("[CB-CYC] CB Ruery TimeOut : ");
                    atomCbTimeout3 = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
                    sb3.append(atomCbTimeout3.get());
                    com.tencent.qmsp.sdk.f.g.a(a4, 1, sb3.toString());
                    break;
                }
                break;
            case XiaomiUtilities.OP_DATA_CONNECT_CHANGE /* 10003 */:
                atomUpdateInterval = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
                if (atomUpdateInterval.get() != ((Integer) pair.second).intValue() && ((Integer) pair.second).intValue() > 3600000) {
                    atomUpdateInterval2 = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
                    atomUpdateInterval2.set(((Integer) pair.second).intValue());
                    String a5 = a(k);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("[CB-CYC] Plugin Ruery TimeOut : ");
                    atomUpdateInterval3 = com.tencent.qmsp.sdk.app.a.getAtomUpdateInterval();
                    sb4.append(atomUpdateInterval3.get());
                    com.tencent.qmsp.sdk.f.g.a(a5, 1, sb4.toString());
                    break;
                }
                break;
        }
        return -1;
    }

    private Pair<Integer, Integer> a(String str, JSONObject jSONObject) {
        try {
            return new Pair<>(Integer.valueOf(Integer.parseInt(str)), Integer.valueOf(jSONObject.getInt(str)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String a(byte[] bArr) {
        return com.tencent.qmsp.sdk.f.h.a(bArr);
    }

    private void a(int i2, int i3, int i4) {
        com.tencent.qmsp.sdk.f.g.a(a(k), 1, String.format("[CB] report: funType=%d, result=%d,  NowTaskID=%d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
        com.tencent.qmsp.sdk.a.g gVar = new com.tencent.qmsp.sdk.a.g();
        try {
            gVar.a(i2).a(i3).a(i4);
            com.tencent.qmsp.sdk.a.f.a(gVar.toString(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            com.tencent.qmsp.sdk.f.g.b(com.tencent.qmsp.sdk.f.g.a, 0, "onReport error!");
        }
    }

    private void a(long j2) {
        f.i().c().postDelayed(new a(), j2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0058, code lost:
    
        if (r3.byteValue() != ((java.lang.Integer) r2.second).intValue()) goto L19;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r6) {
        /*
            r5 = this;
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch: java.lang.Exception -> L67
            r1.<init>(r6)     // Catch: java.lang.Exception -> L67
            byte[] r6 = com.tencent.qmsp.sdk.c.g.f     // Catch: java.lang.Exception -> L67
            java.lang.String r6 = r5.a(r6)     // Catch: java.lang.Exception -> L67
            int r6 = r1.getInt(r6)     // Catch: java.lang.Exception -> L67
            r2 = 1
            if (r6 == r2) goto L18
            return
        L18:
            byte[] r6 = com.tencent.qmsp.sdk.c.g.g     // Catch: java.lang.Exception -> L67
            java.lang.String r6 = r5.a(r6)     // Catch: java.lang.Exception -> L67
            org.json.JSONObject r6 = r1.getJSONObject(r6)     // Catch: java.lang.Exception -> L67
            if (r6 == 0) goto L73
            java.util.Iterator r1 = r6.keys()     // Catch: java.lang.Exception -> L67
            if (r1 == 0) goto L73
        L2a:
            boolean r2 = r1.hasNext()     // Catch: java.lang.Exception -> L67
            if (r2 == 0) goto L73
            java.lang.Object r2 = r1.next()     // Catch: java.lang.Exception -> L67
            java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Exception -> L67
            android.util.Pair r2 = r5.a(r2, r6)     // Catch: java.lang.Exception -> L67
            if (r2 == 0) goto L2a
            r5.a(r2)     // Catch: java.lang.Exception -> L67
            j$.util.concurrent.ConcurrentHashMap<java.lang.Integer, java.lang.Integer> r3 = r5.a     // Catch: java.lang.Exception -> L67
            java.lang.Object r4 = r2.first     // Catch: java.lang.Exception -> L67
            java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Exception -> L67
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch: java.lang.Exception -> L67
            if (r3 != 0) goto L4c
            goto L5a
        L4c:
            byte r3 = r3.byteValue()     // Catch: java.lang.Exception -> L67
            java.lang.Object r4 = r2.second     // Catch: java.lang.Exception -> L67
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Exception -> L67
            int r4 = r4.intValue()     // Catch: java.lang.Exception -> L67
            if (r3 == r4) goto L5d
        L5a:
            r0.add(r2)     // Catch: java.lang.Exception -> L67
        L5d:
            j$.util.concurrent.ConcurrentHashMap<java.lang.Integer, java.lang.Integer> r3 = r5.a     // Catch: java.lang.Exception -> L67
            java.lang.Object r4 = r2.first     // Catch: java.lang.Exception -> L67
            java.lang.Object r2 = r2.second     // Catch: java.lang.Exception -> L67
            r3.put(r4, r2)     // Catch: java.lang.Exception -> L67
            goto L2a
        L67:
            r6 = move-exception
            int r1 = r5.e
            r2 = 1002(0x3ea, float:1.404E-42)
            r3 = -1
            r5.a(r2, r3, r1)
            r6.printStackTrace()
        L73:
            r5.g()
            boolean r6 = r0.isEmpty()
            if (r6 != 0) goto L92
            java.util.concurrent.CopyOnWriteArrayList<com.tencent.qmsp.sdk.c.g$c> r6 = r5.b
            java.util.Iterator r6 = r6.iterator()
        L82:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L92
            java.lang.Object r1 = r6.next()
            com.tencent.qmsp.sdk.c.g$c r1 = (com.tencent.qmsp.sdk.c.g.c) r1
            r1.a(r0)
            goto L82
        L92:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.c.g.a(java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONObject jSONObject) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("cb-->Result: ");
            sb.append(jSONObject);
            com.tencent.qmsp.sdk.f.g.a("Task: ", 0, sb.toString());
            int i2 = -1;
            byte[][] bArr = l;
            if (!jSONObject.isNull(a(bArr[0])) && !jSONObject.isNull(a(bArr[1])) && !jSONObject.isNull(a(bArr[2]))) {
                i2 = jSONObject.optInt(a(bArr[0]));
                this.e = Integer.valueOf(jSONObject.optString(a(bArr[1]))).intValue();
                String optString = jSONObject.optString(a(bArr[2]));
                if (i2 == 0 && optString != null && !optString.equals("")) {
                    a(optString);
                }
            }
            a(1001, i2, this.e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.lang.Integer, java.lang.Object] */
    private void b(byte[] bArr) {
        DataInputStream dataInputStream;
        DataInputStream dataInputStream2 = null;
        DataInputStream dataInputStream3 = null;
        DataInputStream dataInputStream4 = null;
        try {
            try {
                dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
            } catch (IOException e) {
                e = e;
            }
            while (true) {
                try {
                    dataInputStream2 = dataInputStream3;
                    if (dataInputStream.available() != 0) {
                        int readInt = dataInputStream.readInt();
                        int readInt2 = dataInputStream.readInt();
                        a(readInt, readInt2);
                        ConcurrentHashMap concurrentHashMap = this.a;
                        Integer valueOf = Integer.valueOf(readInt);
                        ?? valueOf2 = Integer.valueOf(readInt2);
                        concurrentHashMap.put(valueOf, valueOf2);
                        dataInputStream3 = valueOf2;
                    }
                } catch (IOException e2) {
                    e = e2;
                    dataInputStream4 = dataInputStream;
                    e.printStackTrace();
                    if (dataInputStream4 != null) {
                        dataInputStream = dataInputStream4;
                        dataInputStream2 = dataInputStream4;
                        dataInputStream.close();
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    th = th;
                    dataInputStream2 = dataInputStream;
                    if (dataInputStream2 != null) {
                        try {
                            dataInputStream2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
                try {
                    dataInputStream.close();
                    return;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private JSONObject c() {
        try {
            SharedPreferences.Editor edit = this.c.edit();
            edit.putLong(a(j), System.currentTimeMillis());
            edit.commit();
            JSONObject jSONObject = new JSONObject();
            JSONObject a2 = d.a(1);
            if (a2 == null) {
                com.tencent.qmsp.sdk.f.g.d(a(k), 0, "make query head Fail!");
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(com.tencent.qmsp.sdk.a.e.a(20), 1);
            jSONObject2.put(com.tencent.qmsp.sdk.a.e.a(21), LiteMode.FLAG_CALLS_ANIMATIONS);
            jSONObject.put(com.tencent.qmsp.sdk.a.e.a(15), a2);
            jSONObject.put(com.tencent.qmsp.sdk.a.e.a(16), jSONObject2);
            return jSONObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static g d() {
        if (m == null) {
            synchronized (g.class) {
                if (m == null) {
                    m = new g();
                }
            }
        }
        return m;
    }

    private String e() {
        return com.tencent.qmsp.sdk.a.b.a() + File.separator + a(h);
    }

    private boolean f() {
        AtomicInteger atomCbTimeout;
        AtomicInteger atomCbTimeout2;
        try {
            atomCbTimeout = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
            this.d = atomCbTimeout.get();
            long j2 = 0;
            long currentTimeMillis = System.currentTimeMillis() - this.c.getLong(a(j), 0L);
            if (currentTimeMillis >= 0) {
                j2 = currentTimeMillis;
            }
            atomCbTimeout2 = com.tencent.qmsp.sdk.app.a.getAtomCbTimeout();
            if (j2 >= atomCbTimeout2.get()) {
                return true;
            }
            this.d -= j2;
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private void g() {
        byte[] h2 = h();
        if (h2 != null) {
            new m().a(e(), h2, null, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0082 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private byte[] h() {
        /*
            r7 = this;
            r0 = 0
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            r1.<init>()     // Catch: java.lang.Throwable -> L5d java.io.IOException -> L60
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L59
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L53 java.io.IOException -> L59
            j$.util.concurrent.ConcurrentHashMap<java.lang.Integer, java.lang.Integer> r3 = r7.a     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.util.Set r3 = r3.entrySet()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.util.Iterator r3 = r3.iterator()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
        L15:
            boolean r4 = r3.hasNext()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            if (r4 == 0) goto L3c
            java.lang.Object r4 = r3.next()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.lang.Object r5 = r4.getKey()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            int r5 = r5.intValue()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            r2.writeInt(r5)     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.lang.Object r4 = r4.getValue()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            int r4 = r4.intValue()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            r2.writeInt(r4)     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            goto L15
        L3c:
            byte[] r0 = r1.toByteArray()     // Catch: java.io.IOException -> L51 java.lang.Throwable -> L7c
            r1.close()     // Catch: java.io.IOException -> L44
            goto L48
        L44:
            r1 = move-exception
            r1.printStackTrace()
        L48:
            r2.close()     // Catch: java.io.IOException -> L4c
            goto L50
        L4c:
            r1 = move-exception
            r1.printStackTrace()
        L50:
            return r0
        L51:
            r3 = move-exception
            goto L64
        L53:
            r2 = move-exception
            r6 = r2
            r2 = r0
            r0 = r1
            r1 = r6
            goto L80
        L59:
            r2 = move-exception
            r3 = r2
            r2 = r0
            goto L64
        L5d:
            r1 = move-exception
            r2 = r0
            goto L80
        L60:
            r1 = move-exception
            r3 = r1
            r1 = r0
            r2 = r1
        L64:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L7c
            if (r1 == 0) goto L71
            r1.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r1 = move-exception
            r1.printStackTrace()
        L71:
            if (r2 == 0) goto L7b
            r2.close()     // Catch: java.io.IOException -> L77
            goto L7b
        L77:
            r1 = move-exception
            r1.printStackTrace()
        L7b:
            return r0
        L7c:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L80:
            if (r0 == 0) goto L8a
            r0.close()     // Catch: java.io.IOException -> L86
            goto L8a
        L86:
            r0 = move-exception
            r0.printStackTrace()
        L8a:
            if (r2 == 0) goto L94
            r2.close()     // Catch: java.io.IOException -> L90
            goto L94
        L90:
            r0 = move-exception
            r0.printStackTrace()
        L94:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qmsp.sdk.c.g.h():byte[]");
    }

    private void i() {
        String appID;
        try {
            a(1003, 0, this.e);
            JSONObject c2 = c();
            String a2 = a(k);
            StringBuilder sb = new StringBuilder();
            sb.append("CB: ");
            sb.append(c2.toString());
            com.tencent.qmsp.sdk.f.g.d(a2, 0, sb.toString());
            com.tencent.qmsp.sdk.b.g b2 = com.tencent.qmsp.sdk.b.g.b();
            appID = com.tencent.qmsp.sdk.app.a.getAppID();
            b2.a(1, appID, 1, c2, new b());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int a(int i2) {
        Integer num = this.a.get(Integer.valueOf(i2));
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    public void a() {
        if (m != null) {
            m = null;
        }
    }

    public void a(c cVar) {
        this.b.add(cVar);
    }

    public void a(boolean z) {
        boolean taskStatus;
        String a2;
        StringBuilder sb;
        taskStatus = com.tencent.qmsp.sdk.app.a.getTaskStatus();
        if (!taskStatus) {
            com.tencent.qmsp.sdk.f.g.a(a(k), 1, "cb Task Finish！");
            return;
        }
        try {
            byte[] bArr = k;
            com.tencent.qmsp.sdk.f.g.a(a(bArr), 1, "Start to query cb!");
            if (!z) {
                if (f()) {
                    i();
                } else {
                    com.tencent.qmsp.sdk.f.g.a(a(bArr), 1, "time has not arrived!");
                }
            }
            a2 = a(bArr);
            sb = new StringBuilder();
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                a2 = a(k);
                sb = new StringBuilder();
            } catch (Throwable th2) {
                com.tencent.qmsp.sdk.f.g.a(a(k), 0, "next time: " + this.d);
                a(this.d);
                throw th2;
            }
        }
        sb.append("next time: ");
        sb.append(this.d);
        com.tencent.qmsp.sdk.f.g.a(a2, 0, sb.toString());
        a(this.d);
    }

    public void b() {
        byte[] a2 = new m().a(e(), null, 1);
        if (a2 != null) {
            b(a2);
        }
    }
}
