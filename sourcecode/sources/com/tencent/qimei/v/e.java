package com.tencent.qimei.v;

/* compiled from: StrategyController.java */
/* loaded from: classes.dex */
public class e implements com.tencent.qimei.g.c {
    public final /* synthetic */ f a;

    public e(f fVar) {
        this.a = fVar;
    }

    @Override // com.tencent.qimei.g.c
    public void a() {
        j jVar;
        synchronized (this.a) {
            if (!this.a.a()) {
                jVar = this.a.d;
                if (!jVar.b.get()) {
                    this.a.b();
                }
            }
        }
    }

    @Override // com.tencent.qimei.g.c
    public void b() {
    }
}
