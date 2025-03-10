package org.xbill.DNS;

import org.xbill.DNS.utils.base16;

/* loaded from: classes4.dex */
public class NSAPRecord extends Record {
    private byte[] address;

    NSAPRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) {
        this.address = dNSInput.readByteArray();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeByteArray(this.address);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return "0x" + base16.toString(this.address);
    }
}
