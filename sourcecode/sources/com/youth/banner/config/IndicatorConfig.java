package com.youth.banner.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class IndicatorConfig {
    private int currentPosition;
    private int indicatorSize;
    private Margins margins;
    private int gravity = 1;
    private int indicatorSpace = BannerConfig.INDICATOR_SPACE;
    private int normalWidth = BannerConfig.INDICATOR_NORMAL_WIDTH;
    private int selectedWidth = BannerConfig.INDICATOR_SELECTED_WIDTH;
    private int normalColor = BannerConfig.INDICATOR_NORMAL_COLOR;
    private int selectedColor = BannerConfig.INDICATOR_SELECTED_COLOR;
    private int radius = BannerConfig.INDICATOR_RADIUS;
    private int height = BannerConfig.INDICATOR_HEIGHT;
    private boolean attachToBanner = true;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int CENTER = 1;
        public static final int LEFT = 0;
        public static final int RIGHT = 2;
    }

    public static class Margins {
        public int bottomMargin;
        public int leftMargin;
        public int rightMargin;
        public int topMargin;

        public Margins() {
            this(BannerConfig.INDICATOR_MARGIN);
        }

        public Margins(int i) {
            this(i, i, i, i);
        }

        public Margins(int i, int i2, int i3, int i4) {
            this.leftMargin = i;
            this.topMargin = i2;
            this.rightMargin = i3;
            this.bottomMargin = i4;
        }
    }

    public Margins getMargins() {
        if (this.margins == null) {
            setMargins(new Margins());
        }
        return this.margins;
    }

    public IndicatorConfig setMargins(Margins margins) {
        this.margins = margins;
        return this;
    }

    public int getIndicatorSize() {
        return this.indicatorSize;
    }

    public IndicatorConfig setIndicatorSize(int i) {
        this.indicatorSize = i;
        return this;
    }

    public int getNormalColor() {
        return this.normalColor;
    }

    public IndicatorConfig setNormalColor(int i) {
        this.normalColor = i;
        return this;
    }

    public int getSelectedColor() {
        return this.selectedColor;
    }

    public IndicatorConfig setSelectedColor(int i) {
        this.selectedColor = i;
        return this;
    }

    public int getIndicatorSpace() {
        return this.indicatorSpace;
    }

    public IndicatorConfig setIndicatorSpace(int i) {
        this.indicatorSpace = i;
        return this;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public IndicatorConfig setCurrentPosition(int i) {
        this.currentPosition = i;
        return this;
    }

    public int getNormalWidth() {
        return this.normalWidth;
    }

    public IndicatorConfig setNormalWidth(int i) {
        this.normalWidth = i;
        return this;
    }

    public int getSelectedWidth() {
        return this.selectedWidth;
    }

    public IndicatorConfig setSelectedWidth(int i) {
        this.selectedWidth = i;
        return this;
    }

    public int getGravity() {
        return this.gravity;
    }

    public IndicatorConfig setGravity(int i) {
        this.gravity = i;
        return this;
    }

    public boolean isAttachToBanner() {
        return this.attachToBanner;
    }

    public IndicatorConfig setAttachToBanner(boolean z) {
        this.attachToBanner = z;
        return this;
    }

    public int getRadius() {
        return this.radius;
    }

    public IndicatorConfig setRadius(int i) {
        this.radius = i;
        return this;
    }

    public int getHeight() {
        return this.height;
    }

    public IndicatorConfig setHeight(int i) {
        this.height = i;
        return this;
    }
}
