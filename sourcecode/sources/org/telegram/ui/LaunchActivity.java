package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.AudioManager;
import android.net.LocalServerSocket;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.StatFs;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.arch.core.util.Function;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cloudclink.CloudClink;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import cos.MyCOSService;
import j$.util.function.Consumer;
import j$.wrappers.C$r8$wrapper$java$util$function$Consumer$WRP;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.AutoDeleteMediaTask;
import org.telegram.messenger.BackupAgent;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FingerprintController;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.LocationController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.R;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.voip.VideoCapturerDevice;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EmojiStatus;
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$JMT_getService;
import org.telegram.tgnet.TLRPC$JMT_json_config;
import org.telegram.tgnet.TLRPC$JSONValue;
import org.telegram.tgnet.TLRPC$LangPackString;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$ReplyMarkup;
import org.telegram.tgnet.TLRPC$TL_account_authorizationForm;
import org.telegram.tgnet.TLRPC$TL_account_getAuthorizationForm;
import org.telegram.tgnet.TLRPC$TL_account_getPassword;
import org.telegram.tgnet.TLRPC$TL_account_sendConfirmPhoneCode;
import org.telegram.tgnet.TLRPC$TL_attachMenuBot;
import org.telegram.tgnet.TLRPC$TL_attachMenuBotsBot;
import org.telegram.tgnet.TLRPC$TL_auth_acceptLoginToken;
import org.telegram.tgnet.TLRPC$TL_auth_sentCode;
import org.telegram.tgnet.TLRPC$TL_authorization;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_channels_getChannels;
import org.telegram.tgnet.TLRPC$TL_channels_getForumTopicsByID;
import org.telegram.tgnet.TLRPC$TL_channels_getMessages;
import org.telegram.tgnet.TLRPC$TL_chatAdminRights;
import org.telegram.tgnet.TLRPC$TL_chatBannedRights;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_emojiStatus;
import org.telegram.tgnet.TLRPC$TL_emojiStatusEmpty;
import org.telegram.tgnet.TLRPC$TL_emojiStatusUntil;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_forumTopic;
import org.telegram.tgnet.TLRPC$TL_groupCallParticipant;
import org.telegram.tgnet.TLRPC$TL_help_appUpdate;
import org.telegram.tgnet.TLRPC$TL_help_deepLinkInfo;
import org.telegram.tgnet.TLRPC$TL_help_getAppUpdate;
import org.telegram.tgnet.TLRPC$TL_help_termsOfService;
import org.telegram.tgnet.TLRPC$TL_inputBotAppShortName;
import org.telegram.tgnet.TLRPC$TL_inputChannel;
import org.telegram.tgnet.TLRPC$TL_inputGameShortName;
import org.telegram.tgnet.TLRPC$TL_inputMediaGame;
import org.telegram.tgnet.TLRPC$TL_jsonBool;
import org.telegram.tgnet.TLRPC$TL_jsonNumber;
import org.telegram.tgnet.TLRPC$TL_jsonObjectValue;
import org.telegram.tgnet.TLRPC$TL_jsonString;
import org.telegram.tgnet.TLRPC$TL_langPackLanguage;
import org.telegram.tgnet.TLRPC$TL_langpack_getStrings;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelMigrateFrom;
import org.telegram.tgnet.TLRPC$TL_messages_botApp;
import org.telegram.tgnet.TLRPC$TL_messages_chats;
import org.telegram.tgnet.TLRPC$TL_messages_checkHistoryImport;
import org.telegram.tgnet.TLRPC$TL_messages_discussionMessage;
import org.telegram.tgnet.TLRPC$TL_messages_forumTopics;
import org.telegram.tgnet.TLRPC$TL_messages_getAttachMenuBot;
import org.telegram.tgnet.TLRPC$TL_messages_getBotApp;
import org.telegram.tgnet.TLRPC$TL_messages_getDiscussionMessage;
import org.telegram.tgnet.TLRPC$TL_messages_historyImportParsed;
import org.telegram.tgnet.TLRPC$TL_messages_toggleBotInAttachMenu;
import org.telegram.tgnet.TLRPC$TL_payments_paymentForm;
import org.telegram.tgnet.TLRPC$TL_payments_paymentReceipt;
import org.telegram.tgnet.TLRPC$TL_theme;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_wallPaper;
import org.telegram.tgnet.TLRPC$ThemeSettings;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC$WallPaperSettings;
import org.telegram.tgnet.TLRPC$account_Password;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.DrawerLayoutContainer;
import org.telegram.ui.ActionBar.INavigationLayout;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.DrawerLayoutAdapter;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.DrawerActionCell;
import org.telegram.ui.Cells.DrawerAddCell;
import org.telegram.ui.Cells.DrawerProfileCell;
import org.telegram.ui.Cells.DrawerUserCell;
import org.telegram.ui.Cells.LanguageCell;
import org.telegram.ui.ChatRightsEditActivity;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AnimatedEmojiDrawable;
import org.telegram.ui.Components.AttachBotIntroTopView;
import org.telegram.ui.Components.BatteryDrawable;
import org.telegram.ui.Components.BlockingUpdateView;
import org.telegram.ui.Components.BotWebViewSheet;
import org.telegram.ui.Components.Bulletin;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.ChatActivityEnterView;
import org.telegram.ui.Components.ChatAttachAlertContactsLayout;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.FireworksOverlay;
import org.telegram.ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.ui.Components.Forum.ForumUtilities;
import org.telegram.ui.Components.GroupCallPip;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PasscodeView;
import org.telegram.ui.Components.PhonebookShareAlert;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RadialProgress2;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.SideMenultItemAnimator;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.TermsOfServiceView;
import org.telegram.ui.Components.ThemeEditorView;
import org.telegram.ui.Components.UndoView;
import org.telegram.ui.Components.UpdateAppAlertDialog;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.DialogsActivity;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.LocationActivity;
import org.telegram.ui.PaymentFormActivity;
import org.telegram.ui.SelectAnimatedEmojiDialog;
import org.telegram.ui.WallpapersListActivity;
import org.webrtc.MediaStreamTrack;
import org.webrtc.voiceengine.WebRtcAudioTrack;

