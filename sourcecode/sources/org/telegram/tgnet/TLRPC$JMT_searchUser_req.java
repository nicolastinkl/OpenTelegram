package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$JMT_searchUser_req extends TLObject {
    public static int constructor = 613481570;
    public int limit;

    /* renamed from: q, reason: collision with root package name */
    public String f31q;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Vector tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            TLRPC$JMT_searchUser_res TLdeserialize = TLRPC$JMT_searchUser_res.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                return tLRPC$Vector;
            }
            tLRPC$Vector.objects.add(TLdeserialize);
        }
        return tLRPC$Vector;
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.f31q);
        abstractSerializedData.writeInt32(this.limit);
    }
}
