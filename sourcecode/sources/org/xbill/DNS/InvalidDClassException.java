package org.xbill.DNS;

/* loaded from: classes4.dex */
public class InvalidDClassException extends IllegalArgumentException {
    public InvalidDClassException(int i) {
        super("Invalid DNS class: " + i);
    }
}
