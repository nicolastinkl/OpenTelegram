package com.lxj.xpopup.impl;

import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.R$color;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class BottomListPopupView extends BottomPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_cancel;
    TextView tv_title;
    View vv_divider;

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i == 0 ? R$layout._xpopup_bottom_impl_list : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        this.tv_title = (TextView) findViewById(R$id.tv_title);
        this.tv_cancel = (TextView) findViewById(R$id.tv_cancel);
        this.vv_divider = findViewById(R$id.vv_divider);
        TextView textView = this.tv_cancel;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    BottomListPopupView.this.dismiss();
                }
            });
        }
        if (this.tv_title != null) {
            if (TextUtils.isEmpty(this.title)) {
                this.tv_title.setVisibility(8);
                int i = R$id.xpopup_divider;
                if (findViewById(i) != null) {
                    findViewById(i).setVisibility(8);
                }
            } else {
                this.tv_title.setText(this.title);
            }
        }
        List asList = Arrays.asList(this.data);
        int i2 = this.bindItemLayoutId;
        if (i2 == 0) {
            i2 = R$layout._xpopup_adapter_text_match;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(asList, i2) { // from class: com.lxj.xpopup.impl.BottomListPopupView.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder holder, String s, int position) {
                int i3 = R$id.tv_text;
                holder.setText(i3, s);
                ImageView imageView = (ImageView) holder.getViewOrNull(R$id.iv_image);
                int[] iArr = BottomListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= position) {
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(0);
                    imageView.setBackgroundResource(BottomListPopupView.this.iconIds[position]);
                }
                BottomListPopupView bottomListPopupView = BottomListPopupView.this;
                if (bottomListPopupView.bindItemLayoutId == 0) {
                    if (bottomListPopupView.popupInfo.isDarkTheme) {
                        ((TextView) holder.getView(i3)).setTextColor(BottomListPopupView.this.getResources().getColor(R$color._xpopup_white_color));
                    } else {
                        ((TextView) holder.getView(i3)).setTextColor(BottomListPopupView.this.getResources().getColor(R$color._xpopup_dark_color));
                    }
                }
                if (BottomListPopupView.this.checkedPosition != -1) {
                    int i4 = R$id.check_view;
                    if (holder.getViewOrNull(i4) != null) {
                        holder.getView(i4).setVisibility(position != BottomListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) holder.getView(i4)).setColor(XPopup.getPrimaryColor());
                    }
                    TextView textView2 = (TextView) holder.getView(i3);
                    BottomListPopupView bottomListPopupView2 = BottomListPopupView.this;
                    textView2.setTextColor(position == bottomListPopupView2.checkedPosition ? XPopup.getPrimaryColor() : bottomListPopupView2.getResources().getColor(R$color._xpopup_title_color));
                    ((TextView) holder.getView(i3)).setGravity(XPopupUtils.isLayoutRtl(BottomListPopupView.this.getContext()) ? 8388613 : 8388611);
                    return;
                }
                int i5 = R$id.check_view;
                if (holder.getViewOrNull(i5) != null) {
                    holder.getView(i5).setVisibility(8);
                }
                ((TextView) holder.getView(i3)).setGravity(17);
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.BottomListPopupView.3
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (BottomListPopupView.this.selectListener != null) {
                    BottomListPopupView.this.selectListener.onSelect(position, (String) easyAdapter.getData().get(position));
                }
                BottomListPopupView bottomListPopupView = BottomListPopupView.this;
                if (bottomListPopupView.checkedPosition != -1) {
                    bottomListPopupView.checkedPosition = position;
                    easyAdapter.notifyDataSetChanged();
                }
                if (BottomListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    BottomListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    protected void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i = R$color._xpopup_white_color;
        textView.setTextColor(resources.getColor(i));
        TextView textView2 = this.tv_cancel;
        if (textView2 != null) {
            textView2.setTextColor(getResources().getColor(i));
        }
        findViewById(R$id.xpopup_divider).setBackgroundColor(getResources().getColor(R$color._xpopup_list_dark_divider));
        View view = this.vv_divider;
        if (view != null) {
            view.setBackgroundColor(Color.parseColor("#1B1B1B"));
        }
        View popupImplView = getPopupImplView();
        int color = getResources().getColor(R$color._xpopup_dark_color);
        float f = this.popupInfo.borderRadius;
        popupImplView.setBackground(XPopupUtils.createDrawable(color, f, f, 0.0f, 0.0f));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
        TextView textView = this.tv_title;
        Resources resources = getResources();
        int i = R$color._xpopup_dark_color;
        textView.setTextColor(resources.getColor(i));
        TextView textView2 = this.tv_cancel;
        if (textView2 != null) {
            textView2.setTextColor(getResources().getColor(i));
        }
        findViewById(R$id.xpopup_divider).setBackgroundColor(getResources().getColor(R$color._xpopup_list_divider));
        View view = this.vv_divider;
        if (view != null) {
            view.setBackgroundColor(getResources().getColor(R$color._xpopup_white_color));
        }
        View popupImplView = getPopupImplView();
        int color = getResources().getColor(R$color._xpopup_light_color);
        float f = this.popupInfo.borderRadius;
        popupImplView.setBackground(XPopupUtils.createDrawable(color, f, f, 0.0f, 0.0f));
    }
}
