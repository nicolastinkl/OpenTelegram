package com.google.android.exoplayer2.source.rtsp.reader;

import com.google.android.exoplayer2.source.rtsp.RtpPayloadFormat;
import com.google.android.exoplayer2.util.Assertions;

/* loaded from: classes.dex */
public final class DefaultRtpPayloadReaderFactory {
    public RtpPayloadReader createPayloadReader(RtpPayloadFormat rtpPayloadFormat) {
        String str = (String) Assertions.checkNotNull(rtpPayloadFormat.format.sampleMimeType);
        str.hashCode();
        switch (str) {
            case "video/3gpp":
                return new RtpH263Reader(rtpPayloadFormat);
            case "video/hevc":
                return new RtpH265Reader(rtpPayloadFormat);
            case "audio/amr-wb":
            case "audio/3gpp":
                return new RtpAmrReader(rtpPayloadFormat);
            case "audio/mp4a-latm":
                if (rtpPayloadFormat.mediaEncoding.equals("MP4A-LATM")) {
                    return new RtpMp4aReader(rtpPayloadFormat);
                }
                return new RtpAacReader(rtpPayloadFormat);
            case "audio/ac3":
                return new RtpAc3Reader(rtpPayloadFormat);
            case "audio/raw":
            case "audio/g711-alaw":
            case "audio/g711-mlaw":
                return new RtpPcmReader(rtpPayloadFormat);
            case "video/mp4v-es":
                return new RtpMpeg4Reader(rtpPayloadFormat);
            case "video/avc":
                return new RtpH264Reader(rtpPayloadFormat);
            case "audio/opus":
                return new RtpOpusReader(rtpPayloadFormat);
            case "video/x-vnd.on2.vp8":
                return new RtpVp8Reader(rtpPayloadFormat);
            case "video/x-vnd.on2.vp9":
                return new RtpVp9Reader(rtpPayloadFormat);
            default:
                return null;
        }
    }
}
