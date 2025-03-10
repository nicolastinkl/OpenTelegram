package org.xbill.DNS;

/* loaded from: classes4.dex */
abstract class SingleCompressedNameBase extends SingleNameBase {
    protected SingleCompressedNameBase() {
    }

    @Override // org.xbill.DNS.SingleNameBase, org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.singleName.toWire(dNSOutput, compression, z);
    }
}
