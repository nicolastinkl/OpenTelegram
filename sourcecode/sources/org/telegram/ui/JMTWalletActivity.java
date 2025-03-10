package org.telegram.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.XPopup;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_WalletBill;
import org.telegram.tgnet.TLRPC$JMT_getService;
import org.telegram.tgnet.TLRPC$JMT_getWalletBillList;
import org.telegram.tgnet.TLRPC$JMT_jwallet;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.JMTWithdrawPopup;

/* loaded from: classes3.dex */
public class JMTWalletActivity extends BaseFragment {
    private BackupImageView bivAvatar;
    private WalletDetailAdapter mAdapter;
    private Context mContext;
    private List<TLRPC$JMT_WalletBill> mList;
    private RecyclerView rvList;
    private SmartRefreshLayout srlRefresh;
    private TextView tvMoney;
    private TextView tvName;
    private boolean have_password = false;
    private int pageNum = 0;
    private int pageSize = 20;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTWallet", R.string.JMTWallet));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTWalletActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTWalletActivity.this.finishFragment();
            }
        });
        this.actionBar.createMenu().addItem(0, LocaleController.getString("JMTPayPassword", R.string.JMTPayPassword)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTWalletActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (JMTWalletActivity.this.have_password) {
                    Toast.makeText(JMTWalletActivity.this.mContext, LocaleController.getString("JMTPayContactService", R.string.JMTPayContactService), 0).show();
                } else {
                    JMTWalletActivity.this.presentFragment(new JMTSetPayPwdActivity());
                }
            }
        });
        View inflate = View.inflate(context, R.layout.activity_wallet, null);
        this.fragmentView = inflate;
        BackupImageView backupImageView = (BackupImageView) inflate.findViewById(R.id.biv_avatar);
        this.bivAvatar = backupImageView;
        backupImageView.setRoundRadius(AndroidUtilities.dp(15.0f));
        this.tvName = (TextView) this.fragmentView.findViewById(R.id.tv_name);
        this.tvMoney = (TextView) this.fragmentView.findViewById(R.id.tv_money);
        ((TextView) this.fragmentView.findViewById(R.id.tv_can_withdraw_money)).setText(LocaleController.getString("JMTCanWithdraw", R.string.JMTCanWithdraw));
        ((LinearLayout) this.fragmentView.findViewById(R.id.ll_goto_recharge)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTWalletActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTWalletActivity.this.requestService(1);
            }
        });
        ((TextView) this.fragmentView.findViewById(R.id.tv_goto_recharge)).setText(LocaleController.getString("JMTWalletRecharge", R.string.JMTWalletRecharge));
        ((LinearLayout) this.fragmentView.findViewById(R.id.ll_goto_withdraw)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTWalletActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTWalletActivity.this.requestService(2);
            }
        });
        ((TextView) this.fragmentView.findViewById(R.id.tv_goto_withdraw)).setText(LocaleController.getString("JMTWalletWithdraw", R.string.JMTWalletWithdraw));
        ((TextView) this.fragmentView.findViewById(R.id.tv_red_packet_detail)).setText(LocaleController.getString("JMTWalletRedPacketDetail", R.string.JMTWalletRedPacketDetail));
        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) this.fragmentView.findViewById(R.id.srl_refresh);
        this.srlRefresh = smartRefreshLayout;
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(context));
        this.srlRefresh.setRefreshFooter(new ClassicsFooter(context));
        this.srlRefresh.setOnRefreshListener(new OnRefreshListener() { // from class: org.telegram.ui.JMTWalletActivity.5
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                JMTWalletActivity.this.refreshList();
            }
        });
        this.srlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: org.telegram.ui.JMTWalletActivity.6
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                JMTWalletActivity.this.loadMoreList();
            }
        });
        RecyclerView recyclerView = (RecyclerView) this.fragmentView.findViewById(R.id.rv_list);
        this.rvList = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        WalletDetailAdapter walletDetailAdapter = new WalletDetailAdapter(this, this.mContext, arrayList);
        this.mAdapter = walletDetailAdapter;
        this.rvList.setAdapter(walletDetailAdapter);
        loadWallet();
        refreshList();
        return this.fragmentView;
    }

    private void loadWallet() {
        TLRPC$User currentUser = getUserConfig().getCurrentUser();
        this.bivAvatar.setForUserOrChat(currentUser, new AvatarDrawable(currentUser));
        this.tvName.setText(currentUser.first_name);
        getConnectionsManager().sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$JMT_getSelfWallet
            public static int constructor = -571275408;

            @Override // org.telegram.tgnet.TLObject
            public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
                return TLRPC$JMT_jwallet.TLdeserialize(abstractSerializedData, i, z);
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
            }
        }, new RequestDelegate() { // from class: org.telegram.ui.JMTWalletActivity.7
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTWalletActivity.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error != null) {
                            Toast.makeText(JMTWalletActivity.this.mContext, LocaleController.getString("JMTGetErrorRetry", R.string.JMTGetErrorRetry), 0).show();
                            return;
                        }
                        TLRPC$JMT_jwallet tLRPC$JMT_jwallet = (TLRPC$JMT_jwallet) tLObject;
                        JMTWalletActivity.this.tvMoney.setText(new DecimalFormat("0.00").format(Double.valueOf(tLRPC$JMT_jwallet.balance).doubleValue() / 100.0d));
                        JMTWalletActivity.this.have_password = tLRPC$JMT_jwallet.have_password;
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestService(int i) {
        TLRPC$JMT_getService tLRPC$JMT_getService = new TLRPC$JMT_getService();
        tLRPC$JMT_getService.type = i;
        getConnectionsManager().sendRequest(tLRPC$JMT_getService, new AnonymousClass8(i));
    }

    /* renamed from: org.telegram.ui.JMTWalletActivity$8, reason: invalid class name */
    class AnonymousClass8 implements RequestDelegate {
        final /* synthetic */ int val$type;

        AnonymousClass8(int i) {
            this.val$type = i;
        }

        @Override // org.telegram.tgnet.RequestDelegate
        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTWalletActivity.8.1
                @Override // java.lang.Runnable
                public void run() {
                    if (tLRPC$TL_error == null) {
                        final TLRPC$User tLRPC$User = (TLRPC$User) tLObject;
                        if (tLRPC$User instanceof TLRPC$TL_userEmpty) {
                            return;
                        }
                        AnonymousClass8 anonymousClass8 = AnonymousClass8.this;
                        int i = anonymousClass8.val$type;
                        if (i == 1) {
                            new XPopup.Builder(JMTWalletActivity.this.mContext).asCustom(new JMTWithdrawPopup(JMTWalletActivity.this.mContext, LocaleController.getString("JMTContactServiceRecharge", R.string.JMTContactServiceRecharge), new JMTWithdrawPopup.OnContactListener() { // from class: org.telegram.ui.JMTWalletActivity.8.1.1
                                @Override // org.telegram.ui.JMTWithdrawPopup.OnContactListener
                                public void onContact() {
                                    if (JMTWalletActivity.this.getMessagesController().getUser(Long.valueOf(tLRPC$User.id)) == null) {
                                        JMTWalletActivity.this.getMessagesController().putUser(tLRPC$User, true);
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("user_id", tLRPC$User.id);
                                    JMTWalletActivity.this.presentFragment(new ChatActivity(bundle), true);
                                }
                            })).show();
                        } else if (i == 2) {
                            new XPopup.Builder(JMTWalletActivity.this.mContext).asCustom(new JMTWithdrawPopup(JMTWalletActivity.this.mContext, LocaleController.getString("JMTContactService", R.string.JMTContactService), new JMTWithdrawPopup.OnContactListener() { // from class: org.telegram.ui.JMTWalletActivity.8.1.2
                                @Override // org.telegram.ui.JMTWithdrawPopup.OnContactListener
                                public void onContact() {
                                    if (JMTWalletActivity.this.getMessagesController().getUser(Long.valueOf(tLRPC$User.id)) == null) {
                                        JMTWalletActivity.this.getMessagesController().putUser(tLRPC$User, true);
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("user_id", tLRPC$User.id);
                                    JMTWalletActivity.this.presentFragment(new ChatActivity(bundle), true);
                                }
                            })).show();
                        }
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshList() {
        this.pageNum = 0;
        loadList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMoreList() {
        this.pageNum++;
        loadList();
    }

    private void loadList() {
        TLRPC$JMT_getWalletBillList tLRPC$JMT_getWalletBillList = new TLRPC$JMT_getWalletBillList();
        int i = this.pageNum;
        int i2 = this.pageSize;
        tLRPC$JMT_getWalletBillList.offset = i * i2;
        tLRPC$JMT_getWalletBillList.limit = i2;
        tLRPC$JMT_getWalletBillList.date_lte = 0;
        tLRPC$JMT_getWalletBillList.date_gte = 0;
        tLRPC$JMT_getWalletBillList.type = 0;
        getConnectionsManager().sendRequest(tLRPC$JMT_getWalletBillList, new RequestDelegate() { // from class: org.telegram.ui.JMTWalletActivity.9
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTWalletActivity.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject2;
                                if (JMTWalletActivity.this.pageNum == 0) {
                                    JMTWalletActivity.this.mList.clear();
                                }
                                Iterator<Object> it = tLRPC$Vector.objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_WalletBill) {
                                        JMTWalletActivity.this.mList.add((TLRPC$JMT_WalletBill) next);
                                    }
                                }
                                JMTWalletActivity.this.mAdapter.notifyDataSetChanged();
                            }
                        } else if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTWalletActivity.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                        JMTWalletActivity.this.srlRefresh.finishRefresh();
                        JMTWalletActivity.this.srlRefresh.finishLoadMore();
                    }
                });
            }
        });
    }

    class WalletDetailAdapter extends RecyclerView.Adapter<MyAdapterHolder> {
        private Context context;
        private List<TLRPC$JMT_WalletBill> list;

        public WalletDetailAdapter(JMTWalletActivity jMTWalletActivity, Context context, List<TLRPC$JMT_WalletBill> list) {
            this.context = context;
            this.list = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public MyAdapterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyAdapterHolder(LayoutInflater.from(this.context).inflate(R.layout.adapter_wallet_detail, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(MyAdapterHolder myAdapterHolder, int i) {
            TLRPC$JMT_WalletBill tLRPC$JMT_WalletBill = this.list.get(i);
            boolean z = tLRPC$JMT_WalletBill.amount / 100 >= 0;
            int i2 = tLRPC$JMT_WalletBill.type;
            if (i2 == 1) {
                if (z) {
                    myAdapterHolder.tvDetailTitle.setText(LocaleController.getString("JMTWalletDetailFrom", R.string.JMTWalletDetailFrom) + tLRPC$JMT_WalletBill.chat_title);
                } else {
                    myAdapterHolder.tvDetailTitle.setText(LocaleController.getString("JMTWalletDetailTo", R.string.JMTWalletDetailTo) + tLRPC$JMT_WalletBill.chat_title);
                }
            } else if (i2 != 2) {
                myAdapterHolder.tvDetailTitle.setText(LocaleController.getString("JMTWalletDetailRefund", R.string.JMTWalletDetailRefund));
            } else if (z) {
                myAdapterHolder.tvDetailTitle.setText(LocaleController.getString("JMTWalletRecharge", R.string.JMTWalletRecharge));
            } else {
                myAdapterHolder.tvDetailTitle.setText(LocaleController.getString("JMTWalletWithdraw", R.string.JMTWalletWithdraw));
            }
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            myAdapterHolder.tvDetailMoney.setText(decimalFormat.format(Double.valueOf(tLRPC$JMT_WalletBill.amount).doubleValue() / 100.0d));
            myAdapterHolder.tvDetailContent.setText(LocaleController.getString("JMTWalletDetailBalance", R.string.JMTWalletDetailBalance) + decimalFormat.format(Double.valueOf(tLRPC$JMT_WalletBill.balance).doubleValue() / 100.0d));
            myAdapterHolder.tvDetailTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Long.valueOf(new Long((long) tLRPC$JMT_WalletBill.date).longValue() * 1000)));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.list.size();
        }

        class MyAdapterHolder extends RecyclerView.ViewHolder {
            TextView tvDetailContent;
            TextView tvDetailMoney;
            TextView tvDetailTime;
            TextView tvDetailTitle;

            private MyAdapterHolder(WalletDetailAdapter walletDetailAdapter, View view) {
                super(view);
                this.tvDetailTitle = (TextView) view.findViewById(R.id.tv_detail_title);
                this.tvDetailContent = (TextView) view.findViewById(R.id.tv_detail_content);
                this.tvDetailMoney = (TextView) view.findViewById(R.id.tv_detail_money);
                this.tvDetailTime = (TextView) view.findViewById(R.id.tv_detail_time);
            }
        }
    }
}
