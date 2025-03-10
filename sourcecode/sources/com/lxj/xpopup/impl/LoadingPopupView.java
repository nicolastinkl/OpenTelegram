package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class LoadingPopupView extends CenterPopupView {
    private boolean firstShow;
    private Style loadingStyle;
    private View progressBar;
    private View spinnerView;
    private CharSequence title;
    private TextView tv_title;

    public enum Style {
        Spinner,
        ProgressBar
    }

    public LoadingPopupView(Context context, int bindLayoutId) {
        super(context);
        this.loadingStyle = Style.Spinner;
        this.firstShow = true;
        this.bindLayoutId = bindLayoutId;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i != 0 ? i : R$layout._xpopup_center_impl_loading;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(R$id.tv_title);
        this.progressBar = findViewById(R$id.loadProgress);
        this.spinnerView = findViewById(R$id.loadview);
        if (Build.VERSION.SDK_INT >= 21) {
            getPopupImplView().setElevation(10.0f);
        }
        if (this.bindLayoutId == 0) {
            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#212121"), this.popupInfo.borderRadius));
        }
        setup();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onShow() {
        super.onShow();
        this.firstShow = false;
    }

    protected void setup() {
        post(new Runnable() { // from class: com.lxj.xpopup.impl.LoadingPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                if (!LoadingPopupView.this.firstShow) {
                    TransitionManager.beginDelayedTransition(((CenterPopupView) LoadingPopupView.this).centerPopupContainer, new TransitionSet().setDuration(LoadingPopupView.this.getAnimationDuration()).addTransition(new Fade()).addTransition(new ChangeBounds()));
                }
                if (LoadingPopupView.this.title == null || LoadingPopupView.this.title.length() == 0) {
                    XPopupUtils.setVisible(LoadingPopupView.this.tv_title, false);
                } else {
                    XPopupUtils.setVisible(LoadingPopupView.this.tv_title, true);
                    if (LoadingPopupView.this.tv_title != null) {
                        LoadingPopupView.this.tv_title.setText(LoadingPopupView.this.title);
                    }
                }
                if (LoadingPopupView.this.loadingStyle == Style.Spinner) {
                    XPopupUtils.setVisible(LoadingPopupView.this.progressBar, false);
                    XPopupUtils.setVisible(LoadingPopupView.this.spinnerView, true);
                } else {
                    XPopupUtils.setVisible(LoadingPopupView.this.progressBar, true);
                    XPopupUtils.setVisible(LoadingPopupView.this.spinnerView, false);
                }
            }
        });
    }

    public LoadingPopupView setTitle(CharSequence title) {
        this.title = title;
        setup();
        return this;
    }

    public LoadingPopupView setStyle(Style style) {
        this.loadingStyle = style;
        setup();
        return this;
    }
}
