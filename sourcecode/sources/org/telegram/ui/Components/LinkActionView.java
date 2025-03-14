package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_chatInviteExported;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputUserEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_chatInviteImporters;
import org.telegram.tgnet.TLRPC$TL_messages_getChatInviteImporters;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DialogCell;

/* loaded from: classes4.dex */
public class LinkActionView extends LinearLayout {
    private ActionBarPopupWindow actionBarPopupWindow;
    private final AvatarsContainer avatarsContainer;
    private boolean canEdit;
    private final TextView copyView;
    private Delegate delegate;
    BaseFragment fragment;
    private final FrameLayout frameLayout;
    private boolean hideRevokeOption;
    private boolean isChannel;
    String link;
    TextView linkView;
    private String loadedInviteLink;
    boolean loadingImporters;
    ImageView optionsView;
    private boolean permanent;
    float[] point;
    private QRCodeBottomSheet qrCodeBottomSheet;
    private String qrText;
    private final TextView removeView;
    private final TextView shareView;
    private int usersCount;

    public interface Delegate {

        /* renamed from: org.telegram.ui.Components.LinkActionView$Delegate$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$editLink(Delegate delegate) {
            }

            public static void $default$removeLink(Delegate delegate) {
            }

            public static void $default$showUsersForPermanentLink(Delegate delegate) {
            }
        }

        void editLink();

        void removeLink();

        void revokeLink();

        void showUsersForPermanentLink();
    }

    public LinkActionView(final Context context, final BaseFragment baseFragment, final BottomSheet bottomSheet, long j, boolean z, boolean z2) {
        super(context);
        this.canEdit = true;
        this.point = new float[2];
        this.fragment = baseFragment;
        this.permanent = z;
        this.isChannel = z2;
        setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        this.frameLayout = frameLayout;
        TextView textView = new TextView(context);
        this.linkView = textView;
        textView.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(18.0f), AndroidUtilities.dp(40.0f), AndroidUtilities.dp(18.0f));
        this.linkView.setTextSize(1, 16.0f);
        this.linkView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        this.linkView.setSingleLine(true);
        frameLayout.addView(this.linkView);
        ImageView imageView = new ImageView(context);
        this.optionsView = imageView;
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_ab_other));
        this.optionsView.setContentDescription(LocaleController.getString("AccDescrMoreOptions", R.string.AccDescrMoreOptions));
        this.optionsView.setScaleType(ImageView.ScaleType.CENTER);
        frameLayout.addView(this.optionsView, LayoutHelper.createFrame(40, 48, 21));
        addView(frameLayout, LayoutHelper.createLinear(-1, -2, 0, 4, 0, 4, 0));
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        TextView textView2 = new TextView(context);
        this.copyView = textView2;
        textView2.setGravity(1);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(context, R.drawable.msg_copy_filled)), 0, 1, 0);
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(8.0f)), 1, 2, 0);
        int i = R.string.LinkActionCopy;
        spannableStringBuilder.append((CharSequence) LocaleController.getString("LinkActionCopy", i));
        spannableStringBuilder.append((CharSequence) ".").setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(5.0f)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
        textView2.setText(spannableStringBuilder);
        textView2.setContentDescription(LocaleController.getString("LinkActionCopy", i));
        textView2.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView2.setSingleLine(true);
        linearLayout.addView(textView2, LayoutHelper.createLinear(0, 40, 1.0f, 0, 4, 0, 4, 0));
        TextView textView3 = new TextView(context);
        this.shareView = textView3;
        textView3.setGravity(1);
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        spannableStringBuilder2.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(context, R.drawable.msg_share_filled)), 0, 1, 0);
        spannableStringBuilder2.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(8.0f)), 1, 2, 0);
        int i2 = R.string.LinkActionShare;
        spannableStringBuilder2.append((CharSequence) LocaleController.getString("LinkActionShare", i2));
        spannableStringBuilder2.append((CharSequence) ".").setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(5.0f)), spannableStringBuilder2.length() - 1, spannableStringBuilder2.length(), 0);
        textView3.setText(spannableStringBuilder2);
        textView3.setContentDescription(LocaleController.getString("LinkActionShare", i2));
        textView3.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView3.setSingleLine(true);
        linearLayout.addView(textView3, LayoutHelper.createLinear(0, 40, 1.0f, 4, 0, 4, 0));
        TextView textView4 = new TextView(context);
        this.removeView = textView4;
        textView4.setGravity(1);
        SpannableStringBuilder spannableStringBuilder3 = new SpannableStringBuilder();
        spannableStringBuilder3.append((CharSequence) "..").setSpan(new ColoredImageSpan(ContextCompat.getDrawable(context, R.drawable.msg_delete_filled)), 0, 1, 0);
        spannableStringBuilder3.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(8.0f)), 1, 2, 0);
        spannableStringBuilder3.append((CharSequence) LocaleController.getString("DeleteLink", R.string.DeleteLink));
        spannableStringBuilder3.append((CharSequence) ".").setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.dp(5.0f)), spannableStringBuilder3.length() - 1, spannableStringBuilder3.length(), 0);
        textView4.setText(spannableStringBuilder3);
        textView4.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
        textView4.setTextSize(1, 14.0f);
        textView4.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView4.setSingleLine(true);
        linearLayout.addView(textView4, LayoutHelper.createLinear(0, -2, 1.0f, 4, 0, 4, 0));
        textView4.setVisibility(8);
        addView(linearLayout, LayoutHelper.createLinear(-1, -2, 0.0f, 20.0f, 0.0f, 0.0f));
        AvatarsContainer avatarsContainer = new AvatarsContainer(context);
        this.avatarsContainer = avatarsContainer;
        avatarsContainer.avatarsImageView.setAvatarsTextSize(AndroidUtilities.dp(18.0f));
        addView(avatarsContainer, LayoutHelper.createLinear(-1, 44, 0.0f, 12.0f, 0.0f, 0.0f));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LinkActionView.this.lambda$new$0(bottomSheet, baseFragment, view);
            }
        });
        if (z) {
            avatarsContainer.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LinkActionView.this.lambda$new$1(view);
                }
            });
        }
        textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LinkActionView.this.lambda$new$2(baseFragment, view);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LinkActionView.this.lambda$new$4(baseFragment, view);
            }
        });
        this.optionsView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LinkActionView.this.lambda$new$9(context, bottomSheet, baseFragment, view);
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LinkActionView.this.copyView.callOnClick();
            }
        });
        updateColors();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(BottomSheet bottomSheet, BaseFragment baseFragment, View view) {
        try {
            if (this.link == null) {
                return;
            }
            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", this.link));
            if (bottomSheet != null && bottomSheet.getContainer() != null) {
                BulletinFactory.createCopyLinkBulletin(bottomSheet.getContainer()).show();
            } else {
                BulletinFactory.createCopyLinkBulletin(baseFragment).show();
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        this.delegate.showUsersForPermanentLink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(BaseFragment baseFragment, View view) {
        try {
            if (this.link == null) {
                return;
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(COSRequestHeaderKey.TEXT_PLAIN);
            intent.putExtra("android.intent.extra.TEXT", this.link);
            baseFragment.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("InviteToGroupByLink", R.string.InviteToGroupByLink)), 500);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(BaseFragment baseFragment, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString("DeleteLink", R.string.DeleteLink));
        builder.setMessage(LocaleController.getString("DeleteLinkHelp", R.string.DeleteLinkHelp));
        builder.setPositiveButton(LocaleController.getString("Delete", R.string.Delete), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LinkActionView.this.lambda$new$3(dialogInterface, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        baseFragment.showDialog(builder.create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(DialogInterface dialogInterface, int i) {
        Delegate delegate = this.delegate;
        if (delegate != null) {
            delegate.removeLink();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(Context context, BottomSheet bottomSheet, BaseFragment baseFragment, View view) {
        final FrameLayout container;
        if (this.actionBarPopupWindow != null) {
            return;
        }
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(context);
        if (!this.permanent && this.canEdit) {
            ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(context, true, false);
            actionBarMenuSubItem.setTextAndIcon(LocaleController.getString("Edit", R.string.Edit), R.drawable.msg_edit);
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem, LayoutHelper.createLinear(-1, 48));
            actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LinkActionView.this.lambda$new$5(view2);
                }
            });
        }
        ActionBarMenuSubItem actionBarMenuSubItem2 = new ActionBarMenuSubItem(context, true, false);
        actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString("GetQRCode", R.string.GetQRCode), R.drawable.msg_qrcode);
        actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem2, LayoutHelper.createLinear(-1, 48));
        actionBarMenuSubItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LinkActionView.this.lambda$new$6(view2);
            }
        });
        if (!this.hideRevokeOption) {
            ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(context, false, true);
            actionBarMenuSubItem3.setTextAndIcon(LocaleController.getString("RevokeLink", R.string.RevokeLink), R.drawable.msg_delete);
            int i = Theme.key_text_RedRegular;
            actionBarMenuSubItem3.setColors(Theme.getColor(i), Theme.getColor(i));
            actionBarMenuSubItem3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    LinkActionView.this.lambda$new$7(view2);
                }
            });
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem3, LayoutHelper.createLinear(-1, 48));
        }
        if (bottomSheet == null) {
            container = baseFragment.getParentLayout().getOverlayContainerView();
        } else {
            container = bottomSheet.getContainer();
        }
        if (container != null) {
            getPointOnScreen(this.frameLayout, container, this.point);
            float f = this.point[1];
            final View view2 = new View(context) { // from class: org.telegram.ui.Components.LinkActionView.1
                @Override // android.view.View
                protected void onDraw(Canvas canvas) {
                    canvas.drawColor(AndroidUtilities.DARK_STATUS_BAR_OVERLAY);
                    LinkActionView linkActionView = LinkActionView.this;
                    linkActionView.getPointOnScreen(linkActionView.frameLayout, container, LinkActionView.this.point);
                    canvas.save();
                    float y = ((View) LinkActionView.this.frameLayout.getParent()).getY() + LinkActionView.this.frameLayout.getY();
                    if (y < 1.0f) {
                        canvas.clipRect(0.0f, (LinkActionView.this.point[1] - y) + 1.0f, getMeasuredWidth(), getMeasuredHeight());
                    }
                    float[] fArr = LinkActionView.this.point;
                    canvas.translate(fArr[0], fArr[1]);
                    LinkActionView.this.frameLayout.draw(canvas);
                    canvas.restore();
                }
            };
            final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener(this) { // from class: org.telegram.ui.Components.LinkActionView.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    view2.invalidate();
                    return true;
                }
            };
            container.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
            container.addView(view2, LayoutHelper.createFrame(-1, -1.0f));
            float f2 = 0.0f;
            view2.setAlpha(0.0f);
            view2.animate().alpha(1.0f).setDuration(150L);
            actionBarPopupWindowLayout.measure(View.MeasureSpec.makeMeasureSpec(container.getMeasuredWidth(), 0), View.MeasureSpec.makeMeasureSpec(container.getMeasuredHeight(), 0));
            ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(actionBarPopupWindowLayout, -2, -2);
            this.actionBarPopupWindow = actionBarPopupWindow;
            actionBarPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: org.telegram.ui.Components.LinkActionView.3
                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    LinkActionView.this.actionBarPopupWindow = null;
                    view2.animate().cancel();
                    view2.animate().alpha(0.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.LinkActionView.3.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (view2.getParent() != null) {
                                AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                container.removeView(view2);
                            }
                            container.getViewTreeObserver().removeOnPreDrawListener(onPreDrawListener);
                        }
                    });
                }
            });
            this.actionBarPopupWindow.setOutsideTouchable(true);
            this.actionBarPopupWindow.setFocusable(true);
            this.actionBarPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
            this.actionBarPopupWindow.setAnimationStyle(R.style.PopupContextAnimation);
            this.actionBarPopupWindow.setInputMethodMode(2);
            this.actionBarPopupWindow.setSoftInputMode(0);
            actionBarPopupWindowLayout.setDispatchKeyEventListener(new ActionBarPopupWindow.OnDispatchKeyEventListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda12
                @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener
                public final void onDispatchKeyEvent(KeyEvent keyEvent) {
                    LinkActionView.this.lambda$new$8(keyEvent);
                }
            });
            if (AndroidUtilities.isTablet()) {
                f += container.getPaddingTop();
                f2 = 0.0f - container.getPaddingLeft();
            }
            this.actionBarPopupWindow.showAtLocation(container, 0, (int) (((container.getMeasuredWidth() - actionBarPopupWindowLayout.getMeasuredWidth()) - AndroidUtilities.dp(16.0f)) + container.getX() + f2), (int) (f + this.frameLayout.getMeasuredHeight() + container.getY()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            actionBarPopupWindow.dismiss();
        }
        this.delegate.editLink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(View view) {
        showQrCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            actionBarPopupWindow.dismiss();
        }
        revokeLink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && this.actionBarPopupWindow.isShowing()) {
            this.actionBarPopupWindow.dismiss(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.widget.FrameLayout] */
    /* JADX WARN: Type inference failed for: r4v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r4v9, types: [android.view.View] */
    public void getPointOnScreen(FrameLayout frameLayout, FrameLayout frameLayout2, float[] fArr) {
        float f = 0.0f;
        float f2 = 0.0f;
        while (frameLayout != frameLayout2) {
            f += frameLayout.getY();
            f2 += frameLayout.getX();
            if (frameLayout instanceof ScrollView) {
                f -= frameLayout.getScrollY();
            }
            if (!(frameLayout.getParent() instanceof View)) {
                break;
            }
            frameLayout = (View) frameLayout.getParent();
            if (!(frameLayout instanceof ViewGroup)) {
                return;
            }
        }
        fArr[0] = f2 - frameLayout2.getPaddingLeft();
        fArr[1] = f - frameLayout2.getPaddingTop();
    }

