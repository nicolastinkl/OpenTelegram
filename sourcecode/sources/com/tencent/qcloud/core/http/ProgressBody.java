package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.common.QCloudProgressListener;

/* loaded from: classes.dex */
public interface ProgressBody {
    long getBytesTransferred();

    void setProgressListener(QCloudProgressListener qCloudProgressListener);
}
