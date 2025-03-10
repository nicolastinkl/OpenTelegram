package com.hbisoft.hbrecorder;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Iterator;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public class HBRecorder implements MyListener {
    private Activity activity;
    private byte[] byteArray;
    private final Context context;
    private String fileName;
    private final HBRecorderListener hbRecorderListener;
    private int mScreenDensity;
    private int mScreenHeight;
    private int mScreenWidth;
    Uri mUri;
    private String notificationButtonText;
    private String notificationDescription;
    private String notificationTitle;
    private FileObserver observer;
    private int orientation;
    private String outputPath;
    private int resultCode;
    Intent service;
    private boolean isAudioEnabled = true;
    private boolean isVideoHDEnabled = true;
    private int audioBitrate = 0;
    private int audioSamplingRate = 0;
    private int vectorDrawable = 0;
    private String audioSource = "MIC";
    private String videoEncoder = "DEFAULT";
    private boolean enableCustomSettings = false;
    private int videoFrameRate = 30;
    private int videoBitrate = 40000000;
    private String outputFormat = "DEFAULT";
    private long maxFileSize = 0;
    boolean wasOnErrorCalled = false;
    boolean isMaxDurationSet = false;
    int maxDuration = 0;
    boolean mWasUriSet = false;
    Countdown countDown = null;

    public HBRecorder(Context context, HBRecorderListener hBRecorderListener) {
        this.context = context.getApplicationContext();
        this.hbRecorderListener = hBRecorderListener;
        setScreenDensity();
    }

    public void setOutputPath(String str) {
        this.outputPath = str;
    }

    public void setOutputUri(Uri uri) {
        this.mWasUriSet = true;
        this.mUri = uri;
    }

    public boolean wasUriSet() {
        return this.mWasUriSet;
    }

    public void setFileName(String str) {
        this.fileName = str;
    }

    public void setAudioBitrate(int i) {
        this.audioBitrate = i;
    }

    public void setAudioSamplingRate(int i) {
        this.audioSamplingRate = i;
    }

    public void isAudioEnabled(boolean z) {
        this.isAudioEnabled = z;
    }

    public void recordHDVideo(boolean z) {
        this.isVideoHDEnabled = z;
    }

    private void setScreenDensity() {
        this.mScreenDensity = Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public String getFilePath() {
        return ScreenRecordService.getFilePath();
    }

    public String getFileName() {
        return ScreenRecordService.getFileName();
    }

    public void startScreenRecording(Intent intent, int i, Activity activity) {
        this.resultCode = i;
        this.activity = activity;
        startService(intent);
    }

    public void stopScreenRecording() {
        this.context.stopService(new Intent(this.context, (Class<?>) ScreenRecordService.class));
    }

    public boolean isBusyRecording() {
        ActivityManager activityManager = (ActivityManager) this.context.getSystemService("activity");
        if (activityManager == null) {
            return false;
        }
        Iterator<ActivityManager.RunningServiceInfo> it = activityManager.getRunningServices(Integer.MAX_VALUE).iterator();
        while (it.hasNext()) {
            if (ScreenRecordService.class.getName().equals(it.next().service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void setNotificationSmallIcon(int i) {
        Bitmap decodeResource = BitmapFactory.decodeResource(this.context.getResources(), i);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        decodeResource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        this.byteArray = byteArrayOutputStream.toByteArray();
    }

    public void setNotificationTitle(String str) {
        this.notificationTitle = str;
    }

    public void setNotificationDescription(String str) {
        this.notificationDescription = str;
    }

    private void startService(Intent intent) {
        try {
            if (!this.mWasUriSet) {
                if (this.outputPath != null) {
                    this.observer = new FileObserver(new File(this.outputPath).getParent(), this.activity, this);
                } else {
                    this.observer = new FileObserver(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)), this.activity, this);
                }
                this.observer.startWatching();
            }
            Intent intent2 = new Intent(this.context, (Class<?>) ScreenRecordService.class);
            this.service = intent2;
            if (this.mWasUriSet) {
                intent2.putExtra("mUri", this.mUri.toString());
            }
            this.service.putExtra("code", this.resultCode);
            this.service.putExtra("data", intent);
            this.service.putExtra(MediaStreamTrack.AUDIO_TRACK_KIND, this.isAudioEnabled);
            this.service.putExtra("width", this.mScreenWidth);
            this.service.putExtra("height", this.mScreenHeight);
            this.service.putExtra("density", this.mScreenDensity);
            this.service.putExtra("quality", this.isVideoHDEnabled);
            this.service.putExtra("path", this.outputPath);
            this.service.putExtra("fileName", this.fileName);
            this.service.putExtra("orientation", this.orientation);
            this.service.putExtra("audioBitrate", this.audioBitrate);
            this.service.putExtra("audioSamplingRate", this.audioSamplingRate);
            this.service.putExtra("notificationSmallBitmap", this.byteArray);
            this.service.putExtra("notificationSmallVector", this.vectorDrawable);
            this.service.putExtra("notificationTitle", this.notificationTitle);
            this.service.putExtra("notificationDescription", this.notificationDescription);
            this.service.putExtra("notificationButtonText", this.notificationButtonText);
            this.service.putExtra("enableCustomSettings", this.enableCustomSettings);
            this.service.putExtra("audioSource", this.audioSource);
            this.service.putExtra("videoEncoder", this.videoEncoder);
            this.service.putExtra("videoFrameRate", this.videoFrameRate);
            this.service.putExtra("videoBitrate", this.videoBitrate);
            this.service.putExtra("outputFormat", this.outputFormat);
            this.service.putExtra("listener", new ResultReceiver(new Handler()) { // from class: com.hbisoft.hbrecorder.HBRecorder.1
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int i, Bundle bundle) {
                    super.onReceiveResult(i, bundle);
                    if (i == -1) {
                        String string = bundle.getString("errorReason");
                        String string2 = bundle.getString("onComplete");
                        int i2 = bundle.getInt("onStart");
                        int i3 = bundle.getInt("error");
                        if (string != null) {
                            HBRecorder.this.stopCountDown();
                            HBRecorder hBRecorder = HBRecorder.this;
                            if (!hBRecorder.mWasUriSet) {
                                hBRecorder.observer.stopWatching();
                            }
                            HBRecorder hBRecorder2 = HBRecorder.this;
                            hBRecorder2.wasOnErrorCalled = true;
                            if (i3 > 0) {
                                hBRecorder2.hbRecorderListener.HBRecorderOnError(i3, string);
                            } else {
                                hBRecorder2.hbRecorderListener.HBRecorderOnError(100, string);
                            }
                            try {
                                HBRecorder.this.context.stopService(new Intent(HBRecorder.this.context, (Class<?>) ScreenRecordService.class));
                                return;
                            } catch (Exception unused) {
                                return;
                            }
                        }
                        if (string2 != null) {
                            HBRecorder.this.stopCountDown();
                            HBRecorder hBRecorder3 = HBRecorder.this;
                            if (hBRecorder3.mWasUriSet && !hBRecorder3.wasOnErrorCalled) {
                                hBRecorder3.hbRecorderListener.HBRecorderOnComplete();
                            }
                            HBRecorder.this.wasOnErrorCalled = false;
                            return;
                        }
                        if (i2 != 0) {
                            HBRecorder.this.hbRecorderListener.HBRecorderOnStart();
                            HBRecorder hBRecorder4 = HBRecorder.this;
                            if (hBRecorder4.isMaxDurationSet) {
                                hBRecorder4.startCountdown();
                            }
                        }
                    }
                }
            });
            this.service.putExtra("maxFileSize", this.maxFileSize);
            this.context.startService(this.service);
        } catch (Exception e) {
            this.hbRecorderListener.HBRecorderOnError(0, Log.getStackTraceString(e));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCountdown() {
        Countdown countdown = new Countdown(this.maxDuration, 1000L, 0L) { // from class: com.hbisoft.hbrecorder.HBRecorder.2
            @Override // com.hbisoft.hbrecorder.Countdown
            public void onStopCalled() {
            }

            @Override // com.hbisoft.hbrecorder.Countdown
            public void onTick(long j) {
            }

            @Override // com.hbisoft.hbrecorder.Countdown
            public void onFinished() {
                onTick(0L);
                HBRecorder.this.activity.runOnUiThread(new Runnable() { // from class: com.hbisoft.hbrecorder.HBRecorder.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            HBRecorder.this.stopScreenRecording();
                            HBRecorder.this.observer.stopWatching();
                            HBRecorder.this.hbRecorderListener.HBRecorderOnComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        this.countDown = countdown;
        countdown.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopCountDown() {
        Countdown countdown = this.countDown;
        if (countdown != null) {
            countdown.stop();
        }
    }

    @Override // com.hbisoft.hbrecorder.MyListener
    public void callback() {
        this.observer.stopWatching();
        this.hbRecorderListener.HBRecorderOnComplete();
    }
}
