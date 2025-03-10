package org.telegram.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_applyFriend_req;
import org.telegram.tgnet.TLRPC$JMT_searchUser_req;
import org.telegram.tgnet.TLRPC$JMT_searchUser_res;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class JMTAddFriendActivity extends BaseFragment {
    private AddFriendAdapter adapter;
    private Context mContext;
    private List<TLRPC$JMT_searchUser_res> mList;
    private RecyclerView recyclerView;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTAddFriend", R.string.JMTAddFriend));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTAddFriendActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTAddFriendActivity.this.finishFragment();
            }
        });
        ActionBarMenuItem actionBarMenuItemSearchListener = this.actionBar.createMenu().addItem(0, R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItem.ActionBarMenuItemSearchListener() { // from class: org.telegram.ui.JMTAddFriendActivity.2
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onSearchPressed(EditText editText) {
                String trim = editText.getText().toString().trim();
                if (trim.isEmpty()) {
                    return;
                }
                JMTAddFriendActivity.this.searchUser(trim);
            }
        });
        int i = R.string.Search;
        actionBarMenuItemSearchListener.setSearchFieldHint(LocaleController.getString("Search", i));
        actionBarMenuItemSearchListener.setContentDescription(LocaleController.getString("Search", i));
        actionBarMenuItemSearchListener.performClick();
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        this.mList = new ArrayList();
        RecyclerView recyclerView = new RecyclerView(context);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AddFriendAdapter addFriendAdapter = new AddFriendAdapter(context, this.mList);
        this.adapter = addFriendAdapter;
        this.recyclerView.setAdapter(addFriendAdapter);
        frameLayout.addView(this.recyclerView, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchUser(String str) {
        TLRPC$JMT_searchUser_req tLRPC$JMT_searchUser_req = new TLRPC$JMT_searchUser_req();
        tLRPC$JMT_searchUser_req.f31q = str;
        tLRPC$JMT_searchUser_req.limit = 50;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$JMT_searchUser_req, new RequestDelegate() { // from class: org.telegram.ui.JMTAddFriendActivity.3
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTAddFriendActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                JMTAddFriendActivity.this.mList.clear();
                                Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_searchUser_res) {
                                        JMTAddFriendActivity.this.mList.add((TLRPC$JMT_searchUser_res) next);
                                    }
                                }
                                JMTAddFriendActivity.this.adapter.notifyDataSetChanged();
                                if (JMTAddFriendActivity.this.mList.size() == 0) {
                                    Toast.makeText(JMTAddFriendActivity.this.mContext, LocaleController.getString("JMTUserNotExit", R.string.JMTUserNotExit), 0).show();
                                    return;
                                }
                                return;
                            }
                            Toast.makeText(JMTAddFriendActivity.this.mContext, LocaleController.getString("JMTUserNotExit", R.string.JMTUserNotExit), 0).show();
                            return;
                        }
                        if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTAddFriendActivity.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                    }
                });
            }
        });
    }

    class AddFriendAdapter extends RecyclerView.Adapter<AddFriendHolder> {
        private Context mContext;
        private List<TLRPC$JMT_searchUser_res> mList;

        public AddFriendAdapter(Context context, List<TLRPC$JMT_searchUser_res> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public AddFriendHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AddFriendHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_addfriend, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(AddFriendHolder addFriendHolder, int i) {
            TLRPC$JMT_searchUser_res tLRPC$JMT_searchUser_res = this.mList.get(i);
            TLRPC$User tLRPC$User = tLRPC$JMT_searchUser_res.user;
            addFriendHolder.bivIcon.setForUserOrChat(tLRPC$User, new AvatarDrawable(tLRPC$User));
            addFriendHolder.tvTitle.setText(tLRPC$User.first_name);
            addFriendHolder.tvText.setText(tLRPC$User.username);
            if (tLRPC$User.self || tLRPC$JMT_searchUser_res.is_friend) {
                addFriendHolder.tvAdded.setVisibility(8);
            } else {
                addFriendHolder.tvAdded.setVisibility(0);
                addFriendHolder.tvAdded.setOnClickListener(new AnonymousClass1(tLRPC$User));
            }
        }

        /* renamed from: org.telegram.ui.JMTAddFriendActivity$AddFriendAdapter$1, reason: invalid class name */
        class AnonymousClass1 implements View.OnClickListener {
            final /* synthetic */ TLRPC$User val$user;

            AnonymousClass1(TLRPC$User tLRPC$User) {
                this.val$user = tLRPC$User;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TLRPC$JMT_applyFriend_req tLRPC$JMT_applyFriend_req = new TLRPC$JMT_applyFriend_req();
                tLRPC$JMT_applyFriend_req.user_id = this.val$user.id;
                tLRPC$JMT_applyFriend_req.reason = "";
                tLRPC$JMT_applyFriend_req.sourceType = 1;
                tLRPC$JMT_applyFriend_req.sourceId = 0L;
                ConnectionsManager.getInstance(((BaseFragment) JMTAddFriendActivity.this).currentAccount).sendRequest(tLRPC$JMT_applyFriend_req, new RequestDelegate() { // from class: org.telegram.ui.JMTAddFriendActivity.AddFriendAdapter.1.1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTAddFriendActivity.AddFriendAdapter.1.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                if (tLRPC$TL_error2 == null) {
                                    if (tLObject instanceof TLRPC$TL_boolTrue) {
                                        Toast.makeText(AddFriendAdapter.this.mContext, LocaleController.getString("JMTRequestSuccess", R.string.JMTRequestSuccess), 0).show();
                                        return;
                                    } else {
                                        Toast.makeText(AddFriendAdapter.this.mContext, LocaleController.getString("JMTRequestError", R.string.JMTRequestError), 0).show();
                                        return;
                                    }
                                }
                                String str = tLRPC$TL_error2.text;
                                if (str == null) {
                                    Toast.makeText(AddFriendAdapter.this.mContext, LocaleController.getString("JMTRequestNot", R.string.JMTRequestNot), 0).show();
                                } else if (str.equals("APPLY_FRIEND_FORBIDDEN")) {
                                    Toast.makeText(AddFriendAdapter.this.mContext, LocaleController.getString("JMTRequestStop", R.string.JMTRequestStop), 0).show();
                                } else {
                                    Toast.makeText(AddFriendAdapter.this.mContext, LocaleController.getString("JMTRequestNot", R.string.JMTRequestNot), 0).show();
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

        class AddFriendHolder extends RecyclerView.ViewHolder {
            BackupImageView bivIcon;
            TextView tvAdded;
            TextView tvText;
            TextView tvTitle;
            View vDivider;

            private AddFriendHolder(AddFriendAdapter addFriendAdapter, View view) {
                super(view);
                BackupImageView backupImageView = (BackupImageView) view.findViewById(R.id.biv_icon);
                this.bivIcon = backupImageView;
                backupImageView.getImageReceiver().setRoundRadius(AndroidUtilities.dp(19.0f));
                TextView textView = (TextView) view.findViewById(R.id.tv_title);
                this.tvTitle = textView;
                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                TextView textView2 = (TextView) view.findViewById(R.id.tv_text);
                this.tvText = textView2;
                textView2.setTextColor(Theme.getColor(Theme.key_dialogTextGray));
                TextView textView3 = (TextView) view.findViewById(R.id.tv_added);
                this.tvAdded = textView3;
                textView3.setText(LocaleController.getString("JMTAdded", R.string.JMTAdded));
                View findViewById = view.findViewById(R.id.v_divider);
                this.vDivider = findViewById;
                findViewById.setBackgroundColor(Theme.getColor(Theme.key_divider));
            }
        }
    }
}
