package com.hbisoft.hbrecorder;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import org.telegram.tgnet.ConnectionsManager;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public class ScreenRecordService extends Service {
    private static String fileName;
    private static String filePath;
    private int audioBitrate;
    private int audioSamplingRate;
    private int audioSourceAsInt;
    private boolean isAudioEnabled;
    private boolean isCustomSettingsEnabled;
    private boolean isVideoHD;
    private Intent mIntent;
    private MediaProjection mMediaProjection;
    private MediaRecorder mMediaRecorder;
    private int mResultCode;
    private Intent mResultData;
    private int mScreenDensity;
    private int mScreenHeight;
    private int mScreenWidth;
    private VirtualDisplay mVirtualDisplay;
    private String name;
    private int orientationHint;
    private int outputFormatAsInt;
    private String path;
    private int videoBitrate;
    private int videoEncoderAsInt;
    private int videoFrameRate;
    private long maxFileSize = 0;
    private boolean hasMaxFileBeenReached = false;
    private Uri returnedUri = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(final Intent intent, int i, int i2) {
        PendingIntent broadcast;
        Notification build;
        String action = intent.getAction();
        if (action != null && action.equals("pause")) {
            if (Build.VERSION.SDK_INT >= 24) {
                pauseRecording();
            }
        } else if (action != null && action.equals("resume")) {
            if (Build.VERSION.SDK_INT >= 24) {
                resumeRecording();
            }
        } else {
            this.hasMaxFileBeenReached = false;
            this.mIntent = intent;
            this.maxFileSize = intent.getLongExtra("maxFileSize", 0L);
            byte[] byteArrayExtra = intent.getByteArrayExtra("notificationSmallBitmap");
            int intExtra = intent.getIntExtra("notificationSmallVector", 0);
            String stringExtra = intent.getStringExtra("notificationTitle");
            String stringExtra2 = intent.getStringExtra("notificationDescription");
            String stringExtra3 = intent.getStringExtra("notificationButtonText");
            this.orientationHint = intent.getIntExtra("orientation", 400);
            this.mResultCode = intent.getIntExtra("code", -1);
            this.mResultData = (Intent) intent.getParcelableExtra("data");
            this.mScreenWidth = intent.getIntExtra("width", 0);
            this.mScreenHeight = intent.getIntExtra("height", 0);
            if (intent.getStringExtra("mUri") != null) {
                this.returnedUri = Uri.parse(intent.getStringExtra("mUri"));
            }
            if (this.mScreenHeight == 0 || this.mScreenWidth == 0) {
                HBRecorderCodecInfo hBRecorderCodecInfo = new HBRecorderCodecInfo();
                hBRecorderCodecInfo.setContext(this);
                this.mScreenHeight = hBRecorderCodecInfo.getMaxSupportedHeight();
                this.mScreenWidth = hBRecorderCodecInfo.getMaxSupportedWidth();
            }
            this.mScreenDensity = intent.getIntExtra("density", 1);
            this.isVideoHD = intent.getBooleanExtra("quality", true);
            this.isAudioEnabled = intent.getBooleanExtra(MediaStreamTrack.AUDIO_TRACK_KIND, true);
            this.path = intent.getStringExtra("path");
            this.name = intent.getStringExtra("fileName");
            String stringExtra4 = intent.getStringExtra("audioSource");
            String stringExtra5 = intent.getStringExtra("videoEncoder");
            this.videoFrameRate = intent.getIntExtra("videoFrameRate", 30);
            this.videoBitrate = intent.getIntExtra("videoBitrate", 40000000);
            if (stringExtra4 != null) {
                setAudioSourceAsInt(stringExtra4);
            }
            if (stringExtra5 != null) {
                setvideoEncoderAsInt(stringExtra5);
            }
            filePath = this.name;
            this.audioBitrate = intent.getIntExtra("audioBitrate", 128000);
            this.audioSamplingRate = intent.getIntExtra("audioSamplingRate", 44100);
            String stringExtra6 = intent.getStringExtra("outputFormat");
            if (stringExtra6 != null) {
                setOutputFormatAsInt(stringExtra6);
            }
            this.isCustomSettingsEnabled = intent.getBooleanExtra("enableCustomSettings", false);
            if (stringExtra3 == null) {
                stringExtra3 = "STOP RECORDING";
            }
            if (this.audioBitrate == 0) {
                this.audioBitrate = 128000;
            }
            if (this.audioSamplingRate == 0) {
                this.audioSamplingRate = 44100;
            }
            if (stringExtra == null || stringExtra.equals("")) {
                stringExtra = getString(R$string.stop_recording_notification_title);
            }
            if (stringExtra2 == null || stringExtra2.equals("")) {
                stringExtra2 = getString(R$string.stop_recording_notification_message);
            }
            int i3 = Build.VERSION.SDK_INT;
            if (i3 >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel("001", "RecordChannel", 0);
                notificationChannel.setLightColor(-16776961);
                notificationChannel.setLockscreenVisibility(0);
                NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                    Intent intent2 = new Intent(this, (Class<?>) NotificationReceiver.class);
                    if (i3 >= 31) {
                        broadcast = PendingIntent.getBroadcast(this, 0, intent2, ConnectionsManager.FileTypeFile);
                    } else {
                        broadcast = PendingIntent.getBroadcast(this, 0, intent2, 0);
                    }
                    Notification.Action build2 = new Notification.Action.Builder(Icon.createWithResource(this, R.drawable.presence_video_online), stringExtra3, broadcast).build();
                    if (byteArrayExtra != null) {
                        build = new Notification.Builder(getApplicationContext(), "001").setOngoing(true).setSmallIcon(Icon.createWithBitmap(BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length))).setContentTitle(stringExtra).setContentText(stringExtra2).addAction(build2).build();
                    } else if (intExtra != 0) {
                        build = new Notification.Builder(getApplicationContext(), "001").setOngoing(true).setSmallIcon(intExtra).setContentTitle(stringExtra).setContentText(stringExtra2).addAction(build2).build();
                    } else {
                        build = new Notification.Builder(getApplicationContext(), "001").setOngoing(true).setSmallIcon(R$drawable.icon).setContentTitle(stringExtra).setContentText(stringExtra2).addAction(build2).build();
                    }
                    startForeground(101, build);
                }
            } else {
                startForeground(101, new Notification());
            }
            if (this.returnedUri == null && this.path == null) {
                this.path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
            }
            try {
                initRecorder();
            } catch (Exception e) {
                ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("listener");
                Bundle bundle = new Bundle();
                bundle.putString("errorReason", Log.getStackTraceString(e));
                if (resultReceiver != null) {
                    resultReceiver.send(-1, bundle);
                }
            }
            try {
                initMediaProjection();
            } catch (Exception e2) {
                ResultReceiver resultReceiver2 = (ResultReceiver) intent.getParcelableExtra("listener");
                Bundle bundle2 = new Bundle();
                bundle2.putString("errorReason", Log.getStackTraceString(e2));
                if (resultReceiver2 != null) {
                    resultReceiver2.send(-1, bundle2);
                }
            }
            try {
                initVirtualDisplay();
            } catch (Exception e3) {
                ResultReceiver resultReceiver3 = (ResultReceiver) intent.getParcelableExtra("listener");
                Bundle bundle3 = new Bundle();
                bundle3.putString("errorReason", Log.getStackTraceString(e3));
                if (resultReceiver3 != null) {
                    resultReceiver3.send(-1, bundle3);
                }
            }
            this.mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() { // from class: com.hbisoft.hbrecorder.ScreenRecordService.1
                @Override // android.media.MediaRecorder.OnErrorListener
                public void onError(MediaRecorder mediaRecorder, int i4, int i5) {
                    if (i4 == 268435556 && ScreenRecordService.this.hasMaxFileBeenReached) {
                        return;
                    }
                    ResultReceiver resultReceiver4 = (ResultReceiver) intent.getParcelableExtra("listener");
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("error", 38);
                    bundle4.putString("errorReason", String.valueOf(i4));
                    if (resultReceiver4 != null) {
                        resultReceiver4.send(-1, bundle4);
                    }
                }
            });
            this.mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() { // from class: com.hbisoft.hbrecorder.ScreenRecordService.2
                @Override // android.media.MediaRecorder.OnInfoListener
                public void onInfo(MediaRecorder mediaRecorder, int i4, int i5) {
                    if (i4 == 801) {
                        ScreenRecordService.this.hasMaxFileBeenReached = true;
                        Log.i("ScreenRecordService", String.format(Locale.US, "onInfoListen what : %d | extra %d", Integer.valueOf(i4), Integer.valueOf(i5)));
                        ResultReceiver resultReceiver4 = (ResultReceiver) intent.getParcelableExtra("listener");
                        Bundle bundle4 = new Bundle();
                        bundle4.putInt("error", 48);
                        bundle4.putString("errorReason", ScreenRecordService.this.getString(R$string.max_file_reached));
                        if (resultReceiver4 != null) {
                            resultReceiver4.send(-1, bundle4);
                        }
                    }
                }
            });
            try {
                this.mMediaRecorder.start();
                ResultReceiver resultReceiver4 = (ResultReceiver) intent.getParcelableExtra("listener");
                Bundle bundle4 = new Bundle();
                bundle4.putInt("onStart", 111);
                if (resultReceiver4 != null) {
                    resultReceiver4.send(-1, bundle4);
                }
            } catch (Exception e4) {
                ResultReceiver resultReceiver5 = (ResultReceiver) intent.getParcelableExtra("listener");
                Bundle bundle5 = new Bundle();
                bundle5.putInt("error", 38);
                bundle5.putString("errorReason", Log.getStackTraceString(e4));
                if (resultReceiver5 != null) {
                    resultReceiver5.send(-1, bundle5);
                }
            }
        }
        return 1;
    }

    private void pauseRecording() {
        this.mMediaRecorder.pause();
    }

    private void resumeRecording() {
        this.mMediaRecorder.resume();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void setOutputFormatAsInt(String str) {
        char c;
        switch (str.hashCode()) {
            case -2032180703:
                if (str.equals("DEFAULT")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2014514182:
                if (str.equals("MPEG_4")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -813934554:
                if (str.equals("MPEG_2_TS")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 78191:
                if (str.equals("OGG")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2660249:
                if (str.equals("WEBM")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1179611262:
                if (str.equals("AAC_ADTS")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1228866118:
                if (str.equals("THREE_GPP")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1934542573:
                if (str.equals("AMR_NB")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1934542852:
                if (str.equals("AMR_WB")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.outputFormatAsInt = 0;
                break;
            case 1:
                this.outputFormatAsInt = 1;
                break;
            case 2:
                this.outputFormatAsInt = 3;
                break;
            case 3:
                this.outputFormatAsInt = 4;
                break;
            case 4:
                this.outputFormatAsInt = 6;
                break;
            case 5:
                this.outputFormatAsInt = 8;
                break;
            case 6:
                this.outputFormatAsInt = 9;
                break;
            case 7:
                this.outputFormatAsInt = 11;
                break;
            default:
                this.outputFormatAsInt = 2;
                break;
        }
    }

    private void setvideoEncoderAsInt(String str) {
        str.hashCode();
        switch (str) {
            case "DEFAULT":
                this.videoEncoderAsInt = 0;
                break;
            case "MPEG_4_SP":
                this.videoEncoderAsInt = 3;
                break;
            case "VP8":
                this.videoEncoderAsInt = 4;
                break;
            case "H263":
                this.videoEncoderAsInt = 1;
                break;
            case "H264":
                this.videoEncoderAsInt = 2;
                break;
            case "HEVC":
                this.videoEncoderAsInt = 5;
                break;
        }
    }

    private void setAudioSourceAsInt(String str) {
        str.hashCode();
        switch (str) {
            case "DEFAULT":
                this.audioSourceAsInt = 0;
                break;
            case "VOICE_PERFORMANCE":
                this.audioSourceAsInt = 10;
                break;
            case "VOICE_CALL":
                this.audioSourceAsInt = 4;
                break;
            case "MIC":
                this.audioSourceAsInt = 1;
                break;
            case "VOICE_DOWNLINK":
                this.audioSourceAsInt = 3;
                break;
            case "CAMCODER":
                this.audioSourceAsInt = 5;
                break;
            case "VOICE_RECOGNITION":
                this.audioSourceAsInt = 6;
                break;
            case "VOICE_UPLINK":
                this.audioSourceAsInt = 2;
                break;
            case "UNPROCESSED":
                this.audioSourceAsInt = 9;
                break;
            case "VOICE_COMMUNICATION":
                this.audioSourceAsInt = 7;
                break;
            case "REMOTE_SUBMIX":
                this.audioSourceAsInt = 8;
                break;
        }
    }

    private void initMediaProjection() {
        Object systemService = getSystemService("media_projection");
        Objects.requireNonNull(systemService);
        this.mMediaProjection = ((MediaProjectionManager) systemService).getMediaProjection(this.mResultCode, this.mResultData);
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getFileName() {
        return fileName;
    }

    private void initRecorder() throws Exception {
        String replace = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault()).format((Date) new java.sql.Date(System.currentTimeMillis())).replace(" ", "");
        String str = !this.isVideoHD ? "SD" : "HD";
        if (this.name == null) {
            this.name = str + replace;
        }
        filePath = this.path + "/" + this.name + ".mp4";
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(".mp4");
        fileName = sb.toString();
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mMediaRecorder = mediaRecorder;
        if (this.isAudioEnabled) {
            mediaRecorder.setAudioSource(this.audioSourceAsInt);
        }
        this.mMediaRecorder.setVideoSource(2);
        this.mMediaRecorder.setOutputFormat(this.outputFormatAsInt);
        int i = this.orientationHint;
        if (i != 400) {
            this.mMediaRecorder.setOrientationHint(i);
        }
        if (this.isAudioEnabled) {
            this.mMediaRecorder.setAudioEncoder(3);
            this.mMediaRecorder.setAudioEncodingBitRate(this.audioBitrate);
            this.mMediaRecorder.setAudioSamplingRate(this.audioSamplingRate);
        }
        this.mMediaRecorder.setVideoEncoder(this.videoEncoderAsInt);
        if (this.returnedUri != null) {
            try {
                ParcelFileDescriptor openFileDescriptor = getContentResolver().openFileDescriptor(this.returnedUri, "rw");
                Objects.requireNonNull(openFileDescriptor);
                this.mMediaRecorder.setOutputFile(openFileDescriptor.getFileDescriptor());
            } catch (Exception e) {
                ResultReceiver resultReceiver = (ResultReceiver) this.mIntent.getParcelableExtra("listener");
                Bundle bundle = new Bundle();
                bundle.putString("errorReason", Log.getStackTraceString(e));
                if (resultReceiver != null) {
                    resultReceiver.send(-1, bundle);
                }
            }
        } else {
            this.mMediaRecorder.setOutputFile(filePath);
        }
        this.mMediaRecorder.setVideoSize(this.mScreenWidth, this.mScreenHeight);
        if (!this.isCustomSettingsEnabled) {
            if (!this.isVideoHD) {
                this.mMediaRecorder.setVideoEncodingBitRate(12000000);
                this.mMediaRecorder.setVideoFrameRate(30);
            } else {
                this.mMediaRecorder.setVideoEncodingBitRate(this.mScreenWidth * 5 * this.mScreenHeight);
                this.mMediaRecorder.setVideoFrameRate(60);
            }
        } else {
            this.mMediaRecorder.setVideoEncodingBitRate(this.videoBitrate);
            this.mMediaRecorder.setVideoFrameRate(this.videoFrameRate);
        }
        long j = this.maxFileSize;
        if (j > 0) {
            this.mMediaRecorder.setMaxFileSize(j);
        }
        this.mMediaRecorder.prepare();
    }

    private void initVirtualDisplay() {
        this.mVirtualDisplay = this.mMediaProjection.createVirtualDisplay("ScreenRecordService", this.mScreenWidth, this.mScreenHeight, this.mScreenDensity, 16, this.mMediaRecorder.getSurface(), null, null);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        resetAll();
        callOnComplete();
    }

    private void callOnComplete() {
        Intent intent = this.mIntent;
        if (intent != null) {
            ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("listener");
            Bundle bundle = new Bundle();
            bundle.putString("onComplete", "Uri was passed");
            if (resultReceiver != null) {
                resultReceiver.send(-1, bundle);
            }
        }
    }

    private void resetAll() {
        stopForeground(true);
        VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        if (virtualDisplay != null) {
            virtualDisplay.release();
            this.mVirtualDisplay = null;
        }
        MediaRecorder mediaRecorder = this.mMediaRecorder;
        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            this.mMediaRecorder.reset();
        }
        MediaProjection mediaProjection = this.mMediaProjection;
        if (mediaProjection != null) {
            mediaProjection.stop();
            this.mMediaProjection = null;
        }
    }
}
