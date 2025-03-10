package com.tencent.qimei.y;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/* compiled from: X5Browser.java */
/* loaded from: classes.dex */
public class k {
    public Context b;
    public WebView a = null;
    public final a c = new a("x5");

    public k(Context context) {
        this.b = context;
    }

    public final void a() {
        WebView webView = this.a;
        if (webView != null) {
            webView.destroy();
            this.a = null;
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public final void b() {
        WebView webView = new WebView(this.b);
        this.a = webView;
        if (webView.getX5WebViewExtension() == null) {
            this.c.a("x5_sys");
        }
        this.a.removeJavascriptInterface("searchBoxJavaBridge_");
        this.a.removeJavascriptInterface("accessibility");
        this.a.removeJavascriptInterface("accessibilityTraversal");
        WebSettings settings = this.a.getSettings();
        settings.setSavePassword(false);
        settings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 16) {
            settings.setAllowFileAccessFromFileURLs(false);
            settings.setAllowUniversalAccessFromFileURLs(false);
        }
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(-1);
        this.a.addJavascriptInterface(this.c, "JSInterface");
        this.a.setWebViewClient(new h(this));
        this.a.loadUrl(com.tencent.qimei.a.a.a(this.b));
    }
}
