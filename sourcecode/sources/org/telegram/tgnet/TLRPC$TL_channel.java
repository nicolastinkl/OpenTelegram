package org.telegram.tgnet;

import org.telegram.messenger.LiteMode;

/* loaded from: classes3.dex */
public class TLRPC$TL_channel extends TLRPC$Chat {
    public static int constructor = -2094689180;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        readParams(abstractSerializedData, z, true);
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z, boolean z2) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.creator = (readInt32 & 1) != 0;
        this.left = (readInt32 & 4) != 0;
        this.broadcast = (readInt32 & 32) != 0;
        this.verified = (readInt32 & 128) != 0;
        this.megagroup = (readInt32 & 256) != 0;
        this.restricted = (readInt32 & LiteMode.FLAG_CALLS_ANIMATIONS) != 0;
        this.signatures = (readInt32 & LiteMode.FLAG_AUTOPLAY_GIFS) != 0;
        this.min = (readInt32 & LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM) != 0;
        this.scam = (524288 & readInt32) != 0;
        this.has_link = (1048576 & readInt32) != 0;
        this.has_geo = (2097152 & readInt32) != 0;
        this.slowmode_enabled = (4194304 & readInt32) != 0;
        this.call_active = (8388608 & readInt32) != 0;
        this.call_not_empty = (16777216 & readInt32) != 0;
        this.fake = (33554432 & readInt32) != 0;
        this.gigagroup = (67108864 & readInt32) != 0;
        this.noforwards = (134217728 & readInt32) != 0;
        this.join_to_send = (268435456 & readInt32) != 0;
        this.join_request = (536870912 & readInt32) != 0;
        this.forum = (readInt32 & 1073741824) != 0;
        this.flags2 = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0) {
            this.access_hash = abstractSerializedData.readInt64(z);
        }
        this.title = abstractSerializedData.readString(z);
        if ((this.flags & 64) != 0) {
            this.username = abstractSerializedData.readString(z);
        }
        this.photo = TLRPC$ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z, z2);
        this.date = abstractSerializedData.readInt32(z);
        if ((this.flags & LiteMode.FLAG_CALLS_ANIMATIONS) != 0) {
            int readInt322 = abstractSerializedData.readInt32(z);
            if (readInt322 != 481674261) {
                if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt322)));
                }
                return;
            }
            int readInt323 = abstractSerializedData.readInt32(z);
            for (int i = 0; i < readInt323; i++) {
                TLRPC$TL_restrictionReason TLdeserialize = TLRPC$TL_restrictionReason.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize == null) {
                    return;
                }
                this.restriction_reason.add(TLdeserialize);
            }
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM) != 0) {
            this.admin_rights = TLRPC$TL_chatAdminRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & LiteMode.FLAG_CHAT_SCALE) != 0) {
            this.banned_rights = TLRPC$TL_chatBannedRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 262144) != 0) {
            this.default_banned_rights = TLRPC$TL_chatBannedRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 131072) != 0) {
            this.participants_count = abstractSerializedData.readInt32(z);
        }
        if ((this.flags2 & 1) != 0) {
            int readInt324 = abstractSerializedData.readInt32(z);
            if (readInt324 != 481674261) {
                if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt324)));
                }
                return;
            }
            int readInt325 = abstractSerializedData.readInt32(z);
            for (int i2 = 0; i2 < readInt325; i2++) {
                TLRPC$TL_username TLdeserialize2 = TLRPC$TL_username.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize2 == null) {
                    return;
                }
                this.usernames.add(TLdeserialize2);
            }
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.creator ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        int i2 = this.left ? i | 4 : i & (-5);
        this.flags = i2;
        int i3 = this.broadcast ? i2 | 32 : i2 & (-33);
        this.flags = i3;
        int i4 = this.verified ? i3 | 128 : i3 & (-129);
        this.flags = i4;
        int i5 = this.megagroup ? i4 | 256 : i4 & (-257);
        this.flags = i5;
        int i6 = this.restricted ? i5 | LiteMode.FLAG_CALLS_ANIMATIONS : i5 & (-513);
        this.flags = i6;
        int i7 = this.signatures ? i6 | LiteMode.FLAG_AUTOPLAY_GIFS : i6 & (-2049);
        this.flags = i7;
        int i8 = this.min ? i7 | LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM : i7 & (-4097);
        this.flags = i8;
        int i9 = this.scam ? i8 | 524288 : i8 & (-524289);
        this.flags = i9;
        int i10 = this.has_link ? i9 | 1048576 : i9 & (-1048577);
        this.flags = i10;
        int i11 = this.has_geo ? i10 | 2097152 : i10 & (-2097153);
        this.flags = i11;
        int i12 = this.slowmode_enabled ? i11 | 4194304 : i11 & (-4194305);
        this.flags = i12;
        int i13 = this.call_active ? i12 | 8388608 : i12 & (-8388609);
        this.flags = i13;
        int i14 = this.call_not_empty ? i13 | ConnectionsManager.FileTypePhoto : i13 & (-16777217);
        this.flags = i14;
        int i15 = this.fake ? i14 | ConnectionsManager.FileTypeVideo : i14 & (-33554433);
        this.flags = i15;
        int i16 = this.gigagroup ? i15 | ConnectionsManager.FileTypeFile : i15 & (-67108865);
        this.flags = i16;
        int i17 = this.noforwards ? i16 | 134217728 : i16 & (-134217729);
        this.flags = i17;
        int i18 = this.join_to_send ? i17 | 268435456 : i17 & (-268435457);
        this.flags = i18;
        int i19 = this.join_request ? i18 | 536870912 : i18 & (-536870913);
        this.flags = i19;
        int i20 = this.forum ? i19 | 1073741824 : i19 & (-1073741825);
        this.flags = i20;
        abstractSerializedData.writeInt32(i20);
        abstractSerializedData.writeInt32(this.flags2);
        abstractSerializedData.writeInt64(this.id);
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0) {
            abstractSerializedData.writeInt64(this.access_hash);
        }
        abstractSerializedData.writeString(this.title);
        if ((this.flags & 64) != 0) {
            abstractSerializedData.writeString(this.username);
        }
        this.photo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.date);
        if ((this.flags & LiteMode.FLAG_CALLS_ANIMATIONS) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.restriction_reason.size();
            abstractSerializedData.writeInt32(size);
            for (int i21 = 0; i21 < size; i21++) {
                this.restriction_reason.get(i21).serializeToStream(abstractSerializedData);
            }
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM) != 0) {
            this.admin_rights.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & LiteMode.FLAG_CHAT_SCALE) != 0) {
            this.banned_rights.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 262144) != 0) {
            this.default_banned_rights.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 131072) != 0) {
            abstractSerializedData.writeInt32(this.participants_count);
        }
        if ((this.flags2 & 1) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size2 = this.usernames.size();
            abstractSerializedData.writeInt32(size2);
            for (int i22 = 0; i22 < size2; i22++) {
                this.usernames.get(i22).serializeToStream(abstractSerializedData);
            }
        }
    }
}
