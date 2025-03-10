package org.xbill.DNS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lombok.Generated;

/* loaded from: classes4.dex */
public class RRset implements Serializable {
    private short position;
    private final ArrayList<Record> rrs;
    private final ArrayList<RRSIGRecord> sigs;
    private long ttl;

    @Generated
    protected boolean canEqual(Object obj) {
        return obj instanceof RRset;
    }

    @Generated
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RRset)) {
            return false;
        }
        RRset rRset = (RRset) obj;
        if (!rRset.canEqual(this)) {
            return false;
        }
        ArrayList<Record> arrayList = this.rrs;
        ArrayList<Record> arrayList2 = rRset.rrs;
        if (arrayList != null ? !arrayList.equals(arrayList2) : arrayList2 != null) {
            return false;
        }
        ArrayList<RRSIGRecord> arrayList3 = this.sigs;
        ArrayList<RRSIGRecord> arrayList4 = rRset.sigs;
        return arrayList3 != null ? arrayList3.equals(arrayList4) : arrayList4 == null;
    }

    @Generated
    public int hashCode() {
        ArrayList<Record> arrayList = this.rrs;
        int hashCode = arrayList == null ? 43 : arrayList.hashCode();
        ArrayList<RRSIGRecord> arrayList2 = this.sigs;
        return ((hashCode + 59) * 59) + (arrayList2 != null ? arrayList2.hashCode() : 43);
    }

    public RRset() {
        this.rrs = new ArrayList<>(1);
        this.sigs = new ArrayList<>(0);
    }

    public RRset(Record record) {
        this();
        addRR(record);
    }

    public RRset(RRset rRset) {
        this.rrs = new ArrayList<>(rRset.rrs);
        this.sigs = new ArrayList<>(rRset.sigs);
        this.position = rRset.position;
        this.ttl = rRset.ttl;
    }

    public void addRR(Record record) {
        if (record instanceof RRSIGRecord) {
            addRR((RRSIGRecord) record, this.sigs);
        } else {
            addRR(record, this.rrs);
        }
    }

    private <X extends Record> void addRR(X x, List<X> list) {
        if (this.sigs.isEmpty() && this.rrs.isEmpty()) {
            list.add(x);
            this.ttl = x.getTTL();
            return;
        }
        checkSameRRset(x, this.rrs);
        checkSameRRset(x, this.sigs);
        if (x.getTTL() > this.ttl) {
            x = (X) x.cloneRecord();
            x.setTTL(this.ttl);
        } else if (x.getTTL() < this.ttl) {
            this.ttl = x.getTTL();
            adjustTtl(x.getTTL(), this.rrs);
            adjustTtl(x.getTTL(), this.sigs);
        }
        if (list.contains(x)) {
            return;
        }
        list.add(x);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <X extends Record> void adjustTtl(long j, List<X> list) {
        for (int i = 0; i < list.size(); i++) {
            Record cloneRecord = ((Record) list.get(i)).cloneRecord();
            cloneRecord.setTTL(j);
            list.set(i, cloneRecord);
        }
    }

    private void checkSameRRset(Record record, List<? extends Record> list) {
        if (!list.isEmpty() && !record.sameRRset(list.get(0))) {
            throw new IllegalArgumentException("record does not match rrset");
        }
    }

    public List<Record> rrs(boolean z) {
        if (!z || this.rrs.size() <= 1) {
            return Collections.unmodifiableList(this.rrs);
        }
        ArrayList arrayList = new ArrayList(this.rrs.size());
        if (this.position == Short.MAX_VALUE) {
            this.position = (short) 0;
        }
        short s = this.position;
        this.position = (short) (s + 1);
        int size = s % this.rrs.size();
        ArrayList<Record> arrayList2 = this.rrs;
        arrayList.addAll(arrayList2.subList(size, arrayList2.size()));
        arrayList.addAll(this.rrs.subList(0, size));
        return arrayList;
    }

    public List<Record> rrs() {
        return rrs(true);
    }

    public Name getName() {
        return first().getName();
    }

    public int getType() {
        return first().getRRsetType();
    }

    public int getDClass() {
        return first().getDClass();
    }

    public long getTTL() {
        return first().getTTL();
    }

    public Record first() {
        if (!this.rrs.isEmpty()) {
            return this.rrs.get(0);
        }
        if (!this.sigs.isEmpty()) {
            return this.sigs.get(0);
        }
        throw new IllegalStateException("rrset is empty");
    }

    private void appendRrList(Iterator<? extends Record> it, StringBuilder sb) {
        while (it.hasNext()) {
            Record next = it.next();
            sb.append("[");
            sb.append(next.rdataToString());
            sb.append("]");
            if (it.hasNext()) {
                sb.append(" ");
            }
        }
    }

    public String toString() {
        if (this.rrs.isEmpty() && this.sigs.isEmpty()) {
            return "{empty}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append(getName());
        sb.append(" ");
        sb.append(getTTL());
        sb.append(" ");
        sb.append(DClass.string(getDClass()));
        sb.append(" ");
        sb.append(Type.string(getType()));
        sb.append(" ");
        appendRrList(this.rrs.iterator(), sb);
        if (!this.sigs.isEmpty()) {
            sb.append(" sigs: ");
            appendRrList(this.sigs.iterator(), sb);
        }
        sb.append(" }");
        return sb.toString();
    }
}
