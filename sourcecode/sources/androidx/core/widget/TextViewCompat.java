package androidx.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormatSymbols;
import android.os.Build;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.core.text.PrecomputedTextCompat;
import androidx.core.util.Preconditions;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class TextViewCompat {
    public static void setCompoundDrawablesRelative(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 18) {
            Api17Impl.setCompoundDrawablesRelative(textView, drawable, drawable2, drawable3, drawable4);
            return;
        }
        if (i >= 17) {
            boolean z = Api17Impl.getLayoutDirection(textView) == 1;
            Drawable drawable5 = z ? drawable3 : drawable;
            if (!z) {
                drawable = drawable3;
            }
            textView.setCompoundDrawables(drawable5, drawable2, drawable, drawable4);
            return;
        }
        textView.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    public static void setTextAppearance(TextView textView, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            textView.setTextAppearance(i);
        } else {
            textView.setTextAppearance(textView.getContext(), i);
        }
    }

    public static Drawable[] getCompoundDrawablesRelative(TextView textView) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 18) {
            return Api17Impl.getCompoundDrawablesRelative(textView);
        }
        if (i >= 17) {
            boolean z = Api17Impl.getLayoutDirection(textView) == 1;
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            if (z) {
                Drawable drawable = compoundDrawables[2];
                Drawable drawable2 = compoundDrawables[0];
                compoundDrawables[0] = drawable;
                compoundDrawables[2] = drawable2;
            }
            return compoundDrawables;
        }
        return textView.getCompoundDrawables();
    }

    public static ActionMode.Callback wrapCustomSelectionActionModeCallback(TextView textView, ActionMode.Callback callback) {
        int i = Build.VERSION.SDK_INT;
        return (i < 26 || i > 27 || (callback instanceof OreoCallback) || callback == null) ? callback : new OreoCallback(callback, textView);
    }

    public static ActionMode.Callback unwrapCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        return (!(callback instanceof OreoCallback) || Build.VERSION.SDK_INT < 26) ? callback : ((OreoCallback) callback).getWrappedCallback();
    }

    private static class OreoCallback implements ActionMode.Callback {
        private final ActionMode.Callback mCallback;
        private boolean mCanUseMenuBuilderReferences;
        private boolean mInitializedMenuBuilderReferences = false;
        private Class<?> mMenuBuilderClass;
        private Method mMenuBuilderRemoveItemAtMethod;
        private final TextView mTextView;

        OreoCallback(ActionMode.Callback callback, TextView textView) {
            this.mCallback = callback;
            this.mTextView = textView;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mCallback.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            recomputeProcessTextMenuItems(menu);
            return this.mCallback.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mCallback.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mCallback.onDestroyActionMode(actionMode);
        }

        ActionMode.Callback getWrappedCallback() {
            return this.mCallback;
        }

        private void recomputeProcessTextMenuItems(Menu menu) {
            Method declaredMethod;
            Context context = this.mTextView.getContext();
            PackageManager packageManager = context.getPackageManager();
            if (!this.mInitializedMenuBuilderReferences) {
                this.mInitializedMenuBuilderReferences = true;
                try {
                    Class<?> cls = Class.forName("com.android.internal.view.menu.MenuBuilder");
                    this.mMenuBuilderClass = cls;
                    this.mMenuBuilderRemoveItemAtMethod = cls.getDeclaredMethod("removeItemAt", Integer.TYPE);
                    this.mCanUseMenuBuilderReferences = true;
                } catch (ClassNotFoundException | NoSuchMethodException unused) {
                    this.mMenuBuilderClass = null;
                    this.mMenuBuilderRemoveItemAtMethod = null;
                    this.mCanUseMenuBuilderReferences = false;
                }
            }
            try {
                if (this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance(menu)) {
                    declaredMethod = this.mMenuBuilderRemoveItemAtMethod;
                } else {
                    declaredMethod = menu.getClass().getDeclaredMethod("removeItemAt", Integer.TYPE);
                }
                for (int size = menu.size() - 1; size >= 0; size--) {
                    MenuItem item = menu.getItem(size);
                    if (item.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(item.getIntent().getAction())) {
                        declaredMethod.invoke(menu, Integer.valueOf(size));
                    }
                }
                List<ResolveInfo> supportedActivities = getSupportedActivities(context, packageManager);
                for (int i = 0; i < supportedActivities.size(); i++) {
                    ResolveInfo resolveInfo = supportedActivities.get(i);
                    menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo, this.mTextView)).setShowAsAction(1);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused2) {
            }
        }

        private List<ResolveInfo> getSupportedActivities(Context context, PackageManager packageManager) {
            ArrayList arrayList = new ArrayList();
            if (!(context instanceof Activity)) {
                return arrayList;
            }
            for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(resolveInfo, context)) {
                    arrayList.add(resolveInfo);
                }
            }
            return arrayList;
        }

        private boolean isSupportedActivity(ResolveInfo resolveInfo, Context context) {
            if (context.getPackageName().equals(resolveInfo.activityInfo.packageName)) {
                return true;
            }
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (!activityInfo.exported) {
                return false;
            }
            String str = activityInfo.permission;
            return str == null || context.checkSelfPermission(str) == 0;
        }

        private Intent createProcessTextIntentForResolveInfo(ResolveInfo resolveInfo, TextView textView) {
            Intent putExtra = createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", !isEditable(textView));
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            return putExtra.setClassName(activityInfo.packageName, activityInfo.name);
        }

        private boolean isEditable(TextView textView) {
            return (textView instanceof Editable) && textView.onCheckIsTextEditor() && textView.isEnabled();
        }

        private Intent createProcessTextIntent() {
            return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType(COSRequestHeaderKey.TEXT_PLAIN);
        }
    }

    public static void setFirstBaselineToTopHeight(TextView textView, int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 28) {
            Api28Impl.setFirstBaselineToTopHeight(textView, i);
            return;
        }
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (i3 < 16 || Api16Impl.getIncludeFontPadding(textView)) {
            i2 = fontMetricsInt.top;
        } else {
            i2 = fontMetricsInt.ascent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), i + i2, textView.getPaddingRight(), textView.getPaddingBottom());
        }
    }

    public static void setLastBaselineToBottomHeight(TextView textView, int i) {
        int i2;
        Preconditions.checkArgumentNonnegative(i);
        Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
        if (Build.VERSION.SDK_INT < 16 || Api16Impl.getIncludeFontPadding(textView)) {
            i2 = fontMetricsInt.bottom;
        } else {
            i2 = fontMetricsInt.descent;
        }
        if (i > Math.abs(i2)) {
            textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), i - i2);
        }
    }

    public static int getFirstBaselineToTopHeight(TextView textView) {
        return textView.getPaddingTop() - textView.getPaint().getFontMetricsInt().top;
    }

    public static int getLastBaselineToBottomHeight(TextView textView) {
        return textView.getPaddingBottom() + textView.getPaint().getFontMetricsInt().bottom;
    }

    public static void setLineHeight(TextView textView, int i) {
        Preconditions.checkArgumentNonnegative(i);
        if (i != textView.getPaint().getFontMetricsInt(null)) {
            textView.setLineSpacing(i - r0, 1.0f);
        }
    }

    public static PrecomputedTextCompat.Params getTextMetricsParams(TextView textView) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            return new PrecomputedTextCompat.Params(Api28Impl.getTextMetricsParams(textView));
        }
        PrecomputedTextCompat.Params.Builder builder = new PrecomputedTextCompat.Params.Builder(new TextPaint(textView.getPaint()));
        if (i >= 23) {
            builder.setBreakStrategy(Api23Impl.getBreakStrategy(textView));
            builder.setHyphenationFrequency(Api23Impl.getHyphenationFrequency(textView));
        }
        if (i >= 18) {
            builder.setTextDirection(getTextDirectionHeuristic(textView));
        }
        return builder.build();
    }

    public static void setTextMetricsParams(TextView textView, PrecomputedTextCompat.Params params) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 18) {
            Api17Impl.setTextDirection(textView, getTextDirection(params.getTextDirection()));
        }
        if (i < 23) {
            float textScaleX = params.getTextPaint().getTextScaleX();
            textView.getPaint().set(params.getTextPaint());
            if (textScaleX == textView.getTextScaleX()) {
                textView.setTextScaleX((textScaleX / 2.0f) + 1.0f);
            }
            textView.setTextScaleX(textScaleX);
            return;
        }
        textView.getPaint().set(params.getTextPaint());
        Api23Impl.setBreakStrategy(textView, params.getBreakStrategy());
        Api23Impl.setHyphenationFrequency(textView, params.getHyphenationFrequency());
    }

    public static void setPrecomputedText(TextView textView, PrecomputedTextCompat precomputedTextCompat) {
        if (Build.VERSION.SDK_INT >= 29) {
            textView.setText(precomputedTextCompat.getPrecomputedText());
        } else {
            if (!getTextMetricsParams(textView).equalsWithoutTextDirection(precomputedTextCompat.getParams())) {
                throw new IllegalArgumentException("Given text can not be applied to TextView.");
            }
            textView.setText(precomputedTextCompat);
        }
    }

    private static TextDirectionHeuristic getTextDirectionHeuristic(TextView textView) {
        if (textView.getTransformationMethod() instanceof PasswordTransformationMethod) {
            return TextDirectionHeuristics.LTR;
        }
        if (Build.VERSION.SDK_INT >= 28 && (textView.getInputType() & 15) == 3) {
            byte directionality = Character.getDirectionality(Api28Impl.getDigitStrings(Api24Impl.getInstance(Api17Impl.getTextLocale(textView)))[0].codePointAt(0));
            if (directionality == 1 || directionality == 2) {
                return TextDirectionHeuristics.RTL;
            }
            return TextDirectionHeuristics.LTR;
        }
        boolean z = Api17Impl.getLayoutDirection(textView) == 1;
        switch (Api17Impl.getTextDirection(textView)) {
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                if (!z) {
                    break;
                } else {
                    break;
                }
        }
        return TextDirectionHeuristics.LTR;
    }

    private static int getTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL || textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 1;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.ANYRTL_LTR) {
            return 2;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LTR) {
            return 3;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.RTL) {
            return 4;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.LOCALE) {
            return 5;
        }
        if (textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
            return 6;
        }
        return textDirectionHeuristic == TextDirectionHeuristics.FIRSTSTRONG_RTL ? 7 : 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setCompoundDrawableTintList(TextView textView, ColorStateList colorStateList) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            Api23Impl.setCompoundDrawableTintList(textView, colorStateList);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintList(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setCompoundDrawableTintMode(TextView textView, PorterDuff.Mode mode) {
        Preconditions.checkNotNull(textView);
        if (Build.VERSION.SDK_INT >= 24) {
            Api23Impl.setCompoundDrawableTintMode(textView, mode);
        } else if (textView instanceof TintableCompoundDrawablesView) {
            ((TintableCompoundDrawablesView) textView).setSupportCompoundDrawablesTintMode(mode);
        }
    }

    static class Api17Impl {
        static void setCompoundDrawablesRelative(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        }

        static int getLayoutDirection(View view) {
            return view.getLayoutDirection();
        }

        static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView textView, int i, int i2, int i3, int i4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        }

        static Drawable[] getCompoundDrawablesRelative(TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }

        static void setTextDirection(View view, int i) {
            view.setTextDirection(i);
        }

        static Locale getTextLocale(TextView textView) {
            return textView.getTextLocale();
        }

        static int getTextDirection(View view) {
            return view.getTextDirection();
        }
    }

    static class Api16Impl {
        static int getMaxLines(TextView textView) {
            return textView.getMaxLines();
        }

        static int getMinLines(TextView textView) {
            return textView.getMinLines();
        }

        static boolean getIncludeFontPadding(TextView textView) {
            return textView.getIncludeFontPadding();
        }
    }

    static class Api28Impl {
        static void setFirstBaselineToTopHeight(TextView textView, int i) {
            textView.setFirstBaselineToTopHeight(i);
        }

        static PrecomputedText.Params getTextMetricsParams(TextView textView) {
            return textView.getTextMetricsParams();
        }

        static String[] getDigitStrings(DecimalFormatSymbols decimalFormatSymbols) {
            return decimalFormatSymbols.getDigitStrings();
        }
    }

    static class Api23Impl {
        static int getBreakStrategy(TextView textView) {
            return textView.getBreakStrategy();
        }

        static void setBreakStrategy(TextView textView, int i) {
            textView.setBreakStrategy(i);
        }

        static int getHyphenationFrequency(TextView textView) {
            return textView.getHyphenationFrequency();
        }

        static void setHyphenationFrequency(TextView textView, int i) {
            textView.setHyphenationFrequency(i);
        }

        static PorterDuff.Mode getCompoundDrawableTintMode(TextView textView) {
            return textView.getCompoundDrawableTintMode();
        }

        static ColorStateList getCompoundDrawableTintList(TextView textView) {
            return textView.getCompoundDrawableTintList();
        }

        static void setCompoundDrawableTintList(TextView textView, ColorStateList colorStateList) {
            textView.setCompoundDrawableTintList(colorStateList);
        }

        static void setCompoundDrawableTintMode(TextView textView, PorterDuff.Mode mode) {
            textView.setCompoundDrawableTintMode(mode);
        }
    }

    static class Api24Impl {
        static DecimalFormatSymbols getInstance(Locale locale) {
            return DecimalFormatSymbols.getInstance(locale);
        }
    }
}
