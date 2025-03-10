package com.shubao.xinstall.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.shubao.xinstall.a.f.n;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;

/* loaded from: classes.dex */
public final class d {
    public static volatile d a;
    private static final ThreadPoolExecutor b;
    private static final ThreadPoolExecutor c;
    private static Boolean i;
    private Integer d;
    private Integer e;
    private Context f;
    private SharedPreferences g;
    private f h;

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int availableProcessors2 = Runtime.getRuntime().availableProcessors() * 4;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        b = new ThreadPoolExecutor(availableProcessors, availableProcessors2, 60L, timeUnit, new LinkedBlockingQueue(1));
        c = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors() * 4, 60L, timeUnit, new LinkedBlockingQueue(1));
    }

    private d(Context context, f fVar) {
        this.f = context;
        this.h = fVar;
        this.g = context.getSharedPreferences("net_error_mamage_sp_file", 0);
        b.execute(new Runnable() { // from class: com.shubao.xinstall.a.a.d.1
            @Override // java.lang.Runnable
            public final void run() {
                d.a(d.this);
                if (d.b(d.this).booleanValue() && d.this.b().booleanValue()) {
                    d.d(d.this);
                }
            }
        });
    }

    private int a(String str) {
        return this.g.getInt(str, 0);
    }

    public static d a(Context context, f fVar) {
        if (a == null) {
            synchronized (com.shubao.xinstall.a.b.class) {
                if (a == null) {
                    a = new d(context, fVar);
                }
            }
        }
        return a;
    }

    private Boolean a(Integer num, JSONArray jSONArray) {
        File b2 = b(num);
        try {
            new n();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(b2), "utf-8"));
            bufferedWriter.write(jSONArray.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            a(b2.getName(), jSONArray.length());
            return Boolean.TRUE;
        } catch (FileNotFoundException e) {
            e = e;
            e.printStackTrace();
            return Boolean.FALSE;
        } catch (UnsupportedEncodingException e2) {
            e = e2;
            e.printStackTrace();
            return Boolean.FALSE;
        } catch (IOException e3) {
            e3.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private static String a(String str, String str2) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bytes2 = str2.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(bytes, "HmacSHA1"));
            return new String(com.shubao.xinstall.a.f.e.a(mac.doFinal(bytes2)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Not supported encoding method UTF-8", e);
        } catch (InvalidKeyException e2) {
            throw new RuntimeException("Failed to calculate the signature", e2);
        } catch (NoSuchAlgorithmException e3) {
            throw new RuntimeException("Not supported signature method hmac-sha1", e3);
        }
    }

    private JSONArray a(Integer num) {
        JSONArray jSONArray = new JSONArray();
        File b2 = b(num);
        if (!b2.exists()) {
            System.out.println("未找到文件");
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(b2), "utf-8"));
            String str = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return new JSONArray(str);
                }
                str = str + readLine;
            }
        } catch (Exception unused) {
            return jSONArray;
        }
    }

    public static void a() {
        if (a == null || !a.b().booleanValue()) {
            return;
        }
        b.execute(new Runnable() { // from class: com.shubao.xinstall.a.a.d.2
            @Override // java.lang.Runnable
            public final void run() {
                d.d(d.a);
            }
        });
    }

    static /* synthetic */ void a(d dVar) {
        String c2 = c((Integer) 6);
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, ?> entry : dVar.g.getAll().entrySet()) {
            if (entry.getKey().compareTo(c2) <= 0) {
                arrayList.add(entry.getKey());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            dVar.b(new File((String) it.next()));
        }
    }

    static /* synthetic */ void a(d dVar, com.shubao.xinstall.a.b.b bVar) {
        if (bVar != null) {
            if (com.shubao.xinstall.a.b.b == null || !com.shubao.xinstall.a.b.b.c.d().c().booleanValue()) {
                try {
                    File c2 = dVar.c();
                    if (!(c2.exists() && c2.isDirectory())) {
                        a(dVar.c());
                    }
                    File b2 = dVar.b((Integer) 0);
                    if (!b2.isFile() || !b2.exists()) {
                        b2.createNewFile();
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.put(bVar.a());
                        dVar.a((Integer) 0, jSONArray);
                        return;
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(b2, "rw");
                    randomAccessFile.seek(randomAccessFile.length() - 1);
                    new n();
                    randomAccessFile.writeBytes("," + bVar.a().toString() + "]");
                    dVar.a(b2.getName(), Integer.valueOf(dVar.a(b2.getName())).intValue() + 1);
                } catch (Exception unused) {
                }
            }
        }
    }

    public static void a(final com.shubao.xinstall.a.b.b bVar) {
        if (a != null) {
            c.execute(new Runnable() { // from class: com.shubao.xinstall.a.a.d.3
                @Override // java.lang.Runnable
                public final void run() {
                    d.a(d.a, com.shubao.xinstall.a.b.b.this);
                }
            });
        }
    }

    private void a(String str, int i2) {
        try {
            SharedPreferences.Editor edit = this.g.edit();
            edit.putInt(str, i2);
            edit.apply();
        } catch (Exception unused) {
        }
    }

    private static boolean a(File file) {
        while (!file.getParentFile().exists()) {
            a(file.getParentFile());
        }
        return file.mkdir();
    }

    private File b(Integer num) {
        return new File(c().toString() + "/" + c(num));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Boolean b() {
        if (this.h.d() != null && this.h.d().c != null) {
            this.d = this.h.d().c;
            this.e = 0;
            for (int i2 = 1; i2 <= 5; i2++) {
                File b2 = b(Integer.valueOf(i2));
                if (b2.exists() && b2.isFile()) {
                    this.e = Integer.valueOf(this.e.intValue() + a(b2.getName()));
                }
            }
            if (this.e.intValue() >= this.d.intValue()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    static /* synthetic */ Boolean b(d dVar) {
        String c2 = c((Integer) 6);
        String c3 = c((Integer) 1);
        new ArrayList();
        Iterator<Map.Entry<String, ?>> it = dVar.g.getAll().entrySet().iterator();
        while (it.hasNext()) {
            String str = it.next().getKey().toString();
            if (str.compareTo(c2) > 0 && str.compareTo(c3) <= 0) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void b(File file) {
        if (file.exists() && file.isFile() && file.delete()) {
            String name = file.getName();
            try {
                SharedPreferences.Editor edit = this.g.edit();
                edit.remove(name);
                edit.apply();
            } catch (Exception unused) {
            }
        }
    }

    private File c() {
        return new File(this.f.getFilesDir().getPath() + "/Xinstall/NetErrorDir/Android");
    }

    private static String c(Integer num) {
        StringBuilder sb;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        if (num.intValue() != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(5, -num.intValue());
            sb = new StringBuilder();
            sb.append("local");
            sb.append("_");
            date = calendar.getTime();
        } else {
            sb = new StringBuilder();
            sb.append("local");
            sb.append("_");
        }
        sb.append(simpleDateFormat.format(date));
        sb.append(".json");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01d3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static /* synthetic */ void d(com.shubao.xinstall.a.a.d r13) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.shubao.xinstall.a.a.d.d(com.shubao.xinstall.a.a.d):void");
    }
}
