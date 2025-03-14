package org.telegram.ui.ActionBar;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes4.dex */
public class MenuDrawable extends Drawable {
    public static int TYPE_DEFAULT = 0;
    public static int TYPE_UDPATE_AVAILABLE = 1;
    public static int TYPE_UDPATE_DOWNLOADING = 2;
    private int alpha;
    private float animatedDownloadProgress;
    private int backColor;
    private Paint backPaint;
    private int currentAnimationTime;
    private float currentRotation;
    private float downloadProgress;
    private float downloadProgressAnimationStart;
    private float downloadProgressTime;
    private float downloadRadOffset;
    private float finalRotation;
    private int iconColor;
    private DecelerateInterpolator interpolator;
    private long lastFrameTime;
    private boolean miniIcon;
    private Paint paint;
    private int previousType;
    private RectF rect;
    private boolean reverseAngle;
    private boolean rotateToBack;
    private boolean roundCap;
    private int type;
    private float typeAnimationProgress;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public MenuDrawable() {
        this(TYPE_DEFAULT);
    }

    public MenuDrawable(int i) {
        this.paint = new Paint(1);
        this.backPaint = new Paint(1);
        this.rotateToBack = true;
        this.interpolator = new DecelerateInterpolator();
        this.rect = new RectF();
        this.alpha = 255;
        this.paint.setStrokeWidth(AndroidUtilities.dp(2.0f));
        this.backPaint.setStrokeWidth(AndroidUtilities.density * 1.66f);
        this.backPaint.setStrokeCap(Paint.Cap.ROUND);
        this.backPaint.setStyle(Paint.Style.STROKE);
        this.previousType = TYPE_DEFAULT;
        this.type = i;
        this.typeAnimationProgress = 1.0f;
    }

    public void setRotateToBack(boolean z) {
        this.rotateToBack = z;
    }

    public void setRotation(float f, boolean z) {
        this.lastFrameTime = 0L;
        float f2 = this.currentRotation;
        if (f2 == 1.0f) {
            this.reverseAngle = true;
        } else if (f2 == 0.0f) {
            this.reverseAngle = false;
        }
        this.lastFrameTime = 0L;
        if (z) {
            if (f2 < f) {
                this.currentAnimationTime = (int) (f2 * 200.0f);
            } else {
                this.currentAnimationTime = (int) ((1.0f - f2) * 200.0f);
            }
            this.lastFrameTime = SystemClock.elapsedRealtime();
            this.finalRotation = f;
        } else {
            this.currentRotation = f;
            this.finalRotation = f;
        }
        invalidateSelf();
    }

    public void setType(int i, boolean z) {
        int i2 = this.type;
        if (i2 == i) {
            return;
        }
        this.previousType = i2;
        this.type = i;
        if (z) {
            this.typeAnimationProgress = 0.0f;
        } else {
            this.typeAnimationProgress = 1.0f;
        }
        invalidateSelf();
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0385  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x04e3  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0221  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(android.graphics.Canvas r26) {
        /*
            Method dump skipped, instructions count: 1289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.ActionBar.MenuDrawable.draw(android.graphics.Canvas):void");
    }

    public void setUpdateDownloadProgress(float f, boolean z) {
        if (!z) {
            this.animatedDownloadProgress = f;
            this.downloadProgressAnimationStart = f;
        } else {
            if (this.animatedDownloadProgress > f) {
                this.animatedDownloadProgress = f;
            }
            this.downloadProgressAnimationStart = this.animatedDownloadProgress;
        }
        this.downloadProgress = f;
        this.downloadProgressTime = 0.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.alpha != i) {
            this.alpha = i;
            this.paint.setAlpha(i);
            this.backPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(24.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(24.0f);
    }

    public void setIconColor(int i) {
        this.iconColor = i;
    }

    public void setBackColor(int i) {
        this.backColor = i;
    }

    public void setRoundCap() {
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.roundCap = true;
    }

    public void setMiniIcon(boolean z) {
        this.miniIcon = z;
    }
}
