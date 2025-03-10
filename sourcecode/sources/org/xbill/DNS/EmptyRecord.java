package org.xbill.DNS;

/* loaded from: classes4.dex */
class EmptyRecord extends Record {
    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) {
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return "";
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
    }

    EmptyRecord() {
    }
}
