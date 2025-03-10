package org.telegram.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_getWalletPassword;
import org.telegram.tgnet.TLRPC$JMT_setWalletPassword;
import org.telegram.tgnet.TLRPC$JMT_verifyWalletPassword;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

/* loaded from: classes3.dex */
public class JMTSetPayPwdActivity extends BaseFragment {
    private Context mContext;
    private PinView pvConfirmPwd;
    private PinView pvOriginalPwd;
    private PinView pvSettingPwd;
    private TextView tvConfirmTitle;
    private TextView tvOriginalTitle;
    private TextView tvSettingTitle;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTPayPassword", R.string.JMTPayPassword));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTSetPayPwdActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTSetPayPwdActivity.this.finishFragment();
            }
        });
        this.actionBar.createMenu().addItem(0, LocaleController.getString("JMTSave", R.string.JMTSave)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTSetPayPwdActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (JMTSetPayPwdActivity.this.pvOriginalPwd.getVisibility() == 0) {
                    if (!JMTSetPayPwdActivity.this.pvOriginalPwd.getText().toString().isEmpty()) {
                        if (JMTSetPayPwdActivity.this.pvOriginalPwd.getText().toString().length() < 6) {
                            Toast.makeText(context, LocaleController.getString("JMTPayInputWholeOriginalPassword", R.string.JMTPayInputWholeOriginalPassword), 0).show();
                            return;
                        }
                    } else {
                        Toast.makeText(context, LocaleController.getString("JMTPayInputOriginalPassword", R.string.JMTPayInputOriginalPassword), 0).show();
                        return;
                    }
                }
                if (!JMTSetPayPwdActivity.this.pvSettingPwd.getText().toString().isEmpty()) {
                    if (JMTSetPayPwdActivity.this.pvSettingPwd.getText().toString().length() >= 6) {
                        if (!JMTSetPayPwdActivity.this.pvConfirmPwd.getText().toString().isEmpty()) {
                            if (JMTSetPayPwdActivity.this.pvConfirmPwd.getText().toString().length() >= 6) {
                                if (JMTSetPayPwdActivity.this.pvOriginalPwd.getVisibility() == 0) {
                                    JMTSetPayPwdActivity jMTSetPayPwdActivity = JMTSetPayPwdActivity.this;
                                    jMTSetPayPwdActivity.verifyPassword(jMTSetPayPwdActivity.pvOriginalPwd.getText().toString());
                                    return;
                                } else {
                                    JMTSetPayPwdActivity jMTSetPayPwdActivity2 = JMTSetPayPwdActivity.this;
                                    jMTSetPayPwdActivity2.savePassword(jMTSetPayPwdActivity2.pvConfirmPwd.getText().toString());
                                    return;
                                }
                            }
                            Toast.makeText(context, LocaleController.getString("JMTPayInputWholeConfirmPassword", R.string.JMTPayInputWholeConfirmPassword), 0).show();
                            return;
                        }
                        Toast.makeText(context, LocaleController.getString("JMTPayInputConfirmPassword", R.string.JMTPayInputConfirmPassword), 0).show();
                        return;
                    }
                    Toast.makeText(context, LocaleController.getString("JMTPayInputWholeNewPassword", R.string.JMTPayInputWholeNewPassword), 0).show();
                    return;
                }
                Toast.makeText(context, LocaleController.getString("JMTPayInputNewPassword", R.string.JMTPayInputNewPassword), 0).show();
            }
        });
        this.fragmentView = View.inflate(context, R.layout.activity_set_pay_pwd, null);
        int screenWidth = ((JMTDiscoverWebActivity.getScreenWidth(context) - 30) / 6) - 7;
        TextView textView = (TextView) this.fragmentView.findViewById(R.id.tv_original_title);
        this.tvOriginalTitle = textView;
        int i = Theme.key_dialogTextBlack;
        textView.setTextColor(Theme.getColor(i));
        this.tvOriginalTitle.setText(LocaleController.getString("JMTPayOriginalPassword", R.string.JMTPayOriginalPassword));
        PinView pinView = (PinView) this.fragmentView.findViewById(R.id.pv_original_pwd);
        this.pvOriginalPwd = pinView;
        pinView.setItemWidth(screenWidth);
        this.pvOriginalPwd.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTSetPayPwdActivity.3
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (charSequence.length() == 6) {
                    JMTSetPayPwdActivity.this.pvSettingPwd.requestFocus();
                }
            }
        });
        TextView textView2 = (TextView) this.fragmentView.findViewById(R.id.tv_setting_title);
        this.tvSettingTitle = textView2;
        textView2.setTextColor(Theme.getColor(i));
        this.tvSettingTitle.setText(LocaleController.getString("JMTPaySettingPassword", R.string.JMTPaySettingPassword));
        PinView pinView2 = (PinView) this.fragmentView.findViewById(R.id.pv_setting_pwd);
        this.pvSettingPwd = pinView2;
        pinView2.setItemWidth(screenWidth);
        this.pvSettingPwd.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTSetPayPwdActivity.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (charSequence.length() == 6) {
                    JMTSetPayPwdActivity.this.pvConfirmPwd.requestFocus();
                }
            }
        });
        TextView textView3 = (TextView) this.fragmentView.findViewById(R.id.tv_confirm_title);
        this.tvConfirmTitle = textView3;
        textView3.setTextColor(Theme.getColor(i));
        this.tvConfirmTitle.setText(LocaleController.getString("JMTPayConfirmPassword", R.string.JMTPayConfirmPassword));
        PinView pinView3 = (PinView) this.fragmentView.findViewById(R.id.pv_confirm_pwd);
        this.pvConfirmPwd = pinView3;
        pinView3.setItemWidth(screenWidth);
        getConnectionsManager().sendRequest(new TLRPC$JMT_getWalletPassword(), new RequestDelegate() { // from class: org.telegram.ui.JMTSetPayPwdActivity.5
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTSetPayPwdActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
                            Toast.makeText(context, LocaleController.getString("JMTPayContactService", R.string.JMTPayContactService), 0).show();
                            JMTSetPayPwdActivity.this.finishFragment();
                        }
                    }
                });
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyPassword(String str) {
        TLRPC$JMT_verifyWalletPassword tLRPC$JMT_verifyWalletPassword = new TLRPC$JMT_verifyWalletPassword();
        tLRPC$JMT_verifyWalletPassword.password = str;
        getConnectionsManager().sendRequest(tLRPC$JMT_verifyWalletPassword, new RequestDelegate() { // from class: org.telegram.ui.JMTSetPayPwdActivity.6
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTSetPayPwdActivity.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            if (!(tLObject instanceof TLRPC$TL_boolTrue)) {
                                Toast.makeText(JMTSetPayPwdActivity.this.mContext, LocaleController.getString("JMTPayOriginalError", R.string.JMTPayOriginalError), 0).show();
                            } else {
                                JMTSetPayPwdActivity jMTSetPayPwdActivity = JMTSetPayPwdActivity.this;
                                jMTSetPayPwdActivity.savePassword(jMTSetPayPwdActivity.pvConfirmPwd.getText().toString());
                            }
                        }
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void savePassword(String str) {
        TLRPC$JMT_setWalletPassword tLRPC$JMT_setWalletPassword = new TLRPC$JMT_setWalletPassword();
        tLRPC$JMT_setWalletPassword.password = str;
        getConnectionsManager().sendRequest(tLRPC$JMT_setWalletPassword, new RequestDelegate() { // from class: org.telegram.ui.JMTSetPayPwdActivity.7
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTSetPayPwdActivity.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            if (tLObject instanceof TLRPC$TL_boolTrue) {
                                Toast.makeText(JMTSetPayPwdActivity.this.mContext, LocaleController.getString("JMTPaySettingSuccess", R.string.JMTPaySettingSuccess), 0).show();
                                JMTSetPayPwdActivity.this.finishFragment();
                            } else {
                                Toast.makeText(JMTSetPayPwdActivity.this.mContext, LocaleController.getString("JMTPaySettingError", R.string.JMTPaySettingError), 0).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
