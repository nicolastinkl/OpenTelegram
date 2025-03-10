package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.model.tag.CompleteMultipartUpload;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class XmlSlimBuilder {
    public static String buildCompleteMultipartUpload(CompleteMultipartUpload completeMultipartUpload) throws IOException, XmlPullParserException {
        if (completeMultipartUpload == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer newSerializer = XmlPullParserFactory.newInstance().newSerializer();
        newSerializer.setOutput(stringWriter);
        newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        newSerializer.startDocument("UTF-8", null);
        newSerializer.startTag("", "CompleteMultipartUpload");
        List<CompleteMultipartUpload.Part> list = completeMultipartUpload.parts;
        if (list != null) {
            for (CompleteMultipartUpload.Part part : list) {
                if (part != null) {
                    newSerializer.startTag("", "Part");
                    addElement(newSerializer, "PartNumber", String.valueOf(part.partNumber));
                    addElement(newSerializer, Headers.ETAG, part.eTag);
                    newSerializer.endTag("", "Part");
                }
            }
        }
        newSerializer.endTag("", "CompleteMultipartUpload");
        newSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    private static void addElement(XmlSerializer xmlSerializer, String str, String str2) throws IOException {
        if (str2 != null) {
            xmlSerializer.startTag("", str);
            xmlSerializer.text(str2);
            xmlSerializer.endTag("", str);
        }
    }

    private static String removeXMLHeader(String str) {
        return (str == null || !str.startsWith("<?xml")) ? str : str.substring(str.indexOf("?>") + 2);
    }
}
