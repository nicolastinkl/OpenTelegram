package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.Bulletin;
import org.telegram.ui.Components.LinkSpanDrawable;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PremiumPreviewFragment;

/* loaded from: classes4.dex */
public final class BulletinFactory {
    private final FrameLayout containerLayout;
    private final BaseFragment fragment;
    private final Theme.ResourcesProvider resourcesProvider;

    public static BulletinFactory of(BaseFragment baseFragment) {
        return new BulletinFactory(baseFragment);
    }

    public static BulletinFactory of(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider) {
        return new BulletinFactory(frameLayout, resourcesProvider);
    }

    public static boolean canShowBulletin(BaseFragment baseFragment) {
        return (baseFragment == null || baseFragment.getParentActivity() == null || baseFragment.getLayoutContainer() == null) ? false : true;
    }

    public static BulletinFactory global() {
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null) {
            return null;
        }
        return of(lastFragment);
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PHOTO' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class FileType {
        private static final /* synthetic */ FileType[] $VALUES;
        public static final FileType AUDIO;
        public static final FileType AUDIOS;
        public static final FileType GIF;
        public static final FileType GIF_TO_DOWNLOADS;
        public static final FileType MEDIA;
        public static final FileType PHOTO;
        public static final FileType PHOTOS;
        public static final FileType PHOTO_TO_DOWNLOADS;
        public static final FileType UNKNOWN;
        public static final FileType UNKNOWNS;
        public static final FileType VIDEO;
        public static final FileType VIDEOS;
        public static final FileType VIDEO_TO_DOWNLOADS;
        private final Icon icon;
        private final String localeKey;
        private final int localeRes;
        private final boolean plural;

        private static /* synthetic */ FileType[] $values() {
            return new FileType[]{PHOTO, PHOTOS, VIDEO, VIDEOS, MEDIA, PHOTO_TO_DOWNLOADS, VIDEO_TO_DOWNLOADS, GIF, GIF_TO_DOWNLOADS, AUDIO, AUDIOS, UNKNOWN, UNKNOWNS};
        }

        public static FileType valueOf(String str) {
            return (FileType) Enum.valueOf(FileType.class, str);
        }

        public static FileType[] values() {
            return (FileType[]) $VALUES.clone();
        }

        static {
            int i = R.string.PhotoSavedHint;
            Icon icon = Icon.SAVED_TO_GALLERY;
            PHOTO = new FileType("PHOTO", 0, "PhotoSavedHint", i, icon);
            PHOTOS = new FileType("PHOTOS", 1, "PhotosSavedHint", icon);
            VIDEO = new FileType("VIDEO", 2, "VideoSavedHint", R.string.VideoSavedHint, icon);
            VIDEOS = new FileType("VIDEOS", 3, "VideosSavedHint", icon);
            MEDIA = new FileType("MEDIA", 4, "MediaSavedHint", icon);
            int i2 = R.string.PhotoSavedToDownloadsHint;
            Icon icon2 = Icon.SAVED_TO_DOWNLOADS;
            PHOTO_TO_DOWNLOADS = new FileType("PHOTO_TO_DOWNLOADS", 5, "PhotoSavedToDownloadsHint", i2, icon2);
            VIDEO_TO_DOWNLOADS = new FileType("VIDEO_TO_DOWNLOADS", 6, "VideoSavedToDownloadsHint", R.string.VideoSavedToDownloadsHint, icon2);
            GIF = new FileType("GIF", 7, "GifSavedHint", R.string.GifSavedHint, Icon.SAVED_TO_GIFS);
            GIF_TO_DOWNLOADS = new FileType("GIF_TO_DOWNLOADS", 8, "GifSavedToDownloadsHint", R.string.GifSavedToDownloadsHint, icon2);
            int i3 = R.string.AudioSavedHint;
            Icon icon3 = Icon.SAVED_TO_MUSIC;
            AUDIO = new FileType("AUDIO", 9, "AudioSavedHint", i3, icon3);
            AUDIOS = new FileType("AUDIOS", 10, "AudiosSavedHint", icon3);
            UNKNOWN = new FileType("UNKNOWN", 11, "FileSavedHint", R.string.FileSavedHint, icon2);
            UNKNOWNS = new FileType("UNKNOWNS", 12, "FilesSavedHint", icon2);
            $VALUES = $values();
        }

        private FileType(String str, int i, String str2, int i2, Icon icon) {
            this.localeKey = str2;
            this.localeRes = i2;
            this.icon = icon;
            this.plural = false;
        }

        private FileType(String str, int i, String str2, Icon icon) {
            this.localeKey = str2;
            this.icon = icon;
            this.localeRes = 0;
            this.plural = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getText(int i) {
            if (this.plural) {
                return LocaleController.formatPluralString(this.localeKey, i, new Object[0]);
            }
            return LocaleController.getString(this.localeKey, this.localeRes);
        }

        private enum Icon {
            SAVED_TO_DOWNLOADS(R.raw.ic_download, 2, "Box", "Arrow"),
            SAVED_TO_GALLERY(R.raw.ic_save_to_gallery, 0, "Box", "Arrow", "Mask", "Arrow 2", "Splash"),
            SAVED_TO_MUSIC(R.raw.ic_save_to_music, 2, "Box", "Arrow"),
            SAVED_TO_GIFS(R.raw.ic_save_to_gifs, 0, "gif");

            private final String[] layers;
            private final int paddingBottom;
            private final int resId;

            Icon(int i, int i2, String... strArr) {
                this.resId = i;
                this.paddingBottom = i2;
                this.layers = strArr;
            }
        }
    }

