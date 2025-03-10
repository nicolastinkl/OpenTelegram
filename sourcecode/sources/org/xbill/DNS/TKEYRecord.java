package org.xbill.DNS;

import com.tencent.cos.xml.common.RequestMethod;
import j$.time.Instant;
import java.io.IOException;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class TKEYRecord extends Record {
    private Name alg;
    private int error;
    private byte[] key;
    private int mode;
    private byte[] other;
    private Instant timeExpire;
    private Instant timeInception;

    TKEYRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.alg = new Name(dNSInput);
        this.timeInception = Instant.ofEpochSecond(dNSInput.readU32());
        this.timeExpire = Instant.ofEpochSecond(dNSInput.readU32());
        this.mode = dNSInput.readU16();
        this.error = dNSInput.readU16();
        int readU16 = dNSInput.readU16();
        if (readU16 > 0) {
            this.key = dNSInput.readByteArray(readU16);
        } else {
            this.key = null;
        }
        int readU162 = dNSInput.readU16();
        if (readU162 > 0) {
            this.other = dNSInput.readByteArray(readU162);
        } else {
            this.other = null;
        }
    }

    protected String modeString() {
        int i = this.mode;
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? Integer.toString(i) : RequestMethod.DELETE : "RESOLVERASSIGNED" : "GSSAPI" : "DIFFIEHELLMAN" : "SERVERASSIGNED";
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.alg);
        sb.append(" ");
        if (Options.check("multiline")) {
            sb.append("(\n\t");
        }
        sb.append(FormattedTime.format(this.timeInception));
        sb.append(" ");
        sb.append(FormattedTime.format(this.timeExpire));
        sb.append(" ");
        sb.append(modeString());
        sb.append(" ");
        sb.append(Rcode.TSIGstring(this.error));
        if (Options.check("multiline")) {
            sb.append("\n");
            byte[] bArr = this.key;
            if (bArr != null) {
                sb.append(base64.formatString(bArr, 64, "\t", false));
                sb.append("\n");
            }
            byte[] bArr2 = this.other;
            if (bArr2 != null) {
                sb.append(base64.formatString(bArr2, 64, "\t", false));
            }
            sb.append(" )");
        } else {
            sb.append(" ");
            byte[] bArr3 = this.key;
            if (bArr3 != null) {
                sb.append(base64.toString(bArr3));
                sb.append(" ");
            }
            byte[] bArr4 = this.other;
            if (bArr4 != null) {
                sb.append(base64.toString(bArr4));
            }
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        this.alg.toWire(dNSOutput, null, z);
        dNSOutput.writeU32(this.timeInception.getEpochSecond());
        dNSOutput.writeU32(this.timeExpire.getEpochSecond());
        dNSOutput.writeU16(this.mode);
        dNSOutput.writeU16(this.error);
        byte[] bArr = this.key;
        if (bArr != null) {
            dNSOutput.writeU16(bArr.length);
            dNSOutput.writeByteArray(this.key);
        } else {
            dNSOutput.writeU16(0);
        }
        byte[] bArr2 = this.other;
        if (bArr2 != null) {
            dNSOutput.writeU16(bArr2.length);
            dNSOutput.writeByteArray(this.other);
        } else {
            dNSOutput.writeU16(0);
        }
    }
}
