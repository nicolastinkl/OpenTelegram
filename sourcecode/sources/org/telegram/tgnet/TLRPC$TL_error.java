package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_error extends TLObject {
    public static int constructor = -994444869;
    public int code;
    public String text;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.code = abstractSerializedData.readInt32(z);
        this.text = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.code);
        abstractSerializedData.writeString(this.text);
    }
}
