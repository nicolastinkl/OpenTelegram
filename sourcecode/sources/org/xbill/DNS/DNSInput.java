package org.xbill.DNS;

import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class DNSInput {
    private final ByteBuffer byteBuffer;
    private final int limit;
    private final int offset;
    private int savedEnd;
    private int savedPos;

    public DNSInput(byte[] bArr) {
        this(ByteBuffer.wrap(bArr));
    }

    public DNSInput(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
        this.offset = byteBuffer.position();
        this.limit = byteBuffer.limit();
        this.savedPos = -1;
        this.savedEnd = -1;
    }

    public int current() {
        return this.byteBuffer.position() - this.offset;
    }

    public int remaining() {
        return this.byteBuffer.remaining();
    }

    private void require(int i) throws WireParseException {
        if (i > remaining()) {
            throw new WireParseException("end of input");
        }
    }

    public void setActive(int i) {
        if (i > this.limit - this.byteBuffer.position()) {
            throw new IllegalArgumentException("cannot set active region past end of input");
        }
        ByteBuffer byteBuffer = this.byteBuffer;
        byteBuffer.limit(byteBuffer.position() + i);
    }

    public void clearActive() {
        this.byteBuffer.limit(this.limit);
    }

    public int saveActive() {
        return this.byteBuffer.limit() - this.offset;
    }

    public void restoreActive(int i) {
        int i2 = this.offset;
        if (i + i2 > this.limit) {
            throw new IllegalArgumentException("cannot set active region past end of input");
        }
        this.byteBuffer.limit(i + i2);
    }

    public void jump(int i) {
        int i2 = this.offset;
        if (i + i2 >= this.limit) {
            throw new IllegalArgumentException("cannot jump past end of input");
        }
        this.byteBuffer.position(i2 + i);
        this.byteBuffer.limit(this.limit);
    }

    public void save() {
        this.savedPos = this.byteBuffer.position();
        this.savedEnd = this.byteBuffer.limit();
    }

    public void restore() {
        int i = this.savedPos;
        if (i < 0) {
            throw new IllegalStateException("no previous state");
        }
        this.byteBuffer.position(i);
        this.byteBuffer.limit(this.savedEnd);
        this.savedPos = -1;
        this.savedEnd = -1;
    }

    public int readU8() throws WireParseException {
        require(1);
        return this.byteBuffer.get() & 255;
    }

    public int readU16() throws WireParseException {
        require(2);
        return this.byteBuffer.getShort() & 65535;
    }

    public long readU32() throws WireParseException {
        require(4);
        return this.byteBuffer.getInt() & 4294967295L;
    }

    public void readByteArray(byte[] bArr, int i, int i2) throws WireParseException {
        require(i2);
        this.byteBuffer.get(bArr, i, i2);
    }

    public byte[] readByteArray(int i) throws WireParseException {
        require(i);
        byte[] bArr = new byte[i];
        this.byteBuffer.get(bArr, 0, i);
        return bArr;
    }

    public byte[] readByteArray() {
        int remaining = remaining();
        byte[] bArr = new byte[remaining];
        this.byteBuffer.get(bArr, 0, remaining);
        return bArr;
    }

    public byte[] readCountedString() throws WireParseException {
        return readByteArray(readU8());
    }
}
