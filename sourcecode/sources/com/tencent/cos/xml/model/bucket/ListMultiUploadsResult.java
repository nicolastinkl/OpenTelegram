package com.tencent.cos.xml.model.bucket;

import android.util.Xml;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ListMultipartUploads;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ListMultiUploadsResult extends CosXmlResult {
    public ListMultipartUploads listMultipartUploads;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.listMultipartUploads = new ListMultipartUploads();
        try {
            parseListMultipartUploadsResult(httpResponse.byteStream(), this.listMultipartUploads);
        } catch (IOException e) {
            throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
        } catch (XmlPullParserException e2) {
            throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ListMultipartUploads listMultipartUploads = this.listMultipartUploads;
        if (listMultipartUploads != null) {
            return listMultipartUploads.toString();
        }
        return super.printResult();
    }

    public static void parseListMultipartUploadsResult(InputStream inputStream, ListMultipartUploads listMultipartUploads) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        listMultipartUploads.uploads = new ArrayList();
        listMultipartUploads.commonPrefixes = new ArrayList();
        ListMultipartUploads.Upload upload = null;
        ListMultipartUploads.CommonPrefixes commonPrefixes = null;
        ListMultipartUploads.Owner owner = null;
        ListMultipartUploads.Initiator initiator = null;
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    newPullParser.next();
                    listMultipartUploads.bucket = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-Type")) {
                    newPullParser.next();
                    listMultipartUploads.encodingType = newPullParser.getText();
                } else if (name.equalsIgnoreCase("KeyMarker")) {
                    newPullParser.next();
                    listMultipartUploads.keyMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadIdMarker")) {
                    newPullParser.next();
                    listMultipartUploads.uploadIdMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("NextKeyMarker")) {
                    newPullParser.next();
                    listMultipartUploads.nextKeyMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("NextUploadIdMarker")) {
                    newPullParser.next();
                    listMultipartUploads.nextUploadIdMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxUploads")) {
                    newPullParser.next();
                    listMultipartUploads.maxUploads = newPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    newPullParser.next();
                    listMultipartUploads.isTruncated = Boolean.parseBoolean(newPullParser.getText());
                } else if (name.equalsIgnoreCase("Prefix")) {
                    newPullParser.next();
                    if (commonPrefixes == null) {
                        listMultipartUploads.prefix = newPullParser.getText();
                    } else {
                        commonPrefixes.prefix = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Delimiter")) {
                    newPullParser.next();
                    listMultipartUploads.delimiter = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Upload")) {
                    upload = new ListMultipartUploads.Upload();
                } else if (name.equalsIgnoreCase("Key")) {
                    newPullParser.next();
                    upload.key = newPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    newPullParser.next();
                    upload.uploadID = newPullParser.getText();
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    newPullParser.next();
                    upload.storageClass = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Initiator")) {
                    initiator = new ListMultipartUploads.Initiator();
                } else if (name.equalsIgnoreCase("UIN")) {
                    newPullParser.next();
                    if (initiator != null) {
                        initiator.uin = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListMultipartUploads.Owner();
                } else if (name.equalsIgnoreCase("UID")) {
                    newPullParser.next();
                    if (owner != null) {
                        owner.uid = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ID")) {
                    newPullParser.next();
                    if (owner != null) {
                        owner.id = newPullParser.getText();
                    } else if (initiator != null) {
                        initiator.id = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DisplayName")) {
                    newPullParser.next();
                    if (owner != null) {
                        owner.displayName = newPullParser.getText();
                    } else if (initiator != null) {
                        initiator.displayName = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Initiated")) {
                    newPullParser.next();
                    upload.initiated = newPullParser.getText();
                } else if (name.equalsIgnoreCase("CommonPrefixs")) {
                    commonPrefixes = new ListMultipartUploads.CommonPrefixes();
                }
            } else if (eventType == 3) {
                String name2 = newPullParser.getName();
                if (name2.equalsIgnoreCase("Upload")) {
                    listMultipartUploads.uploads.add(upload);
                    upload = null;
                } else if (name2.equalsIgnoreCase("CommonPrefixs")) {
                    listMultipartUploads.commonPrefixes.add(commonPrefixes);
                    commonPrefixes = null;
                } else if (name2.equalsIgnoreCase("Owner")) {
                    upload.owner = owner;
                    owner = null;
                } else if (name2.equalsIgnoreCase("Initiator")) {
                    upload.initiator = initiator;
                    initiator = null;
                }
            }
        }
    }
}
