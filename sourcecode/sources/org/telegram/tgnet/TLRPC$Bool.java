package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$Bool extends TLObject {
    public static TLRPC$Bool TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Bool tLRPC$Bool;
        if (i == -1720552011) {
            tLRPC$Bool = new TLRPC$Bool() { // from class: org.telegram.tgnet.TLRPC$TL_boolTrue
                public static int constructor = -1720552011;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        } else {
            tLRPC$Bool = i != -1132882121 ? null : new TLRPC$Bool() { // from class: org.telegram.tgnet.TLRPC$TL_boolFalse
                public static int constructor = -1132882121;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        }
        if (tLRPC$Bool == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in Bool", Integer.valueOf(i)));
        }
        if (tLRPC$Bool != null) {
            tLRPC$Bool.readParams(abstractSerializedData, z);
        }
        return tLRPC$Bool;
    }
}
