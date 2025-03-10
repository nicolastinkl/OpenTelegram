package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BubbleLayout;

/* loaded from: classes.dex */
public abstract class BubbleAttachPopupView extends BasePopupView {
    protected BubbleLayout bubbleContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    protected void addInnerContent() {
        this.bubbleContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bubbleContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_bubble_attach_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.bubbleContainer.getChildCount() == 0) {
            addInnerContent();
        }
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.atView == null && popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for BubbleAttachPopupView before show()！");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.bubbleContainer.setElevation(XPopupUtils.dp2px(getContext(), 10.0f));
        }
        this.bubbleContainer.setShadowRadius(XPopupUtils.dp2px(getContext(), 0.0f));
        PopupInfo popupInfo2 = this.popupInfo;
        this.defaultOffsetY = popupInfo2.offsetY;
        this.defaultOffsetX = popupInfo2.offsetX;
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                BubbleAttachPopupView.this.doAttach();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                BubbleAttachPopupView.this.doAttach();
            }
        });
    }

    public void doAttach() {
        int screenHeight;
        int i;
        float screenHeight2;
        int i2;
        if (this.popupInfo == null) {
            return;
        }
        this.maxY = XPopupUtils.getAppHeight(getContext()) - this.overflow;
        final boolean isLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.touchPoint != null) {
            PointF pointF = XPopup.longClickPoint;
            if (pointF != null) {
                popupInfo.touchPoint = pointF;
            }
            popupInfo.touchPoint.x -= getActivityContentLeft();
            float f = this.popupInfo.touchPoint.y;
            this.centerY = f;
            if (f + ((float) getPopupContentView().getMeasuredHeight()) > this.maxY) {
                this.isShowUp = this.popupInfo.touchPoint.y > ((float) XPopupUtils.getScreenHeight(getContext())) / 2.0f;
            } else {
                this.isShowUp = false;
            }
            this.isShowLeft = this.popupInfo.touchPoint.x > ((float) XPopupUtils.getAppWidth(getContext())) / 2.0f;
            ViewGroup.LayoutParams layoutParams = getPopupContentView().getLayoutParams();
            if (isShowUpToTarget()) {
                screenHeight2 = this.popupInfo.touchPoint.y - getStatusBarHeight();
                i2 = this.overflow;
            } else {
                screenHeight2 = XPopupUtils.getScreenHeight(getContext()) - this.popupInfo.touchPoint.y;
                i2 = this.overflow;
            }
            int i3 = (int) (screenHeight2 - i2);
            int appWidth = (int) ((this.isShowLeft ? this.popupInfo.touchPoint.x : XPopupUtils.getAppWidth(getContext()) - this.popupInfo.touchPoint.x) - this.overflow);
            if (getPopupContentView().getMeasuredHeight() > i3) {
                layoutParams.height = i3;
            }
            if (getPopupContentView().getMeasuredWidth() > appWidth) {
                layoutParams.width = appWidth;
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.3
                @Override // java.lang.Runnable
                public void run() {
                    BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                    PopupInfo popupInfo2 = bubbleAttachPopupView.popupInfo;
                    if (popupInfo2 == null) {
                        return;
                    }
                    if (popupInfo2.isCenterHorizontal) {
                        bubbleAttachPopupView.translationX = (popupInfo2.touchPoint.x + bubbleAttachPopupView.defaultOffsetX) - (bubbleAttachPopupView.getPopupContentView().getMeasuredWidth() / 2.0f);
                    } else if (isLayoutRtl) {
                        bubbleAttachPopupView.translationX = -(((XPopupUtils.getAppWidth(bubbleAttachPopupView.getContext()) - BubbleAttachPopupView.this.popupInfo.touchPoint.x) - r2.defaultOffsetX) - (r2.getPopupContentView().getMeasuredWidth() / 2.0f));
                    } else {
                        bubbleAttachPopupView.translationX = ((popupInfo2.touchPoint.x + bubbleAttachPopupView.defaultOffsetX) - bubbleAttachPopupView.getPopupContentView().getMeasuredWidth()) + BubbleAttachPopupView.this.bubbleContainer.getShadowRadius();
                    }
                    if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                        BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView2.translationY = (bubbleAttachPopupView2.popupInfo.touchPoint.y - bubbleAttachPopupView2.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                    } else {
                        BubbleAttachPopupView bubbleAttachPopupView3 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView3.translationY = bubbleAttachPopupView3.popupInfo.touchPoint.y + bubbleAttachPopupView3.defaultOffsetY;
                    }
                    BubbleAttachPopupView bubbleAttachPopupView4 = BubbleAttachPopupView.this;
                    if (bubbleAttachPopupView4.popupInfo.isCenterHorizontal) {
                        bubbleAttachPopupView4.bubbleContainer.setLookPositionCenter(true);
                    } else if (bubbleAttachPopupView4.isShowUpToTarget()) {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                    } else {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                    }
                    BubbleAttachPopupView bubbleAttachPopupView5 = BubbleAttachPopupView.this;
                    bubbleAttachPopupView5.bubbleContainer.setLookPosition(Math.max(0, (int) (((bubbleAttachPopupView5.popupInfo.touchPoint.x - bubbleAttachPopupView5.defaultOffsetX) - bubbleAttachPopupView5.translationX) - (r1.mLookWidth / 2))));
                    BubbleAttachPopupView.this.bubbleContainer.invalidate();
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                    BubbleAttachPopupView.this.initAndStartAnimation();
                }
            });
            return;
        }
        final Rect atViewRect = popupInfo.getAtViewRect();
        atViewRect.left -= getActivityContentLeft();
        int activityContentLeft = atViewRect.right - getActivityContentLeft();
        atViewRect.right = activityContentLeft;
        int i4 = (atViewRect.left + activityContentLeft) / 2;
        boolean z = ((float) (atViewRect.bottom + getPopupContentView().getMeasuredHeight())) > this.maxY;
        this.centerY = (atViewRect.top + atViewRect.bottom) / 2.0f;
        if (z) {
            this.isShowUp = true;
        } else {
            this.isShowUp = false;
        }
        this.isShowLeft = i4 > XPopupUtils.getAppWidth(getContext()) / 2;
        ViewGroup.LayoutParams layoutParams2 = getPopupContentView().getLayoutParams();
        if (isShowUpToTarget()) {
            screenHeight = atViewRect.top - getStatusBarHeight();
            i = this.overflow;
        } else {
            screenHeight = XPopupUtils.getScreenHeight(getContext()) - atViewRect.bottom;
            i = this.overflow;
        }
        int i5 = screenHeight - i;
        int appWidth2 = (this.isShowLeft ? atViewRect.right : XPopupUtils.getAppWidth(getContext()) - atViewRect.left) - this.overflow;
        if (getPopupContentView().getMeasuredHeight() > i5) {
            layoutParams2.height = i5;
        }
        if (getPopupContentView().getMeasuredWidth() > appWidth2) {
            layoutParams2.width = appWidth2;
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.4
            @Override // java.lang.Runnable
            public void run() {
                BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                PopupInfo popupInfo2 = bubbleAttachPopupView.popupInfo;
                if (popupInfo2 == null) {
                    return;
                }
                if (popupInfo2.isCenterHorizontal) {
                    Rect rect = atViewRect;
                    bubbleAttachPopupView.translationX = (((rect.left + rect.right) / 2.0f) + bubbleAttachPopupView.defaultOffsetX) - (bubbleAttachPopupView.getPopupContentView().getMeasuredWidth() / 2.0f);
                } else if (isLayoutRtl) {
                    if (bubbleAttachPopupView.isShowLeft) {
                        int appWidth3 = XPopupUtils.getAppWidth(bubbleAttachPopupView.getContext()) - atViewRect.right;
                        BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView.translationX = -((appWidth3 - bubbleAttachPopupView2.defaultOffsetX) - bubbleAttachPopupView2.bubbleContainer.getShadowRadius());
                    } else {
                        int appWidth4 = XPopupUtils.getAppWidth(bubbleAttachPopupView.getContext()) - atViewRect.left;
                        BubbleAttachPopupView bubbleAttachPopupView3 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView.translationX = -(((appWidth4 + bubbleAttachPopupView3.defaultOffsetX) + bubbleAttachPopupView3.bubbleContainer.getShadowRadius()) - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth());
                    }
                } else if (bubbleAttachPopupView.isShowLeft) {
                    bubbleAttachPopupView.translationX = ((atViewRect.right + bubbleAttachPopupView.defaultOffsetX) - bubbleAttachPopupView.getPopupContentView().getMeasuredWidth()) + BubbleAttachPopupView.this.bubbleContainer.getShadowRadius();
                } else {
                    bubbleAttachPopupView.translationX = (atViewRect.left + bubbleAttachPopupView.defaultOffsetX) - bubbleAttachPopupView.bubbleContainer.getShadowRadius();
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.translationY = (atViewRect.top - r0.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                } else {
                    BubbleAttachPopupView.this.translationY = atViewRect.bottom + r0.defaultOffsetY;
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                } else {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                }
                BubbleAttachPopupView bubbleAttachPopupView4 = BubbleAttachPopupView.this;
                if (bubbleAttachPopupView4.popupInfo.isCenterHorizontal) {
                    bubbleAttachPopupView4.bubbleContainer.setLookPositionCenter(true);
                } else if (isLayoutRtl) {
                    if (bubbleAttachPopupView4.isShowLeft) {
                        BubbleLayout bubbleLayout = bubbleAttachPopupView4.bubbleContainer;
                        float width = (-bubbleAttachPopupView4.translationX) - (atViewRect.width() / 2);
                        BubbleAttachPopupView bubbleAttachPopupView5 = BubbleAttachPopupView.this;
                        bubbleLayout.setLookPosition(Math.max(0, (int) ((width - bubbleAttachPopupView5.defaultOffsetX) + (bubbleAttachPopupView5.bubbleContainer.mLookWidth / 2))));
                    } else {
                        BubbleLayout bubbleLayout2 = bubbleAttachPopupView4.bubbleContainer;
                        int width2 = atViewRect.width() / 2;
                        BubbleAttachPopupView bubbleAttachPopupView6 = BubbleAttachPopupView.this;
                        bubbleLayout2.setLookPosition(Math.max(0, (width2 - bubbleAttachPopupView6.defaultOffsetX) + (bubbleAttachPopupView6.bubbleContainer.mLookWidth / 2)));
                    }
                } else {
                    BubbleLayout bubbleLayout3 = bubbleAttachPopupView4.bubbleContainer;
                    Rect rect2 = atViewRect;
                    bubbleLayout3.setLookPosition(Math.max(0, (int) (((rect2.right - (rect2.width() / 2)) - BubbleAttachPopupView.this.translationX) - (r3.bubbleContainer.mLookWidth / 2))));
                }
                BubbleAttachPopupView.this.bubbleContainer.invalidate();
                BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                BubbleAttachPopupView.this.initAndStartAnimation();
            }
        });
    }

    protected void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    protected boolean isShowUpToTarget() {
        PopupInfo popupInfo = this.popupInfo;
        return popupInfo.positionByWindowCenter ? this.centerY > ((float) (XPopupUtils.getAppHeight(getContext()) / 2)) : (this.isShowUp || popupInfo.popupPosition == PopupPosition.Top) && popupInfo.popupPosition != PopupPosition.Bottom;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }
}
