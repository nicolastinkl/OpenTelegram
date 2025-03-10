package com.tencent.cos.xml.model.tag.pic;

import com.tencent.qcloud.qcloudxml.annoation.XmlBean;
import com.tencent.qcloud.qcloudxml.annoation.XmlElement;

@XmlBean(method = XmlBean.GenerateMethod.FROM, name = "ImageInfo")
/* loaded from: classes.dex */
public class ImageInfo {

    @XmlElement(name = "Ave")
    public String ave;

    @XmlElement(name = "Format")
    public String format;

    @XmlElement(name = "Height")
    public int height;

    @XmlElement(name = "Orientation")
    public int orientation;

    @XmlElement(name = "Quality")
    public int quality;

    @XmlElement(name = "Width")
    public int width;
}
