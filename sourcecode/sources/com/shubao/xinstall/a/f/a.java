package com.shubao.xinstall.a.f;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import j$.util.DesugarCollections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes.dex */
public final class a {
    private static Map<String, a> a = new HashMap();
    private C0013a b;

    /* renamed from: com.shubao.xinstall.a.f.a$a, reason: collision with other inner class name */
    public class C0013a {
        final AtomicLong a;
        final AtomicInteger b;
        final Map<File, Long> c;
        protected File d;
        private final long f;
        private final int g;

        private C0013a(File file) {
            this.c = DesugarCollections.synchronizedMap(new HashMap());
            this.d = file;
            this.f = 10485760L;
            this.g = Integer.MAX_VALUE;
            this.a = new AtomicLong();
            this.b = new AtomicInteger();
            new Thread(new Runnable() { // from class: com.shubao.xinstall.a.f.a.a.1
                @Override // java.lang.Runnable
                public final void run() {
                    File[] listFiles = C0013a.this.d.listFiles();
                    if (listFiles != null) {
                        int i = 0;
                        int i2 = 0;
                        for (File file2 : listFiles) {
                            i = (int) (i + file2.length());
                            i2++;
                            C0013a.this.c.put(file2, Long.valueOf(file2.lastModified()));
                        }
                        C0013a.this.a.set(i);
                        C0013a.this.b.set(i2);
                    }
                }
            }).start();
        }

        /* synthetic */ C0013a(a aVar, File file, byte b) {
            this(file);
        }

        private long a() {
            File file;
            if (this.c.isEmpty()) {
                return 0L;
            }
            Set<Map.Entry<File, Long>> entrySet = this.c.entrySet();
            synchronized (this.c) {
                file = null;
                Long l = null;
                for (Map.Entry<File, Long> entry : entrySet) {
                    if (file == null) {
                        file = entry.getKey();
                        l = entry.getValue();
                    } else {
                        Long value = entry.getValue();
                        if (value.longValue() < l.longValue()) {
                            file = entry.getKey();
                            l = value;
                        }
                    }
                }
            }
            long length = file.length();
            if (file.delete()) {
                this.c.remove(file);
            }
            return length;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(File file) {
            int i = this.b.get();
            while (i + 1 > this.g) {
                this.a.addAndGet(-a());
                i = this.b.addAndGet(-1);
            }
            this.b.addAndGet(1);
            long length = file.length();
            long j = this.a.get();
            while (j + length > this.f) {
                j = this.a.addAndGet(-a());
            }
            this.a.addAndGet(length);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(valueOf.longValue());
            this.c.put(file, valueOf);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public File b(String str) {
            File file = this.d;
            StringBuilder sb = new StringBuilder();
            sb.append(str.hashCode());
            return new File(file, sb.toString());
        }

        final File a(String str) {
            File b = b(str);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            b.setLastModified(valueOf.longValue());
            this.c.put(b, valueOf);
            return b;
        }
    }

    static class b {
        private static String a() {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            String sb2 = sb.toString();
            while (sb2.length() < 13) {
                sb2 = "0".concat(sb2);
            }
            return sb2 + "-604800 ";
        }

        static String a(String str) {
            return a() + str;
        }

        static boolean a(byte[] bArr) {
            String[] strArr = b(bArr) ? new String[]{new String(a(bArr, 0, 13)), new String(a(bArr, 14, c(bArr)))} : null;
            if (strArr != null) {
                String str = strArr[0];
                while (str.startsWith("0")) {
                    str = str.substring(1, str.length());
                }
                if (System.currentTimeMillis() > Long.valueOf(str).longValue() + (Long.valueOf(strArr[1]).longValue() * 1000)) {
                    return true;
                }
            }
            return false;
        }

        private static byte[] a(byte[] bArr, int i, int i2) {
            int i3 = i2 - i;
            if (i3 >= 0) {
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
                return bArr2;
            }
            throw new IllegalArgumentException(i + " > " + i2);
        }

        static boolean b(byte[] bArr) {
            return bArr != null && bArr.length > 15 && bArr[13] == 45 && c(bArr) > 14;
        }

        private static int c(byte[] bArr) {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] == 32) {
                    return i;
                }
            }
            return -1;
        }
    }

    private a(File file) {
        if (file.exists() || file.mkdirs()) {
            this.b = new C0013a(this, file, (byte) 0);
        } else {
            throw new RuntimeException("can't make dirs in " + file.getAbsolutePath());
        }
    }

    public static a a(Context context, String str) {
        File file = new File(context.getCacheDir(), str);
        Log.d("ACache", file.getAbsolutePath());
        a aVar = a.get(file.getAbsoluteFile() + a());
        if (aVar != null) {
            return aVar;
        }
        a aVar2 = new a(file);
        a.put(file.getAbsolutePath() + a(), aVar2);
        return aVar2;
    }

    private static String a() {
        return "_" + Process.myPid();
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [boolean] */
    public final String a(String str) {
        BufferedReader bufferedReader;
        File a2 = this.b.a(str);
        ?? exists = a2.exists();
        BufferedReader bufferedReader2 = null;
        try {
            if (exists == 0) {
                return null;
            }
            try {
                bufferedReader = new BufferedReader(new FileReader(a2));
                String str2 = "";
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        str2 = str2 + readLine;
                    } catch (IOException e) {
                        e = e;
                        Log.i("ACache", e.getMessage());
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e2) {
                                Log.i("ACache", e2.getMessage());
                            }
                        }
                        return null;
                    }
                }
                if (b.a(str2.getBytes())) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e3) {
                        Log.i("ACache", e3.getMessage());
                    }
                    this.b.a(str).delete();
                    return null;
                }
                if (b.b(str2.getBytes())) {
                    str2 = str2.substring(str2.indexOf(32) + 1, str2.length());
                }
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                    Log.i("ACache", e4.getMessage());
                }
                return str2;
            } catch (IOException e5) {
                e = e5;
                bufferedReader = null;
            } catch (Throwable th) {
                th = th;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e6) {
                        Log.i("ACache", e6.getMessage());
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedReader2 = exists;
        }
    }

    public final void a(String str, String str2) {
        StringBuilder sb;
        File b2 = this.b.b(str);
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(b2), 1024);
                try {
                    bufferedWriter2.write(str2);
                } catch (IOException e) {
                    e = e;
                    bufferedWriter = bufferedWriter2;
                    Log.i("ACache", e.getMessage());
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e2) {
                            e = e2;
                            sb = new StringBuilder();
                            sb.append(e.getMessage());
                            Log.i("ACache", sb.toString());
                            this.b.a(b2);
                        }
                    }
                    this.b.a(b2);
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e3) {
                            Log.i("ACache", e3.getMessage());
                        }
                    }
                    this.b.a(b2);
                    throw th;
                }
                try {
                    bufferedWriter2.flush();
                    bufferedWriter2.close();
                } catch (IOException e4) {
                    e = e4;
                    sb = new StringBuilder();
                    sb.append(e.getMessage());
                    Log.i("ACache", sb.toString());
                    this.b.a(b2);
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e5) {
            e = e5;
        }
        this.b.a(b2);
    }

    public final void b(String str, String str2) {
        a(str, b.a(str2));
    }
}
