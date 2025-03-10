package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class RPRecord extends Record {
    private Name mailbox;
    private Name textDomain;

    RPRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.mailbox = new Name(dNSInput);
        this.textDomain = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.mailbox + " " + this.textDomain;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.mailbox.toWire(dNSOutput, null, z);
        this.textDomain.toWire(dNSOutput, null, z);
    }
}
