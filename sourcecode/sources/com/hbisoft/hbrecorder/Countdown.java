package com.hbisoft.hbrecorder;

import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public abstract class Countdown extends Timer {
    private long delay;
    private long interval;
    private boolean restart;
    private long startTime;
    private TimerTask task;

    public abstract void onFinished();

    public abstract void onStopCalled();

    public abstract void onTick(long j);

    public Countdown(long j, long j2, long j3) {
        super("PreciseCountdown", true);
        this.startTime = -1L;
        this.restart = false;
        this.delay = j3;
        this.interval = j2;
        this.task = getTask(j);
    }

    public void start() {
        scheduleAtFixedRate(this.task, this.delay, this.interval);
    }

    public void stop() {
        onStopCalled();
        this.task.cancel();
        dispose();
    }

    public void dispose() {
        cancel();
        purge();
    }

    private TimerTask getTask(final long j) {
        return new TimerTask() { // from class: com.hbisoft.hbrecorder.Countdown.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                long j2;
                if (Countdown.this.startTime >= 0 && !Countdown.this.restart) {
                    j2 = j - (scheduledExecutionTime() - Countdown.this.startTime);
                    if (j2 <= 0) {
                        cancel();
                        Countdown.this.startTime = -1L;
                        Countdown.this.onFinished();
                        return;
                    }
                } else {
                    Countdown.this.startTime = scheduledExecutionTime();
                    j2 = j;
                    Countdown.this.restart = false;
                }
                Countdown.this.onTick(j2);
            }
        };
    }
}
