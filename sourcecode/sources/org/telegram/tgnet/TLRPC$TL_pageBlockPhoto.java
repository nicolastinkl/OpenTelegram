package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_pageBlockPhoto extends TLRPC$PageBlock {
    public static int constructor = 391759200;
    public TLRPC$TL_pageCaption caption;
    public int flags;
    public long photo_id;
    public String url;
    public long webpage_id;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.photo_id = abstractSerializedData.readInt64(z);
        this.caption = TLRPC$TL_pageCaption.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.url = abstractSerializedData.readString(z);
        }
        if ((this.flags & 1) != 0) {
            this.webpage_id = abstractSerializedData.readInt64(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.photo_id);
        this.caption.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeString(this.url);
        }
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt64(this.webpage_id);
        }
    }
}
