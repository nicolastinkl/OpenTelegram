package org.telegram.ui.Components;

import android.graphics.CornerPathEffect;
import android.graphics.Path;
import android.os.Build;
import android.text.Layout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LiteMode;

/* loaded from: classes4.dex */
public class LinkPath extends Path {
    private static CornerPathEffect roundedEffect;
    private static int roundedEffectRadius;
    private int baselineShift;
    public float centerX;
    public float centerY;
    private Layout currentLayout;
    private int currentLine;
    private int lineHeight;
    private boolean useRoundRect;
    private float xOffset;
    private float yOffset;
    private float lastTop = -1.0f;
    private boolean allowReset = true;

    public static int getRadius() {
        return AndroidUtilities.dp(5.0f);
    }

    public static CornerPathEffect getRoundedEffect() {
        if (roundedEffect == null || roundedEffectRadius != getRadius()) {
            int radius = getRadius();
            roundedEffectRadius = radius;
            roundedEffect = new CornerPathEffect(radius);
        }
        return roundedEffect;
    }

    public LinkPath() {
    }

    public LinkPath(boolean z) {
        this.useRoundRect = z;
    }

    public void setCurrentLayout(Layout layout, int i, float f) {
        setCurrentLayout(layout, i, 0.0f, f);
    }

    public void setCurrentLayout(Layout layout, int i, float f, float f2) {
        int lineCount;
        if (layout == null) {
            this.currentLayout = null;
            this.currentLine = 0;
            this.lastTop = -1.0f;
            this.xOffset = f;
            this.yOffset = f2;
            return;
        }
        this.currentLayout = layout;
        this.currentLine = layout.getLineForOffset(i);
        this.lastTop = -1.0f;
        this.xOffset = f;
        this.yOffset = f2;
        if (Build.VERSION.SDK_INT < 28 || (lineCount = layout.getLineCount()) <= 0) {
            return;
        }
        int i2 = lineCount - 1;
        this.lineHeight = layout.getLineBottom(i2) - layout.getLineTop(i2);
    }

    public void setAllowReset(boolean z) {
        this.allowReset = z;
    }

    public void setBaselineShift(int i) {
        this.baselineShift = i;
    }

    @Override // android.graphics.Path
    public void addRect(float f, float f2, float f3, float f4, Path.Direction direction) {
        Layout layout = this.currentLayout;
        if (layout == null) {
            super.addRect(f, f2, f3, f4, direction);
            return;
        }
        try {
            float f5 = this.yOffset;
            float f6 = f2 + f5;
            float f7 = f5 + f4;
            float f8 = this.lastTop;
            if (f8 == -1.0f) {
                this.lastTop = f6;
            } else if (f8 != f6) {
                this.lastTop = f6;
                this.currentLine++;
            }
            float lineRight = layout.getLineRight(this.currentLine);
            float lineLeft = this.currentLayout.getLineLeft(this.currentLine);
            if (f < lineRight) {
                if (f > lineLeft || f3 > lineLeft) {
                    if (f3 <= lineRight) {
                        lineRight = f3;
                    }
                    if (f >= lineLeft) {
                        lineLeft = f;
                    }
                    float f9 = this.xOffset;
                    float f10 = lineLeft + f9;
                    float f11 = f9 + lineRight;
                    if (Build.VERSION.SDK_INT < 28) {
                        f7 -= f7 != ((float) this.currentLayout.getHeight()) ? this.currentLayout.getSpacingAdd() : 0.0f;
                    } else if (f7 - f6 > this.lineHeight) {
                        f7 = this.yOffset + (f7 != ((float) this.currentLayout.getHeight()) ? this.currentLayout.getLineBottom(this.currentLine) - this.currentLayout.getSpacingAdd() : 0.0f);
                    }
                    int i = this.baselineShift;
                    if (i < 0) {
                        f7 += i;
                    } else if (i > 0) {
                        f6 += i;
                    }
                    float f12 = f7;
                    this.centerX = (f11 + f10) / 2.0f;
                    this.centerY = (f12 + f6) / 2.0f;
                    if (this.useRoundRect && LiteMode.isEnabled(LiteMode.FLAGS_CHAT)) {
                        super.addRect(f10 - (getRadius() / 2.0f), f6, f11 + (getRadius() / 2.0f), f12, direction);
                    } else {
                        super.addRect(f10, f6, f11, f12, direction);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    @Override // android.graphics.Path
    public void reset() {
        if (this.allowReset) {
            super.reset();
        }
    }
}
