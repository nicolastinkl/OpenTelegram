package q.rorbin.badgeview;

import android.graphics.PointF;
import java.util.List;

/* loaded from: classes4.dex */
public class MathUtil {
    public static double getTanRadian(double d, int i) {
        if (d < 0.0d) {
            d += 1.5707963267948966d;
        }
        return d + ((i - 1) * 1.5707963267948966d);
    }

    public static double radianToAngle(double d) {
        return (d / 6.283185307179586d) * 360.0d;
    }

    public static int getQuadrant(PointF pointF, PointF pointF2) {
        float f = pointF.x;
        float f2 = pointF2.x;
        if (f > f2) {
            float f3 = pointF.y;
            float f4 = pointF2.y;
            if (f3 > f4) {
                return 4;
            }
            return f3 < f4 ? 1 : -1;
        }
        if (f >= f2) {
            return -1;
        }
        float f5 = pointF.y;
        float f6 = pointF2.y;
        if (f5 > f6) {
            return 3;
        }
        return f5 < f6 ? 2 : -1;
    }

    public static float getPointDistance(PointF pointF, PointF pointF2) {
        return (float) Math.sqrt(Math.pow(pointF.x - pointF2.x, 2.0d) + Math.pow(pointF.y - pointF2.y, 2.0d));
    }

    public static void getInnertangentPoints(PointF pointF, float f, Double d, List<PointF> list) {
        float f2;
        if (d != null) {
            double atan = (float) Math.atan(d.doubleValue());
            double d2 = f;
            float cos2 = (float) (Math.cos(atan) * d2);
            f2 = (float) (Math.sin(atan) * d2);
            f = cos2;
        } else {
            f2 = 0.0f;
        }
        list.add(new PointF(pointF.x + f, pointF.y + f2));
        list.add(new PointF(pointF.x - f, pointF.y - f2));
    }
}
