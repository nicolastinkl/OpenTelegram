package com.tencent.cos.xml.transfer;

import android.util.Xml;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.model.tag.CopyObject;
import com.tencent.cos.xml.model.tag.InitiateMultipartUpload;
import com.tencent.cos.xml.model.tag.ListParts;
import com.tencent.cos.xml.model.tag.PostResponse;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class XmlSlimParser extends BaseXmlSlimParser {
    public static void parseInitiateMultipartUploadResult(InputStream inputStream, InitiateMultipartUpload initiateMultipartUpload) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    newPullParser.next();
                    initiateMultipartUpload.bucket = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Key")) {
                    newPullParser.next();
                    initiateMultipartUpload.key = newPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    newPullParser.next();
                    initiateMultipartUpload.uploadId = newPullParser.getText();
                }
            }
        }
    }

    public static void parseListPartsResult(InputStream inputStream, ListParts listParts) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        listParts.parts = new ArrayList();
        ListParts.Owner owner = null;
        ListParts.Initiator initiator = null;
        ListParts.Part part = null;
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    newPullParser.next();
                    listParts.bucket = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-type")) {
                    newPullParser.next();
                    listParts.encodingType = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Key")) {
                    newPullParser.next();
                    listParts.key = newPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    newPullParser.next();
                    listParts.uploadId = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListParts.Owner();
                } else if (name.equalsIgnoreCase("Initiator")) {
                    initiator = new ListParts.Initiator();
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
                        owner.disPlayName = newPullParser.getText();
                    } else if (initiator != null) {
                        initiator.disPlayName = newPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("PartNumberMarker")) {
                    newPullParser.next();
                    listParts.partNumberMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    newPullParser.next();
                    listParts.storageClass = newPullParser.getText();
                } else if (name.equalsIgnoreCase("NextPartNumberMarker")) {
                    newPullParser.next();
                    listParts.nextPartNumberMarker = newPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxParts")) {
                    newPullParser.next();
                    listParts.maxParts = newPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    newPullParser.next();
                    listParts.isTruncated = Boolean.parseBoolean(newPullParser.getText());
                } else if (name.equalsIgnoreCase("Part")) {
                    part = new ListParts.Part();
                } else if (name.equalsIgnoreCase("PartNumber")) {
                    newPullParser.next();
                    part.partNumber = newPullParser.getText();
                } else if (name.equalsIgnoreCase("LastModified")) {
                    newPullParser.next();
                    part.lastModified = newPullParser.getText();
                } else if (name.equalsIgnoreCase(Headers.ETAG)) {
                    newPullParser.next();
                    part.eTag = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Size")) {
                    newPullParser.next();
                    part.size = newPullParser.getText();
                }
            } else if (eventType == 3) {
                String name2 = newPullParser.getName();
                if (name2.equalsIgnoreCase("Owner")) {
                    listParts.owner = owner;
                    owner = null;
                } else if (name2.equalsIgnoreCase("Initiator")) {
                    listParts.initiator = initiator;
                    initiator = null;
                } else if (name2.equalsIgnoreCase("Part")) {
                    listParts.parts.add(part);
                    part = null;
                }
            }
        }
    }

    public static void parsePostResponseResult(InputStream inputStream, PostResponse postResponse) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase("Location")) {
                    newPullParser.next();
                    postResponse.location = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Bucket")) {
                    newPullParser.next();
                    postResponse.bucket = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Key")) {
                    newPullParser.next();
                    postResponse.key = newPullParser.getText();
                } else if (name.equalsIgnoreCase(Headers.ETAG)) {
                    newPullParser.next();
                    postResponse.eTag = newPullParser.getText();
                }
            }
        }
    }

    public static void parseCopyObjectResult(InputStream inputStream, CopyObject copyObject) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase(Headers.ETAG)) {
                    newPullParser.next();
                    copyObject.eTag = newPullParser.getText();
                } else if (name.equalsIgnoreCase("LastModified")) {
                    newPullParser.next();
                    copyObject.lastModified = newPullParser.getText();
                }
            }
        }
    }
}
