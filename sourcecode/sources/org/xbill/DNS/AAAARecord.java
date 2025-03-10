package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public class AAAARecord extends Record {
    private byte[] address;

    AAAARecord() {
    }

    public AAAARecord(Name name, int i, long j, InetAddress inetAddress) {
        super(name, 28, i, j);
        if (Address.familyOf(inetAddress) != 1 && Address.familyOf(inetAddress) != 2) {
            throw new IllegalArgumentException("invalid IPv4/IPv6 address");
        }
        this.address = inetAddress.getAddress();
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.address = dNSInput.readByteArray(16);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        try {
            InetAddress byAddress = InetAddress.getByAddress(null, this.address);
            if (byAddress.getAddress().length == 4) {
                return "::ffff:" + byAddress.getHostAddress();
            }
            return byAddress.getHostAddress();
        } catch (UnknownHostException unused) {
            return null;
        }
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeByteArray(this.address);
    }
}
