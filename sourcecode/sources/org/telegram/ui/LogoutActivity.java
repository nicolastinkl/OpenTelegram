package org.telegram.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AuthTokensHelper;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedPrefsHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_auth_logOut;
import org.telegram.tgnet.TLRPC$TL_auth_loggedOut;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.LogoutActivity;

/* loaded from: classes3.dex */
public class LogoutActivity extends BaseFragment {
    private static LoadingPopupView loadingPopupView;
    private int addAccountRow;
    private int alternativeHeaderRow;
    private int alternativeSectionRow;
    private int cacheRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int logoutRow;
    private int logoutSectionRow;
    private int passcodeRow;
    private int phoneRow;
    private int rowCount;
    private int supportRow;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.rowCount = 0;
        int i = 0 + 1;
        this.rowCount = i;
        this.alternativeSectionRow = 0;
        this.rowCount = i + 1;
        this.logoutRow = i;
        return true;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.getString("LogOutTitle", R.string.LogOutTitle));
        if (AndroidUtilities.isTablet()) {
            this.actionBar.setOccupyStatusBar(false);
        }
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.LogoutActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    LogoutActivity.this.finishFragment();
                }
            }
        });
        this.listAdapter = new ListAdapter(context);
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout2 = (FrameLayout) this.fragmentView;
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        recyclerListView.setVerticalScrollBarEnabled(false);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.LogoutActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i, float f, float f2) {
                LogoutActivity.this.lambda$createView$0(view, i, f, f2);
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view, int i, float f, float f2) {
        Integer num = null;
        if (i == this.addAccountRow) {
            int i2 = 0;
            for (int i3 = 0; i3 >= 0; i3--) {
                if (!UserConfig.getInstance(i3).isClientActivated()) {
                    i2++;
                    if (num == null) {
                        num = Integer.valueOf(i3);
                    }
                }
            }
            if (!UserConfig.hasPremiumOnAccounts()) {
                i2 += 0;
            }
            if (i2 > 0 && num != null) {
                presentFragment(new LoginActivity(num.intValue()));
                return;
            } else {
                if (UserConfig.hasPremiumOnAccounts()) {
                    return;
                }
                showDialog(new LimitReachedBottomSheet(this, getContext(), 7, this.currentAccount));
                return;
            }
        }
        if (i == this.passcodeRow) {
            presentFragment(PasscodeActivity.determineOpenFragment());
            return;
        }
        if (i == this.cacheRow) {
            presentFragment(new CacheControlActivity());
            return;
        }
        if (i == this.phoneRow) {
            presentFragment(new ActionIntroActivity(3));
            return;
        }
        if (i == this.supportRow) {
            showDialog(AlertsCreator.createSupportAlert(this, null));
        } else {
            if (i != this.logoutRow || getParentActivity() == null) {
                return;
            }
            showDialog(makeLogOutDialog(getParentActivity(), this.currentAccount));
        }
    }

    public static AlertDialog makeLogOutDialog(Context context, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(LocaleController.getString("AreYouSureLogout", R.string.AreYouSureLogout));
        int i2 = R.string.LogOut;
        builder.setTitle(LocaleController.getString("LogOut", i2));
        builder.setPositiveButton(LocaleController.getString("LogOut", i2), new AnonymousClass2(context, i));
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        AlertDialog create = builder.create();
        TextView textView = (TextView) create.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
        return create;
    }

    /* renamed from: org.telegram.ui.LogoutActivity$2, reason: invalid class name */
    class AnonymousClass2 implements DialogInterface.OnClickListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ int val$currentAccount;

        AnonymousClass2(Context context, int i) {
            this.val$context = context;
            this.val$currentAccount = i;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            LoadingPopupView unused = LogoutActivity.loadingPopupView = (LoadingPopupView) new XPopup.Builder(this.val$context).dismissOnBackPressed(Boolean.FALSE).isLightNavigationBar(true).asLoading(LocaleController.getString("JMTExiting", R.string.JMTExiting), LoadingPopupView.Style.ProgressBar).show();
            final MessagesController messagesController = MessagesController.getInstance(this.val$currentAccount);
            messagesController.unregistedPush();
            TLRPC$TL_auth_logOut tLRPC$TL_auth_logOut = new TLRPC$TL_auth_logOut();
            ConnectionsManager connectionsManager = messagesController.getConnectionsManager();
            final int i2 = this.val$currentAccount;
            final Context context = this.val$context;
            connectionsManager.sendRequest(tLRPC$TL_auth_logOut, new RequestDelegate() { // from class: org.telegram.ui.LogoutActivity$2$$ExternalSyntheticLambda1
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LogoutActivity.AnonymousClass2.lambda$onClick$1(MessagesController.this, i2, context, tLObject, tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClick$1(final MessagesController messagesController, final int i, final Context context, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            messagesController.getConnectionsManager().cleanup(false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LogoutActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LogoutActivity.AnonymousClass2.lambda$onClick$0(TLObject.this, messagesController, i, context);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onClick$0(TLObject tLObject, MessagesController messagesController, int i, Context context) {
            boolean z;
            LogoutActivity.loadingPopupView.dismiss();
            if (tLObject instanceof TLRPC$TL_auth_loggedOut) {
                TLRPC$TL_auth_loggedOut tLRPC$TL_auth_loggedOut = (TLRPC$TL_auth_loggedOut) tLObject;
                if (tLRPC$TL_auth_loggedOut.future_auth_token != null) {
                    AuthTokensHelper.addLogOutToken(tLRPC$TL_auth_loggedOut);
                }
                messagesController.getUserConfig().clearConfig();
                SharedPrefsHelper.cleanupAccount(i);
                ArrayList<NotificationCenter.NotificationCenterDelegate> observers = messagesController.getNotificationCenter().getObservers(NotificationCenter.appDidLogout);
                if (observers != null) {
                    int size = observers.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        if (observers.get(i2) instanceof LaunchActivity) {
                            z = false;
                            break;
                        }
                    }
                }
                z = true;
                if (z && UserConfig.selectedAccount == i) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= 1) {
                            i3 = -1;
                            break;
                        } else if (UserConfig.getInstance(i3).isClientActivated()) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                    if (i3 != -1) {
                        UserConfig.selectedAccount = i3;
                        UserConfig.getInstance(0).saveConfig(false);
                        LaunchActivity.clearFragments();
                    }
                }
                messagesController.getNotificationCenter().postNotificationName(NotificationCenter.appDidLogout, new Object[0]);
                messagesController.getMessagesStorage().cleanup(false);
                messagesController.cleanup();
                messagesController.getContactsController().deleteUnknownAppAccounts();
                return;
            }
            Toast.makeText(context, LocaleController.getString("JMTExitError", R.string.JMTExitError), 0).show();
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    protected void onDialogDismiss(Dialog dialog) {
        DownloadController.getInstance(this.currentAccount).checkAutodownloadSettings();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return LogoutActivity.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType != 1) {
                if (itemViewType == 3) {
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == LogoutActivity.this.logoutRow) {
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_text_RedRegular));
                        textSettingsCell.setText(LocaleController.getString("LogOutTitle", R.string.LogOutTitle), false);
                        return;
                    }
                    return;
                }
                if (itemViewType != 4) {
                    return;
                }
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                if (i == LogoutActivity.this.logoutSectionRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString("LogOutInfo", R.string.LogOutInfo));
                    return;
                }
                return;
            }
            TextDetailSettingsCell textDetailSettingsCell = (TextDetailSettingsCell) viewHolder.itemView;
            if (i != LogoutActivity.this.addAccountRow) {
                if (i != LogoutActivity.this.passcodeRow) {
                    if (i != LogoutActivity.this.cacheRow) {
                        if (i != LogoutActivity.this.phoneRow) {
                            if (i == LogoutActivity.this.supportRow) {
                                textDetailSettingsCell.setTextAndValueAndIcon(LocaleController.getString("ContactSupport", R.string.ContactSupport), LocaleController.getString("ContactSupportInfo", R.string.ContactSupportInfo), R.drawable.msg_help, false);
                                return;
                            }
                            return;
                        }
                        textDetailSettingsCell.setTextAndValueAndIcon(LocaleController.getString("ChangePhoneNumber", R.string.ChangePhoneNumber), LocaleController.getString("ChangePhoneNumberInfo", R.string.ChangePhoneNumberInfo), R.drawable.msg_newphone, true);
                        return;
                    }
                    textDetailSettingsCell.setTextAndValueAndIcon(LocaleController.getString("ClearCache", R.string.ClearCache), LocaleController.getString("ClearCacheInfo", R.string.ClearCacheInfo), R.drawable.msg_clearcache, true);
                    return;
                }
                textDetailSettingsCell.setTextAndValueAndIcon(LocaleController.getString("SetPasscode", R.string.SetPasscode), LocaleController.getString("SetPasscodeInfo", R.string.SetPasscodeInfo), R.drawable.msg_permissions, true);
                return;
            }
            textDetailSettingsCell.setTextAndValueAndIcon(LocaleController.getString("AddAnotherAccount", R.string.AddAnotherAccount), LocaleController.getString("AddAnotherAccountInfo", R.string.AddAnotherAccountInfo), R.drawable.msg_contact_add, true);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == LogoutActivity.this.addAccountRow || adapterPosition == LogoutActivity.this.passcodeRow || adapterPosition == LogoutActivity.this.cacheRow || adapterPosition == LogoutActivity.this.phoneRow || adapterPosition == LogoutActivity.this.supportRow || adapterPosition == LogoutActivity.this.logoutRow;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                view = new View(this.mContext);
            } else if (i == 1) {
                TextDetailSettingsCell textDetailSettingsCell = new TextDetailSettingsCell(this.mContext);
                textDetailSettingsCell.setMultilineDetail(true);
                textDetailSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textDetailSettingsCell;
            } else if (i == 2) {
                view = new ShadowSectionCell(this.mContext);
            } else if (i == 3) {
                view = new TextSettingsCell(this.mContext);
                view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                view = new TextInfoPrivacyCell(this.mContext);
                view.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
            }
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == LogoutActivity.this.alternativeHeaderRow) {
                return 0;
            }
            if (i == LogoutActivity.this.addAccountRow || i == LogoutActivity.this.passcodeRow || i == LogoutActivity.this.cacheRow || i == LogoutActivity.this.phoneRow || i == LogoutActivity.this.supportRow) {
                return 1;
            }
            if (i == LogoutActivity.this.alternativeSectionRow) {
                return 2;
            }
            return i == LogoutActivity.this.logoutRow ? 3 : 4;
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, HeaderCell.class, TextDetailSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        ActionBar actionBar = this.actionBar;
        int i = ThemeDescription.FLAG_BACKGROUND;
        int i2 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        int i3 = Theme.key_windowBackgroundGrayShadow;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_text_RedRegular));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlackText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"imageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayIcon));
        return arrayList;
    }
}
