package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public abstract class AttachPopupView extends BasePopupView {
    protected FrameLayout attachPopupContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    public AttachPopupView(Context context) {
        super(context);
        this.defaultOffsetY = 0;
        this.defaultOffsetX = 0;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.maxY = XPopupUtils.getAppHeight(getContext());
        this.overflow = XPopupUtils.dp2px(getContext(), 10.0f);
        this.centerY = 0.0f;
        this.attachPopupContainer = (FrameLayout) findViewById(R$id.attachPopupContainer);
    }

    protected void addInnerContent() {
        this.attachPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.attachPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_attach_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        if (this.attachPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.atView == null && popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for AttachPopupView before show()！");
        }
        this.defaultOffsetY = popupInfo.offsetY;
        int i = popupInfo.offsetX;
        this.defaultOffsetX = i;
        this.attachPopupContainer.setTranslationX(i);
        this.attachPopupContainer.setTranslationY(this.popupInfo.offsetY);
        applyBg();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                AttachPopupView.this.doAttach();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                AttachPopupView.this.doAttach();
            }
        });
    }

    protected void applyBg() {
        Drawable.ConstantState constantState;
        Drawable.ConstantState constantState2;
        if (this.isCreated) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (getPopupImplView().getBackground() != null && (constantState2 = getPopupImplView().getBackground().getConstantState()) != null) {
                this.attachPopupContainer.setBackground(constantState2.newDrawable(getResources()));
                getPopupImplView().setBackground(null);
            }
            this.attachPopupContainer.setElevation(XPopupUtils.dp2px(getContext(), 10.0f));
            return;
        }
        if (getPopupImplView().getBackground() == null || (constantState = getPopupImplView().getBackground().getConstantState()) == null) {
            return;
        }
        this.attachPopupContainer.setBackground(constantState.newDrawable(getResources()));
        getPopupImplView().setBackground(null);
    }

    public void doAttach() {
        if (this.popupInfo == null) {
            return;
        }
        int navBarHeight = getNavBarHeight();
        this.maxY = (XPopupUtils.getAppHeight(getContext()) - this.overflow) - navBarHeight;
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
            this.isShowLeft = this.popupInfo.touchPoint.x < ((float) XPopupUtils.getAppWidth(getContext())) / 2.0f;
            ViewGroup.LayoutParams layoutParams = getPopupContentView().getLayoutParams();
            int statusBarHeight = (int) (isShowUpToTarget() ? (this.popupInfo.touchPoint.y - getStatusBarHeight()) - this.overflow : ((XPopupUtils.getScreenHeight(getContext()) - this.popupInfo.touchPoint.y) - this.overflow) - navBarHeight);
            int appWidth = (int) ((this.isShowLeft ? XPopupUtils.getAppWidth(getContext()) - this.popupInfo.touchPoint.x : this.popupInfo.touchPoint.x) - this.overflow);
            if (getPopupContentView().getMeasuredHeight() > statusBarHeight) {
                layoutParams.height = statusBarHeight;
            }
            if (getPopupContentView().getMeasuredWidth() > appWidth) {
                layoutParams.width = Math.max(appWidth, getPopupWidth());
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.3
                @Override // java.lang.Runnable
                public void run() {
                    float appWidth2;
                    AttachPopupView attachPopupView = AttachPopupView.this;
                    PopupInfo popupInfo2 = attachPopupView.popupInfo;
                    if (popupInfo2 == null) {
                        return;
                    }
                    if (isLayoutRtl) {
                        if (attachPopupView.isShowLeft) {
                            appWidth2 = ((XPopupUtils.getAppWidth(attachPopupView.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) - r2.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                        } else {
                            appWidth2 = (XPopupUtils.getAppWidth(attachPopupView.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) + r2.defaultOffsetX;
                        }
                        attachPopupView.translationX = -appWidth2;
                    } else {
                        boolean z = attachPopupView.isShowLeft;
                        float f2 = popupInfo2.touchPoint.x;
                        attachPopupView.translationX = z ? f2 + attachPopupView.defaultOffsetX : (f2 - attachPopupView.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                    }
                    AttachPopupView attachPopupView2 = AttachPopupView.this;
                    if (attachPopupView2.popupInfo.isCenterHorizontal) {
                        if (attachPopupView2.isShowLeft) {
                            if (isLayoutRtl) {
                                attachPopupView2.translationX += attachPopupView2.getPopupContentView().getMeasuredWidth() / 2.0f;
                            } else {
                                attachPopupView2.translationX -= attachPopupView2.getPopupContentView().getMeasuredWidth() / 2.0f;
                            }
                        } else if (isLayoutRtl) {
                            attachPopupView2.translationX -= attachPopupView2.getPopupContentView().getMeasuredWidth() / 2.0f;
                        } else {
                            attachPopupView2.translationX += attachPopupView2.getPopupContentView().getMeasuredWidth() / 2.0f;
                        }
                    }
                    if (AttachPopupView.this.isShowUpToTarget()) {
                        AttachPopupView attachPopupView3 = AttachPopupView.this;
                        attachPopupView3.translationY = (attachPopupView3.popupInfo.touchPoint.y - attachPopupView3.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                    } else {
                        AttachPopupView attachPopupView4 = AttachPopupView.this;
                        attachPopupView4.translationY = attachPopupView4.popupInfo.touchPoint.y + attachPopupView4.defaultOffsetY;
                    }
                    AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                    AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                    AttachPopupView.this.initAndStartAnimation();
                }
            });
            return;
        }
        final Rect atViewRect = popupInfo.getAtViewRect();
        atViewRect.left -= getActivityContentLeft();
        int activityContentLeft = atViewRect.right - getActivityContentLeft();
        atViewRect.right = activityContentLeft;
        int i = (atViewRect.left + activityContentLeft) / 2;
        boolean z = ((float) (atViewRect.bottom + getPopupContentView().getMeasuredHeight())) > this.maxY;
        int i2 = atViewRect.top;
        this.centerY = (atViewRect.bottom + i2) / 2.0f;
        if (z) {
            int statusBarHeight2 = (i2 - getStatusBarHeight()) - this.overflow;
            if (getPopupContentView().getMeasuredHeight() > statusBarHeight2) {
                this.isShowUp = ((float) statusBarHeight2) > this.maxY - ((float) atViewRect.bottom);
            } else {
                this.isShowUp = true;
            }
        } else {
            this.isShowUp = false;
        }
        this.isShowLeft = i < XPopupUtils.getAppWidth(getContext()) / 2;
        ViewGroup.LayoutParams layoutParams2 = getPopupContentView().getLayoutParams();
        int statusBarHeight3 = isShowUpToTarget() ? (atViewRect.top - getStatusBarHeight()) - this.overflow : ((XPopupUtils.getScreenHeight(getContext()) - atViewRect.bottom) - this.overflow) - navBarHeight;
        int appWidth2 = (this.isShowLeft ? XPopupUtils.getAppWidth(getContext()) - atViewRect.left : atViewRect.right) - this.overflow;
        if (getPopupContentView().getMeasuredHeight() > statusBarHeight3) {
            layoutParams2.height = statusBarHeight3;
        }
        if (getPopupContentView().getMeasuredWidth() > appWidth2) {
            layoutParams2.width = Math.max(appWidth2, getPopupWidth());
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.4
            @Override // java.lang.Runnable
            public void run() {
                AttachPopupView attachPopupView = AttachPopupView.this;
                if (attachPopupView.popupInfo == null) {
                    return;
                }
                if (isLayoutRtl) {
                    attachPopupView.translationX = -(attachPopupView.isShowLeft ? ((XPopupUtils.getAppWidth(attachPopupView.getContext()) - atViewRect.left) - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX : (XPopupUtils.getAppWidth(attachPopupView.getContext()) - atViewRect.right) + AttachPopupView.this.defaultOffsetX);
                } else {
                    attachPopupView.translationX = attachPopupView.isShowLeft ? atViewRect.left + attachPopupView.defaultOffsetX : (atViewRect.right - attachPopupView.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                }
                AttachPopupView attachPopupView2 = AttachPopupView.this;
                if (attachPopupView2.popupInfo.isCenterHorizontal) {
                    if (attachPopupView2.isShowLeft) {
                        if (isLayoutRtl) {
                            attachPopupView2.translationX -= (atViewRect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        } else {
                            attachPopupView2.translationX += (atViewRect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        }
                    } else if (isLayoutRtl) {
                        attachPopupView2.translationX += (atViewRect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    } else {
                        attachPopupView2.translationX -= (atViewRect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    }
                }
                if (AttachPopupView.this.isShowUpToTarget()) {
                    AttachPopupView.this.translationY = (atViewRect.top - r0.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                } else {
                    AttachPopupView.this.translationY = atViewRect.bottom + r0.defaultOffsetY;
                }
                AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                AttachPopupView.this.initAndStartAnimation();
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
        ScrollScaleAnimator scrollScaleAnimator;
        if (isShowUpToTarget()) {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftBottom : PopupAnimation.ScrollAlphaFromRightBottom);
        } else {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftTop : PopupAnimation.ScrollAlphaFromRightTop);
        }
        return scrollScaleAnimator;
    }
}
