package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_updateNotifySettings;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_forumTopic;
import org.telegram.tgnet.TLRPC$TL_inputNotifyForumTopic;
import org.telegram.tgnet.TLRPC$TL_inputPeerNotifySettings;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TopicExceptionCell;
import org.telegram.ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.NotificationsSettingsActivity;
import org.telegram.ui.ProfileNotificationsActivity;
import org.telegram.ui.TopicsFragment;
import org.telegram.ui.TopicsNotifySettingsFragments;

/* loaded from: classes3.dex */
public class TopicsNotifySettingsFragments extends BaseFragment {
    Adapter adapter;
    long dialogId;
    HashSet<Integer> exceptionsTopics;
    ArrayList<Item> items;
    RecyclerListView recyclerListView;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeException$0(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    public TopicsNotifySettingsFragments(Bundle bundle) {
        super(bundle);
        this.items = new ArrayList<>();
        this.exceptionsTopics = new HashSet<>();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.TopicsNotifySettingsFragments.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    TopicsNotifySettingsFragments.this.finishFragment();
                }
            }
        });
        this.actionBar.setTitle(LocaleController.getString(R.string.NotificationsExceptions));
        this.recyclerListView = new RecyclerListView(context);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setSupportsChangeAnimations(false);
        this.recyclerListView.setItemAnimator(defaultItemAnimator);
        this.recyclerListView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerListView recyclerListView = this.recyclerListView;
        Adapter adapter = new Adapter();
        this.adapter = adapter;
        recyclerListView.setAdapter(adapter);
        this.recyclerListView.setOnItemClickListener(new AnonymousClass2());
        frameLayout.addView(this.recyclerListView);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        return this.fragmentView;
    }

    /* renamed from: org.telegram.ui.TopicsNotifySettingsFragments$2, reason: invalid class name */
    class AnonymousClass2 implements RecyclerListView.OnItemClickListener {
        AnonymousClass2() {
        }

        @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
        public void onItemClick(View view, int i) {
            if (TopicsNotifySettingsFragments.this.items.get(i).viewType == 1) {
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", -TopicsNotifySettingsFragments.this.dialogId);
                bundle.putBoolean("for_select", true);
                TopicsFragment topicsFragment = new TopicsFragment(bundle);
                topicsFragment.setExcludeTopics(TopicsNotifySettingsFragments.this.exceptionsTopics);
                topicsFragment.setOnTopicSelectedListener(new TopicsFragment.OnTopicSelectedListener() { // from class: org.telegram.ui.TopicsNotifySettingsFragments$2$$ExternalSyntheticLambda2
                    @Override // org.telegram.ui.TopicsFragment.OnTopicSelectedListener
                    public final void onTopicSelected(TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
                        TopicsNotifySettingsFragments.AnonymousClass2.this.lambda$onItemClick$1(tLRPC$TL_forumTopic);
                    }
                });
                TopicsNotifySettingsFragments.this.presentFragment(topicsFragment);
            }
            if (TopicsNotifySettingsFragments.this.items.get(i).viewType == 2) {
                TLRPC$TL_forumTopic tLRPC$TL_forumTopic = TopicsNotifySettingsFragments.this.items.get(i).topic;
                Bundle bundle2 = new Bundle();
                bundle2.putLong("dialog_id", TopicsNotifySettingsFragments.this.dialogId);
                bundle2.putInt("topic_id", tLRPC$TL_forumTopic.id);
                bundle2.putBoolean("exception", false);
                ProfileNotificationsActivity profileNotificationsActivity = new ProfileNotificationsActivity(bundle2);
                profileNotificationsActivity.setDelegate(new AnonymousClass1(tLRPC$TL_forumTopic));
                TopicsNotifySettingsFragments.this.presentFragment(profileNotificationsActivity);
            }
            if (TopicsNotifySettingsFragments.this.items.get(i).viewType == 4) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TopicsNotifySettingsFragments.this.getParentActivity());
                builder.setTitle(LocaleController.getString("NotificationsDeleteAllExceptionTitle", R.string.NotificationsDeleteAllExceptionTitle));
                builder.setMessage(LocaleController.getString("NotificationsDeleteAllExceptionAlert", R.string.NotificationsDeleteAllExceptionAlert));
                builder.setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.TopicsNotifySettingsFragments$2$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        TopicsNotifySettingsFragments.AnonymousClass2.this.lambda$onItemClick$2(dialogInterface, i2);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                AlertDialog create = builder.create();
                TopicsNotifySettingsFragments.this.showDialog(create);
                TextView textView = (TextView) create.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1(final TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
            Bundle bundle = new Bundle();
            bundle.putLong("dialog_id", TopicsNotifySettingsFragments.this.dialogId);
            bundle.putInt("topic_id", tLRPC$TL_forumTopic.id);
            bundle.putBoolean("exception", true);
            ProfileNotificationsActivity profileNotificationsActivity = new ProfileNotificationsActivity(bundle);
            profileNotificationsActivity.setDelegate(new ProfileNotificationsActivity.ProfileNotificationsActivityDelegate() { // from class: org.telegram.ui.TopicsNotifySettingsFragments$2$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.ProfileNotificationsActivity.ProfileNotificationsActivityDelegate
                public final void didCreateNewException(NotificationsSettingsActivity.NotificationException notificationException) {
                    TopicsNotifySettingsFragments.AnonymousClass2.this.lambda$onItemClick$0(tLRPC$TL_forumTopic, notificationException);
                }

                @Override // org.telegram.ui.ProfileNotificationsActivity.ProfileNotificationsActivityDelegate
                public /* synthetic */ void didRemoveException(long j) {
                    ProfileNotificationsActivity.ProfileNotificationsActivityDelegate.CC.$default$didRemoveException(this, j);
                }
            });
            TopicsNotifySettingsFragments.this.presentFragment(profileNotificationsActivity);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0(TLRPC$TL_forumTopic tLRPC$TL_forumTopic, NotificationsSettingsActivity.NotificationException notificationException) {
            TopicsNotifySettingsFragments.this.exceptionsTopics.add(Integer.valueOf(tLRPC$TL_forumTopic.id));
            TopicsNotifySettingsFragments.this.updateRows();
        }

        /* renamed from: org.telegram.ui.TopicsNotifySettingsFragments$2$1, reason: invalid class name */
        class AnonymousClass1 implements ProfileNotificationsActivity.ProfileNotificationsActivityDelegate {
            final /* synthetic */ TLRPC$TL_forumTopic val$topic;

            @Override // org.telegram.ui.ProfileNotificationsActivity.ProfileNotificationsActivityDelegate
            public void didCreateNewException(NotificationsSettingsActivity.NotificationException notificationException) {
            }

            AnonymousClass1(TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
                this.val$topic = tLRPC$TL_forumTopic;
            }

            @Override // org.telegram.ui.ProfileNotificationsActivity.ProfileNotificationsActivityDelegate
            public void didRemoveException(long j) {
                TopicsNotifySettingsFragments.this.removeException(this.val$topic.id);
                final TLRPC$TL_forumTopic tLRPC$TL_forumTopic = this.val$topic;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.TopicsNotifySettingsFragments$2$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TopicsNotifySettingsFragments.AnonymousClass2.AnonymousClass1.this.lambda$didRemoveException$0(tLRPC$TL_forumTopic);
                    }
                }, 300L);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$didRemoveException$0(TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
                TopicsNotifySettingsFragments.this.exceptionsTopics.remove(Integer.valueOf(tLRPC$TL_forumTopic.id));
                TopicsNotifySettingsFragments.this.updateRows();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$2(DialogInterface dialogInterface, int i) {
            Iterator<Integer> it = TopicsNotifySettingsFragments.this.exceptionsTopics.iterator();
            while (it.hasNext()) {
                TopicsNotifySettingsFragments.this.removeException(it.next().intValue());
            }
            TopicsNotifySettingsFragments.this.exceptionsTopics.clear();
            TopicsNotifySettingsFragments.this.updateRows();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeException(int i) {
        getNotificationsController().getNotificationsSettingsFacade().clearPreference(this.dialogId, i);
        TLRPC$TL_account_updateNotifySettings tLRPC$TL_account_updateNotifySettings = new TLRPC$TL_account_updateNotifySettings();
        tLRPC$TL_account_updateNotifySettings.settings = new TLRPC$TL_inputPeerNotifySettings();
        TLRPC$TL_inputNotifyForumTopic tLRPC$TL_inputNotifyForumTopic = new TLRPC$TL_inputNotifyForumTopic();
        tLRPC$TL_inputNotifyForumTopic.peer = getMessagesController().getInputPeer(this.dialogId);
        tLRPC$TL_inputNotifyForumTopic.top_msg_id = i;
        tLRPC$TL_account_updateNotifySettings.peer = tLRPC$TL_inputNotifyForumTopic;
        getConnectionsManager().sendRequest(tLRPC$TL_account_updateNotifySettings, new RequestDelegate() { // from class: org.telegram.ui.TopicsNotifySettingsFragments$$ExternalSyntheticLambda0
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                TopicsNotifySettingsFragments.lambda$removeException$0(tLObject, tLRPC$TL_error);
            }
        });
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.dialogId = this.arguments.getLong("dialog_id");
        updateRows();
        return super.onFragmentCreate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRows() {
        ArrayList<? extends AdapterWithDiffUtils.Item> arrayList;
        int i = 0;
        int i2 = 1;
        TLRPC$TL_forumTopic tLRPC$TL_forumTopic = null;
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        byte b6 = 0;
        byte b7 = 0;
        byte b8 = 0;
        if ((this.isPaused || this.adapter == null) ? false : true) {
            arrayList = new ArrayList<>();
            arrayList.addAll(this.items);
        } else {
            arrayList = null;
        }
        this.items.clear();
        this.items.add(new Item(i2, tLRPC$TL_forumTopic));
        ArrayList<TLRPC$TL_forumTopic> topics = getMessagesController().getTopicsController().getTopics(-this.dialogId);
        if (topics != null) {
            int i3 = 0;
            while (i < topics.size()) {
                if (this.exceptionsTopics.contains(Integer.valueOf(topics.get(i).id))) {
                    this.items.add(new Item(2, topics.get(i)));
                    i3 = 1;
                }
                i++;
            }
            i = i3;
        }
        int i4 = 3;
        if (i != 0) {
            this.items.add(new Item(i4, b6 == true ? 1 : 0));
            this.items.add(new Item(4, b4 == true ? 1 : 0));
        }
        this.items.add(new Item(i4, b2 == true ? 1 : 0));
        Adapter adapter = this.adapter;
        if (adapter != null) {
            adapter.setItems(arrayList, this.items);
        }
    }

    public void setExceptions(HashSet<Integer> hashSet) {
        this.exceptionsTopics = hashSet;
    }

    private class Adapter extends AdapterWithDiffUtils {
        private Adapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            View view2;
            if (i == 1) {
                TextCell textCell = new TextCell(viewGroup.getContext());
                textCell.setTextAndIcon(LocaleController.getString("NotificationsAddAnException", R.string.NotificationsAddAnException), R.drawable.msg_contact_add, true);
                textCell.setColors(Theme.key_windowBackgroundWhiteBlueIcon, Theme.key_windowBackgroundWhiteBlueButton);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textCell;
            } else if (i == 2) {
                View topicExceptionCell = new TopicExceptionCell(viewGroup.getContext());
                topicExceptionCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = topicExceptionCell;
            } else if (i == 3) {
                view = new ShadowSectionCell(viewGroup.getContext());
            } else if (i == 4) {
                TextCell textCell2 = new TextCell(viewGroup.getContext());
                textCell2.setText(LocaleController.getString("NotificationsDeleteAllException", R.string.NotificationsDeleteAllException), false);
                textCell2.setColors(-1, Theme.key_text_RedRegular);
                textCell2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textCell2;
            } else {
                view2 = null;
                view2.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                return new RecyclerListView.Holder(view2);
            }
            view2 = view;
            view2.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(view2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (TopicsNotifySettingsFragments.this.items.get(i).viewType == 2) {
                TopicExceptionCell topicExceptionCell = (TopicExceptionCell) viewHolder.itemView;
                TopicsNotifySettingsFragments topicsNotifySettingsFragments = TopicsNotifySettingsFragments.this;
                topicExceptionCell.setTopic(topicsNotifySettingsFragments.dialogId, topicsNotifySettingsFragments.items.get(i).topic);
                boolean z = true;
                if (i != TopicsNotifySettingsFragments.this.items.size() - 1 && TopicsNotifySettingsFragments.this.items.get(i + 1).viewType != 2) {
                    z = false;
                }
                topicExceptionCell.drawDivider = z;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return TopicsNotifySettingsFragments.this.items.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return TopicsNotifySettingsFragments.this.items.get(i).viewType;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 1 || viewHolder.getItemViewType() == 2 || viewHolder.getItemViewType() == 4;
        }
    }

    private class Item extends AdapterWithDiffUtils.Item {
        final TLRPC$TL_forumTopic topic;

        private Item(TopicsNotifySettingsFragments topicsNotifySettingsFragments, int i, TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
            super(i, false);
            this.topic = tLRPC$TL_forumTopic;
        }

        public boolean equals(Object obj) {
            TLRPC$TL_forumTopic tLRPC$TL_forumTopic;
            if (this == obj) {
                return true;
            }
            if (obj == null || Item.class != obj.getClass()) {
                return false;
            }
            Item item = (Item) obj;
            if (this.viewType != item.viewType) {
                return false;
            }
            TLRPC$TL_forumTopic tLRPC$TL_forumTopic2 = this.topic;
            return tLRPC$TL_forumTopic2 == null || (tLRPC$TL_forumTopic = item.topic) == null || tLRPC$TL_forumTopic2.id == tLRPC$TL_forumTopic.id;
        }
    }
}
