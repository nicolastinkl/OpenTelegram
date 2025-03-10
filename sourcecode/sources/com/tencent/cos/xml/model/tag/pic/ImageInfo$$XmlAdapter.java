package com.tencent.cos.xml.model.tag.pic;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ImageInfo$$XmlAdapter extends IXmlAdapter<ImageInfo> {
    private HashMap<String, ChildElementBinder<ImageInfo>> childElementBinders;

    public ImageInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ImageInfo>> hashMap = new HashMap<>();
        this.childElementBinders = hashMap;
        hashMap.put("Format", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.width = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.height = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.quality = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Ave", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.ave = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Orientation", new ChildElementBinder<ImageInfo>() { // from class: com.tencent.cos.xml.model.tag.pic.ImageInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageInfo imageInfo, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                imageInfo.orientation = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ImageInfo fromXml(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        ImageInfo imageInfo = new ImageInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ImageInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, imageInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return imageInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return imageInfo;
    }
}
