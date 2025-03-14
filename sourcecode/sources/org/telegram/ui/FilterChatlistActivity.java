package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Dialog;
import org.telegram.tgnet.TLRPC$TL_chatlists_deleteExportedInvite;
import org.telegram.tgnet.TLRPC$TL_chatlists_editExportedInvite;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_exportedChatlistInvite;
import org.telegram.tgnet.TLRPC$TL_inputChatlistDialogFilter;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.GroupCreateUserCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.CircularProgressDrawable;
import org.telegram.ui.Components.ColoredImageSpan;
import org.telegram.ui.Components.CrossfadeDrawable;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.FolderBottomSheet;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.ui.Components.QRCodeBottomSheet;
import org.telegram.ui.Components.RLottieImageView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.FilterChatlistActivity;

/* loaded from: classes3.dex */
public class FilterChatlistActivity extends BaseFragment {
    private ListAdapter adapter;
    private ActionBarMenuItem doneButton;
    private CrossfadeDrawable doneButtonDrawable;
    private ValueAnimator doneButtonDrawableAnimator;
    MessagesController.DialogFilter filter;
    private FolderBottomSheet.HeaderCell headerCountCell;
    private HintInnerCell hintCountCell;
    TLRPC$TL_exportedChatlistInvite invite;
    private long lastClicked;
    private long lastClickedDialogId;
    private RecyclerListView listView;
    private Utilities.Callback<TLRPC$TL_exportedChatlistInvite> onDelete;
    private Utilities.Callback<TLRPC$TL_exportedChatlistInvite> onEdit;
    private boolean peersChanged;
    private int savingTitleReqId;
    private boolean titleChanged;
    private ArrayList<Long> selectedPeers = new ArrayList<>();
    private ArrayList<Long> allowedPeers = new ArrayList<>();
    private ArrayList<Long> peers = new ArrayList<>();
    private int shiftDp = -5;
    private boolean saving = false;
    private int rowsCount = 0;
    private int linkRow = -1;
    private int linkHeaderRow = -1;
    private int linkSectionRow = -1;
    private int chatsHeaderRow = -1;
    private int chatsStartRow = -1;
    private int chatsEndRow = -1;
    private int chatsSectionRow = -1;
    private Runnable enableDoneLoading = new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            FilterChatlistActivity.this.lambda$new$7();
        }
    };
    private float doneButtonAlpha = 1.0f;

    public FilterChatlistActivity(MessagesController.DialogFilter dialogFilter, TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite) {
        this.filter = dialogFilter;
        this.invite = tLRPC$TL_exportedChatlistInvite;
    }

    public void setOnDelete(Utilities.Callback<TLRPC$TL_exportedChatlistInvite> callback) {
        this.onDelete = callback;
    }

    public void setOnEdit(Utilities.Callback<TLRPC$TL_exportedChatlistInvite> callback) {
        this.onEdit = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActionBarTitle(boolean z) {
        TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = this.invite;
        String string = TextUtils.isEmpty(tLRPC$TL_exportedChatlistInvite == null ? null : tLRPC$TL_exportedChatlistInvite.title) ? LocaleController.getString("FilterShare", R.string.FilterShare) : this.invite.title;
        if (z) {
            this.actionBar.setTitleAnimated(string, false, 220L);
        } else {
            this.actionBar.setTitle(string);
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        updateActionBarTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.FilterChatlistActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    if (FilterChatlistActivity.this.checkDiscard()) {
                        FilterChatlistActivity.this.finishFragment();
                    }
                } else if (i == 1) {
                    if (Math.abs(FilterChatlistActivity.this.doneButtonAlpha - 1.0f) < 0.1f) {
                        FilterChatlistActivity.this.save();
                    } else if (Math.abs(FilterChatlistActivity.this.doneButtonAlpha - 0.5f) < 0.1f) {
                        FilterChatlistActivity.this.shakeHeader();
                    }
                }
            }
        });
        ActionBarMenu createMenu = this.actionBar.createMenu();
        Drawable mutate = context.getResources().getDrawable(R.drawable.ic_ab_done).mutate();
        int i = Theme.key_actionBarDefaultIcon;
        mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i), PorterDuff.Mode.MULTIPLY));
        CrossfadeDrawable crossfadeDrawable = new CrossfadeDrawable(mutate, new CircularProgressDrawable(Theme.getColor(i)));
        this.doneButtonDrawable = crossfadeDrawable;
        this.doneButton = createMenu.addItemWithWidth(1, crossfadeDrawable, AndroidUtilities.dp(56.0f), LocaleController.getString("Done", R.string.Done));
        checkDoneButton();
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        FrameLayout frameLayout2 = frameLayout;
        frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        RecyclerListView recyclerListView = new RecyclerListView(this, context) { // from class: org.telegram.ui.FilterChatlistActivity.2
            @Override // android.view.ViewGroup, android.view.View
            public boolean requestFocus(int i2, Rect rect) {
                return false;
            }
        };
        this.listView = recyclerListView;
        recyclerListView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView2 = this.listView;
        ListAdapter listAdapter = new ListAdapter();
        this.adapter = listAdapter;
        recyclerListView2.setAdapter(listAdapter);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                FilterChatlistActivity.this.lambda$createView$0(view, i2);
            }
        });
        getMessagesController().updateFilterDialogs(this.filter);
        this.peers.clear();
        if (this.invite != null) {
            for (int i2 = 0; i2 < this.invite.peers.size(); i2++) {
                long peerDialogId = DialogObject.getPeerDialogId(this.invite.peers.get(i2));
                this.peers.add(Long.valueOf(peerDialogId));
                this.selectedPeers.add(Long.valueOf(peerDialogId));
                this.allowedPeers.add(Long.valueOf(peerDialogId));
            }
        }
        for (int i3 = 0; i3 < this.filter.dialogs.size(); i3++) {
            TLRPC$Dialog tLRPC$Dialog = this.filter.dialogs.get(i3);
            if (tLRPC$Dialog != null && !DialogObject.isEncryptedDialog(tLRPC$Dialog.id) && !this.peers.contains(Long.valueOf(tLRPC$Dialog.id))) {
                long j = tLRPC$Dialog.id;
                boolean z = j < 0;
                if (j < 0) {
                    z = FilterCreateActivity.canAddToFolder(getMessagesController().getChat(Long.valueOf(-tLRPC$Dialog.id)));
                }
                if (z) {
                    this.peers.add(Long.valueOf(tLRPC$Dialog.id));
                    this.allowedPeers.add(Long.valueOf(tLRPC$Dialog.id));
                }
            }
        }
        for (int i4 = 0; i4 < this.filter.dialogs.size(); i4++) {
            TLRPC$Dialog tLRPC$Dialog2 = this.filter.dialogs.get(i4);
            if (tLRPC$Dialog2 != null && !DialogObject.isEncryptedDialog(tLRPC$Dialog2.id) && !this.peers.contains(Long.valueOf(tLRPC$Dialog2.id)) && !this.allowedPeers.contains(Long.valueOf(tLRPC$Dialog2.id))) {
                this.peers.add(Long.valueOf(tLRPC$Dialog2.id));
            }
        }
        updateRows();
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view, int i) {
        String string;
        String str;
        if (getParentActivity() != null && (view instanceof GroupCreateUserCell)) {
            long longValue = this.peers.get(i - this.chatsStartRow).longValue();
            if (this.selectedPeers.contains(Long.valueOf(longValue))) {
                this.selectedPeers.remove(Long.valueOf(longValue));
                this.peersChanged = true;
                checkDoneButton();
                ((GroupCreateUserCell) view).setChecked(false, true);
            } else if (this.allowedPeers.contains(Long.valueOf(longValue))) {
                if (this.selectedPeers.size() + 1 > getMaxChats()) {
                    showDialog(new LimitReachedBottomSheet(this, getContext(), 4, this.currentAccount));
                    return;
                }
                this.selectedPeers.add(Long.valueOf(longValue));
                this.peersChanged = true;
                checkDoneButton();
                ((GroupCreateUserCell) view).setChecked(true, true);
            } else {
                int i2 = -this.shiftDp;
                this.shiftDp = i2;
                AndroidUtilities.shakeViewSpring(view, i2);
                BotWebViewVibrationEffect.APP_ERROR.vibrate();
                ArrayList arrayList = new ArrayList();
                if (longValue >= 0) {
                    arrayList.add(getMessagesController().getUser(Long.valueOf(longValue)));
                    TLRPC$User user = getMessagesController().getUser(Long.valueOf(longValue));
                    if (user != null && user.bot) {
                        str = LocaleController.getString("FilterInviteBotToast", R.string.FilterInviteBotToast);
                    } else {
                        str = LocaleController.getString("FilterInviteUserToast", R.string.FilterInviteUserToast);
                    }
                } else {
                    TLRPC$Chat chat = getMessagesController().getChat(Long.valueOf(-longValue));
                    if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                        if (ChatObject.isPublic(chat)) {
                            string = LocaleController.getString("FilterInviteChannelToast", R.string.FilterInviteChannelToast);
                        } else {
                            string = LocaleController.getString("FilterInvitePrivateChannelToast", R.string.FilterInvitePrivateChannelToast);
                        }
                    } else if (ChatObject.isPublic(chat)) {
                        string = LocaleController.getString("FilterInviteGroupToast", R.string.FilterInviteGroupToast);
                    } else {
                        string = LocaleController.getString("FilterInvitePrivateGroupToast", R.string.FilterInvitePrivateGroupToast);
                    }
                    arrayList.add(chat);
                    str = string;
                }
                if (this.lastClickedDialogId != longValue || System.currentTimeMillis() - this.lastClicked > 1500) {
                    this.lastClickedDialogId = longValue;
                    this.lastClicked = System.currentTimeMillis();
                    BulletinFactory.of(this).createChatsBulletin(arrayList, str, null).show();
                    return;
                }
                return;
            }
            checkPeersChanged();
            updateHeaderCell(true);
            updateHintCell(true);
        }
    }

    private void checkPeersChanged() {
        TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = this.invite;
        if (tLRPC$TL_exportedChatlistInvite == null || tLRPC$TL_exportedChatlistInvite.url == null || !this.peersChanged) {
            return;
        }
        boolean z = true;
        boolean z2 = this.selectedPeers.size() != this.invite.peers.size();
        if (!z2) {
            for (int i = 0; i < this.invite.peers.size(); i++) {
                if (!this.selectedPeers.contains(Long.valueOf(DialogObject.getPeerDialogId(this.invite.peers.get(i))))) {
                    break;
                }
            }
        }
        z = z2;
        if (z) {
            return;
        }
        this.peersChanged = false;
        checkDoneButton();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        return checkDiscard();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        return checkDiscard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save() {
        if (this.invite == null || this.saving || !this.peersChanged) {
            return;
        }
        updateDoneProgress(true);
        this.saving = true;
        this.invite.peers.clear();
        for (int i = 0; i < this.selectedPeers.size(); i++) {
            this.invite.peers.add(getMessagesController().getPeer(this.selectedPeers.get(i).longValue()));
        }
        TLRPC$TL_chatlists_editExportedInvite tLRPC$TL_chatlists_editExportedInvite = new TLRPC$TL_chatlists_editExportedInvite();
        TLRPC$TL_inputChatlistDialogFilter tLRPC$TL_inputChatlistDialogFilter = new TLRPC$TL_inputChatlistDialogFilter();
        tLRPC$TL_chatlists_editExportedInvite.chatlist = tLRPC$TL_inputChatlistDialogFilter;
        tLRPC$TL_inputChatlistDialogFilter.filter_id = this.filter.id;
        tLRPC$TL_chatlists_editExportedInvite.slug = getSlug();
        tLRPC$TL_chatlists_editExportedInvite.revoked = this.invite.revoked;
        tLRPC$TL_chatlists_editExportedInvite.flags |= 4;
        for (int i2 = 0; i2 < this.selectedPeers.size(); i2++) {
            tLRPC$TL_chatlists_editExportedInvite.peers.add(getMessagesController().getInputPeer(this.selectedPeers.get(i2).longValue()));
        }
        getConnectionsManager().sendRequest(tLRPC$TL_chatlists_editExportedInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                FilterChatlistActivity.this.lambda$save$2(tLObject, tLRPC$TL_error);
            }
        });
        Utilities.Callback<TLRPC$TL_exportedChatlistInvite> callback = this.onEdit;
        if (callback != null) {
            callback.run(this.invite);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$2(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                FilterChatlistActivity.this.lambda$save$1(tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$save$1(TLRPC$TL_error tLRPC$TL_error) {
        updateDoneProgress(false);
        this.saving = false;
        if (tLRPC$TL_error != null && "INVITES_TOO_MUCH".equals(tLRPC$TL_error.text)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), 12, this.currentAccount));
            return;
        }
        if (tLRPC$TL_error != null && "INVITE_PEERS_TOO_MUCH".equals(tLRPC$TL_error.text)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), 4, this.currentAccount));
        } else if (tLRPC$TL_error != null && "CHATLISTS_TOO_MUCH".equals(tLRPC$TL_error.text)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), 13, this.currentAccount));
        } else {
            finishFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveTitle() {
        if (this.savingTitleReqId != 0) {
            getConnectionsManager().cancelRequest(this.savingTitleReqId, true);
            this.savingTitleReqId = 0;
        }
        TLRPC$TL_chatlists_editExportedInvite tLRPC$TL_chatlists_editExportedInvite = new TLRPC$TL_chatlists_editExportedInvite();
        TLRPC$TL_inputChatlistDialogFilter tLRPC$TL_inputChatlistDialogFilter = new TLRPC$TL_inputChatlistDialogFilter();
        tLRPC$TL_chatlists_editExportedInvite.chatlist = tLRPC$TL_inputChatlistDialogFilter;
        tLRPC$TL_inputChatlistDialogFilter.filter_id = this.filter.id;
        tLRPC$TL_chatlists_editExportedInvite.slug = getSlug();
        TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = this.invite;
        tLRPC$TL_chatlists_editExportedInvite.revoked = tLRPC$TL_exportedChatlistInvite.revoked;
        tLRPC$TL_chatlists_editExportedInvite.flags |= 2;
        tLRPC$TL_chatlists_editExportedInvite.title = tLRPC$TL_exportedChatlistInvite.title;
        this.savingTitleReqId = getConnectionsManager().sendRequest(tLRPC$TL_chatlists_editExportedInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda8
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                FilterChatlistActivity.this.lambda$saveTitle$4(tLObject, tLRPC$TL_error);
            }
        });
        Utilities.Callback<TLRPC$TL_exportedChatlistInvite> callback = this.onEdit;
        if (callback != null) {
            callback.run(this.invite);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveTitle$4(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                FilterChatlistActivity.this.lambda$saveTitle$3(tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveTitle$3(TLRPC$TL_error tLRPC$TL_error) {
        this.savingTitleReqId = 0;
        if (tLRPC$TL_error == null) {
            BulletinFactory.of(this).createSimpleBulletin(R.raw.contact_check, LocaleController.getString("FilterInviteNameEdited", R.string.FilterInviteNameEdited)).show();
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.savingTitleReqId != 0) {
            getConnectionsManager().cancelRequest(this.savingTitleReqId, true);
            this.savingTitleReqId = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHintCell(boolean z) {
        HintInnerCell hintInnerCell = this.hintCountCell;
        if (hintInnerCell == null) {
            return;
        }
        if (this.invite == null) {
            hintInnerCell.setText(LocaleController.getString("FilterInviteHeaderNo", R.string.FilterInviteHeaderNo), z);
        } else {
            hintInnerCell.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("FilterInviteHeader", this.selectedPeers.size(), this.filter.name)), z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHeaderCell(boolean z) {
        FolderBottomSheet.HeaderCell headerCell = this.headerCountCell;
        if (headerCell == null) {
            return;
        }
        headerCell.setText(this.selectedPeers.size() <= 0 ? LocaleController.getString("FilterInviteHeaderChatsEmpty") : LocaleController.formatPluralString("FilterInviteHeaderChats", this.selectedPeers.size(), new Object[0]), z);
        if (this.allowedPeers.size() > 1) {
            final boolean z2 = this.selectedPeers.size() >= Math.min(getMaxChats(), this.allowedPeers.size());
            this.headerCountCell.setAction(LocaleController.getString(!z2 ? R.string.SelectAll : R.string.DeselectAll), new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    FilterChatlistActivity.this.lambda$updateHeaderCell$5(z2);
                }
            });
        } else {
            this.headerCountCell.setAction("", null);
        }
        if (z) {
            AndroidUtilities.makeAccessibilityAnnouncement(((Object) this.headerCountCell.textView.getText()) + ", " + ((Object) this.headerCountCell.actionTextView.getText()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateHeaderCell$5(boolean z) {
        deselectAll(this.headerCountCell, z);
    }

    public void updateRows() {
        this.rowsCount = 0;
        int i = 0 + 1;
        this.rowsCount = i;
        TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = this.invite;
        if (tLRPC$TL_exportedChatlistInvite != null) {
            int i2 = i + 1;
            this.rowsCount = i2;
            this.linkHeaderRow = i;
            int i3 = i2 + 1;
            this.rowsCount = i3;
            this.linkRow = i2;
            this.rowsCount = i3 + 1;
            this.linkSectionRow = i3;
        } else {
            this.linkHeaderRow = -1;
            this.linkRow = -1;
            this.linkSectionRow = -1;
        }
        if (tLRPC$TL_exportedChatlistInvite == null && this.peers.isEmpty()) {
            this.chatsHeaderRow = -1;
            this.chatsStartRow = -1;
            this.chatsEndRow = -1;
            this.chatsSectionRow = -1;
        } else {
            int i4 = this.rowsCount;
            int i5 = i4 + 1;
            this.rowsCount = i5;
            this.chatsHeaderRow = i4;
            int i6 = i5 + 1;
            this.rowsCount = i6;
            this.chatsStartRow = i5;
            int size = i6 + (this.peers.size() - 1);
            this.rowsCount = size;
            this.chatsEndRow = size;
            this.rowsCount = size + 1;
            this.chatsSectionRow = size;
        }
        ListAdapter listAdapter = this.adapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSlug() {
        String str;
        TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = this.invite;
        if (tLRPC$TL_exportedChatlistInvite == null || (str = tLRPC$TL_exportedChatlistInvite.url) == null) {
            return null;
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    class ListAdapter extends RecyclerListView.SelectionAdapter {
        ListAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FrameLayout frameLayout;
            if (i == 0) {
                frameLayout = new HintInnerCell(FilterChatlistActivity.this.getContext(), R.raw.folder_share);
            } else if (i == 2) {
                frameLayout = new TextInfoPrivacyCell(FilterChatlistActivity.this.getContext());
            } else if (i == 3) {
                frameLayout = new AnonymousClass1(FilterChatlistActivity.this.getContext(), FilterChatlistActivity.this);
                frameLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 4) {
                frameLayout = new GroupCreateUserCell(FilterChatlistActivity.this.getContext(), 1, 0, false);
                frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else if (i == 5) {
                frameLayout = new FolderBottomSheet.HeaderCell(FilterChatlistActivity.this.getContext());
                frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            } else {
                frameLayout = null;
            }
            return new RecyclerListView.Holder(frameLayout);
        }

        /* renamed from: org.telegram.ui.FilterChatlistActivity$ListAdapter$1, reason: invalid class name */
        class AnonymousClass1 extends InviteLinkCell {
            AnonymousClass1(Context context, BaseFragment baseFragment) {
                super(context, baseFragment);
            }

            @Override // org.telegram.ui.FilterChatlistActivity.InviteLinkCell
            protected void deleteLink() {
                TLRPC$TL_chatlists_deleteExportedInvite tLRPC$TL_chatlists_deleteExportedInvite = new TLRPC$TL_chatlists_deleteExportedInvite();
                TLRPC$TL_inputChatlistDialogFilter tLRPC$TL_inputChatlistDialogFilter = new TLRPC$TL_inputChatlistDialogFilter();
                tLRPC$TL_chatlists_deleteExportedInvite.chatlist = tLRPC$TL_inputChatlistDialogFilter;
                FilterChatlistActivity filterChatlistActivity = FilterChatlistActivity.this;
                tLRPC$TL_inputChatlistDialogFilter.filter_id = filterChatlistActivity.filter.id;
                tLRPC$TL_chatlists_deleteExportedInvite.slug = filterChatlistActivity.getSlug();
                final AlertDialog alertDialog = new AlertDialog(getContext(), 3);
                alertDialog.showDelayed(180L);
                FilterChatlistActivity.this.getConnectionsManager().sendRequest(tLRPC$TL_chatlists_deleteExportedInvite, new RequestDelegate() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda7
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        FilterChatlistActivity.ListAdapter.AnonymousClass1.this.lambda$deleteLink$3(alertDialog, tLObject, tLRPC$TL_error);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$deleteLink$3(final AlertDialog alertDialog, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilterChatlistActivity.ListAdapter.AnonymousClass1.this.lambda$deleteLink$2(alertDialog);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$deleteLink$2(AlertDialog alertDialog) {
                alertDialog.dismiss();
                if (FilterChatlistActivity.this.onDelete != null) {
                    FilterChatlistActivity.this.onDelete.run(FilterChatlistActivity.this.invite);
                }
                FilterChatlistActivity.this.finishFragment();
            }

            @Override // org.telegram.ui.FilterChatlistActivity.InviteLinkCell
            public void editname() {
                TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = FilterChatlistActivity.this.invite;
                if (tLRPC$TL_exportedChatlistInvite == null || tLRPC$TL_exportedChatlistInvite.url == null) {
                    return;
                }
                final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(getContext());
                editTextBoldCursor.setBackgroundDrawable(Theme.createEditTextDrawable(getContext(), true));
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setDialogButtonColorKey(Theme.key_dialogButton);
                builder.setTitle(LocaleController.getString("FilterInviteEditName", R.string.FilterInviteEditName));
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AndroidUtilities.hideKeyboard(EditTextBoldCursor.this);
                    }
                });
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(1);
                builder.setView(linearLayout);
                editTextBoldCursor.setTextSize(1, 16.0f);
                int i = Theme.key_dialogTextBlack;
                editTextBoldCursor.setTextColor(Theme.getColor(i));
                editTextBoldCursor.setMaxLines(1);
                editTextBoldCursor.setLines(1);
                editTextBoldCursor.setInputType(16385);
                editTextBoldCursor.setGravity(51);
                editTextBoldCursor.setSingleLine(true);
                editTextBoldCursor.setImeOptions(6);
                editTextBoldCursor.setHint(FilterChatlistActivity.this.filter.name);
                editTextBoldCursor.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
                editTextBoldCursor.setCursorColor(Theme.getColor(i));
                editTextBoldCursor.setCursorSize(AndroidUtilities.dp(20.0f));
                editTextBoldCursor.setCursorWidth(1.5f);
                editTextBoldCursor.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
                linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 6, 24, 0));
                editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda4
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                        boolean lambda$editname$5;
                        lambda$editname$5 = FilterChatlistActivity.ListAdapter.AnonymousClass1.lambda$editname$5(AlertDialog.Builder.this, textView, i2, keyEvent);
                        return lambda$editname$5;
                    }
                });
                editTextBoldCursor.addTextChangedListener(new TextWatcher(this) { // from class: org.telegram.ui.FilterChatlistActivity.ListAdapter.1.1
                    boolean ignoreTextChange;

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        if (!this.ignoreTextChange && editable.length() > 32) {
                            this.ignoreTextChange = true;
                            editable.delete(32, editable.length());
                            AndroidUtilities.shakeView(editTextBoldCursor);
                            editTextBoldCursor.performHapticFeedback(3, 2);
                            this.ignoreTextChange = false;
                        }
                    }
                });
                if (!TextUtils.isEmpty(FilterChatlistActivity.this.invite.title)) {
                    editTextBoldCursor.setText(FilterChatlistActivity.this.invite.title);
                    editTextBoldCursor.setSelection(editTextBoldCursor.length());
                }
                builder.setPositiveButton(LocaleController.getString("Save", R.string.Save), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        FilterChatlistActivity.ListAdapter.AnonymousClass1.this.lambda$editname$6(editTextBoldCursor, builder, dialogInterface, i2);
                    }
                });
                AlertDialog create = builder.create();
                create.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        FilterChatlistActivity.ListAdapter.AnonymousClass1.lambda$editname$8(EditTextBoldCursor.this, dialogInterface);
                    }
                });
                create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AndroidUtilities.hideKeyboard(EditTextBoldCursor.this);
                    }
                });
                create.show();
                create.setTextColor(Theme.getColor(i));
                editTextBoldCursor.requestFocus();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ boolean lambda$editname$5(AlertDialog.Builder builder, TextView textView, int i, KeyEvent keyEvent) {
                AndroidUtilities.hideKeyboard(textView);
                builder.create().getButton(-1).callOnClick();
                return false;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$editname$6(EditTextBoldCursor editTextBoldCursor, AlertDialog.Builder builder, DialogInterface dialogInterface, int i) {
                AndroidUtilities.hideKeyboard(editTextBoldCursor);
                builder.getDismissRunnable().run();
                FilterChatlistActivity.this.invite.title = editTextBoldCursor.getText().toString();
                FilterChatlistActivity.this.titleChanged = true;
                FilterChatlistActivity.this.updateActionBarTitle(true);
                FilterChatlistActivity.this.saveTitle();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ void lambda$editname$8(final EditTextBoldCursor editTextBoldCursor, DialogInterface dialogInterface) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$ListAdapter$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilterChatlistActivity.ListAdapter.AnonymousClass1.lambda$editname$7(EditTextBoldCursor.this);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static /* synthetic */ void lambda$editname$7(EditTextBoldCursor editTextBoldCursor) {
                editTextBoldCursor.requestFocus();
                AndroidUtilities.showKeyboard(editTextBoldCursor);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            String str;
            String str2;
            TLRPC$Chat tLRPC$Chat;
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                FilterChatlistActivity.this.hintCountCell = (HintInnerCell) viewHolder.itemView;
                FilterChatlistActivity.this.updateHintCell(false);
                return;
            }
            if (itemViewType == 2) {
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(FilterChatlistActivity.this.getContext(), i == FilterChatlistActivity.this.chatsSectionRow ? R.drawable.greydivider_bottom : R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                if (i == FilterChatlistActivity.this.chatsSectionRow) {
                    textInfoPrivacyCell.setFixedSize(0);
                    FilterChatlistActivity filterChatlistActivity = FilterChatlistActivity.this;
                    if (filterChatlistActivity.invite == null || filterChatlistActivity.allowedPeers.isEmpty()) {
                        textInfoPrivacyCell.setText(LocaleController.getString("FilterInviteHintNo", R.string.FilterInviteHintNo));
                        return;
                    } else {
                        textInfoPrivacyCell.setText(LocaleController.getString("FilterInviteHint", R.string.FilterInviteHint));
                        return;
                    }
                }
                textInfoPrivacyCell.setFixedSize(12);
                return;
            }
            if (itemViewType == 3) {
                InviteLinkCell inviteLinkCell = (InviteLinkCell) viewHolder.itemView;
                TLRPC$TL_exportedChatlistInvite tLRPC$TL_exportedChatlistInvite = FilterChatlistActivity.this.invite;
                inviteLinkCell.setLink(tLRPC$TL_exportedChatlistInvite != null ? tLRPC$TL_exportedChatlistInvite.url : null, false);
                return;
            }
            if (itemViewType != 4) {
                if (itemViewType == 5) {
                    FolderBottomSheet.HeaderCell headerCell = (FolderBottomSheet.HeaderCell) viewHolder.itemView;
                    if (headerCell == FilterChatlistActivity.this.headerCountCell) {
                        FilterChatlistActivity.this.headerCountCell = null;
                    }
                    if (i != FilterChatlistActivity.this.linkHeaderRow) {
                        FilterChatlistActivity.this.headerCountCell = headerCell;
                        FilterChatlistActivity filterChatlistActivity2 = FilterChatlistActivity.this;
                        if (filterChatlistActivity2.invite != null && !filterChatlistActivity2.allowedPeers.isEmpty()) {
                            FilterChatlistActivity.this.updateHeaderCell(false);
                            return;
                        } else {
                            headerCell.setText(LocaleController.getString("FilterInviteHeaderChatsNo", R.string.FilterInviteHeaderChatsNo), false);
                            headerCell.setAction("", null);
                            return;
                        }
                    }
                    headerCell.setText(LocaleController.getString("InviteLink", R.string.InviteLink), false);
                    headerCell.setAction("", null);
                    return;
                }
                return;
            }
            GroupCreateUserCell groupCreateUserCell = (GroupCreateUserCell) viewHolder.itemView;
            long longValue = ((Long) FilterChatlistActivity.this.peers.get(i - FilterChatlistActivity.this.chatsStartRow)).longValue();
            if (longValue >= 0) {
                TLRPC$User user = FilterChatlistActivity.this.getMessagesController().getUser(Long.valueOf(longValue));
                if (user != 0) {
                    str2 = UserObject.getUserName(user);
                    tLRPC$Chat = user;
                } else {
                    str2 = null;
                    tLRPC$Chat = user;
                }
            } else {
                TLRPC$Chat chat = FilterChatlistActivity.this.getMessagesController().getChat(Long.valueOf(-longValue));
                if (chat != null) {
                    r3 = chat.title;
                    if (chat.participants_count != 0) {
                        if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                            str = LocaleController.formatPluralStringComma("Subscribers", chat.participants_count);
                        } else {
                            str = LocaleController.formatPluralStringComma("Members", chat.participants_count);
                        }
                    } else if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                        str = LocaleController.getString("ChannelPublic");
                    } else {
                        str = LocaleController.getString("MegaPublic");
                    }
                } else {
                    str = null;
                }
                String str3 = r3;
                r3 = str;
                str2 = str3;
                tLRPC$Chat = chat;
            }
            if (FilterChatlistActivity.this.allowedPeers.contains(Long.valueOf(longValue))) {
                groupCreateUserCell.setForbiddenCheck(false);
                groupCreateUserCell.setChecked(FilterChatlistActivity.this.selectedPeers.contains(Long.valueOf(longValue)), false);
            } else {
                groupCreateUserCell.setForbiddenCheck(true);
                groupCreateUserCell.setChecked(false, false);
                if (tLRPC$Chat instanceof TLRPC$User) {
                    if (((TLRPC$User) tLRPC$Chat).bot) {
                        r3 = LocaleController.getString("FilterInviteBot", R.string.FilterInviteBot);
                    } else {
                        r3 = LocaleController.getString("FilterInviteUser", R.string.FilterInviteUser);
                    }
                } else if (tLRPC$Chat instanceof TLRPC$Chat) {
                    if (ChatObject.isChannelAndNotMegaGroup(tLRPC$Chat)) {
                        r3 = LocaleController.getString("FilterInviteChannel", R.string.FilterInviteChannel);
                    } else {
                        r3 = LocaleController.getString("FilterInviteGroup", R.string.FilterInviteGroup);
                    }
                }
            }
            groupCreateUserCell.setTag(Long.valueOf(longValue));
            groupCreateUserCell.setObject(tLRPC$Chat, str2, r3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return FilterChatlistActivity.this.rowsCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 0;
            }
            if (i == FilterChatlistActivity.this.chatsSectionRow || i == FilterChatlistActivity.this.linkSectionRow) {
                return 2;
            }
            if (i == FilterChatlistActivity.this.linkRow) {
                return 3;
            }
            if (i < FilterChatlistActivity.this.chatsStartRow || i >= FilterChatlistActivity.this.chatsEndRow) {
                return (i == FilterChatlistActivity.this.chatsHeaderRow || i == FilterChatlistActivity.this.linkHeaderRow) ? 5 : 0;
            }
            return 4;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 4;
        }
    }

    private int getMaxChats() {
        return getUserConfig().isPremium() ? getMessagesController().dialogFiltersChatsLimitPremium : getMessagesController().dialogFiltersChatsLimitDefault;
    }

    private void deselectAll(final FolderBottomSheet.HeaderCell headerCell, final boolean z) {
        this.selectedPeers.clear();
        if (!z) {
            this.selectedPeers.addAll(this.allowedPeers.subList(0, Math.min(getMaxChats(), this.allowedPeers.size())));
        }
        headerCell.setAction(LocaleController.getString(!(this.selectedPeers.size() >= Math.min(getMaxChats(), this.allowedPeers.size())) ? R.string.SelectAll : R.string.DeselectAll), new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                FilterChatlistActivity.this.lambda$deselectAll$6(headerCell, z);
            }
        });
        this.peersChanged = true;
        checkPeersChanged();
        checkDoneButton();
        updateHeaderCell(true);
        updateHintCell(true);
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (childAt instanceof GroupCreateUserCell) {
                Object tag = childAt.getTag();
                if (tag instanceof Long) {
                    ((GroupCreateUserCell) childAt).setChecked(this.selectedPeers.contains(Long.valueOf(((Long) tag).longValue())), true);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deselectAll$6(FolderBottomSheet.HeaderCell headerCell, boolean z) {
        deselectAll(headerCell, !z);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
    }

    public static class HintInnerCell extends FrameLayout {
        private RLottieImageView imageView;
        private TextView subtitleTextView;

        public HintInnerCell(Context context, int i) {
            super(context);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.imageView = rLottieImageView;
            rLottieImageView.setAnimation(i, 90, 90);
            this.imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.imageView.playAnimation();
            this.imageView.setImportantForAccessibility(2);
            addView(this.imageView, LayoutHelper.createFrame(90, 90.0f, 49, 0.0f, 14.0f, 0.0f, 0.0f));
            TextView textView = new TextView(context);
            this.subtitleTextView = textView;
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
            this.subtitleTextView.setTextSize(1, 14.0f);
            this.subtitleTextView.setGravity(17);
            this.subtitleTextView.setLines(2);
            addView(this.subtitleTextView, LayoutHelper.createFrame(-1, -2.0f, 49, 40.0f, 121.0f, 40.0f, 24.0f));
        }

        public void setText(CharSequence charSequence, boolean z) {
            this.subtitleTextView.setText(charSequence);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7() {
        updateDoneProgress(true);
    }

    private void updateDoneProgress(boolean z) {
        if (!z) {
            AndroidUtilities.cancelRunOnUIThread(this.enableDoneLoading);
        }
        if (this.doneButtonDrawable != null) {
            ValueAnimator valueAnimator = this.doneButtonDrawableAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            float[] fArr = new float[2];
            fArr[0] = this.doneButtonDrawable.getProgress();
            fArr[1] = z ? 1.0f : 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
            this.doneButtonDrawableAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    FilterChatlistActivity.this.lambda$updateDoneProgress$8(valueAnimator2);
                }
            });
            this.doneButtonDrawableAnimator.setDuration((long) (Math.abs(this.doneButtonDrawable.getProgress() - (z ? 1.0f : 0.0f)) * 200.0f));
            this.doneButtonDrawableAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.doneButtonDrawableAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDoneProgress$8(ValueAnimator valueAnimator) {
        this.doneButtonDrawable.setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
        this.doneButtonDrawable.invalidateSelf();
    }

    private void checkDoneButton() {
        float f = this.peersChanged ? this.selectedPeers.isEmpty() ^ true ? 1.0f : 0.5f : 0.0f;
        if (Math.abs(this.doneButtonAlpha - f) > 0.1f) {
            this.doneButton.clearAnimation();
            ViewPropertyAnimator animate = this.doneButton.animate();
            this.doneButtonAlpha = f;
            animate.alpha(f).setDuration(320L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shakeHeader() {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            View childAt = this.listView.getChildAt(i);
            if (this.listView.getChildAdapterPosition(childAt) == this.chatsHeaderRow && (childAt instanceof FolderBottomSheet.HeaderCell)) {
                int i2 = -this.shiftDp;
                this.shiftDp = i2;
                AndroidUtilities.shakeViewSpring(childAt, i2);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkDiscard() {
        if (this.selectedPeers.isEmpty() || !this.peersChanged) {
            return true;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString("UnsavedChanges", R.string.UnsavedChanges));
        builder.setMessage(LocaleController.getString("UnsavedChangesMessage", R.string.UnsavedChangesMessage));
        builder.setPositiveButton(LocaleController.getString("ApplyTheme", R.string.ApplyTheme), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilterChatlistActivity.this.lambda$checkDiscard$9(dialogInterface, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString("PassportDiscard", R.string.PassportDiscard), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FilterChatlistActivity.this.lambda$checkDiscard$10(dialogInterface, i);
            }
        });
        showDialog(builder.create());
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkDiscard$9(DialogInterface dialogInterface, int i) {
        save();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkDiscard$10(DialogInterface dialogInterface, int i) {
        finishFragment();
    }

    public static class InviteLinkCell extends FrameLayout {
        private ActionBarPopupWindow actionBarPopupWindow;
        ButtonsBox buttonsBox;
        private float changeAlpha;
        private ValueAnimator changeAnimator;
        TextView copyButton;
        TextView generateButton;
        private String lastUrl;
        FrameLayout linkBox;
        ImageView optionsIcon;
        BaseFragment parentFragment;
        private float[] point;
        TextView shareButton;
        SimpleTextView spoilerTextView;
        SimpleTextView textView;

        protected void deleteLink() {
        }

        public void editname() {
        }

        protected void generate() {
        }

        class ButtonsBox extends FrameLayout {
            private Paint paint;
            private Path path;
            private float[] radii;
            private float t;

            public ButtonsBox(InviteLinkCell inviteLinkCell, Context context) {
                super(context);
                this.paint = new Paint();
                this.radii = new float[8];
                this.path = new Path();
                setWillNotDraw(false);
                this.paint.setColor(Theme.getColor(Theme.key_featuredStickers_addButton));
            }

            public void setT(float f) {
                this.t = f;
                invalidate();
            }

            private void setRadii(float f, float f2) {
                float[] fArr = this.radii;
                fArr[7] = f;
                fArr[6] = f;
                fArr[1] = f;
                fArr[0] = f;
                fArr[5] = f2;
                fArr[4] = f2;
                fArr[3] = f2;
                fArr[2] = f2;
            }

            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                float measuredWidth = getMeasuredWidth() / 2.0f;
                this.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, measuredWidth - AndroidUtilities.lerp(0, AndroidUtilities.dp(4.0f), this.t), getMeasuredHeight());
                setRadii(AndroidUtilities.dp(8.0f), AndroidUtilities.lerp(0, AndroidUtilities.dp(8.0f), this.t));
                this.path.addRoundRect(rectF, this.radii, Path.Direction.CW);
                canvas.drawPath(this.path, this.paint);
                this.path.rewind();
                rectF.set(measuredWidth + AndroidUtilities.lerp(0, AndroidUtilities.dp(4.0f), this.t), 0.0f, getMeasuredWidth(), getMeasuredHeight());
                setRadii(AndroidUtilities.lerp(0, AndroidUtilities.dp(8.0f), this.t), AndroidUtilities.dp(8.0f));
                this.path.addRoundRect(rectF, this.radii, Path.Direction.CW);
                canvas.drawPath(this.path, this.paint);
            }
        }

        public InviteLinkCell(Context context, BaseFragment baseFragment) {
            super(context);
            this.point = new float[2];
            this.parentFragment = baseFragment;
            FrameLayout frameLayout = new FrameLayout(context);
            this.linkBox = frameLayout;
            int dp = AndroidUtilities.dp(8.0f);
            int i = Theme.key_graySection;
            frameLayout.setBackground(Theme.createSimpleSelectorRoundRectDrawable(dp, Theme.getColor(i), Theme.blendOver(Theme.getColor(i), Theme.getColor(Theme.key_listSelector))));
            this.linkBox.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$new$0(view);
                }
            });
            addView(this.linkBox, LayoutHelper.createFrame(-1, 48.0f, 55, 22.0f, 9.0f, 22.0f, 0.0f));
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.spoilerTextView = simpleTextView;
            simpleTextView.setTextSize(16);
            SimpleTextView simpleTextView2 = this.spoilerTextView;
            int i2 = Theme.key_windowBackgroundWhiteBlackText;
            simpleTextView2.setTextColor(Theme.getColor(i2));
            SpannableString spannableString = new SpannableString("t.me/folder/N3k/dImA/bIo");
            TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
            textStyleRun.flags |= 256;
            spannableString.setSpan(new TextStyleSpan(textStyleRun), 0, spannableString.length(), 33);
            this.spoilerTextView.setText(spannableString);
            this.spoilerTextView.setAlpha(1.0f);
            this.linkBox.addView(this.spoilerTextView, LayoutHelper.createFrame(-1, -2.0f, 23, 20.0f, 0.0f, 40.0f, 0.0f));
            SimpleTextView simpleTextView3 = new SimpleTextView(context);
            this.textView = simpleTextView3;
            simpleTextView3.setTextSize(16);
            this.textView.setTextColor(Theme.getColor(i2));
            this.textView.setText(spannableString);
            this.textView.setAlpha(0.0f);
            this.linkBox.addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 23, 20.0f, 0.0f, 40.0f, 0.0f));
            ImageView imageView = new ImageView(context);
            this.optionsIcon = imageView;
            imageView.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_ab_other));
            this.optionsIcon.setScaleType(ImageView.ScaleType.CENTER);
            this.optionsIcon.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogTextGray3), PorterDuff.Mode.SRC_IN));
            this.optionsIcon.setAlpha(0.0f);
            this.optionsIcon.setVisibility(8);
            this.optionsIcon.setContentDescription(LocaleController.getString("AccDescrMoreOptions", R.string.AccDescrMoreOptions));
            this.optionsIcon.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$new$2(view);
                }
            });
            this.linkBox.addView(this.optionsIcon, LayoutHelper.createFrame(40, 40.0f, 21, 4.0f, 4.0f, 4.0f, 4.0f));
            ButtonsBox buttonsBox = new ButtonsBox(this, context);
            this.buttonsBox = buttonsBox;
            addView(buttonsBox, LayoutHelper.createFrame(-1, 42.0f, 55, 22.0f, 69.0f, 22.0f, 0.0f));
            TextView textView = new TextView(this, context) { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.1
                @Override // android.widget.TextView, android.view.View
                protected void onMeasure(int i3, int i4) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec((View.MeasureSpec.getSize(i3) - AndroidUtilities.dp(8.0f)) / 2, 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
                }
            };
            this.copyButton = textView;
            textView.setGravity(17);
            TextView textView2 = this.copyButton;
            int i3 = Theme.key_featuredStickers_buttonText;
            textView2.setTextColor(Theme.getColor(i3));
            this.copyButton.setBackground(Theme.createRadSelectorDrawable(822083583, 8, 8));
            this.copyButton.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.copyButton.setTextSize(14.0f);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(context, R.drawable.msg_copy_filled)), 0, 1, 0);
            spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(8.0f)), 1, 2, 0);
            spannableStringBuilder.append((CharSequence) LocaleController.getString("LinkActionCopy", R.string.LinkActionCopy));
            spannableStringBuilder.append((CharSequence) ".").setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(5.0f)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
            this.copyButton.setText(spannableStringBuilder);
            this.copyButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$new$3(view);
                }
            });
            this.copyButton.setAlpha(0.0f);
            this.copyButton.setVisibility(8);
            this.buttonsBox.addView(this.copyButton, LayoutHelper.createFrame(-1, -1, 3));
            TextView textView3 = new TextView(this, context) { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.2
                @Override // android.widget.TextView, android.view.View
                protected void onMeasure(int i4, int i5) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec((View.MeasureSpec.getSize(i4) - AndroidUtilities.dp(8.0f)) / 2, 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
                }
            };
            this.shareButton = textView3;
            textView3.setGravity(17);
            this.shareButton.setTextColor(Theme.getColor(i3));
            this.shareButton.setBackground(Theme.createRadSelectorDrawable(822083583, 8, 8));
            this.shareButton.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.shareButton.setTextSize(14.0f);
            SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
            spannableStringBuilder2.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(context, R.drawable.msg_share_filled)), 0, 1, 0);
            spannableStringBuilder2.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(8.0f)), 1, 2, 0);
            spannableStringBuilder2.append((CharSequence) LocaleController.getString("LinkActionShare", R.string.LinkActionShare));
            spannableStringBuilder2.append((CharSequence) ".").setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(5.0f)), spannableStringBuilder2.length() - 1, spannableStringBuilder2.length(), 0);
            this.shareButton.setText(spannableStringBuilder2);
            this.shareButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$new$4(view);
                }
            });
            this.shareButton.setAlpha(0.0f);
            this.shareButton.setVisibility(8);
            this.buttonsBox.addView(this.shareButton, LayoutHelper.createFrame(-1, -1, 5));
            TextView textView4 = new TextView(context);
            this.generateButton = textView4;
            textView4.setGravity(17);
            this.generateButton.setTextColor(Theme.getColor(i3));
            this.generateButton.setBackground(Theme.createRadSelectorDrawable(822083583, 8, 8));
            this.generateButton.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.generateButton.setTextSize(14.0f);
            this.generateButton.setText("Generate Invite Link");
            this.generateButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$new$5(view);
                }
            });
            this.generateButton.setAlpha(1.0f);
            this.generateButton.setVisibility(0);
            this.buttonsBox.addView(this.generateButton, LayoutHelper.createFrame(-1, -1.0f));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            copy();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(View view) {
            if (Build.VERSION.SDK_INT >= 21 && (this.linkBox.getBackground() instanceof RippleDrawable)) {
                this.linkBox.getBackground().setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});
                postDelayed(new Runnable() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        FilterChatlistActivity.InviteLinkCell.this.lambda$new$1();
                    }
                }, 180L);
            }
            options();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            this.linkBox.getBackground().setState(new int[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            copy();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(View view) {
            share();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5(View view) {
            generate();
        }

        public void setLink(final String str, boolean z) {
            this.lastUrl = str;
            if (str != null) {
                if (str.startsWith("http://")) {
                    str = str.substring(7);
                }
                if (str.startsWith("https://")) {
                    str = str.substring(8);
                }
            }
            this.textView.setText(str);
            if (this.changeAlpha != (str != null ? 1 : 0)) {
                ValueAnimator valueAnimator = this.changeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.changeAnimator = null;
                }
                if (z) {
                    this.generateButton.setVisibility(0);
                    this.optionsIcon.setVisibility(0);
                    this.copyButton.setVisibility(0);
                    this.shareButton.setVisibility(0);
                    float[] fArr = new float[2];
                    fArr[0] = this.changeAlpha;
                    fArr[1] = str == null ? 0.0f : 1.0f;
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                    this.changeAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            FilterChatlistActivity.InviteLinkCell.this.lambda$setLink$6(valueAnimator2);
                        }
                    });
                    this.changeAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (str == null) {
                                InviteLinkCell.this.generateButton.setVisibility(0);
                                InviteLinkCell.this.optionsIcon.setVisibility(8);
                                InviteLinkCell.this.copyButton.setVisibility(8);
                                InviteLinkCell.this.shareButton.setVisibility(8);
                                return;
                            }
                            InviteLinkCell.this.generateButton.setVisibility(8);
                            InviteLinkCell.this.optionsIcon.setVisibility(0);
                            InviteLinkCell.this.copyButton.setVisibility(0);
                            InviteLinkCell.this.shareButton.setVisibility(0);
                        }
                    });
                    this.changeAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.changeAnimator.setDuration(320L);
                    this.changeAnimator.start();
                    return;
                }
                this.changeAlpha = str == null ? 0.0f : 1.0f;
                updateChangeAlpha();
                if (str == null) {
                    this.generateButton.setVisibility(0);
                    this.optionsIcon.setVisibility(8);
                    this.copyButton.setVisibility(8);
                    this.shareButton.setVisibility(8);
                    return;
                }
                this.generateButton.setVisibility(8);
                this.optionsIcon.setVisibility(0);
                this.copyButton.setVisibility(0);
                this.shareButton.setVisibility(0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setLink$6(ValueAnimator valueAnimator) {
            this.changeAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            updateChangeAlpha();
        }

        private void updateChangeAlpha() {
            this.buttonsBox.setT(this.changeAlpha);
            this.copyButton.setAlpha(this.changeAlpha);
            this.shareButton.setAlpha(this.changeAlpha);
            this.optionsIcon.setAlpha(this.changeAlpha);
            this.generateButton.setAlpha(1.0f - this.changeAlpha);
            this.textView.setAlpha(this.changeAlpha);
            this.spoilerTextView.setAlpha(1.0f - this.changeAlpha);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(127.0f), 1073741824));
        }

        public void options() {
            if (this.actionBarPopupWindow != null || this.lastUrl == null) {
                return;
            }
            ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(getContext());
            ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(getContext(), true, false);
            actionBarMenuSubItem.setTextAndIcon(LocaleController.getString("EditName", R.string.EditName), R.drawable.msg_edit);
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem, LayoutHelper.createLinear(-1, 48));
            actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$options$7(view);
                }
            });
            ActionBarMenuSubItem actionBarMenuSubItem2 = new ActionBarMenuSubItem(getContext(), false, false);
            actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString("GetQRCode", R.string.GetQRCode), R.drawable.msg_qrcode);
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem2, LayoutHelper.createLinear(-1, 48));
            actionBarMenuSubItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$options$8(view);
                }
            });
            ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(getContext(), false, true);
            actionBarMenuSubItem3.setTextAndIcon(LocaleController.getString("DeleteLink", R.string.DeleteLink), R.drawable.msg_delete);
            int i = Theme.key_text_RedRegular;
            actionBarMenuSubItem3.setColors(Theme.getColor(i), Theme.getColor(i));
            actionBarMenuSubItem3.setSelectorColor(Theme.multAlpha(Theme.getColor(i), 0.12f));
            actionBarMenuSubItem3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FilterChatlistActivity.InviteLinkCell.this.lambda$options$9(view);
                }
            });
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem3, LayoutHelper.createLinear(-1, 48));
            final FrameLayout overlayContainerView = this.parentFragment.getParentLayout().getOverlayContainerView();
            if (overlayContainerView != null) {
                getPointOnScreen(this.linkBox, overlayContainerView, this.point);
                float f = this.point[1];
                final View view = new View(getContext()) { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.4
                    @Override // android.view.View
                    protected void onDraw(Canvas canvas) {
                        canvas.drawColor(AndroidUtilities.DARK_STATUS_BAR_OVERLAY);
                        InviteLinkCell inviteLinkCell = InviteLinkCell.this;
                        inviteLinkCell.getPointOnScreen(inviteLinkCell.linkBox, overlayContainerView, inviteLinkCell.point);
                        canvas.save();
                        float y = ((View) InviteLinkCell.this.linkBox.getParent()).getY() + InviteLinkCell.this.linkBox.getY();
                        if (y < 1.0f) {
                            canvas.clipRect(0.0f, (InviteLinkCell.this.point[1] - y) + 1.0f, getMeasuredWidth(), getMeasuredHeight());
                        }
                        canvas.translate(InviteLinkCell.this.point[0], InviteLinkCell.this.point[1]);
                        InviteLinkCell.this.linkBox.draw(canvas);
                        canvas.restore();
                    }
                };
                final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.5
                    @Override // android.view.ViewTreeObserver.OnPreDrawListener
                    public boolean onPreDraw() {
                        view.invalidate();
                        return true;
                    }
                };
                overlayContainerView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
                overlayContainerView.addView(view, LayoutHelper.createFrame(-1, -1.0f));
                float f2 = 0.0f;
                view.setAlpha(0.0f);
                view.animate().alpha(1.0f).setDuration(150L);
                actionBarPopupWindowLayout.measure(View.MeasureSpec.makeMeasureSpec(overlayContainerView.getMeasuredWidth(), 0), View.MeasureSpec.makeMeasureSpec(overlayContainerView.getMeasuredHeight(), 0));
                ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(actionBarPopupWindowLayout, -2, -2);
                this.actionBarPopupWindow = actionBarPopupWindow;
                actionBarPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.6
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        InviteLinkCell.this.actionBarPopupWindow = null;
                        view.animate().cancel();
                        view.animate().alpha(0.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.FilterChatlistActivity.InviteLinkCell.6.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                if (view.getParent() != null) {
                                    AnonymousClass6 anonymousClass6 = AnonymousClass6.this;
                                    overlayContainerView.removeView(view);
                                }
                                overlayContainerView.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
                            }
                        });
                    }
                });
                this.actionBarPopupWindow.setOutsideTouchable(true);
                this.actionBarPopupWindow.setFocusable(true);
                this.actionBarPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
                this.actionBarPopupWindow.setAnimationStyle(R.style.PopupContextAnimation);
                this.actionBarPopupWindow.setInputMethodMode(2);
                this.actionBarPopupWindow.setSoftInputMode(0);
                actionBarPopupWindowLayout.setDispatchKeyEventListener(new ActionBarPopupWindow.OnDispatchKeyEventListener() { // from class: org.telegram.ui.FilterChatlistActivity$InviteLinkCell$$ExternalSyntheticLambda10
                    @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener
                    public final void onDispatchKeyEvent(KeyEvent keyEvent) {
                        FilterChatlistActivity.InviteLinkCell.this.lambda$options$10(keyEvent);
                    }
                });
                if (AndroidUtilities.isTablet()) {
                    f += overlayContainerView.getPaddingTop();
                    f2 = 0.0f - overlayContainerView.getPaddingLeft();
                }
                this.actionBarPopupWindow.showAtLocation(overlayContainerView, 0, (int) (((overlayContainerView.getMeasuredWidth() - actionBarPopupWindowLayout.getMeasuredWidth()) - AndroidUtilities.dp(16.0f)) + overlayContainerView.getX() + f2), (int) (f + this.linkBox.getMeasuredHeight() + overlayContainerView.getY()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$options$7(View view) {
            ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
            editname();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$options$8(View view) {
            ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
            qrcode();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$options$9(View view) {
            ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
            if (actionBarPopupWindow != null) {
                actionBarPopupWindow.dismiss();
            }
            deleteLink();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$options$10(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && this.actionBarPopupWindow.isShowing()) {
                this.actionBarPopupWindow.dismiss(true);
            }
        }

        public void copy() {
            String str = this.lastUrl;
            if (str == null) {
                return;
            }
            AndroidUtilities.addToClipboard(str);
            BulletinFactory.of(this.parentFragment).createCopyBulletin(LocaleController.getString("LinkCopied", R.string.LinkCopied)).show();
        }

        protected void share() {
            if (this.lastUrl == null) {
                return;
            }
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType(COSRequestHeaderKey.TEXT_PLAIN);
                intent.putExtra("android.intent.extra.TEXT", this.lastUrl);
                this.parentFragment.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink)), 500);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void qrcode() {
            if (this.lastUrl == null) {
                return;
            }
            QRCodeBottomSheet qRCodeBottomSheet = new QRCodeBottomSheet(getContext(), LocaleController.getString("InviteByQRCode", R.string.InviteByQRCode), this.lastUrl, LocaleController.getString("QRCodeLinkHelpFolder", R.string.QRCodeLinkHelpFolder), false);
            qRCodeBottomSheet.setCenterAnimation(R.raw.qr_code_logo);
            qRCodeBottomSheet.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v0, types: [android.widget.FrameLayout] */
        /* JADX WARN: Type inference failed for: r4v1, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r4v9, types: [android.view.View] */
        public void getPointOnScreen(FrameLayout frameLayout, FrameLayout frameLayout2, float[] fArr) {
            float f = 0.0f;
            float f2 = 0.0f;
            while (frameLayout != frameLayout2) {
                f += frameLayout.getY();
                f2 += frameLayout.getX();
                if (frameLayout instanceof ScrollView) {
                    f -= frameLayout.getScrollY();
                }
                if (!(frameLayout.getParent() instanceof View)) {
                    break;
                }
                frameLayout = (View) frameLayout.getParent();
                if (!(frameLayout instanceof ViewGroup)) {
                    return;
                }
            }
            fArr[0] = f2 - frameLayout2.getPaddingLeft();
            fArr[1] = f - frameLayout2.getPaddingTop();
        }
    }
}
