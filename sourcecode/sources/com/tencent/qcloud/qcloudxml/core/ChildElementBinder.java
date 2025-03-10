package com.tencent.qcloud.qcloudxml.core;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public interface ChildElementBinder<T> {
    void fromXml(XmlPullParser xmlPullParser, T t, String str) throws IOException, XmlPullParserException;
}
