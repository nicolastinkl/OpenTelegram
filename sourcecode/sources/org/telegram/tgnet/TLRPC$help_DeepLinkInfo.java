package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$help_DeepLinkInfo extends TLObject {
    public static TLRPC$help_DeepLinkInfo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$help_DeepLinkInfo tLRPC$help_DeepLinkInfo;
        if (i == 1722786150) {
            tLRPC$help_DeepLinkInfo = new TLRPC$help_DeepLinkInfo() { // from class: org.telegram.tgnet.TLRPC$TL_help_deepLinkInfoEmpty
                public static int constructor = 1722786150;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else {
            tLRPC$help_DeepLinkInfo = i != 1783556146 ? null : new TLRPC$TL_help_deepLinkInfo();
        }
        if (tLRPC$help_DeepLinkInfo == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in help_DeepLinkInfo", Integer.valueOf(i)));
        }
        if (tLRPC$help_DeepLinkInfo != null) {
            tLRPC$help_DeepLinkInfo.readParams(abstractSerializedData, z);
        }
        return tLRPC$help_DeepLinkInfo;
    }
}
