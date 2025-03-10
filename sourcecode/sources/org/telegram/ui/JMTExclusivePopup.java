package org.telegram.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.lxj.xpopup.core.CenterPopupView;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_getMyLink;
import org.telegram.tgnet.TLRPC$JSONValue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_jsonObjectValue;
import org.telegram.tgnet.TLRPC$TL_jsonString;
import org.telegram.tgnet.TLRPC$Vector;

/* loaded from: classes3.dex */
public class JMTExclusivePopup extends CenterPopupView {
    private Context mContext;
    private OnSuccessListener onSuccessListener;

    public interface OnSuccessListener {
        void onSuccess(String str);
    }

    public JMTExclusivePopup(Context context, OnSuccessListener onSuccessListener) {
        super(context);
        this.mContext = context;
        this.onSuccessListener = onSuccessListener;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_exclusive_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ((ImageView) findViewById(R.id.jmt_exclusive_close)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTExclusivePopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTExclusivePopup.this.dismiss();
            }
        });
        ((TextView) findViewById(R.id.jmt_exclusive_title)).setText(LocaleController.getString("JMTExclusiveChannelHint", R.string.JMTExclusiveChannelHint));
        ((PinView) findViewById(R.id.jmt_exclusive_pv)).addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTExclusivePopup.2
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (charSequence.length() == 6) {
                    JMTExclusivePopup.this.verifyCode(charSequence.toString());
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyCode(String str) {
        TLRPC$JMT_getMyLink tLRPC$JMT_getMyLink = new TLRPC$JMT_getMyLink();
        tLRPC$JMT_getMyLink.code = str;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_getMyLink, new RequestDelegate() { // from class: org.telegram.ui.JMTExclusivePopup.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTExclusivePopup.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error != null) {
                            Toast.makeText(JMTExclusivePopup.this.mContext, LocaleController.getString("JMTExclusiveChannelError", R.string.JMTExclusiveChannelError), 0).show();
                        } else {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$TL_jsonObjectValue) {
                                        TLRPC$TL_jsonObjectValue tLRPC$TL_jsonObjectValue = (TLRPC$TL_jsonObjectValue) next;
                                        if (!tLRPC$TL_jsonObjectValue.key.equals("url")) {
                                            Toast.makeText(JMTExclusivePopup.this.mContext, LocaleController.getString("JMTExclusiveChannelError", R.string.JMTExclusiveChannelError), 0).show();
                                        } else {
                                            TLRPC$JSONValue tLRPC$JSONValue = tLRPC$TL_jsonObjectValue.value;
                                            if (!(tLRPC$JSONValue instanceof TLRPC$TL_jsonString)) {
                                                Toast.makeText(JMTExclusivePopup.this.mContext, LocaleController.getString("JMTExclusiveChannelError", R.string.JMTExclusiveChannelError), 0).show();
                                            } else {
                                                TLRPC$TL_jsonString tLRPC$TL_jsonString = (TLRPC$TL_jsonString) tLRPC$JSONValue;
                                                if (!TextUtils.isEmpty(tLRPC$TL_jsonString.value)) {
                                                    JMTExclusivePopup.this.onSuccessListener.onSuccess(tLRPC$TL_jsonString.value);
                                                } else {
                                                    Toast.makeText(JMTExclusivePopup.this.mContext, LocaleController.getString("JMTExclusiveChannelError", R.string.JMTExclusiveChannelError), 0).show();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        JMTExclusivePopup.this.dismiss();
                    }
                });
            }
        }, 10);
    }
}
