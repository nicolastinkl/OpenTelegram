package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_upload_saveBigFilePart extends TLObject {
    public static int constructor = -562337987;
    public NativeByteBuffer bytes;
    public long file_id;
    public int file_part;
    public int file_total_parts;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.file_id);
        abstractSerializedData.writeInt32(this.file_part);
        abstractSerializedData.writeInt32(this.file_total_parts);
        abstractSerializedData.writeByteBuffer(this.bytes);
    }

    @Override // org.telegram.tgnet.TLObject
    public void freeResources() {
        NativeByteBuffer nativeByteBuffer;
        if (this.disableFree || (nativeByteBuffer = this.bytes) == null) {
            return;
        }
        nativeByteBuffer.reuse();
        this.bytes = null;
    }
}
