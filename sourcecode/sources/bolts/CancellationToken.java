package bolts;

import java.util.Locale;

/* loaded from: classes.dex */
public class CancellationToken {
    private final CancellationTokenSource tokenSource;

    CancellationToken(CancellationTokenSource cancellationTokenSource) {
        this.tokenSource = cancellationTokenSource;
    }

    public boolean isCancellationRequested() {
        return this.tokenSource.isCancellationRequested();
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", CancellationToken.class.getName(), Integer.toHexString(hashCode()), Boolean.toString(this.tokenSource.isCancellationRequested()));
    }
}
