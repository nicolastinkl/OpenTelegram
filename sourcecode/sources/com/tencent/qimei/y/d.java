package com.tencent.qimei.y;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/* compiled from: SysBrowser.java */
/* loaded from: classes.dex */
public class d extends WebViewClient {
    public final /* synthetic */ g a;

    public d(g gVar) {
        this.a = gVar;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        try {
            this.a.a(webView);
        } catch (Exception e) {
            com.tencent.qimei.k.a.a(e);
        }
    }
}
