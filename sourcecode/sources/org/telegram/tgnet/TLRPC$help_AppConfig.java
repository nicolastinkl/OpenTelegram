package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$help_AppConfig extends TLObject {
    public static TLRPC$help_AppConfig TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$help_AppConfig tLRPC$TL_help_appConfig;
        if (i == -585598930) {
            tLRPC$TL_help_appConfig = new TLRPC$TL_help_appConfig();
        } else {
            tLRPC$TL_help_appConfig = i != 2094949405 ? null : new TLRPC$help_AppConfig() { // from class: org.telegram.tgnet.TLRPC$TL_help_appConfigNotModified
                public static int constructor = 2094949405;

                @Override // org.telegram.tgnet.TLObject
                public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                    abstractSerializedData2.writeInt32(constructor);
                }
            };
        }
        if (tLRPC$TL_help_appConfig == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in help_AppConfig", Integer.valueOf(i)));
        }
        if (tLRPC$TL_help_appConfig != null) {
            tLRPC$TL_help_appConfig.readParams(abstractSerializedData, z);
        }
        return tLRPC$TL_help_appConfig;
    }
}
