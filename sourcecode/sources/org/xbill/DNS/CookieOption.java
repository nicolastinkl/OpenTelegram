package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base16;

/* loaded from: classes4.dex */
public class CookieOption extends EDNSOption {
    private byte[] clientCookie;
    private byte[] serverCookie;

    CookieOption() {
        super(10);
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionFromWire(DNSInput dNSInput) throws IOException {
        int remaining = dNSInput.remaining();
        if (remaining < 8) {
            throw new WireParseException("invalid length of client cookie");
        }
        this.clientCookie = dNSInput.readByteArray(8);
        if (remaining > 8) {
            if (remaining < 16 || remaining > 40) {
                throw new WireParseException("invalid length of server cookie");
            }
            this.serverCookie = dNSInput.readByteArray();
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionToWire(DNSOutput dNSOutput) {
        dNSOutput.writeByteArray(this.clientCookie);
        byte[] bArr = this.serverCookie;
        if (bArr != null) {
            dNSOutput.writeByteArray(bArr);
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    String optionToString() {
        if (this.serverCookie != null) {
            return base16.toString(this.clientCookie) + " " + base16.toString(this.serverCookie);
        }
        return base16.toString(this.clientCookie);
    }
}
