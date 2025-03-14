package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Objects;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MrzRecognizer;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$TL_account_authorizations;
import org.telegram.tgnet.TLRPC$TL_account_resetAuthorization;
import org.telegram.tgnet.TLRPC$TL_account_resetWebAuthorization;
import org.telegram.tgnet.TLRPC$TL_account_setAuthorizationTTL;
import org.telegram.tgnet.TLRPC$TL_account_webAuthorizations;
import org.telegram.tgnet.TLRPC$TL_auth_acceptLoginToken;
import org.telegram.tgnet.TLRPC$TL_authorization;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$TL_webAuthorization;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.CameraScanActivity;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.RadioColorCell;
import org.telegram.ui.Cells.SessionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ColoredImageSpan;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.FlickerLoadingView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LinkSpanDrawable;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.Components.UndoView;
import org.telegram.ui.Components.voip.CellFlickerDrawable;
import org.telegram.ui.SessionBottomSheet;
import org.telegram.ui.SessionsActivity;

/* loaded from: classes3.dex */
public class SessionsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private TLRPC$TL_authorization currentSession;
    private int currentSessionRow;
    private int currentSessionSectionRow;
    private int currentType;
    private Delegate delegate;
    private EmptyTextProgressView emptyView;
    private boolean fragmentOpened;
    private FlickerLoadingView globalFlickerLoadingView;
    private boolean highlightLinkDesktopDevice;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loading;
    private int noOtherSessionsRow;
    private int otherSessionsEndRow;
    private int otherSessionsSectionRow;
    private int otherSessionsStartRow;
    private int otherSessionsTerminateDetail;
    private int passwordSessionsDetailRow;
    private int passwordSessionsEndRow;
    private int passwordSessionsSectionRow;
    private int passwordSessionsStartRow;
    private int qrCodeDividerRow;
    private int qrCodeRow;
    private int rowCount;
    private int terminateAllSessionsDetailRow;
    private int terminateAllSessionsRow;
    private int ttlDays;
    private int ttlDivideRow;
    private int ttlHeaderRow;
    private int ttlRow;
    private UndoView undoView;
    private ArrayList<TLObject> sessions = new ArrayList<>();
    private ArrayList<TLObject> passwordSessions = new ArrayList<>();
    private int repeatLoad = 0;

    public interface Delegate {
        void sessionsLoaded();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createView$0(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    public SessionsActivity(int i) {
        this.currentType = i;
    }

    public SessionsActivity setHighlightLinkDesktopDevice() {
        this.highlightLinkDesktopDevice = true;
        return this;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        lambda$loadSessions$17(false);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.newSessionReceived);
        return true;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.newSessionReceived);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        super.onTransitionAnimationEnd(z, z2);
        if (!z || z2) {
            return;
        }
        this.fragmentOpened = true;
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof ScanQRCodeView) {
                ((ScanQRCodeView) childAt).buttonTextView.invalidate();
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        FlickerLoadingView flickerLoadingView = new FlickerLoadingView(context);
        this.globalFlickerLoadingView = flickerLoadingView;
        int i = 1;
        flickerLoadingView.setIsSingleCell(true);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("Devices", R.string.Devices));
        } else {
            this.actionBar.setTitle(LocaleController.getString("WebSessionsTitle", R.string.WebSessionsTitle));
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.SessionsActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i2) {
                if (i2 == -1) {
                    SessionsActivity.this.finishFragment();
                }
            }
        });
        this.listAdapter = new ListAdapter(context);
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        FrameLayout frameLayout2 = frameLayout;
        frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        EmptyTextProgressView emptyTextProgressView = new EmptyTextProgressView(context);
        this.emptyView = emptyTextProgressView;
        emptyTextProgressView.showProgress();
        frameLayout2.addView(this.emptyView, LayoutHelper.createFrame(-1, -1, 17));
        RecyclerListView recyclerListView = new RecyclerListView(context) { // from class: org.telegram.ui.SessionsActivity.2
            @Override // org.telegram.ui.Components.RecyclerListView
            public Integer getSelectorColor(int i2) {
                if (i2 == SessionsActivity.this.terminateAllSessionsRow) {
                    return Integer.valueOf(Theme.multAlpha(getThemedColor(Theme.key_text_RedRegular), 0.1f));
                }
                return Integer.valueOf(getThemedColor(Theme.key_listSelector));
            }
        };
        this.listView = recyclerListView;
        recyclerListView.setLayoutManager(new LinearLayoutManager(this, context, i, false) { // from class: org.telegram.ui.SessionsActivity.3
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        });
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setAnimateEmptyView(true, 0);
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setDurations(150L);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.DEFAULT;
        defaultItemAnimator.setMoveInterpolator(cubicBezierInterpolator);
        defaultItemAnimator.setTranslationInterpolator(cubicBezierInterpolator);
        this.listView.setItemAnimator(defaultItemAnimator);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda20
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                SessionsActivity.this.lambda$createView$13(view, i2);
            }
        });
        if (this.currentType == 0) {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4(context);
            this.undoView = anonymousClass4;
            frameLayout2.addView(anonymousClass4, LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 8.0f));
        }
        updateRows();
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$13(View view, final int i) {
        CharSequence charSequence;
        TLRPC$TL_authorization tLRPC$TL_authorization;
        String string;
        boolean z = true;
        if (i == this.ttlRow) {
            if (getParentActivity() == null) {
                return;
            }
            int i2 = this.ttlDays;
            int i3 = i2 <= 7 ? 0 : i2 <= 93 ? 1 : i2 <= 183 ? 2 : 3;
            final AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("SessionsSelfDestruct", R.string.SessionsSelfDestruct));
            String[] strArr = {LocaleController.formatPluralString("Weeks", 1, new Object[0]), LocaleController.formatPluralString("Months", 3, new Object[0]), LocaleController.formatPluralString("Months", 6, new Object[0]), LocaleController.formatPluralString("Years", 1, new Object[0])};
            LinearLayout linearLayout = new LinearLayout(getParentActivity());
            linearLayout.setOrientation(1);
            builder.setView(linearLayout);
            int i4 = 0;
            while (i4 < 4) {
                RadioColorCell radioColorCell = new RadioColorCell(getParentActivity());
                radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
                radioColorCell.setTag(Integer.valueOf(i4));
                radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                radioColorCell.setTextAndValue(strArr[i4], i3 == i4);
                linearLayout.addView(radioColorCell);
                radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
                radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        SessionsActivity.this.lambda$createView$1(builder, view2);
                    }
                });
                i4++;
            }
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            showDialog(builder.create());
            return;
        }
        if (i == this.terminateAllSessionsRow) {
            if (getParentActivity() == null) {
                return;
            }
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getParentActivity());
            if (this.currentType == 0) {
                builder2.setMessage(LocaleController.getString("AreYouSureSessions", R.string.AreYouSureSessions));
                builder2.setTitle(LocaleController.getString("AreYouSureSessionsTitle", R.string.AreYouSureSessionsTitle));
                string = LocaleController.getString("Terminate", R.string.Terminate);
            } else {
                builder2.setMessage(LocaleController.getString("AreYouSureWebSessions", R.string.AreYouSureWebSessions));
                builder2.setTitle(LocaleController.getString("TerminateWebSessionsTitle", R.string.TerminateWebSessionsTitle));
                string = LocaleController.getString("Disconnect", R.string.Disconnect);
            }
            builder2.setPositiveButton(string, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i5) {
                    SessionsActivity.this.lambda$createView$6(dialogInterface, i5);
                }
            });
            builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            AlertDialog create = builder2.create();
            showDialog(create);
            TextView textView = (TextView) create.getButton(-1);
            if (textView != null) {
                textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                return;
            }
            return;
        }
        if (((i < this.otherSessionsStartRow || i >= this.otherSessionsEndRow) && ((i < this.passwordSessionsStartRow || i >= this.passwordSessionsEndRow) && i != this.currentSessionRow)) || getParentActivity() == null) {
            return;
        }
        if (this.currentType == 0) {
            if (i == this.currentSessionRow) {
                tLRPC$TL_authorization = this.currentSession;
            } else {
                int i5 = this.otherSessionsStartRow;
                if (i >= i5 && i < this.otherSessionsEndRow) {
                    tLRPC$TL_authorization = (TLRPC$TL_authorization) this.sessions.get(i - i5);
                } else {
                    tLRPC$TL_authorization = (TLRPC$TL_authorization) this.passwordSessions.get(i - this.passwordSessionsStartRow);
                }
                z = false;
            }
            showSessionBottomSheet(tLRPC$TL_authorization, z);
            return;
        }
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getParentActivity());
        final boolean[] zArr = new boolean[1];
        if (this.currentType == 0) {
            builder3.setMessage(LocaleController.getString("TerminateSessionText", R.string.TerminateSessionText));
            builder3.setTitle(LocaleController.getString("AreYouSureSessionTitle", R.string.AreYouSureSessionTitle));
            charSequence = LocaleController.getString("Terminate", R.string.Terminate);
        } else {
            TLRPC$TL_webAuthorization tLRPC$TL_webAuthorization = (TLRPC$TL_webAuthorization) this.sessions.get(i - this.otherSessionsStartRow);
            builder3.setMessage(LocaleController.formatString("TerminateWebSessionText", R.string.TerminateWebSessionText, tLRPC$TL_webAuthorization.domain));
            builder3.setTitle(LocaleController.getString("TerminateWebSessionTitle", R.string.TerminateWebSessionTitle));
            CharSequence string2 = LocaleController.getString("Disconnect", R.string.Disconnect);
            FrameLayout frameLayout = new FrameLayout(getParentActivity());
            TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(tLRPC$TL_webAuthorization.bot_id));
            String firstName = user != null ? UserObject.getFirstName(user) : "";
            CheckBoxCell checkBoxCell = new CheckBoxCell(getParentActivity(), 1);
            checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            checkBoxCell.setText(LocaleController.formatString("TerminateWebSessionStop", R.string.TerminateWebSessionStop, firstName), "", false, false);
            checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
            frameLayout.addView(checkBoxCell, LayoutHelper.createFrame(-1, 48.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
            checkBoxCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SessionsActivity.lambda$createView$7(zArr, view2);
                }
            });
            builder3.setCustomViewOffset(16);
            builder3.setView(frameLayout);
            charSequence = string2;
        }
        builder3.setPositiveButton(charSequence, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i6) {
                SessionsActivity.this.lambda$createView$12(i, zArr, dialogInterface, i6);
            }
        });
        builder3.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        AlertDialog create2 = builder3.create();
        showDialog(create2);
        TextView textView2 = (TextView) create2.getButton(-1);
        if (textView2 != null) {
            textView2.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(AlertDialog.Builder builder, View view) {
        int i;
        builder.getDismissRunnable().run();
        Integer num = (Integer) view.getTag();
        if (num.intValue() == 0) {
            i = 7;
        } else if (num.intValue() == 1) {
            i = 90;
        } else if (num.intValue() == 2) {
            i = 183;
        } else {
            i = num.intValue() == 3 ? 365 : 0;
        }
        TLRPC$TL_account_setAuthorizationTTL tLRPC$TL_account_setAuthorizationTTL = new TLRPC$TL_account_setAuthorizationTTL();
        tLRPC$TL_account_setAuthorizationTTL.authorization_ttl_days = i;
        this.ttlDays = i;
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
        getConnectionsManager().sendRequest(tLRPC$TL_account_setAuthorizationTTL, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda19
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                SessionsActivity.lambda$createView$0(tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$6(DialogInterface dialogInterface, int i) {
        if (this.currentType == 0) {
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_auth_resetAuthorizations
                public static int constructor = -1616179942;

                @Override // org.telegram.tgnet.TLObject
                public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i2, boolean z) {
                    return TLRPC$Bool.TLdeserialize(abstractSerializedData, i2, z);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                }
            }, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda13
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.this.lambda$createView$3(tLObject, tLRPC$TL_error);
                }
            });
        } else {
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_account_resetWebAuthorizations
                public static int constructor = 1747789204;

                @Override // org.telegram.tgnet.TLObject
                public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i2, boolean z) {
                    return TLRPC$Bool.TLdeserialize(abstractSerializedData, i2, z);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                }
            }, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda14
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.this.lambda$createView$5(tLObject, tLRPC$TL_error);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$createView$2(tLRPC$TL_error, tLObject);
            }
        });
        for (int i = 0; i < 1; i++) {
            UserConfig userConfig = UserConfig.getInstance(i);
            if (userConfig.isClientActivated()) {
                userConfig.registeredForPush = false;
                userConfig.saveConfig(false);
                MessagesController.getInstance(i).registerForPush(SharedConfig.pushType, SharedConfig.pushString);
                ConnectionsManager.getInstance(i).setUserId(userConfig.getClientUserId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
        if (getParentActivity() != null && tLRPC$TL_error == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
            BulletinFactory.of(this).createSimpleBulletin(R.raw.contact_check, LocaleController.getString("AllSessionsTerminated", R.string.AllSessionsTerminated)).show();
            lambda$loadSessions$17(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$createView$4(tLRPC$TL_error, tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
        if (getParentActivity() == null) {
            return;
        }
        if (tLRPC$TL_error == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
            BulletinFactory.of(this).createSimpleBulletin(R.raw.contact_check, LocaleController.getString("AllWebSessionsTerminated", R.string.AllWebSessionsTerminated)).show();
        } else {
            BulletinFactory.of(this).createSimpleBulletin(R.raw.error, LocaleController.getString("UnknownError", R.string.UnknownError)).show();
        }
        lambda$loadSessions$17(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createView$7(boolean[] zArr, View view) {
        if (view.isEnabled()) {
            zArr[0] = !zArr[0];
            ((CheckBoxCell) view).setChecked(zArr[0], true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$12(int i, boolean[] zArr, DialogInterface dialogInterface, int i2) {
        final TLRPC$TL_authorization tLRPC$TL_authorization;
        if (getParentActivity() == null) {
            return;
        }
        final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 3);
        alertDialog.setCanCancel(false);
        alertDialog.show();
        if (this.currentType == 0) {
            int i3 = this.otherSessionsStartRow;
            if (i >= i3 && i < this.otherSessionsEndRow) {
                tLRPC$TL_authorization = (TLRPC$TL_authorization) this.sessions.get(i - i3);
            } else {
                tLRPC$TL_authorization = (TLRPC$TL_authorization) this.passwordSessions.get(i - this.passwordSessionsStartRow);
            }
            TLRPC$TL_account_resetAuthorization tLRPC$TL_account_resetAuthorization = new TLRPC$TL_account_resetAuthorization();
            tLRPC$TL_account_resetAuthorization.hash = tLRPC$TL_authorization.hash;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_account_resetAuthorization, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda15
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.this.lambda$createView$9(alertDialog, tLRPC$TL_authorization, tLObject, tLRPC$TL_error);
                }
            });
            return;
        }
        final TLRPC$TL_webAuthorization tLRPC$TL_webAuthorization = (TLRPC$TL_webAuthorization) this.sessions.get(i - this.otherSessionsStartRow);
        TLRPC$TL_account_resetWebAuthorization tLRPC$TL_account_resetWebAuthorization = new TLRPC$TL_account_resetWebAuthorization();
        tLRPC$TL_account_resetWebAuthorization.hash = tLRPC$TL_webAuthorization.hash;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_account_resetWebAuthorization, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda16
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                SessionsActivity.this.lambda$createView$11(alertDialog, tLRPC$TL_webAuthorization, tLObject, tLRPC$TL_error);
            }
        });
        if (zArr[0]) {
            MessagesController.getInstance(this.currentAccount).blockPeer(tLRPC$TL_webAuthorization.bot_id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$9(final AlertDialog alertDialog, final TLRPC$TL_authorization tLRPC$TL_authorization, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$createView$8(alertDialog, tLRPC$TL_error, tLRPC$TL_authorization);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$8(AlertDialog alertDialog, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_authorization tLRPC$TL_authorization) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLRPC$TL_error == null) {
            this.sessions.remove(tLRPC$TL_authorization);
            this.passwordSessions.remove(tLRPC$TL_authorization);
            updateRows();
            ListAdapter listAdapter = this.listAdapter;
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$11(final AlertDialog alertDialog, final TLRPC$TL_webAuthorization tLRPC$TL_webAuthorization, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$createView$10(alertDialog, tLRPC$TL_error, tLRPC$TL_webAuthorization);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$10(AlertDialog alertDialog, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_webAuthorization tLRPC$TL_webAuthorization) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLRPC$TL_error == null) {
            this.sessions.remove(tLRPC$TL_webAuthorization);
            updateRows();
            ListAdapter listAdapter = this.listAdapter;
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
        }
    }

    /* renamed from: org.telegram.ui.SessionsActivity$4, reason: invalid class name */
    class AnonymousClass4 extends UndoView {
        AnonymousClass4(Context context) {
            super(context);
        }

        @Override // org.telegram.ui.Components.UndoView
        public void hide(boolean z, int i) {
            if (!z) {
                final TLRPC$TL_authorization tLRPC$TL_authorization = (TLRPC$TL_authorization) getCurrentInfoObject();
                TLRPC$TL_account_resetAuthorization tLRPC$TL_account_resetAuthorization = new TLRPC$TL_account_resetAuthorization();
                tLRPC$TL_account_resetAuthorization.hash = tLRPC$TL_authorization.hash;
                ConnectionsManager.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).sendRequest(tLRPC$TL_account_resetAuthorization, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$4$$ExternalSyntheticLambda1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        SessionsActivity.AnonymousClass4.this.lambda$hide$1(tLRPC$TL_authorization, tLObject, tLRPC$TL_error);
                    }
                });
            }
            super.hide(z, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hide$1(final TLRPC$TL_authorization tLRPC$TL_authorization, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SessionsActivity.AnonymousClass4.this.lambda$hide$0(tLRPC$TL_error, tLRPC$TL_authorization);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hide$0(TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_authorization tLRPC$TL_authorization) {
            if (tLRPC$TL_error == null) {
                SessionsActivity.this.sessions.remove(tLRPC$TL_authorization);
                SessionsActivity.this.passwordSessions.remove(tLRPC$TL_authorization);
                SessionsActivity.this.updateRows();
                if (SessionsActivity.this.listAdapter != null) {
                    SessionsActivity.this.listAdapter.notifyDataSetChanged();
                }
                SessionsActivity.this.lambda$loadSessions$17(true);
            }
        }
    }

    /* renamed from: org.telegram.ui.SessionsActivity$5, reason: invalid class name */
    class AnonymousClass5 implements SessionBottomSheet.Callback {
        AnonymousClass5() {
        }

        @Override // org.telegram.ui.SessionBottomSheet.Callback
        public void onSessionTerminated(final TLRPC$TL_authorization tLRPC$TL_authorization) {
            TLRPC$TL_account_resetAuthorization tLRPC$TL_account_resetAuthorization = new TLRPC$TL_account_resetAuthorization();
            tLRPC$TL_account_resetAuthorization.hash = tLRPC$TL_authorization.hash;
            ConnectionsManager.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).sendRequest(tLRPC$TL_account_resetAuthorization, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$5$$ExternalSyntheticLambda1
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.AnonymousClass5.this.lambda$onSessionTerminated$1(tLRPC$TL_authorization, tLObject, tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionTerminated$1(final TLRPC$TL_authorization tLRPC$TL_authorization, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SessionsActivity.AnonymousClass5.this.lambda$onSessionTerminated$0(tLRPC$TL_error, tLRPC$TL_authorization);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSessionTerminated$0(TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_authorization tLRPC$TL_authorization) {
            if (tLRPC$TL_error == null) {
                SessionsActivity.this.sessions.remove(tLRPC$TL_authorization);
                SessionsActivity.this.passwordSessions.remove(tLRPC$TL_authorization);
                SessionsActivity.this.updateRows();
                if (SessionsActivity.this.listAdapter != null) {
                    SessionsActivity.this.listAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void showSessionBottomSheet(TLRPC$TL_authorization tLRPC$TL_authorization, boolean z) {
        if (tLRPC$TL_authorization == null) {
            return;
        }
        new SessionBottomSheet(this, tLRPC$TL_authorization, z, new AnonymousClass5()).show();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        UndoView undoView = this.undoView;
        if (undoView != null) {
            undoView.hide(true, 0);
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onBecomeFullyHidden() {
        UndoView undoView = this.undoView;
        if (undoView != null) {
            undoView.hide(true, 0);
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.newSessionReceived) {
            lambda$loadSessions$17(true);
        }
    }

    /* renamed from: loadSessions, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$loadSessions$17(final boolean z) {
        if (this.loading) {
            return;
        }
        if (!z) {
            this.loading = true;
        }
        if (this.currentType == 0) {
            ConnectionsManager.getInstance(this.currentAccount).bindRequestToGuid(ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_account_getAuthorizations
                public static int constructor = -484392616;

                @Override // org.telegram.tgnet.TLObject
                public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z2) {
                    return TLRPC$TL_account_authorizations.TLdeserialize(abstractSerializedData, i, z2);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                }
            }, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda18
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.this.lambda$loadSessions$16(z, tLObject, tLRPC$TL_error);
                }
            }), this.classGuid);
        } else {
            ConnectionsManager.getInstance(this.currentAccount).bindRequestToGuid(ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_account_getWebAuthorizations
                public static int constructor = 405695855;

                @Override // org.telegram.tgnet.TLObject
                public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z2) {
                    return TLRPC$TL_account_webAuthorizations.TLdeserialize(abstractSerializedData, i, z2);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                }
            }, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda17
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    SessionsActivity.this.lambda$loadSessions$19(z, tLObject, tLRPC$TL_error);
                }
            }), this.classGuid);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSessions$16(final boolean z, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$loadSessions$15(tLRPC$TL_error, tLObject, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSessions$15(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, final boolean z) {
        this.loading = false;
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.getItemCount();
        }
        if (tLRPC$TL_error == null) {
            this.sessions.clear();
            this.passwordSessions.clear();
            TLRPC$TL_account_authorizations tLRPC$TL_account_authorizations = (TLRPC$TL_account_authorizations) tLObject;
            int size = tLRPC$TL_account_authorizations.authorizations.size();
            for (int i = 0; i < size; i++) {
                TLRPC$TL_authorization tLRPC$TL_authorization = tLRPC$TL_account_authorizations.authorizations.get(i);
                if ((tLRPC$TL_authorization.flags & 1) != 0) {
                    this.currentSession = tLRPC$TL_authorization;
                } else if (tLRPC$TL_authorization.password_pending) {
                    this.passwordSessions.add(tLRPC$TL_authorization);
                } else {
                    this.sessions.add(tLRPC$TL_authorization);
                }
            }
            this.ttlDays = tLRPC$TL_account_authorizations.authorization_ttl_days;
            updateRows();
            Delegate delegate = this.delegate;
            if (delegate != null) {
                delegate.sessionsLoaded();
            }
        }
        ListAdapter listAdapter2 = this.listAdapter;
        if (listAdapter2 != null) {
            listAdapter2.notifyDataSetChanged();
        }
        int i2 = this.repeatLoad;
        if (i2 > 0) {
            int i3 = i2 - 1;
            this.repeatLoad = i3;
            if (i3 > 0) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        SessionsActivity.this.lambda$loadSessions$14(z);
                    }
                }, 2500L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSessions$19(final boolean z, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                SessionsActivity.this.lambda$loadSessions$18(tLRPC$TL_error, tLObject, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadSessions$18(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, final boolean z) {
        this.loading = false;
        if (tLRPC$TL_error == null) {
            this.sessions.clear();
            TLRPC$TL_account_webAuthorizations tLRPC$TL_account_webAuthorizations = (TLRPC$TL_account_webAuthorizations) tLObject;
            MessagesController.getInstance(this.currentAccount).putUsers(tLRPC$TL_account_webAuthorizations.users, false);
            this.sessions.addAll(tLRPC$TL_account_webAuthorizations.authorizations);
            updateRows();
        }
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
        int i = this.repeatLoad;
        if (i > 0) {
            int i2 = i - 1;
            this.repeatLoad = i2;
            if (i2 > 0) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        SessionsActivity.this.lambda$loadSessions$17(z);
                    }
                }, 2500L);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRows() {
        this.rowCount = 0;
        this.currentSessionSectionRow = -1;
        this.currentSessionRow = -1;
        this.terminateAllSessionsRow = -1;
        this.terminateAllSessionsDetailRow = -1;
        this.passwordSessionsSectionRow = -1;
        this.passwordSessionsStartRow = -1;
        this.passwordSessionsEndRow = -1;
        this.passwordSessionsDetailRow = -1;
        this.otherSessionsSectionRow = -1;
        this.otherSessionsStartRow = -1;
        this.otherSessionsEndRow = -1;
        this.otherSessionsTerminateDetail = -1;
        this.noOtherSessionsRow = -1;
        this.qrCodeRow = -1;
        this.qrCodeDividerRow = -1;
        this.ttlHeaderRow = -1;
        this.ttlRow = -1;
        this.ttlDivideRow = -1;
        if (this.currentType == 0 && getMessagesController().qrLoginCamera) {
            int i = this.rowCount;
            int i2 = i + 1;
            this.rowCount = i2;
            this.qrCodeRow = i;
            this.rowCount = i2 + 1;
            this.qrCodeDividerRow = i2;
        }
        if (this.loading) {
            if (this.currentType == 0) {
                int i3 = this.rowCount;
                int i4 = i3 + 1;
                this.rowCount = i4;
                this.currentSessionSectionRow = i3;
                this.rowCount = i4 + 1;
                this.currentSessionRow = i4;
                return;
            }
            return;
        }
        if (this.currentSession != null) {
            int i5 = this.rowCount;
            int i6 = i5 + 1;
            this.rowCount = i6;
            this.currentSessionSectionRow = i5;
            this.rowCount = i6 + 1;
            this.currentSessionRow = i6;
        }
        if (!this.passwordSessions.isEmpty() || !this.sessions.isEmpty()) {
            int i7 = this.rowCount;
            int i8 = i7 + 1;
            this.rowCount = i8;
            this.terminateAllSessionsRow = i7;
            this.rowCount = i8 + 1;
            this.terminateAllSessionsDetailRow = i8;
            this.noOtherSessionsRow = -1;
        } else {
            this.terminateAllSessionsRow = -1;
            this.terminateAllSessionsDetailRow = -1;
            if (this.currentType == 1 || this.currentSession != null) {
                int i9 = this.rowCount;
                this.rowCount = i9 + 1;
                this.noOtherSessionsRow = i9;
            } else {
                this.noOtherSessionsRow = -1;
            }
        }
        if (!this.passwordSessions.isEmpty()) {
            int i10 = this.rowCount;
            int i11 = i10 + 1;
            this.rowCount = i11;
            this.passwordSessionsSectionRow = i10;
            this.passwordSessionsStartRow = i11;
            int size = i11 + this.passwordSessions.size();
            this.rowCount = size;
            this.passwordSessionsEndRow = size;
            this.rowCount = size + 1;
            this.passwordSessionsDetailRow = size;
        }
        if (!this.sessions.isEmpty()) {
            int i12 = this.rowCount;
            int i13 = i12 + 1;
            this.rowCount = i13;
            this.otherSessionsSectionRow = i12;
            this.otherSessionsStartRow = i13;
            this.otherSessionsEndRow = i13 + this.sessions.size();
            int size2 = this.rowCount + this.sessions.size();
            this.rowCount = size2;
            this.rowCount = size2 + 1;
            this.otherSessionsTerminateDetail = size2;
        }
        if (this.ttlDays > 0) {
            int i14 = this.rowCount;
            int i15 = i14 + 1;
            this.rowCount = i15;
            this.ttlHeaderRow = i14;
            int i16 = i15 + 1;
            this.rowCount = i16;
            this.ttlRow = i15;
            this.rowCount = i16 + 1;
            this.ttlDivideRow = i16;
        }
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
            setHasStableIds(true);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == SessionsActivity.this.terminateAllSessionsRow || (adapterPosition >= SessionsActivity.this.otherSessionsStartRow && adapterPosition < SessionsActivity.this.otherSessionsEndRow) || ((adapterPosition >= SessionsActivity.this.passwordSessionsStartRow && adapterPosition < SessionsActivity.this.passwordSessionsEndRow) || adapterPosition == SessionsActivity.this.currentSessionRow || adapterPosition == SessionsActivity.this.ttlRow);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return SessionsActivity.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textCell;
            if (i == 0) {
                textCell = new TextCell(this.mContext);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 1) {
                textCell = new TextInfoPrivacyCell(this.mContext);
            } else if (i == 2) {
                textCell = new HeaderCell(this.mContext);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 5) {
                textCell = SessionsActivity.this.new ScanQRCodeView(this.mContext);
            } else if (i == 6) {
                textCell = new TextSettingsCell(this.mContext);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                textCell = new SessionCell(this.mContext, SessionsActivity.this.currentType);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            }
            return new RecyclerListView.Holder(textCell);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                TextCell textCell = (TextCell) viewHolder.itemView;
                if (i != SessionsActivity.this.terminateAllSessionsRow) {
                    if (i == SessionsActivity.this.qrCodeRow) {
                        int i2 = Theme.key_windowBackgroundWhiteBlueText4;
                        textCell.setColors(i2, i2);
                        textCell.setTag(Integer.valueOf(i2));
                        textCell.setTextAndIcon(LocaleController.getString("AuthAnotherClient", R.string.AuthAnotherClient), R.drawable.msg_qrcode, true ^ SessionsActivity.this.sessions.isEmpty());
                        return;
                    }
                    return;
                }
                int i3 = Theme.key_text_RedRegular;
                textCell.setColors(i3, i3);
                textCell.setTag(Integer.valueOf(i3));
                if (SessionsActivity.this.currentType == 0) {
                    textCell.setTextAndIcon(LocaleController.getString("TerminateAllSessions", R.string.TerminateAllSessions), R.drawable.msg_block2, false);
                    return;
                } else {
                    textCell.setTextAndIcon(LocaleController.getString("TerminateAllWebSessions", R.string.TerminateAllWebSessions), R.drawable.msg_block2, false);
                    return;
                }
            }
            if (itemViewType == 1) {
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                textInfoPrivacyCell.setFixedSize(0);
                if (i == SessionsActivity.this.terminateAllSessionsDetailRow) {
                    if (SessionsActivity.this.currentType == 0) {
                        textInfoPrivacyCell.setText(LocaleController.getString("ClearOtherSessionsHelp", R.string.ClearOtherSessionsHelp));
                    } else {
                        textInfoPrivacyCell.setText(LocaleController.getString("ClearOtherWebSessionsHelp", R.string.ClearOtherWebSessionsHelp));
                    }
                    textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    return;
                }
                if (i == SessionsActivity.this.otherSessionsTerminateDetail) {
                    if (SessionsActivity.this.currentType == 0) {
                        if (SessionsActivity.this.sessions.isEmpty()) {
                            textInfoPrivacyCell.setText("");
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("SessionsListInfo", R.string.SessionsListInfo));
                        }
                    } else {
                        textInfoPrivacyCell.setText(LocaleController.getString("TerminateWebSessionInfo", R.string.TerminateWebSessionInfo));
                    }
                    textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    return;
                }
                if (i != SessionsActivity.this.passwordSessionsDetailRow) {
                    if (i == SessionsActivity.this.qrCodeDividerRow || i == SessionsActivity.this.ttlDivideRow || i == SessionsActivity.this.noOtherSessionsRow) {
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        textInfoPrivacyCell.setText("");
                        textInfoPrivacyCell.setFixedSize(12);
                        return;
                    }
                    return;
                }
                textInfoPrivacyCell.setText(LocaleController.getString("LoginAttemptsInfo", R.string.LoginAttemptsInfo));
                if (SessionsActivity.this.otherSessionsTerminateDetail == -1) {
                    textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    return;
                } else {
                    textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    return;
                }
            }
            if (itemViewType == 2) {
                HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                if (i != SessionsActivity.this.currentSessionSectionRow) {
                    if (i == SessionsActivity.this.otherSessionsSectionRow) {
                        if (SessionsActivity.this.currentType == 0) {
                            headerCell.setText(LocaleController.getString("OtherSessions", R.string.OtherSessions));
                            return;
                        } else {
                            headerCell.setText(LocaleController.getString("OtherWebSessions", R.string.OtherWebSessions));
                            return;
                        }
                    }
                    if (i != SessionsActivity.this.passwordSessionsSectionRow) {
                        if (i == SessionsActivity.this.ttlHeaderRow) {
                            headerCell.setText(LocaleController.getString("TerminateOldSessionHeader", R.string.TerminateOldSessionHeader));
                            return;
                        }
                        return;
                    }
                    headerCell.setText(LocaleController.getString("LoginAttempts", R.string.LoginAttempts));
                    return;
                }
                headerCell.setText(LocaleController.getString("CurrentSession", R.string.CurrentSession));
                return;
            }
            if (itemViewType != 5) {
                if (itemViewType == 6) {
                    ((TextSettingsCell) viewHolder.itemView).setTextAndValue(LocaleController.getString("IfInactiveFor", R.string.IfInactiveFor), (SessionsActivity.this.ttlDays <= 30 || SessionsActivity.this.ttlDays > 183) ? SessionsActivity.this.ttlDays == 365 ? LocaleController.formatPluralString("Years", SessionsActivity.this.ttlDays / 365, new Object[0]) : LocaleController.formatPluralString("Weeks", SessionsActivity.this.ttlDays / 7, new Object[0]) : LocaleController.formatPluralString("Months", SessionsActivity.this.ttlDays / 30, new Object[0]), true, false);
                    return;
                }
                SessionCell sessionCell = (SessionCell) viewHolder.itemView;
                if (i == SessionsActivity.this.currentSessionRow) {
                    if (SessionsActivity.this.currentSession == null) {
                        sessionCell.showStub(SessionsActivity.this.globalFlickerLoadingView);
                        return;
                    }
                    TLRPC$TL_authorization tLRPC$TL_authorization = SessionsActivity.this.currentSession;
                    if (SessionsActivity.this.sessions.isEmpty() && SessionsActivity.this.passwordSessions.isEmpty() && SessionsActivity.this.qrCodeRow == -1) {
                        r1 = false;
                    }
                    sessionCell.setSession(tLRPC$TL_authorization, r1);
                    return;
                }
                if (i < SessionsActivity.this.otherSessionsStartRow || i >= SessionsActivity.this.otherSessionsEndRow) {
                    if (i < SessionsActivity.this.passwordSessionsStartRow || i >= SessionsActivity.this.passwordSessionsEndRow) {
                        return;
                    }
                    sessionCell.setSession((TLObject) SessionsActivity.this.passwordSessions.get(i - SessionsActivity.this.passwordSessionsStartRow), i != SessionsActivity.this.passwordSessionsEndRow - 1);
                    return;
                }
                sessionCell.setSession((TLObject) SessionsActivity.this.sessions.get(i - SessionsActivity.this.otherSessionsStartRow), i != SessionsActivity.this.otherSessionsEndRow - 1);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            int hash;
            if (i == SessionsActivity.this.terminateAllSessionsRow) {
                hash = Objects.hash(0, 0);
            } else if (i != SessionsActivity.this.terminateAllSessionsDetailRow) {
                if (i != SessionsActivity.this.otherSessionsTerminateDetail) {
                    if (i == SessionsActivity.this.passwordSessionsDetailRow) {
                        hash = Objects.hash(0, 3);
                    } else if (i == SessionsActivity.this.qrCodeDividerRow) {
                        hash = Objects.hash(0, 4);
                    } else if (i == SessionsActivity.this.ttlDivideRow) {
                        hash = Objects.hash(0, 5);
                    } else if (i == SessionsActivity.this.noOtherSessionsRow) {
                        hash = Objects.hash(0, 6);
                    } else if (i == SessionsActivity.this.currentSessionSectionRow) {
                        hash = Objects.hash(0, 7);
                    } else if (i == SessionsActivity.this.otherSessionsSectionRow) {
                        hash = Objects.hash(0, 8);
                    } else if (i == SessionsActivity.this.passwordSessionsSectionRow) {
                        hash = Objects.hash(0, 9);
                    } else if (i == SessionsActivity.this.ttlHeaderRow) {
                        hash = Objects.hash(0, 10);
                    } else if (i == SessionsActivity.this.currentSessionRow) {
                        hash = Objects.hash(0, 11);
                    } else if (i < SessionsActivity.this.otherSessionsStartRow || i >= SessionsActivity.this.otherSessionsEndRow) {
                        if (i < SessionsActivity.this.passwordSessionsStartRow || i >= SessionsActivity.this.passwordSessionsEndRow) {
                            if (i == SessionsActivity.this.qrCodeRow) {
                                hash = Objects.hash(0, 12);
                            } else {
                                if (i == SessionsActivity.this.ttlRow) {
                                    hash = Objects.hash(0, 13);
                                }
                                hash = Objects.hash(0, -1);
                            }
                        } else {
                            TLObject tLObject = (TLObject) SessionsActivity.this.passwordSessions.get(i - SessionsActivity.this.passwordSessionsStartRow);
                            if (tLObject instanceof TLRPC$TL_authorization) {
                                hash = Objects.hash(2, Long.valueOf(((TLRPC$TL_authorization) tLObject).hash));
                            } else {
                                if (tLObject instanceof TLRPC$TL_webAuthorization) {
                                    hash = Objects.hash(2, Long.valueOf(((TLRPC$TL_webAuthorization) tLObject).hash));
                                }
                                hash = Objects.hash(0, -1);
                            }
                        }
                    } else {
                        TLObject tLObject2 = (TLObject) SessionsActivity.this.sessions.get(i - SessionsActivity.this.otherSessionsStartRow);
                        if (tLObject2 instanceof TLRPC$TL_authorization) {
                            hash = Objects.hash(1, Long.valueOf(((TLRPC$TL_authorization) tLObject2).hash));
                        } else {
                            if (tLObject2 instanceof TLRPC$TL_webAuthorization) {
                                hash = Objects.hash(1, Long.valueOf(((TLRPC$TL_webAuthorization) tLObject2).hash));
                            }
                            hash = Objects.hash(0, -1);
                        }
                    }
                } else {
                    hash = Objects.hash(0, 2);
                }
            } else {
                hash = Objects.hash(0, 1);
            }
            return hash;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == SessionsActivity.this.terminateAllSessionsRow) {
                return 0;
            }
            if (i == SessionsActivity.this.terminateAllSessionsDetailRow || i == SessionsActivity.this.otherSessionsTerminateDetail || i == SessionsActivity.this.passwordSessionsDetailRow || i == SessionsActivity.this.qrCodeDividerRow || i == SessionsActivity.this.ttlDivideRow || i == SessionsActivity.this.noOtherSessionsRow) {
                return 1;
            }
            if (i == SessionsActivity.this.currentSessionSectionRow || i == SessionsActivity.this.otherSessionsSectionRow || i == SessionsActivity.this.passwordSessionsSectionRow || i == SessionsActivity.this.ttlHeaderRow) {
                return 2;
            }
            if (i == SessionsActivity.this.currentSessionRow) {
                return 4;
            }
            if (i >= SessionsActivity.this.otherSessionsStartRow && i < SessionsActivity.this.otherSessionsEndRow) {
                return 4;
            }
            if (i >= SessionsActivity.this.passwordSessionsStartRow && i < SessionsActivity.this.passwordSessionsEndRow) {
                return 4;
            }
            if (i == SessionsActivity.this.qrCodeRow) {
                return 5;
            }
            return i == SessionsActivity.this.ttlRow ? 6 : 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ScanQRCodeView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
        TextView buttonTextView;
        CellFlickerDrawable flickerDrawable;
        BackupImageView imageView;
        TextView textView;

        public ScanQRCodeView(Context context) {
            super(context);
            this.flickerDrawable = new CellFlickerDrawable();
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            addView(backupImageView, LayoutHelper.createFrame(120, 120.0f, 1, 0.0f, 16.0f, 0.0f, 0.0f));
            CellFlickerDrawable cellFlickerDrawable = this.flickerDrawable;
            cellFlickerDrawable.repeatEnabled = false;
            cellFlickerDrawable.animationSpeedScale = 1.2f;
            this.imageView.setOnClickListener(new View.OnClickListener(SessionsActivity.this) { // from class: org.telegram.ui.SessionsActivity.ScanQRCodeView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (ScanQRCodeView.this.imageView.getImageReceiver().getLottieAnimation() == null || ScanQRCodeView.this.imageView.getImageReceiver().getLottieAnimation().isRunning()) {
                        return;
                    }
                    ScanQRCodeView.this.imageView.getImageReceiver().getLottieAnimation().setCurrentFrame(0, false);
                    ScanQRCodeView.this.imageView.getImageReceiver().getLottieAnimation().restart();
                }
            });
            int i = Theme.key_windowBackgroundWhiteBlackText;
            Theme.getColor(i);
            int i2 = Theme.key_windowBackgroundWhite;
            Theme.getColor(i2);
            int i3 = Theme.key_featuredStickers_addButton;
            Theme.getColor(i3);
            Theme.getColor(i2);
            LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context);
            this.textView = linksTextView;
            addView(linksTextView, LayoutHelper.createFrame(-1, -2.0f, 0, 36.0f, 152.0f, 36.0f, 0.0f));
            this.textView.setGravity(1);
            this.textView.setTextColor(Theme.getColor(i));
            this.textView.setTextSize(1, 15.0f);
            this.textView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
            this.textView.setHighlightColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkSelection));
            setBackgroundColor(Theme.getColor(i2));
            String string = LocaleController.getString("AuthAnotherClientInfo4", R.string.AuthAnotherClientInfo4);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
            int indexOf = string.indexOf(42);
            int i4 = indexOf + 1;
            int indexOf2 = string.indexOf(42, i4);
            if (indexOf != -1 && indexOf2 != -1 && indexOf != indexOf2) {
                this.textView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
                spannableStringBuilder.replace(indexOf2, indexOf2 + 1, (CharSequence) "");
                spannableStringBuilder.replace(indexOf, i4, (CharSequence) "");
                spannableStringBuilder.setSpan(new URLSpanNoUnderline(LocaleController.getString("AuthAnotherClientDownloadClientUrl", R.string.AuthAnotherClientDownloadClientUrl)), indexOf, indexOf2 - 1, 33);
            }
            String spannableStringBuilder2 = spannableStringBuilder.toString();
            int indexOf3 = spannableStringBuilder2.indexOf(42);
            int i5 = indexOf3 + 1;
            int indexOf4 = spannableStringBuilder2.indexOf(42, i5);
            if (indexOf3 != -1 && indexOf4 != -1 && indexOf3 != indexOf4) {
                this.textView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
                spannableStringBuilder.replace(indexOf4, indexOf4 + 1, (CharSequence) "");
                spannableStringBuilder.replace(indexOf3, i5, (CharSequence) "");
                spannableStringBuilder.setSpan(new URLSpanNoUnderline(LocaleController.getString("AuthAnotherWebClientUrl", R.string.AuthAnotherWebClientUrl)), indexOf3, indexOf4 - 1, 33);
            }
            this.textView.setText(spannableStringBuilder);
            TextView textView = new TextView(context, SessionsActivity.this) { // from class: org.telegram.ui.SessionsActivity.ScanQRCodeView.2
                @Override // android.view.View
                public void draw(Canvas canvas) {
                    super.draw(canvas);
                    ScanQRCodeView scanQRCodeView = ScanQRCodeView.this;
                    if (scanQRCodeView.flickerDrawable.progress <= 1.0f && SessionsActivity.this.highlightLinkDesktopDevice && SessionsActivity.this.fragmentOpened) {
                        RectF rectF = AndroidUtilities.rectTmp;
                        rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                        ScanQRCodeView.this.flickerDrawable.setParentWidth(getMeasuredWidth());
                        ScanQRCodeView.this.flickerDrawable.draw(canvas, rectF, AndroidUtilities.dp(8.0f), null);
                        invalidate();
                    }
                }
            };
            this.buttonTextView = textView;
            textView.setPadding(AndroidUtilities.dp(34.0f), 0, AndroidUtilities.dp(34.0f), 0);
            this.buttonTextView.setGravity(17);
            this.buttonTextView.setTextSize(1, 14.0f);
            this.buttonTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
            spannableStringBuilder3.append((CharSequence) ".  ").append((CharSequence) LocaleController.getString("LinkDesktopDevice", R.string.LinkDesktopDevice));
            spannableStringBuilder3.setSpan(new ColoredImageSpan(ContextCompat.getDrawable(getContext(), R.drawable.msg_mini_qr)), 0, 1, 0);
            this.buttonTextView.setText(spannableStringBuilder3);
            this.buttonTextView.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
            this.buttonTextView.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), Theme.getColor(i3), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
            this.buttonTextView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$ScanQRCodeView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SessionsActivity.ScanQRCodeView.this.lambda$new$0(view);
                }
            });
            addView(this.buttonTextView, LayoutHelper.createFrame(-1, 48.0f, 80, 16.0f, 15.0f, 16.0f, 16.0f));
            setSticker();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            if (SessionsActivity.this.getParentActivity() == null) {
                return;
            }
            if (Build.VERSION.SDK_INT < 23 || SessionsActivity.this.getParentActivity().checkSelfPermission("android.permission.CAMERA") == 0) {
                SessionsActivity.this.openCameraScanActivity();
            } else {
                SessionsActivity.this.getParentActivity().requestPermissions(new String[]{"android.permission.CAMERA"}, 34);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(276.0f), 1073741824));
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            setSticker();
            NotificationCenter.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).addObserver(this, NotificationCenter.diceStickersDidLoad);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            NotificationCenter.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).removeObserver(this, NotificationCenter.diceStickersDidLoad);
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (i == NotificationCenter.diceStickersDidLoad && AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME.equals((String) objArr[0])) {
                setSticker();
            }
        }

        private void setSticker() {
            TLRPC$TL_messages_stickerSet stickerSetByName = MediaDataController.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).getStickerSetByName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME);
            if (stickerSetByName == null) {
                stickerSetByName = MediaDataController.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).getStickerSetByEmojiOrName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME);
            }
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = stickerSetByName;
            TLRPC$Document tLRPC$Document = (tLRPC$TL_messages_stickerSet == null || tLRPC$TL_messages_stickerSet.documents.size() <= 6) ? null : tLRPC$TL_messages_stickerSet.documents.get(6);
            SvgHelper.SvgDrawable svgThumb = tLRPC$Document != null ? DocumentObject.getSvgThumb(tLRPC$Document.thumbs, Theme.key_emptyListPlaceholder, 0.2f) : null;
            if (svgThumb != null) {
                svgThumb.overrideWidthAndHeight(LiteMode.FLAG_CALLS_ANIMATIONS, LiteMode.FLAG_CALLS_ANIMATIONS);
            }
            if (tLRPC$Document == null) {
                MediaDataController.getInstance(((BaseFragment) SessionsActivity.this).currentAccount).loadStickersByEmojiOrName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME, false, tLRPC$TL_messages_stickerSet == null);
            } else {
                this.imageView.setImage(ImageLocation.getForDocument(tLRPC$Document), "130_130", "tgs", svgThumb, tLRPC$TL_messages_stickerSet);
                this.imageView.getImageReceiver().setAutoRepeat(2);
            }
        }
    }

    /* renamed from: org.telegram.ui.SessionsActivity$6, reason: invalid class name */
    class AnonymousClass6 implements CameraScanActivity.CameraScanActivityDelegate {
        private TLObject response = null;
        private TLRPC$TL_error error = null;

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public /* synthetic */ void didFindMrzInfo(MrzRecognizer.Result result) {
            CameraScanActivity.CameraScanActivityDelegate.CC.$default$didFindMrzInfo(this, result);
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public /* synthetic */ String getSubtitleText() {
            return CameraScanActivity.CameraScanActivityDelegate.CC.$default$getSubtitleText(this);
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public /* synthetic */ void onDismiss() {
            CameraScanActivity.CameraScanActivityDelegate.CC.$default$onDismiss(this);
        }

        AnonymousClass6() {
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public void didFindQr(String str) {
            TLObject tLObject = this.response;
            if (tLObject instanceof TLRPC$TL_authorization) {
                TLRPC$TL_authorization tLRPC$TL_authorization = (TLRPC$TL_authorization) tLObject;
                if (((TLRPC$TL_authorization) tLObject).password_pending) {
                    SessionsActivity.this.passwordSessions.add(0, tLRPC$TL_authorization);
                    SessionsActivity.this.repeatLoad = 4;
                    SessionsActivity.this.lambda$loadSessions$17(false);
                } else {
                    SessionsActivity.this.sessions.add(0, tLRPC$TL_authorization);
                }
                SessionsActivity.this.updateRows();
                SessionsActivity.this.listAdapter.notifyDataSetChanged();
                SessionsActivity.this.undoView.showWithAction(0L, 11, this.response);
                return;
            }
            if (this.error != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SessionsActivity.AnonymousClass6.this.lambda$didFindQr$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFindQr$0() {
            String str;
            String str2 = this.error.text;
            if (str2 != null && str2.equals("AUTH_TOKEN_EXCEPTION")) {
                str = LocaleController.getString("AccountAlreadyLoggedIn", R.string.AccountAlreadyLoggedIn);
            } else {
                str = LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + this.error.text;
            }
            AlertsCreator.showSimpleAlert(SessionsActivity.this, LocaleController.getString("AuthAnotherClient", R.string.AuthAnotherClient), str);
        }

        @Override // org.telegram.ui.CameraScanActivity.CameraScanActivityDelegate
        public boolean processQr(final String str, final Runnable runnable) {
            this.response = null;
            this.error = null;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$6$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    SessionsActivity.AnonymousClass6.this.lambda$processQr$4(str, runnable);
                }
            }, 750L);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processQr$4(String str, final Runnable runnable) {
            try {
                byte[] decode = Base64.decode(str.substring(17).replaceAll("\\/", "_").replaceAll("\\+", "-"), 8);
                TLRPC$TL_auth_acceptLoginToken tLRPC$TL_auth_acceptLoginToken = new TLRPC$TL_auth_acceptLoginToken();
                tLRPC$TL_auth_acceptLoginToken.token = decode;
                SessionsActivity.this.getConnectionsManager().sendRequest(tLRPC$TL_auth_acceptLoginToken, new RequestDelegate() { // from class: org.telegram.ui.SessionsActivity$6$$ExternalSyntheticLambda4
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        SessionsActivity.AnonymousClass6.this.lambda$processQr$2(runnable, tLObject, tLRPC$TL_error);
                    }
                });
            } catch (Exception e) {
                FileLog.e("Failed to pass qr code auth", e);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$6$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SessionsActivity.AnonymousClass6.this.lambda$processQr$3();
                    }
                });
                runnable.run();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processQr$2(final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.SessionsActivity$6$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    SessionsActivity.AnonymousClass6.this.lambda$processQr$1(tLObject, tLRPC$TL_error, runnable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processQr$1(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error, Runnable runnable) {
            this.response = tLObject;
            this.error = tLRPC$TL_error;
            runnable.run();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processQr$3() {
            AlertsCreator.showSimpleAlert(SessionsActivity.this, LocaleController.getString("AuthAnotherClient", R.string.AuthAnotherClient), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCameraScanActivity() {
        CameraScanActivity.showAsSheet((BaseFragment) this, false, 2, (CameraScanActivity.CameraScanActivityDelegate) new AnonymousClass6());
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, HeaderCell.class, SessionCell.class}, null, null, null, Theme.key_windowBackgroundWhite));
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
        arrayList.add(new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle));
        int i3 = Theme.key_text_RedRegular;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueText4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteBlueHeader));
        int i4 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{SessionCell.class}, new String[]{"nameTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{SessionCell.class}, new String[]{"onlineTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteValueText));
        int i5 = Theme.key_windowBackgroundWhiteGrayText3;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{SessionCell.class}, new String[]{"onlineTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{SessionCell.class}, new String[]{"detailTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i4));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{SessionCell.class}, new String[]{"detailExTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i5));
        arrayList.add(new ThemeDescription(this.undoView, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_undo_background));
        arrayList.add(new ThemeDescription(this.undoView, 0, new Class[]{UndoView.class}, new String[]{"undoImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        arrayList.add(new ThemeDescription(this.undoView, 0, new Class[]{UndoView.class}, new String[]{"undoTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i3));
        int i6 = Theme.key_undo_infoColor;
        arrayList.add(new ThemeDescription(this.undoView, 0, new Class[]{UndoView.class}, new String[]{"infoTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.undoView, 0, new Class[]{UndoView.class}, new String[]{"textPaint"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.undoView, 0, new Class[]{UndoView.class}, new String[]{"progressPaint"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.undoView, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{UndoView.class}, new String[]{"leftImageView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        return arrayList;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (getParentActivity() != null && i == 34) {
            if (iArr.length > 0 && iArr[0] == 0) {
                openCameraScanActivity();
            } else {
                new AlertDialog.Builder(getParentActivity()).setMessage(AndroidUtilities.replaceTags(LocaleController.getString("QRCodePermissionNoCameraWithHint", R.string.QRCodePermissionNoCameraWithHint))).setPositiveButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.SessionsActivity$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        SessionsActivity.this.lambda$onRequestPermissionsResultFragment$20(dialogInterface, i2);
                    }
                }).setNegativeButton(LocaleController.getString("ContactsPermissionAlertNotNow", R.string.ContactsPermissionAlertNotNow), null).setTopAnimation(R.raw.permission_request_camera, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestPermissionsResultFragment$20(DialogInterface dialogInterface, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            getParentActivity().startActivity(intent);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    int getSessionsCount() {
        if (this.sessions.size() == 0 && this.loading) {
            return 0;
        }
        return this.sessions.size() + 1;
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }
}
