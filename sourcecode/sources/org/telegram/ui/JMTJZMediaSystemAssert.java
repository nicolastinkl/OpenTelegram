package org.telegram.ui;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.Jzvd;

/* loaded from: classes3.dex */
public class JMTJZMediaSystemAssert extends JZMediaInterface implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
    public MediaPlayer mediaPlayer;

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public JMTJZMediaSystemAssert(Jzvd jzvd) {
        super(jzvd);
    }

    @Override // cn.jzvd.JZMediaInterface
    public void prepare() {
        release();
        HandlerThread handlerThread = new HandlerThread("JMTJZVD");
        this.mMediaHandlerThread = handlerThread;
        handlerThread.start();
        this.mMediaHandler = new Handler(this.mMediaHandlerThread.getLooper());
        this.handler = new Handler();
        this.mMediaHandler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$prepare$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepare$0() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(2).build());
            this.mediaPlayer.setLooping(this.jzvd.jzDataSource.looping);
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.setOnBufferingUpdateListener(this);
            this.mediaPlayer.setScreenOnWhilePlaying(true);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnErrorListener(this);
            this.mediaPlayer.setOnInfoListener(this);
            this.mediaPlayer.setOnVideoSizeChangedListener(this);
            AssetFileDescriptor openFd = this.jzvd.getContext().getAssets().openFd(this.jzvd.jzDataSource.getCurrentUrl().toString());
            this.mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setSurface(new Surface(JZMediaInterface.SAVED_SURFACE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // cn.jzvd.JZMediaInterface
    public void start() {
        Handler handler = this.mMediaHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$start$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$1() {
        this.mediaPlayer.start();
    }

    @Override // cn.jzvd.JZMediaInterface
    public void pause() {
        Handler handler = this.mMediaHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$pause$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pause$2() {
        this.mediaPlayer.pause();
    }

    @Override // cn.jzvd.JZMediaInterface
    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    @Override // cn.jzvd.JZMediaInterface
    public void seekTo(final long j) {
        Handler handler = this.mMediaHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$seekTo$3(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$seekTo$3(long j) {
        try {
            this.mediaPlayer.seekTo((int) j);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override // cn.jzvd.JZMediaInterface
    public void release() {
        final HandlerThread handlerThread;
        final MediaPlayer mediaPlayer;
        Handler handler = this.mMediaHandler;
        if (handler == null || (handlerThread = this.mMediaHandlerThread) == null || (mediaPlayer = this.mediaPlayer) == null) {
            return;
        }
        JZMediaInterface.SAVED_SURFACE = null;
        handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.lambda$release$4(mediaPlayer, handlerThread);
            }
        });
        this.mediaPlayer = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$release$4(MediaPlayer mediaPlayer, HandlerThread handlerThread) {
        mediaPlayer.setSurface(null);
        mediaPlayer.release();
        handlerThread.quit();
    }

    @Override // cn.jzvd.JZMediaInterface
    public long getCurrentPosition() {
        if (this.mediaPlayer != null) {
            return r0.getCurrentPosition();
        }
        return 0L;
    }

    @Override // cn.jzvd.JZMediaInterface
    public long getDuration() {
        if (this.mediaPlayer != null) {
            return r0.getDuration();
        }
        return 0L;
    }

    @Override // cn.jzvd.JZMediaInterface
    public void setVolume(final float f, final float f2) {
        Handler handler = this.mMediaHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$setVolume$5(f, f2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVolume$5(float f, float f2) {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f, f2);
        }
    }

    @Override // cn.jzvd.JZMediaInterface
    public void setSpeed(float f) {
        if (Build.VERSION.SDK_INT >= 23) {
            PlaybackParams playbackParams = this.mediaPlayer.getPlaybackParams();
            playbackParams.setSpeed(f);
            this.mediaPlayer.setPlaybackParams(playbackParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPrepared$6() {
        this.jzvd.onPrepared();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onPrepared$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCompletion$7() {
        this.jzvd.onCompletion();
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onCompletion$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBufferingUpdate$8(int i) {
        this.jzvd.setBufferProgress(i);
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int i) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onBufferingUpdate$8(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSeekComplete$9() {
        this.jzvd.onSeekComplete();
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onSeekComplete$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$10(int i, int i2) {
        this.jzvd.onError(i, i2);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, final int i, final int i2) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onError$10(i, i2);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInfo$11(int i, int i2) {
        this.jzvd.onInfo(i, i2);
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, final int i, final int i2) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onInfo$11(i, i2);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVideoSizeChanged$12(int i, int i2) {
        this.jzvd.onVideoSizeChanged(i, i2);
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, final int i, final int i2) {
        this.handler.post(new Runnable() { // from class: org.telegram.ui.JMTJZMediaSystemAssert$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                JMTJZMediaSystemAssert.this.lambda$onVideoSizeChanged$12(i, i2);
            }
        });
    }

    @Override // cn.jzvd.JZMediaInterface
    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        SurfaceTexture surfaceTexture2 = JZMediaInterface.SAVED_SURFACE;
        if (surfaceTexture2 == null) {
            JZMediaInterface.SAVED_SURFACE = surfaceTexture;
            prepare();
        } else {
            this.jzvd.textureView.setSurfaceTexture(surfaceTexture2);
        }
    }
}
