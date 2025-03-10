package org.telegram.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.lxj.xpopup.core.CenterPopupView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_agreeNewLogin;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;

/* loaded from: classes3.dex */
public class JMTDangerPopup extends CenterPopupView {
    private Context mContext;
    private OnSuccessListener onSuccessListener;

    public interface OnSuccessListener {
        void onSuccess();
    }

    public JMTDangerPopup(Context context, OnSuccessListener onSuccessListener) {
        super(context);
        this.mContext = context;
        this.onSuccessListener = onSuccessListener;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_danger_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ((ImageView) findViewById(R.id.jmt_danger_close)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTDangerPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTDangerPopup.this.dismiss();
            }
        });
        ((TextView) findViewById(R.id.jmt_danger_title)).setText(LocaleController.getString("JMTLoginDanger", R.string.JMTLoginDanger));
        ((PinView) findViewById(R.id.jmt_danger_pv)).addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTDangerPopup.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 6) {
                    JMTDangerPopup.this.verifyCode(charSequence.toString());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyCode(String str) {
        TLRPC$JMT_agreeNewLogin tLRPC$JMT_agreeNewLogin = new TLRPC$JMT_agreeNewLogin();
        tLRPC$JMT_agreeNewLogin.code = str;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_agreeNewLogin, new RequestDelegate() { // from class: org.telegram.ui.JMTDangerPopup.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTDangerPopup.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            if (tLObject instanceof TLRPC$TL_boolTrue) {
                                Toast.makeText(JMTDangerPopup.this.mContext, LocaleController.getString("JMTLoginDangerSuccess", R.string.JMTLoginDangerSuccess), 0).show();
                                JMTDangerPopup.this.onSuccessListener.onSuccess();
                            } else {
                                Toast.makeText(JMTDangerPopup.this.mContext, LocaleController.getString("JMTLoginDangerError", R.string.JMTLoginDangerError), 0).show();
                            }
                        }
                        JMTDangerPopup.this.dismiss();
                    }
                });
            }
        }, 10);
    }
}
