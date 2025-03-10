package org.xbill.DNS;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes4.dex */
public class Header implements Cloneable {
    private static final Random random = new SecureRandom();
    private int[] counts;
    private int flags;
    private int id;

    public Header(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("DNS message ID " + i + " is out of range");
        }
        this.counts = new int[4];
        this.flags = 0;
        this.id = i;
    }

    public Header() {
        this(random.nextInt(65535));
    }

    Header(DNSInput dNSInput) throws IOException {
        this(dNSInput.readU16());
        this.flags = dNSInput.readU16();
        int i = 0;
        while (true) {
            int[] iArr = this.counts;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = dNSInput.readU16();
            i++;
        }
    }

    void toWire(DNSOutput dNSOutput) {
        dNSOutput.writeU16(getID());
        dNSOutput.writeU16(this.flags);
        for (int i : this.counts) {
            dNSOutput.writeU16(i);
        }
    }

    public byte[] toWire() {
        DNSOutput dNSOutput = new DNSOutput();
        toWire(dNSOutput);
        return dNSOutput.toByteArray();
    }

    private static boolean validFlag(int i) {
        return i >= 0 && i <= 15 && Flags.isFlag(i);
    }

    private static void checkFlag(int i) {
        if (validFlag(i)) {
            return;
        }
        throw new IllegalArgumentException("invalid flag bit " + i);
    }

    static int setFlag(int i, int i2, boolean z) {
        checkFlag(i2);
        return z ? i | (1 << (15 - i2)) : i & (~(1 << (15 - i2)));
    }

    public void setFlag(int i) {
        checkFlag(i);
        this.flags = setFlag(this.flags, i, true);
    }

    public boolean getFlag(int i) {
        checkFlag(i);
        return ((1 << (15 - i)) & this.flags) != 0;
    }

    public int getID() {
        return this.id;
    }

    public int getRcode() {
        return this.flags & 15;
    }

    public void setOpcode(int i) {
        if (i < 0 || i > 15) {
            throw new IllegalArgumentException("DNS Opcode " + i + "is out of range");
        }
        int i2 = this.flags & 34815;
        this.flags = i2;
        this.flags = (i << 11) | i2;
    }

    public int getOpcode() {
        return (this.flags >> 11) & 15;
    }

    void incCount(int i) {
        int[] iArr = this.counts;
        if (iArr[i] == 65535) {
            throw new IllegalStateException("DNS section count cannot be incremented");
        }
        iArr[i] = iArr[i] + 1;
    }

    void decCount(int i) {
        int[] iArr = this.counts;
        if (iArr[i] == 0) {
            throw new IllegalStateException("DNS section count cannot be decremented");
        }
        iArr[i] = iArr[i] - 1;
    }

    public int getCount(int i) {
        return this.counts[i];
    }

    int getFlagsByte() {
        return this.flags;
    }

    public String printFlags() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            if (validFlag(i) && getFlag(i)) {
                sb.append(Flags.string(i));
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    String toStringWithRcode(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(";; ->>HEADER<<- ");
        sb.append("opcode: ");
        sb.append(Opcode.string(getOpcode()));
        sb.append(", status: ");
        sb.append(Rcode.string(i));
        sb.append(", id: ");
        sb.append(getID());
        sb.append("\n");
        sb.append(";; flags: ");
        sb.append(printFlags());
        sb.append("; ");
        for (int i2 = 0; i2 < 4; i2++) {
            sb.append(Section.string(i2));
            sb.append(": ");
            sb.append(getCount(i2));
            sb.append(" ");
        }
        return sb.toString();
    }

    public String toString() {
        return toStringWithRcode(getRcode());
    }

    public Header clone() {
        try {
            Header header = (Header) super.clone();
            header.id = this.id;
            header.flags = this.flags;
            int[] iArr = new int[header.counts.length];
            header.counts = iArr;
            int[] iArr2 = this.counts;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            return header;
        } catch (CloneNotSupportedException e) {
            throw e;
        }
    }
}
