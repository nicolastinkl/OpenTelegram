package com.tencent.qimei.y;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/* compiled from: SysBrowser.java */
/* loaded from: classes.dex */
public class g {
    public Context b;
    public WebView a = null;
    public final a c = new a("sys");

    public g(Context context) {
        this.b = context;
    }

    public final void a(WebView webView) {
        if (Build.VERSION.SDK_INT >= 19) {
            webView.evaluateJavascript("javascript:new Fingerprint().getNative()", new c(this));
        } else {
            webView.loadUrl("javascript:new Fingerprint().getNative()");
        }
    }

    public static /* synthetic */ void a(g gVar) {
        if (gVar.a != null) {
            gVar.a();
        }
        try {
            WebView webView = new WebView(gVar.b);
            gVar.a = webView;
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            gVar.a.removeJavascriptInterface("accessibility");
            gVar.a.removeJavascriptInterface("accessibilityTraversal");
            WebSettings settings = gVar.a.getSettings();
            settings.setSavePassword(false);
            settings.setAllowFileAccess(false);
            if (Build.VERSION.SDK_INT >= 16) {
                settings.setAllowFileAccessFromFileURLs(false);
                settings.setAllowUniversalAccessFromFileURLs(false);
            }
            settings.setJavaScriptEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setCacheMode(-1);
            gVar.a.addJavascriptInterface(gVar.c, "JSInterface");
            gVar.a.setWebViewClient(new d(gVar));
            gVar.a.loadUrl(com.tencent.qimei.a.a.a(gVar.b));
        } catch (RuntimeException e) {
            com.tencent.qimei.k.a.a(e);
        }
    }

    public final void a() {
        WebView webView = this.a;
        if (webView != null) {
            webView.destroy();
            this.a = null;
        }
    }
}
