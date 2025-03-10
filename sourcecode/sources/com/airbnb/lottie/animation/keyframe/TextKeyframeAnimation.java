package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: classes.dex */
public class TextKeyframeAnimation extends KeyframeAnimation<DocumentData> {
    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    /* bridge */ /* synthetic */ Object getValue(Keyframe keyframe, float f) {
        return getValue((Keyframe<DocumentData>) keyframe, f);
    }

    public TextKeyframeAnimation(List<Keyframe<DocumentData>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    DocumentData getValue(Keyframe<DocumentData> keyframe, float f) {
        DocumentData documentData;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback == 0) {
            if (f != 1.0f || (documentData = keyframe.endValue) == null) {
                return keyframe.startValue;
            }
            return documentData;
        }
        float f2 = keyframe.startFrame;
        Float f3 = keyframe.endFrame;
        float floatValue = f3 == null ? Float.MAX_VALUE : f3.floatValue();
        DocumentData documentData2 = keyframe.startValue;
        DocumentData documentData3 = documentData2;
        DocumentData documentData4 = keyframe.endValue;
        return (DocumentData) lottieValueCallback.getValueInternal(f2, floatValue, documentData3, documentData4 == null ? documentData2 : documentData4, f, getInterpolatedCurrentKeyframeProgress(), getProgress());
    }

    public void setStringValueCallback(final LottieValueCallback<String> lottieValueCallback) {
        final LottieFrameInfo lottieFrameInfo = new LottieFrameInfo();
        final DocumentData documentData = new DocumentData();
        super.setValueCallback(new LottieValueCallback<DocumentData>(this) { // from class: com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.airbnb.lottie.value.LottieValueCallback
            public DocumentData getValue(LottieFrameInfo<DocumentData> lottieFrameInfo2) {
                lottieFrameInfo.set(lottieFrameInfo2.getStartFrame(), lottieFrameInfo2.getEndFrame(), lottieFrameInfo2.getStartValue().text, lottieFrameInfo2.getEndValue().text, lottieFrameInfo2.getLinearKeyframeProgress(), lottieFrameInfo2.getInterpolatedKeyframeProgress(), lottieFrameInfo2.getOverallProgress());
                String str = (String) lottieValueCallback.getValue(lottieFrameInfo);
                DocumentData endValue = lottieFrameInfo2.getInterpolatedKeyframeProgress() == 1.0f ? lottieFrameInfo2.getEndValue() : lottieFrameInfo2.getStartValue();
                documentData.set(str, endValue.fontName, endValue.size, endValue.justification, endValue.tracking, endValue.lineHeight, endValue.baselineShift, endValue.color, endValue.strokeColor, endValue.strokeWidth, endValue.strokeOverFill, endValue.boxPosition, endValue.boxSize);
                return documentData;
            }
        });
    }
}
