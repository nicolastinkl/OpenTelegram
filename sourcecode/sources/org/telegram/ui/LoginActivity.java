package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Property;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import androidx.core.graphics.ColorUtils;
import androidx.dynamicanimation.animation.DynamicAnimation;
import cn.jzvd.JZDataSource;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallRetryAdapter;
import com.fm.openinstall.model.AppData;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi$AttestationResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.xinstall.XInstall;
import com.xinstall.listener.XInstallListener;
import com.xinstall.model.XAppData;
import com.xinstall.model.XAppError;
import cos.MyCOSService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.AuthTokensHelper;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.CallReceiver;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.GenericProvider;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.PushListenerController;
import org.telegram.messenger.R;
import org.telegram.messenger.SRPHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$JMT_auth_authorizationV2;
import org.telegram.tgnet.TLRPC$JMT_json_config;
import org.telegram.tgnet.TLRPC$JMT_signInByGuestV2;
import org.telegram.tgnet.TLRPC$JMT_signInByUsernameV2;
import org.telegram.tgnet.TLRPC$JMT_signUpByUsernameV3;
import org.telegram.tgnet.TLRPC$JMT_signUpByUsernameV4;
import org.telegram.tgnet.TLRPC$JSONValue;
import org.telegram.tgnet.TLRPC$PasswordKdfAlgo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_account_changePhone;
import org.telegram.tgnet.TLRPC$TL_account_checkUsername;
import org.telegram.tgnet.TLRPC$TL_account_confirmPhone;
import org.telegram.tgnet.TLRPC$TL_account_deleteAccount;
import org.telegram.tgnet.TLRPC$TL_account_emailVerified;
import org.telegram.tgnet.TLRPC$TL_account_emailVerifiedLogin;
import org.telegram.tgnet.TLRPC$TL_account_getPassword;
import org.telegram.tgnet.TLRPC$TL_account_passwordInputSettings;
import org.telegram.tgnet.TLRPC$TL_account_sendVerifyEmailCode;
import org.telegram.tgnet.TLRPC$TL_account_sentEmailCode;
import org.telegram.tgnet.TLRPC$TL_account_verifyEmail;
import org.telegram.tgnet.TLRPC$TL_auth_authorization;
import org.telegram.tgnet.TLRPC$TL_auth_authorizationSignUpRequired;
import org.telegram.tgnet.TLRPC$TL_auth_cancelCode;
import org.telegram.tgnet.TLRPC$TL_auth_checkPassword;
import org.telegram.tgnet.TLRPC$TL_auth_checkRecoveryPassword;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeCall;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeFlashCall;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeFragmentSms;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeMissedCall;
import org.telegram.tgnet.TLRPC$TL_auth_codeTypeSms;
import org.telegram.tgnet.TLRPC$TL_auth_passwordRecovery;
import org.telegram.tgnet.TLRPC$TL_auth_recoverPassword;
import org.telegram.tgnet.TLRPC$TL_auth_requestFirebaseSms;
import org.telegram.tgnet.TLRPC$TL_auth_requestPasswordRecovery;
import org.telegram.tgnet.TLRPC$TL_auth_resendCode;
import org.telegram.tgnet.TLRPC$TL_auth_resetLoginEmail;
import org.telegram.tgnet.TLRPC$TL_auth_sentCode;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeApp;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeCall;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeEmailCode;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeFirebaseSms;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeFlashCall;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeFragmentSms;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeMissedCall;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeSetUpEmailRequired;
import org.telegram.tgnet.TLRPC$TL_auth_sentCodeTypeSms;
import org.telegram.tgnet.TLRPC$TL_auth_signIn;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_emailVerificationCode;
import org.telegram.tgnet.TLRPC$TL_emailVerificationGoogle;
import org.telegram.tgnet.TLRPC$TL_emailVerifyPurposeLoginChange;
import org.telegram.tgnet.TLRPC$TL_emailVerifyPurposeLoginSetup;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_termsOfService;
import org.telegram.tgnet.TLRPC$TL_inputCheckPasswordSRP;
import org.telegram.tgnet.TLRPC$TL_jsonBool;
import org.telegram.tgnet.TLRPC$TL_jsonNumber;
import org.telegram.tgnet.TLRPC$TL_jsonObjectValue;
import org.telegram.tgnet.TLRPC$TL_jsonString;
import org.telegram.tgnet.TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$VideoSize;
import org.telegram.tgnet.TLRPC$account_Password;
import org.telegram.tgnet.TLRPC$auth_Authorization;
import org.telegram.tgnet.TLRPC$auth_CodeType;
import org.telegram.tgnet.TLRPC$auth_SentCode;
import org.telegram.tgnet.TLRPC$auth_SentCodeType;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.CustomPhoneKeyboardView;
import org.telegram.ui.Components.Easings;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.ImageUpdater;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LoginOrView;
import org.telegram.ui.Components.OutlineEditText;
import org.telegram.ui.Components.OutlineTextContainerView;
import org.telegram.ui.Components.ProxyDrawable;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RLottieImageView;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.Components.SimpleThemeDescription;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.SlideView;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.Components.TransformableLoginButtonView;
import org.telegram.ui.Components.VerticalPositionAutoAnimator;
import org.telegram.ui.JMTDangerPopup;
import org.telegram.ui.LoginActivity;

