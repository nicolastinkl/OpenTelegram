package org.xbill.DNS;

import j$.time.Duration;
import j$.util.Collection$EL;
import j$.util.Comparator;
import j$.util.concurrent.atomic.DesugarAtomicInteger;
import j$.util.function.BiFunction;
import j$.util.function.Function;
import j$.util.function.IntUnaryOperator;
import j$.util.function.ToIntFunction;
import j$.util.stream.Collectors;
import j$.wrappers.C$r8$wrapper$java$util$function$BiFunction$WRP;
import j$.wrappers.C$r8$wrapper$java$util$function$Function$WRP;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Resolver;

/* loaded from: classes4.dex */
public class ExtendedResolver implements Resolver {
    private final AtomicInteger lbStart;
    private boolean loadBalance;
    private final List<ResolverEntry> resolvers;
    private int retries;
    private Duration timeout;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) ExtendedResolver.class);
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    public static final Duration DEFAULT_RESOLVER_TIMEOUT = Duration.ofSeconds(5);

    @Override // org.xbill.DNS.Resolver
    public /* synthetic */ Message send(Message message) {
        return Resolver.CC.$default$send(this, message);
    }

    @Override // org.xbill.DNS.Resolver
    public /* synthetic */ void setTimeout(int i) {
        setTimeout(Duration.ofSeconds(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Resolution {
        private final int[] attempts;
        private int currentResolver;
        private final long endTime;
        private final Message query;
        private List<ResolverEntry> resolvers;
        private final int retriesPerResolver;

        Resolution(ExtendedResolver extendedResolver, Message message) {
            this.resolvers = new ArrayList(extendedResolver.resolvers);
            this.endTime = System.nanoTime() + extendedResolver.timeout.toNanos();
            if (extendedResolver.loadBalance) {
                int updateAndGet = DesugarAtomicInteger.updateAndGet(extendedResolver.lbStart, new IntUnaryOperator() { // from class: org.xbill.DNS.ExtendedResolver$Resolution$$ExternalSyntheticLambda2
                    @Override // j$.util.function.IntUnaryOperator
                    public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
                        return IntUnaryOperator.CC.$default$andThen(this, intUnaryOperator);
                    }

                    @Override // j$.util.function.IntUnaryOperator
                    public final int applyAsInt(int i) {
                        int lambda$new$0;
                        lambda$new$0 = ExtendedResolver.Resolution.this.lambda$new$0(i);
                        return lambda$new$0;
                    }

                    @Override // j$.util.function.IntUnaryOperator
                    public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
                        return IntUnaryOperator.CC.$default$compose(this, intUnaryOperator);
                    }
                });
                if (updateAndGet > 0) {
                    ArrayList arrayList = new ArrayList(this.resolvers.size());
                    for (int i = 0; i < this.resolvers.size(); i++) {
                        arrayList.add(this.resolvers.get((i + updateAndGet) % this.resolvers.size()));
                    }
                    this.resolvers = arrayList;
                }
            } else {
                this.resolvers = (List) Collection$EL.stream(this.resolvers).sorted(Comparator.CC.comparingInt(new ToIntFunction() { // from class: org.xbill.DNS.ExtendedResolver$Resolution$$ExternalSyntheticLambda4
                    @Override // j$.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        int lambda$new$1;
                        lambda$new$1 = ExtendedResolver.Resolution.lambda$new$1((ExtendedResolver.ResolverEntry) obj);
                        return lambda$new$1;
                    }
                })).collect(Collectors.toList());
            }
            this.attempts = new int[this.resolvers.size()];
            this.retriesPerResolver = extendedResolver.retries;
            this.query = message;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ int lambda$new$0(int i) {
            return (i + 1) % this.resolvers.size();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$new$1(ResolverEntry resolverEntry) {
            return resolverEntry.failures.get();
        }

        private CompletionStage<Message> send(Executor executor) {
            ResolverEntry resolverEntry = this.resolvers.get(this.currentResolver);
            ExtendedResolver.log.debug("Sending {}/{}, id={} to resolver {} ({}), attempt {} of {}", this.query.getQuestion().getName(), Type.string(this.query.getQuestion().getType()), Integer.valueOf(this.query.getHeader().getID()), Integer.valueOf(this.currentResolver), resolverEntry.resolver, Integer.valueOf(this.attempts[this.currentResolver] + 1), Integer.valueOf(this.retriesPerResolver));
            int[] iArr = this.attempts;
            int i = this.currentResolver;
            iArr[i] = iArr[i] + 1;
            return resolverEntry.resolver.sendAsync(this.query, executor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public CompletionStage<Message> startAsync(final Executor executor) {
            return send(executor).handle(C$r8$wrapper$java$util$function$BiFunction$WRP.convert(new BiFunction() { // from class: org.xbill.DNS.ExtendedResolver$Resolution$$ExternalSyntheticLambda0
                @Override // j$.util.function.BiFunction
                public /* synthetic */ BiFunction andThen(Function function) {
                    return BiFunction.CC.$default$andThen(this, function);
                }

                @Override // j$.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    CompletionStage lambda$startAsync$2;
                    lambda$startAsync$2 = ExtendedResolver.Resolution.this.lambda$startAsync$2(executor, (Message) obj, (Throwable) obj2);
                    return lambda$startAsync$2;
                }
            })).thenCompose(C$r8$wrapper$java$util$function$Function$WRP.convert(Function.CC.identity()));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: handle, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
        public CompletionStage<Message> lambda$startAsync$2(Message message, Throwable th, final Executor executor) {
            AtomicInteger atomicInteger = this.resolvers.get(this.currentResolver).failures;
            if (th != null) {
                ExtendedResolver.log.debug("Failed to resolve {}/{}, id={} with resolver {} ({}) on attempt {} of {}, reason={}", this.query.getQuestion().getName(), Type.string(this.query.getQuestion().getType()), Integer.valueOf(this.query.getHeader().getID()), Integer.valueOf(this.currentResolver), this.resolvers.get(this.currentResolver).resolver, Integer.valueOf(this.attempts[this.currentResolver]), Integer.valueOf(this.retriesPerResolver), th.getMessage());
                atomicInteger.incrementAndGet();
                if (this.endTime - System.nanoTime() < 0) {
                    CompletableFuture completableFuture = new CompletableFuture();
                    completableFuture.completeExceptionally(new IOException("Timed out while trying to resolve " + this.query.getQuestion().getName() + "/" + Type.string(this.query.getQuestion().type) + ", id=" + this.query.getHeader().getID()));
                    return completableFuture;
                }
                int size = (this.currentResolver + 1) % this.resolvers.size();
                this.currentResolver = size;
                if (this.attempts[size] < this.retriesPerResolver) {
                    return send(executor).handle(C$r8$wrapper$java$util$function$BiFunction$WRP.convert(new BiFunction() { // from class: org.xbill.DNS.ExtendedResolver$Resolution$$ExternalSyntheticLambda1
                        @Override // j$.util.function.BiFunction
                        public /* synthetic */ BiFunction andThen(Function function) {
                            return BiFunction.CC.$default$andThen(this, function);
                        }

                        @Override // j$.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            CompletionStage lambda$handle$3;
                            lambda$handle$3 = ExtendedResolver.Resolution.this.lambda$handle$3(executor, (Message) obj, (Throwable) obj2);
                            return lambda$handle$3;
                        }
                    })).thenCompose(C$r8$wrapper$java$util$function$Function$WRP.convert(Function.CC.identity()));
                }
                CompletableFuture completableFuture2 = new CompletableFuture();
                completableFuture2.completeExceptionally(th);
                return completableFuture2;
            }
            DesugarAtomicInteger.updateAndGet(atomicInteger, new IntUnaryOperator() { // from class: org.xbill.DNS.ExtendedResolver$Resolution$$ExternalSyntheticLambda3
                @Override // j$.util.function.IntUnaryOperator
                public /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
                    return IntUnaryOperator.CC.$default$andThen(this, intUnaryOperator);
                }

                @Override // j$.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    int lambda$handle$4;
                    lambda$handle$4 = ExtendedResolver.Resolution.lambda$handle$4(i);
                    return lambda$handle$4;
                }

                @Override // j$.util.function.IntUnaryOperator
                public /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
                    return IntUnaryOperator.CC.$default$compose(this, intUnaryOperator);
                }
            });
            return CompletableFuture.completedFuture(message);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$handle$4(int i) {
            if (i > 0) {
                return (int) Math.log(i);
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ResolverEntry {
        private final AtomicInteger failures;
        private final Resolver resolver;

        @Generated
        public ResolverEntry(Resolver resolver, AtomicInteger atomicInteger) {
            this.resolver = resolver;
            this.failures = atomicInteger;
        }

        ResolverEntry(Resolver resolver) {
            this(resolver, new AtomicInteger(0));
        }

        public String toString() {
            return this.resolver.toString();
        }
    }

    public ExtendedResolver() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.resolvers = copyOnWriteArrayList;
        this.lbStart = new AtomicInteger();
        this.retries = 3;
        this.timeout = DEFAULT_TIMEOUT;
        copyOnWriteArrayList.addAll((Collection) Collection$EL.stream(ResolverConfig.getCurrentConfig().servers()).map(new Function() { // from class: org.xbill.DNS.ExtendedResolver$$ExternalSyntheticLambda0
            @Override // j$.util.function.Function
            public /* synthetic */ Function andThen(Function function) {
                return Function.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.Function
            public final Object apply(Object obj) {
                ExtendedResolver.ResolverEntry lambda$new$0;
                lambda$new$0 = ExtendedResolver.lambda$new$0((InetSocketAddress) obj);
                return lambda$new$0;
            }

            @Override // j$.util.function.Function
            public /* synthetic */ Function compose(Function function) {
                return Function.CC.$default$compose(this, function);
            }
        }).collect(Collectors.toList()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ ResolverEntry lambda$new$0(InetSocketAddress inetSocketAddress) {
        SimpleResolver simpleResolver = new SimpleResolver(inetSocketAddress);
        simpleResolver.setTimeout(DEFAULT_RESOLVER_TIMEOUT);
        return new ResolverEntry(simpleResolver);
    }

    @Override // org.xbill.DNS.Resolver
    public void setPort(int i) {
        Iterator<ResolverEntry> it = this.resolvers.iterator();
        while (it.hasNext()) {
            it.next().resolver.setPort(i);
        }
    }

    @Override // org.xbill.DNS.Resolver
    public Duration getTimeout() {
        return this.timeout;
    }

    @Override // org.xbill.DNS.Resolver
    public void setTimeout(Duration duration) {
        this.timeout = duration;
    }

    @Override // org.xbill.DNS.Resolver
    public CompletionStage<Message> sendAsync(Message message) {
        return sendAsync(message, ForkJoinPool.commonPool());
    }

    @Override // org.xbill.DNS.Resolver
    public CompletionStage<Message> sendAsync(Message message, Executor executor) {
        return new Resolution(this, message).startAsync(executor);
    }

    public String toString() {
        return "ExtendedResolver of " + this.resolvers;
    }
}
