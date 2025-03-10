package org.xbill.DNS;

import java.net.InetAddress;
import java.net.UnknownHostException;

/* loaded from: classes4.dex */
public class ClientSubnetOption extends EDNSOption {
    private InetAddress address;
    private int family;
    private int scopePrefixLength;
    private int sourcePrefixLength;

    ClientSubnetOption() {
        super(8);
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionFromWire(DNSInput dNSInput) throws WireParseException {
        int readU16 = dNSInput.readU16();
        this.family = readU16;
        if (readU16 != 1 && readU16 != 2) {
            throw new WireParseException("unknown address family");
        }
        int readU8 = dNSInput.readU8();
        this.sourcePrefixLength = readU8;
        if (readU8 > Address.addressLength(this.family) * 8) {
            throw new WireParseException("invalid source netmask");
        }
        int readU82 = dNSInput.readU8();
        this.scopePrefixLength = readU82;
        if (readU82 > Address.addressLength(this.family) * 8) {
            throw new WireParseException("invalid scope netmask");
        }
        byte[] readByteArray = dNSInput.readByteArray();
        if (readByteArray.length != (this.sourcePrefixLength + 7) / 8) {
            throw new WireParseException("invalid address");
        }
        byte[] bArr = new byte[Address.addressLength(this.family)];
        System.arraycopy(readByteArray, 0, bArr, 0, readByteArray.length);
        try {
            InetAddress byAddress = InetAddress.getByAddress(bArr);
            this.address = byAddress;
            if (!Address.truncate(byAddress, this.sourcePrefixLength).equals(this.address)) {
                throw new WireParseException("invalid padding");
            }
        } catch (UnknownHostException e) {
            throw new WireParseException("invalid address", e);
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionToWire(DNSOutput dNSOutput) {
        dNSOutput.writeU16(this.family);
        dNSOutput.writeU8(this.sourcePrefixLength);
        dNSOutput.writeU8(this.scopePrefixLength);
        dNSOutput.writeByteArray(this.address.getAddress(), 0, (this.sourcePrefixLength + 7) / 8);
    }

    @Override // org.xbill.DNS.EDNSOption
    String optionToString() {
        return this.address.getHostAddress() + "/" + this.sourcePrefixLength + ", scope netmask " + this.scopePrefixLength;
    }
}
