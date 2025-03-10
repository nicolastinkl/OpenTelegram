package org.telegram.tgnet;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class TLRPC$Updates extends TLObject {
    public long chat_id;
    public int date;
    public int flags;
    public long from_id;
    public TLRPC$MessageFwdHeader fwd_from;
    public int id;
    public TLRPC$MessageMedia media;
    public boolean media_unread;
    public boolean mentioned;
    public String message;
    public boolean out;
    public int pts;
    public int pts_count;
    public TLRPC$TL_messageReplyHeader reply_to;
    public int seq;
    public int seq_start;
    public boolean silent;
    public int ttl_period;
    public TLRPC$Update update;
    public long user_id;
    public long via_bot_id;
    public ArrayList<TLRPC$Update> updates = new ArrayList<>();
    public ArrayList<TLRPC$User> users = new ArrayList<>();
    public ArrayList<TLRPC$Chat> chats = new ArrayList<>();
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList<>();

    public static TLRPC$Updates TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Updates tLRPC$JMT_receiveRedPacketByRedPacketId;
        switch (i) {
            case -2143691715:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$JMT_receiveRedPacketByRedPacketId();
                break;
            case -1877614335:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updateShortSentMessage();
                break;
            case -484987010:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updatesTooLong();
                break;
            case -55788147:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$JMT_getRedPackageResultsByRedPacketId();
                break;
            case 826001400:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updateShortMessage();
                break;
            case 1299050149:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updateShortChatMessage();
                break;
            case 1918567619:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updatesCombined();
                break;
            case 1957577280:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updates();
                break;
            case 2027216577:
                tLRPC$JMT_receiveRedPacketByRedPacketId = new TLRPC$TL_updateShort();
                break;
            default:
                tLRPC$JMT_receiveRedPacketByRedPacketId = null;
                break;
        }
        if (tLRPC$JMT_receiveRedPacketByRedPacketId == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in Updates", Integer.valueOf(i)));
        }
        if (tLRPC$JMT_receiveRedPacketByRedPacketId != null) {
            tLRPC$JMT_receiveRedPacketByRedPacketId.readParams(abstractSerializedData, z);
        }
        return tLRPC$JMT_receiveRedPacketByRedPacketId;
    }
}
