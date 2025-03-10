package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;

/* loaded from: classes.dex */
final class RtspRequest {
    public final RtspHeaders headers;
    public final String messageBody;
    public final int method;
    public final Uri uri;

    public RtspRequest(Uri uri, int i, RtspHeaders rtspHeaders, String str) {
        this.uri = uri;
        this.method = i;
        this.headers = rtspHeaders;
        this.messageBody = str;
    }
}
