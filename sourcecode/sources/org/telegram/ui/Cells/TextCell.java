package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AnimatedTextView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RLottieImageView;
import org.telegram.ui.Components.Switch;

/* loaded from: classes4.dex */
public class TextCell extends FrameLayout {
    private int changeProgressStartDelay;
    private Switch checkBox;
    private boolean drawLoading;
    private float drawLoadingProgress;
    public int heightDp;
    public int imageLeft;
    public final RLottieImageView imageView;
    private boolean inDialogs;
    private boolean incrementLoadingProgress;
    private int lastWidth;
    private int leftPadding;
    private float loadingProgress;
    private int loadingSize;
    private boolean needDivider;
    public int offsetFromImage;
    Paint paint;
    private boolean prioritizeTitleOverValue;
    private Theme.ResourcesProvider resourcesProvider;
    private final SimpleTextView subtitleView;
    public final SimpleTextView textView;
    private ImageView valueImageView;
    public final SimpleTextView valueSpoilersTextView;
    private CharSequence valueText;
    public final AnimatedTextView valueTextView;

    protected int getOffsetFromImage(boolean z) {
        return z ? 65 : 71;
    }

    public TextCell(Context context) {
        this(context, 23, false, false, null);
    }

    public TextCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        this(context, 23, false, false, resourcesProvider);
    }

    public TextCell(Context context, int i, boolean z) {
        this(context, i, z, false, null);
    }

    public TextCell(Context context, int i, boolean z, boolean z2, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.offsetFromImage = 71;
        this.heightDp = 50;
        this.imageLeft = 21;
        this.resourcesProvider = resourcesProvider;
        this.leftPadding = i;
        SimpleTextView simpleTextView = new SimpleTextView(context);
        this.textView = simpleTextView;
        simpleTextView.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlack : Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
        simpleTextView.setTextSize(16);
        simpleTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        simpleTextView.setImportantForAccessibility(2);
        addView(simpleTextView, LayoutHelper.createFrame(-2, -1.0f));
        SimpleTextView simpleTextView2 = new SimpleTextView(context);
        this.subtitleView = simpleTextView2;
        simpleTextView2.setTextColor(Theme.getColor(z ? Theme.key_dialogTextGray : Theme.key_windowBackgroundWhiteGrayText, resourcesProvider));
        simpleTextView2.setTextSize(13);
        simpleTextView2.setGravity(LocaleController.isRTL ? 5 : 3);
        simpleTextView2.setImportantForAccessibility(2);
        addView(simpleTextView2, LayoutHelper.createFrame(-2, -1.0f));
        AnimatedTextView animatedTextView = new AnimatedTextView(context, false, false, true);
        this.valueTextView = animatedTextView;
        animatedTextView.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlue2 : Theme.key_windowBackgroundWhiteValueText, resourcesProvider));
        animatedTextView.setPadding(0, AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f));
        animatedTextView.setTextSize(AndroidUtilities.dp(16.0f));
        animatedTextView.setGravity(LocaleController.isRTL ? 3 : 5);
        animatedTextView.setImportantForAccessibility(2);
        animatedTextView.setTranslationY(AndroidUtilities.dp(-2.0f));
        addView(animatedTextView);
        SimpleTextView simpleTextView3 = new SimpleTextView(context);
        this.valueSpoilersTextView = simpleTextView3;
        simpleTextView3.setEllipsizeByGradient(18, Boolean.FALSE);
        simpleTextView3.setTextColor(Theme.getColor(z ? Theme.key_dialogTextBlue2 : Theme.key_windowBackgroundWhiteValueText, resourcesProvider));
        simpleTextView3.setGravity(LocaleController.isRTL ? 3 : 5);
        simpleTextView3.setTextSize(16);
        simpleTextView3.setImportantForAccessibility(2);
        simpleTextView3.setVisibility(8);
        addView(simpleTextView3);
        RLottieImageView rLottieImageView = new RLottieImageView(context);
        this.imageView = rLottieImageView;
        rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
        rLottieImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(z ? Theme.key_dialogIcon : Theme.key_windowBackgroundWhiteGrayIcon, resourcesProvider), PorterDuff.Mode.MULTIPLY));
        addView(rLottieImageView);
        ImageView imageView = new ImageView(context);
        this.valueImageView = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        addView(this.valueImageView);
        if (z2) {
            Switch r3 = new Switch(context, resourcesProvider);
            this.checkBox = r3;
            int i2 = Theme.key_switchTrack;
            int i3 = Theme.key_switchTrackChecked;
            int i4 = Theme.key_windowBackgroundWhite;
            r3.setColors(i2, i3, i4, i4);
            addView(this.checkBox, LayoutHelper.createFrame(37, 20.0f, (LocaleController.isRTL ? 3 : 5) | 16, 22.0f, 0.0f, 22.0f, 0.0f));
        }
        setFocusable(true);
    }

    public boolean isChecked() {
        Switch r0 = this.checkBox;
        return r0 != null && r0.isChecked();
    }

    public Switch getCheckBox() {
        return this.checkBox;
    }

    public void setIsInDialogs() {
        this.inDialogs = true;
    }

    public SimpleTextView getTextView() {
        return this.textView;
    }

    public RLottieImageView getImageView() {
        return this.imageView;
    }

    public AnimatedTextView getValueTextView() {
        return this.valueTextView;
    }

    public ImageView getValueImageView() {
        return this.valueImageView;
    }

    public void setPrioritizeTitleOverValue(boolean z) {
        if (this.prioritizeTitleOverValue != z) {
            this.prioritizeTitleOverValue = z;
            requestLayout();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        CharSequence charSequence;
        int size = View.MeasureSpec.getSize(i);
        int dp = AndroidUtilities.dp(this.heightDp);
        int i3 = this.lastWidth;
        if (i3 != 0 && i3 != size && (charSequence = this.valueText) != null) {
            AnimatedTextView animatedTextView = this.valueTextView;
            animatedTextView.setText(TextUtils.ellipsize(charSequence, animatedTextView.getPaint(), AndroidUtilities.displaySize.x / 2.5f, TextUtils.TruncateAt.END), false);
        }
        this.lastWidth = size;
        if (this.prioritizeTitleOverValue) {
            this.textView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(this.leftPadding + 71), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            this.subtitleView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(this.leftPadding + 71), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            this.valueTextView.measure(View.MeasureSpec.makeMeasureSpec((size - AndroidUtilities.dp(this.leftPadding + 103)) - this.textView.getTextWidth(), LocaleController.isRTL ? LinearLayoutManager.INVALID_OFFSET : 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            this.valueSpoilersTextView.measure(View.MeasureSpec.makeMeasureSpec((size - AndroidUtilities.dp(this.leftPadding + 103)) - this.textView.getTextWidth(), LocaleController.isRTL ? LinearLayoutManager.INVALID_OFFSET : 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        } else {
            this.valueTextView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(this.leftPadding), LocaleController.isRTL ? LinearLayoutManager.INVALID_OFFSET : 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            this.valueSpoilersTextView.measure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.dp(this.leftPadding), LocaleController.isRTL ? LinearLayoutManager.INVALID_OFFSET : 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            int max = Math.max(this.valueTextView.width(), this.valueSpoilersTextView.getTextWidth());
            this.textView.measure(View.MeasureSpec.makeMeasureSpec(Math.max(0, (size - AndroidUtilities.dp(this.leftPadding + 71)) - max), LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
            this.subtitleView.measure(View.MeasureSpec.makeMeasureSpec((size - AndroidUtilities.dp(this.leftPadding + 71)) - max, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        }
        if (this.imageView.getVisibility() == 0) {
            this.imageView.measure(View.MeasureSpec.makeMeasureSpec(size, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(dp, LinearLayoutManager.INVALID_OFFSET));
        }
        if (this.valueImageView.getVisibility() == 0) {
            this.valueImageView.measure(View.MeasureSpec.makeMeasureSpec(size, LinearLayoutManager.INVALID_OFFSET), View.MeasureSpec.makeMeasureSpec(dp, LinearLayoutManager.INVALID_OFFSET));
        }
        Switch r0 = this.checkBox;
        if (r0 != null) {
            r0.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(37.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(20.0f), 1073741824));
        }
        setMeasuredDimension(size, dp + (this.needDivider ? 1 : 0));
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        Switch r0 = this.checkBox;
        if (r0 != null) {
            r0.setEnabled(z);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int dp;
        int i5 = i4 - i2;
        int i6 = i3 - i;
        int max = (i5 - Math.max(this.valueSpoilersTextView.getTextHeight(), this.valueTextView.getTextHeight())) / 2;
        int dp2 = LocaleController.isRTL ? AndroidUtilities.dp(this.leftPadding) : (i6 - this.valueTextView.getMeasuredWidth()) - AndroidUtilities.dp(this.leftPadding);
        if (this.prioritizeTitleOverValue && !LocaleController.isRTL) {
            dp2 = (i6 - this.valueTextView.getMeasuredWidth()) - AndroidUtilities.dp(this.leftPadding);
        }
        AnimatedTextView animatedTextView = this.valueTextView;
        animatedTextView.layout(dp2, max, animatedTextView.getMeasuredWidth() + dp2, this.valueTextView.getMeasuredHeight() + max);
        int dp3 = LocaleController.isRTL ? AndroidUtilities.dp(this.leftPadding) : (i6 - this.valueSpoilersTextView.getMeasuredWidth()) - AndroidUtilities.dp(this.leftPadding);
        SimpleTextView simpleTextView = this.valueSpoilersTextView;
        simpleTextView.layout(dp3, max, simpleTextView.getMeasuredWidth() + dp3, this.valueSpoilersTextView.getMeasuredHeight() + max);
        if (LocaleController.isRTL) {
            dp = (getMeasuredWidth() - this.textView.getMeasuredWidth()) - AndroidUtilities.dp(this.imageView.getVisibility() == 0 ? this.offsetFromImage : this.leftPadding);
        } else {
            dp = AndroidUtilities.dp(this.imageView.getVisibility() == 0 ? this.offsetFromImage : this.leftPadding);
        }
        if (this.subtitleView.getVisibility() == 0) {
            float f = this.heightDp > 50 ? 4 : 2;
            int textHeight = (((i5 - this.textView.getTextHeight()) - this.subtitleView.getTextHeight()) - AndroidUtilities.dp(f)) / 2;
            SimpleTextView simpleTextView2 = this.textView;
            simpleTextView2.layout(dp, textHeight, simpleTextView2.getMeasuredWidth() + dp, this.textView.getMeasuredHeight() + textHeight);
            int textHeight2 = textHeight + this.textView.getTextHeight() + AndroidUtilities.dp(f);
            SimpleTextView simpleTextView3 = this.subtitleView;
            simpleTextView3.layout(dp, textHeight2, simpleTextView3.getMeasuredWidth() + dp, this.subtitleView.getMeasuredHeight() + textHeight2);
        } else {
            int textHeight3 = (i5 - this.textView.getTextHeight()) / 2;
            SimpleTextView simpleTextView4 = this.textView;
            simpleTextView4.layout(dp, textHeight3, simpleTextView4.getMeasuredWidth() + dp, this.textView.getMeasuredHeight() + textHeight3);
        }
        if (this.imageView.getVisibility() == 0) {
            int dp4 = (AndroidUtilities.dp(this.heightDp > 50 ? 0.0f : 2.0f) + ((i5 - this.imageView.getMeasuredHeight()) / 2)) - this.imageView.getPaddingTop();
            int dp5 = !LocaleController.isRTL ? AndroidUtilities.dp(this.imageLeft) : (i6 - this.imageView.getMeasuredWidth()) - AndroidUtilities.dp(this.imageLeft);
            RLottieImageView rLottieImageView = this.imageView;
            rLottieImageView.layout(dp5, dp4, rLottieImageView.getMeasuredWidth() + dp5, this.imageView.getMeasuredHeight() + dp4);
        }
        if (this.valueImageView.getVisibility() == 0) {
            int measuredHeight = (i5 - this.valueImageView.getMeasuredHeight()) / 2;
            int dp6 = LocaleController.isRTL ? AndroidUtilities.dp(23.0f) : (i6 - this.valueImageView.getMeasuredWidth()) - AndroidUtilities.dp(23.0f);
            ImageView imageView = this.valueImageView;
            imageView.layout(dp6, measuredHeight, imageView.getMeasuredWidth() + dp6, this.valueImageView.getMeasuredHeight() + measuredHeight);
        }
        Switch r6 = this.checkBox;
        if (r6 == null || r6.getVisibility() != 0) {
            return;
        }
        int measuredHeight2 = (i5 - this.checkBox.getMeasuredHeight()) / 2;
        int dp7 = LocaleController.isRTL ? AndroidUtilities.dp(22.0f) : (i6 - this.checkBox.getMeasuredWidth()) - AndroidUtilities.dp(22.0f);
        Switch r7 = this.checkBox;
        r7.layout(dp7, measuredHeight2, r7.getMeasuredWidth() + dp7, this.checkBox.getMeasuredHeight() + measuredHeight2);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    public void setColors(int i, int i2) {
        this.textView.setTextColor(Theme.getColor(i2, this.resourcesProvider));
        this.textView.setTag(Integer.valueOf(i2));
        if (i >= 0) {
            this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i, this.resourcesProvider), PorterDuff.Mode.MULTIPLY));
            this.imageView.setTag(Integer.valueOf(i));
        }
    }

    public void setText(String str, boolean z) {
        this.imageLeft = 21;
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = null;
        animatedTextView.setText(null, false);
        this.imageView.setVisibility(8);
        this.valueTextView.setVisibility(8);
        this.valueSpoilersTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    public void setTextAndIcon(String str, int i, boolean z) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = null;
        animatedTextView.setText(null, false);
        this.imageView.setImageResource(i);
        this.imageView.setVisibility(0);
        this.valueTextView.setVisibility(8);
        this.valueSpoilersTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    public void setTextAndIcon(String str, Drawable drawable, boolean z) {
        this.offsetFromImage = 68;
        this.imageLeft = 18;
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = null;
        animatedTextView.setText(null, false);
        this.imageView.setColorFilter((ColorFilter) null);
        if (drawable instanceof RLottieDrawable) {
            this.imageView.setAnimation((RLottieDrawable) drawable);
        } else {
            this.imageView.setImageDrawable(drawable);
        }
        this.imageView.setVisibility(0);
        this.valueTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.imageView.setPadding(0, AndroidUtilities.dp(6.0f), 0, 0);
        this.needDivider = z;
        setWillNotDraw(!z);
    }

    public void setOffsetFromImage(int i) {
        this.offsetFromImage = i;
    }

    public void setImageLeft(int i) {
        this.imageLeft = i;
    }

    public void setTextAndValue(String str, String str2, boolean z, boolean z2) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = str2;
        animatedTextView.setText(TextUtils.ellipsize(str2, animatedTextView.getPaint(), AndroidUtilities.displaySize.x / 2.5f, TextUtils.TruncateAt.END), z);
        this.valueTextView.setVisibility(0);
        this.valueSpoilersTextView.setVisibility(8);
        this.imageView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.needDivider = z2;
        setWillNotDraw(!z2);
        Switch r5 = this.checkBox;
        if (r5 != null) {
            r5.setVisibility(8);
        }
    }

    public void setTextAndValueAndColorfulIcon(String str, CharSequence charSequence, boolean z, int i, int i2, boolean z2) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = charSequence;
        animatedTextView.setText(TextUtils.ellipsize(charSequence, animatedTextView.getPaint(), AndroidUtilities.displaySize.x / 2.5f, TextUtils.TruncateAt.END), z);
        this.valueTextView.setVisibility(0);
        this.valueSpoilersTextView.setVisibility(8);
        setColorfulIcon(i2, i);
        this.valueImageView.setVisibility(8);
        this.needDivider = z2;
        setWillNotDraw(!z2);
        Switch r5 = this.checkBox;
        if (r5 != null) {
            r5.setVisibility(8);
        }
    }

    public void setTextAndSpoilersValueAndIcon(String str, CharSequence charSequence, int i, boolean z) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        this.valueSpoilersTextView.setVisibility(0);
        this.valueSpoilersTextView.setText(charSequence);
        this.valueTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.imageView.setVisibility(0);
        this.imageView.setTranslationX(0.0f);
        this.imageView.setTranslationY(0.0f);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(i);
        this.needDivider = z;
        setWillNotDraw(!z);
        Switch r3 = this.checkBox;
        if (r3 != null) {
            r3.setVisibility(8);
        }
    }

    public void setTextAndValueAndIcon(String str, String str2, int i, boolean z) {
        setTextAndValueAndIcon(str, str2, false, i, z);
    }

    public void setTextAndValueAndIcon(String str, String str2, boolean z, int i, boolean z2) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = str2;
        animatedTextView.setText(TextUtils.ellipsize(str2, animatedTextView.getPaint(), AndroidUtilities.displaySize.x / 2.5f, TextUtils.TruncateAt.END), z);
        this.valueTextView.setVisibility(0);
        this.valueSpoilersTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.imageView.setVisibility(0);
        this.imageView.setTranslationX(0.0f);
        this.imageView.setTranslationY(0.0f);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(i);
        this.needDivider = z2;
        setWillNotDraw(!z2);
        Switch r5 = this.checkBox;
        if (r5 != null) {
            r5.setVisibility(8);
        }
    }

    public void setColorfulIcon(int i, int i2) {
        this.offsetFromImage = getOffsetFromImage(true);
        this.imageView.setVisibility(0);
        this.imageView.setPadding(AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f));
        this.imageView.setTranslationX(AndroidUtilities.dp(LocaleController.isRTL ? 0.0f : -3.0f));
        this.imageView.setImageResource(i2);
        this.imageView.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
        this.imageView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.dp(9.0f), i));
    }

    public void setTextAndCheck(CharSequence charSequence, boolean z, boolean z2) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(charSequence);
        this.imageView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        this.needDivider = z2;
        Switch r3 = this.checkBox;
        if (r3 != null) {
            r3.setVisibility(0);
            this.checkBox.setChecked(z, false);
        }
        this.needDivider = z2;
        setWillNotDraw(!z2);
    }

    public void setTextAndCheckAndIcon(CharSequence charSequence, boolean z, int i, boolean z2) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(charSequence);
        this.valueTextView.setVisibility(8);
        this.valueSpoilersTextView.setVisibility(8);
        this.valueImageView.setVisibility(8);
        Switch r3 = this.checkBox;
        if (r3 != null) {
            r3.setVisibility(0);
            this.checkBox.setChecked(z, false);
        }
        this.imageView.setVisibility(0);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.imageView.setImageResource(i);
        this.needDivider = z2;
        setWillNotDraw(!z2);
    }

    public void setTextAndValueDrawable(String str, Drawable drawable, boolean z) {
        this.imageLeft = 21;
        this.offsetFromImage = getOffsetFromImage(false);
        this.textView.setText(str);
        AnimatedTextView animatedTextView = this.valueTextView;
        this.valueText = null;
        animatedTextView.setText(null, false);
        this.valueImageView.setVisibility(0);
        this.valueImageView.setImageDrawable(drawable);
        this.valueTextView.setVisibility(8);
        this.valueSpoilersTextView.setVisibility(8);
        this.imageView.setVisibility(8);
        this.imageView.setPadding(0, AndroidUtilities.dp(7.0f), 0, 0);
        this.needDivider = z;
        setWillNotDraw(!z);
        Switch r3 = this.checkBox;
        if (r3 != null) {
            r3.setVisibility(8);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        float dp;
        int i;
        if (this.needDivider) {
            float f2 = 20.0f;
            if (LocaleController.isRTL) {
                dp = 0.0f;
            } else {
                if (this.imageView.getVisibility() == 0) {
                    f = this.inDialogs ? 72 : 68;
                } else {
                    f = 20.0f;
                }
                dp = AndroidUtilities.dp(f);
            }
            float measuredHeight = getMeasuredHeight() - 1;
            int measuredWidth = getMeasuredWidth();
            if (LocaleController.isRTL) {
                if (this.imageView.getVisibility() == 0) {
                    f2 = this.inDialogs ? 72 : 68;
                }
                i = AndroidUtilities.dp(f2);
            } else {
                i = 0;
            }
            canvas.drawLine(dp, measuredHeight, measuredWidth - i, getMeasuredHeight() - 1, Theme.dividerPaint);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        CharSequence text = this.textView.getText();
        if (!TextUtils.isEmpty(text)) {
            CharSequence text2 = this.valueTextView.getText();
            if (!TextUtils.isEmpty(text2)) {
                text = TextUtils.concat(text, ": ", text2);
            }
        }
        if (this.checkBox != null) {
            accessibilityNodeInfo.setClassName("android.widget.Switch");
            accessibilityNodeInfo.setCheckable(true);
            accessibilityNodeInfo.setChecked(this.checkBox.isChecked());
            StringBuilder sb = new StringBuilder();
            sb.append(this.textView.getText());
            if (!TextUtils.isEmpty(this.valueTextView.getText())) {
                sb.append('\n');
                sb.append(this.valueTextView.getText());
            }
            accessibilityNodeInfo.setContentDescription(sb);
        } else if (!TextUtils.isEmpty(text)) {
            accessibilityNodeInfo.setText(text);
        }
        accessibilityNodeInfo.addAction(16);
    }

    public void setNeedDivider(boolean z) {
        if (this.needDivider != z) {
            this.needDivider = z;
            setWillNotDraw(!z);
            invalidate();
        }
    }

    public void setChecked(boolean z) {
        this.checkBox.setChecked(z, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setDrawLoading(boolean z, int i, boolean z2) {
        this.drawLoading = z;
        this.loadingSize = i;
        if (!z2) {
            this.drawLoadingProgress = z ? 1.0f : 0.0f;
        }
        invalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        if (this.drawLoading || this.drawLoadingProgress != 0.0f) {
            if (this.paint == null) {
                Paint paint = new Paint(1);
                this.paint = paint;
                paint.setColor(Theme.getColor(Theme.key_dialogSearchBackground, this.resourcesProvider));
            }
            if (this.incrementLoadingProgress) {
                float f = this.loadingProgress + 0.016f;
                this.loadingProgress = f;
                if (f > 1.0f) {
                    this.loadingProgress = 1.0f;
                    this.incrementLoadingProgress = false;
                }
            } else {
                float f2 = this.loadingProgress - 0.016f;
                this.loadingProgress = f2;
                if (f2 < 0.0f) {
                    this.loadingProgress = 0.0f;
                    this.incrementLoadingProgress = true;
                }
            }
            int i = this.changeProgressStartDelay;
            if (i > 0) {
                this.changeProgressStartDelay = i - 15;
            } else {
                boolean z = this.drawLoading;
                if (z) {
                    float f3 = this.drawLoadingProgress;
                    if (f3 != 1.0f) {
                        float f4 = f3 + 0.10666667f;
                        this.drawLoadingProgress = f4;
                        if (f4 > 1.0f) {
                            this.drawLoadingProgress = 1.0f;
                        }
                    }
                }
                if (!z) {
                    float f5 = this.drawLoadingProgress;
                    if (f5 != 0.0f) {
                        float f6 = f5 - 0.10666667f;
                        this.drawLoadingProgress = f6;
                        if (f6 < 0.0f) {
                            this.drawLoadingProgress = 0.0f;
                        }
                    }
                }
            }
            this.paint.setAlpha((int) (((this.loadingProgress * 0.4f) + 0.6f) * this.drawLoadingProgress * 255.0f));
            int measuredHeight = getMeasuredHeight() >> 1;
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set((getMeasuredWidth() - AndroidUtilities.dp(21.0f)) - AndroidUtilities.dp(this.loadingSize), measuredHeight - AndroidUtilities.dp(3.0f), getMeasuredWidth() - AndroidUtilities.dp(21.0f), measuredHeight + AndroidUtilities.dp(3.0f));
            if (LocaleController.isRTL) {
                rectF.left = getMeasuredWidth() - rectF.left;
                rectF.right = getMeasuredWidth() - rectF.right;
            }
            canvas.drawRoundRect(rectF, AndroidUtilities.dp(3.0f), AndroidUtilities.dp(3.0f), this.paint);
            invalidate();
        }
        this.valueTextView.setAlpha(1.0f - this.drawLoadingProgress);
        this.valueSpoilersTextView.setAlpha(1.0f - this.drawLoadingProgress);
        super.dispatchDraw(canvas);
    }

    public void setSubtitle(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.subtitleView.setVisibility(0);
            this.subtitleView.setText(charSequence);
        } else {
            this.subtitleView.setVisibility(8);
        }
    }
}
