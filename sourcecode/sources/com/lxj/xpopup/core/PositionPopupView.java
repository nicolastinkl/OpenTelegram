package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.DragOrientation;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.PositionPopupContainer;

/* loaded from: classes.dex */
public class PositionPopupView extends BasePopupView {
    PositionPopupContainer positionPopupContainer;

    public PositionPopupView(Context context) {
        super(context);
        this.positionPopupContainer = (PositionPopupContainer) findViewById(R$id.positionPopupContainer);
        this.positionPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.positionPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected final int getInnerLayoutId() {
        return R$layout._xpopup_position_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void initPopupContent() {
        super.initPopupContent();
        setClipChildren(false);
        setClipToPadding(false);
        PositionPopupContainer positionPopupContainer = this.positionPopupContainer;
        positionPopupContainer.enableDrag = this.popupInfo.enableDrag;
        positionPopupContainer.dragOrientation = getDragOrientation();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.PositionPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                PositionPopupView.this.doPosition();
            }
        });
        this.positionPopupContainer.setOnPositionDragChangeListener(new PositionPopupContainer.OnPositionDragListener() { // from class: com.lxj.xpopup.core.PositionPopupView.2
            @Override // com.lxj.xpopup.widget.PositionPopupContainer.OnPositionDragListener
            public void onDismiss() {
                PositionPopupView.this.dismiss();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void doMeasure() {
        super.doMeasure();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.PositionPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                PositionPopupView.this.doPosition();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doPosition() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.isCenterHorizontal) {
            this.positionPopupContainer.setTranslationX((!XPopupUtils.isLayoutRtl(getContext()) ? XPopupUtils.getAppWidth(getContext()) - this.positionPopupContainer.getMeasuredWidth() : -(XPopupUtils.getAppWidth(getContext()) - this.positionPopupContainer.getMeasuredWidth())) / 2.0f);
        } else {
            this.positionPopupContainer.setTranslationX(popupInfo.offsetX);
        }
        this.positionPopupContainer.setTranslationY(this.popupInfo.offsetY);
        initAndStartAnimation();
    }

    protected void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }

    protected DragOrientation getDragOrientation() {
        return DragOrientation.DragToUp;
    }
}
