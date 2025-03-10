package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import android.os.Handler;
import android.util.SparseArray;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.source.rtsp.RtspClient;
import com.google.android.exoplayer2.source.rtsp.RtspHeaders;
import com.google.android.exoplayer2.source.rtsp.RtspMediaPeriod;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.source.rtsp.RtspMessageUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.tencent.cos.xml.crypto.Headers;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.SocketFactory;

/* loaded from: classes.dex */
final class RtspClient implements Closeable {
    private final boolean debugLoggingEnabled;
    private boolean hasPendingPauseRequest;
    private boolean hasUpdatedTimelineAndTracks;
    private KeepAliveMonitor keepAliveMonitor;
    private final PlaybackEventListener playbackEventListener;
    private boolean receivedAuthorizationRequest;
    private RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo;
    private RtspAuthenticationInfo rtspAuthenticationInfo;
    private String sessionId;
    private final SessionInfoListener sessionInfoListener;
    private final SocketFactory socketFactory;
    private Uri uri;
    private final String userAgent;
    private final ArrayDeque<RtspMediaPeriod.RtpLoadInfo> pendingSetupRtpLoadInfos = new ArrayDeque<>();
    private final SparseArray<RtspRequest> pendingRequests = new SparseArray<>();
    private final MessageSender messageSender = new MessageSender();
    private RtspMessageChannel messageChannel = new RtspMessageChannel(new MessageListener());
    private long pendingSeekPositionUs = -9223372036854775807L;
    private int rtspState = -1;

    public interface PlaybackEventListener {
        void onPlaybackError(RtspMediaSource.RtspPlaybackException rtspPlaybackException);

        void onPlaybackStarted(long j, ImmutableList<RtspTrackTiming> immutableList);

        void onRtspSetupCompleted();
    }

    public interface SessionInfoListener {
        void onSessionTimelineRequestFailed(String str, Throwable th);

        void onSessionTimelineUpdated(RtspSessionTiming rtspSessionTiming, ImmutableList<RtspMediaTrack> immutableList);
    }

    public RtspClient(SessionInfoListener sessionInfoListener, PlaybackEventListener playbackEventListener, String str, Uri uri, SocketFactory socketFactory, boolean z) {
        this.sessionInfoListener = sessionInfoListener;
        this.playbackEventListener = playbackEventListener;
        this.userAgent = str;
        this.socketFactory = socketFactory;
        this.debugLoggingEnabled = z;
        this.uri = RtspMessageUtil.removeUserInfo(uri);
        this.rtspAuthUserInfo = RtspMessageUtil.parseUserInfo(uri);
    }

    public void start() throws IOException {
        try {
            this.messageChannel.open(getSocket(this.uri));
            this.messageSender.sendOptionsRequest(this.uri, this.sessionId);
        } catch (IOException e) {
            Util.closeQuietly(this.messageChannel);
            throw e;
        }
    }

    public int getState() {
        return this.rtspState;
    }

    public void setupSelectedTracks(List<RtspMediaPeriod.RtpLoadInfo> list) {
        this.pendingSetupRtpLoadInfos.addAll(list);
        continueSetupRtspTrack();
    }

    public void startPlayback(long j) {
        this.messageSender.sendPlayRequest(this.uri, j, (String) Assertions.checkNotNull(this.sessionId));
    }

    public void seekToUs(long j) {
        if (this.rtspState == 2 && !this.hasPendingPauseRequest) {
            this.messageSender.sendPauseRequest(this.uri, (String) Assertions.checkNotNull(this.sessionId));
        }
        this.pendingSeekPositionUs = j;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        KeepAliveMonitor keepAliveMonitor = this.keepAliveMonitor;
        if (keepAliveMonitor != null) {
            keepAliveMonitor.close();
            this.keepAliveMonitor = null;
            this.messageSender.sendTeardownRequest(this.uri, (String) Assertions.checkNotNull(this.sessionId));
        }
        this.messageChannel.close();
    }

