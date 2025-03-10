package org.telegram.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import java.text.DecimalFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_RedPackage;
import org.telegram.tgnet.TLRPC$JMT_getWalletPassword;
import org.telegram.tgnet.TLRPC$JMT_inputMediaRedPackage;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_sendMedia;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatUsersActivity;
import org.telegram.ui.JMTPaymentPopup;

/* loaded from: classes3.dex */
public class JMTSendRedPacketActivity extends BaseFragment {
    private long chatId;
    private long dialogId;
    EditText etRedPacketContent;
    EditText etRedPacketLength;
    EditText etRedPacketMoney;
    private int groupLength;
    LinearLayout llRedPacketLength;
    LinearLayout llRedPacketType;
    LinearLayout llRedPacketWhole;
    LinearLayout llSendRedPacket;
    private Context mContext;
    int redPacketType = BuildVars.defaultRedPacketType;
    TextView tvGroupLength;
    TextView tvRedPacketMoney;
    TextView tvRedPacketShow;
    TextView tvRedPacketSymbol;
    TextView tvRedPacketType;
    TextView tvRedPacketWhole;
    TLRPC$User wholeUser;

    public JMTSendRedPacketActivity(long j) {
        this.dialogId = j;
    }

    public JMTSendRedPacketActivity(long j, long j2, int i) {
        this.dialogId = j;
        this.chatId = j2;
        int i2 = BuildVars.maxSendRedPacketCount;
        if (i > i2) {
            this.groupLength = i2;
        } else {
            this.groupLength = i;
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTSendRedPacket", R.string.JMTSendRedPacket));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTSendRedPacketActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTSendRedPacketActivity.this.finishFragment();
            }
        });
        View inflate = View.inflate(context, R.layout.activity_send_red_packet, null);
        this.fragmentView = inflate;
        this.llRedPacketType = (LinearLayout) inflate.findViewById(R.id.ll_red_packet_type);
        TextView textView = (TextView) this.fragmentView.findViewById(R.id.tv_red_packet_type);
        this.tvRedPacketType = textView;
        if (this.redPacketType == 1) {
            textView.setText(LocaleController.getString("JMTLuckyRedEnvelope", R.string.JMTLuckyRedEnvelope));
        } else {
            textView.setText(LocaleController.getString("JMTOrdinaryRedEnvelope", R.string.JMTOrdinaryRedEnvelope));
        }
        this.llRedPacketLength = (LinearLayout) this.fragmentView.findViewById(R.id.ll_red_packet_length);
        ((TextView) this.fragmentView.findViewById(R.id.tv_red_packet_length)).setText(LocaleController.getString("JMTRedPacketCountPoint", R.string.JMTRedPacketCountPoint));
        EditText editText = (EditText) this.fragmentView.findViewById(R.id.et_red_packet_length);
        this.etRedPacketLength = editText;
        editText.setText(LocaleController.getString("JMTInputPackageCount", R.string.JMTInputPackageCount));
        ((TextView) this.fragmentView.findViewById(R.id.tv_red_packet_length_unit)).setText(LocaleController.getString("JMTIndivual", R.string.JMTIndivual));
        this.llRedPacketWhole = (LinearLayout) this.fragmentView.findViewById(R.id.ll_red_packet_whole);
        ((TextView) this.fragmentView.findViewById(R.id.tv_red_packet_whole_title)).setText(LocaleController.getString("JMTSendWho", R.string.JMTSendWho));
        this.tvRedPacketWhole = (TextView) this.fragmentView.findViewById(R.id.tv_red_packet_whole);
        TextView textView2 = (TextView) this.fragmentView.findViewById(R.id.tv_group_length);
        this.tvGroupLength = textView2;
        int i = Theme.key_dialogTextBlack;
        textView2.setTextColor(Theme.getColor(i));
        this.tvRedPacketMoney = (TextView) this.fragmentView.findViewById(R.id.tv_red_packet_money);
        this.etRedPacketMoney = (EditText) this.fragmentView.findViewById(R.id.et_red_packet_money);
        this.etRedPacketMoney.setFilters(new InputFilter[]{new CashierInputFilter()});
        this.etRedPacketMoney.setHint(LocaleController.getString("JMTInputMoney", R.string.JMTInputMoney));
        EditText editText2 = (EditText) this.fragmentView.findViewById(R.id.et_red_packet_content);
        this.etRedPacketContent = editText2;
        editText2.setHint(LocaleController.getString("JMTGoodLuck", R.string.JMTGoodLuck));
        TextView textView3 = (TextView) this.fragmentView.findViewById(R.id.tv_red_packet_symbol);
        this.tvRedPacketSymbol = textView3;
        textView3.setTextColor(Theme.getColor(i));
        TextView textView4 = (TextView) this.fragmentView.findViewById(R.id.tv_red_packet_show);
        this.tvRedPacketShow = textView4;
        textView4.setTextColor(Theme.getColor(i));
        this.llSendRedPacket = (LinearLayout) this.fragmentView.findViewById(R.id.ll_send_red_packet);
        ((TextView) this.fragmentView.findViewById(R.id.tv_send_red_packet)).setText(LocaleController.getString("JMTStuffingMoney", R.string.JMTStuffingMoney));
        if (this.chatId != 0) {
            this.llRedPacketType.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTSendRedPacketActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    new XPopup.Builder(JMTSendRedPacketActivity.this.mContext).asCenterList(null, new String[]{LocaleController.getString("JMTOrdinaryRedEnvelope", R.string.JMTOrdinaryRedEnvelope), LocaleController.getString("JMTLuckyRedEnvelope", R.string.JMTLuckyRedEnvelope), LocaleController.getString("JMTExclusiveRedEnvelope", R.string.JMTExclusiveRedEnvelope)}, new OnSelectListener() { // from class: org.telegram.ui.JMTSendRedPacketActivity.2.1
                        @Override // com.lxj.xpopup.interfaces.OnSelectListener
                        public void onSelect(int i2, String str) {
                            JMTSendRedPacketActivity jMTSendRedPacketActivity = JMTSendRedPacketActivity.this;
                            jMTSendRedPacketActivity.redPacketType = i2;
                            jMTSendRedPacketActivity.tvRedPacketType.setText(str);
                            JMTSendRedPacketActivity.this.switchRedPacketType();
                        }
                    }).show();
                }
            });
            this.llRedPacketType.setVisibility(0);
            if (this.groupLength != 0) {
                this.tvGroupLength.setText(LocaleController.getString("JMTGroupHasTotalOf", R.string.JMTGroupHasTotalOf) + this.groupLength + LocaleController.getString("JMTPeople", R.string.JMTPeople));
                this.tvGroupLength.setVisibility(0);
            }
            this.redPacketType = BuildVars.defaultRedPacketType;
        } else {
            this.redPacketType = 3;
        }
        switchRedPacketType();
        this.llRedPacketWhole.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTSendRedPacketActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", JMTSendRedPacketActivity.this.chatId);
                bundle.putInt("type", 4);
                bundle.putBoolean("open_search", true);
                ChatUsersActivity chatUsersActivity = new ChatUsersActivity(bundle);
                chatUsersActivity.setInfo(JMTSendRedPacketActivity.this.getMessagesController().getChatFull(JMTSendRedPacketActivity.this.chatId));
                chatUsersActivity.setJMTSelectUserDelegate(new ChatUsersActivity.JMTSelectUserDelegate() { // from class: org.telegram.ui.JMTSendRedPacketActivity.3.1
                    @Override // org.telegram.ui.ChatUsersActivity.JMTSelectUserDelegate
                    public void didSelectUser(TLRPC$User tLRPC$User) {
                        JMTSendRedPacketActivity jMTSendRedPacketActivity = JMTSendRedPacketActivity.this;
                        jMTSendRedPacketActivity.wholeUser = tLRPC$User;
                        jMTSendRedPacketActivity.tvRedPacketWhole.setText(tLRPC$User.first_name);
                    }
                });
                JMTSendRedPacketActivity.this.presentFragment(chatUsersActivity);
            }
        });
        this.etRedPacketLength.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTSendRedPacketActivity.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (JMTSendRedPacketActivity.this.redPacketType != 0) {
                    return;
                }
                if (charSequence.toString().isEmpty()) {
                    JMTSendRedPacketActivity.this.tvRedPacketShow.setText("0.00");
                    return;
                }
                if (JMTSendRedPacketActivity.this.etRedPacketMoney.getText().toString().isEmpty()) {
                    JMTSendRedPacketActivity.this.tvRedPacketShow.setText("0.00");
                    return;
                }
                JMTSendRedPacketActivity.this.tvRedPacketShow.setText(new DecimalFormat("0.00").format(Integer.parseInt(charSequence.toString()) * Double.parseDouble(JMTSendRedPacketActivity.this.etRedPacketMoney.getText().toString())));
            }
        });
        this.etRedPacketMoney.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.JMTSendRedPacketActivity.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                if (charSequence.toString().isEmpty()) {
                    JMTSendRedPacketActivity.this.tvRedPacketShow.setText("0.00");
                    return;
                }
                JMTSendRedPacketActivity jMTSendRedPacketActivity = JMTSendRedPacketActivity.this;
                if (jMTSendRedPacketActivity.redPacketType != 0) {
                    jMTSendRedPacketActivity.tvRedPacketShow.setText(charSequence.toString());
                    return;
                }
                if (jMTSendRedPacketActivity.etRedPacketLength.getText().toString().isEmpty()) {
                    JMTSendRedPacketActivity.this.tvRedPacketShow.setText("0.00");
                    return;
                }
                JMTSendRedPacketActivity.this.tvRedPacketShow.setText(new DecimalFormat("0.00").format(Integer.parseInt(JMTSendRedPacketActivity.this.etRedPacketLength.getText().toString()) * Double.parseDouble(charSequence.toString())));
            }
        });
        this.llSendRedPacket.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTSendRedPacketActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTSendRedPacketActivity.this.submit();
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchRedPacketType() {
        int i = this.redPacketType;
        if (i == 0 || i == 3) {
            if (this.chatId != 0) {
                this.llRedPacketLength.setVisibility(0);
                this.etRedPacketLength.setText("");
            } else {
                this.etRedPacketLength.setText("1");
            }
            this.llRedPacketWhole.setVisibility(8);
            this.tvRedPacketWhole.setText("");
            this.wholeUser = null;
            this.tvRedPacketMoney.setText(LocaleController.getString("JMTSingleAmount", R.string.JMTSingleAmount));
            this.etRedPacketMoney.setText("");
            return;
        }
        if (i == 1) {
            if (this.chatId != 0) {
                this.llRedPacketLength.setVisibility(0);
            }
            this.llRedPacketWhole.setVisibility(8);
            this.etRedPacketLength.setText("");
            this.tvRedPacketWhole.setText("");
            this.wholeUser = null;
            this.tvRedPacketMoney.setText(LocaleController.getString("JMTTotalAmount", R.string.JMTTotalAmount));
            this.etRedPacketMoney.setText("");
            return;
        }
        if (i == 2) {
            this.llRedPacketLength.setVisibility(8);
            this.llRedPacketWhole.setVisibility(0);
            this.etRedPacketLength.setText("");
            this.tvRedPacketWhole.setText("");
            this.wholeUser = null;
            this.tvRedPacketMoney.setText(LocaleController.getString("JMTShowMoney", R.string.JMTShowMoney));
            this.etRedPacketMoney.setText("");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submit() {
        if (this.redPacketType == 2) {
            if (this.wholeUser == null) {
                Toast.makeText(this.mContext, LocaleController.getString("JMTInputExclusiveHint", R.string.JMTInputExclusiveHint), 0).show();
                return;
            }
        } else {
            if (this.etRedPacketLength.getText().toString().isEmpty()) {
                Toast.makeText(this.mContext, LocaleController.getString("JMTInputPackageCount", R.string.JMTInputPackageCount), 0).show();
                return;
            }
            if (this.etRedPacketLength.getText().toString().startsWith("0")) {
                Toast.makeText(this.mContext, LocaleController.getString("JMTInputPackageCountError", R.string.JMTInputPackageCountError), 0).show();
                return;
            }
            if (this.chatId != 0 && Integer.parseInt(this.etRedPacketLength.getText().toString()) > this.groupLength) {
                Toast.makeText(this.mContext, LocaleController.getString("JMTMaxRedPacketCount", R.string.JMTMaxRedPacketCount) + this.groupLength, 0).show();
                return;
            }
        }
        if (this.etRedPacketMoney.getText().toString().isEmpty()) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTINputPackageMoney", R.string.JMTINputPackageMoney), 0).show();
            return;
        }
        if (this.etRedPacketMoney.getText().toString().isEmpty()) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTINputPackageMoneyError", R.string.JMTINputPackageMoneyError), 0).show();
            return;
        }
        double parseDouble = Double.parseDouble(this.etRedPacketMoney.getText().toString());
        double parseDouble2 = Double.parseDouble(this.tvRedPacketShow.getText().toString());
        if (parseDouble2 < 1.0d) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTMinPacketMoney", R.string.JMTMinPacketMoney), 0).show();
        } else if (parseDouble2 > 100000.0d) {
            Toast.makeText(this.mContext, LocaleController.getString("JMTMaxPacketMoney", R.string.JMTMaxPacketMoney), 0).show();
        } else {
            getConnectionsManager().sendRequest(new TLRPC$JMT_getWalletPassword(), new AnonymousClass7(parseDouble2, parseDouble));
        }
    }

    /* renamed from: org.telegram.ui.JMTSendRedPacketActivity$7, reason: invalid class name */
    class AnonymousClass7 implements RequestDelegate {
        final /* synthetic */ double val$single_amount;
        final /* synthetic */ double val$total_amount;

        AnonymousClass7(double d, double d2) {
            this.val$total_amount = d;
            this.val$single_amount = d2;
        }

        @Override // org.telegram.tgnet.RequestDelegate
        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTSendRedPacketActivity.7.1
                @Override // java.lang.Runnable
                public void run() {
                    if (tLRPC$TL_error != null) {
                        Toast.makeText(JMTSendRedPacketActivity.this.mContext, LocaleController.getString("JMTRequestError", R.string.JMTRequestError), 0).show();
                        return;
                    }
                    if (!(tLObject instanceof TLRPC$TL_boolTrue)) {
                        Toast.makeText(JMTSendRedPacketActivity.this.mContext, LocaleController.getString("JMTPleaseSetPassword", R.string.JMTPleaseSetPassword), 0).show();
                        JMTSendRedPacketActivity.this.presentFragment(new JMTSetPayPwdActivity());
                        return;
                    }
                    new XPopup.Builder(JMTSendRedPacketActivity.this.mContext).autoOpenSoftInput(Boolean.TRUE).asCustom(new JMTPaymentPopup(JMTSendRedPacketActivity.this.mContext, AnonymousClass7.this.val$total_amount + "", new JMTPaymentPopup.OnPaymentListener() { // from class: org.telegram.ui.JMTSendRedPacketActivity.7.1.1
                        @Override // org.telegram.ui.JMTPaymentPopup.OnPaymentListener
                        public void onPayment() {
                            AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                            JMTSendRedPacketActivity.this.sendRedPacket(anonymousClass7.val$single_amount, anonymousClass7.val$total_amount);
                        }
                    })).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRedPacket(double d, double d2) {
        TLRPC$TL_messages_sendMedia tLRPC$TL_messages_sendMedia = new TLRPC$TL_messages_sendMedia();
        TLRPC$JMT_inputMediaRedPackage tLRPC$JMT_inputMediaRedPackage = new TLRPC$JMT_inputMediaRedPackage();
        TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage = new TLRPC$JMT_RedPackage();
        tLRPC$JMT_RedPackage.sender_id = getUserConfig().getClientUserId();
        String trim = this.etRedPacketContent.getText().toString().trim();
        if (trim.equals("")) {
            tLRPC$JMT_RedPackage.title = LocaleController.getString("JMTGoodLuck", R.string.JMTGoodLuck);
        } else {
            tLRPC$JMT_RedPackage.title = trim;
        }
        int i = this.redPacketType;
        tLRPC$JMT_RedPackage.red_package_type = i;
        if (i == 2) {
            TLRPC$TL_userEmpty tLRPC$TL_userEmpty = new TLRPC$TL_userEmpty();
            tLRPC$TL_userEmpty.id = this.wholeUser.id;
            tLRPC$JMT_RedPackage.designated_user = tLRPC$TL_userEmpty;
            tLRPC$JMT_RedPackage.count = 1;
        } else {
            tLRPC$JMT_RedPackage.count = Integer.parseInt(this.etRedPacketLength.getText().toString());
        }
        tLRPC$JMT_RedPackage.single_amount = (int) Math.round(d * 100.0d);
        tLRPC$JMT_RedPackage.total_amount = (int) Math.round(d2 * 100.0d);
        tLRPC$JMT_RedPackage.cover = new TLRPC$TL_photoEmpty();
        tLRPC$JMT_inputMediaRedPackage.red_package = tLRPC$JMT_RedPackage;
        tLRPC$TL_messages_sendMedia.media = tLRPC$JMT_inputMediaRedPackage;
        tLRPC$TL_messages_sendMedia.peer = getMessagesController().getInputPeer(this.dialogId);
        tLRPC$TL_messages_sendMedia.random_id = Utilities.random.nextLong();
        getConnectionsManager().sendRequest(tLRPC$TL_messages_sendMedia, new RequestDelegate() { // from class: org.telegram.ui.JMTSendRedPacketActivity.8
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTSendRedPacketActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            JMTSendRedPacketActivity.this.getMessagesController().processUpdates((TLRPC$Updates) tLObject, false);
                            Toast.makeText(JMTSendRedPacketActivity.this.mContext, LocaleController.getString("JMTSendSuccess", R.string.JMTSendSuccess), 0).show();
                            JMTSendRedPacketActivity.this.finishFragment();
                        } else {
                            String str = tLRPC$TL_error2.text;
                            if (str == null || !str.equals("SEND_RED_PACKET_FORBIDDEN")) {
                                Toast.makeText(JMTSendRedPacketActivity.this.mContext, LocaleController.getString("JMTSendErrorRetry", R.string.JMTSendErrorRetry), 0).show();
                            } else {
                                Toast.makeText(JMTSendRedPacketActivity.this.mContext, LocaleController.getString("JMTSendRedPackageReject", R.string.JMTSendRedPackageReject), 0).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
