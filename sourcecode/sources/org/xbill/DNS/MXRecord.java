package org.xbill.DNS;

/* loaded from: classes4.dex */
public class MXRecord extends U16NameBase {
    MXRecord() {
    }

    @Override // org.xbill.DNS.U16NameBase, org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.u16Field);
        this.nameField.toWire(dNSOutput, compression, z);
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getNameField();
    }
}
