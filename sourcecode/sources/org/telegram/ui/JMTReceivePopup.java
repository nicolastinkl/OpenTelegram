package org.telegram.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lxj.xpopup.core.CenterPopupView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_RedPackage;
import org.telegram.tgnet.TLRPC$JMT_receiveRedPacketByRedPacketId;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

/* loaded from: classes3.dex */
public class JMTReceivePopup extends CenterPopupView {
    private BackupImageView bivAvatar;
    private boolean isReceiveRequesting;
    private ImageView ivClose;
    private ImageView ivOpen;
    private LinearLayout llLookRecord;
    private Context mContext;
    private JMTRedPacketListPopup mPopup;
    private TLRPC$JMT_RedPackage mRedPackage;
    private TextView tvName;
    private TextView tvTitle;

    public JMTReceivePopup(Context context, JMTRedPacketListPopup jMTRedPacketListPopup, TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage) {
        super(context);
        this.isReceiveRequesting = false;
        this.mContext = context;
        this.mPopup = jMTRedPacketListPopup;
        this.mRedPackage = tLRPC$JMT_RedPackage;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_receive_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        BackupImageView backupImageView = (BackupImageView) findViewById(R.id.biv_avatar);
        this.bivAvatar = backupImageView;
        backupImageView.setRoundRadius(AndroidUtilities.dp(10.0f));
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.ivOpen = (ImageView) findViewById(R.id.iv_open);
        this.llLookRecord = (LinearLayout) findViewById(R.id.ll_look_record);
        ((TextView) findViewById(R.id.tv_look_record)).setText(LocaleController.getString("JMTLookReceiveDetail", R.string.JMTLookReceiveDetail));
        this.ivClose = (ImageView) findViewById(R.id.iv_close);
        TLRPC$User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(this.mRedPackage.sender_id));
        if (user != null) {
            this.bivAvatar.setForUserOrChat(user, new AvatarDrawable(user));
            TextView textView = this.tvName;
            String str = user.first_name;
            if (str == null) {
                str = user.username;
            }
            textView.setText(str);
        }
        TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage = this.mRedPackage;
        if (tLRPC$JMT_RedPackage.red_package_type == 2) {
            TLRPC$User tLRPC$User = tLRPC$JMT_RedPackage.designated_user;
            String str2 = tLRPC$User.first_name;
            if (str2 == null) {
                str2 = tLRPC$User.username;
            }
            this.tvTitle.setText(LocaleController.getString("JMTGiveRedPackage", R.string.JMTGiveRedPackage) + str2 + LocaleController.getString("JMTOfExclusivePackage", R.string.JMTOfExclusivePackage));
        } else {
            this.tvTitle.setText(tLRPC$JMT_RedPackage.title);
        }
        this.isReceiveRequesting = false;
        this.ivOpen.setOnClickListener(new AnonymousClass1());
        this.llLookRecord.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTReceivePopup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTReceivePopup.this.mPopup.dismiss();
                JMTReceivePopup.this.dismiss();
                ((LaunchActivity) JMTReceivePopup.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTReceivePopup.this.mPopup.mDialogId, JMTReceivePopup.this.mRedPackage));
            }
        });
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTReceivePopup.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTReceivePopup.this.dismiss();
            }
        });
    }

    /* renamed from: org.telegram.ui.JMTReceivePopup$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (JMTReceivePopup.this.isReceiveRequesting) {
                return;
            }
            JMTReceivePopup.this.isReceiveRequesting = true;
            TLRPC$JMT_receiveRedPacketByRedPacketId tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$JMT_receiveRedPacketByRedPacketId();
            tLRPC$JMT_receiveRedPacketByRedPacketId.peer = MessagesController.getInstance(UserConfig.selectedAccount).getInputPeer(JMTReceivePopup.this.mPopup.mDialogId);
            tLRPC$JMT_receiveRedPacketByRedPacketId.red_packet_id = JMTReceivePopup.this.mRedPackage.id;
            ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_receiveRedPacketByRedPacketId, new RequestDelegate() { // from class: org.telegram.ui.JMTReceivePopup.1.1
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTReceivePopup.1.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            JMTReceivePopup.this.isReceiveRequesting = false;
                            TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                            if (tLRPC$TL_error2 == null) {
                                ChatActivity.loadHasRedPackage();
                                JMTReceivePopup.this.mPopup.refreshList();
                                JMTReceivePopup.this.mPopup.dismiss();
                                JMTReceivePopup.this.dismiss();
                                ((LaunchActivity) JMTReceivePopup.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTReceivePopup.this.mPopup.mDialogId, JMTReceivePopup.this.mRedPackage));
                                return;
                            }
                            if (tLRPC$TL_error2.text.equals("RED_PACKAGE_RECEIVED")) {
                                ChatActivity.loadHasRedPackage();
                                JMTReceivePopup.this.mPopup.refreshList();
                                JMTReceivePopup.this.mPopup.dismiss();
                                JMTReceivePopup.this.dismiss();
                                ((LaunchActivity) JMTReceivePopup.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTReceivePopup.this.mPopup.mDialogId, JMTReceivePopup.this.mRedPackage));
                                return;
                            }
                            if (tLRPC$TL_error.text.equals("RED_PACKAGE_RECEIVED_FINISHED")) {
                                Toast.makeText(JMTReceivePopup.this.mContext, LocaleController.getString("JMTReceiveReceiveFinished", R.string.JMTReceiveReceiveFinished), 0).show();
                                ChatActivity.loadHasRedPackage();
                                JMTReceivePopup.this.mPopup.refreshList();
                                JMTReceivePopup.this.mPopup.dismiss();
                                JMTReceivePopup.this.dismiss();
                                return;
                            }
                            Toast.makeText(JMTReceivePopup.this.mContext, LocaleController.getString("JMTReceiveErrorRetry", R.string.JMTReceiveErrorRetry), 0).show();
                            JMTReceivePopup.this.dismiss();
                        }
                    });
                }
            });
        }
    }
}
