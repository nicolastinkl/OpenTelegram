package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.BubbleActivity;

/* loaded from: classes4.dex */
public class PhotoFilterBlurControl extends FrameLayout {
    private static final float BlurInsetProximity = AndroidUtilities.dp(20.0f);
    private static final float BlurViewCenterInset = AndroidUtilities.dp(30.0f);
    private static final float BlurViewRadiusInset = AndroidUtilities.dp(30.0f);
    private BlurViewActiveControl activeControl;
    private Size actualAreaSize;
    private float angle;
    private Paint arcPaint;
    private RectF arcRect;
    private Point centerPoint;
    private boolean checkForMoving;
    private boolean checkForZooming;
    private PhotoFilterLinearBlurControlDelegate delegate;
    private float falloff;
    private boolean inBubbleMode;
    private boolean isMoving;
    private boolean isZooming;
    private Paint paint;
    private float pointerScale;
    private float pointerStartX;
    private float pointerStartY;
    private float size;
    private Point startCenterPoint;
    private float startDistance;
    private float startPointerDistance;
    private float startRadius;
    private int type;

    private enum BlurViewActiveControl {
        BlurViewActiveControlNone,
        BlurViewActiveControlCenter,
        BlurViewActiveControlInnerRadius,
        BlurViewActiveControlOuterRadius,
        BlurViewActiveControlWholeArea,
        BlurViewActiveControlRotation
    }

    public interface PhotoFilterLinearBlurControlDelegate {
        void valueChanged(Point point, float f, float f2, float f3);
    }

    private float degreesToRadians(float f) {
        return (f * 3.1415927f) / 180.0f;
    }

    private void setSelected(boolean z, boolean z2) {
    }

    public PhotoFilterBlurControl(Context context) {
        super(context);
        this.startCenterPoint = new Point();
        this.actualAreaSize = new Size();
        this.centerPoint = new Point(0.5f, 0.5f);
        this.falloff = 0.15f;
        this.size = 0.35f;
        this.arcRect = new RectF();
        this.pointerScale = 1.0f;
        this.checkForMoving = true;
        this.paint = new Paint(1);
        this.arcPaint = new Paint(1);
        setWillNotDraw(false);
        this.paint.setColor(-1);
        this.arcPaint.setColor(-1);
        this.arcPaint.setStrokeWidth(AndroidUtilities.dp(2.0f));
        this.arcPaint.setStyle(Paint.Style.STROKE);
        this.inBubbleMode = context instanceof BubbleActivity;
    }

    public void setType(int i) {
        this.type = i;
        invalidate();
    }

    public void setDelegate(PhotoFilterLinearBlurControlDelegate photoFilterLinearBlurControlDelegate) {
        this.delegate = photoFilterLinearBlurControlDelegate;
    }

