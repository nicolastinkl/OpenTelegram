package androidx.core.widget;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class PopupWindowCompat {
    private static Field sOverlapAnchorField;
    private static boolean sOverlapAnchorFieldAttempted;
    private static Method sSetWindowLayoutTypeMethod;
    private static boolean sSetWindowLayoutTypeMethodAttempted;

    public static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        if (Build.VERSION.SDK_INT >= 19) {
            Api19Impl.showAsDropDown(popupWindow, view, i, i2, i3);
            return;
        }
        if ((GravityCompat.getAbsoluteGravity(i3, ViewCompat.getLayoutDirection(view)) & 7) == 5) {
            i -= popupWindow.getWidth() - view.getWidth();
        }
        popupWindow.showAsDropDown(view, i, i2);
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 23) {
            Api23Impl.setOverlapAnchor(popupWindow, z);
            return;
        }
        if (i >= 21) {
            if (!sOverlapAnchorFieldAttempted) {
                try {
                    Field declaredField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
                    sOverlapAnchorField = declaredField;
                    declaredField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i("PopupWindowCompatApi21", "Could not fetch mOverlapAnchor field from PopupWindow", e);
                }
                sOverlapAnchorFieldAttempted = true;
            }
            Field field = sOverlapAnchorField;
            if (field != null) {
                try {
                    field.set(popupWindow, Boolean.valueOf(z));
                } catch (IllegalAccessException e2) {
                    Log.i("PopupWindowCompatApi21", "Could not set overlap anchor field in PopupWindow", e2);
                }
            }
        }
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setWindowLayoutType(popupWindow, i);
            return;
        }
        if (!sSetWindowLayoutTypeMethodAttempted) {
            try {
                Method declaredMethod = PopupWindow.class.getDeclaredMethod("setWindowLayoutType", Integer.TYPE);
                sSetWindowLayoutTypeMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception unused) {
            }
            sSetWindowLayoutTypeMethodAttempted = true;
        }
        Method method = sSetWindowLayoutTypeMethod;
        if (method != null) {
            try {
                method.invoke(popupWindow, Integer.valueOf(i));
            } catch (Exception unused2) {
            }
        }
    }

    static class Api23Impl {
        static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
            popupWindow.setOverlapAnchor(z);
        }

        static boolean getOverlapAnchor(PopupWindow popupWindow) {
            return popupWindow.getOverlapAnchor();
        }

        static void setWindowLayoutType(PopupWindow popupWindow, int i) {
            popupWindow.setWindowLayoutType(i);
        }

        static int getWindowLayoutType(PopupWindow popupWindow) {
            return popupWindow.getWindowLayoutType();
        }
    }

    static class Api19Impl {
        static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            popupWindow.showAsDropDown(view, i, i2, i3);
        }
    }
}
