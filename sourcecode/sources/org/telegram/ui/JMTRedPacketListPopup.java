package org.telegram.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_RedPackage;
import org.telegram.tgnet.TLRPC$JMT_getRedPackageList;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;

/* loaded from: classes3.dex */
public class JMTRedPacketListPopup extends AttachPopupView {
    public boolean isCreate;
    private int loadRedPackageCount;
    private RedPacketListAdapter mAdapter;
    private Context mContext;
    public long mDialogId;
    private List<TLRPC$JMT_RedPackage> mList;
    private int pageNum;
    private int pageSize;
    private RefreshLayout srlRefreshLayout;
    private TextView tvEmptyView;

    public JMTRedPacketListPopup(Context context, long j) {
        super(context);
        this.isCreate = false;
        this.loadRedPackageCount = 5;
        this.pageNum = 0;
        this.pageSize = 10;
        this.mContext = context;
        this.mDialogId = j;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.popup_red_packet_list;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.srl_refresh_layout);
        this.srlRefreshLayout = refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this.mContext));
        this.srlRefreshLayout.setRefreshFooter(new ClassicsFooter(this.mContext));
        this.srlRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: org.telegram.ui.JMTRedPacketListPopup.1
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout2) {
                JMTRedPacketListPopup.this.refreshList();
            }
        });
        this.srlRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: org.telegram.ui.JMTRedPacketListPopup.2
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout2) {
                JMTRedPacketListPopup.this.loadingList();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_red_packet);
        TextView textView = (TextView) findViewById(R.id.tv_empty_view);
        this.tvEmptyView = textView;
        textView.setText(LocaleController.getString("JMTRedPacketEmpty", R.string.JMTRedPacketEmpty));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        RedPacketListAdapter redPacketListAdapter = new RedPacketListAdapter(this.mContext, arrayList);
        this.mAdapter = redPacketListAdapter;
        recyclerView.setAdapter(redPacketListAdapter);
        refreshList();
        this.isCreate = true;
    }

    public void refreshList() {
        this.pageNum = 0;
        List<TLRPC$JMT_RedPackage> list = this.mList;
        if (list != null && list.size() > 10) {
            this.pageSize = this.mList.size();
        } else {
            this.pageSize = 10;
        }
        loadList();
    }

    public void loadingList() {
        this.pageNum++;
        loadList();
    }

    private void loadList() {
        int i = this.loadRedPackageCount;
        if (i <= 0) {
            return;
        }
        this.loadRedPackageCount = i - 1;
        TLRPC$JMT_getRedPackageList tLRPC$JMT_getRedPackageList = new TLRPC$JMT_getRedPackageList();
        tLRPC$JMT_getRedPackageList.peer = MessagesController.getInstance(UserConfig.selectedAccount).getInputPeer(this.mDialogId);
        int i2 = this.pageNum;
        int i3 = this.pageSize;
        tLRPC$JMT_getRedPackageList.offset = i2 * i3;
        tLRPC$JMT_getRedPackageList.limit = i3;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_getRedPackageList, new RequestDelegate() { // from class: org.telegram.ui.JMTRedPacketListPopup.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTRedPacketListPopup.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        JMTRedPacketListPopup.this.loadRedPackageCount = 5;
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject2;
                                if (JMTRedPacketListPopup.this.pageNum == 0) {
                                    JMTRedPacketListPopup.this.mList.clear();
                                }
                                Iterator<Object> it = tLRPC$Vector.objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_RedPackage) {
                                        JMTRedPacketListPopup.this.mList.add((TLRPC$JMT_RedPackage) next);
                                    }
                                }
                                if (JMTRedPacketListPopup.this.mList.size() == 0) {
                                    JMTRedPacketListPopup.this.tvEmptyView.setVisibility(0);
                                } else {
                                    JMTRedPacketListPopup.this.tvEmptyView.setVisibility(8);
                                }
                                JMTRedPacketListPopup.this.mAdapter.notifyDataSetChanged();
                            }
                        } else if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTRedPacketListPopup.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                        JMTRedPacketListPopup.this.srlRefreshLayout.finishRefresh();
                        JMTRedPacketListPopup.this.srlRefreshLayout.finishLoadMore();
                    }
                });
            }
        });
    }

    public class RedPacketListAdapter extends RecyclerView.Adapter<RedPacketListHolder> {
        private Drawable emptyDrawable;
        private Context mContext;
        private List<TLRPC$JMT_RedPackage> mList;
        private Drawable normalDrawable;

        public RedPacketListAdapter(Context context, List<TLRPC$JMT_RedPackage> list) {
            this.mContext = context;
            this.mList = list;
            this.normalDrawable = ContextCompat.getDrawable(context, R.drawable.jmt_red_packet_normal);
            this.emptyDrawable = ContextCompat.getDrawable(this.mContext, R.drawable.jmt_red_packet_empty);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RedPacketListHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new RedPacketListHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_red_packet_item, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RedPacketListHolder redPacketListHolder, int i) {
            final TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage = this.mList.get(i);
            final boolean z = ((long) tLRPC$JMT_RedPackage.expired_time) <= System.currentTimeMillis() / 1000;
            redPacketListHolder.llRedPacketItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTRedPacketListPopup.RedPacketListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage2 = tLRPC$JMT_RedPackage;
                    if (tLRPC$JMT_RedPackage2.red_package_type == 2 && !tLRPC$JMT_RedPackage2.designated_user.self) {
                        Toast.makeText(RedPacketListAdapter.this.mContext, LocaleController.getString("JMTUnableCollect", R.string.JMTUnableCollect), 0).show();
                        return;
                    }
                    if (z) {
                        JMTRedPacketListPopup.this.dismiss();
                        ((LaunchActivity) RedPacketListAdapter.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTRedPacketListPopup.this.mDialogId, tLRPC$JMT_RedPackage));
                        return;
                    }
                    int i2 = tLRPC$JMT_RedPackage2.status;
                    if (i2 == 0) {
                        new XPopup.Builder(RedPacketListAdapter.this.mContext).asCustom(new JMTReceivePopup(RedPacketListAdapter.this.mContext, JMTRedPacketListPopup.this, tLRPC$JMT_RedPackage)).show();
                        return;
                    }
                    if (i2 == 1) {
                        boolean z2 = false;
                        for (int i3 = 0; i3 < tLRPC$JMT_RedPackage.receivers.size(); i3++) {
                            if (tLRPC$JMT_RedPackage.receivers.get(i3).longValue() == UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId()) {
                                z2 = true;
                            }
                        }
                        if (z2) {
                            JMTRedPacketListPopup.this.dismiss();
                            ((LaunchActivity) RedPacketListAdapter.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTRedPacketListPopup.this.mDialogId, tLRPC$JMT_RedPackage));
                            return;
                        } else {
                            new XPopup.Builder(RedPacketListAdapter.this.mContext).asCustom(new JMTReceivePopup(RedPacketListAdapter.this.mContext, JMTRedPacketListPopup.this, tLRPC$JMT_RedPackage)).show();
                            return;
                        }
                    }
                    JMTRedPacketListPopup.this.dismiss();
                    ((LaunchActivity) RedPacketListAdapter.this.mContext).lambda$runLinkRequest$77(new JMTReceiveDetailActivity(JMTRedPacketListPopup.this.mDialogId, tLRPC$JMT_RedPackage));
                }
            });
            redPacketListHolder.tvRedPacketTitle.setText(tLRPC$JMT_RedPackage.title);
            redPacketListHolder.tvRedPacketSubtitle.setVisibility(8);
            boolean z2 = false;
            for (int i2 = 0; i2 < tLRPC$JMT_RedPackage.receivers.size(); i2++) {
                if (tLRPC$JMT_RedPackage.receivers.get(i2).longValue() == UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId()) {
                    z2 = true;
                }
            }
            if (tLRPC$JMT_RedPackage.status == 2) {
                redPacketListHolder.llRedPacketItem.setBackground(this.emptyDrawable);
                if (z2) {
                    redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTRedPacketReceived", R.string.JMTRedPacketReceived));
                } else {
                    redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTReceiveFinished", R.string.JMTReceiveFinished));
                }
                redPacketListHolder.tvRedPacketSubtitle.setVisibility(0);
            } else if (z) {
                redPacketListHolder.llRedPacketItem.setBackground(this.emptyDrawable);
                if (z2) {
                    redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTRedPacketReceived", R.string.JMTRedPacketReceived));
                } else {
                    redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTRedPackageExpired", R.string.JMTRedPackageExpired));
                }
                redPacketListHolder.tvRedPacketSubtitle.setVisibility(0);
            } else if (z2) {
                redPacketListHolder.llRedPacketItem.setBackground(this.emptyDrawable);
                redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTRedPacketReceived", R.string.JMTRedPacketReceived));
                redPacketListHolder.tvRedPacketSubtitle.setVisibility(0);
            } else {
                redPacketListHolder.llRedPacketItem.setBackground(this.normalDrawable);
            }
            if (tLRPC$JMT_RedPackage.red_package_type == 2) {
                TLRPC$User tLRPC$User = tLRPC$JMT_RedPackage.designated_user;
                redPacketListHolder.tvRedPacketSubtitle.setText(LocaleController.getString("JMTGiveRedPackage", R.string.JMTGiveRedPackage) + tLRPC$User.first_name + LocaleController.getString("JMTOfExclusivePackage", R.string.JMTOfExclusivePackage));
                redPacketListHolder.tvRedPacketSubtitle.setVisibility(0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mList.size();
        }

        class RedPacketListHolder extends RecyclerView.ViewHolder {
            LinearLayout llRedPacketItem;
            TextView tvRedPacketSubtitle;
            TextView tvRedPacketTitle;

            private RedPacketListHolder(RedPacketListAdapter redPacketListAdapter, View view) {
                super(view);
                this.llRedPacketItem = (LinearLayout) view.findViewById(R.id.ll_red_packet_item);
                this.tvRedPacketTitle = (TextView) view.findViewById(R.id.tv_red_packet_title);
                this.tvRedPacketSubtitle = (TextView) view.findViewById(R.id.tv_red_packet_subtitle);
            }
        }
    }
}
