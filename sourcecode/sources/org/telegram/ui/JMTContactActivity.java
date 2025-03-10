package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_deleteFriend_req;
import org.telegram.tgnet.TLRPC$JMT_friendList_req;
import org.telegram.tgnet.TLRPC$JMT_friendList_res;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTContactActivity extends BaseFragment {
    private ContactAdapter adapter;
    private JMTContactsActivityDelegate delegate;
    private Context mContext;
    private List<TLRPC$JMT_friendList_res> mList;
    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;

    public interface JMTContactsActivityDelegate {
        void didSelectContact(TLRPC$User tLRPC$User, String str, JMTContactActivity jMTContactActivity);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Contacts", R.string.Contacts));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTContactActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTContactActivity.this.finishFragment();
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        View inflate = View.inflate(context, R.layout.activity_contact, null);
        RefreshLayout refreshLayout = (RefreshLayout) inflate.findViewById(R.id.refreshLayout);
        this.refreshLayout = refreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: org.telegram.ui.JMTContactActivity.2
            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout2) {
                JMTContactActivity.this.loadList();
            }
        });
        this.mList = new ArrayList();
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ContactAdapter contactAdapter = new ContactAdapter(context, this.mList);
        this.adapter = contactAdapter;
        this.recyclerView.setAdapter(contactAdapter);
        frameLayout.addView(inflate, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    public void setDelegate(JMTContactsActivityDelegate jMTContactsActivityDelegate) {
        this.delegate = jMTContactsActivityDelegate;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        loadList();
        return super.onFragmentCreate();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        this.delegate = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadList() {
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLRPC$JMT_friendList_req(), new RequestDelegate() { // from class: org.telegram.ui.JMTContactActivity.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTContactActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                JMTContactActivity.this.mList.clear();
                                Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_friendList_res) {
                                        JMTContactActivity.this.mList.add((TLRPC$JMT_friendList_res) next);
                                    }
                                }
                                JMTContactActivity.this.adapter.notifyDataSetChanged();
                            }
                        } else if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTContactActivity.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                        JMTContactActivity.this.refreshLayout.finishRefresh();
                        JMTContactActivity.this.refreshLayout.finishLoadMore();
                    }
                });
            }
        });
    }

    class ContactAdapter extends RecyclerView.Adapter<NewApplyHolder> {
        private Context mContext;
        private List<TLRPC$JMT_friendList_res> mList;

        public ContactAdapter(Context context, List<TLRPC$JMT_friendList_res> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public NewApplyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new NewApplyHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_contact, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(NewApplyHolder newApplyHolder, int i) {
            final TLRPC$User tLRPC$User = this.mList.get(i).user;
            newApplyHolder.bivIcon.setForUserOrChat(tLRPC$User, new AvatarDrawable(tLRPC$User));
            newApplyHolder.tvTitle.setText(tLRPC$User.first_name);
            if (BuildVars.isCanShowFriendInfo) {
                newApplyHolder.tvText.setText(tLRPC$User.username);
            } else {
                newApplyHolder.tvText.setText("-");
            }
            newApplyHolder.llContainer.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTContactActivity.ContactAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (JMTContactActivity.this.delegate != null) {
                        JMTContactActivity.this.delegate.didSelectContact(tLRPC$User, null, JMTContactActivity.this);
                        JMTContactActivity.this.delegate = null;
                        JMTContactActivity.this.finishFragment();
                    } else {
                        if (JMTContactActivity.this.getMessagesController().getUser(Long.valueOf(tLRPC$User.id)) == null) {
                            JMTContactActivity.this.getMessagesController().putUser(tLRPC$User, true);
                        }
                        Bundle bundle = new Bundle();
                        bundle.putLong("user_id", tLRPC$User.id);
                        JMTContactActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                }
            });
            newApplyHolder.llContainer.setOnLongClickListener(new AnonymousClass2(tLRPC$User));
        }

        /* renamed from: org.telegram.ui.JMTContactActivity$ContactAdapter$2, reason: invalid class name */
        class AnonymousClass2 implements View.OnLongClickListener {
            final /* synthetic */ TLRPC$User val$user;

            AnonymousClass2(TLRPC$User tLRPC$User) {
                this.val$user = tLRPC$User;
            }

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (JMTContactActivity.this.delegate != null) {
                    return false;
                }
                new AlertDialog.Builder(JMTContactActivity.this.getParentActivity()).setTitle(LocaleController.getString("JMTDeleteFriend", R.string.JMTDeleteFriend)).setMessage(LocaleController.getString("JMTDeleteRequest", R.string.JMTDeleteRequest)).setNegativeButton(LocaleController.getString("JMTCancel", R.string.JMTCancel), null).setPositiveButton(LocaleController.getString("JMTComfirm", R.string.JMTComfirm), new AnonymousClass1()).create().show();
                return false;
            }

            /* renamed from: org.telegram.ui.JMTContactActivity$ContactAdapter$2$1, reason: invalid class name */
            class AnonymousClass1 implements DialogInterface.OnClickListener {
                AnonymousClass1() {
                }

                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    TLRPC$JMT_deleteFriend_req tLRPC$JMT_deleteFriend_req = new TLRPC$JMT_deleteFriend_req();
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    tLRPC$JMT_deleteFriend_req.friend_id = anonymousClass2.val$user.id;
                    ConnectionsManager.getInstance(((BaseFragment) JMTContactActivity.this).currentAccount).sendRequest(tLRPC$JMT_deleteFriend_req, new RequestDelegate() { // from class: org.telegram.ui.JMTContactActivity.ContactAdapter.2.1.1
                        @Override // org.telegram.tgnet.RequestDelegate
                        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTContactActivity.ContactAdapter.2.1.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                    if (tLRPC$TL_error2 == null) {
                                        if (!(tLObject instanceof TLRPC$TL_boolTrue)) {
                                            Toast.makeText(ContactAdapter.this.mContext, LocaleController.getString("JMTDeleteError", R.string.JMTDeleteError), 0).show();
                                            return;
                                        } else {
                                            JMTContactActivity.this.getMessagesController().deleteDialog(AnonymousClass2.this.val$user.id, 0);
                                            JMTContactActivity.this.loadList();
                                            return;
                                        }
                                    }
                                    String str = tLRPC$TL_error2.text;
                                    if (str != null) {
                                        if (str.contains("DELETE_FRIEND_FORBIDDEN")) {
                                            Toast.makeText(ContactAdapter.this.mContext, LocaleController.getString("JMTStopDelete", R.string.JMTStopDelete), 0).show();
                                        } else {
                                            Toast.makeText(ContactAdapter.this.mContext, tLRPC$TL_error.text, 0).show();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mList.size();
        }

        class NewApplyHolder extends RecyclerView.ViewHolder {
            BackupImageView bivIcon;
            LinearLayout llContainer;
            TextView tvText;
            TextView tvTitle;
            View vDivider;

            private NewApplyHolder(ContactAdapter contactAdapter, View view) {
                super(view);
                this.llContainer = (LinearLayout) view.findViewById(R.id.ll_container);
                BackupImageView backupImageView = (BackupImageView) view.findViewById(R.id.biv_icon);
                this.bivIcon = backupImageView;
                backupImageView.setRoundRadius(AndroidUtilities.dp(25.0f));
                TextView textView = (TextView) view.findViewById(R.id.tv_title);
                this.tvTitle = textView;
                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                TextView textView2 = (TextView) view.findViewById(R.id.tv_text);
                this.tvText = textView2;
                textView2.setTextColor(Theme.getColor(Theme.key_dialogTextGray));
                View findViewById = view.findViewById(R.id.v_divider);
                this.vDivider = findViewById;
                findViewById.setBackgroundColor(Theme.getColor(Theme.key_divider));
            }
        }
    }
}
