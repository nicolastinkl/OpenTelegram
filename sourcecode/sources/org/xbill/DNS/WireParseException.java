package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class WireParseException extends IOException {
    public WireParseException() {
    }

    public WireParseException(String str) {
        super(str);
    }

    public WireParseException(String str, Throwable th) {
        super(str);
        initCause(th);
    }
}
