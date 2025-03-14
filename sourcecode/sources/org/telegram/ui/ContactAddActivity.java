package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.utils.PhotoUtilities;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageActionSuggestProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photos_photo;
import org.telegram.tgnet.TLRPC$TL_photos_uploadContactProfilePhoto;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;
import org.telegram.tgnet.TLRPC$VideoSize;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.BulletinFactory;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.ImageUpdater;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RadialProgressView;
import org.telegram.ui.LNavigation.NavigationExt;

/* loaded from: classes3.dex */
public class ContactAddActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, ImageUpdater.ImageUpdaterDelegate {
    private boolean addContact;
    private TLRPC$FileLocation avatar;
    private AnimatorSet avatarAnimation;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private View avatarOverlay;
    private RadialProgressView avatarProgressView;
    private CheckBoxCell checkBoxCell;
    private ContactAddActivityDelegate delegate;
    private View doneButton;
    private EditTextBoldCursor firstNameField;
    private String firstNameFromCard;
    private ImageUpdater imageUpdater;
    private TextView infoTextView;
    private EditTextBoldCursor lastNameField;
    private String lastNameFromCard;
    private LinearLayout linearLayout;
    private TextView nameTextView;
    private boolean needAddException;
    private BackupImageView oldAvatarView;
    TextCell oldPhotoCell;
    private TextView onlineTextView;
    boolean paused;
    private String phone;
    private int photoSelectedType;
    private int photoSelectedTypeFinal;
    private TLRPC$Photo prevAvatar;
    private Theme.ResourcesProvider resourcesProvider;
    MessageObject suggestPhotoMessageFinal;
    private long user_id;

    public interface ContactAddActivityDelegate {
        void didAddToContacts();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createView$0(View view, MotionEvent motionEvent) {
        return true;
    }

    public ContactAddActivity(Bundle bundle) {
        super(bundle);
        this.imageUpdater = new ImageUpdater(true, 0, true);
    }

