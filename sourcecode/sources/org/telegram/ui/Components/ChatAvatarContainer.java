package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipants;
import org.telegram.tgnet.TLRPC$EmojiStatus;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_emojiStatus;
import org.telegram.tgnet.TLRPC$TL_emojiStatusUntil;
import org.telegram.tgnet.TLRPC$TL_forumTopic;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$UserStatus;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AnimatedEmojiDrawable;
import org.telegram.ui.Components.AutoDeletePopupWrapper;
import org.telegram.ui.Components.SharedMediaLayout;
import org.telegram.ui.TopicsFragment;

/* loaded from: classes4.dex */
public class ChatAvatarContainer extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    public boolean allowShorterStatus;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImageView;
    private int currentAccount;
    private int currentConnectionState;
    StatusDrawable currentTypingDrawable;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatusDrawable;
    private boolean[] isOnline;
    private int largerWidth;
    private CharSequence lastSubtitle;
    private int lastSubtitleColorKey;
    private int lastWidth;
    private int leftPadding;
    private boolean occupyStatusBar;
    private int onlineCount;
    private Integer overrideSubtitleColor;
    private ChatActivity parentFragment;
    private Theme.ResourcesProvider resourcesProvider;
    private String rightDrawableContentDescription;
    private boolean rightDrawableIsScamOrVerified;
    private boolean secretChatTimer;
    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private StatusDrawable[] statusDrawables;
    public boolean[] statusMadeShorter;
    private AtomicReference<SimpleTextView> subtitleTextLargerCopyView;
    private SimpleTextView subtitleTextView;
    private ImageView timeItem;
    private TimerDrawable timerDrawable;
    private AnimatorSet titleAnimation;
    private AtomicReference<SimpleTextView> titleTextLargerCopyView;
    private SimpleTextView titleTextView;

    protected boolean onAvatarClick() {
        return false;
    }

    private class SimpleTextConnectedView extends SimpleTextView {
        private AtomicReference<SimpleTextView> reference;

        public SimpleTextConnectedView(ChatAvatarContainer chatAvatarContainer, Context context, AtomicReference<SimpleTextView> atomicReference) {
            super(context);
            this.reference = atomicReference;
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            SimpleTextView simpleTextView;
            AtomicReference<SimpleTextView> atomicReference = this.reference;
            if (atomicReference != null && (simpleTextView = atomicReference.get()) != null) {
                simpleTextView.setTranslationY(f);
            }
            super.setTranslationY(f);
        }

        @Override // org.telegram.ui.ActionBar.SimpleTextView
        public boolean setText(CharSequence charSequence) {
            SimpleTextView simpleTextView;
            AtomicReference<SimpleTextView> atomicReference = this.reference;
            if (atomicReference != null && (simpleTextView = atomicReference.get()) != null) {
                simpleTextView.setText(charSequence);
            }
            return super.setText(charSequence);
        }
    }

    public ChatAvatarContainer(Context context, BaseFragment baseFragment, boolean z) {
        this(context, baseFragment, z, null);
    }

    public ChatAvatarContainer(Context context, BaseFragment baseFragment, boolean z, final Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.titleTextLargerCopyView = new AtomicReference<>();
        this.subtitleTextLargerCopyView = new AtomicReference<>();
        this.statusDrawables = new StatusDrawable[6];
        this.avatarDrawable = new AvatarDrawable();
        this.currentAccount = UserConfig.selectedAccount;
        this.occupyStatusBar = true;
        this.leftPadding = AndroidUtilities.dp(8.0f);
        this.lastWidth = -1;
        this.largerWidth = -1;
        this.isOnline = new boolean[1];
        this.statusMadeShorter = new boolean[1];
        this.onlineCount = -1;
        this.lastSubtitleColorKey = -1;
        this.allowShorterStatus = false;
        this.rightDrawableIsScamOrVerified = false;
        this.rightDrawableContentDescription = null;
        this.resourcesProvider = resourcesProvider;
        boolean z2 = baseFragment instanceof ChatActivity;
        if (z2) {
            this.parentFragment = (ChatActivity) baseFragment;
        }
        ChatActivity chatActivity = this.parentFragment;
        final boolean z3 = (chatActivity == null || chatActivity.getChatMode() != 0 || UserObject.isReplyUser(this.parentFragment.getCurrentUser())) ? false : true;
        this.avatarImageView = new BackupImageView(this, context) { // from class: org.telegram.ui.Components.ChatAvatarContainer.1
            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                if (z3 && getImageReceiver().hasNotThumb()) {
                    accessibilityNodeInfo.setText(LocaleController.getString("AccDescrProfilePicture", R.string.AccDescrProfilePicture));
                    if (Build.VERSION.SDK_INT >= 21) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString("Open", R.string.Open)));
                        return;
                    }
                    return;
                }
                accessibilityNodeInfo.setVisibleToUser(false);
            }
        };
        if (z2 || (baseFragment instanceof TopicsFragment)) {
            this.sharedMediaPreloader = new SharedMediaLayout.SharedMediaPreloader(baseFragment);
            ChatActivity chatActivity2 = this.parentFragment;
            if (chatActivity2 != null && (chatActivity2.isThreadChat() || this.parentFragment.getChatMode() == 2)) {
                this.avatarImageView.setVisibility(8);
            }
        }
        this.avatarImageView.setContentDescription(LocaleController.getString("AccDescrProfilePicture", R.string.AccDescrProfilePicture));
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(21.0f));
        addView(this.avatarImageView);
        if (z3) {
            this.avatarImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChatAvatarContainer.this.lambda$new$0(view);
                }
            });
        }
        SimpleTextConnectedView simpleTextConnectedView = new SimpleTextConnectedView(this, context, this.titleTextLargerCopyView);
        this.titleTextView = simpleTextConnectedView;
        simpleTextConnectedView.setEllipsizeByGradient(true);
        this.titleTextView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        this.titleTextView.setTextSize(18);
        this.titleTextView.setGravity(3);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.titleTextView.setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
        this.titleTextView.setCanHideRightDrawable(false);
        this.titleTextView.setRightDrawableOutside(true);
        this.titleTextView.setPadding(0, AndroidUtilities.dp(6.0f), 0, AndroidUtilities.dp(12.0f));
        addView(this.titleTextView);
        SimpleTextConnectedView simpleTextConnectedView2 = new SimpleTextConnectedView(this, context, this.subtitleTextLargerCopyView);
        this.subtitleTextView = simpleTextConnectedView2;
        simpleTextConnectedView2.setEllipsizeByGradient(true);
        SimpleTextView simpleTextView = this.subtitleTextView;
        int i = Theme.key_actionBarDefaultSubtitle;
        simpleTextView.setTextColor(getThemedColor(i));
        this.subtitleTextView.setTag(Integer.valueOf(i));
        this.subtitleTextView.setTextSize(14);
        this.subtitleTextView.setGravity(3);
        this.subtitleTextView.setPadding(0, 0, AndroidUtilities.dp(10.0f), 0);
        if (BuildVars.isShowChatSubTitle) {
            addView(this.subtitleTextView);
        }
        if (this.parentFragment != null) {
            ImageView imageView = new ImageView(context);
            this.timeItem = imageView;
            imageView.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(5.0f), AndroidUtilities.dp(5.0f));
            this.timeItem.setScaleType(ImageView.ScaleType.CENTER);
            this.timeItem.setAlpha(0.0f);
            this.timeItem.setScaleY(0.0f);
            this.timeItem.setScaleX(0.0f);
            this.timeItem.setVisibility(8);
            ImageView imageView2 = this.timeItem;
            TimerDrawable timerDrawable = new TimerDrawable(context, resourcesProvider);
            this.timerDrawable = timerDrawable;
            imageView2.setImageDrawable(timerDrawable);
            addView(this.timeItem);
            this.secretChatTimer = z;
            this.timeItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChatAvatarContainer.this.lambda$new$1(resourcesProvider, view);
                }
            });
            if (this.secretChatTimer) {
                this.timeItem.setContentDescription(LocaleController.getString("SetTimer", R.string.SetTimer));
            } else {
                this.timeItem.setContentDescription(LocaleController.getString("AccAutoDeleteTimer", R.string.AccAutoDeleteTimer));
            }
        }
        ChatActivity chatActivity3 = this.parentFragment;
        if (chatActivity3 != null && chatActivity3.getChatMode() == 0) {
            if ((!this.parentFragment.isThreadChat() || this.parentFragment.isTopic) && !UserObject.isReplyUser(this.parentFragment.getCurrentUser())) {
                setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ChatAvatarContainer.this.lambda$new$2(view);
                    }
                });
            }
            TLRPC$Chat currentChat = this.parentFragment.getCurrentChat();
            this.statusDrawables[0] = new TypingDotsDrawable(true);
            this.statusDrawables[1] = new RecordStatusDrawable(true);
            this.statusDrawables[2] = new SendingFileDrawable(true);
            this.statusDrawables[3] = new PlayingGameDrawable(false, resourcesProvider);
            this.statusDrawables[4] = new RoundStatusDrawable(true);
            this.statusDrawables[5] = new ChoosingStickerStatusDrawable(true);
            int i2 = 0;
            while (true) {
                StatusDrawable[] statusDrawableArr = this.statusDrawables;
                if (i2 >= statusDrawableArr.length) {
                    break;
                }
                statusDrawableArr[i2].setIsChat(currentChat != null);
                i2++;
            }
        }
        this.emojiStatusDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this.titleTextView, AndroidUtilities.dp(24.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (onAvatarClick()) {
            return;
        }
        openProfile(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(Theme.ResourcesProvider resourcesProvider, View view) {
        if (this.secretChatTimer) {
            this.parentFragment.showDialog(AlertsCreator.createTTLAlert(getContext(), this.parentFragment.getCurrentEncryptedChat(), resourcesProvider).create());
        } else {
            openSetTimer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        openProfile(false);
    }

    public void setTitleExpand(boolean z) {
        int dp = z ? AndroidUtilities.dp(10.0f) : 0;
        if (this.titleTextView.getPaddingRight() != dp) {
            this.titleTextView.setPadding(0, AndroidUtilities.dp(6.0f), dp, AndroidUtilities.dp(12.0f));
            requestLayout();
            invalidate();
        }
    }

    public void setOverrideSubtitleColor(Integer num) {
        this.overrideSubtitleColor = num;
    }

    public boolean openSetTimer() {
        int i;
        if (this.parentFragment.getParentActivity() == null) {
            return false;
        }
        TLRPC$Chat currentChat = this.parentFragment.getCurrentChat();
        if (currentChat != null && !ChatObject.canUserDoAdminAction(currentChat, 13)) {
            if (this.timeItem.getTag() != null) {
                this.parentFragment.showTimerHint();
            }
            return false;
        }
        TLRPC$ChatFull currentChatInfo = this.parentFragment.getCurrentChatInfo();
        TLRPC$UserFull currentUserInfo = this.parentFragment.getCurrentUserInfo();
        if (currentUserInfo != null) {
            i = currentUserInfo.ttl_period;
        } else {
            i = currentChatInfo != null ? currentChatInfo.ttl_period : 0;
        }
        AutoDeletePopupWrapper autoDeletePopupWrapper = new AutoDeletePopupWrapper(getContext(), null, new AutoDeletePopupWrapper.Callback() { // from class: org.telegram.ui.Components.ChatAvatarContainer.2
            @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
            public void dismiss() {
                ActionBarPopupWindow[] actionBarPopupWindowArr = r2;
                if (actionBarPopupWindowArr[0] != null) {
                    actionBarPopupWindowArr[0].dismiss();
                }
            }

            @Override // org.telegram.ui.Components.AutoDeletePopupWrapper.Callback
            public void setAutoDeleteHistory(int i2, int i3) {
                UndoView undoView;
                if (ChatAvatarContainer.this.parentFragment == null) {
                    return;
                }
                ChatAvatarContainer.this.parentFragment.getMessagesController().setDialogHistoryTTL(ChatAvatarContainer.this.parentFragment.getDialogId(), i2);
                TLRPC$ChatFull currentChatInfo2 = ChatAvatarContainer.this.parentFragment.getCurrentChatInfo();
                TLRPC$UserFull currentUserInfo2 = ChatAvatarContainer.this.parentFragment.getCurrentUserInfo();
                if ((currentUserInfo2 == null && currentChatInfo2 == null) || (undoView = ChatAvatarContainer.this.parentFragment.getUndoView()) == null) {
                    return;
                }
                undoView.showWithAction(ChatAvatarContainer.this.parentFragment.getDialogId(), i3, ChatAvatarContainer.this.parentFragment.getCurrentUser(), Integer.valueOf(currentUserInfo2 != null ? currentUserInfo2.ttl_period : currentChatInfo2.ttl_period), (Runnable) null, (Runnable) null);
            }
        }, true, 0, this.resourcesProvider);
        autoDeletePopupWrapper.lambda$updateItems$7(i);
        int i2 = -2;
        final ActionBarPopupWindow[] actionBarPopupWindowArr = {new ActionBarPopupWindow(autoDeletePopupWrapper.windowLayout, i2, i2) { // from class: org.telegram.ui.Components.ChatAvatarContainer.3
            @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                if (ChatAvatarContainer.this.parentFragment != null) {
                    ChatAvatarContainer.this.parentFragment.dimBehindView(false);
                }
            }
        }};
        actionBarPopupWindowArr[0].setPauseNotifications(true);
        actionBarPopupWindowArr[0].setDismissAnimationDuration(220);
        actionBarPopupWindowArr[0].setOutsideTouchable(true);
        actionBarPopupWindowArr[0].setClippingEnabled(true);
        actionBarPopupWindowArr[0].setAnimationStyle(R.style.PopupContextAnimation);
        actionBarPopupWindowArr[0].setFocusable(true);
        autoDeletePopupWrapper.windowLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), LinearLayoutManager.INVALID_OFFSET));
        actionBarPopupWindowArr[0].setInputMethodMode(2);
        actionBarPopupWindowArr[0].getContentView().setFocusableInTouchMode(true);
        ActionBarPopupWindow actionBarPopupWindow = actionBarPopupWindowArr[0];
        BackupImageView backupImageView = this.avatarImageView;
        actionBarPopupWindow.showAtLocation(backupImageView, 0, (int) (backupImageView.getX() + getX()), (int) this.avatarImageView.getY());
        this.parentFragment.dimBehindView(true);
        return true;
    }

    public void openProfile(boolean z) {
        openProfile(z, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001b, code lost:
    
        if (r7.avatarImageView.getImageReceiver().hasNotThumb() != false) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void openProfile(boolean r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ChatAvatarContainer.openProfile(boolean, boolean):void");
    }

    public void setOccupyStatusBar(boolean z) {
        this.occupyStatusBar = z;
    }

    public void setTitleColors(int i, int i2) {
        this.titleTextView.setTextColor(i);
        this.subtitleTextView.setTextColor(i2);
        this.subtitleTextView.setTag(Integer.valueOf(i2));
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i) + this.titleTextView.getPaddingRight();
        int dp = size - AndroidUtilities.dp((this.avatarImageView.getVisibility() == 0 ? 54 : 0) + 16);
        this.avatarImageView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(42.0f), 1073741824));
        this.titleTextView.measure(View.MeasureSpec.makeMeasureSpec(dp, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(32.0f) + this.titleTextView.getPaddingRight(), LinearLayoutManager.INVALID_OFFSET));
        this.subtitleTextView.measure(View.MeasureSpec.makeMeasureSpec(dp, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), LinearLayoutManager.INVALID_OFFSET));
        ImageView imageView = this.timeItem;
        if (imageView != null) {
            imageView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(34.0f), 1073741824));
        }
        setMeasuredDimension(size - AndroidUtilities.dp(56.0f), View.MeasureSpec.getSize(i2));
        int i3 = this.lastWidth;
        if (i3 != -1 && i3 != size && i3 > size) {
            fadeOutToLessWidth(i3);
        }
        SimpleTextView simpleTextView = this.titleTextLargerCopyView.get();
        if (simpleTextView != null) {
            simpleTextView.measure(View.MeasureSpec.makeMeasureSpec(this.largerWidth - AndroidUtilities.dp((this.avatarImageView.getVisibility() != 0 ? 0 : 54) + 16), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), LinearLayoutManager.INVALID_OFFSET));
        }
        this.lastWidth = size;
    }

    private void fadeOutToLessWidth(int i) {
        this.largerWidth = i;
        View view = (SimpleTextView) this.titleTextLargerCopyView.get();
        if (view != null) {
            removeView(view);
        }
        SimpleTextView simpleTextView = new SimpleTextView(getContext());
        this.titleTextLargerCopyView.set(simpleTextView);
        simpleTextView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        simpleTextView.setTextSize(18);
        simpleTextView.setGravity(3);
        simpleTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        simpleTextView.setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
        simpleTextView.setRightDrawable(this.titleTextView.getRightDrawable());
        simpleTextView.setRightDrawableOutside(this.titleTextView.getRightDrawableOutside());
        simpleTextView.setLeftDrawable(this.titleTextView.getLeftDrawable());
        simpleTextView.setText(this.titleTextView.getText());
        ViewPropertyAnimator duration = simpleTextView.animate().alpha(0.0f).setDuration(350L);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        duration.setInterpolator(cubicBezierInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ChatAvatarContainer.this.lambda$fadeOutToLessWidth$3();
            }
        }).start();
        addView(simpleTextView);
        View view2 = (SimpleTextView) this.subtitleTextLargerCopyView.get();
        if (view2 != null) {
            removeView(view2);
        }
        SimpleTextView simpleTextView2 = new SimpleTextView(getContext());
        this.subtitleTextLargerCopyView.set(simpleTextView2);
        int i2 = Theme.key_actionBarDefaultSubtitle;
        simpleTextView2.setTextColor(getThemedColor(i2));
        simpleTextView2.setTag(Integer.valueOf(i2));
        simpleTextView2.setTextSize(14);
        simpleTextView2.setGravity(3);
        simpleTextView2.setText(this.subtitleTextView.getText());
        simpleTextView2.animate().alpha(0.0f).setDuration(350L).setInterpolator(cubicBezierInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.Components.ChatAvatarContainer$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ChatAvatarContainer.this.lambda$fadeOutToLessWidth$4();
            }
        }).start();
        addView(simpleTextView2);
        setClipChildren(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeOutToLessWidth$3() {
        SimpleTextView simpleTextView = this.titleTextLargerCopyView.get();
        if (simpleTextView != null) {
            removeView(simpleTextView);
            this.titleTextLargerCopyView.set(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fadeOutToLessWidth$4() {
        SimpleTextView simpleTextView = this.subtitleTextLargerCopyView.get();
        if (simpleTextView != null) {
            removeView(simpleTextView);
            this.subtitleTextLargerCopyView.set(null);
            setClipChildren(true);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int currentActionBarHeight = ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(42.0f)) / 2) + ((Build.VERSION.SDK_INT < 21 || !this.occupyStatusBar) ? 0 : AndroidUtilities.statusBarHeight);
        BackupImageView backupImageView = this.avatarImageView;
        int i5 = this.leftPadding;
        int i6 = currentActionBarHeight + 1;
        backupImageView.layout(i5, i6, AndroidUtilities.dp(42.0f) + i5, AndroidUtilities.dp(42.0f) + i6);
        int dp = this.leftPadding + (this.avatarImageView.getVisibility() == 0 ? AndroidUtilities.dp(54.0f) : 0);
        SimpleTextView simpleTextView = this.titleTextLargerCopyView.get();
        if (this.subtitleTextView.getVisibility() != 8) {
            this.titleTextView.layout(dp, (AndroidUtilities.dp(1.3f) + currentActionBarHeight) - this.titleTextView.getPaddingTop(), this.titleTextView.getMeasuredWidth() + dp, (((this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.dp(1.3f)) - this.titleTextView.getPaddingTop()) + this.titleTextView.getPaddingBottom());
            if (simpleTextView != null) {
                simpleTextView.layout(dp, AndroidUtilities.dp(1.3f) + currentActionBarHeight, simpleTextView.getMeasuredWidth() + dp, simpleTextView.getTextHeight() + currentActionBarHeight + AndroidUtilities.dp(1.3f));
            }
        } else {
            this.titleTextView.layout(dp, (AndroidUtilities.dp(11.0f) + currentActionBarHeight) - this.titleTextView.getPaddingTop(), this.titleTextView.getMeasuredWidth() + dp, (((this.titleTextView.getTextHeight() + currentActionBarHeight) + AndroidUtilities.dp(11.0f)) - this.titleTextView.getPaddingTop()) + this.titleTextView.getPaddingBottom());
            if (simpleTextView != null) {
                simpleTextView.layout(dp, AndroidUtilities.dp(11.0f) + currentActionBarHeight, simpleTextView.getMeasuredWidth() + dp, simpleTextView.getTextHeight() + currentActionBarHeight + AndroidUtilities.dp(11.0f));
            }
        }
        ImageView imageView = this.timeItem;
        if (imageView != null) {
            imageView.layout(this.leftPadding + AndroidUtilities.dp(16.0f), AndroidUtilities.dp(15.0f) + currentActionBarHeight, this.leftPadding + AndroidUtilities.dp(50.0f), AndroidUtilities.dp(49.0f) + currentActionBarHeight);
        }
        this.subtitleTextView.layout(dp, AndroidUtilities.dp(24.0f) + currentActionBarHeight, this.subtitleTextView.getMeasuredWidth() + dp, this.subtitleTextView.getTextHeight() + currentActionBarHeight + AndroidUtilities.dp(24.0f));
        SimpleTextView simpleTextView2 = this.subtitleTextLargerCopyView.get();
        if (simpleTextView2 != null) {
            simpleTextView2.layout(dp, AndroidUtilities.dp(24.0f) + currentActionBarHeight, simpleTextView2.getMeasuredWidth() + dp, currentActionBarHeight + simpleTextView2.getTextHeight() + AndroidUtilities.dp(24.0f));
        }
    }

    public void setLeftPadding(int i) {
        this.leftPadding = i;
    }

    public void showTimeItem(boolean z) {
        ImageView imageView = this.timeItem;
        if (imageView != null && imageView.getTag() == null && this.avatarImageView.getVisibility() == 0) {
            this.timeItem.clearAnimation();
            this.timeItem.setVisibility(0);
            this.timeItem.setTag(1);
            if (z) {
                this.timeItem.animate().setDuration(180L).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setListener(null).start();
                return;
            }
            this.timeItem.setAlpha(1.0f);
            this.timeItem.setScaleY(1.0f);
            this.timeItem.setScaleX(1.0f);
        }
    }

    public void hideTimeItem(boolean z) {
        ImageView imageView = this.timeItem;
        if (imageView == null || imageView.getTag() == null) {
            return;
        }
        this.timeItem.clearAnimation();
        this.timeItem.setTag(null);
        if (z) {
            this.timeItem.animate().setDuration(180L).alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAvatarContainer.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ChatAvatarContainer.this.timeItem.setVisibility(8);
                    super.onAnimationEnd(animator);
                }
            }).start();
            return;
        }
        this.timeItem.setVisibility(8);
        this.timeItem.setAlpha(0.0f);
        this.timeItem.setScaleY(0.0f);
        this.timeItem.setScaleX(0.0f);
    }

    public void setTime(int i, boolean z) {
        if (this.timerDrawable == null) {
            return;
        }
        if (i != 0 || this.secretChatTimer) {
            showTimeItem(z);
            this.timerDrawable.setTime(i);
        }
    }

    public void setTitleIcons(Drawable drawable, Drawable drawable2) {
        this.titleTextView.setLeftDrawable(drawable);
        if (this.rightDrawableIsScamOrVerified) {
            return;
        }
        if (drawable2 != null) {
            this.rightDrawableContentDescription = LocaleController.getString("NotificationsMuted", R.string.NotificationsMuted);
        } else {
            this.rightDrawableContentDescription = null;
        }
        this.titleTextView.setRightDrawable(drawable2);
    }

    public void setTitle(CharSequence charSequence) {
        setTitle(charSequence, false, false, false, false, null, false);
    }

    public void setTitle(CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4, TLRPC$EmojiStatus tLRPC$EmojiStatus, boolean z5) {
        if (charSequence != null) {
            charSequence = Emoji.replaceEmoji(charSequence, this.titleTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(24.0f), false);
        }
        this.titleTextView.setText(charSequence);
        if (z || z2) {
            if (this.titleTextView.getRightDrawable() instanceof ScamDrawable) {
                return;
            }
            ScamDrawable scamDrawable = new ScamDrawable(11, !z ? 1 : 0);
            scamDrawable.setColor(getThemedColor(Theme.key_actionBarDefaultSubtitle));
            this.titleTextView.setRightDrawable(scamDrawable);
            this.rightDrawableContentDescription = LocaleController.getString("ScamMessage", R.string.ScamMessage);
            this.rightDrawableIsScamOrVerified = true;
            return;
        }
        if (z3) {
            Drawable mutate = getResources().getDrawable(R.drawable.verified_area).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
            Drawable mutate2 = getResources().getDrawable(R.drawable.verified_check).mutate();
            mutate2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedCheck), PorterDuff.Mode.MULTIPLY));
            this.titleTextView.setRightDrawable(new CombinedDrawable(mutate, mutate2));
            this.rightDrawableIsScamOrVerified = true;
            this.rightDrawableContentDescription = LocaleController.getString("AccDescrVerified", R.string.AccDescrVerified);
            return;
        }
        if (z4) {
            boolean z6 = tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatus;
            if (!z6 && (tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatusUntil)) {
                int i = ((TLRPC$TL_emojiStatusUntil) tLRPC$EmojiStatus).until;
                long currentTimeMillis = System.currentTimeMillis() / 1000;
            }
            if ((this.titleTextView.getRightDrawable() instanceof AnimatedEmojiDrawable.WrapSizeDrawable) && (((AnimatedEmojiDrawable.WrapSizeDrawable) this.titleTextView.getRightDrawable()).getDrawable() instanceof AnimatedEmojiDrawable)) {
                ((AnimatedEmojiDrawable) ((AnimatedEmojiDrawable.WrapSizeDrawable) this.titleTextView.getRightDrawable()).getDrawable()).removeView(this.titleTextView);
            }
            if (z6) {
                this.emojiStatusDrawable.set(((TLRPC$TL_emojiStatus) tLRPC$EmojiStatus).document_id, z5);
            } else {
                if (tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatusUntil) {
                    TLRPC$TL_emojiStatusUntil tLRPC$TL_emojiStatusUntil = (TLRPC$TL_emojiStatusUntil) tLRPC$EmojiStatus;
                    if (tLRPC$TL_emojiStatusUntil.until > ((int) (System.currentTimeMillis() / 1000))) {
                        this.emojiStatusDrawable.set(tLRPC$TL_emojiStatusUntil.document_id, z5);
                    }
                }
                Drawable mutate3 = ContextCompat.getDrawable(ApplicationLoader.applicationContext, R.drawable.msg_premium_liststar).mutate();
                mutate3.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
                this.emojiStatusDrawable.set(mutate3, z5);
            }
            this.emojiStatusDrawable.setColor(Integer.valueOf(getThemedColor(Theme.key_profile_verifiedBackground)));
            this.titleTextView.setRightDrawable(this.emojiStatusDrawable);
            this.rightDrawableIsScamOrVerified = true;
            this.rightDrawableContentDescription = LocaleController.getString("AccDescrPremium", R.string.AccDescrPremium);
            return;
        }
        if (this.titleTextView.getRightDrawable() instanceof ScamDrawable) {
            this.titleTextView.setRightDrawable((Drawable) null);
            this.rightDrawableIsScamOrVerified = false;
            this.rightDrawableContentDescription = null;
        }
    }

    public void setSubtitle(CharSequence charSequence) {
        if (this.lastSubtitle == null) {
            this.subtitleTextView.setText(charSequence);
        } else {
            this.lastSubtitle = charSequence;
        }
    }

    public ImageView getTimeItem() {
        return this.timeItem;
    }

    public SimpleTextView getTitleTextView() {
        return this.titleTextView;
    }

    public SimpleTextView getSubtitleTextView() {
        return this.subtitleTextView;
    }

    public void onDestroy() {
        SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader = this.sharedMediaPreloader;
        if (sharedMediaPreloader != null) {
            sharedMediaPreloader.onDestroy(this.parentFragment);
        }
    }

    private void setTypingAnimation(boolean z) {
        int i = 0;
        if (z) {
            try {
                int intValue = MessagesController.getInstance(this.currentAccount).getPrintingStringType(this.parentFragment.getDialogId(), this.parentFragment.getThreadId()).intValue();
                if (intValue == 5) {
                    this.subtitleTextView.replaceTextWithDrawable(this.statusDrawables[intValue], "**oo**");
                    this.statusDrawables[intValue].setColor(getThemedColor(Theme.key_chat_status));
                    this.subtitleTextView.setLeftDrawable((Drawable) null);
                } else {
                    this.subtitleTextView.replaceTextWithDrawable(null, null);
                    this.statusDrawables[intValue].setColor(getThemedColor(Theme.key_chat_status));
                    this.subtitleTextView.setLeftDrawable(this.statusDrawables[intValue]);
                }
                this.currentTypingDrawable = this.statusDrawables[intValue];
                while (true) {
                    StatusDrawable[] statusDrawableArr = this.statusDrawables;
                    if (i >= statusDrawableArr.length) {
                        return;
                    }
                    if (i == intValue) {
                        statusDrawableArr[i].start();
                    } else {
                        statusDrawableArr[i].stop();
                    }
                    i++;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        } else {
            this.currentTypingDrawable = null;
            this.subtitleTextView.setLeftDrawable((Drawable) null);
            this.subtitleTextView.replaceTextWithDrawable(null, null);
            while (true) {
                StatusDrawable[] statusDrawableArr2 = this.statusDrawables;
                if (i >= statusDrawableArr2.length) {
                    return;
                }
                statusDrawableArr2[i].stop();
                i++;
            }
        }
    }

    public void updateSubtitle() {
        updateSubtitle(false);
    }

    public void updateSubtitle(boolean z) {
        String string;
        TLRPC$ChatParticipants tLRPC$ChatParticipants;
        int i;
        String formatShortNumber;
        ChatActivity chatActivity = this.parentFragment;
        if (chatActivity == null) {
            return;
        }
        TLRPC$User currentUser = chatActivity.getCurrentUser();
        if (UserObject.isUserSelf(currentUser) || UserObject.isReplyUser(currentUser) || this.parentFragment.getChatMode() != 0) {
            if (this.subtitleTextView.getVisibility() != 8) {
                this.subtitleTextView.setVisibility(8);
                return;
            }
            return;
        }
        TLRPC$Chat currentChat = this.parentFragment.getCurrentChat();
        boolean z2 = false;
        CharSequence printingString = MessagesController.getInstance(this.currentAccount).getPrintingString(this.parentFragment.getDialogId(), this.parentFragment.getThreadId(), false);
        CharSequence charSequence = "";
        if (printingString != null) {
            printingString = TextUtils.replace(printingString, new String[]{"..."}, new String[]{""});
        }
        if (printingString == null || printingString.length() == 0 || (ChatObject.isChannel(currentChat) && !currentChat.megagroup)) {
            if (this.parentFragment.isThreadChat() && !this.parentFragment.isTopic) {
                if (this.titleTextView.getTag() != null) {
                    return;
                }
                this.titleTextView.setTag(1);
                AnimatorSet animatorSet = this.titleAnimation;
                if (animatorSet != null) {
                    animatorSet.cancel();
                    this.titleAnimation = null;
                }
                if (z) {
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.titleAnimation = animatorSet2;
                    animatorSet2.playTogether(ObjectAnimator.ofFloat(this.titleTextView, (Property<SimpleTextView, Float>) View.TRANSLATION_Y, AndroidUtilities.dp(9.7f)), ObjectAnimator.ofFloat(this.subtitleTextView, (Property<SimpleTextView, Float>) View.ALPHA, 0.0f));
                    this.titleAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAvatarContainer.5
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                            ChatAvatarContainer.this.titleAnimation = null;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (ChatAvatarContainer.this.titleAnimation == animator) {
                                ChatAvatarContainer.this.subtitleTextView.setVisibility(4);
                                ChatAvatarContainer.this.titleAnimation = null;
                            }
                        }
                    });
                    this.titleAnimation.setDuration(180L);
                    this.titleAnimation.start();
                    return;
                }
                this.titleTextView.setTranslationY(AndroidUtilities.dp(9.7f));
                this.subtitleTextView.setAlpha(0.0f);
                this.subtitleTextView.setVisibility(4);
                return;
            }
            setTypingAnimation(false);
            ChatActivity chatActivity2 = this.parentFragment;
            if (chatActivity2.isTopic && currentChat != null) {
                TLRPC$TL_forumTopic findTopic = MessagesController.getInstance(this.currentAccount).getTopicsController().findTopic(currentChat.id, this.parentFragment.getTopicId());
                int i2 = findTopic != null ? findTopic.totalMessagesCount - 1 : 0;
                string = i2 > 0 ? LocaleController.formatPluralString("messages", i2, Integer.valueOf(i2)) : LocaleController.formatString("TopicProfileStatus", R.string.TopicProfileStatus, currentChat.title);
            } else if (currentChat != null) {
                TLRPC$ChatFull currentChatInfo = chatActivity2.getCurrentChatInfo();
                if (ChatObject.isChannel(currentChat)) {
                    if (currentChatInfo != null && (i = currentChatInfo.participants_count) != 0) {
                        if (currentChat.megagroup) {
                            string = this.onlineCount > 1 ? String.format("%s, %s", LocaleController.formatPluralString("Members", i, new Object[0]), LocaleController.formatPluralString("OnlineCount", Math.min(this.onlineCount, currentChatInfo.participants_count), new Object[0])) : LocaleController.formatPluralString("Members", i, new Object[0]);
                        } else {
                            int[] iArr = new int[1];
                            boolean isAccessibilityScreenReaderEnabled = AndroidUtilities.isAccessibilityScreenReaderEnabled();
                            int i3 = currentChatInfo.participants_count;
                            if (isAccessibilityScreenReaderEnabled) {
                                iArr[0] = i3;
                                formatShortNumber = String.valueOf(i3);
                            } else {
                                formatShortNumber = LocaleController.formatShortNumber(i3, iArr);
                            }
                            if (currentChat.megagroup) {
                                string = LocaleController.formatPluralString("Members", iArr[0], new Object[0]).replace(String.format("%d", Integer.valueOf(iArr[0])), formatShortNumber);
                            } else {
                                string = LocaleController.formatPluralString("Subscribers", iArr[0], new Object[0]).replace(String.format("%d", Integer.valueOf(iArr[0])), formatShortNumber);
                            }
                        }
                    } else if (currentChat.megagroup) {
                        if (currentChatInfo == null) {
                            string = LocaleController.getString("Loading", R.string.Loading).toLowerCase();
                        } else if (currentChat.has_geo) {
                            string = LocaleController.getString("MegaLocation", R.string.MegaLocation).toLowerCase();
                        } else if (ChatObject.isPublic(currentChat)) {
                            string = LocaleController.getString("MegaPublic", R.string.MegaPublic).toLowerCase();
                        } else {
                            string = LocaleController.getString("MegaPrivate", R.string.MegaPrivate).toLowerCase();
                        }
                    } else if (ChatObject.isPublic(currentChat)) {
                        string = LocaleController.getString("ChannelPublic", R.string.ChannelPublic).toLowerCase();
                    } else {
                        string = LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate).toLowerCase();
                    }
                } else if (ChatObject.isKickedFromChat(currentChat)) {
                    string = LocaleController.getString("YouWereKicked", R.string.YouWereKicked);
                } else if (ChatObject.isLeftFromChat(currentChat)) {
                    string = LocaleController.getString("YouLeft", R.string.YouLeft);
                } else {
                    int i4 = currentChat.participants_count;
                    if (currentChatInfo != null && (tLRPC$ChatParticipants = currentChatInfo.participants) != null) {
                        i4 = tLRPC$ChatParticipants.participants.size();
                    }
                    string = (this.onlineCount <= 1 || i4 == 0) ? LocaleController.formatPluralString("Members", i4, new Object[0]) : String.format("%s, %s", LocaleController.formatPluralString("Members", i4, new Object[0]), LocaleController.formatPluralString("OnlineCount", this.onlineCount, new Object[0]));
                }
            } else if (currentUser != null) {
                TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(currentUser.id));
                if (user != null) {
                    currentUser = user;
                }
                if (!UserObject.isReplyUser(currentUser)) {
                    if (currentUser.id == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
                        string = LocaleController.getString("ChatYourSelf", R.string.ChatYourSelf);
                    } else {
                        long j = currentUser.id;
                        if (j == 333000 || j == 777000 || j == 42777) {
                            string = LocaleController.getString("ServiceNotifications", R.string.ServiceNotifications);
                        } else if (MessagesController.isSupportUser(currentUser)) {
                            string = LocaleController.getString("SupportStatus", R.string.SupportStatus);
                        } else if (currentUser.bot) {
                            string = LocaleController.getString("Bot", R.string.Bot);
                        } else {
                            boolean[] zArr = this.isOnline;
                            zArr[0] = false;
                            charSequence = LocaleController.formatUserStatus(this.currentAccount, currentUser, zArr, this.allowShorterStatus ? this.statusMadeShorter : null);
                            z2 = this.isOnline[0];
                        }
                    }
                }
            }
            charSequence = string;
        } else {
            if (this.parentFragment.isThreadChat() && this.titleTextView.getTag() != null) {
                this.titleTextView.setTag(null);
                this.subtitleTextView.setVisibility(0);
                AnimatorSet animatorSet3 = this.titleAnimation;
                if (animatorSet3 != null) {
                    animatorSet3.cancel();
                    this.titleAnimation = null;
                }
                if (z) {
                    AnimatorSet animatorSet4 = new AnimatorSet();
                    this.titleAnimation = animatorSet4;
                    animatorSet4.playTogether(ObjectAnimator.ofFloat(this.titleTextView, (Property<SimpleTextView, Float>) View.TRANSLATION_Y, 0.0f), ObjectAnimator.ofFloat(this.subtitleTextView, (Property<SimpleTextView, Float>) View.ALPHA, 1.0f));
                    this.titleAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAvatarContainer.6
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            ChatAvatarContainer.this.titleAnimation = null;
                        }
                    });
                    this.titleAnimation.setDuration(180L);
                    this.titleAnimation.start();
                } else {
                    this.titleTextView.setTranslationY(0.0f);
                    this.subtitleTextView.setAlpha(1.0f);
                }
            }
            charSequence = MessagesController.getInstance(this.currentAccount).getPrintingStringType(this.parentFragment.getDialogId(), this.parentFragment.getThreadId()).intValue() == 5 ? Emoji.replaceEmoji(printingString, this.subtitleTextView.getTextPaint().getFontMetricsInt(), AndroidUtilities.dp(15.0f), false) : printingString;
            setTypingAnimation(true);
            z2 = true;
        }
        this.lastSubtitleColorKey = z2 ? Theme.key_chat_status : Theme.key_actionBarDefaultSubtitle;
        if (this.lastSubtitle == null) {
            this.subtitleTextView.setText(charSequence);
            Integer num = this.overrideSubtitleColor;
            if (num == null) {
                this.subtitleTextView.setTextColor(getThemedColor(this.lastSubtitleColorKey));
                this.subtitleTextView.setTag(Integer.valueOf(this.lastSubtitleColorKey));
                return;
            } else {
                this.subtitleTextView.setTextColor(num.intValue());
                return;
            }
        }
        this.lastSubtitle = charSequence;
    }

    public int getLastSubtitleColorKey() {
        return this.lastSubtitleColorKey;
    }

    public void setChatAvatar(TLRPC$Chat tLRPC$Chat) {
        this.avatarDrawable.setInfo(tLRPC$Chat);
        BackupImageView backupImageView = this.avatarImageView;
        if (backupImageView != null) {
            backupImageView.setForUserOrChat(tLRPC$Chat, this.avatarDrawable);
            this.avatarImageView.setRoundRadius(AndroidUtilities.dp((tLRPC$Chat == null || !tLRPC$Chat.forum) ? 21.0f : 16.0f));
        }
    }

    public void setUserAvatar(TLRPC$User tLRPC$User) {
        setUserAvatar(tLRPC$User, false);
    }

    public void setUserAvatar(TLRPC$User tLRPC$User, boolean z) {
        this.avatarDrawable.setInfo(tLRPC$User);
        if (UserObject.isReplyUser(tLRPC$User)) {
            this.avatarDrawable.setAvatarType(12);
            this.avatarDrawable.setScaleSize(0.8f);
            BackupImageView backupImageView = this.avatarImageView;
            if (backupImageView != null) {
                backupImageView.setImage((ImageLocation) null, (String) null, this.avatarDrawable, tLRPC$User);
                return;
            }
            return;
        }
        if (UserObject.isUserSelf(tLRPC$User) && !z) {
            this.avatarDrawable.setAvatarType(1);
            this.avatarDrawable.setScaleSize(0.8f);
            BackupImageView backupImageView2 = this.avatarImageView;
            if (backupImageView2 != null) {
                backupImageView2.setImage((ImageLocation) null, (String) null, this.avatarDrawable, tLRPC$User);
                return;
            }
            return;
        }
        this.avatarDrawable.setScaleSize(1.0f);
        BackupImageView backupImageView3 = this.avatarImageView;
        if (backupImageView3 != null) {
            backupImageView3.setForUserOrChat(tLRPC$User, this.avatarDrawable);
        }
    }

    public void checkAndUpdateAvatar() {
        ChatActivity chatActivity = this.parentFragment;
        if (chatActivity == null) {
            return;
        }
        TLRPC$User currentUser = chatActivity.getCurrentUser();
        TLRPC$Chat currentChat = this.parentFragment.getCurrentChat();
        if (currentUser == null) {
            if (currentChat != null) {
                this.avatarDrawable.setInfo(currentChat);
                BackupImageView backupImageView = this.avatarImageView;
                if (backupImageView != null) {
                    backupImageView.setForUserOrChat(currentChat, this.avatarDrawable);
                }
                this.avatarImageView.setRoundRadius(AndroidUtilities.dp(currentChat.forum ? 16.0f : 21.0f));
                return;
            }
            return;
        }
        this.avatarDrawable.setInfo(currentUser);
        if (UserObject.isReplyUser(currentUser)) {
            this.avatarDrawable.setScaleSize(0.8f);
            this.avatarDrawable.setAvatarType(12);
            BackupImageView backupImageView2 = this.avatarImageView;
            if (backupImageView2 != null) {
                backupImageView2.setImage((ImageLocation) null, (String) null, this.avatarDrawable, currentUser);
                return;
            }
            return;
        }
        if (UserObject.isUserSelf(currentUser)) {
            this.avatarDrawable.setScaleSize(0.8f);
            this.avatarDrawable.setAvatarType(1);
            BackupImageView backupImageView3 = this.avatarImageView;
            if (backupImageView3 != null) {
                backupImageView3.setImage((ImageLocation) null, (String) null, this.avatarDrawable, currentUser);
                return;
            }
            return;
        }
        this.avatarDrawable.setScaleSize(1.0f);
        BackupImageView backupImageView4 = this.avatarImageView;
        if (backupImageView4 != null) {
            backupImageView4.imageReceiver.setForUserOrChat(currentUser, this.avatarDrawable, null, true, 3);
        }
    }

    public void updateOnlineCount() {
        TLRPC$UserStatus tLRPC$UserStatus;
        boolean z;
        ChatActivity chatActivity = this.parentFragment;
        if (chatActivity == null) {
            return;
        }
        this.onlineCount = 0;
        TLRPC$ChatFull currentChatInfo = chatActivity.getCurrentChatInfo();
        if (currentChatInfo == null) {
            return;
        }
        int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
        if ((currentChatInfo instanceof TLRPC$TL_chatFull) || (((z = currentChatInfo instanceof TLRPC$TL_channelFull)) && currentChatInfo.participants_count <= 200 && currentChatInfo.participants != null)) {
            for (int i = 0; i < currentChatInfo.participants.participants.size(); i++) {
                TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(currentChatInfo.participants.participants.get(i).user_id));
                if (user != null && (tLRPC$UserStatus = user.status) != null && ((tLRPC$UserStatus.expires > currentTime || user.id == UserConfig.getInstance(this.currentAccount).getClientUserId()) && user.status.expires > 10000)) {
                    this.onlineCount++;
                }
            }
            return;
        }
        if (!z || currentChatInfo.participants_count <= 200) {
            return;
        }
        this.onlineCount = currentChatInfo.online_count;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
            this.currentConnectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
            updateCurrentConnectionState();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.parentFragment != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.didUpdateConnectionState) {
            int connectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
            if (this.currentConnectionState != connectionState) {
                this.currentConnectionState = connectionState;
                updateCurrentConnectionState();
                return;
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            SimpleTextView simpleTextView = this.titleTextView;
            if (simpleTextView != null) {
                simpleTextView.invalidate();
            }
            SimpleTextView simpleTextView2 = this.subtitleTextView;
            if (simpleTextView2 != null) {
                simpleTextView2.invalidate();
            }
            invalidate();
        }
    }

    private void updateCurrentConnectionState() {
        String string;
        int i = this.currentConnectionState;
        if (i == 2) {
            string = LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork);
        } else if (i == 1) {
            string = LocaleController.getString("Connecting", R.string.Connecting);
        } else if (i == 5) {
            string = LocaleController.getString("Updating", R.string.Updating);
        } else {
            string = i == 4 ? LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy) : null;
        }
        if (string == null) {
            CharSequence charSequence = this.lastSubtitle;
            if (charSequence != null) {
                this.subtitleTextView.setText(charSequence);
                this.lastSubtitle = null;
                Integer num = this.overrideSubtitleColor;
                if (num != null) {
                    this.subtitleTextView.setTextColor(num.intValue());
                    return;
                }
                int i2 = this.lastSubtitleColorKey;
                if (i2 >= 0) {
                    this.subtitleTextView.setTextColor(getThemedColor(i2));
                    this.subtitleTextView.setTag(Integer.valueOf(this.lastSubtitleColorKey));
                    return;
                }
                return;
            }
            return;
        }
        if (this.lastSubtitle == null) {
            this.lastSubtitle = this.subtitleTextView.getText();
        }
        this.subtitleTextView.setText(string);
        Integer num2 = this.overrideSubtitleColor;
        if (num2 != null) {
            this.subtitleTextView.setTextColor(num2.intValue());
            return;
        }
        SimpleTextView simpleTextView = this.subtitleTextView;
        int i3 = Theme.key_actionBarDefaultSubtitle;
        simpleTextView.setTextColor(getThemedColor(i3));
        this.subtitleTextView.setTag(Integer.valueOf(i3));
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(this.titleTextView.getText());
        if (this.rightDrawableContentDescription != null) {
            sb.append(", ");
            sb.append(this.rightDrawableContentDescription);
        }
        sb.append("\n");
        sb.append(this.subtitleTextView.getText());
        accessibilityNodeInfo.setContentDescription(sb);
        if (!accessibilityNodeInfo.isClickable() || Build.VERSION.SDK_INT < 21) {
            return;
        }
        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, LocaleController.getString("OpenProfile", R.string.OpenProfile)));
    }

    public SharedMediaLayout.SharedMediaPreloader getSharedMediaPreloader() {
        return this.sharedMediaPreloader;
    }

    public BackupImageView getAvatarImageView() {
        return this.avatarImageView;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void updateColors() {
        StatusDrawable statusDrawable = this.currentTypingDrawable;
        if (statusDrawable != null) {
            statusDrawable.setColor(getThemedColor(Theme.key_chat_status));
        }
    }
}
