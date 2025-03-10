package org.xbill.DNS;

import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class OPENPGPKEYRecord extends Record {
    private byte[] cert;

    OPENPGPKEYRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) {
        this.cert = dNSInput.readByteArray();
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        if (this.cert != null) {
            if (Options.check("multiline")) {
                sb.append("(\n");
                sb.append(base64.formatString(this.cert, 64, "\t", true));
            } else {
                sb.append(base64.toString(this.cert));
            }
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeByteArray(this.cert);
    }
}
