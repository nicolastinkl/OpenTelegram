package com.tencent.qimei.j;

import android.content.Context;
import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;

/* compiled from: PropertyReader.java */
/* loaded from: classes.dex */
public class d {
    public String a;

    public d(Context context, String str) {
        if (context == null) {
            return;
        }
        this.a = str;
    }

    public String a() {
        long currentTimeMillis = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        Date date = new Date();
        date.setTime(currentTimeMillis);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
        String c = com.tencent.qimei.i.f.a(this.a).c("f_uptimes");
        LinkedList linkedList = new LinkedList();
        boolean z = false;
        for (String str : c.split(";")) {
            if (!str.isEmpty()) {
                linkedList.add(str);
            }
        }
        if (linkedList.size() == 0 || !((String) linkedList.getFirst()).equals(format)) {
            linkedList.addFirst(format);
            z = true;
        }
        if (linkedList.size() > 5) {
            linkedList.remove(linkedList.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            sb.append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        if (z) {
            com.tencent.qimei.i.f.a(this.a).a("f_uptimes", sb.toString());
        }
        return sb.toString();
    }
}
