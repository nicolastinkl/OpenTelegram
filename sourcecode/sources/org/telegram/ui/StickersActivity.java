package org.telegram.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_messages_reorderStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.FeaturedStickerSetCell2;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.RadioColorCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.StickerSetCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.Bulletin;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.EmojiPacksAlert;
import org.telegram.ui.Components.ItemOptions;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberTextView;
import org.telegram.ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.ReorderingBulletinLayout;
import org.telegram.ui.Components.ShareAlert;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.TrendingStickersAlert;
import org.telegram.ui.Components.TrendingStickersLayout;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.StickersActivity;

/* loaded from: classes3.dex */
public class StickersActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private int activeReorderingRequests;
    private int archivedInfoRow;
    private int archivedRow;
    private int currentType;
    private ActionBarMenuItem deleteMenuItem;
    private int dynamicPackOrder;
    private int dynamicPackOrderInfo;
    private int emojiPacksRow;
    private int featuredRow;
    private int featuredStickersEndRow;
    private int featuredStickersHeaderRow;
    private int featuredStickersShadowRow;
    private int featuredStickersShowMoreRow;
    private int featuredStickersStartRow;
    ArrayList<TLRPC$TL_messages_stickerSet> frozenEmojiPacks;
    private boolean isListeningForFeaturedUpdate;
    private ItemTouchHelper itemTouchHelper;
    private int largeEmojiRow;
    private LinearLayoutManager layoutManager;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int loopInfoRow;
    private int loopRow;
    private int masksInfoRow;
    private int masksRow;
    private boolean needReorder;
    private int reactionsDoubleTapRow;
    private int rowCount;
    private NumberTextView selectedCountTextView;
    private int stickersBotInfo;
    private int stickersEndRow;
    private int stickersHeaderRow;
    private int stickersSettingsRow;
    private int stickersShadowRow;
    private int stickersStartRow;
    private int suggestAnimatedEmojiInfoRow;
    private int suggestAnimatedEmojiRow;
    private int suggestRow;
    private TrendingStickersAlert trendingStickersAlert;
    private boolean updateSuggestStickers;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createView$0(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<TLRPC$StickerSetCovered> getFeaturedSets() {
        MediaDataController mediaDataController = MediaDataController.getInstance(this.currentAccount);
        if (this.currentType == 5) {
            ArrayList arrayList = new ArrayList(mediaDataController.getFeaturedEmojiSets());
            int i = 0;
            while (i < arrayList.size()) {
                if (arrayList.get(i) == null || mediaDataController.isStickerPackInstalled(((TLRPC$StickerSetCovered) arrayList.get(i)).set.id, false)) {
                    arrayList.remove(i);
                    i--;
                }
                i++;
            }
            return arrayList;
        }
        return mediaDataController.getFeaturedStickerSets();
    }

    public class TouchHelperCallback extends ItemTouchHelper.Callback {
        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        }

        public TouchHelperCallback() {
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean isLongPressDragEnabled() {
            return StickersActivity.this.listAdapter.hasSelected();
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() != 0) {
                return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
            }
            return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            if (viewHolder.getItemViewType() != viewHolder2.getItemViewType()) {
                return false;
            }
            StickersActivity.this.listAdapter.swapElements(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (i == 0) {
                StickersActivity.this.sendReorder();
            } else {
                StickersActivity.this.listView.cancelClickRunnables(false);
                viewHolder.itemView.setPressed(true);
            }
            super.onSelectedChanged(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setPressed(false);
        }
    }

    public StickersActivity(int i, ArrayList<TLRPC$TL_messages_stickerSet> arrayList) {
        this.currentType = i;
        this.frozenEmojiPacks = arrayList;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        MediaDataController.getInstance(this.currentAccount).checkStickers(this.currentType);
        int i = this.currentType;
        if (i == 0) {
            MediaDataController.getInstance(this.currentAccount).checkFeaturedStickers();
            MediaDataController.getInstance(this.currentAccount).checkStickers(1);
            MediaDataController.getInstance(this.currentAccount).checkStickers(5);
        } else if (i == 6) {
            MediaDataController.getInstance(this.currentAccount).checkFeaturedEmoji();
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.featuredEmojiDidLoad);
        }
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.archivedStickersCountDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.featuredStickersDidLoad);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        updateRows(false);
        return true;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.currentType == 6) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.featuredEmojiDidLoad);
        }
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.archivedStickersCountDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.featuredStickersDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    @SuppressLint({"ClickableViewAccessibility"})
    public View createView(final Context context) {
        ArrayList<TLRPC$TL_messages_stickerSet> arrayList;
        this.actionBar.setBackButtonDrawable(new BackDrawable(false));
        this.actionBar.setAllowOverlayTitle(true);
        int i = this.currentType;
        if (i == 0) {
            this.actionBar.setTitle(LocaleController.getString("StickersName", R.string.StickersName));
        } else if (i == 1) {
            this.actionBar.setTitle(LocaleController.getString("Masks", R.string.Masks));
        } else if (i == 5) {
            this.actionBar.setTitle(LocaleController.getString("Emoji", R.string.Emoji));
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.StickersActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i2) {
                if (i2 == -1) {
                    if (StickersActivity.this.onBackPressed()) {
                        StickersActivity.this.finishFragment();
                    }
                } else if (i2 == 0 || i2 == 1 || i2 == 2) {
                    if (!StickersActivity.this.needReorder) {
                        if (StickersActivity.this.activeReorderingRequests == 0) {
                            StickersActivity.this.listAdapter.processSelectionMenu(i2);
                            return;
                        }
                        return;
                    }
                    StickersActivity.this.sendReorder();
                }
            }
        });
        ActionBarMenu createActionMode = this.actionBar.createActionMode();
        NumberTextView numberTextView = new NumberTextView(createActionMode.getContext());
        this.selectedCountTextView = numberTextView;
        numberTextView.setTextSize(18);
        this.selectedCountTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.selectedCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        createActionMode.addView(this.selectedCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 72, 0, 0, 0));
        this.selectedCountTextView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$createView$0;
                lambda$createView$0 = StickersActivity.lambda$createView$0(view, motionEvent);
                return lambda$createView$0;
            }
        });
        createActionMode.addItemWithWidth(2, R.drawable.msg_share, AndroidUtilities.dp(54.0f));
        createActionMode.addItemWithWidth(0, R.drawable.msg_archive, AndroidUtilities.dp(54.0f));
        this.deleteMenuItem = createActionMode.addItemWithWidth(1, R.drawable.msg_delete, AndroidUtilities.dp(54.0f));
        if (this.currentType != 5 || (arrayList = this.frozenEmojiPacks) == null) {
            arrayList = new ArrayList<>(MessagesController.getInstance(this.currentAccount).filterPremiumStickers(MediaDataController.getInstance(this.currentAccount).getStickerSets(this.currentType)));
        }
        this.listAdapter = new ListAdapter(context, arrayList, getFeaturedSets());
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        FrameLayout frameLayout2 = frameLayout;
        frameLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        RecyclerListView recyclerListView = new RecyclerListView(context) { // from class: org.telegram.ui.StickersActivity.2
            @Override // org.telegram.ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                if (((BaseFragment) StickersActivity.this).actionBar.isActionModeShowed()) {
                    drawSectionBackground(canvas, StickersActivity.this.stickersHeaderRow, StickersActivity.this.stickersEndRow, getThemedColor(Theme.key_windowBackgroundWhite));
                }
                super.dispatchDraw(canvas);
            }
        };
        this.listView = recyclerListView;
        recyclerListView.setFocusable(true);
        this.listView.setTag(7);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator() { // from class: org.telegram.ui.StickersActivity.3
            @Override // androidx.recyclerview.widget.DefaultItemAnimator
            protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                super.onMoveAnimationUpdate(viewHolder);
                StickersActivity.this.listView.invalidate();
            }
        };
        defaultItemAnimator.setMoveDuration(350L);
        defaultItemAnimator.setMoveInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.listView.setItemAnimator(defaultItemAnimator);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) { // from class: org.telegram.ui.StickersActivity.4
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            protected void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
                iArr[1] = StickersActivity.this.listView.getHeight();
            }
        };
        this.layoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(1);
        this.listView.setLayoutManager(this.layoutManager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback());
        this.itemTouchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.listView);
        ((DefaultItemAnimator) this.listView.getItemAnimator()).setSupportsChangeAnimations(false);
        frameLayout2.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                StickersActivity.this.lambda$createView$2(context, view, i2);
            }
        });
        this.listView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i2) {
                boolean lambda$createView$3;
                lambda$createView$3 = StickersActivity.this.lambda$createView$3(view, i2);
                return lambda$createView$3;
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(Context context, View view, int i) {
        if (i < this.featuredStickersStartRow || i >= this.featuredStickersEndRow || getParentActivity() == null) {
            if (i == this.featuredStickersShowMoreRow || i == this.featuredRow) {
                if (this.currentType == 5) {
                    ArrayList arrayList = new ArrayList();
                    List<TLRPC$StickerSetCovered> featuredSets = getFeaturedSets();
                    if (featuredSets != null) {
                        for (int i2 = 0; i2 < featuredSets.size(); i2++) {
                            TLRPC$StickerSetCovered tLRPC$StickerSetCovered = featuredSets.get(i2);
                            if (tLRPC$StickerSetCovered != null && tLRPC$StickerSetCovered.set != null) {
                                TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                                TLRPC$StickerSet tLRPC$StickerSet = tLRPC$StickerSetCovered.set;
                                tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSet.id;
                                tLRPC$TL_inputStickerSetID.access_hash = tLRPC$StickerSet.access_hash;
                                arrayList.add(tLRPC$TL_inputStickerSetID);
                            }
                        }
                    }
                    MediaDataController.getInstance(this.currentAccount).markFeaturedStickersAsRead(true, true);
                    showDialog(new EmojiPacksAlert(this, getParentActivity(), getResourceProvider(), arrayList));
                    return;
                }
                TrendingStickersAlert trendingStickersAlert = new TrendingStickersAlert(context, this, new TrendingStickersLayout(context, new TrendingStickersLayout.Delegate() { // from class: org.telegram.ui.StickersActivity.5
                    @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
                    public void onStickerSetAdd(TLRPC$StickerSetCovered tLRPC$StickerSetCovered2, boolean z) {
                        MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), tLRPC$StickerSetCovered2, 2, StickersActivity.this, false, false);
                    }

                    @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
                    public void onStickerSetRemove(TLRPC$StickerSetCovered tLRPC$StickerSetCovered2) {
                        MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), tLRPC$StickerSetCovered2, 0, StickersActivity.this, false, false);
                    }
                }), null);
                this.trendingStickersAlert = trendingStickersAlert;
                trendingStickersAlert.show();
                return;
            }
            if (i >= this.stickersStartRow && i < this.stickersEndRow && getParentActivity() != null) {
                if (this.listAdapter.hasSelected()) {
                    this.listAdapter.toggleSelected(i);
                    return;
                }
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) this.listAdapter.stickerSets.get(i - this.stickersStartRow);
                ArrayList<TLRPC$Document> arrayList2 = tLRPC$TL_messages_stickerSet.documents;
                if (arrayList2 == null || arrayList2.isEmpty()) {
                    return;
                }
                TLRPC$StickerSet tLRPC$StickerSet2 = tLRPC$TL_messages_stickerSet.set;
                if (tLRPC$StickerSet2 != null && tLRPC$StickerSet2.emojis) {
                    ArrayList arrayList3 = new ArrayList();
                    TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID2 = new TLRPC$TL_inputStickerSetID();
                    TLRPC$StickerSet tLRPC$StickerSet3 = tLRPC$TL_messages_stickerSet.set;
                    tLRPC$TL_inputStickerSetID2.id = tLRPC$StickerSet3.id;
                    tLRPC$TL_inputStickerSetID2.access_hash = tLRPC$StickerSet3.access_hash;
                    arrayList3.add(tLRPC$TL_inputStickerSetID2);
                    showDialog(new EmojiPacksAlert(this, getParentActivity(), getResourceProvider(), arrayList3));
                    return;
                }
                showDialog(new StickersAlert(getParentActivity(), this, (TLRPC$InputStickerSet) null, tLRPC$TL_messages_stickerSet, (StickersAlert.StickersAlertDelegate) null));
                return;
            }
            if (i == this.archivedRow) {
                presentFragment(new ArchivedStickersActivity(this.currentType));
                return;
            }
            if (i == this.masksRow) {
                presentFragment(new StickersActivity(1, null));
                return;
            }
            if (i == this.emojiPacksRow) {
                presentFragment(new StickersActivity(5, null));
                return;
            }
            if (i == this.suggestRow) {
                if (getParentActivity() == null) {
                    return;
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("SuggestStickers", R.string.SuggestStickers));
                String[] strArr = {LocaleController.getString("SuggestStickersAll", R.string.SuggestStickersAll), LocaleController.getString("SuggestStickersInstalled", R.string.SuggestStickersInstalled), LocaleController.getString("SuggestStickersNone", R.string.SuggestStickersNone)};
                LinearLayout linearLayout = new LinearLayout(getParentActivity());
                linearLayout.setOrientation(1);
                builder.setView(linearLayout);
                int i3 = 0;
                while (i3 < 3) {
                    RadioColorCell radioColorCell = new RadioColorCell(getParentActivity());
                    radioColorCell.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
                    radioColorCell.setTag(Integer.valueOf(i3));
                    radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
                    radioColorCell.setTextAndValue(strArr[i3], SharedConfig.suggestStickers == i3);
                    radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
                    linearLayout.addView(radioColorCell);
                    radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            StickersActivity.this.lambda$createView$1(builder, view2);
                        }
                    });
                    i3++;
                }
                showDialog(builder.create());
                return;
            }
            if (i == this.loopRow) {
                SharedConfig.toggleLoopStickers();
                this.listAdapter.notifyItemChanged(this.loopRow, 0);
                return;
            }
            if (i == this.largeEmojiRow) {
                SharedConfig.toggleBigEmoji();
                ((TextCheckCell) view).setChecked(SharedConfig.allowBigEmoji);
                return;
            }
            if (i == this.suggestAnimatedEmojiRow) {
                SharedConfig.toggleSuggestAnimatedEmoji();
                ((TextCheckCell) view).setChecked(SharedConfig.suggestAnimatedEmoji);
                return;
            } else if (i == this.reactionsDoubleTapRow) {
                presentFragment(new ReactionsDoubleTapManageActivity());
                return;
            } else {
                if (i == this.dynamicPackOrder) {
                    SharedConfig.toggleUpdateStickersOrderOnSend();
                    ((TextCheckCell) view).setChecked(SharedConfig.updateStickersOrderOnSend);
                    return;
                }
                return;
            }
        }
        TLRPC$StickerSetCovered tLRPC$StickerSetCovered2 = (TLRPC$StickerSetCovered) this.listAdapter.featuredStickerSets.get(i - this.featuredStickersStartRow);
        TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID3 = new TLRPC$TL_inputStickerSetID();
        TLRPC$StickerSet tLRPC$StickerSet4 = tLRPC$StickerSetCovered2.set;
        tLRPC$TL_inputStickerSetID3.id = tLRPC$StickerSet4.id;
        tLRPC$TL_inputStickerSetID3.access_hash = tLRPC$StickerSet4.access_hash;
        if (this.currentType == 5) {
            ArrayList arrayList4 = new ArrayList(1);
            arrayList4.add(tLRPC$TL_inputStickerSetID3);
            showDialog(new EmojiPacksAlert(this, getParentActivity(), getResourceProvider(), arrayList4));
            return;
        }
        showDialog(new StickersAlert(getParentActivity(), this, tLRPC$TL_inputStickerSetID3, (TLRPC$TL_messages_stickerSet) null, (StickersAlert.StickersAlertDelegate) null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(AlertDialog.Builder builder, View view) {
        SharedConfig.setSuggestStickers(((Integer) view.getTag()).intValue());
        this.updateSuggestStickers = true;
        this.listAdapter.notifyItemChanged(this.suggestRow);
        builder.getDismissRunnable().run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$3(View view, int i) {
        if (this.listAdapter.hasSelected() || i < this.stickersStartRow || i >= this.stickersEndRow) {
            return false;
        }
        this.listAdapter.toggleSelected(i);
        return true;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (this.listAdapter.hasSelected()) {
            this.listAdapter.clearSelected();
            return false;
        }
        return super.onBackPressed();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.stickersDidLoad) {
            int intValue = ((Integer) objArr[0]).intValue();
            int i3 = this.currentType;
            if (intValue == i3) {
                this.listAdapter.loadingFeaturedStickerSets.clear();
                updateRows(((Boolean) objArr[1]).booleanValue());
                return;
            } else {
                if (i3 == 0 && intValue == 1) {
                    this.listAdapter.notifyItemChanged(this.masksRow);
                    return;
                }
                return;
            }
        }
        if (i == NotificationCenter.featuredStickersDidLoad || i == NotificationCenter.featuredEmojiDidLoad) {
            updateRows(false);
        } else {
            if (i == NotificationCenter.archivedStickersCountDidLoad) {
                if (((Integer) objArr[0]).intValue() == this.currentType) {
                    updateRows(false);
                    return;
                }
                return;
            }
            int i4 = NotificationCenter.currentUserPremiumStatusChanged;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendReorder() {
        if (this.needReorder) {
            MediaDataController.getInstance(this.currentAccount).calcNewHash(this.currentType);
            this.needReorder = false;
            this.activeReorderingRequests++;
            TLRPC$TL_messages_reorderStickerSets tLRPC$TL_messages_reorderStickerSets = new TLRPC$TL_messages_reorderStickerSets();
            int i = this.currentType;
            tLRPC$TL_messages_reorderStickerSets.masks = i == 1;
            tLRPC$TL_messages_reorderStickerSets.emojis = i == 5;
            for (int i2 = 0; i2 < this.listAdapter.stickerSets.size(); i2++) {
                tLRPC$TL_messages_reorderStickerSets.order.add(Long.valueOf(((TLRPC$TL_messages_stickerSet) this.listAdapter.stickerSets.get(i2)).set.id));
            }
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_messages_reorderStickerSets, new RequestDelegate() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda3
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    StickersActivity.this.lambda$sendReorder$5(tLObject, tLRPC$TL_error);
                }
            });
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.stickersDidLoad, Integer.valueOf(this.currentType), Boolean.TRUE);
            if (!SharedConfig.updateStickersOrderOnSend || this.dynamicPackOrder == -1) {
                return;
            }
            SharedConfig.toggleUpdateStickersOrderOnSend();
            BulletinFactory.of(this).createSimpleBulletin(R.raw.filter_reorder, LocaleController.getString("DynamicPackOrderOff", R.string.DynamicPackOrderOff), LocaleController.getString("DynamicPackOrderOffInfo", R.string.DynamicPackOrderOffInfo)).show();
            for (int i3 = 0; i3 < this.listView.getChildCount(); i3++) {
                View childAt = this.listView.getChildAt(i3);
                if (this.listView.getChildAdapterPosition(childAt) == this.dynamicPackOrder && (childAt instanceof TextCheckCell)) {
                    ((TextCheckCell) childAt).setChecked(SharedConfig.updateStickersOrderOnSend);
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendReorder$4() {
        this.activeReorderingRequests--;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendReorder$5(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.StickersActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                StickersActivity.this.lambda$sendReorder$4();
            }
        });
    }

    private void updateRows(boolean z) {
        final ArrayList<TLRPC$TL_messages_stickerSet> arrayList;
        boolean z2;
        DiffUtil.DiffResult diffResult;
        MediaDataController mediaDataController = MediaDataController.getInstance(this.currentAccount);
        if (this.currentType == 5) {
            if (z || this.frozenEmojiPacks == null) {
                this.frozenEmojiPacks = new ArrayList<>(MessagesController.getInstance(this.currentAccount).filterPremiumStickers(mediaDataController.getStickerSets(this.currentType)));
            }
            arrayList = this.frozenEmojiPacks;
        } else {
            arrayList = new ArrayList<>(MessagesController.getInstance(this.currentAccount).filterPremiumStickers(mediaDataController.getStickerSets(this.currentType)));
        }
        List<TLRPC$StickerSetCovered> featuredSets = getFeaturedSets();
        if (featuredSets.size() > 3) {
            featuredSets = featuredSets.subList(0, 3);
            z2 = true;
        } else {
            z2 = false;
        }
        final ArrayList arrayList2 = new ArrayList(featuredSets);
        if (this.currentType == 5) {
            boolean isPremium = UserConfig.getInstance(this.currentAccount).isPremium();
            if (!isPremium) {
                int i = 0;
                while (true) {
                    if (i >= arrayList.size()) {
                        break;
                    }
                    if (!MessageObject.isPremiumEmojiPack(arrayList.get(i))) {
                        isPremium = true;
                        break;
                    }
                    i++;
                }
            }
            if (!isPremium) {
                for (int i2 = 0; i2 < featuredSets.size() && MessageObject.isPremiumEmojiPack(featuredSets.get(i2)); i2++) {
                }
            }
        }
        DiffUtil.DiffResult diffResult2 = null;
        if (this.listAdapter != null) {
            if (this.isPaused) {
                diffResult = null;
            } else {
                diffResult2 = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: org.telegram.ui.StickersActivity.6
                    List<TLRPC$TL_messages_stickerSet> oldList;

                    {
                        this.oldList = StickersActivity.this.listAdapter.stickerSets;
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public int getOldListSize() {
                        return this.oldList.size();
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public int getNewListSize() {
                        return arrayList.size();
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public boolean areItemsTheSame(int i3, int i4) {
                        return this.oldList.get(i3).set.id == ((TLRPC$TL_messages_stickerSet) arrayList.get(i4)).set.id;
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public boolean areContentsTheSame(int i3, int i4) {
                        TLRPC$StickerSet tLRPC$StickerSet = this.oldList.get(i3).set;
                        TLRPC$StickerSet tLRPC$StickerSet2 = ((TLRPC$TL_messages_stickerSet) arrayList.get(i4)).set;
                        return TextUtils.equals(tLRPC$StickerSet.title, tLRPC$StickerSet2.title) && tLRPC$StickerSet.count == tLRPC$StickerSet2.count;
                    }
                });
                diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: org.telegram.ui.StickersActivity.7
                    List<TLRPC$StickerSetCovered> oldList;

                    {
                        this.oldList = StickersActivity.this.listAdapter.featuredStickerSets;
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public int getOldListSize() {
                        return this.oldList.size();
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public int getNewListSize() {
                        return arrayList2.size();
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public boolean areItemsTheSame(int i3, int i4) {
                        return this.oldList.get(i3).set.id == ((TLRPC$StickerSetCovered) arrayList2.get(i4)).set.id;
                    }

                    @Override // androidx.recyclerview.widget.DiffUtil.Callback
                    public boolean areContentsTheSame(int i3, int i4) {
                        TLRPC$StickerSet tLRPC$StickerSet = this.oldList.get(i3).set;
                        TLRPC$StickerSet tLRPC$StickerSet2 = ((TLRPC$StickerSetCovered) arrayList2.get(i4)).set;
                        return TextUtils.equals(tLRPC$StickerSet.title, tLRPC$StickerSet2.title) && tLRPC$StickerSet.count == tLRPC$StickerSet2.count && tLRPC$StickerSet.installed == tLRPC$StickerSet2.installed;
                    }
                });
            }
            this.listAdapter.setStickerSets(arrayList);
            this.listAdapter.setFeaturedStickerSets(arrayList2);
        } else {
            diffResult = null;
        }
        this.rowCount = 0;
        this.loopRow = -1;
        this.loopInfoRow = -1;
        this.archivedRow = -1;
        int i3 = this.currentType;
        if (i3 == 0) {
            this.rowCount = 0 + 1;
            this.featuredRow = 0;
            this.masksRow = -1;
            if (mediaDataController.getArchivedStickersCount(i3) != 0) {
                boolean z3 = this.archivedRow == -1;
                int i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.archivedRow = i4;
                ListAdapter listAdapter = this.listAdapter;
                if (listAdapter != null && z3) {
                    listAdapter.notifyItemRangeInserted(i4, 1);
                }
            }
            this.archivedInfoRow = -1;
            int i5 = this.rowCount;
            this.rowCount = i5 + 1;
            this.emojiPacksRow = i5;
        } else {
            this.featuredRow = -1;
            this.masksRow = -1;
            this.emojiPacksRow = -1;
            if (mediaDataController.getArchivedStickersCount(i3) != 0) {
                boolean z4 = this.archivedRow == -1;
                int i6 = this.rowCount;
                int i7 = i6 + 1;
                this.rowCount = i7;
                this.archivedRow = i6;
                if (this.currentType == 1) {
                    this.rowCount = i7 + 1;
                } else {
                    i7 = -1;
                }
                this.archivedInfoRow = i7;
                ListAdapter listAdapter2 = this.listAdapter;
                if (listAdapter2 != null && z4) {
                    listAdapter2.notifyItemRangeInserted(i6, i7 == -1 ? 1 : 2);
                }
            } else {
                int i8 = this.archivedRow;
                int i9 = this.archivedInfoRow;
                this.archivedRow = -1;
                this.archivedInfoRow = -1;
                ListAdapter listAdapter3 = this.listAdapter;
                if (listAdapter3 != null && i8 != -1) {
                    listAdapter3.notifyItemRangeRemoved(i8, i9 == -1 ? 1 : 2);
                }
            }
        }
        int i10 = this.currentType;
        if (i10 == 5) {
            int i11 = this.rowCount;
            int i12 = i11 + 1;
            this.rowCount = i12;
            this.suggestAnimatedEmojiRow = i11;
            this.rowCount = i12 + 1;
            this.suggestAnimatedEmojiInfoRow = i12;
        } else {
            this.suggestAnimatedEmojiRow = -1;
            this.suggestAnimatedEmojiInfoRow = -1;
        }
        if (i10 == 0) {
            int i13 = this.rowCount;
            this.rowCount = i13 + 1;
            this.reactionsDoubleTapRow = i13;
        } else {
            this.reactionsDoubleTapRow = -1;
        }
        this.stickersBotInfo = -1;
        if (i10 == 0) {
            int i14 = this.rowCount;
            this.rowCount = i14 + 1;
            this.stickersBotInfo = i14;
        }
        this.featuredStickersHeaderRow = -1;
        this.featuredStickersStartRow = -1;
        this.featuredStickersEndRow = -1;
        this.featuredStickersShowMoreRow = -1;
        this.featuredStickersShadowRow = -1;
        if (i10 == 0) {
            int i15 = this.rowCount;
            int i16 = i15 + 1;
            this.rowCount = i16;
            this.stickersSettingsRow = i15;
            int i17 = i16 + 1;
            this.rowCount = i17;
            this.suggestRow = i16;
            int i18 = i17 + 1;
            this.rowCount = i18;
            this.largeEmojiRow = i17;
            int i19 = i18 + 1;
            this.rowCount = i19;
            this.dynamicPackOrder = i18;
            this.rowCount = i19 + 1;
            this.dynamicPackOrderInfo = i19;
        } else {
            this.stickersSettingsRow = -1;
            this.suggestRow = -1;
            this.largeEmojiRow = -1;
            this.dynamicPackOrder = -1;
            this.dynamicPackOrderInfo = -1;
        }
        int size = arrayList.size();
        if (size > 0) {
            if (this.currentType == 5 || (!arrayList2.isEmpty() && this.currentType == 0)) {
                int i20 = this.rowCount;
                this.rowCount = i20 + 1;
                this.stickersHeaderRow = i20;
            } else {
                this.stickersHeaderRow = -1;
            }
            int i21 = this.rowCount;
            this.stickersStartRow = i21;
            int i22 = i21 + size;
            this.rowCount = i22;
            this.stickersEndRow = i22;
            int i23 = this.currentType;
            if (i23 != 1 && i23 != 5) {
                this.rowCount = i22 + 1;
                this.stickersShadowRow = i22;
                this.masksInfoRow = -1;
            } else if (i23 == 1) {
                this.rowCount = i22 + 1;
                this.masksInfoRow = i22;
                this.stickersShadowRow = -1;
            } else {
                this.stickersShadowRow = -1;
                this.masksInfoRow = -1;
            }
        } else {
            this.stickersHeaderRow = -1;
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
            this.masksInfoRow = -1;
        }
        if (!arrayList2.isEmpty() && this.currentType == 5) {
            if (size > 0) {
                int i24 = this.rowCount;
                this.rowCount = i24 + 1;
                this.featuredStickersShadowRow = i24;
            }
            int i25 = this.rowCount;
            int i26 = i25 + 1;
            this.rowCount = i26;
            this.featuredStickersHeaderRow = i25;
            this.featuredStickersStartRow = i26;
            int size2 = i26 + arrayList2.size();
            this.rowCount = size2;
            this.featuredStickersEndRow = size2;
            if (z2) {
                this.rowCount = size2 + 1;
                this.featuredStickersShowMoreRow = size2;
            }
        }
        if (this.currentType == 5) {
            int i27 = this.rowCount;
            this.rowCount = i27 + 1;
            this.stickersBotInfo = i27;
        }
        ListAdapter listAdapter4 = this.listAdapter;
        if (listAdapter4 != null) {
            if (diffResult2 != null) {
                final int i28 = this.stickersStartRow;
                if (i28 < 0) {
                    i28 = this.rowCount;
                }
                listAdapter4.notifyItemRangeChanged(0, i28);
                diffResult2.dispatchUpdatesTo(new ListUpdateCallback() { // from class: org.telegram.ui.StickersActivity.8
                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onInserted(int i29, int i30) {
                        StickersActivity.this.listAdapter.notifyItemRangeInserted(i28 + i29, i30);
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onRemoved(int i29, int i30) {
                        StickersActivity.this.listAdapter.notifyItemRangeRemoved(i28 + i29, i30);
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onMoved(int i29, int i30) {
                        if (StickersActivity.this.currentType == 5) {
                            ListAdapter listAdapter5 = StickersActivity.this.listAdapter;
                            int i31 = i28;
                            listAdapter5.notifyItemMoved(i29 + i31, i31 + i30);
                        }
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onChanged(int i29, int i30, Object obj) {
                        StickersActivity.this.listAdapter.notifyItemRangeChanged(i28 + i29, i30);
                    }
                });
            }
            if (diffResult != null) {
                final int i29 = this.featuredStickersStartRow;
                if (i29 < 0) {
                    i29 = this.rowCount;
                }
                this.listAdapter.notifyItemRangeChanged(0, i29);
                diffResult.dispatchUpdatesTo(new ListUpdateCallback() { // from class: org.telegram.ui.StickersActivity.9
                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onMoved(int i30, int i31) {
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onInserted(int i30, int i31) {
                        StickersActivity.this.listAdapter.notifyItemRangeInserted(i29 + i30, i31);
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onRemoved(int i30, int i31) {
                        StickersActivity.this.listAdapter.notifyItemRangeRemoved(i29 + i30, i31);
                    }

                    @Override // androidx.recyclerview.widget.ListUpdateCallback
                    public void onChanged(int i30, int i31, Object obj) {
                        StickersActivity.this.listAdapter.notifyItemRangeChanged(i29 + i30, i31);
                    }
                });
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    @SuppressLint({"NotifyDataSetChanged"})
    public void onResume() {
        super.onResume();
        ListAdapter listAdapter = this.listAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;
        private final LongSparseArray<Boolean> selectedItems = new LongSparseArray<>();
        private final List<TLRPC$TL_messages_stickerSet> stickerSets = new ArrayList();
        private final List<TLRPC$StickerSetCovered> featuredStickerSets = new ArrayList();
        private final List<Long> loadingFeaturedStickerSets = new ArrayList();

        public ListAdapter(Context context, List<TLRPC$TL_messages_stickerSet> list, List<TLRPC$StickerSetCovered> list2) {
            this.mContext = context;
            setStickerSets(list);
            if (list2.size() > 3) {
                setFeaturedStickerSets(list2.subList(0, 3));
            } else {
                setFeaturedStickerSets(list2);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @SuppressLint({"NotifyDataSetChanged"})
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            if (StickersActivity.this.isListeningForFeaturedUpdate) {
                StickersActivity.this.isListeningForFeaturedUpdate = false;
            }
        }

        public void setStickerSets(List<TLRPC$TL_messages_stickerSet> list) {
            this.stickerSets.clear();
            this.stickerSets.addAll(list);
        }

        public void setFeaturedStickerSets(List<TLRPC$StickerSetCovered> list) {
            this.featuredStickerSets.clear();
            this.featuredStickerSets.addAll(list);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return StickersActivity.this.rowCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            if (i < StickersActivity.this.featuredStickersStartRow || i >= StickersActivity.this.featuredStickersEndRow) {
                return (i < StickersActivity.this.stickersStartRow || i >= StickersActivity.this.stickersEndRow) ? i : this.stickerSets.get(i - StickersActivity.this.stickersStartRow).set.id;
            }
            return this.featuredStickerSets.get(i - StickersActivity.this.featuredStickersStartRow).set.id;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void processSelectionMenu(final int i) {
            String string;
            TextView textView;
            int i2 = 0;
            if (i == 2) {
                StringBuilder sb = new StringBuilder();
                int size = this.stickerSets.size();
                while (i2 < size) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = this.stickerSets.get(i2);
                    if (this.selectedItems.get(tLRPC$TL_messages_stickerSet.set.id, Boolean.FALSE).booleanValue()) {
                        if (sb.length() != 0) {
                            sb.append("\n");
                        }
                        sb.append(StickersActivity.this.getLinkForSet(tLRPC$TL_messages_stickerSet));
                    }
                    i2++;
                }
                String sb2 = sb.toString();
                ShareAlert createShareAlert = ShareAlert.createShareAlert(((BaseFragment) StickersActivity.this).fragmentView.getContext(), null, sb2, false, sb2, false);
                createShareAlert.setDelegate(new ShareAlert.ShareAlertDelegate() { // from class: org.telegram.ui.StickersActivity.ListAdapter.1
                    @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
                    public void didShare() {
                        ListAdapter.this.clearSelected();
                    }

                    @Override // org.telegram.ui.Components.ShareAlert.ShareAlertDelegate
                    public boolean didCopy() {
                        ListAdapter.this.clearSelected();
                        return true;
                    }
                });
                createShareAlert.show();
                return;
            }
            if (i == 0 || i == 1) {
                final ArrayList arrayList = new ArrayList(this.selectedItems.size());
                int size2 = this.stickerSets.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    TLRPC$StickerSet tLRPC$StickerSet = this.stickerSets.get(i3).set;
                    if (this.selectedItems.get(tLRPC$StickerSet.id, Boolean.FALSE).booleanValue()) {
                        arrayList.add(tLRPC$StickerSet);
                    }
                }
                int size3 = arrayList.size();
                if (size3 != 0) {
                    if (size3 == 1) {
                        int size4 = this.stickerSets.size();
                        while (true) {
                            if (i2 >= size4) {
                                break;
                            }
                            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2 = this.stickerSets.get(i2);
                            if (this.selectedItems.get(tLRPC$TL_messages_stickerSet2.set.id, Boolean.FALSE).booleanValue()) {
                                processSelectionOption(i, tLRPC$TL_messages_stickerSet2);
                                break;
                            }
                            i2++;
                        }
                        StickersActivity.this.listAdapter.clearSelected();
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StickersActivity.this.getParentActivity());
                    if (i == 1) {
                        builder.setTitle(LocaleController.formatString("DeleteStickerSetsAlertTitle", R.string.DeleteStickerSetsAlertTitle, LocaleController.formatPluralString("StickerSets", size3, new Object[0])));
                        builder.setMessage(LocaleController.formatString("DeleteStickersAlertMessage", R.string.DeleteStickersAlertMessage, Integer.valueOf(size3)));
                        string = LocaleController.getString("Delete", R.string.Delete);
                    } else {
                        builder.setTitle(LocaleController.formatString("ArchiveStickerSetsAlertTitle", R.string.ArchiveStickerSetsAlertTitle, LocaleController.formatPluralString("StickerSets", size3, new Object[0])));
                        builder.setMessage(LocaleController.formatString("ArchiveStickersAlertMessage", R.string.ArchiveStickersAlertMessage, Integer.valueOf(size3)));
                        string = LocaleController.getString("Archive", R.string.Archive);
                    }
                    builder.setPositiveButton(string, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            StickersActivity.ListAdapter.this.lambda$processSelectionMenu$0(arrayList, i, dialogInterface, i4);
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    AlertDialog create = builder.create();
                    StickersActivity.this.showDialog(create);
                    if (i != 1 || (textView = (TextView) create.getButton(-1)) == null) {
                        return;
                    }
                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$processSelectionMenu$0(ArrayList arrayList, int i, DialogInterface dialogInterface, int i2) {
            StickersActivity.this.listAdapter.clearSelected();
            MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSets(arrayList, StickersActivity.this.currentType, i == 1 ? 0 : 1, StickersActivity.this, true);
        }

        private void processSelectionOption(int i, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            int indexOf;
            if (i == 0) {
                MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), tLRPC$TL_messages_stickerSet, !tLRPC$TL_messages_stickerSet.set.archived ? 1 : 2, StickersActivity.this, true, true);
                return;
            }
            if (i == 1) {
                MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), tLRPC$TL_messages_stickerSet, 0, StickersActivity.this, true, true);
                return;
            }
            if (i == 2) {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType(COSRequestHeaderKey.TEXT_PLAIN);
                    intent.putExtra("android.intent.extra.TEXT", StickersActivity.this.getLinkForSet(tLRPC$TL_messages_stickerSet));
                    StickersActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("StickersShare", R.string.StickersShare)), 500);
                    return;
                } catch (Exception e) {
                    FileLog.e(e);
                    return;
                }
            }
            if (i != 3) {
                if (i != 4 || (indexOf = this.stickerSets.indexOf(tLRPC$TL_messages_stickerSet)) < 0) {
                    return;
                }
                StickersActivity.this.listAdapter.toggleSelected(StickersActivity.this.stickersStartRow + indexOf);
                return;
            }
            try {
                Locale locale = Locale.US;
                StringBuilder sb = new StringBuilder();
                sb.append("https://");
                sb.append(MessagesController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).linkPrefix);
                sb.append("/");
                sb.append(tLRPC$TL_messages_stickerSet.set.emojis ? "addemoji" : "addstickers");
                sb.append("/%s");
                ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", String.format(locale, sb.toString(), tLRPC$TL_messages_stickerSet.set.short_name)));
                BulletinFactory.createCopyLinkBulletin(StickersActivity.this).show();
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:187:0x04b0, code lost:
        
            if (r6 == false) goto L161;
         */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r12, int r13) {
            /*
                Method dump skipped, instructions count: 1246
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.StickersActivity.ListAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindViewHolder$1(View view) {
            FeaturedStickerSetCell2 featuredStickerSetCell2 = (FeaturedStickerSetCell2) view.getParent();
            TLRPC$StickerSetCovered stickerSet = featuredStickerSetCell2.getStickerSet();
            if (this.loadingFeaturedStickerSets.contains(Long.valueOf(stickerSet.set.id))) {
                return;
            }
            StickersActivity.this.isListeningForFeaturedUpdate = true;
            this.loadingFeaturedStickerSets.add(Long.valueOf(stickerSet.set.id));
            featuredStickerSetCell2.setDrawProgress(true, true);
            if (featuredStickerSetCell2.isInstalled()) {
                MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), stickerSet, 0, StickersActivity.this, false, false);
            } else {
                MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), stickerSet, 2, StickersActivity.this, false, false);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, List list) {
            if (list.isEmpty()) {
                onBindViewHolder(viewHolder, i);
                return;
            }
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                if (i < StickersActivity.this.stickersStartRow || i >= StickersActivity.this.stickersEndRow) {
                    return;
                }
                StickerSetCell stickerSetCell = (StickerSetCell) viewHolder.itemView;
                if (list.contains(1)) {
                    stickerSetCell.setChecked(this.selectedItems.get(getItemId(i), Boolean.FALSE).booleanValue());
                }
                if (list.contains(2)) {
                    stickerSetCell.setReorderable(hasSelected());
                }
                if (list.contains(3)) {
                    stickerSetCell.setNeedDivider(i - StickersActivity.this.stickersStartRow != this.stickerSets.size() - 1);
                    return;
                }
                return;
            }
            if (itemViewType == 4) {
                if (list.contains(0) && i == StickersActivity.this.loopRow) {
                    ((TextCheckCell) viewHolder.itemView).setChecked(SharedConfig.loopStickers());
                    return;
                }
                return;
            }
            if (itemViewType == 7 && list.contains(4) && i >= StickersActivity.this.featuredStickersStartRow && i <= StickersActivity.this.featuredStickersEndRow) {
                ((FeaturedStickerSetCell2) viewHolder.itemView).setStickersSet(this.featuredStickerSets.get(i - StickersActivity.this.featuredStickersStartRow), true, false, false, true);
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 7 || itemViewType == 2 || itemViewType == 4 || itemViewType == 5;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$onCreateViewHolder$2(StickerSetCell stickerSetCell, View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            StickersActivity.this.itemTouchHelper.startDrag(StickersActivity.this.listView.getChildViewHolder(stickerSetCell));
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$9(View view) {
            StickerSetCell stickerSetCell = (StickerSetCell) view.getParent();
            final TLRPC$TL_messages_stickerSet stickersSet = stickerSetCell.getStickersSet();
            ItemOptions makeOptions = ItemOptions.makeOptions(StickersActivity.this, stickerSetCell);
            makeOptions.add(R.drawable.msg_archive, LocaleController.getString("StickersHide", R.string.StickersHide), new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$3(stickersSet);
                }
            });
            if (stickersSet.set.official) {
                makeOptions.add(R.drawable.msg_reorder, LocaleController.getString("StickersReorder", R.string.StickersReorder), new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$4(stickersSet);
                    }
                });
            } else {
                makeOptions.add(R.drawable.msg_link, LocaleController.getString("StickersCopy", R.string.StickersCopy), new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$5(stickersSet);
                    }
                });
                makeOptions.add(R.drawable.msg_reorder, LocaleController.getString("StickersReorder", R.string.StickersReorder), new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$6(stickersSet);
                    }
                });
                makeOptions.add(R.drawable.msg_share, LocaleController.getString("StickersShare", R.string.StickersShare), new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$7(stickersSet);
                    }
                });
                makeOptions.add(R.drawable.msg_delete, LocaleController.getString("StickersRemove", R.string.StickersRemove), true, new Runnable() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$8(stickersSet);
                    }
                });
            }
            makeOptions.setMinWidth(190);
            makeOptions.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$3(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(0, tLRPC$TL_messages_stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$4(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(4, tLRPC$TL_messages_stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$5(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(3, tLRPC$TL_messages_stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$6(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(4, tLRPC$TL_messages_stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$7(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(2, tLRPC$TL_messages_stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$8(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            processSelectionOption(1, tLRPC$TL_messages_stickerSet);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @SuppressLint({"ClickableViewAccessibility"})
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            int i2 = 1;
            if (i == 0) {
                final StickerSetCell stickerSetCell = new StickerSetCell(this.mContext, i2) { // from class: org.telegram.ui.StickersActivity.ListAdapter.3
                    @Override // org.telegram.ui.Cells.StickerSetCell
                    protected void onPremiumButtonClick() {
                        StickersActivity.this.showDialog(new PremiumFeatureBottomSheet(StickersActivity.this, 11, false));
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r2v0 */
                    /* JADX WARN: Type inference failed for: r2v1, types: [org.telegram.tgnet.TLRPC$StickerSetCovered] */
                    /* JADX WARN: Type inference failed for: r2v3, types: [org.telegram.tgnet.TLRPC$StickerSetCovered] */
                    @Override // org.telegram.ui.Cells.StickerSetCell
                    protected void onAddButtonClick() {
                        TLRPC$TL_messages_stickerSet stickersSet = getStickersSet();
                        if (stickersSet == null || stickersSet.set == null) {
                            return;
                        }
                        ArrayList<TLRPC$StickerSetCovered> featuredEmojiSets = StickersActivity.this.getMediaDataController().getFeaturedEmojiSets();
                        TLRPC$StickerSetCovered tLRPC$StickerSetCovered = 0;
                        int i3 = 0;
                        while (true) {
                            if (i3 >= featuredEmojiSets.size()) {
                                break;
                            }
                            if (stickersSet.set.id == featuredEmojiSets.get(i3).set.id) {
                                tLRPC$StickerSetCovered = featuredEmojiSets.get(i3);
                                break;
                            }
                            i3++;
                        }
                        if (tLRPC$StickerSetCovered != 0) {
                            if (ListAdapter.this.loadingFeaturedStickerSets.contains(Long.valueOf(tLRPC$StickerSetCovered.set.id))) {
                                return;
                            } else {
                                ListAdapter.this.loadingFeaturedStickerSets.add(Long.valueOf(tLRPC$StickerSetCovered.set.id));
                            }
                        }
                        MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), tLRPC$StickerSetCovered == 0 ? stickersSet : tLRPC$StickerSetCovered, 2, StickersActivity.this, false, false);
                    }

                    @Override // org.telegram.ui.Cells.StickerSetCell
                    protected void onRemoveButtonClick() {
                        MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).toggleStickerSet(StickersActivity.this.getParentActivity(), getStickersSet(), 0, StickersActivity.this, false, true);
                    }
                };
                stickerSetCell.setBackgroundColor(StickersActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                stickerSetCell.setOnReorderButtonTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view2, MotionEvent motionEvent) {
                        boolean lambda$onCreateViewHolder$2;
                        lambda$onCreateViewHolder$2 = StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$2(stickerSetCell, view2, motionEvent);
                        return lambda$onCreateViewHolder$2;
                    }
                });
                stickerSetCell.setOnOptionsClick(new View.OnClickListener() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        StickersActivity.ListAdapter.this.lambda$onCreateViewHolder$9(view2);
                    }
                });
                view = stickerSetCell;
            } else if (i == 1) {
                View textInfoPrivacyCell = new TextInfoPrivacyCell(this.mContext);
                textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                view = textInfoPrivacyCell;
            } else if (i == 2) {
                View textCell = new TextCell(this.mContext);
                textCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textCell;
            } else if (i == 3) {
                view = new ShadowSectionCell(this.mContext);
            } else if (i == 5) {
                View textSettingsCell = new TextSettingsCell(this.mContext);
                textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textSettingsCell;
            } else if (i == 6) {
                View headerCell = new HeaderCell(this.mContext);
                headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = headerCell;
            } else if (i == 7) {
                FeaturedStickerSetCell2 featuredStickerSetCell2 = new FeaturedStickerSetCell2(this.mContext, StickersActivity.this.getResourceProvider()) { // from class: org.telegram.ui.StickersActivity.ListAdapter.2
                    @Override // org.telegram.ui.Cells.FeaturedStickerSetCell2
                    protected void onPremiumButtonClick() {
                        StickersActivity.this.showDialog(new PremiumFeatureBottomSheet(StickersActivity.this, 11, false));
                    }
                };
                featuredStickerSetCell2.setBackgroundColor(StickersActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                featuredStickerSetCell2.getTextView().setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                view = featuredStickerSetCell2;
            } else {
                View textCheckCell = new TextCheckCell(this.mContext);
                textCheckCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                view = textCheckCell;
            }
            view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i >= StickersActivity.this.featuredStickersStartRow && i < StickersActivity.this.featuredStickersEndRow) {
                return 7;
            }
            if (i >= StickersActivity.this.stickersStartRow && i < StickersActivity.this.stickersEndRow) {
                return 0;
            }
            if (i == StickersActivity.this.stickersBotInfo || i == StickersActivity.this.archivedInfoRow || i == StickersActivity.this.loopInfoRow || i == StickersActivity.this.suggestAnimatedEmojiInfoRow || i == StickersActivity.this.masksInfoRow || i == StickersActivity.this.dynamicPackOrderInfo) {
                return 1;
            }
            if (i == StickersActivity.this.archivedRow || i == StickersActivity.this.masksRow || i == StickersActivity.this.featuredRow || i == StickersActivity.this.emojiPacksRow || i == StickersActivity.this.suggestRow || i == StickersActivity.this.featuredStickersShowMoreRow) {
                return 2;
            }
            if (i == StickersActivity.this.stickersShadowRow || i == StickersActivity.this.featuredStickersShadowRow) {
                return 3;
            }
            if (i == StickersActivity.this.loopRow || i == StickersActivity.this.largeEmojiRow || i == StickersActivity.this.suggestAnimatedEmojiRow || i == StickersActivity.this.dynamicPackOrder) {
                return 4;
            }
            if (i == StickersActivity.this.reactionsDoubleTapRow) {
                return 5;
            }
            return (i == StickersActivity.this.featuredStickersHeaderRow || i == StickersActivity.this.stickersHeaderRow || i == StickersActivity.this.stickersSettingsRow) ? 6 : 0;
        }

        public void swapElements(int i, int i2) {
            if (i != i2) {
                StickersActivity.this.needReorder = true;
            }
            MediaDataController mediaDataController = MediaDataController.getInstance(((BaseFragment) StickersActivity.this).currentAccount);
            swapListElements(this.stickerSets, i - StickersActivity.this.stickersStartRow, i2 - StickersActivity.this.stickersStartRow);
            Collections.sort(mediaDataController.getStickerSets(StickersActivity.this.currentType), new Comparator() { // from class: org.telegram.ui.StickersActivity$ListAdapter$$ExternalSyntheticLambda10
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$swapElements$10;
                    lambda$swapElements$10 = StickersActivity.ListAdapter.this.lambda$swapElements$10((TLRPC$TL_messages_stickerSet) obj, (TLRPC$TL_messages_stickerSet) obj2);
                    return lambda$swapElements$10;
                }
            });
            notifyItemMoved(i, i2);
            if (i == StickersActivity.this.stickersEndRow - 1 || i2 == StickersActivity.this.stickersEndRow - 1) {
                notifyItemRangeChanged(i, 3);
                notifyItemRangeChanged(i2, 3);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$swapElements$10(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2) {
            int indexOf = this.stickerSets.indexOf(tLRPC$TL_messages_stickerSet);
            int indexOf2 = this.stickerSets.indexOf(tLRPC$TL_messages_stickerSet2);
            if (indexOf < 0 || indexOf2 < 0) {
                return 0;
            }
            return indexOf - indexOf2;
        }

        private void swapListElements(List<TLRPC$TL_messages_stickerSet> list, int i, int i2) {
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = list.get(i);
            list.set(i, list.get(i2));
            list.set(i2, tLRPC$TL_messages_stickerSet);
        }

        public void toggleSelected(int i) {
            long itemId = getItemId(i);
            this.selectedItems.put(itemId, Boolean.valueOf(!r2.get(itemId, Boolean.FALSE).booleanValue()));
            notifyItemChanged(i, 1);
            checkActionMode();
        }

        public void clearSelected() {
            this.selectedItems.clear();
            notifyStickersItemsChanged(1);
            checkActionMode();
        }

        public boolean hasSelected() {
            return this.selectedItems.indexOfValue(Boolean.TRUE) != -1;
        }

        public int getSelectedCount() {
            int size = this.selectedItems.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (this.selectedItems.valueAt(i2).booleanValue()) {
                    i++;
                }
            }
            return i;
        }

        private void checkActionMode() {
            int selectedCount = StickersActivity.this.listAdapter.getSelectedCount();
            boolean isActionModeShowed = ((BaseFragment) StickersActivity.this).actionBar.isActionModeShowed();
            if (selectedCount <= 0) {
                if (isActionModeShowed) {
                    ((BaseFragment) StickersActivity.this).actionBar.hideActionMode();
                    notifyStickersItemsChanged(2);
                    return;
                }
                return;
            }
            checkActionModeIcons();
            StickersActivity.this.selectedCountTextView.setNumber(selectedCount, isActionModeShowed);
            if (isActionModeShowed) {
                return;
            }
            ((BaseFragment) StickersActivity.this).actionBar.showActionMode();
            notifyStickersItemsChanged(2);
            if (SharedConfig.stickersReorderingHintUsed || StickersActivity.this.currentType == 5) {
                return;
            }
            SharedConfig.setStickersReorderingHintUsed(true);
            Bulletin.make(((BaseFragment) StickersActivity.this).parentLayout.getLastFragment(), new ReorderingBulletinLayout(this.mContext, LocaleController.getString("StickersReorderHint", R.string.StickersReorderHint), null), 3250).show();
        }

        private void checkActionModeIcons() {
            boolean z;
            if (hasSelected()) {
                int size = this.stickerSets.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        z = true;
                        break;
                    } else {
                        if (this.selectedItems.get(this.stickerSets.get(i).set.id, Boolean.FALSE).booleanValue() && this.stickerSets.get(i).set.official && !this.stickerSets.get(i).set.emojis) {
                            z = false;
                            break;
                        }
                        i++;
                    }
                }
                int i2 = z ? 0 : 8;
                if (StickersActivity.this.deleteMenuItem.getVisibility() != i2) {
                    StickersActivity.this.deleteMenuItem.setVisibility(i2);
                }
            }
        }

        private void notifyStickersItemsChanged(Object obj) {
            notifyItemRangeChanged(StickersActivity.this.stickersStartRow, StickersActivity.this.stickersEndRow - StickersActivity.this.stickersStartRow, obj);
        }

        private CharSequence addStickersBotSpan(String str) {
            String str2 = "@stickers";
            int indexOf = str.indexOf("@stickers");
            if (indexOf != -1) {
                try {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
                    spannableStringBuilder.setSpan(new URLSpanNoUnderline(str2) { // from class: org.telegram.ui.StickersActivity.ListAdapter.4
                        @Override // org.telegram.ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                        public void onClick(View view) {
                            MessagesController.getInstance(((BaseFragment) StickersActivity.this).currentAccount).openByUserName("stickers", StickersActivity.this, 3);
                        }
                    }, indexOf, indexOf + 9, 18);
                    return spannableStringBuilder;
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            return str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getLinkForSet(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
        Locale locale = Locale.US;
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(MessagesController.getInstance(this.currentAccount).linkPrefix);
        sb.append("/");
        sb.append(tLRPC$TL_messages_stickerSet.set.emojis ? "addemoji" : "addstickers");
        sb.append("/%s");
        return String.format(locale, sb.toString(), tLRPC$TL_messages_stickerSet.set.short_name);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        int i = Theme.key_windowBackgroundWhite;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{StickerSetCell.class, TextSettingsCell.class, TextCheckCell.class}, null, null, null, i));
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray));
        ActionBar actionBar = this.actionBar;
        int i2 = ThemeDescription.FLAG_BACKGROUND;
        int i3 = Theme.key_actionBarDefault;
        arrayList.add(new ThemeDescription(actionBar, i2, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, i3));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        ActionBar actionBar2 = this.actionBar;
        int i4 = ThemeDescription.FLAG_AB_AM_ITEMSCOLOR;
        int i5 = Theme.key_actionBarActionModeDefaultIcon;
        arrayList.add(new ThemeDescription(actionBar2, i4, null, null, null, null, i5));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_BACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_TOPBACKGROUND, null, null, null, null, Theme.key_actionBarActionModeDefaultTop));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_AM_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarActionModeDefaultSelector));
        arrayList.add(new ThemeDescription(this.selectedCountTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i5));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider));
        int i6 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_switchTrack));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_switchTrackChecked));
        int i7 = Theme.key_windowBackgroundGrayShadow;
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, i7));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteLinkText));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteValueText));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, i7));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"textView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i6));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"valueTextView"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_windowBackgroundWhiteGrayText2));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_stickers_menuSelector));
        int i8 = Theme.key_stickers_menu;
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i8));
        arrayList.add(new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"reorderButton"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i8));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{StickerSetCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, i));
        arrayList.add(new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{StickerSetCell.class}, new String[]{"checkBox"}, (Paint[]) null, (Drawable[]) null, (ThemeDescription.ThemeDescriptionDelegate) null, Theme.key_checkboxCheck));
        TrendingStickersAlert trendingStickersAlert = this.trendingStickersAlert;
        if (trendingStickersAlert != null) {
            arrayList.addAll(trendingStickersAlert.getThemeDescriptions());
        }
        return arrayList;
    }
}
