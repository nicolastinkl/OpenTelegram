package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.R;

/* loaded from: classes4.dex */
public class VideoTimelinePlayView extends View {
    public static int TYPE_LEFT = 0;
    public static int TYPE_PROGRESS = 2;
    public static int TYPE_RIGHT = 1;
    private static final Object sync = new Object();
    Paint bitmapPaint;
    private int currentMode;
    private AsyncTask<Integer, Integer, Bitmap> currentTask;
    private VideoTimelineViewDelegate delegate;
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private ArrayList<android.graphics.Rect> exclusionRects;
    private android.graphics.Rect exclustionRect;
    private int frameHeight;
    private long frameTimeOffset;
    private int frameWidth;
    private ArrayList<BitmapFrame> frames;
    private int framesToLoad;
    private int lastWidth;
    private float maxProgressDiff;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private float minProgressDiff;
    private Paint paint;
    private Paint paint2;
    private float playProgress;
    private float pressDx;
    private boolean pressedLeft;
    private boolean pressedPlay;
    private boolean pressedRight;
    private float progressLeft;
    private float progressRight;
    private RectF rect3;
    private long videoLength;

    public interface VideoTimelineViewDelegate {
        void didStartDragging(int i);

        void didStopDragging(int i);

        void onLeftProgressChanged(float f);

        void onPlayProgressChanged(float f);

        void onRightProgressChanged(float f);
    }

    public VideoTimelinePlayView(Context context) {
        super(context);
        this.progressRight = 1.0f;
        this.playProgress = 0.5f;
        this.frames = new ArrayList<>();
        this.maxProgressDiff = 1.0f;
        this.minProgressDiff = 0.0f;
        this.rect3 = new RectF();
        this.currentMode = 0;
        this.bitmapPaint = new Paint();
        this.exclusionRects = new ArrayList<>();
        this.exclustionRect = new android.graphics.Rect();
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setColor(-1);
        Paint paint2 = new Paint();
        this.paint2 = paint2;
        paint2.setColor(2130706432);
        Drawable drawable = context.getResources().getDrawable(R.drawable.video_cropleft);
        this.drawableLeft = drawable;
        drawable.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
        Drawable drawable2 = context.getResources().getDrawable(R.drawable.video_cropright);
        this.drawableRight = drawable2;
        drawable2.setColorFilter(new PorterDuffColorFilter(-16777216, PorterDuff.Mode.MULTIPLY));
        this.exclusionRects.add(this.exclustionRect);
    }

    public float getProgress() {
        return this.playProgress;
    }

    public float getLeftProgress() {
        return this.progressLeft;
    }

    public float getRightProgress() {
        return this.progressRight;
    }

    public void setMinProgressDiff(float f) {
        this.minProgressDiff = f;
    }

    public void setMode(int i) {
        if (this.currentMode == i) {
            return;
        }
        this.currentMode = i;
        invalidate();
    }

