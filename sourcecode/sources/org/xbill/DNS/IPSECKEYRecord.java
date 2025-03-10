package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class IPSECKEYRecord extends Record {
    private int algorithmType;
    private Object gateway;
    private int gatewayType;
    private byte[] key;
    private int precedence;

    IPSECKEYRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.precedence = dNSInput.readU8();
        this.gatewayType = dNSInput.readU8();
        this.algorithmType = dNSInput.readU8();
        int i = this.gatewayType;
        if (i == 0) {
            this.gateway = null;
        } else if (i == 1) {
            this.gateway = InetAddress.getByAddress(dNSInput.readByteArray(4));
        } else if (i == 2) {
            this.gateway = InetAddress.getByAddress(dNSInput.readByteArray(16));
        } else if (i == 3) {
            this.gateway = new Name(dNSInput);
        } else {
            throw new WireParseException("invalid gateway type");
        }
        if (dNSInput.remaining() > 0) {
            this.key = dNSInput.readByteArray();
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.precedence);
        sb.append(" ");
        sb.append(this.gatewayType);
        sb.append(" ");
        sb.append(this.algorithmType);
        sb.append(" ");
        int i = this.gatewayType;
        if (i == 0) {
            sb.append(".");
        } else if (i == 1 || i == 2) {
            sb.append(((InetAddress) this.gateway).getHostAddress());
        } else if (i == 3) {
            sb.append(this.gateway);
        }
        if (this.key != null) {
            sb.append(" ");
            sb.append(base64.toString(this.key));
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU8(this.precedence);
        dNSOutput.writeU8(this.gatewayType);
        dNSOutput.writeU8(this.algorithmType);
        int i = this.gatewayType;
        if (i == 1 || i == 2) {
            dNSOutput.writeByteArray(((InetAddress) this.gateway).getAddress());
        } else if (i == 3) {
            ((Name) this.gateway).toWire(dNSOutput, null, z);
        }
        byte[] bArr = this.key;
        if (bArr != null) {
            dNSOutput.writeByteArray(bArr);
        }
    }
}
