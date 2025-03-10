package com.tencent.cos.xml.utils;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class QCloudXmlUtils {
    public static <T> T fromXml(InputStream inputStream, Class<T> cls) throws CosXmlClientException {
        try {
            return (T) QCloudXml.fromXml(inputStream, cls);
        } catch (IOException e) {
            throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e);
        } catch (XmlPullParserException e2) {
            throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e2);
        }
    }

    public static <T> String toXml(T t) throws CosXmlClientException {
        try {
            return QCloudXml.toXml(t);
        } catch (IOException e) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e);
        } catch (XmlPullParserException e2) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), e2);
        }
    }
}
