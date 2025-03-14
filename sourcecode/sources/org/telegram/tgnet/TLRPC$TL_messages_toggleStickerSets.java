package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_toggleStickerSets extends TLObject {
    public static int constructor = -1257951254;
    public boolean archive;
    public int flags;
    public ArrayList<TLRPC$InputStickerSet> stickersets = new ArrayList<>();
    public boolean unarchive;
    public boolean uninstall;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Bool.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.uninstall ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        int i2 = this.archive ? i | 2 : i & (-3);
        this.flags = i2;
        int i3 = this.unarchive ? i2 | 4 : i2 & (-5);
        this.flags = i3;
        abstractSerializedData.writeInt32(i3);
        abstractSerializedData.writeInt32(481674261);
        int size = this.stickersets.size();
        abstractSerializedData.writeInt32(size);
        for (int i4 = 0; i4 < size; i4++) {
            this.stickersets.get(i4).serializeToStream(abstractSerializedData);
        }
    }
}
