package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$TL_updateDeleteMessages extends TLRPC$Update {
    public static int constructor = -1576161051;
    public ArrayList<Integer> messages = new ArrayList<>();
    public int pts;
    public int pts_count;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        if (readInt32 != 481674261) {
            if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt32)));
            }
            return;
        }
        int readInt322 = abstractSerializedData.readInt32(z);
        for (int i = 0; i < readInt322; i++) {
            this.messages.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
        }
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.messages.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(this.messages.get(i).intValue());
        }
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
    }
}