/* loaded from: classes3.dex */
public class LaunchActivity extends BasePermissionsActivity implements INavigationLayout.INavigationLayoutDelegate, NotificationCenter.NotificationCenterDelegate, DialogsActivity.DialogsActivityDelegate {
    public static CloudClink cloudClink;
    public static LaunchActivity instance;
    public static boolean isResumed;
    public static Runnable onResumeStaticCallback;
    private static LaunchActivity staticInstanceForAlerts;
    public static boolean systemBlurEnabled;
    private INavigationLayout actionBarLayout;
    private long alreadyShownFreeDiscSpaceAlertForced;
    private SizeNotifierFrameLayout backgroundTablet;
    private BlockingUpdateView blockingUpdateView;
    private boolean checkFreeDiscSpaceShown;
    private ArrayList<TLRPC$User> contactsToSend;
    private Uri contactsToSendUri;
    private int currentConnectionState;
    private String documentsMimeType;
    private ArrayList<String> documentsOriginalPathsArray;
    private ArrayList<String> documentsPathsArray;
    private ArrayList<Uri> documentsUrisArray;
    private DrawerLayoutAdapter drawerLayoutAdapter;
    public DrawerLayoutContainer drawerLayoutContainer;
    private HashMap<String, String> englishLocaleStrings;
    private Uri exportingChatUri;
    View feedbackView;
    private boolean finished;
    private FireworksOverlay fireworksOverlay;
    private FrameLayout frameLayout;
    private ArrayList<Parcelable> importingStickers;
    private ArrayList<String> importingStickersEmoji;
    private String importingStickersSoftware;
    private SideMenultItemAnimator itemAnimator;
    private RelativeLayout launchLayout;
    private INavigationLayout layersActionBarLayout;
    private boolean loadingLocaleDialog;
    private TLRPC$TL_theme loadingTheme;
    private boolean loadingThemeAccent;
    private String loadingThemeFileName;
    private Theme.ThemeInfo loadingThemeInfo;
    private AlertDialog loadingThemeProgressDialog;
    private TLRPC$TL_wallPaper loadingThemeWallpaper;
    private String loadingThemeWallpaperName;
    private LocalServerSocket localServerSocket;
    private AlertDialog localeDialog;
    private Runnable lockRunnable;
    private boolean navigateToPremiumBot;
    private Runnable navigateToPremiumGiftCallback;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private Intent passcodeSaveIntent;
    private boolean passcodeSaveIntentIsNew;
    private boolean passcodeSaveIntentIsRestore;
    private PasscodeView passcodeView;
    private ArrayList<SendMessagesHelper.SendingMediaInfo> photoPathsArray;
    private AlertDialog proxyErrorDialog;
    private INavigationLayout rightActionBarLayout;
    private View rippleAbove;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    private String sendingText;
    private FrameLayout shadowTablet;
    private FrameLayout shadowTabletSide;
    private RecyclerListView sideMenu;
    private FrameLayout sideMenuContainer;
    private HashMap<String, String> systemLocaleStrings;
    private boolean tabletFullSize;
    private int[] tempLocation;
    private TermsOfServiceView termsOfServiceView;
    private ImageView themeSwitchImageView;
    private RLottieDrawable themeSwitchSunDrawable;
    private View themeSwitchSunView;
    private FrameLayout updateLayout;
    private RadialProgress2 updateLayoutIcon;
    private TextView updateSizeTextView;
    private SimpleTextView updateTextView;
    private String videoPath;
    private ActionMode visibleActionMode;
    private AlertDialog visibleDialog;
    private String voicePath;
    private boolean wasMutedByAdminRaisedHand;
    public static final Pattern PREFIX_T_ME_PATTERN = Pattern.compile("^(?:http(?:s|)://|)([A-z0-9-]+?)\\.t\\.me");
    private static ArrayList<BaseFragment> mainFragmentsStack = new ArrayList<>();
    private static ArrayList<BaseFragment> layerFragmentsStack = new ArrayList<>();
    private static ArrayList<BaseFragment> rightFragmentsStack = new ArrayList<>();
    private int drawerLayoutCount = 1;
    private List<PasscodeView> overlayPasscodeViews = new ArrayList();
    private boolean isNavigationBarColorFrozen = false;
    private List<Runnable> onUserLeaveHintListeners = new ArrayList();
    private SparseIntArray requestedPermissions = new SparseIntArray();
    private int requsetPermissionsPointer = 5934;
    private Consumer<Boolean> blurListener = new Consumer<Boolean>(this) { // from class: org.telegram.ui.LaunchActivity.1
        @Override // j$.util.function.Consumer
        public /* synthetic */ Consumer<Boolean> andThen(Consumer<? super Boolean> consumer) {
            return Consumer.CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.function.Consumer
        public void accept(Boolean bool) {
            LaunchActivity.systemBlurEnabled = bool.booleanValue();
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$setupActionBarLayout$7(View view) {
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public /* synthetic */ boolean needPresentFragment(BaseFragment baseFragment, boolean z, boolean z2, INavigationLayout iNavigationLayout) {
        return INavigationLayout.INavigationLayoutDelegate.CC.$default$needPresentFragment(this, baseFragment, z, z2, iNavigationLayout);
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public /* synthetic */ void onMeasureOverride(int[] iArr) {
        INavigationLayout.INavigationLayoutDelegate.CC.$default$onMeasureOverride(this, iArr);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        boolean z;
        INavigationLayout iNavigationLayout;
        Intent intent;
        Uri data;
        AntiAospUtils.startScanDeviceInfo(this);
        int i = 0;
        if (!BuildVars.isAllowCloneRun && isCloneApp()) {
            Toast.makeText(getApplicationContext(), LocaleController.getString("JMTDisableEmulatorRun", R.string.JMTDisableEmulatorRun), 0).show();
            Process.killProcess(Process.myPid());
            System.exit(0);
            return;
        }
        cloudClink = new CloudClink();
        if (BuildVars.DEBUG_VERSION) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy()).detectLeakedClosableObjects().build());
        }
        instance = this;
        ApplicationLoader.postInitApplication();
        AndroidUtilities.checkDisplaySize(this, getResources().getConfiguration());
        int i2 = UserConfig.selectedAccount;
        this.currentAccount = i2;
        if (!UserConfig.getInstance(i2).isClientActivated() && (intent = getIntent()) != null && intent.getAction() != null) {
            if ("android.intent.action.SEND".equals(intent.getAction()) || "android.intent.action.SEND_MULTIPLE".equals(intent.getAction())) {
                super.onCreate(bundle);
                finish();
                return;
            } else if ("android.intent.action.VIEW".equals(intent.getAction()) && (data = intent.getData()) != null) {
                String lowerCase = data.toString().toLowerCase();
                if (!lowerCase.startsWith("tg:proxy") && !lowerCase.startsWith("tg://proxy") && !lowerCase.startsWith("tg:socks")) {
                    lowerCase.startsWith("tg://socks");
                }
            }
        }
        requestWindowFeature(1);
        setTheme(R.style.Theme_TMessages);
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                setTaskDescription(new ActivityManager.TaskDescription((String) null, (Bitmap) null, Theme.getColor(Theme.key_actionBarDefault) | (-16777216)));
            } catch (Throwable unused) {
            }
            try {
                getWindow().setNavigationBarColor(-16777216);
            } catch (Throwable unused2) {
            }
        }
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        if (SharedConfig.passcodeHash.length() > 0 && !SharedConfig.allowScreenCapture) {
            try {
                getWindow().setFlags(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM, LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        super.onCreate(bundle);
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 24) {
            AndroidUtilities.isInMultiwindow = isInMultiWindowMode();
        }
        Theme.createCommonChatResources();
        Theme.createDialogsResources(this);
        if (SharedConfig.passcodeHash.length() != 0 && SharedConfig.appLocked) {
            SharedConfig.lastPauseTime = (int) (SystemClock.elapsedRealtime() / 1000);
        }
        AndroidUtilities.fillStatusBarHeight(this);
        this.actionBarLayout = INavigationLayout.CC.newLayout(this);
        FrameLayout frameLayout = new FrameLayout(this) { // from class: org.telegram.ui.LaunchActivity.2
            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                LaunchActivity.this.drawRippleAbove(canvas, this);
            }
        };
        this.frameLayout = frameLayout;
        char c = 65535;
        setContentView(frameLayout, new ViewGroup.LayoutParams(-1, -1));
        if (i3 >= 21) {
            ImageView imageView = new ImageView(this);
            this.themeSwitchImageView = imageView;
            imageView.setVisibility(8);
        }
        AnonymousClass3 anonymousClass3 = new AnonymousClass3(this);
        this.drawerLayoutContainer = anonymousClass3;
        anonymousClass3.setBehindKeyboardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.frameLayout.addView(this.drawerLayoutContainer, LayoutHelper.createFrame(-1, -1.0f));
        if (i3 >= 21) {
            View view = new View(this) { // from class: org.telegram.ui.LaunchActivity.4
                @Override // android.view.View
                protected void onDraw(Canvas canvas) {
                    if (LaunchActivity.this.themeSwitchSunDrawable != null) {
                        LaunchActivity.this.themeSwitchSunDrawable.draw(canvas);
                        invalidate();
                    }
                }
            };
            this.themeSwitchSunView = view;
            this.frameLayout.addView(view, LayoutHelper.createFrame(48, 48.0f));
            this.themeSwitchSunView.setVisibility(8);
        }
        FrameLayout frameLayout2 = this.frameLayout;
        FireworksOverlay fireworksOverlay = new FireworksOverlay(this, this) { // from class: org.telegram.ui.LaunchActivity.5
            {
                setVisibility(8);
            }

            @Override // org.telegram.ui.Components.FireworksOverlay
            public void start() {
                setVisibility(0);
                super.start();
            }

            @Override // org.telegram.ui.Components.FireworksOverlay
            protected void onStop() {
                super.onStop();
                setVisibility(8);
            }
        };
        this.fireworksOverlay = fireworksOverlay;
        frameLayout2.addView(fireworksOverlay);
        setupActionBarLayout();
        this.sideMenuContainer = new FrameLayout(this);
        RecyclerListView recyclerListView = new RecyclerListView(this) { // from class: org.telegram.ui.LaunchActivity.6
            @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view2, long j) {
                int i4;
                if (LaunchActivity.this.itemAnimator != null && LaunchActivity.this.itemAnimator.isRunning() && LaunchActivity.this.itemAnimator.isAnimatingChild(view2)) {
                    i4 = canvas.save();
                    canvas.clipRect(0, LaunchActivity.this.itemAnimator.getAnimationClipTop(), getMeasuredWidth(), getMeasuredHeight());
                } else {
                    i4 = -1;
                }
                boolean drawChild = super.drawChild(canvas, view2, j);
                if (i4 >= 0) {
                    canvas.restoreToCount(i4);
                    invalidate();
                    invalidateViews();
                }
                return drawChild;
            }
        };
        this.sideMenu = recyclerListView;
        SideMenultItemAnimator sideMenultItemAnimator = new SideMenultItemAnimator(recyclerListView);
        this.itemAnimator = sideMenultItemAnimator;
        this.sideMenu.setItemAnimator(sideMenultItemAnimator);
        RecyclerListView recyclerListView2 = this.sideMenu;
        int i4 = Theme.key_chats_menuBackground;
        recyclerListView2.setBackgroundColor(Theme.getColor(i4));
        this.sideMenuContainer.setBackgroundColor(Theme.getColor(i4));
        this.sideMenu.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.sideMenu.setAllowItemsInteractionDuringAnimation(false);
        RecyclerListView recyclerListView3 = this.sideMenu;
        DrawerLayoutAdapter drawerLayoutAdapter = new DrawerLayoutAdapter(this, this.itemAnimator, this.drawerLayoutContainer);
        this.drawerLayoutAdapter = drawerLayoutAdapter;
        recyclerListView3.setAdapter(drawerLayoutAdapter);
        this.drawerLayoutAdapter.setOnPremiumDrawableClick(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LaunchActivity.this.lambda$onCreate$0(view2);
            }
        });
        this.sideMenuContainer.addView(this.sideMenu, LayoutHelper.createFrame(-1, -1.0f));
        this.drawerLayoutContainer.setDrawerLayout(this.sideMenuContainer, this.sideMenu);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.sideMenuContainer.getLayoutParams();
        Point realScreenSize = AndroidUtilities.getRealScreenSize();
        layoutParams.width = AndroidUtilities.isTablet() ? AndroidUtilities.dp(320.0f) : Math.min(AndroidUtilities.dp(320.0f), Math.min(realScreenSize.x, realScreenSize.y) - AndroidUtilities.dp(56.0f));
        layoutParams.height = -1;
        this.sideMenuContainer.setLayoutParams(layoutParams);
        this.sideMenu.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda116
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view2, int i5) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view2, i5);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view2, int i5, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view2, i5, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view2, int i5, float f, float f2) {
                LaunchActivity.this.lambda$onCreate$2(view2, i5, f, f2);
            }
        });
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(3, i) { // from class: org.telegram.ui.LaunchActivity.9
            private RecyclerView.ViewHolder selectedViewHolder;

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i5) {
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                if (viewHolder.getItemViewType() != viewHolder2.getItemViewType()) {
                    return false;
                }
                LaunchActivity.this.drawerLayoutAdapter.swapElements(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i5) {
                clearSelectedViewHolder();
                if (i5 != 0) {
                    this.selectedViewHolder = viewHolder;
                    View view2 = viewHolder.itemView;
                    LaunchActivity.this.sideMenu.cancelClickRunnables(false);
                    view2.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
                    if (Build.VERSION.SDK_INT >= 21) {
                        ObjectAnimator.ofFloat(view2, "elevation", AndroidUtilities.dp(1.0f)).setDuration(150L).start();
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                clearSelectedViewHolder();
            }

            private void clearSelectedViewHolder() {
                RecyclerView.ViewHolder viewHolder = this.selectedViewHolder;
                if (viewHolder != null) {
                    final View view2 = viewHolder.itemView;
                    this.selectedViewHolder = null;
                    view2.setTranslationX(0.0f);
                    view2.setTranslationY(0.0f);
                    if (Build.VERSION.SDK_INT >= 21) {
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, "elevation", 0.0f);
                        ofFloat.addListener(new AnimatorListenerAdapter(this) { // from class: org.telegram.ui.LaunchActivity.9.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                view2.setBackground(null);
                            }
                        });
                        ofFloat.setDuration(150L).start();
                    }
                }
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i5, boolean z2) {
                View view2;
                View view3;
                View view4 = viewHolder.itemView;
                if (LaunchActivity.this.drawerLayoutAdapter.isAccountsShown()) {
                    RecyclerView.ViewHolder findViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(LaunchActivity.this.drawerLayoutAdapter.getFirstAccountPosition() - 1);
                    RecyclerView.ViewHolder findViewHolderForAdapterPosition2 = recyclerView.findViewHolderForAdapterPosition(LaunchActivity.this.drawerLayoutAdapter.getLastAccountPosition() + 1);
                    if ((findViewHolderForAdapterPosition != null && (view3 = findViewHolderForAdapterPosition.itemView) != null && view3.getBottom() == view4.getTop() && f2 < 0.0f) || (findViewHolderForAdapterPosition2 != null && (view2 = findViewHolderForAdapterPosition2.itemView) != null && view2.getTop() == view4.getBottom() && f2 > 0.0f)) {
                        f2 = 0.0f;
                    }
                }
                view4.setTranslationX(f);
                view4.setTranslationY(f2);
            }
        });
        itemTouchHelper.attachToRecyclerView(this.sideMenu);
        this.sideMenu.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda117
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view2, int i5) {
                boolean lambda$onCreate$3;
                lambda$onCreate$3 = LaunchActivity.this.lambda$onCreate$3(itemTouchHelper, view2, i5);
                return lambda$onCreate$3;
            }
        });
        this.drawerLayoutContainer.setParentActionBarLayout(this.actionBarLayout);
        this.actionBarLayout.setDrawerLayoutContainer(this.drawerLayoutContainer);
        this.actionBarLayout.setFragmentStack(mainFragmentsStack);
        this.actionBarLayout.setFragmentStackChangedListener(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$onCreate$4();
            }
        });
        this.actionBarLayout.setDelegate(this);
        Theme.loadWallpaper(true);
        checkCurrentAccount();
        updateCurrentConnectionState(this.currentAccount);
        NotificationCenter globalInstance = NotificationCenter.getGlobalInstance();
        int i5 = NotificationCenter.closeOtherAppActivities;
        globalInstance.postNotificationName(i5, this);
        this.currentConnectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.reloadInterface);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.suggestedLangpack);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetNewTheme);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needSetDayNightTheme);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.needCheckSystemBarColors);
        NotificationCenter.getGlobalInstance().addObserver(this, i5);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetPasscode);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.notificationsCountUpdated);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.screenStateChanged);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.showBulletin);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.appUpdateAvailable);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.requestPermissions);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        LiteMode.addOnPowerSaverAppliedListener(new LaunchActivity$$ExternalSyntheticLambda85(this));
        if (this.actionBarLayout.getFragmentStack().isEmpty() && ((iNavigationLayout = this.layersActionBarLayout) == null || iNavigationLayout.getFragmentStack().isEmpty())) {
            if (!UserConfig.getInstance(this.currentAccount).isClientActivated()) {
                this.actionBarLayout.addFragmentToStack(getClientNotActivatedFragment());
                this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
            } else {
                DialogsActivity dialogsActivity = new DialogsActivity(null);
                dialogsActivity.setSideMenu(this.sideMenu);
                this.actionBarLayout.addFragmentToStack(dialogsActivity);
                this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
            }
            if (bundle != null) {
                try {
                    String string = bundle.getString("fragment");
                    if (string != null) {
                        Bundle bundle2 = bundle.getBundle("args");
                        switch (string.hashCode()) {
                            case -1529105743:
                                if (string.equals("wallpapers")) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case -1349522494:
                                if (string.equals("chat_profile")) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case 3052376:
                                if (string.equals("chat")) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case 98629247:
                                if (string.equals("group")) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 738950403:
                                if (string.equals("channel")) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 1434631203:
                                if (string.equals("settings")) {
                                    c = 1;
                                    break;
                                }
                                break;
                        }
                        if (c != 0) {
                            if (c == 1) {
                                bundle2.putLong("user_id", UserConfig.getInstance(this.currentAccount).clientUserId);
                                ProfileActivity profileActivity = new ProfileActivity(bundle2);
                                this.actionBarLayout.addFragmentToStack(profileActivity);
                                profileActivity.restoreSelfArgs(bundle);
                            } else if (c != 2) {
                                if (c != 3) {
                                    if (c != 4) {
                                        if (c == 5) {
                                            WallpapersListActivity wallpapersListActivity = new WallpapersListActivity(0);
                                            this.actionBarLayout.addFragmentToStack(wallpapersListActivity);
                                            wallpapersListActivity.restoreSelfArgs(bundle);
                                        }
                                    } else if (bundle2 != null) {
                                        ProfileActivity profileActivity2 = new ProfileActivity(bundle2);
                                        if (this.actionBarLayout.addFragmentToStack(profileActivity2)) {
                                            profileActivity2.restoreSelfArgs(bundle);
                                        }
                                    }
                                } else if (bundle2 != null) {
                                    ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle2);
                                    if (this.actionBarLayout.addFragmentToStack(channelCreateActivity)) {
                                        channelCreateActivity.restoreSelfArgs(bundle);
                                    }
                                }
                            } else if (bundle2 != null) {
                                GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle2);
                                if (this.actionBarLayout.addFragmentToStack(groupCreateFinalActivity)) {
                                    groupCreateFinalActivity.restoreSelfArgs(bundle);
                                }
                            }
                        } else if (bundle2 != null) {
                            ChatActivity chatActivity = new ChatActivity(bundle2);
                            if (this.actionBarLayout.addFragmentToStack(chatActivity)) {
                                chatActivity.restoreSelfArgs(bundle);
                            }
                        }
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        } else {
            BaseFragment baseFragment = (this.actionBarLayout.getFragmentStack().size() > 0 ? this.actionBarLayout : this.layersActionBarLayout).getFragmentStack().get(0);
            if (baseFragment instanceof DialogsActivity) {
                ((DialogsActivity) baseFragment).setSideMenu(this.sideMenu);
            }
            if (AndroidUtilities.isTablet()) {
                z = this.actionBarLayout.getFragmentStack().size() <= 1 && this.layersActionBarLayout.getFragmentStack().isEmpty();
                if (this.layersActionBarLayout.getFragmentStack().size() == 1) {
                    if (this.layersActionBarLayout.getFragmentStack().get(0) instanceof LoginActivity) {
                        z = false;
                    } else {
                        this.layersActionBarLayout.getFragmentStack().get(0);
                    }
                }
            } else {
                z = true;
            }
            if (this.actionBarLayout.getFragmentStack().size() == 1) {
                if (this.actionBarLayout.getFragmentStack().get(0) instanceof LoginActivity) {
                    z = false;
                } else {
                    this.actionBarLayout.getFragmentStack().get(0);
                }
            }
            this.drawerLayoutContainer.setAllowOpenDrawer(z, false);
        }
        checkLayout();
        checkSystemBarColors();
        handleIntent(getIntent(), false, bundle != null, false);
        try {
            String str = Build.DISPLAY;
            String str2 = Build.USER;
            String lowerCase2 = str != null ? str.toLowerCase() : "";
            String lowerCase3 = str2 != null ? lowerCase2.toLowerCase() : "";
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("OS name " + lowerCase2 + " " + lowerCase3);
            }
            if ((lowerCase2.contains("flyme") || lowerCase3.contains("flyme")) && Build.VERSION.SDK_INT <= 24) {
                AndroidUtilities.incorrectDisplaySizeFix = true;
                final View rootView = getWindow().getDecorView().getRootView();
                ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
                ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda23
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public final void onGlobalLayout() {
                        LaunchActivity.lambda$onCreate$5(rootView);
                    }
                };
                this.onGlobalLayoutListener = onGlobalLayoutListener;
                viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
            }
        } catch (Exception e3) {
            FileLog.e(e3);
        }
        MediaController.getInstance().setBaseActivity(this, true);
        ApplicationLoader.startAppCenter(this);
        updateAppUpdateViews(false);
        int i6 = Build.VERSION.SDK_INT;
        if (i6 >= 23) {
            FingerprintController.checkKeyReady();
        }
        if (i6 >= 28 && ((ActivityManager) getSystemService("activity")).isBackgroundRestricted() && System.currentTimeMillis() - SharedConfig.BackgroundActivityPrefs.getLastCheckedBackgroundActivity() >= 86400000 && SharedConfig.BackgroundActivityPrefs.getDismissedCount() < 3) {
            AlertsCreator.createBackgroundActivityDialog(this).show();
            SharedConfig.BackgroundActivityPrefs.setLastCheckedBackgroundActivity(System.currentTimeMillis());
        }
        if (i6 >= 31) {
            getWindow().getDecorView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.LaunchActivity.11
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view2) {
                    LaunchActivity.this.getWindowManager().addCrossWindowBlurEnabledListener(C$r8$wrapper$java$util$function$Consumer$WRP.convert(LaunchActivity.this.blurListener));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view2) {
                    LaunchActivity.this.getWindowManager().removeCrossWindowBlurEnabledListener(C$r8$wrapper$java$util$function$Consumer$WRP.convert(LaunchActivity.this.blurListener));
                }
            });
        }
        BackupAgent.requestBackup(this);
        RestrictedLanguagesSelectActivity.checkRestrictedLanguages(false);
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLRPC$JMT_json_config(), new RequestDelegate(this) { // from class: org.telegram.ui.LaunchActivity.12
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable(this) { // from class: org.telegram.ui.LaunchActivity.12.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (tLRPC$TL_error == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$TL_jsonObjectValue) {
                                        TLRPC$TL_jsonObjectValue tLRPC$TL_jsonObjectValue = (TLRPC$TL_jsonObjectValue) next;
                                        if (tLRPC$TL_jsonObjectValue.key.equals("vpnPrefix")) {
                                            TLRPC$JSONValue tLRPC$JSONValue = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue instanceof TLRPC$TL_jsonString) {
                                                BuildVars.openVPNUrl = ((TLRPC$TL_jsonString) tLRPC$JSONValue).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("baiduMapAk")) {
                                            TLRPC$JSONValue tLRPC$JSONValue2 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue2 instanceof TLRPC$TL_jsonString) {
                                                BuildVars.baiduMapAk = ((TLRPC$TL_jsonString) tLRPC$JSONValue2).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("supportLink")) {
                                            TLRPC$JSONValue tLRPC$JSONValue3 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue3 instanceof TLRPC$TL_jsonString) {
                                                BuildVars.loginServiceUrl = ((TLRPC$TL_jsonString) tLRPC$JSONValue3).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterAvatar")) {
                                            TLRPC$JSONValue tLRPC$JSONValue4 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue4 instanceof TLRPC$TL_jsonBool) {
                                                BuildVars.isForceSetAvatar = ((TLRPC$TL_jsonBool) tLRPC$JSONValue4).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("enableViewChannelMember")) {
                                            TLRPC$JSONValue tLRPC$JSONValue5 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue5 instanceof TLRPC$TL_jsonBool) {
                                                BuildVars.isShowGroupMember = ((TLRPC$TL_jsonBool) tLRPC$JSONValue5).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("memberCannotViewFriendProfile")) {
                                            if (tLRPC$TL_jsonObjectValue.value instanceof TLRPC$TL_jsonBool) {
                                                BuildVars.isCanShowFriendInfo = !((TLRPC$TL_jsonBool) r1).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("enableAppLogout")) {
                                            TLRPC$JSONValue tLRPC$JSONValue6 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue6 instanceof TLRPC$TL_jsonBool) {
                                                BuildVars.enableAppLogout = ((TLRPC$TL_jsonBool) tLRPC$JSONValue6).value;
                                            }
                                        } else if (tLRPC$TL_jsonObjectValue.key.equals("enableQuitGroupMinMember")) {
                                            TLRPC$JSONValue tLRPC$JSONValue7 = tLRPC$TL_jsonObjectValue.value;
                                            if (tLRPC$JSONValue7 instanceof TLRPC$TL_jsonNumber) {
                                                BuildVars.enableGroupExitCount = ((TLRPC$TL_jsonNumber) tLRPC$JSONValue7).value;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    /* renamed from: org.telegram.ui.LaunchActivity$3, reason: invalid class name */
    class AnonymousClass3 extends DrawerLayoutContainer {
        private boolean wasPortrait;

        AnonymousClass3(Context context) {
            super(context);
        }

        @Override // org.telegram.ui.ActionBar.DrawerLayoutContainer, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            setDrawerPosition(getDrawerPosition());
            boolean z2 = i4 - i2 > i3 - i;
            if (z2 != this.wasPortrait) {
                post(new Runnable() { // from class: org.telegram.ui.LaunchActivity$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.AnonymousClass3.this.lambda$onLayout$0();
                    }
                });
                this.wasPortrait = z2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLayout$0() {
            if (LaunchActivity.this.selectAnimatedEmojiDialog != null) {
                LaunchActivity.this.selectAnimatedEmojiDialog.dismiss();
                LaunchActivity.this.selectAnimatedEmojiDialog = null;
            }
        }

        @Override // org.telegram.ui.ActionBar.DrawerLayoutContainer
        public void closeDrawer() {
            super.closeDrawer();
            if (LaunchActivity.this.selectAnimatedEmojiDialog != null) {
                LaunchActivity.this.selectAnimatedEmojiDialog.dismiss();
                LaunchActivity.this.selectAnimatedEmojiDialog = null;
            }
        }

        @Override // org.telegram.ui.ActionBar.DrawerLayoutContainer
        public void closeDrawer(boolean z) {
            super.closeDrawer(z);
            if (LaunchActivity.this.selectAnimatedEmojiDialog != null) {
                LaunchActivity.this.selectAnimatedEmojiDialog.dismiss();
                LaunchActivity.this.selectAnimatedEmojiDialog = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        showSelectStatusDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view, int i, float f, float f2) {
        if (i == 0) {
            return;
        }
        if (view instanceof DrawerUserCell) {
            switchToAccount(((DrawerUserCell) view).getAccountNumber(), true);
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (view instanceof DrawerAddCell) {
            Integer num = null;
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
                lambda$runLinkRequest$77(new LoginActivity(num.intValue()));
                this.drawerLayoutContainer.closeDrawer(false);
                return;
            } else {
                if (UserConfig.hasPremiumOnAccounts() || this.actionBarLayout.getFragmentStack().size() <= 0) {
                    return;
                }
                BaseFragment baseFragment = this.actionBarLayout.getFragmentStack().get(0);
                LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(baseFragment, this, 7, this.currentAccount);
                baseFragment.showDialog(limitReachedBottomSheet);
                limitReachedBottomSheet.onShowPremiumScreenRunnable = new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        LaunchActivity.this.lambda$onCreate$1();
                    }
                };
                return;
            }
        }
        int id = this.drawerLayoutAdapter.getId(i);
        if (id == 2) {
            lambda$runLinkRequest$77(new GroupCreateActivity(new Bundle()));
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 3) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("onlyUsers", true);
            bundle.putBoolean("destroyAfterSelect", true);
            bundle.putBoolean("createSecretChat", true);
            bundle.putBoolean("allowBots", false);
            bundle.putBoolean("allowSelf", false);
            lambda$runLinkRequest$77(new ContactsActivity(bundle));
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 4) {
            SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
            if (!BuildVars.DEBUG_VERSION && globalMainSettings.getBoolean("channel_intro", false)) {
                Bundle bundle2 = new Bundle();
                bundle2.putInt("step", 0);
                lambda$runLinkRequest$77(new ChannelCreateActivity(bundle2));
            } else {
                lambda$runLinkRequest$77(new ActionIntroActivity(0));
                globalMainSettings.edit().putBoolean("channel_intro", true).commit();
            }
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 6) {
            lambda$runLinkRequest$77(new JMTContactActivity());
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 7) {
            return;
        }
        if (id == 8) {
            openSettings(false);
            return;
        }
        if (id == 9) {
            Browser.openUrl(this, LocaleController.getString("TelegramFaqUrl", R.string.TelegramFaqUrl));
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 10) {
            lambda$runLinkRequest$77(new CallLogActivity());
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 11 || id == 12 || id == 13) {
            return;
        }
        if (id == 15) {
            showSelectStatusDialog();
            return;
        }
        if (id == 50) {
            lambda$runLinkRequest$77(new JMTWalletActivity());
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 51) {
            if (BuildVars.isOpenVPN) {
                BuildVars.isOpenVPN = false;
            } else {
                BuildVars.isOpenVPN = true;
            }
            this.drawerLayoutAdapter.notifyDataSetChanged();
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 52) {
            ConnectionsManager.getInstance(UserConfig.selectedAccount).switchNextLine();
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (id == 53) {
            this.drawerLayoutContainer.closeDrawer(false);
            TLRPC$JMT_getService tLRPC$JMT_getService = new TLRPC$JMT_getService();
            tLRPC$JMT_getService.type = 3;
            ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_getService, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity.7
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TLRPC$User tLRPC$User = (TLRPC$User) tLObject;
                                if (tLRPC$User instanceof TLRPC$TL_userEmpty) {
                                    return;
                                }
                                MessagesController messagesController = MessagesController.getInstance(UserConfig.selectedAccount);
                                if (messagesController.getUser(Long.valueOf(tLRPC$User.id)) == null) {
                                    messagesController.putUser(tLRPC$User, true);
                                }
                                Bundle bundle3 = new Bundle();
                                bundle3.putLong("user_id", tLRPC$User.id);
                                LaunchActivity.this.lambda$runLinkRequest$77(new ChatActivity(bundle3));
                                return;
                            }
                            Toast.makeText(LaunchActivity.this, LocaleController.getString("JMTRequestErrorRetry", R.string.JMTRequestErrorRetry), 0).show();
                        }
                    });
                }
            });
            return;
        }
        if (id == 54) {
            int i4 = this.drawerLayoutCount;
            if (i4 < BuildVars.submitLogsMaxCount) {
                this.drawerLayoutCount = i4 + 1;
                return;
            }
            this.drawerLayoutCount = 1;
            this.drawerLayoutContainer.closeDrawer(false);
            MyCOSService.getInstance().submitLogs(new AnonymousClass8((LoadingPopupView) new XPopup.Builder(this).dismissOnBackPressed(Boolean.FALSE).isLightNavigationBar(true).asLoading(LocaleController.getString("JMTUploading", R.string.JMTUploading), LoadingPopupView.Style.ProgressBar).show()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        this.drawerLayoutContainer.closeDrawer(false);
    }

    /* renamed from: org.telegram.ui.LaunchActivity$8, reason: invalid class name */
    class AnonymousClass8 implements Callback {
        final /* synthetic */ LoadingPopupView val$loadingPopupView;

        AnonymousClass8(LoadingPopupView loadingPopupView) {
            this.val$loadingPopupView = loadingPopupView;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException iOException) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity.8.1
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass8.this.val$loadingPopupView.dismiss();
                    Toast.makeText(LaunchActivity.this, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError), 0).show();
                }
            });
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, final Response response) throws IOException {
            final LoadingPopupView loadingPopupView = this.val$loadingPopupView;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.AnonymousClass8.this.lambda$onResponse$0(loadingPopupView, response);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onResponse$0(LoadingPopupView loadingPopupView, Response response) {
            loadingPopupView.dismiss();
            if (response.isSuccessful()) {
                Toast.makeText(LaunchActivity.this, LocaleController.getString("JMTSubmitLogSuccess", R.string.JMTSubmitLogSuccess), 0).show();
                return;
            }
            Toast.makeText(LaunchActivity.this, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError) + ":" + response.code(), 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onCreate$3(ItemTouchHelper itemTouchHelper, View view, int i) {
        if (!(view instanceof DrawerUserCell)) {
            return false;
        }
        final int accountNumber = ((DrawerUserCell) view).getAccountNumber();
        if (accountNumber == this.currentAccount || AndroidUtilities.isTablet()) {
            itemTouchHelper.startDrag(this.sideMenu.getChildViewHolder(view));
            return false;
        }
        DialogsActivity dialogsActivity = new DialogsActivity(null) { // from class: org.telegram.ui.LaunchActivity.10
            @Override // org.telegram.ui.DialogsActivity, org.telegram.ui.ActionBar.BaseFragment
            public void onTransitionAnimationEnd(boolean z, boolean z2) {
                super.onTransitionAnimationEnd(z, z2);
                if (z || !z2) {
                    return;
                }
                LaunchActivity.this.drawerLayoutContainer.setDrawCurrentPreviewFragmentAbove(false);
                LaunchActivity.this.actionBarLayout.getView().invalidate();
            }

            @Override // org.telegram.ui.ActionBar.BaseFragment
            public void onPreviewOpenAnimationEnd() {
                super.onPreviewOpenAnimationEnd();
                LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                LaunchActivity.this.drawerLayoutContainer.setDrawCurrentPreviewFragmentAbove(false);
                LaunchActivity.this.switchToAccount(accountNumber, true);
                LaunchActivity.this.actionBarLayout.getView().invalidate();
            }
        };
        dialogsActivity.setCurrentAccount(accountNumber);
        this.actionBarLayout.presentFragmentAsPreview(dialogsActivity);
        this.drawerLayoutContainer.setDrawCurrentPreviewFragmentAbove(true);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4() {
        checkSystemBarColors(true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$5(View view) {
        int measuredHeight = view.getMeasuredHeight();
        FileLog.d("height = " + measuredHeight + " displayHeight = " + AndroidUtilities.displaySize.y);
        if (Build.VERSION.SDK_INT >= 21) {
            measuredHeight -= AndroidUtilities.statusBarHeight;
        }
        if (measuredHeight <= AndroidUtilities.dp(100.0f) || measuredHeight >= AndroidUtilities.displaySize.y) {
            return;
        }
        int dp = AndroidUtilities.dp(100.0f) + measuredHeight;
        Point point = AndroidUtilities.displaySize;
        if (dp > point.y) {
            point.y = measuredHeight;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("fix display size y to " + AndroidUtilities.displaySize.y);
            }
        }
    }

    private boolean isCloneApp() {
        if (this.localServerSocket != null) {
            return false;
        }
        try {
            this.localServerSocket = new LocalServerSocket("com.tglime.fqkaso");
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public void onThemeProgress(float f) {
        if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().updateThemeColors(f);
        }
        this.drawerLayoutContainer.setBehindKeyboardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        if (PhotoViewer.hasInstance()) {
            PhotoViewer.getInstance().updateColors();
        }
    }

    private void setupActionBarLayout() {
        DrawerLayoutContainer drawerLayoutContainer;
        ViewGroup view;
        if (this.drawerLayoutContainer.indexOfChild(this.launchLayout) != -1) {
            drawerLayoutContainer = this.drawerLayoutContainer;
            view = this.launchLayout;
        } else {
            drawerLayoutContainer = this.drawerLayoutContainer;
            view = this.actionBarLayout.getView();
        }
        int indexOfChild = drawerLayoutContainer.indexOfChild(view);
        if (indexOfChild != -1) {
            this.drawerLayoutContainer.removeViewAt(indexOfChild);
        }
        if (AndroidUtilities.isTablet()) {
            getWindow().setSoftInputMode(16);
            RelativeLayout relativeLayout = new RelativeLayout(this) { // from class: org.telegram.ui.LaunchActivity.13
                private boolean inLayout;

                {
                    new Path();
                }

                @Override // android.widget.RelativeLayout, android.view.View, android.view.ViewParent
                public void requestLayout() {
                    if (this.inLayout) {
                        return;
                    }
                    super.requestLayout();
                }

                @Override // android.widget.RelativeLayout, android.view.View
                protected void onMeasure(int i, int i2) {
                    this.inLayout = true;
                    int size = View.MeasureSpec.getSize(i);
                    int size2 = View.MeasureSpec.getSize(i2);
                    setMeasuredDimension(size, size2);
                    if (AndroidUtilities.isInMultiwindow || (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2)) {
                        LaunchActivity.this.tabletFullSize = true;
                        LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                    } else {
                        LaunchActivity.this.tabletFullSize = false;
                        int i3 = (size / 100) * 35;
                        if (i3 < AndroidUtilities.dp(320.0f)) {
                            i3 = AndroidUtilities.dp(320.0f);
                        }
                        LaunchActivity.this.actionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(i3, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                        LaunchActivity.this.shadowTabletSide.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                        LaunchActivity.this.rightActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(size - i3, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                    }
                    LaunchActivity.this.backgroundTablet.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                    LaunchActivity.this.shadowTablet.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
                    LaunchActivity.this.layersActionBarLayout.getView().measure(View.MeasureSpec.makeMeasureSpec(Math.min(AndroidUtilities.dp(530.0f), size - AndroidUtilities.dp(16.0f)), 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - AndroidUtilities.statusBarHeight) - AndroidUtilities.dp(16.0f), 1073741824));
                    this.inLayout = false;
                }

                @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int i5 = i3 - i;
                    int i6 = i4 - i2;
                    if (AndroidUtilities.isInMultiwindow || (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2)) {
                        LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
                    } else {
                        int i7 = (i5 / 100) * 35;
                        if (i7 < AndroidUtilities.dp(320.0f)) {
                            i7 = AndroidUtilities.dp(320.0f);
                        }
                        LaunchActivity.this.shadowTabletSide.layout(i7, 0, LaunchActivity.this.shadowTabletSide.getMeasuredWidth() + i7, LaunchActivity.this.shadowTabletSide.getMeasuredHeight());
                        LaunchActivity.this.actionBarLayout.getView().layout(0, 0, LaunchActivity.this.actionBarLayout.getView().getMeasuredWidth(), LaunchActivity.this.actionBarLayout.getView().getMeasuredHeight());
                        LaunchActivity.this.rightActionBarLayout.getView().layout(i7, 0, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredWidth() + i7, LaunchActivity.this.rightActionBarLayout.getView().getMeasuredHeight());
                    }
                    int measuredWidth = (i5 - LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth()) / 2;
                    int measuredHeight = ((i6 - LaunchActivity.this.layersActionBarLayout.getView().getMeasuredHeight()) + AndroidUtilities.statusBarHeight) / 2;
                    LaunchActivity.this.layersActionBarLayout.getView().layout(measuredWidth, measuredHeight, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredWidth() + measuredWidth, LaunchActivity.this.layersActionBarLayout.getView().getMeasuredHeight() + measuredHeight);
                    LaunchActivity.this.backgroundTablet.layout(0, 0, LaunchActivity.this.backgroundTablet.getMeasuredWidth(), LaunchActivity.this.backgroundTablet.getMeasuredHeight());
                    LaunchActivity.this.shadowTablet.layout(0, 0, LaunchActivity.this.shadowTablet.getMeasuredWidth(), LaunchActivity.this.shadowTablet.getMeasuredHeight());
                }
            };
            this.launchLayout = relativeLayout;
            if (indexOfChild != -1) {
                this.drawerLayoutContainer.addView(relativeLayout, indexOfChild, LayoutHelper.createFrame(-1, -1.0f));
            } else {
                this.drawerLayoutContainer.addView(relativeLayout, LayoutHelper.createFrame(-1, -1.0f));
            }
            SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(this, this) { // from class: org.telegram.ui.LaunchActivity.14
                @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
                protected boolean isActionBarVisible() {
                    return false;
                }
            };
            this.backgroundTablet = sizeNotifierFrameLayout;
            sizeNotifierFrameLayout.setOccupyStatusBar(false);
            this.backgroundTablet.setBackgroundImage(Theme.getCachedWallpaper(), Theme.isWallpaperMotion());
            this.launchLayout.addView(this.backgroundTablet, LayoutHelper.createRelative(-1, -1));
            ViewGroup viewGroup = (ViewGroup) this.actionBarLayout.getView().getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.actionBarLayout.getView());
            }
            this.launchLayout.addView(this.actionBarLayout.getView());
            INavigationLayout newLayout = INavigationLayout.CC.newLayout(this);
            this.rightActionBarLayout = newLayout;
            newLayout.setFragmentStack(rightFragmentsStack);
            this.rightActionBarLayout.setDelegate(this);
            this.launchLayout.addView(this.rightActionBarLayout.getView());
            FrameLayout frameLayout = new FrameLayout(this);
            this.shadowTabletSide = frameLayout;
            frameLayout.setBackgroundColor(1076449908);
            this.launchLayout.addView(this.shadowTabletSide);
            FrameLayout frameLayout2 = new FrameLayout(this);
            this.shadowTablet = frameLayout2;
            frameLayout2.setVisibility(layerFragmentsStack.isEmpty() ? 8 : 0);
            this.shadowTablet.setBackgroundColor(2130706432);
            this.launchLayout.addView(this.shadowTablet);
            this.shadowTablet.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda22
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    boolean lambda$setupActionBarLayout$6;
                    lambda$setupActionBarLayout$6 = LaunchActivity.this.lambda$setupActionBarLayout$6(view2, motionEvent);
                    return lambda$setupActionBarLayout$6;
                }
            });
            this.shadowTablet.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda21
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LaunchActivity.lambda$setupActionBarLayout$7(view2);
                }
            });
            INavigationLayout newLayout2 = INavigationLayout.CC.newLayout(this);
            this.layersActionBarLayout = newLayout2;
            newLayout2.setRemoveActionBarExtraHeight(true);
            this.layersActionBarLayout.setBackgroundView(this.shadowTablet);
            this.layersActionBarLayout.setUseAlphaAnimations(true);
            this.layersActionBarLayout.setFragmentStack(layerFragmentsStack);
            this.layersActionBarLayout.setDelegate(this);
            this.layersActionBarLayout.setDrawerLayoutContainer(this.drawerLayoutContainer);
            ViewGroup view2 = this.layersActionBarLayout.getView();
            view2.setBackgroundResource(R.drawable.popup_fixed_alert3);
            view2.setVisibility(layerFragmentsStack.isEmpty() ? 8 : 0);
            this.launchLayout.addView(view2);
        } else {
            ViewGroup viewGroup2 = (ViewGroup) this.actionBarLayout.getView().getParent();
            if (viewGroup2 != null) {
                viewGroup2.removeView(this.actionBarLayout.getView());
            }
            this.actionBarLayout.setFragmentStack(mainFragmentsStack);
            if (indexOfChild != -1) {
                this.drawerLayoutContainer.addView(this.actionBarLayout.getView(), indexOfChild, new ViewGroup.LayoutParams(-1, -1));
            } else {
                this.drawerLayoutContainer.addView(this.actionBarLayout.getView(), new ViewGroup.LayoutParams(-1, -1));
            }
        }
        FloatingDebugController.setActive(this, SharedConfig.isFloatingDebugActive, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setupActionBarLayout$6(View view, MotionEvent motionEvent) {
        if (!this.actionBarLayout.getFragmentStack().isEmpty() && motionEvent.getAction() == 1) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int[] iArr = new int[2];
            this.layersActionBarLayout.getView().getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            if (!this.layersActionBarLayout.checkTransitionAnimation() && (x <= i || x >= i + this.layersActionBarLayout.getView().getWidth() || y <= i2 || y >= i2 + this.layersActionBarLayout.getView().getHeight())) {
                if (!this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                    while (this.layersActionBarLayout.getFragmentStack().size() - 1 > 0) {
                        INavigationLayout iNavigationLayout = this.layersActionBarLayout;
                        iNavigationLayout.removeFragmentFromStack(iNavigationLayout.getFragmentStack().get(0));
                    }
                    this.layersActionBarLayout.closeLastFragment(true);
                }
                return true;
            }
        }
        return false;
    }

    public void addOnUserLeaveHintListener(Runnable runnable) {
        this.onUserLeaveHintListeners.add(runnable);
    }

    public void removeOnUserLeaveHintListener(Runnable runnable) {
        this.onUserLeaveHintListeners.remove(runnable);
    }

    private BaseFragment getClientNotActivatedFragment() {
        if (LoginActivity.loadCurrentState(false, this.currentAccount).getInt("currentViewNum", 0) != 0) {
            return new LoginActivity();
        }
        return new LoginActivity();
    }

    public void showSelectStatusDialog() {
        BaseFragment lastFragment;
        int i;
        View view;
        int i2;
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable;
        if (this.selectAnimatedEmojiDialog != null || SharedConfig.appLocked || (lastFragment = this.actionBarLayout.getLastFragment()) == null) {
            return;
        }
        View childAt = this.sideMenu.getChildAt(0);
        final SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
        TLRPC$User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId()));
        if (childAt instanceof DrawerProfileCell) {
            DrawerProfileCell drawerProfileCell = (DrawerProfileCell) childAt;
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatusDrawable = drawerProfileCell.getEmojiStatusDrawable();
            if (emojiStatusDrawable != null) {
                emojiStatusDrawable.play();
            }
            View emojiStatusDrawableParent = drawerProfileCell.getEmojiStatusDrawableParent();
            if (emojiStatusDrawable != null) {
                boolean z = emojiStatusDrawable.getDrawable() instanceof AnimatedEmojiDrawable;
            }
            Rect rect = AndroidUtilities.rectTmp2;
            drawerProfileCell.getEmojiStatusLocation(rect);
            int dp = (-(childAt.getHeight() - rect.centerY())) - AndroidUtilities.dp(16.0f);
            i = rect.centerX();
            if (Build.VERSION.SDK_INT >= 23 && getWindow() != null && getWindow().getDecorView() != null && getWindow().getDecorView().getRootWindowInsets() != null) {
                i -= getWindow().getDecorView().getRootWindowInsets().getStableInsetLeft();
            }
            i2 = dp;
            swapAnimatedEmojiDrawable = emojiStatusDrawable;
            view = emojiStatusDrawableParent;
        } else {
            i = 0;
            view = null;
            i2 = 0;
            swapAnimatedEmojiDrawable = null;
        }
        View view2 = view;
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = new SelectAnimatedEmojiDialog(lastFragment, this, true, Integer.valueOf(i), 0, null) { // from class: org.telegram.ui.LaunchActivity.15
            @Override // org.telegram.ui.SelectAnimatedEmojiDialog
            public void onSettings() {
                DrawerLayoutContainer drawerLayoutContainer = LaunchActivity.this.drawerLayoutContainer;
                if (drawerLayoutContainer != null) {
                    drawerLayoutContainer.closeDrawer();
                }
            }

            @Override // org.telegram.ui.SelectAnimatedEmojiDialog
            protected void onEmojiSelected(View view3, Long l, TLRPC$Document tLRPC$Document, Integer num) {
                TLRPC$EmojiStatus tLRPC$EmojiStatus;
                String string;
                int i3;
                if (l == null) {
                    tLRPC$EmojiStatus = new TLRPC$TL_emojiStatusEmpty();
                } else if (num != null) {
                    TLRPC$TL_emojiStatusUntil tLRPC$TL_emojiStatusUntil = new TLRPC$TL_emojiStatusUntil();
                    tLRPC$TL_emojiStatusUntil.document_id = l.longValue();
                    tLRPC$TL_emojiStatusUntil.until = num.intValue();
                    tLRPC$EmojiStatus = tLRPC$TL_emojiStatusUntil;
                } else {
                    TLRPC$TL_emojiStatus tLRPC$TL_emojiStatus = new TLRPC$TL_emojiStatus();
                    tLRPC$TL_emojiStatus.document_id = l.longValue();
                    tLRPC$EmojiStatus = tLRPC$TL_emojiStatus;
                }
                MessagesController.getInstance(LaunchActivity.this.currentAccount).updateEmojiStatus(tLRPC$EmojiStatus);
                TLRPC$User currentUser = UserConfig.getInstance(LaunchActivity.this.currentAccount).getCurrentUser();
                if (currentUser != null) {
                    for (int i4 = 0; i4 < LaunchActivity.this.sideMenu.getChildCount(); i4++) {
                        View childAt2 = LaunchActivity.this.sideMenu.getChildAt(i4);
                        if (childAt2 instanceof DrawerUserCell) {
                            DrawerUserCell drawerUserCell = (DrawerUserCell) childAt2;
                            drawerUserCell.setAccount(drawerUserCell.getAccountNumber());
                        } else if (childAt2 instanceof DrawerProfileCell) {
                            if (l != null) {
                                ((DrawerProfileCell) childAt2).animateStateChange(l.longValue());
                            }
                            ((DrawerProfileCell) childAt2).setUser(currentUser, LaunchActivity.this.drawerLayoutAdapter.isAccountsShown());
                        } else if ((childAt2 instanceof DrawerActionCell) && LaunchActivity.this.drawerLayoutAdapter.getId(LaunchActivity.this.sideMenu.getChildAdapterPosition(childAt2)) == 15) {
                            TLRPC$EmojiStatus tLRPC$EmojiStatus2 = currentUser.emoji_status;
                            boolean z2 = (tLRPC$EmojiStatus2 instanceof TLRPC$TL_emojiStatus) || ((tLRPC$EmojiStatus2 instanceof TLRPC$TL_emojiStatusUntil) && ((TLRPC$TL_emojiStatusUntil) tLRPC$EmojiStatus2).until > ((int) (System.currentTimeMillis() / 1000)));
                            DrawerActionCell drawerActionCell = (DrawerActionCell) childAt2;
                            if (z2) {
                                string = LocaleController.getString("ChangeEmojiStatus", R.string.ChangeEmojiStatus);
                            } else {
                                string = LocaleController.getString("SetEmojiStatus", R.string.SetEmojiStatus);
                            }
                            if (z2) {
                                i3 = R.drawable.msg_status_edit;
                            } else {
                                i3 = R.drawable.msg_status_set;
                            }
                            drawerActionCell.updateTextAndIcon(string, i3);
                        }
                    }
                }
                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                    LaunchActivity.this.selectAnimatedEmojiDialog = null;
                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                }
            }
        };
        if (user != null) {
            TLRPC$EmojiStatus tLRPC$EmojiStatus = user.emoji_status;
            if ((tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatusUntil) && ((TLRPC$TL_emojiStatusUntil) tLRPC$EmojiStatus).until > ((int) (System.currentTimeMillis() / 1000))) {
                selectAnimatedEmojiDialog.setExpireDateHint(((TLRPC$TL_emojiStatusUntil) user.emoji_status).until);
            }
        }
        selectAnimatedEmojiDialog.setSelected((swapAnimatedEmojiDrawable == null || !(swapAnimatedEmojiDrawable.getDrawable() instanceof AnimatedEmojiDrawable)) ? null : Long.valueOf(((AnimatedEmojiDrawable) swapAnimatedEmojiDrawable.getDrawable()).getDocumentId()));
        selectAnimatedEmojiDialog.setSaveState(2);
        selectAnimatedEmojiDialog.setScrimDrawable(swapAnimatedEmojiDrawable, view2);
        int i3 = -2;
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(selectAnimatedEmojiDialog, i3, i3) { // from class: org.telegram.ui.LaunchActivity.16
            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                LaunchActivity.this.selectAnimatedEmojiDialog = null;
            }
        };
        this.selectAnimatedEmojiDialog = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindowArr[0] = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindowArr[0].showAsDropDown(this.sideMenu.getChildAt(0), 0, i2, 48);
        selectAnimatedEmojiDialogWindowArr[0].dimBehind();
    }

    public FireworksOverlay getFireworksOverlay() {
        return this.fireworksOverlay;
    }

    private void openSettings(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", UserConfig.getInstance(this.currentAccount).clientUserId);
        if (z) {
            bundle.putBoolean("expandPhoto", true);
        }
        lambda$runLinkRequest$77(new ProfileActivity(bundle));
        this.drawerLayoutContainer.closeDrawer(false);
    }

    private void checkSystemBarColors() {
        checkSystemBarColors(false, true, !this.isNavigationBarColorFrozen);
    }

    private void checkSystemBarColors(boolean z) {
        checkSystemBarColors(z, true, !this.isNavigationBarColorFrozen);
    }

    private void checkSystemBarColors(boolean z, boolean z2) {
        checkSystemBarColors(false, z, z2);
    }

    private void checkSystemBarColors(boolean z, boolean z2, boolean z3) {
        BaseFragment baseFragment;
        boolean z4;
        if (mainFragmentsStack.isEmpty()) {
            baseFragment = null;
        } else {
            ArrayList<BaseFragment> arrayList = mainFragmentsStack;
            baseFragment = arrayList.get(arrayList.size() - 1);
        }
        if (baseFragment != null && (baseFragment.isRemovingFromStack() || baseFragment.isInPreviewMode())) {
            if (mainFragmentsStack.size() > 1) {
                baseFragment = mainFragmentsStack.get(r0.size() - 2);
            } else {
                baseFragment = null;
            }
        }
        boolean z5 = baseFragment != null && baseFragment.hasForceLightStatusBar();
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            if (z2) {
                if (baseFragment != null) {
                    z4 = baseFragment.isLightStatusBar();
                } else {
                    z4 = ColorUtils.calculateLuminance(Theme.getColor(Theme.key_actionBarDefault, null, true)) > 0.699999988079071d;
                }
                AndroidUtilities.setLightStatusBar(getWindow(), z4, z5);
            }
            if (i >= 26 && z3 && (!z || baseFragment == null || !baseFragment.isInPreviewMode())) {
                Window window = getWindow();
                int color = (baseFragment == null || !z) ? Theme.getColor(Theme.key_windowBackgroundGray, null, true) : baseFragment.getNavigationBarColor();
                if (window.getNavigationBarColor() != color) {
                    window.setNavigationBarColor(color);
                    AndroidUtilities.setLightNavigationBar(getWindow(), AndroidUtilities.computePerceivedBrightness(color) >= 0.721f);
                }
            }
        }
        if (i < 21 || !z2) {
            return;
        }
        getWindow().setStatusBarColor(0);
    }

    public FrameLayout getMainContainerFrameLayout() {
        return this.frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ DialogsActivity lambda$switchToAccount$8(Void r1) {
        return new DialogsActivity(null);
    }

    public void switchToAccount(int i, boolean z) {
        switchToAccount(i, z, new GenericProvider() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda82
            @Override // org.telegram.messenger.GenericProvider
            public final Object provide(Object obj) {
                DialogsActivity lambda$switchToAccount$8;
                lambda$switchToAccount$8 = LaunchActivity.lambda$switchToAccount$8((Void) obj);
                return lambda$switchToAccount$8;
            }
        });
    }

    public void switchToAccount(int i, boolean z, GenericProvider<Void, DialogsActivity> genericProvider) {
        if (i == UserConfig.selectedAccount || !UserConfig.isValidAccount(i)) {
            return;
        }
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(true, false);
        UserConfig.selectedAccount = i;
        UserConfig.getInstance(0).saveConfig(false);
        checkCurrentAccount();
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.removeAllFragments();
            this.rightActionBarLayout.removeAllFragments();
            if (!this.tabletFullSize) {
                this.shadowTabletSide.setVisibility(0);
                if (this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                    this.backgroundTablet.setVisibility(0);
                }
                this.rightActionBarLayout.getView().setVisibility(8);
            }
            this.layersActionBarLayout.getView().setVisibility(8);
        }
        if (z) {
            this.actionBarLayout.removeAllFragments();
        } else {
            this.actionBarLayout.removeFragmentFromStack(0);
        }
        DialogsActivity provide = genericProvider.provide(null);
        provide.setSideMenu(this.sideMenu);
        this.actionBarLayout.addFragmentToStack(provide, 0);
        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
        this.actionBarLayout.rebuildFragments(1);
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.rebuildFragments(1);
            this.rightActionBarLayout.rebuildFragments(1);
        }
        if (!ApplicationLoader.mainInterfacePaused) {
            ConnectionsManager.getInstance(this.currentAccount).setAppPaused(false, false);
        }
        if (UserConfig.getInstance(i).unacceptedTermsOfService != null) {
            showTosActivity(i, UserConfig.getInstance(i).unacceptedTermsOfService);
        }
        updateCurrentConnectionState(this.currentAccount);
    }

    private void switchToAvailableAccountOrLogout() {
        int i = 0;
        while (true) {
            if (i >= 1) {
                i = -1;
                break;
            } else if (UserConfig.getInstance(i).isClientActivated()) {
                break;
            } else {
                i++;
            }
        }
        TermsOfServiceView termsOfServiceView = this.termsOfServiceView;
        if (termsOfServiceView != null) {
            termsOfServiceView.setVisibility(8);
        }
        if (i != -1) {
            switchToAccount(i, true);
            return;
        }
        DrawerLayoutAdapter drawerLayoutAdapter = this.drawerLayoutAdapter;
        if (drawerLayoutAdapter != null) {
            drawerLayoutAdapter.notifyDataSetChanged();
        }
        RestrictedLanguagesSelectActivity.checkRestrictedLanguages(true);
        clearFragments();
        this.actionBarLayout.rebuildLogout();
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.rebuildLogout();
            this.rightActionBarLayout.rebuildLogout();
        }
        lambda$runLinkRequest$77(new LoginActivity());
    }

    public static void clearFragments() {
        Iterator<BaseFragment> it = mainFragmentsStack.iterator();
        while (it.hasNext()) {
            it.next().onFragmentDestroy();
        }
        mainFragmentsStack.clear();
        if (AndroidUtilities.isTablet()) {
            Iterator<BaseFragment> it2 = layerFragmentsStack.iterator();
            while (it2.hasNext()) {
                it2.next().onFragmentDestroy();
            }
            layerFragmentsStack.clear();
            Iterator<BaseFragment> it3 = rightFragmentsStack.iterator();
            while (it3.hasNext()) {
                it3.next().onFragmentDestroy();
            }
            rightFragmentsStack.clear();
        }
    }

    public int getMainFragmentsCount() {
        return mainFragmentsStack.size();
    }

    private void checkCurrentAccount() {
        int i = this.currentAccount;
        if (i != UserConfig.selectedAccount) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mainUserInfoChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openArticle);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.hasNewContactsToImport);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowPlayServicesAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadFailed);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.historyImportProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupCallUpdated);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersImportComplete);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.newSuggestionsAvailable);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.chatSwithcedToForum);
        }
        int i2 = UserConfig.selectedAccount;
        this.currentAccount = i2;
        NotificationCenter.getInstance(i2).addObserver(this, NotificationCenter.appDidLogout);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.mainUserInfoChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.didUpdateConnectionState);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.openArticle);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.hasNewContactsToImport);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.needShowPlayServicesAlert);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileLoadProgressChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.fileLoadFailed);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.historyImportProgressChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupCallUpdated);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.stickersImportComplete);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.newSuggestionsAvailable);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserShowLimitReachedDialog);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.chatSwithcedToForum);
    }

    private void checkLayout() {
        if (!AndroidUtilities.isTablet() || this.rightActionBarLayout == null) {
            return;
        }
        if (AndroidUtilities.getWasTablet() == null || AndroidUtilities.getWasTablet().booleanValue() == AndroidUtilities.isTabletForce()) {
            if (!AndroidUtilities.isInMultiwindow && (!AndroidUtilities.isSmallTablet() || getResources().getConfiguration().orientation == 2)) {
                this.tabletFullSize = false;
                List<BaseFragment> fragmentStack = this.actionBarLayout.getFragmentStack();
                if (fragmentStack.size() >= 2) {
                    while (1 < fragmentStack.size()) {
                        BaseFragment baseFragment = fragmentStack.get(1);
                        if (baseFragment instanceof ChatActivity) {
                            ((ChatActivity) baseFragment).setIgnoreAttachOnPause(true);
                        }
                        baseFragment.onPause();
                        baseFragment.onFragmentDestroy();
                        baseFragment.setParentLayout(null);
                        fragmentStack.remove(baseFragment);
                        this.rightActionBarLayout.addFragmentToStack(baseFragment);
                    }
                    PasscodeView passcodeView = this.passcodeView;
                    if (passcodeView == null || passcodeView.getVisibility() != 0) {
                        this.actionBarLayout.rebuildFragments(1);
                        this.rightActionBarLayout.rebuildFragments(1);
                    }
                }
                this.rightActionBarLayout.getView().setVisibility(this.rightActionBarLayout.getFragmentStack().isEmpty() ? 8 : 0);
                this.backgroundTablet.setVisibility(this.rightActionBarLayout.getFragmentStack().isEmpty() ? 0 : 8);
                this.shadowTabletSide.setVisibility(this.actionBarLayout.getFragmentStack().isEmpty() ? 8 : 0);
                return;
            }
            this.tabletFullSize = true;
            List<BaseFragment> fragmentStack2 = this.rightActionBarLayout.getFragmentStack();
            if (!fragmentStack2.isEmpty()) {
                while (fragmentStack2.size() > 0) {
                    BaseFragment baseFragment2 = fragmentStack2.get(0);
                    if (baseFragment2 instanceof ChatActivity) {
                        ((ChatActivity) baseFragment2).setIgnoreAttachOnPause(true);
                    }
                    baseFragment2.onPause();
                    baseFragment2.onFragmentDestroy();
                    baseFragment2.setParentLayout(null);
                    fragmentStack2.remove(baseFragment2);
                    this.actionBarLayout.addFragmentToStack(baseFragment2);
                }
                PasscodeView passcodeView2 = this.passcodeView;
                if (passcodeView2 == null || passcodeView2.getVisibility() != 0) {
                    this.actionBarLayout.rebuildFragments(1);
                }
            }
            this.shadowTabletSide.setVisibility(8);
            this.rightActionBarLayout.getView().setVisibility(8);
            this.backgroundTablet.setVisibility(this.actionBarLayout.getFragmentStack().isEmpty() ? 0 : 8);
        }
    }

    private void showUpdateActivity(int i, TLRPC$TL_help_appUpdate tLRPC$TL_help_appUpdate, boolean z) {
        if (this.blockingUpdateView == null) {
            BlockingUpdateView blockingUpdateView = new BlockingUpdateView(this) { // from class: org.telegram.ui.LaunchActivity.17
                @Override // org.telegram.ui.Components.BlockingUpdateView, android.view.View
                public void setVisibility(int i2) {
                    super.setVisibility(i2);
                    if (i2 == 8) {
                        LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    }
                }
            };
            this.blockingUpdateView = blockingUpdateView;
            this.drawerLayoutContainer.addView(blockingUpdateView, LayoutHelper.createFrame(-1, -1.0f));
        }
        this.blockingUpdateView.show(i, tLRPC$TL_help_appUpdate, z);
        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
    }

    private void showTosActivity(int i, TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService) {
        if (this.termsOfServiceView == null) {
            TermsOfServiceView termsOfServiceView = new TermsOfServiceView(this);
            this.termsOfServiceView = termsOfServiceView;
            termsOfServiceView.setAlpha(0.0f);
            this.drawerLayoutContainer.addView(this.termsOfServiceView, LayoutHelper.createFrame(-1, -1.0f));
            this.termsOfServiceView.setDelegate(new AnonymousClass18());
        }
        TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService2 = UserConfig.getInstance(i).unacceptedTermsOfService;
        if (tLRPC$TL_help_termsOfService2 != tLRPC$TL_help_termsOfService && (tLRPC$TL_help_termsOfService2 == null || !tLRPC$TL_help_termsOfService2.id.data.equals(tLRPC$TL_help_termsOfService.id.data))) {
            UserConfig.getInstance(i).unacceptedTermsOfService = tLRPC$TL_help_termsOfService;
            UserConfig.getInstance(i).saveConfig(false);
        }
        this.termsOfServiceView.show(i, tLRPC$TL_help_termsOfService);
        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
        this.termsOfServiceView.animate().alpha(1.0f).setDuration(150L).setInterpolator(AndroidUtilities.decelerateInterpolator).setListener(null).start();
    }

    /* renamed from: org.telegram.ui.LaunchActivity$18, reason: invalid class name */
    class AnonymousClass18 implements TermsOfServiceView.TermsOfServiceViewDelegate {
        AnonymousClass18() {
        }

        @Override // org.telegram.ui.Components.TermsOfServiceView.TermsOfServiceViewDelegate
        public void onAcceptTerms(int i) {
            UserConfig.getInstance(i).unacceptedTermsOfService = null;
            UserConfig.getInstance(i).saveConfig(false);
            LaunchActivity.this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
            if (LaunchActivity.mainFragmentsStack.size() > 0) {
                ((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1)).onResume();
            }
            LaunchActivity.this.termsOfServiceView.animate().alpha(0.0f).setDuration(150L).setInterpolator(AndroidUtilities.accelerateInterpolator).withEndAction(new Runnable() { // from class: org.telegram.ui.LaunchActivity$18$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.AnonymousClass18.this.lambda$onAcceptTerms$0();
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAcceptTerms$0() {
            LaunchActivity.this.termsOfServiceView.setVisibility(8);
        }
    }

    public void showPasscodeActivity(boolean z, boolean z2, int i, int i2, final Runnable runnable, Runnable runnable2) {
        if (this.drawerLayoutContainer == null) {
            return;
        }
        if (this.passcodeView == null) {
            PasscodeView passcodeView = new PasscodeView(this);
            this.passcodeView = passcodeView;
            this.drawerLayoutContainer.addView(passcodeView, LayoutHelper.createFrame(-1, -1.0f));
        }
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = this.selectAnimatedEmojiDialog;
        if (selectAnimatedEmojiDialogWindow != null) {
            selectAnimatedEmojiDialogWindow.dismiss();
            this.selectAnimatedEmojiDialog = null;
        }
        SharedConfig.appLocked = true;
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(false, false);
        } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(false, true);
        } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
        if (playingMessageObject != null && playingMessageObject.isRoundVideo()) {
            MediaController.getInstance().cleanupPlayer(true, true);
        }
        this.passcodeView.onShow(this.overlayPasscodeViews.isEmpty() && z, z2, i, i2, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$showPasscodeActivity$9(runnable);
            }
        }, runnable2);
        int i3 = 0;
        while (i3 < this.overlayPasscodeViews.size()) {
            this.overlayPasscodeViews.get(i3).onShow(z && i3 == this.overlayPasscodeViews.size() - 1, z2, i, i2, null, null);
            i3++;
        }
        SharedConfig.isWaitingForPasscodeEnter = true;
        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
        PasscodeView.PasscodeViewDelegate passcodeViewDelegate = new PasscodeView.PasscodeViewDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda115
            @Override // org.telegram.ui.Components.PasscodeView.PasscodeViewDelegate
            public final void didAcceptedPassword(PasscodeView passcodeView2) {
                LaunchActivity.this.lambda$showPasscodeActivity$10(passcodeView2);
            }
        };
        this.passcodeView.setDelegate(passcodeViewDelegate);
        Iterator<PasscodeView> it = this.overlayPasscodeViews.iterator();
        while (it.hasNext()) {
            it.next().setDelegate(passcodeViewDelegate);
        }
        try {
            NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasscodeActivity$9(Runnable runnable) {
        this.actionBarLayout.getView().setVisibility(4);
        if (AndroidUtilities.isTablet()) {
            INavigationLayout iNavigationLayout = this.layersActionBarLayout;
            if (iNavigationLayout != null && iNavigationLayout.getView() != null && this.layersActionBarLayout.getView().getVisibility() == 0) {
                this.layersActionBarLayout.getView().setVisibility(4);
            }
            INavigationLayout iNavigationLayout2 = this.rightActionBarLayout;
            if (iNavigationLayout2 != null && iNavigationLayout2.getView() != null) {
                this.rightActionBarLayout.getView().setVisibility(4);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPasscodeActivity$10(PasscodeView passcodeView) {
        SharedConfig.isWaitingForPasscodeEnter = false;
        Intent intent = this.passcodeSaveIntent;
        if (intent != null) {
            handleIntent(intent, this.passcodeSaveIntentIsNew, this.passcodeSaveIntentIsRestore, true);
            this.passcodeSaveIntent = null;
        }
        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
        this.actionBarLayout.getView().setVisibility(0);
        this.actionBarLayout.rebuildFragments(1);
        if (AndroidUtilities.isTablet()) {
            this.layersActionBarLayout.rebuildFragments(1);
            this.rightActionBarLayout.rebuildFragments(1);
            if (this.layersActionBarLayout.getView().getVisibility() == 4) {
                this.layersActionBarLayout.getView().setVisibility(0);
            }
            this.rightActionBarLayout.getView().setVisibility(0);
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.passcodeDismissed, passcodeView);
        try {
            NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public boolean allowShowFingerprintDialog(PasscodeView passcodeView) {
        if (!this.overlayPasscodeViews.isEmpty()) {
            List<PasscodeView> list = this.overlayPasscodeViews;
            if (list.get(list.size() - 1) != passcodeView) {
                return false;
            }
        } else if (passcodeView != this.passcodeView) {
            return false;
        }
        return true;
    }

    private boolean handleIntent(Intent intent, boolean z, boolean z2, boolean z3) {
        return lambda$handleIntent$15(intent, z, z2, z3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: handleIntent, reason: merged with bridge method [inline-methods] */
    public boolean lambda$handleIntent$15(Intent intent, boolean z, boolean z2, boolean z3, Browser.Progress progress) {
        return handleIntent(intent, z, z2, z3, progress, true);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:1179:0x19f7, code lost:
    
        if (r3.longValue() == 0) goto L874;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x0342, code lost:
    
        if (r91.sendingText == null) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x014f, code lost:
    
        if (r2.equals(r0) != false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:791:0x0b89, code lost:
    
        if (r11.intValue() == 0) goto L490;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x22f9, code lost:
    
        if (r1.checkCanOpenChat(r0, r2.get(r2.size() - r3)) != false) goto L1108;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x28d0  */
    /* JADX WARN: Removed duplicated region for block: B:1032:0x1561 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1087:0x187e  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x292c  */
    /* JADX WARN: Removed duplicated region for block: B:1130:0x1964 A[Catch: Exception -> 0x1973, TRY_LEAVE, TryCatch #5 {Exception -> 0x1973, blocks: (B:1128:0x1958, B:1130:0x1964), top: B:1127:0x1958 }] */
    /* JADX WARN: Removed duplicated region for block: B:1138:0x1971  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x2934  */
    /* JADX WARN: Removed duplicated region for block: B:1227:0x1cd5  */
    /* JADX WARN: Removed duplicated region for block: B:1229:0x1d15  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x2348  */
    /* JADX WARN: Removed duplicated region for block: B:1305:0x0409  */
    /* JADX WARN: Removed duplicated region for block: B:1325:0x053b  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x2596  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x25b4  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x25c6  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x25a6  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:333:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x1de6  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x1e2e  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x1e4c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:536:0x1f22 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0349  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x2003  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x2015  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x0765  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x2292  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x085b A[Catch: Exception -> 0x0869, TRY_LEAVE, TryCatch #1 {Exception -> 0x0869, blocks: (B:654:0x084f, B:656:0x085b), top: B:653:0x084f }] */
    /* JADX WARN: Removed duplicated region for block: B:669:0x0868  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x22c4  */
    /* JADX WARN: Removed duplicated region for block: B:794:0x0b93  */
    /* JADX WARN: Removed duplicated region for block: B:797:0x0c00  */
    /* JADX WARN: Removed duplicated region for block: B:799:0x0c03  */
    /* JADX WARN: Removed duplicated region for block: B:809:0x0c4b  */
    /* JADX WARN: Removed duplicated region for block: B:811:0x0c72  */
    /* JADX WARN: Removed duplicated region for block: B:814:0x0b98  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x2878 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x2881  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x2915  */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r11v19 */
    /* JADX WARN: Type inference failed for: r11v258 */
    /* JADX WARN: Type inference failed for: r11v260 */
    /* JADX WARN: Type inference failed for: r11v261 */
    /* JADX WARN: Type inference failed for: r11v8, types: [android.content.Intent] */
    /* JADX WARN: Type inference failed for: r12v195 */
    /* JADX WARN: Type inference failed for: r12v200 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v213 */
    /* JADX WARN: Type inference failed for: r12v214 */
    /* JADX WARN: Type inference failed for: r12v9, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r18v10 */
    /* JADX WARN: Type inference failed for: r18v11 */
    /* JADX WARN: Type inference failed for: r18v13 */
    /* JADX WARN: Type inference failed for: r18v9 */
    /* JADX WARN: Type inference failed for: r1v25, types: [org.telegram.ui.ActionBar.DrawerLayoutContainer] */
    /* JADX WARN: Type inference failed for: r1v28, types: [org.telegram.ui.ActionBar.INavigationLayout] */
    /* JADX WARN: Type inference failed for: r1v30, types: [org.telegram.ui.ActionBar.INavigationLayout] */
    /* JADX WARN: Type inference failed for: r1v31, types: [org.telegram.ui.ActionBar.INavigationLayout] */
    /* JADX WARN: Type inference failed for: r1v39, types: [org.telegram.ui.ActionBar.DrawerLayoutContainer] */
    /* JADX WARN: Type inference failed for: r1v56 */
    /* JADX WARN: Type inference failed for: r2v88, types: [org.telegram.ui.ActionBar.INavigationLayout$NavigationParams] */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r41v22 */
    /* JADX WARN: Type inference failed for: r41v23 */
    /* JADX WARN: Type inference failed for: r41v28 */
    /* JADX WARN: Type inference failed for: r41v29 */
    /* JADX WARN: Type inference failed for: r41v30 */
    /* JADX WARN: Type inference failed for: r41v31 */
    /* JADX WARN: Type inference failed for: r41v32 */
    /* JADX WARN: Type inference failed for: r41v33 */
    /* JADX WARN: Type inference failed for: r41v34 */
    /* JADX WARN: Type inference failed for: r41v35 */
    /* JADX WARN: Type inference failed for: r7v10, types: [android.os.Bundle, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v19 */
    /* JADX WARN: Type inference failed for: r7v242 */
    /* JADX WARN: Type inference failed for: r7v247 */
    /* JADX WARN: Type inference failed for: r7v254 */
    @android.annotation.SuppressLint({"Range"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean handleIntent(final android.content.Intent r92, final boolean r93, final boolean r94, final boolean r95, final org.telegram.messenger.browser.Browser.Progress r96, boolean r97) {
        /*
            Method dump skipped, instructions count: 10596
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.handleIntent(android.content.Intent, boolean, boolean, boolean, org.telegram.messenger.browser.Browser$Progress, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$11(String str) {
        if (this.actionBarLayout.getFragmentStack().isEmpty()) {
            return;
        }
        this.actionBarLayout.getFragmentStack().get(0).presentFragment(new PremiumPreviewFragment(Uri.parse(str).getQueryParameter("ref")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$12(Intent intent, boolean z) {
        handleIntent(intent, true, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$14(final AlertDialog alertDialog, final String str, final Bundle bundle, final TLRPC$TL_account_sendConfirmPhoneCode tLRPC$TL_account_sendConfirmPhoneCode, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda75
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$handleIntent$13(alertDialog, tLRPC$TL_error, str, bundle, tLObject, tLRPC$TL_account_sendConfirmPhoneCode);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$13(AlertDialog alertDialog, TLRPC$TL_error tLRPC$TL_error, String str, Bundle bundle, TLObject tLObject, TLRPC$TL_account_sendConfirmPhoneCode tLRPC$TL_account_sendConfirmPhoneCode) {
        alertDialog.dismiss();
        if (tLRPC$TL_error == null) {
            lambda$runLinkRequest$77(new LoginActivity().cancelAccountDeletion(str, bundle, (TLRPC$TL_auth_sentCode) tLObject));
        } else {
            AlertsCreator.processError(this.currentAccount, tLRPC$TL_error, getActionBarLayout().getLastFragment(), tLRPC$TL_account_sendConfirmPhoneCode, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$17(final int[] iArr, LocationController.SharingLocationInfo sharingLocationInfo) {
        iArr[0] = sharingLocationInfo.messageObject.currentAccount;
        switchToAccount(iArr[0], true);
        LocationActivity locationActivity = new LocationActivity(2);
        locationActivity.setMessageObject(sharingLocationInfo.messageObject);
        final long dialogId = sharingLocationInfo.messageObject.getDialogId();
        locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda125
            @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
            public final void didSelectLocation(TLRPC$MessageMedia tLRPC$MessageMedia, int i, boolean z, int i2) {
                LaunchActivity.lambda$handleIntent$16(iArr, dialogId, tLRPC$MessageMedia, i, z, i2);
            }
        });
        lambda$runLinkRequest$77(locationActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleIntent$16(int[] iArr, long j, TLRPC$MessageMedia tLRPC$MessageMedia, int i, boolean z, int i2) {
        SendMessagesHelper.getInstance(iArr[0]).sendMessage(tLRPC$MessageMedia, j, (MessageObject) null, (MessageObject) null, (TLRPC$ReplyMarkup) null, (HashMap<String, String>) null, z, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$18() {
        if (this.actionBarLayout.getFragmentStack().isEmpty()) {
            return;
        }
        this.actionBarLayout.getFragmentStack().get(0).showDialog(new StickersAlert(this, this.importingStickersSoftware, this.importingStickers, this.importingStickersEmoji, (Theme.ResourcesProvider) null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$19(BaseFragment baseFragment, boolean z) {
        presentFragment(baseFragment, z, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$20(boolean z, int[] iArr, TLRPC$User tLRPC$User, String str, ContactsActivity contactsActivity) {
        TLRPC$UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(tLRPC$User.id);
        VoIPHelper.startCall(tLRPC$User, z, userFull != null && userFull.video_calls_available, this, userFull, AccountInstance.getInstance(iArr[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$24(final ActionIntroActivity actionIntroActivity, String str) {
        final AlertDialog alertDialog = new AlertDialog(this, 3);
        alertDialog.setCanCancel(false);
        alertDialog.show();
        byte[] decode = Base64.decode(str.substring(17), 8);
        TLRPC$TL_auth_acceptLoginToken tLRPC$TL_auth_acceptLoginToken = new TLRPC$TL_auth_acceptLoginToken();
        tLRPC$TL_auth_acceptLoginToken.token = decode;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_auth_acceptLoginToken, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda87
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LaunchActivity.lambda$handleIntent$23(AlertDialog.this, actionIntroActivity, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleIntent$23(final AlertDialog alertDialog, final ActionIntroActivity actionIntroActivity, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.lambda$handleIntent$22(AlertDialog.this, tLObject, actionIntroActivity, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleIntent$22(AlertDialog alertDialog, TLObject tLObject, final ActionIntroActivity actionIntroActivity, final TLRPC$TL_error tLRPC$TL_error) {
        try {
            alertDialog.dismiss();
        } catch (Exception unused) {
        }
        if (tLObject instanceof TLRPC$TL_authorization) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.lambda$handleIntent$21(ActionIntroActivity.this, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleIntent$21(ActionIntroActivity actionIntroActivity, TLRPC$TL_error tLRPC$TL_error) {
        AlertsCreator.showSimpleAlert(actionIntroActivity, LocaleController.getString("AuthAnotherClient", R.string.AuthAnotherClient), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleIntent$25(BaseFragment baseFragment, String str, String str2, DialogInterface dialogInterface, int i) {
        NewContactBottomSheet newContactBottomSheet = new NewContactBottomSheet(baseFragment, this);
        newContactBottomSheet.setInitialPhoneNumber(str, false);
        if (str2 != null) {
            String[] split = str2.split(" ", 2);
            newContactBottomSheet.setInitialName(split[0], split.length > 1 ? split[1] : null);
        }
        newContactBottomSheet.show();
    }

    public static int getTimestampFromLink(Uri uri) {
        String queryParameter;
        int i;
        if (uri.getPathSegments().contains(MediaStreamTrack.VIDEO_TRACK_KIND)) {
            queryParameter = uri.getQuery();
        } else {
            queryParameter = uri.getQueryParameter("t") != null ? uri.getQueryParameter("t") : null;
        }
        if (queryParameter == null) {
            return -1;
        }
        try {
            i = Integer.parseInt(queryParameter);
        } catch (Throwable unused) {
            i = -1;
        }
        if (i == -1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            try {
                return (int) ((simpleDateFormat.parse(queryParameter).getTime() - simpleDateFormat.parse("00:00").getTime()) / 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    private void openDialogsToSend(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("onlySelect", true);
        bundle.putBoolean("canSelectTopics", true);
        bundle.putInt("dialogsType", 3);
        bundle.putBoolean("allowSwitchAccount", true);
        ArrayList<TLRPC$User> arrayList = this.contactsToSend;
        if (arrayList != null) {
            if (arrayList.size() != 1) {
                bundle.putString("selectAlertString", LocaleController.getString("SendContactToText", R.string.SendMessagesToText));
                bundle.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroupText", R.string.SendContactToGroupText));
            }
        } else {
            bundle.putString("selectAlertString", LocaleController.getString("SendMessagesToText", R.string.SendMessagesToText));
            bundle.putString("selectAlertStringGroup", LocaleController.getString("SendMessagesToGroupText", R.string.SendMessagesToGroupText));
        }
        DialogsActivity dialogsActivity = new DialogsActivity(bundle) { // from class: org.telegram.ui.LaunchActivity.19
            @Override // org.telegram.ui.DialogsActivity
            public boolean shouldShowNextButton(DialogsActivity dialogsActivity2, ArrayList<Long> arrayList2, CharSequence charSequence, boolean z2) {
                if (LaunchActivity.this.exportingChatUri != null) {
                    return false;
                }
                if (LaunchActivity.this.contactsToSend != null && LaunchActivity.this.contactsToSend.size() == 1 && !LaunchActivity.mainFragmentsStack.isEmpty()) {
                    return true;
                }
                if (arrayList2.size() <= 1) {
                    return LaunchActivity.this.videoPath != null || (LaunchActivity.this.photoPathsArray != null && LaunchActivity.this.photoPathsArray.size() > 0);
                }
                return false;
            }
        };
        dialogsActivity.setDelegate(this);
        this.actionBarLayout.presentFragment(dialogsActivity, !AndroidUtilities.isTablet() ? this.actionBarLayout.getFragmentStack().size() <= 1 || !(this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity) : this.layersActionBarLayout.getFragmentStack().size() <= 0 || !(this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity), !z, true, false);
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(false, false);
        } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(false, true);
        } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(false, true);
        }
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.dismiss();
        }
        if (z) {
            return;
        }
        this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
        if (AndroidUtilities.isTablet()) {
            this.actionBarLayout.rebuildFragments(1);
            this.rightActionBarLayout.rebuildFragments(1);
        } else {
            this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
        }
    }

    private int runCommentRequest(int i, Runnable runnable, Integer num, Integer num2, Integer num3, TLRPC$Chat tLRPC$Chat) {
        return runCommentRequest(i, runnable, num, num2, num3, tLRPC$Chat, null);
    }

    private int runCommentRequest(final int i, final Runnable runnable, final Integer num, final Integer num2, final Integer num3, final TLRPC$Chat tLRPC$Chat, final Runnable runnable2) {
        if (tLRPC$Chat == null) {
            return 0;
        }
        final TLRPC$TL_messages_getDiscussionMessage tLRPC$TL_messages_getDiscussionMessage = new TLRPC$TL_messages_getDiscussionMessage();
        tLRPC$TL_messages_getDiscussionMessage.peer = MessagesController.getInputPeer(tLRPC$Chat);
        tLRPC$TL_messages_getDiscussionMessage.msg_id = (num2 != null ? num : num3).intValue();
        return ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_messages_getDiscussionMessage, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda93
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LaunchActivity.this.lambda$runCommentRequest$29(i, tLRPC$Chat, num3, num, tLRPC$TL_messages_getDiscussionMessage, num2, runnable2, runnable, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runCommentRequest$29(final int i, final TLRPC$Chat tLRPC$Chat, final Integer num, final Integer num2, final TLRPC$TL_messages_getDiscussionMessage tLRPC$TL_messages_getDiscussionMessage, final Integer num3, final Runnable runnable, final Runnable runnable2, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runCommentRequest$28(tLObject, i, tLRPC$Chat, num, num2, tLRPC$TL_messages_getDiscussionMessage, num3, runnable, runnable2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runCommentRequest$28(TLObject tLObject, int i, final TLRPC$Chat tLRPC$Chat, final Integer num, final Integer num2, final TLRPC$TL_messages_getDiscussionMessage tLRPC$TL_messages_getDiscussionMessage, final Integer num3, final Runnable runnable, Runnable runnable2) {
        boolean z = false;
        if (tLObject instanceof TLRPC$TL_messages_discussionMessage) {
            TLRPC$TL_messages_discussionMessage tLRPC$TL_messages_discussionMessage = (TLRPC$TL_messages_discussionMessage) tLObject;
            MessagesController.getInstance(i).putUsers(tLRPC$TL_messages_discussionMessage.users, false);
            MessagesController.getInstance(i).putChats(tLRPC$TL_messages_discussionMessage.chats, false);
            final ArrayList<MessageObject> arrayList = new ArrayList<>();
            int size = tLRPC$TL_messages_discussionMessage.messages.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(new MessageObject(UserConfig.selectedAccount, tLRPC$TL_messages_discussionMessage.messages.get(i2), true, true));
            }
            if (!arrayList.isEmpty() || (tLRPC$Chat.forum && num != null && num.intValue() == 1)) {
                if (tLRPC$Chat.forum) {
                    TLRPC$TL_channels_getForumTopicsByID tLRPC$TL_channels_getForumTopicsByID = new TLRPC$TL_channels_getForumTopicsByID();
                    tLRPC$TL_channels_getForumTopicsByID.channel = MessagesController.getInstance(this.currentAccount).getInputChannel(tLRPC$Chat.id);
                    tLRPC$TL_channels_getForumTopicsByID.topics.add(num);
                    ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_channels_getForumTopicsByID, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda104
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject2, TLRPC$TL_error tLRPC$TL_error) {
                            LaunchActivity.this.lambda$runCommentRequest$27(tLRPC$Chat, num, num2, arrayList, tLRPC$TL_messages_getDiscussionMessage, num3, runnable, tLObject2, tLRPC$TL_error);
                        }
                    });
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putLong("chat_id", -arrayList.get(0).getDialogId());
                    bundle.putInt("message_id", Math.max(1, num2.intValue()));
                    ChatActivity chatActivity = new ChatActivity(bundle);
                    chatActivity.setThreadMessages(arrayList, tLRPC$Chat, tLRPC$TL_messages_getDiscussionMessage.msg_id, tLRPC$TL_messages_discussionMessage.read_inbox_max_id, tLRPC$TL_messages_discussionMessage.read_outbox_max_id, null);
                    if (num3 != null) {
                        chatActivity.setHighlightMessageId(num3.intValue());
                    } else if (num != null) {
                        chatActivity.setHighlightMessageId(num2.intValue());
                    }
                    lambda$runLinkRequest$77(chatActivity);
                }
                z = true;
            }
        }
        if (!z) {
            try {
                if (!mainFragmentsStack.isEmpty()) {
                    ArrayList<BaseFragment> arrayList2 = mainFragmentsStack;
                    BulletinFactory.of(arrayList2.get(arrayList2.size() - 1)).createErrorBulletin(LocaleController.getString("ChannelPostDeleted", R.string.ChannelPostDeleted)).show();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        if (runnable2 != null) {
            try {
                runnable2.run();
            } catch (Exception e2) {
                FileLog.e(e2);
                return;
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runCommentRequest$27(final TLRPC$Chat tLRPC$Chat, final Integer num, final Integer num2, final ArrayList arrayList, final TLRPC$TL_messages_getDiscussionMessage tLRPC$TL_messages_getDiscussionMessage, final Integer num3, final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runCommentRequest$26(tLRPC$TL_error, tLObject, tLRPC$Chat, num, num2, arrayList, tLRPC$TL_messages_getDiscussionMessage, num3, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runCommentRequest$26(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, TLRPC$Chat tLRPC$Chat, Integer num, Integer num2, ArrayList arrayList, TLRPC$TL_messages_getDiscussionMessage tLRPC$TL_messages_getDiscussionMessage, Integer num3, Runnable runnable) {
        if (tLRPC$TL_error == null) {
            TLRPC$TL_messages_forumTopics tLRPC$TL_messages_forumTopics = (TLRPC$TL_messages_forumTopics) tLObject;
            SparseArray<TLRPC$Message> sparseArray = new SparseArray<>();
            for (int i = 0; i < tLRPC$TL_messages_forumTopics.messages.size(); i++) {
                sparseArray.put(tLRPC$TL_messages_forumTopics.messages.get(i).id, tLRPC$TL_messages_forumTopics.messages.get(i));
            }
            MessagesController.getInstance(this.currentAccount).putUsers(tLRPC$TL_messages_forumTopics.users, false);
            MessagesController.getInstance(this.currentAccount).putChats(tLRPC$TL_messages_forumTopics.chats, false);
            MessagesController.getInstance(this.currentAccount).getTopicsController().processTopics(tLRPC$Chat.id, tLRPC$TL_messages_forumTopics.topics, sparseArray, false, 2, -1);
        }
        TLRPC$TL_forumTopic findTopic = MessagesController.getInstance(this.currentAccount).getTopicsController().findTopic(tLRPC$Chat.id, num.intValue());
        if (findTopic != null) {
            Bundle bundle = new Bundle();
            bundle.putLong("chat_id", tLRPC$Chat.id);
            if (num2.intValue() != findTopic.id) {
                bundle.putInt("message_id", Math.max(1, num2.intValue()));
            }
            ChatActivity chatActivity = new ChatActivity(bundle);
            if (arrayList.isEmpty()) {
                TLRPC$Message tLRPC$Message = new TLRPC$Message();
                tLRPC$Message.id = 1;
                tLRPC$Message.action = new TLRPC$TL_messageActionChannelMigrateFrom();
                arrayList.add(new MessageObject(this.currentAccount, tLRPC$Message, false, false));
            }
            chatActivity.setThreadMessages(arrayList, tLRPC$Chat, tLRPC$TL_messages_getDiscussionMessage.msg_id, findTopic.read_inbox_max_id, findTopic.read_outbox_max_id, findTopic);
            if (num3 != null) {
                chatActivity.setHighlightMessageId(num3.intValue());
            } else if (num2.intValue() != findTopic.id) {
                chatActivity.setHighlightMessageId(num2.intValue());
            }
            lambda$runLinkRequest$77(chatActivity);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v4 */
    private void runImportRequest(final Uri uri, ArrayList<Uri> arrayList) {
        final int i = UserConfig.selectedAccount;
        final AlertDialog alertDialog = new AlertDialog(this, 3);
        final int[] iArr = {0};
        final ?? r3 = 0;
        InputStream inputStream = null;
        try {
            try {
                InputStream openInputStream = getContentResolver().openInputStream(uri);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openInputStream));
                    StringBuilder sb = new StringBuilder();
                    int i2 = 0;
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null || i2 >= 100) {
                            break;
                        }
                        sb.append(readLine);
                        sb.append('\n');
                        i2++;
                    }
                    String sb2 = sb.toString();
                    if (openInputStream != null) {
                        try {
                            openInputStream.close();
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                    TLRPC$TL_messages_checkHistoryImport tLRPC$TL_messages_checkHistoryImport = new TLRPC$TL_messages_checkHistoryImport();
                    tLRPC$TL_messages_checkHistoryImport.import_head = sb2;
                    iArr[0] = ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_messages_checkHistoryImport, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda96
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            LaunchActivity.this.lambda$runImportRequest$31(uri, i, alertDialog, tLObject, tLRPC$TL_error);
                        }
                    });
                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda2
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            LaunchActivity.lambda$runImportRequest$32(i, iArr, r3, dialogInterface);
                        }
                    });
                    try {
                        alertDialog.showDelayed(300L);
                    } catch (Exception unused) {
                    }
                } catch (Exception e2) {
                    e = e2;
                    r3 = openInputStream;
                    FileLog.e(e);
                    if (r3 != 0) {
                        try {
                            r3.close();
                        } catch (Exception e3) {
                            FileLog.e(e3);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    inputStream = openInputStream;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e4) {
                            FileLog.e(e4);
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runImportRequest$31(final Uri uri, final int i, final AlertDialog alertDialog, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda66
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runImportRequest$30(tLObject, uri, i, alertDialog);
            }
        }, 2L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runImportRequest$30(TLObject tLObject, Uri uri, int i, AlertDialog alertDialog) {
        boolean z;
        if (isFinishing()) {
            return;
        }
        boolean z2 = false;
        if (tLObject != null && this.actionBarLayout != null) {
            TLRPC$TL_messages_historyImportParsed tLRPC$TL_messages_historyImportParsed = (TLRPC$TL_messages_historyImportParsed) tLObject;
            Bundle bundle = new Bundle();
            bundle.putBoolean("onlySelect", true);
            bundle.putString("importTitle", tLRPC$TL_messages_historyImportParsed.title);
            bundle.putBoolean("allowSwitchAccount", true);
            if (tLRPC$TL_messages_historyImportParsed.pm) {
                bundle.putInt("dialogsType", 12);
            } else if (tLRPC$TL_messages_historyImportParsed.group) {
                bundle.putInt("dialogsType", 11);
            } else {
                String uri2 = uri.toString();
                Iterator<String> it = MessagesController.getInstance(i).exportPrivateUri.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (uri2.contains(it.next())) {
                        bundle.putInt("dialogsType", 12);
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    Iterator<String> it2 = MessagesController.getInstance(i).exportGroupUri.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (uri2.contains(it2.next())) {
                            bundle.putInt("dialogsType", 11);
                            z = true;
                            break;
                        }
                    }
                    if (!z) {
                        bundle.putInt("dialogsType", 13);
                    }
                }
            }
            if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
                SecretMediaViewer.getInstance().closePhoto(false, false);
            } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                PhotoViewer.getInstance().closePhoto(false, true);
            } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                ArticleViewer.getInstance().close(false, true);
            }
            GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
            if (groupCallActivity != null) {
                groupCallActivity.dismiss();
            }
            this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
            if (AndroidUtilities.isTablet()) {
                this.actionBarLayout.rebuildFragments(1);
                this.rightActionBarLayout.rebuildFragments(1);
            } else {
                this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
            }
            DialogsActivity dialogsActivity = new DialogsActivity(bundle);
            dialogsActivity.setDelegate(this);
            if (!AndroidUtilities.isTablet() ? !(this.actionBarLayout.getFragmentStack().size() <= 1 || !(this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity)) : !(this.layersActionBarLayout.getFragmentStack().size() <= 0 || !(this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity))) {
                z2 = true;
            }
            this.actionBarLayout.presentFragment(dialogsActivity, z2, false, true, false);
        } else {
            if (this.documentsUrisArray == null) {
                this.documentsUrisArray = new ArrayList<>();
            }
            this.documentsUrisArray.add(0, this.exportingChatUri);
            this.exportingChatUri = null;
            openDialogsToSend(true);
        }
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runImportRequest$32(int i, int[] iArr, Runnable runnable, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void runLinkRequest(final int r45, final java.lang.String r46, final java.lang.String r47, final java.lang.String r48, final java.lang.String r49, final java.lang.String r50, final java.lang.String r51, final java.lang.String r52, final java.lang.String r53, final java.lang.String r54, final java.lang.String r55, final java.lang.String r56, final boolean r57, final java.lang.Integer r58, final java.lang.Long r59, final java.lang.Integer r60, final java.lang.Integer r61, final java.lang.String r62, final java.util.HashMap<java.lang.String, java.lang.String> r63, final java.lang.String r64, final java.lang.String r65, final java.lang.String r66, final java.lang.String r67, final org.telegram.tgnet.TLRPC$TL_wallPaper r68, final java.lang.String r69, final java.lang.String r70, final java.lang.String r71, final java.lang.String r72, final int r73, final int r74, final java.lang.String r75, final java.lang.String r76, final java.lang.String r77, final java.lang.String r78, final java.lang.String r79, final org.telegram.messenger.browser.Browser.Progress r80, final boolean r81) {
        /*
            Method dump skipped, instructions count: 1425
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.runLinkRequest(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.Integer, java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String, java.util.HashMap, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.telegram.tgnet.TLRPC$TL_wallPaper, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.telegram.messenger.browser.Browser$Progress, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$35(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, boolean z, Integer num, Long l, Integer num2, Integer num3, String str12, HashMap hashMap, String str13, String str14, String str15, String str16, TLRPC$TL_wallPaper tLRPC$TL_wallPaper, String str17, String str18, String str19, String str20, int i2, String str21, String str22, String str23, String str24, String str25, Browser.Progress progress, boolean z2, int i3) {
        if (i3 != i) {
            switchToAccount(i3, true);
        }
        runLinkRequest(i3, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, z, num, l, num2, num3, str12, hashMap, str13, str14, str15, str16, tLRPC$TL_wallPaper, str17, str18, str19, str20, 1, i2, str21, str22, str23, str24, str25, progress, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$36(Browser.Progress progress, AlertDialog alertDialog) {
        if (progress != null) {
            progress.end();
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$38(final int i, final String str, final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda62
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$37(tLObject, i, str, tLRPC$TL_error, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$37(TLObject tLObject, int i, String str, TLRPC$TL_error tLRPC$TL_error, Runnable runnable) {
        if (tLObject instanceof TLRPC$User) {
            TLRPC$User tLRPC$User = (TLRPC$User) tLObject;
            MessagesController.getInstance(i).putUser(tLRPC$User, false);
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", tLRPC$User.id);
            lambda$runLinkRequest$77(new ChatActivity(bundle));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("cant import contact token. token=");
            sb.append(str);
            sb.append(" err=");
            sb.append(tLRPC$TL_error == null ? null : tLRPC$TL_error.text);
            FileLog.e(sb.toString());
            BulletinFactory.of(mainFragmentsStack.get(r2.size() - 1)).createErrorBulletin(LocaleController.getString(R.string.NoUsernameFound)).show();
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$40(final int i, final String str, final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.lambda$runLinkRequest$39(TLObject.this, i, str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$runLinkRequest$39(org.telegram.tgnet.TLObject r7, int r8, java.lang.String r9, java.lang.Runnable r10) {
        /*
            java.util.ArrayList<org.telegram.ui.ActionBar.BaseFragment> r0 = org.telegram.ui.LaunchActivity.mainFragmentsStack
            int r1 = r0.size()
            int r1 = r1 + (-1)
            java.lang.Object r0 = r0.get(r1)
            org.telegram.ui.ActionBar.BaseFragment r0 = (org.telegram.ui.ActionBar.BaseFragment) r0
            boolean r1 = r7 instanceof org.telegram.tgnet.TLRPC$chatlist_ChatlistInvite
            if (r1 == 0) goto L6f
            org.telegram.tgnet.TLRPC$chatlist_ChatlistInvite r7 = (org.telegram.tgnet.TLRPC$chatlist_ChatlistInvite) r7
            boolean r1 = r7 instanceof org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInvite
            r2 = 0
            if (r1 == 0) goto L24
            r2 = r7
            org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInvite r2 = (org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInvite) r2
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r3 = r2.chats
            java.util.ArrayList<org.telegram.tgnet.TLRPC$User> r2 = r2.users
        L20:
            r6 = r3
            r3 = r2
            r2 = r6
            goto L31
        L24:
            boolean r3 = r7 instanceof org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInviteAlready
            if (r3 == 0) goto L30
            r2 = r7
            org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInviteAlready r2 = (org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInviteAlready) r2
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r3 = r2.chats
            java.util.ArrayList<org.telegram.tgnet.TLRPC$User> r2 = r2.users
            goto L20
        L30:
            r3 = r2
        L31:
            org.telegram.messenger.MessagesController r4 = org.telegram.messenger.MessagesController.getInstance(r8)
            r5 = 0
            r4.putChats(r2, r5)
            org.telegram.messenger.MessagesController r8 = org.telegram.messenger.MessagesController.getInstance(r8)
            r8.putUsers(r3, r5)
            if (r1 == 0) goto L60
            r8 = r7
            org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInvite r8 = (org.telegram.tgnet.TLRPC$TL_chatlists_chatlistInvite) r8
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Peer> r8 = r8.peers
            boolean r8 = r8.isEmpty()
            if (r8 != 0) goto L4e
            goto L60
        L4e:
            org.telegram.ui.Components.BulletinFactory r7 = org.telegram.ui.Components.BulletinFactory.of(r0)
            int r8 = org.telegram.messenger.R.string.NoFolderFound
            java.lang.String r8 = org.telegram.messenger.LocaleController.getString(r8)
            org.telegram.ui.Components.Bulletin r7 = r7.createErrorBulletin(r8)
            r7.show()
            goto L80
        L60:
            org.telegram.ui.Components.FolderBottomSheet r8 = new org.telegram.ui.Components.FolderBottomSheet
            r8.<init>(r0, r9, r7)
            if (r0 == 0) goto L6b
            r0.showDialog(r8)
            goto L80
        L6b:
            r8.show()
            goto L80
        L6f:
            org.telegram.ui.Components.BulletinFactory r7 = org.telegram.ui.Components.BulletinFactory.of(r0)
            int r8 = org.telegram.messenger.R.string.NoFolderFound
            java.lang.String r8 = org.telegram.messenger.LocaleController.getString(r8)
            org.telegram.ui.Components.Bulletin r7 = r7.createErrorBulletin(r8)
            r7.show()
        L80:
            r10.run()     // Catch: java.lang.Exception -> L84
            goto L88
        L84:
            r7 = move-exception
            org.telegram.messenger.FileLog.e(r7)
        L88:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.lambda$runLinkRequest$39(org.telegram.tgnet.TLObject, int, java.lang.String, java.lang.Runnable):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$43(final int i, final String str, final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$42(tLRPC$TL_error, tLObject, i, str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$42(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, int i, String str, Runnable runnable) {
        PaymentFormActivity paymentFormActivity;
        if (tLRPC$TL_error != null) {
            BulletinFactory.of(mainFragmentsStack.get(r3.size() - 1)).createErrorBulletin(LocaleController.getString(R.string.PaymentInvoiceLinkInvalid)).show();
        } else if (!isFinishing()) {
            if (tLObject instanceof TLRPC$TL_payments_paymentForm) {
                TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm = (TLRPC$TL_payments_paymentForm) tLObject;
                MessagesController.getInstance(i).putUsers(tLRPC$TL_payments_paymentForm.users, false);
                paymentFormActivity = new PaymentFormActivity(tLRPC$TL_payments_paymentForm, str, getActionBarLayout().getLastFragment());
            } else {
                paymentFormActivity = tLObject instanceof TLRPC$TL_payments_paymentReceipt ? new PaymentFormActivity((TLRPC$TL_payments_paymentReceipt) tLObject) : null;
            }
            if (paymentFormActivity != null) {
                final Runnable runnable2 = this.navigateToPremiumGiftCallback;
                if (runnable2 != null) {
                    this.navigateToPremiumGiftCallback = null;
                    paymentFormActivity.setPaymentFormCallback(new PaymentFormActivity.PaymentFormCallback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda126
                        @Override // org.telegram.ui.PaymentFormActivity.PaymentFormCallback
                        public final void onInvoiceStatusChanged(PaymentFormActivity.InvoiceStatus invoiceStatus) {
                            LaunchActivity.lambda$runLinkRequest$41(runnable2, invoiceStatus);
                        }
                    });
                }
                lambda$runLinkRequest$77(paymentFormActivity);
            }
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$41(Runnable runnable, PaymentFormActivity.InvoiceStatus invoiceStatus) {
        if (invoiceStatus == PaymentFormActivity.InvoiceStatus.PAID) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$62(final String str, final String str2, final String str3, String str4, final int i, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final String str12, final String str13, final String str14, final String str15, final boolean z, final Integer num, final Long l, final Integer num2, final Integer num3, final HashMap hashMap, final String str16, final String str17, final String str18, final String str19, final TLRPC$TL_wallPaper tLRPC$TL_wallPaper, final String str20, final String str21, final int i2, final int i3, final String str22, final String str23, final String str24, final Browser.Progress progress, final boolean z2, final Runnable runnable, final String str25, int[] iArr, final Long l2) {
        long longValue;
        boolean z3;
        BaseFragment baseFragment;
        final TLRPC$User user;
        if (isFinishing()) {
            return;
        }
        boolean z4 = true;
        if (l2 != null && this.actionBarLayout != null && ((str == null && str2 == null) || ((str != null && l2.longValue() > 0) || ((str2 != null && l2.longValue() > 0) || (str3 != null && l2.longValue() < 0))))) {
            if (!TextUtils.isEmpty(str4) && (user = MessagesController.getInstance(i).getUser(l2)) != null && user.bot) {
                TLRPC$TL_messages_getBotApp tLRPC$TL_messages_getBotApp = new TLRPC$TL_messages_getBotApp();
                TLRPC$TL_inputBotAppShortName tLRPC$TL_inputBotAppShortName = new TLRPC$TL_inputBotAppShortName();
                tLRPC$TL_inputBotAppShortName.bot_id = MessagesController.getInstance(this.currentAccount).getInputUser(user);
                tLRPC$TL_inputBotAppShortName.short_name = str4;
                tLRPC$TL_messages_getBotApp.app = tLRPC$TL_inputBotAppShortName;
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_messages_getBotApp, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda103
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LaunchActivity.this.lambda$runLinkRequest$47(str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, z, num, l, num2, num3, str, hashMap, str16, str17, str18, str19, tLRPC$TL_wallPaper, str20, str21, str2, str3, i2, i3, str22, str23, str24, progress, z2, runnable, user, str25, tLObject, tLRPC$TL_error);
                    }
                });
                return;
            }
            if (str22 == null || str23 != null) {
                if (num != null && ((num3 != null || num2 != null) && l2.longValue() < 0)) {
                    iArr[0] = runCommentRequest(i, runnable, num, num3, num2, MessagesController.getInstance(i).getChat(Long.valueOf(-l2.longValue())));
                    if (iArr[0] != 0) {
                        z4 = false;
                    }
                } else if (str != null) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("onlySelect", true);
                    bundle.putBoolean("cantSendToChannels", true);
                    bundle.putInt("dialogsType", 1);
                    bundle.putString("selectAlertString", LocaleController.getString("SendGameToText", R.string.SendGameToText));
                    bundle.putString("selectAlertStringGroup", LocaleController.getString("SendGameToGroupText", R.string.SendGameToGroupText));
                    DialogsActivity dialogsActivity = new DialogsActivity(bundle);
                    final TLRPC$User user2 = MessagesController.getInstance(i).getUser(l2);
                    dialogsActivity.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda121
                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public final boolean didSelectDialogs(DialogsActivity dialogsActivity2, ArrayList arrayList, CharSequence charSequence, boolean z5, TopicsFragment topicsFragment) {
                            boolean lambda$runLinkRequest$55;
                            lambda$runLinkRequest$55 = LaunchActivity.this.lambda$runLinkRequest$55(str, i, user2, dialogsActivity2, arrayList, charSequence, z5, topicsFragment);
                            return lambda$runLinkRequest$55;
                        }
                    });
                    this.actionBarLayout.presentFragment(dialogsActivity, !AndroidUtilities.isTablet() ? this.actionBarLayout.getFragmentStack().size() <= 1 || !(this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity) : this.layersActionBarLayout.getFragmentStack().size() <= 0 || !(this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1) instanceof DialogsActivity), true, true, false);
                    if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
                        SecretMediaViewer.getInstance().closePhoto(false, false);
                    } else if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                        PhotoViewer.getInstance().closePhoto(false, true);
                    } else if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                        ArticleViewer.getInstance().close(false, true);
                    }
                    GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
                    if (groupCallActivity != null) {
                        groupCallActivity.dismiss();
                    }
                    this.drawerLayoutContainer.setAllowOpenDrawer(false, false);
                    if (AndroidUtilities.isTablet()) {
                        this.actionBarLayout.rebuildFragments(1);
                        this.rightActionBarLayout.rebuildFragments(1);
                    } else {
                        this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
                    }
                } else if (str10 != null || str11 != null) {
                    final TLRPC$User user3 = MessagesController.getInstance(i).getUser(l2);
                    if (user3 == null || (user3.bot && user3.bot_nochats)) {
                        try {
                            if (mainFragmentsStack.isEmpty()) {
                                return;
                            }
                            ArrayList<BaseFragment> arrayList = mainFragmentsStack;
                            BulletinFactory.of(arrayList.get(arrayList.size() - 1)).createErrorBulletin(LocaleController.getString("BotCantJoinGroups", R.string.BotCantJoinGroups)).show();
                            return;
                        } catch (Exception e) {
                            FileLog.e(e);
                            return;
                        }
                    }
                    Bundle bundle2 = new Bundle();
                    bundle2.putBoolean("onlySelect", true);
                    bundle2.putInt("dialogsType", 2);
                    bundle2.putBoolean("resetDelegate", false);
                    bundle2.putBoolean("closeFragment", false);
                    bundle2.putBoolean("allowGroups", str10 != null);
                    bundle2.putBoolean("allowChannels", str11 != null);
                    String str26 = TextUtils.isEmpty(str10) ? TextUtils.isEmpty(str11) ? null : str11 : str10;
                    final DialogsActivity dialogsActivity2 = new DialogsActivity(bundle2);
                    final String str27 = str26;
                    dialogsActivity2.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda120
                        @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                        public final boolean didSelectDialogs(DialogsActivity dialogsActivity3, ArrayList arrayList2, CharSequence charSequence, boolean z5, TopicsFragment topicsFragment) {
                            boolean lambda$runLinkRequest$60;
                            lambda$runLinkRequest$60 = LaunchActivity.this.lambda$runLinkRequest$60(i, user3, str12, str27, dialogsActivity2, dialogsActivity3, arrayList2, charSequence, z5, topicsFragment);
                            return lambda$runLinkRequest$60;
                        }
                    });
                    lambda$runLinkRequest$77(dialogsActivity2);
                } else {
                    Bundle bundle3 = new Bundle();
                    TLRPC$User user4 = MessagesController.getInstance(i).getUser(l2);
                    if (l2.longValue() < 0) {
                        bundle3.putLong("chat_id", -l2.longValue());
                        longValue = l2.longValue();
                    } else {
                        bundle3.putLong("user_id", l2.longValue());
                        longValue = l2.longValue();
                    }
                    if (str9 == null || user4 == null || !user4.bot) {
                        z3 = false;
                    } else {
                        bundle3.putString("botUser", str9);
                        z3 = true;
                    }
                    if (this.navigateToPremiumBot) {
                        this.navigateToPremiumBot = false;
                        bundle3.putBoolean("premium_bot", true);
                    }
                    if (num != null) {
                        bundle3.putInt("message_id", num.intValue());
                    }
                    if (str2 != null) {
                        bundle3.putString("voicechat", str2);
                    }
                    if (str3 != null) {
                        bundle3.putString("livestream", str3);
                    }
                    if (i3 >= 0) {
                        bundle3.putInt("video_timestamp", i3);
                    }
                    if (str23 != null) {
                        bundle3.putString("attach_bot", str23);
                    }
                    if (str22 != null) {
                        bundle3.putString("attach_bot_start_command", str22);
                    }
                    if (mainFragmentsStack.isEmpty() || str2 != null) {
                        baseFragment = null;
                    } else {
                        ArrayList<BaseFragment> arrayList2 = mainFragmentsStack;
                        baseFragment = arrayList2.get(arrayList2.size() - 1);
                    }
                    if (baseFragment == null || MessagesController.getInstance(i).checkCanOpenChat(bundle3, baseFragment)) {
                        if (z3 && (baseFragment instanceof ChatActivity)) {
                            ChatActivity chatActivity = (ChatActivity) baseFragment;
                            if (chatActivity.getDialogId() == longValue) {
                                chatActivity.setBotUser(str9);
                            }
                        }
                        long j = -longValue;
                        TLRPC$Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(j));
                        if (chat != null && chat.forum) {
                            Integer num4 = num2 == null ? num : num2;
                            if (num4 != null && num4.intValue() != 0) {
                                openForumFromLink(longValue, num4.intValue(), num, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda29
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        LaunchActivity.lambda$runLinkRequest$61(runnable);
                                    }
                                });
                            } else {
                                Bundle bundle4 = new Bundle();
                                bundle4.putLong("chat_id", j);
                                lambda$runLinkRequest$77(new TopicsFragment(bundle4));
                                try {
                                    runnable.run();
                                } catch (Exception e2) {
                                    FileLog.e(e2);
                                }
                            }
                        } else {
                            MessagesController.getInstance(i).ensureMessagesLoaded(longValue, num == null ? 0 : num.intValue(), new AnonymousClass21(runnable, str3, baseFragment, longValue, num, bundle3));
                        }
                        z4 = false;
                    }
                }
            } else {
                final TLRPC$User user5 = MessagesController.getInstance(i).getUser(l2);
                if (user5 != null && user5.bot) {
                    if (user5.bot_attach_menu) {
                        TLRPC$TL_messages_getAttachMenuBot tLRPC$TL_messages_getAttachMenuBot = new TLRPC$TL_messages_getAttachMenuBot();
                        tLRPC$TL_messages_getAttachMenuBot.bot = MessagesController.getInstance(i).getInputUser(l2.longValue());
                        ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_messages_getAttachMenuBot, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda92
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                LaunchActivity.this.lambda$runLinkRequest$54(i, str24, user5, str22, l2, tLObject, tLRPC$TL_error);
                            }
                        });
                    } else {
                        ArrayList<BaseFragment> arrayList3 = mainFragmentsStack;
                        BulletinFactory.of(arrayList3.get(arrayList3.size() - 1)).createErrorBulletin(LocaleController.getString(R.string.BotCantAddToAttachMenu)).show();
                    }
                } else {
                    ArrayList<BaseFragment> arrayList4 = mainFragmentsStack;
                    BulletinFactory.of(arrayList4.get(arrayList4.size() - 1)).createErrorBulletin(LocaleController.getString(R.string.BotSetAttachLinkNotBot)).show();
                }
            }
        } else {
            try {
                BaseFragment lastFragment = getLastFragment();
                if (lastFragment != null) {
                    if (lastFragment instanceof ChatActivity) {
                        ((ChatActivity) lastFragment).shakeContent();
                    }
                    if (AndroidUtilities.isNumeric(str5)) {
                        BulletinFactory.of(lastFragment).createErrorBulletin(LocaleController.getString("NoPhoneFound", R.string.NoPhoneFound)).show();
                    } else {
                        BulletinFactory.of(lastFragment).createErrorBulletin(LocaleController.getString("NoUsernameFound", R.string.NoUsernameFound)).show();
                    }
                }
            } catch (Exception e3) {
                FileLog.e(e3);
            }
        }
        if (z4) {
            try {
                runnable.run();
            } catch (Exception e4) {
                FileLog.e(e4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$44(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, boolean z, Integer num, Long l, Integer num2, Integer num3, String str12, HashMap hashMap, String str13, String str14, String str15, String str16, TLRPC$TL_wallPaper tLRPC$TL_wallPaper, String str17, String str18, String str19, String str20, int i, int i2, String str21, String str22, String str23, Browser.Progress progress, boolean z2) {
        runLinkRequest(this.currentAccount, str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, z, num, l, num2, num3, str12, hashMap, str13, str14, str15, str16, tLRPC$TL_wallPaper, str17, str18, str19, str20, i, i2, str21, str22, str23, null, null, progress, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$47(final String str, final String str2, final String str3, final String str4, final String str5, final String str6, final String str7, final String str8, final String str9, final String str10, final String str11, final boolean z, final Integer num, final Long l, final Integer num2, final Integer num3, final String str12, final HashMap hashMap, final String str13, final String str14, final String str15, final String str16, final TLRPC$TL_wallPaper tLRPC$TL_wallPaper, final String str17, final String str18, final String str19, final String str20, final int i, final int i2, final String str21, final String str22, final String str23, final Browser.Progress progress, final boolean z2, final Runnable runnable, final TLRPC$User tLRPC$User, final String str24, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        if (tLRPC$TL_error != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda57
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$runLinkRequest$44(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, z, num, l, num2, num3, str12, hashMap, str13, str14, str15, str16, tLRPC$TL_wallPaper, str17, str18, str19, str20, i, i2, str21, str22, str23, progress, z2);
                }
            });
        } else {
            final TLRPC$TL_messages_botApp tLRPC$TL_messages_botApp = (TLRPC$TL_messages_botApp) tLObject;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$runLinkRequest$46(runnable, tLRPC$User, tLRPC$TL_messages_botApp, str24, z2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$46(Runnable runnable, final TLRPC$User tLRPC$User, final TLRPC$TL_messages_botApp tLRPC$TL_messages_botApp, final String str, boolean z) {
        runnable.run();
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        final BaseFragment baseFragment = mainFragmentsStack.get(r0.size() - 1);
        Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda76
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$45(baseFragment, tLRPC$User, tLRPC$TL_messages_botApp, atomicBoolean, str);
            }
        };
        if (tLRPC$TL_messages_botApp.inactive || z) {
            AlertsCreator.createBotLaunchAlert(baseFragment, tLRPC$TL_messages_botApp, tLRPC$User, atomicBoolean, runnable2);
        } else {
            runnable2.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$45(BaseFragment baseFragment, TLRPC$User tLRPC$User, TLRPC$TL_messages_botApp tLRPC$TL_messages_botApp, AtomicBoolean atomicBoolean, String str) {
        BotWebViewSheet botWebViewSheet = new BotWebViewSheet(this, baseFragment.getResourceProvider());
        botWebViewSheet.setParentActivity(this);
        int i = this.currentAccount;
        long j = tLRPC$User.id;
        botWebViewSheet.requestWebView(i, j, j, null, null, 3, 0, false, baseFragment, tLRPC$TL_messages_botApp.app, atomicBoolean.get(), str, tLRPC$User);
        botWebViewSheet.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$54(final int i, final String str, final TLRPC$User tLRPC$User, final String str2, final Long l, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda63
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$53(tLObject, i, str, tLRPC$User, str2, l);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$53(TLObject tLObject, final int i, String str, final TLRPC$User tLRPC$User, final String str2, final Long l) {
        final DialogsActivity dialogsActivity;
        if (tLObject instanceof TLRPC$TL_attachMenuBotsBot) {
            TLRPC$TL_attachMenuBotsBot tLRPC$TL_attachMenuBotsBot = (TLRPC$TL_attachMenuBotsBot) tLObject;
            MessagesController.getInstance(i).putUsers(tLRPC$TL_attachMenuBotsBot.users, false);
            TLRPC$TL_attachMenuBot tLRPC$TL_attachMenuBot = tLRPC$TL_attachMenuBotsBot.bot;
            ArrayList<BaseFragment> arrayList = mainFragmentsStack;
            final BaseFragment baseFragment = arrayList.get(arrayList.size() - 1);
            ArrayList arrayList2 = new ArrayList();
            if (!TextUtils.isEmpty(str)) {
                for (String str3 : str.split(" ")) {
                    if (MediaDataController.canShowAttachMenuBotForTarget(tLRPC$TL_attachMenuBot, str3)) {
                        arrayList2.add(str3);
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                dialogsActivity = null;
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("dialogsType", 14);
                bundle.putBoolean("onlySelect", true);
                bundle.putBoolean("allowGroups", arrayList2.contains("groups"));
                bundle.putBoolean("allowUsers", arrayList2.contains("users"));
                bundle.putBoolean("allowChannels", arrayList2.contains("channels"));
                bundle.putBoolean("allowBots", arrayList2.contains("bots"));
                DialogsActivity dialogsActivity2 = new DialogsActivity(bundle);
                dialogsActivity2.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda122
                    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
                    public final boolean didSelectDialogs(DialogsActivity dialogsActivity3, ArrayList arrayList3, CharSequence charSequence, boolean z, TopicsFragment topicsFragment) {
                        boolean lambda$runLinkRequest$48;
                        lambda$runLinkRequest$48 = LaunchActivity.this.lambda$runLinkRequest$48(tLRPC$User, str2, i, dialogsActivity3, arrayList3, charSequence, z, topicsFragment);
                        return lambda$runLinkRequest$48;
                    }
                });
                dialogsActivity = dialogsActivity2;
            }
            if (!tLRPC$TL_attachMenuBot.inactive) {
                if (dialogsActivity != null) {
                    lambda$runLinkRequest$77(dialogsActivity);
                    return;
                }
                if (baseFragment instanceof ChatActivity) {
                    ChatActivity chatActivity = (ChatActivity) baseFragment;
                    if (!MediaDataController.canShowAttachMenuBot(tLRPC$TL_attachMenuBot, chatActivity.getCurrentUser() != null ? chatActivity.getCurrentUser() : chatActivity.getCurrentChat())) {
                        BulletinFactory.of(baseFragment).createErrorBulletin(LocaleController.getString(R.string.BotAlreadyAddedToAttachMenu)).show();
                        return;
                    } else {
                        chatActivity.openAttachBotLayout(tLRPC$User.id, str2);
                        return;
                    }
                }
                BulletinFactory.of(baseFragment).createErrorBulletin(LocaleController.getString(R.string.BotAlreadyAddedToAttachMenu)).show();
                return;
            }
            AttachBotIntroTopView attachBotIntroTopView = new AttachBotIntroTopView(this);
            attachBotIntroTopView.setColor(Theme.getColor(Theme.key_chat_attachIcon));
            attachBotIntroTopView.setBackgroundColor(Theme.getColor(Theme.key_dialogTopBackground));
            attachBotIntroTopView.setAttachBot(tLRPC$TL_attachMenuBot);
            final AtomicBoolean atomicBoolean = new AtomicBoolean();
            AlertDialog.Builder negativeButton = new AlertDialog.Builder(this).setTopView(attachBotIntroTopView).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BotRequestAttachPermission", R.string.BotRequestAttachPermission, UserObject.getUserName(tLRPC$User)))).setPositiveButton(LocaleController.getString(R.string.BotAddToMenu), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    LaunchActivity.this.lambda$runLinkRequest$51(i, l, atomicBoolean, dialogsActivity, baseFragment, tLRPC$User, str2, dialogInterface, i2);
                }
            }).setNegativeButton(LocaleController.getString(R.string.Cancel), null);
            if (tLRPC$TL_attachMenuBot.request_write_access) {
                atomicBoolean.set(true);
                final CheckBoxCell checkBoxCell = new CheckBoxCell(this, 5, baseFragment.getResourceProvider());
                checkBoxCell.setBackground(Theme.getSelectorDrawable(false));
                checkBoxCell.setMultiline(true);
                checkBoxCell.setText(AndroidUtilities.replaceTags(LocaleController.formatString("OpenUrlOption2", R.string.OpenUrlOption2, UserObject.getUserName(tLRPC$User))), "", true, false);
                checkBoxCell.setPadding(LocaleController.isRTL ? AndroidUtilities.dp(16.0f) : AndroidUtilities.dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(8.0f) : AndroidUtilities.dp(16.0f), 0);
                checkBoxCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda16
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LaunchActivity.lambda$runLinkRequest$52(CheckBoxCell.this, atomicBoolean, view);
                    }
                });
                negativeButton.setView(checkBoxCell);
            }
            negativeButton.show();
            return;
        }
        ArrayList<BaseFragment> arrayList3 = mainFragmentsStack;
        BulletinFactory.of(arrayList3.get(arrayList3.size() - 1)).createErrorBulletin(LocaleController.getString(R.string.BotCantAddToAttachMenu)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runLinkRequest$48(TLRPC$User tLRPC$User, String str, int i, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        bundle.putString("attach_bot", UserObject.getPublicUsername(tLRPC$User));
        if (str != null) {
            bundle.putString("attach_bot_start_command", str);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
            this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$51(final int i, Long l, AtomicBoolean atomicBoolean, final DialogsActivity dialogsActivity, final BaseFragment baseFragment, final TLRPC$User tLRPC$User, final String str, DialogInterface dialogInterface, int i2) {
        TLRPC$TL_messages_toggleBotInAttachMenu tLRPC$TL_messages_toggleBotInAttachMenu = new TLRPC$TL_messages_toggleBotInAttachMenu();
        tLRPC$TL_messages_toggleBotInAttachMenu.bot = MessagesController.getInstance(i).getInputUser(l.longValue());
        tLRPC$TL_messages_toggleBotInAttachMenu.enabled = true;
        tLRPC$TL_messages_toggleBotInAttachMenu.write_allowed = atomicBoolean.get();
        ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_messages_toggleBotInAttachMenu, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda95
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LaunchActivity.this.lambda$runLinkRequest$50(i, dialogsActivity, baseFragment, tLRPC$User, str, tLObject, tLRPC$TL_error);
            }
        }, 66);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$50(final int i, final DialogsActivity dialogsActivity, final BaseFragment baseFragment, final TLRPC$User tLRPC$User, final String str, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$49(tLObject, i, dialogsActivity, baseFragment, tLRPC$User, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$49(TLObject tLObject, int i, DialogsActivity dialogsActivity, BaseFragment baseFragment, TLRPC$User tLRPC$User, String str) {
        if (tLObject instanceof TLRPC$TL_boolTrue) {
            MediaDataController.getInstance(i).loadAttachMenuBots(false, true);
            if (dialogsActivity != null) {
                lambda$runLinkRequest$77(dialogsActivity);
            } else if (baseFragment instanceof ChatActivity) {
                ((ChatActivity) baseFragment).openAttachBotLayout(tLRPC$User.id, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$52(CheckBoxCell checkBoxCell, AtomicBoolean atomicBoolean, View view) {
        boolean z = !checkBoxCell.isChecked();
        checkBoxCell.setChecked(z, true);
        atomicBoolean.set(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runLinkRequest$55(String str, int i, TLRPC$User tLRPC$User, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        TLRPC$TL_inputMediaGame tLRPC$TL_inputMediaGame = new TLRPC$TL_inputMediaGame();
        TLRPC$TL_inputGameShortName tLRPC$TL_inputGameShortName = new TLRPC$TL_inputGameShortName();
        tLRPC$TL_inputMediaGame.id = tLRPC$TL_inputGameShortName;
        tLRPC$TL_inputGameShortName.short_name = str;
        tLRPC$TL_inputGameShortName.bot_id = MessagesController.getInstance(i).getInputUser(tLRPC$User);
        SendMessagesHelper.getInstance(i).sendGame(MessagesController.getInstance(i).getInputPeer(j), tLRPC$TL_inputMediaGame, 0L, 0L);
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
            this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runLinkRequest$60(final int i, final TLRPC$User tLRPC$User, final String str, final String str2, final DialogsActivity dialogsActivity, DialogsActivity dialogsActivity2, ArrayList arrayList, CharSequence charSequence, boolean z, TopicsFragment topicsFragment) {
        TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights;
        final long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        final TLRPC$Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j));
        if (chat != null && (chat.creator || ((tLRPC$TL_chatAdminRights = chat.admin_rights) != null && tLRPC$TL_chatAdminRights.add_admins))) {
            MessagesController.getInstance(i).checkIsInChat(false, chat, tLRPC$User, new MessagesController.IsInChatCheckedCallback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda83
                @Override // org.telegram.messenger.MessagesController.IsInChatCheckedCallback
                public final void run(boolean z2, TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights2, String str3) {
                    LaunchActivity.this.lambda$runLinkRequest$58(str, str2, i, chat, dialogsActivity, tLRPC$User, j, z2, tLRPC$TL_chatAdminRights2, str3);
                }
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            int i2 = R.string.AddBot;
            builder.setTitle(LocaleController.getString("AddBot", i2));
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("AddMembersAlertNamesText", R.string.AddMembersAlertNamesText, UserObject.getUserName(tLRPC$User), chat == null ? "" : chat.title)));
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            builder.setPositiveButton(LocaleController.getString("AddBot", i2), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda10
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i3) {
                    LaunchActivity.this.lambda$runLinkRequest$59(j, i, tLRPC$User, str2, dialogInterface, i3);
                }
            });
            builder.show();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$58(final String str, final String str2, final int i, final TLRPC$Chat tLRPC$Chat, final DialogsActivity dialogsActivity, final TLRPC$User tLRPC$User, final long j, final boolean z, final TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights, final String str3) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$57(str, tLRPC$TL_chatAdminRights, z, str2, i, tLRPC$Chat, dialogsActivity, tLRPC$User, j, str3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$57(String str, TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights, boolean z, String str2, final int i, final TLRPC$Chat tLRPC$Chat, final DialogsActivity dialogsActivity, TLRPC$User tLRPC$User, long j, String str3) {
        TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights2;
        TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights3;
        if (str != null) {
            String[] split = str.split("\\+| ");
            tLRPC$TL_chatAdminRights2 = new TLRPC$TL_chatAdminRights();
            for (String str4 : split) {
                str4.hashCode();
                switch (str4) {
                    case "ban_users":
                    case "restrict_members":
                        tLRPC$TL_chatAdminRights2.ban_users = true;
                        break;
                    case "anonymous":
                        tLRPC$TL_chatAdminRights2.anonymous = true;
                        break;
                    case "change_info":
                        tLRPC$TL_chatAdminRights2.change_info = true;
                        break;
                    case "delete_messages":
                        tLRPC$TL_chatAdminRights2.delete_messages = true;
                        break;
                    case "edit_messages":
                        tLRPC$TL_chatAdminRights2.edit_messages = true;
                        break;
                    case "manage_call":
                    case "manage_video_chats":
                        tLRPC$TL_chatAdminRights2.manage_call = true;
                        break;
                    case "manage_chat":
                    case "other":
                        tLRPC$TL_chatAdminRights2.other = true;
                        break;
                    case "promote_members":
                    case "add_admins":
                        tLRPC$TL_chatAdminRights2.add_admins = true;
                        break;
                    case "invite_users":
                        tLRPC$TL_chatAdminRights2.invite_users = true;
                        break;
                    case "post_messages":
                        tLRPC$TL_chatAdminRights2.post_messages = true;
                        break;
                    case "pin_messages":
                        tLRPC$TL_chatAdminRights2.pin_messages = true;
                        break;
                }
            }
        } else {
            tLRPC$TL_chatAdminRights2 = null;
        }
        if (tLRPC$TL_chatAdminRights2 == null && tLRPC$TL_chatAdminRights == null) {
            tLRPC$TL_chatAdminRights3 = null;
        } else {
            if (tLRPC$TL_chatAdminRights2 != null) {
                if (tLRPC$TL_chatAdminRights == null) {
                    tLRPC$TL_chatAdminRights3 = tLRPC$TL_chatAdminRights2;
                } else {
                    tLRPC$TL_chatAdminRights.change_info = tLRPC$TL_chatAdminRights2.change_info || tLRPC$TL_chatAdminRights.change_info;
                    tLRPC$TL_chatAdminRights.post_messages = tLRPC$TL_chatAdminRights2.post_messages || tLRPC$TL_chatAdminRights.post_messages;
                    tLRPC$TL_chatAdminRights.edit_messages = tLRPC$TL_chatAdminRights2.edit_messages || tLRPC$TL_chatAdminRights.edit_messages;
                    tLRPC$TL_chatAdminRights.add_admins = tLRPC$TL_chatAdminRights2.add_admins || tLRPC$TL_chatAdminRights.add_admins;
                    tLRPC$TL_chatAdminRights.delete_messages = tLRPC$TL_chatAdminRights2.delete_messages || tLRPC$TL_chatAdminRights.delete_messages;
                    tLRPC$TL_chatAdminRights.ban_users = tLRPC$TL_chatAdminRights2.ban_users || tLRPC$TL_chatAdminRights.ban_users;
                    tLRPC$TL_chatAdminRights.invite_users = tLRPC$TL_chatAdminRights2.invite_users || tLRPC$TL_chatAdminRights.invite_users;
                    tLRPC$TL_chatAdminRights.pin_messages = tLRPC$TL_chatAdminRights2.pin_messages || tLRPC$TL_chatAdminRights.pin_messages;
                    tLRPC$TL_chatAdminRights.manage_call = tLRPC$TL_chatAdminRights2.manage_call || tLRPC$TL_chatAdminRights.manage_call;
                    tLRPC$TL_chatAdminRights.anonymous = tLRPC$TL_chatAdminRights2.anonymous || tLRPC$TL_chatAdminRights.anonymous;
                    tLRPC$TL_chatAdminRights.other = tLRPC$TL_chatAdminRights2.other || tLRPC$TL_chatAdminRights.other;
                }
            }
            tLRPC$TL_chatAdminRights3 = tLRPC$TL_chatAdminRights;
        }
        if (z && tLRPC$TL_chatAdminRights2 == null && !TextUtils.isEmpty(str2)) {
            MessagesController.getInstance(this.currentAccount).addUserToChat(tLRPC$Chat.id, tLRPC$User, 0, str2, dialogsActivity, true, new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$runLinkRequest$56(i, tLRPC$Chat, dialogsActivity);
                }
            }, null);
            return;
        }
        ChatRightsEditActivity chatRightsEditActivity = new ChatRightsEditActivity(tLRPC$User.id, -j, tLRPC$TL_chatAdminRights3, null, null, str3, 2, true, !z, str2);
        chatRightsEditActivity.setDelegate(new ChatRightsEditActivity.ChatRightsEditActivityDelegate(this) { // from class: org.telegram.ui.LaunchActivity.20
            @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
            public void didChangeOwner(TLRPC$User tLRPC$User2) {
            }

            @Override // org.telegram.ui.ChatRightsEditActivity.ChatRightsEditActivityDelegate
            public void didSetRights(int i2, TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights4, TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights, String str5) {
                dialogsActivity.removeSelfFromStack();
                NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
            }
        });
        this.actionBarLayout.presentFragment(chatRightsEditActivity, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$56(int i, TLRPC$Chat tLRPC$Chat, DialogsActivity dialogsActivity) {
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        bundle.putLong("chat_id", tLRPC$Chat.id);
        if (MessagesController.getInstance(this.currentAccount).checkCanOpenChat(bundle, dialogsActivity)) {
            presentFragment(new ChatActivity(bundle), true, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$59(long j, int i, TLRPC$User tLRPC$User, String str, DialogInterface dialogInterface, int i2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        long j2 = -j;
        bundle.putLong("chat_id", j2);
        ChatActivity chatActivity = new ChatActivity(bundle);
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
        MessagesController.getInstance(i).addUserToChat(j2, tLRPC$User, 0, str, chatActivity, null);
        this.actionBarLayout.presentFragment(chatActivity, true, false, true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$61(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* renamed from: org.telegram.ui.LaunchActivity$21, reason: invalid class name */
    class AnonymousClass21 implements MessagesController.MessagesLoadedCallback {
        final /* synthetic */ Bundle val$args;
        final /* synthetic */ long val$dialog_id;
        final /* synthetic */ Runnable val$dismissLoading;
        final /* synthetic */ BaseFragment val$lastFragment;
        final /* synthetic */ String val$livestream;
        final /* synthetic */ Integer val$messageId;

        AnonymousClass21(Runnable runnable, String str, BaseFragment baseFragment, long j, Integer num, Bundle bundle) {
            this.val$dismissLoading = runnable;
            this.val$livestream = str;
            this.val$lastFragment = baseFragment;
            this.val$dialog_id = j;
            this.val$messageId = num;
            this.val$args = bundle;
        }

        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        public void onMessagesLoaded(boolean z) {
            BaseFragment chatActivity;
            try {
                this.val$dismissLoading.run();
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (LaunchActivity.this.isFinishing()) {
                return;
            }
            if (this.val$livestream != null) {
                BaseFragment baseFragment = this.val$lastFragment;
                if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).getDialogId() == this.val$dialog_id) {
                    chatActivity = this.val$lastFragment;
                    final BaseFragment baseFragment2 = chatActivity;
                    final String str = this.val$livestream;
                    final long j = this.val$dialog_id;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$21$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LaunchActivity.AnonymousClass21.this.lambda$onMessagesLoaded$2(str, j, baseFragment2);
                        }
                    }, 150L);
                }
            }
            BaseFragment baseFragment3 = this.val$lastFragment;
            if ((baseFragment3 instanceof ChatActivity) && ((ChatActivity) baseFragment3).getDialogId() == this.val$dialog_id && this.val$messageId == null) {
                ChatActivity chatActivity2 = (ChatActivity) this.val$lastFragment;
                AndroidUtilities.shakeViewSpring(chatActivity2.getChatListView(), 5.0f);
                BotWebViewVibrationEffect.APP_ERROR.vibrate();
                ChatActivityEnterView chatActivityEnterView = chatActivity2.getChatActivityEnterView();
                for (int i = 0; i < chatActivityEnterView.getChildCount(); i++) {
                    AndroidUtilities.shakeViewSpring(chatActivityEnterView.getChildAt(i), 5.0f);
                }
                ActionBar actionBar = chatActivity2.getActionBar();
                for (int i2 = 0; i2 < actionBar.getChildCount(); i2++) {
                    AndroidUtilities.shakeViewSpring(actionBar.getChildAt(i2), 5.0f);
                }
                chatActivity = this.val$lastFragment;
            } else {
                chatActivity = new ChatActivity(this.val$args);
                LaunchActivity.this.actionBarLayout.presentFragment(chatActivity);
            }
            final BaseFragment baseFragment22 = chatActivity;
            final String str2 = this.val$livestream;
            final long j2 = this.val$dialog_id;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$21$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.AnonymousClass21.this.lambda$onMessagesLoaded$2(str2, j2, baseFragment22);
                }
            }, 150L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessagesLoaded$2(String str, final long j, final BaseFragment baseFragment) {
            if (str != null) {
                final AccountInstance accountInstance = AccountInstance.getInstance(LaunchActivity.this.currentAccount);
                long j2 = -j;
                ChatObject.Call groupCall = accountInstance.getMessagesController().getGroupCall(j2, false);
                if (groupCall != null) {
                    VoIPHelper.startCall(accountInstance.getMessagesController().getChat(Long.valueOf(j2)), accountInstance.getMessagesController().getInputPeer(j), null, false, Boolean.valueOf(groupCall.call.rtmp_stream ? false : true), LaunchActivity.this, baseFragment, accountInstance);
                    return;
                }
                TLRPC$ChatFull chatFull = accountInstance.getMessagesController().getChatFull(j2);
                if (chatFull != null) {
                    if (chatFull.call == null) {
                        if (baseFragment.getParentActivity() != null) {
                            BulletinFactory.of(baseFragment).createSimpleBulletin(R.raw.linkbroken, LocaleController.getString("InviteExpired", R.string.InviteExpired)).show();
                            return;
                        }
                        return;
                    }
                    accountInstance.getMessagesController().getGroupCall(j2, true, new Runnable() { // from class: org.telegram.ui.LaunchActivity$21$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            LaunchActivity.AnonymousClass21.this.lambda$onMessagesLoaded$1(accountInstance, j, baseFragment);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessagesLoaded$1(final AccountInstance accountInstance, final long j, final BaseFragment baseFragment) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$21$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.AnonymousClass21.this.lambda$onMessagesLoaded$0(accountInstance, j, baseFragment);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessagesLoaded$0(AccountInstance accountInstance, long j, BaseFragment baseFragment) {
            long j2 = -j;
            ChatObject.Call groupCall = accountInstance.getMessagesController().getGroupCall(j2, false);
            VoIPHelper.startCall(accountInstance.getMessagesController().getChat(Long.valueOf(j2)), accountInstance.getMessagesController().getInputPeer(j), null, false, Boolean.valueOf(groupCall == null || !groupCall.call.rtmp_stream), LaunchActivity.this, baseFragment, accountInstance);
        }

        @Override // org.telegram.messenger.MessagesController.MessagesLoadedCallback
        public void onError() {
            if (!LaunchActivity.this.isFinishing()) {
                AlertsCreator.showSimpleAlert((BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), LocaleController.getString("JoinToGroupErrorNotExist", R.string.JoinToGroupErrorNotExist));
            }
            try {
                this.val$dismissLoading.run();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$65(final int i, final AlertDialog alertDialog, final Runnable runnable, final String str, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$64(tLRPC$TL_error, tLObject, i, alertDialog, runnable, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:
    
        if (r10.chat.has_geo != false) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0077, code lost:
    
        if (r15.checkCanOpenChat(r7, r0.get(r0.size() - 1)) != false) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$runLinkRequest$64(org.telegram.tgnet.TLRPC$TL_error r10, org.telegram.tgnet.TLObject r11, int r12, org.telegram.ui.ActionBar.AlertDialog r13, final java.lang.Runnable r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.lambda$runLinkRequest$64(org.telegram.tgnet.TLRPC$TL_error, org.telegram.tgnet.TLObject, int, org.telegram.ui.ActionBar.AlertDialog, java.lang.Runnable, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$63(boolean[] zArr, DialogInterface dialogInterface) {
        zArr[0] = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$67(final int i, final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        if (tLRPC$TL_error == null) {
            MessagesController.getInstance(i).processUpdates((TLRPC$Updates) tLObject, false);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda54
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$66(runnable, tLRPC$TL_error, tLObject, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$66(Runnable runnable, TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, int i) {
        if (isFinishing()) {
            return;
        }
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLRPC$TL_error == null) {
            if (this.actionBarLayout != null) {
                TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                if (tLRPC$Updates.chats.isEmpty()) {
                    return;
                }
                TLRPC$Chat tLRPC$Chat = tLRPC$Updates.chats.get(0);
                tLRPC$Chat.left = false;
                tLRPC$Chat.kicked = false;
                MessagesController.getInstance(i).putUsers(tLRPC$Updates.users, false);
                MessagesController.getInstance(i).putChats(tLRPC$Updates.chats, false);
                Bundle bundle = new Bundle();
                bundle.putLong("chat_id", tLRPC$Chat.id);
                if (!mainFragmentsStack.isEmpty()) {
                    if (!MessagesController.getInstance(i).checkCanOpenChat(bundle, mainFragmentsStack.get(r0.size() - 1))) {
                        return;
                    }
                }
                ChatActivity chatActivity = new ChatActivity(bundle);
                NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
                this.actionBarLayout.presentFragment(chatActivity, false, true, true, false);
                return;
            }
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
            builder.setMessage(LocaleController.getString("FloodWait", R.string.FloodWait));
        } else if (tLRPC$TL_error.text.equals("USERS_TOO_MUCH")) {
            builder.setMessage(LocaleController.getString("JoinToGroupErrorFull", R.string.JoinToGroupErrorFull));
        } else {
            builder.setMessage(LocaleController.getString("JoinToGroupErrorNotExist", R.string.JoinToGroupErrorNotExist));
        }
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        showAlertDialog(builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$runLinkRequest$68(boolean z, int i, String str, DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z2, TopicsFragment topicsFragment) {
        long j = ((MessagesStorage.TopicKey) arrayList.get(0)).dialogId;
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        bundle.putBoolean("hasUrl", z);
        if (DialogObject.isEncryptedDialog(j)) {
            bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j));
        } else if (DialogObject.isUserDialog(j)) {
            bundle.putLong("user_id", j);
        } else {
            bundle.putLong("chat_id", -j);
        }
        if (MessagesController.getInstance(i).checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
            MediaDataController.getInstance(i).saveDraft(j, 0, str, null, null, false);
            this.actionBarLayout.presentFragment(new ChatActivity(bundle), true, false, true, false);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$72(int[] iArr, final int i, final Runnable runnable, final TLRPC$TL_account_getAuthorizationForm tLRPC$TL_account_getAuthorizationForm, final String str, final String str2, final String str3, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        final TLRPC$TL_account_authorizationForm tLRPC$TL_account_authorizationForm = (TLRPC$TL_account_authorizationForm) tLObject;
        if (tLRPC$TL_account_authorizationForm != null) {
            iArr[0] = ConnectionsManager.getInstance(i).sendRequest(new TLRPC$TL_account_getPassword(), new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda100
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC$TL_error tLRPC$TL_error2) {
                    LaunchActivity.this.lambda$runLinkRequest$70(runnable, i, tLRPC$TL_account_authorizationForm, tLRPC$TL_account_getAuthorizationForm, str, str2, str3, tLObject2, tLRPC$TL_error2);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$runLinkRequest$71(runnable, tLRPC$TL_error);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$70(final Runnable runnable, final int i, final TLRPC$TL_account_authorizationForm tLRPC$TL_account_authorizationForm, final TLRPC$TL_account_getAuthorizationForm tLRPC$TL_account_getAuthorizationForm, final String str, final String str2, final String str3, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$69(runnable, tLObject, i, tLRPC$TL_account_authorizationForm, tLRPC$TL_account_getAuthorizationForm, str, str2, str3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$69(Runnable runnable, TLObject tLObject, int i, TLRPC$TL_account_authorizationForm tLRPC$TL_account_authorizationForm, TLRPC$TL_account_getAuthorizationForm tLRPC$TL_account_getAuthorizationForm, String str, String str2, String str3) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLObject != null) {
            MessagesController.getInstance(i).putUsers(tLRPC$TL_account_authorizationForm.users, false);
            lambda$runLinkRequest$77(new PassportActivity(5, tLRPC$TL_account_getAuthorizationForm.bot_id, tLRPC$TL_account_getAuthorizationForm.scope, tLRPC$TL_account_getAuthorizationForm.public_key, str, str2, str3, tLRPC$TL_account_authorizationForm, (TLRPC$account_Password) tLObject));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$71(Runnable runnable, TLRPC$TL_error tLRPC$TL_error) {
        try {
            runnable.run();
            if ("APP_VERSION_OUTDATED".equals(tLRPC$TL_error.text)) {
                AlertsCreator.showUpdateAppAlert(this, LocaleController.getString("UpdateAppAlert", R.string.UpdateAppAlert), true);
            } else {
                showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text));
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$74(final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$73(runnable, tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$73(Runnable runnable, TLObject tLObject) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLObject instanceof TLRPC$TL_help_deepLinkInfo) {
            TLRPC$TL_help_deepLinkInfo tLRPC$TL_help_deepLinkInfo = (TLRPC$TL_help_deepLinkInfo) tLObject;
            AlertsCreator.showUpdateAppAlert(this, tLRPC$TL_help_deepLinkInfo.message, tLRPC$TL_help_deepLinkInfo.update_app);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$76(final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$75(runnable, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$75(Runnable runnable, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLObject instanceof TLRPC$TL_langPackLanguage) {
            showAlertDialog(AlertsCreator.createLanguageAlert(this, (TLRPC$TL_langPackLanguage) tLObject));
            return;
        }
        if (tLRPC$TL_error != null) {
            if ("LANG_CODE_NOT_SUPPORTED".equals(tLRPC$TL_error.text)) {
                showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("LanguageUnsupportedError", R.string.LanguageUnsupportedError)));
                return;
            }
            showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$79(final Runnable runnable, final TLRPC$TL_wallPaper tLRPC$TL_wallPaper, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$78(runnable, tLObject, tLRPC$TL_wallPaper, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$runLinkRequest$78(Runnable runnable, TLObject tLObject, TLRPC$TL_wallPaper tLRPC$TL_wallPaper, TLRPC$TL_error tLRPC$TL_error) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (tLObject instanceof TLRPC$TL_wallPaper) {
            TLRPC$TL_wallPaper tLRPC$TL_wallPaper2 = (TLRPC$TL_wallPaper) tLObject;
            if (tLRPC$TL_wallPaper2.pattern) {
                String str = tLRPC$TL_wallPaper2.slug;
                TLRPC$WallPaperSettings tLRPC$WallPaperSettings = tLRPC$TL_wallPaper.settings;
                WallpapersListActivity.ColorWallpaper colorWallpaper = new WallpapersListActivity.ColorWallpaper(str, tLRPC$WallPaperSettings.background_color, tLRPC$WallPaperSettings.second_background_color, tLRPC$WallPaperSettings.third_background_color, tLRPC$WallPaperSettings.fourth_background_color, AndroidUtilities.getWallpaperRotation(tLRPC$WallPaperSettings.rotation, false), r0.intensity / 100.0f, tLRPC$TL_wallPaper.settings.motion, null);
                colorWallpaper.pattern = tLRPC$TL_wallPaper2;
                tLRPC$TL_wallPaper2 = colorWallpaper;
            }
            ThemePreviewActivity themePreviewActivity = new ThemePreviewActivity(tLRPC$TL_wallPaper2, null, true, false);
            TLRPC$WallPaperSettings tLRPC$WallPaperSettings2 = tLRPC$TL_wallPaper.settings;
            themePreviewActivity.setInitialModes(tLRPC$WallPaperSettings2.blur, tLRPC$WallPaperSettings2.motion, tLRPC$WallPaperSettings2.intensity);
            lambda$runLinkRequest$77(themePreviewActivity);
            return;
        }
        showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$80(Browser.Progress progress) {
        this.loadingThemeFileName = null;
        this.loadingThemeWallpaperName = null;
        this.loadingThemeWallpaper = null;
        this.loadingThemeInfo = null;
        this.loadingThemeProgressDialog = null;
        this.loadingTheme = null;
        if (progress != null) {
            progress.end();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$82(final AlertDialog alertDialog, final Runnable runnable, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda68
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$81(tLObject, alertDialog, runnable, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$81(TLObject tLObject, AlertDialog alertDialog, Runnable runnable, TLRPC$TL_error tLRPC$TL_error) {
        char c;
        if (tLObject instanceof TLRPC$TL_theme) {
            TLRPC$TL_theme tLRPC$TL_theme = (TLRPC$TL_theme) tLObject;
            TLRPC$TL_wallPaper tLRPC$TL_wallPaper = null;
            c = 0;
            TLRPC$ThemeSettings tLRPC$ThemeSettings = tLRPC$TL_theme.settings.size() > 0 ? tLRPC$TL_theme.settings.get(0) : null;
            if (tLRPC$ThemeSettings != null) {
                Theme.ThemeInfo theme = Theme.getTheme(Theme.getBaseThemeKey(tLRPC$ThemeSettings));
                if (theme != null) {
                    TLRPC$WallPaper tLRPC$WallPaper = tLRPC$ThemeSettings.wallpaper;
                    if (tLRPC$WallPaper instanceof TLRPC$TL_wallPaper) {
                        tLRPC$TL_wallPaper = (TLRPC$TL_wallPaper) tLRPC$WallPaper;
                        if (!FileLoader.getInstance(this.currentAccount).getPathToAttach(tLRPC$TL_wallPaper.document, true).exists()) {
                            this.loadingThemeProgressDialog = alertDialog;
                            this.loadingThemeAccent = true;
                            this.loadingThemeInfo = theme;
                            this.loadingTheme = tLRPC$TL_theme;
                            this.loadingThemeWallpaper = tLRPC$TL_wallPaper;
                            this.loadingThemeWallpaperName = FileLoader.getAttachFileName(tLRPC$TL_wallPaper.document);
                            FileLoader.getInstance(this.currentAccount).loadFile(tLRPC$TL_wallPaper.document, tLRPC$TL_wallPaper, 1, 1);
                            return;
                        }
                    }
                    try {
                        runnable.run();
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    openThemeAccentPreview(tLRPC$TL_theme, tLRPC$TL_wallPaper, theme);
                }
                c = 1;
            } else {
                TLRPC$Document tLRPC$Document = tLRPC$TL_theme.document;
                if (tLRPC$Document != null) {
                    this.loadingThemeAccent = false;
                    this.loadingTheme = tLRPC$TL_theme;
                    this.loadingThemeFileName = FileLoader.getAttachFileName(tLRPC$Document);
                    this.loadingThemeProgressDialog = alertDialog;
                    FileLoader.getInstance(this.currentAccount).loadFile(this.loadingTheme.document, tLRPC$TL_theme, 1, 1);
                }
                c = 1;
            }
        } else {
            if (tLRPC$TL_error == null || !"THEME_FORMAT_INVALID".equals(tLRPC$TL_error.text)) {
                c = 2;
            }
            c = 1;
        }
        if (c != 0) {
            try {
                runnable.run();
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            if (c == 1) {
                showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("Theme", R.string.Theme), LocaleController.getString("ThemeNotSupported", R.string.ThemeNotSupported)));
            } else {
                showAlertDialog(AlertsCreator.createSimpleAlert(this, LocaleController.getString("Theme", R.string.Theme), LocaleController.getString("ThemeNotFound", R.string.ThemeNotFound)));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$84(final int[] iArr, final int i, final Runnable runnable, final Integer num, final Integer num2, final Integer num3, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda70
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$83(tLObject, iArr, i, runnable, num, num2, num3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0037 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$runLinkRequest$83(org.telegram.tgnet.TLObject r11, int[] r12, int r13, java.lang.Runnable r14, java.lang.Integer r15, java.lang.Integer r16, java.lang.Integer r17) {
        /*
            r10 = this;
            r8 = r10
            r0 = r11
            boolean r1 = r0 instanceof org.telegram.tgnet.TLRPC$TL_messages_chats
            r9 = 0
            if (r1 == 0) goto L34
            org.telegram.tgnet.TLRPC$TL_messages_chats r0 = (org.telegram.tgnet.TLRPC$TL_messages_chats) r0
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r1 = r0.chats
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L34
            int r1 = r8.currentAccount
            org.telegram.messenger.MessagesController r1 = org.telegram.messenger.MessagesController.getInstance(r1)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r2 = r0.chats
            r1.putChats(r2, r9)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Chat> r0 = r0.chats
            java.lang.Object r0 = r0.get(r9)
            r7 = r0
            org.telegram.tgnet.TLRPC$Chat r7 = (org.telegram.tgnet.TLRPC$Chat) r7
            r1 = r10
            r2 = r13
            r3 = r14
            r4 = r15
            r5 = r16
            r6 = r17
            int r0 = r1.runCommentRequest(r2, r3, r4, r5, r6, r7)
            r12[r9] = r0
            goto L35
        L34:
            r9 = 1
        L35:
            if (r9 == 0) goto L54
            r14.run()     // Catch: java.lang.Exception -> L3b
            goto L40
        L3b:
            r0 = move-exception
            r1 = r0
            org.telegram.messenger.FileLog.e(r1)
        L40:
            int r0 = org.telegram.messenger.R.string.DialogNotAvailable
            java.lang.String r0 = org.telegram.messenger.LocaleController.getString(r0)
            int r1 = org.telegram.messenger.R.string.LinkNotFound
            java.lang.String r1 = org.telegram.messenger.LocaleController.getString(r1)
            r2 = 0
            org.telegram.ui.ActionBar.AlertDialog$Builder r0 = org.telegram.ui.Components.AlertsCreator.createNoAccessAlert(r10, r0, r1, r2)
            r10.showAlertDialog(r0)
        L54:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.lambda$runLinkRequest$83(org.telegram.tgnet.TLObject, int[], int, java.lang.Runnable, java.lang.Integer, java.lang.Integer, java.lang.Integer):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$85(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$88(final Bundle bundle, final Long l, int[] iArr, final Runnable runnable, final Integer num, final Integer num2, final BaseFragment baseFragment, final int i) {
        if (this.actionBarLayout.presentFragment(new ChatActivity(bundle))) {
            return;
        }
        TLRPC$TL_channels_getChannels tLRPC$TL_channels_getChannels = new TLRPC$TL_channels_getChannels();
        TLRPC$TL_inputChannel tLRPC$TL_inputChannel = new TLRPC$TL_inputChannel();
        tLRPC$TL_inputChannel.channel_id = l.longValue();
        tLRPC$TL_channels_getChannels.id.add(tLRPC$TL_inputChannel);
        iArr[0] = ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_channels_getChannels, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda101
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LaunchActivity.this.lambda$runLinkRequest$87(runnable, num, l, num2, baseFragment, i, bundle, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$87(final Runnable runnable, final Integer num, final Long l, final Integer num2, final BaseFragment baseFragment, final int i, final Bundle bundle, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$runLinkRequest$86(runnable, tLObject, num, l, num2, baseFragment, i, bundle);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$runLinkRequest$86(Runnable runnable, TLObject tLObject, Integer num, Long l, Integer num2, BaseFragment baseFragment, int i, Bundle bundle) {
        try {
            runnable.run();
        } catch (Exception e) {
            FileLog.e(e);
        }
        boolean z = true;
        if (tLObject instanceof TLRPC$TL_messages_chats) {
            TLRPC$TL_messages_chats tLRPC$TL_messages_chats = (TLRPC$TL_messages_chats) tLObject;
            if (!tLRPC$TL_messages_chats.chats.isEmpty()) {
                MessagesController.getInstance(this.currentAccount).putChats(tLRPC$TL_messages_chats.chats, false);
                TLRPC$Chat tLRPC$Chat = tLRPC$TL_messages_chats.chats.get(0);
                if (tLRPC$Chat != null && tLRPC$Chat.forum) {
                    if (num != null) {
                        openForumFromLink(-l.longValue(), num.intValue(), num2, null);
                    } else {
                        openForumFromLink(-l.longValue(), num2.intValue(), null, null);
                    }
                }
                if (baseFragment == null || MessagesController.getInstance(i).checkCanOpenChat(bundle, baseFragment)) {
                    this.actionBarLayout.presentFragment(new ChatActivity(bundle));
                }
                z = false;
            }
        }
        if (z) {
            showAlertDialog(AlertsCreator.createNoAccessAlert(this, LocaleController.getString(R.string.DialogNotAvailable), LocaleController.getString(R.string.LinkNotFound), null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$89(int i, int[] iArr, Runnable runnable, DialogInterface dialogInterface) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runLinkRequest$90(int i, int[] iArr, Runnable runnable) {
        ConnectionsManager.getInstance(i).cancelRequest(iArr[0], true);
        if (runnable != null) {
            runnable.run();
        }
    }

    private void openForumFromLink(final long j, int i, final Integer num, final Runnable runnable) {
        if (num == null) {
            Bundle bundle = new Bundle();
            bundle.putLong("chat_id", -j);
            lambda$runLinkRequest$77(new TopicsFragment(bundle));
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        TLRPC$TL_channels_getMessages tLRPC$TL_channels_getMessages = new TLRPC$TL_channels_getMessages();
        tLRPC$TL_channels_getMessages.channel = MessagesController.getInstance(this.currentAccount).getInputChannel(-j);
        tLRPC$TL_channels_getMessages.id.add(num);
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_channels_getMessages, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda97
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LaunchActivity.this.lambda$openForumFromLink$92(num, j, runnable, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openForumFromLink$92(final Integer num, final long j, final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda67
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$openForumFromLink$91(tLObject, num, j, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openForumFromLink$91(TLObject tLObject, Integer num, long j, Runnable runnable) {
        TLRPC$Message tLRPC$Message;
        if (tLObject instanceof TLRPC$messages_Messages) {
            ArrayList<TLRPC$Message> arrayList = ((TLRPC$messages_Messages) tLObject).messages;
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i) != null && arrayList.get(i).id == num.intValue()) {
                    tLRPC$Message = arrayList.get(i);
                    break;
                }
            }
        }
        tLRPC$Message = null;
        if (tLRPC$Message != null) {
            runCommentRequest(this.currentAccount, null, Integer.valueOf(tLRPC$Message.id), null, Integer.valueOf(MessageObject.getTopicId(tLRPC$Message, MessagesController.getInstance(this.currentAccount).isForum(tLRPC$Message))), MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j)), runnable);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putLong("chat_id", -j);
        lambda$runLinkRequest$77(new TopicsFragment(bundle));
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<org.telegram.tgnet.TLRPC$TL_contact> findContacts(java.lang.String r18, java.lang.String r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.findContacts(java.lang.String, java.lang.String, boolean):java.util.List");
    }

    private void createUpdateUI() {
        if (this.sideMenuContainer == null) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(this) { // from class: org.telegram.ui.LaunchActivity.23
            private int lastGradientWidth;
            private LinearGradient updateGradient;
            private Paint paint = new Paint();
            private Matrix matrix = new Matrix();

            @Override // android.view.View
            public void draw(Canvas canvas) {
                if (this.updateGradient != null) {
                    this.paint.setColor(-1);
                    this.paint.setShader(this.updateGradient);
                    this.updateGradient.setLocalMatrix(this.matrix);
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.paint);
                    LaunchActivity.this.updateLayoutIcon.setBackgroundGradientDrawable(this.updateGradient);
                    LaunchActivity.this.updateLayoutIcon.draw(canvas);
                }
                super.draw(canvas);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                int size = View.MeasureSpec.getSize(i);
                if (this.lastGradientWidth != size) {
                    this.updateGradient = new LinearGradient(0.0f, 0.0f, size, 0.0f, new int[]{-9846926, -11291731}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
                    this.lastGradientWidth = size;
                }
            }
        };
        this.updateLayout = frameLayout;
        frameLayout.setWillNotDraw(false);
        this.updateLayout.setVisibility(4);
        this.updateLayout.setTranslationY(AndroidUtilities.dp(44.0f));
        if (Build.VERSION.SDK_INT >= 21) {
            this.updateLayout.setBackground(Theme.getSelectorDrawable(1090519039, false));
        }
        this.sideMenuContainer.addView(this.updateLayout, LayoutHelper.createFrame(-1, 44, 83));
        this.updateLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LaunchActivity.this.lambda$createUpdateUI$93(view);
            }
        });
        RadialProgress2 radialProgress2 = new RadialProgress2(this.updateLayout);
        this.updateLayoutIcon = radialProgress2;
        radialProgress2.setColors(-1, -1, -1, -1);
        this.updateLayoutIcon.setProgressRect(AndroidUtilities.dp(22.0f), AndroidUtilities.dp(11.0f), AndroidUtilities.dp(44.0f), AndroidUtilities.dp(33.0f));
        this.updateLayoutIcon.setCircleRadius(AndroidUtilities.dp(11.0f));
        this.updateLayoutIcon.setAsMini();
        SimpleTextView simpleTextView = new SimpleTextView(this);
        this.updateTextView = simpleTextView;
        simpleTextView.setTextSize(15);
        this.updateTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.updateTextView.setText(LocaleController.getString("AppUpdate", R.string.AppUpdate));
        this.updateTextView.setTextColor(-1);
        this.updateTextView.setGravity(3);
        this.updateLayout.addView(this.updateTextView, LayoutHelper.createFrame(-2, -2.0f, 16, 74.0f, 0.0f, 0.0f, 0.0f));
        TextView textView = new TextView(this);
        this.updateSizeTextView = textView;
        textView.setTextSize(1, 15.0f);
        this.updateSizeTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.updateSizeTextView.setGravity(5);
        this.updateSizeTextView.setTextColor(-1);
        this.updateLayout.addView(this.updateSizeTextView, LayoutHelper.createFrame(-2, -2.0f, 21, 0.0f, 0.0f, 17.0f, 0.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createUpdateUI$93(View view) {
        if (SharedConfig.isAppUpdateAvailable()) {
            if (this.updateLayoutIcon.getIcon() == 2) {
                FileLoader.getInstance(this.currentAccount).loadFile(SharedConfig.pendingAppUpdate.document, "update", 1, 1);
                updateAppUpdateViews(true);
            } else if (this.updateLayoutIcon.getIcon() == 3) {
                FileLoader.getInstance(this.currentAccount).cancelLoadFile(SharedConfig.pendingAppUpdate.document);
                updateAppUpdateViews(true);
            } else {
                AndroidUtilities.openForView(SharedConfig.pendingAppUpdate.document, true, (Activity) this);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0130 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateAppUpdateViews(boolean r14) {
        /*
            Method dump skipped, instructions count: 462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.updateAppUpdateViews(boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateAppUpdateViews$94(View view) {
        if (view != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    public void checkAppUpdate(boolean z) {
        if (z || !BuildVars.DEBUG_VERSION) {
            if (z || BuildVars.CHECK_UPDATES) {
                if (z || Math.abs(System.currentTimeMillis() - SharedConfig.lastUpdateCheckTime) >= MessagesController.getInstance(0).updateCheckDelay * 1000) {
                    TLRPC$TL_help_getAppUpdate tLRPC$TL_help_getAppUpdate = new TLRPC$TL_help_getAppUpdate();
                    try {
                        tLRPC$TL_help_getAppUpdate.source = ApplicationLoader.applicationContext.getPackageManager().getInstallerPackageName(ApplicationLoader.applicationContext.getPackageName());
                    } catch (Exception unused) {
                    }
                    if (tLRPC$TL_help_getAppUpdate.source == null) {
                        tLRPC$TL_help_getAppUpdate.source = "";
                    }
                    final int i = this.currentAccount;
                    ConnectionsManager.getInstance(i).sendRequest(tLRPC$TL_help_getAppUpdate, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda88
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            LaunchActivity.this.lambda$checkAppUpdate$96(i, tLObject, tLRPC$TL_error);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAppUpdate$96(final int i, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        SharedConfig.lastUpdateCheckTime = System.currentTimeMillis();
        SharedConfig.saveConfig();
        if (tLObject instanceof TLRPC$TL_help_appUpdate) {
            final TLRPC$TL_help_appUpdate tLRPC$TL_help_appUpdate = (TLRPC$TL_help_appUpdate) tLObject;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda74
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$checkAppUpdate$95(tLRPC$TL_help_appUpdate, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAppUpdate$95(TLRPC$TL_help_appUpdate tLRPC$TL_help_appUpdate, int i) {
        TLRPC$TL_help_appUpdate tLRPC$TL_help_appUpdate2 = SharedConfig.pendingAppUpdate;
        if ((tLRPC$TL_help_appUpdate2 == null || !tLRPC$TL_help_appUpdate2.version.equals(tLRPC$TL_help_appUpdate.version)) && SharedConfig.setNewAppVersionAvailable(tLRPC$TL_help_appUpdate)) {
            if (tLRPC$TL_help_appUpdate.can_not_skip) {
                showUpdateActivity(i, tLRPC$TL_help_appUpdate, false);
            } else {
                this.drawerLayoutAdapter.notifyDataSetChanged();
                try {
                    new UpdateAppAlertDialog(this, tLRPC$TL_help_appUpdate, i).show();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.appUpdateAvailable, new Object[0]);
        }
    }

    public AlertDialog showAlertDialog(AlertDialog.Builder builder) {
        try {
            AlertDialog alertDialog = this.visibleDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            AlertDialog show = builder.show();
            this.visibleDialog = show;
            show.setCanceledOnTouchOutside(true);
            this.visibleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda14
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LaunchActivity.this.lambda$showAlertDialog$97(dialogInterface);
                }
            });
            return this.visibleDialog;
        } catch (Exception e2) {
            FileLog.e(e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAlertDialog$97(DialogInterface dialogInterface) {
        AlertDialog alertDialog = this.visibleDialog;
        if (alertDialog != null) {
            if (alertDialog == this.localeDialog) {
                INavigationLayout iNavigationLayout = this.actionBarLayout;
                BaseFragment lastFragment = iNavigationLayout == null ? null : iNavigationLayout.getLastFragment();
                try {
                    String str = LocaleController.getInstance().getCurrentLocaleInfo().shortName;
                    if (lastFragment != null) {
                        BulletinFactory.of(lastFragment).createSimpleBulletin(R.raw.msg_translate, getStringForLanguageAlert(str.equals("en") ? this.englishLocaleStrings : this.systemLocaleStrings, "ChangeLanguageLater", R.string.ChangeLanguageLater)).setDuration(5000).show();
                    } else {
                        BulletinFactory.of(Bulletin.BulletinWindow.make(this), null).createSimpleBulletin(R.raw.msg_translate, getStringForLanguageAlert(str.equals("en") ? this.englishLocaleStrings : this.systemLocaleStrings, "ChangeLanguageLater", R.string.ChangeLanguageLater)).setDuration(5000).show();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                this.localeDialog = null;
            } else if (alertDialog == this.proxyErrorDialog) {
                MessagesController.getGlobalMainSettings();
                SharedPreferences.Editor edit = MessagesController.getGlobalMainSettings().edit();
                edit.putBoolean("proxy_enabled", false);
                edit.putBoolean("proxy_enabled_calls", false);
                edit.commit();
                ConnectionsManager.setProxySettings(false, "", 1080, "", "", "");
                NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.proxySettingsChanged, new Object[0]);
                this.proxyErrorDialog = null;
            }
        }
        this.visibleDialog = null;
    }

    public void showBulletin(Function<BulletinFactory, Bulletin> function) {
        BaseFragment baseFragment;
        if (!layerFragmentsStack.isEmpty()) {
            baseFragment = layerFragmentsStack.get(r0.size() - 1);
        } else if (!rightFragmentsStack.isEmpty()) {
            baseFragment = rightFragmentsStack.get(r0.size() - 1);
        } else if (mainFragmentsStack.isEmpty()) {
            baseFragment = null;
        } else {
            baseFragment = mainFragmentsStack.get(r0.size() - 1);
        }
        if (BulletinFactory.canShowBulletin(baseFragment)) {
            function.apply(BulletinFactory.of(baseFragment)).show();
        }
    }

    public void setNavigateToPremiumBot(boolean z) {
        this.navigateToPremiumBot = z;
    }

    public void setNavigateToPremiumGiftCallback(Runnable runnable) {
        this.navigateToPremiumGiftCallback = runnable;
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent, true, false, false);
    }

    public void onNewIntent(Intent intent, Browser.Progress progress) {
        super.onNewIntent(intent);
        lambda$handleIntent$15(intent, true, false, false, progress);
    }

    @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
    public boolean didSelectDialogs(final DialogsActivity dialogsActivity, final ArrayList<MessagesStorage.TopicKey> arrayList, final CharSequence charSequence, final boolean z, TopicsFragment topicsFragment) {
        ChatActivity chatActivity;
        boolean z2;
        ChatActivity chatActivity2;
        MessageObject messageObject;
        int i;
        long j;
        ChatActivity chatActivity3;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        ArrayList<SendMessagesHelper.SendingMediaInfo> arrayList2;
        TLRPC$TL_forumTopic findTopic;
        final int currentAccount = dialogsActivity != null ? dialogsActivity.getCurrentAccount() : this.currentAccount;
        final Uri uri = this.exportingChatUri;
        if (uri != null) {
            final ArrayList arrayList3 = this.documentsUrisArray != null ? new ArrayList(this.documentsUrisArray) : null;
            final AlertDialog alertDialog = new AlertDialog(this, 3);
            SendMessagesHelper.getInstance(currentAccount).prepareImportHistory(arrayList.get(0).dialogId, this.exportingChatUri, this.documentsUrisArray, new MessagesStorage.LongCallback() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda84
                @Override // org.telegram.messenger.MessagesStorage.LongCallback
                public final void run(long j2) {
                    LaunchActivity.this.lambda$didSelectDialogs$98(currentAccount, dialogsActivity, z, arrayList3, uri, alertDialog, j2);
                }
            });
            try {
                alertDialog.showDelayed(300L);
            } catch (Exception unused) {
            }
        } else {
            final boolean z7 = dialogsActivity == null || dialogsActivity.notify;
            if (arrayList.size() <= 1) {
                long j2 = arrayList.get(0).dialogId;
                Bundle bundle = new Bundle();
                bundle.putBoolean("scrollToTopOnResume", true);
                if (!AndroidUtilities.isTablet()) {
                    NotificationCenter.getInstance(currentAccount).postNotificationName(NotificationCenter.closeChats, new Object[0]);
                }
                if (DialogObject.isEncryptedDialog(j2)) {
                    bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j2));
                } else if (DialogObject.isUserDialog(j2)) {
                    bundle.putLong("user_id", j2);
                } else {
                    bundle.putLong("chat_id", -j2);
                }
                if (!MessagesController.getInstance(currentAccount).checkCanOpenChat(bundle, dialogsActivity)) {
                    return false;
                }
                ChatActivity chatActivity4 = new ChatActivity(bundle);
                ForumUtilities.applyTopic(chatActivity4, arrayList.get(0));
                chatActivity = chatActivity4;
            } else {
                chatActivity = null;
            }
            ArrayList<TLRPC$User> arrayList4 = this.contactsToSend;
            int size = arrayList4 != null ? arrayList4.size() + 0 : 0;
            if (this.videoPath != null) {
                size++;
            }
            if (this.voicePath != null) {
                size++;
            }
            ArrayList<SendMessagesHelper.SendingMediaInfo> arrayList5 = this.photoPathsArray;
            if (arrayList5 != null) {
                size += arrayList5.size();
            }
            ArrayList<String> arrayList6 = this.documentsPathsArray;
            if (arrayList6 != null) {
                size += arrayList6.size();
            }
            ArrayList<Uri> arrayList7 = this.documentsUrisArray;
            if (arrayList7 != null) {
                size += arrayList7.size();
            }
            if (this.videoPath == null && this.voicePath == null && this.photoPathsArray == null && this.documentsPathsArray == null && this.documentsUrisArray == null && this.sendingText != null) {
                size++;
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (AlertsCreator.checkSlowMode(this, this.currentAccount, arrayList.get(i2).dialogId, size > 1)) {
                    return false;
                }
            }
            if (topicsFragment != null) {
                topicsFragment.removeSelfFromStack();
            }
            ArrayList<TLRPC$User> arrayList8 = this.contactsToSend;
            if (arrayList8 != null && arrayList8.size() == 1 && !mainFragmentsStack.isEmpty()) {
                ArrayList<BaseFragment> arrayList9 = mainFragmentsStack;
                PhonebookShareAlert phonebookShareAlert = new PhonebookShareAlert(arrayList9.get(arrayList9.size() - 1), null, null, this.contactsToSendUri, null, null, null);
                final ChatActivity chatActivity5 = chatActivity;
                phonebookShareAlert.setDelegate(new ChatAttachAlertContactsLayout.PhonebookShareAlertDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda114
                    @Override // org.telegram.ui.Components.ChatAttachAlertContactsLayout.PhonebookShareAlertDelegate
                    public final void didSelectContact(TLRPC$User tLRPC$User, boolean z8, int i3) {
                        LaunchActivity.this.lambda$didSelectDialogs$99(chatActivity5, arrayList, currentAccount, charSequence, z7, tLRPC$User, z8, i3);
                    }
                });
                ArrayList<BaseFragment> arrayList10 = mainFragmentsStack;
                arrayList10.get(arrayList10.size() - 1).showDialog(phonebookShareAlert);
                chatActivity2 = chatActivity;
                z2 = true;
            } else {
                int i3 = 0;
                z2 = false;
                String str = null;
                while (i3 < arrayList.size()) {
                    long j3 = arrayList.get(i3).dialogId;
                    int i4 = arrayList.get(i3).topicId;
                    AccountInstance accountInstance = AccountInstance.getInstance(UserConfig.selectedAccount);
                    if (i4 == 0 || (findTopic = accountInstance.getMessagesController().getTopicsController().findTopic(-j3, i4)) == null || findTopic.topicStartMessage == null) {
                        messageObject = null;
                    } else {
                        messageObject = new MessageObject(accountInstance.getCurrentAccount(), findTopic.topicStartMessage, false, false);
                        messageObject.isTopicMainMessage = true;
                    }
                    if (chatActivity != null) {
                        i = i4;
                        j = j3;
                        boolean z8 = dialogsActivity == null || this.videoPath != null || ((arrayList2 = this.photoPathsArray) != null && arrayList2.size() > 0);
                        ChatActivity chatActivity6 = chatActivity;
                        this.actionBarLayout.presentFragment(chatActivity, dialogsActivity != null, z8, true, false);
                        boolean z9 = dialogsActivity != null;
                        String str2 = this.videoPath;
                        if (str2 != null && i == 0) {
                            chatActivity6.openVideoEditor(str2, this.sendingText);
                            this.sendingText = null;
                            z5 = false;
                            z6 = true;
                        } else {
                            ArrayList<SendMessagesHelper.SendingMediaInfo> arrayList11 = this.photoPathsArray;
                            if (arrayList11 == null || arrayList11.size() <= 0 || i != 0) {
                                z5 = false;
                            } else {
                                z5 = chatActivity6.openPhotosEditor(this.photoPathsArray, (charSequence == null || charSequence.length() == 0) ? this.sendingText : charSequence);
                                if (z5) {
                                    this.sendingText = null;
                                }
                            }
                            z6 = false;
                        }
                        chatActivity3 = chatActivity6;
                        z2 = z9;
                        z3 = z5;
                        z4 = z6;
                    } else {
                        i = i4;
                        j = j3;
                        chatActivity3 = chatActivity;
                        if (this.videoPath != null) {
                            String str3 = this.sendingText;
                            if (str3 != null && str3.length() <= 1024) {
                                str = this.sendingText;
                                this.sendingText = null;
                            }
                            ArrayList arrayList12 = new ArrayList();
                            arrayList12.add(this.videoPath);
                            SendMessagesHelper.prepareSendingDocuments(accountInstance, arrayList12, arrayList12, null, str, null, j, messageObject, messageObject, null, null, z7, 0);
                        }
                        z3 = false;
                        z4 = false;
                    }
                    if (this.photoPathsArray != null && !z3) {
                        String str4 = this.sendingText;
                        if (str4 != null && str4.length() <= 1024 && this.photoPathsArray.size() == 1) {
                            this.photoPathsArray.get(0).caption = this.sendingText;
                            this.sendingText = null;
                        }
                        SendMessagesHelper.prepareSendingMedia(accountInstance, this.photoPathsArray, j, messageObject, messageObject, null, false, false, null, z7, 0, false);
                    }
                    if (this.documentsPathsArray != null || this.documentsUrisArray != null) {
                        String str5 = this.sendingText;
                        if (str5 != null && str5.length() <= 1024) {
                            ArrayList<String> arrayList13 = this.documentsPathsArray;
                            int size2 = arrayList13 != null ? arrayList13.size() : 0;
                            ArrayList<Uri> arrayList14 = this.documentsUrisArray;
                            if (size2 + (arrayList14 != null ? arrayList14.size() : 0) == 1) {
                                str = this.sendingText;
                                this.sendingText = null;
                            }
                        }
                        SendMessagesHelper.prepareSendingDocuments(accountInstance, this.documentsPathsArray, this.documentsOriginalPathsArray, this.documentsUrisArray, str, this.documentsMimeType, j, messageObject, messageObject, null, null, z7, 0);
                    }
                    if (this.voicePath != null) {
                        File file = new File(this.voicePath);
                        if (file.exists()) {
                            TLRPC$TL_document tLRPC$TL_document = new TLRPC$TL_document();
                            tLRPC$TL_document.file_reference = new byte[0];
                            tLRPC$TL_document.dc_id = LinearLayoutManager.INVALID_OFFSET;
                            tLRPC$TL_document.id = SharedConfig.getLastLocalId();
                            tLRPC$TL_document.user_id = accountInstance.getUserConfig().getClientUserId();
                            tLRPC$TL_document.mime_type = "audio/ogg";
                            tLRPC$TL_document.date = accountInstance.getConnectionsManager().getCurrentTime();
                            tLRPC$TL_document.size = (int) file.length();
                            TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                            tLRPC$TL_documentAttributeAudio.voice = true;
                            byte[] waveform = MediaController.getWaveform(file.getAbsolutePath());
                            tLRPC$TL_documentAttributeAudio.waveform = waveform;
                            if (waveform != null) {
                                tLRPC$TL_documentAttributeAudio.flags |= 4;
                            }
                            tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeAudio);
                            accountInstance.getSendMessagesHelper().sendMessage(tLRPC$TL_document, null, file.getAbsolutePath(), j, messageObject, messageObject, this.sendingText, null, null, null, z7, 0, 0, null, null, false);
                            if (this.sendingText != null) {
                                this.sendingText = null;
                            }
                        }
                    }
                    String str6 = this.sendingText;
                    if (str6 != null) {
                        SendMessagesHelper.prepareSendingText(accountInstance, str6, j, i, z7, 0);
                    }
                    ArrayList<TLRPC$User> arrayList15 = this.contactsToSend;
                    if (arrayList15 != null && !arrayList15.isEmpty()) {
                        for (int i5 = 0; i5 < this.contactsToSend.size(); i5++) {
                            SendMessagesHelper.getInstance(currentAccount).sendMessage(this.contactsToSend.get(i5), j, messageObject, messageObject, (TLRPC$ReplyMarkup) null, (HashMap<String, String>) null, z7, 0);
                        }
                    }
                    if (!TextUtils.isEmpty(charSequence) && !z4 && !z3) {
                        SendMessagesHelper.prepareSendingText(accountInstance, charSequence.toString(), j, i, z7, 0);
                    }
                    i3++;
                    chatActivity = chatActivity3;
                }
                chatActivity2 = chatActivity;
            }
            if (dialogsActivity != null && chatActivity2 == null && !z2) {
                dialogsActivity.finishFragment();
            }
        }
        this.photoPathsArray = null;
        this.videoPath = null;
        this.voicePath = null;
        this.sendingText = null;
        this.documentsPathsArray = null;
        this.documentsOriginalPathsArray = null;
        this.contactsToSend = null;
        this.contactsToSendUri = null;
        this.exportingChatUri = null;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectDialogs$98(int i, DialogsActivity dialogsActivity, boolean z, ArrayList arrayList, Uri uri, AlertDialog alertDialog, long j) {
        if (j != 0) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("scrollToTopOnResume", true);
            if (!AndroidUtilities.isTablet()) {
                NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.closeChats, new Object[0]);
            }
            if (DialogObject.isUserDialog(j)) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            ChatActivity chatActivity = new ChatActivity(bundle);
            chatActivity.setOpenImport();
            this.actionBarLayout.presentFragment(chatActivity, dialogsActivity != null || z, dialogsActivity == null, true, false);
        } else {
            this.documentsUrisArray = arrayList;
            if (arrayList == null) {
                this.documentsUrisArray = new ArrayList<>();
            }
            this.documentsUrisArray.add(0, uri);
            openDialogsToSend(true);
        }
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectDialogs$99(ChatActivity chatActivity, ArrayList arrayList, int i, CharSequence charSequence, boolean z, TLRPC$User tLRPC$User, boolean z2, int i2) {
        TLRPC$TL_forumTopic findTopic;
        if (chatActivity != null) {
            this.actionBarLayout.presentFragment(chatActivity, true, false, true, false);
        }
        AccountInstance accountInstance = AccountInstance.getInstance(UserConfig.selectedAccount);
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            long j = ((MessagesStorage.TopicKey) arrayList.get(i3)).dialogId;
            int i4 = ((MessagesStorage.TopicKey) arrayList.get(i3)).topicId;
            MessageObject messageObject = null;
            if (i4 != 0 && (findTopic = accountInstance.getMessagesController().getTopicsController().findTopic(-j, i4)) != null && findTopic.topicStartMessage != null) {
                messageObject = new MessageObject(accountInstance.getCurrentAccount(), findTopic.topicStartMessage, false, false);
                messageObject.isTopicMainMessage = true;
            }
            MessageObject messageObject2 = messageObject;
            SendMessagesHelper.getInstance(i).sendMessage(tLRPC$User, j, messageObject2, messageObject2, (TLRPC$ReplyMarkup) null, (HashMap<String, String>) null, z2, i2);
            if (!TextUtils.isEmpty(charSequence)) {
                SendMessagesHelper.prepareSendingText(accountInstance, charSequence.toString(), j, z, 0);
            }
        }
    }

    private void onFinish() {
        Runnable runnable = this.lockRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.lockRunnable = null;
        }
        if (this.finished) {
            return;
        }
        this.finished = true;
        int i = this.currentAccount;
        if (i != -1) {
            NotificationCenter.getInstance(i).removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mainUserInfoChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.didUpdateConnectionState);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.wasUnableToFindCurrentLocation);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.openArticle);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.hasNewContactsToImport);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.needShowPlayServicesAlert);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadFailed);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.historyImportProgressChanged);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupCallUpdated);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.stickersImportComplete);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.newSuggestionsAvailable);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserShowLimitReachedDialog);
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        }
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needShowAlert);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetNewWallpapper);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.suggestedLangpack);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.reloadInterface);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetNewTheme);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needSetDayNightTheme);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.needCheckSystemBarColors);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.closeOtherAppActivities);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetPasscode);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.notificationsCountUpdated);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.screenStateChanged);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.showBulletin);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.appUpdateAvailable);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.requestPermissions);
        LiteMode.removeOnPowerSaverAppliedListener(new LaunchActivity$$ExternalSyntheticLambda85(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPowerSaver(boolean z) {
        BaseFragment lastFragment;
        if (Build.VERSION.SDK_INT < 21 || this.actionBarLayout == null || !z || LiteMode.getPowerSaverLevel() >= 100 || (lastFragment = this.actionBarLayout.getLastFragment()) == null || (lastFragment instanceof LiteModeSettingsActivity)) {
            return;
        }
        int batteryLevel = LiteMode.getBatteryLevel();
        BulletinFactory.of(lastFragment).createSimpleBulletin(new BatteryDrawable(batteryLevel / 100.0f, -1, lastFragment.getThemedColor(Theme.key_dialogSwipeRemove), 1.3f), LocaleController.getString("LowPowerEnabledTitle", R.string.LowPowerEnabledTitle), LocaleController.formatString("LowPowerEnabledSubtitle", R.string.LowPowerEnabledSubtitle, String.format("%d%%", Integer.valueOf(batteryLevel))), LocaleController.getString("Disable", R.string.Disable), new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$onPowerSaver$100();
            }
        }).setDuration(5000).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPowerSaver$100() {
        lambda$runLinkRequest$77(new LiteModeSettingsActivity());
    }

    /* renamed from: presentFragment, reason: merged with bridge method [inline-methods] */
    public void lambda$runLinkRequest$77(BaseFragment baseFragment) {
        this.actionBarLayout.presentFragment(baseFragment);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z, boolean z2) {
        return this.actionBarLayout.presentFragment(baseFragment, z, z2, true, false);
    }

    public INavigationLayout getActionBarLayout() {
        return this.actionBarLayout;
    }

    public INavigationLayout getLayersActionBarLayout() {
        return this.layersActionBarLayout;
    }

    public INavigationLayout getRightActionBarLayout() {
        return this.rightActionBarLayout;
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        VoIPService sharedInstance;
        if (SharedConfig.passcodeHash.length() != 0 && SharedConfig.lastPauseTime != 0) {
            SharedConfig.lastPauseTime = 0;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("reset lastPauseTime onActivityResult");
            }
            UserConfig.getInstance(this.currentAccount).saveConfig(false);
        }
        if (i == 105) {
            if (Build.VERSION.SDK_INT >= 23) {
                boolean canDrawOverlays = Settings.canDrawOverlays(this);
                ApplicationLoader.canDrawOverlays = canDrawOverlays;
                if (canDrawOverlays) {
                    GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
                    if (groupCallActivity != null) {
                        groupCallActivity.dismissInternal();
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda36
                        @Override // java.lang.Runnable
                        public final void run() {
                            LaunchActivity.this.lambda$onActivityResult$101();
                        }
                    }, 200L);
                    return;
                }
                return;
            }
            return;
        }
        super.onActivityResult(i, i2, intent);
        if (i == 520) {
            if (i2 != -1 || (sharedInstance = VoIPService.getSharedInstance()) == null) {
                return;
            }
            VideoCapturerDevice.mediaProjectionPermissionResultData = intent;
            sharedInstance.createCaptureDevice(true);
            return;
        }
        if (i == 140) {
            LocationController.getInstance(this.currentAccount).startFusedLocationRequest(i2 == -1);
            return;
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.onActivityResult(i, i2, intent);
        }
        if (this.actionBarLayout.getFragmentStack().size() != 0) {
            this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1).onActivityResultFragment(i, i2, intent);
        }
        if (AndroidUtilities.isTablet()) {
            if (this.rightActionBarLayout.getFragmentStack().size() != 0) {
                this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1).onActivityResultFragment(i, i2, intent);
            }
            if (this.layersActionBarLayout.getFragmentStack().size() != 0) {
                this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1).onActivityResultFragment(i, i2, intent);
            }
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.onActivityResultReceived, Integer.valueOf(i), Integer.valueOf(i2), intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$101() {
        GroupCallPip.clearForce();
        GroupCallPip.updateVisibility(this);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (checkPermissionsResult(i, strArr, iArr)) {
            if (this.actionBarLayout.getFragmentStack().size() != 0) {
                this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
            }
            if (AndroidUtilities.isTablet()) {
                if (this.rightActionBarLayout.getFragmentStack().size() != 0) {
                    this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
                }
                if (this.layersActionBarLayout.getFragmentStack().size() != 0) {
                    this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1).onRequestPermissionsResultFragment(i, strArr, iArr);
                }
            }
            VoIPFragment.onRequestPermissionsResult(i, strArr, iArr);
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.onRequestPermissionResultReceived, Integer.valueOf(i), strArr, iArr);
            if (this.requestedPermissions.get(i, -1) >= 0) {
                int i2 = this.requestedPermissions.get(i, -1);
                this.requestedPermissions.delete(i);
                NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.permissionsGranted, Integer.valueOf(i2));
            }
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        isResumed = false;
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.stopAllHeavyOperations, Integer.valueOf(LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM));
        ApplicationLoader.mainInterfacePaused = true;
        final int i = this.currentAccount;
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.lambda$onPause$102(i);
            }
        });
        onPasscodePause();
        this.actionBarLayout.onPause();
        if (AndroidUtilities.isTablet()) {
            INavigationLayout iNavigationLayout = this.rightActionBarLayout;
            if (iNavigationLayout != null) {
                iNavigationLayout.onPause();
            }
            INavigationLayout iNavigationLayout2 = this.layersActionBarLayout;
            if (iNavigationLayout2 != null) {
                iNavigationLayout2.onPause();
            }
        }
        PasscodeView passcodeView = this.passcodeView;
        if (passcodeView != null) {
            passcodeView.onPause();
        }
        Iterator<PasscodeView> it = this.overlayPasscodeViews.iterator();
        while (it.hasNext()) {
            it.next().onPause();
        }
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(true, false);
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onPause();
        }
        if (VoIPFragment.getInstance() != null) {
            VoIPFragment.onPause();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onPause$102(int i) {
        ApplicationLoader.mainInterfacePausedStageQueue = true;
        ApplicationLoader.mainInterfacePausedStageQueueTime = 0L;
        if (VoIPService.getSharedInstance() == null) {
            MessagesController.getInstance(i).ignoreSetOnline = false;
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        Browser.bindCustomTabsService(this);
        ApplicationLoader.mainInterfaceStopped = false;
        GroupCallPip.updateVisibility(this);
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.onResume();
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        Browser.unbindCustomTabsService(this);
        ApplicationLoader.mainInterfaceStopped = true;
        GroupCallPip.updateVisibility(this);
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.onPause();
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (PhotoViewer.getPipInstance() != null) {
            PhotoViewer.getPipInstance().destroyPhotoViewer();
        }
        if (PhotoViewer.hasInstance()) {
            PhotoViewer.getInstance().destroyPhotoViewer();
        }
        if (SecretMediaViewer.hasInstance()) {
            SecretMediaViewer.getInstance().destroyPhotoViewer();
        }
        if (ArticleViewer.hasInstance()) {
            ArticleViewer.getInstance().destroyArticleViewer();
        }
        if (ContentPreviewViewer.hasInstance()) {
            ContentPreviewViewer.getInstance().destroy();
        }
        GroupCallActivity groupCallActivity = GroupCallActivity.groupCallInstance;
        if (groupCallActivity != null) {
            groupCallActivity.dismissInternal();
        }
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        MediaController.getInstance().setBaseActivity(this, false);
        MediaController.getInstance().setFeedbackView(this.feedbackView, false);
        if (pipRoundVideoView != null) {
            pipRoundVideoView.close(false);
        }
        Theme.destroyResources();
        EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.getInstance();
        if (embedBottomSheet != null) {
            embedBottomSheet.destroy();
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.destroy();
        }
        try {
            AlertDialog alertDialog = this.visibleDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            if (this.onGlobalLayoutListener != null) {
                getWindow().getDecorView().getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        super.onDestroy();
        onFinish();
        FloatingDebugController.onDestroy();
        stopCloud();
    }

    public static int startCloud() {
        if (cloudClink == null) {
            cloudClink = new CloudClink();
        }
        return cloudClink.Start(BuildVars.cloudKey);
    }

    public static void stopCloud() {
        CloudClink cloudClink2 = cloudClink;
        if (cloudClink2 != null) {
            cloudClink2.Stop();
        }
    }

    @Override // android.app.Activity
    protected void onUserLeaveHint() {
        Iterator<Runnable> it = this.onUserLeaveHintListeners.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
        INavigationLayout iNavigationLayout = this.actionBarLayout;
        if (iNavigationLayout != null) {
            iNavigationLayout.onUserLeaveHint();
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        MessageObject playingMessageObject;
        super.onResume();
        isResumed = true;
        Runnable runnable = onResumeStaticCallback;
        if (runnable != null) {
            runnable.run();
            onResumeStaticCallback = null;
        }
        if (Theme.selectedAutoNightType == 3) {
            Theme.checkAutoNightThemeConditions();
        }
        checkWasMutedByAdmin(true);
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.startAllHeavyOperations, Integer.valueOf(LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM));
        MediaController mediaController = MediaController.getInstance();
        ViewGroup view = this.actionBarLayout.getView();
        this.feedbackView = view;
        mediaController.setFeedbackView(view, true);
        ApplicationLoader.mainInterfacePaused = false;
        MessagesController.getInstance(this.currentAccount).sortDialogs(null);
        showLanguageAlert(false);
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda80
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.lambda$onResume$103();
            }
        });
        checkFreeDiscSpace(0);
        MediaController.checkGallery();
        onPasscodeResume();
        PasscodeView passcodeView = this.passcodeView;
        if (passcodeView == null || passcodeView.getVisibility() != 0) {
            this.actionBarLayout.onResume();
            if (AndroidUtilities.isTablet()) {
                INavigationLayout iNavigationLayout = this.rightActionBarLayout;
                if (iNavigationLayout != null) {
                    iNavigationLayout.onResume();
                }
                INavigationLayout iNavigationLayout2 = this.layersActionBarLayout;
                if (iNavigationLayout2 != null) {
                    iNavigationLayout2.onResume();
                }
            }
        } else {
            this.actionBarLayout.dismissDialogs();
            if (AndroidUtilities.isTablet()) {
                INavigationLayout iNavigationLayout3 = this.rightActionBarLayout;
                if (iNavigationLayout3 != null) {
                    iNavigationLayout3.dismissDialogs();
                }
                INavigationLayout iNavigationLayout4 = this.layersActionBarLayout;
                if (iNavigationLayout4 != null) {
                    iNavigationLayout4.dismissDialogs();
                }
            }
            this.passcodeView.onResume();
            Iterator<PasscodeView> it = this.overlayPasscodeViews.iterator();
            while (it.hasNext()) {
                it.next().onResume();
            }
        }
        ConnectionsManager.getInstance(this.currentAccount).setAppPaused(false, false);
        updateCurrentConnectionState(this.currentAccount);
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().onResume();
        }
        if (PipRoundVideoView.getInstance() != null && MediaController.getInstance().isMessagePaused() && (playingMessageObject = MediaController.getInstance().getPlayingMessageObject()) != null) {
            MediaController.getInstance().seekToProgress(playingMessageObject, playingMessageObject.audioProgress);
        }
        if (UserConfig.getInstance(UserConfig.selectedAccount).unacceptedTermsOfService != null) {
            int i = UserConfig.selectedAccount;
            showTosActivity(i, UserConfig.getInstance(i).unacceptedTermsOfService);
        } else {
            TLRPC$TL_help_appUpdate tLRPC$TL_help_appUpdate = SharedConfig.pendingAppUpdate;
            if (tLRPC$TL_help_appUpdate != null && tLRPC$TL_help_appUpdate.can_not_skip) {
                showUpdateActivity(UserConfig.selectedAccount, SharedConfig.pendingAppUpdate, true);
            }
        }
        checkAppUpdate(false);
        if (Build.VERSION.SDK_INT >= 23) {
            ApplicationLoader.canDrawOverlays = Settings.canDrawOverlays(this);
        }
        if (VoIPFragment.getInstance() != null) {
            VoIPFragment.onResume();
        }
        invalidateTabletMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onResume$103() {
        ApplicationLoader.mainInterfacePausedStageQueue = false;
        ApplicationLoader.mainInterfacePausedStageQueueTime = System.currentTimeMillis();
    }

    private void invalidateTabletMode() {
        Boolean wasTablet = AndroidUtilities.getWasTablet();
        if (wasTablet == null) {
            return;
        }
        AndroidUtilities.resetWasTabletFlag();
        if (wasTablet.booleanValue() != AndroidUtilities.isTablet()) {
            int i = 0;
            long j = 0;
            if (wasTablet.booleanValue()) {
                mainFragmentsStack.addAll(rightFragmentsStack);
                mainFragmentsStack.addAll(layerFragmentsStack);
                rightFragmentsStack.clear();
                layerFragmentsStack.clear();
            } else {
                ArrayList<BaseFragment> arrayList = new ArrayList(mainFragmentsStack);
                mainFragmentsStack.clear();
                rightFragmentsStack.clear();
                layerFragmentsStack.clear();
                long j2 = 0;
                for (BaseFragment baseFragment : arrayList) {
                    if (baseFragment instanceof DialogsActivity) {
                        DialogsActivity dialogsActivity = (DialogsActivity) baseFragment;
                        if (dialogsActivity.isMainDialogList() && !dialogsActivity.isArchive()) {
                            mainFragmentsStack.add(baseFragment);
                        }
                    }
                    if (baseFragment instanceof ChatActivity) {
                        ChatActivity chatActivity = (ChatActivity) baseFragment;
                        if (!chatActivity.isInScheduleMode()) {
                            rightFragmentsStack.add(baseFragment);
                            if (j2 == 0) {
                                j2 = chatActivity.getDialogId();
                                i = chatActivity.getTopicId();
                            }
                        }
                    }
                    layerFragmentsStack.add(baseFragment);
                }
                j = j2;
            }
            setupActionBarLayout();
            this.actionBarLayout.rebuildFragments(1);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.rebuildFragments(1);
                this.layersActionBarLayout.rebuildFragments(1);
                Iterator<BaseFragment> it = mainFragmentsStack.iterator();
                while (it.hasNext()) {
                    BaseFragment next = it.next();
                    if (next instanceof DialogsActivity) {
                        DialogsActivity dialogsActivity2 = (DialogsActivity) next;
                        if (dialogsActivity2.isMainDialogList()) {
                            dialogsActivity2.setOpenedDialogId(j, i);
                        }
                    }
                }
            }
        }
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        AndroidUtilities.checkDisplaySize(this, configuration);
        super.onConfigurationChanged(configuration);
        checkLayout();
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        if (pipRoundVideoView != null) {
            pipRoundVideoView.onConfigurationChanged();
        }
        EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.getInstance();
        if (embedBottomSheet != null) {
            embedBottomSheet.onConfigurationChanged(configuration);
        }
        PhotoViewer pipInstance = PhotoViewer.getPipInstance();
        if (pipInstance != null) {
            pipInstance.onConfigurationChanged(configuration);
        }
        ThemeEditorView themeEditorView = ThemeEditorView.getInstance();
        if (themeEditorView != null) {
            themeEditorView.onConfigurationChanged();
        }
        if (Theme.selectedAutoNightType == 3) {
            Theme.checkAutoNightThemeConditions();
        }
    }

    @Override // android.app.Activity
    public void onMultiWindowModeChanged(boolean z) {
        AndroidUtilities.isInMultiwindow = z;
        checkLayout();
    }

    /* JADX WARN: Code restructure failed: missing block: B:138:0x031f, code lost:
    
        if (((org.telegram.ui.ProfileActivity) r1.get(r1.size() - 1)).isSettings() == false) goto L123;
     */
    /* JADX WARN: Removed duplicated region for block: B:137:0x030e  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0683  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0691 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0692  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x068b  */
    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didReceivedNotification(int r24, final int r25, java.lang.Object... r26) {
        /*
            Method dump skipped, instructions count: 2832
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.didReceivedNotification(int, int, java.lang.Object[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$didReceivedNotification$104(int i, DialogInterface dialogInterface, int i2) {
        if (mainFragmentsStack.isEmpty()) {
            return;
        }
        MessagesController messagesController = MessagesController.getInstance(i);
        ArrayList<BaseFragment> arrayList = mainFragmentsStack;
        messagesController.openByUserName("spambot", arrayList.get(arrayList.size() - 1), 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$105(DialogInterface dialogInterface, int i) {
        MessagesController.getInstance(this.currentAccount).performLogout(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$107(final HashMap hashMap, final int i, DialogInterface dialogInterface, int i2) {
        if (mainFragmentsStack.isEmpty()) {
            return;
        }
        if (AndroidUtilities.isMapsInstalled(mainFragmentsStack.get(r3.size() - 1))) {
            LocationActivity locationActivity = new LocationActivity(0);
            locationActivity.setDelegate(new LocationActivity.LocationActivityDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda124
                @Override // org.telegram.ui.LocationActivity.LocationActivityDelegate
                public final void didSelectLocation(TLRPC$MessageMedia tLRPC$MessageMedia, int i3, boolean z, int i4) {
                    LaunchActivity.lambda$didReceivedNotification$106(hashMap, i, tLRPC$MessageMedia, i3, z, i4);
                }
            });
            lambda$runLinkRequest$77(locationActivity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$didReceivedNotification$106(HashMap hashMap, int i, TLRPC$MessageMedia tLRPC$MessageMedia, int i2, boolean z, int i3) {
        Iterator it = hashMap.entrySet().iterator();
        while (it.hasNext()) {
            MessageObject messageObject = (MessageObject) ((Map.Entry) it.next()).getValue();
            SendMessagesHelper.getInstance(i).sendMessage(tLRPC$MessageMedia, messageObject.getDialogId(), messageObject, (MessageObject) null, (TLRPC$ReplyMarkup) null, (HashMap<String, String>) null, z, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$didReceivedNotification$108(int i, HashMap hashMap, boolean z, boolean z2, DialogInterface dialogInterface, int i2) {
        ContactsController.getInstance(i).syncPhoneBookByAlert(hashMap, z, z2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$didReceivedNotification$109(int i, HashMap hashMap, boolean z, boolean z2, DialogInterface dialogInterface, int i2) {
        ContactsController.getInstance(i).syncPhoneBookByAlert(hashMap, z, z2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$didReceivedNotification$110(int i, HashMap hashMap, boolean z, boolean z2, DialogInterface dialogInterface, int i2) {
        ContactsController.getInstance(i).syncPhoneBookByAlert(hashMap, z, z2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$111(ValueAnimator valueAnimator) {
        this.frameLayout.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$112() {
        if (this.isNavigationBarColorFrozen) {
            this.isNavigationBarColorFrozen = false;
            checkSystemBarColors(false, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$114(final Theme.ThemeInfo themeInfo, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda69
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$didReceivedNotification$113(tLObject, themeInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$113(TLObject tLObject, Theme.ThemeInfo themeInfo) {
        if (tLObject instanceof TLRPC$TL_wallPaper) {
            TLRPC$TL_wallPaper tLRPC$TL_wallPaper = (TLRPC$TL_wallPaper) tLObject;
            this.loadingThemeInfo = themeInfo;
            this.loadingThemeWallpaperName = FileLoader.getAttachFileName(tLRPC$TL_wallPaper.document);
            this.loadingThemeWallpaper = tLRPC$TL_wallPaper;
            FileLoader.getInstance(themeInfo.account).loadFile(tLRPC$TL_wallPaper.document, tLRPC$TL_wallPaper, 1, 1);
            return;
        }
        onThemeLoadFinish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$116(Theme.ThemeInfo themeInfo, File file) {
        themeInfo.createBackground(file, themeInfo.pathToWallpaper);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$didReceivedNotification$115();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$115() {
        if (this.loadingTheme == null) {
            return;
        }
        File file = new File(ApplicationLoader.getFilesDirFixed(), "remote" + this.loadingTheme.id + ".attheme");
        TLRPC$TL_theme tLRPC$TL_theme = this.loadingTheme;
        Theme.ThemeInfo applyThemeFile = Theme.applyThemeFile(file, tLRPC$TL_theme.title, tLRPC$TL_theme, true);
        if (applyThemeFile != null) {
            lambda$runLinkRequest$77(new ThemePreviewActivity(applyThemeFile, true, 0, false, false));
        }
        onThemeLoadFinish();
    }

    private void invalidateCachedViews(View view) {
        if (view.getLayerType() != 0) {
            view.invalidate();
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                invalidateCachedViews(viewGroup.getChildAt(i));
            }
        }
    }

    private void checkWasMutedByAdmin(boolean z) {
        ChatObject.Call call;
        long j;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        boolean z2 = false;
        if (sharedInstance != null && (call = sharedInstance.groupCall) != null) {
            boolean z3 = this.wasMutedByAdminRaisedHand;
            TLRPC$InputPeer groupCallPeer = sharedInstance.getGroupCallPeer();
            if (groupCallPeer != null) {
                j = groupCallPeer.user_id;
                if (j == 0) {
                    long j2 = groupCallPeer.chat_id;
                    if (j2 == 0) {
                        j2 = groupCallPeer.channel_id;
                    }
                    j = -j2;
                }
            } else {
                j = UserConfig.getInstance(this.currentAccount).clientUserId;
            }
            TLRPC$TL_groupCallParticipant tLRPC$TL_groupCallParticipant = call.participants.get(j);
            boolean z4 = (tLRPC$TL_groupCallParticipant == null || tLRPC$TL_groupCallParticipant.can_self_unmute || !tLRPC$TL_groupCallParticipant.muted) ? false : true;
            if (z4 && tLRPC$TL_groupCallParticipant.raise_hand_rating != 0) {
                z2 = true;
            }
            this.wasMutedByAdminRaisedHand = z2;
            if (z || !z3 || z2 || z4 || GroupCallActivity.groupCallInstance != null) {
                return;
            }
            showVoiceChatTooltip(38);
            return;
        }
        this.wasMutedByAdminRaisedHand = false;
    }

    private void showVoiceChatTooltip(int i) {
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null || mainFragmentsStack.isEmpty() || sharedInstance.groupCall == null) {
            return;
        }
        TLRPC$Chat chat = sharedInstance.getChat();
        BaseFragment baseFragment = this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1);
        UndoView undoView = null;
        if (baseFragment instanceof ChatActivity) {
            ChatActivity chatActivity = (ChatActivity) baseFragment;
            if (chatActivity.getDialogId() == (-chat.id)) {
                chat = null;
            }
            undoView = chatActivity.getUndoView();
        } else if (baseFragment instanceof DialogsActivity) {
            undoView = ((DialogsActivity) baseFragment).getUndoView();
        } else if (baseFragment instanceof ProfileActivity) {
            undoView = ((ProfileActivity) baseFragment).getUndoView();
        }
        if (undoView != null) {
            undoView.showWithAction(0L, i, chat);
        }
        if (i != 38 || VoIPService.getSharedInstance() == null) {
            return;
        }
        VoIPService.getSharedInstance().playAllowTalkSound();
    }

    private String getStringForLanguageAlert(HashMap<String, String> hashMap, String str, int i) {
        String str2 = hashMap.get(str);
        return str2 == null ? LocaleController.getString(str, i) : str2;
    }

    private void openThemeAccentPreview(TLRPC$TL_theme tLRPC$TL_theme, TLRPC$TL_wallPaper tLRPC$TL_wallPaper, Theme.ThemeInfo themeInfo) {
        int i = themeInfo.lastAccentId;
        Theme.ThemeAccent createNewAccent = themeInfo.createNewAccent(tLRPC$TL_theme, this.currentAccount);
        themeInfo.prevAccentId = themeInfo.currentAccentId;
        themeInfo.setCurrentAccentId(createNewAccent.id);
        createNewAccent.pattern = tLRPC$TL_wallPaper;
        lambda$runLinkRequest$77(new ThemePreviewActivity(themeInfo, i != themeInfo.lastAccentId, 0, false, false));
    }

    private void onThemeLoadFinish() {
        AlertDialog alertDialog = this.loadingThemeProgressDialog;
        if (alertDialog != null) {
            try {
                alertDialog.dismiss();
            } finally {
                this.loadingThemeProgressDialog = null;
            }
        }
        this.loadingThemeWallpaperName = null;
        this.loadingThemeWallpaper = null;
        this.loadingThemeInfo = null;
        this.loadingThemeFileName = null;
        this.loadingTheme = null;
    }

    private void checkFreeDiscSpace(final int i) {
        staticInstanceForAlerts = this;
        AutoDeleteMediaTask.run();
        SharedConfig.checkLogsToDelete();
        if ((Build.VERSION.SDK_INT < 26 || i != 0) && !this.checkFreeDiscSpaceShown) {
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda43
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchActivity.this.lambda$checkFreeDiscSpace$119(i);
                }
            }, 2000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkFreeDiscSpace$119(int i) {
        File directory;
        long availableBlocksLong;
        if (UserConfig.getInstance(this.currentAccount).isClientActivated()) {
            try {
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                if ((((i == 2 || i == 1) && Math.abs(this.alreadyShownFreeDiscSpaceAlertForced - System.currentTimeMillis()) > 240000) || Math.abs(globalMainSettings.getLong("last_space_check", 0L) - System.currentTimeMillis()) >= 259200000) && (directory = FileLoader.getDirectory(4)) != null) {
                    StatFs statFs = new StatFs(directory.getAbsolutePath());
                    if (Build.VERSION.SDK_INT < 18) {
                        availableBlocksLong = Math.abs(statFs.getAvailableBlocks() * statFs.getBlockSize());
                    } else {
                        availableBlocksLong = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
                    }
                    if (i > 0 || availableBlocksLong < 52428800) {
                        if (i > 0) {
                            this.alreadyShownFreeDiscSpaceAlertForced = System.currentTimeMillis();
                        }
                        globalMainSettings.edit().putLong("last_space_check", System.currentTimeMillis()).commit();
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda38
                            @Override // java.lang.Runnable
                            public final void run() {
                                LaunchActivity.this.lambda$checkFreeDiscSpace$118();
                            }
                        });
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkFreeDiscSpace$118() {
        if (this.checkFreeDiscSpaceShown) {
            return;
        }
        try {
            Dialog createFreeSpaceDialog = AlertsCreator.createFreeSpaceDialog(this);
            createFreeSpaceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda15
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LaunchActivity.this.lambda$checkFreeDiscSpace$117(dialogInterface);
                }
            });
            this.checkFreeDiscSpaceShown = true;
            createFreeSpaceDialog.show();
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkFreeDiscSpace$117(DialogInterface dialogInterface) {
        this.checkFreeDiscSpaceShown = false;
    }

    public static void checkFreeDiscSpaceStatic(int i) {
        LaunchActivity launchActivity = staticInstanceForAlerts;
        if (launchActivity != null) {
            launchActivity.checkFreeDiscSpace(i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006a A[Catch: Exception -> 0x011e, TryCatch #0 {Exception -> 0x011e, blocks: (B:3:0x0007, B:5:0x0010, B:9:0x001e, B:12:0x0056, B:15:0x005e, B:18:0x0065, B:20:0x006a, B:23:0x007e, B:27:0x00a0, B:32:0x00bc), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showLanguageAlertInternal(org.telegram.messenger.LocaleController.LocaleInfo r17, org.telegram.messenger.LocaleController.LocaleInfo r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.showLanguageAlertInternal(org.telegram.messenger.LocaleController$LocaleInfo, org.telegram.messenger.LocaleController$LocaleInfo, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showLanguageAlertInternal$120(LocaleController.LocaleInfo[] localeInfoArr, LanguageCell[] languageCellArr, View view) {
        Integer num = (Integer) view.getTag();
        localeInfoArr[0] = ((LanguageCell) view).getCurrentLocale();
        int i = 0;
        while (i < languageCellArr.length) {
            languageCellArr[i].setLanguageSelected(i == num.intValue(), true);
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlertInternal$121(View view) {
        this.localeDialog = null;
        this.drawerLayoutContainer.closeDrawer(true);
        lambda$runLinkRequest$77(new LanguageSelectActivity());
        AlertDialog alertDialog = this.visibleDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.visibleDialog = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlertInternal$122(LocaleController.LocaleInfo[] localeInfoArr, DialogInterface dialogInterface, int i) {
        LocaleController.getInstance().applyLanguage(localeInfoArr[0], true, false, this.currentAccount);
        rebuildAllFragments(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void drawRippleAbove(Canvas canvas, View view) {
        View view2;
        if (view == null || (view2 = this.rippleAbove) == null || view2.getBackground() == null) {
            return;
        }
        if (this.tempLocation == null) {
            this.tempLocation = new int[2];
        }
        this.rippleAbove.getLocationInWindow(this.tempLocation);
        int[] iArr = this.tempLocation;
        int i = iArr[0];
        int i2 = iArr[1];
        view.getLocationInWindow(iArr);
        int[] iArr2 = this.tempLocation;
        int i3 = i - iArr2[0];
        int i4 = i2 - iArr2[1];
        canvas.save();
        canvas.translate(i3, i4);
        this.rippleAbove.getBackground().draw(canvas);
        canvas.restore();
    }

    private void showLanguageAlert(boolean z) {
        String str;
        if (UserConfig.getInstance(this.currentAccount).isClientActivated()) {
            try {
                if (!this.loadingLocaleDialog && !ApplicationLoader.mainInterfacePaused) {
                    String string = MessagesController.getGlobalMainSettings().getString("language_showed2", "");
                    final String str2 = MessagesController.getInstance(this.currentAccount).suggestedLangCode;
                    if (!z && string.equals(str2)) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("alert already showed for " + string);
                            return;
                        }
                        return;
                    }
                    final LocaleController.LocaleInfo[] localeInfoArr = new LocaleController.LocaleInfo[2];
                    String str3 = str2.contains("-") ? str2.split("-")[0] : str2;
                    if ("in".equals(str3)) {
                        str = "id";
                    } else if ("iw".equals(str3)) {
                        str = "he";
                    } else {
                        str = "jw".equals(str3) ? "jv" : null;
                    }
                    for (int i = 0; i < LocaleController.getInstance().languages.size(); i++) {
                        LocaleController.LocaleInfo localeInfo = LocaleController.getInstance().languages.get(i);
                        if (localeInfo.shortName.equals("en")) {
                            localeInfoArr[0] = localeInfo;
                        }
                        if (localeInfo.shortName.replace("_", "-").equals(str2) || localeInfo.shortName.equals(str3) || localeInfo.shortName.equals(str)) {
                            localeInfoArr[1] = localeInfo;
                        }
                        if (localeInfoArr[0] != null && localeInfoArr[1] != null) {
                            break;
                        }
                    }
                    if (localeInfoArr[0] != null && localeInfoArr[1] != null && localeInfoArr[0] != localeInfoArr[1]) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("show lang alert for " + localeInfoArr[0].getKey() + " and " + localeInfoArr[1].getKey());
                        }
                        this.systemLocaleStrings = null;
                        this.englishLocaleStrings = null;
                        this.loadingLocaleDialog = true;
                        TLRPC$TL_langpack_getStrings tLRPC$TL_langpack_getStrings = new TLRPC$TL_langpack_getStrings();
                        tLRPC$TL_langpack_getStrings.lang_code = localeInfoArr[1].getLangCode();
                        tLRPC$TL_langpack_getStrings.keys.add("English");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguage");
                        tLRPC$TL_langpack_getStrings.keys.add("ChooseYourLanguageOther");
                        tLRPC$TL_langpack_getStrings.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_langpack_getStrings, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda111
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                LaunchActivity.this.lambda$showLanguageAlert$124(localeInfoArr, str2, tLObject, tLRPC$TL_error);
                            }
                        }, 8);
                        TLRPC$TL_langpack_getStrings tLRPC$TL_langpack_getStrings2 = new TLRPC$TL_langpack_getStrings();
                        tLRPC$TL_langpack_getStrings2.lang_code = localeInfoArr[0].getLangCode();
                        tLRPC$TL_langpack_getStrings2.keys.add("English");
                        tLRPC$TL_langpack_getStrings2.keys.add("ChooseYourLanguage");
                        tLRPC$TL_langpack_getStrings2.keys.add("ChooseYourLanguageOther");
                        tLRPC$TL_langpack_getStrings2.keys.add("ChangeLanguageLater");
                        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_langpack_getStrings2, new RequestDelegate() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda110
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                LaunchActivity.this.lambda$showLanguageAlert$126(localeInfoArr, str2, tLObject, tLRPC$TL_error);
                            }
                        }, 8);
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlert$124(final LocaleController.LocaleInfo[] localeInfoArr, final String str, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        final HashMap hashMap = new HashMap();
        if (tLObject != null) {
            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
            for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                TLRPC$LangPackString tLRPC$LangPackString = (TLRPC$LangPackString) tLRPC$Vector.objects.get(i);
                hashMap.put(tLRPC$LangPackString.key, tLRPC$LangPackString.value);
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$showLanguageAlert$123(hashMap, localeInfoArr, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlert$123(HashMap hashMap, LocaleController.LocaleInfo[] localeInfoArr, String str) {
        this.systemLocaleStrings = hashMap;
        if (this.englishLocaleStrings == null || hashMap == null) {
            return;
        }
        showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlert$126(final LocaleController.LocaleInfo[] localeInfoArr, final String str, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        final HashMap hashMap = new HashMap();
        if (tLObject != null) {
            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
            for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                TLRPC$LangPackString tLRPC$LangPackString = (TLRPC$LangPackString) tLRPC$Vector.objects.get(i);
                hashMap.put(tLRPC$LangPackString.key, tLRPC$LangPackString.value);
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$showLanguageAlert$125(hashMap, localeInfoArr, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLanguageAlert$125(HashMap hashMap, LocaleController.LocaleInfo[] localeInfoArr, String str) {
        this.englishLocaleStrings = hashMap;
        if (hashMap == null || this.systemLocaleStrings == null) {
            return;
        }
        showLanguageAlertInternal(localeInfoArr[1], localeInfoArr[0], str);
    }

    private void onPasscodePause() {
        if (this.lockRunnable != null) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("cancel lockRunnable onPasscodePause");
            }
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (SharedConfig.passcodeHash.length() != 0) {
            SharedConfig.lastPauseTime = (int) (SystemClock.elapsedRealtime() / 1000);
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.LaunchActivity.27
                @Override // java.lang.Runnable
                public void run() {
                    if (LaunchActivity.this.lockRunnable == this) {
                        if (AndroidUtilities.needShowPasscode(true)) {
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.d("lock app");
                            }
                            LaunchActivity.this.showPasscodeActivity(true, false, -1, -1, null, null);
                            try {
                                NotificationsController.getInstance(UserConfig.selectedAccount).showNotifications();
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        } else if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("didn't pass lock check");
                        }
                        LaunchActivity.this.lockRunnable = null;
                    }
                }
            };
            this.lockRunnable = runnable;
            if (SharedConfig.appLocked) {
                AndroidUtilities.runOnUIThread(runnable, 1000L);
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("schedule app lock in 1000");
                }
            } else if (SharedConfig.autoLockIn != 0) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("schedule app lock in " + ((SharedConfig.autoLockIn * 1000) + 1000));
                }
                AndroidUtilities.runOnUIThread(this.lockRunnable, (SharedConfig.autoLockIn * 1000) + 1000);
            }
        } else {
            SharedConfig.lastPauseTime = 0;
        }
        SharedConfig.saveConfig();
    }

    public void addOverlayPasscodeView(PasscodeView passcodeView) {
        this.overlayPasscodeViews.add(passcodeView);
    }

    public void removeOverlayPasscodeView(PasscodeView passcodeView) {
        this.overlayPasscodeViews.remove(passcodeView);
    }

    private void onPasscodeResume() {
        if (this.lockRunnable != null) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("cancel lockRunnable onPasscodeResume");
            }
            AndroidUtilities.cancelRunOnUIThread(this.lockRunnable);
            this.lockRunnable = null;
        }
        if (AndroidUtilities.needShowPasscode(true)) {
            showPasscodeActivity(true, false, -1, -1, null, null);
        }
        if (SharedConfig.lastPauseTime != 0) {
            SharedConfig.lastPauseTime = 0;
            SharedConfig.saveConfig();
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("reset lastPauseTime onPasscodeResume");
            }
        }
    }

    private void updateCurrentConnectionState(int i) {
        String str;
        if (this.actionBarLayout == null) {
            return;
        }
        int i2 = 0;
        int connectionState = ConnectionsManager.getInstance(this.currentAccount).getConnectionState();
        this.currentConnectionState = connectionState;
        if (connectionState == 2) {
            i2 = R.string.WaitingForNetwork;
            str = "WaitingForNetwork";
        } else if (connectionState == 5) {
            i2 = R.string.Updating;
            str = "Updating";
        } else if (connectionState == 4) {
            i2 = R.string.ConnectingToProxy;
            str = "ConnectingToProxy";
        } else if (connectionState == 1) {
            i2 = R.string.Connecting;
            str = "Connecting";
        } else {
            str = null;
        }
        this.actionBarLayout.setTitleOverlayText(str, i2, (connectionState == 1 || connectionState == 4) ? new Runnable() { // from class: org.telegram.ui.LaunchActivity$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                LaunchActivity.this.lambda$updateCurrentConnectionState$127();
            }
        } : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCurrentConnectionState$127() {
        BaseFragment baseFragment;
        if (AndroidUtilities.isTablet()) {
            if (!layerFragmentsStack.isEmpty()) {
                baseFragment = layerFragmentsStack.get(r0.size() - 1);
            }
            baseFragment = null;
        } else {
            if (!mainFragmentsStack.isEmpty()) {
                baseFragment = mainFragmentsStack.get(r0.size() - 1);
            }
            baseFragment = null;
        }
        if ((baseFragment instanceof ProxyListActivity) || (baseFragment instanceof ProxySettingsActivity)) {
            return;
        }
        lambda$runLinkRequest$77(new ProxyListActivity());
    }

    public void hideVisibleActionMode() {
        ActionMode actionMode = this.visibleActionMode;
        if (actionMode == null) {
            return;
        }
        actionMode.finish();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        try {
            super.onSaveInstanceState(bundle);
            BaseFragment baseFragment = null;
            if (AndroidUtilities.isTablet()) {
                INavigationLayout iNavigationLayout = this.layersActionBarLayout;
                if (iNavigationLayout != null && !iNavigationLayout.getFragmentStack().isEmpty()) {
                    baseFragment = this.layersActionBarLayout.getFragmentStack().get(this.layersActionBarLayout.getFragmentStack().size() - 1);
                } else {
                    INavigationLayout iNavigationLayout2 = this.rightActionBarLayout;
                    if (iNavigationLayout2 != null && !iNavigationLayout2.getFragmentStack().isEmpty()) {
                        baseFragment = this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1);
                    } else if (!this.actionBarLayout.getFragmentStack().isEmpty()) {
                        baseFragment = this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1);
                    }
                }
            } else if (!this.actionBarLayout.getFragmentStack().isEmpty()) {
                baseFragment = this.actionBarLayout.getFragmentStack().get(this.actionBarLayout.getFragmentStack().size() - 1);
            }
            if (baseFragment != null) {
                Bundle arguments = baseFragment.getArguments();
                if ((baseFragment instanceof ChatActivity) && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "chat");
                } else if ((baseFragment instanceof GroupCreateFinalActivity) && arguments != null) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "group");
                } else if (baseFragment instanceof WallpapersListActivity) {
                    bundle.putString("fragment", "wallpapers");
                } else if (baseFragment instanceof ProfileActivity) {
                    ProfileActivity profileActivity = (ProfileActivity) baseFragment;
                    if (profileActivity.isSettings()) {
                        bundle.putString("fragment", "settings");
                    } else if (profileActivity.isChat() && arguments != null) {
                        bundle.putBundle("args", arguments);
                        bundle.putString("fragment", "chat_profile");
                    }
                } else if ((baseFragment instanceof ChannelCreateActivity) && arguments != null && arguments.getInt("step") == 0) {
                    bundle.putBundle("args", arguments);
                    bundle.putString("fragment", "channel");
                }
                baseFragment.saveSelfArgs(bundle);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        if (FloatingDebugController.onBackPressed()) {
            return;
        }
        PasscodeView passcodeView = this.passcodeView;
        if (passcodeView != null && passcodeView.getVisibility() == 0) {
            finish();
            return;
        }
        if (ContentPreviewViewer.hasInstance() && ContentPreviewViewer.getInstance().isVisible()) {
            ContentPreviewViewer.getInstance().closeWithMenu();
        }
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(true, false);
            return;
        }
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(true, false);
            return;
        }
        if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
            ArticleViewer.getInstance().close(true, false);
            return;
        }
        if (this.drawerLayoutContainer.isDrawerOpened()) {
            this.drawerLayoutContainer.closeDrawer(false);
            return;
        }
        if (AndroidUtilities.isTablet()) {
            if (this.layersActionBarLayout.getView().getVisibility() == 0) {
                this.layersActionBarLayout.onBackPressed();
                return;
            }
            if (this.rightActionBarLayout.getView().getVisibility() == 0 && !this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                BaseFragment baseFragment = this.rightActionBarLayout.getFragmentStack().get(this.rightActionBarLayout.getFragmentStack().size() - 1);
                if (baseFragment.onBackPressed()) {
                    baseFragment.finishFragment();
                    return;
                }
                return;
            }
            this.actionBarLayout.onBackPressed();
            return;
        }
        this.actionBarLayout.onBackPressed();
    }

    @Override // android.app.Activity, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        INavigationLayout iNavigationLayout = this.actionBarLayout;
        if (iNavigationLayout != null) {
            iNavigationLayout.onLowMemory();
            if (AndroidUtilities.isTablet()) {
                INavigationLayout iNavigationLayout2 = this.rightActionBarLayout;
                if (iNavigationLayout2 != null) {
                    iNavigationLayout2.onLowMemory();
                }
                INavigationLayout iNavigationLayout3 = this.layersActionBarLayout;
                if (iNavigationLayout3 != null) {
                    iNavigationLayout3.onLowMemory();
                }
            }
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onActionModeStarted(ActionMode actionMode) {
        super.onActionModeStarted(actionMode);
        this.visibleActionMode = actionMode;
        try {
            Menu menu = actionMode.getMenu();
            if (menu != null && !this.actionBarLayout.extendActionMode(menu) && AndroidUtilities.isTablet() && !this.rightActionBarLayout.extendActionMode(menu)) {
                this.layersActionBarLayout.extendActionMode(menu);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (Build.VERSION.SDK_INT < 23 || actionMode.getType() != 1) {
            this.actionBarLayout.onActionModeStarted(actionMode);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.onActionModeStarted(actionMode);
                this.layersActionBarLayout.onActionModeStarted(actionMode);
            }
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onActionModeFinished(ActionMode actionMode) {
        super.onActionModeFinished(actionMode);
        if (this.visibleActionMode == actionMode) {
            this.visibleActionMode = null;
        }
        if (Build.VERSION.SDK_INT < 23 || actionMode.getType() != 1) {
            this.actionBarLayout.onActionModeFinished(actionMode);
            if (AndroidUtilities.isTablet()) {
                this.rightActionBarLayout.onActionModeFinished(actionMode);
                this.layersActionBarLayout.onActionModeFinished(actionMode);
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean onPreIme() {
        if (SecretMediaViewer.hasInstance() && SecretMediaViewer.getInstance().isVisible()) {
            SecretMediaViewer.getInstance().closePhoto(true, false);
            return true;
        }
        if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
            PhotoViewer.getInstance().closePhoto(true, false);
            return true;
        }
        if (!ArticleViewer.hasInstance() || !ArticleViewer.getInstance().isVisible()) {
            return false;
        }
        ArticleViewer.getInstance().close(true, false);
        return true;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        keyEvent.getKeyCode();
        if (keyEvent.getAction() == 0 && (keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25)) {
            if (VoIPService.getSharedInstance() != null) {
                if (Build.VERSION.SDK_INT >= 32) {
                    boolean isSpeakerMuted = WebRtcAudioTrack.isSpeakerMuted();
                    AudioManager audioManager = (AudioManager) getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
                    boolean z = audioManager.getStreamVolume(0) == audioManager.getStreamMinVolume(0) && keyEvent.getKeyCode() == 25;
                    WebRtcAudioTrack.setSpeakerMute(z);
                    if (isSpeakerMuted != WebRtcAudioTrack.isSpeakerMuted()) {
                        showVoiceChatTooltip(z ? 42 : 43);
                    }
                }
            } else if (!mainFragmentsStack.isEmpty() && ((!PhotoViewer.hasInstance() || !PhotoViewer.getInstance().isVisible()) && keyEvent.getRepeatCount() == 0)) {
                ArrayList<BaseFragment> arrayList = mainFragmentsStack;
                BaseFragment baseFragment = arrayList.get(arrayList.size() - 1);
                if ((baseFragment instanceof ChatActivity) && ((ChatActivity) baseFragment).maybePlayVisibleVideo()) {
                    return true;
                }
                if (AndroidUtilities.isTablet() && !rightFragmentsStack.isEmpty()) {
                    ArrayList<BaseFragment> arrayList2 = rightFragmentsStack;
                    BaseFragment baseFragment2 = arrayList2.get(arrayList2.size() - 1);
                    if ((baseFragment2 instanceof ChatActivity) && ((ChatActivity) baseFragment2).maybePlayVisibleVideo()) {
                        return true;
                    }
                }
            }
        }
        try {
            return super.dispatchKeyEvent(keyEvent);
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 82 && !SharedConfig.isWaitingForPasscodeEnter) {
            if (PhotoViewer.hasInstance() && PhotoViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (ArticleViewer.hasInstance() && ArticleViewer.getInstance().isVisible()) {
                return super.onKeyUp(i, keyEvent);
            }
            if (AndroidUtilities.isTablet()) {
                if (this.layersActionBarLayout.getView().getVisibility() == 0 && !this.layersActionBarLayout.getFragmentStack().isEmpty()) {
                    this.layersActionBarLayout.getView().onKeyUp(i, keyEvent);
                } else if (this.rightActionBarLayout.getView().getVisibility() == 0 && !this.rightActionBarLayout.getFragmentStack().isEmpty()) {
                    this.rightActionBarLayout.getView().onKeyUp(i, keyEvent);
                } else {
                    this.actionBarLayout.getView().onKeyUp(i, keyEvent);
                }
            } else if (this.actionBarLayout.getFragmentStack().size() == 1) {
                if (!this.drawerLayoutContainer.isDrawerOpened()) {
                    if (getCurrentFocus() != null) {
                        AndroidUtilities.hideKeyboard(getCurrentFocus());
                    }
                    this.drawerLayoutContainer.openDrawer(false);
                } else {
                    this.drawerLayoutContainer.closeDrawer(false);
                }
            } else {
                this.actionBarLayout.getView().onKeyUp(i, keyEvent);
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:124:0x0267, code lost:
    
        if (org.telegram.ui.LaunchActivity.mainFragmentsStack.size() == 1) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x027f, code lost:
    
        if ((org.telegram.ui.LaunchActivity.mainFragmentsStack.get(0) instanceof org.telegram.ui.LoginActivity) != false) goto L129;
     */
    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean needPresentFragment(org.telegram.ui.ActionBar.INavigationLayout r9, org.telegram.ui.ActionBar.INavigationLayout.NavigationParams r10) {
        /*
            Method dump skipped, instructions count: 651
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.needPresentFragment(org.telegram.ui.ActionBar.INavigationLayout, org.telegram.ui.ActionBar.INavigationLayout$NavigationParams):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x0162, code lost:
    
        if (org.telegram.ui.LaunchActivity.mainFragmentsStack.size() == 1) goto L85;
     */
    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean needAddFragmentToStack(org.telegram.ui.ActionBar.BaseFragment r7, org.telegram.ui.ActionBar.INavigationLayout r8) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LaunchActivity.needAddFragmentToStack(org.telegram.ui.ActionBar.BaseFragment, org.telegram.ui.ActionBar.INavigationLayout):boolean");
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public boolean needCloseLastFragment(INavigationLayout iNavigationLayout) {
        if (AndroidUtilities.isTablet()) {
            if (iNavigationLayout == this.actionBarLayout && iNavigationLayout.getFragmentStack().size() <= 1) {
                onFinish();
                finish();
                return false;
            }
            if (iNavigationLayout == this.rightActionBarLayout) {
                if (!this.tabletFullSize) {
                    this.backgroundTablet.setVisibility(0);
                }
            } else if (iNavigationLayout == this.layersActionBarLayout && this.actionBarLayout.getFragmentStack().isEmpty() && this.layersActionBarLayout.getFragmentStack().size() == 1) {
                onFinish();
                finish();
                return false;
            }
        } else {
            if (iNavigationLayout.getFragmentStack().size() <= 1) {
                onFinish();
                finish();
                return false;
            }
            if (iNavigationLayout.getFragmentStack().size() >= 2 && !(iNavigationLayout.getFragmentStack().get(0) instanceof LoginActivity)) {
                this.drawerLayoutContainer.setAllowOpenDrawer(true, false);
            }
        }
        return true;
    }

    public void rebuildAllFragments(boolean z) {
        INavigationLayout iNavigationLayout = this.layersActionBarLayout;
        if (iNavigationLayout != null) {
            iNavigationLayout.rebuildAllFragmentViews(z, z);
        } else {
            this.actionBarLayout.rebuildAllFragmentViews(z, z);
        }
    }

    @Override // org.telegram.ui.ActionBar.INavigationLayout.INavigationLayoutDelegate
    public void onRebuildAllFragments(INavigationLayout iNavigationLayout, boolean z) {
        if (AndroidUtilities.isTablet() && iNavigationLayout == this.layersActionBarLayout) {
            this.rightActionBarLayout.rebuildAllFragmentViews(z, z);
            this.actionBarLayout.rebuildAllFragmentViews(z, z);
        }
        this.drawerLayoutAdapter.notifyDataSetChanged();
    }

    public static BaseFragment getLastFragment() {
        LaunchActivity launchActivity = instance;
        if (launchActivity == null || launchActivity.getActionBarLayout() == null) {
            return null;
        }
        return instance.getActionBarLayout().getLastFragment();
    }
}
