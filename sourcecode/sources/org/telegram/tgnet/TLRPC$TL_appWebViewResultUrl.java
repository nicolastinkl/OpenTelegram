package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_appWebViewResultUrl extends TLObject {
    public static int constructor = 1008422669;
    public String url;

    public static TLRPC$TL_appWebViewResultUrl TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in TL_appWebViewResultUrl", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$TL_appWebViewResultUrl tLRPC$TL_appWebViewResultUrl = new TLRPC$TL_appWebViewResultUrl();
        tLRPC$TL_appWebViewResultUrl.readParams(abstractSerializedData, z);
        return tLRPC$TL_appWebViewResultUrl;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.url = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.url);
    }
}
