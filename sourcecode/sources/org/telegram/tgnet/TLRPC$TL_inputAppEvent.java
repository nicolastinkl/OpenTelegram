package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputAppEvent extends TLObject {
    public static int constructor = 488313413;
    public TLRPC$JSONValue data;
    public long peer;
    public double time;
    public String type;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.time = abstractSerializedData.readDouble(z);
        this.type = abstractSerializedData.readString(z);
        this.peer = abstractSerializedData.readInt64(z);
        this.data = TLRPC$JSONValue.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeDouble(this.time);
        abstractSerializedData.writeString(this.type);
        abstractSerializedData.writeInt64(this.peer);
        this.data.serializeToStream(abstractSerializedData);
    }
}
