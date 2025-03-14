package org.telegram.messenger;

import android.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.telegram.messenger.Utilities;

/* loaded from: classes3.dex */
public abstract class Fetcher<Args, R> {
    private HashMap<Pair<Integer, Args>, R> cachedResults;
    private HashMap<Pair<Integer, Args>, Long> lastRequestedRemotely;
    private HashMap<Pair<Integer, Args>, ArrayList<Utilities.Callback<R>>> loadingCallbacks;
    private final long requestRemotelyTimeout = 240000;

    protected void getRemote(int i, Args args, long j, Utilities.Callback3<Boolean, R, Long> callback3) {
    }

    protected void setLocal(int i, Args args, R r, long j) {
    }

    protected boolean useCache(Args args) {
        return true;
    }

    protected void getLocal(int i, Args args, Utilities.Callback2<Long, R> callback2) {
        callback2.run(0L, null);
    }

    public void fetch(final int i, final Args args, Utilities.Callback<R> callback) {
        final Pair<Integer, Args> pair = new Pair<>(Integer.valueOf(i), args);
        if (isLoading(pair)) {
            saveCallback(pair, callback);
            return;
        }
        R cachedResult = getCachedResult(pair);
        if (cachedResult == null || shouldRequest(pair)) {
            saveCallback(pair, callback);
            getLocal(i, args, new Utilities.Callback2() { // from class: org.telegram.messenger.Fetcher$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    Fetcher.this.lambda$fetch$1(pair, i, args, (Long) obj, obj2);
                }
            });
        } else if (callback != null) {
            callback.run(cachedResult);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$fetch$1(final Pair pair, final int i, final Object obj, Long l, final Object obj2) {
        if (shouldRequest(pair)) {
            saveLastRequested(pair);
            getRemote(i, obj, l.longValue(), new Utilities.Callback3() { // from class: org.telegram.messenger.Fetcher$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback3
                public final void run(Object obj3, Object obj4, Object obj5) {
                    Fetcher.this.lambda$fetch$0(pair, obj2, i, obj, (Boolean) obj3, obj4, (Long) obj5);
                }
            });
        } else {
            cacheResult(pair, obj2);
            callCallbacks(pair, obj2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$fetch$0(Pair pair, Object obj, int i, Object obj2, Boolean bool, Object obj3, Long l) {
        if (bool.booleanValue()) {
            cacheResult(pair, obj);
            callCallbacks(pair, obj);
        } else {
            if (obj3 != 0) {
                setLocal(i, obj2, obj3, l.longValue());
                cacheResult(pair, obj3);
            }
            callCallbacks(pair, obj3);
        }
    }

    private R getCachedResult(Pair<Integer, Args> pair) {
        HashMap<Pair<Integer, Args>, R> hashMap = this.cachedResults;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(pair);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void cacheResult(Pair<Integer, Args> pair, R r) {
        if (useCache(pair.second)) {
            if (this.cachedResults == null) {
                this.cachedResults = new HashMap<>();
            }
            this.cachedResults.put(pair, r);
        }
    }

    private void saveLastRequested(Pair<Integer, Args> pair) {
        if (this.lastRequestedRemotely == null) {
            this.lastRequestedRemotely = new HashMap<>();
        }
        this.lastRequestedRemotely.put(pair, Long.valueOf(System.currentTimeMillis()));
    }

    private boolean shouldRequest(Pair<Integer, Args> pair) {
        HashMap<Pair<Integer, Args>, Long> hashMap = this.lastRequestedRemotely;
        Long l = hashMap != null ? hashMap.get(pair) : null;
        return l == null || System.currentTimeMillis() - l.longValue() >= 240000;
    }

    private boolean isLoading(Pair<Integer, Args> pair) {
        HashMap<Pair<Integer, Args>, ArrayList<Utilities.Callback<R>>> hashMap = this.loadingCallbacks;
        return (hashMap == null || hashMap.get(pair) == null) ? false : true;
    }

    private void saveCallback(Pair<Integer, Args> pair, Utilities.Callback<R> callback) {
        if (callback == null) {
            return;
        }
        if (this.loadingCallbacks == null) {
            this.loadingCallbacks = new HashMap<>();
        }
        ArrayList<Utilities.Callback<R>> arrayList = this.loadingCallbacks.get(pair);
        if (arrayList == null) {
            HashMap<Pair<Integer, Args>, ArrayList<Utilities.Callback<R>>> hashMap = this.loadingCallbacks;
            ArrayList<Utilities.Callback<R>> arrayList2 = new ArrayList<>();
            hashMap.put(pair, arrayList2);
            arrayList = arrayList2;
        }
        arrayList.add(callback);
    }

    private void callCallbacks(Pair<Integer, Args> pair, final R r) {
        final ArrayList<Utilities.Callback<R>> arrayList;
        HashMap<Pair<Integer, Args>, ArrayList<Utilities.Callback<R>>> hashMap = this.loadingCallbacks;
        if (hashMap == null || (arrayList = hashMap.get(pair)) == null) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.Fetcher$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Fetcher.lambda$callCallbacks$2(arrayList, r);
            }
        });
        this.loadingCallbacks.remove(pair);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$callCallbacks$2(ArrayList arrayList, Object obj) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((Utilities.Callback) it.next()).run(obj);
        }
        arrayList.clear();
    }
}
