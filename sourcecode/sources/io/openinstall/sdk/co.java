package io.openinstall.sdk;

import io.openinstall.sdk.ch;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class co extends ch implements ct {
    private final Queue<a> a = new ConcurrentLinkedQueue();
    private final Queue<a> b = new ConcurrentLinkedQueue();

    private class a implements ch.a {
        private final int b;
        private final byte[] c;
        private final int d;
        private final long e;
        private final DatagramChannel f;
        private final cg g;

        private a(int i, byte[] bArr, int i2, long j, DatagramChannel datagramChannel, cg cgVar) {
            this.b = i;
            this.c = bArr;
            this.d = i2;
            this.e = j;
            this.f = datagramChannel;
            this.g = cgVar;
        }

        /* synthetic */ a(co coVar, int i, byte[] bArr, int i2, long j, DatagramChannel datagramChannel, cg cgVar, cp cpVar) {
            this(i, bArr, i2, j, datagramChannel, cgVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Exception exc) {
            b();
            this.g.a(exc);
        }

        private void b() {
            try {
                this.f.disconnect();
            } catch (IOException unused) {
            } catch (Throwable th) {
                co.b(this.f);
                throw th;
            }
            co.b(this.f);
        }

        void a() throws IOException {
            ByteBuffer wrap = ByteBuffer.wrap(this.c);
            DatagramChannel datagramChannel = this.f;
            int send = datagramChannel.send(wrap, datagramChannel.socket().getRemoteSocketAddress());
            if (send == 0) {
                throw new EOFException("Insufficient room for the datagram in the underlying output buffer for transaction " + this.b);
            }
            if (send >= this.c.length) {
                return;
            }
            throw new EOFException("Could not send all data for transaction " + this.b);
        }

        @Override // io.openinstall.sdk.ch.a
        public void a(SelectionKey selectionKey) {
            int read;
            if (selectionKey.isReadable()) {
                DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
                ByteBuffer allocate = ByteBuffer.allocate(this.d);
                try {
                    read = datagramChannel.read(allocate);
                } catch (IOException e) {
                    e = e;
                }
                if (read <= 0) {
                    throw new EOFException();
                }
                allocate.flip();
                byte[] bArr = new byte[read];
                System.arraycopy(allocate.array(), 0, bArr, 0, read);
                b();
                this.g.a(bArr);
                co.this.b.remove(this);
            }
            e = new EOFException("Key for transaction " + this.b + " is not readable");
            a(e);
            co.this.b.remove(this);
        }
    }

    public co() {
        ch.b(new cp(this), false);
        ch.a(new cq(this), false);
        ch.c(new cr(this), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(DatagramChannel datagramChannel) {
        if (datagramChannel != null) {
            try {
                datagramChannel.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        while (!this.a.isEmpty()) {
            a remove = this.a.remove();
            try {
                remove.f.register(ch.a(), 1, remove);
                remove.a();
            } catch (IOException e) {
                remove.a(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        Iterator<a> it = this.b.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.e - System.nanoTime() < 0) {
                next.a(new SocketTimeoutException("Query timed out"));
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a.clear();
        EOFException eOFException = new EOFException("Client is closing");
        Iterator<a> it = this.b.iterator();
        while (it.hasNext()) {
            it.next().a(eOFException);
        }
        this.b.clear();
    }

    @Override // io.openinstall.sdk.ct
    public void a(InetSocketAddress inetSocketAddress, bl blVar, byte[] bArr, int i, int i2, cg cgVar) {
        Selector a2;
        DatagramChannel open;
        long nanoTime = System.nanoTime() + TimeUnit.SECONDS.toNanos(i2);
        DatagramChannel datagramChannel = null;
        try {
            a2 = ch.a();
            open = DatagramChannel.open();
        } catch (IOException e) {
            e = e;
        } catch (Throwable unused) {
        }
        try {
            open.configureBlocking(false);
            a aVar = new a(this, blVar.a().a(), bArr, i, nanoTime, open, cgVar, null);
            open.connect(inetSocketAddress);
            this.b.add(aVar);
            this.a.add(aVar);
            a2.wakeup();
        } catch (IOException e2) {
            e = e2;
            datagramChannel = open;
            b(datagramChannel);
            cgVar.a(e);
        } catch (Throwable unused2) {
            datagramChannel = open;
            b(datagramChannel);
        }
    }
}
