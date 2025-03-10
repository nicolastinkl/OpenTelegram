package com.tencent.beacon.base.net.a;

import android.text.TextUtils;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.e.h;
import com.tencent.beacon.pack.SocketRequestPackage;
import java.util.Map;

/* compiled from: SocketRequestConverter.java */
/* loaded from: classes.dex */
public final class d implements c<JceRequestEntity, SocketRequestPackage> {
    private Map<String, String> b(JceRequestEntity jceRequestEntity) {
        Map<String, String> header = jceRequestEntity.getHeader();
        if (!header.containsKey("sid")) {
            String c = h.b().c();
            if (!TextUtils.isEmpty(c)) {
                header.put("sid", c);
            }
        }
        return header;
    }

    @Override // com.tencent.beacon.base.net.a.c
    public SocketRequestPackage a(JceRequestEntity jceRequestEntity) {
        return new SocketRequestPackage(b(jceRequestEntity), jceRequestEntity.getContent());
    }
}
