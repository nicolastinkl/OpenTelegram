package org.telegram.messenger;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.Pair;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.telegram.messenger.NotificationBadge;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.ResultCallback;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$MessageAction;
import org.telegram.tgnet.TLRPC$TL_account_getChatThemes;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageActionSetChatWallPaper;
import org.telegram.tgnet.TLRPC$TL_messages_setChatTheme;
import org.telegram.tgnet.TLRPC$TL_messages_setChatWallPaper;
import org.telegram.tgnet.TLRPC$TL_theme;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$TL_wallPaper;
import org.telegram.tgnet.TLRPC$TL_wallPaperSettings;
import org.telegram.tgnet.TLRPC$Theme;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC$WallPaperSettings;
import org.telegram.ui.ActionBar.EmojiThemes;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatBackgroundDrawable;

/* loaded from: classes3.dex */
public class ChatThemeController extends BaseController {
    private static List<EmojiThemes> allChatThemes = null;
    private static volatile long lastReloadTimeMs = 0;
    private static final long reloadTimeoutMs = 7200000;
    private static volatile long themesHash;
    private final LongSparseArray<String> dialogEmoticonsMap;
    public static volatile DispatchQueue chatThemeQueue = new DispatchQueue("chatThemeQueue");
    private static final HashMap<Long, Bitmap> themeIdWallpaperThumbMap = new HashMap<>();
    private static final ChatThemeController[] instances = new ChatThemeController[1];

    public static void clearWallpaperImages() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$clearWallpaper$8(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    public static void init() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        themesHash = 0L;
        lastReloadTimeMs = 0L;
        try {
            themesHash = sharedPreferences.getLong("hash", 0L);
            lastReloadTimeMs = sharedPreferences.getLong("lastReload", 0L);
        } catch (Exception e) {
            FileLog.e(e);
        }
        allChatThemes = getAllChatThemesFromPrefs();
        preloadSticker("❌");
        if (allChatThemes.isEmpty()) {
            return;
        }
        Iterator<EmojiThemes> it = allChatThemes.iterator();
        while (it.hasNext()) {
            preloadSticker(it.next().getEmoticon());
        }
    }

    private static void preloadSticker(String str) {
        new ImageReceiver().setImage(ImageLocation.getForDocument(MediaDataController.getInstance(UserConfig.selectedAccount).getEmojiAnimatedSticker(str)), "50_50", null, null, null, 0);
        Emoji.preloadEmoji(str);
    }

