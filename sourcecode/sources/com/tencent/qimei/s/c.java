package com.tencent.qimei.s;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qimei.i.f;
import com.tencent.qimei.o.j;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: FileOperator.java */
/* loaded from: classes.dex */
public class c {
    public AtomicInteger a = new AtomicInteger();
    public Context b;
    public String c;
    public String d;
    public b e;

    /* compiled from: FileOperator.java */
    public interface b {
    }

    public c(Context context, String str, b bVar) {
        this.b = context;
        this.c = str;
        this.e = bVar;
        this.d = this.b.getFilesDir().getAbsolutePath() + "/jsfile/";
    }

    public final boolean a(String str) {
        return new File(this.d + str).exists();
    }

    public void b(boolean z) {
        String c = f.a(this.c).c("js_md_tm");
        if (z) {
            c = "";
        }
        com.tencent.qimei.b.a.a().a(new com.tencent.qimei.d.a("https://tun-cos-1258344701.file.myqcloud.com/fp.js", c, new com.tencent.qimei.s.b(this, "js_md_tm", "lc_fe_st_js")));
    }

    public final boolean a(String str, String str2) {
        return com.tencent.qimei.j.a.a(com.tencent.qimei.j.c.a(this.d, str)).equals(f.a(this.c).c(str2));
    }

    public void a(boolean z) {
        String c = f.a(this.c).c("hm_md_tm");
        if (z) {
            c = "";
        }
        com.tencent.qimei.b.a.a().a(new com.tencent.qimei.d.a("https://tun-cos-1258344701.file.myqcloud.com/my.html", c, new com.tencent.qimei.s.a(this, "hm_md_tm", "lc_fe_st_hm")));
    }

    /* compiled from: FileOperator.java */
    abstract class a implements com.tencent.qimei.d.c {
        public final String a;
        public final String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public abstract String a(String str);

        @Override // com.tencent.qimei.d.c
        public void a(String str, String... strArr) {
            String str2 = strArr.length > 0 ? strArr[0] : "";
            f.a(c.this.c).a(this.a, str2);
            c.a(c.this, this.b, a(str));
            com.tencent.qimei.k.a.b("HidTask", "pull content from server,code:%d lastModifiedTime:%s", 200, str2);
        }

        @Override // com.tencent.qimei.d.c
        public void a(String str, int i, String str2) {
            c.a(c.this, this.b, null);
            com.tencent.qimei.k.a.a("HidTask", "pull content from server,code = %d", Integer.valueOf(i));
        }
    }

    public static /* synthetic */ void a(c cVar, String str, String str2) {
        if (cVar.a.incrementAndGet() == 2) {
            ((j) cVar.e).c();
        }
        f.a(cVar.c).a("lc_fe_tm", System.currentTimeMillis());
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        f.a(cVar.c).a(str, com.tencent.qimei.j.a.a(str2));
    }
}
