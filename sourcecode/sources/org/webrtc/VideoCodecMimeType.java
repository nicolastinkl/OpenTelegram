package org.webrtc;

import org.telegram.messenger.MediaController;

/* loaded from: classes4.dex */
enum VideoCodecMimeType {
    VP8("video/x-vnd.on2.vp8"),
    VP9("video/x-vnd.on2.vp9"),
    H264(MediaController.VIDEO_MIME_TYPE),
    H265("video/hevc"),
    AV1("video/av01");

    private final String mimeType;

    VideoCodecMimeType(String str) {
        this.mimeType = str;
    }

    String mimeType() {
        return this.mimeType;
    }
}
