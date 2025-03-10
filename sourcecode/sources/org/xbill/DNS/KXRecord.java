package org.xbill.DNS;

/* loaded from: classes4.dex */
public class KXRecord extends U16NameBase {
    KXRecord() {
    }

    @Override // org.xbill.DNS.Record
    public Name getAdditionalName() {
        return getNameField();
    }
}
