package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_help_passportConfig extends TLRPC$help_PassportConfig {
    public static int constructor = -1600596305;
    public TLRPC$TL_dataJSON countries_langs;
    public int hash;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.hash = abstractSerializedData.readInt32(z);
        this.countries_langs = TLRPC$TL_dataJSON.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.hash);
        this.countries_langs.serializeToStream(abstractSerializedData);
    }
}
