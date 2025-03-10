package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_response_link extends TLObject {
    public static int constructor = -989144578;
    public TLRPC$Photo cover;
    public String title;
    public String url;
    public int vpnPort;

    public static TLRPC$JMT_response_link TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_response_link", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_response_link tLRPC$JMT_response_link = new TLRPC$JMT_response_link();
        tLRPC$JMT_response_link.readParams(abstractSerializedData, z);
        return tLRPC$JMT_response_link;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.cover = TLRPC$Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.title = abstractSerializedData.readString(z);
        this.url = abstractSerializedData.readString(z);
        this.vpnPort = abstractSerializedData.readInt32(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
