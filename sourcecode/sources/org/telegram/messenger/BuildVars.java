package org.telegram.messenger;

import android.os.Build;
import com.android.billingclient.api.ProductDetails;
import java.util.Iterator;
import java.util.Objects;
import org.telegram.tgnet.TLRPC$JMT_response_home_link;
import org.telegram.ui.LoginActivity;

/* loaded from: classes3.dex */
public class BuildVars {
    public static String APP_HASH = null;
    public static int APP_ID = 0;
    public static int BUILD_VERSION = 0;
    public static String BUILD_VERSION_STRING = null;
    public static boolean CHECK_UPDATES = false;
    public static boolean DEBUG_PRIVATE_VERSION = false;
    public static boolean DEBUG_VERSION = false;
    public static String GOOGLE_AUTH_CLIENT_ID = null;
    public static String HUAWEI_APP_ID = null;
    public static boolean IS_BILLING_UNAVAILABLE = false;
    public static boolean LOGS_ENABLED = true;
    public static boolean NO_SCOPED_STORAGE = false;
    public static String PLAYSTORE_APP_URL = null;
    public static String SAFETYNET_KEY = null;
    public static String SMS_HASH = null;
    public static boolean USE_CLOUD_STRINGS = false;
    public static String baiduMapAk;
    public static String baiduMapUrl;
    private static Boolean betaApp;
    public static String buglyAppId;
    public static String cloudKey;
    public static int cloudPort;
    public static int controlInstallSDK;
    public static long currentTime;
    public static int defaultRedPacketType;
    public static boolean enableAppLogout;
    public static boolean enableGroupExit;
    public static double enableGroupExitCount;
    public static String exclusiveChannelImageUrl;
    public static String floatUrl;
    public static int floatVpnPort;
    public static TLRPC$JMT_response_home_link homeLink;
    public static boolean isAllowCloneRun;
    public static boolean isAlwaysShowNotification;
    public static boolean isCanOpenProfile;
    public static boolean isCanShareContact;
    public static boolean isCanShowFriendInfo;
    public static boolean isCheckPasswordUpperCase;
    public static boolean isClearRequestOnReconnect;
    public static boolean isComplexPassword;
    public static boolean isDownloadLineFromDNS;
    public static boolean isDownloadLineFromOnline;
    public static boolean isFirstPrivateChatHint;
    public static boolean isForceSetAvatar;
    public static boolean isInviteCode;
    public static boolean isLimitUserName;
    public static boolean isLimitUserNameFirstLetter;
    public static boolean isNeedBindPhone;
    public static boolean isOpenCloudDefense;
    public static boolean isOpenCreateGroup;
    public static boolean isOpenFireFastShot;
    public static boolean isOpenGuestLogin;
    public static boolean isOpenVPN;
    public static boolean isOpenWithOtherApp;
    public static boolean isReceiveRedPacket;
    public static boolean isReceiveRedPacketAfterMute;
    public static boolean isRegisterVerify;
    public static boolean isReportRegisterByGuest;
    public static boolean isRequestContacts;
    public static boolean isRequestContactsPrompt;
    public static boolean isSaveDialogAfterKick;
    public static boolean isSaveToAlbum;
    public static boolean isSaveToAlbumAndDownload;
    public static boolean isSendRedPacket;
    public static boolean isShowAttachLocation;
    public static boolean isShowChatDefaultBackground;
    public static boolean isShowChatSubTitle;
    public static boolean isShowComplaintService;
    public static boolean isShowDeleteBanUser;
    public static boolean isShowDeleteSingleMsg;
    public static boolean isShowEditButton;
    public static boolean isShowEditedMessage;
    public static boolean isShowExclusiveChannel;
    public static boolean isShowForwardMultiMsg;
    public static boolean isShowForwardSingleMsg;
    public static boolean isShowForwardedName;
    public static boolean isShowGroupMember;
    public static boolean isShowLastOnlineTime;
    public static boolean isShowLoginImage;
    public static boolean isShowLoginPrivacyView;
    public static boolean isShowLoginVideo;
    public static boolean isShowNetworkVPN;
    public static boolean isShowOnlineService;
    public static boolean isShowPasswordEye;
    public static boolean isShowProfileShareMedia;
    public static boolean isShowReadStatus;
    public static boolean isShowRecordScreen;
    public static boolean isShowRecordVideo;
    public static boolean isShowRedPacketCountAndBest;
    public static boolean isShowReplyForwardedName;
    public static boolean isShowSwitchNextLine;
    public static boolean isShowUserNameLoginMode;
    public static boolean isShowVideoCall;
    public static boolean isShowVoiceCall;
    public static boolean isShowVoiceFrom;
    public static boolean isShowWallet;
    public static boolean isSignInByGuest;
    public static boolean isSplitLoginAndRegister;
    public static long isSubmitLogsTime;
    public static int limitNickNameMax;
    public static int limitUserNameMax;
    public static int limitUserNameMin;
    public static String loginServiceUrl;
    public static int maxSelectMsgCount;
    public static int maxSendRedPacketCount;
    public static LoginActivity.MergeUserNameView mergeUserNameView;
    public static String openVPNUrl;
    public static boolean requireRegisterPhone;
    public static boolean sendNewProtocol;
    public static int showChatNoticeType;
    public static int showSwitchDownloadLineType;
    private static Boolean standaloneApp;
    public static int submitLogsMaxCount;
    public static long tempChatId;

