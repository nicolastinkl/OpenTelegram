package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_help_appConfig extends TLRPC$help_AppConfig {
    public static int constructor = -585598930;
    public TLRPC$JSONValue config;
    public int hash;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.hash = abstractSerializedData.readInt32(z);
        this.config = TLRPC$JSONValue.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.hash);
        this.config.serializeToStream(abstractSerializedData);
    }
}
