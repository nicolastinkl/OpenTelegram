package org.xbill.DNS;

import j$.lang.Iterable$EL;
import j$.util.Collection$EL;
import j$.util.function.Consumer;
import j$.util.function.Function;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xbill.DNS.utils.base16;
import org.xbill.DNS.utils.base64;

/* loaded from: classes4.dex */
public class HIPRecord extends Record {
    private byte[] hit;
    private int pkAlgorithm;
    private byte[] publicKey;
    private List<Name> rvServers = new ArrayList();

    HIPRecord() {
    }

    @Override // org.xbill.DNS.Record
    protected String rrToString() {
        StringBuilder sb = new StringBuilder();
        if (Options.check("multiline")) {
            sb.append("( ");
        }
        String str = Options.check("multiline") ? "\n\t" : " ";
        sb.append(this.pkAlgorithm);
        sb.append(" ");
        sb.append(base16.toString(this.hit));
        sb.append(str);
        sb.append(base64.toString(this.publicKey));
        if (!this.rvServers.isEmpty()) {
            sb.append(str);
        }
        sb.append((String) Collection$EL.stream(this.rvServers).map(new Function() { // from class: org.xbill.DNS.HIPRecord$$ExternalSyntheticLambda1
            @Override // j$.util.function.Function
            public /* synthetic */ Function andThen(Function function) {
                return Function.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.Function
            public final Object apply(Object obj) {
                return ((Name) obj).toString();
            }

            @Override // j$.util.function.Function
            public /* synthetic */ Function compose(Function function) {
                return Function.CC.$default$compose(this, function);
            }
        }).collect(Collectors.joining(str)));
        if (Options.check("multiline")) {
            sb.append(" )");
        }
        return sb.toString();
    }

    @Override // org.xbill.DNS.Record
    protected void rrToWire(final DNSOutput dNSOutput, Compression compression, final boolean z) {
        dNSOutput.writeU8(this.hit.length);
        dNSOutput.writeU8(this.pkAlgorithm);
        dNSOutput.writeU16(this.publicKey.length);
        dNSOutput.writeByteArray(this.hit);
        dNSOutput.writeByteArray(this.publicKey);
        Iterable$EL.forEach(this.rvServers, new Consumer() { // from class: org.xbill.DNS.HIPRecord$$ExternalSyntheticLambda0
            @Override // j$.util.function.Consumer
            public final void accept(Object obj) {
                ((Name) obj).toWire(DNSOutput.this, null, z);
            }

            @Override // j$.util.function.Consumer
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.CC.$default$andThen(this, consumer);
            }
        });
    }

    @Override // org.xbill.DNS.Record
    protected void rrFromWire(DNSInput dNSInput) throws IOException {
        int readU8 = dNSInput.readU8();
        this.pkAlgorithm = dNSInput.readU8();
        int readU16 = dNSInput.readU16();
        this.hit = dNSInput.readByteArray(readU8);
        this.publicKey = dNSInput.readByteArray(readU16);
        while (dNSInput.remaining() > 0) {
            this.rvServers.add(new Name(dNSInput));
        }
    }
}
