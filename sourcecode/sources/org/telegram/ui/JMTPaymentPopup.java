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
import org.telegram.tgnet.TLRPC$JMT_verifyWalletPassword;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;

/* loaded from: classes3.dex */
public class JMTPaymentPopup extends CenterPopupView {
    private Context mContext;
    private String money;
    private OnPaymentListener onPaymentListener;

    public interface OnPaymentListener {
        void onPayment();
    }

    public JMTPaymentPopup(Context context, String str, OnPaymentListener onPaymentListener) {
        super(context);
        this.mContext = context;
        this.money = str;
        this.onPaymentListener = onPaymentListener;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_payment_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ((ImageView) findViewById(R.id.jmt_payment_close)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTPaymentPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTPaymentPopup.this.dismiss();
            }
        });
        ((TextView) findViewById(R.id.jmt_payment_title)).setText(LocaleController.getString("JMTInputPaymentPassword", R.string.JMTInputPaymentPassword));
        ((TextView) findViewById(R.id.jmt_payment_money)).setText(LocaleController.getString("JmtInputPaymentMoney", R.string.JmtInputPaymentMoney) + this.money);
        ((PinView) findViewById(R.id.jmt_payment_pv)).addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTPaymentPopup.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 6) {
                    JMTPaymentPopup.this.verifyPassword(charSequence.toString());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyPassword(String str) {
        TLRPC$JMT_verifyWalletPassword tLRPC$JMT_verifyWalletPassword = new TLRPC$JMT_verifyWalletPassword();
        tLRPC$JMT_verifyWalletPassword.password = str;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_verifyWalletPassword, new RequestDelegate() { // from class: org.telegram.ui.JMTPaymentPopup.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTPaymentPopup.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            if (tLObject instanceof TLRPC$TL_boolTrue) {
                                JMTPaymentPopup.this.onPaymentListener.onPayment();
                            } else {
                                Toast.makeText(JMTPaymentPopup.this.mContext, LocaleController.getString("JMTPaymentPasswordError", R.string.JMTPaymentPasswordError), 0).show();
                            }
                        }
                        JMTPaymentPopup.this.dismiss();
                    }
                });
            }
        });
    }
}
