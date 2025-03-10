package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_setContactSignUpNotification;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.NotificationsCheckCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

/* loaded from: classes3.dex */
public class NotificationsSettingsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private int accountsAllRow;
    private int accountsInfoRow;
    private int accountsSectionRow;
    private ListAdapter adapter;
    private int androidAutoAlertRow;
    private int badgeNumberMessagesRow;
    private int badgeNumberMutedRow;
    private int badgeNumberSection;
    private int badgeNumberSection2Row;
    private int badgeNumberShowRow;
    private int callsRingtoneRow;
    private int callsSection2Row;
    private int callsSectionRow;
    private int callsVibrateRow;
    private int channelsRow;
    private int contactJoinedRow;
    private int eventsSection2Row;
    private int eventsSectionRow;
    private int groupRow;
    private int inappPreviewRow;
    private int inappPriorityRow;
    private int inappSectionRow;
    private int inappSoundRow;
    private int inappVibrateRow;
    private int inchatSoundRow;
    private RecyclerListView listView;
    private int notificationsSection2Row;
    private int notificationsSectionRow;
    private int notificationsServiceConnectionRow;
    private int notificationsServiceRow;
    private int otherSection2Row;
    private int otherSectionRow;
    private int pinnedMessageRow;
    private int privateRow;
    private int repeatRow;
    private int resetNotificationsRow;
    private int resetNotificationsSectionRow;
    private int resetSection2Row;
    private int resetSectionRow;
    private boolean updateRepeatNotifications;
    private boolean updateRingtone;
    private boolean updateVibrate;
    private boolean reseting = false;
    private ArrayList<NotificationException> exceptionUsers = null;
    private ArrayList<NotificationException> exceptionChats = null;
    private ArrayList<NotificationException> exceptionChannels = null;
    private int rowCount = 0;

    public static class NotificationException {
        public long did;
        public boolean hasCustom;
        public int muteUntil;
        public int notify;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createView$5(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        MessagesController.getInstance(this.currentAccount).loadSignUpNotificationsSettings();
        loadExceptions();
        if (UserConfig.getActivatedAccountsCount() > 1) {
            int i = this.rowCount;
            int i2 = i + 1;
            this.rowCount = i2;
            this.accountsSectionRow = i;
            int i3 = i2 + 1;
            this.rowCount = i3;
            this.accountsAllRow = i2;
            this.rowCount = i3 + 1;
            this.accountsInfoRow = i3;
        } else {
            this.accountsSectionRow = -1;
            this.accountsAllRow = -1;
            this.accountsInfoRow = -1;
        }
        int i4 = this.rowCount;
        int i5 = i4 + 1;
        this.rowCount = i5;
        this.notificationsSectionRow = i4;
        int i6 = i5 + 1;
        this.rowCount = i6;
        this.privateRow = i5;
        int i7 = i6 + 1;
        this.rowCount = i7;
        this.groupRow = i6;
        int i8 = i7 + 1;
        this.rowCount = i8;
        this.channelsRow = i7;
        int i9 = i8 + 1;
        this.rowCount = i9;
        this.notificationsSection2Row = i8;
        int i10 = i9 + 1;
        this.rowCount = i10;
        this.callsSectionRow = i9;
        int i11 = i10 + 1;
        this.rowCount = i11;
        this.callsVibrateRow = i10;
        int i12 = i11 + 1;
        this.rowCount = i12;
        this.callsRingtoneRow = i11;
        this.rowCount = i12 + 1;
        this.eventsSection2Row = i12;
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.notificationsSettingsUpdated);
        return super.onFragmentCreate();
    }

    private void loadExceptions() {
        MessagesStorage.getInstance(this.currentAccount).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsSettingsActivity.this.lambda$loadExceptions$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0130, code lost:
    
        if (r4.deleted != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x015d, code lost:
    
        r7 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x015b, code lost:
    
        if (r4.deleted != false) goto L38;
     */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x028d A[LOOP:3: B:121:0x028b->B:122:0x028d, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0234  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$loadExceptions$1() {
        /*
            Method dump skipped, instructions count: 732
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.NotificationsSettingsActivity.lambda$loadExceptions$1():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadExceptions$0(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, ArrayList arrayList6) {
        MessagesController.getInstance(this.currentAccount).putUsers(arrayList, true);
        MessagesController.getInstance(this.currentAccount).putChats(arrayList2, true);
        MessagesController.getInstance(this.currentAccount).putEncryptedChats(arrayList3, true);
        this.exceptionUsers = arrayList4;
        this.exceptionChats = arrayList5;
        this.exceptionChannels = arrayList6;
        this.adapter.notifyItemChanged(this.privateRow);
        this.adapter.notifyItemChanged(this.groupRow);
        this.adapter.notifyItemChanged(this.channelsRow);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("NotificationsAndSounds", R.string.NotificationsAndSounds));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.NotificationsSettingsActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    NotificationsSettingsActivity.this.finishFragment();
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        FrameLayout frameLayout2 = frameLayout;
        frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        RecyclerListView recyclerListView = new RecyclerListView(context);
        this.listView = recyclerListView;
        recyclerListView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(new LinearLayoutManager(this, context, 1, false) { // from class: org.telegram.ui.NotificationsSettingsActivity.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        });
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView2 = this.listView;
        ListAdapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView2.setAdapter(listAdapter);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda9
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
                NotificationsSettingsActivity.this.lambda$createView$8(view, i, f, f2);
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$8(View view, final int i, float f, float f2) {
        ArrayList<NotificationException> arrayList;
        if (getParentActivity() == null) {
            return;
        }
        int i2 = this.privateRow;
        int i3 = 2;
        boolean z = false;
        z = false;
        z = false;
        z = false;
        z = false;
        z = false;
        z = false;
        if (i == i2 || i == this.groupRow || i == this.channelsRow) {
            if (i == i2) {
                arrayList = this.exceptionUsers;
                i3 = 1;
            } else if (i == this.groupRow) {
                arrayList = this.exceptionChats;
                i3 = 0;
            } else {
                arrayList = this.exceptionChannels;
            }
            if (arrayList == null) {
                return;
            }
            NotificationsCheckCell notificationsCheckCell = (NotificationsCheckCell) view;
            boolean isGlobalNotificationsEnabled = getNotificationsController().isGlobalNotificationsEnabled(i3);
            if ((LocaleController.isRTL && f <= AndroidUtilities.dp(76.0f)) || (!LocaleController.isRTL && f >= view.getMeasuredWidth() - AndroidUtilities.dp(76.0f))) {
                getNotificationsController().setGlobalNotificationsEnabled(i3, !isGlobalNotificationsEnabled ? 0 : Integer.MAX_VALUE);
                showExceptionsAlert(i);
                notificationsCheckCell.setChecked(!isGlobalNotificationsEnabled, 0);
                this.adapter.notifyItemChanged(i);
            } else {
                presentFragment(new NotificationsCustomSettingsActivity(i3, arrayList));
            }
            z = isGlobalNotificationsEnabled;
        } else {
            Parcelable parcelable = null;
            parcelable = null;
            if (i == this.callsRingtoneRow) {
                try {
                    SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(this.currentAccount);
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.TYPE", 1);
                    intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                    intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", true);
                    intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(1));
                    Uri uri = Settings.System.DEFAULT_RINGTONE_URI;
                    String path = uri != null ? uri.getPath() : null;
                    String string = notificationsSettings.getString("CallsRingtonePath", path);
                    if (string != null && !string.equals("NoSound")) {
                        parcelable = string.equals(path) ? uri : Uri.parse(string);
                    }
                    intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", parcelable);
                    startActivityForResult(intent, i);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else if (i == this.resetNotificationsRow) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("ResetNotificationsAlertTitle", R.string.ResetNotificationsAlertTitle));
                builder.setMessage(LocaleController.getString("ResetNotificationsAlert", R.string.ResetNotificationsAlert));
                builder.setPositiveButton(LocaleController.getString("Reset", R.string.Reset), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        NotificationsSettingsActivity.this.lambda$createView$4(dialogInterface, i4);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                AlertDialog create = builder.create();
                showDialog(create);
                TextView textView = (TextView) create.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                }
            } else if (i == this.inappSoundRow) {
                SharedPreferences notificationsSettings2 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit = notificationsSettings2.edit();
                z = notificationsSettings2.getBoolean("EnableInAppSounds", true);
                edit.putBoolean("EnableInAppSounds", !z);
                edit.commit();
            } else if (i == this.inappVibrateRow) {
                SharedPreferences notificationsSettings3 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit2 = notificationsSettings3.edit();
                z = notificationsSettings3.getBoolean("EnableInAppVibrate", true);
                edit2.putBoolean("EnableInAppVibrate", !z);
                edit2.commit();
            } else if (i == this.inappPreviewRow) {
                SharedPreferences notificationsSettings4 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit3 = notificationsSettings4.edit();
                z = notificationsSettings4.getBoolean("EnableInAppPreview", true);
                edit3.putBoolean("EnableInAppPreview", !z);
                edit3.commit();
            } else if (i == this.inchatSoundRow) {
                SharedPreferences notificationsSettings5 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit4 = notificationsSettings5.edit();
                z = notificationsSettings5.getBoolean("EnableInChatSound", true);
                edit4.putBoolean("EnableInChatSound", !z);
                edit4.commit();
                getNotificationsController().setInChatSoundEnabled(!z);
            } else if (i == this.inappPriorityRow) {
                SharedPreferences notificationsSettings6 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit5 = notificationsSettings6.edit();
                z = notificationsSettings6.getBoolean("EnableInAppPriority", false);
                edit5.putBoolean("EnableInAppPriority", !z);
                edit5.commit();
            } else if (i == this.contactJoinedRow) {
                SharedPreferences notificationsSettings7 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit6 = notificationsSettings7.edit();
                z = notificationsSettings7.getBoolean("EnableContactJoined", true);
                MessagesController.getInstance(this.currentAccount).enableJoined = !z;
                edit6.putBoolean("EnableContactJoined", !z);
                edit6.commit();
                TLRPC$TL_account_setContactSignUpNotification tLRPC$TL_account_setContactSignUpNotification = new TLRPC$TL_account_setContactSignUpNotification();
                tLRPC$TL_account_setContactSignUpNotification.silent = z;
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_account_setContactSignUpNotification, new RequestDelegate() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda8
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        NotificationsSettingsActivity.lambda$createView$5(tLObject, tLRPC$TL_error);
                    }
                });
            } else if (i == this.pinnedMessageRow) {
                SharedPreferences notificationsSettings8 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit7 = notificationsSettings8.edit();
                z = notificationsSettings8.getBoolean("PinnedMessages", true);
                edit7.putBoolean("PinnedMessages", !z);
                edit7.commit();
            } else if (i == this.androidAutoAlertRow) {
                SharedPreferences notificationsSettings9 = MessagesController.getNotificationsSettings(this.currentAccount);
                SharedPreferences.Editor edit8 = notificationsSettings9.edit();
                z = notificationsSettings9.getBoolean("EnableAutoNotifications", false);
                edit8.putBoolean("EnableAutoNotifications", !z);
                edit8.commit();
            } else if (i == this.badgeNumberShowRow) {
                SharedPreferences.Editor edit9 = MessagesController.getNotificationsSettings(this.currentAccount).edit();
                z = getNotificationsController().showBadgeNumber;
                getNotificationsController().showBadgeNumber = !z;
                edit9.putBoolean("badgeNumber", getNotificationsController().showBadgeNumber);
                edit9.commit();
                getNotificationsController().updateBadge();
            } else if (i == this.badgeNumberMutedRow) {
                SharedPreferences.Editor edit10 = MessagesController.getNotificationsSettings(this.currentAccount).edit();
                z = getNotificationsController().showBadgeMuted;
                getNotificationsController().showBadgeMuted = !z;
                edit10.putBoolean("badgeNumberMuted", getNotificationsController().showBadgeMuted);
                edit10.commit();
                getNotificationsController().updateBadge();
                getMessagesStorage().updateMutedDialogsFiltersCounters();
            } else if (i == this.badgeNumberMessagesRow) {
                SharedPreferences.Editor edit11 = MessagesController.getNotificationsSettings(this.currentAccount).edit();
                z = getNotificationsController().showBadgeMessages;
                getNotificationsController().showBadgeMessages = !z;
                edit11.putBoolean("badgeNumberMessages", getNotificationsController().showBadgeMessages);
                edit11.commit();
                getNotificationsController().updateBadge();
            } else if (i == this.notificationsServiceConnectionRow) {
                SharedPreferences notificationsSettings10 = MessagesController.getNotificationsSettings(this.currentAccount);
                boolean z2 = notificationsSettings10.getBoolean("pushConnection", getMessagesController().backgroundConnection);
                SharedPreferences.Editor edit12 = notificationsSettings10.edit();
                edit12.putBoolean("pushConnection", !z2);
                edit12.commit();
                if (!z2) {
                    ConnectionsManager.getInstance(this.currentAccount).setPushConnectionEnabled(true);
                } else {
                    ConnectionsManager.getInstance(this.currentAccount).setPushConnectionEnabled(false);
                }
                z = z2;
            } else if (i == this.accountsAllRow) {
                SharedPreferences globalNotificationsSettings = MessagesController.getGlobalNotificationsSettings();
                boolean z3 = globalNotificationsSettings.getBoolean("AllAccounts", true);
                SharedPreferences.Editor edit13 = globalNotificationsSettings.edit();
                edit13.putBoolean("AllAccounts", !z3);
                edit13.commit();
                SharedConfig.showNotificationsForAllAccounts = !z3;
                for (int i4 = 0; i4 < 1; i4++) {
                    if (SharedConfig.showNotificationsForAllAccounts) {
                        NotificationsController.getInstance(i4).showNotifications();
                    } else if (i4 == this.currentAccount) {
                        NotificationsController.getInstance(i4).showNotifications();
                    } else {
                        NotificationsController.getInstance(i4).hideNotifications();
                    }
                }
                z = z3;
            } else if (i == this.notificationsServiceRow) {
                SharedPreferences notificationsSettings11 = MessagesController.getNotificationsSettings(this.currentAccount);
                z = notificationsSettings11.getBoolean("pushService", getMessagesController().keepAliveService);
                SharedPreferences.Editor edit14 = notificationsSettings11.edit();
                edit14.putBoolean("pushService", !z);
                edit14.commit();
                ApplicationLoader.startPushService();
            } else if (i == this.callsVibrateRow) {
                if (getParentActivity() == null) {
                    return;
                } else {
                    showDialog(AlertsCreator.createVibrationSelectDialog(getParentActivity(), 0L, 0, i == this.callsVibrateRow ? "vibrate_calls" : null, new Runnable() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationsSettingsActivity.this.lambda$createView$6(i);
                        }
                    }));
                }
            } else if (i == this.repeatRow) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getParentActivity());
                builder2.setTitle(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications));
                builder2.setItems(new CharSequence[]{LocaleController.getString("RepeatDisabled", R.string.RepeatDisabled), LocaleController.formatPluralString("Minutes", 5, new Object[0]), LocaleController.formatPluralString("Minutes", 10, new Object[0]), LocaleController.formatPluralString("Minutes", 30, new Object[0]), LocaleController.formatPluralString("Hours", 1, new Object[0]), LocaleController.formatPluralString("Hours", 2, new Object[0]), LocaleController.formatPluralString("Hours", 4, new Object[0])}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i5) {
                        NotificationsSettingsActivity.this.lambda$createView$7(i, dialogInterface, i5);
                    }
                });
                builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                showDialog(builder2.create());
            }
        }
        if (view instanceof TextCheckCell) {
            ((TextCheckCell) view).setChecked(!z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(DialogInterface dialogInterface, int i) {
        if (this.reseting) {
            return;
        }
        this.reseting = true;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_account_resetNotifySettings
            public static int constructor = -612493497;

            @Override // org.telegram.tgnet.TLObject
            public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i2, boolean z) {
                return TLRPC$Bool.TLdeserialize(abstractSerializedData, i2, z);
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
            }
        }, new RequestDelegate() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda7
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                NotificationsSettingsActivity.this.lambda$createView$3(tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsSettingsActivity.this.lambda$createView$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2() {
        getMessagesController().enableJoined = true;
        this.reseting = false;
        SharedPreferences.Editor edit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
        edit.clear();
        edit.commit();
        this.exceptionChats.clear();
        this.exceptionUsers.clear();
        this.adapter.notifyDataSetChanged();
        if (getParentActivity() != null) {
            Toast.makeText(getParentActivity(), LocaleController.getString("ResetNotificationsText", R.string.ResetNotificationsText), 0).show();
        }
        getMessagesStorage().updateMutedDialogsFiltersCounters();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$6(int i) {
        this.updateVibrate = true;
        this.adapter.notifyItemChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$7(int i, DialogInterface dialogInterface, int i2) {
        MessagesController.getNotificationsSettings(this.currentAccount).edit().putInt("repeat_messages", i2 != 1 ? i2 == 2 ? 10 : i2 == 3 ? 30 : i2 == 4 ? 60 : i2 == 5 ? 120 : i2 == 6 ? 240 : 0 : 5).commit();
        this.updateRepeatNotifications = true;
        this.adapter.notifyItemChanged(i);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        Ringtone ringtone;
        if (i2 == -1) {
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            String str = null;
            if (uri != null && (ringtone = RingtoneManager.getRingtone(getParentActivity(), uri)) != null) {
                if (i == this.callsRingtoneRow) {
                    if (uri.equals(Settings.System.DEFAULT_RINGTONE_URI)) {
                        str = LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone);
                    } else {
                        str = ringtone.getTitle(getParentActivity());
                    }
                } else if (uri.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
                    str = LocaleController.getString("SoundDefault", R.string.SoundDefault);
                } else {
                    str = ringtone.getTitle(getParentActivity());
                }
                ringtone.stop();
            }
            SharedPreferences.Editor edit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
            if (i == this.callsRingtoneRow) {
                if (str != null && uri != null) {
                    edit.putString("CallsRingtone", str);
                    edit.putString("CallsRingtonePath", uri.toString());
                } else {
                    edit.putString("CallsRingtone", "NoSound");
                    edit.putString("CallsRingtonePath", "NoSound");
                }
                this.updateRingtone = true;
            }
            edit.commit();
            this.adapter.notifyItemChanged(i);
        }
    }

    private void showExceptionsAlert(int i) {
        final ArrayList<NotificationException> arrayList;
        String formatPluralString;
        if (i == this.privateRow) {
            arrayList = this.exceptionUsers;
            if (arrayList != null && !arrayList.isEmpty()) {
                formatPluralString = LocaleController.formatPluralString("ChatsException", arrayList.size(), new Object[0]);
            }
            formatPluralString = null;
        } else if (i == this.groupRow) {
            arrayList = this.exceptionChats;
            if (arrayList != null && !arrayList.isEmpty()) {
                formatPluralString = LocaleController.formatPluralString("Groups", arrayList.size(), new Object[0]);
            }
            formatPluralString = null;
        } else {
            arrayList = this.exceptionChannels;
            if (arrayList != null && !arrayList.isEmpty()) {
                formatPluralString = LocaleController.formatPluralString("Channels", arrayList.size(), new Object[0]);
            }
            formatPluralString = null;
        }
        if (formatPluralString == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        if (arrayList.size() == 1) {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsExceptionsSingleAlert", R.string.NotificationsExceptionsSingleAlert, formatPluralString)));
        } else {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsExceptionsAlert", R.string.NotificationsExceptionsAlert, formatPluralString)));
        }
        builder.setTitle(LocaleController.getString("NotificationsExceptions", R.string.NotificationsExceptions));
        builder.setNeutralButton(LocaleController.getString("ViewExceptions", R.string.ViewExceptions), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.NotificationsSettingsActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                NotificationsSettingsActivity.this.lambda$showExceptionsAlert$9(arrayList, dialogInterface, i2);
            }
        });
        builder.setNegativeButton(LocaleController.getString("OK", R.string.OK), null);
        showDialog(builder.create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showExceptionsAlert$9(ArrayList arrayList, DialogInterface dialogInterface, int i) {
        presentFragment(new NotificationsCustomSettingsActivity(-1, arrayList));
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        ListAdapter listAdapter = this.adapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.notificationsSettingsUpdated) {
            this.adapter.notifyDataSetChanged();
        }
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return (adapterPosition == NotificationsSettingsActivity.this.notificationsSectionRow || adapterPosition == NotificationsSettingsActivity.this.notificationsSection2Row || adapterPosition == NotificationsSettingsActivity.this.inappSectionRow || adapterPosition == NotificationsSettingsActivity.this.eventsSectionRow || adapterPosition == NotificationsSettingsActivity.this.otherSectionRow || adapterPosition == NotificationsSettingsActivity.this.resetSectionRow || adapterPosition == NotificationsSettingsActivity.this.badgeNumberSection || adapterPosition == NotificationsSettingsActivity.this.otherSection2Row || adapterPosition == NotificationsSettingsActivity.this.resetSection2Row || adapterPosition == NotificationsSettingsActivity.this.callsSection2Row || adapterPosition == NotificationsSettingsActivity.this.callsSectionRow || adapterPosition == NotificationsSettingsActivity.this.badgeNumberSection2Row || adapterPosition == NotificationsSettingsActivity.this.accountsSectionRow || adapterPosition == NotificationsSettingsActivity.this.accountsInfoRow || adapterPosition == NotificationsSettingsActivity.this.resetNotificationsSectionRow || adapterPosition == NotificationsSettingsActivity.this.eventsSection2Row) ? false : true;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return NotificationsSettingsActivity.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            if (i == 0) {
                headerCell = new HeaderCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 1) {
                headerCell = new TextCheckCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 2) {
                headerCell = new TextDetailSettingsCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 3) {
                headerCell = new NotificationsCheckCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 4) {
                headerCell = new ShadowSectionCell(this.mContext);
            } else if (i == 5) {
                headerCell = new TextSettingsCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                headerCell = new TextInfoPrivacyCell(this.mContext);
                headerCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            }
            return new RecyclerListView.Holder(headerCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String string;
            ArrayList arrayList;
            int i2;
            String formatPluralString;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i != NotificationsSettingsActivity.this.notificationsSectionRow) {
                        if (i != NotificationsSettingsActivity.this.inappSectionRow) {
                            if (i != NotificationsSettingsActivity.this.eventsSectionRow) {
                                if (i != NotificationsSettingsActivity.this.otherSectionRow) {
                                    if (i != NotificationsSettingsActivity.this.resetSectionRow) {
                                        if (i != NotificationsSettingsActivity.this.callsSectionRow) {
                                            if (i != NotificationsSettingsActivity.this.badgeNumberSection) {
                                                if (i == NotificationsSettingsActivity.this.accountsSectionRow) {
                                                    headerCell.setText(LocaleController.getString("ShowNotificationsFor", R.string.ShowNotificationsFor));
                                                    break;
                                                }
                                            } else {
                                                headerCell.setText(LocaleController.getString("BadgeNumber", R.string.BadgeNumber));
                                                break;
                                            }
                                        } else {
                                            headerCell.setText(LocaleController.getString("VoipNotificationSettings", R.string.VoipNotificationSettings));
                                            break;
                                        }
                                    } else {
                                        headerCell.setText(LocaleController.getString("Reset", R.string.Reset));
                                        break;
                                    }
                                } else {
                                    headerCell.setText(LocaleController.getString("NotificationsOther", R.string.NotificationsOther));
                                    break;
                                }
                            } else {
                                headerCell.setText(LocaleController.getString("Events", R.string.Events));
                                break;
                            }
                        } else {
                            headerCell.setText(LocaleController.getString("InAppNotifications", R.string.InAppNotifications));
                            break;
                        }
                    } else {
                        headerCell.setText(LocaleController.getString("NotificationsForChats", R.string.NotificationsForChats));
                        break;
                    }
                    break;
                case 1:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(((BaseFragment) NotificationsSettingsActivity.this).currentAccount);
                    if (i != NotificationsSettingsActivity.this.inappSoundRow) {
                        if (i != NotificationsSettingsActivity.this.inappVibrateRow) {
                            if (i != NotificationsSettingsActivity.this.inappPreviewRow) {
                                if (i != NotificationsSettingsActivity.this.inappPriorityRow) {
                                    if (i != NotificationsSettingsActivity.this.contactJoinedRow) {
                                        if (i != NotificationsSettingsActivity.this.pinnedMessageRow) {
                                            if (i != NotificationsSettingsActivity.this.androidAutoAlertRow) {
                                                if (i != NotificationsSettingsActivity.this.notificationsServiceRow) {
                                                    if (i != NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                                                        if (i == NotificationsSettingsActivity.this.badgeNumberShowRow) {
                                                            textCheckCell.setTextAndCheck(LocaleController.getString("BadgeNumberShow", R.string.BadgeNumberShow), NotificationsSettingsActivity.this.getNotificationsController().showBadgeNumber, true);
                                                            break;
                                                        } else if (i == NotificationsSettingsActivity.this.badgeNumberMutedRow) {
                                                            textCheckCell.setTextAndCheck(LocaleController.getString("BadgeNumberMutedChats", R.string.BadgeNumberMutedChats), NotificationsSettingsActivity.this.getNotificationsController().showBadgeMuted, true);
                                                            break;
                                                        } else if (i == NotificationsSettingsActivity.this.badgeNumberMessagesRow) {
                                                            textCheckCell.setTextAndCheck(LocaleController.getString("BadgeNumberUnread", R.string.BadgeNumberUnread), NotificationsSettingsActivity.this.getNotificationsController().showBadgeMessages, false);
                                                            break;
                                                        } else if (i != NotificationsSettingsActivity.this.inchatSoundRow) {
                                                            if (i != NotificationsSettingsActivity.this.callsVibrateRow) {
                                                                if (i == NotificationsSettingsActivity.this.accountsAllRow) {
                                                                    textCheckCell.setTextAndCheck(LocaleController.getString("AllAccounts", R.string.AllAccounts), MessagesController.getGlobalNotificationsSettings().getBoolean("AllAccounts", true), false);
                                                                    break;
                                                                }
                                                            } else {
                                                                textCheckCell.setTextAndCheck(LocaleController.getString("Vibrate", R.string.Vibrate), notificationsSettings.getBoolean("EnableCallVibrate", true), true);
                                                                break;
                                                            }
                                                        } else {
                                                            textCheckCell.setTextAndCheck(LocaleController.getString("InChatSound", R.string.InChatSound), notificationsSettings.getBoolean("EnableInChatSound", true), true);
                                                            break;
                                                        }
                                                    } else {
                                                        textCheckCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsServiceConnection", R.string.NotificationsServiceConnection), LocaleController.getString("NotificationsServiceConnectionInfo", R.string.NotificationsServiceConnectionInfo), notificationsSettings.getBoolean("pushConnection", NotificationsSettingsActivity.this.getMessagesController().backgroundConnection), true, true);
                                                        break;
                                                    }
                                                } else {
                                                    textCheckCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsService", R.string.NotificationsService), LocaleController.getString("NotificationsServiceInfo", R.string.NotificationsServiceInfo), notificationsSettings.getBoolean("pushService", NotificationsSettingsActivity.this.getMessagesController().keepAliveService), true, true);
                                                    break;
                                                }
                                            } else {
                                                textCheckCell.setTextAndCheck("Android Auto", notificationsSettings.getBoolean("EnableAutoNotifications", false), true);
                                                break;
                                            }
                                        } else {
                                            textCheckCell.setTextAndCheck(LocaleController.getString("PinnedMessages", R.string.PinnedMessages), notificationsSettings.getBoolean("PinnedMessages", true), false);
                                            break;
                                        }
                                    } else {
                                        textCheckCell.setTextAndCheck(LocaleController.getString("ContactJoined", R.string.ContactJoined), notificationsSettings.getBoolean("EnableContactJoined", true), true);
                                        break;
                                    }
                                } else {
                                    textCheckCell.setTextAndCheck(LocaleController.getString("NotificationsImportance", R.string.NotificationsImportance), notificationsSettings.getBoolean("EnableInAppPriority", false), false);
                                    break;
                                }
                            } else {
                                textCheckCell.setTextAndCheck(LocaleController.getString("InAppPreview", R.string.InAppPreview), notificationsSettings.getBoolean("EnableInAppPreview", true), true);
                                break;
                            }
                        } else {
                            textCheckCell.setTextAndCheck(LocaleController.getString("InAppVibrate", R.string.InAppVibrate), notificationsSettings.getBoolean("EnableInAppVibrate", true), true);
                            break;
                        }
                    } else {
                        textCheckCell.setTextAndCheck(LocaleController.getString("InAppSounds", R.string.InAppSounds), notificationsSettings.getBoolean("EnableInAppSounds", true), true);
                        break;
                    }
                    break;
                case 2:
                    TextDetailSettingsCell textDetailSettingsCell = (TextDetailSettingsCell) viewHolder.itemView;
                    textDetailSettingsCell.setMultilineDetail(true);
                    if (i == NotificationsSettingsActivity.this.resetNotificationsRow) {
                        textDetailSettingsCell.setTextAndValue(LocaleController.getString("ResetAllNotifications", R.string.ResetAllNotifications), LocaleController.getString("UndoAllCustom", R.string.UndoAllCustom), false);
                        break;
                    }
                    break;
                case 3:
                    NotificationsCheckCell notificationsCheckCell = (NotificationsCheckCell) viewHolder.itemView;
                    SharedPreferences notificationsSettings2 = MessagesController.getNotificationsSettings(((BaseFragment) NotificationsSettingsActivity.this).currentAccount);
                    int currentTime = ConnectionsManager.getInstance(((BaseFragment) NotificationsSettingsActivity.this).currentAccount).getCurrentTime();
                    if (i != NotificationsSettingsActivity.this.privateRow) {
                        if (i == NotificationsSettingsActivity.this.groupRow) {
                            string = LocaleController.getString("NotificationsGroups", R.string.NotificationsGroups);
                            arrayList = NotificationsSettingsActivity.this.exceptionChats;
                            i2 = notificationsSettings2.getInt("EnableGroup2", 0);
                        } else {
                            string = LocaleController.getString("NotificationsChannels", R.string.NotificationsChannels);
                            arrayList = NotificationsSettingsActivity.this.exceptionChannels;
                            i2 = notificationsSettings2.getInt("EnableChannel2", 0);
                        }
                    } else {
                        string = LocaleController.getString("NotificationsPrivateChats", R.string.NotificationsPrivateChats);
                        arrayList = NotificationsSettingsActivity.this.exceptionUsers;
                        i2 = notificationsSettings2.getInt("EnableAll2", 0);
                    }
                    boolean z = i2 < currentTime;
                    int i3 = (!z && i2 - 31536000 < currentTime) ? 2 : 0;
                    StringBuilder sb = new StringBuilder();
                    if (arrayList != null && !arrayList.isEmpty()) {
                        z = i2 < currentTime;
                        if (z) {
                            sb.append(LocaleController.getString("NotificationsOn", R.string.NotificationsOn));
                        } else if (i2 - 31536000 >= currentTime) {
                            sb.append(LocaleController.getString("NotificationsOff", R.string.NotificationsOff));
                        } else {
                            sb.append(LocaleController.formatString("NotificationsOffUntil", R.string.NotificationsOffUntil, LocaleController.stringForMessageListDate(i2)));
                        }
                        if (sb.length() != 0) {
                            sb.append(", ");
                        }
                        sb.append(LocaleController.formatPluralString("Exception", arrayList.size(), new Object[0]));
                    } else {
                        sb.append(LocaleController.getString("TapToChange", R.string.TapToChange));
                    }
                    notificationsCheckCell.setTextAndValueAndCheck(string, sb, z, i3, i != NotificationsSettingsActivity.this.channelsRow);
                    break;
                case 4:
                    if (i == NotificationsSettingsActivity.this.resetNotificationsSectionRow) {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        break;
                    } else {
                        viewHolder.itemView.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        break;
                    }
                case 5:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    SharedPreferences notificationsSettings3 = MessagesController.getNotificationsSettings(((BaseFragment) NotificationsSettingsActivity.this).currentAccount);
                    if (i != NotificationsSettingsActivity.this.callsRingtoneRow) {
                        if (i != NotificationsSettingsActivity.this.callsVibrateRow) {
                            if (i == NotificationsSettingsActivity.this.repeatRow) {
                                int i4 = notificationsSettings3.getInt("repeat_messages", 60);
                                if (i4 == 0) {
                                    formatPluralString = LocaleController.getString("RepeatNotificationsNever", R.string.RepeatNotificationsNever);
                                } else if (i4 < 60) {
                                    formatPluralString = LocaleController.formatPluralString("Minutes", i4, new Object[0]);
                                } else {
                                    formatPluralString = LocaleController.formatPluralString("Hours", i4 / 60, new Object[0]);
                                }
                                textSettingsCell.setTextAndValue(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications), formatPluralString, NotificationsSettingsActivity.this.updateRepeatNotifications, false);
                                NotificationsSettingsActivity.this.updateRepeatNotifications = false;
                                break;
                            }
                        } else {
                            int i5 = notificationsSettings3.getInt("vibrate_calls", 0);
                            if (i5 == 0) {
                                textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), NotificationsSettingsActivity.this.updateVibrate, true);
                            } else if (i5 == 1) {
                                textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Short", R.string.Short), NotificationsSettingsActivity.this.updateVibrate, true);
                            } else if (i5 == 2) {
                                textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), NotificationsSettingsActivity.this.updateVibrate, true);
                            } else if (i5 == 3) {
                                textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Long", R.string.Long), NotificationsSettingsActivity.this.updateVibrate, true);
                            } else if (i5 == 4) {
                                textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent), NotificationsSettingsActivity.this.updateVibrate, true);
                            }
                            NotificationsSettingsActivity.this.updateVibrate = false;
                            break;
                        }
                    } else {
                        String string2 = notificationsSettings3.getString("CallsRingtone", LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone));
                        if (string2.equals("NoSound")) {
                            string2 = LocaleController.getString("NoSound", R.string.NoSound);
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("VoipSettingsRingtone", R.string.VoipSettingsRingtone), string2, NotificationsSettingsActivity.this.updateRingtone, false);
                        NotificationsSettingsActivity.this.updateRingtone = false;
                        break;
                    }
                    break;
                case 6:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == NotificationsSettingsActivity.this.accountsInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("ShowNotificationsForInfo", R.string.ShowNotificationsForInfo));
                        break;
                    }
                    break;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == NotificationsSettingsActivity.this.eventsSectionRow || i == NotificationsSettingsActivity.this.otherSectionRow || i == NotificationsSettingsActivity.this.resetSectionRow || i == NotificationsSettingsActivity.this.callsSectionRow || i == NotificationsSettingsActivity.this.badgeNumberSection || i == NotificationsSettingsActivity.this.inappSectionRow || i == NotificationsSettingsActivity.this.notificationsSectionRow || i == NotificationsSettingsActivity.this.accountsSectionRow) {
                return 0;
            }
            if (i == NotificationsSettingsActivity.this.inappSoundRow || i == NotificationsSettingsActivity.this.inappVibrateRow || i == NotificationsSettingsActivity.this.notificationsServiceConnectionRow || i == NotificationsSettingsActivity.this.inappPreviewRow || i == NotificationsSettingsActivity.this.contactJoinedRow || i == NotificationsSettingsActivity.this.pinnedMessageRow || i == NotificationsSettingsActivity.this.notificationsServiceRow || i == NotificationsSettingsActivity.this.badgeNumberMutedRow || i == NotificationsSettingsActivity.this.badgeNumberMessagesRow || i == NotificationsSettingsActivity.this.badgeNumberShowRow || i == NotificationsSettingsActivity.this.inappPriorityRow || i == NotificationsSettingsActivity.this.inchatSoundRow || i == NotificationsSettingsActivity.this.androidAutoAlertRow || i == NotificationsSettingsActivity.this.accountsAllRow) {
                return 1;
            }
            if (i == NotificationsSettingsActivity.this.resetNotificationsRow) {
                return 2;
            }
            if (i == NotificationsSettingsActivity.this.privateRow || i == NotificationsSettingsActivity.this.groupRow || i == NotificationsSettingsActivity.this.channelsRow) {
                return 3;
            }
            if (i == NotificationsSettingsActivity.this.eventsSection2Row || i == NotificationsSettingsActivity.this.notificationsSection2Row || i == NotificationsSettingsActivity.this.otherSection2Row || i == NotificationsSettingsActivity.this.resetSection2Row || i == NotificationsSettingsActivity.this.callsSection2Row || i == NotificationsSettingsActivity.this.badgeNumberSection2Row || i == NotificationsSettingsActivity.this.resetNotificationsSectionRow) {
                return 4;
            }
            return i == NotificationsSettingsActivity.this.accountsInfoRow ? 6 : 5;
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{HeaderCell.class, TextCheckCell.class, TextDetailSettingsCell.class, TextSettingsCell.class, NotificationsCheckCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
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
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        int i3 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        int i4 = Theme.key_windowBackgroundWhiteGrayText2;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        int i5 = Theme.key_switchTrack;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        int i6 = Theme.key_switchTrackChecked;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{NotificationsCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteValueText));
        int i7 = Theme.key_windowBackgroundGrayShadow;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, i7));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, i7));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteLinkText));
        return arrayList;
    }
}
