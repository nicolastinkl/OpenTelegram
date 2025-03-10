package org.xbill.DNS;

import j$.time.Clock;
import j$.time.Duration;
import j$.time.Instant;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.utils.base64;
import org.xbill.DNS.utils.hexdump;

/* loaded from: classes4.dex */
public class TSIG {
    public static final Duration FUDGE;
    public static final Name HMAC_MD5;
    public static final Name HMAC_SHA1;
    public static final Name HMAC_SHA224;
    public static final Name HMAC_SHA256;
    public static final Name HMAC_SHA384;
    public static final Name HMAC_SHA512;

    @Generated
    private static final Logger log = LoggerFactory.getLogger((Class<?>) TSIG.class);
    private final Name alg;
    private final Clock clock;
    private final String macAlgorithm;
    private final SecretKey macKey;
    private final Name name;
    private final Mac sharedHmac;

    static {
        Name.fromConstantString("gss-tsig.");
        Name fromConstantString = Name.fromConstantString("HMAC-MD5.SIG-ALG.REG.INT.");
        HMAC_MD5 = fromConstantString;
        Name fromConstantString2 = Name.fromConstantString("hmac-sha1.");
        HMAC_SHA1 = fromConstantString2;
        Name fromConstantString3 = Name.fromConstantString("hmac-sha224.");
        HMAC_SHA224 = fromConstantString3;
        Name fromConstantString4 = Name.fromConstantString("hmac-sha256.");
        HMAC_SHA256 = fromConstantString4;
        Name fromConstantString5 = Name.fromConstantString("hmac-sha384.");
        HMAC_SHA384 = fromConstantString5;
        Name fromConstantString6 = Name.fromConstantString("hmac-sha512.");
        HMAC_SHA512 = fromConstantString6;
        HashMap hashMap = new HashMap();
        hashMap.put(fromConstantString, "HmacMD5");
        hashMap.put(fromConstantString2, "HmacSHA1");
        hashMap.put(fromConstantString3, "HmacSHA224");
        hashMap.put(fromConstantString4, "HmacSHA256");
        hashMap.put(fromConstantString5, "HmacSHA384");
        hashMap.put(fromConstantString6, "HmacSHA512");
        Collections.unmodifiableMap(hashMap);
        FUDGE = Duration.ofSeconds(300L);
    }

