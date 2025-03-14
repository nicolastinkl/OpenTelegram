package okio;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* compiled from: Sink.kt */
/* loaded from: classes3.dex */
public interface Sink extends Closeable, Flushable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close() throws IOException;

    void flush() throws IOException;

    Timeout timeout();

    void write(Buffer buffer, long j) throws IOException;
}
