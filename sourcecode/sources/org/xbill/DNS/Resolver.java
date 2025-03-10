package org.xbill.DNS;

import j$.time.Duration;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes4.dex */
public interface Resolver {
    Duration getTimeout();

    Message send(Message message) throws IOException;

    CompletionStage<Message> sendAsync(Message message);

    CompletionStage<Message> sendAsync(Message message, Executor executor);

    void setPort(int i);

    @Deprecated
    void setTimeout(int i);

    void setTimeout(Duration duration);

    /* renamed from: org.xbill.DNS.Resolver$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static Message $default$send(Resolver _this, Message message) throws IOException {
            try {
                return _this.sendAsync(message).toCompletableFuture().get(_this.getTimeout().toMillis(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException(e);
            } catch (ExecutionException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                }
                throw new IOException(e2.getCause());
            } catch (TimeoutException unused) {
                throw new IOException("Timed out while trying to resolve " + message.getQuestion().getName() + "/" + Type.string(message.getQuestion().type) + ", id=" + message.getHeader().getID());
            }
        }
    }
}
