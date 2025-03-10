package okhttp3;

import java.io.IOException;

/* compiled from: Call.kt */
/* loaded from: classes3.dex */
public interface Call extends Cloneable {
    void cancel();

    void enqueue(Callback callback);

    Response execute() throws IOException;
}
