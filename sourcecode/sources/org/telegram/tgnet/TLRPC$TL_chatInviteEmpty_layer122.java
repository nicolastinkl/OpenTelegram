package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_chatInviteEmpty_layer122 extends TLRPC$TL_chatInviteExported {
    public static int constructor = 1776236393;

    @Override // org.telegram.tgnet.TLRPC$TL_chatInviteExported, org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
