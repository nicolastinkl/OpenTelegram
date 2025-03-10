package org.telegram.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_bindPhone;
import org.telegram.tgnet.TLRPC$JMT_isBoundPhone;
import org.telegram.tgnet.TLRPC$JMT_jAuthIsBoundPhone;
import org.telegram.tgnet.TLRPC$JMT_sendSmsCode;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTBindPhoneActivity extends BaseFragment {
    private Button btBind;
    private Button btGet;
    private CountDownTimer countDown;
    private EditText etCode;
    private EditText etPhone;
    private Context mContext;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        return false;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        return false;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setTitle(LocaleController.getString("JMTBindPhone", R.string.JMTBindPhone));
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        View inflate = View.inflate(context, R.layout.activity_bind_phone, null);
        EditText editText = (EditText) inflate.findViewById(R.id.et_phone);
        this.etPhone = editText;
        editText.setHint(LocaleController.getString("JMTPhoneEmpty", R.string.JMTPhoneEmpty));
        EditText editText2 = (EditText) inflate.findViewById(R.id.et_code);
        this.etCode = editText2;
        editText2.setHint(LocaleController.getString("JMTCodeEmpty", R.string.JMTCodeEmpty));
        Button button = (Button) inflate.findViewById(R.id.bt_get);
        this.btGet = button;
        button.setText(LocaleController.getString("JMTGetCode", R.string.JMTGetCode));
        Button button2 = (Button) inflate.findViewById(R.id.bt_bind);
        this.btBind = button2;
        button2.setText(LocaleController.getString("JMTBind", R.string.JMTBind));
        frameLayout.addView(inflate, LayoutHelper.createFrame(-1, -1.0f));
        this.btGet.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTBindPhoneActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTBindPhoneActivity.this.getVerifyCode();
            }
        });
        this.btBind.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTBindPhoneActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTBindPhoneActivity.this.bindPhone();
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getVerifyCode() {
        String trim = this.etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTPhoneEmpty", R.string.JMTPhoneEmpty), 0).show();
            return;
        }
        if (!trim.matches("1[0-9]{10}")) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTPhoneError", R.string.JMTPhoneError), 0).show();
            return;
        }
        TLRPC$JMT_sendSmsCode tLRPC$JMT_sendSmsCode = new TLRPC$JMT_sendSmsCode();
        tLRPC$JMT_sendSmsCode.phone = trim;
        tLRPC$JMT_sendSmsCode.type = "bindPhone";
        tLRPC$JMT_sendSmsCode.ext = "";
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$JMT_sendSmsCode, new AnonymousClass3());
    }

    /* renamed from: org.telegram.ui.JMTBindPhoneActivity$3, reason: invalid class name */
    class AnonymousClass3 implements RequestDelegate {
        AnonymousClass3() {
        }

        @Override // org.telegram.tgnet.RequestDelegate
        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTBindPhoneActivity.3.1
                @Override // java.lang.Runnable
                public void run() {
                    TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                    if (tLRPC$TL_error2 == null) {
                        if (tLObject instanceof TLRPC$TL_boolTrue) {
                            Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTSendCodeSuccess", R.string.JMTSendCodeSuccess), 0).show();
                            JMTBindPhoneActivity.this.btGet.setEnabled(false);
                            JMTBindPhoneActivity.this.countDown = new CountDownTimer(60000L, 1000L) { // from class: org.telegram.ui.JMTBindPhoneActivity.3.1.1
                                @Override // android.os.CountDownTimer
                                public void onTick(long j) {
                                    JMTBindPhoneActivity.this.btGet.setText((j / 1000) + "s");
                                }

                                @Override // android.os.CountDownTimer
                                public void onFinish() {
                                    JMTBindPhoneActivity.this.btGet.setEnabled(true);
                                    JMTBindPhoneActivity.this.btGet.setText(LocaleController.getString("JMTGetCode", R.string.JMTGetCode));
                                }
                            }.start();
                            return;
                        }
                        Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTSendCodeError", R.string.JMTSendCodeError), 0).show();
                        return;
                    }
                    String str = tLRPC$TL_error2.text;
                    if (str == null) {
                        Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTSendCodeError", R.string.JMTSendCodeError), 0).show();
                    } else if (str.equals("TOO_MANY_REQUESTS")) {
                        Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTSendCodeFrequently", R.string.JMTSendCodeFrequently), 0).show();
                    } else {
                        Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTSendCodeError", R.string.JMTSendCodeError), 0).show();
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPhone() {
        final String trim = this.etPhone.getText().toString().trim();
        final String trim2 = this.etCode.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTPhoneEmpty", R.string.JMTPhoneEmpty), 0).show();
            return;
        }
        if (!trim.matches("1[0-9]{10}")) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTPhoneError", R.string.JMTPhoneError), 0).show();
        } else {
            if (TextUtils.isEmpty(trim2)) {
                Toast.makeText(this.mContext, LocaleController.getString("JMTCodeEmpty", R.string.JMTCodeEmpty), 0).show();
                return;
            }
            TLRPC$JMT_isBoundPhone tLRPC$JMT_isBoundPhone = new TLRPC$JMT_isBoundPhone();
            tLRPC$JMT_isBoundPhone.ext = "";
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$JMT_isBoundPhone, new RequestDelegate() { // from class: org.telegram.ui.JMTBindPhoneActivity.4
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTBindPhoneActivity.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TLObject tLObject2 = tLObject;
                                if (tLObject2 instanceof TLRPC$JMT_jAuthIsBoundPhone) {
                                    TLRPC$JMT_jAuthIsBoundPhone tLRPC$JMT_jAuthIsBoundPhone = (TLRPC$JMT_jAuthIsBoundPhone) tLObject2;
                                    if (tLRPC$JMT_jAuthIsBoundPhone.require_level == 0) {
                                        JMTBindPhoneActivity.this.finishFragment();
                                    } else if (tLRPC$JMT_jAuthIsBoundPhone.bound == 1) {
                                        JMTBindPhoneActivity.this.finishFragment();
                                    } else {
                                        AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                        JMTBindPhoneActivity.this.bindPhone(trim, trim2);
                                    }
                                }
                            }
                        }
                    });
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPhone(String str, String str2) {
        TLRPC$JMT_bindPhone tLRPC$JMT_bindPhone = new TLRPC$JMT_bindPhone();
        tLRPC$JMT_bindPhone.phone = str;
        tLRPC$JMT_bindPhone.code = str2;
        tLRPC$JMT_bindPhone.ext = "";
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$JMT_bindPhone, new RequestDelegate() { // from class: org.telegram.ui.JMTBindPhoneActivity.5
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTBindPhoneActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            if (tLObject instanceof TLRPC$TL_boolTrue) {
                                Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTBindSuccess", R.string.JMTBindSuccess), 0).show();
                                JMTBindPhoneActivity.this.finishFragment();
                                return;
                            } else {
                                Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTBindError", R.string.JMTBindError), 0).show();
                                return;
                            }
                        }
                        String str3 = tLRPC$TL_error2.text;
                        if (str3 == null) {
                            Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTBindError", R.string.JMTBindError), 0).show();
                            return;
                        }
                        if (str3.equals("SMS_CODE_WRONG")) {
                            Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTCodeError", R.string.JMTCodeError), 0).show();
                        } else if (tLRPC$TL_error.text.equals("PHONE_ALREADY_BOUND")) {
                            Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTPhoneOccupied", R.string.JMTPhoneOccupied), 0).show();
                        } else {
                            Toast.makeText(JMTBindPhoneActivity.this.mContext, LocaleController.getString("JMTBindError", R.string.JMTBindError), 0).show();
                        }
                    }
                });
            }
        });
    }
}
