package com.google.android.exoplayer2.source;

import android.os.Handler;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;

/* loaded from: classes.dex */
public interface MediaSource {

    /* renamed from: com.google.android.exoplayer2.source.MediaSource$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Timeline $default$getInitialTimeline(MediaSource mediaSource) {
            return null;
        }

        public static boolean $default$isSingleWindow(MediaSource mediaSource) {
            return true;
        }
    }

    public interface Factory {
        MediaSource createMediaSource(MediaItem mediaItem);

        Factory setDrmSessionManagerProvider(DrmSessionManagerProvider drmSessionManagerProvider);

        Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy);
    }

    public interface MediaSourceCaller {
        void onSourceInfoRefreshed(MediaSource mediaSource, Timeline timeline);
    }

    void addDrmEventListener(Handler handler, DrmSessionEventListener drmSessionEventListener);

    void addEventListener(Handler handler, MediaSourceEventListener mediaSourceEventListener);

    MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator, long j);

    void disable(MediaSourceCaller mediaSourceCaller);

    void enable(MediaSourceCaller mediaSourceCaller);

    Timeline getInitialTimeline();

    MediaItem getMediaItem();

    boolean isSingleWindow();

    void maybeThrowSourceInfoRefreshError() throws IOException;

    void prepareSource(MediaSourceCaller mediaSourceCaller, TransferListener transferListener, PlayerId playerId);

    void releasePeriod(MediaPeriod mediaPeriod);

    void releaseSource(MediaSourceCaller mediaSourceCaller);

    void removeDrmEventListener(DrmSessionEventListener drmSessionEventListener);

    void removeEventListener(MediaSourceEventListener mediaSourceEventListener);

    public static final class MediaPeriodId extends com.google.android.exoplayer2.source.MediaPeriodId {
        public MediaPeriodId(Object obj) {
            super(obj);
        }

        public MediaPeriodId(Object obj, long j) {
            super(obj, j);
        }

        public MediaPeriodId(Object obj, long j, int i) {
            super(obj, j, i);
        }

        public MediaPeriodId(Object obj, int i, int i2, long j) {
            super(obj, i, i2, j);
        }

        public MediaPeriodId(com.google.android.exoplayer2.source.MediaPeriodId mediaPeriodId) {
            super(mediaPeriodId);
        }

        @Override // com.google.android.exoplayer2.source.MediaPeriodId
        public MediaPeriodId copyWithPeriodUid(Object obj) {
            return new MediaPeriodId(super.copyWithPeriodUid(obj));
        }
    }
}
