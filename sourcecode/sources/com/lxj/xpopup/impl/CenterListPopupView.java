package com.lxj.xpopup.impl;

import android.content.Context;
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
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.CheckView;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class CenterListPopupView extends CenterPopupView {
    int checkedPosition;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;
    CharSequence title;
    TextView tv_title;

    public CenterListPopupView(Context context, int bindLayoutId, int bindItemLayoutId) {
        super(context);
        this.checkedPosition = -1;
        this.bindLayoutId = bindLayoutId;
        this.bindItemLayoutId = bindItemLayoutId;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i == 0 ? R$layout._xpopup_center_impl_list : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        TextView textView = (TextView) findViewById(R$id.tv_title);
        this.tv_title = textView;
        if (textView != null) {
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
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(asList, i2) { // from class: com.lxj.xpopup.impl.CenterListPopupView.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder holder, String s, int position) {
                int i3 = R$id.tv_text;
                holder.setText(i3, s);
                ImageView imageView = (ImageView) holder.getViewOrNull(R$id.iv_image);
                int[] iArr = CenterListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= position) {
                    if (imageView != null) {
                        imageView.setVisibility(8);
                    }
                } else if (imageView != null) {
                    imageView.setVisibility(0);
                    imageView.setBackgroundResource(CenterListPopupView.this.iconIds[position]);
                }
                if (((CenterPopupView) CenterListPopupView.this).bindItemLayoutId == 0) {
                    if (CenterListPopupView.this.popupInfo.isDarkTheme) {
                        ((TextView) holder.getView(i3)).setTextColor(CenterListPopupView.this.getResources().getColor(R$color._xpopup_white_color));
                    } else {
                        ((TextView) holder.getView(i3)).setTextColor(CenterListPopupView.this.getResources().getColor(R$color._xpopup_dark_color));
                    }
                }
                if (CenterListPopupView.this.checkedPosition != -1) {
                    int i4 = R$id.check_view;
                    if (holder.getViewOrNull(i4) != null) {
                        holder.getView(i4).setVisibility(position != CenterListPopupView.this.checkedPosition ? 8 : 0);
                        ((CheckView) holder.getView(i4)).setColor(XPopup.getPrimaryColor());
                    }
                    TextView textView2 = (TextView) holder.getView(i3);
                    CenterListPopupView centerListPopupView = CenterListPopupView.this;
                    textView2.setTextColor(position == centerListPopupView.checkedPosition ? XPopup.getPrimaryColor() : centerListPopupView.getResources().getColor(R$color._xpopup_title_color));
                    ((TextView) holder.getView(i3)).setGravity(XPopupUtils.isLayoutRtl(CenterListPopupView.this.getContext()) ? 8388613 : 8388611);
                    return;
                }
                int i5 = R$id.check_view;
                if (holder.getViewOrNull(i5) != null) {
                    holder.getView(i5).setVisibility(8);
                }
                ((TextView) holder.getView(i3)).setGravity(17);
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.CenterListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (CenterListPopupView.this.selectListener != null && position >= 0 && position < easyAdapter.getData().size()) {
                    CenterListPopupView.this.selectListener.onSelect(position, (String) easyAdapter.getData().get(position));
                }
                CenterListPopupView centerListPopupView = CenterListPopupView.this;
                if (centerListPopupView.checkedPosition != -1) {
                    centerListPopupView.checkedPosition = position;
                    easyAdapter.notifyDataSetChanged();
                }
                if (CenterListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    CenterListPopupView.this.dismiss();
                }
            }
        });
        this.recyclerView.setAdapter(easyAdapter);
        applyTheme();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
        this.tv_title.setTextColor(getResources().getColor(R$color._xpopup_white_color));
        findViewById(R$id.xpopup_divider).setBackgroundColor(getResources().getColor(R$color._xpopup_list_dark_divider));
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
        this.tv_title.setTextColor(getResources().getColor(R$color._xpopup_dark_color));
        findViewById(R$id.xpopup_divider).setBackgroundColor(getResources().getColor(R$color._xpopup_list_divider));
    }

    public CenterListPopupView setStringData(CharSequence title, String[] data, int[] iconIds) {
        this.title = title;
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }

    public CenterListPopupView setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }

    public CenterListPopupView setCheckedPosition(int position) {
        this.checkedPosition = position;
        return this;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getMaxWidth() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return 0;
        }
        int i = popupInfo.maxWidth;
        return i == 0 ? super.getMaxWidth() : i;
    }
}
