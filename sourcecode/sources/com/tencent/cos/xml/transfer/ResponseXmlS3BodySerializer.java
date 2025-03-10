package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CosError;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ResponseXmlS3BodySerializer<T> extends ResponseBodyConverter<T> {
    private CosXmlResult cosXmlResult;

    public ResponseXmlS3BodySerializer(CosXmlResult cosXmlResult) {
        this.cosXmlResult = cosXmlResult;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse httpResponse) throws QCloudClientException, QCloudServiceException {
        parseCOSXMLError(httpResponse);
        this.cosXmlResult.parseResponseBody(httpResponse);
        return (T) this.cosXmlResult;
    }

    private void parseCOSXMLError(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        int code = httpResponse.code();
        if (code < 200 || code >= 300) {
            CosXmlServiceException cosXmlServiceException = new CosXmlServiceException(httpResponse.message());
            cosXmlServiceException.setStatusCode(code);
            cosXmlServiceException.setRequestId(httpResponse.header(Headers.REQUEST_ID));
            InputStream byteStream = httpResponse.byteStream();
            if (byteStream != null) {
                CosError cosError = new CosError();
                try {
                    BaseXmlSlimParser.parseError(byteStream, cosError);
                    String str = cosError.code;
                    if (str != null) {
                        cosXmlServiceException.setErrorCode(str);
                    }
                    String str2 = cosError.message;
                    if (str2 != null) {
                        cosXmlServiceException.setErrorMessage(str2);
                    }
                    String str3 = cosError.requestId;
                    if (str3 != null) {
                        cosXmlServiceException.setRequestId(str3);
                    }
                    String str4 = cosError.resource;
                    if (str4 != null) {
                        cosXmlServiceException.setServiceName(str4);
                        throw cosXmlServiceException;
                    }
                    throw cosXmlServiceException;
                } catch (IOException e) {
                    throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
                } catch (XmlPullParserException e2) {
                    throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
                }
            }
            throw cosXmlServiceException;
        }
    }
}
