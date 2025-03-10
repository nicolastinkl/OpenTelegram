package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
abstract class SingleNameBase extends Record {
    protected Name singleName;

    protected SingleNameBase() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.singleName = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.singleName.toString();
    }

    protected Name getSingleName() {
        return this.singleName;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.singleName.toWire(dNSOutput, null, z);
    }
}
