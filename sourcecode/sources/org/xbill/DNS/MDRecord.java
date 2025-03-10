package org.xbill.DNS;

/* loaded from: classes4.dex */
public class MDRecord extends SingleNameBase {
    MDRecord() {
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getSingleName();
    }
}