    public ContactAddActivity(Bundle bundle, Theme.ResourcesProvider resourcesProvider) {
        super(bundle);
        this.resourcesProvider = resourcesProvider;
        this.imageUpdater = new ImageUpdater(true, 0, true);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public Theme.ResourcesProvider getResourceProvider() {
        return this.resourcesProvider;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.updateInterfaces);
        getNotificationCenter().addObserver(this, NotificationCenter.dialogPhotosLoaded);
        this.user_id = getArguments().getLong("user_id", 0L);
        this.phone = getArguments().getString("phone");
        this.firstNameFromCard = getArguments().getString("first_name_card");
        this.lastNameFromCard = getArguments().getString("last_name_card");
        this.addContact = getArguments().getBoolean("addContact", false);
        this.needAddException = MessagesController.getNotificationsSettings(this.currentAccount).getBoolean("dialog_bar_exception" + this.user_id, false);
        TLRPC$User user = this.user_id != 0 ? getMessagesController().getUser(Long.valueOf(this.user_id)) : null;
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null) {
            imageUpdater.parentFragment = this;
            imageUpdater.setDelegate(this);
        }
        return user != null && super.onFragmentCreate();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.updateInterfaces);
        getNotificationCenter().removeObserver(this, NotificationCenter.dialogPhotosLoaded);
        ImageUpdater imageUpdater = this.imageUpdater;
        if (imageUpdater != null) {
            imageUpdater.clear();
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        String str;
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_avatar_actionBarSelectorBlue, this.resourcesProvider), false);
        this.actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon, this.resourcesProvider), false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.addContact) {
            this.actionBar.setTitle(LocaleController.getString("NewContact", R.string.NewContact));
        } else {
            this.actionBar.setTitle(LocaleController.getString("EditContact", R.string.EditContact));
        }
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.ContactAddActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i != -1) {
                    if (i != 1 || ContactAddActivity.this.firstNameField.getText().length() == 0) {
                        return;
                    }
                    TLRPC$User user = ContactAddActivity.this.getMessagesController().getUser(Long.valueOf(ContactAddActivity.this.user_id));
                    user.first_name = ContactAddActivity.this.firstNameField.getText().toString();
                    user.last_name = ContactAddActivity.this.lastNameField.getText().toString();
                    ContactAddActivity.this.getContactsController().addContact(user, ContactAddActivity.this.checkBoxCell != null && ContactAddActivity.this.checkBoxCell.isChecked());
                    MessagesController.getNotificationsSettings(((BaseFragment) ContactAddActivity.this).currentAccount).edit().putInt("dialog_bar_vis3" + ContactAddActivity.this.user_id, 3).commit();
                    ContactAddActivity.this.getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_NAME));
                    ContactAddActivity.this.getNotificationCenter().postNotificationName(NotificationCenter.peerSettingsDidLoad, Long.valueOf(ContactAddActivity.this.user_id));
                    ContactAddActivity.this.finishFragment();
                    if (ContactAddActivity.this.delegate != null) {
                        ContactAddActivity.this.delegate.didAddToContacts();
                        return;
                    }
                    return;
                }
                ContactAddActivity.this.finishFragment();
            }
        });
        this.doneButton = this.actionBar.createMenu().addItem(1, LocaleController.getString("Done", R.string.Done).toUpperCase());
        this.fragmentView = new ScrollView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        this.linearLayout = linearLayout;
        linearLayout.setOrientation(1);
        ((ScrollView) this.fragmentView).addView(this.linearLayout, LayoutHelper.createScroll(-1, -2, 51));
        this.linearLayout.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$createView$0;
                lambda$createView$0 = ContactAddActivity.lambda$createView$0(view, motionEvent);
                return lambda$createView$0;
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        this.linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 24.0f, 24.0f, 24.0f, 0.0f));
        BackupImageView backupImageView = new BackupImageView(context);
        this.avatarImage = backupImageView;
        backupImageView.setRoundRadius(AndroidUtilities.dp(30.0f));
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(60, 60, (LocaleController.isRTL ? 5 : 3) | 48));
        final Paint paint = new Paint(1);
        paint.setColor(1426063360);
        View view = new View(context) { // from class: org.telegram.ui.ContactAddActivity.2
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                if (ContactAddActivity.this.avatarImage == null || !ContactAddActivity.this.avatarImage.getImageReceiver().hasNotThumb()) {
                    return;
                }
                paint.setAlpha((int) (ContactAddActivity.this.avatarImage.getImageReceiver().getCurrentAlpha() * 85.0f));
                canvas.drawCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, getMeasuredWidth() / 2.0f, paint);
            }
        };
        this.avatarOverlay = view;
        frameLayout.addView(view, LayoutHelper.createFrame(60, 60, (LocaleController.isRTL ? 5 : 3) | 48));
        RadialProgressView radialProgressView = new RadialProgressView(context);
        this.avatarProgressView = radialProgressView;
        radialProgressView.setSize(AndroidUtilities.dp(30.0f));
        this.avatarProgressView.setProgressColor(-1);
        this.avatarProgressView.setNoProgress(false);
        frameLayout.addView(this.avatarProgressView, LayoutHelper.createFrame(60, 60, (LocaleController.isRTL ? 5 : 3) | 48));
        showAvatarProgress(false, false);
        TextView textView = new TextView(context);
        this.nameTextView = textView;
        int i = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(Theme.getColor(i, this.resourcesProvider));
        this.nameTextView.setTextSize(1, 20.0f);
        this.nameTextView.setLines(1);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TextUtils.TruncateAt.END);
        this.nameTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        TextView textView2 = this.nameTextView;
        boolean z = LocaleController.isRTL;
        frameLayout.addView(textView2, LayoutHelper.createFrame(-2, -2.0f, (z ? 5 : 3) | 48, z ? 0.0f : 80.0f, 3.0f, z ? 80.0f : 0.0f, 0.0f));
        TextView textView3 = new TextView(context);
        this.onlineTextView = textView3;
        textView3.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, this.resourcesProvider));
        this.onlineTextView.setTextSize(1, 14.0f);
        this.onlineTextView.setLines(1);
        this.onlineTextView.setMaxLines(1);
        this.onlineTextView.setSingleLine(true);
        this.onlineTextView.setEllipsize(TextUtils.TruncateAt.END);
        this.onlineTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        TextView textView4 = this.onlineTextView;
        boolean z2 = LocaleController.isRTL;
        frameLayout.addView(textView4, LayoutHelper.createFrame(-2, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 0.0f : 80.0f, 32.0f, z2 ? 80.0f : 0.0f, 0.0f));
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context) { // from class: org.telegram.ui.ContactAddActivity.3
            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected Theme.ResourcesProvider getResourcesProvider() {
                return ContactAddActivity.this.resourcesProvider;
            }
        };
        this.firstNameField = editTextBoldCursor;
        editTextBoldCursor.setTextSize(1, 18.0f);
        EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
        int i2 = Theme.key_windowBackgroundWhiteHintText;
        editTextBoldCursor2.setHintTextColor(Theme.getColor(i2, this.resourcesProvider));
        this.firstNameField.setTextColor(Theme.getColor(i, this.resourcesProvider));
        this.firstNameField.setBackgroundDrawable(null);
        EditTextBoldCursor editTextBoldCursor3 = this.firstNameField;
        int i3 = Theme.key_windowBackgroundWhiteInputField;
        int themedColor = getThemedColor(i3);
        int i4 = Theme.key_windowBackgroundWhiteInputFieldActivated;
        int themedColor2 = getThemedColor(i4);
        int i5 = Theme.key_text_RedRegular;
        editTextBoldCursor3.setLineColors(themedColor, themedColor2, getThemedColor(i5));
        this.firstNameField.setMaxLines(1);
        this.firstNameField.setLines(1);
        this.firstNameField.setSingleLine(true);
        this.firstNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.firstNameField.setInputType(49152);
        this.firstNameField.setImeOptions(5);
        this.firstNameField.setHint(LocaleController.getString("FirstName", R.string.FirstName));
        this.firstNameField.setCursorColor(Theme.getColor(i, this.resourcesProvider));
        this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.firstNameField.setCursorWidth(1.5f);
        this.linearLayout.addView(this.firstNameField, LayoutHelper.createLinear(-1, 36, 24.0f, 24.0f, 24.0f, 0.0f));
        this.firstNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView5, int i6, KeyEvent keyEvent) {
                boolean lambda$createView$1;
                lambda$createView$1 = ContactAddActivity.this.lambda$createView$1(textView5, i6, keyEvent);
                return lambda$createView$1;
            }
        });
        this.firstNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.telegram.ui.ContactAddActivity.4
            boolean focused;

            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view2, boolean z3) {
                if (!ContactAddActivity.this.paused && !z3 && this.focused) {
                    FileLog.d("changed");
                }
                this.focused = z3;
            }
        });
        this.firstNameField.setText(this.firstNameFromCard);
        EditTextBoldCursor editTextBoldCursor4 = new EditTextBoldCursor(context) { // from class: org.telegram.ui.ContactAddActivity.5
            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected Theme.ResourcesProvider getResourcesProvider() {
                return ContactAddActivity.this.resourcesProvider;
            }
        };
        this.lastNameField = editTextBoldCursor4;
        editTextBoldCursor4.setTextSize(1, 18.0f);
        this.lastNameField.setHintTextColor(Theme.getColor(i2, this.resourcesProvider));
        this.lastNameField.setTextColor(Theme.getColor(i, this.resourcesProvider));
        this.lastNameField.setBackgroundDrawable(null);
        this.lastNameField.setLineColors(getThemedColor(i3), getThemedColor(i4), getThemedColor(i5));
        this.lastNameField.setMaxLines(1);
        this.lastNameField.setLines(1);
        this.lastNameField.setSingleLine(true);
        this.lastNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.lastNameField.setInputType(49152);
        this.lastNameField.setImeOptions(6);
        this.lastNameField.setHint(LocaleController.getString("LastName", R.string.LastName));
        this.lastNameField.setCursorColor(Theme.getColor(i, this.resourcesProvider));
        this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.lastNameField.setCursorWidth(1.5f);
        this.linearLayout.addView(this.lastNameField, LayoutHelper.createLinear(-1, 36, 24.0f, 16.0f, 24.0f, 0.0f));
        this.lastNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda4
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView5, int i6, KeyEvent keyEvent) {
                boolean lambda$createView$2;
                lambda$createView$2 = ContactAddActivity.this.lambda$createView$2(textView5, i6, keyEvent);
                return lambda$createView$2;
            }
        });
        this.lastNameField.setText(this.lastNameFromCard);
        final TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
        if (user != null && this.firstNameFromCard == null && this.lastNameFromCard == null) {
            if (user.phone == null && (str = this.phone) != null) {
                user.phone = PhoneFormat.stripExceptNumbers(str);
            }
            this.firstNameField.setText(user.first_name);
            EditTextBoldCursor editTextBoldCursor5 = this.firstNameField;
            editTextBoldCursor5.setSelection(editTextBoldCursor5.length());
            this.lastNameField.setText(user.last_name);
        }
        TextView textView5 = new TextView(context);
        this.infoTextView = textView5;
        textView5.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.infoTextView.setTextSize(1, 14.0f);
        this.infoTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        if (this.addContact) {
            if (!this.needAddException || TextUtils.isEmpty(getPhone())) {
                this.linearLayout.addView(this.infoTextView, LayoutHelper.createLinear(-1, -2, 24.0f, 18.0f, 24.0f, 0.0f));
            }
            if (this.needAddException) {
                CheckBoxCell checkBoxCell = new CheckBoxCell(getParentActivity(), 0);
                this.checkBoxCell = checkBoxCell;
                checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                this.checkBoxCell.setText(AndroidUtilities.replaceCharSequence("%1$s", AndroidUtilities.replaceTags(LocaleController.getString("SharePhoneNumberWith", R.string.SharePhoneNumberWith)), Emoji.replaceEmoji(UserObject.getFirstName(user), this.infoTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(12.0f), false)), "", true, false);
                this.checkBoxCell.setPadding(AndroidUtilities.dp(7.0f), 0, AndroidUtilities.dp(7.0f), 0);
                this.checkBoxCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        ContactAddActivity.this.lambda$createView$3(view2);
                    }
                });
                this.linearLayout.addView(this.checkBoxCell, LayoutHelper.createLinear(-1, -2, 0.0f, 10.0f, 0.0f, 0.0f));
            }
        } else {
            this.oldAvatarView = new BackupImageView(context);
            this.oldPhotoCell = new TextCell(context, this.resourcesProvider) { // from class: org.telegram.ui.ContactAddActivity.6
                @Override // org.telegram.ui.Cells.TextCell, android.widget.FrameLayout, android.view.View
                protected void onMeasure(int i6, int i7) {
                    super.onMeasure(i6, i7);
                    ContactAddActivity.this.oldAvatarView.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(30.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(30.0f), 1073741824));
                    ContactAddActivity.this.oldAvatarView.setRoundRadius(AndroidUtilities.dp(30.0f));
                }

                @Override // org.telegram.ui.Cells.TextCell, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z3, int i6, int i7, int i8, int i9) {
                    super.onLayout(z3, i6, i7, i8, i9);
                    int dp = AndroidUtilities.dp(21.0f);
                    int measuredHeight = (getMeasuredHeight() - ContactAddActivity.this.oldAvatarView.getMeasuredHeight()) / 2;
                    ContactAddActivity.this.oldAvatarView.layout(dp, measuredHeight, ContactAddActivity.this.oldAvatarView.getMeasuredWidth() + dp, ContactAddActivity.this.oldAvatarView.getMeasuredHeight() + measuredHeight);
                }
            };
            if (this.avatarDrawable == null) {
                this.avatarDrawable = new AvatarDrawable(user);
            }
            this.oldAvatarView.setForUserOrChat(user.photo, this.avatarDrawable);
            this.oldPhotoCell.addView(this.oldAvatarView, LayoutHelper.createFrame(30, 30.0f, 16, 21.0f, 0.0f, 21.0f, 0.0f));
            this.oldPhotoCell.setText(LocaleController.getString("ResetToOriginalPhoto", R.string.ResetToOriginalPhoto), false);
            this.oldPhotoCell.getImageView().setVisibility(0);
            this.oldPhotoCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.oldPhotoCell.setColors(Theme.key_windowBackgroundWhiteBlueIcon, Theme.key_windowBackgroundWhiteBlueButton);
            this.oldPhotoCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ContactAddActivity.this.lambda$createView$5(context, user, view2);
                }
            });
            this.linearLayout.addView(this.oldPhotoCell, LayoutHelper.createLinear(-1, -2, 0, 0, 0, 0, 0));
            getMessagesController().loadDialogPhotos(this.user_id, 2, 0, true, getClassGuid());
            TLRPC$UserFull userFull = getMessagesController().getUserFull(this.user_id);
            if (userFull != null) {
                TLRPC$Photo tLRPC$Photo = userFull.profile_photo;
                this.prevAvatar = tLRPC$Photo;
                if (tLRPC$Photo == null) {
                    this.prevAvatar = userFull.fallback_photo;
                }
            }
            updateCustomPhotoInfo();
        }
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$1(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 5) {
            return false;
        }
        this.lastNameField.requestFocus();
        EditTextBoldCursor editTextBoldCursor = this.lastNameField;
        editTextBoldCursor.setSelection(editTextBoldCursor.length());
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$2(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        this.doneButton.performClick();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(View view) {
        this.checkBoxCell.setChecked(!r3.isChecked(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(Context context, final TLRPC$User tLRPC$User, View view) {
        AlertsCreator.createSimpleAlert(context, LocaleController.getString("ResetToOriginalPhotoTitle", R.string.ResetToOriginalPhotoTitle), LocaleController.formatString("ResetToOriginalPhotoMessage", R.string.ResetToOriginalPhotoMessage, tLRPC$User.first_name), LocaleController.getString("Reset", R.string.Reset), new Runnable() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ContactAddActivity.this.lambda$createView$4(tLRPC$User);
            }
        }, this.resourcesProvider).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(TLRPC$User tLRPC$User) {
        this.avatar = null;
        sendPhotoChangedRequest(null, null, null, null, null, 0.0d, 2);
        TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
        user.photo.personal = false;
        TLRPC$UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(this.user_id);
        if (userFull != null) {
            userFull.personal_photo = null;
            userFull.flags &= -2097153;
            getMessagesStorage().updateUserInfo(userFull, true);
        }
        TLRPC$Photo tLRPC$Photo = this.prevAvatar;
        if (tLRPC$Photo != null) {
            user.photo.photo_id = tLRPC$Photo.id;
            ArrayList<TLRPC$PhotoSize> arrayList = tLRPC$Photo.sizes;
            TLRPC$PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 100);
            TLRPC$PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 1000);
            if (closestPhotoSizeWithSize != null) {
                user.photo.photo_small = closestPhotoSizeWithSize.location;
            }
            if (closestPhotoSizeWithSize2 != null) {
                user.photo.photo_big = closestPhotoSizeWithSize2.location;
            }
        } else {
            user.photo = null;
            user.flags &= -33;
        }
        ArrayList<TLRPC$User> arrayList2 = new ArrayList<>();
        arrayList2.add(tLRPC$User);
        getMessagesStorage().putUsersAndChats(arrayList2, null, false, true);
        updateCustomPhotoInfo();
        getNotificationCenter().postNotificationName(NotificationCenter.reloadDialogPhotos, new Object[0]);
        getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_AVATAR));
    }

    private void showAvatarProgress(final boolean z, boolean z2) {
        if (this.avatarProgressView == null) {
            return;
        }
        AnimatorSet animatorSet = this.avatarAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.avatarAnimation = null;
        }
        if (z2) {
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.avatarAnimation = animatorSet2;
            if (z) {
                this.avatarProgressView.setVisibility(0);
                this.avatarOverlay.setVisibility(0);
                this.avatarAnimation.playTogether(ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 1.0f), ObjectAnimator.ofFloat(this.avatarOverlay, (Property<View, Float>) View.ALPHA, 1.0f));
            } else {
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this.avatarProgressView, (Property<RadialProgressView, Float>) View.ALPHA, 0.0f), ObjectAnimator.ofFloat(this.avatarOverlay, (Property<View, Float>) View.ALPHA, 0.0f));
            }
            this.avatarAnimation.setDuration(180L);
            this.avatarAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.ContactAddActivity.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (ContactAddActivity.this.avatarAnimation == null || ContactAddActivity.this.avatarProgressView == null) {
                        return;
                    }
                    if (!z) {
                        ContactAddActivity.this.avatarProgressView.setVisibility(4);
                        ContactAddActivity.this.avatarOverlay.setVisibility(4);
                    }
                    ContactAddActivity.this.avatarAnimation = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    ContactAddActivity.this.avatarAnimation = null;
                }
            });
            this.avatarAnimation.start();
            return;
        }
        if (z) {
            this.avatarProgressView.setAlpha(1.0f);
            this.avatarProgressView.setVisibility(0);
            this.avatarOverlay.setAlpha(1.0f);
            this.avatarOverlay.setVisibility(0);
            return;
        }
        this.avatarProgressView.setAlpha(0.0f);
        this.avatarProgressView.setVisibility(4);
        this.avatarOverlay.setAlpha(0.0f);
        this.avatarOverlay.setVisibility(4);
    }

    public void setDelegate(ContactAddActivityDelegate contactAddActivityDelegate) {
        this.delegate = contactAddActivityDelegate;
    }

    private void updateAvatarLayout() {
        TLRPC$User user;
        if (this.nameTextView == null || (user = getMessagesController().getUser(Long.valueOf(this.user_id))) == null) {
            return;
        }
        if (TextUtils.isEmpty(getPhone())) {
            this.nameTextView.setText(LocaleController.getString("MobileHidden", R.string.MobileHidden));
            this.infoTextView.setText(AndroidUtilities.replaceCharSequence("%1$s", AndroidUtilities.replaceTags(LocaleController.getString("MobileHiddenExceptionInfo", R.string.MobileHiddenExceptionInfo)), Emoji.replaceEmoji(UserObject.getFirstName(user), this.infoTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(12.0f), false)));
        } else {
            this.nameTextView.setText(PhoneFormat.getInstance().format("+" + getPhone()));
            if (this.needAddException) {
                this.infoTextView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("MobileVisibleInfo", R.string.MobileVisibleInfo, UserObject.getFirstName(user))));
            }
        }
        this.onlineTextView.setText(LocaleController.formatUserStatus(this.currentAccount, user));
        if (this.avatar == null) {
            BackupImageView backupImageView = this.avatarImage;
            AvatarDrawable avatarDrawable = new AvatarDrawable(user);
            this.avatarDrawable = avatarDrawable;
            backupImageView.setForUserOrChat(user, avatarDrawable);
        }
    }

    private String getPhone() {
        TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
        return (user == null || TextUtils.isEmpty(user.phone)) ? this.phone : user.phone;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        int i3 = 0;
        if (i == NotificationCenter.updateInterfaces) {
            int intValue = ((Integer) objArr[0]).intValue();
            if ((MessagesController.UPDATE_MASK_AVATAR & intValue) == 0 && (intValue & MessagesController.UPDATE_MASK_STATUS) == 0) {
                return;
            }
            updateAvatarLayout();
            return;
        }
        if (i == NotificationCenter.dialogPhotosLoaded) {
            int intValue2 = ((Integer) objArr[3]).intValue();
            long longValue = ((Long) objArr[0]).longValue();
            boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
            if (this.user_id == longValue && this.classGuid == intValue2) {
                ArrayList arrayList = (ArrayList) objArr[4];
                if (arrayList != null) {
                    while (true) {
                        if (i3 >= arrayList.size()) {
                            break;
                        }
                        if (arrayList.get(i3) != null) {
                            this.prevAvatar = (TLRPC$Photo) arrayList.get(i3);
                            updateCustomPhotoInfo();
                            break;
                        }
                        i3++;
                    }
                }
                if (this.prevAvatar == null && booleanValue) {
                    MessagesController.getInstance(this.currentAccount).loadDialogPhotos(longValue, 80, 0, false, getClassGuid());
                }
            }
        }
    }

    private void updateCustomPhotoInfo() {
        if (this.addContact) {
            return;
        }
        TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
        if (this.fragmentBeginToShow) {
            TransitionManager.beginDelayedTransition(this.linearLayout);
        }
        TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto = user.photo;
        if (tLRPC$UserProfilePhoto != null && tLRPC$UserProfilePhoto.personal) {
            this.oldPhotoCell.setVisibility(0);
            TLRPC$Photo tLRPC$Photo = this.prevAvatar;
            if (tLRPC$Photo != null) {
                this.oldAvatarView.setImage(ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(tLRPC$Photo.sizes, 1000), this.prevAvatar), "50_50", this.avatarDrawable, (Object) null);
            }
        } else {
            this.oldPhotoCell.setVisibility(8);
        }
        if (this.avatarDrawable == null) {
            this.avatarDrawable = new AvatarDrawable(user);
        }
        TLRPC$FileLocation tLRPC$FileLocation = this.avatar;
        if (tLRPC$FileLocation == null) {
            this.avatarImage.setForUserOrChat(user, this.avatarDrawable);
        } else {
            this.avatarImage.setImage(ImageLocation.getForLocal(tLRPC$FileLocation), "50_50", this.avatarDrawable, getMessagesController().getUser(Long.valueOf(this.user_id)));
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        this.paused = true;
        this.imageUpdater.onPause();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        updateAvatarLayout();
        this.imageUpdater.onResume();
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public boolean canFinishFragment() {
        return this.photoSelectedTypeFinal != 1;
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didUploadPhoto(final TLRPC$InputFile tLRPC$InputFile, final TLRPC$InputFile tLRPC$InputFile2, final double d, String str, final TLRPC$PhotoSize tLRPC$PhotoSize, final TLRPC$PhotoSize tLRPC$PhotoSize2, final boolean z, final TLRPC$VideoSize tLRPC$VideoSize) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ContactAddActivity.this.lambda$didUploadPhoto$7(tLRPC$PhotoSize2, tLRPC$InputFile, tLRPC$InputFile2, tLRPC$PhotoSize, tLRPC$VideoSize, d, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didUploadPhoto$7(TLRPC$PhotoSize tLRPC$PhotoSize, TLRPC$InputFile tLRPC$InputFile, TLRPC$InputFile tLRPC$InputFile2, TLRPC$PhotoSize tLRPC$PhotoSize2, TLRPC$VideoSize tLRPC$VideoSize, double d, boolean z) {
        if (this.imageUpdater.isCanceled()) {
            return;
        }
        int i = this.photoSelectedTypeFinal;
        if (i == 2) {
            this.avatar = tLRPC$PhotoSize.location;
        } else if (i == 1) {
            NavigationExt.backToFragment(this, new NavigationExt.FragmentConsumer() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda11
                @Override // org.telegram.ui.LNavigation.NavigationExt.FragmentConsumer
                public final boolean consume(BaseFragment baseFragment) {
                    boolean lambda$didUploadPhoto$6;
                    lambda$didUploadPhoto$6 = ContactAddActivity.this.lambda$didUploadPhoto$6(baseFragment);
                    return lambda$didUploadPhoto$6;
                }
            });
        }
        if (tLRPC$InputFile != null || tLRPC$InputFile2 != null) {
            TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
            if (this.suggestPhotoMessageFinal == null && user != null) {
                PhotoUtilities.applyPhotoToUser(tLRPC$PhotoSize, tLRPC$PhotoSize2, tLRPC$InputFile2 != null, user, true);
                ArrayList<TLRPC$User> arrayList = new ArrayList<>();
                arrayList.add(user);
                getMessagesStorage().putUsersAndChats(arrayList, null, false, true);
                getNotificationCenter().postNotificationName(NotificationCenter.reloadDialogPhotos, new Object[0]);
                getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_AVATAR));
            }
            sendPhotoChangedRequest(this.avatar, tLRPC$PhotoSize2.location, tLRPC$InputFile, tLRPC$InputFile2, tLRPC$VideoSize, d, this.photoSelectedTypeFinal);
            showAvatarProgress(false, true);
        } else {
            this.avatarImage.setImage(ImageLocation.getForLocal(this.avatar), "50_50", this.avatarDrawable, getMessagesController().getUser(Long.valueOf(this.user_id)));
            if (this.photoSelectedTypeFinal == 2) {
                showAvatarProgress(true, false);
            } else {
                createServiceMessageLocal(tLRPC$PhotoSize, tLRPC$PhotoSize2, z);
            }
        }
        updateCustomPhotoInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$didUploadPhoto$6(BaseFragment baseFragment) {
        if (baseFragment instanceof ChatActivity) {
            ChatActivity chatActivity = (ChatActivity) baseFragment;
            if (chatActivity.getDialogId() == this.user_id && chatActivity.getChatMode() == 0) {
                chatActivity.scrollToLastMessage(true, false);
                return true;
            }
        }
        return false;
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didUploadFailed() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ContactAddActivity.this.lambda$didUploadFailed$8();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didUploadFailed$8() {
        ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
        if (this.suggestPhotoMessageFinal != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(this.suggestPhotoMessageFinal.getId()));
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.messagesDeleted, arrayList, 0L, Boolean.FALSE);
        }
    }

    private void createServiceMessageLocal(TLRPC$PhotoSize tLRPC$PhotoSize, TLRPC$PhotoSize tLRPC$PhotoSize2, boolean z) {
        TLRPC$TL_messageService tLRPC$TL_messageService = new TLRPC$TL_messageService();
        tLRPC$TL_messageService.random_id = SendMessagesHelper.getInstance(this.currentAccount).getNextRandomId();
        tLRPC$TL_messageService.dialog_id = this.user_id;
        tLRPC$TL_messageService.unread = true;
        tLRPC$TL_messageService.out = true;
        int newMessageId = getUserConfig().getNewMessageId();
        tLRPC$TL_messageService.id = newMessageId;
        tLRPC$TL_messageService.local_id = newMessageId;
        TLRPC$TL_peerUser tLRPC$TL_peerUser = new TLRPC$TL_peerUser();
        tLRPC$TL_messageService.from_id = tLRPC$TL_peerUser;
        tLRPC$TL_peerUser.user_id = getUserConfig().getClientUserId();
        tLRPC$TL_messageService.flags |= 256;
        TLRPC$TL_peerUser tLRPC$TL_peerUser2 = new TLRPC$TL_peerUser();
        tLRPC$TL_messageService.peer_id = tLRPC$TL_peerUser2;
        tLRPC$TL_peerUser2.user_id = this.user_id;
        tLRPC$TL_messageService.date = getConnectionsManager().getCurrentTime();
        TLRPC$TL_messageActionSuggestProfilePhoto tLRPC$TL_messageActionSuggestProfilePhoto = new TLRPC$TL_messageActionSuggestProfilePhoto();
        tLRPC$TL_messageService.action = tLRPC$TL_messageActionSuggestProfilePhoto;
        TLRPC$TL_photo tLRPC$TL_photo = new TLRPC$TL_photo();
        tLRPC$TL_messageActionSuggestProfilePhoto.photo = tLRPC$TL_photo;
        tLRPC$TL_photo.sizes.add(tLRPC$PhotoSize);
        tLRPC$TL_messageActionSuggestProfilePhoto.photo.sizes.add(tLRPC$PhotoSize2);
        tLRPC$TL_messageActionSuggestProfilePhoto.video = z;
        tLRPC$TL_messageActionSuggestProfilePhoto.photo.file_reference = new byte[0];
        ArrayList<MessageObject> arrayList = new ArrayList<>();
        MessageObject messageObject = new MessageObject(this.currentAccount, tLRPC$TL_messageService, false, false);
        this.suggestPhotoMessageFinal = messageObject;
        arrayList.add(messageObject);
        new ArrayList().add(tLRPC$TL_messageService);
        MessagesController.getInstance(this.currentAccount).updateInterfaceWithMessages(this.user_id, arrayList, false);
        getMessagesController().photoSuggestion.put(tLRPC$TL_messageService.local_id, this.imageUpdater);
    }

    private void sendPhotoChangedRequest(final TLRPC$FileLocation tLRPC$FileLocation, final TLRPC$FileLocation tLRPC$FileLocation2, TLRPC$InputFile tLRPC$InputFile, final TLRPC$InputFile tLRPC$InputFile2, TLRPC$VideoSize tLRPC$VideoSize, double d, final int i) {
        TLRPC$TL_photos_uploadContactProfilePhoto tLRPC$TL_photos_uploadContactProfilePhoto = new TLRPC$TL_photos_uploadContactProfilePhoto();
        tLRPC$TL_photos_uploadContactProfilePhoto.user_id = getMessagesController().getInputUser(this.user_id);
        if (tLRPC$InputFile != null) {
            tLRPC$TL_photos_uploadContactProfilePhoto.file = tLRPC$InputFile;
            tLRPC$TL_photos_uploadContactProfilePhoto.flags |= 1;
        }
        if (tLRPC$InputFile2 != null) {
            tLRPC$TL_photos_uploadContactProfilePhoto.video = tLRPC$InputFile2;
            int i2 = tLRPC$TL_photos_uploadContactProfilePhoto.flags | 2;
            tLRPC$TL_photos_uploadContactProfilePhoto.flags = i2;
            tLRPC$TL_photos_uploadContactProfilePhoto.video_start_ts = d;
            tLRPC$TL_photos_uploadContactProfilePhoto.flags = i2 | 4;
        }
        if (tLRPC$VideoSize != null) {
            tLRPC$TL_photos_uploadContactProfilePhoto.flags |= 32;
            tLRPC$TL_photos_uploadContactProfilePhoto.video_emoji_markup = tLRPC$VideoSize;
        }
        if (i == 1) {
            tLRPC$TL_photos_uploadContactProfilePhoto.suggest = true;
            tLRPC$TL_photos_uploadContactProfilePhoto.flags |= 8;
        } else {
            tLRPC$TL_photos_uploadContactProfilePhoto.save = true;
            tLRPC$TL_photos_uploadContactProfilePhoto.flags |= 16;
        }
        getConnectionsManager().sendRequest(tLRPC$TL_photos_uploadContactProfilePhoto, new RequestDelegate() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                ContactAddActivity.this.lambda$sendPhotoChangedRequest$10(tLRPC$FileLocation, tLRPC$InputFile2, tLRPC$FileLocation2, i, tLObject, tLRPC$TL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPhotoChangedRequest$10(final TLRPC$FileLocation tLRPC$FileLocation, final TLRPC$InputFile tLRPC$InputFile, final TLRPC$FileLocation tLRPC$FileLocation2, final int i, final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ContactAddActivity.this.lambda$sendPhotoChangedRequest$9(tLRPC$FileLocation, tLRPC$InputFile, tLObject, tLRPC$FileLocation2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPhotoChangedRequest$9(TLRPC$FileLocation tLRPC$FileLocation, TLRPC$InputFile tLRPC$InputFile, TLObject tLObject, TLRPC$FileLocation tLRPC$FileLocation2, int i) {
        if (this.suggestPhotoMessageFinal != null) {
            return;
        }
        if ((tLRPC$FileLocation == null && tLRPC$InputFile == null) || tLObject == null) {
            return;
        }
        TLRPC$TL_photos_photo tLRPC$TL_photos_photo = (TLRPC$TL_photos_photo) tLObject;
        ArrayList<TLRPC$PhotoSize> arrayList = tLRPC$TL_photos_photo.photo.sizes;
        TLRPC$User user = getMessagesController().getUser(Long.valueOf(this.user_id));
        TLRPC$UserFull userFull = MessagesController.getInstance(this.currentAccount).getUserFull(this.user_id);
        if (userFull != null) {
            userFull.personal_photo = tLRPC$TL_photos_photo.photo;
            userFull.flags |= 2097152;
            getMessagesStorage().updateUserInfo(userFull, true);
        }
        if (user != null) {
            TLRPC$PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 100);
            TLRPC$PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 1000);
            if (closestPhotoSizeWithSize != null && tLRPC$FileLocation != null) {
                FileLoader.getInstance(this.currentAccount).getPathToAttach(tLRPC$FileLocation, true).renameTo(FileLoader.getInstance(this.currentAccount).getPathToAttach(closestPhotoSizeWithSize, true));
                ImageLoader.getInstance().replaceImageInCache(tLRPC$FileLocation.volume_id + "_" + tLRPC$FileLocation.local_id + "@50_50", closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + "@50_50", ImageLocation.getForUser(user, 1), false);
            }
            if (closestPhotoSizeWithSize2 != null && tLRPC$FileLocation2 != null) {
                FileLoader.getInstance(this.currentAccount).getPathToAttach(tLRPC$FileLocation2, true).renameTo(FileLoader.getInstance(this.currentAccount).getPathToAttach(closestPhotoSizeWithSize2, true));
            }
            PhotoUtilities.applyPhotoToUser(tLRPC$TL_photos_photo.photo, user, true);
            ArrayList<TLRPC$User> arrayList2 = new ArrayList<>();
            arrayList2.add(user);
            getMessagesStorage().putUsersAndChats(arrayList2, null, false, true);
            getMessagesStorage().addDialogPhoto(this.user_id, tLRPC$TL_photos_photo.photo);
            getNotificationCenter().postNotificationName(NotificationCenter.reloadDialogPhotos, new Object[0]);
            getNotificationCenter().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_AVATAR));
            if (getParentActivity() != null) {
                if (i == 2) {
                    BulletinFactory.of(this).createUsersBulletin(arrayList2, AndroidUtilities.replaceTags(LocaleController.formatString("UserCustomPhotoSeted", R.string.UserCustomPhotoSeted, user.first_name))).show();
                } else {
                    BulletinFactory.of(this).createUsersBulletin(arrayList2, AndroidUtilities.replaceTags(LocaleController.formatString("UserCustomPhotoSeted", R.string.UserCustomPhotoSeted, user.first_name))).show();
                }
            }
        }
        this.avatar = null;
        updateCustomPhotoInfo();
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public String getInitialSearchString() {
        return ImageUpdater.ImageUpdaterDelegate.CC.$default$getInitialSearchString(this);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void onUploadProgressChanged(float f) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        radialProgressView.setProgress(f);
    }

    @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
    public void didStartUpload(boolean z) {
        RadialProgressView radialProgressView = this.avatarProgressView;
        if (radialProgressView == null) {
            return;
        }
        this.photoSelectedTypeFinal = this.photoSelectedType;
        radialProgressView.setProgress(0.0f);
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        ThemeDescription.ThemeDescriptionDelegate themeDescriptionDelegate = new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.ContactAddActivity$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                ContactAddActivity.this.lambda$getThemeDescriptions$11();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        };
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        TextView textView = this.nameTextView;
        int i = ThemeDescription.FLAG_TEXTCOLOR;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(textView, i, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.onlineTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText3));
        arrayList.add(new ThemeDescription(this.firstNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        EditTextBoldCursor editTextBoldCursor = this.firstNameField;
        int i3 = ThemeDescription.FLAG_HINTTEXTCOLOR;
        int i4 = Theme.key_windowBackgroundWhiteHintText;
        arrayList.add(new ThemeDescription(editTextBoldCursor, i3, null, null, null, null, i4));
        EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
        int i5 = ThemeDescription.FLAG_BACKGROUNDFILTER;
        int i6 = Theme.key_windowBackgroundWhiteInputField;
        arrayList.add(new ThemeDescription(editTextBoldCursor2, i5, null, null, null, null, i6));
        EditTextBoldCursor editTextBoldCursor3 = this.firstNameField;
        int i7 = ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE;
        int i8 = Theme.key_windowBackgroundWhiteInputFieldActivated;
        arrayList.add(new ThemeDescription(editTextBoldCursor3, i7, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.infoTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText4));
        arrayList.add(new ThemeDescription(null, 0, null, null, Theme.avatarDrawables, themeDescriptionDelegate, Theme.key_avatar_text));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundRed));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundOrange));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundViolet));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundGreen));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundCyan));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundBlue));
        arrayList.add(new ThemeDescription(null, 0, null, null, null, themeDescriptionDelegate, Theme.key_avatar_backgroundPink));
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$11() {
        TLRPC$User user;
        if (this.avatarImage == null || (user = getMessagesController().getUser(Long.valueOf(this.user_id))) == null) {
            return;
        }
        this.avatarDrawable.setInfo(user);
        this.avatarImage.invalidate();
    }
}
