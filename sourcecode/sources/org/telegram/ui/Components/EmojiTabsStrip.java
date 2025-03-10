package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.EmojiTabsStrip;
import org.telegram.ui.Components.EmojiView;
import org.telegram.ui.Components.Premium.PremiumLockIconView;

/* loaded from: classes4.dex */
public class EmojiTabsStrip extends ScrollableHorizontalScrollView {
    public boolean animateAppear;
    private int animatedEmojiCacheType;
    private ValueAnimator appearAnimation;
    private int appearCount;
    private int currentType;
    private EmojiTabsView emojiTabs;
    boolean first;
    private boolean forceTabsShow;
    private boolean includeAnimated;
    private Runnable onSettingsOpenRunnable;
    private int packsIndexStart;
    private float paddingLeftDp;
    private int recentDrawableId;
    private boolean recentFirstChange;
    private boolean recentIsShown;
    public EmojiTabButton recentTab;
    private HashMap<View, android.graphics.Rect> removingViews;
    private Theme.ResourcesProvider resourcesProvider;
    private float selectAnimationT;
    private ValueAnimator selectAnimator;
    private float selectT;
    private int selected;
    private EmojiTabButton settingsTab;
    private boolean showSelected;
    private AnimatedFloat showSelectedAlpha;
    public EmojiTabButton toggleEmojiStickersTab;
    public boolean updateButtonDrawables;
    private boolean wasDrawn;
    private int wasIndex;
    private static int[] emojiTabsDrawableIds = {R.drawable.msg_emoji_smiles, R.drawable.msg_emoji_cat, R.drawable.msg_emoji_food, R.drawable.msg_emoji_activities, R.drawable.msg_emoji_travel, R.drawable.msg_emoji_objects, R.drawable.msg_emoji_other, R.drawable.msg_emoji_flags};
    private static int[] emojiTabsAnimatedDrawableIds = {R.raw.msg_emoji_smiles, R.raw.msg_emoji_cat, R.raw.msg_emoji_food, R.raw.msg_emoji_activities, R.raw.msg_emoji_travel, R.raw.msg_emoji_objects, R.raw.msg_emoji_other, R.raw.msg_emoji_flags};

    protected boolean allowEmojisForNonPremium() {
        return false;
    }

    protected boolean doIncludeFeatured() {
        return true;
    }

    protected boolean onTabClick(int i) {
        return true;
    }

    protected void onTabCreate(EmojiTabButton emojiTabButton) {
    }

