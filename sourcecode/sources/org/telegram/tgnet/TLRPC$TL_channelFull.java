package org.telegram.tgnet;

import org.telegram.messenger.CharacterCompat;
import org.telegram.messenger.LiteMode;

/* loaded from: classes3.dex */
public class TLRPC$TL_channelFull extends TLRPC$ChatFull {
    public static int constructor = -231385849;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int readInt32 = abstractSerializedData.readInt32(z);
        this.flags = readInt32;
        this.can_view_participants = (readInt32 & 8) != 0;
        this.can_set_username = (readInt32 & 64) != 0;
        this.can_set_stickers = (readInt32 & 128) != 0;
        this.hidden_prehistory = (readInt32 & 1024) != 0;
        this.can_set_location = (65536 & readInt32) != 0;
        this.has_scheduled = (524288 & readInt32) != 0;
        this.can_view_stats = (1048576 & readInt32) != 0;
        this.blocked = (readInt32 & 4194304) != 0;
        int readInt322 = abstractSerializedData.readInt32(z);
        this.flags2 = readInt322;
        this.can_delete_channel = (readInt322 & 1) != 0;
        this.antispam = (readInt322 & 2) != 0;
        this.participants_hidden = (readInt322 & 4) != 0;
        this.translations_disabled = (readInt322 & 8) != 0;
        this.id = abstractSerializedData.readInt64(z);
        this.about = abstractSerializedData.readString(z);
        if ((this.flags & 1) != 0) {
            this.participants_count = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 2) != 0) {
            this.admins_count = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 4) != 0) {
            this.kicked_count = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 4) != 0) {
            this.banned_count = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0) {
            this.online_count = abstractSerializedData.readInt32(z);
        }
        this.read_inbox_max_id = abstractSerializedData.readInt32(z);
        this.read_outbox_max_id = abstractSerializedData.readInt32(z);
        this.unread_count = abstractSerializedData.readInt32(z);
        this.chat_photo = TLRPC$Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 8388608) != 0) {
            this.exported_invite = TLRPC$ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        int readInt323 = abstractSerializedData.readInt32(z);
        if (readInt323 != 481674261) {
            if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt323)));
            }
            return;
        }
        int readInt324 = abstractSerializedData.readInt32(z);
        for (int i = 0; i < readInt324; i++) {
            TLRPC$BotInfo TLdeserialize = TLRPC$BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                return;
            }
            this.bot_info.add(TLdeserialize);
        }
        if ((this.flags & 16) != 0) {
            this.migrated_from_chat_id = abstractSerializedData.readInt64(z);
        }
        if ((this.flags & 16) != 0) {
            this.migrated_from_max_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 32) != 0) {
            this.pinned_msg_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 256) != 0) {
            this.stickerset = TLRPC$StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & LiteMode.FLAG_CALLS_ANIMATIONS) != 0) {
            this.available_min_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & LiteMode.FLAG_AUTOPLAY_GIFS) != 0) {
            this.folder_id = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM) != 0) {
            this.linked_chat_id = abstractSerializedData.readInt64(z);
        }
        if ((this.flags & LiteMode.FLAG_CHAT_SCALE) != 0) {
            this.location = TLRPC$ChannelLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 131072) != 0) {
            this.slowmode_seconds = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 262144) != 0) {
            this.slowmode_next_send_date = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM) != 0) {
            this.stats_dc = abstractSerializedData.readInt32(z);
        }
        this.pts = abstractSerializedData.readInt32(z);
        if ((this.flags & 2097152) != 0) {
            this.call = TLRPC$TL_inputGroupCall.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & ConnectionsManager.FileTypePhoto) != 0) {
            this.ttl_period = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & ConnectionsManager.FileTypeVideo) != 0) {
            int readInt325 = abstractSerializedData.readInt32(z);
            if (readInt325 != 481674261) {
                if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt325)));
                }
                return;
            } else {
                int readInt326 = abstractSerializedData.readInt32(z);
                for (int i2 = 0; i2 < readInt326; i2++) {
                    this.pending_suggestions.add(abstractSerializedData.readString(z));
                }
            }
        }
        if ((this.flags & ConnectionsManager.FileTypeFile) != 0) {
            this.groupcall_default_join_as = TLRPC$Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 134217728) != 0) {
            this.theme_emoticon = abstractSerializedData.readString(z);
        }
        if ((this.flags & 268435456) != 0) {
            this.requests_pending = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 268435456) != 0) {
            int readInt327 = abstractSerializedData.readInt32(z);
            if (readInt327 != 481674261) {
                if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt327)));
                }
                return;
            } else {
                int readInt328 = abstractSerializedData.readInt32(z);
                for (int i3 = 0; i3 < readInt328; i3++) {
                    this.recent_requesters.add(Long.valueOf(abstractSerializedData.readInt64(z)));
                }
            }
        }
        if ((this.flags & 536870912) != 0) {
            this.default_send_as = TLRPC$Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 1073741824) != 0) {
            this.available_reactions = TLRPC$ChatReactions.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.can_view_participants ? this.flags | 8 : this.flags & (-9);
        this.flags = i;
        int i2 = this.can_set_username ? i | 64 : i & (-65);
        this.flags = i2;
        int i3 = this.can_set_stickers ? i2 | 128 : i2 & (-129);
        this.flags = i3;
        int i4 = this.hidden_prehistory ? i3 | 1024 : i3 & (-1025);
        this.flags = i4;
        int i5 = this.can_set_location ? i4 | CharacterCompat.MIN_SUPPLEMENTARY_CODE_POINT : i4 & (-65537);
        this.flags = i5;
        int i6 = this.has_scheduled ? i5 | 524288 : i5 & (-524289);
        this.flags = i6;
        int i7 = this.can_view_stats ? i6 | 1048576 : i6 & (-1048577);
        this.flags = i7;
        int i8 = this.blocked ? i7 | 4194304 : i7 & (-4194305);
        this.flags = i8;
        abstractSerializedData.writeInt32(i8);
        int i9 = this.can_delete_channel ? this.flags2 | 1 : this.flags2 & (-2);
        this.flags2 = i9;
        int i10 = this.antispam ? i9 | 2 : i9 & (-3);
        this.flags2 = i10;
        int i11 = this.participants_hidden ? i10 | 4 : i10 & (-5);
        this.flags2 = i11;
        int i12 = this.translations_disabled ? i11 | 8 : i11 & (-9);
        this.flags2 = i12;
        abstractSerializedData.writeInt32(i12);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeString(this.about);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.participants_count);
        }
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeInt32(this.admins_count);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.kicked_count);
        }
        if ((this.flags & 4) != 0) {
            abstractSerializedData.writeInt32(this.banned_count);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM) != 0) {
            abstractSerializedData.writeInt32(this.online_count);
        }
        abstractSerializedData.writeInt32(this.read_inbox_max_id);
        abstractSerializedData.writeInt32(this.read_outbox_max_id);
        abstractSerializedData.writeInt32(this.unread_count);
        this.chat_photo.serializeToStream(abstractSerializedData);
        this.notify_settings.serializeToStream(abstractSerializedData);
        if ((this.flags & 8388608) != 0) {
            this.exported_invite.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.bot_info.size();
        abstractSerializedData.writeInt32(size);
        for (int i13 = 0; i13 < size; i13++) {
            this.bot_info.get(i13).serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeInt64(this.migrated_from_chat_id);
        }
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeInt32(this.migrated_from_max_id);
        }
        if ((this.flags & 32) != 0) {
            abstractSerializedData.writeInt32(this.pinned_msg_id);
        }
        if ((this.flags & 256) != 0) {
            this.stickerset.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & LiteMode.FLAG_CALLS_ANIMATIONS) != 0) {
            abstractSerializedData.writeInt32(this.available_min_id);
        }
        if ((this.flags & LiteMode.FLAG_AUTOPLAY_GIFS) != 0) {
            abstractSerializedData.writeInt32(this.folder_id);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_KEYBOARD_NOT_PREMIUM) != 0) {
            abstractSerializedData.writeInt64(this.linked_chat_id);
        }
        if ((this.flags & LiteMode.FLAG_CHAT_SCALE) != 0) {
            this.location.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 131072) != 0) {
            abstractSerializedData.writeInt32(this.slowmode_seconds);
        }
        if ((this.flags & 262144) != 0) {
            abstractSerializedData.writeInt32(this.slowmode_next_send_date);
        }
        if ((this.flags & LiteMode.FLAG_ANIMATED_EMOJI_CHAT_NOT_PREMIUM) != 0) {
            abstractSerializedData.writeInt32(this.stats_dc);
        }
        abstractSerializedData.writeInt32(this.pts);
        if ((this.flags & 2097152) != 0) {
            this.call.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & ConnectionsManager.FileTypePhoto) != 0) {
            abstractSerializedData.writeInt32(this.ttl_period);
        }
        if ((this.flags & ConnectionsManager.FileTypeVideo) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size2 = this.pending_suggestions.size();
            abstractSerializedData.writeInt32(size2);
            for (int i14 = 0; i14 < size2; i14++) {
                abstractSerializedData.writeString(this.pending_suggestions.get(i14));
            }
        }
        if ((this.flags & ConnectionsManager.FileTypeFile) != 0) {
            this.groupcall_default_join_as.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 134217728) != 0) {
            abstractSerializedData.writeString(this.theme_emoticon);
        }
        if ((this.flags & 268435456) != 0) {
            abstractSerializedData.writeInt32(this.requests_pending);
        }
        if ((this.flags & 268435456) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size3 = this.recent_requesters.size();
            abstractSerializedData.writeInt32(size3);
            for (int i15 = 0; i15 < size3; i15++) {
                abstractSerializedData.writeInt64(this.recent_requesters.get(i15).longValue());
            }
        }
        if ((this.flags & 536870912) != 0) {
            this.default_send_as.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 1073741824) != 0) {
            this.available_reactions.serializeToStream(abstractSerializedData);
        }
    }
}
