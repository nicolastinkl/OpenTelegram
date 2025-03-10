package cn.jzvd;

import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.jzvd.Jzvd;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public abstract class Jzvd extends FrameLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    public static Jzvd CURRENT_JZVD = null;
    public static final int SCREEN_FULLSCREEN = 1;
    public static final int SCREEN_NORMAL = 0;
    public static final int SCREEN_TINY = 2;
    public static final int STATE_AUTO_COMPLETE = 7;
    public static final int STATE_ERROR = 8;
    public static final int STATE_IDLE = -1;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_PAUSE = 6;
    public static final int STATE_PLAYING = 5;
    public static final int STATE_PREPARED = 4;
    public static final int STATE_PREPARING = 1;
    public static final int STATE_PREPARING_CHANGE_URL = 2;
    public static final int STATE_PREPARING_PLAYING = 3;
    public static final String TAG = "JZVD";
    public static final int THRESHOLD = 80;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER = 0;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP = 2;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL = 3;
    protected Timer UPDATE_PROGRESS_TIMER;
    protected int blockHeight;
    protected int blockIndex;
    protected ViewGroup.LayoutParams blockLayoutParams;
    protected int blockWidth;
    public ViewGroup bottomContainer;
    public TextView currentTimeTextView;
    public ImageView fullscreenButton;
    protected long gobakFullscreenTime;
    protected long gotoFullscreenTime;
    public int heightRatio;
    public JZDataSource jzDataSource;
    protected Context jzvdContext;
    protected AudioManager mAudioManager;
    protected boolean mChangeBrightness;
    protected boolean mChangePosition;
    protected boolean mChangeVolume;
    protected long mCurrentPosition;
    protected float mDownX;
    protected float mDownY;
    protected float mGestureDownBrightness;
    protected long mGestureDownPosition;
    protected int mGestureDownVolume;
    protected ProgressTimerTask mProgressTimerTask;
    protected int mScreenHeight;
    protected int mScreenWidth;
    protected long mSeekTimePosition;
    protected boolean mTouchingProgressBar;
    public JZMediaInterface mediaInterface;
    public Class mediaInterfaceClass;
    public int positionInList;
    public boolean preloading;
    public SeekBar progressBar;
    public int screen;
    public long seekToInAdvance;
    public int seekToManulPosition;
    public ImageView startButton;
    public int state;
    public JZTextureView textureView;
    public ViewGroup textureViewContainer;
    public ViewGroup topContainer;
    public TextView totalTimeTextView;
    public int videoRotation;
    public int widthRatio;
    public static LinkedList<ViewGroup> CONTAINER_LIST = new LinkedList<>();
    public static boolean TOOL_BAR_EXIST = true;
    public static int FULLSCREEN_ORIENTATION = 6;
    public static int NORMAL_ORIENTATION = 1;
    public static boolean SAVE_PROGRESS = true;
    public static boolean WIFI_TIP_DIALOG_SHOWED = false;
    public static int VIDEO_IMAGE_DISPLAY_TYPE = 0;
    public static long lastAutoFullscreenTime = 0;
    public static int ON_PLAY_PAUSE_TMP_STATE = 0;
    public static int backUpBufferState = -1;
    public static float PROGRESS_DRAG_RATE = 1.0f;
    public static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: cn.jzvd.Jzvd.1
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public void onAudioFocusChange(int focusChange) {
            if (focusChange != -2) {
                if (focusChange != -1) {
                    return;
                }
                Jzvd.releaseAllVideos();
                Log.d(Jzvd.TAG, "AUDIOFOCUS_LOSS [" + hashCode() + "]");
                return;
            }
            try {
                Jzvd jzvd = Jzvd.CURRENT_JZVD;
                if (jzvd != null && jzvd.state == 5) {
                    jzvd.startButton.performClick();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            Log.d(Jzvd.TAG, "AUDIOFOCUS_LOSS_TRANSIENT [" + hashCode() + "]");
        }
    };

    public void dismissBrightnessDialog() {
    }

    public void dismissProgressDialog() {
    }

    public void dismissVolumeDialog() {
    }

    public abstract int getLayoutId();

    public void onSeekComplete() {
    }

    public void showBrightnessDialog(int brightnessPercent) {
    }

    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
    }

    public void showVolumeDialog(float deltaY, int volumePercent) {
    }

    public void showWifiDialog() {
    }

    public Jzvd(Context context) {
        super(context);
        this.state = -1;
        this.screen = -1;
        this.widthRatio = 0;
        this.heightRatio = 0;
        this.positionInList = -1;
        this.videoRotation = 0;
        this.seekToManulPosition = -1;
        this.seekToInAdvance = 0L;
        this.preloading = false;
        this.gobakFullscreenTime = 0L;
        this.gotoFullscreenTime = 0L;
        init(context);
    }

    public Jzvd(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = -1;
        this.screen = -1;
        this.widthRatio = 0;
        this.heightRatio = 0;
        this.positionInList = -1;
        this.videoRotation = 0;
        this.seekToManulPosition = -1;
        this.seekToInAdvance = 0L;
        this.preloading = false;
        this.gobakFullscreenTime = 0L;
        this.gotoFullscreenTime = 0L;
        init(context);
    }

    public static void goOnPlayOnResume() {
        Jzvd jzvd = CURRENT_JZVD;
        if (jzvd != null) {
            int i = jzvd.state;
            if (i == 6) {
                if (ON_PLAY_PAUSE_TMP_STATE == 6) {
                    jzvd.onStatePause();
                    CURRENT_JZVD.mediaInterface.pause();
                } else {
                    jzvd.onStatePlaying();
                    CURRENT_JZVD.mediaInterface.start();
                }
                ON_PLAY_PAUSE_TMP_STATE = 0;
            } else if (i == 1) {
                jzvd.startVideo();
            }
            Jzvd jzvd2 = CURRENT_JZVD;
            if (jzvd2.screen == 1) {
                JZUtils.hideStatusBar(jzvd2.jzvdContext);
                JZUtils.hideSystemUI(CURRENT_JZVD.jzvdContext);
            }
        }
    }

    public static void goOnPlayOnPause() {
        Jzvd jzvd = CURRENT_JZVD;
        if (jzvd != null) {
            int i = jzvd.state;
            if (i == 7 || i == 0 || i == 8) {
                releaseAllVideos();
                return;
            }
            if (i == 1) {
                setCurrentJzvd(jzvd);
                CURRENT_JZVD.state = 1;
            } else {
                ON_PLAY_PAUSE_TMP_STATE = i;
                jzvd.onStatePause();
                CURRENT_JZVD.mediaInterface.pause();
            }
        }
    }

    public static void startFullscreenDirectly(Context context, Class _class, String url, String title) {
        startFullscreenDirectly(context, _class, new JZDataSource(url, title));
    }

    public static void startFullscreenDirectly(Context context, Class _class, JZDataSource jzDataSource) {
        JZUtils.hideStatusBar(context);
        JZUtils.setRequestedOrientation(context, FULLSCREEN_ORIENTATION);
        JZUtils.hideSystemUI(context);
        ViewGroup viewGroup = (ViewGroup) JZUtils.scanForActivity(context).getWindow().getDecorView();
        try {
            Jzvd jzvd = (Jzvd) _class.getConstructor(Context.class).newInstance(context);
            viewGroup.addView(jzvd, new FrameLayout.LayoutParams(-1, -1));
            jzvd.setUp(jzDataSource, 1);
            jzvd.startVideo();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void releaseAllVideos() {
        Log.d(TAG, "releaseAllVideos");
        Jzvd jzvd = CURRENT_JZVD;
        if (jzvd != null) {
            jzvd.reset();
            CURRENT_JZVD = null;
        }
        CONTAINER_LIST.clear();
    }

    public static boolean backPress() {
        Jzvd jzvd;
        Jzvd jzvd2;
        Log.i(TAG, "backPress");
        if (CONTAINER_LIST.size() != 0 && (jzvd2 = CURRENT_JZVD) != null) {
            jzvd2.gotoNormalScreen();
            return true;
        }
        if (CONTAINER_LIST.size() != 0 || (jzvd = CURRENT_JZVD) == null || jzvd.screen == 0) {
            return false;
        }
        jzvd.clearFloatScreen();
        return true;
    }

    public static void setCurrentJzvd(Jzvd jzvd) {
        Jzvd jzvd2 = CURRENT_JZVD;
        if (jzvd2 != null) {
            jzvd2.reset();
        }
        CURRENT_JZVD = jzvd;
    }

    public static void setTextureViewRotation(int rotation) {
        JZTextureView jZTextureView;
        Jzvd jzvd = CURRENT_JZVD;
        if (jzvd == null || (jZTextureView = jzvd.textureView) == null) {
            return;
        }
        jZTextureView.setRotation(rotation);
    }

    public static void setVideoImageDisplayType(int type) {
        JZTextureView jZTextureView;
        VIDEO_IMAGE_DISPLAY_TYPE = type;
        Jzvd jzvd = CURRENT_JZVD;
        if (jzvd == null || (jZTextureView = jzvd.textureView) == null) {
            return;
        }
        jZTextureView.requestLayout();
    }

    public void init(Context context) {
        View.inflate(context, getLayoutId(), this);
        this.jzvdContext = context;
        this.startButton = (ImageView) findViewById(R$id.start);
        this.fullscreenButton = (ImageView) findViewById(R$id.fullscreen);
        this.progressBar = (SeekBar) findViewById(R$id.bottom_seek_progress);
        this.currentTimeTextView = (TextView) findViewById(R$id.current);
        this.totalTimeTextView = (TextView) findViewById(R$id.total);
        this.bottomContainer = (ViewGroup) findViewById(R$id.layout_bottom);
        this.textureViewContainer = (ViewGroup) findViewById(R$id.surface_container);
        this.topContainer = (ViewGroup) findViewById(R$id.layout_top);
        if (this.startButton == null) {
            this.startButton = new ImageView(context);
        }
        if (this.fullscreenButton == null) {
            this.fullscreenButton = new ImageView(context);
        }
        if (this.progressBar == null) {
            this.progressBar = new SeekBar(context);
        }
        if (this.currentTimeTextView == null) {
            this.currentTimeTextView = new TextView(context);
        }
        if (this.totalTimeTextView == null) {
            this.totalTimeTextView = new TextView(context);
        }
        if (this.bottomContainer == null) {
            this.bottomContainer = new LinearLayout(context);
        }
        if (this.textureViewContainer == null) {
            this.textureViewContainer = new FrameLayout(context);
        }
        if (this.topContainer == null) {
            this.topContainer = new RelativeLayout(context);
        }
        this.startButton.setOnClickListener(this);
        this.fullscreenButton.setOnClickListener(this);
        this.progressBar.setOnSeekBarChangeListener(this);
        this.bottomContainer.setOnClickListener(this);
        this.textureViewContainer.setOnClickListener(this);
        this.textureViewContainer.setOnTouchListener(this);
        this.mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        this.mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        this.state = -1;
    }

    public void setUp(String url, String title) {
        setUp(new JZDataSource(url, title), 0);
    }

    public void setUp(String url, String title, int screen) {
        setUp(new JZDataSource(url, title), screen);
    }

    public void setUp(JZDataSource jzDataSource, int screen) {
        setUp(jzDataSource, screen, JZMediaSystem.class);
    }

    public void setUp(String url, String title, int screen, Class mediaInterfaceClass) {
        setUp(new JZDataSource(url, title), screen, mediaInterfaceClass);
    }

    public void setUp(JZDataSource jzDataSource, int screen, Class mediaInterfaceClass) {
        this.jzDataSource = jzDataSource;
        this.screen = screen;
        onStateNormal();
        this.mediaInterfaceClass = mediaInterfaceClass;
    }

    public void setMediaInterface(Class mediaInterfaceClass) {
        reset();
        this.mediaInterfaceClass = mediaInterfaceClass;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        int id = v.getId();
        if (id == R$id.start) {
            clickStart();
        } else if (id == R$id.fullscreen) {
            clickFullscreen();
        }
    }

    protected void clickFullscreen() {
        Log.i(TAG, "onClick fullscreen [" + hashCode() + "] ");
        if (this.state == 7) {
            return;
        }
        if (this.screen == 1) {
            backPress();
            return;
        }
        Log.d(TAG, "toFullscreenActivity [" + hashCode() + "] ");
        gotoFullscreen();
    }

    protected void clickStart() {
        Log.i(TAG, "onClick start [" + hashCode() + "] ");
        JZDataSource jZDataSource = this.jzDataSource;
        if (jZDataSource == null || jZDataSource.urlsMap.isEmpty() || this.jzDataSource.getCurrentUrl() == null) {
            Toast.makeText(getContext(), getResources().getString(R$string.no_url), 0).show();
            return;
        }
        int i = this.state;
        if (i == 0) {
            if (!this.jzDataSource.getCurrentUrl().toString().startsWith("file") && !this.jzDataSource.getCurrentUrl().toString().startsWith("/") && !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                showWifiDialog();
                return;
            } else {
                startVideo();
                return;
            }
        }
        if (i == 5) {
            Log.d(TAG, "pauseVideo [" + hashCode() + "] ");
            this.mediaInterface.pause();
            onStatePause();
            return;
        }
        if (i == 6) {
            this.mediaInterface.start();
            onStatePlaying();
        } else if (i == 7) {
            startVideo();
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (v.getId() != R$id.surface_container) {
            return false;
        }
        int action = event.getAction();
        if (action == 0) {
            touchActionDown(x, y);
            return false;
        }
        if (action == 1) {
            touchActionUp();
            return false;
        }
        if (action != 2) {
            return false;
        }
        touchActionMove(x, y);
        return false;
    }

    protected void touchActionUp() {
        Log.i(TAG, "onTouch surfaceContainer actionUp [" + hashCode() + "] ");
        this.mTouchingProgressBar = false;
        dismissProgressDialog();
        dismissVolumeDialog();
        dismissBrightnessDialog();
        if (this.mChangePosition) {
            this.mediaInterface.seekTo(this.mSeekTimePosition);
            long duration = getDuration();
            long j = this.mSeekTimePosition * 100;
            if (duration == 0) {
                duration = 1;
            }
            this.progressBar.setProgress((int) (j / duration));
        }
        startProgressTimer();
    }

    protected void touchActionMove(float x, float y) {
        Log.i(TAG, "onTouch surfaceContainer actionMove [" + hashCode() + "] ");
        float f = x - this.mDownX;
        float f2 = y - this.mDownY;
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        if (this.screen == 1) {
            if (this.mDownX > JZUtils.getScreenWidth(getContext()) || this.mDownY < JZUtils.getStatusBarHeight(getContext())) {
                return;
            }
            if (!this.mChangePosition && !this.mChangeVolume && !this.mChangeBrightness && (abs > 80.0f || abs2 > 80.0f)) {
                cancelProgressTimer();
                if (abs >= 80.0f) {
                    if (this.state != 8) {
                        this.mChangePosition = true;
                        this.mGestureDownPosition = getCurrentPositionWhenPlaying();
                    }
                } else if (this.mDownX < this.mScreenHeight * 0.5f) {
                    this.mChangeBrightness = true;
                    float f3 = JZUtils.getWindow(getContext()).getAttributes().screenBrightness;
                    if (f3 < 0.0f) {
                        try {
                            this.mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), "screen_brightness");
                            Log.i(TAG, "current system brightness: " + this.mGestureDownBrightness);
                        } catch (Settings.SettingNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        this.mGestureDownBrightness = f3 * 255.0f;
                        Log.i(TAG, "current activity brightness: " + this.mGestureDownBrightness);
                    }
                } else {
                    this.mChangeVolume = true;
                    this.mGestureDownVolume = this.mAudioManager.getStreamVolume(3);
                }
            }
        }
        if (this.mChangePosition) {
            long duration = getDuration();
            if (PROGRESS_DRAG_RATE <= 0.0f) {
                Log.d(TAG, "error PROGRESS_DRAG_RATE value");
                PROGRESS_DRAG_RATE = 1.0f;
            }
            long j = (int) (this.mGestureDownPosition + ((duration * f) / (this.mScreenWidth * PROGRESS_DRAG_RATE)));
            this.mSeekTimePosition = j;
            if (j > duration) {
                this.mSeekTimePosition = duration;
            }
            showProgressDialog(f, JZUtils.stringForTime(this.mSeekTimePosition), this.mSeekTimePosition, JZUtils.stringForTime(duration), duration);
        }
        if (this.mChangeVolume) {
            f2 = -f2;
            this.mAudioManager.setStreamVolume(3, this.mGestureDownVolume + ((int) (((this.mAudioManager.getStreamMaxVolume(3) * f2) * 3.0f) / this.mScreenHeight)), 0);
            showVolumeDialog(-f2, (int) (((this.mGestureDownVolume * 100) / r14) + (((f2 * 3.0f) * 100.0f) / this.mScreenHeight)));
        }
        if (this.mChangeBrightness) {
            float f4 = -f2;
            WindowManager.LayoutParams attributes = JZUtils.getWindow(getContext()).getAttributes();
            float f5 = this.mGestureDownBrightness;
            float f6 = (int) (((f4 * 255.0f) * 3.0f) / this.mScreenHeight);
            if ((f5 + f6) / 255.0f >= 1.0f) {
                attributes.screenBrightness = 1.0f;
            } else if ((f5 + f6) / 255.0f <= 0.0f) {
                attributes.screenBrightness = 0.01f;
            } else {
                attributes.screenBrightness = (f5 + f6) / 255.0f;
            }
            JZUtils.getWindow(getContext()).setAttributes(attributes);
            showBrightnessDialog((int) (((this.mGestureDownBrightness * 100.0f) / 255.0f) + (((f4 * 3.0f) * 100.0f) / this.mScreenHeight)));
        }
    }

    protected void touchActionDown(float x, float y) {
        Log.i(TAG, "onTouch surfaceContainer actionDown [" + hashCode() + "] ");
        this.mTouchingProgressBar = true;
        this.mDownX = x;
        this.mDownY = y;
        this.mChangeVolume = false;
        this.mChangePosition = false;
        this.mChangeBrightness = false;
    }

    public void onStateNormal() {
        Log.i(TAG, "onStateNormal  [" + hashCode() + "] ");
        this.state = 0;
        cancelProgressTimer();
        JZMediaInterface jZMediaInterface = this.mediaInterface;
        if (jZMediaInterface != null) {
            jZMediaInterface.release();
        }
    }

    public void onStatePreparing() {
        Log.i(TAG, "onStatePreparing  [" + hashCode() + "] ");
        this.state = 1;
        resetProgressAndTime();
    }

    public void onStatePreparingPlaying() {
        Log.i(TAG, "onStatePreparingPlaying  [" + hashCode() + "] ");
        this.state = 3;
    }

    public void onStatePreparingChangeUrl() {
        Log.i(TAG, "onStatePreparingChangeUrl  [" + hashCode() + "] ");
        this.state = 2;
        releaseAllVideos();
        startVideo();
    }

    public void changeUrl(JZDataSource jzDataSource, long seekToInAdvance) {
        this.jzDataSource = jzDataSource;
        this.seekToInAdvance = seekToInAdvance;
        onStatePreparingChangeUrl();
    }

    public void onPrepared() {
        Log.i(TAG, "onPrepared  [" + hashCode() + "] ");
        this.state = 4;
        if (!this.preloading) {
            this.mediaInterface.start();
            this.preloading = false;
        }
        if (this.jzDataSource.getCurrentUrl().toString().toLowerCase().contains("mp3") || this.jzDataSource.getCurrentUrl().toString().toLowerCase().contains("wma") || this.jzDataSource.getCurrentUrl().toString().toLowerCase().contains("aac") || this.jzDataSource.getCurrentUrl().toString().toLowerCase().contains("m4a") || this.jzDataSource.getCurrentUrl().toString().toLowerCase().contains("wav")) {
            onStatePlaying();
        }
    }

    public void startPreloading() {
        this.preloading = true;
        startVideo();
    }

    public void startVideoAfterPreloading() {
        if (this.state == 4) {
            this.mediaInterface.start();
        } else {
            this.preloading = false;
            startVideo();
        }
    }

    public void onStatePlaying() {
        Log.i(TAG, "onStatePlaying  [" + hashCode() + "] ");
        if (this.state == 4) {
            Log.d(TAG, "onStatePlaying:STATE_PREPARED ");
            AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
            this.mAudioManager = audioManager;
            audioManager.requestAudioFocus(onAudioFocusChangeListener, 3, 2);
            long j = this.seekToInAdvance;
            if (j != 0) {
                this.mediaInterface.seekTo(j);
                this.seekToInAdvance = 0L;
            } else {
                long savedProgress = JZUtils.getSavedProgress(getContext(), this.jzDataSource.getCurrentUrl());
                if (savedProgress != 0) {
                    this.mediaInterface.seekTo(savedProgress);
                }
            }
        }
        this.state = 5;
        startProgressTimer();
    }

    public void onStatePause() {
        Log.i(TAG, "onStatePause  [" + hashCode() + "] ");
        this.state = 6;
        startProgressTimer();
    }

    public void onStateError() {
        Log.i(TAG, "onStateError  [" + hashCode() + "] ");
        this.state = 8;
        cancelProgressTimer();
    }

    public void onStateAutoComplete() {
        Log.i(TAG, "onStateAutoComplete  [" + hashCode() + "] ");
        this.state = 7;
        cancelProgressTimer();
        this.progressBar.setProgress(100);
        this.currentTimeTextView.setText(this.totalTimeTextView.getText());
    }

    public void onInfo(int what, int extra) {
        Log.d(TAG, "onInfo what - " + what + " extra - " + extra);
        if (what == 3) {
            Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START");
            int i = this.state;
            if (i == 4 || i == 2 || i == 3) {
                onStatePlaying();
                return;
            }
            return;
        }
        if (what == 701) {
            Log.d(TAG, "MEDIA_INFO_BUFFERING_START");
            backUpBufferState = this.state;
            setState(3);
        } else if (what == 702) {
            Log.d(TAG, "MEDIA_INFO_BUFFERING_END");
            int i2 = backUpBufferState;
            if (i2 != -1) {
                setState(i2);
                backUpBufferState = -1;
            }
        }
    }

    public void onError(int what, int extra) {
        Log.e(TAG, "onError " + what + " - " + extra + " [" + hashCode() + "] ");
        if (what == 38 || extra == -38 || what == -38 || extra == 38 || extra == -19) {
            return;
        }
        onStateError();
        this.mediaInterface.release();
    }

    public void onCompletion() {
        Runtime.getRuntime().gc();
        Log.i(TAG, "onAutoCompletion  [" + hashCode() + "] ");
        cancelProgressTimer();
        dismissBrightnessDialog();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateAutoComplete();
        this.mediaInterface.release();
        JZUtils.scanForActivity(getContext()).getWindow().clearFlags(128);
        JZUtils.saveProgress(getContext(), this.jzDataSource.getCurrentUrl(), 0L);
        if (this.screen == 1) {
            if (CONTAINER_LIST.size() == 0) {
                clearFloatScreen();
            } else {
                gotoNormalCompletion();
            }
        }
    }

    public void gotoNormalCompletion() {
        this.gobakFullscreenTime = System.currentTimeMillis();
        ((ViewGroup) JZUtils.scanForActivity(this.jzvdContext).getWindow().getDecorView()).removeView(this);
        this.textureViewContainer.removeView(this.textureView);
        CONTAINER_LIST.getLast().removeViewAt(this.blockIndex);
        CONTAINER_LIST.getLast().addView(this, this.blockIndex, this.blockLayoutParams);
        CONTAINER_LIST.pop();
        setScreenNormal();
        JZUtils.showStatusBar(this.jzvdContext);
        JZUtils.setRequestedOrientation(this.jzvdContext, NORMAL_ORIENTATION);
        JZUtils.showSystemUI(this.jzvdContext);
    }

    public void reset() {
        Log.i(TAG, "reset  [" + hashCode() + "] ");
        int i = this.state;
        if (i == 5 || i == 6) {
            JZUtils.saveProgress(getContext(), this.jzDataSource.getCurrentUrl(), getCurrentPositionWhenPlaying());
        }
        cancelProgressTimer();
        dismissBrightnessDialog();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateNormal();
        this.textureViewContainer.removeAllViews();
        ((AudioManager) getApplicationContext().getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND)).abandonAudioFocus(onAudioFocusChangeListener);
        JZUtils.scanForActivity(getContext()).getWindow().clearFlags(128);
        JZMediaInterface jZMediaInterface = this.mediaInterface;
        if (jZMediaInterface != null) {
            jZMediaInterface.release();
        }
    }

    public void setState(int state) {
        switch (state) {
            case 0:
                onStateNormal();
                break;
            case 1:
                onStatePreparing();
                break;
            case 2:
                onStatePreparingChangeUrl();
                break;
            case 3:
                onStatePreparingPlaying();
                break;
            case 5:
                onStatePlaying();
                break;
            case 6:
                onStatePause();
                break;
            case 7:
                onStateAutoComplete();
                break;
            case 8:
                onStateError();
                break;
        }
    }

    public void setScreen(int screen) {
        if (screen == 0) {
            setScreenNormal();
        } else if (screen == 1) {
            setScreenFullscreen();
        } else {
            if (screen != 2) {
                return;
            }
            setScreenTiny();
        }
    }

    public void startVideo() {
        Log.d(TAG, "startVideo [" + hashCode() + "] ");
        setCurrentJzvd(this);
        try {
            this.mediaInterface = (JZMediaInterface) this.mediaInterfaceClass.getConstructor(Jzvd.class).newInstance(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
        addTextureView();
        JZUtils.scanForActivity(getContext()).getWindow().addFlags(128);
        onStatePreparing();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = this.screen;
        if (i == 1 || i == 2) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (this.widthRatio != 0 && this.heightRatio != 0) {
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int i2 = (int) ((size * this.heightRatio) / this.widthRatio);
            setMeasuredDimension(size, i2);
            getChildAt(0).measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void addTextureView() {
        Log.d(TAG, "addTextureView [" + hashCode() + "] ");
        JZTextureView jZTextureView = this.textureView;
        if (jZTextureView != null) {
            this.textureViewContainer.removeView(jZTextureView);
        }
        JZTextureView jZTextureView2 = new JZTextureView(getContext().getApplicationContext());
        this.textureView = jZTextureView2;
        jZTextureView2.setSurfaceTextureListener(this.mediaInterface);
        this.textureViewContainer.addView(this.textureView, new FrameLayout.LayoutParams(-1, -1, 17));
    }

    public void clearFloatScreen() {
        JZUtils.showStatusBar(getContext());
        JZUtils.setRequestedOrientation(getContext(), NORMAL_ORIENTATION);
        JZUtils.showSystemUI(getContext());
        ((ViewGroup) JZUtils.scanForActivity(getContext()).getWindow().getDecorView()).removeView(this);
        JZMediaInterface jZMediaInterface = this.mediaInterface;
        if (jZMediaInterface != null) {
            jZMediaInterface.release();
        }
        CURRENT_JZVD = null;
    }

    public void onVideoSizeChanged(int width, int height) {
        Log.i(TAG, "onVideoSizeChanged  [" + hashCode() + "] ");
        JZTextureView jZTextureView = this.textureView;
        if (jZTextureView != null) {
            int i = this.videoRotation;
            if (i != 0) {
                jZTextureView.setRotation(i);
            }
            this.textureView.setVideoSize(width, height);
        }
    }

    public void startProgressTimer() {
        Log.i(TAG, "startProgressTimer:  [" + hashCode() + "] ");
        cancelProgressTimer();
        this.UPDATE_PROGRESS_TIMER = new Timer();
        ProgressTimerTask progressTimerTask = new ProgressTimerTask();
        this.mProgressTimerTask = progressTimerTask;
        this.UPDATE_PROGRESS_TIMER.schedule(progressTimerTask, 0L, 300L);
    }

    public void cancelProgressTimer() {
        Timer timer = this.UPDATE_PROGRESS_TIMER;
        if (timer != null) {
            timer.cancel();
        }
        ProgressTimerTask progressTimerTask = this.mProgressTimerTask;
        if (progressTimerTask != null) {
            progressTimerTask.cancel();
        }
    }

    public void onProgress(int progress, long position, long duration) {
        this.mCurrentPosition = position;
        if (!this.mTouchingProgressBar) {
            int i = this.seekToManulPosition;
            if (i == -1) {
                this.progressBar.setProgress(progress);
            } else if (i > progress) {
                return;
            } else {
                this.seekToManulPosition = -1;
            }
        }
        if (position != 0) {
            this.currentTimeTextView.setText(JZUtils.stringForTime(position));
        }
        this.totalTimeTextView.setText(JZUtils.stringForTime(duration));
    }

    public void setBufferProgress(int bufferProgress) {
        this.progressBar.setSecondaryProgress(bufferProgress);
    }

    public void resetProgressAndTime() {
        this.mCurrentPosition = 0L;
        this.progressBar.setProgress(0);
        this.progressBar.setSecondaryProgress(0);
        this.currentTimeTextView.setText(JZUtils.stringForTime(0L));
        this.totalTimeTextView.setText(JZUtils.stringForTime(0L));
    }

    public long getCurrentPositionWhenPlaying() {
        int i = this.state;
        if (i != 5 && i != 6 && i != 3) {
            return 0L;
        }
        try {
            return this.mediaInterface.getCurrentPosition();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public long getDuration() {
        try {
            return this.mediaInterface.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStartTrackingTouch [" + hashCode() + "] ");
        cancelProgressTimer();
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStopTrackingTouch [" + hashCode() + "] ");
        startProgressTimer();
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            parent.requestDisallowInterceptTouchEvent(false);
        }
        int i = this.state;
        if (i == 5 || i == 6) {
            long progress = (seekBar.getProgress() * getDuration()) / 100;
            this.seekToManulPosition = seekBar.getProgress();
            this.mediaInterface.seekTo(progress);
            Log.i(TAG, "seekTo " + progress + " [" + hashCode() + "] ");
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            this.currentTimeTextView.setText(JZUtils.stringForTime((progress * getDuration()) / 100));
        }
    }

    public void cloneAJzvd(ViewGroup vg) {
        try {
            Jzvd jzvd = (Jzvd) getClass().getConstructor(Context.class).newInstance(getContext());
            jzvd.setId(getId());
            jzvd.setMinimumWidth(this.blockWidth);
            jzvd.setMinimumHeight(this.blockHeight);
            vg.addView(jzvd, this.blockIndex, this.blockLayoutParams);
            jzvd.setUp(this.jzDataSource.cloneMe(), 0, this.mediaInterfaceClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
    }

    public void gotoFullscreen() {
        this.gotoFullscreenTime = System.currentTimeMillis();
        ViewGroup viewGroup = (ViewGroup) getParent();
        this.jzvdContext = viewGroup.getContext();
        this.blockLayoutParams = getLayoutParams();
        this.blockIndex = viewGroup.indexOfChild(this);
        this.blockWidth = getWidth();
        this.blockHeight = getHeight();
        viewGroup.removeView(this);
        cloneAJzvd(viewGroup);
        CONTAINER_LIST.add(viewGroup);
        ((ViewGroup) JZUtils.scanForActivity(this.jzvdContext).getWindow().getDecorView()).addView(this, new FrameLayout.LayoutParams(-1, -1));
        setScreenFullscreen();
        JZUtils.hideStatusBar(this.jzvdContext);
        JZUtils.setRequestedOrientation(this.jzvdContext, FULLSCREEN_ORIENTATION);
        JZUtils.hideSystemUI(this.jzvdContext);
    }

    public void gotoNormalScreen() {
        this.gobakFullscreenTime = System.currentTimeMillis();
        ((ViewGroup) JZUtils.scanForActivity(this.jzvdContext).getWindow().getDecorView()).removeView(this);
        CONTAINER_LIST.getLast().removeViewAt(this.blockIndex);
        CONTAINER_LIST.getLast().addView(this, this.blockIndex, this.blockLayoutParams);
        CONTAINER_LIST.pop();
        setScreenNormal();
        JZUtils.showStatusBar(this.jzvdContext);
        JZUtils.setRequestedOrientation(this.jzvdContext, NORMAL_ORIENTATION);
        JZUtils.showSystemUI(this.jzvdContext);
    }

    public void setScreenNormal() {
        this.screen = 0;
    }

    public void setScreenFullscreen() {
        this.screen = 1;
    }

    public void setScreenTiny() {
        this.screen = 2;
    }

    public void autoFullscreen(float x) {
        int i;
        if (CURRENT_JZVD != null) {
            int i2 = this.state;
            if ((i2 != 5 && i2 != 6) || (i = this.screen) == 1 || i == 2) {
                return;
            }
            if (x > 0.0f) {
                JZUtils.setRequestedOrientation(getContext(), 0);
            } else {
                JZUtils.setRequestedOrientation(getContext(), 8);
            }
            gotoFullscreen();
        }
    }

    public void autoQuitFullscreen() {
        if (System.currentTimeMillis() - lastAutoFullscreenTime > 2000 && this.state == 5 && this.screen == 1) {
            lastAutoFullscreenTime = System.currentTimeMillis();
            backPress();
        }
    }

    public Context getApplicationContext() {
        Context applicationContext;
        Context context = getContext();
        return (context == null || (applicationContext = context.getApplicationContext()) == null) ? context : applicationContext;
    }

    public class ProgressTimerTask extends TimerTask {
        public ProgressTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Jzvd jzvd = Jzvd.this;
            int i = jzvd.state;
            if (i == 5 || i == 6 || i == 3) {
                jzvd.post(new Runnable() { // from class: cn.jzvd.Jzvd$ProgressTimerTask$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        Jzvd.ProgressTimerTask.this.lambda$run$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            long currentPositionWhenPlaying = Jzvd.this.getCurrentPositionWhenPlaying();
            long duration = Jzvd.this.getDuration();
            Jzvd.this.onProgress((int) ((100 * currentPositionWhenPlaying) / (duration == 0 ? 1L : duration)), currentPositionWhenPlaying, duration);
        }
    }
}
