package com.google.android.gms.signin;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;

/* compiled from: com.google.android.gms:play-services-base@@18.1.0 */
/* loaded from: classes.dex */
public final class zad {
    public static final Api.ClientKey zaa;
    public static final Api.ClientKey zab;
    public static final Api.AbstractClientBuilder zac;
    static final Api.AbstractClientBuilder zad;
    public static final Api zag;

    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zaa = clientKey;
        Api.ClientKey clientKey2 = new Api.ClientKey();
        zab = clientKey2;
        zaa zaaVar = new zaa();
        zac = zaaVar;
        zab zabVar = new zab();
        zad = zabVar;
        new Scope("profile");
        new Scope("email");
        zag = new Api("SignIn.API", zaaVar, clientKey);
        new Api("SignIn.INTERNAL_API", zabVar, clientKey2);
    }
}
