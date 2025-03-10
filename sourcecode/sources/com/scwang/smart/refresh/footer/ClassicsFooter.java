package com.scwang.smart.refresh.footer;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.scwang.smart.drawable.ProgressDrawable;
import com.scwang.smart.refresh.classics.ArrowDrawable;
import com.scwang.smart.refresh.classics.ClassicsAbstract;
import com.scwang.smart.refresh.footer.classics.R$id;
import com.scwang.smart.refresh.footer.classics.R$layout;
import com.scwang.smart.refresh.footer.classics.R$string;
import com.scwang.smart.refresh.footer.classics.R$styleable;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.scwang.smart.refresh.layout.util.SmartUtil;

/* loaded from: classes.dex */
public class ClassicsFooter extends ClassicsAbstract<ClassicsFooter> implements RefreshFooter {
    public static String REFRESH_FOOTER_FAILED;
    public static String REFRESH_FOOTER_FINISH;
    public static String REFRESH_FOOTER_LOADING;
    public static String REFRESH_FOOTER_NOTHING;
    public static String REFRESH_FOOTER_PULLING;
    public static String REFRESH_FOOTER_REFRESHING;
    public static String REFRESH_FOOTER_RELEASE;
    protected boolean mNoMoreData;
    protected String mTextFailed;
    protected String mTextFinish;
    protected String mTextLoading;
    protected String mTextNothing;
    protected String mTextPulling;
    protected String mTextRefreshing;
    protected String mTextRelease;

    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mNoMoreData = false;
        View.inflate(context, R$layout.srl_classics_footer, this);
        ImageView imageView = (ImageView) findViewById(R$id.srl_classics_arrow);
        this.mArrowView = imageView;
        ImageView imageView2 = (ImageView) findViewById(R$id.srl_classics_progress);
        this.mProgressView = imageView2;
        this.mTitleText = (TextView) findViewById(R$id.srl_classics_title);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ClassicsFooter);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ClassicsFooter_srlDrawableMarginRight, SmartUtil.dp2px(20.0f));
        layoutParams2.rightMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        int i = R$styleable.ClassicsFooter_srlDrawableArrowSize;
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(i, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(i, layoutParams.height);
        int i2 = R$styleable.ClassicsFooter_srlDrawableProgressSize;
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(i2, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(i2, layoutParams2.height);
        int i3 = R$styleable.ClassicsFooter_srlDrawableSize;
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(i3, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(i3, layoutParams.height);
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(i3, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(i3, layoutParams2.height);
        this.mFinishDuration = obtainStyledAttributes.getInt(R$styleable.ClassicsFooter_srlFinishDuration, this.mFinishDuration);
        this.mSpinnerStyle = SpinnerStyle.values[obtainStyledAttributes.getInt(R$styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal)];
        int i4 = R$styleable.ClassicsFooter_srlDrawableArrow;
        if (obtainStyledAttributes.hasValue(i4)) {
            this.mArrowView.setImageDrawable(obtainStyledAttributes.getDrawable(i4));
        } else if (this.mArrowView.getDrawable() == null) {
            ArrowDrawable arrowDrawable = new ArrowDrawable();
            this.mArrowDrawable = arrowDrawable;
            arrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        int i5 = R$styleable.ClassicsFooter_srlDrawableProgress;
        if (obtainStyledAttributes.hasValue(i5)) {
            this.mProgressView.setImageDrawable(obtainStyledAttributes.getDrawable(i5));
        } else if (this.mProgressView.getDrawable() == null) {
            ProgressDrawable progressDrawable = new ProgressDrawable();
            this.mProgressDrawable = progressDrawable;
            progressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(r3, SmartUtil.dp2px(16.0f)));
        }
        int i6 = R$styleable.ClassicsFooter_srlPrimaryColor;
        if (obtainStyledAttributes.hasValue(i6)) {
            super.setPrimaryColor(obtainStyledAttributes.getColor(i6, 0));
        }
        int i7 = R$styleable.ClassicsFooter_srlAccentColor;
        if (obtainStyledAttributes.hasValue(i7)) {
            super.setAccentColor(obtainStyledAttributes.getColor(i7, 0));
        }
        int i8 = R$styleable.ClassicsFooter_srlTextPulling;
        if (obtainStyledAttributes.hasValue(i8)) {
            this.mTextPulling = obtainStyledAttributes.getString(i8);
        } else {
            String str = REFRESH_FOOTER_PULLING;
            if (str != null) {
                this.mTextPulling = str;
            } else {
                this.mTextPulling = context.getString(R$string.srl_footer_pulling);
            }
        }
        int i9 = R$styleable.ClassicsFooter_srlTextRelease;
        if (obtainStyledAttributes.hasValue(i9)) {
            this.mTextRelease = obtainStyledAttributes.getString(i9);
        } else {
            String str2 = REFRESH_FOOTER_RELEASE;
            if (str2 != null) {
                this.mTextRelease = str2;
            } else {
                this.mTextRelease = context.getString(R$string.srl_footer_release);
            }
        }
        int i10 = R$styleable.ClassicsFooter_srlTextLoading;
        if (obtainStyledAttributes.hasValue(i10)) {
            this.mTextLoading = obtainStyledAttributes.getString(i10);
        } else {
            String str3 = REFRESH_FOOTER_LOADING;
            if (str3 != null) {
                this.mTextLoading = str3;
            } else {
                this.mTextLoading = context.getString(R$string.srl_footer_loading);
            }
        }
        int i11 = R$styleable.ClassicsFooter_srlTextRefreshing;
        if (obtainStyledAttributes.hasValue(i11)) {
            this.mTextRefreshing = obtainStyledAttributes.getString(i11);
        } else {
            String str4 = REFRESH_FOOTER_REFRESHING;
            if (str4 != null) {
                this.mTextRefreshing = str4;
            } else {
                this.mTextRefreshing = context.getString(R$string.srl_footer_refreshing);
            }
        }
        int i12 = R$styleable.ClassicsFooter_srlTextFinish;
        if (obtainStyledAttributes.hasValue(i12)) {
            this.mTextFinish = obtainStyledAttributes.getString(i12);
        } else {
            String str5 = REFRESH_FOOTER_FINISH;
            if (str5 != null) {
                this.mTextFinish = str5;
            } else {
                this.mTextFinish = context.getString(R$string.srl_footer_finish);
            }
        }
        int i13 = R$styleable.ClassicsFooter_srlTextFailed;
        if (obtainStyledAttributes.hasValue(i13)) {
            this.mTextFailed = obtainStyledAttributes.getString(i13);
        } else {
            String str6 = REFRESH_FOOTER_FAILED;
            if (str6 != null) {
                this.mTextFailed = str6;
            } else {
                this.mTextFailed = context.getString(R$string.srl_footer_failed);
            }
        }
        int i14 = R$styleable.ClassicsFooter_srlTextNothing;
        if (obtainStyledAttributes.hasValue(i14)) {
            this.mTextNothing = obtainStyledAttributes.getString(i14);
        } else {
            String str7 = REFRESH_FOOTER_NOTHING;
            if (str7 != null) {
                this.mTextNothing = str7;
            } else {
                this.mTextNothing = context.getString(R$string.srl_footer_nothing);
            }
        }
        obtainStyledAttributes.recycle();
        imageView2.animate().setInterpolator(null);
        this.mTitleText.setText(isInEditMode() ? this.mTextLoading : this.mTextPulling);
        if (isInEditMode()) {
            imageView.setVisibility(8);
        } else {
            imageView2.setVisibility(8);
        }
    }

    @Override // com.scwang.smart.refresh.classics.ClassicsAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    public int onFinish(RefreshLayout refreshLayout, boolean z) {
        super.onFinish(refreshLayout, z);
        if (this.mNoMoreData) {
            return 0;
        }
        this.mTitleText.setText(z ? this.mTextFinish : this.mTextFailed);
        return this.mFinishDuration;
    }

    @Override // com.scwang.smart.refresh.classics.ClassicsAbstract, com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshComponent
    @Deprecated
    public void setPrimaryColors(int... iArr) {
        if (this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(iArr);
        }
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean z) {
        if (this.mNoMoreData == z) {
            return true;
        }
        this.mNoMoreData = z;
        ImageView imageView = this.mArrowView;
        if (z) {
            this.mTitleText.setText(this.mTextNothing);
            imageView.setVisibility(8);
            return true;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.setVisibility(0);
        return true;
    }

    @Override // com.scwang.smart.refresh.layout.simple.SimpleComponent, com.scwang.smart.refresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        ImageView imageView = this.mArrowView;
        if (this.mNoMoreData) {
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[refreshState2.ordinal()]) {
            case 1:
                imageView.setVisibility(0);
                break;
            case 2:
                break;
            case 3:
            case 4:
                imageView.setVisibility(8);
                this.mTitleText.setText(this.mTextLoading);
                return;
            case 5:
                this.mTitleText.setText(this.mTextRelease);
                imageView.animate().rotation(0.0f);
                return;
            case 6:
                this.mTitleText.setText(this.mTextRefreshing);
                imageView.setVisibility(8);
                return;
            default:
                return;
        }
        this.mTitleText.setText(this.mTextPulling);
        imageView.animate().rotation(180.0f);
    }

    /* renamed from: com.scwang.smart.refresh.footer.ClassicsFooter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smart$refresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
