package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.analytics.AnalyticsCollector;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.HandlerWrapper;
import com.google.common.collect.ImmutableList;

/* loaded from: classes.dex */
final class MediaPeriodQueue {
    private final AnalyticsCollector analyticsCollector;
    private final HandlerWrapper analyticsCollectorHandler;
    private int length;
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    private MediaPeriodHolder playing;
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private final Timeline.Period period = new Timeline.Period();
    private final Timeline.Window window = new Timeline.Window();

    private boolean areDurationsCompatible(long j, long j2) {
        return j == -9223372036854775807L || j == j2;
    }

    public MediaPeriodQueue(AnalyticsCollector analyticsCollector, HandlerWrapper handlerWrapper) {
        this.analyticsCollector = analyticsCollector;
        this.analyticsCollectorHandler = handlerWrapper;
    }

    public boolean updateRepeatMode(Timeline timeline, int i) {
        this.repeatMode = i;
        return updateForPlaybackModeChange(timeline);
    }

    public boolean updateShuffleModeEnabled(Timeline timeline, boolean z) {
        this.shuffleModeEnabled = z;
        return updateForPlaybackModeChange(timeline);
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder != null && mediaPeriodHolder.mediaPeriod == mediaPeriod;
    }

    public void reevaluateBuffer(long j) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder != null) {
            mediaPeriodHolder.reevaluateBuffer(j);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        return mediaPeriodHolder == null || (!mediaPeriodHolder.info.isFinal && mediaPeriodHolder.isFullyBuffered() && this.loading.info.durationUs != -9223372036854775807L && this.length < 100);
    }

    public MediaPeriodInfo getNextMediaPeriodInfo(long j, PlaybackInfo playbackInfo) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        if (mediaPeriodHolder == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(playbackInfo.timeline, mediaPeriodHolder, j);
    }

    public MediaPeriodHolder enqueueNextMediaPeriodHolder(RendererCapabilities[] rendererCapabilitiesArr, TrackSelector trackSelector, Allocator allocator, MediaSourceList mediaSourceList, MediaPeriodInfo mediaPeriodInfo, TrackSelectorResult trackSelectorResult) {
        MediaPeriodHolder mediaPeriodHolder = this.loading;
        MediaPeriodHolder mediaPeriodHolder2 = new MediaPeriodHolder(rendererCapabilitiesArr, mediaPeriodHolder == null ? 1000000000000L : (mediaPeriodHolder.getRendererOffset() + this.loading.info.durationUs) - mediaPeriodInfo.startPositionUs, trackSelector, allocator, mediaSourceList, mediaPeriodInfo, trackSelectorResult);
        MediaPeriodHolder mediaPeriodHolder3 = this.loading;
        if (mediaPeriodHolder3 != null) {
            mediaPeriodHolder3.setNext(mediaPeriodHolder2);
        } else {
            this.playing = mediaPeriodHolder2;
            this.reading = mediaPeriodHolder2;
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder2;
        this.length++;
        notifyQueueUpdate();
        return mediaPeriodHolder2;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.reading;
        Assertions.checkState((mediaPeriodHolder == null || mediaPeriodHolder.getNext() == null) ? false : true);
        this.reading = this.reading.getNext();
        notifyQueueUpdate();
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return null;
        }
        if (mediaPeriodHolder == this.reading) {
            this.reading = mediaPeriodHolder.getNext();
        }
        this.playing.release();
        int i = this.length - 1;
        this.length = i;
        if (i == 0) {
            this.loading = null;
            MediaPeriodHolder mediaPeriodHolder2 = this.playing;
            this.oldFrontPeriodUid = mediaPeriodHolder2.uid;
            this.oldFrontPeriodWindowSequenceNumber = mediaPeriodHolder2.info.id.windowSequenceNumber;
        }
        this.playing = this.playing.getNext();
        notifyQueueUpdate();
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        boolean z = false;
        Assertions.checkState(mediaPeriodHolder != null);
        if (mediaPeriodHolder.equals(this.loading)) {
            return false;
        }
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.getNext() != null) {
            mediaPeriodHolder = mediaPeriodHolder.getNext();
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                z = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.setNext(null);
        notifyQueueUpdate();
        return z;
    }

    public void clear() {
        if (this.length == 0) {
            return;
        }
        MediaPeriodHolder mediaPeriodHolder = (MediaPeriodHolder) Assertions.checkStateNotNull(this.playing);
        this.oldFrontPeriodUid = mediaPeriodHolder.uid;
        this.oldFrontPeriodWindowSequenceNumber = mediaPeriodHolder.info.id.windowSequenceNumber;
        while (mediaPeriodHolder != null) {
            mediaPeriodHolder.release();
            mediaPeriodHolder = mediaPeriodHolder.getNext();
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = 0;
        notifyQueueUpdate();
    }

    public boolean updateQueuedPeriods(Timeline timeline, long j, long j2) {
        MediaPeriodInfo mediaPeriodInfo;
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        MediaPeriodHolder mediaPeriodHolder2 = null;
        while (mediaPeriodHolder != null) {
            MediaPeriodInfo mediaPeriodInfo2 = mediaPeriodHolder.info;
            if (mediaPeriodHolder2 == null) {
                mediaPeriodInfo = getUpdatedMediaPeriodInfo(timeline, mediaPeriodInfo2);
            } else {
                MediaPeriodInfo followingMediaPeriodInfo = getFollowingMediaPeriodInfo(timeline, mediaPeriodHolder2, j);
                if (followingMediaPeriodInfo == null) {
                    return !removeAfter(mediaPeriodHolder2);
                }
                if (!canKeepMediaPeriodHolder(mediaPeriodInfo2, followingMediaPeriodInfo)) {
                    return !removeAfter(mediaPeriodHolder2);
                }
                mediaPeriodInfo = followingMediaPeriodInfo;
            }
            mediaPeriodHolder.info = mediaPeriodInfo.copyWithRequestedContentPositionUs(mediaPeriodInfo2.requestedContentPositionUs);
            if (!areDurationsCompatible(mediaPeriodInfo2.durationUs, mediaPeriodInfo.durationUs)) {
                mediaPeriodHolder.updateClipping();
                long j3 = mediaPeriodInfo.durationUs;
                return (removeAfter(mediaPeriodHolder) || (mediaPeriodHolder == this.reading && !mediaPeriodHolder.info.isFollowedByTransitionToSameStream && ((j2 > Long.MIN_VALUE ? 1 : (j2 == Long.MIN_VALUE ? 0 : -1)) == 0 || (j2 > ((j3 > (-9223372036854775807L) ? 1 : (j3 == (-9223372036854775807L) ? 0 : -1)) == 0 ? Long.MAX_VALUE : mediaPeriodHolder.toRendererTime(j3)) ? 1 : (j2 == ((j3 > (-9223372036854775807L) ? 1 : (j3 == (-9223372036854775807L) ? 0 : -1)) == 0 ? Long.MAX_VALUE : mediaPeriodHolder.toRendererTime(j3)) ? 0 : -1)) >= 0))) ? false : true;
            }
            mediaPeriodHolder2 = mediaPeriodHolder;
            mediaPeriodHolder = mediaPeriodHolder.getNext();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.exoplayer2.MediaPeriodInfo getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline r19, com.google.android.exoplayer2.MediaPeriodInfo r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r3 = r2.id
            boolean r12 = r0.isLastInPeriod(r3)
            boolean r13 = r0.isLastInWindow(r1, r3)
            boolean r14 = r0.isLastInTimeline(r1, r3, r12)
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r4 = r2.id
            java.lang.Object r4 = r4.periodUid
            com.google.android.exoplayer2.Timeline$Period r5 = r0.period
            r1.getPeriodByUid(r4, r5)
            boolean r1 = r3.isAd()
            r4 = -1
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r1 != 0) goto L35
            int r1 = r3.nextAdGroupIndex
            if (r1 != r4) goto L2e
            goto L35
        L2e:
            com.google.android.exoplayer2.Timeline$Period r7 = r0.period
            long r7 = r7.getAdGroupTimeUs(r1)
            goto L36
        L35:
            r7 = r5
        L36:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L48
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r5 = r3.adGroupIndex
            int r6 = r3.adIndexInAdGroup
            long r5 = r1.getAdDurationUs(r5, r6)
        L46:
            r9 = r5
            goto L5c
        L48:
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 == 0) goto L55
            r5 = -9223372036854775808
            int r1 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r1 != 0) goto L53
            goto L55
        L53:
            r9 = r7
            goto L5c
        L55:
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r5 = r1.getDurationUs()
            goto L46
        L5c:
            boolean r1 = r3.isAd()
            if (r1 == 0) goto L6c
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            int r4 = r3.adGroupIndex
            boolean r1 = r1.isServerSideInsertedAdGroup(r4)
            r11 = r1
            goto L7d
        L6c:
            int r1 = r3.nextAdGroupIndex
            if (r1 == r4) goto L7b
            com.google.android.exoplayer2.Timeline$Period r4 = r0.period
            boolean r1 = r4.isServerSideInsertedAdGroup(r1)
            if (r1 == 0) goto L7b
            r1 = 1
            r11 = 1
            goto L7d
        L7b:
            r1 = 0
            r11 = 0
        L7d:
            com.google.android.exoplayer2.MediaPeriodInfo r15 = new com.google.android.exoplayer2.MediaPeriodInfo
            long r4 = r2.startPositionUs
            long r1 = r2.requestedContentPositionUs
            r16 = r1
            r1 = r15
            r2 = r3
            r3 = r4
            r5 = r16
            r1.<init>(r2, r3, r5, r7, r9, r11, r12, r13, r14)
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.MediaPeriodQueue.getUpdatedMediaPeriodInfo(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.MediaPeriodInfo):com.google.android.exoplayer2.MediaPeriodInfo");
    }

    private static MediaSource.MediaPeriodId resolveMediaPeriodIdForAds(Timeline timeline, Object obj, long j, long j2, Timeline.Window window, Timeline.Period period) {
        timeline.getPeriodByUid(obj, period);
        timeline.getWindow(period.windowIndex, window);
        int indexOfPeriod = timeline.getIndexOfPeriod(obj);
        Object obj2 = obj;
        while (period.durationUs == 0 && period.getAdGroupCount() > 0 && period.isServerSideInsertedAdGroup(period.getRemovedAdGroupCount()) && period.getAdGroupIndexForPositionUs(0L) == -1) {
            int i = indexOfPeriod + 1;
            if (indexOfPeriod >= window.lastPeriodIndex) {
                break;
            }
            timeline.getPeriod(i, period, true);
            obj2 = Assertions.checkNotNull(period.uid);
            indexOfPeriod = i;
        }
        timeline.getPeriodByUid(obj2, period);
        int adGroupIndexForPositionUs = period.getAdGroupIndexForPositionUs(j);
        if (adGroupIndexForPositionUs == -1) {
            return new MediaSource.MediaPeriodId(obj2, j2, period.getAdGroupIndexAfterPositionUs(j));
        }
        return new MediaSource.MediaPeriodId(obj2, adGroupIndexForPositionUs, period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j2);
    }

    public MediaSource.MediaPeriodId resolveMediaPeriodIdForAdsAfterPeriodPositionChange(Timeline timeline, Object obj, long j) {
        long resolvePeriodIndexToWindowSequenceNumber = resolvePeriodIndexToWindowSequenceNumber(timeline, obj);
        timeline.getPeriodByUid(obj, this.period);
        timeline.getWindow(this.period.windowIndex, this.window);
        boolean z = false;
        for (int indexOfPeriod = timeline.getIndexOfPeriod(obj); indexOfPeriod >= this.window.firstPeriodIndex; indexOfPeriod--) {
            timeline.getPeriod(indexOfPeriod, this.period, true);
            boolean z2 = this.period.getAdGroupCount() > 0;
            z |= z2;
            Timeline.Period period = this.period;
            if (period.getAdGroupIndexForPositionUs(period.durationUs) != -1) {
                obj = Assertions.checkNotNull(this.period.uid);
            }
            if (z && (!z2 || this.period.durationUs != 0)) {
                break;
            }
        }
        return resolveMediaPeriodIdForAds(timeline, obj, j, resolvePeriodIndexToWindowSequenceNumber, this.window, this.period);
    }

    private void notifyQueueUpdate() {
        final ImmutableList.Builder builder = ImmutableList.builder();
        for (MediaPeriodHolder mediaPeriodHolder = this.playing; mediaPeriodHolder != null; mediaPeriodHolder = mediaPeriodHolder.getNext()) {
            builder.add((ImmutableList.Builder) mediaPeriodHolder.info.id);
        }
        MediaPeriodHolder mediaPeriodHolder2 = this.reading;
        final MediaSource.MediaPeriodId mediaPeriodId = mediaPeriodHolder2 == null ? null : mediaPeriodHolder2.info.id;
        this.analyticsCollectorHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.MediaPeriodQueue$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaPeriodQueue.this.lambda$notifyQueueUpdate$0(builder, mediaPeriodId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyQueueUpdate$0(ImmutableList.Builder builder, MediaSource.MediaPeriodId mediaPeriodId) {
        this.analyticsCollector.updateMediaPeriodQueueInfo(builder.build(), mediaPeriodId);
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Timeline timeline, Object obj) {
        int indexOfPeriod;
        int i = timeline.getPeriodByUid(obj, this.period).windowIndex;
        Object obj2 = this.oldFrontPeriodUid;
        if (obj2 != null && (indexOfPeriod = timeline.getIndexOfPeriod(obj2)) != -1 && timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
            return this.oldFrontPeriodWindowSequenceNumber;
        }
        for (MediaPeriodHolder mediaPeriodHolder = this.playing; mediaPeriodHolder != null; mediaPeriodHolder = mediaPeriodHolder.getNext()) {
            if (mediaPeriodHolder.uid.equals(obj)) {
                return mediaPeriodHolder.info.id.windowSequenceNumber;
            }
        }
        for (MediaPeriodHolder mediaPeriodHolder2 = this.playing; mediaPeriodHolder2 != null; mediaPeriodHolder2 = mediaPeriodHolder2.getNext()) {
            int indexOfPeriod2 = timeline.getIndexOfPeriod(mediaPeriodHolder2.uid);
            if (indexOfPeriod2 != -1 && timeline.getPeriod(indexOfPeriod2, this.period).windowIndex == i) {
                return mediaPeriodHolder2.info.id.windowSequenceNumber;
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        if (this.playing == null) {
            this.oldFrontPeriodUid = obj;
            this.oldFrontPeriodWindowSequenceNumber = j;
        }
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodInfo mediaPeriodInfo, MediaPeriodInfo mediaPeriodInfo2) {
        return mediaPeriodInfo.startPositionUs == mediaPeriodInfo2.startPositionUs && mediaPeriodInfo.id.equals(mediaPeriodInfo2.id);
    }

    private boolean updateForPlaybackModeChange(Timeline timeline) {
        MediaPeriodHolder mediaPeriodHolder = this.playing;
        if (mediaPeriodHolder == null) {
            return true;
        }
        int indexOfPeriod = timeline.getIndexOfPeriod(mediaPeriodHolder.uid);
        while (true) {
            indexOfPeriod = timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (mediaPeriodHolder.getNext() != null && !mediaPeriodHolder.info.isLastInTimelinePeriod) {
                mediaPeriodHolder = mediaPeriodHolder.getNext();
            }
            MediaPeriodHolder next = mediaPeriodHolder.getNext();
            if (indexOfPeriod == -1 || next == null || timeline.getIndexOfPeriod(next.uid) != indexOfPeriod) {
                break;
            }
            mediaPeriodHolder = next;
        }
        boolean removeAfter = removeAfter(mediaPeriodHolder);
        mediaPeriodHolder.info = getUpdatedMediaPeriodInfo(timeline, mediaPeriodHolder.info);
        return !removeAfter;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.timeline, playbackInfo.periodId, playbackInfo.requestedContentPositionUs, playbackInfo.positionUs);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00cc, code lost:
    
        if (r0.isServerSideInsertedAdGroup(r0.getRemovedAdGroupCount()) != false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.android.exoplayer2.MediaPeriodInfo getFollowingMediaPeriodInfo(com.google.android.exoplayer2.Timeline r20, com.google.android.exoplayer2.MediaPeriodHolder r21, long r22) {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.MediaPeriodQueue.getFollowingMediaPeriodInfo(com.google.android.exoplayer2.Timeline, com.google.android.exoplayer2.MediaPeriodHolder, long):com.google.android.exoplayer2.MediaPeriodInfo");
    }

    private MediaPeriodInfo getMediaPeriodInfo(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j, long j2) {
        timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (mediaPeriodId.isAd()) {
            return getMediaPeriodInfoForAd(timeline, mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j, mediaPeriodId.windowSequenceNumber);
        }
        return getMediaPeriodInfoForContent(timeline, mediaPeriodId.periodUid, j2, j, mediaPeriodId.windowSequenceNumber);
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Timeline timeline, Object obj, int i, int i2, long j, long j2) {
        MediaSource.MediaPeriodId mediaPeriodId = new MediaSource.MediaPeriodId(obj, i, i2, j2);
        long adDurationUs = timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup);
        long adResumePositionUs = i2 == this.period.getFirstAdIndexToPlay(i) ? this.period.getAdResumePositionUs() : 0L;
        return new MediaPeriodInfo(mediaPeriodId, (adDurationUs == -9223372036854775807L || adResumePositionUs < adDurationUs) ? adResumePositionUs : Math.max(0L, adDurationUs - 1), j, -9223372036854775807L, adDurationUs, this.period.isServerSideInsertedAdGroup(mediaPeriodId.adGroupIndex), false, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x002a, code lost:
    
        if (r9.isServerSideInsertedAdGroup(r9.getRemovedAdGroupCount()) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.android.exoplayer2.MediaPeriodInfo getMediaPeriodInfoForContent(com.google.android.exoplayer2.Timeline r25, java.lang.Object r26, long r27, long r29, long r31) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r26
            r3 = r27
            com.google.android.exoplayer2.Timeline$Period r5 = r0.period
            r1.getPeriodByUid(r2, r5)
            com.google.android.exoplayer2.Timeline$Period r5 = r0.period
            int r5 = r5.getAdGroupIndexAfterPositionUs(r3)
            r6 = 1
            r7 = -1
            r8 = 0
            if (r5 != r7) goto L2d
            com.google.android.exoplayer2.Timeline$Period r9 = r0.period
            int r9 = r9.getAdGroupCount()
            if (r9 <= 0) goto L4c
            com.google.android.exoplayer2.Timeline$Period r9 = r0.period
            int r10 = r9.getRemovedAdGroupCount()
            boolean r9 = r9.isServerSideInsertedAdGroup(r10)
            if (r9 == 0) goto L4c
            goto L4a
        L2d:
            com.google.android.exoplayer2.Timeline$Period r9 = r0.period
            boolean r9 = r9.isServerSideInsertedAdGroup(r5)
            if (r9 == 0) goto L4c
            com.google.android.exoplayer2.Timeline$Period r9 = r0.period
            long r9 = r9.getAdGroupTimeUs(r5)
            com.google.android.exoplayer2.Timeline$Period r11 = r0.period
            long r12 = r11.durationUs
            int r14 = (r9 > r12 ? 1 : (r9 == r12 ? 0 : -1))
            if (r14 != 0) goto L4c
            boolean r9 = r11.hasPlayedAdGroup(r5)
            if (r9 == 0) goto L4c
            r5 = -1
        L4a:
            r9 = 1
            goto L4d
        L4c:
            r9 = 0
        L4d:
            com.google.android.exoplayer2.source.MediaSource$MediaPeriodId r11 = new com.google.android.exoplayer2.source.MediaSource$MediaPeriodId
            r12 = r31
            r11.<init>(r2, r12, r5)
            boolean r2 = r0.isLastInPeriod(r11)
            boolean r22 = r0.isLastInWindow(r1, r11)
            boolean r23 = r0.isLastInTimeline(r1, r11, r2)
            if (r5 == r7) goto L6d
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            boolean r1 = r1.isServerSideInsertedAdGroup(r5)
            if (r1 == 0) goto L6d
            r20 = 1
            goto L6f
        L6d:
            r20 = 0
        L6f:
            r12 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r5 == r7) goto L7f
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r14 = r1.getAdGroupTimeUs(r5)
        L7c:
            r16 = r14
            goto L88
        L7f:
            if (r9 == 0) goto L86
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r14 = r1.durationUs
            goto L7c
        L86:
            r16 = r12
        L88:
            int r1 = (r16 > r12 ? 1 : (r16 == r12 ? 0 : -1))
            if (r1 == 0) goto L96
            r14 = -9223372036854775808
            int r1 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r1 != 0) goto L93
            goto L96
        L93:
            r18 = r16
            goto L9c
        L96:
            com.google.android.exoplayer2.Timeline$Period r1 = r0.period
            long r14 = r1.durationUs
            r18 = r14
        L9c:
            int r1 = (r18 > r12 ? 1 : (r18 == r12 ? 0 : -1))
            if (r1 == 0) goto Lb3
            int r1 = (r3 > r18 ? 1 : (r3 == r18 ? 0 : -1))
            if (r1 < 0) goto Lb3
            if (r23 != 0) goto Laa
            if (r9 != 0) goto La9
            goto Laa
        La9:
            r6 = 0
        Laa:
            r3 = 0
            long r5 = (long) r6
            long r5 = r18 - r5
            long r3 = java.lang.Math.max(r3, r5)
        Lb3:
            r12 = r3
            com.google.android.exoplayer2.MediaPeriodInfo r1 = new com.google.android.exoplayer2.MediaPeriodInfo
            r10 = r1
            r14 = r29
            r21 = r2
            r10.<init>(r11, r12, r14, r16, r18, r20, r21, r22, r23)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.MediaPeriodQueue.getMediaPeriodInfoForContent(com.google.android.exoplayer2.Timeline, java.lang.Object, long, long, long):com.google.android.exoplayer2.MediaPeriodInfo");
    }

    private boolean isLastInPeriod(MediaSource.MediaPeriodId mediaPeriodId) {
        return !mediaPeriodId.isAd() && mediaPeriodId.nextAdGroupIndex == -1;
    }

    private boolean isLastInWindow(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId) {
        if (isLastInPeriod(mediaPeriodId)) {
            return timeline.getWindow(timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).windowIndex, this.window).lastPeriodIndex == timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        }
        return false;
    }

    private boolean isLastInTimeline(Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, boolean z) {
        int indexOfPeriod = timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        return !timeline.getWindow(timeline.getPeriod(indexOfPeriod, this.period).windowIndex, this.window).isDynamic && timeline.isLastPeriod(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) && z;
    }

    private long getMinStartPositionAfterAdGroupUs(Timeline timeline, Object obj, int i) {
        timeline.getPeriodByUid(obj, this.period);
        long adGroupTimeUs = this.period.getAdGroupTimeUs(i);
        if (adGroupTimeUs == Long.MIN_VALUE) {
            return this.period.durationUs;
        }
        return adGroupTimeUs + this.period.getContentResumeOffsetUs(i);
    }
}
