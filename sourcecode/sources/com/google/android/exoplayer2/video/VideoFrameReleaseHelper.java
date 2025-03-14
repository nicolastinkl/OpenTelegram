package com.google.android.exoplayer2.video;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class VideoFrameReleaseHelper {
    private int changeFrameRateStrategy;
    private final DisplayHelper displayHelper;
    private float formatFrameRate;
    private long frameIndex;
    private final FixedFrameRateEstimator frameRateEstimator = new FixedFrameRateEstimator();
    private long lastAdjustedFrameIndex;
    private long lastAdjustedReleaseTimeNs;
    private long pendingLastAdjustedFrameIndex;
    private long pendingLastAdjustedReleaseTimeNs;
    private float playbackSpeed;
    private boolean started;
    private Surface surface;
    private float surfaceMediaFrameRate;
    private float surfacePlaybackFrameRate;
    private long vsyncDurationNs;
    private long vsyncOffsetNs;
    private final VSyncSampler vsyncSampler;

    /* JADX INFO: Access modifiers changed from: private */
    interface DisplayHelper {

        public interface Listener {
            void onDefaultDisplayChanged(Display display);
        }

        void register(Listener listener);

        void unregister();
    }

    public VideoFrameReleaseHelper(Context context) {
        DisplayHelper maybeBuildDisplayHelper = maybeBuildDisplayHelper(context);
        this.displayHelper = maybeBuildDisplayHelper;
        this.vsyncSampler = maybeBuildDisplayHelper != null ? VSyncSampler.getInstance() : null;
        this.vsyncDurationNs = -9223372036854775807L;
        this.vsyncOffsetNs = -9223372036854775807L;
        this.formatFrameRate = -1.0f;
        this.playbackSpeed = 1.0f;
        this.changeFrameRateStrategy = 0;
    }

    public void setChangeFrameRateStrategy(int i) {
        if (this.changeFrameRateStrategy == i) {
            return;
        }
        this.changeFrameRateStrategy = i;
        updateSurfacePlaybackFrameRate(true);
    }

    public void onStarted() {
        this.started = true;
        resetAdjustment();
        if (this.displayHelper != null) {
            ((VSyncSampler) Assertions.checkNotNull(this.vsyncSampler)).addObserver();
            this.displayHelper.register(new DisplayHelper.Listener() { // from class: com.google.android.exoplayer2.video.VideoFrameReleaseHelper$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper.Listener
                public final void onDefaultDisplayChanged(Display display) {
                    VideoFrameReleaseHelper.this.updateDefaultDisplayRefreshRateParams(display);
                }
            });
        }
        updateSurfacePlaybackFrameRate(false);
    }

    public void onSurfaceChanged(Surface surface) {
        if (surface instanceof PlaceholderSurface) {
            surface = null;
        }
        if (this.surface == surface) {
            return;
        }
        clearSurfaceFrameRate();
        this.surface = surface;
        updateSurfacePlaybackFrameRate(true);
    }

    public void onPositionReset() {
        resetAdjustment();
    }

    public void onPlaybackSpeed(float f) {
        this.playbackSpeed = f;
        resetAdjustment();
        updateSurfacePlaybackFrameRate(false);
    }

    public void onFormatChanged(float f) {
        this.formatFrameRate = f;
        this.frameRateEstimator.reset();
        updateSurfaceMediaFrameRate();
    }

    public void onNextFrame(long j) {
        long j2 = this.pendingLastAdjustedFrameIndex;
        if (j2 != -1) {
            this.lastAdjustedFrameIndex = j2;
            this.lastAdjustedReleaseTimeNs = this.pendingLastAdjustedReleaseTimeNs;
        }
        this.frameIndex++;
        this.frameRateEstimator.onNextFrame(j * 1000);
        updateSurfaceMediaFrameRate();
    }

    public void onStopped() {
        this.started = false;
        DisplayHelper displayHelper = this.displayHelper;
        if (displayHelper != null) {
            displayHelper.unregister();
            ((VSyncSampler) Assertions.checkNotNull(this.vsyncSampler)).removeObserver();
        }
        clearSurfaceFrameRate();
    }

    public long adjustReleaseTime(long j) {
        long j2;
        VSyncSampler vSyncSampler;
        if (this.lastAdjustedFrameIndex != -1 && this.frameRateEstimator.isSynced()) {
            long frameDurationNs = this.lastAdjustedReleaseTimeNs + ((long) ((this.frameRateEstimator.getFrameDurationNs() * (this.frameIndex - this.lastAdjustedFrameIndex)) / this.playbackSpeed));
            if (!adjustmentAllowed(j, frameDurationNs)) {
                resetAdjustment();
            } else {
                j2 = frameDurationNs;
                this.pendingLastAdjustedFrameIndex = this.frameIndex;
                this.pendingLastAdjustedReleaseTimeNs = j2;
                vSyncSampler = this.vsyncSampler;
                if (vSyncSampler != null || this.vsyncDurationNs == -9223372036854775807L) {
                    return j2;
                }
                long j3 = vSyncSampler.sampledVsyncTimeNs;
                return j3 == -9223372036854775807L ? j2 : closestVsync(j2, j3, this.vsyncDurationNs) - this.vsyncOffsetNs;
            }
        }
        j2 = j;
        this.pendingLastAdjustedFrameIndex = this.frameIndex;
        this.pendingLastAdjustedReleaseTimeNs = j2;
        vSyncSampler = this.vsyncSampler;
        if (vSyncSampler != null) {
        }
        return j2;
    }

    private void resetAdjustment() {
        this.frameIndex = 0L;
        this.lastAdjustedFrameIndex = -1L;
        this.pendingLastAdjustedFrameIndex = -1L;
    }

    private static boolean adjustmentAllowed(long j, long j2) {
        return Math.abs(j - j2) <= 20000000;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x005c, code lost:
    
        if (java.lang.Math.abs(r0 - r8.surfaceMediaFrameRate) >= (r8.frameRateEstimator.isSynced() && (r8.frameRateEstimator.getMatchingFrameDurationSumNs() > 5000000000L ? 1 : (r8.frameRateEstimator.getMatchingFrameDurationSumNs() == 5000000000L ? 0 : -1)) >= 0 ? 0.02f : 1.0f)) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005f, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006a, code lost:
    
        if (r8.frameRateEstimator.getFramesWithoutSyncCount() >= 30) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateSurfaceMediaFrameRate() {
        /*
            r8 = this;
            int r0 = com.google.android.exoplayer2.util.Util.SDK_INT
            r1 = 30
            if (r0 < r1) goto L73
            android.view.Surface r0 = r8.surface
            if (r0 != 0) goto Lc
            goto L73
        Lc:
            com.google.android.exoplayer2.video.FixedFrameRateEstimator r0 = r8.frameRateEstimator
            boolean r0 = r0.isSynced()
            if (r0 == 0) goto L1b
            com.google.android.exoplayer2.video.FixedFrameRateEstimator r0 = r8.frameRateEstimator
            float r0 = r0.getFrameRate()
            goto L1d
        L1b:
            float r0 = r8.formatFrameRate
        L1d:
            float r2 = r8.surfaceMediaFrameRate
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 != 0) goto L24
            return
        L24:
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r4 = 0
            r5 = 1
            int r6 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r6 == 0) goto L61
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 == 0) goto L61
            com.google.android.exoplayer2.video.FixedFrameRateEstimator r1 = r8.frameRateEstimator
            boolean r1 = r1.isSynced()
            if (r1 == 0) goto L49
            com.google.android.exoplayer2.video.FixedFrameRateEstimator r1 = r8.frameRateEstimator
            long r1 = r1.getMatchingFrameDurationSumNs()
            r6 = 5000000000(0x12a05f200, double:2.470328229E-314)
            int r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r3 < 0) goto L49
            r1 = 1
            goto L4a
        L49:
            r1 = 0
        L4a:
            if (r1 == 0) goto L50
            r1 = 1017370378(0x3ca3d70a, float:0.02)
            goto L52
        L50:
            r1 = 1065353216(0x3f800000, float:1.0)
        L52:
            float r2 = r8.surfaceMediaFrameRate
            float r2 = r0 - r2
            float r2 = java.lang.Math.abs(r2)
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 < 0) goto L5f
            goto L6c
        L5f:
            r5 = 0
            goto L6c
        L61:
            if (r6 == 0) goto L64
            goto L6c
        L64:
            com.google.android.exoplayer2.video.FixedFrameRateEstimator r2 = r8.frameRateEstimator
            int r2 = r2.getFramesWithoutSyncCount()
            if (r2 < r1) goto L5f
        L6c:
            if (r5 == 0) goto L73
            r8.surfaceMediaFrameRate = r0
            r8.updateSurfacePlaybackFrameRate(r4)
        L73:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.VideoFrameReleaseHelper.updateSurfaceMediaFrameRate():void");
    }

    private void updateSurfacePlaybackFrameRate(boolean z) {
        Surface surface;
        if (Util.SDK_INT < 30 || (surface = this.surface) == null || this.changeFrameRateStrategy == Integer.MIN_VALUE) {
            return;
        }
        float f = 0.0f;
        if (this.started) {
            float f2 = this.surfaceMediaFrameRate;
            if (f2 != -1.0f) {
                f = this.playbackSpeed * f2;
            }
        }
        if (z || this.surfacePlaybackFrameRate != f) {
            this.surfacePlaybackFrameRate = f;
            Api30.setSurfaceFrameRate(surface, f);
        }
    }

    private void clearSurfaceFrameRate() {
        Surface surface;
        if (Util.SDK_INT < 30 || (surface = this.surface) == null || this.changeFrameRateStrategy == Integer.MIN_VALUE || this.surfacePlaybackFrameRate == 0.0f) {
            return;
        }
        this.surfacePlaybackFrameRate = 0.0f;
        Api30.setSurfaceFrameRate(surface, 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDefaultDisplayRefreshRateParams(Display display) {
        if (display != null) {
            long refreshRate = (long) (1.0E9d / display.getRefreshRate());
            this.vsyncDurationNs = refreshRate;
            this.vsyncOffsetNs = (refreshRate * 80) / 100;
        } else {
            Log.w("VideoFrameReleaseHelper", "Unable to query display refresh rate");
            this.vsyncDurationNs = -9223372036854775807L;
            this.vsyncOffsetNs = -9223372036854775807L;
        }
    }

    private static long closestVsync(long j, long j2, long j3) {
        long j4;
        long j5 = j2 + (((j - j2) / j3) * j3);
        if (j <= j5) {
            j4 = j5 - j3;
        } else {
            j5 = j3 + j5;
            j4 = j5;
        }
        return j5 - j < j - j4 ? j5 : j4;
    }

    private static DisplayHelper maybeBuildDisplayHelper(Context context) {
        if (context == null) {
            return null;
        }
        Context applicationContext = context.getApplicationContext();
        DisplayHelper maybeBuildNewInstance = Util.SDK_INT >= 17 ? DisplayHelperV17.maybeBuildNewInstance(applicationContext) : null;
        return maybeBuildNewInstance == null ? DisplayHelperV16.maybeBuildNewInstance(applicationContext) : maybeBuildNewInstance;
    }

    private static final class Api30 {
        public static void setSurfaceFrameRate(Surface surface, float f) {
            try {
                surface.setFrameRate(f, f == 0.0f ? 0 : 1);
            } catch (IllegalStateException e) {
                Log.e("VideoFrameReleaseHelper", "Failed to call Surface.setFrameRate", e);
            }
        }
    }

    private static final class DisplayHelperV16 implements DisplayHelper {
        private final WindowManager windowManager;

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
        }

        public static DisplayHelper maybeBuildNewInstance(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                return new DisplayHelperV16(windowManager);
            }
            return null;
        }

        private DisplayHelperV16(WindowManager windowManager) {
            this.windowManager = windowManager;
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            listener.onDefaultDisplayChanged(this.windowManager.getDefaultDisplay());
        }
    }

    private static final class DisplayHelperV17 implements DisplayHelper, DisplayManager.DisplayListener {
        private final DisplayManager displayManager;
        private DisplayHelper.Listener listener;

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        public static DisplayHelper maybeBuildNewInstance(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager != null) {
                return new DisplayHelperV17(displayManager);
            }
            return null;
        }

        private DisplayHelperV17(DisplayManager displayManager) {
            this.displayManager = displayManager;
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void register(DisplayHelper.Listener listener) {
            this.listener = listener;
            this.displayManager.registerDisplayListener(this, Util.createHandlerForCurrentLooper());
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void unregister() {
            this.displayManager.unregisterDisplayListener(this);
            this.listener = null;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            DisplayHelper.Listener listener = this.listener;
            if (listener == null || i != 0) {
                return;
            }
            listener.onDefaultDisplayChanged(getDefaultDisplay());
        }

        private Display getDefaultDisplay() {
            return this.displayManager.getDisplay(0);
        }
    }

    private static final class VSyncSampler implements Choreographer.FrameCallback, Handler.Callback {
        private static final VSyncSampler INSTANCE = new VSyncSampler();
        private Choreographer choreographer;
        private final HandlerThread choreographerOwnerThread;
        private final Handler handler;
        private int observerCount;
        public volatile long sampledVsyncTimeNs = -9223372036854775807L;

        public static VSyncSampler getInstance() {
            return INSTANCE;
        }

        private VSyncSampler() {
            HandlerThread handlerThread = new HandlerThread("ExoPlayer:FrameReleaseChoreographer");
            this.choreographerOwnerThread = handlerThread;
            handlerThread.start();
            Handler createHandler = Util.createHandler(handlerThread.getLooper(), this);
            this.handler = createHandler;
            createHandler.sendEmptyMessage(0);
        }

        public void addObserver() {
            this.handler.sendEmptyMessage(1);
        }

        public void removeObserver() {
            this.handler.sendEmptyMessage(2);
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            this.sampledVsyncTimeNs = j;
            ((Choreographer) Assertions.checkNotNull(this.choreographer)).postFrameCallbackDelayed(this, 500L);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                createChoreographerInstanceInternal();
                return true;
            }
            if (i == 1) {
                addObserverInternal();
                return true;
            }
            if (i != 2) {
                return false;
            }
            removeObserverInternal();
            return true;
        }

        private void createChoreographerInstanceInternal() {
            try {
                this.choreographer = Choreographer.getInstance();
            } catch (RuntimeException e) {
                Log.w("VideoFrameReleaseHelper", "Vsync sampling disabled due to platform error", e);
            }
        }

        private void addObserverInternal() {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                int i = this.observerCount + 1;
                this.observerCount = i;
                if (i == 1) {
                    choreographer.postFrameCallback(this);
                }
            }
        }

        private void removeObserverInternal() {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                int i = this.observerCount - 1;
                this.observerCount = i;
                if (i == 0) {
                    choreographer.removeFrameCallback(this);
                    this.sampledVsyncTimeNs = -9223372036854775807L;
                }
            }
        }
    }
}
