package org.telegram.ui;

import android.content.Context;
import android.widget.TextView;
import com.lxj.xpopup.core.AttachPopupView;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class JMTMsgHintPopup extends AttachPopupView {
    private String mMsgHint;

    public JMTMsgHintPopup(Context context, String str) {
        super(context);
        this.mMsgHint = str;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_popup_msg_hint;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ((TextView) findViewById(R.id.jmt_popup_msg_hint_text)).setText(this.mMsgHint);
    }
}
