package com.tencent.qmsp.sdk.g.d;

import android.os.AsyncTask;

/* loaded from: classes.dex */
public class d extends AsyncTask<Void, Void, Boolean> {
    public a a;
    public c b;

    public d(a aVar, c cVar) {
        this.a = aVar;
        this.b = cVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean doInBackground(Void... voidArr) {
        boolean c;
        c cVar;
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
        if (c && (cVar = this.b) != null) {
            cVar.a(true);
        }
        return Boolean.valueOf(c);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
    }
}
