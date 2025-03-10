package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class NAPTRRecord extends Record {
    private byte[] flags;
    private int order;
    private int preference;
    private byte[] regexp;
    private Name replacement;
    private byte[] service;

    NAPTRRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.order = dNSInput.readU16();
        this.preference = dNSInput.readU16();
        this.flags = dNSInput.readCountedString();
        this.service = dNSInput.readCountedString();
        this.regexp = dNSInput.readCountedString();
        this.replacement = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.order + " " + this.preference + " " + Record.byteArrayToString(this.flags, true) + " " + Record.byteArrayToString(this.service, true) + " " + Record.byteArrayToString(this.regexp, true) + " " + this.replacement;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.order);
        dNSOutput.writeU16(this.preference);
        dNSOutput.writeCountedString(this.flags);
        dNSOutput.writeCountedString(this.service);
        dNSOutput.writeCountedString(this.regexp);
        this.replacement.toWire(dNSOutput, null, z);
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return this.replacement;
    }
}