@SuppressLint({"HardwareIds"})
/* loaded from: classes3.dex */
public class LoginActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private static final int SHOW_DELAY;
    private int activityMode;
    private Runnable animationFinishCallback;
    private ImageView backButtonView;
    private AlertDialog cancelDeleteProgressDialog;
    private TLRPC$TL_auth_sentCode cancelDeletionCode;
    private Bundle cancelDeletionParams;
    private String cancelDeletionPhone;
    private String captchaJson;
    private int currentConnectionState;
    private int currentDoneType;
    private TLRPC$TL_help_termsOfService currentTermsOfService;
    private int currentViewNum;
    private boolean customKeyboardWasVisible;
    private boolean[] doneButtonVisible;
    private AnimatorSet doneItemAnimation;
    private boolean[] doneProgressVisible;
    private Runnable[] editDoneCallback;
    private Runnable emailChangeFinishCallback;
    private VerticalPositionAutoAnimator floatingAutoAnimator;
    private FrameLayout floatingButtonContainer;
    private TransformableLoginButtonView floatingButtonIcon;
    private RadialProgressView floatingProgressView;
    private View introView;
    private boolean isAnimatingIntro;
    private boolean isRequestingFirebaseSms;
    private ValueAnimator keyboardAnimator;
    private Runnable keyboardHideCallback;
    private LinearLayout keyboardLinearLayout;
    private CustomPhoneKeyboardView keyboardView;
    private boolean needRequestPermissions;
    private boolean newAccount;
    private Dialog permissionsDialog;
    private ArrayList<String> permissionsItems;
    private Dialog permissionsShowDialog;
    private ArrayList<String> permissionsShowItems;
    private boolean[] postedEditDoneCallback;
    private int progressRequestId;
    private ImageView proxyButtonView;
    private boolean proxyButtonVisible;
    private ProxyDrawable proxyDrawable;
    private RadialProgressView radialProgressView;
    private boolean restoringState;
    private AnimatorSet[] showDoneAnimation;
    private Runnable showProxyButtonDelayed;
    private SizeNotifierFrameLayout sizeNotifierFrameLayout;
    private FrameLayout slideViewsContainer;
    private TextView startMessagingButton;
    private boolean syncContacts;
    private SlideView[] views;

    private static class ProgressView extends View {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(DynamicAnimation dynamicAnimation, float f, float f2) {
    }

    static {
        SHOW_DELAY = SharedConfig.getDevicePerformanceClass() <= 1 ? ImageReceiver.DEFAULT_CROSSFADE_DURATION : 100;
    }

    public LoginActivity() {
        this.views = new SlideView[17];
        this.permissionsItems = new ArrayList<>();
        this.permissionsShowItems = new ArrayList<>();
        this.syncContacts = true;
        this.activityMode = 0;
        this.showDoneAnimation = new AnimatorSet[2];
        this.doneButtonVisible = new boolean[]{true, false};
        this.customKeyboardWasVisible = false;
        this.doneProgressVisible = new boolean[2];
        this.editDoneCallback = new Runnable[2];
        this.postedEditDoneCallback = new boolean[2];
        this.captchaJson = "";
    }

    public LoginActivity(int i) {
        this.views = new SlideView[17];
        this.permissionsItems = new ArrayList<>();
        this.permissionsShowItems = new ArrayList<>();
        this.syncContacts = true;
        this.activityMode = 0;
        this.showDoneAnimation = new AnimatorSet[2];
        this.doneButtonVisible = new boolean[]{true, false};
        this.customKeyboardWasVisible = false;
        this.doneProgressVisible = new boolean[2];
        this.editDoneCallback = new Runnable[2];
        this.postedEditDoneCallback = new boolean[2];
        this.captchaJson = "";
        this.currentAccount = i;
        this.newAccount = true;
    }

    public LoginActivity changeEmail(Runnable runnable) {
        this.activityMode = 3;
        this.currentViewNum = 12;
        this.emailChangeFinishCallback = runnable;
        return this;
    }

    public LoginActivity cancelAccountDeletion(String str, Bundle bundle, TLRPC$TL_auth_sentCode tLRPC$TL_auth_sentCode) {
        this.cancelDeletionPhone = str;
        this.cancelDeletionParams = bundle;
        this.cancelDeletionCode = tLRPC$TL_auth_sentCode;
        this.activityMode = 1;
        return this;
    }

    public LoginActivity changePhoneNumber() {
        this.activityMode = 2;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInCancelAccountDeletionMode() {
        return this.activityMode == 1;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        int i = 0;
        while (true) {
            SlideView[] slideViewArr = this.views;
            if (i >= slideViewArr.length) {
                break;
            }
            if (slideViewArr[i] != null) {
                slideViewArr[i].onDestroyActivity();
            }
            i++;
        }
        AlertDialog alertDialog = this.cancelDeleteProgressDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.cancelDeleteProgressDialog = null;
        }
        for (Runnable runnable : this.editDoneCallback) {
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
        }
        getNotificationCenter().removeObserver(this, NotificationCenter.didUpdateConnectionState);
        getNotificationCenter().removeObserver(this, NotificationCenter.updateCurrentIpAndPort);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.didUpdateConnectionState);
        getNotificationCenter().addObserver(this, NotificationCenter.updateCurrentIpAndPort);
        return super.onFragmentCreate();
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x04d6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0500 A[EDGE_INSN: B:134:0x0500->B:135:0x0500 BREAK  A[LOOP:1: B:74:0x0452->B:119:0x04fb], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0273  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x040e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0457  */
    @Override // org.telegram.ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View createView(android.content.Context r29) {
        /*
            Method dump skipped, instructions count: 1301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.createView(android.content.Context):android.view.View");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(int i, boolean z) {
        Runnable runnable;
        if (i > AndroidUtilities.dp(20.0f) && isCustomKeyboardVisible()) {
            AndroidUtilities.hideKeyboard(this.fragmentView);
        }
        if (i > AndroidUtilities.dp(20.0f) || (runnable = this.keyboardHideCallback) == null) {
            return;
        }
        runnable.run();
        this.keyboardHideCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        onDoneButtonPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(View view) {
        if (onBackPressed()) {
            finishFragment();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(View view) {
        presentFragment(new ProxyListActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCustomKeyboardForceDisabled() {
        Point point = AndroidUtilities.displaySize;
        return point.x > point.y || AndroidUtilities.isTablet() || AndroidUtilities.isAccessibilityTouchExplorationEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCustomKeyboardVisible() {
        return this.views[this.currentViewNum].hasCustomKeyboard() && !isCustomKeyboardForceDisabled();
    }

    private void setCustomKeyboardVisible(boolean z, boolean z2) {
        if (this.customKeyboardWasVisible == z && z2) {
            return;
        }
        this.customKeyboardWasVisible = z;
        if (isCustomKeyboardForceDisabled()) {
            z = false;
        }
        if (z) {
            AndroidUtilities.hideKeyboard(this.fragmentView);
            AndroidUtilities.requestAltFocusable(getParentActivity(), this.classGuid);
            if (z2) {
                ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(300L);
                this.keyboardAnimator = duration;
                duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.keyboardAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LoginActivity.this.lambda$setCustomKeyboardVisible$5(valueAnimator);
                    }
                });
                this.keyboardAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        LoginActivity.this.keyboardView.setVisibility(0);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (LoginActivity.this.keyboardAnimator == animator) {
                            LoginActivity.this.keyboardAnimator = null;
                        }
                    }
                });
                this.keyboardAnimator.start();
                return;
            }
            this.keyboardView.setVisibility(0);
            return;
        }
        AndroidUtilities.removeAltFocusable(getParentActivity(), this.classGuid);
        if (z2) {
            ValueAnimator duration2 = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(300L);
            this.keyboardAnimator = duration2;
            duration2.setInterpolator(Easings.easeInOutQuad);
            this.keyboardAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LoginActivity.this.lambda$setCustomKeyboardVisible$6(valueAnimator);
                }
            });
            this.keyboardAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    LoginActivity.this.keyboardView.setVisibility(8);
                    if (LoginActivity.this.keyboardAnimator == animator) {
                        LoginActivity.this.keyboardAnimator = null;
                    }
                }
            });
            this.keyboardAnimator.start();
            return;
        }
        this.keyboardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCustomKeyboardVisible$5(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.keyboardView.setAlpha(floatValue);
        this.keyboardView.setTranslationY((1.0f - floatValue) * AndroidUtilities.dp(230.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCustomKeyboardVisible$6(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.keyboardView.setAlpha(floatValue);
        this.keyboardView.setTranslationY((1.0f - floatValue) * AndroidUtilities.dp(230.0f));
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        if (BuildVars.isShowLoginVideo) {
            JZUtils.clearSavedProgress(getContext(), null);
            Jzvd.goOnPlayOnPause();
        }
        if (this.newAccount) {
            ConnectionsManager.getInstance(this.currentAccount).setAppPaused(true, false);
        }
        AndroidUtilities.removeAltFocusable(getParentActivity(), this.classGuid);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        SlideView slideView;
        int i;
        super.onResume();
        if (BuildVars.isShowLoginVideo) {
            Jzvd.goOnPlayOnResume();
        }
        if (this.newAccount) {
            ConnectionsManager.getInstance(this.currentAccount).setAppPaused(false, false);
        }
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        View view = this.fragmentView;
        if (view != null) {
            view.requestLayout();
        }
        try {
            int i2 = this.currentViewNum;
            if (i2 >= 1 && i2 <= 4) {
                SlideView[] slideViewArr = this.views;
                if ((slideViewArr[i2] instanceof LoginActivitySmsView) && (i = ((LoginActivitySmsView) slideViewArr[i2]).openTime) != 0 && Math.abs((System.currentTimeMillis() / 1000) - i) >= 86400) {
                    this.views[this.currentViewNum].onBackPressed(true);
                    setPage(0, false, null, true);
                }
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        int i3 = this.currentViewNum;
        if (i3 == 0 && !this.needRequestPermissions && (slideView = this.views[i3]) != null) {
            slideView.onShow();
        }
        if (isCustomKeyboardVisible()) {
            AndroidUtilities.hideKeyboard(this.fragmentView);
            AndroidUtilities.requestAltFocusable(getParentActivity(), this.classGuid);
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onConfigurationChanged(Configuration configuration) {
        setCustomKeyboardVisible(this.views[this.currentViewNum].hasCustomKeyboard(), false);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (strArr.length == 0 || iArr.length == 0) {
            return;
        }
        boolean z = iArr[0] == 0;
        if (i == 6 || i == 7) {
            return;
        }
        if (i == 20) {
            if (z) {
                ((LoginActivityRegisterView) this.views[5]).imageUpdater.openCamera();
            }
        } else if (i == 151 && z) {
            final LoginActivityRegisterView loginActivityRegisterView = (LoginActivityRegisterView) this.views[5];
            loginActivityRegisterView.post(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.lambda$onRequestPermissionsResultFragment$7(LoginActivity.LoginActivityRegisterView.this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onRequestPermissionsResultFragment$7(LoginActivityRegisterView loginActivityRegisterView) {
        loginActivityRegisterView.imageUpdater.openGallery();
    }

    public static Bundle loadCurrentState(boolean z, int i) {
        try {
            Bundle bundle = new Bundle();
            Context context = ApplicationLoader.applicationContext;
            StringBuilder sb = new StringBuilder();
            sb.append("logininfo2");
            sb.append(z ? "_" + i : "");
            for (Map.Entry<String, ?> entry : context.getSharedPreferences(sb.toString(), 0).getAll().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String[] split = key.split("_\\|_");
                if (split.length == 1) {
                    if (value instanceof String) {
                        bundle.putString(key, (String) value);
                    } else if (value instanceof Integer) {
                        bundle.putInt(key, ((Integer) value).intValue());
                    } else if (value instanceof Boolean) {
                        bundle.putBoolean(key, ((Boolean) value).booleanValue());
                    }
                } else if (split.length == 2) {
                    Bundle bundle2 = bundle.getBundle(split[0]);
                    if (bundle2 == null) {
                        bundle2 = new Bundle();
                        bundle.putBundle(split[0], bundle2);
                    }
                    if (value instanceof String) {
                        bundle2.putString(split[1], (String) value);
                    } else if (value instanceof Integer) {
                        bundle2.putInt(split[1], ((Integer) value).intValue());
                    } else if (value instanceof Boolean) {
                        bundle2.putBoolean(split[1], ((Boolean) value).booleanValue());
                    }
                }
            }
            return bundle;
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    private void clearCurrentState() {
        String str;
        Context context = ApplicationLoader.applicationContext;
        StringBuilder sb = new StringBuilder();
        sb.append("logininfo2");
        if (this.newAccount) {
            str = "_" + this.currentAccount;
        } else {
            str = "";
        }
        sb.append(str);
        SharedPreferences.Editor edit = context.getSharedPreferences(sb.toString(), 0).edit();
        edit.clear();
        edit.commit();
    }

    private void putBundleToEditor(Bundle bundle, SharedPreferences.Editor editor, String str) {
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof String) {
                if (str != null) {
                    editor.putString(str + "_|_" + str2, (String) obj);
                } else {
                    editor.putString(str2, (String) obj);
                }
            } else if (obj instanceof Integer) {
                if (str != null) {
                    editor.putInt(str + "_|_" + str2, ((Integer) obj).intValue());
                } else {
                    editor.putInt(str2, ((Integer) obj).intValue());
                }
            } else if (obj instanceof Boolean) {
                if (str != null) {
                    editor.putBoolean(str + "_|_" + str2, ((Boolean) obj).booleanValue());
                } else {
                    editor.putBoolean(str2, ((Boolean) obj).booleanValue());
                }
            } else if (obj instanceof Bundle) {
                putBundleToEditor((Bundle) obj, editor, str2);
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    protected void onDialogDismiss(Dialog dialog) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (dialog == this.permissionsDialog && !this.permissionsItems.isEmpty() && getParentActivity() != null) {
                    getParentActivity().requestPermissions((String[]) this.permissionsItems.toArray(new String[0]), 6);
                } else {
                    if (dialog != this.permissionsShowDialog || this.permissionsShowItems.isEmpty() || getParentActivity() == null) {
                        return;
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda18
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.this.lambda$onDialogDismiss$8();
                        }
                    }, 200L);
                    getParentActivity().requestPermissions((String[]) this.permissionsShowItems.toArray(new String[0]), 7);
                }
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDialogDismiss$8() {
        this.needRequestPermissions = false;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        int i = this.currentViewNum;
        int i2 = 0;
        if (i != 0 && (this.activityMode != 3 || i != 12)) {
            if (i == 6) {
                this.views[i].onBackPressed(true);
                setPage(0, true, null, true);
            } else if (i == 7 || i == 8) {
                this.views[i].onBackPressed(true);
                setPage(6, true, null, true);
            } else if ((i >= 1 && i <= 4) || i == 11 || i == 15) {
                if (this.views[i].onBackPressed(false)) {
                    setPage(0, true, null, true);
                }
            } else if (i == 5) {
                ((LoginActivityRegisterView) this.views[i]).wrongNumber.callOnClick();
            } else if (i == 9) {
                this.views[i].onBackPressed(true);
                setPage(7, true, null, true);
            } else if (i == 10) {
                this.views[i].onBackPressed(true);
                setPage(9, true, null, true);
            } else if (i == 13) {
                this.views[i].onBackPressed(true);
                setPage(12, true, null, true);
            } else if (this.views[i].onBackPressed(true)) {
                setPage(0, true, null, true);
            }
            return false;
        }
        while (true) {
            SlideView[] slideViewArr = this.views;
            if (i2 < slideViewArr.length) {
                if (slideViewArr[i2] != null) {
                    slideViewArr[i2].onDestroyActivity();
                }
                i2++;
            } else {
                clearCurrentState();
                return true;
            }
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onActivityResultFragment(int i, int i2, Intent intent) {
        LoginActivityRegisterView loginActivityRegisterView = (LoginActivityRegisterView) this.views[5];
        if (loginActivityRegisterView != null) {
            loginActivityRegisterView.imageUpdater.onActivityResult(i, i2, intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void needShowAlert(String str, String str2) {
        if (str2 == null || getParentActivity() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(str);
        builder.setMessage(str2);
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        showDialog(builder.create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFieldError(final View view, boolean z) {
        view.performHapticFeedback(3, 2);
        AndroidUtilities.shakeViewSpring(view, 3.5f);
        if (z && (view instanceof OutlineTextContainerView)) {
            int i = R.id.timeout_callback;
            Runnable runnable = (Runnable) view.getTag(i);
            if (runnable != null) {
                view.removeCallbacks(runnable);
            }
            final OutlineTextContainerView outlineTextContainerView = (OutlineTextContainerView) view;
            AtomicReference atomicReference = new AtomicReference();
            final EditText attachedEditText = outlineTextContainerView.getAttachedEditText();
            final AnonymousClass8 anonymousClass8 = new AnonymousClass8(this, attachedEditText, atomicReference);
            outlineTextContainerView.animateError(1.0f);
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.lambda$onFieldError$10(OutlineTextContainerView.this, view, attachedEditText, anonymousClass8);
                }
            };
            atomicReference.set(runnable2);
            view.postDelayed(runnable2, 2000L);
            view.setTag(i, runnable2);
            if (attachedEditText != null) {
                attachedEditText.addTextChangedListener(anonymousClass8);
            }
        }
    }

    /* renamed from: org.telegram.ui.LoginActivity$8, reason: invalid class name */
    class AnonymousClass8 implements TextWatcher {
        final /* synthetic */ EditText val$editText;
        final /* synthetic */ AtomicReference val$timeoutCallbackRef;

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        AnonymousClass8(LoginActivity loginActivity, EditText editText, AtomicReference atomicReference) {
            this.val$editText = editText;
            this.val$timeoutCallbackRef = atomicReference;
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            final EditText editText = this.val$editText;
            final AtomicReference atomicReference = this.val$timeoutCallbackRef;
            editText.post(new Runnable() { // from class: org.telegram.ui.LoginActivity$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.AnonymousClass8.this.lambda$beforeTextChanged$0(editText, atomicReference);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$beforeTextChanged$0(EditText editText, AtomicReference atomicReference) {
            editText.removeTextChangedListener(this);
            editText.removeCallbacks((Runnable) atomicReference.get());
            ((Runnable) atomicReference.get()).run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onFieldError$10(OutlineTextContainerView outlineTextContainerView, View view, final EditText editText, final TextWatcher textWatcher) {
        outlineTextContainerView.animateError(0.0f);
        view.setTag(R.id.timeout_callback, null);
        if (editText != null) {
            editText.post(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    editText.removeTextChangedListener(textWatcher);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDoneButton(final boolean z, boolean z2) {
        TimeInterpolator timeInterpolator;
        int i = this.currentDoneType;
        final boolean z3 = i == 0;
        if (this.doneButtonVisible[i] == z) {
            return;
        }
        AnimatorSet[] animatorSetArr = this.showDoneAnimation;
        if (animatorSetArr[i] != null) {
            if (z2) {
                animatorSetArr[i].removeAllListeners();
            }
            this.showDoneAnimation[this.currentDoneType].cancel();
        }
        boolean[] zArr = this.doneButtonVisible;
        int i2 = this.currentDoneType;
        zArr[i2] = z;
        if (!z2) {
            if (z) {
                if (z3) {
                    this.floatingButtonContainer.setVisibility(0);
                    this.floatingAutoAnimator.setOffsetY(0.0f);
                    return;
                }
                return;
            }
            if (z3) {
                this.floatingButtonContainer.setVisibility(8);
                this.floatingAutoAnimator.setOffsetY(AndroidUtilities.dpf2(70.0f));
                return;
            }
            return;
        }
        this.showDoneAnimation[i2] = new AnimatorSet();
        if (z) {
            if (z3) {
                if (this.floatingButtonContainer.getVisibility() != 0) {
                    this.floatingAutoAnimator.setOffsetY(AndroidUtilities.dpf2(70.0f));
                    this.floatingButtonContainer.setVisibility(0);
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.floatingAutoAnimator.getOffsetY(), 0.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LoginActivity.this.lambda$showDoneButton$12(valueAnimator);
                    }
                });
                this.showDoneAnimation[this.currentDoneType].play(ofFloat);
            }
        } else if (z3) {
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.floatingAutoAnimator.getOffsetY(), AndroidUtilities.dpf2(70.0f));
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LoginActivity.this.lambda$showDoneButton$13(valueAnimator);
                }
            });
            this.showDoneAnimation[this.currentDoneType].play(ofFloat2);
        }
        this.showDoneAnimation[this.currentDoneType].addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (LoginActivity.this.showDoneAnimation[!z3 ? 1 : 0] == null || !LoginActivity.this.showDoneAnimation[!z3 ? 1 : 0].equals(animator) || z) {
                    return;
                }
                if (z3) {
                    LoginActivity.this.floatingButtonContainer.setVisibility(8);
                }
                if (!z3 || LoginActivity.this.floatingButtonIcon.getAlpha() == 1.0f) {
                    return;
                }
                LoginActivity.this.floatingButtonIcon.setAlpha(1.0f);
                LoginActivity.this.floatingButtonIcon.setScaleX(1.0f);
                LoginActivity.this.floatingButtonIcon.setScaleY(1.0f);
                LoginActivity.this.floatingButtonIcon.setVisibility(0);
                LoginActivity.this.floatingButtonContainer.setEnabled(true);
                LoginActivity.this.floatingProgressView.setAlpha(0.0f);
                LoginActivity.this.floatingProgressView.setScaleX(0.1f);
                LoginActivity.this.floatingProgressView.setScaleY(0.1f);
                LoginActivity.this.floatingProgressView.setVisibility(4);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (LoginActivity.this.showDoneAnimation[!z3 ? 1 : 0] == null || !LoginActivity.this.showDoneAnimation[!z3 ? 1 : 0].equals(animator)) {
                    return;
                }
                LoginActivity.this.showDoneAnimation[!z3 ? 1 : 0] = null;
            }
        });
        int i3 = ImageReceiver.DEFAULT_CROSSFADE_DURATION;
        if (!z3) {
            timeInterpolator = null;
        } else if (z) {
            i3 = 200;
            timeInterpolator = AndroidUtilities.decelerateInterpolator;
        } else {
            timeInterpolator = AndroidUtilities.accelerateInterpolator;
        }
        this.showDoneAnimation[this.currentDoneType].setDuration(i3);
        this.showDoneAnimation[this.currentDoneType].setInterpolator(timeInterpolator);
        this.showDoneAnimation[this.currentDoneType].start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDoneButton$12(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.floatingAutoAnimator.setOffsetY(floatValue);
        this.floatingButtonContainer.setAlpha(1.0f - (floatValue / AndroidUtilities.dpf2(70.0f)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDoneButton$13(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.floatingAutoAnimator.setOffsetY(floatValue);
        this.floatingButtonContainer.setAlpha(1.0f - (floatValue / AndroidUtilities.dpf2(70.0f)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDoneButtonPressed() {
        if (this.doneButtonVisible[this.currentDoneType]) {
            if (this.radialProgressView.getTag() != null) {
                if (getParentActivity() == null) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                builder.setTitle(LocaleController.getString("StopLoadingTitle", R.string.StopLoadingTitle));
                builder.setMessage(LocaleController.getString("StopLoading", R.string.StopLoading));
                builder.setPositiveButton(LocaleController.getString("WaitMore", R.string.WaitMore), null);
                builder.setNegativeButton(LocaleController.getString("Stop", R.string.Stop), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda6
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.this.lambda$onDoneButtonPressed$14(dialogInterface, i);
                    }
                });
                showDialog(builder.create());
                return;
            }
            this.views[this.currentViewNum].onNextPressed(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDoneButtonPressed$14(DialogInterface dialogInterface, int i) {
        this.views[this.currentViewNum].onCancelPressed();
        needHideProgress(true);
    }

    private void showEditDoneProgress(boolean z, boolean z2) {
        lambda$showEditDoneProgress$15(z, z2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: showEditDoneProgress, reason: merged with bridge method [inline-methods] */
    public void lambda$showEditDoneProgress$15(final boolean z, final boolean z2, final boolean z3) {
        if (z2 && this.doneProgressVisible[this.currentDoneType] == z && !z3) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.this.lambda$showEditDoneProgress$15(z, z2, z3);
                }
            });
            return;
        }
        final int i = this.currentDoneType;
        final boolean z4 = i == 0;
        if (!z3 && !z4) {
            this.doneProgressVisible[i] = z;
            if (z2) {
                if (this.postedEditDoneCallback[i]) {
                    AndroidUtilities.cancelRunOnUIThread(this.editDoneCallback[i]);
                    this.postedEditDoneCallback[this.currentDoneType] = false;
                    return;
                } else if (z) {
                    Runnable[] runnableArr = this.editDoneCallback;
                    Runnable runnable = new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.this.lambda$showEditDoneProgress$16(i, z, z2);
                        }
                    };
                    runnableArr[i] = runnable;
                    AndroidUtilities.runOnUIThread(runnable, 2000L);
                    this.postedEditDoneCallback[this.currentDoneType] = true;
                    return;
                }
            }
        } else {
            this.postedEditDoneCallback[i] = false;
            this.doneProgressVisible[i] = z;
        }
        AnimatorSet animatorSet = this.doneItemAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (z2) {
            this.doneItemAnimation = new AnimatorSet();
            float[] fArr = new float[2];
            fArr[0] = z ? 0.0f : 1.0f;
            fArr[1] = z ? 1.0f : 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (z) {
                        if (z4) {
                            LoginActivity.this.floatingButtonIcon.setVisibility(0);
                            LoginActivity.this.floatingProgressView.setVisibility(0);
                            LoginActivity.this.floatingButtonContainer.setEnabled(false);
                            return;
                        }
                        LoginActivity.this.radialProgressView.setVisibility(0);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (z4) {
                        if (!z) {
                            LoginActivity.this.floatingProgressView.setVisibility(4);
                            LoginActivity.this.floatingButtonIcon.setVisibility(0);
                            LoginActivity.this.floatingButtonContainer.setEnabled(true);
                        } else {
                            LoginActivity.this.floatingButtonIcon.setVisibility(4);
                            LoginActivity.this.floatingProgressView.setVisibility(0);
                        }
                    } else if (!z) {
                        LoginActivity.this.radialProgressView.setVisibility(4);
                    }
                    if (LoginActivity.this.doneItemAnimation == null || !LoginActivity.this.doneItemAnimation.equals(animator)) {
                        return;
                    }
                    LoginActivity.this.doneItemAnimation = null;
                }
            });
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LoginActivity.this.lambda$showEditDoneProgress$17(z4, valueAnimator);
                }
            });
            this.doneItemAnimation.playTogether(ofFloat);
            this.doneItemAnimation.setDuration(150L);
            this.doneItemAnimation.start();
            return;
        }
        if (z) {
            if (z4) {
                this.floatingProgressView.setVisibility(0);
                this.floatingButtonIcon.setVisibility(4);
                this.floatingButtonContainer.setEnabled(false);
                this.floatingButtonIcon.setScaleX(0.1f);
                this.floatingButtonIcon.setScaleY(0.1f);
                this.floatingButtonIcon.setAlpha(0.0f);
                this.floatingProgressView.setScaleX(1.0f);
                this.floatingProgressView.setScaleY(1.0f);
                this.floatingProgressView.setAlpha(1.0f);
                return;
            }
            this.radialProgressView.setVisibility(0);
            this.radialProgressView.setScaleX(1.0f);
            this.radialProgressView.setScaleY(1.0f);
            this.radialProgressView.setAlpha(1.0f);
            return;
        }
        this.radialProgressView.setTag(null);
        if (z4) {
            this.floatingProgressView.setVisibility(4);
            this.floatingButtonIcon.setVisibility(0);
            this.floatingButtonContainer.setEnabled(true);
            this.floatingProgressView.setScaleX(0.1f);
            this.floatingProgressView.setScaleY(0.1f);
            this.floatingProgressView.setAlpha(0.0f);
            this.floatingButtonIcon.setScaleX(1.0f);
            this.floatingButtonIcon.setScaleY(1.0f);
            this.floatingButtonIcon.setAlpha(1.0f);
            return;
        }
        this.radialProgressView.setVisibility(4);
        this.radialProgressView.setScaleX(0.1f);
        this.radialProgressView.setScaleY(0.1f);
        this.radialProgressView.setAlpha(0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDoneProgress$16(int i, boolean z, boolean z2) {
        int i2 = this.currentDoneType;
        this.currentDoneType = i;
        lambda$showEditDoneProgress$15(z, z2, true);
        this.currentDoneType = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditDoneProgress$17(boolean z, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        if (z) {
            float f = 1.0f - floatValue;
            float f2 = (f * 0.9f) + 0.1f;
            this.floatingButtonIcon.setScaleX(f2);
            this.floatingButtonIcon.setScaleY(f2);
            this.floatingButtonIcon.setAlpha(f);
            float f3 = (0.9f * floatValue) + 0.1f;
            this.floatingProgressView.setScaleX(f3);
            this.floatingProgressView.setScaleY(f3);
            this.floatingProgressView.setAlpha(floatValue);
            return;
        }
        float f4 = (0.9f * floatValue) + 0.1f;
        this.radialProgressView.setScaleX(f4);
        this.radialProgressView.setScaleY(f4);
        this.radialProgressView.setAlpha(floatValue);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void needShowProgress(int i) {
        needShowProgress(i, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void needShowProgress(int i, boolean z) {
        if (isInCancelAccountDeletionMode() && i == 0) {
            if (this.cancelDeleteProgressDialog != null || getParentActivity() == null || getParentActivity().isFinishing()) {
                return;
            }
            AlertDialog alertDialog = new AlertDialog(getParentActivity(), 3);
            this.cancelDeleteProgressDialog = alertDialog;
            alertDialog.setCanCancel(false);
            this.cancelDeleteProgressDialog.show();
            return;
        }
        this.progressRequestId = i;
        showEditDoneProgress(true, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void needHideProgress(boolean z) {
        needHideProgress(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void needHideProgress(boolean z, boolean z2) {
        AlertDialog alertDialog;
        if (this.progressRequestId != 0) {
            if (z) {
                ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.progressRequestId, true);
            }
            this.progressRequestId = 0;
        }
        if (isInCancelAccountDeletionMode() && (alertDialog = this.cancelDeleteProgressDialog) != null) {
            alertDialog.dismiss();
            this.cancelDeleteProgressDialog = null;
        }
        showEditDoneProgress(false, z2);
    }

    public void setPage(int i, boolean z, Bundle bundle, boolean z2) {
        final boolean z3 = i == 0 || i == 5 || i == 6 || i == 9 || i == 10 || i == 12 || i == 16;
        if (i == this.currentViewNum) {
            z = false;
        }
        if (z3) {
            this.currentDoneType = 1;
            showDoneButton(false, z);
            showEditDoneProgress(false, z);
            this.currentDoneType = 0;
            showEditDoneProgress(false, z);
            if (!z) {
                showDoneButton(true, false);
            }
        } else {
            this.currentDoneType = 0;
            showDoneButton(false, z);
            showEditDoneProgress(false, z);
            if (i != 8) {
                this.currentDoneType = 1;
            }
        }
        if (z) {
            SlideView[] slideViewArr = this.views;
            final SlideView slideView = slideViewArr[this.currentViewNum];
            SlideView slideView2 = slideViewArr[i];
            this.currentViewNum = i;
            this.backButtonView.setVisibility((slideView2.needBackButton() || this.newAccount) ? 0 : 8);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.backButtonView.setColorFilter(-1);
            } else {
                this.backButtonView.setColorFilter(-8421505);
            }
            slideView2.setParams(bundle, false);
            setParentActivityTitle(slideView2.getHeaderName());
            slideView2.onShow();
            int i2 = AndroidUtilities.displaySize.x;
            if (z2) {
                i2 = -i2;
            }
            slideView2.setX(i2);
            slideView2.setVisibility(0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.11
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (LoginActivity.this.currentDoneType == 0 && z3) {
                        LoginActivity.this.showDoneButton(true, true);
                    }
                    slideView.setVisibility(8);
                    slideView.onHide();
                    slideView.setX(0.0f);
                }
            });
            Animator[] animatorArr = new Animator[2];
            Property property = View.TRANSLATION_X;
            float[] fArr = new float[1];
            fArr[0] = z2 ? AndroidUtilities.displaySize.x : -AndroidUtilities.displaySize.x;
            animatorArr[0] = ObjectAnimator.ofFloat(slideView, (Property<SlideView, Float>) property, fArr);
            animatorArr[1] = ObjectAnimator.ofFloat(slideView2, (Property<SlideView, Float>) View.TRANSLATION_X, 0.0f);
            animatorSet.playTogether(animatorArr);
            animatorSet.setDuration(300L);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.start();
            setCustomKeyboardVisible(slideView2.hasCustomKeyboard(), true);
            return;
        }
        this.backButtonView.setVisibility((this.views[i].needBackButton() || this.newAccount) ? 0 : 8);
        if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
            this.backButtonView.setColorFilter(-1);
        } else {
            this.backButtonView.setColorFilter(-8421505);
        }
        this.views[this.currentViewNum].setVisibility(8);
        this.views[this.currentViewNum].onHide();
        this.currentViewNum = i;
        this.views[i].setParams(bundle, false);
        this.views[i].setVisibility(0);
        setParentActivityTitle(this.views[i].getHeaderName());
        this.views[i].onShow();
        setCustomKeyboardVisible(this.views[i].hasCustomKeyboard(), false);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void saveSelfArgs(Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("currentViewNum", this.currentViewNum);
            bundle2.putInt("syncContacts", this.syncContacts ? 1 : 0);
            for (int i = 0; i <= this.currentViewNum; i++) {
                SlideView slideView = this.views[i];
                if (slideView != null) {
                    slideView.saveStateParams(bundle2);
                }
            }
            Context context = ApplicationLoader.applicationContext;
            StringBuilder sb = new StringBuilder();
            sb.append("logininfo2");
            sb.append(this.newAccount ? "_" + this.currentAccount : "");
            SharedPreferences.Editor edit = context.getSharedPreferences(sb.toString(), 0).edit();
            edit.clear();
            putBundleToEditor(bundle2, edit, null);
            edit.commit();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void needFinishActivity(final boolean z, boolean z2, int i) {
        if (getParentActivity() != null) {
            AndroidUtilities.setLightStatusBar(getParentActivity().getWindow(), false);
        }
        clearCurrentState();
        if (getParentActivity() instanceof LaunchActivity) {
            if (this.newAccount) {
                this.newAccount = false;
                ((LaunchActivity) getParentActivity()).switchToAccount(this.currentAccount, false, new GenericProvider() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda27
                    @Override // org.telegram.messenger.GenericProvider
                    public final Object provide(Object obj) {
                        DialogsActivity lambda$needFinishActivity$18;
                        lambda$needFinishActivity$18 = LoginActivity.lambda$needFinishActivity$18(z, (Void) obj);
                        return lambda$needFinishActivity$18;
                    }
                });
                finishFragment();
                return;
            }
            if (z && z2) {
                TwoStepVerificationSetupActivity twoStepVerificationSetupActivity = new TwoStepVerificationSetupActivity(6, null);
                twoStepVerificationSetupActivity.setBlockingAlert(i);
                twoStepVerificationSetupActivity.setFromRegistration(true);
                presentFragment(twoStepVerificationSetupActivity, true);
            } else {
                Bundle bundle = new Bundle();
                bundle.putBoolean("afterSignup", z);
                presentFragment(new DialogsActivity(bundle), true);
            }
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
            LocaleController.getInstance().loadRemoteLanguages(this.currentAccount);
            RestrictedLanguagesSelectActivity.checkRestrictedLanguages(true);
            return;
        }
        if (getParentActivity() instanceof ExternalActionActivity) {
            ((ExternalActionActivity) getParentActivity()).onFinishLogin();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ DialogsActivity lambda$needFinishActivity$18(boolean z, Void r2) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("afterSignup", z);
        return new DialogsActivity(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthSuccess(TLRPC$TL_auth_authorization tLRPC$TL_auth_authorization) {
        onAuthSuccess(tLRPC$TL_auth_authorization, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAuthSuccess(TLRPC$TL_auth_authorization tLRPC$TL_auth_authorization, boolean z) {
        BuildVars.isSubmitLogsTime = System.currentTimeMillis();
        MessagesController.getInstance(this.currentAccount).cleanup();
        ConnectionsManager.getInstance(this.currentAccount).setUserId(tLRPC$TL_auth_authorization.user.id);
        UserConfig.getInstance(this.currentAccount).clearConfig();
        MessagesController.getInstance(this.currentAccount).cleanup();
        UserConfig.getInstance(this.currentAccount).syncContacts = this.syncContacts;
        UserConfig.getInstance(this.currentAccount).setCurrentUser(tLRPC$TL_auth_authorization.user);
        UserConfig.getInstance(this.currentAccount).saveConfig(true);
        MessagesStorage.getInstance(this.currentAccount).cleanup(true);
        ArrayList<TLRPC$User> arrayList = new ArrayList<>();
        arrayList.add(tLRPC$TL_auth_authorization.user);
        MessagesStorage.getInstance(this.currentAccount).putUsersAndChats(arrayList, null, true, true);
        MessagesController.getInstance(this.currentAccount).putUser(tLRPC$TL_auth_authorization.user, false);
        ContactsController.getInstance(this.currentAccount).checkAppAccount();
        MessagesController.getInstance(this.currentAccount).checkPromoInfo(true);
        ConnectionsManager.getInstance(this.currentAccount).updateDcSettings();
        if (tLRPC$TL_auth_authorization.future_auth_token != null) {
            AuthTokensHelper.saveLogInToken(tLRPC$TL_auth_authorization);
        } else {
            FileLog.d("onAuthSuccess future_auth_token is empty");
        }
        if (z) {
            MessagesController.getInstance(this.currentAccount).putDialogsEndReachedAfterRegistration();
        }
        MediaDataController.getInstance(this.currentAccount).loadStickersByEmojiOrName(AndroidUtilities.STICKERS_PLACEHOLDER_PACK_NAME, false, true);
        needFinishActivity(z, tLRPC$TL_auth_authorization.setup_password_required, tLRPC$TL_auth_authorization.otherwise_relogin_days);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillNextCodeParams(Bundle bundle, TLRPC$TL_account_sentEmailCode tLRPC$TL_account_sentEmailCode) {
        bundle.putString("emailPattern", tLRPC$TL_account_sentEmailCode.email_pattern);
        bundle.putInt("length", tLRPC$TL_account_sentEmailCode.length);
        setPage(13, true, bundle, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: fillNextCodeParams, reason: merged with bridge method [inline-methods] */
    public void lambda$resendCodeFromSafetyNet$19(Bundle bundle, TLRPC$auth_SentCode tLRPC$auth_SentCode) {
        lambda$fillNextCodeParams$23(bundle, tLRPC$auth_SentCode, true);
    }

    private void resendCodeFromSafetyNet(final Bundle bundle, TLRPC$auth_SentCode tLRPC$auth_SentCode) {
        if (this.isRequestingFirebaseSms) {
            needHideProgress(false);
            this.isRequestingFirebaseSms = false;
            TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode = new TLRPC$TL_auth_resendCode();
            tLRPC$TL_auth_resendCode.phone_number = bundle.getString("phoneFormated");
            tLRPC$TL_auth_resendCode.phone_code_hash = tLRPC$auth_SentCode.phone_code_hash;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_auth_resendCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda28
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.this.lambda$resendCodeFromSafetyNet$22(bundle, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resendCodeFromSafetyNet$22(final Bundle bundle, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        if (tLObject != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, tLObject);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.this.lambda$resendCodeFromSafetyNet$21();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resendCodeFromSafetyNet$21() {
        if (getParentActivity() == null || getParentActivity().isFinishing() || getContext() == null) {
            return;
        }
        new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(R.string.RestorePasswordNoEmailTitle)).setMessage(LocaleController.getString(R.string.SafetyNetErrorOccurred)).setPositiveButton(LocaleController.getString(R.string.OK), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.this.lambda$resendCodeFromSafetyNet$20(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resendCodeFromSafetyNet$20(DialogInterface dialogInterface, int i) {
        if (this.currentViewNum != 0) {
            setPage(0, true, null, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: fillNextCodeParams, reason: merged with bridge method [inline-methods] */
    public void lambda$fillNextCodeParams$23(final Bundle bundle, final TLRPC$auth_SentCode tLRPC$auth_SentCode, final boolean z) {
        TLRPC$auth_SentCodeType tLRPC$auth_SentCodeType = tLRPC$auth_SentCode.type;
        if ((tLRPC$auth_SentCodeType instanceof TLRPC$TL_auth_sentCodeTypeFirebaseSms) && !tLRPC$auth_SentCodeType.verifiedFirebase && !this.isRequestingFirebaseSms) {
            if (PushListenerController.GooglePushListenerServiceProvider.INSTANCE.hasServices()) {
                needShowProgress(0);
                this.isRequestingFirebaseSms = true;
                SafetyNet.getClient(ApplicationLoader.applicationContext).attest(tLRPC$auth_SentCode.type.nonce, BuildVars.SAFETYNET_KEY).addOnSuccessListener(new OnSuccessListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda14
                    @Override // com.google.android.gms.tasks.OnSuccessListener
                    public final void onSuccess(Object obj) {
                        LoginActivity.this.lambda$fillNextCodeParams$25(bundle, tLRPC$auth_SentCode, z, (SafetyNetApi$AttestationResponse) obj);
                    }
                }).addOnFailureListener(new OnFailureListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.gms.tasks.OnFailureListener
                    public final void onFailure(Exception exc) {
                        LoginActivity.this.lambda$fillNextCodeParams$26(bundle, tLRPC$auth_SentCode, exc);
                    }
                });
                return;
            } else {
                FileLog.d("Resend firebase sms because firebase is not available");
                resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
                return;
            }
        }
        bundle.putString("phoneHash", tLRPC$auth_SentCode.phone_code_hash);
        TLRPC$auth_CodeType tLRPC$auth_CodeType = tLRPC$auth_SentCode.next_type;
        if (tLRPC$auth_CodeType instanceof TLRPC$TL_auth_codeTypeCall) {
            bundle.putInt("nextType", 4);
        } else if (tLRPC$auth_CodeType instanceof TLRPC$TL_auth_codeTypeFlashCall) {
            bundle.putInt("nextType", 3);
        } else if (tLRPC$auth_CodeType instanceof TLRPC$TL_auth_codeTypeSms) {
            bundle.putInt("nextType", 2);
        } else if (tLRPC$auth_CodeType instanceof TLRPC$TL_auth_codeTypeMissedCall) {
            bundle.putInt("nextType", 11);
        } else if (tLRPC$auth_CodeType instanceof TLRPC$TL_auth_codeTypeFragmentSms) {
            bundle.putInt("nextType", 15);
        }
        if (tLRPC$auth_SentCode.type instanceof TLRPC$TL_auth_sentCodeTypeApp) {
            bundle.putInt("type", 1);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            setPage(1, z, bundle, false);
            return;
        }
        if (tLRPC$auth_SentCode.timeout == 0) {
            tLRPC$auth_SentCode.timeout = 60;
        }
        bundle.putInt("timeout", tLRPC$auth_SentCode.timeout * 1000);
        TLRPC$auth_SentCodeType tLRPC$auth_SentCodeType2 = tLRPC$auth_SentCode.type;
        if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeCall) {
            bundle.putInt("type", 4);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            setPage(4, z, bundle, false);
            return;
        }
        if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeFlashCall) {
            bundle.putInt("type", 3);
            bundle.putString("pattern", tLRPC$auth_SentCode.type.pattern);
            setPage(3, z, bundle, false);
            return;
        }
        if ((tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeSms) || (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeFirebaseSms)) {
            bundle.putInt("type", 2);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            bundle.putBoolean("firebase", tLRPC$auth_SentCode.type instanceof TLRPC$TL_auth_sentCodeTypeFirebaseSms);
            setPage(2, z, bundle, false);
            return;
        }
        if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeFragmentSms) {
            bundle.putInt("type", 15);
            bundle.putString("url", tLRPC$auth_SentCode.type.url);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            setPage(15, z, bundle, false);
            return;
        }
        if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeMissedCall) {
            bundle.putInt("type", 11);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            bundle.putString("prefix", tLRPC$auth_SentCode.type.prefix);
            setPage(11, z, bundle, false);
            return;
        }
        if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeSetUpEmailRequired) {
            bundle.putBoolean("googleSignInAllowed", tLRPC$auth_SentCodeType2.google_signin_allowed);
            setPage(12, z, bundle, false);
        } else if (tLRPC$auth_SentCodeType2 instanceof TLRPC$TL_auth_sentCodeTypeEmailCode) {
            bundle.putBoolean("googleSignInAllowed", tLRPC$auth_SentCodeType2.google_signin_allowed);
            bundle.putString("emailPattern", tLRPC$auth_SentCode.type.email_pattern);
            bundle.putInt("length", tLRPC$auth_SentCode.type.length);
            bundle.putInt("nextPhoneLoginDate", tLRPC$auth_SentCode.type.next_phone_login_date);
            bundle.putInt("resetAvailablePeriod", tLRPC$auth_SentCode.type.reset_available_period);
            bundle.putInt("resetPendingDate", tLRPC$auth_SentCode.type.reset_pending_date);
            setPage(14, z, bundle, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillNextCodeParams$25(final Bundle bundle, final TLRPC$auth_SentCode tLRPC$auth_SentCode, final boolean z, SafetyNetApi$AttestationResponse safetyNetApi$AttestationResponse) {
        String jwsResult = safetyNetApi$AttestationResponse.getJwsResult();
        if (jwsResult != null) {
            TLRPC$TL_auth_requestFirebaseSms tLRPC$TL_auth_requestFirebaseSms = new TLRPC$TL_auth_requestFirebaseSms();
            tLRPC$TL_auth_requestFirebaseSms.phone_number = bundle.getString("phoneFormated");
            tLRPC$TL_auth_requestFirebaseSms.phone_code_hash = tLRPC$auth_SentCode.phone_code_hash;
            tLRPC$TL_auth_requestFirebaseSms.safety_net_token = jwsResult;
            tLRPC$TL_auth_requestFirebaseSms.flags |= 1;
            String[] split = jwsResult.split("\\.");
            if (split.length > 0) {
                try {
                    JSONObject jSONObject = new JSONObject(new String(Base64.decode(split[1].getBytes(StandardCharsets.UTF_8), 0)));
                    if (jSONObject.optBoolean("basicIntegrity") && jSONObject.optBoolean("ctsProfileMatch")) {
                        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_auth_requestFirebaseSms, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda30
                            @Override // org.telegram.tgnet.RequestDelegate
                            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                LoginActivity.this.lambda$fillNextCodeParams$24(tLRPC$auth_SentCode, bundle, z, tLObject, tLRPC$TL_error);
                            }
                        }, 10);
                    } else {
                        FileLog.d("Resend firebase sms because ctsProfileMatch or basicIntegrity = false");
                        resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
                    }
                    return;
                } catch (JSONException e) {
                    FileLog.e(e);
                    FileLog.d("Resend firebase sms because of exception");
                    resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
                    return;
                }
            }
            FileLog.d("Resend firebase sms because can't split JWS token");
            resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
            return;
        }
        FileLog.d("Resend firebase sms because JWS = null");
        resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillNextCodeParams$24(final TLRPC$auth_SentCode tLRPC$auth_SentCode, final Bundle bundle, final boolean z, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        if (tLObject instanceof TLRPC$TL_boolTrue) {
            needHideProgress(false);
            this.isRequestingFirebaseSms = false;
            tLRPC$auth_SentCode.type.verifiedFirebase = true;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.this.lambda$fillNextCodeParams$23(bundle, tLRPC$auth_SentCode, z);
                }
            });
            return;
        }
        FileLog.d("Resend firebase sms because auth.requestFirebaseSms = false");
        resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillNextCodeParams$26(Bundle bundle, TLRPC$auth_SentCode tLRPC$auth_SentCode, Exception exc) {
        FileLog.e(exc);
        FileLog.d("Resend firebase sms because of safetynet exception");
        resendCodeFromSafetyNet(bundle, tLRPC$auth_SentCode);
    }

    public class JsBridge {
        private WebView wvCaptcha;

        public JsBridge(WebView webView) {
            this.wvCaptcha = webView;
        }

        @JavascriptInterface
        public void getData(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.JsBridge.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has("errorCode")) {
                            return;
                        }
                        LoginActivity.this.captchaJson = jSONObject.toString();
                        JsBridge.this.wvCaptcha.setVisibility(4);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public class MergeUserNameView extends SlideView {
        private boolean checkUserNameSuccess;
        private int currentRetryCount;
        private FrameLayout fl_root;
        private boolean isExistUserName;
        private ImageView ivImage;
        private JMTJzvdStdAssert jzvdStd;
        private LinearLayout ll_guest_login;
        private Context mContext;
        private OutlineEditText oetPassword;
        private OutlineEditText oetUsername;
        private ScrollView sv_user_login;
        private TextView tvPasswordPoint;
        private TextView tvUsernamePoint;
        private int tvVersionCount;
        private TextView tv_guest_point;
        private TextView tv_service;
        private TextView tv_version;
        private TextView tv_welcome_subtitle;
        private TextView tv_welcome_title;
        private WebView wvCaptcha;

        static /* synthetic */ int access$2908(MergeUserNameView mergeUserNameView) {
            int i = mergeUserNameView.tvVersionCount;
            mergeUserNameView.tvVersionCount = i + 1;
            return i;
        }

        static /* synthetic */ int access$4308(MergeUserNameView mergeUserNameView) {
            int i = mergeUserNameView.currentRetryCount;
            mergeUserNameView.currentRetryCount = i + 1;
            return i;
        }

        public MergeUserNameView(Context context) {
            super(context);
            this.tvVersionCount = 1;
            this.checkUserNameSuccess = false;
            this.isExistUserName = false;
            this.currentRetryCount = 0;
            this.mContext = context;
            View inflate = View.inflate(context, R.layout.view_jmt_merge, this);
            this.fl_root = (FrameLayout) inflate.findViewById(R.id.fl_root);
            this.jzvdStd = (JMTJzvdStdAssert) inflate.findViewById(R.id.jz_video);
            this.ivImage = (ImageView) inflate.findViewById(R.id.iv_image);
            if (BuildVars.isShowLoginVideo) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                Jzvd.setVideoImageDisplayType(2);
                Jzvd.NORMAL_ORIENTATION = 1;
                JZDataSource jZDataSource = new JZDataSource("jmt_login_video.mp4");
                jZDataSource.looping = true;
                this.jzvdStd.setUp(jZDataSource, 0, JMTJZMediaSystemAssert.class);
                this.jzvdStd.startVideo();
            } else {
                this.jzvdStd.setVisibility(8);
            }
            if (BuildVars.isShowLoginImage) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                try {
                    this.ivImage.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("jmt_login_image.png")));
                } catch (Exception unused) {
                    this.ivImage.setVisibility(8);
                }
            } else {
                this.ivImage.setVisibility(8);
            }
            this.sv_user_login = (ScrollView) inflate.findViewById(R.id.sv_user_login);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_welcome_title);
            this.tv_welcome_title = textView;
            int i = Theme.key_windowBackgroundWhiteInputFieldActivated;
            textView.setTextColor(Theme.getColor(i));
            this.tv_welcome_title.setText(LocaleController.getString("JMTWelcomeTitle", R.string.JMTWelcomeTitle));
            TextView textView2 = (TextView) inflate.findViewById(R.id.tv_welcome_subtitle);
            this.tv_welcome_subtitle = textView2;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView2.setTextColor(-1);
            }
            this.tv_welcome_subtitle.setText(LocaleController.getString("JMTWelcomeSubtitle", R.string.JMTWelcomeSubtitle));
            this.oetUsername = (OutlineEditText) inflate.findViewById(R.id.oet_username);
            this.tvUsernamePoint = (TextView) inflate.findViewById(R.id.tv_username_point);
            OutlineEditText outlineEditText = (OutlineEditText) inflate.findViewById(R.id.oet_password);
            this.oetPassword = outlineEditText;
            outlineEditText.setShowEye(BuildVars.isShowPasswordEye);
            this.tvPasswordPoint = (TextView) inflate.findViewById(R.id.tv_password_point);
            this.wvCaptcha = (WebView) inflate.findViewById(R.id.wv_captcha);
            this.ll_guest_login = (LinearLayout) inflate.findViewById(R.id.ll_guest_login);
            TextView textView3 = (TextView) inflate.findViewById(R.id.tv_guest_point);
            this.tv_guest_point = textView3;
            textView3.setText(LocaleController.getString("JMTGuestLogin", R.string.JMTGuestLogin));
            this.tv_version = (TextView) inflate.findViewById(R.id.tv_version);
            showVersion();
            this.tv_version.setOnClickListener(new AnonymousClass1(LoginActivity.this));
            TextView textView4 = (TextView) inflate.findViewById(R.id.tv_service);
            this.tv_service = textView4;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView4.setTextColor(Color.parseColor("#ffffff"));
            } else {
                textView4.setTextColor(Color.parseColor("#a8a8a8"));
            }
            this.tv_service.setText(LocaleController.getString("JMTService", R.string.JMTService));
            if (BuildVars.isShowOnlineService) {
                this.tv_service.setVisibility(0);
            }
            this.tv_service.setOnClickListener(new View.OnClickListener(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(BuildVars.loginServiceUrl)) {
                        Toast.makeText(MergeUserNameView.this.mContext, LocaleController.getString("JMTGetServiceUrl", R.string.JMTGetServiceUrl), 0).show();
                    } else {
                        LoginActivity.this.presentFragment(new JMTWebViewActivity(BuildVars.loginServiceUrl));
                    }
                }
            });
            WebSettings settings = this.wvCaptcha.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setCacheMode(2);
            this.wvCaptcha.setWebViewClient(new WebViewClient(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.3
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    webView.loadUrl(str);
                    return true;
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                    super.onReceivedError(webView, webResourceRequest, webResourceError);
                    MergeUserNameView.this.wvCaptcha.reload();
                }
            });
            settings.setJavaScriptEnabled(true);
            WebView webView = this.wvCaptcha;
            webView.addJavascriptInterface(LoginActivity.this.new JsBridge(webView), "jsBridge");
            this.wvCaptcha.loadUrl("file:///android_asset/tx_captcha.html");
            this.oetUsername.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.4
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetUsername.getEditText().setImeOptions(5);
            this.oetUsername.setIsLogin(BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetUsername.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetUsername.getEditText().setTextColor(-14540254);
            }
            this.oetUsername.getEditText().setHintTextColor(-1);
            this.oetUsername.setHint(LocaleController.getString("JMTUsername", R.string.JMTUsername));
            this.oetUsername.getEditText().addTextChangedListener(new TextWatcher(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.5
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    String obj = MergeUserNameView.this.oetUsername.getEditText().getText().toString();
                    if (obj.isEmpty()) {
                        return;
                    }
                    if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                        MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
                    }
                    MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTCheckUser", R.string.JMTCheckUser));
                    MergeUserNameView.this.checkUsername(obj);
                }
            });
            this.oetPassword.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.6
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.oetPassword.getEditText().setImeOptions(6);
            this.oetPassword.setIsLogin(BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetPassword.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetPassword.getEditText().setTextColor(-14540254);
            }
            this.oetPassword.getEditText().setHintTextColor(-1);
            if (BuildVars.isComplexPassword) {
                this.oetPassword.setHint(LocaleController.getString("JMTPassword", R.string.JMTPassword));
            } else {
                this.oetPassword.setHint(LocaleController.getString("JMTPasswordSimpleHint", R.string.JMTPasswordSimpleHint));
            }
            this.oetPassword.getEditText().addTextChangedListener(new TextWatcher(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.7
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    MergeUserNameView mergeUserNameView = MergeUserNameView.this;
                    mergeUserNameView.checkPassword(mergeUserNameView.oetPassword.getEditText().getText().toString());
                }
            });
            this.oetPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.LoginActivity$MergeUserNameView$$ExternalSyntheticLambda0
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView5, int i2, KeyEvent keyEvent) {
                    boolean lambda$new$0;
                    lambda$new$0 = LoginActivity.MergeUserNameView.this.lambda$new$0(textView5, i2, keyEvent);
                    return lambda$new$0;
                }
            });
            if (!BuildVars.isOpenGuestLogin) {
                this.sv_user_login.setVisibility(0);
            } else {
                this.ll_guest_login.setVisibility(0);
            }
            for (int i2 = 0; i2 < 1; i2++) {
                ConnectionsManager.getInstance(i2).cleanup(false);
            }
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(new TLRPC$JMT_json_config(), new RequestDelegate(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.8
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            MergeUserNameView.this.showVersion();
                            if (tLRPC$TL_error == null) {
                                TLObject tLObject2 = tLObject;
                                if (tLObject2 instanceof TLRPC$Vector) {
                                    Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                    while (it.hasNext()) {
                                        Object next = it.next();
                                        if (next instanceof TLRPC$TL_jsonObjectValue) {
                                            TLRPC$TL_jsonObjectValue tLRPC$TL_jsonObjectValue = (TLRPC$TL_jsonObjectValue) next;
                                            if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterInviteCode")) {
                                                TLRPC$JSONValue tLRPC$JSONValue = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isInviteCode = ((TLRPC$TL_jsonBool) tLRPC$JSONValue).value;
                                                    ((LoginActivityRegisterView) LoginActivity.this.views[5]).lastNameOutlineView.setVisibility(BuildVars.isInviteCode ? 0 : 8);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterPhone")) {
                                                TLRPC$JSONValue tLRPC$JSONValue2 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue2 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.requireRegisterPhone = ((TLRPC$TL_jsonBool) tLRPC$JSONValue2).value;
                                                    ((LoginActivityRegisterView) LoginActivity.this.views[5]).phoneOutlineView.setVisibility(BuildVars.requireRegisterPhone ? 0 : 8);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableRegisterVerifyCode")) {
                                                TLRPC$JSONValue tLRPC$JSONValue3 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue3 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isRegisterVerify = ((TLRPC$TL_jsonBool) tLRPC$JSONValue3).value;
                                                    MergeUserNameView.this.wvCaptcha.setVisibility(BuildVars.isRegisterVerify ? 0 : 4);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("vpnPrefix")) {
                                                TLRPC$JSONValue tLRPC$JSONValue4 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue4 instanceof TLRPC$TL_jsonString) {
                                                    BuildVars.openVPNUrl = ((TLRPC$TL_jsonString) tLRPC$JSONValue4).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("baiduMapAk")) {
                                                TLRPC$JSONValue tLRPC$JSONValue5 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue5 instanceof TLRPC$TL_jsonString) {
                                                    BuildVars.baiduMapAk = ((TLRPC$TL_jsonString) tLRPC$JSONValue5).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("supportLink")) {
                                                TLRPC$JSONValue tLRPC$JSONValue6 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue6 instanceof TLRPC$TL_jsonString) {
                                                    String str = ((TLRPC$TL_jsonString) tLRPC$JSONValue6).value;
                                                    BuildVars.loginServiceUrl = str;
                                                    if (TextUtils.isEmpty(str)) {
                                                        MergeUserNameView.this.tv_service.setVisibility(4);
                                                    } else {
                                                        MergeUserNameView.this.tv_service.setText(LocaleController.getString("JMTService", R.string.JMTService));
                                                    }
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterAvatar")) {
                                                TLRPC$JSONValue tLRPC$JSONValue7 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue7 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isForceSetAvatar = ((TLRPC$TL_jsonBool) tLRPC$JSONValue7).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableViewChannelMember")) {
                                                TLRPC$JSONValue tLRPC$JSONValue8 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue8 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isShowGroupMember = ((TLRPC$TL_jsonBool) tLRPC$JSONValue8).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("memberCannotViewFriendProfile")) {
                                                if (tLRPC$TL_jsonObjectValue.value instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isCanShowFriendInfo = !((TLRPC$TL_jsonBool) r1).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableAppLogout")) {
                                                TLRPC$JSONValue tLRPC$JSONValue9 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue9 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.enableAppLogout = ((TLRPC$TL_jsonBool) tLRPC$JSONValue9).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableQuitGroupMinMember")) {
                                                TLRPC$JSONValue tLRPC$JSONValue10 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue10 instanceof TLRPC$TL_jsonNumber) {
                                                    BuildVars.enableGroupExitCount = ((TLRPC$TL_jsonNumber) tLRPC$JSONValue10).value;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }, 10);
        }

        /* renamed from: org.telegram.ui.LoginActivity$MergeUserNameView$1, reason: invalid class name */
        class AnonymousClass1 implements View.OnClickListener {
            AnonymousClass1(LoginActivity loginActivity) {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MergeUserNameView.this.tvVersionCount >= BuildVars.submitLogsMaxCount) {
                    MergeUserNameView.this.tvVersionCount = 1;
                    MyCOSService.getInstance().submitLogs(new C01021((LoadingPopupView) new XPopup.Builder(MergeUserNameView.this.mContext).dismissOnBackPressed(Boolean.FALSE).isLightNavigationBar(true).asLoading(LocaleController.getString("JMTUploading", R.string.JMTUploading), LoadingPopupView.Style.ProgressBar).show()));
                    return;
                }
                MergeUserNameView.access$2908(MergeUserNameView.this);
            }

            /* renamed from: org.telegram.ui.LoginActivity$MergeUserNameView$1$1, reason: invalid class name and collision with other inner class name */
            class C01021 implements Callback {
                final /* synthetic */ LoadingPopupView val$loadingPopupView;

                C01021(LoadingPopupView loadingPopupView) {
                    this.val$loadingPopupView = loadingPopupView;
                }

                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.1.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            C01021.this.val$loadingPopupView.dismiss();
                            Toast.makeText(MergeUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError), 0).show();
                        }
                    });
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, final Response response) throws IOException {
                    final LoadingPopupView loadingPopupView = this.val$loadingPopupView;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$MergeUserNameView$1$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.MergeUserNameView.AnonymousClass1.C01021.this.lambda$onResponse$0(loadingPopupView, response);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onResponse$0(LoadingPopupView loadingPopupView, Response response) {
                    loadingPopupView.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(MergeUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogSuccess", R.string.JMTSubmitLogSuccess), 0).show();
                        return;
                    }
                    Toast.makeText(MergeUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError) + ":" + response.code(), 0).show();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        public void showVersion() {
            String str;
            try {
                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                int i = packageInfo.versionCode / 10;
                if (BuildVars.isOpenCloudDefense) {
                    str = "-s";
                } else {
                    str = ConnectionsManager.connectedCurrentIp;
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split("\\.");
                        if (split.length != 0) {
                            str = "-" + split[split.length - 1];
                        }
                    }
                }
                this.tv_version.setText("v" + packageInfo.versionName + "(" + i + str + ")");
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        private String getDeviceId() {
            String clientId = DeviceIdentifier.getClientId(true);
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getOAID(this.mContext);
            }
            if (clientId.equals("00000000-0000-0000-0000-000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (clientId.equals("00000000000000000000000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getPseudoID();
            }
            return TextUtils.isEmpty(clientId) ? DeviceIdentifier.getGUID(this.mContext) : clientId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void signInByGuest() {
            LoginActivity.this.needShowProgress(1234567890);
            if (ConnectionsManager.getInstance(UserConfig.selectedAccount).getConnectionState() == 1) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.9
                    @Override // java.lang.Runnable
                    public void run() {
                        MergeUserNameView.this.signInByGuest();
                    }
                }, 200L);
            } else {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.10
                    @Override // java.lang.Runnable
                    public void run() {
                        int i = BuildVars.controlInstallSDK;
                        if (i == 1) {
                            OpenInstall.getInstallCanRetry(new AppInstallRetryAdapter() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.10.1
                                @Override // com.fm.openinstall.listener.AppInstallRetryAdapter
                                public void onInstall(AppData appData, boolean z) {
                                    String channel = appData.getChannel();
                                    if (z) {
                                        if (MergeUserNameView.this.currentRetryCount >= 3) {
                                            MergeUserNameView.this.realSignInByGuest(channel);
                                            return;
                                        } else {
                                            MergeUserNameView.access$4308(MergeUserNameView.this);
                                            MergeUserNameView.this.signInByGuest();
                                            return;
                                        }
                                    }
                                    MergeUserNameView.this.realSignInByGuest(channel);
                                }
                            }, 2);
                        } else if (i == 2) {
                            XInstall.getInstallParam(new XInstallListener() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.10.2
                                @Override // com.xinstall.listener.XInstallListener
                                public void onInstallFinish(XAppData xAppData, XAppError xAppError) {
                                    String channelCode = xAppData.getChannelCode();
                                    if (TextUtils.isEmpty(channelCode)) {
                                        if (MergeUserNameView.this.currentRetryCount >= 3) {
                                            MergeUserNameView.this.realSignInByGuest(channelCode);
                                            return;
                                        } else {
                                            MergeUserNameView.access$4308(MergeUserNameView.this);
                                            MergeUserNameView.this.signInByGuest();
                                            return;
                                        }
                                    }
                                    MergeUserNameView.this.realSignInByGuest(channelCode);
                                }
                            });
                        }
                    }
                }, 3000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void realSignInByGuest(String str) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：MergeUserNameView，游客登录，开始请求：JMT_signInByGuestV2，channelCode=" + str);
            }
            TLRPC$JMT_signInByGuestV2 tLRPC$JMT_signInByGuestV2 = new TLRPC$JMT_signInByGuestV2();
            tLRPC$JMT_signInByGuestV2.device_id = getDeviceId();
            tLRPC$JMT_signInByGuestV2.channel_code = str;
            ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$JMT_signInByGuestV2, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.11
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("现在是：MergeUserNameView，游客登录，请求成功：JMT_signInByGuestV2");
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.11.1
                        @Override // java.lang.Runnable
                        public void run() {
                            LoginActivity.this.needHideProgress(false);
                            BuildVars.isSignInByGuest = false;
                            if (tLRPC$TL_error == null) {
                                if (BuildVars.LOGS_ENABLED) {
                                    FileLog.d("现在是：MergeUserNameView，游客登录，返回成功：JMT_signInByGuestV2");
                                }
                                TLRPC$JMT_auth_authorizationV2 tLRPC$JMT_auth_authorizationV2 = (TLRPC$JMT_auth_authorizationV2) tLObject;
                                if (tLRPC$JMT_auth_authorizationV2.new_user == 1) {
                                    if (BuildVars.LOGS_ENABLED) {
                                        FileLog.d("现在是：MergeUserNameView，游客登录，返回成功：JMT_signInByGuestV2，这是新用户");
                                    }
                                    if (!BuildVars.isReportRegisterByGuest) {
                                        BuildVars.isReportRegisterByGuest = true;
                                        int i = BuildVars.controlInstallSDK;
                                        if (i == 1) {
                                            OpenInstall.reportRegister();
                                        } else if (i == 2) {
                                            XInstall.reportRegister();
                                        }
                                        if (BuildVars.LOGS_ENABLED) {
                                            FileLog.d("现在是：MergeUserNameView，游客登录，上报SDK注册调用成功：" + BuildVars.controlInstallSDK);
                                        }
                                    }
                                }
                                LoginActivity.this.onAuthSuccess(tLRPC$JMT_auth_authorizationV2.v1);
                                return;
                            }
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.d("现在是：MergeUserNameView，游客登录，返回失败：JMT_signInByGuestV2");
                            }
                            if ("IP_BLOCKED".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPBlocked", R.string.JMTErrIPBlocked));
                                return;
                            }
                            if ("ACCOUNT_BLOCKED".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAccountBlocked", R.string.JMTErrAccountBlocked));
                                return;
                            }
                            if ("IP_NOT_IN_WHITE_LIST".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPNotInWhiteList", R.string.JMTErrIPNotInWhiteList));
                                return;
                            }
                            if ("APP_VERSION_DEPRECATED".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAppVersionDeprecated", R.string.JMTErrAppVersionDeprecated));
                                return;
                            }
                            if ("INTERNAL_SERVER_ERROR".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrServerBusy", R.string.JMTErrServerBusy));
                                return;
                            }
                            if ("PHONE_NUMBER_UNOCCUPIED".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhoneNumberUnoccupied", R.string.JMTErrPhoneNumberUnoccupied));
                                return;
                            }
                            if ("CLIENT_DANGEROUS".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrClientDangerous", R.string.JMTErrClientDangerous));
                                return;
                            }
                            if ("PHONE_PASSWORD_FLOOD".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhonePasswordFlood", R.string.JMTErrPhonePasswordFlood));
                                return;
                            }
                            if (tLRPC$TL_error.text.startsWith("PASSWORD_ERROR_")) {
                                String[] split = tLRPC$TL_error.text.replace("PASSWORD_ERROR_", "").split("_");
                                if (split.length != 2) {
                                    MergeUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                    MergeUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("PasswordDoNotMatch", R.string.vertify_failed));
                                    return;
                                } else {
                                    int parseInt = Integer.parseInt(split[0]);
                                    new XPopup.Builder(MergeUserNameView.this.getContext()).popupAnimation(PopupAnimation.TranslateAlphaFromTop).isCenterHorizontal(true).offsetY(200).asCustom(new JMTErrorMsgPopup(MergeUserNameView.this.getContext(), LocaleController.formatString("JMTLoginErrorMaxCount", R.string.JMTLoginErrorMaxCount, Integer.valueOf(parseInt), Long.valueOf(Long.parseLong(split[1]) - parseInt)))).show();
                                    return;
                                }
                            }
                            if (tLRPC$TL_error.text.startsWith("PHONE_NUMBER_BANNED")) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrBannedPhone", R.string.JMTErrBannedPhone));
                                return;
                            }
                            if (tLRPC$TL_error.text.contains("TOO_OFTEN")) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTIPMax", R.string.JMTIPMax));
                            } else if ("TOO_OFTEN".equals(tLRPC$TL_error.text)) {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTIPMax", R.string.JMTIPMax));
                            } else {
                                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                            }
                        }
                    });
                }
            }, 10);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (BuildVars.isOpenGuestLogin) {
                if (BuildVars.isSignInByGuest) {
                    return;
                }
                BuildVars.isSignInByGuest = true;
                signInByGuest();
                return;
            }
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            final String trim = this.oetUsername.getEditText().getText().toString().trim();
            final String trim2 = this.oetPassword.getEditText().getText().toString().trim();
            if (trim.isEmpty() || trim2.isEmpty()) {
                if (trim.isEmpty()) {
                    this.tvUsernamePoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvUsernamePoint.setText(LocaleController.getString("JMTUsernameEmpty", R.string.JMTUsernameEmpty));
                    return;
                } else {
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordEmpty", R.string.JMTPasswordEmpty));
                    return;
                }
            }
            if (BuildVars.isLimitUserNameFirstLetter && !trim.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (!this.checkUserNameSuccess) {
                checkUsername(trim);
                return;
            }
            String str2 = "";
            if (!this.isExistUserName) {
                if (BuildVars.isLimitUserName) {
                    if (trim.length() < BuildVars.limitUserNameMin || trim.length() > BuildVars.limitUserNameMax) {
                        this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                        this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameControl", R.string.JMTUserNameControl) + BuildVars.limitUserNameMin + "-" + BuildVars.limitUserNameMax + LocaleController.getString("JMTUserNameControlEnd", R.string.JMTUserNameControlEnd));
                        return;
                    }
                } else if (trim.length() > 30) {
                    this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                    this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameMaxLength", R.string.JMTUserNameMaxLength));
                    return;
                }
                if (BuildVars.isComplexPassword) {
                    if (BuildVars.isCheckPasswordUpperCase) {
                        if (!trim2.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$")) {
                            this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                            String string = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                            if (!trim2.matches(".*[a-z]+.*")) {
                                str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                            } else if (!trim2.matches(".*[A-Z]+.*")) {
                                str2 = LocaleController.getString("JMTQueShaoDaXie", R.string.JMTQueShaoDaXie);
                            } else if (!trim2.matches(".*[0-9]+.*")) {
                                str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                            }
                            this.tvPasswordPoint.setText(string + " - " + str2);
                            return;
                        }
                    } else if (!trim2.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,16}$")) {
                        this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                        String string2 = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                        if (!trim2.matches(".*[a-z]+.*")) {
                            str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                        } else if (!trim2.matches(".*[0-9]+.*")) {
                            str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                        }
                        this.tvPasswordPoint.setText(string2 + " - " + str2);
                        return;
                    }
                } else if (trim2.length() < 6 || trim2.length() > 16) {
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                    this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordSimple", R.string.JMTPasswordSimple));
                    return;
                }
            }
            if (!BuildVars.isRegisterVerify || !TextUtils.isEmpty(LoginActivity.this.captchaJson)) {
                try {
                    if (TextUtils.isEmpty(LoginActivity.this.captchaJson)) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("device_id", getDeviceId());
                        str2 = jSONObject.toString();
                    } else {
                        JSONObject jSONObject2 = new JSONObject(LoginActivity.this.captchaJson);
                        jSONObject2.put("device_id", getDeviceId());
                        str2 = jSONObject2.toString();
                    }
                } catch (JSONException unused) {
                }
                TLRPC$JMT_signInByUsernameV2 tLRPC$JMT_signInByUsernameV2 = new TLRPC$JMT_signInByUsernameV2();
                tLRPC$JMT_signInByUsernameV2.username = trim;
                tLRPC$JMT_signInByUsernameV2.password = trim2;
                tLRPC$JMT_signInByUsernameV2.ext = str2;
                LoginActivity.this.needShowProgress(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$JMT_signInByUsernameV2, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.12
                    @Override // org.telegram.tgnet.RequestDelegate
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.12.1
                            @Override // java.lang.Runnable
                            public void run() {
                                LoginActivity.this.needHideProgress(false);
                                TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                if (tLRPC$TL_error2 == null) {
                                    TLObject tLObject2 = tLObject;
                                    if (!(tLObject2 instanceof TLRPC$TL_auth_authorizationSignUpRequired)) {
                                        LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject2);
                                        return;
                                    }
                                    TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService = ((TLRPC$TL_auth_authorizationSignUpRequired) tLObject2).terms_of_service;
                                    if (tLRPC$TL_help_termsOfService != null) {
                                        LoginActivity.this.currentTermsOfService = tLRPC$TL_help_termsOfService;
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", trim);
                                    bundle.putString("password", trim2);
                                    bundle.putString("ext", "");
                                    LoginActivity.this.setPage(5, true, bundle, false);
                                    return;
                                }
                                if ("PASSWORD_HASH_INVALID".equals(tLRPC$TL_error2.text)) {
                                    MergeUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                    MergeUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                                } else if ("LOGIN_DANGEROUS".equals(tLRPC$TL_error.text)) {
                                    new XPopup.Builder(MergeUserNameView.this.mContext).autoOpenSoftInput(Boolean.TRUE).asCustom(new JMTDangerPopup(MergeUserNameView.this.mContext, new JMTDangerPopup.OnSuccessListener(this) { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.12.1.1
                                        @Override // org.telegram.ui.JMTDangerPopup.OnSuccessListener
                                        public void onSuccess() {
                                        }
                                    })).show();
                                } else if ("IP_BLOCKED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPBlocked", R.string.JMTErrIPBlocked));
                                } else if ("ACCOUNT_BLOCKED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAccountBlocked", R.string.JMTErrAccountBlocked));
                                } else if ("IP_NOT_IN_WHITE_LIST".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPNotInWhiteList", R.string.JMTErrIPNotInWhiteList));
                                } else if ("APP_VERSION_DEPRECATED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAppVersionDeprecated", R.string.JMTErrAppVersionDeprecated));
                                } else if ("INTERNAL_SERVER_ERROR".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrServerBusy", R.string.JMTErrServerBusy));
                                } else if ("PHONE_NUMBER_UNOCCUPIED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhoneNumberUnoccupied", R.string.JMTErrPhoneNumberUnoccupied));
                                } else if ("CLIENT_DANGEROUS".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrClientDangerous", R.string.JMTErrClientDangerous));
                                } else if ("PHONE_PASSWORD_FLOOD".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhonePasswordFlood", R.string.JMTErrPhonePasswordFlood));
                                } else if (tLRPC$TL_error.text.startsWith("PASSWORD_ERROR_")) {
                                    String[] split = tLRPC$TL_error.text.replace("PASSWORD_ERROR_", "").split("_");
                                    if (split.length != 2) {
                                        MergeUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                        MergeUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("PasswordDoNotMatch", R.string.vertify_failed));
                                    } else {
                                        int parseInt = Integer.parseInt(split[0]);
                                        new XPopup.Builder(MergeUserNameView.this.getContext()).popupAnimation(PopupAnimation.TranslateAlphaFromTop).isCenterHorizontal(true).offsetY(200).asCustom(new JMTErrorMsgPopup(MergeUserNameView.this.getContext(), LocaleController.formatString("JMTLoginErrorMaxCount", R.string.JMTLoginErrorMaxCount, Integer.valueOf(parseInt), Long.valueOf(Long.parseLong(split[1]) - parseInt)))).show();
                                    }
                                } else if (tLRPC$TL_error.text.startsWith("PHONE_NUMBER_BANNED")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrBannedPhone", R.string.JMTErrBannedPhone));
                                } else {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                                }
                                if (BuildVars.isRegisterVerify) {
                                    LoginActivity.this.captchaJson = "";
                                    MergeUserNameView.this.wvCaptcha.reload();
                                    MergeUserNameView.this.wvCaptcha.setVisibility(0);
                                }
                            }
                        });
                    }
                }, 10));
                return;
            }
            Toast.makeText(LoginActivity.this.getParentActivity(), LocaleController.getString("JMTVertifyPrompt", R.string.JMTVertifyPrompt), 0).show();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            if (BuildVars.isShowLoginVideo) {
                Jzvd.goOnPlayOnResume();
                JMTJzvdStdAssert jMTJzvdStdAssert = this.jzvdStd;
                if (jMTJzvdStdAssert.state != 5) {
                    jMTJzvdStdAssert.startVideo();
                }
            }
            if (!BuildVars.isOpenGuestLogin || BuildVars.isSignInByGuest) {
                return;
            }
            BuildVars.isSignInByGuest = true;
            signInByGuest();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onHide() {
            super.onHide();
            if (BuildVars.isShowLoginVideo) {
                JZUtils.clearSavedProgress(getContext(), null);
                Jzvd.goOnPlayOnPause();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkUsername(String str) {
            this.checkUserNameSuccess = false;
            this.isExistUserName = false;
            if (BuildVars.isLimitUserNameFirstLetter && !str.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：MergeUserNameView，输入用户名，开始请求：TL_account_checkUsername");
            }
            TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername = new TLRPC$TL_account_checkUsername();
            tLRPC$TL_account_checkUsername.username = str;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_checkUsername, new AnonymousClass13(tLRPC$TL_account_checkUsername), 10);
        }

        /* renamed from: org.telegram.ui.LoginActivity$MergeUserNameView$13, reason: invalid class name */
        class AnonymousClass13 implements RequestDelegate {
            final /* synthetic */ TLRPC$TL_account_checkUsername val$req;

            AnonymousClass13(TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername) {
                this.val$req = tLRPC$TL_account_checkUsername;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("现在是：MergeUserNameView，输入用户名，请求成功：TL_account_checkUsername");
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.13.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
                            MergeUserNameView.this.checkUserNameSuccess = true;
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameOk", R.string.JMTUserNameOk));
                            return;
                        }
                        if (tLRPC$TL_error2 != null && "USERNAME_INVALID".equals(tLRPC$TL_error2.text) && AnonymousClass13.this.val$req.username.length() <= 4) {
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error;
                        if (tLRPC$TL_error3 != null && "USERNAME_INVALID".equals(tLRPC$TL_error3.text)) {
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInvalid", R.string.UsernameInvalid));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error4 = tLRPC$TL_error;
                        if (tLRPC$TL_error4 == null || !"USERNAME_PURCHASE_AVAILABLE".equals(tLRPC$TL_error4.text)) {
                            MergeUserNameView.this.checkUserNameSuccess = true;
                            MergeUserNameView.this.isExistUserName = true;
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameHas", R.string.JMTUserNameHas));
                            if (BuildVars.isShowUserNameLoginMode) {
                                new AlertDialog.Builder(MergeUserNameView.this.mContext).setTitle(LocaleController.getString("PasswordHintPlaceholder", R.string.PasswordHintPlaceholder)).setMessage(LocaleController.getString("JMTUserHasGotoLogin", R.string.JMTUserHasGotoLogin)).setPositiveButton(LocaleController.getString("JMTComfirm", R.string.JMTComfirm), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity.MergeUserNameView.13.1.1
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MergeUserNameView.this.oetPassword.getEditText().requestFocus();
                                    }
                                }).create().show();
                                return;
                            }
                            return;
                        }
                        if (AnonymousClass13.this.val$req.username.length() <= 4) {
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                        } else {
                            MergeUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            MergeUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInUsePurchase", R.string.UsernameInUsePurchase));
                        }
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkPassword(String str) {
            String str2 = "";
            this.tvPasswordPoint.setText("");
            if (this.isExistUserName) {
                return;
            }
            if (BuildVars.isComplexPassword) {
                if (BuildVars.isCheckPasswordUpperCase) {
                    if (str.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$")) {
                        return;
                    }
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                    String string = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                    if (!str.matches(".*[a-z]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                    } else if (!str.matches(".*[A-Z]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoDaXie", R.string.JMTQueShaoDaXie);
                    } else if (!str.matches(".*[0-9]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                    }
                    this.tvPasswordPoint.setText(string + " - " + str2);
                    return;
                }
                if (str.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,16}$")) {
                    return;
                }
                this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                String string2 = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                if (!str.matches(".*[a-z]+.*")) {
                    str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                } else if (!str.matches(".*[0-9]+.*")) {
                    str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                }
                this.tvPasswordPoint.setText(string2 + " - " + str2);
                return;
            }
            if (str.length() < 6 || str.length() > 16) {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordSimple", R.string.JMTPasswordSimple));
            }
        }
    }

    public class RegisterUserNameView extends SlideView {
        private FrameLayout fl_root;
        private ImageView ivImage;
        private JMTJzvdStdAssert jzvdStd;
        private Context mContext;
        private OutlineEditText oetPassword;
        private OutlineEditText oetUsername;
        private ScrollView sv_user_login;
        private TextView tvPasswordPoint;
        private TextView tvUsernamePoint;
        private int tvVersionCount;
        private TextView tv_goto_login;
        private TextView tv_guest_point;
        private TextView tv_service;
        private TextView tv_version;
        private TextView tv_welcome_subtitle;
        private TextView tv_welcome_title;

        static /* synthetic */ int access$5508(RegisterUserNameView registerUserNameView) {
            int i = registerUserNameView.tvVersionCount;
            registerUserNameView.tvVersionCount = i + 1;
            return i;
        }

        public RegisterUserNameView(Context context) {
            super(context);
            this.tvVersionCount = 1;
            this.mContext = context;
            View inflate = View.inflate(context, R.layout.view_jmt_register, this);
            this.fl_root = (FrameLayout) inflate.findViewById(R.id.fl_root);
            this.jzvdStd = (JMTJzvdStdAssert) inflate.findViewById(R.id.jz_video);
            this.ivImage = (ImageView) inflate.findViewById(R.id.iv_image);
            if (BuildVars.isShowLoginVideo) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                Jzvd.setVideoImageDisplayType(2);
                Jzvd.NORMAL_ORIENTATION = 1;
                JZDataSource jZDataSource = new JZDataSource("jmt_login_video.mp4");
                jZDataSource.looping = true;
                this.jzvdStd.setUp(jZDataSource, 0, JMTJZMediaSystemAssert.class);
                this.jzvdStd.startVideo();
            } else {
                this.jzvdStd.setVisibility(8);
            }
            if (BuildVars.isShowLoginImage) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                try {
                    this.ivImage.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("jmt_login_image.png")));
                } catch (Exception unused) {
                    this.ivImage.setVisibility(8);
                }
            } else {
                this.ivImage.setVisibility(8);
            }
            this.sv_user_login = (ScrollView) inflate.findViewById(R.id.sv_user_login);
            TextView textView = (TextView) inflate.findViewById(R.id.tv_welcome_title);
            this.tv_welcome_title = textView;
            int i = Theme.key_windowBackgroundWhiteInputFieldActivated;
            textView.setTextColor(Theme.getColor(i));
            this.tv_welcome_title.setText(LocaleController.getString("JMTWelcomeTitle", R.string.JMTWelcomeTitle));
            TextView textView2 = (TextView) inflate.findViewById(R.id.tv_welcome_subtitle);
            this.tv_welcome_subtitle = textView2;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView2.setTextColor(-1);
            }
            this.tv_welcome_subtitle.setText(LocaleController.getString("JMTWelcomeRegister", R.string.JMTWelcomeRegister));
            this.oetUsername = (OutlineEditText) inflate.findViewById(R.id.oet_username);
            this.tvUsernamePoint = (TextView) inflate.findViewById(R.id.tv_username_point);
            OutlineEditText outlineEditText = (OutlineEditText) inflate.findViewById(R.id.oet_password);
            this.oetPassword = outlineEditText;
            outlineEditText.setShowEye(BuildVars.isShowPasswordEye);
            this.tvPasswordPoint = (TextView) inflate.findViewById(R.id.tv_password_point);
            TextView textView3 = (TextView) inflate.findViewById(R.id.tv_service);
            this.tv_service = textView3;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView3.setTextColor(Color.parseColor("#ffffff"));
            } else {
                textView3.setTextColor(Color.parseColor("#a8a8a8"));
            }
            this.tv_service.setText(LocaleController.getString("JMTService", R.string.JMTService));
            if (BuildVars.isShowOnlineService) {
                this.tv_service.setVisibility(0);
            }
            this.tv_service.setOnClickListener(new View.OnClickListener(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(BuildVars.loginServiceUrl)) {
                        Toast.makeText(RegisterUserNameView.this.mContext, LocaleController.getString("JMTGetServiceUrl", R.string.JMTGetServiceUrl), 0).show();
                    } else {
                        LoginActivity.this.presentFragment(new JMTWebViewActivity(BuildVars.loginServiceUrl));
                    }
                }
            });
            TextView textView4 = (TextView) inflate.findViewById(R.id.tv_goto_login);
            this.tv_goto_login = textView4;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView4.setTextColor(Color.parseColor("#ffffff"));
            } else {
                textView4.setTextColor(Color.parseColor("#a8a8a8"));
            }
            this.tv_goto_login.setText(LocaleController.getString("JMTGoToLogin", R.string.JMTGoToLogin));
            this.tv_goto_login.setOnClickListener(new View.OnClickListener(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    new AlertDialog.Builder(RegisterUserNameView.this.mContext).setTitle(LocaleController.getString("PasswordHintPlaceholder", R.string.PasswordHintPlaceholder)).setMessage(LocaleController.getString("JMTHasAccountPoint", R.string.JMTHasAccountPoint)).setNegativeButton(LocaleController.getString("JMTAlertCancel", R.string.JMTAlertCancel), null).setPositiveButton(LocaleController.getString("JMTAlertContinue", R.string.JMTAlertContinue), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.2.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public void onClick(DialogInterface dialogInterface, int i2) {
                            LoginActivity.this.setPage(16, true, null, false);
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.d("现在是：RegisterUserNameView，跳转到登录页面");
                            }
                        }
                    }).create().show();
                }
            });
            TextView textView5 = (TextView) inflate.findViewById(R.id.tv_guest_point);
            this.tv_guest_point = textView5;
            textView5.setText(LocaleController.getString("JMTGuestLogin", R.string.JMTGuestLogin));
            this.tv_version = (TextView) inflate.findViewById(R.id.tv_version);
            showVersion();
            this.tv_version.setOnClickListener(new AnonymousClass3(LoginActivity.this));
            this.oetUsername.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.4
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetUsername.getEditText().setImeOptions(5);
            this.oetUsername.setIsLogin(BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetUsername.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetUsername.getEditText().setTextColor(-14540254);
            }
            this.oetUsername.getEditText().setHintTextColor(-1);
            this.oetUsername.setHint(LocaleController.getString("JMTUsername", R.string.JMTUsername));
            this.oetUsername.getEditText().addTextChangedListener(new TextWatcher(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.5
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    String obj = RegisterUserNameView.this.oetUsername.getEditText().getText().toString();
                    if (obj.isEmpty()) {
                        return;
                    }
                    if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                        RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
                    }
                    RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTCheckUser", R.string.JMTCheckUser));
                    RegisterUserNameView.this.checkUsername(obj);
                }
            });
            this.oetPassword.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.6
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.oetPassword.getEditText().setImeOptions(6);
            this.oetPassword.setIsLogin(BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetPassword.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetPassword.getEditText().setTextColor(-14540254);
            }
            this.oetPassword.getEditText().setHintTextColor(-1);
            if (BuildVars.isComplexPassword) {
                this.oetPassword.setHint(LocaleController.getString("JMTPassword", R.string.JMTPassword));
            } else {
                this.oetPassword.setHint(LocaleController.getString("JMTPasswordSimpleHint", R.string.JMTPasswordSimpleHint));
            }
            this.oetPassword.getEditText().addTextChangedListener(new TextWatcher(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.7
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    RegisterUserNameView registerUserNameView = RegisterUserNameView.this;
                    registerUserNameView.checkPassword(registerUserNameView.oetPassword.getEditText().getText().toString());
                }
            });
            this.oetPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.LoginActivity$RegisterUserNameView$$ExternalSyntheticLambda0
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView6, int i2, KeyEvent keyEvent) {
                    boolean lambda$new$0;
                    lambda$new$0 = LoginActivity.RegisterUserNameView.this.lambda$new$0(textView6, i2, keyEvent);
                    return lambda$new$0;
                }
            });
            this.sv_user_login.setVisibility(0);
            for (int i2 = 0; i2 < 1; i2++) {
                ConnectionsManager.getInstance(i2).cleanup(false);
            }
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(new TLRPC$JMT_json_config(), new RequestDelegate(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.8
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            RegisterUserNameView.this.showVersion();
                            if (tLRPC$TL_error == null) {
                                TLObject tLObject2 = tLObject;
                                if (tLObject2 instanceof TLRPC$Vector) {
                                    Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                    while (it.hasNext()) {
                                        Object next = it.next();
                                        if (next instanceof TLRPC$TL_jsonObjectValue) {
                                            TLRPC$TL_jsonObjectValue tLRPC$TL_jsonObjectValue = (TLRPC$TL_jsonObjectValue) next;
                                            if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterInviteCode")) {
                                                TLRPC$JSONValue tLRPC$JSONValue = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isInviteCode = ((TLRPC$TL_jsonBool) tLRPC$JSONValue).value;
                                                    ((LoginActivityRegisterView) LoginActivity.this.views[5]).lastNameOutlineView.setVisibility(BuildVars.isInviteCode ? 0 : 8);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterPhone")) {
                                                TLRPC$JSONValue tLRPC$JSONValue2 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue2 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.requireRegisterPhone = ((TLRPC$TL_jsonBool) tLRPC$JSONValue2).value;
                                                    ((LoginActivityRegisterView) LoginActivity.this.views[5]).phoneOutlineView.setVisibility(BuildVars.requireRegisterPhone ? 0 : 8);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableRegisterVerifyCode")) {
                                                TLRPC$JSONValue tLRPC$JSONValue3 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue3 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isRegisterVerify = ((TLRPC$TL_jsonBool) tLRPC$JSONValue3).value;
                                                    ((LoginUserNameView) LoginActivity.this.views[16]).wvCaptcha.setVisibility(BuildVars.isRegisterVerify ? 0 : 4);
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("vpnPrefix")) {
                                                TLRPC$JSONValue tLRPC$JSONValue4 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue4 instanceof TLRPC$TL_jsonString) {
                                                    BuildVars.openVPNUrl = ((TLRPC$TL_jsonString) tLRPC$JSONValue4).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("baiduMapAk")) {
                                                TLRPC$JSONValue tLRPC$JSONValue5 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue5 instanceof TLRPC$TL_jsonString) {
                                                    BuildVars.baiduMapAk = ((TLRPC$TL_jsonString) tLRPC$JSONValue5).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("supportLink")) {
                                                TLRPC$JSONValue tLRPC$JSONValue6 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue6 instanceof TLRPC$TL_jsonString) {
                                                    BuildVars.loginServiceUrl = ((TLRPC$TL_jsonString) tLRPC$JSONValue6).value;
                                                    LoginUserNameView loginUserNameView = (LoginUserNameView) LoginActivity.this.views[16];
                                                    if (TextUtils.isEmpty(BuildVars.loginServiceUrl)) {
                                                        RegisterUserNameView.this.tv_service.setVisibility(4);
                                                        loginUserNameView.tv_service.setVisibility(4);
                                                    } else {
                                                        TextView textView6 = RegisterUserNameView.this.tv_service;
                                                        int i3 = R.string.JMTService;
                                                        textView6.setText(LocaleController.getString("JMTService", i3));
                                                        loginUserNameView.tv_service.setText(LocaleController.getString("JMTService", i3));
                                                    }
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("requireRegisterAvatar")) {
                                                TLRPC$JSONValue tLRPC$JSONValue7 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue7 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isForceSetAvatar = ((TLRPC$TL_jsonBool) tLRPC$JSONValue7).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableViewChannelMember")) {
                                                TLRPC$JSONValue tLRPC$JSONValue8 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue8 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isShowGroupMember = ((TLRPC$TL_jsonBool) tLRPC$JSONValue8).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("memberCannotViewFriendProfile")) {
                                                if (tLRPC$TL_jsonObjectValue.value instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.isCanShowFriendInfo = !((TLRPC$TL_jsonBool) r1).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableAppLogout")) {
                                                TLRPC$JSONValue tLRPC$JSONValue9 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue9 instanceof TLRPC$TL_jsonBool) {
                                                    BuildVars.enableAppLogout = ((TLRPC$TL_jsonBool) tLRPC$JSONValue9).value;
                                                }
                                            } else if (tLRPC$TL_jsonObjectValue.key.equals("enableQuitGroupMinMember")) {
                                                TLRPC$JSONValue tLRPC$JSONValue10 = tLRPC$TL_jsonObjectValue.value;
                                                if (tLRPC$JSONValue10 instanceof TLRPC$TL_jsonNumber) {
                                                    BuildVars.enableGroupExitCount = ((TLRPC$TL_jsonNumber) tLRPC$JSONValue10).value;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }, 10);
        }

        /* renamed from: org.telegram.ui.LoginActivity$RegisterUserNameView$3, reason: invalid class name */
        class AnonymousClass3 implements View.OnClickListener {
            AnonymousClass3(LoginActivity loginActivity) {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (RegisterUserNameView.this.tvVersionCount >= BuildVars.submitLogsMaxCount) {
                    RegisterUserNameView.this.tvVersionCount = 1;
                    MyCOSService.getInstance().submitLogs(new AnonymousClass1((LoadingPopupView) new XPopup.Builder(RegisterUserNameView.this.mContext).dismissOnBackPressed(Boolean.FALSE).isLightNavigationBar(true).asLoading(LocaleController.getString("JMTUploading", R.string.JMTUploading), LoadingPopupView.Style.ProgressBar).show()));
                    return;
                }
                RegisterUserNameView.access$5508(RegisterUserNameView.this);
            }

            /* renamed from: org.telegram.ui.LoginActivity$RegisterUserNameView$3$1, reason: invalid class name */
            class AnonymousClass1 implements Callback {
                final /* synthetic */ LoadingPopupView val$loadingPopupView;

                AnonymousClass1(LoadingPopupView loadingPopupView) {
                    this.val$loadingPopupView = loadingPopupView;
                }

                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.3.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass1.this.val$loadingPopupView.dismiss();
                            Toast.makeText(RegisterUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError), 0).show();
                        }
                    });
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, final Response response) throws IOException {
                    final LoadingPopupView loadingPopupView = this.val$loadingPopupView;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$RegisterUserNameView$3$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.RegisterUserNameView.AnonymousClass3.AnonymousClass1.this.lambda$onResponse$0(loadingPopupView, response);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onResponse$0(LoadingPopupView loadingPopupView, Response response) {
                    loadingPopupView.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogSuccess", R.string.JMTSubmitLogSuccess), 0).show();
                        return;
                    }
                    Toast.makeText(RegisterUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError) + ":" + response.code(), 0).show();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        public void showVersion() {
            String str;
            try {
                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                int i = packageInfo.versionCode / 10;
                if (BuildVars.isOpenCloudDefense) {
                    str = "-s";
                } else {
                    str = ConnectionsManager.connectedCurrentIp;
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split("\\.");
                        if (split.length != 0) {
                            str = "-" + split[split.length - 1];
                        }
                    }
                }
                this.tv_version.setText("v" + packageInfo.versionName + "(" + i + str + ")");
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getDeviceId() {
            String clientId = DeviceIdentifier.getClientId(true);
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getOAID(this.mContext);
            }
            if (clientId.equals("00000000-0000-0000-0000-000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (clientId.equals("00000000000000000000000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getPseudoID();
            }
            return TextUtils.isEmpty(clientId) ? DeviceIdentifier.getGUID(this.mContext) : clientId;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            String trim = this.oetUsername.getEditText().getText().toString().trim();
            String trim2 = this.oetPassword.getEditText().getText().toString().trim();
            if (trim.isEmpty() || trim2.isEmpty()) {
                if (trim.isEmpty()) {
                    this.tvUsernamePoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvUsernamePoint.setText(LocaleController.getString("JMTUsernameEmpty", R.string.JMTUsernameEmpty));
                    return;
                } else {
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordEmpty", R.string.JMTPasswordEmpty));
                    return;
                }
            }
            if (BuildVars.isLimitUserNameFirstLetter && !trim.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：RegisterUserNameView，点击下一步，开始请求：TL_account_checkUsername");
            }
            TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername = new TLRPC$TL_account_checkUsername();
            tLRPC$TL_account_checkUsername.username = trim;
            LoginActivity.this.needShowProgress(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_checkUsername, new AnonymousClass9(trim, trim2, tLRPC$TL_account_checkUsername), 10));
        }

        /* renamed from: org.telegram.ui.LoginActivity$RegisterUserNameView$9, reason: invalid class name */
        class AnonymousClass9 implements RequestDelegate {
            final /* synthetic */ String val$password;
            final /* synthetic */ TLRPC$TL_account_checkUsername val$req;
            final /* synthetic */ String val$username;

            AnonymousClass9(String str, String str2, TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername) {
                this.val$username = str;
                this.val$password = str2;
                this.val$req = tLRPC$TL_account_checkUsername;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("现在是：RegisterUserNameView，点击下一步，请求成功：TL_account_checkUsername");
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.9.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LoginActivity.this.needHideProgress(false);
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameOk", R.string.JMTUserNameOk));
                            if (BuildVars.isLimitUserName) {
                                if (AnonymousClass9.this.val$username.length() < BuildVars.limitUserNameMin || AnonymousClass9.this.val$username.length() > BuildVars.limitUserNameMax) {
                                    RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                    RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameControl", R.string.JMTUserNameControl) + BuildVars.limitUserNameMin + "-" + BuildVars.limitUserNameMax + LocaleController.getString("JMTUserNameControlEnd", R.string.JMTUserNameControlEnd));
                                    return;
                                }
                            } else if (AnonymousClass9.this.val$username.length() > 30) {
                                RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameMaxLength", R.string.JMTUserNameMaxLength));
                                return;
                            }
                            String str = "";
                            if (BuildVars.isComplexPassword) {
                                if (BuildVars.isCheckPasswordUpperCase) {
                                    if (!AnonymousClass9.this.val$password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$")) {
                                        RegisterUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                        String string = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                                        if (!AnonymousClass9.this.val$password.matches(".*[a-z]+.*")) {
                                            str = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                                        } else if (!AnonymousClass9.this.val$password.matches(".*[A-Z]+.*")) {
                                            str = LocaleController.getString("JMTQueShaoDaXie", R.string.JMTQueShaoDaXie);
                                        } else if (!AnonymousClass9.this.val$password.matches(".*[0-9]+.*")) {
                                            str = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                                        }
                                        RegisterUserNameView.this.tvPasswordPoint.setText(string + " - " + str);
                                        return;
                                    }
                                } else if (!AnonymousClass9.this.val$password.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,16}$")) {
                                    RegisterUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                    String string2 = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                                    if (!AnonymousClass9.this.val$password.matches(".*[a-z]+.*")) {
                                        str = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                                    } else if (!AnonymousClass9.this.val$password.matches(".*[0-9]+.*")) {
                                        str = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                                    }
                                    RegisterUserNameView.this.tvPasswordPoint.setText(string2 + " - " + str);
                                    return;
                                }
                            } else if (AnonymousClass9.this.val$password.length() < 6 || AnonymousClass9.this.val$password.length() > 16) {
                                RegisterUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                RegisterUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordSimple", R.string.JMTPasswordSimple));
                                return;
                            }
                            try {
                                if (TextUtils.isEmpty(LoginActivity.this.captchaJson)) {
                                    JSONObject jSONObject = new JSONObject();
                                    jSONObject.put("device_id", RegisterUserNameView.this.getDeviceId());
                                    str = jSONObject.toString();
                                } else {
                                    JSONObject jSONObject2 = new JSONObject(LoginActivity.this.captchaJson);
                                    jSONObject2.put("device_id", RegisterUserNameView.this.getDeviceId());
                                    str = jSONObject2.toString();
                                }
                            } catch (JSONException unused) {
                            }
                            if (BuildVars.LOGS_ENABLED) {
                                FileLog.d("现在是：RegisterUserNameView，跳转到注册资料页面");
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("username", AnonymousClass9.this.val$username);
                            bundle.putString("password", AnonymousClass9.this.val$password);
                            bundle.putString("ext", str);
                            LoginActivity.this.setPage(5, true, bundle, false);
                            return;
                        }
                        if (tLRPC$TL_error2 != null && "USERNAME_INVALID".equals(tLRPC$TL_error2.text) && AnonymousClass9.this.val$req.username.length() <= 4) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error;
                        if (tLRPC$TL_error3 != null && "USERNAME_INVALID".equals(tLRPC$TL_error3.text)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInvalid", R.string.UsernameInvalid));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error4 = tLRPC$TL_error;
                        if (tLRPC$TL_error4 == null || !"USERNAME_PURCHASE_AVAILABLE".equals(tLRPC$TL_error4.text)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            TextView textView = RegisterUserNameView.this.tvUsernamePoint;
                            int i = R.string.JMTRegisterUserNameHas;
                            textView.setText(LocaleController.getString("JMTRegisterUserNameHas", i));
                            AndroidUtilities.hideKeyboard(((BaseFragment) LoginActivity.this).fragmentView);
                            new AlertDialog.Builder(RegisterUserNameView.this.mContext).setTitle(LocaleController.getString("PasswordHintPlaceholder", R.string.PasswordHintPlaceholder)).setMessage(LocaleController.getString("JMTRegisterUserNameHas", i)).setPositiveButton(LocaleController.getString("JMTGotoLogin", R.string.JMTGotoLogin), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.9.1.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i2) {
                                    LoginActivity.this.setPage(16, true, null, false);
                                }
                            }).create().show();
                            return;
                        }
                        if (AnonymousClass9.this.val$req.username.length() <= 4) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                        } else {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInUsePurchase", R.string.UsernameInUsePurchase));
                        }
                    }
                });
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            if (BuildVars.isShowLoginVideo) {
                JMTJzvdStdAssert jMTJzvdStdAssert = this.jzvdStd;
                if (jMTJzvdStdAssert.state != 5) {
                    jMTJzvdStdAssert.startVideo();
                }
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onHide() {
            super.onHide();
            if (BuildVars.isShowLoginVideo) {
                this.jzvdStd.reset();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkUsername(String str) {
            if (BuildVars.isLimitUserNameFirstLetter && !str.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：RegisterUserNameView，输入用户名，开始请求：TL_account_checkUsername");
            }
            TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername = new TLRPC$TL_account_checkUsername();
            tLRPC$TL_account_checkUsername.username = str;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_checkUsername, new AnonymousClass10(tLRPC$TL_account_checkUsername), 10);
        }

        /* renamed from: org.telegram.ui.LoginActivity$RegisterUserNameView$10, reason: invalid class name */
        class AnonymousClass10 implements RequestDelegate {
            final /* synthetic */ TLRPC$TL_account_checkUsername val$req;

            AnonymousClass10(TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername) {
                this.val$req = tLRPC$TL_account_checkUsername;
            }

            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("现在是：RegisterUserNameView，输入用户名，请求成功：TL_account_checkUsername");
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.10.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameOk", R.string.JMTUserNameOk));
                            return;
                        }
                        if (tLRPC$TL_error2 != null && "USERNAME_INVALID".equals(tLRPC$TL_error2.text) && AnonymousClass10.this.val$req.username.length() <= 4) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error;
                        if (tLRPC$TL_error3 != null && "USERNAME_INVALID".equals(tLRPC$TL_error3.text)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInvalid", R.string.UsernameInvalid));
                            return;
                        }
                        TLRPC$TL_error tLRPC$TL_error4 = tLRPC$TL_error;
                        if (tLRPC$TL_error4 == null || !"USERNAME_PURCHASE_AVAILABLE".equals(tLRPC$TL_error4.text)) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                            TextView textView = RegisterUserNameView.this.tvUsernamePoint;
                            int i = R.string.JMTRegisterUserNameHas;
                            textView.setText(LocaleController.getString("JMTRegisterUserNameHas", i));
                            AndroidUtilities.hideKeyboard(((BaseFragment) LoginActivity.this).fragmentView);
                            new AlertDialog.Builder(RegisterUserNameView.this.mContext).setTitle(LocaleController.getString("PasswordHintPlaceholder", R.string.PasswordHintPlaceholder)).setMessage(LocaleController.getString("JMTRegisterUserNameHas", i)).setPositiveButton(LocaleController.getString("JMTGotoLogin", R.string.JMTGotoLogin), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity.RegisterUserNameView.10.1.1
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i2) {
                                    LoginActivity.this.setPage(16, true, null, false);
                                }
                            }).create().show();
                            return;
                        }
                        if (AnonymousClass10.this.val$req.username.length() <= 4) {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                        } else {
                            RegisterUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                            RegisterUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInUsePurchase", R.string.UsernameInUsePurchase));
                        }
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkPassword(String str) {
            String str2 = "";
            this.tvPasswordPoint.setText("");
            if (BuildVars.isComplexPassword) {
                if (BuildVars.isCheckPasswordUpperCase) {
                    if (str.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$")) {
                        return;
                    }
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                    String string = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                    if (!str.matches(".*[a-z]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                    } else if (!str.matches(".*[A-Z]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoDaXie", R.string.JMTQueShaoDaXie);
                    } else if (!str.matches(".*[0-9]+.*")) {
                        str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                    }
                    this.tvPasswordPoint.setText(string + " - " + str2);
                    return;
                }
                if (str.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,16}$")) {
                    return;
                }
                this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                String string2 = LocaleController.getString("JMTPasswordLength", R.string.JMTPasswordLength);
                if (!str.matches(".*[a-z]+.*")) {
                    str2 = LocaleController.getString("JMTQueShaoXiaoXie", R.string.JMTQueShaoXiaoXie);
                } else if (!str.matches(".*[0-9]+.*")) {
                    str2 = LocaleController.getString("JMTQueShaoShuZi", R.string.JMTQueShaoShuZi);
                }
                this.tvPasswordPoint.setText(string2 + " - " + str2);
                return;
            }
            if (str.length() < 6 || str.length() > 16) {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordSimple", R.string.JMTPasswordSimple));
            }
        }
    }

    public class LoginUserNameView extends SlideView {
        private boolean checkUserNameSuccess;
        private FrameLayout fl_root;
        private ImageView ivImage;
        private JMTJzvdStdAssert jzvdStd;
        private Context mContext;
        private OutlineEditText oetPassword;
        private OutlineEditText oetUsername;
        private TextView tvPasswordPoint;
        private TextView tvUsernamePoint;
        private int tvVersionCount;
        private TextView tv_service;
        private TextView tv_version;
        private TextView tv_welcome_subtitle;
        private TextView tv_welcome_title;
        private WebView wvCaptcha;

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        static /* synthetic */ int access$7108(LoginUserNameView loginUserNameView) {
            int i = loginUserNameView.tvVersionCount;
            loginUserNameView.tvVersionCount = i + 1;
            return i;
        }

        public LoginUserNameView(Context context) {
            super(context);
            boolean z = true;
            this.tvVersionCount = 1;
            this.checkUserNameSuccess = false;
            this.mContext = context;
            View inflate = View.inflate(context, R.layout.view_jmt_login, this);
            this.fl_root = (FrameLayout) inflate.findViewById(R.id.fl_root);
            this.jzvdStd = (JMTJzvdStdAssert) inflate.findViewById(R.id.jz_video);
            this.ivImage = (ImageView) inflate.findViewById(R.id.iv_image);
            if (BuildVars.isShowLoginVideo) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                Jzvd.setVideoImageDisplayType(2);
                Jzvd.NORMAL_ORIENTATION = 1;
                JZDataSource jZDataSource = new JZDataSource("jmt_login_video.mp4");
                jZDataSource.looping = true;
                this.jzvdStd.setUp(jZDataSource, 0, JMTJZMediaSystemAssert.class);
                this.jzvdStd.startVideo();
            } else {
                this.jzvdStd.setVisibility(8);
            }
            if (BuildVars.isShowLoginImage) {
                this.fl_root.setBackgroundColor(Color.parseColor("#000000"));
                try {
                    this.ivImage.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("jmt_login_image.png")));
                } catch (Exception unused) {
                    this.ivImage.setVisibility(8);
                }
            } else {
                this.ivImage.setVisibility(8);
            }
            TextView textView = (TextView) inflate.findViewById(R.id.tv_welcome_title);
            this.tv_welcome_title = textView;
            int i = Theme.key_windowBackgroundWhiteInputFieldActivated;
            textView.setTextColor(Theme.getColor(i));
            this.tv_welcome_title.setText(LocaleController.getString("JMTWelcomeTitle", R.string.JMTWelcomeTitle));
            TextView textView2 = (TextView) inflate.findViewById(R.id.tv_welcome_subtitle);
            this.tv_welcome_subtitle = textView2;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView2.setTextColor(-1);
            }
            this.tv_welcome_subtitle.setText(LocaleController.getString("JMTWelcomeLogin", R.string.JMTWelcomeLogin));
            this.oetUsername = (OutlineEditText) inflate.findViewById(R.id.oet_username);
            this.tvUsernamePoint = (TextView) inflate.findViewById(R.id.tv_username_point);
            OutlineEditText outlineEditText = (OutlineEditText) inflate.findViewById(R.id.oet_password);
            this.oetPassword = outlineEditText;
            outlineEditText.setShowEye(BuildVars.isShowPasswordEye);
            this.tvPasswordPoint = (TextView) inflate.findViewById(R.id.tv_password_point);
            this.wvCaptcha = (WebView) inflate.findViewById(R.id.wv_captcha);
            this.tv_version = (TextView) inflate.findViewById(R.id.tv_version);
            showVersion();
            this.tv_version.setOnClickListener(new AnonymousClass1(LoginActivity.this));
            TextView textView3 = (TextView) inflate.findViewById(R.id.tv_service);
            this.tv_service = textView3;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                textView3.setTextColor(Color.parseColor("#ffffff"));
            } else {
                textView3.setTextColor(Color.parseColor("#a8a8a8"));
            }
            this.tv_service.setText(LocaleController.getString("JMTService", R.string.JMTService));
            if (BuildVars.isShowOnlineService) {
                this.tv_service.setVisibility(0);
            }
            this.tv_service.setOnClickListener(new View.OnClickListener(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (TextUtils.isEmpty(BuildVars.loginServiceUrl)) {
                        Toast.makeText(LoginUserNameView.this.mContext, LocaleController.getString("JMTGetServiceUrl", R.string.JMTGetServiceUrl), 0).show();
                    } else {
                        LoginActivity.this.presentFragment(new JMTWebViewActivity(BuildVars.loginServiceUrl));
                    }
                }
            });
            WebSettings settings = this.wvCaptcha.getSettings();
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setCacheMode(2);
            this.wvCaptcha.setWebViewClient(new WebViewClient(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.3
                @Override // android.webkit.WebViewClient
                public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                    webView.loadUrl(str);
                    return true;
                }

                @Override // android.webkit.WebViewClient
                public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                    super.onReceivedError(webView, webResourceRequest, webResourceError);
                    LoginUserNameView.this.wvCaptcha.reload();
                }
            });
            settings.setJavaScriptEnabled(true);
            WebView webView = this.wvCaptcha;
            webView.addJavascriptInterface(LoginActivity.this.new JsBridge(webView), "jsBridge");
            this.wvCaptcha.loadUrl("file:///android_asset/tx_captcha.html");
            this.oetUsername.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.4
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetUsername.getEditText().setImeOptions(5);
            this.oetUsername.setIsLogin(BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetUsername.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetUsername.getEditText().setTextColor(-14540254);
            }
            this.oetUsername.getEditText().setHintTextColor(-1);
            this.oetUsername.setHint(LocaleController.getString("JMTUsername", R.string.JMTUsername));
            this.oetUsername.getEditText().addTextChangedListener(new TextWatcher(LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.5
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    String obj = LoginUserNameView.this.oetUsername.getEditText().getText().toString();
                    if (obj.isEmpty()) {
                        return;
                    }
                    if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                        LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
                    } else {
                        LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
                    }
                    LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTCheckUser", R.string.JMTCheckUser));
                    LoginUserNameView.this.checkUsername(obj);
                }
            });
            this.oetPassword.getEditText().setKeyListener(new DigitsKeyListener(this, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.6
                @Override // android.text.method.DigitsKeyListener, android.text.method.KeyListener
                public int getInputType() {
                    return 1;
                }

                @Override // android.text.method.DigitsKeyListener, android.text.method.NumberKeyListener
                protected char[] getAcceptedChars() {
                    return "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
                }
            });
            this.oetPassword.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.oetPassword.getEditText().setImeOptions(6);
            OutlineEditText outlineEditText2 = this.oetPassword;
            if (!BuildVars.isShowLoginVideo && !BuildVars.isShowLoginImage) {
                z = false;
            }
            outlineEditText2.setIsLogin(z);
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.oetPassword.getEditText().setTextColor(Theme.getColor(i));
            } else {
                this.oetPassword.getEditText().setTextColor(-14540254);
            }
            this.oetPassword.getEditText().setHintTextColor(-1);
            if (BuildVars.isComplexPassword) {
                this.oetPassword.setHint(LocaleController.getString("JMTPassword", R.string.JMTPassword));
            } else {
                this.oetPassword.setHint(LocaleController.getString("JMTPasswordSimpleHint", R.string.JMTPasswordSimpleHint));
            }
            this.oetPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.LoginActivity$LoginUserNameView$$ExternalSyntheticLambda0
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView4, int i2, KeyEvent keyEvent) {
                    boolean lambda$new$0;
                    lambda$new$0 = LoginActivity.LoginUserNameView.this.lambda$new$0(textView4, i2, keyEvent);
                    return lambda$new$0;
                }
            });
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginUserNameView$1, reason: invalid class name */
        class AnonymousClass1 implements View.OnClickListener {
            AnonymousClass1(LoginActivity loginActivity) {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (LoginUserNameView.this.tvVersionCount >= BuildVars.submitLogsMaxCount) {
                    LoginUserNameView.this.tvVersionCount = 1;
                    MyCOSService.getInstance().submitLogs(new C00991((LoadingPopupView) new XPopup.Builder(LoginUserNameView.this.mContext).dismissOnBackPressed(Boolean.FALSE).isLightNavigationBar(true).asLoading(LocaleController.getString("JMTUploading", R.string.JMTUploading), LoadingPopupView.Style.ProgressBar).show()));
                    return;
                }
                LoginUserNameView.access$7108(LoginUserNameView.this);
            }

            /* renamed from: org.telegram.ui.LoginActivity$LoginUserNameView$1$1, reason: invalid class name and collision with other inner class name */
            class C00991 implements Callback {
                final /* synthetic */ LoadingPopupView val$loadingPopupView;

                C00991(LoadingPopupView loadingPopupView) {
                    this.val$loadingPopupView = loadingPopupView;
                }

                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.1.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            C00991.this.val$loadingPopupView.dismiss();
                            Toast.makeText(LoginUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError), 0).show();
                        }
                    });
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, final Response response) throws IOException {
                    final LoadingPopupView loadingPopupView = this.val$loadingPopupView;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginUserNameView$1$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.LoginUserNameView.AnonymousClass1.C00991.this.lambda$onResponse$0(loadingPopupView, response);
                        }
                    });
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onResponse$0(LoadingPopupView loadingPopupView, Response response) {
                    loadingPopupView.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogSuccess", R.string.JMTSubmitLogSuccess), 0).show();
                        return;
                    }
                    Toast.makeText(LoginUserNameView.this.mContext, LocaleController.getString("JMTSubmitLogError", R.string.JMTSubmitLogError) + ":" + response.code(), 0).show();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        public void showVersion() {
            String str;
            try {
                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                int i = packageInfo.versionCode / 10;
                if (BuildVars.isOpenCloudDefense) {
                    str = "-s";
                } else {
                    str = ConnectionsManager.connectedCurrentIp;
                    if (!TextUtils.isEmpty(str)) {
                        String[] split = str.split("\\.");
                        if (split.length != 0) {
                            str = "-" + split[split.length - 1];
                        }
                    }
                }
                this.tv_version.setText("v" + packageInfo.versionName + "(" + i + str + ")");
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        private String getDeviceId() {
            String clientId = DeviceIdentifier.getClientId(true);
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getOAID(this.mContext);
            }
            if (clientId.equals("00000000-0000-0000-0000-000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (clientId.equals("00000000000000000000000000000000")) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getAndroidID(this.mContext);
            }
            if (TextUtils.isEmpty(clientId)) {
                clientId = DeviceIdentifier.getPseudoID();
            }
            return TextUtils.isEmpty(clientId) ? DeviceIdentifier.getGUID(this.mContext) : clientId;
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            AndroidUtilities.hideKeyboard(((BaseFragment) LoginActivity.this).fragmentView);
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：LoginUserNameView，返回到：RegisterUserNameView");
            }
            return super.onBackPressed(z);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            String str2;
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#ffffff"));
            } else {
                this.tvPasswordPoint.setTextColor(Color.parseColor("#a8a8a8"));
            }
            String trim = this.oetUsername.getEditText().getText().toString().trim();
            String trim2 = this.oetPassword.getEditText().getText().toString().trim();
            if (trim.isEmpty() || trim2.isEmpty()) {
                if (trim.isEmpty()) {
                    this.tvUsernamePoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvUsernamePoint.setText(LocaleController.getString("JMTUsernameEmpty", R.string.JMTUsernameEmpty));
                    return;
                } else {
                    this.tvPasswordPoint.setTextColor(Color.parseColor("#ff0000"));
                    this.tvPasswordPoint.setText(LocaleController.getString("JMTPasswordEmpty", R.string.JMTPasswordEmpty));
                    return;
                }
            }
            if (BuildVars.isLimitUserNameFirstLetter && !trim.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (!this.checkUserNameSuccess) {
                checkUsername(trim);
                return;
            }
            if (!BuildVars.isRegisterVerify || !TextUtils.isEmpty(LoginActivity.this.captchaJson)) {
                try {
                    if (TextUtils.isEmpty(LoginActivity.this.captchaJson)) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("device_id", getDeviceId());
                        str2 = jSONObject.toString();
                    } else {
                        JSONObject jSONObject2 = new JSONObject(LoginActivity.this.captchaJson);
                        jSONObject2.put("device_id", getDeviceId());
                        str2 = jSONObject2.toString();
                    }
                } catch (JSONException unused) {
                    str2 = "";
                }
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("现在是：LoginUserNameView，点击下一步，开始请求：JMT_signInByUsernameV2");
                }
                TLRPC$JMT_signInByUsernameV2 tLRPC$JMT_signInByUsernameV2 = new TLRPC$JMT_signInByUsernameV2();
                tLRPC$JMT_signInByUsernameV2.username = trim;
                tLRPC$JMT_signInByUsernameV2.password = trim2;
                tLRPC$JMT_signInByUsernameV2.ext = str2;
                LoginActivity.this.needShowProgress(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$JMT_signInByUsernameV2, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.7
                    @Override // org.telegram.tgnet.RequestDelegate
                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("现在是：LoginUserNameView，点击下一步，请求成功：JMT_signInByUsernameV2");
                        }
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.7.1
                            @Override // java.lang.Runnable
                            public void run() {
                                LoginActivity.this.needHideProgress(false);
                                TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                                if (tLRPC$TL_error2 == null) {
                                    TLObject tLObject2 = tLObject;
                                    if (!(tLObject2 instanceof TLRPC$TL_auth_authorizationSignUpRequired)) {
                                        LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject2);
                                        return;
                                    }
                                    TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService = ((TLRPC$TL_auth_authorizationSignUpRequired) tLObject2).terms_of_service;
                                    if (tLRPC$TL_help_termsOfService != null) {
                                        LoginActivity.this.currentTermsOfService = tLRPC$TL_help_termsOfService;
                                    }
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTAccountNotExist", R.string.JMTAccountNotExist));
                                    return;
                                }
                                if ("PASSWORD_HASH_INVALID".equals(tLRPC$TL_error2.text)) {
                                    LoginUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                    LoginUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch));
                                } else if ("LOGIN_DANGEROUS".equals(tLRPC$TL_error.text)) {
                                    new XPopup.Builder(LoginUserNameView.this.mContext).autoOpenSoftInput(Boolean.TRUE).asCustom(new JMTDangerPopup(LoginUserNameView.this.mContext, new JMTDangerPopup.OnSuccessListener(this) { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.7.1.1
                                        @Override // org.telegram.ui.JMTDangerPopup.OnSuccessListener
                                        public void onSuccess() {
                                        }
                                    })).show();
                                } else if ("IP_BLOCKED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPBlocked", R.string.JMTErrIPBlocked));
                                } else if ("ACCOUNT_BLOCKED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAccountBlocked", R.string.JMTErrAccountBlocked));
                                } else if ("IP_NOT_IN_WHITE_LIST".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPNotInWhiteList", R.string.JMTErrIPNotInWhiteList));
                                } else if ("APP_VERSION_DEPRECATED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAppVersionDeprecated", R.string.JMTErrAppVersionDeprecated));
                                } else if ("INTERNAL_SERVER_ERROR".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrServerBusy", R.string.JMTErrServerBusy));
                                } else if ("PHONE_NUMBER_UNOCCUPIED".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhoneNumberUnoccupied", R.string.JMTErrPhoneNumberUnoccupied));
                                } else if ("CLIENT_DANGEROUS".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrClientDangerous", R.string.JMTErrClientDangerous));
                                } else if ("PHONE_PASSWORD_FLOOD".equals(tLRPC$TL_error.text)) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhonePasswordFlood", R.string.JMTErrPhonePasswordFlood));
                                } else if (tLRPC$TL_error.text.startsWith("PASSWORD_ERROR_")) {
                                    String[] split = tLRPC$TL_error.text.replace("PASSWORD_ERROR_", "").split("_");
                                    if (split.length != 2) {
                                        LoginUserNameView.this.tvPasswordPoint.setTextColor(Color.parseColor("#FF0000"));
                                        LoginUserNameView.this.tvPasswordPoint.setText(LocaleController.getString("PasswordDoNotMatch", R.string.vertify_failed));
                                    } else {
                                        int parseInt = Integer.parseInt(split[0]);
                                        new XPopup.Builder(LoginUserNameView.this.getContext()).popupAnimation(PopupAnimation.TranslateAlphaFromTop).isCenterHorizontal(true).offsetY(200).asCustom(new JMTErrorMsgPopup(LoginUserNameView.this.getContext(), LocaleController.formatString("JMTLoginErrorMaxCount", R.string.JMTLoginErrorMaxCount, Integer.valueOf(parseInt), Long.valueOf(Long.parseLong(split[1]) - parseInt)))).show();
                                    }
                                } else if (tLRPC$TL_error.text.startsWith("PHONE_NUMBER_BANNED")) {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrBannedPhone", R.string.JMTErrBannedPhone));
                                } else {
                                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                                }
                                if (BuildVars.isRegisterVerify) {
                                    LoginActivity.this.captchaJson = "";
                                    LoginUserNameView.this.wvCaptcha.reload();
                                    LoginUserNameView.this.wvCaptcha.setVisibility(0);
                                }
                            }
                        });
                    }
                }, 10));
                return;
            }
            Toast.makeText(LoginActivity.this.getParentActivity(), LocaleController.getString("JMTVertifyPrompt", R.string.JMTVertifyPrompt), 0).show();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            if (BuildVars.isShowLoginVideo) {
                JMTJzvdStdAssert jMTJzvdStdAssert = this.jzvdStd;
                if (jMTJzvdStdAssert.state != 5) {
                    jMTJzvdStdAssert.startVideo();
                }
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onHide() {
            super.onHide();
            if (BuildVars.isShowLoginVideo) {
                this.jzvdStd.reset();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void checkUsername(String str) {
            this.checkUserNameSuccess = false;
            if (BuildVars.isLimitUserNameFirstLetter && !str.substring(0, 1).matches("^[a-zA-Z]")) {
                this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameString", R.string.JMTUserNameString));
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：LoginUserNameView，输入用户名，开始请求：TL_account_checkUsername");
            }
            final TLRPC$TL_account_checkUsername tLRPC$TL_account_checkUsername = new TLRPC$TL_account_checkUsername();
            tLRPC$TL_account_checkUsername.username = str;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_checkUsername, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.8
                @Override // org.telegram.tgnet.RequestDelegate
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("现在是：LoginUserNameView，输入用户名，请求成功：TL_account_checkUsername");
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity.LoginUserNameView.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                            if (tLRPC$TL_error2 == null && (tLObject instanceof TLRPC$TL_boolTrue)) {
                                LoginUserNameView.this.checkUserNameSuccess = true;
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTLoginUserNameHint", R.string.JMTLoginUserNameHint));
                                return;
                            }
                            if (tLRPC$TL_error2 != null && "USERNAME_INVALID".equals(tLRPC$TL_error2.text) && tLRPC$TL_account_checkUsername.username.length() <= 4) {
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                                return;
                            }
                            TLRPC$TL_error tLRPC$TL_error3 = tLRPC$TL_error;
                            if (tLRPC$TL_error3 != null && "USERNAME_INVALID".equals(tLRPC$TL_error3.text)) {
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInvalid", R.string.UsernameInvalid));
                                return;
                            }
                            TLRPC$TL_error tLRPC$TL_error4 = tLRPC$TL_error;
                            if (tLRPC$TL_error4 == null || !"USERNAME_PURCHASE_AVAILABLE".equals(tLRPC$TL_error4.text)) {
                                LoginUserNameView.this.checkUserNameSuccess = true;
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameHas", R.string.JMTUserNameHas));
                            } else if (tLRPC$TL_account_checkUsername.username.length() <= 4) {
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("JMTUserNameShort", R.string.JMTUserNameShort));
                            } else {
                                LoginUserNameView.this.tvUsernamePoint.setTextColor(Color.parseColor("#FF0000"));
                                LoginUserNameView.this.tvUsernamePoint.setText(LocaleController.getString("UsernameInUsePurchase", R.string.UsernameInUsePurchase));
                            }
                        }
                    });
                }
            }, 10);
        }
    }

    public class LoginActivitySmsView extends SlideView implements NotificationCenter.NotificationCenterDelegate {
        private RLottieImageView blueImageView;
        private FrameLayout bottomContainer;
        private String catchedPhone;
        private CodeFieldContainer codeFieldContainer;
        private int codeTime;
        private Timer codeTimer;
        private TextView confirmTextView;
        private Bundle currentParams;
        private int currentType;
        private RLottieDrawable dotsDrawable;
        private RLottieDrawable dotsToStarsDrawable;
        private String emailPhone;
        private Runnable errorColorTimeout;
        private ViewSwitcher errorViewSwitcher;
        RLottieDrawable hintDrawable;
        private boolean isDotsAnimationVisible;
        private boolean isResendingCode;
        private double lastCodeTime;
        private double lastCurrentTime;
        private String lastError;
        private int length;
        private ImageView missedCallArrowIcon;
        private TextView missedCallDescriptionSubtitle;
        private ImageView missedCallPhoneIcon;
        private boolean nextPressed;
        private int nextType;
        private LinearLayout openFragmentButton;
        private TextView openFragmentButtonText;
        private RLottieImageView openFragmentImageView;
        private int openTime;
        private String pattern;
        private String phone;
        private String phoneHash;
        private boolean postedErrorColorTimeout;
        private String prefix;
        private TextView prefixTextView;
        private FrameLayout problemFrame;
        private TextView problemText;
        private ProgressView progressView;
        private String requestPhone;
        private RLottieDrawable starsToDotsDrawable;
        private int time;
        private TextView timeText;
        private Timer timeTimer;
        private final Object timerSync;
        private TextView titleTextView;
        private String url;
        private boolean waitingForEvent;
        private TextView wrongCode;

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onBackPressed$42(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        static /* synthetic */ int access$10026(LoginActivitySmsView loginActivitySmsView, double d) {
            int i = (int) (loginActivitySmsView.time - d);
            loginActivitySmsView.time = i;
            return i;
        }

        static /* synthetic */ int access$9326(LoginActivitySmsView loginActivitySmsView, double d) {
            int i = (int) (loginActivitySmsView.codeTime - d);
            loginActivitySmsView.codeTime = i;
            return i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            int i = 0;
            this.postedErrorColorTimeout = false;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    break;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
            if (this.errorViewSwitcher.getCurrentView() != (this.currentType == 15 ? this.openFragmentButton : this.problemFrame)) {
                this.errorViewSwitcher.showNext();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:16:0x03d6  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x03f6  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x045d  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x05de  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x062a  */
        /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x060c  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x04c7  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LoginActivitySmsView(final android.content.Context r41, int r42) {
            /*
                Method dump skipped, instructions count: 1589
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivitySmsView.<init>(org.telegram.ui.LoginActivity, android.content.Context, int):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(View view) {
            if (this.time <= 0 || this.timeTimer == null) {
                this.isResendingCode = true;
                this.timeText.invalidate();
                this.timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteValueText));
                int i = this.nextType;
                if (i != 4 && i != 2 && i != 11 && i != 15) {
                    if (i == 3) {
                        AndroidUtilities.setWaitingForSms(false);
                        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
                        this.waitingForEvent = false;
                        destroyCodeTimer();
                        resendCode();
                        return;
                    }
                    return;
                }
                this.timeText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
                int i2 = this.nextType;
                if (i2 == 4 || i2 == 11) {
                    this.timeText.setText(LocaleController.getString("Calling", R.string.Calling));
                } else {
                    this.timeText.setText(LocaleController.getString("SendingSms", R.string.SendingSms));
                }
                final Bundle bundle = new Bundle();
                bundle.putString("phone", this.phone);
                bundle.putString("ephone", this.emailPhone);
                bundle.putString("phoneFormated", this.requestPhone);
                createCodeTimer();
                TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode = new TLRPC$TL_auth_resendCode();
                tLRPC$TL_auth_resendCode.phone_number = this.requestPhone;
                tLRPC$TL_auth_resendCode.phone_code_hash = this.phoneHash;
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_resendCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda39
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LoginActivity.LoginActivitySmsView.this.lambda$new$3(bundle, tLObject, tLRPC$TL_error);
                    }
                }, 10);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(Bundle bundle, TLObject tLObject) {
            LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, (TLRPC$TL_auth_sentCode) tLObject);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(final Bundle bundle, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            if (tLObject != null) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda26
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.this.lambda$new$1(bundle, tLObject);
                    }
                });
            } else {
                if (tLRPC$TL_error == null || tLRPC$TL_error.text == null) {
                    return;
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.this.lambda$new$2(tLRPC$TL_error);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(TLRPC$TL_error tLRPC$TL_error) {
            this.lastError = tLRPC$TL_error.text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5(View view) {
            try {
                getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.url)));
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$8(Context context, View view) {
            if (this.nextPressed || this.timeText.getVisibility() != 8 || this.isResendingCode) {
                return;
            }
            if (!(this.nextType == 0)) {
                if (LoginActivity.this.radialProgressView.getTag() != null) {
                    return;
                }
                resendCode();
                return;
            }
            new AlertDialog.Builder(context).setTitle(LocaleController.getString(R.string.RestorePasswordNoEmailTitle)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("DidNotGetTheCodeInfo", R.string.DidNotGetTheCodeInfo, this.phone))).setNeutralButton(LocaleController.getString(R.string.DidNotGetTheCodeHelpButton), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivitySmsView.this.lambda$new$6(dialogInterface, i);
                }
            }).setPositiveButton(LocaleController.getString(R.string.Close), null).setNegativeButton(LocaleController.getString(R.string.DidNotGetTheCodeEditNumberButton), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivitySmsView.this.lambda$new$7(dialogInterface, i);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$6(DialogInterface dialogInterface, int i) {
            try {
                PackageInfo packageInfo = ApplicationLoader.applicationContext.getPackageManager().getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
                String format = String.format(Locale.US, "%s (%d)", packageInfo.versionName, Integer.valueOf(packageInfo.versionCode));
                Intent intent = new Intent("android.intent.action.SENDTO");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra("android.intent.extra.EMAIL", new String[]{"sms@telegram.org"});
                intent.putExtra("android.intent.extra.SUBJECT", "Android registration/login issue " + format + " " + this.emailPhone);
                intent.putExtra("android.intent.extra.TEXT", "Phone: " + this.requestPhone + "\nApp version: " + format + "\nOS version: SDK " + Build.VERSION.SDK_INT + "\nDevice Name: " + Build.MANUFACTURER + Build.MODEL + "\nLocale: " + Locale.getDefault() + "\nError: " + this.lastError);
                getContext().startActivity(Intent.createChooser(intent, "Send email..."));
            } catch (Exception unused) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.AppName), LocaleController.getString("NoMailInstalled", R.string.NoMailInstalled));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$7(DialogInterface dialogInterface, int i) {
            LoginActivity.this.setPage(0, true, null, true);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            this.confirmTextView.setTextColor(Theme.getColor(LoginActivity.this.isInCancelAccountDeletionMode() ? Theme.key_windowBackgroundWhiteBlackText : Theme.key_windowBackgroundWhiteGrayText6));
            this.confirmTextView.setLinkTextColor(Theme.getColor(Theme.key_chats_actionBackground));
            TextView textView = this.titleTextView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            textView.setTextColor(Theme.getColor(i));
            if (this.currentType == 11) {
                this.missedCallDescriptionSubtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
                this.missedCallArrowIcon.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated), PorterDuff.Mode.SRC_IN));
                this.missedCallPhoneIcon.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i), PorterDuff.Mode.SRC_IN));
                this.prefixTextView.setTextColor(Theme.getColor(i));
            }
            applyLottieColors(this.hintDrawable);
            applyLottieColors(this.starsToDotsDrawable);
            applyLottieColors(this.dotsDrawable);
            applyLottieColors(this.dotsToStarsDrawable);
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            if (codeFieldContainer != null) {
                codeFieldContainer.invalidate();
            }
            Integer num = (Integer) this.timeText.getTag();
            if (num == null) {
                num = Integer.valueOf(Theme.key_windowBackgroundWhiteGrayText6);
            }
            this.timeText.setTextColor(Theme.getColor(num.intValue()));
            if (this.currentType != 15) {
                this.problemText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            }
            this.wrongCode.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }

        private void applyLottieColors(RLottieDrawable rLottieDrawable) {
            if (rLottieDrawable != null) {
                rLottieDrawable.setLayerColor("Bubble.**", Theme.getColor(Theme.key_chats_actionBackground));
                int i = Theme.key_windowBackgroundWhiteBlackText;
                rLottieDrawable.setLayerColor("Phone.**", Theme.getColor(i));
                rLottieDrawable.setLayerColor("Note.**", Theme.getColor(i));
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean hasCustomKeyboard() {
            return this.currentType != 3;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onCancelPressed() {
            this.nextPressed = false;
        }

        private void resendCode() {
            if (this.nextPressed || this.isResendingCode || LoginActivity.this.isRequestingFirebaseSms) {
                return;
            }
            this.isResendingCode = true;
            this.timeText.invalidate();
            this.problemText.invalidate();
            final Bundle bundle = new Bundle();
            bundle.putString("phone", this.phone);
            bundle.putString("ephone", this.emailPhone);
            bundle.putString("phoneFormated", this.requestPhone);
            this.nextPressed = true;
            TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode = new TLRPC$TL_auth_resendCode();
            tLRPC$TL_auth_resendCode.phone_number = this.requestPhone;
            tLRPC$TL_auth_resendCode.phone_code_hash = this.phoneHash;
            tryShowProgress(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_resendCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda38
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivitySmsView.this.lambda$resendCode$10(bundle, tLObject, tLRPC$TL_error);
                }
            }, 10));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resendCode$10(final Bundle bundle, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$resendCode$9(tLRPC$TL_error, bundle, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resendCode$9(TLRPC$TL_error tLRPC$TL_error, Bundle bundle, TLObject tLObject) {
            this.nextPressed = false;
            if (tLRPC$TL_error == null) {
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, (TLRPC$TL_auth_sentCode) tLObject);
            } else {
                String str = tLRPC$TL_error.text;
                if (str != null) {
                    if (str.contains("PHONE_NUMBER_INVALID")) {
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                    } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                    } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                        onBackPressed(true);
                        LoginActivity.this.setPage(0, true, null, true);
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                    } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("FloodWait", R.string.FloodWait));
                    } else if (tLRPC$TL_error.code != -1000) {
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred) + "\n" + tLRPC$TL_error.text);
                    }
                }
            }
            tryHideProgress(false);
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            CodeNumberField[] codeNumberFieldArr;
            super.onConfigurationChanged(configuration);
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            if (codeFieldContainer == null || (codeNumberFieldArr = codeFieldContainer.codeField) == null) {
                return;
            }
            for (CodeNumberField codeNumberField : codeNumberFieldArr) {
                if (Build.VERSION.SDK_INT >= 21) {
                    codeNumberField.setShowSoftInputOnFocusCompat(!hasCustomKeyboard() || LoginActivity.this.isCustomKeyboardForceDisabled());
                }
            }
        }

        private void tryShowProgress(int i) {
            lambda$tryShowProgress$11(i, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: tryShowProgress, reason: merged with bridge method [inline-methods] */
        public void lambda$tryShowProgress$11(final int i, final boolean z) {
            if (this.starsToDotsDrawable == null) {
                LoginActivity.this.needShowProgress(i, z);
                return;
            }
            if (this.isDotsAnimationVisible) {
                return;
            }
            this.isDotsAnimationVisible = true;
            if (this.hintDrawable.getCurrentFrame() != this.hintDrawable.getFramesCount() - 1) {
                this.hintDrawable.setOnAnimationEndListener(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda22
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.this.lambda$tryShowProgress$12(i, z);
                    }
                });
                return;
            }
            this.starsToDotsDrawable.setOnAnimationEndListener(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryShowProgress$14();
                }
            });
            this.blueImageView.setAutoRepeat(false);
            this.starsToDotsDrawable.setCurrentFrame(0, false);
            this.blueImageView.setAnimation(this.starsToDotsDrawable);
            this.blueImageView.playAnimation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryShowProgress$12(final int i, final boolean z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryShowProgress$11(i, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryShowProgress$14() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryShowProgress$13();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryShowProgress$13() {
            this.blueImageView.setAutoRepeat(true);
            this.dotsDrawable.setCurrentFrame(0, false);
            this.dotsDrawable.setAutoRepeat(1);
            this.blueImageView.setAnimation(this.dotsDrawable);
            this.blueImageView.playAnimation();
        }

        private void tryHideProgress(boolean z) {
            tryHideProgress(z, true);
        }

        private void tryHideProgress(boolean z, boolean z2) {
            if (this.starsToDotsDrawable == null) {
                LoginActivity.this.needHideProgress(z, z2);
            } else if (this.isDotsAnimationVisible) {
                this.isDotsAnimationVisible = false;
                this.blueImageView.setAutoRepeat(false);
                this.dotsDrawable.setAutoRepeat(0);
                this.dotsDrawable.setOnFinishCallback(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.this.lambda$tryHideProgress$18();
                    }
                }, this.dotsDrawable.getFramesCount() - 1);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryHideProgress$18() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryHideProgress$17();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryHideProgress$16() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryHideProgress$15();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryHideProgress$17() {
            this.dotsToStarsDrawable.setOnAnimationEndListener(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$tryHideProgress$16();
                }
            });
            this.blueImageView.setAutoRepeat(false);
            this.dotsToStarsDrawable.setCurrentFrame(0, false);
            this.blueImageView.setAnimation(this.dotsToStarsDrawable);
            this.blueImageView.playAnimation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryHideProgress$15() {
            this.blueImageView.setAutoRepeat(false);
            this.blueImageView.setAnimation(this.hintDrawable);
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            int i = this.currentType;
            if (i == 3 || i == 11) {
                return this.phone;
            }
            return LocaleController.getString("YourCode", R.string.YourCode);
        }

        /* JADX WARN: Removed duplicated region for block: B:127:0x0377  */
        /* JADX WARN: Removed duplicated region for block: B:128:0x0380  */
        @Override // org.telegram.ui.Components.SlideView
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void setParams(android.os.Bundle r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 1077
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivitySmsView.setParams(android.os.Bundle, boolean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setParams$20(View view, boolean z) {
            if (z) {
                LoginActivity.this.keyboardView.setEditText((EditText) view);
                LoginActivity.this.keyboardView.setDispatchBackWhenEmpty(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setProblemTextVisible(boolean z) {
            TextView textView = this.problemText;
            if (textView == null) {
                return;
            }
            float f = z ? 1.0f : 0.0f;
            if (textView.getAlpha() != f) {
                this.problemText.animate().cancel();
                this.problemText.animate().alpha(f).setDuration(150L).start();
            }
        }

        private void createCodeTimer() {
            if (this.codeTimer != null) {
                return;
            }
            this.codeTime = 15000;
            this.codeTimer = new Timer();
            this.lastCodeTime = System.currentTimeMillis();
            this.codeTimer.schedule(new AnonymousClass7(), 0L, 1000L);
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$7, reason: invalid class name */
        class AnonymousClass7 extends TimerTask {
            AnonymousClass7() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$7$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.AnonymousClass7.this.lambda$run$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0() {
                double currentTimeMillis = System.currentTimeMillis();
                double d = currentTimeMillis - LoginActivitySmsView.this.lastCodeTime;
                LoginActivitySmsView.this.lastCodeTime = currentTimeMillis;
                LoginActivitySmsView.access$9326(LoginActivitySmsView.this, d);
                if (LoginActivitySmsView.this.codeTime <= 1000) {
                    LoginActivitySmsView.this.setProblemTextVisible(true);
                    LoginActivitySmsView.this.timeText.setVisibility(8);
                    if (LoginActivitySmsView.this.problemText != null) {
                        LoginActivitySmsView.this.problemText.setVisibility(0);
                    }
                    LoginActivitySmsView.this.destroyCodeTimer();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void destroyCodeTimer() {
            try {
                synchronized (this.timerSync) {
                    Timer timer = this.codeTimer;
                    if (timer != null) {
                        timer.cancel();
                        this.codeTimer = null;
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        private void createTimer() {
            if (this.timeTimer != null) {
                return;
            }
            TextView textView = this.timeText;
            int i = Theme.key_windowBackgroundWhiteGrayText6;
            textView.setTextColor(Theme.getColor(i));
            this.timeText.setTag(R.id.color_key_tag, Integer.valueOf(i));
            Timer timer = new Timer();
            this.timeTimer = timer;
            timer.schedule(new AnonymousClass8(), 0L, 1000L);
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivitySmsView$8, reason: invalid class name */
        class AnonymousClass8 extends TimerTask {
            AnonymousClass8() {
            }

            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (LoginActivitySmsView.this.timeTimer == null) {
                    return;
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.AnonymousClass8.this.lambda$run$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$run$0() {
                double currentTimeMillis = System.currentTimeMillis();
                double d = currentTimeMillis - LoginActivitySmsView.this.lastCurrentTime;
                LoginActivitySmsView.this.lastCurrentTime = currentTimeMillis;
                LoginActivitySmsView.access$10026(LoginActivitySmsView.this, d);
                if (LoginActivitySmsView.this.time >= 1000) {
                    int i = (LoginActivitySmsView.this.time / 1000) / 60;
                    int i2 = (LoginActivitySmsView.this.time / 1000) - (i * 60);
                    if (LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 3 || LoginActivitySmsView.this.nextType == 11) {
                        LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("CallAvailableIn", R.string.CallAvailableIn, Integer.valueOf(i), Integer.valueOf(i2)));
                    } else if (LoginActivitySmsView.this.nextType == 2) {
                        LoginActivitySmsView.this.timeText.setText(LocaleController.formatString("SmsAvailableIn", R.string.SmsAvailableIn, Integer.valueOf(i), Integer.valueOf(i2)));
                    }
                    ProgressView unused = LoginActivitySmsView.this.progressView;
                    return;
                }
                LoginActivitySmsView.this.destroyTimer();
                if (LoginActivitySmsView.this.nextType == 3 || LoginActivitySmsView.this.nextType == 4 || LoginActivitySmsView.this.nextType == 2 || LoginActivitySmsView.this.nextType == 11) {
                    if (LoginActivitySmsView.this.nextType == 4) {
                        LoginActivitySmsView.this.timeText.setText(LocaleController.getString("RequestCallButton", R.string.RequestCallButton));
                    } else if (LoginActivitySmsView.this.nextType == 11 || LoginActivitySmsView.this.nextType == 3) {
                        LoginActivitySmsView.this.timeText.setText(LocaleController.getString("RequestMissedCall", R.string.RequestMissedCall));
                    } else {
                        LoginActivitySmsView.this.timeText.setText(LocaleController.getString("RequestSmsButton", R.string.RequestSmsButton));
                    }
                    TextView textView = LoginActivitySmsView.this.timeText;
                    int i3 = Theme.key_chats_actionBackground;
                    textView.setTextColor(Theme.getColor(i3));
                    LoginActivitySmsView.this.timeText.setTag(R.id.color_key_tag, Integer.valueOf(i3));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void destroyTimer() {
            TextView textView = this.timeText;
            int i = Theme.key_windowBackgroundWhiteGrayText6;
            textView.setTextColor(Theme.getColor(i));
            this.timeText.setTag(R.id.color_key_tag, Integer.valueOf(i));
            try {
                synchronized (this.timerSync) {
                    Timer timer = this.timeTimer;
                    if (timer != null) {
                        timer.cancel();
                        this.timeTimer = null;
                    }
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (LoginActivity.this.currentViewNum == 11) {
                if (this.nextPressed) {
                    return;
                }
            } else {
                if (this.nextPressed) {
                    return;
                }
                if ((LoginActivity.this.currentViewNum < 1 || LoginActivity.this.currentViewNum > 4) && LoginActivity.this.currentViewNum != 15) {
                    return;
                }
            }
            if (str == null) {
                str = this.codeFieldContainer.getCode();
            }
            int i = 0;
            if (TextUtils.isEmpty(str)) {
                LoginActivity.this.onFieldError(this.codeFieldContainer, false);
                return;
            }
            if (LoginActivity.this.currentViewNum < 1 || LoginActivity.this.currentViewNum > 4 || !this.codeFieldContainer.isFocusSuppressed) {
                this.nextPressed = true;
                int i2 = this.currentType;
                if (i2 == 15) {
                    NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
                } else if (i2 == 2) {
                    AndroidUtilities.setWaitingForSms(false);
                    NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
                } else if (i2 == 3) {
                    NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveCall);
                }
                this.waitingForEvent = false;
                int i3 = LoginActivity.this.activityMode;
                if (i3 == 1) {
                    this.requestPhone = LoginActivity.this.cancelDeletionPhone;
                    final TLRPC$TL_account_confirmPhone tLRPC$TL_account_confirmPhone = new TLRPC$TL_account_confirmPhone();
                    tLRPC$TL_account_confirmPhone.phone_code = str;
                    tLRPC$TL_account_confirmPhone.phone_code_hash = this.phoneHash;
                    destroyTimer();
                    CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
                    codeFieldContainer.isFocusSuppressed = true;
                    CodeNumberField[] codeNumberFieldArr = codeFieldContainer.codeField;
                    int length = codeNumberFieldArr.length;
                    while (i < length) {
                        codeNumberFieldArr[i].animateFocusedProgress(0.0f);
                        i++;
                    }
                    tryShowProgress(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_confirmPhone, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda40
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$28(tLRPC$TL_account_confirmPhone, tLObject, tLRPC$TL_error);
                        }
                    }, 2));
                    return;
                }
                if (i3 == 2) {
                    TLRPC$TL_account_changePhone tLRPC$TL_account_changePhone = new TLRPC$TL_account_changePhone();
                    tLRPC$TL_account_changePhone.phone_number = this.requestPhone;
                    tLRPC$TL_account_changePhone.phone_code = str;
                    tLRPC$TL_account_changePhone.phone_code_hash = this.phoneHash;
                    destroyTimer();
                    CodeFieldContainer codeFieldContainer2 = this.codeFieldContainer;
                    codeFieldContainer2.isFocusSuppressed = true;
                    CodeNumberField[] codeNumberFieldArr2 = codeFieldContainer2.codeField;
                    int length2 = codeNumberFieldArr2.length;
                    while (i < length2) {
                        codeNumberFieldArr2[i].animateFocusedProgress(0.0f);
                        i++;
                    }
                    lambda$tryShowProgress$11(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_changePhone, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda37
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$24(tLObject, tLRPC$TL_error);
                        }
                    }, 2), true);
                    LoginActivity.this.showDoneButton(true, true);
                    return;
                }
                final TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn = new TLRPC$TL_auth_signIn();
                tLRPC$TL_auth_signIn.phone_number = this.requestPhone;
                tLRPC$TL_auth_signIn.phone_code = str;
                tLRPC$TL_auth_signIn.phone_code_hash = this.phoneHash;
                tLRPC$TL_auth_signIn.flags |= 1;
                destroyTimer();
                CodeFieldContainer codeFieldContainer3 = this.codeFieldContainer;
                codeFieldContainer3.isFocusSuppressed = true;
                CodeNumberField[] codeNumberFieldArr3 = codeFieldContainer3.codeField;
                int length3 = codeNumberFieldArr3.length;
                while (i < length3) {
                    codeNumberFieldArr3[i].animateFocusedProgress(0.0f);
                    i++;
                }
                lambda$tryShowProgress$11(ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_signIn, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda42
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$36(tLRPC$TL_auth_signIn, tLObject, tLRPC$TL_error);
                    }
                }, 10), true);
                LoginActivity.this.showDoneButton(true, true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$24(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$23(tLRPC$TL_error, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0181  */
        /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onNextPressed$23(org.telegram.tgnet.TLRPC$TL_error r8, org.telegram.tgnet.TLObject r9) {
            /*
                Method dump skipped, instructions count: 411
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivitySmsView.lambda$onNextPressed$23(org.telegram.tgnet.TLRPC$TL_error, org.telegram.tgnet.TLObject):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$22() {
            try {
                ((BaseFragment) LoginActivity.this).fragmentView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(R.string.YourPasswordSuccess)).setMessage(LocaleController.formatString(R.string.ChangePhoneNumberSuccessWithPhone, PhoneFormat.getInstance().format("+" + this.requestPhone))).setPositiveButton(LocaleController.getString(R.string.OK), null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda3
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$21(dialogInterface);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$21(DialogInterface dialogInterface) {
            LoginActivity.this.finishFragment();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$28(final TLRPC$TL_account_confirmPhone tLRPC$TL_account_confirmPhone, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$27(tLRPC$TL_error, tLRPC$TL_account_confirmPhone);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$27(TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_account_confirmPhone tLRPC$TL_account_confirmPhone) {
            int i;
            int i2;
            tryHideProgress(false);
            this.nextPressed = false;
            if (tLRPC$TL_error == null) {
                animateSuccess(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$26();
                    }
                });
                return;
            }
            this.lastError = tLRPC$TL_error.text;
            int i3 = this.currentType;
            if ((i3 == 3 && ((i2 = this.nextType) == 4 || i2 == 2)) || ((i3 == 2 && ((i = this.nextType) == 4 || i == 3)) || (i3 == 4 && this.nextType == 2))) {
                createTimer();
            }
            int i4 = this.currentType;
            if (i4 == 15) {
                NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i4 == 2) {
                AndroidUtilities.setWaitingForSms(true);
                NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i4 == 3) {
                AndroidUtilities.setWaitingForCall(true);
                NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didReceiveCall);
            }
            this.waitingForEvent = true;
            if (this.currentType != 3) {
                AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_account_confirmPhone, new Object[0]);
            }
            if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                shakeWrongCode();
            } else if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                onBackPressed(true);
                LoginActivity.this.setPage(0, true, null, true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$26() {
            new AlertDialog.Builder(LoginActivity.this.getParentActivity()).setTitle(LocaleController.getString(R.string.CancelLinkSuccessTitle)).setMessage(LocaleController.formatString("CancelLinkSuccess", R.string.CancelLinkSuccess, PhoneFormat.getInstance().format("+" + this.phone))).setPositiveButton(LocaleController.getString(R.string.Close), null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$25(dialogInterface);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$25(DialogInterface dialogInterface) {
            LoginActivity.this.finishFragment();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$36(final TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$35(tLRPC$TL_error, tLObject, tLRPC$TL_auth_signIn);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0184  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onNextPressed$35(org.telegram.tgnet.TLRPC$TL_error r6, final org.telegram.tgnet.TLObject r7, final org.telegram.tgnet.TLRPC$TL_auth_signIn r8) {
            /*
                Method dump skipped, instructions count: 427
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivitySmsView.lambda$onNextPressed$35(org.telegram.tgnet.TLRPC$TL_error, org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_auth_signIn):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$29(Bundle bundle) {
            LoginActivity.this.setPage(5, true, bundle, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$30(TLObject tLObject) {
            LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$33(final TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$32(tLRPC$TL_error, tLObject, tLRPC$TL_auth_signIn);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$32(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn) {
            this.nextPressed = false;
            LoginActivity.this.showDoneButton(false, true);
            if (tLRPC$TL_error != null) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                return;
            }
            TLRPC$account_Password tLRPC$account_Password = (TLRPC$account_Password) tLObject;
            if (!TwoStepVerificationActivity.canHandleCurrentPassword(tLRPC$account_Password, true)) {
                AlertsCreator.showUpdateAppAlert(LoginActivity.this.getParentActivity(), LocaleController.getString("UpdateAppAlert", R.string.UpdateAppAlert), true);
                return;
            }
            final Bundle bundle = new Bundle();
            SerializedData serializedData = new SerializedData(tLRPC$account_Password.getObjectSize());
            tLRPC$account_Password.serializeToStream(serializedData);
            bundle.putString("password", Utilities.bytesToHex(serializedData.toByteArray()));
            bundle.putString("phoneFormated", this.requestPhone);
            bundle.putString("phoneHash", this.phoneHash);
            bundle.putString("code", tLRPC$TL_auth_signIn.phone_code);
            animateSuccess(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onNextPressed$31(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$31(Bundle bundle) {
            LoginActivity.this.setPage(6, true, bundle, false);
        }

        private void animateSuccess(final Runnable runnable) {
            if (this.currentType == 3) {
                runnable.run();
                return;
            }
            final int i = 0;
            while (true) {
                CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
                if (i < codeFieldContainer.codeField.length) {
                    codeFieldContainer.postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.LoginActivitySmsView.this.lambda$animateSuccess$37(i);
                        }
                    }, i * 75);
                    i++;
                } else {
                    codeFieldContainer.postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda27
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.LoginActivitySmsView.this.lambda$animateSuccess$38(runnable);
                        }
                    }, (this.codeFieldContainer.codeField.length * 75) + 400);
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$animateSuccess$37(int i) {
            this.codeFieldContainer.codeField[i].animateSuccessProgress(1.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$animateSuccess$38(Runnable runnable) {
            int i = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i < codeNumberFieldArr.length) {
                    codeNumberFieldArr[i].animateSuccessProgress(0.0f);
                    i++;
                } else {
                    runnable.run();
                    this.codeFieldContainer.isFocusSuppressed = false;
                    return;
                }
            }
        }

        private void shakeWrongCode() {
            try {
                this.codeFieldContainer.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            int i = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    break;
                }
                codeNumberFieldArr[i].setText("");
                this.codeFieldContainer.codeField[i].animateErrorProgress(1.0f);
                i++;
            }
            if (this.errorViewSwitcher.getCurrentView() != this.wrongCode) {
                this.errorViewSwitcher.showNext();
            }
            this.codeFieldContainer.codeField[0].requestFocus();
            AndroidUtilities.shakeViewSpring(this.codeFieldContainer, this.currentType == 11 ? 3.5f : 10.0f, new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$shakeWrongCode$40();
                }
            });
            removeCallbacks(this.errorColorTimeout);
            postDelayed(this.errorColorTimeout, 5000L);
            this.postedErrorColorTimeout = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$shakeWrongCode$40() {
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$shakeWrongCode$39();
                }
            }, 150L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$shakeWrongCode$39() {
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            int i = 0;
            codeFieldContainer.isFocusSuppressed = false;
            codeFieldContainer.codeField[0].requestFocus();
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    return;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            removeCallbacks(this.errorColorTimeout);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            if (LoginActivity.this.activityMode != 0) {
                LoginActivity.this.finishFragment();
                return false;
            }
            if (!z) {
                LoginActivity loginActivity = LoginActivity.this;
                loginActivity.showDialog(new AlertDialog.Builder(loginActivity.getParentActivity()).setTitle(LocaleController.getString(R.string.EditNumber)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("EditNumberInfo", R.string.EditNumberInfo, this.phone))).setPositiveButton(LocaleController.getString(R.string.Close), null).setNegativeButton(LocaleController.getString(R.string.Edit), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.LoginActivitySmsView.this.lambda$onBackPressed$41(dialogInterface, i);
                    }
                }).create());
                return false;
            }
            this.nextPressed = false;
            tryHideProgress(true);
            TLRPC$TL_auth_cancelCode tLRPC$TL_auth_cancelCode = new TLRPC$TL_auth_cancelCode();
            tLRPC$TL_auth_cancelCode.phone_number = this.requestPhone;
            tLRPC$TL_auth_cancelCode.phone_code_hash = this.phoneHash;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_cancelCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda43
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivitySmsView.lambda$onBackPressed$42(tLObject, tLRPC$TL_error);
                }
            }, 10);
            destroyTimer();
            destroyCodeTimer();
            this.currentParams = null;
            int i = this.currentType;
            if (i == 15) {
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i == 2) {
                AndroidUtilities.setWaitingForSms(false);
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i == 3) {
                AndroidUtilities.setWaitingForCall(false);
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveCall);
            }
            this.waitingForEvent = false;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackPressed$41(DialogInterface dialogInterface, int i) {
            onBackPressed(true);
            LoginActivity.this.setPage(0, true, null, true);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onDestroyActivity() {
            super.onDestroyActivity();
            int i = this.currentType;
            if (i == 15) {
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i == 2) {
                AndroidUtilities.setWaitingForSms(false);
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveSmsCode);
            } else if (i == 3) {
                AndroidUtilities.setWaitingForCall(false);
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didReceiveCall);
            }
            this.waitingForEvent = false;
            destroyTimer();
            destroyCodeTimer();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            RLottieDrawable rLottieDrawable = this.hintDrawable;
            if (rLottieDrawable != null) {
                rLottieDrawable.setCurrentFrame(0);
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySmsView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySmsView.this.lambda$onShow$43();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$43() {
            CodeNumberField[] codeNumberFieldArr;
            if (this.currentType != 3 && (codeNumberFieldArr = this.codeFieldContainer.codeField) != null) {
                for (int length = codeNumberFieldArr.length - 1; length >= 0; length--) {
                    if (length == 0 || this.codeFieldContainer.codeField[length].length() != 0) {
                        this.codeFieldContainer.codeField[length].requestFocus();
                        CodeNumberField[] codeNumberFieldArr2 = this.codeFieldContainer.codeField;
                        codeNumberFieldArr2[length].setSelection(codeNumberFieldArr2[length].length());
                        LoginActivity.this.showKeyboard(this.codeFieldContainer.codeField[length]);
                        break;
                    }
                }
            }
            RLottieDrawable rLottieDrawable = this.hintDrawable;
            if (rLottieDrawable != null) {
                rLottieDrawable.start();
            }
            if (this.currentType == 15) {
                this.openFragmentImageView.getAnimatedDrawable().setCurrentFrame(0, false);
                this.openFragmentImageView.getAnimatedDrawable().start();
            }
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (this.waitingForEvent) {
                CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
                if (codeFieldContainer.codeField == null) {
                    return;
                }
                if (i == NotificationCenter.didReceiveSmsCode) {
                    codeFieldContainer.setText("" + objArr[0]);
                    onNextPressed(null);
                    return;
                }
                if (i == NotificationCenter.didReceiveCall) {
                    String str = "" + objArr[0];
                    if (AndroidUtilities.checkPhonePattern(this.pattern, str)) {
                        if (!this.pattern.equals("*")) {
                            this.catchedPhone = str;
                            AndroidUtilities.endIncomingCall();
                        }
                        onNextPressed(str);
                        CallReceiver.clearLastCall();
                    }
                }
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onHide() {
            super.onHide();
            this.isResendingCode = false;
            this.nextPressed = false;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String code = this.codeFieldContainer.getCode();
            if (code.length() != 0) {
                bundle.putString("smsview_code_" + this.currentType, code);
            }
            String str = this.catchedPhone;
            if (str != null) {
                bundle.putString("catchedPhone", str);
            }
            if (this.currentParams != null) {
                bundle.putBundle("smsview_params_" + this.currentType, this.currentParams);
            }
            int i = this.time;
            if (i != 0) {
                bundle.putInt("time", i);
            }
            int i2 = this.openTime;
            if (i2 != 0) {
                bundle.putInt("open", i2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("smsview_params_" + this.currentType);
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            String string = bundle.getString("catchedPhone");
            if (string != null) {
                this.catchedPhone = string;
            }
            String string2 = bundle.getString("smsview_code_" + this.currentType);
            if (string2 != null) {
                CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
                if (codeFieldContainer.codeField != null) {
                    codeFieldContainer.setText(string2);
                }
            }
            int i = bundle.getInt("time");
            if (i != 0) {
                this.time = i;
            }
            int i2 = bundle.getInt("open");
            if (i2 != 0) {
                this.openTime = i2;
            }
        }
    }

    public class LoginActivityPasswordView extends SlideView {
        private TextView cancelButton;
        private EditTextBoldCursor codeField;
        private TextView confirmTextView;
        private Bundle currentParams;
        private TLRPC$account_Password currentPassword;
        private RLottieImageView lockImageView;
        private boolean nextPressed;
        private OutlineTextContainerView outlineCodeField;
        private String passwordString;
        private String phoneCode;
        private String phoneHash;
        private String requestPhone;
        private TextView titleView;

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0142  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x01c9  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x01cc  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0144  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LoginActivityPasswordView(final android.content.Context r20) {
            /*
                Method dump skipped, instructions count: 502
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivityPasswordView.<init>(org.telegram.ui.LoginActivity, android.content.Context):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view, boolean z) {
            this.outlineCodeField.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$1(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$6(Context context, View view) {
            if (LoginActivity.this.radialProgressView.getTag() != null) {
                return;
            }
            if (this.currentPassword.has_recovery) {
                LoginActivity.this.needShowProgress(0);
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(new TLRPC$TL_auth_requestPasswordRecovery(), new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda12
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LoginActivity.LoginActivityPasswordView.this.lambda$new$4(tLObject, tLRPC$TL_error);
                    }
                }, 10);
            } else {
                AndroidUtilities.hideKeyboard(this.codeField);
                new AlertDialog.Builder(context).setTitle(LocaleController.getString(R.string.RestorePasswordNoEmailTitle)).setMessage(LocaleController.getString(R.string.RestorePasswordNoEmailText)).setPositiveButton(LocaleController.getString(R.string.Close), null).setNegativeButton(LocaleController.getString(R.string.ResetAccount), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.LoginActivityPasswordView.this.lambda$new$5(dialogInterface, i);
                    }
                }).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityPasswordView.this.lambda$new$3(tLRPC$TL_error, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
            String formatPluralString;
            LoginActivity.this.needHideProgress(false);
            if (tLRPC$TL_error == null) {
                final TLRPC$TL_auth_passwordRecovery tLRPC$TL_auth_passwordRecovery = (TLRPC$TL_auth_passwordRecovery) tLObject;
                if (LoginActivity.this.getParentActivity() == null) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getParentActivity());
                String str = tLRPC$TL_auth_passwordRecovery.email_pattern;
                SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(str);
                int indexOf = str.indexOf(42);
                int lastIndexOf = str.lastIndexOf(42);
                if (indexOf != lastIndexOf && indexOf != -1 && lastIndexOf != -1) {
                    TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                    textStyleRun.flags |= 256;
                    textStyleRun.start = indexOf;
                    int i = lastIndexOf + 1;
                    textStyleRun.end = i;
                    valueOf.setSpan(new TextStyleSpan(textStyleRun), indexOf, i, 0);
                }
                builder.setMessage(AndroidUtilities.formatSpannable(LocaleController.getString(R.string.RestoreEmailSent), valueOf));
                builder.setTitle(LocaleController.getString("RestoreEmailSentTitle", R.string.RestoreEmailSentTitle));
                builder.setPositiveButton(LocaleController.getString(R.string.Continue), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        LoginActivity.LoginActivityPasswordView.this.lambda$new$2(tLRPC$TL_auth_passwordRecovery, dialogInterface, i2);
                    }
                });
                Dialog showDialog = LoginActivity.this.showDialog(builder.create());
                if (showDialog != null) {
                    showDialog.setCanceledOnTouchOutside(false);
                    showDialog.setCancelable(false);
                    return;
                }
                return;
            }
            if (!tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                return;
            }
            int intValue = Utilities.parseInt((CharSequence) tLRPC$TL_error.text).intValue();
            if (intValue < 60) {
                formatPluralString = LocaleController.formatPluralString("Seconds", intValue, new Object[0]);
            } else {
                formatPluralString = LocaleController.formatPluralString("Minutes", intValue / 60, new Object[0]);
            }
            LoginActivity.this.needShowAlert(LocaleController.getString(R.string.WrongCodeTitle), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, formatPluralString));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(TLRPC$TL_auth_passwordRecovery tLRPC$TL_auth_passwordRecovery, DialogInterface dialogInterface, int i) {
            Bundle bundle = new Bundle();
            bundle.putString("email_unconfirmed_pattern", tLRPC$TL_auth_passwordRecovery.email_pattern);
            bundle.putString("password", this.passwordString);
            bundle.putString("requestPhone", this.requestPhone);
            bundle.putString("phoneHash", this.phoneHash);
            bundle.putString("phoneCode", this.phoneCode);
            LoginActivity.this.setPage(7, true, bundle, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5(DialogInterface dialogInterface, int i) {
            LoginActivity.this.tryResetAccount(this.requestPhone, this.phoneHash, this.phoneCode);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            TextView textView = this.titleView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            textView.setTextColor(Theme.getColor(i));
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.codeField.setTextColor(Theme.getColor(i));
            this.codeField.setCursorColor(Theme.getColor(i));
            this.codeField.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.cancelButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.outlineCodeField.updateColor();
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onCancelPressed() {
            this.nextPressed = false;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            if (bundle.isEmpty()) {
                AndroidUtilities.hideKeyboard(this.codeField);
                return;
            }
            this.codeField.setText("");
            this.currentParams = bundle;
            String string = bundle.getString("password");
            this.passwordString = string;
            if (string != null) {
                SerializedData serializedData = new SerializedData(Utilities.hexToBytes(string));
                this.currentPassword = TLRPC$account_Password.TLdeserialize(serializedData, serializedData.readInt32(false), false);
            }
            this.requestPhone = bundle.getString("phoneFormated");
            this.phoneHash = bundle.getString("phoneHash");
            this.phoneCode = bundle.getString("code");
            TLRPC$account_Password tLRPC$account_Password = this.currentPassword;
            if (tLRPC$account_Password != null && !TextUtils.isEmpty(tLRPC$account_Password.hint)) {
                this.codeField.setHint(this.currentPassword.hint);
            } else {
                this.codeField.setHint((CharSequence) null);
            }
        }

        private void onPasscodeError(boolean z) {
            if (LoginActivity.this.getParentActivity() == null) {
                return;
            }
            if (z) {
                this.codeField.setText("");
            }
            LoginActivity.this.onFieldError(this.outlineCodeField, true);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (this.nextPressed) {
                return;
            }
            final String obj = this.codeField.getText().toString();
            if (obj.length() == 0) {
                onPasscodeError(false);
                return;
            }
            this.nextPressed = true;
            LoginActivity.this.needShowProgress(0);
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$12(obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$12(String str) {
            TLRPC$PasswordKdfAlgo tLRPC$PasswordKdfAlgo = this.currentPassword.current_algo;
            boolean z = tLRPC$PasswordKdfAlgo instanceof TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow;
            byte[] x = z ? SRPHelper.getX(AndroidUtilities.getStringBytes(str), (TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow) tLRPC$PasswordKdfAlgo) : null;
            TLRPC$TL_auth_checkPassword tLRPC$TL_auth_checkPassword = new TLRPC$TL_auth_checkPassword();
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda13
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$11(tLObject, tLRPC$TL_error);
                }
            };
            if (z) {
                TLRPC$account_Password tLRPC$account_Password = this.currentPassword;
                TLRPC$TL_inputCheckPasswordSRP startCheck = SRPHelper.startCheck(x, tLRPC$account_Password.srp_id, tLRPC$account_Password.srp_B, (TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow) tLRPC$PasswordKdfAlgo);
                tLRPC$TL_auth_checkPassword.password = startCheck;
                if (startCheck != null) {
                    ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_checkPassword, requestDelegate, 10);
                    return;
                }
                TLRPC$TL_error tLRPC$TL_error = new TLRPC$TL_error();
                tLRPC$TL_error.text = "PASSWORD_HASH_INVALID";
                requestDelegate.run(null, tLRPC$TL_error);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$11(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$10(tLRPC$TL_error, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$10(TLRPC$TL_error tLRPC$TL_error, final TLObject tLObject) {
            String formatPluralString;
            this.nextPressed = false;
            if (tLRPC$TL_error != null && "SRP_ID_INVALID".equals(tLRPC$TL_error.text)) {
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(new TLRPC$TL_account_getPassword(), new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda11
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject2, TLRPC$TL_error tLRPC$TL_error2) {
                        LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$8(tLObject2, tLRPC$TL_error2);
                    }
                }, 8);
                return;
            }
            if (tLObject instanceof TLRPC$TL_auth_authorization) {
                LoginActivity.this.showDoneButton(false, true);
                postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$9(tLObject);
                    }
                }, 150L);
                return;
            }
            LoginActivity.this.needHideProgress(false);
            if (tLRPC$TL_error.text.equals("PASSWORD_HASH_INVALID")) {
                onPasscodeError(true);
                return;
            }
            if (!tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                return;
            }
            int intValue = Utilities.parseInt((CharSequence) tLRPC$TL_error.text).intValue();
            if (intValue < 60) {
                formatPluralString = LocaleController.formatPluralString("Seconds", intValue, new Object[0]);
            } else {
                formatPluralString = LocaleController.formatPluralString("Minutes", intValue / 60, new Object[0]);
            }
            LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, formatPluralString));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$8(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityPasswordView.this.lambda$onNextPressed$7(tLRPC$TL_error, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$7(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
            if (tLRPC$TL_error == null) {
                this.currentPassword = (TLRPC$account_Password) tLObject;
                onNextPressed(null);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$9(TLObject tLObject) {
            LoginActivity.this.needHideProgress(false, false);
            AndroidUtilities.hideKeyboard(this.codeField);
            LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            this.nextPressed = false;
            LoginActivity.this.needHideProgress(true);
            this.currentParams = null;
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityPasswordView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityPasswordView.this.lambda$onShow$13();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$13() {
            EditTextBoldCursor editTextBoldCursor = this.codeField;
            if (editTextBoldCursor != null) {
                editTextBoldCursor.requestFocus();
                EditTextBoldCursor editTextBoldCursor2 = this.codeField;
                editTextBoldCursor2.setSelection(editTextBoldCursor2.length());
                LoginActivity.this.showKeyboard(this.codeField);
                this.lockImageView.getAnimatedDrawable().setCurrentFrame(0, false);
                this.lockImageView.playAnimation();
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String obj = this.codeField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("passview_code", obj);
            }
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("passview_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("passview_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            String string = bundle.getString("passview_code");
            if (string != null) {
                this.codeField.setText(string);
            }
        }
    }

    public class LoginActivityResetWaitView extends SlideView {
        private TextView confirmTextView;
        private Bundle currentParams;
        private String phoneCode;
        private String phoneHash;
        private String requestPhone;
        private TextView resetAccountButton;
        private TextView resetAccountText;
        private TextView resetAccountTime;
        private int startTime;
        private Runnable timeRunnable;
        private TextView titleView;
        private RLottieImageView waitImageView;
        private int waitTime;
        private Boolean wasResetButtonActive;

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        public LoginActivityResetWaitView(Context context) {
            super(context);
            setOrientation(1);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(1);
            linearLayout.setGravity(17);
            FrameLayout frameLayout = new FrameLayout(context);
            RLottieImageView rLottieImageView = new RLottieImageView(context);
            this.waitImageView = rLottieImageView;
            rLottieImageView.setAutoRepeat(true);
            this.waitImageView.setAnimation(R.raw.sandclock, 120, 120);
            frameLayout.addView(this.waitImageView, LayoutHelper.createFrame(120, 120, 1));
            Point point = AndroidUtilities.displaySize;
            frameLayout.setVisibility((point.x <= point.y || AndroidUtilities.isTablet()) ? 0 : 8);
            linearLayout.addView(frameLayout, LayoutHelper.createFrame(-1, -2, 1));
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTextSize(1, 18.0f);
            this.titleView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            TextView textView2 = this.titleView;
            int i = R.string.ResetAccount;
            textView2.setText(LocaleController.getString(i));
            this.titleView.setGravity(17);
            this.titleView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            linearLayout.addView(this.titleView, LayoutHelper.createFrame(-1, -2.0f, 1, 32.0f, 16.0f, 32.0f, 0.0f));
            TextView textView3 = new TextView(context);
            this.confirmTextView = textView3;
            textView3.setTextSize(1, 14.0f);
            this.confirmTextView.setGravity(1);
            this.confirmTextView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            linearLayout.addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, 1, 12, 8, 12, 0));
            addView(linearLayout, LayoutHelper.createLinear(-1, 0, 1.0f));
            TextView textView4 = new TextView(context);
            this.resetAccountText = textView4;
            textView4.setGravity(1);
            this.resetAccountText.setText(LocaleController.getString("ResetAccountStatus", R.string.ResetAccountStatus));
            this.resetAccountText.setTextSize(1, 14.0f);
            this.resetAccountText.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.resetAccountText, LayoutHelper.createLinear(-2, -2, 49, 0, 24, 0, 0));
            TextView textView5 = new TextView(context);
            this.resetAccountTime = textView5;
            textView5.setGravity(1);
            this.resetAccountTime.setTextSize(1, 20.0f);
            this.resetAccountTime.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.resetAccountTime.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.resetAccountTime, LayoutHelper.createLinear(-2, -2, 1, 0, 8, 0, 0));
            TextView textView6 = new TextView(context);
            this.resetAccountButton = textView6;
            textView6.setGravity(17);
            this.resetAccountButton.setText(LocaleController.getString(i));
            this.resetAccountButton.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.resetAccountButton.setTextSize(1, 15.0f);
            this.resetAccountButton.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.resetAccountButton.setPadding(AndroidUtilities.dp(34.0f), 0, AndroidUtilities.dp(34.0f), 0);
            this.resetAccountButton.setTextColor(-1);
            addView(this.resetAccountButton, LayoutHelper.createLinear(-1, 50, 1, 16, 32, 16, 48));
            this.resetAccountButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.LoginActivityResetWaitView.this.lambda$new$3(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            if (LoginActivity.this.radialProgressView.getTag() != null) {
                return;
            }
            LoginActivity loginActivity = LoginActivity.this;
            loginActivity.showDialog(new AlertDialog.Builder(loginActivity.getParentActivity()).setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning)).setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText)).setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivityResetWaitView.this.lambda$new$2(dialogInterface, i);
                }
            }).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).create());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(DialogInterface dialogInterface, int i) {
            LoginActivity.this.needShowProgress(0);
            TLRPC$TL_account_deleteAccount tLRPC$TL_account_deleteAccount = new TLRPC$TL_account_deleteAccount();
            tLRPC$TL_account_deleteAccount.reason = "Forgot password";
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_deleteAccount, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$$ExternalSyntheticLambda3
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityResetWaitView.this.lambda$new$1(tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityResetWaitView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityResetWaitView.this.lambda$new$0(tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(TLRPC$TL_error tLRPC$TL_error) {
            LoginActivity.this.needHideProgress(false);
            if (tLRPC$TL_error == null) {
                if (this.requestPhone == null || this.phoneHash == null || this.phoneCode == null) {
                    LoginActivity.this.setPage(0, true, null, true);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phoneFormated", this.requestPhone);
                bundle.putString("phoneHash", this.phoneHash);
                bundle.putString("code", this.phoneCode);
                LoginActivity.this.setPage(5, true, bundle, false);
                return;
            }
            if (tLRPC$TL_error.text.equals("2FA_RECENT_CONFIRM")) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
            } else {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            TextView textView = this.titleView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            textView.setTextColor(Theme.getColor(i));
            this.confirmTextView.setTextColor(Theme.getColor(i));
            this.resetAccountText.setTextColor(Theme.getColor(i));
            this.resetAccountTime.setTextColor(Theme.getColor(i));
            this.resetAccountButton.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), Theme.getColor(Theme.key_changephoneinfo_image2), Theme.getColor(Theme.key_chats_actionPressedBackground)));
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("ResetAccount", R.string.ResetAccount);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateTimeText() {
            int max = Math.max(0, this.waitTime - (ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).getCurrentTime() - this.startTime));
            int i = max / 86400;
            int round = Math.round(max / 86400.0f);
            int i2 = max / 3600;
            int i3 = (max / 60) % 60;
            int i4 = max % 60;
            if (i >= 2) {
                this.resetAccountTime.setText(LocaleController.formatPluralString("Days", round, new Object[0]));
            } else {
                this.resetAccountTime.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
            }
            boolean z = max == 0;
            Boolean bool = this.wasResetButtonActive;
            if (bool == null || bool.booleanValue() != z) {
                if (!z) {
                    this.waitImageView.setAutoRepeat(true);
                    if (!this.waitImageView.isPlaying()) {
                        this.waitImageView.playAnimation();
                    }
                } else {
                    this.waitImageView.getAnimatedDrawable().setAutoRepeat(0);
                }
                this.resetAccountTime.setVisibility(z ? 4 : 0);
                this.resetAccountText.setVisibility(z ? 4 : 0);
                this.resetAccountButton.setVisibility(z ? 0 : 4);
                this.wasResetButtonActive = Boolean.valueOf(z);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            this.currentParams = bundle;
            this.requestPhone = bundle.getString("phoneFormated");
            this.phoneHash = bundle.getString("phoneHash");
            this.phoneCode = bundle.getString("code");
            this.startTime = bundle.getInt("startTime");
            this.waitTime = bundle.getInt("waitTime");
            this.confirmTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ResetAccountInfo", R.string.ResetAccountInfo, LocaleController.addNbsp(PhoneFormat.getInstance().format("+" + this.requestPhone)))));
            updateTimeText();
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.LoginActivity.LoginActivityResetWaitView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (LoginActivityResetWaitView.this.timeRunnable != this) {
                        return;
                    }
                    LoginActivityResetWaitView.this.updateTimeText();
                    AndroidUtilities.runOnUIThread(LoginActivityResetWaitView.this.timeRunnable, 1000L);
                }
            };
            this.timeRunnable = runnable;
            AndroidUtilities.runOnUIThread(runnable, 1000L);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            LoginActivity.this.needHideProgress(true);
            AndroidUtilities.cancelRunOnUIThread(this.timeRunnable);
            this.timeRunnable = null;
            this.currentParams = null;
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("resetview_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("resetview_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
        }
    }

    public class LoginActivitySetupEmail extends SlideView {
        private Bundle currentParams;
        private EditTextBoldCursor emailField;
        private OutlineTextContainerView emailOutlineView;
        private String emailPhone;
        private GoogleSignInAccount googleAccount;
        private RLottieImageView inboxImageView;
        private LoginOrView loginOrView;
        private boolean nextPressed;
        private String phone;
        private String phoneHash;
        private String requestPhone;
        private TextView signInWithGoogleView;
        private TextView subtitleView;
        private TextView titleView;

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:13:0x00f3  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x00f6  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0079  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LoginActivitySetupEmail(android.content.Context r27) {
            /*
                Method dump skipped, instructions count: 597
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivitySetupEmail.<init>(org.telegram.ui.LoginActivity, android.content.Context):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view, boolean z) {
            this.emailOutlineView.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$1(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            NotificationCenter.getGlobalInstance().addObserver(new NotificationCenter.NotificationCenterDelegate() { // from class: org.telegram.ui.LoginActivity.LoginActivitySetupEmail.2
                @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
                public void didReceivedNotification(int i, int i2, Object... objArr) {
                    int intValue = ((Integer) objArr[0]).intValue();
                    ((Integer) objArr[1]).intValue();
                    Intent intent = (Intent) objArr[2];
                    NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.onActivityResultReceived);
                    if (intValue == 200) {
                        try {
                            LoginActivitySetupEmail.this.googleAccount = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
                            LoginActivitySetupEmail.this.onNextPressed(null);
                        } catch (ApiException e) {
                            FileLog.e(e);
                        }
                    }
                }
            }, NotificationCenter.onActivityResultReceived);
            final GoogleSignInClient client = GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder().requestIdToken(BuildVars.GOOGLE_AUTH_CLIENT_ID).requestEmail().build());
            client.signOut().addOnCompleteListener(new OnCompleteListener() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda3
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$new$2(client, task);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(GoogleSignInClient googleSignInClient, Task task) {
            LoginActivity.this.getParentActivity().startActivityForResult(googleSignInClient.getSignInIntent(), 200);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            TextView textView = this.titleView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            textView.setTextColor(Theme.getColor(i));
            this.subtitleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.emailField.setTextColor(Theme.getColor(i));
            this.signInWithGoogleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.loginOrView.updateColors();
            this.emailOutlineView.invalidate();
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("AddEmailTitle", R.string.AddEmailTitle);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            this.emailField.setText("");
            this.currentParams = bundle;
            this.phone = bundle.getString("phone");
            this.emailPhone = this.currentParams.getString("ephone");
            this.requestPhone = this.currentParams.getString("phoneFormated");
            this.phoneHash = this.currentParams.getString("phoneHash");
            int i = (bundle.getBoolean("googleSignInAllowed") && PushListenerController.GooglePushListenerServiceProvider.INSTANCE.hasServices()) ? 0 : 8;
            this.loginOrView.setVisibility(i);
            this.signInWithGoogleView.setVisibility(i);
            LoginActivity.this.showKeyboard(this.emailField);
            this.emailField.requestFocus();
        }

        private void onPasscodeError(boolean z) {
            if (LoginActivity.this.getParentActivity() == null) {
                return;
            }
            try {
                this.emailOutlineView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (z) {
                this.emailField.setText("");
            }
            this.emailField.requestFocus();
            LoginActivity.this.onFieldError(this.emailOutlineView, true);
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$onPasscodeError$4();
                }
            }, 300L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPasscodeError$4() {
            this.emailField.requestFocus();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (this.nextPressed) {
                return;
            }
            GoogleSignInAccount googleSignInAccount = this.googleAccount;
            String email = googleSignInAccount != null ? googleSignInAccount.getEmail() : this.emailField.getText().toString();
            final Bundle bundle = new Bundle();
            bundle.putString("phone", this.phone);
            bundle.putString("ephone", this.emailPhone);
            bundle.putString("phoneFormated", this.requestPhone);
            bundle.putString("phoneHash", this.phoneHash);
            bundle.putString("email", email);
            bundle.putBoolean("setup", true);
            if (this.googleAccount != null) {
                final TLRPC$TL_account_verifyEmail tLRPC$TL_account_verifyEmail = new TLRPC$TL_account_verifyEmail();
                if (LoginActivity.this.activityMode == 3) {
                    tLRPC$TL_account_verifyEmail.purpose = new TLRPC$TL_emailVerifyPurposeLoginChange();
                } else {
                    TLRPC$TL_emailVerifyPurposeLoginSetup tLRPC$TL_emailVerifyPurposeLoginSetup = new TLRPC$TL_emailVerifyPurposeLoginSetup();
                    tLRPC$TL_emailVerifyPurposeLoginSetup.phone_number = this.requestPhone;
                    tLRPC$TL_emailVerifyPurposeLoginSetup.phone_code_hash = this.phoneHash;
                    tLRPC$TL_account_verifyEmail.purpose = tLRPC$TL_emailVerifyPurposeLoginSetup;
                }
                TLRPC$TL_emailVerificationGoogle tLRPC$TL_emailVerificationGoogle = new TLRPC$TL_emailVerificationGoogle();
                tLRPC$TL_emailVerificationGoogle.token = this.googleAccount.getIdToken();
                tLRPC$TL_account_verifyEmail.verification = tLRPC$TL_emailVerificationGoogle;
                this.googleAccount = null;
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_verifyEmail, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda9
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LoginActivity.LoginActivitySetupEmail.this.lambda$onNextPressed$6(bundle, tLRPC$TL_account_verifyEmail, tLObject, tLRPC$TL_error);
                    }
                }, 10);
                return;
            }
            if (TextUtils.isEmpty(email)) {
                onPasscodeError(false);
                return;
            }
            this.nextPressed = true;
            LoginActivity.this.needShowProgress(0);
            final TLRPC$TL_account_sendVerifyEmailCode tLRPC$TL_account_sendVerifyEmailCode = new TLRPC$TL_account_sendVerifyEmailCode();
            if (LoginActivity.this.activityMode == 3) {
                tLRPC$TL_account_sendVerifyEmailCode.purpose = new TLRPC$TL_emailVerifyPurposeLoginChange();
            } else {
                TLRPC$TL_emailVerifyPurposeLoginSetup tLRPC$TL_emailVerifyPurposeLoginSetup2 = new TLRPC$TL_emailVerifyPurposeLoginSetup();
                tLRPC$TL_emailVerifyPurposeLoginSetup2.phone_number = this.requestPhone;
                tLRPC$TL_emailVerifyPurposeLoginSetup2.phone_code_hash = this.phoneHash;
                tLRPC$TL_account_sendVerifyEmailCode.purpose = tLRPC$TL_emailVerifyPurposeLoginSetup2;
            }
            tLRPC$TL_account_sendVerifyEmailCode.email = email;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_account_sendVerifyEmailCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda8
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$onNextPressed$8(bundle, tLRPC$TL_account_sendVerifyEmailCode, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$6(final Bundle bundle, final TLRPC$TL_account_verifyEmail tLRPC$TL_account_verifyEmail, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$onNextPressed$5(tLObject, bundle, tLRPC$TL_error, tLRPC$TL_account_verifyEmail);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$5(TLObject tLObject, Bundle bundle, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_account_verifyEmail tLRPC$TL_account_verifyEmail) {
            if ((tLObject instanceof TLRPC$TL_account_emailVerified) && LoginActivity.this.activityMode == 3) {
                LoginActivity.this.finishFragment();
                LoginActivity.this.emailChangeFinishCallback.run();
                return;
            }
            if (tLObject instanceof TLRPC$TL_account_emailVerifiedLogin) {
                TLRPC$TL_account_emailVerifiedLogin tLRPC$TL_account_emailVerifiedLogin = (TLRPC$TL_account_emailVerifiedLogin) tLObject;
                bundle.putString("email", tLRPC$TL_account_emailVerifiedLogin.email);
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, tLRPC$TL_account_emailVerifiedLogin.sent_code);
            } else if (tLRPC$TL_error != null) {
                if (tLRPC$TL_error.text.contains("EMAIL_NOT_ALLOWED")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString(R.string.EmailNotAllowed));
                } else if (tLRPC$TL_error.text.contains("EMAIL_TOKEN_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString(R.string.EmailTokenInvalid));
                } else if (tLRPC$TL_error.code != -1000) {
                    AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_account_verifyEmail, new Object[0]);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$8(final Bundle bundle, final TLRPC$TL_account_sendVerifyEmailCode tLRPC$TL_account_sendVerifyEmailCode, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$onNextPressed$7(tLObject, bundle, tLRPC$TL_error, tLRPC$TL_account_sendVerifyEmailCode);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$7(TLObject tLObject, Bundle bundle, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_account_sendVerifyEmailCode tLRPC$TL_account_sendVerifyEmailCode) {
            LoginActivity.this.needHideProgress(false);
            this.nextPressed = false;
            if (tLObject instanceof TLRPC$TL_account_sentEmailCode) {
                LoginActivity.this.fillNextCodeParams(bundle, (TLRPC$TL_account_sentEmailCode) tLObject);
                return;
            }
            String str = tLRPC$TL_error.text;
            if (str != null) {
                if (str.contains("EMAIL_INVALID")) {
                    onPasscodeError(false);
                    return;
                }
                if (tLRPC$TL_error.text.contains("EMAIL_NOT_ALLOWED")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString(R.string.EmailNotAllowed));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_PASSWORD_FLOOD")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("FloodWait", R.string.FloodWait));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_NUMBER_FLOOD")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("PhoneNumberFlood", R.string.PhoneNumberFlood));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                    onBackPressed(true);
                    LoginActivity.this.setPage(0, true, null, true);
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("FloodWait", R.string.FloodWait));
                } else if (tLRPC$TL_error.code != -1000) {
                    AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_account_sendVerifyEmailCode, this.requestPhone);
                }
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivitySetupEmail$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivitySetupEmail.this.lambda$onShow$9();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$9() {
            this.inboxImageView.getAnimatedDrawable().setCurrentFrame(0, false);
            this.inboxImageView.playAnimation();
            this.emailField.requestFocus();
            AndroidUtilities.showKeyboard(this.emailField);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String obj = this.emailField.getText().toString();
            if (obj != null && obj.length() != 0) {
                bundle.putString("emailsetup_email", obj);
            }
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("emailsetup_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("emailsetup_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            String string = bundle.getString("emailsetup_email");
            if (string != null) {
                this.emailField.setText(string);
            }
        }
    }

    public class LoginActivityEmailCodeView extends SlideView {
        private FrameLayout cantAccessEmailFrameLayout;
        private TextView cantAccessEmailView;
        private CodeFieldContainer codeFieldContainer;
        private TextView confirmTextView;
        private Bundle currentParams;
        private String email;
        private String emailPhone;
        private TextView emailResetInView;
        private Runnable errorColorTimeout;
        private ViewSwitcher errorViewSwitcher;
        private GoogleSignInAccount googleAccount;
        private RLottieImageView inboxImageView;
        private boolean isFromSetup;
        private boolean isSetup;
        private int length;
        private LoginOrView loginOrView;
        private boolean nextPressed;
        private String phone;
        private String phoneHash;
        private boolean postedErrorColorTimeout;
        private String requestPhone;
        private boolean requestingEmailReset;
        private Runnable resendCodeTimeout;
        private TextView resendCodeView;
        private FrameLayout resendFrameLayout;
        private int resetAvailablePeriod;
        private int resetPendingDate;
        private boolean resetRequestPending;
        private TextView signInWithGoogleView;
        private TextView titleView;
        private Runnable updateResetPendingDateCallback;
        private TextView wrongCodeView;

        @Override // org.telegram.ui.Components.SlideView
        public boolean hasCustomKeyboard() {
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            boolean z = false;
            this.postedErrorColorTimeout = false;
            int i = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    break;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
            if (this.errorViewSwitcher.getCurrentView() != this.resendFrameLayout) {
                this.errorViewSwitcher.showNext();
                FrameLayout frameLayout = this.cantAccessEmailFrameLayout;
                if (this.resendCodeView.getVisibility() != 0 && LoginActivity.this.activityMode != 3 && !this.isSetup) {
                    z = true;
                }
                AndroidUtilities.updateViewVisibilityAnimated(frameLayout, z, 1.0f, true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            showResendCodeView(true);
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x00a2  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x011e  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x033a  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0352  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0121  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00a5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LoginActivityEmailCodeView(final android.content.Context r26, boolean r27) {
            /*
                Method dump skipped, instructions count: 917
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivityEmailCodeView.<init>(org.telegram.ui.LoginActivity, android.content.Context, boolean):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            NotificationCenter.getGlobalInstance().addObserver(new NotificationCenter.NotificationCenterDelegate() { // from class: org.telegram.ui.LoginActivity.LoginActivityEmailCodeView.3
                @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
                public void didReceivedNotification(int i, int i2, Object... objArr) {
                    int intValue = ((Integer) objArr[0]).intValue();
                    ((Integer) objArr[1]).intValue();
                    Intent intent = (Intent) objArr[2];
                    NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.onActivityResultReceived);
                    if (intValue == 200) {
                        try {
                            LoginActivityEmailCodeView.this.googleAccount = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
                            LoginActivityEmailCodeView.this.onNextPressed(null);
                        } catch (ApiException e) {
                            FileLog.e(e);
                        }
                    }
                }
            }, NotificationCenter.onActivityResultReceived);
            final GoogleSignInClient client = GoogleSignIn.getClient(getContext(), new GoogleSignInOptions.Builder().requestIdToken(BuildVars.GOOGLE_AUTH_CLIENT_ID).requestEmail().build());
            client.signOut().addOnCompleteListener(new OnCompleteListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda6
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public final void onComplete(Task task) {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$new$2(client, task);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(GoogleSignInClient googleSignInClient, Task task) {
            LoginActivity.this.getParentActivity().startActivityForResult(googleSignInClient.getSignInIntent(), 200);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$7(Context context, View view) {
            String string = this.currentParams.getString("emailPattern");
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
            int indexOf = string.indexOf(42);
            int lastIndexOf = string.lastIndexOf(42);
            if (indexOf != lastIndexOf && indexOf != -1 && lastIndexOf != -1) {
                TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                textStyleRun.flags |= 256;
                textStyleRun.start = indexOf;
                int i = lastIndexOf + 1;
                textStyleRun.end = i;
                spannableStringBuilder.setSpan(new TextStyleSpan(textStyleRun), indexOf, i, 0);
            }
            new AlertDialog.Builder(context).setTitle(LocaleController.getString(R.string.LoginEmailResetTitle)).setMessage(AndroidUtilities.formatSpannable(AndroidUtilities.replaceTags(LocaleController.getString(R.string.LoginEmailResetMessage)), spannableStringBuilder, getTimePattern(this.resetAvailablePeriod))).setPositiveButton(LocaleController.getString(R.string.LoginEmailResetButton), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$new$6(dialogInterface, i2);
                }
            }).setNegativeButton(LocaleController.getString(R.string.Cancel), null).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$6(DialogInterface dialogInterface, int i) {
            final Bundle bundle = new Bundle();
            bundle.putString("phone", this.phone);
            bundle.putString("ephone", this.emailPhone);
            bundle.putString("phoneFormated", this.requestPhone);
            final TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail = new TLRPC$TL_auth_resetLoginEmail();
            tLRPC$TL_auth_resetLoginEmail.phone_number = this.requestPhone;
            tLRPC$TL_auth_resetLoginEmail.phone_code_hash = this.phoneHash;
            LoginActivity.this.getConnectionsManager().sendRequest(tLRPC$TL_auth_resetLoginEmail, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda28
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$new$5(bundle, tLRPC$TL_auth_resetLoginEmail, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5(final Bundle bundle, final TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$new$4(tLObject, bundle, tLRPC$TL_error, tLRPC$TL_auth_resetLoginEmail);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(TLObject tLObject, Bundle bundle, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail) {
            String str;
            if (tLObject instanceof TLRPC$TL_auth_sentCode) {
                TLRPC$TL_auth_sentCode tLRPC$TL_auth_sentCode = (TLRPC$TL_auth_sentCode) tLObject;
                TLRPC$auth_SentCodeType tLRPC$auth_SentCodeType = tLRPC$TL_auth_sentCode.type;
                if (tLRPC$auth_SentCodeType instanceof TLRPC$TL_auth_sentCodeTypeEmailCode) {
                    tLRPC$auth_SentCodeType.email_pattern = this.currentParams.getString("emailPattern");
                    this.resetRequestPending = true;
                }
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, tLRPC$TL_auth_sentCode);
                return;
            }
            if (tLRPC$TL_error == null || (str = tLRPC$TL_error.text) == null) {
                return;
            }
            if (!str.contains("PHONE_CODE_EXPIRED")) {
                AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_auth_resetLoginEmail, new Object[0]);
                return;
            }
            onBackPressed(true);
            LoginActivity.this.setPage(0, true, null, true);
            LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("CodeExpired", R.string.CodeExpired));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$8(View view) {
            requestEmailReset();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$11(View view) {
            if (this.resendCodeView.getVisibility() == 0 && this.resendCodeView.getAlpha() == 1.0f) {
                showResendCodeView(false);
                final TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode = new TLRPC$TL_auth_resendCode();
                tLRPC$TL_auth_resendCode.phone_number = this.requestPhone;
                tLRPC$TL_auth_resendCode.phone_code_hash = this.phoneHash;
                final Bundle bundle = new Bundle();
                bundle.putString("phone", this.phone);
                bundle.putString("ephone", this.emailPhone);
                bundle.putString("phoneFormated", this.requestPhone);
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_resendCode, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda26
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        LoginActivity.LoginActivityEmailCodeView.this.lambda$new$10(bundle, tLRPC$TL_auth_resendCode, tLObject, tLRPC$TL_error);
                    }
                }, 10);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$10(final Bundle bundle, final TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$new$9(tLObject, bundle, tLRPC$TL_error, tLRPC$TL_auth_resendCode);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$9(TLObject tLObject, Bundle bundle, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_auth_resendCode tLRPC$TL_auth_resendCode) {
            if (tLObject instanceof TLRPC$TL_auth_sentCode) {
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, (TLRPC$TL_auth_sentCode) tLObject);
            } else {
                if (tLRPC$TL_error == null || tLRPC$TL_error.text == null) {
                    return;
                }
                AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_auth_resendCode, new Object[0]);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestEmailReset() {
            if (this.requestingEmailReset) {
                return;
            }
            this.requestingEmailReset = true;
            final Bundle bundle = new Bundle();
            bundle.putString("phone", this.phone);
            bundle.putString("ephone", this.emailPhone);
            bundle.putString("phoneFormated", this.requestPhone);
            final TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail = new TLRPC$TL_auth_resetLoginEmail();
            tLRPC$TL_auth_resetLoginEmail.phone_number = this.requestPhone;
            tLRPC$TL_auth_resetLoginEmail.phone_code_hash = this.phoneHash;
            LoginActivity.this.getConnectionsManager().sendRequest(tLRPC$TL_auth_resetLoginEmail, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda27
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$requestEmailReset$13(bundle, tLRPC$TL_auth_resetLoginEmail, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestEmailReset$13(final Bundle bundle, final TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$requestEmailReset$12(tLObject, bundle, tLRPC$TL_error, tLRPC$TL_auth_resetLoginEmail);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$requestEmailReset$12(TLObject tLObject, Bundle bundle, TLRPC$TL_error tLRPC$TL_error, TLRPC$TL_auth_resetLoginEmail tLRPC$TL_auth_resetLoginEmail) {
            String str;
            this.requestingEmailReset = false;
            if (tLObject instanceof TLRPC$TL_auth_sentCode) {
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, (TLRPC$TL_auth_sentCode) tLObject);
                return;
            }
            if (tLRPC$TL_error == null || (str = tLRPC$TL_error.text) == null) {
                return;
            }
            if (str.contains("TASK_ALREADY_EXISTS")) {
                new AlertDialog.Builder(getContext()).setTitle(LocaleController.getString(R.string.LoginEmailResetPremiumRequiredTitle)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(R.string.LoginEmailResetPremiumRequiredMessage, LocaleController.addNbsp(PhoneFormat.getInstance().format("+" + this.requestPhone))))).setPositiveButton(LocaleController.getString(R.string.OK), null).show();
                return;
            }
            if (!tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                AlertsCreator.processError(((BaseFragment) LoginActivity.this).currentAccount, tLRPC$TL_error, LoginActivity.this, tLRPC$TL_auth_resetLoginEmail, new Object[0]);
                return;
            }
            onBackPressed(true);
            LoginActivity.this.setPage(0, true, null, true);
            LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("CodeExpired", R.string.CodeExpired));
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            this.titleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            TextView textView = this.confirmTextView;
            int i = Theme.key_windowBackgroundWhiteGrayText6;
            textView.setTextColor(Theme.getColor(i));
            TextView textView2 = this.signInWithGoogleView;
            int i2 = Theme.key_windowBackgroundWhiteBlueText4;
            textView2.setTextColor(Theme.getColor(i2));
            this.loginOrView.updateColors();
            this.resendCodeView.setTextColor(Theme.getColor(i2));
            this.cantAccessEmailView.setTextColor(Theme.getColor(i2));
            this.emailResetInView.setTextColor(Theme.getColor(i));
            this.wrongCodeView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
            this.codeFieldContainer.invalidate();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            removeCallbacks(this.errorColorTimeout);
            removeCallbacks(this.resendCodeTimeout);
        }

        private void showResendCodeView(boolean z) {
            AndroidUtilities.updateViewVisibilityAnimated(this.resendCodeView, z);
            AndroidUtilities.updateViewVisibilityAnimated(this.cantAccessEmailFrameLayout, (z || LoginActivity.this.activityMode == 3 || this.isSetup) ? false : true);
            if (this.loginOrView.getVisibility() != 8) {
                this.loginOrView.setLayoutParams(LayoutHelper.createFrame(-1, 16.0f, 17, 0.0f, 0.0f, 0.0f, z ? 8.0f : 16.0f));
                this.loginOrView.requestLayout();
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString(R.string.VerificationCode);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            this.currentParams = bundle;
            this.requestPhone = bundle.getString("phoneFormated");
            this.phoneHash = this.currentParams.getString("phoneHash");
            this.phone = this.currentParams.getString("phone");
            this.emailPhone = this.currentParams.getString("ephone");
            this.isFromSetup = this.currentParams.getBoolean("setup");
            this.length = this.currentParams.getInt("length");
            this.email = this.currentParams.getString("email");
            this.resetAvailablePeriod = this.currentParams.getInt("resetAvailablePeriod");
            this.resetPendingDate = this.currentParams.getInt("resetPendingDate");
            int i = 8;
            if (LoginActivity.this.activityMode == 3) {
                this.confirmTextView.setText(LocaleController.formatString(R.string.CheckYourNewEmailSubtitle, this.email));
                AndroidUtilities.updateViewVisibilityAnimated(this.cantAccessEmailFrameLayout, false, 1.0f, false);
            } else if (this.isSetup) {
                this.confirmTextView.setText(LocaleController.formatString(R.string.VerificationCodeSubtitle, this.email));
                AndroidUtilities.updateViewVisibilityAnimated(this.cantAccessEmailFrameLayout, false, 1.0f, false);
            } else {
                AndroidUtilities.updateViewVisibilityAnimated(this.cantAccessEmailFrameLayout, true, 1.0f, false);
                this.cantAccessEmailView.setVisibility(this.resetPendingDate == 0 ? 0 : 8);
                this.emailResetInView.setVisibility(this.resetPendingDate != 0 ? 0 : 8);
                if (this.resetPendingDate != 0) {
                    updateResetPendingDate();
                }
            }
            this.codeFieldContainer.setNumbersCount(this.length, 1);
            for (CodeNumberField codeNumberField : this.codeFieldContainer.codeField) {
                codeNumberField.setShowSoftInputOnFocusCompat(!hasCustomKeyboard() || LoginActivity.this.isCustomKeyboardForceDisabled());
                codeNumberField.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.LoginActivity.LoginActivityEmailCodeView.7
                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    }

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                        if (LoginActivityEmailCodeView.this.postedErrorColorTimeout) {
                            LoginActivityEmailCodeView loginActivityEmailCodeView = LoginActivityEmailCodeView.this;
                            loginActivityEmailCodeView.removeCallbacks(loginActivityEmailCodeView.errorColorTimeout);
                            LoginActivityEmailCodeView.this.errorColorTimeout.run();
                        }
                    }
                });
                codeNumberField.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z2) {
                        LoginActivity.LoginActivityEmailCodeView.this.lambda$setParams$14(view, z2);
                    }
                });
            }
            this.codeFieldContainer.setText("");
            if (!this.isFromSetup && LoginActivity.this.activityMode != 3) {
                String string = this.currentParams.getString("emailPattern");
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
                int indexOf = string.indexOf(42);
                int lastIndexOf = string.lastIndexOf(42);
                if (indexOf != lastIndexOf && indexOf != -1 && lastIndexOf != -1) {
                    TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                    textStyleRun.flags |= 256;
                    textStyleRun.start = indexOf;
                    int i2 = lastIndexOf + 1;
                    textStyleRun.end = i2;
                    spannableStringBuilder.setSpan(new TextStyleSpan(textStyleRun), indexOf, i2, 0);
                }
                this.confirmTextView.setText(AndroidUtilities.formatSpannable(LocaleController.getString(R.string.CheckYourEmailSubtitle), spannableStringBuilder));
            }
            if (bundle.getBoolean("googleSignInAllowed") && PushListenerController.GooglePushListenerServiceProvider.INSTANCE.hasServices()) {
                i = 0;
            }
            this.loginOrView.setVisibility(i);
            this.signInWithGoogleView.setVisibility(i);
            LoginActivity.this.showKeyboard(this.codeFieldContainer.codeField[0]);
            this.codeFieldContainer.requestFocus();
            if (!z && bundle.containsKey("nextType")) {
                AndroidUtilities.runOnUIThread(this.resendCodeTimeout, bundle.getInt("timeout"));
            }
            if (this.resetPendingDate != 0) {
                AndroidUtilities.runOnUIThread(this.updateResetPendingDateCallback, 1000L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setParams$14(View view, boolean z) {
            if (z) {
                LoginActivity.this.keyboardView.setEditText((EditText) view);
                LoginActivity.this.keyboardView.setDispatchBackWhenEmpty(true);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onHide() {
            super.onHide();
            if (this.resetPendingDate != 0) {
                AndroidUtilities.cancelRunOnUIThread(this.updateResetPendingDateCallback);
            }
        }

        private String getTimePatternForTimer(int i) {
            int i2 = i / 86400;
            int i3 = i % 86400;
            int i4 = i3 / 3600;
            int i5 = i3 % 3600;
            int i6 = i5 / 60;
            int i7 = i5 % 60;
            if (i4 >= 16) {
                i2++;
            }
            if (i2 != 0) {
                return LocaleController.formatString(R.string.LoginEmailResetInSinglePattern, LocaleController.formatPluralString("Days", i2, new Object[0]));
            }
            StringBuilder sb = new StringBuilder();
            sb.append(i4 != 0 ? String.format(Locale.ROOT, "%02d:", Integer.valueOf(i4)) : "");
            Locale locale = Locale.ROOT;
            sb.append(String.format(locale, "%02d:", Integer.valueOf(i6)));
            sb.append(String.format(locale, "%02d", Integer.valueOf(i7)));
            return LocaleController.formatString(R.string.LoginEmailResetInSinglePattern, sb.toString());
        }

        private String getTimePattern(int i) {
            int i2 = i / 86400;
            int i3 = i % 86400;
            int i4 = i3 / 3600;
            int i5 = (i3 % 3600) / 60;
            if (i2 == 0 && i4 == 0) {
                i5 = Math.max(1, i5);
            }
            return (i2 == 0 || i4 == 0) ? (i4 == 0 || i5 == 0) ? i2 != 0 ? LocaleController.formatString(R.string.LoginEmailResetInSinglePattern, LocaleController.formatPluralString("Days", i2, new Object[0])) : i4 != 0 ? LocaleController.formatString(R.string.LoginEmailResetInSinglePattern, LocaleController.formatPluralString("Hours", i2, new Object[0])) : LocaleController.formatString(R.string.LoginEmailResetInSinglePattern, LocaleController.formatPluralString("Minutes", i5, new Object[0])) : LocaleController.formatString(R.string.LoginEmailResetInDoublePattern, LocaleController.formatPluralString("Hours", i4, new Object[0]), LocaleController.formatPluralString("Minutes", i5, new Object[0])) : LocaleController.formatString(R.string.LoginEmailResetInDoublePattern, LocaleController.formatPluralString("Days", i2, new Object[0]), LocaleController.formatPluralString("Hours", i4, new Object[0]));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateResetPendingDate() {
            int currentTimeMillis = (int) (this.resetPendingDate - (System.currentTimeMillis() / 1000));
            if (this.resetPendingDate <= 0 || currentTimeMillis <= 0) {
                this.emailResetInView.setVisibility(0);
                this.emailResetInView.setText(LocaleController.getString(R.string.LoginEmailResetPleaseWait));
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivityEmailCodeView.this.requestEmailReset();
                    }
                }, 1000L);
                return;
            }
            String formatString = LocaleController.formatString(R.string.LoginEmailResetInTime, getTimePatternForTimer(currentTimeMillis));
            SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(formatString);
            int indexOf = formatString.indexOf(42);
            int lastIndexOf = formatString.lastIndexOf(42);
            if (indexOf != lastIndexOf && indexOf != -1 && lastIndexOf != -1) {
                valueOf.replace(lastIndexOf, lastIndexOf + 1, (CharSequence) "");
                valueOf.replace(indexOf, indexOf + 1, (CharSequence) "");
                valueOf.setSpan(new ForegroundColorSpan(LoginActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastIndexOf - 1, 33);
            }
            this.emailResetInView.setText(valueOf);
            AndroidUtilities.runOnUIThread(this.updateResetPendingDateCallback, 1000L);
        }

        private void onPasscodeError(boolean z) {
            if (LoginActivity.this.getParentActivity() == null) {
                return;
            }
            try {
                this.codeFieldContainer.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (z) {
                for (CodeNumberField codeNumberField : this.codeFieldContainer.codeField) {
                    codeNumberField.setText("");
                }
            }
            for (CodeNumberField codeNumberField2 : this.codeFieldContainer.codeField) {
                codeNumberField2.animateErrorProgress(1.0f);
            }
            this.codeFieldContainer.codeField[0].requestFocus();
            AndroidUtilities.shakeViewSpring(this.codeFieldContainer, new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onPasscodeError$16();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPasscodeError$16() {
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onPasscodeError$15();
                }
            }, 150L);
            removeCallbacks(this.errorColorTimeout);
            postDelayed(this.errorColorTimeout, 3000L);
            this.postedErrorColorTimeout = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPasscodeError$15() {
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            int i = 0;
            codeFieldContainer.isFocusSuppressed = false;
            codeFieldContainer.codeField[0].requestFocus();
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    return;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn;
            if (this.nextPressed) {
                return;
            }
            AndroidUtilities.cancelRunOnUIThread(this.resendCodeTimeout);
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            codeFieldContainer.isFocusSuppressed = true;
            CodeNumberField[] codeNumberFieldArr = codeFieldContainer.codeField;
            if (codeNumberFieldArr != null) {
                for (CodeNumberField codeNumberField : codeNumberFieldArr) {
                    codeNumberField.animateFocusedProgress(0.0f);
                }
            }
            final String code = this.codeFieldContainer.getCode();
            if (code.length() == 0 && this.googleAccount == null) {
                onPasscodeError(false);
                return;
            }
            this.nextPressed = true;
            LoginActivity.this.needShowProgress(0);
            if (LoginActivity.this.activityMode == 3) {
                TLRPC$TL_account_verifyEmail tLRPC$TL_account_verifyEmail = new TLRPC$TL_account_verifyEmail();
                tLRPC$TL_account_verifyEmail.purpose = new TLRPC$TL_emailVerifyPurposeLoginChange();
                TLRPC$TL_emailVerificationCode tLRPC$TL_emailVerificationCode = new TLRPC$TL_emailVerificationCode();
                tLRPC$TL_emailVerificationCode.code = code;
                tLRPC$TL_account_verifyEmail.verification = tLRPC$TL_emailVerificationCode;
                tLRPC$TL_auth_signIn = tLRPC$TL_account_verifyEmail;
            } else if (this.isFromSetup) {
                TLRPC$TL_account_verifyEmail tLRPC$TL_account_verifyEmail2 = new TLRPC$TL_account_verifyEmail();
                TLRPC$TL_emailVerifyPurposeLoginSetup tLRPC$TL_emailVerifyPurposeLoginSetup = new TLRPC$TL_emailVerifyPurposeLoginSetup();
                tLRPC$TL_emailVerifyPurposeLoginSetup.phone_number = this.requestPhone;
                tLRPC$TL_emailVerifyPurposeLoginSetup.phone_code_hash = this.phoneHash;
                tLRPC$TL_account_verifyEmail2.purpose = tLRPC$TL_emailVerifyPurposeLoginSetup;
                TLRPC$TL_emailVerificationCode tLRPC$TL_emailVerificationCode2 = new TLRPC$TL_emailVerificationCode();
                tLRPC$TL_emailVerificationCode2.code = code;
                tLRPC$TL_account_verifyEmail2.verification = tLRPC$TL_emailVerificationCode2;
                tLRPC$TL_auth_signIn = tLRPC$TL_account_verifyEmail2;
            } else {
                TLRPC$TL_auth_signIn tLRPC$TL_auth_signIn2 = new TLRPC$TL_auth_signIn();
                tLRPC$TL_auth_signIn2.phone_number = this.requestPhone;
                tLRPC$TL_auth_signIn2.phone_code_hash = this.phoneHash;
                if (this.googleAccount != null) {
                    TLRPC$TL_emailVerificationGoogle tLRPC$TL_emailVerificationGoogle = new TLRPC$TL_emailVerificationGoogle();
                    tLRPC$TL_emailVerificationGoogle.token = this.googleAccount.getIdToken();
                    tLRPC$TL_auth_signIn2.email_verification = tLRPC$TL_emailVerificationGoogle;
                } else {
                    TLRPC$TL_emailVerificationCode tLRPC$TL_emailVerificationCode3 = new TLRPC$TL_emailVerificationCode();
                    tLRPC$TL_emailVerificationCode3.code = code;
                    tLRPC$TL_auth_signIn2.email_verification = tLRPC$TL_emailVerificationCode3;
                }
                tLRPC$TL_auth_signIn2.flags |= 2;
                tLRPC$TL_auth_signIn = tLRPC$TL_auth_signIn2;
            }
            CodeFieldContainer codeFieldContainer2 = this.codeFieldContainer;
            codeFieldContainer2.isFocusSuppressed = true;
            CodeNumberField[] codeNumberFieldArr2 = codeFieldContainer2.codeField;
            if (codeNumberFieldArr2 != null) {
                for (CodeNumberField codeNumberField2 : codeNumberFieldArr2) {
                    codeNumberField2.animateFocusedProgress(0.0f);
                }
            }
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_signIn, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda30
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onNextPressed$23(code, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$23(final String str, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onNextPressed$22(tLRPC$TL_error, str, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:21:0x017c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ void lambda$onNextPressed$22(org.telegram.tgnet.TLRPC$TL_error r6, final java.lang.String r7, final org.telegram.tgnet.TLObject r8) {
            /*
                Method dump skipped, instructions count: 416
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivityEmailCodeView.lambda$onNextPressed$22(org.telegram.tgnet.TLRPC$TL_error, java.lang.String, org.telegram.tgnet.TLObject):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$17(Bundle bundle) {
            LoginActivity.this.setPage(5, true, bundle, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$18(TLObject tLObject, Bundle bundle) {
            if ((tLObject instanceof TLRPC$TL_account_emailVerified) && LoginActivity.this.activityMode == 3) {
                LoginActivity.this.finishFragment();
                LoginActivity.this.emailChangeFinishCallback.run();
            } else if (tLObject instanceof TLRPC$TL_account_emailVerifiedLogin) {
                LoginActivity.this.lambda$resendCodeFromSafetyNet$19(bundle, ((TLRPC$TL_account_emailVerifiedLogin) tLObject).sent_code);
            } else if (tLObject instanceof TLRPC$TL_auth_authorization) {
                LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$21(final String str, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onNextPressed$20(tLRPC$TL_error, tLObject, str);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$20(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, String str) {
            this.nextPressed = false;
            LoginActivity.this.showDoneButton(false, true);
            if (tLRPC$TL_error != null) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                return;
            }
            TLRPC$account_Password tLRPC$account_Password = (TLRPC$account_Password) tLObject;
            if (!TwoStepVerificationActivity.canHandleCurrentPassword(tLRPC$account_Password, true)) {
                AlertsCreator.showUpdateAppAlert(LoginActivity.this.getParentActivity(), LocaleController.getString("UpdateAppAlert", R.string.UpdateAppAlert), true);
                return;
            }
            final Bundle bundle = new Bundle();
            SerializedData serializedData = new SerializedData(tLRPC$account_Password.getObjectSize());
            tLRPC$account_Password.serializeToStream(serializedData);
            bundle.putString("password", Utilities.bytesToHex(serializedData.toByteArray()));
            bundle.putString("phoneFormated", this.requestPhone);
            bundle.putString("phoneHash", this.phoneHash);
            bundle.putString("code", str);
            animateSuccess(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$onNextPressed$19(bundle);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$19(Bundle bundle) {
            LoginActivity.this.setPage(6, true, bundle, false);
        }

        private void animateSuccess(final Runnable runnable) {
            if (this.googleAccount != null) {
                runnable.run();
                return;
            }
            final int i = 0;
            while (true) {
                CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
                if (i < codeFieldContainer.codeField.length) {
                    codeFieldContainer.postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda16
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.LoginActivityEmailCodeView.this.lambda$animateSuccess$24(i);
                        }
                    }, i * 75);
                    i++;
                } else {
                    codeFieldContainer.postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda19
                        @Override // java.lang.Runnable
                        public final void run() {
                            LoginActivity.LoginActivityEmailCodeView.this.lambda$animateSuccess$25(runnable);
                        }
                    }, (this.codeFieldContainer.codeField.length * 75) + 400);
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$animateSuccess$24(int i) {
            this.codeFieldContainer.codeField[i].animateSuccessProgress(1.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$animateSuccess$25(Runnable runnable) {
            int i = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i < codeNumberFieldArr.length) {
                    codeNumberFieldArr[i].animateSuccessProgress(0.0f);
                    i++;
                } else {
                    runnable.run();
                    this.codeFieldContainer.isFocusSuppressed = false;
                    return;
                }
            }
        }

        private void shakeWrongCode() {
            try {
                this.codeFieldContainer.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            int i = 0;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    break;
                }
                codeNumberFieldArr[i].setText("");
                this.codeFieldContainer.codeField[i].animateErrorProgress(1.0f);
                i++;
            }
            if (this.errorViewSwitcher.getCurrentView() == this.resendFrameLayout) {
                this.errorViewSwitcher.showNext();
                AndroidUtilities.updateViewVisibilityAnimated(this.cantAccessEmailFrameLayout, false, 1.0f, true);
            }
            this.codeFieldContainer.codeField[0].requestFocus();
            AndroidUtilities.shakeViewSpring(this.codeFieldContainer, 10.0f, new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$shakeWrongCode$27();
                }
            });
            removeCallbacks(this.errorColorTimeout);
            postDelayed(this.errorColorTimeout, 5000L);
            this.postedErrorColorTimeout = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$shakeWrongCode$27() {
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityEmailCodeView.this.lambda$shakeWrongCode$26();
                }
            }, 150L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$shakeWrongCode$26() {
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            int i = 0;
            codeFieldContainer.isFocusSuppressed = false;
            codeFieldContainer.codeField[0].requestFocus();
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    return;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            if (this.resetRequestPending) {
                this.resetRequestPending = false;
            } else {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityEmailCodeView$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivityEmailCodeView.this.lambda$onShow$28();
                    }
                }, LoginActivity.SHOW_DELAY);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$28() {
            this.inboxImageView.getAnimatedDrawable().setCurrentFrame(0, false);
            this.inboxImageView.playAnimation();
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            if (codeFieldContainer == null || codeFieldContainer.codeField == null) {
                return;
            }
            codeFieldContainer.setText("");
            this.codeFieldContainer.codeField[0].requestFocus();
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String code = this.codeFieldContainer.getCode();
            if (code != null && code.length() != 0) {
                bundle.putString("emailcode_code", code);
            }
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("emailcode_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("emailcode_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            String string = bundle.getString("emailcode_code");
            if (string != null) {
                this.codeFieldContainer.setText(string);
            }
        }
    }

    public class LoginActivityRecoverView extends SlideView {
        private CodeFieldContainer codeFieldContainer;
        private TextView confirmTextView;
        private Bundle currentParams;
        private Runnable errorColorTimeout;
        private RLottieImageView inboxImageView;
        private boolean nextPressed;
        private String passwordString;
        private String phoneCode;
        private String phoneHash;
        private boolean postedErrorColorTimeout;
        private String requestPhone;
        private TextView titleView;
        private TextView troubleButton;

        @Override // org.telegram.ui.Components.SlideView
        public boolean hasCustomKeyboard() {
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            int i = 0;
            this.postedErrorColorTimeout = false;
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    return;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x00f5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public LoginActivityRecoverView(android.content.Context r20) {
            /*
                Method dump skipped, instructions count: 407
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.LoginActivity.LoginActivityRecoverView.<init>(org.telegram.ui.LoginActivity, android.content.Context):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view, boolean z) {
            if (z) {
                LoginActivity.this.keyboardView.setEditText((EditText) view);
                LoginActivity.this.keyboardView.setDispatchBackWhenEmpty(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4(View view) {
            Dialog showDialog = LoginActivity.this.showDialog(new AlertDialog.Builder(LoginActivity.this.getParentActivity()).setTitle(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle)).setMessage(LocaleController.getString("RestoreEmailTroubleText", R.string.RestoreEmailTroubleText)).setPositiveButton(LocaleController.getString(R.string.OK), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivityRecoverView.this.lambda$new$2(dialogInterface, i);
                }
            }).setNegativeButton(LocaleController.getString(R.string.ResetAccount), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivityRecoverView.this.lambda$new$3(dialogInterface, i);
                }
            }).create());
            if (showDialog != null) {
                showDialog.setCanceledOnTouchOutside(false);
                showDialog.setCancelable(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$2(DialogInterface dialogInterface, int i) {
            LoginActivity.this.setPage(6, true, new Bundle(), true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(DialogInterface dialogInterface, int i) {
            LoginActivity.this.tryResetAccount(this.requestPhone, this.phoneHash, this.phoneCode);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            this.titleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            this.troubleButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.codeFieldContainer.invalidate();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            removeCallbacks(this.errorColorTimeout);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onCancelPressed() {
            this.nextPressed = false;
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("LoginPassword", R.string.LoginPassword);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            this.codeFieldContainer.setText("");
            this.currentParams = bundle;
            this.passwordString = bundle.getString("password");
            this.requestPhone = this.currentParams.getString("requestPhone");
            this.phoneHash = this.currentParams.getString("phoneHash");
            this.phoneCode = this.currentParams.getString("phoneCode");
            String string = this.currentParams.getString("email_unconfirmed_pattern");
            SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(string);
            int indexOf = string.indexOf(42);
            int lastIndexOf = string.lastIndexOf(42);
            if (indexOf != lastIndexOf && indexOf != -1 && lastIndexOf != -1) {
                TextStyleSpan.TextStyleRun textStyleRun = new TextStyleSpan.TextStyleRun();
                textStyleRun.flags |= 256;
                textStyleRun.start = indexOf;
                int i = lastIndexOf + 1;
                textStyleRun.end = i;
                valueOf.setSpan(new TextStyleSpan(textStyleRun), indexOf, i, 0);
            }
            this.troubleButton.setText(AndroidUtilities.formatSpannable(LocaleController.getString(R.string.RestoreEmailNoAccess), valueOf));
            LoginActivity.this.showKeyboard(this.codeFieldContainer);
            this.codeFieldContainer.requestFocus();
        }

        private void onPasscodeError(boolean z) {
            if (LoginActivity.this.getParentActivity() == null) {
                return;
            }
            try {
                this.codeFieldContainer.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (z) {
                for (CodeNumberField codeNumberField : this.codeFieldContainer.codeField) {
                    codeNumberField.setText("");
                }
            }
            for (CodeNumberField codeNumberField2 : this.codeFieldContainer.codeField) {
                codeNumberField2.animateErrorProgress(1.0f);
            }
            this.codeFieldContainer.codeField[0].requestFocus();
            AndroidUtilities.shakeViewSpring(this.codeFieldContainer, new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRecoverView.this.lambda$onPasscodeError$6();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPasscodeError$6() {
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRecoverView.this.lambda$onPasscodeError$5();
                }
            }, 150L);
            removeCallbacks(this.errorColorTimeout);
            postDelayed(this.errorColorTimeout, 3000L);
            this.postedErrorColorTimeout = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPasscodeError$5() {
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            int i = 0;
            codeFieldContainer.isFocusSuppressed = false;
            codeFieldContainer.codeField[0].requestFocus();
            while (true) {
                CodeNumberField[] codeNumberFieldArr = this.codeFieldContainer.codeField;
                if (i >= codeNumberFieldArr.length) {
                    return;
                }
                codeNumberFieldArr[i].animateErrorProgress(0.0f);
                i++;
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (this.nextPressed) {
                return;
            }
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            codeFieldContainer.isFocusSuppressed = true;
            for (CodeNumberField codeNumberField : codeFieldContainer.codeField) {
                codeNumberField.animateFocusedProgress(0.0f);
            }
            final String code = this.codeFieldContainer.getCode();
            if (code.length() == 0) {
                onPasscodeError(false);
                return;
            }
            this.nextPressed = true;
            LoginActivity.this.needShowProgress(0);
            TLRPC$TL_auth_checkRecoveryPassword tLRPC$TL_auth_checkRecoveryPassword = new TLRPC$TL_auth_checkRecoveryPassword();
            tLRPC$TL_auth_checkRecoveryPassword.code = code;
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_checkRecoveryPassword, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda9
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityRecoverView.this.lambda$onNextPressed$8(code, tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$8(final String str, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRecoverView.this.lambda$onNextPressed$7(tLObject, str, tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onNextPressed$7(TLObject tLObject, String str, TLRPC$TL_error tLRPC$TL_error) {
            String formatPluralString;
            LoginActivity.this.needHideProgress(false);
            this.nextPressed = false;
            if (tLObject instanceof TLRPC$TL_boolTrue) {
                Bundle bundle = new Bundle();
                bundle.putString("emailCode", str);
                bundle.putString("password", this.passwordString);
                LoginActivity.this.setPage(9, true, bundle, false);
                return;
            }
            if (tLRPC$TL_error == null || tLRPC$TL_error.text.startsWith("CODE_INVALID")) {
                onPasscodeError(true);
                return;
            }
            if (!tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                return;
            }
            int intValue = Utilities.parseInt((CharSequence) tLRPC$TL_error.text).intValue();
            if (intValue < 60) {
                formatPluralString = LocaleController.formatPluralString("Seconds", intValue, new Object[0]);
            } else {
                formatPluralString = LocaleController.formatPluralString("Minutes", intValue / 60, new Object[0]);
            }
            LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, formatPluralString));
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            LoginActivity.this.needHideProgress(true);
            this.currentParams = null;
            this.nextPressed = false;
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRecoverView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRecoverView.this.lambda$onShow$9();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$9() {
            this.inboxImageView.getAnimatedDrawable().setCurrentFrame(0, false);
            this.inboxImageView.playAnimation();
            CodeFieldContainer codeFieldContainer = this.codeFieldContainer;
            if (codeFieldContainer != null) {
                codeFieldContainer.codeField[0].requestFocus();
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String code = this.codeFieldContainer.getCode();
            if (code != null && code.length() != 0) {
                bundle.putString("recoveryview_code", code);
            }
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("recoveryview_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("recoveryview_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            String string = bundle.getString("recoveryview_code");
            if (string != null) {
                this.codeFieldContainer.setText(string);
            }
        }
    }

    public class LoginActivityNewPasswordView extends SlideView {
        private TextView cancelButton;
        private EditTextBoldCursor[] codeField;
        private TextView confirmTextView;
        private Bundle currentParams;
        private TLRPC$account_Password currentPassword;
        private int currentStage;
        private String emailCode;
        private boolean isPasswordVisible;
        private String newPassword;
        private boolean nextPressed;
        private OutlineTextContainerView[] outlineFields;
        private ImageView passwordButton;
        private String passwordString;
        private TextView titleTextView;

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        public LoginActivityNewPasswordView(Context context, int i) {
            super(context);
            int i2;
            this.currentStage = i;
            setOrientation(1);
            EditTextBoldCursor[] editTextBoldCursorArr = new EditTextBoldCursor[i == 1 ? 1 : 2];
            this.codeField = editTextBoldCursorArr;
            this.outlineFields = new OutlineTextContainerView[editTextBoldCursorArr.length];
            TextView textView = new TextView(context);
            this.titleTextView = textView;
            float f = 18.0f;
            textView.setTextSize(1, 18.0f);
            this.titleTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.titleTextView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.titleTextView.setGravity(49);
            this.titleTextView.setText(LocaleController.getString(R.string.SetNewPassword));
            addView(this.titleTextView, LayoutHelper.createLinear(-2, -2, 1, 8, AndroidUtilities.isSmallScreen() ? 16 : 72, 8, 0));
            TextView textView2 = new TextView(context);
            this.confirmTextView = textView2;
            textView2.setTextSize(1, 16.0f);
            this.confirmTextView.setGravity(1);
            this.confirmTextView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.confirmTextView, LayoutHelper.createLinear(-2, -2, 1, 8, 6, 8, 16));
            final int i3 = 0;
            while (i3 < this.codeField.length) {
                final OutlineTextContainerView outlineTextContainerView = new OutlineTextContainerView(context);
                this.outlineFields[i3] = outlineTextContainerView;
                if (i == 0) {
                    i2 = i3 == 0 ? R.string.PleaseEnterNewFirstPasswordHint : R.string.PleaseEnterNewSecondPasswordHint;
                } else {
                    i2 = R.string.PasswordHintPlaceholder;
                }
                outlineTextContainerView.setText(LocaleController.getString(i2));
                this.codeField[i3] = new EditTextBoldCursor(context);
                this.codeField[i3].setCursorSize(AndroidUtilities.dp(20.0f));
                this.codeField[i3].setCursorWidth(1.5f);
                this.codeField[i3].setImeOptions(268435461);
                this.codeField[i3].setTextSize(1, f);
                this.codeField[i3].setMaxLines(1);
                this.codeField[i3].setBackground(null);
                int dp = AndroidUtilities.dp(16.0f);
                this.codeField[i3].setPadding(dp, dp, dp, dp);
                if (i == 0) {
                    this.codeField[i3].setInputType(129);
                    this.codeField[i3].setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                this.codeField[i3].setTypeface(Typeface.DEFAULT);
                this.codeField[i3].setGravity(LocaleController.isRTL ? 5 : 3);
                EditTextBoldCursor editTextBoldCursor = this.codeField[i3];
                boolean z = i3 == 0 && i == 0;
                editTextBoldCursor.addTextChangedListener(new TextWatcher(LoginActivity.this, z) { // from class: org.telegram.ui.LoginActivity.LoginActivityNewPasswordView.1
                    final /* synthetic */ boolean val$showPasswordButton;

                    @Override // android.text.TextWatcher
                    public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                    }

                    @Override // android.text.TextWatcher
                    public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
                    }

                    {
                        this.val$showPasswordButton = z;
                    }

                    @Override // android.text.TextWatcher
                    public void afterTextChanged(Editable editable) {
                        if (this.val$showPasswordButton) {
                            if (LoginActivityNewPasswordView.this.passwordButton.getVisibility() == 0 || TextUtils.isEmpty(editable)) {
                                if (LoginActivityNewPasswordView.this.passwordButton.getVisibility() == 8 || !TextUtils.isEmpty(editable)) {
                                    return;
                                }
                                AndroidUtilities.updateViewVisibilityAnimated(LoginActivityNewPasswordView.this.passwordButton, false, 0.1f, true);
                                return;
                            }
                            if (LoginActivityNewPasswordView.this.isPasswordVisible) {
                                LoginActivityNewPasswordView.this.passwordButton.callOnClick();
                            }
                            AndroidUtilities.updateViewVisibilityAnimated(LoginActivityNewPasswordView.this.passwordButton, true, 0.1f, true);
                        }
                    }
                });
                this.codeField[i3].setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z2) {
                        LoginActivity.LoginActivityNewPasswordView.lambda$new$0(OutlineTextContainerView.this, view, z2);
                    }
                });
                if (z) {
                    LinearLayout linearLayout = new LinearLayout(context);
                    linearLayout.setOrientation(0);
                    linearLayout.setGravity(16);
                    linearLayout.addView(this.codeField[i3], LayoutHelper.createLinear(0, -2, 1.0f));
                    ImageView imageView = new ImageView(context);
                    this.passwordButton = imageView;
                    imageView.setImageResource(R.drawable.msg_message);
                    AndroidUtilities.updateViewVisibilityAnimated(this.passwordButton, true, 0.1f, false);
                    this.passwordButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            LoginActivity.LoginActivityNewPasswordView.this.lambda$new$1(view);
                        }
                    });
                    linearLayout.addView(this.passwordButton, LayoutHelper.createLinearRelatively(24.0f, 24.0f, 0, 0.0f, 0.0f, 14.0f, 0.0f));
                    outlineTextContainerView.addView(linearLayout, LayoutHelper.createFrame(-1, -2.0f));
                } else {
                    outlineTextContainerView.addView(this.codeField[i3], LayoutHelper.createFrame(-1, -2.0f));
                }
                outlineTextContainerView.attachEditText(this.codeField[i3]);
                addView(outlineTextContainerView, LayoutHelper.createLinear(-1, -2, 1, 16, 16, 16, 0));
                this.codeField[i3].setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda4
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(TextView textView3, int i4, KeyEvent keyEvent) {
                        boolean lambda$new$2;
                        lambda$new$2 = LoginActivity.LoginActivityNewPasswordView.this.lambda$new$2(i3, textView3, i4, keyEvent);
                        return lambda$new$2;
                    }
                });
                i3++;
                f = 18.0f;
            }
            if (i == 0) {
                this.confirmTextView.setText(LocaleController.getString("PleaseEnterNewFirstPasswordLogin", R.string.PleaseEnterNewFirstPasswordLogin));
            } else {
                this.confirmTextView.setText(LocaleController.getString("PasswordHintTextLogin", R.string.PasswordHintTextLogin));
            }
            TextView textView3 = new TextView(context);
            this.cancelButton = textView3;
            textView3.setGravity(19);
            this.cancelButton.setTextSize(1, 15.0f);
            this.cancelButton.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.cancelButton.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), 0);
            this.cancelButton.setText(LocaleController.getString(R.string.YourEmailSkip));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.addView(this.cancelButton, LayoutHelper.createFrame(-1, Build.VERSION.SDK_INT >= 21 ? 56 : 60, 80, 0.0f, 0.0f, 0.0f, 32.0f));
            addView(frameLayout, LayoutHelper.createLinear(-1, -1, 80));
            VerticalPositionAutoAnimator.attach(this.cancelButton);
            this.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$new$3(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$new$0(OutlineTextContainerView outlineTextContainerView, View view, boolean z) {
            outlineTextContainerView.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            this.isPasswordVisible = !this.isPasswordVisible;
            int i = 0;
            while (true) {
                EditTextBoldCursor[] editTextBoldCursorArr = this.codeField;
                if (i >= editTextBoldCursorArr.length) {
                    break;
                }
                int selectionStart = editTextBoldCursorArr[i].getSelectionStart();
                int selectionEnd = this.codeField[i].getSelectionEnd();
                this.codeField[i].setInputType((this.isPasswordVisible ? 144 : 128) | 1);
                this.codeField[i].setSelection(selectionStart, selectionEnd);
                i++;
            }
            this.passwordButton.setTag(Boolean.valueOf(this.isPasswordVisible));
            this.passwordButton.setColorFilter(Theme.getColor(this.isPasswordVisible ? Theme.key_windowBackgroundWhiteInputFieldActivated : Theme.key_windowBackgroundWhiteHintText));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$2(int i, TextView textView, int i2, KeyEvent keyEvent) {
            if (i == 0) {
                EditTextBoldCursor[] editTextBoldCursorArr = this.codeField;
                if (editTextBoldCursorArr.length == 2) {
                    editTextBoldCursorArr[1].requestFocus();
                    return true;
                }
            }
            if (i2 != 5) {
                return false;
            }
            onNextPressed(null);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$3(View view) {
            if (this.currentStage == 0) {
                recoverPassword(null, null);
            } else {
                recoverPassword(this.newPassword, null);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.confirmTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            for (EditTextBoldCursor editTextBoldCursor : this.codeField) {
                editTextBoldCursor.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                editTextBoldCursor.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated));
            }
            for (OutlineTextContainerView outlineTextContainerView : this.outlineFields) {
                outlineTextContainerView.updateColor();
            }
            this.cancelButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            ImageView imageView = this.passwordButton;
            if (imageView != null) {
                imageView.setColorFilter(Theme.getColor(this.isPasswordVisible ? Theme.key_windowBackgroundWhiteInputFieldActivated : Theme.key_windowBackgroundWhiteHintText));
                this.passwordButton.setBackground(Theme.createSelectorDrawable(LoginActivity.this.getThemedColor(Theme.key_listSelector), 1));
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onCancelPressed() {
            this.nextPressed = false;
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("NewPassword", R.string.NewPassword);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            int i = 0;
            while (true) {
                EditTextBoldCursor[] editTextBoldCursorArr = this.codeField;
                if (i >= editTextBoldCursorArr.length) {
                    break;
                }
                editTextBoldCursorArr[i].setText("");
                i++;
            }
            this.currentParams = bundle;
            this.emailCode = bundle.getString("emailCode");
            String string = this.currentParams.getString("password");
            this.passwordString = string;
            if (string != null) {
                SerializedData serializedData = new SerializedData(Utilities.hexToBytes(string));
                TLRPC$account_Password TLdeserialize = TLRPC$account_Password.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                this.currentPassword = TLdeserialize;
                TwoStepVerificationActivity.initPasswordNewAlgo(TLdeserialize);
            }
            this.newPassword = this.currentParams.getString("new_password");
            LoginActivity.this.showKeyboard(this.codeField[0]);
            this.codeField[0].requestFocus();
        }

        private void onPasscodeError(boolean z, int i) {
            if (LoginActivity.this.getParentActivity() == null) {
                return;
            }
            try {
                this.codeField[i].performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            AndroidUtilities.shakeView(this.codeField[i]);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (this.nextPressed) {
                return;
            }
            String obj = this.codeField[0].getText().toString();
            if (obj.length() == 0) {
                onPasscodeError(false, 0);
                return;
            }
            if (this.currentStage == 0) {
                if (!obj.equals(this.codeField[1].getText().toString())) {
                    onPasscodeError(false, 1);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("emailCode", this.emailCode);
                bundle.putString("new_password", obj);
                bundle.putString("password", this.passwordString);
                LoginActivity.this.setPage(10, true, bundle, false);
                return;
            }
            this.nextPressed = true;
            LoginActivity.this.needShowProgress(0);
            recoverPassword(this.newPassword, obj);
        }

        private void recoverPassword(final String str, final String str2) {
            final TLRPC$TL_auth_recoverPassword tLRPC$TL_auth_recoverPassword = new TLRPC$TL_auth_recoverPassword();
            tLRPC$TL_auth_recoverPassword.code = this.emailCode;
            if (!TextUtils.isEmpty(str)) {
                tLRPC$TL_auth_recoverPassword.flags |= 1;
                TLRPC$TL_account_passwordInputSettings tLRPC$TL_account_passwordInputSettings = new TLRPC$TL_account_passwordInputSettings();
                tLRPC$TL_auth_recoverPassword.new_settings = tLRPC$TL_account_passwordInputSettings;
                tLRPC$TL_account_passwordInputSettings.flags |= 1;
                tLRPC$TL_account_passwordInputSettings.hint = str2 != null ? str2 : "";
                tLRPC$TL_account_passwordInputSettings.new_algo = this.currentPassword.new_algo;
            }
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$9(str, str2, tLRPC$TL_auth_recoverPassword);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$9(final String str, final String str2, TLRPC$TL_auth_recoverPassword tLRPC$TL_auth_recoverPassword) {
            byte[] stringBytes = str != null ? AndroidUtilities.getStringBytes(str) : null;
            RequestDelegate requestDelegate = new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda9
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$8(str, str2, tLObject, tLRPC$TL_error);
                }
            };
            TLRPC$PasswordKdfAlgo tLRPC$PasswordKdfAlgo = this.currentPassword.new_algo;
            if (tLRPC$PasswordKdfAlgo instanceof TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow) {
                if (str != null) {
                    tLRPC$TL_auth_recoverPassword.new_settings.new_password_hash = SRPHelper.getVBytes(stringBytes, (TLRPC$TL_passwordKdfAlgoSHA256SHA256PBKDF2HMACSHA512iter100000SHA256ModPow) tLRPC$PasswordKdfAlgo);
                    if (tLRPC$TL_auth_recoverPassword.new_settings.new_password_hash == null) {
                        TLRPC$TL_error tLRPC$TL_error = new TLRPC$TL_error();
                        tLRPC$TL_error.text = "ALGO_INVALID";
                        requestDelegate.run(null, tLRPC$TL_error);
                    }
                }
                ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$TL_auth_recoverPassword, requestDelegate, 10);
                return;
            }
            TLRPC$TL_error tLRPC$TL_error2 = new TLRPC$TL_error();
            tLRPC$TL_error2.text = "PASSWORD_HASH_INVALID";
            requestDelegate.run(null, tLRPC$TL_error2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$8(final String str, final String str2, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$7(tLRPC$TL_error, str, str2, tLObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$7(TLRPC$TL_error tLRPC$TL_error, final String str, final String str2, final TLObject tLObject) {
            String formatPluralString;
            if (tLRPC$TL_error == null || (!"SRP_ID_INVALID".equals(tLRPC$TL_error.text) && !"NEW_SALT_INVALID".equals(tLRPC$TL_error.text))) {
                LoginActivity.this.needHideProgress(false);
                if (tLObject instanceof TLRPC$auth_Authorization) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getParentActivity());
                    builder.setPositiveButton(LocaleController.getString(R.string.Continue), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$6(tLObject, dialogInterface, i);
                        }
                    });
                    if (TextUtils.isEmpty(str)) {
                        builder.setMessage(LocaleController.getString(R.string.YourPasswordReset));
                    } else {
                        builder.setMessage(LocaleController.getString(R.string.YourPasswordChangedSuccessText));
                    }
                    builder.setTitle(LocaleController.getString(R.string.TwoStepVerificationTitle));
                    Dialog showDialog = LoginActivity.this.showDialog(builder.create());
                    if (showDialog != null) {
                        showDialog.setCanceledOnTouchOutside(false);
                        showDialog.setCancelable(false);
                        return;
                    }
                    return;
                }
                if (tLRPC$TL_error != null) {
                    this.nextPressed = false;
                    if (!tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                        LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                        return;
                    }
                    int intValue = Utilities.parseInt((CharSequence) tLRPC$TL_error.text).intValue();
                    if (intValue < 60) {
                        formatPluralString = LocaleController.formatPluralString("Seconds", intValue, new Object[0]);
                    } else {
                        formatPluralString = LocaleController.formatPluralString("Minutes", intValue / 60, new Object[0]);
                    }
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, formatPluralString));
                    return;
                }
                return;
            }
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(new TLRPC$TL_account_getPassword(), new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda10
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC$TL_error tLRPC$TL_error2) {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$5(str, str2, tLObject2, tLRPC$TL_error2);
                }
            }, 8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$5(final String str, final String str2, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$recoverPassword$4(tLRPC$TL_error, tLObject, str, str2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$4(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, String str, String str2) {
            if (tLRPC$TL_error == null) {
                TLRPC$account_Password tLRPC$account_Password = (TLRPC$account_Password) tLObject;
                this.currentPassword = tLRPC$account_Password;
                TwoStepVerificationActivity.initPasswordNewAlgo(tLRPC$account_Password);
                recoverPassword(str, str2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$recoverPassword$6(TLObject tLObject, DialogInterface dialogInterface, int i) {
            LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            LoginActivity.this.needHideProgress(true);
            this.currentParams = null;
            this.nextPressed = false;
            return true;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityNewPasswordView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityNewPasswordView.this.lambda$onShow$10();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$10() {
            EditTextBoldCursor[] editTextBoldCursorArr = this.codeField;
            if (editTextBoldCursorArr != null) {
                editTextBoldCursorArr[0].requestFocus();
                EditTextBoldCursor[] editTextBoldCursorArr2 = this.codeField;
                editTextBoldCursorArr2[0].setSelection(editTextBoldCursorArr2[0].length());
                AndroidUtilities.showKeyboard(this.codeField[0]);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            if (this.currentParams != null) {
                bundle.putBundle("recoveryview_params" + this.currentStage, this.currentParams);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle("recoveryview_params" + this.currentStage);
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
        }
    }

    public class LoginActivityRegisterView extends SlideView implements ImageUpdater.ImageUpdaterDelegate {
        private TLRPC$FileLocation avatar;
        private AnimatorSet avatarAnimation;
        private TLRPC$FileLocation avatarBig;
        private AvatarDrawable avatarDrawable;
        private RLottieImageView avatarEditor;
        private BackupImageView avatarImage;
        private View avatarOverlay;
        private RadialProgressView avatarProgressView;
        private RLottieDrawable cameraDrawable;
        private RLottieDrawable cameraWaitDrawable;
        private Bundle currentParams;
        private int currentRetryCount;
        private TextView descriptionTextView;
        private FrameLayout editTextContainer;
        private String ext;
        private EditTextBoldCursor firstNameField;
        private OutlineTextContainerView firstNameOutlineView;
        private ImageUpdater imageUpdater;
        private boolean isCameraWaitAnimationAllowed;
        private EditTextBoldCursor lastNameField;
        private OutlineTextContainerView lastNameOutlineView;
        private boolean nextPressed;
        private String password;
        private EditTextBoldCursor phoneField;
        private OutlineTextContainerView phoneOutlineView;
        private TextView privacyView;
        private TextView titleTextView;
        private String username;
        private TextView wrongNumber;

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ boolean canFinishFragment() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$canFinishFragment(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ void didStartUpload(boolean z) {
            ImageUpdater.ImageUpdaterDelegate.CC.$default$didStartUpload(this, z);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ void didUploadFailed() {
            ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* bridge */ /* synthetic */ String getInitialSearchString() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$getInitialSearchString(this);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean needBackButton() {
            return true;
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ void onUploadProgressChanged(float f) {
            ImageUpdater.ImageUpdaterDelegate.CC.$default$onUploadProgressChanged(this, f);
        }

        static /* synthetic */ int access$16208(LoginActivityRegisterView loginActivityRegisterView) {
            int i = loginActivityRegisterView.currentRetryCount;
            loginActivityRegisterView.currentRetryCount = i + 1;
            return i;
        }

        public class LinkSpan extends ClickableSpan {
            public LinkSpan() {
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                LoginActivityRegisterView.this.showTermsOfService(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showTermsOfService(boolean z) {
            if (LoginActivity.this.currentTermsOfService == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString("TermsOfService", R.string.TermsOfService));
            if (z) {
                builder.setPositiveButton(LocaleController.getString("Accept", R.string.Accept), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.LoginActivityRegisterView.this.lambda$showTermsOfService$0(dialogInterface, i);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Decline", R.string.Decline), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda3
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.LoginActivityRegisterView.this.lambda$showTermsOfService$3(dialogInterface, i);
                    }
                });
            } else {
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(LoginActivity.this.currentTermsOfService.text);
            MessageObject.addEntitiesToText(spannableStringBuilder, LoginActivity.this.currentTermsOfService.entities, false, false, false, false);
            builder.setMessage(spannableStringBuilder);
            LoginActivity.this.showDialog(builder.create());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showTermsOfService$0(DialogInterface dialogInterface, int i) {
            LoginActivity.this.currentTermsOfService.popup = false;
            onNextPressed(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showTermsOfService$3(DialogInterface dialogInterface, int i) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString("TermsOfService", R.string.TermsOfService));
            builder.setMessage(LocaleController.getString("TosDecline", R.string.TosDecline));
            builder.setPositiveButton(LocaleController.getString("SignUp", R.string.SignUp), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda4
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface2, int i2) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$showTermsOfService$1(dialogInterface2, i2);
                }
            });
            builder.setNegativeButton(LocaleController.getString("Decline", R.string.Decline), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface2, int i2) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$showTermsOfService$2(dialogInterface2, i2);
                }
            });
            LoginActivity.this.showDialog(builder.create());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showTermsOfService$1(DialogInterface dialogInterface, int i) {
            LoginActivity.this.currentTermsOfService.popup = false;
            onNextPressed(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showTermsOfService$2(DialogInterface dialogInterface, int i) {
            onBackPressed(true);
            LoginActivity.this.setPage(0, true, null, true);
        }

        public LoginActivityRegisterView(Context context) {
            super(context);
            this.nextPressed = false;
            this.isCameraWaitAnimationAllowed = true;
            this.currentRetryCount = 0;
            setOrientation(1);
            ImageUpdater imageUpdater = new ImageUpdater(false, 0, false);
            this.imageUpdater = imageUpdater;
            imageUpdater.setOpenWithFrontfaceCamera(true);
            this.imageUpdater.setSearchAvailable(false);
            this.imageUpdater.setUploadAfterSelect(false);
            ImageUpdater imageUpdater2 = this.imageUpdater;
            imageUpdater2.parentFragment = LoginActivity.this;
            imageUpdater2.setDelegate(this);
            FrameLayout frameLayout = new FrameLayout(context);
            addView(frameLayout, LayoutHelper.createLinear(78, 78, 1));
            this.avatarDrawable = new AvatarDrawable();
            BackupImageView backupImageView = new BackupImageView(context, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.1
                @Override // android.view.View
                public void invalidate() {
                    if (LoginActivityRegisterView.this.avatarOverlay != null) {
                        LoginActivityRegisterView.this.avatarOverlay.invalidate();
                    }
                    super.invalidate();
                }

                @Override // android.view.View
                public void invalidate(int i, int i2, int i3, int i4) {
                    if (LoginActivityRegisterView.this.avatarOverlay != null) {
                        LoginActivityRegisterView.this.avatarOverlay.invalidate();
                    }
                    super.invalidate(i, i2, i3, i4);
                }
            };
            this.avatarImage = backupImageView;
            backupImageView.setRoundRadius(AndroidUtilities.dp(64.0f));
            this.avatarDrawable.setAvatarType(13);
            this.avatarDrawable.setInfo(5L, null, null);
            this.avatarImage.setImageDrawable(this.avatarDrawable);
            frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(-1, -1.0f));
            Paint paint = new Paint(1);
            paint.setColor(1426063360);
            View view = new View(context, LoginActivity.this, paint) { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.2
                final /* synthetic */ Paint val$paint;

                {
                    this.val$paint = paint;
                }

                @Override // android.view.View
                protected void onDraw(Canvas canvas) {
                    if (LoginActivityRegisterView.this.avatarImage == null || LoginActivityRegisterView.this.avatarProgressView.getVisibility() != 0) {
                        return;
                    }
                    this.val$paint.setAlpha((int) (LoginActivityRegisterView.this.avatarImage.getImageReceiver().getCurrentAlpha() * 85.0f * LoginActivityRegisterView.this.avatarProgressView.getAlpha()));
                    canvas.drawCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, getMeasuredWidth() / 2.0f, this.val$paint);
                }
            };
            this.avatarOverlay = view;
            frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f));
            this.avatarOverlay.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$7(view2);
                }
            });
            int i = R.raw.camera;
            this.cameraDrawable = new RLottieDrawable(i, String.valueOf(i), AndroidUtilities.dp(70.0f), AndroidUtilities.dp(70.0f), false, null);
            int i2 = R.raw.camera_wait;
            this.cameraWaitDrawable = new RLottieDrawable(i2, String.valueOf(i2), AndroidUtilities.dp(70.0f), AndroidUtilities.dp(70.0f), false, null);
            RLottieImageView rLottieImageView = new RLottieImageView(context, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.3
                @Override // android.view.View
                public void invalidate(int i3, int i4, int i5, int i6) {
                    super.invalidate(i3, i4, i5, i6);
                    LoginActivityRegisterView.this.avatarOverlay.invalidate();
                }

                @Override // android.view.View
                public void invalidate() {
                    super.invalidate();
                    LoginActivityRegisterView.this.avatarOverlay.invalidate();
                }
            };
            this.avatarEditor = rLottieImageView;
            rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
            this.avatarEditor.setAnimation(this.cameraDrawable);
            this.avatarEditor.setEnabled(false);
            this.avatarEditor.setClickable(false);
            frameLayout.addView(this.avatarEditor, LayoutHelper.createFrame(-1, -1.0f));
            this.avatarEditor.addOnAttachStateChangeListener(new AnonymousClass4(LoginActivity.this));
            RadialProgressView radialProgressView = new RadialProgressView(context, LoginActivity.this) { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.5
                @Override // org.telegram.ui.Components.RadialProgressView, android.view.View
                public void setAlpha(float f) {
                    super.setAlpha(f);
                    LoginActivityRegisterView.this.avatarOverlay.invalidate();
                }
            };
            this.avatarProgressView = radialProgressView;
            radialProgressView.setSize(AndroidUtilities.dp(30.0f));
            this.avatarProgressView.setProgressColor(-1);
            frameLayout.addView(this.avatarProgressView, LayoutHelper.createFrame(-1, -1.0f));
            showAvatarProgress(false, false);
            TextView textView = new TextView(context);
            this.titleTextView = textView;
            textView.setText(LocaleController.getString(R.string.RegistrationProfileInfo));
            this.titleTextView.setTextSize(1, 18.0f);
            this.titleTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            this.titleTextView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.titleTextView.setGravity(1);
            addView(this.titleTextView, LayoutHelper.createLinear(-2, -2, 1, 8, 12, 8, 0));
            TextView textView2 = new TextView(context);
            this.descriptionTextView = textView2;
            textView2.setText(LocaleController.getString("RegisterText2", R.string.RegisterText2));
            this.descriptionTextView.setGravity(1);
            this.descriptionTextView.setTextSize(1, 14.0f);
            this.descriptionTextView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            addView(this.descriptionTextView, LayoutHelper.createLinear(-2, -2, 1, 8, 6, 8, 0));
            FrameLayout frameLayout2 = new FrameLayout(context);
            this.editTextContainer = frameLayout2;
            addView(frameLayout2, LayoutHelper.createLinear(-1, -2, 8.0f, 21.0f, 8.0f, 0.0f));
            OutlineTextContainerView outlineTextContainerView = new OutlineTextContainerView(context);
            this.firstNameOutlineView = outlineTextContainerView;
            outlineTextContainerView.setText(LocaleController.getString(R.string.FirstName));
            EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context);
            this.firstNameField = editTextBoldCursor;
            editTextBoldCursor.setCursorSize(AndroidUtilities.dp(20.0f));
            this.firstNameField.setCursorWidth(1.5f);
            this.firstNameField.setImeOptions(268435461);
            this.firstNameField.setTextSize(1, 17.0f);
            this.firstNameField.setMaxLines(1);
            this.firstNameField.setInputType(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            this.firstNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$8(view2, z);
                }
            });
            this.firstNameField.setBackground(null);
            this.firstNameField.setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
            this.firstNameField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(BuildVars.limitNickNameMax)});
            this.firstNameOutlineView.attachEditText(this.firstNameField);
            this.firstNameOutlineView.addView(this.firstNameField, LayoutHelper.createFrame(-1, -2, 48));
            this.firstNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda11
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView3, int i3, KeyEvent keyEvent) {
                    boolean lambda$new$9;
                    lambda$new$9 = LoginActivity.LoginActivityRegisterView.this.lambda$new$9(textView3, i3, keyEvent);
                    return lambda$new$9;
                }
            });
            OutlineTextContainerView outlineTextContainerView2 = new OutlineTextContainerView(context);
            this.lastNameOutlineView = outlineTextContainerView2;
            outlineTextContainerView2.setText(LocaleController.getString("JMTInvitationCode", R.string.JMTInvitationCode));
            EditTextBoldCursor editTextBoldCursor2 = new EditTextBoldCursor(context);
            this.lastNameField = editTextBoldCursor2;
            editTextBoldCursor2.setCursorSize(AndroidUtilities.dp(20.0f));
            this.lastNameField.setCursorWidth(1.5f);
            this.lastNameField.setImeOptions(268435462);
            this.lastNameField.setTextSize(1, 17.0f);
            this.lastNameField.setMaxLines(1);
            this.lastNameField.setInputType(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
            this.lastNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda8
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$10(view2, z);
                }
            });
            this.lastNameField.setBackground(null);
            this.lastNameField.setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
            this.lastNameOutlineView.attachEditText(this.lastNameField);
            this.lastNameOutlineView.addView(this.lastNameField, LayoutHelper.createFrame(-1, -2, 48));
            OutlineTextContainerView outlineTextContainerView3 = new OutlineTextContainerView(context);
            this.phoneOutlineView = outlineTextContainerView3;
            outlineTextContainerView3.setText(LocaleController.getString("PhoneNumber", R.string.PhoneNumber));
            EditTextBoldCursor editTextBoldCursor3 = new EditTextBoldCursor(context);
            this.phoneField = editTextBoldCursor3;
            editTextBoldCursor3.setCursorSize(AndroidUtilities.dp(20.0f));
            this.phoneField.setCursorWidth(1.5f);
            this.phoneField.setImeOptions(268435461);
            this.phoneField.setTextSize(1, 17.0f);
            this.phoneField.setMaxLines(1);
            this.phoneField.setInputType(3);
            this.phoneField.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$11(view2, z);
                }
            });
            this.phoneField.setBackground(null);
            this.phoneField.setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
            this.phoneOutlineView.attachEditText(this.phoneField);
            this.phoneOutlineView.addView(this.phoneField, LayoutHelper.createFrame(-1, -2, 48));
            buildEditTextLayout(AndroidUtilities.isSmallScreen());
            TextView textView3 = new TextView(context);
            this.wrongNumber = textView3;
            textView3.setText(LocaleController.getString("CancelRegistration", R.string.CancelRegistration));
            this.wrongNumber.setGravity((LocaleController.isRTL ? 5 : 3) | 1);
            this.wrongNumber.setTextSize(1, 14.0f);
            this.wrongNumber.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.wrongNumber.setPadding(0, AndroidUtilities.dp(24.0f), 0, 0);
            this.wrongNumber.setVisibility(8);
            addView(this.wrongNumber, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 0, 20, 0, 0));
            this.wrongNumber.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$12(view2);
                }
            });
            FrameLayout frameLayout3 = new FrameLayout(context);
            addView(frameLayout3, LayoutHelper.createLinear(-1, -1, 83));
            TextView textView4 = new TextView(context);
            this.privacyView = textView4;
            textView4.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
            this.privacyView.setTextSize(1, AndroidUtilities.isSmallScreen() ? 13.0f : 14.0f);
            this.privacyView.setLineSpacing(AndroidUtilities.dp(2.0f), 1.0f);
            this.privacyView.setGravity(16);
            if (BuildVars.isShowLoginPrivacyView) {
                frameLayout3.addView(this.privacyView, LayoutHelper.createFrame(-2, Build.VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, 83, 14.0f, 0.0f, 70.0f, 32.0f));
            }
            VerticalPositionAutoAnimator.attach(this.privacyView);
            String string = LocaleController.getString("TermsOfServiceLogin", R.string.TermsOfServiceLogin);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
            int indexOf = string.indexOf(42);
            int lastIndexOf = string.lastIndexOf(42);
            if (indexOf != -1 && lastIndexOf != -1 && indexOf != lastIndexOf) {
                spannableStringBuilder.replace(lastIndexOf, lastIndexOf + 1, (CharSequence) "");
                spannableStringBuilder.replace(indexOf, indexOf + 1, (CharSequence) "");
                spannableStringBuilder.setSpan(new LinkSpan(), indexOf, lastIndexOf - 1, 33);
            }
            this.privacyView.setText(spannableStringBuilder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$7(View view) {
            this.imageUpdater.openMenu(this.avatar != null, new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$4();
                }
            }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda5
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$new$6(dialogInterface);
                }
            }, 0);
            this.isCameraWaitAnimationAllowed = false;
            this.avatarEditor.setAnimation(this.cameraDrawable);
            this.cameraDrawable.setCurrentFrame(0);
            this.cameraDrawable.setCustomEndFrame(43);
            this.avatarEditor.playAnimation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$4() {
            this.avatar = null;
            this.avatarBig = null;
            showAvatarProgress(false, true);
            this.avatarImage.setImage((ImageLocation) null, (String) null, this.avatarDrawable, (Object) null);
            this.avatarEditor.setAnimation(this.cameraDrawable);
            this.cameraDrawable.setCurrentFrame(0);
            this.isCameraWaitAnimationAllowed = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$6(DialogInterface dialogInterface) {
            if (!this.imageUpdater.isUploadingImage()) {
                this.avatarEditor.setAnimation(this.cameraDrawable);
                this.cameraDrawable.setCustomEndFrame(86);
                this.avatarEditor.setOnAnimationEndListener(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivityRegisterView.this.lambda$new$5();
                    }
                });
                this.avatarEditor.playAnimation();
                return;
            }
            this.avatarEditor.setAnimation(this.cameraDrawable);
            this.cameraDrawable.setCurrentFrame(0, false);
            this.isCameraWaitAnimationAllowed = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$5() {
            this.isCameraWaitAnimationAllowed = true;
        }

        /* renamed from: org.telegram.ui.LoginActivity$LoginActivityRegisterView$4, reason: invalid class name */
        class AnonymousClass4 implements View.OnAttachStateChangeListener {
            private boolean isAttached;
            private long lastRun = System.currentTimeMillis();
            private Runnable cameraWaitCallback = new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$4$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.AnonymousClass4.this.lambda$$2();
                }
            };

            AnonymousClass4(LoginActivity loginActivity) {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$$2() {
                if (this.isAttached) {
                    if (LoginActivityRegisterView.this.isCameraWaitAnimationAllowed && System.currentTimeMillis() - this.lastRun >= 10000) {
                        LoginActivityRegisterView.this.avatarEditor.setAnimation(LoginActivityRegisterView.this.cameraWaitDrawable);
                        LoginActivityRegisterView.this.cameraWaitDrawable.setCurrentFrame(0, false);
                        LoginActivityRegisterView.this.cameraWaitDrawable.setOnAnimationEndListener(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$4$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                LoginActivity.LoginActivityRegisterView.AnonymousClass4.this.lambda$$1();
                            }
                        });
                        LoginActivityRegisterView.this.avatarEditor.playAnimation();
                        this.lastRun = System.currentTimeMillis();
                    }
                    LoginActivityRegisterView.this.avatarEditor.postDelayed(this.cameraWaitCallback, 1000L);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$$1() {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LoginActivity.LoginActivityRegisterView.AnonymousClass4.this.lambda$$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$$0() {
                LoginActivityRegisterView.this.cameraDrawable.setCurrentFrame(0, false);
                LoginActivityRegisterView.this.avatarEditor.setAnimation(LoginActivityRegisterView.this.cameraDrawable);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                this.isAttached = true;
                view.post(this.cameraWaitCallback);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                this.isAttached = false;
                view.removeCallbacks(this.cameraWaitCallback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$8(View view, boolean z) {
            this.firstNameOutlineView.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$9(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5) {
                return false;
            }
            this.lastNameField.requestFocus();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$10(View view, boolean z) {
            this.lastNameOutlineView.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$11(View view, boolean z) {
            this.phoneOutlineView.animateSelection(z ? 1.0f : 0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$12(View view) {
            if (LoginActivity.this.radialProgressView.getTag() != null) {
                return;
            }
            onBackPressed(false);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void updateColors() {
            this.avatarDrawable.invalidateSelf();
            TextView textView = this.titleTextView;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            textView.setTextColor(Theme.getColor(i));
            TextView textView2 = this.descriptionTextView;
            int i2 = Theme.key_windowBackgroundWhiteGrayText6;
            textView2.setTextColor(Theme.getColor(i2));
            this.firstNameField.setTextColor(Theme.getColor(i));
            EditTextBoldCursor editTextBoldCursor = this.firstNameField;
            int i3 = Theme.key_windowBackgroundWhiteInputFieldActivated;
            editTextBoldCursor.setCursorColor(Theme.getColor(i3));
            this.lastNameField.setTextColor(Theme.getColor(i));
            this.lastNameField.setCursorColor(Theme.getColor(i3));
            this.phoneField.setTextColor(Theme.getColor(i));
            this.phoneField.setCursorColor(Theme.getColor(i3));
            this.wrongNumber.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
            this.privacyView.setTextColor(Theme.getColor(i2));
            this.privacyView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
            this.firstNameOutlineView.updateColor();
            this.lastNameOutlineView.updateColor();
            this.phoneOutlineView.updateColor();
        }

        private void buildEditTextLayout(boolean z) {
            boolean hasFocus = this.firstNameField.hasFocus();
            boolean hasFocus2 = this.lastNameField.hasFocus();
            boolean hasFocus3 = this.phoneField.hasFocus();
            this.editTextContainer.removeAllViews();
            if (z) {
                LinearLayout linearLayout = new LinearLayout(LoginActivity.this.getParentActivity());
                linearLayout.setOrientation(0);
                this.firstNameOutlineView.setText(LocaleController.getString(R.string.FirstNameSmall));
                this.lastNameOutlineView.setText(LocaleController.getString("JMTInvitationCode", R.string.JMTInvitationCode));
                this.phoneOutlineView.setText(LocaleController.getString("PhoneNumber", R.string.PhoneNumber));
                linearLayout.addView(this.firstNameOutlineView, LayoutHelper.createLinear(0, -2, 1.0f, 0, 0, 8, 0));
                linearLayout.addView(this.lastNameOutlineView, LayoutHelper.createLinear(0, -2, 1.0f, 8, 0, 0, 0));
                linearLayout.addView(this.phoneOutlineView, LayoutHelper.createLinear(0, -2, 1.0f, 8, 0, 0, 0));
                this.editTextContainer.addView(linearLayout);
                if (hasFocus) {
                    this.firstNameField.requestFocus();
                    AndroidUtilities.showKeyboard(this.firstNameField);
                    return;
                } else if (hasFocus2) {
                    this.lastNameField.requestFocus();
                    AndroidUtilities.showKeyboard(this.lastNameField);
                    return;
                } else {
                    if (hasFocus3) {
                        this.phoneField.requestFocus();
                        AndroidUtilities.showKeyboard(this.phoneField);
                        return;
                    }
                    return;
                }
            }
            this.firstNameOutlineView.setText(LocaleController.getString(R.string.FirstName));
            this.lastNameOutlineView.setText(LocaleController.getString("JMTInvitationCode", R.string.JMTInvitationCode));
            this.phoneOutlineView.setText(LocaleController.getString("PhoneNumber", R.string.PhoneNumber));
            this.editTextContainer.addView(this.firstNameOutlineView, LayoutHelper.createFrame(-1, -2.0f, 48, 8.0f, 0.0f, 8.0f, 0.0f));
            this.editTextContainer.addView(this.lastNameOutlineView, LayoutHelper.createFrame(-1, -2.0f, 48, 8.0f, 82.0f, 8.0f, 0.0f));
            this.editTextContainer.addView(this.phoneOutlineView, LayoutHelper.createFrame(-1, -2.0f, 48, 8.0f, 164.0f, 8.0f, 0.0f));
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didUploadPhoto(TLRPC$InputFile tLRPC$InputFile, TLRPC$InputFile tLRPC$InputFile2, double d, String str, final TLRPC$PhotoSize tLRPC$PhotoSize, final TLRPC$PhotoSize tLRPC$PhotoSize2, boolean z, TLRPC$VideoSize tLRPC$VideoSize) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.this.lambda$didUploadPhoto$13(tLRPC$PhotoSize2, tLRPC$PhotoSize);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didUploadPhoto$13(TLRPC$PhotoSize tLRPC$PhotoSize, TLRPC$PhotoSize tLRPC$PhotoSize2) {
            TLRPC$FileLocation tLRPC$FileLocation = tLRPC$PhotoSize.location;
            this.avatar = tLRPC$FileLocation;
            this.avatarBig = tLRPC$PhotoSize2.location;
            this.avatarImage.setImage(ImageLocation.getForLocal(tLRPC$FileLocation), "50_50", this.avatarDrawable, (Object) null);
        }

        private void showAvatarProgress(final boolean z, boolean z2) {
            if (this.avatarEditor == null) {
                return;
            }
            AnimatorSet animatorSet = this.avatarAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.avatarAnimation = null;
            }
            if (z2) {
                this.avatarAnimation = new AnimatorSet();
                if (z) {
                    this.avatarProgressView.setVisibility(0);
                    this.avatarAnimation.playTogether(ObjectAnimator.ofFloat(this.avatarEditor, (Property<RLottieImageView, Float>) View.ALPHA, 0.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 1.0f));
                } else {
                    this.avatarEditor.setVisibility(0);
                    this.avatarAnimation.playTogether(ObjectAnimator.ofFloat(this.avatarEditor, (Property<RLottieImageView, Float>) View.ALPHA, 1.0f), ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 0.0f));
                }
                this.avatarAnimation.setDuration(180L);
                this.avatarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (LoginActivityRegisterView.this.avatarAnimation == null || LoginActivityRegisterView.this.avatarEditor == null) {
                            return;
                        }
                        if (z) {
                            LoginActivityRegisterView.this.avatarEditor.setVisibility(4);
                        } else {
                            LoginActivityRegisterView.this.avatarProgressView.setVisibility(4);
                        }
                        LoginActivityRegisterView.this.avatarAnimation = null;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        LoginActivityRegisterView.this.avatarAnimation = null;
                    }
                });
                this.avatarAnimation.start();
                return;
            }
            if (z) {
                this.avatarEditor.setAlpha(1.0f);
                this.avatarEditor.setVisibility(4);
                this.avatarProgressView.setAlpha(1.0f);
                this.avatarProgressView.setVisibility(0);
                return;
            }
            this.avatarEditor.setAlpha(1.0f);
            this.avatarEditor.setVisibility(0);
            this.avatarProgressView.setAlpha(0.0f);
            this.avatarProgressView.setVisibility(4);
        }

        @Override // org.telegram.ui.Components.SlideView
        public boolean onBackPressed(boolean z) {
            if (z) {
                LoginActivity.this.needHideProgress(true);
                this.nextPressed = false;
                this.currentParams = null;
                return true;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString(R.string.Warning));
            builder.setMessage(LocaleController.getString("AreYouSureRegistration", R.string.AreYouSureRegistration));
            builder.setNegativeButton(LocaleController.getString("Stop", R.string.Stop), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$onBackPressed$14(dialogInterface, i);
                }
            });
            builder.setPositiveButton(LocaleController.getString("Continue", R.string.Continue), null);
            LoginActivity.this.showDialog(builder.create());
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBackPressed$14(DialogInterface dialogInterface, int i) {
            onBackPressed(true);
            LoginActivity.this.setPage(0, true, null, true);
            hidePrivacyView();
        }

        @Override // org.telegram.ui.Components.SlideView
        public String getHeaderName() {
            return LocaleController.getString("YourName", R.string.YourName);
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onCancelPressed() {
            this.nextPressed = false;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onShow() {
            super.onShow();
            if (this.privacyView != null) {
                if (LoginActivity.this.restoringState) {
                    this.privacyView.setAlpha(1.0f);
                } else {
                    this.privacyView.setAlpha(0.0f);
                    this.privacyView.animate().alpha(1.0f).setDuration(200L).setStartDelay(300L).setInterpolator(AndroidUtilities.decelerateInterpolator).start();
                }
            }
            EditTextBoldCursor editTextBoldCursor = this.firstNameField;
            if (editTextBoldCursor != null) {
                editTextBoldCursor.requestFocus();
                EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
                editTextBoldCursor2.setSelection(editTextBoldCursor2.length());
                AndroidUtilities.showKeyboard(this.firstNameField);
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.this.lambda$onShow$15();
                }
            }, LoginActivity.SHOW_DELAY);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onShow$15() {
            EditTextBoldCursor editTextBoldCursor = this.firstNameField;
            if (editTextBoldCursor != null) {
                editTextBoldCursor.requestFocus();
                EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
                editTextBoldCursor2.setSelection(editTextBoldCursor2.length());
                AndroidUtilities.showKeyboard(this.firstNameField);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void setParams(Bundle bundle, boolean z) {
            if (bundle == null) {
                return;
            }
            this.firstNameField.setText("");
            this.lastNameField.setText("");
            this.phoneField.setText("");
            this.username = bundle.getString("username");
            this.password = bundle.getString("password");
            this.ext = bundle.getString("ext");
            this.currentParams = bundle;
        }

        @Override // org.telegram.ui.Components.SlideView
        public void onNextPressed(String str) {
            if (this.nextPressed) {
                return;
            }
            if (LoginActivity.this.currentTermsOfService != null && LoginActivity.this.currentTermsOfService.popup) {
                showTermsOfService(true);
                return;
            }
            if (BuildVars.isForceSetAvatar && this.avatarBig == null) {
                Toast.makeText(LoginActivity.this.getParentActivity(), LocaleController.getString("JMTUploadUserAvatar", R.string.JMTUploadUserAvatar), 0).show();
                return;
            }
            String trim = this.firstNameField.getText().toString().trim();
            if (trim.length() == 0) {
                LoginActivity.this.onFieldError(this.firstNameOutlineView, true);
                return;
            }
            if (BuildVars.isInviteCode && this.lastNameField.length() == 0) {
                LoginActivity.this.onFieldError(this.lastNameOutlineView, true);
                return;
            }
            String trim2 = this.phoneField.getText().toString().trim();
            if (BuildVars.requireRegisterPhone) {
                if (trim2.length() == 0) {
                    LoginActivity.this.onFieldError(this.phoneOutlineView, true);
                    return;
                } else if (!trim2.matches("1[0-9]{10}")) {
                    Toast.makeText(getContext(), LocaleController.getString("JMTPhoneError", R.string.JMTPhoneError), 0).show();
                    return;
                }
            }
            this.nextPressed = true;
            signUpByUser(trim, trim2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void signUpByUser(final String str, final String str2) {
            int i = BuildVars.controlInstallSDK;
            if (i == 1) {
                OpenInstall.getInstallCanRetry(new AppInstallRetryAdapter() { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.7
                    @Override // com.fm.openinstall.listener.AppInstallRetryAdapter
                    public void onInstall(AppData appData, boolean z) {
                        String channel = appData.getChannel();
                        if (z) {
                            if (LoginActivityRegisterView.this.currentRetryCount >= 3) {
                                LoginActivityRegisterView.this.realSignUpByUser(str, str2, channel);
                                return;
                            } else {
                                LoginActivityRegisterView.access$16208(LoginActivityRegisterView.this);
                                LoginActivityRegisterView.this.signUpByUser(str, str2);
                                return;
                            }
                        }
                        LoginActivityRegisterView.this.realSignUpByUser(str, str2, channel);
                    }
                }, 2);
            } else if (i == 2) {
                XInstall.getInstallParam(new XInstallListener() { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.8
                    @Override // com.xinstall.listener.XInstallListener
                    public void onInstallFinish(XAppData xAppData, XAppError xAppError) {
                        String channelCode = xAppData.getChannelCode();
                        if (TextUtils.isEmpty(channelCode)) {
                            if (LoginActivityRegisterView.this.currentRetryCount >= 3) {
                                LoginActivityRegisterView.this.realSignUpByUser(str, str2, channelCode);
                                return;
                            } else {
                                LoginActivityRegisterView.access$16208(LoginActivityRegisterView.this);
                                LoginActivityRegisterView.this.signUpByUser(str, str2);
                                return;
                            }
                        }
                        LoginActivityRegisterView.this.realSignUpByUser(str, str2, channelCode);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public void realSignUpByUser(String str, String str2, String str3) {
            TLRPC$JMT_signUpByUsernameV4 tLRPC$JMT_signUpByUsernameV4;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：LoginActivityRegisterView，开始请求:TLRPC.JMT_signUpByUsername，channelCode=" + str3);
            }
            if (!BuildVars.isSplitLoginAndRegister) {
                TLRPC$JMT_signUpByUsernameV3 tLRPC$JMT_signUpByUsernameV3 = new TLRPC$JMT_signUpByUsernameV3();
                tLRPC$JMT_signUpByUsernameV3.username = this.username;
                tLRPC$JMT_signUpByUsernameV3.password = this.password;
                tLRPC$JMT_signUpByUsernameV3.first_name = str;
                if (!BuildVars.requireRegisterPhone) {
                    str2 = "";
                }
                tLRPC$JMT_signUpByUsernameV3.last_name = str2;
                tLRPC$JMT_signUpByUsernameV3.invite_code = this.lastNameField.getText().toString().trim();
                tLRPC$JMT_signUpByUsernameV3.channel_code = str3;
                tLRPC$JMT_signUpByUsernameV4 = tLRPC$JMT_signUpByUsernameV3;
            } else {
                TLRPC$JMT_signUpByUsernameV4 tLRPC$JMT_signUpByUsernameV42 = new TLRPC$JMT_signUpByUsernameV4();
                tLRPC$JMT_signUpByUsernameV42.username = this.username;
                tLRPC$JMT_signUpByUsernameV42.password = this.password;
                tLRPC$JMT_signUpByUsernameV42.first_name = str;
                if (!BuildVars.requireRegisterPhone) {
                    str2 = "";
                }
                tLRPC$JMT_signUpByUsernameV42.last_name = str2;
                tLRPC$JMT_signUpByUsernameV42.invite_code = this.lastNameField.getText().toString().trim();
                tLRPC$JMT_signUpByUsernameV42.channel_code = str3;
                tLRPC$JMT_signUpByUsernameV42.ext = this.ext;
                tLRPC$JMT_signUpByUsernameV4 = tLRPC$JMT_signUpByUsernameV42;
            }
            LoginActivity.this.needShowProgress(0);
            ConnectionsManager.getInstance(((BaseFragment) LoginActivity.this).currentAccount).sendRequest(tLRPC$JMT_signUpByUsernameV4, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda18
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    LoginActivity.LoginActivityRegisterView.this.lambda$realSignUpByUser$18(tLObject, tLRPC$TL_error);
                }
            }, 10);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$realSignUpByUser$18(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.this.lambda$realSignUpByUser$17(tLObject, tLRPC$TL_error);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$realSignUpByUser$17(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("现在是：LoginActivityRegisterView，请求成功:TLRPC.JMT_signUpByUsername");
            }
            this.nextPressed = false;
            if (!(tLObject instanceof TLRPC$TL_auth_authorization)) {
                LoginActivity.this.needHideProgress(false);
                if (tLRPC$TL_error.text.contains("PHONE_NUMBER_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidPhoneNumber", R.string.InvalidPhoneNumber));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_CODE_EMPTY") || tLRPC$TL_error.text.contains("PHONE_CODE_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidCode", R.string.InvalidCode));
                    return;
                }
                if (tLRPC$TL_error.text.contains("PHONE_CODE_EXPIRED")) {
                    onBackPressed(true);
                    LoginActivity.this.setPage(0, true, null, true);
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("CodeExpired", R.string.CodeExpired));
                    return;
                }
                if (tLRPC$TL_error.text.contains("FIRSTNAME_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidFirstName", R.string.InvalidFirstName));
                    return;
                }
                if (tLRPC$TL_error.text.contains("LASTNAME_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("InvalidLastName", R.string.InvalidLastName));
                    return;
                }
                if (tLRPC$TL_error.text.contains("INVITE_CODE_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTInvitationCodeError", R.string.JMTInvitationCodeError));
                    return;
                }
                if (tLRPC$TL_error.text.contains("USERNAME_OCCUPIED")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTUsernameOccupied", R.string.JMTUsernameOccupied));
                    return;
                }
                if (tLRPC$TL_error.text.contains("USERNAME_INVALID")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTUsernameInvalid", R.string.JMTUsernameInvalid));
                    return;
                }
                if (tLRPC$TL_error.text.contains("DEVICE_REGISTER_TOO_OFTEN")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrorCode999", R.string.JMTErrorCode999));
                    return;
                }
                if ("IP_BLOCKED".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPBlocked", R.string.JMTErrIPBlocked));
                    return;
                }
                if ("ACCOUNT_BLOCKED".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAccountBlocked", R.string.JMTErrAccountBlocked));
                    return;
                }
                if ("IP_NOT_IN_WHITE_LIST".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrIPNotInWhiteList", R.string.JMTErrIPNotInWhiteList));
                    return;
                }
                if ("APP_VERSION_DEPRECATED".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrAppVersionDeprecated", R.string.JMTErrAppVersionDeprecated));
                    return;
                }
                if ("INTERNAL_SERVER_ERROR".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrServerBusy", R.string.JMTErrServerBusy));
                    return;
                }
                if ("PHONE_NUMBER_UNOCCUPIED".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhoneNumberUnoccupied", R.string.JMTErrPhoneNumberUnoccupied));
                    return;
                }
                if ("CLIENT_DANGEROUS".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrClientDangerous", R.string.JMTErrClientDangerous));
                    return;
                }
                if ("PHONE_PASSWORD_FLOOD".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrPhonePasswordFlood", R.string.JMTErrPhonePasswordFlood));
                    return;
                }
                if (tLRPC$TL_error.text.contains("TOO_OFTEN")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTIPMax", R.string.JMTIPMax));
                    return;
                }
                if ("TOO_OFTEN".equals(tLRPC$TL_error.text)) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTIPMax", R.string.JMTIPMax));
                    return;
                } else if (tLRPC$TL_error.text.startsWith("PHONE_NUMBER_BANNED")) {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("JMTErrBannedPhone", R.string.JMTErrBannedPhone));
                    return;
                } else {
                    LoginActivity.this.needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
                    return;
                }
            }
            hidePrivacyView();
            LoginActivity.this.showDoneButton(false, true);
            postDelayed(new Runnable() { // from class: org.telegram.ui.LoginActivity$LoginActivityRegisterView$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.LoginActivityRegisterView.this.lambda$realSignUpByUser$16(tLObject);
                }
            }, 250L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$realSignUpByUser$16(TLObject tLObject) {
            LoginActivity.this.needHideProgress(false, false);
            AndroidUtilities.hideKeyboard(((BaseFragment) LoginActivity.this).fragmentView.findFocus());
            int i = BuildVars.controlInstallSDK;
            if (i == 1) {
                OpenInstall.reportRegister();
            } else if (i == 2) {
                XInstall.reportRegister();
            }
            LoginActivity.this.onAuthSuccess((TLRPC$TL_auth_authorization) tLObject, true);
            final TLRPC$FileLocation tLRPC$FileLocation = this.avatarBig;
            if (tLRPC$FileLocation != null) {
                Utilities.cacheClearQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.LoginActivity.LoginActivityRegisterView.9
                    @Override // java.lang.Runnable
                    public void run() {
                        MessagesController.getInstance(((BaseFragment) LoginActivity.this).currentAccount).uploadAndApplyUserAvatar(tLRPC$FileLocation);
                        MessagesController.getInstance(((BaseFragment) LoginActivity.this).currentAccount).uploadAndApplyUserAvatar(tLRPC$FileLocation);
                        MessagesController.getInstance(((BaseFragment) LoginActivity.this).currentAccount).uploadAndApplyUserAvatar(tLRPC$FileLocation);
                    }
                });
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void saveStateParams(Bundle bundle) {
            String obj = this.firstNameField.getText().toString();
            if (obj.length() != 0) {
                bundle.putString("registerview_first", obj);
            }
            String obj2 = this.lastNameField.getText().toString();
            if (obj2.length() != 0) {
                bundle.putString("registerview_last", obj2);
            }
            if (LoginActivity.this.currentTermsOfService != null) {
                SerializedData serializedData = new SerializedData(LoginActivity.this.currentTermsOfService.getObjectSize());
                LoginActivity.this.currentTermsOfService.serializeToStream(serializedData);
                bundle.putString("terms", Base64.encodeToString(serializedData.toByteArray(), 0));
                serializedData.cleanup();
            }
            Bundle bundle2 = this.currentParams;
            if (bundle2 != null) {
                bundle.putBundle("registerview_params", bundle2);
            }
        }

        @Override // org.telegram.ui.Components.SlideView
        public void restoreStateParams(Bundle bundle) {
            byte[] decode;
            Bundle bundle2 = bundle.getBundle("registerview_params");
            this.currentParams = bundle2;
            if (bundle2 != null) {
                setParams(bundle2, true);
            }
            try {
                String string = bundle.getString("terms");
                if (string != null && (decode = Base64.decode(string, 0)) != null) {
                    SerializedData serializedData = new SerializedData(decode);
                    LoginActivity.this.currentTermsOfService = TLRPC$TL_help_termsOfService.TLdeserialize(serializedData, serializedData.readInt32(false), false);
                    serializedData.cleanup();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            String string2 = bundle.getString("registerview_first");
            if (string2 != null) {
                this.firstNameField.setText(string2);
            }
            String string3 = bundle.getString("registerview_last");
            if (string3 != null) {
                this.lastNameField.setText(string3);
            }
        }

        private void hidePrivacyView() {
            this.privacyView.animate().alpha(0.0f).setDuration(150L).setStartDelay(0L).setInterpolator(AndroidUtilities.accelerateInterpolator).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showKeyboard(View view) {
        if (isCustomKeyboardVisible()) {
            return true;
        }
        return AndroidUtilities.showKeyboard(view);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public AnimatorSet onCustomTransitionAnimation(boolean z, final Runnable runnable) {
        if (!z || this.introView == null) {
            return null;
        }
        if (this.fragmentView.getParent() instanceof View) {
            ((View) this.fragmentView.getParent()).setTranslationX(0.0f);
        }
        final TransformableLoginButtonView transformableLoginButtonView = new TransformableLoginButtonView(this.fragmentView.getContext());
        transformableLoginButtonView.setButtonText(this.startMessagingButton.getPaint(), this.startMessagingButton.getText().toString());
        final int width = this.startMessagingButton.getWidth();
        final int height = this.startMessagingButton.getHeight();
        final int i = this.floatingButtonIcon.getLayoutParams().width;
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        transformableLoginButtonView.setLayoutParams(layoutParams);
        int[] iArr = new int[2];
        this.fragmentView.getLocationInWindow(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        this.startMessagingButton.getLocationInWindow(iArr);
        final float f = iArr[0] - i2;
        final float f2 = iArr[1] - i3;
        transformableLoginButtonView.setTranslationX(f);
        transformableLoginButtonView.setTranslationY(f2);
        final int width2 = (((getParentLayout().getView().getWidth() - this.floatingButtonIcon.getLayoutParams().width) - ((ViewGroup.MarginLayoutParams) this.floatingButtonContainer.getLayoutParams()).rightMargin) - getParentLayout().getView().getPaddingLeft()) - getParentLayout().getView().getPaddingRight();
        final int height2 = ((((getParentLayout().getView().getHeight() - this.floatingButtonIcon.getLayoutParams().height) - ((ViewGroup.MarginLayoutParams) this.floatingButtonContainer.getLayoutParams()).bottomMargin) - (isCustomKeyboardVisible() ? AndroidUtilities.dp(230.0f) : 0)) - getParentLayout().getView().getPaddingTop()) - getParentLayout().getView().getPaddingBottom();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.LoginActivity.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                LoginActivity.this.floatingButtonContainer.setVisibility(4);
                LoginActivity.this.keyboardLinearLayout.setAlpha(0.0f);
                ((BaseFragment) LoginActivity.this).fragmentView.setBackgroundColor(0);
                LoginActivity.this.startMessagingButton.setVisibility(4);
                ((FrameLayout) ((BaseFragment) LoginActivity.this).fragmentView).addView(transformableLoginButtonView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                LoginActivity.this.keyboardLinearLayout.setAlpha(1.0f);
                LoginActivity.this.startMessagingButton.setVisibility(0);
                ((BaseFragment) LoginActivity.this).fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                LoginActivity.this.floatingButtonContainer.setVisibility(0);
                ((FrameLayout) ((BaseFragment) LoginActivity.this).fragmentView).removeView(transformableLoginButtonView);
                if (LoginActivity.this.animationFinishCallback != null) {
                    AndroidUtilities.runOnUIThread(LoginActivity.this.animationFinishCallback);
                    LoginActivity.this.animationFinishCallback = null;
                }
                LoginActivity.this.isAnimatingIntro = false;
                runnable.run();
            }
        });
        final int color = Theme.getColor(Theme.key_windowBackgroundWhite);
        final int alpha = Color.alpha(color);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LoginActivity.this.lambda$onCustomTransitionAnimation$27(color, alpha, layoutParams, width, i, height, transformableLoginButtonView, f, width2, f2, height2, valueAnimator);
            }
        });
        ofFloat.setInterpolator(CubicBezierInterpolator.DEFAULT);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300L);
        animatorSet.playTogether(ofFloat);
        animatorSet.start();
        return animatorSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCustomTransitionAnimation$27(int i, int i2, ViewGroup.MarginLayoutParams marginLayoutParams, int i3, int i4, int i5, TransformableLoginButtonView transformableLoginButtonView, float f, int i6, float f2, int i7, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.keyboardLinearLayout.setAlpha(floatValue);
        this.fragmentView.setBackgroundColor(ColorUtils.setAlphaComponent(i, (int) (i2 * floatValue)));
        float f3 = 1.0f - floatValue;
        this.slideViewsContainer.setTranslationY(AndroidUtilities.dp(20.0f) * f3);
        if (!isCustomKeyboardForceDisabled()) {
            this.keyboardView.setTranslationY(r4.getLayoutParams().height * f3);
            this.floatingButtonContainer.setTranslationY(this.keyboardView.getLayoutParams().height * f3);
        }
        this.introView.setTranslationY((-AndroidUtilities.dp(20.0f)) * floatValue);
        float f4 = (f3 * 0.05f) + 0.95f;
        this.introView.setScaleX(f4);
        this.introView.setScaleY(f4);
        marginLayoutParams.width = (int) (i3 + ((i4 - i3) * floatValue));
        marginLayoutParams.height = (int) (i5 + ((i4 - i5) * floatValue));
        transformableLoginButtonView.requestLayout();
        transformableLoginButtonView.setProgress(floatValue);
        transformableLoginButtonView.setTranslationX(f + ((i6 - f) * floatValue));
        transformableLoginButtonView.setTranslationY(f2 + ((i7 - f2) * floatValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColors() {
        try {
            this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        } catch (Exception unused) {
        }
        Activity parentActivity = getParentActivity();
        int dp = AndroidUtilities.dp(56.0f);
        int i = Theme.key_chats_actionBackground;
        Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(dp, Theme.getColor(i), Theme.getColor(Theme.key_chats_actionPressedBackground));
        if (Build.VERSION.SDK_INT < 21) {
            Drawable mutate = parentActivity.getResources().getDrawable(R.drawable.floating_shadow).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
            CombinedDrawable combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
            combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            createSimpleSelectorCircleDrawable = combinedDrawable;
        }
        this.floatingButtonContainer.setBackground(createSimpleSelectorCircleDrawable);
        if (BuildVars.isShowLoginVideo || BuildVars.isShowLoginImage) {
            this.backButtonView.setColorFilter(-1);
        } else {
            this.backButtonView.setColorFilter(-8421505);
        }
        ImageView imageView = this.backButtonView;
        int i2 = Theme.key_listSelector;
        imageView.setBackground(Theme.createSelectorDrawable(Theme.getColor(i2)));
        ProxyDrawable proxyDrawable = this.proxyDrawable;
        int i3 = Theme.key_windowBackgroundWhiteBlackText;
        proxyDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i3), PorterDuff.Mode.SRC_IN));
        this.proxyDrawable.setColorKey(i3);
        this.proxyButtonView.setBackground(Theme.createSelectorDrawable(Theme.getColor(i2)));
        this.radialProgressView.setProgressColor(Theme.getColor(i));
        TransformableLoginButtonView transformableLoginButtonView = this.floatingButtonIcon;
        int i4 = Theme.key_chats_actionIcon;
        transformableLoginButtonView.setColor(Theme.getColor(i4));
        this.floatingButtonIcon.setBackgroundColor(Theme.getColor(i));
        this.floatingProgressView.setProgressColor(Theme.getColor(i4));
        for (SlideView slideView : this.views) {
            slideView.updateColors();
        }
        this.keyboardView.updateColors();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        return SimpleThemeDescription.createThemeDescriptions(new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda31
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                LoginActivity.this.updateColors();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        }, Theme.key_windowBackgroundWhiteBlackText, Theme.key_windowBackgroundWhiteGrayText6, Theme.key_windowBackgroundWhiteHintText, Theme.key_listSelector, Theme.key_chats_actionBackground, Theme.key_chats_actionIcon, Theme.key_windowBackgroundWhiteInputField, Theme.key_windowBackgroundWhiteInputFieldActivated, Theme.key_windowBackgroundWhiteValueText, Theme.key_text_RedBold, Theme.key_windowBackgroundWhiteGrayText, Theme.key_checkbox, Theme.key_windowBackgroundWhiteBlueText4, Theme.key_changephoneinfo_image2, Theme.key_chats_actionPressedBackground, Theme.key_text_RedRegular, Theme.key_windowBackgroundWhiteLinkText, Theme.key_checkboxSquareUnchecked, Theme.key_checkboxSquareBackground, Theme.key_checkboxSquareCheck, Theme.key_dialogBackground, Theme.key_dialogTextGray2, Theme.key_dialogTextBlack);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryResetAccount(final String str, final String str2, final String str3) {
        if (this.radialProgressView.getTag() != null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setMessage(LocaleController.getString("ResetMyAccountWarningText", R.string.ResetMyAccountWarningText));
        builder.setTitle(LocaleController.getString("ResetMyAccountWarning", R.string.ResetMyAccountWarning));
        builder.setPositiveButton(LocaleController.getString("ResetMyAccountWarningReset", R.string.ResetMyAccountWarningReset), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LoginActivity.this.lambda$tryResetAccount$30(str, str2, str3, dialogInterface, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryResetAccount$30(final String str, final String str2, final String str3, DialogInterface dialogInterface, int i) {
        needShowProgress(0);
        TLRPC$TL_account_deleteAccount tLRPC$TL_account_deleteAccount = new TLRPC$TL_account_deleteAccount();
        tLRPC$TL_account_deleteAccount.reason = "Forgot password";
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_account_deleteAccount, new RequestDelegate() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda29
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LoginActivity.this.lambda$tryResetAccount$29(str, str2, str3, tLObject, tLRPC$TL_error);
            }
        }, 10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryResetAccount$29(final String str, final String str2, final String str3, TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                LoginActivity.this.lambda$tryResetAccount$28(tLRPC$TL_error, str, str2, str3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryResetAccount$28(TLRPC$TL_error tLRPC$TL_error, String str, String str2, String str3) {
        needHideProgress(false);
        if (tLRPC$TL_error == null) {
            if (str == null || str2 == null || str3 == null) {
                setPage(0, true, null, true);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("phoneFormated", str);
            bundle.putString("phoneHash", str2);
            bundle.putString("code", str3);
            setPage(5, true, bundle, false);
            return;
        }
        if (tLRPC$TL_error.text.equals("2FA_RECENT_CONFIRM")) {
            needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), LocaleController.getString("ResetAccountCancelledAlert", R.string.ResetAccountCancelledAlert));
            return;
        }
        if (tLRPC$TL_error.text.startsWith("2FA_CONFIRM_WAIT_")) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("phoneFormated", str);
            bundle2.putString("phoneHash", str2);
            bundle2.putString("code", str3);
            bundle2.putInt("startTime", ConnectionsManager.getInstance(this.currentAccount).getCurrentTime());
            bundle2.putInt("waitTime", Utilities.parseInt((CharSequence) tLRPC$TL_error.text.replace("2FA_CONFIRM_WAIT_", "")).intValue());
            setPage(8, true, bundle2, false);
            return;
        }
        needShowAlert(LocaleController.getString(R.string.RestorePasswordNoEmailTitle), tLRPC$TL_error.text);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        return ColorUtils.calculateLuminance(Theme.getColor(Theme.key_windowBackgroundWhite, null, true)) > 0.699999988079071d;
    }

    private void updateProxyButton(boolean z, boolean z2) {
        if (this.proxyDrawable == null) {
            return;
        }
        int connectionState = getConnectionsManager().getConnectionState();
        if (this.currentConnectionState != connectionState || z2) {
            this.currentConnectionState = connectionState;
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            boolean z3 = sharedPreferences.getBoolean("proxy_enabled", false) && !TextUtils.isEmpty(sharedPreferences.getString("proxy_ip", ""));
            int i = this.currentConnectionState;
            boolean z4 = i == 3 || i == 5;
            boolean z5 = i == 1 || i == 2 || i == 4;
            if (z3) {
                this.proxyDrawable.setConnected(true, z4, z);
                showProxyButton(true, z);
            } else if ((getMessagesController().blockedCountry && !SharedConfig.proxyList.isEmpty()) || z5) {
                this.proxyDrawable.setConnected(true, z4, z);
                showProxyButtonDelayed();
            } else {
                showProxyButton(false, z);
            }
        }
    }

    private void showProxyButtonDelayed() {
        if (this.proxyButtonVisible) {
            return;
        }
        Runnable runnable = this.showProxyButtonDelayed;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        this.proxyButtonVisible = true;
        Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                LoginActivity.this.lambda$showProxyButtonDelayed$31();
            }
        };
        this.showProxyButtonDelayed = runnable2;
        AndroidUtilities.runOnUIThread(runnable2, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProxyButtonDelayed$31() {
        this.proxyButtonVisible = false;
        showProxyButton(true, true);
    }

    private void showProxyButton(final boolean z, boolean z2) {
        if (z == this.proxyButtonVisible) {
            return;
        }
        Runnable runnable = this.showProxyButtonDelayed;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.showProxyButtonDelayed = null;
        }
        this.proxyButtonVisible = z;
        this.proxyButtonView.clearAnimation();
        if (z2) {
            this.proxyButtonView.setVisibility(8);
            this.proxyButtonView.animate().alpha(z ? 1.0f : 0.0f).withEndAction(new Runnable() { // from class: org.telegram.ui.LoginActivity$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    LoginActivity.this.lambda$showProxyButton$32(z);
                }
            }).start();
        } else {
            this.proxyButtonView.setVisibility(8);
            this.proxyButtonView.setAlpha(z ? 1.0f : 0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showProxyButton$32(boolean z) {
        if (z) {
            return;
        }
        this.proxyButtonView.setVisibility(8);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.didUpdateConnectionState) {
            updateProxyButton(true, false);
            return;
        }
        if (i == NotificationCenter.updateCurrentIpAndPort) {
            for (SlideView slideView : this.views) {
                if (slideView instanceof MergeUserNameView) {
                    ((MergeUserNameView) slideView).showVersion();
                } else if (slideView instanceof RegisterUserNameView) {
                    ((RegisterUserNameView) slideView).showVersion();
                } else if (slideView instanceof LoginUserNameView) {
                    ((LoginUserNameView) slideView).showVersion();
                }
            }
        }
    }
}
