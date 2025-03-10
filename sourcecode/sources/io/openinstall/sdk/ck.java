package io.openinstall.sdk;

import io.openinstall.sdk.ch;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes.dex */
public final class ck extends ch implements cs {
    private final Queue<a> a = new ConcurrentLinkedQueue();
    private final Map<InetSocketAddress, a> b = new ConcurrentHashMap();

    private class a implements ch.a {
        final Queue<b> a = new ConcurrentLinkedQueue();
        ByteBuffer b = ByteBuffer.allocate(2);
        ByteBuffer c = ByteBuffer.allocate(65535);
        int d = 0;
        private final SocketChannel f;

        public a(SocketChannel socketChannel) {
            this.f = socketChannel;
        }

        private void a() {
            try {
                if (this.d == 0) {
                    if (this.f.read(this.b) < 0) {
                        b(new EOFException());
                        return;
                    } else if (this.b.position() == 2) {
                        int i = ((this.b.get(0) & 255) << 8) + (this.b.get(1) & 255);
                        this.b.flip();
                        this.c.limit(i);
                        this.d = 1;
                    }
                }
                if (this.f.read(this.c) < 0) {
                    b(new EOFException());
                    return;
                }
                if (this.c.hasRemaining()) {
                    return;
                }
                this.d = 0;
                this.c.flip();
                int limit = this.c.limit();
                byte[] bArr = new byte[limit];
                System.arraycopy(this.c.array(), this.c.arrayOffset(), bArr, 0, this.c.limit());
                if (limit < 2) {
                    return;
                }
                int i2 = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
                Iterator<b> it = this.a.iterator();
                while (it.hasNext()) {
                    b next = it.next();
                    if (i2 == next.a.a().a()) {
                        next.e.a(bArr);
                        it.remove();
                        return;
                    }
                }
            } catch (IOException e) {
                b(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(IOException iOException) {
            a(iOException);
            for (Map.Entry entry : ck.this.b.entrySet()) {
                if (entry.getValue() == this) {
                    ck.this.b.remove(entry.getKey());
                    try {
                        this.f.close();
                        return;
                    } catch (IOException unused) {
                        return;
                    }
                }
            }
        }

        private void b(SelectionKey selectionKey) {
            try {
                this.f.finishConnect();
                selectionKey.interestOps(4);
            } catch (IOException e) {
                b(e);
            }
        }

        private void c(SelectionKey selectionKey) {
            Iterator<b> it = this.a.iterator();
            while (it.hasNext()) {
                b next = it.next();
                try {
                    next.a();
                } catch (IOException e) {
                    next.e.a(e);
                    it.remove();
                }
            }
            selectionKey.interestOps(1);
        }

        void a(IOException iOException) {
            Iterator<b> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().e.a(iOException);
                it.remove();
            }
        }

        @Override // io.openinstall.sdk.ch.a
        public void a(SelectionKey selectionKey) {
            if (selectionKey.isValid()) {
                if (selectionKey.isConnectable()) {
                    b(selectionKey);
                    return;
                }
                if (selectionKey.isWritable()) {
                    c(selectionKey);
                }
                if (selectionKey.isReadable()) {
                    a();
                }
            }
        }
    }

    private static class b {
        private final bl a;
        private final byte[] b;
        private final long c;
        private final SocketChannel d;
        private final cg e;
        private boolean f;

        public b(bl blVar, byte[] bArr, long j, SocketChannel socketChannel, cg cgVar) {
            this.a = blVar;
            this.b = bArr;
            this.c = j;
            this.d = socketChannel;
            this.e = cgVar;
        }

        void a() throws IOException {
            if (this.f) {
                return;
            }
            ByteBuffer allocate = ByteBuffer.allocate(this.b.length + 2);
            allocate.put((byte) (this.b.length >>> 8));
            allocate.put((byte) (this.b.length & 255));
            allocate.put(this.b);
            allocate.flip();
            while (allocate.hasRemaining()) {
                long write = this.d.write(allocate);
                if (write == 0) {
                    throw new EOFException("Insufficient room for the data in the underlying output buffer for transaction " + this.a.a().a());
                }
                if (write < this.b.length) {
                    throw new EOFException("Could not write all data for transaction " + this.a.a().a());
                }
            }
            this.f = true;
        }
    }

    public ck() {
        ch.b(new cl(this), true);
        ch.a(new cm(this), true);
        ch.c(new cn(this), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        while (!this.a.isEmpty()) {
            a remove = this.a.remove();
            try {
                Selector a2 = ch.a();
                if (remove.f.isConnected()) {
                    remove.f.keyFor(a2).interestOps(4);
                } else {
                    remove.f.register(a2, 8, remove);
                }
            } catch (IOException e) {
                remove.b(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        Iterator<a> it = this.b.values().iterator();
        while (it.hasNext()) {
            Iterator<b> it2 = it.next().a.iterator();
            while (it2.hasNext()) {
                b next = it2.next();
                if (next.c - System.nanoTime() < 0) {
                    next.e.a(new SocketTimeoutException("Query timed out"));
                    it2.remove();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        this.a.clear();
        EOFException eOFException = new EOFException("Client is closing");
        Iterator<a> it = this.b.values().iterator();
        while (it.hasNext()) {
            it.next().a(eOFException);
        }
        this.b.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003f A[Catch: IOException -> 0x005b, TRY_LEAVE, TryCatch #2 {IOException -> 0x005b, blocks: (B:3:0x0004, B:9:0x003f, B:17:0x0039, B:7:0x0022), top: B:2:0x0004, inners: #1 }] */
    @Override // io.openinstall.sdk.cs
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.net.InetSocketAddress r13, io.openinstall.sdk.bl r14, byte[] r15, int r16, io.openinstall.sdk.cg r17) {
        /*
            r12 = this;
            r1 = r12
            r0 = r13
            r9 = r17
            java.nio.channels.Selector r10 = io.openinstall.sdk.ch.a()     // Catch: java.io.IOException -> L5b
            long r2 = java.lang.System.nanoTime()     // Catch: java.io.IOException -> L5b
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.io.IOException -> L5b
            r5 = r16
            long r5 = (long) r5     // Catch: java.io.IOException -> L5b
            long r4 = r4.toNanos(r5)     // Catch: java.io.IOException -> L5b
            long r5 = r2 + r4
            java.util.Map<java.net.InetSocketAddress, io.openinstall.sdk.ck$a> r2 = r1.b     // Catch: java.io.IOException -> L5b
            java.lang.Object r2 = r2.get(r13)     // Catch: java.io.IOException -> L5b
            io.openinstall.sdk.ck$a r2 = (io.openinstall.sdk.ck.a) r2     // Catch: java.io.IOException -> L5b
            if (r2 != 0) goto L3c
            r3 = 0
            java.nio.channels.SocketChannel r3 = java.nio.channels.SocketChannel.open()     // Catch: java.io.IOException -> L33
            r4 = 0
            r3.configureBlocking(r4)     // Catch: java.io.IOException -> L33
            r3.connect(r13)     // Catch: java.io.IOException -> L33
            io.openinstall.sdk.ck$a r0 = new io.openinstall.sdk.ck$a     // Catch: java.io.IOException -> L33
            r0.<init>(r3)     // Catch: java.io.IOException -> L33
            goto L3d
        L33:
            r0 = move-exception
            if (r3 == 0) goto L39
            r3.close()     // Catch: java.io.IOException -> L39
        L39:
            r9.a(r0)     // Catch: java.io.IOException -> L5b
        L3c:
            r0 = r2
        L3d:
            if (r0 == 0) goto L5f
            io.openinstall.sdk.ck$b r11 = new io.openinstall.sdk.ck$b     // Catch: java.io.IOException -> L5b
            java.nio.channels.SocketChannel r7 = io.openinstall.sdk.ck.a.a(r0)     // Catch: java.io.IOException -> L5b
            r2 = r11
            r3 = r14
            r4 = r15
            r8 = r17
            r2.<init>(r3, r4, r5, r7, r8)     // Catch: java.io.IOException -> L5b
            java.util.Queue<io.openinstall.sdk.ck$b> r2 = r0.a     // Catch: java.io.IOException -> L5b
            r2.add(r11)     // Catch: java.io.IOException -> L5b
            java.util.Queue<io.openinstall.sdk.ck$a> r2 = r1.a     // Catch: java.io.IOException -> L5b
            r2.add(r0)     // Catch: java.io.IOException -> L5b
            r10.wakeup()     // Catch: java.io.IOException -> L5b
            goto L5f
        L5b:
            r0 = move-exception
            r9.a(r0)
        L5f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.openinstall.sdk.ck.a(java.net.InetSocketAddress, io.openinstall.sdk.bl, byte[], int, io.openinstall.sdk.cg):void");
    }
}
