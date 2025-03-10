package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class OPTRecord extends Record {
    private List<EDNSOption> options;

    OPTRecord() {
    }

    public OPTRecord(int i, int i2, int i3, int i4, List<EDNSOption> list) {
        super(Name.root, 41, i, 0L);
        Record.checkU16("payloadSize", i);
        Record.checkU8("xrcode", i2);
        Record.checkU8("version", i3);
        Record.checkU16("flags", i4);
        this.ttl = (i2 << 24) + (i3 << 16) + i4;
        if (list != null) {
            this.options = new ArrayList(list);
        }
    }

    public OPTRecord(int i, int i2, int i3, int i4) {
        this(i, i2, i3, i4, null);
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        if (dNSInput.remaining() > 0) {
            this.options = new ArrayList();
        }
        while (dNSInput.remaining() > 0) {
            this.options.add(EDNSOption.fromWire(dNSInput));
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        List<EDNSOption> list = this.options;
        if (list != null) {
            sb.append(list);
            sb.append(" ");
        }
        sb.append(" ; payload ");
        sb.append(getPayloadSize());
        sb.append(", xrcode ");
        sb.append(getExtendedRcode());
        sb.append(", version ");
        sb.append(getVersion());
        sb.append(", flags ");
        sb.append(getFlags());
        return sb.toString();
    }

    public int getPayloadSize() {
        return this.dclass;
    }

    public int getExtendedRcode() {
        return (int) (this.ttl >>> 24);
    }

    public int getVersion() {
        return (int) ((this.ttl >>> 16) & 255);
    }

    public int getFlags() {
        return (int) (this.ttl & 65535);
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        List<EDNSOption> list = this.options;
        if (list == null) {
            return;
        }
        Iterator<EDNSOption> it = list.iterator();
        while (it.hasNext()) {
            it.next().toWire(dNSOutput);
        }
    }

    @Override // org.xbill.DNS.Record
    public boolean equals(Object obj) {
        return super.equals(obj) && this.ttl == ((OPTRecord) obj).ttl;
    }

    @Override // org.xbill.DNS.Record
    public int hashCode() {
        int i = 0;
        for (byte b : toWireCanonical()) {
            i += (i << 3) + (b & 255);
        }
        return i;
    }
}
