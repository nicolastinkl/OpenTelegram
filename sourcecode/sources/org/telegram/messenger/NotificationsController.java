package org.telegram.messenger;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.PostProcessor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import com.youth.banner.config.BannerConfig;
import j$.util.function.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$Dialog;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageAction;
import org.telegram.tgnet.TLRPC$MessageFwdHeader;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$TL_account_updateNotifySettings;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputNotifyBroadcasts;
import org.telegram.tgnet.TLRPC$TL_inputNotifyChats;
import org.telegram.tgnet.TLRPC$TL_inputNotifyForumTopic;
import org.telegram.tgnet.TLRPC$TL_inputNotifyPeer;
import org.telegram.tgnet.TLRPC$TL_inputNotifyUsers;
import org.telegram.tgnet.TLRPC$TL_inputPeerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_messageActionContactSignUp;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageActionSetMessagesTTL;
import org.telegram.tgnet.TLRPC$TL_messageActionUserJoined;
import org.telegram.tgnet.TLRPC$TL_messageEntitySpoiler;
import org.telegram.tgnet.TLRPC$TL_notificationSoundDefault;
import org.telegram.tgnet.TLRPC$TL_notificationSoundLocal;
import org.telegram.tgnet.TLRPC$TL_notificationSoundNone;
import org.telegram.tgnet.TLRPC$TL_notificationSoundRingtone;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.Components.spoilers.SpoilerEffect;
import org.telegram.ui.PopupNotificationActivity;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes3.dex */
public class NotificationsController extends BaseController {
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    private static volatile NotificationsController[] Instance = null;
    public static String OTHER_NOTIFICATIONS_CHANNEL = null;
    public static final int SETTING_MUTE_2_DAYS = 2;
    public static final int SETTING_MUTE_8_HOURS = 1;
    public static final int SETTING_MUTE_CUSTOM = 5;
    public static final int SETTING_MUTE_FOREVER = 3;
    public static final int SETTING_MUTE_HOUR = 0;
    public static final int SETTING_MUTE_UNMUTE = 4;
    public static final int SETTING_SOUND_OFF = 1;
    public static final int SETTING_SOUND_ON = 0;
    public static final int TYPE_CHANNEL = 2;
    public static final int TYPE_GROUP = 0;
    public static final int TYPE_PRIVATE = 1;
    protected static AudioManager audioManager;
    private static final Object[] lockObjects;
    private static NotificationManagerCompat notificationManager;
    private static NotificationManager systemNotificationManager;
    private AlarmManager alarmManager;
    private boolean channelGroupsCreated;
    private ArrayList<MessageObject> delayedPushMessages;
    NotificationsSettingsFacade dialogsNotificationsFacade;
    private LongSparseArray<MessageObject> fcmRandomMessagesDict;
    private Boolean groupsCreated;
    private boolean inChatSoundEnabled;
    private int lastBadgeCount;
    private int lastButtonId;
    public long lastNotificationChannelCreateTime;
    private int lastOnlineFromOtherDevice;
    private long lastSoundOutPlay;
    private long lastSoundPlay;
    private LongSparseArray<Integer> lastWearNotifiedMessageId;
    private String launcherClassName;
    private SpoilerEffect mediaSpoilerEffect;
    private Runnable notificationDelayRunnable;
    private PowerManager.WakeLock notificationDelayWakelock;
    private String notificationGroup;
    private int notificationId;
    private boolean notifyCheck;
    private long openedDialogId;
    private HashSet<Long> openedInBubbleDialogs;
    private int openedTopicId;
    private int personalCount;
    public ArrayList<MessageObject> popupMessages;
    public ArrayList<MessageObject> popupReplyMessages;
    private LongSparseArray<Integer> pushDialogs;
    private LongSparseArray<Integer> pushDialogsOverrideMention;
    private ArrayList<MessageObject> pushMessages;
    private LongSparseArray<SparseArray<MessageObject>> pushMessagesDict;
    public boolean showBadgeMessages;
    public boolean showBadgeMuted;
    public boolean showBadgeNumber;
    private LongSparseArray<Point> smartNotificationsDialogs;
    private int soundIn;
    private boolean soundInLoaded;
    private int soundOut;
    private boolean soundOutLoaded;
    private SoundPool soundPool;
    private int soundRecord;
    private boolean soundRecordLoaded;
    char[] spoilerChars;
    private int total_unread_count;
    private LongSparseArray<Integer> wearNotificationsIds;
    private static DispatchQueue notificationsQueue = new DispatchQueue("notificationsQueue");
    public static long globalSecretChatId = DialogObject.makeEncryptedDialogId(1);

