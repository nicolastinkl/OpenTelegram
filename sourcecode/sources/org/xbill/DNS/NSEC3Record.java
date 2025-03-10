package org.xbill.DNS;

import java.io.IOException;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base32;

/* loaded from: classes4.dex */
public class NSEC3Record extends Record {
    private static final base32 b32 = new base32("0123456789ABCDEFGHIJKLMNOPQRSTUV=", false, false);
    private int flags;
    private int hashAlg;
    private int iterations;
    private byte[] next;
    private byte[] salt;
    private TypeBitmap types;

    NSEC3Record() {
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        this.hashAlg = dNSInput.readU8();
        this.flags = dNSInput.readU8();
        this.iterations = dNSInput.readU16();
        int readU8 = dNSInput.readU8();
        if (readU8 > 0) {
            this.salt = dNSInput.readByteArray(readU8);
        } else {
            this.salt = null;
        }
        this.next = dNSInput.readByteArray(dNSInput.readU8());
        this.types = new TypeBitmap(dNSInput);
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(DNSOutput dNSOutput, Compression compression, boolean z) {
        dNSOutput.writeU8(this.hashAlg);
        dNSOutput.writeU8(this.flags);
        dNSOutput.writeU16(this.iterations);
        byte[] bArr = this.salt;
        if (bArr != null) {
            dNSOutput.writeU8(bArr.length);
            dNSOutput.writeByteArray(this.salt);
        } else {
            dNSOutput.writeU8(0);
        }
        dNSOutput.writeU8(this.next.length);
        dNSOutput.writeByteArray(this.next);
        this.types.toWire(dNSOutput);
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.hashAlg);
        sb.append(' ');
        sb.append(this.flags);
        sb.append(' ');
        sb.append(this.iterations);
        sb.append(' ');
        byte[] bArr = this.salt;
        if (bArr == null) {
            sb.append('-');
        } else {
            sb.append(base16.toString(bArr));
        }
        sb.append(' ');
        sb.append(b32.toString(this.next));
        if (!this.types.empty()) {
            sb.append(' ');
            sb.append(this.types.toString());
        }
        return sb.toString();
    }
}
