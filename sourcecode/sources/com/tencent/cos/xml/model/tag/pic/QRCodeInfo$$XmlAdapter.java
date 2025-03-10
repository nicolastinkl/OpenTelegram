package com.tencent.cos.xml.model.tag.pic;

import com.tencent.cos.xml.model.tag.pic.QRCodeInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class QRCodeInfo$$XmlAdapter extends IXmlAdapter<QRCodeInfo> {
    private HashMap<String, ChildElementBinder<QRCodeInfo>> childElementBinders;

    public QRCodeInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<QRCodeInfo>> hashMap = new HashMap<>();
        this.childElementBinders = hashMap;
        hashMap.put("CodeUrl", new ChildElementBinder<QRCodeInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.QRCodeInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, QRCodeInfo qRCodeInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                qRCodeInfo.codeUrl = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CodeLocation", new ChildElementBinder<QRCodeInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.QRCodeInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, QRCodeInfo qRCodeInfo, String str) throws IOException, XmlPullParserException {
                if (qRCodeInfo.codeLocation == null) {
                    qRCodeInfo.codeLocation = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        qRCodeInfo.codeLocation.add((QRCodeInfo.QRCodePoint) QCloudXml.fromXml(xmlPullParser, QRCodeInfo.QRCodePoint.class, "Point"));
                    } else if (eventType == 3 && "CodeLocation".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public QRCodeInfo fromXml(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        QRCodeInfo qRCodeInfo = new QRCodeInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<QRCodeInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, qRCodeInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "QRcodeInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return qRCodeInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return qRCodeInfo;
    }
}
