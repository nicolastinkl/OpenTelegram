package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class CERTRecord extends Record {
    private int alg;
    private byte[] cert;
    private int certType;
    private int keyTag;

    CERTRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.certType = dNSInput.readU16();
        this.keyTag = dNSInput.readU16();
        this.alg = dNSInput.readU8();
        this.cert = dNSInput.readByteArray();
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.certType);
        sb.append(" ");
        sb.append(this.keyTag);
        sb.append(" ");
        sb.append(this.alg);
        if (this.cert != null) {
            if (Options.check("multiline")) {
                sb.append(" (\n");
                sb.append(base64.formatString(this.cert, 64, "\t", true));
            } else {
                sb.append(" ");
                sb.append(base64.toString(this.cert));
            }
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.certType);
        dNSOutput.writeU16(this.keyTag);
        dNSOutput.writeU8(this.alg);
        dNSOutput.writeByteArray(this.cert);
    }
}
