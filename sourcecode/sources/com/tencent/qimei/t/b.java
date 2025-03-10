package com.tencent.qimei.t;

import com.tencent.qimei.shellapi.IDependency;
import java.util.HashMap;

/* compiled from: DependencyManager.java */
/* loaded from: classes.dex */
public class b {
    public static final HashMap<String, IDependency> a = new HashMap<>();

    /* compiled from: DependencyManager.java */
    private static final class a {
        public static final b a = new b(null);
    }

    public /* synthetic */ b(com.tencent.qimei.t.a aVar) {
    }

    public static b a() {
        return a.a;
    }

    public void a(String str, IDependency iDependency) {
        a.put(str, iDependency);
    }

    public IDependency a(String str) {
        return a.get(str);
    }
}
