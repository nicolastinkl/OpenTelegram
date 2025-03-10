package com.google.android.exoplayer2.source.rtsp;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.telegram.messenger.MediaController;

/* loaded from: classes.dex */
public final class RtpPayloadFormat {
    public final int clockRate;
    public final ImmutableMap<String, String> fmtpParameters;
    public final Format format;
    public final String mediaEncoding;
    public final int rtpPayloadType;

    static boolean isFormatSupported(MediaDescription mediaDescription) {
        String upperCase = Ascii.toUpperCase(mediaDescription.rtpMapAttribute.mediaEncoding);
        upperCase.hashCode();
        switch (upperCase) {
            case "MPEG4-GENERIC":
            case "L8":
            case "AC3":
            case "AMR":
            case "L16":
            case "VP8":
            case "VP9":
            case "H264":
            case "H265":
            case "OPUS":
            case "PCMA":
            case "PCMU":
            case "MP4A-LATM":
            case "AMR-WB":
            case "MP4V-ES":
            case "H263-1998":
            case "H263-2000":
                return true;
            default:
                return false;
        }
    }

    public static String getMimeTypeFromRtpMediaType(String str) {
        String upperCase = Ascii.toUpperCase(str);
        upperCase.hashCode();
        switch (upperCase) {
            case "MPEG4-GENERIC":
            case "MP4A-LATM":
                return MediaController.AUIDO_MIME_TYPE;
            case "L8":
            case "L16":
                return "audio/raw";
            case "AC3":
                return "audio/ac3";
            case "AMR":
                return "audio/3gpp";
            case "VP8":
                return "video/x-vnd.on2.vp8";
            case "VP9":
                return "video/x-vnd.on2.vp9";
            case "H264":
                return MediaController.VIDEO_MIME_TYPE;
            case "H265":
                return "video/hevc";
            case "OPUS":
                return "audio/opus";
            case "PCMA":
                return "audio/g711-alaw";
            case "PCMU":
                return "audio/g711-mlaw";
            case "AMR-WB":
                return "audio/amr-wb";
            case "MP4V-ES":
                return "video/mp4v-es";
            case "H263-1998":
            case "H263-2000":
                return "video/3gpp";
            default:
                throw new IllegalArgumentException(str);
        }
    }

    public static int getRawPcmEncodingType(String str) {
        Assertions.checkArgument(str.equals("L8") || str.equals("L16"));
        return str.equals("L8") ? 3 : 268435456;
    }

    public RtpPayloadFormat(Format format, int i, int i2, Map<String, String> map, String str) {
        this.rtpPayloadType = i;
        this.clockRate = i2;
        this.format = format;
        this.fmtpParameters = ImmutableMap.copyOf((Map) map);
        this.mediaEncoding = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RtpPayloadFormat.class != obj.getClass()) {
            return false;
        }
        RtpPayloadFormat rtpPayloadFormat = (RtpPayloadFormat) obj;
        return this.rtpPayloadType == rtpPayloadFormat.rtpPayloadType && this.clockRate == rtpPayloadFormat.clockRate && this.format.equals(rtpPayloadFormat.format) && this.fmtpParameters.equals(rtpPayloadFormat.fmtpParameters) && this.mediaEncoding.equals(rtpPayloadFormat.mediaEncoding);
    }

    public int hashCode() {
        return ((((((((217 + this.rtpPayloadType) * 31) + this.clockRate) * 31) + this.format.hashCode()) * 31) + this.fmtpParameters.hashCode()) * 31) + this.mediaEncoding.hashCode();
    }
}
