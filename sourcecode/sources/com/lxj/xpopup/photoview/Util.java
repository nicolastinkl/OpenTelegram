package com.lxj.xpopup.photoview;

import android.widget.ImageView;

/* loaded from: classes.dex */
class Util {
    static int getPointerIndex(int action) {
        return (action & 65280) >> 8;
    }

    static void checkZoomLevels(float minZoom, float midZoom, float maxZoom) {
        if (minZoom >= midZoom) {
            throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
        }
        if (midZoom >= maxZoom) {
            throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
        }
    }

    static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    /* renamed from: com.lxj.xpopup.photoview.Util$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    static boolean isSupportedScaleType(final ImageView.ScaleType scaleType) {
        return (scaleType == null || AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()] == 1) ? false : true;
    }
}
