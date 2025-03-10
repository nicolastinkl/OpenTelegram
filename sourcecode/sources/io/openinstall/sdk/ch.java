package io.openinstall.sdk;

import java.io.IOException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/* loaded from: classes.dex */
public abstract class ch {
    private static final Runnable[] a = new Runnable[2];
    private static final Runnable[] b = new Runnable[2];
    private static final Runnable[] c = new Runnable[2];
    private static Thread d;
    private static Thread e;
    private static volatile Selector f;
    private static volatile boolean g;

    interface a {
        void a(SelectionKey selectionKey);
    }

    static Selector a() throws IOException {
        if (f == null) {
            synchronized (ch.class) {
                if (f == null) {
                    f = Selector.open();
                    g = true;
                    Thread thread = new Thread(new ci());
                    d = thread;
                    thread.setDaemon(true);
                    d.setName("dnsjava NIO selector");
                    d.start();
                    Thread thread2 = new Thread(new cj());
                    e = thread2;
                    thread2.setName("dnsjava NIO shutdown hook");
                    Runtime.getRuntime().addShutdownHook(e);
                }
            }
        }
        return f;
    }

    static synchronized void a(Runnable runnable, boolean z) {
        synchronized (ch.class) {
            a(a, runnable, z);
        }
    }

    private static synchronized void a(Runnable[] runnableArr) {
        synchronized (ch.class) {
            Runnable runnable = runnableArr[0];
            if (runnable != null) {
                runnable.run();
            }
            Runnable runnable2 = runnableArr[1];
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    private static void a(Runnable[] runnableArr, Runnable runnable, boolean z) {
        if (z) {
            runnableArr[0] = runnable;
        } else {
            runnableArr[1] = runnable;
        }
    }

    static void b() {
        while (g) {
            try {
                if (f.select(1000) == 0) {
                    a(a);
                }
                if (g) {
                    a(b);
                    c();
                }
            } catch (IOException | ClosedSelectorException unused) {
            }
        }
    }

    static synchronized void b(Runnable runnable, boolean z) {
        synchronized (ch.class) {
            a(b, runnable, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(boolean z) {
        g = false;
        if (!z) {
            try {
                Runtime.getRuntime().removeShutdownHook(e);
            } catch (Exception unused) {
            }
        }
        try {
            a(c);
        } catch (Exception unused2) {
        }
        Selector selector = f;
        Thread thread = d;
        synchronized (ch.class) {
            f = null;
            d = null;
            e = null;
        }
        if (selector != null) {
            selector.wakeup();
            try {
                selector.close();
            } catch (IOException unused3) {
            }
        }
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException unused4) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void c() {
        Iterator<SelectionKey> it = f.selectedKeys().iterator();
        while (it.hasNext()) {
            SelectionKey next = it.next();
            it.remove();
            ((a) next.attachment()).a(next);
        }
    }

    static synchronized void c(Runnable runnable, boolean z) {
        synchronized (ch.class) {
            a(c, runnable, z);
        }
    }
}
