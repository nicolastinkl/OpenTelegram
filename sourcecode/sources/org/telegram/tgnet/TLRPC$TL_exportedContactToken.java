package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_exportedContactToken extends TLObject {
    public static int constructor = 1103040667;
    public int expires;
    public String url;

    public static TLRPC$TL_exportedContactToken TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in TL_exportedContactToken", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$TL_exportedContactToken tLRPC$TL_exportedContactToken = new TLRPC$TL_exportedContactToken();
        tLRPC$TL_exportedContactToken.readParams(abstractSerializedData, z);
        return tLRPC$TL_exportedContactToken;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
        this.expires = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeInt32(this.expires);
    }
}
