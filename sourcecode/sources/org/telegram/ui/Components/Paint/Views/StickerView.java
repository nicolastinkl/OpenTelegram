package org.telegram.ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DocumentAttribute;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_maskCoords;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.Paint.Views.EntityView;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.Rect;
import org.telegram.ui.Components.Size;

/* loaded from: classes4.dex */
public class StickerView extends EntityView {
    private int anchor;
    private Size baseSize;
    public final ImageReceiver centerImage;
    private FrameLayoutDrawer containerView;
    private boolean mirrored;
    private Object parentObject;
    private TLRPC$Document sticker;

    protected void didSetAnimatedSticker(RLottieDrawable rLottieDrawable) {
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            StickerView.this.stickerDraw(canvas);
        }
    }

    public StickerView(Context context, Point point, float f, float f2, Size size, TLRPC$Document tLRPC$Document, Object obj) {
        super(context, point);
        this.anchor = -1;
        int i = 0;
        this.mirrored = false;
        this.centerImage = new ImageReceiver();
        setRotation(f);
        setScale(f2);
        this.sticker = tLRPC$Document;
        this.baseSize = size;
        this.parentObject = obj;
        while (true) {
            if (i >= tLRPC$Document.attributes.size()) {
                break;
            }
            TLRPC$DocumentAttribute tLRPC$DocumentAttribute = tLRPC$Document.attributes.get(i);
            if (tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                TLRPC$TL_maskCoords tLRPC$TL_maskCoords = tLRPC$DocumentAttribute.mask_coords;
                if (tLRPC$TL_maskCoords != null) {
                    this.anchor = tLRPC$TL_maskCoords.n;
                }
            } else {
                i++;
            }
        }
        FrameLayoutDrawer frameLayoutDrawer = new FrameLayoutDrawer(context);
        this.containerView = frameLayoutDrawer;
        addView(frameLayoutDrawer, LayoutHelper.createFrame(-1, -1.0f));
        this.centerImage.setAspectFit(true);
        this.centerImage.setInvalidateAll(true);
        this.centerImage.setParentView(this.containerView);
        this.centerImage.setImage(ImageLocation.getForDocument(tLRPC$Document), (String) null, ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(tLRPC$Document.thumbs, 90), tLRPC$Document), (String) null, "webp", obj, 1);
        this.centerImage.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Components.Paint.Views.StickerView$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
                StickerView.this.lambda$new$0(imageReceiver, z, z2, z3);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver);
            }
        });
        updatePosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        RLottieDrawable lottieAnimation;
        if (!z || z2 || (lottieAnimation = imageReceiver.getLottieAnimation()) == null) {
            return;
        }
        didSetAnimatedSticker(lottieAnimation);
    }

    public StickerView(Context context, StickerView stickerView, Point point) {
        this(context, point, stickerView.getRotation(), stickerView.getScale(), stickerView.baseSize, stickerView.sticker, stickerView.parentObject);
        if (stickerView.mirrored) {
            mirror();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.centerImage.onDetachedFromWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.centerImage.onAttachedToWindow();
    }

    public int getAnchor() {
        return this.anchor;
    }

    public void mirror() {
        this.mirrored = !this.mirrored;
        this.containerView.invalidate();
    }

    public boolean isMirrored() {
        return this.mirrored;
    }

    @Override // org.telegram.ui.Components.Paint.Views.EntityView
    protected void updatePosition() {
        Size size = this.baseSize;
        float f = size.width / 2.0f;
        float f2 = size.height / 2.0f;
        setX(getPositionX() - f);
        setY(getPositionY() - f2);
        updateSelectionView();
    }

    protected void stickerDraw(Canvas canvas) {
        if (this.containerView == null) {
            return;
        }
        canvas.save();
        if (this.mirrored) {
            canvas.scale(-1.0f, 1.0f);
            canvas.translate(-this.baseSize.width, 0.0f);
        }
        ImageReceiver imageReceiver = this.centerImage;
        Size size = this.baseSize;
        imageReceiver.setImageCoords(0.0f, 0.0f, (int) size.width, (int) size.height);
        this.centerImage.draw(canvas);
        canvas.restore();
    }

    public long getDuration() {
        RLottieDrawable lottieAnimation = this.centerImage.getLottieAnimation();
        if (lottieAnimation != null) {
            return lottieAnimation.getDuration();
        }
        if (this.centerImage.getAnimation() != null) {
            return r0.getDurationMs();
        }
        return 0L;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int) this.baseSize.width, 1073741824), View.MeasureSpec.makeMeasureSpec((int) this.baseSize.height, 1073741824));
    }

    @Override // org.telegram.ui.Components.Paint.Views.EntityView
    protected Rect getSelectionBounds() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return new Rect();
        }
        float scaleX = viewGroup.getScaleX();
        float measuredWidth = getMeasuredWidth() * (getScale() + 0.5f);
        float f = measuredWidth / 2.0f;
        float f2 = measuredWidth * scaleX;
        return new Rect((getPositionX() - f) * scaleX, (getPositionY() - f) * scaleX, f2, f2);
    }

    @Override // org.telegram.ui.Components.Paint.Views.EntityView
    protected EntityView.SelectionView createSelectionView() {
        return new StickerViewSelectionView(this, getContext());
    }

    public TLRPC$Document getSticker() {
        return this.sticker;
    }

    public Object getParentObject() {
        return this.parentObject;
    }

    public Size getBaseSize() {
        return this.baseSize;
    }

    public class StickerViewSelectionView extends EntityView.SelectionView {
        private RectF arcRect;

        public StickerViewSelectionView(StickerView stickerView, Context context) {
            super(context);
            this.arcRect = new RectF();
        }

        @Override // org.telegram.ui.Components.Paint.Views.EntityView.SelectionView
        protected int pointInsideHandle(float f, float f2) {
            float dp = AndroidUtilities.dp(1.0f);
            float dp2 = AndroidUtilities.dp(19.5f);
            float f3 = dp + dp2;
            float f4 = f3 * 2.0f;
            float measuredHeight = ((getMeasuredHeight() - f4) / 2.0f) + f3;
            if (f > f3 - dp2 && f2 > measuredHeight - dp2 && f < f3 + dp2 && f2 < measuredHeight + dp2) {
                return 1;
            }
            if (f > ((getMeasuredWidth() - f4) + f3) - dp2 && f2 > measuredHeight - dp2 && f < f3 + (getMeasuredWidth() - f4) + dp2 && f2 < measuredHeight + dp2) {
                return 2;
            }
            float measuredWidth = getMeasuredWidth() / 2.0f;
            return Math.pow((double) (f - measuredWidth), 2.0d) + Math.pow((double) (f2 - measuredWidth), 2.0d) < Math.pow((double) measuredWidth, 2.0d) ? 3 : 0;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float dp = AndroidUtilities.dp(1.0f);
            float dp2 = AndroidUtilities.dp(4.5f);
            float dp3 = dp + dp2 + AndroidUtilities.dp(15.0f);
            float measuredWidth = (getMeasuredWidth() / 2) - dp3;
            float f = dp3 + (2.0f * measuredWidth);
            this.arcRect.set(dp3, dp3, f, f);
            canvas.drawArc(this.arcRect, 0.0f, 180.0f, false, this.paint);
            canvas.drawArc(this.arcRect, 180.0f, 180.0f, false, this.paint);
            float f2 = measuredWidth + dp3;
            canvas.drawCircle(dp3, f2, dp2, this.dotPaint);
            canvas.drawCircle(dp3, f2, dp2, this.dotStrokePaint);
            canvas.drawCircle(f, f2, dp2, this.dotPaint);
            canvas.drawCircle(f, f2, dp2, this.dotStrokePaint);
        }
    }
}
