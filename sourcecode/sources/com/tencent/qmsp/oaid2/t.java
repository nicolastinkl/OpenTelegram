package com.tencent.qmsp.oaid2;

import android.os.AsyncTask;

/* loaded from: classes.dex */
public class t extends AsyncTask<Void, Void, Boolean> {
    public q a;
    public s b;

    public t(q qVar, s sVar) {
        this.a = qVar;
        this.b = sVar;
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean doInBackground(Void... voidArr) {
        boolean c;
        s sVar;
        if (this.a == null) {
            return Boolean.FALSE;
        }
        int i = 0;
        while (true) {
            try {
                c = this.a.c();
            } catch (InterruptedException unused) {
            }
            if (c) {
                break;
            }
            Thread.sleep(10L);
            i++;
            if (i >= 30) {
                break;
            }
        }
        if (c && (sVar = this.b) != null) {
            sVar.a(true);
        }
        return Boolean.valueOf(c);
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
    }
}
