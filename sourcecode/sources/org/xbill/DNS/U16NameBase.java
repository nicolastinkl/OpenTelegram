package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
abstract class U16NameBase extends Record {
    protected Name nameField;
    protected int u16Field;

    protected U16NameBase() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.u16Field = dNSInput.readU16();
        this.nameField = new Name(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        return this.u16Field + " " + this.nameField;
    }

    protected Name getNameField() {
        return this.nameField;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.u16Field);
        this.nameField.toWire(dNSOutput, null, z);
    }
}
