package org.telegram.ui;

import android.content.Context;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_updateProfile;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

/* loaded from: classes3.dex */
public class ChangeNameActivity extends BaseFragment {
    private View doneButton;
    private EditTextBoldCursor firstNameField;
    private EditTextBoldCursor lastNameField;
    private Theme.ResourcesProvider resourcesProvider;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$createView$0(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveName$2(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
    }

    public ChangeNameActivity(Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_avatar_actionBarSelectorBlue, this.resourcesProvider), false);
        this.actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon, this.resourcesProvider), false);
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("EditName", R.string.EditName));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.ChangeNameActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i != -1) {
                    if (i != 1 || ChangeNameActivity.this.firstNameField.getText().length() == 0) {
                        return;
                    }
                    ChangeNameActivity.this.saveName();
                    ChangeNameActivity.this.finishFragment();
                    return;
                }
                ChangeNameActivity.this.finishFragment();
            }
        });
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_ab_done, AndroidUtilities.dp(56.0f), LocaleController.getString("Done", R.string.Done));
        TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(UserConfig.getInstance(this.currentAccount).getClientUserId()));
        if (user == null) {
            user = UserConfig.getInstance(this.currentAccount).getCurrentUser();
        }
        LinearLayout linearLayout = new LinearLayout(context);
        this.fragmentView = linearLayout;
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        ((LinearLayout) this.fragmentView).setOrientation(1);
        this.fragmentView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.ChangeNameActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                boolean lambda$createView$0;
                lambda$createView$0 = ChangeNameActivity.lambda$createView$0(view, motionEvent);
                return lambda$createView$0;
            }
        });
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context) { // from class: org.telegram.ui.ChangeNameActivity.2
            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected Theme.ResourcesProvider getResourcesProvider() {
                return ChangeNameActivity.this.resourcesProvider;
            }
        };
        this.firstNameField = editTextBoldCursor;
        editTextBoldCursor.setTextSize(1, 18.0f);
        EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
        int i = Theme.key_windowBackgroundWhiteHintText;
        editTextBoldCursor2.setHintTextColor(Theme.getColor(i, this.resourcesProvider));
        EditTextBoldCursor editTextBoldCursor3 = this.firstNameField;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        editTextBoldCursor3.setTextColor(Theme.getColor(i2, this.resourcesProvider));
        this.firstNameField.setBackgroundDrawable(null);
        EditTextBoldCursor editTextBoldCursor4 = this.firstNameField;
        int i3 = Theme.key_windowBackgroundWhiteInputField;
        int themedColor = getThemedColor(i3);
        int i4 = Theme.key_windowBackgroundWhiteInputFieldActivated;
        int themedColor2 = getThemedColor(i4);
        int i5 = Theme.key_text_RedRegular;
        editTextBoldCursor4.setLineColors(themedColor, themedColor2, getThemedColor(i5));
        this.firstNameField.setMaxLines(1);
        this.firstNameField.setLines(1);
        this.firstNameField.setSingleLine(true);
        this.firstNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.firstNameField.setInputType(49152);
        this.firstNameField.setImeOptions(5);
        this.firstNameField.setHint(LocaleController.getString("FirstName", R.string.FirstName));
        this.firstNameField.setCursorColor(Theme.getColor(i2, this.resourcesProvider));
        this.firstNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.firstNameField.setCursorWidth(1.5f);
        this.firstNameField.setFilters(new InputFilter[]{new InputFilter.LengthFilter(BuildVars.limitNickNameMax)});
        linearLayout.addView(this.firstNameField, LayoutHelper.createLinear(-1, 36, 24.0f, 24.0f, 24.0f, 0.0f));
        this.firstNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.ChangeNameActivity$$ExternalSyntheticLambda1
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i6, KeyEvent keyEvent) {
                boolean lambda$createView$1;
                lambda$createView$1 = ChangeNameActivity.this.lambda$createView$1(textView, i6, keyEvent);
                return lambda$createView$1;
            }
        });
        EditTextBoldCursor editTextBoldCursor5 = new EditTextBoldCursor(context) { // from class: org.telegram.ui.ChangeNameActivity.3
            @Override // org.telegram.ui.Components.EditTextBoldCursor
            protected Theme.ResourcesProvider getResourcesProvider() {
                return ChangeNameActivity.this.resourcesProvider;
            }
        };
        this.lastNameField = editTextBoldCursor5;
        editTextBoldCursor5.setTextSize(1, 18.0f);
        this.lastNameField.setHintTextColor(Theme.getColor(i, this.resourcesProvider));
        this.lastNameField.setTextColor(Theme.getColor(i2, this.resourcesProvider));
        this.lastNameField.setBackgroundDrawable(null);
        this.lastNameField.setLineColors(getThemedColor(i3), getThemedColor(i4), getThemedColor(i5));
        this.lastNameField.setMaxLines(1);
        this.lastNameField.setLines(1);
        this.lastNameField.setSingleLine(true);
        this.lastNameField.setGravity(LocaleController.isRTL ? 5 : 3);
        this.lastNameField.setInputType(49152);
        this.lastNameField.setImeOptions(6);
        this.lastNameField.setHint(LocaleController.getString("LastName", R.string.LastName));
        this.lastNameField.setCursorColor(Theme.getColor(i2, this.resourcesProvider));
        this.lastNameField.setCursorSize(AndroidUtilities.dp(20.0f));
        this.lastNameField.setCursorWidth(1.5f);
        if (user != null) {
            this.firstNameField.setText(user.first_name);
            EditTextBoldCursor editTextBoldCursor6 = this.firstNameField;
            editTextBoldCursor6.setSelection(editTextBoldCursor6.length());
            this.lastNameField.setText(user.last_name);
        }
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$1(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6 && i != 5) {
            return false;
        }
        this.doneButton.performClick();
        return true;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        if (MessagesController.getGlobalMainSettings().getBoolean("view_animations", true)) {
            return;
        }
        this.firstNameField.requestFocus();
        AndroidUtilities.showKeyboard(this.firstNameField);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveName() {
        String str;
        TLRPC$User currentUser = UserConfig.getInstance(this.currentAccount).getCurrentUser();
        if (currentUser == null || this.lastNameField.getText() == null || this.firstNameField.getText() == null) {
            return;
        }
        String trim = this.firstNameField.getText().toString().trim();
        String obj = this.lastNameField.getText().toString();
        String str2 = currentUser.first_name;
        if (str2 == null || !str2.equals(trim) || (str = currentUser.last_name) == null || !str.equals(obj)) {
            if (trim.isEmpty()) {
                Toast.makeText(getContext(), LocaleController.getString("JMTUsernameEmpty", R.string.JMTUsernameEmpty), 0).show();
                return;
            }
            TLRPC$TL_account_updateProfile tLRPC$TL_account_updateProfile = new TLRPC$TL_account_updateProfile();
            tLRPC$TL_account_updateProfile.flags = 3;
            tLRPC$TL_account_updateProfile.first_name = trim;
            currentUser.first_name = trim;
            tLRPC$TL_account_updateProfile.last_name = obj;
            currentUser.last_name = obj;
            TLRPC$User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(UserConfig.getInstance(this.currentAccount).getClientUserId()));
            if (user != null) {
                user.first_name = tLRPC$TL_account_updateProfile.first_name;
                user.last_name = tLRPC$TL_account_updateProfile.last_name;
            }
            UserConfig.getInstance(this.currentAccount).saveConfig(true);
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
            NotificationCenter.getInstance(this.currentAccount).postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_NAME));
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tLRPC$TL_account_updateProfile, new RequestDelegate() { // from class: org.telegram.ui.ChangeNameActivity$$ExternalSyntheticLambda3
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    ChangeNameActivity.lambda$saveName$2(tLObject, tLRPC$TL_error);
                }
            });
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public Theme.ResourcesProvider getResourceProvider() {
        return this.resourcesProvider;
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ChangeNameActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ChangeNameActivity.this.lambda$onTransitionAnimationEnd$3();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTransitionAnimationEnd$3() {
        EditTextBoldCursor editTextBoldCursor = this.firstNameField;
        if (editTextBoldCursor != null) {
            editTextBoldCursor.requestFocus();
            AndroidUtilities.showKeyboard(this.firstNameField);
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        ArrayList<ThemeDescription> arrayList = new ArrayList<>();
        arrayList.add(new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle));
        arrayList.add(new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector));
        EditTextBoldCursor editTextBoldCursor = this.firstNameField;
        int i = ThemeDescription.FLAG_TEXTCOLOR;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        arrayList.add(new ThemeDescription(editTextBoldCursor, i, null, null, null, null, i2));
        EditTextBoldCursor editTextBoldCursor2 = this.firstNameField;
        int i3 = ThemeDescription.FLAG_HINTTEXTCOLOR;
        int i4 = Theme.key_windowBackgroundWhiteHintText;
        arrayList.add(new ThemeDescription(editTextBoldCursor2, i3, null, null, null, null, i4));
        EditTextBoldCursor editTextBoldCursor3 = this.firstNameField;
        int i5 = ThemeDescription.FLAG_BACKGROUNDFILTER;
        int i6 = Theme.key_windowBackgroundWhiteInputField;
        arrayList.add(new ThemeDescription(editTextBoldCursor3, i5, null, null, null, null, i6));
        EditTextBoldCursor editTextBoldCursor4 = this.firstNameField;
        int i7 = ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE;
        int i8 = Theme.key_windowBackgroundWhiteInputFieldActivated;
        arrayList.add(new ThemeDescription(editTextBoldCursor4, i7, null, null, null, null, i8));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, i2));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, i4));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, i6));
        arrayList.add(new ThemeDescription(this.lastNameField, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, i8));
        return arrayList;
    }
}
