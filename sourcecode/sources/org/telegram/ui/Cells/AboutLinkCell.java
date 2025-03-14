package org.telegram.ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.AboutLinkCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LinkPath;
import org.telegram.ui.Components.LinkSpanDrawable;
import org.telegram.ui.Components.LoadingDrawable;
import org.telegram.ui.Components.StaticLayoutEx;
import org.telegram.ui.Components.URLSpanNoUnderline;

/* loaded from: classes4.dex */
public class AboutLinkCell extends FrameLayout {
    private static final int COLLAPSED_HEIGHT = AndroidUtilities.dp(76.0f);
    private static final int MOST_SPEC = View.MeasureSpec.makeMeasureSpec(999999, LinearLayoutManager.INVALID_OFFSET);
    final float SPACE;
    private Paint backgroundPaint;
    private FrameLayout bottomShadow;
    private ValueAnimator collapseAnimator;
    private FrameLayout container;
    private LoadingDrawable currentLoading;
    private Browser.Progress currentProgress;
    private float expandT;
    private boolean expanded;
    private StaticLayout firstThreeLinesLayout;
    private int lastInlineLine;
    private int lastMaxWidth;
    private LinkSpanDrawable.LinkCollector links;
    Runnable longPressedRunnable;
    private boolean moreButtonDisabled;
    private boolean needSpace;
    private StaticLayout[] nextLinesLayouts;
    private Point[] nextLinesLayoutsPositions;
    private String oldText;
    private BaseFragment parentFragment;
    private LinkSpanDrawable pressedLink;
    private Layout pressedLinkLayout;
    private float pressedLinkYOffset;
    private Theme.ResourcesProvider resourcesProvider;
    private Drawable rippleBackground;
    private boolean shouldExpand;
    private Drawable showMoreBackgroundDrawable;
    private FrameLayout showMoreTextBackgroundView;
    private TextView showMoreTextView;
    private SpannableStringBuilder stringBuilder;
    private StaticLayout textLayout;
    private int textX;
    private int textY;
    private TextView valueTextView;

    protected void didExtend() {
    }

    protected void didPressUrl(String str, Browser.Progress progress) {
    }

    protected void didResizeEnd() {
    }

    protected void didResizeStart() {
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        return false;
    }

