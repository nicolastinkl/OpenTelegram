package q.rorbin.badgeview;

import android.content.Context;

/* loaded from: classes4.dex */
public class DisplayUtil {
    public static int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
