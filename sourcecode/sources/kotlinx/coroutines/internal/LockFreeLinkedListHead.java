package kotlinx.coroutines.internal;

/* compiled from: LockFreeLinkedList.kt */
/* loaded from: classes3.dex */
public class LockFreeLinkedListHead extends LockFreeLinkedListNode {
    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public boolean isRemoved() {
        return false;
    }
}
