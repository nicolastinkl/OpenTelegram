package io.openinstall.sdk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* loaded from: classes.dex */
public class ed implements Cloneable {
    private final long a;
    private final List<ec<Integer, a>> b = new ArrayList();

    private static class a {
        byte[] a;

        public a(byte[] bArr) {
            this.a = bArr;
        }

        public byte[] a() {
            byte[] bArr = this.a;
            int length = bArr.length - 12;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 12, bArr2, 0, length);
            return bArr2;
        }
    }

    public ed(long j) {
        this.a = j;
    }

    private byte[] a(int i) {
        for (ec<Integer, a> ecVar : this.b) {
            if (ecVar.a.intValue() == i) {
                return ecVar.b.a();
            }
        }
        return null;
    }

    private void b(int i) {
        Iterator<ec<Integer, a>> it = this.b.iterator();
        while (it.hasNext()) {
            if (it.next().a.intValue() == i) {
                it.remove();
            }
        }
    }

    public long a() {
        return this.a + c();
    }

    public void a(int i, byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 8 + 4];
        ByteBuffer order = ByteBuffer.wrap(bArr2).order(ByteOrder.LITTLE_ENDIAN);
        order.putLong(r0 - 8).putInt(i);
        order.put(bArr);
        ec<Integer, a> ecVar = new ec<>(Integer.valueOf(i), new a(bArr2));
        ListIterator<ec<Integer, a>> listIterator = this.b.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().a.intValue() == i) {
                listIterator.set(ecVar);
                return;
            }
        }
        this.b.add(ecVar);
    }

    public void a(byte[] bArr) {
        if (bArr == null) {
            b(987894612);
        } else {
            a(987894612, bArr);
        }
    }

    public long b() {
        return this.a;
    }

    public long c() {
        long j = 32;
        while (this.b.iterator().hasNext()) {
            j += r0.next().b.a.length;
        }
        return j;
    }

    public byte[] d() {
        return a(987894612);
    }

    public ByteBuffer[] e() {
        ByteBuffer[] byteBufferArr = new ByteBuffer[this.b.size() + 2];
        long c = c() - 8;
        byteBufferArr[0] = (ByteBuffer) ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(c).flip();
        Iterator<ec<Integer, a>> it = this.b.iterator();
        int i = 1;
        while (it.hasNext()) {
            byteBufferArr[i] = ByteBuffer.wrap(it.next().b.a);
            i++;
        }
        byteBufferArr[i] = (ByteBuffer) ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN).putLong(c).putLong(2334950737559900225L).putLong(3617552046287187010L).flip();
        return byteBufferArr;
    }

    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public ed clone() {
        ed edVar = new ed(this.a);
        for (ec<Integer, a> ecVar : this.b) {
            edVar.b.add(new ec<>(ecVar.a, ecVar.b));
        }
        return edVar;
    }
}
