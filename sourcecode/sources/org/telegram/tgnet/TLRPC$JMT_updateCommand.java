package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_updateCommand extends TLRPC$Update {
    public static int constructor = -347357643;
    public String command;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.command = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.command);
    }
}
