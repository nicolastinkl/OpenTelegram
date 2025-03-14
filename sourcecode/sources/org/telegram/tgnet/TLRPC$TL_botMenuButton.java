package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_botMenuButton extends TLRPC$BotMenuButton {
    public static int constructor = -944407322;
    public String text;
    public String url;

    @Override // org.telegram.tgnet.TLObject
    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
        this.url = abstractSerializedData.readString(z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
        abstractSerializedData.writeString(this.url);
    }
}
