package org.telegram.ui;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$ReplyMarkup;
import org.telegram.tgnet.TLRPC$TL_geoPoint;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTBaiduMapActivity extends BaseFragment {
    private long dialogId;
    private Context mContext;
    private WebView mWebView;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        return false;
    }

    public JMTBaiduMapActivity(long j) {
        this.dialogId = j;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTHtmlMap", R.string.JMTHtmlMap));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTBaiduMapActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTBaiduMapActivity.this.finishFragment();
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        WebView webView = new WebView(context);
        this.mWebView = webView;
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(-1);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setGeolocationDatabasePath(context.getApplicationContext().getFilesDir().getPath());
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        WebViewClient webViewClient = new WebViewClient(this) { // from class: org.telegram.ui.JMTBaiduMapActivity.2
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView webView2, String str) {
                return super.shouldOverrideUrlLoading(webView2, str);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, int i, String str, String str2) {
                super.onReceivedError(webView2, i, str, str2);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(webView2, str);
            }
        };
        WebChromeClient webChromeClient = new WebChromeClient() { // from class: org.telegram.ui.JMTBaiduMapActivity.3
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                super.onProgressChanged(webView2, i);
            }

            @Override // android.webkit.WebChromeClient
            public void onReceivedTitle(WebView webView2, String str) {
                super.onReceivedTitle(webView2, str);
            }

            @Override // android.webkit.WebChromeClient
            public boolean onShowFileChooser(WebView webView2, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                JMTBaiduMapActivity.this.uploadMessageAboveL = valueCallback;
                JMTBaiduMapActivity.this.openImageChooserActivity();
                return true;
            }

            @Override // android.webkit.WebChromeClient
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.message().startsWith("native_message:")) {
                    try {
                        JSONObject jSONObject = new JSONObject(new JSONArray(consoleMessage.message().substring(15)).getString(1));
                        double d = jSONObject.getDouble("lat");
                        double d2 = jSONObject.getDouble("lng");
                        TLRPC$TL_messageMediaGeo tLRPC$TL_messageMediaGeo = new TLRPC$TL_messageMediaGeo();
                        TLRPC$TL_geoPoint tLRPC$TL_geoPoint = new TLRPC$TL_geoPoint();
                        tLRPC$TL_messageMediaGeo.geo = tLRPC$TL_geoPoint;
                        tLRPC$TL_geoPoint.lat = d;
                        tLRPC$TL_geoPoint._long = d2;
                        JMTBaiduMapActivity.this.getSendMessagesHelper().sendMessage((TLRPC$MessageMedia) tLRPC$TL_messageMediaGeo, JMTBaiduMapActivity.this.dialogId, (MessageObject) null, (MessageObject) null, (TLRPC$ReplyMarkup) null, (HashMap<String, String>) null, true, 0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JMTBaiduMapActivity.this.finishFragment();
                    return true;
                }
                return super.onConsoleMessage(consoleMessage);
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                callback.invoke(str, true, false);
                super.onGeolocationPermissionsShowPrompt(str, callback);
            }
        };
        DownloadListener downloadListener = new DownloadListener(this) { // from class: org.telegram.ui.JMTBaiduMapActivity.4
            @Override // android.webkit.DownloadListener
            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        };
        this.mWebView.setWebViewClient(webViewClient);
        this.mWebView.setWebChromeClient(webChromeClient);
        this.mWebView.setDownloadListener(downloadListener);
        frameLayout.addView(this.mWebView, LayoutHelper.createFrame(-1, -1.0f));
        if (Build.VERSION.SDK_INT >= 23) {
            getParentActivity().requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
        }
        return this.fragmentView;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResultFragment(i, strArr, iArr);
        if (getParentActivity() == null || i != 2 || iArr == null || iArr.length == 0) {
            return;
        }
        if (iArr[0] != 0) {
            showDialog(AlertsCreator.createLocationRequiredDialog(getParentActivity(), false));
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTBaiduMapActivity.5
                @Override // java.lang.Runnable
                public void run() {
                    JMTBaiduMapActivity.this.mWebView.loadUrl("file:///android_asset/map/index.html?ak=" + BuildVars.baiduMapAk);
                    try {
                        SmartLocation.with(JMTBaiduMapActivity.this.mContext).location().oneFix().start(new OnLocationUpdatedListener() { // from class: org.telegram.ui.JMTBaiduMapActivity.5.1
                            @Override // io.nlopez.smartlocation.OnLocationUpdatedListener
                            public void onLocationUpdated(Location location) {
                                JMTBaiduMapActivity.this.mWebView.loadUrl("file:///android_asset/map/index.html?ak=" + BuildVars.baiduMapAk + "&lng=" + location.getLongitude() + "&lat=" + location.getLatitude());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openImageChooserActivity() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Image Chooser"), 10000);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        super.onActivityResultFragment(i, i2, intent);
        if (i == 10000) {
            if (this.uploadMessage == null && this.uploadMessageAboveL == null) {
                return;
            }
            Uri data = (intent == null || i2 != -1) ? null : intent.getData();
            if (this.uploadMessageAboveL != null) {
                onActivityResultAboveL(i, i2, intent);
                return;
            }
            ValueCallback<Uri> valueCallback = this.uploadMessage;
            if (valueCallback != null) {
                valueCallback.onReceiveValue(data);
                this.uploadMessage = null;
            }
        }
    }

    @TargetApi(21)
    private void onActivityResultAboveL(int i, int i2, Intent intent) {
        Uri[] uriArr;
        if (i != 10000 || this.uploadMessageAboveL == null) {
            return;
        }
        if (i2 != -1 || intent == null) {
            uriArr = null;
        } else {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                uriArr = new Uri[clipData.getItemCount()];
                for (int i3 = 0; i3 < clipData.getItemCount(); i3++) {
                    uriArr[i3] = clipData.getItemAt(i3).getUri();
                }
            } else {
                uriArr = null;
            }
            if (dataString != null) {
                uriArr = new Uri[]{Uri.parse(dataString)};
            }
        }
        this.uploadMessageAboveL.onReceiveValue(uriArr);
        this.uploadMessageAboveL = null;
    }

    @TargetApi(19)
    public void releaseAllWebViewCallback() {
        try {
            if (Build.VERSION.SDK_INT < 16) {
                Field declaredField = WebView.class.getDeclaredField("mWebViewCore").getType().getDeclaredField("mBrowserFrame").getType().getDeclaredField("sConfigCallback");
                declaredField.setAccessible(true);
                declaredField.set(null, null);
            } else {
                Field declaredField2 = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (declaredField2 != null) {
                    declaredField2.setAccessible(true);
                    declaredField2.set(null, null);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException unused) {
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        releaseAllWebViewCallback();
        this.mWebView.removeAllViews();
        this.mWebView.destroy();
    }
}
