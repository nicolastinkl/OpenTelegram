package com.lxj.xpopup.impl;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class InputConfirmPopupView extends ConfirmPopupView {
    OnCancelListener cancelListener;
    OnInputConfirmListener inputConfirmListener;
    public CharSequence inputContent;

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        XPopupUtils.setVisible(this.et_input, true);
        if (!TextUtils.isEmpty(this.hint)) {
            this.et_input.setHint(this.hint);
        }
        if (!TextUtils.isEmpty(this.inputContent)) {
            this.et_input.setText(this.inputContent);
            this.et_input.setSelection(this.inputContent.length());
        }
        XPopupUtils.setCursorDrawableColor(this.et_input, XPopup.getPrimaryColor());
        if (this.bindLayoutId == 0) {
            this.et_input.post(new Runnable() { // from class: com.lxj.xpopup.impl.InputConfirmPopupView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InputConfirmPopupView.this.lambda$onCreate$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        if (this.et_input.getMeasuredWidth() > 0) {
            this.et_input.setBackgroundDrawable(XPopupUtils.createSelector(XPopupUtils.createBitmapDrawable(getContext(), this.et_input.getMeasuredWidth(), Color.parseColor("#888888")), XPopupUtils.createBitmapDrawable(getContext(), this.et_input.getMeasuredWidth(), XPopup.getPrimaryColor())));
        }
    }

    public EditText getEditText() {
        return this.et_input;
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        this.et_input.setHintTextColor(Color.parseColor("#888888"));
        this.et_input.setTextColor(Color.parseColor("#333333"));
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        this.et_input.setHintTextColor(Color.parseColor("#888888"));
        this.et_input.setTextColor(Color.parseColor("#dddddd"));
    }

    @Override // com.lxj.xpopup.impl.ConfirmPopupView, android.view.View.OnClickListener
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
            OnInputConfirmListener onInputConfirmListener = this.inputConfirmListener;
            if (onInputConfirmListener != null) {
                onInputConfirmListener.onConfirm(this.et_input.getText().toString().trim());
            }
            if (this.popupInfo.autoDismiss.booleanValue()) {
                dismiss();
            }
        }
    }
}
