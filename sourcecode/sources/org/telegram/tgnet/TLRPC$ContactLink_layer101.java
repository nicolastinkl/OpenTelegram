package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$ContactLink_layer101 extends TLObject {
    public static TLRPC$ContactLink_layer101 TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$ContactLink_layer101 tLRPC$ContactLink_layer101;
        if (i == -721239344) {
            tLRPC$ContactLink_layer101 = new TLRPC$ContactLink_layer101() { // from class: org.telegram.tgnet.TLRPC$TL_contactLinkContact
                public static int constructor = -721239344;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else if (i == -17968211) {
            tLRPC$ContactLink_layer101 = new TLRPC$ContactLink_layer101() { // from class: org.telegram.tgnet.TLRPC$TL_contactLinkNone
                public static int constructor = -17968211;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else {
            tLRPC$ContactLink_layer101 = i != 1599050311 ? null : new TLRPC$ContactLink_layer101() { // from class: org.telegram.tgnet.TLRPC$TL_contactLinkUnknown
                public static int constructor = 1599050311;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        }
        if (tLRPC$ContactLink_layer101 == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in ContactLink", Integer.valueOf(i)));
        }
        if (tLRPC$ContactLink_layer101 != null) {
            tLRPC$ContactLink_layer101.readParams(abstractSerializedData, z);
        }
        return tLRPC$ContactLink_layer101;
    }
}
