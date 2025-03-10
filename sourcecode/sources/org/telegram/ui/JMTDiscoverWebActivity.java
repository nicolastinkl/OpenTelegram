package org.telegram.ui;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.hjq.window.EasyWindow;
import com.hjq.window.draggable.SpringDraggable;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTDiscoverWebActivity extends BaseFragment {
    private ImageLocation mImageLocation;
    private String mUrl;
    private int mVpnPort;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    public JMTDiscoverWebActivity(String str, int i, ImageLocation imageLocation) {
        this.mUrl = str;
        this.mVpnPort = i;
        this.mImageLocation = imageLocation;
        BuildVars.floatUrl = str;
        BuildVars.floatVpnPort = i;
        EasyWindow.cancelAll();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        String str;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTDiscover", R.string.JMTDiscover));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTDiscoverWebActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (DialogsActivity.discoverWebView.canGoBack()) {
                    DialogsActivity.discoverWebView.goBack();
                    return;
                }
                DialogsActivity.discoverWebView.removeAllViews();
                DialogsActivity.discoverWebView.destroy();
                DialogsActivity.discoverWebView = null;
                JMTDiscoverWebActivity.this.finishFragment();
            }
        });
        ActionBarMenu createMenu = this.actionBar.createMenu();
        createMenu.addItem(0, R.mipmap.ic_refresh_24).setOnClickListener(new View.OnClickListener(this) { // from class: org.telegram.ui.JMTDiscoverWebActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DialogsActivity.discoverWebView.reload();
            }
        });
        createMenu.addItem(1, R.drawable.ic_float_web).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTDiscoverWebActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                final LaunchActivity launchActivity = (LaunchActivity) JMTDiscoverWebActivity.this.getParentActivity();
                View inflate = View.inflate(launchActivity, R.layout.window_float, null);
                int i = R.id.biv_icon;
                final BackupImageView backupImageView = (BackupImageView) inflate.findViewById(i);
                backupImageView.setRoundRadius(AndroidUtilities.dp(30.0f));
                backupImageView.setImage(JMTDiscoverWebActivity.this.mImageLocation, "60_60", (Drawable) null, (Object) null);
                EasyWindow.with(launchActivity).setContentView(inflate).setWidth(AndroidUtilities.dp(60.0f)).setHeight(AndroidUtilities.dp(60.0f)).setXOffset((JMTDiscoverWebActivity.getScreenWidth(launchActivity) / 2) - AndroidUtilities.dp(30.0f)).setDraggable(new SpringDraggable()).setOnClickListener(i, new EasyWindow.OnClickListener<BackupImageView>(this) { // from class: org.telegram.ui.JMTDiscoverWebActivity.3.1
                    @Override // com.hjq.window.EasyWindow.OnClickListener
                    public /* bridge */ /* synthetic */ void onClick(EasyWindow easyWindow, BackupImageView backupImageView2) {
                        onClick2((EasyWindow<?>) easyWindow, backupImageView2);
                    }

                    /* renamed from: onClick, reason: avoid collision after fix types in other method */
                    public void onClick2(EasyWindow<?> easyWindow, BackupImageView backupImageView2) {
                        easyWindow.cancel();
                        launchActivity.lambda$runLinkRequest$77(new JMTDiscoverWebActivity(BuildVars.floatUrl, BuildVars.floatVpnPort, backupImageView.getImageReceiver().getImageLocation()));
                    }
                }).show();
                JMTDiscoverActivity jMTDiscoverActivity = JMTDiscoverActivity.activity;
                if (jMTDiscoverActivity != null) {
                    jMTDiscoverActivity.removeSelfFromStack();
                    JMTDiscoverActivity.activity = null;
                }
                JMTDiscoverWebActivity.this.finishFragment();
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        FrameLayout frameLayout2 = frameLayout;
        WebView webView = DialogsActivity.discoverWebView;
        if (webView == null) {
            WebView webView2 = new WebView(context);
            DialogsActivity.discoverWebView = webView2;
            WebSettings settings = webView2.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setCacheMode(-1);
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setDomStorageEnabled(true);
            settings.setJavaScriptEnabled(true);
            settings.setBlockNetworkImage(false);
            if (Build.VERSION.SDK_INT >= 21) {
                settings.setMixedContentMode(0);
            }
            WebViewClient webViewClient = new WebViewClient() { // from class: org.telegram.ui.JMTDiscoverWebActivity.4
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView3, String str2) {
                    BuildVars.floatUrl = str2;
                    BuildVars.floatVpnPort = JMTDiscoverWebActivity.this.mVpnPort;
                    return super.shouldOverrideUrlLoading(webView3, str2);
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView webView3, int i, String str2, String str3) {
                    super.onReceivedError(webView3, i, str2, str3);
                }

                @Override // android.webkit.WebViewClient
                public void onPageFinished(WebView webView3, String str2) {
                    super.onPageFinished(webView3, str2);
                    BuildVars.floatUrl = str2;
                    BuildVars.floatVpnPort = JMTDiscoverWebActivity.this.mVpnPort;
                }
            };
            WebChromeClient webChromeClient = new WebChromeClient() { // from class: org.telegram.ui.JMTDiscoverWebActivity.5
                @Override // android.webkit.WebChromeClient
                public void onProgressChanged(WebView webView3, int i) {
                    super.onProgressChanged(webView3, i);
                }

                @Override // android.webkit.WebChromeClient
                public void onReceivedTitle(WebView webView3, String str2) {
                    super.onReceivedTitle(webView3, str2);
                }

                @Override // android.webkit.WebChromeClient
                public boolean onShowFileChooser(WebView webView3, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                    JMTDiscoverWebActivity.this.uploadMessageAboveL = valueCallback;
                    JMTDiscoverWebActivity.this.openImageChooserActivity();
                    return true;
                }
            };
            DownloadListener downloadListener = new DownloadListener(this) { // from class: org.telegram.ui.JMTDiscoverWebActivity.6
                @Override // android.webkit.DownloadListener
                public void onDownloadStart(String str2, String str3, String str4, String str5, long j) {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str2)));
                }
            };
            DialogsActivity.discoverWebView.setWebViewClient(webViewClient);
            DialogsActivity.discoverWebView.setWebChromeClient(webChromeClient);
            DialogsActivity.discoverWebView.setDownloadListener(downloadListener);
            if (BuildVars.isOpenVPN && this.mVpnPort != 0) {
                String str2 = BuildVars.openVPNUrl;
                if (this.mUrl.startsWith("https://") && str2.startsWith("http://")) {
                    str2 = str2.replace("http://", "https://");
                }
                str = str2 + ":" + this.mVpnPort;
            } else {
                str = this.mUrl;
            }
            DialogsActivity.discoverWebView.loadUrl(str);
        } else {
            ViewParent parent = webView.getParent();
            while (true) {
                if (parent == null) {
                    break;
                }
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(DialogsActivity.discoverWebView);
                    break;
                }
                parent = parent.getParent();
            }
        }
        frameLayout2.addView(DialogsActivity.discoverWebView, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
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

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (DialogsActivity.discoverWebView.canGoBack()) {
            DialogsActivity.discoverWebView.goBack();
            return false;
        }
        DialogsActivity.discoverWebView.removeAllViews();
        DialogsActivity.discoverWebView.destroy();
        DialogsActivity.discoverWebView = null;
        return true;
    }
}
