package org.telegram.messenger;

import android.text.TextUtils;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.tgnet.TLRPC$EmojiStatus;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$TL_emojiStatus;
import org.telegram.tgnet.TLRPC$TL_emojiStatusUntil;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_userContact_old2;
import org.telegram.tgnet.TLRPC$TL_userDeleted_old2;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_userProfilePhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_userSelf_old3;
import org.telegram.tgnet.TLRPC$TL_username;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserFull;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;

/* loaded from: classes3.dex */
public class UserObject {
    public static boolean isReplyUser(long j) {
        return j == 708513 || j == 1271266957;
    }

    public static boolean isDeleted(TLRPC$User tLRPC$User) {
        return tLRPC$User == null || (tLRPC$User instanceof TLRPC$TL_userDeleted_old2) || (tLRPC$User instanceof TLRPC$TL_userEmpty) || tLRPC$User.deleted;
    }

    public static boolean isContact(TLRPC$User tLRPC$User) {
        return tLRPC$User != null && ((tLRPC$User instanceof TLRPC$TL_userContact_old2) || tLRPC$User.contact || tLRPC$User.mutual_contact);
    }

    public static boolean isUserSelf(TLRPC$User tLRPC$User) {
        return tLRPC$User != null && ((tLRPC$User instanceof TLRPC$TL_userSelf_old3) || tLRPC$User.self);
    }

    public static boolean isReplyUser(TLRPC$User tLRPC$User) {
        if (tLRPC$User != null) {
            long j = tLRPC$User.id;
            if (j == 708513 || j == 1271266957) {
                return true;
            }
        }
        return false;
    }

    public static String getUserName(TLRPC$User tLRPC$User) {
        if (tLRPC$User == null || isDeleted(tLRPC$User)) {
            return LocaleController.getString("HiddenName", R.string.HiddenName);
        }
        String formatName = ContactsController.formatName(tLRPC$User.first_name, tLRPC$User.last_name);
        if (formatName.length() != 0 || TextUtils.isEmpty(tLRPC$User.phone)) {
            return formatName;
        }
        return PhoneFormat.getInstance().format("+" + tLRPC$User.phone);
    }

    public static String getPublicUsername(TLRPC$User tLRPC$User, boolean z) {
        if (tLRPC$User == null) {
            return null;
        }
        if (!TextUtils.isEmpty(tLRPC$User.username)) {
            return tLRPC$User.username;
        }
        if (tLRPC$User.usernames != null) {
            for (int i = 0; i < tLRPC$User.usernames.size(); i++) {
                TLRPC$TL_username tLRPC$TL_username = tLRPC$User.usernames.get(i);
                if (tLRPC$TL_username != null && (((tLRPC$TL_username.active && !z) || tLRPC$TL_username.editable) && !TextUtils.isEmpty(tLRPC$TL_username.username))) {
                    return tLRPC$TL_username.username;
                }
            }
        }
        return null;
    }

    public static String getPublicUsername(TLRPC$User tLRPC$User) {
        return getPublicUsername(tLRPC$User, false);
    }

    public static boolean hasPublicUsername(TLRPC$User tLRPC$User, String str) {
        if (tLRPC$User != null && str != null) {
            if (str.equalsIgnoreCase(tLRPC$User.username)) {
                return true;
            }
            if (tLRPC$User.usernames != null) {
                for (int i = 0; i < tLRPC$User.usernames.size(); i++) {
                    TLRPC$TL_username tLRPC$TL_username = tLRPC$User.usernames.get(i);
                    if (tLRPC$TL_username != null && tLRPC$TL_username.active && str.equalsIgnoreCase(tLRPC$TL_username.username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getFirstName(TLRPC$User tLRPC$User) {
        return getFirstName(tLRPC$User, true);
    }

    public static String getFirstName(TLRPC$User tLRPC$User, boolean z) {
        if (tLRPC$User == null || isDeleted(tLRPC$User)) {
            return "DELETED";
        }
        String str = tLRPC$User.first_name;
        if (TextUtils.isEmpty(str)) {
            str = tLRPC$User.last_name;
        } else if (!z && str.length() <= 2) {
            return ContactsController.formatName(tLRPC$User.first_name, tLRPC$User.last_name);
        }
        return !TextUtils.isEmpty(str) ? str : LocaleController.getString("HiddenName", R.string.HiddenName);
    }

    public static boolean hasPhoto(TLRPC$User tLRPC$User) {
        TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto;
        return (tLRPC$User == null || (tLRPC$UserProfilePhoto = tLRPC$User.photo) == null || (tLRPC$UserProfilePhoto instanceof TLRPC$TL_userProfilePhotoEmpty)) ? false : true;
    }

    public static TLRPC$UserProfilePhoto getPhoto(TLRPC$User tLRPC$User) {
        if (hasPhoto(tLRPC$User)) {
            return tLRPC$User.photo;
        }
        return null;
    }

    public static boolean hasFallbackPhoto(TLRPC$UserFull tLRPC$UserFull) {
        TLRPC$Photo tLRPC$Photo;
        return (tLRPC$UserFull == null || (tLRPC$Photo = tLRPC$UserFull.fallback_photo) == null || (tLRPC$Photo instanceof TLRPC$TL_photoEmpty)) ? false : true;
    }

    public static Long getEmojiStatusDocumentId(TLRPC$User tLRPC$User) {
        if (tLRPC$User == null) {
            return null;
        }
        return getEmojiStatusDocumentId(tLRPC$User.emoji_status);
    }

    public static Long getEmojiStatusDocumentId(TLRPC$EmojiStatus tLRPC$EmojiStatus) {
        if (tLRPC$EmojiStatus == null) {
            return null;
        }
        if (tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatus) {
            return Long.valueOf(((TLRPC$TL_emojiStatus) tLRPC$EmojiStatus).document_id);
        }
        if (tLRPC$EmojiStatus instanceof TLRPC$TL_emojiStatusUntil) {
            TLRPC$TL_emojiStatusUntil tLRPC$TL_emojiStatusUntil = (TLRPC$TL_emojiStatusUntil) tLRPC$EmojiStatus;
            if (tLRPC$TL_emojiStatusUntil.until > ((int) (System.currentTimeMillis() / 1000))) {
                return Long.valueOf(tLRPC$TL_emojiStatusUntil.document_id);
            }
        }
        return null;
    }
}
