package com.google.firebase.components;

import com.google.firebase.inject.Provider;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class AbstractComponentContainer implements ComponentContainer {
    AbstractComponentContainer() {
    }

    @Override // com.google.firebase.components.ComponentContainer
    public <T> T get(Class<T> cls) {
        Provider<T> provider = getProvider(cls);
        if (provider == null) {
            return null;
        }
        return provider.get();
    }

    @Override // com.google.firebase.components.ComponentContainer
    public <T> Set<T> setOf(Class<T> cls) {
        return setOfProvider(cls).get();
    }
}
