package com.tencent.qcloud.qcloudxml.core;

import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class QCloudXml {
    static final Map<Class<?>, IXmlAdapter<?>> XML_ADAPTERS = new HashMap();

    public static <T> T fromXml(InputStream inputStream, Class<T> cls) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, "UTF-8");
        return (T) fromXml(newPullParser, cls);
    }

    public static <T> T fromXml(XmlPullParser xmlPullParser, Class<T> cls) throws XmlPullParserException, IOException {
        return (T) fromXml(xmlPullParser, cls, null);
    }

    public static <T> T fromXml(XmlPullParser xmlPullParser, Class<T> cls, String str) throws XmlPullParserException, IOException {
        return (T) createXmlAdapter(cls).fromXml(xmlPullParser, str);
    }

    public static <T> String toXml(T t) throws XmlPullParserException, IOException {
        if (t == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        XmlSerializer newSerializer = XmlPullParserFactory.newInstance().newSerializer();
        newSerializer.setOutput(stringWriter);
        newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        newSerializer.startDocument("UTF-8", null);
        toXml(newSerializer, t);
        newSerializer.endDocument();
        return removeXMLHeader(stringWriter.toString());
    }

    public static <T> void toXml(XmlSerializer xmlSerializer, T t) throws XmlPullParserException, IOException {
        toXml(xmlSerializer, t, null);
    }

    public static <T> void toXml(XmlSerializer xmlSerializer, T t, String str) throws XmlPullParserException, IOException {
        createXmlAdapter(t.getClass()).toXml(xmlSerializer, t, str);
    }

    private static <T> IXmlAdapter<T> createXmlAdapter(Class<?> cls) {
        Map<Class<?>, IXmlAdapter<?>> map = XML_ADAPTERS;
        IXmlAdapter<T> iXmlAdapter = (IXmlAdapter) map.get(cls);
        if (iXmlAdapter != null) {
            return iXmlAdapter;
        }
        String name = cls.getName();
        try {
            IXmlAdapter<T> iXmlAdapter2 = (IXmlAdapter) Class.forName(name + "$$XmlAdapter").newInstance();
            map.put(cls, iXmlAdapter2);
            return iXmlAdapter2;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No IXmlAdapter for class " + name + " found. Expected name of the xml adapter is " + name + "$$XmlAdapter", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("No IXmlAdapter for class " + name + " found. Expected name of the xml adapter is " + name + "$$XmlAdapter", e2);
        } catch (InstantiationException e3) {
            throw new RuntimeException("No IXmlAdapter for class " + name + " found. Expected name of the xml adapter is " + name + "$$XmlAdapter", e3);
        }
    }

    private static String removeXMLHeader(String str) {
        return (str == null || !str.startsWith("<?xml")) ? str : str.substring(str.indexOf("?>") + 2);
    }
}