    public void setQrText(String str) {
        this.qrText = str;
    }

    private void showQrCode() {
        int i;
        String str;
        Context context = getContext();
        String string = LocaleController.getString("InviteByQRCode", R.string.InviteByQRCode);
        String str2 = this.link;
        String str3 = this.qrText;
        if (str3 == null) {
            if (this.isChannel) {
                i = R.string.QRCodeLinkHelpChannel;
                str = "QRCodeLinkHelpChannel";
            } else {
                i = R.string.QRCodeLinkHelpGroup;
                str = "QRCodeLinkHelpGroup";
            }
            str3 = LocaleController.getString(str, i);
        }
        QRCodeBottomSheet qRCodeBottomSheet = new QRCodeBottomSheet(context, string, str2, str3, false) { // from class: org.telegram.ui.Components.LinkActionView.5
            @Override // org.telegram.ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface
            public void dismiss() {
                super.dismiss();
                LinkActionView.this.qrCodeBottomSheet = null;
            }
        };
        this.qrCodeBottomSheet = qRCodeBottomSheet;
        qRCodeBottomSheet.setCenterAnimation(R.raw.qr_code_logo);
        this.qrCodeBottomSheet.show();
        ActionBarPopupWindow actionBarPopupWindow = this.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            actionBarPopupWindow.dismiss();
        }
    }