    public void setMaxProgressDiff(float f) {
        this.maxProgressDiff = f;
        float f2 = this.progressRight;
        float f3 = this.progressLeft;
        if (f2 - f3 > f) {
            this.progressRight = f3 + f;
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (Build.VERSION.SDK_INT >= 29) {
            this.exclustionRect.set(i, 0, i3, getMeasuredHeight());
            setSystemGestureExclusionRects(this.exclusionRects);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(32.0f);
        float f = measuredWidth;
        int dp = ((int) (this.progressLeft * f)) + AndroidUtilities.dp(16.0f);
        int dp2 = ((int) (this.playProgress * f)) + AndroidUtilities.dp(16.0f);
        int dp3 = ((int) (this.progressRight * f)) + AndroidUtilities.dp(16.0f);
        if (motionEvent.getAction() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.mediaMetadataRetriever == null) {
                return false;
            }
            int dp4 = AndroidUtilities.dp(16.0f);
            int dp5 = AndroidUtilities.dp(8.0f);
            if (dp3 != dp && dp2 - dp5 <= x && x <= dp5 + dp2 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate = this.delegate;
                if (videoTimelineViewDelegate != null) {
                    videoTimelineViewDelegate.didStartDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = true;
                this.pressDx = (int) (x - dp2);
                invalidate();
                return true;
            }
            if (dp - dp4 <= x && x <= Math.min(dp + dp4, dp3) && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate2 = this.delegate;
                if (videoTimelineViewDelegate2 != null) {
                    videoTimelineViewDelegate2.didStartDragging(TYPE_LEFT);
                }
                this.pressedLeft = true;
                this.pressDx = (int) (x - dp);
                invalidate();
                return true;
            }
            if (dp3 - dp4 <= x && x <= dp4 + dp3 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate3 = this.delegate;
                if (videoTimelineViewDelegate3 != null) {
                    videoTimelineViewDelegate3.didStartDragging(TYPE_RIGHT);
                }
                this.pressedRight = true;
                this.pressDx = (int) (x - dp3);
                invalidate();
                return true;
            }
            if (dp <= x && x <= dp3 && y >= 0.0f && y <= getMeasuredHeight()) {
                VideoTimelineViewDelegate videoTimelineViewDelegate4 = this.delegate;
                if (videoTimelineViewDelegate4 != null) {
                    videoTimelineViewDelegate4.didStartDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = true;
                float dp6 = (x - AndroidUtilities.dp(16.0f)) / f;
                this.playProgress = dp6;
                VideoTimelineViewDelegate videoTimelineViewDelegate5 = this.delegate;
                if (videoTimelineViewDelegate5 != null) {
                    videoTimelineViewDelegate5.onPlayProgressChanged(dp6);
                }
                this.pressDx = 0.0f;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (this.pressedLeft) {
                VideoTimelineViewDelegate videoTimelineViewDelegate6 = this.delegate;
                if (videoTimelineViewDelegate6 != null) {
                    videoTimelineViewDelegate6.didStopDragging(TYPE_LEFT);
                }
                this.pressedLeft = false;
                return true;
            }
            if (this.pressedRight) {
                VideoTimelineViewDelegate videoTimelineViewDelegate7 = this.delegate;
                if (videoTimelineViewDelegate7 != null) {
                    videoTimelineViewDelegate7.didStopDragging(TYPE_RIGHT);
                }
                this.pressedRight = false;
                return true;
            }
            if (this.pressedPlay) {
                VideoTimelineViewDelegate videoTimelineViewDelegate8 = this.delegate;
                if (videoTimelineViewDelegate8 != null) {
                    videoTimelineViewDelegate8.didStopDragging(TYPE_PROGRESS);
                }
                this.pressedPlay = false;
            }
        } else if (motionEvent.getAction() == 2) {
            if (this.pressedPlay) {
                float dp7 = (((int) (x - this.pressDx)) - AndroidUtilities.dp(16.0f)) / f;
                this.playProgress = dp7;
                float f2 = this.progressLeft;
                if (dp7 < f2) {
                    this.playProgress = f2;
                } else {
                    float f3 = this.progressRight;
                    if (dp7 > f3) {
                        this.playProgress = f3;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate9 = this.delegate;
                if (videoTimelineViewDelegate9 != null) {
                    videoTimelineViewDelegate9.onPlayProgressChanged(this.playProgress);
                }
                invalidate();
                return true;
            }
            if (this.pressedLeft) {
                int i = (int) (x - this.pressDx);
                if (i < AndroidUtilities.dp(16.0f)) {
                    dp3 = AndroidUtilities.dp(16.0f);
                } else if (i <= dp3) {
                    dp3 = i;
                }
                float dp8 = (dp3 - AndroidUtilities.dp(16.0f)) / f;
                this.progressLeft = dp8;
                float f4 = this.progressRight;
                float f5 = f4 - dp8;
                float f6 = this.maxProgressDiff;
                if (f5 > f6) {
                    this.progressRight = dp8 + f6;
                } else {
                    float f7 = this.minProgressDiff;
                    if (f7 != 0.0f && f4 - dp8 < f7) {
                        float f8 = f4 - f7;
                        this.progressLeft = f8;
                        if (f8 < 0.0f) {
                            this.progressLeft = 0.0f;
                        }
                    }
                }
                float f9 = this.progressLeft;
                float f10 = this.playProgress;
                if (f9 > f10) {
                    this.playProgress = f9;
                } else {
                    float f11 = this.progressRight;
                    if (f11 < f10) {
                        this.playProgress = f11;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate10 = this.delegate;
                if (videoTimelineViewDelegate10 != null) {
                    videoTimelineViewDelegate10.onLeftProgressChanged(f9);
                }
                invalidate();
                return true;
            }
            if (this.pressedRight) {
                int i2 = (int) (x - this.pressDx);
                if (i2 >= dp) {
                    dp = i2 > AndroidUtilities.dp(16.0f) + measuredWidth ? measuredWidth + AndroidUtilities.dp(16.0f) : i2;
                }
                float dp9 = (dp - AndroidUtilities.dp(16.0f)) / f;
                this.progressRight = dp9;
                float f12 = this.progressLeft;
                float f13 = dp9 - f12;
                float f14 = this.maxProgressDiff;
                if (f13 > f14) {
                    this.progressLeft = dp9 - f14;
                } else {
                    float f15 = this.minProgressDiff;
                    if (f15 != 0.0f && dp9 - f12 < f15) {
                        float f16 = f12 + f15;
                        this.progressRight = f16;
                        if (f16 > 1.0f) {
                            this.progressRight = 1.0f;
                        }
                    }
                }
                float f17 = this.progressLeft;
                float f18 = this.playProgress;
                if (f17 > f18) {
                    this.playProgress = f17;
                } else {
                    float f19 = this.progressRight;
                    if (f19 < f18) {
                        this.playProgress = f19;
                    }
                }
                VideoTimelineViewDelegate videoTimelineViewDelegate11 = this.delegate;
                if (videoTimelineViewDelegate11 != null) {
                    videoTimelineViewDelegate11.onRightProgressChanged(this.progressRight);
                }
                invalidate();
                return true;
            }
        }
        return true;
    }

    public void setVideoPath(String str, float f, float f2) {
        destroy();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        this.mediaMetadataRetriever = mediaMetadataRetriever;
        this.progressLeft = f;
        this.progressRight = f2;
        try {
            mediaMetadataRetriever.setDataSource(str);
            this.videoLength = Long.parseLong(this.mediaMetadataRetriever.extractMetadata(9));
        } catch (Exception e) {
            FileLog.e(e);
        }
        invalidate();
    }

    public void setRightProgress(float f) {
        this.progressRight = f;
        VideoTimelineViewDelegate videoTimelineViewDelegate = this.delegate;
        if (videoTimelineViewDelegate != null) {
            videoTimelineViewDelegate.didStartDragging(TYPE_RIGHT);
        }
        VideoTimelineViewDelegate videoTimelineViewDelegate2 = this.delegate;
        if (videoTimelineViewDelegate2 != null) {
            videoTimelineViewDelegate2.onRightProgressChanged(this.progressRight);
        }
        VideoTimelineViewDelegate videoTimelineViewDelegate3 = this.delegate;
        if (videoTimelineViewDelegate3 != null) {
            videoTimelineViewDelegate3.didStopDragging(TYPE_RIGHT);
        }
        invalidate();
    }

    public void setDelegate(VideoTimelineViewDelegate videoTimelineViewDelegate) {
        this.delegate = videoTimelineViewDelegate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadFrames(int i) {
        if (this.mediaMetadataRetriever == null) {
            return;
        }
        if (i == 0) {
            this.frameHeight = AndroidUtilities.dp(40.0f);
            this.framesToLoad = Math.max(1, (getMeasuredWidth() - AndroidUtilities.dp(16.0f)) / this.frameHeight);
            this.frameWidth = (int) Math.ceil((getMeasuredWidth() - AndroidUtilities.dp(16.0f)) / this.framesToLoad);
            this.frameTimeOffset = this.videoLength / this.framesToLoad;
        }
        AsyncTask<Integer, Integer, Bitmap> asyncTask = new AsyncTask<Integer, Integer, Bitmap>() { // from class: org.telegram.ui.Components.VideoTimelinePlayView.1
            private int frameNum = 0;

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public Bitmap doInBackground(Integer... numArr) {
                this.frameNum = numArr[0].intValue();
                Bitmap bitmap = null;
                if (isCancelled()) {
                    return null;
                }
                try {
                    Bitmap frameAtTime = VideoTimelinePlayView.this.mediaMetadataRetriever.getFrameAtTime(VideoTimelinePlayView.this.frameTimeOffset * this.frameNum * 1000, 2);
                    try {
                        if (isCancelled()) {
                            return null;
                        }
                        if (frameAtTime == null) {
                            return frameAtTime;
                        }
                        Bitmap createBitmap = Bitmap.createBitmap(VideoTimelinePlayView.this.frameWidth, VideoTimelinePlayView.this.frameHeight, frameAtTime.getConfig());
                        Canvas canvas = new Canvas(createBitmap);
                        float max = Math.max(VideoTimelinePlayView.this.frameWidth / frameAtTime.getWidth(), VideoTimelinePlayView.this.frameHeight / frameAtTime.getHeight());
                        int width = (int) (frameAtTime.getWidth() * max);
                        int height = (int) (frameAtTime.getHeight() * max);
                        canvas.drawBitmap(frameAtTime, new android.graphics.Rect(0, 0, frameAtTime.getWidth(), frameAtTime.getHeight()), new android.graphics.Rect((VideoTimelinePlayView.this.frameWidth - width) / 2, (VideoTimelinePlayView.this.frameHeight - height) / 2, width, height), (Paint) null);
                        frameAtTime.recycle();
                        return createBitmap;
                    } catch (Exception e) {
                        e = e;
                        bitmap = frameAtTime;
                        FileLog.e(e);
                        return bitmap;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(Bitmap bitmap) {
                if (isCancelled()) {
                    return;
                }
                VideoTimelinePlayView.this.frames.add(new BitmapFrame(bitmap));
                VideoTimelinePlayView.this.invalidate();
                if (this.frameNum < VideoTimelinePlayView.this.framesToLoad) {
                    VideoTimelinePlayView.this.reloadFrames(this.frameNum + 1);
                }
            }
        };
        this.currentTask = asyncTask;
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Integer.valueOf(i), null, null);
    }

    public void destroy() {
        Bitmap bitmap;
        synchronized (sync) {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = this.mediaMetadataRetriever;
                if (mediaMetadataRetriever != null) {
                    mediaMetadataRetriever.release();
                    this.mediaMetadataRetriever = null;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        for (int i = 0; i < this.frames.size(); i++) {
            BitmapFrame bitmapFrame = this.frames.get(i);
            if (bitmapFrame != null && (bitmap = bitmapFrame.bitmap) != null) {
                bitmap.recycle();
            }
        }
        this.frames.clear();
        AsyncTask<Integer, Integer, Bitmap> asyncTask = this.currentTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.currentTask = null;
        }
    }

    public boolean isDragging() {
        return this.pressedPlay;
    }

    public void setProgress(float f) {
        this.playProgress = f;
        invalidate();
    }

    public void clearFrames() {
        for (int i = 0; i < this.frames.size(); i++) {
            BitmapFrame bitmapFrame = this.frames.get(i);
            if (bitmapFrame != null) {
                bitmapFrame.bitmap.recycle();
            }
        }
        this.frames.clear();
        AsyncTask<Integer, Integer, Bitmap> asyncTask = this.currentTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.currentTask = null;
        }
        invalidate();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        if (this.lastWidth != size) {
            clearFrames();
            this.lastWidth = size;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth() - AndroidUtilities.dp(32.0f);
        float f = measuredWidth;
        int dp = ((int) (this.progressLeft * f)) + AndroidUtilities.dp(16.0f);
        int dp2 = ((int) (this.progressRight * f)) + AndroidUtilities.dp(16.0f);
        canvas.save();
        canvas.clipRect(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(20.0f) + measuredWidth, AndroidUtilities.dp(48.0f));
        int i = 0;
        float f2 = 1.0f;
        if (this.frames.isEmpty() && this.currentTask == null) {
            reloadFrames(0);
        } else {
            int i2 = 0;
            while (i < this.frames.size()) {
                BitmapFrame bitmapFrame = this.frames.get(i);
                if (bitmapFrame.bitmap != null) {
                    int dp3 = AndroidUtilities.dp(16.0f) + (this.frameWidth * i2);
                    int dp4 = AndroidUtilities.dp(6.0f);
                    float f3 = bitmapFrame.alpha;
                    if (f3 != f2) {
                        float f4 = f3 + 0.16f;
                        bitmapFrame.alpha = f4;
                        if (f4 > f2) {
                            bitmapFrame.alpha = f2;
                        } else {
                            invalidate();
                        }
                        this.bitmapPaint.setAlpha((int) (bitmapFrame.alpha * 255.0f));
                        canvas.drawBitmap(bitmapFrame.bitmap, dp3, dp4, this.bitmapPaint);
                    } else {
                        canvas.drawBitmap(bitmapFrame.bitmap, dp3, dp4, (Paint) null);
                    }
                }
                i2++;
                i++;
                f2 = 1.0f;
            }
        }
        int dp5 = AndroidUtilities.dp(6.0f);
        int dp6 = AndroidUtilities.dp(48.0f);
        float f5 = dp5;
        float f6 = dp;
        canvas.drawRect(AndroidUtilities.dp(16.0f), f5, f6, AndroidUtilities.dp(46.0f), this.paint2);
        canvas.drawRect(AndroidUtilities.dp(4.0f) + dp2, f5, AndroidUtilities.dp(16.0f) + measuredWidth + AndroidUtilities.dp(4.0f), AndroidUtilities.dp(46.0f), this.paint2);
        float f7 = dp6;
        canvas.drawRect(f6, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(2.0f) + dp, f7, this.paint);
        canvas.drawRect(AndroidUtilities.dp(2.0f) + dp2, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f) + dp2, f7, this.paint);
        canvas.drawRect(AndroidUtilities.dp(2.0f) + dp, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(4.0f) + dp2, f5, this.paint);
        canvas.drawRect(AndroidUtilities.dp(2.0f) + dp, dp6 - AndroidUtilities.dp(2.0f), AndroidUtilities.dp(4.0f) + dp2, f7, this.paint);
        canvas.restore();
        this.rect3.set(dp - AndroidUtilities.dp(8.0f), AndroidUtilities.dp(4.0f), AndroidUtilities.dp(2.0f) + dp, f7);
        canvas.drawRoundRect(this.rect3, AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f), this.paint);
        this.drawableLeft.setBounds(dp - AndroidUtilities.dp(8.0f), AndroidUtilities.dp(4.0f) + ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2), dp + AndroidUtilities.dp(2.0f), ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2) + AndroidUtilities.dp(22.0f));
        this.drawableLeft.draw(canvas);
        this.rect3.set(AndroidUtilities.dp(2.0f) + dp2, AndroidUtilities.dp(4.0f), AndroidUtilities.dp(12.0f) + dp2, f7);
        canvas.drawRoundRect(this.rect3, AndroidUtilities.dp(2.0f), AndroidUtilities.dp(2.0f), this.paint);
        this.drawableRight.setBounds(AndroidUtilities.dp(2.0f) + dp2, AndroidUtilities.dp(4.0f) + ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2), dp2 + AndroidUtilities.dp(12.0f), ((AndroidUtilities.dp(44.0f) - AndroidUtilities.dp(18.0f)) / 2) + AndroidUtilities.dp(22.0f));
        this.drawableRight.draw(canvas);
        float dp7 = AndroidUtilities.dp(18.0f) + (f * this.playProgress);
        this.rect3.set(dp7 - AndroidUtilities.dp(1.5f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(1.5f) + dp7, AndroidUtilities.dp(50.0f));
        canvas.drawRoundRect(this.rect3, AndroidUtilities.dp(1.0f), AndroidUtilities.dp(1.0f), this.paint2);
        canvas.drawCircle(dp7, AndroidUtilities.dp(52.0f), AndroidUtilities.dp(3.5f), this.paint2);
        this.rect3.set(dp7 - AndroidUtilities.dp(1.0f), AndroidUtilities.dp(2.0f), AndroidUtilities.dp(1.0f) + dp7, AndroidUtilities.dp(50.0f));
        canvas.drawRoundRect(this.rect3, AndroidUtilities.dp(1.0f), AndroidUtilities.dp(1.0f), this.paint);
        canvas.drawCircle(dp7, AndroidUtilities.dp(52.0f), AndroidUtilities.dp(3.0f), this.paint);
    }

    private static class BitmapFrame {
        float alpha;
        Bitmap bitmap;

        public BitmapFrame(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }
}
