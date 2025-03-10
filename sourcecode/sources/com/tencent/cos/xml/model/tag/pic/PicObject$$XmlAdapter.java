package com.tencent.cos.xml.model.tag.pic;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class PicObject$$XmlAdapter extends IXmlAdapter<PicObject> {
    private HashMap<String, ChildElementBinder<PicObject>> childElementBinders;

    public PicObject$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PicObject>> hashMap = new HashMap<>();
        this.childElementBinders = hashMap;
        hashMap.put("Key", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.key = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.location = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Format", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.width = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.height = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Size", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.size = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.quality = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(Headers.ETAG, new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws IOException, XmlPullParserException {
                xmlPullParser.next();
                picObject.etag = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PicObject fromXml(XmlPullParser xmlPullParser, String str) throws IOException, XmlPullParserException {
        PicObject picObject = new PicObject();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PicObject> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, picObject, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Object" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return picObject;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return picObject;
    }
}
