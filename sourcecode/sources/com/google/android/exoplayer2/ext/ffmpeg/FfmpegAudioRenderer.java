package com.google.android.exoplayer2.ext.ffmpeg;

import android.os.Handler;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DecoderAudioRenderer;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.decoder.CryptoConfig;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TraceUtil;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes.dex */
public final class FfmpegAudioRenderer extends DecoderAudioRenderer<FfmpegAudioDecoder> {
    private static final int DEFAULT_INPUT_BUFFER_SIZE = 5760;
    private static final int NUM_BUFFERS = 16;
    private static final String TAG = "FfmpegAudioRenderer";

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.Renderer
    public /* bridge */ /* synthetic */ void setPlaybackSpeed(float f, float f2) throws ExoPlaybackException {
        Renderer.CC.$default$setPlaybackSpeed(this, f, f2);
    }

    @Override // com.google.android.exoplayer2.BaseRenderer, com.google.android.exoplayer2.RendererCapabilities
    public int supportsMixedMimeTypeAdaptation() {
        return 8;
    }

    public FfmpegAudioRenderer() {
        this((Handler) null, (AudioRendererEventListener) null, new AudioProcessor[0]);
    }

    public FfmpegAudioRenderer(Handler handler, AudioRendererEventListener audioRendererEventListener, AudioProcessor... audioProcessorArr) {
        this(handler, audioRendererEventListener, new DefaultAudioSink.Builder().setAudioProcessors(audioProcessorArr).build());
    }

    public FfmpegAudioRenderer(Handler handler, AudioRendererEventListener audioRendererEventListener, AudioSink audioSink) {
        super(handler, audioRendererEventListener, audioSink);
    }

    @Override // com.google.android.exoplayer2.audio.DecoderAudioRenderer
    protected int supportsFormatInternal(Format format) {
        String str = (String) Assertions.checkNotNull(format.sampleMimeType);
        if (!FfmpegLibrary.isAvailable() || !MimeTypes.isAudio(str)) {
            return 0;
        }
        if (!FfmpegLibrary.supportsFormat(str)) {
            return 1;
        }
        if (sinkSupportsFormat(format, 2) || sinkSupportsFormat(format, 4)) {
            return format.cryptoType != 0 ? 2 : 4;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.audio.DecoderAudioRenderer
    public FfmpegAudioDecoder createDecoder(Format format, CryptoConfig cryptoConfig) throws FfmpegDecoderException {
        TraceUtil.beginSection("createFfmpegAudioDecoder");
        int i = format.maxInputSize;
        FfmpegAudioDecoder ffmpegAudioDecoder = new FfmpegAudioDecoder(format, 16, 16, i != -1 ? i : DEFAULT_INPUT_BUFFER_SIZE, shouldOutputFloat(format));
        TraceUtil.endSection();
        return ffmpegAudioDecoder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.exoplayer2.audio.DecoderAudioRenderer
    public Format getOutputFormat(FfmpegAudioDecoder ffmpegAudioDecoder) {
        Assertions.checkNotNull(ffmpegAudioDecoder);
        return new Format.Builder().setSampleMimeType("audio/raw").setChannelCount(ffmpegAudioDecoder.getChannelCount()).setSampleRate(ffmpegAudioDecoder.getSampleRate()).setPcmEncoding(ffmpegAudioDecoder.getEncoding()).build();
    }

    private boolean sinkSupportsFormat(Format format, int i) {
        return sinkSupportsFormat(Util.getPcmFormat(i, format.channelCount, format.sampleRate));
    }

    private boolean shouldOutputFloat(Format format) {
        if (!sinkSupportsFormat(format, 2)) {
            return true;
        }
        if (getSinkFormatSupport(Util.getPcmFormat(4, format.channelCount, format.sampleRate)) != 2) {
            return false;
        }
        return !"audio/ac3".equals(format.sampleMimeType);
    }
}
