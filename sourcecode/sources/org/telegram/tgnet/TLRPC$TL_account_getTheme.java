package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_getTheme extends TLObject {
    public static int constructor = -1919060949;
    public long document_id;
    public String format;
    public TLRPC$InputTheme theme;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Theme.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.format);
        this.theme.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt64(this.document_id);
    }
}
