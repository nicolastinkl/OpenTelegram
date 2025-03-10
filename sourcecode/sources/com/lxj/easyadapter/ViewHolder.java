package com.lxj.easyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ViewHolder.kt */
/* loaded from: classes.dex */
public final class ViewHolder extends RecyclerView.ViewHolder {
    public static final Companion Companion = new Companion(null);
    private final View convertView;
    private final SparseArray<View> mViews;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewHolder(View convertView) {
        super(convertView);
        Intrinsics.checkNotNullParameter(convertView, "convertView");
        this.convertView = convertView;
        this.mViews = new SparseArray<>();
    }

    public final View getConvertView() {
        return this.convertView;
    }

    public final <T extends View> T getView(int i) {
        T t = (T) this.mViews.get(i);
        if (t == null) {
            t = (T) this.convertView.findViewById(i);
            this.mViews.put(i, t);
        }
        Objects.requireNonNull(t, "null cannot be cast to non-null type T of com.lxj.easyadapter.ViewHolder.getView");
        return t;
    }

    public final <T extends View> T getViewOrNull(int i) {
        T t = (T) this.mViews.get(i);
        if (t == null) {
            t = (T) this.convertView.findViewById(i);
            this.mViews.put(i, t);
        }
        if (t instanceof View) {
            return t;
        }
        return null;
    }

    public final ViewHolder setText(int i, CharSequence text) {
        Intrinsics.checkNotNullParameter(text, "text");
        TextView textView = (TextView) getView(i);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    /* compiled from: ViewHolder.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ViewHolder createViewHolder(View itemView) {
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            return new ViewHolder(itemView);
        }

        public final ViewHolder createViewHolder(Context context, ViewGroup parent, int i) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(parent, "parent");
            View itemView = LayoutInflater.from(context).inflate(i, parent, false);
            Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
            return new ViewHolder(itemView);
        }
    }
}
