package org.xbill.DNS;

import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public abstract class EDNSOption {
    private final int code;

    abstract void optionFromWire(DNSInput dNSInput) throws IOException;

    abstract String optionToString();

    abstract void optionToWire(DNSOutput dNSOutput);

    public static class Code {
        private static Mnemonic codes;

        static {
            Mnemonic mnemonic = new Mnemonic("EDNS Option Codes", 1);
            codes = mnemonic;
            mnemonic.setMaximum(65535);
            codes.setPrefix("CODE");
            codes.setNumericAllowed(true);
            codes.add(1, "LLQ");
            codes.add(2, "UL");
            codes.add(3, "NSID");
            codes.add(5, "DAU");
            codes.add(6, "DHU");
            codes.add(7, "N3U");
            codes.add(8, "edns-client-subnet");
            codes.add(9, "EDNS_EXPIRE");
            codes.add(10, "COOKIE");
            codes.add(11, "edns-tcp-keepalive");
            codes.add(12, "Padding");
            codes.add(13, "CHAIN");
            codes.add(14, "edns-key-tag");
            codes.add(15, "Extended_DNS_Error");
            codes.add(16, "EDNS-Client-Tag");
            codes.add(17, "EDNS-Server-Tag");
        }

        public static String string(int i) {
            return codes.getText(i);
        }
    }

    public EDNSOption(int i) {
        this.code = Record.checkU16("code", i);
    }

    public String toString() {
        return "{" + Code.string(this.code) + ": " + optionToString() + "}";
    }

    public int getCode() {
        return this.code;
    }

    byte[] getData() {
        DNSOutput dNSOutput = new DNSOutput();
        optionToWire(dNSOutput);
        return dNSOutput.toByteArray();
    }

    static EDNSOption fromWire(DNSInput dNSInput) throws IOException {
        EDNSOption nSIDOption;
        int readU16 = dNSInput.readU16();
        int readU162 = dNSInput.readU16();
        if (dNSInput.remaining() < readU162) {
            throw new WireParseException("truncated option");
        }
        int saveActive = dNSInput.saveActive();
        dNSInput.setActive(readU162);
        if (readU16 == 3) {
            nSIDOption = new NSIDOption();
        } else if (readU16 == 15) {
            nSIDOption = new ExtendedErrorCodeOption();
        } else if (readU16 == 5 || readU16 == 6 || readU16 == 7) {
            nSIDOption = new DnssecAlgorithmOption(readU16, new int[0]);
        } else if (readU16 == 8) {
            nSIDOption = new ClientSubnetOption();
        } else if (readU16 == 10) {
            nSIDOption = new CookieOption();
        } else if (readU16 == 11) {
            nSIDOption = new TcpKeepaliveOption();
        } else {
            nSIDOption = new GenericEDNSOption(readU16);
        }
        nSIDOption.optionFromWire(dNSInput);
        dNSInput.restoreActive(saveActive);
        return nSIDOption;
    }

    void toWire(DNSOutput dNSOutput) {
        dNSOutput.writeU16(this.code);
        int current = dNSOutput.current();
        dNSOutput.writeU16(0);
        optionToWire(dNSOutput);
        dNSOutput.writeU16At((dNSOutput.current() - current) - 2, current);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof EDNSOption)) {
            return false;
        }
        EDNSOption eDNSOption = (EDNSOption) obj;
        if (this.code != eDNSOption.code) {
            return false;
        }
        return Arrays.equals(getData(), eDNSOption.getData());
    }

    public int hashCode() {
        int i = 0;
        for (byte b : getData()) {
            i += (i << 3) + (b & 255);
        }
        return i;
    }
}