    public EmojiTabsStrip(Context context, Theme.ResourcesProvider resourcesProvider, boolean z, final boolean z2, int i, Runnable runnable) {
        super(context);
        this.recentDrawableId = R.drawable.msg_emoji_recent;
        this.forceTabsShow = !UserConfig.getInstance(UserConfig.selectedAccount).isPremium();
        this.showSelected = true;
        this.removingViews = new HashMap<>();
        this.selectT = 0.0f;
        this.selectAnimationT = 0.0f;
        this.selected = 0;
        this.wasIndex = 0;
        this.animateAppear = true;
        this.animatedEmojiCacheType = 6;
        this.updateButtonDrawables = true;
        this.recentFirstChange = true;
        this.recentIsShown = true;
        this.first = true;
        this.paddingLeftDp = 11.0f;
        this.includeAnimated = z2;
        this.resourcesProvider = resourcesProvider;
        this.onSettingsOpenRunnable = runnable;
        this.currentType = i;
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.EmojiTabsStrip.1
            HashMap<Integer, Integer> lastX = new HashMap<>();
            private Paint paint = new Paint(1);
            private RectF from = new RectF();
            private RectF to = new RectF();
            private RectF rect = new RectF();
            private Path path = new Path();

            @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z3, int i2, int i3, int i4, int i5) {
                int i6 = (i5 - i3) / 2;
                if (z2) {
                    int paddingLeft = getPaddingLeft() - (!EmojiTabsStrip.this.recentIsShown ? AndroidUtilities.dp(33.0f) : 0);
                    for (int i7 = 0; i7 < getChildCount(); i7++) {
                        View childAt = getChildAt(i7);
                        if (childAt != EmojiTabsStrip.this.settingsTab && !EmojiTabsStrip.this.removingViews.containsKey(childAt) && childAt != null) {
                            childAt.layout(paddingLeft, i6 - (childAt.getMeasuredHeight() / 2), childAt.getMeasuredWidth() + paddingLeft, (childAt.getMeasuredHeight() / 2) + i6);
                            boolean z4 = childAt instanceof EmojiTabButton;
                            Integer valueOf = z4 ? ((EmojiTabButton) childAt).id : childAt instanceof EmojiTabsView ? Integer.valueOf(((EmojiTabsView) childAt).id) : null;
                            if (EmojiTabsStrip.this.animateAppear && z4) {
                                EmojiTabButton emojiTabButton = (EmojiTabButton) childAt;
                                if (emojiTabButton.newly) {
                                    emojiTabButton.newly = false;
                                    childAt.setScaleX(0.0f);
                                    childAt.setScaleY(0.0f);
                                    childAt.setAlpha(0.0f);
                                    childAt.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                                }
                            }
                            if (valueOf != null) {
                                if (this.lastX.get(valueOf) != null && this.lastX.get(valueOf).intValue() != paddingLeft) {
                                    childAt.setTranslationX(this.lastX.get(valueOf).intValue() - paddingLeft);
                                    childAt.animate().translationX(0.0f).setDuration(250L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
                                }
                                this.lastX.put(valueOf, Integer.valueOf(paddingLeft));
                            }
                            paddingLeft += childAt.getMeasuredWidth() + AndroidUtilities.dp(3.0f);
                        }
                    }
                    if (EmojiTabsStrip.this.settingsTab != null) {
                        int dp = paddingLeft + (EmojiTabsStrip.this.recentIsShown ? 0 : AndroidUtilities.dp(33.0f));
                        Integer num = EmojiTabsStrip.this.settingsTab.id;
                        if (EmojiTabsStrip.this.settingsTab.getMeasuredWidth() + dp + getPaddingRight() <= EmojiTabsStrip.this.getMeasuredWidth()) {
                            EmojiTabButton emojiTabButton2 = EmojiTabsStrip.this.settingsTab;
                            int i8 = i4 - i2;
                            int paddingRight = (i8 - getPaddingRight()) - EmojiTabsStrip.this.settingsTab.getMeasuredWidth();
                            emojiTabButton2.layout(paddingRight, i6 - (EmojiTabsStrip.this.settingsTab.getMeasuredHeight() / 2), i8 - getPaddingRight(), i6 + (EmojiTabsStrip.this.settingsTab.getMeasuredHeight() / 2));
                            dp = paddingRight;
                        } else {
                            EmojiTabsStrip.this.settingsTab.layout(dp, i6 - (EmojiTabsStrip.this.settingsTab.getMeasuredHeight() / 2), EmojiTabsStrip.this.settingsTab.getMeasuredWidth() + dp, i6 + (EmojiTabsStrip.this.settingsTab.getMeasuredHeight() / 2));
                        }
                        if (num != null) {
                            if (this.lastX.get(num) != null && this.lastX.get(num).intValue() != dp) {
                                EmojiTabsStrip.this.settingsTab.setTranslationX(this.lastX.get(num).intValue() - dp);
                                EmojiTabsStrip.this.settingsTab.animate().translationX(0.0f).setDuration(350L).start();
                            }
                            this.lastX.put(num, Integer.valueOf(dp));
                            return;
                        }
                        return;
                    }
                    return;
                }
                int childCount = getChildCount() - (!EmojiTabsStrip.this.recentIsShown ? 1 : 0);
                int paddingLeft2 = (int) (((((i4 - i2) - getPaddingLeft()) - getPaddingRight()) - (AndroidUtilities.dp(30.0f) * childCount)) / Math.max(1, childCount - 1));
                int paddingLeft3 = getPaddingLeft();
                while (r11 < childCount) {
                    View childAt2 = getChildAt((!EmojiTabsStrip.this.recentIsShown ? 1 : 0) + r11);
                    if (childAt2 != null) {
                        childAt2.layout(paddingLeft3, i6 - (childAt2.getMeasuredHeight() / 2), childAt2.getMeasuredWidth() + paddingLeft3, (childAt2.getMeasuredHeight() / 2) + i6);
                        paddingLeft3 += childAt2.getMeasuredWidth() + paddingLeft2;
                    }
                    r11++;
                }
            }

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(99999999, LinearLayoutManager.INVALID_OFFSET);
                int paddingLeft = (getPaddingLeft() + getPaddingRight()) - ((int) (EmojiTabsStrip.this.recentIsShown ? 0.0f : EmojiTabsStrip.this.recentTab.getAlpha() * AndroidUtilities.dp(33.0f)));
                for (int i4 = 0; i4 < getChildCount(); i4++) {
                    View childAt = getChildAt(i4);
                    if (childAt != null) {
                        childAt.measure(makeMeasureSpec, i3);
                        paddingLeft += childAt.getMeasuredWidth() + (i4 + 1 < getChildCount() ? AndroidUtilities.dp(3.0f) : 0);
                    }
                }
                if (!z2) {
                    setMeasuredDimension(View.MeasureSpec.getSize(i2), View.MeasureSpec.getSize(i3));
                } else {
                    setMeasuredDimension(Math.max(paddingLeft, View.MeasureSpec.getSize(i2)), View.MeasureSpec.getSize(i3));
                }
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                for (Map.Entry entry : EmojiTabsStrip.this.removingViews.entrySet()) {
                    View view = (View) entry.getKey();
                    if (view != null) {
                        android.graphics.Rect rect = (android.graphics.Rect) entry.getValue();
                        canvas.save();
                        canvas.translate(rect.left, rect.top);
                        canvas.scale(view.getScaleX(), view.getScaleY(), rect.width() / 2.0f, rect.height() / 2.0f);
                        view.draw(canvas);
                        canvas.restore();
                    }
                }
                if (EmojiTabsStrip.this.showSelectedAlpha == null) {
                    EmojiTabsStrip.this.showSelectedAlpha = new AnimatedFloat(this, 350L, CubicBezierInterpolator.EASE_OUT_QUINT);
                }
                float f = EmojiTabsStrip.this.showSelectedAlpha.set(EmojiTabsStrip.this.showSelected ? 1.0f : 0.0f);
                int floor = (int) Math.floor(EmojiTabsStrip.this.selectT);
                int ceil = (int) Math.ceil(EmojiTabsStrip.this.selectT);
                getChildBounds(floor, this.from);
                getChildBounds(ceil, this.to);
                AndroidUtilities.lerp(this.from, this.to, EmojiTabsStrip.this.selectT - floor, this.rect);
                float clamp = EmojiTabsStrip.this.emojiTabs != null ? MathUtils.clamp(1.0f - Math.abs(EmojiTabsStrip.this.selectT - 1.0f), 0.0f, 1.0f) : 0.0f;
                float f2 = EmojiTabsStrip.this.selectAnimationT * 4.0f * (1.0f - EmojiTabsStrip.this.selectAnimationT);
                float width = (this.rect.width() / 2.0f) * ((0.3f * f2) + 1.0f);
                float height = (this.rect.height() / 2.0f) * (1.0f - (f2 * 0.05f));
                RectF rectF = this.rect;
                rectF.set(rectF.centerX() - width, this.rect.centerY() - height, this.rect.centerX() + width, this.rect.centerY() + height);
                float dp = AndroidUtilities.dp(AndroidUtilities.lerp(8.0f, 16.0f, clamp));
                this.paint.setColor(EmojiTabsStrip.this.selectorColor());
                if (EmojiTabsStrip.this.forceTabsShow) {
                    this.paint.setAlpha((int) (r6.getAlpha() * f * (1.0f - (clamp * 0.5f))));
                }
                this.path.rewind();
                this.path.addRoundRect(this.rect, dp, dp, Path.Direction.CW);
                canvas.drawPath(this.path, this.paint);
                if (EmojiTabsStrip.this.forceTabsShow) {
                    this.path.rewind();
                    getChildBounds(1, this.rect);
                    this.path.addRoundRect(this.rect, AndroidUtilities.dpf2(16.0f), AndroidUtilities.dpf2(16.0f), Path.Direction.CW);
                    this.paint.setColor(EmojiTabsStrip.this.selectorColor());
                    this.paint.setAlpha((int) (r0.getAlpha() * 0.5f));
                    canvas.drawPath(this.path, this.paint);
                }
                if (EmojiTabsStrip.this.emojiTabs != null) {
                    this.path.addCircle(EmojiTabsStrip.this.emojiTabs.getLeft() + AndroidUtilities.dp(15.0f), (EmojiTabsStrip.this.emojiTabs.getTop() + EmojiTabsStrip.this.emojiTabs.getBottom()) / 2.0f, AndroidUtilities.dp(15.0f), Path.Direction.CW);
                }
                super.dispatchDraw(canvas);
                EmojiTabsStrip.this.wasDrawn = true;
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view == EmojiTabsStrip.this.emojiTabs) {
                    canvas.save();
                    canvas.clipPath(this.path);
                    boolean drawChild = super.drawChild(canvas, view, j);
                    canvas.restore();
                    return drawChild;
                }
                return super.drawChild(canvas, view, j);
            }

            private void getChildBounds(int i2, RectF rectF) {
                View childAt = getChildAt(MathUtils.clamp(i2, 0, getChildCount() - 1));
                rectF.set(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
                rectF.set(rectF.centerX() - ((rectF.width() / 2.0f) * childAt.getScaleX()), rectF.centerY() - ((rectF.height() / 2.0f) * childAt.getScaleY()), rectF.centerX() + ((rectF.width() / 2.0f) * childAt.getScaleX()), rectF.centerY() + ((rectF.height() / 2.0f) * childAt.getScaleY()));
            }
        };
        this.contentView = linearLayout;
        linearLayout.setClipToPadding(false);
        this.contentView.setOrientation(0);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        addView(this.contentView);
        if (i == 4) {
            LinearLayout linearLayout2 = this.contentView;
            EmojiTabButton emojiTabButton = new EmojiTabButton(context, R.drawable.msg_emoji_stickers, false, false);
            this.toggleEmojiStickersTab = emojiTabButton;
            linearLayout2.addView(emojiTabButton);
        }
        if (i == 3) {
            this.recentDrawableId = R.drawable.msg_emoji_smiles;
        }
        LinearLayout linearLayout3 = this.contentView;
        EmojiTabButton emojiTabButton2 = new EmojiTabButton(context, this.recentDrawableId, false, false);
        this.recentTab = emojiTabButton2;
        linearLayout3.addView(emojiTabButton2);
        this.recentTab.id = -934918565;
        if (z2) {
            if (z) {
                LinearLayout linearLayout4 = this.contentView;
                EmojiTabsView emojiTabsView = new EmojiTabsView(context);
                this.emojiTabs = emojiTabsView;
                linearLayout4.addView(emojiTabsView);
                this.emojiTabs.id = 3552126;
            }
            this.packsIndexStart = this.contentView.getChildCount();
            updateClickListeners();
            return;
        }
        int i2 = 0;
        while (true) {
            int[] iArr = emojiTabsDrawableIds;
            if (i2 < iArr.length) {
                this.contentView.addView(new EmojiTabButton(context, iArr[i2], false, i2 == 0));
                i2++;
            } else {
                updateClickListeners();
                return;
            }
        }
    }

    public void showSelected(boolean z) {
        this.showSelected = z;
        this.contentView.invalidate();
    }

    public void showRecent(boolean z) {
        if (this.recentIsShown == z) {
            return;
        }
        this.recentIsShown = z;
        if (this.recentFirstChange) {
            this.recentTab.setAlpha(z ? 1.0f : 0.0f);
        } else {
            this.recentTab.animate().alpha(z ? 1.0f : 0.0f).setDuration(200L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        }
        if ((!z && this.selected == 0) || (z && this.selected == 1)) {
            select(0, !this.recentFirstChange);
        }
        this.contentView.requestLayout();
        this.recentFirstChange = false;
    }

    private boolean isFreeEmojiPack(TLRPC$StickerSet tLRPC$StickerSet, ArrayList<TLRPC$Document> arrayList) {
        if (tLRPC$StickerSet == null || arrayList == null) {
            return false;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (!MessageObject.isFreeEmoji(arrayList.get(i))) {
                return false;
            }
        }
        return true;
    }

    private TLRPC$Document getThumbDocument(TLRPC$StickerSet tLRPC$StickerSet, ArrayList<TLRPC$Document> arrayList) {
        if (tLRPC$StickerSet == null) {
            return null;
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                TLRPC$Document tLRPC$Document = arrayList.get(i);
                if (tLRPC$Document.id == tLRPC$StickerSet.thumb_document_id) {
                    return tLRPC$Document;
                }
            }
        }
        if (arrayList == null || arrayList.size() < 1) {
            return null;
        }
        return arrayList.get(0);
    }

    private static class DelayedAnimatedEmojiDrawable extends Drawable {
        int account;
        int alpha = 255;
        int cacheType;
        TLRPC$Document document;
        long documentId;
        AnimatedEmojiDrawable drawable;
        private ColorFilter lastColorFilter;
        private View view;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        public DelayedAnimatedEmojiDrawable(int i, int i2, TLRPC$Document tLRPC$Document) {
            this.account = i;
            this.cacheType = i2;
            this.document = tLRPC$Document;
            if (tLRPC$Document != null) {
                this.documentId = tLRPC$Document.id;
            }
        }

        public void load() {
            if (this.drawable != null) {
                return;
            }
            TLRPC$Document tLRPC$Document = this.document;
            if (tLRPC$Document != null) {
                this.drawable = AnimatedEmojiDrawable.make(this.account, this.cacheType, tLRPC$Document);
            } else {
                this.drawable = AnimatedEmojiDrawable.make(this.account, this.cacheType, this.documentId);
            }
            ColorFilter colorFilter = this.lastColorFilter;
            if (colorFilter != null) {
                this.drawable.setColorFilter(colorFilter);
            }
            this.drawable.setAlpha(this.alpha);
            this.drawable.setCallback(new Drawable.Callback() { // from class: org.telegram.ui.Components.EmojiTabsStrip.DelayedAnimatedEmojiDrawable.1
                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(Drawable drawable) {
                    DelayedAnimatedEmojiDrawable.this.invalidateSelf();
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                    DelayedAnimatedEmojiDrawable.this.scheduleSelf(runnable, j);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                    DelayedAnimatedEmojiDrawable.this.unscheduleSelf(runnable);
                }
            });
            View view = this.view;
            if (view != null) {
                view.invalidate();
            }
            invalidateSelf();
        }

        public boolean equals(long j) {
            return this.documentId == j;
        }

        public void updateView(View view) {
            AnimatedEmojiDrawable animatedEmojiDrawable;
            AnimatedEmojiDrawable animatedEmojiDrawable2;
            View view2 = this.view;
            if (view2 == view) {
                return;
            }
            if (view2 != null && (animatedEmojiDrawable2 = this.drawable) != null) {
                animatedEmojiDrawable2.removeView(view2);
            }
            this.view = view;
            if (view == null || (animatedEmojiDrawable = this.drawable) == null) {
                return;
            }
            animatedEmojiDrawable.addView(view);
        }

        public void removeView() {
            AnimatedEmojiDrawable animatedEmojiDrawable;
            View view = this.view;
            if (view != null && (animatedEmojiDrawable = this.drawable) != null) {
                animatedEmojiDrawable.removeView(view);
            }
            this.view = null;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.drawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.setBounds(getBounds());
                this.drawable.draw(canvas);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.alpha = i;
            AnimatedEmojiDrawable animatedEmojiDrawable = this.drawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.setAlpha(i);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.lastColorFilter = colorFilter;
            AnimatedEmojiDrawable animatedEmojiDrawable = this.drawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.setColorFilter(colorFilter);
            }
        }
    }

    protected boolean isInstalled(EmojiView.EmojiPack emojiPack) {
        return emojiPack.installed;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00d7, code lost:
    
        if (r3 == false) goto L59;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateEmojiPacks(java.util.ArrayList<org.telegram.ui.Components.EmojiView.EmojiPack> r20) {
        /*
            Method dump skipped, instructions count: 399
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.EmojiTabsStrip.updateEmojiPacks(java.util.ArrayList):void");
    }

    public void updateClickListeners() {
        int i = 0;
        final int i2 = 0;
        while (i < this.contentView.getChildCount()) {
            View childAt = this.contentView.getChildAt(i);
            if (childAt instanceof EmojiTabsView) {
                EmojiTabsView emojiTabsView = (EmojiTabsView) childAt;
                int i3 = 0;
                while (i3 < emojiTabsView.contentView.getChildCount()) {
                    emojiTabsView.contentView.getChildAt(i3).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            EmojiTabsStrip.this.lambda$updateClickListeners$0(i2, view);
                        }
                    });
                    i3++;
                    i2++;
                }
                i2--;
            } else if (childAt != null) {
                childAt.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        EmojiTabsStrip.this.lambda$updateClickListeners$1(i2, view);
                    }
                });
            }
            i++;
            i2++;
        }
        EmojiTabButton emojiTabButton = this.settingsTab;
        if (emojiTabButton != null) {
            emojiTabButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    EmojiTabsStrip.this.lambda$updateClickListeners$2(view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateClickListeners$0(int i, View view) {
        onTabClick(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateClickListeners$1(int i, View view) {
        onTabClick(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateClickListeners$2(View view) {
        Runnable runnable = this.onSettingsOpenRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setPaddingLeft(float f) {
        this.paddingLeftDp = f;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        this.contentView.setPadding(AndroidUtilities.dp(this.paddingLeftDp), 0, AndroidUtilities.dp(11.0f), 0);
        super.onMeasure(i, i2);
    }

    public void updateColors() {
        EmojiTabButton emojiTabButton = this.recentTab;
        if (emojiTabButton != null) {
            emojiTabButton.updateColor();
        }
    }

    public void select(int i) {
        select(i, true);
    }

    public void select(int i, boolean z) {
        int i2;
        boolean z2 = z && !this.first;
        EmojiTabButton emojiTabButton = this.toggleEmojiStickersTab;
        if (emojiTabButton != null) {
            i++;
        }
        if (!this.recentIsShown || emojiTabButton != null) {
            i = Math.max(1, i);
        }
        int i3 = this.selected;
        int i4 = 0;
        int i5 = 0;
        while (i4 < this.contentView.getChildCount()) {
            View childAt = this.contentView.getChildAt(i4);
            if (childAt instanceof EmojiTabsView) {
                EmojiTabsView emojiTabsView = (EmojiTabsView) childAt;
                int i6 = i5;
                int i7 = 0;
                while (i7 < emojiTabsView.contentView.getChildCount()) {
                    View childAt2 = emojiTabsView.contentView.getChildAt(i7);
                    if (childAt2 instanceof EmojiTabButton) {
                        ((EmojiTabButton) childAt2).updateSelect(i == i6, z2);
                    }
                    i7++;
                    i6++;
                }
                i2 = i6 - 1;
            } else {
                if (childAt instanceof EmojiTabButton) {
                    ((EmojiTabButton) childAt).updateSelect(i == i5, z2);
                }
                i2 = i5;
            }
            if (i >= i5 && i <= i2) {
                this.selected = i4;
            }
            i4++;
            i5 = i2 + 1;
        }
        if (i3 != this.selected) {
            ValueAnimator valueAnimator = this.selectAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            final float f = this.selectT;
            final float f2 = this.selected;
            if (z2) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                this.selectAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        EmojiTabsStrip.this.lambda$select$3(f, f2, valueAnimator2);
                    }
                });
                this.selectAnimator.setDuration(350L);
                this.selectAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.selectAnimator.start();
            } else {
                this.selectAnimationT = 1.0f;
                this.selectT = AndroidUtilities.lerp(f, f2, 1.0f);
                this.contentView.invalidate();
            }
            EmojiTabsView emojiTabsView2 = this.emojiTabs;
            if (emojiTabsView2 != null) {
                emojiTabsView2.show(this.selected == 1 || this.forceTabsShow, z2);
            }
            View childAt3 = this.contentView.getChildAt(this.selected);
            if (this.selected >= 2) {
                scrollToVisible(childAt3.getLeft(), childAt3.getRight());
            } else {
                scrollTo(0);
            }
        }
        if (this.wasIndex != i) {
            EmojiTabsView emojiTabsView3 = this.emojiTabs;
            if (emojiTabsView3 != null && this.selected == 1 && i >= 1 && i <= emojiTabsView3.contentView.getChildCount() + 1) {
                this.emojiTabs.scrollToVisible(AndroidUtilities.dp(((i - 1) * 36) - 6), AndroidUtilities.dp(r0 + 30));
            }
            this.wasIndex = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$select$3(float f, float f2, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.selectAnimationT = floatValue;
        this.selectT = AndroidUtilities.lerp(f, f2, floatValue);
        this.contentView.invalidate();
    }

    protected ColorFilter getEmojiColorFilter() {
        return Theme.chat_animatedEmojiTextColorFilter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int selectorColor() {
        return Theme.getColor(Theme.key_chat_emojiPanelIcon, this.resourcesProvider) & 788529151;
    }

    public void setAnimatedEmojiCacheType(int i) {
        this.animatedEmojiCacheType = i;
    }

    public class EmojiTabButton extends ViewGroup {
        DelayedAnimatedEmojiDrawable animatedEmoji;
        boolean attached;
        private boolean forceSelector;
        public Integer id;
        private ImageView imageView;
        private boolean isAnimatedEmoji;
        public boolean keepAttached;
        private ValueAnimator lockAnimator;
        private float lockT;
        private PremiumLockIconView lockView;
        private RLottieDrawable lottieDrawable;
        public boolean newly;
        private boolean round;
        private ValueAnimator selectAnimator;
        private float selectT;
        private boolean selected;
        private boolean wasVisible;

        public EmojiTabButton(Context context, int i, int i2, boolean z, boolean z2) {
            super(context);
            this.round = z;
            this.forceSelector = z2;
            if (z) {
                setBackground(Theme.createCircleSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 0, 0));
            } else if (z2) {
                setBackground(Theme.createRadSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 8, 8));
            }
            if (Build.VERSION.SDK_INT >= 23) {
                RLottieDrawable rLottieDrawable = new RLottieDrawable(i2, "" + i2, AndroidUtilities.dp(24.0f), AndroidUtilities.dp(24.0f), false, null);
                this.lottieDrawable = rLottieDrawable;
                rLottieDrawable.setBounds(AndroidUtilities.dp(3.0f), AndroidUtilities.dp(3.0f), AndroidUtilities.dp(27.0f), AndroidUtilities.dp(27.0f));
                this.lottieDrawable.setMasterParent(this);
                this.lottieDrawable.setAllowDecodeSingleFrame(true);
                this.lottieDrawable.start();
            } else {
                ImageView imageView = new ImageView(context);
                this.imageView = imageView;
                imageView.setImageDrawable(context.getResources().getDrawable(i).mutate());
                addView(this.imageView);
            }
            setColor(Theme.getColor(Theme.key_chat_emojiPanelIcon, EmojiTabsStrip.this.resourcesProvider));
        }

        public EmojiTabButton(Context context, int i, boolean z, boolean z2) {
            super(context);
            this.round = z;
            this.forceSelector = z2;
            if (z) {
                setBackground(Theme.createCircleSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 0, 0));
            } else if (z2) {
                setBackground(Theme.createRadSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 8, 8));
            }
            ImageView imageView = new ImageView(context);
            this.imageView = imageView;
            imageView.setImageDrawable(context.getResources().getDrawable(i).mutate());
            setColor(Theme.getColor(Theme.key_chat_emojiPanelIcon, EmojiTabsStrip.this.resourcesProvider));
            addView(this.imageView);
        }

        public EmojiTabButton(Context context, Drawable drawable, boolean z, boolean z2, boolean z3) {
            super(context);
            this.newly = true;
            this.round = z2;
            this.forceSelector = z3;
            if (z2) {
                setBackground(Theme.createCircleSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 0, 0));
            } else if (z3) {
                setBackground(Theme.createRadSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 8, 8));
            }
            ImageView imageView = new ImageView(context, EmojiTabsStrip.this) { // from class: org.telegram.ui.Components.EmojiTabsStrip.EmojiTabButton.1
                @Override // android.widget.ImageView, android.view.View
                protected void onDraw(Canvas canvas) {
                }

                @Override // android.view.View
                public void invalidate() {
                    super.invalidate();
                    EmojiTabButton.this.updateLockImageReceiver();
                }

                @Override // android.view.View
                protected void dispatchDraw(Canvas canvas) {
                    Drawable drawable2 = getDrawable();
                    if (drawable2 != null) {
                        drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                        drawable2.setAlpha(255);
                        drawable2.draw(canvas);
                    }
                }

                @Override // android.widget.ImageView
                public void setImageDrawable(Drawable drawable2) {
                    super.setImageDrawable(drawable2);
                }
            };
            this.imageView = imageView;
            imageView.setImageDrawable(drawable);
            if ((drawable instanceof AnimatedEmojiDrawable) || (drawable instanceof DelayedAnimatedEmojiDrawable)) {
                this.isAnimatedEmoji = true;
                this.imageView.setColorFilter(EmojiTabsStrip.this.getEmojiColorFilter());
            }
            addView(this.imageView);
            PremiumLockIconView premiumLockIconView = new PremiumLockIconView(context, PremiumLockIconView.TYPE_STICKERS_PREMIUM_LOCKED, EmojiTabsStrip.this.resourcesProvider);
            this.lockView = premiumLockIconView;
            premiumLockIconView.setAlpha(0.0f);
            this.lockView.setScaleX(0.0f);
            this.lockView.setScaleY(0.0f);
            updateLockImageReceiver();
            addView(this.lockView);
            setColor(Theme.getColor(Theme.key_chat_emojiPanelIcon, EmojiTabsStrip.this.resourcesProvider));
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            RLottieDrawable rLottieDrawable = this.lottieDrawable;
            if (rLottieDrawable == null || !this.wasVisible) {
                return;
            }
            rLottieDrawable.draw(canvas);
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (this.wasVisible) {
                return super.drawChild(canvas, view, j);
            }
            return true;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (this.wasVisible) {
                super.onDraw(canvas);
            }
        }

        @Override // android.view.View
        public boolean performClick() {
            playAnimation();
            return super.performClick();
        }

        private void playAnimation() {
            AnimatedEmojiDrawable animatedEmojiDrawable;
            ImageReceiver imageReceiver;
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable = this.animatedEmoji;
            if (delayedAnimatedEmojiDrawable == null || (animatedEmojiDrawable = delayedAnimatedEmojiDrawable.drawable) == null || (imageReceiver = animatedEmojiDrawable.getImageReceiver()) == null) {
                return;
            }
            if (imageReceiver.getAnimation() != null) {
                imageReceiver.getAnimation().seekTo(0L, true);
            }
            imageReceiver.startAnimation();
        }

        private void stopAnimation() {
            AnimatedEmojiDrawable animatedEmojiDrawable;
            ImageReceiver imageReceiver;
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable = this.animatedEmoji;
            if (delayedAnimatedEmojiDrawable == null || (animatedEmojiDrawable = delayedAnimatedEmojiDrawable.drawable) == null || (imageReceiver = animatedEmojiDrawable.getImageReceiver()) == null) {
                return;
            }
            if (imageReceiver.getLottieAnimation() != null) {
                imageReceiver.getLottieAnimation().setCurrentFrame(0);
                imageReceiver.getLottieAnimation().stop();
            } else if (imageReceiver.getAnimation() != null) {
                imageReceiver.getAnimation().stop();
            }
        }

        public void updateVisibilityInbounds(boolean z, boolean z2) {
            RLottieDrawable rLottieDrawable;
            if (!this.wasVisible && z && (rLottieDrawable = this.lottieDrawable) != null && !rLottieDrawable.isRunning() && !z2) {
                this.lottieDrawable.setProgress(0.0f);
                this.lottieDrawable.start();
            }
            if (this.wasVisible != z) {
                this.wasVisible = z;
                if (z) {
                    invalidate();
                    PremiumLockIconView premiumLockIconView = this.lockView;
                    if (premiumLockIconView != null) {
                        premiumLockIconView.invalidate();
                    }
                    ImageView imageView = this.imageView;
                    if (imageView != null && (imageView.getDrawable() instanceof DelayedAnimatedEmojiDrawable)) {
                        ((DelayedAnimatedEmojiDrawable) this.imageView.getDrawable()).load();
                    }
                    initLock();
                    ImageView imageView2 = this.imageView;
                    if (imageView2 != null) {
                        imageView2.invalidate();
                    }
                } else {
                    stopAnimation();
                }
                updateAttachState();
            }
        }

        private void initLock() {
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable;
            AnimatedEmojiDrawable animatedEmojiDrawable;
            ImageReceiver imageReceiver;
            if (this.lockView == null || (delayedAnimatedEmojiDrawable = this.animatedEmoji) == null || (animatedEmojiDrawable = delayedAnimatedEmojiDrawable.drawable) == null || (imageReceiver = animatedEmojiDrawable.getImageReceiver()) == null) {
                return;
            }
            this.lockView.setImageReceiver(imageReceiver);
        }

        public void setLock(Boolean bool) {
            if (this.lockView == null) {
                return;
            }
            if (bool == null) {
                updateLock(false);
                return;
            }
            updateLock(true);
            if (bool.booleanValue()) {
                this.lockView.setImageResource(R.drawable.msg_mini_lockedemoji);
                return;
            }
            Drawable mutate = getResources().getDrawable(R.drawable.msg_mini_addemoji).mutate();
            mutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.MULTIPLY));
            this.lockView.setImageDrawable(mutate);
        }

        private void updateLock(final boolean z) {
            ValueAnimator valueAnimator = this.lockAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (Math.abs(this.lockT - (z ? 1.0f : 0.0f)) < 0.01f) {
                return;
            }
            this.lockView.setVisibility(0);
            float[] fArr = new float[2];
            fArr[0] = this.lockT;
            fArr[1] = z ? 1.0f : 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
            this.lockAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$EmojiTabButton$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    EmojiTabsStrip.EmojiTabButton.this.lambda$updateLock$0(valueAnimator2);
                }
            });
            this.lockAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiTabsStrip.EmojiTabButton.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (z) {
                        return;
                    }
                    EmojiTabButton.this.lockView.setVisibility(8);
                }
            });
            this.lockAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.lockAnimator.setDuration(200L);
            this.lockAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateLock$0(ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.lockT = floatValue;
            this.lockView.setScaleX(floatValue);
            this.lockView.setScaleY(this.lockT);
            this.lockView.setAlpha(this.lockT);
        }

        public void updateLockImageReceiver() {
            ImageReceiver imageReceiver;
            PremiumLockIconView premiumLockIconView = this.lockView;
            if (premiumLockIconView == null || premiumLockIconView.ready() || !(getDrawable() instanceof AnimatedEmojiDrawable) || (imageReceiver = ((AnimatedEmojiDrawable) getDrawable()).getImageReceiver()) == null) {
                return;
            }
            this.lockView.setImageReceiver(imageReceiver);
            this.lockView.invalidate();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(AndroidUtilities.dp(30.0f), AndroidUtilities.dp(30.0f));
            ImageView imageView = this.imageView;
            if (imageView != null) {
                imageView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824));
            }
            PremiumLockIconView premiumLockIconView = this.lockView;
            if (premiumLockIconView != null) {
                premiumLockIconView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(12.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(12.0f), 1073741824));
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            ImageView imageView = this.imageView;
            if (imageView != null) {
                int i5 = (i3 - i) / 2;
                int i6 = (i4 - i2) / 2;
                imageView.layout(i5 - (imageView.getMeasuredWidth() / 2), i6 - (this.imageView.getMeasuredHeight() / 2), i5 + (this.imageView.getMeasuredWidth() / 2), i6 + (this.imageView.getMeasuredHeight() / 2));
            }
            PremiumLockIconView premiumLockIconView = this.lockView;
            if (premiumLockIconView != null) {
                int i7 = i3 - i;
                int i8 = i4 - i2;
                premiumLockIconView.layout(i7 - premiumLockIconView.getMeasuredWidth(), i8 - this.lockView.getMeasuredHeight(), i7, i8);
            }
        }

        public Drawable getDrawable() {
            ImageView imageView = this.imageView;
            if (imageView != null) {
                return imageView.getDrawable();
            }
            return null;
        }

        public void setDrawable(Drawable drawable) {
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable = drawable instanceof DelayedAnimatedEmojiDrawable ? (DelayedAnimatedEmojiDrawable) drawable : null;
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable2 = this.animatedEmoji;
            if (delayedAnimatedEmojiDrawable2 != delayedAnimatedEmojiDrawable) {
                if (delayedAnimatedEmojiDrawable2 != null && this.attached && this.wasVisible) {
                    delayedAnimatedEmojiDrawable2.removeView();
                }
                this.animatedEmoji = delayedAnimatedEmojiDrawable;
                if (delayedAnimatedEmojiDrawable != null && this.attached && this.wasVisible) {
                    delayedAnimatedEmojiDrawable.updateView(this.imageView);
                }
                if (this.wasVisible) {
                    this.animatedEmoji.load();
                }
                initLock();
            }
            ImageView imageView = this.imageView;
            if (imageView != null) {
                imageView.setImageDrawable(drawable);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.attached = true;
            updateAttachState();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.attached = false;
            updateAttachState();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateAttachState() {
            DelayedAnimatedEmojiDrawable delayedAnimatedEmojiDrawable = this.animatedEmoji;
            if (delayedAnimatedEmojiDrawable != null) {
                if ((this.keepAttached || this.attached) && this.wasVisible) {
                    delayedAnimatedEmojiDrawable.updateView(this.imageView);
                } else {
                    delayedAnimatedEmojiDrawable.removeView();
                }
            }
        }

        public void updateSelect(final boolean z, boolean z2) {
            ImageView imageView = this.imageView;
            if ((imageView == null || imageView.getDrawable() != null) && this.selected != z) {
                this.selected = z;
                ValueAnimator valueAnimator = this.selectAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.selectAnimator = null;
                }
                if (!z) {
                    stopAnimation();
                }
                if (z2) {
                    float[] fArr = new float[2];
                    fArr[0] = this.selectT;
                    fArr[1] = z ? 1.0f : 0.0f;
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                    this.selectAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$EmojiTabButton$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            EmojiTabsStrip.EmojiTabButton.this.lambda$updateSelect$1(valueAnimator2);
                        }
                    });
                    this.selectAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.EmojiTabsStrip.EmojiTabButton.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            EmojiTabButton emojiTabButton = EmojiTabButton.this;
                            if (!EmojiTabsStrip.this.updateButtonDrawables || emojiTabButton.round) {
                                return;
                            }
                            if (z || EmojiTabButton.this.forceSelector) {
                                if (EmojiTabButton.this.getBackground() == null) {
                                    EmojiTabButton emojiTabButton2 = EmojiTabButton.this;
                                    emojiTabButton2.setBackground(Theme.createRadSelectorDrawable(EmojiTabsStrip.this.selectorColor(), 8, 8));
                                    return;
                                }
                                return;
                            }
                            EmojiTabButton.this.setBackground(null);
                        }
                    });
                    this.selectAnimator.setDuration(350L);
                    this.selectAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.selectAnimator.start();
                    return;
                }
                this.selectT = z ? 1.0f : 0.0f;
                updateColor();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateSelect$1(ValueAnimator valueAnimator) {
            this.selectT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            setColor(ColorUtils.blendARGB(Theme.getColor(Theme.key_chat_emojiPanelIcon, EmojiTabsStrip.this.resourcesProvider), Theme.getColor(Theme.key_chat_emojiPanelIconSelected, EmojiTabsStrip.this.resourcesProvider), this.selectT));
        }

        public void updateColor() {
            Theme.setSelectorDrawableColor(getBackground(), EmojiTabsStrip.this.selectorColor(), false);
            setColor(ColorUtils.blendARGB(Theme.getColor(Theme.key_chat_emojiPanelIcon, EmojiTabsStrip.this.resourcesProvider), Theme.getColor(Theme.key_chat_emojiPanelIconSelected, EmojiTabsStrip.this.resourcesProvider), this.selectT));
        }

        private void setColor(int i) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY);
            ImageView imageView = this.imageView;
            if (imageView != null && !this.isAnimatedEmoji) {
                imageView.setColorFilter(porterDuffColorFilter);
                this.imageView.invalidate();
            }
            RLottieDrawable rLottieDrawable = this.lottieDrawable;
            if (rLottieDrawable != null) {
                rLottieDrawable.setColorFilter(porterDuffColorFilter);
                invalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class EmojiTabsView extends ScrollableHorizontalScrollView {
        public int id;
        private float showT;
        private boolean shown;

        public EmojiTabsView(Context context) {
            super(context);
            this.shown = EmojiTabsStrip.this.forceTabsShow;
            this.showT = EmojiTabsStrip.this.forceTabsShow ? 1.0f : 0.0f;
            setSmoothScrollingEnabled(true);
            setHorizontalScrollBarEnabled(false);
            setVerticalScrollBarEnabled(false);
            if (Build.VERSION.SDK_INT >= 21) {
                setNestedScrollingEnabled(true);
            }
            LinearLayout linearLayout = new LinearLayout(context, EmojiTabsStrip.this) { // from class: org.telegram.ui.Components.EmojiTabsStrip.EmojiTabsView.1
                @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    int paddingLeft = getPaddingLeft();
                    int i5 = (i4 - i2) / 2;
                    for (int i6 = 0; i6 < getChildCount(); i6++) {
                        View childAt = getChildAt(i6);
                        if (childAt != EmojiTabsStrip.this.settingsTab && childAt != null) {
                            childAt.layout(paddingLeft, i5 - (childAt.getMeasuredHeight() / 2), childAt.getMeasuredWidth() + paddingLeft, (childAt.getMeasuredHeight() / 2) + i5);
                            paddingLeft += childAt.getMeasuredWidth() + AndroidUtilities.dp(2.0f);
                        }
                    }
                }

                @Override // android.widget.LinearLayout, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(Math.max(View.MeasureSpec.getSize(i), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(EmojiTabsView.this.contentView.getChildCount() * 32), 1073741824)), i2);
                }
            };
            this.contentView = linearLayout;
            linearLayout.setOrientation(0);
            addView(this.contentView, new FrameLayout.LayoutParams(-2, -1));
            for (int i = 0; i < EmojiTabsStrip.emojiTabsDrawableIds.length; i++) {
                this.contentView.addView(new EmojiTabButton(context, EmojiTabsStrip.emojiTabsDrawableIds[i], EmojiTabsStrip.emojiTabsAnimatedDrawableIds[i], true, false, EmojiTabsStrip.this) { // from class: org.telegram.ui.Components.EmojiTabsStrip.EmojiTabsView.2
                    {
                        EmojiTabsStrip emojiTabsStrip = EmojiTabsStrip.this;
                    }

                    @Override // android.view.View
                    public boolean onTouchEvent(MotionEvent motionEvent) {
                        EmojiTabsView.this.intercept(motionEvent);
                        return super.onTouchEvent(motionEvent);
                    }
                });
            }
        }

        @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.lerp(AndroidUtilities.dp(30.0f), maxWidth(), this.showT), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(30.0f), 1073741824));
        }

        public int maxWidth() {
            return AndroidUtilities.dp(Math.min(5.7f, this.contentView.getChildCount()) * 32.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void intercept(MotionEvent motionEvent) {
            if (!this.shown || this.scrollingAnimation) {
                return;
            }
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1) {
                    this.touching = false;
                    return;
                } else if (action != 2) {
                    return;
                }
            }
            this.touching = true;
            if (!this.scrollingAnimation) {
                resetScrollTo();
            }
            EmojiTabsStrip.this.requestDisallowInterceptTouchEvent(true);
        }

        @Override // android.widget.HorizontalScrollView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            intercept(motionEvent);
            return super.onTouchEvent(motionEvent);
        }

        public void show(boolean z, boolean z2) {
            if (z == this.shown) {
                return;
            }
            this.shown = z;
            if (!z) {
                scrollTo(0);
            }
            ValueAnimator valueAnimator = this.showAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (z2) {
                float[] fArr = new float[2];
                fArr[0] = this.showT;
                fArr[1] = z ? 1.0f : 0.0f;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
                this.showAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.EmojiTabsStrip$EmojiTabsView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        EmojiTabsStrip.EmojiTabsView.this.lambda$show$0(valueAnimator2);
                    }
                });
                this.showAnimator.setDuration(475L);
                this.showAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.showAnimator.start();
                return;
            }
            this.showT = z ? 1.0f : 0.0f;
            invalidate();
            requestLayout();
            updateButtonsVisibility();
            EmojiTabsStrip.this.contentView.invalidate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$show$0(ValueAnimator valueAnimator) {
            this.showT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
            requestLayout();
            updateButtonsVisibility();
            EmojiTabsStrip.this.contentView.invalidate();
        }
    }
}
