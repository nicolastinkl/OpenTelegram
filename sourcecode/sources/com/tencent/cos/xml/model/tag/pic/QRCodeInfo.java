package com.tencent.cos.xml.model.tag.pic;

import android.graphics.Point;
import com.tencent.qcloud.qcloudxml.annoation.XmlBean;
import com.tencent.qcloud.qcloudxml.annoation.XmlElement;
import java.util.List;

@XmlBean(method = XmlBean.GenerateMethod.FROM, name = "QRcodeInfo")
/* loaded from: classes.dex */
public class QRCodeInfo {
    public List<QRCodePoint> codeLocation;
    public String codeUrl;

    @XmlBean(method = XmlBean.GenerateMethod.FROM, name = "Point")
    public static class QRCodePoint {

        @XmlElement(ignoreName = true)
        public String point;

        public Point point() {
            String[] split = this.point.split(",");
            if (split.length == 2) {
                return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
            return null;
        }
    }
}
