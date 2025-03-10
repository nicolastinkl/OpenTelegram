package com.tencent.cos.xml.model.tag.pic;

import com.tencent.cos.xml.crypto.Headers;
import com.tencent.qcloud.qcloudxml.annoation.XmlBean;
import com.tencent.qcloud.qcloudxml.annoation.XmlElement;

@XmlBean(method = XmlBean.GenerateMethod.FROM, name = "Object")
/* loaded from: classes.dex */
public class PicObject {

    @XmlElement(name = Headers.ETAG)
    public String etag;

    @XmlElement(name = "Format")
    public String format;

    @XmlElement(name = "Height")
    public int height;

    @XmlElement(name = "Key")
    public String key;

    @XmlElement(name = "Location")
    public String location;

    @XmlElement(name = "Quality")
    public int quality;

    @XmlElement(name = "Size")
    public int size;

    @XmlElement(name = "Width")
    public int width;
}
