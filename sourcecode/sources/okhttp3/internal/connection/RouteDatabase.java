package okhttp3.internal.connection;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Route;

/* compiled from: RouteDatabase.kt */
/* loaded from: classes3.dex */
public final class RouteDatabase {
    private final Set<Route> failedRoutes = new LinkedHashSet();

    public final synchronized void failed(Route failedRoute) {
        Intrinsics.checkNotNullParameter(failedRoute, "failedRoute");
        this.failedRoutes.add(failedRoute);
    }

    public final synchronized void connected(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        this.failedRoutes.remove(route);
    }

    public final synchronized boolean shouldPostpone(Route route) {
        Intrinsics.checkNotNullParameter(route, "route");
        return this.failedRoutes.contains(route);
    }
}
