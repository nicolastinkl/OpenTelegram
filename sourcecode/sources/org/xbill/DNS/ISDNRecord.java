package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class ISDNRecord extends Record {
    private byte[] address;
    private byte[] subAddress;

    ISDNRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.address = dNSInput.readCountedString();
        if (dNSInput.remaining() > 0) {
            this.subAddress = dNSInput.readCountedString();
        }
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeCountedString(this.address);
        byte[] bArr = this.subAddress;
        if (bArr != null) {
            dNSOutput.writeCountedString(bArr);
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Record.byteArrayToString(this.address, true));
        if (this.subAddress != null) {
            sb.append(" ");
            sb.append(Record.byteArrayToString(this.subAddress, true));
        }
        return sb.toString();
    }
}
