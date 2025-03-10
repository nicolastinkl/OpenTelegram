package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.ViewGroup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BubbleLayout;

/* loaded from: classes.dex */
public class BubbleHorizontalAttachPopupView extends BubbleAttachPopupView {
    float translationX;
    float translationY;

    @Override // com.lxj.xpopup.core.BubbleAttachPopupView, com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        this.bubbleContainer.setLook(BubbleLayout.Look.LEFT);
        super.initPopupContent();
        PopupInfo popupInfo = this.popupInfo;
        this.defaultOffsetY = popupInfo.offsetY;
        int i = popupInfo.offsetX;
        if (i == 0) {
            i = XPopupUtils.dp2px(getContext(), 2.0f);
        }
        this.defaultOffsetX = i;
    }

    @Override // com.lxj.xpopup.core.BubbleAttachPopupView
    public void doAttach() {
        int appWidth;
        int i;
        float appWidth2;
        int i2;
        final boolean isLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
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
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleHorizontalAttachPopupView.1
                @Override // java.lang.Runnable
                public void run() {
                    float f;
                    float appWidth3;
                    BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView = BubbleHorizontalAttachPopupView.this;
                    if (bubbleHorizontalAttachPopupView.popupInfo == null) {
                        return;
                    }
                    if (!isLayoutRtl) {
                        if (bubbleHorizontalAttachPopupView.isShowLeftToTarget()) {
                            f = (BubbleHorizontalAttachPopupView.this.popupInfo.touchPoint.x - r1.getPopupContentView().getMeasuredWidth()) - BubbleHorizontalAttachPopupView.this.defaultOffsetX;
                        } else {
                            f = BubbleHorizontalAttachPopupView.this.popupInfo.touchPoint.x + r1.defaultOffsetX;
                        }
                        bubbleHorizontalAttachPopupView.translationX = f;
                    } else {
                        if (bubbleHorizontalAttachPopupView.isShowLeft) {
                            appWidth3 = (XPopupUtils.getAppWidth(bubbleHorizontalAttachPopupView.getContext()) - BubbleHorizontalAttachPopupView.this.popupInfo.touchPoint.x) + r2.defaultOffsetX;
                        } else {
                            appWidth3 = ((XPopupUtils.getAppWidth(bubbleHorizontalAttachPopupView.getContext()) - BubbleHorizontalAttachPopupView.this.popupInfo.touchPoint.x) - r2.getPopupContentView().getMeasuredWidth()) - BubbleHorizontalAttachPopupView.this.defaultOffsetX;
                        }
                        bubbleHorizontalAttachPopupView.translationX = -appWidth3;
                    }
                    BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView2 = BubbleHorizontalAttachPopupView.this;
                    float measuredHeight = bubbleHorizontalAttachPopupView2.popupInfo.touchPoint.y - (bubbleHorizontalAttachPopupView2.getPopupContentView().getMeasuredHeight() * 0.5f);
                    BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView3 = BubbleHorizontalAttachPopupView.this;
                    bubbleHorizontalAttachPopupView2.translationY = measuredHeight + bubbleHorizontalAttachPopupView3.defaultOffsetY;
                    bubbleHorizontalAttachPopupView3.doBubble();
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
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleHorizontalAttachPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                if (isLayoutRtl) {
                    BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView = BubbleHorizontalAttachPopupView.this;
                    bubbleHorizontalAttachPopupView.translationX = -(bubbleHorizontalAttachPopupView.isShowLeft ? (XPopupUtils.getAppWidth(bubbleHorizontalAttachPopupView.getContext()) - atViewRect.left) + BubbleHorizontalAttachPopupView.this.defaultOffsetX : ((XPopupUtils.getAppWidth(bubbleHorizontalAttachPopupView.getContext()) - atViewRect.right) - BubbleHorizontalAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleHorizontalAttachPopupView.this.defaultOffsetX);
                } else {
                    BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView2 = BubbleHorizontalAttachPopupView.this;
                    bubbleHorizontalAttachPopupView2.translationX = bubbleHorizontalAttachPopupView2.isShowLeftToTarget() ? (atViewRect.left - BubbleHorizontalAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleHorizontalAttachPopupView.this.defaultOffsetX : atViewRect.right + BubbleHorizontalAttachPopupView.this.defaultOffsetX;
                }
                BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView3 = BubbleHorizontalAttachPopupView.this;
                Rect rect = atViewRect;
                float height = rect.top + (((rect.height() - BubbleHorizontalAttachPopupView.this.getPopupContentView().getMeasuredHeight()) - (BubbleHorizontalAttachPopupView.this.bubbleContainer.getShadowRadius() * 2)) / 2.0f);
                BubbleHorizontalAttachPopupView bubbleHorizontalAttachPopupView4 = BubbleHorizontalAttachPopupView.this;
                bubbleHorizontalAttachPopupView3.translationY = height + bubbleHorizontalAttachPopupView4.defaultOffsetY;
                bubbleHorizontalAttachPopupView4.doBubble();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doBubble() {
        if (isShowLeftToTarget()) {
            this.bubbleContainer.setLook(BubbleLayout.Look.RIGHT);
        } else {
            this.bubbleContainer.setLook(BubbleLayout.Look.LEFT);
        }
        if (this.defaultOffsetY == 0) {
            this.bubbleContainer.setLookPositionCenter(true);
        } else {
            this.bubbleContainer.setLookPosition(Math.max(0, (int) (((r0.getMeasuredHeight() / 2.0f) - this.defaultOffsetY) - (this.bubbleContainer.mLookLength / 2))));
        }
        this.bubbleContainer.invalidate();
        getPopupContentView().setTranslationX(this.translationX);
        getPopupContentView().setTranslationY(this.translationY);
        initAndStartAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isShowLeftToTarget() {
        return (this.isShowLeft || this.popupInfo.popupPosition == PopupPosition.Left) && this.popupInfo.popupPosition != PopupPosition.Right;
    }
}
