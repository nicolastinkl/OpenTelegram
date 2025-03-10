package org.xbill.DNS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes4.dex */
public class Message implements Cloneable {
    private Header header;
    private TSIGRecord querytsig;
    private List<Record>[] sections;
    private int size;
    int tsigState;
    private int tsigerror;
    private TSIG tsigkey;
    int tsigstart;

    public void setResolver(Resolver resolver) {
    }

    private Message(Header header) {
        this.sections = new List[4];
        this.header = header;
    }

    public Message(int i) {
        this(new Header(i));
    }

    public Message() {
        this(new Header());
    }

    public static Message newQuery(Record record) {
        Message message = new Message();
        message.header.setOpcode(0);
        message.header.setFlag(7);
        message.addRecord(record, 0);
        return message;
    }

    Message(DNSInput dNSInput) throws IOException {
        this(new Header(dNSInput));
        boolean z = this.header.getOpcode() == 5;
        boolean flag = this.header.getFlag(6);
        for (int i = 0; i < 4; i++) {
            try {
                int count = this.header.getCount(i);
                if (count > 0) {
                    this.sections[i] = new ArrayList(count);
                }
                for (int i2 = 0; i2 < count; i2++) {
                    int current = dNSInput.current();
                    Record fromWire = Record.fromWire(dNSInput, i, z);
                    this.sections[i].add(fromWire);
                    if (i == 3) {
                        if (fromWire.getType() == 250) {
                            this.tsigstart = current;
                            if (i2 != count - 1) {
                                throw new WireParseException("TSIG is not the last record in the message");
                            }
                        }
                        if (fromWire.getType() == 24) {
                            ((SIGRecord) fromWire).getTypeCovered();
                        }
                    }
                }
            } catch (WireParseException e) {
                if (!flag) {
                    throw e;
                }
            }
        }
        this.size = dNSInput.current();
    }

    public Message(byte[] bArr) throws IOException {
        this(new DNSInput(bArr));
    }

    public Header getHeader() {
        return this.header;
    }

    public void addRecord(Record record, int i) {
        List<Record>[] listArr = this.sections;
        if (listArr[i] == null) {
            listArr[i] = new LinkedList();
        }
        this.header.incCount(i);
        this.sections[i].add(record);
    }

    public Record getQuestion() {
        List<Record> list = this.sections[0];
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public TSIGRecord getTSIG() {
        int count = this.header.getCount(3);
        if (count == 0) {
            return null;
        }
        Record record = this.sections[3].get(count - 1);
        if (record.type != 250) {
            return null;
        }
        return (TSIGRecord) record;
    }

    public boolean isSigned() {
        int i = this.tsigState;
        return i == 3 || i == 1 || i == 4;
    }

    public boolean isVerified() {
        return this.tsigState == 1;
    }

    public OPTRecord getOPT() {
        for (Record record : getSection(3)) {
            if (record instanceof OPTRecord) {
                return (OPTRecord) record;
            }
        }
        return null;
    }

    public int getRcode() {
        int rcode = this.header.getRcode();
        OPTRecord opt = getOPT();
        return opt != null ? rcode + (opt.getExtendedRcode() << 4) : rcode;
    }

    public List<Record> getSection(int i) {
        List<Record>[] listArr = this.sections;
        if (listArr[i] == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(listArr[i]);
    }

    private static boolean sameSet(Record record, Record record2) {
        return record.getRRsetType() == record2.getRRsetType() && record.getDClass() == record2.getDClass() && record.getName().equals(record2.getName());
    }

    public List<RRset> getSectionRRsets(int i) {
        if (this.sections[i] == null) {
            return Collections.emptyList();
        }
        LinkedList linkedList = new LinkedList();
        HashSet hashSet = new HashSet();
        for (Record record : getSection(i)) {
            Name name = record.getName();
            boolean z = true;
            if (hashSet.contains(name)) {
                int size = linkedList.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    RRset rRset = (RRset) linkedList.get(size);
                    if (rRset.getType() == record.getRRsetType() && rRset.getDClass() == record.getDClass() && rRset.getName().equals(name)) {
                        rRset.addRR(record);
                        z = false;
                        break;
                    }
                    size--;
                }
            }
            if (z) {
                linkedList.add(new RRset(record));
                hashSet.add(name);
            }
        }
        return linkedList;
    }

    void toWire(DNSOutput dNSOutput) {
        this.header.toWire(dNSOutput);
        Compression compression = new Compression();
        int i = 0;
        while (true) {
            List<Record>[] listArr = this.sections;
            if (i >= listArr.length) {
                return;
            }
            if (listArr[i] != null) {
                Iterator<Record> it = listArr[i].iterator();
                while (it.hasNext()) {
                    it.next().toWire(dNSOutput, i, compression);
                }
            }
            i++;
        }
    }

    private int sectionToWire(DNSOutput dNSOutput, int i, Compression compression, int i2) {
        int size = this.sections[i].size();
        int current = dNSOutput.current();
        Record record = null;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            Record record2 = this.sections[i].get(i5);
            if (i != 3 || !(record2 instanceof OPTRecord)) {
                if (record != null && !sameSet(record2, record)) {
                    current = dNSOutput.current();
                    i4 = i3;
                }
                record2.toWire(dNSOutput, i, compression);
                if (dNSOutput.current() > i2) {
                    dNSOutput.jump(current);
                    return size - i4;
                }
                i3++;
                record = record2;
            }
        }
        return size - i3;
    }

