package com.hjq.window;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hjq.window.EasyWindow;
import com.hjq.window.ScreenOrientationMonitor;
import com.hjq.window.draggable.BaseDraggable;
import com.hjq.window.draggable.MovingDraggable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class EasyWindow<X extends EasyWindow<?>> implements Runnable, ScreenOrientationMonitor.OnScreenOrientationCallback {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static final List<EasyWindow<?>> sWindowInstanceSet = new ArrayList();
    private Context mContext;
    private ViewGroup mDecorView;
    private BaseDraggable mDraggable;
    private int mDuration;
    private ActivityWindowLifecycle mLifecycle;
    private OnWindowLifecycle mListener;
    private ScreenOrientationMonitor mScreenOrientationMonitor;
    private boolean mShowing;
    private String mTag;
    private final Runnable mUpdateRunnable;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;

    public interface OnClickListener<V extends View> {
        void onClick(EasyWindow<?> easyWindow, V v);
    }

    public interface OnLongClickListener<V extends View> {
        boolean onLongClick(EasyWindow<?> easyWindow, V v);
    }

    public interface OnTouchListener<V extends View> {
        boolean onTouch(EasyWindow<?> easyWindow, V v, MotionEvent motionEvent);
    }

    public interface OnWindowLifecycle {

        /* renamed from: com.hjq.window.EasyWindow$OnWindowLifecycle$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onWindowCancel(OnWindowLifecycle onWindowLifecycle, EasyWindow easyWindow) {
            }

            public static void $default$onWindowRecycle(OnWindowLifecycle onWindowLifecycle, EasyWindow easyWindow) {
            }

            public static void $default$onWindowShow(OnWindowLifecycle onWindowLifecycle, EasyWindow easyWindow) {
            }
        }

        void onWindowCancel(EasyWindow<?> easyWindow);

        void onWindowRecycle(EasyWindow<?> easyWindow);

        void onWindowShow(EasyWindow<?> easyWindow);
    }

    public static EasyWindow with(Activity activity) {
        return new EasyWindow(activity);
    }

    public static EasyWindow with(Application application) {
        return new EasyWindow(application);
    }

    public static synchronized void cancelAll() {
        synchronized (EasyWindow.class) {
            for (EasyWindow<?> easyWindow : sWindowInstanceSet) {
                if (easyWindow != null) {
                    easyWindow.cancel();
                }
            }
        }
    }

    public static synchronized void cancelByClass(Class<EasyWindow<?>> cls) {
        synchronized (EasyWindow.class) {
            if (cls == null) {
                return;
            }
            for (EasyWindow<?> easyWindow : sWindowInstanceSet) {
                if (easyWindow != null && cls.equals(easyWindow.getClass())) {
                    easyWindow.cancel();
                }
            }
        }
    }

    public static synchronized void cancelByTag(String str) {
        synchronized (EasyWindow.class) {
            if (str == null) {
                return;
            }
            for (EasyWindow<?> easyWindow : sWindowInstanceSet) {
                if (easyWindow != null && str.equals(easyWindow.getTag())) {
                    easyWindow.cancel();
                }
            }
        }
    }

    public static synchronized void recycleAll() {
        synchronized (EasyWindow.class) {
            Iterator<EasyWindow<?>> it = sWindowInstanceSet.iterator();
            while (it.hasNext()) {
                EasyWindow<?> next = it.next();
                if (next != null) {
                    it.remove();
                    next.recycle();
                }
            }
        }
    }

    public static void recycleByClass(Class<EasyWindow<?>> cls) {
        if (cls == null) {
            return;
        }
        Iterator<EasyWindow<?>> it = sWindowInstanceSet.iterator();
        while (it.hasNext()) {
            EasyWindow<?> next = it.next();
            if (next != null && cls.equals(next.getClass())) {
                it.remove();
                next.recycle();
            }
        }
    }

    public static void recycleByTag(String str) {
        if (str == null) {
            return;
        }
        Iterator<EasyWindow<?>> it = sWindowInstanceSet.iterator();
        while (it.hasNext()) {
            EasyWindow<?> next = it.next();
            if (next != null && str.equals(next.getTag())) {
                it.remove();
                next.recycle();
            }
        }
    }

    public EasyWindow(Activity activity) {
        this((Context) activity);
        View decorView = activity.getWindow().getDecorView();
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        if ((attributes.flags & 1024) != 0 || (decorView.getSystemUiVisibility() & 4) != 0) {
            addWindowFlags(1024);
        }
        if (Build.VERSION.SDK_INT >= 28) {
            setLayoutInDisplayCutoutMode(attributes.layoutInDisplayCutoutMode);
        }
        int i = attributes.systemUiVisibility;
        if (i != 0) {
            setSystemUiVisibility(i);
        }
        if (decorView.getSystemUiVisibility() != 0) {
            this.mDecorView.setSystemUiVisibility(decorView.getSystemUiVisibility());
        }
        ActivityWindowLifecycle activityWindowLifecycle = new ActivityWindowLifecycle(this, activity);
        this.mLifecycle = activityWindowLifecycle;
        activityWindowLifecycle.register();
    }

    public EasyWindow(Application application) {
        this((Context) application);
        if (Build.VERSION.SDK_INT >= 26) {
            setWindowType(2038);
        } else {
            setWindowType(2003);
        }
    }

    private EasyWindow(Context context) {
        this.mUpdateRunnable = new Runnable() { // from class: com.hjq.window.EasyWindow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EasyWindow.this.update();
            }
        };
        this.mContext = context;
        this.mDecorView = new WindowLayout(context);
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.mWindowParams = layoutParams;
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.windowAnimations = android.R.style.Animation.Toast;
        layoutParams.packageName = context.getPackageName();
        this.mWindowParams.flags = 40;
        sWindowInstanceSet.add(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setTag(String str) {
        this.mTag = str;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWidth(int i) {
        View childAt;
        ViewGroup.LayoutParams layoutParams;
        this.mWindowParams.width = i;
        if (this.mDecorView.getChildCount() > 0 && (layoutParams = (childAt = this.mDecorView.getChildAt(0)).getLayoutParams()) != null && layoutParams.width != i) {
            layoutParams.width = i;
            childAt.setLayoutParams(layoutParams);
        }
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setHeight(int i) {
        View childAt;
        ViewGroup.LayoutParams layoutParams;
        this.mWindowParams.height = i;
        if (this.mDecorView.getChildCount() > 0 && (layoutParams = (childAt = this.mDecorView.getChildAt(0)).getLayoutParams()) != null && layoutParams.height != i) {
            layoutParams.height = i;
            childAt.setLayoutParams(layoutParams);
        }
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setGravity(int i) {
        this.mWindowParams.gravity = i;
        postUpdate();
        post(new Runnable() { // from class: com.hjq.window.EasyWindow$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EasyWindow.this.lambda$setGravity$0();
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGravity$0() {
        BaseDraggable baseDraggable = this.mDraggable;
        if (baseDraggable != null) {
            baseDraggable.refreshLocationCoordinate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setXOffset(int i) {
        this.mWindowParams.x = i;
        postUpdate();
        post(new Runnable() { // from class: com.hjq.window.EasyWindow$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                EasyWindow.this.lambda$setXOffset$1();
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setXOffset$1() {
        BaseDraggable baseDraggable = this.mDraggable;
        if (baseDraggable != null) {
            baseDraggable.refreshLocationCoordinate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setYOffset(int i) {
        this.mWindowParams.y = i;
        postUpdate();
        post(new Runnable() { // from class: com.hjq.window.EasyWindow$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EasyWindow.this.lambda$setYOffset$2();
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setYOffset$2() {
        BaseDraggable baseDraggable = this.mDraggable;
        if (baseDraggable != null) {
            baseDraggable.refreshLocationCoordinate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setOutsideTouchable(boolean z) {
        if (z) {
            addWindowFlags(40);
        } else {
            removeWindowFlags(40);
        }
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setBackgroundDimAmount(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("amount must be a value between 0 and 1");
        }
        this.mWindowParams.dimAmount = f;
        if (f != 0.0f) {
            addWindowFlags(2);
        } else {
            removeWindowFlags(2);
        }
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X addWindowFlags(int i) {
        WindowManager.LayoutParams layoutParams = this.mWindowParams;
        layoutParams.flags = i | layoutParams.flags;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X removeWindowFlags(int i) {
        WindowManager.LayoutParams layoutParams = this.mWindowParams;
        layoutParams.flags = (~i) & layoutParams.flags;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowFlags(int i) {
        this.mWindowParams.flags = i;
        postUpdate();
        return this;
    }

    public boolean hasWindowFlags(int i) {
        return (i & this.mWindowParams.flags) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowType(int i) {
        this.mWindowParams.type = i;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setAnimStyle(int i) {
        this.mWindowParams.windowAnimations = i;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setSoftInputMode(int i) {
        this.mWindowParams.softInputMode = i;
        removeWindowFlags(8);
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowToken(IBinder iBinder) {
        this.mWindowParams.token = iBinder;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowAlpha(float f) {
        this.mWindowParams.alpha = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setVerticalMargin(float f) {
        this.mWindowParams.verticalMargin = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setHorizontalMargin(float f) {
        this.mWindowParams.horizontalMargin = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setBitmapFormat(int i) {
        this.mWindowParams.format = i;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setSystemUiVisibility(int i) {
        this.mWindowParams.systemUiVisibility = i;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setVerticalWeight(float f) {
        this.mWindowParams.verticalWeight = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setLayoutInDisplayCutoutMode(int i) {
        if (Build.VERSION.SDK_INT >= 28) {
            this.mWindowParams.layoutInDisplayCutoutMode = i;
            postUpdate();
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setPreferredDisplayModeId(int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mWindowParams.preferredDisplayModeId = i;
            postUpdate();
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowTitle(CharSequence charSequence) {
        this.mWindowParams.setTitle(charSequence);
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setScreenBrightness(float f) {
        this.mWindowParams.screenBrightness = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setButtonBrightness(float f) {
        this.mWindowParams.buttonBrightness = f;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setPreferredRefreshRate(float f) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mWindowParams.preferredRefreshRate = f;
            postUpdate();
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setColorMode(int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mWindowParams.setColorMode(i);
            postUpdate();
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setBlurBehindRadius(int i) {
        if (Build.VERSION.SDK_INT >= 31) {
            this.mWindowParams.setBlurBehindRadius(i);
            addWindowFlags(4);
            postUpdate();
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setScreenOrientation(int i) {
        this.mWindowParams.screenOrientation = i;
        postUpdate();
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setWindowParams(WindowManager.LayoutParams layoutParams) {
        this.mWindowParams = layoutParams;
        postUpdate();
        return this;
    }

    public X setDraggable() {
        return setDraggable(new MovingDraggable());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setDraggable(BaseDraggable baseDraggable) {
        this.mDraggable = baseDraggable;
        if (baseDraggable != null) {
            removeWindowFlags(16);
            removeWindowFlags(LiteMode.FLAG_CALLS_ANIMATIONS);
            if (isShowing()) {
                update();
                baseDraggable.start(this);
            }
        }
        if (this.mScreenOrientationMonitor == null) {
            this.mScreenOrientationMonitor = new ScreenOrientationMonitor(this.mContext.getResources().getConfiguration());
        }
        this.mScreenOrientationMonitor.registerCallback(this.mContext, this);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setDuration(int i) {
        this.mDuration = i;
        if (isShowing() && this.mDuration != 0) {
            removeCallbacks(this);
            postDelayed(this, this.mDuration);
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setOnToastLifecycle(OnWindowLifecycle onWindowLifecycle) {
        this.mListener = onWindowLifecycle;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setDecorView(ViewGroup viewGroup) {
        this.mDecorView = viewGroup;
        return this;
    }

    public X setContentView(int i) {
        return setContentView(LayoutInflater.from(this.mContext).inflate(i, this.mDecorView, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setContentView(View view) {
        int i;
        if (this.mDecorView.getChildCount() > 0) {
            this.mDecorView.removeAllViews();
        }
        this.mDecorView.addView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin = 0;
            marginLayoutParams.bottomMargin = 0;
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
        }
        WindowManager.LayoutParams layoutParams2 = this.mWindowParams;
        if (layoutParams2.gravity == 0) {
            if (layoutParams instanceof FrameLayout.LayoutParams) {
                int i2 = ((FrameLayout.LayoutParams) layoutParams).gravity;
                if (i2 != -1) {
                    layoutParams2.gravity = i2;
                }
            } else if ((layoutParams instanceof LinearLayout.LayoutParams) && (i = ((LinearLayout.LayoutParams) layoutParams).gravity) != -1) {
                layoutParams2.gravity = i;
            }
            if (layoutParams2.gravity == 0) {
                layoutParams2.gravity = 17;
            }
        }
        if (layoutParams != null) {
            int i3 = layoutParams2.width;
            if (i3 == -2 && layoutParams2.height == -2) {
                layoutParams2.width = layoutParams.width;
                layoutParams2.height = layoutParams.height;
            } else {
                layoutParams.width = i3;
                layoutParams.height = layoutParams2.height;
            }
        }
        postUpdate();
        return this;
    }

    public void showAsDropDown(View view) {
        showAsDropDown(view, 80);
    }

    public void showAsDropDown(View view, int i) {
        showAsDropDown(view, i, 0, 0);
    }

    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (this.mDecorView.getChildCount() == 0 || this.mWindowParams == null) {
            throw new IllegalArgumentException("WindowParams and view cannot be empty");
        }
        if (Build.VERSION.SDK_INT >= 17) {
            i = Gravity.getAbsoluteGravity(i, view.getResources().getConfiguration().getLayoutDirection());
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        WindowManager.LayoutParams layoutParams = this.mWindowParams;
        layoutParams.gravity = 8388659;
        int i4 = (iArr[0] - rect.left) + i2;
        layoutParams.x = i4;
        layoutParams.y = (iArr[1] - rect.top) + i3;
        if ((i & 3) == 3) {
            int width = this.mDecorView.getWidth();
            if (width == 0) {
                width = this.mDecorView.getMeasuredWidth();
            }
            if (width == 0) {
                this.mDecorView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                width = this.mDecorView.getMeasuredWidth();
            }
            this.mWindowParams.x -= width;
        } else if ((i & 5) == 5) {
            layoutParams.x = i4 + view.getWidth();
        }
        if ((i & 48) == 48) {
            int height = this.mDecorView.getHeight();
            if (height == 0) {
                height = this.mDecorView.getMeasuredHeight();
            }
            if (height == 0) {
                this.mDecorView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
                height = this.mDecorView.getMeasuredHeight();
            }
            this.mWindowParams.y -= height;
        } else if ((i & 80) == 80) {
            this.mWindowParams.y += view.getHeight();
        }
        show();
    }

    public void show() {
        if (this.mDecorView.getChildCount() == 0 || this.mWindowParams == null) {
            throw new IllegalArgumentException("WindowParams and view cannot be empty");
        }
        if (this.mShowing) {
            update();
            return;
        }
        Context context = this.mContext;
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isFinishing()) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 17 && activity.isDestroyed()) {
                return;
            }
        }
        try {
            if (this.mDecorView.getParent() != null) {
                this.mWindowManager.removeViewImmediate(this.mDecorView);
            }
            this.mWindowManager.addView(this.mDecorView, this.mWindowParams);
            this.mShowing = true;
            if (this.mDuration != 0) {
                removeCallbacks(this);
                postDelayed(this, this.mDuration);
            }
            BaseDraggable baseDraggable = this.mDraggable;
            if (baseDraggable != null) {
                baseDraggable.start(this);
            }
            OnWindowLifecycle onWindowLifecycle = this.mListener;
            if (onWindowLifecycle != null) {
                onWindowLifecycle.onWindowShow(this);
            }
        } catch (WindowManager.BadTokenException | IllegalArgumentException | IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        if (this.mShowing) {
            try {
                try {
                    this.mWindowManager.removeViewImmediate(this.mDecorView);
                    removeCallbacks(this);
                    OnWindowLifecycle onWindowLifecycle = this.mListener;
                    if (onWindowLifecycle != null) {
                        onWindowLifecycle.onWindowCancel(this);
                    }
                } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
                    e.printStackTrace();
                }
            } finally {
                this.mShowing = false;
            }
        }
    }

    public void postUpdate() {
        if (isShowing()) {
            removeCallbacks(this.mUpdateRunnable);
            post(this.mUpdateRunnable);
        }
    }

    public void update() {
        if (isShowing()) {
            try {
                this.mWindowManager.updateViewLayout(this.mDecorView, this.mWindowParams);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void recycle() {
        if (isShowing()) {
            cancel();
        }
        ScreenOrientationMonitor screenOrientationMonitor = this.mScreenOrientationMonitor;
        if (screenOrientationMonitor != null) {
            screenOrientationMonitor.unregisterCallback(this.mContext);
        }
        OnWindowLifecycle onWindowLifecycle = this.mListener;
        if (onWindowLifecycle != null) {
            onWindowLifecycle.onWindowRecycle(this);
        }
        ActivityWindowLifecycle activityWindowLifecycle = this.mLifecycle;
        if (activityWindowLifecycle != null) {
            activityWindowLifecycle.unregister();
        }
        this.mListener = null;
        this.mContext = null;
        this.mDecorView = null;
        this.mWindowManager = null;
        this.mWindowParams = null;
        this.mLifecycle = null;
        this.mDraggable = null;
        this.mScreenOrientationMonitor = null;
        sWindowInstanceSet.remove(this);
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public WindowManager.LayoutParams getWindowParams() {
        return this.mWindowParams;
    }

    public BaseDraggable getDraggable() {
        return this.mDraggable;
    }

    public Context getContext() {
        return this.mContext;
    }

    public View getDecorView() {
        return this.mDecorView;
    }

    public View getContentView() {
        if (this.mDecorView.getChildCount() == 0) {
            return null;
        }
        return this.mDecorView.getChildAt(0);
    }

    public <V extends View> V findViewById(int i) {
        return (V) this.mDecorView.findViewById(i);
    }

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this.mContext, cls));
    }

    public void startActivity(Intent intent) {
        if (!(this.mContext instanceof Activity)) {
            intent.addFlags(268435456);
        }
        this.mContext.startActivity(intent);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setVisibility(int i, int i2) {
        findViewById(i).setVisibility(i2);
        return this;
    }

    public X setText(int i) {
        return setText(android.R.id.message, i);
    }

    public X setText(int i, int i2) {
        return setText(i, this.mContext.getResources().getString(i2));
    }

    public X setText(CharSequence charSequence) {
        return setText(android.R.id.message, charSequence);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setText(int i, CharSequence charSequence) {
        ((TextView) findViewById(i)).setText(charSequence);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setTextColor(int i, int i2) {
        ((TextView) findViewById(i)).setTextColor(i2);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setTextSize(int i, float f) {
        ((TextView) findViewById(i)).setTextSize(f);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setTextSize(int i, int i2, float f) {
        ((TextView) findViewById(i)).setTextSize(i2, f);
        return this;
    }

    public X setHint(int i, int i2) {
        return setHint(i, this.mContext.getResources().getString(i2));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setHint(int i, CharSequence charSequence) {
        ((TextView) findViewById(i)).setHint(charSequence);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setHintColor(int i, int i2) {
        ((TextView) findViewById(i)).setHintTextColor(i2);
        return this;
    }

    public X setBackground(int i, int i2) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = this.mContext.getDrawable(i2);
        } else {
            drawable = this.mContext.getResources().getDrawable(i2);
        }
        return setBackground(i, drawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setBackground(int i, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            findViewById(i).setBackground(drawable);
        } else {
            findViewById(i).setBackgroundDrawable(drawable);
        }
        return this;
    }

    public X setImageDrawable(int i, int i2) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = this.mContext.getDrawable(i2);
        } else {
            drawable = this.mContext.getResources().getDrawable(i2);
        }
        return setImageDrawable(i, drawable);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public X setImageDrawable(int i, Drawable drawable) {
        ((ImageView) findViewById(i)).setImageDrawable(drawable);
        return this;
    }

    public Handler getHandler() {
        return HANDLER;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean post(Runnable runnable) {
        return postDelayed(runnable, 0L);
    }

    public boolean postDelayed(Runnable runnable, long j) {
        if (j < 0) {
            j = 0;
        }
        return postAtTime(runnable, SystemClock.uptimeMillis() + j);
    }

    public boolean postAtTime(Runnable runnable, long j) {
        return HANDLER.postAtTime(runnable, this, j);
    }

    public void removeCallbacks(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

    public void removeCallbacksAndMessages() {
        HANDLER.removeCallbacksAndMessages(this);
    }

    public X setOnClickListener(OnClickListener<? extends View> onClickListener) {
        return setOnClickListener(this.mDecorView, onClickListener);
    }

    public X setOnClickListener(int i, OnClickListener<? extends View> onClickListener) {
        return setOnClickListener(findViewById(i), onClickListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private X setOnClickListener(View view, OnClickListener<? extends View> onClickListener) {
        removeWindowFlags(16);
        view.setClickable(true);
        view.setOnClickListener(new ViewClickWrapper(this, onClickListener));
        return this;
    }

    public X setOnLongClickListener(OnLongClickListener<? extends View> onLongClickListener) {
        return setOnLongClickListener(this.mDecorView, onLongClickListener);
    }

    public X setOnLongClickListener(int i, OnLongClickListener<? extends View> onLongClickListener) {
        return setOnLongClickListener(findViewById(i), onLongClickListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private X setOnLongClickListener(View view, OnLongClickListener<? extends View> onLongClickListener) {
        removeWindowFlags(16);
        view.setClickable(true);
        view.setOnLongClickListener(new ViewLongClickWrapper(this, onLongClickListener));
        return this;
    }

    public X setOnTouchListener(OnTouchListener<? extends View> onTouchListener) {
        return setOnTouchListener(this.mDecorView, onTouchListener);
    }

    public X setOnTouchListener(int i, OnTouchListener<? extends View> onTouchListener) {
        return setOnTouchListener(findViewById(i), onTouchListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private X setOnTouchListener(View view, OnTouchListener<? extends View> onTouchListener) {
        removeWindowFlags(16);
        view.setEnabled(true);
        view.setOnTouchListener(new ViewTouchWrapper(this, onTouchListener));
        return this;
    }

    @Override // java.lang.Runnable
    public void run() {
        cancel();
    }

    @Override // com.hjq.window.ScreenOrientationMonitor.OnScreenOrientationCallback
    public void onScreenOrientationChange(int i) {
        BaseDraggable baseDraggable;
        if (isShowing() && (baseDraggable = this.mDraggable) != null) {
            baseDraggable.onScreenOrientationChange();
        }
    }
}