    public AboutLinkCell(Context context, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        new Point();
        new LinkPath(true);
        this.nextLinesLayouts = null;
        this.lastInlineLine = -1;
        this.needSpace = false;
        this.backgroundPaint = new Paint();
        this.SPACE = AndroidUtilities.dp(3.0f);
        this.longPressedRunnable = new AnonymousClass2();
        this.expandT = 0.0f;
        this.lastMaxWidth = 0;
        this.shouldExpand = false;
        this.resourcesProvider = resourcesProvider;
        this.parentFragment = baseFragment;
        FrameLayout frameLayout = new FrameLayout(context);
        this.container = frameLayout;
        frameLayout.setImportantForAccessibility(2);
        this.links = new LinkSpanDrawable.LinkCollector(this.container);
        this.rippleBackground = Theme.createRadSelectorDrawable(Theme.getColor(Theme.key_listSelector, resourcesProvider), 0, 0);
        TextView textView = new TextView(context);
        this.valueTextView = textView;
        textView.setVisibility(8);
        this.valueTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider));
        this.valueTextView.setTextSize(1, 13.0f);
        this.valueTextView.setLines(1);
        this.valueTextView.setMaxLines(1);
        this.valueTextView.setSingleLine(true);
        this.valueTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.valueTextView.setImportantForAccessibility(2);
        this.valueTextView.setFocusable(false);
        this.container.addView(this.valueTextView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 80, 23.0f, 0.0f, 23.0f, 10.0f));
        this.bottomShadow = new FrameLayout(context);
        Drawable mutate = context.getResources().getDrawable(R.drawable.gradient_bottom).mutate();
        int i = Theme.key_windowBackgroundWhite;
        mutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i, resourcesProvider), PorterDuff.Mode.SRC_ATOP));
        this.bottomShadow.setBackground(mutate);
        addView(this.bottomShadow, LayoutHelper.createFrame(-1, 12.0f, 87, 0.0f, 0.0f, 0.0f, 0.0f));
        addView(this.container, LayoutHelper.createFrame(-1, -1, 55));
        TextView textView2 = new TextView(this, context) { // from class: org.telegram.ui.Cells.AboutLinkCell.1
            private boolean pressed = false;

            @Override // android.widget.TextView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                boolean z = this.pressed;
                if (motionEvent.getAction() == 0) {
                    this.pressed = true;
                } else if (motionEvent.getAction() != 2) {
                    this.pressed = false;
                }
                if (z != this.pressed) {
                    invalidate();
                }
                return this.pressed || super.onTouchEvent(motionEvent);
            }

            @Override // android.widget.TextView, android.view.View
            protected void onDraw(Canvas canvas) {
                if (this.pressed) {
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                    canvas.drawRoundRect(rectF, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f), Theme.chat_urlPaint);
                }
                super.onDraw(canvas);
            }
        };
        this.showMoreTextView = textView2;
        textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText, resourcesProvider));
        this.showMoreTextView.setTextSize(1, 16.0f);
        this.showMoreTextView.setLines(1);
        this.showMoreTextView.setMaxLines(1);
        this.showMoreTextView.setSingleLine(true);
        this.showMoreTextView.setText(LocaleController.getString("DescriptionMore", R.string.DescriptionMore));
        this.showMoreTextView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Cells.AboutLinkCell$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AboutLinkCell.this.lambda$new$0(view);
            }
        });
        this.showMoreTextView.setPadding(AndroidUtilities.dp(2.0f), 0, AndroidUtilities.dp(2.0f), 0);
        this.showMoreTextBackgroundView = new FrameLayout(context);
        Drawable mutate2 = context.getResources().getDrawable(R.drawable.gradient_left).mutate();
        this.showMoreBackgroundDrawable = mutate2;
        mutate2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i, resourcesProvider), PorterDuff.Mode.MULTIPLY));
        this.showMoreTextBackgroundView.setBackground(this.showMoreBackgroundDrawable);
        FrameLayout frameLayout2 = this.showMoreTextBackgroundView;
        frameLayout2.setPadding(frameLayout2.getPaddingLeft() + AndroidUtilities.dp(4.0f), AndroidUtilities.dp(1.0f), 0, AndroidUtilities.dp(3.0f));
        this.showMoreTextBackgroundView.addView(this.showMoreTextView, LayoutHelper.createFrame(-2, -2.0f));
        addView(this.showMoreTextBackgroundView, LayoutHelper.createFrame(-2, -2.0f, 85, 22.0f - (r1.getPaddingLeft() / AndroidUtilities.density), 0.0f, 22.0f - (this.showMoreTextBackgroundView.getPaddingRight() / AndroidUtilities.density), 6.0f));
        this.backgroundPaint.setColor(Theme.getColor(i, resourcesProvider));
        setWillNotDraw(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        updateCollapse(true, true);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (this.showMoreTextView.getVisibility() == 0 && x >= this.showMoreTextBackgroundView.getLeft() && x <= this.showMoreTextBackgroundView.getRight() && y >= this.showMoreTextBackgroundView.getTop() && y <= this.showMoreTextBackgroundView.getBottom()) {
            return false;
        }
        if (this.textLayout != null || this.nextLinesLayouts != null) {
            if (motionEvent.getAction() == 0 || (this.pressedLink != null && motionEvent.getAction() == 1)) {
                if (motionEvent.getAction() == 0) {
                    resetPressedLink();
                    LinkSpanDrawable hitLink = hitLink(x, y);
                    if (hitLink != null) {
                        this.pressedLinkLayout = this.textLayout;
                        LinkSpanDrawable.LinkCollector linkCollector = this.links;
                        this.pressedLink = hitLink;
                        linkCollector.addLink(hitLink);
                        AndroidUtilities.runOnUIThread(this.longPressedRunnable, ViewConfiguration.getLongPressTimeout());
                        z = true;
                    }
                } else {
                    LinkSpanDrawable linkSpanDrawable = this.pressedLink;
                    if (linkSpanDrawable != null) {
                        try {
                            onLinkClick((ClickableSpan) linkSpanDrawable.getSpan(), this.textLayout, this.pressedLinkYOffset);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                        resetPressedLink();
                        z = true;
                    }
                }
                return !z || super.onTouchEvent(motionEvent);
            }
            if (motionEvent.getAction() == 3) {
                resetPressedLink();
            }
        }
        z = false;
        if (z) {
        }
    }

    private void setShowMoreMarginBottom(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.showMoreTextBackgroundView.getLayoutParams();
        if (layoutParams.bottomMargin != i) {
            layoutParams.bottomMargin = i;
            this.showMoreTextBackgroundView.setLayoutParams(layoutParams);
        }
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        float pow = ((View) getParent()) == null ? 1.0f : (float) Math.pow(r0.getAlpha(), 2.0d);
        drawText(canvas);
        float alpha = this.showMoreTextBackgroundView.getAlpha();
        if (alpha > 0.0f) {
            canvas.save();
            canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (alpha * 255.0f), 31);
            this.showMoreBackgroundDrawable.setAlpha((int) (pow * 255.0f));
            canvas.translate(this.showMoreTextBackgroundView.getLeft(), this.showMoreTextBackgroundView.getTop());
            this.showMoreTextBackgroundView.draw(canvas);
            canvas.restore();
        }
        float alpha2 = this.bottomShadow.getAlpha();
        if (alpha2 > 0.0f) {
            canvas.save();
            canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (alpha2 * 255.0f), 31);
            canvas.translate(this.bottomShadow.getLeft(), this.bottomShadow.getTop());
            this.bottomShadow.draw(canvas);
            canvas.restore();
        }
        this.container.draw(canvas);
        super.draw(canvas);
    }

    private void drawText(Canvas canvas) {
        StaticLayout staticLayout;
        int i;
        int i2;
        StaticLayout staticLayout2;
        canvas.save();
        canvas.clipRect(AndroidUtilities.dp(15.0f), AndroidUtilities.dp(8.0f), getWidth() - AndroidUtilities.dp(23.0f), getHeight());
        int dp = AndroidUtilities.dp(23.0f);
        this.textX = dp;
        float f = 0.0f;
        canvas.translate(dp, 0.0f);
        LinkSpanDrawable.LinkCollector linkCollector = this.links;
        if (linkCollector != null && linkCollector.draw(canvas)) {
            invalidate();
        }
        int dp2 = AndroidUtilities.dp(8.0f);
        this.textY = dp2;
        canvas.translate(0.0f, dp2);
        try {
            Theme.profile_aboutTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkIn, this.resourcesProvider);
            staticLayout = this.firstThreeLinesLayout;
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (staticLayout != null && this.shouldExpand) {
            staticLayout.draw(canvas);
            int lineCount = this.firstThreeLinesLayout.getLineCount() - 1;
            float lineTop = this.firstThreeLinesLayout.getLineTop(lineCount) + this.firstThreeLinesLayout.getTopPadding();
            float lineRight = this.firstThreeLinesLayout.getLineRight(lineCount) + (this.needSpace ? this.SPACE : 0.0f);
            float lineBottom = (this.firstThreeLinesLayout.getLineBottom(lineCount) - this.firstThreeLinesLayout.getLineTop(lineCount)) - this.firstThreeLinesLayout.getBottomPadding();
            float easeInOutCubic = easeInOutCubic(1.0f - ((float) Math.pow(this.expandT, 0.25d)));
            if (this.nextLinesLayouts != null) {
                float f2 = lineRight;
                int i3 = 0;
                while (true) {
                    StaticLayout[] staticLayoutArr = this.nextLinesLayouts;
                    if (i3 >= staticLayoutArr.length) {
                        break;
                    }
                    StaticLayout staticLayout3 = staticLayoutArr[i3];
                    if (staticLayout3 != null) {
                        int save = canvas.save();
                        Point[] pointArr = this.nextLinesLayoutsPositions;
                        if (pointArr[i3] != null) {
                            pointArr[i3].set((int) (this.textX + (f2 * easeInOutCubic)), (int) (this.textY + lineTop + ((1.0f - easeInOutCubic) * lineBottom)));
                        }
                        int i4 = this.lastInlineLine;
                        if (i4 != -1 && i4 <= i3) {
                            canvas.translate(f, lineTop + lineBottom);
                            i2 = save;
                            staticLayout2 = staticLayout3;
                            i = i3;
                            canvas.saveLayerAlpha(0.0f, 0.0f, staticLayout3.getWidth(), staticLayout3.getHeight(), (int) (this.expandT * 255.0f), 31);
                        } else {
                            i2 = save;
                            staticLayout2 = staticLayout3;
                            i = i3;
                            canvas.translate(f2 * easeInOutCubic, ((1.0f - easeInOutCubic) * lineBottom) + lineTop);
                        }
                        StaticLayout staticLayout4 = staticLayout2;
                        staticLayout4.draw(canvas);
                        canvas.restoreToCount(i2);
                        f2 += staticLayout4.getLineRight(0) + this.SPACE;
                        lineBottom += staticLayout4.getLineBottom(0) + staticLayout4.getTopPadding();
                    } else {
                        i = i3;
                    }
                    i3 = i + 1;
                    f = 0.0f;
                }
            }
            canvas.restore();
        }
        StaticLayout staticLayout5 = this.textLayout;
        if (staticLayout5 != null) {
            staticLayout5.draw(canvas);
        }
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetPressedLink() {
        this.links.clear();
        this.pressedLink = null;
        AndroidUtilities.cancelRunOnUIThread(this.longPressedRunnable);
        invalidate();
    }

    public void setText(String str, boolean z) {
        setTextAndValue(str, null, z);
    }

    public void setTextAndValue(String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, this.oldText)) {
            return;
        }
        try {
            this.oldText = AndroidUtilities.getSafeString(str);
        } catch (Throwable unused) {
            this.oldText = str;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.oldText);
        this.stringBuilder = spannableStringBuilder;
        MessageObject.addLinks(false, spannableStringBuilder, false, false, !z);
        Emoji.replaceEmoji(this.stringBuilder, Theme.profile_aboutTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
        if (this.lastMaxWidth <= 0) {
            this.lastMaxWidth = AndroidUtilities.displaySize.x - AndroidUtilities.dp(46.0f);
        }
        checkTextLayout(this.lastMaxWidth, true);
        updateHeight();
        int visibility = this.valueTextView.getVisibility();
        if (TextUtils.isEmpty(str2)) {
            this.valueTextView.setVisibility(8);
        } else {
            this.valueTextView.setText(str2);
            this.valueTextView.setVisibility(0);
        }
        if (visibility != this.valueTextView.getVisibility()) {
            checkTextLayout(this.lastMaxWidth, true);
        }
        requestLayout();
    }

    /* renamed from: org.telegram.ui.Cells.AboutLinkCell$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (AboutLinkCell.this.pressedLink != null) {
                final String url = AboutLinkCell.this.pressedLink.getSpan() instanceof URLSpanNoUnderline ? ((URLSpanNoUnderline) AboutLinkCell.this.pressedLink.getSpan()).getURL() : AboutLinkCell.this.pressedLink.getSpan() instanceof URLSpan ? ((URLSpan) AboutLinkCell.this.pressedLink.getSpan()).getURL() : AboutLinkCell.this.pressedLink.getSpan().toString();
                try {
                    AboutLinkCell.this.performHapticFeedback(0, 2);
                } catch (Exception unused) {
                }
                final Layout layout = AboutLinkCell.this.pressedLinkLayout;
                final float f = AboutLinkCell.this.pressedLinkYOffset;
                final ClickableSpan clickableSpan = (ClickableSpan) AboutLinkCell.this.pressedLink.getSpan();
                BottomSheet.Builder builder = new BottomSheet.Builder(AboutLinkCell.this.parentFragment.getParentActivity());
                builder.setTitle(url);
                builder.setItems(new CharSequence[]{LocaleController.getString("Open", R.string.Open), LocaleController.getString("Copy", R.string.Copy)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Cells.AboutLinkCell$2$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        AboutLinkCell.AnonymousClass2.this.lambda$run$0(clickableSpan, layout, f, url, dialogInterface, i);
                    }
                });
                builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Cells.AboutLinkCell$2$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        AboutLinkCell.AnonymousClass2.this.lambda$run$1(dialogInterface);
                    }
                });
                builder.show();
                AboutLinkCell.this.pressedLink = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ClickableSpan clickableSpan, Layout layout, float f, String str, DialogInterface dialogInterface, int i) {
            if (i == 0) {
                AboutLinkCell.this.onLinkClick(clickableSpan, layout, f);
                return;
            }
            if (i == 1) {
                AndroidUtilities.addToClipboard(str);
                if (AndroidUtilities.shouldShowClipboardToast()) {
                    if (str.startsWith("@")) {
                        BulletinFactory.of(AboutLinkCell.this.parentFragment).createSimpleBulletin(R.raw.copy, LocaleController.getString("UsernameCopied", R.string.UsernameCopied)).show();
                    } else if (str.startsWith("#") || str.startsWith("$")) {
                        BulletinFactory.of(AboutLinkCell.this.parentFragment).createSimpleBulletin(R.raw.copy, LocaleController.getString("HashtagCopied", R.string.HashtagCopied)).show();
                    } else {
                        BulletinFactory.of(AboutLinkCell.this.parentFragment).createSimpleBulletin(R.raw.copy, LocaleController.getString("LinkCopied", R.string.LinkCopied)).show();
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$1(DialogInterface dialogInterface) {
            AboutLinkCell.this.resetPressedLink();
        }
    }

    private LinkSpanDrawable hitLink(int i, int i2) {
        if (i >= this.showMoreTextView.getLeft() && i <= this.showMoreTextView.getRight() && i2 >= this.showMoreTextView.getTop() && i2 <= this.showMoreTextView.getBottom()) {
            return null;
        }
        if (getMeasuredWidth() > 0 && i > getMeasuredWidth() - AndroidUtilities.dp(23.0f)) {
            return null;
        }
        StaticLayout staticLayout = this.firstThreeLinesLayout;
        if (staticLayout != null && this.expandT < 1.0f && this.shouldExpand) {
            LinkSpanDrawable checkTouchTextLayout = checkTouchTextLayout(staticLayout, this.textX, this.textY, i, i2);
            if (checkTouchTextLayout != null) {
                return checkTouchTextLayout;
            }
            if (this.nextLinesLayouts != null) {
                int i3 = 0;
                while (true) {
                    StaticLayout[] staticLayoutArr = this.nextLinesLayouts;
                    if (i3 >= staticLayoutArr.length) {
                        break;
                    }
                    StaticLayout staticLayout2 = staticLayoutArr[i3];
                    Point[] pointArr = this.nextLinesLayoutsPositions;
                    LinkSpanDrawable checkTouchTextLayout2 = checkTouchTextLayout(staticLayout2, pointArr[i3].x, pointArr[i3].y, i, i2);
                    if (checkTouchTextLayout2 != null) {
                        return checkTouchTextLayout2;
                    }
                    i3++;
                }
            }
        }
        LinkSpanDrawable checkTouchTextLayout3 = checkTouchTextLayout(this.textLayout, this.textX, this.textY, i, i2);
        if (checkTouchTextLayout3 != null) {
            return checkTouchTextLayout3;
        }
        return null;
    }

    private LinkSpanDrawable checkTouchTextLayout(StaticLayout staticLayout, int i, int i2, int i3, int i4) {
        int i5 = i3 - i;
        int i6 = i4 - i2;
        try {
            int lineForVertical = staticLayout.getLineForVertical(i6);
            float f = i5;
            int offsetForHorizontal = staticLayout.getOffsetForHorizontal(lineForVertical, f);
            float lineLeft = staticLayout.getLineLeft(lineForVertical);
            if (lineLeft > f || lineLeft + staticLayout.getLineWidth(lineForVertical) < f || i6 < 0 || i6 > staticLayout.getHeight()) {
                return null;
            }
            Spannable spannable = (Spannable) staticLayout.getText();
            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
            if (clickableSpanArr.length == 0 || AndroidUtilities.isAccessibilityScreenReaderEnabled()) {
                return null;
            }
            LinkSpanDrawable linkSpanDrawable = new LinkSpanDrawable(clickableSpanArr[0], this.parentFragment.getResourceProvider(), i3, i4);
            int spanStart = spannable.getSpanStart(clickableSpanArr[0]);
            int spanEnd = spannable.getSpanEnd(clickableSpanArr[0]);
            LinkPath obtainNewPath = linkSpanDrawable.obtainNewPath();
            float f2 = i2;
            this.pressedLinkYOffset = f2;
            obtainNewPath.setCurrentLayout(staticLayout, spanStart, f2);
            staticLayout.getSelectionPath(spanStart, spanEnd, obtainNewPath);
            return linkSpanDrawable;
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLinkClick(ClickableSpan clickableSpan, Layout layout, float f) {
        Browser.Progress progress = this.currentProgress;
        AnonymousClass3 anonymousClass3 = null;
        if (progress != null) {
            progress.cancel();
            this.currentProgress = null;
        }
        if (layout != null && clickableSpan != null) {
            anonymousClass3 = new AnonymousClass3(layout, clickableSpan, f);
        }
        this.currentProgress = anonymousClass3;
        if (clickableSpan instanceof URLSpanNoUnderline) {
            String url = ((URLSpanNoUnderline) clickableSpan).getURL();
            if (url.startsWith("@") || url.startsWith("#") || url.startsWith("/")) {
                didPressUrl(url, this.currentProgress);
                return;
            }
            return;
        }
        if (clickableSpan instanceof URLSpan) {
            String url2 = ((URLSpan) clickableSpan).getURL();
            if (AndroidUtilities.shouldShowUrlInAlert(url2)) {
                AlertsCreator.showOpenUrlAlert(this.parentFragment, url2, true, true, true, this.currentProgress, null);
                return;
            } else {
                Browser.openUrl(getContext(), Uri.parse(url2), true, true, this.currentProgress);
                return;
            }
        }
        clickableSpan.onClick(this);
    }

    /* renamed from: org.telegram.ui.Cells.AboutLinkCell$3, reason: invalid class name */
    class AnonymousClass3 extends Browser.Progress {
        LoadingDrawable thisLoading;
        final /* synthetic */ Layout val$layout;
        final /* synthetic */ ClickableSpan val$pressedLink;
        final /* synthetic */ float val$yOffset;

        AnonymousClass3(Layout layout, ClickableSpan clickableSpan, float f) {
            this.val$layout = layout;
            this.val$pressedLink = clickableSpan;
            this.val$yOffset = f;
        }

        @Override // org.telegram.messenger.browser.Browser.Progress
        public void init() {
            if (AboutLinkCell.this.currentLoading != null) {
                AboutLinkCell.this.links.removeLoading(AboutLinkCell.this.currentLoading, true);
            }
            AboutLinkCell aboutLinkCell = AboutLinkCell.this;
            LoadingDrawable makeLoading = LinkSpanDrawable.LinkCollector.makeLoading(this.val$layout, this.val$pressedLink, this.val$yOffset);
            this.thisLoading = makeLoading;
            aboutLinkCell.currentLoading = makeLoading;
            LoadingDrawable loadingDrawable = this.thisLoading;
            int i = Theme.key_chat_linkSelectBackground;
            loadingDrawable.setColors(Theme.multAlpha(Theme.getColor(i, AboutLinkCell.this.resourcesProvider), 0.8f), Theme.multAlpha(Theme.getColor(i, AboutLinkCell.this.resourcesProvider), 1.3f), Theme.multAlpha(Theme.getColor(i, AboutLinkCell.this.resourcesProvider), 1.0f), Theme.multAlpha(Theme.getColor(i, AboutLinkCell.this.resourcesProvider), 4.0f));
            this.thisLoading.strokePaint.setStrokeWidth(AndroidUtilities.dpf2(1.25f));
            AboutLinkCell.this.links.addLoading(this.thisLoading);
        }

        @Override // org.telegram.messenger.browser.Browser.Progress
        public void end(boolean z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.AboutLinkCell$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AboutLinkCell.AnonymousClass3.this.lambda$end$0();
                }
            }, z ? 0L : 350L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$end$0() {
            if (this.thisLoading != null) {
                AboutLinkCell.this.links.removeLoading(this.thisLoading, true);
            }
        }
    }

    public class SpringInterpolator {
        public float friction;
        public float tension;
        private float position = 0.0f;
        private float velocity = 0.0f;

        public SpringInterpolator(AboutLinkCell aboutLinkCell, float f, float f2) {
            this.tension = f;
            this.friction = f2;
        }

        public float getValue(float f) {
            float min = Math.min(f, 250.0f);
            while (min > 0.0f) {
                float min2 = Math.min(min, 18.0f);
                step(min2);
                min -= min2;
            }
            return this.position;
        }

        private void step(float f) {
            float f2 = (-this.tension) * 1.0E-6f;
            float f3 = this.position;
            float f4 = (-this.friction) * 0.001f;
            float f5 = this.velocity;
            float f6 = f5 + ((((f2 * (f3 - 1.0f)) + (f4 * f5)) / 1.0f) * f);
            this.velocity = f6;
            this.position = f3 + (f6 * f);
        }
    }

    public void updateCollapse(boolean z, boolean z2) {
        ValueAnimator valueAnimator = this.collapseAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.collapseAnimator = null;
        }
        final float f = this.expandT;
        final float f2 = z ? 1.0f : 0.0f;
        if (z2) {
            if (f2 > 0.0f) {
                didExtend();
            }
            float textHeight = textHeight();
            float min = Math.min(COLLAPSED_HEIGHT, textHeight);
            Math.abs(AndroidUtilities.lerp(min, textHeight, f2) - AndroidUtilities.lerp(min, textHeight, f));
            this.collapseAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            float abs = Math.abs(f - f2) * 1250.0f * 2.0f;
            final SpringInterpolator springInterpolator = new SpringInterpolator(this, 380.0f, 20.17f);
            final AtomicReference atomicReference = new AtomicReference(Float.valueOf(f));
            this.collapseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.AboutLinkCell$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    AboutLinkCell.this.lambda$updateCollapse$1(atomicReference, f, f2, springInterpolator, valueAnimator2);
                }
            });
            this.collapseAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.AboutLinkCell.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AboutLinkCell.this.didResizeEnd();
                    if (AboutLinkCell.this.container.getBackground() == null) {
                        AboutLinkCell.this.container.setBackground(AboutLinkCell.this.rippleBackground);
                    }
                    AboutLinkCell.this.expanded = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    AboutLinkCell.this.didResizeStart();
                }
            });
            this.collapseAnimator.setDuration((long) abs);
            this.collapseAnimator.start();
            return;
        }
        this.expandT = f2;
        forceLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCollapse$1(AtomicReference atomicReference, float f, float f2, SpringInterpolator springInterpolator, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float floatValue2 = (floatValue - ((Float) atomicReference.getAndSet(Float.valueOf(floatValue))).floatValue()) * 1000.0f * 8.0f;
        AndroidUtilities.lerp(f, f2, ((Float) valueAnimator.getAnimatedValue()).floatValue());
        float lerp = AndroidUtilities.lerp(f, f2, springInterpolator.getValue(floatValue2));
        this.expandT = lerp;
        if (lerp > 0.8f && this.container.getBackground() == null) {
            this.container.setBackground(this.rippleBackground);
        }
        this.showMoreTextBackgroundView.setAlpha(1.0f - this.expandT);
        this.bottomShadow.setAlpha((float) Math.pow(1.0f - this.expandT, 2.0d));
        updateHeight();
        this.container.invalidate();
    }

    private int fromHeight() {
        return Math.min(COLLAPSED_HEIGHT + (this.valueTextView.getVisibility() == 0 ? AndroidUtilities.dp(20.0f) : 0), textHeight());
    }

    private int updateHeight() {
        int textHeight = textHeight();
        float fromHeight = fromHeight();
        if (this.shouldExpand) {
            textHeight = (int) AndroidUtilities.lerp(fromHeight, textHeight, this.expandT);
        }
        setHeight(textHeight);
        return textHeight;
    }

    private void setHeight(int i) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            if (getMinimumHeight() == 0) {
                getHeight();
            } else {
                getMinimumHeight();
            }
            layoutParams = new RecyclerView.LayoutParams(-1, i);
        } else {
            r1 = ((ViewGroup.MarginLayoutParams) layoutParams).height != i;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = i;
        }
        if (r1) {
            setLayoutParams(layoutParams);
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        checkTextLayout(View.MeasureSpec.getSize(i) - AndroidUtilities.dp(46.0f), false);
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(updateHeight(), 1073741824));
    }

    private StaticLayout makeTextLayout(CharSequence charSequence, int i) {
        if (Build.VERSION.SDK_INT >= 24) {
            return StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), Theme.profile_aboutTextPaint, i).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(LocaleController.isRTL ? StaticLayoutEx.ALIGN_RIGHT() : StaticLayoutEx.ALIGN_LEFT()).build();
        }
        return new StaticLayout(charSequence, Theme.profile_aboutTextPaint, i, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    private void checkTextLayout(int i, boolean z) {
        if (this.moreButtonDisabled) {
            this.shouldExpand = false;
        }
        SpannableStringBuilder spannableStringBuilder = this.stringBuilder;
        if (spannableStringBuilder != null && (i != this.lastMaxWidth || z)) {
            StaticLayout makeTextLayout = makeTextLayout(spannableStringBuilder, i);
            this.textLayout = makeTextLayout;
            this.shouldExpand = makeTextLayout.getLineCount() >= 4;
            if (this.textLayout.getLineCount() >= 3 && this.shouldExpand) {
                int max = Math.max(this.textLayout.getLineStart(2), this.textLayout.getLineEnd(2));
                if (this.stringBuilder.charAt(max - 1) == '\n') {
                    max--;
                }
                int i2 = max - 1;
                this.needSpace = (this.stringBuilder.charAt(i2) == ' ' || this.stringBuilder.charAt(i2) == '\n') ? false : true;
                this.firstThreeLinesLayout = makeTextLayout(this.stringBuilder.subSequence(0, max), i);
                this.nextLinesLayouts = new StaticLayout[this.textLayout.getLineCount() - 3];
                this.nextLinesLayoutsPositions = new Point[this.textLayout.getLineCount() - 3];
                float lineRight = this.firstThreeLinesLayout.getLineRight(this.firstThreeLinesLayout.getLineCount() - 1) + (this.needSpace ? this.SPACE : 0.0f);
                this.lastInlineLine = -1;
                if (this.showMoreTextBackgroundView.getMeasuredWidth() <= 0) {
                    FrameLayout frameLayout = this.showMoreTextBackgroundView;
                    int i3 = MOST_SPEC;
                    frameLayout.measure(i3, i3);
                }
                for (int i4 = 3; i4 < this.textLayout.getLineCount(); i4++) {
                    int lineStart = this.textLayout.getLineStart(i4);
                    int lineEnd = this.textLayout.getLineEnd(i4);
                    StaticLayout makeTextLayout2 = makeTextLayout(this.stringBuilder.subSequence(Math.min(lineStart, lineEnd), Math.max(lineStart, lineEnd)), i);
                    int i5 = i4 - 3;
                    this.nextLinesLayouts[i5] = makeTextLayout2;
                    this.nextLinesLayoutsPositions[i5] = new Point();
                    if (this.lastInlineLine == -1 && lineRight > (i - this.showMoreTextBackgroundView.getMeasuredWidth()) + this.showMoreTextBackgroundView.getPaddingLeft()) {
                        this.lastInlineLine = i5;
                    }
                    lineRight += makeTextLayout2.getLineRight(0) + this.SPACE;
                }
                if (lineRight < (i - this.showMoreTextBackgroundView.getMeasuredWidth()) + this.showMoreTextBackgroundView.getPaddingLeft()) {
                    this.shouldExpand = false;
                }
            }
            if (!this.shouldExpand) {
                this.firstThreeLinesLayout = null;
                this.nextLinesLayouts = null;
            }
            this.lastMaxWidth = i;
            this.container.setMinimumHeight(textHeight());
            if (this.shouldExpand && this.firstThreeLinesLayout != null) {
                int fromHeight = fromHeight() - AndroidUtilities.dp(8.0f);
                StaticLayout staticLayout = this.firstThreeLinesLayout;
                setShowMoreMarginBottom((((fromHeight - staticLayout.getLineBottom(staticLayout.getLineCount() - 1)) - this.showMoreTextBackgroundView.getPaddingBottom()) - this.showMoreTextView.getPaddingBottom()) - (this.showMoreTextView.getLayout() == null ? 0 : this.showMoreTextView.getLayout().getHeight() - this.showMoreTextView.getLayout().getLineBottom(this.showMoreTextView.getLineCount() - 1)));
            }
        }
        this.showMoreTextView.setVisibility(this.shouldExpand ? 0 : 8);
        if (!this.shouldExpand && this.container.getBackground() == null) {
            this.container.setBackground(this.rippleBackground);
        }
        if (!this.shouldExpand || this.expandT >= 1.0f || this.container.getBackground() == null) {
            return;
        }
        this.container.setBackground(null);
    }

    private int textHeight() {
        StaticLayout staticLayout = this.textLayout;
        int height = (staticLayout != null ? staticLayout.getHeight() : AndroidUtilities.dp(20.0f)) + AndroidUtilities.dp(16.0f);
        return this.valueTextView.getVisibility() == 0 ? height + AndroidUtilities.dp(23.0f) : height;
    }

    public boolean onClick() {
        if (!this.shouldExpand || this.expandT > 0.0f) {
            return false;
        }
        updateCollapse(true, true);
        return true;
    }

    private float easeInOutCubic(float f) {
        return ((double) f) < 0.5d ? 4.0f * f * f * f : 1.0f - (((float) Math.pow((f * (-2.0f)) + 2.0f, 3.0d)) / 2.0f);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.textLayout != null) {
            SpannableStringBuilder spannableStringBuilder = this.stringBuilder;
            CharSequence text = this.valueTextView.getText();
            accessibilityNodeInfo.setClassName("android.widget.TextView");
            if (TextUtils.isEmpty(text)) {
                accessibilityNodeInfo.setText(spannableStringBuilder);
                return;
            }
            accessibilityNodeInfo.setText(((Object) text) + ": " + ((Object) spannableStringBuilder));
        }
    }

    public void setMoreButtonDisabled(boolean z) {
        this.moreButtonDisabled = z;
    }
}
