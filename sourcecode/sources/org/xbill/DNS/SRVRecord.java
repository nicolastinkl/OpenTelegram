package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class SRVRecord extends Record {
    private int port;
    private int priority;
    private Name target;
    private int weight;

    SRVRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.priority = dNSInput.readU16();
        this.weight = dNSInput.readU16();
        this.port = dNSInput.readU16();
        this.target = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.priority + " " + this.weight + " " + this.port + " " + this.target;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.priority);
        dNSOutput.writeU16(this.weight);
        dNSOutput.writeU16(this.port);
        this.target.toWire(dNSOutput, null, z);
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return this.target;
    }
}
