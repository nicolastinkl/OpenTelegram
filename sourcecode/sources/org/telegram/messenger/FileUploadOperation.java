package org.telegram.messenger;

import android.content.SharedPreferences;
import android.util.SparseArray;
import android.util.SparseIntArray;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;

/* loaded from: classes3.dex */
public class FileUploadOperation {
    private static final int initialRequestsCount = 8;
    private static final int initialRequestsSlowNetworkCount = 1;
    private static final int maxUploadingKBytes = 2048;
    private static final int maxUploadingSlowNetworkKBytes = 32;
    private static final int minUploadChunkSize = 128;
    private static final int minUploadChunkSlowNetworkSize = 32;
    private long availableSize;
    private int currentAccount;
    private long currentFileId;
    private int currentPartNum;
    private int currentType;
    private int currentUploadRequetsCount;
    private FileUploadOperationDelegate delegate;
    private long estimatedSize;
    private String fileKey;
    private int fingerprint;
    private boolean forceSmallFile;
    private ArrayList<byte[]> freeRequestIvs;
    private boolean isBigFile;
    private boolean isEncrypted;
    private boolean isLastPart;
    private byte[] iv;
    private byte[] ivChange;
    private byte[] key;
    protected long lastProgressUpdateTime;
    private int lastSavedPartNum;
    private int maxRequestsCount;
    private boolean nextPartFirst;
    private int operationGuid;
    private SharedPreferences preferences;
    private byte[] readBuffer;
    private long readBytesCount;
    private int requestNum;
    private int saveInfoTimes;
    private boolean slowNetwork;
    private boolean started;
    private int state;
    private RandomAccessFile stream;
    private long totalFileSize;
    private int totalPartsCount;
    private boolean uploadFirstPartLater;
    private int uploadStartTime;
    private long uploadedBytesCount;
    private String uploadingFilePath;
    private int uploadChunkSize = CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT;
    private SparseIntArray requestTokens = new SparseIntArray();
    private SparseArray<UploadCachedResult> cachedResults = new SparseArray<>();
    private boolean[] recalculatedEstimatedSize = {false, false};

    public interface FileUploadOperationDelegate {
        void didChangedUploadProgress(FileUploadOperation fileUploadOperation, long j, long j2);

        void didFailedUploadingFile(FileUploadOperation fileUploadOperation);

        void didFinishUploadingFile(FileUploadOperation fileUploadOperation, TLRPC$InputFile tLRPC$InputFile, TLRPC$InputEncryptedFile tLRPC$InputEncryptedFile, byte[] bArr, byte[] bArr2);
    }

    private static class UploadCachedResult {
        private long bytesOffset;
        private byte[] iv;

        private UploadCachedResult() {
        }
    }

    public FileUploadOperation(int i, String str, boolean z, long j, int i2) {
        this.currentAccount = i;
        this.uploadingFilePath = str;
        this.isEncrypted = z;
        this.estimatedSize = j;
        this.currentType = i2;
        this.uploadFirstPartLater = (j == 0 || z) ? false : true;
    }

    public long getTotalFileSize() {
        return this.totalFileSize;
    }

    public void setDelegate(FileUploadOperationDelegate fileUploadOperationDelegate) {
        this.delegate = fileUploadOperationDelegate;
    }

    public void start() {
        if (this.state != 0) {
            return;
        }
        this.state = 1;
        AutoDeleteMediaTask.lockFile(this.uploadingFilePath);
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                FileUploadOperation.this.lambda$start$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$0() {
        this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
        this.slowNetwork = ApplicationLoader.isConnectionSlow();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("start upload on slow network = " + this.slowNetwork);
        }
        int i = this.slowNetwork ? 1 : 8;
        for (int i2 = 0; i2 < i; i2++) {
            startUploadRequest();
        }
    }

