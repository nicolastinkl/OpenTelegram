package org.telegram.tgnet;

/* loaded from: classes3.dex */
public class TLRPC$TL_inputReportReasonPersonalDetails extends TLRPC$ReportReason {
    public static int constructor = -1631091139;

    @Override // org.telegram.tgnet.TLObject
    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
