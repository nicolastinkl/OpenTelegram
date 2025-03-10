package com.lxj.xpopup.impl;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;
import com.lxj.xpopup.R$color;
import com.lxj.xpopup.R$id;
import com.lxj.xpopup.R$layout;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class AttachListPopupView extends AttachPopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    protected int contentGravity;
    String[] data;
    int[] iconIds;
    RecyclerView recyclerView;
    private OnSelectListener selectListener;

    public AttachListPopupView(Context context, int bindLayoutId, int bindItemLayoutId) {
        super(context);
        this.contentGravity = 17;
        this.bindLayoutId = bindLayoutId;
        this.bindItemLayoutId = bindItemLayoutId;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        int i = this.bindLayoutId;
        return i == 0 ? R$layout._xpopup_attach_impl_list : i;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = (RecyclerView) findViewById(R$id.recyclerView);
        this.recyclerView = recyclerView;
        if (this.bindLayoutId != 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        List asList = Arrays.asList(this.data);
        int i = this.bindItemLayoutId;
        if (i == 0) {
            i = R$layout._xpopup_adapter_text;
        }
        final EasyAdapter<String> easyAdapter = new EasyAdapter<String>(asList, i) { // from class: com.lxj.xpopup.impl.AttachListPopupView.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.lxj.easyadapter.EasyAdapter
            public void bind(ViewHolder holder, String s, int position) {
                int i2 = R$id.tv_text;
                holder.setText(i2, s);
                ImageView imageView = (ImageView) holder.getViewOrNull(R$id.iv_image);
                int[] iArr = AttachListPopupView.this.iconIds;
                if (iArr == null || iArr.length <= position) {
                    XPopupUtils.setVisible(imageView, false);
                } else if (imageView != null) {
                    XPopupUtils.setVisible(imageView, true);
                    imageView.setBackgroundResource(AttachListPopupView.this.iconIds[position]);
                }
                AttachListPopupView attachListPopupView = AttachListPopupView.this;
                if (attachListPopupView.bindItemLayoutId == 0) {
                    if (attachListPopupView.popupInfo.isDarkTheme) {
                        ((TextView) holder.getView(i2)).setTextColor(AttachListPopupView.this.getResources().getColor(R$color._xpopup_white_color));
                    } else {
                        ((TextView) holder.getView(i2)).setTextColor(AttachListPopupView.this.getResources().getColor(R$color._xpopup_dark_color));
                    }
                    ((LinearLayout) holder.getView(R$id._ll_temp)).setGravity(AttachListPopupView.this.contentGravity);
                }
            }
        };
        easyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() { // from class: com.lxj.xpopup.impl.AttachListPopupView.2
            @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (AttachListPopupView.this.selectListener != null) {
                    AttachListPopupView.this.selectListener.onSelect(position, (String) easyAdapter.getData().get(position));
                }
                if (AttachListPopupView.this.popupInfo.autoDismiss.booleanValue()) {
                    AttachListPopupView.this.dismiss();
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
            this.attachPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(this.popupInfo.isDarkTheme ? R$color._xpopup_dark_color : R$color._xpopup_light_color), this.popupInfo.borderRadius));
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyDarkTheme() {
        super.applyDarkTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.TRUE);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void applyLightTheme() {
        super.applyLightTheme();
        ((VerticalRecyclerView) this.recyclerView).setupDivider(Boolean.FALSE);
    }

    public AttachListPopupView setStringData(String[] data, int[] iconIds) {
        this.data = data;
        this.iconIds = iconIds;
        return this;
    }

    public AttachListPopupView setContentGravity(int gravity) {
        this.contentGravity = gravity;
        return this;
    }

    public AttachListPopupView setOnSelectListener(OnSelectListener selectListener) {
        this.selectListener = selectListener;
        return this;
    }
}
