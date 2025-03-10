package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CopyObject;
import com.tencent.cos.xml.model.tag.CosError;
import com.tencent.cos.xml.transfer.XmlSlimParser;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import com.tencent.cos.xml.utils.CloseUtil;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class CopyObjectResult extends CosXmlResult {
    public CopyObject copyObject;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        byte[] bytes;
        ByteArrayInputStream byteArrayInputStream;
        super.parseResponseBody(httpResponse);
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            try {
                this.copyObject = new CopyObject();
                bytes = httpResponse.bytes();
                byteArrayInputStream = new ByteArrayInputStream(bytes);
            } catch (IOException e) {
                e = e;
            } catch (XmlPullParserException e2) {
                e = e2;
            }
            try {
                XmlSlimParser.parseCopyObjectResult(byteArrayInputStream, this.copyObject);
                if (this.copyObject.eTag == null && bytes != null && bytes.length > 0) {
                    byteArrayInputStream.reset();
                    CosXmlServiceException cosXmlServiceException = new CosXmlServiceException("failed");
                    CosError cosError = new CosError();
                    BaseXmlSlimParser.parseError(byteArrayInputStream, cosError);
                    cosXmlServiceException.setErrorCode(cosError.code);
                    cosXmlServiceException.setErrorMessage(cosError.message);
                    cosXmlServiceException.setRequestId(cosError.requestId);
                    cosXmlServiceException.setServiceName(cosError.resource);
                    cosXmlServiceException.setStatusCode(httpResponse.code());
                    throw cosXmlServiceException;
                }
                CloseUtil.closeQuietly(byteArrayInputStream);
            } catch (IOException e3) {
                e = e3;
                throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
            } catch (XmlPullParserException e4) {
                e = e4;
                throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e);
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream2 = byteArrayInputStream;
                CloseUtil.closeQuietly(byteArrayInputStream2);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        CopyObject copyObject = this.copyObject;
        return copyObject != null ? copyObject.toString() : super.printResult();
    }
}
