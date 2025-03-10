package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class MINFORecord extends Record {
    private Name errorAddress;
    private Name responsibleAddress;

    MINFORecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.responsibleAddress = new Name(dNSInput);
        this.errorAddress = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.responsibleAddress + " " + this.errorAddress;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.responsibleAddress.toWire(dNSOutput, null, z);
        this.errorAddress.toWire(dNSOutput, null, z);
    }
}