    public static String getGlobalNotificationsKey(int i) {
        return i == 0 ? "EnableGroup2" : i == 1 ? "EnableAll2" : "EnableChannel2";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateServerNotificationsSettings$39(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateServerNotificationsSettings$40(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    static {
        notificationManager = null;
        systemNotificationManager = null;
        if (Build.VERSION.SDK_INT >= 26 && ApplicationLoader.applicationContext != null) {
            notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
            systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
            checkOtherNotificationsChannel();
        }
        audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        Instance = new NotificationsController[1];
        lockObjects = new Object[1];
        for (int i = 0; i < 1; i++) {
            lockObjects[i] = new Object();
        }
    }

    public static NotificationsController getInstance(int i) {
        NotificationsController notificationsController = Instance[i];
        if (notificationsController == null) {
            synchronized (lockObjects[i]) {
                notificationsController = Instance[i];
                if (notificationsController == null) {
                    NotificationsController[] notificationsControllerArr = Instance;
                    NotificationsController notificationsController2 = new NotificationsController(i);
                    notificationsControllerArr[i] = notificationsController2;
                    notificationsController = notificationsController2;
                }
            }
        }
        return notificationsController;
    }

    public NotificationsController(int i) {
        super(i);
        this.pushMessages = new ArrayList<>();
        this.delayedPushMessages = new ArrayList<>();
        this.pushMessagesDict = new LongSparseArray<>();
        this.fcmRandomMessagesDict = new LongSparseArray<>();
        this.smartNotificationsDialogs = new LongSparseArray<>();
        this.pushDialogs = new LongSparseArray<>();
        this.wearNotificationsIds = new LongSparseArray<>();
        this.lastWearNotifiedMessageId = new LongSparseArray<>();
        this.pushDialogsOverrideMention = new LongSparseArray<>();
        this.popupMessages = new ArrayList<>();
        this.popupReplyMessages = new ArrayList<>();
        this.openedInBubbleDialogs = new HashSet<>();
        this.openedDialogId = 0L;
        this.openedTopicId = 0;
        this.lastButtonId = 5000;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.notifyCheck = false;
        this.lastOnlineFromOtherDevice = 0;
        this.lastBadgeCount = -1;
        this.mediaSpoilerEffect = new SpoilerEffect();
        this.spoilerChars = new char[]{10252, 10338, 10385, 10280};
        this.notificationId = this.currentAccount + 1;
        StringBuilder sb = new StringBuilder();
        sb.append("messages");
        int i2 = this.currentAccount;
        sb.append(i2 == 0 ? "" : Integer.valueOf(i2));
        this.notificationGroup = sb.toString();
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        this.inChatSoundEnabled = notificationsSettings.getBoolean("EnableInChatSound", true);
        this.showBadgeNumber = notificationsSettings.getBoolean("badgeNumber", true);
        this.showBadgeMuted = notificationsSettings.getBoolean("badgeNumberMuted", false);
        this.showBadgeMessages = notificationsSettings.getBoolean("badgeNumberMessages", true);
        notificationManager = NotificationManagerCompat.from(ApplicationLoader.applicationContext);
        systemNotificationManager = (NotificationManager) ApplicationLoader.applicationContext.getSystemService("notification");
        try {
            audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            this.alarmManager = (AlarmManager) ApplicationLoader.applicationContext.getSystemService("alarm");
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            PowerManager.WakeLock newWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(1, "telegram:notification_delay_lock");
            this.notificationDelayWakelock = newWakeLock;
            newWakeLock.setReferenceCounted(false);
        } catch (Exception e3) {
            FileLog.e(e3);
        }
        this.notificationDelayRunnable = new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$new$0();
            }
        };
        this.dialogsNotificationsFacade = new NotificationsSettingsFacade(this.currentAccount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("delay reached");
        }
        if (!this.delayedPushMessages.isEmpty()) {
            showOrUpdateNotification(true);
            this.delayedPushMessages.clear();
        }
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public static void checkOtherNotificationsChannel() {
        SharedPreferences sharedPreferences;
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            OTHER_NOTIFICATIONS_CHANNEL = sharedPreferences.getString("OtherKey", "Other3");
        } else {
            sharedPreferences = null;
        }
        NotificationChannel notificationChannel = systemNotificationManager.getNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
        if (notificationChannel != null && notificationChannel.getImportance() == 0) {
            systemNotificationManager.deleteNotificationChannel(OTHER_NOTIFICATIONS_CHANNEL);
            OTHER_NOTIFICATIONS_CHANNEL = null;
            notificationChannel = null;
        }
        if (OTHER_NOTIFICATIONS_CHANNEL == null) {
            if (sharedPreferences == null) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
            }
            OTHER_NOTIFICATIONS_CHANNEL = "Other" + Utilities.random.nextLong();
            sharedPreferences.edit().putString("OtherKey", OTHER_NOTIFICATIONS_CHANNEL).commit();
        }
        if (notificationChannel == null) {
            NotificationChannel notificationChannel2 = new NotificationChannel(OTHER_NOTIFICATIONS_CHANNEL, "Internal notifications", 3);
            notificationChannel2.enableLights(false);
            notificationChannel2.enableVibration(false);
            notificationChannel2.setSound(null, null);
            try {
                systemNotificationManager.createNotificationChannel(notificationChannel2);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public static String getSharedPrefKey(long j, int i) {
        return i != 0 ? String.format(Locale.US, "%d_%d", Long.valueOf(j), Integer.valueOf(i)) : String.valueOf(j);
    }

    public void muteUntil(long j, int i, int i2) {
        long j2 = 0;
        if (j != 0) {
            SharedPreferences.Editor edit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
            boolean z = i != 0;
            boolean isGlobalNotificationsEnabled = getInstance(this.currentAccount).isGlobalNotificationsEnabled(j);
            String sharedPrefKey = getSharedPrefKey(j, i);
            if (i2 != Integer.MAX_VALUE) {
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 3);
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + sharedPrefKey, getConnectionsManager().getCurrentTime() + i2);
                j2 = (((long) i2) << 32) | 1;
            } else if (!isGlobalNotificationsEnabled && !z) {
                edit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey);
            } else {
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey, 2);
                j2 = 1L;
            }
            edit.apply();
            if (i == 0) {
                getInstance(this.currentAccount).removeNotificationsForDialog(j);
                MessagesStorage.getInstance(this.currentAccount).setDialogFlags(j, j2);
                TLRPC$Dialog tLRPC$Dialog = MessagesController.getInstance(this.currentAccount).dialogs_dict.get(j);
                if (tLRPC$Dialog != null) {
                    TLRPC$TL_peerNotifySettings tLRPC$TL_peerNotifySettings = new TLRPC$TL_peerNotifySettings();
                    tLRPC$Dialog.notify_settings = tLRPC$TL_peerNotifySettings;
                    if (i2 != Integer.MAX_VALUE || isGlobalNotificationsEnabled) {
                        tLRPC$TL_peerNotifySettings.mute_until = i2;
                    }
                }
            }
            getInstance(this.currentAccount).updateServerNotificationsSettings(j, i);
        }
    }

    public void cleanup() {
        this.popupMessages.clear();
        this.popupReplyMessages.clear();
        this.channelGroupsCreated = false;
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$cleanup$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$1() {
        this.openedDialogId = 0L;
        this.openedTopicId = 0;
        this.total_unread_count = 0;
        this.personalCount = 0;
        this.pushMessages.clear();
        this.pushMessagesDict.clear();
        this.fcmRandomMessagesDict.clear();
        this.pushDialogs.clear();
        this.wearNotificationsIds.clear();
        this.lastWearNotifiedMessageId.clear();
        this.openedInBubbleDialogs.clear();
        this.delayedPushMessages.clear();
        this.notifyCheck = false;
        this.lastBadgeCount = 0;
        try {
            if (this.notificationDelayWakelock.isHeld()) {
                this.notificationDelayWakelock.release();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        dismissNotification();
        setBadge(getTotalAllUnreadCount());
        SharedPreferences.Editor edit = getAccountInstance().getNotificationsSettings().edit();
        edit.clear();
        edit.commit();
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                systemNotificationManager.deleteNotificationChannelGroup("channels" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("groups" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("private" + this.currentAccount);
                systemNotificationManager.deleteNotificationChannelGroup("other" + this.currentAccount);
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                for (int i = 0; i < size; i++) {
                    String id = notificationChannels.get(i).getId();
                    if (id.startsWith(str)) {
                        try {
                            systemNotificationManager.deleteNotificationChannel(id);
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("delete channel cleanup " + id);
                        }
                    }
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
    }

    public void setInChatSoundEnabled(boolean z) {
        this.inChatSoundEnabled = z;
    }

    public void setOpenedDialogId(final long j, final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$setOpenedDialogId$2(j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOpenedDialogId$2(long j, int i) {
        this.openedDialogId = j;
        this.openedTopicId = i;
    }

    public void setOpenedInBubble(final long j, final boolean z) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$setOpenedInBubble$3(z, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOpenedInBubble$3(boolean z, long j) {
        if (z) {
            this.openedInBubbleDialogs.add(Long.valueOf(j));
        } else {
            this.openedInBubbleDialogs.remove(Long.valueOf(j));
        }
    }

    public void setLastOnlineFromOtherDevice(final int i) {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$setLastOnlineFromOtherDevice$4(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setLastOnlineFromOtherDevice$4(int i) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("set last online from other device = " + i);
        }
        this.lastOnlineFromOtherDevice = i;
    }

    public void removeNotificationsForDialog(long j) {
        processReadMessages(null, j, 0, Integer.MAX_VALUE, false);
        LongSparseIntArray longSparseIntArray = new LongSparseIntArray();
        longSparseIntArray.put(j, 0);
        processDialogsUpdateRead(longSparseIntArray);
    }

    public boolean hasMessagesToReply() {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            TLRPC$Message tLRPC$Message = messageObject.messageOwner;
            if ((!tLRPC$Message.mentioned || !(tLRPC$Message.action instanceof TLRPC$TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && (messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup())) {
                return true;
            }
        }
        return false;
    }

    protected void forceShowPopupForReply() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$forceShowPopupForReply$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceShowPopupForReply$6() {
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.pushMessages.size(); i++) {
            MessageObject messageObject = this.pushMessages.get(i);
            long dialogId = messageObject.getDialogId();
            TLRPC$Message tLRPC$Message = messageObject.messageOwner;
            if ((!tLRPC$Message.mentioned || !(tLRPC$Message.action instanceof TLRPC$TL_messageActionPinMessage)) && !DialogObject.isEncryptedDialog(dialogId) && (messageObject.messageOwner.peer_id.channel_id == 0 || messageObject.isSupergroup())) {
                arrayList.add(0, messageObject);
            }
        }
        if (arrayList.isEmpty() || AndroidUtilities.needShowPasscode() || SharedConfig.isWaitingForPasscodeEnter) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$forceShowPopupForReply$5(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceShowPopupForReply$5(ArrayList arrayList) {
        this.popupReplyMessages = arrayList;
        Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
        intent.putExtra("force", true);
        intent.putExtra("currentAccount", this.currentAccount);
        intent.setFlags(268763140);
        ApplicationLoader.applicationContext.startActivity(intent);
        ApplicationLoader.applicationContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public void removeDeletedMessagesFromNotifications(final LongSparseArray<ArrayList<Integer>> longSparseArray, final boolean z) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$removeDeletedMessagesFromNotifications$9(longSparseArray, z, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$9(LongSparseArray longSparseArray, boolean z, final ArrayList arrayList) {
        long j;
        Integer num;
        LongSparseArray longSparseArray2 = longSparseArray;
        int i = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        int i2 = 0;
        while (i2 < longSparseArray.size()) {
            long keyAt = longSparseArray2.keyAt(i2);
            SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(keyAt);
            if (sparseArray != null) {
                ArrayList arrayList2 = (ArrayList) longSparseArray2.get(keyAt);
                int size = arrayList2.size();
                int i3 = 0;
                while (i3 < size) {
                    int intValue = ((Integer) arrayList2.get(i3)).intValue();
                    MessageObject messageObject = sparseArray.get(intValue);
                    if (messageObject == null || (z && !messageObject.isReactionPush)) {
                        j = keyAt;
                    } else {
                        j = keyAt;
                        long dialogId = messageObject.getDialogId();
                        Integer num2 = this.pushDialogs.get(dialogId);
                        if (num2 == null) {
                            num2 = 0;
                        }
                        Integer valueOf = Integer.valueOf(num2.intValue() - 1);
                        if (valueOf.intValue() <= 0) {
                            this.smartNotificationsDialogs.remove(dialogId);
                            num = 0;
                        } else {
                            num = valueOf;
                        }
                        if (!num.equals(num2)) {
                            if (getMessagesController().isForum(dialogId)) {
                                int i4 = this.total_unread_count - (num2.intValue() > 0 ? 1 : 0);
                                this.total_unread_count = i4;
                                this.total_unread_count = i4 + (num.intValue() > 0 ? 1 : 0);
                            } else {
                                int intValue2 = this.total_unread_count - num2.intValue();
                                this.total_unread_count = intValue2;
                                this.total_unread_count = intValue2 + num.intValue();
                            }
                            this.pushDialogs.put(dialogId, num);
                        }
                        if (num.intValue() == 0) {
                            this.pushDialogs.remove(dialogId);
                            this.pushDialogsOverrideMention.remove(dialogId);
                        }
                        sparseArray.remove(intValue);
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                    }
                    i3++;
                    keyAt = j;
                }
                long j2 = keyAt;
                if (sparseArray.size() == 0) {
                    this.pushMessagesDict.remove(j2);
                }
            }
            i2++;
            longSparseArray2 = longSparseArray;
        }
        if (!arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$removeDeletedMessagesFromNotifications$7(arrayList);
                }
            });
        }
        if (i != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size2 = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$removeDeletedMessagesFromNotifications$8(size2);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$7(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedMessagesFromNotifications$8(int i) {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().postNotificationName(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void removeDeletedHisoryFromNotifications(final LongSparseIntArray longSparseIntArray) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$removeDeletedHisoryFromNotifications$12(longSparseIntArray, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$12(LongSparseIntArray longSparseIntArray, final ArrayList arrayList) {
        Integer num;
        int i = this.total_unread_count;
        getAccountInstance().getNotificationsSettings();
        Integer num2 = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= longSparseIntArray.size()) {
                break;
            }
            long keyAt = longSparseIntArray.keyAt(i2);
            long j = -keyAt;
            long j2 = longSparseIntArray.get(keyAt);
            Integer num3 = this.pushDialogs.get(j);
            if (num3 == null) {
                num3 = num2;
            }
            Integer num4 = num3;
            int i3 = 0;
            while (i3 < this.pushMessages.size()) {
                MessageObject messageObject = this.pushMessages.get(i3);
                if (messageObject.getDialogId() == j) {
                    num = num2;
                    if (messageObject.getId() <= j2) {
                        SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(j);
                        if (sparseArray != null) {
                            sparseArray.remove(messageObject.getId());
                            if (sparseArray.size() == 0) {
                                this.pushMessagesDict.remove(j);
                            }
                        }
                        this.delayedPushMessages.remove(messageObject);
                        this.pushMessages.remove(messageObject);
                        i3--;
                        if (isPersonalMessage(messageObject)) {
                            this.personalCount--;
                        }
                        arrayList.add(messageObject);
                        num4 = Integer.valueOf(num4.intValue() - 1);
                    }
                } else {
                    num = num2;
                }
                i3++;
                num2 = num;
            }
            Integer num5 = num2;
            if (num4.intValue() <= 0) {
                this.smartNotificationsDialogs.remove(j);
                num4 = num5;
            }
            if (!num4.equals(num3)) {
                if (getMessagesController().isForum(j)) {
                    int i4 = this.total_unread_count - (num3.intValue() > 0 ? 1 : 0);
                    this.total_unread_count = i4;
                    this.total_unread_count = i4 + (num4.intValue() <= 0 ? 0 : 1);
                } else {
                    int intValue = this.total_unread_count - num3.intValue();
                    this.total_unread_count = intValue;
                    this.total_unread_count = intValue + num4.intValue();
                }
                this.pushDialogs.put(j, num4);
            }
            if (num4.intValue() == 0) {
                this.pushDialogs.remove(j);
                this.pushDialogsOverrideMention.remove(j);
            }
            i2++;
            num2 = num5;
        }
        if (arrayList.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$removeDeletedHisoryFromNotifications$10(arrayList);
                }
            });
        }
        if (i != this.total_unread_count) {
            if (!this.notifyCheck) {
                this.delayedPushMessages.clear();
                showOrUpdateNotification(this.notifyCheck);
            } else {
                scheduleNotificationDelay(this.lastOnlineFromOtherDevice > getConnectionsManager().getCurrentTime());
            }
            final int size = this.pushDialogs.size();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$removeDeletedHisoryFromNotifications$11(size);
                }
            });
        }
        this.notifyCheck = false;
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$10(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeDeletedHisoryFromNotifications$11(int i) {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().postNotificationName(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processReadMessages(final LongSparseIntArray longSparseIntArray, final long j, final int i, final int i2, final boolean z) {
        final ArrayList arrayList = new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$processReadMessages$14(longSparseIntArray, arrayList, j, i2, i, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00d7, code lost:
    
        r8 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processReadMessages$14(org.telegram.messenger.support.LongSparseIntArray r19, final java.util.ArrayList r20, long r21, int r23, int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processReadMessages$14(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList, long, int, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processReadMessages$13(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int addToPopupMessages(java.util.ArrayList<org.telegram.messenger.MessageObject> r5, org.telegram.messenger.MessageObject r6, long r7, boolean r9, android.content.SharedPreferences r10) {
        /*
            r4 = this;
            boolean r0 = org.telegram.messenger.DialogObject.isEncryptedDialog(r7)
            r1 = 3
            r2 = 0
            if (r0 != 0) goto L62
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "custom_"
            r0.append(r3)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            boolean r0 = r10.getBoolean(r0, r2)
            if (r0 == 0) goto L35
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "popup_"
            r0.append(r3)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            int r0 = r10.getInt(r0, r2)
            goto L36
        L35:
            r0 = 0
        L36:
            if (r0 != 0) goto L58
            boolean r0 = org.telegram.messenger.BuildVars.isAlwaysShowNotification
            if (r0 == 0) goto L3d
            goto L3e
        L3d:
            r1 = 0
        L3e:
            if (r9 == 0) goto L48
            java.lang.String r7 = "popupChannel"
            int r7 = r10.getInt(r7, r1)
        L46:
            r1 = r7
            goto L63
        L48:
            boolean r7 = org.telegram.messenger.DialogObject.isChatDialog(r7)
            if (r7 == 0) goto L51
            java.lang.String r7 = "popupGroup"
            goto L53
        L51:
            java.lang.String r7 = "popupAll"
        L53:
            int r7 = r10.getInt(r7, r1)
            goto L46
        L58:
            r7 = 1
            if (r0 != r7) goto L5c
            goto L63
        L5c:
            r7 = 2
            if (r0 != r7) goto L60
            goto L62
        L60:
            r1 = r0
            goto L63
        L62:
            r1 = 0
        L63:
            if (r1 == 0) goto L78
            org.telegram.tgnet.TLRPC$Message r7 = r6.messageOwner
            org.telegram.tgnet.TLRPC$Peer r7 = r7.peer_id
            long r7 = r7.channel_id
            r9 = 0
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r0 == 0) goto L78
            boolean r7 = r6.isSupergroup()
            if (r7 != 0) goto L78
            r1 = 0
        L78:
            if (r1 == 0) goto L7d
            r5.add(r2, r6)
        L7d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.addToPopupMessages(java.util.ArrayList, org.telegram.messenger.MessageObject, long, boolean, android.content.SharedPreferences):int");
    }

    public void processEditedMessages(final LongSparseArray<ArrayList<MessageObject>> longSparseArray) {
        if (longSparseArray.size() == 0) {
            return;
        }
        new ArrayList(0);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$processEditedMessages$15(longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processEditedMessages$15(LongSparseArray longSparseArray) {
        int size = longSparseArray.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            longSparseArray.keyAt(i);
            ArrayList arrayList = (ArrayList) longSparseArray.valueAt(i);
            int size2 = arrayList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                long j = messageObject.messageOwner.peer_id.channel_id;
                SparseArray<MessageObject> sparseArray = this.pushMessagesDict.get(j != 0 ? -j : 0L);
                if (sparseArray == null) {
                    break;
                }
                MessageObject messageObject2 = sparseArray.get(messageObject.getId());
                if (messageObject2 != null && messageObject2.isReactionPush) {
                    messageObject2 = null;
                }
                if (messageObject2 != null) {
                    sparseArray.put(messageObject.getId(), messageObject);
                    int indexOf = this.pushMessages.indexOf(messageObject2);
                    if (indexOf >= 0) {
                        this.pushMessages.set(indexOf, messageObject);
                    }
                    int indexOf2 = this.delayedPushMessages.indexOf(messageObject2);
                    if (indexOf2 >= 0) {
                        this.delayedPushMessages.set(indexOf2, messageObject);
                    }
                    z = true;
                }
            }
        }
        if (z) {
            showOrUpdateNotification(false);
        }
    }

    public void processNewMessages(final ArrayList<MessageObject> arrayList, final boolean z, final boolean z2, final CountDownLatch countDownLatch) {
        if (!arrayList.isEmpty()) {
            final ArrayList arrayList2 = new ArrayList(0);
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$processNewMessages$18(arrayList, arrayList2, z2, z, countDownLatch);
                }
            });
        } else if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0048, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageActionUserJoined) == false) goto L19;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0145  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processNewMessages$18(java.util.ArrayList r30, final java.util.ArrayList r31, boolean r32, boolean r33, java.util.concurrent.CountDownLatch r34) {
        /*
            Method dump skipped, instructions count: 884
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processNewMessages$18(java.util.ArrayList, java.util.ArrayList, boolean, boolean, java.util.concurrent.CountDownLatch):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processNewMessages$16(ArrayList arrayList, int i) {
        this.popupMessages.addAll(0, arrayList);
        if (ApplicationLoader.mainInterfacePaused || !ApplicationLoader.isScreenOn) {
            if (i == 3 || ((i == 1 && ApplicationLoader.isScreenOn) || (i == 2 && !ApplicationLoader.isScreenOn))) {
                Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) PopupNotificationActivity.class);
                intent.setFlags(268763140);
                try {
                    ApplicationLoader.applicationContext.startActivity(intent);
                } catch (Throwable unused) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processNewMessages$17(int i) {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().postNotificationName(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private void appendMessage(MessageObject messageObject) {
        for (int i = 0; i < this.pushMessages.size(); i++) {
            if (this.pushMessages.get(i).getId() == messageObject.getId() && this.pushMessages.get(i).getDialogId() == messageObject.getDialogId()) {
                return;
            }
        }
        this.pushMessages.add(0, messageObject);
    }

    public int getTotalUnreadCount() {
        return this.total_unread_count;
    }

    public void processDialogsUpdateRead(final LongSparseIntArray longSparseIntArray) {
        final ArrayList arrayList = new ArrayList();
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$processDialogsUpdateRead$21(longSparseIntArray, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0068 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0093 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processDialogsUpdateRead$21(org.telegram.messenger.support.LongSparseIntArray r18, final java.util.ArrayList r19) {
        /*
            Method dump skipped, instructions count: 415
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.lambda$processDialogsUpdateRead$21(org.telegram.messenger.support.LongSparseIntArray, java.util.ArrayList):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDialogsUpdateRead$19(ArrayList arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            this.popupMessages.remove(arrayList.get(i));
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processDialogsUpdateRead$20(int i) {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().postNotificationName(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    public void processLoadedUnreadMessages(final LongSparseArray<Integer> longSparseArray, final ArrayList<TLRPC$Message> arrayList, final ArrayList<MessageObject> arrayList2, ArrayList<TLRPC$User> arrayList3, ArrayList<TLRPC$Chat> arrayList4, ArrayList<TLRPC$EncryptedChat> arrayList5) {
        getMessagesController().putUsers(arrayList3, true);
        getMessagesController().putChats(arrayList4, true);
        getMessagesController().putEncryptedChats(arrayList5, true);
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$processLoadedUnreadMessages$23(arrayList, longSparseArray, arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processLoadedUnreadMessages$23(ArrayList arrayList, LongSparseArray longSparseArray, ArrayList arrayList2) {
        boolean z;
        boolean z2;
        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader;
        int i;
        SparseArray<MessageObject> sparseArray;
        long j;
        boolean z3;
        SparseArray<MessageObject> sparseArray2;
        ArrayList arrayList3 = arrayList;
        this.pushDialogs.clear();
        this.pushMessages.clear();
        this.pushMessagesDict.clear();
        boolean z4 = false;
        this.total_unread_count = 0;
        this.personalCount = 0;
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        LongSparseArray longSparseArray2 = new LongSparseArray();
        long j2 = 0;
        int i2 = 1;
        if (arrayList3 != null) {
            int i3 = 0;
            while (i3 < arrayList.size()) {
                TLRPC$Message tLRPC$Message = (TLRPC$Message) arrayList3.get(i3);
                if (tLRPC$Message != null && ((tLRPC$MessageFwdHeader = tLRPC$Message.fwd_from) == null || !tLRPC$MessageFwdHeader.imported)) {
                    TLRPC$MessageAction tLRPC$MessageAction = tLRPC$Message.action;
                    if (!(tLRPC$MessageAction instanceof TLRPC$TL_messageActionSetMessagesTTL) && (!tLRPC$Message.silent || (!(tLRPC$MessageAction instanceof TLRPC$TL_messageActionContactSignUp) && !(tLRPC$MessageAction instanceof TLRPC$TL_messageActionUserJoined)))) {
                        long j3 = tLRPC$Message.peer_id.channel_id;
                        long j4 = j3 != j2 ? -j3 : j2;
                        SparseArray<MessageObject> sparseArray3 = this.pushMessagesDict.get(j4);
                        if (sparseArray3 == null || sparseArray3.indexOfKey(tLRPC$Message.id) < 0) {
                            MessageObject messageObject = new MessageObject(this.currentAccount, tLRPC$Message, z4, z4);
                            if (isPersonalMessage(messageObject)) {
                                this.personalCount += i2;
                            }
                            i = i3;
                            long dialogId = messageObject.getDialogId();
                            int topicId = MessageObject.getTopicId(messageObject.messageOwner, getMessagesController().isForum(messageObject));
                            if (messageObject.messageOwner.mentioned) {
                                sparseArray = sparseArray3;
                                j = messageObject.getFromChatId();
                            } else {
                                sparseArray = sparseArray3;
                                j = dialogId;
                            }
                            int indexOfKey = longSparseArray2.indexOfKey(j);
                            if (indexOfKey >= 0 && topicId == 0) {
                                z3 = ((Boolean) longSparseArray2.valueAt(indexOfKey)).booleanValue();
                            } else {
                                int notifyOverride = getNotifyOverride(notificationsSettings, j, topicId);
                                if (notifyOverride == -1) {
                                    z3 = isGlobalNotificationsEnabled(j);
                                } else {
                                    z3 = notifyOverride != 2;
                                }
                                longSparseArray2.put(j, Boolean.valueOf(z3));
                            }
                            if (z3 && (j != this.openedDialogId || !ApplicationLoader.isScreenOn)) {
                                if (sparseArray == null) {
                                    sparseArray2 = new SparseArray<>();
                                    this.pushMessagesDict.put(j4, sparseArray2);
                                } else {
                                    sparseArray2 = sparseArray;
                                }
                                sparseArray2.put(tLRPC$Message.id, messageObject);
                                appendMessage(messageObject);
                                if (dialogId != j) {
                                    Integer num = this.pushDialogsOverrideMention.get(dialogId);
                                    this.pushDialogsOverrideMention.put(dialogId, Integer.valueOf(num == null ? 1 : num.intValue() + 1));
                                }
                            }
                            i3 = i + 1;
                            arrayList3 = arrayList;
                            z4 = false;
                            j2 = 0;
                            i2 = 1;
                        }
                    }
                }
                i = i3;
                i3 = i + 1;
                arrayList3 = arrayList;
                z4 = false;
                j2 = 0;
                i2 = 1;
            }
        }
        for (int i4 = 0; i4 < longSparseArray.size(); i4++) {
            long keyAt = longSparseArray.keyAt(i4);
            int indexOfKey2 = longSparseArray2.indexOfKey(keyAt);
            if (indexOfKey2 >= 0) {
                z2 = ((Boolean) longSparseArray2.valueAt(indexOfKey2)).booleanValue();
            } else {
                int notifyOverride2 = getNotifyOverride(notificationsSettings, keyAt, 0);
                if (notifyOverride2 == -1) {
                    z2 = isGlobalNotificationsEnabled(keyAt);
                } else {
                    z2 = notifyOverride2 != 2;
                }
                longSparseArray2.put(keyAt, Boolean.valueOf(z2));
            }
            if (z2) {
                int intValue = ((Integer) longSparseArray.valueAt(i4)).intValue();
                this.pushDialogs.put(keyAt, Integer.valueOf(intValue));
                if (getMessagesController().isForum(keyAt)) {
                    this.total_unread_count += intValue > 0 ? 1 : 0;
                } else {
                    this.total_unread_count += intValue;
                }
            }
        }
        if (arrayList2 != null) {
            for (int i5 = 0; i5 < arrayList2.size(); i5++) {
                MessageObject messageObject2 = (MessageObject) arrayList2.get(i5);
                int id = messageObject2.getId();
                if (this.pushMessagesDict.indexOfKey(id) < 0) {
                    if (isPersonalMessage(messageObject2)) {
                        this.personalCount++;
                    }
                    long dialogId2 = messageObject2.getDialogId();
                    int topicId2 = MessageObject.getTopicId(messageObject2.messageOwner, getMessagesController().isForum(messageObject2));
                    TLRPC$Message tLRPC$Message2 = messageObject2.messageOwner;
                    long j5 = tLRPC$Message2.random_id;
                    long fromChatId = tLRPC$Message2.mentioned ? messageObject2.getFromChatId() : dialogId2;
                    int indexOfKey3 = longSparseArray2.indexOfKey(fromChatId);
                    if (indexOfKey3 >= 0 && topicId2 == 0) {
                        z = ((Boolean) longSparseArray2.valueAt(indexOfKey3)).booleanValue();
                    } else {
                        int notifyOverride3 = getNotifyOverride(notificationsSettings, fromChatId, topicId2);
                        if (notifyOverride3 == -1) {
                            z = isGlobalNotificationsEnabled(fromChatId);
                        } else {
                            z = notifyOverride3 != 2;
                        }
                        longSparseArray2.put(fromChatId, Boolean.valueOf(z));
                    }
                    if (z && (fromChatId != this.openedDialogId || !ApplicationLoader.isScreenOn)) {
                        if (id != 0) {
                            long j6 = messageObject2.messageOwner.peer_id.channel_id;
                            long j7 = j6 != 0 ? -j6 : 0L;
                            SparseArray<MessageObject> sparseArray4 = this.pushMessagesDict.get(j7);
                            if (sparseArray4 == null) {
                                sparseArray4 = new SparseArray<>();
                                this.pushMessagesDict.put(j7, sparseArray4);
                            }
                            sparseArray4.put(id, messageObject2);
                        } else if (j5 != 0) {
                            this.fcmRandomMessagesDict.put(j5, messageObject2);
                        }
                        appendMessage(messageObject2);
                        if (dialogId2 != fromChatId) {
                            Integer num2 = this.pushDialogsOverrideMention.get(dialogId2);
                            this.pushDialogsOverrideMention.put(dialogId2, Integer.valueOf(num2 == null ? 1 : num2.intValue() + 1));
                        }
                        Integer num3 = this.pushDialogs.get(fromChatId);
                        int intValue2 = num3 != null ? num3.intValue() + 1 : 1;
                        if (getMessagesController().isForum(fromChatId)) {
                            if (num3 != null) {
                                this.total_unread_count -= num3.intValue() > 0 ? 1 : 0;
                            }
                            this.total_unread_count += intValue2 > 0 ? 1 : 0;
                        } else {
                            if (num3 != null) {
                                this.total_unread_count -= num3.intValue();
                            }
                            this.total_unread_count += intValue2;
                        }
                        this.pushDialogs.put(fromChatId, Integer.valueOf(intValue2));
                    }
                }
            }
        }
        final int size = this.pushDialogs.size();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$processLoadedUnreadMessages$22(size);
            }
        });
        showOrUpdateNotification(SystemClock.elapsedRealtime() / 1000 < 60);
        if (this.showBadgeNumber) {
            setBadge(getTotalAllUnreadCount());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processLoadedUnreadMessages$22(int i) {
        if (this.total_unread_count == 0) {
            this.popupMessages.clear();
            NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
        }
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.notificationsCountUpdated, Integer.valueOf(this.currentAccount));
        getNotificationCenter().postNotificationName(NotificationCenter.dialogsUnreadCounterChanged, Integer.valueOf(i));
    }

    private int getTotalAllUnreadCount() {
        int size;
        int i = 0;
        for (int i2 = 0; i2 < 1; i2++) {
            if (UserConfig.getInstance(i2).isClientActivated()) {
                NotificationsController notificationsController = getInstance(i2);
                if (notificationsController.showBadgeNumber) {
                    if (notificationsController.showBadgeMessages) {
                        if (notificationsController.showBadgeMuted) {
                            try {
                                ArrayList arrayList = new ArrayList(MessagesController.getInstance(i2).allDialogs);
                                int size2 = arrayList.size();
                                for (int i3 = 0; i3 < size2; i3++) {
                                    TLRPC$Dialog tLRPC$Dialog = (TLRPC$Dialog) arrayList.get(i3);
                                    if ((tLRPC$Dialog == null || !DialogObject.isChatDialog(tLRPC$Dialog.id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-tLRPC$Dialog.id)))) && tLRPC$Dialog != null) {
                                        i += MessagesController.getInstance(i2).getDialogUnreadCount(tLRPC$Dialog);
                                    }
                                }
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                        } else {
                            size = notificationsController.total_unread_count;
                        }
                    } else if (notificationsController.showBadgeMuted) {
                        try {
                            int size3 = MessagesController.getInstance(i2).allDialogs.size();
                            for (int i4 = 0; i4 < size3; i4++) {
                                TLRPC$Dialog tLRPC$Dialog2 = MessagesController.getInstance(i2).allDialogs.get(i4);
                                if ((!DialogObject.isChatDialog(tLRPC$Dialog2.id) || !ChatObject.isNotInChat(getMessagesController().getChat(Long.valueOf(-tLRPC$Dialog2.id)))) && MessagesController.getInstance(i2).getDialogUnreadCount(tLRPC$Dialog2) != 0) {
                                    i++;
                                }
                            }
                        } catch (Exception e2) {
                            FileLog.e((Throwable) e2, false);
                        }
                    } else {
                        size = notificationsController.pushDialogs.size();
                    }
                    i += size;
                }
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateBadge$24() {
        setBadge(getTotalAllUnreadCount());
    }

    public void updateBadge() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$updateBadge$24();
            }
        });
    }

    private void setBadge(int i) {
        if (this.lastBadgeCount == i) {
            return;
        }
        this.lastBadgeCount = i;
        NotificationBadge.applyCount(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x0243, code lost:
    
        if (r12.getBoolean("EnablePreviewAll", true) == false) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x025b, code lost:
    
        r1 = r26.messageOwner;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x026b, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageService) == false) goto L700;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x026d, code lost:
    
        r27[0] = null;
        r2 = r1.action;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0275, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionSetSameChatWallPaper) == false) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x027f, code lost:
    
        return org.telegram.messenger.LocaleController.getString("WallpaperSameNotification", org.telegram.messenger.R.string.WallpaperSameNotification);
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0282, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionSetChatWallPaper) == false) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x028c, code lost:
    
        return org.telegram.messenger.LocaleController.getString("WallpaperNotification", org.telegram.messenger.R.string.WallpaperNotification);
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x028f, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGeoProximityReached) == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0297, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x029a, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionUserJoined) != false) goto L698;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x029e, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionContactSignUp) == false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02a4, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionUserUpdatedPhoto) == false) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02b4, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationContactNewPhoto", org.telegram.messenger.R.string.NotificationContactNewPhoto, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02b8, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionLoginUnknownLocation) == false) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02ba, code lost:
    
        r1 = org.telegram.messenger.LocaleController.formatString("formatDateAtTime", org.telegram.messenger.R.string.formatDateAtTime, org.telegram.messenger.LocaleController.getInstance().formatterYear.format(r26.messageOwner.date * 1000), org.telegram.messenger.LocaleController.getInstance().formatterDay.format(r26.messageOwner.date * 1000));
        r2 = org.telegram.messenger.R.string.NotificationUnrecognizedDevice;
        r0 = r26.messageOwner.action;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0316, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationUnrecognizedDevice", r2, getUserConfig().getCurrentUser().first_name, r1, r0.title, r0.address);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0319, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGameScore) != false) goto L696;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x031d, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent) == false) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0323, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall) == false) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0327, code lost:
    
        if (r2.video == false) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0331, code lost:
    
        return org.telegram.messenger.LocaleController.getString("CallMessageVideoIncomingMissed", org.telegram.messenger.R.string.CallMessageVideoIncomingMissed);
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x033a, code lost:
    
        return org.telegram.messenger.LocaleController.getString("CallMessageIncomingMissed", org.telegram.messenger.R.string.CallMessageIncomingMissed);
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x033d, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser) == false) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x033f, code lost:
    
        r3 = r2.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0345, code lost:
    
        if (r3 != 0) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x034e, code lost:
    
        if (r2.users.size() != 1) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0350, code lost:
    
        r3 = r26.messageOwner.action.users.get(0).longValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0365, code lost:
    
        if (r3 == 0) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x036f, code lost:
    
        if (r26.messageOwner.peer_id.channel_id == 0) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0373, code lost:
    
        if (r5.megagroup != false) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0388, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelAddedByNotification", org.telegram.messenger.R.string.ChannelAddedByNotification, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x038b, code lost:
    
        if (r3 != r19) goto L219;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x03a0, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationInvitedToGroup", org.telegram.messenger.R.string.NotificationInvitedToGroup, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x03a1, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x03ad, code lost:
    
        if (r0 != null) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x03af, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x03b5, code lost:
    
        if (r9 != r0.id) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x03b9, code lost:
    
        if (r5.megagroup == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x03ce, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupAddSelfMega", org.telegram.messenger.R.string.NotificationGroupAddSelfMega, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x03e2, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupAddSelf", org.telegram.messenger.R.string.NotificationGroupAddSelf, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x03eb, code lost:
    
        return org.telegram.messenger.LocaleController.getString("JMTWelcomeJoin", org.telegram.messenger.R.string.JMTWelcomeJoin);
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x03ec, code lost:
    
        r1 = new java.lang.StringBuilder();
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x03fc, code lost:
    
        if (r11 >= r26.messageOwner.action.users.size()) goto L819;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x03fe, code lost:
    
        r2 = getMessagesController().getUser(r26.messageOwner.action.users.get(r11));
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0412, code lost:
    
        if (r2 == null) goto L821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0414, code lost:
    
        r2 = org.telegram.messenger.UserObject.getUserName(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x041c, code lost:
    
        if (r1.length() == 0) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x041e, code lost:
    
        r1.append(", ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0423, code lost:
    
        r1.append(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x0426, code lost:
    
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0431, code lost:
    
        return org.telegram.messenger.LocaleController.getString("JMTWelcomeJoin", org.telegram.messenger.R.string.JMTWelcomeJoin);
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0434, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGroupCall) == false) goto L249;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x0449, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupCreatedCall", org.telegram.messenger.R.string.NotificationGroupCreatedCall, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x044c, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGroupCallScheduled) == false) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0454, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x0457, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionInviteToGroupCall) == false) goto L284;
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x0459, code lost:
    
        r3 = r2.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x045f, code lost:
    
        if (r3 != 0) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0468, code lost:
    
        if (r2.users.size() != 1) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x046a, code lost:
    
        r3 = r26.messageOwner.action.users.get(0).longValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x047f, code lost:
    
        if (r3 == 0) goto L272;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x0483, code lost:
    
        if (r3 != r19) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:236:0x0498, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedYouToCall", org.telegram.messenger.R.string.NotificationGroupInvitedYouToCall, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x0499, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x04a5, code lost:
    
        if (r0 != null) goto L270;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x04a7, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x04c2, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedToCall", org.telegram.messenger.R.string.NotificationGroupInvitedToCall, r7, r5.title, org.telegram.messenger.UserObject.getUserName(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x04c3, code lost:
    
        r1 = new java.lang.StringBuilder();
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x04d3, code lost:
    
        if (r2 >= r26.messageOwner.action.users.size()) goto L822;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x04d5, code lost:
    
        r3 = getMessagesController().getUser(r26.messageOwner.action.users.get(r2));
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x04e9, code lost:
    
        if (r3 == null) goto L824;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x04eb, code lost:
    
        r3 = org.telegram.messenger.UserObject.getUserName(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x04f3, code lost:
    
        if (r1.length() == 0) goto L280;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x04f5, code lost:
    
        r1.append(", ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:250:0x04fa, code lost:
    
        r1.append(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x04fd, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0519, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedToCall", org.telegram.messenger.R.string.NotificationGroupInvitedToCall, r7, r5.title, r1.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x051d, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByLink) == false) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0532, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationInvitedToGroupByLink", org.telegram.messenger.R.string.NotificationInvitedToGroupByLink, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0538, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatEditTitle) == false) goto L292;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x054a, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupName", org.telegram.messenger.R.string.NotificationEditedGroupName, r7, r2.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x054d, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatEditPhoto) != false) goto L680;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0551, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeletePhoto) == false) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x0557, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser) == false) goto L313;
     */
    /* JADX WARN: Code restructure failed: missing block: B:271:0x0559, code lost:
    
        r1 = r2.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x055d, code lost:
    
        if (r1 != r19) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x0572, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupKickYou", org.telegram.messenger.R.string.NotificationGroupKickYou, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:276:0x0578, code lost:
    
        if (r1 != r9) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x058a, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupLeftMember", org.telegram.messenger.R.string.NotificationGroupLeftMember, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x058b, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r26.messageOwner.action.user_id));
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x059d, code lost:
    
        if (r0 != null) goto L311;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x059f, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x05bb, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupKickMember", org.telegram.messenger.R.string.NotificationGroupKickMember, r7, r5.title, org.telegram.messenger.UserObject.getUserName(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x05be, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatCreate) == false) goto L317;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x05c6, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x05c9, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate) == false) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x05d1, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x05d4, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo) == false) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x05e6, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ActionMigrateFromGroupNotify", org.telegram.messenger.R.string.ActionMigrateFromGroupNotify, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x05eb, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChannelMigrateFrom) == false) goto L329;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x05fb, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ActionMigrateFromGroupNotify", org.telegram.messenger.R.string.ActionMigrateFromGroupNotify, r2.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x05fe, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionScreenshotTaken) == false) goto L333;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x0606, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:305:0x0609, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPinMessage) == false) goto L661;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x060f, code lost:
    
        if (r5 == null) goto L447;
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x0615, code lost:
    
        if (org.telegram.messenger.ChatObject.isChannel(r5) == false) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x0619, code lost:
    
        if (r5.megagroup == false) goto L447;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x061b, code lost:
    
        r0 = r26.replyMessageObject;
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x061d, code lost:
    
        if (r0 != null) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x0632, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoText", org.telegram.messenger.R.string.NotificationActionPinnedNoText, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x063a, code lost:
    
        if (r0.isMusic() == false) goto L349;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x064c, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedMusic", org.telegram.messenger.R.string.NotificationActionPinnedMusic, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x0653, code lost:
    
        if (r0.isVideo() == false) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x0659, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L357;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x0663, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L357;
     */
    /* JADX WARN: Code restructure failed: missing block: B:327:0x068c, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r7, "📹 " + r0.messageOwner.message, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:329:0x06a0, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVideo", org.telegram.messenger.R.string.NotificationActionPinnedVideo, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:0x06a5, code lost:
    
        if (r0.isGif() == false) goto L369;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x06ab, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x06b5, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x06de, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r7, "🎬 " + r0.messageOwner.message, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x06f2, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGif", org.telegram.messenger.R.string.NotificationActionPinnedGif, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x06fa, code lost:
    
        if (r0.isVoice() == false) goto L373;
     */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x070c, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVoice", org.telegram.messenger.R.string.NotificationActionPinnedVoice, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x0711, code lost:
    
        if (r0.isRoundVideo() == false) goto L377;
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x0723, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedRound", org.telegram.messenger.R.string.NotificationActionPinnedRound, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x0728, code lost:
    
        if (r0.isSticker() != false) goto L441;
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x072e, code lost:
    
        if (r0.isAnimatedSticker() == false) goto L382;
     */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x0732, code lost:
    
        r3 = r0.messageOwner;
        r6 = r3.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x0738, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L392;
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x073e, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x0746, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:359:0x076f, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r7, "📎 " + r0.messageOwner.message, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x0783, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedFile", org.telegram.messenger.R.string.NotificationActionPinnedFile, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x0786, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L439;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x078a, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L397;
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x0790, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x07a5, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoLive", org.telegram.messenger.R.string.NotificationActionPinnedGeoLive, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x07aa, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L405;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x07ac, code lost:
    
        r6 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x07ca, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedContact2", org.telegram.messenger.R.string.NotificationActionPinnedContact2, r7, r5.title, org.telegram.messenger.ContactsController.formatName(r6.first_name, r6.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x07cd, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L413;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x07cf, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r6).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x07d5, code lost:
    
        if (r0.quiz == false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:379:0x07ef, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedQuiz2", org.telegram.messenger.R.string.NotificationActionPinnedQuiz2, r7, r5.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x0808, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPoll2", org.telegram.messenger.R.string.NotificationActionPinnedPoll2, r7, r5.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x080b, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x0811, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L421;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x0819, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L421;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0842, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r7, "🖼 " + r0.messageOwner.message, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:0x0856, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPhoto", org.telegram.messenger.R.string.NotificationActionPinnedPhoto, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x085c, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L427;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:0x086e, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGame", org.telegram.messenger.R.string.NotificationActionPinnedGame, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x086f, code lost:
    
        r3 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x0871, code lost:
    
        if (r3 == null) goto L437;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x0877, code lost:
    
        if (r3.length() <= 0) goto L437;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x0879, code lost:
    
        r0 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x087f, code lost:
    
        if (r0.length() <= 20) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0881, code lost:
    
        r3 = new java.lang.StringBuilder();
        r6 = 0;
        r3.append((java.lang.Object) r0.subSequence(0, 20));
        r3.append("...");
        r0 = r3.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x0897, code lost:
    
        r1 = org.telegram.messenger.R.string.NotificationActionPinnedText;
        r2 = new java.lang.Object[3];
        r2[r6] = r7;
        r2[1] = r0;
        r2[2] = r5.title;
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x08aa, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", r1, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0896, code lost:
    
        r6 = 0;
        r0 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x08be, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoText", org.telegram.messenger.R.string.NotificationActionPinnedNoText, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x08d2, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeo", org.telegram.messenger.R.string.NotificationActionPinnedGeo, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x08d3, code lost:
    
        r0 = r0.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x08d9, code lost:
    
        if (r0 == null) goto L445;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x08ef, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerEmoji", org.telegram.messenger.R.string.NotificationActionPinnedStickerEmoji, r7, r5.title, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0901, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedSticker", org.telegram.messenger.R.string.NotificationActionPinnedSticker, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:0x0903, code lost:
    
        if (r5 == null) goto L555;
     */
    /* JADX WARN: Code restructure failed: missing block: B:418:0x0905, code lost:
    
        r0 = r26.replyMessageObject;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x0907, code lost:
    
        if (r0 != null) goto L453;
     */
    /* JADX WARN: Code restructure failed: missing block: B:421:0x0918, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedNoTextChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:423:0x091e, code lost:
    
        if (r0.isMusic() == false) goto L457;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x092e, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedMusicChannel", org.telegram.messenger.R.string.NotificationActionPinnedMusicChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x0935, code lost:
    
        if (r0.isVideo() == false) goto L467;
     */
    /* JADX WARN: Code restructure failed: missing block: B:429:0x093b, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L465;
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x0945, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L465;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x096b, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r5.title, "📹 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x097c, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVideoChannel", org.telegram.messenger.R.string.NotificationActionPinnedVideoChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x0981, code lost:
    
        if (r0.isGif() == false) goto L477;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x0987, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L475;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b1, code lost:
    
        if (r12.getBoolean("EnablePreviewGroup", true) != false) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:441:0x0991, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L475;
     */
    /* JADX WARN: Code restructure failed: missing block: B:443:0x09b7, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r5.title, "🎬 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:445:0x09c8, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGifChannel", org.telegram.messenger.R.string.NotificationActionPinnedGifChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:447:0x09cf, code lost:
    
        if (r0.isVoice() == false) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:449:0x09df, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVoiceChannel", org.telegram.messenger.R.string.NotificationActionPinnedVoiceChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:451:0x09e4, code lost:
    
        if (r0.isRoundVideo() == false) goto L485;
     */
    /* JADX WARN: Code restructure failed: missing block: B:453:0x09f4, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedRoundChannel", org.telegram.messenger.R.string.NotificationActionPinnedRoundChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x09f9, code lost:
    
        if (r0.isSticker() != false) goto L549;
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x09ff, code lost:
    
        if (r0.isAnimatedSticker() == false) goto L490;
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x0a03, code lost:
    
        r3 = r0.messageOwner;
        r6 = r3.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:459:0x0a09, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L500;
     */
    /* JADX WARN: Code restructure failed: missing block: B:461:0x0a0f, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L498;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x0a17, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L498;
     */
    /* JADX WARN: Code restructure failed: missing block: B:465:0x0a3d, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r5.title, "📎 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x0a4e, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedFileChannel", org.telegram.messenger.R.string.NotificationActionPinnedFileChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x0a51, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L547;
     */
    /* JADX WARN: Code restructure failed: missing block: B:471:0x0a55, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L505;
     */
    /* JADX WARN: Code restructure failed: missing block: B:473:0x0a5b, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L509;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x0a6d, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoLiveChannel", org.telegram.messenger.R.string.NotificationActionPinnedGeoLiveChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x0a71, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L513;
     */
    /* JADX WARN: Code restructure failed: missing block: B:478:0x0a73, code lost:
    
        r6 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:479:0x0a8f, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedContactChannel2", org.telegram.messenger.R.string.NotificationActionPinnedContactChannel2, r5.title, org.telegram.messenger.ContactsController.formatName(r6.first_name, r6.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00bd, code lost:
    
        if (r12.getBoolean("EnablePreviewChannel", r2) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:481:0x0a92, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L521;
     */
    /* JADX WARN: Code restructure failed: missing block: B:482:0x0a94, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r6).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x0a9a, code lost:
    
        if (r0.quiz == false) goto L519;
     */
    /* JADX WARN: Code restructure failed: missing block: B:485:0x0ab1, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedQuizChannel2", org.telegram.messenger.R.string.NotificationActionPinnedQuizChannel2, r5.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:487:0x0ac7, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPollChannel2", org.telegram.messenger.R.string.NotificationActionPinnedPollChannel2, r5.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:489:0x0aca, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L531;
     */
    /* JADX WARN: Code restructure failed: missing block: B:491:0x0ad0, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L529;
     */
    /* JADX WARN: Code restructure failed: missing block: B:493:0x0ad8, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L529;
     */
    /* JADX WARN: Code restructure failed: missing block: B:495:0x0afe, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r5.title, "🖼 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:497:0x0b0f, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPhotoChannel", org.telegram.messenger.R.string.NotificationActionPinnedPhotoChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:499:0x0b14, code lost:
    
        if ((r6 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L535;
     */
    /* JADX WARN: Code restructure failed: missing block: B:501:0x0b24, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGameChannel", org.telegram.messenger.R.string.NotificationActionPinnedGameChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x0b25, code lost:
    
        r3 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:503:0x0b27, code lost:
    
        if (r3 == null) goto L545;
     */
    /* JADX WARN: Code restructure failed: missing block: B:505:0x0b2d, code lost:
    
        if (r3.length() <= 0) goto L545;
     */
    /* JADX WARN: Code restructure failed: missing block: B:506:0x0b2f, code lost:
    
        r0 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:507:0x0b35, code lost:
    
        if (r0.length() <= 20) goto L542;
     */
    /* JADX WARN: Code restructure failed: missing block: B:508:0x0b37, code lost:
    
        r3 = new java.lang.StringBuilder();
        r9 = 0;
        r3.append((java.lang.Object) r0.subSequence(0, 20));
        r3.append("...");
        r0 = r3.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:509:0x0b4d, code lost:
    
        r1 = org.telegram.messenger.R.string.NotificationActionPinnedTextChannel;
        r2 = new java.lang.Object[2];
        r2[r9] = r5.title;
        r2[1] = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:510:0x0b5d, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", r1, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:511:0x0b4c, code lost:
    
        r9 = 0;
        r0 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:513:0x0b6e, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedNoTextChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:515:0x0b7f, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoChannel", org.telegram.messenger.R.string.NotificationActionPinnedGeoChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:516:0x0b80, code lost:
    
        r0 = r0.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x0b85, code lost:
    
        if (r0 == null) goto L553;
     */
    /* JADX WARN: Code restructure failed: missing block: B:519:0x0b99, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerEmojiChannel", org.telegram.messenger.R.string.NotificationActionPinnedStickerEmojiChannel, r5.title, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x0ba9, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerChannel", org.telegram.messenger.R.string.NotificationActionPinnedStickerChannel, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:522:0x0baa, code lost:
    
        r0 = r26.replyMessageObject;
     */
    /* JADX WARN: Code restructure failed: missing block: B:523:0x0bad, code lost:
    
        if (r0 != null) goto L559;
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0bbb, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextUser", org.telegram.messenger.R.string.NotificationActionPinnedNoTextUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:527:0x0bc0, code lost:
    
        if (r0.isMusic() == false) goto L563;
     */
    /* JADX WARN: Code restructure failed: missing block: B:529:0x0bce, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedMusicUser", org.telegram.messenger.R.string.NotificationActionPinnedMusicUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:531:0x0bd5, code lost:
    
        if (r0.isVideo() == false) goto L573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:533:0x0bdb, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L571;
     */
    /* JADX WARN: Code restructure failed: missing block: B:535:0x0be5, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L571;
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0c09, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextUser", org.telegram.messenger.R.string.NotificationActionPinnedTextUser, r7, "📹 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:539:0x0c18, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVideoUser", org.telegram.messenger.R.string.NotificationActionPinnedVideoUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:541:0x0c1d, code lost:
    
        if (r0.isGif() == false) goto L583;
     */
    /* JADX WARN: Code restructure failed: missing block: B:543:0x0c23, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L581;
     */
    /* JADX WARN: Code restructure failed: missing block: B:545:0x0c2d, code lost:
    
        if (android.text.TextUtils.isEmpty(r0.messageOwner.message) != false) goto L581;
     */
    /* JADX WARN: Code restructure failed: missing block: B:547:0x0c51, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextUser", org.telegram.messenger.R.string.NotificationActionPinnedTextUser, r7, "🎬 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:549:0x0c60, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGifUser", org.telegram.messenger.R.string.NotificationActionPinnedGifUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:551:0x0c67, code lost:
    
        if (r0.isVoice() == false) goto L587;
     */
    /* JADX WARN: Code restructure failed: missing block: B:553:0x0c75, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVoiceUser", org.telegram.messenger.R.string.NotificationActionPinnedVoiceUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:555:0x0c7a, code lost:
    
        if (r0.isRoundVideo() == false) goto L591;
     */
    /* JADX WARN: Code restructure failed: missing block: B:557:0x0c88, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedRoundUser", org.telegram.messenger.R.string.NotificationActionPinnedRoundUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:559:0x0c8d, code lost:
    
        if (r0.isSticker() != false) goto L655;
     */
    /* JADX WARN: Code restructure failed: missing block: B:561:0x0c93, code lost:
    
        if (r0.isAnimatedSticker() == false) goto L596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:562:0x0c97, code lost:
    
        r3 = r0.messageOwner;
        r5 = r3.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x0c9d, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L606;
     */
    /* JADX WARN: Code restructure failed: missing block: B:565:0x0ca3, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L604;
     */
    /* JADX WARN: Code restructure failed: missing block: B:567:0x0cab, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L604;
     */
    /* JADX WARN: Code restructure failed: missing block: B:569:0x0ccf, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextUser", org.telegram.messenger.R.string.NotificationActionPinnedTextUser, r7, "📎 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:571:0x0cde, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedFileUser", org.telegram.messenger.R.string.NotificationActionPinnedFileUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:573:0x0ce1, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L653;
     */
    /* JADX WARN: Code restructure failed: missing block: B:575:0x0ce5, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:577:0x0ceb, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L615;
     */
    /* JADX WARN: Code restructure failed: missing block: B:579:0x0cfb, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoLiveUser", org.telegram.messenger.R.string.NotificationActionPinnedGeoLiveUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:581:0x0cff, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L619;
     */
    /* JADX WARN: Code restructure failed: missing block: B:582:0x0d01, code lost:
    
        r5 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:583:0x0d1b, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedContactUser", org.telegram.messenger.R.string.NotificationActionPinnedContactUser, r7, org.telegram.messenger.ContactsController.formatName(r5.first_name, r5.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:585:0x0d1e, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:586:0x0d20, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r5).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:587:0x0d26, code lost:
    
        if (r0.quiz == false) goto L625;
     */
    /* JADX WARN: Code restructure failed: missing block: B:589:0x0d3b, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedQuizUser", org.telegram.messenger.R.string.NotificationActionPinnedQuizUser, r7, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:591:0x0d4f, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPollUser", org.telegram.messenger.R.string.NotificationActionPinnedPollUser, r7, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:593:0x0d52, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L637;
     */
    /* JADX WARN: Code restructure failed: missing block: B:595:0x0d58, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:597:0x0d60, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:599:0x0d84, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextUser", org.telegram.messenger.R.string.NotificationActionPinnedTextUser, r7, "🖼 " + r0.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:601:0x0d93, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPhotoUser", org.telegram.messenger.R.string.NotificationActionPinnedPhotoUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:603:0x0d98, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L641;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x0da6, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGameUser", org.telegram.messenger.R.string.NotificationActionPinnedGameUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:606:0x0da7, code lost:
    
        r3 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:607:0x0da9, code lost:
    
        if (r3 == null) goto L651;
     */
    /* JADX WARN: Code restructure failed: missing block: B:609:0x0daf, code lost:
    
        if (r3.length() <= 0) goto L651;
     */
    /* JADX WARN: Code restructure failed: missing block: B:610:0x0db1, code lost:
    
        r0 = r0.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:611:0x0db7, code lost:
    
        if (r0.length() <= 20) goto L648;
     */
    /* JADX WARN: Code restructure failed: missing block: B:612:0x0db9, code lost:
    
        r3 = new java.lang.StringBuilder();
        r5 = 0;
        r3.append((java.lang.Object) r0.subSequence(0, 20));
        r3.append("...");
        r0 = r3.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:613:0x0dcf, code lost:
    
        r1 = org.telegram.messenger.R.string.NotificationActionPinnedTextUser;
        r2 = new java.lang.Object[2];
        r2[r5] = r7;
        r2[1] = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:614:0x0ddd, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextUser", r1, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:615:0x0dce, code lost:
    
        r5 = 0;
        r0 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:617:0x0dec, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextUser", org.telegram.messenger.R.string.NotificationActionPinnedNoTextUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:619:0x0dfb, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoUser", org.telegram.messenger.R.string.NotificationActionPinnedGeoUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:620:0x0dfc, code lost:
    
        r0 = r0.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:621:0x0e02, code lost:
    
        if (r0 == null) goto L659;
     */
    /* JADX WARN: Code restructure failed: missing block: B:623:0x0e13, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerEmojiUser", org.telegram.messenger.R.string.NotificationActionPinnedStickerEmojiUser, r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:625:0x0e20, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerUser", org.telegram.messenger.R.string.NotificationActionPinnedStickerUser, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:627:0x0e23, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionSetChatTheme) == false) goto L674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:628:0x0e25, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageActionSetChatTheme) r2).emoticon;
     */
    /* JADX WARN: Code restructure failed: missing block: B:629:0x0e2d, code lost:
    
        if (android.text.TextUtils.isEmpty(r0) == false) goto L669;
     */
    /* JADX WARN: Code restructure failed: missing block: B:631:0x0e31, code lost:
    
        if (r3 != r19) goto L668;
     */
    /* JADX WARN: Code restructure failed: missing block: B:633:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChatThemeDisabledYou", org.telegram.messenger.R.string.ChatThemeDisabledYou, new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:635:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChatThemeDisabled", org.telegram.messenger.R.string.ChatThemeDisabled, r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:637:0x0e55, code lost:
    
        if (r3 != r19) goto L672;
     */
    /* JADX WARN: Code restructure failed: missing block: B:639:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChangedChatThemeYou", org.telegram.messenger.R.string.ChatThemeChangedYou, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:641:0x0e73, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChangedChatThemeTo", org.telegram.messenger.R.string.ChatThemeChangedTo, r7, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:643:0x0e76, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByRequest) == false) goto L678;
     */
    /* JADX WARN: Code restructure failed: missing block: B:645:0x0e7e, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:646:0x0e7f, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:648:0x0e89, code lost:
    
        if (r1.peer_id.channel_id == 0) goto L690;
     */
    /* JADX WARN: Code restructure failed: missing block: B:650:0x0e8d, code lost:
    
        if (r5.megagroup != false) goto L690;
     */
    /* JADX WARN: Code restructure failed: missing block: B:652:0x0e93, code lost:
    
        if (r26.isVideoAvatar() == false) goto L688;
     */
    /* JADX WARN: Code restructure failed: missing block: B:654:0x0ea5, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelVideoEditNotification", org.telegram.messenger.R.string.ChannelVideoEditNotification, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:656:0x0eb6, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelPhotoEditNotification", org.telegram.messenger.R.string.ChannelPhotoEditNotification, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:658:0x0ebc, code lost:
    
        if (r26.isVideoAvatar() == false) goto L694;
     */
    /* JADX WARN: Code restructure failed: missing block: B:660:0x0ed0, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupVideo", org.telegram.messenger.R.string.NotificationEditedGroupVideo, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:662:0x0ee3, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupPhoto", org.telegram.messenger.R.string.NotificationEditedGroupPhoto, r7, r5.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:664:0x0eea, code lost:
    
        return r26.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:666:0x0ef9, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationContactJoined", org.telegram.messenger.R.string.NotificationContactJoined, r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:668:0x0efe, code lost:
    
        if (r26.isMediaEmpty() == false) goto L708;
     */
    /* JADX WARN: Code restructure failed: missing block: B:670:0x0f08, code lost:
    
        if (android.text.TextUtils.isEmpty(r26.messageOwner.message) != false) goto L706;
     */
    /* JADX WARN: Code restructure failed: missing block: B:672:0x0f0e, code lost:
    
        return replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:674:0x0f17, code lost:
    
        return org.telegram.messenger.LocaleController.getString(r23, org.telegram.messenger.R.string.Message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:675:0x0f18, code lost:
    
        r1 = r23;
        r2 = r26.messageOwner;
     */
    /* JADX WARN: Code restructure failed: missing block: B:676:0x0f20, code lost:
    
        if ((r2.media instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L722;
     */
    /* JADX WARN: Code restructure failed: missing block: B:678:0x0f26, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L716;
     */
    /* JADX WARN: Code restructure failed: missing block: B:680:0x0f2e, code lost:
    
        if (android.text.TextUtils.isEmpty(r2.message) != false) goto L716;
     */
    /* JADX WARN: Code restructure failed: missing block: B:682:0x0f43, code lost:
    
        return "🖼 " + replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:684:0x0f4a, code lost:
    
        if (r26.messageOwner.media.ttl_seconds == 0) goto L720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:686:0x0f54, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachDestructingPhoto", org.telegram.messenger.R.string.AttachDestructingPhoto);
     */
    /* JADX WARN: Code restructure failed: missing block: B:688:0x0f5d, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachPhoto", org.telegram.messenger.R.string.AttachPhoto);
     */
    /* JADX WARN: Code restructure failed: missing block: B:690:0x0f62, code lost:
    
        if (r26.isVideo() == false) goto L736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:692:0x0f68, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L730;
     */
    /* JADX WARN: Code restructure failed: missing block: B:694:0x0f72, code lost:
    
        if (android.text.TextUtils.isEmpty(r26.messageOwner.message) != false) goto L730;
     */
    /* JADX WARN: Code restructure failed: missing block: B:696:0x0f87, code lost:
    
        return "📹 " + replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:698:0x0f8e, code lost:
    
        if (r26.messageOwner.media.ttl_seconds == 0) goto L734;
     */
    /* JADX WARN: Code restructure failed: missing block: B:700:0x0f98, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachDestructingVideo", org.telegram.messenger.R.string.AttachDestructingVideo);
     */
    /* JADX WARN: Code restructure failed: missing block: B:702:0x0fa1, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachVideo", org.telegram.messenger.R.string.AttachVideo);
     */
    /* JADX WARN: Code restructure failed: missing block: B:704:0x0fa6, code lost:
    
        if (r26.isGame() == false) goto L740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:706:0x0fb0, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachGame", org.telegram.messenger.R.string.AttachGame);
     */
    /* JADX WARN: Code restructure failed: missing block: B:708:0x0fb5, code lost:
    
        if (r26.isVoice() == false) goto L744;
     */
    /* JADX WARN: Code restructure failed: missing block: B:710:0x0fbf, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachAudio", org.telegram.messenger.R.string.AttachAudio);
     */
    /* JADX WARN: Code restructure failed: missing block: B:712:0x0fc4, code lost:
    
        if (r26.isRoundVideo() == false) goto L748;
     */
    /* JADX WARN: Code restructure failed: missing block: B:714:0x0fce, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachRound", org.telegram.messenger.R.string.AttachRound);
     */
    /* JADX WARN: Code restructure failed: missing block: B:716:0x0fd3, code lost:
    
        if (r26.isMusic() == false) goto L752;
     */
    /* JADX WARN: Code restructure failed: missing block: B:718:0x0fdd, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachMusic", org.telegram.messenger.R.string.AttachMusic);
     */
    /* JADX WARN: Code restructure failed: missing block: B:719:0x0fde, code lost:
    
        r2 = r26.messageOwner.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:720:0x0fe4, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L756;
     */
    /* JADX WARN: Code restructure failed: missing block: B:722:0x0fee, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachContact", org.telegram.messenger.R.string.AttachContact);
     */
    /* JADX WARN: Code restructure failed: missing block: B:724:0x0ff1, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L764;
     */
    /* JADX WARN: Code restructure failed: missing block: B:726:0x0ff9, code lost:
    
        if (((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r2).poll.quiz == false) goto L762;
     */
    /* JADX WARN: Code restructure failed: missing block: B:728:0x1003, code lost:
    
        return org.telegram.messenger.LocaleController.getString("QuizPoll", org.telegram.messenger.R.string.QuizPoll);
     */
    /* JADX WARN: Code restructure failed: missing block: B:730:0x100c, code lost:
    
        return org.telegram.messenger.LocaleController.getString("Poll", org.telegram.messenger.R.string.Poll);
     */
    /* JADX WARN: Code restructure failed: missing block: B:732:0x100f, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L810;
     */
    /* JADX WARN: Code restructure failed: missing block: B:734:0x1013, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L769;
     */
    /* JADX WARN: Code restructure failed: missing block: B:736:0x1019, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L773;
     */
    /* JADX WARN: Code restructure failed: missing block: B:738:0x1023, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachLiveLocation", org.telegram.messenger.R.string.AttachLiveLocation);
     */
    /* JADX WARN: Code restructure failed: missing block: B:740:0x1026, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L804;
     */
    /* JADX WARN: Code restructure failed: missing block: B:742:0x102c, code lost:
    
        if (r26.isSticker() != false) goto L798;
     */
    /* JADX WARN: Code restructure failed: missing block: B:744:0x1032, code lost:
    
        if (r26.isAnimatedSticker() == false) goto L780;
     */
    /* JADX WARN: Code restructure failed: missing block: B:746:0x1039, code lost:
    
        if (r26.isGif() == false) goto L790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:748:0x103f, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L788;
     */
    /* JADX WARN: Code restructure failed: missing block: B:750:0x1049, code lost:
    
        if (android.text.TextUtils.isEmpty(r26.messageOwner.message) != false) goto L788;
     */
    /* JADX WARN: Code restructure failed: missing block: B:752:0x105e, code lost:
    
        return "🎬 " + replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:754:0x1067, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachGif", org.telegram.messenger.R.string.AttachGif);
     */
    /* JADX WARN: Code restructure failed: missing block: B:756:0x106c, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L796;
     */
    /* JADX WARN: Code restructure failed: missing block: B:758:0x1076, code lost:
    
        if (android.text.TextUtils.isEmpty(r26.messageOwner.message) != false) goto L796;
     */
    /* JADX WARN: Code restructure failed: missing block: B:760:0x108b, code lost:
    
        return "📎 " + replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:762:0x1094, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachDocument", org.telegram.messenger.R.string.AttachDocument);
     */
    /* JADX WARN: Code restructure failed: missing block: B:763:0x1095, code lost:
    
        r0 = r26.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:764:0x1099, code lost:
    
        if (r0 == null) goto L802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:766:0x10b7, code lost:
    
        return r0 + " " + org.telegram.messenger.LocaleController.getString("AttachSticker", org.telegram.messenger.R.string.AttachSticker);
     */
    /* JADX WARN: Code restructure failed: missing block: B:768:0x10c0, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachSticker", org.telegram.messenger.R.string.AttachSticker);
     */
    /* JADX WARN: Code restructure failed: missing block: B:770:0x10c7, code lost:
    
        if (android.text.TextUtils.isEmpty(r26.messageText) != false) goto L808;
     */
    /* JADX WARN: Code restructure failed: missing block: B:772:0x10cd, code lost:
    
        return replaceSpoilers(r26);
     */
    /* JADX WARN: Code restructure failed: missing block: B:774:0x10d4, code lost:
    
        return org.telegram.messenger.LocaleController.getString(r1, org.telegram.messenger.R.string.Message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:776:0x10dd, code lost:
    
        return org.telegram.messenger.LocaleController.getString("AttachLocation", org.telegram.messenger.R.string.AttachLocation);
     */
    /* JADX WARN: Code restructure failed: missing block: B:780:0x024f, code lost:
    
        if (r12.getBoolean("EnablePreviewGroup", r6) != false) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:783:0x0259, code lost:
    
        if (r12.getBoolean(r24, r6) != false) goto L160;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getShortStringForMessage(org.telegram.messenger.MessageObject r26, java.lang.String[] r27, boolean[] r28) {
        /*
            Method dump skipped, instructions count: 4339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.getShortStringForMessage(org.telegram.messenger.MessageObject, java.lang.String[], boolean[]):java.lang.String");
    }

    private String replaceSpoilers(MessageObject messageObject) {
        TLRPC$Message tLRPC$Message;
        String str;
        if (messageObject == null || (tLRPC$Message = messageObject.messageOwner) == null || (str = tLRPC$Message.message) == null || tLRPC$Message.entities == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < messageObject.messageOwner.entities.size(); i++) {
            if (messageObject.messageOwner.entities.get(i) instanceof TLRPC$TL_messageEntitySpoiler) {
                TLRPC$TL_messageEntitySpoiler tLRPC$TL_messageEntitySpoiler = (TLRPC$TL_messageEntitySpoiler) messageObject.messageOwner.entities.get(i);
                for (int i2 = 0; i2 < tLRPC$TL_messageEntitySpoiler.length; i2++) {
                    int i3 = tLRPC$TL_messageEntitySpoiler.offset + i2;
                    char[] cArr = this.spoilerChars;
                    sb.setCharAt(i3, cArr[i2 % cArr.length]);
                }
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:279:0x0608, code lost:
    
        if (r12.getBoolean(r22, true) == false) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x0616, code lost:
    
        r8 = r27.messageOwner;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x061a, code lost:
    
        if ((r8 instanceof org.telegram.tgnet.TLRPC$TL_messageService) == false) goto L588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:282:0x061c, code lost:
    
        r9 = r8.action;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x0620, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser) == false) goto L310;
     */
    /* JADX WARN: Code restructure failed: missing block: B:284:0x0622, code lost:
    
        r3 = r9.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:285:0x0628, code lost:
    
        if (r3 != 0) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0631, code lost:
    
        if (r9.users.size() != 1) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0633, code lost:
    
        r3 = r27.messageOwner.action.users.get(0).longValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x0648, code lost:
    
        if (r3 == 0) goto L299;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0652, code lost:
    
        if (r27.messageOwner.peer_id.channel_id == 0) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x0656, code lost:
    
        if (r14.megagroup != false) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x0658, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChannelAddedByNotification", org.telegram.messenger.R.string.ChannelAddedByNotification, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x066f, code lost:
    
        if (r3 != r19) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x0671, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationInvitedToGroup", org.telegram.messenger.R.string.NotificationInvitedToGroup, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x0686, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0692, code lost:
    
        if (r0 != null) goto L292;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0694, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x069a, code lost:
    
        if (r24 != r0.id) goto L298;
     */
    /* JADX WARN: Code restructure failed: missing block: B:305:0x069e, code lost:
    
        if (r14.megagroup == false) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x06a0, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationGroupAddSelfMega", org.telegram.messenger.R.string.NotificationGroupAddSelfMega, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x06b5, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationGroupAddSelf", org.telegram.messenger.R.string.NotificationGroupAddSelf, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x06ca, code lost:
    
        r0 = org.telegram.messenger.LocaleController.getString("JMTWelcomeJoin", org.telegram.messenger.R.string.JMTWelcomeJoin);
     */
    /* JADX WARN: Code restructure failed: missing block: B:309:0x06d4, code lost:
    
        r1 = new java.lang.StringBuilder();
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:311:0x06e4, code lost:
    
        if (r11 >= r27.messageOwner.action.users.size()) goto L778;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x06e6, code lost:
    
        r2 = getMessagesController().getUser(r27.messageOwner.action.users.get(r11));
     */
    /* JADX WARN: Code restructure failed: missing block: B:313:0x06fa, code lost:
    
        if (r2 == null) goto L780;
     */
    /* JADX WARN: Code restructure failed: missing block: B:314:0x06fc, code lost:
    
        r2 = org.telegram.messenger.UserObject.getUserName(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x0704, code lost:
    
        if (r1.length() == 0) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x0706, code lost:
    
        r1.append(", ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:317:0x070b, code lost:
    
        r1.append(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x070e, code lost:
    
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x0711, code lost:
    
        r0 = org.telegram.messenger.LocaleController.getString("JMTWelcomeJoin", org.telegram.messenger.R.string.JMTWelcomeJoin);
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x071d, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGroupCall) == false) goto L313;
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupCreatedCall", org.telegram.messenger.R.string.NotificationGroupCreatedCall, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0736, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGroupCallScheduled) == false) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:330:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x0742, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionInviteToGroupCall) == false) goto L344;
     */
    /* JADX WARN: Code restructure failed: missing block: B:333:0x0744, code lost:
    
        r3 = r9.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:0x074a, code lost:
    
        if (r3 != 0) goto L323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x0753, code lost:
    
        if (r9.users.size() != 1) goto L323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:337:0x0755, code lost:
    
        r3 = r27.messageOwner.action.users.get(0).longValue();
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x076a, code lost:
    
        if (r3 == 0) goto L333;
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:0x076e, code lost:
    
        if (r3 != r19) goto L328;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x0770, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedYouToCall", org.telegram.messenger.R.string.NotificationGroupInvitedYouToCall, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x0785, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x0791, code lost:
    
        if (r0 != null) goto L332;
     */
    /* JADX WARN: Code restructure failed: missing block: B:345:0x0793, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:346:0x0795, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedToCall", org.telegram.messenger.R.string.NotificationGroupInvitedToCall, r2, r14.title, org.telegram.messenger.UserObject.getUserName(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:347:0x07b0, code lost:
    
        r1 = new java.lang.StringBuilder();
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x07c0, code lost:
    
        if (r3 >= r27.messageOwner.action.users.size()) goto L781;
     */
    /* JADX WARN: Code restructure failed: missing block: B:350:0x07c2, code lost:
    
        r4 = getMessagesController().getUser(r27.messageOwner.action.users.get(r3));
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x07d6, code lost:
    
        if (r4 == null) goto L783;
     */
    /* JADX WARN: Code restructure failed: missing block: B:352:0x07d8, code lost:
    
        r4 = org.telegram.messenger.UserObject.getUserName(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:353:0x07e0, code lost:
    
        if (r1.length() == 0) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x07e2, code lost:
    
        r1.append(", ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x07e7, code lost:
    
        r1.append(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x07ea, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x07ed, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationGroupInvitedToCall", org.telegram.messenger.R.string.NotificationGroupInvitedToCall, r2, r14.title, r1.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x080b, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByLink) == false) goto L347;
     */
    /* JADX WARN: Code restructure failed: missing block: B:364:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationInvitedToGroupByLink", org.telegram.messenger.R.string.NotificationInvitedToGroupByLink, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:366:0x0825, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatEditTitle) == false) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupName", org.telegram.messenger.R.string.NotificationEditedGroupName, r2, r9.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x083b, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatEditPhoto) != false) goto L576;
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x083f, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeletePhoto) == false) goto L355;
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x0845, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser) == false) goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:375:0x0847, code lost:
    
        r3 = r9.user_id;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x084b, code lost:
    
        if (r3 != r19) goto L360;
     */
    /* JADX WARN: Code restructure failed: missing block: B:378:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupKickYou", org.telegram.messenger.R.string.NotificationGroupKickYou, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x0867, code lost:
    
        if (r3 != r24) goto L363;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupLeftMember", org.telegram.messenger.R.string.NotificationGroupLeftMember, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x087b, code lost:
    
        r0 = getMessagesController().getUser(java.lang.Long.valueOf(r27.messageOwner.action.user_id));
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x088d, code lost:
    
        if (r0 != null) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x088f, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationGroupKickMember", org.telegram.messenger.R.string.NotificationGroupKickMember, r2, r14.title, org.telegram.messenger.UserObject.getUserName(r0));
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x08ac, code lost:
    
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x08af, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatCreate) == false) goto L371;
     */
    /* JADX WARN: Code restructure failed: missing block: B:391:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x08bb, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate) == false) goto L374;
     */
    /* JADX WARN: Code restructure failed: missing block: B:395:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x08c7, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo) == false) goto L377;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ActionMigrateFromGroupNotify", org.telegram.messenger.R.string.ActionMigrateFromGroupNotify, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x08df, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChannelMigrateFrom) == false) goto L380;
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ActionMigrateFromGroupNotify", org.telegram.messenger.R.string.ActionMigrateFromGroupNotify, r9.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x08f3, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionScreenshotTaken) == false) goto L383;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x08ff, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionPinMessage) == false) goto L558;
     */
    /* JADX WARN: Code restructure failed: missing block: B:411:0x0907, code lost:
    
        if (org.telegram.messenger.ChatObject.isChannel(r14) == false) goto L474;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x090b, code lost:
    
        if (r14.megagroup == false) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:414:0x090f, code lost:
    
        r2 = r27.replyMessageObject;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:0x0911, code lost:
    
        if (r2 != null) goto L393;
     */
    /* JADX WARN: Code restructure failed: missing block: B:417:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedNoTextChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x092b, code lost:
    
        if (r2.isMusic() == false) goto L396;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x092d, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedMusicChannel", org.telegram.messenger.R.string.NotificationActionPinnedMusicChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:0x0943, code lost:
    
        if (r2.isVideo() == false) goto L404;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x0949, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x0953, code lost:
    
        if (android.text.TextUtils.isEmpty(r2.messageOwner.message) != false) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x0955, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r14.title, "📹 " + r2.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x097b, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVideoChannel", org.telegram.messenger.R.string.NotificationActionPinnedVideoChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x0991, code lost:
    
        if (r2.isGif() == false) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x0997, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x09a1, code lost:
    
        if (android.text.TextUtils.isEmpty(r2.messageOwner.message) != false) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x09a3, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r14.title, "🎬 " + r2.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x09c9, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGifChannel", org.telegram.messenger.R.string.NotificationActionPinnedGifChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x09e1, code lost:
    
        if (r2.isVoice() == false) goto L415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:439:0x09e3, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVoiceChannel", org.telegram.messenger.R.string.NotificationActionPinnedVoiceChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:441:0x09f7, code lost:
    
        if (r2.isRoundVideo() == false) goto L418;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:0x09f9, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedRoundChannel", org.telegram.messenger.R.string.NotificationActionPinnedRoundChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:444:0x0a0d, code lost:
    
        if (r2.isSticker() != false) goto L470;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x0a13, code lost:
    
        if (r2.isAnimatedSticker() == false) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:447:0x0a17, code lost:
    
        r3 = r2.messageOwner;
        r5 = r3.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:448:0x0a1d, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L431;
     */
    /* JADX WARN: Code restructure failed: missing block: B:450:0x0a23, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L430;
     */
    /* JADX WARN: Code restructure failed: missing block: B:452:0x0a2b, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L430;
     */
    /* JADX WARN: Code restructure failed: missing block: B:453:0x0a2d, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r14.title, "📎 " + r2.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:454:0x0a53, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedFileChannel", org.telegram.messenger.R.string.NotificationActionPinnedFileChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:456:0x0a67, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L469;
     */
    /* JADX WARN: Code restructure failed: missing block: B:458:0x0a6b, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L436;
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x0a71, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L439;
     */
    /* JADX WARN: Code restructure failed: missing block: B:461:0x0a73, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoLiveChannel", org.telegram.messenger.R.string.NotificationActionPinnedGeoLiveChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x0a87, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L442;
     */
    /* JADX WARN: Code restructure failed: missing block: B:464:0x0a89, code lost:
    
        r0 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r27.messageOwner.media;
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedContactChannel2", org.telegram.messenger.R.string.NotificationActionPinnedContactChannel2, r14.title, org.telegram.messenger.ContactsController.formatName(r0.first_name, r0.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:466:0x0aae, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x0ab0, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r5).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:468:0x0ab6, code lost:
    
        if (r0.quiz == false) goto L447;
     */
    /* JADX WARN: Code restructure failed: missing block: B:469:0x0ab8, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedQuizChannel2", org.telegram.messenger.R.string.NotificationActionPinnedQuizChannel2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:470:0x0acf, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPollChannel2", org.telegram.messenger.R.string.NotificationActionPinnedPollChannel2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:472:0x0ae8, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:474:0x0aee, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L455;
     */
    /* JADX WARN: Code restructure failed: missing block: B:476:0x0af6, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L455;
     */
    /* JADX WARN: Code restructure failed: missing block: B:477:0x0af8, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedTextChannel, r14.title, "🖼 " + r2.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:478:0x0b1e, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPhotoChannel", org.telegram.messenger.R.string.NotificationActionPinnedPhotoChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:480:0x0b34, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:481:0x0b36, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGameChannel", org.telegram.messenger.R.string.NotificationActionPinnedGameChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:482:0x0b46, code lost:
    
        r0 = r2.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x0b48, code lost:
    
        if (r0 == null) goto L468;
     */
    /* JADX WARN: Code restructure failed: missing block: B:485:0x0b4e, code lost:
    
        if (r0.length() <= 0) goto L468;
     */
    /* JADX WARN: Code restructure failed: missing block: B:486:0x0b50, code lost:
    
        r0 = r2.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:487:0x0b56, code lost:
    
        if (r0.length() <= 20) goto L466;
     */
    /* JADX WARN: Code restructure failed: missing block: B:488:0x0b58, code lost:
    
        r1 = new java.lang.StringBuilder();
        r3 = 0;
        r1.append((java.lang.Object) r0.subSequence(0, 20));
        r1.append("...");
        r0 = r1.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:489:0x0b70, code lost:
    
        r1 = org.telegram.messenger.R.string.NotificationActionPinnedTextChannel;
        r2 = new java.lang.Object[2];
        r2[r3] = r14.title;
        r2[1] = r0;
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedTextChannel", r1, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:490:0x0b6f, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:491:0x0b82, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoTextChannel", org.telegram.messenger.R.string.NotificationActionPinnedNoTextChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:492:0x0b94, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoChannel", org.telegram.messenger.R.string.NotificationActionPinnedGeoChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:493:0x0ba6, code lost:
    
        r0 = r2.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:494:0x0bab, code lost:
    
        if (r0 == null) goto L473;
     */
    /* JADX WARN: Code restructure failed: missing block: B:495:0x0bad, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerEmojiChannel", org.telegram.messenger.R.string.NotificationActionPinnedStickerEmojiChannel, r14.title, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:496:0x0bc1, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerChannel", org.telegram.messenger.R.string.NotificationActionPinnedStickerChannel, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:497:0x0bd2, code lost:
    
        r8 = r27.replyMessageObject;
     */
    /* JADX WARN: Code restructure failed: missing block: B:498:0x0bd5, code lost:
    
        if (r8 != null) goto L477;
     */
    /* JADX WARN: Code restructure failed: missing block: B:500:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoText", org.telegram.messenger.R.string.NotificationActionPinnedNoText, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:502:0x0bf1, code lost:
    
        if (r8.isMusic() == false) goto L480;
     */
    /* JADX WARN: Code restructure failed: missing block: B:503:0x0bf3, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedMusic", org.telegram.messenger.R.string.NotificationActionPinnedMusic, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:505:0x0c0b, code lost:
    
        if (r8.isVideo() == false) goto L488;
     */
    /* JADX WARN: Code restructure failed: missing block: B:507:0x0c11, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L487;
     */
    /* JADX WARN: Code restructure failed: missing block: B:509:0x0c1b, code lost:
    
        if (android.text.TextUtils.isEmpty(r8.messageOwner.message) != false) goto L487;
     */
    /* JADX WARN: Code restructure failed: missing block: B:510:0x0c1d, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r2, "📹 " + r8.messageOwner.message, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:511:0x0c45, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVideo", org.telegram.messenger.R.string.NotificationActionPinnedVideo, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:513:0x0c5e, code lost:
    
        if (r8.isGif() == false) goto L496;
     */
    /* JADX WARN: Code restructure failed: missing block: B:515:0x0c64, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L495;
     */
    /* JADX WARN: Code restructure failed: missing block: B:517:0x0c6e, code lost:
    
        if (android.text.TextUtils.isEmpty(r8.messageOwner.message) != false) goto L495;
     */
    /* JADX WARN: Code restructure failed: missing block: B:518:0x0c70, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r2, "🎬 " + r8.messageOwner.message, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:519:0x0c98, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGif", org.telegram.messenger.R.string.NotificationActionPinnedGif, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:521:0x0cb4, code lost:
    
        if (r8.isVoice() == false) goto L499;
     */
    /* JADX WARN: Code restructure failed: missing block: B:522:0x0cb6, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedVoice", org.telegram.messenger.R.string.NotificationActionPinnedVoice, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:524:0x0ccc, code lost:
    
        if (r8.isRoundVideo() == false) goto L502;
     */
    /* JADX WARN: Code restructure failed: missing block: B:525:0x0cce, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedRound", org.telegram.messenger.R.string.NotificationActionPinnedRound, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:527:0x0ce4, code lost:
    
        if (r8.isSticker() != false) goto L554;
     */
    /* JADX WARN: Code restructure failed: missing block: B:529:0x0cea, code lost:
    
        if (r8.isAnimatedSticker() == false) goto L507;
     */
    /* JADX WARN: Code restructure failed: missing block: B:530:0x0cee, code lost:
    
        r3 = r8.messageOwner;
        r5 = r3.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:531:0x0cf4, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L515;
     */
    /* JADX WARN: Code restructure failed: missing block: B:533:0x0cfa, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L514;
     */
    /* JADX WARN: Code restructure failed: missing block: B:535:0x0d02, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L514;
     */
    /* JADX WARN: Code restructure failed: missing block: B:536:0x0d04, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r2, "📎 " + r8.messageOwner.message, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:537:0x0d2c, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedFile", org.telegram.messenger.R.string.NotificationActionPinnedFile, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:539:0x0d43, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L553;
     */
    /* JADX WARN: Code restructure failed: missing block: B:541:0x0d47, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L520;
     */
    /* JADX WARN: Code restructure failed: missing block: B:543:0x0d4d, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L523;
     */
    /* JADX WARN: Code restructure failed: missing block: B:544:0x0d4f, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeoLive", org.telegram.messenger.R.string.NotificationActionPinnedGeoLive, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:546:0x0d66, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L526;
     */
    /* JADX WARN: Code restructure failed: missing block: B:547:0x0d68, code lost:
    
        r0 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r27.messageOwner.media;
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedContact2", org.telegram.messenger.R.string.NotificationActionPinnedContact2, r2, r14.title, org.telegram.messenger.ContactsController.formatName(r0.first_name, r0.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:549:0x0d8f, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L532;
     */
    /* JADX WARN: Code restructure failed: missing block: B:550:0x0d91, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r5).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:551:0x0d97, code lost:
    
        if (r0.quiz == false) goto L531;
     */
    /* JADX WARN: Code restructure failed: missing block: B:552:0x0d99, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedQuiz2", org.telegram.messenger.R.string.NotificationActionPinnedQuiz2, r2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:553:0x0db2, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPoll2", org.telegram.messenger.R.string.NotificationActionPinnedPoll2, r2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:555:0x0dcd, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L540;
     */
    /* JADX WARN: Code restructure failed: missing block: B:557:0x0dd3, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L539;
     */
    /* JADX WARN: Code restructure failed: missing block: B:559:0x0ddb, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L539;
     */
    /* JADX WARN: Code restructure failed: missing block: B:560:0x0ddd, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", org.telegram.messenger.R.string.NotificationActionPinnedText, r2, "🖼 " + r8.messageOwner.message, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:561:0x0e05, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedPhoto", org.telegram.messenger.R.string.NotificationActionPinnedPhoto, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x0e1f, code lost:
    
        if ((r5 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L543;
     */
    /* JADX WARN: Code restructure failed: missing block: B:564:0x0e21, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGame", org.telegram.messenger.R.string.NotificationActionPinnedGame, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:565:0x0e33, code lost:
    
        r0 = r8.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:566:0x0e35, code lost:
    
        if (r0 == null) goto L552;
     */
    /* JADX WARN: Code restructure failed: missing block: B:568:0x0e3b, code lost:
    
        if (r0.length() <= 0) goto L552;
     */
    /* JADX WARN: Code restructure failed: missing block: B:569:0x0e3d, code lost:
    
        r0 = r8.messageText;
     */
    /* JADX WARN: Code restructure failed: missing block: B:570:0x0e43, code lost:
    
        if (r0.length() <= 20) goto L550;
     */
    /* JADX WARN: Code restructure failed: missing block: B:571:0x0e45, code lost:
    
        r1 = new java.lang.StringBuilder();
        r3 = 0;
        r1.append((java.lang.Object) r0.subSequence(0, 20));
        r1.append("...");
        r0 = r1.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x0e5d, code lost:
    
        r1 = org.telegram.messenger.R.string.NotificationActionPinnedText;
        r4 = new java.lang.Object[3];
        r4[r3] = r2;
        r4[1] = r0;
        r4[2] = r14.title;
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedText", r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:573:0x0e5c, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:574:0x0e71, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedNoText", org.telegram.messenger.R.string.NotificationActionPinnedNoText, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:575:0x0e86, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedGeo", org.telegram.messenger.R.string.NotificationActionPinnedGeo, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:576:0x0e9b, code lost:
    
        r0 = r8.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:577:0x0ea1, code lost:
    
        if (r0 == null) goto L557;
     */
    /* JADX WARN: Code restructure failed: missing block: B:578:0x0ea3, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedStickerEmoji", org.telegram.messenger.R.string.NotificationActionPinnedStickerEmoji, r2, r14.title, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:579:0x0eb8, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationActionPinnedSticker", org.telegram.messenger.R.string.NotificationActionPinnedSticker, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:581:0x0ecd, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGameScore) == false) goto L561;
     */
    /* JADX WARN: Code restructure failed: missing block: B:583:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:585:0x0ed9, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionSetChatTheme) == false) goto L573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:586:0x0edb, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageActionSetChatTheme) r9).emoticon;
     */
    /* JADX WARN: Code restructure failed: missing block: B:587:0x0ee3, code lost:
    
        if (android.text.TextUtils.isEmpty(r0) == false) goto L569;
     */
    /* JADX WARN: Code restructure failed: missing block: B:589:0x0ee7, code lost:
    
        if (r3 != r19) goto L568;
     */
    /* JADX WARN: Code restructure failed: missing block: B:590:0x0ee9, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChatThemeDisabledYou", org.telegram.messenger.R.string.ChatThemeDisabledYou, new java.lang.Object[0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:591:0x0ef6, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChatThemeDisabled", org.telegram.messenger.R.string.ChatThemeDisabled, r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:593:0x0f0d, code lost:
    
        if (r3 != r19) goto L572;
     */
    /* JADX WARN: Code restructure failed: missing block: B:594:0x0f0f, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChangedChatThemeYou", org.telegram.messenger.R.string.ChatThemeChangedYou, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:595:0x0f1d, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChangedChatThemeTo", org.telegram.messenger.R.string.ChatThemeChangedTo, r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:597:0x0f30, code lost:
    
        if ((r9 instanceof org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByRequest) == false) goto L774;
     */
    /* JADX WARN: Code restructure failed: missing block: B:599:?, code lost:
    
        return r27.messageText.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:601:0x0f42, code lost:
    
        if (r8.peer_id.channel_id == 0) goto L584;
     */
    /* JADX WARN: Code restructure failed: missing block: B:603:0x0f46, code lost:
    
        if (r14.megagroup != false) goto L584;
     */
    /* JADX WARN: Code restructure failed: missing block: B:605:0x0f4c, code lost:
    
        if (r27.isVideoAvatar() == false) goto L583;
     */
    /* JADX WARN: Code restructure failed: missing block: B:607:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelVideoEditNotification", org.telegram.messenger.R.string.ChannelVideoEditNotification, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:609:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelPhotoEditNotification", org.telegram.messenger.R.string.ChannelPhotoEditNotification, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:611:0x0f77, code lost:
    
        if (r27.isVideoAvatar() == false) goto L587;
     */
    /* JADX WARN: Code restructure failed: missing block: B:613:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupVideo", org.telegram.messenger.R.string.NotificationEditedGroupVideo, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:615:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationEditedGroupPhoto", org.telegram.messenger.R.string.NotificationEditedGroupPhoto, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:617:0x0fa5, code lost:
    
        if (org.telegram.messenger.ChatObject.isChannel(r14) == false) goto L677;
     */
    /* JADX WARN: Code restructure failed: missing block: B:619:0x0fa9, code lost:
    
        if (r14.megagroup != false) goto L677;
     */
    /* JADX WARN: Code restructure failed: missing block: B:621:0x0faf, code lost:
    
        if (r27.isMediaEmpty() == false) goto L599;
     */
    /* JADX WARN: Code restructure failed: missing block: B:622:0x0fb1, code lost:
    
        if (r28 != false) goto L598;
     */
    /* JADX WARN: Code restructure failed: missing block: B:624:0x0fbb, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L598;
     */
    /* JADX WARN: Code restructure failed: missing block: B:625:0x0fbd, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, r27.messageOwner.message);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:626:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:628:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageNoText", org.telegram.messenger.R.string.ChannelMessageNoText, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:629:0x0fe4, code lost:
    
        r4 = r27.messageOwner;
     */
    /* JADX WARN: Code restructure failed: missing block: B:630:0x0fec, code lost:
    
        if ((r4.media instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L608;
     */
    /* JADX WARN: Code restructure failed: missing block: B:631:0x0fee, code lost:
    
        if (r28 != false) goto L607;
     */
    /* JADX WARN: Code restructure failed: missing block: B:633:0x0ff4, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L607;
     */
    /* JADX WARN: Code restructure failed: missing block: B:635:0x0ffc, code lost:
    
        if (android.text.TextUtils.isEmpty(r4.message) != false) goto L607;
     */
    /* JADX WARN: Code restructure failed: missing block: B:636:0x0ffe, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, "🖼 " + r27.messageOwner.message);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:637:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:639:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessagePhoto", org.telegram.messenger.R.string.ChannelMessagePhoto, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:641:0x1038, code lost:
    
        if (r27.isVideo() == false) goto L617;
     */
    /* JADX WARN: Code restructure failed: missing block: B:642:0x103a, code lost:
    
        if (r28 != false) goto L616;
     */
    /* JADX WARN: Code restructure failed: missing block: B:644:0x1040, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L616;
     */
    /* JADX WARN: Code restructure failed: missing block: B:646:0x104a, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L616;
     */
    /* JADX WARN: Code restructure failed: missing block: B:647:0x104c, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, "📹 " + r27.messageOwner.message);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:648:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:650:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageVideo", org.telegram.messenger.R.string.ChannelMessageVideo, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:652:0x1088, code lost:
    
        if (r27.isVoice() == false) goto L620;
     */
    /* JADX WARN: Code restructure failed: missing block: B:654:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageAudio", org.telegram.messenger.R.string.ChannelMessageAudio, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:656:0x109c, code lost:
    
        if (r27.isRoundVideo() == false) goto L623;
     */
    /* JADX WARN: Code restructure failed: missing block: B:658:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageRound", org.telegram.messenger.R.string.ChannelMessageRound, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:660:0x10b0, code lost:
    
        if (r27.isMusic() == false) goto L626;
     */
    /* JADX WARN: Code restructure failed: missing block: B:662:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageMusic", org.telegram.messenger.R.string.ChannelMessageMusic, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:663:0x10c0, code lost:
    
        r1 = r27.messageOwner.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:664:0x10c6, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L629;
     */
    /* JADX WARN: Code restructure failed: missing block: B:665:0x10c8, code lost:
    
        r1 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:666:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageContact2", org.telegram.messenger.R.string.ChannelMessageContact2, r2, org.telegram.messenger.ContactsController.formatName(r1.first_name, r1.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:668:0x10e6, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:669:0x10e8, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r1).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:670:0x10ee, code lost:
    
        if (r0.quiz == false) goto L634;
     */
    /* JADX WARN: Code restructure failed: missing block: B:671:0x10f0, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChannelMessageQuiz2", org.telegram.messenger.R.string.ChannelMessageQuiz2, r2, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:672:0x1105, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChannelMessagePoll2", org.telegram.messenger.R.string.ChannelMessagePoll2, r2, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:674:0x111c, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L676;
     */
    /* JADX WARN: Code restructure failed: missing block: B:676:0x1120, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L640;
     */
    /* JADX WARN: Code restructure failed: missing block: B:678:0x1126, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L643;
     */
    /* JADX WARN: Code restructure failed: missing block: B:680:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageLiveLocation", org.telegram.messenger.R.string.ChannelMessageLiveLocation, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:682:0x113a, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L670;
     */
    /* JADX WARN: Code restructure failed: missing block: B:684:0x1140, code lost:
    
        if (r27.isSticker() != false) goto L666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:686:0x1146, code lost:
    
        if (r27.isAnimatedSticker() == false) goto L650;
     */
    /* JADX WARN: Code restructure failed: missing block: B:688:0x114e, code lost:
    
        if (r27.isGif() == false) goto L659;
     */
    /* JADX WARN: Code restructure failed: missing block: B:689:0x1150, code lost:
    
        if (r28 != false) goto L658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:691:0x1156, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:693:0x1160, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:694:0x1162, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, "🎬 " + r27.messageOwner.message);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:695:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:697:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageGIF", org.telegram.messenger.R.string.ChannelMessageGIF, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:698:0x1198, code lost:
    
        if (r28 != false) goto L665;
     */
    /* JADX WARN: Code restructure failed: missing block: B:700:0x119e, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L665;
     */
    /* JADX WARN: Code restructure failed: missing block: B:702:0x11a8, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L665;
     */
    /* JADX WARN: Code restructure failed: missing block: B:703:0x11aa, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, "📎 " + r27.messageOwner.message);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:704:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:706:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageDocument", org.telegram.messenger.R.string.ChannelMessageDocument, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:707:0x11e0, code lost:
    
        r0 = r27.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:708:0x11e6, code lost:
    
        if (r0 == null) goto L669;
     */
    /* JADX WARN: Code restructure failed: missing block: B:709:0x11e8, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChannelMessageStickerEmoji", org.telegram.messenger.R.string.ChannelMessageStickerEmoji, r2, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:710:0x11f9, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("ChannelMessageSticker", org.telegram.messenger.R.string.ChannelMessageSticker, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:712:0x1208, code lost:
    
        if (r28 != false) goto L675;
     */
    /* JADX WARN: Code restructure failed: missing block: B:714:0x1210, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageText) != false) goto L675;
     */
    /* JADX WARN: Code restructure failed: missing block: B:715:0x1212, code lost:
    
        r14 = org.telegram.messenger.LocaleController.formatString("NotificationMessageText", org.telegram.messenger.R.string.NotificationMessageText, r2, r27.messageText);
        r29[0] = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:716:?, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:718:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageNoText", org.telegram.messenger.R.string.ChannelMessageNoText, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:720:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("ChannelMessageMap", org.telegram.messenger.R.string.ChannelMessageMap, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:722:0x1249, code lost:
    
        if (r27.isMediaEmpty() == false) goto L684;
     */
    /* JADX WARN: Code restructure failed: missing block: B:723:0x124b, code lost:
    
        if (r28 != false) goto L683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:725:0x1255, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:727:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, r27.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:729:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupNoText", org.telegram.messenger.R.string.NotificationMessageGroupNoText, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:730:0x1285, code lost:
    
        r3 = r27.messageOwner;
     */
    /* JADX WARN: Code restructure failed: missing block: B:731:0x128d, code lost:
    
        if ((r3.media instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPhoto) == false) goto L693;
     */
    /* JADX WARN: Code restructure failed: missing block: B:732:0x128f, code lost:
    
        if (r28 != false) goto L692;
     */
    /* JADX WARN: Code restructure failed: missing block: B:734:0x1295, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L692;
     */
    /* JADX WARN: Code restructure failed: missing block: B:736:0x129d, code lost:
    
        if (android.text.TextUtils.isEmpty(r3.message) != false) goto L692;
     */
    /* JADX WARN: Code restructure failed: missing block: B:738:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, "🖼 " + r27.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:740:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupPhoto", org.telegram.messenger.R.string.NotificationMessageGroupPhoto, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:742:0x12e0, code lost:
    
        if (r27.isVideo() == false) goto L702;
     */
    /* JADX WARN: Code restructure failed: missing block: B:743:0x12e2, code lost:
    
        if (r28 != false) goto L701;
     */
    /* JADX WARN: Code restructure failed: missing block: B:745:0x12e8, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L701;
     */
    /* JADX WARN: Code restructure failed: missing block: B:747:0x12f2, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L701;
     */
    /* JADX WARN: Code restructure failed: missing block: B:749:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, "📹 " + r27.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:751:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString(" ", org.telegram.messenger.R.string.NotificationMessageGroupVideo, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:753:0x1338, code lost:
    
        if (r27.isVoice() == false) goto L705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:755:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupAudio", org.telegram.messenger.R.string.NotificationMessageGroupAudio, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:757:0x1350, code lost:
    
        if (r27.isRoundVideo() == false) goto L708;
     */
    /* JADX WARN: Code restructure failed: missing block: B:759:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupRound", org.telegram.messenger.R.string.NotificationMessageGroupRound, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:761:0x1368, code lost:
    
        if (r27.isMusic() == false) goto L711;
     */
    /* JADX WARN: Code restructure failed: missing block: B:763:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupMusic", org.telegram.messenger.R.string.NotificationMessageGroupMusic, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:764:0x137c, code lost:
    
        r1 = r27.messageOwner.media;
     */
    /* JADX WARN: Code restructure failed: missing block: B:765:0x1382, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaContact) == false) goto L714;
     */
    /* JADX WARN: Code restructure failed: missing block: B:766:0x1384, code lost:
    
        r1 = (org.telegram.tgnet.TLRPC$TL_messageMediaContact) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:767:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupContact2", org.telegram.messenger.R.string.NotificationMessageGroupContact2, r2, r14.title, org.telegram.messenger.ContactsController.formatName(r1.first_name, r1.last_name));
     */
    /* JADX WARN: Code restructure failed: missing block: B:769:0x13a7, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaPoll) == false) goto L720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:770:0x13a9, code lost:
    
        r0 = ((org.telegram.tgnet.TLRPC$TL_messageMediaPoll) r1).poll;
     */
    /* JADX WARN: Code restructure failed: missing block: B:771:0x13af, code lost:
    
        if (r0.quiz == false) goto L719;
     */
    /* JADX WARN: Code restructure failed: missing block: B:772:0x13b1, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupQuiz2", org.telegram.messenger.R.string.NotificationMessageGroupQuiz2, r2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:773:0x13ca, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupPoll2", org.telegram.messenger.R.string.NotificationMessageGroupPoll2, r2, r14.title, r0.question);
     */
    /* JADX WARN: Code restructure failed: missing block: B:775:0x13e5, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame) == false) goto L723;
     */
    /* JADX WARN: Code restructure failed: missing block: B:777:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupGame", org.telegram.messenger.R.string.NotificationMessageGroupGame, r2, r14.title, r1.game.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:779:0x1404, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeo) != false) goto L763;
     */
    /* JADX WARN: Code restructure failed: missing block: B:781:0x1408, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaVenue) == false) goto L728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:783:0x140e, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive) == false) goto L731;
     */
    /* JADX WARN: Code restructure failed: missing block: B:785:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupLiveLocation", org.telegram.messenger.R.string.NotificationMessageGroupLiveLocation, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:787:0x1427, code lost:
    
        if ((r1 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaDocument) == false) goto L758;
     */
    /* JADX WARN: Code restructure failed: missing block: B:789:0x142d, code lost:
    
        if (r27.isSticker() != false) goto L754;
     */
    /* JADX WARN: Code restructure failed: missing block: B:791:0x1433, code lost:
    
        if (r27.isAnimatedSticker() == false) goto L738;
     */
    /* JADX WARN: Code restructure failed: missing block: B:793:0x143b, code lost:
    
        if (r27.isGif() == false) goto L747;
     */
    /* JADX WARN: Code restructure failed: missing block: B:794:0x143d, code lost:
    
        if (r28 != false) goto L746;
     */
    /* JADX WARN: Code restructure failed: missing block: B:796:0x1443, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L746;
     */
    /* JADX WARN: Code restructure failed: missing block: B:798:0x144d, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L746;
     */
    /* JADX WARN: Code restructure failed: missing block: B:800:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, "🎬 " + r27.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:802:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupGif", org.telegram.messenger.R.string.NotificationMessageGroupGif, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:803:0x148c, code lost:
    
        if (r28 != false) goto L753;
     */
    /* JADX WARN: Code restructure failed: missing block: B:805:0x1492, code lost:
    
        if (android.os.Build.VERSION.SDK_INT < 19) goto L753;
     */
    /* JADX WARN: Code restructure failed: missing block: B:807:0x149c, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageOwner.message) != false) goto L753;
     */
    /* JADX WARN: Code restructure failed: missing block: B:809:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, "📎 " + r27.messageOwner.message);
     */
    /* JADX WARN: Code restructure failed: missing block: B:811:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupDocument", org.telegram.messenger.R.string.NotificationMessageGroupDocument, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:812:0x14db, code lost:
    
        r0 = r27.getStickerEmoji();
     */
    /* JADX WARN: Code restructure failed: missing block: B:813:0x14e1, code lost:
    
        if (r0 == null) goto L757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:814:0x14e3, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupStickerEmoji", org.telegram.messenger.R.string.NotificationMessageGroupStickerEmoji, r2, r14.title, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:815:0x14f8, code lost:
    
        r0 = org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupSticker", org.telegram.messenger.R.string.NotificationMessageGroupSticker, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:816:0x150b, code lost:
    
        if (r28 != false) goto L762;
     */
    /* JADX WARN: Code restructure failed: missing block: B:818:0x1513, code lost:
    
        if (android.text.TextUtils.isEmpty(r27.messageText) != false) goto L762;
     */
    /* JADX WARN: Code restructure failed: missing block: B:820:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupText", org.telegram.messenger.R.string.NotificationMessageGroupText, r2, r14.title, r27.messageText);
     */
    /* JADX WARN: Code restructure failed: missing block: B:822:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupNoText", org.telegram.messenger.R.string.NotificationMessageGroupNoText, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:824:?, code lost:
    
        return org.telegram.messenger.LocaleController.formatString("NotificationMessageGroupMap", org.telegram.messenger.R.string.NotificationMessageGroupMap, r2, r14.title);
     */
    /* JADX WARN: Code restructure failed: missing block: B:827:0x0614, code lost:
    
        if (r12.getBoolean("EnablePreviewChannel", r13) != false) goto L269;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String getStringForMessage(org.telegram.messenger.MessageObject r27, boolean r28, boolean[] r29, boolean[] r30) {
        /*
            Method dump skipped, instructions count: 5516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.getStringForMessage(org.telegram.messenger.MessageObject, boolean, boolean[], boolean[]):java.lang.String");
    }

    private void scheduleNotificationRepeat() {
        try {
            Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) NotificationRepeat.class);
            intent.putExtra("currentAccount", this.currentAccount);
            PendingIntent service = PendingIntent.getService(ApplicationLoader.applicationContext, 0, intent, ConnectionsManager.FileTypeVideo);
            if (getAccountInstance().getNotificationsSettings().getInt("repeat_messages", 60) > 0 && this.personalCount > 0) {
                this.alarmManager.set(2, SystemClock.elapsedRealtime() + (r1 * 60 * 1000), service);
            } else {
                this.alarmManager.cancel(service);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private boolean isPersonalMessage(MessageObject messageObject) {
        TLRPC$MessageAction tLRPC$MessageAction;
        TLRPC$Message tLRPC$Message = messageObject.messageOwner;
        TLRPC$Peer tLRPC$Peer = tLRPC$Message.peer_id;
        return tLRPC$Peer != null && tLRPC$Peer.chat_id == 0 && tLRPC$Peer.channel_id == 0 && ((tLRPC$MessageAction = tLRPC$Message.action) == null || (tLRPC$MessageAction instanceof TLRPC$TL_messageActionEmpty));
    }

    private int getNotifyOverride(SharedPreferences sharedPreferences, long j, int i) {
        int property = this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY, j, i, -1);
        if (property != 3 || this.dialogsNotificationsFacade.getProperty(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL, j, i, 0) < getConnectionsManager().getCurrentTime()) {
            return property;
        }
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNotifications$25() {
        showOrUpdateNotification(false);
    }

    public void showNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$showNotifications$25();
            }
        });
    }

    public void hideNotifications() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$hideNotifications$26();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideNotifications$26() {
        notificationManager.cancel(this.notificationId);
        this.lastWearNotifiedMessageId.clear();
        for (int i = 0; i < this.wearNotificationsIds.size(); i++) {
            notificationManager.cancel(this.wearNotificationsIds.valueAt(i).intValue());
        }
        this.wearNotificationsIds.clear();
    }

    private void dismissNotification() {
        try {
            notificationManager.cancel(this.notificationId);
            this.pushMessages.clear();
            this.pushMessagesDict.clear();
            this.lastWearNotifiedMessageId.clear();
            for (int i = 0; i < this.wearNotificationsIds.size(); i++) {
                if (!this.openedInBubbleDialogs.contains(Long.valueOf(this.wearNotificationsIds.keyAt(i)))) {
                    notificationManager.cancel(this.wearNotificationsIds.valueAt(i).intValue());
                }
            }
            this.wearNotificationsIds.clear();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.lambda$dismissNotification$27();
                }
            });
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dismissNotification$27() {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.pushMessagesUpdated, new Object[0]);
    }

    private void playInChatSound() {
        if (!this.inChatSoundEnabled || MediaController.getInstance().isRecordingAudio()) {
            return;
        }
        try {
            if (audioManager.getRingerMode() == 0) {
                return;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            if (getNotifyOverride(getAccountInstance().getNotificationsSettings(), this.openedDialogId, this.openedTopicId) == 2) {
                return;
            }
            notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationsController.this.lambda$playInChatSound$29();
                }
            });
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playInChatSound$29() {
        if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundPlay) <= 500) {
            return;
        }
        try {
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda3
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.lambda$playInChatSound$28(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundIn == 0 && !this.soundInLoaded) {
                this.soundInLoaded = true;
                this.soundIn = this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_in, 1);
            }
            int i = this.soundIn;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$playInChatSound$28(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private void scheduleNotificationDelay(boolean z) {
        try {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("delay notification start, onlineReason = " + z);
            }
            this.notificationDelayWakelock.acquire(10000L);
            notificationsQueue.cancelRunnable(this.notificationDelayRunnable);
            notificationsQueue.postRunnable(this.notificationDelayRunnable, z ? BannerConfig.LOOP_TIME : 1000);
        } catch (Exception e) {
            FileLog.e(e);
            showOrUpdateNotification(this.notifyCheck);
        }
    }

    protected void repeatNotificationMaybe() {
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$repeatNotificationMaybe$30();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$repeatNotificationMaybe$30() {
        int i = Calendar.getInstance().get(11);
        if (i >= 11 && i <= 22) {
            notificationManager.cancel(this.notificationId);
            showOrUpdateNotification(true);
        } else {
            scheduleNotificationRepeat();
        }
    }

    private boolean isEmptyVibration(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return false;
        }
        for (long j : jArr) {
            if (j != 0) {
                return false;
            }
        }
        return true;
    }

    public void deleteNotificationChannel(long j, int i) {
        deleteNotificationChannel(j, i, -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: deleteNotificationChannelInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteNotificationChannel$31(long j, int i, int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor edit = notificationsSettings.edit();
            if (i2 == 0 || i2 == -1) {
                String str = "org.telegram.key" + j;
                if (i != 0) {
                    str = str + ".topic" + i;
                }
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    edit.remove(str).remove(str + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("delete channel internal " + string);
                    }
                }
            }
            if (i2 == 1 || i2 == -1) {
                String str2 = "org.telegram.keyia" + j;
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    edit.remove(str2).remove(str2 + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("delete channel internal " + string2);
                    }
                }
            }
            edit.commit();
        } catch (Exception e3) {
            FileLog.e(e3);
        }
    }

    public void deleteNotificationChannel(final long j, final int i, final int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$deleteNotificationChannel$31(j, i, i2);
            }
        });
    }

    public void deleteNotificationChannelGlobal(int i) {
        deleteNotificationChannelGlobal(i, -1);
    }

    /* renamed from: deleteNotificationChannelGlobalInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteNotificationChannelGlobal$32(int i, int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            SharedPreferences.Editor edit = notificationsSettings.edit();
            if (i2 == 0 || i2 == -1) {
                String str = i == 2 ? "channels" : i == 0 ? "groups" : "private";
                String string = notificationsSettings.getString(str, null);
                if (string != null) {
                    edit.remove(str).remove(str + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("delete channel global internal " + string);
                    }
                }
            }
            if (i2 == 1 || i2 == -1) {
                String str2 = i == 2 ? "channels_ia" : i == 0 ? "groups_ia" : "private_ia";
                String string2 = notificationsSettings.getString(str2, null);
                if (string2 != null) {
                    edit.remove(str2).remove(str2 + "_s");
                    try {
                        systemNotificationManager.deleteNotificationChannel(string2);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("delete channel global internal " + string2);
                    }
                }
            }
            edit.remove(i == 2 ? "overwrite_channel" : i == 0 ? "overwrite_group" : "overwrite_private");
            edit.commit();
        } catch (Exception e3) {
            FileLog.e(e3);
        }
    }

    public void deleteNotificationChannelGlobal(final int i, final int i2) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$deleteNotificationChannelGlobal$32(i, i2);
            }
        });
    }

    public void deleteAllNotificationChannels() {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$deleteAllNotificationChannels$33();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAllNotificationChannels$33() {
        try {
            SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
            Map<String, ?> all = notificationsSettings.getAll();
            SharedPreferences.Editor edit = notificationsSettings.edit();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith("org.telegram.key")) {
                    if (!key.endsWith("_s")) {
                        String str = (String) entry.getValue();
                        systemNotificationManager.deleteNotificationChannel(str);
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("delete all channel " + str);
                        }
                    }
                    edit.remove(key);
                }
            }
            edit.commit();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private boolean unsupportedNotificationShortcut() {
        return Build.VERSION.SDK_INT < 29 || !SharedConfig.chatBubbles;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00f0 A[Catch: Exception -> 0x014e, TryCatch #0 {Exception -> 0x014e, blocks: (B:10:0x0020, B:13:0x0061, B:14:0x0069, B:17:0x0079, B:19:0x00a2, B:21:0x00b2, B:22:0x00bc, B:24:0x00f0, B:25:0x00f8, B:27:0x0101, B:28:0x0120, B:31:0x0137, B:36:0x0108, B:38:0x010e, B:39:0x0113, B:40:0x0111, B:41:0x0118, B:42:0x00f4, B:44:0x0075, B:45:0x0065), top: B:9:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0101 A[Catch: Exception -> 0x014e, TryCatch #0 {Exception -> 0x014e, blocks: (B:10:0x0020, B:13:0x0061, B:14:0x0069, B:17:0x0079, B:19:0x00a2, B:21:0x00b2, B:22:0x00bc, B:24:0x00f0, B:25:0x00f8, B:27:0x0101, B:28:0x0120, B:31:0x0137, B:36:0x0108, B:38:0x010e, B:39:0x0113, B:40:0x0111, B:41:0x0118, B:42:0x00f4, B:44:0x0075, B:45:0x0065), top: B:9:0x0020 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f4 A[Catch: Exception -> 0x014e, TryCatch #0 {Exception -> 0x014e, blocks: (B:10:0x0020, B:13:0x0061, B:14:0x0069, B:17:0x0079, B:19:0x00a2, B:21:0x00b2, B:22:0x00bc, B:24:0x00f0, B:25:0x00f8, B:27:0x0101, B:28:0x0120, B:31:0x0137, B:36:0x0108, B:38:0x010e, B:39:0x0113, B:40:0x0111, B:41:0x0118, B:42:0x00f4, B:44:0x0075, B:45:0x0065), top: B:9:0x0020 }] */
    @android.annotation.SuppressLint({"RestrictedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String createNotificationShortcut(androidx.core.app.NotificationCompat.Builder r18, long r19, java.lang.String r21, org.telegram.tgnet.TLRPC$User r22, org.telegram.tgnet.TLRPC$Chat r23, androidx.core.app.Person r24) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.createNotificationShortcut(androidx.core.app.NotificationCompat$Builder, long, java.lang.String, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, androidx.core.app.Person):java.lang.String");
    }

    @TargetApi(26)
    protected void ensureGroupsCreated() {
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        if (this.groupsCreated == null) {
            this.groupsCreated = Boolean.valueOf(notificationsSettings.getBoolean("groupsCreated4", false));
        }
        if (!this.groupsCreated.booleanValue()) {
            try {
                String str = this.currentAccount + "channel";
                List<NotificationChannel> notificationChannels = systemNotificationManager.getNotificationChannels();
                int size = notificationChannels.size();
                SharedPreferences.Editor editor = null;
                for (int i = 0; i < size; i++) {
                    NotificationChannel notificationChannel = notificationChannels.get(i);
                    String id = notificationChannel.getId();
                    if (id.startsWith(str)) {
                        int importance = notificationChannel.getImportance();
                        if (importance != 4 && importance != 5 && !id.contains("_ia_")) {
                            if (id.contains("_channels_")) {
                                if (editor == null) {
                                    editor = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editor.remove("priority_channel").remove("vibrate_channel").remove("ChannelSoundPath").remove("ChannelSound");
                            } else if (id.contains("_groups_")) {
                                if (editor == null) {
                                    editor = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editor.remove("priority_group").remove("vibrate_group").remove("GroupSoundPath").remove("GroupSound");
                            } else if (id.contains("_private_")) {
                                if (editor == null) {
                                    editor = getAccountInstance().getNotificationsSettings().edit();
                                }
                                editor.remove("priority_messages");
                                editor.remove("priority_group").remove("vibrate_messages").remove("GlobalSoundPath").remove("GlobalSound");
                            } else {
                                long longValue = Utilities.parseLong(id.substring(9, id.indexOf(95, 9))).longValue();
                                if (longValue != 0) {
                                    if (editor == null) {
                                        editor = getAccountInstance().getNotificationsSettings().edit();
                                    }
                                    editor.remove("priority_" + longValue).remove("vibrate_" + longValue).remove("sound_path_" + longValue).remove("sound_" + longValue);
                                }
                            }
                        }
                        systemNotificationManager.deleteNotificationChannel(id);
                    }
                }
                if (editor != null) {
                    editor.commit();
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            notificationsSettings.edit().putBoolean("groupsCreated4", true).commit();
            this.groupsCreated = Boolean.TRUE;
        }
        if (this.channelGroupsCreated) {
            return;
        }
        List<NotificationChannelGroup> notificationChannelGroups = systemNotificationManager.getNotificationChannelGroups();
        String str2 = "channels" + this.currentAccount;
        String str3 = "groups" + this.currentAccount;
        String str4 = "private" + this.currentAccount;
        String str5 = "other" + this.currentAccount;
        int size2 = notificationChannelGroups.size();
        String str6 = str5;
        String str7 = str4;
        for (int i2 = 0; i2 < size2; i2++) {
            String id2 = notificationChannelGroups.get(i2).getId();
            if (str2 != null && str2.equals(id2)) {
                str2 = null;
            } else if (str3 != null && str3.equals(id2)) {
                str3 = null;
            } else if (str7 != null && str7.equals(id2)) {
                str7 = null;
            } else if (str6 != null && str6.equals(id2)) {
                str6 = null;
            }
            if (str2 == null && str3 == null && str7 == null && str6 == null) {
                break;
            }
        }
        if (str2 != null || str3 != null || str7 != null || str6 != null) {
            TLRPC$User user = getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId()));
            if (user == null) {
                getUserConfig().getCurrentUser();
            }
            String str8 = user != null ? " (" + ContactsController.formatName(user.first_name, user.last_name) + ")" : "";
            ArrayList arrayList = new ArrayList();
            if (str2 != null) {
                arrayList.add(new NotificationChannelGroup(str2, LocaleController.getString("NotificationsChannels", R.string.NotificationsChannels) + str8));
            }
            if (str3 != null) {
                arrayList.add(new NotificationChannelGroup(str3, LocaleController.getString("NotificationsGroups", R.string.NotificationsGroups) + str8));
            }
            if (str7 != null) {
                arrayList.add(new NotificationChannelGroup(str7, LocaleController.getString("NotificationsPrivateChats", R.string.NotificationsPrivateChats) + str8));
            }
            if (str6 != null) {
                arrayList.add(new NotificationChannelGroup(str6, LocaleController.getString("NotificationsOther", R.string.NotificationsOther) + str8));
            }
            systemNotificationManager.createNotificationChannelGroups(arrayList);
        }
        this.channelGroupsCreated = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0419 A[LOOP:1: B:148:0x0416->B:150:0x0419, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x042b  */
    @android.annotation.TargetApi(26)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String validateChannelId(long r26, int r28, java.lang.String r29, long[] r30, int r31, android.net.Uri r32, int r33, boolean r34, boolean r35, boolean r36, int r37) {
        /*
            Method dump skipped, instructions count: 1386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.validateChannelId(long, int, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):java.lang.String");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(16:173|(1:(1:176)(2:377|(1:379)))(1:380)|177|(13:182|(1:375)(16:(2:187|(2:361|362)(1:195))(2:363|(1:373))|196|(1:203)|204|205|206|(1:356)(2:208|(1:210)(3:344|345|(4:347|(1:349)(1:354)|350|(1:352))))|211|(1:342)(1:(4:216|217|(1:219)|323)(5:324|(3:329|(2:331|(1:333))(1:(2:335|(2:337|338)))|323)|339|(1:341)|323))|220|(1:322)(8:(3:317|(1:319)(1:321)|320)|(2:230|(8:232|(6:(1:238)(1:300)|(1:240)(1:299)|241|(1:243)(4:286|(1:288)(2:(2:294|(1:296)(1:297))|298)|289|245)|244|245)|301|(0)(0)|241|(0)(0)|244|245)(2:302|(1:304)(2:305|(1:315)(2:311|312))))|316|(0)(0)|241|(0)(0)|244|245)|246|(1:285)(4:254|(4:256|(3:258|(4:260|(1:262)|263|264)(2:266|267)|265)|268|269)|270|271)|(2:279|(1:281)(1:282))|283|284)|374|362|196|(3:199|201|203)|204|205|206|(0)(0)|211|(1:213)|342)|376|374|362|196|(0)|204|205|206|(0)(0)|211|(0)|342) */
    /* JADX WARN: Code restructure failed: missing block: B:343:0x08a4, code lost:
    
        if (android.os.Build.VERSION.SDK_INT >= 26) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:358:0x080a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x080c, code lost:
    
        org.telegram.messenger.FileLog.e(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:460:0x014c, code lost:
    
        if (r10 == 0) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:461:0x014e, code lost:
    
        r3 = org.telegram.messenger.LocaleController.getString("NotificationHiddenChatName", org.telegram.messenger.R.string.NotificationHiddenChatName);
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x0157, code lost:
    
        r3 = org.telegram.messenger.LocaleController.getString("NotificationHiddenName", org.telegram.messenger.R.string.NotificationHiddenName);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0442 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0491 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x051e  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x05ff  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x060b A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x061b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0622 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x062f  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x063a A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0663  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x067a  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x06b1 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x07c3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0811 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0865  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x09a6 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:243:0x09b7 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x09c5  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x09b0  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x085f  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0745 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0603  */
    /* JADX WARN: Removed duplicated region for block: B:396:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x0464  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x02b4 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:455:0x01d0 A[Catch: Exception -> 0x0b64, TRY_ENTER, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:456:0x01ef A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:458:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:465:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0132 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0174 A[Catch: Exception -> 0x0b64, TRY_ENTER, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b4 A[Catch: Exception -> 0x0b64, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0241 A[Catch: Exception -> 0x0b64, TRY_ENTER, TryCatch #2 {Exception -> 0x0b64, blocks: (B:10:0x0022, B:12:0x0046, B:15:0x004a, B:17:0x0062, B:18:0x0068, B:21:0x007c, B:25:0x008a, B:27:0x0096, B:28:0x009c, B:30:0x00ac, B:32:0x00ba, B:34:0x00c0, B:36:0x00dd, B:38:0x00e9, B:41:0x0106, B:43:0x010c, B:44:0x011c, B:46:0x0124, B:50:0x012c, B:52:0x0132, B:57:0x0169, B:60:0x0174, B:62:0x017c, B:63:0x01a9, B:65:0x01b4, B:69:0x022b, B:72:0x0241, B:77:0x025e, B:78:0x02a0, B:81:0x036f, B:92:0x038a, B:94:0x03a6, B:96:0x03dd, B:98:0x03e7, B:100:0x0442, B:103:0x0468, B:106:0x0477, B:108:0x0491, B:110:0x04c8, B:111:0x04f1, B:113:0x04ff, B:117:0x0520, B:119:0x052a, B:120:0x053f, B:125:0x0605, B:127:0x060b, B:134:0x0622, B:136:0x0628, B:143:0x063a, B:146:0x0644, B:149:0x064d, B:168:0x0675, B:171:0x067e, B:173:0x06b1, B:176:0x06bc, B:177:0x06d9, B:179:0x06df, B:182:0x06e5, B:184:0x06ee, B:187:0x06f6, B:189:0x06fa, B:191:0x06fe, B:193:0x0706, B:196:0x0767, B:199:0x07c5, B:201:0x07c9, B:203:0x07cf, B:208:0x0811, B:210:0x081e, B:217:0x086c, B:224:0x08b0, B:228:0x08ef, B:230:0x08f7, B:232:0x08fb, B:234:0x0903, B:238:0x090c, B:240:0x09a6, B:243:0x09b7, B:246:0x0a1f, B:248:0x0a25, B:250:0x0a29, B:252:0x0a34, B:254:0x0a3a, B:256:0x0a45, B:258:0x0a54, B:260:0x0a60, B:262:0x0a81, B:263:0x0a86, B:265:0x0ab5, B:269:0x0ac7, B:273:0x0af2, B:275:0x0af8, B:277:0x0b00, B:279:0x0b06, B:281:0x0b18, B:282:0x0b2f, B:283:0x0b45, B:288:0x09c8, B:296:0x09ec, B:298:0x0a00, B:300:0x0936, B:301:0x093b, B:302:0x093e, B:304:0x0944, B:307:0x094e, B:309:0x0956, B:314:0x0994, B:315:0x099c, B:317:0x08ba, B:319:0x08c2, B:320:0x08ea, B:322:0x0a0c, B:331:0x087f, B:335:0x088b, B:339:0x0894, B:342:0x089e, B:360:0x080c, B:365:0x071f, B:367:0x0723, B:369:0x0727, B:371:0x072f, B:379:0x06ce, B:381:0x0745, B:383:0x0752, B:385:0x075d, B:390:0x0537, B:391:0x0562, B:393:0x056e, B:394:0x0583, B:395:0x057b, B:398:0x05aa, B:400:0x05b4, B:401:0x05c9, B:402:0x05c1, B:405:0x04da, B:410:0x03ff, B:412:0x0412, B:413:0x041e, B:415:0x0422, B:421:0x0272, B:423:0x0277, B:424:0x028b, B:426:0x02b4, B:428:0x02d8, B:430:0x02f0, B:435:0x02fa, B:436:0x0300, B:440:0x030d, B:441:0x0321, B:443:0x0326, B:444:0x033a, B:445:0x034d, B:447:0x0355, B:451:0x035e, B:452:0x01c3, B:455:0x01d0, B:456:0x01ef, B:457:0x0189, B:461:0x014e, B:463:0x0157, B:464:0x0160, B:469:0x0115, B:470:0x0118, B:477:0x00c3, B:479:0x00c9, B:484:0x007a, B:206:0x07e8, B:312:0x0960, B:160:0x0666), top: B:9:0x0022, inners: #0, #1, #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x036d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0384  */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r2v46 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r49v0, types: [org.telegram.messenger.BaseController, org.telegram.messenger.NotificationsController] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showOrUpdateNotification(boolean r50) {
        /*
            Method dump skipped, instructions count: 2926
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showOrUpdateNotification(boolean):void");
    }

    private boolean isSilentMessage(MessageObject messageObject) {
        return messageObject.messageOwner.silent || messageObject.isReactionPush;
    }

    @SuppressLint({"NewApi"})
    private void setNotificationChannel(Notification notification, NotificationCompat.Builder builder, boolean z) {
        if (z) {
            builder.setChannelId(OTHER_NOTIFICATIONS_CHANNEL);
        } else {
            builder.setChannelId(notification.getChannelId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetNotificationSound(NotificationCompat.Builder builder, long j, int i, String str, long[] jArr, int i2, Uri uri, int i3, boolean z, boolean z2, boolean z3, int i4) {
        Uri uri2 = Settings.System.DEFAULT_RINGTONE_URI;
        if (uri2 == null || uri == null || TextUtils.equals(uri2.toString(), uri.toString())) {
            return;
        }
        SharedPreferences.Editor edit = getAccountInstance().getNotificationsSettings().edit();
        String uri3 = uri2.toString();
        String string = LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone);
        if (z) {
            if (i4 == 2) {
                edit.putString("ChannelSound", string);
            } else if (i4 == 0) {
                edit.putString("GroupSound", string);
            } else {
                edit.putString("GlobalSound", string);
            }
            if (i4 == 2) {
                edit.putString("ChannelSoundPath", uri3);
            } else if (i4 == 0) {
                edit.putString("GroupSoundPath", uri3);
            } else {
                edit.putString("GlobalSoundPath", uri3);
            }
            getNotificationsController().lambda$deleteNotificationChannelGlobal$32(i4, -1);
        } else {
            edit.putString("sound_" + getSharedPrefKey(j, i), string);
            edit.putString("sound_path_" + getSharedPrefKey(j, i), uri3);
            lambda$deleteNotificationChannel$31(j, i, -1);
        }
        edit.commit();
        builder.setChannelId(validateChannelId(j, i, str, jArr, i2, Settings.System.DEFAULT_RINGTONE_URI, i3, z, z2, z3, i4));
        notificationManager.notify(this.notificationId, builder.build());
    }

    /* JADX WARN: Can't wrap try/catch for region: R(68:68|(2:70|(1:72)(5:476|(1:478)|479|480|481))(3:482|(1:492)(2:486|(53:490|74|(1:76)(2:473|(1:475))|77|78|(3:80|(1:82)(1:471)|83)(1:472)|(3:85|(2:87|(1:89)(3:458|459|(3:461|(1:463)(1:465)|464)))(1:469)|466)(1:470)|(3:91|(1:97)|98)|99|(3:453|(1:455)(1:457)|456)(1:102)|103|(1:105)|106|(1:445)(1:110)|111|(1:444)(6:114|(1:116)|(3:419|420|(6:424|425|426|(5:430|431|432|433|120)|119|120))|118|119|120)|121|(1:418)(1:124)|125|(1:417)|132|(1:416)(1:139)|140|(6:143|(1:145)(4:149|(1:151)(2:323|(3:325|(1:327)|(2:154|(1:156)(1:157))(20:158|(1:160)|161|(2:319|(1:321)(1:322))(1:167)|168|(11:(1:171)(2:315|(1:317))|172|173|(2:(3:176|(2:(2:179|(1:181))(1:309)|182)(2:310|(1:312))|148)|313)(1:314)|183|(3:280|(1:308)(3:286|(1:307)(3:289|(1:293)|(1:306)(1:303))|304)|305)(1:187)|188|(6:190|(1:278)(7:203|(1:277)(3:207|(9:259|260|261|262|263|264|265|266|267)(1:209)|210)|211|(1:213)(1:258)|214|(3:252|253|254)(3:216|(1:218)|251)|(6:220|(1:222)|223|(1:225)|226|(2:231|(3:233|(2:238|239)(1:235)|(1:237)))))|250|(0)|226|(3:229|231|(0)))(1:279)|242|(2:246|247)|148)|318|173|(0)(0)|183|(1:185)|280|(1:282)|308|305|188|(0)(0)|242|(1:249)(3:244|246|247)|148)))|152|(0)(0))|146|147|148|141)|328|329|(1:331)(2:412|(1:414)(1:415))|332|(1:334)|335|(1:337)|338|(2:340|(1:342)(1:407))(2:408|(1:410)(1:411))|(1:344)(1:406)|345|346|347|(1:349)|(1:351)|(1:353)|354|(1:402)(1:358)|359|(1:361)|(1:363)|364|(3:370|(4:372|(3:374|(4:376|(1:378)|379|380)(2:382|383)|381)|384|385)|386)|387|(1:401)(2:390|(1:394))|395|(1:397)|398|399))|491)|73|74|(0)(0)|77|78|(0)(0)|(0)(0)|(0)|99|(0)|447|449|451|453|(0)(0)|456|103|(0)|106|(1:108)|445|111|(0)|444|121|(0)|418|125|(1:127)|417|132|(1:135)|416|140|(1:141)|328|329|(0)(0)|332|(0)|335|(0)|338|(0)(0)|(0)(0)|345|346|347|(0)|(0)|(0)|354|(1:356)|402|359|(0)|(0)|364|(5:366|368|370|(0)|386)|387|(0)|401|395|(0)|398|399) */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x0ca0, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x0ca1, code lost:
    
        org.telegram.messenger.FileLog.e(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:513:0x02cd, code lost:
    
        if (r9.local_id != 0) goto L121;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x045e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x051a  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x052f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0558 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x05df A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x05f3  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0608 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0639  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x06c1  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x073d  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x081d  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x091a  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0948  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x09a0  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x09d8  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x09e7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:233:0x09f9  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0a52  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0a62 A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0926 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x091d  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0a3f  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0774  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0abc  */
    /* JADX WARN: Removed duplicated region for block: B:334:0x0af8  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0b17  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x0b6f  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0bce  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0ca6  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x0cb1  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0cb6  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0cc0  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x0cd4  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0cd9  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x0ce5  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0cf4  */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0d7b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0dad  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x0bf1  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0ba8  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x0ac6  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:457:0x04d2  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:472:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0432  */
    @android.annotation.SuppressLint({"InlinedApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showExtraNotifications(androidx.core.app.NotificationCompat.Builder r72, java.lang.String r73, long r74, int r76, java.lang.String r77, long[] r78, int r79, android.net.Uri r80, int r81, boolean r82, boolean r83, boolean r84, int r85) {
        /*
            Method dump skipped, instructions count: 3927
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.showExtraNotifications(androidx.core.app.NotificationCompat$Builder, java.lang.String, long, int, java.lang.String, long[], int, android.net.Uri, int, boolean, boolean, boolean, int):void");
    }

    /* renamed from: org.telegram.messenger.NotificationsController$1NotificationHolder, reason: invalid class name */
    class C1NotificationHolder {
        TLRPC$Chat chat;
        long dialogId;
        int id;
        String name;
        NotificationCompat.Builder notification;
        int topicId;
        TLRPC$User user;
        final /* synthetic */ String val$chatName;
        final /* synthetic */ int val$chatType;
        final /* synthetic */ int val$importance;
        final /* synthetic */ boolean val$isDefault;
        final /* synthetic */ boolean val$isInApp;
        final /* synthetic */ boolean val$isSilent;
        final /* synthetic */ int val$lastTopicId;
        final /* synthetic */ int val$ledColor;
        final /* synthetic */ Uri val$sound;
        final /* synthetic */ long[] val$vibrationPattern;

        C1NotificationHolder(int i, long j, int i2, String str, TLRPC$User tLRPC$User, TLRPC$Chat tLRPC$Chat, NotificationCompat.Builder builder, int i3, String str2, long[] jArr, int i4, Uri uri, int i5, boolean z, boolean z2, boolean z3, int i6) {
            this.val$lastTopicId = i3;
            this.val$chatName = str2;
            this.val$vibrationPattern = jArr;
            this.val$ledColor = i4;
            this.val$sound = uri;
            this.val$importance = i5;
            this.val$isDefault = z;
            this.val$isInApp = z2;
            this.val$isSilent = z3;
            this.val$chatType = i6;
            this.id = i;
            this.name = str;
            this.user = tLRPC$User;
            this.chat = tLRPC$Chat;
            this.notification = builder;
            this.dialogId = j;
            this.topicId = i2;
        }

        void call() {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.w("show dialog notification with id " + this.id + " " + this.dialogId + " user=" + this.user + " chat=" + this.chat);
            }
            try {
                NotificationsController.notificationManager.notify(this.id, this.notification.build());
            } catch (SecurityException e) {
                FileLog.e(e);
                NotificationsController.this.resetNotificationSound(this.notification, this.dialogId, this.val$lastTopicId, this.val$chatName, this.val$vibrationPattern, this.val$ledColor, this.val$sound, this.val$importance, this.val$isDefault, this.val$isInApp, this.val$isSilent, this.val$chatType);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showExtraNotifications$34(Uri uri, File file) {
        ApplicationLoader.applicationContext.revokeUriPermission(uri, 1);
        if (file != null) {
            file.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadRoundAvatar$36(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setPostProcessor(new PostProcessor() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda1
            @Override // android.graphics.PostProcessor
            public final int onPostProcess(Canvas canvas) {
                int lambda$loadRoundAvatar$35;
                lambda$loadRoundAvatar$35 = NotificationsController.lambda$loadRoundAvatar$35(canvas);
                return lambda$loadRoundAvatar$35;
            }
        });
    }

    @TargetApi(28)
    public static void loadRoundAvatar(File file, Person.Builder builder) {
        if (file != null) {
            try {
                builder.setIcon(IconCompat.createWithBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(file), new ImageDecoder.OnHeaderDecodedListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda0
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                        NotificationsController.lambda$loadRoundAvatar$36(imageDecoder, imageInfo, source);
                    }
                })));
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$loadRoundAvatar$35(Canvas canvas) {
        Path path = new Path();
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        int width = canvas.getWidth();
        float f = width / 2;
        path.addRoundRect(0.0f, 0.0f, width, canvas.getHeight(), f, f, Path.Direction.CW);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawPath(path, paint);
        return -3;
    }

    public void playOutChatSound() {
        if (!this.inChatSoundEnabled || MediaController.getInstance().isRecordingAudio()) {
            return;
        }
        try {
            if (audioManager.getRingerMode() == 0) {
                return;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        notificationsQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$playOutChatSound$38();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playOutChatSound$38() {
        try {
            if (Math.abs(SystemClock.elapsedRealtime() - this.lastSoundOutPlay) <= 100) {
                return;
            }
            this.lastSoundOutPlay = SystemClock.elapsedRealtime();
            if (this.soundPool == null) {
                SoundPool soundPool = new SoundPool(3, 1, 0);
                this.soundPool = soundPool;
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda2
                    @Override // android.media.SoundPool.OnLoadCompleteListener
                    public final void onLoadComplete(SoundPool soundPool2, int i, int i2) {
                        NotificationsController.lambda$playOutChatSound$37(soundPool2, i, i2);
                    }
                });
            }
            if (this.soundOut == 0 && !this.soundOutLoaded) {
                this.soundOutLoaded = true;
                this.soundOut = this.soundPool.load(ApplicationLoader.applicationContext, R.raw.sound_out, 1);
            }
            int i = this.soundOut;
            if (i != 0) {
                try {
                    this.soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$playOutChatSound$37(SoundPool soundPool, int i, int i2) {
        if (i2 == 0) {
            try {
                soundPool.play(i, 1.0f, 1.0f, 1, 0, 1.0f);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public void clearDialogNotificationsSettings(long j, int i) {
        SharedPreferences.Editor edit = getAccountInstance().getNotificationsSettings().edit();
        String sharedPrefKey = getSharedPrefKey(j, i);
        edit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + sharedPrefKey).remove(NotificationsSettingsFacade.PROPERTY_CUSTOM + sharedPrefKey);
        getMessagesStorage().setDialogFlags(j, 0L);
        TLRPC$Dialog tLRPC$Dialog = getMessagesController().dialogs_dict.get(j);
        if (tLRPC$Dialog != null) {
            tLRPC$Dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
        }
        edit.commit();
        getNotificationsController().updateServerNotificationsSettings(j, i, true);
    }

    public void setDialogNotificationsSettings(long j, int i, int i2) {
        SharedPreferences.Editor edit = getAccountInstance().getNotificationsSettings().edit();
        TLRPC$Dialog tLRPC$Dialog = MessagesController.getInstance(UserConfig.selectedAccount).dialogs_dict.get(j);
        if (i2 == 4) {
            if (isGlobalNotificationsEnabled(j)) {
                edit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i));
            } else {
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i), 0);
            }
            getMessagesStorage().setDialogFlags(j, 0L);
            if (tLRPC$Dialog != null) {
                tLRPC$Dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
        } else {
            int currentTime = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentTime();
            if (i2 == 0) {
                currentTime += 3600;
            } else if (i2 == 1) {
                currentTime += 28800;
            } else if (i2 == 2) {
                currentTime += 172800;
            } else if (i2 == 3) {
                currentTime = Integer.MAX_VALUE;
            }
            long j2 = 1;
            if (i2 == 3) {
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i), 2);
            } else {
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i), 3);
                edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, i), currentTime);
                j2 = 1 | (((long) currentTime) << 32);
            }
            getInstance(UserConfig.selectedAccount).removeNotificationsForDialog(j);
            MessagesStorage.getInstance(UserConfig.selectedAccount).setDialogFlags(j, j2);
            if (tLRPC$Dialog != null) {
                TLRPC$TL_peerNotifySettings tLRPC$TL_peerNotifySettings = new TLRPC$TL_peerNotifySettings();
                tLRPC$Dialog.notify_settings = tLRPC$TL_peerNotifySettings;
                tLRPC$TL_peerNotifySettings.mute_until = currentTime;
            }
        }
        edit.commit();
        updateServerNotificationsSettings(j, i);
    }

    public void updateServerNotificationsSettings(long j, int i) {
        updateServerNotificationsSettings(j, i, true);
    }

    public void updateServerNotificationsSettings(long j, int i, boolean z) {
        if (z) {
            getNotificationCenter().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        }
        if (DialogObject.isEncryptedDialog(j)) {
            return;
        }
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        TLRPC$TL_account_updateNotifySettings tLRPC$TL_account_updateNotifySettings = new TLRPC$TL_account_updateNotifySettings();
        TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings = new TLRPC$TL_inputPeerNotifySettings();
        tLRPC$TL_account_updateNotifySettings.settings = tLRPC$TL_inputPeerNotifySettings;
        tLRPC$TL_inputPeerNotifySettings.flags |= 1;
        tLRPC$TL_inputPeerNotifySettings.show_previews = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_CONTENT_PREVIEW + getSharedPrefKey(j, i), true);
        TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings2 = tLRPC$TL_account_updateNotifySettings.settings;
        tLRPC$TL_inputPeerNotifySettings2.flags = tLRPC$TL_inputPeerNotifySettings2.flags | 2;
        tLRPC$TL_inputPeerNotifySettings2.silent = notificationsSettings.getBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + getSharedPrefKey(j, i), false);
        int i2 = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i), -1);
        if (i2 != -1) {
            TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings3 = tLRPC$TL_account_updateNotifySettings.settings;
            tLRPC$TL_inputPeerNotifySettings3.flags |= 4;
            if (i2 == 3) {
                tLRPC$TL_inputPeerNotifySettings3.mute_until = notificationsSettings.getInt(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + getSharedPrefKey(j, i), 0);
            } else {
                tLRPC$TL_inputPeerNotifySettings3.mute_until = i2 == 2 ? Integer.MAX_VALUE : 0;
            }
        }
        long j2 = notificationsSettings.getLong("sound_document_id_" + getSharedPrefKey(j, i), 0L);
        String string = notificationsSettings.getString("sound_path_" + getSharedPrefKey(j, i), null);
        TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings4 = tLRPC$TL_account_updateNotifySettings.settings;
        tLRPC$TL_inputPeerNotifySettings4.flags = tLRPC$TL_inputPeerNotifySettings4.flags | 8;
        if (j2 != 0) {
            TLRPC$TL_notificationSoundRingtone tLRPC$TL_notificationSoundRingtone = new TLRPC$TL_notificationSoundRingtone();
            tLRPC$TL_notificationSoundRingtone.id = j2;
            tLRPC$TL_account_updateNotifySettings.settings.sound = tLRPC$TL_notificationSoundRingtone;
        } else if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                tLRPC$TL_account_updateNotifySettings.settings.sound = new TLRPC$TL_notificationSoundNone();
            } else {
                TLRPC$TL_notificationSoundLocal tLRPC$TL_notificationSoundLocal = new TLRPC$TL_notificationSoundLocal();
                tLRPC$TL_notificationSoundLocal.title = notificationsSettings.getString("sound_" + getSharedPrefKey(j, i), null);
                tLRPC$TL_notificationSoundLocal.data = string;
                tLRPC$TL_account_updateNotifySettings.settings.sound = tLRPC$TL_notificationSoundLocal;
            }
        } else {
            tLRPC$TL_inputPeerNotifySettings4.sound = new TLRPC$TL_notificationSoundDefault();
        }
        if (i != 0) {
            TLRPC$TL_inputNotifyForumTopic tLRPC$TL_inputNotifyForumTopic = new TLRPC$TL_inputNotifyForumTopic();
            tLRPC$TL_inputNotifyForumTopic.peer = getMessagesController().getInputPeer(j);
            tLRPC$TL_inputNotifyForumTopic.top_msg_id = i;
            tLRPC$TL_account_updateNotifySettings.peer = tLRPC$TL_inputNotifyForumTopic;
        } else {
            TLRPC$TL_inputNotifyPeer tLRPC$TL_inputNotifyPeer = new TLRPC$TL_inputNotifyPeer();
            tLRPC$TL_account_updateNotifySettings.peer = tLRPC$TL_inputNotifyPeer;
            tLRPC$TL_inputNotifyPeer.peer = getMessagesController().getInputPeer(j);
        }
        getConnectionsManager().sendRequest(tLRPC$TL_account_updateNotifySettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda42
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                NotificationsController.lambda$updateServerNotificationsSettings$39(tLObject, tLRPC$TL_error);
            }
        });
    }

    public void updateServerNotificationsSettings(int i) {
        String str;
        String str2;
        String str3;
        SharedPreferences notificationsSettings = getAccountInstance().getNotificationsSettings();
        TLRPC$TL_account_updateNotifySettings tLRPC$TL_account_updateNotifySettings = new TLRPC$TL_account_updateNotifySettings();
        TLRPC$TL_inputPeerNotifySettings tLRPC$TL_inputPeerNotifySettings = new TLRPC$TL_inputPeerNotifySettings();
        tLRPC$TL_account_updateNotifySettings.settings = tLRPC$TL_inputPeerNotifySettings;
        tLRPC$TL_inputPeerNotifySettings.flags = 5;
        if (i == 0) {
            tLRPC$TL_account_updateNotifySettings.peer = new TLRPC$TL_inputNotifyChats();
            tLRPC$TL_account_updateNotifySettings.settings.mute_until = notificationsSettings.getInt("EnableGroup2", 0);
            tLRPC$TL_account_updateNotifySettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewGroup", true);
            str = "GroupSound";
            str2 = "GroupSoundDocId";
            str3 = "GroupSoundPath";
        } else if (i == 1) {
            tLRPC$TL_account_updateNotifySettings.peer = new TLRPC$TL_inputNotifyUsers();
            tLRPC$TL_account_updateNotifySettings.settings.mute_until = notificationsSettings.getInt("EnableAll2", 0);
            tLRPC$TL_account_updateNotifySettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewAll", true);
            str = "GlobalSound";
            str2 = "GlobalSoundDocId";
            str3 = "GlobalSoundPath";
        } else {
            tLRPC$TL_account_updateNotifySettings.peer = new TLRPC$TL_inputNotifyBroadcasts();
            tLRPC$TL_account_updateNotifySettings.settings.mute_until = notificationsSettings.getInt("EnableChannel2", 0);
            tLRPC$TL_account_updateNotifySettings.settings.show_previews = notificationsSettings.getBoolean("EnablePreviewChannel", true);
            str = "ChannelSound";
            str2 = "ChannelSoundDocId";
            str3 = "ChannelSoundPath";
        }
        tLRPC$TL_account_updateNotifySettings.settings.flags |= 8;
        long j = notificationsSettings.getLong(str2, 0L);
        String string = notificationsSettings.getString(str3, "NoSound");
        if (j != 0) {
            TLRPC$TL_notificationSoundRingtone tLRPC$TL_notificationSoundRingtone = new TLRPC$TL_notificationSoundRingtone();
            tLRPC$TL_notificationSoundRingtone.id = j;
            tLRPC$TL_account_updateNotifySettings.settings.sound = tLRPC$TL_notificationSoundRingtone;
        } else if (string != null) {
            if (string.equalsIgnoreCase("NoSound")) {
                tLRPC$TL_account_updateNotifySettings.settings.sound = new TLRPC$TL_notificationSoundNone();
            } else {
                TLRPC$TL_notificationSoundLocal tLRPC$TL_notificationSoundLocal = new TLRPC$TL_notificationSoundLocal();
                tLRPC$TL_notificationSoundLocal.title = notificationsSettings.getString(str, null);
                tLRPC$TL_notificationSoundLocal.data = string;
                tLRPC$TL_account_updateNotifySettings.settings.sound = tLRPC$TL_notificationSoundLocal;
            }
        } else {
            tLRPC$TL_account_updateNotifySettings.settings.sound = new TLRPC$TL_notificationSoundDefault();
        }
        getConnectionsManager().sendRequest(tLRPC$TL_account_updateNotifySettings, new RequestDelegate() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda41
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                NotificationsController.lambda$updateServerNotificationsSettings$40(tLObject, tLRPC$TL_error);
            }
        });
    }

    public boolean isGlobalNotificationsEnabled(long j) {
        return isGlobalNotificationsEnabled(j, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0028, code lost:
    
        if (r4.megagroup == false) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x000e, code lost:
    
        if (r6.booleanValue() != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isGlobalNotificationsEnabled(long r4, java.lang.Boolean r6) {
        /*
            r3 = this;
            boolean r0 = org.telegram.messenger.DialogObject.isChatDialog(r4)
            r1 = 2
            r2 = 0
            if (r0 == 0) goto L2b
            if (r6 == 0) goto L13
            boolean r4 = r6.booleanValue()
            if (r4 == 0) goto L11
            goto L2c
        L11:
            r1 = 0
            goto L2c
        L13:
            org.telegram.messenger.MessagesController r6 = r3.getMessagesController()
            long r4 = -r4
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            org.telegram.tgnet.TLRPC$Chat r4 = r6.getChat(r4)
            boolean r5 = org.telegram.messenger.ChatObject.isChannel(r4)
            if (r5 == 0) goto L11
            boolean r4 = r4.megagroup
            if (r4 != 0) goto L11
            goto L2c
        L2b:
            r1 = 1
        L2c:
            boolean r4 = r3.isGlobalNotificationsEnabled(r1)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.NotificationsController.isGlobalNotificationsEnabled(long, java.lang.Boolean):boolean");
    }

    public boolean isGlobalNotificationsEnabled(int i) {
        return getAccountInstance().getNotificationsSettings().getInt(getGlobalNotificationsKey(i), 0) < getConnectionsManager().getCurrentTime();
    }

    public void setGlobalNotificationsEnabled(int i, int i2) {
        getAccountInstance().getNotificationsSettings().edit().putInt(getGlobalNotificationsKey(i), i2).commit();
        updateServerNotificationsSettings(i);
        getMessagesStorage().updateMutedDialogsFiltersCounters();
        deleteNotificationChannelGlobal(i);
    }

    public void muteDialog(long j, int i, boolean z) {
        if (z) {
            getInstance(this.currentAccount).muteUntil(j, i, Integer.MAX_VALUE);
            return;
        }
        boolean isGlobalNotificationsEnabled = getInstance(this.currentAccount).isGlobalNotificationsEnabled(j);
        boolean z2 = i != 0;
        SharedPreferences.Editor edit = MessagesController.getNotificationsSettings(this.currentAccount).edit();
        if (isGlobalNotificationsEnabled && !z2) {
            edit.remove(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i));
        } else {
            edit.putInt(NotificationsSettingsFacade.PROPERTY_NOTIFY + getSharedPrefKey(j, i), 0);
        }
        if (i == 0) {
            getMessagesStorage().setDialogFlags(j, 0L);
            TLRPC$Dialog tLRPC$Dialog = getMessagesController().dialogs_dict.get(j);
            if (tLRPC$Dialog != null) {
                tLRPC$Dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
        }
        edit.apply();
        updateServerNotificationsSettings(j, i);
    }

    public NotificationsSettingsFacade getNotificationsSettingsFacade() {
        return this.dialogsNotificationsFacade;
    }

    public void loadTopicsNotificationsExceptions(final long j, final Consumer<HashSet<Integer>> consumer) {
        getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.this.lambda$loadTopicsNotificationsExceptions$42(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadTopicsNotificationsExceptions$42(long j, final Consumer consumer) {
        final HashSet hashSet = new HashSet();
        Iterator<Map.Entry<String, ?>> it = MessagesController.getNotificationsSettings(this.currentAccount).getAll().entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (key.startsWith(NotificationsSettingsFacade.PROPERTY_NOTIFY + j)) {
                int intValue = Utilities.parseInt((CharSequence) key.replace(NotificationsSettingsFacade.PROPERTY_NOTIFY + j, "")).intValue();
                if (intValue != 0 && getMessagesController().isDialogMuted(j, intValue) != getMessagesController().isDialogMuted(j, 0)) {
                    hashSet.add(Integer.valueOf(intValue));
                }
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.NotificationsController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                NotificationsController.lambda$loadTopicsNotificationsExceptions$41(Consumer.this, hashSet);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadTopicsNotificationsExceptions$41(Consumer consumer, HashSet hashSet) {
        if (consumer != null) {
            consumer.accept(hashSet);
        }
    }

    private static class DialogKey {
        final long dialogId;
        final int topicId;

        private DialogKey(long j, int i) {
            this.dialogId = j;
            this.topicId = i;
        }
    }
}
