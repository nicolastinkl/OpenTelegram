package org.xbill.DNS;

import j$.time.Instant;
import j$.time.ZoneOffset;
import j$.time.format.DateTimeFormatter;

/* loaded from: classes4.dex */
final class FormattedTime {
    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneOffset.UTC);

    public static String format(Instant instant) {
        return DEFAULT_FORMAT.format(instant);
    }
}
