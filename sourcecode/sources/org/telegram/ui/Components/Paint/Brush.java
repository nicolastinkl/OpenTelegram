package org.telegram.ui.Components.Paint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Arrays;
import java.util.List;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;

/* loaded from: classes4.dex */
public abstract class Brush {
    public static List<Brush> BRUSHES_LIST = Arrays.asList(new Radial(), new Arrow(), new Elliptical(), new Neon(), new Blurer(), new Eraser());

    public float getAlpha() {
        return 0.85f;
    }

    public float getAngle() {
        return 0.0f;
    }

    public float getDefaultWeight() {
        return 0.25f;
    }

    public int getIconRes() {
        return 0;
    }

    public float getOverrideAlpha() {
        return 1.0f;
    }

    public float getPreviewScale() {
        return 0.4f;
    }

    public float getScale() {
        return 1.0f;
    }

    public String getShaderName(int i) {
        if (i == 0) {
            return "blitWithMask";
        }
        if (i == 1) {
            return "compositeWithMask";
        }
        if (i != 2) {
            return null;
        }
        return "brush";
    }

    public float getSmoothThicknessRate() {
        return 1.0f;
    }

    public float getSpacing() {
        return 0.15f;
    }

    public boolean isEraser() {
        return false;
    }

    public int getStampResId() {
        return R.drawable.paint_radial_brush;
    }

    public Bitmap getStamp() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        return BitmapFactory.decodeResource(ApplicationLoader.applicationContext.getResources(), getStampResId(), options);
    }

    public static class Radial extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_pen;
        }
    }

    public static class Elliptical extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAlpha() {
            return 0.3f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getDefaultWeight() {
            return 0.5f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getOverrideAlpha() {
            return 0.45f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getPreviewScale() {
            return 0.4f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getScale() {
            return 1.5f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getSpacing() {
            return 0.04f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAngle() {
            return (float) Math.toRadians(0.0d);
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getStampResId() {
            return R.drawable.paint_elliptical_brush;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_marker;
        }
    }

    public static class Neon extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAlpha() {
            return 0.7f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getDefaultWeight() {
            return 0.5f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getPreviewScale() {
            return 0.2f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getScale() {
            return 1.45f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public String getShaderName(int i) {
            if (i == 0) {
                return "blitWithMaskLight";
            }
            if (i == 1) {
                return "compositeWithMaskLight";
            }
            if (i != 2) {
                return null;
            }
            return "brushLight";
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getSpacing() {
            return 0.07f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getStampResId() {
            return R.drawable.paint_neon_brush;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_neon;
        }
    }

    public static class Arrow extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public float getDefaultWeight() {
            return 0.25f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getSmoothThicknessRate() {
            return 0.25f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_arrow;
        }
    }

    public static class Eraser extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAlpha() {
            return 1.0f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getDefaultWeight() {
            return 1.0f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getPreviewScale() {
            return 0.35f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public String getShaderName(int i) {
            if (i == 0) {
                return "blitWithMaskEraser";
            }
            if (i == 1) {
                return "compositeWithMaskEraser";
            }
            if (i != 2) {
                return null;
            }
            return "brush";
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public boolean isEraser() {
            return true;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_eraser;
        }
    }

    public static class Blurer extends Brush {
        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAlpha() {
            return 1.0f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getDefaultWeight() {
            return 1.0f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getPreviewScale() {
            return 0.35f;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public String getShaderName(int i) {
            if (i == 0) {
                return "blitWithMaskBlurer";
            }
            if (i == 1) {
                return "compositeWithMaskBlurer";
            }
            if (i != 2) {
                return null;
            }
            return "brush";
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public int getIconRes() {
            return R.raw.photo_blur;
        }
    }

    public static abstract class Shape extends Brush {
        public static List<Shape> SHAPES_LIST = Arrays.asList(new Circle(), new Rectangle(), new Star(), new Bubble(), new Arrow());

        @Override // org.telegram.ui.Components.Paint.Brush
        public float getAlpha() {
            return 1.0f;
        }

        public int getFilledIconRes() {
            return 0;
        }

        @Override // org.telegram.ui.Components.Paint.Brush
        public String getShaderName(int i) {
            if (i == 0 || i == 1) {
                return "shape";
            }
            if (i != 2) {
                return null;
            }
            return "brush";
        }

        public String getShapeName() {
            return null;
        }

        public int getShapeShaderType() {
            return 0;
        }

        public static Shape make(int i) {
            if (i < 0 || i > SHAPES_LIST.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Shape type must be in range from 0 to ");
                sb.append(SHAPES_LIST.size() - 1);
                sb.append(", but got ");
                sb.append(i);
                throw new IndexOutOfBoundsException(sb.toString());
            }
            return SHAPES_LIST.get(i);
        }

        public static class Circle extends Shape {
            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getShapeShaderType() {
                return 0;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public String getShapeName() {
                return LocaleController.getString(R.string.PaintCircle);
            }

            @Override // org.telegram.ui.Components.Paint.Brush
            public int getIconRes() {
                return R.drawable.photo_circle;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getFilledIconRes() {
                return R.drawable.photo_circle_fill;
            }
        }

        public static class Rectangle extends Shape {
            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getShapeShaderType() {
                return 1;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public String getShapeName() {
                return LocaleController.getString(R.string.PaintRectangle);
            }

            @Override // org.telegram.ui.Components.Paint.Brush
            public int getIconRes() {
                return R.drawable.photo_rectangle;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getFilledIconRes() {
                return R.drawable.photo_rectangle_fill;
            }
        }

        public static class Star extends Shape {
            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getShapeShaderType() {
                return 2;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public String getShapeName() {
                return LocaleController.getString(R.string.PaintStar);
            }

            @Override // org.telegram.ui.Components.Paint.Brush
            public int getIconRes() {
                return R.drawable.photo_star;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getFilledIconRes() {
                return R.drawable.photo_star_fill;
            }
        }

        public static class Bubble extends Shape {
            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getShapeShaderType() {
                return 3;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public String getShapeName() {
                return LocaleController.getString(R.string.PaintBubble);
            }

            @Override // org.telegram.ui.Components.Paint.Brush
            public int getIconRes() {
                return R.drawable.msg_msgbubble;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getFilledIconRes() {
                return R.drawable.msg_msgbubble2;
            }
        }

        public static class Arrow extends Shape {
            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getShapeShaderType() {
                return 4;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public String getShapeName() {
                return LocaleController.getString(R.string.PaintArrow);
            }

            @Override // org.telegram.ui.Components.Paint.Brush
            public int getIconRes() {
                return R.drawable.photo_arrowshape;
            }

            @Override // org.telegram.ui.Components.Paint.Brush.Shape
            public int getFilledIconRes() {
                return R.drawable.photo_arrowshape;
            }
        }
    }
}
