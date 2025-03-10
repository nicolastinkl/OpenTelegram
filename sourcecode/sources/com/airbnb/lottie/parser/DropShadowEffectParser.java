package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* loaded from: classes.dex */
public class DropShadowEffectParser {
    private static final JsonReader.Options DROP_SHADOW_EFFECT_NAMES = JsonReader.Options.of("ef");
    private static final JsonReader.Options INNER_EFFECT_NAMES = JsonReader.Options.of("nm", "v");
    private AnimatableColorValue color;
    private AnimatableFloatValue direction;
    private AnimatableFloatValue distance;
    private AnimatableFloatValue opacity;
    private AnimatableFloatValue radius;

    DropShadowEffect parse(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableFloatValue animatableFloatValue3;
        AnimatableFloatValue animatableFloatValue4;
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(DROP_SHADOW_EFFECT_NAMES) == 0) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    maybeParseInnerEffect(jsonReader, lottieComposition);
                }
                jsonReader.endArray();
            } else {
                jsonReader.skipName();
                jsonReader.skipValue();
            }
        }
        AnimatableColorValue animatableColorValue = this.color;
        if (animatableColorValue == null || (animatableFloatValue = this.opacity) == null || (animatableFloatValue2 = this.direction) == null || (animatableFloatValue3 = this.distance) == null || (animatableFloatValue4 = this.radius) == null) {
            return null;
        }
        return new DropShadowEffect(animatableColorValue, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, animatableFloatValue4);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0052, code lost:
    
        if (r0.equals("Opacity") == false) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void maybeParseInnerEffect(com.airbnb.lottie.parser.moshi.JsonReader r6, com.airbnb.lottie.LottieComposition r7) throws java.io.IOException {
        /*
            r5 = this;
            r6.beginObject()
            java.lang.String r0 = ""
        L5:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L90
            com.airbnb.lottie.parser.moshi.JsonReader$Options r1 = com.airbnb.lottie.parser.DropShadowEffectParser.INNER_EFFECT_NAMES
            int r1 = r6.selectName(r1)
            if (r1 == 0) goto L8a
            r2 = 1
            if (r1 == r2) goto L1d
            r6.skipName()
            r6.skipValue()
            goto L5
        L1d:
            r0.hashCode()
            r1 = -1
            int r3 = r0.hashCode()
            r4 = 0
            switch(r3) {
                case 353103893: goto L55;
                case 397447147: goto L4c;
                case 1041377119: goto L41;
                case 1379387491: goto L36;
                case 1383710113: goto L2b;
                default: goto L29;
            }
        L29:
            r2 = -1
            goto L5f
        L2b:
            java.lang.String r2 = "Softness"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L34
            goto L29
        L34:
            r2 = 4
            goto L5f
        L36:
            java.lang.String r2 = "Shadow Color"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L3f
            goto L29
        L3f:
            r2 = 3
            goto L5f
        L41:
            java.lang.String r2 = "Direction"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L4a
            goto L29
        L4a:
            r2 = 2
            goto L5f
        L4c:
            java.lang.String r3 = "Opacity"
            boolean r3 = r0.equals(r3)
            if (r3 != 0) goto L5f
            goto L29
        L55:
            java.lang.String r2 = "Distance"
            boolean r2 = r0.equals(r2)
            if (r2 != 0) goto L5e
            goto L29
        L5e:
            r2 = 0
        L5f:
            switch(r2) {
                case 0: goto L82;
                case 1: goto L7b;
                case 2: goto L74;
                case 3: goto L6d;
                case 4: goto L66;
                default: goto L62;
            }
        L62:
            r6.skipValue()
            goto L5
        L66:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r6, r7)
            r5.radius = r1
            goto L5
        L6d:
            com.airbnb.lottie.model.animatable.AnimatableColorValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseColor(r6, r7)
            r5.color = r1
            goto L5
        L74:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r6, r7, r4)
            r5.direction = r1
            goto L5
        L7b:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r6, r7, r4)
            r5.opacity = r1
            goto L5
        L82:
            com.airbnb.lottie.model.animatable.AnimatableFloatValue r1 = com.airbnb.lottie.parser.AnimatableValueParser.parseFloat(r6, r7)
            r5.distance = r1
            goto L5
        L8a:
            java.lang.String r0 = r6.nextString()
            goto L5
        L90:
            r6.endObject()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.DropShadowEffectParser.maybeParseInnerEffect(com.airbnb.lottie.parser.moshi.JsonReader, com.airbnb.lottie.LottieComposition):void");
    }
}
