package org.telegram.messenger;

import android.appwidget.AppWidgetManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.collection.LongSparseArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import j$.util.function.Consumer;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLiteException;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.TopicsController;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$ChatParticipants;
import org.telegram.tgnet.TLRPC$Dialog;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DraftMessage;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$InputChannel;
import org.telegram.tgnet.TLRPC$InputDialogPeer;
import org.telegram.tgnet.TLRPC$InputMedia;
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageAction;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageFwdHeader;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$MessageReplies;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$Poll;
import org.telegram.tgnet.TLRPC$PollResults;
import org.telegram.tgnet.TLRPC$ReplyMarkup;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channels_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_chatAdminRights;
import org.telegram.tgnet.TLRPC$TL_chatBannedRights;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_chatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_folderPeer;
import org.telegram.tgnet.TLRPC$TL_forumTopic;
import org.telegram.tgnet.TLRPC$TL_inputFolderPeer;
import org.telegram.tgnet.TLRPC$TL_inputMediaGame;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageActionGeoProximityReached;
import org.telegram.tgnet.TLRPC$TL_messageActionTopicCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionTopicEdit;
import org.telegram.tgnet.TLRPC$TL_messageEntityCustomEmoji;
import org.telegram.tgnet.TLRPC$TL_messageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaPoll;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported_old;
import org.telegram.tgnet.TLRPC$TL_messageReactions;
import org.telegram.tgnet.TLRPC$TL_messageReplyHeader;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer;
import org.telegram.tgnet.TLRPC$TL_messages_botResults;
import org.telegram.tgnet.TLRPC$TL_messages_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_messages_deleteScheduledMessages;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photos_photos;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC$TL_updates;
import org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong;
import org.telegram.tgnet.TLRPC$TL_userStatusLastMonth;
import org.telegram.tgnet.TLRPC$TL_userStatusLastWeek;
import org.telegram.tgnet.TLRPC$TL_userStatusRecently;
import org.telegram.tgnet.TLRPC$TL_username;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;
import org.telegram.tgnet.TLRPC$UserStatus;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$WallPaper;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC$photos_Photos;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.DialogsSearchAdapter;

/* loaded from: classes3.dex */
public class MessagesStorage extends BaseController {
    public static final String[] DATABASE_TABLES;
    public static final int LAST_DB_VERSION = 117;
    private int archiveUnreadCount;
    private int[][] bots;
    private File cacheFile;
    private int[][] channels;
    private int[][] contacts;
    private SQLiteDatabase database;
    private boolean databaseCreated;
    private boolean databaseMigrationInProgress;
    private ArrayList<MessagesController.DialogFilter> dialogFilters;
    private SparseArray<MessagesController.DialogFilter> dialogFiltersMap;
    private LongSparseIntArray dialogIsForum;
    private LongSparseArray<Integer> dialogsWithMentions;
    private LongSparseArray<Integer> dialogsWithUnread;
    private int[][] groups;
    private int lastDateValue;
    private int lastPtsValue;
    private int lastQtsValue;
    private int lastSavedDate;
    private int lastSavedPts;
    private int lastSavedQts;
    private int lastSavedSeq;
    private int lastSecretVersion;
    private int lastSeqValue;
    private AtomicLong lastTaskId;
    private int mainUnreadCount;
    private int[] mentionChannels;
    private int[] mentionGroups;
    private int[][] nonContacts;
    private CountDownLatch openSync;
    private volatile int pendingArchiveUnreadCount;
    private volatile int pendingMainUnreadCount;
    private int secretG;
    private byte[] secretPBytes;
    private File shmCacheFile;
    public boolean showClearDatabaseAlert;
    private DispatchQueue storageQueue;
    private SparseArray<ArrayList<Runnable>> tasks;
    boolean tryRecover;
    private LongSparseArray<Boolean> unknownDialogsIds;
    private File walCacheFile;
    private static volatile MessagesStorage[] Instance = new MessagesStorage[1];
    private static final Object[] lockObjects = new Object[1];

    public interface BooleanCallback {
        void run(boolean z);
    }

    public interface IntCallback {
        void run(int i);
    }

    public interface LongCallback {
        void run(long j);
    }

    public interface StringCallback {
        void run(String str);
    }

    static {
        for (int i = 0; i < 1; i++) {
            lockObjects[i] = new Object();
        }
        DATABASE_TABLES = new String[]{"messages_holes", "media_holes_v2", "scheduled_messages_v2", "messages_v2", "download_queue", "user_contacts_v7", "user_phones_v7", "dialogs", "dialog_filter", "dialog_filter_ep", "dialog_filter_pin_v2", "randoms_v2", "enc_tasks_v4", "messages_seq", "params", "media_v4", "bot_keyboard", "bot_keyboard_topics", "chat_settings_v2", "user_settings", "chat_pinned_v2", "chat_pinned_count", "chat_hints", "botcache", "users_data", "users", "chats", "enc_chats", "channel_users_v2", "channel_admins_v3", "contacts", "user_photos", "dialog_settings", "web_recent_v3", "stickers_v2", "stickers_featured", "stickers_dice", "stickersets", "hashtag_recent_v2", "webpage_pending_v2", "sent_files_v2", "search_recent", "media_counts_v2", "keyvalue", "bot_info_v2", "pending_tasks", "requested_holes", "sharing_locations", "shortcut_widget", "emoji_keywords_v2", "emoji_keywords_info_v2", "wallpapers2", "unread_push_messages", "polls_v2", "reactions", "reaction_mentions", "downloading_documents", "animated_emoji", "attach_menu_bots", "premium_promo", "emoji_statuses", "messages_holes_topics", "messages_topics", "media_topics", "media_holes_topics", "topics", "media_counts_topics", "reaction_mentions_topics", "emoji_groups"};
    }

    public static MessagesStorage getInstance(int i) {
        MessagesStorage messagesStorage = Instance[i];
        if (messagesStorage == null) {
            synchronized (lockObjects[i]) {
                messagesStorage = Instance[i];
                if (messagesStorage == null) {
                    MessagesStorage[] messagesStorageArr = Instance;
                    MessagesStorage messagesStorage2 = new MessagesStorage(i);
                    messagesStorageArr[i] = messagesStorage2;
                    messagesStorage = messagesStorage2;
                }
            }
        }
        return messagesStorage;
    }

    private void ensureOpened() {
        try {
            this.openSync.await();
        } catch (Throwable unused) {
        }
    }

    public int getLastDateValue() {
        ensureOpened();
        return this.lastDateValue;
    }

    public void setLastDateValue(int i) {
        ensureOpened();
        this.lastDateValue = i;
    }

    public int getLastPtsValue() {
        ensureOpened();
        return this.lastPtsValue;
    }

    public int getMainUnreadCount() {
        return this.mainUnreadCount;
    }

    public int getArchiveUnreadCount() {
        return this.archiveUnreadCount;
    }

    public void setLastPtsValue(int i) {
        ensureOpened();
        this.lastPtsValue = i;
    }

    public int getLastQtsValue() {
        ensureOpened();
        return this.lastQtsValue;
    }

    public void setLastQtsValue(int i) {
        ensureOpened();
        this.lastQtsValue = i;
    }

    public int getLastSeqValue() {
        ensureOpened();
        return this.lastSeqValue;
    }

    public void setLastSeqValue(int i) {
        ensureOpened();
        this.lastSeqValue = i;
    }

    public int getLastSecretVersion() {
        ensureOpened();
        return this.lastSecretVersion;
    }

    public void setLastSecretVersion(int i) {
        ensureOpened();
        this.lastSecretVersion = i;
    }

    public byte[] getSecretPBytes() {
        ensureOpened();
        return this.secretPBytes;
    }

    public void setSecretPBytes(byte[] bArr) {
        ensureOpened();
        this.secretPBytes = bArr;
    }

    public int getSecretG() {
        ensureOpened();
        return this.secretG;
    }

    public void setSecretG(int i) {
        ensureOpened();
        this.secretG = i;
    }

    public MessagesStorage(int i) {
        super(i);
        this.lastTaskId = new AtomicLong(System.currentTimeMillis());
        this.tasks = new SparseArray<>();
        this.lastDateValue = 0;
        this.lastPtsValue = 0;
        this.lastQtsValue = 0;
        this.lastSeqValue = 0;
        this.lastSecretVersion = 0;
        this.secretPBytes = null;
        this.secretG = 0;
        this.lastSavedSeq = 0;
        this.lastSavedPts = 0;
        this.lastSavedDate = 0;
        this.lastSavedQts = 0;
        this.dialogFilters = new ArrayList<>();
        this.dialogFiltersMap = new SparseArray<>();
        this.unknownDialogsIds = new LongSparseArray<>();
        this.openSync = new CountDownLatch(1);
        this.dialogIsForum = new LongSparseIntArray();
        this.contacts = new int[][]{new int[2], new int[2]};
        this.nonContacts = new int[][]{new int[2], new int[2]};
        this.bots = new int[][]{new int[2], new int[2]};
        this.channels = new int[][]{new int[2], new int[2]};
        this.groups = new int[][]{new int[2], new int[2]};
        this.mentionChannels = new int[2];
        this.mentionGroups = new int[2];
        this.dialogsWithMentions = new LongSparseArray<>();
        this.dialogsWithUnread = new LongSparseArray<>();
        DispatchQueue dispatchQueue = new DispatchQueue("storageQueue_" + i);
        this.storageQueue = dispatchQueue;
        dispatchQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        openDatabase(1);
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public DispatchQueue getStorageQueue() {
        return this.storageQueue;
    }

    public void bindTaskToGuid(Runnable runnable, int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.tasks.put(i, arrayList);
        }
        arrayList.add(runnable);
    }

    public void cancelTasksForGuid(int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.storageQueue.cancelRunnable(arrayList.get(i2));
        }
        this.tasks.remove(i);
    }

    public void completeTaskForGuid(Runnable runnable, int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(runnable);
        if (arrayList.isEmpty()) {
            this.tasks.remove(i);
        }
    }

    public long getDatabaseSize() {
        File file = this.cacheFile;
        long length = file != null ? 0 + file.length() : 0L;
        File file2 = this.shmCacheFile;
        return file2 != null ? length + file2.length() : length;
    }

    public void openDatabase(int i) {
        if (!NativeLoader.loaded()) {
            int i2 = 0;
            while (!NativeLoader.loaded()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i2++;
                if (i2 > 5) {
                    break;
                }
            }
        }
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (this.currentAccount != 0) {
            File file = new File(filesDirFixed, "account" + this.currentAccount + "/");
            file.mkdirs();
            filesDirFixed = file;
        }
        this.cacheFile = new File(filesDirFixed, "cache4.db");
        this.walCacheFile = new File(filesDirFixed, "cache4.db-wal");
        this.shmCacheFile = new File(filesDirFixed, "cache4.db-shm");
        this.databaseCreated = false;
        boolean z = !this.cacheFile.exists();
        try {
            SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(this.cacheFile.getPath());
            this.database = sQLiteDatabase;
            sQLiteDatabase.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
            this.database.executeFast("PRAGMA temp_store = MEMORY").stepThis().dispose();
            this.database.executeFast("PRAGMA journal_mode = WAL").stepThis().dispose();
            this.database.executeFast("PRAGMA journal_size_limit = 10485760").stepThis().dispose();
            if (z) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("create new database");
                }
                createTables(this.database);
            } else {
                int intValue = this.database.executeInt("PRAGMA user_version", new Object[0]).intValue();
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("current db version = " + intValue);
                }
                if (intValue == 0) {
                    throw new Exception("malformed");
                }
                try {
                    SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT seq, pts, date, qts, lsv, sg, pbytes FROM params WHERE id = 1", new Object[0]);
                    if (queryFinalized.next()) {
                        this.lastSeqValue = queryFinalized.intValue(0);
                        this.lastPtsValue = queryFinalized.intValue(1);
                        this.lastDateValue = queryFinalized.intValue(2);
                        this.lastQtsValue = queryFinalized.intValue(3);
                        this.lastSecretVersion = queryFinalized.intValue(4);
                        this.secretG = queryFinalized.intValue(5);
                        if (queryFinalized.isNull(6)) {
                            this.secretPBytes = null;
                        } else {
                            byte[] byteArrayValue = queryFinalized.byteArrayValue(6);
                            this.secretPBytes = byteArrayValue;
                            if (byteArrayValue != null && byteArrayValue.length == 1) {
                                this.secretPBytes = null;
                            }
                        }
                    }
                    queryFinalized.dispose();
                } catch (Exception e2) {
                    FileLog.e(e2);
                    if (e2.getMessage() != null && e2.getMessage().contains("malformed")) {
                        throw new RuntimeException("malformed");
                    }
                    try {
                        this.database.executeFast("CREATE TABLE IF NOT EXISTS params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
                        this.database.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
                    } catch (Exception e3) {
                        FileLog.e(e3);
                    }
                }
                if (intValue < 117) {
                    try {
                        updateDbToLastVersion(intValue);
                    } catch (Exception e4) {
                        if (BuildVars.DEBUG_PRIVATE_VERSION) {
                            throw e4;
                        }
                        FileLog.e(e4);
                        throw new RuntimeException("malformed");
                    }
                }
            }
            this.databaseCreated = true;
        } catch (Exception e5) {
            FileLog.e(e5);
            if (i < 3 && e5.getMessage() != null && e5.getMessage().contains("malformed")) {
                if (i == 2) {
                    cleanupInternal(true);
                    clearLoadingDialogsOffsets();
                } else {
                    cleanupInternal(false);
                }
                openDatabase(i != 1 ? 3 : 2);
                return;
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$openDatabase$1();
            }
        });
        loadDialogFilters();
        loadUnreadMessages();
        loadPendingTasks();
        try {
            this.openSync.countDown();
        } catch (Throwable unused) {
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$openDatabase$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDatabase$1() {
        if (this.databaseMigrationInProgress) {
            this.databaseMigrationInProgress = false;
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.onDatabaseMigration, Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDatabase$2() {
        this.showClearDatabaseAlert = false;
        NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.onDatabaseOpened, new Object[0]);
    }

    private void clearLoadingDialogsOffsets() {
        for (int i = 0; i < 2; i++) {
            getUserConfig().setDialogsLoadOffset(i, 0, 0, 0L, 0L, 0L, 0L);
            getUserConfig().setTotalDialogsCount(i, 0);
        }
        getUserConfig().saveConfig(false);
    }

    private boolean recoverDatabase() {
        this.database.close();
        boolean recoverDatabase = DatabaseMigrationHelper.recoverDatabase(this.cacheFile, this.walCacheFile, this.shmCacheFile, this.currentAccount);
        FileLog.e("Database restored = " + recoverDatabase);
        if (recoverDatabase) {
            try {
                SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(this.cacheFile.getPath());
                this.database = sQLiteDatabase;
                sQLiteDatabase.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
                this.database.executeFast("PRAGMA temp_store = MEMORY").stepThis().dispose();
                this.database.executeFast("PRAGMA journal_mode = WAL").stepThis().dispose();
                this.database.executeFast("PRAGMA journal_size_limit = 10485760").stepThis().dispose();
            } catch (SQLiteException e) {
                FileLog.e(new Exception(e));
                recoverDatabase = false;
            }
        }
        if (!recoverDatabase) {
            cleanupInternal(true);
            openDatabase(1);
            recoverDatabase = this.databaseCreated;
            FileLog.e("Try create new database = " + recoverDatabase);
        }
        if (recoverDatabase) {
            reset();
        }
        return recoverDatabase;
    }

    public static void createTables(SQLiteDatabase sQLiteDatabase) throws SQLiteException {
        sQLiteDatabase.executeFast("CREATE TABLE messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE scheduled_messages_v2(mid INTEGER, uid INTEGER, send_state INTEGER, date INTEGER, data BLOB, ttl INTEGER, replydata BLOB, reply_to_message_id INTEGER, PRIMARY KEY(mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_scheduled_messages_v2 ON scheduled_messages_v2(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_idx_scheduled_messages_v2 ON scheduled_messages_v2(uid, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_scheduled_messages_v2 ON scheduled_messages_v2(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_scheduled_messages_v2 ON scheduled_messages_v2(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_v2(mid INTEGER, uid INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER, forwards INTEGER, replies_data BLOB, thread_reply_id INTEGER, is_channel INTEGER, reply_to_message_id INTEGER, custom_params BLOB, group_id INTEGER, PRIMARY KEY(mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_read_out_idx_messages_v2 ON messages_v2(uid, mid, read_state, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages_v2 ON messages_v2(uid, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages_v2 ON messages_v2(mid, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages_v2 ON messages_v2(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages_v2 ON messages_v2(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages_v2 ON messages_v2(uid, mention, read_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS is_channel_idx_messages_v2 ON messages_v2(mid, is_channel);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_messages_v2 ON messages_v2(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_messages_v2 ON messages_v2(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_groupid_messages_v2 ON messages_v2(uid, mid, group_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, parent TEXT, PRIMARY KEY (uid, type));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialogs(did INTEGER PRIMARY KEY, date INTEGER, unread_count INTEGER, last_mid INTEGER, inbox_max INTEGER, outbox_max INTEGER, last_mid_i INTEGER, unread_count_i INTEGER, pts INTEGER, date_i INTEGER, pinned INTEGER, flags INTEGER, folder_id INTEGER, data BLOB, unread_reactions INTEGER, last_mid_group INTEGER, ttl_period INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS date_idx_dialogs ON dialogs(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS last_mid_idx_dialogs ON dialogs(last_mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS folder_id_idx_dialogs ON dialogs(folder_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS flags_idx_dialogs ON dialogs(flags);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter(id INTEGER PRIMARY KEY, ord INTEGER, unread_count INTEGER, flags INTEGER, title TEXT)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter_ep(id INTEGER, peer INTEGER, PRIMARY KEY (id, peer))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter_pin_v2(id INTEGER, peer INTEGER, pin INTEGER, PRIMARY KEY (id, peer))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE randoms_v2(random_id INTEGER, mid INTEGER, uid INTEGER, PRIMARY KEY (random_id, mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_idx_randoms_v2 ON randoms_v2(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE enc_tasks_v4(mid INTEGER, uid INTEGER, date INTEGER, media INTEGER, PRIMARY KEY(mid, uid, media))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v4 ON enc_tasks_v4(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_v4(mid INTEGER, uid INTEGER, date INTEGER, type INTEGER, data BLOB, PRIMARY KEY(mid, uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media_v4 ON media_v4(uid, mid, type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid_v2 ON bot_keyboard(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_keyboard_topics(uid INTEGER, tid INTEGER, mid INTEGER, info BLOB, PRIMARY KEY(uid, tid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_topics_idx_mid_v2 ON bot_keyboard_topics(mid, uid, tid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER, online INTEGER, inviter INTEGER, links INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_settings(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS user_settings_pinned_idx ON user_settings(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_pinned_v2(uid INTEGER, mid INTEGER, data BLOB, PRIMARY KEY (uid, mid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_pinned_count(uid INTEGER PRIMARY KEY, count INTEGER, end INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE users_data(uid INTEGER PRIMARY KEY, about TEXT)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE users(uid INTEGER PRIMARY KEY, name TEXT, status INTEGER, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chats(uid INTEGER PRIMARY KEY, name TEXT, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE enc_chats(uid INTEGER PRIMARY KEY, user INTEGER, name TEXT, data BLOB, g BLOB, authkey BLOB, ttl INTEGER, layer INTEGER, seq_in INTEGER, seq_out INTEGER, use_count INTEGER, exchange_id INTEGER, key_date INTEGER, fprint INTEGER, fauthkey BLOB, khash BLOB, in_seq_no INTEGER, admin_id INTEGER, mtproto_seq INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE channel_admins_v3(did INTEGER, uid INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE contacts(uid INTEGER PRIMARY KEY, mutual INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_photos(uid INTEGER, id INTEGER, data BLOB, PRIMARY KEY (uid, id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, document BLOB, PRIMARY KEY (id, type));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash INTEGER, premium INTEGER, emoji INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_dice(emoji TEXT PRIMARY KEY, data BLOB, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickersets(id INTEGER PRIMATE KEY, data BLOB, hash INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE webpage_pending_v2(id INTEGER, mid INTEGER, uid INTEGER, PRIMARY KEY (id, mid, uid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE sent_files_v2(uid TEXT, type INTEGER, data BLOB, parent TEXT, PRIMARY KEY (uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, old INTEGER, PRIMARY KEY(uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE keyvalue(id TEXT PRIMARY KEY, value TEXT)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_info_v2(uid INTEGER, dialogId INTEGER, info BLOB, PRIMARY KEY(uid, dialogId))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB, proximity INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE shortcut_widget(id INTEGER, did INTEGER, ord INTEGER, PRIMARY KEY (id, did));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS shortcut_widget_did ON shortcut_widget(did);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_keywords_v2(lang TEXT, keyword TEXT, emoji TEXT, PRIMARY KEY(lang, keyword, emoji));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS emoji_keywords_v2_keyword ON emoji_keywords_v2(keyword);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_keywords_info_v2(lang TEXT PRIMARY KEY, alias TEXT, version INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE wallpapers2(uid INTEGER PRIMARY KEY, data BLOB, num INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS wallpapers_num ON wallpapers2(num);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE unread_push_messages(uid INTEGER, mid INTEGER, random INTEGER, date INTEGER, data BLOB, fm TEXT, name TEXT, uname TEXT, flags INTEGER, PRIMARY KEY(uid, mid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_push_messages_idx_date ON unread_push_messages(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_push_messages_idx_random ON unread_push_messages(random);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE polls_v2(mid INTEGER, uid INTEGER, id INTEGER, PRIMARY KEY (mid, uid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS polls_id_v2 ON polls_v2(id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reactions(data BLOB, hash INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reaction_mentions(message_id INTEGER, state INTEGER, dialog_id INTEGER, PRIMARY KEY(message_id, dialog_id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reaction_mentions_did ON reaction_mentions(dialog_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE downloading_documents(data BLOB, hash INTEGER, id INTEGER, state INTEGER, date INTEGER, PRIMARY KEY(hash, id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE animated_emoji(document_id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE attach_menu_bots(data BLOB, hash INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE premium_promo(data BLOB, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_statuses(data BLOB, type INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_holes_topics(uid INTEGER, topic_id INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, topic_id, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes_topics(uid, topic_id, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_topics(mid INTEGER, uid INTEGER, topic_id INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER, forwards INTEGER, replies_data BLOB, thread_reply_id INTEGER, is_channel INTEGER, reply_to_message_id INTEGER, custom_params BLOB, PRIMARY KEY(mid, topic_id, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages_topics ON messages_topics(uid, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages_topics ON messages_topics(mid, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages_topics ON messages_topics(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages_topics ON messages_topics(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS is_channel_idx_messages_topics ON messages_topics(mid, is_channel);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_messages_topics ON messages_topics(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_messages_topics ON messages_topics(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_uid_messages_topics ON messages_topics(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_read_out_idx_messages_topics ON messages_topics(uid, topic_id, mid, read_state, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages_topics ON messages_topics(uid, topic_id, mention, read_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_messages_topics ON messages_topics(uid, topic_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_date_mid_messages_topics ON messages_topics(uid, topic_id, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_mid_messages_topics ON messages_topics(uid, topic_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_topics(mid INTEGER, uid INTEGER, topic_id INTEGER, date INTEGER, type INTEGER, data BLOB, PRIMARY KEY(mid, uid, topic_id, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media_topics ON media_topics(uid, topic_id, mid, type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_holes_topics(uid INTEGER, topic_id INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, topic_id, type, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_topics ON media_holes_topics(uid, topic_id, type, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE topics(did INTEGER, topic_id INTEGER, data BLOB, top_message INTEGER, topic_message BLOB, unread_count INTEGER, max_read_id INTEGER, unread_mentions INTEGER, unread_reactions INTEGER, read_outbox INTEGER, pinned INTEGER, total_messages_count INTEGER, hidden INTEGER, PRIMARY KEY(did, topic_id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS did_top_message_topics ON topics(did, top_message);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS did_topics ON topics(did);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_counts_topics(uid INTEGER, topic_id INTEGER, type INTEGER, count INTEGER, old INTEGER, PRIMARY KEY(uid, topic_id, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reaction_mentions_topics(message_id INTEGER, state INTEGER, dialog_id INTEGER, topic_id INTEGER, PRIMARY KEY(message_id, dialog_id, topic_id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reaction_mentions_topics_did ON reaction_mentions_topics(dialog_id, topic_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_groups(type INTEGER PRIMARY KEY, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE app_config(data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("PRAGMA user_version = 117").stepThis().dispose();
    }

    public boolean isDatabaseMigrationInProgress() {
        return this.databaseMigrationInProgress;
    }

    private void updateDbToLastVersion(int i) throws Exception {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateDbToLastVersion$3();
            }
        });
        FileLog.d("MessagesStorage start db migration from " + i + " to 117");
        int migrate = DatabaseMigrationHelper.migrate(this, i);
        StringBuilder sb = new StringBuilder();
        sb.append("MessagesStorage db migration finished to varsion ");
        sb.append(migrate);
        FileLog.d(sb.toString());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateDbToLastVersion$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDbToLastVersion$3() {
        this.databaseMigrationInProgress = true;
        NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.onDatabaseMigration, Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDbToLastVersion$4() {
        this.databaseMigrationInProgress = false;
        NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.onDatabaseMigration, Boolean.FALSE);
    }

    void executeNoException(String str) {
        try {
            this.database.executeFast(str).stepThis().dispose();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void cleanupInternal(boolean z) {
        if (z) {
            reset();
        } else {
            clearDatabaseValues();
        }
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
            this.database = null;
        }
        if (z) {
            File file = this.cacheFile;
            if (file != null) {
                file.delete();
                this.cacheFile = null;
            }
            File file2 = this.walCacheFile;
            if (file2 != null) {
                file2.delete();
                this.walCacheFile = null;
            }
            File file3 = this.shmCacheFile;
            if (file3 != null) {
                file3.delete();
                this.shmCacheFile = null;
            }
        }
    }

    public void clearDatabaseValues() {
        this.lastDateValue = 0;
        this.lastSeqValue = 0;
        this.lastPtsValue = 0;
        this.lastQtsValue = 0;
        this.lastSecretVersion = 0;
        this.mainUnreadCount = 0;
        this.archiveUnreadCount = 0;
        this.pendingMainUnreadCount = 0;
        this.pendingArchiveUnreadCount = 0;
        this.dialogFilters.clear();
        this.dialogFiltersMap.clear();
        this.unknownDialogsIds.clear();
        this.lastSavedSeq = 0;
        this.lastSavedPts = 0;
        this.lastSavedDate = 0;
        this.lastSavedQts = 0;
        this.secretPBytes = null;
        this.secretG = 0;
    }

    public void cleanup(final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda200
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$cleanup$6(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$6(boolean z) {
        cleanupInternal(true);
        openDatabase(1);
        if (z) {
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$cleanup$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$5() {
        getMessagesController().getDifference();
    }

    public void saveSecretParams(final int i, final int i2, final byte[] bArr) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveSecretParams$7(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSecretParams$7(int i, int i2, byte[] bArr) {
        try {
            SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE params SET lsv = ?, sg = ?, pbytes = ? WHERE id = 1");
            executeFast.bindInteger(1, i);
            executeFast.bindInteger(2, i2);
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(bArr != null ? bArr.length : 1);
            if (bArr != null) {
                nativeByteBuffer.writeBytes(bArr);
            }
            executeFast.bindByteBuffer(3, nativeByteBuffer);
            executeFast.step();
            executeFast.dispose();
            nativeByteBuffer.reuse();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void checkSQLException(Throwable th) {
        checkSQLException(th, true);
    }

    private void checkSQLException(Throwable th, boolean z) {
        if ((th instanceof SQLiteException) && th.getMessage() != null && th.getMessage().contains("is malformed") && !this.tryRecover) {
            this.tryRecover = true;
            FileLog.e("disk image malformed detected, try recover");
            if (recoverDatabase()) {
                this.tryRecover = false;
                clearLoadingDialogsOffsets();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$checkSQLException$8();
                    }
                });
                FileLog.e(new Exception("database restored!!"));
                return;
            }
            FileLog.e(new Exception(th), z);
            return;
        }
        FileLog.e(th, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSQLException$8() {
        getNotificationCenter().postNotificationName(NotificationCenter.onDatabaseReset, new Object[0]);
    }

    public void fixNotificationSettings() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$fixNotificationSettings$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fixNotificationSettings$9() {
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            Map<String, ?> all = MessagesController.getNotificationsSettings(this.currentAccount).getAll();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith(NotificationsSettingsFacade.PROPERTY_NOTIFY)) {
                    Integer num = (Integer) entry.getValue();
                    if (num.intValue() == 2 || num.intValue() == 3) {
                        String replace = key.replace(NotificationsSettingsFacade.PROPERTY_NOTIFY, "");
                        long j = 1;
                        if (num.intValue() != 2) {
                            if (((Integer) all.get(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + replace)) != null) {
                                j = 1 | (r4.intValue() << 32);
                            }
                        }
                        try {
                            longSparseArray.put(Long.parseLong(replace), Long.valueOf(j));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                this.database.beginTransaction();
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO dialog_settings VALUES(?, ?)");
                for (int i = 0; i < longSparseArray.size(); i++) {
                    executeFast.requery();
                    executeFast.bindLong(1, longSparseArray.keyAt(i));
                    executeFast.bindLong(2, ((Long) longSparseArray.valueAt(i)).longValue());
                    executeFast.step();
                }
                executeFast.dispose();
                this.database.commitTransaction();
            } catch (Exception e2) {
                checkSQLException(e2);
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public long createPendingTask(final NativeByteBuffer nativeByteBuffer) {
        if (nativeByteBuffer == null) {
            return 0L;
        }
        final long andAdd = this.lastTaskId.getAndAdd(1L);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$createPendingTask$10(andAdd, nativeByteBuffer);
            }
        });
        return andAdd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPendingTask$10(long j, NativeByteBuffer nativeByteBuffer) {
        try {
            try {
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO pending_tasks VALUES(?, ?)");
                executeFast.bindLong(1, j);
                executeFast.bindByteBuffer(2, nativeByteBuffer);
                executeFast.step();
                executeFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
            }
        } finally {
            nativeByteBuffer.reuse();
        }
    }

    public void removePendingTask(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$removePendingTask$11(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePendingTask$11(long j) {
        try {
            this.database.executeFast("DELETE FROM pending_tasks WHERE id = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    private void loadPendingTasks() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadPendingTasks$31();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$31() {
        try {
            SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT id, data FROM pending_tasks WHERE 1", new Object[0]);
            while (queryFinalized.next()) {
                final long longValue = queryFinalized.longValue(0);
                NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(1);
                if (byteBufferValue != null) {
                    int readInt32 = byteBufferValue.readInt32(false);
                    if (readInt32 != 100) {
                        switch (readInt32) {
                            case 0:
                                final TLRPC$Chat TLdeserialize = TLRPC$Chat.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                if (TLdeserialize != null) {
                                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda172
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MessagesStorage.this.lambda$loadPendingTasks$12(TLdeserialize, longValue);
                                        }
                                    });
                                    break;
                                }
                                break;
                            case 1:
                                final long readInt322 = byteBufferValue.readInt32(false);
                                final int readInt323 = byteBufferValue.readInt32(false);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda79
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$13(readInt322, readInt323, longValue);
                                    }
                                });
                                break;
                            case 2:
                            case 5:
                            case 8:
                            case 10:
                            case 14:
                                final TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                                tLRPC$TL_dialog.id = byteBufferValue.readInt64(false);
                                tLRPC$TL_dialog.top_message = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.read_inbox_max_id = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.read_outbox_max_id = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.unread_count = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.last_message_date = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.pts = byteBufferValue.readInt32(false);
                                tLRPC$TL_dialog.flags = byteBufferValue.readInt32(false);
                                if (readInt32 >= 5) {
                                    tLRPC$TL_dialog.pinned = byteBufferValue.readBool(false);
                                    tLRPC$TL_dialog.pinnedNum = byteBufferValue.readInt32(false);
                                }
                                if (readInt32 >= 8) {
                                    tLRPC$TL_dialog.unread_mentions_count = byteBufferValue.readInt32(false);
                                }
                                if (readInt32 >= 10) {
                                    tLRPC$TL_dialog.unread_mark = byteBufferValue.readBool(false);
                                }
                                if (readInt32 >= 14) {
                                    tLRPC$TL_dialog.folder_id = byteBufferValue.readInt32(false);
                                }
                                final TLRPC$InputPeer TLdeserialize2 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda177
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$14(tLRPC$TL_dialog, TLdeserialize2, longValue);
                                    }
                                });
                                break;
                            case 3:
                                getSendMessagesHelper().sendGame(TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false), (TLRPC$TL_inputMediaGame) TLRPC$InputMedia.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false), byteBufferValue.readInt64(false), longValue);
                                break;
                            case 4:
                                final long readInt64 = byteBufferValue.readInt64(false);
                                final boolean readBool = byteBufferValue.readBool(false);
                                final TLRPC$InputPeer TLdeserialize3 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda124
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$15(readInt64, readBool, TLdeserialize3, longValue);
                                    }
                                });
                                break;
                            case 6:
                                final long readInt324 = byteBufferValue.readInt32(false);
                                final int readInt325 = byteBufferValue.readInt32(false);
                                final TLRPC$InputChannel TLdeserialize4 = TLRPC$InputChannel.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda81
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$16(readInt324, readInt325, longValue, TLdeserialize4);
                                    }
                                });
                                break;
                            case 7:
                                final long readInt326 = byteBufferValue.readInt32(false);
                                int readInt327 = byteBufferValue.readInt32(false);
                                TLObject TLdeserialize5 = TLRPC$TL_messages_deleteMessages.TLdeserialize(byteBufferValue, readInt327, false);
                                final TLObject TLdeserialize6 = TLdeserialize5 == null ? TLRPC$TL_channels_deleteMessages.TLdeserialize(byteBufferValue, readInt327, false) : TLdeserialize5;
                                if (TLdeserialize6 == null) {
                                    removePendingTask(longValue);
                                    break;
                                } else {
                                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda98
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MessagesStorage.this.lambda$loadPendingTasks$18(readInt326, longValue, TLdeserialize6);
                                        }
                                    });
                                    break;
                                }
                            case 9:
                                final long readInt642 = byteBufferValue.readInt64(false);
                                final TLRPC$InputPeer TLdeserialize7 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda115
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$20(readInt642, TLdeserialize7, longValue);
                                    }
                                });
                                break;
                            case 11:
                                final int readInt328 = byteBufferValue.readInt32(false);
                                final long readInt329 = byteBufferValue.readInt32(false);
                                final int readInt3210 = byteBufferValue.readInt32(false);
                                final TLRPC$InputChannel TLdeserialize8 = readInt329 != 0 ? TLRPC$InputChannel.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false) : null;
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda83
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$21(readInt329, readInt328, TLdeserialize8, readInt3210, longValue);
                                    }
                                });
                                break;
                            case 12:
                            case 19:
                            case 20:
                                removePendingTask(longValue);
                                break;
                            case 13:
                                final long readInt643 = byteBufferValue.readInt64(false);
                                final boolean readBool2 = byteBufferValue.readBool(false);
                                final int readInt3211 = byteBufferValue.readInt32(false);
                                final int readInt3212 = byteBufferValue.readInt32(false);
                                final boolean readBool3 = byteBufferValue.readBool(false);
                                final TLRPC$InputPeer TLdeserialize9 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda123
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$24(readInt643, readBool2, readInt3211, readInt3212, readBool3, TLdeserialize9, longValue);
                                    }
                                });
                                break;
                            case 15:
                                final TLRPC$InputPeer TLdeserialize10 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda184
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$25(TLdeserialize10, longValue);
                                    }
                                });
                                break;
                            case 16:
                                final int readInt3213 = byteBufferValue.readInt32(false);
                                int readInt3214 = byteBufferValue.readInt32(false);
                                final ArrayList arrayList = new ArrayList();
                                for (int i = 0; i < readInt3214; i++) {
                                    arrayList.add(TLRPC$InputDialogPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false));
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda62
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$26(readInt3213, arrayList, longValue);
                                    }
                                });
                                break;
                            case 17:
                                final int readInt3215 = byteBufferValue.readInt32(false);
                                int readInt3216 = byteBufferValue.readInt32(false);
                                final ArrayList arrayList2 = new ArrayList();
                                for (int i2 = 0; i2 < readInt3216; i2++) {
                                    arrayList2.add(TLRPC$TL_inputFolderPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false));
                                }
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda63
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$27(readInt3215, arrayList2, longValue);
                                    }
                                });
                                break;
                            case 18:
                                final long readInt644 = byteBufferValue.readInt64(false);
                                byteBufferValue.readInt32(false);
                                final TLRPC$TL_messages_deleteScheduledMessages TLdeserialize11 = TLRPC$TL_messages_deleteScheduledMessages.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                if (TLdeserialize11 == null) {
                                    removePendingTask(longValue);
                                    break;
                                } else {
                                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda99
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MessagesStorage.this.lambda$loadPendingTasks$28(readInt644, longValue, TLdeserialize11);
                                        }
                                    });
                                    break;
                                }
                            case 21:
                                final Theme.OverrideWallpaperInfo overrideWallpaperInfo = new Theme.OverrideWallpaperInfo();
                                byteBufferValue.readInt64(false);
                                overrideWallpaperInfo.isBlurred = byteBufferValue.readBool(false);
                                overrideWallpaperInfo.isMotion = byteBufferValue.readBool(false);
                                overrideWallpaperInfo.color = byteBufferValue.readInt32(false);
                                overrideWallpaperInfo.gradientColor1 = byteBufferValue.readInt32(false);
                                overrideWallpaperInfo.rotation = byteBufferValue.readInt32(false);
                                overrideWallpaperInfo.intensity = (float) byteBufferValue.readDouble(false);
                                final boolean readBool4 = byteBufferValue.readBool(false);
                                overrideWallpaperInfo.slug = byteBufferValue.readString(false);
                                overrideWallpaperInfo.originalFileName = byteBufferValue.readString(false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda198
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$23(overrideWallpaperInfo, readBool4, longValue);
                                    }
                                });
                                break;
                            case 22:
                                final TLRPC$InputPeer TLdeserialize12 = TLRPC$InputPeer.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda185
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$29(TLdeserialize12, longValue);
                                    }
                                });
                                break;
                            case 23:
                                final long readInt645 = byteBufferValue.readInt64(false);
                                final int readInt3217 = byteBufferValue.readInt32(false);
                                final int readInt3218 = byteBufferValue.readInt32(false);
                                final TLRPC$InputChannel TLdeserialize13 = (!DialogObject.isEncryptedDialog(readInt645) && DialogObject.isChatDialog(readInt645) && byteBufferValue.hasRemaining()) ? TLRPC$InputChannel.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false) : null;
                                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda84
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$22(readInt645, readInt3217, TLdeserialize13, readInt3218, longValue);
                                    }
                                });
                                break;
                            case 24:
                                final long readInt646 = byteBufferValue.readInt64(false);
                                int readInt3219 = byteBufferValue.readInt32(false);
                                TLObject TLdeserialize14 = TLRPC$TL_messages_deleteMessages.TLdeserialize(byteBufferValue, readInt3219, false);
                                final TLObject TLdeserialize15 = TLdeserialize14 == null ? TLRPC$TL_channels_deleteMessages.TLdeserialize(byteBufferValue, readInt3219, false) : TLdeserialize14;
                                if (TLdeserialize15 == null) {
                                    removePendingTask(longValue);
                                    break;
                                } else {
                                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda97
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            MessagesStorage.this.lambda$loadPendingTasks$19(readInt646, longValue, TLdeserialize15);
                                        }
                                    });
                                    break;
                                }
                            case 25:
                                final long readInt647 = byteBufferValue.readInt64(false);
                                final int readInt3220 = byteBufferValue.readInt32(false);
                                final TLRPC$InputChannel TLdeserialize16 = TLRPC$InputChannel.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda82
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        MessagesStorage.this.lambda$loadPendingTasks$17(readInt647, readInt3220, longValue, TLdeserialize16);
                                    }
                                });
                                break;
                        }
                    } else {
                        final int readInt3221 = byteBufferValue.readInt32(false);
                        final boolean readBool5 = byteBufferValue.readBool(false);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda66
                            @Override // java.lang.Runnable
                            public final void run() {
                                MessagesStorage.this.lambda$loadPendingTasks$30(readInt3221, readBool5, longValue);
                            }
                        });
                    }
                    byteBufferValue.reuse();
                }
            }
            queryFinalized.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$12(TLRPC$Chat tLRPC$Chat, long j) {
        getMessagesController().loadUnknownChannel(tLRPC$Chat, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$13(long j, int i, long j2) {
        getMessagesController().getChannelDifference(j, i, j2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$14(TLRPC$Dialog tLRPC$Dialog, TLRPC$InputPeer tLRPC$InputPeer, long j) {
        getMessagesController().checkLastDialogMessage(tLRPC$Dialog, tLRPC$InputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$15(long j, boolean z, TLRPC$InputPeer tLRPC$InputPeer, long j2) {
        getMessagesController().pinDialog(j, z, tLRPC$InputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$16(long j, int i, long j2, TLRPC$InputChannel tLRPC$InputChannel) {
        getMessagesController().getChannelDifference(j, i, j2, tLRPC$InputChannel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$17(long j, int i, long j2, TLRPC$InputChannel tLRPC$InputChannel) {
        getMessagesController().getChannelDifference(j, i, j2, tLRPC$InputChannel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$18(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, -j, true, false, false, j2, tLObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$19(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, j, true, false, false, j2, tLObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$20(long j, TLRPC$InputPeer tLRPC$InputPeer, long j2) {
        getMessagesController().markDialogAsUnread(j, tLRPC$InputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$21(long j, int i, TLRPC$InputChannel tLRPC$InputChannel, int i2, long j2) {
        getMessagesController().markMessageAsRead2(-j, i, tLRPC$InputChannel, i2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$22(long j, int i, TLRPC$InputChannel tLRPC$InputChannel, int i2, long j2) {
        getMessagesController().markMessageAsRead2(j, i, tLRPC$InputChannel, i2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$23(Theme.OverrideWallpaperInfo overrideWallpaperInfo, boolean z, long j) {
        getMessagesController().saveWallpaperToServer(null, overrideWallpaperInfo, z, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$24(long j, boolean z, int i, int i2, boolean z2, TLRPC$InputPeer tLRPC$InputPeer, long j2) {
        getMessagesController().deleteDialog(j, z ? 1 : 0, i, i2, z2, tLRPC$InputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$25(TLRPC$InputPeer tLRPC$InputPeer, long j) {
        getMessagesController().loadUnknownDialog(tLRPC$InputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$26(int i, ArrayList arrayList, long j) {
        getMessagesController().reorderPinnedDialogs(i, arrayList, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$27(int i, ArrayList arrayList, long j) {
        getMessagesController().addDialogToFolder(null, i, -1, arrayList, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$28(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, j, true, true, false, j2, tLObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$29(TLRPC$InputPeer tLRPC$InputPeer, long j) {
        getMessagesController().reloadMentionsCountForChannel(tLRPC$InputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$30(int i, boolean z, long j) {
        getSecretChatHelper().declineSecretChat(i, z, j);
    }

    public void saveChannelPts(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda43
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveChannelPts$32(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChannelPts$32(int i, long j) {
        try {
            SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE dialogs SET pts = ? WHERE did = ?");
            executeFast.bindInteger(1, i);
            executeFast.bindLong(2, -j);
            executeFast.step();
            executeFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: saveDiffParamsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$saveDiffParams$33(int i, int i2, int i3, int i4) {
        try {
            if (this.lastSavedSeq == i && this.lastSavedPts == i2 && this.lastSavedDate == i3 && this.lastQtsValue == i4) {
                return;
            }
            SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE params SET seq = ?, pts = ?, date = ?, qts = ? WHERE id = 1");
            executeFast.bindInteger(1, i);
            executeFast.bindInteger(2, i2);
            executeFast.bindInteger(3, i3);
            executeFast.bindInteger(4, i4);
            executeFast.step();
            executeFast.dispose();
            this.lastSavedSeq = i;
            this.lastSavedPts = i2;
            this.lastSavedDate = i3;
            this.lastSavedQts = i4;
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void saveDiffParams(final int i, final int i2, final int i3, final int i4) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveDiffParams$33(i, i2, i3, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMutedDialogsFiltersCounters$34() {
        resetAllUnreadCounters(true);
    }

    public void updateMutedDialogsFiltersCounters() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMutedDialogsFiltersCounters$34();
            }
        });
    }

    public void setDialogFlags(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda91
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogFlags$35(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogFlags$35(long j, long j2) {
        try {
            SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT flags FROM dialog_settings WHERE did = " + j, new Object[0]);
            int intValue = queryFinalized.next() ? queryFinalized.intValue(0) : 0;
            queryFinalized.dispose();
            if (j2 == intValue) {
                return;
            }
            this.database.executeFast(String.format(Locale.US, "REPLACE INTO dialog_settings VALUES(%d, %d)", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            resetAllUnreadCounters(true);
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putPushMessage(final MessageObject messageObject) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda165
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putPushMessage$36(messageObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPushMessage$36(MessageObject messageObject) {
        try {
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(messageObject.messageOwner.getObjectSize());
            messageObject.messageOwner.serializeToStream(nativeByteBuffer);
            int i = messageObject.localType == 2 ? 1 : 0;
            if (messageObject.localChannel) {
                i |= 2;
            }
            SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO unread_push_messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            executeFast.requery();
            executeFast.bindLong(1, messageObject.getDialogId());
            executeFast.bindInteger(2, messageObject.getId());
            executeFast.bindLong(3, messageObject.messageOwner.random_id);
            executeFast.bindInteger(4, messageObject.messageOwner.date);
            executeFast.bindByteBuffer(5, nativeByteBuffer);
            CharSequence charSequence = messageObject.messageText;
            if (charSequence == null) {
                executeFast.bindNull(6);
            } else {
                executeFast.bindString(6, charSequence.toString());
            }
            String str = messageObject.localName;
            if (str == null) {
                executeFast.bindNull(7);
            } else {
                executeFast.bindString(7, str);
            }
            String str2 = messageObject.localUserName;
            if (str2 == null) {
                executeFast.bindNull(8);
            } else {
                executeFast.bindString(8, str2);
            }
            executeFast.bindInteger(9, i);
            executeFast.step();
            nativeByteBuffer.reuse();
            executeFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearLocalDatabase() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearLocalDatabase$37();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0313 A[Catch: all -> 0x0330, Exception -> 0x0332, TryCatch #10 {Exception -> 0x0332, blocks: (B:62:0x021e, B:63:0x0222, B:65:0x0313, B:66:0x0325), top: B:61:0x021e }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03e6  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0402  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0407  */
    /* JADX WARN: Type inference failed for: r7v23 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$clearLocalDatabase$37() {
        /*
            Method dump skipped, instructions count: 1038
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$clearLocalDatabase$37():void");
    }

    public void saveTopics(final long j, final List<TLRPC$TL_forumTopic> list, final boolean z, boolean z2) {
        if (z2) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda109
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$saveTopics$38(j, list, z);
                }
            });
        } else {
            saveTopicsInternal(j, list, z, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveTopics$38(long j, List list, boolean z) {
        saveTopicsInternal(j, list, z, true);
    }

    private void saveTopicsInternal(long j, List<TLRPC$TL_forumTopic> list, boolean z, boolean z2) {
        int i;
        int i2;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                HashSet hashSet = new HashSet();
                HashMap hashMap = new HashMap();
                int i3 = 0;
                while (true) {
                    i = 2;
                    if (i3 >= list.size()) {
                        break;
                    }
                    TLRPC$TL_forumTopic tLRPC$TL_forumTopic = list.get(i3);
                    SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT did, pinned FROM topics WHERE did = " + j + " AND topic_id = " + tLRPC$TL_forumTopic.id, new Object[0]);
                    boolean next = queryFinalized.next();
                    if (next) {
                        hashMap.put(Integer.valueOf(i3), Integer.valueOf(queryFinalized.intValue(2)));
                    }
                    queryFinalized.dispose();
                    if (next) {
                        hashSet.add(Integer.valueOf(i3));
                    }
                    i3++;
                }
                if (z) {
                    this.database.executeFast("DELETE FROM topics WHERE did = " + j).stepThis().dispose();
                }
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO topics VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                if (z2) {
                    try {
                        this.database.beginTransaction();
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        this.database.commitTransaction();
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        this.database.commitTransaction();
                        throw th;
                    }
                }
                int i4 = 0;
                while (i4 < list.size()) {
                    TLRPC$TL_forumTopic tLRPC$TL_forumTopic2 = list.get(i4);
                    boolean contains = hashSet.contains(Integer.valueOf(i4));
                    executeFast.requery();
                    executeFast.bindLong(1, j);
                    executeFast.bindInteger(i, tLRPC$TL_forumTopic2.id);
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$TL_forumTopic2.getObjectSize());
                    tLRPC$TL_forumTopic2.serializeToStream(nativeByteBuffer);
                    executeFast.bindByteBuffer(3, nativeByteBuffer);
                    executeFast.bindInteger(4, tLRPC$TL_forumTopic2.top_message);
                    NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(tLRPC$TL_forumTopic2.topicStartMessage.getObjectSize());
                    tLRPC$TL_forumTopic2.topicStartMessage.serializeToStream(nativeByteBuffer2);
                    executeFast.bindByteBuffer(5, nativeByteBuffer2);
                    executeFast.bindInteger(6, tLRPC$TL_forumTopic2.unread_count);
                    executeFast.bindInteger(7, tLRPC$TL_forumTopic2.read_inbox_max_id);
                    executeFast.bindInteger(8, tLRPC$TL_forumTopic2.unread_mentions_count);
                    executeFast.bindInteger(9, tLRPC$TL_forumTopic2.unread_reactions_count);
                    executeFast.bindInteger(10, tLRPC$TL_forumTopic2.read_outbox_max_id);
                    if (tLRPC$TL_forumTopic2.isShort && hashMap.containsKey(Integer.valueOf(i4))) {
                        executeFast.bindInteger(11, ((Integer) hashMap.get(Integer.valueOf(i4))).intValue());
                    } else {
                        executeFast.bindInteger(11, tLRPC$TL_forumTopic2.pinned ? tLRPC$TL_forumTopic2.pinnedOrder + 1 : 0);
                    }
                    executeFast.bindInteger(12, tLRPC$TL_forumTopic2.totalMessagesCount);
                    executeFast.bindInteger(13, tLRPC$TL_forumTopic2.hidden ? 1 : 0);
                    executeFast.step();
                    nativeByteBuffer2.reuse();
                    nativeByteBuffer.reuse();
                    if (contains) {
                        int i5 = tLRPC$TL_forumTopic2.top_message;
                        i2 = i4;
                        closeHolesInTable("messages_holes_topics", j, i5, i5, tLRPC$TL_forumTopic2.id);
                        int i6 = tLRPC$TL_forumTopic2.top_message;
                        closeHolesInMedia(j, i6, i6, -1, 0);
                    } else {
                        i2 = i4;
                        SQLiteDatabase sQLiteDatabase = this.database;
                        Locale locale = Locale.ENGLISH;
                        sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM messages_holes_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(tLRPC$TL_forumTopic2.id))).stepThis().dispose();
                        this.database.executeFast(String.format(locale, "DELETE FROM media_holes_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(tLRPC$TL_forumTopic2.id))).stepThis().dispose();
                        this.database.executeFast(String.format(locale, "DELETE FROM messages_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(tLRPC$TL_forumTopic2.id))).stepThis().dispose();
                        this.database.executeFast(String.format(locale, "DELETE FROM media_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(tLRPC$TL_forumTopic2.id))).stepThis().dispose();
                        SQLitePreparedStatement executeFast2 = this.database.executeFast("REPLACE INTO messages_holes_topics VALUES(?, ?, ?, ?)");
                        createFirstHoles(j, executeFast2, this.database.executeFast("REPLACE INTO media_holes_topics VALUES(?, ?, ?, ?, ?)"), tLRPC$TL_forumTopic2.top_message, tLRPC$TL_forumTopic2.id);
                        executeFast2.dispose();
                        executeFast2.dispose();
                    }
                    i4 = i2 + 1;
                    i = 2;
                }
                resetAllUnreadCounters(false);
                if (executeFast != null) {
                    executeFast.dispose();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
        this.database.commitTransaction();
    }

    public void updateTopicData(final long j, final TLRPC$TL_forumTopic tLRPC$TL_forumTopic, final int i) {
        if (tLRPC$TL_forumTopic == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateTopicData$39(i, tLRPC$TL_forumTopic, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateTopicData$39(int r12, org.telegram.tgnet.TLRPC$TL_forumTopic r13, long r14) {
        /*
            Method dump skipped, instructions count: 290
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateTopicData$39(int, org.telegram.tgnet.TLRPC$TL_forumTopic, long):void");
    }

    public void loadTopics(final long j, final Consumer<ArrayList<TLRPC$TL_forumTopic>> consumer) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda110
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadTopics$41(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x028a A[Catch: Exception -> 0x01c9, all -> 0x02b0, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x01c9, blocks: (B:67:0x0124, B:69:0x012f, B:71:0x013c, B:72:0x0147, B:75:0x015a, B:77:0x0160, B:79:0x016b, B:110:0x01bc, B:121:0x01db, B:145:0x0265, B:149:0x028a, B:152:0x0297), top: B:66:0x0124 }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0297 A[Catch: Exception -> 0x01c9, all -> 0x02b0, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x01c9, blocks: (B:67:0x0124, B:69:0x012f, B:71:0x013c, B:72:0x0147, B:75:0x015a, B:77:0x0160, B:79:0x016b, B:110:0x01bc, B:121:0x01db, B:145:0x0265, B:149:0x028a, B:152:0x0297), top: B:66:0x0124 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x02df  */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$loadTopics$41(long r21, j$.util.function.Consumer r23) {
        /*
            Method dump skipped, instructions count: 739
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadTopics$41(long, j$.util.function.Consumer):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadTopics$40(ArrayList arrayList, ArrayList arrayList2) {
        if (!arrayList.isEmpty()) {
            getMessagesController().putUsers(arrayList, true);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        getMessagesController().putChats(arrayList2, true);
    }

    public void loadGroupedMessagesForTopicUpdates(ArrayList<TopicsController.TopicUpdate> arrayList) {
        if (arrayList == null) {
            return;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).reloadTopic && !arrayList.get(i).onlyCounters && arrayList.get(i).topMessage != null) {
                    long j = arrayList.get(i).topMessage.grouped_id;
                    if (j != 0) {
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(j);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(j, arrayList2);
                        }
                        arrayList2.add(arrayList.get(i));
                    }
                }
            }
            for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                long keyAt = longSparseArray.keyAt(i2);
                ArrayList arrayList3 = (ArrayList) longSparseArray.valueAt(i2);
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE uid = %s AND group_id = %s ORDER BY date DESC", Long.valueOf(((TopicsController.TopicUpdate) arrayList3.get(0)).dialogId), Long.valueOf(keyAt)), new Object[0]);
                ArrayList<MessageObject> arrayList4 = null;
                while (queryFinalized.next()) {
                    NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                    TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                    if (TLdeserialize != null) {
                        TLdeserialize.readAttachPath(byteBufferValue, UserConfig.getInstance(this.currentAccount).clientUserId);
                    }
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList4.add(new MessageObject(this.currentAccount, TLdeserialize, false, false));
                }
                queryFinalized.dispose();
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((TopicsController.TopicUpdate) arrayList3.get(i3)).groupedMessages = arrayList4;
                }
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public void loadGroupedMessagesForTopics(long j, ArrayList<TLRPC$TL_forumTopic> arrayList) {
        if (arrayList == null) {
            return;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).topMessage != null) {
                    long j2 = arrayList.get(i).topMessage.grouped_id;
                    if (j2 != 0) {
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(j2);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(j2, arrayList2);
                        }
                        arrayList2.add(arrayList.get(i));
                    }
                }
            }
            for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                long keyAt = longSparseArray.keyAt(i2);
                ArrayList arrayList3 = (ArrayList) longSparseArray.valueAt(i2);
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE uid = %s AND group_id = %s ORDER BY date DESC", Long.valueOf(j), Long.valueOf(keyAt)), new Object[0]);
                ArrayList<MessageObject> arrayList4 = null;
                while (queryFinalized.next()) {
                    NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                    TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                    if (TLdeserialize != null) {
                        TLdeserialize.readAttachPath(byteBufferValue, UserConfig.getInstance(this.currentAccount).clientUserId);
                    }
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList4.add(new MessageObject(this.currentAccount, TLdeserialize, false, false));
                }
                queryFinalized.dispose();
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((TLRPC$TL_forumTopic) arrayList3.get(i3)).groupedMessages = arrayList4;
                }
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public void removeTopic(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda74
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$removeTopic$42(j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeTopic$42(long j, int i) {
        try {
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.US;
            sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM topics WHERE did = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
            this.database.executeFast(String.format(locale, "DELETE FROM messages_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void removeTopics(final long j, final ArrayList<Integer> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda154
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$removeTopics$43(arrayList, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeTopics$43(ArrayList arrayList, long j) {
        try {
            String join = TextUtils.join(", ", arrayList);
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.US;
            sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM topics WHERE did = %d AND topic_id IN (%s)", Long.valueOf(j), join)).stepThis().dispose();
            this.database.executeFast(String.format(locale, "DELETE FROM messages_topics WHERE uid = %d AND topic_id IN (%s)", Long.valueOf(j), join)).stepThis().dispose();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void updateTopicsWithReadMessages(final HashMap<TopicKey, Integer> hashMap) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda163
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateTopicsWithReadMessages$44(hashMap);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTopicsWithReadMessages$44(HashMap hashMap) {
        for (TopicKey topicKey : hashMap.keySet()) {
            try {
                this.database.executeFast(String.format(Locale.US, "UPDATE topics SET read_outbox = max((SELECT read_outbox FROM topics WHERE did = %d AND topic_id = %d), %d) WHERE did = %d AND topic_id = %d", Long.valueOf(topicKey.dialogId), Integer.valueOf(topicKey.topicId), Integer.valueOf(((Integer) hashMap.get(topicKey)).intValue()), Long.valueOf(topicKey.dialogId), Integer.valueOf(topicKey.topicId))).stepThis().dispose();
            } catch (SQLiteException e) {
                checkSQLException(e);
            }
        }
    }

    public void setDialogTtl(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogTtl$45(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogTtl$45(int i, long j) {
        try {
            this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET ttl_period = %d WHERE did = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
        } catch (SQLiteException e) {
            checkSQLException(e);
        }
    }

    public ArrayList<File> getDatabaseFiles() {
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(this.cacheFile);
        arrayList.add(this.walCacheFile);
        arrayList.add(this.shmCacheFile);
        return arrayList;
    }

    public void reset() {
        clearDatabaseValues();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$reset$46();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reset$46() {
        for (int i = 0; i < 2; i++) {
            getUserConfig().setDialogsLoadOffset(i, 0, 0, 0L, 0L, 0L, 0L);
            getUserConfig().setTotalDialogsCount(i, 0);
        }
        getUserConfig().clearFilters();
        getUserConfig().clearPinnedDialogsLoaded();
        NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.didClearDatabase, new Object[0]);
        getMediaDataController().loadAttachMenuBots(false, true);
        getNotificationCenter().postNotificationName(NotificationCenter.onDatabaseReset, new Object[0]);
    }

    private static class ReadDialog {
        public int date;
        public int lastMid;
        public int unreadCount;

        private ReadDialog() {
        }
    }

    public void readAllDialogs(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$readAllDialogs$48(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.telegram.messenger.MessagesStorage$1] */
    /* JADX WARN: Type inference failed for: r0v6 */
    public /* synthetic */ void lambda$readAllDialogs$48(int i) {
        SQLiteCursor queryFinalized;
        SQLiteCursor sQLiteCursor = 0;
        SQLiteCursor sQLiteCursor2 = null;
        try {
            try {
                ArrayList<Long> arrayList = new ArrayList<>();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                final LongSparseArray longSparseArray = new LongSparseArray();
                if (i >= 0) {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT did, last_mid, unread_count, date FROM dialogs WHERE unread_count > 0 AND folder_id = %1$d", Integer.valueOf(i)), new Object[0]);
                } else {
                    queryFinalized = this.database.queryFinalized("SELECT did, last_mid, unread_count, date FROM dialogs WHERE unread_count > 0", new Object[0]);
                }
                while (queryFinalized.next()) {
                    try {
                        long longValue = queryFinalized.longValue(0);
                        if (!DialogObject.isFolderDialogId(longValue)) {
                            ReadDialog readDialog = new ReadDialog();
                            readDialog.lastMid = queryFinalized.intValue(1);
                            readDialog.unreadCount = queryFinalized.intValue(2);
                            readDialog.date = queryFinalized.intValue(3);
                            longSparseArray.put(longValue, readDialog);
                            if (!DialogObject.isEncryptedDialog(longValue)) {
                                if (DialogObject.isChatDialog(longValue)) {
                                    long j = -longValue;
                                    if (!arrayList2.contains(Long.valueOf(j))) {
                                        arrayList2.add(Long.valueOf(j));
                                    }
                                } else if (!arrayList.contains(Long.valueOf(longValue))) {
                                    arrayList.add(Long.valueOf(longValue));
                                }
                            } else {
                                int encryptedChatId = DialogObject.getEncryptedChatId(longValue);
                                if (!arrayList3.contains(Integer.valueOf(encryptedChatId))) {
                                    arrayList3.add(Integer.valueOf(encryptedChatId));
                                }
                            }
                        }
                    } catch (Exception e) {
                        sQLiteCursor = queryFinalized;
                        e = e;
                        checkSQLException(e);
                        if (sQLiteCursor != 0) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        sQLiteCursor2 = queryFinalized;
                        th = th;
                        if (sQLiteCursor2 != null) {
                            sQLiteCursor2.dispose();
                        }
                        throw th;
                    }
                }
                queryFinalized.dispose();
                final ArrayList<TLRPC$User> arrayList4 = new ArrayList<>();
                final ArrayList<TLRPC$Chat> arrayList5 = new ArrayList<>();
                final ArrayList<TLRPC$EncryptedChat> arrayList6 = new ArrayList<>();
                if (!arrayList3.isEmpty()) {
                    getEncryptedChatsInternal(TextUtils.join(",", arrayList3), arrayList6, arrayList);
                }
                if (!arrayList.isEmpty()) {
                    getUsersInternal(TextUtils.join(",", arrayList), arrayList4);
                }
                if (!arrayList2.isEmpty()) {
                    getChatsInternal(TextUtils.join(",", arrayList2), arrayList5);
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda159
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$readAllDialogs$47(arrayList4, arrayList5, arrayList6, longSparseArray);
                    }
                });
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readAllDialogs$47(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, LongSparseArray longSparseArray) {
        getMessagesController().putUsers(arrayList, true);
        getMessagesController().putChats(arrayList2, true);
        getMessagesController().putEncryptedChats(arrayList3, true);
        for (int i = 0; i < longSparseArray.size(); i++) {
            long keyAt = longSparseArray.keyAt(i);
            ReadDialog readDialog = (ReadDialog) longSparseArray.valueAt(i);
            MessagesController messagesController = getMessagesController();
            int i2 = readDialog.lastMid;
            messagesController.markDialogAsRead(keyAt, i2, i2, readDialog.date, false, 0, readDialog.unreadCount, true, 0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b5 A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00de A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00fd A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01b6 A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01c5 A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01dd A[Catch: all -> 0x03e3, Exception -> 0x03e6, TryCatch #4 {all -> 0x03e3, blocks: (B:5:0x002d, B:7:0x0035, B:9:0x005d, B:13:0x006d, B:16:0x008c, B:19:0x00a2, B:21:0x00b5, B:23:0x00bd, B:24:0x00c2, B:26:0x00de, B:27:0x00ea, B:29:0x00fd, B:31:0x0108, B:33:0x012d, B:34:0x0134, B:36:0x0148, B:38:0x014c, B:40:0x0150, B:42:0x0156, B:44:0x015a, B:46:0x015e, B:48:0x0166, B:50:0x016c, B:53:0x017d, B:55:0x0189, B:56:0x0190, B:58:0x0194, B:59:0x01b0, B:61:0x01b6, B:63:0x01bc, B:64:0x01bf, B:66:0x01c5, B:68:0x01d5, B:72:0x01dd, B:74:0x01e5, B:76:0x01ef, B:79:0x01f7, B:81:0x0202, B:86:0x01a2, B:93:0x01a6, B:101:0x0210), top: B:4:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x008b  */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.telegram.tgnet.TLRPC$messages_Dialogs loadDialogsByIds(java.lang.String r22, java.util.ArrayList<java.lang.Long> r23, java.util.ArrayList<java.lang.Long> r24, java.util.ArrayList<java.lang.Integer> r25) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 1016
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadDialogsByIds(java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList):org.telegram.tgnet.TLRPC$messages_Dialogs");
    }

    private void loadDialogFilters() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadDialogFilters$50();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0262  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$loadDialogFilters$50() {
        /*
            Method dump skipped, instructions count: 614
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadDialogFilters$50():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$loadDialogFilters$49(MessagesController.DialogFilter dialogFilter, MessagesController.DialogFilter dialogFilter2) {
        int i = dialogFilter.order;
        int i2 = dialogFilter2.order;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:264:0x04fc, code lost:
    
        if (r13.indexOfKey(r6.id) >= 0) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x05c1, code lost:
    
        if (r20 == 0) goto L293;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x06aa  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02a5 A[Catch: all -> 0x069e, Exception -> 0x06a2, TryCatch #5 {Exception -> 0x06a2, all -> 0x069e, blocks: (B:7:0x000a, B:26:0x002b, B:28:0x002e, B:77:0x0120, B:80:0x0148, B:82:0x0156, B:84:0x017b, B:87:0x018a, B:88:0x0191, B:90:0x0195, B:92:0x01be, B:93:0x01a0, B:95:0x01a4, B:98:0x01a9, B:100:0x01b4, B:104:0x01d2, B:106:0x01e0, B:108:0x01f7, B:110:0x0207, B:112:0x0219, B:114:0x0222, B:118:0x0291, B:119:0x0237, B:121:0x024f, B:124:0x025e, B:125:0x0265, B:127:0x0269, B:130:0x026e, B:131:0x0283, B:133:0x0279, B:137:0x029f, B:139:0x02a5, B:141:0x02b7, B:143:0x02c3, B:146:0x02ca, B:148:0x02df, B:152:0x02ef, B:155:0x02fa, B:156:0x0302, B:158:0x0308, B:160:0x030c, B:161:0x0321, B:163:0x033b, B:164:0x0317, B:166:0x0329, B:169:0x0341, B:170:0x034a, B:173:0x0350, B:178:0x0361, B:179:0x037b, B:181:0x0380, B:183:0x0385, B:185:0x0392, B:186:0x039c, B:188:0x03a1, B:190:0x03af, B:191:0x03b6, B:193:0x03bb, B:195:0x03c0, B:197:0x03cd, B:198:0x03d3, B:200:0x03d8, B:202:0x03e6, B:203:0x03eb, B:205:0x03f0, B:207:0x03f5, B:209:0x0402, B:210:0x0408, B:212:0x040d, B:214:0x041b, B:215:0x0420, B:217:0x0425, B:219:0x042a, B:221:0x0437, B:222:0x043d, B:224:0x0442, B:226:0x0450, B:227:0x0455, B:229:0x045a, B:231:0x045f, B:233:0x046c, B:234:0x0472, B:236:0x0477, B:238:0x0485, B:240:0x048e, B:242:0x0497, B:248:0x04b7, B:252:0x04d0, B:254:0x04d4, B:255:0x04e5, B:257:0x04e8, B:260:0x0517, B:261:0x04ee, B:263:0x04f3, B:265:0x04fe, B:267:0x0504, B:269:0x0509, B:275:0x04d7, B:277:0x04db, B:280:0x04e0, B:281:0x04e3, B:282:0x04c6, B:291:0x0574, B:292:0x0526, B:294:0x0536, B:296:0x053c, B:298:0x0540, B:299:0x0545, B:301:0x0548, B:303:0x054b, B:305:0x0550, B:307:0x0559, B:310:0x0565, B:312:0x056a, B:317:0x0543, B:320:0x0580, B:322:0x058c, B:328:0x05ab, B:332:0x05c6, B:334:0x05ca, B:335:0x05db, B:340:0x0607, B:341:0x05de, B:343:0x05e3, B:346:0x05f2, B:348:0x05f7, B:351:0x05ff, B:355:0x05cd, B:357:0x05d1, B:360:0x05d6, B:361:0x05d9, B:362:0x05ba, B:369:0x0661, B:370:0x0616, B:372:0x0626, B:374:0x062c, B:376:0x0630, B:377:0x0635, B:379:0x0638, B:381:0x063d, B:384:0x0646, B:386:0x064b, B:388:0x0654, B:391:0x065f, B:394:0x0633, B:397:0x066a, B:399:0x0671, B:404:0x067c, B:406:0x0680, B:409:0x0683, B:411:0x0687, B:413:0x068b, B:421:0x0364, B:423:0x0368, B:425:0x0370, B:426:0x0373, B:427:0x0375, B:429:0x0377), top: B:6:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x06b2  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x048e A[Catch: all -> 0x069e, Exception -> 0x06a2, TryCatch #5 {Exception -> 0x06a2, all -> 0x069e, blocks: (B:7:0x000a, B:26:0x002b, B:28:0x002e, B:77:0x0120, B:80:0x0148, B:82:0x0156, B:84:0x017b, B:87:0x018a, B:88:0x0191, B:90:0x0195, B:92:0x01be, B:93:0x01a0, B:95:0x01a4, B:98:0x01a9, B:100:0x01b4, B:104:0x01d2, B:106:0x01e0, B:108:0x01f7, B:110:0x0207, B:112:0x0219, B:114:0x0222, B:118:0x0291, B:119:0x0237, B:121:0x024f, B:124:0x025e, B:125:0x0265, B:127:0x0269, B:130:0x026e, B:131:0x0283, B:133:0x0279, B:137:0x029f, B:139:0x02a5, B:141:0x02b7, B:143:0x02c3, B:146:0x02ca, B:148:0x02df, B:152:0x02ef, B:155:0x02fa, B:156:0x0302, B:158:0x0308, B:160:0x030c, B:161:0x0321, B:163:0x033b, B:164:0x0317, B:166:0x0329, B:169:0x0341, B:170:0x034a, B:173:0x0350, B:178:0x0361, B:179:0x037b, B:181:0x0380, B:183:0x0385, B:185:0x0392, B:186:0x039c, B:188:0x03a1, B:190:0x03af, B:191:0x03b6, B:193:0x03bb, B:195:0x03c0, B:197:0x03cd, B:198:0x03d3, B:200:0x03d8, B:202:0x03e6, B:203:0x03eb, B:205:0x03f0, B:207:0x03f5, B:209:0x0402, B:210:0x0408, B:212:0x040d, B:214:0x041b, B:215:0x0420, B:217:0x0425, B:219:0x042a, B:221:0x0437, B:222:0x043d, B:224:0x0442, B:226:0x0450, B:227:0x0455, B:229:0x045a, B:231:0x045f, B:233:0x046c, B:234:0x0472, B:236:0x0477, B:238:0x0485, B:240:0x048e, B:242:0x0497, B:248:0x04b7, B:252:0x04d0, B:254:0x04d4, B:255:0x04e5, B:257:0x04e8, B:260:0x0517, B:261:0x04ee, B:263:0x04f3, B:265:0x04fe, B:267:0x0504, B:269:0x0509, B:275:0x04d7, B:277:0x04db, B:280:0x04e0, B:281:0x04e3, B:282:0x04c6, B:291:0x0574, B:292:0x0526, B:294:0x0536, B:296:0x053c, B:298:0x0540, B:299:0x0545, B:301:0x0548, B:303:0x054b, B:305:0x0550, B:307:0x0559, B:310:0x0565, B:312:0x056a, B:317:0x0543, B:320:0x0580, B:322:0x058c, B:328:0x05ab, B:332:0x05c6, B:334:0x05ca, B:335:0x05db, B:340:0x0607, B:341:0x05de, B:343:0x05e3, B:346:0x05f2, B:348:0x05f7, B:351:0x05ff, B:355:0x05cd, B:357:0x05d1, B:360:0x05d6, B:361:0x05d9, B:362:0x05ba, B:369:0x0661, B:370:0x0616, B:372:0x0626, B:374:0x062c, B:376:0x0630, B:377:0x0635, B:379:0x0638, B:381:0x063d, B:384:0x0646, B:386:0x064b, B:388:0x0654, B:391:0x065f, B:394:0x0633, B:397:0x066a, B:399:0x0671, B:404:0x067c, B:406:0x0680, B:409:0x0683, B:411:0x0687, B:413:0x068b, B:421:0x0364, B:423:0x0368, B:425:0x0370, B:426:0x0373, B:427:0x0375, B:429:0x0377), top: B:6:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:402:0x0677  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void calcUnreadCounters(boolean r28) {
        /*
            Method dump skipped, instructions count: 1718
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.calcUnreadCounters(boolean):void");
    }

    private void saveDialogFilterInternal(MessagesController.DialogFilter dialogFilter, boolean z, boolean z2) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (!this.dialogFilters.contains(dialogFilter)) {
                    if (z) {
                        if (this.dialogFilters.get(0).isDefault()) {
                            this.dialogFilters.add(1, dialogFilter);
                        } else {
                            this.dialogFilters.add(0, dialogFilter);
                        }
                    } else {
                        this.dialogFilters.add(dialogFilter);
                    }
                    this.dialogFiltersMap.put(dialogFilter.id, dialogFilter);
                }
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO dialog_filter VALUES(?, ?, ?, ?, ?)");
                try {
                    executeFast.bindInteger(1, dialogFilter.id);
                    executeFast.bindInteger(2, dialogFilter.order);
                    executeFast.bindInteger(3, dialogFilter.unreadCount);
                    executeFast.bindInteger(4, dialogFilter.flags);
                    executeFast.bindString(5, dialogFilter.id == 0 ? "ALL_CHATS" : dialogFilter.name);
                    executeFast.step();
                    executeFast.dispose();
                    if (z2) {
                        this.database.executeFast("DELETE FROM dialog_filter_ep WHERE id = " + dialogFilter.id).stepThis().dispose();
                        this.database.executeFast("DELETE FROM dialog_filter_pin_v2 WHERE id = " + dialogFilter.id).stepThis().dispose();
                        this.database.beginTransaction();
                        SQLitePreparedStatement executeFast2 = this.database.executeFast("REPLACE INTO dialog_filter_pin_v2 VALUES(?, ?, ?)");
                        int size = dialogFilter.alwaysShow.size();
                        for (int i = 0; i < size; i++) {
                            long longValue = dialogFilter.alwaysShow.get(i).longValue();
                            executeFast2.requery();
                            executeFast2.bindInteger(1, dialogFilter.id);
                            executeFast2.bindLong(2, longValue);
                            executeFast2.bindInteger(3, dialogFilter.pinnedDialogs.get(longValue, LinearLayoutManager.INVALID_OFFSET));
                            executeFast2.step();
                        }
                        int size2 = dialogFilter.pinnedDialogs.size();
                        for (int i2 = 0; i2 < size2; i2++) {
                            long keyAt = dialogFilter.pinnedDialogs.keyAt(i2);
                            if (DialogObject.isEncryptedDialog(keyAt)) {
                                executeFast2.requery();
                                executeFast2.bindInteger(1, dialogFilter.id);
                                executeFast2.bindLong(2, keyAt);
                                executeFast2.bindInteger(3, dialogFilter.pinnedDialogs.valueAt(i2));
                                executeFast2.step();
                            }
                        }
                        executeFast2.dispose();
                        executeFast = this.database.executeFast("REPLACE INTO dialog_filter_ep VALUES(?, ?)");
                        int size3 = dialogFilter.neverShow.size();
                        for (int i3 = 0; i3 < size3; i3++) {
                            executeFast.requery();
                            executeFast.bindInteger(1, dialogFilter.id);
                            executeFast.bindLong(2, dialogFilter.neverShow.get(i3).longValue());
                            executeFast.step();
                        }
                        executeFast.dispose();
                        this.database.commitTransaction();
                    }
                    SQLiteDatabase sQLiteDatabase = this.database;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.commitTransaction();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLitePreparedStatement = executeFast;
                    checkSQLException(e);
                    SQLiteDatabase sQLiteDatabase2 = this.database;
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLitePreparedStatement = executeFast;
                    SQLiteDatabase sQLiteDatabase3 = this.database;
                    if (sQLiteDatabase3 != null) {
                        sQLiteDatabase3.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private ArrayList<Long> toPeerIds(ArrayList<TLRPC$InputPeer> arrayList) {
        ArrayList<Long> arrayList2 = new ArrayList<>();
        if (arrayList == null) {
            return arrayList2;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TLRPC$InputPeer tLRPC$InputPeer = arrayList.get(i);
            if (tLRPC$InputPeer != null) {
                long j = tLRPC$InputPeer.user_id;
                if (j == 0) {
                    long j2 = tLRPC$InputPeer.chat_id;
                    if (j2 == 0) {
                        j2 = tLRPC$InputPeer.channel_id;
                    }
                    j = -j2;
                }
                arrayList2.add(Long.valueOf(j));
            }
        }
        return arrayList2;
    }

    public void checkLoadedRemoteFilters(final TLRPC$Vector tLRPC$Vector, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda193
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$checkLoadedRemoteFilters$52(tLRPC$Vector, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0262 A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0301 A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0313 A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x031d A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0199 A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01c7 A[Catch: Exception -> 0x056e, TryCatch #0 {Exception -> 0x056e, blocks: (B:3:0x0004, B:5:0x0012, B:7:0x0022, B:9:0x005d, B:11:0x0072, B:12:0x007e, B:14:0x0082, B:15:0x0085, B:17:0x0089, B:18:0x008c, B:20:0x0090, B:21:0x0093, B:23:0x0097, B:24:0x009a, B:26:0x009e, B:27:0x00a1, B:29:0x00a5, B:30:0x00a8, B:32:0x00ac, B:33:0x00af, B:35:0x00b3, B:37:0x00ba, B:38:0x00bd, B:40:0x00cf, B:42:0x00e0, B:43:0x00e7, B:45:0x00eb, B:46:0x00f1, B:48:0x0110, B:50:0x0124, B:54:0x0134, B:58:0x0142, B:60:0x015a, B:64:0x017e, B:65:0x016f, B:68:0x0190, B:70:0x0199, B:74:0x01b4, B:75:0x01a6, B:78:0x01b7, B:80:0x01c7, B:82:0x01db, B:83:0x01ee, B:85:0x01f8, B:87:0x0204, B:89:0x021c, B:91:0x0234, B:93:0x023a, B:96:0x0244, B:99:0x01e0, B:102:0x01e9, B:103:0x01e7, B:107:0x0262, B:108:0x026a, B:110:0x0270, B:116:0x0294, B:117:0x0299, B:119:0x029f, B:121:0x02a6, B:122:0x02ad, B:124:0x02b3, B:126:0x02bd, B:127:0x02c3, B:133:0x02db, B:135:0x02e8, B:141:0x02ef, B:145:0x02f2, B:144:0x02f7, B:149:0x02a2, B:150:0x0297, B:152:0x02fa, B:154:0x0301, B:156:0x0313, B:158:0x031d, B:161:0x0477, B:170:0x0337, B:173:0x035e, B:175:0x0367, B:177:0x0376, B:178:0x0389, B:180:0x0391, B:181:0x0396, B:183:0x03af, B:185:0x03b5, B:187:0x037b, B:190:0x0384, B:191:0x0382, B:195:0x03c1, B:197:0x03c8, B:198:0x03cd, B:200:0x03d4, B:202:0x03e2, B:204:0x03ec, B:205:0x03ef, B:207:0x03f7, B:210:0x0448, B:212:0x040c, B:215:0x041c, B:217:0x0432, B:218:0x0435, B:220:0x043d, B:224:0x041a, B:226:0x0456, B:227:0x03cb, B:228:0x03c4, B:230:0x046a, B:234:0x048b, B:237:0x049f, B:239:0x04b5, B:241:0x04ce, B:243:0x04db, B:245:0x04e9, B:247:0x04ff, B:249:0x050c, B:251:0x051a, B:253:0x052c, B:255:0x0532, B:257:0x0538, B:259:0x053e, B:262:0x0552, B:264:0x04c9), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$checkLoadedRemoteFilters$52(org.telegram.tgnet.TLRPC$Vector r36, java.lang.Runnable r37) {
        /*
            Method dump skipped, instructions count: 1395
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$checkLoadedRemoteFilters$52(org.telegram.tgnet.TLRPC$Vector, java.lang.Runnable):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$checkLoadedRemoteFilters$51(LongSparseIntArray longSparseIntArray, Long l, Long l2) {
        int i = longSparseIntArray.get(l.longValue());
        int i2 = longSparseIntArray.get(l2.longValue());
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processLoadedFilterPeersInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$processLoadedFilterPeers$54(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, TLRPC$messages_Dialogs tLRPC$messages_Dialogs2, ArrayList<TLRPC$User> arrayList, ArrayList<TLRPC$Chat> arrayList2, ArrayList<MessagesController.DialogFilter> arrayList3, SparseArray<MessagesController.DialogFilter> sparseArray, ArrayList<Integer> arrayList4, HashMap<Integer, HashSet<Long>> hashMap, HashSet<Integer> hashSet, Runnable runnable) {
        putUsersAndChats(arrayList, arrayList2, true, false);
        int size = sparseArray.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            lambda$deleteDialogFilter$55(sparseArray.valueAt(i));
            i++;
            z = true;
        }
        Iterator<Integer> it = hashSet.iterator();
        while (it.hasNext()) {
            MessagesController.DialogFilter dialogFilter = this.dialogFiltersMap.get(it.next().intValue());
            if (dialogFilter != null) {
                dialogFilter.pendingUnreadCount = -1;
            }
        }
        for (Map.Entry<Integer, HashSet<Long>> entry : hashMap.entrySet()) {
            MessagesController.DialogFilter dialogFilter2 = this.dialogFiltersMap.get(entry.getKey().intValue());
            if (dialogFilter2 != null) {
                Iterator<Long> it2 = entry.getValue().iterator();
                while (it2.hasNext()) {
                    dialogFilter2.pinnedDialogs.delete(it2.next().longValue());
                }
                z = true;
            }
        }
        int size2 = arrayList3.size();
        int i2 = 0;
        while (i2 < size2) {
            saveDialogFilterInternal(arrayList3.get(i2), false, true);
            i2++;
            z = true;
        }
        int size3 = this.dialogFilters.size();
        boolean z2 = false;
        for (int i3 = 0; i3 < size3; i3++) {
            MessagesController.DialogFilter dialogFilter3 = this.dialogFilters.get(i3);
            int indexOf = arrayList4.indexOf(Integer.valueOf(dialogFilter3.id));
            if (dialogFilter3.order != indexOf) {
                dialogFilter3.order = indexOf;
                z2 = true;
                z = true;
            }
        }
        if (z2) {
            Collections.sort(this.dialogFilters, new Comparator() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda213
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    int lambda$processLoadedFilterPeersInternal$53;
                    lambda$processLoadedFilterPeersInternal$53 = MessagesStorage.lambda$processLoadedFilterPeersInternal$53((MessagesController.DialogFilter) obj, (MessagesController.DialogFilter) obj2);
                    return lambda$processLoadedFilterPeersInternal$53;
                }
            });
            saveDialogFiltersOrderInternal();
        }
        int i4 = z ? 1 : 2;
        calcUnreadCounters(true);
        getMessagesController().processLoadedDialogFilters(new ArrayList<>(this.dialogFilters), tLRPC$messages_Dialogs, tLRPC$messages_Dialogs2, arrayList, arrayList2, null, i4, runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$processLoadedFilterPeersInternal$53(MessagesController.DialogFilter dialogFilter, MessagesController.DialogFilter dialogFilter2) {
        int i = dialogFilter.order;
        int i2 = dialogFilter2.order;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    protected void processLoadedFilterPeers(final TLRPC$messages_Dialogs tLRPC$messages_Dialogs, final TLRPC$messages_Dialogs tLRPC$messages_Dialogs2, final ArrayList<TLRPC$User> arrayList, final ArrayList<TLRPC$Chat> arrayList2, final ArrayList<MessagesController.DialogFilter> arrayList3, final SparseArray<MessagesController.DialogFilter> sparseArray, final ArrayList<Integer> arrayList4, final HashMap<Integer, HashSet<Long>> hashMap, final HashSet<Integer> hashSet, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda196
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$processLoadedFilterPeers$54(tLRPC$messages_Dialogs, tLRPC$messages_Dialogs2, arrayList, arrayList2, arrayList3, sparseArray, arrayList4, hashMap, hashSet, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: deleteDialogFilterInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteDialogFilter$55(MessagesController.DialogFilter dialogFilter) {
        try {
            this.dialogFilters.remove(dialogFilter);
            this.dialogFiltersMap.remove(dialogFilter.id);
            this.database.executeFast("DELETE FROM dialog_filter WHERE id = " + dialogFilter.id).stepThis().dispose();
            this.database.executeFast("DELETE FROM dialog_filter_ep WHERE id = " + dialogFilter.id).stepThis().dispose();
            this.database.executeFast("DELETE FROM dialog_filter_pin_v2 WHERE id = " + dialogFilter.id).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void deleteDialogFilter(final MessagesController.DialogFilter dialogFilter) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda167
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$deleteDialogFilter$55(dialogFilter);
            }
        });
    }

    public void saveDialogFilter(final MessagesController.DialogFilter dialogFilter, final boolean z, final boolean z2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda168
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveDialogFilter$57(dialogFilter, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFilter$57(MessagesController.DialogFilter dialogFilter, boolean z, boolean z2) {
        saveDialogFilterInternal(dialogFilter, z, z2);
        calcUnreadCounters(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveDialogFilter$56();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFilter$56() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
        getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE));
    }

    public void saveDialogFiltersOrderInternal() {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE dialog_filter SET ord = ?, flags = ? WHERE id = ?");
                int size = this.dialogFilters.size();
                for (int i = 0; i < size; i++) {
                    MessagesController.DialogFilter dialogFilter = this.dialogFilters.get(i);
                    sQLitePreparedStatement.requery();
                    sQLitePreparedStatement.bindInteger(1, dialogFilter.order);
                    sQLitePreparedStatement.bindInteger(2, dialogFilter.flags);
                    sQLitePreparedStatement.bindInteger(3, dialogFilter.id);
                    sQLitePreparedStatement.step();
                }
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void saveDialogFiltersOrder() {
        final ArrayList arrayList = new ArrayList(getMessagesController().dialogFilters);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda150
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveDialogFiltersOrder$58(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFiltersOrder$58(ArrayList arrayList) {
        this.dialogFilters.clear();
        this.dialogFiltersMap.clear();
        this.dialogFilters.addAll(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            ((MessagesController.DialogFilter) arrayList.get(i)).order = i;
            this.dialogFiltersMap.put(((MessagesController.DialogFilter) arrayList.get(i)).id, (MessagesController.DialogFilter) arrayList.get(i));
        }
        saveDialogFiltersOrderInternal();
    }

    protected static void addReplyMessages(TLRPC$Message tLRPC$Message, LongSparseArray<SparseArray<ArrayList<TLRPC$Message>>> longSparseArray, LongSparseArray<ArrayList<Integer>> longSparseArray2) {
        int i = tLRPC$Message.reply_to.reply_to_msg_id;
        long replyToDialogId = MessageObject.getReplyToDialogId(tLRPC$Message);
        SparseArray<ArrayList<TLRPC$Message>> sparseArray = longSparseArray.get(replyToDialogId);
        ArrayList<Integer> arrayList = longSparseArray2.get(replyToDialogId);
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            longSparseArray.put(replyToDialogId, sparseArray);
        }
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            longSparseArray2.put(replyToDialogId, arrayList);
        }
        ArrayList<TLRPC$Message> arrayList2 = sparseArray.get(tLRPC$Message.reply_to.reply_to_msg_id);
        if (arrayList2 == null) {
            arrayList2 = new ArrayList<>();
            sparseArray.put(tLRPC$Message.reply_to.reply_to_msg_id, arrayList2);
            if (!arrayList.contains(Integer.valueOf(tLRPC$Message.reply_to.reply_to_msg_id))) {
                arrayList.add(Integer.valueOf(tLRPC$Message.reply_to.reply_to_msg_id));
            }
        }
        arrayList2.add(tLRPC$Message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0092 A[Catch: all -> 0x0101, Exception -> 0x0103, TryCatch #1 {Exception -> 0x0103, blocks: (B:41:0x0045, B:26:0x008c, B:28:0x0092, B:30:0x0098, B:33:0x00d3, B:35:0x00da, B:38:0x00f4, B:25:0x0069), top: B:40:0x0045, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00f4 A[Catch: all -> 0x0101, Exception -> 0x0103, TRY_LEAVE, TryCatch #1 {Exception -> 0x0103, blocks: (B:41:0x0045, B:26:0x008c, B:28:0x0092, B:30:0x0098, B:33:0x00d3, B:35:0x00da, B:38:0x00f4, B:25:0x0069), top: B:40:0x0045, outer: #0 }] */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r14v10 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4, types: [boolean, int] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x0067 -> B:24:0x008c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void loadReplyMessages(androidx.collection.LongSparseArray<android.util.SparseArray<java.util.ArrayList<org.telegram.tgnet.TLRPC$Message>>> r20, androidx.collection.LongSparseArray<java.util.ArrayList<java.lang.Integer>> r21, java.util.ArrayList<java.lang.Long> r22, java.util.ArrayList<java.lang.Long> r23, boolean r24) throws org.telegram.SQLite.SQLiteException {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadReplyMessages(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, java.util.ArrayList, java.util.ArrayList, boolean):void");
    }

    public void loadUnreadMessages() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadUnreadMessages$60();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02d4 A[Catch: all -> 0x041c, Exception -> 0x041f, TryCatch #10 {Exception -> 0x041f, all -> 0x041c, blocks: (B:126:0x023d, B:128:0x0243, B:130:0x0249, B:133:0x0283, B:136:0x0292, B:139:0x02a2, B:141:0x02b2, B:143:0x02bc, B:144:0x02cc, B:146:0x02d4, B:148:0x02e0, B:149:0x0309, B:152:0x0316, B:155:0x031f, B:158:0x032b, B:164:0x02ea, B:166:0x02f2, B:168:0x02ff, B:171:0x029c, B:172:0x028c, B:173:0x027c, B:177:0x034b), top: B:125:0x023d }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x031a  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02ea A[Catch: all -> 0x041c, Exception -> 0x041f, TryCatch #10 {Exception -> 0x041f, all -> 0x041c, blocks: (B:126:0x023d, B:128:0x0243, B:130:0x0249, B:133:0x0283, B:136:0x0292, B:139:0x02a2, B:141:0x02b2, B:143:0x02bc, B:144:0x02cc, B:146:0x02d4, B:148:0x02e0, B:149:0x0309, B:152:0x0316, B:155:0x031f, B:158:0x032b, B:164:0x02ea, B:166:0x02f2, B:168:0x02ff, B:171:0x029c, B:172:0x028c, B:173:0x027c, B:177:0x034b), top: B:125:0x023d }] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v12, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$loadUnreadMessages$60() {
        /*
            Method dump skipped, instructions count: 1114
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadUnreadMessages$60():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUnreadMessages$59(LongSparseArray longSparseArray, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5) {
        getNotificationsController().processLoadedUnreadMessages(longSparseArray, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
    }

    public void putWallpapers(final ArrayList<TLRPC$WallPaper> arrayList, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda57
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putWallpapers$61(i, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putWallpapers$61(int r11, java.util.ArrayList r12) {
        /*
            r10 = this;
            r0 = 0
            r1 = 1
            if (r11 != r1) goto L13
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            java.lang.String r3 = "DELETE FROM wallpapers2 WHERE num >= -1"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.stepThis()     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            r2.dispose()     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
        L13:
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            if (r11 == 0) goto L23
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            java.lang.String r3 = "REPLACE INTO wallpapers2 VALUES(?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            goto L2b
        L23:
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            java.lang.String r3 = "UPDATE wallpapers2 SET data = ? WHERE uid = ?"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
        L2b:
            r3 = 0
            int r4 = r12.size()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
        L30:
            if (r3 >= r4) goto L73
            java.lang.Object r5 = r12.get(r3)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            org.telegram.tgnet.TLRPC$WallPaper r5 = (org.telegram.tgnet.TLRPC$WallPaper) r5     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r2.requery()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            org.telegram.tgnet.NativeByteBuffer r6 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            int r7 = r5.getObjectSize()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r5.serializeToStream(r6)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r7 = 2
            if (r11 == 0) goto L62
            long r8 = r5.id     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r2.bindLong(r1, r8)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r2.bindByteBuffer(r7, r6)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r5 = 3
            if (r11 >= 0) goto L59
            r2.bindInteger(r5, r11)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            goto L6a
        L59:
            if (r11 != r7) goto L5d
            r7 = -1
            goto L5e
        L5d:
            r7 = r3
        L5e:
            r2.bindInteger(r5, r7)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            goto L6a
        L62:
            r2.bindByteBuffer(r1, r6)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            long r8 = r5.id     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r2.bindLong(r7, r8)     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
        L6a:
            r2.step()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            r6.reuse()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            int r3 = r3 + 1
            goto L30
        L73:
            r2.dispose()     // Catch: java.lang.Throwable -> L83 java.lang.Exception -> L86
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            r11.commitTransaction()     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8b
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database
            if (r11 == 0) goto L9b
            r11.commitTransaction()
            goto L9b
        L83:
            r11 = move-exception
            r0 = r2
            goto L9c
        L86:
            r11 = move-exception
            r0 = r2
            goto L8c
        L89:
            r11 = move-exception
            goto L9c
        L8b:
            r11 = move-exception
        L8c:
            r10.checkSQLException(r11)     // Catch: java.lang.Throwable -> L89
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database
            if (r11 == 0) goto L96
            r11.commitTransaction()
        L96:
            if (r0 == 0) goto L9b
            r0.dispose()
        L9b:
            return
        L9c:
            org.telegram.SQLite.SQLiteDatabase r12 = r10.database
            if (r12 == 0) goto La3
            r12.commitTransaction()
        La3:
            if (r0 == 0) goto La8
            r0.dispose()
        La8:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putWallpapers$61(int, java.util.ArrayList):void");
    }

    public void deleteWallpaper(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$deleteWallpaper$62(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteWallpaper$62(long j) {
        try {
            this.database.executeFast("DELETE FROM wallpapers2 WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getWallpapers() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getWallpapers$64();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWallpapers$64() {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursor = this.database.queryFinalized("SELECT data FROM wallpapers2 WHERE 1 ORDER BY num ASC", new Object[0]);
                final ArrayList arrayList = new ArrayList();
                while (sQLiteCursor.next()) {
                    NativeByteBuffer byteBufferValue = sQLiteCursor.byteBufferValue(0);
                    if (byteBufferValue != null) {
                        TLRPC$WallPaper TLdeserialize = TLRPC$WallPaper.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        if (TLdeserialize != null) {
                            arrayList.add(TLdeserialize);
                        }
                    }
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.lambda$getWallpapers$63(arrayList);
                    }
                });
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor == null) {
                    return;
                }
            }
            sQLiteCursor.dispose();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getWallpapers$63(ArrayList arrayList) {
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.wallpapersDidLoad, arrayList);
    }

    public void addRecentLocalFile(final String str, final String str2, final TLRPC$Document tLRPC$Document) {
        if (str == null || str.length() == 0) {
            return;
        }
        if ((str2 == null || str2.length() == 0) && tLRPC$Document == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda178
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$addRecentLocalFile$65(tLRPC$Document, str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addRecentLocalFile$65(TLRPC$Document tLRPC$Document, String str, String str2) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (tLRPC$Document != null) {
                    sQLitePreparedStatement = this.database.executeFast("UPDATE web_recent_v3 SET document = ? WHERE image_url = ?");
                    sQLitePreparedStatement.requery();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$Document.getObjectSize());
                    tLRPC$Document.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatement.bindByteBuffer(1, nativeByteBuffer);
                    sQLitePreparedStatement.bindString(2, str);
                    sQLitePreparedStatement.step();
                    sQLitePreparedStatement.dispose();
                    nativeByteBuffer.reuse();
                } else {
                    sQLitePreparedStatement = this.database.executeFast("UPDATE web_recent_v3 SET local_url = ? WHERE image_url = ?");
                    sQLitePreparedStatement.requery();
                    sQLitePreparedStatement.bindString(1, str2);
                    sQLitePreparedStatement.bindString(2, str);
                    sQLitePreparedStatement.step();
                    sQLitePreparedStatement.dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void deleteUserChatHistory(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda90
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$deleteUserChatHistory$68(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00de A[Catch: all -> 0x00ed, Exception -> 0x00f0, TRY_LEAVE, TryCatch #7 {Exception -> 0x00f0, all -> 0x00ed, blocks: (B:3:0x0004, B:29:0x00a0, B:32:0x00c6, B:34:0x00de), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00fe  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$deleteUserChatHistory$68(final long r18, long r20) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$deleteUserChatHistory$68(long, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteUserChatHistory$66(ArrayList arrayList, long j, ArrayList arrayList2) {
        getFileLoader().cancelLoadFiles(arrayList);
        getMessagesController().markDialogMessageAsDeleted(j, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteUserChatHistory$67(ArrayList arrayList, long j) {
        NotificationCenter notificationCenter = getNotificationCenter();
        int i = NotificationCenter.messagesDeleted;
        Object[] objArr = new Object[3];
        objArr[0] = arrayList;
        objArr[1] = Long.valueOf(DialogObject.isChatDialog(j) ? -j : 0L);
        objArr[2] = Boolean.FALSE;
        notificationCenter.postNotificationName(i, objArr);
    }

    private boolean addFilesToDelete(TLRPC$Message tLRPC$Message, ArrayList<File> arrayList, ArrayList<Pair<Long, Integer>> arrayList2, ArrayList<String> arrayList3, boolean z) {
        long j;
        int i;
        int i2 = 0;
        if (tLRPC$Message == null) {
            return false;
        }
        TLRPC$Document document = MessageObject.getDocument(tLRPC$Message);
        TLRPC$Photo photo = MessageObject.getPhoto(tLRPC$Message);
        if (MessageObject.isVoiceMessage(tLRPC$Message)) {
            if (document == null || getMediaDataController().ringtoneDataStore.contains(document.id)) {
                return false;
            }
            j = document.id;
            i = 2;
        } else {
            if (MessageObject.isStickerMessage(tLRPC$Message) || MessageObject.isAnimatedStickerMessage(tLRPC$Message)) {
                if (document == null) {
                    return false;
                }
                j = document.id;
            } else if (MessageObject.isVideoMessage(tLRPC$Message) || MessageObject.isRoundVideoMessage(tLRPC$Message) || MessageObject.isGifMessage(tLRPC$Message)) {
                if (document == null) {
                    return false;
                }
                j = document.id;
                i = 4;
            } else if (document != null) {
                if (getMediaDataController().ringtoneDataStore.contains(document.id)) {
                    return false;
                }
                j = document.id;
                i = 8;
            } else if (photo == null || FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize()) == null) {
                j = 0;
                i = 0;
            } else {
                j = photo.id;
            }
            i = 1;
        }
        if (j != 0) {
            arrayList2.add(new Pair<>(Long.valueOf(j), Integer.valueOf(i)));
        }
        if (photo != null) {
            int size = photo.sizes.size();
            while (i2 < size) {
                TLRPC$PhotoSize tLRPC$PhotoSize = photo.sizes.get(i2);
                String attachFileName = FileLoader.getAttachFileName(tLRPC$PhotoSize);
                if (!TextUtils.isEmpty(attachFileName)) {
                    arrayList3.add(attachFileName);
                }
                File pathToAttach = getFileLoader().getPathToAttach(tLRPC$PhotoSize, z);
                if (pathToAttach.toString().length() > 0) {
                    arrayList.add(pathToAttach);
                }
                i2++;
            }
            return true;
        }
        if (document == null) {
            return false;
        }
        String attachFileName2 = FileLoader.getAttachFileName(document);
        if (!TextUtils.isEmpty(attachFileName2)) {
            arrayList3.add(attachFileName2);
        }
        File pathToAttach2 = getFileLoader().getPathToAttach(document, z);
        if (pathToAttach2.toString().length() > 0) {
            arrayList.add(pathToAttach2);
        }
        int size2 = document.thumbs.size();
        while (i2 < size2) {
            File pathToAttach3 = getFileLoader().getPathToAttach(document.thumbs.get(i2));
            if (pathToAttach3.toString().length() > 0) {
                arrayList.add(pathToAttach3);
            }
            i2++;
        }
        return true;
    }

    public void deleteDialog(long j, int i) {
        deleteDialog(j, i, false);
    }

    public void deleteDialog(final long j, final int i, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda54
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$deleteDialog$71(i, j, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0514 A[Catch: all -> 0x053a, Exception -> 0x0542, TryCatch #21 {Exception -> 0x0542, all -> 0x053a, blocks: (B:189:0x000c, B:3:0x0048, B:14:0x0115, B:138:0x0421, B:140:0x0514, B:141:0x051c, B:144:0x033d, B:146:0x03d9, B:148:0x03df, B:149:0x03ff, B:150:0x005b, B:173:0x00eb), top: B:188:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03d9 A[Catch: all -> 0x053a, Exception -> 0x0542, TryCatch #21 {Exception -> 0x0542, all -> 0x053a, blocks: (B:189:0x000c, B:3:0x0048, B:14:0x0115, B:138:0x0421, B:140:0x0514, B:141:0x051c, B:144:0x033d, B:146:0x03d9, B:148:0x03df, B:149:0x03ff, B:150:0x005b, B:173:0x00eb), top: B:188:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03ff A[Catch: all -> 0x053a, Exception -> 0x0542, TryCatch #21 {Exception -> 0x0542, all -> 0x053a, blocks: (B:189:0x000c, B:3:0x0048, B:14:0x0115, B:138:0x0421, B:140:0x0514, B:141:0x051c, B:144:0x033d, B:146:0x03d9, B:148:0x03df, B:149:0x03ff, B:150:0x005b, B:173:0x00eb), top: B:188:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x054e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0553  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0558  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x055d  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0565  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0574  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r15v36, types: [org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r1v84, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r24v0, types: [org.telegram.messenger.BaseController, org.telegram.messenger.MessagesStorage] */
    /* JADX WARN: Type inference failed for: r3v26, types: [org.telegram.tgnet.AbstractSerializedData, org.telegram.tgnet.NativeByteBuffer] */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v3, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r4v1, types: [org.telegram.tgnet.AbstractSerializedData, org.telegram.tgnet.NativeByteBuffer] */
    /* JADX WARN: Type inference failed for: r6v1, types: [org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v29 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$deleteDialog$71(int r25, long r26, boolean r28) {
        /*
            Method dump skipped, instructions count: 1400
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$deleteDialog$71(int, long, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDialog$69(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDialog$70() {
        getNotificationCenter().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
    }

    public void onDeleteQueryComplete(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$onDeleteQueryComplete$72(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeleteQueryComplete$72(long j) {
        try {
            this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getDialogPhotos(final long j, final int i, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDialogPhotos$74(i2, j, i, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogPhotos$74(final int i, final long j, final int i2, final int i3) {
        SQLiteCursor queryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (i != 0) {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d AND id < %d ORDER BY rowid ASC LIMIT %d", Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2)), new Object[0]);
                } else {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d ORDER BY rowid ASC LIMIT %d", Long.valueOf(j), Integer.valueOf(i2)), new Object[0]);
                }
                SQLiteCursor sQLiteCursor2 = queryFinalized;
                try {
                    final TLRPC$TL_photos_photos tLRPC$TL_photos_photos = new TLRPC$TL_photos_photos();
                    final ArrayList arrayList = new ArrayList();
                    while (sQLiteCursor2.next()) {
                        NativeByteBuffer byteBufferValue = sQLiteCursor2.byteBufferValue(0);
                        if (byteBufferValue != null) {
                            TLRPC$Photo TLdeserialize = TLRPC$Photo.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            if (byteBufferValue.remaining() > 0) {
                                arrayList.add(TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false));
                            } else {
                                arrayList.add(null);
                            }
                            byteBufferValue.reuse();
                            tLRPC$TL_photos_photos.photos.add(TLdeserialize);
                        }
                    }
                    sQLiteCursor2.dispose();
                    Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda197
                        @Override // java.lang.Runnable
                        public final void run() {
                            MessagesStorage.this.lambda$getDialogPhotos$73(tLRPC$TL_photos_photos, arrayList, j, i2, i, i3);
                        }
                    });
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = sQLiteCursor2;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = sQLiteCursor2;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogPhotos$73(TLRPC$photos_Photos tLRPC$photos_Photos, ArrayList arrayList, long j, int i, int i2, int i3) {
        getMessagesController().processLoadedUserPhotos(tLRPC$photos_Photos, arrayList, j, i, i2, true, i3);
    }

    public void clearUserPhotos(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda70
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearUserPhotos$75(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearUserPhotos$75(long j) {
        try {
            this.database.executeFast("DELETE FROM user_photos WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearUserPhoto(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearUserPhoto$76(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearUserPhoto$76(long j, long j2) {
        try {
            this.database.executeFast("DELETE FROM user_photos WHERE uid = " + j + " AND id = " + j2).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void resetDialogs(final TLRPC$messages_Dialogs tLRPC$messages_Dialogs, final int i, final int i2, final int i3, final int i4, final int i5, final LongSparseArray<TLRPC$Dialog> longSparseArray, final LongSparseArray<ArrayList<MessageObject>> longSparseArray2, final TLRPC$Message tLRPC$Message, final int i6) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda195
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$resetDialogs$78(tLRPC$messages_Dialogs, i6, i2, i3, i4, i5, tLRPC$Message, i, longSparseArray, longSparseArray2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:125:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x02ee A[Catch: all -> 0x0342, Exception -> 0x0345, LOOP:5: B:69:0x02eb->B:71:0x02ee, LOOP_END, TryCatch #4 {Exception -> 0x0345, all -> 0x0342, blocks: (B:3:0x0006, B:4:0x0024, B:6:0x002c, B:8:0x0040, B:31:0x0094, B:33:0x01da, B:35:0x01e8, B:39:0x022e, B:40:0x01ed, B:44:0x0208, B:46:0x0210, B:47:0x0213, B:49:0x0223, B:50:0x0225, B:52:0x0229, B:56:0x0234, B:59:0x0261, B:61:0x0269, B:65:0x0277, B:71:0x02ee, B:73:0x030d, B:63:0x027a, B:83:0x0287, B:86:0x0290, B:88:0x0298, B:92:0x02a6, B:90:0x02a9, B:96:0x02b7, B:99:0x02c0, B:101:0x02c8, B:105:0x02d6, B:103:0x02dd), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$resetDialogs$78(org.telegram.tgnet.TLRPC$messages_Dialogs r33, int r34, int r35, int r36, int r37, int r38, org.telegram.tgnet.TLRPC$Message r39, int r40, androidx.collection.LongSparseArray r41, androidx.collection.LongSparseArray r42) {
        /*
            Method dump skipped, instructions count: 869
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$resetDialogs$78(org.telegram.tgnet.TLRPC$messages_Dialogs, int, int, int, int, int, org.telegram.tgnet.TLRPC$Message, int, androidx.collection.LongSparseArray, androidx.collection.LongSparseArray):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$resetDialogs$77(LongSparseIntArray longSparseIntArray, Long l, Long l2) {
        int i = longSparseIntArray.get(l.longValue());
        int i2 = longSparseIntArray.get(l2.longValue());
        if (i < i2) {
            return 1;
        }
        return i > i2 ? -1 : 0;
    }

    public void putDialogPhotos(final long j, final TLRPC$photos_Photos tLRPC$photos_Photos, final ArrayList<TLRPC$Message> arrayList) {
        if (tLRPC$photos_Photos == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda121
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putDialogPhotos$79(j, tLRPC$photos_Photos, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: putDialogPhotosInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$putDialogPhotos$79(long j, TLRPC$photos_Photos tLRPC$photos_Photos, ArrayList<TLRPC$Message> arrayList) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.executeFast("DELETE FROM user_photos WHERE uid = " + j).stepThis().dispose();
                sQLitePreparedStatement = this.database.executeFast("REPLACE INTO user_photos VALUES(?, ?, ?)");
                int size = tLRPC$photos_Photos.photos.size();
                for (int i = 0; i < size; i++) {
                    TLRPC$Photo tLRPC$Photo = tLRPC$photos_Photos.photos.get(i);
                    if (!(tLRPC$Photo instanceof TLRPC$TL_photoEmpty) && tLRPC$Photo != null) {
                        if (tLRPC$Photo.file_reference == null) {
                            tLRPC$Photo.file_reference = new byte[0];
                        }
                        sQLitePreparedStatement.requery();
                        int objectSize = tLRPC$Photo.getObjectSize();
                        if (arrayList != null && i < arrayList.size() && arrayList.get(i) != null) {
                            objectSize += arrayList.get(i).getObjectSize();
                        }
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(objectSize);
                        tLRPC$Photo.serializeToStream(nativeByteBuffer);
                        if (arrayList != null && i < arrayList.size() && arrayList.get(i) != null) {
                            arrayList.get(i).serializeToStream(nativeByteBuffer);
                        }
                        sQLitePreparedStatement.bindLong(1, j);
                        sQLitePreparedStatement.bindLong(2, tLRPC$Photo.id);
                        sQLitePreparedStatement.bindByteBuffer(3, nativeByteBuffer);
                        sQLitePreparedStatement.step();
                        nativeByteBuffer.reuse();
                    }
                }
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void addDialogPhoto(final long j, final TLRPC$Photo tLRPC$Photo) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda116
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$addDialogPhoto$80(j, tLRPC$Photo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addDialogPhoto$80(long j, TLRPC$Photo tLRPC$Photo) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM user_photos WHERE uid = %d ORDER BY rowid ASC", Long.valueOf(j)), new Object[0]);
                try {
                    TLRPC$TL_photos_photos tLRPC$TL_photos_photos = new TLRPC$TL_photos_photos();
                    ArrayList<TLRPC$Message> arrayList = new ArrayList<>();
                    while (queryFinalized.next()) {
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                        if (byteBufferValue != null) {
                            TLRPC$Photo TLdeserialize = TLRPC$Photo.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            if (byteBufferValue.remaining() > 0) {
                                arrayList.add(TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false));
                            } else {
                                arrayList.add(null);
                            }
                            byteBufferValue.reuse();
                            tLRPC$TL_photos_photos.photos.add(TLdeserialize);
                            arrayList.add(null);
                        }
                    }
                    queryFinalized.dispose();
                    tLRPC$TL_photos_photos.photos.add(0, tLRPC$Photo);
                    lambda$putDialogPhotos$79(j, tLRPC$TL_photos_photos, arrayList);
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void emptyMessagesMedia(final long j, final ArrayList<Integer> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda152
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$emptyMessagesMedia$83(arrayList, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x012c A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0162 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0180 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01a0 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b0 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01c1 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c6 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01b6 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a4 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x018b A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0176 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0132 A[Catch: all -> 0x01dc, Exception -> 0x01e1, TryCatch #6 {Exception -> 0x01e1, all -> 0x01dc, blocks: (B:36:0x00d0, B:38:0x00d6, B:40:0x0112, B:44:0x011a, B:46:0x012c, B:47:0x0139, B:50:0x0144, B:53:0x0152, B:55:0x0162, B:56:0x017a, B:58:0x0180, B:61:0x0187, B:62:0x018f, B:64:0x01a0, B:65:0x01a7, B:67:0x01b0, B:68:0x01b9, B:70:0x01c1, B:72:0x01c6, B:74:0x01c9, B:76:0x01b6, B:77:0x01a4, B:78:0x0185, B:79:0x018b, B:80:0x0176, B:85:0x0132, B:88:0x01d0), top: B:35:0x00d0 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0210  */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$emptyMessagesMedia$83(java.util.ArrayList r19, long r20) {
        /*
            Method dump skipped, instructions count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$emptyMessagesMedia$83(java.util.ArrayList, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$emptyMessagesMedia$81(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            getNotificationCenter().postNotificationName(NotificationCenter.updateMessageMedia, arrayList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$emptyMessagesMedia$82(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    public void updateMessagePollResults(final long j, final TLRPC$Poll tLRPC$Poll, final TLRPC$PollResults tLRPC$PollResults) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda117
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessagePollResults$84(j, tLRPC$Poll, tLRPC$PollResults);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessagePollResults$84(long j, TLRPC$Poll tLRPC$Poll, TLRPC$PollResults tLRPC$PollResults) {
        LongSparseArray longSparseArray;
        SQLitePreparedStatement sQLitePreparedStatement;
        int i;
        SQLiteCursor queryFinalized;
        ArrayList arrayList;
        int i2;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                int i3 = 1;
                int i4 = 0;
                SQLiteCursor queryFinalized2 = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, mid FROM polls_v2 WHERE id = %d", Long.valueOf(j)), new Object[0]);
                LongSparseArray longSparseArray2 = null;
                while (queryFinalized2.next()) {
                    try {
                        long longValue = queryFinalized2.longValue(0);
                        if (longSparseArray2 == null) {
                            longSparseArray2 = new LongSparseArray();
                        }
                        ArrayList arrayList2 = (ArrayList) longSparseArray2.get(longValue);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray2.put(longValue, arrayList2);
                        }
                        arrayList2.add(Integer.valueOf(queryFinalized2.intValue(1)));
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized2;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized2;
                    }
                }
                queryFinalized2.dispose();
                if (longSparseArray2 != null) {
                    this.database.beginTransaction();
                    SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE messages_v2 SET data = ? WHERE mid = ? AND uid = ?");
                    SQLitePreparedStatement executeFast2 = this.database.executeFast("UPDATE messages_topics SET data = ? WHERE mid = ? AND uid = ?");
                    int size = longSparseArray2.size();
                    int i5 = 0;
                    while (i5 < size) {
                        long keyAt = longSparseArray2.keyAt(i5);
                        ArrayList arrayList3 = (ArrayList) longSparseArray2.valueAt(i5);
                        int size2 = arrayList3.size();
                        int i6 = 0;
                        while (i6 < size2) {
                            Integer num = (Integer) arrayList3.get(i6);
                            SQLiteCursor sQLiteCursor2 = sQLiteCursor;
                            boolean z = false;
                            while (i4 < 2) {
                                boolean z2 = i4 == i3;
                                if (z2) {
                                    try {
                                        longSparseArray = longSparseArray2;
                                        sQLitePreparedStatement = executeFast2;
                                        i = size;
                                        queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_topics WHERE mid = %d AND uid = %d", num, Long.valueOf(keyAt)), new Object[0]);
                                    } catch (Exception e2) {
                                        e = e2;
                                        sQLiteCursor = sQLiteCursor2;
                                        checkSQLException(e);
                                        if (sQLiteCursor == null) {
                                            return;
                                        }
                                        sQLiteCursor.dispose();
                                    } catch (Throwable th2) {
                                        th = th2;
                                        sQLiteCursor = sQLiteCursor2;
                                        if (sQLiteCursor != null) {
                                            sQLiteCursor.dispose();
                                        }
                                        throw th;
                                    }
                                } else {
                                    longSparseArray = longSparseArray2;
                                    sQLitePreparedStatement = executeFast2;
                                    i = size;
                                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE mid = %d AND uid = %d", num, Long.valueOf(keyAt)), new Object[0]);
                                }
                                SQLitePreparedStatement sQLitePreparedStatement2 = z2 ? sQLitePreparedStatement : executeFast;
                                if (queryFinalized.next()) {
                                    NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                                    if (byteBufferValue != null) {
                                        TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                        arrayList = arrayList3;
                                        i2 = size2;
                                        TLdeserialize.readAttachPath(byteBufferValue, getUserConfig().clientUserId);
                                        byteBufferValue.reuse();
                                        TLRPC$MessageMedia tLRPC$MessageMedia = TLdeserialize.media;
                                        if (tLRPC$MessageMedia instanceof TLRPC$TL_messageMediaPoll) {
                                            TLRPC$TL_messageMediaPoll tLRPC$TL_messageMediaPoll = (TLRPC$TL_messageMediaPoll) tLRPC$MessageMedia;
                                            if (tLRPC$Poll != null) {
                                                tLRPC$TL_messageMediaPoll.poll = tLRPC$Poll;
                                            }
                                            if (tLRPC$PollResults != null) {
                                                MessageObject.updatePollResults(tLRPC$TL_messageMediaPoll, tLRPC$PollResults);
                                            }
                                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(TLdeserialize.getObjectSize());
                                            TLdeserialize.serializeToStream(nativeByteBuffer);
                                            sQLitePreparedStatement2.requery();
                                            sQLitePreparedStatement2.bindByteBuffer(1, nativeByteBuffer);
                                            sQLitePreparedStatement2.bindInteger(2, num.intValue());
                                            sQLitePreparedStatement2.bindLong(3, keyAt);
                                            sQLitePreparedStatement2.step();
                                            nativeByteBuffer.reuse();
                                        }
                                    } else {
                                        arrayList = arrayList3;
                                        i2 = size2;
                                    }
                                    z = true;
                                } else {
                                    arrayList = arrayList3;
                                    i2 = size2;
                                }
                                queryFinalized.dispose();
                                i4++;
                                sQLiteCursor2 = queryFinalized;
                                arrayList3 = arrayList;
                                longSparseArray2 = longSparseArray;
                                executeFast2 = sQLitePreparedStatement;
                                size = i;
                                size2 = i2;
                                i3 = 1;
                            }
                            LongSparseArray longSparseArray3 = longSparseArray2;
                            SQLitePreparedStatement sQLitePreparedStatement3 = executeFast2;
                            int i7 = size;
                            ArrayList arrayList4 = arrayList3;
                            int i8 = size2;
                            if (!z) {
                                this.database.executeFast(String.format(Locale.US, "DELETE FROM polls_v2 WHERE mid = %d AND uid = %d", num, Long.valueOf(keyAt))).stepThis().dispose();
                            }
                            i6++;
                            sQLiteCursor = sQLiteCursor2;
                            arrayList3 = arrayList4;
                            longSparseArray2 = longSparseArray3;
                            executeFast2 = sQLitePreparedStatement3;
                            size = i7;
                            size2 = i8;
                            i3 = 1;
                            i4 = 0;
                        }
                        i5++;
                        size = size;
                        i3 = 1;
                        i4 = 0;
                    }
                    executeFast.dispose();
                    this.database.commitTransaction();
                }
                if (sQLiteCursor == null) {
                    return;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e3) {
            e = e3;
        }
        sQLiteCursor.dispose();
    }

    public void updateMessageReactions(final long j, final int i, final TLRPC$TL_messageReactions tLRPC$TL_messageReactions) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageReactions$85(i, j, tLRPC$TL_messageReactions);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageReactions$85(int i, long j, TLRPC$TL_messageReactions tLRPC$TL_messageReactions) {
        SQLiteCursor queryFinalized;
        NativeByteBuffer byteBufferValue;
        SQLitePreparedStatement executeFast;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                this.database.beginTransaction();
                for (int i2 = 0; i2 < 2; i2++) {
                    if (i2 == 0) {
                        queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j)), new Object[0]);
                    } else {
                        queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_topics WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j)), new Object[0]);
                    }
                    try {
                        if (queryFinalized.next() && (byteBufferValue = queryFinalized.byteBufferValue(0)) != null) {
                            TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            if (TLdeserialize != null) {
                                TLdeserialize.readAttachPath(byteBufferValue, getUserConfig().clientUserId);
                                byteBufferValue.reuse();
                                MessageObject.updateReactions(TLdeserialize, tLRPC$TL_messageReactions);
                                if (i2 == 0) {
                                    executeFast = this.database.executeFast("UPDATE messages_v2 SET data = ? WHERE mid = ? AND uid = ?");
                                } else {
                                    executeFast = this.database.executeFast("UPDATE messages_topics SET data = ? WHERE mid = ? AND uid = ?");
                                }
                                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(TLdeserialize.getObjectSize());
                                TLdeserialize.serializeToStream(nativeByteBuffer);
                                executeFast.requery();
                                executeFast.bindByteBuffer(1, nativeByteBuffer);
                                executeFast.bindInteger(2, i);
                                executeFast.bindLong(3, j);
                                executeFast.step();
                                nativeByteBuffer.reuse();
                                executeFast.dispose();
                            } else {
                                byteBufferValue.reuse();
                            }
                        }
                        queryFinalized.dispose();
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                }
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateMessageVoiceTranscriptionOpen(final long j, final int i, final TLRPC$Message tLRPC$Message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda51
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageVoiceTranscriptionOpen$86(i, j, tLRPC$Message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscriptionOpen$86(int i, long j, TLRPC$Message tLRPC$Message) {
        SQLitePreparedStatement executeFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC$Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionOpen = tLRPC$Message.voiceTranscriptionOpen;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionRated = tLRPC$Message.voiceTranscriptionRated;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = tLRPC$Message.voiceTranscriptionFinal;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionForce = tLRPC$Message.voiceTranscriptionForce;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = tLRPC$Message.voiceTranscriptionId;
                for (int i2 = 0; i2 < 2; i2++) {
                    if (i2 == 0) {
                        executeFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        executeFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        executeFast.requery();
                        NativeByteBuffer writeLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (writeLocalParams != null) {
                            executeFast.bindByteBuffer(1, writeLocalParams);
                        } else {
                            executeFast.bindNull(1);
                        }
                        executeFast.bindInteger(2, i);
                        executeFast.bindLong(3, j);
                        executeFast.step();
                        executeFast.dispose();
                        if (writeLocalParams != null) {
                            writeLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void updateMessageVoiceTranscription(final long j, final int i, final String str, final long j2, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageVoiceTranscription$87(i, j, z, j2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscription$87(int i, long j, boolean z, long j2, String str) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC$Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = z;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = j2;
                messageWithCustomParamsOnlyInternal.voiceTranscription = str;
                SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                try {
                    executeFast.requery();
                    NativeByteBuffer writeLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                    if (writeLocalParams != null) {
                        executeFast.bindByteBuffer(1, writeLocalParams);
                    } else {
                        executeFast.bindNull(1);
                    }
                    executeFast.bindInteger(2, i);
                    executeFast.bindLong(3, j);
                    executeFast.step();
                    executeFast.dispose();
                    this.database.commitTransaction();
                    if (writeLocalParams != null) {
                        writeLocalParams.reuse();
                    }
                    SQLiteDatabase sQLiteDatabase = this.database;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.commitTransaction();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLitePreparedStatement = executeFast;
                    checkSQLException(e);
                    SQLiteDatabase sQLiteDatabase2 = this.database;
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLitePreparedStatement = executeFast;
                    SQLiteDatabase sQLiteDatabase3 = this.database;
                    if (sQLiteDatabase3 != null) {
                        sQLiteDatabase3.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void updateMessageVoiceTranscription(final long j, final int i, final String str, final TLRPC$Message tLRPC$Message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageVoiceTranscription$88(i, j, tLRPC$Message, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscription$88(int i, long j, TLRPC$Message tLRPC$Message, String str) {
        SQLitePreparedStatement executeFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC$Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionOpen = tLRPC$Message.voiceTranscriptionOpen;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionRated = tLRPC$Message.voiceTranscriptionRated;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = tLRPC$Message.voiceTranscriptionFinal;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionForce = tLRPC$Message.voiceTranscriptionForce;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = tLRPC$Message.voiceTranscriptionId;
                messageWithCustomParamsOnlyInternal.voiceTranscription = str;
                for (int i2 = 0; i2 < 2; i2++) {
                    if (i2 == 0) {
                        executeFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        executeFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        executeFast.requery();
                        NativeByteBuffer writeLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (writeLocalParams != null) {
                            executeFast.bindByteBuffer(1, writeLocalParams);
                        } else {
                            executeFast.bindNull(1);
                        }
                        executeFast.bindInteger(2, i);
                        executeFast.bindLong(3, j);
                        executeFast.step();
                        executeFast.dispose();
                        this.database.commitTransaction();
                        if (writeLocalParams != null) {
                            writeLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateMessageCustomParams(final long j, final TLRPC$Message tLRPC$Message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda186
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageCustomParams$89(tLRPC$Message, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageCustomParams$89(TLRPC$Message tLRPC$Message, long j) {
        SQLitePreparedStatement executeFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC$Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(tLRPC$Message.id, j);
                MessageCustomParamsHelper.copyParams(tLRPC$Message, messageWithCustomParamsOnlyInternal);
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        executeFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        executeFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        executeFast.requery();
                        NativeByteBuffer writeLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (writeLocalParams != null) {
                            executeFast.bindByteBuffer(1, writeLocalParams);
                        } else {
                            executeFast.bindNull(1);
                        }
                        executeFast.bindInteger(2, tLRPC$Message.id);
                        executeFast.bindLong(3, j);
                        executeFast.step();
                        executeFast.dispose();
                        if (writeLocalParams != null) {
                            writeLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public TLRPC$Message getMessageWithCustomParamsOnlyInternal(int i, long j) {
        boolean z;
        TLRPC$TL_message tLRPC$TL_message = new TLRPC$TL_message();
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT custom_params FROM messages_v2 WHERE mid = " + i + " AND uid = " + j, new Object[0]);
                try {
                    if (queryFinalized.next()) {
                        MessageCustomParamsHelper.readLocalParams(tLRPC$TL_message, queryFinalized.byteBufferValue(0));
                        z = true;
                    } else {
                        z = false;
                    }
                    queryFinalized.dispose();
                    if (!z) {
                        sQLiteCursor = this.database.queryFinalized("SELECT custom_params FROM messages_topics WHERE mid = " + i + " AND uid = " + j, new Object[0]);
                        if (sQLiteCursor.next()) {
                            MessageCustomParamsHelper.readLocalParams(tLRPC$TL_message, sQLiteCursor.byteBufferValue(0));
                        }
                        sQLiteCursor.dispose();
                    }
                } catch (SQLiteException e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    return tLRPC$TL_message;
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                e = e2;
            }
            return tLRPC$TL_message;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void getNewTask(final LongSparseArray<ArrayList<Integer>> longSparseArray, final LongSparseArray<ArrayList<Integer>> longSparseArray2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda131
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getNewTask$90(longSparseArray, longSparseArray2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00a5, code lost:
    
        if (r15 > 0) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00f9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getNewTask$90(androidx.collection.LongSparseArray r14, androidx.collection.LongSparseArray r15) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getNewTask$90(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray):void");
    }

    public void markMentionMessageAsRead(final long j, final int i, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$markMentionMessageAsRead$91(i, j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMentionMessageAsRead$91(int i, long j, long j2) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteDatabase sQLiteDatabase = this.database;
                Locale locale = Locale.US;
                sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                try {
                    int max = queryFinalized.next() ? Math.max(0, queryFinalized.intValue(0) - 1) : 0;
                    queryFinalized.dispose();
                    this.database.executeFast(String.format(locale, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", Integer.valueOf(max), Long.valueOf(j2))).stepThis().dispose();
                    LongSparseIntArray longSparseIntArray = new LongSparseIntArray(1);
                    longSparseIntArray.put(j2, max);
                    if (max == 0) {
                        updateFiltersReadCounter(null, longSparseIntArray, true);
                    }
                    getMessagesController().processDialogsUpdateRead(null, longSparseIntArray);
                    this.database.executeFast(String.format(locale, "UPDATE messages_topics SET read_state = read_state | 2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
                    SQLiteCursor queryFinalized2 = this.database.queryFinalized(String.format(locale, "SELECT data FROM messages_topics WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j)), new Object[0]);
                    int i2 = 0;
                    while (queryFinalized2.next()) {
                        try {
                            NativeByteBuffer byteBufferValue = queryFinalized2.byteBufferValue(0);
                            if (byteBufferValue != null) {
                                TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                                byteBufferValue.reuse();
                                i2 = MessageObject.getTopicId(TLdeserialize, isForum(j));
                            }
                        } catch (Exception e) {
                            sQLiteCursor = queryFinalized2;
                            e = e;
                            checkSQLException(e);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            sQLiteCursor = queryFinalized2;
                            th = th;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            throw th;
                        }
                    }
                    queryFinalized2.dispose();
                    if (i2 != 0) {
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        Locale locale2 = Locale.US;
                        queryFinalized2 = sQLiteDatabase2.queryFinalized(String.format(locale2, "SELECT unread_mentions FROM topics WHERE did = %d AND topic_id = %d", Long.valueOf(j2), Integer.valueOf(i2)), new Object[0]);
                        int max2 = queryFinalized2.next() ? Math.max(0, queryFinalized2.intValue(0) - 1) : 0;
                        queryFinalized2.dispose();
                        this.database.executeFast(String.format(locale2, "UPDATE topics SET unread_mentions = %d WHERE did = %d AND topic_id = %d", Integer.valueOf(max2), Long.valueOf(j), Integer.valueOf(i2))).stepThis().dispose();
                        getMessagesController().getTopicsController().updateMentionsUnread(j, i2, max2);
                    }
                } catch (Exception e2) {
                    e = e2;
                    sQLiteCursor = queryFinalized;
                } catch (Throwable th2) {
                    th = th2;
                    sQLiteCursor = queryFinalized;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void markMessageAsMention(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$markMessageAsMention$92(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessageAsMention$92(int i, long j) {
        try {
            this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET mention = 1, read_state = read_state & ~2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void resetMentionsCount(final long j, final int i, final int i2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$resetMentionsCount$93(i, j, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetMentionsCount$93(int i, long j, int i2) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (i == 0) {
                    SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j, new Object[0]);
                    try {
                        int intValue = queryFinalized.next() ? queryFinalized.intValue(0) : 0;
                        queryFinalized.dispose();
                        if (intValue == 0 && i2 == 0) {
                            return;
                        }
                        if (i2 == 0) {
                            this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j))).stepThis().dispose();
                        }
                        this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", Integer.valueOf(i2), Long.valueOf(j))).stepThis().dispose();
                        LongSparseIntArray longSparseIntArray = new LongSparseIntArray(1);
                        longSparseIntArray.put(j, i2);
                        getMessagesController().processDialogsUpdateRead(null, longSparseIntArray);
                        if (i2 == 0) {
                            updateFiltersReadCounter(null, longSparseIntArray, true);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                }
                this.database.executeFast(String.format(Locale.US, "UPDATE topics SET unread_mentions = %d WHERE did = %d AND topic_id = %d", Integer.valueOf(i2), Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
                TopicsController.TopicUpdate topicUpdate = new TopicsController.TopicUpdate();
                topicUpdate.dialogId = j;
                topicUpdate.topicId = i;
                topicUpdate.onlyCounters = true;
                topicUpdate.unreadMentions = i2;
                topicUpdate.unreadCount = -1;
                getMessagesController().getTopicsController().processUpdate(Collections.singletonList(topicUpdate));
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void createTaskForMid(final long j, final int i, final int i2, final int i3, final int i4, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$createTaskForMid$95(i2, i3, i4, i, z, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForMid$95(int i, int i2, int i3, int i4, final boolean z, final long j) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                int max = Math.max(i, i2) + i3;
                SparseArray<ArrayList<Integer>> sparseArray = new SparseArray<>();
                final ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(Integer.valueOf(i4));
                sparseArray.put(max, arrayList);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda203
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$createTaskForMid$94(z, j, arrayList);
                    }
                });
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO enc_tasks_v4 VALUES(?, ?, ?, ?)");
                for (int i5 = 0; i5 < sparseArray.size(); i5++) {
                    try {
                        int keyAt = sparseArray.keyAt(i5);
                        ArrayList<Integer> arrayList2 = sparseArray.get(keyAt);
                        for (int i6 = 0; i6 < arrayList2.size(); i6++) {
                            executeFast.requery();
                            executeFast.bindInteger(1, arrayList2.get(i6).intValue());
                            executeFast.bindLong(2, j);
                            executeFast.bindInteger(3, keyAt);
                            executeFast.bindInteger(4, 1);
                            executeFast.step();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                executeFast.dispose();
                this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET ttl = 0 WHERE mid = %d AND uid = %d", Integer.valueOf(i4), Long.valueOf(j))).stepThis().dispose();
                getMessagesController().didAddedNewTask(max, j, sparseArray);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForMid$94(boolean z, long j, ArrayList arrayList) {
        if (!z) {
            markMessagesContentAsRead(j, arrayList, 0);
        }
        getNotificationCenter().postNotificationName(NotificationCenter.messagesReadContent, Long.valueOf(j), arrayList);
    }

    public void createTaskForSecretChat(final int i, final int i2, final int i3, final int i4, final ArrayList<Long> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$createTaskForSecretChat$97(i, arrayList, i4, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:56:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0178  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$createTaskForSecretChat$97(int r18, java.util.ArrayList r19, int r20, int r21, int r22) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$createTaskForSecretChat$97(int, java.util.ArrayList, int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForSecretChat$96(long j, ArrayList arrayList) {
        markMessagesContentAsRead(j, arrayList, 0);
        getNotificationCenter().postNotificationName(NotificationCenter.messagesReadContent, Long.valueOf(j), arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:200:0x03fd, code lost:
    
        if (r14.indexOfKey(-r4.id) >= 0) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x0649, code lost:
    
        if (r12.indexOfKey(r15.id) >= 0) goto L332;
     */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03a2  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x03b2  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x03f4  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x041f  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x081b  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x0851  */
    /* JADX WARN: Removed duplicated region for block: B:543:0x0907  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateFiltersReadCounter(org.telegram.messenger.support.LongSparseIntArray r26, org.telegram.messenger.support.LongSparseIntArray r27, boolean r28) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 2809
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.updateFiltersReadCounter(org.telegram.messenger.support.LongSparseIntArray, org.telegram.messenger.support.LongSparseIntArray, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFiltersReadCounter$98() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x03a0 A[Catch: Exception -> 0x03a8, TRY_LEAVE, TryCatch #0 {Exception -> 0x03a8, blocks: (B:3:0x0008, B:7:0x0024, B:8:0x003c, B:10:0x0042, B:13:0x0049, B:16:0x0050, B:23:0x005a, B:19:0x005e, B:32:0x0064, B:33:0x02b3, B:35:0x02b9, B:39:0x0390, B:41:0x03a0, B:46:0x02c3, B:48:0x02ce, B:49:0x02dc, B:51:0x02e2, B:53:0x02ec, B:55:0x0340, B:56:0x02f4, B:58:0x0318, B:59:0x031e, B:61:0x0323, B:64:0x032a, B:67:0x0342, B:68:0x034b, B:70:0x0351, B:71:0x035a, B:73:0x0360, B:75:0x036a, B:77:0x0384, B:78:0x0372, B:81:0x0386, B:82:0x0389, B:84:0x0069, B:87:0x0070, B:89:0x0076, B:93:0x008b, B:95:0x0092, B:97:0x015f, B:101:0x00af, B:102:0x00d1, B:106:0x00d9, B:108:0x00e0, B:110:0x0108, B:112:0x0113, B:113:0x0143, B:115:0x012b, B:117:0x012f, B:118:0x0147, B:120:0x014b, B:124:0x0083, B:126:0x019e, B:128:0x01a4, B:130:0x01ab, B:131:0x01d4, B:133:0x01da, B:135:0x01f2, B:137:0x01f8, B:139:0x01ff, B:141:0x0206, B:143:0x0228, B:144:0x022f, B:146:0x024c, B:148:0x023d, B:153:0x0256, B:157:0x0267, B:159:0x0271, B:161:0x0278, B:166:0x027e, B:169:0x0285, B:171:0x028b), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateDialogsWithReadMessagesInternal(java.util.ArrayList<java.lang.Integer> r22, org.telegram.messenger.support.LongSparseIntArray r23, org.telegram.messenger.support.LongSparseIntArray r24, androidx.collection.LongSparseArray<java.util.ArrayList<java.lang.Integer>> r25, org.telegram.messenger.support.LongSparseIntArray r26) {
        /*
            Method dump skipped, instructions count: 941
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.updateDialogsWithReadMessagesInternal(java.util.ArrayList, org.telegram.messenger.support.LongSparseIntArray, org.telegram.messenger.support.LongSparseIntArray, androidx.collection.LongSparseArray, org.telegram.messenger.support.LongSparseIntArray):void");
    }

    private static boolean isEmpty(SparseArray<?> sparseArray) {
        return sparseArray == null || sparseArray.size() == 0;
    }

    private static boolean isEmpty(LongSparseIntArray longSparseIntArray) {
        return longSparseIntArray == null || longSparseIntArray.size() == 0;
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private static boolean isEmpty(SparseIntArray sparseIntArray) {
        return sparseIntArray == null || sparseIntArray.size() == 0;
    }

    private static boolean isEmpty(LongSparseArray<?> longSparseArray) {
        return longSparseArray == null || longSparseArray.size() == 0;
    }

    public void updateDialogsWithReadMessages(final LongSparseIntArray longSparseIntArray, final LongSparseIntArray longSparseIntArray2, final LongSparseArray<ArrayList<Integer>> longSparseArray, final LongSparseIntArray longSparseIntArray3, boolean z) {
        if (isEmpty(longSparseIntArray) && isEmpty(longSparseIntArray2) && isEmpty(longSparseArray) && isEmpty(longSparseIntArray3)) {
            return;
        }
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda170
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$updateDialogsWithReadMessages$99(longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
                }
            });
        } else {
            updateDialogsWithReadMessagesInternal(null, longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsWithReadMessages$99(LongSparseIntArray longSparseIntArray, LongSparseIntArray longSparseIntArray2, LongSparseArray longSparseArray, LongSparseIntArray longSparseIntArray3) {
        updateDialogsWithReadMessagesInternal(null, longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
    }

    public void updateChatParticipants(final TLRPC$ChatParticipants tLRPC$ChatParticipants) {
        if (tLRPC$ChatParticipants == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda175
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChatParticipants$101(tLRPC$ChatParticipants);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatParticipants$101(TLRPC$ChatParticipants tLRPC$ChatParticipants) {
        final TLRPC$ChatFull tLRPC$ChatFull;
        NativeByteBuffer byteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT info, pinned, online, inviter FROM chat_settings_v2 WHERE uid = " + tLRPC$ChatParticipants.chat_id, new Object[0]);
                try {
                    new ArrayList();
                    if (!queryFinalized.next() || (byteBufferValue = queryFinalized.byteBufferValue(0)) == null) {
                        tLRPC$ChatFull = null;
                    } else {
                        tLRPC$ChatFull = TLRPC$ChatFull.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        tLRPC$ChatFull.pinned_msg_id = queryFinalized.intValue(1);
                        tLRPC$ChatFull.online_count = queryFinalized.intValue(2);
                        tLRPC$ChatFull.inviterId = queryFinalized.longValue(3);
                    }
                    queryFinalized.dispose();
                    if (tLRPC$ChatFull instanceof TLRPC$TL_chatFull) {
                        tLRPC$ChatFull.participants = tLRPC$ChatParticipants;
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda173
                            @Override // java.lang.Runnable
                            public final void run() {
                                MessagesStorage.this.lambda$updateChatParticipants$100(tLRPC$ChatFull);
                            }
                        });
                        SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?, ?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$ChatFull.getObjectSize());
                        tLRPC$ChatFull.serializeToStream(nativeByteBuffer);
                        executeFast.bindLong(1, tLRPC$ChatFull.id);
                        executeFast.bindByteBuffer(2, nativeByteBuffer);
                        executeFast.bindInteger(3, tLRPC$ChatFull.pinned_msg_id);
                        executeFast.bindInteger(4, tLRPC$ChatFull.online_count);
                        executeFast.bindLong(5, tLRPC$ChatFull.inviterId);
                        executeFast.bindInteger(6, tLRPC$ChatFull.invitesCount);
                        executeFast.step();
                        executeFast.dispose();
                        nativeByteBuffer.reuse();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatParticipants$100(TLRPC$ChatFull tLRPC$ChatFull) {
        NotificationCenter notificationCenter = getNotificationCenter();
        int i = NotificationCenter.chatInfoDidLoad;
        Boolean bool = Boolean.FALSE;
        notificationCenter.postNotificationName(i, tLRPC$ChatFull, 0, bool, bool);
    }

    public void loadChannelAdmins(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda69
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadChannelAdmins$102(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadChannelAdmins$102(long j) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT uid, data FROM channel_admins_v3 WHERE did = " + j, new Object[0]);
                try {
                    LongSparseArray<TLRPC$ChannelParticipant> longSparseArray = new LongSparseArray<>();
                    while (queryFinalized.next()) {
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(1);
                        if (byteBufferValue != null) {
                            TLRPC$ChannelParticipant TLdeserialize = TLRPC$ChannelParticipant.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            byteBufferValue.reuse();
                            if (TLdeserialize != null) {
                                longSparseArray.put(queryFinalized.longValue(0), TLdeserialize);
                            }
                        }
                    }
                    queryFinalized.dispose();
                    getMessagesController().processLoadedChannelAdmins(longSparseArray, j, true);
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void putChannelAdmins(final long j, final LongSparseArray<TLRPC$ChannelParticipant> longSparseArray) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda100
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putChannelAdmins$103(j, longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putChannelAdmins$103(long j, LongSparseArray longSparseArray) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.executeFast("DELETE FROM channel_admins_v3 WHERE did = " + j).stepThis().dispose();
                this.database.beginTransaction();
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO channel_admins_v3 VALUES(?, ?, ?)");
                for (int i = 0; i < longSparseArray.size(); i++) {
                    try {
                        executeFast.requery();
                        executeFast.bindLong(1, j);
                        executeFast.bindLong(2, longSparseArray.keyAt(i));
                        TLRPC$ChannelParticipant tLRPC$ChannelParticipant = (TLRPC$ChannelParticipant) longSparseArray.valueAt(i);
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$ChannelParticipant.getObjectSize());
                        tLRPC$ChannelParticipant.serializeToStream(nativeByteBuffer);
                        executeFast.bindByteBuffer(3, nativeByteBuffer);
                        executeFast.step();
                        nativeByteBuffer.reuse();
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                executeFast.dispose();
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void updateChannelUsers(final long j, final ArrayList<TLRPC$ChannelParticipant> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda103
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChannelUsers$104(j, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChannelUsers$104(long j, ArrayList arrayList) {
        long j2 = -j;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.executeFast("DELETE FROM channel_users_v2 WHERE did = " + j2).stepThis().dispose();
                this.database.beginTransaction();
                SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO channel_users_v2 VALUES(?, ?, ?, ?)");
                try {
                    int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
                    for (int i = 0; i < arrayList.size(); i++) {
                        TLRPC$ChannelParticipant tLRPC$ChannelParticipant = (TLRPC$ChannelParticipant) arrayList.get(i);
                        executeFast.requery();
                        executeFast.bindLong(1, j2);
                        executeFast.bindLong(2, MessageObject.getPeerId(tLRPC$ChannelParticipant.peer));
                        executeFast.bindInteger(3, currentTimeMillis);
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$ChannelParticipant.getObjectSize());
                        tLRPC$ChannelParticipant.serializeToStream(nativeByteBuffer);
                        executeFast.bindByteBuffer(4, nativeByteBuffer);
                        executeFast.step();
                        nativeByteBuffer.reuse();
                        currentTimeMillis--;
                    }
                    executeFast.dispose();
                    this.database.commitTransaction();
                    loadChatInfo(j, true, null, false, true);
                    SQLiteDatabase sQLiteDatabase = this.database;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.commitTransaction();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLitePreparedStatement = executeFast;
                    checkSQLException(e);
                    SQLiteDatabase sQLiteDatabase2 = this.database;
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLitePreparedStatement = executeFast;
                    SQLiteDatabase sQLiteDatabase3 = this.database;
                    if (sQLiteDatabase3 != null) {
                        sQLiteDatabase3.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void saveBotCache(final String str, final TLObject tLObject) {
        if (tLObject == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda171
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveBotCache$105(tLObject, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBotCache$105(TLObject tLObject, String str) {
        int i;
        SQLitePreparedStatement executeFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                int currentTime = getConnectionsManager().getCurrentTime();
                try {
                    if (tLObject instanceof TLRPC$TL_messages_botCallbackAnswer) {
                        i = ((TLRPC$TL_messages_botCallbackAnswer) tLObject).cache_time;
                    } else {
                        if (tLObject instanceof TLRPC$TL_messages_botResults) {
                            i = ((TLRPC$TL_messages_botResults) tLObject).cache_time;
                        }
                        executeFast = this.database.executeFast("REPLACE INTO botcache VALUES(?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize());
                        tLObject.serializeToStream(nativeByteBuffer);
                        executeFast.bindString(1, str);
                        executeFast.bindInteger(2, currentTime);
                        executeFast.bindByteBuffer(3, nativeByteBuffer);
                        executeFast.step();
                        executeFast.dispose();
                        nativeByteBuffer.reuse();
                        return;
                    }
                    NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(tLObject.getObjectSize());
                    tLObject.serializeToStream(nativeByteBuffer2);
                    executeFast.bindString(1, str);
                    executeFast.bindInteger(2, currentTime);
                    executeFast.bindByteBuffer(3, nativeByteBuffer2);
                    executeFast.step();
                    executeFast.dispose();
                    nativeByteBuffer2.reuse();
                    return;
                } catch (Exception e) {
                    e = e;
                    sQLitePreparedStatement = executeFast;
                    checkSQLException(e);
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    th = th;
                    sQLitePreparedStatement = executeFast;
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                    throw th;
                }
                currentTime += i;
                executeFast = this.database.executeFast("REPLACE INTO botcache VALUES(?, ?, ?)");
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void getBotCache(final String str, final RequestDelegate requestDelegate) {
        if (str == null || requestDelegate == null) {
            return;
        }
        final int currentTime = getConnectionsManager().getCurrentTime();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda56
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getBotCache$106(currentTime, str, requestDelegate);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getBotCache$106(int r5, java.lang.String r6, org.telegram.tgnet.RequestDelegate r7) {
        /*
            r4 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r4.database     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r2.<init>()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            java.lang.String r3 = "DELETE FROM botcache WHERE date < "
            r2.append(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r2.append(r5)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            org.telegram.SQLite.SQLitePreparedStatement r5 = r1.executeFast(r5)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            org.telegram.SQLite.SQLitePreparedStatement r5 = r5.stepThis()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r5.dispose()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            org.telegram.SQLite.SQLiteDatabase r5 = r4.database     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            java.lang.String r1 = "SELECT data FROM botcache WHERE id = ?"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            r3 = 0
            r2[r3] = r6     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            org.telegram.SQLite.SQLiteCursor r5 = r5.queryFinalized(r1, r2)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L6a
            boolean r6 = r5.next()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            if (r6 == 0) goto L56
            org.telegram.tgnet.NativeByteBuffer r6 = r5.byteBufferValue(r3)     // Catch: java.lang.Exception -> L50 java.lang.Throwable -> L60
            if (r6 == 0) goto L56
            int r1 = r6.readInt32(r3)     // Catch: java.lang.Exception -> L50 java.lang.Throwable -> L60
            int r2 = org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer.constructor     // Catch: java.lang.Exception -> L50 java.lang.Throwable -> L60
            if (r1 != r2) goto L46
            org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer r1 = org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer.TLdeserialize(r6, r1, r3)     // Catch: java.lang.Exception -> L50 java.lang.Throwable -> L60
            goto L4a
        L46:
            org.telegram.tgnet.TLRPC$messages_BotResults r1 = org.telegram.tgnet.TLRPC$messages_BotResults.TLdeserialize(r6, r1, r3)     // Catch: java.lang.Exception -> L50 java.lang.Throwable -> L60
        L4a:
            r6.reuse()     // Catch: java.lang.Exception -> L4e java.lang.Throwable -> L79
            goto L57
        L4e:
            r6 = move-exception
            goto L52
        L50:
            r6 = move-exception
            r1 = r0
        L52:
            r4.checkSQLException(r6)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L79
            goto L57
        L56:
            r1 = r0
        L57:
            r5.dispose()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L79
            r7.run(r1, r0)
            goto L78
        L5e:
            r6 = move-exception
            goto L6d
        L60:
            r6 = move-exception
            r1 = r0
            goto L7a
        L63:
            r6 = move-exception
            r1 = r0
            goto L6d
        L66:
            r6 = move-exception
            r5 = r0
            r1 = r5
            goto L7a
        L6a:
            r6 = move-exception
            r5 = r0
            r1 = r5
        L6d:
            r4.checkSQLException(r6)     // Catch: java.lang.Throwable -> L79
            r7.run(r1, r0)
            if (r5 == 0) goto L78
            r5.dispose()
        L78:
            return
        L79:
            r6 = move-exception
        L7a:
            r7.run(r1, r0)
            if (r5 == 0) goto L82
            r5.dispose()
        L82:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getBotCache$106(int, java.lang.String, org.telegram.tgnet.RequestDelegate):void");
    }

    public ArrayList<TLRPC$UserFull> loadUserInfos(HashSet<Long> hashSet) {
        ArrayList<TLRPC$UserFull> arrayList = new ArrayList<>();
        try {
            String join = TextUtils.join(",", hashSet);
            SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT info, pinned FROM user_settings WHERE uid IN(" + join + ")", new Object[0]);
            while (queryFinalized.next()) {
                NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                if (byteBufferValue != null) {
                    TLRPC$UserFull TLdeserialize = TLRPC$UserFull.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                    TLdeserialize.pinned_msg_id = queryFinalized.intValue(1);
                    arrayList.add(TLdeserialize);
                    byteBufferValue.reuse();
                }
            }
            queryFinalized.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return arrayList;
    }

    public void loadUserInfo(final TLRPC$User tLRPC$User, final boolean z, final int i, int i2) {
        if (tLRPC$User == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda191
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadUserInfo$107(tLRPC$User, z, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:49:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$loadUserInfo$107(org.telegram.tgnet.TLRPC$User r20, boolean r21, int r22) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadUserInfo$107(org.telegram.tgnet.TLRPC$User, boolean, int):void");
    }

    public void updateUserInfo(final TLRPC$UserFull tLRPC$UserFull, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda192
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateUserInfo$108(tLRPC$UserFull, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateUserInfo$108(org.telegram.tgnet.TLRPC$UserFull r10, boolean r11) {
        /*
            r9 = this;
            org.telegram.tgnet.TLRPC$User r0 = r10.user
            if (r0 == 0) goto L7
            long r0 = r0.id
            goto L9
        L7:
            long r0 = r10.id
        L9:
            r2 = 0
            if (r11 == 0) goto L33
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r3.<init>()     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.String r4 = "SELECT uid FROM user_settings WHERE uid = "
            r3.append(r4)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r3.append(r0)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            org.telegram.SQLite.SQLiteCursor r11 = r11.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            boolean r3 = r11.next()     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> Lbd
            r11.dispose()     // Catch: java.lang.Exception -> L30 java.lang.Throwable -> Lbd
            if (r3 != 0) goto L33
            return
        L30:
            r10 = move-exception
            goto Laf
        L33:
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.String r3 = "REPLACE INTO user_settings VALUES(?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            org.telegram.tgnet.NativeByteBuffer r3 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            int r4 = r10.getObjectSize()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r3.<init>(r4)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r10.serializeToStream(r3)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r4 = 1
            r11.bindLong(r4, r0)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r5 = 2
            r11.bindByteBuffer(r5, r3)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r6 = 3
            int r7 = r10.pinned_msg_id     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.bindInteger(r6, r7)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.step()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.dispose()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r3.reuse()     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            int r11 = r10.flags     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r11 = r11 & 2048(0x800, float:2.87E-42)
            if (r11 == 0) goto L83
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.String r3 = "UPDATE dialogs SET folder_id = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            int r3 = r10.folder_id     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.bindInteger(r4, r3)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.bindLong(r5, r0)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.step()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.dispose()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            androidx.collection.LongSparseArray<java.lang.Boolean> r11 = r9.unknownDialogsIds     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            org.telegram.tgnet.TLRPC$User r3 = r10.user     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            long r6 = r3.id     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r11.remove(r6)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
        L83:
            int r11 = r10.flags     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            r11 = r11 & 16384(0x4000, float:2.2959E-41)
            if (r11 == 0) goto Lbc
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            java.lang.String r3 = "UPDATE dialogs SET ttl_period = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> Laa java.lang.Exception -> Lad
            int r10 = r10.ttl_period     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.bindInteger(r4, r10)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.bindLong(r5, r0)     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.step()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            r11.dispose()     // Catch: java.lang.Throwable -> La0 java.lang.Exception -> La5
            goto Lbc
        La0:
            r10 = move-exception
            r8 = r2
            r2 = r11
            r11 = r8
            goto Lbe
        La5:
            r10 = move-exception
            r8 = r2
            r2 = r11
            r11 = r8
            goto Laf
        Laa:
            r10 = move-exception
            r11 = r2
            goto Lbe
        Lad:
            r10 = move-exception
            r11 = r2
        Laf:
            r9.checkSQLException(r10)     // Catch: java.lang.Throwable -> Lbd
            if (r2 == 0) goto Lb7
            r2.dispose()
        Lb7:
            if (r11 == 0) goto Lbc
            r11.dispose()
        Lbc:
            return
        Lbd:
            r10 = move-exception
        Lbe:
            if (r2 == 0) goto Lc3
            r2.dispose()
        Lc3:
            if (r11 == 0) goto Lc8
            r11.dispose()
        Lc8:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateUserInfo$108(org.telegram.tgnet.TLRPC$UserFull, boolean):void");
    }

    public void saveChatInviter(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda92
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveChatInviter$109(j2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChatInviter$109(long j, long j2) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE chat_settings_v2 SET inviter = ? WHERE uid = ?");
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindLong(1, j);
                sQLitePreparedStatement.bindLong(2, j2);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void saveChatLinksCount(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$saveChatLinksCount$110(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChatLinksCount$110(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE chat_settings_v2 SET links = ? WHERE uid = ?");
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindInteger(1, i);
                sQLitePreparedStatement.bindLong(2, j);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateChatInfo(final TLRPC$ChatFull tLRPC$ChatFull, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda174
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChatInfo$111(tLRPC$ChatFull, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:55:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0168  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v22 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateChatInfo$111(org.telegram.tgnet.TLRPC$ChatFull r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateChatInfo$111(org.telegram.tgnet.TLRPC$ChatFull, boolean):void");
    }

    public void updateChatOnlineCount(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda42
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChatOnlineCount$112(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatOnlineCount$112(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE chat_settings_v2 SET online = ? WHERE uid = ?");
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindInteger(1, i);
                sQLitePreparedStatement.bindLong(2, j);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updatePinnedMessages(final long j, final ArrayList<Integer> arrayList, final boolean z, final int i, final int i2, final boolean z2, final HashMap<Integer, MessageObject> hashMap) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda207
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updatePinnedMessages$115(z, hashMap, i2, j, arrayList, i, z2);
            }
        });
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r11v15 ??, still in use, count: 1, list:
          (r11v15 ?? I:java.lang.Runnable) from 0x015f: INVOKE (r11v15 ?? I:java.lang.Runnable) STATIC call: org.telegram.messenger.AndroidUtilities.runOnUIThread(java.lang.Runnable):void A[Catch: all -> 0x017c, Exception -> 0x0182, MD:(java.lang.Runnable):void (m)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        	at jadx.core.dex.visitors.ConstructorVisitor.visit(ConstructorVisitor.java:42)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePinnedMessages$115(
    /*  JADX ERROR: JadxRuntimeException in pass: ConstructorVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r11v15 ??, still in use, count: 1, list:
          (r11v15 ?? I:java.lang.Runnable) from 0x015f: INVOKE (r11v15 ?? I:java.lang.Runnable) STATIC call: org.telegram.messenger.AndroidUtilities.runOnUIThread(java.lang.Runnable):void A[Catch: all -> 0x017c, Exception -> 0x0182, MD:(java.lang.Runnable):void (m)]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
        	at jadx.core.utils.InsnRemover.perform(InsnRemover.java:73)
        	at jadx.core.dex.visitors.ConstructorVisitor.replaceInvoke(ConstructorVisitor.java:59)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r19v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:238)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:223)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:168)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:401)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
        */

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePinnedMessages$113(long j, ArrayList arrayList, HashMap hashMap, int i, int i2, boolean z) {
        getNotificationCenter().postNotificationName(NotificationCenter.didLoadPinnedMessages, Long.valueOf(j), arrayList, Boolean.TRUE, null, hashMap, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePinnedMessages$114(long j, ArrayList arrayList, HashMap hashMap, int i, int i2, boolean z) {
        getNotificationCenter().postNotificationName(NotificationCenter.didLoadPinnedMessages, Long.valueOf(j), arrayList, Boolean.FALSE, null, hashMap, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }

    public void updateChatInfo(final long j, final long j2, final int i, final long j3, final int i2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda80
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChatInfo$116(j, i, j2, j3, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatInfo$116(long j, int i, long j2, long j3, int i2) {
        final TLRPC$ChatFull tLRPC$ChatFull;
        TLRPC$ChatParticipant tLRPC$TL_chatParticipant;
        NativeByteBuffer byteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                int i3 = 0;
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT info, pinned, online, inviter FROM chat_settings_v2 WHERE uid = " + j, new Object[0]);
                try {
                    new ArrayList();
                    if (!queryFinalized.next() || (byteBufferValue = queryFinalized.byteBufferValue(0)) == null) {
                        tLRPC$ChatFull = null;
                    } else {
                        tLRPC$ChatFull = TLRPC$ChatFull.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        tLRPC$ChatFull.pinned_msg_id = queryFinalized.intValue(1);
                        tLRPC$ChatFull.online_count = queryFinalized.intValue(2);
                        tLRPC$ChatFull.inviterId = queryFinalized.longValue(3);
                    }
                    queryFinalized.dispose();
                    if (tLRPC$ChatFull instanceof TLRPC$TL_chatFull) {
                        if (i == 1) {
                            while (true) {
                                if (i3 >= tLRPC$ChatFull.participants.participants.size()) {
                                    break;
                                }
                                if (tLRPC$ChatFull.participants.participants.get(i3).user_id == j2) {
                                    tLRPC$ChatFull.participants.participants.remove(i3);
                                    break;
                                }
                                i3++;
                            }
                        } else if (i == 0) {
                            Iterator<TLRPC$ChatParticipant> it = tLRPC$ChatFull.participants.participants.iterator();
                            while (it.hasNext()) {
                                if (it.next().user_id == j2) {
                                    return;
                                }
                            }
                            TLRPC$TL_chatParticipant tLRPC$TL_chatParticipant2 = new TLRPC$TL_chatParticipant();
                            tLRPC$TL_chatParticipant2.user_id = j2;
                            tLRPC$TL_chatParticipant2.inviter_id = j3;
                            tLRPC$TL_chatParticipant2.date = getConnectionsManager().getCurrentTime();
                            tLRPC$ChatFull.participants.participants.add(tLRPC$TL_chatParticipant2);
                        } else if (i == 2) {
                            while (true) {
                                if (i3 >= tLRPC$ChatFull.participants.participants.size()) {
                                    break;
                                }
                                TLRPC$ChatParticipant tLRPC$ChatParticipant = tLRPC$ChatFull.participants.participants.get(i3);
                                if (tLRPC$ChatParticipant.user_id == j2) {
                                    if (j3 == 1) {
                                        tLRPC$TL_chatParticipant = new TLRPC$TL_chatParticipantAdmin();
                                    } else {
                                        tLRPC$TL_chatParticipant = new TLRPC$TL_chatParticipant();
                                    }
                                    tLRPC$TL_chatParticipant.user_id = tLRPC$ChatParticipant.user_id;
                                    tLRPC$TL_chatParticipant.date = tLRPC$ChatParticipant.date;
                                    tLRPC$TL_chatParticipant.inviter_id = tLRPC$ChatParticipant.inviter_id;
                                    tLRPC$ChatFull.participants.participants.set(i3, tLRPC$TL_chatParticipant);
                                } else {
                                    i3++;
                                }
                            }
                        }
                        tLRPC$ChatFull.participants.version = i2;
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage.2
                            @Override // java.lang.Runnable
                            public void run() {
                                NotificationCenter notificationCenter = MessagesStorage.this.getNotificationCenter();
                                int i4 = NotificationCenter.chatInfoDidLoad;
                                Boolean bool = Boolean.FALSE;
                                notificationCenter.postNotificationName(i4, tLRPC$ChatFull, 0, bool, bool);
                            }
                        });
                        SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?, ?, ?, ?)");
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$ChatFull.getObjectSize());
                        tLRPC$ChatFull.serializeToStream(nativeByteBuffer);
                        executeFast.bindLong(1, j);
                        executeFast.bindByteBuffer(2, nativeByteBuffer);
                        executeFast.bindInteger(3, tLRPC$ChatFull.pinned_msg_id);
                        executeFast.bindInteger(4, tLRPC$ChatFull.online_count);
                        executeFast.bindLong(5, tLRPC$ChatFull.inviterId);
                        executeFast.bindInteger(6, tLRPC$ChatFull.invitesCount);
                        executeFast.step();
                        executeFast.dispose();
                        nativeByteBuffer.reuse();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean isMigratedChat(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda128
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$isMigratedChat$117(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isMigratedChat$117(long j, boolean[] zArr, CountDownLatch countDownLatch) {
        TLRPC$ChatFull tLRPC$ChatFull;
        NativeByteBuffer byteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized("SELECT info FROM chat_settings_v2 WHERE uid = " + j, new Object[0]);
                try {
                    new ArrayList();
                    if (!queryFinalized.next() || (byteBufferValue = queryFinalized.byteBufferValue(0)) == null) {
                        tLRPC$ChatFull = null;
                    } else {
                        tLRPC$ChatFull = TLRPC$ChatFull.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                    }
                    queryFinalized.dispose();
                    zArr[0] = (tLRPC$ChatFull instanceof TLRPC$TL_channelFull) && tLRPC$ChatFull.migrated_from_chat_id != 0;
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    countDownLatch.countDown();
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    countDownLatch.countDown();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
        countDownLatch.countDown();
    }

    public TLRPC$Message getMessage(final long j, final long j2) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getMessage$118(j, j2, atomicReference, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return (TLRPC$Message) atomicReference.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessage$118(long j, long j2, AtomicReference atomicReference, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursor = this.database.queryFinalized("SELECT data FROM messages_v2 WHERE uid = " + j + " AND mid = " + j2 + " LIMIT 1", new Object[0]);
                while (sQLiteCursor.next()) {
                    NativeByteBuffer byteBufferValue = sQLiteCursor.byteBufferValue(0);
                    if (byteBufferValue != null) {
                        TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        atomicReference.set(TLdeserialize);
                    }
                }
                sQLiteCursor.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                }
            }
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    public boolean hasInviteMeMessage(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda127
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$hasInviteMeMessage$119(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hasInviteMeMessage$119(long j, boolean[] zArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                long clientUserId = getUserConfig().getClientUserId();
                sQLiteCursor = this.database.queryFinalized("SELECT data FROM messages_v2 WHERE uid = " + (-j) + " AND out = 0 ORDER BY mid DESC LIMIT 100", new Object[0]);
                while (true) {
                    if (!sQLiteCursor.next()) {
                        break;
                    }
                    NativeByteBuffer byteBufferValue = sQLiteCursor.byteBufferValue(0);
                    if (byteBufferValue != null) {
                        TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        TLRPC$MessageAction tLRPC$MessageAction = TLdeserialize.action;
                        if ((tLRPC$MessageAction instanceof TLRPC$TL_messageActionChatAddUser) && tLRPC$MessageAction.users.contains(Long.valueOf(clientUserId))) {
                            zArr[0] = true;
                            break;
                        }
                    }
                }
                sQLiteCursor.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                }
            }
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(17:0|1|(5:2|3|5|6|7)|(18:(3:270|271|(32:273|274|275|276|10|11|12|13|14|(6:241|242|(4:245|(2:247|248)(1:250)|249|243)|251|252|(1:254))(2:16|(12:172|173|174|175|(11:178|179|(2:212|213)(1:181)|182|183|184|(1:186)(1:208)|(1:190)|(2:206|207)(7:193|(1:195)|196|197|198|199|201)|202|176)|220|221|222|(4:225|(2:227|228)(1:230)|229|223)|231|232|(1:234)))|18|(1:22)|48|49|50|51|52|(5:56|57|58|53|54)|65|66|67|69|70|(6:135|136|137|138|(1:140)|141)(1:72)|73|74|(3:103|104|(10:106|(3:118|119|(5:121|77|(6:79|(1:81)(1:100)|82|83|84|(3:86|(2:88|89)|91))(1:102)|92|93))(1:108)|109|110|111|112|77|(0)(0)|92|93))|76|77|(0)(0)|92|93))|51|52|(2:53|54)|65|66|67|69|70|(0)(0)|73|74|(0)|76|77|(0)(0)|92|93)|9|10|11|12|13|14|(0)(0)|18|(2:20|22)|48|49|50|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(21:0|1|2|3|5|6|7|(18:(3:270|271|(32:273|274|275|276|10|11|12|13|14|(6:241|242|(4:245|(2:247|248)(1:250)|249|243)|251|252|(1:254))(2:16|(12:172|173|174|175|(11:178|179|(2:212|213)(1:181)|182|183|184|(1:186)(1:208)|(1:190)|(2:206|207)(7:193|(1:195)|196|197|198|199|201)|202|176)|220|221|222|(4:225|(2:227|228)(1:230)|229|223)|231|232|(1:234)))|18|(1:22)|48|49|50|51|52|(5:56|57|58|53|54)|65|66|67|69|70|(6:135|136|137|138|(1:140)|141)(1:72)|73|74|(3:103|104|(10:106|(3:118|119|(5:121|77|(6:79|(1:81)(1:100)|82|83|84|(3:86|(2:88|89)|91))(1:102)|92|93))(1:108)|109|110|111|112|77|(0)(0)|92|93))|76|77|(0)(0)|92|93))|51|52|(2:53|54)|65|66|67|69|70|(0)(0)|73|74|(0)|76|77|(0)(0)|92|93)|9|10|11|12|13|14|(0)(0)|18|(2:20|22)|48|49|50|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x036e, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0366, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x037c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x0376, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:263:0x0377, code lost:
    
        r19 = r15;
        r2 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x038a, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x0399, code lost:
    
        r2 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0382, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x0383, code lost:
    
        r19 = r15;
        r2 = r7;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x028b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0263 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ca A[Catch: all -> 0x0376, Exception -> 0x037c, TRY_ENTER, TRY_LEAVE, TryCatch #38 {Exception -> 0x037c, all -> 0x0376, blocks: (B:13:0x0080, B:16:0x00ca), top: B:12:0x0080 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0223 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02e6 A[Catch: all -> 0x0339, Exception -> 0x0344, TRY_LEAVE, TryCatch #36 {Exception -> 0x0344, all -> 0x0339, blocks: (B:74:0x0286, B:77:0x02e0, B:79:0x02e6), top: B:73:0x0286 }] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v34, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v35 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.telegram.tgnet.TLRPC$ChatFull loadChatInfoInternal(long r25, boolean r27, boolean r28, boolean r29, int r30) {
        /*
            Method dump skipped, instructions count: 982
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadChatInfoInternal(long, boolean, boolean, boolean, int):org.telegram.tgnet.TLRPC$ChatFull");
    }

    public TLRPC$ChatFull loadChatInfo(long j, boolean z, CountDownLatch countDownLatch, boolean z2, boolean z3) {
        return loadChatInfo(j, z, countDownLatch, z2, z3, 0);
    }

    public TLRPC$ChatFull loadChatInfo(final long j, final boolean z, final CountDownLatch countDownLatch, final boolean z2, final boolean z3, final int i) {
        final TLRPC$ChatFull[] tLRPC$ChatFullArr = new TLRPC$ChatFull[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda209
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$loadChatInfo$120(tLRPC$ChatFullArr, j, z, z2, z3, i, countDownLatch);
            }
        });
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (Throwable unused) {
            }
        }
        return tLRPC$ChatFullArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadChatInfo$120(TLRPC$ChatFull[] tLRPC$ChatFullArr, long j, boolean z, boolean z2, boolean z3, int i, CountDownLatch countDownLatch) {
        tLRPC$ChatFullArr[0] = loadChatInfoInternal(j, z, z2, z3, i);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void processPendingRead(final long j, final int i, final int i2, final int i3) {
        final int i4 = this.lastSavedDate;
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda78
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$processPendingRead$121(j, i, i3, i4, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$processPendingRead$121(long r19, int r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 462
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$processPendingRead$121(long, int, int, int, int):void");
    }

    public void putContacts(ArrayList<TLRPC$TL_contact> arrayList, final boolean z) {
        if (!arrayList.isEmpty() || z) {
            final ArrayList arrayList2 = new ArrayList(arrayList);
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda206
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$putContacts$122(z, arrayList2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putContacts$122(boolean r8, java.util.ArrayList r9) {
        /*
            r7 = this;
            r0 = 0
            if (r8 == 0) goto L12
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            java.lang.String r1 = "DELETE FROM contacts WHERE 1"
            org.telegram.SQLite.SQLitePreparedStatement r8 = r8.executeFast(r1)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            org.telegram.SQLite.SQLitePreparedStatement r8 = r8.stepThis()     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            r8.dispose()     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
        L12:
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            r8.beginTransaction()     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            java.lang.String r1 = "REPLACE INTO contacts VALUES(?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r8 = r8.executeFast(r1)     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            r1 = 0
            r2 = 0
        L21:
            int r3 = r9.size()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            if (r2 >= r3) goto L46
            java.lang.Object r3 = r9.get(r2)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            org.telegram.tgnet.TLRPC$TL_contact r3 = (org.telegram.tgnet.TLRPC$TL_contact) r3     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            r8.requery()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            long r4 = r3.user_id     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            r6 = 1
            r8.bindLong(r6, r4)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            r4 = 2
            boolean r3 = r3.mutual     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            if (r3 == 0) goto L3c
            goto L3d
        L3c:
            r6 = 0
        L3d:
            r8.bindInteger(r4, r6)     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            r8.step()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            int r2 = r2 + 1
            goto L21
        L46:
            r8.dispose()     // Catch: java.lang.Throwable -> L53 java.lang.Exception -> L57
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            r8.commitTransaction()     // Catch: java.lang.Throwable -> L5b java.lang.Exception -> L5d
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database
            if (r8 == 0) goto L6d
            goto L6a
        L53:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L6e
        L57:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto L5e
        L5b:
            r8 = move-exception
            goto L6e
        L5d:
            r8 = move-exception
        L5e:
            r7.checkSQLException(r8)     // Catch: java.lang.Throwable -> L5b
            if (r0 == 0) goto L66
            r0.dispose()
        L66:
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database
            if (r8 == 0) goto L6d
        L6a:
            r8.commitTransaction()
        L6d:
            return
        L6e:
            if (r0 == 0) goto L73
            r0.dispose()
        L73:
            org.telegram.SQLite.SQLiteDatabase r9 = r7.database
            if (r9 == 0) goto L7a
            r9.commitTransaction()
        L7a:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putContacts$122(boolean, java.util.ArrayList):void");
    }

    public void deleteContacts(final ArrayList<Long> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda143
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$deleteContacts$123(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteContacts$123(ArrayList arrayList) {
        try {
            String join = TextUtils.join(",", arrayList);
            this.database.executeFast("DELETE FROM contacts WHERE uid IN(" + join + ")").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void applyPhoneBookUpdates(final String str, final String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda136
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$applyPhoneBookUpdates$124(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyPhoneBookUpdates$124(String str, String str2) {
        try {
            if (str.length() != 0) {
                this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 0 WHERE sphone IN(%s)", str)).stepThis().dispose();
            }
            if (str2.length() != 0) {
                this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 1 WHERE sphone IN(%s)", str2)).stepThis().dispose();
            }
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putCachedPhoneBook(final HashMap<String, ContactsController.Contact> hashMap, final boolean z, boolean z2) {
        if (hashMap != null) {
            if (!hashMap.isEmpty() || z || z2) {
                this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda164
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$putCachedPhoneBook$125(hashMap, z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0110, code lost:
    
        if (r12 != null) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0137, code lost:
    
        r12.commitTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x013a, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0135, code lost:
    
        if (r12 != null) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putCachedPhoneBook$125(java.util.HashMap r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putCachedPhoneBook$125(java.util.HashMap, boolean):void");
    }

    public void getCachedPhoneBook(final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda199
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getCachedPhoneBook$126(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x013a, code lost:
    
        if (r10 != null) goto L67;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:0x015e A[Catch: all -> 0x01ee, Exception -> 0x01f0, TryCatch #0 {Exception -> 0x01f0, blocks: (B:79:0x0144, B:81:0x0169, B:83:0x016f, B:85:0x017b, B:87:0x019d, B:88:0x019f, B:90:0x01a3, B:91:0x01a5, B:92:0x01a8, B:94:0x01b0, B:97:0x01bc, B:99:0x01c2, B:101:0x01c8, B:102:0x01cc, B:105:0x01ea, B:117:0x015e), top: B:77:0x0142, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f9 A[Catch: all -> 0x0132, TRY_LEAVE, TryCatch #2 {all -> 0x0132, blocks: (B:62:0x00e9, B:64:0x00f9), top: B:61:0x00e9 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0144 A[Catch: all -> 0x01ee, Exception -> 0x01f0, TRY_ENTER, TryCatch #0 {Exception -> 0x01f0, blocks: (B:79:0x0144, B:81:0x0169, B:83:0x016f, B:85:0x017b, B:87:0x019d, B:88:0x019f, B:90:0x01a3, B:91:0x01a5, B:92:0x01a8, B:94:0x01b0, B:97:0x01bc, B:99:0x01c2, B:101:0x01c8, B:102:0x01cc, B:105:0x01ea, B:117:0x015e), top: B:77:0x0142, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x016f A[Catch: all -> 0x01ee, Exception -> 0x01f0, TryCatch #0 {Exception -> 0x01f0, blocks: (B:79:0x0144, B:81:0x0169, B:83:0x016f, B:85:0x017b, B:87:0x019d, B:88:0x019f, B:90:0x01a3, B:91:0x01a5, B:92:0x01a8, B:94:0x01b0, B:97:0x01bc, B:99:0x01c2, B:101:0x01c8, B:102:0x01cc, B:105:0x01ea, B:117:0x015e), top: B:77:0x0142, outer: #8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getCachedPhoneBook$126(boolean r25) {
        /*
            Method dump skipped, instructions count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getCachedPhoneBook$126(boolean):void");
    }

    public void getContacts() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getContacts$127();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getContacts$127() {
        /*
            r11 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            r3 = 1
            org.telegram.SQLite.SQLiteDatabase r4 = r11.database     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.String r5 = "SELECT * FROM contacts WHERE 1"
            r6 = 0
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            org.telegram.SQLite.SQLiteCursor r4 = r4.queryFinalized(r5, r7)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            r5.<init>()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
        L1c:
            boolean r7 = r4.next()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            if (r7 == 0) goto L4d
            int r7 = r4.intValue(r6)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            long r7 = (long) r7     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            org.telegram.tgnet.TLRPC$TL_contact r9 = new org.telegram.tgnet.TLRPC$TL_contact     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            r9.<init>()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            r9.user_id = r7     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            int r7 = r4.intValue(r3)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            if (r7 != r3) goto L36
            r7 = 1
            goto L37
        L36:
            r7 = 0
        L37:
            r9.mutual = r7     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            int r7 = r5.length()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            if (r7 == 0) goto L44
            java.lang.String r7 = ","
            r5.append(r7)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
        L44:
            r0.add(r9)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            long r7 = r9.user_id     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            r5.append(r7)     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            goto L1c
        L4d:
            r4.dispose()     // Catch: java.lang.Exception -> L5e java.lang.Throwable -> L7c
            int r4 = r5.length()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            if (r4 == 0) goto L74
            java.lang.String r4 = r5.toString()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            r11.getUsersInternal(r4, r1)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L62
            goto L74
        L5e:
            r2 = move-exception
            goto L66
        L60:
            r0 = move-exception
            goto L7e
        L62:
            r4 = move-exception
            r10 = r4
            r4 = r2
            r2 = r10
        L66:
            r0.clear()     // Catch: java.lang.Throwable -> L7c
            r1.clear()     // Catch: java.lang.Throwable -> L7c
            r11.checkSQLException(r2)     // Catch: java.lang.Throwable -> L7c
            if (r4 == 0) goto L74
            r4.dispose()
        L74:
            org.telegram.messenger.ContactsController r2 = r11.getContactsController()
            r2.processLoadedContacts(r0, r1, r3)
            return
        L7c:
            r0 = move-exception
            r2 = r4
        L7e:
            if (r2 == 0) goto L83
            r2.dispose()
        L83:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getContacts$127():void");
    }

    public void getUnsentMessages(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getUnsentMessages$128(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02bc  */
    /* JADX WARN: Type inference failed for: r11v1, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v5, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r12v5, types: [org.telegram.tgnet.AbstractSerializedData, org.telegram.tgnet.NativeByteBuffer] */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r14v3, types: [java.lang.Object, org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r3v3, types: [org.telegram.messenger.SendMessagesHelper] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getUnsentMessages$128(int r20) {
        /*
            Method dump skipped, instructions count: 704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getUnsentMessages$128(int):void");
    }

    public boolean checkMessageByRandomId(final long j) {
        final boolean[] zArr = new boolean[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$checkMessageByRandomId$129(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
    
        if (r0 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$checkMessageByRandomId$129(long r7, boolean[] r9, java.util.concurrent.CountDownLatch r10) {
        /*
            r6 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r6.database     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            java.util.Locale r2 = java.util.Locale.US     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            java.lang.String r3 = "SELECT random_id FROM randoms_v2 WHERE random_id = %d"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            r8 = 0
            r5[r8] = r7     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            java.lang.String r7 = java.lang.String.format(r2, r3, r5)     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            java.lang.Object[] r2 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            org.telegram.SQLite.SQLiteCursor r0 = r1.queryFinalized(r7, r2)     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            boolean r7 = r0.next()     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            if (r7 == 0) goto L2c
            r9[r8] = r4     // Catch: java.lang.Throwable -> L24 java.lang.Exception -> L26
            goto L2c
        L24:
            r7 = move-exception
            goto L33
        L26:
            r7 = move-exception
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L24
            if (r0 == 0) goto L2f
        L2c:
            r0.dispose()
        L2f:
            r10.countDown()
            return
        L33:
            if (r0 == 0) goto L38
            r0.dispose()
        L38:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$checkMessageByRandomId$129(long, boolean[], java.util.concurrent.CountDownLatch):void");
    }

    public boolean checkMessageId(final long j, final int i) {
        final boolean[] zArr = new boolean[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda88
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$checkMessageId$130(j, i, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        if (r0 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$checkMessageId$130(long r6, int r8, boolean[] r9, java.util.concurrent.CountDownLatch r10) {
        /*
            r5 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r5.database     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.util.Locale r2 = java.util.Locale.US     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r3 = "SELECT mid FROM messages_v2 WHERE uid = %d AND mid = %d"
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r7 = 0
            r4[r7] = r6     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r8 = 1
            r4[r8] = r6     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r6 = java.lang.String.format(r2, r3, r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.Object[] r2 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            org.telegram.SQLite.SQLiteCursor r0 = r1.queryFinalized(r6, r2)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            boolean r6 = r0.next()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            if (r6 == 0) goto L33
            r9[r7] = r8     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            goto L33
        L2b:
            r6 = move-exception
            goto L3a
        L2d:
            r6 = move-exception
            r5.checkSQLException(r6)     // Catch: java.lang.Throwable -> L2b
            if (r0 == 0) goto L36
        L33:
            r0.dispose()
        L36:
            r10.countDown()
            return
        L3a:
            if (r0 == 0) goto L3f
            r0.dispose()
        L3f:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$checkMessageId$130(long, int, boolean[], java.util.concurrent.CountDownLatch):void");
    }

    public void getUnreadMention(final long j, final int i, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getUnreadMention$132(i, j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUnreadMention$132(int i, long j, final IntCallback intCallback) {
        SQLiteCursor queryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (i != 0) {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT MIN(mid) FROM messages_topics WHERE uid = %d AND topic_id = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j), Integer.valueOf(i)), new Object[0]);
                } else {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT MIN(mid) FROM messages_v2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j)), new Object[0]);
                }
                sQLiteCursor = queryFinalized;
                final int intValue = sQLiteCursor.next() ? sQLiteCursor.intValue(0) : 0;
                sQLiteCursor.dispose();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.IntCallback.this.run(intValue);
                    }
                });
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor == null) {
                    return;
                }
            }
            sQLiteCursor.dispose();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    public void getMessagesCount(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda113
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getMessagesCount$134(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessagesCount$134(long j, final IntCallback intCallback) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursor = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM messages_v2 WHERE uid = %d", Long.valueOf(j)), new Object[0]);
                final int intValue = sQLiteCursor.next() ? sQLiteCursor.intValue(0) : 0;
                sQLiteCursor.dispose();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.IntCallback.this.run(intValue);
                    }
                });
            } catch (Exception e) {
                checkSQLException(e);
            }
        } finally {
            sQLiteCursor.dispose();
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:(20:(5:(3:59|60|(12:62|63|64|65|66|(18:598|(2:600|601)(2:792|793)|602|603|(1:605)(1:787)|606|607|608|(1:783)(12:611|(1:613)(1:780)|614|615|(1:617)(1:777)|618|(1:620)(1:776)|621|(1:623)(1:775)|624|625|(1:(9:629|630|(1:632)(1:749)|633|(6:(1:636)(1:744)|637|638|(1:640)(1:740)|641|642)(1:745)|643|(11:(2:646|647)(5:698|699|700|701|702)|648|649|(2:688|689)(1:651)|652|(3:682|683|684)(3:654|655|656)|657|(1:659)(1:676)|660|(3:670|(1:672)|(1:674)(1:675))(1:(1:665)(1:669))|666)(3:706|(7:(2:709|710)(2:732|733)|711|712|(1:714)(1:728)|715|716|(4:719|(1:721)(1:724)|722|668))(1:737)|718)|667|668)(7:(1:751)(1:772)|752|753|(1:755)|756|757|(6:(1:760)(1:769)|761|(1:763)|764|765|(8:767|(0)(0)|633|(0)(0)|643|(0)(0)|667|668)))))|773|630|(0)(0)|633|(0)(0)|643|(0)(0)|667|668)(5:73|74|(7:(1:77)(1:513)|78|79|(1:81)(1:507)|82|(1:(1:85)(1:501))(2:502|(1:504)(1:505))|86)(2:514|(2:(5:(2:518|519)(4:533|534|535|536)|520|(1:522)(1:532)|523|(1:(1:526)(1:528))(1:(1:530)(1:531)))(2:542|(1:544)(1:545))|527)(15:546|(1:548)(1:595)|549|550|(1:552)(1:592)|554|555|(4:578|579|580|581)(1:557)|558|559|(1:561)(1:572)|562|(1:(1:565)(1:567))(1:(1:569)(1:570))|566|88))|87|88)|89|(6:91|(5:95|(1:300)(7:97|98|99|100|(51:102|103|104|105|106|107|108|109|110|111|(2:(2:116|117)|115)|140|(1:146)|147|(3:149|150|151)|155|156|157|158|159|160|(2:274|275)|162|163|164|(2:166|167)(1:273)|168|169|(4:171|172|(1:174)|175)(1:272)|176|(1:271)(1:180)|181|182|(2:184|185)|186|187|188|(2:190|191)(2:268|(1:270))|192|193|(1:195)|196|197|198|(7:200|(2:202|203)(1:262)|240|241|(2:245|(1:247))|248|(7:250|(1:252)(5:253|(1:255)(1:261)|256|(1:258)(1:260)|259)|206|(1:210)|211|(10:218|219|220|221|222|223|224|225|(1:227)|228)(1:213)|214))(1:263)|205|206|(1:239)(2:208|210)|211|(0)(0)|214)(1:296)|215|216)|217|92|93)|301|302|303|304)(1:500)|305|306|(6:308|(3:478|479|(1:484))|316|(1:318)|472|(1:474))(1:485)|(3:418|(13:439|440|441|442|443|444|445|446|448|449|(3:451|452|(2:456|455))(1:458)|454|455)(8:420|421|422|424|425|(2:427|(2:429|430))|434|430)|431)(1:321)))(4:804|(2:806|807)(1:825)|808|(2:810|(4:(1:813)(1:819)|814|(1:816)(1:818)|817)))|305|306|(0)(0)|(0)(0))|602|603|(0)(0)|606|607|608|(0)|783|773|630|(0)(0)|633|(0)(0)|643|(0)(0)|667|668|89|(0)(0))|66|(1:68)|598|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x1b3d, code lost:
    
        if (r6.reply_to_random_id != 0) goto L900;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x1d62, code lost:
    
        if (r62 == 3) goto L1006;
     */
    /* JADX WARN: Code restructure failed: missing block: B:798:0x0eba, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:799:0x0f2d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 16, insn: 0x2043: MOVE (r4 I:??[OBJECT, ARRAY]) = (r16 I:??[OBJECT, ARRAY]), block:B:1352:0x2042 */
    /* JADX WARN: Not initialized variable reg: 37, insn: 0x0379: MOVE (r8 I:??[OBJECT, ARRAY]) = (r37 I:??[OBJECT, ARRAY]), block:B:1092:0x0373 */
    /* JADX WARN: Not initialized variable reg: 37, insn: 0x0774: MOVE (r8 I:??[OBJECT, ARRAY]) = (r37 I:??[OBJECT, ARRAY]), block:B:1090:0x076e */
    /* JADX WARN: Not initialized variable reg: 40, insn: 0x05b8: MOVE (r23 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r40 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:1058:0x05ae */
    /* JADX WARN: Removed duplicated region for block: B:126:0x213b  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x2151  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x2175 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x217c  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x2184  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x1c45  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x1bf9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:308:0x1d33  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x1e62  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x1d8a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:485:0x1d86  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x21af  */
    /* JADX WARN: Removed duplicated region for block: B:493:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:500:0x1d08  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0817  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0841 A[Catch: Exception -> 0x084b, all -> 0x15cc, TRY_ENTER, TRY_LEAVE, TryCatch #96 {all -> 0x15cc, blocks: (B:359:0x1e99, B:361:0x1e9f, B:363:0x1ea5, B:366:0x1ec7, B:369:0x1ee7, B:371:0x1eed, B:373:0x1efb, B:375:0x1eff, B:387:0x1f1d, B:56:0x083b, B:59:0x0841, B:64:0x08d0, B:79:0x0952, B:81:0x0958, B:82:0x095e, B:449:0x1daa, B:451:0x1db0, B:455:0x1dbe, B:425:0x1e39, B:427:0x1e3f, B:430:0x1e49, B:520:0x0b0e, B:522:0x0b14, B:523:0x0b1a, B:603:0x0f22, B:605:0x0f28, B:607:0x0f31, B:615:0x0f88, B:617:0x0f8e, B:618:0x0f94, B:621:0x0fed, B:623:0x0ff3, B:624:0x0ff9, B:638:0x117a, B:641:0x1184, B:649:0x11ed, B:689:0x11f3, B:652:0x11fc, B:657:0x1258, B:659:0x125e, B:660:0x1264, B:712:0x148d, B:714:0x1493, B:715:0x149c, B:804:0x084e, B:808:0x0891, B:810:0x0897, B:813:0x089f, B:814:0x08b0, B:816:0x08b9, B:817:0x08c0, B:819:0x08a8, B:1147:0x1847, B:1149:0x184d, B:1150:0x1856), top: B:18:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:600:0x0ee6 A[Catch: all -> 0x0052, Exception -> 0x0f07, TRY_ENTER, TRY_LEAVE, TryCatch #24 {all -> 0x0052, blocks: (B:1286:0x0066, B:389:0x1f2a, B:391:0x1f31, B:393:0x1f3e, B:395:0x1f4d, B:33:0x0223, B:834:0x07d6, B:837:0x07da, B:840:0x07e5, B:843:0x07ee, B:77:0x08e9, B:85:0x0965, B:501:0x09b9, B:504:0x0a0a, B:505:0x0a54, B:513:0x0922, B:518:0x0aba, B:526:0x0b21, B:528:0x0b73, B:530:0x0bc0, B:531:0x0c0b, B:534:0x0aea, B:536:0x0afa, B:544:0x0c58, B:545:0x0ca2, B:548:0x0cf7, B:579:0x0d42, B:581:0x0d52, B:565:0x0d9c, B:567:0x0de7, B:569:0x0e2d, B:570:0x0e70, B:557:0x0d6d, B:595:0x0d19, B:600:0x0ee6, B:613:0x0f3b, B:620:0x0f99, B:636:0x1115, B:646:0x1199, B:683:0x1201, B:665:0x1270, B:669:0x12df, B:674:0x1348, B:675:0x13c4, B:654:0x1234, B:656:0x1244, B:699:0x11c9, B:702:0x11d9, B:709:0x1450, B:721:0x14a5, B:724:0x1511, B:733:0x1473, B:744:0x114c, B:751:0x100b, B:760:0x107b, B:769:0x10ac, B:772:0x1040, B:776:0x0fc9, B:780:0x0f66, B:793:0x0f0a, B:806:0x0853, B:825:0x0877, B:54:0x0821, B:880:0x02ec, B:882:0x02fe, B:931:0x03a5, B:997:0x048c, B:1000:0x0490, B:1004:0x049b, B:979:0x0505, B:981:0x050e, B:921:0x0799, B:965:0x053b, B:951:0x04c5, B:1017:0x05d9, B:1024:0x0629, B:1036:0x064c, B:1039:0x05fc, B:1041:0x068d, B:1048:0x06f7, B:1053:0x0722, B:1056:0x06be, B:1069:0x0441, B:1097:0x1615, B:1107:0x163c, B:1110:0x1648, B:1227:0x166b, B:1230:0x1670, B:1127:0x1729, B:1132:0x1788, B:1133:0x17c4, B:1136:0x180a, B:1145:0x1831, B:1152:0x185b, B:1166:0x18d1, B:1172:0x18c6, B:1197:0x1953, B:1206:0x1989), top: B:18:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:605:0x0f28 A[Catch: Exception -> 0x0f2d, all -> 0x15cc, TRY_ENTER, TRY_LEAVE, TryCatch #96 {all -> 0x15cc, blocks: (B:359:0x1e99, B:361:0x1e9f, B:363:0x1ea5, B:366:0x1ec7, B:369:0x1ee7, B:371:0x1eed, B:373:0x1efb, B:375:0x1eff, B:387:0x1f1d, B:56:0x083b, B:59:0x0841, B:64:0x08d0, B:79:0x0952, B:81:0x0958, B:82:0x095e, B:449:0x1daa, B:451:0x1db0, B:455:0x1dbe, B:425:0x1e39, B:427:0x1e3f, B:430:0x1e49, B:520:0x0b0e, B:522:0x0b14, B:523:0x0b1a, B:603:0x0f22, B:605:0x0f28, B:607:0x0f31, B:615:0x0f88, B:617:0x0f8e, B:618:0x0f94, B:621:0x0fed, B:623:0x0ff3, B:624:0x0ff9, B:638:0x117a, B:641:0x1184, B:649:0x11ed, B:689:0x11f3, B:652:0x11fc, B:657:0x1258, B:659:0x125e, B:660:0x1264, B:712:0x148d, B:714:0x1493, B:715:0x149c, B:804:0x084e, B:808:0x0891, B:810:0x0897, B:813:0x089f, B:814:0x08b0, B:816:0x08b9, B:817:0x08c0, B:819:0x08a8, B:1147:0x1847, B:1149:0x184d, B:1150:0x1856), top: B:18:0x0064 }] */
    /* JADX WARN: Removed duplicated region for block: B:610:0x0f37 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:632:0x110e  */
    /* JADX WARN: Removed duplicated region for block: B:635:0x1113  */
    /* JADX WARN: Removed duplicated region for block: B:645:0x1197  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x08da  */
    /* JADX WARN: Removed duplicated region for block: B:706:0x1445  */
    /* JADX WARN: Removed duplicated region for block: B:745:0x1193  */
    /* JADX WARN: Removed duplicated region for block: B:749:0x1110  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x0f30  */
    /* JADX WARN: Removed duplicated region for block: B:792:0x0f0a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:804:0x084e A[Catch: all -> 0x15cc, Exception -> 0x15d1, TRY_ENTER, TRY_LEAVE, TryCatch #60 {Exception -> 0x15d1, blocks: (B:56:0x083b, B:804:0x084e, B:808:0x0891), top: B:55:0x083b }] */
    /* JADX WARN: Removed duplicated region for block: B:833:0x07d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x19f7  */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v261, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v270, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v280, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r10v285, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v16 */
    /* JADX WARN: Type inference failed for: r11v191, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r11v200, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v13 */
    /* JADX WARN: Type inference failed for: r12v14, types: [int] */
    /* JADX WARN: Type inference failed for: r12v15 */
    /* JADX WARN: Type inference failed for: r12v16 */
    /* JADX WARN: Type inference failed for: r12v17 */
    /* JADX WARN: Type inference failed for: r12v18, types: [int] */
    /* JADX WARN: Type inference failed for: r12v19 */
    /* JADX WARN: Type inference failed for: r12v20 */
    /* JADX WARN: Type inference failed for: r12v3 */
    /* JADX WARN: Type inference failed for: r12v4 */
    /* JADX WARN: Type inference failed for: r12v5 */
    /* JADX WARN: Type inference failed for: r12v6 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r13v106, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r13v108, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r13v11, types: [java.lang.Object, org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r14v21, types: [org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r16v15 */
    /* JADX WARN: Type inference failed for: r16v17 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r1v145 */
    /* JADX WARN: Type inference failed for: r1v146 */
    /* JADX WARN: Type inference failed for: r1v188 */
    /* JADX WARN: Type inference failed for: r1v22, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r22v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r26v48 */
    /* JADX WARN: Type inference failed for: r26v49 */
    /* JADX WARN: Type inference failed for: r26v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r26v50 */
    /* JADX WARN: Type inference failed for: r26v57 */
    /* JADX WARN: Type inference failed for: r26v59 */
    /* JADX WARN: Type inference failed for: r26v60 */
    /* JADX WARN: Type inference failed for: r26v64 */
    /* JADX WARN: Type inference failed for: r26v67 */
    /* JADX WARN: Type inference failed for: r2v1, types: [long] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v14, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r2v162, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r2v163 */
    /* JADX WARN: Type inference failed for: r2v164 */
    /* JADX WARN: Type inference failed for: r2v165, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v168, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r2v169 */
    /* JADX WARN: Type inference failed for: r2v170 */
    /* JADX WARN: Type inference failed for: r2v176 */
    /* JADX WARN: Type inference failed for: r2v180, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r2v209 */
    /* JADX WARN: Type inference failed for: r2v300 */
    /* JADX WARN: Type inference failed for: r2v301 */
    /* JADX WARN: Type inference failed for: r2v302 */
    /* JADX WARN: Type inference failed for: r2v303 */
    /* JADX WARN: Type inference failed for: r2v304 */
    /* JADX WARN: Type inference failed for: r2v305 */
    /* JADX WARN: Type inference failed for: r2v306 */
    /* JADX WARN: Type inference failed for: r2v307 */
    /* JADX WARN: Type inference failed for: r2v308 */
    /* JADX WARN: Type inference failed for: r2v309 */
    /* JADX WARN: Type inference failed for: r2v314 */
    /* JADX WARN: Type inference failed for: r2v315 */
    /* JADX WARN: Type inference failed for: r2v56 */
    /* JADX WARN: Type inference failed for: r2v57 */
    /* JADX WARN: Type inference failed for: r2v61 */
    /* JADX WARN: Type inference failed for: r2v69 */
    /* JADX WARN: Type inference failed for: r3v35, types: [org.telegram.tgnet.AbstractSerializedData, org.telegram.tgnet.NativeByteBuffer] */
    /* JADX WARN: Type inference failed for: r3v66, types: [int] */
    /* JADX WARN: Type inference failed for: r3v75, types: [int] */
    /* JADX WARN: Type inference failed for: r41v0 */
    /* JADX WARN: Type inference failed for: r41v1 */
    /* JADX WARN: Type inference failed for: r41v10 */
    /* JADX WARN: Type inference failed for: r41v11 */
    /* JADX WARN: Type inference failed for: r41v12 */
    /* JADX WARN: Type inference failed for: r41v13 */
    /* JADX WARN: Type inference failed for: r41v14 */
    /* JADX WARN: Type inference failed for: r41v15 */
    /* JADX WARN: Type inference failed for: r41v16 */
    /* JADX WARN: Type inference failed for: r41v17 */
    /* JADX WARN: Type inference failed for: r41v18 */
    /* JADX WARN: Type inference failed for: r41v19 */
    /* JADX WARN: Type inference failed for: r41v2 */
    /* JADX WARN: Type inference failed for: r41v20 */
    /* JADX WARN: Type inference failed for: r41v21 */
    /* JADX WARN: Type inference failed for: r41v22 */
    /* JADX WARN: Type inference failed for: r41v23 */
    /* JADX WARN: Type inference failed for: r41v24 */
    /* JADX WARN: Type inference failed for: r41v25 */
    /* JADX WARN: Type inference failed for: r41v26 */
    /* JADX WARN: Type inference failed for: r41v27 */
    /* JADX WARN: Type inference failed for: r41v28 */
    /* JADX WARN: Type inference failed for: r41v29 */
    /* JADX WARN: Type inference failed for: r41v3 */
    /* JADX WARN: Type inference failed for: r41v30 */
    /* JADX WARN: Type inference failed for: r41v31 */
    /* JADX WARN: Type inference failed for: r41v36 */
    /* JADX WARN: Type inference failed for: r41v4 */
    /* JADX WARN: Type inference failed for: r41v43 */
    /* JADX WARN: Type inference failed for: r41v44 */
    /* JADX WARN: Type inference failed for: r41v46 */
    /* JADX WARN: Type inference failed for: r41v48 */
    /* JADX WARN: Type inference failed for: r41v49 */
    /* JADX WARN: Type inference failed for: r41v5 */
    /* JADX WARN: Type inference failed for: r41v50 */
    /* JADX WARN: Type inference failed for: r41v51 */
    /* JADX WARN: Type inference failed for: r41v52 */
    /* JADX WARN: Type inference failed for: r41v53 */
    /* JADX WARN: Type inference failed for: r41v54 */
    /* JADX WARN: Type inference failed for: r41v55 */
    /* JADX WARN: Type inference failed for: r41v56 */
    /* JADX WARN: Type inference failed for: r41v57 */
    /* JADX WARN: Type inference failed for: r41v58 */
    /* JADX WARN: Type inference failed for: r41v59 */
    /* JADX WARN: Type inference failed for: r41v6 */
    /* JADX WARN: Type inference failed for: r41v60 */
    /* JADX WARN: Type inference failed for: r41v61 */
    /* JADX WARN: Type inference failed for: r41v62 */
    /* JADX WARN: Type inference failed for: r41v63 */
    /* JADX WARN: Type inference failed for: r41v64 */
    /* JADX WARN: Type inference failed for: r41v65 */
    /* JADX WARN: Type inference failed for: r41v66 */
    /* JADX WARN: Type inference failed for: r41v67 */
    /* JADX WARN: Type inference failed for: r41v68 */
    /* JADX WARN: Type inference failed for: r41v7 */
    /* JADX WARN: Type inference failed for: r41v8 */
    /* JADX WARN: Type inference failed for: r41v9 */
    /* JADX WARN: Type inference failed for: r4v125 */
    /* JADX WARN: Type inference failed for: r4v127 */
    /* JADX WARN: Type inference failed for: r4v128 */
    /* JADX WARN: Type inference failed for: r4v129, types: [int] */
    /* JADX WARN: Type inference failed for: r4v130 */
    /* JADX WARN: Type inference failed for: r4v131 */
    /* JADX WARN: Type inference failed for: r4v133 */
    /* JADX WARN: Type inference failed for: r4v135, types: [int] */
    /* JADX WARN: Type inference failed for: r4v141 */
    /* JADX WARN: Type inference failed for: r4v142 */
    /* JADX WARN: Type inference failed for: r4v145 */
    /* JADX WARN: Type inference failed for: r4v146 */
    /* JADX WARN: Type inference failed for: r4v159 */
    /* JADX WARN: Type inference failed for: r4v161 */
    /* JADX WARN: Type inference failed for: r4v191 */
    /* JADX WARN: Type inference failed for: r4v194 */
    /* JADX WARN: Type inference failed for: r4v195 */
    /* JADX WARN: Type inference failed for: r4v61, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v89, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v92, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v96, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r62v0, types: [int] */
    /* JADX WARN: Type inference failed for: r6v203, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v209, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v218, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v221, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v8, types: [org.telegram.tgnet.AbstractSerializedData, org.telegram.tgnet.NativeByteBuffer] */
    /* JADX WARN: Type inference failed for: r7v10, types: [org.telegram.tgnet.TLRPC$Message] */
    /* JADX WARN: Type inference failed for: r8v53, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v154, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v157, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v171, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v174, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v211, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v221, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v249, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r9v255, types: [java.lang.StringBuilder] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Runnable getMessagesInternal(final long r53, final long r55, int r57, int r58, final int r59, int r60, final int r61, final int r62, final boolean r63, final int r64, final int r65, final boolean r66, final boolean r67) {
        /*
            Method dump skipped, instructions count: 8627
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.getMessagesInternal(long, long, int, int, int, int, int, int, boolean, int, int, boolean, boolean):java.lang.Runnable");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getMessagesInternal$135(TLRPC$Message tLRPC$Message, TLRPC$Message tLRPC$Message2) {
        int i;
        int i2;
        int i3 = tLRPC$Message.id;
        if (i3 > 0 && (i2 = tLRPC$Message2.id) > 0) {
            if (i3 > i2) {
                return -1;
            }
            return i3 < i2 ? 1 : 0;
        }
        if (i3 < 0 && (i = tLRPC$Message2.id) < 0) {
            if (i3 < i) {
                return -1;
            }
            return i3 > i ? 1 : 0;
        }
        int i4 = tLRPC$Message.date;
        int i5 = tLRPC$Message2.date;
        if (i4 > i5) {
            return -1;
        }
        return i4 < i5 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessagesInternal$136(TLRPC$TL_messages_messages tLRPC$TL_messages_messages, int i, long j, long j2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, boolean z, boolean z2, int i11, int i12, boolean z3, int i13, boolean z4, boolean z5) {
        getMessagesController().processLoadedMessages(tLRPC$TL_messages_messages, i, j, j2, i2, i3, i4, true, i5, i6, i7, i8, i9, i10, z, z2 ? 1 : 0, i11, i12, z3, i13, z4, z5);
    }

    private void getAnimatedEmoji(String str, ArrayList<TLRPC$Document> arrayList) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursor = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM animated_emoji WHERE document_id IN (%s)", str), new Object[0]);
                while (sQLiteCursor.next()) {
                    NativeByteBuffer byteBufferValue = sQLiteCursor.byteBufferValue(0);
                    try {
                        TLRPC$Document TLdeserialize = TLRPC$Document.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(true), true);
                        if (TLdeserialize != null && TLdeserialize.id != 0) {
                            arrayList.add(TLdeserialize);
                        }
                    } catch (Exception e) {
                        checkSQLException(e);
                    }
                    if (byteBufferValue != null) {
                        byteBufferValue.reuse();
                    }
                }
            } catch (SQLiteException e2) {
                e2.printStackTrace();
                if (sQLiteCursor == null) {
                    return;
                }
            }
            sQLiteCursor.dispose();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    public void getMessages(final long j, final long j2, boolean z, final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final boolean z2, final int i7, final int i8, final boolean z3, final boolean z4) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda93
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getMessages$138(j, j2, i, i2, i3, i4, i5, i6, z2, i7, i8, z3, z4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessages$138(long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, int i8, boolean z2, boolean z3) {
        final Runnable messagesInternal = getMessagesInternal(j, j2, i, i2, i3, i4, i5, i6, z, i7, i8, z2, z3);
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                messagesInternal.run();
            }
        });
    }

    public void clearSentMedia() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearSentMedia$139();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearSentMedia$139() {
        try {
            this.database.executeFast("DELETE FROM sent_files_v2 WHERE 1").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public Object[] getSentFile(final String str, final int i) {
        if (str == null || str.toLowerCase().endsWith("attheme")) {
            return null;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] objArr = new Object[2];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda135
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getSentFile$140(str, i, objArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        if (objArr[0] != null) {
            return objArr;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSentFile$140(String str, int i, Object[] objArr, CountDownLatch countDownLatch) {
        NativeByteBuffer byteBufferValue;
        try {
            try {
                String MD5 = Utilities.MD5(str);
                if (MD5 != null) {
                    SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, parent FROM sent_files_v2 WHERE uid = '%s' AND type = %d", MD5, Integer.valueOf(i)), new Object[0]);
                    if (queryFinalized.next() && (byteBufferValue = queryFinalized.byteBufferValue(0)) != null) {
                        TLRPC$MessageMedia TLdeserialize = TLRPC$MessageMedia.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                        if (TLdeserialize instanceof TLRPC$TL_messageMediaDocument) {
                            objArr[0] = ((TLRPC$TL_messageMediaDocument) TLdeserialize).document;
                        } else if (TLdeserialize instanceof TLRPC$TL_messageMediaPhoto) {
                            objArr[0] = ((TLRPC$TL_messageMediaPhoto) TLdeserialize).photo;
                        }
                        if (objArr[0] != null) {
                            objArr[1] = queryFinalized.stringValue(1);
                        }
                    }
                    queryFinalized.dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    private void updateWidgets(long j) {
        ArrayList<Long> arrayList = new ArrayList<>();
        arrayList.add(Long.valueOf(j));
        updateWidgets(arrayList);
    }

    private void updateWidgets(ArrayList<Long> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        AppWidgetManager appWidgetManager = null;
        try {
            TextUtils.join(",", arrayList);
            SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT DISTINCT id FROM shortcut_widget WHERE did IN(%s,-1)", TextUtils.join(",", arrayList)), new Object[0]);
            while (queryFinalized.next()) {
                if (appWidgetManager == null) {
                    appWidgetManager = AppWidgetManager.getInstance(ApplicationLoader.applicationContext);
                }
                appWidgetManager.notifyAppWidgetViewDataChanged(queryFinalized.intValue(0), R.id.list_view);
            }
            queryFinalized.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putWidgetDialogs(final int i, final ArrayList<TopicKey> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putWidgetDialogs$141(i, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putWidgetDialogs$141(int i, ArrayList arrayList) {
        try {
            this.database.beginTransaction();
            this.database.executeFast("DELETE FROM shortcut_widget WHERE id = " + i).stepThis().dispose();
            SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO shortcut_widget VALUES(?, ?, ?)");
            if (arrayList.isEmpty()) {
                executeFast.requery();
                executeFast.bindInteger(1, i);
                executeFast.bindLong(2, -1L);
                executeFast.bindInteger(3, 0);
                executeFast.step();
            } else {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    long j = ((TopicKey) arrayList.get(i2)).dialogId;
                    executeFast.requery();
                    executeFast.bindInteger(1, i);
                    executeFast.bindLong(2, j);
                    executeFast.bindInteger(3, i2);
                    executeFast.step();
                }
            }
            executeFast.dispose();
            this.database.commitTransaction();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearWidgetDialogs(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearWidgetDialogs$142(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearWidgetDialogs$142(int i) {
        try {
            this.database.executeFast("DELETE FROM shortcut_widget WHERE id = " + i).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getWidgetDialogIds(final int i, final int i2, final ArrayList<Long> arrayList, final ArrayList<TLRPC$User> arrayList2, final ArrayList<TLRPC$Chat> arrayList3, final boolean z) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getWidgetDialogIds$143(i, arrayList, arrayList2, arrayList3, z, i2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWidgetDialogIds$143(int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, boolean z, int i2, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM shortcut_widget WHERE id = %d ORDER BY ord ASC", Integer.valueOf(i)), new Object[0]);
                while (queryFinalized.next()) {
                    try {
                        long longValue = queryFinalized.longValue(0);
                        if (longValue != -1) {
                            arrayList.add(Long.valueOf(longValue));
                            if (arrayList2 != null && arrayList3 != null) {
                                if (DialogObject.isUserDialog(longValue)) {
                                    arrayList4.add(Long.valueOf(longValue));
                                } else {
                                    arrayList5.add(Long.valueOf(-longValue));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                        throw th;
                    }
                }
                queryFinalized.dispose();
                if (!z && arrayList.isEmpty()) {
                    if (i2 == 0) {
                        SQLiteCursor queryFinalized2 = this.database.queryFinalized("SELECT did FROM dialogs WHERE folder_id = 0 ORDER BY pinned DESC, date DESC LIMIT 0,10", new Object[0]);
                        while (queryFinalized2.next()) {
                            long longValue2 = queryFinalized2.longValue(0);
                            if (!DialogObject.isFolderDialogId(longValue2)) {
                                arrayList.add(Long.valueOf(longValue2));
                                if (arrayList2 != null && arrayList3 != null) {
                                    if (DialogObject.isUserDialog(longValue2)) {
                                        arrayList4.add(Long.valueOf(longValue2));
                                    } else {
                                        arrayList5.add(Long.valueOf(-longValue2));
                                    }
                                }
                            }
                        }
                        queryFinalized2.dispose();
                    } else {
                        SQLiteCursor queryFinalized3 = getMessagesStorage().getDatabase().queryFinalized("SELECT did FROM chat_hints WHERE type = 0 ORDER BY rating DESC LIMIT 4", new Object[0]);
                        while (queryFinalized3.next()) {
                            long longValue3 = queryFinalized3.longValue(0);
                            arrayList.add(Long.valueOf(longValue3));
                            if (arrayList2 != null && arrayList3 != null) {
                                if (DialogObject.isUserDialog(longValue3)) {
                                    arrayList4.add(Long.valueOf(longValue3));
                                } else {
                                    arrayList5.add(Long.valueOf(-longValue3));
                                }
                            }
                        }
                        queryFinalized3.dispose();
                    }
                }
                if (arrayList2 != null && arrayList3 != null) {
                    if (!arrayList5.isEmpty()) {
                        getChatsInternal(TextUtils.join(",", arrayList5), arrayList3);
                    }
                    if (!arrayList4.isEmpty()) {
                        getUsersInternal(TextUtils.join(",", arrayList4), arrayList2);
                    }
                }
            } catch (Exception e2) {
                e = e2;
            }
            countDownLatch.countDown();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void getWidgetDialogs(final int i, final int i2, final ArrayList<Long> arrayList, final LongSparseArray<TLRPC$Dialog> longSparseArray, final LongSparseArray<TLRPC$Message> longSparseArray2, final ArrayList<TLRPC$User> arrayList2, final ArrayList<TLRPC$Chat> arrayList3) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getWidgetDialogs$144(i, arrayList, i2, longSparseArray, longSparseArray2, arrayList3, arrayList2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWidgetDialogs$144(int i, ArrayList arrayList, int i2, LongSparseArray longSparseArray, LongSparseArray longSparseArray2, ArrayList arrayList2, ArrayList arrayList3, CountDownLatch countDownLatch) {
        boolean z;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                ArrayList arrayList4 = new ArrayList();
                ArrayList arrayList5 = new ArrayList();
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM shortcut_widget WHERE id = %d ORDER BY ord ASC", Integer.valueOf(i)), new Object[0]);
                while (queryFinalized.next()) {
                    try {
                        long longValue = queryFinalized.longValue(0);
                        if (longValue != -1) {
                            arrayList.add(Long.valueOf(longValue));
                            if (DialogObject.isUserDialog(longValue)) {
                                arrayList4.add(Long.valueOf(longValue));
                            } else {
                                arrayList5.add(Long.valueOf(-longValue));
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                        throw th;
                    }
                }
                queryFinalized.dispose();
                if (arrayList.isEmpty() && i2 == 1) {
                    SQLiteCursor queryFinalized2 = getMessagesStorage().getDatabase().queryFinalized("SELECT did FROM chat_hints WHERE type = 0 ORDER BY rating DESC LIMIT 4", new Object[0]);
                    while (queryFinalized2.next()) {
                        long longValue2 = queryFinalized2.longValue(0);
                        arrayList.add(Long.valueOf(longValue2));
                        if (DialogObject.isUserDialog(longValue2)) {
                            arrayList4.add(Long.valueOf(longValue2));
                        } else {
                            arrayList5.add(Long.valueOf(-longValue2));
                        }
                    }
                    queryFinalized2.dispose();
                }
                if (arrayList.isEmpty()) {
                    queryFinalized = this.database.queryFinalized("SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date FROM dialogs as d LEFT JOIN messages_v2 as m ON d.last_mid = m.mid AND d.did = m.uid WHERE d.folder_id = 0 ORDER BY d.pinned DESC, d.date DESC LIMIT 0,10", new Object[0]);
                    z = true;
                } else {
                    queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date FROM dialogs as d LEFT JOIN messages_v2 as m ON d.last_mid = m.mid AND d.did = m.uid WHERE d.did IN(%s)", TextUtils.join(",", arrayList)), new Object[0]);
                    z = false;
                }
                while (queryFinalized.next()) {
                    long longValue3 = queryFinalized.longValue(0);
                    if (!DialogObject.isFolderDialogId(longValue3)) {
                        if (z) {
                            arrayList.add(Long.valueOf(longValue3));
                        }
                        TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
                        tLRPC$TL_dialog.id = longValue3;
                        tLRPC$TL_dialog.top_message = queryFinalized.intValue(1);
                        tLRPC$TL_dialog.unread_count = queryFinalized.intValue(2);
                        tLRPC$TL_dialog.last_message_date = queryFinalized.intValue(3);
                        longSparseArray.put(tLRPC$TL_dialog.id, tLRPC$TL_dialog);
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(4);
                        if (byteBufferValue != null) {
                            TLRPC$Message TLdeserialize = TLRPC$Message.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            TLdeserialize.readAttachPath(byteBufferValue, getUserConfig().clientUserId);
                            byteBufferValue.reuse();
                            MessageObject.setUnreadFlags(TLdeserialize, queryFinalized.intValue(5));
                            TLdeserialize.id = queryFinalized.intValue(6);
                            TLdeserialize.send_state = queryFinalized.intValue(7);
                            int intValue = queryFinalized.intValue(8);
                            if (intValue != 0) {
                                tLRPC$TL_dialog.last_message_date = intValue;
                            }
                            long j = tLRPC$TL_dialog.id;
                            TLdeserialize.dialog_id = j;
                            longSparseArray2.put(j, TLdeserialize);
                            addUsersAndChatsFromMessage(TLdeserialize, arrayList4, arrayList5, null);
                        }
                    }
                }
                queryFinalized.dispose();
                if (!z && arrayList.size() > longSparseArray.size()) {
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        long longValue4 = ((Long) arrayList.get(i3)).longValue();
                        if (longSparseArray.get(((Long) arrayList.get(i3)).longValue()) == null) {
                            TLRPC$TL_dialog tLRPC$TL_dialog2 = new TLRPC$TL_dialog();
                            tLRPC$TL_dialog2.id = longValue4;
                            longSparseArray.put(longValue4, tLRPC$TL_dialog2);
                            if (DialogObject.isChatDialog(longValue4)) {
                                long j2 = -longValue4;
                                if (arrayList5.contains(Long.valueOf(j2))) {
                                    arrayList5.add(Long.valueOf(j2));
                                }
                            } else if (arrayList4.contains(Long.valueOf(longValue4))) {
                                arrayList4.add(Long.valueOf(longValue4));
                            }
                        }
                    }
                }
                if (!arrayList5.isEmpty()) {
                    getChatsInternal(TextUtils.join(",", arrayList5), arrayList2);
                }
                if (!arrayList4.isEmpty()) {
                    getUsersInternal(TextUtils.join(",", arrayList4), arrayList3);
                }
            } catch (Exception e2) {
                e = e2;
            }
            countDownLatch.countDown();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void putSentFile(final String str, final TLObject tLObject, final int i, final String str2) {
        if (str == null || tLObject == null || str2 == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda137
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putSentFile$145(str, tLObject, i, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putSentFile$145(String str, TLObject tLObject, int i, String str2) {
        TLRPC$MessageMedia tLRPC$MessageMedia;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                String MD5 = Utilities.MD5(str);
                if (MD5 != null) {
                    if (tLObject instanceof TLRPC$Photo) {
                        tLRPC$MessageMedia = new TLRPC$TL_messageMediaPhoto();
                        tLRPC$MessageMedia.photo = (TLRPC$Photo) tLObject;
                        tLRPC$MessageMedia.flags |= 1;
                    } else if (tLObject instanceof TLRPC$Document) {
                        tLRPC$MessageMedia = new TLRPC$TL_messageMediaDocument();
                        tLRPC$MessageMedia.document = (TLRPC$Document) tLObject;
                        tLRPC$MessageMedia.flags |= 1;
                    } else {
                        tLRPC$MessageMedia = null;
                    }
                    if (tLRPC$MessageMedia == null) {
                        return;
                    }
                    sQLitePreparedStatement = this.database.executeFast("REPLACE INTO sent_files_v2 VALUES(?, ?, ?, ?)");
                    sQLitePreparedStatement.requery();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$MessageMedia.getObjectSize());
                    tLRPC$MessageMedia.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatement.bindString(1, MD5);
                    sQLitePreparedStatement.bindInteger(2, i);
                    sQLitePreparedStatement.bindByteBuffer(3, nativeByteBuffer);
                    sQLitePreparedStatement.bindString(4, str2);
                    sQLitePreparedStatement.step();
                    nativeByteBuffer.reuse();
                }
                if (sQLitePreparedStatement == null) {
                    return;
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatSeq(final TLRPC$EncryptedChat tLRPC$EncryptedChat, final boolean z) {
        if (tLRPC$EncryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda183
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateEncryptedChatSeq$146(tLRPC$EncryptedChat, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatSeq$146(TLRPC$EncryptedChat tLRPC$EncryptedChat, boolean z) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE enc_chats SET seq_in = ?, seq_out = ?, use_count = ?, in_seq_no = ?, mtproto_seq = ? WHERE uid = ?");
                sQLitePreparedStatement.bindInteger(1, tLRPC$EncryptedChat.seq_in);
                sQLitePreparedStatement.bindInteger(2, tLRPC$EncryptedChat.seq_out);
                sQLitePreparedStatement.bindInteger(3, (tLRPC$EncryptedChat.key_use_count_in << 16) | tLRPC$EncryptedChat.key_use_count_out);
                sQLitePreparedStatement.bindInteger(4, tLRPC$EncryptedChat.in_seq_no);
                sQLitePreparedStatement.bindInteger(5, tLRPC$EncryptedChat.mtproto_seq);
                sQLitePreparedStatement.bindInteger(6, tLRPC$EncryptedChat.id);
                sQLitePreparedStatement.step();
                if (z && tLRPC$EncryptedChat.in_seq_no != 0) {
                    long encryptedChatId = DialogObject.getEncryptedChatId(tLRPC$EncryptedChat.id);
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM messages_v2 WHERE mid IN (SELECT m.mid FROM messages_v2 as m LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.uid = %d AND m.date = 0 AND m.mid < 0 AND s.seq_out <= %d) AND uid = %d", Long.valueOf(encryptedChatId), Integer.valueOf(tLRPC$EncryptedChat.in_seq_no), Long.valueOf(encryptedChatId))).stepThis().dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatTTL(final TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        if (tLRPC$EncryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda180
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateEncryptedChatTTL$147(tLRPC$EncryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatTTL$147(TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE enc_chats SET ttl = ? WHERE uid = ?");
                sQLitePreparedStatement.bindInteger(1, tLRPC$EncryptedChat.ttl);
                sQLitePreparedStatement.bindInteger(2, tLRPC$EncryptedChat.id);
                sQLitePreparedStatement.step();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatLayer(final TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        if (tLRPC$EncryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda181
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateEncryptedChatLayer$148(tLRPC$EncryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatLayer$148(TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE enc_chats SET layer = ? WHERE uid = ?");
                sQLitePreparedStatement.bindInteger(1, tLRPC$EncryptedChat.layer);
                sQLitePreparedStatement.bindInteger(2, tLRPC$EncryptedChat.id);
                sQLitePreparedStatement.step();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChat(final TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        if (tLRPC$EncryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda179
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateEncryptedChat$149(tLRPC$EncryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChat$149(TLRPC$EncryptedChat tLRPC$EncryptedChat) {
        byte[] bArr;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                byte[] bArr2 = tLRPC$EncryptedChat.key_hash;
                if ((bArr2 == null || bArr2.length < 16) && (bArr = tLRPC$EncryptedChat.auth_key) != null) {
                    tLRPC$EncryptedChat.key_hash = AndroidUtilities.calcAuthKeyHash(bArr);
                }
                sQLitePreparedStatement = this.database.executeFast("UPDATE enc_chats SET data = ?, g = ?, authkey = ?, ttl = ?, layer = ?, seq_in = ?, seq_out = ?, use_count = ?, exchange_id = ?, key_date = ?, fprint = ?, fauthkey = ?, khash = ?, in_seq_no = ?, admin_id = ?, mtproto_seq = ? WHERE uid = ?");
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$EncryptedChat.getObjectSize());
                byte[] bArr3 = tLRPC$EncryptedChat.a_or_b;
                NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(bArr3 != null ? bArr3.length : 1);
                byte[] bArr4 = tLRPC$EncryptedChat.auth_key;
                NativeByteBuffer nativeByteBuffer3 = new NativeByteBuffer(bArr4 != null ? bArr4.length : 1);
                byte[] bArr5 = tLRPC$EncryptedChat.future_auth_key;
                NativeByteBuffer nativeByteBuffer4 = new NativeByteBuffer(bArr5 != null ? bArr5.length : 1);
                byte[] bArr6 = tLRPC$EncryptedChat.key_hash;
                NativeByteBuffer nativeByteBuffer5 = new NativeByteBuffer(bArr6 != null ? bArr6.length : 1);
                tLRPC$EncryptedChat.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatement.bindByteBuffer(1, nativeByteBuffer);
                byte[] bArr7 = tLRPC$EncryptedChat.a_or_b;
                if (bArr7 != null) {
                    nativeByteBuffer2.writeBytes(bArr7);
                }
                byte[] bArr8 = tLRPC$EncryptedChat.auth_key;
                if (bArr8 != null) {
                    nativeByteBuffer3.writeBytes(bArr8);
                }
                byte[] bArr9 = tLRPC$EncryptedChat.future_auth_key;
                if (bArr9 != null) {
                    nativeByteBuffer4.writeBytes(bArr9);
                }
                byte[] bArr10 = tLRPC$EncryptedChat.key_hash;
                if (bArr10 != null) {
                    nativeByteBuffer5.writeBytes(bArr10);
                }
                sQLitePreparedStatement.bindByteBuffer(2, nativeByteBuffer2);
                sQLitePreparedStatement.bindByteBuffer(3, nativeByteBuffer3);
                sQLitePreparedStatement.bindInteger(4, tLRPC$EncryptedChat.ttl);
                sQLitePreparedStatement.bindInteger(5, tLRPC$EncryptedChat.layer);
                sQLitePreparedStatement.bindInteger(6, tLRPC$EncryptedChat.seq_in);
                sQLitePreparedStatement.bindInteger(7, tLRPC$EncryptedChat.seq_out);
                sQLitePreparedStatement.bindInteger(8, (tLRPC$EncryptedChat.key_use_count_in << 16) | tLRPC$EncryptedChat.key_use_count_out);
                sQLitePreparedStatement.bindLong(9, tLRPC$EncryptedChat.exchange_id);
                sQLitePreparedStatement.bindInteger(10, tLRPC$EncryptedChat.key_create_date);
                sQLitePreparedStatement.bindLong(11, tLRPC$EncryptedChat.future_key_fingerprint);
                sQLitePreparedStatement.bindByteBuffer(12, nativeByteBuffer4);
                sQLitePreparedStatement.bindByteBuffer(13, nativeByteBuffer5);
                sQLitePreparedStatement.bindInteger(14, tLRPC$EncryptedChat.in_seq_no);
                sQLitePreparedStatement.bindLong(15, tLRPC$EncryptedChat.admin_id);
                sQLitePreparedStatement.bindInteger(16, tLRPC$EncryptedChat.mtproto_seq);
                sQLitePreparedStatement.bindInteger(17, tLRPC$EncryptedChat.id);
                sQLitePreparedStatement.step();
                nativeByteBuffer.reuse();
                nativeByteBuffer2.reuse();
                nativeByteBuffer3.reuse();
                nativeByteBuffer4.reuse();
                nativeByteBuffer5.reuse();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void isDialogHasTopMessage(final long j, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda101
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$isDialogHasTopMessage$150(j, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0032, code lost:
    
        if (r1 == null) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$isDialogHasTopMessage$150(long r8, java.lang.Runnable r10) {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r7.database     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.lang.String r4 = "SELECT last_mid FROM dialogs WHERE did = %d"
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            r6[r0] = r8     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.lang.String r8 = java.lang.String.format(r3, r4, r6)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            java.lang.Object[] r9 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            org.telegram.SQLite.SQLiteCursor r1 = r2.queryFinalized(r8, r9)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            boolean r8 = r1.next()     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            if (r8 == 0) goto L28
            int r8 = r1.intValue(r0)     // Catch: java.lang.Throwable -> L2c java.lang.Exception -> L2e
            if (r8 == 0) goto L28
            r0 = 1
        L28:
            r1.dispose()
            goto L35
        L2c:
            r8 = move-exception
            goto L3b
        L2e:
            r8 = move-exception
            r7.checkSQLException(r8)     // Catch: java.lang.Throwable -> L2c
            if (r1 == 0) goto L35
            goto L28
        L35:
            if (r0 != 0) goto L3a
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r10)
        L3a:
            return
        L3b:
            if (r1 == 0) goto L40
            r1.dispose()
        L40:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$isDialogHasTopMessage$150(long, java.lang.Runnable):void");
    }

    public boolean hasAuthMessage(final int i) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda68
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$hasAuthMessage$151(i, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0028, code lost:
    
        if (r0 == null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$hasAuthMessage$151(int r7, boolean[] r8, java.util.concurrent.CountDownLatch r9) {
        /*
            r6 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r6.database     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            java.util.Locale r2 = java.util.Locale.US     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            java.lang.String r3 = "SELECT mid FROM messages_v2 WHERE uid = 777000 AND date = %d AND mid < 0 LIMIT 1"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            r5 = 0
            r4[r5] = r7     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            java.lang.String r7 = java.lang.String.format(r2, r3, r4)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            java.lang.Object[] r2 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            org.telegram.SQLite.SQLiteCursor r0 = r1.queryFinalized(r7, r2)     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            boolean r7 = r0.next()     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            r8[r5] = r7     // Catch: java.lang.Throwable -> L22 java.lang.Exception -> L24
            goto L2a
        L22:
            r7 = move-exception
            goto L31
        L24:
            r7 = move-exception
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L22
            if (r0 == 0) goto L2d
        L2a:
            r0.dispose()
        L2d:
            r9.countDown()
            return
        L31:
            if (r0 == 0) goto L36
            r0.dispose()
        L36:
            r9.countDown()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$hasAuthMessage$151(int, boolean[], java.util.concurrent.CountDownLatch):void");
    }

    public void getEncryptedChat(final long j, final CountDownLatch countDownLatch, final ArrayList<TLObject> arrayList) {
        if (countDownLatch == null || arrayList == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getEncryptedChat$152(j, arrayList, countDownLatch);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEncryptedChat$152(long j, ArrayList arrayList, CountDownLatch countDownLatch) {
        try {
            try {
                ArrayList<Long> arrayList2 = new ArrayList<>();
                ArrayList<TLRPC$EncryptedChat> arrayList3 = new ArrayList<>();
                getEncryptedChatsInternal("" + j, arrayList3, arrayList2);
                if (!arrayList3.isEmpty() && !arrayList2.isEmpty()) {
                    ArrayList<TLRPC$User> arrayList4 = new ArrayList<>();
                    getUsersInternal(TextUtils.join(",", arrayList2), arrayList4);
                    if (!arrayList4.isEmpty()) {
                        arrayList.add(arrayList3.get(0));
                        arrayList.add(arrayList4.get(0));
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    public void putEncryptedChat(final TLRPC$EncryptedChat tLRPC$EncryptedChat, final TLRPC$User tLRPC$User, final TLRPC$Dialog tLRPC$Dialog) {
        if (tLRPC$EncryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda182
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putEncryptedChat$153(tLRPC$EncryptedChat, tLRPC$User, tLRPC$Dialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0193  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putEncryptedChat$153(org.telegram.tgnet.TLRPC$EncryptedChat r17, org.telegram.tgnet.TLRPC$User r18, org.telegram.tgnet.TLRPC$Dialog r19) {
        /*
            Method dump skipped, instructions count: 407
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putEncryptedChat$153(org.telegram.tgnet.TLRPC$EncryptedChat, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Dialog):void");
    }

    private String formatUserSearchName(TLRPC$User tLRPC$User) {
        StringBuilder sb = new StringBuilder();
        String str = tLRPC$User.first_name;
        if (str != null && str.length() > 0) {
            sb.append(tLRPC$User.first_name);
        }
        String str2 = tLRPC$User.last_name;
        if (str2 != null && str2.length() > 0) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(tLRPC$User.last_name);
        }
        sb.append(";;;");
        String str3 = tLRPC$User.username;
        if (str3 != null && str3.length() > 0) {
            sb.append(tLRPC$User.username);
        } else {
            ArrayList<TLRPC$TL_username> arrayList = tLRPC$User.usernames;
            if (arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < tLRPC$User.usernames.size(); i++) {
                    TLRPC$TL_username tLRPC$TL_username = tLRPC$User.usernames.get(i);
                    if (tLRPC$TL_username != null && tLRPC$TL_username.active) {
                        sb.append(tLRPC$TL_username.username);
                        sb.append(";;");
                    }
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    private void putUsersInternal(ArrayList<TLRPC$User> arrayList) throws Exception {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO users VALUES(?, ?, ?, ?)");
        for (int i = 0; i < arrayList.size(); i++) {
            TLRPC$User tLRPC$User = arrayList.get(i);
            if (tLRPC$User != null && tLRPC$User.min) {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM users WHERE uid = %d", Long.valueOf(tLRPC$User.id)), new Object[0]);
                if (queryFinalized.next()) {
                    try {
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                        if (byteBufferValue != null) {
                            TLRPC$User TLdeserialize = TLRPC$User.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            byteBufferValue.reuse();
                            if (TLdeserialize != null) {
                                String str = tLRPC$User.username;
                                if (str != null) {
                                    TLdeserialize.username = str;
                                    TLdeserialize.flags |= 8;
                                } else {
                                    TLdeserialize.username = null;
                                    TLdeserialize.flags &= -9;
                                }
                                if (tLRPC$User.apply_min_photo) {
                                    TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto = tLRPC$User.photo;
                                    if (tLRPC$UserProfilePhoto != null) {
                                        TLdeserialize.photo = tLRPC$UserProfilePhoto;
                                        TLdeserialize.flags |= 32;
                                    } else {
                                        TLdeserialize.photo = null;
                                        TLdeserialize.flags &= -33;
                                    }
                                }
                                tLRPC$User = TLdeserialize;
                            }
                        }
                    } catch (Exception e) {
                        checkSQLException(e);
                    }
                }
                queryFinalized.dispose();
            }
            executeFast.requery();
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$User.getObjectSize());
            tLRPC$User.serializeToStream(nativeByteBuffer);
            executeFast.bindLong(1, tLRPC$User.id);
            executeFast.bindString(2, formatUserSearchName(tLRPC$User));
            TLRPC$UserStatus tLRPC$UserStatus = tLRPC$User.status;
            if (tLRPC$UserStatus != null) {
                if (tLRPC$UserStatus instanceof TLRPC$TL_userStatusRecently) {
                    tLRPC$UserStatus.expires = -100;
                } else if (tLRPC$UserStatus instanceof TLRPC$TL_userStatusLastWeek) {
                    tLRPC$UserStatus.expires = -101;
                } else if (tLRPC$UserStatus instanceof TLRPC$TL_userStatusLastMonth) {
                    tLRPC$UserStatus.expires = -102;
                }
                executeFast.bindInteger(3, tLRPC$UserStatus.expires);
            } else {
                executeFast.bindInteger(3, 0);
            }
            executeFast.bindByteBuffer(4, nativeByteBuffer);
            executeFast.step();
            nativeByteBuffer.reuse();
        }
        executeFast.dispose();
    }

    public void updateChatDefaultBannedRights(final long j, final TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights, final int i) {
        if (tLRPC$TL_chatBannedRights == null || j == 0) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda85
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateChatDefaultBannedRights$154(j, i, tLRPC$TL_chatBannedRights);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v16, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r8v6 */
    public /* synthetic */ void lambda$updateChatDefaultBannedRights$154(long j, int i, TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights) {
        TLRPC$Chat tLRPC$Chat;
        NativeByteBuffer byteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid = %d", Long.valueOf(j)), new Object[0]);
                try {
                    if (!queryFinalized.next() || (byteBufferValue = queryFinalized.byteBufferValue(0)) == null) {
                        tLRPC$Chat = null;
                    } else {
                        tLRPC$Chat = TLRPC$Chat.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                        byteBufferValue.reuse();
                    }
                    queryFinalized.dispose();
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    j = 0;
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    j = 0;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    if (j != 0) {
                        j.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                j = 0;
            } catch (Throwable th2) {
                th = th2;
                j = 0;
            }
            if (tLRPC$Chat != null) {
                if (tLRPC$Chat.default_banned_rights == null || i >= tLRPC$Chat.version) {
                    tLRPC$Chat.default_banned_rights = tLRPC$TL_chatBannedRights;
                    tLRPC$Chat.flags |= 262144;
                    tLRPC$Chat.version = i;
                    j = this.database.executeFast("UPDATE chats SET data = ? WHERE uid = ?");
                    try {
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$Chat.getObjectSize());
                        tLRPC$Chat.serializeToStream(nativeByteBuffer);
                        j.bindByteBuffer(1, nativeByteBuffer);
                        j.bindLong(2, tLRPC$Chat.id);
                        j.step();
                        nativeByteBuffer.reuse();
                        j.dispose();
                    } catch (Exception e3) {
                        e = e3;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        if (j != 0) {
                            j.dispose();
                        }
                    }
                }
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private void putChatsInternal(ArrayList<TLRPC$Chat> arrayList) throws Exception {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        SQLitePreparedStatement executeFast = this.database.executeFast("REPLACE INTO chats VALUES(?, ?, ?)");
        for (int i = 0; i < arrayList.size(); i++) {
            TLRPC$Chat tLRPC$Chat = arrayList.get(i);
            if (tLRPC$Chat.min) {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid = %d", Long.valueOf(tLRPC$Chat.id)), new Object[0]);
                if (queryFinalized.next()) {
                    try {
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                        if (byteBufferValue != null) {
                            TLRPC$Chat TLdeserialize = TLRPC$Chat.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            byteBufferValue.reuse();
                            if (TLdeserialize != null) {
                                TLdeserialize.title = tLRPC$Chat.title;
                                TLdeserialize.photo = tLRPC$Chat.photo;
                                TLdeserialize.broadcast = tLRPC$Chat.broadcast;
                                TLdeserialize.verified = tLRPC$Chat.verified;
                                TLdeserialize.megagroup = tLRPC$Chat.megagroup;
                                TLdeserialize.call_not_empty = tLRPC$Chat.call_not_empty;
                                TLdeserialize.call_active = tLRPC$Chat.call_active;
                                TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights = tLRPC$Chat.default_banned_rights;
                                if (tLRPC$TL_chatBannedRights != null) {
                                    TLdeserialize.default_banned_rights = tLRPC$TL_chatBannedRights;
                                    TLdeserialize.flags |= 262144;
                                }
                                TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights = tLRPC$Chat.admin_rights;
                                if (tLRPC$TL_chatAdminRights != null) {
                                    TLdeserialize.admin_rights = tLRPC$TL_chatAdminRights;
                                    TLdeserialize.flags |= LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM;
                                }
                                TLRPC$TL_chatBannedRights tLRPC$TL_chatBannedRights2 = tLRPC$Chat.banned_rights;
                                if (tLRPC$TL_chatBannedRights2 != null) {
                                    TLdeserialize.banned_rights = tLRPC$TL_chatBannedRights2;
                                    TLdeserialize.flags |= LiteMode.FLAG_CHAT_SCALE;
                                }
                                String str = tLRPC$Chat.username;
                                if (str != null) {
                                    TLdeserialize.username = str;
                                    TLdeserialize.flags |= 64;
                                } else {
                                    TLdeserialize.username = null;
                                    TLdeserialize.flags &= -65;
                                }
                                tLRPC$Chat = TLdeserialize;
                            }
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
                queryFinalized.dispose();
            }
            executeFast.requery();
            tLRPC$Chat.flags |= 131072;
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLRPC$Chat.getObjectSize());
            tLRPC$Chat.serializeToStream(nativeByteBuffer);
            executeFast.bindLong(1, tLRPC$Chat.id);
            String str2 = tLRPC$Chat.title;
            if (str2 != null) {
                executeFast.bindString(2, str2.toLowerCase());
            } else {
                executeFast.bindString(2, "");
            }
            executeFast.bindByteBuffer(3, nativeByteBuffer);
            executeFast.step();
            nativeByteBuffer.reuse();
            this.dialogIsForum.put(-tLRPC$Chat.id, tLRPC$Chat.forum ? 1 : 0);
        }
        executeFast.dispose();
    }

    public void getUsersInternal(String str, ArrayList<TLRPC$User> arrayList) throws Exception {
        if (str == null || str.length() == 0 || arrayList == null) {
            return;
        }
        SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, status FROM users WHERE uid IN(%s)", str), new Object[0]);
        while (queryFinalized.next()) {
            try {
                NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                if (byteBufferValue != null) {
                    TLRPC$User TLdeserialize = TLRPC$User.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                    byteBufferValue.reuse();
                    if (TLdeserialize != null) {
                        TLRPC$UserStatus tLRPC$UserStatus = TLdeserialize.status;
                        if (tLRPC$UserStatus != null) {
                            tLRPC$UserStatus.expires = queryFinalized.intValue(1);
                        }
                        arrayList.add(TLdeserialize);
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        queryFinalized.dispose();
    }

    public void getChatsInternal(String str, ArrayList<TLRPC$Chat> arrayList) throws Exception {
        getChatsInternal(str, arrayList, true);
    }

    public void getChatsInternal(String str, ArrayList<TLRPC$Chat> arrayList, boolean z) throws Exception {
        if (str == null || str.length() == 0 || arrayList == null) {
            return;
        }
        SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid IN(%s)", str), new Object[0]);
        while (queryFinalized.next()) {
            try {
                NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                if (byteBufferValue != null) {
                    TLRPC$Chat TLdeserialize = TLRPC$Chat.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false, z);
                    byteBufferValue.reuse();
                    if (TLdeserialize != null) {
                        arrayList.add(TLdeserialize);
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        queryFinalized.dispose();
    }

    public void getEncryptedChatsInternal(String str, ArrayList<TLRPC$EncryptedChat> arrayList, ArrayList<Long> arrayList2) throws Exception {
        if (str == null || str.length() == 0 || arrayList == null) {
            return;
        }
        SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, user, g, authkey, ttl, layer, seq_in, seq_out, use_count, exchange_id, key_date, fprint, fauthkey, khash, in_seq_no, admin_id, mtproto_seq FROM enc_chats WHERE uid IN(%s)", str), new Object[0]);
        while (queryFinalized.next()) {
            try {
                NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(0);
                if (byteBufferValue != null) {
                    TLRPC$EncryptedChat TLdeserialize = TLRPC$EncryptedChat.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                    byteBufferValue.reuse();
                    if (TLdeserialize != null) {
                        long longValue = queryFinalized.longValue(1);
                        TLdeserialize.user_id = longValue;
                        if (arrayList2 != null && !arrayList2.contains(Long.valueOf(longValue))) {
                            arrayList2.add(Long.valueOf(TLdeserialize.user_id));
                        }
                        TLdeserialize.a_or_b = queryFinalized.byteArrayValue(2);
                        TLdeserialize.auth_key = queryFinalized.byteArrayValue(3);
                        TLdeserialize.ttl = queryFinalized.intValue(4);
                        TLdeserialize.layer = queryFinalized.intValue(5);
                        TLdeserialize.seq_in = queryFinalized.intValue(6);
                        TLdeserialize.seq_out = queryFinalized.intValue(7);
                        int intValue = queryFinalized.intValue(8);
                        TLdeserialize.key_use_count_in = (short) (intValue >> 16);
                        TLdeserialize.key_use_count_out = (short) intValue;
                        TLdeserialize.exchange_id = queryFinalized.longValue(9);
                        TLdeserialize.key_create_date = queryFinalized.intValue(10);
                        TLdeserialize.future_key_fingerprint = queryFinalized.longValue(11);
                        TLdeserialize.future_auth_key = queryFinalized.byteArrayValue(12);
                        TLdeserialize.key_hash = queryFinalized.byteArrayValue(13);
                        TLdeserialize.in_seq_no = queryFinalized.intValue(14);
                        long longValue2 = queryFinalized.longValue(15);
                        if (longValue2 != 0) {
                            TLdeserialize.admin_id = longValue2;
                        }
                        TLdeserialize.mtproto_seq = queryFinalized.intValue(16);
                        arrayList.add(TLdeserialize);
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        queryFinalized.dispose();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: putUsersAndChatsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$putUsersAndChats$155(ArrayList<TLRPC$User> arrayList, ArrayList<TLRPC$Chat> arrayList2, boolean z) {
        SQLiteDatabase sQLiteDatabase;
        try {
            if (z) {
                try {
                    this.database.beginTransaction();
                } catch (Exception e) {
                    checkSQLException(e);
                    sQLiteDatabase = this.database;
                    if (sQLiteDatabase == null) {
                        return;
                    }
                }
            }
            putUsersInternal(arrayList);
            putChatsInternal(arrayList2);
            sQLiteDatabase = this.database;
            if (sQLiteDatabase == null) {
                return;
            }
            sQLiteDatabase.commitTransaction();
        } catch (Throwable th) {
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 != null) {
                sQLiteDatabase2.commitTransaction();
            }
            throw th;
        }
    }

    public void putUsersAndChats(final ArrayList<TLRPC$User> arrayList, final ArrayList<TLRPC$Chat> arrayList2, final boolean z, boolean z2) {
        if (arrayList == null || !arrayList.isEmpty() || arrayList2 == null || !arrayList2.isEmpty()) {
            if (z2) {
                this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda160
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$putUsersAndChats$155(arrayList, arrayList2, z);
                    }
                });
            } else {
                lambda$putUsersAndChats$155(arrayList, arrayList2, z);
            }
        }
    }

    public void removeFromDownloadQueue(final long j, final int i, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda201
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$removeFromDownloadQueue$156(z, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x008a -> B:12:0x008f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x008c -> B:12:0x008f). Please report as a decompilation issue!!! */
    public /* synthetic */ void lambda$removeFromDownloadQueue$156(boolean z, int i, long j) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (z) {
                    SQLiteDatabase sQLiteDatabase = this.database;
                    Locale locale = Locale.US;
                    SQLiteCursor queryFinalized = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT min(date) FROM download_queue WHERE type = %d", Integer.valueOf(i)), new Object[0]);
                    try {
                        int intValue = queryFinalized.next() ? queryFinalized.intValue(0) : -1;
                        queryFinalized.dispose();
                        if (intValue != -1) {
                            this.database.executeFast(String.format(locale, "UPDATE download_queue SET date = %d WHERE uid = %d AND type = %d", Integer.valueOf(intValue - 1), Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                } else {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE uid = %d AND type = %d", Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void deleteFromDownloadQueue(final java.util.ArrayList<android.util.Pair<java.lang.Long, java.lang.Integer>> r9, boolean r10) {
        /*
            r8 = this;
            if (r9 == 0) goto L8b
            boolean r0 = r9.isEmpty()
            if (r0 == 0) goto La
            goto L8b
        La:
            r0 = 0
            if (r10 == 0) goto L12
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
        L12:
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            java.lang.String r2 = "DELETE FROM download_queue WHERE uid = ? AND type = ?"
            org.telegram.SQLite.SQLitePreparedStatement r1 = r1.executeFast(r2)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r2 = 0
            int r3 = r9.size()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
        L1f:
            if (r2 >= r3) goto L48
            java.lang.Object r4 = r9.get(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            android.util.Pair r4 = (android.util.Pair) r4     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r1.requery()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.Object r5 = r4.first     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.Long r5 = (java.lang.Long) r5     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            long r5 = r5.longValue()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r7 = 1
            r1.bindLong(r7, r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5 = 2
            java.lang.Object r4 = r4.second     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            int r4 = r4.intValue()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r1.bindInteger(r5, r4)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r1.step()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            int r2 = r2 + 1
            goto L1f
        L48:
            r1.dispose()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r10 == 0) goto L52
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1.commitTransaction()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
        L52:
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda141 r1 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda141     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            r1.<init>()     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r1)     // Catch: java.lang.Throwable -> L67 java.lang.Exception -> L69
            if (r10 == 0) goto L7b
            org.telegram.SQLite.SQLiteDatabase r9 = r8.database
            if (r9 == 0) goto L7b
            goto L78
        L61:
            r9 = move-exception
            r0 = r1
            goto L7c
        L64:
            r9 = move-exception
            r0 = r1
            goto L6a
        L67:
            r9 = move-exception
            goto L7c
        L69:
            r9 = move-exception
        L6a:
            r8.checkSQLException(r9)     // Catch: java.lang.Throwable -> L67
            if (r0 == 0) goto L72
            r0.dispose()
        L72:
            if (r10 == 0) goto L7b
            org.telegram.SQLite.SQLiteDatabase r9 = r8.database
            if (r9 == 0) goto L7b
        L78:
            r9.commitTransaction()
        L7b:
            return
        L7c:
            if (r0 == 0) goto L81
            r0.dispose()
        L81:
            if (r10 == 0) goto L8a
            org.telegram.SQLite.SQLiteDatabase r10 = r8.database
            if (r10 == 0) goto L8a
            r10.commitTransaction()
        L8a:
            throw r9
        L8b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.deleteFromDownloadQueue(java.util.ArrayList, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFromDownloadQueue$157(ArrayList arrayList) {
        getDownloadController().cancelDownloading(arrayList);
    }

    public void clearDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$clearDownloadQueue$158(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearDownloadQueue$158(int i) {
        try {
            if (i == 0) {
                this.database.executeFast("DELETE FROM download_queue WHERE 1").stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE type = %d", Integer.valueOf(i))).stepThis().dispose();
            }
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDownloadQueue$160(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDownloadQueue$160(final int i) {
        int i2;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                final ArrayList arrayList = new ArrayList();
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, type, data, parent FROM download_queue WHERE type = %d ORDER BY date DESC LIMIT 3", Integer.valueOf(i)), new Object[0]);
                while (queryFinalized.next()) {
                    try {
                        DownloadObject downloadObject = new DownloadObject();
                        downloadObject.type = queryFinalized.intValue(1);
                        downloadObject.id = queryFinalized.longValue(0);
                        downloadObject.parent = queryFinalized.stringValue(3);
                        NativeByteBuffer byteBufferValue = queryFinalized.byteBufferValue(2);
                        if (byteBufferValue != null) {
                            TLRPC$MessageMedia TLdeserialize = TLRPC$MessageMedia.TLdeserialize(byteBufferValue, byteBufferValue.readInt32(false), false);
                            byteBufferValue.reuse();
                            TLRPC$Document tLRPC$Document = TLdeserialize.document;
                            if (tLRPC$Document != null) {
                                downloadObject.object = tLRPC$Document;
                                downloadObject.secret = MessageObject.isVideoDocument(tLRPC$Document) && (i2 = TLdeserialize.ttl_seconds) > 0 && i2 <= 60;
                            } else {
                                TLRPC$Photo tLRPC$Photo = TLdeserialize.photo;
                                if (tLRPC$Photo != null) {
                                    downloadObject.object = tLRPC$Photo;
                                    int i3 = TLdeserialize.ttl_seconds;
                                    downloadObject.secret = i3 > 0 && i3 <= 60;
                                }
                            }
                            downloadObject.forceCache = (TLdeserialize.flags & LinearLayoutManager.INVALID_OFFSET) != 0;
                        }
                        arrayList.add(downloadObject);
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = queryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = queryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                }
                queryFinalized.dispose();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda59
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.this.lambda$getDownloadQueue$159(i, arrayList);
                    }
                });
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDownloadQueue$159(int i, ArrayList arrayList) {
        getDownloadController().processDownloadObjects(i, arrayList);
    }

    private int getMessageMediaType(TLRPC$Message tLRPC$Message) {
        if (tLRPC$Message instanceof TLRPC$TL_message_secret) {
            if (!(tLRPC$Message.media instanceof TLRPC$TL_messageMediaPhoto) && !MessageObject.isGifMessage(tLRPC$Message) && !MessageObject.isVoiceMessage(tLRPC$Message) && !MessageObject.isVideoMessage(tLRPC$Message) && !MessageObject.isRoundVideoMessage(tLRPC$Message)) {
                return -1;
            }
            int i = tLRPC$Message.ttl;
            return (i <= 0 || i > 60) ? 0 : 1;
        }
        if (tLRPC$Message instanceof TLRPC$TL_message) {
            TLRPC$MessageMedia tLRPC$MessageMedia = tLRPC$Message.media;
            if (((tLRPC$MessageMedia instanceof TLRPC$TL_messageMediaPhoto) || (tLRPC$MessageMedia instanceof TLRPC$TL_messageMediaDocument)) && tLRPC$MessageMedia.ttl_seconds != 0) {
                return 1;
            }
        }
        return ((tLRPC$Message.media instanceof TLRPC$TL_messageMediaPhoto) || MessageObject.isVideoMessage(tLRPC$Message)) ? 0 : -1;
    }

    public void putWebPages(final LongSparseArray<TLRPC$WebPage> longSparseArray) {
        if (isEmpty(longSparseArray)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda129
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putWebPages$162(longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putWebPages$162(androidx.collection.LongSparseArray r18) {
        /*
            Method dump skipped, instructions count: 466
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putWebPages$162(androidx.collection.LongSparseArray):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putWebPages$161(ArrayList arrayList) {
        getNotificationCenter().postNotificationName(NotificationCenter.didReceivedWebpages, arrayList);
    }

    public void overwriteChannel(final long j, final TLRPC$TL_updates_channelDifferenceTooLong tLRPC$TL_updates_channelDifferenceTooLong, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda86
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$overwriteChannel$164(j, i, tLRPC$TL_updates_channelDifferenceTooLong);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x026c A[Catch: all -> 0x027e, Exception -> 0x0281, TRY_LEAVE, TryCatch #4 {Exception -> 0x0281, all -> 0x027e, blocks: (B:3:0x000a, B:11:0x003c, B:14:0x022c, B:17:0x025a, B:18:0x0262, B:20:0x026c), top: B:2:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x028f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$overwriteChannel$164(long r20, int r22, final org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong r23) {
        /*
            Method dump skipped, instructions count: 659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$overwriteChannel$164(long, int, org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$overwriteChannel$163(long j, TLRPC$TL_updates_channelDifferenceTooLong tLRPC$TL_updates_channelDifferenceTooLong) {
        getNotificationCenter().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(j), Boolean.TRUE, tLRPC$TL_updates_channelDifferenceTooLong);
    }

    public void putChannelViews(final LongSparseArray<SparseIntArray> longSparseArray, final LongSparseArray<SparseIntArray> longSparseArray2, final LongSparseArray<SparseArray<TLRPC$MessageReplies>> longSparseArray3, final boolean z) {
        if (isEmpty(longSparseArray) && isEmpty(longSparseArray2) && isEmpty(longSparseArray3)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda132
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putChannelViews$165(longSparseArray, longSparseArray2, longSparseArray3, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0136 A[Catch: all -> 0x0215, Exception -> 0x0219, TRY_LEAVE, TryCatch #12 {Exception -> 0x0219, all -> 0x0215, blocks: (B:51:0x00d0, B:53:0x00d6, B:55:0x00e7, B:58:0x012b, B:63:0x0136, B:103:0x01d8), top: B:50:0x00d0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putChannelViews$165(androidx.collection.LongSparseArray r23, androidx.collection.LongSparseArray r24, androidx.collection.LongSparseArray r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 603
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putChannelViews$165(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /* renamed from: updateRepliesMaxReadIdInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$updateRepliesMaxReadId$167(final long r21, final int r23, final int r24, int r25) {
        /*
            Method dump skipped, instructions count: 656
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateRepliesMaxReadId$167(long, int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRepliesMaxReadIdInternal$166(long j, int i, int i2, int i3, int i4) {
        getMessagesController().getTopicsController().updateMaxReadId(j, i, i2, i3, i4);
    }

    private void resetForumBadgeIfNeed(long j) {
        Locale locale;
        SQLiteCursor queryFinalized;
        LongSparseIntArray longSparseIntArray;
        SQLiteCursor sQLiteCursor = null;
        try {
            SQLiteDatabase sQLiteDatabase = this.database;
            locale = Locale.ENGLISH;
            queryFinalized = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT topic_id FROM topics WHERE did = %d AND unread_count > 0", Long.valueOf(j)), new Object[0]);
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (queryFinalized.next()) {
                longSparseIntArray = null;
            } else {
                longSparseIntArray = new LongSparseIntArray();
                longSparseIntArray.put(j, 0);
            }
            queryFinalized.dispose();
            if (longSparseIntArray != null) {
                this.database.executeFast(String.format(locale, "UPDATE dialogs SET unread_count = 0, unread_count_i = 0 WHERE did = %d", Long.valueOf(j))).stepThis().dispose();
            }
            updateFiltersReadCounter(longSparseIntArray, null, true);
            getMessagesController().processDialogsUpdateRead(longSparseIntArray, null);
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = queryFinalized;
            try {
                checkSQLException(th);
            } finally {
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                }
            }
        }
    }

    public void updateRepliesMaxReadId(final long j, final int i, final int i2, final int i3, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda76
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$updateRepliesMaxReadId$167(j, i, i2, i3);
                }
            });
        } else {
            lambda$updateRepliesMaxReadId$167(j, i, i2, i3);
        }
    }

    public void updateRepliesCount(final long j, final int i, final ArrayList<TLRPC$Peer> arrayList, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateRepliesCount$168(i, j, i3, arrayList, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateRepliesCount$168(int r16, long r17, int r19, java.util.ArrayList r20, int r21) {
        /*
            r15 = this;
            r1 = r15
            r0 = r20
            r2 = r21
            r3 = 0
            org.telegram.SQLite.SQLiteDatabase r4 = r1.database     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9b
            java.lang.String r5 = "UPDATE messages_v2 SET replies_data = ? WHERE mid = ? AND uid = ?"
            org.telegram.SQLite.SQLitePreparedStatement r4 = r4.executeFast(r5)     // Catch: java.lang.Throwable -> L98 java.lang.Exception -> L9b
            org.telegram.SQLite.SQLiteDatabase r5 = r1.database     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            java.util.Locale r6 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            java.lang.String r7 = "SELECT replies_data FROM messages_v2 WHERE mid = %d AND uid = %d"
            r8 = 2
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            java.lang.Integer r10 = java.lang.Integer.valueOf(r16)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r11 = 0
            r9[r11] = r10     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r12 = r17
            long r12 = -r12
            java.lang.Long r10 = java.lang.Long.valueOf(r12)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r14 = 1
            r9[r14] = r10     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            java.lang.String r6 = java.lang.String.format(r6, r7, r9)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            java.lang.Object[] r7 = new java.lang.Object[r11]     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            org.telegram.SQLite.SQLiteCursor r5 = r5.queryFinalized(r6, r7)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            boolean r6 = r5.next()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            if (r6 == 0) goto L4a
            org.telegram.tgnet.NativeByteBuffer r6 = r5.byteBufferValue(r11)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            if (r6 == 0) goto L4a
            int r7 = r6.readInt32(r11)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            org.telegram.tgnet.TLRPC$MessageReplies r7 = org.telegram.tgnet.TLRPC$MessageReplies.TLdeserialize(r6, r7, r11)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            r6.reuse()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            goto L4b
        L4a:
            r7 = r3
        L4b:
            r5.dispose()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8e
            if (r7 == 0) goto L88
            int r5 = r7.replies     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            int r5 = r5 + r19
            r7.replies = r5     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            if (r5 >= 0) goto L5a
            r7.replies = r11     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
        L5a:
            if (r0 == 0) goto L63
            r7.recent_repliers = r0     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            int r0 = r7.flags     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r0 = r0 | r8
            r7.flags = r0     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
        L63:
            if (r2 == 0) goto L67
            r7.max_id = r2     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
        L67:
            r4.requery()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            org.telegram.tgnet.NativeByteBuffer r0 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            int r2 = r7.getObjectSize()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r7.serializeToStream(r0)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r4.bindByteBuffer(r14, r0)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r2 = r16
            r4.bindInteger(r8, r2)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r2 = 3
            r4.bindLong(r2, r12)     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r4.step()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            r0.reuse()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
        L88:
            r4.dispose()     // Catch: java.lang.Throwable -> L90 java.lang.Exception -> L94
            goto Laa
        L8c:
            r0 = move-exception
            goto L92
        L8e:
            r0 = move-exception
            goto L96
        L90:
            r0 = move-exception
            r5 = r3
        L92:
            r3 = r4
            goto Lac
        L94:
            r0 = move-exception
            r5 = r3
        L96:
            r3 = r4
            goto L9d
        L98:
            r0 = move-exception
            r5 = r3
            goto Lac
        L9b:
            r0 = move-exception
            r5 = r3
        L9d:
            r15.checkSQLException(r0)     // Catch: java.lang.Throwable -> Lab
            if (r3 == 0) goto La5
            r3.dispose()
        La5:
            if (r5 == 0) goto Laa
            r5.dispose()
        Laa:
            return
        Lab:
            r0 = move-exception
        Lac:
            if (r3 == 0) goto Lb1
            r3.dispose()
        Lb1:
            if (r5 == 0) goto Lb6
            r5.dispose()
        Lb6:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateRepliesCount$168(int, long, int, java.util.ArrayList, int):void");
    }

    private boolean isValidKeyboardToSave(TLRPC$Message tLRPC$Message) {
        TLRPC$ReplyMarkup tLRPC$ReplyMarkup = tLRPC$Message.reply_markup;
        return (tLRPC$ReplyMarkup == null || (tLRPC$ReplyMarkup instanceof TLRPC$TL_replyInlineMarkup) || (tLRPC$ReplyMarkup.selective && !tLRPC$Message.mentioned)) ? false : true;
    }

    public void updateMessageVerifyFlags(final ArrayList<TLRPC$Message> arrayList) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda148
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateMessageVerifyFlags$169(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVerifyFlags$169(ArrayList arrayList) {
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        boolean z = false;
        try {
            try {
                this.database.beginTransaction();
                try {
                    SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE messages_v2 SET imp = ? WHERE mid = ? AND uid = ?");
                    try {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            TLRPC$Message tLRPC$Message = (TLRPC$Message) arrayList.get(i);
                            executeFast.requery();
                            int i2 = tLRPC$Message.stickerVerified;
                            executeFast.bindInteger(1, i2 == 0 ? 1 : i2 == 2 ? 2 : 0);
                            executeFast.bindInteger(2, tLRPC$Message.id);
                            executeFast.bindLong(3, MessageObject.getDialogId(tLRPC$Message));
                            executeFast.step();
                        }
                        executeFast.dispose();
                        this.database.commitTransaction();
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        z = true;
                        checkSQLException(e);
                        if (z && (sQLiteDatabase2 = this.database) != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        z = true;
                        if (z && (sQLiteDatabase = this.database) != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(18:503|(1:505)(1:883)|506|(7:(4:(7:(3:874|875|(28:879|510|(2:(1:515)|516)|517|(2:(1:520)|521)|(4:523|(2:525|(4:527|(1:529)|(1:534)|(3:536|(2:538|(3:540|(1:542)|(1:547)))(1:869)|868)(1:870)))(1:872)|871|(0)(0))(1:873)|548|(5:551|(1:553)(1:668)|(27:(1:562)(1:667)|563|564|(3:566|567|568)(1:657)|569|570|(3:572|573|(20:575|576|577|(2:579|580)(2:653|654)|581|(1:583)(2:649|(1:651)(1:652))|584|(1:586)(1:648)|587|(1:589)(2:646|647)|590|(4:592|593|(1:595)(1:643)|596)(2:644|645)|597|598|599|(3:601|602|603)(2:636|637)|(3:605|606|(3:608|609|610)(1:629))(1:630)|611|(1:613)|(2:615|616)(1:617)))(1:656)|655|576|577|(0)(0)|581|(0)(0)|584|(0)(0)|587|(0)(0)|590|(0)(0)|597|598|599|(0)(0)|(0)(0)|611|(0)|(0)(0))(2:556|557)|558|549)|669|670|671|672|673|(5:848|849|850|851|852)(1:675)|676|677|(4:679|680|681|(3:(1:684)(1:687)|685|686))(1:840)|688|(1:832)(3:(2:693|694)(1:831)|695|696)|697|698|(4:(2:701|702)(1:799)|703|704|705)(3:800|(5:804|805|806|807|808)(1:802)|803)|706|707|(5:709|710|(2:780|781)|712|(2:716|(3:722|(2:724|725)(5:752|(5:757|(1:(1:765)(1:(1:774)(3:769|(1:771)(1:773)|772)))|775|776|777)|778|779|777)|(11:727|(1:729)|730|731|732|733|(1:735)(1:740)|736|737|738|739))))|788|738|739))(1:508)|706|707|(0)|788|738|739)|697|698|(0)(0))|676|677|(0)(0)|688|(1:690)|832)|509|510|(3:512|(0)|516)|517|(0)|(0)(0)|548|(1:549)|669|670|671|672|673|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:530:0x0b1f, code lost:
    
        if (r5.id <= r8) goto L426;
     */
    /* JADX WARN: Code restructure failed: missing block: B:543:0x0b51, code lost:
    
        if (r5.id <= r2) goto L441;
     */
    /* JADX WARN: Code restructure failed: missing block: B:782:0x0e94, code lost:
    
        if (r5.post != false) goto L651;
     */
    /* JADX WARN: Code restructure failed: missing block: B:864:0x107c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:866:0x1073, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x1aa4  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x1aa9  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x1aae  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x1ab3  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x1ab8  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x1abd  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x1ac2  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x1ac7  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x1acc  */
    /* JADX WARN: Removed duplicated region for block: B:119:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x03af A[Catch: all -> 0x0578, Exception -> 0x0586, TryCatch #78 {Exception -> 0x0586, all -> 0x0578, blocks: (B:176:0x025e, B:178:0x027b, B:180:0x027f, B:182:0x028c, B:183:0x029d, B:185:0x02a6, B:187:0x02d0, B:188:0x02d8, B:189:0x02e6, B:191:0x02ec, B:193:0x02f2, B:196:0x02f8, B:198:0x02fc, B:200:0x0302, B:203:0x030c, B:205:0x0315, B:207:0x0337, B:208:0x033d, B:210:0x0345, B:216:0x03af, B:221:0x03c6, B:223:0x03f2, B:224:0x03f8, B:225:0x0408, B:227:0x040c, B:229:0x0410, B:231:0x0414, B:233:0x041e, B:234:0x0423, B:235:0x0433, B:237:0x0437, B:239:0x0441, B:240:0x0446, B:241:0x0467, B:244:0x046f, B:245:0x048b, B:247:0x0495, B:248:0x04a6, B:250:0x04ac, B:251:0x04af, B:253:0x04bc, B:254:0x04c9, B:256:0x04da, B:257:0x04e4, B:259:0x04ed, B:261:0x04fd, B:262:0x0505, B:264:0x050b, B:265:0x050e, B:267:0x051b, B:268:0x0523, B:270:0x053f, B:272:0x0545, B:274:0x0556, B:278:0x055c, B:291:0x03bf, B:293:0x036f, B:295:0x0379, B:296:0x0383, B:298:0x038b, B:299:0x038e, B:301:0x039c, B:302:0x03a6, B:316:0x05a6, B:317:0x05ae, B:319:0x05b4, B:322:0x05ca, B:324:0x05d3, B:325:0x062e, B:327:0x0634, B:329:0x0643, B:334:0x064f, B:335:0x0657, B:337:0x065f, B:340:0x066a, B:343:0x066f, B:345:0x0674, B:346:0x0679, B:348:0x0680, B:350:0x0698, B:353:0x06b3, B:355:0x06ba, B:357:0x06c1, B:359:0x06c9, B:362:0x06e1, B:366:0x06d5, B:369:0x06a6, B:377:0x0739, B:378:0x077b, B:380:0x0781, B:382:0x079b, B:387:0x07a8, B:388:0x07ad, B:390:0x07b8, B:392:0x07c5, B:396:0x07cc, B:398:0x07dd, B:400:0x07f9, B:403:0x0813, B:405:0x081e, B:407:0x0825, B:409:0x082d, B:413:0x084c, B:416:0x0837, B:419:0x0844, B:422:0x0802, B:424:0x080e, B:431:0x086d, B:433:0x0874, B:434:0x08ba, B:436:0x08c0, B:438:0x08ca, B:440:0x08cf, B:445:0x08d4, B:448:0x08e1, B:450:0x08eb, B:453:0x08f5, B:462:0x0918, B:463:0x0920, B:465:0x0926, B:466:0x096b, B:468:0x0971, B:470:0x097e, B:475:0x09cd, B:477:0x09d3, B:478:0x09e3, B:480:0x09e9, B:482:0x09f3, B:484:0x09f8, B:487:0x09fe), top: B:175:0x025e }] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x082d A[Catch: all -> 0x0578, Exception -> 0x0586, TryCatch #78 {Exception -> 0x0586, all -> 0x0578, blocks: (B:176:0x025e, B:178:0x027b, B:180:0x027f, B:182:0x028c, B:183:0x029d, B:185:0x02a6, B:187:0x02d0, B:188:0x02d8, B:189:0x02e6, B:191:0x02ec, B:193:0x02f2, B:196:0x02f8, B:198:0x02fc, B:200:0x0302, B:203:0x030c, B:205:0x0315, B:207:0x0337, B:208:0x033d, B:210:0x0345, B:216:0x03af, B:221:0x03c6, B:223:0x03f2, B:224:0x03f8, B:225:0x0408, B:227:0x040c, B:229:0x0410, B:231:0x0414, B:233:0x041e, B:234:0x0423, B:235:0x0433, B:237:0x0437, B:239:0x0441, B:240:0x0446, B:241:0x0467, B:244:0x046f, B:245:0x048b, B:247:0x0495, B:248:0x04a6, B:250:0x04ac, B:251:0x04af, B:253:0x04bc, B:254:0x04c9, B:256:0x04da, B:257:0x04e4, B:259:0x04ed, B:261:0x04fd, B:262:0x0505, B:264:0x050b, B:265:0x050e, B:267:0x051b, B:268:0x0523, B:270:0x053f, B:272:0x0545, B:274:0x0556, B:278:0x055c, B:291:0x03bf, B:293:0x036f, B:295:0x0379, B:296:0x0383, B:298:0x038b, B:299:0x038e, B:301:0x039c, B:302:0x03a6, B:316:0x05a6, B:317:0x05ae, B:319:0x05b4, B:322:0x05ca, B:324:0x05d3, B:325:0x062e, B:327:0x0634, B:329:0x0643, B:334:0x064f, B:335:0x0657, B:337:0x065f, B:340:0x066a, B:343:0x066f, B:345:0x0674, B:346:0x0679, B:348:0x0680, B:350:0x0698, B:353:0x06b3, B:355:0x06ba, B:357:0x06c1, B:359:0x06c9, B:362:0x06e1, B:366:0x06d5, B:369:0x06a6, B:377:0x0739, B:378:0x077b, B:380:0x0781, B:382:0x079b, B:387:0x07a8, B:388:0x07ad, B:390:0x07b8, B:392:0x07c5, B:396:0x07cc, B:398:0x07dd, B:400:0x07f9, B:403:0x0813, B:405:0x081e, B:407:0x0825, B:409:0x082d, B:413:0x084c, B:416:0x0837, B:419:0x0844, B:422:0x0802, B:424:0x080e, B:431:0x086d, B:433:0x0874, B:434:0x08ba, B:436:0x08c0, B:438:0x08ca, B:440:0x08cf, B:445:0x08d4, B:448:0x08e1, B:450:0x08eb, B:453:0x08f5, B:462:0x0918, B:463:0x0920, B:465:0x0926, B:466:0x096b, B:468:0x0971, B:470:0x097e, B:475:0x09cd, B:477:0x09d3, B:478:0x09e3, B:480:0x09e9, B:482:0x09f3, B:484:0x09f8, B:487:0x09fe), top: B:175:0x025e }] */
    /* JADX WARN: Removed duplicated region for block: B:412:0x084b  */
    /* JADX WARN: Removed duplicated region for block: B:416:0x0837 A[Catch: all -> 0x0578, Exception -> 0x0586, TryCatch #78 {Exception -> 0x0586, all -> 0x0578, blocks: (B:176:0x025e, B:178:0x027b, B:180:0x027f, B:182:0x028c, B:183:0x029d, B:185:0x02a6, B:187:0x02d0, B:188:0x02d8, B:189:0x02e6, B:191:0x02ec, B:193:0x02f2, B:196:0x02f8, B:198:0x02fc, B:200:0x0302, B:203:0x030c, B:205:0x0315, B:207:0x0337, B:208:0x033d, B:210:0x0345, B:216:0x03af, B:221:0x03c6, B:223:0x03f2, B:224:0x03f8, B:225:0x0408, B:227:0x040c, B:229:0x0410, B:231:0x0414, B:233:0x041e, B:234:0x0423, B:235:0x0433, B:237:0x0437, B:239:0x0441, B:240:0x0446, B:241:0x0467, B:244:0x046f, B:245:0x048b, B:247:0x0495, B:248:0x04a6, B:250:0x04ac, B:251:0x04af, B:253:0x04bc, B:254:0x04c9, B:256:0x04da, B:257:0x04e4, B:259:0x04ed, B:261:0x04fd, B:262:0x0505, B:264:0x050b, B:265:0x050e, B:267:0x051b, B:268:0x0523, B:270:0x053f, B:272:0x0545, B:274:0x0556, B:278:0x055c, B:291:0x03bf, B:293:0x036f, B:295:0x0379, B:296:0x0383, B:298:0x038b, B:299:0x038e, B:301:0x039c, B:302:0x03a6, B:316:0x05a6, B:317:0x05ae, B:319:0x05b4, B:322:0x05ca, B:324:0x05d3, B:325:0x062e, B:327:0x0634, B:329:0x0643, B:334:0x064f, B:335:0x0657, B:337:0x065f, B:340:0x066a, B:343:0x066f, B:345:0x0674, B:346:0x0679, B:348:0x0680, B:350:0x0698, B:353:0x06b3, B:355:0x06ba, B:357:0x06c1, B:359:0x06c9, B:362:0x06e1, B:366:0x06d5, B:369:0x06a6, B:377:0x0739, B:378:0x077b, B:380:0x0781, B:382:0x079b, B:387:0x07a8, B:388:0x07ad, B:390:0x07b8, B:392:0x07c5, B:396:0x07cc, B:398:0x07dd, B:400:0x07f9, B:403:0x0813, B:405:0x081e, B:407:0x0825, B:409:0x082d, B:413:0x084c, B:416:0x0837, B:419:0x0844, B:422:0x0802, B:424:0x080e, B:431:0x086d, B:433:0x0874, B:434:0x08ba, B:436:0x08c0, B:438:0x08ca, B:440:0x08cf, B:445:0x08d4, B:448:0x08e1, B:450:0x08eb, B:453:0x08f5, B:462:0x0918, B:463:0x0920, B:465:0x0926, B:466:0x096b, B:468:0x0971, B:470:0x097e, B:475:0x09cd, B:477:0x09d3, B:478:0x09e3, B:480:0x09e9, B:482:0x09f3, B:484:0x09f8, B:487:0x09fe), top: B:175:0x025e }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x1813  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x0ae9 A[Catch: all -> 0x0ac8, Exception -> 0x0ad1, TryCatch #68 {Exception -> 0x0ad1, all -> 0x0ac8, blocks: (B:875:0x0aba, B:877:0x0ac2, B:512:0x0ae1, B:515:0x0ae9, B:516:0x0aef, B:520:0x0afa, B:521:0x0aff, B:523:0x0b04, B:525:0x0b11, B:527:0x0b19, B:529:0x0b1d, B:532:0x0b23, B:536:0x0b31, B:538:0x0b41, B:540:0x0b4b, B:542:0x0b4f, B:545:0x0b55, B:567:0x0bbf, B:573:0x0bed, B:580:0x0c09, B:589:0x0c40, B:593:0x0c64, B:596:0x0c6b, B:643:0x0c69, B:868:0x0b5e, B:871:0x0b2a), top: B:874:0x0aba }] */
    /* JADX WARN: Removed duplicated region for block: B:519:0x0af8  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0b04 A[Catch: all -> 0x0ac8, Exception -> 0x0ad1, TryCatch #68 {Exception -> 0x0ad1, all -> 0x0ac8, blocks: (B:875:0x0aba, B:877:0x0ac2, B:512:0x0ae1, B:515:0x0ae9, B:516:0x0aef, B:520:0x0afa, B:521:0x0aff, B:523:0x0b04, B:525:0x0b11, B:527:0x0b19, B:529:0x0b1d, B:532:0x0b23, B:536:0x0b31, B:538:0x0b41, B:540:0x0b4b, B:542:0x0b4f, B:545:0x0b55, B:567:0x0bbf, B:573:0x0bed, B:580:0x0c09, B:589:0x0c40, B:593:0x0c64, B:596:0x0c6b, B:643:0x0c69, B:868:0x0b5e, B:871:0x0b2a), top: B:874:0x0aba }] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x1818  */
    /* JADX WARN: Removed duplicated region for block: B:536:0x0b31 A[Catch: all -> 0x0ac8, Exception -> 0x0ad1, TryCatch #68 {Exception -> 0x0ad1, all -> 0x0ac8, blocks: (B:875:0x0aba, B:877:0x0ac2, B:512:0x0ae1, B:515:0x0ae9, B:516:0x0aef, B:520:0x0afa, B:521:0x0aff, B:523:0x0b04, B:525:0x0b11, B:527:0x0b19, B:529:0x0b1d, B:532:0x0b23, B:536:0x0b31, B:538:0x0b41, B:540:0x0b4b, B:542:0x0b4f, B:545:0x0b55, B:567:0x0bbf, B:573:0x0bed, B:580:0x0c09, B:589:0x0c40, B:593:0x0c64, B:596:0x0c6b, B:643:0x0c69, B:868:0x0b5e, B:871:0x0b2a), top: B:874:0x0aba }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x181d  */
    /* JADX WARN: Removed duplicated region for block: B:551:0x0b75  */
    /* JADX WARN: Removed duplicated region for block: B:579:0x0c07  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0c1c  */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0c2f  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x0c40 A[Catch: all -> 0x0ac8, Exception -> 0x0ad1, TRY_ENTER, TRY_LEAVE, TryCatch #68 {Exception -> 0x0ad1, all -> 0x0ac8, blocks: (B:875:0x0aba, B:877:0x0ac2, B:512:0x0ae1, B:515:0x0ae9, B:516:0x0aef, B:520:0x0afa, B:521:0x0aff, B:523:0x0b04, B:525:0x0b11, B:527:0x0b19, B:529:0x0b1d, B:532:0x0b23, B:536:0x0b31, B:538:0x0b41, B:540:0x0b4b, B:542:0x0b4f, B:545:0x0b55, B:567:0x0bbf, B:573:0x0bed, B:580:0x0c09, B:589:0x0c40, B:593:0x0c64, B:596:0x0c6b, B:643:0x0c69, B:868:0x0b5e, B:871:0x0b2a), top: B:874:0x0aba }] */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0c60  */
    /* JADX WARN: Removed duplicated region for block: B:601:0x0c8a  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x0c9b A[Catch: all -> 0x0cd2, Exception -> 0x0cd6, TRY_LEAVE, TryCatch #69 {Exception -> 0x0cd6, all -> 0x0cd2, blocks: (B:599:0x0c7d, B:605:0x0c9b, B:637:0x0c96), top: B:598:0x0c7d }] */
    /* JADX WARN: Removed duplicated region for block: B:613:0x0cb3 A[Catch: all -> 0x0ccc, Exception -> 0x0ccf, TryCatch #72 {Exception -> 0x0ccf, all -> 0x0ccc, blocks: (B:610:0x0ca2, B:611:0x0cae, B:613:0x0cb3, B:615:0x0cb8, B:629:0x0ca8), top: B:609:0x0ca2 }] */
    /* JADX WARN: Removed duplicated region for block: B:615:0x0cb8 A[Catch: all -> 0x0ccc, Exception -> 0x0ccf, TRY_LEAVE, TryCatch #72 {Exception -> 0x0ccf, all -> 0x0ccc, blocks: (B:610:0x0ca2, B:611:0x0cae, B:613:0x0cb3, B:615:0x0cb8, B:629:0x0ca8), top: B:609:0x0ca2 }] */
    /* JADX WARN: Removed duplicated region for block: B:617:0x0cbb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:630:0x0cad  */
    /* JADX WARN: Removed duplicated region for block: B:636:0x0c94  */
    /* JADX WARN: Removed duplicated region for block: B:644:0x0c6f  */
    /* JADX WARN: Removed duplicated region for block: B:646:0x0c56  */
    /* JADX WARN: Removed duplicated region for block: B:648:0x0c31  */
    /* JADX WARN: Removed duplicated region for block: B:649:0x0c1e  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x0c0f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x1a58  */
    /* JADX WARN: Removed duplicated region for block: B:675:0x0d35  */
    /* JADX WARN: Removed duplicated region for block: B:679:0x0d3d A[Catch: all -> 0x0d15, Exception -> 0x0d1a, TRY_ENTER, TRY_LEAVE, TryCatch #62 {Exception -> 0x0d1a, all -> 0x0d15, blocks: (B:852:0x0d04, B:679:0x0d3d, B:684:0x0d6c, B:685:0x0d77, B:690:0x0dc5, B:693:0x0dcb), top: B:851:0x0d04 }] */
    /* JADX WARN: Removed duplicated region for block: B:700:0x0e11  */
    /* JADX WARN: Removed duplicated region for block: B:709:0x0e88 A[Catch: all -> 0x103d, Exception -> 0x1046, TRY_LEAVE, TryCatch #107 {Exception -> 0x1046, all -> 0x103d, blocks: (B:707:0x0e83, B:709:0x0e88, B:712:0x0e9d, B:714:0x0eab, B:716:0x0eb6, B:722:0x0ec4, B:727:0x0f80, B:730:0x0f8e, B:752:0x0ee6, B:779:0x0f69), top: B:706:0x0e83 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x1a61  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x1a66  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x1a6b  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x1a70  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x1a75  */
    /* JADX WARN: Removed duplicated region for block: B:800:0x0e4a A[Catch: all -> 0x104f, Exception -> 0x1058, TRY_ENTER, TRY_LEAVE, TryCatch #98 {Exception -> 0x1058, all -> 0x104f, blocks: (B:698:0x0e0b, B:800:0x0e4a), top: B:697:0x0e0b }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x1a7a  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x1a7f  */
    /* JADX WARN: Removed duplicated region for block: B:840:0x0dc0  */
    /* JADX WARN: Removed duplicated region for block: B:848:0x0cfc A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x1a84  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x1a89  */
    /* JADX WARN: Removed duplicated region for block: B:870:0x0b62  */
    /* JADX WARN: Removed duplicated region for block: B:873:0x0b69  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x1a8e  */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x1a96  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x1a9f  */
    /* renamed from: putMessagesInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$putMessages$173(java.util.ArrayList<org.telegram.tgnet.TLRPC$Message> r55, boolean r56, boolean r57, int r58, boolean r59, boolean r60, int r61) {
        /*
            Method dump skipped, instructions count: 6864
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putMessages$173(java.util.ArrayList, boolean, boolean, int, boolean, boolean, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessagesInternal$170(int i) {
        getDownloadController().newDownloadObjectsAvailable(i);
    }

    private void createOrEditTopic(final long j, TLRPC$Message tLRPC$Message) {
        final TLRPC$TL_forumTopic tLRPC$TL_forumTopic = new TLRPC$TL_forumTopic();
        tLRPC$TL_forumTopic.topicStartMessage = tLRPC$Message;
        tLRPC$TL_forumTopic.top_message = tLRPC$Message.id;
        tLRPC$TL_forumTopic.topMessage = tLRPC$Message;
        tLRPC$TL_forumTopic.from_id = getMessagesController().getPeer(getUserConfig().clientUserId);
        tLRPC$TL_forumTopic.notify_settings = new TLRPC$TL_peerNotifySettings();
        tLRPC$TL_forumTopic.unread_count = 0;
        TLRPC$MessageAction tLRPC$MessageAction = tLRPC$Message.action;
        if (tLRPC$MessageAction instanceof TLRPC$TL_messageActionTopicCreate) {
            TLRPC$TL_messageActionTopicCreate tLRPC$TL_messageActionTopicCreate = (TLRPC$TL_messageActionTopicCreate) tLRPC$MessageAction;
            tLRPC$TL_forumTopic.id = tLRPC$Message.id;
            long j2 = tLRPC$TL_messageActionTopicCreate.icon_emoji_id;
            tLRPC$TL_forumTopic.icon_emoji_id = j2;
            tLRPC$TL_forumTopic.title = tLRPC$TL_messageActionTopicCreate.title;
            tLRPC$TL_forumTopic.icon_color = tLRPC$TL_messageActionTopicCreate.icon_color;
            if (j2 != 0) {
                tLRPC$TL_forumTopic.flags |= 1;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(tLRPC$TL_forumTopic);
            saveTopics(j, arrayList, false, false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda118
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$createOrEditTopic$171(j, tLRPC$TL_forumTopic);
                }
            });
            return;
        }
        if (tLRPC$MessageAction instanceof TLRPC$TL_messageActionTopicEdit) {
            TLRPC$TL_messageActionTopicEdit tLRPC$TL_messageActionTopicEdit = (TLRPC$TL_messageActionTopicEdit) tLRPC$MessageAction;
            tLRPC$TL_forumTopic.id = MessageObject.getTopicId(tLRPC$Message, true);
            tLRPC$TL_forumTopic.icon_emoji_id = tLRPC$TL_messageActionTopicEdit.icon_emoji_id;
            tLRPC$TL_forumTopic.title = tLRPC$TL_messageActionTopicEdit.title;
            tLRPC$TL_forumTopic.closed = tLRPC$TL_messageActionTopicEdit.closed;
            tLRPC$TL_forumTopic.hidden = tLRPC$TL_messageActionTopicEdit.hidden;
            int i = tLRPC$TL_messageActionTopicEdit.flags;
            int i2 = (i & 1) != 0 ? 1 : 0;
            if ((i & 2) != 0) {
                i2 += 2;
            }
            if ((i & 4) != 0) {
                i2 += 8;
            }
            if ((i & 8) != 0) {
                i2 += 32;
            }
            final int i3 = i2;
            updateTopicData(j, tLRPC$TL_forumTopic, i3);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda119
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$createOrEditTopic$172(j, tLRPC$TL_forumTopic, i3);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOrEditTopic$171(long j, TLRPC$TL_forumTopic tLRPC$TL_forumTopic) {
        getMessagesController().getTopicsController().onTopicCreated(j, tLRPC$TL_forumTopic, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOrEditTopic$172(long j, TLRPC$TL_forumTopic tLRPC$TL_forumTopic, int i) {
        getMessagesController().getTopicsController().updateTopicInUi(j, tLRPC$TL_forumTopic, i);
    }

    public void putMessages(ArrayList<TLRPC$Message> arrayList, boolean z, boolean z2, boolean z3, int i, boolean z4, int i2) {
        putMessages(arrayList, z, z2, z3, i, false, z4, i2);
    }

    public void putMessages(final ArrayList<TLRPC$Message> arrayList, final boolean z, boolean z2, final boolean z3, final int i, final boolean z4, final boolean z5, final int i2) {
        if (arrayList.size() == 0) {
            return;
        }
        if (z2) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda162
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$putMessages$173(arrayList, z, z3, i, z4, z5, i2);
                }
            });
        } else {
            lambda$putMessages$173(arrayList, z, z3, i, z4, z5, i2);
        }
    }

    public void markMessageAsSendError(final TLRPC$Message tLRPC$Message, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda187
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$markMessageAsSendError$174(tLRPC$Message, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessageAsSendError$174(TLRPC$Message tLRPC$Message, boolean z) {
        try {
            long j = tLRPC$Message.id;
            if (z) {
                this.database.executeFast(String.format(Locale.US, "UPDATE scheduled_messages_v2 SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(tLRPC$Message)))).stepThis().dispose();
            } else {
                SQLiteDatabase sQLiteDatabase = this.database;
                Locale locale = Locale.US;
                sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(tLRPC$Message)))).stepThis().dispose();
                this.database.executeFast(String.format(locale, "UPDATE messages_topics SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(tLRPC$Message)))).stepThis().dispose();
            }
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void setMessageSeq(final int i, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setMessageSeq$175(i, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageSeq$175(int i, int i2, int i3) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("REPLACE INTO messages_seq VALUES(?, ?, ?)");
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindInteger(1, i);
                sQLitePreparedStatement.bindInteger(2, i2);
                sQLitePreparedStatement.bindInteger(3, i3);
                sQLitePreparedStatement.step();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement == null) {
                    return;
                }
            }
            sQLitePreparedStatement.dispose();
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(7:229|230|231|232|(3:234|235|236)(1:248)|237|(1:239))(1:3)|4|(13:(2:202|(18:204|205|206|207|208|209|210|211|20|(13:178|179|180|181|(2:183|184)(1:187)|185|(1:26)(1:177)|162|163|(2:165|166)|168|28|(1:30)(1:(4:70|(18:83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99|100)(3:72|73|74)|75|76)(10:33|(1:35)(1:65)|36|37|38|(1:40)|42|(1:44)|45|46)))(1:23)|24|(0)(0)|162|163|(0)|168|28|(0)(0)))|180|181|(0)(0)|185|(0)(0)|162|163|(0)|168|28|(0)(0)|(10:(0)|(1:172)|(1:80)|(1:105)|(1:128)|(0)|(0)|(0)|(0)|(0)))|6|(6:8|(1:10)(1:19)|11|12|13|14)|20|(0)|178|179) */
    /* JADX WARN: Can't wrap try/catch for region: R(14:83|84|85|86|87|88|(3:89|90|91)|(2:92|93)|94|(2:95|96)|97|98|99|100) */
    /* JADX WARN: Can't wrap try/catch for region: R(23:0|1|(7:229|230|231|232|(3:234|235|236)(1:248)|237|(1:239))(1:3)|4|(2:202|(18:204|205|206|207|208|209|210|211|20|(13:178|179|180|181|(2:183|184)(1:187)|185|(1:26)(1:177)|162|163|(2:165|166)|168|28|(1:30)(1:(4:70|(18:83|84|85|86|87|88|89|90|91|92|93|94|95|96|97|98|99|100)(3:72|73|74)|75|76)(10:33|(1:35)(1:65)|36|37|38|(1:40)|42|(1:44)|45|46)))(1:23)|24|(0)(0)|162|163|(0)|168|28|(0)(0)))|6|(6:8|(1:10)(1:19)|11|12|13|14)|20|(0)|178|179|180|181|(0)(0)|185|(0)(0)|162|163|(0)|168|28|(0)(0)|(10:(0)|(1:172)|(1:80)|(1:105)|(1:128)|(0)|(0)|(0)|(0)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x038b, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x038c, code lost:
    
        checkSQLException(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x038f, code lost:
    
        if (r5 == null) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0324, code lost:
    
        if (r3 != null) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x019a, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x019b, code lost:
    
        checkSQLException(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x019e, code lost:
    
        if (r8 == null) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0156, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0160, code lost:
    
        checkSQLException(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0163, code lost:
    
        if (r8 != null) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x0165, code lost:
    
        r8.dispose();
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x015d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x015e, code lost:
    
        r8 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0158, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0159, code lost:
    
        r8 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x009c, code lost:
    
        if (r10 == null) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x016c, code lost:
    
        if (r4 == 1) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x03f6, code lost:
    
        if (r5 != null) goto L216;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0374  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x018f A[Catch: all -> 0x0197, Exception -> 0x019a, TRY_LEAVE, TryCatch #10 {Exception -> 0x019a, blocks: (B:163:0x0170, B:165:0x018f), top: B:162:0x0170, outer: #32 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0149 A[Catch: Exception -> 0x0156, all -> 0x0415, TRY_LEAVE, TryCatch #24 {Exception -> 0x0156, blocks: (B:181:0x0143, B:183:0x0149), top: B:180:0x0143 }] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0122 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0058 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01a9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x00b5  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v10, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r10v25 */
    /* JADX WARN: Type inference failed for: r10v26 */
    /* JADX WARN: Type inference failed for: r10v27 */
    /* JADX WARN: Type inference failed for: r10v29 */
    /* JADX WARN: Type inference failed for: r10v6, types: [long] */
    /* JADX WARN: Type inference failed for: r10v7 */
    /* renamed from: updateMessageStateAndIdInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long[] lambda$updateMessageStateAndId$177(long r20, long r22, java.lang.Integer r24, int r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 1052
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateMessageStateAndId$177(long, long, java.lang.Integer, int, int, int):long[]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageStateAndIdInternal$176(TLRPC$TL_updates tLRPC$TL_updates) {
        getMessagesController().processUpdates(tLRPC$TL_updates, false);
    }

    public long[] updateMessageStateAndId(final long j, final long j2, final Integer num, final int i, final int i2, boolean z, final int i3) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda94
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$updateMessageStateAndId$177(j, j2, num, i, i2, i3);
                }
            });
            return null;
        }
        return lambda$updateMessageStateAndId$177(j, j2, num, i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateUsersInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateUsers$178(ArrayList<TLRPC$User> arrayList, boolean z, boolean z2) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (z) {
                    if (z2) {
                        this.database.beginTransaction();
                    }
                    SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE users SET status = ? WHERE uid = ?");
                    try {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            TLRPC$User tLRPC$User = arrayList.get(i);
                            executeFast.requery();
                            TLRPC$UserStatus tLRPC$UserStatus = tLRPC$User.status;
                            if (tLRPC$UserStatus != null) {
                                executeFast.bindInteger(1, tLRPC$UserStatus.expires);
                            } else {
                                executeFast.bindInteger(1, 0);
                            }
                            executeFast.bindLong(2, tLRPC$User.id);
                            executeFast.step();
                        }
                        executeFast.dispose();
                        if (z2) {
                            this.database.commitTransaction();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = executeFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = executeFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    LongSparseArray longSparseArray = new LongSparseArray();
                    int size2 = arrayList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        TLRPC$User tLRPC$User2 = arrayList.get(i2);
                        if (sb.length() != 0) {
                            sb.append(",");
                        }
                        sb.append(tLRPC$User2.id);
                        longSparseArray.put(tLRPC$User2.id, tLRPC$User2);
                    }
                    ArrayList<TLRPC$User> arrayList2 = new ArrayList<>();
                    getUsersInternal(sb.toString(), arrayList2);
                    int size3 = arrayList2.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        TLRPC$User tLRPC$User3 = arrayList2.get(i3);
                        TLRPC$User tLRPC$User4 = (TLRPC$User) longSparseArray.get(tLRPC$User3.id);
                        if (tLRPC$User4 != null) {
                            if (tLRPC$User4.first_name != null && tLRPC$User4.last_name != null) {
                                if (!UserObject.isContact(tLRPC$User3)) {
                                    tLRPC$User3.first_name = tLRPC$User4.first_name;
                                    tLRPC$User3.last_name = tLRPC$User4.last_name;
                                }
                                tLRPC$User3.username = tLRPC$User4.username;
                            } else {
                                TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto = tLRPC$User4.photo;
                                if (tLRPC$UserProfilePhoto != null) {
                                    tLRPC$User3.photo = tLRPC$UserProfilePhoto;
                                } else {
                                    String str = tLRPC$User4.phone;
                                    if (str != null) {
                                        tLRPC$User3.phone = str;
                                    }
                                }
                            }
                        }
                    }
                    if (!arrayList2.isEmpty()) {
                        if (z2) {
                            this.database.beginTransaction();
                        }
                        putUsersInternal(arrayList2);
                        if (z2) {
                            this.database.commitTransaction();
                        }
                    }
                }
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void updateUsers(final ArrayList<TLRPC$User> arrayList, final boolean z, final boolean z2, boolean z3) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (z3) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda161
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$updateUsers$178(arrayList, z, z2);
                }
            });
        } else {
            lambda$updateUsers$178(arrayList, z, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00ed  */
    /* renamed from: markMessagesAsReadInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$markMessagesAsRead$180(org.telegram.messenger.support.LongSparseIntArray r18, org.telegram.messenger.support.LongSparseIntArray r19, android.util.SparseIntArray r20) {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsRead$180(org.telegram.messenger.support.LongSparseIntArray, org.telegram.messenger.support.LongSparseIntArray, android.util.SparseIntArray):void");
    }

    private void markMessagesContentAsReadInternal(long j, ArrayList<Integer> arrayList, int i) {
        SQLiteCursor sQLiteCursor = null;
        ArrayList<Integer> arrayList2 = null;
        sQLiteCursor = null;
        try {
            try {
                String join = TextUtils.join(",", arrayList);
                SQLiteDatabase sQLiteDatabase = this.database;
                Locale locale = Locale.US;
                sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE mid IN (%s) AND uid = %d", join, Long.valueOf(j))).stepThis().dispose();
                if (i != 0) {
                    SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(locale, "SELECT mid, ttl FROM messages_v2 WHERE mid IN (%s) AND uid = %d AND ttl > 0", join, Long.valueOf(j)), new Object[0]);
                    while (queryFinalized.next()) {
                        try {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList<>();
                            }
                            arrayList2.add(Integer.valueOf(queryFinalized.intValue(0)));
                        } catch (Exception e) {
                            e = e;
                            sQLiteCursor = queryFinalized;
                            checkSQLException(e);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            sQLiteCursor = queryFinalized;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            throw th;
                        }
                    }
                    if (arrayList2 != null) {
                        emptyMessagesMedia(j, arrayList2);
                    }
                    queryFinalized.dispose();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void markMessagesContentAsRead(final long j, final ArrayList<Integer> arrayList, final int i) {
        if (isEmpty(arrayList)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda104
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$markMessagesContentAsRead$179(j, arrayList, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007c  */
    /* JADX WARN: Type inference failed for: r9v1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$markMessagesContentAsRead$179(long r7, java.util.ArrayList r9, int r10) {
        /*
            r6 = this;
            r0 = 0
            int r2 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r2 != 0) goto L80
            r7 = 0
            androidx.collection.LongSparseArray r8 = new androidx.collection.LongSparseArray     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r8.<init>()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            org.telegram.SQLite.SQLiteDatabase r0 = r6.database     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.util.Locale r1 = java.util.Locale.US     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r2 = "SELECT uid, mid FROM messages_v2 WHERE mid IN (%s) AND is_channel = 0"
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r5 = ","
            java.lang.String r9 = android.text.TextUtils.join(r5, r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r5 = 0
            r4[r5] = r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r9 = java.lang.String.format(r1, r2, r4)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            org.telegram.SQLite.SQLiteCursor r9 = r0.queryFinalized(r9, r1)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
        L28:
            boolean r0 = r9.next()     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            if (r0 == 0) goto L4e
            long r0 = r9.longValue(r5)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            java.lang.Object r2 = r8.get(r0)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            if (r2 != 0) goto L42
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            r2.<init>()     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            r8.put(r0, r2)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
        L42:
            int r0 = r9.intValue(r3)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            r2.add(r0)     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            goto L28
        L4e:
            r9.dispose()     // Catch: java.lang.Exception -> L67 java.lang.Throwable -> L79
            int r9 = r8.size()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
        L55:
            if (r5 >= r9) goto L83
            long r0 = r8.keyAt(r5)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.Object r2 = r8.valueAt(r5)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.util.ArrayList r2 = (java.util.ArrayList) r2     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r6.markMessagesContentAsReadInternal(r0, r2, r10)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            int r5 = r5 + 1
            goto L55
        L67:
            r7 = move-exception
            goto L70
        L69:
            r8 = move-exception
            r9 = r7
            r7 = r8
            goto L7a
        L6d:
            r8 = move-exception
            r9 = r7
            r7 = r8
        L70:
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L79
            if (r9 == 0) goto L83
            r9.dispose()
            goto L83
        L79:
            r7 = move-exception
        L7a:
            if (r9 == 0) goto L7f
            r9.dispose()
        L7f:
            throw r7
        L80:
            r6.markMessagesContentAsReadInternal(r7, r9, r10)
        L83:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesContentAsRead$179(long, java.util.ArrayList, int):void");
    }

    public void markMessagesAsRead(final LongSparseIntArray longSparseIntArray, final LongSparseIntArray longSparseIntArray2, final SparseIntArray sparseIntArray, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda169
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$markMessagesAsRead$180(longSparseIntArray, longSparseIntArray2, sparseIntArray);
                }
            });
        } else {
            lambda$markMessagesAsRead$180(longSparseIntArray, longSparseIntArray2, sparseIntArray);
        }
    }

    public void markMessagesAsDeletedByRandoms(final ArrayList<Long> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda144
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$markMessagesAsDeletedByRandoms$182(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedByRandoms$182(ArrayList arrayList) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT mid, uid FROM randoms_v2 WHERE random_id IN(%s)", TextUtils.join(",", arrayList)), new Object[0]);
                try {
                    LongSparseArray longSparseArray = new LongSparseArray();
                    while (queryFinalized.next()) {
                        long longValue = queryFinalized.longValue(1);
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(longValue);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(longValue, arrayList2);
                        }
                        arrayList2.add(Integer.valueOf(queryFinalized.intValue(0)));
                    }
                    queryFinalized.dispose();
                    if (longSparseArray.isEmpty()) {
                        return;
                    }
                    int size = longSparseArray.size();
                    for (int i = 0; i < size; i++) {
                        long keyAt = longSparseArray.keyAt(i);
                        final ArrayList<Integer> arrayList3 = (ArrayList) longSparseArray.valueAt(i);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda149
                            @Override // java.lang.Runnable
                            public final void run() {
                                MessagesStorage.this.lambda$markMessagesAsDeletedByRandoms$181(arrayList3);
                            }
                        });
                        updateDialogsWithReadMessagesInternal(arrayList3, null, null, null, null);
                        lambda$markMessagesAsDeleted$186(keyAt, arrayList3, true, false);
                        lambda$updateDialogsWithDeletedMessages$185(keyAt, 0L, arrayList3, null);
                    }
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedByRandoms$181(ArrayList arrayList) {
        getNotificationCenter().postNotificationName(NotificationCenter.messagesDeleted, arrayList, 0L, Boolean.FALSE);
    }

    protected void deletePushMessages(long j, ArrayList<Integer> arrayList) {
        try {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM unread_push_messages WHERE uid = %d AND mid IN(%s)", Long.valueOf(j), TextUtils.join(",", arrayList))).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    private void broadcastScheduledMessagesChange(final Long l) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor queryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM scheduled_messages_v2 WHERE uid = %d", l), new Object[0]);
                try {
                    final int intValue = queryFinalized.next() ? queryFinalized.intValue(0) : 0;
                    queryFinalized.dispose();
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda134
                        @Override // java.lang.Runnable
                        public final void run() {
                            MessagesStorage.this.lambda$broadcastScheduledMessagesChange$183(l, intValue);
                        }
                    });
                } catch (Exception e) {
                    e = e;
                    sQLiteCursor = queryFinalized;
                    checkSQLException(e);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLiteCursor = queryFinalized;
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$broadcastScheduledMessagesChange$183(Long l, int i) {
        getNotificationCenter().postNotificationName(NotificationCenter.scheduledMessagesUpdated, l, Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(20:(9:76|77|78|79|80|81|(2:566|567)|83|84)|(3:86|87|(13:89|(3:91|92|93)(1:564)|94|(1:96)|(1:559)|100|101|(8:108|109|110|111|112|113|114|116)(2:104|105)|71|72|73|74|(27:577|121|(6:123|124|(9:128|129|130|131|(10:510|511|512|513|514|515|516|517|(2:(1:520)|521)|522)(1:133)|(11:138|139|140|141|142|143|144|145|(3:147|148|149)(1:496)|150|(1:160)(2:(1:153)|(2:158|159)(1:157)))(2:135|136)|137|125|126)|541|542|166)(1:548)|167|(8:(1:(1:171)(1:186))(2:187|(1:(1:190)(1:191))(1:(3:193|194|179)(1:195)))|172|173|(1:175)|176|177|178|179)|196|197|(10:200|201|202|(1:204)(1:215)|205|206|207|208|209|198)|221|222|223|(5:225|(15:228|229|230|(1:232)(1:256)|233|234|(1:237)|238|239|240|(2:(1:243)|244)|245|(2:247|248)(1:250)|249|226)|262|263|(3:265|(2:268|266)|269))(1:492)|270|271|(17:273|274|275|(2:277|(6:279|280|281|282|283|284)(1:456))(1:457)|285|286|287|(1:289)(1:446)|290|(5:425|426|427|(4:429|430|431|432)|440)|292|(6:294|295|(8:299|(1:301)|302|(1:304)(1:310)|(2:306|307)(1:309)|308|296|297)|311|312|(13:314|315|316|317|(6:319|320|321|(9:323|324|325|326|327|(1:329)(1:336)|330|(2:332|333)(1:335)|334)|349|350)|356|357|358|359|360|(5:362|(7:365|(1:367)|368|(1:370)(1:376)|(2:372|373)(1:375)|374|363)|377|378|(7:380|381|382|(5:384|385|(6:388|(1:390)(1:398)|391|(2:393|394)(2:396|397)|395|386)|399|400)|406|407|408)(1:412))(1:413)|409|410))|424|360|(0)(0)|409|410)|465|466|467|(1:(1:470)(1:471))|472|(1:(1:475)(1:476))|477|(1:479)|480|(4:482|(2:485|483)|486|487)|(1:489)|491)(0)))|565|100|101|(0)|106|108|109|110|111|112|113|114|116|71|72|73|74|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x021a, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:553:0x021d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:554:0x0243, code lost:
    
        r18 = r6;
        r28 = r21;
        r21 = r25;
        r13 = 0;
        r25 = r41;
        r41 = r3;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0279 A[Catch: all -> 0x0cb9, Exception -> 0x0cbf, TRY_ENTER, TRY_LEAVE, TryCatch #56 {Exception -> 0x0cbf, all -> 0x0cb9, blocks: (B:3:0x0006, B:6:0x0012, B:19:0x005e, B:21:0x0083, B:68:0x009a, B:70:0x00d4, B:123:0x0279, B:167:0x03cc, B:171:0x03d8, B:177:0x0455, B:179:0x045d, B:186:0x03e7, B:190:0x03fb, B:191:0x040a, B:195:0x041e, B:197:0x046e, B:198:0x0489, B:200:0x048f, B:206:0x04ca, B:223:0x052b, B:225:0x0531, B:226:0x053b, B:228:0x0541, B:234:0x058a, B:237:0x05af, B:238:0x05d7, B:243:0x0606, B:244:0x060b, B:245:0x0614, B:247:0x062a, B:249:0x0630, B:265:0x0650, B:266:0x0654, B:268:0x065a, B:270:0x066b, B:583:0x00fd), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x048f A[Catch: all -> 0x0cb9, Exception -> 0x0cbf, TRY_LEAVE, TryCatch #56 {Exception -> 0x0cbf, all -> 0x0cb9, blocks: (B:3:0x0006, B:6:0x0012, B:19:0x005e, B:21:0x0083, B:68:0x009a, B:70:0x00d4, B:123:0x0279, B:167:0x03cc, B:171:0x03d8, B:177:0x0455, B:179:0x045d, B:186:0x03e7, B:190:0x03fb, B:191:0x040a, B:195:0x041e, B:197:0x046e, B:198:0x0489, B:200:0x048f, B:206:0x04ca, B:223:0x052b, B:225:0x0531, B:226:0x053b, B:228:0x0541, B:234:0x058a, B:237:0x05af, B:238:0x05d7, B:243:0x0606, B:244:0x060b, B:245:0x0614, B:247:0x062a, B:249:0x0630, B:265:0x0650, B:266:0x0654, B:268:0x065a, B:270:0x066b, B:583:0x00fd), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0531 A[Catch: all -> 0x0cb9, Exception -> 0x0cbf, TryCatch #56 {Exception -> 0x0cbf, all -> 0x0cb9, blocks: (B:3:0x0006, B:6:0x0012, B:19:0x005e, B:21:0x0083, B:68:0x009a, B:70:0x00d4, B:123:0x0279, B:167:0x03cc, B:171:0x03d8, B:177:0x0455, B:179:0x045d, B:186:0x03e7, B:190:0x03fb, B:191:0x040a, B:195:0x041e, B:197:0x046e, B:198:0x0489, B:200:0x048f, B:206:0x04ca, B:223:0x052b, B:225:0x0531, B:226:0x053b, B:228:0x0541, B:234:0x058a, B:237:0x05af, B:238:0x05d7, B:243:0x0606, B:244:0x060b, B:245:0x0614, B:247:0x062a, B:249:0x0630, B:265:0x0650, B:266:0x0654, B:268:0x065a, B:270:0x066b, B:583:0x00fd), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0c98  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0674  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0c9d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0ca2  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x09ad A[Catch: all -> 0x0b59, Exception -> 0x0b60, TryCatch #58 {Exception -> 0x0b60, all -> 0x0b59, blocks: (B:297:0x0851, B:299:0x0857, B:301:0x0862, B:302:0x0867, B:304:0x086f, B:306:0x0888, B:308:0x088d, B:310:0x087e, B:312:0x089e, B:360:0x09a7, B:362:0x09ad, B:363:0x09cb, B:365:0x09d1, B:367:0x09e5, B:368:0x09ea, B:370:0x09f2, B:372:0x0a09, B:374:0x0a0e, B:376:0x0a00, B:378:0x0a1d), top: B:296:0x0851 }] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0af6  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0cca  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0ccf  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0cd4  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x0bb9  */
    /* JADX WARN: Removed duplicated region for block: B:474:0x0bee  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x0c2d A[Catch: all -> 0x0ca6, Exception -> 0x0cad, TryCatch #61 {Exception -> 0x0cad, all -> 0x0ca6, blocks: (B:284:0x06a1, B:285:0x06fd, B:456:0x06c6, B:467:0x0b98, B:470:0x0bbb, B:471:0x0bcb, B:472:0x0be8, B:475:0x0bf0, B:476:0x0c00, B:477:0x0c1d, B:479:0x0c2d, B:480:0x0c31, B:483:0x0c39, B:485:0x0c3f, B:487:0x0c76, B:489:0x0c85), top: B:283:0x06a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0c38  */
    /* JADX WARN: Removed duplicated region for block: B:489:0x0c85 A[Catch: all -> 0x0ca6, Exception -> 0x0cad, TRY_LEAVE, TryCatch #61 {Exception -> 0x0cad, all -> 0x0ca6, blocks: (B:284:0x06a1, B:285:0x06fd, B:456:0x06c6, B:467:0x0b98, B:470:0x0bbb, B:471:0x0bcb, B:472:0x0be8, B:475:0x0bf0, B:476:0x0c00, B:477:0x0c1d, B:479:0x0c2d, B:480:0x0c31, B:483:0x0c39, B:485:0x0c3f, B:487:0x0c76, B:489:0x0c85), top: B:283:0x06a1 }] */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0668  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0ce2  */
    /* JADX WARN: Removed duplicated region for block: B:548:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0ce7  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0cec  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0123  */
    /* JADX WARN: Type inference failed for: r13v49, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v52 */
    /* JADX WARN: Type inference failed for: r13v69 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /* renamed from: markMessagesAsDeletedInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<java.lang.Long> lambda$markMessagesAsDeleted$186(long r37, java.util.ArrayList<java.lang.Integer> r39, boolean r40, boolean r41) {
        /*
            Method dump skipped, instructions count: 3312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsDeleted$186(long, java.util.ArrayList, boolean, boolean):java.util.ArrayList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$184(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0414  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x042c  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0431  */
    /* renamed from: updateDialogsWithDeletedMessagesInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$updateDialogsWithDeletedMessages$185(long r24, long r26, java.util.ArrayList<java.lang.Integer> r28, java.util.ArrayList<java.lang.Long> r29) {
        /*
            Method dump skipped, instructions count: 1077
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogsWithDeletedMessages$185(long, long, java.util.ArrayList, java.util.ArrayList):void");
    }

    public void updateDialogsWithDeletedMessages(final long j, final long j2, final ArrayList<Integer> arrayList, final ArrayList<Long> arrayList2, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda95
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$updateDialogsWithDeletedMessages$185(j, j2, arrayList, arrayList2);
                }
            });
        } else {
            lambda$updateDialogsWithDeletedMessages$185(j, j2, arrayList, arrayList2);
        }
    }

    public ArrayList<Long> markMessagesAsDeleted(final long j, final ArrayList<Integer> arrayList, boolean z, final boolean z2, final boolean z3) {
        if (arrayList.isEmpty()) {
            return null;
        }
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda108
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$markMessagesAsDeleted$186(j, arrayList, z2, z3);
                }
            });
            return null;
        }
        return lambda$markMessagesAsDeleted$186(j, arrayList, z2, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:109:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d4 A[Catch: all -> 0x0110, Exception -> 0x011b, TRY_LEAVE, TryCatch #15 {Exception -> 0x011b, all -> 0x0110, blocks: (B:6:0x004b, B:8:0x0051, B:24:0x00bf, B:28:0x00cd, B:30:0x00d4), top: B:5:0x004b }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0141 A[Catch: all -> 0x031d, Exception -> 0x031f, TRY_LEAVE, TryCatch #14 {Exception -> 0x031f, all -> 0x031d, blocks: (B:45:0x0127, B:46:0x013b, B:48:0x0141, B:54:0x0179, B:89:0x01cf, B:96:0x0231, B:106:0x0276), top: B:44:0x0127 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0226 A[Catch: all -> 0x0318, Exception -> 0x031b, TryCatch #13 {Exception -> 0x031b, all -> 0x0318, blocks: (B:91:0x0220, B:93:0x0226, B:94:0x022c, B:97:0x0248, B:99:0x024e, B:104:0x0273), top: B:90:0x0220 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0231 A[Catch: all -> 0x031d, Exception -> 0x031f, TRY_ENTER, TRY_LEAVE, TryCatch #14 {Exception -> 0x031f, all -> 0x031d, blocks: (B:45:0x0127, B:46:0x013b, B:48:0x0141, B:54:0x0179, B:89:0x01cf, B:96:0x0231, B:106:0x0276), top: B:44:0x0127 }] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* renamed from: markMessagesAsDeletedInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<java.lang.Long> lambda$markMessagesAsDeleted$188(long r24, int r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 849
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsDeleted$188(long, int, boolean):java.util.ArrayList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$187(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    public ArrayList<Long> markMessagesAsDeleted(final long j, final int i, boolean z, final boolean z2) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda87
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$markMessagesAsDeleted$188(j, i, z2);
                }
            });
            return null;
        }
        return lambda$markMessagesAsDeleted$188(j, i, z2);
    }

    private void fixUnsupportedMedia(TLRPC$Message tLRPC$Message) {
        if (tLRPC$Message == null) {
            return;
        }
        TLRPC$MessageMedia tLRPC$MessageMedia = tLRPC$Message.media;
        if (tLRPC$MessageMedia instanceof TLRPC$TL_messageMediaUnsupported_old) {
            if (tLRPC$MessageMedia.bytes.length == 0) {
                tLRPC$MessageMedia.bytes = Utilities.intToBytes(158);
            }
        } else if (tLRPC$MessageMedia instanceof TLRPC$TL_messageMediaUnsupported) {
            TLRPC$TL_messageMediaUnsupported_old tLRPC$TL_messageMediaUnsupported_old = new TLRPC$TL_messageMediaUnsupported_old();
            tLRPC$Message.media = tLRPC$TL_messageMediaUnsupported_old;
            tLRPC$TL_messageMediaUnsupported_old.bytes = Utilities.intToBytes(158);
            tLRPC$Message.flags |= LiteMode.FLAG_CALLS_ANIMATIONS;
        }
    }

    private void doneHolesInTable(String str, long j, int i, int i2) throws Exception {
        SQLitePreparedStatement executeFast;
        int i3 = 2;
        if (i2 != 0) {
            if (i == 0) {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(i2))).stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = 0", Long.valueOf(j), Integer.valueOf(i2))).stepThis().dispose();
            }
        } else if (i == 0) {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d", Long.valueOf(j))).stepThis().dispose();
        } else {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = 0", Long.valueOf(j))).stepThis().dispose();
        }
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (i2 != 0) {
                    executeFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?, ?)");
                } else {
                    executeFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?)");
                }
                sQLitePreparedStatement = executeFast;
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindLong(1, j);
                if (i2 != 0) {
                    sQLitePreparedStatement.bindInteger(2, i2);
                    i3 = 3;
                }
                sQLitePreparedStatement.bindInteger(i3, 1);
                sQLitePreparedStatement.bindInteger(i3 + 1, 1);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                throw e;
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void doneHolesInMedia(long j, int i, int i2, int i3) throws Exception {
        SQLitePreparedStatement executeFast;
        SQLitePreparedStatement executeFast2;
        int i4;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        int i5 = 3;
        if (i2 == -1) {
            if (i3 != 0) {
                if (i == 0) {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(i3))).stepThis().dispose();
                } else {
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_topics WHERE uid = %d AND topic_id = %d AND start = 0", Long.valueOf(j), Integer.valueOf(i3))).stepThis().dispose();
                }
            } else if (i == 0) {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d", Long.valueOf(j))).stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND start = 0", Long.valueOf(j))).stepThis().dispose();
            }
            try {
                try {
                    if (i3 != 0) {
                        executeFast2 = this.database.executeFast("REPLACE INTO media_holes_topics VALUES(?, ?, ?, ?, ?)");
                    } else {
                        executeFast2 = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                    }
                    sQLitePreparedStatement = executeFast2;
                    for (int i6 = 0; i6 < 8; i6++) {
                        sQLitePreparedStatement.requery();
                        sQLitePreparedStatement.bindLong(1, j);
                        if (i3 != 0) {
                            sQLitePreparedStatement.bindInteger(2, i3);
                            i4 = 3;
                        } else {
                            i4 = 2;
                        }
                        int i7 = i4 + 1;
                        sQLitePreparedStatement.bindInteger(i4, i6);
                        sQLitePreparedStatement.bindInteger(i7, 1);
                        sQLitePreparedStatement.bindInteger(i7 + 1, 1);
                        sQLitePreparedStatement.step();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                        return;
                    }
                    return;
                } catch (Exception e) {
                    throw e;
                }
            } finally {
            }
        }
        if (i3 != 0) {
            if (i == 0) {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_topics WHERE uid = %d AND topic_id = %d AND type = %d", Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(i2))).stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_topics WHERE uid = %d AND topic_id = %d AND type = %d AND start = 0", Long.valueOf(j), Integer.valueOf(i3), Integer.valueOf(i2))).stepThis().dispose();
            }
        } else if (i == 0) {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d", Long.valueOf(j), Integer.valueOf(i2))).stepThis().dispose();
        } else {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM media_holes_v2 WHERE uid = %d AND type = %d AND start = 0", Long.valueOf(j), Integer.valueOf(i2))).stepThis().dispose();
        }
        try {
            try {
                if (i3 != 0) {
                    executeFast = this.database.executeFast("REPLACE INTO media_holes_topics VALUES(?, ?, ?, ?, ?)");
                } else {
                    executeFast = this.database.executeFast("REPLACE INTO media_holes_v2 VALUES(?, ?, ?, ?)");
                }
                sQLitePreparedStatement = executeFast;
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindLong(1, j);
                if (i3 != 0) {
                    sQLitePreparedStatement.bindInteger(2, i3);
                } else {
                    i5 = 2;
                }
                int i8 = i5 + 1;
                sQLitePreparedStatement.bindInteger(i5, i2);
                sQLitePreparedStatement.bindInteger(i8, 1);
                sQLitePreparedStatement.bindInteger(i8 + 1, 1);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
                sQLitePreparedStatement.dispose();
            } catch (Exception e2) {
                throw e2;
            }
        } finally {
        }
    }

    private static class Hole {
        public int end;
        public int start;
        public int type;

        public Hole(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public Hole(int i, int i2, int i3) {
            this.type = i;
            this.start = i2;
            this.end = i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x049a  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void closeHolesInMedia(long r25, int r27, int r28, int r29, int r30) {
        /*
            Method dump skipped, instructions count: 1187
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.closeHolesInMedia(long, int, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0481  */
    /* JADX WARN: Removed duplicated region for block: B:102:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0474  */
    /* JADX WARN: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x047c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void closeHolesInTable(java.lang.String r25, long r26, int r28, int r29, int r30) {
        /*
            Method dump skipped, instructions count: 1157
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.closeHolesInTable(java.lang.String, long, int, int, int):void");
    }

    public void replaceMessageIfExists(final TLRPC$Message tLRPC$Message, final ArrayList<TLRPC$User> arrayList, final ArrayList<TLRPC$Chat> arrayList2, final boolean z) {
        if (tLRPC$Message == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda188
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$replaceMessageIfExists$190(tLRPC$Message, z, arrayList, arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0224 A[Catch: all -> 0x0294, Exception -> 0x0299, TRY_ENTER, TryCatch #11 {Exception -> 0x0299, all -> 0x0294, blocks: (B:8:0x0034, B:18:0x0047, B:19:0x005f, B:21:0x006c, B:22:0x006f, B:27:0x0087, B:32:0x0198, B:34:0x009b, B:73:0x0195, B:115:0x00a4, B:118:0x01a7, B:125:0x01b8, B:132:0x01cc, B:145:0x01d5, B:150:0x0224, B:151:0x0227, B:153:0x0231, B:154:0x023c, B:156:0x0242, B:158:0x0256, B:160:0x025c, B:162:0x0270, B:172:0x005b, B:178:0x02a1, B:179:0x02a4), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0231 A[Catch: all -> 0x0294, Exception -> 0x0299, TryCatch #11 {Exception -> 0x0299, all -> 0x0294, blocks: (B:8:0x0034, B:18:0x0047, B:19:0x005f, B:21:0x006c, B:22:0x006f, B:27:0x0087, B:32:0x0198, B:34:0x009b, B:73:0x0195, B:115:0x00a4, B:118:0x01a7, B:125:0x01b8, B:132:0x01cc, B:145:0x01d5, B:150:0x0224, B:151:0x0227, B:153:0x0231, B:154:0x023c, B:156:0x0242, B:158:0x0256, B:160:0x025c, B:162:0x0270, B:172:0x005b, B:178:0x02a1, B:179:0x02a4), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0290  */
    /* JADX WARN: Removed duplicated region for block: B:167:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:172:0x005b A[Catch: all -> 0x0294, Exception -> 0x0299, TRY_ENTER, TryCatch #11 {Exception -> 0x0299, all -> 0x0294, blocks: (B:8:0x0034, B:18:0x0047, B:19:0x005f, B:21:0x006c, B:22:0x006f, B:27:0x0087, B:32:0x0198, B:34:0x009b, B:73:0x0195, B:115:0x00a4, B:118:0x01a7, B:125:0x01b8, B:132:0x01cc, B:145:0x01d5, B:150:0x0224, B:151:0x0227, B:153:0x0231, B:154:0x023c, B:156:0x0242, B:158:0x0256, B:160:0x025c, B:162:0x0270, B:172:0x005b, B:178:0x02a1, B:179:0x02a4), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006c A[Catch: all -> 0x0294, Exception -> 0x0299, TryCatch #11 {Exception -> 0x0299, all -> 0x0294, blocks: (B:8:0x0034, B:18:0x0047, B:19:0x005f, B:21:0x006c, B:22:0x006f, B:27:0x0087, B:32:0x0198, B:34:0x009b, B:73:0x0195, B:115:0x00a4, B:118:0x01a7, B:125:0x01b8, B:132:0x01cc, B:145:0x01d5, B:150:0x0224, B:151:0x0227, B:153:0x0231, B:154:0x023c, B:156:0x0242, B:158:0x0256, B:160:0x025c, B:162:0x0270, B:172:0x005b, B:178:0x02a1, B:179:0x02a4), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f9 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0131 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0151 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0170 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x017d A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0195 A[Catch: all -> 0x0294, Exception -> 0x0299, TRY_ENTER, TryCatch #11 {Exception -> 0x0299, all -> 0x0294, blocks: (B:8:0x0034, B:18:0x0047, B:19:0x005f, B:21:0x006c, B:22:0x006f, B:27:0x0087, B:32:0x0198, B:34:0x009b, B:73:0x0195, B:115:0x00a4, B:118:0x01a7, B:125:0x01b8, B:132:0x01cc, B:145:0x01d5, B:150:0x0224, B:151:0x0227, B:153:0x0231, B:154:0x023c, B:156:0x0242, B:158:0x0256, B:160:0x025c, B:162:0x0270, B:172:0x005b, B:178:0x02a1, B:179:0x02a4), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0198 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0176 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015e A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0147 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0101 A[Catch: all -> 0x019f, Exception -> 0x01a3, TryCatch #10 {Exception -> 0x01a3, all -> 0x019f, blocks: (B:36:0x00ac, B:38:0x00bb, B:39:0x00c1, B:41:0x00e1, B:45:0x00e9, B:47:0x00f9, B:48:0x010a, B:51:0x0115, B:54:0x0123, B:56:0x0131, B:57:0x014d, B:59:0x0151, B:62:0x015a, B:63:0x0163, B:65:0x0170, B:67:0x017d, B:69:0x0184, B:70:0x018a, B:71:0x018d, B:76:0x0176, B:77:0x0158, B:78:0x015e, B:79:0x0147, B:84:0x0101), top: B:35:0x00ac }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$replaceMessageIfExists$190(org.telegram.tgnet.TLRPC$Message r19, boolean r20, java.util.ArrayList r21, java.util.ArrayList r22) {
        /*
            Method dump skipped, instructions count: 717
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$replaceMessageIfExists$190(org.telegram.tgnet.TLRPC$Message, boolean, java.util.ArrayList, java.util.ArrayList):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceMessageIfExists$189(MessageObject messageObject, ArrayList arrayList) {
        getNotificationCenter().postNotificationName(NotificationCenter.replaceMessagesObjects, Long.valueOf(messageObject.getDialogId()), arrayList);
    }

    public void putMessages(final TLRPC$messages_Messages tLRPC$messages_Messages, final long j, final int i, final int i2, final boolean z, final boolean z2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda204
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putMessages$192(z2, j, tLRPC$messages_Messages, i3, i, i2, z);
            }
        });
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessages$192(boolean r45, long r46, org.telegram.tgnet.TLRPC$messages_Messages r48, int r49, int r50, int r51, boolean r52) {
        /*
            Method dump skipped, instructions count: 2956
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putMessages$192(boolean, long, org.telegram.tgnet.TLRPC$messages_Messages, int, int, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessages$191(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    public static void addUsersAndChatsFromMessage(TLRPC$Message tLRPC$Message, ArrayList<Long> arrayList, ArrayList<Long> arrayList2, ArrayList<Long> arrayList3) {
        String str;
        TLRPC$Peer tLRPC$Peer;
        TLRPC$TL_messageMediaPoll tLRPC$TL_messageMediaPoll;
        TLRPC$PollResults tLRPC$PollResults;
        ArrayList<Long> arrayList4;
        long fromChatId = MessageObject.getFromChatId(tLRPC$Message);
        if (DialogObject.isUserDialog(fromChatId)) {
            if (!arrayList.contains(Long.valueOf(fromChatId))) {
                arrayList.add(Long.valueOf(fromChatId));
            }
        } else if (DialogObject.isChatDialog(fromChatId)) {
            long j = -fromChatId;
            if (!arrayList2.contains(Long.valueOf(j))) {
                arrayList2.add(Long.valueOf(j));
            }
        }
        long j2 = tLRPC$Message.via_bot_id;
        if (j2 != 0 && !arrayList.contains(Long.valueOf(j2))) {
            arrayList.add(Long.valueOf(tLRPC$Message.via_bot_id));
        }
        TLRPC$MessageAction tLRPC$MessageAction = tLRPC$Message.action;
        if (tLRPC$MessageAction != null) {
            long j3 = tLRPC$MessageAction.user_id;
            if (j3 != 0 && !arrayList.contains(Long.valueOf(j3))) {
                arrayList.add(Long.valueOf(tLRPC$Message.action.user_id));
            }
            long j4 = tLRPC$Message.action.channel_id;
            if (j4 != 0 && !arrayList2.contains(Long.valueOf(j4))) {
                arrayList2.add(Long.valueOf(tLRPC$Message.action.channel_id));
            }
            long j5 = tLRPC$Message.action.chat_id;
            if (j5 != 0 && !arrayList2.contains(Long.valueOf(j5))) {
                arrayList2.add(Long.valueOf(tLRPC$Message.action.chat_id));
            }
            TLRPC$MessageAction tLRPC$MessageAction2 = tLRPC$Message.action;
            if (tLRPC$MessageAction2 instanceof TLRPC$TL_messageActionGeoProximityReached) {
                TLRPC$TL_messageActionGeoProximityReached tLRPC$TL_messageActionGeoProximityReached = (TLRPC$TL_messageActionGeoProximityReached) tLRPC$MessageAction2;
                long peerId = MessageObject.getPeerId(tLRPC$TL_messageActionGeoProximityReached.from_id);
                if (DialogObject.isUserDialog(peerId)) {
                    if (!arrayList.contains(Long.valueOf(peerId))) {
                        arrayList.add(Long.valueOf(peerId));
                    }
                } else {
                    long j6 = -peerId;
                    if (!arrayList2.contains(Long.valueOf(j6))) {
                        arrayList2.add(Long.valueOf(j6));
                    }
                }
                long peerId2 = MessageObject.getPeerId(tLRPC$TL_messageActionGeoProximityReached.to_id);
                if (peerId2 > 0) {
                    if (!arrayList.contains(Long.valueOf(peerId2))) {
                        arrayList.add(Long.valueOf(peerId2));
                    }
                } else {
                    long j7 = -peerId2;
                    if (!arrayList2.contains(Long.valueOf(j7))) {
                        arrayList2.add(Long.valueOf(j7));
                    }
                }
            }
            if (!tLRPC$Message.action.users.isEmpty()) {
                for (int i = 0; i < tLRPC$Message.action.users.size(); i++) {
                    Long l = tLRPC$Message.action.users.get(i);
                    if (!arrayList.contains(l)) {
                        arrayList.add(l);
                    }
                }
            }
        }
        if (!tLRPC$Message.entities.isEmpty()) {
            for (int i2 = 0; i2 < tLRPC$Message.entities.size(); i2++) {
                TLRPC$MessageEntity tLRPC$MessageEntity = tLRPC$Message.entities.get(i2);
                if (tLRPC$MessageEntity instanceof TLRPC$TL_messageEntityMentionName) {
                    arrayList.add(Long.valueOf(((TLRPC$TL_messageEntityMentionName) tLRPC$MessageEntity).user_id));
                } else if (tLRPC$MessageEntity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                    arrayList.add(Long.valueOf(((TLRPC$TL_inputMessageEntityMentionName) tLRPC$MessageEntity).user_id.user_id));
                } else if (arrayList3 != null && (tLRPC$MessageEntity instanceof TLRPC$TL_messageEntityCustomEmoji)) {
                    arrayList3.add(Long.valueOf(((TLRPC$TL_messageEntityCustomEmoji) tLRPC$MessageEntity).document_id));
                }
            }
        }
        TLRPC$MessageMedia tLRPC$MessageMedia = tLRPC$Message.media;
        if (tLRPC$MessageMedia != null) {
            long j8 = tLRPC$MessageMedia.user_id;
            if (j8 != 0 && !arrayList.contains(Long.valueOf(j8))) {
                arrayList.add(Long.valueOf(tLRPC$Message.media.user_id));
            }
            TLRPC$MessageMedia tLRPC$MessageMedia2 = tLRPC$Message.media;
            if ((tLRPC$MessageMedia2 instanceof TLRPC$TL_messageMediaPoll) && (tLRPC$PollResults = (tLRPC$TL_messageMediaPoll = (TLRPC$TL_messageMediaPoll) tLRPC$MessageMedia2).results) != null && (arrayList4 = tLRPC$PollResults.recent_voters) != null && !arrayList4.isEmpty()) {
                arrayList.addAll(tLRPC$TL_messageMediaPoll.results.recent_voters);
            }
        }
        TLRPC$MessageReplies tLRPC$MessageReplies = tLRPC$Message.replies;
        if (tLRPC$MessageReplies != null) {
            int size = tLRPC$MessageReplies.recent_repliers.size();
            for (int i3 = 0; i3 < size; i3++) {
                long peerId3 = MessageObject.getPeerId(tLRPC$Message.replies.recent_repliers.get(i3));
                if (DialogObject.isUserDialog(peerId3)) {
                    if (!arrayList.contains(Long.valueOf(peerId3))) {
                        arrayList.add(Long.valueOf(peerId3));
                    }
                } else if (DialogObject.isChatDialog(peerId3)) {
                    long j9 = -peerId3;
                    if (!arrayList2.contains(Long.valueOf(j9))) {
                        arrayList2.add(Long.valueOf(j9));
                    }
                }
            }
        }
        TLRPC$TL_messageReplyHeader tLRPC$TL_messageReplyHeader = tLRPC$Message.reply_to;
        if (tLRPC$TL_messageReplyHeader != null && (tLRPC$Peer = tLRPC$TL_messageReplyHeader.reply_to_peer_id) != null) {
            long peerId4 = MessageObject.getPeerId(tLRPC$Peer);
            if (DialogObject.isUserDialog(peerId4)) {
                if (!arrayList.contains(Long.valueOf(peerId4))) {
                    arrayList.add(Long.valueOf(peerId4));
                }
            } else if (DialogObject.isChatDialog(peerId4)) {
                long j10 = -peerId4;
                if (!arrayList2.contains(Long.valueOf(j10))) {
                    arrayList2.add(Long.valueOf(j10));
                }
            }
        }
        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader = tLRPC$Message.fwd_from;
        if (tLRPC$MessageFwdHeader != null) {
            TLRPC$Peer tLRPC$Peer2 = tLRPC$MessageFwdHeader.from_id;
            if (tLRPC$Peer2 instanceof TLRPC$TL_peerUser) {
                if (!arrayList.contains(Long.valueOf(tLRPC$Peer2.user_id))) {
                    arrayList.add(Long.valueOf(tLRPC$Message.fwd_from.from_id.user_id));
                }
            } else if (tLRPC$Peer2 instanceof TLRPC$TL_peerChannel) {
                if (!arrayList2.contains(Long.valueOf(tLRPC$Peer2.channel_id))) {
                    arrayList2.add(Long.valueOf(tLRPC$Message.fwd_from.from_id.channel_id));
                }
            } else if ((tLRPC$Peer2 instanceof TLRPC$TL_peerChat) && !arrayList2.contains(Long.valueOf(tLRPC$Peer2.chat_id))) {
                arrayList2.add(Long.valueOf(tLRPC$Message.fwd_from.from_id.chat_id));
            }
            TLRPC$Peer tLRPC$Peer3 = tLRPC$Message.fwd_from.saved_from_peer;
            if (tLRPC$Peer3 != null) {
                long j11 = tLRPC$Peer3.user_id;
                if (j11 != 0) {
                    if (!arrayList2.contains(Long.valueOf(j11))) {
                        arrayList.add(Long.valueOf(tLRPC$Message.fwd_from.saved_from_peer.user_id));
                    }
                } else {
                    long j12 = tLRPC$Peer3.channel_id;
                    if (j12 != 0) {
                        if (!arrayList2.contains(Long.valueOf(j12))) {
                            arrayList2.add(Long.valueOf(tLRPC$Message.fwd_from.saved_from_peer.channel_id));
                        }
                    } else {
                        long j13 = tLRPC$Peer3.chat_id;
                        if (j13 != 0 && !arrayList2.contains(Long.valueOf(j13))) {
                            arrayList2.add(Long.valueOf(tLRPC$Message.fwd_from.saved_from_peer.chat_id));
                        }
                    }
                }
            }
        }
        HashMap<String, String> hashMap = tLRPC$Message.params;
        if (hashMap == null || (str = hashMap.get("fwd_peer")) == null) {
            return;
        }
        long longValue = Utilities.parseLong(str).longValue();
        if (longValue < 0) {
            long j14 = -longValue;
            if (arrayList2.contains(Long.valueOf(j14))) {
                return;
            }
            arrayList2.add(Long.valueOf(j14));
        }
    }

    public void getDialogs(final int i, final int i2, final int i3, boolean z) {
        LongSparseArray<SparseArray<TLRPC$DraftMessage>> drafts;
        int size;
        long[] jArr = null;
        if (z && (size = (drafts = getMediaDataController().getDrafts()).size()) > 0) {
            jArr = new long[size];
            for (int i4 = 0; i4 < size; i4++) {
                if (drafts.valueAt(i4).get(0) != null) {
                    jArr[i4] = drafts.keyAt(i4);
                }
            }
        }
        final long[] jArr2 = jArr;
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDialogs$194(i, i2, i3, jArr2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(10:2|3|4|(19:(10:5|6|7|(13:9|10|(1:12)(1:305)|13|14|15|(25:19|(3:21|(2:23|(1:25)(1:26))|(1:28))(1:167)|29|(2:31|(1:33)(19:165|35|(1:37)(1:164)|38|(1:40)(1:163)|41|(2:43|(1:45))|46|(1:48)|49|(1:51)|52|(7:54|(8:56|(1:58)|59|60|61|(10:65|(3:149|150|(1:152))|67|68|(3:134|135|(9:137|138|139|140|(1:142)|71|72|(3:74|75|76)|130))|70|71|72|(0)|130)|157|130)(1:161)|78|(3:103|104|(1:106))|80|(4:82|(1:84)|85|86)(2:88|(4:90|(1:92)|93|94)(2:95|(2:97|(2:99|100)(1:101))(1:102)))|87)(1:162)|77|78|(0)|80|(0)(0)|87))(1:166)|34|35|(0)(0)|38|(0)(0)|41|(0)|46|(0)|49|(0)|52|(0)(0)|77|78|(0)|80|(0)(0)|87|16|17)|168|169|170|171|(9:173|(6:176|177|178|(2:180|181)(1:183)|182|174)|189|190|(6:194|(2:195|(3:197|(2:199|(2:201|202)(1:269))(2:271|272)|270)(2:273|274))|(2:208|(12:210|211|212|213|(1:215)|216|217|218|(2:222|(7:228|(2:232|(7:234|235|236|237|(1:239)|240|241))|257|237|(0)|240|241))|258|240|241)(2:267|268))(2:204|205)|206|191|192)|275|276|277|278)(2:285|286)|279)(1:311)|110|111|112|(1:117)|114|115)|319|320|(6:322|323|324|325|326|327)(1:380)|328|329|(1:331)|332|(1:334)|335|(5:337|(4:340|(4:343|(2:347|348)|349|341)|352|338)|353|354|(9:356|357|358|359|360|361|(1:363)|114|115))|368|358|359|360|361|(0)|114|115)|312|313|314|315|(7:386|387|(4:390|(4:395|(2:397|(1:399))(2:405|(1:407))|400|(2:402|403)(1:404))(2:392|393)|394|388)|408|409|(5:411|(3:415|412|413)|416|417|418)(1:426)|419)(1:317)|318) */
    /* JADX WARN: Can't wrap try/catch for region: R(28:2|3|4|(10:5|6|7|(13:9|10|(1:12)(1:305)|13|14|15|(25:19|(3:21|(2:23|(1:25)(1:26))|(1:28))(1:167)|29|(2:31|(1:33)(19:165|35|(1:37)(1:164)|38|(1:40)(1:163)|41|(2:43|(1:45))|46|(1:48)|49|(1:51)|52|(7:54|(8:56|(1:58)|59|60|61|(10:65|(3:149|150|(1:152))|67|68|(3:134|135|(9:137|138|139|140|(1:142)|71|72|(3:74|75|76)|130))|70|71|72|(0)|130)|157|130)(1:161)|78|(3:103|104|(1:106))|80|(4:82|(1:84)|85|86)(2:88|(4:90|(1:92)|93|94)(2:95|(2:97|(2:99|100)(1:101))(1:102)))|87)(1:162)|77|78|(0)|80|(0)(0)|87))(1:166)|34|35|(0)(0)|38|(0)(0)|41|(0)|46|(0)|49|(0)|52|(0)(0)|77|78|(0)|80|(0)(0)|87|16|17)|168|169|170|171|(9:173|(6:176|177|178|(2:180|181)(1:183)|182|174)|189|190|(6:194|(2:195|(3:197|(2:199|(2:201|202)(1:269))(2:271|272)|270)(2:273|274))|(2:208|(12:210|211|212|213|(1:215)|216|217|218|(2:222|(7:228|(2:232|(7:234|235|236|237|(1:239)|240|241))|257|237|(0)|240|241))|258|240|241)(2:267|268))(2:204|205)|206|191|192)|275|276|277|278)(2:285|286)|279)(1:311)|110|111|112|(1:117)|114|115)|312|313|314|315|(7:386|387|(4:390|(4:395|(2:397|(1:399))(2:405|(1:407))|400|(2:402|403)(1:404))(2:392|393)|394|388)|408|409|(5:411|(3:415|412|413)|416|417|418)(1:426)|419)(1:317)|318|319|320|(6:322|323|324|325|326|327)(1:380)|328|329|(1:331)|332|(1:334)|335|(5:337|(4:340|(4:343|(2:347|348)|349|341)|352|338)|353|354|(9:356|357|358|359|360|361|(1:363)|114|115))|368|358|359|360|361|(0)|114|115) */
    /* JADX WARN: Can't wrap try/catch for region: R(31:0|1|2|3|4|(10:5|6|7|(13:9|10|(1:12)(1:305)|13|14|15|(25:19|(3:21|(2:23|(1:25)(1:26))|(1:28))(1:167)|29|(2:31|(1:33)(19:165|35|(1:37)(1:164)|38|(1:40)(1:163)|41|(2:43|(1:45))|46|(1:48)|49|(1:51)|52|(7:54|(8:56|(1:58)|59|60|61|(10:65|(3:149|150|(1:152))|67|68|(3:134|135|(9:137|138|139|140|(1:142)|71|72|(3:74|75|76)|130))|70|71|72|(0)|130)|157|130)(1:161)|78|(3:103|104|(1:106))|80|(4:82|(1:84)|85|86)(2:88|(4:90|(1:92)|93|94)(2:95|(2:97|(2:99|100)(1:101))(1:102)))|87)(1:162)|77|78|(0)|80|(0)(0)|87))(1:166)|34|35|(0)(0)|38|(0)(0)|41|(0)|46|(0)|49|(0)|52|(0)(0)|77|78|(0)|80|(0)(0)|87|16|17)|168|169|170|171|(9:173|(6:176|177|178|(2:180|181)(1:183)|182|174)|189|190|(6:194|(2:195|(3:197|(2:199|(2:201|202)(1:269))(2:271|272)|270)(2:273|274))|(2:208|(12:210|211|212|213|(1:215)|216|217|218|(2:222|(7:228|(2:232|(7:234|235|236|237|(1:239)|240|241))|257|237|(0)|240|241))|258|240|241)(2:267|268))(2:204|205)|206|191|192)|275|276|277|278)(2:285|286)|279)(1:311)|110|111|112|(1:117)|114|115)|312|313|314|315|(7:386|387|(4:390|(4:395|(2:397|(1:399))(2:405|(1:407))|400|(2:402|403)(1:404))(2:392|393)|394|388)|408|409|(5:411|(3:415|412|413)|416|417|418)(1:426)|419)(1:317)|318|319|320|(6:322|323|324|325|326|327)(1:380)|328|329|(1:331)|332|(1:334)|335|(5:337|(4:340|(4:343|(2:347|348)|349|341)|352|338)|353|354|(9:356|357|358|359|360|361|(1:363)|114|115))|368|358|359|360|361|(0)|114|115|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0231, code lost:
    
        if ((r2 instanceof org.telegram.tgnet.TLRPC$TL_messageActionGameScore) == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x06d2, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x06d4, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:370:0x06d5, code lost:
    
        r3 = r13;
        r5 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x06dc, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x06e6, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x06e7, code lost:
    
        r3 = r13;
        r5 = r15;
        r4 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x06f6, code lost:
    
        r1 = r0;
        r2 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:435:0x06e3, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x073d  */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x0497 A[Catch: Exception -> 0x049b, all -> 0x04aa, TRY_LEAVE, TryCatch #3 {all -> 0x04aa, blocks: (B:213:0x041d, B:215:0x043b, B:216:0x043d, B:218:0x044f, B:220:0x0453, B:222:0x0457, B:224:0x045d, B:226:0x0461, B:228:0x0465, B:230:0x046c, B:232:0x0472, B:236:0x048e, B:237:0x0493, B:239:0x0497), top: B:212:0x041d }] */
    /* JADX WARN: Removed duplicated region for block: B:363:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0183 A[Catch: all -> 0x051f, Exception -> 0x0526, TryCatch #18 {Exception -> 0x0526, blocks: (B:17:0x00af, B:19:0x00b5, B:21:0x00c2, B:23:0x00cf, B:25:0x00d5, B:26:0x00e4, B:28:0x00f3, B:29:0x0104, B:31:0x0125, B:35:0x0135, B:38:0x0154, B:41:0x016b, B:43:0x0183, B:45:0x018b, B:46:0x0190, B:48:0x01aa, B:49:0x01ba, B:51:0x01c9, B:52:0x01d0, B:54:0x01d7, B:56:0x01e2, B:58:0x0206, B:59:0x0208, B:167:0x00ff), top: B:16:0x00af }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01aa A[Catch: all -> 0x051f, Exception -> 0x0526, TryCatch #18 {Exception -> 0x0526, blocks: (B:17:0x00af, B:19:0x00b5, B:21:0x00c2, B:23:0x00cf, B:25:0x00d5, B:26:0x00e4, B:28:0x00f3, B:29:0x0104, B:31:0x0125, B:35:0x0135, B:38:0x0154, B:41:0x016b, B:43:0x0183, B:45:0x018b, B:46:0x0190, B:48:0x01aa, B:49:0x01ba, B:51:0x01c9, B:52:0x01d0, B:54:0x01d7, B:56:0x01e2, B:58:0x0206, B:59:0x0208, B:167:0x00ff), top: B:16:0x00af }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01c9 A[Catch: all -> 0x051f, Exception -> 0x0526, TryCatch #18 {Exception -> 0x0526, blocks: (B:17:0x00af, B:19:0x00b5, B:21:0x00c2, B:23:0x00cf, B:25:0x00d5, B:26:0x00e4, B:28:0x00f3, B:29:0x0104, B:31:0x0125, B:35:0x0135, B:38:0x0154, B:41:0x016b, B:43:0x0183, B:45:0x018b, B:46:0x0190, B:48:0x01aa, B:49:0x01ba, B:51:0x01c9, B:52:0x01d0, B:54:0x01d7, B:56:0x01e2, B:58:0x0206, B:59:0x0208, B:167:0x00ff), top: B:16:0x00af }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01d7 A[Catch: all -> 0x051f, Exception -> 0x0526, TryCatch #18 {Exception -> 0x0526, blocks: (B:17:0x00af, B:19:0x00b5, B:21:0x00c2, B:23:0x00cf, B:25:0x00d5, B:26:0x00e4, B:28:0x00f3, B:29:0x0104, B:31:0x0125, B:35:0x0135, B:38:0x0154, B:41:0x016b, B:43:0x0183, B:45:0x018b, B:46:0x0190, B:48:0x01aa, B:49:0x01ba, B:51:0x01c9, B:52:0x01d0, B:54:0x01d7, B:56:0x01e2, B:58:0x0206, B:59:0x0208, B:167:0x00ff), top: B:16:0x00af }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x028a  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02e0 A[Catch: all -> 0x0513, Exception -> 0x0519, TryCatch #41 {Exception -> 0x0519, all -> 0x0513, blocks: (B:78:0x02cb, B:104:0x02d1, B:106:0x02d7, B:80:0x02da, B:82:0x02e0, B:84:0x02f0, B:88:0x02fa, B:90:0x0302, B:92:0x030c, B:93:0x0313, B:95:0x031d, B:97:0x0325, B:99:0x0330, B:129:0x02ab, B:161:0x02af, B:169:0x034b), top: B:103:0x02d1 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02fa A[Catch: all -> 0x0513, Exception -> 0x0519, TryCatch #41 {Exception -> 0x0519, all -> 0x0513, blocks: (B:78:0x02cb, B:104:0x02d1, B:106:0x02d7, B:80:0x02da, B:82:0x02e0, B:84:0x02f0, B:88:0x02fa, B:90:0x0302, B:92:0x030c, B:93:0x0313, B:95:0x031d, B:97:0x0325, B:99:0x0330, B:129:0x02ab, B:161:0x02af, B:169:0x034b), top: B:103:0x02d1 }] */
    /* JADX WARN: Type inference failed for: r7v31, types: [org.telegram.tgnet.TLRPC$TL_dialog] */
    /* JADX WARN: Type inference failed for: r7v32, types: [java.lang.Object, org.telegram.tgnet.TLRPC$Dialog] */
    /* JADX WARN: Type inference failed for: r7v36, types: [org.telegram.tgnet.TLRPC$TL_dialogFolder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getDialogs$194(int r34, int r35, int r36, long[] r37) {
        /*
            Method dump skipped, instructions count: 1857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getDialogs$194(int, int, int, long[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogs$193(LongSparseArray longSparseArray) {
        MediaDataController mediaDataController = getMediaDataController();
        mediaDataController.clearDraftsFolderIds();
        if (longSparseArray != null) {
            int size = longSparseArray.size();
            for (int i = 0; i < size; i++) {
                mediaDataController.setDraftFolderId(longSparseArray.keyAt(i), ((Integer) longSparseArray.valueAt(i)).intValue());
            }
        }
    }

    public static void createFirstHoles(long j, SQLitePreparedStatement sQLitePreparedStatement, SQLitePreparedStatement sQLitePreparedStatement2, int i, int i2) throws Exception {
        int i3;
        int i4;
        sQLitePreparedStatement.requery();
        sQLitePreparedStatement.bindLong(1, j);
        if (i2 != 0) {
            sQLitePreparedStatement.bindInteger(2, i2);
            i3 = 3;
        } else {
            i3 = 2;
        }
        int i5 = i3 + 1;
        sQLitePreparedStatement.bindInteger(i3, i == 1 ? 1 : 0);
        sQLitePreparedStatement.bindInteger(i5, i);
        sQLitePreparedStatement.step();
        for (int i6 = 0; i6 < 8; i6++) {
            sQLitePreparedStatement2.requery();
            sQLitePreparedStatement2.bindLong(1, j);
            if (i2 != 0) {
                sQLitePreparedStatement2.bindInteger(2, i2);
                i4 = 3;
            } else {
                i4 = 2;
            }
            int i7 = i4 + 1;
            sQLitePreparedStatement2.bindInteger(i4, i6);
            int i8 = i7 + 1;
            sQLitePreparedStatement2.bindInteger(i7, i == 1 ? 1 : 0);
            sQLitePreparedStatement2.bindInteger(i8, i);
            sQLitePreparedStatement2.step();
        }
    }

    public void updateDialogData(final TLRPC$Dialog tLRPC$Dialog) {
        if (tLRPC$Dialog == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda176
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateDialogData$195(tLRPC$Dialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateDialogData$195(org.telegram.tgnet.TLRPC$Dialog r8) {
        /*
            r7 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r7.database     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r2.<init>()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.String r3 = "SELECT data FROM dialogs WHERE did = "
            r2.append(r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            long r3 = r8.id     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r2.append(r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            org.telegram.SQLite.SQLiteCursor r1 = r1.queryFinalized(r2, r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            boolean r2 = r1.next()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L5a
            if (r2 != 0) goto L27
            r1.dispose()
            return
        L27:
            org.telegram.SQLite.SQLiteDatabase r2 = r7.database     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L5a
            java.lang.String r3 = "UPDATE dialogs SET data = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L5a
            org.telegram.tgnet.NativeByteBuffer r3 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            int r4 = r8.getObjectSize()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r8.serializeToStream(r3)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r4 = 1
            r2.bindByteBuffer(r4, r3)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r4 = 2
            long r5 = r8.id     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r2.bindLong(r4, r5)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r2.step()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r2.dispose()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54
            r3.reuse()     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L5a
            r1.dispose()
            goto L70
        L52:
            r8 = move-exception
            goto L58
        L54:
            r8 = move-exception
            goto L5c
        L56:
            r8 = move-exception
            r2 = r0
        L58:
            r0 = r1
            goto L72
        L5a:
            r8 = move-exception
            r2 = r0
        L5c:
            r0 = r1
            goto L63
        L5e:
            r8 = move-exception
            r2 = r0
            goto L72
        L61:
            r8 = move-exception
            r2 = r0
        L63:
            r7.checkSQLException(r8)     // Catch: java.lang.Throwable -> L71
            if (r0 == 0) goto L6b
            r0.dispose()
        L6b:
            if (r2 == 0) goto L70
            r2.dispose()
        L70:
            return
        L71:
            r8 = move-exception
        L72:
            if (r0 == 0) goto L77
            r0.dispose()
        L77:
            if (r2 == 0) goto L7c
            r2.dispose()
        L7c:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogData$195(org.telegram.tgnet.TLRPC$Dialog):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:315:0x018a, code lost:
    
        if (r14 < 0) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x049e  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x04ca A[Catch: all -> 0x0498, Exception -> 0x049b, TRY_ENTER, TRY_LEAVE, TryCatch #37 {Exception -> 0x049b, all -> 0x0498, blocks: (B:158:0x047f, B:128:0x04ab, B:130:0x04b1, B:133:0x04ca), top: B:157:0x047f }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x04d1 A[Catch: all -> 0x0500, Exception -> 0x0505, TRY_LEAVE, TryCatch #41 {Exception -> 0x0505, all -> 0x0500, blocks: (B:122:0x0427, B:126:0x04a4, B:131:0x04be, B:134:0x04cd, B:136:0x04d1, B:156:0x04bb, B:125:0x04a0), top: B:121:0x0427 }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x04f0  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x047f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x019e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0241 A[Catch: all -> 0x0222, Exception -> 0x0229, TRY_ENTER, TRY_LEAVE, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0275 A[Catch: all -> 0x0222, Exception -> 0x0229, TRY_ENTER, TRY_LEAVE, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x02bb A[Catch: all -> 0x0222, Exception -> 0x0229, TRY_ENTER, TRY_LEAVE, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:212:0x02d1 A[Catch: all -> 0x0222, Exception -> 0x0229, TRY_ENTER, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x02f7 A[Catch: all -> 0x0222, Exception -> 0x0229, TRY_LEAVE, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:220:0x0307 A[Catch: all -> 0x0222, Exception -> 0x0229, TryCatch #63 {Exception -> 0x0229, all -> 0x0222, blocks: (B:275:0x021b, B:187:0x0241, B:198:0x0275, B:202:0x0297, B:205:0x029e, B:209:0x02bb, B:212:0x02d1, B:214:0x02f7, B:217:0x0301, B:220:0x0307, B:221:0x030f, B:263:0x029c), top: B:274:0x021b }] */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x03b0 A[Catch: all -> 0x03d2, Exception -> 0x03da, TRY_LEAVE, TryCatch #56 {Exception -> 0x03da, all -> 0x03d2, blocks: (B:245:0x0396, B:239:0x03b0), top: B:244:0x0396 }] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x037d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x02a4  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0254  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x06b6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x06c0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x06c5  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x06ca  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x06d4  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x06d9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x06de  */
    /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x06ed  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x06f2  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x06f7  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x06fc  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0701  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0706  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x070b  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0710  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0715  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x071a  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x071f  */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void putDialogsInternal(org.telegram.tgnet.TLRPC$messages_Dialogs r32, int r33) {
        /*
            Method dump skipped, instructions count: 1827
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.putDialogsInternal(org.telegram.tgnet.TLRPC$messages_Dialogs, int):void");
    }

    public void getDialogFolderId(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda111
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDialogFolderId$197(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogFolderId$197(long j, final IntCallback intCallback) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (this.unknownDialogsIds.get(j) == null) {
                    sQLiteCursor = this.database.queryFinalized("SELECT folder_id FROM dialogs WHERE did = ?", Long.valueOf(j));
                    r2 = sQLiteCursor.next() ? sQLiteCursor.intValue(0) : -1;
                    sQLiteCursor.dispose();
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        MessagesStorage.IntCallback.this.run(r2);
                    }
                });
                if (sQLiteCursor == null) {
                    return;
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor == null) {
                    return;
                }
            }
            sQLiteCursor.dispose();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    public void setDialogsFolderId(final ArrayList<TLRPC$TL_folderPeer> arrayList, final ArrayList<TLRPC$TL_inputFolderPeer> arrayList2, final long j, final int i) {
        if (arrayList == null && arrayList2 == null && j == 0) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda158
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogsFolderId$198(arrayList, arrayList2, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogsFolderId$198(ArrayList arrayList, ArrayList arrayList2, int i, long j) {
        boolean z;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                SQLitePreparedStatement executeFast = this.database.executeFast("UPDATE dialogs SET folder_id = ?, pinned = ? WHERE did = ?");
                try {
                    if (arrayList != null) {
                        int size = arrayList.size();
                        z = false;
                        for (int i2 = 0; i2 < size; i2++) {
                            TLRPC$TL_folderPeer tLRPC$TL_folderPeer = (TLRPC$TL_folderPeer) arrayList.get(i2);
                            long peerDialogId = DialogObject.getPeerDialogId(tLRPC$TL_folderPeer.peer);
                            executeFast.requery();
                            executeFast.bindInteger(1, tLRPC$TL_folderPeer.folder_id);
                            if (tLRPC$TL_folderPeer.folder_id == 1) {
                                z = true;
                            }
                            executeFast.bindInteger(2, 0);
                            executeFast.bindLong(3, peerDialogId);
                            executeFast.step();
                            this.unknownDialogsIds.remove(peerDialogId);
                        }
                    } else if (arrayList2 != null) {
                        int size2 = arrayList2.size();
                        z = false;
                        for (int i3 = 0; i3 < size2; i3++) {
                            TLRPC$TL_inputFolderPeer tLRPC$TL_inputFolderPeer = (TLRPC$TL_inputFolderPeer) arrayList2.get(i3);
                            long peerDialogId2 = DialogObject.getPeerDialogId(tLRPC$TL_inputFolderPeer.peer);
                            executeFast.requery();
                            executeFast.bindInteger(1, tLRPC$TL_inputFolderPeer.folder_id);
                            if (tLRPC$TL_inputFolderPeer.folder_id == 1) {
                                z = true;
                            }
                            executeFast.bindInteger(2, 0);
                            executeFast.bindLong(3, peerDialogId2);
                            executeFast.step();
                            this.unknownDialogsIds.remove(peerDialogId2);
                        }
                    } else {
                        executeFast.requery();
                        executeFast.bindInteger(1, i);
                        boolean z2 = i == 1;
                        executeFast.bindInteger(2, 0);
                        executeFast.bindLong(3, j);
                        executeFast.step();
                        z = z2;
                    }
                    executeFast.dispose();
                    this.database.commitTransaction();
                    if (!z) {
                        lambda$checkIfFolderEmpty$200(1);
                    }
                    resetAllUnreadCounters(false);
                    SQLiteDatabase sQLiteDatabase = this.database;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.commitTransaction();
                    }
                } catch (Exception e) {
                    e = e;
                    sQLitePreparedStatement = executeFast;
                    checkSQLException(e);
                    SQLiteDatabase sQLiteDatabase2 = this.database;
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                } catch (Throwable th) {
                    th = th;
                    sQLitePreparedStatement = executeFast;
                    SQLiteDatabase sQLiteDatabase3 = this.database;
                    if (sQLiteDatabase3 != null) {
                        sQLiteDatabase3.commitTransaction();
                    }
                    if (sQLitePreparedStatement != null) {
                        sQLitePreparedStatement.dispose();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: checkIfFolderEmptyInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$checkIfFolderEmpty$200(final int i) {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                boolean z = true;
                sQLiteCursor = this.database.queryFinalized("SELECT did FROM dialogs WHERE folder_id = ?", Integer.valueOf(i));
                while (sQLiteCursor.next()) {
                    long longValue = sQLiteCursor.longValue(0);
                    if (!DialogObject.isUserDialog(longValue) && !DialogObject.isEncryptedDialog(longValue)) {
                        TLRPC$Chat chat = getChat(-longValue);
                        if (ChatObject.isNotInChat(chat) || chat.migrated_to != null) {
                        }
                    }
                    z = false;
                }
                sQLiteCursor.dispose();
                if (z) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda31
                        @Override // java.lang.Runnable
                        public final void run() {
                            MessagesStorage.this.lambda$checkIfFolderEmptyInternal$199(i);
                        }
                    });
                    this.database.executeFast("DELETE FROM dialogs WHERE did = " + DialogObject.makeFolderDialogId(i)).stepThis().dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor == null) {
                    return;
                }
            }
            sQLiteCursor.dispose();
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkIfFolderEmptyInternal$199(int i) {
        getMessagesController().onFolderEmpty(i);
    }

    public void checkIfFolderEmpty(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$checkIfFolderEmpty$200(i);
            }
        });
    }

    public void unpinAllDialogsExceptNew(final ArrayList<Long> arrayList, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda151
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$unpinAllDialogsExceptNew$201(arrayList, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$unpinAllDialogsExceptNew$201(java.util.ArrayList r10, int r11) {
        /*
            r9 = this;
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r1.<init>()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            org.telegram.SQLite.SQLiteDatabase r2 = r9.database     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r4 = "SELECT did, folder_id FROM dialogs WHERE pinned > 0 AND did NOT IN (%s)"
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r7 = ","
            java.lang.String r10 = android.text.TextUtils.join(r7, r10)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r7 = 0
            r6[r7] = r10     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r10 = java.lang.String.format(r3, r4, r6)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            org.telegram.SQLite.SQLiteCursor r10 = r2.queryFinalized(r10, r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
        L22:
            boolean r2 = r10.next()     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            if (r2 == 0) goto L4a
            long r2 = r10.longValue(r7)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            int r4 = r10.intValue(r5)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            if (r4 != r11) goto L22
            boolean r4 = org.telegram.messenger.DialogObject.isEncryptedDialog(r2)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            if (r4 != 0) goto L22
            boolean r2 = org.telegram.messenger.DialogObject.isFolderDialogId(r2)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            if (r2 != 0) goto L22
            long r2 = r10.longValue(r7)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            r1.add(r2)     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            goto L22
        L4a:
            r10.dispose()     // Catch: java.lang.Throwable -> L82 java.lang.Exception -> L87
            boolean r10 = r1.isEmpty()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            if (r10 != 0) goto L9e
            org.telegram.SQLite.SQLiteDatabase r10 = r9.database     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            java.lang.String r11 = "UPDATE dialogs SET pinned = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r10 = r10.executeFast(r11)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L8f
            r11 = 0
        L5c:
            int r2 = r1.size()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            if (r11 >= r2) goto L7c
            java.lang.Object r2 = r1.get(r11)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            java.lang.Long r2 = (java.lang.Long) r2     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            long r2 = r2.longValue()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            r10.requery()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            r10.bindInteger(r5, r7)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            r4 = 2
            r10.bindLong(r4, r2)     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            r10.step()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            int r11 = r11 + 1
            goto L5c
        L7c:
            r10.dispose()     // Catch: java.lang.Exception -> L80 java.lang.Throwable -> L9f
            goto L9e
        L80:
            r11 = move-exception
            goto L91
        L82:
            r11 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto La0
        L87:
            r11 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto L91
        L8c:
            r11 = move-exception
            r10 = r0
            goto La0
        L8f:
            r11 = move-exception
            r10 = r0
        L91:
            r9.checkSQLException(r11)     // Catch: java.lang.Throwable -> L9f
            if (r0 == 0) goto L99
            r0.dispose()
        L99:
            if (r10 == 0) goto L9e
            r10.dispose()
        L9e:
            return
        L9f:
            r11 = move-exception
        La0:
            if (r0 == 0) goto La5
            r0.dispose()
        La5:
            if (r10 == 0) goto Laa
            r10.dispose()
        Laa:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$unpinAllDialogsExceptNew$201(java.util.ArrayList, int):void");
    }

    public void setDialogUnread(final long j, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda122
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogUnread$202(j, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0043 A[Catch: all -> 0x003b, Exception -> 0x003d, TryCatch #2 {Exception -> 0x003d, blocks: (B:9:0x0027, B:12:0x0043, B:13:0x0048, B:17:0x0046, B:23:0x0037, B:28:0x0064, B:29:0x0067), top: B:2:0x0002, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0046 A[Catch: all -> 0x003b, Exception -> 0x003d, TryCatch #2 {Exception -> 0x003d, blocks: (B:9:0x0027, B:12:0x0043, B:13:0x0048, B:17:0x0046, B:23:0x0037, B:28:0x0064, B:29:0x0067), top: B:2:0x0002, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0064 A[Catch: all -> 0x003b, Exception -> 0x003d, TryCatch #2 {Exception -> 0x003d, blocks: (B:9:0x0027, B:12:0x0043, B:13:0x0048, B:17:0x0046, B:23:0x0037, B:28:0x0064, B:29:0x0067), top: B:2:0x0002, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$setDialogUnread$202(long r6, boolean r8) {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r5.database     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            r3.<init>()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            java.lang.String r4 = "SELECT flags FROM dialogs WHERE did = "
            r3.append(r4)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            r3.append(r6)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            org.telegram.SQLite.SQLiteCursor r2 = r2.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L30
            boolean r3 = r2.next()     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L61
            if (r3 == 0) goto L26
            int r3 = r2.intValue(r1)     // Catch: java.lang.Exception -> L2b java.lang.Throwable -> L61
            goto L27
        L26:
            r3 = 0
        L27:
            r2.dispose()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            goto L40
        L2b:
            r3 = move-exception
            goto L32
        L2d:
            r6 = move-exception
            r2 = r0
            goto L62
        L30:
            r3 = move-exception
            r2 = r0
        L32:
            r5.checkSQLException(r3)     // Catch: java.lang.Throwable -> L61
            if (r2 == 0) goto L3f
            r2.dispose()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            goto L3f
        L3b:
            r6 = move-exception
            goto L71
        L3d:
            r6 = move-exception
            goto L68
        L3f:
            r3 = 0
        L40:
            r2 = 1
            if (r8 == 0) goto L46
            r8 = r3 | 1
            goto L48
        L46:
            r8 = r3 & (-2)
        L48:
            org.telegram.SQLite.SQLiteDatabase r3 = r5.database     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            java.lang.String r4 = "UPDATE dialogs SET flags = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r3.executeFast(r4)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            r0.bindInteger(r2, r8)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            r8 = 2
            r0.bindLong(r8, r6)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            r0.step()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            r0.dispose()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            r5.resetAllUnreadCounters(r1)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
            goto L6d
        L61:
            r6 = move-exception
        L62:
            if (r2 == 0) goto L67
            r2.dispose()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
        L67:
            throw r6     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d
        L68:
            r5.checkSQLException(r6)     // Catch: java.lang.Throwable -> L3b
            if (r0 == 0) goto L70
        L6d:
            r0.dispose()
        L70:
            return
        L71:
            if (r0 == 0) goto L76
            r0.dispose()
        L76:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$setDialogUnread$202(long, boolean):void");
    }

    public void resetAllUnreadCounters(boolean z) {
        int size = this.dialogFilters.size();
        for (int i = 0; i < size; i++) {
            MessagesController.DialogFilter dialogFilter = this.dialogFilters.get(i);
            if (z) {
                if ((dialogFilter.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED) != 0) {
                    dialogFilter.pendingUnreadCount = -1;
                }
            } else {
                dialogFilter.pendingUnreadCount = -1;
            }
        }
        calcUnreadCounters(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$resetAllUnreadCounters$203();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetAllUnreadCounters$203() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
        getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE));
    }

    public void setDialogPinned(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogPinned$204(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogPinned$204(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                sQLitePreparedStatement.bindInteger(1, i);
                sQLitePreparedStatement.bindLong(2, j);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void setDialogsPinned(final ArrayList<Long> arrayList, final ArrayList<Integer> arrayList2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda157
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$setDialogsPinned$205(arrayList, arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogsPinned$205(ArrayList arrayList, ArrayList arrayList2) {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    sQLitePreparedStatement.requery();
                    sQLitePreparedStatement.bindInteger(1, ((Integer) arrayList2.get(i)).intValue());
                    sQLitePreparedStatement.bindLong(2, ((Long) arrayList.get(i)).longValue());
                    sQLitePreparedStatement.step();
                }
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatement != null) {
                    sQLitePreparedStatement.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void putDialogs(final TLRPC$messages_Dialogs tLRPC$messages_Dialogs, final int i) {
        if (tLRPC$messages_Dialogs.dialogs.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda194
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$putDialogs$206(tLRPC$messages_Dialogs, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putDialogs$206(TLRPC$messages_Dialogs tLRPC$messages_Dialogs, int i) {
        putDialogsInternal(tLRPC$messages_Dialogs, i);
        try {
            loadUnreadMessages();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getDialogMaxMessageId(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda112
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDialogMaxMessageId$208(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0031, code lost:
    
        if (r1 == null) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getDialogMaxMessageId$208(long r6, final org.telegram.messenger.MessagesStorage.IntCallback r8) {
        /*
            r5 = this;
            r0 = 1
            int[] r0 = new int[r0]
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r5.database     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r3.<init>()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r4 = "SELECT MAX(mid) FROM messages_v2 WHERE uid = "
            r3.append(r4)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r3.append(r6)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            java.lang.String r6 = r3.toString()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r7 = 0
            java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            org.telegram.SQLite.SQLiteCursor r1 = r2.queryFinalized(r6, r3)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            boolean r6 = r1.next()     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            if (r6 == 0) goto L33
            int r6 = r1.intValue(r7)     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            r0[r7] = r6     // Catch: java.lang.Throwable -> L2b java.lang.Exception -> L2d
            goto L33
        L2b:
            r6 = move-exception
            goto L3f
        L2d:
            r6 = move-exception
            r5.checkSQLException(r6)     // Catch: java.lang.Throwable -> L2b
            if (r1 == 0) goto L36
        L33:
            r1.dispose()
        L36:
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda5 r6 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda5
            r6.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r6)
            return
        L3f:
            if (r1 == 0) goto L44
            r1.dispose()
        L44:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getDialogMaxMessageId$208(long, org.telegram.messenger.MessagesStorage$IntCallback):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDialogMaxMessageId$207(IntCallback intCallback, int[] iArr) {
        intCallback.run(iArr[0]);
    }

    public int getDialogReadMax(final boolean z, final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Integer[] numArr = {0};
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda205
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getDialogReadMax$209(z, j, numArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return numArr[0].intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006c, code lost:
    
        if (r1 == null) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getDialogReadMax$209(boolean r5, long r6, java.lang.Integer[] r8, java.util.concurrent.CountDownLatch r9) {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            if (r5 == 0) goto L2e
            org.telegram.SQLite.SQLiteDatabase r5 = r4.database     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r2.<init>()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.String r3 = "SELECT outbox_max FROM dialogs WHERE did = "
            r2.append(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r2.append(r6)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            org.telegram.SQLite.SQLiteCursor r1 = r5.queryFinalized(r6, r7)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            boolean r5 = r1.next()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r5 == 0) goto L6e
            int r5 = r1.intValue(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r8[r0] = r5     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            goto L6e
        L2e:
            org.telegram.SQLite.SQLiteDatabase r5 = r4.database     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r2.<init>()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.String r3 = "SELECT last_mid, inbox_max FROM dialogs WHERE did = "
            r2.append(r3)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r2.append(r6)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.String r6 = r2.toString()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            org.telegram.SQLite.SQLiteCursor r1 = r5.queryFinalized(r6, r7)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            boolean r5 = r1.next()     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r5 == 0) goto L6e
            int r5 = r1.intValue(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r6 = 1
            int r6 = r1.intValue(r6)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            if (r6 <= r5) goto L5f
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r8[r0] = r5     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            goto L6e
        L5f:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            r8[r0] = r5     // Catch: java.lang.Throwable -> L66 java.lang.Exception -> L68
            goto L6e
        L66:
            r5 = move-exception
            goto L75
        L68:
            r5 = move-exception
            r4.checkSQLException(r5)     // Catch: java.lang.Throwable -> L66
            if (r1 == 0) goto L71
        L6e:
            r1.dispose()
        L71:
            r9.countDown()
            return
        L75:
            if (r1 == 0) goto L7a
            r1.dispose()
        L7a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getDialogReadMax$209(boolean, long, java.lang.Integer[], java.util.concurrent.CountDownLatch):void");
    }

    public int getChannelPtsSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Integer[] numArr = {0};
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda125
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getChannelPtsSync$210(j, numArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return numArr[0].intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0033, code lost:
    
        if (r0 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$getChannelPtsSync$210(long r5, java.lang.Integer[] r7, java.util.concurrent.CountDownLatch r8) {
        /*
            r4 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r4.database     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            r2.<init>()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            java.lang.String r3 = "SELECT pts FROM dialogs WHERE did = "
            r2.append(r3)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            long r5 = -r5
            r2.append(r5)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            r6 = 0
            java.lang.Object[] r2 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            org.telegram.SQLite.SQLiteCursor r0 = r1.queryFinalized(r5, r2)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            boolean r5 = r0.next()     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            if (r5 == 0) goto L35
            int r5 = r0.intValue(r6)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            r7[r6] = r5     // Catch: java.lang.Throwable -> L2d java.lang.Exception -> L2f
            goto L35
        L2d:
            r5 = move-exception
            goto L41
        L2f:
            r5 = move-exception
            r4.checkSQLException(r5)     // Catch: java.lang.Throwable -> L2d
            if (r0 == 0) goto L38
        L35:
            r0.dispose()
        L38:
            r8.countDown()     // Catch: java.lang.Exception -> L3c
            goto L40
        L3c:
            r5 = move-exception
            r4.checkSQLException(r5)
        L40:
            return
        L41:
            if (r0 == 0) goto L46
            r0.dispose()
        L46:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getChannelPtsSync$210(long, java.lang.Integer[], java.util.concurrent.CountDownLatch):void");
    }

    public TLRPC$User getUserSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final TLRPC$User[] tLRPC$UserArr = new TLRPC$User[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda210
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getUserSync$211(tLRPC$UserArr, j, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return tLRPC$UserArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUserSync$211(TLRPC$User[] tLRPC$UserArr, long j, CountDownLatch countDownLatch) {
        tLRPC$UserArr[0] = getUser(j);
        countDownLatch.countDown();
    }

    public TLRPC$Chat getChatSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final TLRPC$Chat[] tLRPC$ChatArr = new TLRPC$Chat[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda208
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$getChatSync$212(tLRPC$ChatArr, j, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return tLRPC$ChatArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChatSync$212(TLRPC$Chat[] tLRPC$ChatArr, long j, CountDownLatch countDownLatch) {
        tLRPC$ChatArr[0] = getChat(j);
        countDownLatch.countDown();
    }

    public TLRPC$User getUser(long j) {
        try {
            ArrayList<TLRPC$User> arrayList = new ArrayList<>();
            getUsersInternal("" + j, arrayList);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    public ArrayList<TLRPC$User> getUsers(ArrayList<Long> arrayList) {
        ArrayList<TLRPC$User> arrayList2 = new ArrayList<>();
        try {
            getUsersInternal(TextUtils.join(",", arrayList), arrayList2);
        } catch (Exception e) {
            arrayList2.clear();
            checkSQLException(e);
        }
        return arrayList2;
    }

    public TLRPC$Chat getChat(long j) {
        try {
            ArrayList<TLRPC$Chat> arrayList = new ArrayList<>();
            getChatsInternal("" + j, arrayList);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    public TLRPC$EncryptedChat getEncryptedChat(long j) {
        try {
            ArrayList<TLRPC$EncryptedChat> arrayList = new ArrayList<>();
            getEncryptedChatsInternal("" + j, arrayList, null);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0173, code lost:
    
        if (r11.startsWith(r6) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02d8, code lost:
    
        r6 = (org.telegram.ui.Adapters.DialogsSearchAdapter.DialogSearchResult) r9.get(r14.id);
        r7 = r14.status;
        r26 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x02e4, code lost:
    
        if (r7 == null) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02e6, code lost:
    
        r7.expires = r1.intValue(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02ee, code lost:
    
        if (r10 != 1) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02f0, code lost:
    
        r6.name = org.telegram.messenger.AndroidUtilities.generateSearchName(r14.first_name, r14.last_name, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0324, code lost:
    
        r6.object = r14;
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02fb, code lost:
    
        r6.name = org.telegram.messenger.AndroidUtilities.generateSearchName("@" + org.telegram.messenger.UserObject.getPublicUsername(r14), null, "@" + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x03e5, code lost:
    
        if (r3.contains(" " + r7) != false) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:424:0x0051, code lost:
    
        if (r15.length() == 0) goto L17;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01cf A[Catch: all -> 0x019e, Exception -> 0x01a3, TryCatch #7 {Exception -> 0x01a3, all -> 0x019e, blocks: (B:106:0x0182, B:109:0x01ae, B:111:0x01be, B:113:0x01cf, B:115:0x01e0), top: B:105:0x0182 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01e0 A[Catch: all -> 0x019e, Exception -> 0x01a3, TRY_LEAVE, TryCatch #7 {Exception -> 0x01a3, all -> 0x019e, blocks: (B:106:0x0182, B:109:0x01ae, B:111:0x01be, B:113:0x01cf, B:115:0x01e0), top: B:105:0x0182 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0208 A[Catch: all -> 0x00aa, Exception -> 0x00b0, TRY_ENTER, TRY_LEAVE, TryCatch #11 {Exception -> 0x00b0, all -> 0x00aa, blocks: (B:40:0x0090, B:102:0x016f, B:121:0x0208, B:186:0x0372, B:249:0x0476, B:311:0x066e, B:313:0x0678, B:315:0x067c, B:325:0x0692), top: B:39:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02ab A[Catch: all -> 0x07d6, Exception -> 0x07dc, TryCatch #9 {Exception -> 0x07dc, all -> 0x07d6, blocks: (B:43:0x00ca, B:45:0x00d0, B:48:0x00ee, B:54:0x00f9, B:56:0x00ff, B:72:0x0117, B:74:0x0121, B:83:0x012d, B:85:0x0138, B:92:0x0145, B:94:0x0153, B:98:0x0160, B:123:0x0228, B:125:0x022e, B:128:0x0242, B:130:0x0249, B:133:0x025a, B:135:0x0264, B:138:0x027d, B:140:0x0283, B:144:0x029b, B:148:0x02ab, B:150:0x02b6, B:153:0x02c9, B:168:0x0334, B:156:0x02d8, B:158:0x02e6, B:161:0x02f0, B:162:0x0324, B:165:0x02fb, B:183:0x0356, B:187:0x038e, B:189:0x0394, B:194:0x03ad, B:196:0x03b5, B:199:0x03cc, B:201:0x03d2, B:205:0x0427, B:206:0x03e7, B:208:0x03ee, B:211:0x03ff, B:217:0x0417, B:223:0x0421, B:227:0x042f, B:229:0x0433, B:231:0x0439, B:233:0x043f, B:244:0x0460, B:250:0x0494, B:252:0x049a, B:255:0x04ae, B:257:0x04b7, B:260:0x04c3, B:262:0x04cb, B:265:0x04e2, B:267:0x04e8, B:271:0x0500, B:277:0x050b, B:279:0x0512, B:280:0x0521, B:282:0x0527, B:285:0x0539, B:287:0x05c8, B:288:0x05ca, B:290:0x05d6, B:293:0x05e0, B:294:0x0632, B:297:0x0609, B:275:0x063c, B:306:0x0658, B:338:0x06cf, B:340:0x06d5, B:343:0x06e1, B:346:0x06f5, B:348:0x06fe, B:351:0x070b, B:353:0x0713, B:356:0x072a, B:358:0x0730, B:362:0x0748, B:368:0x0756, B:370:0x075f, B:372:0x076e, B:375:0x0778, B:376:0x07af, B:380:0x0785, B:366:0x07b4, B:391:0x07d0), top: B:42:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0372 A[Catch: all -> 0x00aa, Exception -> 0x00b0, TRY_ENTER, TRY_LEAVE, TryCatch #11 {Exception -> 0x00b0, all -> 0x00aa, blocks: (B:40:0x0090, B:102:0x016f, B:121:0x0208, B:186:0x0372, B:249:0x0476, B:311:0x066e, B:313:0x0678, B:315:0x067c, B:325:0x0692), top: B:39:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x07f4  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:275:0x063c A[Catch: all -> 0x07d6, Exception -> 0x07dc, LOOP:6: B:259:0x04c1->B:275:0x063c, LOOP_END, TryCatch #9 {Exception -> 0x07dc, all -> 0x07d6, blocks: (B:43:0x00ca, B:45:0x00d0, B:48:0x00ee, B:54:0x00f9, B:56:0x00ff, B:72:0x0117, B:74:0x0121, B:83:0x012d, B:85:0x0138, B:92:0x0145, B:94:0x0153, B:98:0x0160, B:123:0x0228, B:125:0x022e, B:128:0x0242, B:130:0x0249, B:133:0x025a, B:135:0x0264, B:138:0x027d, B:140:0x0283, B:144:0x029b, B:148:0x02ab, B:150:0x02b6, B:153:0x02c9, B:168:0x0334, B:156:0x02d8, B:158:0x02e6, B:161:0x02f0, B:162:0x0324, B:165:0x02fb, B:183:0x0356, B:187:0x038e, B:189:0x0394, B:194:0x03ad, B:196:0x03b5, B:199:0x03cc, B:201:0x03d2, B:205:0x0427, B:206:0x03e7, B:208:0x03ee, B:211:0x03ff, B:217:0x0417, B:223:0x0421, B:227:0x042f, B:229:0x0433, B:231:0x0439, B:233:0x043f, B:244:0x0460, B:250:0x0494, B:252:0x049a, B:255:0x04ae, B:257:0x04b7, B:260:0x04c3, B:262:0x04cb, B:265:0x04e2, B:267:0x04e8, B:271:0x0500, B:277:0x050b, B:279:0x0512, B:280:0x0521, B:282:0x0527, B:285:0x0539, B:287:0x05c8, B:288:0x05ca, B:290:0x05d6, B:293:0x05e0, B:294:0x0632, B:297:0x0609, B:275:0x063c, B:306:0x0658, B:338:0x06cf, B:340:0x06d5, B:343:0x06e1, B:346:0x06f5, B:348:0x06fe, B:351:0x070b, B:353:0x0713, B:356:0x072a, B:358:0x0730, B:362:0x0748, B:368:0x0756, B:370:0x075f, B:372:0x076e, B:375:0x0778, B:376:0x07af, B:380:0x0785, B:366:0x07b4, B:391:0x07d0), top: B:42:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x050b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x07fc  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0692 A[Catch: all -> 0x00aa, Exception -> 0x00b0, TRY_ENTER, TRY_LEAVE, TryCatch #11 {Exception -> 0x00b0, all -> 0x00aa, blocks: (B:40:0x0090, B:102:0x016f, B:121:0x0208, B:186:0x0372, B:249:0x0476, B:311:0x066e, B:313:0x0678, B:315:0x067c, B:325:0x0692), top: B:39:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x06b8  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x06d5 A[Catch: all -> 0x07d6, Exception -> 0x07dc, TryCatch #9 {Exception -> 0x07dc, all -> 0x07d6, blocks: (B:43:0x00ca, B:45:0x00d0, B:48:0x00ee, B:54:0x00f9, B:56:0x00ff, B:72:0x0117, B:74:0x0121, B:83:0x012d, B:85:0x0138, B:92:0x0145, B:94:0x0153, B:98:0x0160, B:123:0x0228, B:125:0x022e, B:128:0x0242, B:130:0x0249, B:133:0x025a, B:135:0x0264, B:138:0x027d, B:140:0x0283, B:144:0x029b, B:148:0x02ab, B:150:0x02b6, B:153:0x02c9, B:168:0x0334, B:156:0x02d8, B:158:0x02e6, B:161:0x02f0, B:162:0x0324, B:165:0x02fb, B:183:0x0356, B:187:0x038e, B:189:0x0394, B:194:0x03ad, B:196:0x03b5, B:199:0x03cc, B:201:0x03d2, B:205:0x0427, B:206:0x03e7, B:208:0x03ee, B:211:0x03ff, B:217:0x0417, B:223:0x0421, B:227:0x042f, B:229:0x0433, B:231:0x0439, B:233:0x043f, B:244:0x0460, B:250:0x0494, B:252:0x049a, B:255:0x04ae, B:257:0x04b7, B:260:0x04c3, B:262:0x04cb, B:265:0x04e2, B:267:0x04e8, B:271:0x0500, B:277:0x050b, B:279:0x0512, B:280:0x0521, B:282:0x0527, B:285:0x0539, B:287:0x05c8, B:288:0x05ca, B:290:0x05d6, B:293:0x05e0, B:294:0x0632, B:297:0x0609, B:275:0x063c, B:306:0x0658, B:338:0x06cf, B:340:0x06d5, B:343:0x06e1, B:346:0x06f5, B:348:0x06fe, B:351:0x070b, B:353:0x0713, B:356:0x072a, B:358:0x0730, B:362:0x0748, B:368:0x0756, B:370:0x075f, B:372:0x076e, B:375:0x0778, B:376:0x07af, B:380:0x0785, B:366:0x07b4, B:391:0x07d0), top: B:42:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:366:0x07b4 A[Catch: all -> 0x07d6, Exception -> 0x07dc, LOOP:10: B:350:0x0709->B:366:0x07b4, LOOP_END, TryCatch #9 {Exception -> 0x07dc, all -> 0x07d6, blocks: (B:43:0x00ca, B:45:0x00d0, B:48:0x00ee, B:54:0x00f9, B:56:0x00ff, B:72:0x0117, B:74:0x0121, B:83:0x012d, B:85:0x0138, B:92:0x0145, B:94:0x0153, B:98:0x0160, B:123:0x0228, B:125:0x022e, B:128:0x0242, B:130:0x0249, B:133:0x025a, B:135:0x0264, B:138:0x027d, B:140:0x0283, B:144:0x029b, B:148:0x02ab, B:150:0x02b6, B:153:0x02c9, B:168:0x0334, B:156:0x02d8, B:158:0x02e6, B:161:0x02f0, B:162:0x0324, B:165:0x02fb, B:183:0x0356, B:187:0x038e, B:189:0x0394, B:194:0x03ad, B:196:0x03b5, B:199:0x03cc, B:201:0x03d2, B:205:0x0427, B:206:0x03e7, B:208:0x03ee, B:211:0x03ff, B:217:0x0417, B:223:0x0421, B:227:0x042f, B:229:0x0433, B:231:0x0439, B:233:0x043f, B:244:0x0460, B:250:0x0494, B:252:0x049a, B:255:0x04ae, B:257:0x04b7, B:260:0x04c3, B:262:0x04cb, B:265:0x04e2, B:267:0x04e8, B:271:0x0500, B:277:0x050b, B:279:0x0512, B:280:0x0521, B:282:0x0527, B:285:0x0539, B:287:0x05c8, B:288:0x05ca, B:290:0x05d6, B:293:0x05e0, B:294:0x0632, B:297:0x0609, B:275:0x063c, B:306:0x0658, B:338:0x06cf, B:340:0x06d5, B:343:0x06e1, B:346:0x06f5, B:348:0x06fe, B:351:0x070b, B:353:0x0713, B:356:0x072a, B:358:0x0730, B:362:0x0748, B:368:0x0756, B:370:0x075f, B:372:0x076e, B:375:0x0778, B:376:0x07af, B:380:0x0785, B:366:0x07b4, B:391:0x07d0), top: B:42:0x00ca }] */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0756 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:397:0x0468  */
    /* JADX WARN: Removed duplicated region for block: B:398:0x0362  */
    /* JADX WARN: Type inference failed for: r10v43 */
    /* JADX WARN: Type inference failed for: r10v44 */
    /* JADX WARN: Type inference failed for: r10v49 */
    /* JADX WARN: Type inference failed for: r10v51 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r30v0, types: [java.util.ArrayList, java.util.ArrayList<java.lang.Object>] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void localSearch(int r28, java.lang.String r29, java.util.ArrayList<java.lang.Object> r30, java.util.ArrayList<java.lang.CharSequence> r31, java.util.ArrayList<org.telegram.tgnet.TLRPC$User> r32, java.util.ArrayList<java.lang.Long> r33, int r34) {
        /*
            Method dump skipped, instructions count: 2048
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.localSearch(int, java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$localSearch$213(DialogsSearchAdapter.DialogSearchResult dialogSearchResult, DialogsSearchAdapter.DialogSearchResult dialogSearchResult2) {
        int i = dialogSearchResult.date;
        int i2 = dialogSearchResult2.date;
        if (i < i2) {
            return 1;
        }
        return i > i2 ? -1 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
    
        if (0 == 0) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.ArrayList<java.lang.Integer> getCachedMessagesInRange(long r7, int r9, int r10) {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r6.database     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r4 = "SELECT mid FROM messages_v2 WHERE uid = %d AND date >= %d AND date <= %d"
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r8 = 0
            r5[r8] = r7     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r7 = 1
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r5[r7] = r9     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r7 = 2
            java.lang.Integer r9 = java.lang.Integer.valueOf(r10)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            r5[r7] = r9     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.String r7 = java.lang.String.format(r3, r4, r5)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            org.telegram.SQLite.SQLiteCursor r1 = r2.queryFinalized(r7, r9)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
        L2e:
            boolean r7 = r1.next()     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L48
            if (r7 == 0) goto L44
            int r7 = r1.intValue(r8)     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L48
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L48
            r0.add(r7)     // Catch: java.lang.Exception -> L40 java.lang.Throwable -> L48
            goto L2e
        L40:
            r7 = move-exception
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
        L44:
            r1.dispose()     // Catch: java.lang.Throwable -> L48 java.lang.Exception -> L4a
            goto L50
        L48:
            r7 = move-exception
            goto L54
        L4a:
            r7 = move-exception
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L48
            if (r1 == 0) goto L53
        L50:
            r1.dispose()
        L53:
            return r0
        L54:
            if (r1 == 0) goto L59
            r1.dispose()
        L59:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.getCachedMessagesInRange(long, int, int):java.util.ArrayList");
    }

    public void updateUnreadReactionsCount(long j, int i, int i2) {
        updateUnreadReactionsCount(j, i, i2, false);
    }

    public void updateUnreadReactionsCount(final long j, final int i, final int i2, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda67
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateUnreadReactionsCount$214(i, z, j, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateUnreadReactionsCount$214(int r8, boolean r9, long r10, int r12) {
        /*
            r7 = this;
            r0 = 0
            r1 = 2
            r2 = 1
            r3 = 0
            if (r8 == 0) goto L81
            if (r9 == 0) goto L34
            org.telegram.SQLite.SQLiteDatabase r9 = r7.database     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.String r4 = "SELECT unread_reactions FROM topics WHERE did = %d AND topic_id = %d"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.Long r6 = java.lang.Long.valueOf(r10)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r5[r3] = r6     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.Integer r6 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r5[r2] = r6     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            org.telegram.SQLite.SQLiteCursor r9 = r9.queryFinalized(r4, r5)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            boolean r4 = r9.next()     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            if (r4 == 0) goto L2f
            int r4 = r9.intValue(r3)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            goto L30
        L2f:
            r4 = 0
        L30:
            r9.dispose()     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            goto L35
        L34:
            r4 = 0
        L35:
            org.telegram.SQLite.SQLiteDatabase r9 = r7.database     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.String r5 = "UPDATE topics SET unread_reactions = ? WHERE did = ? AND topic_id = ?"
            org.telegram.SQLite.SQLitePreparedStatement r9 = r9.executeFast(r5)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            int r4 = r4 + r12
            int r3 = java.lang.Math.max(r4, r3)     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            r9.bindInteger(r2, r3)     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            r9.bindLong(r1, r10)     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            r3 = 3
            r9.bindInteger(r3, r8)     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            r9.step()     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            r9.dispose()     // Catch: java.lang.Throwable -> L69 org.telegram.SQLite.SQLiteException -> L6c
            if (r12 != 0) goto Lc0
            org.telegram.SQLite.SQLiteDatabase r9 = r7.database     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            java.lang.String r12 = "UPDATE reaction_mentions_topics SET state = 0 WHERE dialog_id = ? AND topic_id = ? "
            org.telegram.SQLite.SQLitePreparedStatement r0 = r9.executeFast(r12)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r0.bindLong(r2, r10)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r0.bindInteger(r1, r8)     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r0.step()     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            r0.dispose()     // Catch: java.lang.Throwable -> L6f org.telegram.SQLite.SQLiteException -> L71
            goto Lc0
        L69:
            r8 = move-exception
            r0 = r9
            goto L7b
        L6c:
            r8 = move-exception
            r0 = r9
            goto L72
        L6f:
            r8 = move-exception
            goto L7b
        L71:
            r8 = move-exception
        L72:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L6f
            if (r0 == 0) goto Lc0
            r0.dispose()
            goto Lc0
        L7b:
            if (r0 == 0) goto L80
            r0.dispose()
        L80:
            throw r8
        L81:
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            java.lang.String r9 = "UPDATE dialogs SET unread_reactions = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r8 = r8.executeFast(r9)     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            int r9 = java.lang.Math.max(r12, r3)     // Catch: java.lang.Throwable -> Lad org.telegram.SQLite.SQLiteException -> Lb1
            r8.bindInteger(r2, r9)     // Catch: java.lang.Throwable -> Lad org.telegram.SQLite.SQLiteException -> Lb1
            r8.bindLong(r1, r10)     // Catch: java.lang.Throwable -> Lad org.telegram.SQLite.SQLiteException -> Lb1
            r8.step()     // Catch: java.lang.Throwable -> Lad org.telegram.SQLite.SQLiteException -> Lb1
            r8.dispose()     // Catch: java.lang.Throwable -> Lad org.telegram.SQLite.SQLiteException -> Lb1
            if (r12 != 0) goto Lc0
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            java.lang.String r9 = "UPDATE reaction_mentions SET state = 0 WHERE dialog_id = ?"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r8.executeFast(r9)     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            r0.bindLong(r2, r10)     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            r0.step()     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            r0.dispose()     // Catch: java.lang.Throwable -> Lb5 org.telegram.SQLite.SQLiteException -> Lb7
            goto Lc0
        Lad:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto Lc1
        Lb1:
            r9 = move-exception
            r0 = r8
            r8 = r9
            goto Lb8
        Lb5:
            r8 = move-exception
            goto Lc1
        Lb7:
            r8 = move-exception
        Lb8:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> Lb5
            if (r0 == 0) goto Lc0
            r0.dispose()
        Lc0:
            return
        Lc1:
            if (r0 == 0) goto Lc6
            r0.dispose()
        Lc6:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateUnreadReactionsCount$214(int, boolean, long, int):void");
    }

    public void markMessageReactionsAsRead(final long j, final int i, final int i2, boolean z) {
        if (z) {
            getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda75
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesStorage.this.lambda$markMessageReactionsAsRead$215(j, i, i2);
                }
            });
        } else {
            lambda$markMessageReactionsAsRead$215(j, i, i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0172  */
    /* renamed from: markMessageReactionsAsReadInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lambda$markMessageReactionsAsRead$215(long r18, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessageReactionsAsRead$215(long, int, int):void");
    }

    public void updateDialogUnreadReactions(final long j, final int i, final int i2, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda202
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.this.lambda$updateDialogUnreadReactions$216(z, j, i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$updateDialogUnreadReactions$216(boolean r10, long r11, int r13, int r14) {
        /*
            Method dump skipped, instructions count: 223
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogUnreadReactions$216(boolean, long, int, int):void");
    }

    private boolean isForum(long j) {
        int i = this.dialogIsForum.get(j, -1);
        if (i == -1) {
            TLRPC$Chat chat = getChat(-j);
            i = (chat == null || !chat.forum) ? 0 : 1;
            this.dialogIsForum.put(j, i);
        }
        return i == 1;
    }

    public static class TopicKey {
        public long dialogId;
        public int topicId;

        public static TopicKey of(long j, int i) {
            TopicKey topicKey = new TopicKey();
            topicKey.dialogId = j;
            topicKey.topicId = i;
            return topicKey;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TopicKey topicKey = (TopicKey) obj;
            return this.dialogId == topicKey.dialogId && this.topicId == topicKey.topicId;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.dialogId), Integer.valueOf(this.topicId));
        }

        public String toString() {
            return "TopicKey{dialogId=" + this.dialogId + ", topicId=" + this.topicId + '}';
        }
    }
}
