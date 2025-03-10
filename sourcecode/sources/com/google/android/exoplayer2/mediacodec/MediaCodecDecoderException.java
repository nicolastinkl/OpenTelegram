package com.google.android.exoplayer2.mediacodec;

import android.media.MediaCodec;
import com.google.android.exoplayer2.decoder.DecoderException;

/* loaded from: classes.dex */
public class MediaCodecDecoderException extends DecoderException {
    public final String diagnosticInfo;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaCodecDecoderException(java.lang.Throwable r3, com.google.android.exoplayer2.mediacodec.MediaCodecInfo r4) {
        /*
            r2 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Decoder failed: "
            r0.append(r1)
            r1 = 0
            if (r4 != 0) goto Lf
            r4 = r1
            goto L11
        Lf:
            java.lang.String r4 = r4.name
        L11:
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r2.<init>(r4, r3)
            int r4 = com.google.android.exoplayer2.util.Util.SDK_INT
            r0 = 21
            if (r4 < r0) goto L25
            java.lang.String r1 = getDiagnosticInfoV21(r3)
        L25:
            r2.diagnosticInfo = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecDecoderException.<init>(java.lang.Throwable, com.google.android.exoplayer2.mediacodec.MediaCodecInfo):void");
    }

    private static String getDiagnosticInfoV21(Throwable th) {
        if (th instanceof MediaCodec.CodecException) {
            return ((MediaCodec.CodecException) th).getDiagnosticInfo();
        }
        return null;
    }
}
