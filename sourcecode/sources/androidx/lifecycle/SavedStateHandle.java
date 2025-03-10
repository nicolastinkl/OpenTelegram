package androidx.lifecycle;

import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.core.os.BundleKt;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: SavedStateHandle.kt */
/* loaded from: classes.dex */
public final class SavedStateHandle {
    private static final Class<? extends Object>[] ACCEPTABLE_CLASSES;
    public static final Companion Companion = new Companion(null);
    private final Map<String, Object> regular = new LinkedHashMap();
    private final Map<String, SavedStateRegistry.SavedStateProvider> savedStateProviders = new LinkedHashMap();
    private final Map<String, Object> liveDatas = new LinkedHashMap();
    private final Map<String, MutableStateFlow<Object>> flows = new LinkedHashMap();
    private final SavedStateRegistry.SavedStateProvider savedStateProvider = new SavedStateRegistry.SavedStateProvider() { // from class: androidx.lifecycle.SavedStateHandle$$ExternalSyntheticLambda0
        @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
        public final Bundle saveState() {
            Bundle m7savedStateProvider$lambda0;
            m7savedStateProvider$lambda0 = SavedStateHandle.m7savedStateProvider$lambda0(SavedStateHandle.this);
            return m7savedStateProvider$lambda0;
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: savedStateProvider$lambda-0, reason: not valid java name */
    public static final Bundle m7savedStateProvider$lambda0(SavedStateHandle this$0) {
        Map map;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        map = MapsKt__MapsKt.toMap(this$0.savedStateProviders);
        for (Map.Entry entry : map.entrySet()) {
            this$0.set((String) entry.getKey(), ((SavedStateRegistry.SavedStateProvider) entry.getValue()).saveState());
        }
        Set<String> keySet = this$0.regular.keySet();
        ArrayList arrayList = new ArrayList(keySet.size());
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        for (String str : keySet) {
            arrayList.add(str);
            arrayList2.add(this$0.regular.get(str));
        }
        return BundleKt.bundleOf(TuplesKt.to("keys", arrayList), TuplesKt.to("values", arrayList2));
    }

    public final SavedStateRegistry.SavedStateProvider savedStateProvider() {
        return this.savedStateProvider;
    }

    public final <T> void set(String key, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (!Companion.validateValue(t)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can't put value with type ");
            Intrinsics.checkNotNull(t);
            sb.append(t.getClass());
            sb.append(" into saved state");
            throw new IllegalArgumentException(sb.toString());
        }
        Object obj = this.liveDatas.get(key);
        MutableLiveData mutableLiveData = obj instanceof MutableLiveData ? (MutableLiveData) obj : null;
        if (mutableLiveData != null) {
            mutableLiveData.setValue(t);
        } else {
            this.regular.put(key, t);
        }
        MutableStateFlow<Object> mutableStateFlow = this.flows.get(key);
        if (mutableStateFlow == null) {
            return;
        }
        mutableStateFlow.setValue(t);
    }

    /* compiled from: SavedStateHandle.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean validateValue(Object obj) {
            if (obj == null) {
                return true;
            }
            for (Class cls : SavedStateHandle.ACCEPTABLE_CLASSES) {
                Intrinsics.checkNotNull(cls);
                if (cls.isInstance(obj)) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        Class<? extends Object>[] clsArr = new Class[29];
        clsArr[0] = Boolean.TYPE;
        clsArr[1] = boolean[].class;
        clsArr[2] = Double.TYPE;
        clsArr[3] = double[].class;
        Class<SizeF> cls = Integer.TYPE;
        clsArr[4] = cls;
        clsArr[5] = int[].class;
        clsArr[6] = Long.TYPE;
        clsArr[7] = long[].class;
        clsArr[8] = String.class;
        clsArr[9] = String[].class;
        clsArr[10] = Binder.class;
        clsArr[11] = Bundle.class;
        clsArr[12] = Byte.TYPE;
        clsArr[13] = byte[].class;
        clsArr[14] = Character.TYPE;
        clsArr[15] = char[].class;
        clsArr[16] = CharSequence.class;
        clsArr[17] = CharSequence[].class;
        clsArr[18] = ArrayList.class;
        clsArr[19] = Float.TYPE;
        clsArr[20] = float[].class;
        clsArr[21] = Parcelable.class;
        clsArr[22] = Parcelable[].class;
        clsArr[23] = Serializable.class;
        clsArr[24] = Short.TYPE;
        clsArr[25] = short[].class;
        clsArr[26] = SparseArray.class;
        int i = Build.VERSION.SDK_INT;
        clsArr[27] = i >= 21 ? Size.class : cls;
        if (i >= 21) {
            cls = SizeF.class;
        }
        clsArr[28] = cls;
        ACCEPTABLE_CLASSES = clsArr;
    }
}
