package org.telegram.messenger;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.FileLoadOperation;
import org.telegram.messenger.FilePathDatabase;
import org.telegram.messenger.utils.ImmutableByteArrayOutputStream;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$InputFileLocation;
import org.telegram.tgnet.TLRPC$InputWebFileLocation;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_fileHash;
import org.telegram.tgnet.TLRPC$TL_fileLocationToBeDeprecated;
import org.telegram.tgnet.TLRPC$TL_inputDocumentFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputPeerPhotoFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputPhotoFileLocation;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetThumb;
import org.telegram.tgnet.TLRPC$TL_secureFile;
import org.telegram.tgnet.TLRPC$TL_upload_cdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_cdnFileReuploadNeeded;
import org.telegram.tgnet.TLRPC$TL_upload_file;
import org.telegram.tgnet.TLRPC$TL_upload_fileCdnRedirect;
import org.telegram.tgnet.TLRPC$TL_upload_getCdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_getCdnFileHashes;
import org.telegram.tgnet.TLRPC$TL_upload_getFile;
import org.telegram.tgnet.TLRPC$TL_upload_getWebFile;
import org.telegram.tgnet.TLRPC$TL_upload_reuploadCdnFile;
import org.telegram.tgnet.TLRPC$TL_upload_webFile;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.Storage.CacheModel;

/* loaded from: classes3.dex */
public class FileLoadOperation {
    private static final int FINISH_CODE_DEFAULT = 0;
    private static final int FINISH_CODE_FILE_ALREADY_EXIST = 1;
    public static ImmutableByteArrayOutputStream filesQueueByteBuffer = null;
    private static final int preloadMaxBytes = 2097152;
    private static final int stateCanceled = 4;
    private static final int stateDownloading = 1;
    private static final int stateFailed = 2;
    private static final int stateFinished = 3;
    private static final int stateIdle = 0;
    private boolean allowDisordererFileSave;
    private int bigFileSizeFrom;
    private long bytesCountPadding;
    private File cacheFileFinal;
    private File cacheFileGzipTemp;
    private File cacheFileParts;
    private File cacheFilePreload;
    private File cacheFileTemp;
    private File cacheIvTemp;
    private byte[] cdnCheckBytes;
    private int cdnChunkCheckSize;
    private int cdnDatacenterId;
    private HashMap<Long, TLRPC$TL_fileHash> cdnHashes;
    private byte[] cdnIv;
    private byte[] cdnKey;
    private byte[] cdnToken;
    public int currentAccount;
    private int currentDownloadChunkSize;
    private int currentMaxDownloadRequests;
    private int currentType;
    private int datacenterId;
    private ArrayList<RequestInfo> delayedRequestInfos;
    private FileLoadOperationDelegate delegate;
    private int downloadChunkSize;
    private int downloadChunkSizeAnimation;
    private int downloadChunkSizeBig;
    private long downloadedBytes;
    private boolean encryptFile;
    private byte[] encryptIv;
    private byte[] encryptKey;
    private String ext;
    private FilePathDatabase.FileMeta fileMetadata;
    private String fileName;
    private RandomAccessFile fileOutputStream;
    private RandomAccessFile filePartsStream;
    private RandomAccessFile fileReadStream;
    private Runnable fileWriteRunnable;
    private RandomAccessFile fiv;
    private boolean forceSmallChunk;
    private long foundMoovSize;
    private int initialDatacenterId;
    private boolean isCdn;
    private boolean isForceRequest;
    private boolean isPreloadVideoOperation;
    private boolean isStream;
    private byte[] iv;
    private byte[] key;
    protected long lastProgressUpdateTime;
    protected TLRPC$InputFileLocation location;
    private int maxCdnParts;
    private int maxDownloadRequests;
    private int maxDownloadRequestsAnimation;
    private int maxDownloadRequestsBig;
    private int moovFound;
    private long nextAtomOffset;
    private boolean nextPartWasPreloaded;
    private long nextPreloadDownloadOffset;
    private ArrayList<Range> notCheckedCdnRanges;
    private ArrayList<Range> notLoadedBytesRanges;
    private volatile ArrayList<Range> notLoadedBytesRangesCopy;
    private ArrayList<Range> notRequestedBytesRanges;
    public Object parentObject;
    public FilePathDatabase.PathData pathSaveData;
    private volatile boolean paused;
    public boolean preFinished;
    private boolean preloadFinished;
    private long preloadNotRequestedBytesCount;
    private RandomAccessFile preloadStream;
    private int preloadStreamFileOffset;
    private byte[] preloadTempBuffer;
    private int preloadTempBufferCount;
    private HashMap<Long, PreloadRange> preloadedBytesRanges;
    private int priority;
    private FileLoaderPriorityQueue priorityQueue;
    private RequestInfo priorityRequestInfo;
    private int renameRetryCount;
    private ArrayList<RequestInfo> requestInfos;
    private long requestedBytesCount;
    private HashMap<Long, Integer> requestedPreloadedBytesRanges;
    private boolean requestingCdnOffsets;
    protected boolean requestingReference;
    private int requestsCount;
    private boolean reuploadingCdn;
    private long startTime;
    private boolean started;
    private volatile int state;
    private String storeFileName;
    private File storePath;
    FileLoadOperationStream stream;
    private ArrayList<FileLoadOperationStream> streamListeners;
    long streamOffset;
    boolean streamPriority;
    private long streamPriorityStartOffset;
    private long streamStartOffset;
    private boolean supportsPreloading;
    private File tempPath;
    public long totalBytesCount;
    private int totalPreloadedBytes;
    long totalTime;
    private boolean ungzip;
    private WebFile webFile;
    private TLRPC$InputWebFileLocation webLocation;
    public static volatile DispatchQueue filesQueue = new DispatchQueue("writeFileQueue");
    private static final Object lockObject = new Object();

    public interface FileLoadOperationDelegate {
        void didChangedLoadProgress(FileLoadOperation fileLoadOperation, long j, long j2);

        void didFailedLoadingFile(FileLoadOperation fileLoadOperation, int i);

        void didFinishLoadingFile(FileLoadOperation fileLoadOperation, File file);

        void didPreFinishLoading(FileLoadOperation fileLoadOperation, File file);

        boolean hasAnotherRefOnFile(String str);

        void saveFilePath(FilePathDatabase.PathData pathData, File file);
    }

