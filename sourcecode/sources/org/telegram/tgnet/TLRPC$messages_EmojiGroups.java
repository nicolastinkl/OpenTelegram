package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$messages_EmojiGroups extends TLObject {
    public static TLRPC$messages_EmojiGroups TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_EmojiGroups tLRPC$TL_messages_emojiGroups;
        if (i != -2011186869) {
            tLRPC$TL_messages_emojiGroups = i != 1874111879 ? null : new TLRPC$messages_EmojiGroups() { // from class: org.telegram.tgnet.TLRPC$TL_messages_emojiGroupsNotModified
                public static int constructor = 1874111879;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else {
            tLRPC$TL_messages_emojiGroups = new TLRPC$TL_messages_emojiGroups();
        }
        if (tLRPC$TL_messages_emojiGroups == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_EmojiGroups", Integer.valueOf(i)));
        }
        if (tLRPC$TL_messages_emojiGroups != null) {
            tLRPC$TL_messages_emojiGroups.readParams(abstractSerializedData, z);
        }
        return tLRPC$TL_messages_emojiGroups;
    }
}
