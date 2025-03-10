package org.xbill.DNS;

/* loaded from: classes4.dex */
public class MBRecord extends SingleNameBase {
    MBRecord() {
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getSingleName();
    }
}
