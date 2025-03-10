package org.webrtc;

import android.opengl.EGLContext;
import org.webrtc.EglBase;

/* loaded from: classes4.dex */
public interface EglBase14 extends EglBase {

    public interface Context extends EglBase.Context {
        EGLContext getRawContext();
    }
}