    static {
        NO_SCOPED_STORAGE = Build.VERSION.SDK_INT <= 29;
        BUILD_VERSION = 3362;
        BUILD_VERSION_STRING = "9.6.6";
        APP_ID = 4;
        APP_HASH = "014b35b6184100b085b0d0572f9b5103";
        SAFETYNET_KEY = "";
        SMS_HASH = "";
        PLAYSTORE_APP_URL = "";
        GOOGLE_AUTH_CLIENT_ID = "";
        HUAWEI_APP_ID = "";
        IS_BILLING_UNAVAILABLE = false;
        buglyAppId = "a96de5995f";
        isInviteCode = false;
        requireRegisterPhone = false;
        isRegisterVerify = false;
        isForceSetAvatar = false;
        loginServiceUrl = "";
        homeLink = null;
        isShowGroupMember = false;
        isCanShowFriendInfo = false;
        isOpenVPN = false;
        openVPNUrl = "";
        floatUrl = "";
        floatVpnPort = 0;
        baiduMapUrl = "https://api.map.baidu.com/staticimage/v2";
        baiduMapAk = "";
        isSignInByGuest = false;
        tempChatId = 0L;
        currentTime = 0L;
        isSubmitLogsTime = System.currentTimeMillis();
        isRequestContactsPrompt = false;
        enableGroupExitCount = -1.0d;
        isAllowCloneRun = true;
        controlInstallSDK = 1;
        exclusiveChannelImageUrl = "";
        mergeUserNameView = null;
        isReportRegisterByGuest = false;
        isShowReplyForwardedName = false;
        isShowForwardedName = false;
        isShowComplaintService = false;
        isDownloadLineFromDNS = true;
        isShowLoginPrivacyView = true;
        isShowNetworkVPN = false;
        sendNewProtocol = true;
        isOpenCloudDefense = false;
        cloudKey = "BvyoNmnTUIqvZufrqy6EPc/QFvgcZwweLUQZMPRjS0yO7ir5gj50GehaWU1uVA==";
        cloudPort = 20087;
        isShowRecordScreen = false;
        isShowWallet = false;
        isSendRedPacket = false;
        isReceiveRedPacket = false;
        defaultRedPacketType = 1;
        maxSendRedPacketCount = 200;
        isShowRedPacketCountAndBest = true;
        isShowEditButton = false;
        isShowEditedMessage = false;
        isShowVoiceCall = false;
        isShowVideoCall = false;
        isShowLoginVideo = false;
        isShowLoginImage = true;
        isShowDeleteSingleMsg = false;
        isShowForwardSingleMsg = false;
        isShowForwardMultiMsg = false;
        maxSelectMsgCount = 10;
        isOpenGuestLogin = true;
        isOpenCreateGroup = false;
        isReceiveRedPacketAfterMute = true;
        isShowLastOnlineTime = false;
        isRequestContacts = false;
        isNeedBindPhone = true;
        isShowOnlineService = false;
        isSaveDialogAfterKick = false;
        isShowUserNameLoginMode = false;
        isLimitUserNameFirstLetter = false;
        isLimitUserName = false;
        limitUserNameMin = 6;
        limitUserNameMax = 10;
        isComplexPassword = false;
        isCheckPasswordUpperCase = false;
        isShowPasswordEye = true;
        showChatNoticeType = 2;
        isShowExclusiveChannel = false;
        isSaveToAlbum = false;
        isOpenWithOtherApp = false;
        isAlwaysShowNotification = false;
        isSaveToAlbumAndDownload = false;
        isShowProfileShareMedia = false;
        isShowChatSubTitle = true;
        isClearRequestOnReconnect = false;
        isShowSwitchNextLine = true;
        showSwitchDownloadLineType = 1;
        enableAppLogout = true;
        enableGroupExit = false;
        isSplitLoginAndRegister = false;
        isShowChatDefaultBackground = false;
        isCanOpenProfile = false;
        isCanShareContact = false;
        isShowVoiceFrom = false;
        isShowAttachLocation = false;
        submitLogsMaxCount = 5;
        isFirstPrivateChatHint = false;
        isShowDeleteBanUser = false;
        isDownloadLineFromOnline = true;
        isShowReadStatus = true;
        limitNickNameMax = 20;
        isOpenFireFastShot = false;
        isShowRecordVideo = false;
    }

    public static boolean useInvoiceBilling() {
        return BillingController.billingClientEmpty || DEBUG_VERSION || isStandaloneApp() || isBetaApp() || isHuaweiStoreApp() || hasDirectCurrency();
    }

    private static boolean hasDirectCurrency() {
        ProductDetails productDetails;
        if (BillingController.getInstance().isReady() && (productDetails = BillingController.PREMIUM_PRODUCT_DETAILS) != null) {
            Iterator<ProductDetails.SubscriptionOfferDetails> it = productDetails.getSubscriptionOfferDetails().iterator();
            while (it.hasNext()) {
                for (ProductDetails.PricingPhase pricingPhase : it.next().getPricingPhases().getPricingPhaseList()) {
                    Iterator<String> it2 = MessagesController.getInstance(UserConfig.selectedAccount).directPaymentsCurrency.iterator();
                    while (it2.hasNext()) {
                        if (Objects.equals(pricingPhase.getPriceCurrencyCode(), it2.next())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isStandaloneApp() {
        if (standaloneApp == null) {
            standaloneApp = Boolean.valueOf(ApplicationLoader.applicationContext != null && "org.telegram.messenger.web".equals(ApplicationLoader.applicationContext.getPackageName()));
        }
        return standaloneApp.booleanValue();
    }

    public static boolean isBetaApp() {
        if (betaApp == null) {
            betaApp = Boolean.valueOf(ApplicationLoader.applicationContext != null && "org.telegram.messenger.beta".equals(ApplicationLoader.applicationContext.getPackageName()));
        }
        return betaApp.booleanValue();
    }

    public static boolean isHuaweiStoreApp() {
        return ApplicationLoader.isHuaweiStoreBuild();
    }
}
