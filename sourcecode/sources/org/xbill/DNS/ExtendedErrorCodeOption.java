package org.xbill.DNS;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* loaded from: classes4.dex */
public class ExtendedErrorCodeOption extends EDNSOption {
    private static final Mnemonic codes;
    private int errorCode;
    private String text;

    static {
        Mnemonic mnemonic = new Mnemonic("EDNS Extended Error Codes", 1);
        codes = mnemonic;
        mnemonic.setMaximum(65535);
        mnemonic.setPrefix("EDE");
        mnemonic.add(0, "Other");
        mnemonic.add(1, "Unsupported DNSKEY Algorithm");
        mnemonic.add(2, "Unsupported DS Digest Type");
        mnemonic.add(3, "Stale Answer");
        mnemonic.add(4, "Forged Answer");
        mnemonic.add(5, "DNSSEC Indeterminate");
        mnemonic.add(6, "DNSSEC Bogus");
        mnemonic.add(7, "Signature Expired");
        mnemonic.add(8, "Signature Not Yet Valid");
        mnemonic.add(9, "DNSKEY Missing");
        mnemonic.add(10, "RRSIGs Missing");
        mnemonic.add(11, "No Zone Key Bit Set");
        mnemonic.add(12, "NSEC Missing");
        mnemonic.add(13, "Cached Error");
        mnemonic.add(14, "Not Ready");
        mnemonic.add(15, "Blocked");
        mnemonic.add(16, "Censored");
        mnemonic.add(17, "Filtered");
        mnemonic.add(18, "Prohibited");
        mnemonic.add(19, "Stale NXDOMAIN Answer");
        mnemonic.add(20, "Not Authoritative");
        mnemonic.add(21, "Not Supported");
        mnemonic.add(22, "No Reachable Authority");
        mnemonic.add(23, "Network Error");
        mnemonic.add(24, "Invalid Data");
    }

    ExtendedErrorCodeOption() {
        super(15);
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionFromWire(DNSInput dNSInput) throws IOException {
        this.errorCode = dNSInput.readU16();
        if (dNSInput.remaining() > 0) {
            byte[] readByteArray = dNSInput.readByteArray();
            int length = readByteArray.length;
            if (readByteArray[readByteArray.length - 1] == 0) {
                length--;
            }
            this.text = new String(readByteArray, 0, length, StandardCharsets.UTF_8);
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionToWire(DNSOutput dNSOutput) {
        dNSOutput.writeU16(this.errorCode);
        String str = this.text;
        if (str == null || str.length() <= 0) {
            return;
        }
        dNSOutput.writeByteArray(this.text.getBytes(StandardCharsets.UTF_8));
    }

    @Override // org.xbill.DNS.EDNSOption
    String optionToString() {
        if (this.text == null) {
            return codes.getText(this.errorCode);
        }
        return codes.getText(this.errorCode) + ": " + this.text;
    }
}
