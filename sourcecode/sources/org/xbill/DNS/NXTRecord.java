package org.xbill.DNS;

import java.io.IOException;
import java.util.BitSet;

/* loaded from: classes4.dex */
public class NXTRecord extends Record {
    private BitSet bitmap;
    private Name next;

    NXTRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.next = new Name(dNSInput);
        this.bitmap = new BitSet();
        int remaining = dNSInput.remaining();
        for (int i = 0; i < remaining; i++) {
            int readU8 = dNSInput.readU8();
            for (int i2 = 0; i2 < 8; i2++) {
                if (((1 << (7 - i2)) & readU8) != 0) {
                    this.bitmap.set((i * 8) + i2);
                }
            }
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.next);
        int length = this.bitmap.length();
        for (short s = 0; s < length; s = (short) (s + 1)) {
            if (this.bitmap.get(s)) {
                sb.append(" ");
                sb.append(Type.string(s));
            }
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.next.toWire(dNSOutput, null, z);
        int length = this.bitmap.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i |= this.bitmap.get(i2) ? 1 << (7 - (i2 % 8)) : 0;
            if (i2 % 8 == 7 || i2 == length - 1) {
                dNSOutput.writeU8(i);
                i = 0;
            }
        }
    }
}
