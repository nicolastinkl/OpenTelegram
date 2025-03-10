package org.xbill.DNS;

import j$.lang.Iterable$EL;
import j$.util.Collection$EL;
import j$.util.function.Consumer;
import j$.util.function.Function;
import j$.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xbill.DNS.EDNSOption;

/* loaded from: classes4.dex */
public class DnssecAlgorithmOption extends EDNSOption {
    private List<Integer> algCodes;

    private DnssecAlgorithmOption(int i) {
        super(i);
        if (i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException("Invalid option code, must be one of DAU, DHU, N3U");
        }
        this.algCodes = new ArrayList();
    }

    public DnssecAlgorithmOption(int i, int... iArr) {
        this(i);
        if (iArr != null) {
            for (int i2 : iArr) {
                this.algCodes.add(Integer.valueOf(i2));
            }
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionFromWire(DNSInput dNSInput) throws IOException {
        this.algCodes.clear();
        while (dNSInput.remaining() > 0) {
            this.algCodes.add(Integer.valueOf(dNSInput.readU8()));
        }
    }

    @Override // org.xbill.DNS.EDNSOption
    void optionToWire(final DNSOutput dNSOutput) {
        List<Integer> list = this.algCodes;
        dNSOutput.getClass();
        Iterable$EL.forEach(list, new Consumer() { // from class: org.xbill.DNS.DnssecAlgorithmOption$$ExternalSyntheticLambda0
            @Override // j$.util.function.Consumer
            public final void accept(Object obj) {
                DNSOutput.this.writeU8(((Integer) obj).intValue());
            }

            @Override // j$.util.function.Consumer
            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer.CC.$default$andThen(this, consumer);
            }
        });
    }

    @Override // org.xbill.DNS.EDNSOption
    String optionToString() {
        return EDNSOption.Code.string(getCode()) + ": [" + ((String) Collection$EL.stream(this.algCodes).map(new Function() { // from class: org.xbill.DNS.DnssecAlgorithmOption$$ExternalSyntheticLambda1
            @Override // j$.util.function.Function
            public /* synthetic */ Function andThen(Function function) {
                return Function.CC.$default$andThen(this, function);
            }

            @Override // j$.util.function.Function
            public final Object apply(Object obj) {
                return DNSSEC$Algorithm.string(((Integer) obj).intValue());
            }

            @Override // j$.util.function.Function
            public /* synthetic */ Function compose(Function function) {
                return Function.CC.$default$compose(this, function);
            }
        }).collect(Collectors.joining(", "))) + "]";
    }
}
