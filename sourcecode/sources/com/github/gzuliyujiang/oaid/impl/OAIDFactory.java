package com.github.gzuliyujiang.oaid.impl;

import android.app.Application;
import android.content.Context;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDLog;
import com.github.gzuliyujiang.oaid.OAIDRom;

/* loaded from: classes.dex */
public final class OAIDFactory {
    private static IOAID ioaid;

    public static IOAID create(Context context) {
        if (context != null && !(context instanceof Application)) {
            context = context.getApplicationContext();
        }
        IOAID ioaid2 = ioaid;
        if (ioaid2 != null) {
            return ioaid2;
        }
        IOAID createManufacturerImpl = createManufacturerImpl(context);
        ioaid = createManufacturerImpl;
        if (createManufacturerImpl != null && createManufacturerImpl.supported()) {
            OAIDLog.print("Manufacturer interface has been found: " + ioaid.getClass().getName());
            return ioaid;
        }
        IOAID createUniversalImpl = createUniversalImpl(context);
        ioaid = createUniversalImpl;
        return createUniversalImpl;
    }

    private static IOAID createManufacturerImpl(Context context) {
        if (OAIDRom.isLenovo() || OAIDRom.isMotolora()) {
            return new LenovoImpl(context);
        }
        if (OAIDRom.isMeizu()) {
            return new MeizuImpl(context);
        }
        if (OAIDRom.isNubia()) {
            return new NubiaImpl(context);
        }
        if (OAIDRom.isXiaomi() || OAIDRom.isMiui() || OAIDRom.isBlackShark()) {
            return new XiaomiImpl(context);
        }
        if (OAIDRom.isSamsung()) {
            return new SamsungImpl(context);
        }
        if (OAIDRom.isVivo()) {
            return new VivoImpl(context);
        }
        if (OAIDRom.isASUS()) {
            return new AsusImpl(context);
        }
        if (OAIDRom.isHonor()) {
            HonorImpl honorImpl = new HonorImpl(context);
            if (honorImpl.supported()) {
                return honorImpl;
            }
        }
        if (OAIDRom.isHuawei() || OAIDRom.isEmui()) {
            return new HuaweiImpl(context);
        }
        if (OAIDRom.isOppo() || OAIDRom.isOnePlus()) {
            OppoImpl oppoImpl = new OppoImpl(context);
            return oppoImpl.supported() ? oppoImpl : new OppoExtImpl(context);
        }
        if (OAIDRom.isCoolpad(context)) {
            return new CoolpadImpl(context);
        }
        if (OAIDRom.isCoosea()) {
            return new CooseaImpl(context);
        }
        if (OAIDRom.isFreeme()) {
            return new FreemeImpl(context);
        }
        if (OAIDRom.is360OS()) {
            return new QikuImpl(context);
        }
        return null;
    }

    private static IOAID createUniversalImpl(Context context) {
        MsaImpl msaImpl = new MsaImpl(context);
        if (msaImpl.supported()) {
            OAIDLog.print("Mobile Security Alliance has been found: " + MsaImpl.class.getName());
            return msaImpl;
        }
        GmsImpl gmsImpl = new GmsImpl(context);
        if (gmsImpl.supported()) {
            OAIDLog.print("Google Play Service has been found: " + GmsImpl.class.getName());
            return gmsImpl;
        }
        DefaultImpl defaultImpl = new DefaultImpl();
        OAIDLog.print("OAID/AAID was not supported: " + DefaultImpl.class.getName());
        return defaultImpl;
    }
}
