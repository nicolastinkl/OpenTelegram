package org.telegram.messenger;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Base64;
import android.util.LongSparseArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.telegram.messenger.SaveToGallerySettingsHelper;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputStorePaymentPurpose;
import org.telegram.tgnet.TLRPC$TL_account_tmpPassword;
import org.telegram.tgnet.TLRPC$TL_defaultHistoryTTL;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_termsOfService;
import org.telegram.tgnet.TLRPC$User;

/* loaded from: classes3.dex */
public class UserConfig extends BaseController {
    private static volatile UserConfig[] Instance = new UserConfig[1];
    public static final int MAX_ACCOUNT_COUNT = 1;
    public static final int MAX_ACCOUNT_DEFAULT_COUNT = 1;
    public static final int i_dialogsLoadOffsetAccess = 5;
    public static final int i_dialogsLoadOffsetChannelId = 4;
    public static final int i_dialogsLoadOffsetChatId = 3;
    public static final int i_dialogsLoadOffsetDate = 1;
    public static final int i_dialogsLoadOffsetId = 0;
    public static final int i_dialogsLoadOffsetUserId = 2;
    public static int selectedAccount;
    public long autoDownloadConfigLoadTime;
    public List<String> awaitBillingProductIds;
    public TLRPC$InputStorePaymentPurpose billingPaymentPurpose;
    public int botRatingLoadTime;
    LongSparseArray<SaveToGallerySettingsHelper.DialogException> chanelSaveGalleryExceptions;
    public long clientUserId;
    private volatile boolean configLoaded;
    public boolean contactsReimported;
    public int contactsSavedCount;
    private TLRPC$User currentUser;
    public String defaultTopicIcons;
    public boolean draftsLoaded;
    public boolean filtersLoaded;
    public String genericAnimationsStickerPack;
    int globalTtl;
    LongSparseArray<SaveToGallerySettingsHelper.DialogException> groupsSaveGalleryExceptions;
    public boolean hasSecureData;
    public boolean hasValidDialogLoadIds;
    public int lastBroadcastId;
    public int lastContactsSyncTime;
    public int lastHintsSyncTime;
    long lastLoadingTime;
    public int lastMyLocationShareTime;
    public int lastSendMessageId;
    public long lastUpdatedDefaultTopicIcons;
    public long lastUpdatedGenericAnimations;
    public long lastUpdatedPremiumGiftsStickerPack;
    public int loginTime;
    public long migrateOffsetAccess;
    public long migrateOffsetChannelId;
    public long migrateOffsetChatId;
    public int migrateOffsetDate;
    public int migrateOffsetId;
    public long migrateOffsetUserId;
    public boolean notificationsSettingsLoaded;
    public boolean notificationsSignUpSettingsLoaded;
    public String premiumGiftsStickerPack;
    public int ratingLoadTime;
    public boolean registeredForPush;
    public volatile byte[] savedPasswordHash;
    public volatile long savedPasswordTime;
    public volatile byte[] savedSaltedPassword;
    public int sharingMyLocationUntil;
    public boolean suggestContacts;
    private final Object sync;
    public boolean syncContacts;
    public TLRPC$TL_account_tmpPassword tmpPassword;
    boolean ttlIsLoading;
    public TLRPC$TL_help_termsOfService unacceptedTermsOfService;
    public boolean unreadDialogsLoaded;
    LongSparseArray<SaveToGallerySettingsHelper.DialogException> userSaveGalleryExceptions;

    public static UserConfig getInstance(int i) {
        UserConfig userConfig = Instance[i];
        if (userConfig == null) {
            synchronized (UserConfig.class) {
                userConfig = Instance[i];
                if (userConfig == null) {
                    UserConfig[] userConfigArr = Instance;
                    UserConfig userConfig2 = new UserConfig(i);
                    userConfigArr[i] = userConfig2;
                    userConfig = userConfig2;
                }
            }
        }
        return userConfig;
    }

    public static int getActivatedAccountsCount() {
        int i = 0;
        for (int i2 = 0; i2 < 1; i2++) {
            if (AccountInstance.getInstance(i2).getUserConfig().isClientActivated()) {
                i++;
            }
        }
        return i;
    }

