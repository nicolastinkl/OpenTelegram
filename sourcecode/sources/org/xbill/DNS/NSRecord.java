package org.xbill.DNS;

/* loaded from: classes4.dex */
public class NSRecord extends SingleCompressedNameBase {
    NSRecord() {
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getSingleName();
    }
}
