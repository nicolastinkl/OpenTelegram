package org.xbill.DNS;

import j$.time.Duration;
import j$.util.OptionalInt;
import java.io.IOException;

/* loaded from: classes4.dex */
public class TcpKeepaliveOption extends EDNSOption {
    private OptionalInt timeout;

    static {
        Duration.ofMillis(6553600L);
    }

    public TcpKeepaliveOption() {
        super(11);
        this.timeout = OptionalInt.empty();
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionFromWire(DNSInput dNSInput) throws IOException {
        int remaining = dNSInput.remaining();
        if (remaining == 0) {
            this.timeout = OptionalInt.empty();
            return;
        }
        if (remaining == 2) {
            this.timeout = OptionalInt.of(dNSInput.readU16());
            return;
        }
        throw new WireParseException("invalid length (" + remaining + ") of the data in the edns_tcp_keepalive option");
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionToWire(DNSOutput dNSOutput) {
        if (this.timeout.isPresent()) {
            dNSOutput.writeU16(this.timeout.getAsInt());
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    String optionToString() {
        return this.timeout.isPresent() ? String.valueOf(this.timeout.getAsInt()) : "-";
    }
}
