package com.tencent.qcloud.qcloudxml.core;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public abstract class IXmlAdapter<T> {
    public T fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        throw new IllegalStateException("The fromXml method is not implemented");
    }

    public void toXml(XmlSerializer xmlSerializer, T t, String str) throws XmlPullParserException, IOException {
        throw new IllegalStateException("The toXml method is not implemented");
    }
}
