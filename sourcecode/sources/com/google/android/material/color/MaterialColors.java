package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes.dex */
public class MaterialColors {
    public static int getColor(View view, int i) {
        return MaterialAttributes.resolveOrThrow(view, i);
    }

    public static int getColor(Context context, int i, String str) {
        return MaterialAttributes.resolveOrThrow(context, i, str);
    }

    public static int getColor(View view, int i, int i2) {
        return getColor(view.getContext(), i, i2);
    }

    public static int getColor(Context context, int i, int i2) {
        TypedValue resolve = MaterialAttributes.resolve(context, i);
        return resolve != null ? resolve.data : i2;
    }

    public static int layer(View view, int i, int i2, float f) {
        return layer(getColor(view, i), getColor(view, i2), f);
    }

    public static int layer(int i, int i2, float f) {
        return layer(i, ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * f)));
    }

    public static int layer(int i, int i2) {
        return ColorUtils.compositeColors(i2, i);
    }
}
