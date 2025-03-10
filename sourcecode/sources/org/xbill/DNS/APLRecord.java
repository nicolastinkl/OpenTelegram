package org.xbill.DNS;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xbill.DNS.utils.base16;

/* loaded from: classes4.dex */
public class APLRecord extends Record {
    private List<Element> elements;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean validatePrefixLength(int i, int i2) {
        if (i2 < 0 || i2 >= 256) {
            return false;
        }
        if (i != 1 || i2 <= 32) {
            return i != 2 || i2 <= 128;
        }
        return false;
    }

    public static class Element {
        public final Object address;
        public final int family;
        public final boolean negative;
        public final int prefixLength;

        private Element(int i, boolean z, Object obj, int i2) {
            this.family = i;
            this.negative = z;
            this.address = obj;
            this.prefixLength = i2;
            if (!APLRecord.validatePrefixLength(i, i2)) {
                throw new IllegalArgumentException("invalid prefix length");
            }
        }

        public Element(boolean z, InetAddress inetAddress, int i) {
            this(Address.familyOf(inetAddress), z, inetAddress, i);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.negative) {
                sb.append("!");
            }
            sb.append(this.family);
            sb.append(":");
            int i = this.family;
            if (i == 1 || i == 2) {
                sb.append(((InetAddress) this.address).getHostAddress());
            } else {
                sb.append(base16.toString((byte[]) this.address));
            }
            sb.append("/");
            sb.append(this.prefixLength);
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Element)) {
                return false;
            }
            Element element = (Element) obj;
            return this.family == element.family && this.negative == element.negative && this.prefixLength == element.prefixLength && this.address.equals(element.address);
        }

        public int hashCode() {
            return this.address.hashCode() + this.prefixLength + (this.negative ? 1 : 0);
        }
    }

    APLRecord() {
    }

    private static byte[] parseAddress(byte[] bArr, int i) throws WireParseException {
        if (bArr.length > i) {
            throw new WireParseException("invalid address length");
        }
        if (bArr.length == i) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        Element element;
        this.elements = new ArrayList(1);
        while (dNSInput.remaining() != 0) {
            int readU16 = dNSInput.readU16();
            int readU8 = dNSInput.readU8();
            int readU82 = dNSInput.readU8();
            boolean z = (readU82 & 128) != 0;
            byte[] readByteArray = dNSInput.readByteArray(readU82 & (-129));
            if (!validatePrefixLength(readU16, readU8)) {
                throw new WireParseException("invalid prefix length");
            }
            if (readU16 == 1 || readU16 == 2) {
                element = new Element(z, InetAddress.getByAddress(parseAddress(readByteArray, Address.addressLength(readU16))), readU8);
            } else {
                element = new Element(readU16, z, readByteArray, readU8);
            }
            this.elements.add(element);
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Element> it = this.elements.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private static int addressLength(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            if (bArr[length] != 0) {
                return length + 1;
            }
        }
        return 0;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        byte[] address;
        int addressLength;
        for (Element element : this.elements) {
            int i = element.family;
            if (i == 1 || i == 2) {
                address = ((InetAddress) element.address).getAddress();
                addressLength = addressLength(address);
            } else {
                address = (byte[]) element.address;
                addressLength = address.length;
            }
            int i2 = element.negative ? addressLength | 128 : addressLength;
            dNSOutput.writeU16(element.family);
            dNSOutput.writeU8(element.prefixLength);
            dNSOutput.writeU8(i2);
            dNSOutput.writeByteArray(address, 0, addressLength);
        }
    }
}
