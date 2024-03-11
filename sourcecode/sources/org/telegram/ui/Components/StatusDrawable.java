package org.telegram.ui.Components;

import android.graphics.drawable.Drawable;

/* loaded from: classes4.dex */
public abstract class StatusDrawable extends Drawable {
    public abstract void setColor(int i);

    public abstract void setIsChat(boolean z);

    public abstract void start();

    public abstract void stop();
}
