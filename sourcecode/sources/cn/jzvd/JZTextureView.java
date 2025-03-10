package cn.jzvd;

import android.content.Context;
import android.util.Log;
import android.view.TextureView;
import android.view.View;

/* loaded from: classes.dex */
public class JZTextureView extends TextureView {
    public int currentVideoHeight;
    public int currentVideoWidth;

    public JZTextureView(Context context) {
        super(context);
        this.currentVideoWidth = 0;
        this.currentVideoHeight = 0;
        this.currentVideoWidth = 0;
        this.currentVideoHeight = 0;
    }

    public void setVideoSize(int currentVideoWidth, int currentVideoHeight) {
        if (this.currentVideoWidth == currentVideoWidth && this.currentVideoHeight == currentVideoHeight) {
            return;
        }
        this.currentVideoWidth = currentVideoWidth;
        this.currentVideoHeight = currentVideoHeight;
        requestLayout();
    }

    @Override // android.view.View
    public void setRotation(float rotation) {
        if (rotation != getRotation()) {
            super.setRotation(rotation);
            requestLayout();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int i2;
        int i3;
        int i4;
        Log.i("JZResizeTextureView", "onMeasure  [" + hashCode() + "] ");
        int rotation = (int) getRotation();
        int i5 = this.currentVideoWidth;
        int i6 = this.currentVideoHeight;
        int measuredHeight = ((View) getParent()).getMeasuredHeight();
        int measuredWidth = ((View) getParent()).getMeasuredWidth();
        if (measuredWidth != 0 && measuredHeight != 0 && i5 != 0 && i6 != 0 && Jzvd.VIDEO_IMAGE_DISPLAY_TYPE == 1) {
            if (rotation == 90 || rotation == 270) {
                measuredWidth = measuredHeight;
                measuredHeight = measuredWidth;
            }
            i6 = (i5 * measuredHeight) / measuredWidth;
        }
        if (rotation == 90 || rotation == 270) {
            i = widthMeasureSpec;
            i2 = heightMeasureSpec;
        } else {
            i2 = widthMeasureSpec;
            i = heightMeasureSpec;
        }
        int defaultSize = TextureView.getDefaultSize(i5, i2);
        int defaultSize2 = TextureView.getDefaultSize(i6, i);
        if (i5 > 0 && i6 > 0) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i);
            int size2 = View.MeasureSpec.getSize(i);
            Log.i("JZResizeTextureView", "widthMeasureSpec  [" + View.MeasureSpec.toString(i2) + "]");
            Log.i("JZResizeTextureView", "heightMeasureSpec [" + View.MeasureSpec.toString(i) + "]");
            if (mode == 1073741824 && mode2 == 1073741824) {
                int i7 = i5 * size2;
                int i8 = size * i6;
                if (i7 < i8) {
                    defaultSize = i7 / i6;
                } else if (i7 > i8) {
                    i4 = i8 / i5;
                    defaultSize = size;
                    defaultSize2 = i4;
                } else {
                    defaultSize = size;
                }
                defaultSize2 = size2;
            } else if (mode == 1073741824) {
                i4 = (size * i6) / i5;
                if (mode2 == Integer.MIN_VALUE && i4 > size2) {
                    defaultSize = (size2 * i5) / i6;
                    defaultSize2 = size2;
                }
                defaultSize = size;
                defaultSize2 = i4;
            } else if (mode2 == 1073741824) {
                i3 = (size2 * i5) / i6;
                if (mode == Integer.MIN_VALUE && i3 > size) {
                    i4 = (size * i6) / i5;
                    defaultSize = size;
                    defaultSize2 = i4;
                }
                defaultSize = i3;
                defaultSize2 = size2;
            } else {
                if (mode2 != Integer.MIN_VALUE || i6 <= size2) {
                    i3 = i5;
                    size2 = i6;
                } else {
                    i3 = (size2 * i5) / i6;
                }
                if (mode == Integer.MIN_VALUE && i3 > size) {
                    i4 = (size * i6) / i5;
                    defaultSize = size;
                    defaultSize2 = i4;
                }
                defaultSize = i3;
                defaultSize2 = size2;
            }
        }
        if (measuredWidth != 0 && measuredHeight != 0 && i5 != 0 && i6 != 0) {
            int i9 = Jzvd.VIDEO_IMAGE_DISPLAY_TYPE;
            if (i9 != 3) {
                if (i9 == 2) {
                    if (rotation == 90 || rotation == 270) {
                        int i10 = measuredWidth;
                        measuredWidth = measuredHeight;
                        measuredHeight = i10;
                    }
                    double d = i6 / i5;
                    double d2 = measuredHeight;
                    double d3 = measuredWidth;
                    double d4 = d2 / d3;
                    if (d > d4) {
                        i6 = (int) ((d3 / defaultSize) * defaultSize2);
                        i5 = measuredWidth;
                    } else if (d < d4) {
                        i5 = (int) ((d2 / defaultSize2) * defaultSize);
                        i6 = measuredHeight;
                    }
                }
            }
            setMeasuredDimension(i5, i6);
        }
        i5 = defaultSize;
        i6 = defaultSize2;
        setMeasuredDimension(i5, i6);
    }
}
