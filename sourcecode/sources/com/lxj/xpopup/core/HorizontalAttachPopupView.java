package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.ViewGroup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class HorizontalAttachPopupView extends AttachPopupView {
    float translationX;
    float translationY;

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        PopupInfo popupInfo = this.popupInfo;
        this.defaultOffsetY = popupInfo.offsetY;
        int i = popupInfo.offsetX;
        if (i == 0) {
            i = XPopupUtils.dp2px(getContext(), 2.0f);
        }
        this.defaultOffsetX = i;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView
    public void doAttach() {
        int appWidth;
        int i;
        float appWidth2;
        int i2;
        if (this.popupInfo == null) {
            return;
        }
        final boolean isLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        final int measuredWidth = getPopupContentView().getMeasuredWidth();
        final int measuredHeight = getPopupContentView().getMeasuredHeight();
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.touchPoint != null) {
            PointF pointF = XPopup.longClickPoint;
            if (pointF != null) {
                popupInfo.touchPoint = pointF;
            }
            popupInfo.touchPoint.x -= getActivityContentLeft();
            this.isShowLeft = this.popupInfo.touchPoint.x > ((float) XPopupUtils.getAppWidth(getContext())) / 2.0f;
            ViewGroup.LayoutParams layoutParams = getPopupContentView().getLayoutParams();
            if (isLayoutRtl) {
                appWidth2 = this.isShowLeft ? this.popupInfo.touchPoint.x : XPopupUtils.getAppWidth(getContext()) - this.popupInfo.touchPoint.x;
                i2 = this.overflow;
            } else {
                appWidth2 = this.isShowLeft ? this.popupInfo.touchPoint.x : XPopupUtils.getAppWidth(getContext()) - this.popupInfo.touchPoint.x;
                i2 = this.overflow;
            }
            int i3 = (int) (appWidth2 - i2);
            if (getPopupContentView().getMeasuredWidth() > i3) {
                layoutParams.width = Math.max(i3, getPopupWidth());
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.HorizontalAttachPopupView.1
                @Override // java.lang.Runnable
                public void run() {
                    float f;
                    float appWidth3;
                    if (isLayoutRtl) {
                        HorizontalAttachPopupView horizontalAttachPopupView = HorizontalAttachPopupView.this;
                        if (horizontalAttachPopupView.isShowLeft) {
                            appWidth3 = (XPopupUtils.getAppWidth(horizontalAttachPopupView.getContext()) - HorizontalAttachPopupView.this.popupInfo.touchPoint.x) + r2.defaultOffsetX;
                        } else {
                            appWidth3 = ((XPopupUtils.getAppWidth(horizontalAttachPopupView.getContext()) - HorizontalAttachPopupView.this.popupInfo.touchPoint.x) - r2.getPopupContentView().getMeasuredWidth()) - HorizontalAttachPopupView.this.defaultOffsetX;
                        }
                        horizontalAttachPopupView.translationX = -appWidth3;
                    } else {
                        HorizontalAttachPopupView horizontalAttachPopupView2 = HorizontalAttachPopupView.this;
                        if (horizontalAttachPopupView2.isShowLeftToTarget()) {
                            f = (HorizontalAttachPopupView.this.popupInfo.touchPoint.x - measuredWidth) - r1.defaultOffsetX;
                        } else {
                            f = HorizontalAttachPopupView.this.popupInfo.touchPoint.x + r1.defaultOffsetX;
                        }
                        horizontalAttachPopupView2.translationX = f;
                    }
                    HorizontalAttachPopupView horizontalAttachPopupView3 = HorizontalAttachPopupView.this;
                    horizontalAttachPopupView3.translationY = (horizontalAttachPopupView3.popupInfo.touchPoint.y - (measuredHeight * 0.5f)) + horizontalAttachPopupView3.defaultOffsetY;
                    horizontalAttachPopupView3.getPopupContentView().setTranslationX(HorizontalAttachPopupView.this.translationX);
                    HorizontalAttachPopupView.this.getPopupContentView().setTranslationY(HorizontalAttachPopupView.this.translationY);
                    HorizontalAttachPopupView.this.initAndStartAnimation();
                }
            });
            return;
        }
        final Rect atViewRect = popupInfo.getAtViewRect();
        atViewRect.left -= getActivityContentLeft();
        int activityContentLeft = atViewRect.right - getActivityContentLeft();
        atViewRect.right = activityContentLeft;
        this.isShowLeft = (atViewRect.left + activityContentLeft) / 2 > XPopupUtils.getAppWidth(getContext()) / 2;
        ViewGroup.LayoutParams layoutParams2 = getPopupContentView().getLayoutParams();
        if (isLayoutRtl) {
            appWidth = this.isShowLeft ? atViewRect.left : XPopupUtils.getAppWidth(getContext()) - atViewRect.right;
            i = this.overflow;
        } else {
            appWidth = this.isShowLeft ? atViewRect.left : XPopupUtils.getAppWidth(getContext()) - atViewRect.right;
            i = this.overflow;
        }
        int i4 = appWidth - i;
        if (getPopupContentView().getMeasuredWidth() > i4) {
            layoutParams2.width = Math.max(i4, getPopupWidth());
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.HorizontalAttachPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                if (isLayoutRtl) {
                    HorizontalAttachPopupView horizontalAttachPopupView = HorizontalAttachPopupView.this;
                    horizontalAttachPopupView.translationX = -(horizontalAttachPopupView.isShowLeft ? (XPopupUtils.getAppWidth(horizontalAttachPopupView.getContext()) - atViewRect.left) + HorizontalAttachPopupView.this.defaultOffsetX : ((XPopupUtils.getAppWidth(horizontalAttachPopupView.getContext()) - atViewRect.right) - HorizontalAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - HorizontalAttachPopupView.this.defaultOffsetX);
                } else {
                    HorizontalAttachPopupView horizontalAttachPopupView2 = HorizontalAttachPopupView.this;
                    horizontalAttachPopupView2.translationX = horizontalAttachPopupView2.isShowLeftToTarget() ? (atViewRect.left - measuredWidth) - HorizontalAttachPopupView.this.defaultOffsetX : atViewRect.right + HorizontalAttachPopupView.this.defaultOffsetX;
                }
                HorizontalAttachPopupView horizontalAttachPopupView3 = HorizontalAttachPopupView.this;
                Rect rect = atViewRect;
                float height = rect.top + ((rect.height() - measuredHeight) / 2.0f);
                HorizontalAttachPopupView horizontalAttachPopupView4 = HorizontalAttachPopupView.this;
                horizontalAttachPopupView3.translationY = height + horizontalAttachPopupView4.defaultOffsetY;
                horizontalAttachPopupView4.getPopupContentView().setTranslationX(HorizontalAttachPopupView.this.translationX);
                HorizontalAttachPopupView.this.getPopupContentView().setTranslationY(HorizontalAttachPopupView.this.translationY);
                HorizontalAttachPopupView.this.initAndStartAnimation();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowLeftToTarget() {
        return (this.isShowLeft || this.popupInfo.popupPosition == PopupPosition.Left) && this.popupInfo.popupPosition != PopupPosition.Right;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        if (isShowLeftToTarget()) {
            return new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromRight);
        }
        return new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromLeft);
    }
}
