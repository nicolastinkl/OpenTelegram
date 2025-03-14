package org.telegram.messenger;

import android.view.Window;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class FlagSecureReason {
    private static HashMap<Window, Integer> currentSecureReasons;
    private FlagSecureCondition condition;
    private Window window;
    private boolean attached = false;
    private boolean value = false;

    public interface FlagSecureCondition {
        boolean run();
    }

    public FlagSecureReason(Window window, FlagSecureCondition flagSecureCondition) {
        this.window = window;
        this.condition = flagSecureCondition;
    }

    public void invalidate() {
        FlagSecureCondition flagSecureCondition;
        boolean z = this.attached && (flagSecureCondition = this.condition) != null && flagSecureCondition.run();
        if (z != this.value) {
            this.value = z;
            update(z ? 1 : -1);
        }
    }

    public void attach() {
        if (this.attached) {
            return;
        }
        this.attached = true;
        invalidate();
    }

    public void detach() {
        if (this.attached) {
            this.attached = false;
            invalidate();
        }
    }

    private void update(int i) {
        if (currentSecureReasons == null) {
            currentSecureReasons = new HashMap<>();
        }
        Integer num = currentSecureReasons.get(this.window);
        int max = Math.max(0, (num == null ? 0 : num.intValue()) + i);
        if (max <= 0) {
            currentSecureReasons.remove(this.window);
        } else {
            currentSecureReasons.put(this.window, Integer.valueOf(max));
        }
        updateWindowSecure(this.window);
    }

    private static void updateWindowSecure(Window window) {
        if (window == null) {
            return;
        }
        if (isSecuredNow(window)) {
            window.addFlags(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
        } else {
            window.clearFlags(LiteMode.FLAG_ANIMATED_EMOJI_REACTIONS_NOT_PREMIUM);
        }
    }

    public static boolean isSecuredNow(Window window) {
        HashMap<Window, Integer> hashMap = currentSecureReasons;
        return (hashMap == null || hashMap.get(window) == null) ? false : true;
    }
}
