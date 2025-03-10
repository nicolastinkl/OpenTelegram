package org.telegram.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_RedPackage;
import org.telegram.tgnet.TLRPC$JMT_getRedPackageResultsByRedPacketId;
import org.telegram.tgnet.TLRPC$JMT_redPackageReceiver;
import org.telegram.tgnet.TLRPC$JMT_updateMessageRedPackage;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;

/* loaded from: classes3.dex */
public class JMTReceiveDetailActivity extends BaseFragment {
    private BackupImageView bivSenderAvatar;
    private LinearLayout llDetailList;
    private LinearLayout llGotoWallet;
    private LinearLayout llReceiveMoney;
    private ReceiveDetailAdapter mAdapter;
    private Context mContext;
    private long mDialogId;
    private List<TLRPC$JMT_redPackageReceiver> mList;
    private TLRPC$JMT_RedPackage mRedPackage;
    private RecyclerView rvDetailList;
    private TextView tvDetailTitle;
    private TextView tvReceiveMoney;
    private TextView tvSendTitle;
    private TextView tvSenderName;

    public JMTReceiveDetailActivity(long j, TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage) {
        this.mDialogId = j;
        this.mRedPackage = tLRPC$JMT_RedPackage;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackgroundColor(Color.parseColor("#F25745"));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTReceiveDetailActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTReceiveDetailActivity.this.finishFragment();
            }
        });
        View inflate = View.inflate(context, R.layout.activity_receive_detail, null);
        this.fragmentView = inflate;
        BackupImageView backupImageView = (BackupImageView) inflate.findViewById(R.id.biv_sender_avatar);
        this.bivSenderAvatar = backupImageView;
        backupImageView.setRoundRadius(AndroidUtilities.dp(12.0f));
        this.tvSenderName = (TextView) this.fragmentView.findViewById(R.id.tv_sender_name);
        this.tvSendTitle = (TextView) this.fragmentView.findViewById(R.id.tv_send_title);
        this.llReceiveMoney = (LinearLayout) this.fragmentView.findViewById(R.id.ll_receive_money);
        this.tvReceiveMoney = (TextView) this.fragmentView.findViewById(R.id.tv_receive_money);
        ((TextView) this.fragmentView.findViewById(R.id.tv_receive_money_unit)).setText(LocaleController.getString("JMTRedPackageYuan", R.string.JMTRedPackageYuan));
        this.llGotoWallet = (LinearLayout) this.fragmentView.findViewById(R.id.ll_goto_wallet);
        ((TextView) this.fragmentView.findViewById(R.id.tv_goto_wallet)).setText(LocaleController.getString("JMTLookBalance", R.string.JMTLookBalance));
        this.llDetailList = (LinearLayout) this.fragmentView.findViewById(R.id.ll_detail_list);
        this.tvDetailTitle = (TextView) this.fragmentView.findViewById(R.id.tv_detail_title);
        this.rvDetailList = (RecyclerView) this.fragmentView.findViewById(R.id.rv_detail_list);
        this.llGotoWallet.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTReceiveDetailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTReceiveDetailActivity.this.presentFragment(new JMTWalletActivity());
            }
        });
        this.rvDetailList.setLayoutManager(new LinearLayoutManager(context));
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        ReceiveDetailAdapter receiveDetailAdapter = new ReceiveDetailAdapter(this, context, arrayList);
        this.mAdapter = receiveDetailAdapter;
        this.rvDetailList.setAdapter(receiveDetailAdapter);
        loadDetail();
        return this.fragmentView;
    }

    private void loadDetail() {
        TLRPC$JMT_getRedPackageResultsByRedPacketId tLRPC$JMT_getRedPackageResultsByRedPacketId = new TLRPC$JMT_getRedPackageResultsByRedPacketId();
        tLRPC$JMT_getRedPackageResultsByRedPacketId.peer = MessagesController.getInstance(UserConfig.selectedAccount).getInputPeer(this.mDialogId);
        tLRPC$JMT_getRedPackageResultsByRedPacketId.red_packet_id = this.mRedPackage.id;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_getRedPackageResultsByRedPacketId, new RequestDelegate() { // from class: org.telegram.ui.JMTReceiveDetailActivity.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTReceiveDetailActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error != null) {
                            Toast.makeText(JMTReceiveDetailActivity.this.mContext, LocaleController.getString("JMTGetErrorRetry", R.string.JMTGetErrorRetry), 0).show();
                            return;
                        }
                        TLObject tLObject2 = tLObject;
                        if (tLObject2 instanceof TLRPC$Updates) {
                            ArrayList<TLRPC$Update> arrayList = ((TLRPC$Updates) tLObject2).updates;
                            if (arrayList.size() == 0) {
                                return;
                            }
                            TLRPC$JMT_updateMessageRedPackage tLRPC$JMT_updateMessageRedPackage = (TLRPC$JMT_updateMessageRedPackage) arrayList.get(0);
                            TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage = tLRPC$JMT_updateMessageRedPackage.red_package;
                            TLRPC$User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(tLRPC$JMT_RedPackage.sender_id));
                            if (user != null) {
                                JMTReceiveDetailActivity.this.bivSenderAvatar.setForUserOrChat(user, new AvatarDrawable(user));
                                TextView textView = JMTReceiveDetailActivity.this.tvSenderName;
                                StringBuilder sb = new StringBuilder();
                                String str = user.first_name;
                                if (str == null) {
                                    str = user.username;
                                }
                                sb.append(str);
                                sb.append(LocaleController.getString("JMTSendRedPackage", R.string.JMTSendRedPackage));
                                textView.setText(sb.toString());
                            }
                            JMTReceiveDetailActivity.this.tvSendTitle.setText(tLRPC$JMT_RedPackage.title);
                            ArrayList<TLRPC$JMT_redPackageReceiver> arrayList2 = tLRPC$JMT_updateMessageRedPackage.red_package_receiver;
                            if (arrayList2.size() == 0) {
                                return;
                            }
                            int i = 0;
                            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                                TLRPC$JMT_redPackageReceiver tLRPC$JMT_redPackageReceiver = arrayList2.get(i2);
                                i += tLRPC$JMT_redPackageReceiver.amount;
                                if (tLRPC$JMT_redPackageReceiver.user.self) {
                                    JMTReceiveDetailActivity.this.tvReceiveMoney.setText(new DecimalFormat("0.00").format(Double.valueOf(tLRPC$JMT_redPackageReceiver.amount).doubleValue() / 100.0d));
                                    JMTReceiveDetailActivity.this.llReceiveMoney.setVisibility(0);
                                    JMTReceiveDetailActivity.this.llGotoWallet.setVisibility(0);
                                }
                            }
                            JMTReceiveDetailActivity.this.mList.addAll(arrayList2);
                            JMTReceiveDetailActivity.this.mAdapter.notifyDataSetChanged();
                            if (BuildVars.isShowRedPacketCountAndBest) {
                                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                                JMTReceiveDetailActivity.this.tvDetailTitle.setText(LocaleController.getString("JMTAlreadyReceive", R.string.JMTAlreadyReceive) + JMTReceiveDetailActivity.this.mList.size() + "/" + tLRPC$JMT_RedPackage.count + LocaleController.getString("JMTRedPackageCount", R.string.JMTRedPackageCount) + decimalFormat.format(Double.valueOf(i).doubleValue() / 100.0d) + "/" + decimalFormat.format(Double.valueOf(tLRPC$JMT_RedPackage.total_amount).doubleValue() / 100.0d) + LocaleController.getString("JMTRedPackageYuan", R.string.JMTRedPackageYuan));
                                JMTReceiveDetailActivity.this.tvDetailTitle.setVisibility(0);
                            }
                            JMTReceiveDetailActivity.this.llDetailList.setVisibility(0);
                        }
                    }
                });
            }
        });
    }

    class ReceiveDetailAdapter extends RecyclerView.Adapter<MyAdapterHolder> {
        private Context mContext;
        private List<TLRPC$JMT_redPackageReceiver> mList;
        private int maxIndex;

        public ReceiveDetailAdapter(JMTReceiveDetailActivity jMTReceiveDetailActivity, Context context, List<TLRPC$JMT_redPackageReceiver> list) {
            this.maxIndex = 0;
            this.mContext = context;
            this.mList = list;
            int i = 0;
            for (int i2 = 0; i2 < this.mList.size(); i2++) {
                int i3 = this.mList.get(i2).amount;
                if (i3 > i) {
                    this.maxIndex = i2;
                    i = i3;
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public MyAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyAdapterHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_receive_detail, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(MyAdapterHolder myAdapterHolder, int i) {
            TLRPC$User tLRPC$User = this.mList.get(i).user;
            myAdapterHolder.bivUserAvatar.setForUserOrChat(tLRPC$User, new AvatarDrawable(tLRPC$User));
            myAdapterHolder.tvUserName.setText(tLRPC$User.first_name);
            myAdapterHolder.tvUserTime.setText(timestampToDate(r0.date));
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            myAdapterHolder.tvUserMoney.setText(decimalFormat.format(Double.valueOf(r0.amount).doubleValue() / 100.0d) + LocaleController.getString("JMTRedPackageYuan", R.string.JMTRedPackageYuan));
            if (BuildVars.isShowRedPacketCountAndBest) {
                if (this.maxIndex == i) {
                    myAdapterHolder.tvUserMax.setVisibility(0);
                } else {
                    myAdapterHolder.tvUserMax.setVisibility(8);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mList.size();
        }

        private String timestampToDate(long j) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(j * 1000));
        }

        class MyAdapterHolder extends RecyclerView.ViewHolder {
            BackupImageView bivUserAvatar;
            TextView tvUserMax;
            TextView tvUserMoney;
            TextView tvUserName;
            TextView tvUserTime;

            private MyAdapterHolder(ReceiveDetailAdapter receiveDetailAdapter, View view) {
                super(view);
                BackupImageView backupImageView = (BackupImageView) view.findViewById(R.id.biv_user_avatar);
                this.bivUserAvatar = backupImageView;
                backupImageView.setRoundRadius(AndroidUtilities.dp(25.0f));
                this.tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
                this.tvUserTime = (TextView) view.findViewById(R.id.tv_user_time);
                this.tvUserMoney = (TextView) view.findViewById(R.id.tv_user_money);
                TextView textView = (TextView) view.findViewById(R.id.tv_user_max);
                this.tvUserMax = textView;
                textView.setText(LocaleController.getString("JMTRedPacketOptimal", R.string.JMTRedPacketOptimal));
            }
        }
    }
}
