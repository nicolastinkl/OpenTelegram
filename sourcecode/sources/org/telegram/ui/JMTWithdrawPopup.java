package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lxj.xpopup.core.BottomPopupView;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class JMTWithdrawPopup extends BottomPopupView {
    private LinearLayout llWithdrawCancel;
    private LinearLayout llWithdrawOpen;
    private OnContactListener onContactListener;
    private String title;

    public interface OnContactListener {
        void onContact();
    }

    public JMTWithdrawPopup(Context context, String str, OnContactListener onContactListener) {
        super(context);
        this.title = str;
        this.onContactListener = onContactListener;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_withdraw_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        this.llWithdrawOpen = (LinearLayout) findViewById(R.id.ll_withdraw_open);
        ((TextView) findViewById(R.id.tv_point_0)).setText(this.title);
        ((TextView) findViewById(R.id.tv_point_1)).setText(LocaleController.getString("JMTProvideServices", R.string.JMTProvideServices));
        ((TextView) findViewById(R.id.tv_point_2)).setText(LocaleController.getString("JMTContactNow", R.string.JMTContactNow));
        this.llWithdrawCancel = (LinearLayout) findViewById(R.id.ll_withdraw_cancel);
        ((TextView) findViewById(R.id.tv_point_cancel)).setText(LocaleController.getString("JMTCancel", R.string.JMTCancel));
        this.llWithdrawOpen.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTWithdrawPopup$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                JMTWithdrawPopup.this.lambda$onCreate$0(view);
            }
        });
        this.llWithdrawCancel.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTWithdrawPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                JMTWithdrawPopup.this.lambda$onCreate$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        OnContactListener onContactListener = this.onContactListener;
        if (onContactListener != null) {
            onContactListener.onContact();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }
}