    public static void requestAllChatThemes(final ResultCallback<List<EmojiThemes>> resultCallback, final boolean z) {
        if (themesHash == 0 || lastReloadTimeMs == 0) {
            init();
        }
        System.currentTimeMillis();
        TLRPC$TL_account_getChatThemes tLRPC$TL_account_getChatThemes = new TLRPC$TL_account_getChatThemes();
        tLRPC$TL_account_getChatThemes.hash = themesHash;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$TL_account_getChatThemes, new RequestDelegate() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda8
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ChatThemeController.lambda$requestAllChatThemes$3(ResultCallback.this, z, tLObject, tLRPC$TL_error);
            }
        });
        List<EmojiThemes> list = allChatThemes;
        if (list == null || list.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(allChatThemes);
        if (z && !arrayList.get(0).showAsDefaultStub) {
            arrayList.add(0, EmojiThemes.createChatThemesDefault());
        }
        Iterator<EmojiThemes> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().initColors();
        }
        resultCallback.onComplete(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$requestAllChatThemes$3(final ResultCallback resultCallback, final boolean z, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        chatThemeQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ChatThemeController.lambda$requestAllChatThemes$2(TLObject.this, resultCallback, tLRPC$TL_error, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$requestAllChatThemes$2(org.telegram.tgnet.TLObject r7, final org.telegram.tgnet.ResultCallback r8, final org.telegram.tgnet.TLRPC$TL_error r9, boolean r10) {
        /*
            boolean r0 = r7 instanceof org.telegram.tgnet.TLRPC$TL_account_themes
            r1 = 0
            if (r0 == 0) goto L91
            org.telegram.tgnet.TLRPC$TL_account_themes r7 = (org.telegram.tgnet.TLRPC$TL_account_themes) r7
            long r2 = r7.hash
            org.telegram.messenger.ChatThemeController.themesHash = r2
            long r2 = java.lang.System.currentTimeMillis()
            org.telegram.messenger.ChatThemeController.lastReloadTimeMs = r2
            android.content.SharedPreferences r9 = getSharedPreferences()
            android.content.SharedPreferences$Editor r9 = r9.edit()
            r9.clear()
            long r2 = org.telegram.messenger.ChatThemeController.themesHash
            java.lang.String r0 = "hash"
            r9.putLong(r0, r2)
            long r2 = org.telegram.messenger.ChatThemeController.lastReloadTimeMs
            java.lang.String r0 = "lastReload"
            r9.putLong(r0, r2)
            java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_theme> r0 = r7.themes
            int r0 = r0.size()
            java.lang.String r2 = "count"
            r9.putInt(r2, r0)
            java.util.ArrayList r0 = new java.util.ArrayList
            java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_theme> r2 = r7.themes
            int r2 = r2.size()
            r0.<init>(r2)
            r2 = 0
        L41:
            java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_theme> r3 = r7.themes
            int r3 = r3.size()
            if (r2 >= r3) goto L8d
            java.util.ArrayList<org.telegram.tgnet.TLRPC$TL_theme> r3 = r7.themes
            java.lang.Object r3 = r3.get(r2)
            org.telegram.tgnet.TLRPC$TL_theme r3 = (org.telegram.tgnet.TLRPC$TL_theme) r3
            java.lang.String r4 = r3.emoticon
            org.telegram.messenger.Emoji.preloadEmoji(r4)
            org.telegram.tgnet.SerializedData r4 = new org.telegram.tgnet.SerializedData
            int r5 = r3.getObjectSize()
            r4.<init>(r5)
            r3.serializeToStream(r4)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "theme_"
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            byte[] r4 = r4.toByteArray()
            java.lang.String r4 = org.telegram.messenger.Utilities.bytesToHex(r4)
            r9.putString(r5, r4)
            org.telegram.ui.ActionBar.EmojiThemes r4 = new org.telegram.ui.ActionBar.EmojiThemes
            r4.<init>(r3, r1)
            r4.preloadWallpaper()
            r0.add(r4)
            int r2 = r2 + 1
            goto L41
        L8d:
            r9.apply()
            goto L99
        L91:
            boolean r7 = r7 instanceof org.telegram.tgnet.TLRPC$TL_account_themesNotModified
            if (r7 == 0) goto L9b
            java.util.List r0 = getAllChatThemesFromPrefs()
        L99:
            r7 = 0
            goto La5
        L9b:
            r0 = 0
            org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda5 r7 = new org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda5
            r7.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r7)
            r7 = 1
        La5:
            if (r7 != 0) goto Ld6
            if (r10 == 0) goto Lba
            java.lang.Object r7 = r0.get(r1)
            org.telegram.ui.ActionBar.EmojiThemes r7 = (org.telegram.ui.ActionBar.EmojiThemes) r7
            boolean r7 = r7.showAsDefaultStub
            if (r7 != 0) goto Lba
            org.telegram.ui.ActionBar.EmojiThemes r7 = org.telegram.ui.ActionBar.EmojiThemes.createChatThemesDefault()
            r0.add(r1, r7)
        Lba:
            java.util.Iterator r7 = r0.iterator()
        Lbe:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto Lce
            java.lang.Object r9 = r7.next()
            org.telegram.ui.ActionBar.EmojiThemes r9 = (org.telegram.ui.ActionBar.EmojiThemes) r9
            r9.initColors()
            goto Lbe
        Lce:
            org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda2 r7 = new org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda2
            r7.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r7)
        Ld6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.ChatThemeController.lambda$requestAllChatThemes$2(org.telegram.tgnet.TLObject, org.telegram.tgnet.ResultCallback, org.telegram.tgnet.TLRPC$TL_error, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$requestAllChatThemes$1(List list, ResultCallback resultCallback) {
        allChatThemes = new ArrayList(list);
        resultCallback.onComplete(list);
    }

    private static SharedPreferences getSharedPreferences() {
        return ApplicationLoader.applicationContext.getSharedPreferences("chatthemeconfig", 0);
    }

    private static SharedPreferences getEmojiSharedPreferences() {
        return ApplicationLoader.applicationContext.getSharedPreferences("chatthemeconfig_emoji", 0);
    }

    private static List<EmojiThemes> getAllChatThemesFromPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        int i = sharedPreferences.getInt(NotificationBadge.NewHtcHomeBadger.COUNT, 0);
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            SerializedData serializedData = new SerializedData(Utilities.hexToBytes(sharedPreferences.getString("theme_" + i2, "")));
            try {
                TLRPC$TL_theme TLdeserialize = TLRPC$Theme.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                if (TLdeserialize != null) {
                    arrayList.add(new EmojiThemes(TLdeserialize, false));
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        return arrayList;
    }

    public static void requestChatTheme(final String str, final ResultCallback<EmojiThemes> resultCallback) {
        if (TextUtils.isEmpty(str)) {
            resultCallback.onComplete(null);
        } else {
            requestAllChatThemes(new ResultCallback<List<EmojiThemes>>() { // from class: org.telegram.messenger.ChatThemeController.1
                public /* bridge */ /* synthetic */ void onError(Throwable th) {
                    ResultCallback.CC.$default$onError(this, th);
                }

                @Override // org.telegram.tgnet.ResultCallback
                public void onComplete(List<EmojiThemes> list) {
                    for (EmojiThemes emojiThemes : list) {
                        if (str.equals(emojiThemes.getEmoticon())) {
                            emojiThemes.initColors();
                            resultCallback.onComplete(emojiThemes);
                            return;
                        }
                    }
                }

                @Override // org.telegram.tgnet.ResultCallback
                public void onError(TLRPC$TL_error tLRPC$TL_error) {
                    resultCallback.onComplete(null);
                }
            }, false);
        }
    }

    public static ChatThemeController getInstance(int i) {
        ChatThemeController[] chatThemeControllerArr = instances;
        ChatThemeController chatThemeController = chatThemeControllerArr[i];
        if (chatThemeController == null) {
            synchronized (ChatThemeController.class) {
                chatThemeController = chatThemeControllerArr[i];
                if (chatThemeController == null) {
                    chatThemeController = new ChatThemeController(i);
                    chatThemeControllerArr[i] = chatThemeController;
                }
            }
        }
        return chatThemeController;
    }

    public ChatThemeController(int i) {
        super(i);
        this.dialogEmoticonsMap = new LongSparseArray<>();
    }

    public static boolean equals(TLRPC$WallPaper tLRPC$WallPaper, TLRPC$WallPaper tLRPC$WallPaper2) {
        if (tLRPC$WallPaper == null && tLRPC$WallPaper2 == null) {
            return true;
        }
        if (tLRPC$WallPaper == null || tLRPC$WallPaper2 == null) {
            return false;
        }
        String str = tLRPC$WallPaper.uploadingImage;
        if (str != null) {
            return TextUtils.equals(tLRPC$WallPaper2.uploadingImage, str);
        }
        return tLRPC$WallPaper.id == tLRPC$WallPaper2.id && TextUtils.equals(ChatBackgroundDrawable.hash(tLRPC$WallPaper.settings), ChatBackgroundDrawable.hash(tLRPC$WallPaper2.settings));
    }

    public void setDialogTheme(long j, String str, boolean z) {
        if (TextUtils.equals(this.dialogEmoticonsMap.get(j), str)) {
            return;
        }
        if (str == null) {
            this.dialogEmoticonsMap.delete(j);
        } else {
            this.dialogEmoticonsMap.put(j, str);
        }
        if (j >= 0) {
            TLRPC$UserFull userFull = getMessagesController().getUserFull(j);
            if (userFull != null) {
                userFull.theme_emoticon = str;
                getMessagesStorage().updateUserInfo(userFull, true);
            }
        } else {
            TLRPC$ChatFull chatFull = getMessagesController().getChatFull(-j);
            if (chatFull != null) {
                chatFull.theme_emoticon = str;
                getMessagesStorage().updateChatInfo(chatFull, true);
            }
        }
        getEmojiSharedPreferences().edit().putString("chatTheme_" + this.currentAccount + "_" + j, str).apply();
        if (z) {
            TLRPC$TL_messages_setChatTheme tLRPC$TL_messages_setChatTheme = new TLRPC$TL_messages_setChatTheme();
            if (str == null) {
                str = "";
            }
            tLRPC$TL_messages_setChatTheme.emoticon = str;
            tLRPC$TL_messages_setChatTheme.peer = getMessagesController().getInputPeer(j);
            getConnectionsManager().sendRequest(tLRPC$TL_messages_setChatTheme, null);
        }
    }

    public EmojiThemes getDialogTheme(long j) {
        String str = this.dialogEmoticonsMap.get(j);
        if (str == null) {
            str = getEmojiSharedPreferences().getString("chatTheme_" + this.currentAccount + "_" + j, null);
            this.dialogEmoticonsMap.put(j, str);
        }
        if (str != null) {
            for (EmojiThemes emojiThemes : allChatThemes) {
                if (str.equals(emojiThemes.getEmoticon())) {
                    return emojiThemes;
                }
            }
        }
        return null;
    }

    public void saveChatWallpaper(long j, TLRPC$WallPaper tLRPC$WallPaper) {
        if (j < 0) {
            return;
        }
        if (tLRPC$WallPaper != null) {
            SerializedData serializedData = new SerializedData(tLRPC$WallPaper.getObjectSize());
            tLRPC$WallPaper.serializeToStream(serializedData);
            String bytesToHex = Utilities.bytesToHex(serializedData.toByteArray());
            getEmojiSharedPreferences().edit().putString("chatWallpaper_" + this.currentAccount + "_" + j, bytesToHex).apply();
            return;
        }
        getEmojiSharedPreferences().edit().remove("chatWallpaper_" + this.currentAccount + "_" + j).apply();
    }

    public TLRPC$WallPaper getDialogWallpaper(long j) {
        if (j < 0) {
            return null;
        }
        TLRPC$UserFull userFull = getMessagesController().getUserFull(j);
        if (userFull != null) {
            return userFull.wallpaper;
        }
        String string = getEmojiSharedPreferences().getString("chatWallpaper_" + this.currentAccount + "_" + j, null);
        if (string != null) {
            SerializedData serializedData = new SerializedData(Utilities.hexToBytes(string));
            try {
                return TLRPC$WallPaper.TLdeserialize(serializedData, serializedData.readInt32(true), true);
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        return null;
    }

    public static void preloadAllWallpaperImages(boolean z) {
        for (EmojiThemes emojiThemes : allChatThemes) {
            TLRPC$TL_theme tlTheme = emojiThemes.getTlTheme(z ? 1 : 0);
            if (tlTheme != null && !getPatternFile(tlTheme.id).exists()) {
                emojiThemes.loadWallpaper(z ? 1 : 0, null);
            }
        }
    }

    public static void preloadAllWallpaperThumbs(boolean z) {
        for (EmojiThemes emojiThemes : allChatThemes) {
            TLRPC$TL_theme tlTheme = emojiThemes.getTlTheme(z ? 1 : 0);
            if (tlTheme != null) {
                if (!themeIdWallpaperThumbMap.containsKey(Long.valueOf(tlTheme.id))) {
                    emojiThemes.loadWallpaperThumb(z ? 1 : 0, new ResultCallback() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda10
                        @Override // org.telegram.tgnet.ResultCallback
                        public final void onComplete(Object obj) {
                            ChatThemeController.lambda$preloadAllWallpaperThumbs$4((Pair) obj);
                        }

                        @Override // org.telegram.tgnet.ResultCallback
                        public /* synthetic */ void onError(TLRPC$TL_error tLRPC$TL_error) {
                            ResultCallback.CC.$default$onError(this, tLRPC$TL_error);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$preloadAllWallpaperThumbs$4(Pair pair) {
        if (pair != null) {
            themeIdWallpaperThumbMap.put((Long) pair.first, (Bitmap) pair.second);
        }
    }

    public static void clearWallpaperThumbImages() {
        themeIdWallpaperThumbMap.clear();
    }

    public static void getWallpaperBitmap(long j, final ResultCallback<Bitmap> resultCallback) {
        if (themesHash == 0) {
            resultCallback.onComplete(null);
        } else {
            final File patternFile = getPatternFile(j);
            chatThemeQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ChatThemeController.lambda$getWallpaperBitmap$6(patternFile, resultCallback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getWallpaperBitmap$6(File file, final ResultCallback resultCallback) {
        final Bitmap bitmap = null;
        try {
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (resultCallback != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ResultCallback.this.onComplete(bitmap);
                }
            });
        }
    }

    private static File getPatternFile(long j) {
        return new File(ApplicationLoader.getFilesDirFixed(), String.format(Locale.US, "%d_%d.jpg", Long.valueOf(j), Long.valueOf(themesHash)));
    }

    public static void saveWallpaperBitmap(final Bitmap bitmap, long j) {
        final File patternFile = getPatternFile(j);
        chatThemeQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ChatThemeController.lambda$saveWallpaperBitmap$7(patternFile, bitmap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveWallpaperBitmap$7(File file, Bitmap bitmap) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 87, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public static Bitmap getWallpaperThumbBitmap(long j) {
        return themeIdWallpaperThumbMap.get(Long.valueOf(j));
    }

    public void clearCache() {
        themesHash = 0L;
        lastReloadTimeMs = 0L;
        getSharedPreferences().edit().clear().apply();
    }

    public void clearWallpaper(long j, boolean z) {
        TLRPC$TL_messages_setChatWallPaper tLRPC$TL_messages_setChatWallPaper = new TLRPC$TL_messages_setChatWallPaper();
        if (j > 0) {
            tLRPC$TL_messages_setChatWallPaper.peer = MessagesController.getInputPeer(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)));
            TLRPC$UserFull userFull = getMessagesController().getUserFull(j);
            if (userFull != null) {
                userFull.wallpaper = null;
                userFull.flags &= -16777217;
                getMessagesStorage().updateUserInfo(userFull, false);
            }
            saveChatWallpaper(j, null);
            if (z) {
                NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.userInfoDidLoad, Long.valueOf(j), userFull);
            }
        } else {
            tLRPC$TL_messages_setChatWallPaper.peer = MessagesController.getInputPeer(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j)));
        }
        getConnectionsManager().sendRequest(tLRPC$TL_messages_setChatWallPaper, new RequestDelegate() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda9
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ChatThemeController.lambda$clearWallpaper$8(tLObject, tLRPC$TL_error);
            }
        });
    }

    public int setWallpaperToUser(final long j, final String str, Theme.OverrideWallpaperInfo overrideWallpaperInfo, MessageObject messageObject, final Runnable runnable) {
        String str2;
        TLRPC$TL_messages_setChatWallPaper tLRPC$TL_messages_setChatWallPaper = new TLRPC$TL_messages_setChatWallPaper();
        if (j > 0) {
            tLRPC$TL_messages_setChatWallPaper.peer = MessagesController.getInputPeer(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)));
        } else {
            tLRPC$TL_messages_setChatWallPaper.peer = MessagesController.getInputPeer(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j)));
        }
        final boolean z = false;
        if (messageObject != null && (messageObject.messageOwner.action instanceof TLRPC$TL_messageActionSetChatWallPaper)) {
            tLRPC$TL_messages_setChatWallPaper.flags |= 2;
            tLRPC$TL_messages_setChatWallPaper.id = messageObject.getId();
            TLRPC$UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(j);
            if (userFull != null) {
                TLRPC$TL_messageActionSetChatWallPaper tLRPC$TL_messageActionSetChatWallPaper = (TLRPC$TL_messageActionSetChatWallPaper) messageObject.messageOwner.action;
                TLRPC$TL_wallPaper tLRPC$TL_wallPaper = new TLRPC$TL_wallPaper();
                TLRPC$WallPaper tLRPC$WallPaper = tLRPC$TL_messageActionSetChatWallPaper.wallpaper;
                tLRPC$TL_wallPaper.id = tLRPC$WallPaper.id;
                tLRPC$TL_wallPaper.document = tLRPC$WallPaper.document;
                TLRPC$TL_wallPaperSettings tLRPC$TL_wallPaperSettings = new TLRPC$TL_wallPaperSettings();
                tLRPC$TL_wallPaper.settings = tLRPC$TL_wallPaperSettings;
                tLRPC$TL_wallPaperSettings.intensity = (int) (overrideWallpaperInfo.intensity * 100.0f);
                tLRPC$TL_wallPaperSettings.motion = overrideWallpaperInfo.isMotion;
                tLRPC$TL_wallPaperSettings.blur = overrideWallpaperInfo.isBlurred;
                tLRPC$TL_wallPaperSettings.background_color = overrideWallpaperInfo.color;
                tLRPC$TL_wallPaperSettings.second_background_color = overrideWallpaperInfo.gradientColor1;
                tLRPC$TL_wallPaperSettings.third_background_color = overrideWallpaperInfo.gradientColor2;
                tLRPC$TL_wallPaperSettings.fourth_background_color = overrideWallpaperInfo.gradientColor3;
                tLRPC$TL_wallPaperSettings.rotation = overrideWallpaperInfo.rotation;
                tLRPC$TL_wallPaper.uploadingImage = str;
                TLRPC$WallPaper tLRPC$WallPaper2 = userFull.wallpaper;
                if (tLRPC$WallPaper2 != null && (str2 = tLRPC$WallPaper2.uploadingImage) != null && str2.equals(str)) {
                    tLRPC$TL_wallPaper.stripedThumb = userFull.wallpaper.stripedThumb;
                }
                TLRPC$WallPaperSettings tLRPC$WallPaperSettings = tLRPC$TL_wallPaper.settings;
                int i = tLRPC$WallPaperSettings.flags | 1;
                tLRPC$WallPaperSettings.flags = i;
                int i2 = i | 8;
                tLRPC$WallPaperSettings.flags = i2;
                int i3 = i2 | 16;
                tLRPC$WallPaperSettings.flags = i3;
                int i4 = i3 | 32;
                tLRPC$WallPaperSettings.flags = i4;
                tLRPC$WallPaperSettings.flags = i4 | 64;
                TLRPC$TL_wallPaper tLRPC$TL_wallPaper2 = new TLRPC$TL_wallPaper();
                userFull.wallpaper = tLRPC$TL_wallPaper2;
                TLRPC$WallPaper tLRPC$WallPaper3 = tLRPC$TL_messageActionSetChatWallPaper.wallpaper;
                tLRPC$TL_wallPaper2.pattern = tLRPC$WallPaper3.pattern;
                tLRPC$TL_wallPaper2.id = tLRPC$WallPaper3.id;
                tLRPC$TL_wallPaper2.document = tLRPC$WallPaper3.document;
                int i5 = tLRPC$WallPaper3.flags;
                tLRPC$TL_wallPaper2.flags = i5;
                tLRPC$TL_wallPaper2.creator = tLRPC$WallPaper3.creator;
                tLRPC$TL_wallPaper2.dark = tLRPC$WallPaper3.dark;
                tLRPC$TL_wallPaper2.isDefault = tLRPC$WallPaper3.isDefault;
                tLRPC$TL_wallPaper2.slug = tLRPC$WallPaper3.slug;
                tLRPC$TL_wallPaper2.access_hash = tLRPC$WallPaper3.access_hash;
                tLRPC$TL_wallPaper2.stripedThumb = tLRPC$WallPaper3.stripedThumb;
                tLRPC$TL_wallPaper2.settings = tLRPC$TL_wallPaper.settings;
                tLRPC$TL_wallPaper2.flags = i5 | 4;
                userFull.flags |= ConnectionsManager.FileTypePhoto;
                getMessagesStorage().updateUserInfo(userFull, false);
                NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.userInfoDidLoad, Long.valueOf(j), userFull);
                if (runnable != null) {
                    runnable.run();
                }
            }
        } else {
            tLRPC$TL_messages_setChatWallPaper.flags |= 1;
            tLRPC$TL_messages_setChatWallPaper.wallpaper = MessagesController.getInputWallpaper(overrideWallpaperInfo);
            z = true;
        }
        tLRPC$TL_messages_setChatWallPaper.flags |= 4;
        tLRPC$TL_messages_setChatWallPaper.settings = MessagesController.getWallpaperSetting(overrideWallpaperInfo);
        return ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_messages_setChatWallPaper, new RequestDelegate() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda7
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ChatThemeController.this.lambda$setWallpaperToUser$10(j, z, str, runnable, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWallpaperToUser$10(final long j, final boolean z, final String str, final Runnable runnable, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.ChatThemeController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ChatThemeController.this.lambda$setWallpaperToUser$9(tLObject, j, z, str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setWallpaperToUser$9(TLObject tLObject, long j, boolean z, String str, Runnable runnable) {
        String str2;
        if (tLObject instanceof TLRPC$Updates) {
            TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
            TLRPC$UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(j);
            if (userFull != null) {
                int i = 0;
                while (true) {
                    if (i >= tLRPC$Updates.updates.size()) {
                        break;
                    }
                    if (tLRPC$Updates.updates.get(i) instanceof TLRPC$TL_updateNewMessage) {
                        TLRPC$MessageAction tLRPC$MessageAction = ((TLRPC$TL_updateNewMessage) tLRPC$Updates.updates.get(i)).message.action;
                        if (tLRPC$MessageAction instanceof TLRPC$TL_messageActionSetChatWallPaper) {
                            if (z) {
                                TLRPC$TL_messageActionSetChatWallPaper tLRPC$TL_messageActionSetChatWallPaper = (TLRPC$TL_messageActionSetChatWallPaper) tLRPC$MessageAction;
                                tLRPC$TL_messageActionSetChatWallPaper.wallpaper.uploadingImage = str;
                                TLRPC$WallPaper tLRPC$WallPaper = userFull.wallpaper;
                                if (tLRPC$WallPaper != null && (str2 = tLRPC$WallPaper.uploadingImage) != null && str2.equals(str)) {
                                    tLRPC$TL_messageActionSetChatWallPaper.wallpaper.stripedThumb = userFull.wallpaper.stripedThumb;
                                }
                                TLRPC$WallPaper tLRPC$WallPaper2 = tLRPC$TL_messageActionSetChatWallPaper.wallpaper;
                                userFull.wallpaper = tLRPC$WallPaper2;
                                userFull.flags |= ConnectionsManager.FileTypePhoto;
                                saveChatWallpaper(j, tLRPC$WallPaper2);
                                getMessagesStorage().updateUserInfo(userFull, false);
                                NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.userInfoDidLoad, Long.valueOf(j), userFull);
                            }
                        }
                    }
                    i++;
                }
            }
            MessagesController.getInstance(this.currentAccount).processUpdateArray(tLRPC$Updates.updates, tLRPC$Updates.users, tLRPC$Updates.chats, false, tLRPC$Updates.date);
            if (runnable != null) {
                runnable.run();
            }
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.wallpaperSettedToUser, new Object[0]);
        }
    }
}
