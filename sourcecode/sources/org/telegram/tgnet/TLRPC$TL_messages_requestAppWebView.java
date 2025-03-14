package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_requestAppWebView extends TLObject {
    public static int constructor = -1940243652;
    public TLRPC$InputBotApp app;
    public int flags;
    public TLRPC$InputPeer peer;
    public String platform;
    public String start_param;
    public TLRPC$TL_dataJSON theme_params;
    public boolean write_allowed;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_appWebViewResultUrl.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        int i = this.write_allowed ? this.flags | 1 : this.flags & (-2);
        this.flags = i;
        abstractSerializedData.writeInt32(i);
        this.peer.serializeToStream(abstractSerializedData);
        this.app.serializeToStream(abstractSerializedData);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.start_param);
        }
        if ((this.flags & 4) != 0) {
            this.theme_params.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeString(this.platform);
    }
}