    public UserConfig(int i) {
        super(i);
        this.sync = new Object();
        this.lastSendMessageId = -210000;
        this.lastBroadcastId = -1;
        this.unreadDialogsLoaded = true;
        this.migrateOffsetId = -1;
        this.migrateOffsetDate = -1;
        this.migrateOffsetUserId = -1L;
        this.migrateOffsetChatId = -1L;
        this.migrateOffsetChannelId = -1L;
        this.migrateOffsetAccess = -1L;
        this.syncContacts = true;
        this.suggestContacts = true;
        this.awaitBillingProductIds = new ArrayList();
        this.globalTtl = 0;
        this.ttlIsLoading = false;
    }

    public static boolean hasPremiumOnAccounts() {
        for (int i = 0; i < 1; i++) {
            if (AccountInstance.getInstance(i).getUserConfig().isClientActivated() && AccountInstance.getInstance(i).getUserConfig().getUserConfig().isPremium()) {
                return true;
            }
        }
        return false;
    }

    public static int getMaxAccountCount() {
        return hasPremiumOnAccounts() ? 5 : 3;
    }

    public int getNewMessageId() {
        int i;
        synchronized (this.sync) {
            i = this.lastSendMessageId;
            this.lastSendMessageId = i - 1;
        }
        return i;
    }

