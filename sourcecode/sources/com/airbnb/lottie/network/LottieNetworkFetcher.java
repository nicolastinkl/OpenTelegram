package com.airbnb.lottie.network;

import java.io.IOException;

/* loaded from: classes.dex */
public interface LottieNetworkFetcher {
    LottieFetchResult fetchSync(String str) throws IOException;
}
