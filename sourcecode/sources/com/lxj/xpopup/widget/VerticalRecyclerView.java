package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.R$color;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes.dex */
public class VerticalRecyclerView extends RecyclerView {
    public VerticalRecyclerView(Context context) {
        this(context, null);
    }

    public VerticalRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setupDivider(Boolean isDark) {
        SmartDivider smartDivider = new SmartDivider(getContext(), 1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(getResources().getColor(isDark.booleanValue() ? R$color._xpopup_list_dark_divider : R$color._xpopup_list_divider));
        gradientDrawable.setSize(10, XPopupUtils.dp2px(getContext(), 0.5f));
        smartDivider.setDrawable(gradientDrawable);
        addItemDecoration(smartDivider);
    }
}
