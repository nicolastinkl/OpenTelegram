package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.EditTextCaption;
import org.telegram.ui.Components.EmojiView;
import org.telegram.ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.ui.Components.SizeNotifierFrameLayoutPhoto;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PhotoViewer;

/* loaded from: classes4.dex */
public class PhotoViewerCaptionEnterView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate, SizeNotifierFrameLayoutPhoto.SizeNotifierFrameLayoutPhotoDelegate {
    private NumberTextView captionLimitView;
    private float chatActivityEnterViewAnimateFromTop;
    private Drawable checkDrawable;
    private int codePointCount;
    public int currentAccount;
    private PhotoViewerCaptionEnterViewDelegate delegate;
    private final ImageView doneButton;
    private Drawable doneDrawable;
    private ImageView emojiButton;
    private ReplaceableIconDrawable emojiIconDrawable;
    private int emojiPadding;
    private EmojiView emojiView;
    private boolean forceFloatingEmoji;
    private boolean innerTextChange;
    private boolean isInitLineCount;
    private int keyboardHeight;
    private int keyboardHeightLand;
    private boolean keyboardVisible;
    private int lastSizeChangeValue1;
    private boolean lastSizeChangeValue2;
    private String lengthText;
    private TextPaint lengthTextPaint;
    private int lineCount;
    private EditTextCaption messageEditText;
    ValueAnimator messageEditTextAnimator;
    private int messageEditTextPredrawHeigth;
    private int messageEditTextPredrawScrollY;
    float offset;
    Paint paint;
    private boolean popupAnimating;
    private final Theme.ResourcesProvider resourcesProvider;
    private ValueAnimator sendButtonColorAnimator;
    boolean sendButtonEnabled;
    private float sendButtonEnabledProgress;
    private boolean shouldAnimateEditTextWithBounds;
    private SizeNotifierFrameLayoutPhoto sizeNotifierLayout;
    ValueAnimator topBackgroundAnimator;
    private View windowView;

    public interface PhotoViewerCaptionEnterViewDelegate {
        void onCaptionEnter();

        void onEmojiViewCloseEnd();

        void onEmojiViewCloseStart();

        void onEmojiViewOpen();

        void onTextChanged(CharSequence charSequence);

        void onWindowSizeChanged(int i);
    }

    protected void extendActionMode(ActionMode actionMode, Menu menu) {
    }

    public boolean hideActionMode() {
        return false;
    }

    public int getCaptionLimitOffset() {
        return MessagesController.getInstance(this.currentAccount).getCaptionMaxLengthLimit() - this.codePointCount;
    }

    public int getCodePointCount() {
        return this.codePointCount;
    }

