package com.lxj.xpopup.core;

import android.R;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.OnLifecycleEvent;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.animator.EmptyAnimator;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.animator.ShadowBgAnimator;
import com.lxj.xpopup.animator.TranslateAlphaAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BasePopupView extends FrameLayout implements LifecycleObserver, LifecycleOwner, ViewCompat.OnUnhandledKeyEventListenerCompat {
    protected BlurAnimator blurAnimator;
    public FullScreenDialog dialog;
    Runnable dismissWithRunnable;
    protected Runnable doAfterDismissTask;
    protected Runnable doAfterShowTask;
    protected Handler handler;
    private boolean hasModifySoftMode;
    public boolean hasMoveUp;
    private final Runnable initTask;
    protected boolean isCreated;
    protected LifecycleRegistry lifecycleRegistry;
    protected PopupAnimator popupContentAnimator;
    public PopupInfo popupInfo;
    public PopupStatus popupStatus;
    private int preSoftMode;
    protected ShadowBgAnimator shadowBgAnimator;
    private ShowSoftInputTask showSoftInputTask;
    private final int touchSlop;
    private float x;
    private float y;

    protected void applyDarkTheme() {
    }

    protected void applyLightTheme() {
    }

    protected int getActivityContentLeft() {
        return 0;
    }

    protected int getImplLayoutId() {
        return -1;
    }

    protected abstract int getInnerLayoutId();

    protected List<String> getInternalFragmentNames() {
        return null;
    }

    protected PopupAnimator getPopupAnimator() {
        return null;
    }

    protected void initPopupContent() {
    }

    protected boolean onBackPressed() {
        return false;
    }

    protected void onCreate() {
    }

    protected void onKeyboardHeightChange(int height) {
    }

    public BasePopupView(Context context) {
        super(context);
        this.popupStatus = PopupStatus.Dismiss;
        this.isCreated = false;
        this.hasModifySoftMode = false;
        this.preSoftMode = -1;
        this.hasMoveUp = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.initTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.5
            @Override // java.lang.Runnable
            public void run() {
                XPopupCallback xPopupCallback;
                if (BasePopupView.this.getHostWindow() == null) {
                    return;
                }
                BasePopupView basePopupView = BasePopupView.this;
                PopupInfo popupInfo = basePopupView.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.beforeShow(basePopupView);
                }
                BasePopupView.this.beforeShow();
                BasePopupView.this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                BasePopupView basePopupView2 = BasePopupView.this;
                if (!(basePopupView2 instanceof FullScreenPopupView)) {
                    basePopupView2.focusAndProcessBackPress();
                }
                BasePopupView basePopupView3 = BasePopupView.this;
                if ((basePopupView3 instanceof AttachPopupView) || (basePopupView3 instanceof BubbleAttachPopupView) || (basePopupView3 instanceof PositionPopupView) || (basePopupView3 instanceof PartShadowPopupView)) {
                    return;
                }
                basePopupView3.initAnimator();
                BasePopupView.this.doShowAnimation();
                BasePopupView.this.doAfterShow();
            }
        };
        this.doAfterShowTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.6
            @Override // java.lang.Runnable
            public void run() {
                XPopupCallback xPopupCallback;
                BasePopupView basePopupView = BasePopupView.this;
                basePopupView.popupStatus = PopupStatus.Show;
                basePopupView.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
                BasePopupView.this.onShow();
                BasePopupView basePopupView2 = BasePopupView.this;
                if (basePopupView2 instanceof FullScreenPopupView) {
                    basePopupView2.focusAndProcessBackPress();
                }
                BasePopupView basePopupView3 = BasePopupView.this;
                PopupInfo popupInfo = basePopupView3.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.onShow(basePopupView3);
                }
                if (BasePopupView.this.getHostWindow() == null || XPopupUtils.getDecorViewInvisibleHeight(BasePopupView.this.getHostWindow()) <= 0) {
                    return;
                }
                BasePopupView basePopupView4 = BasePopupView.this;
                if (basePopupView4.hasMoveUp) {
                    return;
                }
                XPopupUtils.moveUpToKeyboard(XPopupUtils.getDecorViewInvisibleHeight(basePopupView4.getHostWindow()), BasePopupView.this);
            }
        };
        this.doAfterDismissTask = new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.9
            @Override // java.lang.Runnable
            public void run() {
                View findViewById;
                BasePopupView basePopupView = BasePopupView.this;
                basePopupView.popupStatus = PopupStatus.Dismiss;
                basePopupView.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                PopupInfo popupInfo = BasePopupView.this.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                if (popupInfo.autoOpenSoftInput.booleanValue()) {
                    BasePopupView basePopupView2 = BasePopupView.this;
                    if (basePopupView2 instanceof PartShadowPopupView) {
                        KeyboardUtils.hideSoftInput(basePopupView2);
                    }
                }
                BasePopupView.this.onDismiss();
                XPopup.longClickPoint = null;
                BasePopupView basePopupView3 = BasePopupView.this;
                XPopupCallback xPopupCallback = basePopupView3.popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDismiss(basePopupView3);
                }
                Runnable runnable = BasePopupView.this.dismissWithRunnable;
                if (runnable != null) {
                    runnable.run();
                    BasePopupView.this.dismissWithRunnable = null;
                }
                BasePopupView basePopupView4 = BasePopupView.this;
                PopupInfo popupInfo2 = basePopupView4.popupInfo;
                if (popupInfo2.isRequestFocus && popupInfo2.isViewMode && basePopupView4.getWindowDecorView() != null && (findViewById = BasePopupView.this.getWindowDecorView().findViewById(R.id.content)) != null) {
                    findViewById.setFocusable(true);
                    findViewById.setFocusableInTouchMode(true);
                }
                BasePopupView.this.detachFromHost();
            }
        };
        if (context instanceof Application) {
            throw new IllegalArgumentException("XPopup的Context必须是Activity类型！");
        }
        this.lifecycleRegistry = new LifecycleRegistry(this);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setId(View.generateViewId());
        View inflate = LayoutInflater.from(context).inflate(getInnerLayoutId(), (ViewGroup) this, false);
        inflate.setAlpha(0.0f);
        addView(inflate);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.lifecycleRegistry;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        init();
    }

    public BasePopupView show() {
        FullScreenDialog fullScreenDialog;
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return this;
        }
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            throw new IllegalArgumentException("popupInfo is null, if your popup object is reused, do not set isDestroyOnDismiss(true) !");
        }
        PopupStatus popupStatus = this.popupStatus;
        PopupStatus popupStatus2 = PopupStatus.Showing;
        if (popupStatus != popupStatus2 && popupStatus != PopupStatus.Dismissing) {
            this.popupStatus = popupStatus2;
            if (!popupInfo.isViewMode && (fullScreenDialog = this.dialog) != null && fullScreenDialog.isShowing()) {
                return this;
            }
            activity.getWindow().getDecorView().findViewById(R.id.content).post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.1
                @Override // java.lang.Runnable
                public void run() {
                    BasePopupView.this.attachToHost();
                }
            });
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attachToHost() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            throw new IllegalArgumentException("如果弹窗对象是复用的，则不要设置isDestroyOnDismiss(true)");
        }
        Lifecycle lifecycle = popupInfo.hostLifecycle;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        } else if (getContext() instanceof FragmentActivity) {
            ((FragmentActivity) getContext()).getLifecycle().addObserver(this);
        }
        doMeasure();
        if (this.popupInfo.isViewMode) {
            ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeView(this);
            }
            viewGroup.addView(this, getLayoutParams());
        } else {
            if (this.dialog == null) {
                this.dialog = new FullScreenDialog(getContext()).setContent(this);
            }
            Activity activity = getActivity();
            if (activity != null && !activity.isFinishing() && !this.dialog.isShowing()) {
                this.dialog.show();
            }
        }
        KeyboardUtils.registerSoftInputChangedListener(getHostWindow(), this, new KeyboardUtils.OnSoftInputChangedListener() { // from class: com.lxj.xpopup.core.BasePopupView.2
            @Override // com.lxj.xpopup.util.KeyboardUtils.OnSoftInputChangedListener
            public void onSoftInputChanged(int height) {
                XPopupCallback xPopupCallback;
                BasePopupView.this.onKeyboardHeightChange(height);
                BasePopupView basePopupView = BasePopupView.this;
                PopupInfo popupInfo2 = basePopupView.popupInfo;
                if (popupInfo2 != null && (xPopupCallback = popupInfo2.xPopupCallback) != null) {
                    xPopupCallback.onKeyBoardStateChanged(basePopupView, height);
                }
                if (height == 0) {
                    BasePopupView.this.post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            XPopupUtils.moveDown(BasePopupView.this);
                        }
                    });
                    BasePopupView.this.hasMoveUp = false;
                    return;
                }
                BasePopupView basePopupView2 = BasePopupView.this;
                if ((basePopupView2 instanceof PartShadowPopupView) && basePopupView2.popupStatus == PopupStatus.Showing) {
                    return;
                }
                XPopupUtils.moveUpToKeyboard(height, basePopupView2);
                BasePopupView.this.hasMoveUp = true;
            }
        });
    }

    protected Activity getActivity() {
        return XPopupUtils.context2Activity(getContext());
    }

    protected View getWindowDecorView() {
        if (getHostWindow() == null) {
            return null;
        }
        return (ViewGroup) getHostWindow().getDecorView();
    }

    public View getActivityContentView() {
        return ((ViewGroup) getActivity().getWindow().getDecorView()).getChildAt(0);
    }

    protected void doMeasure() {
        if (getActivity() == null) {
            return;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        View activityContentView = getActivityContentView();
        if (marginLayoutParams == null) {
            marginLayoutParams = new ViewGroup.MarginLayoutParams(activityContentView.getWidth(), activityContentView.getHeight());
        } else {
            marginLayoutParams.width = activityContentView.getWidth();
            marginLayoutParams.height = activityContentView.getHeight();
        }
        PopupInfo popupInfo = this.popupInfo;
        marginLayoutParams.leftMargin = (popupInfo == null || !popupInfo.isViewMode) ? 0 : activityContentView.getLeft();
        marginLayoutParams.topMargin = activityContentView.getTop();
        setLayoutParams(marginLayoutParams);
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        getActivityContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.3
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.doMeasure();
            }
        });
        return super.onApplyWindowInsets(insets);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getActivityContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.4
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.doMeasure();
            }
        });
    }

    protected void init() {
        if (this.shadowBgAnimator == null) {
            this.shadowBgAnimator = new ShadowBgAnimator(this, getAnimationDuration(), getShadowBgColor());
        }
        if (this.popupInfo.hasBlurBg.booleanValue()) {
            BlurAnimator blurAnimator = new BlurAnimator(this, getShadowBgColor());
            this.blurAnimator = blurAnimator;
            blurAnimator.hasShadowBg = this.popupInfo.hasShadowBg.booleanValue();
            this.blurAnimator.decorBitmap = XPopupUtils.view2Bitmap(getActivity().getWindow().getDecorView(), getActivityContentView().getHeight(), 5);
        }
        if ((this instanceof AttachPopupView) || (this instanceof BubbleAttachPopupView) || (this instanceof PartShadowPopupView) || (this instanceof PositionPopupView)) {
            initPopupContent();
        } else if (!this.isCreated) {
            initPopupContent();
        }
        if (!this.isCreated) {
            this.isCreated = true;
            onCreate();
            this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            XPopupCallback xPopupCallback = this.popupInfo.xPopupCallback;
            if (xPopupCallback != null) {
                xPopupCallback.onCreated(this);
            }
        }
        this.handler.post(this.initTask);
    }

    protected void initAnimator() {
        BlurAnimator blurAnimator;
        PopupAnimator popupAnimator;
        getPopupContentView().setAlpha(1.0f);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && (popupAnimator = popupInfo.customAnimator) != null) {
            this.popupContentAnimator = popupAnimator;
            if (popupAnimator.targetView == null) {
                popupAnimator.targetView = getPopupContentView();
            }
        } else {
            PopupAnimator genAnimatorByPopupType = genAnimatorByPopupType();
            this.popupContentAnimator = genAnimatorByPopupType;
            if (genAnimatorByPopupType == null) {
                this.popupContentAnimator = getPopupAnimator();
            }
        }
        PopupInfo popupInfo2 = this.popupInfo;
        if (popupInfo2 != null && popupInfo2.hasShadowBg.booleanValue()) {
            this.shadowBgAnimator.initAnimator();
        }
        PopupInfo popupInfo3 = this.popupInfo;
        if (popupInfo3 != null && popupInfo3.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.initAnimator();
        }
        PopupAnimator popupAnimator2 = this.popupContentAnimator;
        if (popupAnimator2 != null) {
            popupAnimator2.initAnimator();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void detachFromHost() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.isViewMode) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this);
                return;
            }
            return;
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog != null) {
            fullScreenDialog.dismiss();
        }
    }

    public Window getHostWindow() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.isViewMode) {
            Activity activity = getActivity();
            if (activity == null) {
                return null;
            }
            return activity.getWindow();
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog == null) {
            return null;
        }
        return fullScreenDialog.getWindow();
    }

    protected void doAfterShow() {
        this.handler.removeCallbacks(this.doAfterShowTask);
        this.handler.postDelayed(this.doAfterShowTask, getAnimationDuration());
    }

    public void focusAndProcessBackPress() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.isRequestFocus) {
            return;
        }
        setFocusableInTouchMode(true);
        setFocusable(true);
        if (Build.VERSION.SDK_INT >= 28) {
            addOnUnhandledKeyListener(this);
        } else {
            setOnKeyListener(new BackPressListener());
        }
        ArrayList arrayList = new ArrayList();
        XPopupUtils.findAllEditText(arrayList, (ViewGroup) getPopupContentView());
        if (arrayList.size() > 0) {
            this.preSoftMode = getHostWindow().getAttributes().softInputMode;
            if (this.popupInfo.isViewMode) {
                getHostWindow().setSoftInputMode(16);
                this.hasModifySoftMode = true;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                EditText editText = (EditText) arrayList.get(i);
                if (Build.VERSION.SDK_INT >= 28) {
                    addOnUnhandledKeyListener(editText);
                } else if (!XPopupUtils.hasSetKeyListener(editText)) {
                    editText.setOnKeyListener(new BackPressListener());
                }
                if (i == 0) {
                    PopupInfo popupInfo2 = this.popupInfo;
                    if (popupInfo2.autoFocusEditText) {
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
                            showSoftInput(editText);
                        }
                    } else if (popupInfo2.autoOpenSoftInput.booleanValue()) {
                        showSoftInput(this);
                    }
                }
            }
            return;
        }
        if (this.popupInfo.autoOpenSoftInput.booleanValue()) {
            showSoftInput(this);
        }
    }

    @Override // androidx.core.view.ViewCompat.OnUnhandledKeyEventListenerCompat
    public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
        return processKeyEvent(event.getKeyCode(), event);
    }

    protected void addOnUnhandledKeyListener(View view) {
        ViewCompat.removeOnUnhandledKeyEventListener(view, this);
        ViewCompat.addOnUnhandledKeyEventListener(view, this);
    }

    protected void showSoftInput(View focusView) {
        if (this.popupInfo != null) {
            ShowSoftInputTask showSoftInputTask = this.showSoftInputTask;
            if (showSoftInputTask == null) {
                this.showSoftInputTask = new ShowSoftInputTask(focusView);
            } else {
                this.handler.removeCallbacks(showSoftInputTask);
            }
            this.handler.postDelayed(this.showSoftInputTask, 10L);
        }
    }

    public void dismissOrHideSoftInput() {
        if (XPopupUtils.getDecorViewInvisibleHeight(getHostWindow()) == 0) {
            dismiss();
        } else {
            KeyboardUtils.hideSoftInput(this);
        }
    }

    static class ShowSoftInputTask implements Runnable {
        View focusView;

        public ShowSoftInputTask(View focusView) {
            this.focusView = focusView;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view = this.focusView;
            if (view != null) {
                KeyboardUtils.showSoftInput(view);
            }
        }
    }

    protected boolean processKeyEvent(int keyCode, KeyEvent event) {
        XPopupCallback xPopupCallback;
        if (keyCode != 4 || event.getAction() != 1 || this.popupInfo == null) {
            return false;
        }
        if (!onBackPressed() && this.popupInfo.isDismissOnBackPressed.booleanValue() && ((xPopupCallback = this.popupInfo.xPopupCallback) == null || !xPopupCallback.onBackPressed(this))) {
            dismissOrHideSoftInput();
        }
        return true;
    }

    class BackPressListener implements View.OnKeyListener {
        BackPressListener() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            return BasePopupView.this.processKeyEvent(keyCode, event);
        }
    }

    /* renamed from: com.lxj.xpopup.core.BasePopupView$10, reason: invalid class name */
    static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$com$lxj$xpopup$enums$PopupAnimation;

        static {
            int[] iArr = new int[PopupAnimation.values().length];
            $SwitchMap$com$lxj$xpopup$enums$PopupAnimation = iArr;
            try {
                iArr[PopupAnimation.ScaleAlphaFromCenter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightTop.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromLeftBottom.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScaleAlphaFromRightBottom.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromLeft.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromTop.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromRight.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateAlphaFromBottom.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromLeft.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromTop.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromRight.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.TranslateFromBottom.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeft.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftTop.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromTop.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightTop.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRight.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromRightBottom.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromBottom.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.ScrollAlphaFromLeftBottom.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$lxj$xpopup$enums$PopupAnimation[PopupAnimation.NoAnimation.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    protected PopupAnimator genAnimatorByPopupType() {
        PopupAnimation popupAnimation;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || (popupAnimation = popupInfo.popupAnimation) == null) {
            return null;
        }
        switch (AnonymousClass10.$SwitchMap$com$lxj$xpopup$enums$PopupAnimation[popupAnimation.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 6:
            case 7:
            case 8:
            case 9:
                return new TranslateAlphaAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 10:
            case 11:
            case 12:
            case 13:
                return new TranslateAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.popupInfo.popupAnimation);
            case 22:
                return new EmptyAnimator(getPopupContentView(), getAnimationDuration());
            default:
                return null;
        }
    }

    protected void doShowAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateShow();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateShow();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateShow();
        }
    }

    protected void doDismissAnimation() {
        BlurAnimator blurAnimator;
        ShadowBgAnimator shadowBgAnimator;
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.hasShadowBg.booleanValue() && !this.popupInfo.hasBlurBg.booleanValue() && (shadowBgAnimator = this.shadowBgAnimator) != null) {
            shadowBgAnimator.animateDismiss();
        } else if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateDismiss();
        }
        PopupAnimator popupAnimator = this.popupContentAnimator;
        if (popupAnimator != null) {
            popupAnimator.animateDismiss();
        }
    }

    public View getPopupContentView() {
        return getChildAt(0);
    }

    public View getPopupImplView() {
        return ((ViewGroup) getPopupContentView()).getChildAt(0);
    }

    public int getAnimationDuration() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        if (popupInfo.popupAnimation == PopupAnimation.NoAnimation) {
            return 1;
        }
        int i = popupInfo.animationDuration;
        return i >= 0 ? i : XPopup.getAnimationDuration() + 1;
    }

    public int getShadowBgColor() {
        int i;
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || (i = popupInfo.shadowBgColor) == 0) ? XPopup.getShadowBgColor() : i;
    }

    public int getStatusBarBgColor() {
        int i;
        PopupInfo popupInfo = this.popupInfo;
        return (popupInfo == null || (i = popupInfo.statusBarBgColor) == 0) ? XPopup.getStatusBarBgColor() : i;
    }

    protected int getMaxWidth() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        return popupInfo.maxWidth;
    }

    protected int getMaxHeight() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        return popupInfo.maxHeight;
    }

    protected int getPopupWidth() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        return popupInfo.popupWidth;
    }

    protected int getPopupHeight() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        return popupInfo.popupHeight;
    }

    public void dismiss() {
        XPopupCallback xPopupCallback;
        this.handler.removeCallbacks(this.initTask);
        PopupStatus popupStatus = this.popupStatus;
        PopupStatus popupStatus2 = PopupStatus.Dismissing;
        if (popupStatus == popupStatus2 || popupStatus == PopupStatus.Dismiss) {
            return;
        }
        this.popupStatus = popupStatus2;
        clearFocus();
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
            xPopupCallback.beforeDismiss(this);
        }
        beforeDismiss();
        this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        doDismissAnimation();
        doAfterDismiss();
    }

    public void delayDismiss(long delay) {
        if (delay < 0) {
            delay = 0;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.lxj.xpopup.core.BasePopupView.8
            @Override // java.lang.Runnable
            public void run() {
                BasePopupView.this.dismiss();
            }
        }, delay);
    }

    protected void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.autoOpenSoftInput.booleanValue() && !(this instanceof PartShadowPopupView)) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, getAnimationDuration());
    }

    protected void tryRemoveFragments() {
        if (getContext() instanceof FragmentActivity) {
            FragmentManager supportFragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            List<String> internalFragmentNames = getInternalFragmentNames();
            if (fragments == null || fragments.size() <= 0 || internalFragmentNames == null) {
                return;
            }
            for (int i = 0; i < fragments.size(); i++) {
                if (internalFragmentNames.contains(fragments.get(i).getClass().getSimpleName())) {
                    supportFragmentManager.beginTransaction().remove(fragments.get(i)).commitAllowingStateLoss();
                }
            }
        }
    }

    protected void onDismiss() {
        Log.d("tag", "onDismiss");
    }

    protected void beforeDismiss() {
        Log.d("tag", "beforeDismiss");
    }

    protected void beforeShow() {
        Log.d("tag", "beforeShow");
    }

    protected void onShow() {
        Log.d("tag", "onShow");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        onDetachedFromWindow();
        detachFromHost();
        destroy();
    }

    public void destroy() {
        View view;
        View view2;
        ViewCompat.removeOnUnhandledKeyEventListener(this, this);
        if (this.isCreated) {
            this.lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
        }
        this.lifecycleRegistry.removeObserver(this);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null) {
            popupInfo.atView = null;
            popupInfo.xPopupCallback = null;
            Lifecycle lifecycle = popupInfo.hostLifecycle;
            if (lifecycle != null) {
                lifecycle.removeObserver(this);
                this.popupInfo.hostLifecycle = null;
            }
            PopupAnimator popupAnimator = this.popupInfo.customAnimator;
            if (popupAnimator != null) {
                View view3 = popupAnimator.targetView;
                if (view3 != null) {
                    view3.animate().cancel();
                    this.popupInfo.customAnimator.targetView = null;
                }
                this.popupInfo.customAnimator = null;
            }
            if (this.popupInfo.isViewMode) {
                tryRemoveFragments();
            }
            this.popupInfo = null;
        }
        FullScreenDialog fullScreenDialog = this.dialog;
        if (fullScreenDialog != null) {
            if (fullScreenDialog.isShowing()) {
                this.dialog.dismiss();
            }
            this.dialog.contentView = null;
            this.dialog = null;
        }
        ShadowBgAnimator shadowBgAnimator = this.shadowBgAnimator;
        if (shadowBgAnimator != null && (view2 = shadowBgAnimator.targetView) != null) {
            view2.animate().cancel();
        }
        BlurAnimator blurAnimator = this.blurAnimator;
        if (blurAnimator == null || (view = blurAnimator.targetView) == null) {
            return;
        }
        view.animate().cancel();
        Bitmap bitmap = this.blurAnimator.decorBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.blurAnimator.decorBitmap.recycle();
        this.blurAnimator.decorBitmap = null;
    }

    protected int getStatusBarHeight() {
        return XPopupUtils.getStatusBarHeight(getHostWindow());
    }

    protected int getNavBarHeight() {
        return XPopupUtils.getNavBarHeight(getHostWindow());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        Lifecycle lifecycle;
        super.onDetachedFromWindow();
        if (getWindowDecorView() != null) {
            KeyboardUtils.removeLayoutChangeListener(getHostWindow(), this);
        }
        this.handler.removeCallbacksAndMessages(null);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null) {
            if (popupInfo.isViewMode && this.hasModifySoftMode) {
                getHostWindow().setSoftInputMode(this.preSoftMode);
                this.hasModifySoftMode = false;
            }
            if (this.popupInfo.isDestroyOnDismiss) {
                destroy();
            }
        }
        PopupInfo popupInfo2 = this.popupInfo;
        if (popupInfo2 != null && (lifecycle = popupInfo2.hostLifecycle) != null) {
            lifecycle.removeObserver(this);
        } else if (getContext() != null && (getContext() instanceof FragmentActivity)) {
            ((FragmentActivity) getContext()).getLifecycle().removeObserver(this);
        }
        this.popupStatus = PopupStatus.Dismiss;
        this.showSoftInputTask = null;
        this.hasMoveUp = false;
    }

    public void passTouchThrough(MotionEvent event) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null) {
            if (popupInfo.isClickThrough || popupInfo.isTouchThrough) {
                if (popupInfo.isViewMode) {
                    ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        View childAt = viewGroup.getChildAt(i);
                        if (!(childAt instanceof BasePopupView)) {
                            childAt.dispatchTouchEvent(event);
                        }
                    }
                    return;
                }
                getActivity().dispatchTouchEvent(event);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0027, code lost:
    
        if (r0 != 3) goto L34;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            r9 = this;
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            android.view.View r1 = r9.getPopupImplView()
            r1.getGlobalVisibleRect(r0)
            float r1 = r10.getX()
            float r2 = r10.getY()
            boolean r0 = com.lxj.xpopup.util.XPopupUtils.isInRect(r1, r2, r0)
            r1 = 1
            if (r0 != 0) goto L9d
            int r0 = r10.getAction()
            if (r0 == 0) goto L83
            if (r0 == r1) goto L44
            r2 = 2
            if (r0 == r2) goto L2b
            r2 = 3
            if (r0 == r2) goto L44
            goto L9d
        L2b:
            com.lxj.xpopup.core.PopupInfo r0 = r9.popupInfo
            if (r0 == 0) goto L9d
            java.lang.Boolean r0 = r0.isDismissOnTouchOutside
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L3a
            r9.checkDismissArea(r10)
        L3a:
            com.lxj.xpopup.core.PopupInfo r0 = r9.popupInfo
            boolean r0 = r0.isTouchThrough
            if (r0 == 0) goto L9d
            r9.passTouchThrough(r10)
            goto L9d
        L44:
            float r0 = r10.getX()
            float r2 = r9.x
            float r0 = r0 - r2
            float r2 = r10.getY()
            float r3 = r9.y
            float r2 = r2 - r3
            double r3 = (double) r0
            r5 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r3 = java.lang.Math.pow(r3, r5)
            double r7 = (double) r2
            double r5 = java.lang.Math.pow(r7, r5)
            double r3 = r3 + r5
            double r2 = java.lang.Math.sqrt(r3)
            float r0 = (float) r2
            r9.passTouchThrough(r10)
            int r2 = r9.touchSlop
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L7d
            com.lxj.xpopup.core.PopupInfo r0 = r9.popupInfo
            if (r0 == 0) goto L7d
            java.lang.Boolean r0 = r0.isDismissOnTouchOutside
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L7d
            r9.checkDismissArea(r10)
        L7d:
            r10 = 0
            r9.x = r10
            r9.y = r10
            goto L9d
        L83:
            float r0 = r10.getX()
            r9.x = r0
            float r0 = r10.getY()
            r9.y = r0
            com.lxj.xpopup.core.PopupInfo r0 = r9.popupInfo
            if (r0 == 0) goto L9a
            com.lxj.xpopup.interfaces.XPopupCallback r0 = r0.xPopupCallback
            if (r0 == 0) goto L9a
            r0.onClickOutside(r9)
        L9a:
            r9.passTouchThrough(r10)
        L9d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.core.BasePopupView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void checkDismissArea(MotionEvent event) {
        ArrayList<Rect> arrayList = this.popupInfo.notDismissWhenTouchInArea;
        if (arrayList != null && arrayList.size() > 0) {
            boolean z = false;
            Iterator<Rect> it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (XPopupUtils.isInRect(event.getX(), event.getY(), it.next())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                return;
            }
            dismiss();
            return;
        }
        dismiss();
    }
}
