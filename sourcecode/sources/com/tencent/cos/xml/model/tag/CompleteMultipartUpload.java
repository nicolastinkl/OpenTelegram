package com.tencent.cos.xml.model.tag;

import java.util.List;

/* loaded from: classes.dex */
public class CompleteMultipartUpload {
    public List<Part> parts;

    public static class Part {
        public String eTag;
        public int partNumber;
    }
}
