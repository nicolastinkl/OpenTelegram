package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_messages_getSearchResultsCalendar extends TLObject {
    public static int constructor = 1240514025;
    public TLRPC$MessagesFilter filter;
    public int offset_date;
    public int offset_id;
    public TLRPC$InputPeer peer;

    @Override // org.telegram.tgnet.TLObject
    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_searchResultsCalendar.TLdeserialize(abstractSerializedData, i, z);
    }

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.peer.serializeToStream(abstractSerializedData);
        this.filter.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.offset_id);
        abstractSerializedData.writeInt32(this.offset_date);
    }
}
