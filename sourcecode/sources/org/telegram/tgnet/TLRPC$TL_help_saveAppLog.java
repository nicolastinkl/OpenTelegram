package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$TL_help_saveAppLog extends TLObject {
    public static int constructor = 1862465352;
    public ArrayList<TLRPC$TL_inputAppEvent> events = new ArrayList<>();

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.events.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            this.events.get(i).serializeToStream(abstractSerializedData);
        }
    }
}
