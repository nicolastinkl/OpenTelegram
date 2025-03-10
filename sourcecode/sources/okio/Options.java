package okio;

import java.util.List;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: Options.kt */
/* loaded from: classes3.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    public static final Options of(ByteString... byteStringArr) {
        return Companion.of(byteStringArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Options) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Options) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Options) byteString);
    }

    public final ByteString[] getByteStrings$okio() {
        return this.byteStrings;
    }

    public final int[] getTrie$okio() {
        return this.trie;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    /* compiled from: Options.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:46:0x00ee, code lost:
        
            continue;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final okio.Options of(okio.ByteString... r17) {
            /*
                Method dump skipped, instructions count: 326
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.of(okio.ByteString[]):okio.Options");
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            companion.buildTrieRecursive((i4 & 1) != 0 ? 0L : j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long j, Buffer buffer, int i, List<? extends ByteString> list, int i2, int i3, List<Integer> list2) {
            int i4;
            int i5;
            int i6;
            int i7;
            Buffer buffer2;
            int i8 = i;
            if (!(i2 < i3)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            for (int i9 = i2; i9 < i3; i9++) {
                if (!(list.get(i9).size() >= i8)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
            ByteString byteString = list.get(i2);
            ByteString byteString2 = list.get(i3 - 1);
            if (i8 == byteString.size()) {
                int intValue = list2.get(i2).intValue();
                int i10 = i2 + 1;
                ByteString byteString3 = list.get(i10);
                i4 = i10;
                i5 = intValue;
                byteString = byteString3;
            } else {
                i4 = i2;
                i5 = -1;
            }
            if (byteString.getByte(i8) != byteString2.getByte(i8)) {
                int i11 = 1;
                for (int i12 = i4 + 1; i12 < i3; i12++) {
                    if (list.get(i12 - 1).getByte(i8) != list.get(i12).getByte(i8)) {
                        i11++;
                    }
                }
                long intCount = j + getIntCount(buffer) + 2 + (i11 * 2);
                buffer.writeInt(i11);
                buffer.writeInt(i5);
                for (int i13 = i4; i13 < i3; i13++) {
                    byte b = list.get(i13).getByte(i8);
                    if (i13 == i4 || b != list.get(i13 - 1).getByte(i8)) {
                        buffer.writeInt(b & 255);
                    }
                }
                Buffer buffer3 = new Buffer();
                while (i4 < i3) {
                    byte b2 = list.get(i4).getByte(i8);
                    int i14 = i4 + 1;
                    int i15 = i14;
                    while (true) {
                        if (i15 >= i3) {
                            i6 = i3;
                            break;
                        } else {
                            if (b2 != list.get(i15).getByte(i8)) {
                                i6 = i15;
                                break;
                            }
                            i15++;
                        }
                    }
                    if (i14 == i6 && i8 + 1 == list.get(i4).size()) {
                        buffer.writeInt(list2.get(i4).intValue());
                        i7 = i6;
                        buffer2 = buffer3;
                    } else {
                        buffer.writeInt(((int) (intCount + getIntCount(buffer3))) * (-1));
                        i7 = i6;
                        buffer2 = buffer3;
                        buildTrieRecursive(intCount, buffer3, i8 + 1, list, i4, i6, list2);
                    }
                    buffer3 = buffer2;
                    i4 = i7;
                }
                buffer.writeAll(buffer3);
                return;
            }
            int min = Math.min(byteString.size(), byteString2.size());
            int i16 = 0;
            for (int i17 = i8; i17 < min && byteString.getByte(i17) == byteString2.getByte(i17); i17++) {
                i16++;
            }
            long intCount2 = j + getIntCount(buffer) + 2 + i16 + 1;
            buffer.writeInt(-i16);
            buffer.writeInt(i5);
            int i18 = i8 + i16;
            while (i8 < i18) {
                buffer.writeInt(byteString.getByte(i8) & 255);
                i8++;
            }
            if (i4 + 1 == i3) {
                if (!(i18 == list.get(i4).size())) {
                    throw new IllegalStateException("Check failed.".toString());
                }
                buffer.writeInt(list2.get(i4).intValue());
            } else {
                Buffer buffer4 = new Buffer();
                buffer.writeInt(((int) (getIntCount(buffer4) + intCount2)) * (-1));
                buildTrieRecursive(intCount2, buffer4, i18, list, i4, i3, list2);
                buffer.writeAll(buffer4);
            }
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / 4;
        }
    }
}
