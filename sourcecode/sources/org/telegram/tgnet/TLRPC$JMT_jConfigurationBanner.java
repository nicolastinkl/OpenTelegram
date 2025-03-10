package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_jConfigurationBanner extends TLObject {
    public static int constructor = -1965090641;
    public String ext;
    public TLRPC$Photo photo;
    public String url;

    public static TLRPC$JMT_jConfigurationBanner TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_jConfigurationBanner", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_jConfigurationBanner tLRPC$JMT_jConfigurationBanner = new TLRPC$JMT_jConfigurationBanner();
        tLRPC$JMT_jConfigurationBanner.readParams(abstractSerializedData, z);
        return tLRPC$JMT_jConfigurationBanner;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.photo = TLRPC$Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.url = abstractSerializedData.readString(z);
        this.ext = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeString(this.url);
        abstractSerializedData.writeString(this.ext);
    }
}
