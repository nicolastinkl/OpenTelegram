package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$TL_jsonArray extends TLRPC$JSONValue {
    public static int constructor = -146520221;
    public ArrayList<TLRPC$JSONValue> value = new ArrayList<>();

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
            TLRPC$JSONValue TLdeserialize = TLRPC$JSONValue.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                return;
            }
            this.value.add(TLdeserialize);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.value.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            this.value.get(i).serializeToStream(abstractSerializedData);
        }
    }
}
