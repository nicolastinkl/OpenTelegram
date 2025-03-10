package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class WKSRecord extends Record {
    private byte[] address;
    private int protocol;
    private int[] services;

    WKSRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.address = dNSInput.readByteArray(4);
        this.protocol = dNSInput.readU8();
        byte[] readByteArray = dNSInput.readByteArray();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < readByteArray.length; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                if ((readByteArray[i] & 255 & (1 << (7 - i2))) != 0) {
                    arrayList.add(Integer.valueOf((i * 8) + i2));
                }
            }
        }
        this.services = new int[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.services[i3] = ((Integer) arrayList.get(i3)).intValue();
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Address.toDottedQuad(this.address));
        sb.append(" ");
        sb.append(this.protocol);
        for (int i : this.services) {
            sb.append(" ");
            sb.append(i);
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeByteArray(this.address);
        dNSOutput.writeU8(this.protocol);
        int[] iArr = this.services;
        byte[] bArr = new byte[(iArr[iArr.length - 1] / 8) + 1];
        for (int i : iArr) {
            int i2 = i / 8;
            bArr[i2] = (byte) ((1 << (7 - (i % 8))) | bArr[i2]);
        }
        dNSOutput.writeByteArray(bArr);
    }
}
