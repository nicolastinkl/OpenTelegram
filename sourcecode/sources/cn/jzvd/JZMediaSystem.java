package cn.jzvd;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import java.util.Map;

/* loaded from: classes.dex */
public class JZMediaSystem extends JZMediaInterface implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
    public MediaPlayer mediaPlayer;

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public JZMediaSystem(Jzvd jzvd) {
        super(jzvd);
    }

    @Override // cn.jzvd.JZMediaInterface
    public void prepare() {
        release();
        HandlerThread handlerThread = new HandlerThread(Jzvd.TAG);
        this.mMediaHandlerThread = handlerThread;
        handlerThread.start();
        this.mMediaHandler = new Handler(this.mMediaHandlerThread.getLooper());
        this.handler = new Handler();
        this.mMediaHandler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$prepare$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepare$0() {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setAudioStreamType(3);
            this.mediaPlayer.setLooping(this.jzvd.jzDataSource.looping);
            this.mediaPlayer.setOnPreparedListener(this);
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.setOnBufferingUpdateListener(this);
            this.mediaPlayer.setScreenOnWhilePlaying(true);
            this.mediaPlayer.setOnSeekCompleteListener(this);
            this.mediaPlayer.setOnErrorListener(this);
            this.mediaPlayer.setOnInfoListener(this);
            this.mediaPlayer.setOnVideoSizeChangedListener(this);
            MediaPlayer.class.getDeclaredMethod("setDataSource", String.class, Map.class).invoke(this.mediaPlayer, this.jzvd.jzDataSource.getCurrentUrl().toString(), this.jzvd.jzDataSource.headerMap);
            this.mediaPlayer.prepareAsync();
            this.mediaPlayer.setSurface(new Surface(JZMediaInterface.SAVED_SURFACE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$start$1() {
        this.mediaPlayer.start();
    }

    @Override // cn.jzvd.JZMediaInterface
    public void start() {
        this.mMediaHandler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$start$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pause$2() {
        this.mediaPlayer.pause();
    }

    @Override // cn.jzvd.JZMediaInterface
    public void pause() {
        this.mMediaHandler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$pause$2();
            }
        });
    }

    @Override // cn.jzvd.JZMediaInterface
    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    @Override // cn.jzvd.JZMediaInterface
    public void seekTo(final long time) {
        this.mMediaHandler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$seekTo$3(time);
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
        handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.lambda$release$4(mediaPlayer, handlerThread);
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
    public void setVolume(final float leftVolume, final float rightVolume) {
        Handler handler = this.mMediaHandler;
        if (handler == null) {
            return;
        }
        handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$setVolume$5(leftVolume, rightVolume);
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
    public void setSpeed(float speed) {
        if (Build.VERSION.SDK_INT >= 23) {
            PlaybackParams playbackParams = this.mediaPlayer.getPlaybackParams();
            playbackParams.setSpeed(speed);
            this.mediaPlayer.setPlaybackParams(playbackParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPrepared$6() {
        this.jzvd.onPrepared();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onPrepared$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCompletion$7() {
        this.jzvd.onCompletion();
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onCompletion$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBufferingUpdate$8(int i) {
        this.jzvd.setBufferProgress(i);
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int percent) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onBufferingUpdate$8(percent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSeekComplete$9() {
        this.jzvd.onSeekComplete();
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onSeekComplete$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onError$10(int i, int i2) {
        this.jzvd.onError(i, i2);
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, final int what, final int extra) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onError$10(what, extra);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onInfo$11(int i, int i2) {
        this.jzvd.onInfo(i, i2);
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, final int what, final int extra) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onInfo$11(what, extra);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVideoSizeChanged$12(int i, int i2) {
        this.jzvd.onVideoSizeChanged(i, i2);
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, final int width, final int height) {
        this.handler.post(new Runnable() { // from class: cn.jzvd.JZMediaSystem$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                JZMediaSystem.this.lambda$onVideoSizeChanged$12(width, height);
            }
        });
    }

    @Override // cn.jzvd.JZMediaInterface
    public void setSurface(Surface surface) {
        this.mediaPlayer.setSurface(surface);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        SurfaceTexture surfaceTexture = JZMediaInterface.SAVED_SURFACE;
        if (surfaceTexture == null) {
            JZMediaInterface.SAVED_SURFACE = surface;
            prepare();
        } else {
            this.jzvd.textureView.setSurfaceTexture(surfaceTexture);
        }
    }
}
