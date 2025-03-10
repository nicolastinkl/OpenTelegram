package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnClickOutsideListener;
import com.lxj.xpopup.util.XPopupUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class PartShadowContainer extends FrameLayout {
    private OnClickOutsideListener listener;
    public ArrayList<Rect> notDismissArea;
    public BasePopupView popupView;
    private float x;
    private float y;

    public PartShadowContainer(Context context) {
        super(context);
    }

    public PartShadowContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartShadowContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        OnClickOutsideListener onClickOutsideListener;
        boolean z = false;
        View childAt = getChildAt(0);
        int[] iArr = new int[2];
        childAt.getLocationInWindow(iArr);
        if (!XPopupUtils.isInRect(event.getRawX(), event.getRawY(), new Rect(iArr[0], iArr[1], iArr[0] + childAt.getMeasuredWidth(), iArr[1] + childAt.getMeasuredHeight()))) {
            int action = event.getAction();
            if (action == 0) {
                this.x = event.getX();
                this.y = event.getY();
            } else if (action == 1 || action == 2 || action == 3) {
                if (((float) Math.sqrt(Math.pow(event.getX() - this.x, 2.0d) + Math.pow(event.getY() - this.y, 2.0d))) < ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    ArrayList<Rect> arrayList = this.notDismissArea;
                    if (arrayList != null && !arrayList.isEmpty()) {
                        Iterator<Rect> it = this.notDismissArea.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (XPopupUtils.isInRect(event.getRawX(), event.getRawY(), it.next())) {
                                z = true;
                                break;
                            }
                        }
                        if (!z && (onClickOutsideListener = this.listener) != null) {
                            onClickOutsideListener.onClickOutside();
                        }
                    } else {
                        OnClickOutsideListener onClickOutsideListener2 = this.listener;
                        if (onClickOutsideListener2 != null) {
                            onClickOutsideListener2.onClickOutside();
                        }
                    }
                }
                this.x = 0.0f;
                this.y = 0.0f;
            }
        }
        return true;
    }

    public void setOnClickOutsideListener(OnClickOutsideListener listener) {
        this.listener = listener;
    }
}
