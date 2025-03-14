package org.telegram.tgnet;

import org.telegram.messenger.LiteMode;

/* loaded from: classes3.dex */
public class TLRPC$TL_chatAdminRights extends TLObject {
    public static int constructor = 1605510357;
    public boolean add_admins;
    public boolean anonymous;
    public boolean ban_users;
    public boolean change_info;
    public boolean delete_messages;
    public boolean edit_messages;
    public int flags;
    public boolean invite_users;
    public boolean manage_call;
    public boolean manage_topics;
    public boolean other;
    public boolean pin_messages;
    public boolean post_messages;

    public static TLRPC$TL_chatAdminRights TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in TL_chatAdminRights", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$TL_chatAdminRights tLRPC$TL_chatAdminRights = new TLRPC$TL_chatAdminRights();
        tLRPC$TL_chatAdminRights.readParams(abstractSerializedData, z);
        return tLRPC$TL_chatAdminRights;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.change_info = (readInt32 & 1) != 0;
        this.post_messages = (readInt32 & 2) != 0;
        this.edit_messages = (readInt32 & 4) != 0;
        this.delete_messages = (readInt32 & 8) != 0;
        this.ban_users = (readInt32 & 16) != 0;
        this.invite_users = (readInt32 & 32) != 0;
        this.pin_messages = (readInt32 & 128) != 0;
        this.add_admins = (readInt32 & LiteMode.FLAG_CALLS_ANIMATIONS) != 0;
        this.anonymous = (readInt32 & 1024) != 0;
        this.manage_call = (readInt32 & LiteMode.FLAG_AUTOPLAY_GIFS) != 0;
        this.other = (readInt32 & LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM) != 0;
        this.manage_topics = (readInt32 & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0;
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.change_info ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        int i2 = this.post_messages ? i | 2 : i & (-3);
        this.flags = i2;
        int i3 = this.edit_messages ? i2 | 4 : i2 & (-5);
        this.flags = i3;
        int i4 = this.delete_messages ? i3 | 8 : i3 & (-9);
        this.flags = i4;
        int i5 = this.ban_users ? i4 | 16 : i4 & (-17);
        this.flags = i5;
        int i6 = this.invite_users ? i5 | 32 : i5 & (-33);
        this.flags = i6;
        int i7 = this.pin_messages ? i6 | 128 : i6 & (-129);
        this.flags = i7;
        int i8 = this.add_admins ? i7 | LiteMode.FLAG_CALLS_ANIMATIONS : i7 & (-513);
        this.flags = i8;
        int i9 = this.anonymous ? i8 | 1024 : i8 & (-1025);
        this.flags = i9;
        int i10 = this.manage_call ? i9 | LiteMode.FLAG_AUTOPLAY_GIFS : i9 & (-2049);
        this.flags = i10;
        int i11 = this.other ? i10 | LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM : i10 & (-4097);
        this.flags = i11;
        int i12 = this.manage_topics ? i11 | LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM : i11 & (-8193);
        this.flags = i12;
        abstractSerializedData.writeInt32(i12);
    }
}
