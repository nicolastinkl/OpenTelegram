package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.ColorUtils;
import java.util.HashSet;
import java.util.Iterator;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$TL_videoSizeEmojiMarkup;
import org.telegram.tgnet.TLRPC$TL_videoSizeStickerMarkup;
import org.telegram.tgnet.TLRPC$VideoSize;
import org.telegram.ui.Components.AnimatedEmojiSpan;

/* loaded from: classes4.dex */
public class VectorAvatarThumbDrawable extends Drawable implements AnimatedEmojiSpan.InvalidateHolder, AttachableDrawable, NotificationCenter.NotificationCenterDelegate {
    AnimatedEmojiDrawable animatedEmojiDrawable;
    final int currentAccount;
    ImageReceiver currentParent;
    public final GradientTools gradientTools;
    ImageReceiver imageReceiver;
    boolean imageSeted;
    boolean isPremium;
    HashSet<ImageReceiver> parents;
    float roundRadius;
    TLRPC$TL_videoSizeStickerMarkup sizeStickerMarkup;
    ImageReceiver stickerPreloadImageReceiver;
    private final int type;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public VectorAvatarThumbDrawable(TLRPC$VideoSize tLRPC$VideoSize, boolean z, int i) {
        GradientTools gradientTools = new GradientTools();
        this.gradientTools = gradientTools;
        this.parents = new HashSet<>();
        this.stickerPreloadImageReceiver = new ImageReceiver();
        this.currentAccount = UserConfig.selectedAccount;
        this.type = i;
        this.isPremium = z;
        gradientTools.setColors(ColorUtils.setAlphaComponent(tLRPC$VideoSize.background_colors.get(0).intValue(), 255), tLRPC$VideoSize.background_colors.size() > 1 ? ColorUtils.setAlphaComponent(tLRPC$VideoSize.background_colors.get(1).intValue(), 255) : 0, tLRPC$VideoSize.background_colors.size() > 2 ? ColorUtils.setAlphaComponent(tLRPC$VideoSize.background_colors.get(2).intValue(), 255) : 0, tLRPC$VideoSize.background_colors.size() > 3 ? ColorUtils.setAlphaComponent(tLRPC$VideoSize.background_colors.get(3).intValue(), 255) : 0);
        if (tLRPC$VideoSize instanceof TLRPC$TL_videoSizeEmojiMarkup) {
            TLRPC$TL_videoSizeEmojiMarkup tLRPC$TL_videoSizeEmojiMarkup = (TLRPC$TL_videoSizeEmojiMarkup) tLRPC$VideoSize;
            int i2 = 8;
            if (i == 1 && z) {
                i2 = 7;
            } else if (i == 2) {
                i2 = 15;
            }
            this.animatedEmojiDrawable = new AnimatedEmojiDrawable(i2, UserConfig.selectedAccount, tLRPC$TL_videoSizeEmojiMarkup.emoji_id);
            return;
        }
        if (tLRPC$VideoSize instanceof TLRPC$TL_videoSizeStickerMarkup) {
            this.sizeStickerMarkup = (TLRPC$TL_videoSizeStickerMarkup) tLRPC$VideoSize;
            ImageReceiver imageReceiver = new ImageReceiver() { // from class: org.telegram.ui.Components.VectorAvatarThumbDrawable.1
                @Override // org.telegram.messenger.ImageReceiver
                public void invalidate() {
                    VectorAvatarThumbDrawable.this.invalidate();
                }
            };
            this.imageReceiver = imageReceiver;
            imageReceiver.setInvalidateAll(true);
            if (i == 1) {
                this.imageReceiver.setAutoRepeatCount(2);
            }
            setImage();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setImage() {
        /*
            r15 = this;
            int r0 = r15.currentAccount
            org.telegram.messenger.MediaDataController r0 = org.telegram.messenger.MediaDataController.getInstance(r0)
            org.telegram.tgnet.TLRPC$TL_videoSizeStickerMarkup r1 = r15.sizeStickerMarkup
            org.telegram.tgnet.TLRPC$InputStickerSet r1 = r1.stickerset
            r2 = 0
            org.telegram.tgnet.TLRPC$TL_messages_stickerSet r0 = r0.getStickerSet(r1, r2)
            if (r0 == 0) goto L8c
            r1 = 1
            r15.imageSeted = r1
        L14:
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r3 = r0.documents
            int r3 = r3.size()
            if (r2 >= r3) goto L8c
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r3 = r0.documents
            java.lang.Object r3 = r3.get(r2)
            org.telegram.tgnet.TLRPC$Document r3 = (org.telegram.tgnet.TLRPC$Document) r3
            long r3 = r3.id
            org.telegram.tgnet.TLRPC$TL_videoSizeStickerMarkup r5 = r15.sizeStickerMarkup
            long r5 = r5.sticker_id
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L89
            java.util.ArrayList<org.telegram.tgnet.TLRPC$Document> r0 = r0.documents
            java.lang.Object r0 = r0.get(r2)
            org.telegram.tgnet.TLRPC$Document r0 = (org.telegram.tgnet.TLRPC$Document) r0
            boolean r2 = r15.isPremium
            r3 = 0
            java.lang.String r4 = "50_50_firstframe"
            if (r2 == 0) goto L44
            int r2 = r15.type
            if (r2 != r1) goto L44
            java.lang.String r1 = "50_50"
            goto L4b
        L44:
            int r1 = r15.type
            r2 = 2
            if (r1 != r2) goto L4f
            java.lang.String r1 = "100_100"
        L4b:
            r3 = r0
            r6 = r4
            r4 = r1
            goto L50
        L4f:
            r6 = r3
        L50:
            int r1 = org.telegram.ui.ActionBar.Theme.key_windowBackgroundWhiteGrayIcon
            r2 = 1045220557(0x3e4ccccd, float:0.2)
            org.telegram.messenger.SvgHelper$SvgDrawable r9 = org.telegram.messenger.DocumentObject.getSvgThumb(r0, r1, r2)
            org.telegram.messenger.ImageReceiver r2 = r15.imageReceiver
            org.telegram.messenger.ImageLocation r1 = org.telegram.messenger.ImageLocation.getForDocument(r0)
            org.telegram.messenger.ImageLocation r5 = org.telegram.messenger.ImageLocation.getForDocument(r3)
            r7 = 0
            r8 = 0
            r10 = 0
            r14 = 0
            java.lang.String r12 = "tgs"
            r3 = r1
            r13 = r0
            r2.setImage(r3, r4, r5, r6, r7, r8, r9, r10, r12, r13, r14)
            int r1 = r15.type
            r2 = 3
            if (r1 != r2) goto L8c
            org.telegram.messenger.ImageReceiver r2 = r15.stickerPreloadImageReceiver
            org.telegram.messenger.ImageLocation r3 = org.telegram.messenger.ImageLocation.getForDocument(r0)
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r12 = 0
            java.lang.String r4 = "100_100"
            java.lang.String r10 = "tgs"
            r11 = r0
            r2.setImage(r3, r4, r5, r6, r7, r8, r10, r11, r12)
            goto L8c
        L89:
            int r2 = r2 + 1
            goto L14
        L8c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.VectorAvatarThumbDrawable.setImage():void");
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.gradientTools.setBounds(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom);
        if (this.currentParent != null) {
            this.roundRadius = r0.getRoundRadius()[0];
        }
        float f = this.roundRadius;
        if (f == 0.0f) {
            canvas.drawRect(getBounds(), this.gradientTools.paint);
        } else {
            GradientTools gradientTools = this.gradientTools;
            canvas.drawRoundRect(gradientTools.bounds, f, f, gradientTools.paint);
        }
        int centerX = getBounds().centerX();
        int centerY = getBounds().centerY();
        int width = ((int) (getBounds().width() * 0.7f)) >> 1;
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            if (animatedEmojiDrawable.getImageReceiver() != null) {
                this.animatedEmojiDrawable.getImageReceiver().setRoundRadius((int) (width * 2 * 0.13f));
            }
            this.animatedEmojiDrawable.setBounds(centerX - width, centerY - width, centerX + width, centerY + width);
            this.animatedEmojiDrawable.draw(canvas);
        }
        ImageReceiver imageReceiver = this.imageReceiver;
        if (imageReceiver != null) {
            float f2 = width * 2;
            imageReceiver.setRoundRadius((int) (0.13f * f2));
            this.imageReceiver.setImageCoords(centerX - width, centerY - width, f2, f2);
            this.imageReceiver.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.gradientTools.paint.setAlpha(i);
        AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
        if (animatedEmojiDrawable != null) {
            animatedEmojiDrawable.setAlpha(i);
        }
    }

    @Override // org.telegram.ui.Components.AttachableDrawable
    public void onAttachedToWindow(ImageReceiver imageReceiver) {
        if (imageReceiver == null) {
            return;
        }
        this.roundRadius = imageReceiver.getRoundRadius()[0];
        if (this.parents.isEmpty()) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.addView(this);
            }
            ImageReceiver imageReceiver2 = this.imageReceiver;
            if (imageReceiver2 != null) {
                imageReceiver2.onAttachedToWindow();
            }
            ImageReceiver imageReceiver3 = this.stickerPreloadImageReceiver;
            if (imageReceiver3 != null) {
                imageReceiver3.onAttachedToWindow();
            }
        }
        this.parents.add(imageReceiver);
        if (this.sizeStickerMarkup != null) {
            NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.groupStickersDidLoad);
        }
    }

    @Override // org.telegram.ui.Components.AttachableDrawable
    public void onDetachedFromWindow(ImageReceiver imageReceiver) {
        this.parents.remove(imageReceiver);
        if (this.parents.isEmpty()) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
            if (animatedEmojiDrawable != null) {
                animatedEmojiDrawable.removeView(this);
            }
            ImageReceiver imageReceiver2 = this.imageReceiver;
            if (imageReceiver2 != null) {
                imageReceiver2.onDetachedFromWindow();
            }
            ImageReceiver imageReceiver3 = this.stickerPreloadImageReceiver;
            if (imageReceiver3 != null) {
                imageReceiver3.onDetachedFromWindow();
            }
        }
        if (this.sizeStickerMarkup != null) {
            NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.groupStickersDidLoad);
        }
    }

    @Override // org.telegram.ui.Components.AnimatedEmojiSpan.InvalidateHolder
    public void invalidate() {
        Iterator<ImageReceiver> it = this.parents.iterator();
        while (it.hasNext()) {
            it.next().invalidate();
        }
    }

    public boolean equals(Object obj) {
        TLRPC$TL_videoSizeStickerMarkup tLRPC$TL_videoSizeStickerMarkup;
        if (this == obj) {
            return true;
        }
        if (obj != null && VectorAvatarThumbDrawable.class == obj.getClass()) {
            VectorAvatarThumbDrawable vectorAvatarThumbDrawable = (VectorAvatarThumbDrawable) obj;
            if (this.type == vectorAvatarThumbDrawable.type) {
                GradientTools gradientTools = this.gradientTools;
                int i = gradientTools.color1;
                GradientTools gradientTools2 = vectorAvatarThumbDrawable.gradientTools;
                if (i == gradientTools2.color1 && gradientTools.color2 == gradientTools2.color2 && gradientTools.color3 == gradientTools2.color3 && gradientTools.color4 == gradientTools2.color4) {
                    AnimatedEmojiDrawable animatedEmojiDrawable = this.animatedEmojiDrawable;
                    if (animatedEmojiDrawable != null && vectorAvatarThumbDrawable.animatedEmojiDrawable != null) {
                        return animatedEmojiDrawable.getDocumentId() == vectorAvatarThumbDrawable.animatedEmojiDrawable.getDocumentId();
                    }
                    TLRPC$TL_videoSizeStickerMarkup tLRPC$TL_videoSizeStickerMarkup2 = this.sizeStickerMarkup;
                    return tLRPC$TL_videoSizeStickerMarkup2 != null && (tLRPC$TL_videoSizeStickerMarkup = vectorAvatarThumbDrawable.sizeStickerMarkup) != null && tLRPC$TL_videoSizeStickerMarkup2.stickerset.id == tLRPC$TL_videoSizeStickerMarkup.stickerset.id && tLRPC$TL_videoSizeStickerMarkup2.sticker_id == tLRPC$TL_videoSizeStickerMarkup.sticker_id;
                }
            }
        }
        return false;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i != NotificationCenter.groupStickersDidLoad || this.imageSeted) {
            return;
        }
        setImage();
    }

    public void setParent(ImageReceiver imageReceiver) {
        this.currentParent = imageReceiver;
    }
}
