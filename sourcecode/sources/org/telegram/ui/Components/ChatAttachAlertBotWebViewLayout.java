package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.math.MathUtils;
import androidx.core.util.Consumer;
import androidx.core.view.GestureDetectorCompat;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.ChatListItemAnimator;
import java.util.Iterator;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserObject;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$TL_attachMenuBot;
import org.telegram.tgnet.TLRPC$TL_dataJSON;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_prolongWebView;
import org.telegram.tgnet.TLRPC$TL_messages_requestWebView;
import org.telegram.tgnet.TLRPC$TL_webViewResultUrl;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.BotWebViewContainer;
import org.telegram.ui.Components.ChatAttachAlert;
import org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout;
import org.telegram.ui.Components.SimpleFloatPropertyCompat;

/* loaded from: classes4.dex */
public class ChatAttachAlertBotWebViewLayout extends ChatAttachAlert.AttachAlertLayout implements NotificationCenter.NotificationCenterDelegate {
    private long botId;
    private int currentAccount;
    private int customBackground;
    private boolean destroyed;
    private boolean hasCustomBackground;
    private boolean ignoreLayout;
    private boolean ignoreMeasure;
    private boolean isBotButtonAvailable;
    private int measureOffsetY;
    private boolean needCloseConfirmation;
    private boolean needReload;
    private ActionBarMenuItem otherItem;
    private long peerId;
    private Runnable pollRunnable;
    private WebProgressView progressView;
    private long queryId;
    private int replyToMsgId;
    private ActionBarMenuSubItem settingsItem;
    private boolean silent;
    private String startCommand;
    private WebViewSwipeContainer swipeContainer;
    private BotWebViewContainer webViewContainer;
    private ValueAnimator webViewScrollAnimator;

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int needsActionBar() {
        return 1;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    boolean shouldHideBottomButtons() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        TLRPC$ChatFull chatFull;
        TLRPC$Peer tLRPC$Peer;
        if (this.destroyed) {
            return;
        }
        TLRPC$TL_messages_prolongWebView tLRPC$TL_messages_prolongWebView = new TLRPC$TL_messages_prolongWebView();
        tLRPC$TL_messages_prolongWebView.bot = MessagesController.getInstance(this.currentAccount).getInputUser(this.botId);
        tLRPC$TL_messages_prolongWebView.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(this.peerId);
        tLRPC$TL_messages_prolongWebView.query_id = this.queryId;
        tLRPC$TL_messages_prolongWebView.silent = this.silent;
        int i = this.replyToMsgId;
        if (i != 0) {
            tLRPC$TL_messages_prolongWebView.reply_to_msg_id = i;
            tLRPC$TL_messages_prolongWebView.flags |= 1;
        }
        if (this.peerId < 0 && (chatFull = MessagesController.getInstance(this.currentAccount).getChatFull(-this.peerId)) != null && (tLRPC$Peer = chatFull.default_send_as) != null) {
            tLRPC$TL_messages_prolongWebView.send_as = MessagesController.getInstance(this.currentAccount).getInputPeer(tLRPC$Peer);
            tLRPC$TL_messages_prolongWebView.flags |= LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM;
        }
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_messages_prolongWebView, new RequestDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda12
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$1(tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$0(tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(TLRPC$TL_error tLRPC$TL_error) {
        if (this.destroyed) {
            return;
        }
        if (tLRPC$TL_error != null) {
            this.parentAlert.dismiss();
        } else {
            AndroidUtilities.runOnUIThread(this.pollRunnable, 60000L);
        }
    }

    public ChatAttachAlertBotWebViewLayout(ChatAttachAlert chatAttachAlert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(chatAttachAlert, context, resourcesProvider);
        this.pollRunnable = new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$2();
            }
        };
        ActionBarMenuItem addItem = this.parentAlert.actionBar.createMenu().addItem(0, R.drawable.ic_ab_other);
        this.otherItem = addItem;
        addItem.addSubItem(R.id.menu_open_bot, R.drawable.msg_bot, LocaleController.getString(R.string.BotWebViewOpenBot));
        this.settingsItem = this.otherItem.addSubItem(R.id.menu_settings, R.drawable.msg_settings, LocaleController.getString(R.string.BotWebViewSettings));
        this.otherItem.addSubItem(R.id.menu_reload_page, R.drawable.msg_retry, LocaleController.getString(R.string.BotWebViewReloadPage));
        this.otherItem.addSubItem(R.id.menu_delete_bot, R.drawable.msg_delete, LocaleController.getString(R.string.BotWebViewDeleteBot));
        this.parentAlert.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    if (ChatAttachAlertBotWebViewLayout.this.webViewContainer.onBackPressed()) {
                        return;
                    }
                    ChatAttachAlertBotWebViewLayout.this.onCheckDismissByUser();
                    return;
                }
                if (i == R.id.menu_open_bot) {
                    Bundle bundle = new Bundle();
                    bundle.putLong("user_id", ChatAttachAlertBotWebViewLayout.this.botId);
                    ChatAttachAlertBotWebViewLayout.this.parentAlert.baseFragment.presentFragment(new ChatActivity(bundle));
                    ChatAttachAlertBotWebViewLayout.this.parentAlert.dismiss();
                    return;
                }
                if (i == R.id.menu_reload_page) {
                    if (ChatAttachAlertBotWebViewLayout.this.webViewContainer.getWebView() != null) {
                        ChatAttachAlertBotWebViewLayout.this.webViewContainer.getWebView().animate().cancel();
                        ChatAttachAlertBotWebViewLayout.this.webViewContainer.getWebView().animate().alpha(0.0f).start();
                    }
                    ChatAttachAlertBotWebViewLayout.this.progressView.setLoadProgress(0.0f);
                    ChatAttachAlertBotWebViewLayout.this.progressView.setAlpha(1.0f);
                    ChatAttachAlertBotWebViewLayout.this.progressView.setVisibility(0);
                    ChatAttachAlertBotWebViewLayout.this.webViewContainer.setBotUser(MessagesController.getInstance(ChatAttachAlertBotWebViewLayout.this.currentAccount).getUser(Long.valueOf(ChatAttachAlertBotWebViewLayout.this.botId)));
                    ChatAttachAlertBotWebViewLayout.this.webViewContainer.loadFlickerAndSettingsItem(ChatAttachAlertBotWebViewLayout.this.currentAccount, ChatAttachAlertBotWebViewLayout.this.botId, ChatAttachAlertBotWebViewLayout.this.settingsItem);
                    ChatAttachAlertBotWebViewLayout.this.webViewContainer.reload();
                    return;
                }
                if (i == R.id.menu_delete_bot) {
                    Iterator<TLRPC$TL_attachMenuBot> it = MediaDataController.getInstance(ChatAttachAlertBotWebViewLayout.this.currentAccount).getAttachMenuBots().bots.iterator();
                    while (it.hasNext()) {
                        TLRPC$TL_attachMenuBot next = it.next();
                        if (next.bot_id == ChatAttachAlertBotWebViewLayout.this.botId) {
                            ChatAttachAlertBotWebViewLayout chatAttachAlertBotWebViewLayout = ChatAttachAlertBotWebViewLayout.this;
                            chatAttachAlertBotWebViewLayout.parentAlert.onLongClickBotButton(next, MessagesController.getInstance(chatAttachAlertBotWebViewLayout.currentAccount).getUser(Long.valueOf(ChatAttachAlertBotWebViewLayout.this.botId)));
                            return;
                        }
                    }
                    return;
                }
                if (i == R.id.menu_settings) {
                    ChatAttachAlertBotWebViewLayout.this.webViewContainer.onSettingsButtonPressed();
                }
            }
        });
        this.webViewContainer = new BotWebViewContainer(context, resourcesProvider, getThemedColor(Theme.key_dialogBackground)) { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.2
            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0 && !ChatAttachAlertBotWebViewLayout.this.isBotButtonAvailable) {
                    ChatAttachAlertBotWebViewLayout.this.isBotButtonAvailable = true;
                    ChatAttachAlertBotWebViewLayout.this.webViewContainer.restoreButtonData();
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        };
        WebViewSwipeContainer webViewSwipeContainer = new WebViewSwipeContainer(context) { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.3
            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(((View.MeasureSpec.getSize(i2) - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(84.0f)) + ChatAttachAlertBotWebViewLayout.this.measureOffsetY, 1073741824));
            }
        };
        this.swipeContainer = webViewSwipeContainer;
        webViewSwipeContainer.addView(this.webViewContainer, LayoutHelper.createFrame(-1, -1.0f));
        this.swipeContainer.setScrollListener(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$3();
            }
        });
        this.swipeContainer.setScrollEndListener(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$4();
            }
        });
        this.swipeContainer.setDelegate(new WebViewSwipeContainer.Delegate() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda14
            @Override // org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.Delegate
            public final void onDismiss() {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$5();
            }
        });
        this.swipeContainer.setIsKeyboardVisible(new GenericProvider() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda11
            @Override // org.telegram.messenger.GenericProvider
            public final Object provide(Object obj) {
                Boolean lambda$new$6;
                lambda$new$6 = ChatAttachAlertBotWebViewLayout.this.lambda$new$6((Void) obj);
                return lambda$new$6;
            }
        });
        addView(this.swipeContainer, LayoutHelper.createFrame(-1, -1.0f));
        WebProgressView webProgressView = new WebProgressView(context, resourcesProvider);
        this.progressView = webProgressView;
        addView(webProgressView, LayoutHelper.createFrame(-1, -2.0f, 80, 0.0f, 0.0f, 0.0f, 84.0f));
        this.webViewContainer.setWebViewProgressListener(new Consumer() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ChatAttachAlertBotWebViewLayout.this.lambda$new$8((Float) obj);
            }
        });
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetNewTheme);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        this.parentAlert.updateLayout(this, true, 0);
        this.webViewContainer.invalidateViewPortHeight();
        System.currentTimeMillis();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        this.webViewContainer.invalidateViewPortHeight(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5() {
        if (onCheckDismissByUser()) {
            return;
        }
        this.swipeContainer.stickTo(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$new$6(Void r2) {
        return Boolean.valueOf(this.parentAlert.sizeNotifierFrameLayout.getKeyboardHeight() >= AndroidUtilities.dp(20.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(Float f) {
        this.progressView.setLoadProgressAnimated(f.floatValue());
        if (f.floatValue() == 1.0f) {
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(200L);
            duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ChatAttachAlertBotWebViewLayout.this.lambda$new$7(valueAnimator);
                }
            });
            duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ChatAttachAlertBotWebViewLayout.this.progressView.setVisibility(8);
                }
            });
            duration.start();
            requestEnableKeyboard();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(ValueAnimator valueAnimator) {
        this.progressView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public void setNeedCloseConfirmation(boolean z) {
        this.needCloseConfirmation = z;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    boolean onDismissWithTouchOutside() {
        onCheckDismissByUser();
        return false;
    }

    public boolean onCheckDismissByUser() {
        if (this.needCloseConfirmation) {
            TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.botId));
            AlertDialog create = new AlertDialog.Builder(getContext()).setTitle(user != null ? ContactsController.formatName(user.first_name, user.last_name) : null).setMessage(LocaleController.getString(R.string.BotWebViewChangesMayNotBeSaved)).setPositiveButton(LocaleController.getString(R.string.BotWebViewCloseAnyway), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    ChatAttachAlertBotWebViewLayout.this.lambda$onCheckDismissByUser$9(dialogInterface, i);
                }
            }).setNegativeButton(LocaleController.getString(R.string.Cancel), null).create();
            create.show();
            ((TextView) create.getButton(-1)).setTextColor(getThemedColor(Theme.key_text_RedBold));
            return false;
        }
        this.parentAlert.dismiss();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCheckDismissByUser$9(DialogInterface dialogInterface, int i) {
        this.parentAlert.dismiss();
    }

    public void setCustomBackground(int i) {
        this.customBackground = i;
        this.hasCustomBackground = true;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    boolean hasCustomBackground() {
        return this.hasCustomBackground;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public int getCustomBackground() {
        return this.customBackground;
    }

    public boolean canExpandByRequest() {
        return !this.swipeContainer.isSwipeInProgress();
    }

    public void setMeasureOffsetY(int i) {
        this.measureOffsetY = i;
        this.swipeContainer.requestLayout();
    }

    public void disallowSwipeOffsetAnimation() {
        this.swipeContainer.setSwipeOffsetAnimationDisallowed(true);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.ignoreMeasure) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        } else {
            super.onMeasure(i, i2);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onPanTransitionStart(boolean z, int i) {
        boolean z2;
        if (z) {
            this.webViewContainer.setViewPortByMeasureSuppressed(true);
            float topActionBarOffsetY = (-this.swipeContainer.getOffsetY()) + this.swipeContainer.getTopActionBarOffsetY();
            if (this.swipeContainer.getSwipeOffsetY() != topActionBarOffsetY) {
                this.swipeContainer.stickTo(topActionBarOffsetY);
                z2 = true;
            } else {
                z2 = false;
            }
            int measureKeyboardHeight = this.parentAlert.sizeNotifierFrameLayout.measureKeyboardHeight() + i;
            setMeasuredDimension(getMeasuredWidth(), i);
            this.ignoreMeasure = true;
            this.swipeContainer.setSwipeOffsetAnimationDisallowed(true);
            if (z2) {
                return;
            }
            ValueAnimator valueAnimator = this.webViewScrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.webViewScrollAnimator = null;
            }
            if (this.webViewContainer.getWebView() != null) {
                int scrollY = this.webViewContainer.getWebView().getScrollY();
                final int i2 = (measureKeyboardHeight - i) + scrollY;
                ValueAnimator duration = ValueAnimator.ofInt(scrollY, i2).setDuration(250L);
                this.webViewScrollAnimator = duration;
                duration.setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR);
                this.webViewScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        ChatAttachAlertBotWebViewLayout.this.lambda$onPanTransitionStart$10(valueAnimator2);
                    }
                });
                this.webViewScrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (ChatAttachAlertBotWebViewLayout.this.webViewContainer.getWebView() != null) {
                            ChatAttachAlertBotWebViewLayout.this.webViewContainer.getWebView().setScrollY(i2);
                        }
                        if (animator == ChatAttachAlertBotWebViewLayout.this.webViewScrollAnimator) {
                            ChatAttachAlertBotWebViewLayout.this.webViewScrollAnimator = null;
                        }
                    }
                });
                this.webViewScrollAnimator.start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPanTransitionStart$10(ValueAnimator valueAnimator) {
        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        if (this.webViewContainer.getWebView() != null) {
            this.webViewContainer.getWebView().setScrollY(intValue);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    public void onPanTransitionEnd() {
        this.ignoreMeasure = false;
        this.swipeContainer.setSwipeOffsetAnimationDisallowed(false);
        this.webViewContainer.setViewPortByMeasureSuppressed(false);
        requestLayout();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onShow(ChatAttachAlert.AttachAlertLayout attachAlertLayout) {
        this.parentAlert.actionBar.setTitle(UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.botId))));
        this.swipeContainer.setSwipeOffsetY(0.0f);
        if (this.webViewContainer.getWebView() != null) {
            this.webViewContainer.getWebView().scrollTo(0, 0);
        }
        if (this.parentAlert.getBaseFragment() != null) {
            this.webViewContainer.setParentActivity(this.parentAlert.getBaseFragment().getParentActivity());
        }
        this.otherItem.setVisibility(0);
        if (this.webViewContainer.isBackButtonVisible()) {
            return;
        }
        AndroidUtilities.updateImageViewImageAnimated(this.parentAlert.actionBar.getBackButton(), R.drawable.ic_close_white);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onShown() {
        if (this.webViewContainer.isPageLoaded()) {
            requestEnableKeyboard();
        }
        this.swipeContainer.setSwipeOffsetAnimationDisallowed(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$onShown$11();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onShown$11() {
        this.webViewContainer.restoreButtonData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestEnableKeyboard() {
        BaseFragment baseFragment = this.parentAlert.getBaseFragment();
        if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).contentView.measureKeyboardHeight() > AndroidUtilities.dp(20.0f)) {
            AndroidUtilities.hideKeyboard(this.parentAlert.baseFragment.getFragmentView());
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ChatAttachAlertBotWebViewLayout.this.requestEnableKeyboard();
                }
            }, 250L);
        } else {
            this.parentAlert.getWindow().setSoftInputMode(20);
            setFocusable(true);
            this.parentAlert.setFocusable(true);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onHidden() {
        super.onHidden();
        this.parentAlert.setFocusable(false);
        this.parentAlert.getWindow().setSoftInputMode(48);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getCurrentItemTop() {
        return (int) (this.swipeContainer.getSwipeOffsetY() + this.swipeContainer.getOffsetY());
    }

    @Override // android.view.View
    public void setTranslationY(float f) {
        super.setTranslationY(f);
        this.parentAlert.getSheetContainer().invalidate();
    }

    public String getStartCommand() {
        return this.startCommand;
    }

    public void requestWebView(final int i, long j, long j2, boolean z, int i2, String str) {
        TLRPC$ChatFull chatFull;
        TLRPC$Peer tLRPC$Peer;
        this.currentAccount = i;
        this.peerId = j;
        this.botId = j2;
        this.silent = z;
        this.replyToMsgId = i2;
        this.startCommand = str;
        this.webViewContainer.setBotUser(MessagesController.getInstance(i).getUser(Long.valueOf(j2)));
        this.webViewContainer.loadFlickerAndSettingsItem(i, j2, this.settingsItem);
        TLRPC$TL_messages_requestWebView tLRPC$TL_messages_requestWebView = new TLRPC$TL_messages_requestWebView();
        tLRPC$TL_messages_requestWebView.peer = MessagesController.getInstance(i).getInputPeer(j);
        tLRPC$TL_messages_requestWebView.bot = MessagesController.getInstance(i).getInputUser(j2);
        tLRPC$TL_messages_requestWebView.silent = z;
        tLRPC$TL_messages_requestWebView.platform = "android";
        if (j < 0 && (chatFull = MessagesController.getInstance(i).getChatFull(-j)) != null && (tLRPC$Peer = chatFull.default_send_as) != null) {
            tLRPC$TL_messages_requestWebView.send_as = MessagesController.getInstance(i).getInputPeer(tLRPC$Peer);
            tLRPC$TL_messages_requestWebView.flags |= LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM;
        }
        if (str != null) {
            tLRPC$TL_messages_requestWebView.start_param = str;
            tLRPC$TL_messages_requestWebView.flags |= 8;
        }
        if (i2 != 0) {
            tLRPC$TL_messages_requestWebView.reply_to_msg_id = i2;
            tLRPC$TL_messages_requestWebView.flags |= 1;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("bg_color", getThemedColor(Theme.key_dialogBackground));
            jSONObject.put("secondary_bg_color", getThemedColor(Theme.key_windowBackgroundGray));
            jSONObject.put("text_color", getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            jSONObject.put("hint_color", getThemedColor(Theme.key_windowBackgroundWhiteHintText));
            jSONObject.put("link_color", getThemedColor(Theme.key_windowBackgroundWhiteLinkText));
            jSONObject.put("button_color", getThemedColor(Theme.key_featuredStickers_addButton));
            jSONObject.put("button_text_color", getThemedColor(Theme.key_featuredStickers_buttonText));
            TLRPC$TL_dataJSON tLRPC$TL_dataJSON = new TLRPC$TL_dataJSON();
            tLRPC$TL_messages_requestWebView.theme_params = tLRPC$TL_dataJSON;
            tLRPC$TL_dataJSON.data = jSONObject.toString();
            tLRPC$TL_messages_requestWebView.flags |= 4;
        } catch (Exception e) {
            FileLog.e(e);
        }
        ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_messages_requestWebView, new RequestDelegate() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda13
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ChatAttachAlertBotWebViewLayout.this.lambda$requestWebView$13(i, tLObject, tLRPC$TL_error);
            }
        });
        NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.webViewResultSent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestWebView$13(final int i, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                ChatAttachAlertBotWebViewLayout.this.lambda$requestWebView$12(tLObject, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestWebView$12(TLObject tLObject, int i) {
        if (tLObject instanceof TLRPC$TL_webViewResultUrl) {
            TLRPC$TL_webViewResultUrl tLRPC$TL_webViewResultUrl = (TLRPC$TL_webViewResultUrl) tLObject;
            this.queryId = tLRPC$TL_webViewResultUrl.query_id;
            this.webViewContainer.loadUrl(i, tLRPC$TL_webViewResultUrl.url);
            this.swipeContainer.setWebView(this.webViewContainer.getWebView());
            AndroidUtilities.runOnUIThread(this.pollRunnable);
        }
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onDestroy() {
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.webViewResultSent);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetNewTheme);
        ActionBarMenu createMenu = this.parentAlert.actionBar.createMenu();
        this.otherItem.removeAllSubItems();
        createMenu.removeView(this.otherItem);
        this.webViewContainer.destroyWebView();
        this.destroyed = true;
        AndroidUtilities.cancelRunOnUIThread(this.pollRunnable);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void onHide() {
        super.onHide();
        this.otherItem.setVisibility(8);
        this.isBotButtonAvailable = false;
        if (!this.webViewContainer.isBackButtonVisible()) {
            AndroidUtilities.updateImageViewImageAnimated(this.parentAlert.actionBar.getBackButton(), R.drawable.ic_ab_back);
        }
        this.parentAlert.actionBar.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
        if (this.webViewContainer.hasUserPermissions()) {
            this.webViewContainer.destroyWebView();
            this.needReload = true;
        }
    }

    public boolean needReload() {
        if (!this.needReload) {
            return false;
        }
        this.needReload = false;
        return true;
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getListTopPadding() {
        return (int) this.swipeContainer.getOffsetY();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getFirstOffset() {
        return getListTopPadding() + AndroidUtilities.dp(56.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void onPreMeasure(int r3, int r4) {
        /*
            r2 = this;
            boolean r3 = org.telegram.messenger.AndroidUtilities.isTablet()
            if (r3 != 0) goto L14
            android.graphics.Point r3 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r3.x
            int r3 = r3.y
            if (r0 <= r3) goto L14
            float r3 = (float) r4
            r4 = 1080033280(0x40600000, float:3.5)
            float r3 = r3 / r4
            int r3 = (int) r3
            goto L18
        L14:
            int r4 = r4 / 5
            int r3 = r4 * 2
        L18:
            org.telegram.ui.Components.ChatAttachAlert r4 = r2.parentAlert
            r0 = 1
            r4.setAllowNestedScroll(r0)
            r4 = 0
            if (r3 >= 0) goto L22
            r3 = 0
        L22:
            org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer r1 = r2.swipeContainer
            float r1 = r1.getOffsetY()
            float r3 = (float) r3
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L36
            r2.ignoreLayout = r0
            org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer r0 = r2.swipeContainer
            r0.setOffsetY(r3)
            r2.ignoreLayout = r4
        L36:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.onPreMeasure(int, int):void");
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    int getButtonsHideOffset() {
        return ((int) this.swipeContainer.getTopActionBarOffsetY()) + AndroidUtilities.dp(12.0f);
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    boolean onBackPressed() {
        if (this.webViewContainer.onBackPressed()) {
            return true;
        }
        onCheckDismissByUser();
        return true;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.ignoreLayout) {
            return;
        }
        super.requestLayout();
    }

    @Override // org.telegram.ui.Components.ChatAttachAlert.AttachAlertLayout
    void scrollToTop() {
        WebViewSwipeContainer webViewSwipeContainer = this.swipeContainer;
        webViewSwipeContainer.stickTo((-webViewSwipeContainer.getOffsetY()) + this.swipeContainer.getTopActionBarOffsetY());
    }

    public BotWebViewContainer getWebViewContainer() {
        return this.webViewContainer;
    }

    public void setDelegate(BotWebViewContainer.Delegate delegate) {
        this.webViewContainer.setDelegate(delegate);
    }

    public boolean isBotButtonAvailable() {
        return this.isBotButtonAvailable;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.webViewResultSent) {
            if (this.queryId == ((Long) objArr[0]).longValue()) {
                this.webViewContainer.destroyWebView();
                this.needReload = true;
                this.parentAlert.dismiss();
                return;
            }
            return;
        }
        if (i == NotificationCenter.didSetNewTheme) {
            this.webViewContainer.updateFlickerBackgroundColor(getThemedColor(Theme.key_dialogBackground));
        }
    }

    public static class WebViewSwipeContainer extends FrameLayout {
        public static final SimpleFloatPropertyCompat<WebViewSwipeContainer> SWIPE_OFFSET_Y = new SimpleFloatPropertyCompat<>("swipeOffsetY", new SimpleFloatPropertyCompat.Getter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.Components.SimpleFloatPropertyCompat.Getter
            public final float get(Object obj) {
                return ((ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer) obj).getSwipeOffsetY();
            }
        }, new SimpleFloatPropertyCompat.Setter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.SimpleFloatPropertyCompat.Setter
            public final void set(Object obj, float f) {
                ((ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer) obj).setSwipeOffsetY(f);
            }
        });
        private Delegate delegate;
        private boolean flingInProgress;
        private GestureDetectorCompat gestureDetector;
        private GenericProvider<Void, Boolean> isKeyboardVisible;
        private boolean isScrolling;
        private boolean isSwipeDisallowed;
        private boolean isSwipeOffsetAnimationDisallowed;
        private float offsetY;
        private SpringAnimation offsetYAnimator;
        private float pendingOffsetY;
        private float pendingSwipeOffsetY;
        private SpringAnimation scrollAnimator;
        private Runnable scrollEndListener;
        private Runnable scrollListener;
        private float swipeOffsetY;
        private int swipeStickyRange;
        private float topActionBarOffsetY;
        private WebView webView;

        public interface Delegate {
            void onDismiss();
        }

        static /* synthetic */ float access$1124(WebViewSwipeContainer webViewSwipeContainer, float f) {
            float f2 = webViewSwipeContainer.swipeOffsetY - f;
            webViewSwipeContainer.swipeOffsetY = f2;
            return f2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Boolean lambda$new$0(Void r0) {
            return Boolean.FALSE;
        }

        public WebViewSwipeContainer(Context context) {
            super(context);
            this.topActionBarOffsetY = ActionBar.getCurrentActionBarHeight();
            this.offsetY = 0.0f;
            this.pendingOffsetY = -1.0f;
            this.pendingSwipeOffsetY = -2.1474836E9f;
            this.isKeyboardVisible = new GenericProvider() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda3
                @Override // org.telegram.messenger.GenericProvider
                public final Object provide(Object obj) {
                    Boolean lambda$new$0;
                    lambda$new$0 = ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.lambda$new$0((Void) obj);
                    return lambda$new$0;
                }
            };
            final int scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
            this.gestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (WebViewSwipeContainer.this.isSwipeDisallowed) {
                        return false;
                    }
                    if (f2 < 700.0f || !(WebViewSwipeContainer.this.webView == null || WebViewSwipeContainer.this.webView.getScrollY() == 0)) {
                        if (f2 <= -700.0f && WebViewSwipeContainer.this.swipeOffsetY > (-WebViewSwipeContainer.this.offsetY) + WebViewSwipeContainer.this.topActionBarOffsetY) {
                            WebViewSwipeContainer.this.flingInProgress = true;
                            WebViewSwipeContainer webViewSwipeContainer = WebViewSwipeContainer.this;
                            webViewSwipeContainer.stickTo((-webViewSwipeContainer.offsetY) + WebViewSwipeContainer.this.topActionBarOffsetY);
                        }
                        return true;
                    }
                    WebViewSwipeContainer.this.flingInProgress = true;
                    if (WebViewSwipeContainer.this.swipeOffsetY >= WebViewSwipeContainer.this.swipeStickyRange) {
                        if (WebViewSwipeContainer.this.delegate != null) {
                            WebViewSwipeContainer.this.delegate.onDismiss();
                        }
                    } else {
                        WebViewSwipeContainer.this.stickTo(0.0f);
                    }
                    return true;
                }

                /* JADX WARN: Code restructure failed: missing block: B:36:0x00d2, code lost:
                
                    if (r9.this$0.webView.canScrollHorizontally(r12 >= 0.0f ? 1 : -1) == false) goto L37;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:37:0x00ed, code lost:
                
                    r9.this$0.isSwipeDisallowed = true;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:42:0x00eb, code lost:
                
                    if ((java.lang.Math.abs(r12) * 1.5f) >= java.lang.Math.abs(r13)) goto L41;
                 */
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public boolean onScroll(android.view.MotionEvent r10, android.view.MotionEvent r11, float r12, float r13) {
                    /*
                        Method dump skipped, instructions count: 537
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.AnonymousClass1.onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float):boolean");
                }
            });
            updateStickyRange();
        }

        public void setIsKeyboardVisible(GenericProvider<Void, Boolean> genericProvider) {
            this.isKeyboardVisible = genericProvider;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            updateStickyRange();
        }

        private void updateStickyRange() {
            android.graphics.Point point = AndroidUtilities.displaySize;
            this.swipeStickyRange = AndroidUtilities.dp(point.x > point.y ? 8.0f : 64.0f);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void requestDisallowInterceptTouchEvent(boolean z) {
            super.requestDisallowInterceptTouchEvent(z);
            if (z) {
                this.isSwipeDisallowed = true;
                this.isScrolling = false;
            }
        }

        public void setSwipeOffsetAnimationDisallowed(boolean z) {
            this.isSwipeOffsetAnimationDisallowed = z;
        }

        public void setScrollListener(Runnable runnable) {
            this.scrollListener = runnable;
        }

        public void setScrollEndListener(Runnable runnable) {
            this.scrollEndListener = runnable;
        }

        public void setWebView(WebView webView) {
            this.webView = webView;
        }

        public void setTopActionBarOffsetY(float f) {
            this.topActionBarOffsetY = f;
            invalidateTranslation();
        }

        public void setSwipeOffsetY(float f) {
            this.swipeOffsetY = f;
            invalidateTranslation();
        }

        public void setOffsetY(final float f) {
            if (this.pendingSwipeOffsetY != -2.1474836E9f) {
                this.pendingOffsetY = f;
                return;
            }
            SpringAnimation springAnimation = this.offsetYAnimator;
            if (springAnimation != null) {
                springAnimation.cancel();
            }
            final float f2 = this.offsetY;
            final float f3 = f - f2;
            final boolean z = Math.abs((this.swipeOffsetY + f2) - this.topActionBarOffsetY) <= ((float) AndroidUtilities.dp(1.0f));
            if (!this.isSwipeOffsetAnimationDisallowed) {
                SpringAnimation springAnimation2 = this.offsetYAnimator;
                if (springAnimation2 != null) {
                    springAnimation2.cancel();
                }
                SpringAnimation addEndListener = new SpringAnimation(new FloatValueHolder(f2)).setSpring(new SpringForce(f).setStiffness(1400.0f).setDampingRatio(1.0f)).addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda2
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f4, float f5) {
                        ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.this.lambda$setOffsetY$1(f2, f3, z, f, dynamicAnimation, f4, f5);
                    }
                }).addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda0
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f4, float f5) {
                        ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.this.lambda$setOffsetY$2(f, dynamicAnimation, z2, f4, f5);
                    }
                });
                this.offsetYAnimator = addEndListener;
                addEndListener.start();
                return;
            }
            this.offsetY = f;
            if (z) {
                this.swipeOffsetY = MathUtils.clamp(this.swipeOffsetY - Math.max(0.0f, f3), (-this.offsetY) + this.topActionBarOffsetY, (getHeight() - this.offsetY) + this.topActionBarOffsetY);
            }
            invalidateTranslation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setOffsetY$1(float f, float f2, boolean z, float f3, DynamicAnimation dynamicAnimation, float f4, float f5) {
            this.offsetY = f4;
            float f6 = (f4 - f) / f2;
            if (z) {
                this.swipeOffsetY = MathUtils.clamp(this.swipeOffsetY - (f6 * Math.max(0.0f, f2)), (-this.offsetY) + this.topActionBarOffsetY, (getHeight() - this.offsetY) + this.topActionBarOffsetY);
            }
            SpringAnimation springAnimation = this.scrollAnimator;
            if (springAnimation != null && springAnimation.getSpring().getFinalPosition() == (-f) + this.topActionBarOffsetY) {
                this.scrollAnimator.getSpring().setFinalPosition((-f3) + this.topActionBarOffsetY);
            }
            invalidateTranslation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setOffsetY$2(float f, DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
            this.offsetYAnimator = null;
            if (!z) {
                this.offsetY = f;
                invalidateTranslation();
            } else {
                this.pendingOffsetY = f;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidateTranslation() {
            setTranslationY(Math.max(this.topActionBarOffsetY, this.offsetY + this.swipeOffsetY));
            Runnable runnable = this.scrollListener;
            if (runnable != null) {
                runnable.run();
            }
        }

        public float getTopActionBarOffsetY() {
            return this.topActionBarOffsetY;
        }

        public float getOffsetY() {
            return this.offsetY;
        }

        public float getSwipeOffsetY() {
            return this.swipeOffsetY;
        }

        public void setDelegate(Delegate delegate) {
            this.delegate = delegate;
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (this.isScrolling && motionEvent.getActionIndex() != 0) {
                return false;
            }
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            int actionIndex = motionEvent.getActionIndex();
            if (Build.VERSION.SDK_INT >= 29) {
                obtain.setLocation(motionEvent.getRawX(actionIndex), motionEvent.getRawY(actionIndex));
            } else {
                obtain.setLocation(motionEvent.getX(actionIndex) + (motionEvent.getRawX() - motionEvent.getX()), motionEvent.getY(actionIndex) + (motionEvent.getRawY() - motionEvent.getY()));
            }
            boolean onTouchEvent = this.gestureDetector.onTouchEvent(obtain);
            obtain.recycle();
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                this.isSwipeDisallowed = false;
                this.isScrolling = false;
                if (this.flingInProgress) {
                    this.flingInProgress = false;
                } else {
                    float f = this.swipeOffsetY;
                    int i = this.swipeStickyRange;
                    if (f <= (-i)) {
                        stickTo((-this.offsetY) + this.topActionBarOffsetY);
                    } else if (f > (-i) && f <= i) {
                        stickTo(0.0f);
                    } else {
                        Delegate delegate = this.delegate;
                        if (delegate != null) {
                            delegate.onDismiss();
                        }
                    }
                }
            }
            boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
            return !(dispatchTouchEvent || onTouchEvent || motionEvent.getAction() != 0) || dispatchTouchEvent || onTouchEvent;
        }

        public void stickTo(float f) {
            stickTo(f, null);
        }

        public void stickTo(float f, final Runnable runnable) {
            SpringAnimation springAnimation;
            if (this.swipeOffsetY == f || ((springAnimation = this.scrollAnimator) != null && springAnimation.getSpring().getFinalPosition() == f)) {
                if (runnable != null) {
                    runnable.run();
                }
                Runnable runnable2 = this.scrollEndListener;
                if (runnable2 != null) {
                    runnable2.run();
                    return;
                }
                return;
            }
            this.pendingSwipeOffsetY = f;
            SpringAnimation springAnimation2 = this.offsetYAnimator;
            if (springAnimation2 != null) {
                springAnimation2.cancel();
            }
            SpringAnimation springAnimation3 = this.scrollAnimator;
            if (springAnimation3 != null) {
                springAnimation3.cancel();
            }
            SpringAnimation addEndListener = new SpringAnimation(this, SWIPE_OFFSET_Y, f).setSpring(new SpringForce(f).setStiffness(1400.0f).setDampingRatio(1.0f)).addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebViewSwipeContainer$$ExternalSyntheticLambda1
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f2, float f3) {
                    ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer.this.lambda$stickTo$3(runnable, dynamicAnimation, z, f2, f3);
                }
            });
            this.scrollAnimator = addEndListener;
            addEndListener.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stickTo$3(Runnable runnable, DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            if (dynamicAnimation == this.scrollAnimator) {
                this.scrollAnimator = null;
                if (runnable != null) {
                    runnable.run();
                }
                Runnable runnable2 = this.scrollEndListener;
                if (runnable2 != null) {
                    runnable2.run();
                }
                float f3 = this.pendingOffsetY;
                if (f3 != -1.0f) {
                    boolean z2 = this.isSwipeOffsetAnimationDisallowed;
                    this.isSwipeOffsetAnimationDisallowed = true;
                    setOffsetY(f3);
                    this.pendingOffsetY = -1.0f;
                    this.isSwipeOffsetAnimationDisallowed = z2;
                }
                this.pendingSwipeOffsetY = -2.1474836E9f;
            }
        }

        public boolean isSwipeInProgress() {
            return this.isScrolling;
        }
    }

    public static class WebProgressView extends View {
        private final SimpleFloatPropertyCompat<WebProgressView> LOAD_PROGRESS_PROPERTY;
        private Paint bluePaint;
        private float loadProgress;
        private Theme.ResourcesProvider resourcesProvider;
        private SpringAnimation springAnimation;

        public WebProgressView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.LOAD_PROGRESS_PROPERTY = new SimpleFloatPropertyCompat("loadProgress", new SimpleFloatPropertyCompat.Getter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebProgressView$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.Components.SimpleFloatPropertyCompat.Getter
                public final float get(Object obj) {
                    float f;
                    f = ((ChatAttachAlertBotWebViewLayout.WebProgressView) obj).loadProgress;
                    return f;
                }
            }, new SimpleFloatPropertyCompat.Setter() { // from class: org.telegram.ui.Components.ChatAttachAlertBotWebViewLayout$WebProgressView$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.Components.SimpleFloatPropertyCompat.Setter
                public final void set(Object obj, float f) {
                    ((ChatAttachAlertBotWebViewLayout.WebProgressView) obj).setLoadProgress(f);
                }
            }).setMultiplier(100.0f);
            Paint paint = new Paint(1);
            this.bluePaint = paint;
            this.resourcesProvider = resourcesProvider;
            paint.setColor(getThemedColor(Theme.key_featuredStickers_addButton));
            this.bluePaint.setStyle(Paint.Style.STROKE);
            this.bluePaint.setStrokeWidth(AndroidUtilities.dp(2.0f));
            this.bluePaint.setStrokeCap(Paint.Cap.ROUND);
        }

        protected int getThemedColor(int i) {
            return Theme.getColor(i, this.resourcesProvider);
        }

        @Override // android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.springAnimation = new SpringAnimation(this, this.LOAD_PROGRESS_PROPERTY).setSpring(new SpringForce().setStiffness(400.0f).setDampingRatio(1.0f));
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.springAnimation.cancel();
            this.springAnimation = null;
        }

        public void setLoadProgressAnimated(float f) {
            SpringAnimation springAnimation = this.springAnimation;
            if (springAnimation == null) {
                setLoadProgress(f);
            } else {
                springAnimation.getSpring().setFinalPosition(f * 100.0f);
                this.springAnimation.start();
            }
        }

        public void setLoadProgress(float f) {
            this.loadProgress = f;
            invalidate();
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            super.draw(canvas);
            float height = getHeight() - (this.bluePaint.getStrokeWidth() / 2.0f);
            canvas.drawLine(0.0f, height, getWidth() * this.loadProgress, height, this.bluePaint);
        }
    }
}
