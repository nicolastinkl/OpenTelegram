package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_pageBlockEmbed extends TLRPC$PageBlock {
    public static int constructor = -1468953147;
    public boolean allow_scrolling;
    public TLRPC$TL_pageCaption caption;
    public int flags;
    public boolean full_width;
    public int h;
    public String html;
    public long poster_photo_id;
    public String url;
    public int w;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.full_width = (readInt32 & 1) != 0;
        this.allow_scrolling = (readInt32 & 8) != 0;
        if ((readInt32 & 2) != 0) {
            this.url = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.html = abstractSerializedData.readString(z);
        }
        if ((this.flags & 16) != 0) {
            this.poster_photo_id = abstractSerializedData.readInt64(z);
        }
        if ((this.flags & 32) != 0) {
            this.w = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 32) != 0) {
            this.h = abstractSerializedData.readInt32(z);
        }
        this.caption = TLRPC$TL_pageCaption.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.full_width ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        int i2 = this.allow_scrolling ? i | 8 : i & (-9);
        this.flags = i2;
        abstractSerializedData.writeInt32(i2);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.url);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeString(this.html);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeInt64(this.poster_photo_id);
        }
        if ((this.flags & 32) != 0) {
            abstractSerializedData.writeInt32(this.w);
        }
        if ((this.flags & 32) != 0) {
            abstractSerializedData.writeInt32(this.h);
        }
        this.caption.serializeToStream(abstractSerializedData);
    }
}