    public void setStream(final FileLoadOperationStream fileLoadOperationStream, boolean z, long j) {
        this.stream = fileLoadOperationStream;
        this.streamOffset = j;
        this.streamPriority = z;
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$setStream$0(fileLoadOperationStream);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStream$0(FileLoadOperationStream fileLoadOperationStream) {
        if (this.streamListeners == null) {
            this.streamListeners = new ArrayList<>();
        }
        if (fileLoadOperationStream != null && !this.streamListeners.contains(fileLoadOperationStream)) {
            this.streamListeners.add(fileLoadOperationStream);
        }
        if (fileLoadOperationStream == null || this.state == 1 || this.state == 0) {
            return;
        }
        fileLoadOperationStream.newDataAvailable();
    }

    public int getPositionInQueue() {
        return getQueue().getPosition(this);
    }

    protected static class RequestInfo {
        private boolean forceSmallChunk;
        private long offset;
        public long requestStartTime;
        private int requestToken;
        private TLRPC$TL_upload_file response;
        private TLRPC$TL_upload_cdnFile responseCdn;
        private TLRPC$TL_upload_webFile responseWeb;

        protected RequestInfo() {
        }
    }

    public static class Range {
        private long end;
        private long start;

        private Range(long j, long j2) {
            this.start = j;
            this.end = j2;
        }
    }

    private static class PreloadRange {
        private long fileOffset;
        private long length;

        private PreloadRange(long j, long j2) {
            this.fileOffset = j;
            this.length = j2;
        }
    }

    private void updateParams() {
        if (MessagesController.getInstance(this.currentAccount).getfileExperimentalParams && !this.forceSmallChunk) {
            this.downloadChunkSizeBig = 524288;
            this.maxDownloadRequests = 8;
            this.maxDownloadRequestsBig = 8;
        } else {
            this.downloadChunkSizeBig = 131072;
            this.maxDownloadRequests = 4;
            this.maxDownloadRequestsBig = 4;
        }
        this.maxCdnParts = (int) (FileLoader.DEFAULT_MAX_FILE_SIZE / this.downloadChunkSizeBig);
    }

    public FileLoadOperation(ImageLocation imageLocation, Object obj, String str, long j) {
        this.downloadChunkSize = LiteMode.FLAG_CHAT_SCALE;
        this.downloadChunkSizeBig = 131072;
        this.cdnChunkCheckSize = 131072;
        this.maxDownloadRequests = 4;
        this.maxDownloadRequestsBig = 4;
        this.bigFileSizeFrom = 10485760;
        this.maxCdnParts = (int) (FileLoader.DEFAULT_MAX_FILE_SIZE / 131072);
        this.downloadChunkSizeAnimation = 131072;
        this.maxDownloadRequestsAnimation = 4;
        this.preloadTempBuffer = new byte[24];
        this.state = 0;
        updateParams();
        this.parentObject = obj;
        this.fileMetadata = FileLoader.getFileMetadataFromParent(this.currentAccount, obj);
        this.isStream = imageLocation.imageType == 2;
        if (imageLocation.isEncrypted()) {
            TLRPC$InputFileLocation tLRPC$InputFileLocation = new TLRPC$InputFileLocation() { // from class: org.telegram.tgnet.TLRPC$TL_inputEncryptedFileLocation
                public static int constructor = -182231723;

                @Override // org.telegram.tgnet.TLObject
                public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
                    this.id = abstractSerializedData.readInt64(z);
                    this.access_hash = abstractSerializedData.readInt64(z);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                    abstractSerializedData.writeInt64(this.id);
                    abstractSerializedData.writeInt64(this.access_hash);
                }
            };
            this.location = tLRPC$InputFileLocation;
            TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated = imageLocation.location;
            long j2 = tLRPC$TL_fileLocationToBeDeprecated.volume_id;
            tLRPC$InputFileLocation.id = j2;
            tLRPC$InputFileLocation.volume_id = j2;
            tLRPC$InputFileLocation.local_id = tLRPC$TL_fileLocationToBeDeprecated.local_id;
            tLRPC$InputFileLocation.access_hash = imageLocation.access_hash;
            byte[] bArr = new byte[32];
            this.iv = bArr;
            System.arraycopy(imageLocation.iv, 0, bArr, 0, bArr.length);
            this.key = imageLocation.key;
        } else if (imageLocation.photoPeer != null) {
            TLRPC$TL_inputPeerPhotoFileLocation tLRPC$TL_inputPeerPhotoFileLocation = new TLRPC$TL_inputPeerPhotoFileLocation();
            TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated2 = imageLocation.location;
            long j3 = tLRPC$TL_fileLocationToBeDeprecated2.volume_id;
            tLRPC$TL_inputPeerPhotoFileLocation.id = j3;
            tLRPC$TL_inputPeerPhotoFileLocation.volume_id = j3;
            tLRPC$TL_inputPeerPhotoFileLocation.local_id = tLRPC$TL_fileLocationToBeDeprecated2.local_id;
            tLRPC$TL_inputPeerPhotoFileLocation.photo_id = imageLocation.photoId;
            tLRPC$TL_inputPeerPhotoFileLocation.big = imageLocation.photoPeerType == 0;
            tLRPC$TL_inputPeerPhotoFileLocation.peer = imageLocation.photoPeer;
            this.location = tLRPC$TL_inputPeerPhotoFileLocation;
        } else if (imageLocation.stickerSet != null) {
            TLRPC$TL_inputStickerSetThumb tLRPC$TL_inputStickerSetThumb = new TLRPC$TL_inputStickerSetThumb();
            TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated3 = imageLocation.location;
            long j4 = tLRPC$TL_fileLocationToBeDeprecated3.volume_id;
            tLRPC$TL_inputStickerSetThumb.id = j4;
            tLRPC$TL_inputStickerSetThumb.volume_id = j4;
            tLRPC$TL_inputStickerSetThumb.local_id = tLRPC$TL_fileLocationToBeDeprecated3.local_id;
            tLRPC$TL_inputStickerSetThumb.thumb_version = imageLocation.thumbVersion;
            tLRPC$TL_inputStickerSetThumb.stickerset = imageLocation.stickerSet;
            this.location = tLRPC$TL_inputStickerSetThumb;
        } else if (imageLocation.thumbSize != null) {
            if (imageLocation.photoId != 0) {
                TLRPC$TL_inputPhotoFileLocation tLRPC$TL_inputPhotoFileLocation = new TLRPC$TL_inputPhotoFileLocation();
                this.location = tLRPC$TL_inputPhotoFileLocation;
                tLRPC$TL_inputPhotoFileLocation.id = imageLocation.photoId;
                TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated4 = imageLocation.location;
                tLRPC$TL_inputPhotoFileLocation.volume_id = tLRPC$TL_fileLocationToBeDeprecated4.volume_id;
                tLRPC$TL_inputPhotoFileLocation.local_id = tLRPC$TL_fileLocationToBeDeprecated4.local_id;
                tLRPC$TL_inputPhotoFileLocation.access_hash = imageLocation.access_hash;
                tLRPC$TL_inputPhotoFileLocation.file_reference = imageLocation.file_reference;
                tLRPC$TL_inputPhotoFileLocation.thumb_size = imageLocation.thumbSize;
                if (imageLocation.imageType == 2) {
                    this.allowDisordererFileSave = true;
                }
            } else {
                TLRPC$TL_inputDocumentFileLocation tLRPC$TL_inputDocumentFileLocation = new TLRPC$TL_inputDocumentFileLocation();
                this.location = tLRPC$TL_inputDocumentFileLocation;
                tLRPC$TL_inputDocumentFileLocation.id = imageLocation.documentId;
                TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated5 = imageLocation.location;
                tLRPC$TL_inputDocumentFileLocation.volume_id = tLRPC$TL_fileLocationToBeDeprecated5.volume_id;
                tLRPC$TL_inputDocumentFileLocation.local_id = tLRPC$TL_fileLocationToBeDeprecated5.local_id;
                tLRPC$TL_inputDocumentFileLocation.access_hash = imageLocation.access_hash;
                tLRPC$TL_inputDocumentFileLocation.file_reference = imageLocation.file_reference;
                tLRPC$TL_inputDocumentFileLocation.thumb_size = imageLocation.thumbSize;
            }
            TLRPC$InputFileLocation tLRPC$InputFileLocation2 = this.location;
            if (tLRPC$InputFileLocation2.file_reference == null) {
                tLRPC$InputFileLocation2.file_reference = new byte[0];
            }
        } else {
            TLRPC$InputFileLocation tLRPC$InputFileLocation3 = new TLRPC$InputFileLocation() { // from class: org.telegram.tgnet.TLRPC$TL_inputFileLocation
                public static int constructor = -539317279;

                @Override // org.telegram.tgnet.TLObject
                public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
                    this.volume_id = abstractSerializedData.readInt64(z);
                    this.local_id = abstractSerializedData.readInt32(z);
                    this.secret = abstractSerializedData.readInt64(z);
                    this.file_reference = abstractSerializedData.readByteArray(z);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                    abstractSerializedData.writeInt32(constructor);
                    abstractSerializedData.writeInt64(this.volume_id);
                    abstractSerializedData.writeInt32(this.local_id);
                    abstractSerializedData.writeInt64(this.secret);
                    abstractSerializedData.writeByteArray(this.file_reference);
                }
            };
            this.location = tLRPC$InputFileLocation3;
            TLRPC$TL_fileLocationToBeDeprecated tLRPC$TL_fileLocationToBeDeprecated6 = imageLocation.location;
            tLRPC$InputFileLocation3.volume_id = tLRPC$TL_fileLocationToBeDeprecated6.volume_id;
            tLRPC$InputFileLocation3.local_id = tLRPC$TL_fileLocationToBeDeprecated6.local_id;
            tLRPC$InputFileLocation3.secret = imageLocation.access_hash;
            byte[] bArr2 = imageLocation.file_reference;
            tLRPC$InputFileLocation3.file_reference = bArr2;
            if (bArr2 == null) {
                tLRPC$InputFileLocation3.file_reference = new byte[0];
            }
            this.allowDisordererFileSave = true;
        }
        int i = imageLocation.imageType;
        this.ungzip = i == 1 || i == 3;
        int i2 = imageLocation.dc_id;
        this.datacenterId = i2;
        this.initialDatacenterId = i2;
        this.currentType = ConnectionsManager.FileTypePhoto;
        this.totalBytesCount = j;
        this.ext = str == null ? "jpg" : str;
    }

    public FileLoadOperation(SecureDocument secureDocument) {
        this.downloadChunkSize = LiteMode.FLAG_CHAT_SCALE;
        this.downloadChunkSizeBig = 131072;
        this.cdnChunkCheckSize = 131072;
        this.maxDownloadRequests = 4;
        this.maxDownloadRequestsBig = 4;
        this.bigFileSizeFrom = 10485760;
        this.maxCdnParts = (int) (FileLoader.DEFAULT_MAX_FILE_SIZE / 131072);
        this.downloadChunkSizeAnimation = 131072;
        this.maxDownloadRequestsAnimation = 4;
        this.preloadTempBuffer = new byte[24];
        this.state = 0;
        updateParams();
        TLRPC$InputFileLocation tLRPC$InputFileLocation = new TLRPC$InputFileLocation() { // from class: org.telegram.tgnet.TLRPC$TL_inputSecureFileLocation
            public static int constructor = -876089816;

            @Override // org.telegram.tgnet.TLObject
            public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
                this.id = abstractSerializedData.readInt64(z);
                this.access_hash = abstractSerializedData.readInt64(z);
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
                abstractSerializedData.writeInt64(this.id);
                abstractSerializedData.writeInt64(this.access_hash);
            }
        };
        this.location = tLRPC$InputFileLocation;
        TLRPC$TL_secureFile tLRPC$TL_secureFile = secureDocument.secureFile;
        tLRPC$InputFileLocation.id = tLRPC$TL_secureFile.id;
        tLRPC$InputFileLocation.access_hash = tLRPC$TL_secureFile.access_hash;
        this.datacenterId = tLRPC$TL_secureFile.dc_id;
        this.totalBytesCount = tLRPC$TL_secureFile.size;
        this.allowDisordererFileSave = true;
        this.currentType = ConnectionsManager.FileTypeFile;
        this.ext = ".jpg";
    }

    public FileLoadOperation(int i, WebFile webFile) {
        this.downloadChunkSize = LiteMode.FLAG_CHAT_SCALE;
        this.downloadChunkSizeBig = 131072;
        this.cdnChunkCheckSize = 131072;
        this.maxDownloadRequests = 4;
        this.maxDownloadRequestsBig = 4;
        this.bigFileSizeFrom = 10485760;
        this.maxCdnParts = (int) (FileLoader.DEFAULT_MAX_FILE_SIZE / 131072);
        this.downloadChunkSizeAnimation = 131072;
        this.maxDownloadRequestsAnimation = 4;
        this.preloadTempBuffer = new byte[24];
        this.state = 0;
        updateParams();
        this.currentAccount = i;
        this.webFile = webFile;
        this.webLocation = webFile.location;
        this.totalBytesCount = webFile.size;
        int i2 = MessagesController.getInstance(i).webFileDatacenterId;
        this.datacenterId = i2;
        this.initialDatacenterId = i2;
        String mimeTypePart = FileLoader.getMimeTypePart(webFile.mime_type);
        if (webFile.mime_type.startsWith("image/")) {
            this.currentType = ConnectionsManager.FileTypePhoto;
        } else if (webFile.mime_type.equals("audio/ogg")) {
            this.currentType = ConnectionsManager.FileTypeAudio;
        } else if (webFile.mime_type.startsWith("video/")) {
            this.currentType = ConnectionsManager.FileTypeVideo;
        } else {
            this.currentType = ConnectionsManager.FileTypeFile;
        }
        this.allowDisordererFileSave = true;
        this.ext = ImageLoader.getHttpUrlExtension(webFile.url, mimeTypePart);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0101 A[Catch: Exception -> 0x0128, TryCatch #0 {Exception -> 0x0128, blocks: (B:3:0x0030, B:6:0x0040, B:7:0x00a6, B:9:0x00b0, B:13:0x00be, B:15:0x00c8, B:17:0x00d2, B:18:0x00da, B:20:0x00e2, B:23:0x00ec, B:24:0x00f7, B:26:0x0101, B:27:0x0117, B:29:0x011f, B:34:0x0106, B:36:0x010e, B:37:0x0113, B:38:0x00f5, B:40:0x0066, B:42:0x006a, B:44:0x0081, B:45:0x0085, B:47:0x0096, B:51:0x00a0, B:49:0x00a3), top: B:2:0x0030 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x011f A[Catch: Exception -> 0x0128, TRY_LEAVE, TryCatch #0 {Exception -> 0x0128, blocks: (B:3:0x0030, B:6:0x0040, B:7:0x00a6, B:9:0x00b0, B:13:0x00be, B:15:0x00c8, B:17:0x00d2, B:18:0x00da, B:20:0x00e2, B:23:0x00ec, B:24:0x00f7, B:26:0x0101, B:27:0x0117, B:29:0x011f, B:34:0x0106, B:36:0x010e, B:37:0x0113, B:38:0x00f5, B:40:0x0066, B:42:0x006a, B:44:0x0081, B:45:0x0085, B:47:0x0096, B:51:0x00a0, B:49:0x00a3), top: B:2:0x0030 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0106 A[Catch: Exception -> 0x0128, TryCatch #0 {Exception -> 0x0128, blocks: (B:3:0x0030, B:6:0x0040, B:7:0x00a6, B:9:0x00b0, B:13:0x00be, B:15:0x00c8, B:17:0x00d2, B:18:0x00da, B:20:0x00e2, B:23:0x00ec, B:24:0x00f7, B:26:0x0101, B:27:0x0117, B:29:0x011f, B:34:0x0106, B:36:0x010e, B:37:0x0113, B:38:0x00f5, B:40:0x0066, B:42:0x006a, B:44:0x0081, B:45:0x0085, B:47:0x0096, B:51:0x00a0, B:49:0x00a3), top: B:2:0x0030 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public FileLoadOperation(org.telegram.tgnet.TLRPC$Document r12, java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoadOperation.<init>(org.telegram.tgnet.TLRPC$Document, java.lang.Object):void");
    }

    public void setEncryptFile(boolean z) {
        this.encryptFile = z;
        if (z) {
            this.allowDisordererFileSave = false;
        }
    }

    public int getDatacenterId() {
        return this.initialDatacenterId;
    }

    public void setForceRequest(boolean z) {
        this.isForceRequest = z;
    }

    public boolean isForceRequest() {
        return this.isForceRequest;
    }

    public void setPriority(int i) {
        this.priority = i;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPaths(int i, String str, FileLoaderPriorityQueue fileLoaderPriorityQueue, File file, File file2, String str2) {
        this.storePath = file;
        this.tempPath = file2;
        this.currentAccount = i;
        this.fileName = str;
        this.storeFileName = str2;
        this.priorityQueue = fileLoaderPriorityQueue;
    }

    public FileLoaderPriorityQueue getQueue() {
        return this.priorityQueue;
    }

    public boolean wasStarted() {
        return this.started && !this.paused;
    }

    public int getCurrentType() {
        return this.currentType;
    }

    private void removePart(ArrayList<Range> arrayList, long j, long j2) {
        boolean z;
        if (arrayList == null || j2 < j) {
            return;
        }
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Range range = arrayList.get(i2);
            if (j == range.end) {
                range.end = j2;
            } else if (j2 == range.start) {
                range.start = j;
            }
            z = true;
        }
        z = false;
        Collections.sort(arrayList, new Comparator() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda16
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$removePart$1;
                lambda$removePart$1 = FileLoadOperation.lambda$removePart$1((FileLoadOperation.Range) obj, (FileLoadOperation.Range) obj2);
                return lambda$removePart$1;
            }
        });
        while (i < arrayList.size() - 1) {
            Range range2 = arrayList.get(i);
            int i3 = i + 1;
            Range range3 = arrayList.get(i3);
            if (range2.end == range3.start) {
                range2.end = range3.end;
                arrayList.remove(i3);
                i--;
            }
            i++;
        }
        if (z) {
            return;
        }
        arrayList.add(new Range(j, j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$removePart$1(Range range, Range range2) {
        if (range.start > range2.start) {
            return 1;
        }
        return range.start < range2.start ? -1 : 0;
    }

    private void addPart(ArrayList<Range> arrayList, long j, long j2, boolean z) {
        if (arrayList == null || j2 < j) {
            return;
        }
        int size = arrayList.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            Range range = arrayList.get(i);
            if (j > range.start) {
                if (j2 < range.end) {
                    arrayList.add(0, new Range(range.start, j));
                    range.start = j2;
                } else if (j < range.end) {
                    range.end = j;
                }
                z2 = true;
                break;
            }
            if (j2 >= range.end) {
                arrayList.remove(i);
            } else if (j2 > range.start) {
                range.start = j2;
            }
            z2 = true;
            break;
        }
        if (z) {
            if (z2) {
                final ArrayList arrayList2 = new ArrayList(arrayList);
                if (this.fileWriteRunnable != null) {
                    filesQueue.cancelRunnable(this.fileWriteRunnable);
                }
                DispatchQueue dispatchQueue = filesQueue;
                Runnable runnable = new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        FileLoadOperation.this.lambda$addPart$2(arrayList2);
                    }
                };
                this.fileWriteRunnable = runnable;
                dispatchQueue.postRunnable(runnable);
                notifyStreamListeners();
                return;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.e(this.cacheFileFinal + " downloaded duplicate file part " + j + " - " + j2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addPart$2(ArrayList arrayList) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
        } catch (Exception e) {
            FileLog.e((Throwable) e, false);
            if (AndroidUtilities.isENOSPC(e)) {
                LaunchActivity.checkFreeDiscSpaceStatic(1);
            } else if (AndroidUtilities.isEROFS(e)) {
                SharedConfig.checkSdCard(this.cacheFileFinal);
            }
        }
        if (this.filePartsStream == null) {
            return;
        }
        int size = arrayList.size();
        int i = (size * 16) + 4;
        ImmutableByteArrayOutputStream immutableByteArrayOutputStream = filesQueueByteBuffer;
        if (immutableByteArrayOutputStream == null) {
            filesQueueByteBuffer = new ImmutableByteArrayOutputStream(i);
        } else {
            immutableByteArrayOutputStream.reset();
        }
        filesQueueByteBuffer.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Range range = (Range) arrayList.get(i2);
            filesQueueByteBuffer.writeLong(range.start);
            filesQueueByteBuffer.writeLong(range.end);
        }
        synchronized (this) {
            RandomAccessFile randomAccessFile = this.filePartsStream;
            if (randomAccessFile == null) {
                return;
            }
            randomAccessFile.seek(0L);
            this.filePartsStream.write(filesQueueByteBuffer.buf, 0, i);
            this.totalTime += System.currentTimeMillis() - currentTimeMillis;
        }
    }

    private void notifyStreamListeners() {
        ArrayList<FileLoadOperationStream> arrayList = this.streamListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                this.streamListeners.get(i).newDataAvailable();
            }
        }
    }

    protected File getCacheFileFinal() {
        return this.cacheFileFinal;
    }

    protected File getCurrentFile() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final File[] fileArr = new File[1];
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$getCurrentFile$3(fileArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            FileLog.e(e);
        }
        return fileArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCurrentFile$3(File[] fileArr, CountDownLatch countDownLatch) {
        if (this.state == 3) {
            fileArr[0] = this.cacheFileFinal;
        } else {
            fileArr[0] = this.cacheFileTemp;
        }
        countDownLatch.countDown();
    }

    protected File getCurrentFileFast() {
        if (this.state == 3) {
            return this.cacheFileFinal;
        }
        return this.cacheFileTemp;
    }

    private long getDownloadedLengthFromOffsetInternal(ArrayList<Range> arrayList, long j, long j2) {
        long j3;
        if (arrayList == null || this.state == 3 || arrayList.isEmpty()) {
            if (this.state == 3) {
                return j2;
            }
            long j4 = this.downloadedBytes;
            if (j4 == 0) {
                return 0L;
            }
            return Math.min(j2, Math.max(j4 - j, 0L));
        }
        int size = arrayList.size();
        Range range = null;
        int i = 0;
        while (true) {
            if (i >= size) {
                j3 = j2;
                break;
            }
            Range range2 = arrayList.get(i);
            if (j <= range2.start && (range == null || range2.start < range.start)) {
                range = range2;
            }
            if (range2.start <= j && range2.end > j) {
                j3 = 0;
                break;
            }
            i++;
        }
        if (j3 == 0) {
            return 0L;
        }
        if (range != null) {
            return Math.min(j2, range.start - j);
        }
        return Math.min(j2, Math.max(this.totalBytesCount - j, 0L));
    }

    protected float getDownloadedLengthFromOffset(float f) {
        ArrayList<Range> arrayList = this.notLoadedBytesRangesCopy;
        if (this.totalBytesCount == 0 || arrayList == null) {
            return 0.0f;
        }
        return f + (getDownloadedLengthFromOffsetInternal(arrayList, (int) (r4 * f), r4) / this.totalBytesCount);
    }

    protected long[] getDownloadedLengthFromOffset(final long j, final long j2) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final long[] jArr = new long[2];
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$getDownloadedLengthFromOffset$4(jArr, j, j2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception unused) {
        }
        return jArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDownloadedLengthFromOffset$4(long[] jArr, long j, long j2, CountDownLatch countDownLatch) {
        jArr[0] = getDownloadedLengthFromOffsetInternal(this.notLoadedBytesRanges, j, j2);
        if (this.state == 3) {
            jArr[1] = 1;
        }
        countDownLatch.countDown();
    }

    public String getFileName() {
        return this.fileName;
    }

    protected void removeStreamListener(final FileLoadOperationStream fileLoadOperationStream) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$removeStreamListener$5(fileLoadOperationStream);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeStreamListener$5(FileLoadOperationStream fileLoadOperationStream) {
        ArrayList<FileLoadOperationStream> arrayList = this.streamListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(fileLoadOperationStream);
    }

    private void copyNotLoadedRanges() {
        if (this.notLoadedBytesRanges == null) {
            return;
        }
        this.notLoadedBytesRangesCopy = new ArrayList<>(this.notLoadedBytesRanges);
    }

    public void pause() {
        if (this.state != 1) {
            return;
        }
        this.paused = true;
    }

    public boolean start() {
        return start(this.stream, this.streamOffset, this.streamPriority);
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x03ea, code lost:
    
        if (r10 != r28.cacheFileFinal.length()) goto L117;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0608  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0613 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0633  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x06ae  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x06d8  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x075c  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0788  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x07d1  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x083f  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0864 A[Catch: Exception -> 0x086a, TRY_LEAVE, TryCatch #1 {Exception -> 0x086a, blocks: (B:220:0x0853, B:222:0x0864), top: B:219:0x0853 }] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0896  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x089a  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0731  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x06a8  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x08a9  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0402  */
    /* JADX WARN: Type inference failed for: r1v39 */
    /* JADX WARN: Type inference failed for: r1v40, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v44 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean start(org.telegram.messenger.FileLoadOperationStream r29, final long r30, final boolean r32) {
        /*
            Method dump skipped, instructions count: 2288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoadOperation.start(org.telegram.messenger.FileLoadOperationStream, long, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$6(boolean z, long j, boolean z2) {
        if (this.streamListeners == null) {
            this.streamListeners = new ArrayList<>();
        }
        if (z) {
            int i = this.currentDownloadChunkSize;
            long j2 = (j / i) * i;
            RequestInfo requestInfo = this.priorityRequestInfo;
            if (requestInfo != null && requestInfo.offset != j2) {
                this.requestInfos.remove(this.priorityRequestInfo);
                this.requestedBytesCount -= this.currentDownloadChunkSize;
                removePart(this.notRequestedBytesRanges, this.priorityRequestInfo.offset, this.currentDownloadChunkSize + this.priorityRequestInfo.offset);
                if (this.priorityRequestInfo.requestToken != 0) {
                    ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.priorityRequestInfo.requestToken, false);
                    this.requestsCount--;
                }
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.d("frame get cancel request at offset " + this.priorityRequestInfo.offset);
                }
                this.priorityRequestInfo = null;
            }
            if (this.priorityRequestInfo == null) {
                this.streamPriorityStartOffset = j2;
            }
        } else {
            int i2 = this.currentDownloadChunkSize;
            this.streamStartOffset = (j / i2) * i2;
        }
        if (z2) {
            if (this.preloadedBytesRanges != null && getDownloadedLengthFromOffsetInternal(this.notLoadedBytesRanges, this.streamStartOffset, 1L) == 0 && this.preloadedBytesRanges.get(Long.valueOf(this.streamStartOffset)) != null) {
                this.nextPartWasPreloaded = true;
            }
            startDownloadRequest();
            this.nextPartWasPreloaded = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$7(boolean[] zArr) {
        long j = this.totalBytesCount;
        if (j != 0 && ((this.isPreloadVideoOperation && zArr[0]) || this.downloadedBytes == j)) {
            try {
                onFinishLoadingFile(false, 1);
                return;
            } catch (Exception unused) {
                onFail(true, 0);
                return;
            }
        }
        startDownloadRequest();
    }

    public void updateProgress() {
        FileLoadOperationDelegate fileLoadOperationDelegate = this.delegate;
        if (fileLoadOperationDelegate != null) {
            long j = this.downloadedBytes;
            long j2 = this.totalBytesCount;
            if (j == j2 || j2 <= 0) {
                return;
            }
            fileLoadOperationDelegate.didChangedLoadProgress(this, j, j2);
        }
    }

    public boolean isPaused() {
        return this.paused;
    }

    public void setIsPreloadVideoOperation(final boolean z) {
        boolean z2 = this.isPreloadVideoOperation;
        if (z2 != z) {
            if (!z || this.totalBytesCount > 2097152) {
                if (!z && z2) {
                    if (this.state == 3) {
                        this.isPreloadVideoOperation = z;
                        this.state = 0;
                        this.preloadFinished = false;
                        start();
                        return;
                    }
                    if (this.state == 1) {
                        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileLoadOperation.this.lambda$setIsPreloadVideoOperation$8(z);
                            }
                        });
                        return;
                    } else {
                        this.isPreloadVideoOperation = z;
                        return;
                    }
                }
                this.isPreloadVideoOperation = z;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsPreloadVideoOperation$8(boolean z) {
        this.requestedBytesCount = 0L;
        clearOperaion(null, true);
        this.isPreloadVideoOperation = z;
        startDownloadRequest();
    }

    public boolean isPreloadVideoOperation() {
        return this.isPreloadVideoOperation;
    }

    public boolean isPreloadFinished() {
        return this.preloadFinished;
    }

    public void cancel() {
        cancel(false);
    }

    private void cancel(final boolean z) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$cancel$9(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancel$9(boolean z) {
        if (this.state != 3 && this.state != 2) {
            cancelRequests();
            onFail(false, 1);
        }
        if (z) {
            File file = this.cacheFileFinal;
            if (file != null) {
                try {
                    if (!file.delete()) {
                        this.cacheFileFinal.deleteOnExit();
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            File file2 = this.cacheFileTemp;
            if (file2 != null) {
                try {
                    if (!file2.delete()) {
                        this.cacheFileTemp.deleteOnExit();
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            File file3 = this.cacheFileParts;
            if (file3 != null) {
                try {
                    if (!file3.delete()) {
                        this.cacheFileParts.deleteOnExit();
                    }
                } catch (Exception e3) {
                    FileLog.e(e3);
                }
            }
            File file4 = this.cacheIvTemp;
            if (file4 != null) {
                try {
                    if (!file4.delete()) {
                        this.cacheIvTemp.deleteOnExit();
                    }
                } catch (Exception e4) {
                    FileLog.e(e4);
                }
            }
            File file5 = this.cacheFilePreload;
            if (file5 != null) {
                try {
                    if (file5.delete()) {
                        return;
                    }
                    this.cacheFilePreload.deleteOnExit();
                } catch (Exception e5) {
                    FileLog.e(e5);
                }
            }
        }
    }

    private void cancelRequests() {
        if (this.requestInfos != null) {
            for (int i = 0; i < this.requestInfos.size(); i++) {
                RequestInfo requestInfo = this.requestInfos.get(i);
                if (requestInfo.requestToken != 0) {
                    ConnectionsManager.getInstance(this.currentAccount).cancelRequest(requestInfo.requestToken, false);
                }
            }
        }
    }

    private void cleanup() {
        try {
            RandomAccessFile randomAccessFile = this.fileOutputStream;
            if (randomAccessFile != null) {
                try {
                    randomAccessFile.getChannel().close();
                } catch (Exception e) {
                    FileLog.e(e);
                }
                this.fileOutputStream.close();
                this.fileOutputStream = null;
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            RandomAccessFile randomAccessFile2 = this.preloadStream;
            if (randomAccessFile2 != null) {
                try {
                    randomAccessFile2.getChannel().close();
                } catch (Exception e3) {
                    FileLog.e(e3);
                }
                this.preloadStream.close();
                this.preloadStream = null;
            }
        } catch (Exception e4) {
            FileLog.e(e4);
        }
        try {
            RandomAccessFile randomAccessFile3 = this.fileReadStream;
            if (randomAccessFile3 != null) {
                try {
                    randomAccessFile3.getChannel().close();
                } catch (Exception e5) {
                    FileLog.e(e5);
                }
                this.fileReadStream.close();
                this.fileReadStream = null;
            }
        } catch (Exception e6) {
            FileLog.e(e6);
        }
        try {
            if (this.filePartsStream != null) {
                synchronized (this) {
                    try {
                        this.filePartsStream.getChannel().close();
                    } catch (Exception e7) {
                        FileLog.e(e7);
                    }
                    this.filePartsStream.close();
                    this.filePartsStream = null;
                }
            }
        } catch (Exception e8) {
            FileLog.e(e8);
        }
        try {
            RandomAccessFile randomAccessFile4 = this.fiv;
            if (randomAccessFile4 != null) {
                randomAccessFile4.close();
                this.fiv = null;
            }
        } catch (Exception e9) {
            FileLog.e(e9);
        }
        if (this.delayedRequestInfos != null) {
            for (int i = 0; i < this.delayedRequestInfos.size(); i++) {
                RequestInfo requestInfo = this.delayedRequestInfos.get(i);
                if (requestInfo.response != null) {
                    requestInfo.response.disableFree = false;
                    requestInfo.response.freeResources();
                } else if (requestInfo.responseWeb != null) {
                    requestInfo.responseWeb.disableFree = false;
                    requestInfo.responseWeb.freeResources();
                } else if (requestInfo.responseCdn != null) {
                    requestInfo.responseCdn.disableFree = false;
                    requestInfo.responseCdn.freeResources();
                }
            }
            this.delayedRequestInfos.clear();
        }
    }

    private void onFinishLoadingFile(final boolean z, int i) {
        if (this.state != 1) {
            return;
        }
        this.state = 3;
        notifyStreamListeners();
        cleanup();
        if (this.isPreloadVideoOperation) {
            this.preloadFinished = true;
            if (BuildVars.DEBUG_VERSION) {
                if (i == 1) {
                    FileLog.d("file already exist " + this.cacheFileTemp);
                } else {
                    FileLog.d("finished preloading file to " + this.cacheFileTemp + " loaded " + this.totalPreloadedBytes + " of " + this.totalBytesCount);
                }
            }
            if (this.fileMetadata != null) {
                if (this.cacheFileTemp != null) {
                    FileLoader.getInstance(this.currentAccount).getFileDatabase().removeFiles(Collections.singletonList(new CacheModel.FileInfo(this.cacheFileTemp)));
                }
                if (this.cacheFileParts != null) {
                    FileLoader.getInstance(this.currentAccount).getFileDatabase().removeFiles(Collections.singletonList(new CacheModel.FileInfo(this.cacheFileParts)));
                }
            }
            this.delegate.didFinishLoadingFile(this, this.cacheFileFinal);
            return;
        }
        final File file = this.cacheIvTemp;
        final File file2 = this.cacheFileParts;
        final File file3 = this.cacheFilePreload;
        final File file4 = this.cacheFileTemp;
        filesQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                FileLoadOperation.this.lambda$onFinishLoadingFile$13(file, file2, file3, file4, z);
            }
        });
        this.cacheIvTemp = null;
        this.cacheFileParts = null;
        this.cacheFilePreload = null;
        this.delegate.didPreFinishLoading(this, this.cacheFileFinal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x017c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ void lambda$onFinishLoadingFile$13(java.io.File r5, java.io.File r6, java.io.File r7, java.io.File r8, final boolean r9) {
        /*
            Method dump skipped, instructions count: 402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoadOperation.lambda$onFinishLoadingFile$13(java.io.File, java.io.File, java.io.File, java.io.File, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishLoadingFile$10(boolean z) {
        try {
            onFinishLoadingFile(z, 0);
        } catch (Exception unused) {
            onFail(false, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishLoadingFile$11() {
        onFail(false, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishLoadingFile$12(boolean z) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("finished downloading file to " + this.cacheFileFinal + " time = " + (System.currentTimeMillis() - this.startTime) + " dc = " + this.datacenterId + " size = " + AndroidUtilities.formatFileSize(this.totalBytesCount));
        }
        if (z) {
            int i = this.currentType;
            if (i == 50331648) {
                StatsController.getInstance(this.currentAccount).incrementReceivedItemsCount(ApplicationLoader.getCurrentNetworkType(), 3, 1);
            } else if (i == 33554432) {
                StatsController.getInstance(this.currentAccount).incrementReceivedItemsCount(ApplicationLoader.getCurrentNetworkType(), 2, 1);
            } else if (i == 16777216) {
                StatsController.getInstance(this.currentAccount).incrementReceivedItemsCount(ApplicationLoader.getCurrentNetworkType(), 4, 1);
            } else if (i == 67108864) {
                String str = this.ext;
                if (str != null && (str.toLowerCase().endsWith("mp3") || this.ext.toLowerCase().endsWith("m4a"))) {
                    StatsController.getInstance(this.currentAccount).incrementReceivedItemsCount(ApplicationLoader.getCurrentNetworkType(), 7, 1);
                } else {
                    StatsController.getInstance(this.currentAccount).incrementReceivedItemsCount(ApplicationLoader.getCurrentNetworkType(), 5, 1);
                }
            }
        }
        this.delegate.didFinishLoadingFile(this, this.cacheFileFinal);
    }

    private void delayRequestInfo(RequestInfo requestInfo) {
        this.delayedRequestInfos.add(requestInfo);
        if (requestInfo.response != null) {
            requestInfo.response.disableFree = true;
        } else if (requestInfo.responseWeb != null) {
            requestInfo.responseWeb.disableFree = true;
        } else if (requestInfo.responseCdn != null) {
            requestInfo.responseCdn.disableFree = true;
        }
    }

    private long findNextPreloadDownloadOffset(long j, long j2, NativeByteBuffer nativeByteBuffer) {
        long j3;
        int limit = nativeByteBuffer.limit();
        long j4 = j;
        do {
            if (j4 >= j2 - (this.preloadTempBuffer != null ? 16 : 0)) {
                j3 = j2 + limit;
                if (j4 < j3) {
                    if (j4 >= j3 - 16) {
                        long j5 = j3 - j4;
                        if (j5 > 2147483647L) {
                            throw new RuntimeException("!!!");
                        }
                        this.preloadTempBufferCount = (int) j5;
                        nativeByteBuffer.position(nativeByteBuffer.limit() - this.preloadTempBufferCount);
                        nativeByteBuffer.readBytes(this.preloadTempBuffer, 0, this.preloadTempBufferCount, false);
                        return j3;
                    }
                    if (this.preloadTempBufferCount != 0) {
                        nativeByteBuffer.position(0);
                        byte[] bArr = this.preloadTempBuffer;
                        int i = this.preloadTempBufferCount;
                        nativeByteBuffer.readBytes(bArr, i, 16 - i, false);
                        this.preloadTempBufferCount = 0;
                    } else {
                        long j6 = j4 - j2;
                        if (j6 > 2147483647L) {
                            throw new RuntimeException("!!!");
                        }
                        nativeByteBuffer.position((int) j6);
                        nativeByteBuffer.readBytes(this.preloadTempBuffer, 0, 16, false);
                    }
                    byte[] bArr2 = this.preloadTempBuffer;
                    int i2 = ((bArr2[0] & 255) << 24) + ((bArr2[1] & 255) << 16) + ((bArr2[2] & 255) << 8) + (bArr2[3] & 255);
                    if (i2 == 0) {
                        return 0L;
                    }
                    if (i2 == 1) {
                        i2 = ((bArr2[12] & 255) << 24) + ((bArr2[13] & 255) << 16) + ((bArr2[14] & 255) << 8) + (bArr2[15] & 255);
                    }
                    if (bArr2[4] == 109 && bArr2[5] == 111 && bArr2[6] == 111 && bArr2[7] == 118) {
                        return -i2;
                    }
                    j4 += i2;
                }
            }
            return 0L;
        } while (j4 < j3);
        return j4;
    }

    private void requestFileOffsets(long j) {
        if (this.requestingCdnOffsets) {
            return;
        }
        this.requestingCdnOffsets = true;
        TLRPC$TL_upload_getCdnFileHashes tLRPC$TL_upload_getCdnFileHashes = new TLRPC$TL_upload_getCdnFileHashes();
        tLRPC$TL_upload_getCdnFileHashes.file_token = this.cdnToken;
        tLRPC$TL_upload_getCdnFileHashes.offset = j;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_upload_getCdnFileHashes, new RequestDelegate() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda17
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                FileLoadOperation.this.lambda$requestFileOffsets$14(tLObject, tLRPC$TL_error);
            }
        }, null, null, 0, this.datacenterId, 1, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestFileOffsets$14(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        if (tLRPC$TL_error != null) {
            onFail(false, 0);
            return;
        }
        this.requestingCdnOffsets = false;
        TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
        if (!tLRPC$Vector.objects.isEmpty()) {
            if (this.cdnHashes == null) {
                this.cdnHashes = new HashMap<>();
            }
            for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                TLRPC$TL_fileHash tLRPC$TL_fileHash = (TLRPC$TL_fileHash) tLRPC$Vector.objects.get(i);
                this.cdnHashes.put(Long.valueOf(tLRPC$TL_fileHash.offset), tLRPC$TL_fileHash);
            }
        }
        for (int i2 = 0; i2 < this.delayedRequestInfos.size(); i2++) {
            RequestInfo requestInfo = this.delayedRequestInfos.get(i2);
            if (this.notLoadedBytesRanges != null || this.downloadedBytes == requestInfo.offset) {
                this.delayedRequestInfos.remove(i2);
                if (processRequestResult(requestInfo, null)) {
                    return;
                }
                if (requestInfo.response != null) {
                    requestInfo.response.disableFree = false;
                    requestInfo.response.freeResources();
                    return;
                } else if (requestInfo.responseWeb != null) {
                    requestInfo.responseWeb.disableFree = false;
                    requestInfo.responseWeb.freeResources();
                    return;
                } else {
                    if (requestInfo.responseCdn != null) {
                        requestInfo.responseCdn.disableFree = false;
                        requestInfo.responseCdn.freeResources();
                        return;
                    }
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:150:0x0251, code lost:
    
        if (r1 >= r11) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x03bf, code lost:
    
        if (r1 == (r5 - r11)) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x03c5, code lost:
    
        if (r0 != false) goto L147;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0224 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0527 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x058c A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0592 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0231 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x023f A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00a4 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00e1 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0117 A[Catch: Exception -> 0x05a1, TryCatch #2 {Exception -> 0x05a1, blocks: (B:55:0x0057, B:57:0x005b, B:59:0x0065, B:61:0x0069, B:63:0x006f, B:66:0x0094, B:69:0x009c, B:71:0x00a4, B:73:0x00b4, B:75:0x00c2, B:78:0x00c9, B:80:0x00e1, B:81:0x0113, B:83:0x0117, B:85:0x013b, B:86:0x0163, B:88:0x0167, B:89:0x016e, B:91:0x0199, B:93:0x01ab, B:95:0x01c0, B:96:0x01d5, B:97:0x01e2, B:98:0x01e7, B:100:0x0208, B:102:0x020c, B:104:0x0212, B:106:0x0218, B:111:0x0224, B:113:0x051f, B:115:0x0527, B:117:0x0533, B:119:0x053e, B:122:0x0541, B:124:0x054c, B:126:0x0552, B:127:0x0561, B:129:0x0567, B:130:0x0576, B:132:0x057c, B:134:0x058c, B:135:0x0592, B:137:0x0597, B:140:0x0231, B:142:0x0235, B:144:0x01cc, B:145:0x01da, B:147:0x023f, B:152:0x0274, B:154:0x0278, B:156:0x0291, B:158:0x0299, B:163:0x02ad, B:164:0x02c3, B:165:0x02c4, B:166:0x02c8, B:168:0x02cc, B:169:0x02fe, B:171:0x0302, B:173:0x030f, B:174:0x0345, B:176:0x0367, B:178:0x0379, B:180:0x0389, B:185:0x0399, B:187:0x03ae, B:189:0x03b5, B:191:0x03bb, B:196:0x03c7, B:198:0x03d7, B:199:0x03e8, B:204:0x03f9, B:205:0x0400, B:206:0x0401, B:208:0x040e, B:209:0x0448, B:211:0x0457, B:213:0x045b, B:215:0x045f, B:216:0x04ae, B:218:0x04b2, B:219:0x04d0, B:221:0x04da, B:224:0x0393, B:228:0x04f3, B:230:0x04f7, B:231:0x0503, B:233:0x050b, B:235:0x0510, B:237:0x0257, B:241:0x025f, B:250:0x059b, B:252:0x0077, B:254:0x007d, B:255:0x0084, B:257:0x008a), top: B:54:0x0057 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean processRequestResult(org.telegram.messenger.FileLoadOperation.RequestInfo r38, org.telegram.tgnet.TLRPC$TL_error r39) {
        /*
            Method dump skipped, instructions count: 1775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.FileLoadOperation.processRequestResult(org.telegram.messenger.FileLoadOperation$RequestInfo, org.telegram.tgnet.TLRPC$TL_error):boolean");
    }

    protected void onFail(boolean z, final int i) {
        cleanup();
        this.state = i == 1 ? 4 : 2;
        if (this.delegate != null && BuildVars.LOGS_ENABLED) {
            long currentTimeMillis = this.startTime != 0 ? System.currentTimeMillis() - this.startTime : 0L;
            if (i == 1) {
                FileLog.d("cancel downloading file to " + this.cacheFileFinal + " time = " + currentTimeMillis + " dc = " + this.datacenterId + " size = " + AndroidUtilities.formatFileSize(this.totalBytesCount));
            } else {
                FileLog.d("failed downloading file to " + this.cacheFileFinal + " reason = " + i + " time = " + currentTimeMillis + " dc = " + this.datacenterId + " size = " + AndroidUtilities.formatFileSize(this.totalBytesCount));
            }
        }
        if (z) {
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    FileLoadOperation.this.lambda$onFail$15(i);
                }
            });
            return;
        }
        FileLoadOperationDelegate fileLoadOperationDelegate = this.delegate;
        if (fileLoadOperationDelegate != null) {
            fileLoadOperationDelegate.didFailedLoadingFile(this, i);
        }
        notifyStreamListeners();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFail$15(int i) {
        FileLoadOperationDelegate fileLoadOperationDelegate = this.delegate;
        if (fileLoadOperationDelegate != null) {
            fileLoadOperationDelegate.didFailedLoadingFile(this, i);
        }
        notifyStreamListeners();
    }

    private void clearOperaion(RequestInfo requestInfo, boolean z) {
        long j = Long.MAX_VALUE;
        for (int i = 0; i < this.requestInfos.size(); i++) {
            RequestInfo requestInfo2 = this.requestInfos.get(i);
            j = Math.min(requestInfo2.offset, j);
            if (this.isPreloadVideoOperation) {
                this.requestedPreloadedBytesRanges.remove(Long.valueOf(requestInfo2.offset));
            } else {
                removePart(this.notRequestedBytesRanges, requestInfo2.offset, this.currentDownloadChunkSize + requestInfo2.offset);
            }
            if (requestInfo != requestInfo2 && requestInfo2.requestToken != 0) {
                ConnectionsManager.getInstance(this.currentAccount).cancelRequest(requestInfo2.requestToken, false);
            }
        }
        this.requestInfos.clear();
        for (int i2 = 0; i2 < this.delayedRequestInfos.size(); i2++) {
            RequestInfo requestInfo3 = this.delayedRequestInfos.get(i2);
            if (this.isPreloadVideoOperation) {
                this.requestedPreloadedBytesRanges.remove(Long.valueOf(requestInfo3.offset));
            } else {
                removePart(this.notRequestedBytesRanges, requestInfo3.offset, this.currentDownloadChunkSize + requestInfo3.offset);
            }
            if (requestInfo3.response != null) {
                requestInfo3.response.disableFree = false;
                requestInfo3.response.freeResources();
            } else if (requestInfo3.responseWeb != null) {
                requestInfo3.responseWeb.disableFree = false;
                requestInfo3.responseWeb.freeResources();
            } else if (requestInfo3.responseCdn != null) {
                requestInfo3.responseCdn.disableFree = false;
                requestInfo3.responseCdn.freeResources();
            }
            j = Math.min(requestInfo3.offset, j);
        }
        this.delayedRequestInfos.clear();
        this.requestsCount = 0;
        if (!z && this.isPreloadVideoOperation) {
            this.requestedBytesCount = this.totalPreloadedBytes;
        } else if (this.notLoadedBytesRanges == null) {
            this.downloadedBytes = j;
            this.requestedBytesCount = j;
        }
    }

    private void requestReference(RequestInfo requestInfo) {
        TLRPC$WebPage tLRPC$WebPage;
        if (this.requestingReference) {
            return;
        }
        clearOperaion(null, false);
        this.requestingReference = true;
        Object obj = this.parentObject;
        if (obj instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) obj;
            if (messageObject.getId() < 0 && (tLRPC$WebPage = messageObject.messageOwner.media.webpage) != null) {
                this.parentObject = tLRPC$WebPage;
            }
        }
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("debug_loading: " + this.cacheFileFinal.getName() + " file reference expired ");
        }
        FileRefController.getInstance(this.currentAccount).requestReference(this.parentObject, this.location, this, requestInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0 */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r11v8 */
    protected void startDownloadRequest() {
        int i;
        long j;
        long j2;
        TLRPC$TL_upload_getFile tLRPC$TL_upload_getFile;
        HashMap<Long, PreloadRange> hashMap;
        PreloadRange preloadRange;
        ArrayList<Range> arrayList;
        boolean z;
        if (BuildVars.DEBUG_PRIVATE_VERSION && Utilities.stageQueue != null && Utilities.stageQueue.getHandler() != null && Thread.currentThread() != Utilities.stageQueue.getHandler().getLooper().getThread()) {
            throw new RuntimeException("Wrong thread!!!");
        }
        if (this.paused || this.reuploadingCdn) {
            return;
        }
        int i2 = 1;
        if (this.state != 1 || this.requestingReference) {
            return;
        }
        long j3 = 0;
        if (this.streamPriorityStartOffset == 0) {
            if (!this.nextPartWasPreloaded && this.requestInfos.size() + this.delayedRequestInfos.size() >= this.currentMaxDownloadRequests) {
                return;
            }
            if (this.isPreloadVideoOperation) {
                if (this.requestedBytesCount > 2097152) {
                    return;
                }
                if (this.moovFound != 0 && this.requestInfos.size() > 0) {
                    return;
                }
            }
        }
        ?? r11 = 0;
        int max = (this.streamPriorityStartOffset != 0 || this.nextPartWasPreloaded || (this.isPreloadVideoOperation && this.moovFound == 0) || this.totalBytesCount <= 0) ? 1 : Math.max(0, this.currentMaxDownloadRequests - this.requestInfos.size());
        int i3 = 0;
        while (i3 < max) {
            int i4 = 2;
            if (this.isPreloadVideoOperation) {
                if (this.moovFound != 0 && this.preloadNotRequestedBytesCount <= j3) {
                    return;
                }
                long j4 = this.nextPreloadDownloadOffset;
                if (j4 == -1) {
                    int i5 = (preloadMaxBytes / this.currentDownloadChunkSize) + 2;
                    long j5 = j3;
                    while (i5 != 0) {
                        if (!this.requestedPreloadedBytesRanges.containsKey(Long.valueOf(j5))) {
                            j4 = j5;
                            z = true;
                            break;
                        }
                        int i6 = this.currentDownloadChunkSize;
                        j5 += i6;
                        long j6 = this.totalBytesCount;
                        if (j5 > j6) {
                            break;
                        }
                        if (this.moovFound == i4 && j5 == i6 * 8) {
                            j5 = ((j6 - 1048576) / i6) * i6;
                        }
                        i5--;
                        i4 = 2;
                    }
                    j4 = j5;
                    z = false;
                    if (!z && this.requestInfos.isEmpty()) {
                        onFinishLoadingFile(r11, r11);
                    }
                }
                if (this.requestedPreloadedBytesRanges == null) {
                    this.requestedPreloadedBytesRanges = new HashMap<>();
                }
                this.requestedPreloadedBytesRanges.put(Long.valueOf(j4), Integer.valueOf(i2));
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.d("start next preload from " + j4 + " size " + this.totalBytesCount + " for " + this.cacheFilePreload);
                }
                this.preloadNotRequestedBytesCount -= this.currentDownloadChunkSize;
                j2 = j4;
                i = max;
            } else {
                ArrayList<Range> arrayList2 = this.notRequestedBytesRanges;
                if (arrayList2 != null) {
                    long j7 = this.streamPriorityStartOffset;
                    if (j7 == j3) {
                        j7 = this.streamStartOffset;
                    }
                    int size = arrayList2.size();
                    long j8 = Long.MAX_VALUE;
                    i = max;
                    int i7 = 0;
                    long j9 = Long.MAX_VALUE;
                    while (true) {
                        if (i7 >= size) {
                            j7 = j8;
                            break;
                        }
                        Range range = this.notRequestedBytesRanges.get(i7);
                        if (j7 != j3) {
                            if (range.start <= j7 && range.end > j7) {
                                j9 = Long.MAX_VALUE;
                                break;
                            } else if (j7 < range.start && range.start < j8) {
                                j8 = range.start;
                            }
                        }
                        j9 = Math.min(j9, range.start);
                        i7++;
                        j3 = 0;
                    }
                    if (j7 != Long.MAX_VALUE) {
                        j = j7;
                    } else if (j9 == Long.MAX_VALUE) {
                        return;
                    } else {
                        j = j9;
                    }
                } else {
                    i = max;
                    j = this.requestedBytesCount;
                }
                j2 = j;
            }
            if (!this.isPreloadVideoOperation && (arrayList = this.notRequestedBytesRanges) != null) {
                addPart(arrayList, j2, j2 + this.currentDownloadChunkSize, false);
            }
            long j10 = this.totalBytesCount;
            if (j10 > 0 && j2 >= j10) {
                return;
            }
            boolean z2 = j10 <= 0 || i3 == i + (-1) || (j10 > 0 && ((long) this.currentDownloadChunkSize) + j2 >= j10);
            int i8 = this.requestsCount % 2 == 0 ? 2 : ConnectionsManager.ConnectionTypeDownload2;
            int i9 = this.isForceRequest ? 32 : 0;
            if (this.isCdn) {
                TLRPC$TL_upload_getCdnFile tLRPC$TL_upload_getCdnFile = new TLRPC$TL_upload_getCdnFile();
                tLRPC$TL_upload_getCdnFile.file_token = this.cdnToken;
                tLRPC$TL_upload_getCdnFile.offset = j2;
                tLRPC$TL_upload_getCdnFile.limit = this.currentDownloadChunkSize;
                i9 |= 1;
                tLRPC$TL_upload_getFile = tLRPC$TL_upload_getCdnFile;
            } else if (this.webLocation != null) {
                TLRPC$TL_upload_getWebFile tLRPC$TL_upload_getWebFile = new TLRPC$TL_upload_getWebFile();
                tLRPC$TL_upload_getWebFile.location = this.webLocation;
                tLRPC$TL_upload_getWebFile.offset = (int) j2;
                tLRPC$TL_upload_getWebFile.limit = this.currentDownloadChunkSize;
                tLRPC$TL_upload_getFile = tLRPC$TL_upload_getWebFile;
            } else {
                TLRPC$TL_upload_getFile tLRPC$TL_upload_getFile2 = new TLRPC$TL_upload_getFile();
                tLRPC$TL_upload_getFile2.location = this.location;
                tLRPC$TL_upload_getFile2.offset = j2;
                tLRPC$TL_upload_getFile2.limit = this.currentDownloadChunkSize;
                tLRPC$TL_upload_getFile2.cdn_supported = true;
                tLRPC$TL_upload_getFile = tLRPC$TL_upload_getFile2;
            }
            int i10 = i9;
            final TLRPC$TL_upload_getFile tLRPC$TL_upload_getFile3 = tLRPC$TL_upload_getFile;
            this.requestedBytesCount += this.currentDownloadChunkSize;
            final RequestInfo requestInfo = new RequestInfo();
            this.requestInfos.add(requestInfo);
            requestInfo.offset = j2;
            requestInfo.forceSmallChunk = this.forceSmallChunk;
            if (!this.isPreloadVideoOperation && this.supportsPreloading && this.preloadStream != null && (hashMap = this.preloadedBytesRanges) != null && (preloadRange = hashMap.get(Long.valueOf(requestInfo.offset))) != null) {
                requestInfo.response = new TLRPC$TL_upload_file();
                try {
                    if (BuildVars.DEBUG_VERSION && preloadRange.length > 2147483647L) {
                        throw new RuntimeException("cast long to integer");
                    }
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer((int) preloadRange.length);
                    this.preloadStream.seek(preloadRange.fileOffset);
                    this.preloadStream.getChannel().read(nativeByteBuffer.buffer);
                    try {
                        nativeByteBuffer.buffer.position(0);
                        requestInfo.response.bytes = nativeByteBuffer;
                        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                FileLoadOperation.this.lambda$startDownloadRequest$16(requestInfo);
                            }
                        });
                        j3 = 0;
                    } catch (Exception unused) {
                    }
                } catch (Exception unused2) {
                }
                i3++;
                max = i;
                i2 = 1;
                r11 = 0;
            }
            if (this.streamPriorityStartOffset != 0) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.d("frame get offset = " + this.streamPriorityStartOffset);
                }
                j3 = 0;
                this.streamPriorityStartOffset = 0L;
                this.priorityRequestInfo = requestInfo;
            } else {
                j3 = 0;
            }
            TLRPC$InputFileLocation tLRPC$InputFileLocation = this.location;
            if (!(tLRPC$InputFileLocation instanceof TLRPC$TL_inputPeerPhotoFileLocation) || ((TLRPC$TL_inputPeerPhotoFileLocation) tLRPC$InputFileLocation).photo_id != j3) {
                requestInfo.forceSmallChunk = this.forceSmallChunk;
                if (BuildVars.LOGS_ENABLED) {
                    requestInfo.requestStartTime = System.currentTimeMillis();
                }
                final int i11 = this.isCdn ? this.cdnDatacenterId : this.datacenterId;
                final int i12 = i8;
                requestInfo.requestToken = ConnectionsManager.getInstance(this.currentAccount).sendRequestSync(tLRPC$TL_upload_getFile3, new RequestDelegate() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda19
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        FileLoadOperation.this.lambda$startDownloadRequest$18(requestInfo, i11, i12, tLRPC$TL_upload_getFile3, tLObject, tLRPC$TL_error);
                    }
                }, null, null, i10, i11, i8, z2);
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("debug_loading: " + this.cacheFileFinal.getName() + " dc=" + i11 + " send reqId " + requestInfo.requestToken);
                }
                this.requestsCount++;
            } else {
                requestReference(requestInfo);
            }
            i3++;
            max = i;
            i2 = 1;
            r11 = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDownloadRequest$16(RequestInfo requestInfo) {
        processRequestResult(requestInfo, null);
        requestInfo.response.freeResources();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDownloadRequest$18(final RequestInfo requestInfo, int i, int i2, TLObject tLObject, TLObject tLObject2, TLRPC$TL_error tLRPC$TL_error) {
        byte[] bArr;
        if (this.requestInfos.contains(requestInfo)) {
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("debug_loading: " + this.cacheFileFinal.getName() + " time=" + (System.currentTimeMillis() - requestInfo.requestStartTime) + " dcId=" + i + " cdn=" + this.isCdn + " conType=" + i2 + " reqId" + requestInfo.requestToken);
            }
            if (requestInfo == this.priorityRequestInfo) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.d("frame get request completed " + this.priorityRequestInfo.offset);
                }
                this.priorityRequestInfo = null;
            }
            if (tLRPC$TL_error != null) {
                if (FileRefController.isFileRefError(tLRPC$TL_error.text)) {
                    requestReference(requestInfo);
                    return;
                } else if ((tLObject instanceof TLRPC$TL_upload_getCdnFile) && tLRPC$TL_error.text.equals("FILE_TOKEN_INVALID")) {
                    this.isCdn = false;
                    clearOperaion(requestInfo, false);
                    startDownloadRequest();
                    return;
                }
            }
            if (tLObject2 instanceof TLRPC$TL_upload_fileCdnRedirect) {
                TLRPC$TL_upload_fileCdnRedirect tLRPC$TL_upload_fileCdnRedirect = (TLRPC$TL_upload_fileCdnRedirect) tLObject2;
                if (!tLRPC$TL_upload_fileCdnRedirect.file_hashes.isEmpty()) {
                    if (this.cdnHashes == null) {
                        this.cdnHashes = new HashMap<>();
                    }
                    for (int i3 = 0; i3 < tLRPC$TL_upload_fileCdnRedirect.file_hashes.size(); i3++) {
                        TLRPC$TL_fileHash tLRPC$TL_fileHash = tLRPC$TL_upload_fileCdnRedirect.file_hashes.get(i3);
                        this.cdnHashes.put(Long.valueOf(tLRPC$TL_fileHash.offset), tLRPC$TL_fileHash);
                    }
                }
                byte[] bArr2 = tLRPC$TL_upload_fileCdnRedirect.encryption_iv;
                if (bArr2 == null || (bArr = tLRPC$TL_upload_fileCdnRedirect.encryption_key) == null || bArr2.length != 16 || bArr.length != 32) {
                    TLRPC$TL_error tLRPC$TL_error2 = new TLRPC$TL_error();
                    tLRPC$TL_error2.text = "bad redirect response";
                    tLRPC$TL_error2.code = 400;
                    processRequestResult(requestInfo, tLRPC$TL_error2);
                    return;
                }
                this.isCdn = true;
                if (this.notCheckedCdnRanges == null) {
                    ArrayList<Range> arrayList = new ArrayList<>();
                    this.notCheckedCdnRanges = arrayList;
                    arrayList.add(new Range(0L, this.maxCdnParts));
                }
                this.cdnDatacenterId = tLRPC$TL_upload_fileCdnRedirect.dc_id;
                this.cdnIv = tLRPC$TL_upload_fileCdnRedirect.encryption_iv;
                this.cdnKey = tLRPC$TL_upload_fileCdnRedirect.encryption_key;
                this.cdnToken = tLRPC$TL_upload_fileCdnRedirect.file_token;
                clearOperaion(requestInfo, false);
                startDownloadRequest();
                return;
            }
            if (tLObject2 instanceof TLRPC$TL_upload_cdnFileReuploadNeeded) {
                if (this.reuploadingCdn) {
                    return;
                }
                clearOperaion(requestInfo, false);
                this.reuploadingCdn = true;
                TLRPC$TL_upload_reuploadCdnFile tLRPC$TL_upload_reuploadCdnFile = new TLRPC$TL_upload_reuploadCdnFile();
                tLRPC$TL_upload_reuploadCdnFile.file_token = this.cdnToken;
                tLRPC$TL_upload_reuploadCdnFile.request_token = ((TLRPC$TL_upload_cdnFileReuploadNeeded) tLObject2).request_token;
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_upload_reuploadCdnFile, new RequestDelegate() { // from class: org.telegram.messenger.FileLoadOperation$$ExternalSyntheticLambda18
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject3, TLRPC$TL_error tLRPC$TL_error3) {
                        FileLoadOperation.this.lambda$startDownloadRequest$17(requestInfo, tLObject3, tLRPC$TL_error3);
                    }
                }, null, null, 0, this.datacenterId, 1, true);
                return;
            }
            if (tLObject2 instanceof TLRPC$TL_upload_file) {
                requestInfo.response = (TLRPC$TL_upload_file) tLObject2;
            } else if (tLObject2 instanceof TLRPC$TL_upload_webFile) {
                requestInfo.responseWeb = (TLRPC$TL_upload_webFile) tLObject2;
                if (this.totalBytesCount == 0 && requestInfo.responseWeb.size != 0) {
                    this.totalBytesCount = requestInfo.responseWeb.size;
                }
            } else {
                requestInfo.responseCdn = (TLRPC$TL_upload_cdnFile) tLObject2;
            }
            if (tLObject2 != null) {
                int i4 = this.currentType;
                if (i4 == 50331648) {
                    StatsController.getInstance(this.currentAccount).incrementReceivedBytesCount(tLObject2.networkType, 3, tLObject2.getObjectSize() + 4);
                } else if (i4 == 33554432) {
                    StatsController.getInstance(this.currentAccount).incrementReceivedBytesCount(tLObject2.networkType, 2, tLObject2.getObjectSize() + 4);
                } else if (i4 == 16777216) {
                    StatsController.getInstance(this.currentAccount).incrementReceivedBytesCount(tLObject2.networkType, 4, tLObject2.getObjectSize() + 4);
                } else if (i4 == 67108864) {
                    String str = this.ext;
                    if (str != null && (str.toLowerCase().endsWith("mp3") || this.ext.toLowerCase().endsWith("m4a"))) {
                        StatsController.getInstance(this.currentAccount).incrementReceivedBytesCount(tLObject2.networkType, 7, tLObject2.getObjectSize() + 4);
                    } else {
                        StatsController.getInstance(this.currentAccount).incrementReceivedBytesCount(tLObject2.networkType, 5, tLObject2.getObjectSize() + 4);
                    }
                }
            }
            processRequestResult(requestInfo, tLRPC$TL_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startDownloadRequest$17(RequestInfo requestInfo, TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        this.reuploadingCdn = false;
        if (tLRPC$TL_error == null) {
            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
            if (!tLRPC$Vector.objects.isEmpty()) {
                if (this.cdnHashes == null) {
                    this.cdnHashes = new HashMap<>();
                }
                for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                    TLRPC$TL_fileHash tLRPC$TL_fileHash = (TLRPC$TL_fileHash) tLRPC$Vector.objects.get(i);
                    this.cdnHashes.put(Long.valueOf(tLRPC$TL_fileHash.offset), tLRPC$TL_fileHash);
                }
            }
            startDownloadRequest();
            return;
        }
        if (tLRPC$TL_error.text.equals("FILE_TOKEN_INVALID") || tLRPC$TL_error.text.equals("REQUEST_TOKEN_INVALID")) {
            this.isCdn = false;
            clearOperaion(requestInfo, false);
            startDownloadRequest();
            return;
        }
        onFail(false, 0);
    }

    public void setDelegate(FileLoadOperationDelegate fileLoadOperationDelegate) {
        this.delegate = fileLoadOperationDelegate;
    }
}
