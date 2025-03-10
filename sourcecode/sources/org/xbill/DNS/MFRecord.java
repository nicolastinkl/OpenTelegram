package org.xbill.DNS;

/* loaded from: classes4.dex */
public class MFRecord extends SingleNameBase {
    MFRecord() {
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getSingleName();
    }
}
