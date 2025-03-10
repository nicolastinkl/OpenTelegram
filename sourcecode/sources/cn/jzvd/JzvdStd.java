package cn.jzvd;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class JzvdStd extends Jzvd {
    protected static Timer DISMISS_CONTROL_VIEW_TIMER = null;
    public static int LAST_GET_BATTERYLEVEL_PERCENT = 70;
    public static long LAST_GET_BATTERYLEVEL_TIME;
    public ImageView backButton;
    public BroadcastReceiver battertReceiver;
    public ImageView batteryLevel;
    public LinearLayout batteryTimeLayout;
    public ProgressBar bottomProgressBar;
    public TextView clarity;
    public PopupWindow clarityPopWindow;
    protected ArrayDeque<Runnable> delayTask;
    protected GestureDetector gestureDetector;
    public ProgressBar loadingProgressBar;
    protected Dialog mBrightnessDialog;
    protected ProgressBar mDialogBrightnessProgressBar;
    protected TextView mDialogBrightnessTextView;
    protected ImageView mDialogIcon;
    protected ProgressBar mDialogProgressBar;
    protected TextView mDialogSeekTime;
    protected TextView mDialogTotalTime;
    protected ImageView mDialogVolumeImageView;
    protected ProgressBar mDialogVolumeProgressBar;
    protected TextView mDialogVolumeTextView;
    protected DismissControlViewTimerTask mDismissControlViewTimerTask;
    protected boolean mIsWifi;
    protected Dialog mProgressDialog;
    public TextView mRetryBtn;
    public LinearLayout mRetryLayout;
    protected Dialog mVolumeDialog;
    public ImageView posterImageView;
    public TextView replayTextView;
    public ImageView tinyBackImageView;
    public TextView titleTextView;
    public TextView videoCurrentTime;
    public BroadcastReceiver wifiReceiver;

    public JzvdStd(Context context) {
        super(context);
        this.battertReceiver = new BroadcastReceiver() { // from class: cn.jzvd.JzvdStd.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                    JzvdStd.LAST_GET_BATTERYLEVEL_PERCENT = (intent.getIntExtra("level", 0) * 100) / intent.getIntExtra("scale", 100);
                    JzvdStd.this.setBatteryLevel();
                    try {
                        JzvdStd jzvdStd = JzvdStd.this;
                        jzvdStd.jzvdContext.unregisterReceiver(jzvdStd.battertReceiver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.wifiReceiver = new BroadcastReceiver() { // from class: cn.jzvd.JzvdStd.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    boolean isWifiConnected = JZUtils.isWifiConnected(context2);
                    JzvdStd jzvdStd = JzvdStd.this;
                    if (jzvdStd.mIsWifi == isWifiConnected) {
                        return;
                    }
                    jzvdStd.mIsWifi = isWifiConnected;
                    if (isWifiConnected || Jzvd.WIFI_TIP_DIALOG_SHOWED || jzvdStd.state != 5) {
                        return;
                    }
                    jzvdStd.startButton.performClick();
                    JzvdStd.this.showWifiDialog();
                }
            }
        };
        this.delayTask = new ArrayDeque<>();
        this.gestureDetector = new GestureDetector(getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() { // from class: cn.jzvd.JzvdStd.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                int i = JzvdStd.this.state;
                if (i == 5 || i == 6) {
                    Log.d(Jzvd.TAG, "doublClick [" + hashCode() + "] ");
                    JzvdStd.this.startButton.performClick();
                }
                return super.onDoubleTap(e);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e) {
                JzvdStd jzvdStd = JzvdStd.this;
                if (!jzvdStd.mChangePosition && !jzvdStd.mChangeVolume) {
                    jzvdStd.onClickUiToggle();
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    public JzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.battertReceiver = new BroadcastReceiver() { // from class: cn.jzvd.JzvdStd.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                    JzvdStd.LAST_GET_BATTERYLEVEL_PERCENT = (intent.getIntExtra("level", 0) * 100) / intent.getIntExtra("scale", 100);
                    JzvdStd.this.setBatteryLevel();
                    try {
                        JzvdStd jzvdStd = JzvdStd.this;
                        jzvdStd.jzvdContext.unregisterReceiver(jzvdStd.battertReceiver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.wifiReceiver = new BroadcastReceiver() { // from class: cn.jzvd.JzvdStd.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    boolean isWifiConnected = JZUtils.isWifiConnected(context2);
                    JzvdStd jzvdStd = JzvdStd.this;
                    if (jzvdStd.mIsWifi == isWifiConnected) {
                        return;
                    }
                    jzvdStd.mIsWifi = isWifiConnected;
                    if (isWifiConnected || Jzvd.WIFI_TIP_DIALOG_SHOWED || jzvdStd.state != 5) {
                        return;
                    }
                    jzvdStd.startButton.performClick();
                    JzvdStd.this.showWifiDialog();
                }
            }
        };
        this.delayTask = new ArrayDeque<>();
        this.gestureDetector = new GestureDetector(getContext().getApplicationContext(), new GestureDetector.SimpleOnGestureListener() { // from class: cn.jzvd.JzvdStd.3
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                int i = JzvdStd.this.state;
                if (i == 5 || i == 6) {
                    Log.d(Jzvd.TAG, "doublClick [" + hashCode() + "] ");
                    JzvdStd.this.startButton.performClick();
                }
                return super.onDoubleTap(e);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e) {
                JzvdStd jzvdStd = JzvdStd.this;
                if (!jzvdStd.mChangePosition && !jzvdStd.mChangeVolume) {
                    jzvdStd.onClickUiToggle();
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override // cn.jzvd.Jzvd
    public void init(Context context) {
        super.init(context);
        this.batteryTimeLayout = (LinearLayout) findViewById(R$id.battery_time_layout);
        this.bottomProgressBar = (ProgressBar) findViewById(R$id.bottom_progress);
        this.titleTextView = (TextView) findViewById(R$id.title);
        this.backButton = (ImageView) findViewById(R$id.back);
        this.posterImageView = (ImageView) findViewById(R$id.poster);
        this.loadingProgressBar = (ProgressBar) findViewById(R$id.loading);
        this.tinyBackImageView = (ImageView) findViewById(R$id.back_tiny);
        this.batteryLevel = (ImageView) findViewById(R$id.battery_level);
        this.videoCurrentTime = (TextView) findViewById(R$id.video_current_time);
        this.replayTextView = (TextView) findViewById(R$id.replay_text);
        this.clarity = (TextView) findViewById(R$id.clarity);
        this.mRetryBtn = (TextView) findViewById(R$id.retry_btn);
        this.mRetryLayout = (LinearLayout) findViewById(R$id.retry_layout);
        if (this.batteryTimeLayout == null) {
            this.batteryTimeLayout = new LinearLayout(context);
        }
        if (this.bottomProgressBar == null) {
            this.bottomProgressBar = new ProgressBar(context);
        }
        if (this.titleTextView == null) {
            this.titleTextView = new TextView(context);
        }
        if (this.backButton == null) {
            this.backButton = new ImageView(context);
        }
        if (this.posterImageView == null) {
            this.posterImageView = new ImageView(context);
        }
        if (this.loadingProgressBar == null) {
            this.loadingProgressBar = new ProgressBar(context);
        }
        if (this.tinyBackImageView == null) {
            this.tinyBackImageView = new ImageView(context);
        }
        if (this.batteryLevel == null) {
            this.batteryLevel = new ImageView(context);
        }
        if (this.videoCurrentTime == null) {
            this.videoCurrentTime = new TextView(context);
        }
        if (this.replayTextView == null) {
            this.replayTextView = new TextView(context);
        }
        if (this.clarity == null) {
            this.clarity = new TextView(context);
        }
        if (this.mRetryBtn == null) {
            this.mRetryBtn = new TextView(context);
        }
        if (this.mRetryLayout == null) {
            this.mRetryLayout = new LinearLayout(context);
        }
        this.posterImageView.setOnClickListener(this);
        this.backButton.setOnClickListener(this);
        this.tinyBackImageView.setOnClickListener(this);
        this.clarity.setOnClickListener(this);
        this.mRetryBtn.setOnClickListener(this);
    }

    @Override // cn.jzvd.Jzvd
    public void setUp(JZDataSource jzDataSource, int screen, Class mediaInterfaceClass) {
        if (System.currentTimeMillis() - this.gobakFullscreenTime >= 200 && System.currentTimeMillis() - this.gotoFullscreenTime >= 200) {
            super.setUp(jzDataSource, screen, mediaInterfaceClass);
            this.titleTextView.setText(jzDataSource.title);
            setScreen(screen);
        }
    }

    @Override // cn.jzvd.Jzvd
    public void changeUrl(JZDataSource jzDataSource, long seekToInAdvance) {
        super.changeUrl(jzDataSource, seekToInAdvance);
        this.titleTextView.setText(jzDataSource.title);
    }

    public void changeStartButtonSize(int size) {
        ViewGroup.LayoutParams layoutParams = this.startButton.getLayoutParams();
        layoutParams.height = size;
        layoutParams.width = size;
        ViewGroup.LayoutParams layoutParams2 = this.loadingProgressBar.getLayoutParams();
        layoutParams2.height = size;
        layoutParams2.width = size;
    }

    @Override // cn.jzvd.Jzvd
    public int getLayoutId() {
        return R$layout.jz_layout_std;
    }

    @Override // cn.jzvd.Jzvd
    public void onStateNormal() {
        super.onStateNormal();
        changeUiToNormal();
    }

    @Override // cn.jzvd.Jzvd
    public void onStatePreparing() {
        super.onStatePreparing();
        changeUiToPreparing();
    }

    @Override // cn.jzvd.Jzvd
    public void onStatePreparingPlaying() {
        super.onStatePreparingPlaying();
        changeUIToPreparingPlaying();
    }

    @Override // cn.jzvd.Jzvd
    public void onStatePreparingChangeUrl() {
        super.onStatePreparingChangeUrl();
        changeUIToPreparingChangeUrl();
    }

    @Override // cn.jzvd.Jzvd
    public void onStatePlaying() {
        super.onStatePlaying();
        changeUiToPlayingClear();
    }

    @Override // cn.jzvd.Jzvd
    public void onStatePause() {
        super.onStatePause();
        changeUiToPauseShow();
        cancelDismissControlViewTimer();
    }

    @Override // cn.jzvd.Jzvd
    public void onStateError() {
        super.onStateError();
        changeUiToError();
    }

    @Override // cn.jzvd.Jzvd
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        changeUiToComplete();
        cancelDismissControlViewTimer();
        this.bottomProgressBar.setProgress(100);
    }

    @Override // cn.jzvd.Jzvd
    public void startVideo() {
        super.startVideo();
        registerWifiListener(getApplicationContext());
    }

    @Override // cn.jzvd.Jzvd, android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if (id == R$id.surface_container) {
            if (event.getAction() == 1) {
                startDismissControlViewTimer();
                if (this.mChangePosition) {
                    long duration = getDuration();
                    long j = this.mSeekTimePosition * 100;
                    if (duration == 0) {
                        duration = 1;
                    }
                    this.bottomProgressBar.setProgress((int) (j / duration));
                }
            }
            this.gestureDetector.onTouchEvent(event);
        } else if (id == R$id.bottom_seek_progress) {
            int action = event.getAction();
            if (action == 0) {
                cancelDismissControlViewTimer();
            } else if (action == 1) {
                startDismissControlViewTimer();
            }
        }
        return super.onTouch(v, event);
    }

    @Override // cn.jzvd.Jzvd, android.view.View.OnClickListener
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R$id.poster) {
            clickPoster();
            return;
        }
        if (id == R$id.surface_container) {
            clickSurfaceContainer();
            PopupWindow popupWindow = this.clarityPopWindow;
            if (popupWindow != null) {
                popupWindow.dismiss();
                return;
            }
            return;
        }
        if (id == R$id.back) {
            clickBack();
            return;
        }
        if (id == R$id.back_tiny) {
            clickBackTiny();
        } else if (id == R$id.clarity) {
            clickClarity();
        } else if (id == R$id.retry_btn) {
            clickRetryBtn();
        }
    }

    protected void clickRetryBtn() {
        if (this.jzDataSource.urlsMap.isEmpty() || this.jzDataSource.getCurrentUrl() == null) {
            Toast.makeText(this.jzvdContext, getResources().getString(R$string.no_url), 0).show();
            return;
        }
        if (!this.jzDataSource.getCurrentUrl().toString().startsWith("file") && !this.jzDataSource.getCurrentUrl().toString().startsWith("/") && !JZUtils.isWifiConnected(this.jzvdContext) && !Jzvd.WIFI_TIP_DIALOG_SHOWED) {
            showWifiDialog();
        } else {
            this.seekToInAdvance = this.mCurrentPosition;
            startVideo();
        }
    }

    protected void clickClarity() {
        onCLickUiToggleToClear();
        final LinearLayout linearLayout = (LinearLayout) ((LayoutInflater) this.jzvdContext.getSystemService("layout_inflater")).inflate(R$layout.jz_layout_clarity, (ViewGroup) null);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: cn.jzvd.JzvdStd$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                JzvdStd.this.lambda$clickClarity$0(linearLayout, view);
            }
        };
        for (int i = 0; i < this.jzDataSource.urlsMap.size(); i++) {
            String keyFromDataSource = this.jzDataSource.getKeyFromDataSource(i);
            TextView textView = (TextView) View.inflate(this.jzvdContext, R$layout.jz_layout_clarity_item, null);
            textView.setText(keyFromDataSource);
            textView.setTag(Integer.valueOf(i));
            linearLayout.addView(textView, i);
            textView.setOnClickListener(onClickListener);
            if (i == this.jzDataSource.currentUrlIndex) {
                textView.setTextColor(Color.parseColor("#fff85959"));
            }
        }
        PopupWindow popupWindow = new PopupWindow((View) linearLayout, JZUtils.dip2px(this.jzvdContext, 240.0f), -1, true);
        this.clarityPopWindow = popupWindow;
        popupWindow.setContentView(linearLayout);
        this.clarityPopWindow.setAnimationStyle(R$style.pop_animation);
        this.clarityPopWindow.showAtLocation(this.textureViewContainer, 8388613, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clickClarity$0(LinearLayout linearLayout, View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        JZDataSource jZDataSource = this.jzDataSource;
        jZDataSource.currentUrlIndex = intValue;
        changeUrl(jZDataSource, getCurrentPositionWhenPlaying());
        this.clarity.setText(this.jzDataSource.getCurrentKey().toString());
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (i == this.jzDataSource.currentUrlIndex) {
                ((TextView) linearLayout.getChildAt(i)).setTextColor(Color.parseColor("#fff85959"));
            } else {
                ((TextView) linearLayout.getChildAt(i)).setTextColor(Color.parseColor("#ffffff"));
            }
        }
        PopupWindow popupWindow = this.clarityPopWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    protected void clickBackTiny() {
        clearFloatScreen();
    }

    protected void clickBack() {
        Jzvd.backPress();
    }

    protected void clickSurfaceContainer() {
        startDismissControlViewTimer();
    }

    protected void clickPoster() {
        JZDataSource jZDataSource = this.jzDataSource;
        if (jZDataSource == null || jZDataSource.urlsMap.isEmpty() || this.jzDataSource.getCurrentUrl() == null) {
            Toast.makeText(this.jzvdContext, getResources().getString(R$string.no_url), 0).show();
            return;
        }
        int i = this.state;
        if (i != 0) {
            if (i == 7) {
                onClickUiToggle();
            }
        } else if (!this.jzDataSource.getCurrentUrl().toString().startsWith("file") && !this.jzDataSource.getCurrentUrl().toString().startsWith("/") && !JZUtils.isWifiConnected(this.jzvdContext) && !Jzvd.WIFI_TIP_DIALOG_SHOWED) {
            showWifiDialog();
        } else {
            startVideo();
        }
    }

    @Override // cn.jzvd.Jzvd
    public void setScreenNormal() {
        super.setScreenNormal();
        this.fullscreenButton.setImageResource(R$drawable.jz_enlarge);
        this.backButton.setVisibility(8);
        this.tinyBackImageView.setVisibility(4);
        changeStartButtonSize((int) getResources().getDimension(R$dimen.jz_start_button_w_h_normal));
        this.batteryTimeLayout.setVisibility(8);
        this.clarity.setVisibility(8);
    }

    @Override // cn.jzvd.Jzvd
    public void setScreenFullscreen() {
        super.setScreenFullscreen();
        this.fullscreenButton.setImageResource(R$drawable.jz_shrink);
        this.backButton.setVisibility(0);
        this.tinyBackImageView.setVisibility(4);
        this.batteryTimeLayout.setVisibility(0);
        if (this.jzDataSource.urlsMap.size() == 1) {
            this.clarity.setVisibility(8);
        } else {
            this.clarity.setText(this.jzDataSource.getCurrentKey().toString());
            this.clarity.setVisibility(0);
        }
        changeStartButtonSize((int) getResources().getDimension(R$dimen.jz_start_button_w_h_fullscreen));
        setSystemTimeAndBattery();
    }

    @Override // cn.jzvd.Jzvd
    public void setScreenTiny() {
        super.setScreenTiny();
        this.tinyBackImageView.setVisibility(0);
        setAllControlsVisiblity(4, 4, 4, 4, 4, 4, 4);
        this.batteryTimeLayout.setVisibility(8);
        this.clarity.setVisibility(8);
    }

    @Override // cn.jzvd.Jzvd
    public void showWifiDialog() {
        super.showWifiDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this.jzvdContext);
        builder.setMessage(getResources().getString(R$string.tips_not_wifi));
        builder.setPositiveButton(getResources().getString(R$string.tips_not_wifi_confirm), new DialogInterface.OnClickListener() { // from class: cn.jzvd.JzvdStd$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                JzvdStd.this.lambda$showWifiDialog$1(dialogInterface, i);
            }
        });
        builder.setNegativeButton(getResources().getString(R$string.tips_not_wifi_cancel), new DialogInterface.OnClickListener() { // from class: cn.jzvd.JzvdStd$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                JzvdStd.this.lambda$showWifiDialog$2(dialogInterface, i);
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: cn.jzvd.JzvdStd.4
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                Jzvd.releaseAllVideos();
                JzvdStd.this.clearFloatScreen();
            }
        });
        builder.create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showWifiDialog$1(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Jzvd.WIFI_TIP_DIALOG_SHOWED = true;
        if (this.state == 6) {
            this.startButton.performClick();
        } else {
            startVideo();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showWifiDialog$2(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        Jzvd.releaseAllVideos();
        clearFloatScreen();
    }

    @Override // cn.jzvd.Jzvd, android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
        super.onStartTrackingTouch(seekBar);
        cancelDismissControlViewTimer();
    }

    @Override // cn.jzvd.Jzvd, android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
        super.onStopTrackingTouch(seekBar);
        startDismissControlViewTimer();
    }

    public void onClickUiToggle() {
        if (this.bottomContainer.getVisibility() != 0) {
            setSystemTimeAndBattery();
            this.clarity.setText(this.jzDataSource.getCurrentKey().toString());
        }
        int i = this.state;
        if (i == 1) {
            changeUiToPreparing();
            if (this.bottomContainer.getVisibility() == 0) {
                return;
            }
            setSystemTimeAndBattery();
            return;
        }
        if (i == 5) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPlayingClear();
                return;
            } else {
                changeUiToPlayingShow();
                return;
            }
        }
        if (i == 6) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPauseClear();
            } else {
                changeUiToPauseShow();
            }
        }
    }

    public void setSystemTimeAndBattery() {
        this.videoCurrentTime.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        if (System.currentTimeMillis() - LAST_GET_BATTERYLEVEL_TIME > 30000) {
            LAST_GET_BATTERYLEVEL_TIME = System.currentTimeMillis();
            this.jzvdContext.registerReceiver(this.battertReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } else {
            setBatteryLevel();
        }
    }

    public void setBatteryLevel() {
        int i = LAST_GET_BATTERYLEVEL_PERCENT;
        if (i < 15) {
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_10);
            return;
        }
        if (i >= 15 && i < 40) {
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_30);
            return;
        }
        if (i >= 40 && i < 60) {
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_50);
            return;
        }
        if (i >= 60 && i < 80) {
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_70);
            return;
        }
        if (i >= 80 && i < 95) {
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_90);
        } else {
            if (i < 95 || i > 100) {
                return;
            }
            this.batteryLevel.setBackgroundResource(R$drawable.jz_battery_level_100);
        }
    }

    public void onCLickUiToggleToClear() {
        int i = this.state;
        if (i == 1) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPreparing();
            }
        } else if (i == 5) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPlayingClear();
            }
        } else if (i == 6) {
            if (this.bottomContainer.getVisibility() == 0) {
                changeUiToPauseClear();
            }
        } else if (i == 7 && this.bottomContainer.getVisibility() == 0) {
            changeUiToComplete();
        }
    }

    @Override // cn.jzvd.Jzvd
    public void onProgress(int progress, long position, long duration) {
        super.onProgress(progress, position, duration);
        this.bottomProgressBar.setProgress(progress);
    }

    @Override // cn.jzvd.Jzvd
    public void setBufferProgress(int bufferProgress) {
        super.setBufferProgress(bufferProgress);
        this.bottomProgressBar.setSecondaryProgress(bufferProgress);
    }

    @Override // cn.jzvd.Jzvd
    public void resetProgressAndTime() {
        super.resetProgressAndTime();
        this.bottomProgressBar.setProgress(0);
        this.bottomProgressBar.setSecondaryProgress(0);
    }

    public void changeUiToNormal() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
            updateStartImage();
        }
    }

    public void changeUiToPreparing() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(4, 4, 4, 0, 0, 4, 4);
            updateStartImage();
        }
    }

    public void changeUIToPreparingPlaying() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(0, 0, 4, 0, 4, 4, 4);
            updateStartImage();
        }
    }

    public void changeUIToPreparingChangeUrl() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(4, 4, 4, 0, 0, 4, 4);
            updateStartImage();
        }
    }

    public void changeUiToPlayingShow() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
            updateStartImage();
        }
    }

    public void changeUiToPlayingClear() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
        }
    }

    public void changeUiToPauseShow() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(0, 0, 0, 4, 4, 4, 4);
            updateStartImage();
        }
    }

    public void changeUiToPauseClear() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(4, 4, 4, 4, 4, 0, 4);
        }
    }

    public void changeUiToComplete() {
        int i = this.screen;
        if (i == 0 || i == 1) {
            setAllControlsVisiblity(0, 4, 0, 4, 0, 4, 4);
            updateStartImage();
        }
    }

    public void changeUiToError() {
        int i = this.screen;
        if (i == 0) {
            setAllControlsVisiblity(4, 4, 0, 4, 4, 4, 0);
            updateStartImage();
        } else {
            if (i != 1) {
                return;
            }
            setAllControlsVisiblity(0, 4, 0, 4, 4, 4, 0);
            updateStartImage();
        }
    }

    public void setAllControlsVisiblity(int topCon, int bottomCon, int startBtn, int loadingPro, int posterImg, int bottomPro, int retryLayout) {
        this.topContainer.setVisibility(topCon);
        this.bottomContainer.setVisibility(bottomCon);
        this.startButton.setVisibility(startBtn);
        this.loadingProgressBar.setVisibility(loadingPro);
        this.posterImageView.setVisibility(posterImg);
        this.bottomProgressBar.setVisibility(bottomPro);
        this.mRetryLayout.setVisibility(retryLayout);
    }

    public void updateStartImage() {
        int i = this.state;
        if (i == 5) {
            this.startButton.setVisibility(0);
            this.startButton.setImageResource(R$drawable.jz_click_pause_selector);
            this.replayTextView.setVisibility(8);
        } else if (i == 8) {
            this.startButton.setVisibility(4);
            this.replayTextView.setVisibility(8);
        } else if (i == 7) {
            this.startButton.setVisibility(0);
            this.startButton.setImageResource(R$drawable.jz_click_replay_selector);
            this.replayTextView.setVisibility(0);
        } else {
            this.startButton.setImageResource(R$drawable.jz_click_play_selector);
            this.replayTextView.setVisibility(8);
        }
    }

    @Override // cn.jzvd.Jzvd
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {
        super.showProgressDialog(deltaX, seekTime, seekTimePosition, totalTime, totalTimeDuration);
        if (this.mProgressDialog == null) {
            View inflate = LayoutInflater.from(this.jzvdContext).inflate(R$layout.jz_dialog_progress, (ViewGroup) null);
            this.mDialogProgressBar = (ProgressBar) inflate.findViewById(R$id.duration_progressbar);
            this.mDialogSeekTime = (TextView) inflate.findViewById(R$id.tv_current);
            this.mDialogTotalTime = (TextView) inflate.findViewById(R$id.tv_duration);
            this.mDialogIcon = (ImageView) inflate.findViewById(R$id.duration_image_tip);
            this.mProgressDialog = createDialogWithView(inflate);
        }
        if (!this.mProgressDialog.isShowing()) {
            this.mProgressDialog.show();
        }
        this.mDialogSeekTime.setText(seekTime);
        this.mDialogTotalTime.setText(" / " + totalTime);
        this.mDialogProgressBar.setProgress(totalTimeDuration <= 0 ? 0 : (int) ((seekTimePosition * 100) / totalTimeDuration));
        if (deltaX > 0.0f) {
            this.mDialogIcon.setBackgroundResource(R$drawable.jz_forward_icon);
        } else {
            this.mDialogIcon.setBackgroundResource(R$drawable.jz_backward_icon);
        }
        onCLickUiToggleToClear();
    }

    @Override // cn.jzvd.Jzvd
    public void dismissProgressDialog() {
        super.dismissProgressDialog();
        Dialog dialog = this.mProgressDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override // cn.jzvd.Jzvd
    public void showVolumeDialog(float deltaY, int volumePercent) {
        super.showVolumeDialog(deltaY, volumePercent);
        if (this.mVolumeDialog == null) {
            View inflate = LayoutInflater.from(this.jzvdContext).inflate(R$layout.jz_dialog_volume, (ViewGroup) null);
            this.mDialogVolumeImageView = (ImageView) inflate.findViewById(R$id.volume_image_tip);
            this.mDialogVolumeTextView = (TextView) inflate.findViewById(R$id.tv_volume);
            this.mDialogVolumeProgressBar = (ProgressBar) inflate.findViewById(R$id.volume_progressbar);
            this.mVolumeDialog = createDialogWithView(inflate);
        }
        if (!this.mVolumeDialog.isShowing()) {
            this.mVolumeDialog.show();
        }
        if (volumePercent <= 0) {
            this.mDialogVolumeImageView.setBackgroundResource(R$drawable.jz_close_volume);
        } else {
            this.mDialogVolumeImageView.setBackgroundResource(R$drawable.jz_add_volume);
        }
        if (volumePercent > 100) {
            volumePercent = 100;
        } else if (volumePercent < 0) {
            volumePercent = 0;
        }
        this.mDialogVolumeTextView.setText(volumePercent + "%");
        this.mDialogVolumeProgressBar.setProgress(volumePercent);
        onCLickUiToggleToClear();
    }

    @Override // cn.jzvd.Jzvd
    public void dismissVolumeDialog() {
        super.dismissVolumeDialog();
        Dialog dialog = this.mVolumeDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override // cn.jzvd.Jzvd
    public void showBrightnessDialog(int brightnessPercent) {
        super.showBrightnessDialog(brightnessPercent);
        if (this.mBrightnessDialog == null) {
            View inflate = LayoutInflater.from(this.jzvdContext).inflate(R$layout.jz_dialog_brightness, (ViewGroup) null);
            this.mDialogBrightnessTextView = (TextView) inflate.findViewById(R$id.tv_brightness);
            this.mDialogBrightnessProgressBar = (ProgressBar) inflate.findViewById(R$id.brightness_progressbar);
            this.mBrightnessDialog = createDialogWithView(inflate);
        }
        if (!this.mBrightnessDialog.isShowing()) {
            this.mBrightnessDialog.show();
        }
        if (brightnessPercent > 100) {
            brightnessPercent = 100;
        } else if (brightnessPercent < 0) {
            brightnessPercent = 0;
        }
        this.mDialogBrightnessTextView.setText(brightnessPercent + "%");
        this.mDialogBrightnessProgressBar.setProgress(brightnessPercent);
        onCLickUiToggleToClear();
    }

    @Override // cn.jzvd.Jzvd
    public void dismissBrightnessDialog() {
        super.dismissBrightnessDialog();
        Dialog dialog = this.mBrightnessDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public Dialog createDialogWithView(View localView) {
        Dialog dialog = new Dialog(this.jzvdContext, R$style.jz_style_dialog_progress);
        dialog.setContentView(localView);
        Window window = dialog.getWindow();
        window.addFlags(8);
        window.addFlags(32);
        window.addFlags(16);
        window.setLayout(-2, -2);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        window.setAttributes(attributes);
        return dialog;
    }

    public void startDismissControlViewTimer() {
        cancelDismissControlViewTimer();
        DISMISS_CONTROL_VIEW_TIMER = new Timer();
        DismissControlViewTimerTask dismissControlViewTimerTask = new DismissControlViewTimerTask();
        this.mDismissControlViewTimerTask = dismissControlViewTimerTask;
        DISMISS_CONTROL_VIEW_TIMER.schedule(dismissControlViewTimerTask, 2500L);
    }

    public void cancelDismissControlViewTimer() {
        Timer timer = DISMISS_CONTROL_VIEW_TIMER;
        if (timer != null) {
            timer.cancel();
        }
        DismissControlViewTimerTask dismissControlViewTimerTask = this.mDismissControlViewTimerTask;
        if (dismissControlViewTimerTask != null) {
            dismissControlViewTimerTask.cancel();
        }
    }

    @Override // cn.jzvd.Jzvd
    public void onCompletion() {
        super.onCompletion();
        cancelDismissControlViewTimer();
    }

    @Override // cn.jzvd.Jzvd
    public void reset() {
        super.reset();
        cancelDismissControlViewTimer();
        unregisterWifiListener(getApplicationContext());
    }

    public void dissmissControlView() {
        int i = this.state;
        if (i == 0 || i == 8 || i == 7) {
            return;
        }
        post(new Runnable() { // from class: cn.jzvd.JzvdStd$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                JzvdStd.this.lambda$dissmissControlView$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dissmissControlView$3() {
        this.bottomContainer.setVisibility(4);
        this.topContainer.setVisibility(4);
        this.startButton.setVisibility(4);
        if (this.screen != 2) {
            this.bottomProgressBar.setVisibility(0);
        }
    }

    public void registerWifiListener(Context context) {
        if (context == null) {
            return;
        }
        this.mIsWifi = JZUtils.isWifiConnected(context);
        context.registerReceiver(this.wifiReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void unregisterWifiListener(Context context) {
        if (context == null) {
            return;
        }
        try {
            context.unregisterReceiver(this.wifiReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public class DismissControlViewTimerTask extends TimerTask {
        public DismissControlViewTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            JzvdStd.this.dissmissControlView();
        }
    }
}