    private BulletinFactory(BaseFragment baseFragment) {
        this.fragment = baseFragment;
        this.containerLayout = null;
        this.resourcesProvider = baseFragment != null ? baseFragment.getResourceProvider() : null;
    }

    private BulletinFactory(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider) {
        this.containerLayout = frameLayout;
        this.fragment = null;
        this.resourcesProvider = resourcesProvider;
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, int i2) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        if (charSequence != null) {
            String charSequence2 = charSequence.toString();
            SpannableStringBuilder spannableStringBuilder = charSequence instanceof SpannableStringBuilder ? (SpannableStringBuilder) charSequence : new SpannableStringBuilder(charSequence);
            int i3 = 0;
            for (int indexOf = charSequence2.indexOf(10); indexOf >= 0 && indexOf < charSequence.length(); indexOf = charSequence2.indexOf(10, indexOf + 1)) {
                if (i3 >= i2) {
                    spannableStringBuilder.replace(indexOf, indexOf + 1, (CharSequence) " ");
                }
                i3++;
            }
            charSequence = spannableStringBuilder;
        }
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(i2);
        return create(lottieLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.setAnimation(i, 36, 36, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, charSequence.length() + charSequence2.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        return createSimpleBulletin(i, charSequence, charSequence2, charSequence.length() < 20 ? 1500 : 2750, runnable);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Runnable runnable) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.setAnimation(i, 36, 36, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        twoLineLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence3).setUndoAction(runnable));
        return create(twoLineLottieLayout, 5000);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, int i2, Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setTextDirection(5);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        return create(lottieLayout, i2);
    }

    public Bulletin createSimpleBulletin(Drawable drawable, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.imageView.setImageDrawable(drawable);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 2750);
    }

    public Bulletin createSimpleBulletin(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, String str, Runnable runnable) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.imageView.setImageDrawable(drawable);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        twoLineLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(str).setUndoAction(runnable));
        return create(twoLineLottieLayout, 2750);
    }

    public Bulletin createUndoBulletin(CharSequence charSequence, Runnable runnable, Runnable runnable2) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        lottieLayout.setTimer();
        lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(LocaleController.getString("Undo", R.string.Undo)).setUndoAction(runnable).setDelayedAction(runnable2));
        return create(lottieLayout, 5000);
    }

    public Bulletin createUsersBulletin(List<TLRPC$User> list, CharSequence charSequence) {
        return createUsersBulletin(list, charSequence, null);
    }

    public Bulletin createUsersBulletin(List<TLRPC$User> list, CharSequence charSequence, CharSequence charSequence2) {
        int i;
        Bulletin.UsersLayout usersLayout = new Bulletin.UsersLayout(getContext(), charSequence2 != null, this.resourcesProvider);
        if (list != null) {
            i = 0;
            for (int i2 = 0; i2 < list.size() && i < 3; i2++) {
                TLRPC$User tLRPC$User = list.get(i2);
                if (tLRPC$User != null) {
                    i++;
                    usersLayout.avatarsImageView.setCount(i);
                    usersLayout.avatarsImageView.setObject(i - 1, UserConfig.selectedAccount, tLRPC$User);
                }
            }
            if (list.size() == 1) {
                usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.dp(4.0f));
                usersLayout.avatarsImageView.setScaleX(1.2f);
                usersLayout.avatarsImageView.setScaleY(1.2f);
            } else {
                usersLayout.avatarsImageView.setScaleX(1.0f);
                usersLayout.avatarsImageView.setScaleY(1.0f);
            }
        } else {
            i = 0;
        }
        usersLayout.avatarsImageView.commitTransition(false);
        if (charSequence2 != null) {
            usersLayout.textView.setSingleLine(true);
            usersLayout.textView.setMaxLines(1);
            usersLayout.textView.setText(charSequence);
            usersLayout.subtitleView.setText(charSequence2);
            usersLayout.subtitleView.setSingleLine(true);
            usersLayout.subtitleView.setMaxLines(1);
            if (usersLayout.linearLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int dp = AndroidUtilities.dp(70 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).rightMargin = dp;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).leftMargin = dp;
                }
            }
        } else {
            usersLayout.textView.setSingleLine(false);
            usersLayout.textView.setMaxLines(2);
            usersLayout.textView.setText(charSequence);
            if (usersLayout.textView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int dp2 = AndroidUtilities.dp(70 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).rightMargin = dp2;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).leftMargin = dp2;
                }
            }
        }
        return create(usersLayout, 5000);
    }

    public Bulletin createChatsBulletin(List<TLObject> list, CharSequence charSequence, CharSequence charSequence2) {
        int i;
        Bulletin.UsersLayout usersLayout = new Bulletin.UsersLayout(getContext(), charSequence2 != null, this.resourcesProvider);
        if (list != null) {
            i = 0;
            for (int i2 = 0; i2 < list.size() && i < 3; i2++) {
                TLObject tLObject = list.get(i2);
                if (tLObject != null) {
                    i++;
                    usersLayout.avatarsImageView.setCount(i);
                    usersLayout.avatarsImageView.setObject(i - 1, UserConfig.selectedAccount, tLObject);
                }
            }
            if (list.size() == 1) {
                usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.dp(4.0f));
                usersLayout.avatarsImageView.setScaleX(1.2f);
                usersLayout.avatarsImageView.setScaleY(1.2f);
            } else {
                usersLayout.avatarsImageView.setScaleX(1.0f);
                usersLayout.avatarsImageView.setScaleY(1.0f);
            }
        } else {
            i = 0;
        }
        usersLayout.avatarsImageView.commitTransition(false);
        if (charSequence2 != null) {
            usersLayout.textView.setSingleLine(true);
            usersLayout.textView.setMaxLines(1);
            usersLayout.textView.setText(charSequence);
            usersLayout.subtitleView.setText(charSequence2);
            usersLayout.subtitleView.setSingleLine(true);
            usersLayout.subtitleView.setMaxLines(1);
            if (usersLayout.linearLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int dp = AndroidUtilities.dp(74 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).rightMargin = dp;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).leftMargin = dp;
                }
            }
        } else {
            usersLayout.textView.setSingleLine(false);
            usersLayout.textView.setMaxLines(2);
            usersLayout.textView.setText(charSequence);
            if (usersLayout.textView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int dp2 = AndroidUtilities.dp(74 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).rightMargin = dp2;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).leftMargin = dp2;
                }
            }
        }
        if (LocaleController.isRTL) {
            usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.dp(32 - ((i - 1) * 12)));
        }
        return create(usersLayout, 5000);
    }

    public Bulletin createUsersAddedBulletin(ArrayList<TLRPC$User> arrayList, TLRPC$Chat tLRPC$Chat) {
        SpannableStringBuilder spannableStringBuilder;
        if (arrayList == null || arrayList.size() == 0) {
            spannableStringBuilder = null;
        } else if (arrayList.size() == 1) {
            if (ChatObject.isChannelAndNotMegaGroup(tLRPC$Chat)) {
                spannableStringBuilder = AndroidUtilities.replaceTags(LocaleController.formatString("HasBeenAddedToChannel", R.string.HasBeenAddedToChannel, "**" + UserObject.getFirstName(arrayList.get(0)) + "**"));
            } else {
                spannableStringBuilder = AndroidUtilities.replaceTags(LocaleController.formatString("HasBeenAddedToGroup", R.string.HasBeenAddedToGroup, "**" + UserObject.getFirstName(arrayList.get(0)) + "**"));
            }
        } else if (ChatObject.isChannelAndNotMegaGroup(tLRPC$Chat)) {
            spannableStringBuilder = AndroidUtilities.replaceTags(LocaleController.formatPluralString("AddedMembersToChannel", arrayList.size(), new Object[0]));
        } else {
            spannableStringBuilder = AndroidUtilities.replaceTags(LocaleController.formatPluralString("AddedSubscribersToChannel", arrayList.size(), new Object[0]));
        }
        return createUsersBulletin(arrayList, spannableStringBuilder);
    }

    public Bulletin createEmojiBulletin(TLRPC$Document tLRPC$Document, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(tLRPC$Document)) {
            lottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        lottieLayout.setAnimation(tLRPC$Document, 36, 36, new String[0]);
        if (lottieLayout.imageView.getImageReceiver() != null) {
            lottieLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.dp(4.0f));
        }
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        return create(lottieLayout, 2750);
    }

    public Bulletin createEmojiLoadingBulletin(TLRPC$Document tLRPC$Document, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        Bulletin.LoadingLottieLayout loadingLottieLayout = new Bulletin.LoadingLottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(tLRPC$Document)) {
            loadingLottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        loadingLottieLayout.setAnimation(tLRPC$Document, 36, 36, new String[0]);
        loadingLottieLayout.textView.setTextSize(1, 14.0f);
        loadingLottieLayout.textView.setSingleLine(false);
        loadingLottieLayout.textView.setMaxLines(3);
        loadingLottieLayout.textLoadingView.setText(charSequence);
        loadingLottieLayout.textLoadingView.setTextSize(1, 14.0f);
        loadingLottieLayout.textLoadingView.setSingleLine(false);
        loadingLottieLayout.textLoadingView.setMaxLines(3);
        loadingLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        return create(loadingLottieLayout, 2750);
    }

    public Bulletin createContainsEmojiBulletin(TLRPC$Document tLRPC$Document, final boolean z, final Utilities.Callback<TLRPC$InputStickerSet> callback) {
        SpannableStringBuilder spannableStringBuilder;
        LoadingSpan loadingSpan;
        TLRPC$StickerSet tLRPC$StickerSet;
        final TLRPC$InputStickerSet inputStickerSet = MessageObject.getInputStickerSet(tLRPC$Document);
        if (inputStickerSet == null) {
            return null;
        }
        TLRPC$TL_messages_stickerSet stickerSet = MediaDataController.getInstance(UserConfig.selectedAccount).getStickerSet(inputStickerSet, true);
        if (stickerSet == null || (tLRPC$StickerSet = stickerSet.set) == null) {
            if (z) {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", R.string.TopicContainsEmojiPackSingle, "<{LOADING}>")));
            } else {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", R.string.MessageContainsEmojiPackSingle, "<{LOADING}>")));
            }
            int indexOf = spannableStringBuilder.toString().indexOf("<{LOADING}>");
            if (indexOf >= 0) {
                loadingSpan = new LoadingSpan(null, AndroidUtilities.dp(100.0f));
                spannableStringBuilder.setSpan(loadingSpan, indexOf, indexOf + 11, 33);
                int i = Theme.key_undo_infoColor;
                loadingSpan.setColors(ColorUtils.setAlphaComponent(Theme.getColor(i, this.resourcesProvider), 32), ColorUtils.setAlphaComponent(Theme.getColor(i, this.resourcesProvider), 72));
            } else {
                loadingSpan = null;
            }
            final long currentTimeMillis = System.currentTimeMillis();
            final Bulletin show = createEmojiLoadingBulletin(tLRPC$Document, spannableStringBuilder, LocaleController.getString("ViewAction", R.string.ViewAction), new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Utilities.Callback.this.run(inputStickerSet);
                }
            }).show();
            if (loadingSpan != null && (show.getLayout() instanceof Bulletin.LoadingLottieLayout)) {
                loadingSpan.setView(((Bulletin.LoadingLottieLayout) show.getLayout()).textLoadingView);
            }
            MediaDataController.getInstance(UserConfig.selectedAccount).getStickerSet(inputStickerSet, null, false, new Utilities.Callback() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda5
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    BulletinFactory.lambda$createContainsEmojiBulletin$2(z, show, currentTimeMillis, (TLRPC$TL_messages_stickerSet) obj);
                }
            });
            return show;
        }
        return createEmojiBulletin(tLRPC$Document, z ? AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", R.string.TopicContainsEmojiPackSingle, tLRPC$StickerSet.title)) : AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", R.string.MessageContainsEmojiPackSingle, tLRPC$StickerSet.title)), LocaleController.getString("ViewAction", R.string.ViewAction), new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Utilities.Callback.this.run(inputStickerSet);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createContainsEmojiBulletin$2(boolean z, final Bulletin bulletin, long j, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
        final CharSequence string;
        TLRPC$StickerSet tLRPC$StickerSet;
        if (tLRPC$TL_messages_stickerSet == null || (tLRPC$StickerSet = tLRPC$TL_messages_stickerSet.set) == null) {
            string = LocaleController.getString("AddEmojiNotFound", R.string.AddEmojiNotFound);
        } else {
            string = z ? AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", R.string.TopicContainsEmojiPackSingle, tLRPC$StickerSet.title)) : AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", R.string.MessageContainsEmojiPackSingle, tLRPC$StickerSet.title));
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                Bulletin.this.onLoaded(string);
            }
        }, Math.max(1L, 750 - (System.currentTimeMillis() - j)));
    }

    public Bulletin createDownloadBulletin(FileType fileType) {
        return createDownloadBulletin(fileType, this.resourcesProvider);
    }

    public Bulletin createDownloadBulletin(FileType fileType, Theme.ResourcesProvider resourcesProvider) {
        return createDownloadBulletin(fileType, 1, resourcesProvider);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, Theme.ResourcesProvider resourcesProvider) {
        return createDownloadBulletin(fileType, i, 0, 0, resourcesProvider);
    }

    public Bulletin createReportSent(Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(R.raw.chats_infotip, new String[0]);
        lottieLayout.textView.setText(LocaleController.getString("ReportChatSent", R.string.ReportChatSent));
        return create(lottieLayout, 1500);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, int i2, int i3) {
        return createDownloadBulletin(fileType, i, i2, i3, null);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, int i2, int i3, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout;
        if (i2 != 0 && i3 != 0) {
            lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider, i2, i3);
        } else {
            lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        }
        lottieLayout.setAnimation(fileType.icon.resId, fileType.icon.layers);
        lottieLayout.textView.setText(fileType.getText(i));
        if (fileType.icon.paddingBottom != 0) {
            lottieLayout.setIconPaddingBottom(fileType.icon.paddingBottom);
        }
        return create(lottieLayout, 1500);
    }

    public Bulletin createErrorBulletin(CharSequence charSequence) {
        return createErrorBulletin(charSequence, null);
    }

    public Bulletin createErrorBulletin(CharSequence charSequence, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(R.raw.chats_infotip, new String[0]);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 1500);
    }

    public Bulletin createSuccessBulletin(CharSequence charSequence) {
        return createSuccessBulletin(charSequence, null);
    }

    public Bulletin createSuccessBulletin(CharSequence charSequence, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(R.raw.contact_check, new String[0]);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 1500);
    }

    public Bulletin createCaptionLimitBulletin(int i, final Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(R.raw.caption_limit, new String[0]);
        String formatPluralString = LocaleController.formatPluralString("ChannelCaptionLimitPremiumPromo", i, new Object[0]);
        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(AndroidUtilities.replaceTags(formatPluralString));
        int indexOf = formatPluralString.indexOf(42);
        int i2 = indexOf + 1;
        int indexOf2 = formatPluralString.indexOf(42, i2);
        valueOf.replace(indexOf, indexOf2 + 1, (CharSequence) formatPluralString.substring(i2, indexOf2));
        valueOf.setSpan(new ClickableSpan(this) { // from class: org.telegram.ui.Components.BulletinFactory.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                runnable.run();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
            }
        }, indexOf, indexOf2 - 1, 33);
        lottieLayout.textView.setText(valueOf);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        return create(lottieLayout, 5000);
    }

    public Bulletin createRestrictVoiceMessagesPremiumBulletin() {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(R.raw.voip_muted, new String[0]);
        String string = LocaleController.getString(R.string.PrivacyVoiceMessagesPremiumOnly);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        int indexOf = string.indexOf(42);
        int lastIndexOf = string.lastIndexOf(42);
        if (indexOf >= 0) {
            spannableStringBuilder.replace(indexOf, lastIndexOf + 1, (CharSequence) string.substring(indexOf + 1, lastIndexOf));
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.BulletinFactory.2
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    BulletinFactory.this.fragment.presentFragment(new PremiumPreviewFragment("settings"));
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setUnderlineText(false);
                }
            }, indexOf, lastIndexOf - 1, 33);
        }
        lottieLayout.textView.setText(spannableStringBuilder);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 2750);
    }

    public Bulletin createErrorBulletinSubtitle(CharSequence charSequence, CharSequence charSequence2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), resourcesProvider);
        twoLineLottieLayout.setAnimation(R.raw.chats_infotip, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin() {
        return createCopyLinkBulletin(false, this.resourcesProvider);
    }

    public Bulletin createCopyBulletin(String str) {
        return createCopyBulletin(str, null);
    }

    public Bulletin createCopyBulletin(String str, Theme.ResourcesProvider resourcesProvider) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(R.raw.copy, 36, 36, "NULL ROTATION", "Back", "Front");
        lottieLayout.textView.setText(str);
        return create(lottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin(boolean z, Theme.ResourcesProvider resourcesProvider) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        if (z) {
            Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), resourcesProvider);
            twoLineLottieLayout.setAnimation(R.raw.voip_invite, 36, 36, "Wibe", "Circle");
            twoLineLottieLayout.titleTextView.setText(LocaleController.getString("LinkCopied", R.string.LinkCopied));
            twoLineLottieLayout.subtitleTextView.setText(LocaleController.getString("LinkCopiedPrivateInfo", R.string.LinkCopiedPrivateInfo));
            return create(twoLineLottieLayout, 2750);
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(R.raw.voip_invite, 36, 36, "Wibe", "Circle");
        lottieLayout.textView.setText(LocaleController.getString("LinkCopied", R.string.LinkCopied));
        return create(lottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin(String str, Theme.ResourcesProvider resourcesProvider) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(R.raw.voip_invite, 36, 36, "Wibe", "Circle");
        lottieLayout.textView.setText(str);
        return create(lottieLayout, 1500);
    }

    private Bulletin create(Bulletin.Layout layout, int i) {
        BaseFragment baseFragment = this.fragment;
        if (baseFragment != null) {
            return Bulletin.make(baseFragment, layout, i);
        }
        return Bulletin.make(this.containerLayout, layout, i);
    }

    private Context getContext() {
        Context context;
        BaseFragment baseFragment = this.fragment;
        if (baseFragment != null) {
            context = baseFragment.getParentActivity();
            if (context == null && this.fragment.getLayoutContainer() != null) {
                context = this.fragment.getLayoutContainer().getContext();
            }
        } else {
            FrameLayout frameLayout = this.containerLayout;
            context = frameLayout != null ? frameLayout.getContext() : null;
        }
        return context == null ? ApplicationLoader.applicationContext : context;
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, int i) {
        return createMuteBulletin(baseFragment, i, 0, (Theme.ResourcesProvider) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x008a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.telegram.ui.Components.Bulletin createMuteBulletin(org.telegram.ui.ActionBar.BaseFragment r6, int r7, int r8, org.telegram.ui.ActionBar.Theme.ResourcesProvider r9) {
        /*
            org.telegram.ui.Components.Bulletin$LottieLayout r0 = new org.telegram.ui.Components.Bulletin$LottieLayout
            android.app.Activity r1 = r6.getParentActivity()
            r0.<init>(r1, r9)
            java.lang.String r9 = "Hours"
            java.lang.String r1 = "NotificationsMutedForHint"
            r2 = 0
            r3 = 1
            if (r7 == 0) goto L6e
            if (r7 == r3) goto L5b
            r9 = 2
            if (r7 == r9) goto L48
            r9 = 3
            if (r7 == r9) goto L3f
            r9 = 4
            if (r7 == r9) goto L35
            r9 = 5
            if (r7 != r9) goto L2f
            int r7 = org.telegram.messenger.R.string.NotificationsMutedForHint
            java.lang.Object[] r9 = new java.lang.Object[r3]
            java.lang.String r8 = org.telegram.messenger.LocaleController.formatTTLString(r8)
            r9[r2] = r8
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatString(r1, r7, r9)
            r8 = 1
            goto L80
        L2f:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            r6.<init>()
            throw r6
        L35:
            int r7 = org.telegram.messenger.R.string.NotificationsUnmutedHint
            java.lang.String r8 = "NotificationsUnmutedHint"
            java.lang.String r7 = org.telegram.messenger.LocaleController.getString(r8, r7)
            r8 = 0
            goto L7f
        L3f:
            int r7 = org.telegram.messenger.R.string.NotificationsMutedHint
            java.lang.String r8 = "NotificationsMutedHint"
            java.lang.String r7 = org.telegram.messenger.LocaleController.getString(r8, r7)
            goto L7e
        L48:
            int r7 = org.telegram.messenger.R.string.NotificationsMutedForHint
            java.lang.Object[] r8 = new java.lang.Object[r3]
            java.lang.Object[] r4 = new java.lang.Object[r2]
            java.lang.String r5 = "Days"
            java.lang.String r9 = org.telegram.messenger.LocaleController.formatPluralString(r5, r9, r4)
            r8[r2] = r9
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatString(r1, r7, r8)
            goto L7e
        L5b:
            int r7 = org.telegram.messenger.R.string.NotificationsMutedForHint
            java.lang.Object[] r8 = new java.lang.Object[r3]
            r4 = 8
            java.lang.Object[] r5 = new java.lang.Object[r2]
            java.lang.String r9 = org.telegram.messenger.LocaleController.formatPluralString(r9, r4, r5)
            r8[r2] = r9
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatString(r1, r7, r8)
            goto L7e
        L6e:
            int r7 = org.telegram.messenger.R.string.NotificationsMutedForHint
            java.lang.Object[] r8 = new java.lang.Object[r3]
            java.lang.Object[] r4 = new java.lang.Object[r2]
            java.lang.String r9 = org.telegram.messenger.LocaleController.formatPluralString(r9, r3, r4)
            r8[r2] = r9
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatString(r1, r7, r8)
        L7e:
            r8 = 1
        L7f:
            r3 = 0
        L80:
            if (r3 == 0) goto L8a
            int r8 = org.telegram.messenger.R.raw.mute_for
            java.lang.String[] r9 = new java.lang.String[r2]
            r0.setAnimation(r8, r9)
            goto Lb1
        L8a:
            if (r8 == 0) goto La0
            int r8 = org.telegram.messenger.R.raw.ic_mute
            java.lang.String r9 = "Body Main"
            java.lang.String r1 = "Body Top"
            java.lang.String r2 = "Line"
            java.lang.String r3 = "Curve Big"
            java.lang.String r4 = "Curve Small"
            java.lang.String[] r9 = new java.lang.String[]{r9, r1, r2, r3, r4}
            r0.setAnimation(r8, r9)
            goto Lb1
        La0:
            int r8 = org.telegram.messenger.R.raw.ic_unmute
            java.lang.String r9 = "BODY"
            java.lang.String r1 = "Wibe Big"
            java.lang.String r2 = "Wibe Big 3"
            java.lang.String r3 = "Wibe Small"
            java.lang.String[] r9 = new java.lang.String[]{r9, r1, r2, r3}
            r0.setAnimation(r8, r9)
        Lb1:
            org.telegram.ui.Components.LinkSpanDrawable$LinksTextView r8 = r0.textView
            r8.setText(r7)
            r7 = 1500(0x5dc, float:2.102E-42)
            org.telegram.ui.Components.Bulletin r6 = org.telegram.ui.Components.Bulletin.make(r6, r0, r7)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.BulletinFactory.createMuteBulletin(org.telegram.ui.ActionBar.BaseFragment, int, int, org.telegram.ui.ActionBar.Theme$ResourcesProvider):org.telegram.ui.Components.Bulletin");
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, boolean z, int i, Theme.ResourcesProvider resourcesProvider) {
        String formatPluralString;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        LinkSpanDrawable.LinksTextView linksTextView = lottieLayout.textView;
        if (z) {
            formatPluralString = LocaleController.formatPluralString("NotificationsMutedHintChats", i, new Object[0]);
        } else {
            formatPluralString = LocaleController.formatPluralString("NotificationsUnmutedHintChats", i, new Object[0]);
        }
        linksTextView.setText(formatPluralString);
        if (z) {
            lottieLayout.setAnimation(R.raw.ic_mute, "Body Main", "Body Top", "Line", "Curve Big", "Curve Small");
        } else {
            lottieLayout.setAnimation(R.raw.ic_unmute, "BODY", "Wibe Big", "Wibe Big 3", "Wibe Small");
        }
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
        return createMuteBulletin(baseFragment, z ? 3 : 4, 0, resourcesProvider);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Bulletin createUnpinAllMessagesBulletin(BaseFragment baseFragment, int i, boolean z, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout;
        if (baseFragment.getParentActivity() == null) {
            if (runnable2 == null) {
                return null;
            }
            runnable2.run();
            return null;
        }
        if (z) {
            Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(baseFragment.getParentActivity(), resourcesProvider);
            twoLineLottieLayout.setAnimation(R.raw.ic_unpin, 28, 28, "Pin", "Line");
            twoLineLottieLayout.titleTextView.setText(LocaleController.getString("PinnedMessagesHidden", R.string.PinnedMessagesHidden));
            twoLineLottieLayout.subtitleTextView.setText(LocaleController.getString("PinnedMessagesHiddenInfo", R.string.PinnedMessagesHiddenInfo));
            lottieLayout = twoLineLottieLayout;
        } else {
            Bulletin.LottieLayout lottieLayout2 = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
            lottieLayout2.setAnimation(R.raw.ic_unpin, 28, 28, "Pin", "Line");
            lottieLayout2.textView.setText(LocaleController.formatPluralString("MessagesUnpinned", i, new Object[0]));
            lottieLayout = lottieLayout2;
        }
        lottieLayout.setButton(new Bulletin.UndoButton(baseFragment.getParentActivity(), true, resourcesProvider).setUndoAction(runnable).setDelayedAction(runnable2));
        return Bulletin.make(baseFragment, lottieLayout, 5000);
    }

    public static Bulletin createSaveToGalleryBulletin(BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
        return of(baseFragment).createDownloadBulletin(z ? FileType.VIDEO : FileType.PHOTO, resourcesProvider);
    }

    public static Bulletin createSaveToGalleryBulletin(FrameLayout frameLayout, boolean z, int i, int i2) {
        return of(frameLayout, null).createDownloadBulletin(z ? FileType.VIDEO : FileType.PHOTO, 1, i, i2);
    }

    public static Bulletin createSaveToGalleryBulletin(FrameLayout frameLayout, int i, boolean z, int i2, int i3) {
        return of(frameLayout, null).createDownloadBulletin(z ? i > 1 ? FileType.VIDEOS : FileType.VIDEO : i > 1 ? FileType.PHOTOS : FileType.PHOTO, i, i2, i3);
    }

    public static Bulletin createPromoteToAdminBulletin(BaseFragment baseFragment, String str) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(R.raw.ic_admin, "Shield");
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserSetAsAdminHint", R.string.UserSetAsAdminHint, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createAddedAsAdminBulletin(BaseFragment baseFragment, String str) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(R.raw.ic_admin, "Shield");
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserAddedAsAdminHint", R.string.UserAddedAsAdminHint, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.telegram.ui.Components.Bulletin createInviteSentBulletin(android.content.Context r3, android.widget.FrameLayout r4, int r5, long r6, int r8, int r9, int r10) {
        /*
            org.telegram.ui.Components.Bulletin$LottieLayout r8 = new org.telegram.ui.Components.Bulletin$LottieLayout
            r0 = 0
            r8.<init>(r3, r0, r9, r10)
            r3 = 300(0x12c, float:4.2E-43)
            r9 = 1
            r10 = 30
            r0 = 0
            if (r5 > r9) goto L81
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.UserConfig r5 = org.telegram.messenger.UserConfig.getInstance(r5)
            long r1 = r5.clientUserId
            int r5 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r5 != 0) goto L2f
            int r3 = org.telegram.messenger.R.string.InvLinkToSavedMessages
            java.lang.String r5 = "InvLinkToSavedMessages"
            java.lang.String r3 = org.telegram.messenger.LocaleController.getString(r5, r3)
            android.text.SpannableStringBuilder r3 = org.telegram.messenger.AndroidUtilities.replaceTags(r3)
            int r5 = org.telegram.messenger.R.raw.saved_messages
            java.lang.String[] r6 = new java.lang.String[r0]
            r8.setAnimation(r5, r10, r10, r6)
            r5 = -1
            goto La3
        L2f:
            boolean r5 = org.telegram.messenger.DialogObject.isChatDialog(r6)
            if (r5 == 0) goto L57
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            long r6 = -r6
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$Chat r5 = r5.getChat(r6)
            int r6 = org.telegram.messenger.R.string.InvLinkToGroup
            java.lang.Object[] r7 = new java.lang.Object[r9]
            java.lang.String r5 = r5.title
            r7[r0] = r5
            java.lang.String r5 = "InvLinkToGroup"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
            goto L79
        L57:
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$User r5 = r5.getUser(r6)
            int r6 = org.telegram.messenger.R.string.InvLinkToUser
            java.lang.Object[] r7 = new java.lang.Object[r9]
            java.lang.String r5 = org.telegram.messenger.UserObject.getFirstName(r5)
            r7[r0] = r5
            java.lang.String r5 = "InvLinkToUser"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
        L79:
            int r6 = org.telegram.messenger.R.raw.forward
            java.lang.String[] r7 = new java.lang.String[r0]
            r8.setAnimation(r6, r10, r10, r7)
            goto La0
        L81:
            int r6 = org.telegram.messenger.R.string.InvLinkToChats
            java.lang.Object[] r7 = new java.lang.Object[r9]
            java.lang.Object[] r9 = new java.lang.Object[r0]
            java.lang.String r1 = "Chats"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatPluralString(r1, r5, r9)
            r7[r0] = r5
            java.lang.String r5 = "InvLinkToChats"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
            int r6 = org.telegram.messenger.R.raw.forward
            java.lang.String[] r7 = new java.lang.String[r0]
            r8.setAnimation(r6, r10, r10, r7)
        La0:
            r3 = r5
            r5 = 300(0x12c, float:4.2E-43)
        La3:
            org.telegram.ui.Components.LinkSpanDrawable$LinksTextView r6 = r8.textView
            r6.setText(r3)
            if (r5 <= 0) goto Lb3
            org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda3 r3 = new org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda3
            r3.<init>()
            long r5 = (long) r5
            r8.postDelayed(r3, r5)
        Lb3:
            r3 = 1500(0x5dc, float:2.102E-42)
            org.telegram.ui.Components.Bulletin r3 = org.telegram.ui.Components.Bulletin.make(r4, r8, r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.BulletinFactory.createInviteSentBulletin(android.content.Context, android.widget.FrameLayout, int, long, int, int, int):org.telegram.ui.Components.Bulletin");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.telegram.ui.Components.Bulletin createForwardedBulletin(android.content.Context r4, android.widget.FrameLayout r5, int r6, long r7, int r9, int r10, int r11) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.BulletinFactory.createForwardedBulletin(android.content.Context, android.widget.FrameLayout, int, long, int, int, int):org.telegram.ui.Components.Bulletin");
    }

    public static Bulletin createRemoveFromChatBulletin(BaseFragment baseFragment, TLRPC$User tLRPC$User, String str) {
        String str2;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(R.raw.ic_ban, "Hand");
        if (tLRPC$User.deleted) {
            str2 = LocaleController.formatString("HiddenName", R.string.HiddenName, new Object[0]);
        } else {
            str2 = tLRPC$User.first_name;
        }
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserRemovedFromChatHint", R.string.UserRemovedFromChatHint, str2, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createBanBulletin(BaseFragment baseFragment, boolean z) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        if (z) {
            lottieLayout.setAnimation(R.raw.ic_ban, "Hand");
            string = LocaleController.getString("UserBlocked", R.string.UserBlocked);
        } else {
            lottieLayout.setAnimation(R.raw.ic_unban, "Main", "Finger 1", "Finger 2", "Finger 3", "Finger 4");
            string = LocaleController.getString("UserUnblocked", R.string.UserUnblocked);
        }
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(string));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createCopyLinkBulletin(BaseFragment baseFragment) {
        return of(baseFragment).createCopyLinkBulletin();
    }

    public static Bulletin createCopyLinkBulletin(FrameLayout frameLayout) {
        return of(frameLayout, null).createCopyLinkBulletin();
    }

    public static Bulletin createPinMessageBulletin(BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        return createPinMessageBulletin(baseFragment, true, null, null, resourcesProvider);
    }

    public static Bulletin createUnpinMessageBulletin(BaseFragment baseFragment, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        return createPinMessageBulletin(baseFragment, false, runnable, runnable2, resourcesProvider);
    }

    private static Bulletin createPinMessageBulletin(BaseFragment baseFragment, boolean z, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        lottieLayout.setAnimation(z ? R.raw.ic_pin : R.raw.ic_unpin, 28, 28, "Pin", "Line");
        lottieLayout.textView.setText(LocaleController.getString(z ? "MessagePinnedHint" : "MessageUnpinnedHint", z ? R.string.MessagePinnedHint : R.string.MessageUnpinnedHint));
        if (!z) {
            lottieLayout.setButton(new Bulletin.UndoButton(baseFragment.getParentActivity(), true, resourcesProvider).setUndoAction(runnable).setDelayedAction(runnable2));
        }
        return Bulletin.make(baseFragment, lottieLayout, z ? 1500 : 5000);
    }

    public static Bulletin createSoundEnabledBulletin(BaseFragment baseFragment, int i, Theme.ResourcesProvider resourcesProvider) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        boolean z = true;
        if (i == 0) {
            string = LocaleController.getString("SoundOnHint", R.string.SoundOnHint);
        } else if (i == 1) {
            string = LocaleController.getString("SoundOffHint", R.string.SoundOffHint);
            z = false;
        } else {
            throw new IllegalArgumentException();
        }
        if (z) {
            lottieLayout.setAnimation(R.raw.sound_on, new String[0]);
        } else {
            lottieLayout.setAnimation(R.raw.sound_off, new String[0]);
        }
        lottieLayout.textView.setText(string);
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }
}