    public void retryWithRtpTcp() {
        try {
            close();
            RtspMessageChannel rtspMessageChannel = new RtspMessageChannel(new MessageListener());
            this.messageChannel = rtspMessageChannel;
            rtspMessageChannel.open(getSocket(this.uri));
            this.sessionId = null;
            this.receivedAuthorizationRequest = false;
            this.rtspAuthenticationInfo = null;
        } catch (IOException e) {
            this.playbackEventListener.onPlaybackError(new RtspMediaSource.RtspPlaybackException(e));
        }
    }

    public void registerInterleavedDataChannel(int i, RtspMessageChannel.InterleavedBinaryDataListener interleavedBinaryDataListener) {
        this.messageChannel.registerInterleavedBinaryDataListener(i, interleavedBinaryDataListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void continueSetupRtspTrack() {
        RtspMediaPeriod.RtpLoadInfo pollFirst = this.pendingSetupRtpLoadInfos.pollFirst();
        if (pollFirst == null) {
            this.playbackEventListener.onRtspSetupCompleted();
        } else {
            this.messageSender.sendSetupRequest(pollFirst.getTrackUri(), pollFirst.getTransport(), this.sessionId);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeLogMessage(List<String> list) {
        if (this.debugLoggingEnabled) {
            Log.d("RtspClient", Joiner.on("\n").join(list));
        }
    }

    private Socket getSocket(Uri uri) throws IOException {
        Assertions.checkArgument(uri.getHost() != null);
        return this.socketFactory.createSocket((String) Assertions.checkNotNull(uri.getHost()), uri.getPort() > 0 ? uri.getPort() : 554);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchRtspError(Throwable th) {
        RtspMediaSource.RtspPlaybackException rtspPlaybackException;
        if (th instanceof RtspMediaSource.RtspPlaybackException) {
            rtspPlaybackException = (RtspMediaSource.RtspPlaybackException) th;
        } else {
            rtspPlaybackException = new RtspMediaSource.RtspPlaybackException(th);
        }
        if (this.hasUpdatedTimelineAndTracks) {
            this.playbackEventListener.onPlaybackError(rtspPlaybackException);
        } else {
            this.sessionInfoListener.onSessionTimelineRequestFailed(Strings.nullToEmpty(th.getMessage()), th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean serverSupportsDescribe(List<Integer> list) {
        return list.isEmpty() || list.contains(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<RtspMediaTrack> buildTrackList(SessionDescription sessionDescription, Uri uri) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i = 0; i < sessionDescription.mediaDescriptionList.size(); i++) {
            MediaDescription mediaDescription = sessionDescription.mediaDescriptionList.get(i);
            if (RtpPayloadFormat.isFormatSupported(mediaDescription)) {
                builder.add((ImmutableList.Builder) new RtspMediaTrack(mediaDescription, uri));
            }
        }
        return builder.build();
    }

    private final class MessageSender {
        private int cSeq;
        private RtspRequest lastRequest;

        private MessageSender() {
        }

        public void sendOptionsRequest(Uri uri, String str) {
            sendRequest(getRequestWithCommonHeaders(4, str, ImmutableMap.of(), uri));
        }

        public void sendDescribeRequest(Uri uri, String str) {
            sendRequest(getRequestWithCommonHeaders(2, str, ImmutableMap.of(), uri));
        }

        public void sendSetupRequest(Uri uri, String str, String str2) {
            RtspClient.this.rtspState = 0;
            sendRequest(getRequestWithCommonHeaders(10, str2, ImmutableMap.of("Transport", str), uri));
        }

        public void sendPlayRequest(Uri uri, long j, String str) {
            boolean z = true;
            if (RtspClient.this.rtspState != 1 && RtspClient.this.rtspState != 2) {
                z = false;
            }
            Assertions.checkState(z);
            sendRequest(getRequestWithCommonHeaders(6, str, ImmutableMap.of("Range", RtspSessionTiming.getOffsetStartTimeTiming(j)), uri));
        }

        public void sendTeardownRequest(Uri uri, String str) {
            if (RtspClient.this.rtspState == -1 || RtspClient.this.rtspState == 0) {
                return;
            }
            RtspClient.this.rtspState = 0;
            sendRequest(getRequestWithCommonHeaders(12, str, ImmutableMap.of(), uri));
        }

        public void sendPauseRequest(Uri uri, String str) {
            Assertions.checkState(RtspClient.this.rtspState == 2);
            sendRequest(getRequestWithCommonHeaders(5, str, ImmutableMap.of(), uri));
            RtspClient.this.hasPendingPauseRequest = true;
        }

        public void retryLastRequest() {
            Assertions.checkStateNotNull(this.lastRequest);
            ImmutableListMultimap<String, String> asMultiMap = this.lastRequest.headers.asMultiMap();
            HashMap hashMap = new HashMap();
            for (String str : asMultiMap.keySet()) {
                if (!str.equals("CSeq") && !str.equals(Headers.USER_AGENT) && !str.equals("Session") && !str.equals(Headers.COS_AUTHORIZATION)) {
                    hashMap.put(str, (String) Iterables.getLast(asMultiMap.get((ImmutableListMultimap<String, String>) str)));
                }
            }
            sendRequest(getRequestWithCommonHeaders(this.lastRequest.method, RtspClient.this.sessionId, hashMap, this.lastRequest.uri));
        }

        public void sendMethodNotAllowedResponse(int i) {
            sendResponse(new RtspResponse(405, new RtspHeaders.Builder(RtspClient.this.userAgent, RtspClient.this.sessionId, i).build()));
            this.cSeq = Math.max(this.cSeq, i + 1);
        }

        private RtspRequest getRequestWithCommonHeaders(int i, String str, Map<String, String> map, Uri uri) {
            String str2 = RtspClient.this.userAgent;
            int i2 = this.cSeq;
            this.cSeq = i2 + 1;
            RtspHeaders.Builder builder = new RtspHeaders.Builder(str2, str, i2);
            if (RtspClient.this.rtspAuthenticationInfo != null) {
                Assertions.checkStateNotNull(RtspClient.this.rtspAuthUserInfo);
                try {
                    builder.add(Headers.COS_AUTHORIZATION, RtspClient.this.rtspAuthenticationInfo.getAuthorizationHeaderValue(RtspClient.this.rtspAuthUserInfo, uri, i));
                } catch (ParserException e) {
                    RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(e));
                }
            }
            builder.addAll(map);
            return new RtspRequest(uri, i, builder.build(), "");
        }

        private void sendRequest(RtspRequest rtspRequest) {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(rtspRequest.headers.get("CSeq")));
            Assertions.checkState(RtspClient.this.pendingRequests.get(parseInt) == null);
            RtspClient.this.pendingRequests.append(parseInt, rtspRequest);
            ImmutableList<String> serializeRequest = RtspMessageUtil.serializeRequest(rtspRequest);
            RtspClient.this.maybeLogMessage(serializeRequest);
            RtspClient.this.messageChannel.send(serializeRequest);
            this.lastRequest = rtspRequest;
        }

        private void sendResponse(RtspResponse rtspResponse) {
            ImmutableList<String> serializeResponse = RtspMessageUtil.serializeResponse(rtspResponse);
            RtspClient.this.maybeLogMessage(serializeResponse);
            RtspClient.this.messageChannel.send(serializeResponse);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MessageListener implements RtspMessageChannel.MessageListener {
        private final Handler messageHandler = Util.createHandlerForCurrentLooper();

        @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.MessageListener
        public /* synthetic */ void onReceivingFailed(Exception exc) {
            RtspMessageChannel.MessageListener.CC.$default$onReceivingFailed(this, exc);
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.MessageListener
        public /* synthetic */ void onSendingFailed(List list, Exception exc) {
            RtspMessageChannel.MessageListener.CC.$default$onSendingFailed(this, list, exc);
        }

        public MessageListener() {
        }

        @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.MessageListener
        public void onRtspMessageReceived(final List<String> list) {
            this.messageHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.RtspClient$MessageListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RtspClient.MessageListener.this.lambda$onRtspMessageReceived$0(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: handleRtspMessage, reason: merged with bridge method [inline-methods] */
        public void lambda$onRtspMessageReceived$0(List<String> list) {
            RtspClient.this.maybeLogMessage(list);
            if (RtspMessageUtil.isRtspResponse(list)) {
                handleRtspResponse(list);
            } else {
                handleRtspRequest(list);
            }
        }

        private void handleRtspRequest(List<String> list) {
            RtspClient.this.messageSender.sendMethodNotAllowedResponse(Integer.parseInt((String) Assertions.checkNotNull(RtspMessageUtil.parseRequest(list).headers.get("CSeq"))));
        }

        private void handleRtspResponse(List<String> list) {
            int i;
            RtspSessionTiming parseTiming;
            ImmutableList<RtspTrackTiming> of;
            RtspResponse parseResponse = RtspMessageUtil.parseResponse(list);
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(parseResponse.headers.get("CSeq")));
            RtspRequest rtspRequest = (RtspRequest) RtspClient.this.pendingRequests.get(parseInt);
            if (rtspRequest == null) {
                return;
            }
            RtspClient.this.pendingRequests.remove(parseInt);
            int i2 = rtspRequest.method;
            try {
                i = parseResponse.status;
            } catch (ParserException e) {
                RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(e));
            }
            if (i != 200) {
                if (i != 401) {
                    if (i == 301 || i == 302) {
                        if (RtspClient.this.rtspState != -1) {
                            RtspClient.this.rtspState = 0;
                        }
                        String str = parseResponse.headers.get("Location");
                        if (str == null) {
                            RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("Redirection without new location.", null);
                            return;
                        }
                        Uri parse = Uri.parse(str);
                        RtspClient.this.uri = RtspMessageUtil.removeUserInfo(parse);
                        RtspClient.this.rtspAuthUserInfo = RtspMessageUtil.parseUserInfo(parse);
                        RtspClient.this.messageSender.sendDescribeRequest(RtspClient.this.uri, RtspClient.this.sessionId);
                        return;
                    }
                } else if (RtspClient.this.rtspAuthUserInfo != null && !RtspClient.this.receivedAuthorizationRequest) {
                    ImmutableList<String> values = parseResponse.headers.values("WWW-Authenticate");
                    if (values.isEmpty()) {
                        throw ParserException.createForMalformedManifest("Missing WWW-Authenticate header in a 401 response.", null);
                    }
                    for (int i3 = 0; i3 < values.size(); i3++) {
                        RtspClient.this.rtspAuthenticationInfo = RtspMessageUtil.parseWwwAuthenticateHeader(values.get(i3));
                        if (RtspClient.this.rtspAuthenticationInfo.authenticationMechanism == 2) {
                            break;
                        }
                    }
                    RtspClient.this.messageSender.retryLastRequest();
                    RtspClient.this.receivedAuthorizationRequest = true;
                    return;
                }
                RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(RtspMessageUtil.toMethodString(i2) + " " + parseResponse.status));
                return;
            }
            switch (i2) {
                case 1:
                case 3:
                case 7:
                case 8:
                case 9:
                case 11:
                case 12:
                    return;
                case 2:
                    onDescribeResponseReceived(new RtspDescribeResponse(i, SessionDescriptionParser.parse(parseResponse.messageBody)));
                    return;
                case 4:
                    onOptionsResponseReceived(new RtspOptionsResponse(i, RtspMessageUtil.parsePublicHeader(parseResponse.headers.get("Public"))));
                    return;
                case 5:
                    onPauseResponseReceived();
                    return;
                case 6:
                    String str2 = parseResponse.headers.get("Range");
                    if (str2 == null) {
                        parseTiming = RtspSessionTiming.DEFAULT;
                    } else {
                        parseTiming = RtspSessionTiming.parseTiming(str2);
                    }
                    try {
                        String str3 = parseResponse.headers.get("RTP-Info");
                        if (str3 != null) {
                            of = RtspTrackTiming.parseTrackTiming(str3, RtspClient.this.uri);
                        } else {
                            of = ImmutableList.of();
                        }
                    } catch (ParserException unused) {
                        of = ImmutableList.of();
                    }
                    onPlayResponseReceived(new RtspPlayResponse(parseResponse.status, parseTiming, of));
                    return;
                case 10:
                    String str4 = parseResponse.headers.get("Session");
                    String str5 = parseResponse.headers.get("Transport");
                    if (str4 == null || str5 == null) {
                        throw ParserException.createForMalformedManifest("Missing mandatory session or transport header", null);
                    }
                    onSetupResponseReceived(new RtspSetupResponse(parseResponse.status, RtspMessageUtil.parseSessionHeader(str4), str5));
                    return;
                default:
                    throw new IllegalStateException();
            }
            RtspClient.this.dispatchRtspError(new RtspMediaSource.RtspPlaybackException(e));
        }

        private void onOptionsResponseReceived(RtspOptionsResponse rtspOptionsResponse) {
            if (RtspClient.this.keepAliveMonitor != null) {
                return;
            }
            if (RtspClient.serverSupportsDescribe(rtspOptionsResponse.supportedMethods)) {
                RtspClient.this.messageSender.sendDescribeRequest(RtspClient.this.uri, RtspClient.this.sessionId);
            } else {
                RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("DESCRIBE not supported.", null);
            }
        }

        private void onDescribeResponseReceived(RtspDescribeResponse rtspDescribeResponse) {
            RtspSessionTiming rtspSessionTiming = RtspSessionTiming.DEFAULT;
            String str = rtspDescribeResponse.sessionDescription.attributes.get("range");
            if (str != null) {
                try {
                    rtspSessionTiming = RtspSessionTiming.parseTiming(str);
                } catch (ParserException e) {
                    RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("SDP format error.", e);
                    return;
                }
            }
            ImmutableList<RtspMediaTrack> buildTrackList = RtspClient.buildTrackList(rtspDescribeResponse.sessionDescription, RtspClient.this.uri);
            if (buildTrackList.isEmpty()) {
                RtspClient.this.sessionInfoListener.onSessionTimelineRequestFailed("No playable track.", null);
            } else {
                RtspClient.this.sessionInfoListener.onSessionTimelineUpdated(rtspSessionTiming, buildTrackList);
                RtspClient.this.hasUpdatedTimelineAndTracks = true;
            }
        }

        private void onSetupResponseReceived(RtspSetupResponse rtspSetupResponse) {
            Assertions.checkState(RtspClient.this.rtspState != -1);
            RtspClient.this.rtspState = 1;
            RtspClient.this.sessionId = rtspSetupResponse.sessionHeader.sessionId;
            RtspClient.this.continueSetupRtspTrack();
        }

        private void onPlayResponseReceived(RtspPlayResponse rtspPlayResponse) {
            Assertions.checkState(RtspClient.this.rtspState == 1);
            RtspClient.this.rtspState = 2;
            if (RtspClient.this.keepAliveMonitor == null) {
                RtspClient rtspClient = RtspClient.this;
                rtspClient.keepAliveMonitor = rtspClient.new KeepAliveMonitor(30000L);
                RtspClient.this.keepAliveMonitor.start();
            }
            RtspClient.this.pendingSeekPositionUs = -9223372036854775807L;
            RtspClient.this.playbackEventListener.onPlaybackStarted(Util.msToUs(rtspPlayResponse.sessionTiming.startTimeMs), rtspPlayResponse.trackTimingList);
        }

        private void onPauseResponseReceived() {
            Assertions.checkState(RtspClient.this.rtspState == 2);
            RtspClient.this.rtspState = 1;
            RtspClient.this.hasPendingPauseRequest = false;
            if (RtspClient.this.pendingSeekPositionUs != -9223372036854775807L) {
                RtspClient rtspClient = RtspClient.this;
                rtspClient.startPlayback(Util.usToMs(rtspClient.pendingSeekPositionUs));
            }
        }
    }

    private final class KeepAliveMonitor implements Runnable, Closeable {
        private final long intervalMs;
        private boolean isStarted;
        private final Handler keepAliveHandler = Util.createHandlerForCurrentLooper();

        public KeepAliveMonitor(long j) {
            this.intervalMs = j;
        }

        public void start() {
            if (this.isStarted) {
                return;
            }
            this.isStarted = true;
            this.keepAliveHandler.postDelayed(this, this.intervalMs);
        }

        @Override // java.lang.Runnable
        public void run() {
            RtspClient.this.messageSender.sendOptionsRequest(RtspClient.this.uri, RtspClient.this.sessionId);
            this.keepAliveHandler.postDelayed(this, this.intervalMs);
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.isStarted = false;
            this.keepAliveHandler.removeCallbacks(this);
        }
    }
}