    public void saveConfig(final boolean z) {
        NotificationCenter.getInstance(this.currentAccount).doOnIdle(new Runnable() { // from class: org.telegram.messenger.UserConfig$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                UserConfig.this.lambda$saveConfig$0(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveConfig$0(boolean z) {
        if (this.configLoaded) {
            synchronized (this.sync) {
                try {
                    SharedPreferences.Editor edit = getPreferences().edit();
                    if (this.currentAccount == 0) {
                        edit.putInt("selectedAccount", selectedAccount);
                    }
                    edit.putBoolean("registeredForPush", this.registeredForPush);
                    edit.putInt("lastSendMessageId", this.lastSendMessageId);
                    edit.putInt("contactsSavedCount", this.contactsSavedCount);
                    edit.putInt("lastBroadcastId", this.lastBroadcastId);
                    edit.putInt("lastContactsSyncTime", this.lastContactsSyncTime);
                    edit.putInt("lastHintsSyncTime", this.lastHintsSyncTime);
                    edit.putBoolean("draftsLoaded", this.draftsLoaded);
                    edit.putBoolean("unreadDialogsLoaded", this.unreadDialogsLoaded);
                    edit.putInt("ratingLoadTime", this.ratingLoadTime);
                    edit.putInt("botRatingLoadTime", this.botRatingLoadTime);
                    edit.putBoolean("contactsReimported", this.contactsReimported);
                    edit.putInt("loginTime", this.loginTime);
                    edit.putBoolean("syncContacts", this.syncContacts);
                    edit.putBoolean("suggestContacts", this.suggestContacts);
                    edit.putBoolean("hasSecureData", this.hasSecureData);
                    edit.putBoolean("notificationsSettingsLoaded3", this.notificationsSettingsLoaded);
                    edit.putBoolean("notificationsSignUpSettingsLoaded", this.notificationsSignUpSettingsLoaded);
                    edit.putLong("autoDownloadConfigLoadTime", this.autoDownloadConfigLoadTime);
                    edit.putBoolean("hasValidDialogLoadIds", this.hasValidDialogLoadIds);
                    edit.putInt("sharingMyLocationUntil", this.sharingMyLocationUntil);
                    edit.putInt("lastMyLocationShareTime", this.lastMyLocationShareTime);
                    edit.putBoolean("filtersLoaded", this.filtersLoaded);
                    edit.putStringSet("awaitBillingProductIds", new HashSet(this.awaitBillingProductIds));
                    TLRPC$InputStorePaymentPurpose tLRPC$InputStorePaymentPurpose = this.billingPaymentPurpose;
                    if (tLRPC$InputStorePaymentPurpose != null) {
                        SerializedData serializedData = new SerializedData(tLRPC$InputStorePaymentPurpose.getObjectSize());
                        this.billingPaymentPurpose.serializeToStream(serializedData);
                        edit.putString("billingPaymentPurpose", Base64.encodeToString(serializedData.toByteArray(), 0));
                        serializedData.cleanup();
                    } else {
                        edit.remove("billingPaymentPurpose");
                    }
                    edit.putString("premiumGiftsStickerPack", this.premiumGiftsStickerPack);
                    edit.putLong("lastUpdatedPremiumGiftsStickerPack", this.lastUpdatedPremiumGiftsStickerPack);
                    edit.putString("genericAnimationsStickerPack", this.genericAnimationsStickerPack);
                    edit.putLong("lastUpdatedGenericAnimations", this.lastUpdatedGenericAnimations);
                    edit.putInt("6migrateOffsetId", this.migrateOffsetId);
                    if (this.migrateOffsetId != -1) {
                        edit.putInt("6migrateOffsetDate", this.migrateOffsetDate);
                        edit.putLong("6migrateOffsetUserId", this.migrateOffsetUserId);
                        edit.putLong("6migrateOffsetChatId", this.migrateOffsetChatId);
                        edit.putLong("6migrateOffsetChannelId", this.migrateOffsetChannelId);
                        edit.putLong("6migrateOffsetAccess", this.migrateOffsetAccess);
                    }
                    TLRPC$TL_help_termsOfService tLRPC$TL_help_termsOfService = this.unacceptedTermsOfService;
                    if (tLRPC$TL_help_termsOfService != null) {
                        try {
                            SerializedData serializedData2 = new SerializedData(tLRPC$TL_help_termsOfService.getObjectSize());
                            this.unacceptedTermsOfService.serializeToStream(serializedData2);
                            edit.putString("terms", Base64.encodeToString(serializedData2.toByteArray(), 0));
                            serializedData2.cleanup();
                        } catch (Exception unused) {
                        }
                    } else {
                        edit.remove("terms");
                    }
                    SharedConfig.saveConfig();
                    if (this.tmpPassword != null) {
                        SerializedData serializedData3 = new SerializedData();
                        this.tmpPassword.serializeToStream(serializedData3);
                        edit.putString("tmpPassword", Base64.encodeToString(serializedData3.toByteArray(), 0));
                        serializedData3.cleanup();
                    } else {
                        edit.remove("tmpPassword");
                    }
                    if (this.currentUser == null) {
                        edit.remove("user");
                    } else if (z) {
                        SerializedData serializedData4 = new SerializedData();
                        this.currentUser.serializeToStream(serializedData4);
                        edit.putString("user", Base64.encodeToString(serializedData4.toByteArray(), 0));
                        serializedData4.cleanup();
                    }
                    edit.commit();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    public static boolean isValidAccount(int i) {
        return i >= 0 && i < 1 && getInstance(i).isClientActivated();
    }

    public boolean isClientActivated() {
        boolean z;
        synchronized (this.sync) {
            z = this.currentUser != null;
        }
        return z;
    }

    public long getClientUserId() {
        long j;
        synchronized (this.sync) {
            TLRPC$User tLRPC$User = this.currentUser;
            j = tLRPC$User != null ? tLRPC$User.id : 0L;
        }
        return j;
    }

    public String getClientPhone() {
        String str;
        synchronized (this.sync) {
            TLRPC$User tLRPC$User = this.currentUser;
            if (tLRPC$User == null || (str = tLRPC$User.phone) == null) {
                str = "";
            }
        }
        return str;
    }

    public TLRPC$User getCurrentUser() {
        TLRPC$User tLRPC$User;
        synchronized (this.sync) {
            tLRPC$User = this.currentUser;
        }
        return tLRPC$User;
    }

    public void setCurrentUser(TLRPC$User tLRPC$User) {
        synchronized (this.sync) {
            TLRPC$User tLRPC$User2 = this.currentUser;
            this.currentUser = tLRPC$User;
            this.clientUserId = tLRPC$User.id;
            checkPremiumSelf(tLRPC$User2, tLRPC$User);
        }
    }

    private void checkPremiumSelf(TLRPC$User tLRPC$User, final TLRPC$User tLRPC$User2) {
        if (tLRPC$User == null || !(tLRPC$User2 == null || tLRPC$User.premium == tLRPC$User2.premium)) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UserConfig$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UserConfig.this.lambda$checkPremiumSelf$1(tLRPC$User2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkPremiumSelf$1(TLRPC$User tLRPC$User) {
        getMessagesController().updatePremium(tLRPC$User.premium);
        NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.premiumStatusChangedGlobal, new Object[0]);
        getMediaDataController().loadPremiumPromo(false);
        getMediaDataController().loadReactions(false, true);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:9|(1:11)|12|(18:17|18|(1:24)|25|26|27|(1:31)|33|(1:35)|36|(1:40)|41|(1:45)|46|(1:48)|49|50|51)|54|18|(3:20|22|24)|25|26|27|(2:29|31)|33|(0)|36|(2:38|40)|41|(2:43|45)|46|(0)|49|50|51) */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0177, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0178, code lost:
    
        org.telegram.messenger.FileLog.e(r2);
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0185 A[Catch: all -> 0x0200, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:9:0x0009, B:11:0x0012, B:12:0x001a, B:14:0x00d4, B:18:0x00e0, B:20:0x0115, B:22:0x011d, B:24:0x0123, B:25:0x0135, B:27:0x0155, B:29:0x015e, B:31:0x0164, B:33:0x017b, B:35:0x0185, B:36:0x01ad, B:38:0x01b6, B:40:0x01bc, B:41:0x01ce, B:43:0x01d7, B:45:0x01dd, B:46:0x01ef, B:48:0x01f3, B:49:0x01fc, B:50:0x01fe, B:53:0x0178), top: B:3:0x0003, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01f3 A[Catch: all -> 0x0200, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:9:0x0009, B:11:0x0012, B:12:0x001a, B:14:0x00d4, B:18:0x00e0, B:20:0x0115, B:22:0x011d, B:24:0x0123, B:25:0x0135, B:27:0x0155, B:29:0x015e, B:31:0x0164, B:33:0x017b, B:35:0x0185, B:36:0x01ad, B:38:0x01b6, B:40:0x01bc, B:41:0x01ce, B:43:0x01d7, B:45:0x01dd, B:46:0x01ef, B:48:0x01f3, B:49:0x01fc, B:50:0x01fe, B:53:0x0178), top: B:3:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadConfig() {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.UserConfig.loadConfig():void");
    }

    public boolean isConfigLoaded() {
        return this.configLoaded;
    }

    public void savePassword(byte[] bArr, byte[] bArr2) {
        this.savedPasswordTime = SystemClock.elapsedRealtime();
        this.savedPasswordHash = bArr;
        this.savedSaltedPassword = bArr2;
    }

    public void checkSavedPassword() {
        if (!(this.savedSaltedPassword == null && this.savedPasswordHash == null) && Math.abs(SystemClock.elapsedRealtime() - this.savedPasswordTime) >= 1800000) {
            resetSavedPassword();
        }
    }

    public void resetSavedPassword() {
        this.savedPasswordTime = 0L;
        if (this.savedPasswordHash != null) {
            Arrays.fill(this.savedPasswordHash, (byte) 0);
            this.savedPasswordHash = null;
        }
        if (this.savedSaltedPassword != null) {
            Arrays.fill(this.savedSaltedPassword, (byte) 0);
            this.savedSaltedPassword = null;
        }
    }

    public SharedPreferences getPreferences() {
        if (this.currentAccount == 0) {
            return ApplicationLoader.applicationContext.getSharedPreferences("userconfing", 0);
        }
        return ApplicationLoader.applicationContext.getSharedPreferences("userconfig" + this.currentAccount, 0);
    }

    public LongSparseArray<SaveToGallerySettingsHelper.DialogException> getSaveGalleryExceptions(int i) {
        if (i == 1) {
            if (this.userSaveGalleryExceptions == null) {
                this.userSaveGalleryExceptions = SaveToGallerySettingsHelper.loadExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.USERS_PREF_NAME + "_" + this.currentAccount, 0));
            }
            return this.userSaveGalleryExceptions;
        }
        if (i == 2) {
            if (this.groupsSaveGalleryExceptions == null) {
                this.groupsSaveGalleryExceptions = SaveToGallerySettingsHelper.loadExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.GROUPS_PREF_NAME + "_" + this.currentAccount, 0));
            }
            return this.groupsSaveGalleryExceptions;
        }
        if (i != 4) {
            return null;
        }
        if (this.chanelSaveGalleryExceptions == null) {
            this.chanelSaveGalleryExceptions = SaveToGallerySettingsHelper.loadExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.CHANNELS_PREF_NAME + "_" + this.currentAccount, 0));
        }
        return this.chanelSaveGalleryExceptions;
    }

    public void updateSaveGalleryExceptions(int i, LongSparseArray<SaveToGallerySettingsHelper.DialogException> longSparseArray) {
        if (i == 1) {
            this.userSaveGalleryExceptions = longSparseArray;
            SaveToGallerySettingsHelper.saveExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.USERS_PREF_NAME + "_" + this.currentAccount, 0), this.userSaveGalleryExceptions);
            return;
        }
        if (i == 2) {
            this.groupsSaveGalleryExceptions = longSparseArray;
            SaveToGallerySettingsHelper.saveExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.GROUPS_PREF_NAME + "_" + this.currentAccount, 0), this.groupsSaveGalleryExceptions);
            return;
        }
        if (i == 4) {
            this.chanelSaveGalleryExceptions = longSparseArray;
            SaveToGallerySettingsHelper.saveExceptions(ApplicationLoader.applicationContext.getSharedPreferences(SaveToGallerySettingsHelper.CHANNELS_PREF_NAME + "_" + this.currentAccount, 0), this.chanelSaveGalleryExceptions);
        }
    }

    public void clearConfig() {
        getPreferences().edit().clear().apply();
        boolean z = false;
        this.sharingMyLocationUntil = 0;
        this.lastMyLocationShareTime = 0;
        this.currentUser = null;
        this.clientUserId = 0L;
        this.registeredForPush = false;
        this.contactsSavedCount = 0;
        this.lastSendMessageId = -210000;
        this.lastBroadcastId = -1;
        this.notificationsSettingsLoaded = false;
        this.notificationsSignUpSettingsLoaded = false;
        this.migrateOffsetId = -1;
        this.migrateOffsetDate = -1;
        this.migrateOffsetUserId = -1L;
        this.migrateOffsetChatId = -1L;
        this.migrateOffsetChannelId = -1L;
        this.migrateOffsetAccess = -1L;
        this.ratingLoadTime = 0;
        this.botRatingLoadTime = 0;
        this.draftsLoaded = false;
        this.contactsReimported = true;
        this.syncContacts = true;
        this.suggestContacts = true;
        this.unreadDialogsLoaded = true;
        this.hasValidDialogLoadIds = true;
        this.unacceptedTermsOfService = null;
        this.filtersLoaded = false;
        this.hasSecureData = false;
        this.loginTime = (int) (System.currentTimeMillis() / 1000);
        this.lastContactsSyncTime = ((int) (System.currentTimeMillis() / 1000)) - 82800;
        this.lastHintsSyncTime = ((int) (System.currentTimeMillis() / 1000)) - 90000;
        resetSavedPassword();
        int i = 0;
        while (true) {
            if (i >= 1) {
                break;
            }
            if (AccountInstance.getInstance(i).getUserConfig().isClientActivated()) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            SharedConfig.clearConfig();
        }
        saveConfig(true);
    }

    public boolean isPinnedDialogsLoaded(int i) {
        return getPreferences().getBoolean("2pinnedDialogsLoaded" + i, false);
    }

    public void setPinnedDialogsLoaded(int i, boolean z) {
        getPreferences().edit().putBoolean("2pinnedDialogsLoaded" + i, z).commit();
    }

    public void clearPinnedDialogsLoaded() {
        SharedPreferences.Editor edit = getPreferences().edit();
        for (String str : getPreferences().getAll().keySet()) {
            if (str.startsWith("2pinnedDialogsLoaded")) {
                edit.remove(str);
            }
        }
        edit.apply();
    }

    public int getTotalDialogsCount(int i) {
        SharedPreferences preferences = getPreferences();
        StringBuilder sb = new StringBuilder();
        sb.append("2totalDialogsLoadCount");
        sb.append(i == 0 ? "" : Integer.valueOf(i));
        return preferences.getInt(sb.toString(), 0);
    }

    public void setTotalDialogsCount(int i, int i2) {
        SharedPreferences.Editor edit = getPreferences().edit();
        StringBuilder sb = new StringBuilder();
        sb.append("2totalDialogsLoadCount");
        sb.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putInt(sb.toString(), i2).commit();
    }

    public long[] getDialogLoadOffsets(int i) {
        SharedPreferences preferences = getPreferences();
        StringBuilder sb = new StringBuilder();
        sb.append("2dialogsLoadOffsetId");
        sb.append(i == 0 ? "" : Integer.valueOf(i));
        int i2 = preferences.getInt(sb.toString(), this.hasValidDialogLoadIds ? 0 : -1);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("2dialogsLoadOffsetDate");
        sb2.append(i == 0 ? "" : Integer.valueOf(i));
        int i3 = preferences.getInt(sb2.toString(), this.hasValidDialogLoadIds ? 0 : -1);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("2dialogsLoadOffsetUserId");
        sb3.append(i == 0 ? "" : Integer.valueOf(i));
        long prefIntOrLong = AndroidUtilities.getPrefIntOrLong(preferences, sb3.toString(), this.hasValidDialogLoadIds ? 0L : -1L);
        StringBuilder sb4 = new StringBuilder();
        sb4.append("2dialogsLoadOffsetChatId");
        sb4.append(i == 0 ? "" : Integer.valueOf(i));
        long prefIntOrLong2 = AndroidUtilities.getPrefIntOrLong(preferences, sb4.toString(), this.hasValidDialogLoadIds ? 0L : -1L);
        StringBuilder sb5 = new StringBuilder();
        sb5.append("2dialogsLoadOffsetChannelId");
        sb5.append(i == 0 ? "" : Integer.valueOf(i));
        long prefIntOrLong3 = AndroidUtilities.getPrefIntOrLong(preferences, sb5.toString(), this.hasValidDialogLoadIds ? 0L : -1L);
        StringBuilder sb6 = new StringBuilder();
        sb6.append("2dialogsLoadOffsetAccess");
        sb6.append(i != 0 ? Integer.valueOf(i) : "");
        return new long[]{i2, i3, prefIntOrLong, prefIntOrLong2, prefIntOrLong3, preferences.getLong(sb6.toString(), this.hasValidDialogLoadIds ? 0L : -1L)};
    }

    public void setDialogsLoadOffset(int i, int i2, int i3, long j, long j2, long j3, long j4) {
        SharedPreferences.Editor edit = getPreferences().edit();
        StringBuilder sb = new StringBuilder();
        sb.append("2dialogsLoadOffsetId");
        sb.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putInt(sb.toString(), i2);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("2dialogsLoadOffsetDate");
        sb2.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putInt(sb2.toString(), i3);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("2dialogsLoadOffsetUserId");
        sb3.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putLong(sb3.toString(), j);
        StringBuilder sb4 = new StringBuilder();
        sb4.append("2dialogsLoadOffsetChatId");
        sb4.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putLong(sb4.toString(), j2);
        StringBuilder sb5 = new StringBuilder();
        sb5.append("2dialogsLoadOffsetChannelId");
        sb5.append(i == 0 ? "" : Integer.valueOf(i));
        edit.putLong(sb5.toString(), j3);
        StringBuilder sb6 = new StringBuilder();
        sb6.append("2dialogsLoadOffsetAccess");
        sb6.append(i != 0 ? Integer.valueOf(i) : "");
        edit.putLong(sb6.toString(), j4);
        edit.putBoolean("hasValidDialogLoadIds", true);
        edit.commit();
    }

    public boolean isPremium() {
        TLRPC$User tLRPC$User = this.currentUser;
        if (tLRPC$User == null) {
            return false;
        }
        return tLRPC$User.premium;
    }

    public Long getEmojiStatus() {
        return UserObject.getEmojiStatusDocumentId(this.currentUser);
    }

    public int getGlobalTTl() {
        return this.globalTtl;
    }

    public void loadGlobalTTl() {
        if (this.ttlIsLoading || System.currentTimeMillis() - this.lastLoadingTime < 60000) {
            return;
        }
        this.ttlIsLoading = true;
        getConnectionsManager().sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$TL_messages_getDefaultHistoryTTL
            public static int constructor = 1703637384;

            @Override // org.telegram.tgnet.TLObject
            public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
                return TLRPC$TL_defaultHistoryTTL.TLdeserialize(abstractSerializedData, i, z);
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
            }
        }, new RequestDelegate() { // from class: org.telegram.messenger.UserConfig$$ExternalSyntheticLambda3
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                UserConfig.this.lambda$loadGlobalTTl$3(tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadGlobalTTl$3(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.UserConfig$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                UserConfig.this.lambda$loadGlobalTTl$2(tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadGlobalTTl$2(TLObject tLObject) {
        if (tLObject != null) {
            this.globalTtl = ((TLRPC$TL_defaultHistoryTTL) tLObject).period / 60;
            getNotificationCenter().postNotificationName(NotificationCenter.didUpdateGlobalAutoDeleteTimer, new Object[0]);
            this.ttlIsLoading = false;
            this.lastLoadingTime = System.currentTimeMillis();
        }
    }

    public void setGlobalTtl(int i) {
        this.globalTtl = i;
    }

    public void clearFilters() {
        getPreferences().edit().remove("filtersLoaded").apply();
        this.filtersLoaded = false;
    }
}
