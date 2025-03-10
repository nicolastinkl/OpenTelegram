package org.xbill.DNS;

import j$.lang.Iterable$EL;
import j$.time.Duration;
import j$.util.function.Consumer;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Generated;
import org.slf4j.LoggerFactory;
import org.telegram.messenger.LiteMode;
import org.xbill.DNS.NioClient;
import org.xbill.DNS.NioUdpClient;

/* loaded from: classes4.dex */
final class NioUdpClient extends NioClient {
    private static final int EPHEMERAL_RANGE;
    private static final int EPHEMERAL_START;
    private static final Queue<Transaction> pendingTransactions;
    private static final SecureRandom prng;
    private static final Queue<Transaction> registrationQueue;

    static {
        int i;
        int i2;
        LoggerFactory.getLogger((Class<?>) NioUdpClient.class);
        registrationQueue = new ConcurrentLinkedQueue();
        pendingTransactions = new ConcurrentLinkedQueue();
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            i = LiteMode.FLAG_CHAT_SCALE;
            i2 = 60999;
        } else {
            i = 49152;
            i2 = 65535;
        }
        int intValue = Integer.getInteger("dnsjava.udp.ephemeral.start", i).intValue();
        EPHEMERAL_START = intValue;
        EPHEMERAL_RANGE = Integer.getInteger("dnsjava.udp.ephemeral.end", i2).intValue() - intValue;
        if (Boolean.getBoolean("dnsjava.udp.ephemeral.use_ephemeral_port")) {
            prng = null;
        } else {
            prng = new SecureRandom();
        }
        NioClient.addSelectorTimeoutTask(new Runnable() { // from class: org.xbill.DNS.NioUdpClient$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                NioUdpClient.processPendingRegistrations();
            }
        });
        NioClient.addSelectorTimeoutTask(new Runnable() { // from class: org.xbill.DNS.NioUdpClient$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                NioUdpClient.checkTransactionTimeouts();
            }
        });
        NioClient.addCloseTask(new Runnable() { // from class: org.xbill.DNS.NioUdpClient$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NioUdpClient.closeUdp();
            }
        });
    }

    @Generated
    private NioUdpClient() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processPendingRegistrations() {
        while (true) {
            Queue<Transaction> queue = registrationQueue;
            if (queue.isEmpty()) {
                return;
            }
            Transaction remove = queue.remove();
            try {
                remove.channel.register(NioClient.selector(), 1, remove);
                remove.send();
            } catch (IOException e) {
                remove.f.completeExceptionally(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkTransactionTimeouts() {
        Iterator<Transaction> it = pendingTransactions.iterator();
        while (it.hasNext()) {
            Transaction next = it.next();
            if (next.endTime - System.nanoTime() < 0) {
                next.silentCloseChannel();
                next.f.completeExceptionally(new SocketTimeoutException("Query timed out"));
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Transaction implements NioClient.KeyProcessor {
        private final DatagramChannel channel;
        private final byte[] data;
        private final long endTime;
        private final CompletableFuture<byte[]> f;
        private final int max;

        @Generated
        public Transaction(byte[] bArr, int i, long j, DatagramChannel datagramChannel, CompletableFuture<byte[]> completableFuture) {
            this.data = bArr;
            this.max = i;
            this.endTime = j;
            this.channel = datagramChannel;
            this.f = completableFuture;
        }

        void send() throws IOException {
            ByteBuffer wrap = ByteBuffer.wrap(this.data);
            NioClient.verboseLog("UDP write", this.channel.socket().getLocalSocketAddress(), this.channel.socket().getRemoteSocketAddress(), this.data);
            DatagramChannel datagramChannel = this.channel;
            if (datagramChannel.send(wrap, datagramChannel.socket().getRemoteSocketAddress()) <= 0) {
                throw new EOFException();
            }
        }

        @Override // org.xbill.DNS.NioClient.KeyProcessor
        public void processReadyKey(SelectionKey selectionKey) {
            if (!selectionKey.isReadable()) {
                silentCloseChannel();
                this.f.completeExceptionally(new EOFException("channel not readable"));
                NioUdpClient.pendingTransactions.remove(this);
                return;
            }
            DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
            ByteBuffer allocate = ByteBuffer.allocate(this.max);
            try {
                int read = datagramChannel.read(allocate);
                if (read <= 0) {
                    throw new EOFException();
                }
                allocate.flip();
                byte[] bArr = new byte[read];
                System.arraycopy(allocate.array(), 0, bArr, 0, read);
                NioClient.verboseLog("UDP read", datagramChannel.socket().getLocalSocketAddress(), datagramChannel.socket().getRemoteSocketAddress(), bArr);
                silentCloseChannel();
                this.f.complete(bArr);
                NioUdpClient.pendingTransactions.remove(this);
            } catch (IOException e) {
                silentCloseChannel();
                this.f.completeExceptionally(e);
                NioUdpClient.pendingTransactions.remove(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void silentCloseChannel() {
            try {
                this.channel.disconnect();
            } catch (IOException unused) {
            } catch (Throwable th) {
                try {
                    this.channel.close();
                } catch (IOException unused2) {
                }
                throw th;
            }
            try {
                this.channel.close();
            } catch (IOException unused3) {
            }
        }
    }

    static CompletableFuture<byte[]> sendrecv(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, byte[] bArr, int i, Duration duration) {
        SecureRandom secureRandom;
        CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();
        try {
            Selector selector = NioClient.selector();
            DatagramChannel open = DatagramChannel.open();
            boolean z = false;
            open.configureBlocking(false);
            if (inetSocketAddress == null || inetSocketAddress.getPort() == 0) {
                int i2 = 0;
                while (true) {
                    if (i2 >= 1024) {
                        break;
                    }
                    InetSocketAddress inetSocketAddress3 = null;
                    try {
                        if (inetSocketAddress == null) {
                            SecureRandom secureRandom2 = prng;
                            if (secureRandom2 != null) {
                                inetSocketAddress3 = new InetSocketAddress(secureRandom2.nextInt(EPHEMERAL_RANGE) + EPHEMERAL_START);
                            }
                        } else {
                            int port = inetSocketAddress.getPort();
                            if (port == 0 && (secureRandom = prng) != null) {
                                port = secureRandom.nextInt(EPHEMERAL_RANGE) + EPHEMERAL_START;
                            }
                            inetSocketAddress3 = new InetSocketAddress(inetSocketAddress.getAddress(), port);
                        }
                        open.bind((SocketAddress) inetSocketAddress3);
                        z = true;
                        break;
                    } catch (SocketException unused) {
                        i2++;
                    }
                }
                if (!z) {
                    open.close();
                    completableFuture.completeExceptionally(new IOException("No available source port found"));
                    return completableFuture;
                }
            }
            open.connect(inetSocketAddress2);
            Transaction transaction = new Transaction(bArr, i, System.nanoTime() + duration.toNanos(), open, completableFuture);
            pendingTransactions.add(transaction);
            registrationQueue.add(transaction);
            selector.wakeup();
        } catch (IOException e) {
            completableFuture.completeExceptionally(e);
        }
        return completableFuture;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void closeUdp() {
        registrationQueue.clear();
        final EOFException eOFException = new EOFException("Client is closing");
        Queue<Transaction> queue = pendingTransactions;
        Iterable$EL.forEach(queue, new Consumer() { // from class: org.xbill.DNS.NioUdpClient$$ExternalSyntheticLambda3
            @Override // j$.util.function.Consumer
            public final void accept(Object obj) {
                NioUdpClient.lambda$closeUdp$0(eOFException, (NioUdpClient.Transaction) obj);
            }

            @Override // j$.util.function.Consumer
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.CC.$default$andThen(this, consumer);
            }
        });
        queue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$closeUdp$0(EOFException eOFException, Transaction transaction) {
        transaction.f.completeExceptionally(eOFException);
    }
}