    public void updateColors() {
        TextView textView = this.copyView;
        int i = Theme.key_featuredStickers_buttonText;
        textView.setTextColor(Theme.getColor(i));
        this.shareView.setTextColor(Theme.getColor(i));
        this.removeView.setTextColor(Theme.getColor(i));
        TextView textView2 = this.copyView;
        int dp = AndroidUtilities.dp(6.0f);
        int i2 = Theme.key_featuredStickers_addButton;
        int color = Theme.getColor(i2);
        int i3 = Theme.key_featuredStickers_addButtonPressed;
        textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(dp, color, Theme.getColor(i3)));
        this.shareView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), Theme.getColor(i2), Theme.getColor(i3)));
        this.removeView.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), Theme.getColor(Theme.key_chat_attachAudioBackground), ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_windowBackgroundWhite), 120)));
        this.frameLayout.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), Theme.getColor(Theme.key_graySection), ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_listSelector), 76)));
        this.linkView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.optionsView.setColorFilter(Theme.getColor(Theme.key_dialogTextGray3));
        TextView textView3 = this.avatarsContainer.countTextView;
        int i4 = Theme.key_windowBackgroundWhiteBlueText;
        textView3.setTextColor(Theme.getColor(i4));
        this.avatarsContainer.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6.0f), 0, ColorUtils.setAlphaComponent(Theme.getColor(i4), 76)));
        QRCodeBottomSheet qRCodeBottomSheet = this.qrCodeBottomSheet;
        if (qRCodeBottomSheet != null) {
            qRCodeBottomSheet.updateColors();
        }
    }

    public void setLink(String str) {
        this.link = str;
        if (str == null) {
            this.linkView.setText(LocaleController.getString("Loading", R.string.Loading));
        } else if (str.startsWith("https://")) {
            this.linkView.setText(str.substring(8));
        } else {
            this.linkView.setText(str);
        }
    }

    public void setRevoke(boolean z) {
        if (z) {
            this.optionsView.setVisibility(8);
            this.shareView.setVisibility(8);
            this.copyView.setVisibility(8);
            this.removeView.setVisibility(0);
            return;
        }
        this.optionsView.setVisibility(0);
        this.shareView.setVisibility(0);
        this.copyView.setVisibility(0);
        this.removeView.setVisibility(8);
    }

    public void hideRevokeOption(boolean z) {
        if (this.hideRevokeOption != z) {
            this.hideRevokeOption = z;
            this.optionsView.setVisibility(0);
            ImageView imageView = this.optionsView;
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_ab_other));
        }
    }

    private class AvatarsContainer extends FrameLayout {
        AvatarsImageView avatarsImageView;
        TextView countTextView;

        public AvatarsContainer(Context context) {
            super(context);
            this.avatarsImageView = new AvatarsImageView(context, false, LinkActionView.this) { // from class: org.telegram.ui.Components.LinkActionView.AvatarsContainer.1
                @Override // org.telegram.ui.Components.AvatarsImageView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(Math.min(3, LinkActionView.this.usersCount) == 0 ? 0 : ((r2 - 1) * 20) + 24 + 8), 1073741824), i2);
                }
            };
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            addView(linearLayout, LayoutHelper.createFrame(-2, -1, 1));
            TextView textView = new TextView(context);
            this.countTextView = textView;
            textView.setTextSize(1, 14.0f);
            this.countTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
            linearLayout.addView(this.avatarsImageView, LayoutHelper.createLinear(-2, -1));
            linearLayout.addView(this.countTextView, LayoutHelper.createLinear(-2, -2, 16));
            setPadding(0, AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f));
            this.avatarsImageView.commitTransition(false);
        }
    }

    private void revokeLink() {
        if (this.fragment.getParentActivity() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.fragment.getParentActivity());
        builder.setTitle(LocaleController.getString("RevokeLink", R.string.RevokeLink));
        builder.setMessage(LocaleController.getString("RevokeAlert", R.string.RevokeAlert));
        builder.setPositiveButton(LocaleController.getString("RevokeButton", R.string.RevokeButton), new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LinkActionView.this.lambda$revokeLink$10(dialogInterface, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        TextView textView = (TextView) builder.create().getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$revokeLink$10(DialogInterface dialogInterface, int i) {
        Delegate delegate = this.delegate;
        if (delegate != null) {
            delegate.revokeLink();
        }
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public void setUsers(int i, ArrayList<TLRPC$User> arrayList) {
        setUsers(i, arrayList, false);
    }

    public void setUsers(int i, ArrayList<TLRPC$User> arrayList, boolean z) {
        this.usersCount = i;
        if (i == 0) {
            this.avatarsContainer.setVisibility(8);
            setPadding(AndroidUtilities.dp(19.0f), AndroidUtilities.dp(18.0f), AndroidUtilities.dp(19.0f), AndroidUtilities.dp(18.0f));
        } else {
            this.avatarsContainer.setVisibility(0);
            setPadding(AndroidUtilities.dp(19.0f), AndroidUtilities.dp(18.0f), AndroidUtilities.dp(19.0f), AndroidUtilities.dp(10.0f));
            this.avatarsContainer.countTextView.setText(LocaleController.formatPluralString("PeopleJoined", i, new Object[0]));
            this.avatarsContainer.requestLayout();
        }
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                MessagesController.getInstance(UserConfig.selectedAccount).putUser(arrayList.get(i2), false);
            }
            int min = Math.min(3, Math.min(i, arrayList.size()));
            this.avatarsContainer.avatarsImageView.setCount(min);
            for (int i3 = 0; i3 < min; i3++) {
                this.avatarsContainer.avatarsImageView.setObject(i3, UserConfig.selectedAccount, arrayList.get(i3));
            }
        } else {
            this.avatarsContainer.avatarsImageView.setCount(0);
        }
        this.avatarsContainer.avatarsImageView.commitTransition(z);
    }

    public void loadUsers(final TLRPC$TL_chatInviteExported tLRPC$TL_chatInviteExported, long j) {
        if (tLRPC$TL_chatInviteExported == null) {
            setUsers(0, null, false);
            return;
        }
        if (TextUtils.equals(this.loadedInviteLink, tLRPC$TL_chatInviteExported.link)) {
            return;
        }
        setUsers(tLRPC$TL_chatInviteExported.usage, tLRPC$TL_chatInviteExported.importers, false);
        if (tLRPC$TL_chatInviteExported.usage <= 0 || tLRPC$TL_chatInviteExported.importers != null || this.loadingImporters) {
            return;
        }
        TLRPC$TL_messages_getChatInviteImporters tLRPC$TL_messages_getChatInviteImporters = new TLRPC$TL_messages_getChatInviteImporters();
        String str = tLRPC$TL_chatInviteExported.link;
        if (str != null) {
            tLRPC$TL_messages_getChatInviteImporters.flags |= 2;
            tLRPC$TL_messages_getChatInviteImporters.link = str;
        }
        tLRPC$TL_messages_getChatInviteImporters.peer = MessagesController.getInstance(UserConfig.selectedAccount).getInputPeer(-j);
        tLRPC$TL_messages_getChatInviteImporters.offset_user = new TLRPC$TL_inputUserEmpty();
        tLRPC$TL_messages_getChatInviteImporters.limit = Math.min(tLRPC$TL_chatInviteExported.usage, 3);
        this.loadingImporters = true;
        ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tLRPC$TL_messages_getChatInviteImporters, new RequestDelegate() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda11
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                LinkActionView.this.lambda$loadUsers$12(tLRPC$TL_chatInviteExported, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUsers$12(final TLRPC$TL_chatInviteExported tLRPC$TL_chatInviteExported, final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.LinkActionView$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                LinkActionView.this.lambda$loadUsers$11(tLRPC$TL_chatInviteExported, tLRPC$TL_error, tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUsers$11(TLRPC$TL_chatInviteExported tLRPC$TL_chatInviteExported, TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
        this.loadingImporters = false;
        this.loadedInviteLink = tLRPC$TL_chatInviteExported.link;
        if (tLRPC$TL_error == null) {
            TLRPC$TL_messages_chatInviteImporters tLRPC$TL_messages_chatInviteImporters = (TLRPC$TL_messages_chatInviteImporters) tLObject;
            if (tLRPC$TL_chatInviteExported.importers == null) {
                tLRPC$TL_chatInviteExported.importers = new ArrayList<>(3);
            }
            tLRPC$TL_chatInviteExported.importers.clear();
            for (int i = 0; i < tLRPC$TL_messages_chatInviteImporters.users.size(); i++) {
                tLRPC$TL_chatInviteExported.importers.addAll(tLRPC$TL_messages_chatInviteImporters.users);
            }
            setUsers(tLRPC$TL_chatInviteExported.usage, tLRPC$TL_chatInviteExported.importers, true);
        }
    }

    public void setPermanent(boolean z) {
        this.permanent = z;
    }

    public void setCanEdit(boolean z) {
        this.canEdit = z;
    }
}
