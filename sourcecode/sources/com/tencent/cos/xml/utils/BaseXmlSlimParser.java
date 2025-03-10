package com.tencent.cos.xml.utils;

import android.util.Xml;
import com.tencent.cos.xml.model.tag.CosError;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class BaseXmlSlimParser {
    public static void parseError(InputStream inputStream, CosError cosError) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if (name.equalsIgnoreCase("Code")) {
                    newPullParser.next();
                    cosError.code = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Message")) {
                    newPullParser.next();
                    cosError.message = newPullParser.getText();
                } else if (name.equalsIgnoreCase("Resource")) {
                    newPullParser.next();
                    cosError.resource = newPullParser.getText();
                } else if (name.equalsIgnoreCase("RequestId")) {
                    newPullParser.next();
                    cosError.requestId = newPullParser.getText();
                } else if (name.equalsIgnoreCase("TraceId")) {
                    newPullParser.next();
                    cosError.traceId = newPullParser.getText();
                }
            }
        }
    }
}
