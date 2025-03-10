package org.xbill.DNS;

import j$.time.Duration;
import j$.util.Map;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.BiConsumer;
import j$.util.function.Function;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.NioClient;
import org.xbill.DNS.NioTcpClient;

/* loaded from: classes4.dex */
final class NioTcpClient extends NioClient {

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) NioTcpClient.class);
    private static final Queue<ChannelState> registrationQueue = new ConcurrentLinkedQueue();
    private static final Map<ChannelKey, ChannelState> channelMap = new ConcurrentHashMap();

    static {
        NioClient.addSelectorTimeoutTask(new Runnable() { // from class: org.xbill.DNS.NioTcpClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NioTcpClient.processPendingRegistrations();
            }
        });
        NioClient.addSelectorTimeoutTask(new Runnable() { // from class: org.xbill.DNS.NioTcpClient$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                NioTcpClient.checkTransactionTimeouts();
            }
        });
        NioClient.addCloseTask(new Runnable() { // from class: org.xbill.DNS.NioTcpClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NioTcpClient.closeTcp();
            }
        });
    }

    @Generated
    private NioTcpClient() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processPendingRegistrations() {
        while (true) {
            Queue<ChannelState> queue = registrationQueue;
            if (queue.isEmpty()) {
                return;
            }
            ChannelState remove = queue.remove();
            try {
                Selector selector = NioClient.selector();
                if (!remove.channel.isConnected()) {
                    remove.channel.register(selector, 8, remove);
                } else {
                    remove.channel.keyFor(selector).interestOps(4);
                }
            } catch (IOException e) {
                remove.handleChannelException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkTransactionTimeouts() {
        Iterator<ChannelState> it = channelMap.values().iterator();
        while (it.hasNext()) {
            Iterator<Transaction> it2 = it.next().pendingTransactions.iterator();
            while (it2.hasNext()) {
                Transaction next = it2.next();
                if (next.endTime - System.nanoTime() < 0) {
                    next.f.completeExceptionally(new SocketTimeoutException("Query timed out"));
                    it2.remove();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeTcp() {
        registrationQueue.clear();
        final EOFException eOFException = new EOFException("Client is closing");
        Map<ChannelKey, ChannelState> map = channelMap;
        Map.EL.forEach(map, new BiConsumer() { // from class: org.xbill.DNS.NioTcpClient$$ExternalSyntheticLambda3
            @Override // j$.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((NioTcpClient.ChannelState) obj2).handleTransactionException(eOFException);
            }

            @Override // j$.util.function.BiConsumer
            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer.CC.$default$andThen(this, biConsumer);
            }
        });
        map.clear();
    }

    private static class Transaction {
        private final SocketChannel channel;
        private final long endTime;
        private final CompletableFuture<byte[]> f;
        private final Message query;
        private final byte[] queryData;
        private boolean sendDone;

        @Generated
        public Transaction(Message message, byte[] bArr, long j, SocketChannel socketChannel, CompletableFuture<byte[]> completableFuture) {
            this.query = message;
            this.queryData = bArr;
            this.endTime = j;
            this.channel = socketChannel;
            this.f = completableFuture;
        }

        void send() throws IOException {
            if (this.sendDone) {
                return;
            }
            NioClient.verboseLog("TCP write", this.channel.socket().getLocalSocketAddress(), this.channel.socket().getRemoteSocketAddress(), this.queryData);
            ByteBuffer allocate = ByteBuffer.allocate(this.queryData.length + 2);
            allocate.put((byte) (this.queryData.length >>> 8));
            allocate.put((byte) (this.queryData.length & 255));
            allocate.put(this.queryData);
            allocate.flip();
            while (allocate.hasRemaining()) {
                if (this.channel.write(allocate) < 0) {
                    throw new EOFException();
                }
            }
            this.sendDone = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ChannelState implements NioClient.KeyProcessor {
        final SocketChannel channel;
        final Queue<Transaction> pendingTransactions = new ConcurrentLinkedQueue();
        ByteBuffer responseLengthData = ByteBuffer.allocate(2);
        ByteBuffer responseData = ByteBuffer.allocate(65535);
        int readState = 0;

        @Generated
        public ChannelState(SocketChannel socketChannel) {
            this.channel = socketChannel;
        }

        @Override // org.xbill.DNS.NioClient.KeyProcessor
        public void processReadyKey(SelectionKey selectionKey) {
            if (selectionKey.isValid()) {
                if (selectionKey.isConnectable()) {
                    processConnect(selectionKey);
                    return;
                }
                if (selectionKey.isWritable()) {
                    processWrite(selectionKey);
                }
                if (selectionKey.isReadable()) {
                    processRead();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void handleTransactionException(IOException iOException) {
            Iterator<Transaction> it = this.pendingTransactions.iterator();
            while (it.hasNext()) {
                it.next().f.completeExceptionally(iOException);
                it.remove();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleChannelException(IOException iOException) {
            handleTransactionException(iOException);
            for (Map.Entry entry : NioTcpClient.channelMap.entrySet()) {
                if (entry.getValue() == this) {
                    NioTcpClient.channelMap.remove(entry.getKey());
                    try {
                        this.channel.close();
                        return;
                    } catch (IOException e) {
                        NioTcpClient.log.error("failed to close channel", e);
                        return;
                    }
                }
            }
        }

        private void processConnect(SelectionKey selectionKey) {
            try {
                this.channel.finishConnect();
                selectionKey.interestOps(4);
            } catch (IOException e) {
                handleChannelException(e);
            }
        }

        private void processRead() {
            try {
                if (this.readState == 0) {
                    if (this.channel.read(this.responseLengthData) < 0) {
                        handleChannelException(new EOFException());
                        return;
                    } else if (this.responseLengthData.position() == 2) {
                        int i = ((this.responseLengthData.get(0) & 255) << 8) + (this.responseLengthData.get(1) & 255);
                        this.responseLengthData.flip();
                        this.responseData.limit(i);
                        this.readState = 1;
                    }
                }
                if (this.channel.read(this.responseData) < 0) {
                    handleChannelException(new EOFException());
                    return;
                }
                if (this.responseData.hasRemaining()) {
                    return;
                }
                this.readState = 0;
                this.responseData.flip();
                byte[] bArr = new byte[this.responseData.limit()];
                System.arraycopy(this.responseData.array(), this.responseData.arrayOffset(), bArr, 0, this.responseData.limit());
                NioClient.verboseLog("TCP read", this.channel.socket().getLocalSocketAddress(), this.channel.socket().getRemoteSocketAddress(), bArr);
                Iterator<Transaction> it = this.pendingTransactions.iterator();
                while (it.hasNext()) {
                    Transaction next = it.next();
                    if (((bArr[0] & 255) << 8) + (bArr[1] & 255) == next.query.getHeader().getID()) {
                        next.f.complete(bArr);
                        it.remove();
                        return;
                    }
                }
            } catch (IOException e) {
                handleChannelException(e);
            }
        }

        private void processWrite(SelectionKey selectionKey) {
            Iterator<Transaction> it = this.pendingTransactions.iterator();
            while (it.hasNext()) {
                Transaction next = it.next();
                try {
                    next.send();
                } catch (IOException e) {
                    next.f.completeExceptionally(e);
                    it.remove();
                }
            }
            selectionKey.interestOps(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ChannelKey {
        final InetSocketAddress local;
        final InetSocketAddress remote;

        @Generated
        public ChannelKey(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
            this.local = inetSocketAddress;
            this.remote = inetSocketAddress2;
        }

        @Generated
        protected boolean canEqual(Object obj) {
            return obj instanceof ChannelKey;
        }

        @Generated
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ChannelKey)) {
                return false;
            }
            ChannelKey channelKey = (ChannelKey) obj;
            if (!channelKey.canEqual(this)) {
                return false;
            }
            InetSocketAddress inetSocketAddress = this.local;
            InetSocketAddress inetSocketAddress2 = channelKey.local;
            if (inetSocketAddress != null ? !inetSocketAddress.equals(inetSocketAddress2) : inetSocketAddress2 != null) {
                return false;
            }
            InetSocketAddress inetSocketAddress3 = this.remote;
            InetSocketAddress inetSocketAddress4 = channelKey.remote;
            return inetSocketAddress3 != null ? inetSocketAddress3.equals(inetSocketAddress4) : inetSocketAddress4 == null;
        }

        @Generated
        public int hashCode() {
            InetSocketAddress inetSocketAddress = this.local;
            int hashCode = inetSocketAddress == null ? 43 : inetSocketAddress.hashCode();
            InetSocketAddress inetSocketAddress2 = this.remote;
            return ((hashCode + 59) * 59) + (inetSocketAddress2 != null ? inetSocketAddress2.hashCode() : 43);
        }
    }

    static CompletableFuture<byte[]> sendrecv(final InetSocketAddress inetSocketAddress, final InetSocketAddress inetSocketAddress2, Message message, byte[] bArr, Duration duration) {
        final CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();
        try {
            Selector selector = NioClient.selector();
            long nanoTime = System.nanoTime() + duration.toNanos();
            ChannelState channelState = (ChannelState) Map.EL.computeIfAbsent(channelMap, new ChannelKey(inetSocketAddress, inetSocketAddress2), new Function() { // from class: org.xbill.DNS.NioTcpClient$$ExternalSyntheticLambda4
                @Override // j$.util.function.Function
                public /* synthetic */ Function andThen(Function function) {
                    return Function.CC.$default$andThen(this, function);
                }

                @Override // j$.util.function.Function
                public final Object apply(Object obj) {
                    NioTcpClient.ChannelState lambda$sendrecv$1;
                    lambda$sendrecv$1 = NioTcpClient.lambda$sendrecv$1(inetSocketAddress, inetSocketAddress2, completableFuture, (NioTcpClient.ChannelKey) obj);
                    return lambda$sendrecv$1;
                }

                @Override // j$.util.function.Function
                public /* synthetic */ Function compose(Function function) {
                    return Function.CC.$default$compose(this, function);
                }
            });
            if (channelState != null) {
                log.trace("Creating transaction for {}/{}", message.getQuestion().getName(), Type.string(message.getQuestion().getType()));
                channelState.pendingTransactions.add(new Transaction(message, bArr, nanoTime, channelState.channel, completableFuture));
                registrationQueue.add(channelState);
                selector.wakeup();
            }
        } catch (IOException e) {
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ChannelState lambda$sendrecv$1(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, CompletableFuture completableFuture, ChannelKey channelKey) {
        try {
            log.trace("Opening async channel for l={}/r={}", inetSocketAddress, inetSocketAddress2);
            SocketChannel open = SocketChannel.open();
            open.configureBlocking(false);
            if (inetSocketAddress != null) {
                open.bind((SocketAddress) inetSocketAddress);
            }
            open.connect(inetSocketAddress2);
            return new ChannelState(open);
        } catch (IOException e) {
            completableFuture.completeExceptionally(e);
            return null;
        }
    }
}
