package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class CAARecord extends Record {
    private int flags;
    private byte[] tag;
    private byte[] value;

    CAARecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.flags = dNSInput.readU8();
        this.tag = dNSInput.readCountedString();
        this.value = dNSInput.readByteArray();
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.flags + " " + Record.byteArrayToString(this.tag, false) + " " + Record.byteArrayToString(this.value, true);
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU8(this.flags);
        dNSOutput.writeCountedString(this.tag);
        dNSOutput.writeByteArray(this.value);
    }
}