    public PhotoViewerCaptionEnterView(final PhotoViewer photoViewer, Context context, final SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto, View view, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.sendButtonEnabled = true;
        this.sendButtonEnabledProgress = 1.0f;
        this.currentAccount = UserConfig.selectedAccount;
        this.paint = new Paint();
        this.offset = 0.0f;
        this.resourcesProvider = new DarkTheme();
        this.paint.setColor(2130706432);
        setWillNotDraw(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setClipChildren(false);
        this.windowView = view;
        this.sizeNotifierLayout = sizeNotifierFrameLayoutPhoto;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setClipChildren(false);
        linearLayout.setOrientation(0);
        addView(linearLayout, LayoutHelper.createFrame(-1, -2.0f, 51, 2.0f, 0.0f, 0.0f, 0.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setClipChildren(false);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(0, -2, 1.0f));
        ImageView imageView = new ImageView(context);
        this.emojiButton = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.emojiButton.setPadding(AndroidUtilities.dp(4.0f), AndroidUtilities.dp(1.0f), 0, 0);
        this.emojiButton.setAlpha(0.58f);
        frameLayout.addView(this.emojiButton, LayoutHelper.createFrame(48, 48, 83));
        this.emojiButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhotoViewerCaptionEnterView.this.lambda$new$0(view2);
            }
        });
        this.emojiButton.setContentDescription(LocaleController.getString("Emoji", R.string.Emoji));
        ImageView imageView2 = this.emojiButton;
        ReplaceableIconDrawable replaceableIconDrawable = new ReplaceableIconDrawable(context);
        this.emojiIconDrawable = replaceableIconDrawable;
        imageView2.setImageDrawable(replaceableIconDrawable);
        this.emojiIconDrawable.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.MULTIPLY));
        this.emojiIconDrawable.setIcon(R.drawable.input_smile, false);
        TextPaint textPaint = new TextPaint(1);
        this.lengthTextPaint = textPaint;
        textPaint.setTextSize(AndroidUtilities.dp(13.0f));
        this.lengthTextPaint.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.lengthTextPaint.setColor(-2500135);
        EditTextCaption editTextCaption = new EditTextCaption(context, 0 == true ? 1 : 0) { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView.1
            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected int getActionModeStyle() {
                return 2;
            }

            @Override // org.telegram.ui.Components.EditTextCaption, org.telegram.ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            protected void onMeasure(int i, int i2) {
                try {
                    PhotoViewerCaptionEnterView.this.isInitLineCount = getMeasuredWidth() == 0 && getMeasuredHeight() == 0;
                    super.onMeasure(i, i2);
                    if (PhotoViewerCaptionEnterView.this.isInitLineCount) {
                        PhotoViewerCaptionEnterView.this.lineCount = getLineCount();
                    }
                    PhotoViewerCaptionEnterView.this.isInitLineCount = false;
                } catch (Exception e) {
                    setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.dp(51.0f));
                    FileLog.e(e);
                }
            }

            @Override // org.telegram.ui.Components.EditTextEffects, android.widget.TextView
            protected void onSelectionChanged(int i, int i2) {
                super.onSelectionChanged(i, i2);
                if (i != i2) {
                    fixHandleView(false);
                } else {
                    fixHandleView(true);
                }
            }

            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected void extendActionMode(ActionMode actionMode, Menu menu) {
                PhotoViewerCaptionEnterView.this.extendActionMode(actionMode, menu);
            }

            @Override // android.view.View
            public boolean requestRectangleOnScreen(android.graphics.Rect rect) {
                rect.bottom += AndroidUtilities.dp(1000.0f);
                return super.requestRectangleOnScreen(rect);
            }

            @Override // org.telegram.ui.Components.EditTextBoldCursor, org.telegram.ui.Components.EditTextEffects, android.widget.EditText, android.widget.TextView
            public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                super.setText(charSequence, bufferType);
                invalidateForce();
            }
        };
        this.messageEditText = editTextCaption;
        editTextCaption.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view2, boolean z) {
                PhotoViewerCaptionEnterView.this.lambda$new$1(view2, z);
            }
        });
        this.messageEditText.setSelectAllOnFocus(false);
        this.messageEditText.setDelegate(new EditTextCaption.EditTextCaptionDelegate() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.Components.EditTextCaption.EditTextCaptionDelegate
            public final void onSpansChanged() {
                PhotoViewerCaptionEnterView.this.lambda$new$2();
            }
        });
        this.messageEditText.setWindowView(this.windowView);
        this.messageEditText.setHint(LocaleController.getString("AddCaption", R.string.AddCaption));
        this.messageEditText.setImeOptions(268435456);
        this.messageEditText.setLinkTextColor(-8994063);
        EditTextCaption editTextCaption2 = this.messageEditText;
        editTextCaption2.setInputType(editTextCaption2.getInputType() | LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM);
        this.messageEditText.setMaxLines(4);
        this.messageEditText.setHorizontallyScrolling(false);
        this.messageEditText.setTextSize(1, 18.0f);
        this.messageEditText.setGravity(80);
        this.messageEditText.setPadding(0, AndroidUtilities.dp(11.0f), 0, AndroidUtilities.dp(12.0f));
        this.messageEditText.setBackgroundDrawable(null);
        this.messageEditText.setCursorColor(-1);
        this.messageEditText.setCursorSize(AndroidUtilities.dp(20.0f));
        this.messageEditText.setTextColor(-1);
        this.messageEditText.setHighlightColor(1342177279);
        this.messageEditText.setHintTextColor(-1291845633);
        frameLayout.addView(this.messageEditText, LayoutHelper.createFrame(-1, -2.0f, 83, 52.0f, 0.0f, 6.0f, 0.0f));
        this.messageEditText.setOnKeyListener(new View.OnKeyListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view2, int i, KeyEvent keyEvent) {
                boolean lambda$new$3;
                lambda$new$3 = PhotoViewerCaptionEnterView.this.lambda$new$3(view2, i, keyEvent);
                return lambda$new$3;
            }
        });
        this.messageEditText.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhotoViewerCaptionEnterView.this.lambda$new$4(view2);
            }
        });
        this.messageEditText.addTextChangedListener(new AnonymousClass2(photoViewer, sizeNotifierFrameLayoutPhoto));
        this.doneDrawable = Theme.createCircleDrawable(AndroidUtilities.dp(16.0f), -10043398);
        this.checkDrawable = context.getResources().getDrawable(R.drawable.input_done).mutate();
        CombinedDrawable combinedDrawable = new CombinedDrawable(this.doneDrawable, this.checkDrawable, 0, AndroidUtilities.dp(1.0f));
        combinedDrawable.setCustomSize(AndroidUtilities.dp(32.0f), AndroidUtilities.dp(32.0f));
        ImageView imageView3 = new ImageView(context);
        this.doneButton = imageView3;
        imageView3.setScaleType(ImageView.ScaleType.CENTER);
        imageView3.setImageDrawable(combinedDrawable);
        linearLayout.addView(imageView3, LayoutHelper.createLinear(48, 48, 80));
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                PhotoViewerCaptionEnterView.this.lambda$new$5(photoViewer, sizeNotifierFrameLayoutPhoto, view2);
            }
        });
        imageView3.setContentDescription(LocaleController.getString("Done", R.string.Done));
        NumberTextView numberTextView = new NumberTextView(context);
        this.captionLimitView = numberTextView;
        numberTextView.setVisibility(8);
        this.captionLimitView.setTextSize(15);
        this.captionLimitView.setTextColor(-1);
        this.captionLimitView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        this.captionLimitView.setCenterAlign(true);
        addView(this.captionLimitView, LayoutHelper.createFrame(48, 20.0f, 85, 3.0f, 0.0f, 3.0f, 48.0f));
        this.currentAccount = UserConfig.selectedAccount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        if (this.keyboardVisible || ((AndroidUtilities.isInMultiwindow || AndroidUtilities.usingHardwareInput) && !isPopupShowing())) {
            showPopup(1, false);
        } else {
            openKeyboardInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view, boolean z) {
        if (z) {
            try {
                EditTextCaption editTextCaption = this.messageEditText;
                editTextCaption.setSelection(editTextCaption.length(), this.messageEditText.length());
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        this.messageEditText.invalidateEffects();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3(View view, int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (this.windowView != null && hideActionMode()) {
                return true;
            }
            if (!this.keyboardVisible && isPopupShowing()) {
                if (keyEvent.getAction() == 1) {
                    showPopup(0, true);
                }
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view) {
        if (isPopupShowing()) {
            showPopup((AndroidUtilities.isInMultiwindow || AndroidUtilities.usingHardwareInput) ? 0 : 2, false);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$2, reason: invalid class name */
    class AnonymousClass2 implements TextWatcher {
        boolean heightShouldBeChanged;
        boolean processChange = false;
        final /* synthetic */ SizeNotifierFrameLayoutPhoto val$parent;
        final /* synthetic */ PhotoViewer val$photoViewer;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        AnonymousClass2(PhotoViewer photoViewer, SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto) {
            this.val$photoViewer = photoViewer;
            this.val$parent = sizeNotifierFrameLayoutPhoto;
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (PhotoViewerCaptionEnterView.this.lineCount != PhotoViewerCaptionEnterView.this.messageEditText.getLineCount()) {
                this.heightShouldBeChanged = (PhotoViewerCaptionEnterView.this.messageEditText.getLineCount() >= 4) != (PhotoViewerCaptionEnterView.this.lineCount >= 4);
                if (!PhotoViewerCaptionEnterView.this.isInitLineCount && PhotoViewerCaptionEnterView.this.messageEditText.getMeasuredWidth() > 0) {
                    PhotoViewerCaptionEnterView photoViewerCaptionEnterView = PhotoViewerCaptionEnterView.this;
                    photoViewerCaptionEnterView.onLineCountChanged(photoViewerCaptionEnterView.lineCount, PhotoViewerCaptionEnterView.this.messageEditText.getLineCount());
                }
                PhotoViewerCaptionEnterView photoViewerCaptionEnterView2 = PhotoViewerCaptionEnterView.this;
                photoViewerCaptionEnterView2.lineCount = photoViewerCaptionEnterView2.messageEditText.getLineCount();
            } else {
                this.heightShouldBeChanged = false;
            }
            if (PhotoViewerCaptionEnterView.this.innerTextChange) {
                return;
            }
            if (PhotoViewerCaptionEnterView.this.delegate != null) {
                PhotoViewerCaptionEnterView.this.delegate.onTextChanged(charSequence);
            }
            if (i3 - i2 > 1) {
                this.processChange = true;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0172  */
        @Override // android.text.TextWatcher
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void afterTextChanged(android.text.Editable r11) {
            /*
                Method dump skipped, instructions count: 559
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.PhotoViewerCaptionEnterView.AnonymousClass2.afterTextChanged(android.text.Editable):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$afterTextChanged$0(ValueAnimator valueAnimator) {
            PhotoViewerCaptionEnterView.this.sendButtonEnabledProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            Theme.setDrawableColor(PhotoViewerCaptionEnterView.this.checkDrawable, ColorUtils.setAlphaComponent(PhotoViewerCaptionEnterView.this.getThemedColor(Theme.key_dialogFloatingIcon), (int) (Color.alpha(r5) * ((PhotoViewerCaptionEnterView.this.sendButtonEnabledProgress * 0.42f) + 0.58f))));
            PhotoViewerCaptionEnterView.this.doneButton.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(PhotoViewer photoViewer, SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto, View view) {
        if (MessagesController.getInstance(this.currentAccount).getCaptionMaxLengthLimit() - this.codePointCount < 0) {
            AndroidUtilities.shakeView(this.captionLimitView);
            try {
                this.captionLimitView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (MessagesController.getInstance(this.currentAccount).premiumLocked || MessagesController.getInstance(this.currentAccount).captionLengthLimitPremium <= this.codePointCount) {
                return;
            }
            photoViewer.showCaptionLimitBulletin(sizeNotifierFrameLayoutPhoto);
            return;
        }
        this.delegate.onCaptionEnter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLineCountChanged(int i, int i2) {
        if (!TextUtils.isEmpty(this.messageEditText.getText())) {
            this.shouldAnimateEditTextWithBounds = true;
            this.messageEditTextPredrawHeigth = this.messageEditText.getMeasuredHeight();
            this.messageEditTextPredrawScrollY = this.messageEditText.getScrollY();
            invalidate();
        } else {
            this.messageEditText.animate().cancel();
            this.messageEditText.setOffsetY(0.0f);
            this.shouldAnimateEditTextWithBounds = false;
        }
        this.chatActivityEnterViewAnimateFromTop = getTop() + this.offset;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.drawRect(0.0f, this.offset, getMeasuredWidth(), getMeasuredHeight(), this.paint);
        canvas.clipRect(0.0f, this.offset, getMeasuredWidth(), getMeasuredHeight());
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.shouldAnimateEditTextWithBounds) {
            float measuredHeight = (this.messageEditTextPredrawHeigth - this.messageEditText.getMeasuredHeight()) + (this.messageEditTextPredrawScrollY - this.messageEditText.getScrollY());
            EditTextCaption editTextCaption = this.messageEditText;
            editTextCaption.setOffsetY(editTextCaption.getOffsetY() - measuredHeight);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.messageEditText.getOffsetY(), 0.0f);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PhotoViewerCaptionEnterView.this.lambda$onDraw$6(valueAnimator);
                }
            });
            ValueAnimator valueAnimator = this.messageEditTextAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.messageEditTextAnimator = ofFloat;
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(CubicBezierInterpolator.DEFAULT);
            ofFloat.start();
            this.shouldAnimateEditTextWithBounds = false;
        }
        float f = this.chatActivityEnterViewAnimateFromTop;
        if (f == 0.0f || f == getTop() + this.offset) {
            return;
        }
        ValueAnimator valueAnimator2 = this.topBackgroundAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        float top = this.chatActivityEnterViewAnimateFromTop - (getTop() + this.offset);
        this.offset = top;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(top, 0.0f);
        this.topBackgroundAnimator = ofFloat2;
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                PhotoViewerCaptionEnterView.this.lambda$onDraw$7(valueAnimator3);
            }
        });
        this.topBackgroundAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.topBackgroundAnimator.setDuration(200L);
        this.topBackgroundAnimator.start();
        this.chatActivityEnterViewAnimateFromTop = 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$6(ValueAnimator valueAnimator) {
        this.messageEditText.setOffsetY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$7(ValueAnimator valueAnimator) {
        this.offset = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void setForceFloatingEmoji(boolean z) {
        this.forceFloatingEmoji = z;
    }

    public void updateColors() {
        Theme.setDrawableColor(this.doneDrawable, getThemedColor(Theme.key_dialogFloatingButton));
        Theme.setDrawableColor(this.checkDrawable, ColorUtils.setAlphaComponent(getThemedColor(Theme.key_dialogFloatingIcon), (int) (Color.alpha(r0) * ((this.sendButtonEnabledProgress * 0.42f) + 0.58f))));
        EmojiView emojiView = this.emojiView;
        if (emojiView != null) {
            emojiView.updateColors();
        }
    }

    private void onWindowSizeChanged() {
        int height = this.sizeNotifierLayout.getHeight();
        if (!this.keyboardVisible) {
            height -= this.emojiPadding;
        }
        PhotoViewerCaptionEnterViewDelegate photoViewerCaptionEnterViewDelegate = this.delegate;
        if (photoViewerCaptionEnterViewDelegate != null) {
            photoViewerCaptionEnterViewDelegate.onWindowSizeChanged(height);
        }
    }

    public void onCreate() {
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        this.sizeNotifierLayout.setDelegate(this);
    }

    public void onDestroy() {
        hidePopup();
        if (isKeyboardVisible()) {
            closeKeyboard();
        }
        this.keyboardVisible = false;
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto = this.sizeNotifierLayout;
        if (sizeNotifierFrameLayoutPhoto != null) {
            sizeNotifierFrameLayoutPhoto.setDelegate(null);
        }
    }

    public void setDelegate(PhotoViewerCaptionEnterViewDelegate photoViewerCaptionEnterViewDelegate) {
        this.delegate = photoViewerCaptionEnterViewDelegate;
    }

    public void setFieldText(CharSequence charSequence) {
        EditTextCaption editTextCaption = this.messageEditText;
        if (editTextCaption == null) {
            return;
        }
        editTextCaption.setText(charSequence);
        EditTextCaption editTextCaption2 = this.messageEditText;
        editTextCaption2.setSelection(editTextCaption2.getText().length());
        PhotoViewerCaptionEnterViewDelegate photoViewerCaptionEnterViewDelegate = this.delegate;
        if (photoViewerCaptionEnterViewDelegate != null) {
            photoViewerCaptionEnterViewDelegate.onTextChanged(this.messageEditText.getText());
        }
    }

    public int getSelectionLength() {
        EditTextCaption editTextCaption = this.messageEditText;
        if (editTextCaption == null) {
            return 0;
        }
        try {
            return editTextCaption.getSelectionEnd() - this.messageEditText.getSelectionStart();
        } catch (Exception e) {
            FileLog.e(e);
            return 0;
        }
    }

    public int getCursorPosition() {
        EditTextCaption editTextCaption = this.messageEditText;
        if (editTextCaption == null) {
            return 0;
        }
        return editTextCaption.getSelectionStart();
    }

    private class DarkTheme implements Theme.ResourcesProvider {
        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ void applyServiceShaderMatrix(int i, int i2, float f, float f2) {
            Theme.applyServiceShaderMatrix(i, i2, f, f2);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ int getColorOrDefault(int i) {
            return Theme.ResourcesProvider.CC.$default$getColorOrDefault(this, i);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ int getCurrentColor(int i) {
            int color;
            color = getColor(i);
            return color;
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ Drawable getDrawable(String str) {
            return Theme.ResourcesProvider.CC.$default$getDrawable(this, str);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ Paint getPaint(String str) {
            return Theme.ResourcesProvider.CC.$default$getPaint(this, str);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ boolean hasGradientService() {
            return Theme.ResourcesProvider.CC.$default$hasGradientService(this);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ void setAnimatedColor(int i, int i2) {
            Theme.ResourcesProvider.CC.$default$setAnimatedColor(this, i, i2);
        }

        private DarkTheme(PhotoViewerCaptionEnterView photoViewerCaptionEnterView) {
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public int getColor(int i) {
            if (i == Theme.key_dialogBackground) {
                return -14803426;
            }
            if (i == Theme.key_windowBackgroundWhite) {
                return -15198183;
            }
            if (i == Theme.key_windowBackgroundWhiteBlackText) {
                return -1;
            }
            if (i == Theme.key_chat_emojiPanelEmptyText) {
                return -8553090;
            }
            if (i == Theme.key_progressCircle) {
                return -10177027;
            }
            if (i == Theme.key_chat_emojiSearchIcon) {
                return -9211020;
            }
            if (i == Theme.key_chat_emojiPanelStickerPackSelector || i == Theme.key_chat_emojiSearchBackground) {
                return 181267199;
            }
            if (i == Theme.key_chat_emojiPanelIcon || i == Theme.key_chat_emojiBottomPanelIcon) {
                return -9539985;
            }
            if (i == Theme.key_chat_emojiPanelIconSelected || i == Theme.key_chat_emojiPanelStickerPackSelectorLine) {
                return -10177041;
            }
            if (i == Theme.key_chat_emojiPanelBackground) {
                return -14803425;
            }
            if (i == Theme.key_chat_emojiPanelShadowLine) {
                return -1610612736;
            }
            if (i == Theme.key_chat_emojiPanelBackspace) {
                return -9539985;
            }
            if (i == Theme.key_listSelector) {
                return 771751936;
            }
            if (i == Theme.key_divider) {
                return -16777216;
            }
            if (i == Theme.key_dialogFloatingButton) {
                return -10177041;
            }
            return i == Theme.key_dialogFloatingIcon ? -1 : 0;
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public boolean contains(int i) {
            return getColor(i) != 0;
        }
    }

    private void createEmojiView() {
        EmojiView emojiView = this.emojiView;
        if (emojiView != null && emojiView.currentAccount != UserConfig.selectedAccount) {
            this.sizeNotifierLayout.removeView(emojiView);
            this.emojiView = null;
        }
        if (this.emojiView != null) {
            return;
        }
        EmojiView emojiView2 = new EmojiView(null, true, false, false, getContext(), false, null, null, this.resourcesProvider);
        this.emojiView = emojiView2;
        emojiView2.setDelegate(new EmojiView.EmojiViewDelegate() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView.3
            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ boolean canSchedule() {
                return EmojiView.EmojiViewDelegate.CC.$default$canSchedule(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ long getDialogId() {
                return EmojiView.EmojiViewDelegate.CC.$default$getDialogId(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ float getProgressToSearchOpened() {
                return EmojiView.EmojiViewDelegate.CC.$default$getProgressToSearchOpened(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ int getThreadId() {
                return EmojiView.EmojiViewDelegate.CC.$default$getThreadId(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void invalidateEnterView() {
                EmojiView.EmojiViewDelegate.CC.$default$invalidateEnterView(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ boolean isExpanded() {
                return EmojiView.EmojiViewDelegate.CC.$default$isExpanded(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ boolean isInScheduleMode() {
                return EmojiView.EmojiViewDelegate.CC.$default$isInScheduleMode(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ boolean isSearchOpened() {
                return EmojiView.EmojiViewDelegate.CC.$default$isSearchOpened(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ boolean isUserSelf() {
                return EmojiView.EmojiViewDelegate.CC.$default$isUserSelf(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onClearEmojiRecent() {
                EmojiView.EmojiViewDelegate.CC.$default$onClearEmojiRecent(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onEmojiSettingsClick(ArrayList arrayList) {
                EmojiView.EmojiViewDelegate.CC.$default$onEmojiSettingsClick(this, arrayList);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            /* renamed from: onGifSelected */
            public /* synthetic */ void lambda$onGifSelected$1(View view, Object obj, String str, Object obj2, boolean z, int i) {
                EmojiView.EmojiViewDelegate.CC.$default$onGifSelected(this, view, obj, str, obj2, z, i);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onSearchOpenClose(int i) {
                EmojiView.EmojiViewDelegate.CC.$default$onSearchOpenClose(this, i);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onShowStickerSet(TLRPC$StickerSet tLRPC$StickerSet, TLRPC$InputStickerSet tLRPC$InputStickerSet) {
                EmojiView.EmojiViewDelegate.CC.$default$onShowStickerSet(this, tLRPC$StickerSet, tLRPC$InputStickerSet);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onStickerSelected(View view, TLRPC$Document tLRPC$Document, String str, Object obj, MessageObject.SendAnimationData sendAnimationData, boolean z, int i) {
                EmojiView.EmojiViewDelegate.CC.$default$onStickerSelected(this, view, tLRPC$Document, str, obj, sendAnimationData, z, i);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onStickerSetAdd(TLRPC$StickerSetCovered tLRPC$StickerSetCovered) {
                EmojiView.EmojiViewDelegate.CC.$default$onStickerSetAdd(this, tLRPC$StickerSetCovered);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onStickerSetRemove(TLRPC$StickerSetCovered tLRPC$StickerSetCovered) {
                EmojiView.EmojiViewDelegate.CC.$default$onStickerSetRemove(this, tLRPC$StickerSetCovered);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onStickersGroupClick(long j) {
                EmojiView.EmojiViewDelegate.CC.$default$onStickersGroupClick(this, j);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onStickersSettingsClick() {
                EmojiView.EmojiViewDelegate.CC.$default$onStickersSettingsClick(this);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void onTabOpened(int i) {
                EmojiView.EmojiViewDelegate.CC.$default$onTabOpened(this, i);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public /* synthetic */ void showTrendingStickersAlert(TrendingStickersLayout trendingStickersLayout) {
                EmojiView.EmojiViewDelegate.CC.$default$showTrendingStickersAlert(this, trendingStickersLayout);
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public boolean onBackspace() {
                if (PhotoViewerCaptionEnterView.this.messageEditText.length() == 0) {
                    return false;
                }
                PhotoViewerCaptionEnterView.this.messageEditText.dispatchKeyEvent(new KeyEvent(0, 67));
                return true;
            }

            /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$3$1, reason: invalid class name */
            class AnonymousClass1 extends BaseFragment {
                AnonymousClass1() {
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public int getCurrentAccount() {
                    return this.currentAccount;
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public Context getContext() {
                    return PhotoViewerCaptionEnterView.this.getContext();
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public Activity getParentActivity() {
                    for (Context context = getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
                        if (context instanceof Activity) {
                            return (Activity) context;
                        }
                    }
                    return null;
                }

                @Override // org.telegram.ui.ActionBar.BaseFragment
                public Dialog getVisibleDialog() {
                    return new Dialog(PhotoViewerCaptionEnterView.this.getContext()) { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView.3.1.1
                        @Override // android.app.Dialog, android.content.DialogInterface
                        public void dismiss() {
                            if ((AnonymousClass1.this.getParentActivity() instanceof LaunchActivity) && ((LaunchActivity) AnonymousClass1.this.getParentActivity()).getActionBarLayout() != null) {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                ((BaseFragment) anonymousClass1).parentLayout = ((LaunchActivity) anonymousClass1.getParentActivity()).getActionBarLayout();
                                if (((BaseFragment) AnonymousClass1.this).parentLayout != null && ((BaseFragment) AnonymousClass1.this).parentLayout.getLastFragment() != null && ((BaseFragment) AnonymousClass1.this).parentLayout.getLastFragment().getVisibleDialog() != null) {
                                    Dialog visibleDialog = ((BaseFragment) AnonymousClass1.this).parentLayout.getLastFragment().getVisibleDialog();
                                    if (visibleDialog instanceof ChatAttachAlert) {
                                        ((ChatAttachAlert) visibleDialog).dismiss(true);
                                    } else {
                                        visibleDialog.dismiss();
                                    }
                                }
                            }
                            PhotoViewer.getInstance().closePhoto(false, false);
                        }
                    };
                }
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public void onAnimatedEmojiUnlockClick() {
                new PremiumFeatureBottomSheet(new AnonymousClass1(), 11, false).show();
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public void onCustomEmojiSelected(long j, TLRPC$Document tLRPC$Document, String str, boolean z) {
                AnimatedEmojiSpan animatedEmojiSpan;
                int selectionEnd = PhotoViewerCaptionEnterView.this.messageEditText.getSelectionEnd();
                if (selectionEnd < 0) {
                    selectionEnd = 0;
                }
                try {
                    try {
                        PhotoViewerCaptionEnterView.this.innerTextChange = true;
                        SpannableString spannableString = new SpannableString(str);
                        if (tLRPC$Document != null) {
                            animatedEmojiSpan = new AnimatedEmojiSpan(tLRPC$Document, PhotoViewerCaptionEnterView.this.messageEditText.getPaint().getFontMetricsInt());
                        } else {
                            animatedEmojiSpan = new AnimatedEmojiSpan(j, PhotoViewerCaptionEnterView.this.messageEditText.getPaint().getFontMetricsInt());
                        }
                        if (!z) {
                            animatedEmojiSpan.fromEmojiKeyboard = true;
                        }
                        spannableString.setSpan(animatedEmojiSpan, 0, spannableString.length(), 33);
                        PhotoViewerCaptionEnterView.this.messageEditText.setText(PhotoViewerCaptionEnterView.this.messageEditText.getText().insert(selectionEnd, spannableString));
                        int length = selectionEnd + spannableString.length();
                        PhotoViewerCaptionEnterView.this.messageEditText.setSelection(length, length);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                } finally {
                    PhotoViewerCaptionEnterView.this.innerTextChange = false;
                }
            }

            @Override // org.telegram.ui.Components.EmojiView.EmojiViewDelegate
            public void onEmojiSelected(String str) {
                int selectionEnd = PhotoViewerCaptionEnterView.this.messageEditText.getSelectionEnd();
                if (selectionEnd < 0) {
                    selectionEnd = 0;
                }
                try {
                    try {
                        PhotoViewerCaptionEnterView.this.innerTextChange = true;
                        CharSequence replaceEmoji = Emoji.replaceEmoji(str, PhotoViewerCaptionEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        PhotoViewerCaptionEnterView.this.messageEditText.setText(PhotoViewerCaptionEnterView.this.messageEditText.getText().insert(selectionEnd, replaceEmoji));
                        int length = selectionEnd + replaceEmoji.length();
                        PhotoViewerCaptionEnterView.this.messageEditText.setSelection(length, length);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                } finally {
                    PhotoViewerCaptionEnterView.this.innerTextChange = false;
                }
            }
        });
        this.sizeNotifierLayout.addView(this.emojiView);
    }

    public void addEmojiToRecent(String str) {
        createEmojiView();
        this.emojiView.addEmojiToRecent(str);
    }

    public void replaceWithText(int i, int i2, CharSequence charSequence, boolean z) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.messageEditText.getText());
            spannableStringBuilder.replace(i, i2 + i, charSequence);
            if (z) {
                Emoji.replaceEmoji(spannableStringBuilder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            }
            this.messageEditText.setText(spannableStringBuilder);
            this.messageEditText.setSelection(Math.min(i + charSequence.length(), this.messageEditText.length()));
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void setFieldFocused(boolean z) {
        EditTextCaption editTextCaption = this.messageEditText;
        if (editTextCaption == null) {
            return;
        }
        if (z) {
            if (editTextCaption.isFocused()) {
                return;
            }
            this.messageEditText.postDelayed(new Runnable() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    PhotoViewerCaptionEnterView.this.lambda$setFieldFocused$8();
                }
            }, 600L);
        } else {
            if (!editTextCaption.isFocused() || this.keyboardVisible) {
                return;
            }
            this.messageEditText.clearFocus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setFieldFocused$8() {
        EditTextCaption editTextCaption = this.messageEditText;
        if (editTextCaption != null) {
            try {
                editTextCaption.requestFocus();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public CharSequence getFieldCharSequence() {
        return AndroidUtilities.getTrimmedString(this.messageEditText.getText());
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    public boolean isPopupView(View view) {
        return view == this.emojiView;
    }

    private void showPopup(int i, boolean z) {
        if (i == 1) {
            createEmojiView();
            this.emojiView.setVisibility(0);
            this.delegate.onEmojiViewOpen();
            if (this.keyboardHeight <= 0) {
                this.keyboardHeight = MessagesController.getGlobalEmojiSettings().getInt("kbd_height", AndroidUtilities.dp(200.0f));
            }
            if (this.keyboardHeightLand <= 0) {
                this.keyboardHeightLand = MessagesController.getGlobalEmojiSettings().getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
            }
            android.graphics.Point point = AndroidUtilities.displaySize;
            int i2 = point.x > point.y ? this.keyboardHeightLand : this.keyboardHeight;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiView.getLayoutParams();
            layoutParams.width = AndroidUtilities.displaySize.x;
            layoutParams.height = i2;
            this.emojiView.setLayoutParams(layoutParams);
            if (!AndroidUtilities.isInMultiwindow && !this.forceFloatingEmoji) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
            SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto = this.sizeNotifierLayout;
            if (sizeNotifierFrameLayoutPhoto != null) {
                this.emojiPadding = i2;
                sizeNotifierFrameLayoutPhoto.requestLayout();
                this.emojiIconDrawable.setIcon(R.drawable.input_keyboard, true);
                onWindowSizeChanged();
                return;
            }
            return;
        }
        if (this.emojiButton != null) {
            this.emojiIconDrawable.setIcon(R.drawable.input_smile, true);
        }
        if (this.sizeNotifierLayout != null) {
            if (z && i == 0 && this.emojiView != null) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.emojiPadding, 0.0f);
                final float f = this.emojiPadding;
                this.popupAnimating = true;
                this.delegate.onEmojiViewCloseStart();
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        PhotoViewerCaptionEnterView.this.lambda$showPopup$9(f, valueAnimator);
                    }
                });
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.PhotoViewerCaptionEnterView.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewerCaptionEnterView.this.emojiPadding = 0;
                        PhotoViewerCaptionEnterView.this.setTranslationY(0.0f);
                        PhotoViewerCaptionEnterView.this.setAlpha(1.0f);
                        PhotoViewerCaptionEnterView.this.emojiView.setTranslationY(0.0f);
                        PhotoViewerCaptionEnterView.this.popupAnimating = false;
                        PhotoViewerCaptionEnterView.this.delegate.onEmojiViewCloseEnd();
                        PhotoViewerCaptionEnterView.this.emojiView.setVisibility(8);
                        PhotoViewerCaptionEnterView.this.emojiView.setAlpha(1.0f);
                    }
                });
                ofFloat.setDuration(210L);
                ofFloat.setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator);
                ofFloat.start();
            } else if (i == 0) {
                EmojiView emojiView = this.emojiView;
                if (emojiView != null) {
                    emojiView.setVisibility(8);
                }
                this.emojiPadding = 0;
            }
            this.sizeNotifierLayout.requestLayout();
            onWindowSizeChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPopup$9(float f, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.emojiPadding = (int) floatValue;
        float f2 = f - floatValue;
        this.emojiView.setTranslationY(f2);
        setTranslationY(f2);
        float f3 = floatValue / f;
        setAlpha(f3);
        this.emojiView.setAlpha(f3);
    }

    public void hidePopup() {
        if (isPopupShowing()) {
            showPopup(0, true);
        }
    }

    private void openKeyboardInternal() {
        showPopup((AndroidUtilities.isInMultiwindow || AndroidUtilities.usingHardwareInput) ? 0 : 2, false);
        openKeyboard();
    }

    public void openKeyboard() {
        this.messageEditText.requestFocus();
        AndroidUtilities.showKeyboard(this.messageEditText);
        try {
            EditTextCaption editTextCaption = this.messageEditText;
            editTextCaption.setSelection(editTextCaption.length(), this.messageEditText.length());
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public boolean isPopupShowing() {
        EmojiView emojiView = this.emojiView;
        return emojiView != null && emojiView.getVisibility() == 0;
    }

    public boolean isPopupAnimating() {
        return this.popupAnimating;
    }

    public void closeKeyboard() {
        AndroidUtilities.hideKeyboard(this.messageEditText);
        this.messageEditText.clearFocus();
    }

    public boolean isKeyboardVisible() {
        return ((AndroidUtilities.usingHardwareInput || AndroidUtilities.isInMultiwindow) && getTag() != null) || this.keyboardVisible;
    }

    @Override // org.telegram.ui.Components.SizeNotifierFrameLayoutPhoto.SizeNotifierFrameLayoutPhotoDelegate
    public void onSizeChanged(int i, boolean z) {
        boolean z2;
        int i2;
        if (i > AndroidUtilities.dp(50.0f) && this.keyboardVisible && !AndroidUtilities.isInMultiwindow && !this.forceFloatingEmoji) {
            if (z) {
                this.keyboardHeightLand = i;
                MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height_land3", this.keyboardHeightLand).commit();
            } else {
                this.keyboardHeight = i;
                MessagesController.getGlobalEmojiSettings().edit().putInt("kbd_height", this.keyboardHeight).commit();
            }
        }
        if (isPopupShowing()) {
            if (z) {
                i2 = this.keyboardHeightLand;
            } else {
                i2 = this.keyboardHeight;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.emojiView.getLayoutParams();
            int i3 = layoutParams.width;
            int i4 = AndroidUtilities.displaySize.x;
            if (i3 != i4 || layoutParams.height != i2) {
                layoutParams.width = i4;
                layoutParams.height = i2;
                this.emojiView.setLayoutParams(layoutParams);
                SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto = this.sizeNotifierLayout;
                if (sizeNotifierFrameLayoutPhoto != null) {
                    this.emojiPadding = layoutParams.height;
                    sizeNotifierFrameLayoutPhoto.requestLayout();
                    onWindowSizeChanged();
                }
            }
        }
        if (this.lastSizeChangeValue1 == i && this.lastSizeChangeValue2 == z) {
            onWindowSizeChanged();
            return;
        }
        this.lastSizeChangeValue1 = i;
        this.lastSizeChangeValue2 = z;
        boolean z3 = this.keyboardVisible;
        boolean z4 = i > 0;
        this.keyboardVisible = z4;
        if (z4 && isPopupShowing()) {
            showPopup(0, false);
        }
        if (this.emojiPadding != 0 && !(z2 = this.keyboardVisible) && z2 != z3 && !isPopupShowing()) {
            this.emojiPadding = 0;
            this.sizeNotifierLayout.requestLayout();
        }
        onWindowSizeChanged();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        EmojiView emojiView;
        if (i != NotificationCenter.emojiLoaded || (emojiView = this.emojiView) == null) {
            return;
        }
        emojiView.invalidateViews();
    }

    public void setAllowTextEntitiesIntersection(boolean z) {
        this.messageEditText.setAllowTextEntitiesIntersection(z);
    }

    public EditTextCaption getMessageEditText() {
        return this.messageEditText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    public Theme.ResourcesProvider getResourcesProvider() {
        return this.resourcesProvider;
    }
}
