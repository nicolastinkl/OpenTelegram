package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.style.CharacterStyle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.recyclerview.widget.ChatListItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManagerFixed;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ForwardingMessagesParams;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$ReactionCount;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Cells.TextSelectionHelper;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.ForwardingPreviewView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.PinchToZoomHelper;

/* loaded from: classes4.dex */
public class ForwardingPreviewView extends FrameLayout {
    ActionBar actionBar;
    ArrayList<ActionBarMenuSubItem> actionItems;
    Adapter adapter;
    LinearLayout buttonsLayout;
    LinearLayout buttonsLayout2;
    Runnable changeBoundsRunnable;
    ActionBarMenuSubItem changeRecipientView;
    GridLayoutManagerFixed chatLayoutManager;
    RecyclerListView chatListView;
    SizeNotifierFrameLayout chatPreviewContainer;
    int chatTopOffset;
    TLRPC$Chat currentChat;
    int currentTopOffset;
    TLRPC$User currentUser;
    float currentYOffset;
    private final ArrayList<MessageObject.GroupedMessages> drawingGroups;
    private boolean firstLayout;
    ForwardingMessagesParams forwardingMessagesParams;
    ActionBarMenuSubItem hideCaptionView;
    ActionBarMenuSubItem hideSendersNameView;
    boolean isLandscapeMode;
    ChatListItemAnimator itemAnimator;
    int lastSize;
    LinearLayout menuContainer;
    ScrollView menuScrollView;
    ValueAnimator offsetsAnimator;
    android.graphics.Rect rect;
    private final ResourcesDelegate resourcesProvider;
    boolean returnSendersNames;
    TLRPC$Peer sendAsPeer;
    ActionBarMenuSubItem sendMessagesView;
    ActionBarMenuSubItem showCaptionView;
    ActionBarMenuSubItem showSendersNameView;
    boolean showing;
    boolean updateAfterAnimations;
    float yOffset;

    public interface ResourcesDelegate extends Theme.ResourcesProvider {
        Drawable getWallpaperDrawable();

        boolean isWallpaperMotion();
    }

    private void updateColors() {
    }

    protected void didSendPressed() {
    }

    protected void onDismiss(boolean z) {
    }

    protected void selectAnotherChat() {
    }

