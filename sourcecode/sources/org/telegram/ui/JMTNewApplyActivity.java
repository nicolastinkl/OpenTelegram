package org.telegram.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_acceptFriend_req;
import org.telegram.tgnet.TLRPC$JMT_applyList_req;
import org.telegram.tgnet.TLRPC$JMT_applyList_res;
import org.telegram.tgnet.TLRPC$JMT_rejectFriend_req;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTNewApplyActivity extends BaseFragment {
    private NewApplyAdapter adapter;
    private Context mContext;
    private List<TLRPC$JMT_applyList_res> mList;
    private int pageNum = 0;
    private int pageSize = 20;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTNewApply", R.string.JMTNewApply));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTNewApplyActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTNewApplyActivity.this.finishFragment();
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        View inflate = View.inflate(context, R.layout.activity_new_apply, null);
        RefreshLayout refreshLayout = (RefreshLayout) inflate.findViewById(R.id.refreshLayout);
        this.refreshLayout = refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        this.refreshLayout.setRefreshFooter(new ClassicsFooter(context));
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: org.telegram.ui.JMTNewApplyActivity.2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout2) {
                JMTNewApplyActivity.this.refreshList();
            }
        });
        this.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: org.telegram.ui.JMTNewApplyActivity.3
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout2) {
                JMTNewApplyActivity.this.loadMoreList();
            }
        });
        this.mList = new ArrayList();
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        NewApplyAdapter newApplyAdapter = new NewApplyAdapter(context, this.mList);
        this.adapter = newApplyAdapter;
        this.recyclerView.setAdapter(newApplyAdapter);
        frameLayout.addView(inflate, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        refreshList();
        return super.onFragmentCreate();
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
        TLRPC$JMT_applyList_req tLRPC$JMT_applyList_req = new TLRPC$JMT_applyList_req();
        int i = this.pageNum;
        int i2 = this.pageSize;
        tLRPC$JMT_applyList_req.offset = i * i2;
        tLRPC$JMT_applyList_req.limit = i2;
        tLRPC$JMT_applyList_req.status = "";
        tLRPC$JMT_applyList_req.include_apply_by_me = 0;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$JMT_applyList_req, new RequestDelegate() { // from class: org.telegram.ui.JMTNewApplyActivity.4
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTNewApplyActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject2;
                                if (JMTNewApplyActivity.this.pageNum == 0) {
                                    JMTNewApplyActivity.this.mList.clear();
                                }
                                Iterator<Object> it = tLRPC$Vector.objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_applyList_res) {
                                        JMTNewApplyActivity.this.mList.add((TLRPC$JMT_applyList_res) next);
                                    }
                                }
                                JMTNewApplyActivity.this.adapter.notifyDataSetChanged();
                            }
                        } else if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTNewApplyActivity.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                        JMTNewApplyActivity.this.refreshLayout.finishRefresh();
                        JMTNewApplyActivity.this.refreshLayout.finishLoadMore();
                    }
                });
            }
        });
    }

    class NewApplyAdapter extends RecyclerView.Adapter<NewApplyHolder> {
        private Context mContext;
        private List<TLRPC$JMT_applyList_res> mList;

        public NewApplyAdapter(Context context, List<TLRPC$JMT_applyList_res> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public NewApplyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new NewApplyHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_newapply, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(NewApplyHolder newApplyHolder, int i) {
            TLRPC$JMT_applyList_res tLRPC$JMT_applyList_res = this.mList.get(i);
            TLRPC$User tLRPC$User = tLRPC$JMT_applyList_res.user;
            newApplyHolder.bivIcon.setForUserOrChat(tLRPC$User, new AvatarDrawable(tLRPC$User));
            newApplyHolder.tvTitle.setText(tLRPC$User.first_name);
            newApplyHolder.tvText.setText(tLRPC$JMT_applyList_res.reason);
            if (tLRPC$User.self) {
                newApplyHolder.tvReceive.setVisibility(8);
                newApplyHolder.tvReject.setVisibility(8);
                newApplyHolder.tvApproved.setVisibility(8);
                newApplyHolder.tvRejected.setVisibility(8);
                return;
            }
            int i2 = tLRPC$JMT_applyList_res.status;
            if (i2 == 1) {
                newApplyHolder.tvReceive.setVisibility(0);
                newApplyHolder.tvReject.setVisibility(0);
                newApplyHolder.tvApproved.setVisibility(8);
                newApplyHolder.tvRejected.setVisibility(8);
                newApplyHolder.tvReceive.setOnClickListener(new AnonymousClass1(tLRPC$JMT_applyList_res));
                newApplyHolder.tvReject.setOnClickListener(new AnonymousClass2(tLRPC$JMT_applyList_res));
                return;
            }
            if (i2 == 2) {
                newApplyHolder.tvReceive.setVisibility(8);
                newApplyHolder.tvReject.setVisibility(8);
                newApplyHolder.tvApproved.setVisibility(0);
                newApplyHolder.tvRejected.setVisibility(8);
                return;
            }
            if (i2 == 3) {
                newApplyHolder.tvReceive.setVisibility(8);
                newApplyHolder.tvReject.setVisibility(8);
                newApplyHolder.tvApproved.setVisibility(8);
                newApplyHolder.tvRejected.setVisibility(0);
            }
        }

        /* renamed from: org.telegram.ui.JMTNewApplyActivity$NewApplyAdapter$1, reason: invalid class name */
        class AnonymousClass1 implements View.OnClickListener {
            final /* synthetic */ TLRPC$JMT_applyList_res val$res;

            AnonymousClass1(TLRPC$JMT_applyList_res tLRPC$JMT_applyList_res) {
                this.val$res = tLRPC$JMT_applyList_res;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TLRPC$JMT_acceptFriend_req tLRPC$JMT_acceptFriend_req = new TLRPC$JMT_acceptFriend_req();
                tLRPC$JMT_acceptFriend_req.apply_id = this.val$res.apply_id;
                ConnectionsManager.getInstance(((BaseFragment) JMTNewApplyActivity.this).currentAccount).sendRequest(tLRPC$JMT_acceptFriend_req, new RequestDelegate() { // from class: org.telegram.ui.JMTNewApplyActivity.NewApplyAdapter.1.1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTNewApplyActivity.NewApplyAdapter.1.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                if (tLRPC$TL_error2 == null) {
                                    if (!(tLObject instanceof TLRPC$TL_boolTrue)) {
                                        Toast.makeText(NewApplyAdapter.this.mContext, LocaleController.getString("JMTAgreeError", R.string.JMTAgreeError), 0).show();
                                        return;
                                    }
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    anonymousClass1.val$res.status = 2;
                                    JMTNewApplyActivity.this.adapter.notifyDataSetChanged();
                                    return;
                                }
                                if (tLRPC$TL_error2.text != null) {
                                    Toast.makeText(NewApplyAdapter.this.mContext, tLRPC$TL_error.text, 0).show();
                                }
                            }
                        });
                    }
                });
            }
        }

        /* renamed from: org.telegram.ui.JMTNewApplyActivity$NewApplyAdapter$2, reason: invalid class name */
        class AnonymousClass2 implements View.OnClickListener {
            final /* synthetic */ TLRPC$JMT_applyList_res val$res;

            AnonymousClass2(TLRPC$JMT_applyList_res tLRPC$JMT_applyList_res) {
                this.val$res = tLRPC$JMT_applyList_res;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TLRPC$JMT_rejectFriend_req tLRPC$JMT_rejectFriend_req = new TLRPC$JMT_rejectFriend_req();
                tLRPC$JMT_rejectFriend_req.apply_id = this.val$res.apply_id;
                ConnectionsManager.getInstance(((BaseFragment) JMTNewApplyActivity.this).currentAccount).sendRequest(tLRPC$JMT_rejectFriend_req, new RequestDelegate() { // from class: org.telegram.ui.JMTNewApplyActivity.NewApplyAdapter.2.1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTNewApplyActivity.NewApplyAdapter.2.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                if (tLRPC$TL_error2 == null) {
                                    if (!(tLObject instanceof TLRPC$TL_boolTrue)) {
                                        Toast.makeText(NewApplyAdapter.this.mContext, LocaleController.getString("JMTRejectError", R.string.JMTRejectError), 0).show();
                                        return;
                                    }
                                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                                    anonymousClass2.val$res.status = 3;
                                    JMTNewApplyActivity.this.adapter.notifyDataSetChanged();
                                    return;
                                }
                                if (tLRPC$TL_error2.text != null) {
                                    Toast.makeText(NewApplyAdapter.this.mContext, tLRPC$TL_error.text, 0).show();
                                }
                            }
                        });
                    }
                });
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mList.size();
        }

        class NewApplyHolder extends RecyclerView.ViewHolder {
            BackupImageView bivIcon;
            TextView tvApproved;
            TextView tvReceive;
            TextView tvReject;
            TextView tvRejected;
            TextView tvText;
            TextView tvTitle;
            View vDivider;

            private NewApplyHolder(NewApplyAdapter newApplyAdapter, View view) {
                super(view);
                BackupImageView backupImageView = (BackupImageView) view.findViewById(R.id.biv_icon);
                this.bivIcon = backupImageView;
                backupImageView.setRoundRadius(AndroidUtilities.dp(19.0f));
                TextView textView = (TextView) view.findViewById(R.id.tv_title);
                this.tvTitle = textView;
                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                TextView textView2 = (TextView) view.findViewById(R.id.tv_text);
                this.tvText = textView2;
                textView2.setTextColor(Theme.getColor(Theme.key_dialogTextGray));
                TextView textView3 = (TextView) view.findViewById(R.id.tv_receive);
                this.tvReceive = textView3;
                textView3.setText(LocaleController.getString("JMTReceive", R.string.JMTReceive));
                TextView textView4 = (TextView) view.findViewById(R.id.tv_reject);
                this.tvReject = textView4;
                textView4.setText(LocaleController.getString("JMTReject", R.string.JMTReject));
                TextView textView5 = (TextView) view.findViewById(R.id.tv_approved);
                this.tvApproved = textView5;
                textView5.setText(LocaleController.getString("JMTISReceive", R.string.JMTISReceive));
                TextView textView6 = (TextView) view.findViewById(R.id.tv_rejected);
                this.tvRejected = textView6;
                textView6.setText(LocaleController.getString("JMTisReject", R.string.JMTisReject));
                View findViewById = view.findViewById(R.id.v_divider);
                this.vDivider = findViewById;
                findViewById.setBackgroundColor(Theme.getColor(Theme.key_divider));
            }
        }
    }
}
