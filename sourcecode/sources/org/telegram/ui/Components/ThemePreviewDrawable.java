package org.telegram.ui.Components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import java.io.File;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.R;
import org.telegram.messenger.SvgHelper;
import org.telegram.ui.ActionBar.Theme;

/* loaded from: classes4.dex */
public class ThemePreviewDrawable extends BitmapDrawable {
    public ThemePreviewDrawable(File file, DocumentObject.ThemeDocument themeDocument) {
        super(createPreview(file, themeDocument));
    }

    private static Bitmap createPreview(File file, DocumentObject.ThemeDocument themeDocument) {
        boolean z;
        Drawable createDitheredGradientBitmapDrawable;
        MotionBackgroundDrawable motionBackgroundDrawable;
        int patternColor;
        boolean z2;
        Bitmap bitmap;
        Bitmap decodeFile;
        new RectF();
        Paint paint = new Paint();
        Bitmap createBitmap = Bitmaps.createBitmap(560, 678, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        SparseIntArray themeFileValues = Theme.getThemeFileValues(null, themeDocument.baseTheme.assetName, null);
        final SparseIntArray clone = themeFileValues.clone();
        themeDocument.accent.fillAccentColors(themeFileValues, clone);
        int previewColor = Theme.getPreviewColor(clone, Theme.key_actionBarDefault);
        int previewColor2 = Theme.getPreviewColor(clone, Theme.key_actionBarDefaultIcon);
        int previewColor3 = Theme.getPreviewColor(clone, Theme.key_chat_messagePanelBackground);
        int previewColor4 = Theme.getPreviewColor(clone, Theme.key_chat_messagePanelIcons);
        int previewColor5 = Theme.getPreviewColor(clone, Theme.key_chat_inBubble);
        int previewColor6 = Theme.getPreviewColor(clone, Theme.key_chat_outBubble);
        Integer valueOf = Integer.valueOf(clone.get(Theme.key_chat_wallpaper));
        Integer valueOf2 = Integer.valueOf(clone.get(Theme.key_chat_wallpaper_gradient_to1));
        Integer valueOf3 = Integer.valueOf(clone.get(Theme.key_chat_wallpaper_gradient_to2));
        Integer valueOf4 = Integer.valueOf(clone.get(Theme.key_chat_wallpaper_gradient_to3));
        Integer valueOf5 = Integer.valueOf(clone.get(Theme.key_chat_wallpaper_gradient_rotation));
        if (valueOf5 == null) {
            valueOf5 = 45;
        }
        Drawable mutate = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.preview_back).mutate();
        Theme.setDrawableColor(mutate, previewColor2);
        Drawable mutate2 = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.preview_dots).mutate();
        Theme.setDrawableColor(mutate2, previewColor2);
        Drawable mutate3 = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.preview_smile).mutate();
        Theme.setDrawableColor(mutate3, previewColor4);
        Drawable mutate4 = ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.preview_mic).mutate();
        Theme.setDrawableColor(mutate4, previewColor4);
        Theme.MessageDrawable[] messageDrawableArr = new Theme.MessageDrawable[2];
        int i = 0;
        while (i < 2) {
            Drawable drawable = mutate2;
            Paint paint2 = paint;
            int i2 = previewColor;
            messageDrawableArr[i] = new Theme.MessageDrawable(2, i == 1, false) { // from class: org.telegram.ui.Components.ThemePreviewDrawable.1
                @Override // org.telegram.ui.ActionBar.Theme.MessageDrawable
                protected int getColor(int i3) {
                    Integer valueOf6 = Integer.valueOf(clone.get(i3));
                    if (valueOf6 == null) {
                        return Theme.getColor(i3);
                    }
                    return valueOf6.intValue();
                }

                @Override // org.telegram.ui.ActionBar.Theme.MessageDrawable
                protected int getCurrentColor(int i3) {
                    return clone.get(i3);
                }
            };
            Theme.setDrawableColor(messageDrawableArr[i], i == 1 ? previewColor6 : previewColor5);
            i++;
            mutate2 = drawable;
            paint = paint2;
            previewColor = i2;
        }
        Paint paint3 = paint;
        int i3 = previewColor;
        Drawable drawable2 = mutate2;
        if (valueOf != null) {
            if (valueOf2 == null) {
                createDitheredGradientBitmapDrawable = new ColorDrawable(valueOf.intValue());
                patternColor = AndroidUtilities.getPatternColor(valueOf.intValue());
                motionBackgroundDrawable = null;
            } else {
                if (valueOf3.intValue() != 0) {
                    motionBackgroundDrawable = new MotionBackgroundDrawable(valueOf.intValue(), valueOf2.intValue(), valueOf3.intValue(), valueOf4.intValue(), true);
                    createDitheredGradientBitmapDrawable = null;
                } else {
                    createDitheredGradientBitmapDrawable = BackgroundGradientDrawable.createDitheredGradientBitmapDrawable(valueOf5.intValue(), new int[]{valueOf.intValue(), valueOf2.intValue()}, createBitmap.getWidth(), createBitmap.getHeight() - 120);
                    motionBackgroundDrawable = null;
                }
                patternColor = AndroidUtilities.getPatternColor(AndroidUtilities.getAverageColor(valueOf.intValue(), valueOf2.intValue()));
            }
            if (createDitheredGradientBitmapDrawable != null) {
                z2 = false;
                createDitheredGradientBitmapDrawable.setBounds(0, 120, createBitmap.getWidth(), createBitmap.getHeight() - 120);
                createDitheredGradientBitmapDrawable.draw(canvas);
            } else {
                z2 = false;
            }
            if (file != null) {
                if ("application/x-tgwallpattern".equals(themeDocument.mime_type)) {
                    decodeFile = SvgHelper.getBitmap(file, 560, 678, z2);
                } else {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    float f = options.outWidth;
                    float f2 = options.outHeight;
                    float min = Math.min(f / 560, f2 / 678);
                    if (min < 1.2f) {
                        min = 1.0f;
                    }
                    options.inJustDecodeBounds = false;
                    if (min > 1.0f && (f > 560 || f2 > 678)) {
                        int i4 = 1;
                        do {
                            i4 *= 2;
                        } while (i4 * 2 < min);
                        options.inSampleSize = i4;
                    } else {
                        options.inSampleSize = (int) min;
                    }
                    decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                }
                bitmap = decodeFile;
                if (bitmap != null) {
                    if (motionBackgroundDrawable != null) {
                        motionBackgroundDrawable.setPatternBitmap((int) (themeDocument.accent.patternIntensity * 100.0f), bitmap);
                        motionBackgroundDrawable.setBounds(0, 120, createBitmap.getWidth(), createBitmap.getHeight() - 120);
                        motionBackgroundDrawable.draw(canvas);
                    } else {
                        Paint paint4 = new Paint(2);
                        if (themeDocument.accent.patternIntensity >= 0.0f) {
                            paint4.setColorFilter(new PorterDuffColorFilter(patternColor, PorterDuff.Mode.SRC_IN));
                        }
                        paint4.setAlpha(255);
                        float max = Math.max(560 / bitmap.getWidth(), 678 / bitmap.getHeight());
                        int width = (int) (bitmap.getWidth() * max);
                        canvas.save();
                        canvas.translate((560 - width) / 2, (678 - ((int) (bitmap.getHeight() * max))) / 2);
                        canvas.scale(max, max);
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint4);
                        canvas.restore();
                    }
                }
            } else {
                bitmap = null;
            }
            if (bitmap == null && motionBackgroundDrawable != null) {
                motionBackgroundDrawable.setBounds(0, 120, createBitmap.getWidth(), createBitmap.getHeight() - 120);
                motionBackgroundDrawable.draw(canvas);
            }
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            Drawable createDefaultWallpaper = Theme.createDefaultWallpaper(createBitmap.getWidth(), createBitmap.getHeight() - 120);
            createDefaultWallpaper.setBounds(0, 120, createBitmap.getWidth(), createBitmap.getHeight() - 120);
            createDefaultWallpaper.draw(canvas);
        }
        paint3.setColor(i3);
        canvas.drawRect(0.0f, 0.0f, createBitmap.getWidth(), 120.0f, paint3);
        if (mutate != null) {
            int intrinsicHeight = (120 - mutate.getIntrinsicHeight()) / 2;
            mutate.setBounds(13, intrinsicHeight, mutate.getIntrinsicWidth() + 13, mutate.getIntrinsicHeight() + intrinsicHeight);
            mutate.draw(canvas);
        }
        if (drawable2 != null) {
            int width2 = (createBitmap.getWidth() - drawable2.getIntrinsicWidth()) - 10;
            int intrinsicHeight2 = (120 - drawable2.getIntrinsicHeight()) / 2;
            drawable2.setBounds(width2, intrinsicHeight2, drawable2.getIntrinsicWidth() + width2, drawable2.getIntrinsicHeight() + intrinsicHeight2);
            drawable2.draw(canvas);
        }
        messageDrawableArr[1].setBounds(161, 216, createBitmap.getWidth() - 20, 308);
        messageDrawableArr[1].setTop(0, 560, 522, false, false);
        messageDrawableArr[1].draw(canvas);
        messageDrawableArr[1].setBounds(161, 430, createBitmap.getWidth() - 20, 522);
        messageDrawableArr[1].setTop(430, 560, 522, false, false);
        messageDrawableArr[1].draw(canvas);
        messageDrawableArr[0].setBounds(20, 323, 399, 415);
        messageDrawableArr[0].setTop(323, 560, 522, false, false);
        messageDrawableArr[0].draw(canvas);
        paint3.setColor(previewColor3);
        canvas.drawRect(0.0f, createBitmap.getHeight() - 120, createBitmap.getWidth(), createBitmap.getHeight(), paint3);
        if (mutate3 != null) {
            int height = (createBitmap.getHeight() - 120) + ((120 - mutate3.getIntrinsicHeight()) / 2);
            mutate3.setBounds(22, height, mutate3.getIntrinsicWidth() + 22, mutate3.getIntrinsicHeight() + height);
            mutate3.draw(canvas);
        }
        if (mutate4 != null) {
            int width3 = (createBitmap.getWidth() - mutate4.getIntrinsicWidth()) - 22;
            int height2 = (createBitmap.getHeight() - 120) + ((120 - mutate4.getIntrinsicHeight()) / 2);
            mutate4.setBounds(width3, height2, mutate4.getIntrinsicWidth() + width3, mutate4.getIntrinsicHeight() + height2);
            mutate4.draw(canvas);
        }
        return createBitmap;
    }
}