    private float getDistance(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        float x2 = x - motionEvent.getX(1);
        float y2 = y - motionEvent.getY(1);
        return (float) Math.sqrt((x2 * x2) + (y2 * y2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0018, code lost:
    
        if (r2 != 6) goto L90;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 349
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.PhotoFilterBlurControl.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void handlePan(int i, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Point actualCenterPoint = getActualCenterPoint();
        float f = x - actualCenterPoint.x;
        float f2 = y - actualCenterPoint.y;
        float sqrt = (float) Math.sqrt((f * f) + (f2 * f2));
        Size size = this.actualAreaSize;
        float min = Math.min(size.width, size.height);
        float f3 = this.falloff * min;
        float f4 = this.size * min;
        float abs = (float) Math.abs((f * Math.cos(degreesToRadians(this.angle) + 1.5707963267948966d)) + (f2 * Math.sin(degreesToRadians(this.angle) + 1.5707963267948966d)));
        if (i == 1) {
            this.pointerStartX = motionEvent.getX();
            this.pointerStartY = motionEvent.getY();
            r6 = Math.abs(f4 - f3) < BlurInsetProximity ? 1 : 0;
            float f5 = r6 != 0 ? 0.0f : BlurViewRadiusInset;
            float f6 = r6 == 0 ? BlurViewRadiusInset : 0.0f;
            int i2 = this.type;
            if (i2 == 0) {
                if (sqrt < BlurViewCenterInset) {
                    this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                    this.startCenterPoint = actualCenterPoint;
                } else {
                    float f7 = BlurViewRadiusInset;
                    if (abs > f3 - f7 && abs < f5 + f3) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = abs;
                        this.startRadius = f3;
                    } else if (abs > f4 - f6 && abs < f4 + f7) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = abs;
                        this.startRadius = f4;
                    } else if (abs <= f3 - f7 || abs >= f4 + f7) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlRotation;
                    }
                }
            } else if (i2 == 1) {
                if (sqrt < BlurViewCenterInset) {
                    this.activeControl = BlurViewActiveControl.BlurViewActiveControlCenter;
                    this.startCenterPoint = actualCenterPoint;
                } else {
                    float f8 = BlurViewRadiusInset;
                    if (sqrt > f3 - f8 && sqrt < f5 + f3) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlInnerRadius;
                        this.startDistance = sqrt;
                        this.startRadius = f3;
                    } else if (sqrt > f4 - f6 && sqrt < f8 + f4) {
                        this.activeControl = BlurViewActiveControl.BlurViewActiveControlOuterRadius;
                        this.startDistance = sqrt;
                        this.startRadius = f4;
                    }
                }
            }
            setSelected(true, true);
            return;
        }
        if (i != 2) {
            if (i == 3 || i == 4 || i == 5) {
                this.activeControl = BlurViewActiveControl.BlurViewActiveControlNone;
                setSelected(false, true);
                return;
            }
            return;
        }
        int i3 = this.type;
        if (i3 == 0) {
            int i4 = AnonymousClass1.$SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl[this.activeControl.ordinal()];
            if (i4 == 1) {
                float f9 = x - this.pointerStartX;
                float f10 = y - this.pointerStartY;
                float width = (getWidth() - this.actualAreaSize.width) / 2.0f;
                if (Build.VERSION.SDK_INT >= 21 && !this.inBubbleMode) {
                    r6 = AndroidUtilities.statusBarHeight;
                }
                float f11 = r6;
                float height = getHeight();
                Size size2 = this.actualAreaSize;
                float f12 = size2.height;
                Rect rect = new Rect(width, f11 + ((height - f12) / 2.0f), size2.width, f12);
                float f13 = rect.x;
                float max = Math.max(f13, Math.min(rect.width + f13, this.startCenterPoint.x + f9));
                float f14 = rect.y;
                Point point = new Point(max, Math.max(f14, Math.min(rect.height + f14, this.startCenterPoint.y + f10)));
                float f15 = point.x - rect.x;
                Size size3 = this.actualAreaSize;
                float f16 = size3.width;
                this.centerPoint = new Point(f15 / f16, ((point.y - rect.y) + ((f16 - size3.height) / 2.0f)) / f16);
            } else if (i4 == 2) {
                this.falloff = Math.min(Math.max(0.1f, (this.startRadius + (abs - this.startDistance)) / min), this.size - 0.02f);
            } else if (i4 == 3) {
                this.size = Math.max(this.falloff + 0.02f, (this.startRadius + (abs - this.startDistance)) / min);
            } else if (i4 == 4) {
                float f17 = x - this.pointerStartX;
                float f18 = y - this.pointerStartY;
                boolean z = x > actualCenterPoint.x;
                boolean z2 = y > actualCenterPoint.y;
                boolean z3 = Math.abs(f18) > Math.abs(f17);
                if (z || z2 ? !(!z || z2 ? !z || !z2 ? !z3 ? f17 >= 0.0f : f18 >= 0.0f : !z3 ? f17 >= 0.0f : f18 <= 0.0f : !z3 ? f17 <= 0.0f : f18 <= 0.0f) : !(!z3 ? f17 <= 0.0f : f18 >= 0.0f)) {
                    r6 = 1;
                }
                this.angle += ((((float) Math.sqrt((f17 * f17) + (f18 * f18))) * ((r6 * 2) - 1)) / 3.1415927f) / 1.15f;
                this.pointerStartX = x;
                this.pointerStartY = y;
            }
        } else if (i3 == 1) {
            int i5 = AnonymousClass1.$SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl[this.activeControl.ordinal()];
            if (i5 == 1) {
                float f19 = x - this.pointerStartX;
                float f20 = y - this.pointerStartY;
                float width2 = (getWidth() - this.actualAreaSize.width) / 2.0f;
                if (Build.VERSION.SDK_INT >= 21 && !this.inBubbleMode) {
                    r6 = AndroidUtilities.statusBarHeight;
                }
                float f21 = r6;
                float height2 = getHeight();
                Size size4 = this.actualAreaSize;
                float f22 = size4.height;
                Rect rect2 = new Rect(width2, f21 + ((height2 - f22) / 2.0f), size4.width, f22);
                float f23 = rect2.x;
                float max2 = Math.max(f23, Math.min(rect2.width + f23, this.startCenterPoint.x + f19));
                float f24 = rect2.y;
                Point point2 = new Point(max2, Math.max(f24, Math.min(rect2.height + f24, this.startCenterPoint.y + f20)));
                float f25 = point2.x - rect2.x;
                Size size5 = this.actualAreaSize;
                float f26 = size5.width;
                this.centerPoint = new Point(f25 / f26, ((point2.y - rect2.y) + ((f26 - size5.height) / 2.0f)) / f26);
            } else if (i5 == 2) {
                this.falloff = Math.min(Math.max(0.1f, (this.startRadius + (sqrt - this.startDistance)) / min), this.size - 0.02f);
            } else if (i5 == 3) {
                this.size = Math.max(this.falloff + 0.02f, (this.startRadius + (sqrt - this.startDistance)) / min);
            }
        }
        invalidate();
        PhotoFilterLinearBlurControlDelegate photoFilterLinearBlurControlDelegate = this.delegate;
        if (photoFilterLinearBlurControlDelegate != null) {
            photoFilterLinearBlurControlDelegate.valueChanged(this.centerPoint, this.falloff, this.size, degreesToRadians(this.angle) + 1.5707964f);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoFilterBlurControl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl;

        static {
            int[] iArr = new int[BlurViewActiveControl.values().length];
            $SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl = iArr;
            try {
                iArr[BlurViewActiveControl.BlurViewActiveControlCenter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl[BlurViewActiveControl.BlurViewActiveControlInnerRadius.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl[BlurViewActiveControl.BlurViewActiveControlOuterRadius.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$telegram$ui$Components$PhotoFilterBlurControl$BlurViewActiveControl[BlurViewActiveControl.BlurViewActiveControlRotation.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void handlePinch(int i, MotionEvent motionEvent) {
        if (i == 1) {
            this.startPointerDistance = getDistance(motionEvent);
            this.pointerScale = 1.0f;
            this.activeControl = BlurViewActiveControl.BlurViewActiveControlWholeArea;
            setSelected(true, true);
        } else if (i != 2) {
            if (i == 3 || i == 4 || i == 5) {
                this.activeControl = BlurViewActiveControl.BlurViewActiveControlNone;
                setSelected(false, true);
                return;
            }
            return;
        }
        float distance = getDistance(motionEvent);
        float f = this.pointerScale + (((distance - this.startPointerDistance) / AndroidUtilities.density) * 0.01f);
        this.pointerScale = f;
        float max = Math.max(0.1f, this.falloff * f);
        this.falloff = max;
        this.size = Math.max(max + 0.02f, this.size * this.pointerScale);
        this.pointerScale = 1.0f;
        this.startPointerDistance = distance;
        invalidate();
        PhotoFilterLinearBlurControlDelegate photoFilterLinearBlurControlDelegate = this.delegate;
        if (photoFilterLinearBlurControlDelegate != null) {
            photoFilterLinearBlurControlDelegate.valueChanged(this.centerPoint, this.falloff, this.size, degreesToRadians(this.angle) + 1.5707964f);
        }
    }

    public void setActualAreaSize(float f, float f2) {
        Size size = this.actualAreaSize;
        size.width = f;
        size.height = f2;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Point actualCenterPoint = getActualCenterPoint();
        float actualInnerRadius = getActualInnerRadius();
        float actualOuterRadius = getActualOuterRadius();
        canvas.translate(actualCenterPoint.x, actualCenterPoint.y);
        int i = this.type;
        if (i == 0) {
            canvas.rotate(this.angle);
            float dp = AndroidUtilities.dp(6.0f);
            float dp2 = AndroidUtilities.dp(12.0f);
            float dp3 = AndroidUtilities.dp(1.5f);
            for (int i2 = 0; i2 < 30; i2++) {
                float f = dp2 + dp;
                float f2 = i2 * f;
                float f3 = -actualInnerRadius;
                float f4 = f2 + dp2;
                float f5 = dp3 - actualInnerRadius;
                canvas.drawRect(f2, f3, f4, f5, this.paint);
                float f6 = ((-r11) * f) - dp;
                float f7 = f6 - dp2;
                canvas.drawRect(f7, f3, f6, f5, this.paint);
                float f8 = dp3 + actualInnerRadius;
                canvas.drawRect(f2, actualInnerRadius, f4, f8, this.paint);
                canvas.drawRect(f7, actualInnerRadius, f6, f8, this.paint);
            }
            float dp4 = AndroidUtilities.dp(6.0f);
            for (int i3 = 0; i3 < 64; i3++) {
                float f9 = dp4 + dp;
                float f10 = i3 * f9;
                float f11 = -actualOuterRadius;
                float f12 = dp4 + f10;
                float f13 = dp3 - actualOuterRadius;
                canvas.drawRect(f10, f11, f12, f13, this.paint);
                float f14 = ((-i3) * f9) - dp;
                float f15 = f14 - dp4;
                canvas.drawRect(f15, f11, f14, f13, this.paint);
                float f16 = dp3 + actualOuterRadius;
                canvas.drawRect(f10, actualOuterRadius, f12, f16, this.paint);
                canvas.drawRect(f15, actualOuterRadius, f14, f16, this.paint);
            }
        } else if (i == 1) {
            float f17 = -actualInnerRadius;
            this.arcRect.set(f17, f17, actualInnerRadius, actualInnerRadius);
            for (int i4 = 0; i4 < 22; i4++) {
                canvas.drawArc(this.arcRect, 16.35f * i4, 10.2f, false, this.arcPaint);
            }
            float f18 = -actualOuterRadius;
            this.arcRect.set(f18, f18, actualOuterRadius, actualOuterRadius);
            for (int i5 = 0; i5 < 64; i5++) {
                canvas.drawArc(this.arcRect, 5.62f * i5, 3.6f, false, this.arcPaint);
            }
        }
        canvas.drawCircle(0.0f, 0.0f, AndroidUtilities.dp(8.0f), this.paint);
    }

    private Point getActualCenterPoint() {
        float width = getWidth();
        float f = this.actualAreaSize.width;
        float f2 = ((width - f) / 2.0f) + (this.centerPoint.x * f);
        int i = (Build.VERSION.SDK_INT < 21 || this.inBubbleMode) ? 0 : AndroidUtilities.statusBarHeight;
        float height = getHeight();
        Size size = this.actualAreaSize;
        float f3 = size.height;
        float f4 = i + ((height - f3) / 2.0f);
        float f5 = size.width;
        return new Point(f2, (f4 - ((f5 - f3) / 2.0f)) + (this.centerPoint.y * f5));
    }

    private float getActualInnerRadius() {
        Size size = this.actualAreaSize;
        return Math.min(size.width, size.height) * this.falloff;
    }

    private float getActualOuterRadius() {
        Size size = this.actualAreaSize;
        return Math.min(size.width, size.height) * this.size;
    }
}
