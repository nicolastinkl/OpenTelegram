package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class X25Record extends Record {
    private byte[] address;

    X25Record() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.address = dNSInput.readCountedString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeCountedString(this.address);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return Record.byteArrayToString(this.address, true);
    }
}
