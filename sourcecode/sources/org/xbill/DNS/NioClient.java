package org.xbill.DNS;

import j$.lang.Iterable$EL;
import j$.util.function.Consumer;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.utils.hexdump;

/* loaded from: classes4.dex */
public abstract class NioClient {
    private static Thread closeThread;
    private static volatile boolean run;
    private static volatile Selector selector;
    private static Thread selectorThread;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) NioClient.class);
    private static PacketLogger packetLogger = null;
    private static final List<Runnable> timeoutTasks = new CopyOnWriteArrayList();
    private static final List<Runnable> closeTasks = new CopyOnWriteArrayList();

    interface KeyProcessor {
        void processReadyKey(SelectionKey selectionKey);
    }

    static Selector selector() throws IOException {
        if (selector == null) {
            synchronized (NioClient.class) {
                if (selector == null) {
                    selector = Selector.open();
                    log.debug("Starting dnsjava NIO selector thread");
                    run = true;
                    Thread thread = new Thread(new Runnable() { // from class: org.xbill.DNS.NioClient$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            NioClient.runSelector();
                        }
                    });
                    selectorThread = thread;
                    thread.setDaemon(true);
                    selectorThread.setName("dnsjava NIO selector");
                    selectorThread.start();
                    Thread thread2 = new Thread(new Runnable() { // from class: org.xbill.DNS.NioClient$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NioClient.close(true);
                        }
                    });
                    closeThread = thread2;
                    thread2.setName("dnsjava NIO shutdown hook");
                    Runtime.getRuntime().addShutdownHook(closeThread);
                }
            }
        }
        return selector;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void close(boolean z) {
        run = false;
        if (!z) {
            try {
                Runtime.getRuntime().removeShutdownHook(closeThread);
            } catch (Exception unused) {
                log.warn("Failed to remove shutdown hoook, ignoring and continuing close");
            }
        }
        Iterator<Runnable> it = closeTasks.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Exception e) {
                log.warn("Failed to execute a shutdown task, ignoring and continuing close", (Throwable) e);
            }
        }
        selector.wakeup();
        try {
            selector.close();
        } catch (IOException e2) {
            log.warn("Failed to properly close selector, ignoring and continuing close", (Throwable) e2);
        }
        try {
            try {
                selectorThread.join();
                synchronized (NioClient.class) {
                    selector = null;
                    selectorThread = null;
                    closeThread = null;
                }
            } catch (Throwable th) {
                synchronized (NioClient.class) {
                    selector = null;
                    selectorThread = null;
                    closeThread = null;
                    throw th;
                }
            }
        } catch (InterruptedException unused2) {
            Thread.currentThread().interrupt();
            synchronized (NioClient.class) {
                selector = null;
                selectorThread = null;
                closeThread = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void runSelector() {
        while (run) {
            try {
                if (selector.select(1000L) == 0) {
                    Iterable$EL.forEach(timeoutTasks, new Consumer() { // from class: org.xbill.DNS.NioClient$$ExternalSyntheticLambda2
                        @Override // j$.util.function.Consumer
                        public final void accept(Object obj) {
                            ((Runnable) obj).run();
                        }

                        @Override // j$.util.function.Consumer
                        public /* synthetic */ Consumer andThen(Consumer consumer) {
                            return Consumer.CC.$default$andThen(this, consumer);
                        }
                    });
                }
                if (run) {
                    processReadyKeys();
                }
            } catch (IOException e) {
                log.error("A selection operation failed", e);
            } catch (ClosedSelectorException unused) {
            }
        }
        log.debug("dnsjava NIO selector thread stopped");
    }

    static void addSelectorTimeoutTask(Runnable runnable) {
        timeoutTasks.add(runnable);
    }

    static void addCloseTask(Runnable runnable) {
        closeTasks.add(runnable);
    }

    private static void processReadyKeys() {
        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        while (it.hasNext()) {
            SelectionKey next = it.next();
            it.remove();
            ((KeyProcessor) next.attachment()).processReadyKey(next);
        }
    }

    static void verboseLog(String str, SocketAddress socketAddress, SocketAddress socketAddress2, byte[] bArr) {
        Logger logger = log;
        if (logger.isTraceEnabled()) {
            logger.trace(hexdump.dump(str, bArr));
        }
        PacketLogger packetLogger2 = packetLogger;
        if (packetLogger2 != null) {
            packetLogger2.log(str, socketAddress, socketAddress2, bArr);
        }
    }
}
