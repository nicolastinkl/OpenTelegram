package com.tencent.qimei.x;

import com.tencent.qimei.strategy.terminal.ITerminalStrategy;

/* compiled from: DefaultTerminalStrategy.java */
/* loaded from: classes.dex */
public class a implements ITerminalStrategy {
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public boolean d = true;
    public boolean e = true;
    public boolean f = true;
    public boolean g = true;
    public boolean h = false;
    public boolean i = true;
    public String j = "";
    public String k = "";
    public String l = "";
    public String m = "";
    public String n = "";
    public String o = "";
    public String p = "";

    /* renamed from: q, reason: collision with root package name */
    public String f22q = "";

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableAndroidId(boolean z) {
        this.d = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableAudit(boolean z) {
        this.h = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableBuildModel(boolean z) {
        this.i = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableCid(boolean z) {
        this.f = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableIMEI(boolean z) {
        this.b = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableIMSI(boolean z) {
        this.c = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableMAC(boolean z) {
        this.e = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableOAID(boolean z) {
        this.a = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy enableProcessInfo(boolean z) {
        this.g = z;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setAndroidId(String str) {
        this.n = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setBuildModel(String str) {
        this.f22q = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setCid(String str) {
        this.p = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setIMEI(String str) {
        this.l = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setIMSI(String str) {
        this.m = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setMAC(String str) {
        this.o = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setOAID(String str) {
        this.k = str;
        return this;
    }

    @Override // com.tencent.qimei.strategy.terminal.ITerminalStrategy
    public ITerminalStrategy setReportDomain(String str) {
        this.j = str;
        return this;
    }
}
