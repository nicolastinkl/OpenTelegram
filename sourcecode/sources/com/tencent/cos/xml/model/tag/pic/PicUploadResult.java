package com.tencent.cos.xml.model.tag.pic;

import com.tencent.qcloud.qcloudxml.annoation.XmlBean;
import com.tencent.qcloud.qcloudxml.annoation.XmlElement;
import java.util.List;

@XmlBean(method = XmlBean.GenerateMethod.FROM, name = "UploadResult")
/* loaded from: classes.dex */
public class PicUploadResult {

    @XmlElement(name = "OriginalInfo")
    public PicOriginalInfo originalInfo;

    @XmlElement(name = "ProcessResults")
    public List<PicObject> processResults;
}
