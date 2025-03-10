package io.openinstall.sdk;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.LiteMode;

/* loaded from: classes.dex */
public class bs {
    private static String d = "1.2.4.8";
    private InetSocketAddress a;
    private boolean b;
    private int c;
    private final cs e;
    private final ct f;

    public bs() throws UnknownHostException {
        this(null);
    }

    public bs(String str) throws UnknownHostException {
        this.c = 10;
        this.a = new InetSocketAddress(InetAddress.getByName(str == null ? d : str), 53);
        this.e = new ck();
        this.f = new co();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public bl a(byte[] bArr) throws cf {
        try {
            return new bl(bArr);
        } catch (IOException e) {
            e = e;
            e.printStackTrace();
            if (!(e instanceof cf)) {
                e = new cf("Error parsing message");
            }
            throw ((cf) e);
        }
    }

    public bl a(bl blVar) {
        boolean z = true;
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);
        bt btVar = new bt(this, arrayBlockingQueue, blVar.a().a());
        byte[] b = blVar.b(65535);
        if (!this.b && b.length <= 512) {
            z = false;
        }
        if (z) {
            this.e.a(this.a, blVar, b, this.c, btVar);
        } else {
            this.f.a(this.a, blVar, b, LiteMode.FLAG_CALLS_ANIMATIONS, this.c, btVar);
        }
        try {
            Object poll = arrayBlockingQueue.poll((this.c * 1000) + 100, TimeUnit.MILLISECONDS);
            if (poll instanceof bl) {
                return (bl) poll;
            }
            boolean z2 = poll instanceof Throwable;
            return null;
        } catch (InterruptedException unused) {
            return null;
        }
    }

    public void a(int i) {
        this.c = i;
    }
}
