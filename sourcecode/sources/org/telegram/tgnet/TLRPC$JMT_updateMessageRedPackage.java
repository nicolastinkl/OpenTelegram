package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TLRPC$JMT_updateMessageRedPackage extends TLRPC$Update {
    public static int constructor = 1474104220;
    public TLRPC$JMT_RedPackage red_package;
    public long red_package_id;
    public ArrayList<TLRPC$JMT_redPackageReceiver> red_package_receiver = new ArrayList<>();

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.red_package_id = abstractSerializedData.readInt64(z);
        this.red_package = TLRPC$JMT_RedPackage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32 = abstractSerializedData.readInt32(z);
        if (readInt32 != 481674261) {
            if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", Integer.valueOf(readInt32)));
            }
            return;
        }
        int readInt322 = abstractSerializedData.readInt32(z);
        for (int i = 0; i < readInt322; i++) {
            TLRPC$JMT_redPackageReceiver TLdeserialize = TLRPC$JMT_redPackageReceiver.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if (TLdeserialize == null) {
                return;
            }
            this.red_package_receiver.add(TLdeserialize);
        }
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt64(this.red_package_id);
        this.red_package.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.red_package_receiver.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            this.red_package_receiver.get(i).serializeToStream(abstractSerializedData);
        }
    }
}
