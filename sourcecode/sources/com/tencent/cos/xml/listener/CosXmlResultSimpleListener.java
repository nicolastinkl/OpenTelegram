package com.tencent.cos.xml.listener;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;

/* loaded from: classes.dex */
public interface CosXmlResultSimpleListener {
    void onFail(CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException);

    void onSuccess();
}
