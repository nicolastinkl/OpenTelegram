package com.google.android.gms.internal.mlkit_language_id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.mlkit:language-id@@16.1.1 */
/* loaded from: classes.dex */
final class zzfl extends zzfj {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzfl() {
        super();
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final void zza(Object obj, long j) {
        Object unmodifiableList;
        List list = (List) zzhn.zzf(obj, j);
        if (list instanceof zzfg) {
            unmodifiableList = ((zzfg) list).a_();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzgi) && (list instanceof zzew)) {
                zzew zzewVar = (zzew) list;
                if (zzewVar.zza()) {
                    zzewVar.b_();
                    return;
                }
                return;
            }
            unmodifiableList = Collections.unmodifiableList(list);
        }
        zzhn.zza(obj, j, unmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.mlkit_language_id.zzfj
    final <E> void zza(Object obj, Object obj2, long j) {
        zzfh zzfhVar;
        List zzb = zzb(obj2, j);
        int size = zzb.size();
        List zzb2 = zzb(obj, j);
        if (zzb2.isEmpty()) {
            if (zzb2 instanceof zzfg) {
                zzb2 = new zzfh(size);
            } else if ((zzb2 instanceof zzgi) && (zzb2 instanceof zzew)) {
                zzb2 = ((zzew) zzb2).zzb(size);
            } else {
                zzb2 = new ArrayList(size);
            }
            zzhn.zza(obj, j, zzb2);
        } else {
            if (zza.isAssignableFrom(zzb2.getClass())) {
                ArrayList arrayList = new ArrayList(zzb2.size() + size);
                arrayList.addAll(zzb2);
                zzhn.zza(obj, j, arrayList);
                zzfhVar = arrayList;
            } else if (zzb2 instanceof zzhi) {
                zzfh zzfhVar2 = new zzfh(zzb2.size() + size);
                zzfhVar2.addAll((zzhi) zzb2);
                zzhn.zza(obj, j, zzfhVar2);
                zzfhVar = zzfhVar2;
            } else if ((zzb2 instanceof zzgi) && (zzb2 instanceof zzew)) {
                zzew zzewVar = (zzew) zzb2;
                if (!zzewVar.zza()) {
                    zzb2 = zzewVar.zzb(zzb2.size() + size);
                    zzhn.zza(obj, j, zzb2);
                }
            }
            zzb2 = zzfhVar;
        }
        int size2 = zzb2.size();
        int size3 = zzb.size();
        if (size2 > 0 && size3 > 0) {
            zzb2.addAll(zzb);
        }
        if (size2 > 0) {
            zzb = zzb2;
        }
        zzhn.zza(obj, j, zzb);
    }

    private static <E> List<E> zzb(Object obj, long j) {
        return (List) zzhn.zzf(obj, j);
    }
}
