package okio;

import java.util.concurrent.atomic.AtomicReference;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.LiteMode;

/* compiled from: SegmentPool.kt */
/* loaded from: classes3.dex */
public final class SegmentPool {
    private static final int HASH_BUCKET_COUNT;
    private static final AtomicReference<Segment>[] hashBuckets;
    public static final SegmentPool INSTANCE = new SegmentPool();
    private static final int MAX_SIZE = CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT;
    private static final Segment LOCK = new Segment(new byte[0], 0, 0, false, false);

    private SegmentPool() {
    }

    static {
        int highestOneBit = Integer.highestOneBit((Runtime.getRuntime().availableProcessors() * 2) - 1);
        HASH_BUCKET_COUNT = highestOneBit;
        AtomicReference<Segment>[] atomicReferenceArr = new AtomicReference[highestOneBit];
        for (int i = 0; i < highestOneBit; i++) {
            atomicReferenceArr[i] = new AtomicReference<>();
        }
        hashBuckets = atomicReferenceArr;
    }

    public static final Segment take() {
        AtomicReference<Segment> firstRef = INSTANCE.firstRef();
        Segment segment = LOCK;
        Segment andSet = firstRef.getAndSet(segment);
        if (andSet == segment) {
            return new Segment();
        }
        if (andSet == null) {
            firstRef.set(null);
            return new Segment();
        }
        firstRef.set(andSet.next);
        andSet.next = null;
        andSet.limit = 0;
        return andSet;
    }

    public static final void recycle(Segment segment) {
        AtomicReference<Segment> firstRef;
        Segment segment2;
        Intrinsics.checkNotNullParameter(segment, "segment");
        if (!(segment.next == null && segment.prev == null)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (segment.shared || (segment2 = (firstRef = INSTANCE.firstRef()).get()) == LOCK) {
            return;
        }
        int i = segment2 != null ? segment2.limit : 0;
        if (i >= MAX_SIZE) {
            return;
        }
        segment.next = segment2;
        segment.pos = 0;
        segment.limit = i + LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM;
        if (firstRef.compareAndSet(segment2, segment)) {
            return;
        }
        segment.next = null;
    }

    private final AtomicReference<Segment> firstRef() {
        return hashBuckets[(int) (Thread.currentThread().getId() & (HASH_BUCKET_COUNT - 1))];
    }
}
