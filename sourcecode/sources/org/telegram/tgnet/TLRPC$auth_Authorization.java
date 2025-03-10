package org.telegram.tgnet;

/* loaded from: classes3.dex */
public abstract class TLRPC$auth_Authorization extends TLObject {
    public static TLRPC$auth_Authorization TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$auth_Authorization tLRPC$JMT_auth_authorizationV2;
        switch (i) {
            case -1283734134:
                tLRPC$JMT_auth_authorizationV2 = new TLRPC$JMT_auth_authorizationV2();
                break;
            case 782418132:
                tLRPC$JMT_auth_authorizationV2 = new TLRPC$TL_auth_authorization();
                break;
            case 872119224:
                tLRPC$JMT_auth_authorizationV2 = new TLRPC$TL_auth_authorization();
                break;
            case 1148485274:
                tLRPC$JMT_auth_authorizationV2 = new TLRPC$TL_auth_authorizationSignUpRequired();
                break;
            default:
                tLRPC$JMT_auth_authorizationV2 = null;
                break;
        }
        if (tLRPC$JMT_auth_authorizationV2 == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in auth_Authorization", Integer.valueOf(i)));
        }
        if (tLRPC$JMT_auth_authorizationV2 != null) {
            tLRPC$JMT_auth_authorizationV2.readParams(abstractSerializedData, z);
        }
        return tLRPC$JMT_auth_authorizationV2;
    }
}
