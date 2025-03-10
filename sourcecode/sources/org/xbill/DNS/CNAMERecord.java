package org.xbill.DNS;

/* loaded from: classes4.dex */
public class CNAMERecord extends SingleCompressedNameBase {
    CNAMERecord() {
    }

    public Name getTarget() {
        return getSingleName();
    }
}
