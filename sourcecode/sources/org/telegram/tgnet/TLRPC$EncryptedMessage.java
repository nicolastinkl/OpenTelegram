package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$EncryptedMessage extends TLObject {
    public byte[] bytes;
    public int chat_id;
    public int date;
    public TLRPC$EncryptedFile file;
    public long random_id;

    public static TLRPC$EncryptedMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$EncryptedMessage tLRPC$EncryptedMessage;
        if (i != -317144808) {
            tLRPC$EncryptedMessage = i != 594758406 ? null : new TLRPC$EncryptedMessage() { // from class: org.telegram.tgnet.TLRPC$TL_encryptedMessageService
                public static int constructor = 594758406;

                @Override // org.telegram.tgnet.TLObject
                public void readParams(AbstractSerializedData abstractSerializedData2, boolean z2) {
                    this.random_id = abstractSerializedData2.readInt64(z2);
                    this.chat_id = abstractSerializedData2.readInt32(z2);
                    this.date = abstractSerializedData2.readInt32(z2);
                    this.bytes = abstractSerializedData2.readByteArray(z2);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                    abstractSerializedData2.writeInt64(this.random_id);
                    abstractSerializedData2.writeInt32(this.chat_id);
                    abstractSerializedData2.writeInt32(this.date);
                    abstractSerializedData2.writeByteArray(this.bytes);
                }
            };
        } else {
            tLRPC$EncryptedMessage = new TLRPC$EncryptedMessage() { // from class: org.telegram.tgnet.TLRPC$TL_encryptedMessage
                public static int constructor = -317144808;

                @Override // org.telegram.tgnet.TLObject
                public void readParams(AbstractSerializedData abstractSerializedData2, boolean z2) {
                    this.random_id = abstractSerializedData2.readInt64(z2);
                    this.chat_id = abstractSerializedData2.readInt32(z2);
                    this.date = abstractSerializedData2.readInt32(z2);
                    this.bytes = abstractSerializedData2.readByteArray(z2);
                    this.file = TLRPC$EncryptedFile.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z2), z2);
                }

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                    abstractSerializedData2.writeInt64(this.random_id);
                    abstractSerializedData2.writeInt32(this.chat_id);
                    abstractSerializedData2.writeInt32(this.date);
                    abstractSerializedData2.writeByteArray(this.bytes);
                    this.file.serializeToStream(abstractSerializedData2);
                }
            };
        }
        if (tLRPC$EncryptedMessage == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in EncryptedMessage", Integer.valueOf(i)));
        }
        if (tLRPC$EncryptedMessage != null) {
            tLRPC$EncryptedMessage.readParams(abstractSerializedData, z);
        }
        return tLRPC$EncryptedMessage;
    }
}
