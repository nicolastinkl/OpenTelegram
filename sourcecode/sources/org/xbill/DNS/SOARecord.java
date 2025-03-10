package org.xbill.DNS;

import java.io.IOException;

/* loaded from: classes4.dex */
public class SOARecord extends Record {
    private Name admin;
    private long expire;
    private Name host;
    private long minimum;
    private long refresh;
    private long retry;
    private long serial;

    SOARecord() {
    }

    public SOARecord(Name name, int i, long j, Name name2, Name name3, long j2, long j3, long j4, long j5, long j6) {
        super(name, 6, i, j);
        this.host = Record.checkName("host", name2);
        this.admin = Record.checkName("admin", name3);
        this.serial = Record.checkU32("serial", j2);
        this.refresh = Record.checkU32("refresh", j3);
        this.retry = Record.checkU32("retry", j4);
        this.expire = Record.checkU32("expire", j5);
        this.minimum = Record.checkU32("minimum", j6);
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.host = new Name(dNSInput);
        this.admin = new Name(dNSInput);
        this.serial = dNSInput.readU32();
        this.refresh = dNSInput.readU32();
        this.retry = dNSInput.readU32();
        this.expire = dNSInput.readU32();
        this.minimum = dNSInput.readU32();
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.host);
        sb.append(" ");
        sb.append(this.admin);
        if (Options.check("multiline")) {
            sb.append(" (\n\t\t\t\t\t");
            sb.append(this.serial);
            sb.append("\t; serial\n\t\t\t\t\t");
            sb.append(this.refresh);
            sb.append("\t; refresh\n\t\t\t\t\t");
            sb.append(this.retry);
            sb.append("\t; retry\n\t\t\t\t\t");
            sb.append(this.expire);
            sb.append("\t; expire\n\t\t\t\t\t");
            sb.append(this.minimum);
            sb.append(" )\t; minimum");
        } else {
            sb.append(" ");
            sb.append(this.serial);
            sb.append(" ");
            sb.append(this.refresh);
            sb.append(" ");
            sb.append(this.retry);
            sb.append(" ");
            sb.append(this.expire);
            sb.append(" ");
            sb.append(this.minimum);
        }
        return sb.toString();
    }

    public long getSerial() {
        return this.serial;
    }

    public long getMinimum() {
        return this.minimum;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.host.toWire(dNSOutput, compression, z);
        this.admin.toWire(dNSOutput, compression, z);
        dNSOutput.writeU32(this.serial);
        dNSOutput.writeU32(this.refresh);
        dNSOutput.writeU32(this.retry);
        dNSOutput.writeU32(this.expire);
        dNSOutput.writeU32(this.minimum);
    }
}