    private void toWire(DNSOutput dNSOutput, int i) {
        if (i < 12) {
            return;
        }
        TSIG tsig = this.tsigkey;
        if (tsig != null) {
            i -= tsig.recordLength();
        }
        OPTRecord opt = getOPT();
        byte[] bArr = null;
        if (opt != null) {
            bArr = opt.toWire(3);
            i -= bArr.length;
        }
        int current = dNSOutput.current();
        this.header.toWire(dNSOutput);
        Compression compression = new Compression();
        int flagsByte = this.header.getFlagsByte();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= 4) {
                break;
            }
            if (this.sections[i2] != null) {
                int sectionToWire = sectionToWire(dNSOutput, i2, compression, i);
                if (sectionToWire != 0 && i2 != 3) {
                    flagsByte = Header.setFlag(flagsByte, 6, true);
                    int count = this.header.getCount(i2) - sectionToWire;
                    int i4 = current + 4;
                    dNSOutput.writeU16At(count, (i2 * 2) + i4);
                    for (int i5 = i2 + 1; i5 < 3; i5++) {
                        dNSOutput.writeU16At(0, (i5 * 2) + i4);
                    }
                } else if (i2 == 3) {
                    i3 = this.header.getCount(i2) - sectionToWire;
                }
            }
            i2++;
        }
        if (bArr != null) {
            dNSOutput.writeByteArray(bArr);
            i3++;
        }
        if (flagsByte != this.header.getFlagsByte()) {
            dNSOutput.writeU16At(flagsByte, current + 2);
        }
        if (i3 != this.header.getCount(3)) {
            dNSOutput.writeU16At(i3, current + 10);
        }
        TSIG tsig2 = this.tsigkey;
        if (tsig2 != null) {
            tsig2.generate(this, dNSOutput.toByteArray(), this.tsigerror, this.querytsig).toWire(dNSOutput, 3, compression);
            dNSOutput.writeU16At(i3 + 1, current + 10);
        }
    }

    public byte[] toWire() {
        DNSOutput dNSOutput = new DNSOutput();
        toWire(dNSOutput);
        this.size = dNSOutput.current();
        return dNSOutput.toByteArray();
    }

    public byte[] toWire(int i) {
        DNSOutput dNSOutput = new DNSOutput();
        toWire(dNSOutput, i);
        this.size = dNSOutput.current();
        return dNSOutput.toByteArray();
    }

    public void setTSIG(TSIG tsig, int i, TSIGRecord tSIGRecord) {
        this.tsigkey = tsig;
        this.tsigerror = i;
        this.querytsig = tSIGRecord;
    }

    public int numBytes() {
        return this.size;
    }

    public String sectionToString(int i) {
        if (i > 3) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Record record : getSection(i)) {
            if (i == 0) {
                sb.append(";;\t");
                sb.append(record.name);
                sb.append(", type = ");
                sb.append(Type.string(record.type));
                sb.append(", class = ");
                sb.append(DClass.string(record.dclass));
            } else {
                sb.append(record);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (getOPT() != null) {
            sb.append(this.header.toStringWithRcode(getRcode()));
            sb.append("\n");
        } else {
            sb.append(this.header);
            sb.append("\n");
        }
        if (isSigned()) {
            sb.append(";; TSIG ");
            if (isVerified()) {
                sb.append("ok");
            } else {
                sb.append("invalid");
            }
            sb.append('\n');
        }
        for (int i = 0; i < 4; i++) {
            if (this.header.getOpcode() != 5) {
                sb.append(";; ");
                sb.append(Section.longString(i));
                sb.append(":\n");
            } else {
                sb.append(";; ");
                sb.append(Section.updString(i));
                sb.append(":\n");
            }
            sb.append(sectionToString(i));
            sb.append("\n");
        }
        sb.append(";; Message size: ");
        sb.append(numBytes());
        sb.append(" bytes");
        return sb.toString();
    }

    public Message clone() {
        try {
            Message message = (Message) super.clone();
            message.sections = new List[this.sections.length];
            int i = 0;
            while (true) {
                List<Record>[] listArr = this.sections;
                if (i >= listArr.length) {
                    break;
                }
                if (listArr[i] != null) {
                    message.sections[i] = new LinkedList(this.sections[i]);
                }
                i++;
            }
            message.header = this.header.clone();
            TSIGRecord tSIGRecord = this.querytsig;
            if (tSIGRecord != null) {
                message.querytsig = (TSIGRecord) tSIGRecord.cloneRecord();
            }
            return message;
        } catch (CloneNotSupportedException e) {
            throw e;
        }
    }
}
