package org.telegram.tgnet;

import org.telegram.messenger.CharacterCompat;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelForbidden extends TLRPC$Chat {
    public static int constructor = 399807445;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.broadcast = (readInt32 & 32) != 0;
        this.megagroup = (readInt32 & 256) != 0;
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.title = abstractSerializedData.readString(z);
        if ((this.flags & CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT) != 0) {
            this.until_date = abstractSerializedData.readInt32(z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.broadcast ? this.flags | 32 : this.flags & (-33);
        this.flags = i;
        int i2 = this.megagroup ? i | 256 : i & (-257);
        this.flags = i2;
        abstractSerializedData.writeInt32(i2);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeString(this.title);
        if ((this.flags & CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT) != 0) {
            abstractSerializedData.writeInt32(this.until_date);
        }
    }
}
