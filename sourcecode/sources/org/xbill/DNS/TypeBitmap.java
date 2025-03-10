package org.xbill.DNS;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

/* loaded from: classes4.dex */
final class TypeBitmap implements Serializable {
    private TreeSet<Integer> types;

    private TypeBitmap() {
        this.types = new TreeSet<>();
    }

    public TypeBitmap(DNSInput dNSInput) throws WireParseException {
        this();
        while (dNSInput.remaining() > 0) {
            if (dNSInput.remaining() < 2) {
                throw new WireParseException("invalid bitmap descriptor");
            }
            int readU8 = dNSInput.readU8();
            if (readU8 < -1) {
                throw new WireParseException("invalid ordering");
            }
            int readU82 = dNSInput.readU8();
            if (readU82 > dNSInput.remaining()) {
                throw new WireParseException("invalid bitmap");
            }
            for (int i = 0; i < readU82; i++) {
                int readU83 = dNSInput.readU8();
                if (readU83 != 0) {
                    for (int i2 = 0; i2 < 8; i2++) {
                        if (((1 << (7 - i2)) & readU83) != 0) {
                            this.types.add(Integer.valueOf((readU8 * 256) + (i * 8) + i2));
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = this.types.iterator();
        while (it.hasNext()) {
            sb.append(Type.string(it.next().intValue()));
            if (it.hasNext()) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    private static void mapToWire(DNSOutput dNSOutput, TreeSet<Integer> treeSet, int i) {
        int intValue = ((treeSet.last().intValue() & 255) / 8) + 1;
        int[] iArr = new int[intValue];
        dNSOutput.writeU8(i);
        dNSOutput.writeU8(intValue);
        Iterator<Integer> it = treeSet.iterator();
        while (it.hasNext()) {
            int intValue2 = it.next().intValue();
            int i2 = (intValue2 & 255) / 8;
            iArr[i2] = (1 << (7 - (intValue2 % 8))) | iArr[i2];
        }
        for (int i3 = 0; i3 < intValue; i3++) {
            dNSOutput.writeU8(iArr[i3]);
        }
    }

    public void toWire(DNSOutput dNSOutput) {
        if (this.types.size() == 0) {
            return;
        }
        int i = -1;
        TreeSet treeSet = new TreeSet();
        Iterator<Integer> it = this.types.iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            int i2 = intValue >> 8;
            if (i2 != i) {
                if (treeSet.size() > 0) {
                    mapToWire(dNSOutput, treeSet, i);
                    treeSet.clear();
                }
                i = i2;
            }
            treeSet.add(Integer.valueOf(intValue));
        }
        mapToWire(dNSOutput, treeSet, i);
    }

    public boolean empty() {
        return this.types.isEmpty();
    }
}
