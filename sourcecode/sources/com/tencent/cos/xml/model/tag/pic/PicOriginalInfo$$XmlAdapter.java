package com.tencent.cos.xml.model.tag.pic;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class PicOriginalInfo$$XmlAdapter extends IXmlAdapter<PicOriginalInfo> {
    private HashMap<String, ChildElementBinder<PicOriginalInfo>> childElementBinders;

    public PicOriginalInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PicOriginalInfo>> hashMap = new HashMap<>();
        this.childElementBinders = hashMap;
        hashMap.put("Key", new ChildElementBinder<PicOriginalInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.PicOriginalInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicOriginalInfo picOriginalInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picOriginalInfo.key = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<PicOriginalInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.PicOriginalInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicOriginalInfo picOriginalInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picOriginalInfo.location = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Headers.ETAG, new ChildElementBinder<PicOriginalInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.PicOriginalInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicOriginalInfo picOriginalInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picOriginalInfo.etag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ImageInfo", new ChildElementBinder<PicOriginalInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.PicOriginalInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicOriginalInfo picOriginalInfo, String str) throws IOException, XmlPullParserException {
                picOriginalInfo.imageInfo = (ImageInfo) QCloudXml.fromXml(xmlPullParser, ImageInfo.class, "ImageInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PicOriginalInfo fromXml(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        PicOriginalInfo picOriginalInfo = new PicOriginalInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PicOriginalInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, picOriginalInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "OriginalInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return picOriginalInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return picOriginalInfo;
    }
}