    private static boolean verify(byte[] bArr, byte[] bArr2) {
        if (bArr2.length < bArr.length) {
            int length = bArr2.length;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArr, 0, bArr3, 0, length);
            bArr = bArr3;
        }
        return Arrays.equals(bArr2, bArr);
    }

    private Mac initHmac() {
        Mac mac = this.sharedHmac;
        if (mac != null) {
            try {
                return (Mac) mac.clone();
            } catch (CloneNotSupportedException unused) {
                this.sharedHmac.reset();
                return this.sharedHmac;
            }
        }
        try {
            Mac mac2 = Mac.getInstance(this.macAlgorithm);
            mac2.init(this.macKey);
            return mac2;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException("Caught security exception setting up HMAC.", e);
        }
    }

    public TSIGRecord generate(Message message, byte[] bArr, int i, TSIGRecord tSIGRecord) {
        return generate(message, bArr, i, tSIGRecord, true);
    }

    public TSIGRecord generate(Message message, byte[] bArr, int i, TSIGRecord tSIGRecord, boolean z) {
        Instant instant;
        boolean z2;
        Mac initHmac;
        Duration duration;
        byte[] bArr2;
        byte[] bArr3;
        if (i == 18) {
            instant = tSIGRecord.getTimeSigned();
        } else {
            instant = this.clock.instant();
        }
        Instant instant2 = instant;
        if (i == 0 || i == 18 || i == 22) {
            z2 = true;
            initHmac = initHmac();
        } else {
            initHmac = null;
            z2 = false;
        }
        int intValue = Options.intValue("tsigfudge");
        if (intValue < 0 || intValue > 32767) {
            duration = FUDGE;
        } else {
            duration = Duration.ofSeconds(intValue);
        }
        if (tSIGRecord != null && z2) {
            hmacAddSignature(initHmac, tSIGRecord);
        }
        if (z2) {
            Logger logger = log;
            if (logger.isTraceEnabled()) {
                logger.trace(hexdump.dump("TSIG-HMAC rendered message", bArr));
            }
            initHmac.update(bArr);
        }
        DNSOutput dNSOutput = new DNSOutput();
        if (z) {
            this.name.toWireCanonical(dNSOutput);
            dNSOutput.writeU16(255);
            dNSOutput.writeU32(0L);
            this.alg.toWireCanonical(dNSOutput);
        }
        writeTsigTimersVariables(instant2, duration, dNSOutput);
        if (z) {
            dNSOutput.writeU16(i);
            dNSOutput.writeU16(0);
        }
        if (z2) {
            byte[] byteArray = dNSOutput.toByteArray();
            Logger logger2 = log;
            if (logger2.isTraceEnabled()) {
                logger2.trace(hexdump.dump("TSIG-HMAC variables", byteArray));
            }
            bArr2 = initHmac.doFinal(byteArray);
        } else {
            bArr2 = new byte[0];
        }
        byte[] bArr4 = bArr2;
        if (i == 18) {
            DNSOutput dNSOutput2 = new DNSOutput(6);
            writeTsigTime(this.clock.instant(), dNSOutput2);
            bArr3 = dNSOutput2.toByteArray();
        } else {
            bArr3 = null;
        }
        return new TSIGRecord(this.name, 255, 0L, this.alg, instant2, duration, bArr4, message.getHeader().getID(), i, bArr3);
    }

    public void apply(Message message, TSIGRecord tSIGRecord) {
        apply(message, 0, tSIGRecord, true);
    }

    public void apply(Message message, int i, TSIGRecord tSIGRecord, boolean z) {
        message.addRecord(generate(message, message.toWire(), i, tSIGRecord, z), 3);
        message.tsigState = 3;
    }

    public int verify(Message message, byte[] bArr, TSIGRecord tSIGRecord) {
        return verify(message, bArr, tSIGRecord, true);
    }

    public int verify(Message message, byte[] bArr, TSIGRecord tSIGRecord, boolean z) {
        message.tsigState = 4;
        TSIGRecord tsig = message.getTSIG();
        if (tsig == null) {
            return 1;
        }
        if (!tsig.getName().equals(this.name) || !tsig.getAlgorithm().equals(this.alg)) {
            log.debug("BADKEY failure, expected: {}/{}, actual: {}/{}", this.name, this.alg, tsig.getName(), tsig.getAlgorithm());
            return 17;
        }
        Mac initHmac = initHmac();
        if (tSIGRecord != null && tsig.getError() != 17 && tsig.getError() != 16) {
            hmacAddSignature(initHmac, tSIGRecord);
        }
        message.getHeader().decCount(3);
        byte[] wire = message.getHeader().toWire();
        message.getHeader().incCount(3);
        Logger logger = log;
        if (logger.isTraceEnabled()) {
            logger.trace(hexdump.dump("TSIG-HMAC header", wire));
        }
        initHmac.update(wire);
        int length = message.tsigstart - wire.length;
        if (logger.isTraceEnabled()) {
            logger.trace(hexdump.dump("TSIG-HMAC message after header", bArr, wire.length, length));
        }
        initHmac.update(bArr, wire.length, length);
        DNSOutput dNSOutput = new DNSOutput();
        if (z) {
            tsig.getName().toWireCanonical(dNSOutput);
            dNSOutput.writeU16(tsig.dclass);
            dNSOutput.writeU32(tsig.ttl);
            tsig.getAlgorithm().toWireCanonical(dNSOutput);
        }
        writeTsigTimersVariables(tsig.getTimeSigned(), tsig.getFudge(), dNSOutput);
        if (z) {
            dNSOutput.writeU16(tsig.getError());
            if (tsig.getOther() != null) {
                dNSOutput.writeU16(tsig.getOther().length);
                dNSOutput.writeByteArray(tsig.getOther());
            } else {
                dNSOutput.writeU16(0);
            }
        }
        byte[] byteArray = dNSOutput.toByteArray();
        if (logger.isTraceEnabled()) {
            logger.trace(hexdump.dump("TSIG-HMAC variables", byteArray));
        }
        initHmac.update(byteArray);
        byte[] signature = tsig.getSignature();
        int macLength = initHmac.getMacLength();
        int max = Math.max(10, macLength / 2);
        if (signature.length > macLength) {
            logger.debug("BADSIG: signature too long, expected: {}, actual: {}", Integer.valueOf(macLength), Integer.valueOf(signature.length));
            return 16;
        }
        if (signature.length < max) {
            logger.debug("BADSIG: signature too short, expected: {} of {}, actual: {}", Integer.valueOf(max), Integer.valueOf(macLength), Integer.valueOf(signature.length));
            return 16;
        }
        byte[] doFinal = initHmac.doFinal();
        if (!verify(doFinal, signature)) {
            if (logger.isDebugEnabled()) {
                logger.debug("BADSIG: signature verification failed, expected: {}, actual: {}", base64.toString(doFinal), base64.toString(signature));
            }
            return 16;
        }
        Instant instant = this.clock.instant();
        if (Duration.between(instant, tsig.getTimeSigned()).abs().compareTo(tsig.getFudge()) > 0) {
            logger.debug("BADTIME failure, now {} +/- tsig {} > fudge {}", instant, tsig.getTimeSigned(), tsig.getFudge());
            return 18;
        }
        message.tsigState = 1;
        return 0;
    }

    public int recordLength() {
        return this.name.length() + 10 + this.alg.length() + 8 + 18 + 4 + 8;
    }

    private static void hmacAddSignature(Mac mac, TSIGRecord tSIGRecord) {
        byte[] u16 = DNSOutput.toU16(tSIGRecord.getSignature().length);
        Logger logger = log;
        if (logger.isTraceEnabled()) {
            logger.trace(hexdump.dump("TSIG-HMAC signature size", u16));
            logger.trace(hexdump.dump("TSIG-HMAC signature", tSIGRecord.getSignature()));
        }
        mac.update(u16);
        mac.update(tSIGRecord.getSignature());
    }

    private static void writeTsigTimersVariables(Instant instant, Duration duration, DNSOutput dNSOutput) {
        writeTsigTime(instant, dNSOutput);
        dNSOutput.writeU16((int) duration.getSeconds());
    }

    private static void writeTsigTime(Instant instant, DNSOutput dNSOutput) {
        long epochSecond = instant.getEpochSecond();
        dNSOutput.writeU16((int) (epochSecond >> 32));
        dNSOutput.writeU32(epochSecond & 4294967295L);
    }

    public static class StreamVerifier {
        private final TSIG key;
        private TSIGRecord lastTSIG;
        private int lastsigned;
        private int nresponses = 0;

        public StreamVerifier(TSIG tsig, TSIGRecord tSIGRecord) {
            this.key = tsig;
            this.lastTSIG = tSIGRecord;
        }

        public int verify(Message message, byte[] bArr) {
            TSIGRecord tsig = message.getTSIG();
            int i = this.nresponses + 1;
            this.nresponses = i;
            if (i == 1) {
                int verify = this.key.verify(message, bArr, this.lastTSIG);
                this.lastTSIG = tsig;
                return verify;
            }
            if (tsig != null) {
                int verify2 = this.key.verify(message, bArr, this.lastTSIG, false);
                this.lastsigned = this.nresponses;
                this.lastTSIG = tsig;
                return verify2;
            }
            if (i - this.lastsigned >= 100) {
                TSIG.log.debug("FORMERR: missing required signature on {}th message", Integer.valueOf(this.nresponses));
                message.tsigState = 4;
                return 1;
            }
            TSIG.log.trace("Intermediate message {} without signature", Integer.valueOf(this.nresponses));
            message.tsigState = 2;
            return 0;
        }
    }
}
