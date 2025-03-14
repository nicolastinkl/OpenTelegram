package kotlin.jvm.internal;

import java.io.Serializable;

/* compiled from: Lambda.kt */
/* loaded from: classes.dex */
public abstract class Lambda<R> implements FunctionBase<R>, Serializable {
    public Lambda(int i) {
    }

    public String toString() {
        String renderLambdaToString = Reflection.renderLambdaToString(this);
        Intrinsics.checkNotNullExpressionValue(renderLambdaToString, "renderLambdaToString(this)");
        return renderLambdaToString;
    }
}
