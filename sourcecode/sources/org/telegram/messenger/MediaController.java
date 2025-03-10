package org.telegram.messenger;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaCrypto;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.audioinfo.AudioInfo;
import org.telegram.messenger.voip.VoIPService;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DocumentAttribute;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$InputDocument;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$VideoSize;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Adapters.FiltersView;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.EmbedBottomSheet;
import org.telegram.ui.Components.PhotoFilterView;
import org.telegram.ui.Components.PipRoundVideoView;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.VideoPlayer;
import org.telegram.ui.LaunchActivity;
import org.telegram.ui.PhotoViewer;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes3.dex */
public class MediaController implements AudioManager.OnAudioFocusChangeListener, NotificationCenter.NotificationCenterDelegate, SensorEventListener {
    private static final int AUDIO_FOCUSED = 2;
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    public static final String AUIDO_MIME_TYPE = "audio/mp4a-latm";
    private static volatile MediaController Instance = null;
    public static final int VIDEO_BITRATE_1080 = 6800000;
    public static final int VIDEO_BITRATE_360 = 750000;
    public static final int VIDEO_BITRATE_480 = 1000000;
    public static final int VIDEO_BITRATE_720 = 2621440;
    public static final String VIDEO_MIME_TYPE = "video/avc";
    private static final float VOLUME_DUCK = 0.2f;
    private static final float VOLUME_NORMAL = 1.0f;
    public static AlbumEntry allMediaAlbumEntry;
    public static ArrayList<AlbumEntry> allMediaAlbums;
    public static ArrayList<AlbumEntry> allPhotoAlbums;
    public static AlbumEntry allPhotosAlbumEntry;
    public static AlbumEntry allVideosAlbumEntry;
    private static Runnable broadcastPhotosRunnable;
    private static final ConcurrentHashMap<String, Integer> cachedEncoderBitrates;
    private static final String[] projectionPhotos;
    private static final String[] projectionVideo;
    private static Runnable refreshGalleryRunnable;
    private static long volumeBarLastTimeShown;
    private Sensor accelerometerSensor;
    private boolean accelerometerVertical;
    private boolean allowStartRecord;
    private AudioInfo audioInfo;
    private AudioRecord audioRecorder;
    private float audioVolume;
    private ValueAnimator audioVolumeAnimator;
    private Activity baseActivity;
    private boolean callInProgress;
    private int countLess;
    private AspectRatioFrameLayout currentAspectRatioFrameLayout;
    private float currentAspectRatioFrameLayoutRatio;
    private boolean currentAspectRatioFrameLayoutReady;
    private int currentAspectRatioFrameLayoutRotation;
    private int currentPlaylistNum;
    private TextureView currentTextureView;
    private FrameLayout currentTextureViewContainer;
    private boolean downloadingCurrentMessage;
    private ExternalObserver externalObserver;
    private View feedbackView;
    private ByteBuffer fileBuffer;
    private DispatchQueue fileEncodingQueue;
    private BaseFragment flagSecureFragment;
    private boolean forceLoopCurrentPlaylist;
    private MessageObject goingToShowMessageObject;
    private Sensor gravitySensor;
    private int hasAudioFocus;
    private boolean hasRecordAudioFocus;
    private boolean ignoreOnPause;
    private boolean ignoreProximity;
    private boolean inputFieldHasText;
    private InternalObserver internalObserver;
    private boolean isDrawingWasReady;
    private boolean isStreamingCurrentAudio;
    private long lastAccelerometerDetected;
    private int lastChatAccount;
    private long lastChatEnterTime;
    private long lastChatLeaveTime;
    private ArrayList<Long> lastChatVisibleMessages;
    private long lastMediaCheckTime;
    private int lastMessageId;
    private long lastSaveTime;
    private TLRPC$EncryptedChat lastSecretChat;
    private TLRPC$User lastUser;
    private Sensor linearSensor;
    private boolean loadingPlaylist;
    private boolean manualRecording;
    private String[] mediaProjections;
    private PipRoundVideoView pipRoundVideoView;
    private int pipSwitchingState;
    private boolean playMusicAgain;
    private int playerNum;
    private boolean playerWasReady;
    private MessageObject playingMessageObject;
    private int playlistClassGuid;
    private PlaylistGlobalSearchParams playlistGlobalSearchParams;
    private long playlistMergeDialogId;
    private float previousAccValue;
    private boolean proximityHasDifferentValues;
    private Sensor proximitySensor;
    private boolean proximityTouched;
    private PowerManager.WakeLock proximityWakeLock;
    private ChatActivity raiseChat;
    private boolean raiseToEarRecord;
    private int raisedToBack;
    private int raisedToTop;
    private int raisedToTopSign;
    private long recordDialogId;
    private DispatchQueue recordQueue;
    private MessageObject recordReplyingMsg;
    private MessageObject recordReplyingTopMsg;
    private Runnable recordStartRunnable;
    private long recordStartTime;
    private long recordTimeCount;
    private TLRPC$TL_document recordingAudio;
    private File recordingAudioFile;
    private int recordingCurrentAccount;
    private boolean resumeAudioOnFocusGain;
    private long samplesCount;
    private float seekToProgressPending;
    private int sendAfterDone;
    private boolean sendAfterDoneNotify;
    private int sendAfterDoneScheduleDate;
    private SensorManager sensorManager;
    private boolean sensorsStarted;
    private String shouldSavePositionForCurrentAudio;
    private int startObserverToken;
    private StopMediaObserverRunnable stopMediaObserverRunnable;
    private long timeSinceRaise;
    private boolean useFrontSpeaker;
    private VideoPlayer videoPlayer;
    private ArrayList<MessageObject> voiceMessagesPlaylist;
    private SparseArray<MessageObject> voiceMessagesPlaylistMap;
    private boolean voiceMessagesPlaylistUnread;
    private long writedFileLenght;
    private int writedFrame;
    AudioManager.OnAudioFocusChangeListener audioRecordFocusChangedListener = new AudioManager.OnAudioFocusChangeListener() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda2
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public final void onAudioFocusChange(int i) {
            MediaController.this.lambda$new$0(i);
        }
    };
    private final Object videoConvertSync = new Object();
    private long lastTimestamp = 0;
    private float lastProximityValue = -100.0f;
    private float[] gravity = new float[3];
    private float[] gravityFast = new float[3];
    private float[] linearAcceleration = new float[3];
    private int audioFocus = 0;
    private ArrayList<VideoConvertMessage> videoConvertQueue = new ArrayList<>();
    private final Object videoQueueSync = new Object();
    private HashMap<String, MessageObject> generatingWaveform = new HashMap<>();
    public boolean isSilent = false;
    private boolean isPaused = false;
    private VideoPlayer audioPlayer = null;
    private VideoPlayer emojiSoundPlayer = null;
    private int emojiSoundPlayerNum = 0;
    private float currentPlaybackSpeed = VOLUME_NORMAL;
    private float currentMusicPlaybackSpeed = VOLUME_NORMAL;
    private float fastPlaybackSpeed = VOLUME_NORMAL;
    private float fastMusicPlaybackSpeed = VOLUME_NORMAL;
    private long lastProgress = 0;
    private Timer progressTimer = null;
    private final Object progressTimerSync = new Object();
    private ArrayList<MessageObject> playlist = new ArrayList<>();
    private HashMap<Integer, MessageObject> playlistMap = new HashMap<>();
    private ArrayList<MessageObject> shuffledPlaylist = new ArrayList<>();
    private boolean[] playlistEndReached = {false, false};
    private int[] playlistMaxId = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    private Runnable setLoadingRunnable = new Runnable() { // from class: org.telegram.messenger.MediaController.1
        @Override // java.lang.Runnable
        public void run() {
            if (MediaController.this.playingMessageObject == null) {
                return;
            }
            FileLoader.getInstance(MediaController.this.playingMessageObject.currentAccount).setLoadingVideo(MediaController.this.playingMessageObject.getDocument(), true, false);
        }
    };
    private int recordingGuid = -1;
    private short[] recordSamples = new short[1024];
    private final Object sync = new Object();
    private ArrayList<ByteBuffer> recordBuffers = new ArrayList<>();
    public int recordBufferSize = 1280;
    public int sampleRate = 48000;
    private Runnable recordRunnable = new AnonymousClass2();
    private final ValueAnimator.AnimatorUpdateListener audioVolumeUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.messenger.MediaController.3
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            MediaController.this.audioVolume = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            MediaController.this.setPlayerVolume();
        }
    };

    public static class AudioEntry {
        public String author;
        public int duration;
        public String genre;
        public long id;
        public MessageObject messageObject;
        public String path;
        public String title;
    }

    public static class SavedFilterState {
        public float blurAngle;
        public float blurExcludeBlurSize;
        public Point blurExcludePoint;
        public float blurExcludeSize;
        public int blurType;
        public float contrastValue;
        public PhotoFilterView.CurvesToolValue curvesToolValue = new PhotoFilterView.CurvesToolValue();
        public float enhanceValue;
        public float exposureValue;
        public float fadeValue;
        public float grainValue;
        public float highlightsValue;
        public float saturationValue;
        public float shadowsValue;
        public float sharpenValue;
        public float softenSkinValue;
        public int tintHighlightsColor;
        public int tintShadowsColor;
        public float vignetteValue;
        public float warmthValue;
    }

    public interface VideoConvertorListener {
        boolean checkConversionCanceled();

        void didWriteData(long j, float f);
    }

    private static int getVideoBitrateWithFactor(float f) {
        return (int) (f * 2000.0f * 1000.0f * 1.13f);
    }

    public static native byte[] getWaveform(String str);

    public static native int isOpusFile(String str);

    private static boolean isRecognizedFormat(int i) {
        if (i == 39 || i == 2130706688) {
            return true;
        }
        switch (i) {
            case 19:
            case 20:
            case 21:
                return true;
            default:
                return false;
        }
    }

    private native int startRecord(String str, int i);

    private native void stopRecord();

    /* JADX INFO: Access modifiers changed from: private */
    public native int writeFrame(ByteBuffer byteBuffer, int i);

    public native byte[] getWaveform2(short[] sArr, int i);

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    static /* synthetic */ long access$1614(MediaController mediaController, long j) {
        long j2 = mediaController.recordTimeCount + j;
        mediaController.recordTimeCount = j2;
        return j2;
    }

    static /* synthetic */ int access$1708(MediaController mediaController) {
        int i = mediaController.writedFrame;
        mediaController.writedFrame = i + 1;
        return i;
    }

    public boolean isBuffering() {
        VideoPlayer videoPlayer = this.audioPlayer;
        if (videoPlayer != null) {
            return videoPlayer.isBuffering();
        }
        return false;
    }

    private static class AudioBuffer {
        ByteBuffer buffer;
        byte[] bufferBytes;
        int finished;
        long pcmOffset;
        int size;

        public AudioBuffer(int i) {
            this.buffer = ByteBuffer.allocateDirect(i);
            this.bufferBytes = new byte[i];
        }
    }

    static {
        String[] strArr = new String[9];
        strArr[0] = "_id";
        strArr[1] = "bucket_id";
        strArr[2] = "bucket_display_name";
        strArr[3] = "_data";
        int i = Build.VERSION.SDK_INT;
        strArr[4] = i > 28 ? "date_modified" : "datetaken";
        strArr[5] = "orientation";
        strArr[6] = "width";
        strArr[7] = "height";
        strArr[8] = "_size";
        projectionPhotos = strArr;
        String[] strArr2 = new String[9];
        strArr2[0] = "_id";
        strArr2[1] = "bucket_id";
        strArr2[2] = "bucket_display_name";
        strArr2[3] = "_data";
        strArr2[4] = i <= 28 ? "datetaken" : "date_modified";
        strArr2[5] = "duration";
        strArr2[6] = "width";
        strArr2[7] = "height";
        strArr2[8] = "_size";
        projectionVideo = strArr2;
        cachedEncoderBitrates = new ConcurrentHashMap<>();
        allMediaAlbums = new ArrayList<>();
        allPhotoAlbums = new ArrayList<>();
    }

    public static class AlbumEntry {
        public int bucketId;
        public String bucketName;
        public PhotoEntry coverPhoto;
        public ArrayList<PhotoEntry> photos = new ArrayList<>();
        public SparseArray<PhotoEntry> photosByIds = new SparseArray<>();
        public boolean videoOnly;

        public AlbumEntry(int i, String str, PhotoEntry photoEntry) {
            this.bucketId = i;
            this.bucketName = str;
            this.coverPhoto = photoEntry;
        }

        public void addPhoto(PhotoEntry photoEntry) {
            this.photos.add(photoEntry);
            this.photosByIds.put(photoEntry.imageId, photoEntry);
        }
    }

    public static class CropState {
        public float cropPx;
        public float cropPy;
        public float cropRotate;
        public boolean freeform;
        public int height;
        public boolean initied;
        public float lockedAspectRatio;
        public Matrix matrix;
        public boolean mirrored;
        public float scale;
        public float stateScale;
        public int transformHeight;
        public int transformRotation;
        public int transformWidth;
        public int width;
        public float cropScale = MediaController.VOLUME_NORMAL;
        public float cropPw = MediaController.VOLUME_NORMAL;
        public float cropPh = MediaController.VOLUME_NORMAL;

        public CropState clone() {
            CropState cropState = new CropState();
            cropState.cropPx = this.cropPx;
            cropState.cropPy = this.cropPy;
            cropState.cropScale = this.cropScale;
            cropState.cropRotate = this.cropRotate;
            cropState.cropPw = this.cropPw;
            cropState.cropPh = this.cropPh;
            cropState.transformWidth = this.transformWidth;
            cropState.transformHeight = this.transformHeight;
            cropState.transformRotation = this.transformRotation;
            cropState.mirrored = this.mirrored;
            cropState.stateScale = this.stateScale;
            cropState.scale = this.scale;
            cropState.matrix = this.matrix;
            cropState.width = this.width;
            cropState.height = this.height;
            cropState.freeform = this.freeform;
            cropState.lockedAspectRatio = this.lockedAspectRatio;
            cropState.initied = this.initied;
            return cropState;
        }
    }

    public static class MediaEditState {
        public long averageDuration;
        public CharSequence caption;
        public CropState cropState;
        public ArrayList<VideoEditedInfo.MediaEntity> croppedMediaEntities;
        public String croppedPaintPath;
        public VideoEditedInfo editedInfo;
        public ArrayList<TLRPC$MessageEntity> entities;
        public String filterPath;
        public String fullPaintPath;
        public String imagePath;
        public boolean isCropped;
        public boolean isFiltered;
        public boolean isPainted;
        public ArrayList<VideoEditedInfo.MediaEntity> mediaEntities;
        public String paintPath;
        public SavedFilterState savedFilterState;
        public ArrayList<TLRPC$InputDocument> stickers;
        public String thumbPath;
        public int ttl;

        public String getPath() {
            return null;
        }

        public void reset() {
            this.caption = null;
            this.thumbPath = null;
            this.filterPath = null;
            this.imagePath = null;
            this.paintPath = null;
            this.croppedPaintPath = null;
            this.isFiltered = false;
            this.isPainted = false;
            this.isCropped = false;
            this.ttl = 0;
            this.mediaEntities = null;
            this.editedInfo = null;
            this.entities = null;
            this.savedFilterState = null;
            this.stickers = null;
            this.cropState = null;
        }

        public void copyFrom(MediaEditState mediaEditState) {
            this.caption = mediaEditState.caption;
            this.thumbPath = mediaEditState.thumbPath;
            this.imagePath = mediaEditState.imagePath;
            this.filterPath = mediaEditState.filterPath;
            this.paintPath = mediaEditState.paintPath;
            this.croppedPaintPath = mediaEditState.croppedPaintPath;
            this.fullPaintPath = mediaEditState.fullPaintPath;
            this.entities = mediaEditState.entities;
            this.savedFilterState = mediaEditState.savedFilterState;
            this.mediaEntities = mediaEditState.mediaEntities;
            this.croppedMediaEntities = mediaEditState.croppedMediaEntities;
            this.stickers = mediaEditState.stickers;
            this.editedInfo = mediaEditState.editedInfo;
            this.averageDuration = mediaEditState.averageDuration;
            this.isFiltered = mediaEditState.isFiltered;
            this.isPainted = mediaEditState.isPainted;
            this.isCropped = mediaEditState.isCropped;
            this.ttl = mediaEditState.ttl;
            this.cropState = mediaEditState.cropState;
        }
    }

    public static class PhotoEntry extends MediaEditState {
        public int bucketId;
        public boolean canDeleteAfter;
        public long dateTaken;
        public int duration;
        public TLRPC$VideoSize emojiMarkup;
        public boolean hasSpoiler;
        public int height;
        public int imageId;
        public int invert;
        public boolean isAttachSpoilerRevealed;
        public boolean isChatPreviewSpoilerRevealed;
        public boolean isMuted;
        public boolean isVideo;
        public int orientation;
        public String path;
        public long size;
        public int width;

        public PhotoEntry(int i, int i2, long j, String str, int i3, boolean z, int i4, int i5, long j2) {
            this.bucketId = i;
            this.imageId = i2;
            this.dateTaken = j;
            this.path = str;
            this.width = i4;
            this.height = i5;
            this.size = j2;
            if (z) {
                this.duration = i3;
            } else {
                this.orientation = i3;
            }
            this.isVideo = z;
        }

        public PhotoEntry setOrientation(Pair<Integer, Integer> pair) {
            this.orientation = ((Integer) pair.first).intValue();
            this.invert = ((Integer) pair.second).intValue();
            return this;
        }

        public PhotoEntry setOrientation(int i, int i2) {
            this.orientation = i;
            this.invert = i2;
            return this;
        }

        @Override // org.telegram.messenger.MediaController.MediaEditState
        public void copyFrom(MediaEditState mediaEditState) {
            super.copyFrom(mediaEditState);
            this.hasSpoiler = (mediaEditState instanceof PhotoEntry) && ((PhotoEntry) mediaEditState).hasSpoiler;
        }

        @Override // org.telegram.messenger.MediaController.MediaEditState
        public String getPath() {
            return this.path;
        }

        @Override // org.telegram.messenger.MediaController.MediaEditState
        public void reset() {
            if (this.isVideo && this.filterPath != null) {
                new File(this.filterPath).delete();
                this.filterPath = null;
            }
            this.hasSpoiler = false;
            super.reset();
        }
    }

    public static class SearchImage extends MediaEditState {
        public CharSequence caption;
        public int date;
        public TLRPC$Document document;
        public int height;
        public String id;
        public String imageUrl;
        public TLRPC$BotInlineResult inlineResult;
        public HashMap<String, String> params;
        public TLRPC$Photo photo;
        public TLRPC$PhotoSize photoSize;
        public int size;
        public TLRPC$PhotoSize thumbPhotoSize;
        public String thumbUrl;
        public int type;
        public int width;

        @Override // org.telegram.messenger.MediaController.MediaEditState
        public String getPath() {
            if (this.photoSize != null) {
                return FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.photoSize, true).getAbsolutePath();
            }
            if (this.document != null) {
                return FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.document, true).getAbsolutePath();
            }
            return ImageLoader.getHttpFilePath(this.imageUrl, "jpg").getAbsolutePath();
        }

        @Override // org.telegram.messenger.MediaController.MediaEditState
        public void reset() {
            super.reset();
        }

        public String getAttachName() {
            TLRPC$PhotoSize tLRPC$PhotoSize = this.photoSize;
            if (tLRPC$PhotoSize != null) {
                return FileLoader.getAttachFileName(tLRPC$PhotoSize);
            }
            TLRPC$Document tLRPC$Document = this.document;
            if (tLRPC$Document != null) {
                return FileLoader.getAttachFileName(tLRPC$Document);
            }
            return Utilities.MD5(this.imageUrl) + "." + ImageLoader.getHttpUrlExtension(this.imageUrl, "jpg");
        }

        public String getPathToAttach() {
            if (this.photoSize != null) {
                return FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.photoSize, true).getAbsolutePath();
            }
            if (this.document != null) {
                return FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.document, true).getAbsolutePath();
            }
            return this.imageUrl;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i) {
        if (i != 1) {
            this.hasRecordAudioFocus = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VideoConvertMessage {
        public int currentAccount;
        public MessageObject messageObject;
        public VideoEditedInfo videoEditedInfo;

        public VideoConvertMessage(MessageObject messageObject, VideoEditedInfo videoEditedInfo) {
            this.messageObject = messageObject;
            this.currentAccount = messageObject.currentAccount;
            this.videoEditedInfo = videoEditedInfo;
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        AnonymousClass2() {
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x00ff  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 334
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.AnonymousClass2.run():void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$1(final ByteBuffer byteBuffer, boolean z) {
            int i;
            while (byteBuffer.hasRemaining()) {
                if (byteBuffer.remaining() > MediaController.this.fileBuffer.remaining()) {
                    i = byteBuffer.limit();
                    byteBuffer.limit(MediaController.this.fileBuffer.remaining() + byteBuffer.position());
                } else {
                    i = -1;
                }
                MediaController.this.fileBuffer.put(byteBuffer);
                if (MediaController.this.fileBuffer.position() == MediaController.this.fileBuffer.limit() || z) {
                    MediaController mediaController = MediaController.this;
                    if (mediaController.writeFrame(mediaController.fileBuffer, !z ? MediaController.this.fileBuffer.limit() : byteBuffer.position()) != 0) {
                        MediaController.this.fileBuffer.rewind();
                        MediaController.access$1614(MediaController.this, (r1.fileBuffer.limit() / 2) / (MediaController.this.sampleRate / 1000));
                        MediaController.access$1708(MediaController.this);
                    } else {
                        FileLog.e("writing frame failed");
                    }
                }
                if (i != -1) {
                    byteBuffer.limit(i);
                }
            }
            MediaController.this.recordQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.AnonymousClass2.this.lambda$run$0(byteBuffer);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(ByteBuffer byteBuffer) {
            MediaController.this.recordBuffers.add(byteBuffer);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$2(double d) {
            NotificationCenter.getInstance(MediaController.this.recordingCurrentAccount).postNotificationName(NotificationCenter.recordProgressChanged, Integer.valueOf(MediaController.this.recordingGuid), Double.valueOf(d));
        }
    }

    private class InternalObserver extends ContentObserver {
        public InternalObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            MediaController.this.processMediaObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        }
    }

    private class ExternalObserver extends ContentObserver {
        public ExternalObserver() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            MediaController.this.processMediaObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GalleryObserverInternal extends ContentObserver {
        public GalleryObserverInternal() {
            super(null);
        }

        private void scheduleReloadRunnable() {
            AndroidUtilities.runOnUIThread(MediaController.refreshGalleryRunnable = new Runnable() { // from class: org.telegram.messenger.MediaController$GalleryObserverInternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.GalleryObserverInternal.this.lambda$scheduleReloadRunnable$0();
                }
            }, 2000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleReloadRunnable$0() {
            if (!PhotoViewer.getInstance().isVisible()) {
                Runnable unused = MediaController.refreshGalleryRunnable = null;
                MediaController.loadGalleryPhotosAlbums(0);
            } else {
                scheduleReloadRunnable();
            }
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            if (MediaController.refreshGalleryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
            }
            scheduleReloadRunnable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class GalleryObserverExternal extends ContentObserver {
        public GalleryObserverExternal() {
            super(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            if (MediaController.refreshGalleryRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(MediaController.refreshGalleryRunnable);
            }
            AndroidUtilities.runOnUIThread(MediaController.refreshGalleryRunnable = new Runnable() { // from class: org.telegram.messenger.MediaController$GalleryObserverExternal$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.GalleryObserverExternal.lambda$onChange$0();
                }
            }, 2000L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onChange$0() {
            Runnable unused = MediaController.refreshGalleryRunnable = null;
            MediaController.loadGalleryPhotosAlbums(0);
        }
    }

    public static void checkGallery() {
        AlbumEntry albumEntry;
        if (Build.VERSION.SDK_INT < 24 || (albumEntry = allPhotosAlbumEntry) == null) {
            return;
        }
        final int size = albumEntry.photos.size();
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.lambda$checkGallery$1(size);
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        if (r12 == r5) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007b, code lost:
    
        r12 = org.telegram.messenger.MediaController.refreshGalleryRunnable;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007d, code lost:
    
        if (r12 == null) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x007f, code lost:
    
        org.telegram.messenger.AndroidUtilities.cancelRunOnUIThread(r12);
        org.telegram.messenger.MediaController.refreshGalleryRunnable = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0084, code lost:
    
        loadGalleryPhotosAlbums(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0087, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0076, code lost:
    
        if (r4 == null) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004c A[Catch: all -> 0x0072, TryCatch #0 {all -> 0x0072, blocks: (B:11:0x0044, B:13:0x004c, B:15:0x0061, B:17:0x0067), top: B:10:0x0044 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$checkGallery$1(int r12) {
        /*
            java.lang.String r0 = "COUNT(_id)"
            java.lang.String r1 = "android.permission.READ_EXTERNAL_STORAGE"
            r2 = 0
            r3 = 0
            android.content.Context r4 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> L39
            int r4 = r4.checkSelfPermission(r1)     // Catch: java.lang.Throwable -> L39
            if (r4 != 0) goto L31
            android.content.Context r4 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> L39
            android.content.ContentResolver r5 = r4.getContentResolver()     // Catch: java.lang.Throwable -> L39
            android.net.Uri r6 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Throwable -> L39
            java.lang.String[] r7 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L39
            r8 = 0
            r9 = 0
            r10 = 0
            android.database.Cursor r4 = android.provider.MediaStore.Images.Media.query(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L39
            if (r4 == 0) goto L32
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L2f
            if (r5 == 0) goto L32
            int r5 = r4.getInt(r3)     // Catch: java.lang.Throwable -> L2f
            int r5 = r5 + r3
            goto L33
        L2f:
            r5 = move-exception
            goto L3b
        L31:
            r4 = r2
        L32:
            r5 = 0
        L33:
            if (r4 == 0) goto L44
            r4.close()
            goto L44
        L39:
            r5 = move-exception
            r4 = r2
        L3b:
            org.telegram.messenger.FileLog.e(r5)     // Catch: java.lang.Throwable -> L8f
            if (r4 == 0) goto L43
            r4.close()
        L43:
            r5 = 0
        L44:
            android.content.Context r6 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> L72
            int r1 = r6.checkSelfPermission(r1)     // Catch: java.lang.Throwable -> L72
            if (r1 != 0) goto L6c
            android.content.Context r1 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> L72
            android.content.ContentResolver r6 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L72
            android.net.Uri r7 = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Throwable -> L72
            java.lang.String[] r8 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L72
            r9 = 0
            r10 = 0
            r11 = 0
            android.database.Cursor r4 = android.provider.MediaStore.Images.Media.query(r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L72
            if (r4 == 0) goto L6c
            boolean r0 = r4.moveToNext()     // Catch: java.lang.Throwable -> L72
            if (r0 == 0) goto L6c
            int r0 = r4.getInt(r3)     // Catch: java.lang.Throwable -> L72
            int r5 = r5 + r0
        L6c:
            if (r4 == 0) goto L79
        L6e:
            r4.close()
            goto L79
        L72:
            r0 = move-exception
            org.telegram.messenger.FileLog.e(r0)     // Catch: java.lang.Throwable -> L88
            if (r4 == 0) goto L79
            goto L6e
        L79:
            if (r12 == r5) goto L87
            java.lang.Runnable r12 = org.telegram.messenger.MediaController.refreshGalleryRunnable
            if (r12 == 0) goto L84
            org.telegram.messenger.AndroidUtilities.cancelRunOnUIThread(r12)
            org.telegram.messenger.MediaController.refreshGalleryRunnable = r2
        L84:
            loadGalleryPhotosAlbums(r3)
        L87:
            return
        L88:
            r12 = move-exception
            if (r4 == 0) goto L8e
            r4.close()
        L8e:
            throw r12
        L8f:
            r12 = move-exception
            if (r4 == 0) goto L95
            r4.close()
        L95:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.lambda$checkGallery$1(int):void");
    }

    private final class StopMediaObserverRunnable implements Runnable {
        public int currentObserverToken;

        private StopMediaObserverRunnable() {
            this.currentObserverToken = 0;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.currentObserverToken == MediaController.this.startObserverToken) {
                try {
                    if (MediaController.this.internalObserver != null) {
                        ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.this.internalObserver);
                        MediaController.this.internalObserver = null;
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
                try {
                    if (MediaController.this.externalObserver != null) {
                        ApplicationLoader.applicationContext.getContentResolver().unregisterContentObserver(MediaController.this.externalObserver);
                        MediaController.this.externalObserver = null;
                    }
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        }
    }

    public static MediaController getInstance() {
        MediaController mediaController = Instance;
        if (mediaController == null) {
            synchronized (MediaController.class) {
                mediaController = Instance;
                if (mediaController == null) {
                    mediaController = new MediaController();
                    Instance = mediaController;
                }
            }
        }
        return mediaController;
    }

    public MediaController() {
        DispatchQueue dispatchQueue = new DispatchQueue("recordQueue");
        this.recordQueue = dispatchQueue;
        dispatchQueue.setPriority(10);
        DispatchQueue dispatchQueue2 = new DispatchQueue("fileEncodingQueue");
        this.fileEncodingQueue = dispatchQueue2;
        dispatchQueue2.setPriority(10);
        this.recordQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$new$2();
            }
        });
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$new$3();
            }
        });
        this.fileBuffer = ByteBuffer.allocateDirect(1920);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$new$4();
            }
        });
        String[] strArr = new String[7];
        strArr[0] = "_data";
        strArr[1] = "_display_name";
        strArr[2] = "bucket_display_name";
        strArr[3] = Build.VERSION.SDK_INT > 28 ? "date_modified" : "datetaken";
        strArr[4] = "title";
        strArr[5] = "width";
        strArr[6] = "height";
        this.mediaProjections = strArr;
        ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
        try {
            contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, new GalleryObserverExternal());
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            contentResolver.registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, true, new GalleryObserverInternal());
        } catch (Exception e2) {
            FileLog.e(e2);
        }
        try {
            contentResolver.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, true, new GalleryObserverExternal());
        } catch (Exception e3) {
            FileLog.e(e3);
        }
        try {
            contentResolver.registerContentObserver(MediaStore.Video.Media.INTERNAL_CONTENT_URI, true, new GalleryObserverInternal());
        } catch (Exception e4) {
            FileLog.e(e4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2() {
        try {
            this.sampleRate = 48000;
            int minBufferSize = AudioRecord.getMinBufferSize(48000, 16, 2);
            if (minBufferSize <= 0) {
                minBufferSize = 1280;
            }
            this.recordBufferSize = minBufferSize;
            for (int i = 0; i < 5; i++) {
                ByteBuffer allocateDirect = ByteBuffer.allocateDirect(this.recordBufferSize);
                allocateDirect.order(ByteOrder.nativeOrder());
                this.recordBuffers.add(allocateDirect);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3() {
        try {
            this.currentPlaybackSpeed = MessagesController.getGlobalMainSettings().getFloat("playbackSpeed", VOLUME_NORMAL);
            this.currentMusicPlaybackSpeed = MessagesController.getGlobalMainSettings().getFloat("musicPlaybackSpeed", VOLUME_NORMAL);
            this.fastPlaybackSpeed = MessagesController.getGlobalMainSettings().getFloat("fastPlaybackSpeed", 1.8f);
            this.fastMusicPlaybackSpeed = MessagesController.getGlobalMainSettings().getFloat("fastMusicPlaybackSpeed", 1.8f);
            SensorManager sensorManager = (SensorManager) ApplicationLoader.applicationContext.getSystemService("sensor");
            this.sensorManager = sensorManager;
            this.linearSensor = sensorManager.getDefaultSensor(10);
            Sensor defaultSensor = this.sensorManager.getDefaultSensor(9);
            this.gravitySensor = defaultSensor;
            if (this.linearSensor == null || defaultSensor == null) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("gravity or linear sensor not found");
                }
                this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
                this.linearSensor = null;
                this.gravitySensor = null;
            }
            this.proximitySensor = this.sensorManager.getDefaultSensor(8);
            this.proximityWakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(32, "telegram:proximity_lock");
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            AnonymousClass4 anonymousClass4 = new AnonymousClass4();
            TelephonyManager telephonyManager = (TelephonyManager) ApplicationLoader.applicationContext.getSystemService("phone");
            if (telephonyManager != null) {
                telephonyManager.listen(anonymousClass4, 32);
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$4, reason: invalid class name */
    class AnonymousClass4 extends PhoneStateListener {
        AnonymousClass4() {
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(final int i, String str) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.AnonymousClass4.this.lambda$onCallStateChanged$0(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCallStateChanged$0(int i) {
            if (i != 1) {
                if (i == 0) {
                    MediaController.this.callInProgress = false;
                    return;
                } else {
                    if (i == 2) {
                        EmbedBottomSheet embedBottomSheet = EmbedBottomSheet.getInstance();
                        if (embedBottomSheet != null) {
                            embedBottomSheet.pause();
                        }
                        MediaController.this.callInProgress = true;
                        return;
                    }
                    return;
                }
            }
            MediaController mediaController = MediaController.this;
            if (!mediaController.isPlayingMessage(mediaController.playingMessageObject) || MediaController.this.isMessagePaused()) {
                if (MediaController.this.recordStartRunnable != null || MediaController.this.recordingAudio != null) {
                    MediaController.this.stopRecording(2, false, 0);
                }
            } else {
                MediaController mediaController2 = MediaController.this;
                mediaController2.lambda$startAudioAgain$7(mediaController2.playingMessageObject);
            }
            EmbedBottomSheet embedBottomSheet2 = EmbedBottomSheet.getInstance();
            if (embedBottomSheet2 != null) {
                embedBottomSheet2.pause();
            }
            MediaController.this.callInProgress = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4() {
        for (int i = 0; i < 1; i++) {
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.fileLoaded);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.httpFileDidLoad);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.didReceiveNewMessages);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.messagesDeleted);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.removeAllMessagesFromDialog);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.musicDidLoad);
            NotificationCenter.getInstance(i).addObserver(this, NotificationCenter.mediaDidLoad);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.playerDidStartPlaying);
        }
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$onAudioFocusChange$5(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAudioFocusChange$5(int i) {
        if (i == -1) {
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                lambda$startAudioAgain$7(this.playingMessageObject);
            }
            this.hasAudioFocus = 0;
            this.audioFocus = 0;
        } else if (i == 1) {
            this.audioFocus = 2;
            if (this.resumeAudioOnFocusGain) {
                this.resumeAudioOnFocusGain = false;
                if (isPlayingMessage(getPlayingMessageObject()) && isMessagePaused()) {
                    playMessage(getPlayingMessageObject());
                }
            }
        } else if (i == -3) {
            this.audioFocus = 1;
        } else if (i == -2) {
            this.audioFocus = 0;
            if (isPlayingMessage(getPlayingMessageObject()) && !isMessagePaused()) {
                lambda$startAudioAgain$7(this.playingMessageObject);
                this.resumeAudioOnFocusGain = true;
            }
        }
        setPlayerVolume();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlayerVolume() {
        float f;
        try {
            if (this.isSilent) {
                f = 0.0f;
            } else {
                f = this.audioFocus != 1 ? VOLUME_NORMAL : VOLUME_DUCK;
            }
            VideoPlayer videoPlayer = this.audioPlayer;
            if (videoPlayer != null) {
                videoPlayer.setVolume(f * this.audioVolume);
                return;
            }
            VideoPlayer videoPlayer2 = this.videoPlayer;
            if (videoPlayer2 != null) {
                videoPlayer2.setVolume(f);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public VideoPlayer getVideoPlayer() {
        return this.videoPlayer;
    }

    private void startProgressTimer(MessageObject messageObject) {
        synchronized (this.progressTimerSync) {
            Timer timer = this.progressTimer;
            if (timer != null) {
                try {
                    timer.cancel();
                    this.progressTimer = null;
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            messageObject.getFileName();
            Timer timer2 = new Timer();
            this.progressTimer = timer2;
            timer2.schedule(new AnonymousClass5(messageObject), 0L, 17L);
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$5, reason: invalid class name */
    class AnonymousClass5 extends TimerTask {
        final /* synthetic */ MessageObject val$currentPlayingMessageObject;

        AnonymousClass5(MessageObject messageObject) {
            this.val$currentPlayingMessageObject = messageObject;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            synchronized (MediaController.this.sync) {
                final MessageObject messageObject = this.val$currentPlayingMessageObject;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.AnonymousClass5.this.lambda$run$1(messageObject);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$1(MessageObject messageObject) {
            long duration;
            long currentPosition;
            final float f;
            float f2;
            if ((MediaController.this.audioPlayer == null && MediaController.this.videoPlayer == null) || MediaController.this.isPaused) {
                return;
            }
            try {
                if (MediaController.this.videoPlayer != null) {
                    duration = MediaController.this.videoPlayer.getDuration();
                    currentPosition = MediaController.this.videoPlayer.getCurrentPosition();
                    if (currentPosition >= 0 && duration > 0) {
                        float f3 = duration;
                        f2 = MediaController.this.videoPlayer.getBufferedPosition() / f3;
                        f = currentPosition / f3;
                        if (f >= MediaController.VOLUME_NORMAL) {
                            return;
                        }
                    }
                    return;
                }
                duration = MediaController.this.audioPlayer.getDuration();
                currentPosition = MediaController.this.audioPlayer.getCurrentPosition();
                float f4 = duration >= 0 ? currentPosition / duration : 0.0f;
                float bufferedPosition = MediaController.this.audioPlayer.getBufferedPosition() / duration;
                if (duration != -9223372036854775807L && currentPosition >= 0 && MediaController.this.seekToProgressPending == 0.0f) {
                    f = f4;
                    f2 = bufferedPosition;
                }
                return;
                MediaController.this.lastProgress = currentPosition;
                messageObject.audioPlayerDuration = (int) (duration / 1000);
                messageObject.audioProgress = f;
                messageObject.audioProgressSec = (int) (MediaController.this.lastProgress / 1000);
                messageObject.bufferedProgress = f2;
                if (f >= 0.0f && MediaController.this.shouldSavePositionForCurrentAudio != null && SystemClock.elapsedRealtime() - MediaController.this.lastSaveTime >= 1000) {
                    final String str = MediaController.this.shouldSavePositionForCurrentAudio;
                    MediaController.this.lastSaveTime = SystemClock.elapsedRealtime();
                    Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$5$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaController.AnonymousClass5.lambda$run$0(str, f);
                        }
                    });
                }
                NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(messageObject.getId()), Float.valueOf(f));
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$run$0(String str, float f) {
            ApplicationLoader.applicationContext.getSharedPreferences("media_saved_pos", 0).edit().putFloat(str, f).commit();
        }
    }

    private void stopProgressTimer() {
        synchronized (this.progressTimerSync) {
            Timer timer = this.progressTimer;
            if (timer != null) {
                try {
                    timer.cancel();
                    this.progressTimer = null;
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    public void cleanup() {
        cleanupPlayer(true, true);
        this.audioInfo = null;
        this.playMusicAgain = false;
        for (int i = 0; i < 1; i++) {
            DownloadController.getInstance(i).cleanup();
        }
        this.videoConvertQueue.clear();
        this.generatingWaveform.clear();
        this.voiceMessagesPlaylist = null;
        this.voiceMessagesPlaylistMap = null;
        clearPlaylist();
        cancelVideoConvert(null);
    }

    private void clearPlaylist() {
        this.playlist.clear();
        this.playlistMap.clear();
        this.shuffledPlaylist.clear();
        this.playlistClassGuid = 0;
        boolean[] zArr = this.playlistEndReached;
        zArr[1] = false;
        zArr[0] = false;
        this.playlistMergeDialogId = 0L;
        int[] iArr = this.playlistMaxId;
        iArr[1] = Integer.MAX_VALUE;
        iArr[0] = Integer.MAX_VALUE;
        this.loadingPlaylist = false;
        this.playlistGlobalSearchParams = null;
    }

    public void startMediaObserver() {
        ApplicationLoader.applicationHandler.removeCallbacks(this.stopMediaObserverRunnable);
        this.startObserverToken++;
        try {
            if (this.internalObserver == null) {
                ContentResolver contentResolver = ApplicationLoader.applicationContext.getContentResolver();
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ExternalObserver externalObserver = new ExternalObserver();
                this.externalObserver = externalObserver;
                contentResolver.registerContentObserver(uri, false, externalObserver);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            if (this.externalObserver == null) {
                ContentResolver contentResolver2 = ApplicationLoader.applicationContext.getContentResolver();
                Uri uri2 = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                InternalObserver internalObserver = new InternalObserver();
                this.internalObserver = internalObserver;
                contentResolver2.registerContentObserver(uri2, false, internalObserver);
            }
        } catch (Exception e2) {
            FileLog.e(e2);
        }
    }

    public void stopMediaObserver() {
        if (this.stopMediaObserverRunnable == null) {
            this.stopMediaObserverRunnable = new StopMediaObserverRunnable();
        }
        this.stopMediaObserverRunnable.currentObserverToken = this.startObserverToken;
        ApplicationLoader.applicationHandler.postDelayed(this.stopMediaObserverRunnable, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007a, code lost:
    
        if (r10 != 0) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void processMediaObserver(android.net.Uri r14) {
        /*
            r13 = this;
            r0 = 0
            android.graphics.Point r1 = org.telegram.messenger.AndroidUtilities.getRealScreenSize()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            android.content.Context r2 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            android.content.ContentResolver r3 = r2.getContentResolver()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            java.lang.String[] r5 = r13.mediaProjections     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r6 = 0
            r7 = 0
            java.lang.String r8 = "date_added DESC LIMIT 1"
            r4 = r14
            android.database.Cursor r0 = r3.query(r4, r5, r6, r7, r8)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r14.<init>()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r0 == 0) goto Lb1
        L1d:
            boolean r2 = r0.moveToNext()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r2 == 0) goto Lae
            r2 = 0
            java.lang.String r2 = r0.getString(r2)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r3 = 1
            java.lang.String r4 = r0.getString(r3)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r5 = 2
            java.lang.String r5 = r0.getString(r5)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r6 = 3
            long r6 = r0.getLong(r6)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r8 = 4
            java.lang.String r8 = r0.getString(r8)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r9 = 5
            int r9 = r0.getInt(r9)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r10 = 6
            int r10 = r0.getInt(r10)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            java.lang.String r11 = "screenshot"
            if (r2 == 0) goto L54
            java.lang.String r12 = r2.toLowerCase()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            boolean r12 = r12.contains(r11)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r12 != 0) goto L78
        L54:
            if (r4 == 0) goto L60
            java.lang.String r4 = r4.toLowerCase()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            boolean r4 = r4.contains(r11)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r4 != 0) goto L78
        L60:
            if (r5 == 0) goto L6c
            java.lang.String r4 = r5.toLowerCase()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            boolean r4 = r4.contains(r11)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r4 != 0) goto L78
        L6c:
            if (r8 == 0) goto L1d
            java.lang.String r4 = r8.toLowerCase()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            boolean r4 = r4.contains(r11)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r4 == 0) goto L1d
        L78:
            if (r9 == 0) goto L7c
            if (r10 != 0) goto L8a
        L7c:
            android.graphics.BitmapFactory$Options r4 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            r4.<init>()     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            r4.inJustDecodeBounds = r3     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            android.graphics.BitmapFactory.decodeFile(r2, r4)     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            int r9 = r4.outWidth     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            int r10 = r4.outHeight     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
        L8a:
            if (r9 <= 0) goto L9c
            if (r10 <= 0) goto L9c
            int r2 = r1.x     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            if (r9 != r2) goto L96
            int r3 = r1.y     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            if (r10 == r3) goto L9c
        L96:
            if (r10 != r2) goto L1d
            int r2 = r1.y     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            if (r9 != r2) goto L1d
        L9c:
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            r14.add(r2)     // Catch: java.lang.Exception -> La5 java.lang.Throwable -> Lc5
            goto L1d
        La5:
            java.lang.Long r2 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r14.add(r2)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            goto L1d
        Lae:
            r0.close()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
        Lb1:
            boolean r1 = r14.isEmpty()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            if (r1 != 0) goto Lbf
            org.telegram.messenger.MediaController$$ExternalSyntheticLambda30 r1 = new org.telegram.messenger.MediaController$$ExternalSyntheticLambda30     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            r1.<init>()     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r1)     // Catch: java.lang.Throwable -> Lc5 java.lang.Exception -> Lc7
        Lbf:
            if (r0 == 0) goto Lce
        Lc1:
            r0.close()     // Catch: java.lang.Exception -> Lce
            goto Lce
        Lc5:
            r14 = move-exception
            goto Lcf
        Lc7:
            r14 = move-exception
            org.telegram.messenger.FileLog.e(r14)     // Catch: java.lang.Throwable -> Lc5
            if (r0 == 0) goto Lce
            goto Lc1
        Lce:
            return
        Lcf:
            if (r0 == 0) goto Ld4
            r0.close()     // Catch: java.lang.Exception -> Ld4
        Ld4:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.processMediaObserver(android.net.Uri):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processMediaObserver$6(ArrayList arrayList) {
        NotificationCenter.getInstance(this.lastChatAccount).postNotificationName(NotificationCenter.screenshotTook, new Object[0]);
        checkScreenshots(arrayList);
    }

    private void checkScreenshots(ArrayList<Long> arrayList) {
        if (arrayList == null || arrayList.isEmpty() || this.lastChatEnterTime == 0) {
            return;
        }
        if (this.lastUser != null || (this.lastSecretChat instanceof TLRPC$TL_encryptedChat)) {
            boolean z = false;
            for (int i = 0; i < arrayList.size(); i++) {
                Long l = arrayList.get(i);
                if ((this.lastMediaCheckTime == 0 || l.longValue() > this.lastMediaCheckTime) && l.longValue() >= this.lastChatEnterTime && (this.lastChatLeaveTime == 0 || l.longValue() <= this.lastChatLeaveTime + 2000)) {
                    this.lastMediaCheckTime = Math.max(this.lastMediaCheckTime, l.longValue());
                    z = true;
                }
            }
            if (z) {
                if (this.lastSecretChat != null) {
                    SecretChatHelper.getInstance(this.lastChatAccount).sendScreenshotMessage(this.lastSecretChat, this.lastChatVisibleMessages, null);
                } else {
                    SendMessagesHelper.getInstance(this.lastChatAccount).sendScreenshotMessage(this.lastUser, this.lastMessageId, null);
                }
            }
        }
    }

    public void setLastVisibleMessageIds(int i, long j, long j2, TLRPC$User tLRPC$User, TLRPC$EncryptedChat tLRPC$EncryptedChat, ArrayList<Long> arrayList, int i2) {
        this.lastChatEnterTime = j;
        this.lastChatLeaveTime = j2;
        this.lastChatAccount = i;
        this.lastSecretChat = tLRPC$EncryptedChat;
        this.lastUser = tLRPC$User;
        this.lastMessageId = i2;
        this.lastChatVisibleMessages = arrayList;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        ArrayList<MessageObject> arrayList;
        int indexOf;
        int i3 = 0;
        if (i == NotificationCenter.fileLoaded || i == NotificationCenter.httpFileDidLoad) {
            String str = (String) objArr[0];
            MessageObject messageObject = this.playingMessageObject;
            if (messageObject != null && messageObject.currentAccount == i2 && FileLoader.getAttachFileName(messageObject.getDocument()).equals(str)) {
                if (this.downloadingCurrentMessage) {
                    this.playMusicAgain = true;
                    playMessage(this.playingMessageObject);
                    return;
                } else {
                    if (this.audioInfo == null) {
                        try {
                            this.audioInfo = AudioInfo.getAudioInfo(FileLoader.getInstance(UserConfig.selectedAccount).getPathToMessage(this.playingMessageObject.messageOwner));
                            return;
                        } catch (Exception e) {
                            FileLog.e(e);
                            return;
                        }
                    }
                    return;
                }
            }
            return;
        }
        if (i == NotificationCenter.messagesDeleted) {
            if (((Boolean) objArr[2]).booleanValue()) {
                return;
            }
            long longValue = ((Long) objArr[1]).longValue();
            ArrayList arrayList2 = (ArrayList) objArr[0];
            MessageObject messageObject2 = this.playingMessageObject;
            if (messageObject2 != null && longValue == messageObject2.messageOwner.peer_id.channel_id && arrayList2.contains(Integer.valueOf(messageObject2.getId()))) {
                cleanupPlayer(true, true);
            }
            ArrayList<MessageObject> arrayList3 = this.voiceMessagesPlaylist;
            if (arrayList3 == null || arrayList3.isEmpty() || longValue != this.voiceMessagesPlaylist.get(0).messageOwner.peer_id.channel_id) {
                return;
            }
            while (i3 < arrayList2.size()) {
                Integer num = (Integer) arrayList2.get(i3);
                MessageObject messageObject3 = this.voiceMessagesPlaylistMap.get(num.intValue());
                this.voiceMessagesPlaylistMap.remove(num.intValue());
                if (messageObject3 != null) {
                    this.voiceMessagesPlaylist.remove(messageObject3);
                }
                i3++;
            }
            return;
        }
        if (i == NotificationCenter.removeAllMessagesFromDialog) {
            long longValue2 = ((Long) objArr[0]).longValue();
            MessageObject messageObject4 = this.playingMessageObject;
            if (messageObject4 == null || messageObject4.getDialogId() != longValue2) {
                return;
            }
            cleanupPlayer(false, true);
            return;
        }
        if (i == NotificationCenter.musicDidLoad) {
            long longValue3 = ((Long) objArr[0]).longValue();
            MessageObject messageObject5 = this.playingMessageObject;
            if (messageObject5 == null || !messageObject5.isMusic() || this.playingMessageObject.getDialogId() != longValue3 || this.playingMessageObject.scheduled) {
                return;
            }
            ArrayList arrayList4 = (ArrayList) objArr[1];
            ArrayList arrayList5 = (ArrayList) objArr[2];
            this.playlist.addAll(0, arrayList4);
            this.playlist.addAll(arrayList5);
            int size = this.playlist.size();
            for (int i4 = 0; i4 < size; i4++) {
                MessageObject messageObject6 = this.playlist.get(i4);
                this.playlistMap.put(Integer.valueOf(messageObject6.getId()), messageObject6);
                int[] iArr = this.playlistMaxId;
                iArr[0] = Math.min(iArr[0], messageObject6.getId());
            }
            sortPlaylist();
            if (SharedConfig.shuffleMusic) {
                buildShuffledPlayList();
            } else {
                MessageObject messageObject7 = this.playingMessageObject;
                if (messageObject7 != null && (indexOf = this.playlist.indexOf(messageObject7)) >= 0) {
                    this.currentPlaylistNum = indexOf;
                }
            }
            this.playlistClassGuid = ConnectionsManager.generateClassGuid();
            return;
        }
        if (i == NotificationCenter.mediaDidLoad) {
            if (((Integer) objArr[3]).intValue() != this.playlistClassGuid || this.playingMessageObject == null) {
                return;
            }
            long longValue4 = ((Long) objArr[0]).longValue();
            ((Integer) objArr[4]).intValue();
            ArrayList arrayList6 = (ArrayList) objArr[2];
            DialogObject.isEncryptedDialog(longValue4);
            char c = longValue4 == this.playlistMergeDialogId ? (char) 1 : (char) 0;
            if (!arrayList6.isEmpty()) {
                this.playlistEndReached[c] = ((Boolean) objArr[5]).booleanValue();
            }
            int i5 = 0;
            for (int i6 = 0; i6 < arrayList6.size(); i6++) {
                MessageObject messageObject8 = (MessageObject) arrayList6.get(i6);
                if (!this.playlistMap.containsKey(Integer.valueOf(messageObject8.getId()))) {
                    i5++;
                    this.playlist.add(0, messageObject8);
                    this.playlistMap.put(Integer.valueOf(messageObject8.getId()), messageObject8);
                    int[] iArr2 = this.playlistMaxId;
                    iArr2[c] = Math.min(iArr2[c], messageObject8.getId());
                }
            }
            sortPlaylist();
            int indexOf2 = this.playlist.indexOf(this.playingMessageObject);
            if (indexOf2 >= 0) {
                this.currentPlaylistNum = indexOf2;
            }
            this.loadingPlaylist = false;
            if (SharedConfig.shuffleMusic) {
                buildShuffledPlayList();
            }
            if (i5 != 0) {
                NotificationCenter.getInstance(this.playingMessageObject.currentAccount).postNotificationName(NotificationCenter.moreMusicDidLoad, Integer.valueOf(i5));
                return;
            }
            return;
        }
        if (i == NotificationCenter.didReceiveNewMessages) {
            if (((Boolean) objArr[2]).booleanValue() || (arrayList = this.voiceMessagesPlaylist) == null || arrayList.isEmpty() || ((Long) objArr[0]).longValue() != this.voiceMessagesPlaylist.get(0).getDialogId()) {
                return;
            }
            ArrayList arrayList7 = (ArrayList) objArr[1];
            while (i3 < arrayList7.size()) {
                MessageObject messageObject9 = (MessageObject) arrayList7.get(i3);
                if ((messageObject9.isVoice() || messageObject9.isRoundVideo()) && (!this.voiceMessagesPlaylistUnread || (messageObject9.isContentUnread() && !messageObject9.isOut()))) {
                    this.voiceMessagesPlaylist.add(messageObject9);
                    this.voiceMessagesPlaylistMap.put(messageObject9.getId(), messageObject9);
                }
                i3++;
            }
            return;
        }
        if (i != NotificationCenter.playerDidStartPlaying || getInstance().isCurrentPlayer((VideoPlayer) objArr[0])) {
            return;
        }
        getInstance().lambda$startAudioAgain$7(getInstance().getPlayingMessageObject());
    }

    protected boolean isRecordingAudio() {
        return (this.recordStartRunnable == null && this.recordingAudio == null) ? false : true;
    }

    private boolean isNearToSensor(float f) {
        return f < 5.0f && f != this.proximitySensor.getMaximumRange();
    }

    public boolean isRecordingOrListeningByProximity() {
        MessageObject messageObject;
        return this.proximityTouched && (isRecordingAudio() || ((messageObject = this.playingMessageObject) != null && (messageObject.isVoice() || this.playingMessageObject.isRoundVideo())));
    }

    private boolean forbidRaiseToListen() {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                return NotificationsController.audioManager.isWiredHeadsetOn() || NotificationsController.audioManager.isBluetoothA2dpOn();
            }
            for (AudioDeviceInfo audioDeviceInfo : NotificationsController.audioManager.getDevices(2)) {
                int type = audioDeviceInfo.getType();
                if ((type == 8 || type == 26 || type == 27 || type == 4 || type == 3) && audioDeviceInfo.isSink()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        boolean z;
        int i;
        PowerManager.WakeLock wakeLock;
        PowerManager.WakeLock wakeLock2;
        PowerManager.WakeLock wakeLock3;
        MessageObject messageObject;
        PowerManager.WakeLock wakeLock4;
        PowerManager.WakeLock wakeLock5;
        if (this.sensorsStarted && VoIPService.getSharedInstance() == null) {
            if (sensorEvent.sensor.getType() == 8) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("proximity changed to " + sensorEvent.values[0] + " max value = " + sensorEvent.sensor.getMaximumRange());
                }
                float f = this.lastProximityValue;
                float[] fArr = sensorEvent.values;
                if (f != fArr[0]) {
                    this.proximityHasDifferentValues = true;
                }
                this.lastProximityValue = fArr[0];
                if (this.proximityHasDifferentValues) {
                    this.proximityTouched = isNearToSensor(fArr[0]);
                }
            } else {
                Sensor sensor = sensorEvent.sensor;
                if (sensor == this.accelerometerSensor) {
                    double d = this.lastTimestamp == 0 ? 0.9800000190734863d : 1.0d / (((sensorEvent.timestamp - r1) / 1.0E9d) + 1.0d);
                    this.lastTimestamp = sensorEvent.timestamp;
                    float[] fArr2 = this.gravity;
                    double d2 = 1.0d - d;
                    float[] fArr3 = sensorEvent.values;
                    fArr2[0] = (float) ((fArr2[0] * d) + (fArr3[0] * d2));
                    fArr2[1] = (float) ((fArr2[1] * d) + (fArr3[1] * d2));
                    fArr2[2] = (float) ((d * fArr2[2]) + (d2 * fArr3[2]));
                    float[] fArr4 = this.gravityFast;
                    fArr4[0] = (fArr2[0] * 0.8f) + (fArr3[0] * 0.19999999f);
                    fArr4[1] = (fArr2[1] * 0.8f) + (fArr3[1] * 0.19999999f);
                    fArr4[2] = (fArr2[2] * 0.8f) + (fArr3[2] * 0.19999999f);
                    float[] fArr5 = this.linearAcceleration;
                    fArr5[0] = fArr3[0] - fArr2[0];
                    fArr5[1] = fArr3[1] - fArr2[1];
                    fArr5[2] = fArr3[2] - fArr2[2];
                } else if (sensor == this.linearSensor) {
                    float[] fArr6 = this.linearAcceleration;
                    float[] fArr7 = sensorEvent.values;
                    fArr6[0] = fArr7[0];
                    fArr6[1] = fArr7[1];
                    fArr6[2] = fArr7[2];
                } else if (sensor == this.gravitySensor) {
                    float[] fArr8 = this.gravityFast;
                    float[] fArr9 = this.gravity;
                    float[] fArr10 = sensorEvent.values;
                    float f2 = fArr10[0];
                    fArr9[0] = f2;
                    fArr8[0] = f2;
                    float f3 = fArr10[1];
                    fArr9[1] = f3;
                    fArr8[1] = f3;
                    float f4 = fArr10[2];
                    fArr9[2] = f4;
                    fArr8[2] = f4;
                }
            }
            Sensor sensor2 = sensorEvent.sensor;
            if (sensor2 == this.linearSensor || sensor2 == this.gravitySensor || sensor2 == this.accelerometerSensor) {
                float[] fArr11 = this.gravity;
                float f5 = fArr11[0];
                float[] fArr12 = this.linearAcceleration;
                float f6 = (f5 * fArr12[0]) + (fArr11[1] * fArr12[1]) + (fArr11[2] * fArr12[2]);
                int i2 = this.raisedToBack;
                if (i2 != 6 && ((f6 > 0.0f && this.previousAccValue > 0.0f) || (f6 < 0.0f && this.previousAccValue < 0.0f))) {
                    if (f6 > 0.0f) {
                        z = f6 > 15.0f;
                        i = 1;
                    } else {
                        z = f6 < -15.0f;
                        i = 2;
                    }
                    int i3 = this.raisedToTopSign;
                    if (i3 != 0 && i3 != i) {
                        int i4 = this.raisedToTop;
                        if (i4 != 6 || !z) {
                            if (!z) {
                                this.countLess++;
                            }
                            if (this.countLess == 10 || i4 != 6 || i2 != 0) {
                                this.raisedToTop = 0;
                                this.raisedToTopSign = 0;
                                this.raisedToBack = 0;
                                this.countLess = 0;
                            }
                        } else if (i2 < 6) {
                            int i5 = i2 + 1;
                            this.raisedToBack = i5;
                            if (i5 == 6) {
                                this.raisedToTop = 0;
                                this.raisedToTopSign = 0;
                                this.countLess = 0;
                                this.timeSinceRaise = System.currentTimeMillis();
                                if (BuildVars.LOGS_ENABLED && BuildVars.DEBUG_PRIVATE_VERSION) {
                                    FileLog.d("motion detected");
                                }
                            }
                        }
                    } else if (z && i2 == 0 && (i3 == 0 || i3 == i)) {
                        int i6 = this.raisedToTop;
                        if (i6 < 6 && !this.proximityTouched) {
                            this.raisedToTopSign = i;
                            int i7 = i6 + 1;
                            this.raisedToTop = i7;
                            if (i7 == 6) {
                                this.countLess = 0;
                            }
                        }
                    } else {
                        if (!z) {
                            this.countLess++;
                        }
                        if (i3 != i || this.countLess == 10 || this.raisedToTop != 6 || i2 != 0) {
                            this.raisedToBack = 0;
                            this.raisedToTop = 0;
                            this.raisedToTopSign = 0;
                            this.countLess = 0;
                        }
                    }
                }
                this.previousAccValue = f6;
                float[] fArr13 = this.gravityFast;
                this.accelerometerVertical = fArr13[1] > 2.5f && Math.abs(fArr13[2]) < 4.0f && Math.abs(this.gravityFast[0]) > 1.5f;
            }
            if (this.raisedToBack == 6 || this.accelerometerVertical) {
                this.lastAccelerometerDetected = System.currentTimeMillis();
            }
            if (this.proximityTouched && ((this.raisedToBack == 6 || this.accelerometerVertical || System.currentTimeMillis() - this.lastAccelerometerDetected < 60) && !VoIPService.isAnyKindOfCallActive() && !this.manualRecording && !forbidRaiseToListen())) {
                if (SharedConfig.enabledRaiseTo(true) && this.playingMessageObject == null && this.recordStartRunnable == null && this.recordingAudio == null && !PhotoViewer.getInstance().isVisible() && ApplicationLoader.isScreenOn && !this.inputFieldHasText && this.allowStartRecord && this.raiseChat != null && !this.callInProgress) {
                    if (!this.raiseToEarRecord) {
                        if (BuildVars.LOGS_ENABLED) {
                            FileLog.d("start record");
                        }
                        this.useFrontSpeaker = true;
                        if (!this.raiseChat.playFirstUnreadVoiceMessage()) {
                            this.raiseToEarRecord = true;
                            this.useFrontSpeaker = false;
                            startRecording(this.raiseChat.getCurrentAccount(), this.raiseChat.getDialogId(), null, this.raiseChat.getThreadMessage(), this.raiseChat.getClassGuid(), false);
                        }
                        if (this.useFrontSpeaker) {
                            setUseFrontSpeaker(true);
                        }
                        this.ignoreOnPause = true;
                        if (this.proximityHasDifferentValues && (wakeLock5 = this.proximityWakeLock) != null && !wakeLock5.isHeld()) {
                            this.proximityWakeLock.acquire();
                        }
                    }
                } else if (SharedConfig.enabledRaiseTo(false) && (messageObject = this.playingMessageObject) != null && ((messageObject.isVoice() || this.playingMessageObject.isRoundVideo()) && !this.useFrontSpeaker)) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("start listen");
                    }
                    if (this.proximityHasDifferentValues && (wakeLock4 = this.proximityWakeLock) != null && !wakeLock4.isHeld()) {
                        this.proximityWakeLock.acquire();
                    }
                    setUseFrontSpeaker(true);
                    startAudioAgain(false);
                    this.ignoreOnPause = true;
                }
                this.raisedToBack = 0;
                this.raisedToTop = 0;
                this.raisedToTopSign = 0;
                this.countLess = 0;
            } else if (this.proximityTouched && ((((this.accelerometerSensor == null || this.linearSensor == null) && this.gravitySensor == null) || ignoreAccelerometerGestures()) && !VoIPService.isAnyKindOfCallActive())) {
                if (this.playingMessageObject != null && !ApplicationLoader.mainInterfacePaused && ((this.playingMessageObject.isVoice() || this.playingMessageObject.isRoundVideo()) && SharedConfig.enabledRaiseTo(false) && !this.useFrontSpeaker && !this.manualRecording && !forbidRaiseToListen())) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("start listen by proximity only");
                    }
                    if (this.proximityHasDifferentValues && (wakeLock3 = this.proximityWakeLock) != null && !wakeLock3.isHeld()) {
                        this.proximityWakeLock.acquire();
                    }
                    setUseFrontSpeaker(true);
                    startAudioAgain(false);
                    this.ignoreOnPause = true;
                }
            } else if (!this.proximityTouched && !this.manualRecording) {
                if (this.raiseToEarRecord) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("stop record");
                    }
                    stopRecording(2, false, 0);
                    this.raiseToEarRecord = false;
                    this.ignoreOnPause = false;
                    if (this.proximityHasDifferentValues && (wakeLock2 = this.proximityWakeLock) != null && wakeLock2.isHeld()) {
                        this.proximityWakeLock.release();
                    }
                } else if (this.useFrontSpeaker) {
                    if (BuildVars.LOGS_ENABLED) {
                        FileLog.d("stop listen");
                    }
                    this.useFrontSpeaker = false;
                    startAudioAgain(true);
                    this.ignoreOnPause = false;
                    if (this.proximityHasDifferentValues && (wakeLock = this.proximityWakeLock) != null && wakeLock.isHeld()) {
                        this.proximityWakeLock.release();
                    }
                }
            }
            if (this.timeSinceRaise == 0 || this.raisedToBack != 6 || Math.abs(System.currentTimeMillis() - this.timeSinceRaise) <= 1000) {
                return;
            }
            this.raisedToBack = 0;
            this.raisedToTop = 0;
            this.raisedToTopSign = 0;
            this.countLess = 0;
            this.timeSinceRaise = 0L;
        }
    }

    private void setUseFrontSpeaker(boolean z) {
        this.useFrontSpeaker = z;
        AudioManager audioManager = NotificationsController.audioManager;
        if (z) {
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
        } else {
            audioManager.setSpeakerphoneOn(true);
        }
    }

    public void startRecordingIfFromSpeaker() {
        if (this.useFrontSpeaker && this.raiseChat != null && this.allowStartRecord && SharedConfig.enabledRaiseTo(true)) {
            this.raiseToEarRecord = true;
            startRecording(this.raiseChat.getCurrentAccount(), this.raiseChat.getDialogId(), null, this.raiseChat.getThreadMessage(), this.raiseChat.getClassGuid(), false);
            this.ignoreOnPause = true;
        }
    }

    private void startAudioAgain(boolean z) {
        MessageObject messageObject = this.playingMessageObject;
        if (messageObject == null) {
            return;
        }
        NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.audioRouteChanged, Boolean.valueOf(this.useFrontSpeaker));
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.setStreamType(this.useFrontSpeaker ? 0 : 3);
            if (!z) {
                if (this.videoPlayer.getCurrentPosition() < 1000) {
                    this.videoPlayer.seekTo(0L);
                }
                this.videoPlayer.play();
                return;
            }
            lambda$startAudioAgain$7(this.playingMessageObject);
            return;
        }
        VideoPlayer videoPlayer2 = this.audioPlayer;
        boolean z2 = videoPlayer2 != null;
        final MessageObject messageObject2 = this.playingMessageObject;
        float f = messageObject2.audioProgress;
        int i = messageObject2.audioPlayerDuration;
        if (z || videoPlayer2 == null || !videoPlayer2.isPlaying() || i * f > VOLUME_NORMAL) {
            messageObject2.audioProgress = f;
        } else {
            messageObject2.audioProgress = 0.0f;
        }
        cleanupPlayer(false, true);
        playMessage(messageObject2);
        if (z) {
            if (z2) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda31
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.this.lambda$startAudioAgain$7(messageObject2);
                    }
                }, 100L);
            } else {
                lambda$startAudioAgain$7(messageObject2);
            }
        }
    }

    public void setInputFieldHasText(boolean z) {
        this.inputFieldHasText = z;
    }

    public void setAllowStartRecord(boolean z) {
        this.allowStartRecord = z;
    }

    public void startRaiseToEarSensors(ChatActivity chatActivity) {
        if (chatActivity != null) {
            if ((this.accelerometerSensor == null && (this.gravitySensor == null || this.linearAcceleration == null)) || this.proximitySensor == null) {
                return;
            }
            if (!SharedConfig.enabledRaiseTo(false)) {
                MessageObject messageObject = this.playingMessageObject;
                if (messageObject == null) {
                    return;
                }
                if (!messageObject.isVoice() && !this.playingMessageObject.isRoundVideo()) {
                    return;
                }
            }
            this.raiseChat = chatActivity;
            if (this.sensorsStarted) {
                return;
            }
            float[] fArr = this.gravity;
            fArr[2] = 0.0f;
            fArr[1] = 0.0f;
            fArr[0] = 0.0f;
            float[] fArr2 = this.linearAcceleration;
            fArr2[2] = 0.0f;
            fArr2[1] = 0.0f;
            fArr2[0] = 0.0f;
            float[] fArr3 = this.gravityFast;
            fArr3[2] = 0.0f;
            fArr3[1] = 0.0f;
            fArr3[0] = 0.0f;
            this.lastTimestamp = 0L;
            this.previousAccValue = 0.0f;
            this.raisedToTop = 0;
            this.raisedToTopSign = 0;
            this.countLess = 0;
            this.raisedToBack = 0;
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$startRaiseToEarSensors$8();
                }
            });
            this.sensorsStarted = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRaiseToEarSensors$8() {
        Sensor sensor = this.gravitySensor;
        if (sensor != null) {
            this.sensorManager.registerListener(this, sensor, 30000);
        }
        Sensor sensor2 = this.linearSensor;
        if (sensor2 != null) {
            this.sensorManager.registerListener(this, sensor2, 30000);
        }
        Sensor sensor3 = this.accelerometerSensor;
        if (sensor3 != null) {
            this.sensorManager.registerListener(this, sensor3, 30000);
        }
        this.sensorManager.registerListener(this, this.proximitySensor, 3);
    }

    public void stopRaiseToEarSensors(ChatActivity chatActivity, boolean z, boolean z2) {
        PowerManager.WakeLock wakeLock;
        if (this.ignoreOnPause) {
            this.ignoreOnPause = false;
            return;
        }
        if (z2) {
            stopRecording(z ? 2 : 0, false, 0);
        }
        if (!this.sensorsStarted || this.ignoreOnPause) {
            return;
        }
        if ((this.accelerometerSensor == null && (this.gravitySensor == null || this.linearAcceleration == null)) || this.proximitySensor == null || this.raiseChat != chatActivity) {
            return;
        }
        this.raiseChat = null;
        this.sensorsStarted = false;
        this.accelerometerVertical = false;
        this.proximityTouched = false;
        this.raiseToEarRecord = false;
        this.useFrontSpeaker = false;
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$stopRaiseToEarSensors$9();
            }
        });
        if (this.proximityHasDifferentValues && (wakeLock = this.proximityWakeLock) != null && wakeLock.isHeld()) {
            this.proximityWakeLock.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopRaiseToEarSensors$9() {
        Sensor sensor = this.linearSensor;
        if (sensor != null) {
            this.sensorManager.unregisterListener(this, sensor);
        }
        Sensor sensor2 = this.gravitySensor;
        if (sensor2 != null) {
            this.sensorManager.unregisterListener(this, sensor2);
        }
        Sensor sensor3 = this.accelerometerSensor;
        if (sensor3 != null) {
            this.sensorManager.unregisterListener(this, sensor3);
        }
        this.sensorManager.unregisterListener(this, this.proximitySensor);
    }

    public void cleanupPlayer(boolean z, boolean z2) {
        cleanupPlayer(z, z2, false, false);
    }

    public void cleanupPlayer(boolean z, boolean z2, boolean z3, boolean z4) {
        PipRoundVideoView pipRoundVideoView;
        MessageObject messageObject;
        if (this.audioPlayer != null) {
            ValueAnimator valueAnimator = this.audioVolumeAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllUpdateListeners();
                this.audioVolumeAnimator.cancel();
            }
            if (this.audioPlayer.isPlaying() && (messageObject = this.playingMessageObject) != null && !messageObject.isVoice()) {
                final VideoPlayer videoPlayer = this.audioPlayer;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.audioVolume, 0.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        MediaController.this.lambda$cleanupPlayer$10(videoPlayer, valueAnimator2);
                    }
                });
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.messenger.MediaController.6
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        try {
                            videoPlayer.releasePlayer(true);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                });
                ofFloat.setDuration(300L);
                ofFloat.start();
            } else {
                try {
                    this.audioPlayer.releasePlayer(true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            this.audioPlayer = null;
            Theme.unrefAudioVisualizeDrawable(this.playingMessageObject);
        } else {
            VideoPlayer videoPlayer2 = this.videoPlayer;
            if (videoPlayer2 != null) {
                this.currentAspectRatioFrameLayout = null;
                this.currentTextureViewContainer = null;
                this.currentAspectRatioFrameLayoutReady = false;
                this.isDrawingWasReady = false;
                this.currentTextureView = null;
                this.goingToShowMessageObject = null;
                if (z4) {
                    PhotoViewer.getInstance().injectVideoPlayer(this.videoPlayer);
                    MessageObject messageObject2 = this.playingMessageObject;
                    this.goingToShowMessageObject = messageObject2;
                    NotificationCenter.getInstance(messageObject2.currentAccount).postNotificationName(NotificationCenter.messagePlayingGoingToStop, this.playingMessageObject, Boolean.TRUE);
                } else {
                    long currentPosition = videoPlayer2.getCurrentPosition();
                    MessageObject messageObject3 = this.playingMessageObject;
                    if (messageObject3 != null && messageObject3.isVideo() && currentPosition > 0) {
                        MessageObject messageObject4 = this.playingMessageObject;
                        messageObject4.audioProgressMs = (int) currentPosition;
                        NotificationCenter.getInstance(messageObject4.currentAccount).postNotificationName(NotificationCenter.messagePlayingGoingToStop, this.playingMessageObject, Boolean.FALSE);
                    }
                    this.videoPlayer.releasePlayer(true);
                    this.videoPlayer = null;
                }
                try {
                    this.baseActivity.getWindow().clearFlags(128);
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                if (this.playingMessageObject != null && !z4) {
                    AndroidUtilities.cancelRunOnUIThread(this.setLoadingRunnable);
                    FileLoader.getInstance(this.playingMessageObject.currentAccount).removeLoadingVideo(this.playingMessageObject.getDocument(), true, false);
                }
            }
        }
        stopProgressTimer();
        this.lastProgress = 0L;
        this.isPaused = false;
        if (!this.useFrontSpeaker && !SharedConfig.enabledRaiseTo(true)) {
            ChatActivity chatActivity = this.raiseChat;
            stopRaiseToEarSensors(chatActivity, false, false);
            this.raiseChat = chatActivity;
        }
        PowerManager.WakeLock wakeLock = this.proximityWakeLock;
        if (wakeLock != null && wakeLock.isHeld() && !this.proximityTouched) {
            this.proximityWakeLock.release();
        }
        MessageObject messageObject5 = this.playingMessageObject;
        if (messageObject5 != null) {
            if (this.downloadingCurrentMessage) {
                FileLoader.getInstance(messageObject5.currentAccount).cancelLoadFile(this.playingMessageObject.getDocument());
            }
            MessageObject messageObject6 = this.playingMessageObject;
            if (z) {
                messageObject6.resetPlayingProgress();
                NotificationCenter.getInstance(messageObject6.currentAccount).postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), 0);
            }
            this.playingMessageObject = null;
            this.downloadingCurrentMessage = false;
            if (z) {
                NotificationsController.audioManager.abandonAudioFocus(this);
                this.hasAudioFocus = 0;
                int i = -1;
                ArrayList<MessageObject> arrayList = this.voiceMessagesPlaylist;
                if (arrayList != null) {
                    if (z3 && (i = arrayList.indexOf(messageObject6)) >= 0) {
                        this.voiceMessagesPlaylist.remove(i);
                        this.voiceMessagesPlaylistMap.remove(messageObject6.getId());
                        if (this.voiceMessagesPlaylist.isEmpty()) {
                            this.voiceMessagesPlaylist = null;
                            this.voiceMessagesPlaylistMap = null;
                        }
                    } else {
                        this.voiceMessagesPlaylist = null;
                        this.voiceMessagesPlaylistMap = null;
                    }
                }
                ArrayList<MessageObject> arrayList2 = this.voiceMessagesPlaylist;
                if (arrayList2 != null && i < arrayList2.size()) {
                    MessageObject messageObject7 = this.voiceMessagesPlaylist.get(i);
                    playMessage(messageObject7);
                    if (!messageObject7.isRoundVideo() && (pipRoundVideoView = this.pipRoundVideoView) != null) {
                        pipRoundVideoView.close(true);
                        this.pipRoundVideoView = null;
                    }
                } else {
                    if ((messageObject6.isVoice() || messageObject6.isRoundVideo()) && messageObject6.getId() != 0) {
                        startRecordingIfFromSpeaker();
                    }
                    NotificationCenter.getInstance(messageObject6.currentAccount).postNotificationName(NotificationCenter.messagePlayingDidReset, Integer.valueOf(messageObject6.getId()), Boolean.valueOf(z2));
                    this.pipSwitchingState = 0;
                    PipRoundVideoView pipRoundVideoView2 = this.pipRoundVideoView;
                    if (pipRoundVideoView2 != null) {
                        pipRoundVideoView2.close(true);
                        this.pipRoundVideoView = null;
                    }
                }
            }
            if (z2) {
                ApplicationLoader.applicationContext.stopService(new Intent(ApplicationLoader.applicationContext, (Class<?>) MusicPlayerService.class));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanupPlayer$10(VideoPlayer videoPlayer, ValueAnimator valueAnimator) {
        videoPlayer.setVolume((this.audioFocus != 1 ? VOLUME_NORMAL : VOLUME_DUCK) * ((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public boolean isGoingToShowMessageObject(MessageObject messageObject) {
        return this.goingToShowMessageObject == messageObject;
    }

    public void resetGoingToShowMessageObject() {
        this.goingToShowMessageObject = null;
    }

    private boolean isSamePlayingMessage(MessageObject messageObject) {
        MessageObject messageObject2 = this.playingMessageObject;
        if (messageObject2 != null && messageObject2.getDialogId() == messageObject.getDialogId() && this.playingMessageObject.getId() == messageObject.getId()) {
            if ((this.playingMessageObject.eventId == 0) == (messageObject.eventId == 0)) {
                return true;
            }
        }
        return false;
    }

    public boolean seekToProgress(MessageObject messageObject, float f) {
        MessageObject messageObject2 = this.playingMessageObject;
        if ((this.audioPlayer != null || this.videoPlayer != null) && messageObject != null && messageObject2 != null && isSamePlayingMessage(messageObject)) {
            try {
                VideoPlayer videoPlayer = this.audioPlayer;
                if (videoPlayer != null) {
                    long duration = videoPlayer.getDuration();
                    if (duration == -9223372036854775807L) {
                        this.seekToProgressPending = f;
                    } else {
                        messageObject2.audioProgress = f;
                        long j = (int) (duration * f);
                        this.audioPlayer.seekTo(j);
                        this.lastProgress = j;
                    }
                } else {
                    VideoPlayer videoPlayer2 = this.videoPlayer;
                    if (videoPlayer2 != null) {
                        videoPlayer2.seekTo((long) (videoPlayer2.getDuration() * f));
                    }
                }
                NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingDidSeek, Integer.valueOf(messageObject2.getId()), Float.valueOf(f));
                return true;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        return false;
    }

    public long getDuration() {
        VideoPlayer videoPlayer = this.audioPlayer;
        if (videoPlayer == null) {
            return 0L;
        }
        return videoPlayer.getDuration();
    }

    public MessageObject getPlayingMessageObject() {
        return this.playingMessageObject;
    }

    public int getPlayingMessageObjectNum() {
        return this.currentPlaylistNum;
    }

    private void buildShuffledPlayList() {
        if (this.playlist.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.playlist);
        this.shuffledPlaylist.clear();
        MessageObject messageObject = this.playlist.get(this.currentPlaylistNum);
        arrayList.remove(this.currentPlaylistNum);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            int nextInt = Utilities.random.nextInt(arrayList.size());
            this.shuffledPlaylist.add((MessageObject) arrayList.get(nextInt));
            arrayList.remove(nextInt);
        }
        this.shuffledPlaylist.add(messageObject);
        this.currentPlaylistNum = this.shuffledPlaylist.size() - 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0103  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadMoreMusic() {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.loadMoreMusic():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreMusic$12(final int i, final int i2, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$loadMoreMusic$11(i, tLRPC$TL_error, tLObject, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMoreMusic$11(int i, TLRPC$TL_error tLRPC$TL_error, TLObject tLObject, int i2) {
        PlaylistGlobalSearchParams playlistGlobalSearchParams;
        if (this.playlistClassGuid != i || (playlistGlobalSearchParams = this.playlistGlobalSearchParams) == null || this.playingMessageObject == null || tLRPC$TL_error != null) {
            return;
        }
        this.loadingPlaylist = false;
        TLRPC$messages_Messages tLRPC$messages_Messages = (TLRPC$messages_Messages) tLObject;
        playlistGlobalSearchParams.nextSearchRate = tLRPC$messages_Messages.next_rate;
        MessagesStorage.getInstance(i2).putUsersAndChats(tLRPC$messages_Messages.users, tLRPC$messages_Messages.chats, true, true);
        MessagesController.getInstance(i2).putUsers(tLRPC$messages_Messages.users, false);
        MessagesController.getInstance(i2).putChats(tLRPC$messages_Messages.chats, false);
        int size = tLRPC$messages_Messages.messages.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            MessageObject messageObject = new MessageObject(i2, tLRPC$messages_Messages.messages.get(i4), false, true);
            if (!this.playlistMap.containsKey(Integer.valueOf(messageObject.getId()))) {
                this.playlist.add(0, messageObject);
                this.playlistMap.put(Integer.valueOf(messageObject.getId()), messageObject);
                i3++;
            }
        }
        sortPlaylist();
        this.loadingPlaylist = false;
        this.playlistGlobalSearchParams.endReached = this.playlist.size() == this.playlistGlobalSearchParams.totalCount;
        if (SharedConfig.shuffleMusic) {
            buildShuffledPlayList();
        }
        if (i3 != 0) {
            NotificationCenter.getInstance(this.playingMessageObject.currentAccount).postNotificationName(NotificationCenter.moreMusicDidLoad, Integer.valueOf(i3));
        }
    }

    public boolean setPlaylist(ArrayList<MessageObject> arrayList, MessageObject messageObject, long j, PlaylistGlobalSearchParams playlistGlobalSearchParams) {
        return setPlaylist(arrayList, messageObject, j, true, playlistGlobalSearchParams);
    }

    public boolean setPlaylist(ArrayList<MessageObject> arrayList, MessageObject messageObject, long j) {
        return setPlaylist(arrayList, messageObject, j, true, null);
    }

    public boolean setPlaylist(ArrayList<MessageObject> arrayList, MessageObject messageObject, long j, boolean z, PlaylistGlobalSearchParams playlistGlobalSearchParams) {
        if (this.playingMessageObject == messageObject) {
            int indexOf = this.playlist.indexOf(messageObject);
            if (indexOf >= 0) {
                this.currentPlaylistNum = indexOf;
            }
            return playMessage(messageObject);
        }
        this.forceLoopCurrentPlaylist = !z;
        this.playlistMergeDialogId = j;
        this.playMusicAgain = !this.playlist.isEmpty();
        clearPlaylist();
        this.playlistGlobalSearchParams = playlistGlobalSearchParams;
        boolean z2 = false;
        if (!arrayList.isEmpty() && DialogObject.isEncryptedDialog(arrayList.get(0).getDialogId())) {
            z2 = true;
        }
        int i = Integer.MAX_VALUE;
        int i2 = LinearLayoutManager.INVALID_OFFSET;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            MessageObject messageObject2 = arrayList.get(size);
            if (messageObject2.isMusic()) {
                int id = messageObject2.getId();
                if (id > 0 || z2) {
                    i = Math.min(i, id);
                    i2 = Math.max(i2, id);
                }
                this.playlist.add(messageObject2);
                this.playlistMap.put(Integer.valueOf(id), messageObject2);
            }
        }
        sortPlaylist();
        int indexOf2 = this.playlist.indexOf(messageObject);
        this.currentPlaylistNum = indexOf2;
        if (indexOf2 == -1) {
            clearPlaylist();
            this.currentPlaylistNum = this.playlist.size();
            this.playlist.add(messageObject);
            this.playlistMap.put(Integer.valueOf(messageObject.getId()), messageObject);
        }
        if (messageObject.isMusic() && !messageObject.scheduled) {
            if (SharedConfig.shuffleMusic) {
                buildShuffledPlayList();
            }
            if (z) {
                if (this.playlistGlobalSearchParams == null) {
                    MediaDataController.getInstance(messageObject.currentAccount).loadMusic(messageObject.getDialogId(), i, i2);
                } else {
                    this.playlistClassGuid = ConnectionsManager.generateClassGuid();
                }
            }
        }
        return playMessage(messageObject);
    }

    private void sortPlaylist() {
        Collections.sort(this.playlist, new Comparator() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda42
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int lambda$sortPlaylist$13;
                lambda$sortPlaylist$13 = MediaController.lambda$sortPlaylist$13((MessageObject) obj, (MessageObject) obj2);
                return lambda$sortPlaylist$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$sortPlaylist$13(MessageObject messageObject, MessageObject messageObject2) {
        int compare;
        int id = messageObject.getId();
        int id2 = messageObject2.getId();
        long j = messageObject.messageOwner.grouped_id;
        long j2 = messageObject2.messageOwner.grouped_id;
        if (id >= 0 || id2 >= 0) {
            if (j != 0 && j == j2) {
                compare = Integer.compare(id2, id);
            } else {
                return Integer.compare(id, id2);
            }
        } else if (j != 0 && j == j2) {
            compare = Integer.compare(id, id2);
        } else {
            return Integer.compare(id2, id);
        }
        return -compare;
    }

    public void playNextMessage() {
        playNextMessageWithoutOrder(false);
    }

    public boolean findMessageInPlaylistAndPlay(MessageObject messageObject) {
        int indexOf = this.playlist.indexOf(messageObject);
        if (indexOf == -1) {
            return playMessage(messageObject);
        }
        playMessageAtIndex(indexOf);
        return true;
    }

    public void playMessageAtIndex(int i) {
        int i2 = this.currentPlaylistNum;
        if (i2 < 0 || i2 >= this.playlist.size()) {
            return;
        }
        this.currentPlaylistNum = i;
        this.playMusicAgain = true;
        MessageObject messageObject = this.playlist.get(i);
        if (this.playingMessageObject != null && !isSamePlayingMessage(messageObject)) {
            this.playingMessageObject.resetPlayingProgress();
        }
        playMessage(messageObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playNextMessageWithoutOrder(boolean z) {
        int i;
        ArrayList<MessageObject> arrayList = SharedConfig.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (z && (((i = SharedConfig.repeatMode) == 2 || (i == 1 && arrayList.size() == 1)) && !this.forceLoopCurrentPlaylist)) {
            cleanupPlayer(false, false);
            MessageObject messageObject = arrayList.get(this.currentPlaylistNum);
            messageObject.audioProgress = 0.0f;
            messageObject.audioProgressSec = 0;
            playMessage(messageObject);
            return;
        }
        if (traversePlaylist(arrayList, SharedConfig.playOrderReversed ? 1 : -1) && z && SharedConfig.repeatMode == 0 && !this.forceLoopCurrentPlaylist) {
            VideoPlayer videoPlayer = this.audioPlayer;
            if (videoPlayer == null && this.videoPlayer == null) {
                return;
            }
            if (videoPlayer != null) {
                try {
                    videoPlayer.releasePlayer(true);
                } catch (Exception e) {
                    FileLog.e(e);
                }
                this.audioPlayer = null;
                Theme.unrefAudioVisualizeDrawable(this.playingMessageObject);
            } else {
                this.currentAspectRatioFrameLayout = null;
                this.currentTextureViewContainer = null;
                this.currentAspectRatioFrameLayoutReady = false;
                this.currentTextureView = null;
                this.videoPlayer.releasePlayer(true);
                this.videoPlayer = null;
                try {
                    this.baseActivity.getWindow().clearFlags(128);
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                AndroidUtilities.cancelRunOnUIThread(this.setLoadingRunnable);
                FileLoader.getInstance(this.playingMessageObject.currentAccount).removeLoadingVideo(this.playingMessageObject.getDocument(), true, false);
            }
            stopProgressTimer();
            this.lastProgress = 0L;
            this.isPaused = true;
            MessageObject messageObject2 = this.playingMessageObject;
            messageObject2.audioProgress = 0.0f;
            messageObject2.audioProgressSec = 0;
            NotificationCenter.getInstance(messageObject2.currentAccount).postNotificationName(NotificationCenter.messagePlayingProgressDidChanged, Integer.valueOf(this.playingMessageObject.getId()), 0);
            NotificationCenter.getInstance(this.playingMessageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
            return;
        }
        int i2 = this.currentPlaylistNum;
        if (i2 < 0 || i2 >= arrayList.size()) {
            return;
        }
        MessageObject messageObject3 = this.playingMessageObject;
        if (messageObject3 != null) {
            messageObject3.resetPlayingProgress();
        }
        this.playMusicAgain = true;
        playMessage(arrayList.get(this.currentPlaylistNum));
    }

    public void playPreviousMessage() {
        int i;
        ArrayList<MessageObject> arrayList = SharedConfig.shuffleMusic ? this.shuffledPlaylist : this.playlist;
        if (arrayList.isEmpty() || (i = this.currentPlaylistNum) < 0 || i >= arrayList.size()) {
            return;
        }
        MessageObject messageObject = arrayList.get(this.currentPlaylistNum);
        if (messageObject.audioProgressSec > 10) {
            seekToProgress(messageObject, 0.0f);
            return;
        }
        traversePlaylist(arrayList, SharedConfig.playOrderReversed ? -1 : 1);
        if (this.currentPlaylistNum >= arrayList.size()) {
            return;
        }
        this.playMusicAgain = true;
        playMessage(arrayList.get(this.currentPlaylistNum));
    }

    private boolean traversePlaylist(ArrayList<MessageObject> arrayList, int i) {
        MessageObject messageObject;
        int i2;
        MessageObject messageObject2;
        int i3 = this.currentPlaylistNum;
        boolean z = ConnectionsManager.getInstance(UserConfig.selectedAccount).getConnectionState() == 2;
        this.currentPlaylistNum += i;
        if (z) {
            while (this.currentPlaylistNum < arrayList.size() && (i2 = this.currentPlaylistNum) >= 0 && ((messageObject2 = arrayList.get(i2)) == null || !messageObject2.mediaExists)) {
                this.currentPlaylistNum += i;
            }
        }
        if (this.currentPlaylistNum < arrayList.size() && this.currentPlaylistNum >= 0) {
            return false;
        }
        this.currentPlaylistNum = this.currentPlaylistNum >= arrayList.size() ? 0 : arrayList.size() - 1;
        if (!z) {
            return true;
        }
        while (true) {
            int i4 = this.currentPlaylistNum;
            if (i4 < 0 || i4 >= arrayList.size()) {
                break;
            }
            int i5 = this.currentPlaylistNum;
            if (i > 0) {
                if (i5 > i3) {
                    break;
                }
                messageObject = arrayList.get(this.currentPlaylistNum);
                if (messageObject == null && messageObject.mediaExists) {
                    break;
                }
                this.currentPlaylistNum += i;
            } else {
                if (i5 < i3) {
                    break;
                }
                messageObject = arrayList.get(this.currentPlaylistNum);
                if (messageObject == null) {
                }
                this.currentPlaylistNum += i;
            }
        }
        if (this.currentPlaylistNum < arrayList.size() && this.currentPlaylistNum >= 0) {
            return true;
        }
        this.currentPlaylistNum = this.currentPlaylistNum < arrayList.size() ? arrayList.size() - 1 : 0;
        return true;
    }

    protected void checkIsNextMediaFileDownloaded() {
        MessageObject messageObject = this.playingMessageObject;
        if (messageObject == null || !messageObject.isMusic()) {
            return;
        }
        checkIsNextMusicFileDownloaded(this.playingMessageObject.currentAccount);
    }

    private void checkIsNextVoiceFileDownloaded(int i) {
        ArrayList<MessageObject> arrayList = this.voiceMessagesPlaylist;
        if (arrayList == null || arrayList.size() < 2) {
            return;
        }
        MessageObject messageObject = this.voiceMessagesPlaylist.get(1);
        String str = messageObject.messageOwner.attachPath;
        File file = null;
        if (str != null && str.length() > 0) {
            File file2 = new File(messageObject.messageOwner.attachPath);
            if (file2.exists()) {
                file = file2;
            }
        }
        File pathToMessage = file != null ? file : FileLoader.getInstance(i).getPathToMessage(messageObject.messageOwner);
        pathToMessage.exists();
        if (pathToMessage == file || pathToMessage.exists()) {
            return;
        }
        FileLoader.getInstance(i).loadFile(messageObject.getDocument(), messageObject, 0, 0);
    }

    private void checkIsNextMusicFileDownloaded(int i) {
        int i2;
        if (DownloadController.getInstance(i).canDownloadNextTrack()) {
            ArrayList<MessageObject> arrayList = SharedConfig.shuffleMusic ? this.shuffledPlaylist : this.playlist;
            if (arrayList == null || arrayList.size() < 2) {
                return;
            }
            if (SharedConfig.playOrderReversed) {
                i2 = this.currentPlaylistNum + 1;
                if (i2 >= arrayList.size()) {
                    i2 = 0;
                }
            } else {
                i2 = this.currentPlaylistNum - 1;
                if (i2 < 0) {
                    i2 = arrayList.size() - 1;
                }
            }
            if (i2 < 0 || i2 >= arrayList.size()) {
                return;
            }
            MessageObject messageObject = arrayList.get(i2);
            File file = null;
            if (!TextUtils.isEmpty(messageObject.messageOwner.attachPath)) {
                File file2 = new File(messageObject.messageOwner.attachPath);
                if (file2.exists()) {
                    file = file2;
                }
            }
            File pathToMessage = file != null ? file : FileLoader.getInstance(i).getPathToMessage(messageObject.messageOwner);
            pathToMessage.exists();
            if (pathToMessage == file || pathToMessage.exists() || !messageObject.isMusic()) {
                return;
            }
            FileLoader.getInstance(i).loadFile(messageObject.getDocument(), messageObject, 0, 0);
        }
    }

    public void setVoiceMessagesPlaylist(ArrayList<MessageObject> arrayList, boolean z) {
        ArrayList<MessageObject> arrayList2 = arrayList != null ? new ArrayList<>(arrayList) : null;
        this.voiceMessagesPlaylist = arrayList2;
        if (arrayList2 != null) {
            this.voiceMessagesPlaylistUnread = z;
            this.voiceMessagesPlaylistMap = new SparseArray<>();
            for (int i = 0; i < this.voiceMessagesPlaylist.size(); i++) {
                MessageObject messageObject = this.voiceMessagesPlaylist.get(i);
                this.voiceMessagesPlaylistMap.put(messageObject.getId(), messageObject);
            }
        }
    }

    private void checkAudioFocus(MessageObject messageObject) {
        int i;
        int requestAudioFocus;
        if (messageObject.isVoice() || messageObject.isRoundVideo()) {
            i = this.useFrontSpeaker ? 3 : 2;
        } else {
            i = 1;
        }
        if (this.hasAudioFocus != i) {
            this.hasAudioFocus = i;
            if (i == 3) {
                requestAudioFocus = NotificationsController.audioManager.requestAudioFocus(this, 0, 1);
            } else {
                requestAudioFocus = NotificationsController.audioManager.requestAudioFocus(this, 3, (i != 2 || SharedConfig.pauseMusicOnMedia) ? 1 : 3);
            }
            if (requestAudioFocus == 1) {
                this.audioFocus = 2;
            }
        }
    }

    public boolean isPiPShown() {
        return this.pipRoundVideoView != null;
    }

    public void setCurrentVideoVisible(boolean z) {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.currentAspectRatioFrameLayout;
        if (aspectRatioFrameLayout == null) {
            return;
        }
        if (z) {
            PipRoundVideoView pipRoundVideoView = this.pipRoundVideoView;
            if (pipRoundVideoView != null) {
                this.pipSwitchingState = 2;
                pipRoundVideoView.close(true);
                this.pipRoundVideoView = null;
                return;
            } else {
                if (aspectRatioFrameLayout.getParent() == null) {
                    this.currentTextureViewContainer.addView(this.currentAspectRatioFrameLayout);
                }
                this.videoPlayer.setTextureView(this.currentTextureView);
                return;
            }
        }
        if (aspectRatioFrameLayout.getParent() != null) {
            this.pipSwitchingState = 1;
            this.currentTextureViewContainer.removeView(this.currentAspectRatioFrameLayout);
            return;
        }
        if (this.pipRoundVideoView == null) {
            try {
                PipRoundVideoView pipRoundVideoView2 = new PipRoundVideoView();
                this.pipRoundVideoView = pipRoundVideoView2;
                pipRoundVideoView2.show(this.baseActivity, new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.this.lambda$setCurrentVideoVisible$14();
                    }
                });
            } catch (Exception unused) {
                this.pipRoundVideoView = null;
            }
        }
        PipRoundVideoView pipRoundVideoView3 = this.pipRoundVideoView;
        if (pipRoundVideoView3 != null) {
            this.videoPlayer.setTextureView(pipRoundVideoView3.getTextureView());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setCurrentVideoVisible$14() {
        cleanupPlayer(true, true);
    }

    public void setTextureView(TextureView textureView, AspectRatioFrameLayout aspectRatioFrameLayout, FrameLayout frameLayout, boolean z) {
        setTextureView(textureView, aspectRatioFrameLayout, frameLayout, z, null);
    }

    public void setTextureView(TextureView textureView, AspectRatioFrameLayout aspectRatioFrameLayout, FrameLayout frameLayout, boolean z, Runnable runnable) {
        if (textureView == null) {
            return;
        }
        if (!z && this.currentTextureView == textureView) {
            this.pipSwitchingState = 1;
            this.currentTextureView = null;
            this.currentAspectRatioFrameLayout = null;
            this.currentTextureViewContainer = null;
            return;
        }
        if (this.videoPlayer == null || textureView == this.currentTextureView) {
            return;
        }
        this.isDrawingWasReady = aspectRatioFrameLayout != null && aspectRatioFrameLayout.isDrawingReady();
        this.currentTextureView = textureView;
        if (runnable != null && this.pipRoundVideoView == null) {
            try {
                PipRoundVideoView pipRoundVideoView = new PipRoundVideoView();
                this.pipRoundVideoView = pipRoundVideoView;
                pipRoundVideoView.show(this.baseActivity, new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.this.lambda$setTextureView$15();
                    }
                });
            } catch (Exception unused) {
                this.pipRoundVideoView = null;
            }
        }
        PipRoundVideoView pipRoundVideoView2 = this.pipRoundVideoView;
        if (pipRoundVideoView2 != null) {
            this.videoPlayer.setTextureView(pipRoundVideoView2.getTextureView());
        } else {
            this.videoPlayer.setTextureView(this.currentTextureView);
        }
        this.currentAspectRatioFrameLayout = aspectRatioFrameLayout;
        this.currentTextureViewContainer = frameLayout;
        if (!this.currentAspectRatioFrameLayoutReady || aspectRatioFrameLayout == null) {
            return;
        }
        aspectRatioFrameLayout.setAspectRatio(this.currentAspectRatioFrameLayoutRatio, this.currentAspectRatioFrameLayoutRotation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTextureView$15() {
        cleanupPlayer(true, true);
    }

    public void setBaseActivity(Activity activity, boolean z) {
        if (z) {
            this.baseActivity = activity;
        } else if (this.baseActivity == activity) {
            this.baseActivity = null;
        }
    }

    public void setFeedbackView(View view, boolean z) {
        if (z) {
            this.feedbackView = view;
        } else if (this.feedbackView == view) {
            this.feedbackView = null;
        }
    }

    public void setPlaybackSpeed(boolean z, float f) {
        if (z) {
            if (this.currentMusicPlaybackSpeed >= 6.0f && f == VOLUME_NORMAL && this.playingMessageObject != null) {
                this.audioPlayer.pause();
                final MessageObject messageObject = this.playingMessageObject;
                final float f2 = messageObject.audioProgress;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda32
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.this.lambda$setPlaybackSpeed$16(messageObject, f2);
                    }
                }, 50L);
            }
            this.currentMusicPlaybackSpeed = f;
            if (Math.abs(f - VOLUME_NORMAL) > 0.001f) {
                this.fastMusicPlaybackSpeed = f;
            }
        } else {
            this.currentPlaybackSpeed = f;
            if (Math.abs(f - VOLUME_NORMAL) > 0.001f) {
                this.fastPlaybackSpeed = f;
            }
        }
        VideoPlayer videoPlayer = this.audioPlayer;
        if (videoPlayer != null) {
            videoPlayer.setPlaybackSpeed(Math.round(f * 10.0f) / 10.0f);
        } else {
            VideoPlayer videoPlayer2 = this.videoPlayer;
            if (videoPlayer2 != null) {
                videoPlayer2.setPlaybackSpeed(Math.round(f * 10.0f) / 10.0f);
            }
        }
        MessagesController.getGlobalMainSettings().edit().putFloat(z ? "musicPlaybackSpeed" : "playbackSpeed", f).putFloat(z ? "fastMusicPlaybackSpeed" : "fastPlaybackSpeed", z ? this.fastMusicPlaybackSpeed : this.fastPlaybackSpeed).commit();
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.messagePlayingSpeedChanged, new Object[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlaybackSpeed$16(MessageObject messageObject, float f) {
        if (this.audioPlayer == null || this.playingMessageObject == null || this.isPaused) {
            return;
        }
        if (isSamePlayingMessage(messageObject)) {
            seekToProgress(this.playingMessageObject, f);
        }
        this.audioPlayer.play();
    }

    public float getPlaybackSpeed(boolean z) {
        return z ? this.currentMusicPlaybackSpeed : this.currentPlaybackSpeed;
    }

    public float getFastPlaybackSpeed(boolean z) {
        return z ? this.fastMusicPlaybackSpeed : this.fastPlaybackSpeed;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoState(MessageObject messageObject, int[] iArr, boolean z, boolean z2, int i) {
        MessageObject messageObject2;
        if (this.videoPlayer == null) {
            return;
        }
        if (i != 4 && i != 1) {
            try {
                this.baseActivity.getWindow().addFlags(128);
            } catch (Exception e) {
                FileLog.e(e);
            }
        } else {
            try {
                this.baseActivity.getWindow().clearFlags(128);
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
        if (i == 3) {
            this.playerWasReady = true;
            MessageObject messageObject3 = this.playingMessageObject;
            if (messageObject3 != null && (messageObject3.isVideo() || this.playingMessageObject.isRoundVideo())) {
                AndroidUtilities.cancelRunOnUIThread(this.setLoadingRunnable);
                FileLoader.getInstance(messageObject.currentAccount).removeLoadingVideo(this.playingMessageObject.getDocument(), true, false);
            }
            this.currentAspectRatioFrameLayoutReady = true;
            return;
        }
        if (i == 2) {
            if (!z2 || (messageObject2 = this.playingMessageObject) == null) {
                return;
            }
            if (messageObject2.isVideo() || this.playingMessageObject.isRoundVideo()) {
                if (this.playerWasReady) {
                    this.setLoadingRunnable.run();
                    return;
                } else {
                    AndroidUtilities.runOnUIThread(this.setLoadingRunnable, 1000L);
                    return;
                }
            }
            return;
        }
        if (this.videoPlayer.isPlaying() && i == 4) {
            if (this.playingMessageObject.isVideo() && !z && (iArr == null || iArr[0] < 4)) {
                this.videoPlayer.seekTo(0L);
                if (iArr != null) {
                    iArr[0] = iArr[0] + 1;
                    return;
                }
                return;
            }
            cleanupPlayer(true, true, true, false);
        }
    }

    public void injectVideoPlayer(VideoPlayer videoPlayer, MessageObject messageObject) {
        if (videoPlayer == null || messageObject == null) {
            return;
        }
        FileLoader.getInstance(messageObject.currentAccount).setLoadingVideoForPlayer(messageObject.getDocument(), true);
        this.playerWasReady = false;
        clearPlaylist();
        this.videoPlayer = videoPlayer;
        this.playingMessageObject = messageObject;
        int i = this.playerNum + 1;
        this.playerNum = i;
        videoPlayer.setDelegate(new AnonymousClass7(i, messageObject, null, true));
        this.currentAspectRatioFrameLayoutReady = false;
        TextureView textureView = this.currentTextureView;
        if (textureView != null) {
            this.videoPlayer.setTextureView(textureView);
        }
        checkAudioFocus(messageObject);
        setPlayerVolume();
        this.isPaused = false;
        this.lastProgress = 0L;
        MessageObject messageObject2 = this.playingMessageObject;
        this.playingMessageObject = messageObject;
        if (!SharedConfig.enabledRaiseTo(true)) {
            startRaiseToEarSensors(this.raiseChat);
        }
        startProgressTimer(this.playingMessageObject);
        NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingDidStart, messageObject, messageObject2);
    }

    /* renamed from: org.telegram.messenger.MediaController$7, reason: invalid class name */
    class AnonymousClass7 implements VideoPlayer.VideoPlayerDelegate {
        final /* synthetic */ boolean val$destroyAtEnd;
        final /* synthetic */ MessageObject val$messageObject;
        final /* synthetic */ int[] val$playCount;
        final /* synthetic */ int val$tag;

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onRenderedFirstFrame(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        AnonymousClass7(int i, MessageObject messageObject, int[] iArr, boolean z) {
            this.val$tag = i;
            this.val$messageObject = messageObject;
            this.val$playCount = iArr;
            this.val$destroyAtEnd = z;
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onStateChanged(boolean z, int i) {
            if (this.val$tag != MediaController.this.playerNum) {
                return;
            }
            MediaController.this.updateVideoState(this.val$messageObject, this.val$playCount, this.val$destroyAtEnd, z, i);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onError(VideoPlayer videoPlayer, Exception exc) {
            FileLog.e(exc);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            MediaController.this.currentAspectRatioFrameLayoutRotation = i3;
            if (i3 != 90 && i3 != 270) {
                i2 = i;
                i = i2;
            }
            MediaController.this.currentAspectRatioFrameLayoutRatio = i == 0 ? MediaController.VOLUME_NORMAL : (i2 * f) / i;
            if (MediaController.this.currentAspectRatioFrameLayout != null) {
                MediaController.this.currentAspectRatioFrameLayout.setAspectRatio(MediaController.this.currentAspectRatioFrameLayoutRatio, MediaController.this.currentAspectRatioFrameLayoutRotation);
            }
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onRenderedFirstFrame() {
            if (MediaController.this.currentAspectRatioFrameLayout == null || MediaController.this.currentAspectRatioFrameLayout.isDrawingReady()) {
                return;
            }
            MediaController.this.isDrawingWasReady = true;
            MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
            MediaController.this.currentTextureViewContainer.setTag(1);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            if (MediaController.this.videoPlayer == null) {
                return false;
            }
            if (MediaController.this.pipSwitchingState == 2) {
                if (MediaController.this.currentAspectRatioFrameLayout != null) {
                    if (MediaController.this.isDrawingWasReady) {
                        MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
                    }
                    if (MediaController.this.currentAspectRatioFrameLayout.getParent() == null) {
                        MediaController.this.currentTextureViewContainer.addView(MediaController.this.currentAspectRatioFrameLayout);
                    }
                    if (MediaController.this.currentTextureView.getSurfaceTexture() != surfaceTexture) {
                        MediaController.this.currentTextureView.setSurfaceTexture(surfaceTexture);
                    }
                    MediaController.this.videoPlayer.setTextureView(MediaController.this.currentTextureView);
                }
                MediaController.this.pipSwitchingState = 0;
                return true;
            }
            if (MediaController.this.pipSwitchingState == 1) {
                if (MediaController.this.baseActivity != null) {
                    if (MediaController.this.pipRoundVideoView == null) {
                        try {
                            MediaController.this.pipRoundVideoView = new PipRoundVideoView();
                            MediaController.this.pipRoundVideoView.show(MediaController.this.baseActivity, new Runnable() { // from class: org.telegram.messenger.MediaController$7$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MediaController.AnonymousClass7.this.lambda$onSurfaceDestroyed$0();
                                }
                            });
                        } catch (Exception unused) {
                            MediaController.this.pipRoundVideoView = null;
                        }
                    }
                    if (MediaController.this.pipRoundVideoView != null) {
                        if (MediaController.this.pipRoundVideoView.getTextureView().getSurfaceTexture() != surfaceTexture) {
                            MediaController.this.pipRoundVideoView.getTextureView().setSurfaceTexture(surfaceTexture);
                        }
                        MediaController.this.videoPlayer.setTextureView(MediaController.this.pipRoundVideoView.getTextureView());
                    }
                }
                MediaController.this.pipSwitchingState = 0;
                return true;
            }
            if (!PhotoViewer.hasInstance() || !PhotoViewer.getInstance().isInjectingVideoPlayer()) {
                return false;
            }
            PhotoViewer.getInstance().injectVideoPlayerSurface(surfaceTexture);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfaceDestroyed$0() {
            MediaController.this.cleanupPlayer(true, true);
        }
    }

    public void playEmojiSound(final AccountInstance accountInstance, String str, final MessagesController.EmojiSound emojiSound, final boolean z) {
        if (emojiSound == null) {
            return;
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$playEmojiSound$19(emojiSound, accountInstance, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playEmojiSound$19(MessagesController.EmojiSound emojiSound, final AccountInstance accountInstance, boolean z) {
        final TLRPC$TL_document tLRPC$TL_document = new TLRPC$TL_document();
        tLRPC$TL_document.access_hash = emojiSound.accessHash;
        tLRPC$TL_document.id = emojiSound.id;
        tLRPC$TL_document.mime_type = "sound/ogg";
        tLRPC$TL_document.file_reference = emojiSound.fileReference;
        tLRPC$TL_document.dc_id = accountInstance.getConnectionsManager().getCurrentDatacenterId();
        final File pathToAttach = FileLoader.getInstance(accountInstance.getCurrentAccount()).getPathToAttach(tLRPC$TL_document, true);
        if (!pathToAttach.exists()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.lambda$playEmojiSound$18(AccountInstance.this, tLRPC$TL_document);
                }
            });
        } else {
            if (z) {
                return;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$playEmojiSound$17(pathToAttach);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playEmojiSound$17(File file) {
        try {
            int i = this.emojiSoundPlayerNum + 1;
            this.emojiSoundPlayerNum = i;
            VideoPlayer videoPlayer = this.emojiSoundPlayer;
            if (videoPlayer != null) {
                videoPlayer.releasePlayer(true);
            }
            VideoPlayer videoPlayer2 = new VideoPlayer(false);
            this.emojiSoundPlayer = videoPlayer2;
            videoPlayer2.setDelegate(new AnonymousClass8(i));
            this.emojiSoundPlayer.preparePlayer(Uri.fromFile(file), "other");
            this.emojiSoundPlayer.setStreamType(3);
            this.emojiSoundPlayer.play();
        } catch (Exception e) {
            FileLog.e(e);
            VideoPlayer videoPlayer3 = this.emojiSoundPlayer;
            if (videoPlayer3 != null) {
                videoPlayer3.releasePlayer(true);
                this.emojiSoundPlayer = null;
            }
        }
    }

    /* renamed from: org.telegram.messenger.MediaController$8, reason: invalid class name */
    class AnonymousClass8 implements VideoPlayer.VideoPlayerDelegate {
        final /* synthetic */ int val$tag;

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onError(VideoPlayer videoPlayer, Exception exc) {
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onRenderedFirstFrame() {
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onRenderedFirstFrame(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
        }

        AnonymousClass8(int i) {
            this.val$tag = i;
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onStateChanged(boolean z, final int i) {
            final int i2 = this.val$tag;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$8$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.AnonymousClass8.this.lambda$onStateChanged$0(i2, i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onStateChanged$0(int i, int i2) {
            if (i == MediaController.this.emojiSoundPlayerNum && i2 == 4 && MediaController.this.emojiSoundPlayer != null) {
                try {
                    MediaController.this.emojiSoundPlayer.releasePlayer(true);
                    MediaController.this.emojiSoundPlayer = null;
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$playEmojiSound$18(AccountInstance accountInstance, TLRPC$Document tLRPC$Document) {
        accountInstance.getFileLoader().loadFile(tLRPC$Document, null, 1, 1);
    }

    public void checkVolumeBarUI() {
        if (this.isSilent) {
            return;
        }
        try {
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - volumeBarLastTimeShown) < 5000) {
                return;
            }
            AudioManager audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
            int i = this.useFrontSpeaker ? 0 : 3;
            int streamVolume = audioManager.getStreamVolume(i);
            if (streamVolume == 0) {
                audioManager.adjustStreamVolume(i, streamVolume, 1);
                volumeBarLastTimeShown = currentTimeMillis;
            }
        } catch (Exception unused) {
        }
    }

    private void setBluetoothScoOn(boolean z) {
        AudioManager audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
        if (!(audioManager.isBluetoothScoAvailableOffCall() && SharedConfig.recordViaSco) && z) {
            return;
        }
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            try {
                if (defaultAdapter.getProfileConnectionState(1) != 2) {
                }
                if (!z && !audioManager.isBluetoothScoOn()) {
                    audioManager.startBluetoothSco();
                    return;
                } else if (z && audioManager.isBluetoothScoOn()) {
                    audioManager.stopBluetoothSco();
                    return;
                }
            } catch (SecurityException unused) {
                return;
            } catch (Throwable th) {
                FileLog.e(th);
                return;
            }
        }
        if (z) {
            return;
        }
        if (!z) {
        }
        if (z) {
        }
    }

    public boolean playMessage(MessageObject messageObject) {
        return playMessage(messageObject, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:217:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x054e  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x04a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0463 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean playMessage(final org.telegram.messenger.MessageObject r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 1758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.playMessage(org.telegram.messenger.MessageObject, boolean):boolean");
    }

    /* renamed from: org.telegram.messenger.MediaController$9, reason: invalid class name */
    class AnonymousClass9 implements VideoPlayer.VideoPlayerDelegate {
        final /* synthetic */ boolean val$destroyAtEnd;
        final /* synthetic */ MessageObject val$messageObject;
        final /* synthetic */ int[] val$playCount;
        final /* synthetic */ int val$tag;

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onRenderedFirstFrame(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* bridge */ /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }

        AnonymousClass9(int i, MessageObject messageObject, int[] iArr, boolean z) {
            this.val$tag = i;
            this.val$messageObject = messageObject;
            this.val$playCount = iArr;
            this.val$destroyAtEnd = z;
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onStateChanged(boolean z, int i) {
            if (this.val$tag != MediaController.this.playerNum) {
                return;
            }
            MediaController.this.updateVideoState(this.val$messageObject, this.val$playCount, this.val$destroyAtEnd, z, i);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onError(VideoPlayer videoPlayer, Exception exc) {
            FileLog.e(exc);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            MediaController.this.currentAspectRatioFrameLayoutRotation = i3;
            if (i3 != 90 && i3 != 270) {
                i2 = i;
                i = i2;
            }
            MediaController.this.currentAspectRatioFrameLayoutRatio = i == 0 ? MediaController.VOLUME_NORMAL : (i2 * f) / i;
            if (MediaController.this.currentAspectRatioFrameLayout != null) {
                MediaController.this.currentAspectRatioFrameLayout.setAspectRatio(MediaController.this.currentAspectRatioFrameLayoutRatio, MediaController.this.currentAspectRatioFrameLayoutRotation);
            }
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onRenderedFirstFrame() {
            if (MediaController.this.currentAspectRatioFrameLayout == null || MediaController.this.currentAspectRatioFrameLayout.isDrawingReady()) {
                return;
            }
            MediaController.this.isDrawingWasReady = true;
            MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
            MediaController.this.currentTextureViewContainer.setTag(1);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            if (MediaController.this.videoPlayer == null) {
                return false;
            }
            if (MediaController.this.pipSwitchingState == 2) {
                if (MediaController.this.currentAspectRatioFrameLayout != null) {
                    if (MediaController.this.isDrawingWasReady) {
                        MediaController.this.currentAspectRatioFrameLayout.setDrawingReady(true);
                    }
                    if (MediaController.this.currentAspectRatioFrameLayout.getParent() == null) {
                        MediaController.this.currentTextureViewContainer.addView(MediaController.this.currentAspectRatioFrameLayout);
                    }
                    if (MediaController.this.currentTextureView.getSurfaceTexture() != surfaceTexture) {
                        MediaController.this.currentTextureView.setSurfaceTexture(surfaceTexture);
                    }
                    MediaController.this.videoPlayer.setTextureView(MediaController.this.currentTextureView);
                }
                MediaController.this.pipSwitchingState = 0;
                return true;
            }
            if (MediaController.this.pipSwitchingState == 1) {
                if (MediaController.this.baseActivity != null) {
                    if (MediaController.this.pipRoundVideoView == null) {
                        try {
                            MediaController.this.pipRoundVideoView = new PipRoundVideoView();
                            MediaController.this.pipRoundVideoView.show(MediaController.this.baseActivity, new Runnable() { // from class: org.telegram.messenger.MediaController$9$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MediaController.AnonymousClass9.this.lambda$onSurfaceDestroyed$0();
                                }
                            });
                        } catch (Exception unused) {
                            MediaController.this.pipRoundVideoView = null;
                        }
                    }
                    if (MediaController.this.pipRoundVideoView != null) {
                        if (MediaController.this.pipRoundVideoView.getTextureView().getSurfaceTexture() != surfaceTexture) {
                            MediaController.this.pipRoundVideoView.getTextureView().setSurfaceTexture(surfaceTexture);
                        }
                        MediaController.this.videoPlayer.setTextureView(MediaController.this.pipRoundVideoView.getTextureView());
                    }
                }
                MediaController.this.pipSwitchingState = 0;
                return true;
            }
            if (!PhotoViewer.hasInstance() || !PhotoViewer.getInstance().isInjectingVideoPlayer()) {
                return false;
            }
            PhotoViewer.getInstance().injectVideoPlayerSurface(surfaceTexture);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfaceDestroyed$0() {
            MediaController.this.cleanupPlayer(true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$playMessage$20() {
        cleanupPlayer(true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$playMessage$21(MessageObject messageObject, File file) {
        NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.fileLoaded, FileLoader.getAttachFileName(messageObject.getDocument()), file);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$playMessage$22(MessageObject messageObject, File file) {
        NotificationCenter.getInstance(messageObject.currentAccount).postNotificationName(NotificationCenter.fileLoaded, FileLoader.getAttachFileName(messageObject.getDocument()), file);
    }

    public static boolean ignoreAccelerometerGestures() {
        return Build.MANUFACTURER.equalsIgnoreCase("samsung");
    }

    public void updateSilent(boolean z) {
        this.isSilent = z;
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.setLooping(z);
        }
        setPlayerVolume();
        checkVolumeBarUI();
        MessageObject messageObject = this.playingMessageObject;
        if (messageObject != null) {
            NotificationCenter notificationCenter = NotificationCenter.getInstance(messageObject.currentAccount);
            int i = NotificationCenter.messagePlayingPlayStateChanged;
            Object[] objArr = new Object[1];
            MessageObject messageObject2 = this.playingMessageObject;
            objArr[0] = Integer.valueOf(messageObject2 != null ? messageObject2.getId() : 0);
            notificationCenter.postNotificationName(i, objArr);
        }
    }

    public AudioInfo getAudioInfo() {
        return this.audioInfo;
    }

    public void setPlaybackOrderType(int i) {
        boolean z = SharedConfig.shuffleMusic;
        SharedConfig.setPlaybackOrderType(i);
        boolean z2 = SharedConfig.shuffleMusic;
        if (z != z2) {
            if (z2) {
                buildShuffledPlayList();
                return;
            }
            MessageObject messageObject = this.playingMessageObject;
            if (messageObject != null) {
                int indexOf = this.playlist.indexOf(messageObject);
                this.currentPlaylistNum = indexOf;
                if (indexOf == -1) {
                    clearPlaylist();
                    cleanupPlayer(true, true);
                }
            }
        }
    }

    public boolean isStreamingCurrentAudio() {
        return this.isStreamingCurrentAudio;
    }

    public boolean isCurrentPlayer(VideoPlayer videoPlayer) {
        return this.videoPlayer == videoPlayer || this.audioPlayer == videoPlayer;
    }

    /* renamed from: pauseMessage, reason: merged with bridge method [inline-methods] */
    public boolean lambda$startAudioAgain$7(MessageObject messageObject) {
        if ((this.audioPlayer != null || this.videoPlayer != null) && messageObject != null && this.playingMessageObject != null && isSamePlayingMessage(messageObject)) {
            stopProgressTimer();
            try {
                if (this.audioPlayer != null) {
                    if (!this.playingMessageObject.isVoice() && this.playingMessageObject.getDuration() * (VOLUME_NORMAL - this.playingMessageObject.audioProgress) > VOLUME_NORMAL && LaunchActivity.isResumed) {
                        ValueAnimator valueAnimator = this.audioVolumeAnimator;
                        if (valueAnimator != null) {
                            valueAnimator.removeAllUpdateListeners();
                            this.audioVolumeAnimator.cancel();
                        }
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(VOLUME_NORMAL, 0.0f);
                        this.audioVolumeAnimator = ofFloat;
                        ofFloat.addUpdateListener(this.audioVolumeUpdateListener);
                        this.audioVolumeAnimator.setDuration(300L);
                        this.audioVolumeAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.messenger.MediaController.12
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                if (MediaController.this.audioPlayer != null) {
                                    MediaController.this.audioPlayer.pause();
                                }
                            }
                        });
                        this.audioVolumeAnimator.start();
                    } else {
                        this.audioPlayer.pause();
                    }
                } else {
                    VideoPlayer videoPlayer = this.videoPlayer;
                    if (videoPlayer != null) {
                        videoPlayer.pause();
                    }
                }
                this.isPaused = true;
                NotificationCenter.getInstance(this.playingMessageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
                return true;
            } catch (Exception e) {
                FileLog.e(e);
                this.isPaused = false;
            }
        }
        return false;
    }

    private boolean resumeAudio(MessageObject messageObject) {
        if ((this.audioPlayer != null || this.videoPlayer != null) && messageObject != null && this.playingMessageObject != null && isSamePlayingMessage(messageObject)) {
            try {
                startProgressTimer(this.playingMessageObject);
                ValueAnimator valueAnimator = this.audioVolumeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.removeAllListeners();
                    this.audioVolumeAnimator.cancel();
                }
                if (!messageObject.isVoice() && !messageObject.isRoundVideo()) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(this.audioVolume, VOLUME_NORMAL);
                    this.audioVolumeAnimator = ofFloat;
                    ofFloat.addUpdateListener(this.audioVolumeUpdateListener);
                    this.audioVolumeAnimator.setDuration(300L);
                    this.audioVolumeAnimator.start();
                } else {
                    this.audioVolume = VOLUME_NORMAL;
                    setPlayerVolume();
                }
                VideoPlayer videoPlayer = this.audioPlayer;
                if (videoPlayer != null) {
                    videoPlayer.play();
                } else {
                    VideoPlayer videoPlayer2 = this.videoPlayer;
                    if (videoPlayer2 != null) {
                        videoPlayer2.play();
                    }
                }
                checkAudioFocus(messageObject);
                this.isPaused = false;
                NotificationCenter.getInstance(this.playingMessageObject.currentAccount).postNotificationName(NotificationCenter.messagePlayingPlayStateChanged, Integer.valueOf(this.playingMessageObject.getId()));
                return true;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        return false;
    }

    public boolean isVideoDrawingReady() {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.currentAspectRatioFrameLayout;
        return aspectRatioFrameLayout != null && aspectRatioFrameLayout.isDrawingReady();
    }

    public ArrayList<MessageObject> getPlaylist() {
        return this.playlist;
    }

    public boolean isPlayingMessage(MessageObject messageObject) {
        MessageObject messageObject2;
        if ((this.audioPlayer != null || this.videoPlayer != null) && messageObject != null && (messageObject2 = this.playingMessageObject) != null) {
            long j = messageObject2.eventId;
            if (j != 0 && j == messageObject.eventId) {
                return !this.downloadingCurrentMessage;
            }
            if (isSamePlayingMessage(messageObject)) {
                return !this.downloadingCurrentMessage;
            }
        }
        return false;
    }

    public boolean isPlayingMessageAndReadyToDraw(MessageObject messageObject) {
        return this.isDrawingWasReady && isPlayingMessage(messageObject);
    }

    public boolean isMessagePaused() {
        return this.isPaused || this.downloadingCurrentMessage;
    }

    public boolean isDownloadingCurrentMessage() {
        return this.downloadingCurrentMessage;
    }

    public void setReplyingMessage(MessageObject messageObject, MessageObject messageObject2) {
        this.recordReplyingMsg = messageObject;
        this.recordReplyingTopMsg = messageObject2;
    }

    public void requestAudioFocus(boolean z) {
        if (z) {
            if (!this.hasRecordAudioFocus && SharedConfig.pauseMusicOnRecord && NotificationsController.audioManager.requestAudioFocus(this.audioRecordFocusChangedListener, 3, 2) == 1) {
                this.hasRecordAudioFocus = true;
                return;
            }
            return;
        }
        if (this.hasRecordAudioFocus) {
            NotificationsController.audioManager.abandonAudioFocus(this.audioRecordFocusChangedListener);
            this.hasRecordAudioFocus = false;
        }
    }

    public void startRecording(final int i, final long j, final MessageObject messageObject, final MessageObject messageObject2, final int i2, boolean z) {
        boolean z2;
        boolean z3;
        MessageObject messageObject3 = this.playingMessageObject;
        if (messageObject3 == null || !isPlayingMessage(messageObject3) || isMessagePaused()) {
            z2 = z;
            z3 = false;
        } else {
            z2 = z;
            z3 = true;
        }
        this.manualRecording = z2;
        requestAudioFocus(true);
        try {
            this.feedbackView.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        DispatchQueue dispatchQueue = this.recordQueue;
        Runnable runnable = new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$startRecording$27(i, i2, j, messageObject, messageObject2);
            }
        };
        this.recordStartRunnable = runnable;
        dispatchQueue.postRunnable(runnable, z3 ? 500L : 50L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$27(final int i, final int i2, long j, MessageObject messageObject, MessageObject messageObject2) {
        if (this.audioRecorder != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$startRecording$23(i, i2);
                }
            });
            return;
        }
        setBluetoothScoOn(true);
        this.sendAfterDone = 0;
        TLRPC$TL_document tLRPC$TL_document = new TLRPC$TL_document();
        this.recordingAudio = tLRPC$TL_document;
        this.recordingGuid = i2;
        tLRPC$TL_document.file_reference = new byte[0];
        tLRPC$TL_document.dc_id = LinearLayoutManager.INVALID_OFFSET;
        tLRPC$TL_document.id = SharedConfig.getLastLocalId();
        this.recordingAudio.user_id = UserConfig.getInstance(i).getClientUserId();
        TLRPC$TL_document tLRPC$TL_document2 = this.recordingAudio;
        tLRPC$TL_document2.mime_type = "audio/ogg";
        tLRPC$TL_document2.file_reference = new byte[0];
        SharedConfig.saveConfig();
        this.recordingAudioFile = new File(FileLoader.getDirectory(1), System.currentTimeMillis() + "_" + FileLoader.getAttachFileName(this.recordingAudio)) { // from class: org.telegram.messenger.MediaController.13
            @Override // java.io.File
            public boolean delete() {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("delete voice file");
                }
                return super.delete();
            }
        };
        FileLoader.getDirectory(4).mkdirs();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("start recording internal " + this.recordingAudioFile.getPath() + " " + this.recordingAudioFile.exists());
        }
        AutoDeleteMediaTask.lockFile(this.recordingAudioFile);
        try {
            if (startRecord(this.recordingAudioFile.getPath(), this.sampleRate) == 0) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda21
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.this.lambda$startRecording$24(i, i2);
                    }
                });
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("cant init encoder");
                    return;
                }
                return;
            }
            this.audioRecorder = new AudioRecord(0, this.sampleRate, 16, 2, this.recordBufferSize);
            this.recordStartTime = System.currentTimeMillis();
            this.recordTimeCount = 0L;
            this.writedFrame = 0;
            this.samplesCount = 0L;
            this.recordDialogId = j;
            this.recordingCurrentAccount = i;
            this.recordReplyingMsg = messageObject;
            this.recordReplyingTopMsg = messageObject2;
            this.fileBuffer.rewind();
            this.audioRecorder.startRecording();
            this.recordQueue.postRunnable(this.recordRunnable);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$startRecording$26(i, i2);
                }
            });
        } catch (Exception e) {
            FileLog.e(e);
            this.recordingAudio = null;
            stopRecord();
            AutoDeleteMediaTask.unlockFile(this.recordingAudioFile);
            this.recordingAudioFile.delete();
            this.recordingAudioFile = null;
            try {
                this.audioRecorder.release();
                this.audioRecorder = null;
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            setBluetoothScoOn(false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$startRecording$25(i, i2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$23(int i, int i2) {
        this.recordStartRunnable = null;
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.recordStartError, Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$24(int i, int i2) {
        this.recordStartRunnable = null;
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.recordStartError, Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$25(int i, int i2) {
        this.recordStartRunnable = null;
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.recordStartError, Integer.valueOf(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$26(int i, int i2) {
        this.recordStartRunnable = null;
        NotificationCenter.getInstance(i).postNotificationName(NotificationCenter.recordStarted, Integer.valueOf(i2), Boolean.TRUE);
    }

    public void generateWaveform(final MessageObject messageObject) {
        final String str = messageObject.getId() + "_" + messageObject.getDialogId();
        final String absolutePath = FileLoader.getInstance(messageObject.currentAccount).getPathToMessage(messageObject.messageOwner).getAbsolutePath();
        if (this.generatingWaveform.containsKey(str)) {
            return;
        }
        this.generatingWaveform.put(str, messageObject);
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$generateWaveform$29(absolutePath, str, messageObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateWaveform$29(String str, final String str2, final MessageObject messageObject) {
        try {
            final byte[] waveform = getWaveform(str);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$generateWaveform$28(str2, waveform, messageObject);
                }
            });
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$generateWaveform$28(String str, byte[] bArr, MessageObject messageObject) {
        MessageObject remove = this.generatingWaveform.remove(str);
        if (remove == null || bArr == null || remove.getDocument() == null) {
            return;
        }
        int i = 0;
        while (true) {
            if (i >= remove.getDocument().attributes.size()) {
                break;
            }
            TLRPC$DocumentAttribute tLRPC$DocumentAttribute = remove.getDocument().attributes.get(i);
            if (tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                tLRPC$DocumentAttribute.waveform = bArr;
                tLRPC$DocumentAttribute.flags |= 4;
                break;
            }
            i++;
        }
        TLRPC$TL_messages_messages tLRPC$TL_messages_messages = new TLRPC$TL_messages_messages();
        tLRPC$TL_messages_messages.messages.add(remove.messageOwner);
        MessagesStorage.getInstance(remove.currentAccount).putMessages((TLRPC$messages_Messages) tLRPC$TL_messages_messages, remove.getDialogId(), -1, 0, false, messageObject.scheduled, 0);
        ArrayList arrayList = new ArrayList();
        arrayList.add(remove);
        NotificationCenter.getInstance(remove.currentAccount).postNotificationName(NotificationCenter.replaceMessagesObjects, Long.valueOf(remove.getDialogId()), arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRecordingInternal(final int i, final boolean z, final int i2) {
        if (i != 0) {
            final TLRPC$TL_document tLRPC$TL_document = this.recordingAudio;
            final File file = this.recordingAudioFile;
            if (BuildVars.LOGS_ENABLED) {
                FileLog.d("stop recording internal filename " + this.recordingAudioFile.getPath());
            }
            this.fileEncodingQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.this.lambda$stopRecordingInternal$31(file, tLRPC$TL_document, i, z, i2);
                }
            });
        } else {
            AutoDeleteMediaTask.unlockFile(this.recordingAudioFile);
            File file2 = this.recordingAudioFile;
            if (file2 != null) {
                file2.delete();
            }
            requestAudioFocus(false);
        }
        try {
            AudioRecord audioRecord = this.audioRecorder;
            if (audioRecord != null) {
                audioRecord.release();
                this.audioRecorder = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        this.recordingAudio = null;
        this.recordingAudioFile = null;
        this.manualRecording = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopRecordingInternal$31(final File file, final TLRPC$TL_document tLRPC$TL_document, final int i, final boolean z, final int i2) {
        stopRecord();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("stop recording internal in queue " + file.exists() + " " + file.length());
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$stopRecordingInternal$30(file, tLRPC$TL_document, i, z, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopRecordingInternal$30(File file, TLRPC$TL_document tLRPC$TL_document, int i, boolean z, int i2) {
        boolean z2;
        char c;
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d("stop recording internal " + file.exists() + " " + file.length() + "  recordTimeCount " + this.recordTimeCount + " writedFrames" + this.writedFrame);
        }
        if (!file.exists() && BuildVars.DEBUG_VERSION) {
            FileLog.e(new RuntimeException("file not found :( recordTimeCount " + this.recordTimeCount + " writedFrames" + this.writedFrame));
        }
        tLRPC$TL_document.date = ConnectionsManager.getInstance(this.recordingCurrentAccount).getCurrentTime();
        tLRPC$TL_document.size = (int) file.length();
        TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
        tLRPC$TL_documentAttributeAudio.voice = true;
        short[] sArr = this.recordSamples;
        byte[] waveform2 = getWaveform2(sArr, sArr.length);
        tLRPC$TL_documentAttributeAudio.waveform = waveform2;
        if (waveform2 != null) {
            tLRPC$TL_documentAttributeAudio.flags |= 4;
        }
        long j = this.recordTimeCount;
        tLRPC$TL_documentAttributeAudio.duration = (int) (j / 1000);
        tLRPC$TL_document.attributes.add(tLRPC$TL_documentAttributeAudio);
        if (j > 700) {
            if (i == 1) {
                c = 1;
                SendMessagesHelper.getInstance(this.recordingCurrentAccount).sendMessage(tLRPC$TL_document, null, file.getAbsolutePath(), this.recordDialogId, this.recordReplyingMsg, this.recordReplyingTopMsg, null, null, null, null, z, i2, 0, null, null, false);
            } else {
                c = 1;
            }
            NotificationCenter notificationCenter = NotificationCenter.getInstance(this.recordingCurrentAccount);
            int i3 = NotificationCenter.audioDidSent;
            Object[] objArr = new Object[3];
            z2 = false;
            objArr[0] = Integer.valueOf(this.recordingGuid);
            objArr[c] = i == 2 ? tLRPC$TL_document : null;
            objArr[2] = i == 2 ? file.getAbsolutePath() : null;
            notificationCenter.postNotificationName(i3, objArr);
        } else {
            z2 = false;
            NotificationCenter.getInstance(this.recordingCurrentAccount).postNotificationName(NotificationCenter.audioRecordTooShort, Integer.valueOf(this.recordingGuid), Boolean.FALSE, Integer.valueOf((int) j));
            AutoDeleteMediaTask.unlockFile(file);
            file.delete();
        }
        requestAudioFocus(z2);
    }

    public void stopRecording(final int i, final boolean z, final int i2) {
        Runnable runnable = this.recordStartRunnable;
        if (runnable != null) {
            this.recordQueue.cancelRunnable(runnable);
            this.recordStartRunnable = null;
        }
        this.recordQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$stopRecording$33(i, z, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopRecording$33(final int i, boolean z, int i2) {
        if (this.sendAfterDone == 3) {
            this.sendAfterDone = 0;
            stopRecordingInternal(i, z, i2);
            return;
        }
        AudioRecord audioRecord = this.audioRecorder;
        if (audioRecord == null) {
            return;
        }
        try {
            this.sendAfterDone = i;
            this.sendAfterDoneNotify = z;
            this.sendAfterDoneScheduleDate = i2;
            audioRecord.stop();
            setBluetoothScoOn(false);
        } catch (Exception e) {
            FileLog.e(e);
            if (this.recordingAudioFile != null) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.e("delete voice file");
                }
                this.recordingAudioFile.delete();
            }
        }
        if (i == 0) {
            stopRecordingInternal(0, false, 0);
        }
        try {
            this.feedbackView.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$stopRecording$32(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopRecording$32(int i) {
        NotificationCenter notificationCenter = NotificationCenter.getInstance(this.recordingCurrentAccount);
        int i2 = NotificationCenter.recordStopped;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(this.recordingGuid);
        objArr[1] = Integer.valueOf(i == 2 ? 1 : 0);
        notificationCenter.postNotificationName(i2, objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MediaLoader implements NotificationCenter.NotificationCenterDelegate {
        private boolean cancelled;
        private int copiedFiles;
        private AccountInstance currentAccount;
        private boolean finished;
        private float finishedProgress;
        private boolean isMusic;
        private HashMap<String, MessageObject> loadingMessageObjects = new HashMap<>();
        private ArrayList<MessageObject> messageObjects;
        private MessagesStorage.IntCallback onFinishRunnable;
        private AlertDialog progressDialog;
        private CountDownLatch waitingForFile;

        public MediaLoader(Context context, AccountInstance accountInstance, ArrayList<MessageObject> arrayList, MessagesStorage.IntCallback intCallback) {
            this.currentAccount = accountInstance;
            this.messageObjects = arrayList;
            this.onFinishRunnable = intCallback;
            this.isMusic = arrayList.get(0).isMusic();
            this.currentAccount.getNotificationCenter().addObserver(this, NotificationCenter.fileLoaded);
            this.currentAccount.getNotificationCenter().addObserver(this, NotificationCenter.fileLoadProgressChanged);
            this.currentAccount.getNotificationCenter().addObserver(this, NotificationCenter.fileLoadFailed);
            AlertDialog alertDialog = new AlertDialog(context, 2);
            this.progressDialog = alertDialog;
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(true);
            this.progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnCancelListener
                public final void onCancel(DialogInterface dialogInterface) {
                    MediaController.MediaLoader.this.lambda$new$0(dialogInterface);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(DialogInterface dialogInterface) {
            this.cancelled = true;
        }

        public void start() {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.MediaLoader.this.lambda$start$1();
                }
            }, 250L);
            new Thread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.MediaLoader.this.lambda$start$2();
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$1() {
            if (this.finished) {
                return;
            }
            this.progressDialog.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$2() {
            File externalStoragePublicDirectory;
            try {
                if (Build.VERSION.SDK_INT >= 29) {
                    int size = this.messageObjects.size();
                    for (int i = 0; i < size; i++) {
                        MessageObject messageObject = this.messageObjects.get(i);
                        String str = messageObject.messageOwner.attachPath;
                        String documentName = messageObject.getDocumentName();
                        if (str != null && str.length() > 0 && !new File(str).exists()) {
                            str = null;
                        }
                        if (str == null || str.length() == 0) {
                            str = FileLoader.getInstance(this.currentAccount.getCurrentAccount()).getPathToMessage(messageObject.messageOwner).toString();
                        }
                        File file = new File(str);
                        if (!file.exists()) {
                            this.waitingForFile = new CountDownLatch(1);
                            addMessageToLoad(messageObject);
                            this.waitingForFile.await();
                        }
                        if (this.cancelled) {
                            break;
                        }
                        if (file.exists()) {
                            MediaController.saveFileInternal(this.isMusic ? 3 : 2, file, documentName);
                            this.copiedFiles++;
                        }
                    }
                } else {
                    if (this.isMusic) {
                        externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                    } else {
                        externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    }
                    externalStoragePublicDirectory.mkdir();
                    int size2 = this.messageObjects.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        MessageObject messageObject2 = this.messageObjects.get(i2);
                        String documentName2 = messageObject2.getDocumentName();
                        File file2 = new File(externalStoragePublicDirectory, documentName2);
                        if (file2.exists()) {
                            int lastIndexOf = documentName2.lastIndexOf(46);
                            int i3 = 0;
                            while (true) {
                                if (i3 >= 10) {
                                    break;
                                }
                                File file3 = new File(externalStoragePublicDirectory, lastIndexOf != -1 ? documentName2.substring(0, lastIndexOf) + "(" + (i3 + 1) + ")" + documentName2.substring(lastIndexOf) : documentName2 + "(" + (i3 + 1) + ")");
                                if (!file3.exists()) {
                                    file2 = file3;
                                    break;
                                } else {
                                    i3++;
                                    file2 = file3;
                                }
                            }
                        }
                        if (!file2.exists()) {
                            file2.createNewFile();
                        }
                        String str2 = messageObject2.messageOwner.attachPath;
                        if (str2 != null && str2.length() > 0 && !new File(str2).exists()) {
                            str2 = null;
                        }
                        if (str2 == null || str2.length() == 0) {
                            str2 = FileLoader.getInstance(this.currentAccount.getCurrentAccount()).getPathToMessage(messageObject2.messageOwner).toString();
                        }
                        File file4 = new File(str2);
                        if (!file4.exists()) {
                            this.waitingForFile = new CountDownLatch(1);
                            addMessageToLoad(messageObject2);
                            this.waitingForFile.await();
                        }
                        if (file4.exists()) {
                            copyFile(file4, file2, messageObject2.getMimeType());
                            this.copiedFiles++;
                        }
                    }
                }
                checkIfFinished();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        private void checkIfFinished() {
            if (this.loadingMessageObjects.isEmpty()) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.MediaLoader.this.lambda$checkIfFinished$4();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkIfFinished$4() {
            try {
                if (this.progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                } else {
                    this.finished = true;
                }
                if (this.onFinishRunnable != null) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaController.MediaLoader.this.lambda$checkIfFinished$3();
                        }
                    });
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.currentAccount.getNotificationCenter().removeObserver(this, NotificationCenter.fileLoaded);
            this.currentAccount.getNotificationCenter().removeObserver(this, NotificationCenter.fileLoadProgressChanged);
            this.currentAccount.getNotificationCenter().removeObserver(this, NotificationCenter.fileLoadFailed);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkIfFinished$3() {
            this.onFinishRunnable.run(this.copiedFiles);
        }

        private void addMessageToLoad(final MessageObject messageObject) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.MediaLoader.this.lambda$addMessageToLoad$5(messageObject);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addMessageToLoad$5(MessageObject messageObject) {
            TLRPC$Document document = messageObject.getDocument();
            if (document == null) {
                return;
            }
            this.loadingMessageObjects.put(FileLoader.getAttachFileName(document), messageObject);
            this.currentAccount.getFileLoader().loadFile(document, messageObject, 0, 0);
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x017c A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:54:? A[Catch: all -> 0x0186, SYNTHETIC, TRY_LEAVE, TryCatch #2 {all -> 0x0186, blocks: (B:53:0x0185, B:52:0x0182, B:93:0x014b, B:111:0x0157, B:47:0x017c), top: B:7:0x0015, inners: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean copyFile(java.io.File r32, java.io.File r33, java.lang.String r34) {
            /*
                Method dump skipped, instructions count: 421
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.MediaLoader.copyFile(java.io.File, java.io.File, java.lang.String):boolean");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copyFile$6() {
            try {
                this.progressDialog.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copyFile$7(int i) {
            try {
                this.progressDialog.setProgress(i);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$copyFile$8(int i) {
            try {
                this.progressDialog.setProgress(i);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (i == NotificationCenter.fileLoaded || i == NotificationCenter.fileLoadFailed) {
                if (this.loadingMessageObjects.remove((String) objArr[0]) != null) {
                    this.waitingForFile.countDown();
                    return;
                }
                return;
            }
            if (i == NotificationCenter.fileLoadProgressChanged) {
                if (this.loadingMessageObjects.containsKey((String) objArr[0])) {
                    final int longValue = (int) (this.finishedProgress + (((((Long) objArr[1]).longValue() / ((Long) objArr[2]).longValue()) / this.messageObjects.size()) * 100.0f));
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$MediaLoader$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaController.MediaLoader.this.lambda$didReceivedNotification$9(longValue);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didReceivedNotification$9(int i) {
            try {
                this.progressDialog.setProgress(i);
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public static void saveFilesFromMessages(Context context, AccountInstance accountInstance, ArrayList<MessageObject> arrayList, MessagesStorage.IntCallback intCallback) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        new MediaLoader(context, accountInstance, arrayList, intCallback).start();
    }

    public static void saveFile(String str, Context context, int i, String str2, String str3) {
        saveFile(str, context, i, str2, str3, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveFile(java.lang.String r13, android.content.Context r14, final int r15, final java.lang.String r16, final java.lang.String r17, final java.lang.Runnable r18) {
        /*
            r0 = r13
            r1 = r14
            if (r0 == 0) goto L80
            if (r1 != 0) goto L8
            goto L80
        L8:
            boolean r2 = android.text.TextUtils.isEmpty(r13)
            r3 = 0
            if (r2 != 0) goto L27
            java.io.File r2 = new java.io.File
            r2.<init>(r13)
            boolean r0 = r2.exists()
            if (r0 == 0) goto L27
            android.net.Uri r0 = android.net.Uri.fromFile(r2)
            boolean r0 = org.telegram.messenger.AndroidUtilities.isInternalUri(r0)
            if (r0 == 0) goto L25
            goto L27
        L25:
            r6 = r2
            goto L28
        L27:
            r6 = r3
        L28:
            if (r6 != 0) goto L2b
            return
        L2b:
            r0 = 1
            boolean[] r9 = new boolean[r0]
            r2 = 0
            r9[r2] = r2
            boolean r4 = r6.exists()
            if (r4 == 0) goto L80
            boolean[] r12 = new boolean[r0]
            if (r15 == 0) goto L6a
            org.telegram.ui.ActionBar.AlertDialog r4 = new org.telegram.ui.ActionBar.AlertDialog     // Catch: java.lang.Exception -> L66
            r5 = 2
            r4.<init>(r14, r5)     // Catch: java.lang.Exception -> L66
            java.lang.String r1 = "Loading"
            int r5 = org.telegram.messenger.R.string.Loading     // Catch: java.lang.Exception -> L66
            java.lang.String r1 = org.telegram.messenger.LocaleController.getString(r1, r5)     // Catch: java.lang.Exception -> L66
            r4.setMessage(r1)     // Catch: java.lang.Exception -> L66
            r4.setCanceledOnTouchOutside(r2)     // Catch: java.lang.Exception -> L66
            r4.setCancelable(r0)     // Catch: java.lang.Exception -> L66
            org.telegram.messenger.MediaController$$ExternalSyntheticLambda1 r0 = new org.telegram.messenger.MediaController$$ExternalSyntheticLambda1     // Catch: java.lang.Exception -> L66
            r0.<init>()     // Catch: java.lang.Exception -> L66
            r4.setOnCancelListener(r0)     // Catch: java.lang.Exception -> L66
            org.telegram.messenger.MediaController$$ExternalSyntheticLambda40 r0 = new org.telegram.messenger.MediaController$$ExternalSyntheticLambda40     // Catch: java.lang.Exception -> L66
            r0.<init>()     // Catch: java.lang.Exception -> L66
            r1 = 250(0xfa, double:1.235E-321)
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0, r1)     // Catch: java.lang.Exception -> L66
            r8 = r4
            goto L6b
        L66:
            r0 = move-exception
            org.telegram.messenger.FileLog.e(r0)
        L6a:
            r8 = r3
        L6b:
            java.lang.Thread r0 = new java.lang.Thread
            org.telegram.messenger.MediaController$$ExternalSyntheticLambda5 r1 = new org.telegram.messenger.MediaController$$ExternalSyntheticLambda5
            r4 = r1
            r5 = r15
            r7 = r16
            r10 = r17
            r11 = r18
            r4.<init>()
            r0.<init>(r1)
            r0.start()
        L80:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.saveFile(java.lang.String, android.content.Context, int, java.lang.String, java.lang.String, java.lang.Runnable):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveFile$34(boolean[] zArr, DialogInterface dialogInterface) {
        zArr[0] = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveFile$35(boolean[] zArr, AlertDialog alertDialog) {
        if (zArr[0]) {
            return;
        }
        alertDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01de A[Catch: Exception -> 0x021b, TryCatch #11 {Exception -> 0x021b, blocks: (B:3:0x0008, B:5:0x000e, B:8:0x0217, B:17:0x001c, B:18:0x00d7, B:20:0x00dd, B:21:0x00e0, B:107:0x01da, B:109:0x01de, B:113:0x01e9, B:114:0x020c, B:131:0x01d5, B:135:0x003b, B:137:0x005a, B:138:0x0067, B:140:0x007a, B:146:0x008c, B:147:0x00c6, B:149:0x00d3, B:152:0x00af, B:154:0x0061), top: B:2:0x0008 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01b6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[Catch: all -> 0x01c0, SYNTHETIC, TRY_LEAVE, TryCatch #0 {all -> 0x01c0, blocks: (B:53:0x01bf, B:52:0x01bc, B:104:0x0195, B:47:0x01b6), top: B:25:0x00ec, inners: #12 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0215 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:99:? A[Catch: all -> 0x01ae, SYNTHETIC, TRY_LEAVE, TryCatch #8 {all -> 0x01ae, blocks: (B:98:0x01ad, B:97:0x01aa, B:103:0x0192, B:92:0x01a4), top: B:28:0x00f0, inners: #4 }] */
    /* JADX WARN: Type inference failed for: r22v15 */
    /* JADX WARN: Type inference failed for: r22v21 */
    /* JADX WARN: Type inference failed for: r22v4, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v13, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v14, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$saveFile$39(int r20, java.io.File r21, java.lang.String r22, final org.telegram.ui.ActionBar.AlertDialog r23, boolean[] r24, java.lang.String r25, java.lang.Runnable r26, final boolean[] r27) {
        /*
            Method dump skipped, instructions count: 556
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.lambda$saveFile$39(int, java.io.File, java.lang.String, org.telegram.ui.ActionBar.AlertDialog, boolean[], java.lang.String, java.lang.Runnable, boolean[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveFile$36(AlertDialog alertDialog) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveFile$37(AlertDialog alertDialog, int i) {
        try {
            alertDialog.setProgress(i);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveFile$38(AlertDialog alertDialog, boolean[] zArr) {
        try {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            } else {
                zArr[0] = true;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean saveFileInternal(int i, File file, String str) {
        Uri contentUri;
        try {
            ContentValues contentValues = new ContentValues();
            String fileExtension = FileLoader.getFileExtension(file);
            String mimeTypeFromExtension = fileExtension != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension) : null;
            if ((i == 0 || i == 1) && mimeTypeFromExtension != null) {
                if (mimeTypeFromExtension.startsWith("image")) {
                    i = 0;
                }
                if (mimeTypeFromExtension.startsWith(MediaStreamTrack.VIDEO_TRACK_KIND)) {
                    i = 1;
                }
            }
            if (i == 0) {
                if (str == null) {
                    str = AndroidUtilities.generateFileName(0, fileExtension);
                }
                contentUri = MediaStore.Images.Media.getContentUri("external_primary");
                contentValues.put("relative_path", new File(Environment.DIRECTORY_PICTURES, "Telegram") + File.separator);
                contentValues.put("_display_name", str);
                contentValues.put("mime_type", mimeTypeFromExtension);
            } else if (i == 1) {
                if (str == null) {
                    str = AndroidUtilities.generateFileName(1, fileExtension);
                }
                contentValues.put("relative_path", new File(Environment.DIRECTORY_MOVIES, "Telegram") + File.separator);
                contentUri = MediaStore.Video.Media.getContentUri("external_primary");
                contentValues.put("_display_name", str);
            } else if (i == 2) {
                if (str == null) {
                    str = file.getName();
                }
                contentValues.put("relative_path", new File(Environment.DIRECTORY_DOWNLOADS, "Telegram") + File.separator);
                contentUri = MediaStore.Downloads.getContentUri("external_primary");
                contentValues.put("_display_name", str);
            } else {
                if (str == null) {
                    str = file.getName();
                }
                contentValues.put("relative_path", new File(Environment.DIRECTORY_MUSIC, "Telegram") + File.separator);
                contentUri = MediaStore.Audio.Media.getContentUri("external_primary");
                contentValues.put("_display_name", str);
            }
            contentValues.put("mime_type", mimeTypeFromExtension);
            Uri insert = ApplicationLoader.applicationContext.getContentResolver().insert(contentUri, contentValues);
            if (insert != null) {
                FileInputStream fileInputStream = new FileInputStream(file);
                AndroidUtilities.copyFile(fileInputStream, ApplicationLoader.applicationContext.getContentResolver().openOutputStream(insert));
                fileInputStream.close();
            }
            return true;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x00c5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:76:0x00bf -> B:58:0x00c2). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStickerExt(android.net.Uri r8) {
        /*
            Method dump skipped, instructions count: 206
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.getStickerExt(android.net.Uri):java.lang.String");
    }

    public static boolean isWebp(Uri uri) {
        InputStream inputStream = null;
        try {
            try {
                try {
                    inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
                    byte[] bArr = new byte[12];
                    if (inputStream.read(bArr, 0, 12) == 12) {
                        String lowerCase = new String(bArr).toLowerCase();
                        if (lowerCase.startsWith("riff")) {
                            if (lowerCase.endsWith("webp")) {
                                try {
                                    inputStream.close();
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                                return true;
                            }
                        }
                    }
                    inputStream.close();
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                    }
                    throw th;
                }
            } catch (Exception e3) {
                FileLog.e(e3);
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e4) {
            FileLog.e(e4);
        }
        return false;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x003d -> B:18:0x0040). Please report as a decompilation issue!!! */
    public static boolean isGif(Uri uri) {
        InputStream inputStream = null;
        try {
        } catch (Exception e) {
            FileLog.e(e);
        }
        try {
            try {
                inputStream = ApplicationLoader.applicationContext.getContentResolver().openInputStream(uri);
                byte[] bArr = new byte[3];
                if (inputStream.read(bArr, 0, 3) == 3) {
                    if (new String(bArr).equalsIgnoreCase("gif")) {
                        try {
                            inputStream.close();
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                        return true;
                    }
                }
                inputStream.close();
            } catch (Exception e3) {
                FileLog.e(e3);
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return false;
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e4) {
                    FileLog.e(e4);
                }
            }
            throw th;
        }
    }

    public static String getFileName(Uri uri) {
        if (uri == null) {
            return "";
        }
        try {
            if (uri.getScheme().equals("content")) {
                try {
                    Cursor query = ApplicationLoader.applicationContext.getContentResolver().query(uri, new String[]{"_display_name"}, null, null, null);
                    try {
                        r2 = query.moveToFirst() ? query.getString(query.getColumnIndex("_display_name")) : null;
                        query.close();
                    } finally {
                    }
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            if (r2 != null) {
                return r2;
            }
            String path = uri.getPath();
            int lastIndexOf = path.lastIndexOf(47);
            return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;
        } catch (Exception e2) {
            FileLog.e(e2);
            return "";
        }
    }

    public static String copyFileToCache(Uri uri, String str) {
        return copyFileToCache(uri, str, -1L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x0131, code lost:
    
        r3 = r5.getAbsolutePath();
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0135, code lost:
    
        r6.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0139, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x013a, code lost:
    
        org.telegram.messenger.FileLog.e(r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0187 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x017c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:134:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x01ad A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* JADX WARN: Type inference failed for: r7v17, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.io.FileOutputStream] */
    @android.annotation.SuppressLint({"DiscouragedPrivateApi"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String copyFileToCache(android.net.Uri r13, java.lang.String r14, long r15) {
        /*
            Method dump skipped, instructions count: 451
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.copyFileToCache(android.net.Uri, java.lang.String, long):java.lang.String");
    }

    public static void loadGalleryPhotosAlbums(final int i) {
        Thread thread = new Thread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.lambda$loadGalleryPhotosAlbums$41(i);
            }
        });
        thread.setPriority(1);
        thread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|(3:2|3|4)|(3:5|6|7)|8|(2:9|10)|(18:15|16|(2:136|137)|18|19|20|(9:22|(1:24)|25|(2:35|36)|27|(2:30|28)|31|32|33)|41|(1:43)(1:134)|44|(5:46|(1:48)(1:133)|49|(6:52|(2:129|130)(2:54|(1:126)(11:56|57|(7:59|60|61|62|(1:64)(1:121)|(1:66)|67)(1:125)|(3:69|70|71)(1:120)|73|74|(2:76|(1:112)(3:82|83|84))(1:113)|85|86|87|88))|127|128|88|50)|131)|25|(0)|27|(1:28)|31|32|33)|142|143|(1:145)(1:291)|146|147|(44:150|151|152|153|154|155|156|157|158|159|160|161|(1:163)(1:274)|164|165|166|167|168|169|170|171|172|173|174|175|(9:179|180|181|(2:250|251)(2:183|(1:247)(12:185|186|(3:188|189|190)(1:246)|(3:192|193|194)(1:242)|195|(2:197|(1:204)(1:203))|205|(2:207|(1:214)(1:213))|215|216|217|218))|248|249|218|176|177)|254|255|(0)|18|19|20|(0)|41|(0)(0)|44|(0)|25|(0)|27|(1:28)|31|32|33)(18:149|16|(0)|18|19|20|(0)|41|(0)(0)|44|(0)|25|(0)|27|(1:28)|31|32|33)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0291, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:289:0x0292, code lost:
    
        r38 = "_size";
        r33 = "height";
        r31 = "width";
        r29 = "_data";
        r28 = "bucket_display_name";
        r27 = "bucket_id";
        r26 = "_id";
        r30 = " DESC";
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x02b6, code lost:
    
        r10 = null;
        r34 = null;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x028a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x00ba A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x02c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x02d4 A[Catch: all -> 0x044e, TryCatch #20 {all -> 0x044e, blocks: (B:20:0x02ce, B:22:0x02d4, B:41:0x02e2, B:44:0x02fc, B:46:0x030e, B:49:0x032f, B:50:0x034b, B:52:0x0351, B:54:0x0360, B:57:0x038f, B:59:0x03ab), top: B:19:0x02ce }] */
    /* JADX WARN: Removed duplicated region for block: B:291:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x046b A[LOOP:0: B:28:0x0465->B:30:0x046b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0447 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x030e A[Catch: all -> 0x044e, TryCatch #20 {all -> 0x044e, blocks: (B:20:0x02ce, B:22:0x02d4, B:41:0x02e2, B:44:0x02fc, B:46:0x030e, B:49:0x032f, B:50:0x034b, B:52:0x0351, B:54:0x0360, B:57:0x038f, B:59:0x03ab), top: B:19:0x02ce }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0455 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$loadGalleryPhotosAlbums$41(int r54) {
        /*
            Method dump skipped, instructions count: 1183
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.lambda$loadGalleryPhotosAlbums$41(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$loadGalleryPhotosAlbums$40(PhotoEntry photoEntry, PhotoEntry photoEntry2) {
        long j = photoEntry.dateTaken;
        long j2 = photoEntry2.dateTaken;
        if (j < j2) {
            return 1;
        }
        return j > j2 ? -1 : 0;
    }

    private static void broadcastNewPhotos(final int i, final ArrayList<AlbumEntry> arrayList, final ArrayList<AlbumEntry> arrayList2, final Integer num, final AlbumEntry albumEntry, final AlbumEntry albumEntry2, final AlbumEntry albumEntry3, int i2) {
        Runnable runnable = broadcastPhotosRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.lambda$broadcastNewPhotos$42(i, arrayList, arrayList2, num, albumEntry, albumEntry2, albumEntry3);
            }
        };
        broadcastPhotosRunnable = runnable2;
        AndroidUtilities.runOnUIThread(runnable2, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$broadcastNewPhotos$42(int i, ArrayList arrayList, ArrayList arrayList2, Integer num, AlbumEntry albumEntry, AlbumEntry albumEntry2, AlbumEntry albumEntry3) {
        if (PhotoViewer.getInstance().isVisible()) {
            broadcastNewPhotos(i, arrayList, arrayList2, num, albumEntry, albumEntry2, albumEntry3, 1000);
            return;
        }
        allMediaAlbums = arrayList;
        allPhotoAlbums = arrayList2;
        broadcastPhotosRunnable = null;
        allPhotosAlbumEntry = albumEntry2;
        allMediaAlbumEntry = albumEntry;
        allVideosAlbumEntry = albumEntry3;
        NotificationCenter.getGlobalInstance().postNotificationName(NotificationCenter.albumsDidLoad, Integer.valueOf(i), arrayList, arrayList2, num);
    }

    public void scheduleVideoConvert(MessageObject messageObject) {
        scheduleVideoConvert(messageObject, false);
    }

    public boolean scheduleVideoConvert(MessageObject messageObject, boolean z) {
        if (messageObject == null || messageObject.videoEditedInfo == null) {
            return false;
        }
        if (z && !this.videoConvertQueue.isEmpty()) {
            return false;
        }
        if (z) {
            new File(messageObject.messageOwner.attachPath).delete();
        }
        this.videoConvertQueue.add(new VideoConvertMessage(messageObject, messageObject.videoEditedInfo));
        if (this.videoConvertQueue.size() == 1) {
            startVideoConvertFromQueue();
        }
        return true;
    }

    public void cancelVideoConvert(MessageObject messageObject) {
        if (messageObject == null || this.videoConvertQueue.isEmpty()) {
            return;
        }
        for (int i = 0; i < this.videoConvertQueue.size(); i++) {
            VideoConvertMessage videoConvertMessage = this.videoConvertQueue.get(i);
            MessageObject messageObject2 = videoConvertMessage.messageObject;
            if (messageObject2.equals(messageObject) && messageObject2.currentAccount == messageObject.currentAccount) {
                if (i == 0) {
                    synchronized (this.videoConvertSync) {
                        videoConvertMessage.videoEditedInfo.canceled = true;
                    }
                    return;
                }
                this.videoConvertQueue.remove(i);
                return;
            }
        }
    }

    private boolean startVideoConvertFromQueue() {
        int i = 0;
        if (this.videoConvertQueue.isEmpty()) {
            return false;
        }
        VideoConvertMessage videoConvertMessage = this.videoConvertQueue.get(0);
        MessageObject messageObject = videoConvertMessage.messageObject;
        VideoEditedInfo videoEditedInfo = videoConvertMessage.videoEditedInfo;
        synchronized (this.videoConvertSync) {
            if (videoEditedInfo != null) {
                videoEditedInfo.canceled = false;
            }
        }
        Intent intent = new Intent(ApplicationLoader.applicationContext, (Class<?>) VideoEncodingService.class);
        intent.putExtra("path", messageObject.messageOwner.attachPath);
        intent.putExtra("currentAccount", messageObject.currentAccount);
        if (messageObject.messageOwner.media.document != null) {
            while (true) {
                if (i >= messageObject.messageOwner.media.document.attributes.size()) {
                    break;
                }
                if (messageObject.messageOwner.media.document.attributes.get(i) instanceof TLRPC$TL_documentAttributeAnimated) {
                    intent.putExtra("gif", true);
                    break;
                }
                i++;
            }
        }
        if (messageObject.getId() != 0) {
            try {
                ApplicationLoader.applicationContext.startService(intent);
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }
        VideoConvertRunnable.runConversion(videoConvertMessage);
        return true;
    }

    @SuppressLint({"NewApi"})
    public static MediaCodecInfo selectCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        MediaCodecInfo mediaCodecInfo = null;
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (codecInfoAt.isEncoder()) {
                for (String str2 : codecInfoAt.getSupportedTypes()) {
                    if (str2.equalsIgnoreCase(str)) {
                        String name = codecInfoAt.getName();
                        if (name != null && (!name.equals("OMX.SEC.avc.enc") || name.equals("OMX.SEC.AVC.Encoder"))) {
                            return codecInfoAt;
                        }
                        mediaCodecInfo = codecInfoAt;
                    }
                }
            }
        }
        return mediaCodecInfo;
    }

    @SuppressLint({"NewApi"})
    public static int selectColorFormat(MediaCodecInfo mediaCodecInfo, String str) {
        int i;
        MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(str);
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = capabilitiesForType.colorFormats;
            if (i2 >= iArr.length) {
                return i3;
            }
            i = iArr[i2];
            if (isRecognizedFormat(i)) {
                if (!mediaCodecInfo.getName().equals("OMX.SEC.AVC.Encoder") || i != 19) {
                    break;
                }
                i3 = i;
            }
            i2++;
        }
        return i;
    }

    public static int findTrack(MediaExtractor mediaExtractor, boolean z) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            String string = mediaExtractor.getTrackFormat(i).getString("mime");
            if (z) {
                if (string.startsWith("audio/")) {
                    return i;
                }
            } else if (string.startsWith("video/")) {
                return i;
            }
        }
        return -5;
    }

    public static boolean isH264Video(String str) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        boolean z = false;
        try {
            mediaExtractor.setDataSource(str);
            int findTrack = findTrack(mediaExtractor, false);
            if (findTrack >= 0) {
                if (mediaExtractor.getTrackFormat(findTrack).getString("mime").equals(VIDEO_MIME_TYPE)) {
                    z = true;
                }
            }
            return z;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        } finally {
            mediaExtractor.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void didWriteData(final VideoConvertMessage videoConvertMessage, final File file, final boolean z, final long j, final long j2, final boolean z2, final float f) {
        VideoEditedInfo videoEditedInfo = videoConvertMessage.videoEditedInfo;
        final boolean z3 = videoEditedInfo.videoConvertFirstWrite;
        if (z3) {
            videoEditedInfo.videoConvertFirstWrite = false;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MediaController$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                MediaController.this.lambda$didWriteData$43(z2, z, videoConvertMessage, file, f, j, z3, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didWriteData$43(boolean z, boolean z2, VideoConvertMessage videoConvertMessage, File file, float f, long j, boolean z3, long j2) {
        if (z || z2) {
            synchronized (this.videoConvertSync) {
                videoConvertMessage.videoEditedInfo.canceled = false;
            }
            this.videoConvertQueue.remove(videoConvertMessage);
            startVideoConvertFromQueue();
        }
        if (z) {
            NotificationCenter.getInstance(videoConvertMessage.currentAccount).postNotificationName(NotificationCenter.filePreparingFailed, videoConvertMessage.messageObject, file.toString(), Float.valueOf(f), Long.valueOf(j));
            return;
        }
        if (z3) {
            NotificationCenter.getInstance(videoConvertMessage.currentAccount).postNotificationName(NotificationCenter.filePreparingStarted, videoConvertMessage.messageObject, file.toString(), Float.valueOf(f), Long.valueOf(j));
        }
        NotificationCenter notificationCenter = NotificationCenter.getInstance(videoConvertMessage.currentAccount);
        int i = NotificationCenter.fileNewChunkAvailable;
        Object[] objArr = new Object[6];
        objArr[0] = videoConvertMessage.messageObject;
        objArr[1] = file.toString();
        objArr[2] = Long.valueOf(j2);
        objArr[3] = Long.valueOf(z2 ? file.length() : 0L);
        objArr[4] = Float.valueOf(f);
        objArr[5] = Long.valueOf(j);
        notificationCenter.postNotificationName(i, objArr);
    }

    public void pauseByRewind() {
        VideoPlayer videoPlayer = this.audioPlayer;
        if (videoPlayer != null) {
            videoPlayer.pause();
        }
    }

    public void resumeByRewind() {
        VideoPlayer videoPlayer = this.audioPlayer;
        if (videoPlayer == null || this.playingMessageObject == null || this.isPaused) {
            return;
        }
        if (videoPlayer.isBuffering()) {
            MessageObject messageObject = this.playingMessageObject;
            cleanupPlayer(false, false);
            playMessage(messageObject);
            return;
        }
        this.audioPlayer.play();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VideoConvertRunnable implements Runnable {
        private VideoConvertMessage convertMessage;

        private VideoConvertRunnable(VideoConvertMessage videoConvertMessage) {
            this.convertMessage = videoConvertMessage;
        }

        @Override // java.lang.Runnable
        public void run() {
            MediaController.getInstance().convertVideo(this.convertMessage);
        }

        public static void runConversion(final VideoConvertMessage videoConvertMessage) {
            new Thread(new Runnable() { // from class: org.telegram.messenger.MediaController$VideoConvertRunnable$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaController.VideoConvertRunnable.lambda$runConversion$0(MediaController.VideoConvertMessage.this);
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$runConversion$0(VideoConvertMessage videoConvertMessage) {
            try {
                Thread thread = new Thread(new VideoConvertRunnable(videoConvertMessage), "VideoConvertRunnable");
                thread.start();
                thread.join();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01f8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean convertVideo(final org.telegram.messenger.MediaController.VideoConvertMessage r46) {
        /*
            Method dump skipped, instructions count: 530
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.convertVideo(org.telegram.messenger.MediaController$VideoConvertMessage):boolean");
    }

    public static int getVideoBitrate(String str) {
        int i;
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            i = Integer.parseInt(mediaMetadataRetriever.extractMetadata(20));
        } catch (Exception e) {
            FileLog.e(e);
            i = 0;
        }
        try {
            mediaMetadataRetriever.release();
        } catch (Throwable th) {
            FileLog.e(th);
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int makeVideoBitrate(int r5, int r6, int r7, int r8, int r9) {
        /*
            int r0 = java.lang.Math.min(r8, r9)
            r1 = 1065353216(0x3f800000, float:1.0)
            r2 = 1080(0x438, float:1.513E-42)
            if (r0 < r2) goto L10
            r0 = 6800000(0x67c280, float:9.52883E-39)
        Ld:
            r2 = 1065353216(0x3f800000, float:1.0)
            goto L36
        L10:
            int r0 = java.lang.Math.min(r8, r9)
            r2 = 720(0x2d0, float:1.009E-42)
            if (r0 < r2) goto L1c
            r0 = 2600000(0x27ac40, float:3.643376E-39)
            goto Ld
        L1c:
            int r0 = java.lang.Math.min(r8, r9)
            r1 = 480(0x1e0, float:6.73E-43)
            if (r0 < r1) goto L2d
            r0 = 1000000(0xf4240, float:1.401298E-39)
            r1 = 1061158912(0x3f400000, float:0.75)
            r2 = 1063675494(0x3f666666, float:0.9)
            goto L36
        L2d:
            r0 = 750000(0xb71b0, float:1.050974E-39)
            r1 = 1058642330(0x3f19999a, float:0.6)
            r2 = 1060320051(0x3f333333, float:0.7)
        L36:
            float r3 = (float) r7
            float r5 = (float) r5
            float r4 = (float) r8
            float r5 = r5 / r4
            float r6 = (float) r6
            float r4 = (float) r9
            float r6 = r6 / r4
            float r5 = java.lang.Math.min(r5, r6)
            float r3 = r3 / r5
            int r5 = (int) r3
            float r5 = (float) r5
            float r5 = r5 * r1
            int r5 = (int) r5
            int r6 = getVideoBitrateWithFactor(r2)
            float r6 = (float) r6
            r1 = 1231093760(0x49610000, float:921600.0)
            int r9 = r9 * r8
            float r8 = (float) r9
            float r1 = r1 / r8
            float r6 = r6 / r1
            int r6 = (int) r6
            if (r7 >= r6) goto L57
            return r5
        L57:
            if (r5 <= r0) goto L5a
            return r0
        L5a:
            int r5 = java.lang.Math.max(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MediaController.makeVideoBitrate(int, int, int, int, int):int");
    }

    public static int extractRealEncoderBitrate(int i, int i2, int i3) {
        String str = i + "" + i2 + "" + i3;
        ConcurrentHashMap<String, Integer> concurrentHashMap = cachedEncoderBitrates;
        Integer num = concurrentHashMap.get(str);
        if (num != null) {
            return num.intValue();
        }
        try {
            MediaCodec createEncoderByType = MediaCodec.createEncoderByType(VIDEO_MIME_TYPE);
            MediaFormat createVideoFormat = MediaFormat.createVideoFormat(VIDEO_MIME_TYPE, i, i2);
            createVideoFormat.setInteger("color-format", 2130708361);
            createVideoFormat.setInteger("max-bitrate", i3);
            createVideoFormat.setInteger("bitrate", i3);
            createVideoFormat.setInteger("frame-rate", 30);
            createVideoFormat.setInteger("i-frame-interval", 1);
            createEncoderByType.configure(createVideoFormat, (Surface) null, (MediaCrypto) null, 1);
            int integer = createEncoderByType.getOutputFormat().getInteger("bitrate");
            concurrentHashMap.put(str, Integer.valueOf(integer));
            createEncoderByType.release();
            return integer;
        } catch (Exception unused) {
            return i3;
        }
    }

    public static class PlaylistGlobalSearchParams {
        final long dialogId;
        public boolean endReached;
        final FiltersView.MediaFilterData filter;
        public int folderId;
        final long maxDate;
        final long minDate;
        public int nextSearchRate;
        final String query;
        public int totalCount;

        public PlaylistGlobalSearchParams(String str, long j, long j2, long j3, FiltersView.MediaFilterData mediaFilterData) {
            this.filter = mediaFilterData;
            this.query = str;
            this.dialogId = j;
            this.minDate = j2;
            this.maxDate = j3;
        }
    }

    public boolean currentPlaylistIsGlobalSearch() {
        return this.playlistGlobalSearchParams != null;
    }
}
