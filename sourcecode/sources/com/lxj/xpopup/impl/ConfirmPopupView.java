package com.lxj.xpopup.impl;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.lxj.xpopup.R$color;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class ConfirmPopupView extends CenterPopupView implements View.OnClickListener {
    OnCancelListener cancelListener;
    CharSequence cancelText;
    OnConfirmListener confirmListener;
    CharSequence confirmText;
    CharSequence content;
    View divider1;
    View divider2;
    EditText et_input;
    CharSequence hint;
    public boolean isHideCancel;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_confirm;
    TextView tv_content;
    TextView tv_title;

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i != 0 ? i : R$layout._xpopup_center_impl_confirm;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(R$id.tv_title);
        this.tv_content = (TextView) findViewById(R$id.tv_content);
        this.tv_cancel = (TextView) findViewById(R$id.tv_cancel);
        this.tv_confirm = (TextView) findViewById(R$id.tv_confirm);
        this.tv_content.setMovementMethod(LinkMovementMethod.getInstance());
        this.et_input = (EditText) findViewById(R$id.et_input);
        this.divider1 = findViewById(R$id.xpopup_divider1);
        this.divider2 = findViewById(R$id.xpopup_divider2);
        this.tv_cancel.setOnClickListener(this);
        this.tv_confirm.setOnClickListener(this);
        if (!TextUtils.isEmpty(this.title)) {
            this.tv_title.setText(this.title);
        } else {
            XPopupUtils.setVisible(this.tv_title, false);
        }
        if (!TextUtils.isEmpty(this.content)) {
            this.tv_content.setText(this.content);
        } else {
            XPopupUtils.setVisible(this.tv_content, false);
        }
        if (!TextUtils.isEmpty(this.cancelText)) {
            this.tv_cancel.setText(this.cancelText);
        }
        if (!TextUtils.isEmpty(this.confirmText)) {
            this.tv_confirm.setText(this.confirmText);
        }
        if (this.isHideCancel) {
            XPopupUtils.setVisible(this.tv_cancel, false);
            XPopupUtils.setVisible(this.divider2, false);
        }
        applyTheme();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i = R$color._xpopup_content_color;
        textView.setTextColor(resources.getColor(i));
        this.tv_content.setTextColor(getResources().getColor(i));
        this.tv_cancel.setTextColor(Color.parseColor("#666666"));
        this.tv_confirm.setTextColor(XPopup.getPrimaryColor());
        View view = this.divider1;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R$color._xpopup_list_divider));
        }
        View view2 = this.divider2;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(R$color._xpopup_list_divider));
        }
    }

    public TextView getTitleTextView() {
        return (TextView) findViewById(R$id.tv_title);
    }

    public TextView getContentTextView() {
        return (TextView) findViewById(R$id.tv_content);
    }

    public TextView getCancelTextView() {
        return (TextView) findViewById(R$id.tv_cancel);
    }

    public TextView getConfirmTextView() {
        return (TextView) findViewById(R$id.tv_confirm);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i = R$color._xpopup_white_color;
        textView.setTextColor(resources.getColor(i));
        this.tv_content.setTextColor(getResources().getColor(i));
        this.tv_cancel.setTextColor(getResources().getColor(i));
        this.tv_confirm.setTextColor(getResources().getColor(i));
        View view = this.divider1;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R$color._xpopup_list_dark_divider));
        }
        View view2 = this.divider2;
        if (view2 != null) {
            view2.setBackgroundColor(getResources().getColor(R$color._xpopup_list_dark_divider));
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        if (v == this.tv_cancel) {
            OnCancelListener onCancelListener = this.cancelListener;
            if (onCancelListener != null) {
                onCancelListener.onCancel();
            }
            dismiss();
            return;
        }
        if (v == this.tv_confirm) {
            OnConfirmListener onConfirmListener = this.confirmListener;
            if (onConfirmListener != null) {
                onConfirmListener.onConfirm();
            }
            if (this.popupInfo.autoDismiss.booleanValue()) {
                dismiss();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getMaxHeight() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        int i = popupInfo.maxHeight;
        return i == 0 ? (int) (XPopupUtils.getAppHeight(getContext()) * 0.8d) : i;
    }
}
