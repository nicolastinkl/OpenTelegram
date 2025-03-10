package org.xbill.DNS;

import j$.time.Duration;
import j$.time.Instant;
import java.io.IOException;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class TSIGRecord extends Record {
    private Name alg;
    private int error;
    private Duration fudge;
    private int originalID;
    private byte[] other;
    private byte[] signature;
    private Instant timeSigned;

    TSIGRecord() {
    }

    public TSIGRecord(Name name, int i, long j, Name name2, Instant instant, Duration duration, byte[] bArr, int i2, int i3, byte[] bArr2) {
        super(name, 250, i, j);
        this.alg = Record.checkName("alg", name2);
        this.timeSigned = instant;
        Record.checkU16("fudge", (int) duration.getSeconds());
        this.fudge = duration;
        this.signature = bArr;
        this.originalID = Record.checkU16("originalID", i2);
        this.error = Record.checkU16("error", i3);
        this.other = bArr2;
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.alg = new Name(dNSInput);
        this.timeSigned = Instant.ofEpochSecond((dNSInput.readU16() << 32) + dNSInput.readU32());
        this.fudge = Duration.ofSeconds(dNSInput.readU16());
        this.signature = dNSInput.readByteArray(dNSInput.readU16());
        this.originalID = dNSInput.readU16();
        this.error = dNSInput.readU16();
        int readU16 = dNSInput.readU16();
        if (readU16 > 0) {
            this.other = dNSInput.readByteArray(readU16);
        } else {
            this.other = null;
        }
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.alg);
        sb.append(" ");
        if (Options.check("multiline")) {
            sb.append("(\n\t");
        }
        sb.append(this.timeSigned.getEpochSecond());
        sb.append(" ");
        sb.append((int) this.fudge.getSeconds());
        sb.append(" ");
        sb.append(this.signature.length);
        if (Options.check("multiline")) {
            sb.append("\n");
            sb.append(base64.formatString(this.signature, 64, "\t", false));
        } else {
            sb.append(" ");
            sb.append(base64.toString(this.signature));
        }
        sb.append(" ");
        sb.append(Rcode.TSIGstring(this.error));
        sb.append(" ");
        byte[] bArr = this.other;
        if (bArr == null) {
            sb.append(0);
        } else {
            sb.append(bArr.length);
            if (Options.check("multiline")) {
                sb.append("\n\n\n\t");
            } else {
                sb.append(" ");
            }
            if (this.error == 18) {
                if (this.other.length != 6) {
                    sb.append("<invalid BADTIME other data>");
                } else {
                    sb.append("<server time: ");
                    sb.append(Instant.ofEpochSecond(((r1[0] & 255) << 40) + ((r1[1] & 255) << 32) + ((r1[2] & 255) << 24) + ((r1[3] & 255) << 16) + ((r1[4] & 255) << 8) + (r1[5] & 255)));
                    sb.append(">");
                }
            } else {
                sb.append("<");
                sb.append(base64.toString(this.other));
                sb.append(">");
            }
        }
        if (Options.check("multiline")) {
            sb.append(" )");
        }
        return sb.toString();
    }

    public Name getAlgorithm() {
        return this.alg;
    }

    public Instant getTimeSigned() {
        return this.timeSigned;
    }

    public Duration getFudge() {
        return this.fudge;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public int getError() {
        return this.error;
    }

    public byte[] getOther() {
        return this.other;
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.alg.toWire(dNSOutput, null, z);
        long epochSecond = this.timeSigned.getEpochSecond();
        dNSOutput.writeU16((int) (epochSecond >> 32));
        dNSOutput.writeU32(epochSecond & 4294967295L);
        dNSOutput.writeU16((int) this.fudge.getSeconds());
        dNSOutput.writeU16(this.signature.length);
        dNSOutput.writeByteArray(this.signature);
        dNSOutput.writeU16(this.originalID);
        dNSOutput.writeU16(this.error);
        byte[] bArr = this.other;
        if (bArr != null) {
            dNSOutput.writeU16(bArr.length);
            dNSOutput.writeByteArray(this.other);
        } else {
            dNSOutput.writeU16(0);
        }
    }
}
