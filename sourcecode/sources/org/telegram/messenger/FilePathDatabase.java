package org.telegram.messenger;

import android.os.Looper;
import android.util.LongSparseArray;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLiteException;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.CacheByChatsController;
import org.telegram.ui.Storage.CacheModel;

/* loaded from: classes3.dex */
public class FilePathDatabase {
    private static final String DATABASE_BACKUP_NAME = "file_to_path_backup";
    private static final String DATABASE_NAME = "file_to_path";
    private static final int LAST_DB_VERSION = 4;
    public static final int MESSAGE_TYPE_VIDEO_MESSAGE = 0;
    private File cacheFile;
    private final int currentAccount;
    private SQLiteDatabase database;
    boolean databaseCreated;
    private DispatchQueue dispatchQueue;
    private final FileMeta metaTmp = new FileMeta();
    private File shmCacheFile;

    public static class FileMeta {
        public long dialogId;
        public int messageId;
        public long messageSize;
        public int messageType;
    }

    public FilePathDatabase(int i) {
        this.currentAccount = i;
    }

    public void createDatabase(int i, boolean z) {
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (this.currentAccount != 0) {
            File file = new File(filesDirFixed, "account" + this.currentAccount + "/");
            file.mkdirs();
            filesDirFixed = file;
        }
        this.cacheFile = new File(filesDirFixed, "file_to_path.db");
        this.shmCacheFile = new File(filesDirFixed, "file_to_path.db-shm");
        boolean z2 = !this.cacheFile.exists();
        try {
            SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(this.cacheFile.getPath());
            this.database = sQLiteDatabase;
            sQLiteDatabase.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
            this.database.executeFast("PRAGMA temp_store = MEMORY").stepThis().dispose();
            if (z2) {
                this.database.executeFast("CREATE TABLE paths(document_id INTEGER, dc_id INTEGER, type INTEGER, path TEXT, PRIMARY KEY(document_id, dc_id, type));").stepThis().dispose();
                this.database.executeFast("CREATE INDEX IF NOT EXISTS path_in_paths ON paths(path);").stepThis().dispose();
                this.database.executeFast("CREATE TABLE paths_by_dialog_id(path TEXT PRIMARY KEY, dialog_id INTEGER, message_id INTEGER, message_type INTEGER);").stepThis().dispose();
                this.database.executeFast("PRAGMA user_version = 4").stepThis().dispose();
            } else {
                int intValue = this.database.executeInt("PRAGMA user_version", new Object[0]).intValue();
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("current files db version = " + intValue);
                }
                if (intValue == 0) {
                    throw new Exception("malformed");
                }
                migrateDatabase(intValue);
            }
            if (!z) {
                createBackup();
            }
            FileLog.d("files db created from_backup= " + z);
        } catch (Exception e) {
            if (i < 4) {
                if (!z && restoreBackup()) {
                    createDatabase(i + 1, true);
                    return;
                } else {
                    this.cacheFile.delete();
                    this.shmCacheFile.delete();
                    createDatabase(i + 1, false);
                }
            }
            if (BuildVars.DEBUG_VERSION) {
                FileLog.e(e);
            }
        }
    }

    private void migrateDatabase(int i) throws SQLiteException {
        if (i == 1) {
            this.database.executeFast("CREATE INDEX IF NOT EXISTS path_in_paths ON paths(path);").stepThis().dispose();
            this.database.executeFast("PRAGMA user_version = 2").stepThis().dispose();
            i = 2;
        }
        if (i == 2) {
            this.database.executeFast("CREATE TABLE paths_by_dialog_id(path TEXT PRIMARY KEY, dialog_id INTEGER);").stepThis().dispose();
            this.database.executeFast("PRAGMA user_version = 3").stepThis().dispose();
            i = 3;
        }
        if (i == 3) {
            this.database.executeFast("ALTER TABLE paths_by_dialog_id ADD COLUMN message_id INTEGER default 0").stepThis().dispose();
            this.database.executeFast("ALTER TABLE paths_by_dialog_id ADD COLUMN message_type INTEGER default 0").stepThis().dispose();
            this.database.executeFast("PRAGMA user_version = 4").stepThis().dispose();
        }
    }

    private void createBackup() {
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (this.currentAccount != 0) {
            File file = new File(filesDirFixed, "account" + this.currentAccount + "/");
            file.mkdirs();
            filesDirFixed = file;
        }
        File file2 = new File(filesDirFixed, "file_to_path_backup.db");
        try {
            AndroidUtilities.copyFile(this.cacheFile, file2);
            FileLog.d("file db backup created " + file2.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean restoreBackup() {
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (this.currentAccount != 0) {
            File file = new File(filesDirFixed, "account" + this.currentAccount + "/");
            file.mkdirs();
            filesDirFixed = file;
        }
        File file2 = new File(filesDirFixed, "file_to_path_backup.db");
        if (!file2.exists()) {
            return false;
        }
        try {
            return AndroidUtilities.copyFile(file2, this.cacheFile);
        } catch (IOException e) {
            FileLog.e(e);
            return false;
        }
    }

    public String getPath(final long j, final int i, final int i2, boolean z) {
        SQLiteException sQLiteException;
        String str;
        DispatchQueue dispatchQueue;
        if (z) {
            if (BuildVars.DEBUG_PRIVATE_VERSION && (dispatchQueue = this.dispatchQueue) != null && dispatchQueue.getHandler() != null && Thread.currentThread() == this.dispatchQueue.getHandler().getLooper().getThread()) {
                throw new RuntimeException("Error, lead to infinity loop");
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final String[] strArr = new String[1];
            postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FilePathDatabase.this.lambda$getPath$0(j, i, i2, strArr, countDownLatch);
                }
            });
            try {
                countDownLatch.await();
            } catch (Exception unused) {
            }
            return strArr[0];
        }
        SQLiteDatabase sQLiteDatabase = this.database;
        SQLiteCursor sQLiteCursor = null;
        r2 = null;
        String str2 = null;
        sQLiteCursor = null;
        if (sQLiteDatabase == null) {
            return null;
        }
        try {
            try {
                SQLiteCursor queryFinalized = sQLiteDatabase.queryFinalized("SELECT path FROM paths WHERE document_id = " + j + " AND dc_id = " + i + " AND type = " + i2, new Object[0]);
                try {
                    if (queryFinalized.next()) {
                        str2 = queryFinalized.stringValue(0);
                        if (BuildVars.DEBUG_VERSION) {
                            FileLog.d("get file path id=" + j + " dc=" + i + " type=" + i2 + " path=" + str2);
                        }
                    }
                    queryFinalized.dispose();
                    return str2;
                } catch (SQLiteException e) {
                    sQLiteException = e;
                    str = str2;
                    sQLiteCursor = queryFinalized;
                    FileLog.e(sQLiteException);
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                    return str;
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
        } catch (SQLiteException e2) {
            sQLiteException = e2;
            str = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPath$0(long j, int i, int i2, String[] strArr, CountDownLatch countDownLatch) {
        ensureDatabaseCreated();
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase != null) {
            SQLiteCursor sQLiteCursor = null;
            try {
                sQLiteCursor = sQLiteDatabase.queryFinalized("SELECT path FROM paths WHERE document_id = " + j + " AND dc_id = " + i + " AND type = " + i2, new Object[0]);
                if (sQLiteCursor.next()) {
                    strArr[0] = sQLiteCursor.stringValue(0);
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.d("get file path id=" + j + " dc=" + i + " type=" + i2 + " path=" + strArr[0]);
                    }
                }
            } catch (Throwable th) {
                try {
                    FileLog.e(th);
                } finally {
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                }
            }
        }
        countDownLatch.countDown();
    }

    public void ensureDatabaseCreated() {
        if (this.databaseCreated) {
            return;
        }
        if (!NativeLoader.loaded()) {
            int i = 0;
            while (!NativeLoader.loaded()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                if (i > 5) {
                    break;
                }
            }
        }
        createDatabase(0, false);
        this.databaseCreated = true;
    }

    public void putPath(final long j, final int i, final int i2, final String str) {
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$putPath$1(j, i, i2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$putPath$1(long r6, int r8, int r9, java.lang.String r10) {
        /*
            r5 = this;
            boolean r0 = org.telegram.messenger.BuildVars.DEBUG_VERSION
            if (r0 == 0) goto L30
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "put file path id="
            r0.append(r1)
            r0.append(r6)
            java.lang.String r1 = " dc="
            r0.append(r1)
            r0.append(r8)
            java.lang.String r1 = " type="
            r0.append(r1)
            r0.append(r9)
            java.lang.String r1 = " path="
            r0.append(r1)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            org.telegram.messenger.FileLog.d(r0)
        L30:
            r5.ensureDatabaseCreated()
            org.telegram.SQLite.SQLiteDatabase r0 = r5.database
            if (r0 != 0) goto L38
            return
        L38:
            r1 = 0
            if (r10 == 0) goto L73
            java.lang.String r2 = "DELETE FROM paths WHERE path = ?"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r0.executeFast(r2)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r2 = 1
            r0.bindString(r2, r10)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r0.step()     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            org.telegram.SQLite.SQLiteDatabase r3 = r5.database     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            java.lang.String r4 = "REPLACE INTO paths VALUES(?, ?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r1 = r3.executeFast(r4)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r1.requery()     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r1.bindLong(r2, r6)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r6 = 2
            r1.bindInteger(r6, r8)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r6 = 3
            r1.bindInteger(r6, r9)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r6 = 4
            r1.bindString(r6, r10)     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r1.step()     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r1.dispose()     // Catch: java.lang.Throwable -> L6b org.telegram.SQLite.SQLiteException -> L6f
            r6 = r1
            r1 = r0
            goto La0
        L6b:
            r6 = move-exception
            r7 = r1
            r1 = r0
            goto Lbf
        L6f:
            r6 = move-exception
            r7 = r1
            r1 = r0
            goto Lb0
        L73:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r10.<init>()     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            java.lang.String r2 = "DELETE FROM paths WHERE document_id = "
            r10.append(r2)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r10.append(r6)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            java.lang.String r6 = " AND dc_id = "
            r10.append(r6)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r10.append(r8)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            java.lang.String r6 = " AND type = "
            r10.append(r6)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r10.append(r9)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            java.lang.String r6 = r10.toString()     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            org.telegram.SQLite.SQLitePreparedStatement r6 = r0.executeFast(r6)     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            org.telegram.SQLite.SQLitePreparedStatement r6 = r6.stepThis()     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r6.dispose()     // Catch: java.lang.Throwable -> Lab org.telegram.SQLite.SQLiteException -> Lae
            r6 = r1
        La0:
            if (r1 == 0) goto La5
            r1.dispose()
        La5:
            if (r6 == 0) goto Lbd
            r6.dispose()
            goto Lbd
        Lab:
            r6 = move-exception
            r7 = r1
            goto Lbf
        Lae:
            r6 = move-exception
            r7 = r1
        Lb0:
            org.telegram.messenger.FileLog.e(r6)     // Catch: java.lang.Throwable -> Lbe
            if (r1 == 0) goto Lb8
            r1.dispose()
        Lb8:
            if (r7 == 0) goto Lbd
            r7.dispose()
        Lbd:
            return
        Lbe:
            r6 = move-exception
        Lbf:
            if (r1 == 0) goto Lc4
            r1.dispose()
        Lc4:
            if (r7 == 0) goto Lc9
            r7.dispose()
        Lc9:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FilePathDatabase.lambda$putPath$1(long, int, int, java.lang.String):void");
    }

    public void checkMediaExistance(ArrayList<MessageObject> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        final ArrayList arrayList2 = new ArrayList(arrayList);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        long currentTimeMillis = System.currentTimeMillis();
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$checkMediaExistance$2(arrayList2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            FileLog.e(e);
        }
        FileLog.d("checkMediaExistance size=" + arrayList.size() + " time=" + (System.currentTimeMillis() - currentTimeMillis));
        if (BuildVars.DEBUG_VERSION && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            FileLog.e(new Exception("warning, not allowed in main thread"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMediaExistance$2(ArrayList arrayList, CountDownLatch countDownLatch) {
        ensureDatabaseCreated();
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                ((MessageObject) arrayList.get(i)).checkMediaExistance(false);
            } finally {
                try {
                } finally {
                }
            }
        }
    }

    public void clear() {
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$clear$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clear$3() {
        ensureDatabaseCreated();
        try {
            this.database.executeFast("DELETE FROM paths WHERE 1").stepThis().dispose();
            this.database.executeFast("DELETE FROM paths_by_dialog_id WHERE 1").stepThis().dispose();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public boolean hasAnotherRefOnFile(final String str) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = {false};
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$hasAnotherRefOnFile$4(str, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            FileLog.e(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hasAnotherRefOnFile$4(String str, boolean[] zArr, CountDownLatch countDownLatch) {
        ensureDatabaseCreated();
        try {
            try {
                if (this.database.queryFinalized("SELECT document_id FROM paths WHERE path = '" + str + "'", new Object[0]).next()) {
                    zArr[0] = true;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    public void saveFileDialogId(final File file, final FileMeta fileMeta) {
        if (file == null || fileMeta == null) {
            return;
        }
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$saveFileDialogId$5(file, fileMeta);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveFileDialogId$5(File file, FileMeta fileMeta) {
        ensureDatabaseCreated();
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                sQLitePreparedStatement = this.database.executeFast("REPLACE INTO paths_by_dialog_id VALUES(?, ?, ?, ?)");
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindString(1, shield(file.getPath()));
                sQLitePreparedStatement.bindLong(2, fileMeta.dialogId);
                sQLitePreparedStatement.bindInteger(3, fileMeta.messageId);
                sQLitePreparedStatement.bindInteger(4, fileMeta.messageType);
                sQLitePreparedStatement.step();
            } catch (Exception e) {
                FileLog.e(e);
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

    public FileMeta getFileDialogId(File file, FileMeta fileMeta) {
        int i;
        int i2;
        SQLiteCursor sQLiteCursor = null;
        if (file == null) {
            return null;
        }
        if (fileMeta == null) {
            fileMeta = this.metaTmp;
        }
        long j = 0;
        int i3 = 0;
        try {
            try {
                sQLiteCursor = this.database.queryFinalized("SELECT dialog_id, message_id, message_type FROM paths_by_dialog_id WHERE path = '" + shield(file.getPath()) + "'", new Object[0]);
                if (sQLiteCursor.next()) {
                    j = sQLiteCursor.longValue(0);
                    i = sQLiteCursor.intValue(1);
                    try {
                        i3 = i;
                        i2 = sQLiteCursor.intValue(2);
                    } catch (Exception e) {
                        e = e;
                        FileLog.e(e);
                        i3 = i;
                        i2 = 0;
                        fileMeta.dialogId = j;
                        fileMeta.messageId = i3;
                        fileMeta.messageType = i2;
                        return fileMeta;
                    }
                } else {
                    i2 = 0;
                }
                sQLiteCursor.dispose();
            } catch (Exception e2) {
                e = e2;
                i = 0;
            }
            fileMeta.dialogId = j;
            fileMeta.messageId = i3;
            fileMeta.messageType = i2;
            return fileMeta;
        } finally {
            if (0 != 0) {
                sQLiteCursor.dispose();
            }
        }
    }

    private String shield(String str) {
        return str.replace("'", "").replace("\"", "");
    }

    public DispatchQueue getQueue() {
        ensureQueueExist();
        return this.dispatchQueue;
    }

    public void removeFiles(final List<CacheModel.FileInfo> list) {
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$removeFiles$6(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeFiles$6(List list) {
        try {
            ensureDatabaseCreated();
            this.database.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                this.database.executeFast("DELETE FROM paths_by_dialog_id WHERE path = '" + shield(((CacheModel.FileInfo) list.get(i)).file.getPath()) + "'").stepThis().dispose();
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    public LongSparseArray<ArrayList<CacheByChatsController.KeepMediaFile>> lookupFiles(final ArrayList<? extends CacheByChatsController.KeepMediaFile> arrayList) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final LongSparseArray<ArrayList<CacheByChatsController.KeepMediaFile>> longSparseArray = new LongSparseArray<>();
        postRunnable(new Runnable() { // from class: org.telegram.messenger.FilePathDatabase$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                FilePathDatabase.this.lambda$lookupFiles$7(arrayList, longSparseArray, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            FileLog.e(e);
        }
        return longSparseArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$lookupFiles$7(ArrayList arrayList, LongSparseArray longSparseArray, CountDownLatch countDownLatch) {
        try {
            ensureDatabaseCreated();
            FileMeta fileMeta = new FileMeta();
            for (int i = 0; i < arrayList.size(); i++) {
                FileMeta fileDialogId = getFileDialogId(((CacheByChatsController.KeepMediaFile) arrayList.get(i)).file, fileMeta);
                if (fileDialogId != null) {
                    long j = fileDialogId.dialogId;
                    if (j != 0) {
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(j);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(fileDialogId.dialogId, arrayList2);
                        }
                        arrayList2.add((CacheByChatsController.KeepMediaFile) arrayList.get(i));
                    }
                }
            }
        } finally {
            try {
            } finally {
            }
        }
    }

    private void postRunnable(Runnable runnable) {
        ensureQueueExist();
        this.dispatchQueue.postRunnable(runnable);
    }

    private void ensureQueueExist() {
        if (this.dispatchQueue == null) {
            synchronized (this) {
                if (this.dispatchQueue == null) {
                    this.dispatchQueue = new DispatchQueue("files_database_queue_" + this.currentAccount);
                }
            }
        }
    }

    public static class PathData {
        public final int dc;
        public final long id;
        public final int type;

        public PathData(long j, int i, int i2) {
            this.id = j;
            this.dc = i;
            this.type = i2;
        }
    }
}
