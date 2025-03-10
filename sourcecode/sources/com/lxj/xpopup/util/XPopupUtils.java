package com.lxj.xpopup.util;

import android.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.Toast;
import com.lxj.xpopup.R$string;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BubbleAttachPopupView;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executors;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class XPopupUtils {
    private static int preKeyboardHeight;
    private static int sDecorViewDelta;
    private static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void setCursorDrawableColor(EditText et, int color) {
    }

    public static int getAppHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getAppWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager == null) {
            return -1;
        }
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

    public static int dp2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getStatusBarHeight(Window window) {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", "android"));
    }

    public static int getNavBarHeight(Window window) {
        if (!isNavBarVisible(window)) {
            return 0;
        }
        if (Build.VERSION.SDK_INT >= 21 && window != null) {
            View findViewById = window.findViewById(R.id.navigationBarBackground);
            if (findViewById != null && findViewById.getVisibility() == 0) {
                return findViewById.getHeight();
            }
            return 0;
        }
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier != 0) {
            return system.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static void setWidthHeight(View target, int width, int height) {
        if (width > 0 || height > 0) {
            ViewGroup.LayoutParams layoutParams = target.getLayoutParams();
            if (width > 0) {
                layoutParams.width = width;
            }
            if (height > 0) {
                layoutParams.height = height;
            }
            target.setLayoutParams(layoutParams);
        }
    }

    public static void applyPopupSize(final ViewGroup content, final int maxWidth, final int maxHeight, final int popupWidth, final int popupHeight, final Runnable afterApplySize) {
        content.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                XPopupUtils.lambda$applyPopupSize$1(content, maxWidth, popupWidth, maxHeight, popupHeight, afterApplySize);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyPopupSize$1(final ViewGroup content, final int maxWidth, final int popupWidth, final int maxHeight, final int popupHeight, final Runnable afterApplySize) {
        ViewGroup.LayoutParams layoutParams = content.getLayoutParams();
        View childAt = content.getChildAt(0);
        if (childAt == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams2 = childAt.getLayoutParams();
        int measuredWidth = content.getMeasuredWidth();
        if (maxWidth > 0) {
            if (measuredWidth > maxWidth) {
                layoutParams.width = Math.min(measuredWidth, maxWidth);
            }
            if (layoutParams2.width == -1) {
                int min = Math.min(measuredWidth, maxWidth);
                layoutParams2.width = min;
                if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                    layoutParams2.width = (min - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin;
                }
            }
            if (popupWidth > 0) {
                layoutParams.width = Math.min(popupWidth, maxWidth);
                layoutParams2.width = Math.min(popupWidth, maxWidth);
            }
        } else if (popupWidth > 0) {
            layoutParams.width = popupWidth;
            layoutParams2.width = popupWidth;
        }
        if (maxHeight > 0) {
            int measuredHeight = content.getMeasuredHeight();
            if (measuredHeight > maxHeight) {
                layoutParams.height = Math.min(measuredHeight, maxHeight);
            }
            if (popupHeight > 0) {
                layoutParams.height = Math.min(popupHeight, maxHeight);
                layoutParams2.height = Math.min(popupHeight, maxHeight);
            }
        } else if (popupHeight > 0) {
            layoutParams.height = popupHeight;
            layoutParams2.height = popupHeight;
        }
        childAt.setLayoutParams(layoutParams2);
        content.setLayoutParams(layoutParams);
        content.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                XPopupUtils.lambda$null$0(afterApplySize);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$null$0(final Runnable afterApplySize) {
        if (afterApplySize != null) {
            afterApplySize.run();
        }
    }

    public static BitmapDrawable createBitmapDrawable(Context context, int width, int color) {
        Bitmap createBitmap = Bitmap.createBitmap(width, dp2px(context, 1.5f), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0.0f, 0.0f, createBitmap.getWidth(), createBitmap.getHeight(), paint);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), createBitmap);
        bitmapDrawable.setGravity(80);
        return bitmapDrawable;
    }

    public static StateListDrawable createSelector(Drawable defaultDrawable, Drawable focusDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{R.attr.state_focused}, focusDrawable);
        stateListDrawable.addState(new int[0], defaultDrawable);
        return stateListDrawable;
    }

    public static boolean isInRect(float x, float y, Rect rect) {
        return x >= ((float) rect.left) && x <= ((float) rect.right) && y >= ((float) rect.top) && y <= ((float) rect.bottom);
    }

    public static int getDecorViewInvisibleHeight(final Window window) {
        View decorView;
        if (window == null || (decorView = window.getDecorView()) == null) {
            return 0;
        }
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        int abs = Math.abs(decorView.getBottom() - rect.bottom);
        if (abs <= getNavBarHeight(window)) {
            sDecorViewDelta = abs;
            return 0;
        }
        return abs - sDecorViewDelta;
    }

    public static void moveUpToKeyboard(final int keyboardHeight, final BasePopupView pv) {
        preKeyboardHeight = keyboardHeight;
        pv.post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.1
            @Override // java.lang.Runnable
            public void run() {
                XPopupUtils.moveUpToKeyboardInternal(XPopupUtils.preKeyboardHeight, BasePopupView.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ae, code lost:
    
        if (r7 > 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00c2, code lost:
    
        if (r7 > 0) goto L48;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void moveUpToKeyboardInternal(int r7, com.lxj.xpopup.core.BasePopupView r8) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.util.XPopupUtils.moveUpToKeyboardInternal(int, com.lxj.xpopup.core.BasePopupView):void");
    }

    public static void moveDown(BasePopupView pv) {
        if ((pv instanceof PositionPopupView) || (pv instanceof AttachPopupView) || (pv instanceof BubbleAttachPopupView)) {
            return;
        }
        if ((pv instanceof FullScreenPopupView) && pv.getPopupContentView().hasTransientState()) {
            return;
        }
        pv.getPopupContentView().animate().translationY(0.0f).setInterpolator(new LinearInterpolator()).setDuration(100L).start();
    }

    public static boolean isNavBarVisible(Window window) {
        ViewGroup viewGroup;
        boolean z;
        int i;
        if (window == null || (viewGroup = (ViewGroup) window.getDecorView()) == null) {
            return false;
        }
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1) {
                try {
                    if ("navigationBarBackground".equals(window.getContext().getResources().getResourceEntryName(id)) && childAt.getVisibility() == 0) {
                        z = true;
                        break;
                    }
                } catch (Resources.NotFoundException unused) {
                }
            }
        }
        z = false;
        if (!z) {
            return z;
        }
        if (FuckRomUtils.isSamsung() && (i = Build.VERSION.SDK_INT) >= 17 && i < 29) {
            try {
                return Settings.Global.getInt(window.getContext().getContentResolver(), "navigationbar_hide_bar_enabled") == 0;
            } catch (Exception unused2) {
            }
        }
        return (viewGroup.getSystemUiVisibility() & 2) == 0;
    }

    public static void findAllEditText(ArrayList<EditText> list, ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View childAt = group.getChildAt(i);
            if ((childAt instanceof EditText) && childAt.getVisibility() == 0) {
                list.add((EditText) childAt);
            } else if (childAt instanceof ViewGroup) {
                findAllEditText(list, (ViewGroup) childAt);
            }
        }
    }

    public static void saveBmpToAlbum(final Context context, final XPopupImageLoader imageLoader, final Object uri) {
        Executors.newSingleThreadExecutor().execute(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.2
            @Override // java.lang.Runnable
            public void run() {
                Uri uri2;
                File imageFile = XPopupImageLoader.this.getImageFile(context, uri);
                if (imageFile == null) {
                    Context context2 = context;
                    XPopupUtils.showToast(context2, context2.getString(R$string.xpopup_image_not_exist));
                    return;
                }
                try {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), context.getPackageName());
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(file, System.currentTimeMillis() + "." + XPopupUtils.getImageType(imageFile));
                    if (Build.VERSION.SDK_INT < 29) {
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            XPopupUtils.writeFileFromIS(fileOutputStream, new FileInputStream(imageFile));
                            fileOutputStream.close();
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.parse("file://" + file2.getAbsolutePath()));
                            context.sendBroadcast(intent);
                        } finally {
                        }
                    } else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("_display_name", file2.getName());
                        contentValues.put("mime_type", "image/*");
                        if (Environment.getExternalStorageState().equals("mounted")) {
                            uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else {
                            uri2 = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                        }
                        contentValues.put("relative_path", Environment.DIRECTORY_DCIM + "/" + context.getPackageName());
                        contentValues.put("is_pending", (Integer) 1);
                        Uri insert = context.getContentResolver().insert(uri2, contentValues);
                        if (insert == null) {
                            Context context3 = context;
                            XPopupUtils.showToast(context3, context3.getString(R$string.xpopup_saved_fail));
                            return;
                        }
                        ContentResolver contentResolver = context.getContentResolver();
                        OutputStream openOutputStream = contentResolver.openOutputStream(insert);
                        try {
                            XPopupUtils.writeFileFromIS(openOutputStream, new FileInputStream(imageFile));
                            if (openOutputStream != null) {
                                openOutputStream.close();
                            }
                            contentValues.clear();
                            contentValues.put("is_pending", (Integer) 0);
                            contentResolver.update(insert, contentValues, null, null);
                        } finally {
                        }
                    }
                    Context context4 = context;
                    XPopupUtils.showToast(context4, context4.getString(R$string.xpopup_saved_to_gallery));
                } catch (Exception e) {
                    e.printStackTrace();
                    Context context5 = context;
                    XPopupUtils.showToast(context5, context5.getString(R$string.xpopup_saved_fail));
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void showToast(final Context context, final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lxj.xpopup.util.XPopupUtils.3
            @Override // java.lang.Runnable
            public void run() {
                Context context2 = context;
                if (context2 != null) {
                    Toast.makeText(context2, text, 0).show();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean writeFileFromIS(final OutputStream fos, final InputStream is) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fos);
                try {
                    byte[] bArr = new byte[LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM];
                    while (true) {
                        int read = is.read(bArr, 0, LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
                        if (read == -1) {
                            break;
                        }
                        bufferedOutputStream2.write(bArr, 0, read);
                    }
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return true;
                } catch (IOException e3) {
                    e = e3;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    try {
                        is.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    try {
                        is.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                            throw th;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            throw th;
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e8) {
            e = e8;
        }
    }

    public static Bitmap renderScriptBlur(Context context, final Bitmap src, final float radius, final boolean recycle) {
        RenderScript renderScript;
        if (!recycle) {
            src = src.copy(src.getConfig(), true);
        }
        try {
            renderScript = RenderScript.create(context);
            try {
                renderScript.setMessageHandler(new RenderScript.RSMessageHandler());
                Allocation createFromBitmap = Allocation.createFromBitmap(renderScript, src, Allocation.MipmapControl.MIPMAP_NONE, 1);
                Allocation createTyped = Allocation.createTyped(renderScript, createFromBitmap.getType());
                ScriptIntrinsicBlur create = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
                create.setInput(createFromBitmap);
                create.setRadius(radius);
                create.forEach(createTyped);
                createTyped.copyTo(src);
                renderScript.destroy();
                return src;
            } catch (Throwable th) {
                th = th;
                if (renderScript != null) {
                    renderScript.destroy();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            renderScript = null;
        }
    }

    public static Bitmap view2Bitmap(final View view, int clipHeight, int scale) {
        Bitmap createBitmap;
        if (view == null) {
            return null;
        }
        boolean isDrawingCacheEnabled = view.isDrawingCacheEnabled();
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setDrawingCacheEnabled(true);
        view.setWillNotCacheDrawing(false);
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            Bitmap drawingCache2 = view.getDrawingCache();
            if (drawingCache2 != null) {
                int width = drawingCache2.getWidth();
                if (clipHeight <= 0) {
                    clipHeight = drawingCache2.getHeight();
                }
                createBitmap = Bitmap.createBitmap(drawingCache2, 0, 0, width, clipHeight);
            } else {
                int measuredWidth = view.getMeasuredWidth();
                if (clipHeight <= 0) {
                    clipHeight = view.getMeasuredHeight();
                }
                createBitmap = Bitmap.createBitmap(measuredWidth, clipHeight, Bitmap.Config.ARGB_8888);
                view.draw(new Canvas(createBitmap));
            }
        } else {
            int width2 = drawingCache.getWidth();
            if (clipHeight <= 0) {
                clipHeight = drawingCache.getHeight();
            }
            createBitmap = Bitmap.createBitmap(drawingCache, 0, 0, width2, clipHeight);
        }
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheEnabled(isDrawingCacheEnabled);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap, view.getMeasuredWidth() / scale, view.getMeasuredHeight() / scale, true);
        if (!createBitmap.isRecycled() && createBitmap != createScaledBitmap) {
            createBitmap.recycle();
        }
        return createScaledBitmap;
    }

    public static boolean isLayoutRtl(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= 24) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return TextUtils.getLayoutDirectionFromLocale(locale) == 1;
    }

    public static Activity context2Activity(Context ctx) {
        while (ctx instanceof ContextWrapper) {
            if (ctx instanceof Activity) {
                return (Activity) ctx;
            }
            ctx = ((ContextWrapper) ctx).getBaseContext();
        }
        return null;
    }

    public static Drawable createDrawable(int color, float radius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadius(radius);
        return gradientDrawable;
    }

    public static Drawable createDrawable(int color, float tlRadius, float trRadius, float brRadius, float blRadius) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(color);
        gradientDrawable.setCornerRadii(new float[]{tlRadius, tlRadius, trRadius, trRadius, brRadius, brRadius, blRadius, blRadius});
        return gradientDrawable;
    }

    public static boolean hasSetKeyListener(View view) {
        try {
            Method declaredMethod = Class.forName("android.view.View").getDeclaredMethod("getListenerInfo", new Class[0]);
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
            Object invoke = declaredMethod.invoke(view, new Object[0]);
            Field declaredField = Class.forName("android.view.View$ListenerInfo").getDeclaredField("mOnKeyListener");
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }
            return declaredField.get(invoke) != null;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x00a5, code lost:
    
        if (r4.contains("00000200") != false) goto L61;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v28, types: [java.lang.String] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x00d4 -> B:85:0x00d7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getImageType(java.io.File r4) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.util.XPopupUtils.getImageType(java.io.File):java.lang.String");
    }

    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) {
            return "";
        }
        char[] cArr = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int length = bytes.length;
        if (length <= 0) {
            return "";
        }
        char[] cArr2 = new char[length << 1];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bytes[i2] >> 4) & 15];
            i = i3 + 1;
            cArr2[i3] = cArr[bytes[i2] & 15];
        }
        return new String(cArr2);
    }

    public static void setVisible(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? 0 : 8);
        }
    }
}
