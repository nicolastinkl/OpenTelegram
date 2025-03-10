package com.coremedia.iso.boxes.sampleentry;

import com.googlecode.mp4parser.AbstractContainerBox;

/* loaded from: classes.dex */
public abstract class AbstractSampleEntry extends AbstractContainerBox {
    protected int dataReferenceIndex;

    protected AbstractSampleEntry(String str) {
        super(str);
        this.dataReferenceIndex = 1;
    }

    public void setDataReferenceIndex(int i) {
        this.dataReferenceIndex = i;
    }
}
