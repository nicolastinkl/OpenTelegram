package cn.jzvd;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.Surface;
import android.view.TextureView;

/* loaded from: classes.dex */
public abstract class JZMediaInterface implements TextureView.SurfaceTextureListener {
    public static SurfaceTexture SAVED_SURFACE;
    public Handler handler;
    public Jzvd jzvd;
    public Handler mMediaHandler;
    public HandlerThread mMediaHandlerThread;

    public abstract long getCurrentPosition();

    public abstract long getDuration();

    public abstract boolean isPlaying();

    public abstract void pause();

    public abstract void prepare();

    public abstract void release();

    public abstract void seekTo(long time);

    public abstract void setSpeed(float speed);

    public abstract void setSurface(Surface surface);

    public abstract void setVolume(float leftVolume, float rightVolume);

    public abstract void start();

    public JZMediaInterface(Jzvd jzvd) {
        this.jzvd = jzvd;
    }
}
