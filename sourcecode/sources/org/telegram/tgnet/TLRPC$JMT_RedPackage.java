package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$JMT_RedPackage extends TLObject {
    public static int constructor = -591094673;
    public int count;
    public TLRPC$Photo cover;
    public TLRPC$User designated_user;
    public int expired_time;
    public int flags;
    public long id;
    public ArrayList<Long> receivers = new ArrayList<>();
    public int red_package_type;
    public long sender_id;
    public int single_amount;
    public int status;
    public String title;
    public int total_amount;

    public static TLRPC$JMT_RedPackage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor != i) {
            if (z) {
                throw new RuntimeException(String.format("can't parse magic %x in JMT_RedPackage", Integer.valueOf(i)));
            }
            return null;
        }
        TLRPC$JMT_RedPackage tLRPC$JMT_RedPackage = new TLRPC$JMT_RedPackage();
        tLRPC$JMT_RedPackage.readParams(abstractSerializedData, z);
        return tLRPC$JMT_RedPackage;
    }

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.sender_id = abstractSerializedData.readInt64(z);
        this.title = abstractSerializedData.readString(z);
        this.red_package_type = abstractSerializedData.readInt32(z);
        this.count = abstractSerializedData.readInt32(z);
        this.total_amount = abstractSerializedData.readInt32(z);
        this.single_amount = abstractSerializedData.readInt32(z);
        this.cover = TLRPC$Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.status = abstractSerializedData.readInt32(z);
        this.expired_time = abstractSerializedData.readInt32(z);
        if ((this.flags & 1) != 0) {
            this.designated_user = TLRPC$User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        int readInt32 = abstractSerializedData.readInt32(z);
        if (readInt32 != 481674261) {
            if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt32)));
            }
        } else {
            int readInt322 = abstractSerializedData.readInt32(z);
            for (int i = 0; i < readInt322; i++) {
                this.receivers.add(Long.valueOf(abstractSerializedData.readInt64(z)));
            }
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.designated_user != null ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.sender_id);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeInt32(this.red_package_type);
        abstractSerializedData.writeInt32(this.count);
        abstractSerializedData.writeInt32(this.total_amount);
        abstractSerializedData.writeInt32(this.single_amount);
        this.cover.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.status);
        abstractSerializedData.writeInt32(this.expired_time);
        TLRPC$User tLRPC$User = this.designated_user;
        if (tLRPC$User != null) {
            tLRPC$User.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.receivers.size();
        abstractSerializedData.writeInt32(size);
        for (int i2 = 0; i2 < size; i2++) {
            abstractSerializedData.writeInt64(this.receivers.get(i2).longValue());
        }
    }
}
