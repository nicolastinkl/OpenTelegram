package org.telegram.ui;

import android.content.Context;
import android.widget.TextView;
import com.lxj.xpopup.core.PositionPopupView;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class JMTErrorMsgPopup extends PositionPopupView {
    private String content;

    public JMTErrorMsgPopup(Context context, String str) {
        super(context);
        this.content = str;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_error_msg_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ((TextView) findViewById(R.id.tv_error_msg_popup)).setText(this.content);
        delayDismiss(1500L);
    }
}
