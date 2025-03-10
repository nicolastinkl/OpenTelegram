package com.google.android.gms.internal.clearcut;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public final class zzcw extends zzav<String> implements zzcx, RandomAccess {
    private static final zzcw zzlq;
    private final List<Object> zzls;

    static {
        zzcw zzcwVar = new zzcw();
        zzlq = zzcwVar;
        zzcwVar.zzv();
    }

    public zzcw() {
        this(10);
    }

    public zzcw(int i) {
        this((ArrayList<Object>) new ArrayList(i));
    }

    private zzcw(ArrayList<Object> arrayList) {
        this.zzls = arrayList;
    }

    private static String zze(Object obj) {
        return obj instanceof String ? (String) obj : obj instanceof zzbb ? ((zzbb) obj).zzz() : zzci.zzf((byte[]) obj);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ void add(int i, Object obj) {
        zzw();
        this.zzls.add(i, (String) obj);
        ((AbstractList) this).modCount++;
    }

    @Override // com.google.android.gms.internal.clearcut.zzav, java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzw();
        if (collection instanceof zzcx) {
            collection = ((zzcx) collection).zzbt();
        }
        boolean addAll = this.zzls.addAll(i, collection);
        ((AbstractList) this).modCount++;
        return addAll;
    }

    @Override // com.google.android.gms.internal.clearcut.zzav, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    @Override // com.google.android.gms.internal.clearcut.zzav, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final void clear() {
        zzw();
        this.zzls.clear();
        ((AbstractList) this).modCount++;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzls.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzbb) {
            zzbb zzbbVar = (zzbb) obj;
            String zzz = zzbbVar.zzz();
            if (zzbbVar.zzaa()) {
                this.zzls.set(i, zzz);
            }
            return zzz;
        }
        byte[] bArr = (byte[]) obj;
        String zzf = zzci.zzf(bArr);
        if (zzci.zze(bArr)) {
            this.zzls.set(i, zzf);
        }
        return zzf;
    }

    @Override // com.google.android.gms.internal.clearcut.zzcx
    public final Object getRaw(int i) {
        return this.zzls.get(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object remove(int i) {
        zzw();
        Object remove = this.zzls.remove(i);
        ((AbstractList) this).modCount++;
        return zze(remove);
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* synthetic */ Object set(int i, Object obj) {
        zzw();
        return zze(this.zzls.set(i, (String) obj));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zzls.size();
    }

    @Override // com.google.android.gms.internal.clearcut.zzcx
    public final List<?> zzbt() {
        return Collections.unmodifiableList(this.zzls);
    }

    @Override // com.google.android.gms.internal.clearcut.zzcx
    public final zzcx zzbu() {
        return zzu() ? new zzfa(this) : this;
    }

    @Override // com.google.android.gms.internal.clearcut.zzcn
    public final /* synthetic */ zzcn zzi(int i) {
        if (i < size()) {
            throw new IllegalArgumentException();
        }
        ArrayList arrayList = new ArrayList(i);
        arrayList.addAll(this.zzls);
        return new zzcw((ArrayList<Object>) arrayList);
    }
}
