package org.xbill.DNS;

import j$.time.Instant;
import java.io.IOException;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
abstract class SIGBase extends Record {
    protected int alg;
    protected int covered;
    protected Instant expire;
    protected int footprint;
    protected int labels;
    protected long origttl;
    protected byte[] signature;
    protected Name signer;
    protected Instant timeSigned;

    protected SIGBase() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.covered = dNSInput.readU16();
        this.alg = dNSInput.readU8();
        this.labels = dNSInput.readU8();
        this.origttl = dNSInput.readU32();
        this.expire = Instant.ofEpochSecond(dNSInput.readU32());
        this.timeSigned = Instant.ofEpochSecond(dNSInput.readU32());
        this.footprint = dNSInput.readU16();
        this.signer = new Name(dNSInput);
        this.signature = dNSInput.readByteArray();
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Type.string(this.covered));
        sb.append(" ");
        sb.append(this.alg);
        sb.append(" ");
        sb.append(this.labels);
        sb.append(" ");
        sb.append(this.origttl);
        sb.append(" ");
        if (Options.check("multiline")) {
            sb.append("(\n\t");
        }
        sb.append(FormattedTime.format(this.expire));
        sb.append(" ");
        sb.append(FormattedTime.format(this.timeSigned));
        sb.append(" ");
        sb.append(this.footprint);
        sb.append(" ");
        sb.append(this.signer);
        if (Options.check("multiline")) {
            sb.append("\n");
            sb.append(base64.formatString(this.signature, 64, "\t", true));
        } else {
            sb.append(" ");
            sb.append(base64.toString(this.signature));
        }
        return sb.toString();
    }

    public int getTypeCovered() {
        return this.covered;
    }

    @Override // org.xbill.DNS.Record
    public int getRRsetType() {
        return this.covered;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU16(this.covered);
        dNSOutput.writeU8(this.alg);
        dNSOutput.writeU8(this.labels);
        dNSOutput.writeU32(this.origttl);
        dNSOutput.writeU32(this.expire.getEpochSecond());
        dNSOutput.writeU32(this.timeSigned.getEpochSecond());
        dNSOutput.writeU16(this.footprint);
        this.signer.toWire(dNSOutput, null, z);
        dNSOutput.writeByteArray(this.signature);
    }
}
