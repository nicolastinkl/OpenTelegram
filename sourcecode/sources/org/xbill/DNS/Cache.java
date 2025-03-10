package org.xbill.DNS;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: classes4.dex */
public class Cache {

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) Cache.class);
    private CacheMap data;
    private int maxcache;
    private int maxncache;

    private interface Element {
        int compareCredibility(int i);

        boolean expired();

        int getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int limitExpire(long j, long j2) {
        if (j2 >= 0 && j2 < j) {
            j = j2;
        }
        long currentTimeMillis = (System.currentTimeMillis() / 1000) + j;
        if (currentTimeMillis < 0 || currentTimeMillis > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) currentTimeMillis;
    }

    private static class CacheRRset extends RRset implements Element {
        int credibility;
        int expire;

        public CacheRRset(RRset rRset, int i, long j) {
            super(rRset);
            this.credibility = i;
            this.expire = Cache.limitExpire(rRset.getTTL(), j);
        }

        @Override // org.xbill.DNS.Cache.Element
        public final boolean expired() {
            return ((int) (System.currentTimeMillis() / 1000)) >= this.expire;
        }

        @Override // org.xbill.DNS.Cache.Element
        public final int compareCredibility(int i) {
            return this.credibility - i;
        }

        @Override // org.xbill.DNS.RRset
        public String toString() {
            return super.toString() + " cl = " + this.credibility;
        }
    }

    private static class NegativeElement implements Element {
        int credibility;
        int expire;
        Name name;
        int type;

        public NegativeElement(Name name, int i, SOARecord sOARecord, int i2, long j) {
            this.name = name;
            this.type = i;
            long min = sOARecord != null ? Math.min(sOARecord.getMinimum(), sOARecord.getTTL()) : 0L;
            this.credibility = i2;
            this.expire = Cache.limitExpire(min, j);
        }

        @Override // org.xbill.DNS.Cache.Element
        public int getType() {
            return this.type;
        }

        @Override // org.xbill.DNS.Cache.Element
        public final boolean expired() {
            return ((int) (System.currentTimeMillis() / 1000)) >= this.expire;
        }

        @Override // org.xbill.DNS.Cache.Element
        public final int compareCredibility(int i) {
            return this.credibility - i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (this.type == 0) {
                sb.append("NXDOMAIN ");
                sb.append(this.name);
            } else {
                sb.append("NXRRSET ");
                sb.append(this.name);
                sb.append(" ");
                sb.append(Type.string(this.type));
            }
            sb.append(" cl = ");
            sb.append(this.credibility);
            return sb.toString();
        }
    }

    private static class CacheMap extends LinkedHashMap<Name, Object> {
        private int maxsize;

        CacheMap(int i) {
            super(16, 0.75f, true);
            this.maxsize = i;
        }

        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Name, Object> entry) {
            return this.maxsize >= 0 && size() > this.maxsize;
        }
    }

    public Cache(int i) {
        this.maxncache = -1;
        this.maxcache = -1;
        this.data = new CacheMap(50000);
    }

    public Cache() {
        this(1);
    }

    private synchronized Object exactName(Name name) {
        return this.data.get(name);
    }

    private synchronized Element[] allElements(Object obj) {
        if (!(obj instanceof List)) {
            return new Element[]{(Element) obj};
        }
        List list = (List) obj;
        return (Element[]) list.toArray(new Element[list.size()]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x002a, code lost:
    
        if (r0.getType() == r6) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized org.xbill.DNS.Cache.Element oneElement(org.xbill.DNS.Name r4, java.lang.Object r5, int r6, int r7) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 255(0xff, float:3.57E-43)
            if (r6 == r0) goto L49
            boolean r0 = r5 instanceof java.util.List     // Catch: java.lang.Throwable -> L47
            r1 = 0
            if (r0 == 0) goto L23
            java.util.List r5 = (java.util.List) r5     // Catch: java.lang.Throwable -> L47
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Throwable -> L47
        L10:
            boolean r0 = r5.hasNext()     // Catch: java.lang.Throwable -> L47
            if (r0 == 0) goto L2d
            java.lang.Object r0 = r5.next()     // Catch: java.lang.Throwable -> L47
            org.xbill.DNS.Cache$Element r0 = (org.xbill.DNS.Cache.Element) r0     // Catch: java.lang.Throwable -> L47
            int r2 = r0.getType()     // Catch: java.lang.Throwable -> L47
            if (r2 != r6) goto L10
            goto L2e
        L23:
            r0 = r5
            org.xbill.DNS.Cache$Element r0 = (org.xbill.DNS.Cache.Element) r0     // Catch: java.lang.Throwable -> L47
            int r5 = r0.getType()     // Catch: java.lang.Throwable -> L47
            if (r5 != r6) goto L2d
            goto L2e
        L2d:
            r0 = r1
        L2e:
            if (r0 != 0) goto L32
            monitor-exit(r3)
            return r1
        L32:
            boolean r5 = r0.expired()     // Catch: java.lang.Throwable -> L47
            if (r5 == 0) goto L3d
            r3.removeElement(r4, r6)     // Catch: java.lang.Throwable -> L47
            monitor-exit(r3)
            return r1
        L3d:
            int r4 = r0.compareCredibility(r7)     // Catch: java.lang.Throwable -> L47
            if (r4 >= 0) goto L45
            monitor-exit(r3)
            return r1
        L45:
            monitor-exit(r3)
            return r0
        L47:
            r4 = move-exception
            goto L51
        L49:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException     // Catch: java.lang.Throwable -> L47
            java.lang.String r5 = "oneElement(ANY)"
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L47
            throw r4     // Catch: java.lang.Throwable -> L47
        L51:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.Cache.oneElement(org.xbill.DNS.Name, java.lang.Object, int, int):org.xbill.DNS.Cache$Element");
    }

    private synchronized Element findElement(Name name, int i, int i2) {
        Object exactName = exactName(name);
        if (exactName == null) {
            return null;
        }
        return oneElement(name, exactName, i, i2);
    }

    private synchronized void addElement(Name name, Element element) {
        Object obj = this.data.get(name);
        if (obj == null) {
            this.data.put(name, element);
            return;
        }
        int type = element.getType();
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                if (((Element) list.get(i)).getType() == type) {
                    list.set(i, element);
                    return;
                }
            }
            list.add(element);
        } else {
            Element element2 = (Element) obj;
            if (element2.getType() == type) {
                this.data.put(name, element);
            } else {
                LinkedList linkedList = new LinkedList();
                linkedList.add(element2);
                linkedList.add(element);
                this.data.put(name, linkedList);
            }
        }
    }

    private synchronized void removeElement(Name name, int i) {
        Object obj = this.data.get(name);
        if (obj == null) {
            return;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (((Element) list.get(i2)).getType() == i) {
                    list.remove(i2);
                    if (list.size() == 0) {
                        this.data.remove(name);
                    }
                    return;
                }
            }
        } else if (((Element) obj).getType() == i) {
            this.data.remove(name);
        }
    }

    public synchronized void clearCache() {
        this.data.clear();
    }

    public synchronized <T extends Record> void addRRset(RRset rRset, int i) {
        CacheRRset cacheRRset;
        long ttl = rRset.getTTL();
        Name name = rRset.getName();
        int type = rRset.getType();
        Element findElement = findElement(name, type, 0);
        if (ttl == 0) {
            if (findElement != null && findElement.compareCredibility(i) <= 0) {
                removeElement(name, type);
            }
        } else {
            if (findElement != null && findElement.compareCredibility(i) <= 0) {
                findElement = null;
            }
            if (findElement == null) {
                if (rRset instanceof CacheRRset) {
                    cacheRRset = (CacheRRset) rRset;
                } else {
                    cacheRRset = new CacheRRset(rRset, i, this.maxcache);
                }
                addElement(name, cacheRRset);
            }
        }
    }

    public synchronized void addNegative(Name name, int i, SOARecord sOARecord, int i2) {
        long min = sOARecord != null ? Math.min(sOARecord.getMinimum(), sOARecord.getTTL()) : 0L;
        Element findElement = findElement(name, i, 0);
        if (min == 0) {
            if (findElement != null && findElement.compareCredibility(i2) <= 0) {
                removeElement(name, i);
            }
        } else {
            if (findElement != null && findElement.compareCredibility(i2) <= 0) {
                findElement = null;
            }
            if (findElement == null) {
                addElement(name, new NegativeElement(name, i, sOARecord, i2, this.maxncache));
            }
        }
    }

    protected synchronized SetResponse lookup(Name name, int i, int i2) {
        Name name2;
        int labels = name.labels();
        int i3 = labels;
        while (i3 >= 1) {
            boolean z = i3 == 1;
            boolean z2 = i3 == labels;
            if (z) {
                name2 = Name.root;
            } else {
                name2 = z2 ? name : new Name(name, labels - i3);
            }
            Object obj = this.data.get(name2);
            if (obj != null) {
                if (z2 && i == 255) {
                    SetResponse setResponse = new SetResponse(6);
                    int i4 = 0;
                    for (Element element : allElements(obj)) {
                        if (element.expired()) {
                            removeElement(name2, element.getType());
                        } else if ((element instanceof CacheRRset) && element.compareCredibility(i2) >= 0) {
                            setResponse.addRRset((CacheRRset) element);
                            i4++;
                        }
                    }
                    if (i4 > 0) {
                        return setResponse;
                    }
                } else if (z2) {
                    Element oneElement = oneElement(name2, obj, i, i2);
                    if (oneElement instanceof CacheRRset) {
                        SetResponse setResponse2 = new SetResponse(6);
                        setResponse2.addRRset((CacheRRset) oneElement);
                        return setResponse2;
                    }
                    if (oneElement != null) {
                        return new SetResponse(2);
                    }
                    Element oneElement2 = oneElement(name2, obj, 5, i2);
                    if (oneElement2 instanceof CacheRRset) {
                        return new SetResponse(4, (CacheRRset) oneElement2);
                    }
                } else {
                    Element oneElement3 = oneElement(name2, obj, 39, i2);
                    if (oneElement3 instanceof CacheRRset) {
                        return new SetResponse(5, (CacheRRset) oneElement3);
                    }
                }
                Element oneElement4 = oneElement(name2, obj, 2, i2);
                if (oneElement4 instanceof CacheRRset) {
                    return new SetResponse(3, (CacheRRset) oneElement4);
                }
                if (z2 && oneElement(name2, obj, 0, i2) != null) {
                    return SetResponse.ofType(1);
                }
            }
            i3--;
        }
        return SetResponse.ofType(0);
    }

    public SetResponse lookupRecords(Name name, int i, int i2) {
        return lookup(name, i, i2);
    }

    private int getCred(int i, boolean z) {
        if (i == 1) {
            return z ? 4 : 3;
        }
        if (i == 2) {
            return z ? 4 : 3;
        }
        if (i == 3) {
            return 1;
        }
        throw new IllegalArgumentException("getCred: invalid section");
    }

    private static void markAdditional(RRset rRset, Set<Name> set) {
        if (rRset.first().getAdditionalName() == null) {
            return;
        }
        Iterator<Record> it = rRset.rrs().iterator();
        while (it.hasNext()) {
            Name additionalName = it.next().getAdditionalName();
            if (additionalName != null) {
                set.add(additionalName);
            }
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public org.xbill.DNS.SetResponse addMessage(org.xbill.DNS.Message r19) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.xbill.DNS.Cache.addMessage(org.xbill.DNS.Message):org.xbill.DNS.SetResponse");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        synchronized (this) {
            Iterator<Object> it = this.data.values().iterator();
            while (it.hasNext()) {
                for (Element element : allElements(it.next())) {
                    sb.append(element);
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
}
