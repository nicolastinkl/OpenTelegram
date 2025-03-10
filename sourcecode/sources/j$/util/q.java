package j$.util;

import java.util.Collection;
import java.util.SortedSet;

/* loaded from: classes2.dex */
class q extends F {
    final /* synthetic */ SortedSet f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    q(SortedSet sortedSet, Collection collection, int i) {
        super(collection, i);
        this.f = sortedSet;
    }

    @Override // j$.util.F, j$.util.r
    public java.util.Comparator getComparator() {
        return this.f.comparator();
    }
}
