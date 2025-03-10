package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_account_uploadRingtone extends TLObject {
    public static int constructor = -2095414366;
    public TLRPC$InputFile file;
    public String file_name;
    public String mime_type;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Document.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.file.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.file_name);
        abstractSerializedData.writeString(this.mime_type);
    }
}
