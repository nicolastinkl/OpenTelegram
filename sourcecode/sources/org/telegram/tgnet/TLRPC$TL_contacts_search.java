package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_contacts_search extends TLObject {
    public static int constructor = 301470424;
    public int limit;

    /* renamed from: q, reason: collision with root package name */
    public String f34q;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_contacts_found.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.f34q);
        abstractSerializedData.writeInt32(this.limit);
    }
}
