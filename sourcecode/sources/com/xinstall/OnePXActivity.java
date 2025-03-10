package com.xinstall;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.shubao.xinstall.a.a.a.b;
import com.shubao.xinstall.a.f.o;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class OnePXActivity extends Activity {

    public static class DemoRenderer implements GLSurfaceView.Renderer {
        private final OnePXActivity onePXActivity;
        private final StringBuilder sb = new StringBuilder();

        public DemoRenderer(OnePXActivity onePXActivity) {
            this.onePXActivity = onePXActivity;
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 gl10) {
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            o.a(gl10.glGetString(7937));
            b.b.offer(gl10.glGetString(7937));
            this.onePXActivity.finish();
        }
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Window window = getWindow();
            window.setGravity(51);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.x = 0;
            attributes.y = 0;
            attributes.height = 1;
            attributes.width = 1;
            window.setAttributes(attributes);
            LinearLayout linearLayout = new LinearLayout(this);
            GLSurfaceView gLSurfaceView = new GLSurfaceView(this);
            gLSurfaceView.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
            gLSurfaceView.setRenderer(new DemoRenderer(this));
            linearLayout.addView(gLSurfaceView);
            setContentView(linearLayout);
        } catch (Exception unused) {
        }
    }
}
