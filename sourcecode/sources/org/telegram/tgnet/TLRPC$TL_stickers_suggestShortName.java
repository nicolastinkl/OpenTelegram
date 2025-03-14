package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_stickers_suggestShortName extends TLObject {
    public static int constructor = 1303364867;
    public String title;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_stickers_suggestedShortName.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.title);
    }
}
