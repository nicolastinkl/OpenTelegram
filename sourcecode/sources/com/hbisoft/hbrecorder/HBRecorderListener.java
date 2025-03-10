package com.hbisoft.hbrecorder;

/* loaded from: classes.dex */
public interface HBRecorderListener {
    void HBRecorderOnComplete();

    void HBRecorderOnError(int i, String str);

    void HBRecorderOnStart();
}
