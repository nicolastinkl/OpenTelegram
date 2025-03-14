package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CheckBox2;
import org.telegram.ui.Components.CheckBoxSquare;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes4.dex */
public class CheckBoxCell extends FrameLayout {
    private final View checkBox;
    private CheckBox2 checkBoxRound;
    private final int checkBoxSize;
    private CheckBoxSquare checkBoxSquare;
    private View click1Container;
    private View click2Container;
    private View collapsedArrow;
    private final int currentType;
    private boolean isMultiline;
    private boolean needDivider;
    private final Theme.ResourcesProvider resourcesProvider;
    private final TextView textView;
    private final TextView valueTextView;

    public CheckBoxCell(Context context, int i) {
        this(context, i, 17, null);
    }

    public CheckBoxCell(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
        this(context, i, 17, resourcesProvider);
    }

    public CheckBoxCell(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.resourcesProvider = resourcesProvider;
        this.currentType = i;
        TextView textView = new TextView(context) { // from class: org.telegram.ui.Cells.CheckBoxCell.1
            @Override // android.widget.TextView, android.view.View
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                CheckBoxCell.this.updateCollapseArrowTranslation();
            }

            @Override // android.widget.TextView
            public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
            }
        };
        this.textView = textView;
        NotificationCenter.listenEmojiLoading(textView);
        boolean z = true;
        textView.setTag(Integer.valueOf(getThemedColor((i == 1 || i == 5) ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText)));
        textView.setTextSize(1, 16.0f);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        if (i == 3) {
            textView.setGravity(19);
            addView(textView, LayoutHelper.createFrame(-1, -1.0f, 51, 29.0f, 0.0f, 0.0f, 0.0f));
            textView.setPadding(0, 0, 0, AndroidUtilities.dp(3.0f));
        } else {
            textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            if (i == 2) {
                boolean z2 = LocaleController.isRTL;
                addView(textView, LayoutHelper.createFrame(-1, -1.0f, (z2 ? 5 : 3) | 48, z2 ? 8 : 29, 0.0f, z2 ? 29 : 8, 0.0f));
            } else {
                int i3 = i == 4 ? 56 : 46;
                int i4 = i == 4 ? -2 : -1;
                boolean z3 = LocaleController.isRTL;
                addView(textView, LayoutHelper.createFrame(i4, -1.0f, (z3 ? 5 : 3) | 48, z3 ? i2 : i3 + (i2 - 17), 0.0f, z3 ? i3 + (i2 - 17) : i2, 0.0f));
            }
        }
        TextView textView2 = new TextView(context);
        this.valueTextView = textView2;
        textView2.setTag(Integer.valueOf((i == 1 || i == 5) ? Theme.key_dialogTextBlue : Theme.key_windowBackgroundWhiteValueText));
        textView2.setTextSize(1, 16.0f);
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setGravity((LocaleController.isRTL ? 3 : 5) | 16);
        float f = i2;
        addView(textView2, LayoutHelper.createFrame(-2, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, f, 0.0f, f, 0.0f));
        if (i == 4) {
            CheckBox2 checkBox2 = new CheckBox2(context, 21, resourcesProvider);
            this.checkBoxRound = checkBox2;
            this.checkBox = checkBox2;
            checkBox2.setDrawUnchecked(true);
            this.checkBoxRound.setChecked(true, false);
            this.checkBoxRound.setDrawBackgroundAsArc(10);
            this.checkBoxSize = 21;
            float f2 = 21;
            boolean z4 = LocaleController.isRTL;
            addView(checkBox2, LayoutHelper.createFrame(21, f2, (z4 ? 5 : 3) | 48, z4 ? 0 : i2, 16.0f, z4 ? i2 : 0, 0.0f));
        } else {
            if (i != 1 && i != 5) {
                z = false;
            }
            CheckBoxSquare checkBoxSquare = new CheckBoxSquare(context, z, resourcesProvider);
            this.checkBoxSquare = checkBoxSquare;
            this.checkBox = checkBoxSquare;
            this.checkBoxSize = 18;
            if (i == 5) {
                float f3 = 18;
                boolean z5 = LocaleController.isRTL;
                addView(checkBoxSquare, LayoutHelper.createFrame(18, f3, (z5 ? 5 : 3) | 16, z5 ? 0 : i2, 0.0f, z5 ? i2 : 0, 0.0f));
            } else if (i == 3) {
                addView(checkBoxSquare, LayoutHelper.createFrame(18, 18, 51, 0.0f, 15.0f, 0.0f, 0.0f));
            } else if (i == 2) {
                addView(checkBoxSquare, LayoutHelper.createFrame(18, 18, (LocaleController.isRTL ? 5 : 3) | 48, 0.0f, 15.0f, 0.0f, 0.0f));
            } else {
                float f4 = 18;
                boolean z6 = LocaleController.isRTL;
                addView(checkBoxSquare, LayoutHelper.createFrame(18, f4, (z6 ? 5 : 3) | 48, z6 ? 0 : i2, 16.0f, z6 ? i2 : 0, 0.0f));
            }
        }
        updateTextColor();
    }

    public void updateTextColor() {
        TextView textView = this.textView;
        int i = this.currentType;
        textView.setTextColor(getThemedColor((i == 1 || i == 5) ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText));
        TextView textView2 = this.textView;
        int i2 = this.currentType;
        textView2.setLinkTextColor(getThemedColor((i2 == 1 || i2 == 5) ? Theme.key_dialogTextLink : Theme.key_windowBackgroundWhiteLinkText));
        TextView textView3 = this.valueTextView;
        int i3 = this.currentType;
        textView3.setTextColor(getThemedColor((i3 == 1 || i3 == 5) ? Theme.key_dialogTextBlue : Theme.key_windowBackgroundWhiteValueText));
    }

    public void setOnSectionsClickListener(View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        if (onClickListener == null) {
            View view = this.click1Container;
            if (view != null) {
                removeView(view);
                this.click1Container = null;
            }
        } else {
            if (this.click1Container == null) {
                View view2 = new View(getContext());
                this.click1Container = view2;
                view2.setBackground(Theme.createSelectorDrawable(getThemedColor(Theme.key_listSelector), 2));
                addView(this.click1Container, LayoutHelper.createFrame(-1, -1, 119));
            }
            this.click1Container.setOnClickListener(onClickListener);
        }
        if (onClickListener2 == null) {
            View view3 = this.click2Container;
            if (view3 != null) {
                removeView(view3);
                this.click2Container = null;
                return;
            }
            return;
        }
        if (this.click2Container == null) {
            View view4 = new View(getContext());
            this.click2Container = view4;
            addView(view4, LayoutHelper.createFrame(56, -1, LocaleController.isRTL ? 5 : 3));
        }
        this.click2Container.setOnClickListener(onClickListener2);
    }

    public void setCollapsed(Boolean bool) {
        if (bool == null) {
            View view = this.collapsedArrow;
            if (view != null) {
                removeView(view);
                this.collapsedArrow = null;
                return;
            }
            return;
        }
        if (this.collapsedArrow == null) {
            this.collapsedArrow = new View(getContext());
            Drawable mutate = getContext().getResources().getDrawable(R.drawable.arrow_more).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteBlackText), PorterDuff.Mode.MULTIPLY));
            this.collapsedArrow.setBackground(mutate);
            addView(this.collapsedArrow, LayoutHelper.createFrame(16, 16, 16));
        }
        updateCollapseArrowTranslation();
        this.collapsedArrow.animate().cancel();
        this.collapsedArrow.animate().rotation(bool.booleanValue() ? 0.0f : 180.0f).setDuration(340L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCollapseArrowTranslation() {
        float left;
        if (this.collapsedArrow == null) {
            return;
        }
        float f = 0.0f;
        try {
            f = this.textView.getMeasuredWidth();
        } catch (Exception unused) {
        }
        if (LocaleController.isRTL) {
            left = (this.textView.getRight() - f) - AndroidUtilities.dp(20.0f);
        } else {
            left = this.textView.getLeft() + f + AndroidUtilities.dp(4.0f);
        }
        this.collapsedArrow.setTranslationX(left);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        if (this.currentType == 3) {
            this.valueTextView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(10.0f), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(50.0f), 1073741824));
            this.textView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(34.0f), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(50.0f), 1073741824));
            this.checkBox.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.checkBoxSize), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.checkBoxSize), 1073741824));
            setMeasuredDimension(this.textView.getMeasuredWidth() + AndroidUtilities.dp(29.0f), AndroidUtilities.dp(50.0f));
        } else if (this.isMultiline) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        } else {
            setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.dp(50.0f) + (this.needDivider ? 1 : 0));
            int measuredWidth = ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) - AndroidUtilities.dp(this.currentType == 4 ? 60.0f : 34.0f);
            if (this.valueTextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                measuredWidth -= ((ViewGroup.MarginLayoutParams) this.valueTextView.getLayoutParams()).rightMargin;
            }
            this.valueTextView.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth / 2, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            TextView textView = this.textView;
            textView.measure(View.MeasureSpec.makeMeasureSpec(((measuredWidth - ((int) Math.abs(textView.getTranslationX()))) - this.valueTextView.getMeasuredWidth()) - AndroidUtilities.dp(8.0f), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            this.checkBox.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.checkBoxSize), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(this.checkBoxSize), 1073741824));
        }
        View view = this.click1Container;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            this.click1Container.measure(View.MeasureSpec.makeMeasureSpec((size - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin, 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(50.0f), 1073741824));
        }
        View view2 = this.click2Container;
        if (view2 != null) {
            view2.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(56.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(50.0f), 1073741824));
        }
        View view3 = this.collapsedArrow;
        if (view3 != null) {
            view3.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(16.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(16.0f), 1073741824));
        }
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setText(CharSequence charSequence, String str, boolean z, boolean z2) {
        setText(charSequence, str, z, z2, false);
    }

    public void setText(CharSequence charSequence, String str, boolean z, boolean z2, boolean z3) {
        this.textView.setText(charSequence);
        CheckBox2 checkBox2 = this.checkBoxRound;
        if (checkBox2 != null) {
            checkBox2.setChecked(z, z3);
        } else {
            this.checkBoxSquare.setChecked(z, z3);
        }
        this.valueTextView.setText(str);
        this.needDivider = z2;
        setWillNotDraw(!z2);
    }

    public void setPad(int i) {
        int dp = AndroidUtilities.dp(i * 40 * (LocaleController.isRTL ? -1 : 1));
        View view = this.checkBox;
        if (view != null) {
            view.setTranslationX(dp);
        }
        float f = dp;
        this.textView.setTranslationX(f);
        View view2 = this.click1Container;
        if (view2 != null) {
            view2.setTranslationX(f);
        }
        View view3 = this.click2Container;
        if (view3 != null) {
            view3.setTranslationX(f);
        }
    }

    public void setNeedDivider(boolean z) {
        this.needDivider = z;
    }

    public void setMultiline(boolean z) {
        this.isMultiline = z;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.textView.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.checkBox.getLayoutParams();
        if (this.isMultiline) {
            this.textView.setLines(0);
            this.textView.setMaxLines(0);
            this.textView.setSingleLine(false);
            this.textView.setEllipsize(null);
            if (this.currentType != 5) {
                this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(5.0f));
                layoutParams.height = -2;
                layoutParams.topMargin = AndroidUtilities.dp(10.0f);
                layoutParams2.topMargin = AndroidUtilities.dp(12.0f);
            }
        } else {
            this.textView.setLines(1);
            this.textView.setMaxLines(1);
            this.textView.setSingleLine(true);
            this.textView.setEllipsize(TextUtils.TruncateAt.END);
            this.textView.setPadding(0, 0, 0, 0);
            layoutParams.height = -1;
            layoutParams.topMargin = 0;
            layoutParams2.topMargin = AndroidUtilities.dp(15.0f);
        }
        this.textView.setLayoutParams(layoutParams);
        this.checkBox.setLayoutParams(layoutParams2);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.textView.setAlpha(z ? 1.0f : 0.5f);
        this.valueTextView.setAlpha(z ? 1.0f : 0.5f);
        this.checkBox.setAlpha(z ? 1.0f : 0.5f);
    }

    public void setChecked(boolean z, boolean z2) {
        CheckBox2 checkBox2 = this.checkBoxRound;
        if (checkBox2 != null) {
            checkBox2.setChecked(z, z2);
        } else {
            this.checkBoxSquare.setChecked(z, z2);
        }
    }

    public boolean isChecked() {
        CheckBox2 checkBox2 = this.checkBoxRound;
        if (checkBox2 != null) {
            return checkBox2.isChecked();
        }
        return this.checkBoxSquare.isChecked();
    }

    public TextView getTextView() {
        return this.textView;
    }

    public TextView getValueTextView() {
        return this.valueTextView;
    }

    public View getCheckBoxView() {
        return this.checkBox;
    }

    public void setCheckBoxColor(int i, int i2, int i3) {
        CheckBox2 checkBox2 = this.checkBoxRound;
        if (checkBox2 != null) {
            checkBox2.setColor(i, i, i3);
        }
    }

    public CheckBox2 getCheckBoxRound() {
        return this.checkBoxRound;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            int dp = AndroidUtilities.dp(this.currentType == 4 ? 60.0f : 20.0f) + ((int) Math.abs(this.textView.getTranslationX()));
            float f = LocaleController.isRTL ? 0.0f : dp;
            float measuredHeight = getMeasuredHeight() - 1;
            int measuredWidth = getMeasuredWidth();
            if (!LocaleController.isRTL) {
                dp = 0;
            }
            canvas.drawLine(f, measuredHeight, measuredWidth - dp, getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.CheckBox");
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(isChecked());
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public void setIcon(int i) {
        this.checkBoxRound.setIcon(i);
    }

    public boolean hasIcon() {
        return this.checkBoxRound.hasIcon();
    }
}
