package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.LongSparseArray;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSmoothScrollerCustom;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.CompoundEmoji;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiData;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DocumentAttribute;
import org.telegram.tgnet.TLRPC$EmojiStatus;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.tgnet.TLRPC$TL_chatBannedRights;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_resolvedPeer;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_emojiList;
import org.telegram.tgnet.TLRPC$TL_emojiStatus;
import org.telegram.tgnet.TLRPC$TL_emojiStatusEmpty;
import org.telegram.tgnet.TLRPC$TL_emojiStatusUntil;
import org.telegram.tgnet.TLRPC$TL_emojiURL;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_messages_foundStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_getEmojiURL;
import org.telegram.tgnet.TLRPC$TL_messages_getStickers;
import org.telegram.tgnet.TLRPC$TL_messages_reorderStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_searchStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickers;
import org.telegram.tgnet.TLRPC$TL_stickerSetFullCovered;
import org.telegram.tgnet.TLRPC$TL_stickerSetNoCovered;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$WebDocument;
import org.telegram.tgnet.TLRPC$messages_BotResults;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ContextLinkCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.FeaturedStickerSetInfoCell;
import org.telegram.ui.Cells.StickerEmojiCell;
import org.telegram.ui.Cells.StickerSetGroupInfoCell;
import org.telegram.ui.Cells.StickerSetNameCell;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.Bulletin;
import org.telegram.ui.Components.EmojiView;
import org.telegram.ui.Components.ListView.RecyclerListViewWithOverlayDraw;
import org.telegram.ui.Components.PagerSlidingTabStrip;
import org.telegram.ui.Components.Premium.PremiumButtonView;
import org.telegram.ui.Components.RecyclerAnimationScrollHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.ScrollSlidingTabStrip;
import org.telegram.ui.Components.StickerCategoriesListView;
import org.telegram.ui.Components.TrendingStickersLayout;
import org.telegram.ui.ContentPreviewViewer;
import org.telegram.ui.StickersActivity;

/* loaded from: classes4.dex */
public class EmojiView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private ArrayList<Tab> allTabs;
    private boolean allowAnimatedEmoji;
    private boolean allowEmojisForNonPremium;
    private View animateExpandFromButton;
    private int animateExpandFromPosition;
    private long animateExpandStartTime;
    private int animateExpandToPosition;
    private LongSparseArray<AnimatedEmojiDrawable> animatedEmojiDrawables;
    private PorterDuffColorFilter animatedEmojiTextColorFilter;
    private ImageView backspaceButton;
    private AnimatorSet backspaceButtonAnimation;
    private boolean backspaceOnce;
    private boolean backspacePressed;
    private float bottomTabAdditionalTranslation;
    private FrameLayout bottomTabContainer;
    private ValueAnimator bottomTabContainerAnimator;
    private View bottomTabContainerBackground;
    private float bottomTabMainTranslation;
    private FrameLayout bulletinContainer;
    private Runnable checkExpandStickerTabsRunnable;
    private ChooseStickerActionTracker chooseStickerActionTracker;
    private EmojiColorPickerWindow colorPickerView;
    private ContentPreviewViewer.ContentPreviewViewerDelegate contentPreviewViewerDelegate;
    public int currentAccount;
    private int currentBackgroundType;
    private long currentChatId;
    private int currentPage;
    private ArrayList<Tab> currentTabs;
    private EmojiViewDelegate delegate;
    private Paint dotPaint;
    private DragListener dragListener;
    private EmojiGridAdapter emojiAdapter;
    boolean emojiBanned;
    private FrameLayout emojiContainer;
    private EmojiGridView emojiGridView;
    private float emojiLastX;
    private float emojiLastY;
    private GridLayoutManager emojiLayoutManager;
    private Drawable emojiLockDrawable;
    private Paint emojiLockPaint;
    private boolean emojiPackAlertOpened;
    EmojiPagesAdapter emojiPagerAdapter;
    private RecyclerAnimationScrollHelper emojiScrollHelper;
    private EmojiSearchAdapter emojiSearchAdapter;
    private SearchField emojiSearchField;
    private int emojiSize;
    private boolean emojiSmoothScrolling;
    private AnimatorSet emojiTabShadowAnimator;
    private EmojiTabsStrip emojiTabs;
    private View emojiTabsShadow;
    private String[] emojiTitles;
    private ImageViewEmoji emojiTouchedView;
    private float emojiTouchedX;
    private float emojiTouchedY;
    private ArrayList<EmojiPack> emojipacksProcessed;
    private boolean expandStickersByDragg;
    private ArrayList<Long> expandedEmojiSets;
    private int favTabNum;
    private ArrayList<TLRPC$Document> favouriteStickers;
    private ArrayList<TLRPC$StickerSetCovered> featuredEmojiSets;
    private ArrayList<TLRPC$StickerSetCovered> featuredStickerSets;
    private boolean firstEmojiAttach;
    private boolean firstGifAttach;
    private boolean firstStickersAttach;
    private boolean firstTabUpdate;
    private ImageView floatingButton;
    private boolean forseMultiwindowLayout;
    private BaseFragment fragment;
    ArrayList<TLRPC$TL_messages_stickerSet> frozenStickerSets;
    private GifAdapter gifAdapter;
    private final Map<String, TLRPC$messages_BotResults> gifCache;
    private FrameLayout gifContainer;
    private int gifFirstEmojiTabNum;
    private RecyclerListView gifGridView;
    private Drawable[] gifIcons;
    private GifLayoutManager gifLayoutManager;
    private RecyclerListView.OnItemClickListener gifOnItemClickListener;
    private int gifRecentTabNum;
    private GifAdapter gifSearchAdapter;
    private SearchField gifSearchField;
    private GifSearchPreloader gifSearchPreloader;
    private ScrollSlidingTabStrip gifTabs;
    private int gifTrendingTabNum;
    private int groupStickerPackNum;
    private int groupStickerPackPosition;
    private TLRPC$TL_messages_stickerSet groupStickerSet;
    private boolean groupStickersHidden;
    private boolean hasChatStickers;
    private Runnable hideStickersBan;
    private boolean ignorePagerScroll;
    private boolean ignoreStickersScroll;
    private TLRPC$ChatFull info;
    public ArrayList<Long> installedEmojiSets;
    private LongSparseArray<TLRPC$StickerSetCovered> installingStickerSets;
    private boolean isLayout;
    private float lastBottomScrollDy;
    private int lastNotifyWidth;
    private ArrayList<String> lastRecentArray;
    private int lastRecentCount;
    private String[] lastSearchKeyboardLanguage;
    private float lastStickersX;
    private int[] location;
    private TextView mediaBanTooltip;
    private boolean needEmojiSearch;
    private Object outlineProvider;
    private ViewPager pager;
    private boolean premiumBulletin;
    private ArrayList<TLRPC$Document> premiumStickers;
    private int premiumTabNum;
    private TLRPC$StickerSetCovered[] primaryInstallingStickerSets;
    private ArrayList<TLRPC$Document> recentGifs;
    private ArrayList<TLRPC$Document> recentStickers;
    private int recentTabNum;
    android.graphics.Rect rect;
    private LongSparseArray<TLRPC$StickerSetCovered> removingStickerSets;
    private final Theme.ResourcesProvider resourcesProvider;
    private AnimatorSet searchAnimation;
    private ImageView searchButton;
    private int searchFieldHeight;
    private Drawable searchIconDotDrawable;
    private Drawable searchIconDrawable;
    private View shadowLine;
    private AnimatorSet showStickersBanAnimator;
    private boolean showing;
    private long shownBottomTabAfterClick;
    private Drawable[] stickerIcons;
    private ArrayList<TLRPC$TL_messages_stickerSet> stickerSets;
    private ImageView stickerSettingsButton;
    boolean stickersBanned;
    private AnimatorSet stickersButtonAnimation;
    private FrameLayout stickersContainer;
    private boolean stickersContainerAttached;
    private StickersGridAdapter stickersGridAdapter;
    private RecyclerListView stickersGridView;
    private GridLayoutManager stickersLayoutManager;
    private RecyclerListView.OnItemClickListener stickersOnItemClickListener;
    private RecyclerAnimationScrollHelper stickersScrollHelper;
    private SearchField stickersSearchField;
    private StickersSearchGridAdapter stickersSearchGridAdapter;
    private ScrollSlidingTabStrip stickersTab;
    private FrameLayout stickersTabContainer;
    private int stickersTabOffset;
    private Drawable[] tabIcons;
    private final int[] tabsMinusDy;
    private ObjectAnimator[] tabsYAnimators;
    private HashMap<Long, Utilities.Callback<TLRPC$TL_messages_stickerSet>> toInstall;
    private TrendingAdapter trendingAdapter;
    private TrendingAdapter trendingEmojiAdapter;
    private int trendingTabNum;
    private PagerSlidingTabStrip typeTabs;
    private Runnable updateStickersLoadedDelayed;

    public interface DragListener {
        void onDrag(int i);

        void onDragCancel();

        void onDragEnd(float f);

        void onDragStart();
    }

    public static class EmojiPack {
        public ArrayList<TLRPC$Document> documents = new ArrayList<>();
        public boolean expanded;
        public boolean featured;
        public boolean free;
        public boolean installed;
        public TLRPC$StickerSet set;
    }

    public interface EmojiViewDelegate {

        /* renamed from: org.telegram.ui.Components.EmojiView$EmojiViewDelegate$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static boolean $default$canSchedule(EmojiViewDelegate emojiViewDelegate) {
                return false;
            }

            public static long $default$getDialogId(EmojiViewDelegate emojiViewDelegate) {
                return 0L;
            }

            public static float $default$getProgressToSearchOpened(EmojiViewDelegate emojiViewDelegate) {
                return 0.0f;
            }

            public static int $default$getThreadId(EmojiViewDelegate emojiViewDelegate) {
                return 0;
            }

            public static void $default$invalidateEnterView(EmojiViewDelegate emojiViewDelegate) {
            }

            public static boolean $default$isExpanded(EmojiViewDelegate emojiViewDelegate) {
                return false;
            }

            public static boolean $default$isInScheduleMode(EmojiViewDelegate emojiViewDelegate) {
                return false;
            }

            public static boolean $default$isSearchOpened(EmojiViewDelegate emojiViewDelegate) {
                return false;
            }

            public static boolean $default$isUserSelf(EmojiViewDelegate emojiViewDelegate) {
                return false;
            }

            public static void $default$onClearEmojiRecent(EmojiViewDelegate emojiViewDelegate) {
            }

            public static void $default$onEmojiSettingsClick(EmojiViewDelegate emojiViewDelegate, ArrayList arrayList) {
            }

            public static void $default$onGifSelected(EmojiViewDelegate emojiViewDelegate, View view, Object obj, String str, Object obj2, boolean z, int i) {
            }

            public static void $default$onSearchOpenClose(EmojiViewDelegate emojiViewDelegate, int i) {
            }

            public static void $default$onShowStickerSet(EmojiViewDelegate emojiViewDelegate, TLRPC$StickerSet tLRPC$StickerSet, TLRPC$InputStickerSet tLRPC$InputStickerSet) {
            }

            public static void $default$onStickerSelected(EmojiViewDelegate emojiViewDelegate, View view, TLRPC$Document tLRPC$Document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i) {
            }

            public static void $default$onStickerSetAdd(EmojiViewDelegate emojiViewDelegate, TLRPC$StickerSetCovered tLRPC$StickerSetCovered) {
            }

            public static void $default$onStickerSetRemove(EmojiViewDelegate emojiViewDelegate, TLRPC$StickerSetCovered tLRPC$StickerSetCovered) {
            }

            public static void $default$onStickersGroupClick(EmojiViewDelegate emojiViewDelegate, long j) {
            }

            public static void $default$onStickersSettingsClick(EmojiViewDelegate emojiViewDelegate) {
            }

            public static void $default$onTabOpened(EmojiViewDelegate emojiViewDelegate, int i) {
            }

            public static void $default$showTrendingStickersAlert(EmojiViewDelegate emojiViewDelegate, TrendingStickersLayout trendingStickersLayout) {
            }
        }

        boolean canSchedule();

        long getDialogId();

        float getProgressToSearchOpened();

        int getThreadId();

        void invalidateEnterView();

        boolean isExpanded();

        boolean isInScheduleMode();

        boolean isSearchOpened();

        boolean isUserSelf();

        void onAnimatedEmojiUnlockClick();

        boolean onBackspace();

        void onClearEmojiRecent();

        void onCustomEmojiSelected(long j, TLRPC$Document tLRPC$Document, String str, boolean z);

        void onEmojiSelected(String str);

        void onEmojiSettingsClick(ArrayList<TLRPC$TL_messages_stickerSet> arrayList);

        /* renamed from: onGifSelected */
        void lambda$onGifSelected$1(View view, Object obj, String str, Object obj2, boolean z, int i);

        void onSearchOpenClose(int i);

        void onShowStickerSet(TLRPC$StickerSet tLRPC$StickerSet, TLRPC$InputStickerSet tLRPC$InputStickerSet);

        void onStickerSelected(View view, TLRPC$Document tLRPC$Document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i);

        void onStickerSetAdd(TLRPC$StickerSetCovered tLRPC$StickerSetCovered);

        void onStickerSetRemove(TLRPC$StickerSetCovered tLRPC$StickerSetCovered);

        void onStickersGroupClick(long j);

        void onStickersSettingsClick();

        void onTabOpened(int i);

        void showTrendingStickersAlert(TrendingStickersLayout trendingStickersLayout);
    }

    public void setAllow(boolean z, boolean z2, boolean z3) {
        this.currentTabs.clear();
        for (int i = 0; i < this.allTabs.size(); i++) {
            if (this.allTabs.get(i).type == 0) {
                this.currentTabs.add(this.allTabs.get(i));
            }
            if (this.allTabs.get(i).type == 1 && z2) {
                this.currentTabs.add(this.allTabs.get(i));
            }
            int i2 = this.allTabs.get(i).type;
        }
        PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
        if (pagerSlidingTabStrip != null) {
            AndroidUtilities.updateViewVisibilityAnimated(pagerSlidingTabStrip, this.currentTabs.size() > 1, 1.0f, z3);
        }
        ViewPager viewPager = this.pager;
        if (viewPager != null) {
            viewPager.setAdapter(null);
            this.pager.setAdapter(this.emojiPagerAdapter);
            PagerSlidingTabStrip pagerSlidingTabStrip2 = this.typeTabs;
            if (pagerSlidingTabStrip2 != null) {
                pagerSlidingTabStrip2.setViewPager(this.pager);
            }
        }
    }

    public void allowEmojisForNonPremium(boolean z) {
        this.allowEmojisForNonPremium = z;
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$2, reason: invalid class name */
    class AnonymousClass2 implements ContentPreviewViewer.ContentPreviewViewerDelegate {
        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needCopy() {
            return true;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public /* synthetic */ boolean needMenu() {
            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needMenu(this);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public /* synthetic */ boolean needOpen() {
            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needOpen(this);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public /* synthetic */ boolean needRemove() {
            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needRemove(this);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public /* synthetic */ void remove(SendMessagesHelper.ImportingSticker importingSticker) {
            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$remove(this, importingSticker);
        }

        AnonymousClass2() {
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean can() {
            return EmojiView.this.fragment != null;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendSticker(TLRPC$Document tLRPC$Document, String str, Object obj, boolean z, int i) {
            EmojiView.this.delegate.onStickerSelected(null, tLRPC$Document, str, obj, null, z, i);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void resetTouch() {
            if (EmojiView.this.emojiGridView != null) {
                EmojiView.this.emojiGridView.clearAllTouches();
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendEmoji(TLRPC$Document tLRPC$Document) {
            if (EmojiView.this.fragment instanceof ChatActivity) {
                ((ChatActivity) EmojiView.this.fragment).sendAnimatedEmoji(tLRPC$Document, true, 0);
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void setAsEmojiStatus(TLRPC$Document tLRPC$Document, Integer num) {
            TLRPC$EmojiStatus tLRPC$EmojiStatus;
            if (tLRPC$Document == null) {
                tLRPC$EmojiStatus = new TLRPC$TL_emojiStatusEmpty();
            } else if (num != null) {
                TLRPC$TL_emojiStatusUntil tLRPC$TL_emojiStatusUntil = new TLRPC$TL_emojiStatusUntil();
                tLRPC$TL_emojiStatusUntil.document_id = tLRPC$Document.id;
                tLRPC$TL_emojiStatusUntil.until = num.intValue();
                tLRPC$EmojiStatus = tLRPC$TL_emojiStatusUntil;
            } else {
                TLRPC$TL_emojiStatus tLRPC$TL_emojiStatus = new TLRPC$TL_emojiStatus();
                tLRPC$TL_emojiStatus.document_id = tLRPC$Document.id;
                tLRPC$EmojiStatus = tLRPC$TL_emojiStatus;
            }
            TLRPC$User currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
            final TLRPC$EmojiStatus tLRPC$TL_emojiStatusEmpty = currentUser == null ? new TLRPC$TL_emojiStatusEmpty() : currentUser.emoji_status;
            MessagesController.getInstance(EmojiView.this.currentAccount).updateEmojiStatus(tLRPC$EmojiStatus);
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.AnonymousClass2.this.lambda$setAsEmojiStatus$0(tLRPC$TL_emojiStatusEmpty);
                }
            };
            if (tLRPC$Document == null) {
                Bulletin.SimpleLayout simpleLayout = new Bulletin.SimpleLayout(EmojiView.this.getContext(), EmojiView.this.resourcesProvider);
                simpleLayout.textView.setText(LocaleController.getString("RemoveStatusInfo", R.string.RemoveStatusInfo));
                simpleLayout.imageView.setImageResource(R.drawable.msg_settings_premium);
                simpleLayout.imageView.setScaleX(0.8f);
                simpleLayout.imageView.setScaleY(0.8f);
                simpleLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_verifiedBackground, EmojiView.this.resourcesProvider), PorterDuff.Mode.MULTIPLY));
                Bulletin.UndoButton undoButton = new Bulletin.UndoButton(EmojiView.this.getContext(), true, EmojiView.this.resourcesProvider);
                undoButton.setUndoAction(runnable);
                simpleLayout.setButton(undoButton);
                if (EmojiView.this.fragment != null) {
                    Bulletin.make(EmojiView.this.fragment, simpleLayout, 1500).show();
                    return;
                } else {
                    Bulletin.make(EmojiView.this.bulletinContainer, simpleLayout, 1500).show();
                    return;
                }
            }
            (EmojiView.this.fragment != null ? BulletinFactory.of(EmojiView.this.fragment) : BulletinFactory.of(EmojiView.this.bulletinContainer, EmojiView.this.resourcesProvider)).createEmojiBulletin(tLRPC$Document, LocaleController.getString("SetAsEmojiStatusInfo", R.string.SetAsEmojiStatusInfo), LocaleController.getString("Undo", R.string.Undo), runnable).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setAsEmojiStatus$0(TLRPC$EmojiStatus tLRPC$EmojiStatus) {
            MessagesController.getInstance(EmojiView.this.currentAccount).updateEmojiStatus(tLRPC$EmojiStatus);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void copyEmoji(TLRPC$Document tLRPC$Document) {
            SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(MessageObject.findAnimatedEmojiEmoticon(tLRPC$Document));
            valueOf.setSpan(new AnimatedEmojiSpan(tLRPC$Document, (Paint.FontMetricsInt) null), 0, valueOf.length(), 33);
            if (AndroidUtilities.addToClipboard(valueOf)) {
                (EmojiView.this.fragment != null ? BulletinFactory.of(EmojiView.this.fragment) : BulletinFactory.of(EmojiView.this.bulletinContainer, EmojiView.this.resourcesProvider)).createCopyBulletin(LocaleController.getString("EmojiCopied", R.string.EmojiCopied)).show();
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needRemoveFromRecent(TLRPC$Document tLRPC$Document) {
            if (tLRPC$Document != null) {
                if (Emoji.recentEmoji.contains("animated_" + tLRPC$Document.id)) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void removeFromRecent(TLRPC$Document tLRPC$Document) {
            if (tLRPC$Document != null) {
                Emoji.removeRecentEmoji("animated_" + tLRPC$Document.id);
                if (EmojiView.this.emojiAdapter != null) {
                    EmojiView.this.emojiAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public Boolean canSetAsStatus(TLRPC$Document tLRPC$Document) {
            TLRPC$User currentUser;
            if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium() || (currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser()) == null) {
                return null;
            }
            Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(currentUser);
            return Boolean.valueOf(tLRPC$Document != null && (emojiStatusDocumentId == null || emojiStatusDocumentId.longValue() != tLRPC$Document.id));
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean needSend(int i) {
            if (i != 2) {
                return true;
            }
            if ((EmojiView.this.fragment instanceof ChatActivity) && ((ChatActivity) EmojiView.this.fragment).canSendMessage()) {
                if (UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
                    return true;
                }
                if (((ChatActivity) EmojiView.this.fragment).getCurrentUser() != null && UserObject.isUserSelf(((ChatActivity) EmojiView.this.fragment).getCurrentUser())) {
                    return true;
                }
            }
            return false;
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean canSchedule() {
            return EmojiView.this.delegate.canSchedule();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public boolean isInScheduleMode() {
            return EmojiView.this.delegate.isInScheduleMode();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void openSet(TLRPC$InputStickerSet tLRPC$InputStickerSet, boolean z) {
            if (tLRPC$InputStickerSet == null) {
                return;
            }
            EmojiView.this.delegate.onShowStickerSet(null, tLRPC$InputStickerSet);
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void sendGif(Object obj, Object obj2, boolean z, int i) {
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter) {
                EmojiView.this.delegate.lambda$onGifSelected$1(null, obj, null, obj2, z, i);
            } else if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter) {
                EmojiView.this.delegate.lambda$onGifSelected$1(null, obj, null, obj2, z, i);
            }
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public void gifAddedOrDeleted() {
            EmojiView.this.updateRecentGifs();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public long getDialogId() {
            return EmojiView.this.delegate.getDialogId();
        }

        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
        public String getQuery(boolean z) {
            if (z) {
                if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter) {
                    return EmojiView.this.gifSearchAdapter.lastSearchImageString;
                }
                return null;
            }
            if (EmojiView.this.emojiGridView.getAdapter() == EmojiView.this.emojiSearchAdapter) {
                return EmojiView.this.emojiSearchAdapter.lastSearchEmojiString;
            }
            return null;
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        SearchField searchField = this.stickersSearchField;
        if (searchField != null) {
            searchField.searchEditText.setEnabled(z);
        }
        SearchField searchField2 = this.gifSearchField;
        if (searchField2 != null) {
            searchField2.searchEditText.setEnabled(z);
        }
        SearchField searchField3 = this.emojiSearchField;
        if (searchField3 != null) {
            searchField3.searchEditText.setEnabled(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SearchField extends FrameLayout {
        private View backgroundView;
        private FrameLayout box;
        private StickerCategoriesListView categoriesListView;
        private ImageView clear;
        private Runnable delayedToggle;
        private FrameLayout inputBox;
        private View inputBoxGradient;
        private boolean inputBoxShown;
        private boolean isprogress;
        private StickerCategoriesListView.EmojiCategory recent;
        private EditTextBoldCursor searchEditText;
        private ImageView searchImageView;
        private SearchStateDrawable searchStateDrawable;
        private AnimatorSet shadowAnimator;
        private View shadowView;
        private StickerCategoriesListView.EmojiCategory trending;
        private int type;

        @SuppressLint({"ClickableViewAccessibility"})
        public SearchField(Context context, int i) {
            super(context);
            this.inputBoxShown = false;
            this.type = i;
            View view = new View(context);
            this.shadowView = view;
            view.setAlpha(0.0f);
            this.shadowView.setTag(1);
            this.shadowView.setBackgroundColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            addView(this.shadowView, new FrameLayout.LayoutParams(-1, AndroidUtilities.getShadowHeight(), 83));
            View view2 = new View(context);
            this.backgroundView = view2;
            int i2 = Theme.key_chat_emojiPanelBackground;
            view2.setBackgroundColor(EmojiView.this.getThemedColor(i2));
            addView(this.backgroundView, new FrameLayout.LayoutParams(-1, EmojiView.this.searchFieldHeight));
            FrameLayout frameLayout = new FrameLayout(context);
            this.box = frameLayout;
            int dp = AndroidUtilities.dp(18.0f);
            int i3 = Theme.key_chat_emojiSearchBackground;
            frameLayout.setBackground(Theme.createRoundRectDrawable(dp, EmojiView.this.getThemedColor(i3)));
            if (Build.VERSION.SDK_INT >= 21) {
                this.box.setClipToOutline(true);
                this.box.setOutlineProvider(new ViewOutlineProvider(this, EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.SearchField.1
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view3, Outline outline) {
                        outline.setRoundRect(0, 0, view3.getWidth(), view3.getHeight(), AndroidUtilities.dp(18.0f));
                    }
                });
            }
            if (i == 2) {
                addView(this.box, LayoutHelper.createFrame(-1, 36.0f, 119, 10.0f, 8.0f, 10.0f, 8.0f));
            } else {
                addView(this.box, LayoutHelper.createFrame(-1, 36.0f, 119, 10.0f, 6.0f, 10.0f, 8.0f));
            }
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.inputBox = frameLayout2;
            this.box.addView(frameLayout2, LayoutHelper.createFrame(-1, 40.0f, 51, 38.0f, 0.0f, 0.0f, 0.0f));
            this.searchImageView = new ImageView(context);
            SearchStateDrawable searchStateDrawable = new SearchStateDrawable();
            this.searchStateDrawable = searchStateDrawable;
            searchStateDrawable.setIconState(0, false);
            SearchStateDrawable searchStateDrawable2 = this.searchStateDrawable;
            int i4 = Theme.key_chat_emojiSearchIcon;
            searchStateDrawable2.setColor(EmojiView.this.getThemedColor(i4));
            this.searchImageView.setScaleType(ImageView.ScaleType.CENTER);
            this.searchImageView.setImageDrawable(this.searchStateDrawable);
            this.searchImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    EmojiView.SearchField.this.lambda$new$0(view3);
                }
            });
            this.box.addView(this.searchImageView, LayoutHelper.createFrame(36, 36, 51));
            EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context, EmojiView.this, i) { // from class: org.telegram.ui.Components.EmojiView.SearchField.2
                final /* synthetic */ int val$type;

                {
                    this.val$type = i;
                }

                @Override // org.telegram.ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
                public boolean onTouchEvent(MotionEvent motionEvent) {
                    if (!SearchField.this.searchEditText.isEnabled()) {
                        return super.onTouchEvent(motionEvent);
                    }
                    if (motionEvent.getAction() == 0) {
                        if (!EmojiView.this.delegate.isSearchOpened()) {
                            SearchField searchField = SearchField.this;
                            EmojiView.this.openSearch(searchField);
                        }
                        EmojiView.this.delegate.onSearchOpenClose(this.val$type == 1 ? 2 : 1);
                        SearchField.this.searchEditText.requestFocus();
                        AndroidUtilities.showKeyboard(SearchField.this.searchEditText);
                    }
                    return super.onTouchEvent(motionEvent);
                }
            };
            this.searchEditText = editTextBoldCursor;
            editTextBoldCursor.setTextSize(1, 16.0f);
            this.searchEditText.setHintTextColor(EmojiView.this.getThemedColor(i4));
            this.searchEditText.setTextColor(EmojiView.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            this.searchEditText.setBackgroundDrawable(null);
            this.searchEditText.setPadding(0, 0, 0, 0);
            this.searchEditText.setMaxLines(1);
            this.searchEditText.setLines(1);
            this.searchEditText.setSingleLine(true);
            this.searchEditText.setImeOptions(268435459);
            this.searchEditText.setHint(LocaleController.getString("Search", R.string.Search));
            this.searchEditText.setCursorColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_addedIcon));
            this.searchEditText.setCursorSize(AndroidUtilities.dp(20.0f));
            this.searchEditText.setCursorWidth(1.5f);
            this.searchEditText.setTranslationY(AndroidUtilities.dp(-2.0f));
            this.inputBox.addView(this.searchEditText, LayoutHelper.createFrame(-1, 40.0f, 51, 0.0f, 0.0f, 28.0f, 0.0f));
            this.searchEditText.addTextChangedListener(new TextWatcher(EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.SearchField.3
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i5, int i6, int i7) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    SearchField.this.updateButton();
                    String obj = SearchField.this.searchEditText.getText().toString();
                    SearchField.this.search(obj, true);
                    if (SearchField.this.categoriesListView != null) {
                        SearchField.this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                        SearchField.this.categoriesListView.updateCategoriesShown(TextUtils.isEmpty(obj), true);
                    }
                    SearchField.this.toggleClear(!TextUtils.isEmpty(obj));
                    if (SearchField.this.searchEditText != null) {
                        SearchField.this.searchEditText.clearAnimation();
                        SearchField.this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                    }
                    SearchField.this.showInputBoxGradient(false);
                }
            });
            this.inputBoxGradient = new View(context);
            Drawable mutate = context.getResources().getDrawable(R.drawable.gradient_right).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(Theme.blendOver(EmojiView.this.getThemedColor(i2), EmojiView.this.getThemedColor(i3)), PorterDuff.Mode.MULTIPLY));
            this.inputBoxGradient.setBackground(mutate);
            this.inputBoxGradient.setAlpha(0.0f);
            this.inputBox.addView(this.inputBoxGradient, LayoutHelper.createFrame(18, -1, 3));
            ImageView imageView = new ImageView(context);
            this.clear = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            this.clear.setImageDrawable(new CloseProgressDrawable2(1.25f, EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.SearchField.4
                {
                    setSide(AndroidUtilities.dp(7.0f));
                }

                @Override // org.telegram.ui.Components.CloseProgressDrawable2
                protected int getCurrentColor() {
                    return Theme.getColor(Theme.key_chat_emojiSearchIcon, EmojiView.this.resourcesProvider);
                }
            });
            this.clear.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector, EmojiView.this.resourcesProvider), 1, AndroidUtilities.dp(15.0f)));
            this.clear.setAlpha(0.0f);
            this.clear.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    EmojiView.SearchField.this.lambda$new$1(view3);
                }
            });
            this.box.addView(this.clear, LayoutHelper.createFrame(36, 36, 53));
            if (i != 1 || (EmojiView.this.allowAnimatedEmoji && UserConfig.getInstance(UserConfig.selectedAccount).isPremium())) {
                StickerCategoriesListView stickerCategoriesListView = new StickerCategoriesListView(context, null, 0, EmojiView.this.resourcesProvider, EmojiView.this, i) { // from class: org.telegram.ui.Components.EmojiView.SearchField.5
                    final /* synthetic */ int val$type;

                    {
                        this.val$type = i;
                    }

                    @Override // org.telegram.ui.Components.StickerCategoriesListView
                    public void selectCategory(int i5) {
                        super.selectCategory(i5);
                        SearchField searchField = SearchField.this;
                        EmojiView.this.showBottomTab(searchField.categoriesListView.getSelectedCategory() == null, true);
                        if (this.val$type == 1 && EmojiView.this.emojiTabs != null) {
                            EmojiView.this.emojiTabs.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                        } else if (this.val$type == 0 && EmojiView.this.stickersTab != null) {
                            EmojiView.this.stickersTab.showSelected(SearchField.this.categoriesListView.getSelectedCategory() == null);
                        }
                        SearchField.this.updateButton();
                    }

                    @Override // org.telegram.ui.Components.StickerCategoriesListView
                    protected boolean isTabIconsAnimationEnabled(boolean z) {
                        return LiteMode.isEnabled(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS);
                    }
                };
                this.categoriesListView = stickerCategoriesListView;
                stickerCategoriesListView.setDontOccupyWidth(((int) this.searchEditText.getPaint().measureText(((Object) this.searchEditText.getHint()) + "")) + AndroidUtilities.dp(16.0f));
                this.categoriesListView.setBackgroundColor(Theme.blendOver(EmojiView.this.getThemedColor(i2), EmojiView.this.getThemedColor(i3)));
                this.categoriesListView.setOnScrollIntoOccupiedWidth(new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda3
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.SearchField.this.lambda$new$2((Integer) obj);
                    }
                });
                this.categoriesListView.setOnTouchListener(new View.OnTouchListener(EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.SearchField.6
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view3, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == 0) {
                            EmojiView.this.ignorePagerScroll = true;
                        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                            EmojiView.this.ignorePagerScroll = false;
                        }
                        return false;
                    }
                });
                this.categoriesListView.setOnCategoryClick(new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda4
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.SearchField.this.lambda$new$3((StickerCategoriesListView.EmojiCategory) obj);
                    }
                });
                this.box.addView(this.categoriesListView, LayoutHelper.createFrame(-1, 36.0f, 51, 36.0f, 0.0f, 0.0f, 0.0f));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            if (this.searchStateDrawable.getIconState() == 1) {
                this.searchEditText.setText("");
                search(null, false);
                StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
                if (stickerCategoriesListView != null) {
                    stickerCategoriesListView.scrollToStart();
                    this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                    this.categoriesListView.updateCategoriesShown(true, true);
                }
                toggleClear(false);
                EditTextBoldCursor editTextBoldCursor = this.searchEditText;
                if (editTextBoldCursor != null) {
                    editTextBoldCursor.clearAnimation();
                    this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                }
                showInputBoxGradient(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            this.searchEditText.setText("");
            search(null, false);
            StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
            if (stickerCategoriesListView != null) {
                stickerCategoriesListView.scrollToStart();
                this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
                this.categoriesListView.updateCategoriesShown(true, true);
            }
            toggleClear(false);
            EditTextBoldCursor editTextBoldCursor = this.searchEditText;
            if (editTextBoldCursor != null) {
                editTextBoldCursor.clearAnimation();
                this.searchEditText.animate().translationX(0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
            }
            showInputBoxGradient(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(Integer num) {
            this.searchEditText.setTranslationX(-Math.max(0, num.intValue()));
            showInputBoxGradient(num.intValue() > 0);
            updateButton();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(StickerCategoriesListView.EmojiCategory emojiCategory) {
            if (emojiCategory == this.recent) {
                showInputBoxGradient(false);
                this.categoriesListView.selectCategory(this.recent);
                EmojiView.this.gifSearchField.searchEditText.setText("");
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
                return;
            }
            if (emojiCategory == this.trending) {
                showInputBoxGradient(false);
                EmojiView.this.gifSearchField.searchEditText.setText("");
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(EmojiView.this.gifAdapter.trendingSectionItem, -AndroidUtilities.dp(4.0f));
                this.categoriesListView.selectCategory(this.trending);
                ArrayList<String> arrayList = MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchEmojies;
                if (arrayList.isEmpty()) {
                    return;
                }
                EmojiView.this.gifSearchPreloader.preload(arrayList.get(0));
                return;
            }
            if (this.categoriesListView.getSelectedCategory() == emojiCategory) {
                search(null, false);
                this.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
            } else {
                search(emojiCategory.emojis, false);
                this.categoriesListView.selectCategory(emojiCategory);
            }
        }

        public boolean isCategorySelected() {
            StickerCategoriesListView stickerCategoriesListView = this.categoriesListView;
            return (stickerCategoriesListView == null || stickerCategoriesListView.getSelectedCategory() == null) ? false : true;
        }

        public void search(String str, boolean z) {
            int i = this.type;
            if (i == 0) {
                EmojiView.this.stickersSearchGridAdapter.search(str, z);
            } else if (i == 1) {
                EmojiView.this.emojiSearchAdapter.search(str, z);
            } else if (i == 2) {
                EmojiView.this.gifSearchAdapter.search(str, z);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showInputBoxGradient(boolean z) {
            View view;
            if (z == this.inputBoxShown || (view = this.inputBoxGradient) == null) {
                return;
            }
            this.inputBoxShown = z;
            view.clearAnimation();
            this.inputBoxGradient.animate().alpha(z ? 1.0f : 0.0f).setDuration(120L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        }

        public boolean isInProgress() {
            return this.isprogress;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toggleClear(boolean z) {
            if (z) {
                if (this.delayedToggle == null) {
                    Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$SearchField$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            EmojiView.SearchField.this.lambda$toggleClear$4();
                        }
                    };
                    this.delayedToggle = runnable;
                    AndroidUtilities.runOnUIThread(runnable, 340L);
                    return;
                }
                return;
            }
            Runnable runnable2 = this.delayedToggle;
            if (runnable2 != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable2);
                this.delayedToggle = null;
            }
            AndroidUtilities.updateViewShow(this.clear, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$toggleClear$4() {
            AndroidUtilities.updateViewShow(this.clear, true);
        }

        public void showProgress(boolean z) {
            this.isprogress = z;
            if (z) {
                this.searchStateDrawable.setIconState(2);
            } else {
                updateButton(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateButton() {
            updateButton(false);
        }

        private void updateButton(boolean z) {
            StickerCategoriesListView stickerCategoriesListView;
            StickerCategoriesListView stickerCategoriesListView2;
            if (!isInProgress() || ((this.searchEditText.length() == 0 && ((stickerCategoriesListView2 = this.categoriesListView) == null || stickerCategoriesListView2.getSelectedCategory() == null)) || z)) {
                this.searchStateDrawable.setIconState((this.searchEditText.length() > 0 || ((stickerCategoriesListView = this.categoriesListView) != null && stickerCategoriesListView.isCategoriesShown() && (this.categoriesListView.isScrolledIntoOccupiedWidth() || this.categoriesListView.getSelectedCategory() != null))) ? 1 : 0);
                this.isprogress = false;
            }
        }

        public void hideKeyboard() {
            AndroidUtilities.hideKeyboard(this.searchEditText);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showShadow(boolean z, boolean z2) {
            if (z && this.shadowView.getTag() == null) {
                return;
            }
            if (z || this.shadowView.getTag() == null) {
                AnimatorSet animatorSet = this.shadowAnimator;
                if (animatorSet != null) {
                    animatorSet.cancel();
                    this.shadowAnimator = null;
                }
                this.shadowView.setTag(z ? null : 1);
                if (z2) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.shadowAnimator = animatorSet2;
                    Animator[] animatorArr = new Animator[1];
                    View view = this.shadowView;
                    Property property = View.ALPHA;
                    float[] fArr = new float[1];
                    fArr[0] = z ? 1.0f : 0.0f;
                    animatorArr[0] = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr);
                    animatorSet2.playTogether(animatorArr);
                    this.shadowAnimator.setDuration(200L);
                    this.shadowAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                    this.shadowAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.SearchField.7
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            SearchField.this.shadowAnimator = null;
                        }
                    });
                    this.shadowAnimator.start();
                    return;
                }
                this.shadowView.setAlpha(z ? 1.0f : 0.0f);
            }
        }
    }

    private class TypedScrollListener extends RecyclerView.OnScrollListener {
        private boolean smoothScrolling;
        private final int type;

        public TypedScrollListener(int i) {
            this.type = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (recyclerView.getLayoutManager().isSmoothScrolling()) {
                this.smoothScrolling = true;
                return;
            }
            if (i == 0) {
                if (!this.smoothScrolling) {
                    EmojiView.this.animateTabsY(this.type);
                }
                if (EmojiView.this.ignoreStickersScroll) {
                    EmojiView.this.ignoreStickersScroll = false;
                }
                this.smoothScrolling = false;
                return;
            }
            if (i == 1) {
                if (EmojiView.this.ignoreStickersScroll) {
                    EmojiView.this.ignoreStickersScroll = false;
                }
                SearchField searchFieldForType = EmojiView.this.getSearchFieldForType(this.type);
                if (searchFieldForType != null) {
                    searchFieldForType.hideKeyboard();
                }
                this.smoothScrolling = false;
            }
            if (!this.smoothScrolling) {
                EmojiView.this.stopAnimatingTabsY(this.type);
            }
            if (this.type == 0) {
                if (EmojiView.this.chooseStickerActionTracker == null) {
                    EmojiView.this.createStickersChooseActionTracker();
                }
                EmojiView.this.chooseStickerActionTracker.doSomeAction();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            EmojiView.this.checkScroll(this.type);
            EmojiView.this.checkTabsY(this.type, i2);
            checkSearchFieldScroll();
            if (this.smoothScrolling) {
                return;
            }
            EmojiView.this.checkBottomTabScroll(i2);
        }

        private void checkSearchFieldScroll() {
            int i = this.type;
            if (i == 0) {
                EmojiView.this.checkStickersSearchFieldScroll(false);
            } else if (i == 1) {
                EmojiView.this.checkEmojiSearchFieldScroll(false);
            } else {
                if (i != 2) {
                    return;
                }
                EmojiView.this.checkGifSearchFieldScroll(false);
            }
        }
    }

    private class DraggableScrollSlidingTabStrip extends ScrollSlidingTabStrip {
        private float downX;
        private float downY;
        private boolean draggingHorizontally;
        private boolean draggingVertically;
        private boolean first;
        private float lastTranslateX;
        private float lastX;
        private boolean startedScroll;
        private final int touchSlop;
        private VelocityTracker vTracker;

        public DraggableScrollSlidingTabStrip(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
            this.first = true;
            this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }

        @Override // org.telegram.ui.Components.ScrollSlidingTabStrip, android.widget.HorizontalScrollView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (isDragging()) {
                return super.onInterceptTouchEvent(motionEvent);
            }
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getAction() == 0) {
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                this.downX = motionEvent.getRawX();
                this.downY = motionEvent.getRawY();
            } else if (!this.draggingVertically && !this.draggingHorizontally && EmojiView.this.dragListener != null && Math.abs(motionEvent.getRawY() - this.downY) >= this.touchSlop) {
                this.draggingVertically = true;
                this.downY = motionEvent.getRawY();
                EmojiView.this.dragListener.onDragStart();
                if (this.startedScroll) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // org.telegram.ui.Components.ScrollSlidingTabStrip, android.widget.HorizontalScrollView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (isDragging()) {
                return super.onTouchEvent(motionEvent);
            }
            if (this.first) {
                this.first = false;
                this.lastX = motionEvent.getX();
            }
            if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                EmojiView.this.lastStickersX = motionEvent.getRawX();
            }
            if (motionEvent.getAction() == 0) {
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                this.downX = motionEvent.getRawX();
                this.downY = motionEvent.getRawY();
            } else if (!this.draggingVertically && !this.draggingHorizontally && EmojiView.this.dragListener != null) {
                if (Math.abs(motionEvent.getRawX() - this.downX) >= this.touchSlop && canScrollHorizontally((int) (this.downX - motionEvent.getRawX()))) {
                    this.draggingHorizontally = true;
                    AndroidUtilities.cancelRunOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable);
                    EmojiView.this.expandStickersByDragg = true;
                    EmojiView.this.updateStickerTabsPosition();
                } else if (Math.abs(motionEvent.getRawY() - this.downY) >= this.touchSlop) {
                    this.draggingVertically = true;
                    this.downY = motionEvent.getRawY();
                    EmojiView.this.dragListener.onDragStart();
                    if (this.startedScroll) {
                        EmojiView.this.pager.endFakeDrag();
                        this.startedScroll = false;
                    }
                }
            }
            if (EmojiView.this.expandStickersByDragg && (motionEvent.getAction() == 1 || motionEvent.getAction() == 3)) {
                AndroidUtilities.runOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable, 1500L);
            }
            if (this.draggingVertically) {
                if (this.vTracker == null) {
                    this.vTracker = VelocityTracker.obtain();
                }
                this.vTracker.addMovement(motionEvent);
                if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                    EmojiView.this.dragListener.onDrag(Math.round(motionEvent.getRawY() - this.downY));
                } else {
                    this.vTracker.computeCurrentVelocity(1000);
                    float yVelocity = this.vTracker.getYVelocity();
                    this.vTracker.recycle();
                    this.vTracker = null;
                    if (motionEvent.getAction() == 1) {
                        EmojiView.this.dragListener.onDragEnd(yVelocity);
                    } else {
                        EmojiView.this.dragListener.onDragCancel();
                    }
                    this.first = true;
                    this.draggingHorizontally = false;
                    this.draggingVertically = false;
                }
                cancelLongPress();
                return true;
            }
            float translationX = getTranslationX();
            if (getScrollX() == 0 && translationX == 0.0f) {
                if (!this.startedScroll && this.lastX - motionEvent.getX() < 0.0f) {
                    if (EmojiView.this.pager.beginFakeDrag()) {
                        this.startedScroll = true;
                        this.lastTranslateX = getTranslationX();
                    }
                } else if (this.startedScroll && this.lastX - motionEvent.getX() > 0.0f && EmojiView.this.pager.isFakeDragging()) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
            }
            if (this.startedScroll) {
                motionEvent.getX();
                try {
                    this.lastTranslateX = translationX;
                } catch (Exception e) {
                    try {
                        EmojiView.this.pager.endFakeDrag();
                    } catch (Exception unused) {
                    }
                    this.startedScroll = false;
                    FileLog.e(e);
                }
            }
            this.lastX = motionEvent.getX();
            if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                this.first = true;
                this.draggingHorizontally = false;
                this.draggingVertically = false;
                if (this.startedScroll) {
                    EmojiView.this.pager.endFakeDrag();
                    this.startedScroll = false;
                }
            }
            return this.startedScroll || super.onTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEmoji(ImageViewEmoji imageViewEmoji, String str) {
        String str2;
        EmojiViewDelegate emojiViewDelegate;
        if (imageViewEmoji == null) {
            return;
        }
        if (imageViewEmoji.getSpan() != null) {
            if (this.delegate != null) {
                long j = imageViewEmoji.getSpan().documentId;
                TLRPC$Document tLRPC$Document = imageViewEmoji.getSpan().document;
                if (tLRPC$Document == null) {
                    for (int i = 0; i < this.emojipacksProcessed.size(); i++) {
                        EmojiPack emojiPack = this.emojipacksProcessed.get(i);
                        int i2 = 0;
                        while (true) {
                            ArrayList<TLRPC$Document> arrayList = emojiPack.documents;
                            if (arrayList != null && i2 < arrayList.size()) {
                                if (emojiPack.documents.get(i2).id == j) {
                                    tLRPC$Document = emojiPack.documents.get(i2);
                                    break;
                                }
                                i2++;
                            }
                        }
                    }
                }
                if (tLRPC$Document == null) {
                    tLRPC$Document = AnimatedEmojiDrawable.findDocument(this.currentAccount, j);
                }
                TLRPC$Document tLRPC$Document2 = tLRPC$Document;
                String findAnimatedEmojiEmoticon = tLRPC$Document2 != null ? MessageObject.findAnimatedEmojiEmoticon(tLRPC$Document2) : null;
                if (!MessageObject.isFreeEmoji(tLRPC$Document2) && !UserConfig.getInstance(this.currentAccount).isPremium() && (((emojiViewDelegate = this.delegate) == null || !emojiViewDelegate.isUserSelf()) && !this.allowEmojisForNonPremium)) {
                    showBottomTab(false, true);
                    BaseFragment baseFragment = this.fragment;
                    BulletinFactory of = baseFragment != null ? BulletinFactory.of(baseFragment) : BulletinFactory.of(this.bulletinContainer, this.resourcesProvider);
                    if (this.premiumBulletin || this.fragment == null) {
                        of.createEmojiBulletin(tLRPC$Document2, AndroidUtilities.replaceTags(LocaleController.getString("UnlockPremiumEmojiHint", R.string.UnlockPremiumEmojiHint)), LocaleController.getString("PremiumMore", R.string.PremiumMore), new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                EmojiView.this.openPremiumAnimatedEmojiFeature();
                            }
                        }).show();
                    } else {
                        of.createSimpleBulletin(R.raw.saved_messages, AndroidUtilities.replaceTags(LocaleController.getString("UnlockPremiumEmojiHint2", R.string.UnlockPremiumEmojiHint2)), LocaleController.getString("Open", R.string.Open), new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                EmojiView.this.lambda$sendEmoji$0();
                            }
                        }).show();
                    }
                    this.premiumBulletin = !this.premiumBulletin;
                    return;
                }
                this.shownBottomTabAfterClick = SystemClock.elapsedRealtime();
                showBottomTab(true, true);
                addEmojiToRecent("animated_" + j);
                this.delegate.onCustomEmojiSelected(j, tLRPC$Document2, findAnimatedEmojiEmoticon, imageViewEmoji.isRecent);
                return;
            }
            return;
        }
        this.shownBottomTabAfterClick = SystemClock.elapsedRealtime();
        showBottomTab(true, true);
        String str3 = str != null ? str : (String) imageViewEmoji.getTag();
        new SpannableStringBuilder().append((CharSequence) str3);
        if (str != null) {
            EmojiViewDelegate emojiViewDelegate2 = this.delegate;
            if (emojiViewDelegate2 != null) {
                emojiViewDelegate2.onEmojiSelected(Emoji.fixEmoji(str));
                return;
            }
            return;
        }
        if (!imageViewEmoji.isRecent && (str2 = Emoji.emojiColor.get(str3)) != null) {
            str3 = addColorToCode(str3, str2);
        }
        addEmojiToRecent(str3);
        EmojiViewDelegate emojiViewDelegate3 = this.delegate;
        if (emojiViewDelegate3 != null) {
            emojiViewDelegate3.onEmojiSelected(Emoji.fixEmoji(str3));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendEmoji$0() {
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", UserConfig.getInstance(this.currentAccount).getClientUserId());
        this.fragment.presentFragment(new AnonymousClass3(this, bundle));
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$3, reason: invalid class name */
    class AnonymousClass3 extends ChatActivity {
        AnonymousClass3(EmojiView emojiView, Bundle bundle) {
            super(bundle);
        }

        @Override // org.telegram.ui.ChatActivity, org.telegram.ui.ActionBar.BaseFragment
        public void onTransitionAnimationEnd(boolean z, boolean z2) {
            ChatActivityEnterView chatActivityEnterView;
            super.onTransitionAnimationEnd(z, z2);
            if (!z || (chatActivityEnterView = this.chatActivityEnterView) == null) {
                return;
            }
            chatActivityEnterView.showEmojiView();
            this.chatActivityEnterView.postDelayed(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.AnonymousClass3.this.lambda$onTransitionAnimationEnd$0();
                }
            }, 100L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransitionAnimationEnd$0() {
            if (this.chatActivityEnterView.getEmojiView() != null) {
                this.chatActivityEnterView.getEmojiView().scrollEmojisToAnimated();
            }
        }
    }

    public static class ImageViewEmoji extends ImageView {
        ValueAnimator backAnimator;
        private ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolder;
        public AnimatedEmojiDrawable drawable;
        public boolean ignoring;
        private boolean isRecent;
        private EmojiPack pack;
        public int position;
        float pressedProgress;
        private AnimatedEmojiSpan span;

        public ImageViewEmoji(Context context) {
            super(context);
            this.backgroundThreadDrawHolder = new ImageReceiver.BackgroundThreadDrawHolder[2];
            setScaleType(ImageView.ScaleType.CENTER);
            setBackground(Theme.createRadSelectorDrawable(Theme.getColor(Theme.key_listSelector), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f)));
        }

        public void setImageDrawable(Drawable drawable, boolean z) {
            super.setImageDrawable(drawable);
            this.isRecent = z;
        }

        public void setSpan(AnimatedEmojiSpan animatedEmojiSpan) {
            this.span = animatedEmojiSpan;
        }

        public AnimatedEmojiSpan getSpan() {
            return this.span;
        }

        @Override // android.widget.ImageView, android.view.View
        public void onMeasure(int i, int i2) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i));
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("android.view.View");
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            ValueAnimator valueAnimator;
            if (isPressed() != z) {
                super.setPressed(z);
                invalidate();
                if (z && (valueAnimator = this.backAnimator) != null) {
                    valueAnimator.removeAllListeners();
                    this.backAnimator.cancel();
                }
                if (z) {
                    return;
                }
                float f = this.pressedProgress;
                if (f != 0.0f) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f, 0.0f);
                    this.backAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$ImageViewEmoji$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            EmojiView.ImageViewEmoji.this.lambda$setPressed$0(valueAnimator2);
                        }
                    });
                    this.backAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.ImageViewEmoji.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            ImageViewEmoji.this.backAnimator = null;
                        }
                    });
                    this.backAnimator.setInterpolator(new OvershootInterpolator(5.0f));
                    this.backAnimator.setDuration(350L);
                    this.backAnimator.start();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setPressed$0(ValueAnimator valueAnimator) {
            this.pressedProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onDraw(Canvas canvas) {
            if (isPressed()) {
                float f = this.pressedProgress;
                if (f != 1.0f) {
                    float min = f + (Math.min(40.0f, 1000.0f / AndroidUtilities.screenRefreshRate) / 100.0f);
                    this.pressedProgress = min;
                    this.pressedProgress = Utilities.clamp(min, 1.0f, 0.0f);
                    invalidate();
                }
            }
            float f2 = ((1.0f - this.pressedProgress) * 0.2f) + 0.8f;
            canvas.save();
            canvas.scale(f2, f2, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
            super.onDraw(canvas);
            canvas.restore();
        }
    }

    public EmojiView(BaseFragment baseFragment, boolean z, boolean z2, boolean z3, Context context, boolean z4, TLRPC$ChatFull tLRPC$ChatFull, ViewGroup viewGroup, final Theme.ResourcesProvider resourcesProvider) {
        super(context);
        AnonymousClass1 anonymousClass1;
        int i;
        int i2;
        this.allTabs = new ArrayList<>();
        this.currentTabs = new ArrayList<>();
        this.firstEmojiAttach = true;
        this.gifSearchPreloader = new GifSearchPreloader();
        this.gifCache = new HashMap();
        this.firstGifAttach = true;
        this.gifRecentTabNum = -2;
        this.gifTrendingTabNum = -2;
        this.gifFirstEmojiTabNum = -2;
        this.firstStickersAttach = true;
        this.tabsMinusDy = new int[3];
        this.tabsYAnimators = new ObjectAnimator[3];
        this.currentAccount = UserConfig.selectedAccount;
        this.stickerSets = new ArrayList<>();
        this.recentGifs = new ArrayList<>();
        this.recentStickers = new ArrayList<>();
        this.favouriteStickers = new ArrayList<>();
        this.premiumStickers = new ArrayList<>();
        this.featuredStickerSets = new ArrayList<>();
        this.featuredEmojiSets = new ArrayList<>();
        new ArrayList();
        this.expandedEmojiSets = new ArrayList<>();
        this.installedEmojiSets = new ArrayList<>();
        this.emojipacksProcessed = new ArrayList<>();
        this.toInstall = new HashMap<>();
        this.primaryInstallingStickerSets = new TLRPC$StickerSetCovered[10];
        this.installingStickerSets = new LongSparseArray<>();
        this.removingStickerSets = new LongSparseArray<>();
        this.location = new int[2];
        this.recentTabNum = -2;
        this.favTabNum = -2;
        this.trendingTabNum = -2;
        this.premiumTabNum = -2;
        this.currentBackgroundType = -1;
        this.checkExpandStickerTabsRunnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView.1
            @Override // java.lang.Runnable
            public void run() {
                if (EmojiView.this.stickersTab.isDragging()) {
                    return;
                }
                EmojiView.this.expandStickersByDragg = false;
                EmojiView.this.updateStickerTabsPosition();
            }
        };
        this.contentPreviewViewerDelegate = new AnonymousClass2();
        this.premiumBulletin = true;
        this.animateExpandFromPosition = -1;
        this.animateExpandToPosition = -1;
        this.animateExpandStartTime = -1L;
        this.emojiPackAlertOpened = false;
        this.rect = new android.graphics.Rect();
        this.updateStickersLoadedDelayed = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                EmojiView.this.lambda$new$15();
            }
        };
        this.fragment = baseFragment;
        this.allowAnimatedEmoji = z;
        this.resourcesProvider = resourcesProvider;
        int themedColor = getThemedColor(Theme.key_chat_emojiBottomPanelIcon);
        int argb = Color.argb(30, Color.red(themedColor), Color.green(themedColor), Color.blue(themedColor));
        this.searchFieldHeight = AndroidUtilities.dp(50.0f);
        this.needEmojiSearch = z4;
        int i3 = R.drawable.smiles_tab_smiles;
        int i4 = Theme.key_chat_emojiPanelBackspace;
        int themedColor2 = getThemedColor(i4);
        int i5 = Theme.key_chat_emojiPanelIconSelected;
        this.tabIcons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, i3, themedColor2, getThemedColor(i5)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.smiles_tab_gif, getThemedColor(i4), getThemedColor(i5)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.smiles_tab_stickers, getThemedColor(i4), getThemedColor(i5))};
        int i6 = R.drawable.msg_emoji_recent;
        int i7 = Theme.key_chat_emojiPanelIcon;
        Drawable createEmojiIconSelectorDrawable = Theme.createEmojiIconSelectorDrawable(context, R.drawable.emoji_tabs_new1, getThemedColor(i7), getThemedColor(i5));
        this.searchIconDrawable = createEmojiIconSelectorDrawable;
        int i8 = R.drawable.emoji_tabs_new2;
        int i9 = Theme.key_chat_emojiPanelStickerPackSelectorLine;
        Drawable createEmojiIconSelectorDrawable2 = Theme.createEmojiIconSelectorDrawable(context, i8, getThemedColor(i9), getThemedColor(i9));
        this.searchIconDotDrawable = createEmojiIconSelectorDrawable2;
        this.stickerIcons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, i6, getThemedColor(i7), getThemedColor(i5)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.emoji_tabs_faves, getThemedColor(i7), getThemedColor(i5)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.emoji_tabs_new3, getThemedColor(i7), getThemedColor(i5)), new LayerDrawable(new Drawable[]{createEmojiIconSelectorDrawable, createEmojiIconSelectorDrawable2})};
        this.gifIcons = new Drawable[]{Theme.createEmojiIconSelectorDrawable(context, i6, getThemedColor(i7), getThemedColor(i5)), Theme.createEmojiIconSelectorDrawable(context, R.drawable.stickers_gifs_trending, getThemedColor(i7), getThemedColor(i5))};
        int i10 = 8;
        this.emojiTitles = new String[]{LocaleController.getString("Emoji1", R.string.Emoji1), LocaleController.getString("Emoji2", R.string.Emoji2), LocaleController.getString("Emoji3", R.string.Emoji3), LocaleController.getString("Emoji4", R.string.Emoji4), LocaleController.getString("Emoji5", R.string.Emoji5), LocaleController.getString("Emoji6", R.string.Emoji6), LocaleController.getString("Emoji7", R.string.Emoji7), LocaleController.getString("Emoji8", R.string.Emoji8)};
        this.info = tLRPC$ChatFull;
        Paint paint = new Paint(1);
        this.dotPaint = paint;
        paint.setColor(getThemedColor(Theme.key_chat_emojiPanelNewTrending));
        int i11 = Build.VERSION.SDK_INT;
        if (i11 >= 21) {
            this.outlineProvider = new ViewOutlineProvider(this) { // from class: org.telegram.ui.Components.EmojiView.4
                @Override // android.view.ViewOutlineProvider
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(view.getPaddingLeft(), view.getPaddingTop(), view.getMeasuredWidth() - view.getPaddingRight(), view.getMeasuredHeight() - view.getPaddingBottom(), AndroidUtilities.dp(6.0f));
                }
            };
        }
        this.emojiContainer = new FrameLayout(context);
        Tab tab = new Tab();
        tab.type = 0;
        tab.view = this.emojiContainer;
        this.allTabs.add(tab);
        if (z) {
            MediaDataController.getInstance(this.currentAccount).checkStickers(5);
            MediaDataController.getInstance(this.currentAccount).checkFeaturedEmoji();
            this.animatedEmojiTextColorFilter = new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlackText), PorterDuff.Mode.SRC_IN);
        }
        this.emojiGridView = new EmojiGridView(context);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDelay(0L);
        defaultItemAnimator.setAddDuration(220L);
        defaultItemAnimator.setMoveDuration(220L);
        defaultItemAnimator.setChangeDuration(160L);
        defaultItemAnimator.setMoveInterpolator(CubicBezierInterpolator.EASE_OUT);
        this.emojiGridView.setItemAnimator(defaultItemAnimator);
        this.emojiGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$new$1;
                lambda$new$1 = EmojiView.this.lambda$new$1(resourcesProvider, view, motionEvent);
                return lambda$new$1;
            }
        });
        this.emojiGridView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.Components.EmojiView.5
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public boolean onItemClick(View view, int i12) {
                String str;
                boolean z5;
                int i13;
                if (view instanceof ImageViewEmoji) {
                    ImageViewEmoji imageViewEmoji = (ImageViewEmoji) view;
                    if (imageViewEmoji.isRecent) {
                        RecyclerView.ViewHolder findContainingViewHolder = EmojiView.this.emojiGridView.findContainingViewHolder(view);
                        if (findContainingViewHolder != null && findContainingViewHolder.getAdapterPosition() <= EmojiView.this.getRecentEmoji().size()) {
                            EmojiView.this.delegate.onClearEmojiRecent();
                        }
                        EmojiView.this.emojiGridView.clearTouchesFor(view);
                        return true;
                    }
                    if (imageViewEmoji.getSpan() != null || (str = (String) imageViewEmoji.getTag()) == null) {
                        return false;
                    }
                    String replace = str.replace("🏻", "").replace("🏼", "").replace("🏽", "").replace("🏾", "").replace("🏿", "");
                    String str2 = imageViewEmoji.isRecent ? null : Emoji.emojiColor.get(replace);
                    if (!CompoundEmoji.isCompound(replace)) {
                        z5 = EmojiData.emojiColoredMap.contains(replace) ? false : true;
                    }
                    EmojiView.this.emojiTouchedView = imageViewEmoji;
                    EmojiView emojiView = EmojiView.this;
                    emojiView.emojiTouchedX = emojiView.emojiLastX;
                    EmojiView emojiView2 = EmojiView.this;
                    emojiView2.emojiTouchedY = emojiView2.emojiLastY;
                    if (!z5) {
                        EmojiView.this.colorPickerView.setSelection(CompoundEmoji.skinTones.indexOf(str2) + 1);
                    } else {
                        replace = EmojiView.addColorToCode(replace, str2);
                    }
                    EmojiView.this.colorPickerView.setEmoji(replace);
                    int popupWidth = EmojiView.this.colorPickerView.getPopupWidth();
                    int popupHeight = EmojiView.this.colorPickerView.getPopupHeight();
                    imageViewEmoji.getLocationOnScreen(EmojiView.this.location);
                    if (EmojiView.this.colorPickerView.isCompound()) {
                        i13 = 0;
                    } else {
                        i13 = (EmojiView.this.emojiSize * EmojiView.this.colorPickerView.getSelection()) + AndroidUtilities.dp((EmojiView.this.colorPickerView.getSelection() * 4) - (AndroidUtilities.isTablet() ? 5 : 1));
                    }
                    if (EmojiView.this.location[0] - i13 < AndroidUtilities.dp(5.0f)) {
                        i13 += (EmojiView.this.location[0] - i13) - AndroidUtilities.dp(5.0f);
                    } else if ((EmojiView.this.location[0] - i13) + popupWidth > AndroidUtilities.displaySize.x - AndroidUtilities.dp(5.0f)) {
                        i13 += ((EmojiView.this.location[0] - i13) + popupWidth) - (AndroidUtilities.displaySize.x - AndroidUtilities.dp(5.0f));
                    }
                    int i14 = -i13;
                    int top = imageViewEmoji.getTop() < 0 ? imageViewEmoji.getTop() : 0;
                    EmojiView.this.colorPickerView.setupArrow((AndroidUtilities.dp(AndroidUtilities.isTablet() ? 30.0f : 22.0f) - i14) + ((int) AndroidUtilities.dpf2(0.5f)));
                    EmojiView.this.colorPickerView.setFocusable(true);
                    EmojiView.this.colorPickerView.showAsDropDown(view, i14, (((-view.getMeasuredHeight()) - popupHeight) + ((view.getMeasuredHeight() - EmojiView.this.emojiSize) / 2)) - top);
                    EmojiView.this.pager.requestDisallowInterceptTouchEvent(true);
                    EmojiView.this.emojiGridView.hideSelector(true);
                    EmojiView.this.emojiGridView.clearTouchesFor(view);
                    return true;
                }
                return false;
            }
        });
        this.emojiGridView.setInstantClick(true);
        EmojiGridView emojiGridView = this.emojiGridView;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, i10) { // from class: org.telegram.ui.Components.EmojiView.6
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i12) {
                try {
                    LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2) { // from class: org.telegram.ui.Components.EmojiView.6.1
                        @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
                        public void onEnd() {
                            EmojiView.this.emojiSmoothScrolling = false;
                        }
                    };
                    linearSmoothScrollerCustom.setTargetPosition(i12);
                    startSmoothScroll(linearSmoothScrollerCustom);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        };
        this.emojiLayoutManager = gridLayoutManager;
        emojiGridView.setLayoutManager(gridLayoutManager);
        this.emojiGridView.setTopGlowOffset(AndroidUtilities.dp(38.0f));
        this.emojiGridView.setBottomGlowOffset(AndroidUtilities.dp(36.0f));
        this.emojiGridView.setPadding(AndroidUtilities.dp(5.0f), AndroidUtilities.dp(36.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(44.0f));
        EmojiGridView emojiGridView2 = this.emojiGridView;
        int i12 = Theme.key_chat_emojiPanelBackground;
        emojiGridView2.setGlowColor(getThemedColor(i12));
        this.emojiGridView.setSelectorDrawableColor(0);
        this.emojiGridView.setClipToPadding(false);
        this.emojiLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.EmojiView.7
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i13) {
                if (EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiSearchAdapter) {
                    if ((EmojiView.this.needEmojiSearch && i13 == 0) || i13 == EmojiView.this.emojiAdapter.trendingRow || i13 == EmojiView.this.emojiAdapter.trendingHeaderRow || i13 == EmojiView.this.emojiAdapter.recentlyUsedHeaderRow || EmojiView.this.emojiAdapter.positionToSection.indexOfKey(i13) >= 0 || EmojiView.this.emojiAdapter.positionToUnlock.indexOfKey(i13) >= 0) {
                        return EmojiView.this.emojiLayoutManager.getSpanCount();
                    }
                } else if (i13 == 0 || (i13 == 1 && EmojiView.this.emojiSearchAdapter.searchWas && EmojiView.this.emojiSearchAdapter.result.isEmpty())) {
                    return EmojiView.this.emojiLayoutManager.getSpanCount();
                }
                return 1;
            }

            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanGroupIndex(int i13, int i14) {
                return super.getSpanGroupIndex(i13, i14);
            }
        });
        EmojiGridView emojiGridView3 = this.emojiGridView;
        EmojiGridAdapter emojiGridAdapter = new EmojiGridAdapter();
        this.emojiAdapter = emojiGridAdapter;
        emojiGridView3.setAdapter(emojiGridAdapter);
        this.emojiGridView.addItemDecoration(new EmojiGridSpacing());
        this.emojiSearchAdapter = new EmojiSearchAdapter();
        this.emojiContainer.addView(this.emojiGridView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerAnimationScrollHelper recyclerAnimationScrollHelper = new RecyclerAnimationScrollHelper(this.emojiGridView, this.emojiLayoutManager);
        this.emojiScrollHelper = recyclerAnimationScrollHelper;
        recyclerAnimationScrollHelper.setAnimationCallback(new RecyclerAnimationScrollHelper.AnimationCallback() { // from class: org.telegram.ui.Components.EmojiView.8
            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void onPreAnimation() {
                EmojiView.this.emojiGridView.updateEmojiDrawables();
                EmojiView.this.emojiSmoothScrolling = true;
            }

            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void onEndAnimation() {
                EmojiView.this.emojiSmoothScrolling = false;
                EmojiView.this.emojiGridView.updateEmojiDrawables();
            }

            @Override // org.telegram.ui.Components.RecyclerAnimationScrollHelper.AnimationCallback
            public void ignoreView(View view, boolean z5) {
                if (view instanceof ImageViewEmoji) {
                    ((ImageViewEmoji) view).ignoring = z5;
                }
            }
        });
        this.emojiGridView.setOnScrollListener(new TypedScrollListener(1) { // from class: org.telegram.ui.Components.EmojiView.9
            @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i13, int i14) {
                EmojiView.this.updateEmojiTabsPosition();
                super.onScrolled(recyclerView, i13, i14);
            }

            @Override // org.telegram.ui.Components.EmojiView.TypedScrollListener, androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i13) {
                if (i13 == 0) {
                    EmojiView.this.emojiSmoothScrolling = false;
                }
                super.onScrollStateChanged(recyclerView, i13);
            }
        });
        this.emojiTabs = new EmojiTabsStrip(context, resourcesProvider, true, z, 0, baseFragment != null ? new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                EmojiView.this.lambda$new$2();
            }
        } : null) { // from class: org.telegram.ui.Components.EmojiView.10
            @Override // org.telegram.ui.Components.EmojiTabsStrip
            protected boolean isInstalled(EmojiPack emojiPack) {
                return emojiPack.installed || EmojiView.this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.id));
            }

            @Override // org.telegram.ui.Components.EmojiTabsStrip
            protected boolean allowEmojisForNonPremium() {
                return EmojiView.this.allowEmojisForNonPremium;
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                if (EmojiView.this.emojiTabsShadow != null) {
                    EmojiView.this.emojiTabsShadow.setTranslationY(f);
                }
            }

            @Override // org.telegram.ui.Components.EmojiTabsStrip
            protected boolean doIncludeFeatured() {
                return EmojiView.this.featuredEmojiSets.size() <= 0 || ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set == null || MessagesController.getEmojiSettings(EmojiView.this.currentAccount).getLong("emoji_featured_hidden", 0L) == ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.id || !UserConfig.getInstance(UserConfig.selectedAccount).isPremium();
            }

            /* JADX WARN: Removed duplicated region for block: B:36:0x00dc  */
            /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
            @Override // org.telegram.ui.Components.EmojiTabsStrip
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected boolean onTabClick(int r10) {
                /*
                    Method dump skipped, instructions count: 254
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.AnonymousClass10.onTabClick(int):boolean");
            }

            @Override // org.telegram.ui.Components.EmojiTabsStrip
            protected ColorFilter getEmojiColorFilter() {
                return EmojiView.this.animatedEmojiTextColorFilter;
            }
        };
        if (z4) {
            SearchField searchField = new SearchField(context, 1);
            this.emojiSearchField = searchField;
            this.emojiContainer.addView(searchField, new FrameLayout.LayoutParams(-1, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
            this.emojiSearchField.searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.Components.EmojiView.11
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view, boolean z5) {
                    if (z5) {
                        EmojiView.this.lastSearchKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                        MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(EmojiView.this.lastSearchKeyboardLanguage);
                    }
                }
            });
        }
        this.emojiTabs.setBackgroundColor(getThemedColor(i12));
        this.emojiAdapter.processEmoji(true);
        this.emojiTabs.updateEmojiPacks(getEmojipacks());
        this.emojiContainer.addView(this.emojiTabs, LayoutHelper.createFrame(-1, 36.0f));
        View view = new View(context);
        this.emojiTabsShadow = view;
        view.setAlpha(0.0f);
        this.emojiTabsShadow.setTag(1);
        View view2 = this.emojiTabsShadow;
        int i13 = Theme.key_chat_emojiPanelShadowLine;
        view2.setBackgroundColor(getThemedColor(i13));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, AndroidUtilities.getShadowHeight(), 51);
        layoutParams.topMargin = AndroidUtilities.dp(36.0f);
        this.emojiContainer.addView(this.emojiTabsShadow, layoutParams);
        if (z2) {
            if (z3) {
                this.gifContainer = new FrameLayout(context);
                anonymousClass1 = null;
                Tab tab2 = new Tab();
                tab2.type = 1;
                tab2.view = this.gifContainer;
                this.allTabs.add(tab2);
                RecyclerListView recyclerListView = new RecyclerListView(context) { // from class: org.telegram.ui.Components.EmojiView.12
                    private boolean ignoreLayout;
                    private boolean wasMeasured;

                    @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                        return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.gifGridView, 0, EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
                    }

                    @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                    protected void onMeasure(int i14, int i15) {
                        super.onMeasure(i14, i15);
                        if (this.wasMeasured) {
                            return;
                        }
                        EmojiView.this.gifAdapter.notifyDataSetChanged();
                        this.wasMeasured = true;
                    }

                    @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
                    protected void onLayout(boolean z5, int i14, int i15, int i16, int i17) {
                        if (EmojiView.this.firstGifAttach && EmojiView.this.gifAdapter.getItemCount() > 1) {
                            this.ignoreLayout = true;
                            EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
                            EmojiView.this.gifSearchField.setVisibility(0);
                            EmojiView.this.gifTabs.onPageScrolled(0, 0);
                            EmojiView.this.firstGifAttach = false;
                            this.ignoreLayout = false;
                        }
                        super.onLayout(z5, i14, i15, i16, i17);
                        EmojiView.this.checkGifSearchFieldScroll(true);
                    }

                    @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
                    public void requestLayout() {
                        if (this.ignoreLayout) {
                            return;
                        }
                        super.requestLayout();
                    }
                };
                this.gifGridView = recyclerListView;
                recyclerListView.setClipToPadding(false);
                RecyclerListView recyclerListView2 = this.gifGridView;
                GifLayoutManager gifLayoutManager = new GifLayoutManager(context);
                this.gifLayoutManager = gifLayoutManager;
                recyclerListView2.setLayoutManager(gifLayoutManager);
                this.gifGridView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.EmojiView.13
                    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                    public void getItemOffsets(android.graphics.Rect rect, View view3, RecyclerView recyclerView, RecyclerView.State state) {
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(view3);
                        if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter && childAdapterPosition == EmojiView.this.gifAdapter.trendingSectionItem) {
                            rect.set(0, 0, 0, 0);
                            return;
                        }
                        if (childAdapterPosition != 0 || !EmojiView.this.gifAdapter.addSearch) {
                            rect.left = 0;
                            rect.bottom = 0;
                            rect.top = AndroidUtilities.dp(2.0f);
                            rect.right = EmojiView.this.gifLayoutManager.isLastInRow(childAdapterPosition - (EmojiView.this.gifAdapter.addSearch ? 1 : 0)) ? 0 : AndroidUtilities.dp(2.0f);
                            return;
                        }
                        rect.set(0, 0, 0, 0);
                    }
                });
                this.gifGridView.setPadding(0, this.searchFieldHeight, 0, AndroidUtilities.dp(44.0f));
                this.gifGridView.setOverScrollMode(2);
                ((SimpleItemAnimator) this.gifGridView.getItemAnimator()).setSupportsChangeAnimations(false);
                RecyclerListView recyclerListView3 = this.gifGridView;
                GifAdapter gifAdapter = new GifAdapter(this, context, true);
                this.gifAdapter = gifAdapter;
                recyclerListView3.setAdapter(gifAdapter);
                this.gifSearchAdapter = new GifAdapter(this, context);
                this.gifGridView.setOnScrollListener(new TypedScrollListener(2));
                this.gifGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view3, MotionEvent motionEvent) {
                        boolean lambda$new$3;
                        lambda$new$3 = EmojiView.this.lambda$new$3(resourcesProvider, view3, motionEvent);
                        return lambda$new$3;
                    }
                });
                RecyclerListView.OnItemClickListener onItemClickListener = new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda14
                    @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                    public final void onItemClick(View view3, int i14) {
                        EmojiView.this.lambda$new$4(view3, i14);
                    }
                };
                this.gifOnItemClickListener = onItemClickListener;
                this.gifGridView.setOnItemClickListener(onItemClickListener);
                this.gifContainer.addView(this.gifGridView, LayoutHelper.createFrame(-1, -1.0f));
                SearchField searchField2 = new SearchField(context, 2);
                this.gifSearchField = searchField2;
                this.gifContainer.addView(searchField2, new FrameLayout.LayoutParams(-1, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
                DraggableScrollSlidingTabStrip draggableScrollSlidingTabStrip = new DraggableScrollSlidingTabStrip(context, resourcesProvider);
                this.gifTabs = draggableScrollSlidingTabStrip;
                draggableScrollSlidingTabStrip.setType(ScrollSlidingTabStrip.Type.TAB);
                this.gifTabs.setUnderlineHeight(AndroidUtilities.getShadowHeight());
                this.gifTabs.setIndicatorColor(getThemedColor(i9));
                this.gifTabs.setUnderlineColor(getThemedColor(i13));
                this.gifTabs.setBackgroundColor(getThemedColor(i12));
                updateGifTabs();
                this.gifTabs.setDelegate(new ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda16
                    @Override // org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate
                    public final void onPageSelected(int i14) {
                        EmojiView.this.lambda$new$5(i14);
                    }
                });
                this.gifAdapter.loadTrendingGifs();
            } else {
                anonymousClass1 = null;
            }
            this.stickersContainer = new FrameLayout(context) { // from class: org.telegram.ui.Components.EmojiView.14
                @Override // android.view.ViewGroup, android.view.View
                protected void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    EmojiView.this.stickersContainerAttached = true;
                    EmojiView.this.updateStickerTabsPosition();
                    if (EmojiView.this.chooseStickerActionTracker != null) {
                        EmojiView.this.chooseStickerActionTracker.checkVisibility();
                    }
                }

                @Override // android.view.ViewGroup, android.view.View
                protected void onDetachedFromWindow() {
                    super.onDetachedFromWindow();
                    EmojiView.this.stickersContainerAttached = false;
                    EmojiView.this.updateStickerTabsPosition();
                    if (EmojiView.this.chooseStickerActionTracker != null) {
                        EmojiView.this.chooseStickerActionTracker.checkVisibility();
                    }
                }
            };
            MediaDataController.getInstance(this.currentAccount).checkStickers(0);
            MediaDataController.getInstance(this.currentAccount).checkFeaturedStickers();
            RecyclerListViewWithOverlayDraw recyclerListViewWithOverlayDraw = new RecyclerListViewWithOverlayDraw(context) { // from class: org.telegram.ui.Components.EmojiView.15
                boolean ignoreLayout;

                @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, EmojiView.this.stickersGridView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
                }

                @Override // org.telegram.ui.Components.RecyclerListView, android.view.View
                public void setVisibility(int i14) {
                    super.setVisibility(i14);
                }

                @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z5, int i14, int i15, int i16, int i17) {
                    if (EmojiView.this.firstStickersAttach && EmojiView.this.stickersGridAdapter.getItemCount() > 0) {
                        this.ignoreLayout = true;
                        EmojiView.this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                        EmojiView.this.firstStickersAttach = false;
                        this.ignoreLayout = false;
                    }
                    super.onLayout(z5, i14, i15, i16, i17);
                    EmojiView.this.checkStickersSearchFieldScroll(true);
                }

                @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
                public void requestLayout() {
                    if (this.ignoreLayout) {
                        return;
                    }
                    super.requestLayout();
                }

                @Override // androidx.recyclerview.widget.RecyclerView
                public void onScrolled(int i14, int i15) {
                    super.onScrolled(i14, i15);
                    if (EmojiView.this.stickersTabContainer != null) {
                        EmojiView.this.stickersTab.setUnderlineHeight(EmojiView.this.stickersGridView.canScrollVertically(-1) ? AndroidUtilities.getShadowHeight() : 0);
                    }
                }
            };
            this.stickersGridView = recyclerListViewWithOverlayDraw;
            GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 5) { // from class: org.telegram.ui.Components.EmojiView.16
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i14) {
                    try {
                        LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 2);
                        linearSmoothScrollerCustom.setTargetPosition(i14);
                        startSmoothScroll(linearSmoothScrollerCustom);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public int scrollVerticallyBy(int i14, RecyclerView.Recycler recycler, RecyclerView.State state) {
                    int scrollVerticallyBy = super.scrollVerticallyBy(i14, recycler, state);
                    if (scrollVerticallyBy != 0 && EmojiView.this.stickersGridView.getScrollState() == 1) {
                        EmojiView.this.expandStickersByDragg = false;
                        EmojiView.this.updateStickerTabsPosition();
                    }
                    if (EmojiView.this.chooseStickerActionTracker == null) {
                        EmojiView.this.createStickersChooseActionTracker();
                    }
                    EmojiView.this.chooseStickerActionTracker.doSomeAction();
                    return scrollVerticallyBy;
                }
            };
            this.stickersLayoutManager = gridLayoutManager2;
            recyclerListViewWithOverlayDraw.setLayoutManager(gridLayoutManager2);
            this.stickersLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.EmojiView.17
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i14) {
                    if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersGridAdapter) {
                        if (i14 == EmojiView.this.stickersSearchGridAdapter.totalItems || !(EmojiView.this.stickersSearchGridAdapter.cache.get(i14) == null || (EmojiView.this.stickersSearchGridAdapter.cache.get(i14) instanceof TLRPC$Document))) {
                            return EmojiView.this.stickersGridAdapter.stickersPerRow;
                        }
                        return 1;
                    }
                    if (i14 == 0) {
                        return EmojiView.this.stickersGridAdapter.stickersPerRow;
                    }
                    if (i14 == EmojiView.this.stickersGridAdapter.totalItems || !(EmojiView.this.stickersGridAdapter.cache.get(i14) == null || (EmojiView.this.stickersGridAdapter.cache.get(i14) instanceof TLRPC$Document))) {
                        return EmojiView.this.stickersGridAdapter.stickersPerRow;
                    }
                    return 1;
                }
            });
            this.stickersGridView.setPadding(0, AndroidUtilities.dp(36.0f), 0, AndroidUtilities.dp(44.0f));
            this.stickersGridView.setClipToPadding(false);
            Tab tab3 = new Tab();
            tab3.type = 2;
            tab3.view = this.stickersContainer;
            this.allTabs.add(tab3);
            this.stickersSearchGridAdapter = new StickersSearchGridAdapter(context);
            RecyclerListView recyclerListView4 = this.stickersGridView;
            StickersGridAdapter stickersGridAdapter = new StickersGridAdapter(context);
            this.stickersGridAdapter = stickersGridAdapter;
            recyclerListView4.setAdapter(stickersGridAdapter);
            this.stickersGridView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    boolean lambda$new$6;
                    lambda$new$6 = EmojiView.this.lambda$new$6(resourcesProvider, view3, motionEvent);
                    return lambda$new$6;
                }
            });
            RecyclerListView.OnItemClickListener onItemClickListener2 = new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda13
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view3, int i14) {
                    EmojiView.this.lambda$new$7(view3, i14);
                }
            };
            this.stickersOnItemClickListener = onItemClickListener2;
            this.stickersGridView.setOnItemClickListener(onItemClickListener2);
            this.stickersGridView.setGlowColor(getThemedColor(i12));
            this.stickersContainer.addView(this.stickersGridView);
            this.stickersScrollHelper = new RecyclerAnimationScrollHelper(this.stickersGridView, this.stickersLayoutManager);
            SearchField searchField3 = new SearchField(context, 0);
            this.stickersSearchField = searchField3;
            this.stickersContainer.addView(searchField3, new FrameLayout.LayoutParams(-1, this.searchFieldHeight + AndroidUtilities.getShadowHeight()));
            AnonymousClass18 anonymousClass18 = new AnonymousClass18(context, resourcesProvider, baseFragment);
            this.stickersTab = anonymousClass18;
            anonymousClass18.setDragEnabled(true);
            this.stickersTab.setWillNotDraw(false);
            this.stickersTab.setType(ScrollSlidingTabStrip.Type.TAB);
            this.stickersTab.setUnderlineHeight(this.stickersGridView.canScrollVertically(-1) ? AndroidUtilities.getShadowHeight() : 0);
            this.stickersTab.setIndicatorColor(getThemedColor(i9));
            this.stickersTab.setUnderlineColor(getThemedColor(i13));
            if (viewGroup != null) {
                FrameLayout frameLayout = new FrameLayout(context) { // from class: org.telegram.ui.Components.EmojiView.19
                    Paint paint = new Paint();

                    @Override // android.view.ViewGroup, android.view.View
                    protected void dispatchDraw(Canvas canvas) {
                        float dp = AndroidUtilities.dp(50.0f) * EmojiView.this.delegate.getProgressToSearchOpened();
                        if (dp > getMeasuredHeight()) {
                            return;
                        }
                        canvas.save();
                        if (dp != 0.0f) {
                            canvas.clipRect(0.0f, dp, getMeasuredWidth(), getMeasuredHeight());
                        }
                        this.paint.setColor(EmojiView.this.getThemedColor(Theme.key_chat_emojiPanelBackground));
                        canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.dp(36.0f) + EmojiView.this.stickersTab.getExpandedOffset(), this.paint);
                        super.dispatchDraw(canvas);
                        EmojiView.this.stickersTab.drawOverlays(canvas);
                        canvas.restore();
                    }

                    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                    protected void onLayout(boolean z5, int i14, int i15, int i16, int i17) {
                        super.onLayout(z5, i14, i15, i16, i17);
                        EmojiView.this.updateStickerTabsPosition();
                    }
                };
                this.stickersTabContainer = frameLayout;
                frameLayout.addView(this.stickersTab, LayoutHelper.createFrame(-1, 36, 51));
                viewGroup.addView(this.stickersTabContainer, LayoutHelper.createFrame(-1, -2.0f));
            } else {
                this.stickersContainer.addView(this.stickersTab, LayoutHelper.createFrame(-1, 36, 51));
            }
            updateStickerTabs(true);
            this.stickersTab.setDelegate(new ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda15
                @Override // org.telegram.ui.Components.ScrollSlidingTabStrip.ScrollSlidingTabStripDelegate
                public final void onPageSelected(int i14) {
                    EmojiView.this.lambda$new$8(i14);
                }
            });
            this.stickersGridView.setOnScrollListener(new TypedScrollListener(0));
        } else {
            anonymousClass1 = null;
        }
        this.currentTabs.clear();
        this.currentTabs.addAll(this.allTabs);
        ViewPager viewPager = new ViewPager(context) { // from class: org.telegram.ui.Components.EmojiView.20
            @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (EmojiView.this.ignorePagerScroll) {
                    return false;
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(-1));
                }
                try {
                    return super.onInterceptTouchEvent(motionEvent);
                } catch (IllegalArgumentException unused) {
                    return false;
                }
            }

            @Override // androidx.viewpager.widget.ViewPager
            public void setCurrentItem(int i14, boolean z5) {
                EmojiView.this.startStopVisibleGifs(i14 == 1);
                if (i14 != getCurrentItem()) {
                    super.setCurrentItem(i14, z5);
                    return;
                }
                if (i14 != 0) {
                    if (i14 == 1) {
                        EmojiView.this.gifGridView.smoothScrollToPosition(0);
                        return;
                    } else {
                        EmojiView.this.stickersGridView.smoothScrollToPosition(1);
                        return;
                    }
                }
                EmojiView.this.tabsMinusDy[1] = 0;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(EmojiView.this.emojiTabs, (Property<EmojiTabsStrip, Float>) ViewGroup.TRANSLATION_Y, 0.0f);
                ofFloat.setDuration(150L);
                ofFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                ofFloat.start();
                EmojiView.this.scrollEmojisToPosition(1, 0);
                if (EmojiView.this.emojiTabs != null) {
                    EmojiView.this.emojiTabs.select(0);
                }
            }
        };
        this.pager = viewPager;
        EmojiPagesAdapter emojiPagesAdapter = new EmojiPagesAdapter();
        this.emojiPagerAdapter = emojiPagesAdapter;
        viewPager.setAdapter(emojiPagesAdapter);
        ImageView imageView = new ImageView(context) { // from class: org.telegram.ui.Components.EmojiView.21
            @Override // android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    EmojiView.this.backspacePressed = true;
                    EmojiView.this.backspaceOnce = false;
                    EmojiView.this.postBackspaceRunnable(350);
                } else if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
                    EmojiView.this.backspacePressed = false;
                    if (!EmojiView.this.backspaceOnce && EmojiView.this.delegate != null && EmojiView.this.delegate.onBackspace()) {
                        EmojiView.this.backspaceButton.performHapticFeedback(3);
                    }
                }
                super.onTouchEvent(motionEvent);
                return true;
            }
        };
        this.backspaceButton = imageView;
        imageView.setHapticFeedbackEnabled(true);
        this.backspaceButton.setImageResource(R.drawable.smiles_tab_clear);
        this.backspaceButton.setColorFilter(new PorterDuffColorFilter(getThemedColor(i4), PorterDuff.Mode.MULTIPLY));
        this.backspaceButton.setScaleType(ImageView.ScaleType.CENTER);
        ImageView imageView2 = this.backspaceButton;
        int i14 = R.string.AccDescrBackspace;
        imageView2.setContentDescription(LocaleController.getString("AccDescrBackspace", i14));
        this.backspaceButton.setFocusable(true);
        this.backspaceButton.setOnClickListener(new View.OnClickListener(this) { // from class: org.telegram.ui.Components.EmojiView.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view3) {
            }
        });
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.bulletinContainer = frameLayout2;
        if (z4) {
            addView(frameLayout2, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, (AndroidUtilities.getShadowHeight() / AndroidUtilities.density) + 40.0f));
        } else {
            addView(frameLayout2, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 0.0f));
        }
        FrameLayout frameLayout3 = new FrameLayout(context);
        this.bottomTabContainer = frameLayout3;
        frameLayout3.setClickable(true);
        View view3 = new View(context);
        this.shadowLine = view3;
        view3.setBackgroundColor(getThemedColor(i13));
        this.bottomTabContainer.addView(this.shadowLine, new FrameLayout.LayoutParams(-1, AndroidUtilities.getShadowHeight()));
        View view4 = new View(context);
        this.bottomTabContainerBackground = view4;
        this.bottomTabContainer.addView(view4, new FrameLayout.LayoutParams(-1, AndroidUtilities.dp(40.0f), 83));
        if (z4) {
            addView(this.bottomTabContainer, new FrameLayout.LayoutParams(-1, AndroidUtilities.dp(40.0f) + AndroidUtilities.getShadowHeight(), 83));
            this.bottomTabContainer.addView(this.backspaceButton, LayoutHelper.createFrame(47, 40, 85));
            if (i11 >= 21) {
                i2 = argb;
                this.backspaceButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.dp(18.0f)));
            } else {
                i2 = argb;
            }
            ImageView imageView3 = new ImageView(context);
            this.stickerSettingsButton = imageView3;
            imageView3.setImageResource(R.drawable.smiles_tab_settings);
            this.stickerSettingsButton.setColorFilter(new PorterDuffColorFilter(getThemedColor(i4), PorterDuff.Mode.MULTIPLY));
            this.stickerSettingsButton.setScaleType(ImageView.ScaleType.CENTER);
            this.stickerSettingsButton.setFocusable(true);
            if (i11 >= 21) {
                this.stickerSettingsButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.dp(18.0f)));
            }
            this.stickerSettingsButton.setContentDescription(LocaleController.getString("Settings", R.string.Settings));
            this.bottomTabContainer.addView(this.stickerSettingsButton, LayoutHelper.createFrame(47, 40, 85));
            this.stickerSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView.23
                @Override // android.view.View.OnClickListener
                public void onClick(View view5) {
                    if (EmojiView.this.delegate != null) {
                        EmojiView.this.delegate.onStickersSettingsClick();
                    }
                }
            });
            PagerSlidingTabStrip pagerSlidingTabStrip = new PagerSlidingTabStrip(context, resourcesProvider);
            this.typeTabs = pagerSlidingTabStrip;
            pagerSlidingTabStrip.setViewPager(this.pager);
            this.typeTabs.setShouldExpand(false);
            this.typeTabs.setIndicatorHeight(AndroidUtilities.dp(3.0f));
            this.typeTabs.setIndicatorColor(getThemedColor(i5));
            this.typeTabs.setUnderlineHeight(0);
            this.typeTabs.setTabPaddingLeftRight(AndroidUtilities.dp(13.0f));
            this.bottomTabContainer.addView(this.typeTabs, LayoutHelper.createFrame(-2, 40, 81));
            this.typeTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: org.telegram.ui.Components.EmojiView.24
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i15) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i15, float f, int i16) {
                    EmojiView.this.checkGridVisibility(i15, f);
                    EmojiView emojiView = EmojiView.this;
                    emojiView.onPageScrolled(i15, (emojiView.getMeasuredWidth() - EmojiView.this.getPaddingLeft()) - EmojiView.this.getPaddingRight(), i16);
                    boolean z5 = true;
                    EmojiView.this.showBottomTab(true, true);
                    int currentItem = EmojiView.this.pager.getCurrentItem();
                    SearchField searchField4 = currentItem == 0 ? EmojiView.this.emojiSearchField : currentItem == 1 ? EmojiView.this.gifSearchField : EmojiView.this.stickersSearchField;
                    String obj = searchField4.searchEditText.getText().toString();
                    int i17 = 0;
                    while (i17 < 3) {
                        SearchField searchField5 = i17 == 0 ? EmojiView.this.emojiSearchField : i17 == 1 ? EmojiView.this.gifSearchField : EmojiView.this.stickersSearchField;
                        if (searchField5 != null && searchField5 != searchField4 && searchField5.searchEditText != null && !searchField5.searchEditText.getText().toString().equals(obj)) {
                            searchField5.searchEditText.setText(obj);
                            searchField5.searchEditText.setSelection(obj.length());
                        }
                        i17++;
                    }
                    EmojiView emojiView2 = EmojiView.this;
                    if ((i15 != 0 || f <= 0.0f) && i15 != 1) {
                        z5 = false;
                    }
                    emojiView2.startStopVisibleGifs(z5);
                    EmojiView.this.updateStickerTabsPosition();
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i15) {
                    EmojiView.this.saveNewPage();
                    EmojiView.this.showBackspaceButton(i15 == 0, true);
                    EmojiView.this.showStickerSettingsButton(i15 == 2, true);
                    if (EmojiView.this.delegate.isSearchOpened()) {
                        if (i15 == 0) {
                            if (EmojiView.this.emojiSearchField != null) {
                                EmojiView.this.emojiSearchField.searchEditText.requestFocus();
                            }
                        } else if (i15 == 1) {
                            if (EmojiView.this.gifSearchField != null) {
                                EmojiView.this.gifSearchField.searchEditText.requestFocus();
                            }
                        } else if (EmojiView.this.stickersSearchField != null) {
                            EmojiView.this.stickersSearchField.searchEditText.requestFocus();
                        }
                    }
                }
            });
            ImageView imageView4 = new ImageView(context);
            this.searchButton = imageView4;
            imageView4.setImageResource(R.drawable.smiles_tab_search);
            this.searchButton.setColorFilter(new PorterDuffColorFilter(getThemedColor(i4), PorterDuff.Mode.MULTIPLY));
            this.searchButton.setScaleType(ImageView.ScaleType.CENTER);
            this.searchButton.setContentDescription(LocaleController.getString("Search", R.string.Search));
            this.searchButton.setFocusable(true);
            this.searchButton.setVisibility(8);
            if (i11 >= 21) {
                this.searchButton.setBackground(Theme.createSelectorDrawable(i2, 1, AndroidUtilities.dp(18.0f)));
            }
            this.bottomTabContainer.addView(this.searchButton, LayoutHelper.createFrame(47, 40, 83));
            this.searchButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView.25
                @Override // android.view.View.OnClickListener
                public void onClick(View view5) {
                    int currentItem = EmojiView.this.pager.getCurrentItem();
                    SearchField searchField4 = currentItem == 0 ? EmojiView.this.emojiSearchField : currentItem == 1 ? EmojiView.this.gifSearchField : EmojiView.this.stickersSearchField;
                    if (searchField4 == null) {
                        return;
                    }
                    searchField4.searchEditText.requestFocus();
                    MotionEvent obtain = MotionEvent.obtain(0L, 0L, 0, 0.0f, 0.0f, 0);
                    searchField4.searchEditText.onTouchEvent(obtain);
                    obtain.recycle();
                    MotionEvent obtain2 = MotionEvent.obtain(0L, 0L, 1, 0.0f, 0.0f, 0);
                    searchField4.searchEditText.onTouchEvent(obtain2);
                    obtain2.recycle();
                }
            });
            i = 4;
        } else {
            addView(this.bottomTabContainer, LayoutHelper.createFrame((i11 >= 21 ? 40 : 44) + 16, (i11 >= 21 ? 40 : 44) + 8, (LocaleController.isRTL ? 3 : 5) | 80, 0.0f, 0.0f, 2.0f, 0.0f));
            Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), getThemedColor(i12), getThemedColor(i12));
            if (i11 < 21) {
                Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow).mutate();
                mutate.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
                CombinedDrawable combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
                combinedDrawable.setIconSize(AndroidUtilities.dp(36.0f), AndroidUtilities.dp(36.0f));
                createSimpleSelectorCircleDrawable = combinedDrawable;
            } else {
                StateListAnimator stateListAnimator = new StateListAnimator();
                int[] iArr = {android.R.attr.state_pressed};
                ImageView imageView5 = this.floatingButton;
                Property property = View.TRANSLATION_Z;
                stateListAnimator.addState(iArr, ObjectAnimator.ofFloat(imageView5, (Property<ImageView, Float>) property, AndroidUtilities.dp(2.0f), AndroidUtilities.dp(4.0f)).setDuration(200L));
                stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, (Property<ImageView, Float>) property, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(2.0f)).setDuration(200L));
                this.backspaceButton.setStateListAnimator(stateListAnimator);
                this.backspaceButton.setOutlineProvider(new ViewOutlineProvider(this) { // from class: org.telegram.ui.Components.EmojiView.26
                    @Override // android.view.ViewOutlineProvider
                    @SuppressLint({"NewApi"})
                    public void getOutline(View view5, Outline outline) {
                        outline.setOval(0, 0, view5.getMeasuredWidth(), view5.getMeasuredHeight());
                    }
                });
            }
            this.backspaceButton.setPadding(0, 0, AndroidUtilities.dp(2.0f), 0);
            this.backspaceButton.setBackground(createSimpleSelectorCircleDrawable);
            this.backspaceButton.setContentDescription(LocaleController.getString("AccDescrBackspace", i14));
            this.backspaceButton.setFocusable(true);
            i = 4;
            this.bottomTabContainer.addView(this.backspaceButton, LayoutHelper.createFrame((i11 >= 21 ? 40 : 44) - 4, (i11 >= 21 ? 40 : 44) - 4, 51, 10.0f, 0.0f, 10.0f, 0.0f));
            this.shadowLine.setVisibility(8);
            this.bottomTabContainerBackground.setVisibility(8);
        }
        addView(this.pager, 0, LayoutHelper.createFrame(-1, -1, 51));
        CorrectlyMeasuringTextView correctlyMeasuringTextView = new CorrectlyMeasuringTextView(context);
        this.mediaBanTooltip = correctlyMeasuringTextView;
        correctlyMeasuringTextView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.dp(6.0f), getThemedColor(Theme.key_chat_gifSaveHintBackground)));
        this.mediaBanTooltip.setTextColor(getThemedColor(Theme.key_chat_gifSaveHintText));
        this.mediaBanTooltip.setPadding(AndroidUtilities.dp(12.0f), AndroidUtilities.dp(7.0f), AndroidUtilities.dp(12.0f), AndroidUtilities.dp(7.0f));
        this.mediaBanTooltip.setGravity(16);
        this.mediaBanTooltip.setTextSize(1, 14.0f);
        this.mediaBanTooltip.setVisibility(i);
        addView(this.mediaBanTooltip, LayoutHelper.createFrame(-2, -2.0f, 81, 5.0f, 0.0f, 5.0f, 53.0f));
        this.emojiSize = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 40.0f : 32.0f);
        EmojiColorPickerWindow create = EmojiColorPickerWindow.create(context, resourcesProvider);
        this.colorPickerView = create;
        create.setOnSelectionUpdateListener(new Utilities.Callback2() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                EmojiView.this.lambda$new$9((Integer) obj, (Integer) obj2);
            }
        });
        this.currentPage = MessagesController.getGlobalEmojiSettings().getInt("selected_page", 0);
        Emoji.loadRecentEmoji();
        this.emojiAdapter.notifyDataSetChanged();
        setAllow(z2, z3, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$1(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.emojiGridView, getMeasuredHeight(), null, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null) {
            emojiViewDelegate.onEmojiSettingsClick(this.emojiAdapter.frozenEmojiPacks);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.gifGridView, 0, this.gifOnItemClickListener, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view, int i) {
        if (this.delegate == null) {
            return;
        }
        if (this.gifAdapter.addSearch) {
            i--;
        }
        RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
        GifAdapter gifAdapter = this.gifAdapter;
        if (adapter != gifAdapter) {
            RecyclerView.Adapter adapter2 = this.gifGridView.getAdapter();
            GifAdapter gifAdapter2 = this.gifSearchAdapter;
            if (adapter2 != gifAdapter2 || i < 0 || i >= gifAdapter2.results.size()) {
                return;
            }
            this.delegate.lambda$onGifSelected$1(view, this.gifSearchAdapter.results.get(i), this.gifSearchAdapter.lastSearchImageString, this.gifSearchAdapter.bot, true, 0);
            updateRecentGifs();
            return;
        }
        if (i < 0) {
            return;
        }
        if (i < gifAdapter.recentItemsCount) {
            this.delegate.lambda$onGifSelected$1(view, this.recentGifs.get(i), null, "gif", true, 0);
            return;
        }
        if (this.gifAdapter.recentItemsCount > 0) {
            i = (i - this.gifAdapter.recentItemsCount) - 1;
        }
        if (i < 0 || i >= this.gifAdapter.results.size()) {
            return;
        }
        this.delegate.lambda$onGifSelected$1(view, this.gifAdapter.results.get(i), null, this.gifAdapter.bot, true, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(int i) {
        if (i == this.gifTrendingTabNum && this.gifAdapter.results.isEmpty()) {
            return;
        }
        this.gifGridView.stopScroll();
        this.gifTabs.onPageScrolled(i, 0);
        int i2 = 1;
        if (i == this.gifRecentTabNum || i == this.gifTrendingTabNum) {
            this.gifSearchField.searchEditText.setText("");
            if (i != this.gifTrendingTabNum || this.gifAdapter.trendingSectionItem < 1) {
                GifLayoutManager gifLayoutManager = this.gifLayoutManager;
                EmojiViewDelegate emojiViewDelegate = this.delegate;
                if (emojiViewDelegate != null && emojiViewDelegate.isExpanded()) {
                    i2 = 0;
                }
                gifLayoutManager.scrollToPositionWithOffset(i2, 0);
            } else {
                this.gifLayoutManager.scrollToPositionWithOffset(this.gifAdapter.trendingSectionItem, -AndroidUtilities.dp(4.0f));
            }
            if (i == this.gifTrendingTabNum) {
                ArrayList<String> arrayList = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
                if (!arrayList.isEmpty()) {
                    this.gifSearchPreloader.preload(arrayList.get(0));
                }
            }
        } else {
            ArrayList<String> arrayList2 = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
            this.gifSearchAdapter.searchEmoji(arrayList2.get(i - this.gifFirstEmojiTabNum));
            int i3 = this.gifFirstEmojiTabNum;
            if (i - i3 > 0) {
                this.gifSearchPreloader.preload(arrayList2.get((i - i3) - 1));
            }
            if (i - this.gifFirstEmojiTabNum < arrayList2.size() - 1) {
                this.gifSearchPreloader.preload(arrayList2.get((i - this.gifFirstEmojiTabNum) + 1));
            }
        }
        resetTabsY(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$6(Theme.ResourcesProvider resourcesProvider, View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, this.stickersGridView, getMeasuredHeight(), this.stickersOnItemClickListener, this.contentPreviewViewerDelegate, resourcesProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(View view, int i) {
        String str;
        RecyclerView.Adapter adapter = this.stickersGridView.getAdapter();
        StickersSearchGridAdapter stickersSearchGridAdapter = this.stickersSearchGridAdapter;
        if (adapter == stickersSearchGridAdapter) {
            String str2 = stickersSearchGridAdapter.searchQuery;
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered = (TLRPC$StickerSetCovered) this.stickersSearchGridAdapter.positionsToSets.get(i);
            if (tLRPC$StickerSetCovered != null) {
                this.delegate.onShowStickerSet(tLRPC$StickerSetCovered.set, null);
                return;
            }
            str = str2;
        } else {
            str = null;
        }
        if (view instanceof StickerEmojiCell) {
            StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) view;
            if (stickerEmojiCell.getSticker() != null && MessageObject.isPremiumSticker(stickerEmojiCell.getSticker()) && !AccountInstance.getInstance(this.currentAccount).getUserConfig().isPremium()) {
                ContentPreviewViewer.getInstance().showMenuFor(stickerEmojiCell);
                return;
            }
            ContentPreviewViewer.getInstance().reset();
            if (stickerEmojiCell.isDisabled()) {
                return;
            }
            stickerEmojiCell.disable();
            this.delegate.onStickerSelected(stickerEmojiCell, stickerEmojiCell.getSticker(), str, stickerEmojiCell.getParentObject(), stickerEmojiCell.getSendAnimationData(), true, 0);
        }
    }

    /* renamed from: org.telegram.ui.Components.EmojiView$18, reason: invalid class name */
    class AnonymousClass18 extends DraggableScrollSlidingTabStrip {
        final /* synthetic */ BaseFragment val$fragment;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$sendReorder$2(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass18(Context context, Theme.ResourcesProvider resourcesProvider, BaseFragment baseFragment) {
            super(context, resourcesProvider);
            this.val$fragment = baseFragment;
        }

        @Override // org.telegram.ui.Components.ScrollSlidingTabStrip
        protected void updatePosition() {
            EmojiView.this.updateStickerTabsPosition();
            EmojiView.this.stickersTabContainer.invalidate();
            invalidate();
            if (EmojiView.this.delegate != null) {
                EmojiView.this.delegate.invalidateEnterView();
            }
        }

        @Override // org.telegram.ui.Components.ScrollSlidingTabStrip
        protected void stickerSetPositionChanged(int i, int i2) {
            int i3 = i - EmojiView.this.stickersTabOffset;
            int i4 = i2 - EmojiView.this.stickersTabOffset;
            MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
            swapListElements(EmojiView.this.stickerSets, i3, i4);
            Collections.sort(mediaDataController.getStickerSets(0), new Comparator() { // from class: org.telegram.ui.Components.EmojiView$18$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$stickerSetPositionChanged$0;
                    lambda$stickerSetPositionChanged$0 = EmojiView.AnonymousClass18.this.lambda$stickerSetPositionChanged$0((TLRPC$TL_messages_stickerSet) obj, (TLRPC$TL_messages_stickerSet) obj2);
                    return lambda$stickerSetPositionChanged$0;
                }
            });
            ArrayList<TLRPC$TL_messages_stickerSet> arrayList = EmojiView.this.frozenStickerSets;
            if (arrayList != null) {
                arrayList.clear();
                EmojiView emojiView = EmojiView.this;
                emojiView.frozenStickerSets.addAll(emojiView.stickerSets);
            }
            EmojiView.this.reloadStickersAdapter();
            AndroidUtilities.cancelRunOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable);
            AndroidUtilities.runOnUIThread(EmojiView.this.checkExpandStickerTabsRunnable, 1500L);
            sendReorder();
            EmojiView.this.updateStickerTabs(true);
            if (SharedConfig.updateStickersOrderOnSend) {
                SharedConfig.toggleUpdateStickersOrderOnSend();
                BaseFragment baseFragment = this.val$fragment;
                if (baseFragment == null) {
                    if (EmojiView.this.bulletinContainer != null) {
                        BulletinFactory.of(EmojiView.this.bulletinContainer, EmojiView.this.resourcesProvider).createSimpleBulletin(R.raw.filter_reorder, LocaleController.getString("DynamicPackOrderOff", R.string.DynamicPackOrderOff), LocaleController.getString("DynamicPackOrderOffInfo", R.string.DynamicPackOrderOffInfo)).show();
                        return;
                    }
                    return;
                }
                BulletinFactory of = BulletinFactory.of(baseFragment);
                int i5 = R.raw.filter_reorder;
                String string = LocaleController.getString("DynamicPackOrderOff", R.string.DynamicPackOrderOff);
                String string2 = LocaleController.getString("DynamicPackOrderOffInfo", R.string.DynamicPackOrderOffInfo);
                String string3 = LocaleController.getString("Settings");
                final BaseFragment baseFragment2 = this.val$fragment;
                of.createSimpleBulletin(i5, string, string2, string3, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$18$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.AnonymousClass18.lambda$stickerSetPositionChanged$1(BaseFragment.this);
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$stickerSetPositionChanged$0(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2) {
            int indexOf = EmojiView.this.stickerSets.indexOf(tLRPC$TL_messages_stickerSet);
            int indexOf2 = EmojiView.this.stickerSets.indexOf(tLRPC$TL_messages_stickerSet2);
            if (indexOf < 0 || indexOf2 < 0) {
                return 0;
            }
            return indexOf - indexOf2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$stickerSetPositionChanged$1(BaseFragment baseFragment) {
            baseFragment.presentFragment(new StickersActivity(0, null));
        }

        private void swapListElements(List<TLRPC$TL_messages_stickerSet> list, int i, int i2) {
            list.add(i2, list.remove(i));
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v2, types: [int] */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /* JADX WARN: Type inference failed for: r4v3, types: [java.util.ArrayList] */
        private void sendReorder() {
            MediaDataController.getInstance(EmojiView.this.currentAccount).calcNewHash(0);
            TLRPC$TL_messages_reorderStickerSets tLRPC$TL_messages_reorderStickerSets = new TLRPC$TL_messages_reorderStickerSets();
            tLRPC$TL_messages_reorderStickerSets.masks = false;
            tLRPC$TL_messages_reorderStickerSets.emojis = false;
            for (?? r2 = EmojiView.this.hasChatStickers; r2 < EmojiView.this.stickerSets.size(); r2++) {
                tLRPC$TL_messages_reorderStickerSets.order.add(Long.valueOf(((TLRPC$TL_messages_stickerSet) EmojiView.this.stickerSets.get(r2)).set.id));
            }
            ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tLRPC$TL_messages_reorderStickerSets, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$18$$ExternalSyntheticLambda2
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    EmojiView.AnonymousClass18.lambda$sendReorder$2(tLObject, tLRPC$TL_error);
                }
            });
            NotificationCenter.getInstance(EmojiView.this.currentAccount).postNotificationName(NotificationCenter.stickersDidLoad, 0, Boolean.TRUE);
        }

        @Override // org.telegram.ui.Components.ScrollSlidingTabStrip
        protected void invalidateOverlays() {
            EmojiView.this.stickersTabContainer.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(int i) {
        if (this.firstTabUpdate) {
            return;
        }
        if (i == this.trendingTabNum) {
            openTrendingStickers(null);
            return;
        }
        SearchField searchField = this.stickersSearchField;
        if (searchField != null && searchField.isCategorySelected()) {
            this.stickersSearchField.search(null, false);
            this.stickersSearchField.categoriesListView.selectCategory((StickerCategoriesListView.EmojiCategory) null);
        }
        if (i == this.recentTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("recent"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
            int i2 = this.recentTabNum;
            scrollSlidingTabStrip.onPageScrolled(i2, i2 > 0 ? i2 : this.stickersTabOffset);
            return;
        }
        if (i == this.favTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("fav"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.stickersTab;
            int i3 = this.favTabNum;
            scrollSlidingTabStrip2.onPageScrolled(i3, i3 > 0 ? i3 : this.stickersTabOffset);
            return;
        }
        if (i == this.premiumTabNum) {
            this.stickersGridView.stopScroll();
            scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack("premium"), 0);
            resetTabsY(0);
            ScrollSlidingTabStrip scrollSlidingTabStrip3 = this.stickersTab;
            int i4 = this.premiumTabNum;
            scrollSlidingTabStrip3.onPageScrolled(i4, i4 > 0 ? i4 : this.stickersTabOffset);
            return;
        }
        int i5 = i - this.stickersTabOffset;
        if (i5 >= this.stickerSets.size()) {
            return;
        }
        if (i5 >= this.stickerSets.size()) {
            i5 = this.stickerSets.size() - 1;
        }
        this.firstStickersAttach = false;
        this.stickersGridView.stopScroll();
        scrollStickersToPosition(this.stickersGridAdapter.getPositionForPack(this.stickerSets.get(i5)), 0);
        resetTabsY(0);
        checkScroll(0);
        int i6 = this.favTabNum;
        if (i6 <= 0 && (i6 = this.recentTabNum) <= 0) {
            i6 = this.stickersTabOffset;
        }
        this.stickersTab.onPageScrolled(i, i6);
        this.expandStickersByDragg = false;
        updateStickerTabsPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(Integer num, Integer num2) {
        ImageViewEmoji imageViewEmoji = this.emojiTouchedView;
        if (imageViewEmoji == null || !(imageViewEmoji.getDrawable() instanceof CompoundEmoji.CompoundEmojiDrawable)) {
            return;
        }
        ((CompoundEmoji.CompoundEmojiDrawable) this.emojiTouchedView.getDrawable()).update(num.intValue(), num2.intValue());
        String str = (String) this.emojiTouchedView.getTag();
        if (num.intValue() == -1 && num2.intValue() == -1) {
            Emoji.emojiColor.remove(str);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(num.intValue() >= 0 ? CompoundEmoji.skinTones.get(num.intValue()) : "");
            sb.append("\u200d");
            sb.append(num2.intValue() >= 0 ? CompoundEmoji.skinTones.get(num2.intValue()) : "");
            Emoji.emojiColor.put(str, sb.toString());
        }
        Emoji.saveEmojiColors();
    }

    class EmojiGridView extends RecyclerListView {
        private boolean ignoreLayout;
        private int lastChildCount;
        ArrayList<DrawingInBackgroundLine> lineDrawables;
        ArrayList<DrawingInBackgroundLine> lineDrawablesTmp;
        private SparseArray<TouchDownInfo> touches;
        ArrayList<ArrayList<ImageViewEmoji>> unusedArrays;
        ArrayList<DrawingInBackgroundLine> unusedLineDrawables;
        SparseArray<ArrayList<ImageViewEmoji>> viewsGroupedByLines;

        public EmojiGridView(Context context) {
            super(context);
            this.viewsGroupedByLines = new SparseArray<>();
            this.lineDrawables = new ArrayList<>();
            this.lineDrawablesTmp = new ArrayList<>();
            this.unusedArrays = new ArrayList<>();
            this.unusedLineDrawables = new ArrayList<>();
            this.lastChildCount = -1;
            new SparseIntArray();
            new AnimatedFloat(this, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return super.onInterceptTouchEvent(motionEvent) || ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, this, 0, EmojiView.this.contentPreviewViewerDelegate, this.resourcesProvider);
        }

        private AnimatedEmojiSpan[] getAnimatedEmojiSpans() {
            AnimatedEmojiSpan[] animatedEmojiSpanArr = new AnimatedEmojiSpan[EmojiView.this.emojiGridView.getChildCount()];
            for (int i = 0; i < EmojiView.this.emojiGridView.getChildCount(); i++) {
                View childAt = EmojiView.this.emojiGridView.getChildAt(i);
                if (childAt instanceof ImageViewEmoji) {
                    animatedEmojiSpanArr[i] = ((ImageViewEmoji) childAt).getSpan();
                }
            }
            return animatedEmojiSpanArr;
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        protected void onMeasure(int i, int i2) {
            this.ignoreLayout = true;
            int size = View.MeasureSpec.getSize(i);
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount();
            EmojiView.this.emojiLayoutManager.setSpanCount(Math.max(1, size / AndroidUtilities.dp(AndroidUtilities.isTablet() ? 60.0f : 45.0f)));
            this.ignoreLayout = false;
            super.onMeasure(i, i2);
            if (spanCount != EmojiView.this.emojiLayoutManager.getSpanCount()) {
                EmojiView.this.emojiAdapter.notifyDataSetChanged();
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            if (EmojiView.this.needEmojiSearch && EmojiView.this.firstEmojiAttach) {
                this.ignoreLayout = true;
                EmojiView.this.emojiLayoutManager.scrollToPositionWithOffset(0, 0);
                EmojiView.this.firstEmojiAttach = false;
                this.ignoreLayout = false;
            }
            super.onLayout(z, i, i2, i3, i4);
            EmojiView.this.checkEmojiSearchFieldScroll(true);
            updateEmojiDrawables();
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0076  */
        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r8) {
            /*
                Method dump skipped, instructions count: 467
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiGridView.onTouchEvent(android.view.MotionEvent):boolean");
        }

        public void updateEmojiDrawables() {
            EmojiView.this.animatedEmojiDrawables = AnimatedEmojiSpan.update(2, this, getAnimatedEmojiSpans(), (LongSparseArray<AnimatedEmojiDrawable>) EmojiView.this.animatedEmojiDrawables);
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void onScrollStateChanged(int i) {
            super.onScrollStateChanged(i);
            if (i == 0) {
                if (!canScrollVertically(-1) || !canScrollVertically(1)) {
                    EmojiView.this.showBottomTab(true, true);
                }
                if (canScrollVertically(1)) {
                    return;
                }
                EmojiView.this.checkTabsY(1, AndroidUtilities.dp(36.0f));
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (this.lastChildCount != getChildCount()) {
                updateEmojiDrawables();
                this.lastChildCount = getChildCount();
            }
            for (int i = 0; i < this.viewsGroupedByLines.size(); i++) {
                ArrayList<ImageViewEmoji> valueAt = this.viewsGroupedByLines.valueAt(i);
                valueAt.clear();
                this.unusedArrays.add(valueAt);
            }
            this.viewsGroupedByLines.clear();
            boolean z = ((EmojiView.this.animateExpandStartTime > 0L ? 1 : (EmojiView.this.animateExpandStartTime == 0L ? 0 : -1)) > 0 && ((SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime) > animateExpandDuration() ? 1 : ((SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime) == animateExpandDuration() ? 0 : -1)) < 0) && EmojiView.this.animateExpandFromButton != null && EmojiView.this.animateExpandFromPosition >= 0;
            if (EmojiView.this.animatedEmojiDrawables != null && EmojiView.this.emojiGridView != null) {
                for (int i2 = 0; i2 < EmojiView.this.emojiGridView.getChildCount(); i2++) {
                    View childAt = EmojiView.this.emojiGridView.getChildAt(i2);
                    if (childAt instanceof ImageViewEmoji) {
                        int top = childAt.getTop() + ((int) childAt.getTranslationY());
                        ArrayList<ImageViewEmoji> arrayList = this.viewsGroupedByLines.get(top);
                        if (arrayList == null) {
                            if (!this.unusedArrays.isEmpty()) {
                                ArrayList<ArrayList<ImageViewEmoji>> arrayList2 = this.unusedArrays;
                                arrayList = arrayList2.remove(arrayList2.size() - 1);
                            } else {
                                arrayList = new ArrayList<>();
                            }
                            this.viewsGroupedByLines.put(top, arrayList);
                        }
                        arrayList.add((ImageViewEmoji) childAt);
                    }
                    if (z && childAt != null && getChildAdapterPosition(childAt) == EmojiView.this.animateExpandFromPosition - 1) {
                        float interpolation = CubicBezierInterpolator.EASE_OUT.getInterpolation(MathUtils.clamp((SystemClock.elapsedRealtime() - EmojiView.this.animateExpandStartTime) / 140.0f, 0.0f, 1.0f));
                        if (interpolation < 1.0f) {
                            float f = 1.0f - interpolation;
                            canvas.saveLayerAlpha(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom(), (int) (255.0f * f), 31);
                            canvas.translate(childAt.getLeft(), childAt.getTop());
                            float f2 = (f * 0.5f) + 0.5f;
                            canvas.scale(f2, f2, childAt.getWidth() / 2.0f, childAt.getHeight() / 2.0f);
                            EmojiView.this.animateExpandFromButton.draw(canvas);
                            canvas.restore();
                        }
                    }
                }
            }
            this.lineDrawablesTmp.clear();
            this.lineDrawablesTmp.addAll(this.lineDrawables);
            this.lineDrawables.clear();
            long currentTimeMillis = System.currentTimeMillis();
            int i3 = 0;
            while (true) {
                DrawingInBackgroundLine drawingInBackgroundLine = null;
                if (i3 >= this.viewsGroupedByLines.size()) {
                    break;
                }
                ArrayList<ImageViewEmoji> valueAt2 = this.viewsGroupedByLines.valueAt(i3);
                ImageViewEmoji imageViewEmoji = valueAt2.get(0);
                int i4 = imageViewEmoji.position;
                int i5 = 0;
                while (true) {
                    if (i5 >= this.lineDrawablesTmp.size()) {
                        break;
                    }
                    if (this.lineDrawablesTmp.get(i5).position == i4) {
                        drawingInBackgroundLine = this.lineDrawablesTmp.get(i5);
                        this.lineDrawablesTmp.remove(i5);
                        break;
                    }
                    i5++;
                }
                if (drawingInBackgroundLine == null) {
                    if (!this.unusedLineDrawables.isEmpty()) {
                        ArrayList<DrawingInBackgroundLine> arrayList3 = this.unusedLineDrawables;
                        drawingInBackgroundLine = arrayList3.remove(arrayList3.size() - 1);
                    } else {
                        drawingInBackgroundLine = new DrawingInBackgroundLine();
                    }
                    drawingInBackgroundLine.position = i4;
                    drawingInBackgroundLine.onAttachToWindow();
                }
                this.lineDrawables.add(drawingInBackgroundLine);
                drawingInBackgroundLine.imageViewEmojis = valueAt2;
                canvas.save();
                canvas.translate(imageViewEmoji.getLeft(), imageViewEmoji.getY() + imageViewEmoji.getPaddingTop());
                drawingInBackgroundLine.startOffset = imageViewEmoji.getLeft();
                int measuredWidth = getMeasuredWidth() - (imageViewEmoji.getLeft() * 2);
                int measuredHeight = imageViewEmoji.getMeasuredHeight() - imageViewEmoji.getPaddingBottom();
                if (measuredWidth > 0 && measuredHeight > 0) {
                    drawingInBackgroundLine.draw(canvas, currentTimeMillis, measuredWidth, measuredHeight, 1.0f);
                }
                canvas.restore();
                i3++;
            }
            for (int i6 = 0; i6 < this.lineDrawablesTmp.size(); i6++) {
                if (this.unusedLineDrawables.size() < 3) {
                    this.unusedLineDrawables.add(this.lineDrawablesTmp.get(i6));
                    this.lineDrawablesTmp.get(i6).imageViewEmojis = null;
                    this.lineDrawablesTmp.get(i6).reset();
                } else {
                    this.lineDrawablesTmp.get(i6).onDetachFromWindow();
                }
            }
            this.lineDrawablesTmp.clear();
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            updateEmojiDrawables();
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            AnimatedEmojiSpan.release(this, (LongSparseArray<AnimatedEmojiDrawable>) EmojiView.this.animatedEmojiDrawables);
            for (int i = 0; i < this.lineDrawables.size(); i++) {
                this.lineDrawables.get(i).onDetachFromWindow();
            }
            for (int i2 = 0; i2 < this.unusedLineDrawables.size(); i2++) {
                this.unusedLineDrawables.get(i2).onDetachFromWindow();
            }
            this.unusedLineDrawables.addAll(this.lineDrawables);
            this.lineDrawables.clear();
        }

        class TouchDownInfo {
            long time;
            View view;
            float x;
            float y;

            TouchDownInfo(EmojiGridView emojiGridView) {
            }
        }

        public void clearTouchesFor(View view) {
            if (this.touches != null) {
                int i = 0;
                while (i < this.touches.size()) {
                    TouchDownInfo valueAt = this.touches.valueAt(i);
                    if (valueAt.view == view) {
                        this.touches.removeAt(i);
                        i--;
                        View view2 = valueAt.view;
                        if (view2 != null && Build.VERSION.SDK_INT >= 21 && (view2.getBackground() instanceof RippleDrawable)) {
                            valueAt.view.getBackground().setState(new int[0]);
                        }
                        View view3 = valueAt.view;
                        if (view3 != null) {
                            view3.setPressed(false);
                        }
                    }
                    i++;
                }
            }
        }

        public void clearAllTouches() {
            if (this.touches != null) {
                while (this.touches.size() > 0) {
                    TouchDownInfo valueAt = this.touches.valueAt(0);
                    this.touches.removeAt(0);
                    if (valueAt != null) {
                        View view = valueAt.view;
                        if (view != null && Build.VERSION.SDK_INT >= 21 && (view.getBackground() instanceof RippleDrawable)) {
                            valueAt.view.getBackground().setState(new int[0]);
                        }
                        View view2 = valueAt.view;
                        if (view2 != null) {
                            view2.setPressed(false);
                        }
                    }
                }
            }
        }

        public long animateExpandDuration() {
            return animateExpandAppearDuration() + animateExpandCrossfadeDuration() + 150;
        }

        public long animateExpandAppearDuration() {
            return Math.max(600L, Math.min(55, EmojiView.this.animateExpandToPosition - EmojiView.this.animateExpandFromPosition) * 40);
        }

        public long animateExpandCrossfadeDuration() {
            return Math.max(400L, Math.min(45, EmojiView.this.animateExpandToPosition - EmojiView.this.animateExpandFromPosition) * 35);
        }

        class DrawingInBackgroundLine extends DrawingInBackgroundThreadDrawable {
            ArrayList<ImageViewEmoji> imageViewEmojis;
            public int position;
            public int startOffset;
            ArrayList<ImageViewEmoji> drawInBackgroundViews = new ArrayList<>();
            private OvershootInterpolator appearScaleInterpolator = new OvershootInterpolator(3.0f);

            DrawingInBackgroundLine() {
            }

            /* JADX WARN: Code restructure failed: missing block: B:32:0x0086, code lost:
            
                prepareDraw(java.lang.System.currentTimeMillis());
                drawInUiThread(r9, r14);
                reset();
             */
            /* JADX WARN: Code restructure failed: missing block: B:33:?, code lost:
            
                return;
             */
            @Override // org.telegram.ui.Components.DrawingInBackgroundThreadDrawable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void draw(android.graphics.Canvas r9, long r10, int r12, int r13, float r14) {
                /*
                    r8 = this;
                    java.util.ArrayList<org.telegram.ui.Components.EmojiView$ImageViewEmoji> r0 = r8.imageViewEmojis
                    if (r0 != 0) goto L5
                    return
                L5:
                    int r0 = r0.size()
                    r1 = 4
                    r2 = 0
                    r3 = 1
                    if (r0 <= r1) goto L1f
                    int r0 = org.telegram.messenger.SharedConfig.getDevicePerformanceClass()
                    if (r0 == 0) goto L1f
                    r0 = 16388(0x4004, float:2.2964E-41)
                    boolean r0 = org.telegram.messenger.LiteMode.isEnabled(r0)
                    if (r0 != 0) goto L1d
                    goto L1f
                L1d:
                    r0 = 0
                    goto L20
                L1f:
                    r0 = 1
                L20:
                    if (r0 != 0) goto L83
                    org.telegram.ui.Components.EmojiView$EmojiGridView r1 = org.telegram.ui.Components.EmojiView.EmojiGridView.this
                    org.telegram.ui.Components.EmojiView r1 = org.telegram.ui.Components.EmojiView.this
                    long r4 = org.telegram.ui.Components.EmojiView.access$12100(r1)
                    r6 = 0
                    int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    if (r1 <= 0) goto L49
                    long r4 = android.os.SystemClock.elapsedRealtime()
                    org.telegram.ui.Components.EmojiView$EmojiGridView r1 = org.telegram.ui.Components.EmojiView.EmojiGridView.this
                    org.telegram.ui.Components.EmojiView r1 = org.telegram.ui.Components.EmojiView.this
                    long r6 = org.telegram.ui.Components.EmojiView.access$12100(r1)
                    long r4 = r4 - r6
                    org.telegram.ui.Components.EmojiView$EmojiGridView r1 = org.telegram.ui.Components.EmojiView.EmojiGridView.this
                    long r6 = r1.animateExpandDuration()
                    int r1 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                    if (r1 >= 0) goto L49
                    r1 = 1
                    goto L4a
                L49:
                    r1 = 0
                L4a:
                    java.util.ArrayList<org.telegram.ui.Components.EmojiView$ImageViewEmoji> r4 = r8.imageViewEmojis
                    int r4 = r4.size()
                    if (r2 >= r4) goto L83
                    java.util.ArrayList<org.telegram.ui.Components.EmojiView$ImageViewEmoji> r4 = r8.imageViewEmojis
                    java.lang.Object r4 = r4.get(r2)
                    org.telegram.ui.Components.EmojiView$ImageViewEmoji r4 = (org.telegram.ui.Components.EmojiView.ImageViewEmoji) r4
                    float r5 = r4.pressedProgress
                    r6 = 0
                    int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                    if (r5 != 0) goto L84
                    android.animation.ValueAnimator r5 = r4.backAnimator
                    if (r5 != 0) goto L84
                    int r5 = r4.position
                    org.telegram.ui.Components.EmojiView$EmojiGridView r6 = org.telegram.ui.Components.EmojiView.EmojiGridView.this
                    org.telegram.ui.Components.EmojiView r6 = org.telegram.ui.Components.EmojiView.this
                    int r6 = org.telegram.ui.Components.EmojiView.access$12300(r6)
                    if (r5 <= r6) goto L80
                    int r4 = r4.position
                    org.telegram.ui.Components.EmojiView$EmojiGridView r5 = org.telegram.ui.Components.EmojiView.EmojiGridView.this
                    org.telegram.ui.Components.EmojiView r5 = org.telegram.ui.Components.EmojiView.this
                    int r5 = org.telegram.ui.Components.EmojiView.access$12400(r5)
                    if (r4 >= r5) goto L80
                    if (r1 == 0) goto L80
                    goto L84
                L80:
                    int r2 = r2 + 1
                    goto L4a
                L83:
                    r3 = r0
                L84:
                    if (r3 == 0) goto L94
                    long r10 = java.lang.System.currentTimeMillis()
                    r8.prepareDraw(r10)
                    r8.drawInUiThread(r9, r14)
                    r8.reset()
                    goto L97
                L94:
                    super.draw(r9, r10, r12, r13, r14)
                L97:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiGridView.DrawingInBackgroundLine.draw(android.graphics.Canvas, long, int, int, float):void");
            }

            @Override // org.telegram.ui.Components.DrawingInBackgroundThreadDrawable
            public void prepareDraw(long j) {
                AnimatedEmojiDrawable animatedEmojiDrawable;
                this.drawInBackgroundViews.clear();
                for (int i = 0; i < this.imageViewEmojis.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.imageViewEmojis.get(i);
                    if (imageViewEmoji.getSpan() != null && (animatedEmojiDrawable = (AnimatedEmojiDrawable) EmojiView.this.animatedEmojiDrawables.get(imageViewEmoji.span.getDocumentId())) != null && animatedEmojiDrawable.getImageReceiver() != null) {
                        animatedEmojiDrawable.update(j);
                        ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolderArr = imageViewEmoji.backgroundThreadDrawHolder;
                        int i2 = this.threadIndex;
                        ImageReceiver imageReceiver = animatedEmojiDrawable.getImageReceiver();
                        ImageReceiver.BackgroundThreadDrawHolder[] backgroundThreadDrawHolderArr2 = imageViewEmoji.backgroundThreadDrawHolder;
                        int i3 = this.threadIndex;
                        backgroundThreadDrawHolderArr[i2] = imageReceiver.setDrawInBackgroundThread(backgroundThreadDrawHolderArr2[i3], i3);
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].time = j;
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].overrideAlpha = 1.0f;
                        animatedEmojiDrawable.setAlpha(255);
                        int height = (int) (imageViewEmoji.getHeight() * 0.03f);
                        android.graphics.Rect rect = AndroidUtilities.rectTmp2;
                        rect.set((imageViewEmoji.getLeft() + imageViewEmoji.getPaddingLeft()) - this.startOffset, height, (imageViewEmoji.getRight() - imageViewEmoji.getPaddingRight()) - this.startOffset, ((imageViewEmoji.getMeasuredHeight() + height) - imageViewEmoji.getPaddingTop()) - imageViewEmoji.getPaddingBottom());
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].setBounds(rect);
                        imageViewEmoji.drawable = animatedEmojiDrawable;
                        animatedEmojiDrawable.setColorFilter(EmojiView.this.animatedEmojiTextColorFilter);
                        animatedEmojiDrawable.getImageReceiver();
                        this.drawInBackgroundViews.add(imageViewEmoji);
                    }
                }
            }

            @Override // org.telegram.ui.Components.DrawingInBackgroundThreadDrawable
            public void drawInBackground(Canvas canvas) {
                for (int i = 0; i < this.drawInBackgroundViews.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.drawInBackgroundViews.get(i);
                    AnimatedEmojiDrawable animatedEmojiDrawable = imageViewEmoji.drawable;
                    if (animatedEmojiDrawable != null) {
                        animatedEmojiDrawable.draw(canvas, imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex], false);
                    }
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:31:0x017d  */
            /* JADX WARN: Removed duplicated region for block: B:34:0x0194  */
            @Override // org.telegram.ui.Components.DrawingInBackgroundThreadDrawable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            protected void drawInUiThread(android.graphics.Canvas r21, float r22) {
                /*
                    Method dump skipped, instructions count: 416
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiGridView.DrawingInBackgroundLine.drawInUiThread(android.graphics.Canvas, float):void");
            }

            @Override // org.telegram.ui.Components.DrawingInBackgroundThreadDrawable
            public void onFrameReady() {
                super.onFrameReady();
                for (int i = 0; i < this.drawInBackgroundViews.size(); i++) {
                    ImageViewEmoji imageViewEmoji = this.drawInBackgroundViews.get(i);
                    if (imageViewEmoji.backgroundThreadDrawHolder != null) {
                        imageViewEmoji.backgroundThreadDrawHolder[this.threadIndex].release();
                    }
                }
                EmojiView.this.emojiGridView.invalidate();
            }
        }

        @Override // org.telegram.ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            View view;
            View view2;
            boolean z = motionEvent.getActionMasked() == 5 || motionEvent.getActionMasked() == 0;
            boolean z2 = motionEvent.getActionMasked() == 6 || motionEvent.getActionMasked() == 1;
            boolean z3 = motionEvent.getActionMasked() == 3;
            if (z || z2 || z3) {
                int actionIndex = motionEvent.getActionIndex();
                int pointerId = motionEvent.getPointerId(actionIndex);
                if (this.touches == null) {
                    this.touches = new SparseArray<>();
                }
                float x = motionEvent.getX(actionIndex);
                float y = motionEvent.getY(actionIndex);
                View findChildViewUnder = findChildViewUnder(x, y);
                if (!z) {
                    TouchDownInfo touchDownInfo = this.touches.get(pointerId);
                    this.touches.remove(pointerId);
                    if (findChildViewUnder != null && touchDownInfo != null && Math.sqrt(Math.pow(x - touchDownInfo.x, 2.0d) + Math.pow(y - touchDownInfo.y, 2.0d)) < AndroidUtilities.touchSlop * 3.0f && !z3 && (!EmojiView.this.colorPickerView.isShowing() || SystemClock.elapsedRealtime() - touchDownInfo.time < ViewConfiguration.getLongPressTimeout())) {
                        View view3 = touchDownInfo.view;
                        int childAdapterPosition = getChildAdapterPosition(view3);
                        try {
                            if (view3 instanceof ImageViewEmoji) {
                                EmojiView.this.sendEmoji((ImageViewEmoji) view3, null);
                                performHapticFeedback(3, 1);
                            } else if (view3 instanceof EmojiPackExpand) {
                                EmojiView.this.emojiAdapter.expand(childAdapterPosition, (EmojiPackExpand) view3);
                                performHapticFeedback(3, 1);
                            } else if (view3 != null) {
                                view3.callOnClick();
                            }
                        } catch (Exception unused) {
                        }
                    }
                    if (touchDownInfo != null && (view2 = touchDownInfo.view) != null && Build.VERSION.SDK_INT >= 21 && (view2.getBackground() instanceof RippleDrawable)) {
                        touchDownInfo.view.getBackground().setState(new int[0]);
                    }
                    if (touchDownInfo != null && (view = touchDownInfo.view) != null) {
                        view.setPressed(false);
                    }
                } else if (findChildViewUnder != null) {
                    TouchDownInfo touchDownInfo2 = new TouchDownInfo(this);
                    touchDownInfo2.x = x;
                    touchDownInfo2.y = y;
                    touchDownInfo2.time = SystemClock.elapsedRealtime();
                    touchDownInfo2.view = findChildViewUnder;
                    if (Build.VERSION.SDK_INT >= 21 && (findChildViewUnder.getBackground() instanceof RippleDrawable)) {
                        findChildViewUnder.getBackground().setState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled});
                    }
                    touchDownInfo2.view.setPressed(true);
                    this.touches.put(pointerId, touchDownInfo2);
                    stopScroll();
                }
            }
            return super.dispatchTouchEvent(motionEvent) || (!z3 && this.touches.size() > 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createStickersChooseActionTracker() {
        ChooseStickerActionTracker chooseStickerActionTracker = new ChooseStickerActionTracker(this.currentAccount, this.delegate.getDialogId(), this.delegate.getThreadId()) { // from class: org.telegram.ui.Components.EmojiView.27
            @Override // org.telegram.ui.Components.EmojiView.ChooseStickerActionTracker
            public boolean isShown() {
                return EmojiView.this.delegate != null && EmojiView.this.getVisibility() == 0 && EmojiView.this.stickersContainerAttached;
            }
        };
        this.chooseStickerActionTracker = chooseStickerActionTracker;
        chooseStickerActionTracker.checkVisibility();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEmojiTabsPosition() {
        updateEmojiTabsPosition(this.emojiLayoutManager.findFirstCompletelyVisibleItemPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEmojiTabsPosition(int i) {
        if (this.emojiSmoothScrolling) {
            return;
        }
        int i2 = -1;
        if (i != -1) {
            int i3 = 0;
            int size = getRecentEmoji().size() + (this.needEmojiSearch ? 1 : 0) + (this.emojiAdapter.trendingHeaderRow >= 0 ? 3 : 0);
            if (i >= size) {
                int i4 = 0;
                while (true) {
                    String[][] strArr = EmojiData.dataColored;
                    if (i4 >= strArr.length) {
                        break;
                    }
                    size += strArr[i4].length + 1;
                    if (i < size) {
                        i2 = i4 + 1;
                        break;
                    }
                    i4++;
                }
                if (i2 < 0) {
                    ArrayList<EmojiPack> emojipacks = getEmojipacks();
                    int size2 = this.emojiAdapter.packStartPosition.size() - 1;
                    while (true) {
                        if (size2 < 0) {
                            break;
                        }
                        if (((Integer) this.emojiAdapter.packStartPosition.get(size2)).intValue() <= i) {
                            EmojiPack emojiPack = this.emojipacksProcessed.get(size2);
                            while (i3 < emojipacks.size()) {
                                long j = emojipacks.get(i3).set.id;
                                long j2 = emojiPack.set.id;
                                if (j == j2 && (!emojiPack.featured || (!emojiPack.installed && !this.installedEmojiSets.contains(Long.valueOf(j2))))) {
                                    i3 = EmojiData.dataColored.length + 1 + i3;
                                    break;
                                }
                                i3++;
                            }
                        } else {
                            size2--;
                        }
                    }
                }
                i3 = i2;
            }
            if (i3 >= 0) {
                this.emojiTabs.select(i3);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkGridVisibility(int i, float f) {
        if (this.stickersContainer == null || this.gifContainer == null) {
            return;
        }
        if (i == 0) {
            this.emojiGridView.setVisibility(0);
            this.gifGridView.setVisibility(f == 0.0f ? 8 : 0);
            this.gifTabs.setVisibility(f == 0.0f ? 8 : 0);
            this.stickersGridView.setVisibility(8);
            this.stickersTabContainer.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.emojiGridView.setVisibility(8);
            this.gifGridView.setVisibility(0);
            this.gifTabs.setVisibility(0);
            this.stickersGridView.setVisibility(f == 0.0f ? 8 : 0);
            this.stickersTabContainer.setVisibility(f == 0.0f ? 8 : 0);
            return;
        }
        if (i == 2) {
            this.emojiGridView.setVisibility(8);
            this.gifGridView.setVisibility(8);
            this.gifTabs.setVisibility(8);
            this.stickersGridView.setVisibility(0);
            this.stickersTabContainer.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openPremiumAnimatedEmojiFeature() {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null) {
            emojiViewDelegate.onAnimatedEmojiUnlockClick();
        }
    }

    private class EmojiPackButton extends FrameLayout {
        AnimatedTextView addButtonTextView;
        FrameLayout addButtonView;
        PremiumButtonView premiumButtonView;

        public EmojiPackButton(EmojiView emojiView, Context context) {
            super(context);
            AnimatedTextView animatedTextView = new AnimatedTextView(getContext());
            this.addButtonTextView = animatedTextView;
            animatedTextView.setAnimationProperties(0.3f, 0L, 250L, CubicBezierInterpolator.EASE_OUT_QUINT);
            this.addButtonTextView.setTextSize(AndroidUtilities.dp(14.0f));
            this.addButtonTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.addButtonTextView.setTextColor(emojiView.getThemedColor(Theme.key_featuredStickers_buttonText));
            this.addButtonTextView.setGravity(17);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.addButtonView = frameLayout;
            frameLayout.setBackground(Theme.AdaptiveRipple.filledRect(emojiView.getThemedColor(Theme.key_featuredStickers_addButton), 8.0f));
            this.addButtonView.addView(this.addButtonTextView, LayoutHelper.createFrame(-1, -2, 17));
            addView(this.addButtonView, LayoutHelper.createFrame(-1, -1.0f));
            PremiumButtonView premiumButtonView = new PremiumButtonView(getContext(), false);
            this.premiumButtonView = premiumButtonView;
            premiumButtonView.setIcon(R.raw.unlock_icon);
            addView(this.premiumButtonView, LayoutHelper.createFrame(-1, -1.0f));
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            setPadding(AndroidUtilities.dp(6.0f), AndroidUtilities.dp(11.0f), AndroidUtilities.dp(6.0f), AndroidUtilities.dp(11.0f));
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(44.0f) + getPaddingTop() + getPaddingBottom(), 1073741824));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EmojiPackHeader extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
        TextView addButtonView;
        FrameLayout buttonsView;
        private int currentButtonState;
        boolean divider;
        private Paint dividerPaint;
        SimpleTextView headerView;
        RLottieImageView lockView;
        private EmojiPack pack;
        PremiumButtonView premiumButtonView;
        TextView removeButtonView;
        private AnimatorSet stateAnimator;
        private TLRPC$InputStickerSet toInstall;
        private TLRPC$InputStickerSet toUninstall;

        public EmojiPackHeader(Context context) {
            super(context);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.lockView = rLottieImageView;
            int i = R.raw.unlock_icon;
            rLottieImageView.setAnimation(i, 24, 24);
            RLottieImageView rLottieImageView2 = this.lockView;
            int i2 = Theme.key_chat_emojiPanelStickerSetName;
            rLottieImageView2.setColorFilter(EmojiView.this.getThemedColor(i2));
            addView(this.lockView, LayoutHelper.createFrameRelatively(20.0f, 20.0f, 8388611, 10.0f, 15.0f, 0.0f, 0.0f));
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.headerView = simpleTextView;
            simpleTextView.setTextSize(15);
            this.headerView.setTextColor(EmojiView.this.getThemedColor(i2));
            this.headerView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.headerView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiView.EmojiPackHeader.this.lambda$new$0(view);
                }
            });
            this.headerView.setEllipsizeByGradient(true);
            addView(this.headerView, LayoutHelper.createFrameRelatively(-2.0f, -1.0f, 8388611, 15.0f, 15.0f, 0.0f, 0.0f));
            FrameLayout frameLayout = new FrameLayout(context);
            this.buttonsView = frameLayout;
            frameLayout.setPadding(AndroidUtilities.dp(11.0f), AndroidUtilities.dp(11.0f), AndroidUtilities.dp(11.0f), 0);
            this.buttonsView.setClipToPadding(false);
            this.buttonsView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiView.EmojiPackHeader.this.lambda$new$1(view);
                }
            });
            addView(this.buttonsView, LayoutHelper.createFrameRelatively(-2.0f, -1.0f, 8388725));
            TextView textView = new TextView(context);
            this.addButtonView = textView;
            textView.setTextSize(1, 14.0f);
            this.addButtonView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.addButtonView.setText(LocaleController.getString("Add", R.string.Add));
            this.addButtonView.setTextColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_buttonText));
            TextView textView2 = this.addButtonView;
            int i3 = Theme.key_featuredStickers_addButton;
            textView2.setBackground(Theme.AdaptiveRipple.createRect(EmojiView.this.getThemedColor(i3), EmojiView.this.getThemedColor(Theme.key_featuredStickers_addButtonPressed), 16.0f));
            this.addButtonView.setPadding(AndroidUtilities.dp(14.0f), 0, AndroidUtilities.dp(14.0f), 0);
            this.addButtonView.setGravity(17);
            this.addButtonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiView.EmojiPackHeader.this.lambda$new$2(view);
                }
            });
            this.buttonsView.addView(this.addButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            TextView textView3 = new TextView(context);
            this.removeButtonView = textView3;
            textView3.setTextSize(1, 14.0f);
            this.removeButtonView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.removeButtonView.setText(LocaleController.getString("StickersRemove", R.string.StickersRemove));
            this.removeButtonView.setTextColor(EmojiView.this.getThemedColor(Theme.key_featuredStickers_removeButtonText));
            this.removeButtonView.setBackground(Theme.AdaptiveRipple.createRect(0, EmojiView.this.getThemedColor(i3) & 452984831, 16.0f));
            this.removeButtonView.setPadding(AndroidUtilities.dp(12.0f), 0, AndroidUtilities.dp(12.0f), 0);
            this.removeButtonView.setGravity(17);
            this.removeButtonView.setTranslationX(AndroidUtilities.dp(4.0f));
            this.removeButtonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiView.EmojiPackHeader.this.lambda$new$3(view);
                }
            });
            this.buttonsView.addView(this.removeButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            PremiumButtonView premiumButtonView = new PremiumButtonView(context, AndroidUtilities.dp(16.0f), false);
            this.premiumButtonView = premiumButtonView;
            premiumButtonView.setIcon(i);
            this.premiumButtonView.setButton(LocaleController.getString("Unlock", R.string.Unlock), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiView.EmojiPackHeader.this.lambda$new$4(view);
                }
            });
            try {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.premiumButtonView.getIconView().getLayoutParams();
                marginLayoutParams.leftMargin = AndroidUtilities.dp(1.0f);
                marginLayoutParams.topMargin = AndroidUtilities.dp(1.0f);
                int dp = AndroidUtilities.dp(20.0f);
                marginLayoutParams.height = dp;
                marginLayoutParams.width = dp;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.premiumButtonView.getTextView().getLayoutParams();
                marginLayoutParams2.leftMargin = AndroidUtilities.dp(5.0f);
                marginLayoutParams2.topMargin = AndroidUtilities.dp(-0.5f);
                this.premiumButtonView.getChildAt(0).setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), 0);
            } catch (Exception unused) {
            }
            this.buttonsView.addView(this.premiumButtonView, LayoutHelper.createFrameRelatively(-2.0f, 26.0f, 8388661));
            setWillNotDraw(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            TLRPC$StickerSet tLRPC$StickerSet;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (tLRPC$StickerSet = emojiPack.set) == null) {
                return;
            }
            EmojiView.this.openEmojiPackAlert(tLRPC$StickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            TextView textView = this.addButtonView;
            if (textView != null && textView.getVisibility() == 0 && this.addButtonView.isEnabled()) {
                this.addButtonView.performClick();
                return;
            }
            TextView textView2 = this.removeButtonView;
            if (textView2 != null && textView2.getVisibility() == 0 && this.removeButtonView.isEnabled()) {
                this.removeButtonView.performClick();
                return;
            }
            PremiumButtonView premiumButtonView = this.premiumButtonView;
            if (premiumButtonView != null && premiumButtonView.getVisibility() == 0 && this.premiumButtonView.isEnabled()) {
                this.premiumButtonView.performClick();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(View view) {
            TLRPC$StickerSet tLRPC$StickerSet;
            Integer num;
            View view2;
            View childAt;
            int childAdapterPosition;
            int i;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (tLRPC$StickerSet = emojiPack.set) == null) {
                return;
            }
            emojiPack.installed = true;
            if (!EmojiView.this.installedEmojiSets.contains(Long.valueOf(tLRPC$StickerSet.id))) {
                EmojiView.this.installedEmojiSets.add(Long.valueOf(this.pack.set.id));
            }
            updateState(true);
            int i2 = 0;
            while (true) {
                num = null;
                if (i2 >= EmojiView.this.emojiGridView.getChildCount()) {
                    view2 = null;
                    break;
                }
                if ((EmojiView.this.emojiGridView.getChildAt(i2) instanceof EmojiPackExpand) && (childAdapterPosition = EmojiView.this.emojiGridView.getChildAdapterPosition((childAt = EmojiView.this.emojiGridView.getChildAt(i2)))) >= 0 && (i = EmojiView.this.emojiAdapter.positionToExpand.get(childAdapterPosition)) >= 0 && i < EmojiView.this.emojipacksProcessed.size() && EmojiView.this.emojipacksProcessed.get(i) != null && this.pack != null && ((EmojiPack) EmojiView.this.emojipacksProcessed.get(i)).set.id == this.pack.set.id) {
                    num = Integer.valueOf(childAdapterPosition);
                    view2 = childAt;
                    break;
                }
                i2++;
            }
            if (num != null) {
                EmojiView.this.emojiAdapter.expand(num.intValue(), view2);
            }
            if (this.toInstall != null) {
                return;
            }
            TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
            TLRPC$StickerSet tLRPC$StickerSet2 = this.pack.set;
            tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSet2.id;
            tLRPC$TL_inputStickerSetID.access_hash = tLRPC$StickerSet2.access_hash;
            TLRPC$TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(tLRPC$TL_inputStickerSetID, true);
            if (stickerSet == null || stickerSet.set == null) {
                NotificationCenter.getInstance(EmojiView.this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                this.toInstall = tLRPC$TL_inputStickerSetID;
                mediaDataController.getStickerSet(tLRPC$TL_inputStickerSetID, false);
                return;
            }
            install(stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            TLRPC$StickerSet tLRPC$StickerSet;
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null || (tLRPC$StickerSet = emojiPack.set) == null) {
                return;
            }
            emojiPack.installed = false;
            EmojiView.this.installedEmojiSets.remove(Long.valueOf(tLRPC$StickerSet.id));
            updateState(true);
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
            }
            EmojiView.this.updateEmojiTabsPosition();
            if (this.toUninstall != null) {
                return;
            }
            TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
            TLRPC$StickerSet tLRPC$StickerSet2 = this.pack.set;
            tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSet2.id;
            tLRPC$TL_inputStickerSetID.access_hash = tLRPC$StickerSet2.access_hash;
            TLRPC$TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(tLRPC$TL_inputStickerSetID, true);
            if (stickerSet == null || stickerSet.set == null) {
                NotificationCenter.getInstance(EmojiView.this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                this.toUninstall = tLRPC$TL_inputStickerSetID;
                mediaDataController.getStickerSet(tLRPC$TL_inputStickerSetID, false);
                return;
            }
            uninstall(stickerSet);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            ((ViewGroup.MarginLayoutParams) this.headerView.getLayoutParams()).topMargin = AndroidUtilities.dp(this.currentButtonState == 0 ? 10.0f : 15.0f);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.currentButtonState == 0 ? 32.0f : 42.0f), 1073741824));
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.headerView.setRightPadding(this.buttonsView.getWidth() + AndroidUtilities.dp(11.0f));
        }

        public void setStickerSet(EmojiPack emojiPack, boolean z) {
            if (emojiPack == null) {
                return;
            }
            this.pack = emojiPack;
            this.divider = z;
            this.headerView.setText(emojiPack.set.title);
            if (emojiPack.installed && !emojiPack.set.official) {
                this.premiumButtonView.setButton(LocaleController.getString("Restore", R.string.Restore), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EmojiView.EmojiPackHeader.this.lambda$setStickerSet$5(view);
                    }
                });
            } else {
                this.premiumButtonView.setButton(LocaleController.getString("Unlock", R.string.Unlock), new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EmojiView.EmojiPackHeader.this.lambda$setStickerSet$6(view);
                    }
                });
            }
            updateState(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setStickerSet$5(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setStickerSet$6(View view) {
            EmojiView.this.openPremiumAnimatedEmojiFeature();
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            TLRPC$TL_messages_stickerSet stickerSetById;
            TLRPC$TL_messages_stickerSet stickerSetById2;
            if (i == NotificationCenter.groupStickersDidLoad) {
                if (this.toInstall != null && (stickerSetById2 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(this.toInstall.id)) != null && stickerSetById2.set != null) {
                    install(stickerSetById2);
                    this.toInstall = null;
                }
                if (this.toUninstall == null || (stickerSetById = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(this.toUninstall.id)) == null || stickerSetById.set == null) {
                    return;
                }
                uninstall(stickerSetById);
                this.toUninstall = null;
            }
        }

        private BaseFragment getFragment() {
            if (EmojiView.this.fragment != null) {
                return EmojiView.this.fragment;
            }
            return new BaseFragment() { // from class: org.telegram.ui.Components.EmojiView.EmojiPackHeader.1
                @Override // org.telegram.ui.ActionBar.BaseFragment
                public int getCurrentAccount() {
                    return EmojiView.this.currentAccount;
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public View getFragmentView() {
                    return EmojiView.this.bulletinContainer;
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public FrameLayout getLayoutContainer() {
                    return EmojiView.this.bulletinContainer;
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public Theme.ResourcesProvider getResourceProvider() {
                    return EmojiView.this.resourcesProvider;
                }
            };
        }

        private void install(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            EmojiPacksAlert.installSet(getFragment(), tLRPC$TL_messages_stickerSet, true, null, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.EmojiPackHeader.this.lambda$install$7();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$install$7() {
            this.pack.installed = true;
            updateState(true);
        }

        private void uninstall(final TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            EmojiPacksAlert.uninstallSet(getFragment(), tLRPC$TL_messages_stickerSet, true, new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiPackHeader$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.EmojiPackHeader.this.lambda$uninstall$8(tLRPC$TL_messages_stickerSet);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$uninstall$8(TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            this.pack.installed = true;
            if (!EmojiView.this.installedEmojiSets.contains(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id))) {
                EmojiView.this.installedEmojiSets.add(Long.valueOf(tLRPC$TL_messages_stickerSet.set.id));
            }
            updateState(true);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            NotificationCenter.getInstance(EmojiView.this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (this.divider) {
                if (this.dividerPaint == null) {
                    Paint paint = new Paint(1);
                    this.dividerPaint = paint;
                    paint.setStrokeWidth(1.0f);
                    this.dividerPaint.setColor(EmojiView.this.getThemedColor(Theme.key_divider));
                }
                canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), 1.0f, this.dividerPaint);
            }
            super.onDraw(canvas);
        }

        public void updateState(boolean z) {
            EmojiPack emojiPack = this.pack;
            if (emojiPack == null) {
                return;
            }
            int i = 1;
            boolean z2 = emojiPack.installed || EmojiView.this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.id));
            if (this.pack.free || UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium) {
                i = this.pack.featured ? z2 ? 3 : 2 : 0;
            }
            updateState(i, z);
        }

        public void updateState(final int i, boolean z) {
            if ((i == 0) != (this.currentButtonState == 0)) {
                requestLayout();
            }
            this.currentButtonState = i;
            AnimatorSet animatorSet = this.stateAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.stateAnimator = null;
            }
            this.premiumButtonView.setEnabled(i == 1);
            this.addButtonView.setEnabled(i == 2);
            this.removeButtonView.setEnabled(i == 3);
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.stateAnimator = animatorSet2;
                Animator[] animatorArr = new Animator[12];
                RLottieImageView rLottieImageView = this.lockView;
                Property property = FrameLayout.TRANSLATION_X;
                float[] fArr = new float[1];
                fArr[0] = i == 1 ? 0.0f : -AndroidUtilities.dp(16.0f);
                animatorArr[0] = ObjectAnimator.ofFloat(rLottieImageView, (Property<RLottieImageView, Float>) property, fArr);
                RLottieImageView rLottieImageView2 = this.lockView;
                Property property2 = FrameLayout.ALPHA;
                float[] fArr2 = new float[1];
                fArr2[0] = i == 1 ? 1.0f : 0.0f;
                animatorArr[1] = ObjectAnimator.ofFloat(rLottieImageView2, (Property<RLottieImageView, Float>) property2, fArr2);
                SimpleTextView simpleTextView = this.headerView;
                Property property3 = FrameLayout.TRANSLATION_X;
                float[] fArr3 = new float[1];
                fArr3[0] = i == 1 ? AndroidUtilities.dp(16.0f) : 0.0f;
                animatorArr[2] = ObjectAnimator.ofFloat(simpleTextView, (Property<SimpleTextView, Float>) property3, fArr3);
                PremiumButtonView premiumButtonView = this.premiumButtonView;
                Property property4 = FrameLayout.ALPHA;
                float[] fArr4 = new float[1];
                fArr4[0] = i == 1 ? 1.0f : 0.0f;
                animatorArr[3] = ObjectAnimator.ofFloat(premiumButtonView, (Property<PremiumButtonView, Float>) property4, fArr4);
                PremiumButtonView premiumButtonView2 = this.premiumButtonView;
                Property property5 = FrameLayout.SCALE_X;
                float[] fArr5 = new float[1];
                fArr5[0] = i == 1 ? 1.0f : 0.6f;
                animatorArr[4] = ObjectAnimator.ofFloat(premiumButtonView2, (Property<PremiumButtonView, Float>) property5, fArr5);
                PremiumButtonView premiumButtonView3 = this.premiumButtonView;
                Property property6 = FrameLayout.SCALE_Y;
                float[] fArr6 = new float[1];
                fArr6[0] = i == 1 ? 1.0f : 0.6f;
                animatorArr[5] = ObjectAnimator.ofFloat(premiumButtonView3, (Property<PremiumButtonView, Float>) property6, fArr6);
                TextView textView = this.addButtonView;
                Property property7 = FrameLayout.ALPHA;
                float[] fArr7 = new float[1];
                fArr7[0] = i == 2 ? 1.0f : 0.0f;
                animatorArr[6] = ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) property7, fArr7);
                TextView textView2 = this.addButtonView;
                Property property8 = FrameLayout.SCALE_X;
                float[] fArr8 = new float[1];
                fArr8[0] = i == 2 ? 1.0f : 0.6f;
                animatorArr[7] = ObjectAnimator.ofFloat(textView2, (Property<TextView, Float>) property8, fArr8);
                TextView textView3 = this.addButtonView;
                Property property9 = FrameLayout.SCALE_Y;
                float[] fArr9 = new float[1];
                fArr9[0] = i == 2 ? 1.0f : 0.6f;
                animatorArr[8] = ObjectAnimator.ofFloat(textView3, (Property<TextView, Float>) property9, fArr9);
                TextView textView4 = this.removeButtonView;
                Property property10 = FrameLayout.ALPHA;
                float[] fArr10 = new float[1];
                fArr10[0] = i == 3 ? 1.0f : 0.0f;
                animatorArr[9] = ObjectAnimator.ofFloat(textView4, (Property<TextView, Float>) property10, fArr10);
                TextView textView5 = this.removeButtonView;
                Property property11 = FrameLayout.SCALE_X;
                float[] fArr11 = new float[1];
                fArr11[0] = i == 3 ? 1.0f : 0.6f;
                animatorArr[10] = ObjectAnimator.ofFloat(textView5, (Property<TextView, Float>) property11, fArr11);
                TextView textView6 = this.removeButtonView;
                Property property12 = FrameLayout.SCALE_Y;
                float[] fArr12 = new float[1];
                fArr12[0] = i == 3 ? 1.0f : 0.6f;
                animatorArr[11] = ObjectAnimator.ofFloat(textView6, (Property<TextView, Float>) property12, fArr12);
                animatorSet2.playTogether(animatorArr);
                this.stateAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.EmojiPackHeader.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        EmojiPackHeader.this.premiumButtonView.setVisibility(0);
                        EmojiPackHeader.this.addButtonView.setVisibility(0);
                        EmojiPackHeader.this.removeButtonView.setVisibility(0);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EmojiPackHeader.this.premiumButtonView.setVisibility(i == 1 ? 0 : 8);
                        EmojiPackHeader.this.addButtonView.setVisibility(i == 2 ? 0 : 8);
                        EmojiPackHeader.this.removeButtonView.setVisibility(i != 3 ? 8 : 0);
                    }
                });
                this.stateAnimator.setDuration(250L);
                this.stateAnimator.setInterpolator(new OvershootInterpolator(1.02f));
                this.stateAnimator.start();
                return;
            }
            this.lockView.setAlpha(i == 1 ? 1.0f : 0.0f);
            this.lockView.setTranslationX(i == 1 ? 0.0f : -AndroidUtilities.dp(16.0f));
            this.headerView.setTranslationX(i == 1 ? AndroidUtilities.dp(16.0f) : 0.0f);
            this.premiumButtonView.setAlpha(i == 1 ? 1.0f : 0.0f);
            this.premiumButtonView.setScaleX(i == 1 ? 1.0f : 0.6f);
            this.premiumButtonView.setScaleY(i == 1 ? 1.0f : 0.6f);
            this.premiumButtonView.setVisibility(i == 1 ? 0 : 8);
            this.addButtonView.setAlpha(i == 2 ? 1.0f : 0.0f);
            this.addButtonView.setScaleX(i == 2 ? 1.0f : 0.6f);
            this.addButtonView.setScaleY(i == 2 ? 1.0f : 0.6f);
            this.addButtonView.setVisibility(i == 2 ? 0 : 8);
            this.removeButtonView.setAlpha(i == 3 ? 1.0f : 0.0f);
            this.removeButtonView.setScaleX(i == 3 ? 1.0f : 0.6f);
            this.removeButtonView.setScaleY(i == 3 ? 1.0f : 0.6f);
            this.removeButtonView.setVisibility(i != 3 ? 8 : 0);
        }
    }

    public void openEmojiPackAlert(final TLRPC$StickerSet tLRPC$StickerSet) {
        if (this.emojiPackAlertOpened) {
            return;
        }
        this.emojiPackAlertOpened = true;
        ArrayList arrayList = new ArrayList(1);
        TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
        tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSet.id;
        tLRPC$TL_inputStickerSetID.access_hash = tLRPC$StickerSet.access_hash;
        arrayList.add(tLRPC$TL_inputStickerSetID);
        new EmojiPacksAlert(this.fragment, getContext(), this.resourcesProvider, arrayList) { // from class: org.telegram.ui.Components.EmojiView.28
            @Override // org.telegram.ui.Components.EmojiPacksAlert, org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface
            public void dismiss() {
                EmojiView.this.emojiPackAlertOpened = false;
                super.dismiss();
            }

            @Override // org.telegram.ui.Components.EmojiPacksAlert
            protected void onButtonClicked(boolean z) {
                if (z) {
                    if (!EmojiView.this.installedEmojiSets.contains(Long.valueOf(tLRPC$StickerSet.id))) {
                        EmojiView.this.installedEmojiSets.add(Long.valueOf(tLRPC$StickerSet.id));
                    }
                } else {
                    EmojiView.this.installedEmojiSets.remove(Long.valueOf(tLRPC$StickerSet.id));
                }
                EmojiView.this.updateEmojiHeaders();
            }
        }.show();
    }

    public class EmojiGridSpacing extends RecyclerView.ItemDecoration {
        public EmojiGridSpacing() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(android.graphics.Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            if (view instanceof StickerSetNameCell) {
                rect.left = AndroidUtilities.dp(5.0f);
                rect.right = AndroidUtilities.dp(5.0f);
                if (recyclerView.getChildAdapterPosition(view) + 1 <= EmojiView.this.emojiAdapter.plainEmojisCount || UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium) {
                    return;
                }
                rect.top = AndroidUtilities.dp(10.0f);
                return;
            }
            if ((view instanceof RecyclerListView) || (view instanceof EmojiPackHeader)) {
                rect.left = -EmojiView.this.emojiGridView.getPaddingLeft();
                rect.right = -EmojiView.this.emojiGridView.getPaddingRight();
                if (view instanceof EmojiPackHeader) {
                    rect.top = AndroidUtilities.dp(8.0f);
                    return;
                }
                return;
            }
            if (view instanceof BackupImageView) {
                rect.bottom = AndroidUtilities.dp(12.0f);
            }
        }
    }

    public static String addColorToCode(String str, String str2) {
        if (CompoundEmoji.isHandshake(str) != null) {
            return CompoundEmoji.applyColor(str, str2);
        }
        String str3 = null;
        int length = str.length();
        if (length > 2 && str.charAt(str.length() - 2) == 8205) {
            str3 = str.substring(str.length() - 2);
            str = str.substring(0, str.length() - 2);
        } else if (length > 3 && str.charAt(str.length() - 3) == 8205) {
            str3 = str.substring(str.length() - 3);
            str = str.substring(0, str.length() - 3);
        }
        String str4 = str + str2;
        if (str3 == null) {
            return str4;
        }
        return str4 + str3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openTrendingStickers(TLRPC$StickerSetCovered tLRPC$StickerSetCovered) {
        this.delegate.showTrendingStickersAlert(new TrendingStickersLayout(getContext(), new TrendingStickersLayout.Delegate() { // from class: org.telegram.ui.Components.EmojiView.29
            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean canSendSticker() {
                return true;
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSetAdd(TLRPC$StickerSetCovered tLRPC$StickerSetCovered2, boolean z) {
                EmojiView.this.delegate.onStickerSetAdd(tLRPC$StickerSetCovered2);
                if (z) {
                    EmojiView.this.updateStickerTabs(true);
                }
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSetRemove(TLRPC$StickerSetCovered tLRPC$StickerSetCovered2) {
                EmojiView.this.delegate.onStickerSetRemove(tLRPC$StickerSetCovered2);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean onListViewInterceptTouchEvent(RecyclerListView recyclerListView, MotionEvent motionEvent) {
                return ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean onListViewTouchEvent(RecyclerListView recyclerListView, RecyclerListView.OnItemClickListener onItemClickListener, MotionEvent motionEvent) {
                return ContentPreviewViewer.getInstance().onTouch(motionEvent, recyclerListView, EmojiView.this.getMeasuredHeight(), onItemClickListener, EmojiView.this.contentPreviewViewerDelegate, EmojiView.this.resourcesProvider);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public String[] getLastSearchKeyboardLanguage() {
                return EmojiView.this.lastSearchKeyboardLanguage;
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void setLastSearchKeyboardLanguage(String[] strArr) {
                EmojiView.this.lastSearchKeyboardLanguage = strArr;
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public void onStickerSelected(TLRPC$Document tLRPC$Document, Object obj, boolean z, boolean z2, int i) {
                EmojiView.this.delegate.onStickerSelected(null, tLRPC$Document, null, obj, null, z2, i);
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean canSchedule() {
                return EmojiView.this.delegate.canSchedule();
            }

            @Override // org.telegram.ui.Components.TrendingStickersLayout.Delegate
            public boolean isInScheduleMode() {
                return EmojiView.this.delegate.isInScheduleMode();
            }
        }, this.primaryInstallingStickerSets, this.installingStickerSets, this.removingStickerSets, tLRPC$StickerSetCovered, this.resourcesProvider));
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        updateStickerTabsPosition();
        updateBottomTabContainerPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBottomTabContainerPosition() {
        int measuredHeight;
        int dp;
        BaseFragment baseFragment;
        View view = (View) getParent();
        if (view != null) {
            float y = getY();
            if (getLayoutParams().height > 0) {
                measuredHeight = getLayoutParams().height;
            } else {
                measuredHeight = getMeasuredHeight();
            }
            float f = y + measuredHeight;
            if (!AndroidUtilities.isInMultiwindow && ((baseFragment = this.fragment) == null || !baseFragment.isInBubbleMode())) {
                dp = view.getHeight();
            } else {
                dp = AndroidUtilities.dp(1.0f);
            }
            float f2 = f - dp;
            if (this.bottomTabContainer.getTop() - f2 < 0.0f) {
                f2 = 0.0f;
            }
            float f3 = -f2;
            this.bottomTabMainTranslation = f3;
            this.bottomTabContainer.setTranslationY(f3 + this.bottomTabAdditionalTranslation);
            if (this.needEmojiSearch) {
                this.bulletinContainer.setTranslationY(this.bottomTabMainTranslation + this.bottomTabAdditionalTranslation);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStickerTabsPosition() {
        if (this.stickersTabContainer == null) {
            return;
        }
        boolean z = getVisibility() == 0 && this.stickersContainerAttached && this.delegate.getProgressToSearchOpened() != 1.0f;
        this.stickersTabContainer.setVisibility(z ? 0 : 8);
        if (z) {
            this.rect.setEmpty();
            this.pager.getChildVisibleRect(this.stickersContainer, this.rect, null);
            float dp = AndroidUtilities.dp(50.0f) * this.delegate.getProgressToSearchOpened();
            int i = this.rect.left;
            if (i != 0 || dp != 0.0f) {
                this.expandStickersByDragg = false;
            }
            this.stickersTabContainer.setTranslationX(i);
            float top = (((getTop() + getTranslationY()) - this.stickersTabContainer.getTop()) - this.stickersTab.getExpandedOffset()) - dp;
            if (this.stickersTabContainer.getTranslationY() != top) {
                this.stickersTabContainer.setTranslationY(top);
                this.stickersTabContainer.invalidate();
            }
        }
        if (this.expandStickersByDragg && z && this.showing) {
            this.stickersTab.expandStickers(this.lastStickersX, true);
        } else {
            this.expandStickersByDragg = false;
            this.stickersTab.expandStickers(this.lastStickersX, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        updateBottomTabContainerPosition();
        super.dispatchDraw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startStopVisibleGifs(boolean z) {
        RecyclerListView recyclerListView = this.gifGridView;
        if (recyclerListView == null) {
            return;
        }
        int childCount = recyclerListView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.gifGridView.getChildAt(i);
            if (childAt instanceof ContextLinkCell) {
                ImageReceiver photoImage = ((ContextLinkCell) childAt).getPhotoImage();
                if (z) {
                    photoImage.setAllowStartAnimation(true);
                    photoImage.startAnimation();
                } else {
                    photoImage.setAllowStartAnimation(false);
                    photoImage.stopAnimation();
                }
            }
        }
    }

    public ArrayList<String> getRecentEmoji() {
        if (this.allowAnimatedEmoji) {
            return Emoji.recentEmoji;
        }
        if (this.lastRecentArray == null) {
            this.lastRecentArray = new ArrayList<>();
        }
        if (Emoji.recentEmoji.size() != this.lastRecentCount) {
            this.lastRecentArray.clear();
            for (int i = 0; i < Emoji.recentEmoji.size(); i++) {
                if (!Emoji.recentEmoji.get(i).startsWith("animated_")) {
                    this.lastRecentArray.add(Emoji.recentEmoji.get(i));
                }
            }
            this.lastRecentCount = this.lastRecentArray.size();
        }
        return this.lastRecentArray;
    }

    public void addEmojiToRecent(String str) {
        if (str != null) {
            if (str.startsWith("animated_") || Emoji.isValidEmoji(str)) {
                Emoji.addRecentEmoji(str);
                if (getVisibility() != 0 || this.pager.getCurrentItem() != 0) {
                    Emoji.sortEmoji();
                    this.emojiAdapter.notifyDataSetChanged();
                }
                Emoji.saveRecentEmoji();
                if (this.allowAnimatedEmoji) {
                    return;
                }
                ArrayList<String> arrayList = this.lastRecentArray;
                if (arrayList == null) {
                    this.lastRecentArray = new ArrayList<>();
                } else {
                    arrayList.clear();
                }
                for (int i = 0; i < Emoji.recentEmoji.size(); i++) {
                    if (!Emoji.recentEmoji.get(i).startsWith("animated_")) {
                        this.lastRecentArray.add(Emoji.recentEmoji.get(i));
                    }
                }
                this.lastRecentCount = this.lastRecentArray.size();
            }
        }
    }

    public void showSearchField(boolean z) {
        for (int i = 0; i < 3; i++) {
            GridLayoutManager layoutManagerForType = getLayoutManagerForType(i);
            int findFirstVisibleItemPosition = layoutManagerForType.findFirstVisibleItemPosition();
            if (z) {
                if (findFirstVisibleItemPosition == 1 || findFirstVisibleItemPosition == 2) {
                    layoutManagerForType.scrollToPosition(0);
                    resetTabsY(i);
                }
            } else if (findFirstVisibleItemPosition == 0) {
                layoutManagerForType.scrollToPositionWithOffset(0, 0);
            }
        }
    }

    public void hideSearchKeyboard() {
        SearchField searchField = this.stickersSearchField;
        if (searchField != null) {
            searchField.hideKeyboard();
        }
        SearchField searchField2 = this.gifSearchField;
        if (searchField2 != null) {
            searchField2.hideKeyboard();
        }
        SearchField searchField3 = this.emojiSearchField;
        if (searchField3 != null) {
            searchField3.hideKeyboard();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openSearch(SearchField searchField) {
        SearchField searchField2;
        final RecyclerListView recyclerListView;
        View view;
        LinearLayoutManager linearLayoutManager;
        EmojiViewDelegate emojiViewDelegate;
        AnimatorSet animatorSet = this.searchAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.searchAnimation = null;
        }
        this.firstStickersAttach = false;
        this.firstGifAttach = false;
        this.firstEmojiAttach = false;
        int i = 0;
        while (true) {
            if (i >= 3) {
                showBottomTab(false, true);
                return;
            }
            if (i == 0) {
                searchField2 = this.emojiSearchField;
                recyclerListView = this.emojiGridView;
                view = this.emojiTabs;
                linearLayoutManager = this.emojiLayoutManager;
            } else if (i == 1) {
                searchField2 = this.gifSearchField;
                recyclerListView = this.gifGridView;
                view = this.gifTabs;
                linearLayoutManager = this.gifLayoutManager;
            } else {
                searchField2 = this.stickersSearchField;
                recyclerListView = this.stickersGridView;
                view = this.stickersTab;
                linearLayoutManager = this.stickersLayoutManager;
            }
            if (searchField2 != null) {
                if (searchField == searchField2 && (emojiViewDelegate = this.delegate) != null && emojiViewDelegate.isExpanded()) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.searchAnimation = animatorSet2;
                    if (view != null && i != 2) {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, -AndroidUtilities.dp(40.0f)), ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) View.TRANSLATION_Y, -AndroidUtilities.dp(36.0f)), ObjectAnimator.ofFloat(searchField2, (Property<SearchField, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(0.0f)));
                    } else {
                        Animator[] animatorArr = new Animator[2];
                        Property property = View.TRANSLATION_Y;
                        float[] fArr = new float[1];
                        fArr[0] = i == 2 ? 0.0f : -AndroidUtilities.dp(36.0f);
                        animatorArr[0] = ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) property, fArr);
                        animatorArr[1] = ObjectAnimator.ofFloat(searchField2, (Property<SearchField, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(0.0f));
                        animatorSet2.playTogether(animatorArr);
                    }
                    this.searchAnimation.setDuration(220L);
                    this.searchAnimation.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.searchAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.30
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                recyclerListView.setTranslationY(0.0f);
                                if (recyclerListView != EmojiView.this.stickersGridView) {
                                    if (recyclerListView != EmojiView.this.emojiGridView) {
                                        if (recyclerListView == EmojiView.this.gifGridView) {
                                            recyclerListView.setPadding(0, EmojiView.this.searchFieldHeight, 0, 0);
                                        }
                                    } else {
                                        recyclerListView.setPadding(AndroidUtilities.dp(5.0f), 0, AndroidUtilities.dp(5.0f), 0);
                                    }
                                } else {
                                    recyclerListView.setPadding(0, 0, 0, 0);
                                }
                                EmojiView.this.searchAnimation = null;
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                EmojiView.this.searchAnimation = null;
                            }
                        }
                    });
                    this.searchAnimation.start();
                } else {
                    searchField2.setTranslationY(AndroidUtilities.dp(0.0f));
                    if (view != null && i != 2) {
                        view.setTranslationY(-AndroidUtilities.dp(40.0f));
                    }
                    if (recyclerListView == this.stickersGridView) {
                        recyclerListView.setPadding(0, AndroidUtilities.dp(4.0f), 0, 0);
                    } else if (recyclerListView == this.emojiGridView) {
                        recyclerListView.setPadding(AndroidUtilities.dp(5.0f), 0, AndroidUtilities.dp(5.0f), 0);
                    } else if (recyclerListView == this.gifGridView) {
                        recyclerListView.setPadding(0, this.searchFieldHeight, 0, 0);
                    }
                    if (recyclerListView == this.gifGridView) {
                        if (this.gifSearchAdapter.showTrendingWhenSearchEmpty = this.gifAdapter.results.size() > 0) {
                            this.gifSearchAdapter.search("");
                            RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
                            GifAdapter gifAdapter = this.gifSearchAdapter;
                            if (adapter != gifAdapter) {
                                this.gifGridView.setAdapter(gifAdapter);
                            }
                        }
                    }
                    linearLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            }
            i++;
        }
    }

    private void showEmojiShadow(boolean z, boolean z2) {
        if (z && this.emojiTabsShadow.getTag() == null) {
            return;
        }
        if (z || this.emojiTabsShadow.getTag() == null) {
            AnimatorSet animatorSet = this.emojiTabShadowAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.emojiTabShadowAnimator = null;
            }
            this.emojiTabsShadow.setTag(z ? null : 1);
            if (z2) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.emojiTabShadowAnimator = animatorSet2;
                Animator[] animatorArr = new Animator[1];
                View view = this.emojiTabsShadow;
                Property property = View.ALPHA;
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, fArr);
                animatorSet2.playTogether(animatorArr);
                this.emojiTabShadowAnimator.setDuration(200L);
                this.emojiTabShadowAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.emojiTabShadowAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.31
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        EmojiView.this.emojiTabShadowAnimator = null;
                    }
                });
                this.emojiTabShadowAnimator.start();
                return;
            }
            this.emojiTabsShadow.setAlpha(z ? 1.0f : 0.0f);
        }
    }

    public void closeSearch(boolean z) {
        closeSearch(z, -1L);
    }

    private void scrollStickersToPosition(int i, int i2) {
        View findViewByPosition = this.stickersLayoutManager.findViewByPosition(i);
        int findFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition();
        if (findViewByPosition == null && Math.abs(i - findFirstVisibleItemPosition) > 40) {
            this.stickersScrollHelper.setScrollDirection(this.stickersLayoutManager.findFirstVisibleItemPosition() < i ? 0 : 1);
            this.stickersScrollHelper.scrollToPosition(i, i2, false, true);
        } else {
            this.ignoreStickersScroll = true;
            this.stickersGridView.smoothScrollToPosition(i);
        }
    }

    public void scrollEmojisToAnimated() {
        if (this.emojiSmoothScrolling) {
            return;
        }
        try {
            int i = this.emojiAdapter.sectionToPosition.get(EmojiData.dataColored.length);
            if (i > 0) {
                this.emojiGridView.stopScroll();
                updateEmojiTabsPosition(i);
                scrollEmojisToPosition(i, AndroidUtilities.dp(-9.0f));
                checkEmojiTabY(null, 0);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollEmojisToPosition(int i, int i2) {
        View findViewByPosition = this.emojiLayoutManager.findViewByPosition(i);
        int findFirstVisibleItemPosition = this.emojiLayoutManager.findFirstVisibleItemPosition();
        if ((findViewByPosition == null && Math.abs(i - findFirstVisibleItemPosition) > this.emojiLayoutManager.getSpanCount() * 9.0f) || !SharedConfig.animationsEnabled()) {
            this.emojiScrollHelper.setScrollDirection(this.emojiLayoutManager.findFirstVisibleItemPosition() < i ? 0 : 1);
            this.emojiScrollHelper.scrollToPosition(i, i2, false, true);
            return;
        }
        this.ignoreStickersScroll = true;
        LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(this.emojiGridView.getContext(), 2) { // from class: org.telegram.ui.Components.EmojiView.32
            @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom
            public void onEnd() {
                EmojiView.this.emojiSmoothScrolling = false;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScrollerCustom, androidx.recyclerview.widget.RecyclerView.SmoothScroller
            protected void onStart() {
                EmojiView.this.emojiSmoothScrolling = true;
            }
        };
        linearSmoothScrollerCustom.setTargetPosition(i);
        linearSmoothScrollerCustom.setOffset(i2);
        this.emojiLayoutManager.startSmoothScroll(linearSmoothScrollerCustom);
    }

    public void closeSearch(boolean z, long j) {
        SearchField searchField;
        final RecyclerListView recyclerListView;
        final GridLayoutManager gridLayoutManager;
        View view;
        TLRPC$TL_messages_stickerSet stickerSetById;
        int positionForPack;
        AnimatorSet animatorSet = this.searchAnimation;
        StickerCategoriesListView.EmojiCategory emojiCategory = null;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.searchAnimation = null;
        }
        int currentItem = this.pager.getCurrentItem();
        if (currentItem == 2 && j != -1 && (stickerSetById = MediaDataController.getInstance(this.currentAccount).getStickerSetById(j)) != null && (positionForPack = this.stickersGridAdapter.getPositionForPack(stickerSetById)) >= 0 && positionForPack < this.stickersGridAdapter.getItemCount()) {
            scrollStickersToPosition(positionForPack, AndroidUtilities.dp(48.0f));
        }
        GifAdapter gifAdapter = this.gifSearchAdapter;
        if (gifAdapter != null) {
            gifAdapter.showTrendingWhenSearchEmpty = false;
        }
        int i = 0;
        while (i < 3) {
            if (i == 0) {
                searchField = this.emojiSearchField;
                recyclerListView = this.emojiGridView;
                gridLayoutManager = this.emojiLayoutManager;
                view = this.emojiTabs;
            } else if (i == 1) {
                searchField = this.gifSearchField;
                recyclerListView = this.gifGridView;
                gridLayoutManager = this.gifLayoutManager;
                view = this.gifTabs;
            } else {
                searchField = this.stickersSearchField;
                recyclerListView = this.stickersGridView;
                gridLayoutManager = this.stickersLayoutManager;
                view = this.stickersTab;
            }
            if (searchField != null) {
                searchField.searchEditText.setText("");
                if (searchField.categoriesListView != null) {
                    searchField.categoriesListView.selectCategory(emojiCategory);
                    searchField.categoriesListView.scrollToStart();
                }
                if (i == currentItem && z) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.searchAnimation = animatorSet2;
                    if (view != null && i != 1) {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(view, (Property<View, Float>) View.TRANSLATION_Y, 0.0f), ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(36.0f)), ObjectAnimator.ofFloat(searchField, (Property<SearchField, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(36.0f)));
                    } else {
                        animatorSet2.playTogether(ObjectAnimator.ofFloat(recyclerListView, (Property<RecyclerListView, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(36.0f) - this.searchFieldHeight));
                    }
                    this.searchAnimation.setDuration(200L);
                    this.searchAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.searchAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.33
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                int findFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                                recyclerListView.setTranslationY(0.0f);
                                if (recyclerListView != EmojiView.this.stickersGridView) {
                                    if (recyclerListView == EmojiView.this.gifGridView) {
                                        recyclerListView.setPadding(0, EmojiView.this.searchFieldHeight, 0, AndroidUtilities.dp(44.0f));
                                    } else if (recyclerListView == EmojiView.this.emojiGridView) {
                                        recyclerListView.setPadding(AndroidUtilities.dp(5.0f), AndroidUtilities.dp(36.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(44.0f));
                                    }
                                } else {
                                    recyclerListView.setPadding(0, AndroidUtilities.dp(36.0f), 0, AndroidUtilities.dp(44.0f));
                                }
                                if (findFirstVisibleItemPosition != -1) {
                                    gridLayoutManager.scrollToPositionWithOffset(findFirstVisibleItemPosition, 0);
                                }
                                EmojiView.this.searchAnimation = null;
                            }
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            if (animator.equals(EmojiView.this.searchAnimation)) {
                                EmojiView.this.searchAnimation = null;
                            }
                        }
                    });
                    this.searchAnimation.start();
                } else {
                    if (searchField != this.gifSearchField) {
                        searchField.setTranslationY(AndroidUtilities.dp(36.0f) - this.searchFieldHeight);
                    }
                    if (view != null && i != 2) {
                        view.setTranslationY(0.0f);
                    }
                    if (recyclerListView == this.stickersGridView) {
                        recyclerListView.setPadding(0, AndroidUtilities.dp(36.0f), 0, AndroidUtilities.dp(44.0f));
                    } else if (recyclerListView == this.gifGridView) {
                        recyclerListView.setPadding(0, AndroidUtilities.dp(40.0f), 0, AndroidUtilities.dp(44.0f));
                    } else if (recyclerListView == this.emojiGridView) {
                        recyclerListView.setPadding(AndroidUtilities.dp(5.0f), AndroidUtilities.dp(36.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(44.0f));
                    }
                    gridLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            }
            i++;
            emojiCategory = null;
        }
        if (z) {
            return;
        }
        this.delegate.onSearchOpenClose(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkStickersSearchFieldScroll(boolean z) {
        RecyclerListView recyclerListView;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.stickersGridView.findViewHolderForAdapterPosition(0);
            if (findViewHolderForAdapterPosition == null) {
                this.stickersSearchField.showShadow(true, !z);
                return;
            } else {
                this.stickersSearchField.showShadow(findViewHolderForAdapterPosition.itemView.getTop() < this.stickersGridView.getPaddingTop(), !z);
                return;
            }
        }
        if (this.stickersSearchField == null || (recyclerListView = this.stickersGridView) == null) {
            return;
        }
        if (recyclerListView.findViewHolderForAdapterPosition(0) != null) {
            this.stickersSearchField.setTranslationY(r0.itemView.getTop());
        } else {
            this.stickersSearchField.setTranslationY(-this.searchFieldHeight);
        }
        this.stickersSearchField.showShadow(false, !z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBottomTabScroll(float f) {
        int dp;
        if (SystemClock.elapsedRealtime() - this.shownBottomTabAfterClick < ViewConfiguration.getTapTimeout()) {
            return;
        }
        this.lastBottomScrollDy += f;
        if (this.pager.getCurrentItem() == 0) {
            dp = AndroidUtilities.dp(38.0f);
        } else {
            dp = AndroidUtilities.dp(48.0f);
        }
        float f2 = this.lastBottomScrollDy;
        if (f2 >= dp) {
            showBottomTab(false, true);
            return;
        }
        if (f2 <= (-dp)) {
            showBottomTab(true, true);
        } else {
            if ((this.bottomTabContainer.getTag() != null || this.lastBottomScrollDy >= 0.0f) && (this.bottomTabContainer.getTag() == null || this.lastBottomScrollDy <= 0.0f)) {
                return;
            }
            this.lastBottomScrollDy = 0.0f;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBackspaceButton(final boolean z, boolean z2) {
        if (z && this.backspaceButton.getTag() == null) {
            return;
        }
        if (z || this.backspaceButton.getTag() == null) {
            AnimatorSet animatorSet = this.backspaceButtonAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.backspaceButtonAnimation = null;
            }
            this.backspaceButton.setTag(z ? null : 1);
            if (z2) {
                if (z) {
                    this.backspaceButton.setVisibility(0);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.backspaceButtonAnimation = animatorSet2;
                Animator[] animatorArr = new Animator[3];
                ImageView imageView = this.backspaceButton;
                Property property = View.ALPHA;
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) property, fArr);
                ImageView imageView2 = this.backspaceButton;
                Property property2 = View.SCALE_X;
                float[] fArr2 = new float[1];
                fArr2[0] = z ? 1.0f : 0.0f;
                animatorArr[1] = ObjectAnimator.ofFloat(imageView2, (Property<ImageView, Float>) property2, fArr2);
                ImageView imageView3 = this.backspaceButton;
                Property property3 = View.SCALE_Y;
                float[] fArr3 = new float[1];
                fArr3[0] = z ? 1.0f : 0.0f;
                animatorArr[2] = ObjectAnimator.ofFloat(imageView3, (Property<ImageView, Float>) property3, fArr3);
                animatorSet2.playTogether(animatorArr);
                this.backspaceButtonAnimation.setDuration(200L);
                this.backspaceButtonAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.backspaceButtonAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.34
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (z) {
                            return;
                        }
                        EmojiView.this.backspaceButton.setVisibility(4);
                    }
                });
                this.backspaceButtonAnimation.start();
                return;
            }
            this.backspaceButton.setAlpha(z ? 1.0f : 0.0f);
            this.backspaceButton.setScaleX(z ? 1.0f : 0.0f);
            this.backspaceButton.setScaleY(z ? 1.0f : 0.0f);
            this.backspaceButton.setVisibility(z ? 0 : 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showStickerSettingsButton(final boolean z, boolean z2) {
        ImageView imageView = this.stickerSettingsButton;
        if (imageView == null) {
            return;
        }
        if (z && imageView.getTag() == null) {
            return;
        }
        if (z || this.stickerSettingsButton.getTag() == null) {
            AnimatorSet animatorSet = this.stickersButtonAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.stickersButtonAnimation = null;
            }
            this.stickerSettingsButton.setTag(z ? null : 1);
            if (z2) {
                if (z) {
                    this.stickerSettingsButton.setVisibility(0);
                }
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.stickersButtonAnimation = animatorSet2;
                Animator[] animatorArr = new Animator[3];
                ImageView imageView2 = this.stickerSettingsButton;
                Property property = View.ALPHA;
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(imageView2, (Property<ImageView, Float>) property, fArr);
                ImageView imageView3 = this.stickerSettingsButton;
                Property property2 = View.SCALE_X;
                float[] fArr2 = new float[1];
                fArr2[0] = z ? 1.0f : 0.0f;
                animatorArr[1] = ObjectAnimator.ofFloat(imageView3, (Property<ImageView, Float>) property2, fArr2);
                ImageView imageView4 = this.stickerSettingsButton;
                Property property3 = View.SCALE_Y;
                float[] fArr3 = new float[1];
                fArr3[0] = z ? 1.0f : 0.0f;
                animatorArr[2] = ObjectAnimator.ofFloat(imageView4, (Property<ImageView, Float>) property3, fArr3);
                animatorSet2.playTogether(animatorArr);
                this.stickersButtonAnimation.setDuration(200L);
                this.stickersButtonAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.stickersButtonAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.35
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (z) {
                            return;
                        }
                        EmojiView.this.stickerSettingsButton.setVisibility(4);
                    }
                });
                this.stickersButtonAnimation.start();
                return;
            }
            this.stickerSettingsButton.setAlpha(z ? 1.0f : 0.0f);
            this.stickerSettingsButton.setScaleX(z ? 1.0f : 0.0f);
            this.stickerSettingsButton.setScaleY(z ? 1.0f : 0.0f);
            this.stickerSettingsButton.setVisibility(z ? 0 : 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBottomTab(boolean z, boolean z2) {
        float f = 0.0f;
        this.lastBottomScrollDy = 0.0f;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            z = false;
        }
        if (z && this.bottomTabContainer.getTag() == null) {
            return;
        }
        if (z || this.bottomTabContainer.getTag() == null) {
            ValueAnimator valueAnimator = this.bottomTabContainerAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.bottomTabContainerAnimator = null;
            }
            this.bottomTabContainer.setTag(z ? null : 1);
            if (z2) {
                float[] fArr = new float[2];
                fArr[0] = this.bottomTabAdditionalTranslation;
                if (!z) {
                    f = AndroidUtilities.dp(this.needEmojiSearch ? 45.0f : 50.0f);
                }
                fArr[1] = f;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                this.bottomTabContainerAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        EmojiView.this.lambda$showBottomTab$10(valueAnimator2);
                    }
                });
                this.bottomTabContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiView.36
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (EmojiView.this.bottomTabContainerAnimator != animator) {
                            return;
                        }
                        EmojiView emojiView = EmojiView.this;
                        emojiView.bottomTabAdditionalTranslation = ((Float) emojiView.bottomTabContainerAnimator.getAnimatedValue()).floatValue();
                        EmojiView.this.updateBottomTabContainerPosition();
                        EmojiView.this.bottomTabContainerAnimator = null;
                    }
                });
                this.bottomTabContainerAnimator.setDuration(380L);
                this.bottomTabContainerAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.bottomTabContainerAnimator.start();
                return;
            }
            if (!z) {
                f = AndroidUtilities.dp(this.needEmojiSearch ? 45.0f : 50.0f);
            }
            this.bottomTabAdditionalTranslation = f;
            updateBottomTabContainerPosition();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showBottomTab$10(ValueAnimator valueAnimator) {
        this.bottomTabAdditionalTranslation = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        updateBottomTabContainerPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTabsY(int i, int i2) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        if (i == 1) {
            checkEmojiTabY(this.emojiGridView, i2);
            return;
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && !this.ignoreStickersScroll) {
            RecyclerListView listViewForType = getListViewForType(i);
            if (i2 <= 0 || listViewForType == null || listViewForType.getVisibility() != 0 || (findViewHolderForAdapterPosition = listViewForType.findViewHolderForAdapterPosition(0)) == null || findViewHolderForAdapterPosition.itemView.getTop() + this.searchFieldHeight < listViewForType.getPaddingTop()) {
                int[] iArr = this.tabsMinusDy;
                iArr[i] = iArr[i] - i2;
                if (iArr[i] > 0) {
                    iArr[i] = 0;
                } else if (iArr[i] < (-AndroidUtilities.dp(288.0f))) {
                    this.tabsMinusDy[i] = -AndroidUtilities.dp(288.0f);
                }
                if (i == 0) {
                    updateStickerTabsPosition();
                } else {
                    getTabsForType(i).setTranslationY(Math.max(-AndroidUtilities.dp(48.0f), this.tabsMinusDy[i]));
                }
            }
        }
    }

    private void resetTabsY(int i) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && i != 0) {
            View tabsForType = getTabsForType(i);
            this.tabsMinusDy[i] = 0;
            tabsForType.setTranslationY(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void animateTabsY(final int i) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if ((emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) && i != 0) {
            float dpf2 = AndroidUtilities.dpf2(i == 1 ? 36.0f : 48.0f);
            float f = this.tabsMinusDy[i] / (-dpf2);
            if (f <= 0.0f || f >= 1.0f) {
                animateSearchField(i);
                return;
            }
            View tabsForType = getTabsForType(i);
            int i2 = f > 0.5f ? (int) (-Math.ceil(dpf2)) : 0;
            if (f > 0.5f) {
                animateSearchField(i, false, i2);
            }
            if (i == 1) {
                checkEmojiShadow(i2);
            }
            ObjectAnimator[] objectAnimatorArr = this.tabsYAnimators;
            if (objectAnimatorArr[i] == null) {
                objectAnimatorArr[i] = ObjectAnimator.ofFloat(tabsForType, (Property<View, Float>) View.TRANSLATION_Y, tabsForType.getTranslationY(), i2);
                this.tabsYAnimators[i].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        EmojiView.this.lambda$animateTabsY$11(i, valueAnimator);
                    }
                });
                this.tabsYAnimators[i].setDuration(200L);
            } else {
                objectAnimatorArr[i].setFloatValues(tabsForType.getTranslationY(), i2);
            }
            this.tabsYAnimators[i].start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animateTabsY$11(int i, ValueAnimator valueAnimator) {
        this.tabsMinusDy[i] = (int) ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopAnimatingTabsY(int i) {
        ObjectAnimator[] objectAnimatorArr = this.tabsYAnimators;
        if (objectAnimatorArr[i] == null || !objectAnimatorArr[i].isRunning()) {
            return;
        }
        this.tabsYAnimators[i].cancel();
    }

    private void animateSearchField(int i) {
        RecyclerListView listViewForType = getListViewForType(i);
        int dp = AndroidUtilities.dp(i == 1 ? 38.0f : 48.0f);
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = listViewForType.findViewHolderForAdapterPosition(0);
        if (findViewHolderForAdapterPosition != null) {
            int bottom = findViewHolderForAdapterPosition.itemView.getBottom();
            int[] iArr = this.tabsMinusDy;
            float f = (bottom - (dp + iArr[i])) / this.searchFieldHeight;
            if (f > 0.0f || f < 1.0f) {
                animateSearchField(i, f > 0.5f, iArr[i]);
            }
        }
    }

    private void animateSearchField(int i, boolean z, final int i2) {
        if (i == 2 || getListViewForType(i).findViewHolderForAdapterPosition(0) == null) {
            return;
        }
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(this, getContext()) { // from class: org.telegram.ui.Components.EmojiView.37
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int getVerticalSnapPreference() {
                return -1;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            protected int calculateTimeForDeceleration(int i3) {
                return super.calculateTimeForDeceleration(i3) * 16;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i3, int i4, int i5, int i6, int i7) {
                return super.calculateDtToFit(i3, i4, i5, i6, i7) + i2;
            }
        };
        linearSmoothScroller.setTargetPosition(!z ? 1 : 0);
        getLayoutManagerForType(i).startSmoothScroll(linearSmoothScroller);
    }

    private View getTabsForType(int i) {
        if (i == 0) {
            return this.stickersTab;
        }
        if (i == 1) {
            return this.emojiTabs;
        }
        if (i == 2) {
            return this.gifTabs;
        }
        throw new IllegalArgumentException("Unexpected argument: " + i);
    }

    private RecyclerListView getListViewForType(int i) {
        if (i == 0) {
            return this.stickersGridView;
        }
        if (i == 1) {
            return this.emojiGridView;
        }
        if (i == 2) {
            return this.gifGridView;
        }
        throw new IllegalArgumentException("Unexpected argument: " + i);
    }

    private GridLayoutManager getLayoutManagerForType(int i) {
        if (i == 0) {
            return this.stickersLayoutManager;
        }
        if (i == 1) {
            return this.emojiLayoutManager;
        }
        if (i == 2) {
            return this.gifLayoutManager;
        }
        throw new IllegalArgumentException("Unexpected argument: " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SearchField getSearchFieldForType(int i) {
        if (i == 0) {
            return this.stickersSearchField;
        }
        if (i == 1) {
            return this.emojiSearchField;
        }
        if (i == 2) {
            return this.gifSearchField;
        }
        throw new IllegalArgumentException("Unexpected argument: " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEmojiSearchFieldScroll(boolean z) {
        EmojiGridView emojiGridView;
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0);
            if (findViewHolderForAdapterPosition == null) {
                this.emojiSearchField.showShadow(true, !z);
            } else {
                this.emojiSearchField.showShadow(findViewHolderForAdapterPosition.itemView.getTop() < this.emojiGridView.getPaddingTop(), !z);
            }
            showEmojiShadow(false, !z);
            return;
        }
        if (this.emojiSearchField == null || (emojiGridView = this.emojiGridView) == null) {
            return;
        }
        if (emojiGridView.findViewHolderForAdapterPosition(0) != null) {
            this.emojiSearchField.setTranslationY(r0.itemView.getTop());
        } else {
            this.emojiSearchField.setTranslationY(-this.searchFieldHeight);
        }
        this.emojiSearchField.showShadow(false, !z);
        checkEmojiShadow(Math.round(this.emojiTabs.getTranslationY()));
    }

    private void checkEmojiShadow(int i) {
        ObjectAnimator[] objectAnimatorArr = this.tabsYAnimators;
        if (objectAnimatorArr[1] == null || !objectAnimatorArr[1].isRunning()) {
            boolean z = false;
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0);
            int dp = AndroidUtilities.dp(38.0f) + i;
            if (dp > 0 && (findViewHolderForAdapterPosition == null || findViewHolderForAdapterPosition.itemView.getBottom() < dp)) {
                z = true;
            }
            showEmojiShadow(z, !this.isLayout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkEmojiTabY(View view, int i) {
        EmojiGridView emojiGridView;
        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        if (view == null) {
            EmojiTabsStrip emojiTabsStrip = this.emojiTabs;
            this.tabsMinusDy[1] = 0;
            emojiTabsStrip.setTranslationY(0);
            return;
        }
        if (view.getVisibility() != 0 || this.emojiSmoothScrolling) {
            return;
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate == null || !emojiViewDelegate.isSearchOpened()) {
            if (i > 0 && (emojiGridView = this.emojiGridView) != null && emojiGridView.getVisibility() == 0 && (findViewHolderForAdapterPosition = this.emojiGridView.findViewHolderForAdapterPosition(0)) != null) {
                if (findViewHolderForAdapterPosition.itemView.getTop() + (this.needEmojiSearch ? this.searchFieldHeight : 0) >= this.emojiGridView.getPaddingTop()) {
                    return;
                }
            }
            int[] iArr = this.tabsMinusDy;
            iArr[1] = iArr[1] - i;
            if (iArr[1] > 0) {
                iArr[1] = 0;
            } else if (iArr[1] < (-AndroidUtilities.dp(108.0f))) {
                this.tabsMinusDy[1] = -AndroidUtilities.dp(108.0f);
            }
            this.emojiTabs.setTranslationY(Math.max(-AndroidUtilities.dp(36.0f), this.tabsMinusDy[1]));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkGifSearchFieldScroll(boolean z) {
        int findLastVisibleItemPosition;
        RecyclerListView recyclerListView = this.gifGridView;
        if (recyclerListView != null && (recyclerListView.getAdapter() instanceof GifAdapter)) {
            GifAdapter gifAdapter = (GifAdapter) this.gifGridView.getAdapter();
            if (!gifAdapter.searchEndReached && gifAdapter.reqId == 0 && !gifAdapter.results.isEmpty() && (findLastVisibleItemPosition = this.gifLayoutManager.findLastVisibleItemPosition()) != -1 && findLastVisibleItemPosition > this.gifLayoutManager.getItemCount() - 5) {
                gifAdapter.search(gifAdapter.lastSearchImageString, gifAdapter.nextSearchOffset, true, gifAdapter.lastSearchIsEmoji, gifAdapter.lastSearchIsEmoji);
            }
        }
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate != null && emojiViewDelegate.isSearchOpened()) {
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.gifGridView.findViewHolderForAdapterPosition(0);
            if (findViewHolderForAdapterPosition == null) {
                this.gifSearchField.showShadow(true, !z);
                return;
            } else {
                this.gifSearchField.showShadow(findViewHolderForAdapterPosition.itemView.getTop() < this.gifGridView.getPaddingTop(), !z);
                return;
            }
        }
        SearchField searchField = this.gifSearchField;
        if (searchField == null || this.gifGridView == null) {
            return;
        }
        searchField.showShadow(true, !z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollGifsToTop() {
        this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
        resetTabsY(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkScroll(int i) {
        int findFirstVisibleItemPosition;
        int findFirstVisibleItemPosition2;
        if (i == 0) {
            if (this.ignoreStickersScroll || (findFirstVisibleItemPosition2 = this.stickersLayoutManager.findFirstVisibleItemPosition()) == -1 || this.stickersGridView == null) {
                return;
            }
            int i2 = this.favTabNum;
            if (i2 <= 0 && (i2 = this.recentTabNum) <= 0) {
                i2 = this.stickersTabOffset;
            }
            this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(findFirstVisibleItemPosition2), i2);
            return;
        }
        if (i == 2) {
            RecyclerView.Adapter adapter = this.gifGridView.getAdapter();
            GifAdapter gifAdapter = this.gifAdapter;
            if (adapter != gifAdapter || gifAdapter.trendingSectionItem < 0 || this.gifTrendingTabNum < 0 || this.gifRecentTabNum < 0 || (findFirstVisibleItemPosition = this.gifLayoutManager.findFirstVisibleItemPosition()) == -1) {
                return;
            }
            this.gifTabs.onPageScrolled(findFirstVisibleItemPosition >= this.gifAdapter.trendingSectionItem ? this.gifTrendingTabNum : this.gifRecentTabNum, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveNewPage() {
        ViewPager viewPager = this.pager;
        if (viewPager == null) {
            return;
        }
        int currentItem = viewPager.getCurrentItem();
        int i = currentItem != 2 ? currentItem == 1 ? 2 : 0 : 1;
        if (this.currentPage != i) {
            this.currentPage = i;
            MessagesController.getGlobalEmojiSettings().edit().putInt("selected_page", i).commit();
        }
    }

    public void clearRecentEmoji() {
        Emoji.clearRecentEmoji();
        this.emojiAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPageScrolled(int i, int i2, int i3) {
        EmojiViewDelegate emojiViewDelegate = this.delegate;
        if (emojiViewDelegate == null) {
            return;
        }
        if (i == 1) {
            emojiViewDelegate.onTabOpened(i3 != 0 ? 2 : 0);
        } else if (i == 2) {
            emojiViewDelegate.onTabOpened(3);
        } else {
            emojiViewDelegate.onTabOpened(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postBackspaceRunnable(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                EmojiView.this.lambda$postBackspaceRunnable$12(i);
            }
        }, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postBackspaceRunnable$12(int i) {
        if (this.backspacePressed) {
            EmojiViewDelegate emojiViewDelegate = this.delegate;
            if (emojiViewDelegate != null && emojiViewDelegate.onBackspace()) {
                this.backspaceButton.performHapticFeedback(3);
            }
            this.backspaceOnce = true;
            postBackspaceRunnable(Math.max(50, i - 100));
        }
    }

    public void switchToGifRecent() {
        showBackspaceButton(false, false);
        showStickerSettingsButton(false, false);
        this.pager.setCurrentItem(1, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEmojiHeaders() {
        if (this.emojiGridView == null) {
            return;
        }
        for (int i = 0; i < this.emojiGridView.getChildCount(); i++) {
            View childAt = this.emojiGridView.getChildAt(i);
            if (childAt instanceof EmojiPackHeader) {
                ((EmojiPackHeader) childAt).updateState(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStickerTabs(boolean z) {
        TLRPC$Document tLRPC$Document;
        ArrayList<TLRPC$Document> arrayList;
        ArrayList<TLRPC$Document> arrayList2;
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip == null || scrollSlidingTabStrip.isDragging()) {
            return;
        }
        this.recentTabNum = -2;
        this.favTabNum = -2;
        this.trendingTabNum = -2;
        this.premiumTabNum = -2;
        this.hasChatStickers = false;
        this.stickersTabOffset = 0;
        int currentPosition = this.stickersTab.getCurrentPosition();
        this.stickersTab.beginUpdate((getParent() == null || getVisibility() != 0 || (this.installingStickerSets.size() == 0 && this.removingStickerSets.size() == 0)) ? false : true);
        MediaDataController mediaDataController = MediaDataController.getInstance(this.currentAccount);
        SharedPreferences emojiSettings = MessagesController.getEmojiSettings(this.currentAccount);
        this.featuredStickerSets.clear();
        ArrayList<TLRPC$StickerSetCovered> featuredStickerSets = mediaDataController.getFeaturedStickerSets();
        int size = featuredStickerSets.size();
        for (int i = 0; i < size; i++) {
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered = featuredStickerSets.get(i);
            if (!mediaDataController.isStickerPackInstalled(tLRPC$StickerSetCovered.set.id)) {
                this.featuredStickerSets.add(tLRPC$StickerSetCovered);
            }
        }
        TrendingAdapter trendingAdapter = this.trendingAdapter;
        if (trendingAdapter != null) {
            trendingAdapter.notifyDataSetChanged();
        }
        if (!featuredStickerSets.isEmpty() && (this.featuredStickerSets.isEmpty() || emojiSettings.getLong("featured_hidden", 0L) == featuredStickerSets.get(0).set.id)) {
            int i2 = mediaDataController.getUnreadStickerSets().isEmpty() ? 2 : 3;
            StickerTabView addStickerIconTab = this.stickersTab.addStickerIconTab(i2, this.stickerIcons[i2]);
            addStickerIconTab.textView.setText(LocaleController.getString("FeaturedStickersShort", R.string.FeaturedStickersShort));
            addStickerIconTab.setContentDescription(LocaleController.getString("FeaturedStickers", R.string.FeaturedStickers));
            int i3 = this.stickersTabOffset;
            this.trendingTabNum = i3;
            this.stickersTabOffset = i3 + 1;
        }
        if (!this.favouriteStickers.isEmpty()) {
            int i4 = this.stickersTabOffset;
            this.favTabNum = i4;
            this.stickersTabOffset = i4 + 1;
            StickerTabView addStickerIconTab2 = this.stickersTab.addStickerIconTab(1, this.stickerIcons[1]);
            addStickerIconTab2.textView.setText(LocaleController.getString("FavoriteStickersShort", R.string.FavoriteStickersShort));
            addStickerIconTab2.setContentDescription(LocaleController.getString("FavoriteStickers", R.string.FavoriteStickers));
        }
        if (!this.recentStickers.isEmpty()) {
            int i5 = this.stickersTabOffset;
            this.recentTabNum = i5;
            this.stickersTabOffset = i5 + 1;
            StickerTabView addStickerIconTab3 = this.stickersTab.addStickerIconTab(0, this.stickerIcons[0]);
            addStickerIconTab3.textView.setText(LocaleController.getString("RecentStickersShort", R.string.RecentStickersShort));
            addStickerIconTab3.setContentDescription(LocaleController.getString("RecentStickers", R.string.RecentStickers));
        }
        this.stickerSets.clear();
        this.groupStickerSet = null;
        this.groupStickerPackPosition = -1;
        this.groupStickerPackNum = -10;
        if (this.frozenStickerSets == null || z) {
            this.frozenStickerSets = new ArrayList<>(mediaDataController.getStickerSets(0));
        }
        ArrayList<TLRPC$TL_messages_stickerSet> arrayList3 = this.frozenStickerSets;
        int i6 = 0;
        while (true) {
            TLRPC$StickerSetCovered[] tLRPC$StickerSetCoveredArr = this.primaryInstallingStickerSets;
            if (i6 >= tLRPC$StickerSetCoveredArr.length) {
                break;
            }
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered2 = tLRPC$StickerSetCoveredArr[i6];
            if (tLRPC$StickerSetCovered2 != null) {
                TLRPC$TL_messages_stickerSet stickerSetById = mediaDataController.getStickerSetById(tLRPC$StickerSetCovered2.set.id);
                if (stickerSetById != null && !stickerSetById.set.archived) {
                    this.primaryInstallingStickerSets[i6] = null;
                } else {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = new TLRPC$TL_messages_stickerSet();
                    tLRPC$TL_messages_stickerSet.set = tLRPC$StickerSetCovered2.set;
                    TLRPC$Document tLRPC$Document2 = tLRPC$StickerSetCovered2.cover;
                    if (tLRPC$Document2 != null) {
                        tLRPC$TL_messages_stickerSet.documents.add(tLRPC$Document2);
                    } else if (!tLRPC$StickerSetCovered2.covers.isEmpty()) {
                        tLRPC$TL_messages_stickerSet.documents.addAll(tLRPC$StickerSetCovered2.covers);
                    }
                    if (!tLRPC$TL_messages_stickerSet.documents.isEmpty()) {
                        this.stickerSets.add(tLRPC$TL_messages_stickerSet);
                    }
                }
            }
            i6++;
        }
        ArrayList<TLRPC$TL_messages_stickerSet> filterPremiumStickers = MessagesController.getInstance(this.currentAccount).filterPremiumStickers(arrayList3);
        for (int i7 = 0; i7 < filterPremiumStickers.size(); i7++) {
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2 = filterPremiumStickers.get(i7);
            if (!tLRPC$TL_messages_stickerSet2.set.archived && (arrayList2 = tLRPC$TL_messages_stickerSet2.documents) != null && !arrayList2.isEmpty()) {
                this.stickerSets.add(tLRPC$TL_messages_stickerSet2);
            }
        }
        if (this.info != null) {
            long j = MessagesController.getEmojiSettings(this.currentAccount).getLong("group_hide_stickers_" + this.info.id, -1L);
            TLRPC$Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.info.id));
            if (chat == null || this.info.stickerset == null || !ChatObject.hasAdminRights(chat)) {
                this.groupStickersHidden = j != -1;
            } else {
                TLRPC$StickerSet tLRPC$StickerSet = this.info.stickerset;
                if (tLRPC$StickerSet != null) {
                    this.groupStickersHidden = j == tLRPC$StickerSet.id;
                }
            }
            TLRPC$ChatFull tLRPC$ChatFull = this.info;
            TLRPC$StickerSet tLRPC$StickerSet2 = tLRPC$ChatFull.stickerset;
            if (tLRPC$StickerSet2 != null) {
                TLRPC$TL_messages_stickerSet groupStickerSetById = mediaDataController.getGroupStickerSetById(tLRPC$StickerSet2);
                if (groupStickerSetById != null && (arrayList = groupStickerSetById.documents) != null && !arrayList.isEmpty() && groupStickerSetById.set != null) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet3 = new TLRPC$TL_messages_stickerSet();
                    tLRPC$TL_messages_stickerSet3.documents = groupStickerSetById.documents;
                    tLRPC$TL_messages_stickerSet3.packs = groupStickerSetById.packs;
                    tLRPC$TL_messages_stickerSet3.set = groupStickerSetById.set;
                    if (this.groupStickersHidden) {
                        this.groupStickerPackNum = this.stickerSets.size();
                        this.stickerSets.add(tLRPC$TL_messages_stickerSet3);
                    } else {
                        this.groupStickerPackNum = 0;
                        this.stickerSets.add(0, tLRPC$TL_messages_stickerSet3);
                    }
                    if (!this.info.can_set_stickers) {
                        tLRPC$TL_messages_stickerSet3 = null;
                    }
                    this.groupStickerSet = tLRPC$TL_messages_stickerSet3;
                }
            } else if (tLRPC$ChatFull.can_set_stickers) {
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet4 = new TLRPC$TL_messages_stickerSet();
                if (this.groupStickersHidden) {
                    this.groupStickerPackNum = this.stickerSets.size();
                    this.stickerSets.add(tLRPC$TL_messages_stickerSet4);
                } else {
                    this.groupStickerPackNum = 0;
                    this.stickerSets.add(0, tLRPC$TL_messages_stickerSet4);
                }
            }
        }
        int i8 = 0;
        while (i8 < this.stickerSets.size()) {
            if (i8 == this.groupStickerPackNum) {
                TLRPC$Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.info.id));
                if (chat2 == null) {
                    this.stickerSets.remove(0);
                    i8--;
                } else {
                    this.hasChatStickers = true;
                    this.stickersTab.addStickerTab(chat2);
                }
            } else {
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet5 = this.stickerSets.get(i8);
                TLRPC$StickerSet tLRPC$StickerSet3 = tLRPC$TL_messages_stickerSet5.set;
                if (tLRPC$StickerSet3 != null && tLRPC$StickerSet3.thumb_document_id != 0) {
                    for (int i9 = 0; i9 < tLRPC$TL_messages_stickerSet5.documents.size(); i9++) {
                        tLRPC$Document = tLRPC$TL_messages_stickerSet5.documents.get(i9);
                        if (tLRPC$Document != null && tLRPC$TL_messages_stickerSet5.set.thumb_document_id == tLRPC$Document.id) {
                            break;
                        }
                    }
                }
                tLRPC$Document = null;
                if (tLRPC$Document == null) {
                    tLRPC$Document = tLRPC$TL_messages_stickerSet5.documents.get(0);
                }
                TLObject closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tLRPC$TL_messages_stickerSet5.set.thumbs, 90);
                if (closestPhotoSizeWithSize == null || tLRPC$TL_messages_stickerSet5.set.gifs) {
                    closestPhotoSizeWithSize = tLRPC$Document;
                }
                this.stickersTab.addStickerTab(closestPhotoSizeWithSize, tLRPC$Document, tLRPC$TL_messages_stickerSet5).setContentDescription(tLRPC$TL_messages_stickerSet5.set.title + ", " + LocaleController.getString("AccDescrStickerSet", R.string.AccDescrStickerSet));
            }
            i8++;
        }
        this.stickersTab.commitUpdate();
        this.stickersTab.updateTabStyles();
        if (currentPosition != 0) {
            this.stickersTab.onPageScrolled(currentPosition, currentPosition);
        }
        checkPanels();
    }

    private void checkPanels() {
        int findFirstVisibleItemPosition;
        if (this.stickersTab == null || (findFirstVisibleItemPosition = this.stickersLayoutManager.findFirstVisibleItemPosition()) == -1) {
            return;
        }
        int i = this.favTabNum;
        if (i <= 0 && (i = this.recentTabNum) <= 0) {
            i = this.stickersTabOffset;
        }
        this.stickersTab.onPageScrolled(this.stickersGridAdapter.getTabForPosition(findFirstVisibleItemPosition), i);
    }

    private void updateGifTabs() {
        int i;
        int currentPosition = this.gifTabs.getCurrentPosition();
        int i2 = this.gifRecentTabNum;
        boolean z = currentPosition == i2;
        boolean z2 = i2 >= 0;
        boolean z3 = !this.recentGifs.isEmpty();
        this.gifTabs.beginUpdate(false);
        this.gifRecentTabNum = -2;
        this.gifTrendingTabNum = -2;
        this.gifFirstEmojiTabNum = -2;
        if (z3) {
            this.gifRecentTabNum = 0;
            this.gifTabs.addIconTab(0, this.gifIcons[0]).setContentDescription(LocaleController.getString("RecentStickers", R.string.RecentStickers));
            i = 1;
        } else {
            i = 0;
        }
        this.gifTrendingTabNum = i;
        this.gifTabs.addIconTab(1, this.gifIcons[1]).setContentDescription(LocaleController.getString("FeaturedGifs", R.string.FeaturedGifs));
        this.gifFirstEmojiTabNum = i + 1;
        AndroidUtilities.dp(13.0f);
        AndroidUtilities.dp(11.0f);
        ArrayList<String> arrayList = MessagesController.getInstance(this.currentAccount).gifSearchEmojies;
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            String str = arrayList.get(i3);
            Emoji.EmojiDrawable emojiDrawable = Emoji.getEmojiDrawable(str);
            if (emojiDrawable != null) {
                this.gifTabs.addEmojiTab(i3 + 3, emojiDrawable, MediaDataController.getInstance(this.currentAccount).getEmojiAnimatedSticker(str)).setContentDescription(str);
            }
        }
        this.gifTabs.commitUpdate();
        this.gifTabs.updateTabStyles();
        if (z && !z3) {
            this.gifTabs.selectTab(this.gifTrendingTabNum);
            SearchField searchField = this.gifSearchField;
            if (searchField == null || searchField.categoriesListView == null) {
                return;
            }
            this.gifSearchField.categoriesListView.selectCategory(this.gifSearchField.trending);
            return;
        }
        if (ViewCompat.isLaidOut(this.gifTabs)) {
            if (z3 && !z2) {
                this.gifTabs.onPageScrolled(currentPosition + 1, 0);
            } else {
                if (z3 || !z2) {
                    return;
                }
                this.gifTabs.onPageScrolled(currentPosition - 1, 0);
            }
        }
    }

    public void addRecentSticker(TLRPC$Document tLRPC$Document) {
        if (tLRPC$Document == null) {
            return;
        }
        MediaDataController.getInstance(this.currentAccount).addRecentSticker(0, null, tLRPC$Document, (int) (System.currentTimeMillis() / 1000), false);
        boolean isEmpty = this.recentStickers.isEmpty();
        this.recentStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(0);
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        if (isEmpty) {
            updateStickerTabs(false);
        }
    }

    public void addRecentGif(TLRPC$Document tLRPC$Document) {
        if (tLRPC$Document == null) {
            return;
        }
        boolean isEmpty = this.recentGifs.isEmpty();
        updateRecentGifs();
        if (isEmpty) {
            updateStickerTabs(false);
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.isLayout) {
            return;
        }
        super.requestLayout();
    }

    public void updateColors() {
        SearchField searchField;
        if (AndroidUtilities.isInMultiwindow || this.forseMultiwindowLayout) {
            Drawable background = getBackground();
            if (background != null) {
                background.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelBackground), PorterDuff.Mode.MULTIPLY));
            }
        } else {
            int i = Theme.key_chat_emojiPanelBackground;
            setBackgroundColor(getThemedColor(i));
            if (this.needEmojiSearch) {
                this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i));
            }
        }
        EmojiTabsStrip emojiTabsStrip = this.emojiTabs;
        if (emojiTabsStrip != null) {
            emojiTabsStrip.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
            this.emojiTabsShadow.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
        }
        EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
        if (emojiColorPickerWindow != null) {
            emojiColorPickerWindow.updateColors();
        }
        for (int i2 = 0; i2 < 3; i2++) {
            if (i2 == 0) {
                searchField = this.stickersSearchField;
            } else if (i2 == 1) {
                searchField = this.emojiSearchField;
            } else {
                searchField = this.gifSearchField;
            }
            if (searchField != null) {
                searchField.backgroundView.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
                searchField.shadowView.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
                SearchStateDrawable searchStateDrawable = searchField.searchStateDrawable;
                int i3 = Theme.key_chat_emojiSearchIcon;
                searchStateDrawable.setColor(getThemedColor(i3));
                Theme.setDrawableColorByKey(searchField.box.getBackground(), Theme.key_chat_emojiSearchBackground);
                searchField.box.invalidate();
                searchField.searchEditText.setHintTextColor(getThemedColor(i3));
                searchField.searchEditText.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            }
        }
        Paint paint = this.dotPaint;
        if (paint != null) {
            paint.setColor(getThemedColor(Theme.key_chat_emojiPanelNewTrending));
        }
        EmojiGridView emojiGridView = this.emojiGridView;
        if (emojiGridView != null) {
            emojiGridView.setGlowColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        RecyclerListView recyclerListView = this.stickersGridView;
        if (recyclerListView != null) {
            recyclerListView.setGlowColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip != null) {
            scrollSlidingTabStrip.setIndicatorColor(getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine));
            this.stickersTab.setUnderlineColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            this.stickersTab.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.gifTabs;
        if (scrollSlidingTabStrip2 != null) {
            scrollSlidingTabStrip2.setIndicatorColor(getThemedColor(Theme.key_chat_emojiPanelStickerPackSelectorLine));
            this.gifTabs.setUnderlineColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
            this.gifTabs.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelBackground));
        }
        ImageView imageView = this.backspaceButton;
        if (imageView != null) {
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelBackspace), PorterDuff.Mode.MULTIPLY));
            if (this.emojiSearchField == null) {
                Drawable background2 = this.backspaceButton.getBackground();
                int i4 = Theme.key_chat_emojiPanelBackground;
                Theme.setSelectorDrawableColor(background2, getThemedColor(i4), false);
                Theme.setSelectorDrawableColor(this.backspaceButton.getBackground(), getThemedColor(i4), true);
            }
        }
        ImageView imageView2 = this.stickerSettingsButton;
        if (imageView2 != null) {
            imageView2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelBackspace), PorterDuff.Mode.MULTIPLY));
        }
        ImageView imageView3 = this.searchButton;
        if (imageView3 != null) {
            imageView3.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelBackspace), PorterDuff.Mode.MULTIPLY));
        }
        View view = this.shadowLine;
        if (view != null) {
            view.setBackgroundColor(getThemedColor(Theme.key_chat_emojiPanelShadowLine));
        }
        TextView textView = this.mediaBanTooltip;
        if (textView != null) {
            ((ShapeDrawable) textView.getBackground()).getPaint().setColor(getThemedColor(Theme.key_chat_gifSaveHintBackground));
            this.mediaBanTooltip.setTextColor(getThemedColor(Theme.key_chat_gifSaveHintText));
        }
        GifAdapter gifAdapter = this.gifSearchAdapter;
        if (gifAdapter != null) {
            ImageView imageView4 = gifAdapter.progressEmptyView.imageView;
            int i5 = Theme.key_chat_emojiPanelEmptyText;
            imageView4.setColorFilter(new PorterDuffColorFilter(getThemedColor(i5), PorterDuff.Mode.MULTIPLY));
            this.gifSearchAdapter.progressEmptyView.textView.setTextColor(getThemedColor(i5));
            this.gifSearchAdapter.progressEmptyView.progressView.setProgressColor(getThemedColor(Theme.key_progressCircle));
        }
        this.animatedEmojiTextColorFilter = new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlackText), PorterDuff.Mode.SRC_IN);
        int i6 = 0;
        while (true) {
            Drawable[] drawableArr = this.tabIcons;
            if (i6 >= drawableArr.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr[i6], getThemedColor(Theme.key_chat_emojiBottomPanelIcon), false);
            Theme.setEmojiDrawableColor(this.tabIcons[i6], getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i6++;
        }
        EmojiTabsStrip emojiTabsStrip2 = this.emojiTabs;
        if (emojiTabsStrip2 != null) {
            emojiTabsStrip2.updateColors();
        }
        int i7 = 0;
        while (true) {
            Drawable[] drawableArr2 = this.stickerIcons;
            if (i7 >= drawableArr2.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr2[i7], getThemedColor(Theme.key_chat_emojiPanelIcon), false);
            Theme.setEmojiDrawableColor(this.stickerIcons[i7], getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i7++;
        }
        int i8 = 0;
        while (true) {
            Drawable[] drawableArr3 = this.gifIcons;
            if (i8 >= drawableArr3.length) {
                break;
            }
            Theme.setEmojiDrawableColor(drawableArr3[i8], getThemedColor(Theme.key_chat_emojiPanelIcon), false);
            Theme.setEmojiDrawableColor(this.gifIcons[i8], getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
            i8++;
        }
        Drawable drawable = this.searchIconDrawable;
        if (drawable != null) {
            Theme.setEmojiDrawableColor(drawable, getThemedColor(Theme.key_chat_emojiBottomPanelIcon), false);
            Theme.setEmojiDrawableColor(this.searchIconDrawable, getThemedColor(Theme.key_chat_emojiPanelIconSelected), true);
        }
        Drawable drawable2 = this.searchIconDotDrawable;
        if (drawable2 != null) {
            int i9 = Theme.key_chat_emojiPanelStickerPackSelectorLine;
            Theme.setEmojiDrawableColor(drawable2, getThemedColor(i9), false);
            Theme.setEmojiDrawableColor(this.searchIconDotDrawable, getThemedColor(i9), true);
        }
        Paint paint2 = this.emojiLockPaint;
        if (paint2 != null) {
            paint2.setColor(getThemedColor(Theme.key_chat_emojiPanelStickerSetName));
            this.emojiLockPaint.setAlpha((int) (r0.getAlpha() * 0.5f));
        }
        Drawable drawable3 = this.emojiLockDrawable;
        if (drawable3 != null) {
            drawable3.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_emojiPanelStickerSetName), PorterDuff.Mode.MULTIPLY));
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        this.isLayout = true;
        if (AndroidUtilities.isInMultiwindow || this.forseMultiwindowLayout) {
            if (this.currentBackgroundType != 1) {
                if (Build.VERSION.SDK_INT >= 21) {
                    setOutlineProvider((ViewOutlineProvider) this.outlineProvider);
                    setClipToOutline(true);
                    setElevation(AndroidUtilities.dp(2.0f));
                }
                setBackgroundResource(R.drawable.smiles_popup);
                Drawable background = getBackground();
                int i3 = Theme.key_chat_emojiPanelBackground;
                background.setColorFilter(new PorterDuffColorFilter(getThemedColor(i3), PorterDuff.Mode.MULTIPLY));
                if (this.needEmojiSearch) {
                    this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i3));
                }
                this.currentBackgroundType = 1;
            }
        } else if (this.currentBackgroundType != 0) {
            if (Build.VERSION.SDK_INT >= 21) {
                setOutlineProvider(null);
                setClipToOutline(false);
                setElevation(0.0f);
            }
            int i4 = Theme.key_chat_emojiPanelBackground;
            setBackgroundColor(getThemedColor(i4));
            if (this.needEmojiSearch) {
                this.bottomTabContainerBackground.setBackgroundColor(getThemedColor(i4));
            }
            this.currentBackgroundType = 0;
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824));
        this.isLayout = false;
        setTranslationY(getTranslationY());
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        if (this.lastNotifyWidth != i5) {
            this.lastNotifyWidth = i5;
            reloadStickersAdapter();
        }
        super.onLayout(z, i, i2, i3, i4);
        updateBottomTabContainerPosition();
        updateStickerTabsPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadStickersAdapter() {
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        StickersSearchGridAdapter stickersSearchGridAdapter = this.stickersSearchGridAdapter;
        if (stickersSearchGridAdapter != null) {
            stickersSearchGridAdapter.notifyDataSetChanged();
        }
        if (ContentPreviewViewer.getInstance().isVisible()) {
            ContentPreviewViewer.getInstance().close();
        }
        ContentPreviewViewer.getInstance().reset();
    }

    public void setDelegate(EmojiViewDelegate emojiViewDelegate) {
        this.delegate = emojiViewDelegate;
    }

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public void setChatInfo(TLRPC$ChatFull tLRPC$ChatFull) {
        this.info = tLRPC$ChatFull;
        updateStickerTabs(false);
    }

    public void invalidateViews() {
        this.emojiGridView.invalidateViews();
    }

    public void setForseMultiwindowLayout(boolean z) {
        this.forseMultiwindowLayout = z;
    }

    public void onOpen(boolean z) {
        if (this.currentPage != 0 && this.stickersBanned) {
            this.currentPage = 0;
        }
        if (this.currentPage == 0 && this.emojiBanned) {
            this.currentPage = 1;
        }
        if (this.currentPage == 0 || z || this.currentTabs.size() == 1) {
            showBackspaceButton(true, false);
            showStickerSettingsButton(false, false);
            if (this.pager.getCurrentItem() != 0) {
                this.pager.setCurrentItem(0, !z);
            }
        } else {
            int i = this.currentPage;
            if (i == 1) {
                showBackspaceButton(false, false);
                showStickerSettingsButton(true, false);
                if (this.pager.getCurrentItem() != 2) {
                    this.pager.setCurrentItem(2, false);
                }
                ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
                if (scrollSlidingTabStrip != null) {
                    this.firstTabUpdate = true;
                    int i2 = this.favTabNum;
                    if (i2 >= 0) {
                        scrollSlidingTabStrip.selectTab(i2);
                    } else {
                        int i3 = this.recentTabNum;
                        if (i3 >= 0) {
                            scrollSlidingTabStrip.selectTab(i3);
                        } else {
                            scrollSlidingTabStrip.selectTab(this.stickersTabOffset);
                        }
                    }
                    this.firstTabUpdate = false;
                    this.stickersLayoutManager.scrollToPositionWithOffset(0, 0);
                }
            } else if (i == 2) {
                showBackspaceButton(false, false);
                showStickerSettingsButton(false, false);
                if (this.pager.getCurrentItem() != 1) {
                    this.pager.setCurrentItem(1, false);
                }
                ScrollSlidingTabStrip scrollSlidingTabStrip2 = this.gifTabs;
                if (scrollSlidingTabStrip2 != null) {
                    scrollSlidingTabStrip2.selectTab(0);
                }
                SearchField searchField = this.gifSearchField;
                if (searchField != null && searchField.categoriesListView != null) {
                    this.gifSearchField.categoriesListView.selectCategory(this.gifSearchField.recent);
                }
            }
        }
        showBottomTab(true, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.newEmojiSuggestionsAvailable);
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.recentDocumentsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.featuredStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.this.lambda$onAttachedToWindow$13();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$13() {
        updateStickerTabs(false);
        reloadStickersAdapter();
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        boolean z = getVisibility() != i;
        super.setVisibility(i);
        if (z) {
            if (i != 8) {
                Emoji.sortEmoji();
                this.emojiAdapter.notifyDataSetChanged();
                NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersDidLoad);
                if (this.stickersGridAdapter != null) {
                    NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.recentDocumentsDidLoad);
                    updateStickerTabs(false);
                    reloadStickersAdapter();
                }
                checkDocuments(true);
                checkDocuments(false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(0, true, true, false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(0, false, true, false);
                MediaDataController.getInstance(this.currentAccount).loadRecents(2, false, true, false);
            }
            ChooseStickerActionTracker chooseStickerActionTracker = this.chooseStickerActionTracker;
            if (chooseStickerActionTracker != null) {
                chooseStickerActionTracker.checkVisibility();
            }
        }
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void onDestroy() {
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.newEmojiSuggestionsAvailable);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersDidLoad);
        if (this.stickersGridAdapter != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.recentDocumentsDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.featuredStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
        if (emojiColorPickerWindow == null || !emojiColorPickerWindow.isShowing()) {
            return;
        }
        this.colorPickerView.dismiss();
    }

    private void checkDocuments(boolean z) {
        if (z) {
            updateRecentGifs();
            return;
        }
        int size = this.recentStickers.size();
        int size2 = this.favouriteStickers.size();
        this.recentStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(0);
        this.favouriteStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(2);
        if (UserConfig.getInstance(this.currentAccount).isPremium()) {
            this.premiumStickers = MediaDataController.getInstance(this.currentAccount).getRecentStickers(7);
        } else {
            this.premiumStickers = new ArrayList<>();
        }
        for (int i = 0; i < this.favouriteStickers.size(); i++) {
            TLRPC$Document tLRPC$Document = this.favouriteStickers.get(i);
            int i2 = 0;
            while (true) {
                if (i2 < this.recentStickers.size()) {
                    TLRPC$Document tLRPC$Document2 = this.recentStickers.get(i2);
                    if (tLRPC$Document2.dc_id == tLRPC$Document.dc_id && tLRPC$Document2.id == tLRPC$Document.id) {
                        this.recentStickers.remove(i2);
                        break;
                    }
                    i2++;
                }
            }
        }
        if (MessagesController.getInstance(this.currentAccount).premiumLocked) {
            int i3 = 0;
            while (i3 < this.favouriteStickers.size()) {
                if (MessageObject.isPremiumSticker(this.favouriteStickers.get(i3))) {
                    this.favouriteStickers.remove(i3);
                    i3--;
                }
                i3++;
            }
            int i4 = 0;
            while (i4 < this.recentStickers.size()) {
                if (MessageObject.isPremiumSticker(this.recentStickers.get(i4))) {
                    this.recentStickers.remove(i4);
                    i4--;
                }
                i4++;
            }
        }
        if (size != this.recentStickers.size() || size2 != this.favouriteStickers.size()) {
            updateStickerTabs(false);
        }
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        if (stickersGridAdapter != null) {
            stickersGridAdapter.notifyDataSetChanged();
        }
        checkPanels();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRecentGifs() {
        GifAdapter gifAdapter;
        int size = this.recentGifs.size();
        long calcDocumentsHash = MediaDataController.calcDocumentsHash(this.recentGifs, Integer.MAX_VALUE);
        ArrayList<TLRPC$Document> recentGifs = MediaDataController.getInstance(this.currentAccount).getRecentGifs();
        this.recentGifs = recentGifs;
        long calcDocumentsHash2 = MediaDataController.calcDocumentsHash(recentGifs, Integer.MAX_VALUE);
        if ((this.gifTabs != null && size == 0 && !this.recentGifs.isEmpty()) || (size != 0 && this.recentGifs.isEmpty())) {
            updateGifTabs();
        }
        if ((size == this.recentGifs.size() && calcDocumentsHash == calcDocumentsHash2) || (gifAdapter = this.gifAdapter) == null) {
            return;
        }
        gifAdapter.notifyDataSetChanged();
    }

    public void setStickersBanned(boolean z, boolean z2, long j) {
        PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
        if (pagerSlidingTabStrip == null) {
            return;
        }
        this.emojiBanned = z;
        this.stickersBanned = z2;
        if (z2 || z) {
            this.currentChatId = j;
        } else {
            this.currentChatId = 0L;
        }
        View tab = pagerSlidingTabStrip.getTab(z2 ? 2 : 0);
        if (tab != null) {
            tab.setAlpha(this.currentChatId != 0 ? 0.15f : 1.0f);
            if (z2) {
                if (this.currentChatId == 0 || this.pager.getCurrentItem() == 0) {
                    return;
                }
                showBackspaceButton(true, true);
                showStickerSettingsButton(false, true);
                this.pager.setCurrentItem(0, false);
                return;
            }
            if (this.currentChatId == 0 || this.pager.getCurrentItem() == 1) {
                return;
            }
            showBackspaceButton(false, true);
            showStickerSettingsButton(false, true);
            this.pager.setCurrentItem(1, false);
        }
    }

    public void showStickerBanHint(boolean z, final boolean z2, final boolean z3) {
        TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights;
        TLRPC$Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(this.currentChatId));
        if (chat == null) {
            return;
        }
        if (z) {
            if (ChatObject.hasAdminRights(chat) || (tLRPC$TL_chatBannedRights = chat.default_banned_rights) == null || !(tLRPC$TL_chatBannedRights.send_stickers || (z2 && tLRPC$TL_chatBannedRights.send_plain))) {
                TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights2 = chat.banned_rights;
                if (tLRPC$TL_chatBannedRights2 == null) {
                    return;
                }
                if (!AndroidUtilities.isBannedForever(tLRPC$TL_chatBannedRights2)) {
                    if (z2) {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachPlainRestricted", R.string.AttachPlainRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    }
                    if (z3) {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachGifRestricted", R.string.AttachGifRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    } else {
                        this.mediaBanTooltip.setText(LocaleController.formatString("AttachStickersRestricted", R.string.AttachStickersRestricted, LocaleController.formatDateForBan(chat.banned_rights.until_date)));
                    }
                } else if (z2) {
                    this.mediaBanTooltip.setText(LocaleController.getString("AttachPlainRestrictedForever", R.string.AttachPlainRestrictedForever));
                } else if (z3) {
                    this.mediaBanTooltip.setText(LocaleController.getString("AttachGifRestrictedForever", R.string.AttachGifRestrictedForever));
                } else {
                    this.mediaBanTooltip.setText(LocaleController.getString("AttachStickersRestrictedForever", R.string.AttachStickersRestrictedForever));
                }
            } else if (z2) {
                this.mediaBanTooltip.setText(LocaleController.getString("GlobalAttachEmojiRestricted", R.string.GlobalAttachEmojiRestricted));
            } else if (z3) {
                this.mediaBanTooltip.setText(LocaleController.getString("GlobalAttachGifRestricted", R.string.GlobalAttachGifRestricted));
            } else {
                this.mediaBanTooltip.setText(LocaleController.getString("GlobalAttachStickersRestricted", R.string.GlobalAttachStickersRestricted));
            }
            this.mediaBanTooltip.setVisibility(0);
        }
        AnimatorSet animatorSet = this.showStickersBanAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.showStickersBanAnimator = null;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.showStickersBanAnimator = animatorSet2;
        Animator[] animatorArr = new Animator[2];
        TextView textView = this.mediaBanTooltip;
        Property property = View.ALPHA;
        float[] fArr = new float[2];
        fArr[0] = z ? textView.getAlpha() : 1.0f;
        fArr[1] = z ? 1.0f : 0.0f;
        animatorArr[0] = ObjectAnimator.ofFloat(textView, (Property<TextView, Float>) property, fArr);
        TextView textView2 = this.mediaBanTooltip;
        Property property2 = View.TRANSLATION_Y;
        float[] fArr2 = new float[2];
        fArr2[0] = z ? AndroidUtilities.dp(12.0f) : textView2.getTranslationY();
        fArr2[1] = z ? 0.0f : AndroidUtilities.dp(12.0f);
        animatorArr[1] = ObjectAnimator.ofFloat(textView2, (Property<TextView, Float>) property2, fArr2);
        animatorSet2.playTogether(animatorArr);
        Runnable runnable = this.hideStickersBan;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        if (z) {
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.this.lambda$showStickerBanHint$14(z2, z3);
                }
            };
            this.hideStickersBan = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, 3500L);
        }
        this.showStickersBanAnimator.setDuration(320L);
        this.showStickersBanAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.showStickersBanAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showStickerBanHint$14(boolean z, boolean z2) {
        showStickerBanHint(false, z, z2);
    }

    private void updateVisibleTrendingSets() {
        boolean z;
        RecyclerListView recyclerListView = this.stickersGridView;
        if (recyclerListView == null) {
            return;
        }
        try {
            int childCount = recyclerListView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = this.stickersGridView.getChildAt(i);
                if ((childAt instanceof FeaturedStickerSetInfoCell) && ((RecyclerListView.Holder) this.stickersGridView.getChildViewHolder(childAt)) != null) {
                    FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) childAt;
                    ArrayList<Long> unreadStickerSets = MediaDataController.getInstance(this.currentAccount).getUnreadStickerSets();
                    TLRPC$StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
                    boolean z2 = unreadStickerSets != null && unreadStickerSets.contains(Long.valueOf(stickerSet.set.id));
                    int i2 = 0;
                    while (true) {
                        TLRPC$StickerSetCovered[] tLRPC$StickerSetCoveredArr = this.primaryInstallingStickerSets;
                        if (i2 >= tLRPC$StickerSetCoveredArr.length) {
                            z = false;
                            break;
                        } else {
                            if (tLRPC$StickerSetCoveredArr[i2] != null && tLRPC$StickerSetCoveredArr[i2].set.id == stickerSet.set.id) {
                                z = true;
                                break;
                            }
                            i2++;
                        }
                    }
                    featuredStickerSetInfoCell.setStickerSet(stickerSet, z2, true, 0, 0, z);
                    if (z2) {
                        MediaDataController.getInstance(this.currentAccount).markFeaturedStickersByIdAsRead(false, stickerSet.set.id);
                    }
                    boolean z3 = this.installingStickerSets.indexOfKey(stickerSet.set.id) >= 0;
                    boolean z4 = this.removingStickerSets.indexOfKey(stickerSet.set.id) >= 0;
                    if (z3 || z4) {
                        if (z3 && featuredStickerSetInfoCell.isInstalled()) {
                            this.installingStickerSets.remove(stickerSet.set.id);
                            z3 = false;
                        } else if (z4 && !featuredStickerSetInfoCell.isInstalled()) {
                            this.removingStickerSets.remove(stickerSet.set.id);
                        }
                    }
                    featuredStickerSetInfoCell.setAddDrawProgress(!z && z3, true);
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public boolean areThereAnyStickers() {
        StickersGridAdapter stickersGridAdapter = this.stickersGridAdapter;
        return stickersGridAdapter != null && stickersGridAdapter.getItemCount() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$15() {
        EmojiGridAdapter emojiGridAdapter = this.emojiAdapter;
        if (emojiGridAdapter != null) {
            emojiGridAdapter.notifyDataSetChanged(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        Utilities.Callback<TLRPC$TL_messages_stickerSet> remove;
        TLRPC$StickerSet tLRPC$StickerSet;
        if (i == NotificationCenter.stickersDidLoad) {
            if (((Integer) objArr[0]).intValue() == 0) {
                if (this.stickersGridAdapter != null) {
                    updateStickerTabs(((Boolean) objArr[1]).booleanValue());
                    updateVisibleTrendingSets();
                    reloadStickersAdapter();
                    checkPanels();
                    return;
                }
                return;
            }
            if (((Integer) objArr[0]).intValue() == 5) {
                this.emojiAdapter.notifyDataSetChanged(((Boolean) objArr[1]).booleanValue());
                return;
            }
            return;
        }
        if (i == NotificationCenter.recentDocumentsDidLoad) {
            boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
            int intValue = ((Integer) objArr[1]).intValue();
            if (booleanValue || intValue == 0 || intValue == 2) {
                checkDocuments(booleanValue);
                return;
            }
            return;
        }
        if (i == NotificationCenter.featuredStickersDidLoad) {
            updateVisibleTrendingSets();
            PagerSlidingTabStrip pagerSlidingTabStrip = this.typeTabs;
            if (pagerSlidingTabStrip != null) {
                int childCount = pagerSlidingTabStrip.getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    this.typeTabs.getChildAt(i3).invalidate();
                }
            }
            updateStickerTabs(false);
            return;
        }
        if (i == NotificationCenter.featuredEmojiDidLoad) {
            EmojiGridAdapter emojiGridAdapter = this.emojiAdapter;
            if (emojiGridAdapter != null) {
                emojiGridAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        if (i == NotificationCenter.groupStickersDidLoad) {
            TLRPC$ChatFull tLRPC$ChatFull = this.info;
            if (tLRPC$ChatFull != null && (tLRPC$StickerSet = tLRPC$ChatFull.stickerset) != null && tLRPC$StickerSet.id == ((Long) objArr[0]).longValue()) {
                updateStickerTabs(false);
            }
            if (this.toInstall.containsKey((Long) objArr[0]) && objArr.length >= 2) {
                long longValue = ((Long) objArr[0]).longValue();
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) objArr[1];
                if (this.toInstall.get(Long.valueOf(longValue)) != null && tLRPC$TL_messages_stickerSet != null && (remove = this.toInstall.remove(Long.valueOf(longValue))) != null) {
                    remove.run(tLRPC$TL_messages_stickerSet);
                }
            }
            AndroidUtilities.cancelRunOnUIThread(this.updateStickersLoadedDelayed);
            AndroidUtilities.runOnUIThread(this.updateStickersLoadedDelayed, 100L);
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            RecyclerListView recyclerListView = this.stickersGridView;
            if (recyclerListView != null) {
                int childCount2 = recyclerListView.getChildCount();
                for (int i4 = 0; i4 < childCount2; i4++) {
                    View childAt = this.stickersGridView.getChildAt(i4);
                    if ((childAt instanceof StickerSetNameCell) || (childAt instanceof StickerEmojiCell)) {
                        childAt.invalidate();
                    }
                }
            }
            EmojiGridView emojiGridView = this.emojiGridView;
            if (emojiGridView != null) {
                emojiGridView.invalidate();
                int childCount3 = this.emojiGridView.getChildCount();
                for (int i5 = 0; i5 < childCount3; i5++) {
                    View childAt2 = this.emojiGridView.getChildAt(i5);
                    if (childAt2 instanceof ImageViewEmoji) {
                        childAt2.invalidate();
                    }
                }
            }
            EmojiColorPickerWindow emojiColorPickerWindow = this.colorPickerView;
            if (emojiColorPickerWindow != null) {
                emojiColorPickerWindow.pickerView.invalidate();
            }
            ScrollSlidingTabStrip scrollSlidingTabStrip = this.gifTabs;
            if (scrollSlidingTabStrip != null) {
                scrollSlidingTabStrip.invalidateTabs();
                return;
            }
            return;
        }
        if (i == NotificationCenter.newEmojiSuggestionsAvailable) {
            if (this.emojiGridView == null || !this.needEmojiSearch) {
                return;
            }
            if ((this.emojiSearchField.searchStateDrawable.getIconState() == 2 || this.emojiGridView.getAdapter() == this.emojiSearchAdapter) && !TextUtils.isEmpty(this.emojiSearchAdapter.lastSearchEmojiString)) {
                EmojiSearchAdapter emojiSearchAdapter = this.emojiSearchAdapter;
                emojiSearchAdapter.search(emojiSearchAdapter.lastSearchEmojiString);
                return;
            }
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            EmojiGridAdapter emojiGridAdapter2 = this.emojiAdapter;
            if (emojiGridAdapter2 != null) {
                emojiGridAdapter2.notifyDataSetChanged();
            }
            updateEmojiHeaders();
            updateStickerTabs(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemedColor(int i) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null) {
            return resourcesProvider.getColor(i);
        }
        return Theme.getColor(i);
    }

    private class TrendingAdapter extends RecyclerListView.SelectionAdapter {
        private boolean emoji;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        public TrendingAdapter(boolean z) {
            this.emoji = z;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            BackupImageView backupImageView = new BackupImageView(EmojiView.this.getContext()) { // from class: org.telegram.ui.Components.EmojiView.TrendingAdapter.1
                @Override // org.telegram.ui.Components.BackupImageView, android.view.View
                protected void onDraw(Canvas canvas) {
                    super.onDraw(canvas);
                    if (TrendingAdapter.this.emoji) {
                        return;
                    }
                    if (!MediaDataController.getInstance(EmojiView.this.currentAccount).isStickerPackUnread(TrendingAdapter.this.emoji, ((TLRPC$StickerSetCovered) getTag()).set.id) || EmojiView.this.dotPaint == null) {
                        return;
                    }
                    canvas.drawCircle(canvas.getWidth() - AndroidUtilities.dp(8.0f), AndroidUtilities.dp(14.0f), AndroidUtilities.dp(3.0f), EmojiView.this.dotPaint);
                }
            };
            backupImageView.setSize(AndroidUtilities.dp(this.emoji ? 24.0f : 30.0f), AndroidUtilities.dp(this.emoji ? 24.0f : 30.0f));
            backupImageView.setLayerNum(1);
            backupImageView.setAspectFit(true);
            backupImageView.setLayoutParams(new RecyclerView.LayoutParams(AndroidUtilities.dp(this.emoji ? 34.0f : 42.0f), AndroidUtilities.dp(this.emoji ? 34.0f : 42.0f)));
            return new RecyclerListView.Holder(backupImageView);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ArrayList<TLRPC$Document> arrayList;
            ImageLocation forSticker;
            BackupImageView backupImageView = (BackupImageView) viewHolder.itemView;
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered = (TLRPC$StickerSetCovered) (this.emoji ? EmojiView.this.featuredEmojiSets : EmojiView.this.featuredStickerSets).get(i);
            backupImageView.setTag(tLRPC$StickerSetCovered);
            if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetFullCovered) {
                arrayList = ((TLRPC$TL_stickerSetFullCovered) tLRPC$StickerSetCovered).documents;
            } else if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetNoCovered) {
                TLRPC$TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(MediaDataController.getInputStickerSet(tLRPC$StickerSetCovered.set), false);
                arrayList = stickerSet == null ? null : stickerSet.documents;
            } else {
                arrayList = tLRPC$StickerSetCovered.covers;
            }
            TLRPC$Document tLRPC$Document = tLRPC$StickerSetCovered.cover;
            if (tLRPC$Document == null) {
                if (arrayList == null || arrayList.isEmpty()) {
                    tLRPC$Document = null;
                } else {
                    if (tLRPC$StickerSetCovered.set != null) {
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            if (arrayList.get(i2).id == tLRPC$StickerSetCovered.set.thumb_document_id) {
                                tLRPC$Document = arrayList.get(i2);
                                break;
                            }
                        }
                    }
                    tLRPC$Document = null;
                    if (tLRPC$Document == null) {
                        tLRPC$Document = arrayList.get(0);
                    }
                }
            }
            if (tLRPC$Document == null) {
                return;
            }
            if (this.emoji) {
                backupImageView.setColorFilter(MessageObject.isTextColorEmoji(tLRPC$Document) ? Theme.chat_animatedEmojiTextColorFilter : null);
            }
            TLObject closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tLRPC$StickerSetCovered.set.thumbs, 90);
            SvgHelper.SvgDrawable svgThumb = DocumentObject.getSvgThumb(tLRPC$StickerSetCovered.set.thumbs, Theme.key_emptyListPlaceholder, 0.2f);
            if (svgThumb != null) {
                svgThumb.overrideWidthAndHeight(LiteMode.FLAG_CALLS_ANIMATIONS, LiteMode.FLAG_CALLS_ANIMATIONS);
            }
            if (closestPhotoSizeWithSize == null || MessageObject.isVideoSticker(tLRPC$Document)) {
                closestPhotoSizeWithSize = tLRPC$Document;
            }
            boolean z = closestPhotoSizeWithSize instanceof TLRPC$Document;
            if (z) {
                forSticker = ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(tLRPC$Document.thumbs, 90), tLRPC$Document);
            } else if (!(closestPhotoSizeWithSize instanceof TLRPC$PhotoSize)) {
                return;
            } else {
                forSticker = ImageLocation.getForSticker((TLRPC$PhotoSize) closestPhotoSizeWithSize, tLRPC$Document, tLRPC$StickerSetCovered.set.thumb_version);
            }
            if (forSticker == null) {
                return;
            }
            String str = !LiteMode.isEnabled(this.emoji ? LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD : 1) ? "30_30_firstframe" : "30_30";
            if (z && (MessageObject.isAnimatedStickerDocument(tLRPC$Document, true) || MessageObject.isVideoSticker(tLRPC$Document))) {
                if (svgThumb != null) {
                    backupImageView.setImage(ImageLocation.getForDocument(tLRPC$Document), str, svgThumb, 0, tLRPC$StickerSetCovered);
                    return;
                } else {
                    backupImageView.setImage(ImageLocation.getForDocument(tLRPC$Document), str, forSticker, (String) null, 0, tLRPC$StickerSetCovered);
                    return;
                }
            }
            if (forSticker.imageType == 1) {
                backupImageView.setImage(forSticker, str, "tgs", svgThumb, tLRPC$StickerSetCovered);
            } else {
                backupImageView.setImage(forSticker, (String) null, "webp", svgThumb, tLRPC$StickerSetCovered);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return (this.emoji ? EmojiView.this.featuredEmojiSets : EmojiView.this.featuredStickerSets).size();
        }
    }

    private class TrendingListView extends RecyclerListView {
        public TrendingListView(Context context, RecyclerView.Adapter adapter) {
            super(context);
            setNestedScrollingEnabled(true);
            setSelectorRadius(AndroidUtilities.dp(4.0f));
            setSelectorDrawableColor(getThemedColor(Theme.key_listSelector));
            setTag(9);
            setItemAnimator(null);
            setLayoutAnimation(null);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, context, EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.TrendingListView.1
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(0);
            setLayoutManager(linearLayoutManager);
            setAdapter(adapter);
        }

        @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (getParent() != null && getParent().getParent() != null) {
                getParent().getParent().requestDisallowInterceptTouchEvent(canScrollHorizontally(-1) || canScrollHorizontally(1));
                EmojiView.this.pager.requestDisallowInterceptTouchEvent(true);
            }
            return super.onInterceptTouchEvent(motionEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StickersGridAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private int stickersPerRow;
        private int totalItems;
        private SparseArray<Object> rowStartPack = new SparseArray<>();
        private HashMap<Object, Integer> packStartPosition = new HashMap<>();
        private SparseArray<Object> cache = new SparseArray<>();
        private SparseArray<Object> cacheParents = new SparseArray<>();
        private SparseIntArray positionToRow = new SparseIntArray();

        public StickersGridAdapter(Context context) {
            this.context = context;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.itemView instanceof RecyclerListView;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int i = this.totalItems;
            if (i != 0) {
                return i + 1;
            }
            return 0;
        }

        public int getPositionForPack(Object obj) {
            Integer num = this.packStartPosition.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 4;
            }
            Object obj = this.cache.get(i);
            if (obj == null) {
                return 1;
            }
            if (obj instanceof TLRPC$Document) {
                return 0;
            }
            if (!(obj instanceof String)) {
                return 2;
            }
            if ("trend1".equals(obj)) {
                return 5;
            }
            return "trend2".equals(obj) ? 6 : 3;
        }

        public int getTabForPosition(int i) {
            Object obj = this.cache.get(i);
            if ("search".equals(obj) || "trend1".equals(obj) || "trend2".equals(obj)) {
                if (EmojiView.this.favTabNum >= 0) {
                    return EmojiView.this.favTabNum;
                }
                if (EmojiView.this.recentTabNum >= 0) {
                    return EmojiView.this.recentTabNum;
                }
                return 0;
            }
            if (i == 0) {
                i = 1;
            }
            if (this.stickersPerRow == 0) {
                int measuredWidth = EmojiView.this.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            }
            int i2 = this.positionToRow.get(i, LinearLayoutManager.INVALID_OFFSET);
            if (i2 == Integer.MIN_VALUE) {
                return (EmojiView.this.stickerSets.size() - 1) + EmojiView.this.stickersTabOffset;
            }
            Object obj2 = this.rowStartPack.get(i2);
            if (!(obj2 instanceof String)) {
                return EmojiView.this.stickerSets.indexOf((TLRPC$TL_messages_stickerSet) obj2) + EmojiView.this.stickersTabOffset;
            }
            if ("premium".equals(obj2)) {
                return EmojiView.this.premiumTabNum;
            }
            return "recent".equals(obj2) ? EmojiView.this.recentTabNum : EmojiView.this.favTabNum;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$1(StickerSetNameCell stickerSetNameCell, View view) {
            RecyclerView.ViewHolder childViewHolder;
            if (EmojiView.this.stickersGridView.indexOfChild(stickerSetNameCell) == -1 || (childViewHolder = EmojiView.this.stickersGridView.getChildViewHolder(stickerSetNameCell)) == null) {
                return;
            }
            if (childViewHolder.getAdapterPosition() == EmojiView.this.groupStickerPackPosition) {
                if (EmojiView.this.groupStickerSet != null) {
                    if (EmojiView.this.delegate != null) {
                        EmojiView.this.delegate.onStickersGroupClick(EmojiView.this.info.id);
                        return;
                    }
                    return;
                }
                MessagesController.getEmojiSettings(EmojiView.this.currentAccount).edit().putLong("group_hide_stickers_" + EmojiView.this.info.id, EmojiView.this.info.stickerset != null ? EmojiView.this.info.stickerset.id : 0L).apply();
                EmojiView.this.updateStickerTabs(false);
                if (EmojiView.this.stickersGridAdapter != null) {
                    EmojiView.this.stickersGridAdapter.notifyDataSetChanged();
                    return;
                }
                return;
            }
            if (this.cache.get(childViewHolder.getAdapterPosition()) == EmojiView.this.recentStickers) {
                AlertDialog create = new AlertDialog.Builder(this.context).setTitle(LocaleController.getString(R.string.ClearRecentStickersAlertTitle)).setMessage(LocaleController.getString(R.string.ClearRecentStickersAlertMessage)).setPositiveButton(LocaleController.getString(R.string.ClearButton), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        EmojiView.StickersGridAdapter.this.lambda$onCreateViewHolder$0(dialogInterface, i);
                    }
                }).setNegativeButton(LocaleController.getString(R.string.Cancel), null).create();
                create.show();
                TextView textView = (TextView) create.getButton(-1);
                if (textView != null) {
                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$0(DialogInterface dialogInterface, int i) {
            MediaDataController.getInstance(EmojiView.this.currentAccount).clearRecentStickers();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$2(View view) {
            if (EmojiView.this.delegate != null) {
                EmojiView.this.delegate.onStickersGroupClick(EmojiView.this.info.id);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$3(View view) {
            ArrayList<TLRPC$StickerSetCovered> featuredStickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedStickerSets();
            if (featuredStickerSets.isEmpty()) {
                return;
            }
            MessagesController.getEmojiSettings(EmojiView.this.currentAccount).edit().putLong("featured_hidden", featuredStickerSets.get(0).set.id).commit();
            if (EmojiView.this.stickersGridAdapter != null) {
                EmojiView.this.stickersGridAdapter.notifyItemRangeRemoved(1, 2);
            }
            EmojiView.this.updateStickerTabs(false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v4, types: [android.view.View, org.telegram.ui.Cells.StickerSetGroupInfoCell] */
        /* JADX WARN: Type inference failed for: r6v5, types: [android.view.View] */
        /* JADX WARN: Type inference failed for: r6v7, types: [android.view.View, androidx.recyclerview.widget.RecyclerView, org.telegram.ui.Components.EmojiView$TrendingListView, org.telegram.ui.Components.RecyclerListView] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @SuppressLint({"NotifyDataSetChanged"})
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FrameLayout frameLayout;
            switch (i) {
                case 0:
                    frameLayout = new StickerEmojiCell(this, this.context, true) { // from class: org.telegram.ui.Components.EmojiView.StickersGridAdapter.1
                        @Override // android.widget.FrameLayout, android.view.View
                        public void onMeasure(int i2, int i3) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                        }
                    };
                    break;
                case 1:
                    frameLayout = new EmptyCell(this.context);
                    break;
                case 2:
                    final StickerSetNameCell stickerSetNameCell = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider);
                    stickerSetNameCell.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            EmojiView.StickersGridAdapter.this.lambda$onCreateViewHolder$1(stickerSetNameCell, view);
                        }
                    });
                    frameLayout = stickerSetNameCell;
                    break;
                case 3:
                    ?? stickerSetGroupInfoCell = new StickerSetGroupInfoCell(this.context);
                    stickerSetGroupInfoCell.setAddOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            EmojiView.StickersGridAdapter.this.lambda$onCreateViewHolder$2(view);
                        }
                    });
                    stickerSetGroupInfoCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                    frameLayout = stickerSetGroupInfoCell;
                    break;
                case 4:
                    ?? view = new View(this.context);
                    view.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    frameLayout = view;
                    break;
                case 5:
                    StickerSetNameCell stickerSetNameCell2 = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider);
                    stickerSetNameCell2.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            EmojiView.StickersGridAdapter.this.lambda$onCreateViewHolder$3(view2);
                        }
                    });
                    frameLayout = stickerSetNameCell2;
                    break;
                case 6:
                    EmojiView emojiView = EmojiView.this;
                    ?? trendingListView = emojiView.new TrendingListView(this.context, emojiView.trendingAdapter = emojiView.new TrendingAdapter(false));
                    trendingListView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: org.telegram.ui.Components.EmojiView.StickersGridAdapter.2
                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public void getItemOffsets(android.graphics.Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
                            rect.right = AndroidUtilities.dp(8.0f);
                        }
                    });
                    trendingListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersGridAdapter$$ExternalSyntheticLambda4
                        @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                        public final void onItemClick(View view2, int i2) {
                            EmojiView.StickersGridAdapter.this.lambda$onCreateViewHolder$4(view2, i2);
                        }
                    });
                    trendingListView.setLayoutParams(new RecyclerView.LayoutParams(-1, AndroidUtilities.dp(52.0f)));
                    frameLayout = trendingListView;
                    break;
                default:
                    frameLayout = null;
                    break;
            }
            return new RecyclerListView.Holder(frameLayout);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$4(View view, int i) {
            EmojiView.this.openTrendingStickers((TLRPC$StickerSetCovered) view.getTag());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int i2;
            int i3;
            String str;
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 0) {
                TLRPC$Document tLRPC$Document = (TLRPC$Document) this.cache.get(i);
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) viewHolder.itemView;
                stickerEmojiCell.setSticker(tLRPC$Document, this.cacheParents.get(i), false);
                stickerEmojiCell.setRecent(EmojiView.this.recentStickers.contains(tLRPC$Document));
                return;
            }
            ArrayList<TLRPC$Document> arrayList = null;
            if (itemViewType == 1) {
                EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                if (i == this.totalItems) {
                    int i4 = this.positionToRow.get(i - 1, LinearLayoutManager.INVALID_OFFSET);
                    if (i4 == Integer.MIN_VALUE) {
                        emptyCell.setHeight(1);
                        return;
                    }
                    Object obj = this.rowStartPack.get(i4);
                    if (obj instanceof TLRPC$TL_messages_stickerSet) {
                        arrayList = ((TLRPC$TL_messages_stickerSet) obj).documents;
                    } else if (obj instanceof String) {
                        arrayList = "recent".equals(obj) ? EmojiView.this.recentStickers : EmojiView.this.favouriteStickers;
                    }
                    if (arrayList == null) {
                        emptyCell.setHeight(1);
                        return;
                    } else if (!arrayList.isEmpty()) {
                        int height = EmojiView.this.pager.getHeight() - (((int) Math.ceil(arrayList.size() / this.stickersPerRow)) * AndroidUtilities.dp(82.0f));
                        emptyCell.setHeight(height > 0 ? height : 1);
                        return;
                    } else {
                        emptyCell.setHeight(AndroidUtilities.dp(8.0f));
                        return;
                    }
                }
                emptyCell.setHeight(AndroidUtilities.dp(82.0f));
                return;
            }
            if (itemViewType != 2) {
                if (itemViewType == 3) {
                    ((StickerSetGroupInfoCell) viewHolder.itemView).setIsLast(i == this.totalItems - 1);
                    return;
                }
                if (itemViewType != 5) {
                    return;
                }
                StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                if (MediaDataController.getInstance(EmojiView.this.currentAccount).loadFeaturedPremium) {
                    i3 = R.string.FeaturedStickersPremium;
                    str = "FeaturedStickersPremium";
                } else {
                    i3 = R.string.FeaturedStickers;
                    str = "FeaturedStickers";
                }
                stickerSetNameCell.setText(LocaleController.getString(str, i3), R.drawable.msg_close, LocaleController.getString("AccDescrCloseTrendingStickers", R.string.AccDescrCloseTrendingStickers));
                return;
            }
            StickerSetNameCell stickerSetNameCell2 = (StickerSetNameCell) viewHolder.itemView;
            if (i == EmojiView.this.groupStickerPackPosition) {
                if (EmojiView.this.groupStickersHidden && EmojiView.this.groupStickerSet == null) {
                    i2 = 0;
                } else {
                    i2 = EmojiView.this.groupStickerSet != null ? R.drawable.msg_mini_customize : R.drawable.msg_close;
                }
                TLRPC$Chat chat = EmojiView.this.info != null ? MessagesController.getInstance(EmojiView.this.currentAccount).getChat(Long.valueOf(EmojiView.this.info.id)) : null;
                int i5 = R.string.CurrentGroupStickers;
                Object[] objArr = new Object[1];
                objArr[0] = chat != null ? chat.title : "Group Stickers";
                stickerSetNameCell2.setText(LocaleController.formatString("CurrentGroupStickers", i5, objArr), i2);
                return;
            }
            Object obj2 = this.cache.get(i);
            if (!(obj2 instanceof TLRPC$TL_messages_stickerSet)) {
                if (obj2 != EmojiView.this.recentStickers) {
                    if (obj2 != EmojiView.this.favouriteStickers) {
                        if (obj2 == EmojiView.this.premiumStickers) {
                            stickerSetNameCell2.setText(LocaleController.getString("PremiumStickers", R.string.PremiumStickers), 0);
                            return;
                        }
                        return;
                    }
                    stickerSetNameCell2.setText(LocaleController.getString("FavoriteStickers", R.string.FavoriteStickers), 0);
                    return;
                }
                stickerSetNameCell2.setText(LocaleController.getString("RecentStickers", R.string.RecentStickers), R.drawable.msg_close, LocaleController.getString(R.string.ClearRecentStickersAlertTitle));
                return;
            }
            TLRPC$StickerSet tLRPC$StickerSet = ((TLRPC$TL_messages_stickerSet) obj2).set;
            if (tLRPC$StickerSet != null) {
                stickerSetNameCell2.setText(tLRPC$StickerSet.title, 0);
            }
        }

        private void updateItems() {
            ArrayList<TLRPC$Document> arrayList;
            Object obj;
            int i;
            int measuredWidth = EmojiView.this.getMeasuredWidth();
            if (measuredWidth == 0) {
                measuredWidth = AndroidUtilities.displaySize.x;
            }
            this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
            EmojiView.this.stickersLayoutManager.setSpanCount(this.stickersPerRow);
            this.rowStartPack.clear();
            this.packStartPosition.clear();
            this.positionToRow.clear();
            this.cache.clear();
            int i2 = 0;
            this.totalItems = 0;
            ArrayList arrayList2 = EmojiView.this.stickerSets;
            int i3 = -5;
            int i4 = -5;
            int i5 = 0;
            while (i4 < arrayList2.size()) {
                if (i4 == i3) {
                    SparseArray<Object> sparseArray = this.cache;
                    int i6 = this.totalItems;
                    this.totalItems = i6 + 1;
                    sparseArray.put(i6, "search");
                    i5++;
                } else if (i4 != -4) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = null;
                    if (i4 == -3) {
                        arrayList = EmojiView.this.favouriteStickers;
                        this.packStartPosition.put("fav", Integer.valueOf(this.totalItems));
                        obj = "fav";
                    } else if (i4 == -2) {
                        arrayList = EmojiView.this.recentStickers;
                        this.packStartPosition.put("recent", Integer.valueOf(this.totalItems));
                        obj = "recent";
                    } else if (i4 != -1) {
                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2 = (TLRPC$TL_messages_stickerSet) arrayList2.get(i4);
                        ArrayList<TLRPC$Document> arrayList3 = tLRPC$TL_messages_stickerSet2.documents;
                        this.packStartPosition.put(tLRPC$TL_messages_stickerSet2, Integer.valueOf(this.totalItems));
                        tLRPC$TL_messages_stickerSet = tLRPC$TL_messages_stickerSet2;
                        arrayList = arrayList3;
                        obj = null;
                    }
                    if (i4 == EmojiView.this.groupStickerPackNum) {
                        EmojiView.this.groupStickerPackPosition = this.totalItems;
                        if (arrayList.isEmpty()) {
                            this.rowStartPack.put(i5, tLRPC$TL_messages_stickerSet);
                            int i7 = i5 + 1;
                            this.positionToRow.put(this.totalItems, i5);
                            this.rowStartPack.put(i7, tLRPC$TL_messages_stickerSet);
                            this.positionToRow.put(this.totalItems + 1, i7);
                            SparseArray<Object> sparseArray2 = this.cache;
                            int i8 = this.totalItems;
                            this.totalItems = i8 + 1;
                            sparseArray2.put(i8, tLRPC$TL_messages_stickerSet);
                            SparseArray<Object> sparseArray3 = this.cache;
                            int i9 = this.totalItems;
                            this.totalItems = i9 + 1;
                            sparseArray3.put(i9, "group");
                            i5 = i7 + 1;
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        int ceil = (int) Math.ceil(arrayList.size() / this.stickersPerRow);
                        if (tLRPC$TL_messages_stickerSet != null) {
                            this.cache.put(this.totalItems, tLRPC$TL_messages_stickerSet);
                        } else {
                            this.cache.put(this.totalItems, arrayList);
                        }
                        this.positionToRow.put(this.totalItems, i5);
                        int i10 = 0;
                        while (i10 < arrayList.size()) {
                            int i11 = i10 + 1;
                            int i12 = this.totalItems + i11;
                            this.cache.put(i12, arrayList.get(i10));
                            if (tLRPC$TL_messages_stickerSet != null) {
                                this.cacheParents.put(i12, tLRPC$TL_messages_stickerSet);
                            } else {
                                this.cacheParents.put(i12, obj);
                            }
                            this.positionToRow.put(this.totalItems + i11, i5 + 1 + (i10 / this.stickersPerRow));
                            i10 = i11;
                        }
                        int i13 = 0;
                        while (true) {
                            i = ceil + 1;
                            if (i13 >= i) {
                                break;
                            }
                            if (tLRPC$TL_messages_stickerSet != null) {
                                this.rowStartPack.put(i5 + i13, tLRPC$TL_messages_stickerSet);
                            } else if (i4 == -1) {
                                this.rowStartPack.put(i5 + i13, "premium");
                            } else {
                                if (i4 == -2) {
                                    this.rowStartPack.put(i5 + i13, "recent");
                                } else {
                                    this.rowStartPack.put(i5 + i13, "fav");
                                }
                                i13++;
                            }
                            i13++;
                        }
                        this.totalItems += (ceil * this.stickersPerRow) + 1;
                        i5 += i;
                    }
                } else {
                    MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                    SharedPreferences emojiSettings = MessagesController.getEmojiSettings(EmojiView.this.currentAccount);
                    ArrayList<TLRPC$StickerSetCovered> featuredStickerSets = mediaDataController.getFeaturedStickerSets();
                    if (!EmojiView.this.featuredStickerSets.isEmpty() && emojiSettings.getLong("featured_hidden", 0L) != featuredStickerSets.get(i2).set.id) {
                        SparseArray<Object> sparseArray4 = this.cache;
                        int i14 = this.totalItems;
                        this.totalItems = i14 + 1;
                        sparseArray4.put(i14, "trend1");
                        SparseArray<Object> sparseArray5 = this.cache;
                        int i15 = this.totalItems;
                        this.totalItems = i15 + 1;
                        sparseArray5.put(i15, "trend2");
                        i5 += 2;
                    }
                }
                i4++;
                i2 = 0;
                i3 = -5;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyItemRangeRemoved(int i, int i2) {
            updateItems();
            super.notifyItemRangeRemoved(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            updateItems();
            super.notifyDataSetChanged();
        }
    }

    public static class EmojiPackExpand extends FrameLayout {
        public TextView textView;

        public EmojiPackExpand(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextSize(1, 13.0f);
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider));
            this.textView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.dp(11.0f), ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_chat_emojiPanelStickerSetName, resourcesProvider), 99)));
            this.textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.textView.setPadding(AndroidUtilities.dp(6.0f), AndroidUtilities.dp(1.66f), AndroidUtilities.dp(6.0f), AndroidUtilities.dp(2.0f));
            addView(this.textView, LayoutHelper.createFrame(-2, -2, 17));
        }
    }

    public static class CustomEmoji {
        public long documentId;
        public TLRPC$TL_messages_stickerSet stickerSet;

        public TLRPC$Document getDocument() {
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = this.stickerSet;
            if (tLRPC$TL_messages_stickerSet != null && tLRPC$TL_messages_stickerSet.documents != null) {
                for (int i = 0; i < this.stickerSet.documents.size(); i++) {
                    TLRPC$Document tLRPC$Document = this.stickerSet.documents.get(i);
                    if (tLRPC$Document != null && tLRPC$Document.id == this.documentId) {
                        return tLRPC$Document;
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EmojiGridAdapter extends RecyclerListView.SelectionAdapter {
        private int firstTrendingRow;
        private ArrayList<TLRPC$TL_messages_stickerSet> frozenEmojiPacks;
        private int itemCount;
        private ArrayList<Integer> packStartPosition;
        public int plainEmojisCount;
        private SparseIntArray positionToExpand;
        private SparseIntArray positionToSection;
        private SparseIntArray positionToUnlock;
        private int recentlyUsedHeaderRow;
        private ArrayList<Integer> rowHashCodes;
        private SparseIntArray sectionToPosition;
        private int trendingHeaderRow;
        private int trendingRow;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            return i;
        }

        private EmojiGridAdapter() {
            this.trendingHeaderRow = -1;
            this.trendingRow = -1;
            this.firstTrendingRow = -1;
            this.recentlyUsedHeaderRow = -1;
            this.rowHashCodes = new ArrayList<>();
            this.positionToSection = new SparseIntArray();
            this.sectionToPosition = new SparseIntArray();
            this.positionToUnlock = new SparseIntArray();
            this.positionToExpand = new SparseIntArray();
            this.packStartPosition = new ArrayList<>();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.itemCount;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 4 || itemViewType == 3 || itemViewType == 6;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            if (EmojiView.this.featuredEmojiSets == null || EmojiView.this.featuredEmojiSets.isEmpty() || ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set == null) {
                return;
            }
            MessagesController.getEmojiSettings(EmojiView.this.currentAccount).edit().putLong("emoji_featured_hidden", ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.id).commit();
            if (EmojiView.this.emojiAdapter != null) {
                EmojiView.this.emojiAdapter.notifyItemRangeRemoved(1, 3);
            }
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
            }
            updateRows();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$1(View view, int i) {
            TLRPC$StickerSet tLRPC$StickerSet;
            if (view.getTag() instanceof TLRPC$StickerSetCovered) {
                TLRPC$StickerSetCovered tLRPC$StickerSetCovered = (TLRPC$StickerSetCovered) view.getTag();
                ArrayList arrayList = new ArrayList();
                ArrayList<TLRPC$StickerSetCovered> featuredEmojiSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedEmojiSets();
                int i2 = -1;
                for (int i3 = 0; i3 < featuredEmojiSets.size(); i3++) {
                    TLRPC$StickerSetCovered tLRPC$StickerSetCovered2 = featuredEmojiSets.get(i3);
                    if (tLRPC$StickerSetCovered2 != null && tLRPC$StickerSetCovered2.set != null) {
                        TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                        TLRPC$StickerSet tLRPC$StickerSet2 = tLRPC$StickerSetCovered2.set;
                        tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSet2.id;
                        tLRPC$TL_inputStickerSetID.access_hash = tLRPC$StickerSet2.access_hash;
                        arrayList.add(tLRPC$TL_inputStickerSetID);
                        if (tLRPC$StickerSetCovered != null && (tLRPC$StickerSet = tLRPC$StickerSetCovered.set) != null && tLRPC$StickerSet.id == tLRPC$StickerSetCovered2.set.id) {
                            i2 = i3;
                        }
                    }
                }
                MediaDataController.getInstance(EmojiView.this.currentAccount).markFeaturedStickersAsRead(true, true);
                EmojiPacksAlert emojiPacksAlert = new EmojiPacksAlert(EmojiView.this.fragment, EmojiView.this.getContext(), EmojiView.this.fragment == null ? null : EmojiView.this.fragment.getResourceProvider(), arrayList);
                if (i2 >= 0) {
                    emojiPacksAlert.highlight(i2);
                }
                if (EmojiView.this.fragment != null) {
                    EmojiView.this.fragment.showDialog(emojiPacksAlert);
                } else {
                    emojiPacksAlert.show();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r6v7, types: [android.view.ViewGroup, androidx.recyclerview.widget.RecyclerView, org.telegram.ui.Components.EmojiView$TrendingListView, org.telegram.ui.Components.RecyclerListView] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View imageViewEmoji;
            StickerSetNameCell stickerSetNameCell;
            if (i != 0) {
                if (i == 1) {
                    StickerSetNameCell stickerSetNameCell2 = new StickerSetNameCell(EmojiView.this.getContext(), true, EmojiView.this.resourcesProvider);
                    stickerSetNameCell2.setOnIconClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            EmojiView.EmojiGridAdapter.this.lambda$onCreateViewHolder$0(view);
                        }
                    });
                    stickerSetNameCell = stickerSetNameCell2;
                } else if (i == 3) {
                    EmojiView emojiView = EmojiView.this;
                    imageViewEmoji = new EmojiPackButton(emojiView, emojiView.getContext());
                } else if (i == 4) {
                    EmojiView emojiView2 = EmojiView.this;
                    Context context = emojiView2.getContext();
                    EmojiView emojiView3 = EmojiView.this;
                    ?? trendingListView = emojiView2.new TrendingListView(context, emojiView3.trendingEmojiAdapter = emojiView3.new TrendingAdapter(true));
                    trendingListView.setPadding(AndroidUtilities.dp(8.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(8.0f), 0);
                    trendingListView.setClipToPadding(false);
                    trendingListView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: org.telegram.ui.Components.EmojiView.EmojiGridAdapter.1
                        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                        public void getItemOffsets(android.graphics.Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                            rect.right = AndroidUtilities.dp(2.0f);
                        }
                    });
                    trendingListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda2
                        @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                        public final void onItemClick(View view, int i2) {
                            EmojiView.EmojiGridAdapter.this.lambda$onCreateViewHolder$1(view, i2);
                        }
                    });
                    stickerSetNameCell = trendingListView;
                } else if (i == 5) {
                    EmojiView emojiView4 = EmojiView.this;
                    imageViewEmoji = emojiView4.new EmojiPackHeader(emojiView4.getContext());
                } else if (i == 6) {
                    imageViewEmoji = new EmojiPackExpand(EmojiView.this.getContext(), EmojiView.this.resourcesProvider);
                } else {
                    imageViewEmoji = new View(EmojiView.this.getContext());
                    imageViewEmoji.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                }
                imageViewEmoji = stickerSetNameCell;
            } else {
                imageViewEmoji = new ImageViewEmoji(EmojiView.this.getContext());
            }
            return new RecyclerListView.Holder(imageViewEmoji);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, @SuppressLint({"RecyclerView"}) int i) {
            String str;
            String str2;
            Long l;
            TLRPC$Document tLRPC$Document;
            String str3;
            TLRPC$Document tLRPC$Document2;
            String str4;
            int itemViewType = viewHolder.getItemViewType();
            boolean z = true;
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                    stickerSetNameCell.position = i;
                    int i2 = this.positionToSection.get(i);
                    if (i == this.trendingHeaderRow) {
                        stickerSetNameCell.setText(LocaleController.getString("FeaturedEmojiPacks", R.string.FeaturedEmojiPacks), R.drawable.msg_close, LocaleController.getString("AccDescrCloseTrendingEmoji", R.string.AccDescrCloseTrendingEmoji));
                        return;
                    }
                    if (i != this.recentlyUsedHeaderRow) {
                        if (i2 >= EmojiView.this.emojiTitles.length) {
                            try {
                                stickerSetNameCell.setText(((EmojiPack) EmojiView.this.emojipacksProcessed.get(i2 - EmojiView.this.emojiTitles.length)).set.title, 0);
                                return;
                            } catch (Exception unused) {
                                stickerSetNameCell.setText("", 0);
                                return;
                            }
                        }
                        stickerSetNameCell.setText(EmojiView.this.emojiTitles[i2], 0);
                        return;
                    }
                    stickerSetNameCell.setText(LocaleController.getString("RecentlyUsed", R.string.RecentlyUsed), 0);
                    return;
                }
                if (itemViewType == 5) {
                    EmojiPackHeader emojiPackHeader = (EmojiPackHeader) viewHolder.itemView;
                    int length = this.positionToSection.get(i) - EmojiView.this.emojiTitles.length;
                    EmojiPack emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(length);
                    int i3 = length - 1;
                    r3 = i3 >= 0 ? (EmojiPack) EmojiView.this.emojipacksProcessed.get(i3) : null;
                    if (emojiPack == null || !emojiPack.featured || (r3 != null && !r3.free && r3.installed && !UserConfig.getInstance(EmojiView.this.currentAccount).isPremium())) {
                        z = false;
                    }
                    emojiPackHeader.setStickerSet(emojiPack, z);
                    return;
                }
                if (itemViewType != 6) {
                    return;
                }
                EmojiPackExpand emojiPackExpand = (EmojiPackExpand) viewHolder.itemView;
                int i4 = this.positionToExpand.get(i);
                int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
                if (i4 >= 0 && i4 < EmojiView.this.emojipacksProcessed.size()) {
                    r3 = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i4);
                }
                if (r3 != null) {
                    emojiPackExpand.textView.setText("+" + ((r3.documents.size() - spanCount) + 1));
                    return;
                }
                return;
            }
            ImageViewEmoji imageViewEmoji = (ImageViewEmoji) viewHolder.itemView;
            imageViewEmoji.position = i;
            imageViewEmoji.pack = null;
            if (EmojiView.this.needEmojiSearch) {
                i--;
            }
            if (this.recentlyUsedHeaderRow >= 0) {
                i--;
            }
            if (this.trendingRow >= 0) {
                i -= 2;
            }
            int size = EmojiView.this.getRecentEmoji().size();
            if (i < size) {
                String str5 = EmojiView.this.getRecentEmoji().get(i);
                if (str5 != null && str5.startsWith("animated_")) {
                    try {
                        l = Long.valueOf(Long.parseLong(str5.substring(9)));
                        str = null;
                        str4 = null;
                    } catch (Exception unused2) {
                    }
                    str3 = str4;
                    tLRPC$Document2 = null;
                }
                str = str5;
                str4 = str;
                l = null;
                str3 = str4;
                tLRPC$Document2 = null;
            } else {
                int i5 = 0;
                while (true) {
                    String[][] strArr = EmojiData.dataColored;
                    if (i5 >= strArr.length) {
                        str = null;
                        break;
                    }
                    int length2 = strArr[i5].length + 1;
                    int i6 = (i - size) - 1;
                    if (i6 < 0 || i >= size + length2) {
                        size += length2;
                        i5++;
                    } else {
                        String str6 = strArr[i5][i6];
                        String str7 = Emoji.emojiColor.get(str6);
                        if (str7 != null) {
                            str = EmojiView.addColorToCode(str6, str7);
                            str2 = str6;
                        } else {
                            str = str6;
                        }
                    }
                }
                str2 = str;
                if (str2 == null) {
                    boolean isPremium = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium();
                    int spanCount2 = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
                    for (int i7 = 0; i7 < this.packStartPosition.size(); i7++) {
                        EmojiPack emojiPack2 = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i7);
                        int intValue = this.packStartPosition.get(i7).intValue() + 1;
                        int size2 = ((emojiPack2.installed && !emojiPack2.featured && (emojiPack2.free || isPremium)) || emojiPack2.expanded) ? emojiPack2.documents.size() : Math.min(spanCount2, emojiPack2.documents.size());
                        int i8 = imageViewEmoji.position;
                        if (i8 >= intValue && i8 - intValue < size2) {
                            imageViewEmoji.pack = emojiPack2;
                            TLRPC$Document tLRPC$Document3 = emojiPack2.documents.get(imageViewEmoji.position - intValue);
                            tLRPC$Document = tLRPC$Document3;
                            l = tLRPC$Document3 == null ? null : Long.valueOf(tLRPC$Document3.id);
                            str3 = str2;
                            tLRPC$Document2 = tLRPC$Document;
                            z = false;
                        }
                    }
                }
                l = null;
                tLRPC$Document = null;
                str3 = str2;
                tLRPC$Document2 = tLRPC$Document;
                z = false;
            }
            if (l != null) {
                imageViewEmoji.setPadding(AndroidUtilities.dp(3.0f), AndroidUtilities.dp(3.0f), AndroidUtilities.dp(3.0f), AndroidUtilities.dp(3.0f));
            } else {
                imageViewEmoji.setPadding(0, 0, 0, 0);
            }
            if (l != null) {
                imageViewEmoji.setImageDrawable(null, z);
                if (imageViewEmoji.getSpan() == null || imageViewEmoji.getSpan().getDocumentId() != l.longValue()) {
                    if (tLRPC$Document2 != null) {
                        imageViewEmoji.setSpan(new AnimatedEmojiSpan(tLRPC$Document2, (Paint.FontMetricsInt) null));
                    } else {
                        imageViewEmoji.setSpan(new AnimatedEmojiSpan(l.longValue(), (Paint.FontMetricsInt) null));
                    }
                }
            } else {
                imageViewEmoji.setImageDrawable(Emoji.getEmojiBigDrawable(str), z);
                imageViewEmoji.setSpan(null);
            }
            imageViewEmoji.setTag(str3);
            imageViewEmoji.setContentDescription(str);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == this.trendingRow) {
                return 4;
            }
            if (i == this.trendingHeaderRow || i == this.recentlyUsedHeaderRow) {
                return 1;
            }
            if (this.positionToSection.indexOfKey(i) >= 0) {
                return this.positionToSection.get(i) >= EmojiData.dataColored.length ? 5 : 1;
            }
            if (EmojiView.this.needEmojiSearch && i == 0) {
                return 2;
            }
            if (this.positionToUnlock.indexOfKey(i) >= 0) {
                return 3;
            }
            return this.positionToExpand.indexOfKey(i) >= 0 ? 6 : 0;
        }

        public void processEmoji(boolean z) {
            boolean z2;
            EmojiView.this.emojipacksProcessed.clear();
            if (EmojiView.this.allowAnimatedEmoji) {
                MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
                if (z || this.frozenEmojiPacks == null) {
                    this.frozenEmojiPacks = new ArrayList<>(mediaDataController.getStickerSets(5));
                }
                ArrayList<TLRPC$TL_messages_stickerSet> arrayList = this.frozenEmojiPacks;
                boolean z3 = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
                if (!z3) {
                    int i = 0;
                    while (i < arrayList.size()) {
                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = arrayList.get(i);
                        if (tLRPC$TL_messages_stickerSet != null && !MessageObject.isPremiumEmojiPack(tLRPC$TL_messages_stickerSet)) {
                            EmojiPack emojiPack = new EmojiPack();
                            emojiPack.set = tLRPC$TL_messages_stickerSet.set;
                            emojiPack.documents = new ArrayList<>(tLRPC$TL_messages_stickerSet.documents);
                            emojiPack.free = true;
                            emojiPack.installed = mediaDataController.isStickerPackInstalled(tLRPC$TL_messages_stickerSet.set.id);
                            emojiPack.featured = false;
                            emojiPack.expanded = true;
                            EmojiView.this.emojipacksProcessed.add(emojiPack);
                            arrayList.remove(i);
                            i--;
                        }
                        i++;
                    }
                }
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2 = arrayList.get(i2);
                    if (z3) {
                        EmojiPack emojiPack2 = new EmojiPack();
                        TLRPC$StickerSet tLRPC$StickerSet = tLRPC$TL_messages_stickerSet2.set;
                        emojiPack2.set = tLRPC$StickerSet;
                        emojiPack2.documents = tLRPC$TL_messages_stickerSet2.documents;
                        emojiPack2.free = false;
                        emojiPack2.installed = mediaDataController.isStickerPackInstalled(tLRPC$StickerSet.id);
                        emojiPack2.featured = false;
                        emojiPack2.expanded = true;
                        EmojiView.this.emojipacksProcessed.add(emojiPack2);
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        ArrayList arrayList3 = new ArrayList();
                        if (tLRPC$TL_messages_stickerSet2 != null && tLRPC$TL_messages_stickerSet2.documents != null) {
                            for (int i3 = 0; i3 < tLRPC$TL_messages_stickerSet2.documents.size(); i3++) {
                                if (MessageObject.isFreeEmoji(tLRPC$TL_messages_stickerSet2.documents.get(i3))) {
                                    arrayList2.add(tLRPC$TL_messages_stickerSet2.documents.get(i3));
                                } else {
                                    arrayList3.add(tLRPC$TL_messages_stickerSet2.documents.get(i3));
                                }
                            }
                        }
                        if (arrayList2.size() > 0) {
                            EmojiPack emojiPack3 = new EmojiPack();
                            emojiPack3.set = tLRPC$TL_messages_stickerSet2.set;
                            emojiPack3.documents = new ArrayList<>(arrayList2);
                            emojiPack3.free = true;
                            emojiPack3.installed = mediaDataController.isStickerPackInstalled(tLRPC$TL_messages_stickerSet2.set.id);
                            emojiPack3.featured = false;
                            emojiPack3.expanded = true;
                            EmojiView.this.emojipacksProcessed.add(emojiPack3);
                        }
                        if (arrayList3.size() > 0) {
                            EmojiPack emojiPack4 = new EmojiPack();
                            emojiPack4.set = tLRPC$TL_messages_stickerSet2.set;
                            emojiPack4.documents = new ArrayList<>(arrayList3);
                            emojiPack4.free = false;
                            emojiPack4.installed = mediaDataController.isStickerPackInstalled(tLRPC$TL_messages_stickerSet2.set.id);
                            emojiPack4.featured = false;
                            emojiPack4.expanded = EmojiView.this.expandedEmojiSets.contains(Long.valueOf(emojiPack4.set.id));
                            EmojiView.this.emojipacksProcessed.add(emojiPack4);
                        }
                    }
                }
                for (int i4 = 0; i4 < EmojiView.this.featuredEmojiSets.size(); i4++) {
                    TLRPC$StickerSetCovered tLRPC$StickerSetCovered = (TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(i4);
                    EmojiPack emojiPack5 = new EmojiPack();
                    emojiPack5.installed = mediaDataController.isStickerPackInstalled(tLRPC$StickerSetCovered.set.id);
                    TLRPC$StickerSet tLRPC$StickerSet2 = tLRPC$StickerSetCovered.set;
                    emojiPack5.set = tLRPC$StickerSet2;
                    if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetFullCovered) {
                        emojiPack5.documents = ((TLRPC$TL_stickerSetFullCovered) tLRPC$StickerSetCovered).documents;
                    } else if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetNoCovered) {
                        TLRPC$TL_messages_stickerSet stickerSet = mediaDataController.getStickerSet(MediaDataController.getInputStickerSet(tLRPC$StickerSet2), Integer.valueOf(tLRPC$StickerSetCovered.set.hash), false);
                        if (stickerSet != null) {
                            emojiPack5.documents = stickerSet.documents;
                        }
                    } else {
                        emojiPack5.documents = tLRPC$StickerSetCovered.covers;
                    }
                    int i5 = 0;
                    while (true) {
                        if (i5 >= emojiPack5.documents.size()) {
                            z2 = false;
                            break;
                        } else {
                            if (!MessageObject.isFreeEmoji(emojiPack5.documents.get(i5))) {
                                z2 = true;
                                break;
                            }
                            i5++;
                        }
                    }
                    emojiPack5.free = !z2;
                    emojiPack5.expanded = EmojiView.this.expandedEmojiSets.contains(Long.valueOf(emojiPack5.set.id));
                    emojiPack5.featured = true;
                    EmojiView.this.emojipacksProcessed.add(emojiPack5);
                }
                if (EmojiView.this.emojiTabs != null) {
                    EmojiView.this.emojiTabs.updateEmojiPacks(EmojiView.this.getEmojipacks());
                }
            }
        }

        public void expand(int i, View view) {
            int i2 = this.positionToExpand.get(i);
            if (i2 < 0 || i2 >= EmojiView.this.emojipacksProcessed.size()) {
                return;
            }
            EmojiPack emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i2);
            if (emojiPack.expanded) {
                return;
            }
            boolean z = i2 + 1 == EmojiView.this.emojipacksProcessed.size();
            int intValue = this.packStartPosition.get(i2).intValue();
            EmojiView.this.expandedEmojiSets.add(Long.valueOf(emojiPack.set.id));
            boolean z2 = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
            int size = ((emojiPack.installed && !emojiPack.featured && (emojiPack.free || z2)) || emojiPack.expanded) ? emojiPack.documents.size() : Math.min(spanCount, emojiPack.documents.size());
            Integer num = null;
            Integer valueOf = emojiPack.documents.size() > spanCount ? Integer.valueOf(intValue + 1 + size) : null;
            emojiPack.expanded = true;
            int size2 = emojiPack.documents.size() - size;
            if (size2 > 0) {
                valueOf = Integer.valueOf(intValue + 1 + size);
                num = Integer.valueOf(size2);
            }
            processEmoji(false);
            updateRows();
            if (valueOf == null || num == null) {
                return;
            }
            EmojiView.this.animateExpandFromButton = view;
            EmojiView.this.animateExpandFromPosition = valueOf.intValue();
            EmojiView.this.animateExpandToPosition = valueOf.intValue() + num.intValue();
            EmojiView.this.animateExpandStartTime = SystemClock.elapsedRealtime();
            notifyItemRangeInserted(valueOf.intValue(), num.intValue());
            notifyItemChanged(valueOf.intValue());
            if (z) {
                final int intValue2 = valueOf.intValue();
                final float f = num.intValue() > spanCount / 2 ? 1.5f : 4.0f;
                EmojiView.this.post(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiGridAdapter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.EmojiGridAdapter.this.lambda$expand$2(f, intValue2);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$expand$2(float f, int i) {
            try {
                LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(EmojiView.this.emojiGridView.getContext(), 0, f);
                linearSmoothScrollerCustom.setTargetPosition(i);
                EmojiView.this.emojiLayoutManager.startSmoothScroll(linearSmoothScrollerCustom);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        public void updateRows() {
            this.positionToSection.clear();
            this.sectionToPosition.clear();
            this.positionToUnlock.clear();
            this.positionToExpand.clear();
            this.packStartPosition.clear();
            this.rowHashCodes.clear();
            this.itemCount = 0;
            boolean z = UserConfig.getInstance(EmojiView.this.currentAccount).isPremium() || EmojiView.this.allowEmojisForNonPremium;
            if (EmojiView.this.needEmojiSearch) {
                this.itemCount++;
                this.rowHashCodes.add(-1);
            }
            if (z && EmojiView.this.allowAnimatedEmoji && EmojiView.this.featuredEmojiSets.size() > 0 && ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set != null && MessagesController.getEmojiSettings(EmojiView.this.currentAccount).getLong("emoji_featured_hidden", 0L) != ((TLRPC$StickerSetCovered) EmojiView.this.featuredEmojiSets.get(0)).set.id) {
                int i = this.itemCount;
                int i2 = i + 1;
                this.itemCount = i2;
                this.trendingHeaderRow = i;
                int i3 = i2 + 1;
                this.itemCount = i3;
                this.trendingRow = i2;
                this.itemCount = i3 + 1;
                this.recentlyUsedHeaderRow = i3;
            } else {
                this.trendingHeaderRow = -1;
                this.trendingRow = -1;
                this.recentlyUsedHeaderRow = -1;
            }
            ArrayList<String> recentEmoji = EmojiView.this.getRecentEmoji();
            if (EmojiView.this.emojiTabs != null) {
                EmojiView.this.emojiTabs.showRecent(!recentEmoji.isEmpty());
            }
            this.itemCount += recentEmoji.size();
            for (int i4 = 0; i4 < recentEmoji.size(); i4++) {
                this.rowHashCodes.add(Integer.valueOf(Objects.hash(-43263, recentEmoji.get(i4))));
            }
            int i5 = 0;
            int i6 = 0;
            while (true) {
                String[][] strArr = EmojiData.dataColored;
                if (i5 >= strArr.length) {
                    break;
                }
                this.positionToSection.put(this.itemCount, i6);
                this.sectionToPosition.put(i6, this.itemCount);
                this.itemCount += strArr[i5].length + 1;
                this.rowHashCodes.add(Integer.valueOf(Objects.hash(43245, Integer.valueOf(i5))));
                int i7 = 0;
                while (true) {
                    String[][] strArr2 = EmojiData.dataColored;
                    if (i7 < strArr2[i5].length) {
                        this.rowHashCodes.add(Integer.valueOf(strArr2[i5][i7].hashCode()));
                        i7++;
                    }
                }
                i5++;
                i6++;
            }
            int spanCount = EmojiView.this.emojiLayoutManager.getSpanCount() * 3;
            this.plainEmojisCount = this.itemCount;
            this.firstTrendingRow = -1;
            if (EmojiView.this.emojipacksProcessed != null) {
                int i8 = 0;
                while (i8 < EmojiView.this.emojipacksProcessed.size()) {
                    this.positionToSection.put(this.itemCount, i6);
                    this.sectionToPosition.put(i6, this.itemCount);
                    this.packStartPosition.add(Integer.valueOf(this.itemCount));
                    EmojiPack emojiPack = (EmojiPack) EmojiView.this.emojipacksProcessed.get(i8);
                    boolean z2 = emojiPack.featured;
                    if (z2 && this.firstTrendingRow < 0) {
                        this.firstTrendingRow = this.itemCount;
                    }
                    int size = (((emojiPack.installed && !z2 && (emojiPack.free || z)) || emojiPack.expanded) ? emojiPack.documents.size() : Math.min(spanCount, emojiPack.documents.size())) + 1;
                    if (!emojiPack.expanded && emojiPack.documents.size() > spanCount) {
                        size--;
                    }
                    ArrayList<Integer> arrayList = this.rowHashCodes;
                    Object[] objArr = new Object[2];
                    objArr[0] = Integer.valueOf(emojiPack.featured ? 56345 : -645231);
                    TLRPC$StickerSet tLRPC$StickerSet = emojiPack.set;
                    objArr[1] = Long.valueOf(tLRPC$StickerSet == null ? i8 : tLRPC$StickerSet.id);
                    arrayList.add(Integer.valueOf(Objects.hash(objArr)));
                    for (int i9 = 1; i9 < size; i9++) {
                        ArrayList<Integer> arrayList2 = this.rowHashCodes;
                        Object[] objArr2 = new Object[2];
                        objArr2[0] = Integer.valueOf(emojiPack.featured ? 3442 : 3213);
                        objArr2[1] = Long.valueOf(emojiPack.documents.get(i9 - 1).id);
                        arrayList2.add(Integer.valueOf(Objects.hash(objArr2)));
                    }
                    this.itemCount += size;
                    if (!emojiPack.expanded && emojiPack.documents.size() > spanCount) {
                        this.positionToExpand.put(this.itemCount, i8);
                        this.rowHashCodes.add(Integer.valueOf(Objects.hash(-65174, Long.valueOf(emojiPack.set.id))));
                        this.itemCount++;
                    }
                    i8++;
                    i6++;
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            notifyDataSetChanged(false);
        }

        public void notifyDataSetChanged(boolean z) {
            final ArrayList arrayList = new ArrayList(this.rowHashCodes);
            MediaDataController mediaDataController = MediaDataController.getInstance(EmojiView.this.currentAccount);
            ArrayList<TLRPC$StickerSetCovered> featuredEmojiSets = mediaDataController.getFeaturedEmojiSets();
            EmojiView.this.featuredEmojiSets.clear();
            int size = featuredEmojiSets.size();
            for (int i = 0; i < size; i++) {
                TLRPC$StickerSetCovered tLRPC$StickerSetCovered = featuredEmojiSets.get(i);
                if (!mediaDataController.isStickerPackInstalled(tLRPC$StickerSetCovered.set.id) || EmojiView.this.installedEmojiSets.contains(Long.valueOf(tLRPC$StickerSetCovered.set.id))) {
                    EmojiView.this.featuredEmojiSets.add(tLRPC$StickerSetCovered);
                }
            }
            processEmoji(z);
            updateRows();
            if (EmojiView.this.trendingEmojiAdapter != null) {
                EmojiView.this.trendingEmojiAdapter.notifyDataSetChanged();
            }
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: org.telegram.ui.Components.EmojiView.EmojiGridAdapter.2
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i2, int i3) {
                    return true;
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return arrayList.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return EmojiGridAdapter.this.rowHashCodes.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i2, int i3) {
                    return ((Integer) arrayList.get(i2)).equals(EmojiGridAdapter.this.rowHashCodes.get(i3));
                }
            }, false).dispatchUpdatesTo(this);
        }
    }

    public ArrayList<EmojiPack> getEmojipacks() {
        ArrayList<EmojiPack> arrayList = new ArrayList<>();
        for (int i = 0; i < this.emojipacksProcessed.size(); i++) {
            EmojiPack emojiPack = this.emojipacksProcessed.get(i);
            if ((!emojiPack.featured && (emojiPack.installed || this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.id)))) || (emojiPack.featured && !emojiPack.installed && !this.installedEmojiSets.contains(Long.valueOf(emojiPack.set.id)))) {
                arrayList.add(emojiPack);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EmojiSearchAdapter extends RecyclerListView.SelectionAdapter {
        private ArrayList<Long> addedSets;
        private String lastSearchAlias;
        private String lastSearchEmojiString;
        private ArrayList<MediaDataController.KeywordResult> result;
        private Runnable searchRunnable;
        private boolean searchWas;

        private EmojiSearchAdapter() {
            this.result = new ArrayList<>();
            this.addedSets = new ArrayList<>();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int size;
            if (this.result.isEmpty() && !this.searchWas) {
                size = EmojiView.this.getRecentEmoji().size();
            } else {
                if (this.result.isEmpty()) {
                    return 2;
                }
                size = this.result.size();
            }
            return size + 1;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                view = new ImageViewEmoji(EmojiView.this.getContext());
            } else if (i == 1) {
                View view2 = new View(EmojiView.this.getContext());
                view2.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                view = view2;
            } else {
                FrameLayout frameLayout = new FrameLayout(EmojiView.this.getContext()) { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.1
                    @Override // android.widget.FrameLayout, android.view.View
                    protected void onMeasure(int i2, int i3) {
                        int dp;
                        if (((View) EmojiView.this.getParent()) != null) {
                            dp = (int) (r3.getMeasuredHeight() - EmojiView.this.getY());
                        } else {
                            dp = AndroidUtilities.dp(120.0f);
                        }
                        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(dp - EmojiView.this.searchFieldHeight, 1073741824));
                    }
                };
                TextView textView = new TextView(EmojiView.this.getContext());
                textView.setText(LocaleController.getString("NoEmojiFound", R.string.NoEmojiFound));
                textView.setTextSize(1, 16.0f);
                EmojiView emojiView = EmojiView.this;
                int i2 = Theme.key_chat_emojiPanelEmptyText;
                textView.setTextColor(emojiView.getThemedColor(i2));
                frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 49, 0.0f, 10.0f, 0.0f, 0.0f));
                ImageView imageView = new ImageView(EmojiView.this.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setImageResource(R.drawable.msg_emoji_question);
                imageView.setColorFilter(new PorterDuffColorFilter(EmojiView.this.getThemedColor(i2), PorterDuff.Mode.MULTIPLY));
                frameLayout.addView(imageView, LayoutHelper.createFrame(48, 48, 85));
                imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view3) {
                        Object obj;
                        boolean[] zArr = new boolean[1];
                        BottomSheet.Builder builder = new BottomSheet.Builder(EmojiView.this.getContext());
                        LinearLayout linearLayout = new LinearLayout(EmojiView.this.getContext());
                        linearLayout.setOrientation(1);
                        linearLayout.setPadding(AndroidUtilities.dp(21.0f), 0, AndroidUtilities.dp(21.0f), 0);
                        ImageView imageView2 = new ImageView(EmojiView.this.getContext());
                        imageView2.setImageResource(R.drawable.smiles_info);
                        linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, 49, 0, 15, 0, 0));
                        TextView textView2 = new TextView(EmojiView.this.getContext());
                        textView2.setText(LocaleController.getString("EmojiSuggestions", R.string.EmojiSuggestions));
                        textView2.setTextSize(1, 15.0f);
                        textView2.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlue2));
                        textView2.setGravity(LocaleController.isRTL ? 5 : 3);
                        textView2.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                        linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 51, 0, 24, 0, 0));
                        TextView textView3 = new TextView(EmojiView.this.getContext());
                        textView3.setText(AndroidUtilities.replaceTags(LocaleController.getString("EmojiSuggestionsInfo", R.string.EmojiSuggestionsInfo)));
                        textView3.setTextSize(1, 15.0f);
                        textView3.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextBlack));
                        textView3.setGravity(LocaleController.isRTL ? 5 : 3);
                        linearLayout.addView(textView3, LayoutHelper.createLinear(-2, -2, 51, 0, 11, 0, 0));
                        TextView textView4 = new TextView(EmojiView.this.getContext());
                        int i3 = R.string.EmojiSuggestionsUrl;
                        Object[] objArr = new Object[1];
                        if (EmojiSearchAdapter.this.lastSearchAlias == null) {
                            obj = EmojiView.this.lastSearchKeyboardLanguage;
                        } else {
                            obj = EmojiSearchAdapter.this.lastSearchAlias;
                        }
                        objArr[0] = obj;
                        textView4.setText(LocaleController.formatString("EmojiSuggestionsUrl", i3, objArr));
                        textView4.setTextSize(1, 15.0f);
                        textView4.setTextColor(EmojiView.this.getThemedColor(Theme.key_dialogTextLink));
                        textView4.setGravity(LocaleController.isRTL ? 5 : 3);
                        linearLayout.addView(textView4, LayoutHelper.createLinear(-2, -2, 51, 0, 18, 0, 16));
                        textView4.setOnClickListener(new AnonymousClass1(zArr, builder));
                        builder.setCustomView(linearLayout);
                        builder.show();
                    }

                    /* renamed from: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2$1, reason: invalid class name */
                    class AnonymousClass1 implements View.OnClickListener {
                        final /* synthetic */ BottomSheet.Builder val$builder;
                        final /* synthetic */ boolean[] val$loadingUrl;

                        AnonymousClass1(boolean[] zArr, BottomSheet.Builder builder) {
                            this.val$loadingUrl = zArr;
                            this.val$builder = builder;
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            String str;
                            boolean[] zArr = this.val$loadingUrl;
                            if (zArr[0]) {
                                return;
                            }
                            zArr[0] = true;
                            final AlertDialog[] alertDialogArr = {new AlertDialog(EmojiView.this.getContext(), 3)};
                            TLRPC$TL_messages_getEmojiURL tLRPC$TL_messages_getEmojiURL = new TLRPC$TL_messages_getEmojiURL();
                            if (EmojiSearchAdapter.this.lastSearchAlias == null) {
                                str = EmojiView.this.lastSearchKeyboardLanguage[0];
                            } else {
                                str = EmojiSearchAdapter.this.lastSearchAlias;
                            }
                            tLRPC$TL_messages_getEmojiURL.lang_code = str;
                            ConnectionsManager connectionsManager = ConnectionsManager.getInstance(EmojiView.this.currentAccount);
                            final BottomSheet.Builder builder = this.val$builder;
                            final int sendRequest = connectionsManager.sendRequest(tLRPC$TL_messages_getEmojiURL, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2$1$$ExternalSyntheticLambda3
                                @Override // org.telegram.tgnet.RequestDelegate
                                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                    EmojiView.EmojiSearchAdapter.AnonymousClass2.AnonymousClass1.this.lambda$onClick$1(alertDialogArr, builder, tLObject, tLRPC$TL_error);
                                }
                            });
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2$1$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    EmojiView.EmojiSearchAdapter.AnonymousClass2.AnonymousClass1.this.lambda$onClick$3(alertDialogArr, sendRequest);
                                }
                            }, 1000L);
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public /* synthetic */ void lambda$onClick$1(final AlertDialog[] alertDialogArr, final BottomSheet.Builder builder, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    EmojiView.EmojiSearchAdapter.AnonymousClass2.AnonymousClass1.this.lambda$onClick$0(alertDialogArr, tLObject, builder);
                                }
                            });
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public /* synthetic */ void lambda$onClick$0(AlertDialog[] alertDialogArr, TLObject tLObject, BottomSheet.Builder builder) {
                            try {
                                alertDialogArr[0].dismiss();
                            } catch (Throwable unused) {
                            }
                            alertDialogArr[0] = null;
                            if (tLObject instanceof TLRPC$TL_emojiURL) {
                                Browser.openUrl(EmojiView.this.getContext(), ((TLRPC$TL_emojiURL) tLObject).url);
                                builder.getDismissRunnable().run();
                            }
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public /* synthetic */ void lambda$onClick$3(AlertDialog[] alertDialogArr, final int i) {
                            if (alertDialogArr[0] == null) {
                                return;
                            }
                            alertDialogArr[0].setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$2$1$$ExternalSyntheticLambda0
                                @Override // android.content.DialogInterface.OnCancelListener
                                public final void onCancel(DialogInterface dialogInterface) {
                                    EmojiView.EmojiSearchAdapter.AnonymousClass2.AnonymousClass1.this.lambda$onClick$2(i, dialogInterface);
                                }
                            });
                            alertDialogArr[0].show();
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public /* synthetic */ void lambda$onClick$2(int i, DialogInterface dialogInterface) {
                            ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(i, true);
                        }
                    }
                });
                frameLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                view = frameLayout;
            }
            return new RecyclerListView.Holder(view);
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0075  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x009b  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0070  */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r9, int r10) {
            /*
                r8 = this;
                int r0 = r9.getItemViewType()
                if (r0 == 0) goto L8
                goto La8
            L8:
                android.view.View r9 = r9.itemView
                org.telegram.ui.Components.EmojiView$ImageViewEmoji r9 = (org.telegram.ui.Components.EmojiView.ImageViewEmoji) r9
                r9.position = r10
                r0 = 0
                org.telegram.ui.Components.EmojiView.ImageViewEmoji.access$17302(r9, r0)
                int r10 = r10 + (-1)
                java.util.ArrayList<org.telegram.messenger.MediaDataController$KeywordResult> r1 = r8.result
                boolean r1 = r1.isEmpty()
                r2 = 0
                if (r1 == 0) goto L2f
                boolean r1 = r8.searchWas
                if (r1 != 0) goto L2f
                org.telegram.ui.Components.EmojiView r1 = org.telegram.ui.Components.EmojiView.this
                java.util.ArrayList r1 = r1.getRecentEmoji()
                java.lang.Object r10 = r1.get(r10)
                java.lang.String r10 = (java.lang.String) r10
                r1 = 1
                goto L3a
            L2f:
                java.util.ArrayList<org.telegram.messenger.MediaDataController$KeywordResult> r1 = r8.result
                java.lang.Object r10 = r1.get(r10)
                org.telegram.messenger.MediaDataController$KeywordResult r10 = (org.telegram.messenger.MediaDataController.KeywordResult) r10
                java.lang.String r10 = r10.emoji
                r1 = 0
            L3a:
                if (r10 == 0) goto L55
                java.lang.String r3 = "animated_"
                boolean r3 = r10.startsWith(r3)
                if (r3 == 0) goto L55
                r3 = 9
                java.lang.String r3 = r10.substring(r3)     // Catch: java.lang.Exception -> L55
                long r3 = java.lang.Long.parseLong(r3)     // Catch: java.lang.Exception -> L55
                java.lang.Long r10 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Exception -> L55
                r3 = r0
                r4 = r3
                goto L58
            L55:
                r3 = r10
                r4 = r3
                r10 = r0
            L58:
                if (r10 == 0) goto L70
                r2 = 1077936128(0x40400000, float:3.0)
                int r5 = org.telegram.messenger.AndroidUtilities.dp(r2)
                int r6 = org.telegram.messenger.AndroidUtilities.dp(r2)
                int r7 = org.telegram.messenger.AndroidUtilities.dp(r2)
                int r2 = org.telegram.messenger.AndroidUtilities.dp(r2)
                r9.setPadding(r5, r6, r7, r2)
                goto L73
            L70:
                r9.setPadding(r2, r2, r2, r2)
            L73:
                if (r10 == 0) goto L9b
                r9.setImageDrawable(r0, r1)
                org.telegram.ui.Components.AnimatedEmojiSpan r1 = r9.getSpan()
                if (r1 == 0) goto L8e
                org.telegram.ui.Components.AnimatedEmojiSpan r1 = r9.getSpan()
                long r1 = r1.getDocumentId()
                long r5 = r10.longValue()
                int r3 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
                if (r3 == 0) goto La5
            L8e:
                org.telegram.ui.Components.AnimatedEmojiSpan r1 = new org.telegram.ui.Components.AnimatedEmojiSpan
                long r2 = r10.longValue()
                r1.<init>(r2, r0)
                r9.setSpan(r1)
                goto La5
            L9b:
                android.graphics.drawable.Drawable r10 = org.telegram.messenger.Emoji.getEmojiBigDrawable(r3)
                r9.setImageDrawable(r10, r1)
                r9.setSpan(r0)
            La5:
                r9.setTag(r4)
            La8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 1;
            }
            return (i == 1 && this.searchWas && this.result.isEmpty()) ? 2 : 0;
        }

        public void search(String str) {
            search(str, true);
        }

        public void search(String str, boolean z) {
            if (TextUtils.isEmpty(str)) {
                this.lastSearchEmojiString = null;
                if (EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiAdapter) {
                    EmojiView.this.emojiGridView.setAdapter(EmojiView.this.emojiAdapter);
                    this.searchWas = false;
                }
                notifyDataSetChanged();
            } else {
                this.lastSearchEmojiString = str.toLowerCase();
            }
            Runnable runnable = this.searchRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            if (TextUtils.isEmpty(this.lastSearchEmojiString)) {
                return;
            }
            EmojiView.this.emojiSearchField.showProgress(true);
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.EmojiSearchAdapter.this.lambda$search$2();
                }
            };
            this.searchRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, z ? 300L : 0L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$search$2() {
            final LinkedHashSet linkedHashSet = new LinkedHashSet();
            final String str = this.lastSearchEmojiString;
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.EmojiSearchAdapter.this.lambda$search$0(str, linkedHashSet);
                }
            };
            if (Emoji.fullyConsistsOfEmojis(str)) {
                StickerCategoriesListView.search.fetch(UserConfig.selectedAccount, str, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$EmojiSearchAdapter$$ExternalSyntheticLambda2
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.EmojiSearchAdapter.lambda$search$1(linkedHashSet, runnable, (TLRPC$TL_emojiList) obj);
                    }
                });
            } else {
                runnable.run();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$search$0(final String str, final LinkedHashSet linkedHashSet) {
            String[] currentKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
            if (!Arrays.equals(EmojiView.this.lastSearchKeyboardLanguage, currentKeyboardLanguage)) {
                MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(currentKeyboardLanguage);
            }
            EmojiView.this.lastSearchKeyboardLanguage = currentKeyboardLanguage;
            MediaDataController.getInstance(EmojiView.this.currentAccount).getEmojiSuggestions(EmojiView.this.lastSearchKeyboardLanguage, this.lastSearchEmojiString, false, new MediaDataController.KeywordResultCallback() { // from class: org.telegram.ui.Components.EmojiView.EmojiSearchAdapter.3
                @Override // org.telegram.messenger.MediaDataController.KeywordResultCallback
                public void run(ArrayList<MediaDataController.KeywordResult> arrayList, String str2) {
                    String str3;
                    if (str.equals(EmojiSearchAdapter.this.lastSearchEmojiString)) {
                        EmojiSearchAdapter.this.lastSearchAlias = str2;
                        EmojiView.this.emojiSearchField.showProgress(false);
                        EmojiSearchAdapter.this.searchWas = true;
                        if (EmojiView.this.emojiGridView.getAdapter() != EmojiView.this.emojiSearchAdapter) {
                            EmojiView.this.emojiGridView.setAdapter(EmojiView.this.emojiSearchAdapter);
                        }
                        EmojiSearchAdapter.this.result.clear();
                        EmojiSearchAdapter.this.searchByPackname(str, linkedHashSet);
                        Iterator it = linkedHashSet.iterator();
                        while (it.hasNext()) {
                            long longValue = ((Long) it.next()).longValue();
                            MediaDataController.KeywordResult keywordResult = new MediaDataController.KeywordResult();
                            keywordResult.keyword = "";
                            keywordResult.emoji = "animated_" + longValue;
                            EmojiSearchAdapter.this.result.add(keywordResult);
                        }
                        for (int i = 0; i < arrayList.size(); i++) {
                            MediaDataController.KeywordResult keywordResult2 = arrayList.get(i);
                            if (keywordResult2 != null && (str3 = keywordResult2.emoji) != null && (!str3.startsWith("animated_") || !linkedHashSet.contains(Long.valueOf(Long.parseLong(keywordResult2.emoji.substring(9)))))) {
                                EmojiSearchAdapter.this.result.add(keywordResult2);
                            }
                        }
                        EmojiSearchAdapter.this.notifyDataSetChanged();
                    }
                }
            }, null, true, false, true, 25);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$search$1(LinkedHashSet linkedHashSet, Runnable runnable, TLRPC$TL_emojiList tLRPC$TL_emojiList) {
            if (tLRPC$TL_emojiList != null) {
                linkedHashSet.addAll(tLRPC$TL_emojiList.document_id);
            }
            runnable.run();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void searchByPackname(String str, LinkedHashSet<Long> linkedHashSet) {
            TLRPC$StickerSet tLRPC$StickerSet;
            TLRPC$StickerSet tLRPC$StickerSet2;
            if (str == null || str.length() <= 3 || !UserConfig.getInstance(EmojiView.this.currentAccount).isPremium()) {
                return;
            }
            String lowerCase = LocaleController.getInstance().getTranslitString(str).toLowerCase();
            ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSets(5);
            ArrayList<TLRPC$StickerSetCovered> featuredEmojiSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getFeaturedEmojiSets();
            this.addedSets.clear();
            for (int i = 0; i < stickerSets.size(); i++) {
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = stickerSets.get(i);
                if (tLRPC$TL_messages_stickerSet != null && (tLRPC$StickerSet2 = tLRPC$TL_messages_stickerSet.set) != null) {
                    checkAddPackToResults(tLRPC$StickerSet2, tLRPC$TL_messages_stickerSet.documents, lowerCase, linkedHashSet);
                }
            }
            for (int i2 = 0; i2 < featuredEmojiSets.size(); i2++) {
                TLRPC$StickerSetCovered tLRPC$StickerSetCovered = featuredEmojiSets.get(i2);
                if (tLRPC$StickerSetCovered != null && (tLRPC$StickerSet = tLRPC$StickerSetCovered.set) != null) {
                    if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetFullCovered) {
                        checkAddPackToResults(tLRPC$StickerSet, ((TLRPC$TL_stickerSetFullCovered) tLRPC$StickerSetCovered).documents, lowerCase, linkedHashSet);
                    } else if (tLRPC$StickerSetCovered instanceof TLRPC$TL_stickerSetNoCovered) {
                        TLRPC$TL_inputStickerSetID tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                        tLRPC$TL_inputStickerSetID.id = tLRPC$StickerSetCovered.set.id;
                        TLRPC$TL_messages_stickerSet stickerSet = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSet(tLRPC$TL_inputStickerSetID, true);
                        if (stickerSet != null) {
                            checkAddPackToResults(stickerSet.set, stickerSet.documents, lowerCase, linkedHashSet);
                        }
                    } else {
                        checkAddPackToResults(tLRPC$StickerSet, tLRPC$StickerSetCovered.covers, lowerCase, linkedHashSet);
                    }
                }
            }
        }

        private void checkAddPackToResults(TLRPC$StickerSet tLRPC$StickerSet, ArrayList<TLRPC$Document> arrayList, String str, LinkedHashSet<Long> linkedHashSet) {
            if (tLRPC$StickerSet.title == null || this.addedSets.contains(Long.valueOf(tLRPC$StickerSet.id)) || !LocaleController.getInstance().getTranslitString(tLRPC$StickerSet.title.toLowerCase()).contains(str)) {
                return;
            }
            Iterator<TLRPC$Document> it = arrayList.iterator();
            while (it.hasNext()) {
                TLRPC$Document next = it.next();
                if (next != null) {
                    linkedHashSet.add(Long.valueOf(next.id));
                }
            }
            this.addedSets.add(Long.valueOf(tLRPC$StickerSet.id));
        }
    }

    private class EmojiPagesAdapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public void customOnDraw(Canvas canvas, View view, int i) {
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public Drawable getPageIconDrawable(int i) {
            return null;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        private EmojiPagesAdapter() {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public boolean canScrollToTab(int i) {
            if (i == 1 || i == 2) {
                EmojiView emojiView = EmojiView.this;
                if (emojiView.stickersBanned) {
                    emojiView.showStickerBanHint(true, false, i == 1);
                    return false;
                }
            }
            if (i == 0) {
                EmojiView emojiView2 = EmojiView.this;
                if (emojiView2.emojiBanned) {
                    emojiView2.showStickerBanHint(true, true, false);
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return EmojiView.this.currentTabs.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return LocaleController.getString("Emoji", R.string.Emoji);
            }
            if (i == 1) {
                return LocaleController.getString("AccDescrGIFs", R.string.AccDescrGIFs);
            }
            if (i != 2) {
                return null;
            }
            return LocaleController.getString("AccDescrStickers", R.string.AccDescrStickers);
        }

        @Override // org.telegram.ui.Components.PagerSlidingTabStrip.IconTabProvider
        public int getTabPadding(int i) {
            if (i == 0) {
                return AndroidUtilities.dp(18.0f);
            }
            return AndroidUtilities.dp(12.0f);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            View view = ((Tab) EmojiView.this.currentTabs.get(i)).view;
            viewGroup.addView(view);
            return view;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GifAdapter extends RecyclerListView.SelectionAdapter {
        private boolean addSearch;
        private TLRPC$User bot;
        private final Context context;
        private int firstResultItem;
        private int itemsCount;
        private String lastSearchImageString;
        private boolean lastSearchIsEmoji;
        private final int maxRecentRowsCount;
        private String nextSearchOffset;
        private final GifProgressEmptyView progressEmptyView;
        private int recentItemsCount;
        private int reqId;
        private ArrayList<TLRPC$BotInlineResult> results;
        private HashMap<String, TLRPC$BotInlineResult> resultsMap;
        private boolean searchEndReached;
        private Runnable searchRunnable;
        private boolean searchingUser;
        private boolean showTrendingWhenSearchEmpty;
        private int trendingSectionItem;
        private final boolean withRecent;

        public GifAdapter(EmojiView emojiView, Context context) {
            this(context, false, 0);
        }

        public GifAdapter(EmojiView emojiView, Context context, boolean z) {
            this(context, z, z ? Integer.MAX_VALUE : 0);
        }

        public GifAdapter(Context context, boolean z, int i) {
            this.results = new ArrayList<>();
            this.resultsMap = new HashMap<>();
            this.trendingSectionItem = -1;
            this.firstResultItem = -1;
            this.context = context;
            this.withRecent = z;
            this.maxRecentRowsCount = i;
            this.progressEmptyView = z ? null : EmojiView.this.new GifProgressEmptyView(context);
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 0;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.itemsCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0 && this.addSearch) {
                return 1;
            }
            boolean z = this.withRecent;
            if (z && i == this.trendingSectionItem) {
                return 2;
            }
            return (z || !this.results.isEmpty()) ? 0 : 3;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (i == 0) {
                ContextLinkCell contextLinkCell = new ContextLinkCell(this.context);
                contextLinkCell.setIsKeyboard(true);
                contextLinkCell.setCanPreviewGif(true);
                view = contextLinkCell;
            } else if (i == 1) {
                View view2 = new View(EmojiView.this.getContext());
                view2.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                view = view2;
            } else if (i == 2) {
                StickerSetNameCell stickerSetNameCell = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider);
                stickerSetNameCell.setText(LocaleController.getString("FeaturedGifs", R.string.FeaturedGifs), 0);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(-1, -2);
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = AndroidUtilities.dp(2.5f);
                ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = AndroidUtilities.dp(5.5f);
                stickerSetNameCell.setLayoutParams(layoutParams);
                view = stickerSetNameCell;
            } else {
                View view3 = this.progressEmptyView;
                view3.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                view = view3;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() != 0) {
                return;
            }
            ContextLinkCell contextLinkCell = (ContextLinkCell) viewHolder.itemView;
            int i2 = this.firstResultItem;
            if (i2 < 0 || i < i2) {
                contextLinkCell.setGif((TLRPC$Document) EmojiView.this.recentGifs.get(i - (this.addSearch ? 1 : 0)), false);
            } else {
                contextLinkCell.setLink(this.results.get(i - i2), this.bot, true, false, false, true);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            updateRecentItemsCount();
            updateItems();
            super.notifyDataSetChanged();
        }

        private void updateItems() {
            this.trendingSectionItem = -1;
            this.firstResultItem = -1;
            this.itemsCount = 0;
            if (this.addSearch) {
                this.itemsCount = 0 + 1;
            }
            if (this.withRecent) {
                this.itemsCount += this.recentItemsCount;
            }
            if (!this.results.isEmpty()) {
                if (this.withRecent && this.recentItemsCount > 0) {
                    int i = this.itemsCount;
                    this.itemsCount = i + 1;
                    this.trendingSectionItem = i;
                }
                int i2 = this.itemsCount;
                this.firstResultItem = i2;
                this.itemsCount = i2 + this.results.size();
                return;
            }
            if (this.withRecent) {
                return;
            }
            this.itemsCount++;
        }

        private void updateRecentItemsCount() {
            int i;
            if (!this.withRecent || (i = this.maxRecentRowsCount) == 0) {
                return;
            }
            if (i == Integer.MAX_VALUE) {
                this.recentItemsCount = EmojiView.this.recentGifs.size();
                return;
            }
            if (EmojiView.this.gifGridView.getMeasuredWidth() == 0) {
                return;
            }
            int measuredWidth = EmojiView.this.gifGridView.getMeasuredWidth();
            int spanCount = EmojiView.this.gifLayoutManager.getSpanCount();
            int dp = AndroidUtilities.dp(100.0f);
            this.recentItemsCount = 0;
            int size = EmojiView.this.recentGifs.size();
            int i2 = spanCount;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                Size fixSize = EmojiView.this.gifLayoutManager.fixSize(EmojiView.this.gifLayoutManager.getSizeForItem((TLRPC$Document) EmojiView.this.recentGifs.get(i5)));
                int min = Math.min(spanCount, (int) Math.floor(spanCount * (((fixSize.width / fixSize.height) * dp) / measuredWidth)));
                if (i2 < min) {
                    this.recentItemsCount += i3;
                    i4++;
                    if (i4 == this.maxRecentRowsCount) {
                        break;
                    }
                    i2 = spanCount;
                    i3 = 0;
                }
                i3++;
                i2 -= min;
            }
            if (i4 < this.maxRecentRowsCount) {
                this.recentItemsCount += i3;
            }
        }

        public void loadTrendingGifs() {
            search("", "", true, true, true);
        }

        private void searchBotUser() {
            if (this.searchingUser) {
                return;
            }
            this.searchingUser = true;
            TLRPC$TL_contacts_resolveUsername tLRPC$TL_contacts_resolveUsername = new TLRPC$TL_contacts_resolveUsername();
            tLRPC$TL_contacts_resolveUsername.username = MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchBot;
            ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tLRPC$TL_contacts_resolveUsername, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda3
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    EmojiView.GifAdapter.this.lambda$searchBotUser$1(tLObject, tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchBotUser$1(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.GifAdapter.this.lambda$searchBotUser$0(tLObject);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$searchBotUser$0(TLObject tLObject) {
            TLRPC$TL_contacts_resolvedPeer tLRPC$TL_contacts_resolvedPeer = (TLRPC$TL_contacts_resolvedPeer) tLObject;
            MessagesController.getInstance(EmojiView.this.currentAccount).putUsers(tLRPC$TL_contacts_resolvedPeer.users, false);
            MessagesController.getInstance(EmojiView.this.currentAccount).putChats(tLRPC$TL_contacts_resolvedPeer.chats, false);
            MessagesStorage.getInstance(EmojiView.this.currentAccount).putUsersAndChats(tLRPC$TL_contacts_resolvedPeer.users, tLRPC$TL_contacts_resolvedPeer.chats, true, true);
            String str = this.lastSearchImageString;
            this.lastSearchImageString = null;
            search(str, "", false);
        }

        public void search(String str) {
            search(str, true);
        }

        public void search(final String str, boolean z) {
            if (this.withRecent) {
                return;
            }
            int i = this.reqId;
            if (i != 0) {
                if (i >= 0) {
                    ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                }
                this.reqId = 0;
            }
            this.lastSearchIsEmoji = false;
            GifProgressEmptyView gifProgressEmptyView = this.progressEmptyView;
            if (gifProgressEmptyView != null) {
                gifProgressEmptyView.setLoadingState(false);
            }
            Runnable runnable = this.searchRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            if (TextUtils.isEmpty(str)) {
                this.lastSearchImageString = null;
                if (!this.showTrendingWhenSearchEmpty) {
                    int currentPosition = EmojiView.this.gifTabs.getCurrentPosition();
                    if (currentPosition == EmojiView.this.gifRecentTabNum || currentPosition == EmojiView.this.gifTrendingTabNum) {
                        if (EmojiView.this.gifGridView.getAdapter() != EmojiView.this.gifAdapter) {
                            EmojiView.this.gifGridView.setAdapter(EmojiView.this.gifAdapter);
                            return;
                        }
                        return;
                    }
                    searchEmoji(MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchEmojies.get(currentPosition - EmojiView.this.gifFirstEmojiTabNum));
                    return;
                }
                loadTrendingGifs();
                return;
            }
            String lowerCase = str.toLowerCase();
            this.lastSearchImageString = lowerCase;
            if (TextUtils.isEmpty(lowerCase)) {
                return;
            }
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.GifAdapter.this.lambda$search$2(str);
                }
            };
            this.searchRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, z ? 300L : 0L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$search$2(String str) {
            search(str, "", true);
        }

        public void searchEmoji(String str) {
            if (this.lastSearchIsEmoji && TextUtils.equals(this.lastSearchImageString, str)) {
                EmojiView.this.gifLayoutManager.scrollToPositionWithOffset(0, 0);
            } else {
                search(str, "", true, true, true);
            }
        }

        protected void search(String str, String str2, boolean z) {
            search(str, str2, z, false, false);
        }

        protected void search(final String str, final String str2, final boolean z, final boolean z2, final boolean z3) {
            int i = this.reqId;
            if (i != 0) {
                if (i >= 0) {
                    ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                }
                this.reqId = 0;
            }
            this.lastSearchImageString = str;
            this.lastSearchIsEmoji = z2;
            GifProgressEmptyView gifProgressEmptyView = this.progressEmptyView;
            if (gifProgressEmptyView != null) {
                gifProgressEmptyView.setLoadingState(z2);
            }
            TLObject userOrChat = MessagesController.getInstance(EmojiView.this.currentAccount).getUserOrChat(MessagesController.getInstance(EmojiView.this.currentAccount).gifSearchBot);
            if (!(userOrChat instanceof TLRPC$User)) {
                if (z) {
                    searchBotUser();
                    if (this.withRecent) {
                        return;
                    }
                    EmojiView.this.gifSearchField.showProgress(true);
                    return;
                }
                return;
            }
            if (!this.withRecent && TextUtils.isEmpty(str2)) {
                EmojiView.this.gifSearchField.showProgress(true);
            }
            this.bot = (TLRPC$User) userOrChat;
            final String str3 = "gif_search_" + str + "_" + str2;
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda4
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    EmojiView.GifAdapter.this.lambda$search$4(str, str2, z, z2, z3, str3, tLObject, tLRPC$TL_error);
                }
            };
            if (!z3 && !this.withRecent && z2 && TextUtils.isEmpty(str2)) {
                this.results.clear();
                this.resultsMap.clear();
                if (EmojiView.this.gifGridView.getAdapter() != this) {
                    EmojiView.this.gifGridView.setAdapter(this);
                }
                notifyDataSetChanged();
                EmojiView.this.scrollGifsToTop();
            }
            if (!z3 || !EmojiView.this.gifCache.containsKey(str3)) {
                if (!EmojiView.this.gifSearchPreloader.isLoading(str3) && z3) {
                    this.reqId = -1;
                    MessagesStorage.getInstance(EmojiView.this.currentAccount).getBotCache(str3, requestDelegate);
                    return;
                }
                return;
            }
            lambda$search$3(str, str2, z, z2, true, str3, (TLObject) EmojiView.this.gifCache.get(str3));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$search$4(final String str, final String str2, final boolean z, final boolean z2, final boolean z3, final String str3, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifAdapter$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.GifAdapter.this.lambda$search$3(str, str2, z, z2, z3, str3, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: processResponse, reason: merged with bridge method [inline-methods] */
        public void lambda$search$3(String str, String str2, boolean z, boolean z2, boolean z3, String str3, TLObject tLObject) {
            if (str == null || !str.equals(this.lastSearchImageString)) {
                return;
            }
            this.reqId = 0;
            if (z3 && (!(tLObject instanceof TLRPC$messages_BotResults) || ((TLRPC$messages_BotResults) tLObject).results.isEmpty())) {
                search(str, str2, z, z2, false);
                return;
            }
            if (!this.withRecent && TextUtils.isEmpty(str2)) {
                this.results.clear();
                this.resultsMap.clear();
                EmojiView.this.gifSearchField.showProgress(false);
            }
            if (tLObject instanceof TLRPC$messages_BotResults) {
                int size = this.results.size();
                TLRPC$messages_BotResults tLRPC$messages_BotResults = (TLRPC$messages_BotResults) tLObject;
                if (!EmojiView.this.gifCache.containsKey(str3)) {
                    EmojiView.this.gifCache.put(str3, tLRPC$messages_BotResults);
                }
                if (!z3 && tLRPC$messages_BotResults.cache_time != 0) {
                    MessagesStorage.getInstance(EmojiView.this.currentAccount).saveBotCache(str3, tLRPC$messages_BotResults);
                }
                this.nextSearchOffset = tLRPC$messages_BotResults.next_offset;
                int i = 0;
                for (int i2 = 0; i2 < tLRPC$messages_BotResults.results.size(); i2++) {
                    TLRPC$BotInlineResult tLRPC$BotInlineResult = tLRPC$messages_BotResults.results.get(i2);
                    if (!this.resultsMap.containsKey(tLRPC$BotInlineResult.id)) {
                        tLRPC$BotInlineResult.query_id = tLRPC$messages_BotResults.query_id;
                        this.results.add(tLRPC$BotInlineResult);
                        this.resultsMap.put(tLRPC$BotInlineResult.id, tLRPC$BotInlineResult);
                        i++;
                    }
                }
                this.searchEndReached = size == this.results.size() || TextUtils.isEmpty(this.nextSearchOffset);
                if (i != 0) {
                    if (!z2 || size != 0) {
                        updateItems();
                        if (!this.withRecent) {
                            if (size != 0) {
                                notifyItemChanged(size);
                            }
                            notifyItemRangeInserted(size + (EmojiView.this.gifAdapter.addSearch ? 1 : 0), i);
                        } else if (size != 0) {
                            notifyItemChanged(this.recentItemsCount + (EmojiView.this.gifAdapter.addSearch ? 1 : 0) + size);
                            notifyItemRangeInserted(this.recentItemsCount + (EmojiView.this.gifAdapter.addSearch ? 1 : 0) + size + 1, i);
                        } else {
                            notifyItemRangeInserted(this.recentItemsCount + (EmojiView.this.gifAdapter.addSearch ? 1 : 0), i + 1);
                        }
                    } else {
                        notifyDataSetChanged();
                    }
                } else if (this.results.isEmpty()) {
                    notifyDataSetChanged();
                }
            } else {
                notifyDataSetChanged();
            }
            if (this.withRecent) {
                return;
            }
            if (EmojiView.this.gifGridView.getAdapter() != this) {
                EmojiView.this.gifGridView.setAdapter(this);
            }
            if (z2 && !TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                EmojiView.this.scrollGifsToTop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class GifSearchPreloader {
        private final List<String> loadingKeys;

        private GifSearchPreloader() {
            this.loadingKeys = new ArrayList();
        }

        public boolean isLoading(String str) {
            return this.loadingKeys.contains(str);
        }

        public void preload(String str) {
            preload(str, "", true);
        }

        private void preload(final String str, final String str2, final boolean z) {
            final String str3 = "gif_search_" + str + "_" + str2;
            if (z && EmojiView.this.gifCache.containsKey(str3)) {
                return;
            }
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$GifSearchPreloader$$ExternalSyntheticLambda1
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    EmojiView.GifSearchPreloader.this.lambda$preload$1(str, str2, z, str3, tLObject, tLRPC$TL_error);
                }
            };
            if (z) {
                this.loadingKeys.add(str3);
                MessagesStorage.getInstance(EmojiView.this.currentAccount).getBotCache(str3, requestDelegate);
            } else {
                MessagesController messagesController = MessagesController.getInstance(EmojiView.this.currentAccount);
                if (messagesController.getUserOrChat(messagesController.gifSearchBot) instanceof TLRPC$User) {
                    this.loadingKeys.add(str3);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$preload$1(final String str, final String str2, final boolean z, final String str3, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$GifSearchPreloader$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EmojiView.GifSearchPreloader.this.lambda$preload$0(str, str2, z, str3, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: processResponse, reason: merged with bridge method [inline-methods] */
        public void lambda$preload$0(String str, String str2, boolean z, String str3, TLObject tLObject) {
            this.loadingKeys.remove(str3);
            if (EmojiView.this.gifSearchAdapter.lastSearchIsEmoji && EmojiView.this.gifSearchAdapter.lastSearchImageString.equals(str)) {
                EmojiView.this.gifSearchAdapter.lambda$search$3(str, str2, false, true, z, str3, tLObject);
                return;
            }
            if (z && (!(tLObject instanceof TLRPC$messages_BotResults) || ((TLRPC$messages_BotResults) tLObject).results.isEmpty())) {
                preload(str, str2, false);
            } else {
                if (!(tLObject instanceof TLRPC$messages_BotResults) || EmojiView.this.gifCache.containsKey(str3)) {
                    return;
                }
                EmojiView.this.gifCache.put(str3, (TLRPC$messages_BotResults) tLObject);
            }
        }
    }

    private class GifLayoutManager extends ExtendedGridLayoutManager {
        private Size size;

        public GifLayoutManager(Context context) {
            super(context, 100, true);
            this.size = new Size();
            setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(EmojiView.this) { // from class: org.telegram.ui.Components.EmojiView.GifLayoutManager.1
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i) {
                    if ((i == 0 && EmojiView.this.gifAdapter.addSearch) || (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter && EmojiView.this.gifSearchAdapter.results.isEmpty())) {
                        return GifLayoutManager.this.getSpanCount();
                    }
                    GifLayoutManager gifLayoutManager = GifLayoutManager.this;
                    return gifLayoutManager.getSpanSizeForItem(i - (EmojiView.this.gifAdapter.addSearch ? 1 : 0));
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v19, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        /* JADX WARN: Type inference failed for: r4v20, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        /* JADX WARN: Type inference failed for: r4v21, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        /* JADX WARN: Type inference failed for: r4v5, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        /* JADX WARN: Type inference failed for: r4v6, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        /* JADX WARN: Type inference failed for: r4v7, types: [java.util.ArrayList<org.telegram.tgnet.TLRPC$DocumentAttribute>] */
        @Override // org.telegram.ui.Components.ExtendedGridLayoutManager
        protected Size getSizeForItem(int i) {
            List<TLRPC$DocumentAttribute> list;
            TLRPC$Document tLRPC$Document;
            TLRPC$Document tLRPC$Document2;
            TLRPC$Document tLRPC$Document3 = null;
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifAdapter) {
                if (i > EmojiView.this.gifAdapter.recentItemsCount) {
                    TLRPC$BotInlineResult tLRPC$BotInlineResult = (TLRPC$BotInlineResult) EmojiView.this.gifAdapter.results.get((i - EmojiView.this.gifAdapter.recentItemsCount) - 1);
                    tLRPC$Document = tLRPC$BotInlineResult.document;
                    if (tLRPC$Document != null) {
                        tLRPC$Document2 = tLRPC$Document.attributes;
                    } else {
                        TLRPC$WebDocument tLRPC$WebDocument = tLRPC$BotInlineResult.content;
                        if (tLRPC$WebDocument != null) {
                            tLRPC$Document2 = tLRPC$WebDocument.attributes;
                        } else {
                            TLRPC$WebDocument tLRPC$WebDocument2 = tLRPC$BotInlineResult.thumb;
                            if (tLRPC$WebDocument2 != null) {
                                tLRPC$Document2 = tLRPC$WebDocument2.attributes;
                            }
                            list = tLRPC$Document3;
                            tLRPC$Document3 = tLRPC$Document;
                            return getSizeForItem(tLRPC$Document3, list);
                        }
                    }
                    tLRPC$Document3 = tLRPC$Document2;
                    list = tLRPC$Document3;
                    tLRPC$Document3 = tLRPC$Document;
                    return getSizeForItem(tLRPC$Document3, list);
                }
                if (i == EmojiView.this.gifAdapter.recentItemsCount) {
                    return null;
                }
                tLRPC$Document3 = (TLRPC$Document) EmojiView.this.recentGifs.get(i);
                list = tLRPC$Document3.attributes;
                return getSizeForItem(tLRPC$Document3, list);
            }
            if (!EmojiView.this.gifSearchAdapter.results.isEmpty()) {
                TLRPC$BotInlineResult tLRPC$BotInlineResult2 = (TLRPC$BotInlineResult) EmojiView.this.gifSearchAdapter.results.get(i);
                tLRPC$Document = tLRPC$BotInlineResult2.document;
                if (tLRPC$Document != null) {
                    tLRPC$Document2 = tLRPC$Document.attributes;
                } else {
                    TLRPC$WebDocument tLRPC$WebDocument3 = tLRPC$BotInlineResult2.content;
                    if (tLRPC$WebDocument3 != null) {
                        tLRPC$Document2 = tLRPC$WebDocument3.attributes;
                    } else {
                        TLRPC$WebDocument tLRPC$WebDocument4 = tLRPC$BotInlineResult2.thumb;
                        if (tLRPC$WebDocument4 != null) {
                            tLRPC$Document2 = tLRPC$WebDocument4.attributes;
                        }
                        list = tLRPC$Document3;
                        tLRPC$Document3 = tLRPC$Document;
                        return getSizeForItem(tLRPC$Document3, list);
                    }
                }
                tLRPC$Document3 = tLRPC$Document2;
                list = tLRPC$Document3;
                tLRPC$Document3 = tLRPC$Document;
                return getSizeForItem(tLRPC$Document3, list);
            }
            list = null;
            return getSizeForItem(tLRPC$Document3, list);
        }

        @Override // org.telegram.ui.Components.ExtendedGridLayoutManager
        protected int getFlowItemCount() {
            if (EmojiView.this.gifGridView.getAdapter() == EmojiView.this.gifSearchAdapter && EmojiView.this.gifSearchAdapter.results.isEmpty()) {
                return 0;
            }
            return getItemCount() - 1;
        }

        public Size getSizeForItem(TLRPC$Document tLRPC$Document) {
            return getSizeForItem(tLRPC$Document, tLRPC$Document.attributes);
        }

        public Size getSizeForItem(TLRPC$Document tLRPC$Document, List<TLRPC$DocumentAttribute> list) {
            TLRPC$PhotoSize closestPhotoSizeWithSize;
            int i;
            int i2;
            Size size = this.size;
            size.height = 100.0f;
            size.width = 100.0f;
            if (tLRPC$Document != null && (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tLRPC$Document.thumbs, 90)) != null && (i = closestPhotoSizeWithSize.w) != 0 && (i2 = closestPhotoSizeWithSize.h) != 0) {
                Size size2 = this.size;
                size2.width = i;
                size2.height = i2;
            }
            if (list != null) {
                for (int i3 = 0; i3 < list.size(); i3++) {
                    TLRPC$DocumentAttribute tLRPC$DocumentAttribute = list.get(i3);
                    if ((tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeImageSize) || (tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeVideo)) {
                        Size size3 = this.size;
                        size3.width = tLRPC$DocumentAttribute.w;
                        size3.height = tLRPC$DocumentAttribute.h;
                        break;
                    }
                }
            }
            return this.size;
        }
    }

    private class GifProgressEmptyView extends FrameLayout {
        private final ImageView imageView;
        private boolean loadingState;
        private final RadialProgressView progressView;
        private final TextView textView;

        public GifProgressEmptyView(Context context) {
            super(context);
            ImageView imageView = new ImageView(getContext());
            this.imageView = imageView;
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(R.drawable.gif_empty);
            int i = Theme.key_chat_emojiPanelEmptyText;
            imageView.setColorFilter(new PorterDuffColorFilter(EmojiView.this.getThemedColor(i), PorterDuff.Mode.MULTIPLY));
            addView(imageView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 8.0f, 0.0f, 0.0f));
            TextView textView = new TextView(getContext());
            this.textView = textView;
            textView.setText(LocaleController.getString("NoGIFsFound", R.string.NoGIFsFound));
            textView.setTextSize(1, 16.0f);
            textView.setTextColor(EmojiView.this.getThemedColor(i));
            addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 0.0f));
            RadialProgressView radialProgressView = new RadialProgressView(context, EmojiView.this.resourcesProvider);
            this.progressView = radialProgressView;
            radialProgressView.setVisibility(8);
            radialProgressView.setProgressColor(EmojiView.this.getThemedColor(Theme.key_progressCircle));
            addView(radialProgressView, LayoutHelper.createFrame(-2, -2, 17));
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int dp;
            int measuredHeight = EmojiView.this.gifGridView.getMeasuredHeight();
            if (!this.loadingState) {
                dp = (int) ((((measuredHeight - EmojiView.this.searchFieldHeight) - AndroidUtilities.dp(8.0f)) / 3) * 1.7f);
            } else {
                dp = measuredHeight - AndroidUtilities.dp(80.0f);
            }
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(dp, 1073741824));
        }

        public void setLoadingState(boolean z) {
            if (this.loadingState != z) {
                this.loadingState = z;
                this.imageView.setVisibility(z ? 8 : 0);
                this.textView.setVisibility(z ? 8 : 0);
                this.progressView.setVisibility(z ? 0 : 8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StickersSearchGridAdapter extends RecyclerListView.SelectionAdapter {
        private Context context;
        private int emojiSearchId;
        private int reqId;
        private int reqId2;
        private String searchQuery;
        private int totalItems;
        private SparseArray<Object> rowStartPack = new SparseArray<>();
        private SparseArray<Object> cache = new SparseArray<>();
        private SparseArray<Object> cacheParent = new SparseArray<>();
        private SparseIntArray positionToRow = new SparseIntArray();
        private SparseArray<String> positionToEmoji = new SparseArray<>();
        private ArrayList<TLRPC$StickerSetCovered> serverPacks = new ArrayList<>();
        private ArrayList<TLRPC$TL_messages_stickerSet> localPacks = new ArrayList<>();
        private HashMap<TLRPC$TL_messages_stickerSet, Boolean> localPacksByShortName = new HashMap<>();
        private HashMap<TLRPC$TL_messages_stickerSet, Integer> localPacksByName = new HashMap<>();
        private HashMap<ArrayList<TLRPC$Document>, String> emojiStickers = new HashMap<>();
        private ArrayList<ArrayList<TLRPC$Document>> emojiArrays = new ArrayList<>();
        private SparseArray<TLRPC$StickerSetCovered> positionsToSets = new SparseArray<>();
        private Runnable searchRunnable = new AnonymousClass1();

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return false;
        }

        static /* synthetic */ int access$18804(StickersSearchGridAdapter stickersSearchGridAdapter) {
            int i = stickersSearchGridAdapter.emojiSearchId + 1;
            stickersSearchGridAdapter.emojiSearchId = i;
            return i;
        }

        /* renamed from: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            int lastId;
            String query;
            final ArrayList<TLRPC$StickerSetCovered> serverPacks = new ArrayList<>();
            final ArrayList<TLRPC$TL_messages_stickerSet> localPacks = new ArrayList<>();
            final HashMap<TLRPC$TL_messages_stickerSet, Boolean> localPacksByShortName = new HashMap<>();
            final HashMap<TLRPC$TL_messages_stickerSet, Integer> localPacksByName = new HashMap<>();
            final HashMap<ArrayList<TLRPC$Document>, String> emojiStickers = new HashMap<>();
            final ArrayList<ArrayList<TLRPC$Document>> emojiArrays = new ArrayList<>();
            final ArrayList<TLRPC$Document> emojiStickersArray = new ArrayList<>(0);
            final LongSparseArray<TLRPC$Document> emojiStickersMap = new LongSparseArray<>(0);

            AnonymousClass1() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void searchFinish() {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                StickersSearchGridAdapter.this.localPacks = this.localPacks;
                StickersSearchGridAdapter.this.serverPacks = this.serverPacks;
                StickersSearchGridAdapter.this.localPacksByShortName = this.localPacksByShortName;
                StickersSearchGridAdapter.this.localPacksByName = this.localPacksByName;
                StickersSearchGridAdapter.this.emojiStickers = this.emojiStickers;
                StickersSearchGridAdapter.this.emojiArrays = this.emojiArrays;
                EmojiView.this.stickersSearchField.showProgress(false);
                if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersSearchGridAdapter) {
                    EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersSearchGridAdapter);
                }
                StickersSearchGridAdapter.this.notifyDataSetChanged();
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* JADX WARN: Code restructure failed: missing block: B:12:0x0043, code lost:
            
                if (r1.charAt(r5) <= 57343) goto L20;
             */
            /* JADX WARN: Code restructure failed: missing block: B:22:0x005d, code lost:
            
                if (r1.charAt(r5) != 9794) goto L21;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void addFromAllStickers(java.lang.Runnable r11) {
                /*
                    r10 = this;
                    org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter r0 = org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.this
                    org.telegram.ui.Components.EmojiView r0 = org.telegram.ui.Components.EmojiView.this
                    int r0 = r0.currentAccount
                    org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
                    java.util.HashMap r0 = r0.getAllStickers()
                    java.lang.String r1 = r10.query
                    int r1 = r1.length()
                    r2 = 14
                    if (r1 > r2) goto Le7
                    java.lang.String r1 = r10.query
                    int r2 = r1.length()
                    r3 = 0
                    r4 = 0
                L20:
                    if (r4 >= r2) goto La2
                    int r5 = r2 + (-1)
                    r6 = 2
                    r7 = 1
                    if (r4 >= r5) goto L7a
                    char r5 = r1.charAt(r4)
                    r8 = 55356(0xd83c, float:7.757E-41)
                    if (r5 != r8) goto L45
                    int r5 = r4 + 1
                    char r8 = r1.charAt(r5)
                    r9 = 57339(0xdffb, float:8.0349E-41)
                    if (r8 < r9) goto L45
                    char r5 = r1.charAt(r5)
                    r8 = 57343(0xdfff, float:8.0355E-41)
                    if (r5 <= r8) goto L5f
                L45:
                    char r5 = r1.charAt(r4)
                    r8 = 8205(0x200d, float:1.1498E-41)
                    if (r5 != r8) goto L7a
                    int r5 = r4 + 1
                    char r8 = r1.charAt(r5)
                    r9 = 9792(0x2640, float:1.3722E-41)
                    if (r8 == r9) goto L5f
                    char r5 = r1.charAt(r5)
                    r8 = 9794(0x2642, float:1.3724E-41)
                    if (r5 != r8) goto L7a
                L5f:
                    java.lang.CharSequence[] r5 = new java.lang.CharSequence[r6]
                    java.lang.CharSequence r6 = r1.subSequence(r3, r4)
                    r5[r3] = r6
                    int r6 = r4 + 2
                    int r8 = r1.length()
                    java.lang.CharSequence r1 = r1.subSequence(r6, r8)
                    r5[r7] = r1
                    java.lang.CharSequence r1 = android.text.TextUtils.concat(r5)
                    int r2 = r2 + (-2)
                    goto L9d
                L7a:
                    char r5 = r1.charAt(r4)
                    r8 = 65039(0xfe0f, float:9.1139E-41)
                    if (r5 != r8) goto L9f
                    java.lang.CharSequence[] r5 = new java.lang.CharSequence[r6]
                    java.lang.CharSequence r6 = r1.subSequence(r3, r4)
                    r5[r3] = r6
                    int r6 = r4 + 1
                    int r8 = r1.length()
                    java.lang.CharSequence r1 = r1.subSequence(r6, r8)
                    r5[r7] = r1
                    java.lang.CharSequence r1 = android.text.TextUtils.concat(r5)
                    int r2 = r2 + (-1)
                L9d:
                    int r4 = r4 + (-1)
                L9f:
                    int r4 = r4 + r7
                    goto L20
                La2:
                    if (r0 == 0) goto Laf
                    java.lang.String r1 = r1.toString()
                    java.lang.Object r0 = r0.get(r1)
                    java.util.ArrayList r0 = (java.util.ArrayList) r0
                    goto Lb0
                Laf:
                    r0 = 0
                Lb0:
                    if (r0 == 0) goto Le7
                    boolean r1 = r0.isEmpty()
                    if (r1 != 0) goto Le7
                    java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r1 = r10.emojiStickersArray
                    r1.addAll(r0)
                    int r1 = r0.size()
                Lc1:
                    if (r3 >= r1) goto Ld3
                    java.lang.Object r2 = r0.get(r3)
                    org.telegram.tgnet.TLRPC$Document r2 = (org.telegram.tgnet.TLRPC$Document) r2
                    android.util.LongSparseArray<org.telegram.tgnet.TLRPC$Document> r4 = r10.emojiStickersMap
                    long r5 = r2.id
                    r4.put(r5, r2)
                    int r3 = r3 + 1
                    goto Lc1
                Ld3:
                    java.util.HashMap<java.util.ArrayList<org.telegram.tgnet.TLRPC$Document>, java.lang.String> r0 = r10.emojiStickers
                    java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r1 = r10.emojiStickersArray
                    org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter r2 = org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.this
                    java.lang.String r2 = org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.access$19500(r2)
                    r0.put(r1, r2)
                    java.util.ArrayList<java.util.ArrayList<org.telegram.tgnet.TLRPC$Document>> r0 = r10.emojiArrays
                    java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r1 = r10.emojiStickersArray
                    r0.add(r1)
                Le7:
                    r11.run()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.AnonymousClass1.addFromAllStickers(java.lang.Runnable):void");
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addFromSuggestions(final Runnable runnable) {
                final HashMap<String, ArrayList<TLRPC$Document>> allStickers = MediaDataController.getInstance(EmojiView.this.currentAccount).getAllStickers();
                if (allStickers != null && !allStickers.isEmpty() && this.query.length() > 1) {
                    String[] currentKeyboardLanguage = AndroidUtilities.getCurrentKeyboardLanguage();
                    if (!Arrays.equals(EmojiView.this.lastSearchKeyboardLanguage, currentKeyboardLanguage)) {
                        MediaDataController.getInstance(EmojiView.this.currentAccount).fetchNewEmojiKeywords(currentKeyboardLanguage);
                    }
                    EmojiView.this.lastSearchKeyboardLanguage = currentKeyboardLanguage;
                    MediaDataController.getInstance(EmojiView.this.currentAccount).getEmojiSuggestions(EmojiView.this.lastSearchKeyboardLanguage, StickersSearchGridAdapter.this.searchQuery, false, new MediaDataController.KeywordResultCallback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda3
                        @Override // org.telegram.messenger.MediaDataController.KeywordResultCallback
                        public final void run(ArrayList arrayList, String str) {
                            EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.lambda$addFromSuggestions$0(allStickers, runnable, arrayList, str);
                        }
                    }, false);
                    return;
                }
                runnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$addFromSuggestions$0(HashMap hashMap, Runnable runnable, ArrayList arrayList, String str) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    String str2 = ((MediaDataController.KeywordResult) arrayList.get(i)).emoji;
                    ArrayList<TLRPC$Document> arrayList2 = (ArrayList) hashMap.get(str2);
                    if (arrayList2 != null && !arrayList2.isEmpty() && !this.emojiStickers.containsKey(arrayList2)) {
                        this.emojiStickers.put(arrayList2, str2);
                        this.emojiArrays.add(arrayList2);
                    }
                }
                runnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addLocalPacks(Runnable runnable) {
                int indexOfIgnoreCase;
                int indexOfIgnoreCase2;
                ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSets(0);
                MessagesController.getInstance(EmojiView.this.currentAccount).filterPremiumStickers(stickerSets);
                int size = stickerSets.size();
                for (int i = 0; i < size; i++) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = stickerSets.get(i);
                    int indexOfIgnoreCase3 = AndroidUtilities.indexOfIgnoreCase(tLRPC$TL_messages_stickerSet.set.title, StickersSearchGridAdapter.this.searchQuery);
                    if (indexOfIgnoreCase3 >= 0) {
                        if (indexOfIgnoreCase3 == 0 || tLRPC$TL_messages_stickerSet.set.title.charAt(indexOfIgnoreCase3 - 1) == ' ') {
                            this.localPacks.add(tLRPC$TL_messages_stickerSet);
                            this.localPacksByName.put(tLRPC$TL_messages_stickerSet, Integer.valueOf(indexOfIgnoreCase3));
                        }
                    } else {
                        String str = tLRPC$TL_messages_stickerSet.set.short_name;
                        if (str != null && (indexOfIgnoreCase2 = AndroidUtilities.indexOfIgnoreCase(str, StickersSearchGridAdapter.this.searchQuery)) >= 0 && (indexOfIgnoreCase2 == 0 || tLRPC$TL_messages_stickerSet.set.short_name.charAt(indexOfIgnoreCase2 - 1) == ' ')) {
                            this.localPacks.add(tLRPC$TL_messages_stickerSet);
                            this.localPacksByShortName.put(tLRPC$TL_messages_stickerSet, Boolean.TRUE);
                        }
                    }
                }
                ArrayList<TLRPC$TL_messages_stickerSet> stickerSets2 = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSets(3);
                MessagesController.getInstance(EmojiView.this.currentAccount).filterPremiumStickers(stickerSets2);
                int size2 = stickerSets2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet2 = stickerSets2.get(i2);
                    int indexOfIgnoreCase4 = AndroidUtilities.indexOfIgnoreCase(tLRPC$TL_messages_stickerSet2.set.title, StickersSearchGridAdapter.this.searchQuery);
                    if (indexOfIgnoreCase4 >= 0) {
                        if (indexOfIgnoreCase4 == 0 || tLRPC$TL_messages_stickerSet2.set.title.charAt(indexOfIgnoreCase4 - 1) == ' ') {
                            this.localPacks.add(tLRPC$TL_messages_stickerSet2);
                            this.localPacksByName.put(tLRPC$TL_messages_stickerSet2, Integer.valueOf(indexOfIgnoreCase4));
                        }
                    } else {
                        String str2 = tLRPC$TL_messages_stickerSet2.set.short_name;
                        if (str2 != null && (indexOfIgnoreCase = AndroidUtilities.indexOfIgnoreCase(str2, StickersSearchGridAdapter.this.searchQuery)) >= 0 && (indexOfIgnoreCase == 0 || tLRPC$TL_messages_stickerSet2.set.short_name.charAt(indexOfIgnoreCase - 1) == ' ')) {
                            this.localPacks.add(tLRPC$TL_messages_stickerSet2);
                            this.localPacksByShortName.put(tLRPC$TL_messages_stickerSet2, Boolean.TRUE);
                        }
                    }
                }
                runnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void searchStickerSets(final Runnable runnable) {
                TLRPC$TL_messages_searchStickerSets tLRPC$TL_messages_searchStickerSets = new TLRPC$TL_messages_searchStickerSets();
                tLRPC$TL_messages_searchStickerSets.f38q = this.query;
                StickersSearchGridAdapter stickersSearchGridAdapter = StickersSearchGridAdapter.this;
                stickersSearchGridAdapter.reqId = ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tLRPC$TL_messages_searchStickerSets, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda9
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.lambda$searchStickerSets$2(runnable, tLObject, tLRPC$TL_error);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$searchStickerSets$2(final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.lambda$searchStickerSets$1(tLObject, runnable);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$searchStickerSets$1(TLObject tLObject, Runnable runnable) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                if (tLObject instanceof TLRPC$TL_messages_foundStickerSets) {
                    StickersSearchGridAdapter.this.reqId = 0;
                    this.serverPacks.addAll(((TLRPC$TL_messages_foundStickerSets) tLObject).sets);
                }
                runnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void searchStickers(final Runnable runnable) {
                if (Emoji.fullyConsistsOfEmojis(StickersSearchGridAdapter.this.searchQuery)) {
                    final TLRPC$TL_messages_getStickers tLRPC$TL_messages_getStickers = new TLRPC$TL_messages_getStickers();
                    tLRPC$TL_messages_getStickers.emoticon = this.query;
                    tLRPC$TL_messages_getStickers.hash = 0L;
                    StickersSearchGridAdapter stickersSearchGridAdapter = StickersSearchGridAdapter.this;
                    stickersSearchGridAdapter.reqId2 = ConnectionsManager.getInstance(EmojiView.this.currentAccount).sendRequest(tLRPC$TL_messages_getStickers, new RequestDelegate() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda10
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.lambda$searchStickers$4(tLRPC$TL_messages_getStickers, runnable, tLObject, tLRPC$TL_error);
                        }
                    });
                    return;
                }
                runnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$searchStickers$4(final TLRPC$TL_messages_getStickers tLRPC$TL_messages_getStickers, final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.lambda$searchStickers$3(tLRPC$TL_messages_getStickers, tLObject, runnable);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$searchStickers$3(TLRPC$TL_messages_getStickers tLRPC$TL_messages_getStickers, TLObject tLObject, Runnable runnable) {
                if (StickersSearchGridAdapter.this.emojiSearchId != this.lastId) {
                    return;
                }
                StickersSearchGridAdapter.this.reqId2 = 0;
                if (tLRPC$TL_messages_getStickers.emoticon.equals(this.query)) {
                    if (!(tLObject instanceof TLRPC$TL_messages_stickers)) {
                        runnable.run();
                        return;
                    }
                    TLRPC$TL_messages_stickers tLRPC$TL_messages_stickers = (TLRPC$TL_messages_stickers) tLObject;
                    int size = this.emojiStickersArray.size();
                    int size2 = tLRPC$TL_messages_stickers.stickers.size();
                    for (int i = 0; i < size2; i++) {
                        TLRPC$Document tLRPC$Document = tLRPC$TL_messages_stickers.stickers.get(i);
                        if (this.emojiStickersMap.indexOfKey(tLRPC$Document.id) < 0) {
                            this.emojiStickersArray.add(tLRPC$Document);
                        }
                    }
                    if (size != this.emojiStickersArray.size()) {
                        this.emojiStickers.put(this.emojiStickersArray, StickersSearchGridAdapter.this.searchQuery);
                        if (size == 0) {
                            this.emojiArrays.add(this.emojiStickersArray);
                        }
                    }
                }
                runnable.run();
            }

            @Override // java.lang.Runnable
            public void run() {
                if (TextUtils.isEmpty(StickersSearchGridAdapter.this.searchQuery)) {
                    if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersGridAdapter) {
                        EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersGridAdapter);
                    }
                    StickersSearchGridAdapter.this.notifyDataSetChanged();
                    return;
                }
                this.lastId = StickersSearchGridAdapter.access$18804(StickersSearchGridAdapter.this);
                this.query = StickersSearchGridAdapter.this.searchQuery;
                this.serverPacks.clear();
                this.localPacks.clear();
                this.localPacksByShortName.clear();
                this.localPacksByName.clear();
                this.emojiStickers.clear();
                this.emojiArrays.clear();
                this.emojiStickersArray.clear();
                this.emojiStickersMap.clear();
                EmojiView.this.stickersSearchField.showProgress(true);
                Utilities.raceCallbacks(new Runnable() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.searchFinish();
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda5
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.addFromAllStickers((Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda6
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.addFromSuggestions((Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.addLocalPacks((Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda4
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.searchStickerSets((Runnable) obj);
                    }
                }, new Utilities.Callback() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$1$$ExternalSyntheticLambda8
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        EmojiView.StickersSearchGridAdapter.AnonymousClass1.this.searchStickers((Runnable) obj);
                    }
                });
            }
        }

        public StickersSearchGridAdapter(Context context) {
            this.context = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int i = this.totalItems;
            if (i != 1) {
                return i + 1;
            }
            return 2;
        }

        public void search(String str, boolean z) {
            if (this.reqId != 0) {
                ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId, true);
                this.reqId = 0;
            }
            if (this.reqId2 != 0) {
                ConnectionsManager.getInstance(EmojiView.this.currentAccount).cancelRequest(this.reqId2, true);
                this.reqId2 = 0;
            }
            if (TextUtils.isEmpty(str)) {
                this.searchQuery = null;
                this.localPacks.clear();
                this.emojiStickers.clear();
                this.serverPacks.clear();
                if (EmojiView.this.stickersGridView.getAdapter() != EmojiView.this.stickersGridAdapter) {
                    EmojiView.this.stickersGridView.setAdapter(EmojiView.this.stickersGridAdapter);
                }
                notifyDataSetChanged();
                EmojiView.this.stickersSearchField.showProgress(false);
            } else {
                this.searchQuery = str.toLowerCase();
                EmojiView.this.stickersSearchField.showProgress(true);
            }
            AndroidUtilities.cancelRunOnUIThread(this.searchRunnable);
            AndroidUtilities.runOnUIThread(this.searchRunnable, 300L);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (i == 0) {
                return 4;
            }
            if (i == 1 && this.totalItems == 1) {
                return 5;
            }
            Object obj = this.cache.get(i);
            if (obj == null) {
                return 1;
            }
            if (obj instanceof TLRPC$Document) {
                return 0;
            }
            return obj instanceof TLRPC$StickerSetCovered ? 3 : 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) view.getParent();
            TLRPC$StickerSetCovered stickerSet = featuredStickerSetInfoCell.getStickerSet();
            if (EmojiView.this.installingStickerSets.indexOfKey(stickerSet.set.id) >= 0 || EmojiView.this.removingStickerSets.indexOfKey(stickerSet.set.id) >= 0) {
                return;
            }
            if (featuredStickerSetInfoCell.isInstalled()) {
                EmojiView.this.removingStickerSets.put(stickerSet.set.id, stickerSet);
                EmojiView.this.delegate.onStickerSetRemove(featuredStickerSetInfoCell.getStickerSet());
            } else {
                featuredStickerSetInfoCell.setAddDrawProgress(true, true);
                EmojiView.this.installingStickerSets.put(stickerSet.set.id, stickerSet);
                EmojiView.this.delegate.onStickerSetAdd(featuredStickerSetInfoCell.getStickerSet());
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            FrameLayout frameLayout;
            View view;
            boolean z = true;
            if (i == 0) {
                frameLayout = new StickerEmojiCell(this, this.context, z) { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.2
                    @Override // android.widget.FrameLayout, android.view.View
                    public void onMeasure(int i2, int i3) {
                        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                    }
                };
            } else {
                if (i == 1) {
                    view = new EmptyCell(this.context);
                } else if (i == 2) {
                    view = new StickerSetNameCell(this.context, false, EmojiView.this.resourcesProvider);
                } else if (i == 3) {
                    FeaturedStickerSetInfoCell featuredStickerSetInfoCell = new FeaturedStickerSetInfoCell(this.context, 17, false, true, EmojiView.this.resourcesProvider);
                    featuredStickerSetInfoCell.setAddOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiView$StickersSearchGridAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            EmojiView.StickersSearchGridAdapter.this.lambda$onCreateViewHolder$0(view2);
                        }
                    });
                    view = featuredStickerSetInfoCell;
                } else if (i == 4) {
                    View view2 = new View(this.context);
                    view2.setLayoutParams(new RecyclerView.LayoutParams(-1, EmojiView.this.searchFieldHeight));
                    view = view2;
                } else if (i != 5) {
                    view = null;
                } else {
                    frameLayout = new FrameLayout(this.context) { // from class: org.telegram.ui.Components.EmojiView.StickersSearchGridAdapter.3
                        @Override // android.widget.FrameLayout, android.view.View
                        protected void onMeasure(int i2, int i3) {
                            super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec((int) ((((EmojiView.this.stickersGridView.getMeasuredHeight() - EmojiView.this.searchFieldHeight) - AndroidUtilities.dp(8.0f)) / 3) * 1.7f), 1073741824));
                        }
                    };
                    ImageView imageView = new ImageView(this.context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setImageResource(R.drawable.stickers_empty);
                    EmojiView emojiView = EmojiView.this;
                    int i2 = Theme.key_chat_emojiPanelEmptyText;
                    imageView.setColorFilter(new PorterDuffColorFilter(emojiView.getThemedColor(i2), PorterDuff.Mode.MULTIPLY));
                    imageView.setTranslationY(-AndroidUtilities.dp(24.0f));
                    frameLayout.addView(imageView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 28.0f));
                    TextView textView = new TextView(this.context);
                    textView.setText(LocaleController.getString("NoStickersFound", R.string.NoStickersFound));
                    textView.setTextSize(1, 16.0f);
                    textView.setTextColor(EmojiView.this.getThemedColor(i2));
                    frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 42.0f, 0.0f, 9.0f));
                    frameLayout.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                }
                return new RecyclerListView.Holder(view);
            }
            view = frameLayout;
            return new RecyclerListView.Holder(view);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            int itemViewType = viewHolder.getItemViewType();
            boolean z = true;
            z = true;
            if (itemViewType == 0) {
                TLRPC$Document tLRPC$Document = (TLRPC$Document) this.cache.get(i);
                StickerEmojiCell stickerEmojiCell = (StickerEmojiCell) viewHolder.itemView;
                stickerEmojiCell.setSticker(tLRPC$Document, null, this.cacheParent.get(i), this.positionToEmoji.get(i), false);
                if (!EmojiView.this.recentStickers.contains(tLRPC$Document) && !EmojiView.this.favouriteStickers.contains(tLRPC$Document)) {
                    z = false;
                }
                stickerEmojiCell.setRecent(z);
                return;
            }
            Integer num = null;
            if (itemViewType == 1) {
                EmptyCell emptyCell = (EmptyCell) viewHolder.itemView;
                if (i == this.totalItems) {
                    int i2 = this.positionToRow.get(i - 1, LinearLayoutManager.INVALID_OFFSET);
                    if (i2 == Integer.MIN_VALUE) {
                        emptyCell.setHeight(1);
                        return;
                    }
                    Object obj = this.rowStartPack.get(i2);
                    if (obj instanceof TLRPC$TL_messages_stickerSet) {
                        num = Integer.valueOf(((TLRPC$TL_messages_stickerSet) obj).documents.size());
                    } else if (obj instanceof Integer) {
                        num = (Integer) obj;
                    }
                    if (num == null) {
                        emptyCell.setHeight(1);
                        return;
                    } else if (num.intValue() != 0) {
                        int height = EmojiView.this.pager.getHeight() - (((int) Math.ceil(num.intValue() / EmojiView.this.stickersGridAdapter.stickersPerRow)) * AndroidUtilities.dp(82.0f));
                        emptyCell.setHeight(height > 0 ? height : 1);
                        return;
                    } else {
                        emptyCell.setHeight(AndroidUtilities.dp(8.0f));
                        return;
                    }
                }
                emptyCell.setHeight(AndroidUtilities.dp(82.0f));
                return;
            }
            if (itemViewType == 2) {
                StickerSetNameCell stickerSetNameCell = (StickerSetNameCell) viewHolder.itemView;
                Object obj2 = this.cache.get(i);
                if (obj2 instanceof TLRPC$TL_messages_stickerSet) {
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) obj2;
                    if (!TextUtils.isEmpty(this.searchQuery) && this.localPacksByShortName.containsKey(tLRPC$TL_messages_stickerSet)) {
                        TLRPC$StickerSet tLRPC$StickerSet = tLRPC$TL_messages_stickerSet.set;
                        if (tLRPC$StickerSet != null) {
                            stickerSetNameCell.setText(tLRPC$StickerSet.title, 0);
                        }
                        stickerSetNameCell.setUrl(tLRPC$TL_messages_stickerSet.set.short_name, this.searchQuery.length());
                        return;
                    }
                    Integer num2 = this.localPacksByName.get(tLRPC$TL_messages_stickerSet);
                    TLRPC$StickerSet tLRPC$StickerSet2 = tLRPC$TL_messages_stickerSet.set;
                    if (tLRPC$StickerSet2 != null && num2 != null) {
                        stickerSetNameCell.setText(tLRPC$StickerSet2.title, 0, num2.intValue(), !TextUtils.isEmpty(this.searchQuery) ? this.searchQuery.length() : 0);
                    }
                    stickerSetNameCell.setUrl(null, 0);
                    return;
                }
                return;
            }
            if (itemViewType != 3) {
                return;
            }
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered = (TLRPC$StickerSetCovered) this.cache.get(i);
            FeaturedStickerSetInfoCell featuredStickerSetInfoCell = (FeaturedStickerSetInfoCell) viewHolder.itemView;
            boolean z2 = EmojiView.this.installingStickerSets.indexOfKey(tLRPC$StickerSetCovered.set.id) >= 0;
            boolean z3 = EmojiView.this.removingStickerSets.indexOfKey(tLRPC$StickerSetCovered.set.id) >= 0;
            if (z2 || z3) {
                if (z2 && featuredStickerSetInfoCell.isInstalled()) {
                    EmojiView.this.installingStickerSets.remove(tLRPC$StickerSetCovered.set.id);
                    z2 = false;
                } else if (z3 && !featuredStickerSetInfoCell.isInstalled()) {
                    EmojiView.this.removingStickerSets.remove(tLRPC$StickerSetCovered.set.id);
                }
            }
            featuredStickerSetInfoCell.setAddDrawProgress(z2, false);
            int indexOfIgnoreCase = TextUtils.isEmpty(this.searchQuery) ? -1 : AndroidUtilities.indexOfIgnoreCase(tLRPC$StickerSetCovered.set.title, this.searchQuery);
            if (indexOfIgnoreCase >= 0) {
                featuredStickerSetInfoCell.setStickerSet(tLRPC$StickerSetCovered, false, false, indexOfIgnoreCase, this.searchQuery.length());
                return;
            }
            featuredStickerSetInfoCell.setStickerSet(tLRPC$StickerSetCovered, false);
            if (TextUtils.isEmpty(this.searchQuery) || AndroidUtilities.indexOfIgnoreCase(tLRPC$StickerSetCovered.set.short_name, this.searchQuery) != 0) {
                return;
            }
            featuredStickerSetInfoCell.setUrl(tLRPC$StickerSetCovered.set.short_name, this.searchQuery.length());
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v19, types: [org.telegram.tgnet.TLRPC$TL_messages_stickerSet, org.telegram.tgnet.TLRPC$messages_StickerSet] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void notifyDataSetChanged() {
            int i;
            ArrayList<TLRPC$Document> arrayList;
            TLRPC$StickerSetCovered tLRPC$StickerSetCovered;
            this.rowStartPack.clear();
            this.positionToRow.clear();
            this.cache.clear();
            this.positionsToSets.clear();
            this.positionToEmoji.clear();
            this.totalItems = 0;
            int size = this.serverPacks.size();
            int size2 = this.localPacks.size();
            int i2 = !this.emojiArrays.isEmpty() ? 1 : 0;
            int i3 = -1;
            int i4 = -1;
            int i5 = 0;
            while (i4 < size + size2 + i2) {
                if (i4 == i3) {
                    SparseArray<Object> sparseArray = this.cache;
                    int i6 = this.totalItems;
                    this.totalItems = i6 + 1;
                    sparseArray.put(i6, "search");
                    i5++;
                    i = size;
                } else {
                    if (i4 < size2) {
                        TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = this.localPacks.get(i4);
                        arrayList = tLRPC$TL_messages_stickerSet.documents;
                        i = size;
                        tLRPC$StickerSetCovered = tLRPC$TL_messages_stickerSet;
                    } else {
                        int i7 = i4 - size2;
                        if (i7 < i2) {
                            int size3 = this.emojiArrays.size();
                            String str = "";
                            int i8 = 0;
                            for (int i9 = 0; i9 < size3; i9++) {
                                ArrayList<TLRPC$Document> arrayList2 = this.emojiArrays.get(i9);
                                String str2 = this.emojiStickers.get(arrayList2);
                                if (str2 != null && !str.equals(str2)) {
                                    this.positionToEmoji.put(this.totalItems + i8, str2);
                                    str = str2;
                                }
                                int size4 = arrayList2.size();
                                int i10 = 0;
                                while (i10 < size4) {
                                    int i11 = this.totalItems + i8;
                                    int i12 = (i8 / EmojiView.this.stickersGridAdapter.stickersPerRow) + i5;
                                    TLRPC$Document tLRPC$Document = arrayList2.get(i10);
                                    int i13 = size;
                                    this.cache.put(i11, tLRPC$Document);
                                    int i14 = size3;
                                    String str3 = str;
                                    TLRPC$TL_messages_stickerSet stickerSetById = MediaDataController.getInstance(EmojiView.this.currentAccount).getStickerSetById(MediaDataController.getStickerSetId(tLRPC$Document));
                                    if (stickerSetById != null) {
                                        this.cacheParent.put(i11, stickerSetById);
                                    }
                                    this.positionToRow.put(i11, i12);
                                    i8++;
                                    i10++;
                                    size = i13;
                                    size3 = i14;
                                    str = str3;
                                }
                            }
                            i = size;
                            int ceil = (int) Math.ceil(i8 / EmojiView.this.stickersGridAdapter.stickersPerRow);
                            for (int i15 = 0; i15 < ceil; i15++) {
                                this.rowStartPack.put(i5 + i15, Integer.valueOf(i8));
                            }
                            this.totalItems += EmojiView.this.stickersGridAdapter.stickersPerRow * ceil;
                            i5 += ceil;
                        } else {
                            i = size;
                            TLRPC$StickerSetCovered tLRPC$StickerSetCovered2 = this.serverPacks.get(i7 - i2);
                            arrayList = tLRPC$StickerSetCovered2.covers;
                            tLRPC$StickerSetCovered = tLRPC$StickerSetCovered2;
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        int ceil2 = (int) Math.ceil(arrayList.size() / EmojiView.this.stickersGridAdapter.stickersPerRow);
                        this.cache.put(this.totalItems, tLRPC$StickerSetCovered);
                        if (i4 >= size2 && (tLRPC$StickerSetCovered instanceof TLRPC$StickerSetCovered)) {
                            this.positionsToSets.put(this.totalItems, tLRPC$StickerSetCovered);
                        }
                        this.positionToRow.put(this.totalItems, i5);
                        int size5 = arrayList.size();
                        int i16 = 0;
                        while (i16 < size5) {
                            int i17 = i16 + 1;
                            int i18 = this.totalItems + i17;
                            int i19 = i5 + 1 + (i16 / EmojiView.this.stickersGridAdapter.stickersPerRow);
                            this.cache.put(i18, arrayList.get(i16));
                            this.cacheParent.put(i18, tLRPC$StickerSetCovered);
                            this.positionToRow.put(i18, i19);
                            if (i4 >= size2 && (tLRPC$StickerSetCovered instanceof TLRPC$StickerSetCovered)) {
                                this.positionsToSets.put(i18, tLRPC$StickerSetCovered);
                            }
                            i16 = i17;
                        }
                        int i20 = ceil2 + 1;
                        for (int i21 = 0; i21 < i20; i21++) {
                            this.rowStartPack.put(i5 + i21, tLRPC$StickerSetCovered);
                        }
                        this.totalItems += (ceil2 * EmojiView.this.stickersGridAdapter.stickersPerRow) + 1;
                        i5 += i20;
                    }
                }
                i4++;
                size = i;
                i3 = -1;
            }
            super.notifyDataSetChanged();
        }
    }

    public void searchProgressChanged() {
        updateStickerTabsPosition();
    }

    public float getStickersExpandOffset() {
        ScrollSlidingTabStrip scrollSlidingTabStrip = this.stickersTab;
        if (scrollSlidingTabStrip == null) {
            return 0.0f;
        }
        return scrollSlidingTabStrip.getExpandedOffset();
    }

    public void setShowing(boolean z) {
        this.showing = z;
        updateStickerTabsPosition();
    }

    public void onMessageSend() {
        ChooseStickerActionTracker chooseStickerActionTracker = this.chooseStickerActionTracker;
        if (chooseStickerActionTracker != null) {
            chooseStickerActionTracker.reset();
        }
    }

    public static abstract class ChooseStickerActionTracker {
        private final int currentAccount;
        private final long dialogId;
        private final int threadId;
        boolean typingWasSent;
        boolean visible = false;
        long lastActionTime = -1;

        public abstract boolean isShown();

        public ChooseStickerActionTracker(int i, long j, int i2) {
            this.currentAccount = i;
            this.dialogId = j;
            this.threadId = i2;
        }

        public void doSomeAction() {
            if (this.visible) {
                if (this.lastActionTime == -1) {
                    this.lastActionTime = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - this.lastActionTime > 2000) {
                    this.typingWasSent = true;
                    this.lastActionTime = System.currentTimeMillis();
                    MessagesController.getInstance(this.currentAccount).sendTyping(this.dialogId, this.threadId, 10, 0);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            if (this.typingWasSent) {
                MessagesController.getInstance(this.currentAccount).sendTyping(this.dialogId, this.threadId, 2, 0);
            }
            this.lastActionTime = -1L;
        }

        public void checkVisibility() {
            boolean isShown = isShown();
            this.visible = isShown;
            if (isShown) {
                return;
            }
            reset();
        }
    }

    private class Tab {
        int type;
        View view;

        private Tab(EmojiView emojiView) {
        }
    }
}
