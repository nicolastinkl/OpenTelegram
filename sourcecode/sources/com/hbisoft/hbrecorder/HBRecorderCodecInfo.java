package com.hbisoft.hbrecorder;

import android.content.Context;
import android.media.CamcorderProfile;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class HBRecorderCodecInfo {
    private Context context;

    public HBRecorderCodecInfo() {
        new ArrayList();
        new ArrayList();
        new HashMap();
        new HashMap();
    }

    int getMaxSupportedWidth() {
        return getRecordingInfo().width;
    }

    int getMaxSupportedHeight() {
        return getRecordingInfo().height;
    }

    private RecordingInfo getRecordingInfo() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.densityDpi;
        boolean z = this.context.getResources().getConfiguration().orientation == 2;
        CamcorderProfile camcorderProfile = CamcorderProfile.get(1);
        return calculateRecordingInfo(i, i2, i3, z, camcorderProfile != null ? camcorderProfile.videoFrameWidth : -1, camcorderProfile != null ? camcorderProfile.videoFrameHeight : -1, camcorderProfile != null ? camcorderProfile.videoFrameRate : 30, 100);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static final class RecordingInfo {
        final int height;
        final int width;

        RecordingInfo(int i, int i2, int i3, int i4) {
            this.width = i;
            this.height = i2;
        }
    }

    static RecordingInfo calculateRecordingInfo(int i, int i2, int i3, boolean z, int i4, int i5, int i6, int i7) {
        int i8 = (i * i7) / 100;
        int i9 = (i2 * i7) / 100;
        if (i4 == -1 && i5 == -1) {
            return new RecordingInfo(i8, i9, i6, i3);
        }
        int i10 = z ? i4 : i5;
        if (z) {
            i4 = i5;
        }
        if (i10 >= i8 && i4 >= i9) {
            return new RecordingInfo(i8, i9, i6, i3);
        }
        if (z) {
            i10 = (i8 * i4) / i9;
        } else {
            i4 = (i9 * i10) / i8;
        }
        return new RecordingInfo(i10, i4, i6, i3);
    }
}