    public void setSendAsPeer(TLRPC$Peer tLRPC$Peer) {
        this.sendAsPeer = tLRPC$Peer;
        updateMessages();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public ForwardingPreviewView(Context context, final ForwardingMessagesParams forwardingMessagesParams, TLRPC$User tLRPC$User, TLRPC$Chat tLRPC$Chat, int i, final ResourcesDelegate resourcesDelegate) {
        super(context);
        int i2;
        String str;
        int i3;
        String str2;
        this.actionItems = new ArrayList<>();
        this.rect = new android.graphics.Rect();
        this.firstLayout = true;
        this.changeBoundsRunnable = new Runnable() { // from class: org.telegram.ui.Components.ForwardingPreviewView.1
            @Override // java.lang.Runnable
            public void run() {
                ValueAnimator valueAnimator = ForwardingPreviewView.this.offsetsAnimator;
                if (valueAnimator == null || valueAnimator.isRunning()) {
                    return;
                }
                ForwardingPreviewView.this.offsetsAnimator.start();
            }
        };
        this.drawingGroups = new ArrayList<>(10);
        this.currentUser = tLRPC$User;
        this.currentChat = tLRPC$Chat;
        this.forwardingMessagesParams = forwardingMessagesParams;
        this.resourcesProvider = resourcesDelegate;
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.Components.ForwardingPreviewView.2
            @Override // org.telegram.ui.Components.SizeNotifierFrameLayout
            protected Drawable getNewDrawable() {
                Drawable wallpaperDrawable = resourcesDelegate.getWallpaperDrawable();
                return wallpaperDrawable != null ? wallpaperDrawable : super.getNewDrawable();
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getY() < ForwardingPreviewView.this.currentTopOffset) {
                    return false;
                }
                return super.dispatchTouchEvent(motionEvent);
            }
        };
        this.chatPreviewContainer = sizeNotifierFrameLayout;
        sizeNotifierFrameLayout.setBackgroundImage(resourcesDelegate.getWallpaperDrawable(), resourcesDelegate.isWallpaperMotion());
        this.chatPreviewContainer.setOccupyStatusBar(false);
        if (Build.VERSION.SDK_INT >= 21) {
            this.chatPreviewContainer.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.ForwardingPreviewView.3
                @Override // android.view.ViewOutlineProvider
                @TargetApi(21)
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, ForwardingPreviewView.this.currentTopOffset + 1, view.getMeasuredWidth(), view.getMeasuredHeight(), AndroidUtilities.dp(6.0f));
                }
            });
            this.chatPreviewContainer.setClipToOutline(true);
            this.chatPreviewContainer.setElevation(AndroidUtilities.dp(4.0f));
        }
        ActionBar actionBar = new ActionBar(context, resourcesDelegate);
        this.actionBar = actionBar;
        actionBar.setBackgroundColor(getThemedColor(Theme.key_actionBarDefault));
        this.actionBar.setOccupyStatusBar(false);
        RecyclerListView recyclerListView = new RecyclerListView(context, resourcesDelegate) { // from class: org.telegram.ui.Components.ForwardingPreviewView.4
            @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
            public boolean drawChild(Canvas canvas, View view, long j) {
                if (!(view instanceof ChatMessageCell)) {
                    return true;
                }
                ChatMessageCell chatMessageCell = (ChatMessageCell) view;
                boolean drawChild = super.drawChild(canvas, view, j);
                chatMessageCell.drawCheckBox(canvas);
                canvas.save();
                canvas.translate(chatMessageCell.getX(), chatMessageCell.getY());
                chatMessageCell.drawMessageText(canvas, chatMessageCell.getMessageObject().textLayoutBlocks, chatMessageCell.getMessageObject().textXOffset, true, 1.0f, false);
                if (chatMessageCell.getCurrentMessagesGroup() != null || chatMessageCell.getTransitionParams().animateBackgroundBoundsInner) {
                    chatMessageCell.drawNamesLayout(canvas, 1.0f);
                }
                if ((chatMessageCell.getCurrentPosition() != null && chatMessageCell.getCurrentPosition().last) || chatMessageCell.getTransitionParams().animateBackgroundBoundsInner) {
                    chatMessageCell.drawTime(canvas, 1.0f, true);
                }
                if (chatMessageCell.getCurrentPosition() == null || chatMessageCell.getCurrentPosition().last || chatMessageCell.getCurrentMessagesGroup().isDocuments) {
                    chatMessageCell.drawCaptionLayout(canvas, false, 1.0f);
                }
                chatMessageCell.getTransitionParams().recordDrawingStatePreview();
                canvas.restore();
                return drawChild;
            }

            @Override // org.telegram.ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                for (int i4 = 0; i4 < getChildCount(); i4++) {
                    View childAt = getChildAt(i4);
                    if (childAt instanceof ChatMessageCell) {
                        ((ChatMessageCell) childAt).setParentViewSize(ForwardingPreviewView.this.chatPreviewContainer.getMeasuredWidth(), ForwardingPreviewView.this.chatPreviewContainer.getBackgroundSizeY());
                    }
                }
                drawChatBackgroundElements(canvas);
                super.dispatchDraw(canvas);
            }

            @Override // org.telegram.ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z, int i4, int i5, int i6, int i7) {
                super.onLayout(z, i4, i5, i6, i7);
                ForwardingPreviewView.this.updatePositions();
            }

            /* JADX WARN: Type inference failed for: r3v0 */
            /* JADX WARN: Type inference failed for: r3v1, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r3v9 */
            private void drawChatBackgroundElements(Canvas canvas) {
                boolean z;
                int i4;
                MessageObject.GroupedMessages currentMessagesGroup;
                ChatMessageCell chatMessageCell;
                MessageObject.GroupedMessages currentMessagesGroup2;
                int childCount = getChildCount();
                ?? r3 = 0;
                MessageObject.GroupedMessages groupedMessages = null;
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if ((childAt instanceof ChatMessageCell) && ((currentMessagesGroup2 = (chatMessageCell = (ChatMessageCell) childAt).getCurrentMessagesGroup()) == null || currentMessagesGroup2 != groupedMessages)) {
                        chatMessageCell.getCurrentPosition();
                        chatMessageCell.getBackgroundDrawable();
                        groupedMessages = currentMessagesGroup2;
                    }
                }
                int i6 = 0;
                while (i6 < 3) {
                    ForwardingPreviewView.this.drawingGroups.clear();
                    if (i6 != 2 || ForwardingPreviewView.this.chatListView.isFastScrollAnimationRunning()) {
                        int i7 = 0;
                        while (true) {
                            z = true;
                            if (i7 >= childCount) {
                                break;
                            }
                            View childAt2 = ForwardingPreviewView.this.chatListView.getChildAt(i7);
                            if (childAt2 instanceof ChatMessageCell) {
                                ChatMessageCell chatMessageCell2 = (ChatMessageCell) childAt2;
                                if (childAt2.getY() <= ForwardingPreviewView.this.chatListView.getHeight() && childAt2.getY() + childAt2.getHeight() >= 0.0f && (currentMessagesGroup = chatMessageCell2.getCurrentMessagesGroup()) != null && ((i6 != 0 || currentMessagesGroup.messages.size() != 1) && ((i6 != 1 || currentMessagesGroup.transitionParams.drawBackgroundForDeletedItems) && ((i6 != 0 || !chatMessageCell2.getMessageObject().deleted) && ((i6 != 1 || chatMessageCell2.getMessageObject().deleted) && ((i6 != 2 || chatMessageCell2.willRemovedAfterAnimation()) && (i6 == 2 || !chatMessageCell2.willRemovedAfterAnimation()))))))) {
                                    if (!ForwardingPreviewView.this.drawingGroups.contains(currentMessagesGroup)) {
                                        MessageObject.GroupedMessages.TransitionParams transitionParams = currentMessagesGroup.transitionParams;
                                        transitionParams.left = r3;
                                        transitionParams.top = r3;
                                        transitionParams.right = r3;
                                        transitionParams.bottom = r3;
                                        transitionParams.pinnedBotton = r3;
                                        transitionParams.pinnedTop = r3;
                                        transitionParams.cell = chatMessageCell2;
                                        ForwardingPreviewView.this.drawingGroups.add(currentMessagesGroup);
                                    }
                                    currentMessagesGroup.transitionParams.pinnedTop = chatMessageCell2.isPinnedTop();
                                    currentMessagesGroup.transitionParams.pinnedBotton = chatMessageCell2.isPinnedBottom();
                                    int left = chatMessageCell2.getLeft() + chatMessageCell2.getBackgroundDrawableLeft();
                                    int left2 = chatMessageCell2.getLeft() + chatMessageCell2.getBackgroundDrawableRight();
                                    int top = chatMessageCell2.getTop() + chatMessageCell2.getBackgroundDrawableTop();
                                    int top2 = chatMessageCell2.getTop() + chatMessageCell2.getBackgroundDrawableBottom();
                                    if ((chatMessageCell2.getCurrentPosition().flags & 4) == 0) {
                                        top -= AndroidUtilities.dp(10.0f);
                                    }
                                    if ((chatMessageCell2.getCurrentPosition().flags & 8) == 0) {
                                        top2 += AndroidUtilities.dp(10.0f);
                                    }
                                    if (chatMessageCell2.willRemovedAfterAnimation()) {
                                        currentMessagesGroup.transitionParams.cell = chatMessageCell2;
                                    }
                                    MessageObject.GroupedMessages.TransitionParams transitionParams2 = currentMessagesGroup.transitionParams;
                                    int i8 = transitionParams2.top;
                                    if (i8 == 0 || top < i8) {
                                        transitionParams2.top = top;
                                    }
                                    int i9 = transitionParams2.bottom;
                                    if (i9 == 0 || top2 > i9) {
                                        transitionParams2.bottom = top2;
                                    }
                                    int i10 = transitionParams2.left;
                                    if (i10 == 0 || left < i10) {
                                        transitionParams2.left = left;
                                    }
                                    int i11 = transitionParams2.right;
                                    if (i11 == 0 || left2 > i11) {
                                        transitionParams2.right = left2;
                                    }
                                }
                            }
                            i7++;
                        }
                        int i12 = 0;
                        while (i12 < ForwardingPreviewView.this.drawingGroups.size()) {
                            MessageObject.GroupedMessages groupedMessages2 = (MessageObject.GroupedMessages) ForwardingPreviewView.this.drawingGroups.get(i12);
                            if (groupedMessages2 == null) {
                                i4 = i6;
                            } else {
                                float nonAnimationTranslationX = groupedMessages2.transitionParams.cell.getNonAnimationTranslationX(z);
                                MessageObject.GroupedMessages.TransitionParams transitionParams3 = groupedMessages2.transitionParams;
                                float f = transitionParams3.left + nonAnimationTranslationX + transitionParams3.offsetLeft;
                                float f2 = transitionParams3.top + transitionParams3.offsetTop;
                                float f3 = transitionParams3.right + nonAnimationTranslationX + transitionParams3.offsetRight;
                                float f4 = transitionParams3.bottom + transitionParams3.offsetBottom;
                                if (!transitionParams3.backgroundChangeBounds) {
                                    f2 += transitionParams3.cell.getTranslationY();
                                    f4 += groupedMessages2.transitionParams.cell.getTranslationY();
                                }
                                if (f2 < (-AndroidUtilities.dp(20.0f))) {
                                    f2 = -AndroidUtilities.dp(20.0f);
                                }
                                if (f4 > ForwardingPreviewView.this.chatListView.getMeasuredHeight() + AndroidUtilities.dp(20.0f)) {
                                    f4 = ForwardingPreviewView.this.chatListView.getMeasuredHeight() + AndroidUtilities.dp(20.0f);
                                }
                                boolean z2 = (groupedMessages2.transitionParams.cell.getScaleX() == 1.0f && groupedMessages2.transitionParams.cell.getScaleY() == 1.0f) ? false : true;
                                if (z2) {
                                    canvas.save();
                                    canvas.scale(groupedMessages2.transitionParams.cell.getScaleX(), groupedMessages2.transitionParams.cell.getScaleY(), f + ((f3 - f) / 2.0f), f2 + ((f4 - f2) / 2.0f));
                                }
                                MessageObject.GroupedMessages.TransitionParams transitionParams4 = groupedMessages2.transitionParams;
                                i4 = i6;
                                transitionParams4.cell.drawBackground(canvas, (int) f, (int) f2, (int) f3, (int) f4, transitionParams4.pinnedTop, transitionParams4.pinnedBotton, false, 0);
                                MessageObject.GroupedMessages.TransitionParams transitionParams5 = groupedMessages2.transitionParams;
                                transitionParams5.cell = null;
                                transitionParams5.drawCaptionLayout = groupedMessages2.hasCaption;
                                if (z2) {
                                    canvas.restore();
                                    for (int i13 = 0; i13 < childCount; i13++) {
                                        View childAt3 = ForwardingPreviewView.this.chatListView.getChildAt(i13);
                                        if (childAt3 instanceof ChatMessageCell) {
                                            ChatMessageCell chatMessageCell3 = (ChatMessageCell) childAt3;
                                            if (chatMessageCell3.getCurrentMessagesGroup() == groupedMessages2) {
                                                int left3 = chatMessageCell3.getLeft();
                                                int top3 = chatMessageCell3.getTop();
                                                childAt3.setPivotX((f - left3) + ((f3 - f) / 2.0f));
                                                childAt3.setPivotY((f2 - top3) + ((f4 - f2) / 2.0f));
                                            }
                                        }
                                    }
                                }
                            }
                            i12++;
                            i6 = i4;
                            z = true;
                        }
                    }
                    i6++;
                    r3 = 0;
                }
            }
        };
        this.chatListView = recyclerListView;
        AnonymousClass5 anonymousClass5 = new AnonymousClass5(null, this.chatListView, resourcesDelegate, i);
        this.itemAnimator = anonymousClass5;
        recyclerListView.setItemAnimator(anonymousClass5);
        this.chatListView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView.6
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i4, int i5) {
                super.onScrolled(recyclerView, i4, i5);
                for (int i6 = 0; i6 < ForwardingPreviewView.this.chatListView.getChildCount(); i6++) {
                    ((ChatMessageCell) ForwardingPreviewView.this.chatListView.getChildAt(i6)).setParentViewSize(ForwardingPreviewView.this.chatPreviewContainer.getMeasuredWidth(), ForwardingPreviewView.this.chatPreviewContainer.getBackgroundSizeY());
                }
            }
        });
        this.chatListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView.7
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public void onItemClick(View view, int i4) {
                if (ForwardingPreviewView.this.forwardingMessagesParams.previewMessages.size() <= 1) {
                    return;
                }
                int id = forwardingMessagesParams.previewMessages.get(i4).getId();
                boolean z = !forwardingMessagesParams.selectedIds.get(id, false);
                if (ForwardingPreviewView.this.forwardingMessagesParams.selectedIds.size() != 1 || z) {
                    if (!z) {
                        forwardingMessagesParams.selectedIds.delete(id);
                    } else {
                        forwardingMessagesParams.selectedIds.put(id, z);
                    }
                    ((ChatMessageCell) view).setChecked(z, z, true);
                    ForwardingPreviewView.this.actionBar.setTitle(LocaleController.formatPluralString("PreviewForwardMessagesCount", forwardingMessagesParams.selectedIds.size(), new Object[0]));
                }
            }
        });
        RecyclerListView recyclerListView2 = this.chatListView;
        Adapter adapter = new Adapter();
        this.adapter = adapter;
        recyclerListView2.setAdapter(adapter);
        this.chatListView.setPadding(0, AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f));
        AnonymousClass8 anonymousClass8 = new AnonymousClass8(context, 1000, 1, true, forwardingMessagesParams);
        this.chatLayoutManager = anonymousClass8;
        anonymousClass8.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.ForwardingPreviewView.9
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i4) {
                if (i4 < 0 || i4 >= forwardingMessagesParams.previewMessages.size()) {
                    return 1000;
                }
                MessageObject messageObject = forwardingMessagesParams.previewMessages.get(i4);
                MessageObject.GroupedMessages validGroupedMessage = ForwardingPreviewView.this.getValidGroupedMessage(messageObject);
                if (validGroupedMessage != null) {
                    return validGroupedMessage.positions.get(messageObject).spanSize;
                }
                return 1000;
            }
        });
        this.chatListView.setClipToPadding(false);
        this.chatListView.setLayoutManager(this.chatLayoutManager);
        this.chatListView.addItemDecoration(new RecyclerView.ItemDecoration(this) { // from class: org.telegram.ui.Components.ForwardingPreviewView.10
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(android.graphics.Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                ChatMessageCell chatMessageCell;
                MessageObject.GroupedMessages currentMessagesGroup;
                MessageObject.GroupedMessagePosition currentPosition;
                int i4 = 0;
                rect.bottom = 0;
                if (!(view instanceof ChatMessageCell) || (currentMessagesGroup = (chatMessageCell = (ChatMessageCell) view).getCurrentMessagesGroup()) == null || (currentPosition = chatMessageCell.getCurrentPosition()) == null || currentPosition.siblingHeights == null) {
                    return;
                }
                android.graphics.Point point = AndroidUtilities.displaySize;
                float max = Math.max(point.x, point.y) * 0.5f;
                int extraInsetHeight = chatMessageCell.getExtraInsetHeight();
                int i5 = 0;
                while (true) {
                    if (i5 >= currentPosition.siblingHeights.length) {
                        break;
                    }
                    extraInsetHeight += (int) Math.ceil(r3[i5] * max);
                    i5++;
                }
                int round = extraInsetHeight + ((currentPosition.maxY - currentPosition.minY) * Math.round(AndroidUtilities.density * 7.0f));
                int size = currentMessagesGroup.posArray.size();
                while (true) {
                    if (i4 < size) {
                        MessageObject.GroupedMessagePosition groupedMessagePosition = currentMessagesGroup.posArray.get(i4);
                        byte b = groupedMessagePosition.minY;
                        byte b2 = currentPosition.minY;
                        if (b == b2 && ((groupedMessagePosition.minX != currentPosition.minX || groupedMessagePosition.maxX != currentPosition.maxX || b != b2 || groupedMessagePosition.maxY != currentPosition.maxY) && b == b2)) {
                            round -= ((int) Math.ceil(max * groupedMessagePosition.ph)) - AndroidUtilities.dp(4.0f);
                            break;
                        }
                        i4++;
                    } else {
                        break;
                    }
                }
                rect.bottom = -round;
            }
        });
        this.chatPreviewContainer.addView(this.chatListView);
        addView(this.chatPreviewContainer, LayoutHelper.createFrame(-1, 400.0f, 0, 8.0f, 0.0f, 8.0f, 0.0f));
        this.chatPreviewContainer.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
        ScrollView scrollView = new ScrollView(context);
        this.menuScrollView = scrollView;
        addView(scrollView, LayoutHelper.createFrame(-2, -2.0f));
        LinearLayout linearLayout = new LinearLayout(context);
        this.menuContainer = linearLayout;
        linearLayout.setOrientation(1);
        this.menuScrollView.addView(this.menuContainer);
        LinearLayout linearLayout2 = new LinearLayout(context);
        this.buttonsLayout = linearLayout2;
        linearLayout2.setOrientation(1);
        Resources resources = getContext().getResources();
        int i4 = R.drawable.popup_fixed_alert;
        Drawable mutate = resources.getDrawable(i4).mutate();
        int i5 = Theme.key_dialogBackground;
        mutate.setColorFilter(new PorterDuffColorFilter(getThemedColor(i5), PorterDuff.Mode.MULTIPLY));
        this.buttonsLayout.setBackground(mutate);
        this.menuContainer.addView(this.buttonsLayout, LayoutHelper.createFrame(-1, -2.0f));
        ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(context, true, true, false, (Theme.ResourcesProvider) resourcesDelegate);
        this.showSendersNameView = actionBarMenuSubItem;
        this.buttonsLayout.addView(actionBarMenuSubItem, LayoutHelper.createFrame(-1, 48.0f));
        ActionBarMenuSubItem actionBarMenuSubItem2 = this.showSendersNameView;
        if (this.forwardingMessagesParams.multiplyUsers) {
            i2 = R.string.ShowSenderNames;
            str = "ShowSenderNames";
        } else {
            i2 = R.string.ShowSendersName;
            str = "ShowSendersName";
        }
        actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString(str, i2), 0);
        this.showSendersNameView.setChecked(true);
        ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(context, true, false, !forwardingMessagesParams.hasCaption, (Theme.ResourcesProvider) resourcesDelegate);
        this.hideSendersNameView = actionBarMenuSubItem3;
        this.buttonsLayout.addView(actionBarMenuSubItem3, LayoutHelper.createFrame(-1, 48.0f));
        ActionBarMenuSubItem actionBarMenuSubItem4 = this.hideSendersNameView;
        if (this.forwardingMessagesParams.multiplyUsers) {
            i3 = R.string.HideSenderNames;
            str2 = "HideSenderNames";
        } else {
            i3 = R.string.HideSendersName;
            str2 = "HideSendersName";
        }
        actionBarMenuSubItem4.setTextAndIcon(LocaleController.getString(str2, i3), 0);
        this.hideSendersNameView.setChecked(false);
        if (this.forwardingMessagesParams.hasCaption) {
            View view = new View(this, context) { // from class: org.telegram.ui.Components.ForwardingPreviewView.11
                @Override // android.view.View
                protected void onMeasure(int i6, int i7) {
                    super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec(2, 1073741824));
                }
            };
            view.setBackgroundColor(getThemedColor(Theme.key_divider));
            this.buttonsLayout.addView(view, LayoutHelper.createFrame(-1, -2.0f));
            ActionBarMenuSubItem actionBarMenuSubItem5 = new ActionBarMenuSubItem(context, true, false, false, (Theme.ResourcesProvider) resourcesDelegate);
            this.showCaptionView = actionBarMenuSubItem5;
            this.buttonsLayout.addView(actionBarMenuSubItem5, LayoutHelper.createFrame(-1, 48.0f));
            this.showCaptionView.setTextAndIcon(LocaleController.getString("ShowCaption", R.string.ShowCaption), 0);
            this.showCaptionView.setChecked(true);
            ActionBarMenuSubItem actionBarMenuSubItem6 = new ActionBarMenuSubItem(context, true, false, true, (Theme.ResourcesProvider) resourcesDelegate);
            this.hideCaptionView = actionBarMenuSubItem6;
            this.buttonsLayout.addView(actionBarMenuSubItem6, LayoutHelper.createFrame(-1, 48.0f));
            this.hideCaptionView.setTextAndIcon(LocaleController.getString("HideCaption", R.string.HideCaption), 0);
            this.hideCaptionView.setChecked(false);
        }
        LinearLayout linearLayout3 = new LinearLayout(context);
        this.buttonsLayout2 = linearLayout3;
        linearLayout3.setOrientation(1);
        Drawable mutate2 = getContext().getResources().getDrawable(i4).mutate();
        mutate2.setColorFilter(new PorterDuffColorFilter(getThemedColor(i5), PorterDuff.Mode.MULTIPLY));
        this.buttonsLayout2.setBackground(mutate2);
        this.menuContainer.addView(this.buttonsLayout2, LayoutHelper.createFrame(-1, -2.0f, 0, 0.0f, this.forwardingMessagesParams.hasSenders ? -8.0f : 0.0f, 0.0f, 0.0f));
        ActionBarMenuSubItem actionBarMenuSubItem7 = new ActionBarMenuSubItem(context, true, false, (Theme.ResourcesProvider) resourcesDelegate);
        this.changeRecipientView = actionBarMenuSubItem7;
        this.buttonsLayout2.addView(actionBarMenuSubItem7, LayoutHelper.createFrame(-1, 48.0f));
        this.changeRecipientView.setTextAndIcon(LocaleController.getString("ChangeRecipient", R.string.ChangeRecipient), R.drawable.msg_forward_replace);
        ActionBarMenuSubItem actionBarMenuSubItem8 = new ActionBarMenuSubItem(context, false, true, (Theme.ResourcesProvider) resourcesDelegate);
        this.sendMessagesView = actionBarMenuSubItem8;
        this.buttonsLayout2.addView(actionBarMenuSubItem8, LayoutHelper.createFrame(-1, 48.0f));
        this.sendMessagesView.setTextAndIcon(LocaleController.getString("ForwardSendMessages", R.string.ForwardSendMessages), R.drawable.msg_send);
        if (this.forwardingMessagesParams.hasSenders) {
            this.actionItems.add(this.showSendersNameView);
            this.actionItems.add(this.hideSendersNameView);
            if (forwardingMessagesParams.hasCaption) {
                this.actionItems.add(this.showCaptionView);
                this.actionItems.add(this.hideCaptionView);
            }
        }
        this.actionItems.add(this.changeRecipientView);
        this.actionItems.add(this.sendMessagesView);
        this.showSendersNameView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ForwardingPreviewView.this.lambda$new$0(forwardingMessagesParams, view2);
            }
        });
        this.hideSendersNameView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ForwardingPreviewView.this.lambda$new$1(forwardingMessagesParams, view2);
            }
        });
        if (forwardingMessagesParams.hasCaption) {
            this.showCaptionView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ForwardingPreviewView.this.lambda$new$2(forwardingMessagesParams, view2);
                }
            });
            this.hideCaptionView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ForwardingPreviewView.this.lambda$new$3(forwardingMessagesParams, view2);
                }
            });
        }
        this.showSendersNameView.setChecked(!forwardingMessagesParams.hideForwardSendersName);
        this.hideSendersNameView.setChecked(forwardingMessagesParams.hideForwardSendersName);
        if (forwardingMessagesParams.hasCaption) {
            this.showCaptionView.setChecked(!forwardingMessagesParams.hideCaption);
            this.hideCaptionView.setChecked(forwardingMessagesParams.hideCaption);
        }
        if (!forwardingMessagesParams.hasSenders) {
            this.buttonsLayout.setVisibility(8);
        }
        this.sendMessagesView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ForwardingPreviewView.this.lambda$new$4(view2);
            }
        });
        this.changeRecipientView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ForwardingPreviewView.this.lambda$new$5(view2);
            }
        });
        updateMessages();
        updateSubtitle();
        this.actionBar.setTitle(LocaleController.formatPluralString("PreviewForwardMessagesCount", forwardingMessagesParams.selectedIds.size(), new Object[0]));
        this.menuScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                boolean lambda$new$6;
                lambda$new$6 = ForwardingPreviewView.this.lambda$new$6(view2, motionEvent);
                return lambda$new$6;
            }
        });
        setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                boolean lambda$new$7;
                lambda$new$7 = ForwardingPreviewView.this.lambda$new$7(view2, motionEvent);
                return lambda$new$7;
            }
        });
        this.showing = true;
        setAlpha(0.0f);
        setScaleX(0.95f);
        setScaleY(0.95f);
        animate().alpha(1.0f).scaleX(1.0f).setDuration(250L).setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR).scaleY(1.0f);
        updateColors();
    }

    /* renamed from: org.telegram.ui.Components.ForwardingPreviewView$5, reason: invalid class name */
    class AnonymousClass5 extends ChatListItemAnimator {
        Runnable finishRunnable;
        int scrollAnimationIndex;
        final /* synthetic */ int val$currentAccount;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(ChatActivity chatActivity, RecyclerListView recyclerListView, Theme.ResourcesProvider resourcesProvider, int i) {
            super(chatActivity, recyclerListView, resourcesProvider);
            this.val$currentAccount = i;
            this.scrollAnimationIndex = -1;
        }

        @Override // androidx.recyclerview.widget.ChatListItemAnimator
        public void onAnimationStart() {
            super.onAnimationStart();
            AndroidUtilities.cancelRunOnUIThread(ForwardingPreviewView.this.changeBoundsRunnable);
            ForwardingPreviewView.this.changeBoundsRunnable.run();
            if (this.scrollAnimationIndex == -1) {
                this.scrollAnimationIndex = NotificationCenter.getInstance(this.val$currentAccount).setAnimationInProgress(this.scrollAnimationIndex, null, false);
            }
            Runnable runnable = this.finishRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
                this.finishRunnable = null;
            }
        }

        @Override // androidx.recyclerview.widget.ChatListItemAnimator, androidx.recyclerview.widget.DefaultItemAnimator
        protected void onAllAnimationsDone() {
            super.onAllAnimationsDone();
            Runnable runnable = this.finishRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            final int i = this.val$currentAccount;
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.ForwardingPreviewView$5$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ForwardingPreviewView.AnonymousClass5.this.lambda$onAllAnimationsDone$0(i);
                }
            };
            this.finishRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2);
            ForwardingPreviewView forwardingPreviewView = ForwardingPreviewView.this;
            if (forwardingPreviewView.updateAfterAnimations) {
                forwardingPreviewView.updateAfterAnimations = false;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ForwardingPreviewView$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ForwardingPreviewView.AnonymousClass5.this.lambda$onAllAnimationsDone$1();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAllAnimationsDone$0(int i) {
            if (this.scrollAnimationIndex != -1) {
                NotificationCenter.getInstance(i).onAnimationFinish(this.scrollAnimationIndex);
                this.scrollAnimationIndex = -1;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAllAnimationsDone$1() {
            ForwardingPreviewView.this.updateMessages();
        }

        @Override // androidx.recyclerview.widget.ChatListItemAnimator, androidx.recyclerview.widget.DefaultItemAnimator, androidx.recyclerview.widget.RecyclerView.ItemAnimator
        public void endAnimations() {
            super.endAnimations();
            Runnable runnable = this.finishRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            final int i = this.val$currentAccount;
            Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.ForwardingPreviewView$5$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ForwardingPreviewView.AnonymousClass5.this.lambda$endAnimations$2(i);
                }
            };
            this.finishRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$endAnimations$2(int i) {
            if (this.scrollAnimationIndex != -1) {
                NotificationCenter.getInstance(i).onAnimationFinish(this.scrollAnimationIndex);
                this.scrollAnimationIndex = -1;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ForwardingPreviewView$8, reason: invalid class name */
    class AnonymousClass8 extends GridLayoutManagerFixed {
        final /* synthetic */ ForwardingMessagesParams val$params;

        @Override // androidx.recyclerview.widget.GridLayoutManagerFixed
        public boolean shouldLayoutChildFromOpositeSide(View view) {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass8(Context context, int i, int i2, boolean z, ForwardingMessagesParams forwardingMessagesParams) {
            super(context, i, i2, z);
            this.val$params = forwardingMessagesParams;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManagerFixed
        protected boolean hasSiblingChild(int i) {
            byte b;
            MessageObject messageObject = this.val$params.previewMessages.get(i);
            MessageObject.GroupedMessages validGroupedMessage = ForwardingPreviewView.this.getValidGroupedMessage(messageObject);
            if (validGroupedMessage != null) {
                MessageObject.GroupedMessagePosition groupedMessagePosition = validGroupedMessage.positions.get(messageObject);
                if (groupedMessagePosition.minX != groupedMessagePosition.maxX && (b = groupedMessagePosition.minY) == groupedMessagePosition.maxY && b != 0) {
                    int size = validGroupedMessage.posArray.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        MessageObject.GroupedMessagePosition groupedMessagePosition2 = validGroupedMessage.posArray.get(i2);
                        if (groupedMessagePosition2 != groupedMessagePosition) {
                            byte b2 = groupedMessagePosition2.minY;
                            byte b3 = groupedMessagePosition.minY;
                            if (b2 <= b3 && groupedMessagePosition2.maxY >= b3) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            if (BuildVars.DEBUG_PRIVATE_VERSION) {
                super.onLayoutChildren(recycler, state);
                return;
            }
            try {
                super.onLayoutChildren(recycler, state);
            } catch (Exception e) {
                FileLog.e(e);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ForwardingPreviewView$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ForwardingPreviewView.AnonymousClass8.this.lambda$onLayoutChildren$0();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLayoutChildren$0() {
            ForwardingPreviewView.this.adapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ForwardingMessagesParams forwardingMessagesParams, View view) {
        if (forwardingMessagesParams.hideForwardSendersName) {
            this.returnSendersNames = false;
            this.showSendersNameView.setChecked(true);
            this.hideSendersNameView.setChecked(false);
            ActionBarMenuSubItem actionBarMenuSubItem = this.showCaptionView;
            if (actionBarMenuSubItem != null) {
                actionBarMenuSubItem.setChecked(true);
                this.hideCaptionView.setChecked(false);
            }
            forwardingMessagesParams.hideForwardSendersName = false;
            forwardingMessagesParams.hideCaption = false;
            updateMessages();
            updateSubtitle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ForwardingMessagesParams forwardingMessagesParams, View view) {
        if (forwardingMessagesParams.hideForwardSendersName) {
            return;
        }
        this.returnSendersNames = false;
        this.showSendersNameView.setChecked(false);
        this.hideSendersNameView.setChecked(true);
        forwardingMessagesParams.hideForwardSendersName = true;
        updateMessages();
        updateSubtitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(ForwardingMessagesParams forwardingMessagesParams, View view) {
        if (forwardingMessagesParams.hideCaption) {
            if (this.returnSendersNames) {
                forwardingMessagesParams.hideForwardSendersName = false;
            }
            this.returnSendersNames = false;
            this.showCaptionView.setChecked(true);
            this.hideCaptionView.setChecked(false);
            this.showSendersNameView.setChecked(true ^ forwardingMessagesParams.hideForwardSendersName);
            this.hideSendersNameView.setChecked(forwardingMessagesParams.hideForwardSendersName);
            forwardingMessagesParams.hideCaption = false;
            updateMessages();
            updateSubtitle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(ForwardingMessagesParams forwardingMessagesParams, View view) {
        if (forwardingMessagesParams.hideCaption) {
            return;
        }
        this.showCaptionView.setChecked(false);
        this.hideCaptionView.setChecked(true);
        this.showSendersNameView.setChecked(false);
        this.hideSendersNameView.setChecked(true);
        if (!forwardingMessagesParams.hideForwardSendersName) {
            forwardingMessagesParams.hideForwardSendersName = true;
            this.returnSendersNames = true;
        }
        forwardingMessagesParams.hideCaption = true;
        updateMessages();
        updateSubtitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view) {
        didSendPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        selectAnotherChat();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$6(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            dismiss(true);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$7(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            dismiss(true);
        }
        return true;
    }

    private void updateSubtitle() {
        ForwardingMessagesParams forwardingMessagesParams = this.forwardingMessagesParams;
        if (!forwardingMessagesParams.hasSenders) {
            if (forwardingMessagesParams.willSeeSenders) {
                TLRPC$User tLRPC$User = this.currentUser;
                if (tLRPC$User != null) {
                    this.actionBar.setSubtitle(LocaleController.formatString("ForwardPreviewSendersNameVisible", R.string.ForwardPreviewSendersNameVisible, ContactsController.formatName(tLRPC$User.first_name, tLRPC$User.last_name)));
                    return;
                } else if (ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) {
                    this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameVisibleChannel", R.string.ForwardPreviewSendersNameVisibleChannel));
                    return;
                } else {
                    this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameVisibleGroup", R.string.ForwardPreviewSendersNameVisibleGroup));
                    return;
                }
            }
            TLRPC$User tLRPC$User2 = this.currentUser;
            if (tLRPC$User2 != null) {
                this.actionBar.setSubtitle(LocaleController.formatString("ForwardPreviewSendersNameVisible", R.string.ForwardPreviewSendersNameVisible, ContactsController.formatName(tLRPC$User2.first_name, tLRPC$User2.last_name)));
                return;
            } else if (ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) {
                this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameHiddenChannel", R.string.ForwardPreviewSendersNameHiddenChannel));
                return;
            } else {
                this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameHiddenGroup", R.string.ForwardPreviewSendersNameHiddenGroup));
                return;
            }
        }
        if (!forwardingMessagesParams.hideForwardSendersName) {
            TLRPC$User tLRPC$User3 = this.currentUser;
            if (tLRPC$User3 != null) {
                this.actionBar.setSubtitle(LocaleController.formatString("ForwardPreviewSendersNameVisible", R.string.ForwardPreviewSendersNameVisible, ContactsController.formatName(tLRPC$User3.first_name, tLRPC$User3.last_name)));
                return;
            } else if (ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) {
                this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameVisibleChannel", R.string.ForwardPreviewSendersNameVisibleChannel));
                return;
            } else {
                this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameVisibleGroup", R.string.ForwardPreviewSendersNameVisibleGroup));
                return;
            }
        }
        TLRPC$User tLRPC$User4 = this.currentUser;
        if (tLRPC$User4 != null) {
            this.actionBar.setSubtitle(LocaleController.formatString("ForwardPreviewSendersNameHidden", R.string.ForwardPreviewSendersNameHidden, ContactsController.formatName(tLRPC$User4.first_name, tLRPC$User4.last_name)));
        } else if (ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) {
            this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameHiddenChannel", R.string.ForwardPreviewSendersNameHiddenChannel));
        } else {
            this.actionBar.setSubtitle(LocaleController.getString("ForwardPreviewSendersNameHiddenGroup", R.string.ForwardPreviewSendersNameHiddenGroup));
        }
    }

    public void dismiss(boolean z) {
        if (this.showing) {
            this.showing = false;
            animate().alpha(0.0f).scaleX(0.95f).scaleY(0.95f).setDuration(250L).setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ForwardingPreviewView.12
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (ForwardingPreviewView.this.getParent() != null) {
                        ((ViewGroup) ForwardingPreviewView.this.getParent()).removeView(ForwardingPreviewView.this);
                    }
                }
            });
            onDismiss(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMessages() {
        if (this.itemAnimator.isRunning()) {
            this.updateAfterAnimations = true;
            return;
        }
        for (int i = 0; i < this.forwardingMessagesParams.previewMessages.size(); i++) {
            MessageObject messageObject = this.forwardingMessagesParams.previewMessages.get(i);
            messageObject.forceUpdate = true;
            messageObject.sendAsPeer = this.sendAsPeer;
            ForwardingMessagesParams forwardingMessagesParams = this.forwardingMessagesParams;
            if (!forwardingMessagesParams.hideForwardSendersName) {
                messageObject.messageOwner.flags |= 4;
                messageObject.hideSendersName = false;
            } else {
                messageObject.messageOwner.flags &= -5;
                messageObject.hideSendersName = true;
            }
            if (forwardingMessagesParams.hideCaption) {
                messageObject.caption = null;
            } else {
                messageObject.generateCaption();
            }
            if (messageObject.isPoll()) {
                ForwardingMessagesParams.PreviewMediaPoll previewMediaPoll = (ForwardingMessagesParams.PreviewMediaPoll) messageObject.messageOwner.media;
                previewMediaPoll.results.total_voters = this.forwardingMessagesParams.hideCaption ? 0 : previewMediaPoll.totalVotersCached;
            }
        }
        for (int i2 = 0; i2 < this.forwardingMessagesParams.pollChoosenAnswers.size(); i2++) {
            this.forwardingMessagesParams.pollChoosenAnswers.get(i2).chosen = !this.forwardingMessagesParams.hideForwardSendersName;
        }
        for (int i3 = 0; i3 < this.forwardingMessagesParams.groupedMessagesMap.size(); i3++) {
            this.itemAnimator.groupWillChanged(this.forwardingMessagesParams.groupedMessagesMap.valueAt(i3));
        }
        this.adapter.notifyItemRangeChanged(0, this.forwardingMessagesParams.previewMessages.size());
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        this.isLandscapeMode = View.MeasureSpec.getSize(i) > View.MeasureSpec.getSize(i2);
        int size = View.MeasureSpec.getSize(i);
        if (this.isLandscapeMode) {
            size = (int) (View.MeasureSpec.getSize(i) * 0.38f);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.actionItems.size(); i4++) {
            this.actionItems.get(i4).measure(View.MeasureSpec.makeMeasureSpec(size, 0), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0));
            if (this.actionItems.get(i4).getMeasuredWidth() > i3) {
                i3 = this.actionItems.get(i4).getMeasuredWidth();
            }
        }
        this.buttonsLayout.getBackground().getPadding(this.rect);
        android.graphics.Rect rect = this.rect;
        int i5 = i3 + rect.left + rect.right;
        this.buttonsLayout.getLayoutParams().width = i5;
        this.buttonsLayout2.getLayoutParams().width = i5;
        this.buttonsLayout.measure(i, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0));
        this.buttonsLayout2.measure(i, View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 0));
        ((ViewGroup.MarginLayoutParams) this.chatListView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight();
        if (this.isLandscapeMode) {
            this.chatPreviewContainer.getLayoutParams().height = -1;
            ((ViewGroup.MarginLayoutParams) this.chatPreviewContainer.getLayoutParams()).topMargin = AndroidUtilities.dp(8.0f);
            ((ViewGroup.MarginLayoutParams) this.chatPreviewContainer.getLayoutParams()).bottomMargin = AndroidUtilities.dp(8.0f);
            this.chatPreviewContainer.getLayoutParams().width = (int) Math.min(View.MeasureSpec.getSize(i), Math.max(AndroidUtilities.dp(340.0f), View.MeasureSpec.getSize(i) * 0.6f));
            this.menuScrollView.getLayoutParams().height = -1;
        } else {
            ((ViewGroup.MarginLayoutParams) this.chatPreviewContainer.getLayoutParams()).topMargin = 0;
            ((ViewGroup.MarginLayoutParams) this.chatPreviewContainer.getLayoutParams()).bottomMargin = 0;
            this.chatPreviewContainer.getLayoutParams().height = ((View.MeasureSpec.getSize(i2) - AndroidUtilities.dp(6.0f)) - this.buttonsLayout.getMeasuredHeight()) - this.buttonsLayout2.getMeasuredHeight();
            if (this.chatPreviewContainer.getLayoutParams().height < View.MeasureSpec.getSize(i2) * 0.5f) {
                this.chatPreviewContainer.getLayoutParams().height = (int) (View.MeasureSpec.getSize(i2) * 0.5f);
            }
            this.chatPreviewContainer.getLayoutParams().width = -1;
            this.menuScrollView.getLayoutParams().height = View.MeasureSpec.getSize(i2) - this.chatPreviewContainer.getLayoutParams().height;
        }
        int size2 = (View.MeasureSpec.getSize(i) + View.MeasureSpec.getSize(i2)) << 16;
        if (this.lastSize != size2) {
            for (int i6 = 0; i6 < this.forwardingMessagesParams.previewMessages.size(); i6++) {
                if (this.isLandscapeMode) {
                    this.forwardingMessagesParams.previewMessages.get(i6).parentWidth = this.chatPreviewContainer.getLayoutParams().width;
                } else {
                    this.forwardingMessagesParams.previewMessages.get(i6).parentWidth = View.MeasureSpec.getSize(i) - AndroidUtilities.dp(16.0f);
                }
                this.forwardingMessagesParams.previewMessages.get(i6).resetLayout();
                this.forwardingMessagesParams.previewMessages.get(i6).forceUpdate = true;
                Adapter adapter = this.adapter;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
            this.firstLayout = true;
        }
        this.lastSize = size2;
        super.onMeasure(i, i2);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updatePositions();
        this.firstLayout = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePositions() {
        final int i = this.chatTopOffset;
        final float f = this.yOffset;
        if (!this.isLandscapeMode) {
            if (this.chatListView.getChildCount() == 0 || this.chatListView.getChildCount() > this.forwardingMessagesParams.previewMessages.size()) {
                this.chatTopOffset = 0;
            } else {
                int top = this.chatListView.getChildAt(0).getTop();
                for (int i2 = 1; i2 < this.chatListView.getChildCount(); i2++) {
                    if (this.chatListView.getChildAt(i2).getTop() < top) {
                        top = this.chatListView.getChildAt(i2).getTop();
                    }
                }
                int dp = top - AndroidUtilities.dp(4.0f);
                if (dp < 0) {
                    this.chatTopOffset = 0;
                } else {
                    this.chatTopOffset = dp;
                }
            }
            float dp2 = (AndroidUtilities.dp(8.0f) + (((getMeasuredHeight() - AndroidUtilities.dp(16.0f)) - (((this.buttonsLayout.getMeasuredHeight() + this.buttonsLayout2.getMeasuredHeight()) - AndroidUtilities.dp(8.0f)) + (this.chatPreviewContainer.getMeasuredHeight() - this.chatTopOffset))) / 2.0f)) - this.chatTopOffset;
            this.yOffset = dp2;
            if (dp2 > AndroidUtilities.dp(8.0f)) {
                this.yOffset = AndroidUtilities.dp(8.0f);
            }
            this.menuScrollView.setTranslationX(getMeasuredWidth() - this.menuScrollView.getMeasuredWidth());
        } else {
            this.yOffset = 0.0f;
            this.chatTopOffset = 0;
            this.menuScrollView.setTranslationX(this.chatListView.getMeasuredWidth() + AndroidUtilities.dp(8.0f));
        }
        boolean z = this.firstLayout;
        if (z || (this.chatTopOffset == i && this.yOffset == f)) {
            if (z) {
                float f2 = this.yOffset;
                this.currentYOffset = f2;
                int i3 = this.chatTopOffset;
                this.currentTopOffset = i3;
                setOffset(f2, i3);
                return;
            }
            return;
        }
        ValueAnimator valueAnimator = this.offsetsAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.offsetsAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ForwardingPreviewView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ForwardingPreviewView.this.lambda$updatePositions$8(i, f, valueAnimator2);
            }
        });
        this.offsetsAnimator.setDuration(250L);
        this.offsetsAnimator.setInterpolator(ChatListItemAnimator.DEFAULT_INTERPOLATOR);
        this.offsetsAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ForwardingPreviewView.13
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ForwardingPreviewView forwardingPreviewView = ForwardingPreviewView.this;
                forwardingPreviewView.offsetsAnimator = null;
                forwardingPreviewView.setOffset(forwardingPreviewView.yOffset, forwardingPreviewView.chatTopOffset);
            }
        });
        AndroidUtilities.runOnUIThread(this.changeBoundsRunnable, 50L);
        this.currentTopOffset = i;
        this.currentYOffset = f;
        setOffset(f, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePositions$8(int i, float f, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float f2 = 1.0f - floatValue;
        int i2 = (int) ((i * f2) + (this.chatTopOffset * floatValue));
        this.currentTopOffset = i2;
        float f3 = (f * f2) + (this.yOffset * floatValue);
        this.currentYOffset = f3;
        setOffset(f3, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setOffset(float f, int i) {
        if (this.isLandscapeMode) {
            this.actionBar.setTranslationY(0.0f);
            if (Build.VERSION.SDK_INT >= 21) {
                this.chatPreviewContainer.invalidateOutline();
            }
            this.chatPreviewContainer.setTranslationY(0.0f);
            this.menuScrollView.setTranslationY(0.0f);
            return;
        }
        this.actionBar.setTranslationY(i);
        if (Build.VERSION.SDK_INT >= 21) {
            this.chatPreviewContainer.invalidateOutline();
        }
        this.chatPreviewContainer.setTranslationY(f);
        this.menuScrollView.setTranslationY((f + this.chatPreviewContainer.getMeasuredHeight()) - AndroidUtilities.dp(2.0f));
    }

    public boolean isShowing() {
        return this.showing;
    }

    private class Adapter extends RecyclerView.Adapter {
        private Adapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new RecyclerListView.Holder(new ChatMessageCell(viewGroup.getContext(), false, ForwardingPreviewView.this.resourcesProvider));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ChatMessageCell chatMessageCell = (ChatMessageCell) viewHolder.itemView;
            chatMessageCell.setInvalidateSpoilersParent(ForwardingPreviewView.this.forwardingMessagesParams.hasSpoilers);
            chatMessageCell.setParentViewSize(ForwardingPreviewView.this.chatListView.getMeasuredWidth(), ForwardingPreviewView.this.chatListView.getMeasuredHeight());
            int id = chatMessageCell.getMessageObject() != null ? chatMessageCell.getMessageObject().getId() : 0;
            MessageObject messageObject = ForwardingPreviewView.this.forwardingMessagesParams.previewMessages.get(i);
            ForwardingMessagesParams forwardingMessagesParams = ForwardingPreviewView.this.forwardingMessagesParams;
            chatMessageCell.setMessageObject(messageObject, forwardingMessagesParams.groupedMessagesMap.get(forwardingMessagesParams.previewMessages.get(i).getGroupId()), true, true);
            chatMessageCell.setDelegate(new ChatMessageCell.ChatMessageCellDelegate(this) { // from class: org.telegram.ui.Components.ForwardingPreviewView.Adapter.1
                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean canDrawOutboundsContent() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$canDrawOutboundsContent(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean canPerformActions() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$canPerformActions(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didLongPress(ChatMessageCell chatMessageCell2, float f, float f2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didLongPress(this, chatMessageCell2, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didLongPressBotButton(ChatMessageCell chatMessageCell2, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didLongPressBotButton(this, chatMessageCell2, tLRPC$KeyboardButton);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean didLongPressChannelAvatar(ChatMessageCell chatMessageCell2, TLRPC$Chat tLRPC$Chat, int i2, float f, float f2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$didLongPressChannelAvatar(this, chatMessageCell2, tLRPC$Chat, i2, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean didLongPressUserAvatar(ChatMessageCell chatMessageCell2, TLRPC$User tLRPC$User, float f, float f2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$didLongPressUserAvatar(this, chatMessageCell2, tLRPC$User, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean didPressAnimatedEmoji(ChatMessageCell chatMessageCell2, AnimatedEmojiSpan animatedEmojiSpan) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressAnimatedEmoji(this, chatMessageCell2, animatedEmojiSpan);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressBotButton(ChatMessageCell chatMessageCell2, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressBotButton(this, chatMessageCell2, tLRPC$KeyboardButton);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressCancelSendButton(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressCancelSendButton(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressChannelAvatar(ChatMessageCell chatMessageCell2, TLRPC$Chat tLRPC$Chat, int i2, float f, float f2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressChannelAvatar(this, chatMessageCell2, tLRPC$Chat, i2, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressCommentButton(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressCommentButton(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressExtendedMediaPreview(ChatMessageCell chatMessageCell2, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressExtendedMediaPreview(this, chatMessageCell2, tLRPC$KeyboardButton);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressHiddenForward(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressHiddenForward(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressHint(ChatMessageCell chatMessageCell2, int i2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressHint(this, chatMessageCell2, i2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressImage(ChatMessageCell chatMessageCell2, float f, float f2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressImage(this, chatMessageCell2, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressInstantButton(ChatMessageCell chatMessageCell2, int i2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressInstantButton(this, chatMessageCell2, i2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressOther(ChatMessageCell chatMessageCell2, float f, float f2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressOther(this, chatMessageCell2, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressReaction(ChatMessageCell chatMessageCell2, TLRPC$ReactionCount tLRPC$ReactionCount, boolean z) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressReaction(this, chatMessageCell2, tLRPC$ReactionCount, z);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressReplyMessage(ChatMessageCell chatMessageCell2, int i2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressReplyMessage(this, chatMessageCell2, i2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressSideButton(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressSideButton(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressTime(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressTime(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressTopicButton(ChatMessageCell chatMessageCell2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressTopicButton(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressUrl(ChatMessageCell chatMessageCell2, CharacterStyle characterStyle, boolean z) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressUrl(this, chatMessageCell2, characterStyle, z);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressUserAvatar(ChatMessageCell chatMessageCell2, TLRPC$User tLRPC$User, float f, float f2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressUserAvatar(this, chatMessageCell2, tLRPC$User, f, f2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressViaBot(ChatMessageCell chatMessageCell2, String str) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressViaBot(this, chatMessageCell2, str);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressViaBotNotInline(ChatMessageCell chatMessageCell2, long j) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressViaBotNotInline(this, chatMessageCell2, j);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didPressVoteButtons(ChatMessageCell chatMessageCell2, ArrayList arrayList, int i2, int i3, int i4) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didPressVoteButtons(this, chatMessageCell2, arrayList, i2, i3, i4);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void didStartVideoStream(MessageObject messageObject2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$didStartVideoStream(this, messageObject2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ String getAdminRank(long j) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$getAdminRank(this, j);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ PinchToZoomHelper getPinchToZoomHelper() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$getPinchToZoomHelper(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ String getProgressLoadingBotButtonUrl(ChatMessageCell chatMessageCell2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$getProgressLoadingBotButtonUrl(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ CharacterStyle getProgressLoadingLink(ChatMessageCell chatMessageCell2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$getProgressLoadingLink(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ TextSelectionHelper.ChatListTextSelectionHelper getTextSelectionHelper() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$getTextSelectionHelper(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean hasSelectedMessages() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$hasSelectedMessages(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void invalidateBlur() {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$invalidateBlur(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean isLandscape() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$isLandscape(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean isProgressLoading(ChatMessageCell chatMessageCell2, int i2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$isProgressLoading(this, chatMessageCell2, i2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean isReplyOrSelf() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$isReplyOrSelf(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean keyboardIsOpened() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$keyboardIsOpened(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void needOpenWebView(MessageObject messageObject2, String str, String str2, String str3, String str4, int i2, int i3) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$needOpenWebView(this, messageObject2, str, str2, str3, str4, i2, i3);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean needPlayMessage(MessageObject messageObject2, boolean z) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$needPlayMessage(this, messageObject2, z);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void needReloadPolls() {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$needReloadPolls(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void needShowPremiumBulletin(int i2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$needShowPremiumBulletin(this, i2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean onAccessibilityAction(int i2, Bundle bundle) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$onAccessibilityAction(this, i2, bundle);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void onDiceFinished() {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$onDiceFinished(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void setShouldNotRepeatSticker(MessageObject messageObject2) {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$setShouldNotRepeatSticker(this, messageObject2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean shouldDrawThreadProgress(ChatMessageCell chatMessageCell2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$shouldDrawThreadProgress(this, chatMessageCell2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean shouldRepeatSticker(MessageObject messageObject2) {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$shouldRepeatSticker(this, messageObject2);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ boolean shouldShowTopicButton() {
                    return ChatMessageCell.ChatMessageCellDelegate.CC.$default$shouldShowTopicButton(this);
                }

                @Override // org.telegram.ui.Cells.ChatMessageCell.ChatMessageCellDelegate
                public /* synthetic */ void videoTimerReached() {
                    ChatMessageCell.ChatMessageCellDelegate.CC.$default$videoTimerReached(this);
                }
            });
            if (ForwardingPreviewView.this.forwardingMessagesParams.previewMessages.size() > 1) {
                chatMessageCell.setCheckBoxVisible(true, false);
                boolean z = id == ForwardingPreviewView.this.forwardingMessagesParams.previewMessages.get(i).getId();
                ForwardingMessagesParams forwardingMessagesParams2 = ForwardingPreviewView.this.forwardingMessagesParams;
                boolean z2 = forwardingMessagesParams2.selectedIds.get(forwardingMessagesParams2.previewMessages.get(i).getId(), false);
                chatMessageCell.setChecked(z2, z2, z);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ForwardingPreviewView.this.forwardingMessagesParams.previewMessages.size();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MessageObject.GroupedMessages getValidGroupedMessage(MessageObject messageObject) {
        if (messageObject.getGroupId() == 0) {
            return null;
        }
        MessageObject.GroupedMessages groupedMessages = this.forwardingMessagesParams.groupedMessagesMap.get(messageObject.getGroupId());
        if (groupedMessages == null || (groupedMessages.messages.size() > 1 && groupedMessages.positions.get(messageObject) != null)) {
            return groupedMessages;
        }
        return null;
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }
}
