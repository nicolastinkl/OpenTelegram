package org.xbill.DNS;

import j$.time.Duration;
import j$.util.function.Function;
import j$.wrappers.C$r8$wrapper$java$util$function$Function$WRP;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.messenger.LiteMode;
import org.xbill.DNS.Resolver;

/* loaded from: classes4.dex */
public class SimpleResolver implements Resolver {
    private InetSocketAddress address;
    private boolean ignoreTruncation;
    private InetSocketAddress localAddress;
    private OPTRecord queryOPT;
    private Duration timeoutValue;
    private TSIG tsig;
    private boolean useTCP;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) SimpleResolver.class);
    private static InetSocketAddress defaultResolver = new InetSocketAddress(InetAddress.getLoopbackAddress(), 53);

    @Override // org.xbill.DNS.Resolver
    public /* synthetic */ Message send(Message message) {
        return Resolver.CC.$default$send(this, message);
    }

    @Override // org.xbill.DNS.Resolver
    public /* synthetic */ void setTimeout(int i) {
        setTimeout(Duration.ofSeconds(i));
    }

    public SimpleResolver() throws UnknownHostException {
        this((String) null);
    }

    public SimpleResolver(String str) throws UnknownHostException {
        InetAddress byName;
        this.queryOPT = new OPTRecord(1280, 0, 0, 0);
        this.timeoutValue = Duration.ofSeconds(10L);
        if (str == null) {
            InetSocketAddress server = ResolverConfig.getCurrentConfig().server();
            this.address = server;
            if (server == null) {
                this.address = defaultResolver;
                return;
            }
            return;
        }
        if ("0".equals(str)) {
            byName = InetAddress.getLoopbackAddress();
        } else {
            byName = InetAddress.getByName(str);
        }
        this.address = new InetSocketAddress(byName, 53);
    }

    public SimpleResolver(InetSocketAddress inetSocketAddress) {
        this.queryOPT = new OPTRecord(1280, 0, 0, 0);
        this.timeoutValue = Duration.ofSeconds(10L);
        Objects.requireNonNull(inetSocketAddress, "host must not be null");
        this.address = inetSocketAddress;
    }

    @Override // org.xbill.DNS.Resolver
    public void setPort(int i) {
        this.address = new InetSocketAddress(this.address.getAddress(), i);
    }

    @Override // org.xbill.DNS.Resolver
    public void setTimeout(Duration duration) {
        this.timeoutValue = duration;
    }

    @Override // org.xbill.DNS.Resolver
    public Duration getTimeout() {
        return this.timeoutValue;
    }

    private Message parseMessage(byte[] bArr) throws WireParseException {
        try {
            return new Message(bArr);
        } catch (IOException e) {
            if (!(e instanceof WireParseException)) {
                throw new WireParseException("Error parsing message", e);
            }
            throw ((WireParseException) e);
        }
    }

    private void verifyTSIG(Message message, Message message2, byte[] bArr, TSIG tsig) {
        if (tsig == null) {
            return;
        }
        log.debug("TSIG verify: {}", Rcode.TSIGstring(tsig.verify(message2, bArr, message.getTSIG())));
    }

    private void applyEDNS(Message message) {
        if (this.queryOPT == null || message.getOPT() != null) {
            return;
        }
        message.addRecord(this.queryOPT, 3);
    }

    private int maxUDPSize(Message message) {
        OPTRecord opt = message.getOPT();
        return opt == null ? LiteMode.FLAG_CALLS_ANIMATIONS : opt.getPayloadSize();
    }

    @Override // org.xbill.DNS.Resolver
    public CompletionStage<Message> sendAsync(Message message) {
        return sendAsync(message, ForkJoinPool.commonPool());
    }

    @Override // org.xbill.DNS.Resolver
    public CompletionStage<Message> sendAsync(final Message message, Executor executor) {
        Record question;
        if (message.getHeader().getOpcode() == 0 && (question = message.getQuestion()) != null && question.getType() == 252) {
            final CompletableFuture completableFuture = new CompletableFuture();
            CompletableFuture.runAsync(new Runnable() { // from class: org.xbill.DNS.SimpleResolver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SimpleResolver.this.lambda$sendAsync$0(completableFuture, message);
                }
            }, executor);
            return completableFuture;
        }
        Message clone = message.clone();
        applyEDNS(clone);
        TSIG tsig = this.tsig;
        if (tsig != null) {
            clone.setTSIG(tsig, 0, null);
        }
        return sendAsync(clone, this.useTCP, executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAsync$0(CompletableFuture completableFuture, Message message) {
        try {
            completableFuture.complete(sendAXFR(message));
        } catch (IOException e) {
            completableFuture.completeExceptionally(e);
        }
    }

    CompletableFuture<Message> sendAsync(final Message message, boolean z, final Executor executor) {
        CompletableFuture<byte[]> sendrecv;
        final int id = message.getHeader().getID();
        byte[] wire = message.toWire(65535);
        int maxUDPSize = maxUDPSize(message);
        boolean z2 = z || wire.length > maxUDPSize;
        Logger logger = log;
        if (logger.isTraceEnabled()) {
            Object[] objArr = new Object[7];
            objArr[0] = message.getQuestion().getName();
            objArr[1] = Type.string(message.getQuestion().getType());
            objArr[2] = Integer.valueOf(id);
            objArr[3] = z2 ? "tcp" : "udp";
            objArr[4] = this.address.getAddress().getHostAddress();
            objArr[5] = Integer.valueOf(this.address.getPort());
            objArr[6] = message;
            logger.trace("Sending {}/{}, id={} to {}/{}:{}, query:\n{}", objArr);
        } else if (logger.isDebugEnabled()) {
            Object[] objArr2 = new Object[6];
            objArr2[0] = message.getQuestion().getName();
            objArr2[1] = Type.string(message.getQuestion().getType());
            objArr2[2] = Integer.valueOf(id);
            objArr2[3] = z2 ? "tcp" : "udp";
            objArr2[4] = this.address.getAddress().getHostAddress();
            objArr2[5] = Integer.valueOf(this.address.getPort());
            logger.debug("Sending {}/{}, id={} to {}/{}:{}", objArr2);
        }
        if (z2) {
            sendrecv = NioTcpClient.sendrecv(this.localAddress, this.address, message, wire, this.timeoutValue);
        } else {
            sendrecv = NioUdpClient.sendrecv(this.localAddress, this.address, wire, maxUDPSize, this.timeoutValue);
        }
        final boolean z3 = z2;
        return sendrecv.thenComposeAsync(C$r8$wrapper$java$util$function$Function$WRP.convert(new Function() { // from class: org.xbill.DNS.SimpleResolver$$ExternalSyntheticLambda1
            @Override // j$.util.function.Function
            public /* synthetic */ Function andThen(Function function) {
                return Function.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.Function
            public final Object apply(Object obj) {
                CompletionStage lambda$sendAsync$1;
                lambda$sendAsync$1 = SimpleResolver.this.lambda$sendAsync$1(id, message, z3, executor, (byte[]) obj);
                return lambda$sendAsync$1;
            }

            @Override // j$.util.function.Function
            public /* synthetic */ Function compose(Function function) {
                return Function.CC.$default$compose(this, function);
            }
        }), executor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CompletionStage lambda$sendAsync$1(int i, Message message, boolean z, Executor executor, byte[] bArr) {
        CompletableFuture completableFuture = new CompletableFuture();
        if (bArr.length < 12) {
            completableFuture.completeExceptionally(new WireParseException("invalid DNS header - too short"));
            return completableFuture;
        }
        int i2 = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
        if (i2 != i) {
            completableFuture.completeExceptionally(new WireParseException("invalid message id: expected " + i + "; got id " + i2));
            return completableFuture;
        }
        try {
            Message parseMessage = parseMessage(bArr);
            if (!message.getQuestion().getName().equals(parseMessage.getQuestion().getName())) {
                completableFuture.completeExceptionally(new WireParseException("invalid name in message: expected " + message.getQuestion().getName() + "; got " + parseMessage.getQuestion().getName()));
                return completableFuture;
            }
            if (message.getQuestion().getDClass() != parseMessage.getQuestion().getDClass()) {
                completableFuture.completeExceptionally(new WireParseException("invalid class in message: expected " + DClass.string(message.getQuestion().getDClass()) + "; got " + DClass.string(parseMessage.getQuestion().getDClass())));
                return completableFuture;
            }
            if (message.getQuestion().getType() != parseMessage.getQuestion().getType()) {
                completableFuture.completeExceptionally(new WireParseException("invalid type in message: expected " + Type.string(message.getQuestion().getType()) + "; got " + Type.string(parseMessage.getQuestion().getType())));
                return completableFuture;
            }
            verifyTSIG(message, parseMessage, bArr, this.tsig);
            if (!z && !this.ignoreTruncation && parseMessage.getHeader().getFlag(6)) {
                Logger logger = log;
                if (logger.isTraceEnabled()) {
                    logger.trace("Got truncated response for id {}, retrying via TCP, response:\n{}", Integer.valueOf(i), parseMessage);
                } else {
                    logger.debug("Got truncated response for id {}, retrying via TCP", Integer.valueOf(i));
                }
                return sendAsync(message, true, executor);
            }
            parseMessage.setResolver(this);
            completableFuture.complete(parseMessage);
            return completableFuture;
        } catch (WireParseException e) {
            completableFuture.completeExceptionally(e);
            return completableFuture;
        }
    }

    private Message sendAXFR(Message message) throws IOException {
        ZoneTransferIn newAXFR = ZoneTransferIn.newAXFR(message.getQuestion().getName(), this.address, this.tsig);
        newAXFR.setTimeout(this.timeoutValue);
        newAXFR.setLocalAddress(this.localAddress);
        try {
            newAXFR.run();
            List<Record> axfr = newAXFR.getAXFR();
            Message message2 = new Message(message.getHeader().getID());
            message2.getHeader().setFlag(5);
            message2.getHeader().setFlag(0);
            message2.addRecord(message.getQuestion(), 0);
            Iterator<Record> it = axfr.iterator();
            while (it.hasNext()) {
                message2.addRecord(it.next(), 1);
            }
            return message2;
        } catch (ZoneTransferException e) {
            throw new WireParseException(e.getMessage());
        }
    }

    public String toString() {
        return "SimpleResolver [" + this.address + "]";
    }
}