    protected void onNetworkChanged(final boolean z) {
        if (this.state != 1) {
            return;
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                FileUploadOperation.this.lambda$onNetworkChanged$1(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNetworkChanged$1(boolean z) {
        if (this.slowNetwork != z) {
            this.slowNetwork = z;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("network changed to slow = " + this.slowNetwork);
            }
            int i = 0;
            while (true) {
                if (i >= this.requestTokens.size()) {
                    break;
                }
                ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.requestTokens.valueAt(i), true);
                i++;
            }
            this.requestTokens.clear();
            cleanup();
            this.isLastPart = false;
            this.nextPartFirst = false;
            this.requestNum = 0;
            this.currentPartNum = 0;
            this.readBytesCount = 0L;
            this.uploadedBytesCount = 0L;
            this.saveInfoTimes = 0;
            this.key = null;
            this.iv = null;
            this.ivChange = null;
            this.currentUploadRequetsCount = 0;
            this.lastSavedPartNum = 0;
            this.uploadFirstPartLater = false;
            this.cachedResults.clear();
            this.operationGuid++;
            int i2 = this.slowNetwork ? 1 : 8;
            for (int i3 = 0; i3 < i2; i3++) {
                startUploadRequest();
            }
        }
    }

    public void cancel() {
        if (this.state == 3) {
            return;
        }
        this.state = 2;
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FileUploadOperation.this.lambda$cancel$2();
            }
        });
        AutoDeleteMediaTask.unlockFile(this.uploadingFilePath);
        this.delegate.didFailedUploadingFile(this);
        cleanup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$2() {
        for (int i = 0; i < this.requestTokens.size(); i++) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.requestTokens.valueAt(i), true);
        }
    }

    private void cleanup() {
        if (this.preferences == null) {
            this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("uploadinfo", 0);
        }
        this.preferences.edit().remove(this.fileKey + "_time").remove(this.fileKey + "_size").remove(this.fileKey + "_uploaded").remove(this.fileKey + "_id").remove(this.fileKey + "_iv").remove(this.fileKey + "_key").remove(this.fileKey + "_ivc").commit();
        try {
            RandomAccessFile randomAccessFile = this.stream;
            if (randomAccessFile != null) {
                randomAccessFile.close();
                this.stream = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        AutoDeleteMediaTask.unlockFile(this.uploadingFilePath);
    }

    protected void checkNewDataAvailable(final long j, final long j2, final Float f) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                FileUploadOperation.this.lambda$checkNewDataAvailable$3(f, j2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$checkNewDataAvailable$3(java.lang.Float r7, long r8, long r10) {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto L43
            long r2 = r6.estimatedSize
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 == 0) goto L43
            int r2 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r2 != 0) goto L43
            float r2 = r7.floatValue()
            r3 = 1061158912(0x3f400000, float:0.75)
            r4 = 0
            r5 = 1
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L23
            boolean[] r2 = r6.recalculatedEstimatedSize
            boolean r3 = r2[r4]
            if (r3 != 0) goto L23
            r2[r4] = r5
            r4 = 1
        L23:
            float r2 = r7.floatValue()
            r3 = 1064514355(0x3f733333, float:0.95)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L37
            boolean[] r2 = r6.recalculatedEstimatedSize
            boolean r3 = r2[r5]
            if (r3 != 0) goto L37
            r2[r5] = r5
            goto L38
        L37:
            r5 = r4
        L38:
            if (r5 == 0) goto L43
            float r2 = (float) r10
            float r7 = r7.floatValue()
            float r2 = r2 / r7
            long r2 = (long) r2
            r6.estimatedSize = r2
        L43:
            long r2 = r6.estimatedSize
            int r7 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r7 == 0) goto L5f
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 == 0) goto L5f
            r6.estimatedSize = r0
            r6.totalFileSize = r8
            r6.calcTotalPartsCount()
            boolean r7 = r6.uploadFirstPartLater
            if (r7 != 0) goto L5f
            boolean r7 = r6.started
            if (r7 == 0) goto L5f
            r6.storeFileUploadInfo()
        L5f:
            int r7 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
            if (r7 <= 0) goto L64
            goto L65
        L64:
            r8 = r10
        L65:
            r6.availableSize = r8
            int r7 = r6.currentUploadRequetsCount
            int r8 = r6.maxRequestsCount
            if (r7 >= r8) goto L70
            r6.startUploadRequest()
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileUploadOperation.lambda$checkNewDataAvailable$3(java.lang.Float, long, long):void");
    }

    private void storeFileUploadInfo() {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putInt(this.fileKey + "_time", this.uploadStartTime);
        edit.putLong(this.fileKey + "_size", this.totalFileSize);
        edit.putLong(this.fileKey + "_id", this.currentFileId);
        edit.remove(this.fileKey + "_uploaded");
        if (this.isEncrypted) {
            edit.putString(this.fileKey + "_iv", Utilities.bytesToHex(this.iv));
            edit.putString(this.fileKey + "_ivc", Utilities.bytesToHex(this.ivChange));
            edit.putString(this.fileKey + "_key", Utilities.bytesToHex(this.key));
        }
        edit.commit();
    }

    private void calcTotalPartsCount() {
        if (this.uploadFirstPartLater) {
            if (this.isBigFile) {
                long j = this.totalFileSize;
                int i = this.uploadChunkSize;
                this.totalPartsCount = ((int) ((((j - i) + i) - 1) / i)) + 1;
                return;
            } else {
                long j2 = this.totalFileSize - 1024;
                int i2 = this.uploadChunkSize;
                this.totalPartsCount = ((int) (((j2 + i2) - 1) / i2)) + 1;
                return;
            }
        }
        long j3 = this.totalFileSize;
        int i3 = this.uploadChunkSize;
        this.totalPartsCount = (int) (((j3 + i3) - 1) / i3);
    }

    public void setForceSmallFile() {
        this.forceSmallFile = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x02f7 A[Catch: Exception -> 0x0544, TryCatch #2 {Exception -> 0x0544, blocks: (B:6:0x0008, B:8:0x0015, B:12:0x004d, B:14:0x0053, B:15:0x005c, B:17:0x0060, B:19:0x0069, B:20:0x006b, B:22:0x0084, B:24:0x008d, B:25:0x0096, B:28:0x009f, B:31:0x00ba, B:33:0x00be, B:35:0x00c1, B:36:0x00c3, B:39:0x00cc, B:41:0x00d9, B:42:0x00e3, B:44:0x00e7, B:46:0x00f1, B:49:0x0113, B:51:0x0149, B:53:0x014d, B:55:0x0155, B:57:0x015b, B:59:0x01b1, B:62:0x01e9, B:65:0x01fb, B:67:0x01fe, B:69:0x0201, B:73:0x0211, B:75:0x0215, B:81:0x0237, B:84:0x0244, B:86:0x024f, B:88:0x025b, B:90:0x025f, B:91:0x0267, B:93:0x0272, B:95:0x027b, B:99:0x0288, B:101:0x028f, B:103:0x02a6, B:105:0x0279, B:108:0x02b0, B:110:0x02b9, B:112:0x02d5, B:114:0x02dd, B:117:0x02f7, B:119:0x02fb, B:120:0x031b, B:122:0x0327, B:124:0x032b, B:126:0x0333, B:127:0x0336, B:129:0x036d, B:131:0x0379, B:133:0x037d, B:134:0x0394, B:135:0x038b, B:136:0x03a1, B:138:0x03a9, B:141:0x03b6, B:143:0x03ba, B:145:0x03c5, B:146:0x03da, B:150:0x03ea, B:152:0x03ee, B:154:0x03f2, B:155:0x03fa, B:157:0x0405, B:159:0x0409, B:161:0x0411, B:163:0x0424, B:167:0x0431, B:169:0x0438, B:170:0x0465, B:172:0x0469, B:174:0x047e, B:175:0x0485, B:176:0x049d, B:178:0x04a1, B:180:0x04a5, B:181:0x04b4, B:195:0x0481, B:196:0x048b, B:198:0x0418, B:200:0x041c, B:201:0x0422, B:203:0x03cf, B:204:0x03dd, B:214:0x036a, B:215:0x02e0, B:217:0x02e8, B:220:0x0221, B:228:0x0056, B:229:0x0397, B:230:0x039f, B:233:0x0047, B:10:0x0025, B:206:0x033a, B:209:0x0355), top: B:5:0x0008, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0379 A[Catch: Exception -> 0x0544, TryCatch #2 {Exception -> 0x0544, blocks: (B:6:0x0008, B:8:0x0015, B:12:0x004d, B:14:0x0053, B:15:0x005c, B:17:0x0060, B:19:0x0069, B:20:0x006b, B:22:0x0084, B:24:0x008d, B:25:0x0096, B:28:0x009f, B:31:0x00ba, B:33:0x00be, B:35:0x00c1, B:36:0x00c3, B:39:0x00cc, B:41:0x00d9, B:42:0x00e3, B:44:0x00e7, B:46:0x00f1, B:49:0x0113, B:51:0x0149, B:53:0x014d, B:55:0x0155, B:57:0x015b, B:59:0x01b1, B:62:0x01e9, B:65:0x01fb, B:67:0x01fe, B:69:0x0201, B:73:0x0211, B:75:0x0215, B:81:0x0237, B:84:0x0244, B:86:0x024f, B:88:0x025b, B:90:0x025f, B:91:0x0267, B:93:0x0272, B:95:0x027b, B:99:0x0288, B:101:0x028f, B:103:0x02a6, B:105:0x0279, B:108:0x02b0, B:110:0x02b9, B:112:0x02d5, B:114:0x02dd, B:117:0x02f7, B:119:0x02fb, B:120:0x031b, B:122:0x0327, B:124:0x032b, B:126:0x0333, B:127:0x0336, B:129:0x036d, B:131:0x0379, B:133:0x037d, B:134:0x0394, B:135:0x038b, B:136:0x03a1, B:138:0x03a9, B:141:0x03b6, B:143:0x03ba, B:145:0x03c5, B:146:0x03da, B:150:0x03ea, B:152:0x03ee, B:154:0x03f2, B:155:0x03fa, B:157:0x0405, B:159:0x0409, B:161:0x0411, B:163:0x0424, B:167:0x0431, B:169:0x0438, B:170:0x0465, B:172:0x0469, B:174:0x047e, B:175:0x0485, B:176:0x049d, B:178:0x04a1, B:180:0x04a5, B:181:0x04b4, B:195:0x0481, B:196:0x048b, B:198:0x0418, B:200:0x041c, B:201:0x0422, B:203:0x03cf, B:204:0x03dd, B:214:0x036a, B:215:0x02e0, B:217:0x02e8, B:220:0x0221, B:228:0x0056, B:229:0x0397, B:230:0x039f, B:233:0x0047, B:10:0x0025, B:206:0x033a, B:209:0x0355), top: B:5:0x0008, inners: #0, #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x033a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0231  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void startUploadRequest() {
        /*
            Method dump skipped, instructions count: 1364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileUploadOperation.startUploadRequest():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0099, code lost:
    
        if (r21.uploadingFilePath.toLowerCase().endsWith(r9) != false) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$startUploadRequest$4(int r22, int[] r23, int r24, byte[] r25, int r26, int r27, int r28, long r29, org.telegram.tgnet.TLObject r31, org.telegram.tgnet.TLRPC$TL_error r32) {
        /*
            Method dump skipped, instructions count: 712
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileUploadOperation.lambda$startUploadRequest$4(int, int[], int, byte[], int, int, int, long, org.telegram.tgnet.TLObject, org.telegram.tgnet.TLRPC$TL_error):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$6() {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileUploadOperation$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FileUploadOperation.this.lambda$startUploadRequest$5();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUploadRequest$5() {
        if (this.currentUploadRequetsCount < this.maxRequestsCount) {
            startUploadRequest();
        }
    }
}
