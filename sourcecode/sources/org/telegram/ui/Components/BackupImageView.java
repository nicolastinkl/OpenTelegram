package org.telegram.ui.Components;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.SecureDocument;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;

/* loaded from: classes4.dex */
public class BackupImageView extends View {
    public AnimatedEmojiDrawable animatedEmojiDrawable;
    boolean attached;
    private AvatarDrawable avatarDrawable;
    protected boolean blurAllowed;
    protected ImageReceiver blurImageReceiver;
    public boolean drawFromStart;
    protected boolean hasBlur;
    protected int height;
    protected ImageReceiver imageReceiver;
    protected int width;

    public BackupImageView(Context context) {
        super(context);
        this.width = -1;
        this.height = -1;
        ImageReceiver imageReceiver = new ImageReceiver(this);
        this.imageReceiver = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Components.BackupImageView$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver2, boolean z, boolean z2, boolean z3) {
                BackupImageView.this.lambda$new$0(imageReceiver2, z, z2, z3);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver2) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        if (!z || z2) {
            return;
        }
        checkCreateBlurredImage();
    }

    public BackupImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.width = -1;
        this.height = -1;
        ImageReceiver imageReceiver = new ImageReceiver(this);
        this.imageReceiver = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        this.imageReceiver.setDelegate(new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.Components.BackupImageView$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver2, boolean z, boolean z2, boolean z3) {
                BackupImageView.this.lambda$new$1(imageReceiver2, z, z2, z3);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver2) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        if (!z || z2) {
            return;
        }
        checkCreateBlurredImage();
    }

    public void setBlurAllowed(boolean z) {
        if (this.attached) {
            throw new IllegalStateException("You should call setBlurAllowed(...) only when detached!");
        }
        this.blurAllowed = z;
        if (z) {
            this.blurImageReceiver = new ImageReceiver();
        }
    }

    public void setHasBlur(boolean z) {
        if (z && !this.blurAllowed) {
            throw new IllegalStateException("You should call setBlurAllowed(...) before calling setHasBlur(true)!");
        }
        this.hasBlur = z;
        if (!z) {
            if (this.blurImageReceiver.getBitmap() != null && !this.blurImageReceiver.getBitmap().isRecycled()) {
                this.blurImageReceiver.getBitmap().recycle();
            }
            this.blurImageReceiver.setImageBitmap((Bitmap) null);
        }
        checkCreateBlurredImage();
    }

    public void onNewImageSet() {
        if (this.hasBlur) {
            if (this.blurImageReceiver.getBitmap() != null && !this.blurImageReceiver.getBitmap().isRecycled()) {
                this.blurImageReceiver.getBitmap().recycle();
            }
            this.blurImageReceiver.setImageBitmap((Bitmap) null);
            checkCreateBlurredImage();
        }
    }

    private void checkCreateBlurredImage() {
        Bitmap bitmap;
        if (!this.hasBlur || this.blurImageReceiver.getBitmap() != null || this.imageReceiver.getBitmap() == null || (bitmap = this.imageReceiver.getBitmap()) == null || bitmap.isRecycled()) {
            return;
        }
        this.blurImageReceiver.setImageBitmap(Utilities.stackBlurBitmapMax(bitmap));
        invalidate();
    }

    public void setOrientation(int i, boolean z) {
        this.imageReceiver.setOrientation(i, z);
    }

    public void setOrientation(int i, int i2, boolean z) {
        this.imageReceiver.setOrientation(i, i2, z);
    }

    public void setImage(SecureDocument secureDocument, String str) {
        setImage(ImageLocation.getForSecureDocument(secureDocument), str, null, null, null, null, null, 0, null);
    }

    public void setImage(ImageLocation imageLocation, String str, String str2, Drawable drawable, Object obj) {
        setImage(imageLocation, str, null, null, drawable, null, str2, 0, obj);
    }

    public void setImage(ImageLocation imageLocation, String str, Drawable drawable, Object obj) {
        setImage(imageLocation, str, null, null, drawable, null, null, 0, obj);
    }

    public void setImage(ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, Drawable drawable, Object obj) {
        this.imageReceiver.setImage(imageLocation, str, imageLocation2, str2, null, null, drawable, 0L, null, obj, 1);
        onNewImageSet();
    }

    public void setImage(ImageLocation imageLocation, String str, Drawable drawable, int i, Object obj) {
        setImage(imageLocation, str, null, null, drawable, null, null, i, obj);
    }

    public void setForUserOrChat(TLObject tLObject, AvatarDrawable avatarDrawable) {
        this.imageReceiver.setForUserOrChat(tLObject, avatarDrawable);
        onNewImageSet();
    }

    public void setForUserOrChat(TLObject tLObject, AvatarDrawable avatarDrawable, Object obj) {
        this.imageReceiver.setForUserOrChat(tLObject, avatarDrawable, obj);
        onNewImageSet();
    }

    public void setImageMedia(ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, Bitmap bitmap, int i, int i2, Object obj) {
        BackupImageView backupImageView;
        BitmapDrawable bitmapDrawable;
        if (bitmap != null) {
            backupImageView = this;
            bitmapDrawable = new BitmapDrawable((Resources) null, bitmap);
        } else {
            backupImageView = this;
            bitmapDrawable = null;
        }
        backupImageView.imageReceiver.setImage(imageLocation, str, imageLocation2, str2, null, null, bitmapDrawable, i, null, obj, i2);
        onNewImageSet();
    }

    public void setImage(ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, int i, Object obj) {
        setImage(imageLocation, str, imageLocation2, str2, null, null, null, i, obj);
    }

    public void setImage(String str, String str2, Drawable drawable) {
        setImage(ImageLocation.getForPath(str), str2, null, null, drawable, null, null, 0, null);
    }

    public void setImage(String str, String str2, String str3, String str4) {
        setImage(ImageLocation.getForPath(str), str2, ImageLocation.getForPath(str3), str4, null, null, null, 0, null);
    }

    public void setImage(ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, Drawable drawable, Bitmap bitmap, String str3, int i, Object obj) {
        BackupImageView backupImageView;
        Drawable drawable2;
        if (bitmap != null) {
            backupImageView = this;
            drawable2 = new BitmapDrawable((Resources) null, bitmap);
        } else {
            backupImageView = this;
            drawable2 = drawable;
        }
        backupImageView.imageReceiver.setImage(imageLocation, str, imageLocation2, str2, drawable2, i, str3, obj, 0);
        onNewImageSet();
    }

    public void setImage(ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, String str3, long j, int i, Object obj) {
        this.imageReceiver.setImage(imageLocation, str, imageLocation2, str2, null, j, str3, obj, i);
        onNewImageSet();
    }

    public void setImageMedia(VectorAvatarThumbDrawable vectorAvatarThumbDrawable, ImageLocation imageLocation, String str, ImageLocation imageLocation2, String str2, ImageLocation imageLocation3, String str3, String str4, int i, int i2, Object obj) {
        if (vectorAvatarThumbDrawable != null) {
            this.imageReceiver.setImageBitmap(vectorAvatarThumbDrawable);
        } else {
            this.imageReceiver.setImage(imageLocation, str, imageLocation2, str2, imageLocation3, str3, null, i, str4, obj, i2);
        }
        onNewImageSet();
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageReceiver.setImageBitmap(bitmap);
        onNewImageSet();
    }

    public void setImageResource(int i) {
        this.imageReceiver.setImageBitmap(getResources().getDrawable(i));
        invalidate();
        onNewImageSet();
    }

    public void setImageDrawable(Drawable drawable) {
        this.imageReceiver.setImageBitmap(drawable);
        onNewImageSet();
    }

    public void setLayerNum(int i) {
        this.imageReceiver.setLayerNum(i);
    }

    public void setRoundRadius(int i) {
        this.imageReceiver.setRoundRadius(i);
        if (this.blurAllowed) {
            this.blurImageReceiver.setRoundRadius(i);
        }
        invalidate();
    }

    public void setRoundRadius(int i, int i2, int i3, int i4) {
        this.imageReceiver.setRoundRadius(i, i2, i3, i4);
        if (this.blurAllowed) {
            this.blurImageReceiver.setRoundRadius(i, i2, i3, i4);
        }
        invalidate();
    }

    public int[] getRoundRadius() {
        return this.imageReceiver.getRoundRadius();
    }

    public void setAspectFit(boolean z) {
        this.imageReceiver.setAspectFit(z);
    }

    public ImageReceiver getImageReceiver() {
        return this.imageReceiver;
    }

    public void setSize(int i, int i2) {
        this.width = i;
        this.height = i2;
        invalidate();
    }

    public AvatarDrawable getAvatarDrawable() {
        if (this.avatarDrawable == null) {
            this.avatarDrawable = new AvatarDrawable();
        }
        return this.avatarDrawable;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.attached = false;
        this.imageReceiver.onDetachedFromWindow();
        if (this.blurAllowed) {
            this.blurImageReceiver.onDetachedFromWindow();
        }
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.removeView(this);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attached = true;
        this.imageReceiver.onAttachedToWindow();
        if (this.blurAllowed) {
            this.blurImageReceiver.onAttachedToWindow();
        }
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.addView(this);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int i;
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        ImageReceiver imageReceiver = animatedEmojiDrawable != null ? animatedEmojiDrawable.getImageReceiver() : this.imageReceiver;
        if (imageReceiver == null) {
            return;
        }
        int i2 = this.width;
        if (i2 != -1 && (i = this.height) != -1) {
            if (this.drawFromStart) {
                imageReceiver.setImageCoords(0.0f, 0.0f, i2, i);
                if (this.blurAllowed) {
                    this.blurImageReceiver.setImageCoords(0.0f, 0.0f, this.width, this.height);
                }
            } else {
                float width = (getWidth() - this.width) / 2;
                int height = getHeight();
                imageReceiver.setImageCoords(width, (height - r3) / 2, this.width, this.height);
                if (this.blurAllowed) {
                    ImageReceiver imageReceiver2 = this.blurImageReceiver;
                    float width2 = (getWidth() - this.width) / 2;
                    int height2 = getHeight();
                    imageReceiver2.setImageCoords(width2, (height2 - r4) / 2, this.width, this.height);
                }
            }
        } else {
            imageReceiver.setImageCoords(0.0f, 0.0f, getWidth(), getHeight());
            if (this.blurAllowed) {
                this.blurImageReceiver.setImageCoords(0.0f, 0.0f, getWidth(), getHeight());
            }
        }
        imageReceiver.draw(canvas);
        if (this.blurAllowed) {
            this.blurImageReceiver.draw(canvas);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.imageReceiver.setColorFilter(colorFilter);
    }

    public void setAnimatedEmojiDrawable(AnimatedEmojiDrawable animatedEmojiDrawable) {
        AnimatedEmojiDrawable animatedEmojiDrawable2 = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable2 == animatedEmojiDrawable) {
            return;
        }
        if (this.attached && animatedEmojiDrawable2 != null) {
            animatedEmojiDrawable2.removeView(this);
        }
        this.animatedEmojiDrawable = animatedEmojiDrawable;
        if (this.attached && animatedEmojiDrawable != null) {
            animatedEmojiDrawable.addView(this);
        }
        invalidate();
    }
}
