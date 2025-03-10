package org.telegram.ui.Cells;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Property;
import android.util.SparseArray;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.youth.banner.config.BannerConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FlagSecureReason;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.WebFile;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.video.VideoPlayerRewinder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatInvite;
import org.telegram.tgnet.TLRPC$ChatPhoto;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$DocumentAttribute;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageFwdHeader;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$MessagePeerReaction;
import org.telegram.tgnet.TLRPC$MessageReplies;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$Poll;
import org.telegram.tgnet.TLRPC$Reaction;
import org.telegram.tgnet.TLRPC$ReactionCount;
import org.telegram.tgnet.TLRPC$ReplyMarkup;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonCallback;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRequestGeoLocation;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRequestPeer;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRow;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrl;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrlAuth;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonWebView;
import org.telegram.tgnet.TLRPC$TL_messageExtendedMediaPreview;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageReactions;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photoStrippedSize;
import org.telegram.tgnet.TLRPC$TL_pollAnswer;
import org.telegram.tgnet.TLRPC$TL_pollAnswerVoters;
import org.telegram.tgnet.TLRPC$TL_reactionEmoji;
import org.telegram.tgnet.TLRPC$TL_user;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$User;
import org.telegram.tgnet.TLRPC$UserProfilePhoto;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ChatMessageCell;
import org.telegram.ui.Cells.TextSelectionHelper;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AnimatedEmojiDrawable;
import org.telegram.ui.Components.AnimatedEmojiSpan;
import org.telegram.ui.Components.AnimatedFileDrawable;
import org.telegram.ui.Components.AnimatedFloat;
import org.telegram.ui.Components.AnimatedNumberLayout;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.CheckBoxBase;
import org.telegram.ui.Components.CubicBezierInterpolator;
import org.telegram.ui.Components.FloatSeekBarAccessibilityDelegate;
import org.telegram.ui.Components.Forum.MessageTopicButton;
import org.telegram.ui.Components.InfiniteProgress;
import org.telegram.ui.Components.LinkPath;
import org.telegram.ui.Components.LinkSpanDrawable;
import org.telegram.ui.Components.LoadingDrawable;
import org.telegram.ui.Components.MessageBackgroundDrawable;
import org.telegram.ui.Components.MotionBackgroundDrawable;
import org.telegram.ui.Components.MsgClockDrawable;
import org.telegram.ui.Components.Point;
import org.telegram.ui.Components.RLottieDrawable;
import org.telegram.ui.Components.RadialProgress2;
import org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RoundVideoPlayingDrawable;
import org.telegram.ui.Components.SeekBar;
import org.telegram.ui.Components.SeekBarAccessibilityDelegate;
import org.telegram.ui.Components.SeekBarWaveform;
import org.telegram.ui.Components.SlotsDrawable;
import org.telegram.ui.Components.StaticLayoutEx;
import org.telegram.ui.Components.TextStyleSpan;
import org.telegram.ui.Components.TimerParticles;
import org.telegram.ui.Components.TranscribeButton;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanBrowser;
import org.telegram.ui.Components.URLSpanMono;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.Components.VideoForwardDrawable;
import org.telegram.ui.Components.spoilers.SpoilerEffect;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.PinchToZoomHelper;

/* loaded from: classes4.dex */
public class ChatMessageCell extends BaseCell implements SeekBar.SeekBarDelegate, ImageReceiver.ImageReceiverDelegate, DownloadController.FileDownloadProgressListener, TextSelectionHelper.SelectableView, NotificationCenter.NotificationCenterDelegate {
    private static float[] radii = new float[8];
    private final boolean ALPHA_PROPERTY_WORKAROUND;
    public Property<ChatMessageCell, Float> ANIMATION_OFFSET_X;
    private int TAG;
    CharSequence accessibilityText;
    private boolean accessibilityTextContentUnread;
    private long accessibilityTextFileSize;
    private boolean accessibilityTextUnread;
    private SparseArray<Rect> accessibilityVirtualViewBounds;
    private int addedCaptionHeight;
    private boolean addedForTest;
    private int additionalTimeOffsetY;
    private StaticLayout adminLayout;
    private boolean allowAssistant;
    private float alphaInternal;
    private int animateFromStatusDrawableParams;
    private boolean animatePollAnswer;
    private boolean animatePollAnswerAlpha;
    private boolean animatePollAvatars;
    private int animateToStatusDrawableParams;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiDescriptionStack;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiReplyStack;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack;
    private int animatingDrawVideoImageButton;
    private float animatingDrawVideoImageButtonProgress;
    private float animatingLoadingProgressProgress;
    private int animatingNoSound;
    private boolean animatingNoSoundPlaying;
    private float animatingNoSoundProgress;
    private float animationOffsetX;
    private boolean animationRunning;
    private boolean attachedToWindow;
    private StaticLayout authorLayout;
    private int authorX;
    private boolean autoPlayingMedia;
    private int availableTimeWidth;
    private AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage;
    private boolean avatarPressed;
    private Theme.MessageDrawable.PathDrawParams backgroundCacheParams;
    private MessageBackgroundDrawable backgroundDrawable;
    private int backgroundDrawableBottom;
    private int backgroundDrawableLeft;
    private int backgroundDrawableRight;
    private int backgroundDrawableTop;
    private int backgroundHeight;
    private int backgroundWidth;
    private ImageReceiver blurredPhotoImage;
    private int blurredViewBottomOffset;
    private int blurredViewTopOffset;
    private Path botButtonPath;
    private float[] botButtonRadii;
    private ArrayList<BotButton> botButtons;
    private HashMap<String, BotButton> botButtonsByData;
    private HashMap<String, BotButton> botButtonsByPosition;
    private String botButtonsLayout;
    private boolean bottomNearToSet;
    private int buttonPressed;
    private int buttonState;
    private int buttonX;
    private int buttonY;
    private final boolean canDrawBackgroundInParent;
    private boolean canStreamVideo;
    private int captionHeight;
    private StaticLayout captionLayout;
    private int captionOffsetX;
    private AtomicReference<Layout> captionPatchedSpoilersLayout;
    private List<SpoilerEffect> captionSpoilers;
    private Stack<SpoilerEffect> captionSpoilersPool;
    private int captionWidth;
    private float captionX;
    private float captionY;
    private CheckBoxBase checkBox;
    private boolean checkBoxAnimationInProgress;
    private float checkBoxAnimationProgress;
    private int checkBoxTranslation;
    private boolean checkBoxVisible;
    private boolean checkOnlyButtonPressed;
    private String closeTimeText;
    private int closeTimeWidth;
    private int commentArrowX;
    private AvatarDrawable[] commentAvatarDrawables;
    private ImageReceiver[] commentAvatarImages;
    private boolean[] commentAvatarImagesVisible;
    private boolean commentButtonPressed;
    private Rect commentButtonRect;
    private boolean commentDrawUnread;
    private StaticLayout commentLayout;
    private AnimatedNumberLayout commentNumberLayout;
    private int commentNumberWidth;
    private InfiniteProgress commentProgress;
    private float commentProgressAlpha;
    private long commentProgressLastUpadteTime;
    private int commentUnreadX;
    private int commentWidth;
    private int commentX;
    private AvatarDrawable contactAvatarDrawable;
    private float controlsAlpha;
    private int currentAccount;
    private Theme.MessageDrawable currentBackgroundDrawable;
    private Theme.MessageDrawable currentBackgroundSelectedDrawable;
    private CharSequence currentCaption;
    private TLRPC$Chat currentChat;
    private int currentFocusedVirtualView;
    private TLRPC$Chat currentForwardChannel;
    private String currentForwardName;
    private String currentForwardNameString;
    private TLRPC$User currentForwardUser;
    private int currentMapProvider;
    private MessageObject currentMessageObject;
    private MessageObject.GroupedMessages currentMessagesGroup;
    private Object currentNameStatus;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable currentNameStatusDrawable;
    private String currentNameString;
    private TLRPC$FileLocation currentPhoto;
    private String currentPhotoFilter;
    private String currentPhotoFilterThumb;
    private TLRPC$PhotoSize currentPhotoObject;
    private TLRPC$PhotoSize currentPhotoObjectThumb;
    private BitmapDrawable currentPhotoObjectThumbStripped;
    private MessageObject.GroupedMessagePosition currentPosition;
    private String currentRepliesString;
    private TLRPC$PhotoSize currentReplyPhoto;
    private long currentReplyUserId;
    private float currentSelectedBackgroundAlpha;
    private CharSequence currentTimeString;
    private String currentUnlockString;
    private String currentUrl;
    private TLRPC$User currentUser;
    private TLRPC$User currentViaBotUser;
    private String currentViewsString;
    private WebFile currentWebFile;
    private ChatMessageCellDelegate delegate;
    private RectF deleteProgressRect;
    private StaticLayout descriptionLayout;
    private int descriptionX;
    private int descriptionY;
    private Runnable diceFinishCallback;
    private boolean disallowLongPress;
    private StaticLayout docTitleLayout;
    private int docTitleOffsetX;
    private int docTitleWidth;
    private TLRPC$Document documentAttach;
    private int documentAttachType;
    private boolean drawBackground;
    private boolean drawCommentButton;
    private boolean drawCommentNumber;
    public boolean drawForBlur;
    private boolean drawForwardedName;
    public boolean drawFromPinchToZoom;
    private boolean drawImageButton;
    private boolean drawInstantView;
    private int drawInstantViewType;
    private boolean drawMediaCheckBox;
    private boolean drawName;
    private boolean drawNameLayout;
    private boolean drawPhotoImage;
    public boolean drawPinnedBottom;
    private boolean drawPinnedTop;
    private boolean drawRadialCheckBackground;
    private boolean drawSelectionBackground;
    private int drawSideButton;
    private boolean drawTime;
    private float drawTimeX;
    private float drawTimeY;
    private boolean drawTopic;
    private boolean drawVideoImageButton;
    private boolean drawVideoSize;
    private Paint drillHolePaint;
    private Path drillHolePath;
    private StaticLayout durationLayout;
    private int durationWidth;
    private boolean edited;
    boolean enterTransitionInProgress;
    private boolean firstCircleLength;
    private int firstVisibleBlockNum;
    private FlagSecureReason flagSecure;
    private boolean flipImage;
    private boolean forceNotDrawTime;
    private boolean forwardBotPressed;
    private int forwardHeight;
    private int forwardNameCenterX;
    private float[] forwardNameOffsetX;
    private boolean forwardNamePressed;
    private float forwardNameX;
    private int forwardNameY;
    private StaticLayout[] forwardedNameLayout;
    private int forwardedNameWidth;
    private boolean fullyDraw;
    private boolean gamePreviewPressed;
    private LinearGradient gradientShader;
    private boolean groupPhotoInvisible;
    private MessageObject.GroupedMessages groupedMessagesToSet;
    private boolean hadLongPress;
    public boolean hasDiscussion;
    private boolean hasEmbed;
    private boolean hasGamePreview;
    private boolean hasInvoicePreview;
    private boolean hasLinkPreview;
    private int hasMiniProgress;
    private boolean hasNewLineForTime;
    private boolean hasOldCaptionPreview;
    private boolean hasPsaHint;
    private LinkPath highlightPath;
    private CharacterStyle highlightPathSpan;
    private long highlightPathStart;
    private int highlightProgress;
    private float hintButtonProgress;
    private boolean hintButtonVisible;
    private int imageBackgroundColor;
    private int imageBackgroundGradientColor1;
    private int imageBackgroundGradientColor2;
    private int imageBackgroundGradientColor3;
    private int imageBackgroundGradientRotation;
    private float imageBackgroundIntensity;
    private int imageBackgroundSideColor;
    private int imageBackgroundSideWidth;
    private boolean imageDrawn;
    private boolean imagePressed;
    boolean imageReceiversAttachState;
    private boolean inLayout;
    private StaticLayout infoLayout;
    private int infoWidth;
    private int infoX;
    private LoadingDrawable instantButtonLoading;
    private ValueAnimator instantButtonPressAnimator;
    private float instantButtonPressProgress;
    private boolean instantButtonPressed;
    private RectF instantButtonRect;
    private boolean instantPressed;
    private int instantTextLeftX;
    private boolean instantTextNewLine;
    private int instantTextX;
    private StaticLayout instantViewLayout;
    private int instantWidth;
    private Runnable invalidateRunnable;
    private boolean invalidateSpoilersParent;
    private boolean invalidatesParent;
    private boolean isAvatarVisible;
    public boolean isBlurred;
    public boolean isBot;
    private boolean isCaptionSpoilerPressed;
    public boolean isChat;
    private boolean isCheckPressed;
    public boolean isForum;
    public boolean isForumGeneral;
    private boolean isHighlighted;
    private boolean isHighlightedAnimated;
    public boolean isMegagroup;
    public boolean isPinned;
    public boolean isPinnedChat;
    private boolean isPlayingRound;
    private boolean isPressed;
    public boolean isRepliesChat;
    private boolean isRoundVideo;
    private boolean isSmallImage;
    private boolean isSpoilerRevealing;
    public boolean isThreadChat;
    private boolean isThreadPost;
    private boolean isUpdating;
    private int keyboardHeight;
    private long lastAnimationTime;
    private long lastCheckBoxAnimationTime;
    private long lastControlsAlphaChangeTime;
    private int lastDeleteDate;
    private float lastDrawingAudioProgress;
    private int lastHeight;
    private long lastHighlightProgressTime;
    private long lastLoadingSizeTotal;
    private long lastNamesAnimationTime;
    private TLRPC$Poll lastPoll;
    private long lastPollCloseTime;
    private ArrayList<TLRPC$TL_pollAnswerVoters> lastPollResults;
    private int lastPollResultsVoters;
    private String lastPostAuthor;
    private TLRPC$TL_messageReactions lastReactions;
    private int lastRepliesCount;
    private TLRPC$Message lastReplyMessage;
    private long lastSeekUpdateTime;
    private int lastSendState;
    int lastSize;
    private int lastTime;
    private float lastTouchX;
    private float lastTouchY;
    private boolean lastTranslated;
    private int lastViewsCount;
    private int lastVisibleBlockNum;
    private int lastWidth;
    private int layoutHeight;
    private int layoutWidth;
    private int linkBlockNum;
    private int linkPreviewHeight;
    private boolean linkPreviewPressed;
    private int linkSelectionBlockNum;
    public long linkedChatId;
    private LinkSpanDrawable.LinkCollector links;
    private StaticLayout loadingProgressLayout;
    private long loadingProgressLayoutHash;
    private boolean locationExpired;
    private ImageReceiver locationImageReceiver;
    private Drawable locationLoadingThumb;
    private boolean mediaBackground;
    private CheckBoxBase mediaCheckBox;
    private int mediaOffsetY;
    private SpoilerEffect mediaSpoilerEffect;
    private Path mediaSpoilerPath;
    private float[] mediaSpoilerRadii;
    private float mediaSpoilerRevealMaxRadius;
    private float mediaSpoilerRevealProgress;
    private float mediaSpoilerRevealX;
    private float mediaSpoilerRevealY;
    private boolean mediaWasInvisible;
    private MessageObject messageObjectToSet;
    private int miniButtonPressed;
    private int miniButtonState;
    private MotionBackgroundDrawable motionBackgroundDrawable;
    private StaticLayout nameLayout;
    private int nameLayoutWidth;
    private float nameOffsetX;
    private int nameWidth;
    private float nameX;
    private float nameY;
    private int namesOffset;
    private boolean needNewVisiblePart;
    public boolean needReplyImage;
    private int noSoundCenterX;
    private boolean otherPressed;
    private int otherX;
    private int otherY;
    private int overideShouldDrawTimeOnMedia;
    int parentBoundsBottom;
    float parentBoundsTop;
    private int parentHeight;
    public float parentViewTopOffset;
    private int parentWidth;
    private StaticLayout performerLayout;
    private int performerX;
    private ImageReceiver photoImage;
    private boolean photoImageOutOfBounds;
    private boolean photoNotSet;
    private TLObject photoParentObject;
    private StaticLayout photosCountLayout;
    private int photosCountWidth;
    public boolean pinnedBottom;
    public boolean pinnedTop;
    private float pollAnimationProgress;
    private float pollAnimationProgressTime;
    private AvatarDrawable[] pollAvatarDrawables;
    private ImageReceiver[] pollAvatarImages;
    private boolean[] pollAvatarImagesVisible;
    private ArrayList<PollButton> pollButtons;
    private CheckBoxBase[] pollCheckBox;
    private boolean pollClosed;
    private boolean pollHintPressed;
    private int pollHintX;
    private int pollHintY;
    private boolean pollInstantViewTouchesBottom;
    private boolean pollUnvoteInProgress;
    private boolean pollVoteInProgress;
    private int pollVoteInProgressNum;
    private boolean pollVoted;
    private int pressedBotButton;
    private AnimatedEmojiSpan pressedEmoji;
    private LinkSpanDrawable pressedLink;
    private int pressedLinkType;
    private int[] pressedState;
    private int pressedVoteButton;
    private CharacterStyle progressLoadingLink;
    private LoadingDrawable progressLoadingLinkCurrentDrawable;
    private ArrayList<LoadingDrawableLocation> progressLoadingLinkDrawables;
    private float psaButtonProgress;
    private boolean psaButtonVisible;
    private int psaHelpX;
    private int psaHelpY;
    private boolean psaHintPressed;
    private RadialProgress2 radialProgress;
    public final ReactionsLayoutInBubble reactionsLayoutInBubble;
    private RectF rect;
    private Path rectPath;
    private StaticLayout repliesLayout;
    private int repliesTextWidth;
    public float replyHeight;
    public ImageReceiver replyImageReceiver;
    public LoadingDrawable replyLoadingDrawable;
    private float[] replyLoadingSegment;
    private float replyLoadingT;
    public StaticLayout replyNameLayout;
    private int replyNameOffset;
    private int replyNameWidth;
    private boolean replyPanelIsForward;
    private boolean replyPressed;
    private AnimatedFloat replyPressedFloat;
    private Path replyRoundRectPath;
    private float[] replyRoundRectRadii;
    public Drawable replySelector;
    private boolean replySelectorCanBePressed;
    public int replySelectorColor;
    private boolean replySelectorPressed;
    public float replySelectorRadLeft;
    public float replySelectorRadRight;
    public Rect replySelectorRect;
    public List<SpoilerEffect> replySpoilers;
    private Stack<SpoilerEffect> replySpoilersPool;
    public int replyStartX;
    public int replyStartY;
    public StaticLayout replyTextLayout;
    private int replyTextOffset;
    private int replyTextWidth;
    private float replyTouchX;
    private float replyTouchY;
    private Theme.ResourcesProvider resourcesProvider;
    private float roundPlayingDrawableProgress;
    private float roundProgressAlpha;
    float roundSeekbarOutAlpha;
    float roundSeekbarOutProgress;
    int roundSeekbarTouched;
    private float roundToPauseProgress;
    private float roundToPauseProgress2;
    private AnimatedFloat roundVideoPlayPipFloat;
    private RoundVideoPlayingDrawable roundVideoPlayingDrawable;
    private Path sPath;
    private boolean scheduledInvalidate;
    private Rect scrollRect;
    private SeekBar seekBar;
    private SeekBarAccessibilityDelegate seekBarAccessibilityDelegate;
    private SeekBarWaveform seekBarWaveform;
    private int seekBarWaveformTranslateX;
    private int seekBarX;
    private int seekBarY;
    float seekbarRoundX;
    float seekbarRoundY;
    private float selectedBackgroundProgress;
    private Paint selectionOverlayPaint;
    private Drawable[] selectorDrawable;
    private int[] selectorDrawableMaskType;
    private AnimatorSet shakeAnimation;
    public boolean shouldCheckVisibleOnScreen;
    private boolean sideButtonPressed;
    private float sideStartX;
    private float sideStartY;
    private StaticLayout siteNameLayout;
    private boolean siteNameRtl;
    private int siteNameWidth;
    private float slidingOffsetX;
    private StaticLayout songLayout;
    private int songX;
    private SpoilerEffect spoilerPressed;
    private AtomicReference<Layout> spoilersPatchedReplyTextLayout;
    private boolean statusDrawableAnimationInProgress;
    private ValueAnimator statusDrawableAnimator;
    private float statusDrawableProgress;
    private int substractBackgroundHeight;
    private int textX;
    private int textY;
    private float timeAlpha;
    private int timeAudioX;
    private StaticLayout timeLayout;
    private boolean timePressed;
    private int timeTextWidth;
    private boolean timeWasInvisible;
    private int timeWidth;
    private int timeWidthAudio;
    private int timeX;
    private TimerParticles timerParticles;
    private float timerTransitionProgress;
    private StaticLayout titleLayout;
    private int titleX;
    private float toSeekBarProgress;
    private boolean topNearToSet;
    private MessageTopicButton topicButton;
    private long totalChangeTime;
    private int totalCommentWidth;
    private int totalHeight;
    private int totalVisibleBlocksCount;
    private TranscribeButton transcribeButton;
    private float transcribeX;
    private float transcribeY;
    private final TransitionParams transitionParams;
    float transitionYOffsetForDrawables;
    private LoadingDrawable translationLoadingDrawable;
    private StaticLayout translationLoadingDrawableLayout;
    private ArrayList<MessageObject.TextLayoutBlock> translationLoadingDrawableText;
    private AnimatedFloat translationLoadingFloat;
    private LinkPath translationLoadingPath;
    private float unlockAlpha;
    private StaticLayout unlockLayout;
    private SpoilerEffect unlockSpoilerEffect;
    private Path unlockSpoilerPath;
    private float[] unlockSpoilerRadii;
    private int unlockTextWidth;
    private float unlockX;
    private float unlockY;
    private int unmovedTextX;
    private ArrayList<LinkPath> urlPathCache;
    private ArrayList<LinkPath> urlPathSelection;
    private boolean useSeekBarWaveform;
    private boolean useTranscribeButton;
    private int viaNameWidth;
    private TypefaceSpan viaSpan1;
    private TypefaceSpan viaSpan2;
    private int viaWidth;
    private boolean vibrateOnPollVote;
    private int videoButtonPressed;
    private int videoButtonX;
    private int videoButtonY;
    VideoForwardDrawable videoForwardDrawable;
    private StaticLayout videoInfoLayout;
    VideoPlayerRewinder videoPlayerRewinder;
    private RadialProgress2 videoRadialProgress;
    private float viewTop;
    private StaticLayout viewsLayout;
    private int viewsTextWidth;
    private boolean visibleOnScreen;
    private float voteCurrentCircleLength;
    private float voteCurrentProgressTime;
    private long voteLastUpdateTime;
    private float voteRadOffset;
    private boolean voteRisingCircleLength;
    private boolean wasLayout;
    private boolean wasPinned;
    private boolean wasSending;
    private boolean wasTranscriptionOpen;
    private int widthBeforeNewTimeLine;
    private int widthForButtons;
    private boolean willRemoved;
    private boolean wouldBeInPip;

    public interface ChatMessageCellDelegate {

        /* renamed from: org.telegram.ui.Cells.ChatMessageCell$ChatMessageCellDelegate$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static boolean $default$canDrawOutboundsContent(ChatMessageCellDelegate chatMessageCellDelegate) {
                return true;
            }

            public static boolean $default$canPerformActions(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$didLongPress(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didLongPressBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
            }

            public static boolean $default$didLongPressChannelAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$Chat tLRPC$Chat, int i, float f, float f2) {
                return false;
            }

            public static boolean $default$didLongPressUserAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$User tLRPC$User, float f, float f2) {
                return false;
            }

            public static boolean $default$didPressAnimatedEmoji(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, AnimatedEmojiSpan animatedEmojiSpan) {
                return false;
            }

            public static void $default$didPressBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
            }

            public static void $default$didPressCancelSendButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressChannelAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$Chat tLRPC$Chat, int i, float f, float f2) {
            }

            public static void $default$didPressCommentButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressExtendedMediaPreview(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton) {
            }

            public static void $default$didPressHiddenForward(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressHint(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$didPressImage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressInstantButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$didPressOther(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressReaction(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$ReactionCount tLRPC$ReactionCount, boolean z) {
            }

            public static void $default$didPressReplyMessage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$didPressSideButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressTime(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressTopicButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressUrl(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, CharacterStyle characterStyle, boolean z) {
            }

            public static void $default$didPressUserAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC$User tLRPC$User, float f, float f2) {
            }

            public static void $default$didPressViaBot(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, String str) {
            }

            public static void $default$didPressViaBotNotInline(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, long j) {
            }

            public static void $default$didPressVoteButtons(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, ArrayList arrayList, int i, int i2, int i3) {
            }

            public static void $default$didStartVideoStream(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
            }

            public static String $default$getAdminRank(ChatMessageCellDelegate chatMessageCellDelegate, long j) {
                return null;
            }

            public static PinchToZoomHelper $default$getPinchToZoomHelper(ChatMessageCellDelegate chatMessageCellDelegate) {
                return null;
            }

            public static String $default$getProgressLoadingBotButtonUrl(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
                return null;
            }

            public static CharacterStyle $default$getProgressLoadingLink(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
                return null;
            }

            public static TextSelectionHelper.ChatListTextSelectionHelper $default$getTextSelectionHelper(ChatMessageCellDelegate chatMessageCellDelegate) {
                return null;
            }

            public static boolean $default$hasSelectedMessages(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$invalidateBlur(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static boolean $default$isLandscape(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static boolean $default$isProgressLoading(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
                return false;
            }

            public static boolean $default$isReplyOrSelf(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static boolean $default$keyboardIsOpened(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$needOpenWebView(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject, String str, String str2, String str3, String str4, int i, int i2) {
            }

            public static boolean $default$needPlayMessage(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject, boolean z) {
                return false;
            }

            public static void $default$needReloadPolls(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static void $default$needShowPremiumBulletin(ChatMessageCellDelegate chatMessageCellDelegate, int i) {
            }

            public static boolean $default$onAccessibilityAction(ChatMessageCellDelegate chatMessageCellDelegate, int i, Bundle bundle) {
                return false;
            }

            public static void $default$onDiceFinished(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static void $default$setShouldNotRepeatSticker(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
            }

            public static boolean $default$shouldDrawThreadProgress(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
                return false;
            }

            public static boolean $default$shouldRepeatSticker(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
                return true;
            }

            public static boolean $default$shouldShowTopicButton(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$videoTimerReached(ChatMessageCellDelegate chatMessageCellDelegate) {
            }
        }

        boolean canDrawOutboundsContent();

        boolean canPerformActions();

        void didLongPress(ChatMessageCell chatMessageCell, float f, float f2);

        void didLongPressBotButton(ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton);

        boolean didLongPressChannelAvatar(ChatMessageCell chatMessageCell, TLRPC$Chat tLRPC$Chat, int i, float f, float f2);

        boolean didLongPressUserAvatar(ChatMessageCell chatMessageCell, TLRPC$User tLRPC$User, float f, float f2);

        boolean didPressAnimatedEmoji(ChatMessageCell chatMessageCell, AnimatedEmojiSpan animatedEmojiSpan);

        void didPressBotButton(ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton);

        void didPressCancelSendButton(ChatMessageCell chatMessageCell);

        void didPressChannelAvatar(ChatMessageCell chatMessageCell, TLRPC$Chat tLRPC$Chat, int i, float f, float f2);

        void didPressCommentButton(ChatMessageCell chatMessageCell);

        void didPressExtendedMediaPreview(ChatMessageCell chatMessageCell, TLRPC$KeyboardButton tLRPC$KeyboardButton);

        void didPressHiddenForward(ChatMessageCell chatMessageCell);

        void didPressHint(ChatMessageCell chatMessageCell, int i);

        void didPressImage(ChatMessageCell chatMessageCell, float f, float f2);

        void didPressInstantButton(ChatMessageCell chatMessageCell, int i);

        void didPressOther(ChatMessageCell chatMessageCell, float f, float f2);

        void didPressReaction(ChatMessageCell chatMessageCell, TLRPC$ReactionCount tLRPC$ReactionCount, boolean z);

        void didPressReplyMessage(ChatMessageCell chatMessageCell, int i);

        void didPressSideButton(ChatMessageCell chatMessageCell);

        void didPressTime(ChatMessageCell chatMessageCell);

        void didPressTopicButton(ChatMessageCell chatMessageCell);

        void didPressUrl(ChatMessageCell chatMessageCell, CharacterStyle characterStyle, boolean z);

        void didPressUserAvatar(ChatMessageCell chatMessageCell, TLRPC$User tLRPC$User, float f, float f2);

        void didPressViaBot(ChatMessageCell chatMessageCell, String str);

        void didPressViaBotNotInline(ChatMessageCell chatMessageCell, long j);

        void didPressVoteButtons(ChatMessageCell chatMessageCell, ArrayList<TLRPC$TL_pollAnswer> arrayList, int i, int i2, int i3);

        void didStartVideoStream(MessageObject messageObject);

        String getAdminRank(long j);

        PinchToZoomHelper getPinchToZoomHelper();

        String getProgressLoadingBotButtonUrl(ChatMessageCell chatMessageCell);

        CharacterStyle getProgressLoadingLink(ChatMessageCell chatMessageCell);

        TextSelectionHelper.ChatListTextSelectionHelper getTextSelectionHelper();

        boolean hasSelectedMessages();

        void invalidateBlur();

        boolean isLandscape();

        boolean isProgressLoading(ChatMessageCell chatMessageCell, int i);

        boolean isReplyOrSelf();

        boolean keyboardIsOpened();

        void needOpenWebView(MessageObject messageObject, String str, String str2, String str3, String str4, int i, int i2);

        boolean needPlayMessage(MessageObject messageObject, boolean z);

        void needReloadPolls();

        void needShowPremiumBulletin(int i);

        boolean onAccessibilityAction(int i, Bundle bundle);

        void onDiceFinished();

        void setShouldNotRepeatSticker(MessageObject messageObject);

        boolean shouldDrawThreadProgress(ChatMessageCell chatMessageCell);

        boolean shouldRepeatSticker(MessageObject messageObject);

        boolean shouldShowTopicButton();

        void videoTimerReached();
    }

    private boolean checkNeedDrawShareButton(MessageObject messageObject) {
        return false;
    }

    private boolean intersect(float f, float f2, float f3, float f4) {
        return f <= f3 ? f2 >= f3 : f <= f4;
    }

    public RadialProgress2 getRadialProgress() {
        return this.radialProgress;
    }

    public void setEnterTransitionInProgress(boolean z) {
        this.enterTransitionInProgress = z;
        invalidate();
    }

    public ReactionsLayoutInBubble.ReactionButton getReactionButton(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        return this.reactionsLayoutInBubble.getReactionButton(visibleReaction);
    }

    public MessageObject getPrimaryMessageObject() {
        MessageObject messageObject = this.currentMessageObject;
        MessageObject findPrimaryMessageObject = (messageObject == null || this.currentMessagesGroup == null || !messageObject.hasValidGroupId()) ? null : this.currentMessagesGroup.findPrimaryMessageObject();
        return findPrimaryMessageObject != null ? findPrimaryMessageObject : this.currentMessageObject;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.startSpoilers) {
            setSpoilersSuppressed(false);
            return;
        }
        if (i == NotificationCenter.stopSpoilers) {
            setSpoilersSuppressed(true);
            return;
        }
        if (i == NotificationCenter.userInfoDidLoad) {
            TLRPC$User tLRPC$User = this.currentUser;
            if (tLRPC$User != null) {
                if (tLRPC$User.id == ((Long) objArr[0]).longValue()) {
                    setAvatar(this.currentMessageObject);
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            invalidate();
        }
    }

    private void setAvatar(final MessageObject messageObject) {
        TLRPC$Photo tLRPC$Photo;
        if (messageObject == null) {
            return;
        }
        if (this.isAvatarVisible) {
            Drawable drawable = messageObject.customAvatarDrawable;
            if (drawable != null) {
                this.avatarImage.setImageBitmap(drawable);
                return;
            }
            TLRPC$User tLRPC$User = this.currentUser;
            if (tLRPC$User != null) {
                TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto = tLRPC$User.photo;
                if (tLRPC$UserProfilePhoto != null) {
                    this.currentPhoto = tLRPC$UserProfilePhoto.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChatMessageCell.this.lambda$setAvatar$0();
                    }
                });
                return;
            }
            TLRPC$Chat tLRPC$Chat = this.currentChat;
            if (tLRPC$Chat != null) {
                TLRPC$ChatPhoto tLRPC$ChatPhoto = tLRPC$Chat.photo;
                if (tLRPC$ChatPhoto != null) {
                    this.currentPhoto = tLRPC$ChatPhoto.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChatMessageCell.this.lambda$setAvatar$1();
                    }
                });
                return;
            }
            if (messageObject.isSponsored()) {
                TLRPC$ChatInvite tLRPC$ChatInvite = messageObject.sponsoredChatInvite;
                if (tLRPC$ChatInvite != null && tLRPC$ChatInvite.chat != null) {
                    post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            ChatMessageCell.this.lambda$setAvatar$2(messageObject);
                        }
                    });
                    return;
                }
                this.avatarDrawable.setInfo(tLRPC$ChatInvite);
                TLRPC$ChatInvite tLRPC$ChatInvite2 = messageObject.sponsoredChatInvite;
                if (tLRPC$ChatInvite2 == null || (tLRPC$Photo = tLRPC$ChatInvite2.photo) == null) {
                    return;
                }
                this.avatarImage.setImage(ImageLocation.getForPhoto(tLRPC$Photo.sizes.get(0), tLRPC$Photo), "50_50", this.avatarDrawable, null, null, 0);
                return;
            }
            this.currentPhoto = null;
            this.avatarDrawable.setInfo(messageObject.getFromChatId(), null, null);
            this.avatarImage.setImage(null, null, this.avatarDrawable, null, null, 0);
            return;
        }
        this.currentPhoto = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAvatar$0() {
        this.avatarDrawable.setInfo(this.currentUser);
        this.avatarImage.setForUserOrChat(this.currentUser, this.avatarDrawable, null, LiteMode.isEnabled(LiteMode.FLAGS_CHAT), 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAvatar$1() {
        this.avatarDrawable.setInfo(this.currentChat);
        this.avatarImage.setForUserOrChat(this.currentChat, this.avatarDrawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setAvatar$2(MessageObject messageObject) {
        this.avatarDrawable.setInfo(messageObject.sponsoredChatInvite.chat);
        this.avatarImage.setForUserOrChat(messageObject.sponsoredChatInvite.chat, this.avatarDrawable);
    }

    public void setSpoilersSuppressed(boolean z) {
        for (int i = 0; i < this.captionSpoilers.size(); i++) {
            this.captionSpoilers.get(i).setSuppressUpdates(z);
        }
        for (int i2 = 0; i2 < this.replySpoilers.size(); i2++) {
            this.replySpoilers.get(i2).setSuppressUpdates(z);
        }
        if (getMessageObject() == null || getMessageObject().textLayoutBlocks == null) {
            return;
        }
        for (int i3 = 0; i3 < getMessageObject().textLayoutBlocks.size(); i3++) {
            MessageObject.TextLayoutBlock textLayoutBlock = getMessageObject().textLayoutBlocks.get(i3);
            for (int i4 = 0; i4 < textLayoutBlock.spoilers.size(); i4++) {
                textLayoutBlock.spoilers.get(i4).setSuppressUpdates(z);
            }
        }
    }

    public boolean hasSpoilers() {
        if ((hasCaptionLayout() && !this.captionSpoilers.isEmpty()) || (this.replyTextLayout != null && !this.replySpoilers.isEmpty())) {
            return true;
        }
        if (getMessageObject() == null || getMessageObject().textLayoutBlocks == null) {
            return false;
        }
        Iterator<MessageObject.TextLayoutBlock> it = getMessageObject().textLayoutBlocks.iterator();
        while (it.hasNext()) {
            if (!it.next().spoilers.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void updateSpoilersVisiblePart(int i, int i2) {
        if (hasCaptionLayout()) {
            float f = -this.captionY;
            Iterator<SpoilerEffect> it = this.captionSpoilers.iterator();
            while (it.hasNext()) {
                it.next().setVisibleBounds(0.0f, i + f, getWidth(), i2 + f);
            }
        }
        StaticLayout staticLayout = this.replyTextLayout;
        if (staticLayout != null) {
            float height = (-this.replyStartY) - staticLayout.getHeight();
            Iterator<SpoilerEffect> it2 = this.replySpoilers.iterator();
            while (it2.hasNext()) {
                it2.next().setVisibleBounds(0.0f, i + height, getWidth(), i2 + height);
            }
        }
        if (getMessageObject() == null || getMessageObject().textLayoutBlocks == null) {
            return;
        }
        Iterator<MessageObject.TextLayoutBlock> it3 = getMessageObject().textLayoutBlocks.iterator();
        while (it3.hasNext()) {
            MessageObject.TextLayoutBlock next = it3.next();
            Iterator<SpoilerEffect> it4 = next.spoilers.iterator();
            while (it4.hasNext()) {
                it4.next().setVisibleBounds(0.0f, (i - next.textYOffset) - this.textY, getWidth(), (i2 - next.textYOffset) - this.textY);
            }
        }
    }

    public void setScrimReaction(String str) {
        this.reactionsLayoutInBubble.setScrimReaction(str);
    }

    public void drawScrimReaction(Canvas canvas, String str) {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i = groupedMessagePosition.flags;
            if ((i & 8) == 0 || (i & 1) == 0) {
                return;
            }
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble.isSmall) {
            return;
        }
        reactionsLayoutInBubble.draw(canvas, this.transitionParams.animateChangeProgress, str);
    }

    public boolean checkUnreadReactions(float f, int i) {
        if (!this.reactionsLayoutInBubble.hasUnreadReactions) {
            return false;
        }
        float y = getY();
        float f2 = y + r2.y;
        return f2 > f && (f2 + ((float) this.reactionsLayoutInBubble.height)) - ((float) AndroidUtilities.dp(16.0f)) < ((float) i);
    }

    public void markReactionsAsRead() {
        this.reactionsLayoutInBubble.hasUnreadReactions = false;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.markReactionsAsRead();
    }

    public void setVisibleOnScreen(boolean z) {
        if (this.visibleOnScreen != z) {
            this.visibleOnScreen = z;
            checkImageReceiversAttachState();
            if (z) {
                invalidate();
            }
        }
    }

    public void setParentBounds(float f, int i) {
        this.parentBoundsTop = f;
        this.parentBoundsBottom = i;
        if (this.photoImageOutOfBounds) {
            float y = getY() + this.photoImage.getImageY();
            if (this.photoImage.getImageHeight() + y < this.parentBoundsTop || y > this.parentBoundsBottom) {
                return;
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BotButton {
        private int angle;
        private TLRPC$KeyboardButton button;
        private int height;
        private boolean isInviteButton;
        private long lastUpdateTime;
        private LoadingDrawable loadingDrawable;
        private int positionFlags;
        private ValueAnimator pressAnimator;
        private float pressT;
        private boolean pressed;
        private float progressAlpha;
        private Drawable selectorDrawable;
        private StaticLayout title;
        private int width;
        private int x;
        private int y;

        private BotButton() {
        }

        static /* synthetic */ int access$3576(BotButton botButton, int i) {
            int i2 = i | botButton.positionFlags;
            botButton.positionFlags = i2;
            return i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPressed(boolean z) {
            ValueAnimator valueAnimator;
            if (this.pressed != z) {
                this.pressed = z;
                ChatMessageCell.this.invalidateOutbounds();
                if (z && (valueAnimator = this.pressAnimator) != null) {
                    valueAnimator.removeAllListeners();
                    this.pressAnimator.cancel();
                }
                if (z) {
                    return;
                }
                float f = this.pressT;
                if (f != 0.0f) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f, 0.0f);
                    this.pressAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$BotButton$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            ChatMessageCell.BotButton.this.lambda$setPressed$0(valueAnimator2);
                        }
                    });
                    this.pressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.BotButton.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            BotButton.this.pressAnimator = null;
                        }
                    });
                    this.pressAnimator.setInterpolator(new OvershootInterpolator(2.0f));
                    this.pressAnimator.setDuration(350L);
                    this.pressAnimator.start();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setPressed$0(ValueAnimator valueAnimator) {
            this.pressT = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            ChatMessageCell.this.invalidateOutbounds();
        }

        public boolean hasPositionFlag(int i) {
            return (this.positionFlags & i) == i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getPressScale() {
            if (this.pressed) {
                float f = this.pressT;
                if (f != 1.0f) {
                    float min = f + (Math.min(40.0f, 1000.0f / AndroidUtilities.screenRefreshRate) / 100.0f);
                    this.pressT = min;
                    this.pressT = Utilities.clamp(min, 1.0f, 0.0f);
                    ChatMessageCell.this.invalidateOutbounds();
                }
            }
            return ((1.0f - this.pressT) * 0.04f) + 0.96f;
        }
    }

    public static class PollButton {
        private TLRPC$TL_pollAnswer answer;
        private boolean chosen;
        private boolean correct;
        private int count;
        private float decimal;
        public int height;
        private int percent;
        private float percentProgress;
        private boolean prevChosen;
        private int prevPercent;
        private float prevPercentProgress;
        private StaticLayout title;
        public int x;
        public int y;

        static /* synthetic */ int access$2212(PollButton pollButton, int i) {
            int i2 = pollButton.percent + i;
            pollButton.percent = i2;
            return i2;
        }

        static /* synthetic */ float access$2924(PollButton pollButton, float f) {
            float f2 = pollButton.decimal - f;
            pollButton.decimal = f2;
            return f2;
        }
    }

    class LoadingDrawableLocation {
        int blockNum;
        LoadingDrawable drawable;

        LoadingDrawableLocation(ChatMessageCell chatMessageCell) {
        }
    }

    public ChatMessageCell(Context context) {
        this(context, false, null);
    }

    public ChatMessageCell(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.visibleOnScreen = true;
        this.reactionsLayoutInBubble = new ReactionsLayoutInBubble(this);
        this.scrollRect = new Rect();
        this.imageBackgroundGradientRotation = 45;
        this.selectorDrawable = new Drawable[2];
        this.selectorDrawableMaskType = new int[2];
        this.instantButtonRect = new RectF();
        this.pressedState = new int[]{R.attr.state_enabled, R.attr.state_pressed};
        this.deleteProgressRect = new RectF();
        this.rect = new RectF();
        this.timeAlpha = 1.0f;
        this.controlsAlpha = 1.0f;
        this.links = new LinkSpanDrawable.LinkCollector(this);
        this.urlPathCache = new ArrayList<>();
        this.urlPathSelection = new ArrayList<>();
        this.rectPath = new Path();
        this.pollButtons = new ArrayList<>();
        this.botButtons = new ArrayList<>();
        this.botButtonPath = new Path();
        this.botButtonRadii = new float[8];
        this.botButtonsByData = new HashMap<>();
        this.botButtonsByPosition = new HashMap<>();
        this.currentAccount = UserConfig.selectedAccount;
        this.isCheckPressed = true;
        this.drawBackground = true;
        this.backgroundWidth = 100;
        this.commentButtonRect = new Rect();
        this.spoilersPatchedReplyTextLayout = new AtomicReference<>();
        this.forwardedNameLayout = new StaticLayout[2];
        this.forwardNameOffsetX = new float[2];
        this.drawTime = true;
        this.mediaSpoilerPath = new Path();
        this.mediaSpoilerRadii = new float[8];
        this.mediaSpoilerEffect = new SpoilerEffect();
        this.unlockAlpha = 1.0f;
        this.unlockSpoilerEffect = new SpoilerEffect();
        this.unlockSpoilerPath = new Path();
        this.unlockSpoilerRadii = new float[8];
        this.replySelectorRect = new Rect();
        this.ALPHA_PROPERTY_WORKAROUND = Build.VERSION.SDK_INT == 28;
        this.alphaInternal = 1.0f;
        this.transitionParams = new TransitionParams();
        this.roundVideoPlayPipFloat = new AnimatedFloat(this, 200L, CubicBezierInterpolator.EASE_OUT);
        this.diceFinishCallback = new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell.1
            @Override // java.lang.Runnable
            public void run() {
                if (ChatMessageCell.this.delegate != null) {
                    ChatMessageCell.this.delegate.onDiceFinished();
                }
            }
        };
        this.invalidateRunnable = new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell.2
            @Override // java.lang.Runnable
            public void run() {
                ChatMessageCell.this.checkLocationExpired();
                if (ChatMessageCell.this.locationExpired) {
                    ChatMessageCell.this.invalidate();
                    ChatMessageCell.this.scheduledInvalidate = false;
                    return;
                }
                ChatMessageCell.this.invalidate(((int) r0.rect.left) - 5, ((int) ChatMessageCell.this.rect.top) - 5, ((int) ChatMessageCell.this.rect.right) + 5, ((int) ChatMessageCell.this.rect.bottom) + 5);
                if (ChatMessageCell.this.scheduledInvalidate) {
                    AndroidUtilities.runOnUIThread(ChatMessageCell.this.invalidateRunnable, 1000L);
                }
            }
        };
        this.accessibilityVirtualViewBounds = new SparseArray<>();
        this.currentFocusedVirtualView = -1;
        this.backgroundCacheParams = new Theme.MessageDrawable.PathDrawParams();
        this.replySpoilers = new ArrayList();
        this.replySpoilersPool = new Stack<>();
        this.captionSpoilers = new ArrayList();
        this.captionSpoilersPool = new Stack<>();
        this.captionPatchedSpoilersLayout = new AtomicReference<>();
        this.sPath = new Path();
        this.hadLongPress = false;
        this.ANIMATION_OFFSET_X = new Property<ChatMessageCell, Float>(this, Float.class, "animationOffsetX") { // from class: org.telegram.ui.Cells.ChatMessageCell.12
            @Override // android.util.Property
            public Float get(ChatMessageCell chatMessageCell) {
                return Float.valueOf(chatMessageCell.animationOffsetX);
            }

            @Override // android.util.Property
            public void set(ChatMessageCell chatMessageCell, Float f) {
                chatMessageCell.setAnimationOffsetX(f.floatValue());
            }
        };
        this.resourcesProvider = resourcesProvider;
        this.canDrawBackgroundInParent = z;
        this.backgroundDrawable = new MessageBackgroundDrawable(this);
        ImageReceiver imageReceiver = new ImageReceiver();
        this.avatarImage = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarDrawable = new AvatarDrawable();
        ImageReceiver imageReceiver2 = new ImageReceiver(this);
        this.replyImageReceiver = imageReceiver2;
        imageReceiver2.setAllowLoadingOnAttachedOnly(true);
        this.replyImageReceiver.setRoundRadius(AndroidUtilities.dp(4.0f));
        ImageReceiver imageReceiver3 = new ImageReceiver(this);
        this.locationImageReceiver = imageReceiver3;
        imageReceiver3.setAllowLoadingOnAttachedOnly(true);
        this.locationImageReceiver.setRoundRadius(AndroidUtilities.dp(26.1f));
        this.TAG = DownloadController.getInstance(this.currentAccount).generateObserverTag();
        this.contactAvatarDrawable = new AvatarDrawable();
        ImageReceiver imageReceiver4 = new ImageReceiver(this);
        this.photoImage = imageReceiver4;
        imageReceiver4.setAllowLoadingOnAttachedOnly(true);
        this.photoImage.setUseRoundForThumbDrawable(true);
        this.photoImage.setDelegate(this);
        ImageReceiver imageReceiver5 = new ImageReceiver(this);
        this.blurredPhotoImage = imageReceiver5;
        imageReceiver5.setAllowLoadingOnAttachedOnly(true);
        this.blurredPhotoImage.setUseRoundForThumbDrawable(true);
        this.radialProgress = new RadialProgress2(this, resourcesProvider);
        RadialProgress2 radialProgress2 = new RadialProgress2(this, resourcesProvider);
        this.videoRadialProgress = radialProgress2;
        radialProgress2.setDrawBackground(false);
        this.videoRadialProgress.setCircleRadius(AndroidUtilities.dp(15.0f));
        SeekBar seekBar = new SeekBar(this) { // from class: org.telegram.ui.Cells.ChatMessageCell.3
            @Override // org.telegram.ui.Components.SeekBar
            protected void onTimestampUpdate(URLSpanNoUnderline uRLSpanNoUnderline) {
                ChatMessageCell.this.highlightCaptionLink(uRLSpanNoUnderline);
            }
        };
        this.seekBar = seekBar;
        seekBar.setDelegate(this);
        SeekBarWaveform seekBarWaveform = new SeekBarWaveform(context);
        this.seekBarWaveform = seekBarWaveform;
        seekBarWaveform.setDelegate(this);
        this.seekBarWaveform.setParentView(this);
        this.seekBarAccessibilityDelegate = new FloatSeekBarAccessibilityDelegate() { // from class: org.telegram.ui.Cells.ChatMessageCell.4
            @Override // org.telegram.ui.Components.FloatSeekBarAccessibilityDelegate
            public float getProgress() {
                if (ChatMessageCell.this.currentMessageObject.isMusic()) {
                    return ChatMessageCell.this.seekBar.getProgress();
                }
                if (ChatMessageCell.this.currentMessageObject.isVoice()) {
                    return ChatMessageCell.this.useSeekBarWaveform ? ChatMessageCell.this.seekBarWaveform.getProgress() : ChatMessageCell.this.seekBar.getProgress();
                }
                if (ChatMessageCell.this.currentMessageObject.isRoundVideo()) {
                    return ChatMessageCell.this.currentMessageObject.audioProgress;
                }
                return 0.0f;
            }

            @Override // org.telegram.ui.Components.FloatSeekBarAccessibilityDelegate
            public void setProgress(float f) {
                if (ChatMessageCell.this.currentMessageObject.isMusic()) {
                    ChatMessageCell.this.seekBar.setProgress(f);
                } else if (ChatMessageCell.this.currentMessageObject.isVoice()) {
                    if (ChatMessageCell.this.useSeekBarWaveform) {
                        ChatMessageCell.this.seekBarWaveform.setProgress(f);
                    } else {
                        ChatMessageCell.this.seekBar.setProgress(f);
                    }
                } else {
                    if (!ChatMessageCell.this.currentMessageObject.isRoundVideo()) {
                        return;
                    }
                    if (ChatMessageCell.this.useSeekBarWaveform) {
                        if (ChatMessageCell.this.seekBarWaveform != null) {
                            ChatMessageCell.this.seekBarWaveform.setProgress(f);
                        }
                    } else if (ChatMessageCell.this.seekBar != null) {
                        ChatMessageCell.this.seekBar.setProgress(f);
                    }
                    ChatMessageCell.this.currentMessageObject.audioProgress = f;
                }
                ChatMessageCell.this.onSeekBarDrag(f);
                ChatMessageCell.this.invalidate();
            }
        };
        this.roundVideoPlayingDrawable = new RoundVideoPlayingDrawable(this, resourcesProvider);
        setImportantForAccessibility(1);
    }

    public void setResourcesProvider(Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        RadialProgress2 radialProgress2 = this.radialProgress;
        if (radialProgress2 != null) {
            radialProgress2.setResourcesProvider(resourcesProvider);
        }
        RadialProgress2 radialProgress22 = this.videoRadialProgress;
        if (radialProgress22 != null) {
            radialProgress22.setResourcesProvider(resourcesProvider);
        }
        RoundVideoPlayingDrawable roundVideoPlayingDrawable = this.roundVideoPlayingDrawable;
        if (roundVideoPlayingDrawable != null) {
            roundVideoPlayingDrawable.setResourcesProvider(resourcesProvider);
        }
    }

    private void createPollUI() {
        if (this.pollAvatarImages != null) {
            return;
        }
        this.pollAvatarImages = new ImageReceiver[3];
        this.pollAvatarDrawables = new AvatarDrawable[3];
        this.pollAvatarImagesVisible = new boolean[3];
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.pollAvatarImages;
            if (i >= imageReceiverArr.length) {
                break;
            }
            imageReceiverArr[i] = new ImageReceiver(this);
            this.pollAvatarImages[i].setRoundRadius(AndroidUtilities.dp(8.0f));
            this.pollAvatarDrawables[i] = new AvatarDrawable();
            this.pollAvatarDrawables[i].setTextSize(AndroidUtilities.dp(22.0f));
            i++;
        }
        this.pollCheckBox = new CheckBoxBase[10];
        int i2 = 0;
        while (true) {
            CheckBoxBase[] checkBoxBaseArr = this.pollCheckBox;
            if (i2 >= checkBoxBaseArr.length) {
                return;
            }
            checkBoxBaseArr[i2] = new CheckBoxBase(this, 20, this.resourcesProvider);
            this.pollCheckBox[i2].setDrawUnchecked(false);
            this.pollCheckBox[i2].setBackgroundType(9);
            i2++;
        }
    }

    private void createCommentUI() {
        if (this.commentAvatarImages != null) {
            return;
        }
        this.commentAvatarImages = new ImageReceiver[3];
        this.commentAvatarDrawables = new AvatarDrawable[3];
        this.commentAvatarImagesVisible = new boolean[3];
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.commentAvatarImages;
            if (i >= imageReceiverArr.length) {
                return;
            }
            imageReceiverArr[i] = new ImageReceiver(this);
            this.commentAvatarImages[i].setRoundRadius(AndroidUtilities.dp(12.0f));
            this.commentAvatarDrawables[i] = new AvatarDrawable();
            this.commentAvatarDrawables[i].setTextSize(AndroidUtilities.dp(18.0f));
            i++;
        }
    }

    public void resetPressedLink(int i) {
        if (i != -1) {
            this.links.removeLinks(Integer.valueOf(i));
        } else {
            this.links.clear();
        }
        this.pressedEmoji = null;
        if (this.pressedLink != null) {
            if (this.pressedLinkType == i || i == -1) {
                this.pressedLink = null;
                this.pressedLinkType = -1;
                invalidate();
            }
        }
    }

    private void resetUrlPaths() {
        if (this.urlPathSelection.isEmpty()) {
            return;
        }
        this.urlPathCache.addAll(this.urlPathSelection);
        this.urlPathSelection.clear();
    }

    private LinkPath obtainNewUrlPath() {
        LinkPath linkPath;
        if (!this.urlPathCache.isEmpty()) {
            linkPath = this.urlPathCache.get(0);
            this.urlPathCache.remove(0);
        } else {
            linkPath = new LinkPath(true);
        }
        linkPath.reset();
        this.urlPathSelection.add(linkPath);
        return linkPath;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getRealSpanStartAndEnd(Spannable spannable, CharacterStyle characterStyle) {
        int i;
        int i2;
        boolean z;
        TextStyleSpan.TextStyleRun style;
        TLRPC$MessageEntity tLRPC$MessageEntity;
        if (!(characterStyle instanceof URLSpanBrowser) || (style = ((URLSpanBrowser) characterStyle).getStyle()) == null || (tLRPC$MessageEntity = style.urlEntity) == null) {
            i = 0;
            i2 = 0;
            z = false;
        } else {
            i2 = tLRPC$MessageEntity.offset;
            i = tLRPC$MessageEntity.length + i2;
            z = true;
        }
        if (!z) {
            i2 = spannable.getSpanStart(characterStyle);
            i = spannable.getSpanEnd(characterStyle);
        }
        return new int[]{i2, i};
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0277 A[Catch: Exception -> 0x02b3, TryCatch #1 {Exception -> 0x02b3, blocks: (B:34:0x0077, B:36:0x008c, B:37:0x0092, B:39:0x00b4, B:41:0x00bf, B:43:0x00cf, B:48:0x00e0, B:50:0x00ec, B:52:0x00ef, B:54:0x00f5, B:59:0x00ff, B:61:0x0105, B:63:0x010b, B:65:0x0111, B:67:0x0115, B:69:0x0273, B:71:0x0119, B:72:0x0126, B:74:0x012a, B:76:0x0132, B:78:0x0154, B:79:0x0159, B:128:0x0268, B:130:0x0265, B:131:0x0157, B:132:0x0277, B:134:0x027d, B:136:0x0283, B:138:0x028c, B:140:0x0292, B:141:0x0298, B:143:0x029c, B:145:0x02a4, B:148:0x00e3, B:149:0x00d5, B:81:0x0164, B:83:0x0196, B:84:0x0198, B:86:0x01a2, B:88:0x01ae, B:90:0x01c5, B:92:0x01c8, B:94:0x01d3, B:96:0x01f6, B:103:0x01b9, B:105:0x01f9, B:107:0x01ff, B:109:0x0203, B:111:0x020f, B:113:0x022e, B:115:0x0231, B:117:0x023c, B:125:0x021e), top: B:33:0x0077, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00ff A[Catch: Exception -> 0x02b3, TryCatch #1 {Exception -> 0x02b3, blocks: (B:34:0x0077, B:36:0x008c, B:37:0x0092, B:39:0x00b4, B:41:0x00bf, B:43:0x00cf, B:48:0x00e0, B:50:0x00ec, B:52:0x00ef, B:54:0x00f5, B:59:0x00ff, B:61:0x0105, B:63:0x010b, B:65:0x0111, B:67:0x0115, B:69:0x0273, B:71:0x0119, B:72:0x0126, B:74:0x012a, B:76:0x0132, B:78:0x0154, B:79:0x0159, B:128:0x0268, B:130:0x0265, B:131:0x0157, B:132:0x0277, B:134:0x027d, B:136:0x0283, B:138:0x028c, B:140:0x0292, B:141:0x0298, B:143:0x029c, B:145:0x02a4, B:148:0x00e3, B:149:0x00d5, B:81:0x0164, B:83:0x0196, B:84:0x0198, B:86:0x01a2, B:88:0x01ae, B:90:0x01c5, B:92:0x01c8, B:94:0x01d3, B:96:0x01f6, B:103:0x01b9, B:105:0x01f9, B:107:0x01ff, B:109:0x0203, B:111:0x020f, B:113:0x022e, B:115:0x0231, B:117:0x023c, B:125:0x021e), top: B:33:0x0077, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x010b A[Catch: Exception -> 0x02b3, TryCatch #1 {Exception -> 0x02b3, blocks: (B:34:0x0077, B:36:0x008c, B:37:0x0092, B:39:0x00b4, B:41:0x00bf, B:43:0x00cf, B:48:0x00e0, B:50:0x00ec, B:52:0x00ef, B:54:0x00f5, B:59:0x00ff, B:61:0x0105, B:63:0x010b, B:65:0x0111, B:67:0x0115, B:69:0x0273, B:71:0x0119, B:72:0x0126, B:74:0x012a, B:76:0x0132, B:78:0x0154, B:79:0x0159, B:128:0x0268, B:130:0x0265, B:131:0x0157, B:132:0x0277, B:134:0x027d, B:136:0x0283, B:138:0x028c, B:140:0x0292, B:141:0x0298, B:143:0x029c, B:145:0x02a4, B:148:0x00e3, B:149:0x00d5, B:81:0x0164, B:83:0x0196, B:84:0x0198, B:86:0x01a2, B:88:0x01ae, B:90:0x01c5, B:92:0x01c8, B:94:0x01d3, B:96:0x01f6, B:103:0x01b9, B:105:0x01f9, B:107:0x01ff, B:109:0x0203, B:111:0x020f, B:113:0x022e, B:115:0x0231, B:117:0x023c, B:125:0x021e), top: B:33:0x0077, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkTextBlockMotionEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 700
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkTextBlockMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x00c5 A[Catch: Exception -> 0x0142, TryCatch #1 {Exception -> 0x0142, blocks: (B:39:0x0055, B:41:0x0074, B:43:0x007f, B:45:0x008d, B:48:0x009a, B:50:0x00a6, B:52:0x00a9, B:54:0x00af, B:59:0x00b9, B:61:0x00bf, B:63:0x00c5, B:64:0x013e, B:67:0x00d1, B:69:0x00d5, B:71:0x00dd, B:73:0x00ff, B:74:0x0104, B:77:0x0133, B:80:0x0130, B:81:0x0102, B:83:0x009d, B:84:0x0090, B:76:0x010d), top: B:38:0x0055, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d1 A[Catch: Exception -> 0x0142, TryCatch #1 {Exception -> 0x0142, blocks: (B:39:0x0055, B:41:0x0074, B:43:0x007f, B:45:0x008d, B:48:0x009a, B:50:0x00a6, B:52:0x00a9, B:54:0x00af, B:59:0x00b9, B:61:0x00bf, B:63:0x00c5, B:64:0x013e, B:67:0x00d1, B:69:0x00d5, B:71:0x00dd, B:73:0x00ff, B:74:0x0104, B:77:0x0133, B:80:0x0130, B:81:0x0102, B:83:0x009d, B:84:0x0090, B:76:0x010d), top: B:38:0x0055, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkCaptionMotionEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkCaptionMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00ec A[Catch: Exception -> 0x0133, TryCatch #1 {Exception -> 0x0133, blocks: (B:36:0x0065, B:38:0x008c, B:40:0x0097, B:42:0x00a8, B:44:0x00ae, B:49:0x00b8, B:51:0x00be, B:53:0x00c2, B:55:0x012f, B:58:0x00ca, B:60:0x00ec, B:61:0x00f1, B:64:0x0124, B:67:0x0121, B:68:0x00ef, B:63:0x00fe), top: B:35:0x0065, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ef A[Catch: Exception -> 0x0133, TryCatch #1 {Exception -> 0x0133, blocks: (B:36:0x0065, B:38:0x008c, B:40:0x0097, B:42:0x00a8, B:44:0x00ae, B:49:0x00b8, B:51:0x00be, B:53:0x00c2, B:55:0x012f, B:58:0x00ca, B:60:0x00ec, B:61:0x00f1, B:64:0x0124, B:67:0x0121, B:68:0x00ef, B:63:0x00fe), top: B:35:0x0065, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkGameMotionEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkGameMotionEvent(android.view.MotionEvent):boolean");
    }

    private boolean checkTranscribeButtonMotionEvent(MotionEvent motionEvent) {
        TranscribeButton transcribeButton;
        return this.useTranscribeButton && (!this.isPlayingRound || getVideoTranscriptionProgress() > 0.0f || this.wasTranscriptionOpen) && (transcribeButton = this.transcribeButton) != null && transcribeButton.onTouch(motionEvent.getAction(), motionEvent.getX(), motionEvent.getY());
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00e9 A[Catch: Exception -> 0x0133, TryCatch #1 {Exception -> 0x0133, blocks: (B:23:0x0055, B:25:0x006f, B:27:0x0086, B:29:0x0091, B:31:0x00a2, B:33:0x00a8, B:38:0x00b2, B:40:0x00b8, B:42:0x00bc, B:44:0x012f, B:48:0x00c4, B:50:0x00e9, B:51:0x00ee, B:54:0x0124, B:57:0x0121, B:58:0x00ec, B:53:0x00fe), top: B:22:0x0055, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ec A[Catch: Exception -> 0x0133, TryCatch #1 {Exception -> 0x0133, blocks: (B:23:0x0055, B:25:0x006f, B:27:0x0086, B:29:0x0091, B:31:0x00a2, B:33:0x00a8, B:38:0x00b2, B:40:0x00b8, B:42:0x00bc, B:44:0x012f, B:48:0x00c4, B:50:0x00e9, B:51:0x00ee, B:54:0x0124, B:57:0x0121, B:58:0x00ec, B:53:0x00fe), top: B:22:0x0055, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkLinkPreviewMotionEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 1031
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkLinkPreviewMotionEvent(android.view.MotionEvent):boolean");
    }

    private boolean checkPollButtonMotionEvent(MotionEvent motionEvent) {
        int i;
        int i2;
        if (this.currentMessageObject.eventId != 0 || this.pollVoteInProgress || this.pollUnvoteInProgress || this.pollButtons.isEmpty()) {
            return false;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.type != 17 || !messageObject.isSent()) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            this.pressedVoteButton = -1;
            this.pollHintPressed = false;
            if (this.hintButtonVisible && (i = this.pollHintX) != -1 && x >= i && x <= i + AndroidUtilities.dp(40.0f) && y >= (i2 = this.pollHintY) && y <= i2 + AndroidUtilities.dp(40.0f)) {
                this.pollHintPressed = true;
                this.selectorDrawableMaskType[0] = 3;
                if (Build.VERSION.SDK_INT >= 21) {
                    Drawable[] drawableArr = this.selectorDrawable;
                    if (drawableArr[0] != null) {
                        drawableArr[0].setBounds(this.pollHintX - AndroidUtilities.dp(8.0f), this.pollHintY - AndroidUtilities.dp(8.0f), this.pollHintX + AndroidUtilities.dp(32.0f), this.pollHintY + AndroidUtilities.dp(32.0f));
                        this.selectorDrawable[0].setHotspot(x, y);
                        this.selectorDrawable[0].setState(this.pressedState);
                    }
                }
                invalidate();
            } else {
                for (int i3 = 0; i3 < this.pollButtons.size(); i3++) {
                    PollButton pollButton = this.pollButtons.get(i3);
                    int dp = (pollButton.y + this.namesOffset) - AndroidUtilities.dp(13.0f);
                    int i4 = pollButton.x;
                    if (x >= i4 && x <= (i4 + this.backgroundWidth) - AndroidUtilities.dp(31.0f) && y >= dp && y <= pollButton.height + dp + AndroidUtilities.dp(26.0f)) {
                        this.pressedVoteButton = i3;
                        if (!this.pollVoted && !this.pollClosed) {
                            this.selectorDrawableMaskType[0] = 1;
                            if (Build.VERSION.SDK_INT >= 21) {
                                Drawable[] drawableArr2 = this.selectorDrawable;
                                if (drawableArr2[0] != null) {
                                    drawableArr2[0].setBounds(pollButton.x - AndroidUtilities.dp(9.0f), dp, (pollButton.x + this.backgroundWidth) - AndroidUtilities.dp(22.0f), pollButton.height + dp + AndroidUtilities.dp(26.0f));
                                    this.selectorDrawable[0].setHotspot(x, y);
                                    this.selectorDrawable[0].setState(this.pressedState);
                                }
                            }
                            invalidate();
                        }
                    }
                }
                return false;
            }
            return true;
        }
        if (motionEvent.getAction() == 1) {
            if (this.pollHintPressed) {
                playSoundEffect(0);
                this.delegate.didPressHint(this, 0);
                this.pollHintPressed = false;
                if (Build.VERSION.SDK_INT < 21) {
                    return false;
                }
                Drawable[] drawableArr3 = this.selectorDrawable;
                if (drawableArr3[0] == null) {
                    return false;
                }
                drawableArr3[0].setState(StateSet.NOTHING);
                return false;
            }
            if (this.pressedVoteButton == -1) {
                return false;
            }
            playSoundEffect(0);
            if (Build.VERSION.SDK_INT >= 21) {
                Drawable[] drawableArr4 = this.selectorDrawable;
                if (drawableArr4[0] != null) {
                    drawableArr4[0].setState(StateSet.NOTHING);
                }
            }
            if (this.currentMessageObject.scheduled) {
                Toast.makeText(getContext(), LocaleController.getString("MessageScheduledVote", org.telegram.messenger.R.string.MessageScheduledVote), 1).show();
            } else {
                PollButton pollButton2 = this.pollButtons.get(this.pressedVoteButton);
                TLRPC$TL_pollAnswer tLRPC$TL_pollAnswer = pollButton2.answer;
                if (this.pollVoted || this.pollClosed) {
                    ArrayList<TLRPC$TL_pollAnswer> arrayList = new ArrayList<>();
                    arrayList.add(tLRPC$TL_pollAnswer);
                    this.delegate.didPressVoteButtons(this, arrayList, pollButton2.count, pollButton2.x + AndroidUtilities.dp(50.0f), this.namesOffset + pollButton2.y);
                } else if (this.lastPoll.multiple_choice) {
                    if (this.currentMessageObject.checkedVotes.contains(tLRPC$TL_pollAnswer)) {
                        this.currentMessageObject.checkedVotes.remove(tLRPC$TL_pollAnswer);
                        this.pollCheckBox[this.pressedVoteButton].setChecked(false, true);
                    } else {
                        this.currentMessageObject.checkedVotes.add(tLRPC$TL_pollAnswer);
                        this.pollCheckBox[this.pressedVoteButton].setChecked(true, true);
                    }
                } else {
                    this.pollVoteInProgressNum = this.pressedVoteButton;
                    this.pollVoteInProgress = true;
                    this.vibrateOnPollVote = true;
                    this.voteCurrentProgressTime = 0.0f;
                    this.firstCircleLength = true;
                    this.voteCurrentCircleLength = 360.0f;
                    this.voteRisingCircleLength = false;
                    ArrayList<TLRPC$TL_pollAnswer> arrayList2 = new ArrayList<>();
                    arrayList2.add(tLRPC$TL_pollAnswer);
                    this.delegate.didPressVoteButtons(this, arrayList2, -1, 0, 0);
                }
            }
            this.pressedVoteButton = -1;
            invalidate();
            return false;
        }
        if (motionEvent.getAction() != 2) {
            return false;
        }
        if ((this.pressedVoteButton == -1 && !this.pollHintPressed) || Build.VERSION.SDK_INT < 21) {
            return false;
        }
        Drawable[] drawableArr5 = this.selectorDrawable;
        if (drawableArr5[0] == null) {
            return false;
        }
        drawableArr5[0].setHotspot(x, y);
        return false;
    }

    private boolean checkInstantButtonMotionEvent(MotionEvent motionEvent) {
        if (!this.currentMessageObject.isSponsored() && (!this.drawInstantView || this.currentMessageObject.type == 0)) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.drawInstantView) {
                float f = x;
                float f2 = y;
                if (this.instantButtonRect.contains(f, f2)) {
                    this.selectorDrawableMaskType[0] = this.lastPoll == null ? 0 : 2;
                    this.instantPressed = true;
                    if (Build.VERSION.SDK_INT >= 21 && this.selectorDrawable[0] != null && this.instantButtonRect.contains(f, f2)) {
                        this.selectorDrawable[0].setHotspot(f, f2);
                        this.selectorDrawable[0].setState(this.pressedState);
                        setInstantButtonPressed(true);
                    }
                    invalidate();
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.instantPressed) {
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate != null) {
                    if (this.lastPoll != null) {
                        MessageObject messageObject = this.currentMessageObject;
                        if (messageObject.scheduled) {
                            Toast.makeText(getContext(), LocaleController.getString("MessageScheduledVoteResults", org.telegram.messenger.R.string.MessageScheduledVoteResults), 1).show();
                        } else if (this.pollVoted || this.pollClosed) {
                            chatMessageCellDelegate.didPressInstantButton(this, this.drawInstantViewType);
                        } else {
                            if (!messageObject.checkedVotes.isEmpty()) {
                                this.pollVoteInProgressNum = -1;
                                this.pollVoteInProgress = true;
                                this.vibrateOnPollVote = true;
                                this.voteCurrentProgressTime = 0.0f;
                                this.firstCircleLength = true;
                                this.voteCurrentCircleLength = 360.0f;
                                this.voteRisingCircleLength = false;
                            }
                            this.delegate.didPressVoteButtons(this, this.currentMessageObject.checkedVotes, -1, 0, this.namesOffset);
                        }
                    } else {
                        chatMessageCellDelegate.didPressInstantButton(this, this.drawInstantViewType);
                    }
                }
                playSoundEffect(0);
                if (Build.VERSION.SDK_INT >= 21) {
                    Drawable[] drawableArr = this.selectorDrawable;
                    if (drawableArr[0] != null) {
                        drawableArr[0].setState(StateSet.NOTHING);
                    }
                }
                this.instantPressed = false;
                setInstantButtonPressed(false);
                invalidate();
            }
        } else if (motionEvent.getAction() == 2 && this.instantButtonPressed && Build.VERSION.SDK_INT >= 21) {
            Drawable[] drawableArr2 = this.selectorDrawable;
            if (drawableArr2[0] != null) {
                drawableArr2[0].setHotspot(x, y);
            }
        }
        return false;
    }

    private void invalidateWithParent() {
        if (this.currentMessagesGroup != null && getParent() != null) {
            ((ViewGroup) getParent()).invalidate();
        }
        invalidate();
    }

    private boolean checkCommentButtonMotionEvent(MotionEvent motionEvent) {
        int i = 0;
        if (!this.drawCommentButton) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null && (groupedMessagePosition.flags & 1) == 0 && this.commentButtonRect.contains(x, y)) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            int childCount = viewGroup.getChildCount();
            while (true) {
                if (i >= childCount) {
                    break;
                }
                View childAt = viewGroup.getChildAt(i);
                if (childAt != this && (childAt instanceof ChatMessageCell)) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    if (chatMessageCell.drawCommentButton && chatMessageCell.currentMessagesGroup == this.currentMessagesGroup && (chatMessageCell.currentPosition.flags & 1) != 0) {
                        MotionEvent obtain = MotionEvent.obtain(0L, 0L, motionEvent.getActionMasked(), (motionEvent.getX() + getLeft()) - chatMessageCell.getLeft(), (motionEvent.getY() + getTop()) - chatMessageCell.getTop(), 0);
                        chatMessageCell.checkCommentButtonMotionEvent(obtain);
                        obtain.recycle();
                        break;
                    }
                }
                i++;
            }
            return true;
        }
        if (motionEvent.getAction() == 0) {
            if (this.commentButtonRect.contains(x, y)) {
                if (this.currentMessageObject.isSent()) {
                    this.selectorDrawableMaskType[1] = 2;
                    this.commentButtonPressed = true;
                    if (Build.VERSION.SDK_INT >= 21) {
                        Drawable[] drawableArr = this.selectorDrawable;
                        if (drawableArr[1] != null) {
                            drawableArr[1].setHotspot(x, y);
                            this.selectorDrawable[1].setState(this.pressedState);
                        }
                    }
                    invalidateWithParent();
                }
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.commentButtonPressed) {
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate != null) {
                    if (this.isRepliesChat) {
                        chatMessageCellDelegate.didPressSideButton(this);
                    } else {
                        chatMessageCellDelegate.didPressCommentButton(this);
                    }
                }
                playSoundEffect(0);
                if (Build.VERSION.SDK_INT >= 21) {
                    Drawable[] drawableArr2 = this.selectorDrawable;
                    if (drawableArr2[1] != null) {
                        drawableArr2[1].setState(StateSet.NOTHING);
                    }
                }
                this.commentButtonPressed = false;
                invalidateWithParent();
            }
        } else if (motionEvent.getAction() == 2 && this.commentButtonPressed && Build.VERSION.SDK_INT >= 21) {
            Drawable[] drawableArr3 = this.selectorDrawable;
            if (drawableArr3[1] != null) {
                drawableArr3[1].setHotspot(x, y);
            }
        }
        return false;
    }

    private boolean checkOtherButtonMotionEvent(MotionEvent motionEvent) {
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        int i = this.documentAttachType;
        if ((i == 5 || i == 1) && (groupedMessagePosition = this.currentPosition) != null && (groupedMessagePosition.flags & 4) == 0) {
            return false;
        }
        int i2 = this.currentMessageObject.type;
        boolean z = i2 == 16;
        if (!z) {
            z = ((i != 1 && i2 != 12 && i != 5 && i != 4 && i != 2 && i2 != 8) || this.hasGamePreview || this.hasInvoicePreview) ? false : true;
        }
        if (!z) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject.type == 16) {
                boolean isVideoCall = messageObject.isVideoCall();
                int i3 = this.otherX;
                if (x >= i3) {
                    if (x <= i3 + AndroidUtilities.dp((LocaleController.isRTL ? 0 : 200) + 30 + (!isVideoCall ? 2 : 0)) && y >= this.otherY - AndroidUtilities.dp(14.0f) && y <= this.otherY + AndroidUtilities.dp(50.0f)) {
                        this.otherPressed = true;
                        this.selectorDrawableMaskType[0] = 4;
                        if (Build.VERSION.SDK_INT >= 21 && this.selectorDrawable[0] != null) {
                            int dp = this.otherX + AndroidUtilities.dp((LocaleController.isRTL ? 0 : 200) + (!isVideoCall ? 2 : 0)) + (Theme.chat_msgInCallDrawable[isVideoCall ? 1 : 0].getIntrinsicWidth() / 2);
                            int intrinsicHeight = this.otherY + (Theme.chat_msgInCallDrawable[isVideoCall ? 1 : 0].getIntrinsicHeight() / 2);
                            this.selectorDrawable[0].setBounds(dp - AndroidUtilities.dp(20.0f), intrinsicHeight - AndroidUtilities.dp(20.0f), dp + AndroidUtilities.dp(20.0f), intrinsicHeight + AndroidUtilities.dp(20.0f));
                            this.selectorDrawable[0].setHotspot(x, y);
                            this.selectorDrawable[0].setState(this.pressedState);
                        }
                        invalidate();
                        return true;
                    }
                }
            } else if (x >= this.otherX - AndroidUtilities.dp(20.0f) && x <= this.otherX + AndroidUtilities.dp(20.0f) && y >= this.otherY - AndroidUtilities.dp(4.0f) && y <= this.otherY + AndroidUtilities.dp(30.0f)) {
                this.otherPressed = true;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.otherPressed) {
                if (this.currentMessageObject.type == 16 && Build.VERSION.SDK_INT >= 21) {
                    Drawable[] drawableArr = this.selectorDrawable;
                    if (drawableArr[0] != null) {
                        drawableArr[0].setState(StateSet.NOTHING);
                    }
                }
                this.otherPressed = false;
                playSoundEffect(0);
                this.delegate.didPressOther(this, this.otherX, this.otherY);
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 2 && this.currentMessageObject.type == 16 && this.otherPressed && Build.VERSION.SDK_INT >= 21) {
            Drawable[] drawableArr2 = this.selectorDrawable;
            if (drawableArr2[0] != null) {
                drawableArr2[0].setHotspot(x, y);
            }
        }
        return false;
    }

    private void setInstantButtonPressed(boolean z) {
        ValueAnimator valueAnimator;
        if (this.instantButtonPressed != z) {
            invalidate();
            if (z && (valueAnimator = this.instantButtonPressAnimator) != null) {
                valueAnimator.removeAllListeners();
                this.instantButtonPressAnimator.cancel();
            }
            if (!z) {
                float f = this.instantButtonPressProgress;
                if (f != 0.0f) {
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(f, 0.0f);
                    this.instantButtonPressAnimator = ofFloat;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            ChatMessageCell.this.lambda$setInstantButtonPressed$3(valueAnimator2);
                        }
                    });
                    this.instantButtonPressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.5
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            ChatMessageCell.this.instantButtonPressAnimator = null;
                        }
                    });
                    this.instantButtonPressAnimator.setInterpolator(new OvershootInterpolator(5.0f));
                    this.instantButtonPressAnimator.setDuration(350L);
                    this.instantButtonPressAnimator.start();
                }
            }
            this.instantButtonPressed = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setInstantButtonPressed$3(ValueAnimator valueAnimator) {
        this.instantButtonPressProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private boolean checkDateMotionEvent(MotionEvent motionEvent) {
        if (!this.currentMessageObject.isImportedForward()) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            float f = x;
            float f2 = this.drawTimeX;
            if (f < f2 || f > f2 + this.timeWidth) {
                return false;
            }
            float f3 = y;
            float f4 = this.drawTimeY;
            if (f3 < f4 || f3 > f4 + AndroidUtilities.dp(20.0f)) {
                return false;
            }
            this.timePressed = true;
            invalidate();
        } else {
            if (motionEvent.getAction() != 1 || !this.timePressed) {
                return false;
            }
            this.timePressed = false;
            playSoundEffect(0);
            this.delegate.didPressTime(this);
            invalidate();
        }
        return true;
    }

    private boolean checkRoundSeekbar(MotionEvent motionEvent) {
        if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || !MediaController.getInstance().isMessagePaused()) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            float f = x;
            if (f >= this.seekbarRoundX - AndroidUtilities.dp(20.0f) && f <= this.seekbarRoundX + AndroidUtilities.dp(20.0f)) {
                float f2 = y;
                if (f2 >= this.seekbarRoundY - AndroidUtilities.dp(20.0f) && f2 <= this.seekbarRoundY + AndroidUtilities.dp(20.0f)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    cancelCheckLongPress();
                    this.roundSeekbarTouched = 1;
                    invalidate();
                }
            }
            float centerX = f - this.photoImage.getCenterX();
            float centerY = y - this.photoImage.getCenterY();
            float imageWidth = (this.photoImage.getImageWidth() - AndroidUtilities.dp(64.0f)) / 2.0f;
            float f3 = (centerX * centerX) + (centerY * centerY);
            if (f3 < ((this.photoImage.getImageWidth() / 2.0f) * this.photoImage.getImageWidth()) / 2.0f && f3 > imageWidth * imageWidth) {
                getParent().requestDisallowInterceptTouchEvent(true);
                cancelCheckLongPress();
                this.roundSeekbarTouched = 1;
                invalidate();
            }
        } else if (this.roundSeekbarTouched == 1 && motionEvent.getAction() == 2) {
            float degrees = ((float) Math.toDegrees(Math.atan2(y - this.photoImage.getCenterY(), x - this.photoImage.getCenterX()))) + 90.0f;
            if (degrees < 0.0f) {
                degrees += 360.0f;
            }
            float f4 = degrees / 360.0f;
            if (Math.abs(this.currentMessageObject.audioProgress - f4) > 0.9f) {
                if (this.roundSeekbarOutAlpha == 0.0f) {
                    performHapticFeedback(3);
                }
                this.roundSeekbarOutAlpha = 1.0f;
                this.roundSeekbarOutProgress = this.currentMessageObject.audioProgress;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.lastSeekUpdateTime > 100) {
                MediaController.getInstance().seekToProgress(this.currentMessageObject, f4);
                this.lastSeekUpdateTime = currentTimeMillis;
            }
            this.currentMessageObject.audioProgress = f4;
            updatePlayingMessageProgress();
        }
        if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.roundSeekbarTouched != 0) {
            if (motionEvent.getAction() == 1) {
                float degrees2 = ((float) Math.toDegrees(Math.atan2(y - this.photoImage.getCenterY(), x - this.photoImage.getCenterX()))) + 90.0f;
                if (degrees2 < 0.0f) {
                    degrees2 += 360.0f;
                }
                float f5 = degrees2 / 360.0f;
                this.currentMessageObject.audioProgress = f5;
                MediaController.getInstance().seekToProgress(this.currentMessageObject, f5);
                updatePlayingMessageProgress();
            }
            MediaController.getInstance().playMessage(this.currentMessageObject);
            this.roundSeekbarTouched = 0;
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return this.roundSeekbarTouched != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkPhotoImageMotionEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 629
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkPhotoImageMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x0142, code lost:
    
        if (r4 <= (r0 + r6)) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0108, code lost:
    
        if (r4 <= (r0 + r6)) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x010a, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x015c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkAudioMotionEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 444
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.checkAudioMotionEvent(android.view.MotionEvent):boolean");
    }

    public boolean checkSpoilersMotionEvent(MotionEvent motionEvent, int i) {
        int i2;
        MessageObject.GroupedMessages groupedMessages;
        if (i <= 15 && getParent() != null) {
            if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
                ViewGroup viewGroup = (ViewGroup) getParent();
                for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                    View childAt = viewGroup.getChildAt(i3);
                    if (childAt instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                        MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                        if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId) {
                            int i4 = currentPosition.flags;
                            if ((i4 & 8) != 0 && (i4 & 1) != 0 && chatMessageCell != this) {
                                motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                                boolean checkSpoilersMotionEvent = chatMessageCell.checkSpoilersMotionEvent(motionEvent, i + 1);
                                motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                                return checkSpoilersMotionEvent;
                            }
                        }
                    }
                }
            }
            if (this.isSpoilerRevealing) {
                return false;
            }
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                int i5 = this.textX;
                if (x >= i5 && y >= (i2 = this.textY)) {
                    MessageObject messageObject = this.currentMessageObject;
                    if (x <= i5 + messageObject.textWidth && y <= i2 + messageObject.textHeight) {
                        ArrayList<MessageObject.TextLayoutBlock> arrayList = messageObject.textLayoutBlocks;
                        for (int i6 = 0; i6 < arrayList.size() && arrayList.get(i6).textYOffset <= y; i6++) {
                            MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i6);
                            int i7 = textLayoutBlock.isRtl() ? (int) this.currentMessageObject.textXOffset : 0;
                            for (SpoilerEffect spoilerEffect : textLayoutBlock.spoilers) {
                                if (spoilerEffect.getBounds().contains((x - this.textX) + i7, (int) ((y - this.textY) - textLayoutBlock.textYOffset))) {
                                    this.spoilerPressed = spoilerEffect;
                                    this.isCaptionSpoilerPressed = false;
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (hasCaptionLayout()) {
                    float f = x;
                    float f2 = this.captionX;
                    if (f >= f2) {
                        float f3 = y;
                        if (f3 >= this.captionY && f <= f2 + this.captionLayout.getWidth() && f3 <= this.captionY + this.captionLayout.getHeight()) {
                            for (SpoilerEffect spoilerEffect2 : this.captionSpoilers) {
                                if (spoilerEffect2.getBounds().contains((int) (f - this.captionX), (int) (f3 - this.captionY))) {
                                    this.spoilerPressed = spoilerEffect2;
                                    this.isCaptionSpoilerPressed = true;
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (actionMasked == 1 && this.spoilerPressed != null) {
                playSoundEffect(0);
                this.sPath.rewind();
                if (this.isCaptionSpoilerPressed) {
                    Iterator<SpoilerEffect> it = this.captionSpoilers.iterator();
                    while (it.hasNext()) {
                        Rect bounds = it.next().getBounds();
                        this.sPath.addRect(bounds.left, bounds.top, bounds.right, bounds.bottom, Path.Direction.CW);
                    }
                } else {
                    Iterator<MessageObject.TextLayoutBlock> it2 = this.currentMessageObject.textLayoutBlocks.iterator();
                    while (it2.hasNext()) {
                        MessageObject.TextLayoutBlock next = it2.next();
                        Iterator<SpoilerEffect> it3 = next.spoilers.iterator();
                        while (it3.hasNext()) {
                            Rect bounds2 = it3.next().getBounds();
                            Path path = this.sPath;
                            float f4 = bounds2.left;
                            float f5 = bounds2.top;
                            float f6 = next.textYOffset;
                            path.addRect(f4, f5 + f6, bounds2.right, bounds2.bottom + f6, Path.Direction.CW);
                        }
                    }
                }
                this.sPath.computeBounds(this.rect, false);
                float sqrt = (float) Math.sqrt(Math.pow(this.rect.width(), 2.0d) + Math.pow(this.rect.height(), 2.0d));
                this.isSpoilerRevealing = true;
                this.spoilerPressed.setOnRippleEndCallback(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChatMessageCell.this.lambda$checkSpoilersMotionEvent$5();
                    }
                });
                if (this.isCaptionSpoilerPressed) {
                    Iterator<SpoilerEffect> it4 = this.captionSpoilers.iterator();
                    while (it4.hasNext()) {
                        it4.next().startRipple(x - this.captionX, y - this.captionY, sqrt);
                    }
                } else {
                    ArrayList<MessageObject.TextLayoutBlock> arrayList2 = this.currentMessageObject.textLayoutBlocks;
                    if (arrayList2 != null) {
                        Iterator<MessageObject.TextLayoutBlock> it5 = arrayList2.iterator();
                        while (it5.hasNext()) {
                            MessageObject.TextLayoutBlock next2 = it5.next();
                            int i8 = next2.isRtl() ? (int) this.currentMessageObject.textXOffset : 0;
                            Iterator<SpoilerEffect> it6 = next2.spoilers.iterator();
                            while (it6.hasNext()) {
                                it6.next().startRipple((x - this.textX) + i8, (y - next2.textYOffset) - this.textY, sqrt);
                            }
                        }
                    }
                }
                if (getParent() instanceof RecyclerListView) {
                    ViewGroup viewGroup2 = (ViewGroup) getParent();
                    for (int i9 = 0; i9 < viewGroup2.getChildCount(); i9++) {
                        View childAt2 = viewGroup2.getChildAt(i9);
                        if (childAt2 instanceof ChatMessageCell) {
                            final ChatMessageCell chatMessageCell2 = (ChatMessageCell) childAt2;
                            if (chatMessageCell2.getMessageObject() != null && chatMessageCell2.getMessageObject().getReplyMsgId() == getMessageObject().getId() && !chatMessageCell2.replySpoilers.isEmpty()) {
                                chatMessageCell2.replySpoilers.get(0).setOnRippleEndCallback(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda13
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ChatMessageCell.this.lambda$checkSpoilersMotionEvent$7(chatMessageCell2);
                                    }
                                });
                                Iterator<SpoilerEffect> it7 = chatMessageCell2.replySpoilers.iterator();
                                while (it7.hasNext()) {
                                    it7.next().startRipple(r4.getBounds().centerX(), r4.getBounds().centerY(), sqrt);
                                }
                            }
                        }
                    }
                }
                this.spoilerPressed = null;
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$5() {
        post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ChatMessageCell.this.lambda$checkSpoilersMotionEvent$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$4() {
        this.isSpoilerRevealing = false;
        getMessageObject().isSpoilersRevealed = true;
        if (this.isCaptionSpoilerPressed) {
            this.captionSpoilers.clear();
        } else {
            ArrayList<MessageObject.TextLayoutBlock> arrayList = this.currentMessageObject.textLayoutBlocks;
            if (arrayList != null) {
                Iterator<MessageObject.TextLayoutBlock> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().spoilers.clear();
                }
            }
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$7(final ChatMessageCell chatMessageCell) {
        post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                ChatMessageCell.lambda$checkSpoilersMotionEvent$6(ChatMessageCell.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkSpoilersMotionEvent$6(ChatMessageCell chatMessageCell) {
        chatMessageCell.getMessageObject().replyMessageObject.isSpoilersRevealed = true;
        chatMessageCell.replySpoilers.clear();
        chatMessageCell.invalidate();
    }

    private boolean checkBotButtonMotionEvent(MotionEvent motionEvent) {
        int i;
        int dp;
        if (this.botButtons.isEmpty() || this.currentMessageObject.eventId != 0) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.currentMessageObject.isOutOwner()) {
                dp = (getMeasuredWidth() - this.widthForButtons) - AndroidUtilities.dp(10.0f);
            } else {
                dp = this.backgroundDrawableLeft + AndroidUtilities.dp(this.mediaBackground ? 1.0f : 7.0f);
            }
            for (int i2 = 0; i2 < this.botButtons.size(); i2++) {
                BotButton botButton = this.botButtons.get(i2);
                int dp2 = (botButton.y + this.layoutHeight) - AndroidUtilities.dp(2.0f);
                if (x >= botButton.x + dp && x <= botButton.x + dp + botButton.width && y >= dp2 && y <= botButton.height + dp2) {
                    this.pressedBotButton = i2;
                    invalidateOutbounds();
                    if (botButton.selectorDrawable == null) {
                        botButton.selectorDrawable = Theme.createRadSelectorDrawable(getThemedColor(Theme.key_chat_serviceBackgroundSelector), 6, 6);
                        botButton.selectorDrawable.setBounds(botButton.x + dp, dp2, botButton.x + dp + botButton.width, botButton.height + dp2);
                    }
                    if (Build.VERSION.SDK_INT >= 21) {
                        botButton.selectorDrawable.setHotspot(x, y);
                    }
                    botButton.selectorDrawable.setState(this.pressedState);
                    botButton.setPressed(true);
                    final int i3 = this.pressedBotButton;
                    postDelayed(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            ChatMessageCell.this.lambda$checkBotButtonMotionEvent$8(i3);
                        }
                    }, ViewConfiguration.getLongPressTimeout() - 1);
                    return true;
                }
            }
            return false;
        }
        if (motionEvent.getAction() == 1) {
            if (this.pressedBotButton == -1) {
                return false;
            }
            playSoundEffect(0);
            BotButton botButton2 = this.botButtons.get(this.pressedBotButton);
            if (botButton2.selectorDrawable != null) {
                botButton2.selectorDrawable.setState(StateSet.NOTHING);
            }
            botButton2.setPressed(false);
            if (!this.currentMessageObject.scheduled) {
                if (botButton2.button != null) {
                    this.delegate.didPressBotButton(this, botButton2.button);
                }
            } else {
                Toast.makeText(getContext(), LocaleController.getString("MessageScheduledBotAction", org.telegram.messenger.R.string.MessageScheduledBotAction), 1).show();
            }
            this.pressedBotButton = -1;
            invalidateOutbounds();
            return false;
        }
        if (motionEvent.getAction() != 3 || (i = this.pressedBotButton) == -1) {
            return false;
        }
        BotButton botButton3 = this.botButtons.get(i);
        if (botButton3.selectorDrawable != null) {
            botButton3.selectorDrawable.setState(StateSet.NOTHING);
        }
        botButton3.setPressed(false);
        this.pressedBotButton = -1;
        invalidateOutbounds();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkBotButtonMotionEvent$8(int i) {
        int i2 = this.pressedBotButton;
        if (i == i2) {
            BotButton botButton = this.botButtons.get(i2);
            if (botButton != null) {
                if (botButton.selectorDrawable != null) {
                    botButton.selectorDrawable.setState(StateSet.NOTHING);
                }
                botButton.setPressed(false);
                if (!this.currentMessageObject.scheduled && botButton.button != null) {
                    cancelCheckLongPress();
                    this.delegate.didLongPressBotButton(this, botButton.button);
                }
            }
            this.pressedBotButton = -1;
            invalidateOutbounds();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:468:0x066a, code lost:
    
        if (r5 > (r0 + org.telegram.messenger.AndroidUtilities.dp(32 + ((r18.drawSideButton != 3 || r18.commentLayout == null) ? 0 : 18)))) goto L421;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r19) {
        /*
            Method dump skipped, instructions count: 1666
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEvent$9() {
        if (this.replyPressed && !this.replySelectorPressed && this.replySelectorCanBePressed) {
            this.replySelectorPressed = true;
            this.replySelector.setState(new int[]{R.attr.state_pressed, R.attr.state_enabled});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEvent$10() {
        this.replySelector.setState(new int[0]);
        invalidate();
    }

    private boolean checkReactionsTouchEvent(MotionEvent motionEvent) {
        MessageObject.GroupedMessages groupedMessages;
        if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup == null) {
                return false;
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                    if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId) {
                        int i2 = currentPosition.flags;
                        if ((i2 & 8) != 0 && (i2 & 1) != 0) {
                            if (chatMessageCell == this) {
                                return this.reactionsLayoutInBubble.chekTouchEvent(motionEvent);
                            }
                            motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                            boolean chekTouchEvent = chatMessageCell.reactionsLayoutInBubble.chekTouchEvent(motionEvent);
                            motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                            return chekTouchEvent;
                        }
                    }
                }
            }
            return false;
        }
        return this.reactionsLayoutInBubble.chekTouchEvent(motionEvent);
    }

    private boolean checkPinchToZoom(MotionEvent motionEvent) {
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        PinchToZoomHelper pinchToZoomHelper = chatMessageCellDelegate == null ? null : chatMessageCellDelegate.getPinchToZoomHelper();
        if (this.currentMessageObject == null || !this.photoImage.hasNotThumb() || pinchToZoomHelper == null || this.currentMessageObject.isSticker() || this.currentMessageObject.isAnimatedEmoji()) {
            return false;
        }
        if ((this.currentMessageObject.isVideo() && !this.autoPlayingMedia) || this.isRoundVideo || this.currentMessageObject.isAnimatedSticker()) {
            return false;
        }
        if ((!this.currentMessageObject.isDocument() || this.currentMessageObject.isGif()) && !this.currentMessageObject.needDrawBluredPreview()) {
            return pinchToZoomHelper.checkPinchToZoom(motionEvent, this, this.photoImage, this.currentMessageObject);
        }
        return false;
    }

    private boolean checkTextSelection(MotionEvent motionEvent) {
        MessageObject messageObject;
        TLRPC$Message tLRPC$Message;
        int i;
        int dp;
        int i2;
        MessageObject.GroupedMessages groupedMessages;
        TextSelectionHelper.ChatListTextSelectionHelper textSelectionHelper = this.delegate.getTextSelectionHelper();
        if (textSelectionHelper == null || MessagesController.getInstance(this.currentAccount).isChatNoForwards(this.currentMessageObject.getChatId()) || ((tLRPC$Message = (messageObject = this.currentMessageObject).messageOwner) != null && tLRPC$Message.noforwards)) {
            return false;
        }
        ArrayList<MessageObject.TextLayoutBlock> arrayList = messageObject.textLayoutBlocks;
        if (!((arrayList == null || arrayList.isEmpty()) ? false : true) && !hasCaptionLayout()) {
            return false;
        }
        if ((!this.drawSelectionBackground && this.currentMessagesGroup == null) || (this.currentMessagesGroup != null && !this.delegate.hasSelectedMessages())) {
            return false;
        }
        if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup == null) {
                return false;
            }
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                    if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId) {
                        int i4 = currentPosition.flags;
                        if ((i4 & 8) != 0 && (i4 & 1) != 0) {
                            textSelectionHelper.setMaybeTextCord((int) chatMessageCell.captionX, (int) chatMessageCell.captionY);
                            textSelectionHelper.setMessageObject(chatMessageCell);
                            if (chatMessageCell == this) {
                                return textSelectionHelper.onTouchEvent(motionEvent);
                            }
                            motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                            boolean onTouchEvent = textSelectionHelper.onTouchEvent(motionEvent);
                            motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                            return onTouchEvent;
                        }
                    }
                }
            }
            return false;
        }
        if (hasCaptionLayout()) {
            textSelectionHelper.setIsDescription(false);
            textSelectionHelper.setMaybeTextCord((int) this.captionX, (int) this.captionY);
        } else if (this.descriptionLayout != null && motionEvent.getY() > this.descriptionY) {
            textSelectionHelper.setIsDescription(true);
            if (this.hasGamePreview) {
                i2 = this.unmovedTextX - AndroidUtilities.dp(10.0f);
            } else {
                if (this.hasInvoicePreview) {
                    i = this.unmovedTextX;
                    dp = AndroidUtilities.dp(1.0f);
                } else {
                    i = this.unmovedTextX;
                    dp = AndroidUtilities.dp(1.0f);
                }
                i2 = i + dp;
            }
            textSelectionHelper.setMaybeTextCord(i2 + AndroidUtilities.dp(10.0f) + this.descriptionX, this.descriptionY);
        } else {
            textSelectionHelper.setIsDescription(false);
            textSelectionHelper.setMaybeTextCord(this.textX, this.textY);
        }
        textSelectionHelper.setMessageObject(this);
        return textSelectionHelper.onTouchEvent(motionEvent);
    }

    private void updateSelectionTextPosition() {
        int i;
        int dp;
        int i2;
        if (getDelegate() == null || getDelegate().getTextSelectionHelper() == null || !getDelegate().getTextSelectionHelper().isSelected(this.currentMessageObject)) {
            return;
        }
        int textSelectionType = getDelegate().getTextSelectionHelper().getTextSelectionType(this);
        if (textSelectionType == TextSelectionHelper.ChatListTextSelectionHelper.TYPE_DESCRIPTION) {
            if (this.hasGamePreview) {
                i2 = this.unmovedTextX - AndroidUtilities.dp(10.0f);
            } else {
                if (this.hasInvoicePreview) {
                    i = this.unmovedTextX;
                    dp = AndroidUtilities.dp(1.0f);
                } else {
                    i = this.unmovedTextX;
                    dp = AndroidUtilities.dp(1.0f);
                }
                i2 = i + dp;
            }
            getDelegate().getTextSelectionHelper().updateTextPosition(i2 + AndroidUtilities.dp(10.0f) + this.descriptionX, this.descriptionY);
            return;
        }
        if (textSelectionType == TextSelectionHelper.ChatListTextSelectionHelper.TYPE_CAPTION) {
            getDelegate().getTextSelectionHelper().updateTextPosition((int) this.captionX, (int) this.captionY);
        } else {
            getDelegate().getTextSelectionHelper().updateTextPosition(this.textX, this.textY);
        }
    }

    public ArrayList<PollButton> getPollButtons() {
        return this.pollButtons;
    }

    public void updatePlayingMessageProgress() {
        int i;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        VideoPlayerRewinder videoPlayerRewinder = this.videoPlayerRewinder;
        if (videoPlayerRewinder != null && videoPlayerRewinder.rewindCount != 0 && videoPlayerRewinder.rewindByBackSeek) {
            messageObject.audioProgress = videoPlayerRewinder.getVideoProgress();
        }
        if (this.documentAttachType == 4) {
            SeekBar seekBar = this.seekBar;
            if (seekBar != null) {
                seekBar.clearTimestamps();
            }
            if (this.infoLayout == null || !(PhotoViewer.isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isGoingToShowMessageObject(this.currentMessageObject))) {
                AnimatedFileDrawable animation = this.photoImage.getAnimation();
                if (animation != null) {
                    MessageObject messageObject2 = this.currentMessageObject;
                    r2 = animation.getDurationMs() / 1000;
                    messageObject2.audioPlayerDuration = r2;
                    MessageObject messageObject3 = this.currentMessageObject;
                    TLRPC$Message tLRPC$Message = messageObject3.messageOwner;
                    if (tLRPC$Message.ttl > 0 && tLRPC$Message.destroyTime == 0 && !messageObject3.needDrawBluredPreview() && this.currentMessageObject.isVideo() && animation.hasBitmap()) {
                        this.delegate.didStartVideoStream(this.currentMessageObject);
                    }
                }
                if (r2 == 0) {
                    r2 = this.currentMessageObject.getDuration();
                }
                if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    float f = r2;
                    r2 = (int) (f - (this.currentMessageObject.audioProgress * f));
                } else if (animation != null) {
                    if (r2 != 0) {
                        r2 -= animation.getCurrentProgressMs() / 1000;
                    }
                    if (this.delegate != null && animation.getCurrentProgressMs() >= 3000) {
                        this.delegate.videoTimerReached();
                    }
                }
                if (this.lastTime != r2) {
                    String formatShortDuration = AndroidUtilities.formatShortDuration(r2);
                    this.infoWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(formatShortDuration));
                    this.infoLayout = new StaticLayout(formatShortDuration, Theme.chat_infoPaint, this.infoWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    this.lastTime = r2;
                    return;
                }
                return;
            }
            return;
        }
        if (this.isRoundVideo) {
            if (this.useSeekBarWaveform) {
                if (!this.seekBarWaveform.isDragging()) {
                    this.seekBarWaveform.setProgress(this.currentMessageObject.audioProgress, true);
                }
            } else {
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                    this.seekBar.setBufferedProgress(this.currentMessageObject.bufferedProgress);
                }
                this.seekBar.clearTimestamps();
            }
            TLRPC$Document document = this.currentMessageObject.getDocument();
            int i2 = 0;
            while (true) {
                if (i2 >= document.attributes.size()) {
                    i = 0;
                    break;
                }
                TLRPC$DocumentAttribute tLRPC$DocumentAttribute = document.attributes.get(i2);
                if (tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                    i = tLRPC$DocumentAttribute.duration;
                    break;
                }
                i2++;
            }
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                i = Math.max(0, i - this.currentMessageObject.audioProgressSec);
            }
            if (this.lastTime != i) {
                this.lastTime = i;
                String formatLongDuration = AndroidUtilities.formatLongDuration(i);
                this.timeWidthAudio = (int) Math.ceil(Theme.chat_timePaint.measureText(formatLongDuration));
                this.durationLayout = new StaticLayout(formatLongDuration, Theme.chat_timePaint, this.timeWidthAudio, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            float f2 = this.currentMessageObject.audioProgress;
            if (f2 != 0.0f) {
                this.lastDrawingAudioProgress = f2;
                if (f2 > 0.9f) {
                    this.lastDrawingAudioProgress = 1.0f;
                }
            }
            invalidate();
            return;
        }
        if (this.documentAttach != null) {
            if (this.useSeekBarWaveform) {
                if (!this.seekBarWaveform.isDragging()) {
                    this.seekBarWaveform.setProgress(this.currentMessageObject.audioProgress, true);
                }
            } else {
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                    this.seekBar.setBufferedProgress(this.currentMessageObject.bufferedProgress);
                }
                this.seekBar.updateTimestamps(this.currentMessageObject, null);
            }
            if (this.documentAttachType == 3) {
                if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.documentAttach.attributes.size()) {
                            break;
                        }
                        TLRPC$DocumentAttribute tLRPC$DocumentAttribute2 = this.documentAttach.attributes.get(i3);
                        if (tLRPC$DocumentAttribute2 instanceof TLRPC$TL_documentAttributeAudio) {
                            r2 = tLRPC$DocumentAttribute2.duration;
                            break;
                        }
                        i3++;
                    }
                } else {
                    r2 = this.currentMessageObject.audioProgressSec;
                }
                if (this.lastTime != r2) {
                    this.lastTime = r2;
                    String formatLongDuration2 = AndroidUtilities.formatLongDuration(r2);
                    this.timeWidthAudio = (int) Math.ceil(Theme.chat_audioTimePaint.measureText(formatLongDuration2));
                    this.durationLayout = new StaticLayout(formatLongDuration2, Theme.chat_audioTimePaint, this.timeWidthAudio, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
            } else {
                int duration = this.currentMessageObject.getDuration();
                r2 = MediaController.getInstance().isPlayingMessage(this.currentMessageObject) ? this.currentMessageObject.audioProgressSec : 0;
                if (this.lastTime != r2) {
                    this.lastTime = r2;
                    this.durationLayout = new StaticLayout(AndroidUtilities.formatShortDuration(r2, duration), Theme.chat_audioTimePaint, (int) Math.ceil(Theme.chat_audioTimePaint.measureText(r4)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
            }
            invalidate();
        }
    }

    public void setFullyDraw(boolean z) {
        this.fullyDraw = z;
    }

    public void setParentViewSize(int i, int i2) {
        Theme.MessageDrawable messageDrawable;
        this.parentWidth = i;
        this.parentHeight = i2;
        this.backgroundHeight = i2;
        if (!(this.currentMessageObject != null && hasGradientService() && this.currentMessageObject.shouldDrawWithoutBackground()) && ((messageDrawable = this.currentBackgroundDrawable) == null || messageDrawable.getGradientShader() == null)) {
            return;
        }
        invalidate();
    }

    public void setVisiblePart(int i, int i2, int i3, float f, float f2, int i4, int i5, int i6, int i7) {
        this.parentWidth = i4;
        this.parentHeight = i5;
        this.backgroundHeight = i5;
        this.blurredViewTopOffset = i6;
        this.blurredViewBottomOffset = i7;
        if (!this.botButtons.isEmpty() && this.viewTop != f2) {
            invalidate();
        }
        this.viewTop = f2;
        if (i3 != this.parentHeight || f != this.parentViewTopOffset) {
            this.parentViewTopOffset = f;
            this.parentHeight = i3;
        }
        if (this.currentMessageObject != null && hasGradientService() && this.currentMessageObject.shouldDrawWithoutBackground()) {
            invalidate();
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.textLayoutBlocks == null) {
            return;
        }
        int i8 = i - this.textY;
        int i9 = 0;
        for (int i10 = 0; i10 < this.currentMessageObject.textLayoutBlocks.size() && this.currentMessageObject.textLayoutBlocks.get(i10).textYOffset <= i8; i10++) {
            i9 = i10;
        }
        int i11 = -1;
        int i12 = -1;
        int i13 = 0;
        while (i9 < this.currentMessageObject.textLayoutBlocks.size()) {
            float f3 = this.currentMessageObject.textLayoutBlocks.get(i9).textYOffset;
            float f4 = i8;
            if (!intersect(f3, r13.height + f3, f4, i8 + i2)) {
                if (f3 > f4) {
                    break;
                }
            } else {
                if (i11 == -1) {
                    i11 = i9;
                }
                i13++;
                i12 = i9;
            }
            i9++;
        }
        if (this.lastVisibleBlockNum != i12 || this.firstVisibleBlockNum != i11 || this.totalVisibleBlocksCount != i13) {
            this.lastVisibleBlockNum = i12;
            this.firstVisibleBlockNum = i11;
            this.totalVisibleBlocksCount = i13;
            invalidate();
            return;
        }
        if (this.animatedEmojiStack != null) {
            for (int i14 = 0; i14 < this.animatedEmojiStack.holders.size(); i14++) {
                AnimatedEmojiSpan.AnimatedEmojiHolder animatedEmojiHolder = this.animatedEmojiStack.holders.get(i14);
                if (animatedEmojiHolder != null && animatedEmojiHolder.skipDraw && !animatedEmojiHolder.outOfBounds((this.parentBoundsTop - getY()) - animatedEmojiHolder.drawingYOffset, (this.parentBoundsBottom - getY()) - animatedEmojiHolder.drawingYOffset)) {
                    invalidate();
                    return;
                }
            }
        }
    }

    public static StaticLayout generateStaticLayout(CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, i2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int i5 = i;
        int i6 = 0;
        for (int i7 = 0; i7 < i3; i7++) {
            staticLayout.getLineDirections(i7);
            if (staticLayout.getLineLeft(i7) != 0.0f || staticLayout.isRtlCharAt(staticLayout.getLineStart(i7)) || staticLayout.isRtlCharAt(staticLayout.getLineEnd(i7))) {
                i5 = i2;
            }
            int lineEnd = staticLayout.getLineEnd(i7);
            if (lineEnd == charSequence.length()) {
                break;
            }
            int i8 = (lineEnd - 1) + i6;
            if (spannableStringBuilder.charAt(i8) == ' ') {
                spannableStringBuilder.replace(i8, i8 + 1, (CharSequence) "\n");
            } else if (spannableStringBuilder.charAt(i8) != '\n') {
                spannableStringBuilder.insert(i8, (CharSequence) "\n");
                i6++;
            }
            if (i7 == staticLayout.getLineCount() - 1 || i7 == i4 - 1) {
                break;
            }
        }
        int i9 = i5;
        return StaticLayoutEx.createStaticLayout(spannableStringBuilder, textPaint, i9, Layout.Alignment.ALIGN_NORMAL, 1.0f, AndroidUtilities.dp(1.0f), false, TextUtils.TruncateAt.END, i9, i4, true);
    }

    private void didClickedImage() {
        ChatMessageCellDelegate chatMessageCellDelegate;
        TLRPC$WebPage tLRPC$WebPage;
        boolean z;
        TLRPC$MessageMedia tLRPC$MessageMedia;
        TLRPC$ReplyMarkup tLRPC$ReplyMarkup;
        if (this.currentMessageObject.hasMediaSpoilers() && !this.currentMessageObject.isMediaSpoilersRevealed) {
            startRevealMedia(this.lastTouchX, this.lastTouchY);
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        int i = messageObject.type;
        if (i == 20) {
            TLRPC$Message tLRPC$Message = messageObject.messageOwner;
            if (tLRPC$Message == null || (tLRPC$MessageMedia = tLRPC$Message.media) == null || tLRPC$MessageMedia.extended_media == null || (tLRPC$ReplyMarkup = tLRPC$Message.reply_markup) == null) {
                return;
            }
            Iterator<TLRPC$TL_keyboardButtonRow> it = tLRPC$ReplyMarkup.rows.iterator();
            while (it.hasNext()) {
                Iterator<TLRPC$KeyboardButton> it2 = it.next().buttons.iterator();
                if (it2.hasNext()) {
                    this.delegate.didPressExtendedMediaPreview(this, it2.next());
                    return;
                }
            }
            return;
        }
        if (i == 1 || messageObject.isAnyKindOfSticker()) {
            int i2 = this.buttonState;
            if (i2 == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
                return;
            } else {
                if (i2 == 0) {
                    didPressButton(true, false);
                    return;
                }
                return;
            }
        }
        MessageObject messageObject2 = this.currentMessageObject;
        int i3 = messageObject2.type;
        if (i3 == 12) {
            long j = MessageObject.getMedia(messageObject2.messageOwner).user_id;
            this.delegate.didPressUserAvatar(this, j != 0 ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)) : null, this.lastTouchX, this.lastTouchY);
            return;
        }
        if (i3 == 5) {
            if (this.buttonState != -1) {
                didPressButton(true, false);
                return;
            } else if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isMessagePaused()) {
                this.delegate.needPlayMessage(this.currentMessageObject, false);
                return;
            } else {
                MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject);
                return;
            }
        }
        if (i3 == 8) {
            int i4 = this.buttonState;
            if (i4 == -1 || (i4 == 1 && this.canStreamVideo && this.autoPlayingMedia)) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
                return;
            } else {
                if (i4 == 2 || i4 == 0) {
                    didPressButton(true, false);
                    return;
                }
                return;
            }
        }
        int i5 = this.documentAttachType;
        if (i5 == 4) {
            int i6 = this.buttonState;
            if (i6 == -1 || ((z = this.drawVideoImageButton) && (this.autoPlayingMedia || (SharedConfig.streamMedia && this.canStreamVideo)))) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
                return;
            }
            if (z) {
                didPressButton(true, true);
                return;
            } else {
                if (i6 == 0 || i6 == 3) {
                    didPressButton(true, false);
                    return;
                }
                return;
            }
        }
        if (i3 == 4) {
            this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
            return;
        }
        if (i5 == 1) {
            if (this.buttonState == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
                return;
            }
            return;
        }
        if (i5 == 2) {
            if (this.buttonState != -1 || (tLRPC$WebPage = MessageObject.getMedia(messageObject2.messageOwner).webpage) == null) {
                return;
            }
            String str = tLRPC$WebPage.embed_url;
            if (str != null && str.length() != 0) {
                this.delegate.needOpenWebView(this.currentMessageObject, tLRPC$WebPage.embed_url, tLRPC$WebPage.site_name, tLRPC$WebPage.description, tLRPC$WebPage.url, tLRPC$WebPage.embed_width, tLRPC$WebPage.embed_height);
                return;
            } else {
                Browser.openUrl(getContext(), tLRPC$WebPage.url);
                return;
            }
        }
        if (this.hasInvoicePreview) {
            if (this.buttonState == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY);
            }
        } else {
            if (Build.VERSION.SDK_INT < 26 || (chatMessageCellDelegate = this.delegate) == null) {
                return;
            }
            if (i3 == 16) {
                chatMessageCellDelegate.didLongPress(this, 0.0f, 0.0f);
            } else {
                chatMessageCellDelegate.didPressOther(this, this.otherX, this.otherY);
            }
        }
    }

    private void updateSecretTimeText(MessageObject messageObject) {
        String secretTimeString;
        if (messageObject == null || !messageObject.needDrawBluredPreview() || (secretTimeString = messageObject.getSecretTimeString()) == null) {
            return;
        }
        int ceil = (int) Math.ceil(Theme.chat_infoPaint.measureText(secretTimeString));
        this.infoWidth = ceil;
        this.infoLayout = new StaticLayout(TextUtils.ellipsize(secretTimeString, Theme.chat_infoPaint, ceil, TextUtils.TruncateAt.END), Theme.chat_infoPaint, this.infoWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isPhotoDataChanged(org.telegram.messenger.MessageObject r25) {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.isPhotoDataChanged(org.telegram.messenger.MessageObject):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRepliesCount() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.messages.isEmpty()) {
            return this.currentMessagesGroup.messages.get(0).getRepliesCount();
        }
        return this.currentMessageObject.getRepliesCount();
    }

    private ArrayList<TLRPC$Peer> getRecentRepliers() {
        TLRPC$MessageReplies tLRPC$MessageReplies;
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.messages.isEmpty() && (tLRPC$MessageReplies = this.currentMessagesGroup.messages.get(0).messageOwner.replies) != null) {
            return tLRPC$MessageReplies.recent_repliers;
        }
        TLRPC$MessageReplies tLRPC$MessageReplies2 = this.currentMessageObject.messageOwner.replies;
        if (tLRPC$MessageReplies2 != null) {
            return tLRPC$MessageReplies2.recent_repliers;
        }
        return null;
    }

    public void updateAnimatedEmojis() {
        if (this.imageReceiversAttachState) {
            int cacheTypeForEnterView = this.currentMessageObject.wasJustSent ? AnimatedEmojiDrawable.getCacheTypeForEnterView() : 0;
            StaticLayout staticLayout = this.captionLayout;
            if (staticLayout != null) {
                this.animatedEmojiStack = AnimatedEmojiSpan.update(cacheTypeForEnterView, (View) this, false, this.animatedEmojiStack, staticLayout);
            } else {
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                this.animatedEmojiStack = AnimatedEmojiSpan.update(cacheTypeForEnterView, this, chatMessageCellDelegate == null || !chatMessageCellDelegate.canDrawOutboundsContent(), this.animatedEmojiStack, this.currentMessageObject.textLayoutBlocks);
            }
        }
    }

    private void updateCaptionSpoilers() {
        this.captionSpoilersPool.addAll(this.captionSpoilers);
        this.captionSpoilers.clear();
        if (this.captionLayout == null || getMessageObject().isSpoilersRevealed) {
            return;
        }
        SpoilerEffect.addSpoilers(this, this.captionLayout, -1, this.captionOffsetX + this.captionWidth, this.captionSpoilersPool, this.captionSpoilers);
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isUserDataChanged() {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.isUserDataChanged():boolean");
    }

    public ImageReceiver getPhotoImage() {
        return this.photoImage;
    }

    public int getNoSoundIconCenterX() {
        return this.noSoundCenterX;
    }

    public int getForwardNameCenterX() {
        float f;
        TLRPC$User tLRPC$User = this.currentUser;
        if (tLRPC$User != null && tLRPC$User.id == 0) {
            f = this.avatarImage.getCenterX();
        } else {
            f = this.forwardNameX + this.forwardNameCenterX;
        }
        return (int) f;
    }

    public int getChecksX() {
        return this.layoutWidth - AndroidUtilities.dp(SharedConfig.bubbleRadius >= 10 ? 27.3f : 25.3f);
    }

    public int getChecksY() {
        float f;
        int intrinsicHeight;
        if (this.currentMessageObject.shouldDrawWithoutBackground()) {
            f = this.drawTimeY;
            intrinsicHeight = getThemedDrawable("drawableMsgStickerCheck").getIntrinsicHeight();
        } else {
            f = this.drawTimeY;
            intrinsicHeight = Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight();
        }
        return (int) (f - intrinsicHeight);
    }

    public TLRPC$User getCurrentUser() {
        return this.currentUser;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.startSpoilers);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.stopSpoilers);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.userInfoDidLoad);
        cancelShakeAnimation();
        CheckBoxBase checkBoxBase = this.checkBox;
        if (checkBoxBase != null) {
            checkBoxBase.onDetachedFromWindow();
        }
        CheckBoxBase checkBoxBase2 = this.mediaCheckBox;
        if (checkBoxBase2 != null) {
            checkBoxBase2.onDetachedFromWindow();
        }
        if (this.pollCheckBox != null) {
            int i = 0;
            while (true) {
                CheckBoxBase[] checkBoxBaseArr = this.pollCheckBox;
                if (i >= checkBoxBaseArr.length) {
                    break;
                }
                checkBoxBaseArr[i].onDetachedFromWindow();
                i++;
            }
        }
        this.attachedToWindow = false;
        this.avatarImage.onDetachedFromWindow();
        checkImageReceiversAttachState();
        if (this.addedForTest && this.currentUrl != null && this.currentWebFile != null) {
            ImageLoader.getInstance().removeTestWebFile(this.currentUrl);
            this.addedForTest = false;
        }
        DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
        if (getDelegate() != null && getDelegate().getTextSelectionHelper() != null) {
            getDelegate().getTextSelectionHelper().onChatMessageCellDetached(this);
        }
        this.transitionParams.onDetach();
        if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
            Theme.getCurrentAudiVisualizerDrawable().setParentView(null);
        }
        ValueAnimator valueAnimator = this.statusDrawableAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.statusDrawableAnimator.cancel();
        }
        this.reactionsLayoutInBubble.onDetachFromWindow();
        this.statusDrawableAnimationInProgress = false;
        FlagSecureReason flagSecureReason = this.flagSecure;
        if (flagSecureReason != null) {
            flagSecureReason.detach();
        }
        MessageTopicButton messageTopicButton = this.topicButton;
        if (messageTopicButton != null) {
            messageTopicButton.onDetached(this);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.currentNameStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.startSpoilers);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.stopSpoilers);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.userInfoDidLoad);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null) {
            messageObject.animateComments = false;
        }
        MessageObject messageObject2 = this.messageObjectToSet;
        if (messageObject2 != null) {
            messageObject2.animateComments = false;
            setMessageContent(messageObject2, this.groupedMessagesToSet, this.bottomNearToSet, this.topNearToSet);
            this.messageObjectToSet = null;
            this.groupedMessagesToSet = null;
        }
        CheckBoxBase checkBoxBase = this.checkBox;
        if (checkBoxBase != null) {
            checkBoxBase.onAttachedToWindow();
        }
        CheckBoxBase checkBoxBase2 = this.mediaCheckBox;
        if (checkBoxBase2 != null) {
            checkBoxBase2.onAttachedToWindow();
        }
        if (this.pollCheckBox != null) {
            int i = 0;
            while (true) {
                CheckBoxBase[] checkBoxBaseArr = this.pollCheckBox;
                if (i >= checkBoxBaseArr.length) {
                    break;
                }
                checkBoxBaseArr[i].onAttachedToWindow();
                i++;
            }
        }
        this.attachedToWindow = true;
        this.animationOffsetX = 0.0f;
        this.slidingOffsetX = 0.0f;
        this.checkBoxTranslation = 0;
        updateTranslation();
        this.avatarImage.setParentView((View) getParent());
        this.avatarImage.onAttachedToWindow();
        checkImageReceiversAttachState();
        MessageObject messageObject3 = this.currentMessageObject;
        if (messageObject3 != null) {
            setAvatar(messageObject3);
        }
        int i2 = this.documentAttachType;
        if (i2 == 4 && this.autoPlayingMedia) {
            boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
            this.animatingNoSoundPlaying = isPlayingMessage;
            this.animatingNoSoundProgress = isPlayingMessage ? 0.0f : 1.0f;
            this.animatingNoSound = 0;
        } else {
            this.animatingNoSoundPlaying = false;
            this.animatingNoSoundProgress = 0.0f;
            this.animatingDrawVideoImageButtonProgress = ((i2 == 4 || i2 == 2) && this.drawVideoSize) ? 1.0f : 0.0f;
        }
        if (getDelegate() != null && getDelegate().getTextSelectionHelper() != null) {
            getDelegate().getTextSelectionHelper().onChatMessageCellAttached(this);
        }
        if (this.documentAttachType == 5) {
            this.toSeekBarProgress = MediaController.getInstance().isPlayingMessage(this.currentMessageObject) ? 1.0f : 0.0f;
        }
        this.reactionsLayoutInBubble.onAttachToWindow();
        FlagSecureReason flagSecureReason = this.flagSecure;
        if (flagSecureReason != null) {
            flagSecureReason.attach();
        }
        updateFlagSecure();
        MessageObject messageObject4 = this.currentMessageObject;
        if (messageObject4 != null && messageObject4.type == 20 && this.unlockLayout != null) {
            invalidate();
        }
        MessageTopicButton messageTopicButton = this.topicButton;
        if (messageTopicButton != null) {
            messageTopicButton.onAttached(this);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.currentNameStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
    }

    private void checkImageReceiversAttachState() {
        int i = 0;
        boolean z = this.attachedToWindow && (this.visibleOnScreen || !this.shouldCheckVisibleOnScreen);
        if (z == this.imageReceiversAttachState) {
            return;
        }
        this.imageReceiversAttachState = z;
        if (z) {
            this.radialProgress.onAttachedToWindow();
            this.videoRadialProgress.onAttachedToWindow();
            if (this.pollAvatarImages != null) {
                int i2 = 0;
                while (true) {
                    ImageReceiver[] imageReceiverArr = this.pollAvatarImages;
                    if (i2 >= imageReceiverArr.length) {
                        break;
                    }
                    imageReceiverArr[i2].onAttachedToWindow();
                    i2++;
                }
            }
            if (this.commentAvatarImages != null) {
                int i3 = 0;
                while (true) {
                    ImageReceiver[] imageReceiverArr2 = this.commentAvatarImages;
                    if (i3 >= imageReceiverArr2.length) {
                        break;
                    }
                    imageReceiverArr2[i3].onAttachedToWindow();
                    i3++;
                }
            }
            this.replyImageReceiver.onAttachedToWindow();
            this.locationImageReceiver.onAttachedToWindow();
            this.blurredPhotoImage.onAttachedToWindow();
            if (this.photoImage.onAttachedToWindow()) {
                if (this.drawPhotoImage) {
                    updateButtonState(false, false, false);
                }
            } else {
                updateButtonState(false, false, false);
            }
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject != null && (this.isRoundVideo || messageObject.isVideo())) {
                checkVideoPlayback(true, null);
            }
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 != null && !messageObject2.mediaExists) {
                int canDownloadMedia = DownloadController.getInstance(this.currentAccount).canDownloadMedia(this.currentMessageObject.messageOwner);
                TLRPC$Document document = this.currentMessageObject.getDocument();
                if (!(MessageObject.isStickerDocument(document) || MessageObject.isAnimatedStickerDocument(document, true) || MessageObject.isGifDocument(document) || MessageObject.isRoundVideoDocument(document))) {
                    TLRPC$PhotoSize closestPhotoSizeWithSize = document == null ? FileLoader.getClosestPhotoSizeWithSize(this.currentMessageObject.photoThumbs, AndroidUtilities.getPhotoSize()) : null;
                    if (canDownloadMedia == 2 || (canDownloadMedia == 1 && this.currentMessageObject.isVideo())) {
                        if (canDownloadMedia != 2 && document != null && !this.currentMessageObject.shouldEncryptPhotoOrVideo() && this.currentMessageObject.canStreamVideo()) {
                            FileLoader.getInstance(this.currentAccount).loadFile(document, this.currentMessageObject, 1, 0);
                        }
                    } else if (canDownloadMedia != 0) {
                        if (document != null) {
                            FileLoader.getInstance(this.currentAccount).loadFile(document, this.currentMessageObject, 1, (MessageObject.isVideoDocument(document) && this.currentMessageObject.shouldEncryptPhotoOrVideo()) ? 2 : 0);
                        } else if (closestPhotoSizeWithSize != null) {
                            FileLoader fileLoader = FileLoader.getInstance(this.currentAccount);
                            ImageLocation forObject = ImageLocation.getForObject(closestPhotoSizeWithSize, this.currentMessageObject.photoThumbsObject);
                            MessageObject messageObject3 = this.currentMessageObject;
                            fileLoader.loadFile(forObject, messageObject3, null, 1, messageObject3.shouldEncryptPhotoOrVideo() ? 2 : 0);
                        }
                    }
                    updateButtonState(false, false, false);
                }
            }
            this.animatedEmojiReplyStack = AnimatedEmojiSpan.update(0, (View) this, false, this.animatedEmojiReplyStack, this.replyTextLayout);
            this.animatedEmojiDescriptionStack = AnimatedEmojiSpan.update(0, (View) this, false, this.animatedEmojiDescriptionStack, this.descriptionLayout);
            updateAnimatedEmojis();
            return;
        }
        this.radialProgress.onDetachedFromWindow();
        this.videoRadialProgress.onDetachedFromWindow();
        if (this.pollAvatarImages != null) {
            int i4 = 0;
            while (true) {
                ImageReceiver[] imageReceiverArr3 = this.pollAvatarImages;
                if (i4 >= imageReceiverArr3.length) {
                    break;
                }
                imageReceiverArr3[i4].onDetachedFromWindow();
                i4++;
            }
        }
        if (this.commentAvatarImages != null) {
            while (true) {
                ImageReceiver[] imageReceiverArr4 = this.commentAvatarImages;
                if (i >= imageReceiverArr4.length) {
                    break;
                }
                imageReceiverArr4[i].onDetachedFromWindow();
                i++;
            }
        }
        this.replyImageReceiver.onDetachedFromWindow();
        this.locationImageReceiver.onDetachedFromWindow();
        this.photoImage.onDetachedFromWindow();
        this.blurredPhotoImage.onDetachedFromWindow();
        cancelLoading(this.currentMessageObject);
        AnimatedEmojiSpan.release(this, this.animatedEmojiDescriptionStack);
        AnimatedEmojiSpan.release(this, this.animatedEmojiReplyStack);
        AnimatedEmojiSpan.release(this, this.animatedEmojiStack);
    }

    private void cancelLoading(MessageObject messageObject) {
        if (messageObject == null || messageObject.mediaExists || messageObject.putInDownloadsStore || DownloadController.getInstance(this.currentAccount).isDownloading(messageObject.messageOwner.id) || PhotoViewer.getInstance().isVisible()) {
            return;
        }
        TLRPC$Document document = messageObject.getDocument();
        boolean z = true;
        if (!MessageObject.isStickerDocument(document) && !MessageObject.isAnimatedStickerDocument(document, true) && !MessageObject.isGifDocument(document) && !MessageObject.isRoundVideoDocument(document)) {
            z = false;
        }
        if (z) {
            return;
        }
        if (document != null) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(document);
            return;
        }
        TLRPC$PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
        if (closestPhotoSizeWithSize != null) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(closestPhotoSizeWithSize);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(67:(1:1604)(4:1635|(1:(2:1647|1648)(3:1637|1638|(1:1640)(1:1641)))|1643|(1:1645)(1:1646))|1605|(51:1630|1631|634|635|(1:637)(5:1551|(1:1553)(1:1595)|1554|(8:1558|(2:1560|(1:1562)(1:1593))(1:1594)|1563|(1:1565)(1:1592)|1566|(1:1572)|1573|(2:1575|(3:1579|(2:1581|(1:1583))|1584)(1:(4:1586|(1:1588)|1589|(1:1591)))))(1:1556)|1557)|638|(3:1547|(1:1549)|1550)|642|(2:644|(1:646))|(2:1530|(3:1535|(1:1537)(2:1539|(1:1541))|1538)(1:1532))(3:648|649|(2:653|(1:655)(1:1521)))|656|(1:662)(1:1520)|663|(1:1519)(1:665)|666|(1:671)|672|(36:1489|(2:1491|(1:1493))|1495|(2:1505|(1:1507)(35:1508|(33:1515|1512|676|(2:678|(2:680|(2:682|(3:686|(3:691|(1:693)(1:1334)|694)(4:(1:1344)(1:1336)|1337|(1:1339)(1:1341)|1340)|695)(7:1345|(2:1347|(1:1349)(1:1437))(1:1438)|1350|(1:1354)(1:1436)|1355|(2:1360|(4:1365|1366|(1:1368)|(5:1375|(1:1377)|1378|(1:1380)(1:1382)|1381)(2:1383|(1:1388)(1:1389)))(1:(2:(5:1399|(1:1401)|1402|(1:1404)(1:1408)|1405)(1:1409)|1406)(3:1410|(1:1412)(1:1414)|1413)))(6:1418|(1:1431)|1424|(2:1426|1423)|1422|1423)|1407))(2:1439|(2:1441|(1:1443)(3:1444|(1:1446)(1:1469)|(2:1454|(1:1458)(3:1459|(1:1461)(1:1463)|1462))(3:1464|(1:1466)(1:1468)|1467)))(1:1470)))(3:1471|(1:1473)(1:1475)|1474))(2:1476|(1:1482)(2:1483|(2:(1:1487)|1488)))|696|697|(0)(0)|700|(4:702|704|706|708)|709|(23:711|713|717|(0)|720|(0)|723|(0)(0)|726|(0)|729|(13:731|733|(0)(0)|736|(0)|739|(0)(0)|742|(0)|770|(3:772|774|775)|1282|775)|1304|(3:1306|1308|(3:1310|1312|1314))|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1323|(2:1326|1332)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1511|1512|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775))|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1608|1609|1610|(59:1625|1616|1617|635|(0)(0)|638|(1:640)(4:1542|1547|(0)|1550)|642|(0)|(46:1530|(47:1533|1535|(0)(0)|1538|656|(43:658|660|662|663|(39:1517|1519|666|(2:669|671)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1532|656|(0)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|648|649|(46:651|653|(0)(0)|656|(0)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1522|653|(0)(0)|656|(0)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1613|(58:1620|1621|635|(0)(0)|638|(0)(0)|642|(0)|(0)|648|649|(0)|1522|653|(0)(0)|656|(0)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775)|1616|1617|635|(0)(0)|638|(0)(0)|642|(0)|(0)|648|649|(0)|1522|653|(0)(0)|656|(0)|1520|663|(0)|665|666|(0)|672|(0)|674|675|676|(0)(0)|696|697|(0)(0)|700|(0)|709|(0)|1323|(0)|1325|720|(0)|723|(0)(0)|726|(0)|729|(0)|1304|(0)|1315|739|(0)(0)|742|(0)|770|(0)|1282|775) */
    /* JADX WARN: Code restructure failed: missing block: B:1050:0x6178, code lost:
    
        if (r11.button.url.startsWith("tg://resolve") != false) goto L5202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1186:0x5948, code lost:
    
        if ((r1.flags & 8) == 0) goto L4717;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1494:0x12ac, code lost:
    
        if (r2.revealingMediaSpoilers != false) goto L1285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1626:0x0fd6, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1642:0x0f4b, code lost:
    
        r66.captionWidth = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1689:0x0dcd, code lost:
    
        if ((r66.currentPosition.flags & r9) == 0) goto L1023;
     */
    /* JADX WARN: Code restructure failed: missing block: B:2240:0x22ca, code lost:
    
        if (r1 >= (r66.timeWidth + org.telegram.messenger.AndroidUtilities.dp(20 + (!r67.isOutOwner() ? 0 : 20)))) goto L1941;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0097, code lost:
    
        if (r66.isPlayingRound == ((!org.telegram.messenger.MediaController.getInstance().isPlayingMessage(r66.currentMessageObject) || (r6 = r66.delegate) == null || r6.keyboardIsOpened()) ? false : true)) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3508:0x5319, code lost:
    
        if (r0.revealingMediaSpoilers != false) goto L4415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3710:0x4b84, code lost:
    
        if (r0.h != 0) goto L4005;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3722:0x4bb3, code lost:
    
        if (r0.h != 0) goto L4005;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3753:0x4c6d, code lost:
    
        if (r0.h != 0) goto L4055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:3765:0x4c9c, code lost:
    
        if (r0.h != 0) goto L4055;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4048:0x45ad, code lost:
    
        if (r66.isSmallImage == false) goto L3676;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4131:0x4442, code lost:
    
        if (r66.isSmallImage != false) goto L3590;
     */
    /* JADX WARN: Code restructure failed: missing block: B:624:0x0d5d, code lost:
    
        if (r1.isSmall != false) goto L990;
     */
    /* JADX WARN: Multi-variable search skipped. Vars limit reached: 7801 (expected less than 5000) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1017:0x60e1  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:1025:0x611a  */
    /* JADX WARN: Removed duplicated region for block: B:1030:0x611b A[Catch: Exception -> 0x6180, TryCatch #45 {Exception -> 0x6180, blocks: (B:1023:0x6100, B:1027:0x617d, B:1030:0x611b, B:1033:0x6135, B:1037:0x613e, B:1040:0x6147, B:1043:0x6150, B:1046:0x615d, B:1049:0x616c, B:1051:0x6128), top: B:1022:0x6100 }] */
    /* JADX WARN: Removed duplicated region for block: B:1045:0x615c  */
    /* JADX WARN: Removed duplicated region for block: B:1046:0x615d A[Catch: Exception -> 0x6180, TryCatch #45 {Exception -> 0x6180, blocks: (B:1023:0x6100, B:1027:0x617d, B:1030:0x611b, B:1033:0x6135, B:1037:0x613e, B:1040:0x6147, B:1043:0x6150, B:1046:0x615d, B:1049:0x616c, B:1051:0x6128), top: B:1022:0x6100 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:1074:0x5c0a  */
    /* JADX WARN: Removed duplicated region for block: B:1076:0x5ba4  */
    /* JADX WARN: Removed duplicated region for block: B:1077:0x5b83  */
    /* JADX WARN: Removed duplicated region for block: B:1078:0x59c6  */
    /* JADX WARN: Removed duplicated region for block: B:1087:0x5a28  */
    /* JADX WARN: Removed duplicated region for block: B:1094:0x5a52  */
    /* JADX WARN: Removed duplicated region for block: B:1098:0x5aa2 A[Catch: Exception -> 0x5ac4, TryCatch #34 {Exception -> 0x5ac4, blocks: (B:1092:0x5a4c, B:1095:0x5a5c, B:1096:0x5a93, B:1123:0x5a9e, B:1098:0x5aa2, B:1103:0x5ab2, B:1105:0x5ab6, B:1107:0x5abe, B:1125:0x5a53), top: B:1091:0x5a4c }] */
    /* JADX WARN: Removed duplicated region for block: B:1111:0x5a9d  */
    /* JADX WARN: Removed duplicated region for block: B:1114:0x5ace A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1119:0x5aef  */
    /* JADX WARN: Removed duplicated region for block: B:1123:0x5a9e A[Catch: Exception -> 0x5ac4, TryCatch #34 {Exception -> 0x5ac4, blocks: (B:1092:0x5a4c, B:1095:0x5a5c, B:1096:0x5a93, B:1123:0x5a9e, B:1098:0x5aa2, B:1103:0x5ab2, B:1105:0x5ab6, B:1107:0x5abe, B:1125:0x5a53), top: B:1091:0x5a4c }] */
    /* JADX WARN: Removed duplicated region for block: B:1125:0x5a53 A[Catch: Exception -> 0x5ac4, TryCatch #34 {Exception -> 0x5ac4, blocks: (B:1092:0x5a4c, B:1095:0x5a5c, B:1096:0x5a93, B:1123:0x5a9e, B:1098:0x5aa2, B:1103:0x5ab2, B:1105:0x5ab6, B:1107:0x5abe, B:1125:0x5a53), top: B:1091:0x5a4c }] */
    /* JADX WARN: Removed duplicated region for block: B:1131:0x5a2a  */
    /* JADX WARN: Removed duplicated region for block: B:1159:0x58e5  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x5915 A[Catch: Exception -> 0x599f, TryCatch #18 {Exception -> 0x599f, blocks: (B:1151:0x5899, B:1154:0x58cc, B:1157:0x58de, B:1161:0x5915, B:1164:0x5922, B:1167:0x593c, B:1184:0x5943, B:1188:0x5937, B:1191:0x591a, B:1192:0x58e7, B:1193:0x58d8, B:1195:0x58f3, B:1196:0x58a0, B:1199:0x58a5, B:1200:0x58bc, B:1211:0x58c4), top: B:1150:0x5899 }] */
    /* JADX WARN: Removed duplicated region for block: B:1192:0x58e7 A[Catch: Exception -> 0x599f, TryCatch #18 {Exception -> 0x599f, blocks: (B:1151:0x5899, B:1154:0x58cc, B:1157:0x58de, B:1161:0x5915, B:1164:0x5922, B:1167:0x593c, B:1184:0x5943, B:1188:0x5937, B:1191:0x591a, B:1192:0x58e7, B:1193:0x58d8, B:1195:0x58f3, B:1196:0x58a0, B:1199:0x58a5, B:1200:0x58bc, B:1211:0x58c4), top: B:1150:0x5899 }] */
    /* JADX WARN: Removed duplicated region for block: B:1202:0x598b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01d3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1248:0x5828  */
    /* JADX WARN: Removed duplicated region for block: B:1253:0x5841 A[Catch: Exception -> 0x5881, TryCatch #31 {Exception -> 0x5881, blocks: (B:1251:0x582e, B:1253:0x5841, B:1254:0x587a, B:1256:0x5858), top: B:1250:0x582e }] */
    /* JADX WARN: Removed duplicated region for block: B:1256:0x5858 A[Catch: Exception -> 0x5881, TryCatch #31 {Exception -> 0x5881, blocks: (B:1251:0x582e, B:1253:0x5841, B:1254:0x587a, B:1256:0x5858), top: B:1250:0x582e }] */
    /* JADX WARN: Removed duplicated region for block: B:1259:0x582a  */
    /* JADX WARN: Removed duplicated region for block: B:1283:0x2aae  */
    /* JADX WARN: Removed duplicated region for block: B:1303:0x2a8c  */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x2a54  */
    /* JADX WARN: Removed duplicated region for block: B:1316:0x2a01  */
    /* JADX WARN: Removed duplicated region for block: B:1326:0x29ac  */
    /* JADX WARN: Removed duplicated region for block: B:1333:0x294a  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:1476:0x178a  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0216 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1489:0x129a  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0246 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1516:0x1277 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1521:0x1236  */
    /* JADX WARN: Removed duplicated region for block: B:1528:0x115b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1537:0x11a0  */
    /* JADX WARN: Removed duplicated region for block: B:1539:0x11a2  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:1542:0x110a  */
    /* JADX WARN: Removed duplicated region for block: B:1549:0x1123  */
    /* JADX WARN: Removed duplicated region for block: B:1551:0x0fec  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:1596:0x0ec2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x026e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1654:0x0d85  */
    /* JADX WARN: Removed duplicated region for block: B:1723:0x198e  */
    /* JADX WARN: Removed duplicated region for block: B:1726:0x1991  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x5e37  */
    /* JADX WARN: Removed duplicated region for block: B:1823:0x0d02  */
    /* JADX WARN: Removed duplicated region for block: B:1832:0x0cc0  */
    /* JADX WARN: Removed duplicated region for block: B:1833:0x0cb6  */
    /* JADX WARN: Removed duplicated region for block: B:1834:0x0c5f  */
    /* JADX WARN: Removed duplicated region for block: B:1851:0x0bc0  */
    /* JADX WARN: Removed duplicated region for block: B:1892:0x0b27  */
    /* JADX WARN: Removed duplicated region for block: B:1902:0x0a46  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x5ea9  */
    /* JADX WARN: Removed duplicated region for block: B:2140:0x20ac  */
    /* JADX WARN: Removed duplicated region for block: B:2154:0x24ea  */
    /* JADX WARN: Removed duplicated region for block: B:2157:0x2514  */
    /* JADX WARN: Removed duplicated region for block: B:2168:0x2558  */
    /* JADX WARN: Removed duplicated region for block: B:2170:0x255b  */
    /* JADX WARN: Removed duplicated region for block: B:2173:0x2575  */
    /* JADX WARN: Removed duplicated region for block: B:2189:0x2640  */
    /* JADX WARN: Removed duplicated region for block: B:2202:0x25e6  */
    /* JADX WARN: Removed duplicated region for block: B:2203:0x24f6  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x5ee1  */
    /* JADX WARN: Removed duplicated region for block: B:2253:0x22e2  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x5ef6  */
    /* JADX WARN: Removed duplicated region for block: B:2303:0x2743  */
    /* JADX WARN: Removed duplicated region for block: B:2306:0x2752  */
    /* JADX WARN: Removed duplicated region for block: B:2318:0x275e  */
    /* JADX WARN: Removed duplicated region for block: B:2329:0x276c  */
    /* JADX WARN: Removed duplicated region for block: B:2340:0x27a0  */
    /* JADX WARN: Removed duplicated region for block: B:2365:0x282c  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x5f2d  */
    /* JADX WARN: Removed duplicated region for block: B:2377:0x28c0  */
    /* JADX WARN: Removed duplicated region for block: B:2382:0x28d5  */
    /* JADX WARN: Removed duplicated region for block: B:2399:0x2922  */
    /* JADX WARN: Removed duplicated region for block: B:2422:0x2807  */
    /* JADX WARN: Removed duplicated region for block: B:2423:0x2745  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x02b3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x0305  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0326  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0393  */
    /* JADX WARN: Removed duplicated region for block: B:2908:0x36fe  */
    /* JADX WARN: Removed duplicated region for block: B:2914:0x3719  */
    /* JADX WARN: Removed duplicated region for block: B:2941:0x383f  */
    /* JADX WARN: Removed duplicated region for block: B:2944:0x384e  */
    /* JADX WARN: Removed duplicated region for block: B:2951:0x388c  */
    /* JADX WARN: Removed duplicated region for block: B:2955:0x38c8  */
    /* JADX WARN: Removed duplicated region for block: B:2958:0x3882  */
    /* JADX WARN: Removed duplicated region for block: B:2969:0x370c  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:3033:0x3a7e  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:3135:0x3fed  */
    /* JADX WARN: Removed duplicated region for block: B:3141:0x3ffd  */
    /* JADX WARN: Removed duplicated region for block: B:3147:0x4045  */
    /* JADX WARN: Removed duplicated region for block: B:3150:0x407f  */
    /* JADX WARN: Removed duplicated region for block: B:3155:0x40a1  */
    /* JADX WARN: Removed duplicated region for block: B:3163:0x40cc  */
    /* JADX WARN: Removed duplicated region for block: B:3166:0x40f0  */
    /* JADX WARN: Removed duplicated region for block: B:3169:0x4103  */
    /* JADX WARN: Removed duplicated region for block: B:3174:0x4114  */
    /* JADX WARN: Removed duplicated region for block: B:3180:0x412f  */
    /* JADX WARN: Removed duplicated region for block: B:3185:0x414c  */
    /* JADX WARN: Removed duplicated region for block: B:3189:0x4181  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:3192:0x4196  */
    /* JADX WARN: Removed duplicated region for block: B:3200:0x4322  */
    /* JADX WARN: Removed duplicated region for block: B:3209:0x435c  */
    /* JADX WARN: Removed duplicated region for block: B:3212:0x436d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:3215:0x4376  */
    /* JADX WARN: Removed duplicated region for block: B:3218:0x44d8  */
    /* JADX WARN: Removed duplicated region for block: B:3220:0x44e1  */
    /* JADX WARN: Removed duplicated region for block: B:3225:0x44ee  */
    /* JADX WARN: Removed duplicated region for block: B:3227:0x4626  */
    /* JADX WARN: Removed duplicated region for block: B:3231:0x4635 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:3235:0x463e  */
    /* JADX WARN: Removed duplicated region for block: B:3244:0x466b A[Catch: Exception -> 0x46ef, TRY_ENTER, TryCatch #6 {Exception -> 0x46ef, blocks: (B:3233:0x463a, B:3244:0x466b, B:3247:0x468e, B:3248:0x46a3, B:3250:0x46cb, B:3976:0x46da, B:3979:0x4672, B:3985:0x463f), top: B:3232:0x463a }] */
    /* JADX WARN: Removed duplicated region for block: B:3250:0x46cb A[Catch: Exception -> 0x46ef, TryCatch #6 {Exception -> 0x46ef, blocks: (B:3233:0x463a, B:3244:0x466b, B:3247:0x468e, B:3248:0x46a3, B:3250:0x46cb, B:3976:0x46da, B:3979:0x4672, B:3985:0x463f), top: B:3232:0x463a }] */
    /* JADX WARN: Removed duplicated region for block: B:3255:0x46f6  */
    /* JADX WARN: Removed duplicated region for block: B:3258:0x4890  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x04a4  */
    /* JADX WARN: Removed duplicated region for block: B:3264:0x48a1  */
    /* JADX WARN: Removed duplicated region for block: B:3266:0x48a5  */
    /* JADX WARN: Removed duplicated region for block: B:3274:0x4e48  */
    /* JADX WARN: Removed duplicated region for block: B:3291:0x5614  */
    /* JADX WARN: Removed duplicated region for block: B:3297:0x570b  */
    /* JADX WARN: Removed duplicated region for block: B:3300:0x5617  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x0525 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:3370:0x4ff0  */
    /* JADX WARN: Removed duplicated region for block: B:3373:0x5009  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:3406:0x551a  */
    /* JADX WARN: Removed duplicated region for block: B:3419:0x5578 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:3428:0x55b2  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:3447:0x551c  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x05ab A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:3630:0x48f5  */
    /* JADX WARN: Removed duplicated region for block: B:3633:0x4904  */
    /* JADX WARN: Removed duplicated region for block: B:3636:0x490f  */
    /* JADX WARN: Removed duplicated region for block: B:3639:0x4923  */
    /* JADX WARN: Removed duplicated region for block: B:3641:0x4912  */
    /* JADX WARN: Removed duplicated region for block: B:3642:0x4906  */
    /* JADX WARN: Removed duplicated region for block: B:3643:0x48f7  */
    /* JADX WARN: Removed duplicated region for block: B:3648:0x492d  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x064d  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0676 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:374:0x068c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:377:0x06ae  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0706  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:3852:0x46fc  */
    /* JADX WARN: Removed duplicated region for block: B:3866:0x4743  */
    /* JADX WARN: Removed duplicated region for block: B:3869:0x474c  */
    /* JADX WARN: Removed duplicated region for block: B:3872:0x4753  */
    /* JADX WARN: Removed duplicated region for block: B:3877:0x485a  */
    /* JADX WARN: Removed duplicated region for block: B:3899:0x47d1 A[Catch: Exception -> 0x4855, TryCatch #22 {Exception -> 0x4855, blocks: (B:3897:0x47c7, B:3899:0x47d1, B:3945:0x47e0, B:3948:0x47e5), top: B:3896:0x47c7 }] */
    /* JADX WARN: Removed duplicated region for block: B:3924:0x482e  */
    /* JADX WARN: Removed duplicated region for block: B:3955:0x4756  */
    /* JADX WARN: Removed duplicated region for block: B:3956:0x474e  */
    /* JADX WARN: Removed duplicated region for block: B:3957:0x4745  */
    /* JADX WARN: Removed duplicated region for block: B:3962:0x477a  */
    /* JADX WARN: Removed duplicated region for block: B:3964:0x477d  */
    /* JADX WARN: Removed duplicated region for block: B:3976:0x46da A[Catch: Exception -> 0x46ef, TRY_LEAVE, TryCatch #6 {Exception -> 0x46ef, blocks: (B:3233:0x463a, B:3244:0x466b, B:3247:0x468e, B:3248:0x46a3, B:3250:0x46cb, B:3976:0x46da, B:3979:0x4672, B:3985:0x463f), top: B:3232:0x463a }] */
    /* JADX WARN: Removed duplicated region for block: B:3985:0x463f A[Catch: Exception -> 0x46ef, TRY_LEAVE, TryCatch #6 {Exception -> 0x46ef, blocks: (B:3233:0x463a, B:3244:0x466b, B:3247:0x468e, B:3248:0x46a3, B:3250:0x46cb, B:3976:0x46da, B:3979:0x4672, B:3985:0x463f), top: B:3232:0x463a }] */
    /* JADX WARN: Removed duplicated region for block: B:3989:0x44f2  */
    /* JADX WARN: Removed duplicated region for block: B:4074:0x44da  */
    /* JADX WARN: Removed duplicated region for block: B:4075:0x4384 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:410:0x081e  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0821  */
    /* JADX WARN: Removed duplicated region for block: B:4166:0x4362  */
    /* JADX WARN: Removed duplicated region for block: B:4171:0x434f  */
    /* JADX WARN: Removed duplicated region for block: B:4178:0x41ff  */
    /* JADX WARN: Removed duplicated region for block: B:4186:0x4231 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:4190:0x4245  */
    /* JADX WARN: Removed duplicated region for block: B:4197:0x4287  */
    /* JADX WARN: Removed duplicated region for block: B:4215:0x42d4  */
    /* JADX WARN: Removed duplicated region for block: B:4231:0x42fd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:4239:0x425c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0880  */
    /* JADX WARN: Removed duplicated region for block: B:4242:0x426e  */
    /* JADX WARN: Removed duplicated region for block: B:4243:0x4248  */
    /* JADX WARN: Removed duplicated region for block: B:4246:0x4233  */
    /* JADX WARN: Removed duplicated region for block: B:4256:0x4164  */
    /* JADX WARN: Removed duplicated region for block: B:4260:0x4047  */
    /* JADX WARN: Removed duplicated region for block: B:427:0x08d0  */
    /* JADX WARN: Removed duplicated region for block: B:4308:0x3dc1 A[Catch: Exception -> 0x3fe3, TryCatch #8 {Exception -> 0x3fe3, blocks: (B:4308:0x3dc1, B:4311:0x3dc9, B:4314:0x3dd2, B:4317:0x3e08, B:4320:0x3e31, B:4323:0x3e58, B:4324:0x3e3a, B:4327:0x3e47, B:4328:0x3e11, B:4331:0x3e1e, B:4332:0x3deb, B:4335:0x3df7, B:4336:0x3e80, B:4339:0x3ebd, B:4342:0x3ee6, B:4345:0x3f0d, B:4347:0x3f14, B:4348:0x3f20, B:4349:0x3f1b, B:4350:0x3eef, B:4353:0x3efc, B:4354:0x3ec6, B:4357:0x3ed3, B:4358:0x3e9a, B:4361:0x3ea6, B:4370:0x3dbd), top: B:4369:0x3dbd }] */
    /* JADX WARN: Removed duplicated region for block: B:4336:0x3e80 A[Catch: Exception -> 0x3fe3, TryCatch #8 {Exception -> 0x3fe3, blocks: (B:4308:0x3dc1, B:4311:0x3dc9, B:4314:0x3dd2, B:4317:0x3e08, B:4320:0x3e31, B:4323:0x3e58, B:4324:0x3e3a, B:4327:0x3e47, B:4328:0x3e11, B:4331:0x3e1e, B:4332:0x3deb, B:4335:0x3df7, B:4336:0x3e80, B:4339:0x3ebd, B:4342:0x3ee6, B:4345:0x3f0d, B:4347:0x3f14, B:4348:0x3f20, B:4349:0x3f1b, B:4350:0x3eef, B:4353:0x3efc, B:4354:0x3ec6, B:4357:0x3ed3, B:4358:0x3e9a, B:4361:0x3ea6, B:4370:0x3dbd), top: B:4369:0x3dbd }] */
    /* JADX WARN: Removed duplicated region for block: B:4423:0x0874  */
    /* JADX WARN: Removed duplicated region for block: B:4427:0x088a  */
    /* JADX WARN: Removed duplicated region for block: B:4429:0x0891  */
    /* JADX WARN: Removed duplicated region for block: B:4443:0x0832  */
    /* JADX WARN: Removed duplicated region for block: B:4450:0x06f9  */
    /* JADX WARN: Removed duplicated region for block: B:4453:0x06fb  */
    /* JADX WARN: Removed duplicated region for block: B:4454:0x06ba A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:4456:0x068f  */
    /* JADX WARN: Removed duplicated region for block: B:4468:0x060a  */
    /* JADX WARN: Removed duplicated region for block: B:4471:0x0611  */
    /* JADX WARN: Removed duplicated region for block: B:4472:0x0619  */
    /* JADX WARN: Removed duplicated region for block: B:4477:0x060c  */
    /* JADX WARN: Removed duplicated region for block: B:4478:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:4486:0x053a  */
    /* JADX WARN: Removed duplicated region for block: B:4492:0x04d0  */
    /* JADX WARN: Removed duplicated region for block: B:4497:0x619c  */
    /* JADX WARN: Removed duplicated region for block: B:4504:0x04ce A[EDGE_INSN: B:4504:0x04ce->B:328:0x04ce BREAK  A[LOOP:42: B:4495:0x04d8->B:4501:0x61b8], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:4505:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:4520:0x043a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:4532:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:4536:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:4541:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:4545:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:4548:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:4551:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x0a3b  */
    /* JADX WARN: Removed duplicated region for block: B:520:0x0a81  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0ab4  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0abd  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x0b3d  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0b50  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0b6a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:566:0x0b9a  */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0ba5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:572:0x0c10 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:575:0x0c20  */
    /* JADX WARN: Removed duplicated region for block: B:580:0x0c3b  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0c57  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x0c8c  */
    /* JADX WARN: Removed duplicated region for block: B:593:0x0cb3  */
    /* JADX WARN: Removed duplicated region for block: B:596:0x0cbe  */
    /* JADX WARN: Removed duplicated region for block: B:599:0x0ccd  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x0cd4  */
    /* JADX WARN: Removed duplicated region for block: B:614:0x0d26  */
    /* JADX WARN: Removed duplicated region for block: B:629:0x0d77  */
    /* JADX WARN: Removed duplicated region for block: B:637:0x0fe7  */
    /* JADX WARN: Removed duplicated region for block: B:640:0x1108 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:644:0x113a  */
    /* JADX WARN: Removed duplicated region for block: B:651:0x1204  */
    /* JADX WARN: Removed duplicated region for block: B:655:0x1220  */
    /* JADX WARN: Removed duplicated region for block: B:658:0x1266  */
    /* JADX WARN: Removed duplicated region for block: B:668:0x1287 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:678:0x12f6  */
    /* JADX WARN: Removed duplicated region for block: B:699:0x2948  */
    /* JADX WARN: Removed duplicated region for block: B:702:0x2962  */
    /* JADX WARN: Removed duplicated region for block: B:711:0x2996  */
    /* JADX WARN: Removed duplicated region for block: B:719:0x29d1  */
    /* JADX WARN: Removed duplicated region for block: B:722:0x29ef  */
    /* JADX WARN: Removed duplicated region for block: B:725:0x29fe  */
    /* JADX WARN: Removed duplicated region for block: B:728:0x2a2f  */
    /* JADX WARN: Removed duplicated region for block: B:731:0x2a47  */
    /* JADX WARN: Removed duplicated region for block: B:735:0x2a8a  */
    /* JADX WARN: Removed duplicated region for block: B:738:0x2a9f  */
    /* JADX WARN: Removed duplicated region for block: B:741:0x2aac  */
    /* JADX WARN: Removed duplicated region for block: B:744:0x2b0c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:772:0x2b77  */
    /* JADX WARN: Removed duplicated region for block: B:779:0x5732  */
    /* JADX WARN: Removed duplicated region for block: B:787:0x59aa  */
    /* JADX WARN: Removed duplicated region for block: B:795:0x5b0a  */
    /* JADX WARN: Removed duplicated region for block: B:809:0x5b4c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:816:0x5c34  */
    /* JADX WARN: Removed duplicated region for block: B:823:0x5c52  */
    /* JADX WARN: Removed duplicated region for block: B:828:0x5c8e  */
    /* JADX WARN: Removed duplicated region for block: B:839:0x5cbf  */
    /* JADX WARN: Removed duplicated region for block: B:849:0x5cfe  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:854:0x5d1e  */
    /* JADX WARN: Removed duplicated region for block: B:857:0x5d5b  */
    /* JADX WARN: Removed duplicated region for block: B:905:0x5def  */
    /* JADX WARN: Removed duplicated region for block: B:911:0x5dff  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:934:0x5d24  */
    /* JADX WARN: Removed duplicated region for block: B:942:0x5c58  */
    /* JADX WARN: Removed duplicated region for block: B:951:0x5c76  */
    /* JADX WARN: Removed duplicated region for block: B:952:0x5b5e  */
    /* JADX WARN: Removed duplicated region for block: B:960:0x5b81  */
    /* JADX WARN: Removed duplicated region for block: B:963:0x5ba1  */
    /* JADX WARN: Removed duplicated region for block: B:966:0x5bb1  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:983:0x5c26  */
    /* JADX WARN: Removed duplicated region for block: B:985:0x5c28  */
    /* JADX WARN: Type inference failed for: r0v1488, types: [org.telegram.ui.Components.SeekBar] */
    /* JADX WARN: Type inference failed for: r0v345, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r0v488, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r0v639, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r0v896, types: [org.telegram.ui.Components.RadialProgress2] */
    /* JADX WARN: Type inference failed for: r0v904, types: [org.telegram.ui.Components.RadialProgress2] */
    /* JADX WARN: Type inference failed for: r0v905, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r0v910, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r10v10 */
    /* JADX WARN: Type inference failed for: r10v100, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v111, types: [float] */
    /* JADX WARN: Type inference failed for: r10v119 */
    /* JADX WARN: Type inference failed for: r10v12, types: [double] */
    /* JADX WARN: Type inference failed for: r10v121 */
    /* JADX WARN: Type inference failed for: r10v122 */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v134, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v137, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v139, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v140, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v144, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v145, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v147, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v15, types: [double] */
    /* JADX WARN: Type inference failed for: r10v153, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v155, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v158, types: [org.telegram.tgnet.TLRPC$Document] */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v163, types: [org.telegram.messenger.ImageLocation] */
    /* JADX WARN: Type inference failed for: r10v186 */
    /* JADX WARN: Type inference failed for: r10v192 */
    /* JADX WARN: Type inference failed for: r10v196 */
    /* JADX WARN: Type inference failed for: r10v199 */
    /* JADX WARN: Type inference failed for: r10v200 */
    /* JADX WARN: Type inference failed for: r10v211 */
    /* JADX WARN: Type inference failed for: r10v212 */
    /* JADX WARN: Type inference failed for: r10v213 */
    /* JADX WARN: Type inference failed for: r10v214 */
    /* JADX WARN: Type inference failed for: r10v215 */
    /* JADX WARN: Type inference failed for: r10v218 */
    /* JADX WARN: Type inference failed for: r10v219 */
    /* JADX WARN: Type inference failed for: r10v220 */
    /* JADX WARN: Type inference failed for: r10v412 */
    /* JADX WARN: Type inference failed for: r10v434 */
    /* JADX WARN: Type inference failed for: r10v435 */
    /* JADX WARN: Type inference failed for: r10v436 */
    /* JADX WARN: Type inference failed for: r10v437 */
    /* JADX WARN: Type inference failed for: r10v458 */
    /* JADX WARN: Type inference failed for: r10v479 */
    /* JADX WARN: Type inference failed for: r10v493 */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v89, types: [boolean] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v192 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v268 */
    /* JADX WARN: Type inference failed for: r13v269 */
    /* JADX WARN: Type inference failed for: r13v272 */
    /* JADX WARN: Type inference failed for: r13v273 */
    /* JADX WARN: Type inference failed for: r2v1016 */
    /* JADX WARN: Type inference failed for: r2v1017 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v434, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r2v48, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v226, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v249 */
    /* JADX WARN: Type inference failed for: r5v536 */
    /* JADX WARN: Type inference failed for: r63v0 */
    /* JADX WARN: Type inference failed for: r63v1 */
    /* JADX WARN: Type inference failed for: r63v2 */
    /* JADX WARN: Type inference failed for: r63v3 */
    /* JADX WARN: Type inference failed for: r63v4 */
    /* JADX WARN: Type inference failed for: r66v0, types: [android.view.View, android.view.ViewGroup, org.telegram.messenger.DownloadController$FileDownloadProgressListener, org.telegram.ui.Cells.ChatMessageCell] */
    /* JADX WARN: Type inference failed for: r6v280, types: [android.graphics.Canvas, java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v340, types: [android.graphics.Bitmap, android.graphics.drawable.Drawable, java.lang.Object, org.telegram.tgnet.TLRPC$Document, org.telegram.tgnet.TLRPC$PhotoSize] */
    /* JADX WARN: Type inference failed for: r6v602, types: [android.text.StaticLayout, java.lang.Long, java.lang.String, org.telegram.messenger.WebFile, org.telegram.ui.Components.LinkPath, org.telegram.ui.Components.LoadingDrawable] */
    /* JADX WARN: Type inference failed for: r6v603 */
    /* JADX WARN: Type inference failed for: r6v640 */
    /* JADX WARN: Type inference failed for: r6v641 */
    /* JADX WARN: Type inference failed for: r6v642 */
    /* JADX WARN: Type inference failed for: r6v643 */
    /* JADX WARN: Type inference failed for: r6v644 */
    /* JADX WARN: Type inference failed for: r9v35, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v38, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v40, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v41, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v42, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v43, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v45, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v56, types: [org.telegram.messenger.ImageReceiver] */
    /* JADX WARN: Type inference failed for: r9v66, types: [org.telegram.messenger.ImageReceiver] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setMessageContent(org.telegram.messenger.MessageObject r67, org.telegram.messenger.MessageObject.GroupedMessages r68, boolean r69, boolean r70) {
        /*
            Method dump skipped, instructions count: 25031
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.setMessageContent(org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject$GroupedMessages, boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageContent$11(TLRPC$User tLRPC$User, int i, TLRPC$Chat tLRPC$Chat, long j) {
        if (tLRPC$User != null) {
            this.commentAvatarDrawables[i].setInfo(tLRPC$User);
            this.commentAvatarImages[i].setForUserOrChat(tLRPC$User, this.commentAvatarDrawables[i]);
        } else if (tLRPC$Chat != null) {
            this.commentAvatarDrawables[i].setInfo(tLRPC$Chat);
            this.commentAvatarImages[i].setForUserOrChat(tLRPC$Chat, this.commentAvatarDrawables[i]);
        } else {
            this.commentAvatarDrawables[i].setInfo(j, "", "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$setMessageContent$12(PollButton pollButton, PollButton pollButton2) {
        if (pollButton.decimal > pollButton2.decimal) {
            return -1;
        }
        if (pollButton.decimal < pollButton2.decimal) {
            return 1;
        }
        if (pollButton.decimal != pollButton2.decimal) {
            return 0;
        }
        if (pollButton.percent > pollButton2.percent) {
            return 1;
        }
        return pollButton.percent < pollButton2.percent ? -1 : 0;
    }

    private boolean loopStickers() {
        return LiteMode.isEnabled(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void highlightCaptionLink(URLSpan uRLSpan) {
        if (uRLSpan != null) {
            try {
                StaticLayout staticLayout = this.captionLayout;
                if (staticLayout == null || !(staticLayout.getText() instanceof Spanned) || uRLSpan == this.highlightPathSpan) {
                    return;
                }
                this.highlightPathSpan = uRLSpan;
                Spanned spanned = (Spanned) this.captionLayout.getText();
                int spanStart = spanned.getSpanStart(this.highlightPathSpan);
                int spanEnd = spanned.getSpanEnd(this.highlightPathSpan);
                LinkPath linkPath = this.highlightPath;
                if (linkPath != null) {
                    linkPath.rewind();
                } else {
                    this.highlightPath = new LinkPath(true);
                }
                this.highlightPath.setCurrentLayout(this.captionLayout, spanStart, 0.0f);
                this.captionLayout.getSelectionPath(spanStart, spanEnd, this.highlightPath);
                this.highlightPathStart = System.currentTimeMillis();
                invalidate();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    private void calculateUnlockXY() {
        if (this.currentMessageObject.type != 20 || this.unlockLayout == null) {
            return;
        }
        this.unlockX = this.backgroundDrawableLeft + ((this.photoImage.getImageWidth() - this.unlockLayout.getWidth()) / 2.0f);
        this.unlockY = this.backgroundDrawableTop + this.photoImage.getImageY() + ((this.photoImage.getImageHeight() - this.unlockLayout.getHeight()) / 2.0f);
    }

    private void updateFlagSecure() {
        if (this.flagSecure == null) {
            Activity findActivity = AndroidUtilities.findActivity(getContext());
            Window window = findActivity == null ? null : findActivity.getWindow();
            if (window != null) {
                FlagSecureReason flagSecureReason = new FlagSecureReason(window, new FlagSecureReason.FlagSecureCondition() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda15
                    @Override // org.telegram.messenger.FlagSecureReason.FlagSecureCondition
                    public final boolean run() {
                        boolean lambda$updateFlagSecure$13;
                        lambda$updateFlagSecure$13 = ChatMessageCell.this.lambda$updateFlagSecure$13();
                        return lambda$updateFlagSecure$13;
                    }
                });
                this.flagSecure = flagSecureReason;
                if (this.attachedToWindow) {
                    flagSecureReason.attach();
                }
            }
        }
        FlagSecureReason flagSecureReason2 = this.flagSecure;
        if (flagSecureReason2 != null) {
            flagSecureReason2.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateFlagSecure$13() {
        TLRPC$Message tLRPC$Message;
        MessageObject messageObject = this.currentMessageObject;
        return (messageObject == null || (tLRPC$Message = messageObject.messageOwner) == null || (!tLRPC$Message.noforwards && !messageObject.hasRevealedExtendedMedia())) ? false : true;
    }

    public void checkVideoPlayback(boolean z, Bitmap bitmap) {
        if (this.currentMessageObject.isVideo()) {
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                this.photoImage.setAllowStartAnimation(false);
                this.photoImage.stopAnimation();
                return;
            } else {
                this.photoImage.setAllowStartAnimation(true);
                this.photoImage.startAnimation();
                return;
            }
        }
        if (z) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            z = playingMessageObject == null || !playingMessageObject.isRoundVideo();
        }
        this.photoImage.setAllowStartAnimation(z);
        if (bitmap != null) {
            this.photoImage.startCrossfadeFromStaticThumb(bitmap);
        }
        if (z) {
            this.photoImage.startAnimation();
        } else {
            this.photoImage.stopAnimation();
        }
    }

    private static boolean spanSupportsLongPress(CharacterStyle characterStyle) {
        return (characterStyle instanceof URLSpanMono) || (characterStyle instanceof URLSpan);
    }

    @Override // org.telegram.ui.Cells.BaseCell
    protected boolean onLongPress() {
        int i;
        int i2;
        boolean z = false;
        if (this.isRoundVideo && this.isPlayingRound && MediaController.getInstance().isPlayingMessage(this.currentMessageObject) && ((this.lastTouchX - this.photoImage.getCenterX()) * (this.lastTouchX - this.photoImage.getCenterX())) + ((this.lastTouchY - this.photoImage.getCenterY()) * (this.lastTouchY - this.photoImage.getCenterY())) < (this.photoImage.getImageWidth() / 2.0f) * (this.photoImage.getImageWidth() / 2.0f) && (this.lastTouchX > this.photoImage.getCenterX() + (this.photoImage.getImageWidth() / 4.0f) || this.lastTouchX < this.photoImage.getCenterX() - (this.photoImage.getImageWidth() / 4.0f))) {
            boolean z2 = this.lastTouchX > this.photoImage.getCenterX();
            if (this.videoPlayerRewinder == null) {
                this.videoForwardDrawable = new VideoForwardDrawable(true);
                this.videoPlayerRewinder = new VideoPlayerRewinder() { // from class: org.telegram.ui.Cells.ChatMessageCell.6
                    @Override // org.telegram.messenger.video.VideoPlayerRewinder
                    protected void onRewindCanceled() {
                        ChatMessageCell.this.onTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                        ChatMessageCell.this.videoForwardDrawable.setShowing(false);
                    }

                    @Override // org.telegram.messenger.video.VideoPlayerRewinder
                    protected void updateRewindProgressUi(long j, float f, boolean z3) {
                        ChatMessageCell.this.videoForwardDrawable.setTime(Math.abs(j));
                        if (z3) {
                            ChatMessageCell.this.currentMessageObject.audioProgress = f;
                            ChatMessageCell.this.updatePlayingMessageProgress();
                        }
                    }

                    @Override // org.telegram.messenger.video.VideoPlayerRewinder
                    protected void onRewindStart(boolean z3) {
                        ChatMessageCell.this.videoForwardDrawable.setDelegate(new VideoForwardDrawable.VideoForwardDrawableDelegate() { // from class: org.telegram.ui.Cells.ChatMessageCell.6.1
                            @Override // org.telegram.ui.Components.VideoForwardDrawable.VideoForwardDrawableDelegate
                            public void onAnimationEnd() {
                            }

                            @Override // org.telegram.ui.Components.VideoForwardDrawable.VideoForwardDrawableDelegate
                            public void invalidate() {
                                ChatMessageCell.this.invalidate();
                            }
                        });
                        ChatMessageCell.this.videoForwardDrawable.setOneShootAnimation(false);
                        ChatMessageCell.this.videoForwardDrawable.setLeftSide(!z3);
                        ChatMessageCell.this.videoForwardDrawable.setShowing(true);
                        ChatMessageCell.this.invalidate();
                    }
                };
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            this.videoPlayerRewinder.startRewind(MediaController.getInstance().getVideoPlayer(), z2, MediaController.getInstance().getPlaybackSpeed(false));
            return false;
        }
        Drawable drawable = this.replySelector;
        if (drawable != null) {
            this.replySelectorPressed = false;
            drawable.setState(new int[0]);
            invalidate();
        }
        MessageTopicButton messageTopicButton = this.topicButton;
        if (messageTopicButton != null) {
            messageTopicButton.resetClick();
        }
        if (this.pressedEmoji != null) {
            this.pressedEmoji = null;
        }
        LinkSpanDrawable linkSpanDrawable = this.pressedLink;
        if (linkSpanDrawable != null) {
            if (linkSpanDrawable.getSpan() instanceof URLSpanMono) {
                this.hadLongPress = true;
                this.delegate.didPressUrl(this, this.pressedLink.getSpan(), true);
                return true;
            }
            if (this.pressedLink.getSpan() instanceof URLSpanNoUnderline) {
                URLSpanNoUnderline uRLSpanNoUnderline = (URLSpanNoUnderline) this.pressedLink.getSpan();
                if (ChatActivity.isClickableLink(uRLSpanNoUnderline.getURL()) || uRLSpanNoUnderline.getURL().startsWith("/")) {
                    this.hadLongPress = true;
                    this.delegate.didPressUrl(this, this.pressedLink.getSpan(), true);
                    return true;
                }
            } else if (this.pressedLink.getSpan() instanceof URLSpan) {
                this.hadLongPress = true;
                this.delegate.didPressUrl(this, this.pressedLink.getSpan(), true);
                return true;
            }
        }
        resetPressedLink(-1);
        if (this.buttonPressed != 0 || this.miniButtonPressed != 0 || this.videoButtonPressed != 0 || this.pressedBotButton != -1) {
            this.buttonPressed = 0;
            this.miniButtonPressed = 0;
            this.videoButtonPressed = 0;
            this.pressedBotButton = -1;
            invalidate();
        }
        this.linkPreviewPressed = false;
        this.sideButtonPressed = false;
        this.imagePressed = false;
        this.timePressed = false;
        this.gamePreviewPressed = false;
        if (this.pressedVoteButton != -1 || this.pollHintPressed || this.psaHintPressed || this.instantPressed || this.otherPressed || this.commentButtonPressed) {
            this.commentButtonPressed = false;
            this.instantPressed = false;
            setInstantButtonPressed(false);
            this.pressedVoteButton = -1;
            this.pollHintPressed = false;
            this.psaHintPressed = false;
            this.otherPressed = false;
            if (Build.VERSION.SDK_INT >= 21) {
                int i3 = 0;
                while (true) {
                    Drawable[] drawableArr = this.selectorDrawable;
                    if (i3 >= drawableArr.length) {
                        break;
                    }
                    if (drawableArr[i3] != null) {
                        drawableArr[i3].setState(StateSet.NOTHING);
                    }
                    i3++;
                }
            }
            invalidate();
        }
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate != null) {
            if (this.avatarPressed) {
                TLRPC$User tLRPC$User = this.currentUser;
                if (tLRPC$User == null) {
                    TLRPC$Chat tLRPC$Chat = this.currentChat;
                    if (tLRPC$Chat != null) {
                        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader = this.currentMessageObject.messageOwner.fwd_from;
                        if (tLRPC$MessageFwdHeader != null) {
                            if ((tLRPC$MessageFwdHeader.flags & 16) != 0) {
                                i2 = tLRPC$MessageFwdHeader.saved_from_msg_id;
                            } else {
                                i2 = tLRPC$MessageFwdHeader.channel_post;
                            }
                            i = i2;
                        } else {
                            i = 0;
                        }
                        z = chatMessageCellDelegate.didLongPressChannelAvatar(this, tLRPC$Chat, i, this.lastTouchX, this.lastTouchY);
                    }
                } else if (tLRPC$User.id != 0) {
                    z = chatMessageCellDelegate.didLongPressUserAvatar(this, tLRPC$User, this.lastTouchX, this.lastTouchY);
                }
            }
            if (!z) {
                this.delegate.didLongPress(this, this.lastTouchX, this.lastTouchY);
            }
        }
        return true;
    }

    public void showHintButton(boolean z, boolean z2, int i) {
        if (i == -1 || i == 0) {
            if (this.hintButtonVisible == z) {
                return;
            }
            this.hintButtonVisible = z;
            if (!z2) {
                this.hintButtonProgress = z ? 1.0f : 0.0f;
            } else {
                invalidate();
            }
        }
        if ((i == -1 || i == 1) && this.psaButtonVisible != z) {
            this.psaButtonVisible = z;
            if (!z2) {
                this.psaButtonProgress = z ? 1.0f : 0.0f;
            } else {
                setInvalidatesParent(true);
                invalidate();
            }
        }
    }

    public void setCheckPressed(boolean z, boolean z2) {
        this.isCheckPressed = z;
        this.isPressed = z2;
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
    }

    public void setInvalidateSpoilersParent(boolean z) {
        this.invalidateSpoilersParent = z;
    }

    public void setInvalidatesParent(boolean z) {
        this.invalidatesParent = z;
    }

    private boolean invalidateParentForce() {
        return (this.links.isEmpty() && this.reactionsLayoutInBubble.isEmpty) ? false : true;
    }

    public void invalidateOutbounds() {
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate == null || !chatMessageCellDelegate.canDrawOutboundsContent()) {
            if (getParent() instanceof View) {
                ((View) getParent()).invalidate();
                return;
            }
            return;
        }
        super.invalidate();
    }

    @Override // android.view.View, org.telegram.ui.Cells.TextSelectionHelper.SelectableView
    public void invalidate() {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (this.currentMessageObject == null) {
            return;
        }
        super.invalidate();
        if ((this.invalidatesParent || (this.currentMessagesGroup != null && invalidateParentForce())) && getParent() != null) {
            View view = (View) getParent();
            if (view.getParent() != null) {
                view.invalidate();
                ((View) view.getParent()).invalidate();
            }
        }
        if (!this.isBlurred || (chatMessageCellDelegate = this.delegate) == null) {
            return;
        }
        chatMessageCellDelegate.invalidateBlur();
    }

    @Override // android.view.View
    public void invalidate(int i, int i2, int i3, int i4) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (this.currentMessageObject == null) {
            return;
        }
        super.invalidate(i, i2, i3, i4);
        if (this.invalidatesParent && getParent() != null) {
            ((View) getParent()).invalidate(((int) getX()) + i, ((int) getY()) + i2, ((int) getX()) + i3, ((int) getY()) + i4);
        }
        if (!this.isBlurred || (chatMessageCellDelegate = this.delegate) == null) {
            return;
        }
        chatMessageCellDelegate.invalidateBlur();
    }

    public boolean isHighlightedAnimated() {
        return this.isHighlightedAnimated;
    }

    public void setHighlightedAnimated() {
        this.isHighlightedAnimated = true;
        this.highlightProgress = 1000;
        this.lastHighlightProgressTime = System.currentTimeMillis();
        invalidate();
        if (getParent() != null) {
            ((View) getParent()).invalidate();
        }
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean z) {
        if (this.isHighlighted == z) {
            return;
        }
        this.isHighlighted = z;
        if (!z) {
            this.lastHighlightProgressTime = System.currentTimeMillis();
            this.isHighlightedAnimated = true;
            this.highlightProgress = 300;
        } else {
            this.isHighlightedAnimated = false;
            this.highlightProgress = 0;
        }
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
        if (getParent() != null) {
            ((View) getParent()).invalidate();
        }
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(z);
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
    }

    private void updateRadialProgressBackground() {
        if (this.drawRadialCheckBackground) {
            return;
        }
        boolean z = true;
        boolean z2 = (this.isHighlighted || this.isPressed || isPressed()) && !(this.drawPhotoImage && this.photoImage.hasBitmapImage());
        this.radialProgress.setPressed(z2 || this.buttonPressed != 0, false);
        if (this.hasMiniProgress != 0) {
            this.radialProgress.setPressed(z2 || this.miniButtonPressed != 0, true);
        }
        RadialProgress2 radialProgress2 = this.videoRadialProgress;
        if (!z2 && this.videoButtonPressed == 0) {
            z = false;
        }
        radialProgress2.setPressed(z, false);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarPressed() {
        requestDisallowInterceptTouchEvent(true);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarReleased() {
        requestDisallowInterceptTouchEvent(false);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarDrag(float f) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.audioProgress = f;
        MediaController.getInstance().seekToProgress(this.currentMessageObject, f);
        updatePlayingMessageProgress();
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarContinuousDrag(float f) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.audioProgress = f;
        messageObject.audioProgressSec = (int) (messageObject.getDuration() * f);
        updatePlayingMessageProgress();
    }

    public boolean isAnimatingPollAnswer() {
        return this.animatePollAnswerAlpha;
    }

    private void updateWaveform() {
        TLRPC$Message tLRPC$Message;
        MessageObject messageObject;
        TLRPC$Message tLRPC$Message2;
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null) {
            int i = this.documentAttachType;
            if (i == 3 || i == 7) {
                byte[] waveform = messageObject2.getWaveform();
                this.useSeekBarWaveform = waveform != null;
                SeekBarWaveform seekBarWaveform = this.seekBarWaveform;
                if (seekBarWaveform != null) {
                    seekBarWaveform.setWaveform(waveform);
                }
                MessageObject messageObject3 = this.currentMessageObject;
                this.useTranscribeButton = messageObject3 != null && (!messageObject3.isOutOwner() || this.currentMessageObject.isSent()) && ((UserConfig.getInstance(this.currentAccount).isPremium() || !(MessagesController.getInstance(this.currentAccount).didPressTranscribeButtonEnough() || this.currentMessageObject.isOutOwner() || ((((tLRPC$Message2 = (messageObject = this.currentMessageObject).messageOwner) == null || !tLRPC$Message2.voiceTranscriptionForce) && messageObject.getDuration() < 60) || MessagesController.getInstance(this.currentAccount).premiumLocked))) && !(!((this.currentMessageObject.isVoice() && this.useSeekBarWaveform) || this.currentMessageObject.isRoundVideo()) || (tLRPC$Message = this.currentMessageObject.messageOwner) == null || (MessageObject.getMedia(tLRPC$Message) instanceof TLRPC$TL_messageMediaWebPage)));
                updateSeekBarWaveformWidth(null);
            }
        }
    }

    private void updateSeekBarWaveformWidth(Canvas canvas) {
        int i;
        this.seekBarWaveformTranslateX = 0;
        int i2 = -AndroidUtilities.dp((this.hasLinkPreview ? 10 : 0) + 92);
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateBackgroundBoundsInner && ((i = this.documentAttachType) == 3 || i == 7)) {
            int i3 = this.backgroundWidth;
            int i4 = (int) ((i3 - transitionParams.toDeltaLeft) + transitionParams.toDeltaRight);
            int i5 = (int) ((i3 - transitionParams.deltaLeft) + transitionParams.deltaRight);
            if (this.isRoundVideo && !this.drawBackground) {
                i5 = (int) (i5 + (getVideoTranscriptionProgress() * AndroidUtilities.dp(8.0f)));
                i4 += AndroidUtilities.dp(8.0f);
            }
            TransitionParams transitionParams2 = this.transitionParams;
            if (transitionParams2.toDeltaLeft == 0.0f && transitionParams2.toDeltaRight == 0.0f) {
                i4 = i5;
            }
            SeekBarWaveform seekBarWaveform = this.seekBarWaveform;
            if (seekBarWaveform != null) {
                if (transitionParams2.animateUseTranscribeButton) {
                    seekBarWaveform.setSize((i5 + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())), AndroidUtilities.dp(30.0f), i3 + i2 + (!this.useTranscribeButton ? -AndroidUtilities.dp(34.0f) : 0), i4 + i2 + (this.useTranscribeButton ? -AndroidUtilities.dp(34.0f) : 0));
                } else {
                    seekBarWaveform.setSize((i5 + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())), AndroidUtilities.dp(30.0f), (i3 + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())), (i4 + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())));
                }
            }
            SeekBar seekBar = this.seekBar;
            if (seekBar != null) {
                seekBar.setSize((i5 - ((int) (getUseTranscribeButtonProgress() * AndroidUtilities.dp(34.0f)))) - AndroidUtilities.dp((this.documentAttachType != 5 ? 72 : 65) + (this.hasLinkPreview ? 10 : 0)), AndroidUtilities.dp(30.0f));
                return;
            }
            return;
        }
        SeekBarWaveform seekBarWaveform2 = this.seekBarWaveform;
        if (seekBarWaveform2 != null) {
            if (transitionParams.animateUseTranscribeButton) {
                seekBarWaveform2.setSize((this.backgroundWidth + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())), AndroidUtilities.dp(30.0f), this.backgroundWidth + i2 + (!this.useTranscribeButton ? -AndroidUtilities.dp(34.0f) : 0), this.backgroundWidth + i2 + (this.useTranscribeButton ? -AndroidUtilities.dp(34.0f) : 0));
            } else {
                seekBarWaveform2.setSize((this.backgroundWidth + i2) - ((int) (AndroidUtilities.dp(34.0f) * getUseTranscribeButtonProgress())), AndroidUtilities.dp(30.0f));
            }
        }
        SeekBar seekBar2 = this.seekBar;
        if (seekBar2 != null) {
            seekBar2.setSize((this.backgroundWidth - ((int) (getUseTranscribeButtonProgress() * AndroidUtilities.dp(34.0f)))) - AndroidUtilities.dp((this.documentAttachType != 5 ? 72 : 65) + (this.hasLinkPreview ? 10 : 0)), AndroidUtilities.dp(30.0f));
        }
    }

    private int createDocumentLayout(int i, MessageObject messageObject) {
        int i2;
        int i3;
        int i4 = i;
        if (messageObject.type == 0) {
            this.documentAttach = MessageObject.getMedia(messageObject.messageOwner).webpage.document;
        } else {
            this.documentAttach = messageObject.getDocument();
        }
        TLRPC$Document tLRPC$Document = this.documentAttach;
        int i5 = 0;
        if (tLRPC$Document == null) {
            return 0;
        }
        if (MessageObject.isVoiceDocument(tLRPC$Document)) {
            this.documentAttachType = 3;
            int i6 = 0;
            while (true) {
                if (i6 >= this.documentAttach.attributes.size()) {
                    i3 = 0;
                    break;
                }
                TLRPC$DocumentAttribute tLRPC$DocumentAttribute = this.documentAttach.attributes.get(i6);
                if (tLRPC$DocumentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                    i3 = tLRPC$DocumentAttribute.duration;
                    break;
                }
                i6++;
            }
            this.widthBeforeNewTimeLine = (i4 - AndroidUtilities.dp(94.0f)) - ((int) Math.ceil(Theme.chat_audioTimePaint.measureText("00:00")));
            this.availableTimeWidth = i4 - AndroidUtilities.dp(18.0f);
            measureTime(messageObject);
            int dp = AndroidUtilities.dp(174.0f) + this.timeWidth;
            if (!this.hasLinkPreview) {
                this.backgroundWidth = Math.min(i4, dp + ((int) Math.ceil(Theme.chat_audioTimePaint.measureText(AndroidUtilities.formatLongDuration(i3)))));
            }
            this.seekBarWaveform.setMessageObject(messageObject);
            return 0;
        }
        if (MessageObject.isVideoDocument(this.documentAttach)) {
            this.documentAttachType = 4;
            if (!messageObject.needDrawBluredPreview()) {
                updatePlayingMessageProgress();
                String format = String.format("%s", AndroidUtilities.formatFileSize(this.documentAttach.size));
                this.docTitleWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(format));
                this.docTitleLayout = new StaticLayout(format, Theme.chat_infoPaint, this.docTitleWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            return 0;
        }
        if (MessageObject.isMusicDocument(this.documentAttach)) {
            this.documentAttachType = 5;
            int dp2 = i4 - AndroidUtilities.dp(92.0f);
            if (dp2 < 0) {
                dp2 = AndroidUtilities.dp(100.0f);
            }
            int i7 = dp2;
            StaticLayout staticLayout = new StaticLayout(TextUtils.ellipsize(messageObject.getMusicTitle().replace('\n', ' '), Theme.chat_audioTitlePaint, i7 - AndroidUtilities.dp(12.0f), TextUtils.TruncateAt.END), Theme.chat_audioTitlePaint, i7, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.songLayout = staticLayout;
            if (staticLayout.getLineCount() > 0) {
                this.songX = -((int) Math.ceil(this.songLayout.getLineLeft(0)));
            }
            StaticLayout staticLayout2 = new StaticLayout(TextUtils.ellipsize(messageObject.getMusicAuthor().replace('\n', ' '), Theme.chat_audioPerformerPaint, i7, TextUtils.TruncateAt.END), Theme.chat_audioPerformerPaint, i7, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.performerLayout = staticLayout2;
            if (staticLayout2.getLineCount() > 0) {
                this.performerX = -((int) Math.ceil(this.performerLayout.getLineLeft(0)));
            }
            int i8 = 0;
            while (true) {
                if (i8 >= this.documentAttach.attributes.size()) {
                    break;
                }
                TLRPC$DocumentAttribute tLRPC$DocumentAttribute2 = this.documentAttach.attributes.get(i8);
                if (tLRPC$DocumentAttribute2 instanceof TLRPC$TL_documentAttributeAudio) {
                    i5 = tLRPC$DocumentAttribute2.duration;
                    break;
                }
                i8++;
            }
            int ceil = (int) Math.ceil(Theme.chat_audioTimePaint.measureText(AndroidUtilities.formatShortDuration(i5, i5)));
            this.widthBeforeNewTimeLine = (this.backgroundWidth - AndroidUtilities.dp(86.0f)) - ceil;
            this.availableTimeWidth = this.backgroundWidth - AndroidUtilities.dp(28.0f);
            return ceil;
        }
        if (MessageObject.isGifDocument(this.documentAttach, messageObject.hasValidGroupId())) {
            this.documentAttachType = 2;
            if (!messageObject.needDrawBluredPreview()) {
                String string = LocaleController.getString("AttachGif", org.telegram.messenger.R.string.AttachGif);
                this.infoWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(string));
                this.infoLayout = new StaticLayout(string, Theme.chat_infoPaint, this.infoWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                String format2 = String.format("%s", AndroidUtilities.formatFileSize(this.documentAttach.size));
                this.docTitleWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(format2));
                this.docTitleLayout = new StaticLayout(format2, Theme.chat_infoPaint, this.docTitleWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            return 0;
        }
        String str = this.documentAttach.mime_type;
        boolean z = (str != null && (str.toLowerCase().startsWith("image/") || this.documentAttach.mime_type.toLowerCase().startsWith("video/mp4"))) || MessageObject.isDocumentHasThumb(this.documentAttach);
        this.drawPhotoImage = z;
        if (!z) {
            i4 += AndroidUtilities.dp(30.0f);
        }
        this.documentAttachType = 1;
        String documentFileName = FileLoader.getDocumentFileName(this.documentAttach);
        if (documentFileName.length() == 0) {
            documentFileName = LocaleController.getString("AttachDocument", org.telegram.messenger.R.string.AttachDocument);
        }
        StaticLayout createStaticLayout = StaticLayoutEx.createStaticLayout(documentFileName, Theme.chat_docNamePaint, i4, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false, TextUtils.TruncateAt.MIDDLE, i4, 2, false);
        this.docTitleLayout = createStaticLayout;
        this.docTitleOffsetX = LinearLayoutManager.INVALID_OFFSET;
        if (createStaticLayout != null && createStaticLayout.getLineCount() > 0) {
            int i9 = 0;
            while (i5 < this.docTitleLayout.getLineCount()) {
                i9 = Math.max(i9, (int) Math.ceil(this.docTitleLayout.getLineWidth(i5)));
                this.docTitleOffsetX = Math.max(this.docTitleOffsetX, (int) Math.ceil(-this.docTitleLayout.getLineLeft(i5)));
                i5++;
            }
            i2 = Math.min(i4, i9);
        } else {
            this.docTitleOffsetX = 0;
            i2 = i4;
        }
        String str2 = AndroidUtilities.formatFileSize(this.documentAttach.size) + " " + FileLoader.getDocumentExtension(this.documentAttach);
        int dp3 = i4 - AndroidUtilities.dp(30.0f);
        TextPaint textPaint = Theme.chat_infoPaint;
        int min = Math.min(dp3, (int) Math.ceil(textPaint.measureText("000.0 mm / " + AndroidUtilities.formatFileSize(this.documentAttach.size))));
        this.infoWidth = min;
        CharSequence ellipsize = TextUtils.ellipsize(str2, Theme.chat_infoPaint, (float) min, TextUtils.TruncateAt.END);
        try {
            if (this.infoWidth < 0) {
                this.infoWidth = AndroidUtilities.dp(10.0f);
            }
            this.infoLayout = new StaticLayout(ellipsize, Theme.chat_infoPaint, this.infoWidth + AndroidUtilities.dp(6.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (this.drawPhotoImage) {
            this.currentPhotoObject = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 320);
            this.currentPhotoObjectThumb = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 40);
            if ((DownloadController.getInstance(this.currentAccount).getAutodownloadMask() & 1) == 0) {
                this.currentPhotoObject = null;
            }
            TLRPC$PhotoSize tLRPC$PhotoSize = this.currentPhotoObject;
            if (tLRPC$PhotoSize == null || tLRPC$PhotoSize == this.currentPhotoObjectThumb) {
                this.currentPhotoObject = null;
                this.photoImage.setNeedsQualityThumb(true);
                this.photoImage.setShouldGenerateQualityThumb(true);
            } else {
                BitmapDrawable bitmapDrawable = this.currentMessageObject.strippedThumb;
                if (bitmapDrawable != null) {
                    this.currentPhotoObjectThumb = null;
                    this.currentPhotoObjectThumbStripped = bitmapDrawable;
                }
            }
            this.currentPhotoFilter = "86_86_b";
            this.photoImage.setImage(ImageLocation.getForObject(this.currentPhotoObject, messageObject.photoThumbsObject), "86_86", ImageLocation.getForObject(this.currentPhotoObjectThumb, messageObject.photoThumbsObject), this.currentPhotoFilter, this.currentPhotoObjectThumbStripped, 0L, null, messageObject, 1);
        }
        return i2;
    }

    private void calcBackgroundWidth(int i, int i2, int i3) {
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        boolean z = reactionsLayoutInBubble.isEmpty;
        int i4 = (z || reactionsLayoutInBubble.isSmall) ? this.currentMessageObject.lastLineWidth : reactionsLayoutInBubble.lastLineX;
        if (!z && !reactionsLayoutInBubble.isSmall) {
            r4 = i - i4 < i2 || this.currentMessageObject.hasRtl;
            if (this.hasInvoicePreview) {
                this.totalHeight += AndroidUtilities.dp(14.0f);
            }
        } else if (this.hasLinkPreview || this.hasOldCaptionPreview || this.hasGamePreview || this.hasInvoicePreview || i - i4 < i2 || this.currentMessageObject.hasRtl) {
            r4 = true;
        }
        if (r4) {
            this.totalHeight += AndroidUtilities.dp(14.0f);
            this.hasNewLineForTime = true;
            int max = Math.max(i3, i4) + AndroidUtilities.dp(31.0f);
            this.backgroundWidth = max;
            this.backgroundWidth = Math.max(max, (this.currentMessageObject.isOutOwner() ? this.timeWidth + AndroidUtilities.dp(17.0f) : this.timeWidth) + AndroidUtilities.dp(31.0f));
            return;
        }
        int extraTextX = (i3 - getExtraTextX()) - i4;
        if (extraTextX >= 0 && extraTextX <= i2) {
            this.backgroundWidth = ((i3 + i2) - extraTextX) + AndroidUtilities.dp(31.0f);
        } else {
            this.backgroundWidth = Math.max(i3, i4 + i2) + AndroidUtilities.dp(31.0f);
        }
    }

    public void setHighlightedText(String str) {
        MessageObject messageObject = this.messageObjectToSet;
        if (messageObject == null) {
            messageObject = this.currentMessageObject;
        }
        if (messageObject == null || messageObject.messageOwner.message == null || TextUtils.isEmpty(str)) {
            if (this.urlPathSelection.isEmpty()) {
                return;
            }
            this.linkSelectionBlockNum = -1;
            resetUrlPaths();
            invalidate();
            return;
        }
        String lowerCase = str.toLowerCase();
        String lowerCase2 = messageObject.messageOwner.message.toLowerCase();
        int length = lowerCase2.length();
        int i = -1;
        int i2 = -1;
        for (int i3 = 0; i3 < length; i3++) {
            int min = Math.min(lowerCase.length(), length - i3);
            int i4 = 0;
            for (int i5 = 0; i5 < min; i5++) {
                boolean z = lowerCase2.charAt(i3 + i5) == lowerCase.charAt(i5);
                if (z) {
                    if (i4 != 0 || i3 == 0 || " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\n".indexOf(lowerCase2.charAt(i3 - 1)) >= 0) {
                        i4++;
                    } else {
                        z = false;
                    }
                }
                if (!z || i5 == min - 1) {
                    if (i4 > 0 && i4 > i2) {
                        i = i3;
                        i2 = i4;
                    }
                }
            }
        }
        if (i == -1) {
            if (this.urlPathSelection.isEmpty()) {
                return;
            }
            this.linkSelectionBlockNum = -1;
            resetUrlPaths();
            invalidate();
            return;
        }
        int length2 = lowerCase2.length();
        for (int i6 = i + i2; i6 < length2 && " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\n".indexOf(lowerCase2.charAt(i6)) < 0; i6++) {
            i2++;
        }
        int i7 = i + i2;
        if (this.captionLayout != null && !TextUtils.isEmpty(messageObject.caption)) {
            resetUrlPaths();
            try {
                LinkPath obtainNewUrlPath = obtainNewUrlPath();
                obtainNewUrlPath.setCurrentLayout(this.captionLayout, i, 0.0f);
                this.captionLayout.getSelectionPath(i, i7, obtainNewUrlPath);
            } catch (Exception e) {
                FileLog.e(e);
            }
            invalidate();
            return;
        }
        if (messageObject.textLayoutBlocks != null) {
            for (int i8 = 0; i8 < messageObject.textLayoutBlocks.size(); i8++) {
                MessageObject.TextLayoutBlock textLayoutBlock = messageObject.textLayoutBlocks.get(i8);
                if (i >= textLayoutBlock.charactersOffset && i < textLayoutBlock.charactersEnd) {
                    this.linkSelectionBlockNum = i8;
                    resetUrlPaths();
                    try {
                        LinkPath obtainNewUrlPath2 = obtainNewUrlPath();
                        obtainNewUrlPath2.setCurrentLayout(textLayoutBlock.textLayout, i, 0.0f);
                        textLayoutBlock.textLayout.getSelectionPath(i, i7, obtainNewUrlPath2);
                        if (i7 >= textLayoutBlock.charactersOffset + i2) {
                            for (int i9 = i8 + 1; i9 < messageObject.textLayoutBlocks.size(); i9++) {
                                MessageObject.TextLayoutBlock textLayoutBlock2 = messageObject.textLayoutBlocks.get(i9);
                                int i10 = textLayoutBlock2.charactersEnd - textLayoutBlock2.charactersOffset;
                                LinkPath obtainNewUrlPath3 = obtainNewUrlPath();
                                obtainNewUrlPath3.setCurrentLayout(textLayoutBlock2.textLayout, 0, textLayoutBlock2.height);
                                textLayoutBlock2.textLayout.getSelectionPath(0, i7 - textLayoutBlock2.charactersOffset, obtainNewUrlPath3);
                                if (i7 < (textLayoutBlock.charactersOffset + i10) - 1) {
                                    break;
                                }
                            }
                        }
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                    invalidate();
                    return;
                }
            }
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        if (!super.verifyDrawable(drawable)) {
            Drawable[] drawableArr = this.selectorDrawable;
            if (drawable != drawableArr[0] && drawable != drawableArr[1]) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (this.currentMessagesGroup != null) {
            invalidateWithParent();
        }
    }

    private boolean isCurrentLocationTimeExpired(MessageObject messageObject) {
        return MessageObject.getMedia(this.currentMessageObject.messageOwner).period % 60 == 0 ? Math.abs(ConnectionsManager.getInstance(this.currentAccount).getCurrentTime() - messageObject.messageOwner.date) > MessageObject.getMedia(messageObject.messageOwner).period : Math.abs(ConnectionsManager.getInstance(this.currentAccount).getCurrentTime() - messageObject.messageOwner.date) > MessageObject.getMedia(messageObject.messageOwner).period + (-5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLocationExpired() {
        boolean isCurrentLocationTimeExpired;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (isCurrentLocationTimeExpired = isCurrentLocationTimeExpired(messageObject)) == this.locationExpired) {
            return;
        }
        this.locationExpired = isCurrentLocationTimeExpired;
        if (!isCurrentLocationTimeExpired) {
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000L);
            this.scheduledInvalidate = true;
            int dp = this.backgroundWidth - AndroidUtilities.dp(91.0f);
            this.docTitleLayout = new StaticLayout(TextUtils.ellipsize(LocaleController.getString("AttachLiveLocation", org.telegram.messenger.R.string.AttachLiveLocation), Theme.chat_locationTitlePaint, dp, TextUtils.TruncateAt.END), Theme.chat_locationTitlePaint, dp, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        this.currentMessageObject = null;
        setMessageObject(messageObject2, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
    }

    public void setIsUpdating(boolean z) {
        this.isUpdating = true;
    }

    public void setMessageObject(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, boolean z, boolean z2) {
        if (this.attachedToWindow) {
            setMessageContent(messageObject, groupedMessages, z, z2);
            return;
        }
        this.messageObjectToSet = messageObject;
        this.groupedMessagesToSet = groupedMessages;
        this.bottomNearToSet = z;
        this.topNearToSet = z2;
    }

    private int getAdditionalWidthForPosition(MessageObject.GroupedMessagePosition groupedMessagePosition) {
        if (groupedMessagePosition == null) {
            return 0;
        }
        int dp = (groupedMessagePosition.flags & 2) == 0 ? 0 + AndroidUtilities.dp(4.0f) : 0;
        return (groupedMessagePosition.flags & 1) == 0 ? dp + AndroidUtilities.dp(4.0f) : dp;
    }

    public void createSelectorDrawable(final int i) {
        int themedColor;
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (this.psaHintPressed) {
            themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outViews : Theme.key_chat_inViews);
        } else {
            themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewInstantText : Theme.key_chat_inPreviewInstantText);
        }
        Drawable[] drawableArr = this.selectorDrawable;
        if (drawableArr[i] == null) {
            final Paint paint = new Paint(1);
            paint.setColor(-1);
            Drawable drawable = new Drawable() { // from class: org.telegram.ui.Cells.ChatMessageCell.7
                RectF rect = new RectF();
                Path path = new Path();

                @Override // android.graphics.drawable.Drawable
                public int getOpacity() {
                    return -2;
                }

                @Override // android.graphics.drawable.Drawable
                public void setAlpha(int i2) {
                }

                @Override // android.graphics.drawable.Drawable
                public void setColorFilter(ColorFilter colorFilter) {
                }

                @Override // android.graphics.drawable.Drawable
                public void draw(Canvas canvas) {
                    Rect bounds = getBounds();
                    this.rect.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
                    if (ChatMessageCell.this.selectorDrawableMaskType[i] != 3 && ChatMessageCell.this.selectorDrawableMaskType[i] != 4) {
                        if (ChatMessageCell.this.selectorDrawableMaskType[i] != 2) {
                            canvas.drawRoundRect(this.rect, ChatMessageCell.this.selectorDrawableMaskType[i] == 0 ? AndroidUtilities.dp(6.0f) : 0.0f, ChatMessageCell.this.selectorDrawableMaskType[i] == 0 ? AndroidUtilities.dp(6.0f) : 0.0f, paint);
                            return;
                        }
                        this.path.reset();
                        boolean z = ChatMessageCell.this.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.isOutOwner();
                        for (int i2 = 0; i2 < 4; i2++) {
                            if (!ChatMessageCell.this.instantTextNewLine) {
                                if (i2 == 2 && !z) {
                                    float[] fArr = ChatMessageCell.radii;
                                    int i3 = i2 * 2;
                                    float dp = AndroidUtilities.dp(SharedConfig.bubbleRadius);
                                    ChatMessageCell.radii[i3 + 1] = dp;
                                    fArr[i3] = dp;
                                } else if (i2 != 3 || !z) {
                                    if ((ChatMessageCell.this.mediaBackground || ChatMessageCell.this.pinnedBottom) && (i2 == 2 || i2 == 3)) {
                                        float[] fArr2 = ChatMessageCell.radii;
                                        int i4 = i2 * 2;
                                        float[] fArr3 = ChatMessageCell.radii;
                                        int i5 = i4 + 1;
                                        float dp2 = AndroidUtilities.dp(ChatMessageCell.this.pinnedBottom ? Math.min(5, SharedConfig.bubbleRadius) : SharedConfig.bubbleRadius);
                                        fArr3[i5] = dp2;
                                        fArr2[i4] = dp2;
                                    }
                                } else {
                                    float[] fArr4 = ChatMessageCell.radii;
                                    int i6 = i2 * 2;
                                    float dp3 = AndroidUtilities.dp(SharedConfig.bubbleRadius);
                                    ChatMessageCell.radii[i6 + 1] = dp3;
                                    fArr4[i6] = dp3;
                                }
                            }
                            float[] fArr5 = ChatMessageCell.radii;
                            int i7 = i2 * 2;
                            ChatMessageCell.radii[i7 + 1] = 0.0f;
                            fArr5[i7] = 0.0f;
                        }
                        if (!z) {
                            ChatMessageCell chatMessageCell = ChatMessageCell.this;
                            if (!chatMessageCell.drawPinnedBottom && chatMessageCell.currentPosition == null && (ChatMessageCell.this.currentMessageObject == null || ChatMessageCell.this.currentMessageObject.type != 17 || ChatMessageCell.this.pollInstantViewTouchesBottom)) {
                                this.path.moveTo(this.rect.left + AndroidUtilities.dp(6.0f), this.rect.top);
                                this.path.lineTo(this.rect.left + AndroidUtilities.dp(6.0f), (this.rect.bottom - AndroidUtilities.dp(6.0f)) - AndroidUtilities.dp(5.0f));
                                RectF rectF = AndroidUtilities.rectTmp;
                                rectF.set(this.rect.left + AndroidUtilities.dp(-7.0f), this.rect.bottom - AndroidUtilities.dp(23.0f), this.rect.left + AndroidUtilities.dp(6.0f), this.rect.bottom);
                                this.path.arcTo(rectF, 0.0f, 83.0f, false);
                                float f = this.rect.right - (ChatMessageCell.radii[4] * 2.0f);
                                float f2 = this.rect.bottom - (ChatMessageCell.radii[5] * 2.0f);
                                RectF rectF2 = this.rect;
                                rectF.set(f, f2, rectF2.right, rectF2.bottom);
                                this.path.arcTo(rectF, 90.0f, -90.0f, false);
                                Path path = this.path;
                                RectF rectF3 = this.rect;
                                path.lineTo(rectF3.right, rectF3.top);
                                this.path.close();
                                this.path.close();
                                canvas.drawPath(this.path, paint);
                                return;
                            }
                        }
                        this.path.addRoundRect(this.rect, ChatMessageCell.radii, Path.Direction.CW);
                        this.path.close();
                        canvas.drawPath(this.path, paint);
                        return;
                    }
                    canvas.drawCircle(this.rect.centerX(), this.rect.centerY(), AndroidUtilities.dp(ChatMessageCell.this.selectorDrawableMaskType[i] == 3 ? 16.0f : 20.0f), paint);
                }
            };
            int[][] iArr = {StateSet.WILD_CARD};
            int[] iArr2 = new int[1];
            iArr2[0] = 436207615 & getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewInstantText : Theme.key_chat_inPreviewInstantText);
            this.selectorDrawable[i] = new RippleDrawable(new ColorStateList(iArr, iArr2), null, drawable);
            this.selectorDrawable[i].setCallback(this);
        } else {
            Theme.setSelectorDrawableColor(drawableArr[i], themedColor & 436207615, true);
        }
        this.selectorDrawable[i].setVisible(true, false);
    }

    private void createInstantViewButton() {
        String string;
        int measureText;
        if (Build.VERSION.SDK_INT >= 21 && this.drawInstantView) {
            createSelectorDrawable(0);
        }
        if (this.drawInstantView && this.instantViewLayout == null) {
            this.instantWidth = AndroidUtilities.dp(33.0f);
            int i = this.drawInstantViewType;
            if (i == 12) {
                string = LocaleController.getString("OpenChannelPost", org.telegram.messenger.R.string.OpenChannelPost);
            } else if (i == 1) {
                string = LocaleController.getString("OpenChannel", org.telegram.messenger.R.string.OpenChannel);
            } else if (i == 13) {
                string = LocaleController.getString("SendMessage", org.telegram.messenger.R.string.SendMessage).toUpperCase();
            } else if (i == 10) {
                string = LocaleController.getString("OpenBot", org.telegram.messenger.R.string.OpenBot);
            } else if (i == 2) {
                string = LocaleController.getString("OpenGroup", org.telegram.messenger.R.string.OpenGroup);
            } else if (i == 3) {
                string = LocaleController.getString("OpenMessage", org.telegram.messenger.R.string.OpenMessage);
            } else if (i == 5) {
                string = LocaleController.getString("ViewContact", org.telegram.messenger.R.string.ViewContact);
            } else if (i == 6) {
                string = LocaleController.getString("OpenBackground", org.telegram.messenger.R.string.OpenBackground);
            } else if (i == 7) {
                string = LocaleController.getString("OpenTheme", org.telegram.messenger.R.string.OpenTheme);
            } else if (i == 8) {
                if (this.pollVoted || this.pollClosed) {
                    string = LocaleController.getString("PollViewResults", org.telegram.messenger.R.string.PollViewResults);
                } else {
                    string = LocaleController.getString("PollSubmitVotes", org.telegram.messenger.R.string.PollSubmitVotes);
                }
            } else if (i == 9 || i == 11) {
                TLRPC$TL_webPage tLRPC$TL_webPage = (TLRPC$TL_webPage) MessageObject.getMedia(this.currentMessageObject.messageOwner).webpage;
                if (tLRPC$TL_webPage != null && tLRPC$TL_webPage.url.contains("voicechat=")) {
                    string = LocaleController.getString("VoipGroupJoinAsSpeaker", org.telegram.messenger.R.string.VoipGroupJoinAsSpeaker);
                } else {
                    string = LocaleController.getString("VoipGroupJoinAsLinstener", org.telegram.messenger.R.string.VoipGroupJoinAsLinstener);
                }
            } else if (i == 14) {
                string = LocaleController.getString("ViewChatList", org.telegram.messenger.R.string.ViewChatList).toUpperCase();
            } else if (i == 15) {
                string = LocaleController.getString(org.telegram.messenger.R.string.BotWebAppInstantViewOpen).toUpperCase();
            } else {
                string = LocaleController.getString("InstantView", org.telegram.messenger.R.string.InstantView);
            }
            if (this.currentMessageObject.isSponsored() && this.backgroundWidth < (measureText = (int) (Theme.chat_instantViewPaint.measureText(string) + AndroidUtilities.dp(75.0f)))) {
                this.backgroundWidth = measureText;
            }
            int dp = this.backgroundWidth - AndroidUtilities.dp(75.0f);
            this.instantViewLayout = new StaticLayout(TextUtils.ellipsize(string, Theme.chat_instantViewPaint, dp, TextUtils.TruncateAt.END), Theme.chat_instantViewPaint, dp + AndroidUtilities.dp(2.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            if (this.drawInstantViewType == 8) {
                this.instantWidth = this.backgroundWidth - AndroidUtilities.dp(13.0f);
            } else {
                this.instantWidth = this.backgroundWidth - AndroidUtilities.dp(34.0f);
            }
            int dp2 = this.totalHeight + AndroidUtilities.dp(46.0f);
            this.totalHeight = dp2;
            if (this.currentMessageObject.type == 12) {
                this.totalHeight = dp2 + AndroidUtilities.dp(14.0f);
            }
            if (this.currentMessageObject.isSponsored() && this.hasNewLineForTime) {
                this.totalHeight += AndroidUtilities.dp(16.0f);
            }
            StaticLayout staticLayout = this.instantViewLayout;
            if (staticLayout == null || staticLayout.getLineCount() <= 0) {
                return;
            }
            this.instantTextX = (((int) (this.instantWidth - Math.ceil(this.instantViewLayout.getLineWidth(0)))) / 2) + (this.drawInstantViewType == 0 ? AndroidUtilities.dp(8.0f) : 0);
            int lineLeft = (int) this.instantViewLayout.getLineLeft(0);
            this.instantTextLeftX = lineLeft;
            this.instantTextX += -lineLeft;
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.inLayout) {
            return;
        }
        super.requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && (messageObject.checkLayout() || this.lastHeight != AndroidUtilities.displaySize.y)) {
            this.inLayout = true;
            MessageObject messageObject2 = this.currentMessageObject;
            this.currentMessageObject = null;
            setMessageObject(messageObject2, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
            this.inLayout = false;
        }
        updateSelectionTextPosition();
        setMeasuredDimension(View.MeasureSpec.getSize(i), this.totalHeight + this.keyboardHeight);
    }

    public void forceResetMessageObject() {
        MessageObject messageObject = this.messageObjectToSet;
        if (messageObject == null) {
            messageObject = this.currentMessageObject;
        }
        this.currentMessageObject = null;
        setMessageObject(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
    }

    private int getGroupPhotosWidth() {
        int parentWidth = getParentWidth();
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.preview) {
            parentWidth = this.parentWidth;
        }
        if (AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
            return parentWidth;
        }
        if (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2) {
            return parentWidth;
        }
        int i = (parentWidth / 100) * 35;
        if (i < AndroidUtilities.dp(320.0f)) {
            i = AndroidUtilities.dp(320.0f);
        }
        return parentWidth - i;
    }

    private int getExtraTextX() {
        int i = SharedConfig.bubbleRadius;
        if (i >= 15) {
            return AndroidUtilities.dp(2.0f);
        }
        if (i >= 11) {
            return AndroidUtilities.dp(1.0f);
        }
        return 0;
    }

    private int getExtraTimeX() {
        int i;
        if (!this.currentMessageObject.isOutOwner() && ((!this.mediaBackground || this.captionLayout != null) && (i = SharedConfig.bubbleRadius) > 11)) {
            return AndroidUtilities.dp((i - 11) / 1.5f);
        }
        if (!this.currentMessageObject.isOutOwner() && this.isPlayingRound && this.isAvatarVisible && this.currentMessageObject.type == 5) {
            return (int) ((AndroidUtilities.roundPlayingMessageSize - AndroidUtilities.roundMessageSize) * 0.7f);
        }
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int dp;
        int i7;
        int dp2;
        int i8;
        int dp3;
        int i9;
        int dp4;
        int dp5;
        int dp6;
        int i10;
        int dp7;
        int i11;
        int dp8;
        int dp9;
        if (this.currentMessageObject == null) {
            return;
        }
        int measuredHeight = getMeasuredHeight() + (getMeasuredWidth() << 16);
        if (this.lastSize != measuredHeight || !this.wasLayout) {
            this.layoutWidth = getMeasuredWidth();
            this.layoutHeight = getMeasuredHeight() - this.substractBackgroundHeight;
            if (this.timeTextWidth < 0) {
                this.timeTextWidth = AndroidUtilities.dp(10.0f);
            }
            this.timeLayout = new StaticLayout(this.currentTimeString, Theme.chat_timePaint, AndroidUtilities.dp(100.0f) + this.timeTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            if (this.mediaBackground) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.timeX = (this.layoutWidth - this.timeWidth) - AndroidUtilities.dp(42.0f);
                } else {
                    this.timeX = (this.backgroundWidth - AndroidUtilities.dp(4.0f)) - this.timeWidth;
                    if (this.currentMessageObject.isAnyKindOfSticker()) {
                        this.timeX = Math.max(AndroidUtilities.dp(26.0f), this.timeX);
                    }
                    if (this.isAvatarVisible) {
                        this.timeX += AndroidUtilities.dp(48.0f);
                    }
                    MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
                    if (groupedMessagePosition != null && (i5 = groupedMessagePosition.leftSpanOffset) != 0) {
                        this.timeX += (int) Math.ceil((i5 / 1000.0f) * getGroupPhotosWidth());
                    }
                    if (this.captionLayout != null && this.currentPosition != null) {
                        this.timeX += AndroidUtilities.dp(4.0f);
                    }
                }
                if (SharedConfig.bubbleRadius >= 10 && this.captionLayout == null && (i6 = this.documentAttachType) != 7 && i6 != 6) {
                    this.timeX -= AndroidUtilities.dp(2.0f);
                }
            } else if (this.currentMessageObject.isOutOwner()) {
                this.timeX = (this.layoutWidth - this.timeWidth) - AndroidUtilities.dp(38.5f);
            } else {
                this.timeX = (this.backgroundWidth - AndroidUtilities.dp(9.0f)) - this.timeWidth;
                if (this.currentMessageObject.isAnyKindOfSticker()) {
                    this.timeX = Math.max(0, this.timeX);
                }
                if (this.isAvatarVisible) {
                    this.timeX += AndroidUtilities.dp(48.0f);
                }
                if (shouldDrawTimeOnMedia()) {
                    this.timeX -= AndroidUtilities.dp(7.0f);
                }
            }
            this.timeX -= getExtraTimeX();
            try {
                if ((this.currentMessageObject.messageOwner.flags & 1024) != 0) {
                    this.viewsLayout = null;
                } else {
                    this.viewsLayout = null;
                }
            } catch (Exception unused) {
                this.viewsLayout = null;
            }
            if (this.currentRepliesString != null && !this.currentMessageObject.scheduled) {
                this.repliesLayout = new StaticLayout(this.currentRepliesString, Theme.chat_timePaint, this.repliesTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            } else {
                this.repliesLayout = null;
            }
            if (this.isAvatarVisible) {
                this.avatarImage.setImageCoords(AndroidUtilities.dp(6.0f), this.avatarImage.getImageY(), AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
            }
            if (this.currentMessageObject.type == 20 && this.currentUnlockString != null) {
                this.unlockLayout = new StaticLayout(this.currentUnlockString, Theme.chat_unlockExtendedMediaTextPaint, this.unlockTextWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
                int i12 = ((TLRPC$TL_messageExtendedMediaPreview) this.currentMessageObject.messageOwner.media.extended_media).video_duration;
                if (i12 != 0) {
                    String formatDuration = AndroidUtilities.formatDuration(i12, false);
                    this.durationWidth = (int) Math.ceil(Theme.chat_durationPaint.measureText(formatDuration));
                    this.videoInfoLayout = new StaticLayout(formatDuration, Theme.chat_durationPaint, this.durationWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                } else {
                    this.videoInfoLayout = null;
                }
            } else {
                this.unlockLayout = null;
            }
            this.wasLayout = true;
        }
        this.lastSize = measuredHeight;
        if (this.currentMessageObject.type == 0) {
            this.textY = AndroidUtilities.dp(10.0f) + this.namesOffset;
        }
        if (this.isRoundVideo) {
            updatePlayingMessageProgress();
        }
        int i13 = this.documentAttachType;
        if (i13 == 3 || i13 == 7) {
            if (this.currentMessageObject.isOutOwner()) {
                this.seekBarX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(57.0f);
                this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
                this.timeAudioX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(67.0f);
            } else if (this.isChat && !this.isThreadPost && this.currentMessageObject.needDrawAvatar()) {
                this.seekBarX = AndroidUtilities.dp(114.0f);
                this.buttonX = AndroidUtilities.dp(71.0f);
                this.timeAudioX = AndroidUtilities.dp(124.0f);
            } else {
                this.seekBarX = AndroidUtilities.dp(66.0f);
                this.buttonX = AndroidUtilities.dp(23.0f);
                this.timeAudioX = AndroidUtilities.dp(76.0f);
            }
            if (this.hasLinkPreview) {
                this.seekBarX += AndroidUtilities.dp(10.0f);
                this.buttonX += AndroidUtilities.dp(10.0f);
                this.timeAudioX += AndroidUtilities.dp(10.0f);
            }
            updateSeekBarWaveformWidth(null);
            this.seekBarY = AndroidUtilities.dp(13.0f) + this.namesOffset + this.mediaOffsetY;
            int dp10 = AndroidUtilities.dp(13.0f) + this.namesOffset + this.mediaOffsetY;
            this.buttonY = dp10;
            RadialProgress2 radialProgress2 = this.radialProgress;
            int i14 = this.buttonX;
            radialProgress2.setProgressRect(i14, dp10, AndroidUtilities.dp(44.0f) + i14, this.buttonY + AndroidUtilities.dp(44.0f));
            updatePlayingMessageProgress();
            if (this.documentAttachType == 7) {
                MessageObject messageObject = this.currentMessageObject;
                if (messageObject.type == 0 && (this.hasLinkPreview || this.hasGamePreview || this.hasInvoicePreview)) {
                    if (this.hasGamePreview) {
                        i7 = this.unmovedTextX - AndroidUtilities.dp(10.0f);
                    } else {
                        if (this.hasInvoicePreview) {
                            i9 = this.unmovedTextX;
                            dp4 = AndroidUtilities.dp(1.0f);
                        } else {
                            i9 = this.unmovedTextX;
                            dp4 = AndroidUtilities.dp(1.0f);
                        }
                        i7 = i9 + dp4;
                    }
                    if (this.isSmallImage) {
                        i8 = i7 + this.backgroundWidth;
                        dp3 = AndroidUtilities.dp(81.0f);
                        dp = i8 - dp3;
                    } else {
                        dp2 = this.hasInvoicePreview ? -AndroidUtilities.dp(6.3f) : AndroidUtilities.dp(10.0f);
                        dp = i7 + dp2;
                    }
                } else if (messageObject.isOutOwner()) {
                    if (this.mediaBackground) {
                        i8 = this.layoutWidth - this.backgroundWidth;
                        dp3 = AndroidUtilities.dp(3.0f);
                        dp = i8 - dp3;
                    } else {
                        i7 = this.layoutWidth - this.backgroundWidth;
                        dp2 = AndroidUtilities.dp(6.0f);
                        dp = i7 + dp2;
                    }
                } else {
                    if (this.isChat && this.isAvatarVisible && (!this.isPlayingRound || this.currentMessageObject.isVoiceTranscriptionOpen())) {
                        dp = AndroidUtilities.dp(63.0f);
                    } else {
                        dp = AndroidUtilities.dp(15.0f);
                    }
                    MessageObject.GroupedMessagePosition groupedMessagePosition2 = this.currentPosition;
                    if (groupedMessagePosition2 != null && !groupedMessagePosition2.edge) {
                        dp -= AndroidUtilities.dp(10.0f);
                    }
                }
                MessageObject.GroupedMessagePosition groupedMessagePosition3 = this.currentPosition;
                if (groupedMessagePosition3 != null) {
                    if ((groupedMessagePosition3.flags & 1) == 0) {
                        dp -= AndroidUtilities.dp(2.0f);
                    }
                    if (this.currentPosition.leftSpanOffset != 0) {
                        dp += (int) Math.ceil((r2 / 1000.0f) * getGroupPhotosWidth());
                    }
                }
                if (this.currentMessageObject.type != 0) {
                    dp -= AndroidUtilities.dp(2.0f);
                }
                if (this.currentMessageObject.isVoiceTranscriptionOpen()) {
                    dp += AndroidUtilities.dp(10.0f);
                }
                TransitionParams transitionParams = this.transitionParams;
                if (!transitionParams.imageChangeBoundsTransition || transitionParams.updatePhotoImageX) {
                    transitionParams.updatePhotoImageX = false;
                    ImageReceiver imageReceiver = this.photoImage;
                    imageReceiver.setImageCoords(dp, imageReceiver.getImageY(), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
                    return;
                }
                return;
            }
            return;
        }
        if (i13 == 5) {
            if (this.currentMessageObject.isOutOwner()) {
                this.seekBarX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(56.0f);
                this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
                this.timeAudioX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(67.0f);
            } else if (this.isChat && !this.isThreadPost && this.currentMessageObject.needDrawAvatar()) {
                this.seekBarX = AndroidUtilities.dp(113.0f);
                this.buttonX = AndroidUtilities.dp(71.0f);
                this.timeAudioX = AndroidUtilities.dp(124.0f);
            } else {
                this.seekBarX = AndroidUtilities.dp(65.0f);
                this.buttonX = AndroidUtilities.dp(23.0f);
                this.timeAudioX = AndroidUtilities.dp(76.0f);
            }
            if (this.hasLinkPreview) {
                this.seekBarX += AndroidUtilities.dp(10.0f);
                this.buttonX += AndroidUtilities.dp(10.0f);
                this.timeAudioX += AndroidUtilities.dp(10.0f);
            }
            updateSeekBarWaveformWidth(null);
            this.seekBarY = AndroidUtilities.dp(29.0f) + this.namesOffset + this.mediaOffsetY;
            int dp11 = AndroidUtilities.dp(13.0f) + this.namesOffset + this.mediaOffsetY;
            this.buttonY = dp11;
            RadialProgress2 radialProgress22 = this.radialProgress;
            int i15 = this.buttonX;
            radialProgress22.setProgressRect(i15, dp11, AndroidUtilities.dp(44.0f) + i15, this.buttonY + AndroidUtilities.dp(44.0f));
            updatePlayingMessageProgress();
            return;
        }
        if (i13 == 1 && !this.drawPhotoImage) {
            if (this.currentMessageObject.isOutOwner()) {
                this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
            } else if (this.isChat && !this.isThreadPost && this.currentMessageObject.needDrawAvatar()) {
                this.buttonX = AndroidUtilities.dp(71.0f);
            } else {
                this.buttonX = AndroidUtilities.dp(23.0f);
            }
            if (this.hasLinkPreview) {
                this.buttonX += AndroidUtilities.dp(10.0f);
            }
            int dp12 = AndroidUtilities.dp(13.0f) + this.namesOffset + this.mediaOffsetY;
            this.buttonY = dp12;
            RadialProgress2 radialProgress23 = this.radialProgress;
            int i16 = this.buttonX;
            radialProgress23.setProgressRect(i16, dp12, AndroidUtilities.dp(44.0f) + i16, this.buttonY + AndroidUtilities.dp(44.0f));
            this.photoImage.setImageCoords(this.buttonX - AndroidUtilities.dp(10.0f), this.buttonY - AndroidUtilities.dp(10.0f), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        int i17 = messageObject2.type;
        if (i17 == 12) {
            if (messageObject2.isOutOwner()) {
                dp9 = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
            } else if (this.isChat && !this.isThreadPost && this.currentMessageObject.needDrawAvatar()) {
                dp9 = AndroidUtilities.dp(72.0f);
            } else {
                dp9 = AndroidUtilities.dp(23.0f);
            }
            this.photoImage.setImageCoords(dp9, AndroidUtilities.dp(13.0f) + this.namesOffset, AndroidUtilities.dp(44.0f), AndroidUtilities.dp(44.0f));
            return;
        }
        if (i17 == 0 && (this.hasLinkPreview || this.hasGamePreview || this.hasInvoicePreview)) {
            if (this.hasGamePreview) {
                i10 = this.unmovedTextX - AndroidUtilities.dp(10.0f);
            } else {
                if (this.hasInvoicePreview) {
                    i11 = this.unmovedTextX;
                    dp8 = AndroidUtilities.dp(1.0f);
                } else {
                    i11 = this.unmovedTextX;
                    dp8 = AndroidUtilities.dp(1.0f);
                }
                i10 = i11 + dp8;
            }
            if (this.isSmallImage) {
                dp5 = i10 + this.backgroundWidth;
                dp6 = AndroidUtilities.dp(81.0f);
                dp5 -= dp6;
            } else {
                dp7 = this.hasInvoicePreview ? -AndroidUtilities.dp(6.3f) : AndroidUtilities.dp(10.0f);
                dp5 = i10 + dp7;
            }
        } else {
            if (messageObject2.isOutOwner()) {
                if (this.mediaBackground) {
                    dp5 = this.layoutWidth - this.backgroundWidth;
                    dp6 = AndroidUtilities.dp(3.0f);
                } else {
                    i10 = this.layoutWidth - this.backgroundWidth;
                    dp7 = AndroidUtilities.dp(6.0f);
                    dp5 = i10 + dp7;
                }
            } else {
                if (this.isChat && this.isAvatarVisible && !this.isPlayingRound) {
                    dp5 = AndroidUtilities.dp(63.0f);
                } else {
                    dp5 = AndroidUtilities.dp(15.0f);
                }
                MessageObject.GroupedMessagePosition groupedMessagePosition4 = this.currentPosition;
                if (groupedMessagePosition4 != null && !groupedMessagePosition4.edge) {
                    dp6 = AndroidUtilities.dp(10.0f);
                }
            }
            dp5 -= dp6;
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition5 = this.currentPosition;
        if (groupedMessagePosition5 != null) {
            if ((groupedMessagePosition5.flags & 1) == 0) {
                dp5 -= AndroidUtilities.dp(2.0f);
            }
            if (this.currentPosition.leftSpanOffset != 0) {
                dp5 += (int) Math.ceil((r2 / 1000.0f) * getGroupPhotosWidth());
            }
        }
        if (this.currentMessageObject.type != 0) {
            dp5 -= AndroidUtilities.dp(2.0f);
        }
        TransitionParams transitionParams2 = this.transitionParams;
        if (!transitionParams2.imageChangeBoundsTransition || transitionParams2.updatePhotoImageX) {
            transitionParams2.updatePhotoImageX = false;
            ImageReceiver imageReceiver2 = this.photoImage;
            imageReceiver2.setImageCoords(dp5, imageReceiver2.getImageY(), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
        }
        this.buttonX = (int) (dp5 + ((this.photoImage.getImageWidth() - AndroidUtilities.dp(48.0f)) / 2.0f));
        int imageY = (int) (this.photoImage.getImageY() + ((this.photoImage.getImageHeight() - AndroidUtilities.dp(48.0f)) / 2.0f));
        this.buttonY = imageY;
        RadialProgress2 radialProgress24 = this.radialProgress;
        int i18 = this.buttonX;
        radialProgress24.setProgressRect(i18, imageY, AndroidUtilities.dp(48.0f) + i18, this.buttonY + AndroidUtilities.dp(48.0f));
        this.deleteProgressRect.set(this.buttonX + AndroidUtilities.dp(5.0f), this.buttonY + AndroidUtilities.dp(5.0f), this.buttonX + AndroidUtilities.dp(43.0f), this.buttonY + AndroidUtilities.dp(43.0f));
        int i19 = this.documentAttachType;
        if (i19 == 4 || i19 == 2) {
            this.videoButtonX = (int) (this.photoImage.getImageX() + AndroidUtilities.dp(8.0f));
            int imageY2 = (int) (this.photoImage.getImageY() + AndroidUtilities.dp(8.0f));
            this.videoButtonY = imageY2;
            RadialProgress2 radialProgress25 = this.videoRadialProgress;
            int i20 = this.videoButtonX;
            radialProgress25.setProgressRect(i20, imageY2, AndroidUtilities.dp(24.0f) + i20, this.videoButtonY + AndroidUtilities.dp(24.0f));
        }
    }

    public boolean needDelayRoundProgressDraw() {
        int i = this.documentAttachType;
        return (i == 7 || i == 4) && this.currentMessageObject.type != 5 && MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawRoundProgress(android.graphics.Canvas r20) {
        /*
            Method dump skipped, instructions count: 758
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawRoundProgress(android.graphics.Canvas):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updatePollAnimations(long r9) {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.updatePollAnimations(long):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0f64  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0f73  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0fb2  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x1012  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x102d  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x1071  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x101e  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x1009  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0f9d  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0f6c  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0f40  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0f23  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0908  */
    /* JADX WARN: Removed duplicated region for block: B:322:0x095f  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x09f6  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0957  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0f20  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0f3d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawContent(android.graphics.Canvas r35) {
        /*
            Method dump skipped, instructions count: 5268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawContent(android.graphics.Canvas):void");
    }

    private void startRevealMedia(float f, float f2) {
        float sqrt = (float) Math.sqrt(Math.pow(this.photoImage.getImageWidth(), 2.0d) + Math.pow(this.photoImage.getImageHeight(), 2.0d));
        this.mediaSpoilerRevealMaxRadius = sqrt;
        startRevealMedia(f, f2, sqrt);
    }

    private void startRevealMedia(float f, float f2, float f3) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress != 0.0f) {
            return;
        }
        if (messageObject.type == 3) {
            messageObject.forceUpdate = true;
            messageObject.revealingMediaSpoilers = true;
            setMessageContent(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
            MessageObject messageObject2 = this.currentMessageObject;
            messageObject2.revealingMediaSpoilers = false;
            messageObject2.forceUpdate = false;
            if (this.currentMessagesGroup != null) {
                this.radialProgress.setProgress(0.0f, false);
            }
        }
        this.mediaSpoilerRevealX = f;
        this.mediaSpoilerRevealY = f2;
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration((long) MathUtils.clamp(this.mediaSpoilerRevealMaxRadius * 0.3f, 250.0f, 550.0f));
        duration.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ChatMessageCell.this.lambda$startRevealMedia$14(valueAnimator);
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ChatMessageCell.this.currentMessageObject.isMediaSpoilersRevealed = true;
                ChatMessageCell.this.invalidate();
            }
        });
        duration.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRevealMedia$14(ValueAnimator valueAnimator) {
        this.mediaSpoilerRevealProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    private void drawBlurredPhoto(Canvas canvas) {
        if (this.currentMessageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress == 1.0f) {
            return;
        }
        int[] roundRadius = this.photoImage.getRoundRadius();
        float[] fArr = this.mediaSpoilerRadii;
        float f = roundRadius[0];
        fArr[1] = f;
        fArr[0] = f;
        float f2 = roundRadius[1];
        fArr[3] = f2;
        fArr[2] = f2;
        float f3 = roundRadius[2];
        fArr[5] = f3;
        fArr[4] = f3;
        float f4 = roundRadius[3];
        fArr[7] = f4;
        fArr[6] = f4;
        this.mediaSpoilerPath.rewind();
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageX2(), this.photoImage.getImageY2());
        this.mediaSpoilerPath.addRoundRect(rectF, this.mediaSpoilerRadii, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(this.mediaSpoilerPath);
        if (this.mediaSpoilerRevealProgress != 0.0f) {
            this.mediaSpoilerPath.rewind();
            this.mediaSpoilerPath.addCircle(this.mediaSpoilerRevealX, this.mediaSpoilerRevealY, this.mediaSpoilerRevealMaxRadius * this.mediaSpoilerRevealProgress, Path.Direction.CW);
            canvas.clipPath(this.mediaSpoilerPath, Region.Op.DIFFERENCE);
        }
        this.blurredPhotoImage.setImageCoords(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
        this.blurredPhotoImage.setRoundRadius(this.photoImage.getRoundRadius());
        this.blurredPhotoImage.draw(canvas);
        this.mediaSpoilerEffect.setColor(ColorUtils.setAlphaComponent(-1, (int) (Color.alpha(-1) * 0.325f * this.photoImage.getAlpha())));
        this.mediaSpoilerEffect.setBounds((int) this.photoImage.getImageX(), (int) this.photoImage.getImageY(), (int) this.photoImage.getImageX2(), (int) this.photoImage.getImageY2());
        this.mediaSpoilerEffect.draw(canvas);
        canvas.restore();
        invalidate();
    }

    private float getUseTranscribeButtonProgress() {
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateUseTranscribeButton) {
            return this.useTranscribeButton ? 1.0f : 0.0f;
        }
        if (this.useTranscribeButton) {
            return transitionParams.animateChangeProgress;
        }
        return 1.0f - transitionParams.animateChangeProgress;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0017, code lost:
    
        if ((r1 & 1) != 0) goto L10;
     */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateReactionLayoutPosition() {
        /*
            Method dump skipped, instructions count: 512
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.updateReactionLayoutPosition():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:199:0x0972  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0b04  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0b07  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x0b1f  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0b72  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0b9c  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0bb9  */
    /* JADX WARN: Removed duplicated region for block: B:266:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v22 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawLinkPreview(android.graphics.Canvas r36, float r37) {
        /*
            Method dump skipped, instructions count: 3005
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawLinkPreview(android.graphics.Canvas, float):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldDrawMenuDrawable() {
        return this.currentMessagesGroup == null || (this.currentPosition.flags & 4) != 0;
    }

    private void drawBotButtons(Canvas canvas, ArrayList<BotButton> arrayList, int i) {
        int dp;
        Drawable themedDrawable;
        ChatMessageCellDelegate chatMessageCellDelegate;
        float f = 1.0f;
        if (this.currentMessageObject.isOutOwner()) {
            dp = (getMeasuredWidth() - this.widthForButtons) - AndroidUtilities.dp(10.0f);
        } else {
            dp = this.backgroundDrawableLeft + AndroidUtilities.dp((this.mediaBackground || this.drawPinnedBottom) ? 1.0f : 7.0f);
        }
        float f2 = 2.0f;
        float dp2 = (this.layoutHeight - AndroidUtilities.dp(2.0f)) + this.transitionParams.deltaBottom;
        float f3 = 0.0f;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            BotButton botButton = arrayList.get(i2);
            float f4 = botButton.y + botButton.height;
            if (f4 > f3) {
                f3 = f4;
            }
        }
        this.rect.set(0.0f, dp2, getMeasuredWidth(), f3 + dp2);
        if (i != 255) {
            canvas.saveLayerAlpha(this.rect, i, 31);
        } else {
            canvas.save();
        }
        int i3 = 0;
        while (i3 < arrayList.size()) {
            BotButton botButton2 = arrayList.get(i3);
            float dp3 = ((botButton2.y + this.layoutHeight) - AndroidUtilities.dp(f2)) + this.transitionParams.deltaBottom;
            float pressScale = botButton2.getPressScale();
            this.rect.set(botButton2.x + dp, dp3, botButton2.x + dp + botButton2.width, botButton2.height + dp3);
            canvas.save();
            if (pressScale != f) {
                canvas.scale(pressScale, pressScale, this.rect.centerX(), this.rect.centerY());
            }
            applyServiceShaderMatrix();
            Arrays.fill(this.botButtonRadii, AndroidUtilities.dp(Math.min(6.75f, SharedConfig.bubbleRadius)));
            if (botButton2.hasPositionFlag(9)) {
                float[] fArr = this.botButtonRadii;
                float dp4 = AndroidUtilities.dp(SharedConfig.bubbleRadius);
                fArr[7] = dp4;
                fArr[6] = dp4;
            }
            if (botButton2.hasPositionFlag(10)) {
                float[] fArr2 = this.botButtonRadii;
                float dp5 = AndroidUtilities.dp(SharedConfig.bubbleRadius);
                fArr2[5] = dp5;
                fArr2[4] = dp5;
            }
            this.botButtonPath.rewind();
            this.botButtonPath.addRoundRect(this.rect, this.botButtonRadii, Path.Direction.CW);
            canvas.drawPath(this.botButtonPath, getThemedPaint("paintChatActionBackground"));
            if (hasGradientService()) {
                canvas.drawPath(this.botButtonPath, Theme.chat_actionBackgroundGradientDarkenPaint);
            }
            boolean z = (((botButton2.button instanceof TLRPC$TL_keyboardButtonCallback) || (botButton2.button instanceof TLRPC$TL_keyboardButtonGame) || (botButton2.button instanceof TLRPC$TL_keyboardButtonBuy) || (botButton2.button instanceof TLRPC$TL_keyboardButtonUrlAuth)) && SendMessagesHelper.getInstance(this.currentAccount).isSendingCallback(this.currentMessageObject, botButton2.button)) || ((botButton2.button instanceof TLRPC$TL_keyboardButtonRequestGeoLocation) && SendMessagesHelper.getInstance(this.currentAccount).isSendingCurrentLocation(this.currentMessageObject, botButton2.button)) || ((botButton2.button instanceof TLRPC$TL_keyboardButtonUrl) && (chatMessageCellDelegate = this.delegate) != null && chatMessageCellDelegate.isProgressLoading(this, 3) && this.delegate.getProgressLoadingBotButtonUrl(this) == botButton2.button.url);
            canvas.save();
            canvas.clipPath(this.botButtonPath);
            if (z) {
                if (botButton2.loadingDrawable == null) {
                    botButton2.loadingDrawable = new LoadingDrawable();
                    botButton2.loadingDrawable.setRadiiDp(5.5f);
                    botButton2.loadingDrawable.setAppearByGradient(true);
                    botButton2.loadingDrawable.strokePaint.setStrokeWidth(AndroidUtilities.dpf2(1.25f));
                } else if (botButton2.loadingDrawable.isDisappeared() || botButton2.loadingDrawable.isDisappearing()) {
                    botButton2.loadingDrawable.reset();
                    botButton2.loadingDrawable.resetDisappear();
                }
            } else if (botButton2.loadingDrawable != null && !botButton2.loadingDrawable.isDisappearing() && !botButton2.loadingDrawable.isDisappeared()) {
                botButton2.loadingDrawable.disappear();
            }
            if (botButton2.loadingDrawable != null && (z || botButton2.loadingDrawable.isDisappearing())) {
                this.rect.inset(AndroidUtilities.dpf2(0.625f), AndroidUtilities.dpf2(0.625f));
                botButton2.loadingDrawable.setRadii(this.botButtonRadii);
                botButton2.loadingDrawable.setBounds(this.rect);
                LoadingDrawable loadingDrawable = botButton2.loadingDrawable;
                int i4 = Theme.key_chat_serviceBackgroundSelector;
                loadingDrawable.setColors(Theme.multAlpha(Theme.getColor(i4, this.resourcesProvider), f), Theme.multAlpha(Theme.getColor(i4, this.resourcesProvider), 2.5f), Theme.multAlpha(Theme.getColor(i4, this.resourcesProvider), 3.0f), Theme.multAlpha(Theme.getColor(i4, this.resourcesProvider), 10.0f));
                botButton2.loadingDrawable.setAlpha(255);
                botButton2.loadingDrawable.draw(canvas);
                invalidateOutbounds();
            }
            if (botButton2.selectorDrawable != null) {
                int i5 = (int) dp3;
                botButton2.selectorDrawable.setBounds(botButton2.x + dp, i5, botButton2.x + dp + botButton2.width, botButton2.height + i5);
                botButton2.selectorDrawable.setAlpha(255);
                botButton2.selectorDrawable.draw(canvas);
            }
            canvas.restore();
            canvas.save();
            canvas.translate(botButton2.x + dp + AndroidUtilities.dp(5.0f), ((AndroidUtilities.dp(44.0f) - botButton2.title.getLineBottom(botButton2.title.getLineCount() - 1)) / 2) + dp3);
            botButton2.title.draw(canvas);
            canvas.restore();
            if (!(botButton2.button instanceof TLRPC$TL_keyboardButtonWebView)) {
                if (botButton2.button instanceof TLRPC$TL_keyboardButtonUrl) {
                    if (botButton2.isInviteButton) {
                        themedDrawable = getThemedDrawable("drawable_botInvite");
                    } else {
                        themedDrawable = getThemedDrawable("drawableBotLink");
                    }
                    BaseCell.setDrawableBounds(themedDrawable, (((botButton2.x + botButton2.width) - AndroidUtilities.dp(3.0f)) - themedDrawable.getIntrinsicWidth()) + dp, dp3 + AndroidUtilities.dp(3.0f));
                    themedDrawable.draw(canvas);
                } else if (!(botButton2.button instanceof TLRPC$TL_keyboardButtonSwitchInline) && !(botButton2.button instanceof TLRPC$TL_keyboardButtonRequestPeer)) {
                    if (botButton2.button instanceof TLRPC$TL_keyboardButtonBuy) {
                        BaseCell.setDrawableBounds(Theme.chat_botCardDrawable, (((botButton2.x + botButton2.width) - AndroidUtilities.dp(5.0f)) - Theme.chat_botCardDrawable.getIntrinsicWidth()) + dp, dp3 + AndroidUtilities.dp(4.0f));
                        Theme.chat_botCardDrawable.draw(canvas);
                    }
                } else {
                    Drawable themedDrawable2 = getThemedDrawable("drawableBotInline");
                    BaseCell.setDrawableBounds(themedDrawable2, (((botButton2.x + botButton2.width) - AndroidUtilities.dp(3.0f)) - themedDrawable2.getIntrinsicWidth()) + dp, dp3 + AndroidUtilities.dp(3.0f));
                    themedDrawable2.draw(canvas);
                }
            } else {
                Drawable themedDrawable3 = getThemedDrawable("drawableBotWebView");
                BaseCell.setDrawableBounds(themedDrawable3, (((botButton2.x + botButton2.width) - AndroidUtilities.dp(3.0f)) - themedDrawable3.getIntrinsicWidth()) + dp, dp3 + AndroidUtilities.dp(3.0f));
                themedDrawable3.draw(canvas);
            }
            canvas.restore();
            i3++;
            f = 1.0f;
            f2 = 2.0f;
        }
        canvas.restore();
    }

    private boolean allowDrawPhotoImage() {
        return !this.currentMessageObject.hasMediaSpoilers() || this.currentMessageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress != 0.0f || this.blurredPhotoImage.getBitmap() == null;
    }

    public void drawMessageText(Canvas canvas, ArrayList<MessageObject.TextLayoutBlock> arrayList, boolean z, float f, boolean z2) {
        MessageObject messageObject = this.currentMessageObject;
        drawMessageText(canvas, arrayList, messageObject == null ? 0.0f : messageObject.textXOffset, z, f, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x02b6 A[LOOP:2: B:110:0x02ae->B:112:0x02b6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x028f  */
    @android.annotation.SuppressLint({"Range"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawMessageText(android.graphics.Canvas r27, java.util.ArrayList<org.telegram.messenger.MessageObject.TextLayoutBlock> r28, float r29, boolean r30, float r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 847
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawMessageText(android.graphics.Canvas, java.util.ArrayList, float, boolean, float, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0063 A[LOOP:1: B:26:0x0063->B:28:0x0066, LOOP_START, PHI: r2 r5
      0x0063: PHI (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:25:0x0061, B:28:0x0066] A[DONT_GENERATE, DONT_INLINE]
      0x0063: PHI (r5v2 int) = (r5v1 int), (r5v3 int) binds: [B:25:0x0061, B:28:0x0066] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.telegram.ui.Components.AnimatedEmojiSpan[] getAnimatedEmojiSpans() {
        /*
            r7 = this;
            org.telegram.messenger.MessageObject r0 = r7.currentMessageObject
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L1c
            java.lang.CharSequence r0 = r0.messageText
            boolean r3 = r0 instanceof android.text.Spanned
            if (r3 == 0) goto L1c
            r3 = r0
            android.text.Spanned r3 = (android.text.Spanned) r3
            int r0 = r0.length()
            java.lang.Class<org.telegram.ui.Components.AnimatedEmojiSpan> r4 = org.telegram.ui.Components.AnimatedEmojiSpan.class
            java.lang.Object[] r0 = r3.getSpans(r2, r0, r4)
            org.telegram.ui.Components.AnimatedEmojiSpan[] r0 = (org.telegram.ui.Components.AnimatedEmojiSpan[]) r0
            goto L1d
        L1c:
            r0 = r1
        L1d:
            org.telegram.messenger.MessageObject r3 = r7.currentMessageObject
            if (r3 == 0) goto L37
            java.lang.CharSequence r3 = r3.caption
            boolean r4 = r3 instanceof android.text.Spanned
            if (r4 == 0) goto L37
            r4 = r3
            android.text.Spanned r4 = (android.text.Spanned) r4
            int r3 = r3.length()
            java.lang.Class<org.telegram.ui.Components.AnimatedEmojiSpan> r5 = org.telegram.ui.Components.AnimatedEmojiSpan.class
            java.lang.Object[] r3 = r4.getSpans(r2, r3, r5)
            org.telegram.ui.Components.AnimatedEmojiSpan[] r3 = (org.telegram.ui.Components.AnimatedEmojiSpan[]) r3
            goto L38
        L37:
            r3 = r1
        L38:
            if (r0 == 0) goto L3d
            int r4 = r0.length
            if (r4 != 0) goto L43
        L3d:
            if (r3 == 0) goto L6f
            int r4 = r3.length
            if (r4 != 0) goto L43
            goto L6f
        L43:
            if (r0 != 0) goto L47
            r1 = 0
            goto L48
        L47:
            int r1 = r0.length
        L48:
            if (r3 != 0) goto L4c
            r4 = 0
            goto L4d
        L4c:
            int r4 = r3.length
        L4d:
            int r1 = r1 + r4
            org.telegram.ui.Components.AnimatedEmojiSpan[] r1 = new org.telegram.ui.Components.AnimatedEmojiSpan[r1]
            if (r0 == 0) goto L60
            r4 = 0
            r5 = 0
        L54:
            int r6 = r0.length
            if (r4 >= r6) goto L61
            r6 = r0[r4]
            r1[r5] = r6
            int r4 = r4 + 1
            int r5 = r5 + 1
            goto L54
        L60:
            r5 = 0
        L61:
            if (r3 == 0) goto L6f
        L63:
            int r0 = r3.length
            if (r2 >= r0) goto L6f
            r0 = r3[r2]
            r1[r5] = r0
            int r2 = r2 + 1
            int r5 = r5 + 1
            goto L63
        L6f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.getAnimatedEmojiSpans():org.telegram.ui.Components.AnimatedEmojiSpan[]");
    }

    public void updateCaptionLayout() {
        float imageX;
        float imageY;
        float imageHeight;
        MessageObject messageObject = this.currentMessageObject;
        int i = messageObject.type;
        if (i == 1 || i == 20 || this.documentAttachType == 4 || i == 8) {
            TransitionParams transitionParams = this.transitionParams;
            if (transitionParams.imageChangeBoundsTransition) {
                imageX = transitionParams.animateToImageX;
                imageY = transitionParams.animateToImageY;
                imageHeight = transitionParams.animateToImageH;
            } else {
                imageX = this.photoImage.getImageX();
                imageY = this.photoImage.getImageY();
                imageHeight = this.photoImage.getImageHeight();
            }
            this.captionX = imageX + AndroidUtilities.dp(5.0f) + this.captionOffsetX;
            this.captionY = imageY + imageHeight + AndroidUtilities.dp(6.0f);
        } else {
            if (this.hasOldCaptionPreview) {
                this.captionX = this.backgroundDrawableLeft + AndroidUtilities.dp(messageObject.isOutOwner() ? 11.0f : 17.0f) + this.captionOffsetX;
                float dp = (((this.totalHeight - this.captionHeight) - AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f)) - this.linkPreviewHeight) - AndroidUtilities.dp(17.0f);
                this.captionY = dp;
                if (this.drawCommentButton && this.drawSideButton != 3) {
                    this.captionY = dp - AndroidUtilities.dp(shouldDrawTimeOnMedia() ? 41.3f : 43.0f);
                }
            } else {
                if (this.isRoundVideo) {
                    this.captionX = getBackgroundDrawableLeft() + AndroidUtilities.dp((this.currentMessageObject.isOutOwner() ? 0 : 6) + 11);
                } else {
                    int i2 = this.backgroundDrawableLeft;
                    if (!messageObject.isOutOwner() && !this.mediaBackground && !this.drawPinnedBottom) {
                        r7 = 17.0f;
                    }
                    this.captionX = i2 + AndroidUtilities.dp(r7) + this.captionOffsetX;
                }
                float dp2 = (this.totalHeight - this.captionHeight) - AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f);
                this.captionY = dp2;
                if (this.drawCommentButton && this.drawSideButton != 3) {
                    this.captionY = dp2 - AndroidUtilities.dp(shouldDrawTimeOnMedia() ? 41.3f : 43.0f);
                }
                ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
                if (!reactionsLayoutInBubble.isEmpty && !reactionsLayoutInBubble.isSmall) {
                    this.captionY -= reactionsLayoutInBubble.totalHeight;
                }
            }
        }
        this.captionX += getExtraTextX();
    }

    private boolean textIsSelectionMode() {
        return getCurrentMessagesGroup() == null && this.delegate.getTextSelectionHelper() != null && this.delegate.getTextSelectionHelper().isSelected(this.currentMessageObject);
    }

    public float getViewTop() {
        return this.viewTop;
    }

    public int getBackgroundHeight() {
        return this.backgroundHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMiniIconForCurrentState() {
        int i = this.miniButtonState;
        if (i < 0) {
            return 4;
        }
        return i == 0 ? 2 : 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIconForCurrentState() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (messageObject != null && messageObject.hasExtendedMedia())) {
            return 4;
        }
        if (this.documentAttachType == 7 && this.currentMessageObject.isVoiceTranscriptionOpen() && this.canStreamVideo) {
            int i = this.buttonState;
            return (i == 1 || i == 4) ? 1 : 0;
        }
        int i2 = this.documentAttachType;
        if (i2 == 3 || i2 == 5) {
            if (this.currentMessageObject.isOutOwner()) {
                this.radialProgress.setColorKeys(Theme.key_chat_outLoader, Theme.key_chat_outLoaderSelected, Theme.key_chat_outMediaIcon, Theme.key_chat_outMediaIconSelected);
            } else {
                this.radialProgress.setColorKeys(Theme.key_chat_inLoader, Theme.key_chat_inLoaderSelected, Theme.key_chat_inMediaIcon, Theme.key_chat_inMediaIconSelected);
            }
            int i3 = this.buttonState;
            if (i3 == 1) {
                return 1;
            }
            if (i3 == 2) {
                return 2;
            }
            return i3 == 4 ? 3 : 0;
        }
        if (i2 == 1 && !this.drawPhotoImage) {
            if (this.currentMessageObject.isOutOwner()) {
                this.radialProgress.setColorKeys(Theme.key_chat_outLoader, Theme.key_chat_outLoaderSelected, Theme.key_chat_outMediaIcon, Theme.key_chat_outMediaIconSelected);
            } else {
                this.radialProgress.setColorKeys(Theme.key_chat_inLoader, Theme.key_chat_inLoaderSelected, Theme.key_chat_inMediaIcon, Theme.key_chat_inMediaIconSelected);
            }
            int i4 = this.buttonState;
            if (i4 == -1) {
                return 5;
            }
            if (i4 == 0) {
                return 2;
            }
            if (i4 == 1) {
                return 3;
            }
        } else {
            RadialProgress2 radialProgress2 = this.radialProgress;
            int i5 = Theme.key_chat_mediaLoaderPhoto;
            int i6 = Theme.key_chat_mediaLoaderPhotoSelected;
            int i7 = Theme.key_chat_mediaLoaderPhotoIcon;
            int i8 = Theme.key_chat_mediaLoaderPhotoIconSelected;
            radialProgress2.setColorKeys(i5, i6, i7, i8);
            this.videoRadialProgress.setColorKeys(i5, i6, i7, i8);
            int i9 = this.buttonState;
            if (i9 >= 0 && i9 < 4) {
                if (i9 == 0) {
                    return 2;
                }
                if (i9 == 1) {
                    return 3;
                }
                return (i9 != 2 && this.autoPlayingMedia) ? 4 : 0;
            }
            if (i9 == -1) {
                if (this.documentAttachType == 1) {
                    if (this.drawPhotoImage && (this.currentPhotoObject != null || this.currentPhotoObjectThumb != null)) {
                        if (this.photoImage.hasBitmapImage()) {
                            return 4;
                        }
                        MessageObject messageObject2 = this.currentMessageObject;
                        if (messageObject2.mediaExists || messageObject2.attachPathExists) {
                            return 4;
                        }
                    }
                    return 5;
                }
                if (this.currentMessageObject.needDrawBluredPreview()) {
                    MessageObject messageObject3 = this.currentMessageObject;
                    if (messageObject3.messageOwner.destroyTime != 0) {
                        return messageObject3.isOutOwner() ? 9 : 11;
                    }
                    return 7;
                }
                if (this.hasEmbed) {
                    return 0;
                }
            }
        }
        MessageObject messageObject4 = this.currentMessageObject;
        return (messageObject4 != null && this.isRoundVideo && messageObject4.isVoiceTranscriptionOpen()) ? 0 : 4;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x010c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getMaxNameWidth() {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.getMaxNameWidth():int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x011c, code lost:
    
        if ((r12 & 2) != 0) goto L92;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateButtonState(boolean r17, boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 2179
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.updateButtonState(boolean, boolean, boolean):void");
    }

    private void didPressMiniButton(boolean z) {
        int i = this.miniButtonState;
        if (i != 0) {
            if (i == 1) {
                int i2 = this.documentAttachType;
                if ((i2 == 3 || i2 == 5 || i2 == 7) && MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    MediaController.getInstance().cleanupPlayer(true, true);
                }
                this.miniButtonState = 0;
                this.currentMessageObject.loadingCancelled = true;
                FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
                this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                invalidate();
                return;
            }
            return;
        }
        this.miniButtonState = 1;
        this.radialProgress.setProgress(0.0f, false);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.isAnyKindOfSticker()) {
            this.currentMessageObject.putInDownloadsStore = true;
        }
        int i3 = this.documentAttachType;
        if (i3 == 3 || i3 == 5) {
            FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
            this.currentMessageObject.loadingCancelled = false;
        } else if (i3 == 4 || i3 == 7) {
            createLoadingProgressLayout(this.documentAttach);
            FileLoader fileLoader = FileLoader.getInstance(this.currentAccount);
            TLRPC$Document tLRPC$Document = this.documentAttach;
            MessageObject messageObject2 = this.currentMessageObject;
            fileLoader.loadFile(tLRPC$Document, messageObject2, 2, messageObject2.shouldEncryptPhotoOrVideo() ? 2 : 0);
            this.currentMessageObject.loadingCancelled = false;
        }
        this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
        invalidate();
    }

    private void didPressButton(boolean z, boolean z2) {
        MessageObject messageObject;
        MessageObject messageObject2;
        MessageObject messageObject3;
        TLRPC$PhotoSize tLRPC$PhotoSize;
        String str;
        MessageObject messageObject4;
        MessageObject messageObject5 = this.currentMessageObject;
        if (messageObject5 != null && !messageObject5.isAnyKindOfSticker()) {
            this.currentMessageObject.putInDownloadsStore = true;
        }
        int i = this.buttonState;
        if (i == 0 && (!this.drawVideoImageButton || z2)) {
            int i2 = this.documentAttachType;
            if (i2 == 3 || i2 == 5 || (i2 == 7 && (messageObject4 = this.currentMessageObject) != null && messageObject4.isVoiceTranscriptionOpen() && this.currentMessageObject.mediaExists)) {
                if (this.miniButtonState == 0) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                    this.currentMessageObject.loadingCancelled = false;
                }
                if (this.delegate.needPlayMessage(this.currentMessageObject, false)) {
                    if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                        this.miniButtonState = 1;
                        this.radialProgress.setProgress(0.0f, false);
                        this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                    }
                    updatePlayingMessageProgress();
                    this.buttonState = 1;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                    invalidate();
                    return;
                }
                return;
            }
            if (z2) {
                this.videoRadialProgress.setProgress(0.0f, false);
            } else {
                this.radialProgress.setProgress(0.0f, false);
            }
            if (this.currentPhotoObject != null && (this.photoImage.hasNotThumb() || this.currentPhotoObjectThumb == null)) {
                tLRPC$PhotoSize = this.currentPhotoObject;
                str = ((tLRPC$PhotoSize instanceof TLRPC$TL_photoStrippedSize) || "s".equals(tLRPC$PhotoSize.type)) ? this.currentPhotoFilterThumb : this.currentPhotoFilter;
            } else {
                tLRPC$PhotoSize = this.currentPhotoObjectThumb;
                str = this.currentPhotoFilterThumb;
            }
            String str2 = str;
            MessageObject messageObject6 = this.currentMessageObject;
            int i3 = messageObject6.type;
            if (i3 == 1 || i3 == 20) {
                this.photoImage.setForceLoading(true);
                ImageReceiver imageReceiver = this.photoImage;
                ImageLocation forObject = ImageLocation.getForObject(this.currentPhotoObject, this.photoParentObject);
                String str3 = this.currentPhotoFilter;
                ImageLocation forObject2 = ImageLocation.getForObject(this.currentPhotoObjectThumb, this.photoParentObject);
                String str4 = this.currentPhotoFilterThumb;
                BitmapDrawable bitmapDrawable = this.currentPhotoObjectThumbStripped;
                long j = this.currentPhotoObject.size;
                MessageObject messageObject7 = this.currentMessageObject;
                imageReceiver.setImage(forObject, str3, forObject2, str4, bitmapDrawable, j, null, messageObject7, messageObject7.shouldEncryptPhotoOrVideo() ? 2 : 0);
            } else if (i3 == 8) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                if (this.currentMessageObject.loadedFileSize > 0) {
                    createLoadingProgressLayout(this.documentAttach);
                }
            } else if (this.isRoundVideo) {
                if (messageObject6.isSecretMedia()) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.currentMessageObject.getDocument(), this.currentMessageObject, 2, 1);
                } else {
                    MessageObject messageObject8 = this.currentMessageObject;
                    messageObject8.gifState = 2.0f;
                    TLRPC$Document document = messageObject8.getDocument();
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForDocument(document), null, ImageLocation.getForObject(tLRPC$PhotoSize, document), str2, document.size, null, this.currentMessageObject, 0);
                }
                this.wouldBeInPip = true;
                invalidate();
            } else if (i3 == 9) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                if (this.currentMessageObject.loadedFileSize > 0) {
                    createLoadingProgressLayout(this.documentAttach);
                }
            } else {
                int i4 = this.documentAttachType;
                if (i4 == 4) {
                    FileLoader fileLoader = FileLoader.getInstance(this.currentAccount);
                    TLRPC$Document tLRPC$Document = this.documentAttach;
                    MessageObject messageObject9 = this.currentMessageObject;
                    fileLoader.loadFile(tLRPC$Document, messageObject9, 1, messageObject9.shouldEncryptPhotoOrVideo() ? 2 : 0);
                    MessageObject messageObject10 = this.currentMessageObject;
                    if (messageObject10.loadedFileSize > 0) {
                        createLoadingProgressLayout(messageObject10.getDocument());
                    }
                } else if (i3 != 0 || i4 == 0) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForObject(this.currentPhotoObject, this.photoParentObject), this.currentPhotoFilter, ImageLocation.getForObject(this.currentPhotoObjectThumb, this.photoParentObject), this.currentPhotoFilterThumb, this.currentPhotoObjectThumbStripped, 0L, null, this.currentMessageObject, 0);
                } else if (i4 == 2) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForDocument(this.documentAttach), null, ImageLocation.getForDocument(this.currentPhotoObject, this.documentAttach), this.currentPhotoFilterThumb, this.documentAttach.size, null, this.currentMessageObject, 0);
                    MessageObject messageObject11 = this.currentMessageObject;
                    messageObject11.gifState = 2.0f;
                    if (messageObject11.loadedFileSize > 0) {
                        createLoadingProgressLayout(messageObject11.getDocument());
                    }
                } else if (i4 == 1) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                } else if (i4 == 8) {
                    this.photoImage.setImage(ImageLocation.getForDocument(this.documentAttach), this.currentPhotoFilter, ImageLocation.getForDocument(this.currentPhotoObject, this.documentAttach), "b1", 0L, "jpg", this.currentMessageObject, 1);
                }
            }
            this.currentMessageObject.loadingCancelled = false;
            this.buttonState = 1;
            if (z2) {
                this.videoRadialProgress.setIcon(14, false, z);
            } else {
                this.radialProgress.setIcon(getIconForCurrentState(), false, z);
            }
            invalidate();
            return;
        }
        if (i == 1 && (!this.drawVideoImageButton || z2)) {
            this.photoImage.setForceLoading(false);
            int i5 = this.documentAttachType;
            if (i5 == 3 || i5 == 5 || (i5 == 7 && (messageObject3 = this.currentMessageObject) != null && messageObject3.isVoiceTranscriptionOpen())) {
                if (MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject)) {
                    this.buttonState = 0;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, z);
                    invalidate();
                    return;
                }
                return;
            }
            if (this.currentMessageObject.isOut() && !this.drawVideoImageButton && (this.currentMessageObject.isSending() || this.currentMessageObject.isEditing())) {
                if (this.radialProgress.getIcon() != 6) {
                    this.delegate.didPressCancelSendButton(this);
                    return;
                }
                return;
            }
            MessageObject messageObject12 = this.currentMessageObject;
            messageObject12.loadingCancelled = true;
            int i6 = this.documentAttachType;
            if (i6 == 2 || i6 == 4 || i6 == 1 || i6 == 8) {
                FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
            } else {
                int i7 = messageObject12.type;
                if (i7 == 0 || i7 == 1 || i7 == 20 || i7 == 8 || i7 == 5) {
                    ImageLoader.getInstance().cancelForceLoadingForImageReceiver(this.photoImage);
                    this.photoImage.cancelLoadImage();
                } else if (i7 == 9) {
                    FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
                }
            }
            this.buttonState = 0;
            if (z2) {
                this.videoRadialProgress.setIcon(2, false, z);
            } else {
                this.radialProgress.setIcon(getIconForCurrentState(), false, z);
            }
            invalidate();
            return;
        }
        if (i != 2) {
            if (i == 3 || i == 0) {
                if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                    this.miniButtonState = 1;
                    this.radialProgress.setProgress(0.0f, false);
                    this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, z);
                }
                this.delegate.didPressImage(this, 0.0f, 0.0f);
                return;
            }
            if (i == 4) {
                int i8 = this.documentAttachType;
                if (i8 == 3 || i8 == 5 || (i8 == 7 && (messageObject = this.currentMessageObject) != null && messageObject.isVoiceTranscriptionOpen())) {
                    if ((this.currentMessageObject.isOut() && (this.currentMessageObject.isSending() || this.currentMessageObject.isEditing())) || this.currentMessageObject.isSendError()) {
                        if (this.delegate == null || this.radialProgress.getIcon() == 6) {
                            return;
                        }
                        this.delegate.didPressCancelSendButton(this);
                        return;
                    }
                    this.currentMessageObject.loadingCancelled = true;
                    FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
                    this.buttonState = 2;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, z);
                    invalidate();
                    return;
                }
                return;
            }
            return;
        }
        if (this.documentAttachType == 7 && (messageObject2 = this.currentMessageObject) != null && messageObject2.isVoiceTranscriptionOpen()) {
            if (this.miniButtonState == 0) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                this.currentMessageObject.loadingCancelled = false;
            }
            if (this.delegate.needPlayMessage(this.currentMessageObject, false)) {
                if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                    this.miniButtonState = 1;
                    this.radialProgress.setProgress(0.0f, false);
                    this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                }
                updatePlayingMessageProgress();
                this.buttonState = 1;
                this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                invalidate();
            }
            if (this.isRoundVideo) {
                this.wouldBeInPip = true;
                invalidate();
                return;
            }
            return;
        }
        int i9 = this.documentAttachType;
        if (i9 == 3 || i9 == 5) {
            this.radialProgress.setProgress(0.0f, false);
            FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
            this.currentMessageObject.loadingCancelled = false;
            this.buttonState = 4;
            this.radialProgress.setIcon(getIconForCurrentState(), true, z);
            invalidate();
            return;
        }
        if (this.isRoundVideo) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject == null || !playingMessageObject.isRoundVideo()) {
                this.photoImage.setAllowStartAnimation(true);
                this.photoImage.startAnimation();
            }
        } else {
            this.photoImage.setAllowStartAnimation(true);
            this.photoImage.startAnimation();
        }
        this.currentMessageObject.gifState = 0.0f;
        this.buttonState = -1;
        this.radialProgress.setIcon(getIconForCurrentState(), false, z);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onFailedDownload(String str, boolean z) {
        int i = this.documentAttachType;
        updateButtonState(true, i == 3 || i == 5, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00bc, code lost:
    
        if ((r1 & 2) != 0) goto L43;
     */
    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onSuccessDownload(java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.onSuccessDownload(java.lang.String):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
    
        if (r8.mediaExists == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003f, code lost:
    
        if (r8 == 6) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0041, code lost:
    
        if (r7 == 1) goto L32;
     */
    @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void didSetImage(org.telegram.messenger.ImageReceiver r5, boolean r6, boolean r7, boolean r8) {
        /*
            r4 = this;
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            if (r0 == 0) goto L4a
            if (r6 == 0) goto L4a
            r1 = 0
            r2 = 1
            if (r8 != 0) goto L10
            boolean r8 = r0.wasUnread
            if (r8 != 0) goto L10
            r8 = 1
            goto L11
        L10:
            r8 = 0
        L11:
            boolean r8 = r4.setCurrentDiceValue(r8)
            if (r8 == 0) goto L18
            return
        L18:
            if (r7 == 0) goto L26
            org.telegram.messenger.MessageObject r8 = r4.currentMessageObject
            int r0 = r8.type
            r3 = 20
            if (r0 != r3) goto L26
            boolean r8 = r8.mediaExists
            if (r8 == 0) goto L43
        L26:
            if (r7 != 0) goto L4a
            org.telegram.messenger.MessageObject r7 = r4.currentMessageObject
            boolean r8 = r7.mediaExists
            if (r8 != 0) goto L4a
            boolean r8 = r7.attachPathExists
            if (r8 != 0) goto L4a
            int r7 = r7.type
            if (r7 != 0) goto L41
            int r8 = r4.documentAttachType
            r0 = 8
            if (r8 == r0) goto L43
            if (r8 == 0) goto L43
            r0 = 6
            if (r8 == r0) goto L43
        L41:
            if (r7 != r2) goto L4a
        L43:
            org.telegram.messenger.MessageObject r7 = r4.currentMessageObject
            r7.mediaExists = r2
            r4.updateButtonState(r1, r2, r1)
        L4a:
            if (r6 == 0) goto L8c
            org.telegram.messenger.MessageObject r6 = r4.currentMessageObject
            if (r6 == 0) goto L8c
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r6 = r6.getBitmap()
            if (r6 == 0) goto L67
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r6 = r6.getBitmap()
            r6.recycle()
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            r7 = 0
            r6.setImageBitmap(r7)
        L67:
            org.telegram.messenger.MessageObject r6 = r4.currentMessageObject
            boolean r6 = r6.hasMediaSpoilers()
            if (r6 == 0) goto L8c
            android.graphics.Bitmap r6 = r5.getBitmap()
            if (r6 == 0) goto L8c
            android.graphics.Bitmap r6 = r5.getBitmap()
            boolean r6 = r6.isRecycled()
            if (r6 != 0) goto L8c
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r5 = r5.getBitmap()
            android.graphics.Bitmap r5 = org.telegram.messenger.Utilities.stackBlurBitmapMax(r5)
            r6.setImageBitmap(r5)
        L8c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.didSetImage(org.telegram.messenger.ImageReceiver, boolean, boolean, boolean):void");
    }

    public boolean setCurrentDiceValue(boolean z) {
        MessagesController.DiceFrameSuccess diceFrameSuccess;
        if (!this.currentMessageObject.isDice()) {
            return false;
        }
        Drawable drawable = this.photoImage.getDrawable();
        if (drawable instanceof RLottieDrawable) {
            RLottieDrawable rLottieDrawable = (RLottieDrawable) drawable;
            String diceEmoji = this.currentMessageObject.getDiceEmoji();
            TLRPC$TL_messages_stickerSet stickerSetByEmojiOrName = MediaDataController.getInstance(this.currentAccount).getStickerSetByEmojiOrName(diceEmoji);
            if (stickerSetByEmojiOrName != null) {
                int diceValue = this.currentMessageObject.getDiceValue();
                if ("🎰".equals(this.currentMessageObject.getDiceEmoji())) {
                    if (diceValue >= 0 && diceValue <= 64) {
                        ((SlotsDrawable) rLottieDrawable).setDiceNumber(this, diceValue, stickerSetByEmojiOrName, z);
                        if (this.currentMessageObject.isOut()) {
                            rLottieDrawable.setOnFinishCallback(this.diceFinishCallback, Integer.MAX_VALUE);
                        }
                        this.currentMessageObject.wasUnread = false;
                    }
                    if (!rLottieDrawable.hasBaseDice() && stickerSetByEmojiOrName.documents.size() > 0) {
                        ((SlotsDrawable) rLottieDrawable).setBaseDice(this, stickerSetByEmojiOrName);
                    }
                } else {
                    if (!rLottieDrawable.hasBaseDice() && stickerSetByEmojiOrName.documents.size() > 0) {
                        TLRPC$Document tLRPC$Document = stickerSetByEmojiOrName.documents.get(0);
                        if (rLottieDrawable.setBaseDice(FileLoader.getInstance(this.currentAccount).getPathToAttach(tLRPC$Document, true))) {
                            DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
                        } else {
                            DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(FileLoader.getAttachFileName(tLRPC$Document), this.currentMessageObject, this);
                            FileLoader.getInstance(this.currentAccount).loadFile(tLRPC$Document, stickerSetByEmojiOrName, 1, 1);
                        }
                    }
                    if (diceValue >= 0 && diceValue < stickerSetByEmojiOrName.documents.size()) {
                        if (!z && this.currentMessageObject.isOut() && (diceFrameSuccess = MessagesController.getInstance(this.currentAccount).diceSuccess.get(diceEmoji)) != null && diceFrameSuccess.num == diceValue) {
                            rLottieDrawable.setOnFinishCallback(this.diceFinishCallback, diceFrameSuccess.frame);
                        }
                        TLRPC$Document tLRPC$Document2 = stickerSetByEmojiOrName.documents.get(Math.max(diceValue, 0));
                        if (rLottieDrawable.setDiceNumber(FileLoader.getInstance(this.currentAccount).getPathToAttach(tLRPC$Document2, true), z)) {
                            DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
                        } else {
                            DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(FileLoader.getAttachFileName(tLRPC$Document2), this.currentMessageObject, this);
                            FileLoader.getInstance(this.currentAccount).loadFile(tLRPC$Document2, stickerSetByEmojiOrName, 1, 1);
                        }
                        this.currentMessageObject.wasUnread = false;
                    }
                }
            } else {
                MediaDataController.getInstance(this.currentAccount).loadStickersByEmojiOrName(diceEmoji, true, true);
            }
        }
        return true;
    }

    @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
    public void onAnimationReady(ImageReceiver imageReceiver) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && imageReceiver == this.photoImage && messageObject.isAnimatedSticker()) {
            this.delegate.setShouldNotRepeatSticker(this.currentMessageObject);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressDownload(String str, long j, long j2) {
        float min = j2 == 0 ? 0.0f : Math.min(1.0f, j / j2);
        this.currentMessageObject.loadedFileSize = j;
        createLoadingProgressLayout(j, j2);
        if (this.drawVideoImageButton) {
            this.videoRadialProgress.setProgress(min, true);
        } else {
            this.radialProgress.setProgress(min, true);
        }
        int i = this.documentAttachType;
        if (i == 3 || i == 5) {
            if (this.hasMiniProgress != 0) {
                if (this.miniButtonState != 1) {
                    updateButtonState(false, false, false);
                    return;
                }
                return;
            } else {
                if (this.buttonState != 4) {
                    updateButtonState(false, false, false);
                    return;
                }
                return;
            }
        }
        if (this.hasMiniProgress != 0) {
            if (this.miniButtonState != 1) {
                updateButtonState(false, false, false);
            }
        } else if (this.buttonState != 1) {
            updateButtonState(false, false, false);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressUpload(String str, long j, long j2, boolean z) {
        int i;
        float min = j2 == 0 ? 0.0f : Math.min(1.0f, j / j2);
        this.currentMessageObject.loadedFileSize = j;
        this.radialProgress.setProgress(min, true);
        if (j == j2 && this.currentPosition != null && SendMessagesHelper.getInstance(this.currentAccount).isSendingMessage(this.currentMessageObject.getId()) && ((i = this.buttonState) == 1 || (i == 4 && this.documentAttachType == 5))) {
            this.drawRadialCheckBackground = true;
            getIconForCurrentState();
            this.radialProgress.setIcon(6, false, true);
        }
        long j3 = this.lastLoadingSizeTotal;
        if (j3 > 0 && Math.abs(j3 - j2) > 1048576) {
            this.lastLoadingSizeTotal = j2;
        }
        createLoadingProgressLayout(j, j2);
    }

    private void createLoadingProgressLayout(TLRPC$Document tLRPC$Document) {
        if (tLRPC$Document == null) {
            return;
        }
        long[] fileProgressSizes = ImageLoader.getInstance().getFileProgressSizes(FileLoader.getDocumentFileName(tLRPC$Document));
        if (fileProgressSizes != null) {
            createLoadingProgressLayout(fileProgressSizes[0], fileProgressSizes[1]);
        } else {
            createLoadingProgressLayout(this.currentMessageObject.loadedFileSize, tLRPC$Document.size);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void createLoadingProgressLayout(long r17, long r19) {
        /*
            Method dump skipped, instructions count: 281
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.createLoadingProgressLayout(long, long):void");
    }

    @Override // android.view.View
    public void onProvideStructure(ViewStructure viewStructure) {
        super.onProvideStructure(viewStructure);
        if (!this.allowAssistant || Build.VERSION.SDK_INT < 23) {
            return;
        }
        CharSequence charSequence = this.currentMessageObject.messageText;
        if (charSequence != null && charSequence.length() > 0) {
            viewStructure.setText(this.currentMessageObject.messageText);
            return;
        }
        CharSequence charSequence2 = this.currentMessageObject.caption;
        if (charSequence2 == null || charSequence2.length() <= 0) {
            return;
        }
        viewStructure.setText(this.currentMessageObject.caption);
    }

    public void setDelegate(ChatMessageCellDelegate chatMessageCellDelegate) {
        this.delegate = chatMessageCellDelegate;
    }

    public ChatMessageCellDelegate getDelegate() {
        return this.delegate;
    }

    public void setAllowAssistant(boolean z) {
        this.allowAssistant = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:114:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x031d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0235  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01fb  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x026d  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0323  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0388  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x039e  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x03d7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x03e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measureTime(org.telegram.messenger.MessageObject r19) {
        /*
            Method dump skipped, instructions count: 1114
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.measureTime(org.telegram.messenger.MessageObject):void");
    }

    private boolean shouldDrawSelectionOverlay() {
        return hasSelectionOverlay() && ((isPressed() && this.isCheckPressed) || ((!this.isCheckPressed && this.isPressed) || this.isHighlighted || this.isHighlightedAnimated)) && !textIsSelectionMode() && ((this.currentMessagesGroup == null || this.drawSelectionBackground) && this.currentBackgroundDrawable != null);
    }

    private int getSelectionOverlayColor() {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider == null) {
            return 0;
        }
        MessageObject messageObject = this.currentMessageObject;
        return resourcesProvider.getColor((messageObject == null || !messageObject.isOut()) ? Theme.key_chat_inBubbleSelectedOverlay : Theme.key_chat_outBubbleSelectedOverlay);
    }

    private boolean hasSelectionOverlay() {
        int selectionOverlayColor = getSelectionOverlayColor();
        return (selectionOverlayColor == 0 || selectionOverlayColor == -65536) ? false : true;
    }

    private boolean isDrawSelectionBackground() {
        return (((!isPressed() || !this.isCheckPressed) && ((this.isCheckPressed || !this.isPressed) && !this.isHighlighted)) || textIsSelectionMode() || hasSelectionOverlay()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOpenChatByShare(MessageObject messageObject) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader = messageObject.messageOwner.fwd_from;
        return (tLRPC$MessageFwdHeader == null || tLRPC$MessageFwdHeader.saved_from_peer == null || ((chatMessageCellDelegate = this.delegate) != null && !chatMessageCellDelegate.isReplyOrSelf())) ? false : true;
    }

    public boolean isInsideBackground(float f, float f2) {
        if (this.currentBackgroundDrawable != null) {
            if (f >= this.backgroundDrawableLeft && f <= r3 + this.backgroundDrawableRight) {
                return true;
            }
        }
        return false;
    }

    private void updateCurrentUserAndChat() {
        TLRPC$Peer tLRPC$Peer;
        MessageObject messageObject;
        if (this.currentMessageObject == null) {
            return;
        }
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        TLRPC$MessageFwdHeader tLRPC$MessageFwdHeader = this.currentMessageObject.messageOwner.fwd_from;
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        if (tLRPC$MessageFwdHeader != null && (tLRPC$MessageFwdHeader.from_id instanceof TLRPC$TL_peerChannel) && this.currentMessageObject.getDialogId() == clientUserId) {
            this.currentChat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(tLRPC$MessageFwdHeader.from_id.channel_id));
        } else if (tLRPC$MessageFwdHeader != null && (tLRPC$Peer = tLRPC$MessageFwdHeader.saved_from_peer) != null) {
            long j = tLRPC$Peer.user_id;
            if (j != 0) {
                TLRPC$Peer tLRPC$Peer2 = tLRPC$MessageFwdHeader.from_id;
                if (tLRPC$Peer2 instanceof TLRPC$TL_peerUser) {
                    this.currentUser = messagesController.getUser(Long.valueOf(tLRPC$Peer2.user_id));
                } else {
                    this.currentUser = messagesController.getUser(Long.valueOf(j));
                }
            } else if (tLRPC$Peer.channel_id != 0) {
                if (this.currentMessageObject.isSavedFromMegagroup()) {
                    TLRPC$Peer tLRPC$Peer3 = tLRPC$MessageFwdHeader.from_id;
                    if (tLRPC$Peer3 instanceof TLRPC$TL_peerUser) {
                        this.currentUser = messagesController.getUser(Long.valueOf(tLRPC$Peer3.user_id));
                    }
                }
                this.currentChat = messagesController.getChat(Long.valueOf(tLRPC$MessageFwdHeader.saved_from_peer.channel_id));
            } else {
                long j2 = tLRPC$Peer.chat_id;
                if (j2 != 0) {
                    TLRPC$Peer tLRPC$Peer4 = tLRPC$MessageFwdHeader.from_id;
                    if (tLRPC$Peer4 instanceof TLRPC$TL_peerUser) {
                        this.currentUser = messagesController.getUser(Long.valueOf(tLRPC$Peer4.user_id));
                    } else {
                        this.currentChat = messagesController.getChat(Long.valueOf(j2));
                    }
                }
            }
        } else if (tLRPC$MessageFwdHeader != null && (tLRPC$MessageFwdHeader.from_id instanceof TLRPC$TL_peerUser) && (tLRPC$MessageFwdHeader.imported || this.currentMessageObject.getDialogId() == clientUserId)) {
            this.currentUser = messagesController.getUser(Long.valueOf(tLRPC$MessageFwdHeader.from_id.user_id));
        } else if (tLRPC$MessageFwdHeader != null && !TextUtils.isEmpty(tLRPC$MessageFwdHeader.from_name) && (tLRPC$MessageFwdHeader.imported || this.currentMessageObject.getDialogId() == clientUserId)) {
            TLRPC$TL_user tLRPC$TL_user = new TLRPC$TL_user();
            this.currentUser = tLRPC$TL_user;
            tLRPC$TL_user.first_name = tLRPC$MessageFwdHeader.from_name;
        } else {
            long fromChatId = this.currentMessageObject.getFromChatId();
            if (DialogObject.isUserDialog(fromChatId) && !this.currentMessageObject.messageOwner.post) {
                this.currentUser = messagesController.getUser(Long.valueOf(fromChatId));
            } else if (DialogObject.isChatDialog(fromChatId)) {
                this.currentChat = messagesController.getChat(Long.valueOf(-fromChatId));
            } else {
                TLRPC$Message tLRPC$Message = this.currentMessageObject.messageOwner;
                if (tLRPC$Message.post) {
                    this.currentChat = messagesController.getChat(Long.valueOf(tLRPC$Message.peer_id.channel_id));
                }
            }
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null && messageObject2.getChatId() != 0) {
            MessageObject messageObject3 = this.currentMessageObject;
            if (messageObject3.messageOwner != null && (messageObject = messageObject3.replyMessageObject) != null && messageObject.isFromUser()) {
                this.currentReplyUserId = this.currentMessageObject.replyMessageObject.messageOwner.from_id.user_id;
                return;
            }
        }
        this.currentReplyUserId = 0L;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(27:21|(1:23)|24|(2:513|(25:520|(4:533|(1:535)(1:542)|536|(3:538|(1:540)|541))|524|34|(1:36)(1:(1:511)(1:512))|37|(1:39)(1:509)|40|(7:42|(1:44)|45|(1:47)(3:53|(1:55)(1:57)|56)|48|(1:50)(1:52)|51)|58|59|60|61|62|(3:64|(1:66)|67)(1:504)|68|(1:70)|(1:72)(1:503)|73|(2:75|(1:77))|78|(1:80)(2:497|(1:499)(2:500|(1:502)))|81|(1:83)|84)(1:519))(1:32)|33|34|(0)(0)|37|(0)(0)|40|(0)|58|59|60|61|62|(0)(0)|68|(0)|(0)(0)|73|(0)|78|(0)(0)|81|(0)|84) */
    /* JADX WARN: Code restructure failed: missing block: B:478:0x07e5, code lost:
    
        if ((r0.action instanceof org.telegram.tgnet.TLRPC$TL_messageActionTopicCreate) == false) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:505:0x03bc, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:506:0x03bd, code lost:
    
        org.telegram.messenger.FileLog.e(r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x03b8 A[Catch: Exception -> 0x03bc, TRY_LEAVE, TryCatch #4 {Exception -> 0x03bc, blocks: (B:62:0x031b, B:64:0x033e, B:66:0x0355, B:67:0x036a, B:68:0x0379, B:70:0x037d, B:72:0x038a, B:503:0x03b8, B:504:0x0374), top: B:61:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:504:0x0374 A[Catch: Exception -> 0x03bc, TryCatch #4 {Exception -> 0x03bc, blocks: (B:62:0x031b, B:64:0x033e, B:66:0x0355, B:67:0x036a, B:68:0x0379, B:70:0x037d, B:72:0x038a, B:503:0x03b8, B:504:0x0374), top: B:61:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x033e A[Catch: Exception -> 0x03bc, TryCatch #4 {Exception -> 0x03bc, blocks: (B:62:0x031b, B:64:0x033e, B:66:0x0355, B:67:0x036a, B:68:0x0379, B:70:0x037d, B:72:0x038a, B:503:0x03b8, B:504:0x0374), top: B:61:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x037d A[Catch: Exception -> 0x03bc, TryCatch #4 {Exception -> 0x03bc, blocks: (B:62:0x031b, B:64:0x033e, B:66:0x0355, B:67:0x036a, B:68:0x0379, B:70:0x037d, B:72:0x038a, B:503:0x03b8, B:504:0x0374), top: B:61:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x038a A[Catch: Exception -> 0x03bc, TryCatch #4 {Exception -> 0x03bc, blocks: (B:62:0x031b, B:64:0x033e, B:66:0x0355, B:67:0x036a, B:68:0x0379, B:70:0x037d, B:72:0x038a, B:503:0x03b8, B:504:0x0374), top: B:61:0x031b }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x03c4  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x040a  */
    /* JADX WARN: Type inference failed for: r4v108 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.lang.String, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$User] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setMessageObjectInternal(org.telegram.messenger.MessageObject r43) {
        /*
            Method dump skipped, instructions count: 3798
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.setMessageObjectInternal(org.telegram.messenger.MessageObject):void");
    }

    private boolean isNeedAuthorName() {
        return (this.isPinnedChat && this.currentMessageObject.type == 0) || (!this.pinnedTop && this.drawName && this.isChat && (!this.currentMessageObject.isOutOwner() || (this.currentMessageObject.isSupergroup() && this.currentMessageObject.isFromGroup()))) || (this.currentMessageObject.isImportedForward() && this.currentMessageObject.messageOwner.fwd_from.from_id == null);
    }

    private String getAuthorName() {
        TLRPC$Chat tLRPC$Chat;
        String str;
        String str2;
        TLRPC$User tLRPC$User = this.currentUser;
        if (tLRPC$User != null) {
            return UserObject.getUserName(tLRPC$User);
        }
        TLRPC$Chat tLRPC$Chat2 = this.currentChat;
        if (tLRPC$Chat2 != null) {
            return tLRPC$Chat2.title;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.isSponsored()) {
            return "DELETED";
        }
        TLRPC$ChatInvite tLRPC$ChatInvite = this.currentMessageObject.sponsoredChatInvite;
        return (tLRPC$ChatInvite == null || (str2 = tLRPC$ChatInvite.title) == null) ? (tLRPC$ChatInvite == null || (tLRPC$Chat = tLRPC$ChatInvite.chat) == null || (str = tLRPC$Chat.title) == null) ? "" : str : str2;
    }

    private Object getAuthorStatus() {
        TLRPC$User tLRPC$User = this.currentUser;
        if (tLRPC$User == null) {
            return null;
        }
        Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(tLRPC$User);
        if (emojiStatusDocumentId != null) {
            return emojiStatusDocumentId;
        }
        if (this.currentUser.premium) {
            return ContextCompat.getDrawable(ApplicationLoader.applicationContext, org.telegram.messenger.R.drawable.msg_premium_liststar).mutate();
        }
        return null;
    }

    private String getForwardedMessageText(MessageObject messageObject) {
        if (this.hasPsaHint) {
            String string = LocaleController.getString("PsaMessage_" + messageObject.messageOwner.fwd_from.psa_type);
            return string == null ? LocaleController.getString("PsaMessageDefault", org.telegram.messenger.R.string.PsaMessageDefault) : string;
        }
        return LocaleController.getString("ForwardedMessage", org.telegram.messenger.R.string.ForwardedMessage);
    }

    public int getExtraInsetHeight() {
        int i = this.addedCaptionHeight;
        if (this.drawCommentButton) {
            i += AndroidUtilities.dp(shouldDrawTimeOnMedia() ? 41.3f : 43.0f);
        }
        return (this.reactionsLayoutInBubble.isEmpty || !this.currentMessageObject.shouldDrawReactionsInLayout()) ? i : i + this.reactionsLayoutInBubble.totalHeight;
    }

    public ImageReceiver getAvatarImage() {
        if (this.isAvatarVisible) {
            return this.avatarImage;
        }
        return null;
    }

    public float getCheckBoxTranslation() {
        return this.checkBoxTranslation;
    }

    public boolean shouldDrawAlphaLayer() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        return (groupedMessages == null || !groupedMessages.transitionParams.backgroundChangeBounds) && getAlpha() != 1.0f;
    }

    public float getCaptionX() {
        return this.captionX;
    }

    public boolean isDrawPinnedBottom() {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        return this.mediaBackground || this.drawPinnedBottom || (groupedMessagePosition != null && (groupedMessagePosition.flags & 8) == 0 && this.currentMessagesGroup.isDocuments);
    }

    public void drawCheckBox(Canvas canvas) {
        float f;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.isSending() || this.currentMessageObject.isSendError() || this.checkBox == null) {
            return;
        }
        if (this.checkBoxVisible || this.checkBoxAnimationInProgress) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition != null) {
                int i = groupedMessagePosition.flags;
                if ((i & 8) == 0 || (i & 1) == 0) {
                    return;
                }
            }
            canvas.save();
            float y = getY();
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null && groupedMessages.messages.size() > 1) {
                f = (getTop() + this.currentMessagesGroup.transitionParams.offsetTop) - getTranslationY();
            } else {
                f = y + this.transitionParams.deltaTop;
            }
            canvas.translate(0.0f, f + this.transitionYOffsetForDrawables);
            this.checkBox.draw(canvas);
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setBackgroundTopY(boolean r15) {
        /*
            r14 = this;
            r0 = 0
            r1 = 0
        L2:
            r2 = 2
            if (r1 >= r2) goto L85
            r2 = 1
            if (r1 != r2) goto Lb
            if (r15 != 0) goto Lb
            return
        Lb:
            if (r1 != 0) goto L10
            org.telegram.ui.ActionBar.Theme$MessageDrawable r3 = r14.currentBackgroundDrawable
            goto L12
        L10:
            org.telegram.ui.ActionBar.Theme$MessageDrawable r3 = r14.currentBackgroundSelectedDrawable
        L12:
            if (r3 != 0) goto L16
            goto L81
        L16:
            int r4 = r14.parentWidth
            int r5 = r14.parentHeight
            if (r5 != 0) goto L3d
            int r4 = r14.getParentWidth()
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r5 = r5.y
            android.view.ViewParent r6 = r14.getParent()
            boolean r6 = r6 instanceof android.view.View
            if (r6 == 0) goto L3d
            android.view.ViewParent r4 = r14.getParent()
            android.view.View r4 = (android.view.View) r4
            int r5 = r4.getMeasuredWidth()
            int r4 = r4.getMeasuredHeight()
            r7 = r4
            r6 = r5
            goto L3f
        L3d:
            r6 = r4
            r7 = r5
        L3f:
            if (r15 == 0) goto L46
            float r4 = r14.getY()
            goto L4b
        L46:
            int r4 = r14.getTop()
            float r4 = (float) r4
        L4b:
            float r5 = r14.parentViewTopOffset
            float r4 = r4 + r5
            int r8 = (int) r4
            int r9 = (int) r5
            int r10 = r14.blurredViewTopOffset
            int r11 = r14.blurredViewBottomOffset
            boolean r12 = r14.pinnedTop
            boolean r4 = r14.pinnedBottom
            if (r4 != 0) goto L67
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r4 = r14.transitionParams
            float r4 = r4.changePinnedBottomProgress
            r5 = 1065353216(0x3f800000, float:1.0)
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 == 0) goto L65
            goto L67
        L65:
            r13 = 0
            goto L68
        L67:
            r13 = 1
        L68:
            r4 = r3
            r5 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            r4.setTop(r5, r6, r7, r8, r9, r10, r11, r12)
            org.telegram.messenger.MessageObject r4 = r14.currentMessageObject
            if (r4 == 0) goto L7d
            boolean r4 = r4.hasInlineBotButtons()
            if (r4 == 0) goto L7d
            goto L7e
        L7d:
            r2 = 0
        L7e:
            r3.setBotButtonsBottom(r2)
        L81:
            int r1 = r1 + 1
            goto L2
        L85:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.setBackgroundTopY(boolean):void");
    }

    public void setBackgroundTopY(int i) {
        int i2;
        int i3;
        boolean z;
        MessageObject messageObject;
        Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
        int i4 = this.parentWidth;
        int i5 = this.parentHeight;
        if (i5 == 0) {
            i4 = getParentWidth();
            i5 = AndroidUtilities.displaySize.y;
            if (getParent() instanceof View) {
                View view = (View) getParent();
                int measuredWidth = view.getMeasuredWidth();
                i3 = view.getMeasuredHeight();
                i2 = measuredWidth;
                float f = this.parentViewTopOffset;
                z = false;
                messageDrawable.setTop((int) (i + f), i2, i3, (int) f, this.blurredViewTopOffset, this.blurredViewBottomOffset, this.pinnedTop, (this.pinnedBottom && this.transitionParams.changePinnedBottomProgress == 1.0f) ? false : true);
                messageObject = this.currentMessageObject;
                if (messageObject != null && messageObject.hasInlineBotButtons()) {
                    z = true;
                }
                messageDrawable.setBotButtonsBottom(z);
            }
        }
        i2 = i4;
        i3 = i5;
        float f2 = this.parentViewTopOffset;
        z = false;
        messageDrawable.setTop((int) (i + f2), i2, i3, (int) f2, this.blurredViewTopOffset, this.blurredViewBottomOffset, this.pinnedTop, (this.pinnedBottom && this.transitionParams.changePinnedBottomProgress == 1.0f) ? false : true);
        messageObject = this.currentMessageObject;
        if (messageObject != null) {
            z = true;
        }
        messageDrawable.setBotButtonsBottom(z);
    }

    public void setDrawableBoundsInner(Drawable drawable, int i, int i2, int i3, int i4) {
        if (drawable != null) {
            float f = i4 + i2;
            TransitionParams transitionParams = this.transitionParams;
            float f2 = transitionParams.deltaBottom;
            this.transitionYOffsetForDrawables = (f + f2) - ((int) (f + f2));
            drawable.setBounds((int) (i + transitionParams.deltaLeft), (int) (i2 + transitionParams.deltaTop), (int) (i + i3 + transitionParams.deltaRight), (int) (f + f2));
        }
    }

    @Override // android.view.View
    @SuppressLint({"WrongCall"})
    protected void onDraw(Canvas canvas) {
        int i;
        boolean z;
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        MessageTopicButton messageTopicButton;
        MessageTopicButton messageTopicButton2;
        Theme.MessageDrawable messageDrawable;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (this.currentMessageObject == null) {
            return;
        }
        if (!this.wasLayout) {
            onLayout(false, getLeft(), getTop(), getRight(), getBottom());
        }
        if (this.enterTransitionInProgress && this.currentMessageObject.isAnimatedEmojiStickers()) {
            return;
        }
        if (this.currentMessageObject.isOutOwner()) {
            TextPaint textPaint = Theme.chat_msgTextPaint;
            int i7 = Theme.key_chat_messageTextOut;
            textPaint.setColor(getThemedColor(i7));
            Theme.chat_msgGameTextPaint.setColor(getThemedColor(i7));
            TextPaint textPaint2 = Theme.chat_msgGameTextPaint;
            int i8 = Theme.key_chat_messageLinkOut;
            textPaint2.linkColor = getThemedColor(i8);
            Theme.chat_replyTextPaint.linkColor = getThemedColor(i8);
            Theme.chat_msgTextPaint.linkColor = getThemedColor(i8);
        } else {
            TextPaint textPaint3 = Theme.chat_msgTextPaint;
            int i9 = Theme.key_chat_messageTextIn;
            textPaint3.setColor(getThemedColor(i9));
            Theme.chat_msgGameTextPaint.setColor(getThemedColor(i9));
            TextPaint textPaint4 = Theme.chat_msgGameTextPaint;
            int i10 = Theme.key_chat_messageLinkIn;
            textPaint4.linkColor = getThemedColor(i10);
            Theme.chat_replyTextPaint.linkColor = getThemedColor(i10);
            Theme.chat_msgTextPaint.linkColor = getThemedColor(i10);
        }
        if (this.documentAttach != null) {
            int i11 = this.documentAttachType;
            if (i11 == 3 || i11 == 7) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.seekBarWaveform.setColors(getThemedColor(Theme.key_chat_outVoiceSeekbar), getThemedColor(Theme.key_chat_outVoiceSeekbarFill), getThemedColor(Theme.key_chat_outVoiceSeekbarSelected));
                    SeekBar seekBar = this.seekBar;
                    int themedColor = getThemedColor(Theme.key_chat_outAudioSeekbar);
                    int themedColor2 = getThemedColor(Theme.key_chat_outAudioCacheSeekbar);
                    int i12 = Theme.key_chat_outAudioSeekbarFill;
                    seekBar.setColors(themedColor, themedColor2, getThemedColor(i12), getThemedColor(i12), getThemedColor(Theme.key_chat_outAudioSeekbarSelected));
                } else {
                    this.seekBarWaveform.setColors(getThemedColor(Theme.key_chat_inVoiceSeekbar), getThemedColor(Theme.key_chat_inVoiceSeekbarFill), getThemedColor(Theme.key_chat_inVoiceSeekbarSelected));
                    SeekBar seekBar2 = this.seekBar;
                    int themedColor3 = getThemedColor(Theme.key_chat_inAudioSeekbar);
                    int themedColor4 = getThemedColor(Theme.key_chat_inAudioCacheSeekbar);
                    int i13 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar2.setColors(themedColor3, themedColor4, getThemedColor(i13), getThemedColor(i13), getThemedColor(Theme.key_chat_inAudioSeekbarSelected));
                }
            } else if (i11 == 5) {
                if (this.currentMessageObject.isOutOwner()) {
                    SeekBar seekBar3 = this.seekBar;
                    int themedColor5 = getThemedColor(Theme.key_chat_outAudioSeekbar);
                    int themedColor6 = getThemedColor(Theme.key_chat_outAudioCacheSeekbar);
                    int i14 = Theme.key_chat_outAudioSeekbarFill;
                    seekBar3.setColors(themedColor5, themedColor6, getThemedColor(i14), getThemedColor(i14), getThemedColor(Theme.key_chat_outAudioSeekbarSelected));
                } else {
                    SeekBar seekBar4 = this.seekBar;
                    int themedColor7 = getThemedColor(Theme.key_chat_inAudioSeekbar);
                    int themedColor8 = getThemedColor(Theme.key_chat_inAudioCacheSeekbar);
                    int i15 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar4.setColors(themedColor7, themedColor8, getThemedColor(i15), getThemedColor(i15), getThemedColor(Theme.key_chat_inAudioSeekbarSelected));
                }
            }
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.type == 5) {
            TextPaint textPaint5 = Theme.chat_timePaint;
            int themedColor9 = getThemedColor(Theme.key_chat_serviceText);
            if (isDrawSelectionBackground()) {
                i6 = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_inTimeSelectedText;
            } else {
                i6 = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeText : Theme.key_chat_inTimeText;
            }
            textPaint5.setColor(ColorUtils.blendARGB(themedColor9, getThemedColor(i6), getVideoTranscriptionProgress()));
        } else if (this.mediaBackground) {
            if (messageObject.shouldDrawWithoutBackground()) {
                Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_serviceText));
            } else {
                Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_mediaTimeText));
            }
        } else if (messageObject.isOutOwner()) {
            Theme.chat_timePaint.setColor(getThemedColor(isDrawSelectionBackground() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
        } else {
            Theme.chat_timePaint.setColor(getThemedColor(isDrawSelectionBackground() ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
        }
        drawBackgroundInternal(canvas, false);
        if (this.isHighlightedAnimated) {
            long currentTimeMillis = System.currentTimeMillis();
            long abs = Math.abs(currentTimeMillis - this.lastHighlightProgressTime);
            if (abs > 17) {
                abs = 17;
            }
            int i16 = (int) (this.highlightProgress - abs);
            this.highlightProgress = i16;
            this.lastHighlightProgressTime = currentTimeMillis;
            if (i16 <= 0) {
                this.highlightProgress = 0;
                this.isHighlightedAnimated = false;
            }
            invalidate();
            if (getParent() != null) {
                ((View) getParent()).invalidate();
            }
        }
        if (this.alphaInternal != 1.0f) {
            int measuredHeight = getMeasuredHeight();
            int measuredWidth = getMeasuredWidth();
            Theme.MessageDrawable messageDrawable2 = this.currentBackgroundDrawable;
            if (messageDrawable2 != null) {
                i5 = messageDrawable2.getBounds().top;
                i4 = this.currentBackgroundDrawable.getBounds().bottom;
                i3 = this.currentBackgroundDrawable.getBounds().left;
                i2 = this.currentBackgroundDrawable.getBounds().right;
            } else {
                i2 = measuredWidth;
                i3 = 0;
                i4 = measuredHeight;
                i5 = 0;
            }
            if (this.drawSideButton != 0) {
                if (this.currentMessageObject.isOutOwner()) {
                    i3 -= AndroidUtilities.dp(40.0f);
                } else {
                    i2 += AndroidUtilities.dp(40.0f);
                }
            }
            if (getY() < 0.0f) {
                i5 = (int) (-getY());
            }
            float y = getY() + getMeasuredHeight();
            int i17 = this.parentHeight;
            if (y > i17) {
                i4 = (int) (i17 - getY());
            }
            this.rect.set(i3, i5, i2, i4);
            i = canvas.saveLayerAlpha(this.rect, (int) (this.alphaInternal * 255.0f), 31);
        } else {
            i = LinearLayoutManager.INVALID_OFFSET;
        }
        if (!this.transitionParams.animateBackgroundBoundsInner || (messageDrawable = this.currentBackgroundDrawable) == null || this.isRoundVideo) {
            z = false;
        } else {
            Rect bounds = messageDrawable.getBounds();
            canvas.save();
            canvas.clipRect(bounds.left + AndroidUtilities.dp(4.0f), bounds.top + AndroidUtilities.dp(4.0f), bounds.right - AndroidUtilities.dp(4.0f), bounds.bottom - AndroidUtilities.dp(4.0f));
            z = true;
        }
        drawContent(canvas);
        if (z) {
            canvas.restore();
        }
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate == null || chatMessageCellDelegate.canDrawOutboundsContent() || this.transitionParams.messageEntering || getAlpha() != 1.0f) {
            drawOutboundsContent(canvas);
        }
        if (this.replyNameLayout != null) {
            this.replyHeight = AndroidUtilities.dp(7.0f) + Theme.chat_replyNamePaint.getTextSize() + Theme.chat_replyTextPaint.getTextSize();
            if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                MessageObject messageObject2 = this.currentMessageObject;
                if (messageObject2.type != 19) {
                    if (messageObject2.isOutOwner()) {
                        int dp = AndroidUtilities.dp(23.0f);
                        this.replyStartX = dp;
                        if (this.isPlayingRound) {
                            this.replyStartX = dp - (AndroidUtilities.roundPlayingMessageSize - AndroidUtilities.roundMessageSize);
                        }
                    } else if (this.currentMessageObject.type == 5) {
                        this.replyStartX = this.backgroundDrawableLeft + this.backgroundDrawableRight + AndroidUtilities.dp(4.0f);
                    } else {
                        this.replyStartX = this.backgroundDrawableLeft + this.backgroundDrawableRight + AndroidUtilities.dp(17.0f);
                    }
                    if (this.drawForwardedName) {
                        int dp2 = AndroidUtilities.dp(4.0f) + (((int) Theme.chat_forwardNamePaint.getTextSize()) * 2);
                        this.forwardHeight = dp2;
                        this.replyStartY = this.forwardNameY + dp2 + AndroidUtilities.dp(6.0f);
                    } else {
                        int dp3 = AndroidUtilities.dp(12.0f);
                        this.replyStartY = dp3;
                        if (this.drawTopic && (messageTopicButton2 = this.topicButton) != null) {
                            this.replyStartY = dp3 + messageTopicButton2.height() + AndroidUtilities.dp(10.0f);
                        }
                    }
                }
            }
            if (this.currentMessageObject.isOutOwner()) {
                int dp4 = this.backgroundDrawableLeft + AndroidUtilities.dp(12.0f) + getExtraTextX();
                this.replyStartX = dp4;
                if (this.currentMessageObject.type == 19) {
                    this.replyStartX = dp4 - Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + dp4) + AndroidUtilities.dp(14.0f)) - AndroidUtilities.displaySize.x);
                }
            } else if (this.mediaBackground) {
                this.replyStartX = this.backgroundDrawableLeft + AndroidUtilities.dp(12.0f) + getExtraTextX();
            } else {
                this.replyStartX = this.backgroundDrawableLeft + AndroidUtilities.dp(this.drawPinnedBottom ? 12.0f : 18.0f) + getExtraTextX();
            }
            if (this.currentMessageObject.type == 19) {
                this.replyStartX -= AndroidUtilities.dp(7.0f);
            }
            this.forwardHeight = AndroidUtilities.dp(4.0f) + (((int) Theme.chat_forwardNamePaint.getTextSize()) * 2);
            int dp5 = AndroidUtilities.dp(12.0f) + ((!this.drawNameLayout || this.nameLayout == null) ? 0 : AndroidUtilities.dp(6.0f) + ((int) Theme.chat_namePaint.getTextSize())) + ((!this.drawForwardedName || this.forwardedNameLayout[0] == null) ? 0 : AndroidUtilities.dp(4.0f) + this.forwardHeight);
            this.replyStartY = dp5;
            if (this.drawTopic && (messageTopicButton = this.topicButton) != null) {
                this.replyStartY = dp5 + messageTopicButton.height() + AndroidUtilities.dp(5.0f);
            }
        }
        if (this.currentPosition == null && !this.transitionParams.animateBackgroundBoundsInner && (!this.enterTransitionInProgress || this.currentMessageObject.isVoice())) {
            drawNamesLayout(canvas, 1.0f);
        }
        if ((!this.autoPlayingMedia || !MediaController.getInstance().isPlayingMessageAndReadyToDraw(this.currentMessageObject) || this.isRoundVideo) && !this.transitionParams.animateBackgroundBoundsInner) {
            drawOverlays(canvas);
        }
        if ((this.drawTime || !this.mediaBackground) && !this.forceNotDrawTime && !this.transitionParams.animateBackgroundBoundsInner && (!this.enterTransitionInProgress || this.currentMessageObject.isVoice())) {
            drawTime(canvas, 1.0f, false);
        }
        if ((this.controlsAlpha != 1.0f || this.timeAlpha != 1.0f) && this.currentMessageObject.type != 5) {
            long currentTimeMillis2 = System.currentTimeMillis();
            long abs2 = Math.abs(this.lastControlsAlphaChangeTime - currentTimeMillis2);
            long j = this.totalChangeTime + (abs2 <= 17 ? abs2 : 17L);
            this.totalChangeTime = j;
            if (j > 200) {
                this.totalChangeTime = 200L;
            }
            this.lastControlsAlphaChangeTime = currentTimeMillis2;
            if (this.controlsAlpha != 1.0f) {
                this.controlsAlpha = AndroidUtilities.decelerateInterpolator.getInterpolation(this.totalChangeTime / 200.0f);
            }
            if (this.timeAlpha != 1.0f) {
                this.timeAlpha = AndroidUtilities.decelerateInterpolator.getInterpolation(this.totalChangeTime / 200.0f);
            }
            invalidate();
            if (this.forceNotDrawTime && (groupedMessagePosition = this.currentPosition) != null && groupedMessagePosition.last && getParent() != null) {
                ((View) getParent()).invalidate();
            }
        }
        if ((this.drawBackground || this.transitionParams.animateDrawBackground) && shouldDrawSelectionOverlay() && this.currentMessagesGroup == null && hasSelectionOverlay()) {
            if (this.selectionOverlayPaint == null) {
                this.selectionOverlayPaint = new Paint(1);
            }
            this.selectionOverlayPaint.setColor(getSelectionOverlayColor());
            int alpha = this.selectionOverlayPaint.getAlpha();
            this.selectionOverlayPaint.setAlpha((int) (alpha * getHighlightAlpha() * getAlpha()));
            if (this.selectionOverlayPaint.getAlpha() > 0) {
                canvas.save();
                canvas.clipRect(0, 0, getMeasuredWidth(), getMeasuredHeight());
                this.currentBackgroundDrawable.drawCached(canvas, this.backgroundCacheParams, this.selectionOverlayPaint);
                canvas.restore();
            }
            this.selectionOverlayPaint.setAlpha(alpha);
        }
        if (i != Integer.MIN_VALUE) {
            canvas.restoreToCount(i);
        }
        updateSelectionTextPosition();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint({"WrongCall"})
    public void drawBackgroundInternal(Canvas canvas, boolean z) {
        Drawable shadowDrawable;
        Drawable drawable;
        int i;
        int i2;
        int dp;
        int i3;
        int i4;
        Canvas canvas2;
        String str;
        String str2;
        String str3;
        boolean z2;
        float f;
        MessageObject.GroupedMessages groupedMessages;
        Theme.MessageDrawable messageDrawable;
        int i5;
        int i6;
        MessageObject messageObject;
        int i7;
        int dp2;
        Drawable shadowDrawable2;
        int i8;
        int i9;
        int i10;
        int dp3;
        if (this.currentMessageObject == null) {
            return;
        }
        boolean z3 = this.wasLayout;
        if (!z3 && !this.animationRunning) {
            forceLayout();
            return;
        }
        if (!z3) {
            onLayout(false, getLeft(), getTop(), getRight(), getBottom());
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        boolean z4 = groupedMessagePosition != null && (groupedMessagePosition.flags & 8) == 0 && this.currentMessagesGroup.isDocuments && !this.drawPinnedBottom;
        if (this.currentMessageObject.isOutOwner()) {
            if (this.transitionParams.changePinnedBottomProgress >= 1.0f && !this.mediaBackground && !this.drawPinnedBottom && !z4) {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOut");
                this.currentBackgroundSelectedDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOutSelected");
                this.transitionParams.drawPinnedBottomBackground = false;
            } else {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOutMedia");
                this.currentBackgroundSelectedDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOutMediaSelected");
                this.transitionParams.drawPinnedBottomBackground = true;
            }
            setBackgroundTopY(true);
            if (isDrawSelectionBackground() && (this.currentPosition == null || getBackground() != null)) {
                shadowDrawable2 = this.currentBackgroundSelectedDrawable.getShadowDrawable();
            } else {
                shadowDrawable2 = this.currentBackgroundDrawable.getShadowDrawable();
            }
            drawable = shadowDrawable2;
            this.backgroundDrawableLeft = (this.layoutWidth - this.backgroundWidth) - (!this.mediaBackground ? 0 : AndroidUtilities.dp(9.0f));
            int dp4 = this.backgroundWidth - (this.mediaBackground ? 0 : AndroidUtilities.dp(3.0f));
            this.backgroundDrawableRight = dp4;
            MessageObject.GroupedMessages groupedMessages2 = this.currentMessagesGroup;
            if (groupedMessages2 != null && !groupedMessages2.isDocuments && !this.currentPosition.edge) {
                this.backgroundDrawableRight = dp4 + AndroidUtilities.dp(10.0f);
            }
            int i11 = this.backgroundDrawableLeft;
            if (!z4 && this.transitionParams.changePinnedBottomProgress != 1.0f) {
                if (!this.mediaBackground) {
                    this.backgroundDrawableRight -= AndroidUtilities.dp(6.0f);
                }
            } else if (!this.mediaBackground && this.drawPinnedBottom) {
                this.backgroundDrawableRight -= AndroidUtilities.dp(6.0f);
            }
            MessageObject.GroupedMessagePosition groupedMessagePosition2 = this.currentPosition;
            if (groupedMessagePosition2 != null) {
                if ((groupedMessagePosition2.flags & 2) == 0) {
                    this.backgroundDrawableRight += AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                }
                if ((this.currentPosition.flags & 1) == 0) {
                    i11 -= AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                    this.backgroundDrawableRight += AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                }
                if ((this.currentPosition.flags & 4) == 0) {
                    i9 = 0 - AndroidUtilities.dp(SharedConfig.bubbleRadius + 3);
                    i10 = AndroidUtilities.dp(SharedConfig.bubbleRadius + 3) + 0;
                } else {
                    i9 = 0;
                    i10 = 0;
                }
                if ((this.currentPosition.flags & 8) == 0) {
                    i10 += AndroidUtilities.dp(SharedConfig.bubbleRadius + 3);
                }
                i8 = i11;
            } else {
                i8 = i11;
                i9 = 0;
                i10 = 0;
            }
            boolean z5 = this.drawPinnedBottom;
            if (z5 && this.drawPinnedTop) {
                dp3 = 0;
            } else if (z5) {
                dp3 = AndroidUtilities.dp(1.0f);
            } else {
                dp3 = AndroidUtilities.dp(2.0f);
            }
            int dp5 = (this.drawPinnedTop ? 0 : AndroidUtilities.dp(1.0f)) + i9;
            this.backgroundDrawableTop = dp5;
            int i12 = (this.layoutHeight - dp3) + i10;
            this.backgroundDrawableBottom = dp5 + i12;
            if (!this.mediaBackground) {
                if (this.drawPinnedTop) {
                    this.backgroundDrawableTop = dp5 - AndroidUtilities.dp(1.0f);
                    i12 += AndroidUtilities.dp(1.0f);
                }
                if (this.drawPinnedBottom) {
                    this.backgroundDrawableBottom += AndroidUtilities.dp(1.0f);
                    i12 += AndroidUtilities.dp(1.0f);
                }
            }
            int i13 = i12;
            if (z4) {
                setDrawableBoundsInner(this.currentBackgroundDrawable, i8, this.backgroundDrawableTop - i9, this.backgroundDrawableRight, (i13 - i10) + 10);
                setDrawableBoundsInner(this.currentBackgroundSelectedDrawable, this.backgroundDrawableLeft, this.backgroundDrawableTop, this.backgroundDrawableRight - AndroidUtilities.dp(6.0f), i13);
            } else {
                int i14 = i8;
                setDrawableBoundsInner(this.currentBackgroundDrawable, i14, this.backgroundDrawableTop, this.backgroundDrawableRight, i13);
                setDrawableBoundsInner(this.currentBackgroundSelectedDrawable, i14, this.backgroundDrawableTop, this.backgroundDrawableRight, i13);
            }
            setDrawableBoundsInner(drawable, i8, this.backgroundDrawableTop, this.backgroundDrawableRight, i13);
        } else {
            if (this.transitionParams.changePinnedBottomProgress >= 1.0f && !this.mediaBackground && !this.drawPinnedBottom && !z4) {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgIn");
                this.currentBackgroundSelectedDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgInSelected");
                this.transitionParams.drawPinnedBottomBackground = false;
            } else {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgInMedia");
                this.currentBackgroundSelectedDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgInMediaSelected");
                this.transitionParams.drawPinnedBottomBackground = true;
            }
            setBackgroundTopY(true);
            if (isDrawSelectionBackground() && (this.currentPosition == null || getBackground() != null)) {
                shadowDrawable = this.currentBackgroundSelectedDrawable.getShadowDrawable();
            } else {
                shadowDrawable = this.currentBackgroundDrawable.getShadowDrawable();
            }
            drawable = shadowDrawable;
            this.backgroundDrawableLeft = AndroidUtilities.dp(((this.isChat && this.isAvatarVisible) ? 48 : 0) + (!this.mediaBackground ? 3 : 9));
            this.backgroundDrawableRight = this.backgroundWidth - (this.mediaBackground ? 0 : AndroidUtilities.dp(3.0f));
            MessageObject.GroupedMessages groupedMessages3 = this.currentMessagesGroup;
            if (groupedMessages3 != null && !groupedMessages3.isDocuments) {
                if (!this.currentPosition.edge) {
                    this.backgroundDrawableLeft -= AndroidUtilities.dp(10.0f);
                    this.backgroundDrawableRight += AndroidUtilities.dp(10.0f);
                }
                if (this.currentPosition.leftSpanOffset != 0) {
                    this.backgroundDrawableLeft += (int) Math.ceil((r0 / 1000.0f) * getGroupPhotosWidth());
                }
            }
            boolean z6 = this.mediaBackground;
            if ((!z6 && this.drawPinnedBottom) || (!z4 && this.transitionParams.changePinnedBottomProgress != 1.0f)) {
                if (this.drawPinnedBottom || !z6) {
                    this.backgroundDrawableRight -= AndroidUtilities.dp(6.0f);
                }
                if (!this.mediaBackground) {
                    this.backgroundDrawableLeft += AndroidUtilities.dp(6.0f);
                }
            }
            MessageObject.GroupedMessagePosition groupedMessagePosition3 = this.currentPosition;
            if (groupedMessagePosition3 != null) {
                if ((groupedMessagePosition3.flags & 2) == 0) {
                    this.backgroundDrawableRight += AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                }
                if ((this.currentPosition.flags & 1) == 0) {
                    this.backgroundDrawableLeft -= AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                    this.backgroundDrawableRight += AndroidUtilities.dp(SharedConfig.bubbleRadius + 2);
                }
                if ((this.currentPosition.flags & 4) == 0) {
                    i4 = 0 - AndroidUtilities.dp(SharedConfig.bubbleRadius + 3);
                    i3 = AndroidUtilities.dp(SharedConfig.bubbleRadius + 3) + 0;
                } else {
                    i3 = 0;
                    i4 = 0;
                }
                if ((this.currentPosition.flags & 8) == 0) {
                    i3 += AndroidUtilities.dp(SharedConfig.bubbleRadius + 4);
                }
                i = i3;
                i2 = i4;
            } else {
                i = 0;
                i2 = 0;
            }
            boolean z7 = this.drawPinnedBottom;
            if (z7 && this.drawPinnedTop) {
                dp = 0;
            } else if (z7) {
                dp = AndroidUtilities.dp(1.0f);
            } else {
                dp = AndroidUtilities.dp(2.0f);
            }
            int dp6 = i2 + (this.drawPinnedTop ? 0 : AndroidUtilities.dp(1.0f));
            this.backgroundDrawableTop = dp6;
            int i15 = (this.layoutHeight - dp) + i;
            this.backgroundDrawableBottom = dp6 + i15;
            if (!this.mediaBackground) {
                if (this.drawPinnedTop) {
                    this.backgroundDrawableTop = dp6 - AndroidUtilities.dp(1.0f);
                    i15 += AndroidUtilities.dp(1.0f);
                }
                if (this.drawPinnedBottom) {
                    this.backgroundDrawableBottom += AndroidUtilities.dp(1.0f);
                    i15 += AndroidUtilities.dp(1.0f);
                }
            }
            int i16 = i15;
            setDrawableBoundsInner(this.currentBackgroundDrawable, this.backgroundDrawableLeft, this.backgroundDrawableTop, this.backgroundDrawableRight, i16);
            if (z4) {
                setDrawableBoundsInner(this.currentBackgroundSelectedDrawable, AndroidUtilities.dp(6.0f) + this.backgroundDrawableLeft, this.backgroundDrawableTop, this.backgroundDrawableRight - AndroidUtilities.dp(6.0f), i16);
            } else {
                setDrawableBoundsInner(this.currentBackgroundSelectedDrawable, this.backgroundDrawableLeft, this.backgroundDrawableTop, this.backgroundDrawableRight, i16);
            }
            setDrawableBoundsInner(drawable, this.backgroundDrawableLeft, this.backgroundDrawableTop, this.backgroundDrawableRight, i16);
        }
        Drawable drawable2 = drawable;
        if (!this.currentMessageObject.isOutOwner() && this.transitionParams.changePinnedBottomProgress != 1.0f && !this.mediaBackground && !this.drawPinnedBottom) {
            this.backgroundDrawableLeft -= AndroidUtilities.dp(6.0f);
            this.backgroundDrawableRight += AndroidUtilities.dp(6.0f);
        }
        if (this.hasPsaHint) {
            MessageObject.GroupedMessagePosition groupedMessagePosition4 = this.currentPosition;
            if (groupedMessagePosition4 == null || (groupedMessagePosition4.flags & 2) != 0) {
                i7 = this.currentBackgroundDrawable.getBounds().right;
            } else {
                int groupPhotosWidth = getGroupPhotosWidth();
                i7 = 0;
                for (int i17 = 0; i17 < this.currentMessagesGroup.posArray.size(); i17++) {
                    if (this.currentMessagesGroup.posArray.get(i17).minY != 0) {
                        break;
                    }
                    i7 = (int) (i7 + Math.ceil(((r4.pw + r4.leftSpanOffset) / 1000.0f) * groupPhotosWidth));
                }
            }
            Drawable drawable3 = Theme.chat_psaHelpDrawable[this.currentMessageObject.isOutOwner() ? 1 : 0];
            if (this.currentMessageObject.type == 5) {
                dp2 = AndroidUtilities.dp(12.0f);
            } else {
                dp2 = AndroidUtilities.dp((this.drawNameLayout ? 19 : 0) + 10);
            }
            this.psaHelpX = (i7 - drawable3.getIntrinsicWidth()) - AndroidUtilities.dp(this.currentMessageObject.isOutOwner() ? 20.0f : 14.0f);
            this.psaHelpY = dp2 + AndroidUtilities.dp(4.0f);
        }
        if (this.checkBoxVisible || this.checkBoxAnimationInProgress) {
            animateCheckboxTranslation();
            int dp7 = AndroidUtilities.dp(21.0f);
            this.checkBox.setBounds(AndroidUtilities.dp(-27.0f) + this.checkBoxTranslation, (this.currentBackgroundDrawable.getBounds().bottom - AndroidUtilities.dp(8.0f)) - dp7, dp7, dp7);
        }
        if (z || !drawBackgroundInParent()) {
            int saveCount = canvas.getSaveCount();
            if (this.transitionYOffsetForDrawables != 0.0f) {
                canvas.save();
                canvas2 = canvas;
                canvas2.translate(0.0f, this.transitionYOffsetForDrawables);
            } else {
                canvas2 = canvas;
            }
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 == null || !messageObject2.isRoundVideo()) {
                str = "drawableMsgInMedia";
                str2 = "drawableMsgIn";
                str3 = "drawableMsgOut";
                z2 = false;
                f = 0.0f;
            } else {
                float videoTranscriptionProgress = getVideoTranscriptionProgress();
                this.currentBackgroundDrawable.setRoundingRadius(1.0f - videoTranscriptionProgress);
                f = AndroidUtilities.lerp(this.backgroundWidth / 2, 0, videoTranscriptionProgress);
                int i18 = (int) (videoTranscriptionProgress * 255.0f);
                str = "drawableMsgInMedia";
                str2 = "drawableMsgIn";
                str3 = "drawableMsgOut";
                z2 = false;
                canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), Math.max(this.currentBackgroundDrawable.getBounds().bottom, getHeight()), i18, 31);
                this.roundVideoPlayPipFloat.set((((MediaController.getInstance().isPiPShown() && MediaController.getInstance().isPlayingMessageAndReadyToDraw(this.currentMessageObject)) || this.wouldBeInPip) && canvas.isHardwareAccelerated()) ? 1.0f : 0.0f);
                if (MediaController.getInstance().isPiPShown()) {
                    this.wouldBeInPip = false;
                }
            }
            if ((this.drawBackground || this.transitionParams.animateDrawBackground) && this.currentBackgroundDrawable != null && ((this.currentPosition == null || (isDrawSelectionBackground() && (this.currentMessageObject.isMusic() || this.currentMessageObject.isDocument()))) && (!this.enterTransitionInProgress || this.currentMessageObject.isVoice()))) {
                float f2 = this.alphaInternal;
                if (z) {
                    f2 *= getAlpha();
                }
                if (hasSelectionOverlay()) {
                    this.currentSelectedBackgroundAlpha = 0.0f;
                    int i19 = (int) (f2 * 255.0f);
                    this.currentBackgroundDrawable.setAlpha(i19);
                    this.currentBackgroundDrawable.drawCached(canvas2, this.backgroundCacheParams);
                    if (drawable2 != null && this.currentPosition == null) {
                        drawable2.setAlpha(i19);
                        drawable2.draw(canvas2);
                    }
                } else {
                    if (this.isHighlightedAnimated) {
                        this.currentBackgroundDrawable.setAlpha((int) (f2 * 255.0f));
                        this.currentBackgroundDrawable.drawCached(canvas2, this.backgroundCacheParams);
                        float highlightAlpha = getHighlightAlpha();
                        this.currentSelectedBackgroundAlpha = highlightAlpha;
                        if (this.currentPosition == null) {
                            this.currentBackgroundSelectedDrawable.setAlpha((int) (highlightAlpha * f2 * 255.0f));
                            this.currentBackgroundSelectedDrawable.drawCached(canvas2, this.backgroundCacheParams);
                        }
                    } else if (this.selectedBackgroundProgress != 0.0f && ((groupedMessages = this.currentMessagesGroup) == null || !groupedMessages.isDocuments)) {
                        this.currentBackgroundDrawable.setAlpha((int) (f2 * 255.0f));
                        this.currentBackgroundDrawable.drawCached(canvas2, this.backgroundCacheParams);
                        float f3 = this.selectedBackgroundProgress;
                        this.currentSelectedBackgroundAlpha = f3;
                        this.currentBackgroundSelectedDrawable.setAlpha((int) (f3 * f2 * 255.0f));
                        this.currentBackgroundSelectedDrawable.drawCached(canvas2, this.backgroundCacheParams);
                        if (this.currentBackgroundDrawable.getGradientShader() == null) {
                            drawable2 = null;
                        }
                    } else if (isDrawSelectionBackground() && (this.currentPosition == null || this.currentMessageObject.isMusic() || this.currentMessageObject.isDocument() || getBackground() != null)) {
                        if (this.currentPosition != null) {
                            canvas.save();
                        }
                        this.currentSelectedBackgroundAlpha = 1.0f;
                        this.currentBackgroundSelectedDrawable.setAlpha((int) (f2 * 255.0f));
                        this.currentBackgroundSelectedDrawable.drawCached(canvas2, this.backgroundCacheParams);
                        if (this.currentPosition != null) {
                            canvas.restore();
                        }
                    } else {
                        this.currentSelectedBackgroundAlpha = 0.0f;
                        this.currentBackgroundDrawable.setAlpha((int) (f2 * 255.0f));
                        this.currentBackgroundDrawable.drawCached(canvas2, this.backgroundCacheParams);
                    }
                    if (drawable2 != null && this.currentPosition == null) {
                        drawable2.setAlpha((int) (f2 * 255.0f));
                        drawable2.draw(canvas2);
                    }
                    if (this.transitionParams.changePinnedBottomProgress != 1.0f && this.currentPosition == null) {
                        if (this.currentMessageObject.isOutOwner()) {
                            Theme.MessageDrawable messageDrawable2 = (Theme.MessageDrawable) getThemedDrawable(str3);
                            Rect bounds = this.currentBackgroundDrawable.getBounds();
                            messageDrawable2.setBounds(bounds.left, bounds.top, bounds.right + AndroidUtilities.dp(6.0f), bounds.bottom);
                            canvas.save();
                            canvas2.translate(-f, 0.0f);
                            canvas2.clipRect(bounds.right - AndroidUtilities.dp(16.0f), bounds.bottom - AndroidUtilities.dp(16.0f), bounds.right + AndroidUtilities.dp(16.0f), bounds.bottom);
                            int i20 = this.parentWidth;
                            int i21 = this.parentHeight;
                            if (i21 == 0) {
                                i20 = getParentWidth();
                                i21 = AndroidUtilities.displaySize.y;
                                if (getParent() instanceof View) {
                                    View view = (View) getParent();
                                    int measuredWidth = view.getMeasuredWidth();
                                    i6 = view.getMeasuredHeight();
                                    i5 = measuredWidth;
                                    float y = getY();
                                    float f4 = this.parentViewTopOffset;
                                    messageDrawable2.setTop((int) (y + f4), i5, i6, (int) f4, this.blurredViewTopOffset, this.blurredViewBottomOffset, this.pinnedTop, this.pinnedBottom);
                                    messageObject = this.currentMessageObject;
                                    if (messageObject != null && messageObject.hasInlineBotButtons()) {
                                        z2 = true;
                                    }
                                    messageDrawable2.setBotButtonsBottom(z2);
                                    messageDrawable2.setAlpha((int) (((!this.mediaBackground || this.pinnedBottom) ? 1.0f - this.transitionParams.changePinnedBottomProgress : this.transitionParams.changePinnedBottomProgress) * 255.0f));
                                    messageDrawable2.draw(canvas2);
                                    messageDrawable2.setAlpha(255);
                                    canvas.restore();
                                }
                            }
                            i5 = i20;
                            i6 = i21;
                            float y2 = getY();
                            float f42 = this.parentViewTopOffset;
                            messageDrawable2.setTop((int) (y2 + f42), i5, i6, (int) f42, this.blurredViewTopOffset, this.blurredViewBottomOffset, this.pinnedTop, this.pinnedBottom);
                            messageObject = this.currentMessageObject;
                            if (messageObject != null) {
                                z2 = true;
                            }
                            messageDrawable2.setBotButtonsBottom(z2);
                            messageDrawable2.setAlpha((int) (((!this.mediaBackground || this.pinnedBottom) ? 1.0f - this.transitionParams.changePinnedBottomProgress : this.transitionParams.changePinnedBottomProgress) * 255.0f));
                            messageDrawable2.draw(canvas2);
                            messageDrawable2.setAlpha(255);
                            canvas.restore();
                        } else {
                            if (this.transitionParams.drawPinnedBottomBackground) {
                                messageDrawable = (Theme.MessageDrawable) getThemedDrawable(str2);
                            } else {
                                messageDrawable = (Theme.MessageDrawable) getThemedDrawable(str);
                            }
                            messageDrawable.setAlpha((int) (((this.mediaBackground || this.pinnedBottom) ? 1.0f - this.transitionParams.changePinnedBottomProgress : this.transitionParams.changePinnedBottomProgress) * 255.0f));
                            Rect bounds2 = this.currentBackgroundDrawable.getBounds();
                            messageDrawable.setBounds(bounds2.left - AndroidUtilities.dp(6.0f), bounds2.top, bounds2.right, bounds2.bottom);
                            canvas.save();
                            canvas2.translate(f, 0.0f);
                            canvas2.clipRect(bounds2.left - AndroidUtilities.dp(6.0f), bounds2.bottom - AndroidUtilities.dp(16.0f), bounds2.left + AndroidUtilities.dp(18.0f), bounds2.bottom);
                            messageDrawable.draw(canvas2);
                            messageDrawable.setAlpha(255);
                            canvas.restore();
                        }
                    }
                }
            }
            MessageObject messageObject3 = this.currentMessageObject;
            if (messageObject3 != null && messageObject3.isRoundVideo()) {
                this.currentBackgroundDrawable.setRoundingRadius(0.0f);
            }
            canvas2.restoreToCount(saveCount);
        }
    }

    private void animateCheckboxTranslation() {
        boolean z = this.checkBoxVisible;
        if (z || this.checkBoxAnimationInProgress) {
            if ((z && this.checkBoxAnimationProgress == 1.0f) || (!z && this.checkBoxAnimationProgress == 0.0f)) {
                this.checkBoxAnimationInProgress = false;
            }
            this.checkBoxTranslation = (int) Math.ceil((z ? CubicBezierInterpolator.EASE_OUT : CubicBezierInterpolator.EASE_IN).getInterpolation(this.checkBoxAnimationProgress) * AndroidUtilities.dp(35.0f));
            if (!this.currentMessageObject.isOutOwner()) {
                updateTranslation();
            }
            if (this.checkBoxAnimationInProgress) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long j = elapsedRealtime - this.lastCheckBoxAnimationTime;
                this.lastCheckBoxAnimationTime = elapsedRealtime;
                if (this.checkBoxVisible) {
                    float f = this.checkBoxAnimationProgress + (j / 200.0f);
                    this.checkBoxAnimationProgress = f;
                    if (f > 1.0f) {
                        this.checkBoxAnimationProgress = 1.0f;
                    }
                } else {
                    float f2 = this.checkBoxAnimationProgress - (j / 200.0f);
                    this.checkBoxAnimationProgress = f2;
                    if (f2 <= 0.0f) {
                        this.checkBoxAnimationProgress = 0.0f;
                    }
                }
                invalidate();
                ((View) getParent()).invalidate();
            }
        }
    }

    public boolean drawBackgroundInParent() {
        MessageObject messageObject;
        return this.canDrawBackgroundInParent && (messageObject = this.currentMessageObject) != null && messageObject.isOutOwner() && getThemedColor(Theme.key_chat_outBubbleGradient1) != 0;
    }

    public void drawCommentButton(Canvas canvas, float f) {
        if (this.drawSideButton != 3) {
            return;
        }
        int dp = AndroidUtilities.dp(32.0f);
        if (this.commentLayout != null) {
            this.sideStartY -= AndroidUtilities.dp(18.0f);
            dp += AndroidUtilities.dp(18.0f);
        }
        RectF rectF = this.rect;
        float f2 = this.sideStartX;
        rectF.set(f2, this.sideStartY, AndroidUtilities.dp(32.0f) + f2, this.sideStartY + dp);
        applyServiceShaderMatrix();
        if (f != 1.0f) {
            int alpha = getThemedPaint("paintChatActionBackground").getAlpha();
            getThemedPaint("paintChatActionBackground").setAlpha((int) (alpha * f));
            canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), getThemedPaint("paintChatActionBackground"));
            getThemedPaint("paintChatActionBackground").setAlpha(alpha);
        } else {
            canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), getThemedPaint(this.sideButtonPressed ? "paintChatActionBackgroundSelected" : "paintChatActionBackground"));
        }
        if (hasGradientService()) {
            if (f != 1.0f) {
                int alpha2 = Theme.chat_actionBackgroundGradientDarkenPaint.getAlpha();
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha((int) (alpha2 * f));
                canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), Theme.chat_actionBackgroundGradientDarkenPaint);
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha(alpha2);
            } else {
                canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), Theme.chat_actionBackgroundGradientDarkenPaint);
            }
        }
        Drawable themeDrawable = Theme.getThemeDrawable("drawableCommentSticker");
        BaseCell.setDrawableBounds(themeDrawable, this.sideStartX + AndroidUtilities.dp(4.0f), this.sideStartY + AndroidUtilities.dp(4.0f));
        if (f != 1.0f) {
            themeDrawable.setAlpha((int) (f * 255.0f));
            themeDrawable.draw(canvas);
            themeDrawable.setAlpha(255);
        } else {
            themeDrawable.draw(canvas);
        }
        if (this.commentLayout != null) {
            Theme.chat_stickerCommentCountPaint.setColor(getThemedColor(Theme.key_chat_stickerReplyNameText));
            Theme.chat_stickerCommentCountPaint.setAlpha((int) (f * 255.0f));
            if (this.transitionParams.animateComments) {
                if (this.transitionParams.animateCommentsLayout != null) {
                    canvas.save();
                    Theme.chat_stickerCommentCountPaint.setAlpha((int) ((1.0d - this.transitionParams.animateChangeProgress) * 255.0d * f));
                    canvas.translate(this.sideStartX + ((AndroidUtilities.dp(32.0f) - this.transitionParams.animateTotalCommentWidth) / 2), this.sideStartY + AndroidUtilities.dp(30.0f));
                    this.transitionParams.animateCommentsLayout.draw(canvas);
                    canvas.restore();
                }
                Theme.chat_stickerCommentCountPaint.setAlpha((int) (this.transitionParams.animateChangeProgress * 255.0f));
            }
            canvas.save();
            canvas.translate(this.sideStartX + ((AndroidUtilities.dp(32.0f) - this.totalCommentWidth) / 2), this.sideStartY + AndroidUtilities.dp(30.0f));
            this.commentLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void applyServiceShaderMatrix() {
        applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, getX(), this.viewTop);
    }

    private void applyServiceShaderMatrix(int i, int i2, float f, float f2) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(i, i2, f, f2);
        } else {
            Theme.applyServiceShaderMatrix(i, i2, f, f2);
        }
    }

    public boolean hasOutboundsContent() {
        AnimatedEmojiSpan.EmojiGroupedSpans emojiGroupedSpans;
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable;
        if (getAlpha() != 1.0f) {
            return false;
        }
        if ((this.transitionParams.transitionBotButtons.isEmpty() || !this.transitionParams.animateBotButtonsChanged) && this.botButtons.isEmpty() && this.drawSideButton == 0 && ((!this.drawNameLayout || this.nameLayout == null || (swapAnimatedEmojiDrawable = this.currentNameStatusDrawable) == null || swapAnimatedEmojiDrawable.getDrawable() == null) && (((emojiGroupedSpans = this.animatedEmojiStack) == null || emojiGroupedSpans.holders.isEmpty()) && (!this.drawTopic || this.topicButton == null || ((groupedMessagePosition = this.currentPosition) != null && (groupedMessagePosition.minY != 0 || groupedMessagePosition.minX != 0)))))) {
            if (this.currentMessagesGroup != null) {
                return false;
            }
            TransitionParams transitionParams = this.transitionParams;
            if (((!transitionParams.animateReplaceCaptionLayout || transitionParams.animateChangeProgress == 1.0f) && (transitionParams.animateChangeProgress == 1.0f || !transitionParams.animateMessageText)) || transitionParams.animateOutAnimateEmoji == null || this.transitionParams.animateOutAnimateEmoji.holders.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void drawOutboundsContent(Canvas canvas) {
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        float f;
        float f2;
        float f3;
        float dp;
        int extraTextX;
        float f4;
        int themedColor;
        float dp2;
        float dp3;
        if (!this.enterTransitionInProgress) {
            drawAnimatedEmojis(canvas, 1.0f);
        }
        if (this.currentNameStatusDrawable != null && this.drawNameLayout && this.nameLayout != null) {
            if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                themedColor = getThemedColor(Theme.key_chat_stickerNameText);
                if (this.currentMessageObject.isOutOwner()) {
                    dp3 = AndroidUtilities.dp(28.0f);
                } else {
                    dp3 = this.backgroundDrawableLeft + this.transitionParams.deltaLeft + this.backgroundDrawableRight + AndroidUtilities.dp(22.0f);
                }
                dp2 = this.layoutHeight - AndroidUtilities.dp(38.0f);
                f4 = dp3 - this.nameOffsetX;
            } else {
                if (this.mediaBackground || this.currentMessageObject.isOutOwner()) {
                    dp = this.backgroundDrawableLeft + this.transitionParams.deltaLeft + AndroidUtilities.dp(11.0f);
                    extraTextX = getExtraTextX();
                } else {
                    dp = this.backgroundDrawableLeft + this.transitionParams.deltaLeft + AndroidUtilities.dp((this.mediaBackground || !this.drawPinnedBottom) ? 17.0f : 11.0f);
                    extraTextX = getExtraTextX();
                }
                f4 = dp + extraTextX;
                if (this.currentUser != null) {
                    Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
                    if (messageDrawable != null && messageDrawable.hasGradient()) {
                        themedColor = getThemedColor(Theme.key_chat_messageTextOut);
                    } else {
                        themedColor = getThemedColor(AvatarDrawable.getNameColorNameForId(this.currentUser.id));
                    }
                } else if (this.currentChat != null) {
                    if (this.currentMessageObject.isOutOwner() && ChatObject.isChannel(this.currentChat)) {
                        Theme.MessageDrawable messageDrawable2 = this.currentBackgroundDrawable;
                        if (messageDrawable2 != null && messageDrawable2.hasGradient()) {
                            themedColor = getThemedColor(Theme.key_chat_messageTextOut);
                        } else {
                            themedColor = getThemedColor(Theme.key_chat_outForwardedNameText);
                        }
                    } else if (ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) {
                        themedColor = Theme.changeColorAccent(getThemedColor(AvatarDrawable.getNameColorNameForId(5L)));
                    } else if (this.currentMessageObject.isOutOwner()) {
                        themedColor = getThemedColor(Theme.key_chat_outForwardedNameText);
                    } else {
                        themedColor = getThemedColor(AvatarDrawable.getNameColorNameForId(this.currentChat.id));
                    }
                } else {
                    themedColor = getThemedColor(AvatarDrawable.getNameColorNameForId(0L));
                }
                dp2 = AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f);
            }
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null) {
                MessageObject.GroupedMessages.TransitionParams transitionParams = groupedMessages.transitionParams;
                if (transitionParams.backgroundChangeBounds) {
                    f4 += transitionParams.offsetLeft;
                    dp2 += transitionParams.offsetTop - getTranslationY();
                }
            }
            float f5 = f4 + this.animationOffsetX;
            TransitionParams transitionParams2 = this.transitionParams;
            float f6 = dp2 + transitionParams2.deltaTop;
            if (transitionParams2.animateSign) {
                f5 = ((f5 - this.transitionParams.animateNameX) * this.transitionParams.animateChangeProgress) + this.transitionParams.animateNameX;
            }
            this.currentNameStatusDrawable.setBounds((int) (Math.abs(f5) + this.nameLayoutWidth + AndroidUtilities.dp(2.0f)), (int) (((this.nameLayout.getHeight() / 2) + f6) - AndroidUtilities.dp(10.0f)), (int) (Math.abs(f5) + this.nameLayoutWidth + AndroidUtilities.dp(22.0f)), (int) (f6 + (this.nameLayout.getHeight() / 2) + AndroidUtilities.dp(10.0f)));
            this.currentNameStatusDrawable.setColor(Integer.valueOf(ColorUtils.setAlphaComponent(themedColor, 115)));
            this.currentNameStatusDrawable.draw(canvas);
        }
        if (this.drawTopic && this.topicButton != null && ((groupedMessagePosition = this.currentPosition) == null || (groupedMessagePosition.minY == 0 && groupedMessagePosition.minX == 0))) {
            if (!this.isRoundVideo || this.hasLinkPreview) {
                f = 1.0f;
            } else {
                f = (1.0f - getVideoTranscriptionProgress()) * 1.0f;
                TransitionParams transitionParams3 = this.transitionParams;
                if (transitionParams3.animatePlayingRound) {
                    if (this.isPlayingRound) {
                        f3 = 1.0f - transitionParams3.animateChangeProgress;
                    } else {
                        f3 = transitionParams3.animateChangeProgress;
                    }
                    f *= f3;
                } else if (this.isPlayingRound) {
                    f = 0.0f;
                }
            }
            if (!this.transitionParams.animateForwardedLayout) {
                f2 = 1.0f;
            } else if (!this.currentMessageObject.needDrawForwarded()) {
                f2 = 1.0f - this.transitionParams.animateChangeProgress;
            } else {
                f2 = this.transitionParams.animateChangeProgress;
            }
            this.topicButton.drawOutbounds(canvas, f2 * f);
        }
        if (!this.transitionParams.transitionBotButtons.isEmpty()) {
            if (this.transitionParams.animateBotButtonsChanged) {
                drawBotButtons(canvas, this.transitionParams.transitionBotButtons, (int) (MathUtils.clamp(1.0f - ((float) Math.pow(r0.animateChangeProgress, 2.0d)), 0.0f, 1.0f) * 255.0f));
            }
        }
        if (!this.botButtons.isEmpty()) {
            ArrayList<BotButton> arrayList = this.botButtons;
            TransitionParams transitionParams4 = this.transitionParams;
            drawBotButtons(canvas, arrayList, transitionParams4.animateBotButtonsChanged ? (int) (transitionParams4.animateChangeProgress * 255.0f) : 255);
        }
        drawSideButton(canvas);
    }

    public void drawAnimatedEmojis(Canvas canvas, float f) {
        drawAnimatedEmojiMessageText(canvas, f);
    }

    private void drawAnimatedEmojiMessageText(Canvas canvas, float f) {
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateChangeProgress != 1.0f && transitionParams.animateMessageText) {
            canvas.save();
            Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
            if (messageDrawable != null) {
                Rect bounds = messageDrawable.getBounds();
                if (this.currentMessageObject.isOutOwner() && !this.mediaBackground && !this.pinnedBottom) {
                    canvas.clipRect(bounds.left + AndroidUtilities.dp(4.0f), bounds.top + AndroidUtilities.dp(4.0f), bounds.right - AndroidUtilities.dp(10.0f), bounds.bottom - AndroidUtilities.dp(4.0f));
                } else {
                    canvas.clipRect(bounds.left + AndroidUtilities.dp(4.0f), bounds.top + AndroidUtilities.dp(4.0f), bounds.right - AndroidUtilities.dp(4.0f), bounds.bottom - AndroidUtilities.dp(4.0f));
                }
            }
            drawAnimatedEmojiMessageText(canvas, this.transitionParams.animateOutTextBlocks, this.transitionParams.animateOutAnimateEmoji, false, f * (1.0f - this.transitionParams.animateChangeProgress));
            drawAnimatedEmojiMessageText(canvas, this.currentMessageObject.textLayoutBlocks, this.animatedEmojiStack, true, f * this.transitionParams.animateChangeProgress);
            canvas.restore();
            return;
        }
        drawAnimatedEmojiMessageText(canvas, this.currentMessageObject.textLayoutBlocks, this.animatedEmojiStack, true, f);
    }

    private void drawAnimatedEmojiMessageText(Canvas canvas, ArrayList<MessageObject.TextLayoutBlock> arrayList, AnimatedEmojiSpan.EmojiGroupedSpans emojiGroupedSpans, boolean z, float f) {
        int size;
        int i;
        if (arrayList == null || arrayList.isEmpty() || f == 0.0f) {
            return;
        }
        if (z) {
            if (this.fullyDraw) {
                this.firstVisibleBlockNum = 0;
                this.lastVisibleBlockNum = arrayList.size();
            }
            i = this.firstVisibleBlockNum;
            size = this.lastVisibleBlockNum;
        } else {
            size = arrayList.size();
            i = 0;
        }
        int i2 = this.textY;
        float f2 = i2;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateText) {
            float f3 = transitionParams.animateFromTextY;
            float f4 = transitionParams.animateChangeProgress;
            f2 = (f3 * (1.0f - f4)) + (i2 * f4);
        }
        float f5 = f2;
        for (int i3 = i; i3 <= size && i3 < arrayList.size(); i3++) {
            if (i3 >= 0) {
                MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i3);
                canvas.save();
                canvas.translate(this.textX - (textLayoutBlock.isRtl() ? (int) Math.ceil(this.currentMessageObject.textXOffset) : 0), textLayoutBlock.textYOffset + f5 + this.transitionYOffsetForDrawables);
                float f6 = textLayoutBlock.textYOffset + f5 + this.transitionYOffsetForDrawables;
                boolean z2 = this.transitionParams.messageEntering;
                AnimatedEmojiSpan.drawAnimatedEmojis(canvas, textLayoutBlock.textLayout, emojiGroupedSpans, 0.0f, textLayoutBlock.spoilers, 0.0f, 0.0f, f6, f, Theme.chat_animatedEmojiTextColorFilter);
                canvas.restore();
            }
        }
    }

    public void drawAnimatedEmojiCaption(Canvas canvas, float f) {
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateReplaceCaptionLayout || transitionParams.animateChangeProgress == 1.0f) {
            drawAnimatedEmojiCaption(canvas, this.captionLayout, this.animatedEmojiStack, f);
        } else {
            drawAnimatedEmojiCaption(canvas, transitionParams.animateOutCaptionLayout, this.transitionParams.animateOutAnimateEmoji, (1.0f - this.transitionParams.animateChangeProgress) * f);
            drawAnimatedEmojiCaption(canvas, this.captionLayout, this.animatedEmojiStack, f * this.transitionParams.animateChangeProgress);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:16|(3:18|(1:20)(2:22|(6:24|25|26|27|28|29)(2:33|(1:37)))|21)|38|25|26|27|28|29) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x008c, code lost:
    
        r13 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008d, code lost:
    
        org.telegram.messenger.FileLog.e(r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawAnimatedEmojiCaption(android.graphics.Canvas r12, android.text.Layout r13, org.telegram.ui.Components.AnimatedEmojiSpan.EmojiGroupedSpans r14, float r15) {
        /*
            r11 = this;
            if (r13 == 0) goto L93
            org.telegram.messenger.MessageObject r0 = r11.currentMessageObject
            boolean r0 = r0.deleted
            if (r0 == 0) goto Lc
            org.telegram.messenger.MessageObject$GroupedMessagePosition r0 = r11.currentPosition
            if (r0 != 0) goto L93
        Lc:
            r0 = 0
            int r1 = (r15 > r0 ? 1 : (r15 == r0 ? 0 : -1))
            if (r1 > 0) goto L13
            goto L93
        L13:
            r12.save()
            org.telegram.messenger.MessageObject$GroupedMessages r1 = r11.currentMessagesGroup
            if (r1 == 0) goto L20
            org.telegram.messenger.MessageObject$GroupedMessages$TransitionParams r1 = r1.transitionParams
            float r1 = r1.captionEnterProgress
            float r15 = r15 * r1
        L20:
            r9 = r15
            int r15 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r15 != 0) goto L26
            return
        L26:
            float r15 = r11.captionY
            float r0 = r11.captionX
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r1 = r11.transitionParams
            boolean r2 = r1.animateBackgroundBoundsInner
            if (r2 == 0) goto L7a
            boolean r2 = r1.transformGroupToSingleMessage
            if (r2 == 0) goto L3f
            float r1 = r11.getTranslationY()
            float r15 = r15 - r1
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r1 = r11.transitionParams
            float r1 = r1.deltaLeft
        L3d:
            float r0 = r0 + r1
            goto L7a
        L3f:
            boolean r1 = org.telegram.ui.Cells.ChatMessageCell.TransitionParams.access$6300(r1)
            if (r1 == 0) goto L63
            float r15 = r11.captionX
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r0 = r11.transitionParams
            float r1 = r0.animateChangeProgress
            float r15 = r15 * r1
            float r2 = r0.captionFromX
            r3 = 1065353216(0x3f800000, float:1.0)
            float r4 = r3 - r1
            float r2 = r2 * r4
            float r15 = r15 + r2
            float r2 = r11.captionY
            float r2 = r2 * r1
            float r0 = r0.captionFromY
            float r3 = r3 - r1
            float r0 = r0 * r3
            float r2 = r2 + r0
            r0 = r15
            r8 = r2
            goto L7b
        L63:
            org.telegram.messenger.MessageObject r1 = r11.currentMessageObject
            boolean r1 = r1.isVoice()
            if (r1 == 0) goto L75
            org.telegram.messenger.MessageObject r1 = r11.currentMessageObject
            java.lang.CharSequence r1 = r1.caption
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L7a
        L75:
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r1 = r11.transitionParams
            float r1 = r1.deltaLeft
            goto L3d
        L7a:
            r8 = r15
        L7b:
            r12.translate(r0, r8)
            r4 = 0
            java.util.List<org.telegram.ui.Components.spoilers.SpoilerEffect> r5 = r11.captionSpoilers     // Catch: java.lang.Exception -> L8c
            r6 = 0
            r7 = 0
            android.graphics.PorterDuffColorFilter r10 = org.telegram.ui.ActionBar.Theme.chat_animatedEmojiTextColorFilter     // Catch: java.lang.Exception -> L8c
            r1 = r12
            r2 = r13
            r3 = r14
            org.telegram.ui.Components.AnimatedEmojiSpan.drawAnimatedEmojis(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Exception -> L8c
            goto L90
        L8c:
            r13 = move-exception
            org.telegram.messenger.FileLog.e(r13)
        L90:
            r12.restore()
        L93:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawAnimatedEmojiCaption(android.graphics.Canvas, android.text.Layout, org.telegram.ui.Components.AnimatedEmojiSpan$EmojiGroupedSpans, float):void");
    }

    private void drawSideButton(Canvas canvas) {
        if (this.drawSideButton != 0) {
            if (this.currentMessageObject.isOutOwner()) {
                float dp = this.transitionParams.lastBackgroundLeft - AndroidUtilities.dp(40.0f);
                this.sideStartX = dp;
                MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
                if (groupedMessages != null) {
                    this.sideStartX = dp + (groupedMessages.transitionParams.offsetLeft - this.animationOffsetX);
                }
            } else {
                float dp2 = this.transitionParams.lastBackgroundRight + AndroidUtilities.dp(8.0f);
                this.sideStartX = dp2;
                MessageObject.GroupedMessages groupedMessages2 = this.currentMessagesGroup;
                if (groupedMessages2 != null) {
                    this.sideStartX = dp2 + (groupedMessages2.transitionParams.offsetRight - this.animationOffsetX);
                }
            }
            float dp3 = (this.layoutHeight + this.transitionParams.deltaBottom) - AndroidUtilities.dp(41.0f);
            this.sideStartY = dp3;
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject.type == 19 && messageObject.textWidth < this.timeTextWidth) {
                this.sideStartY = dp3 - AndroidUtilities.dp(22.0f);
            }
            MessageObject.GroupedMessages groupedMessages3 = this.currentMessagesGroup;
            if (groupedMessages3 != null) {
                float f = this.sideStartY;
                MessageObject.GroupedMessages.TransitionParams transitionParams = groupedMessages3.transitionParams;
                float f2 = f + transitionParams.offsetBottom;
                this.sideStartY = f2;
                if (transitionParams.backgroundChangeBounds) {
                    this.sideStartY = f2 - getTranslationY();
                }
            }
            ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
            if (!reactionsLayoutInBubble.isSmall) {
                if (this.isRoundVideo) {
                    this.sideStartY -= reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress());
                } else if (reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f) {
                    this.sideStartY -= reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress);
                }
            }
            if (!this.currentMessageObject.isOutOwner() && this.isRoundVideo && !this.hasLinkPreview) {
                float dp4 = this.isAvatarVisible ? (AndroidUtilities.roundPlayingMessageSize - AndroidUtilities.roundMessageSize) * 0.7f : AndroidUtilities.dp(50.0f);
                float videoTranscriptionProgress = this.isPlayingRound ? (1.0f - getVideoTranscriptionProgress()) * dp4 : 0.0f;
                float dp5 = this.isPlayingRound ? AndroidUtilities.dp(28.0f) * (1.0f - getVideoTranscriptionProgress()) : 0.0f;
                TransitionParams transitionParams2 = this.transitionParams;
                if (transitionParams2.animatePlayingRound) {
                    videoTranscriptionProgress = (this.isPlayingRound ? transitionParams2.animateChangeProgress : 1.0f - transitionParams2.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress()) * dp4;
                    dp5 = AndroidUtilities.dp(28.0f) * (this.isPlayingRound ? this.transitionParams.animateChangeProgress : 1.0f - this.transitionParams.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress());
                }
                this.sideStartX -= videoTranscriptionProgress;
                this.sideStartY -= dp5;
            }
            if (this.drawSideButton == 3) {
                if (!this.enterTransitionInProgress || this.currentMessageObject.isVoice()) {
                    drawCommentButton(canvas, 1.0f);
                    return;
                }
                return;
            }
            RectF rectF = this.rect;
            float f3 = this.sideStartX;
            rectF.set(f3, this.sideStartY, AndroidUtilities.dp(32.0f) + f3, this.sideStartY + AndroidUtilities.dp(32.0f));
            applyServiceShaderMatrix();
            canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), getThemedPaint(this.sideButtonPressed ? "paintChatActionBackgroundSelected" : "paintChatActionBackground"));
            if (hasGradientService()) {
                canvas.drawRoundRect(this.rect, AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f), Theme.chat_actionBackgroundGradientDarkenPaint);
            }
            if (this.drawSideButton == 2) {
                Drawable themedDrawable = getThemedDrawable("drawableGoIcon");
                if (this.currentMessageObject.isOutOwner()) {
                    BaseCell.setDrawableBounds(themedDrawable, this.sideStartX + AndroidUtilities.dp(10.0f), this.sideStartY + AndroidUtilities.dp(9.0f));
                    canvas.save();
                    canvas.scale(-1.0f, 1.0f, themedDrawable.getBounds().centerX(), themedDrawable.getBounds().centerY());
                } else {
                    BaseCell.setDrawableBounds(themedDrawable, this.sideStartX + AndroidUtilities.dp(12.0f), this.sideStartY + AndroidUtilities.dp(9.0f));
                }
                themedDrawable.draw(canvas);
                if (this.currentMessageObject.isOutOwner()) {
                    canvas.restore();
                    return;
                }
                return;
            }
            Drawable themedDrawable2 = getThemedDrawable("drawableShareIcon");
            BaseCell.setDrawableBounds(themedDrawable2, this.sideStartX + AndroidUtilities.dp(8.0f), this.sideStartY + AndroidUtilities.dp(9.0f));
            themedDrawable2.draw(canvas);
        }
    }

    public void setTimeAlpha(float f) {
        this.timeAlpha = f;
    }

    public float getTimeAlpha() {
        return this.timeAlpha;
    }

    public int getBackgroundDrawableLeft() {
        int dp;
        int dp2;
        int i;
        if (this.currentMessageObject.isOutOwner()) {
            if (this.isRoundVideo) {
                return (this.layoutWidth - this.backgroundWidth) - ((int) ((1.0f - getVideoTranscriptionProgress()) * AndroidUtilities.dp(9.0f)));
            }
            return (this.layoutWidth - this.backgroundWidth) - (this.mediaBackground ? AndroidUtilities.dp(9.0f) : 0);
        }
        if (this.isRoundVideo) {
            if (this.isChat && this.isAvatarVisible) {
                r1 = 48;
            }
            dp = AndroidUtilities.dp(r1 + 3) + ((int) (AndroidUtilities.dp(6.0f) * (1.0f - getVideoTranscriptionProgress())));
        } else {
            if (this.isChat && this.isAvatarVisible) {
                r1 = 48;
            }
            dp = AndroidUtilities.dp(r1 + (this.mediaBackground ? 9 : 3));
        }
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.isDocuments && (i = this.currentPosition.leftSpanOffset) != 0) {
            dp += (int) Math.ceil((i / 1000.0f) * getGroupPhotosWidth());
        }
        if (this.isRoundVideo) {
            if (!this.drawPinnedBottom) {
                return dp;
            }
            dp2 = (int) (AndroidUtilities.dp(6.0f) * (1.0f - getVideoTranscriptionProgress()));
        } else {
            if (this.mediaBackground || !this.drawPinnedBottom) {
                return dp;
            }
            dp2 = AndroidUtilities.dp(6.0f);
        }
        return dp + dp2;
    }

    public int getBackgroundDrawableRight() {
        int dp;
        int backgroundDrawableLeft;
        int i = this.backgroundWidth;
        if (this.isRoundVideo) {
            dp = i - ((int) (getVideoTranscriptionProgress() * AndroidUtilities.dp(3.0f)));
            if (this.drawPinnedBottom && this.currentMessageObject.isOutOwner()) {
                dp = (int) (dp - (AndroidUtilities.dp(6.0f) * (1.0f - getVideoTranscriptionProgress())));
            }
            if (this.drawPinnedBottom && !this.currentMessageObject.isOutOwner()) {
                dp = (int) (dp - (AndroidUtilities.dp(6.0f) * (1.0f - getVideoTranscriptionProgress())));
            }
            backgroundDrawableLeft = getBackgroundDrawableLeft();
        } else {
            dp = i - (this.mediaBackground ? 0 : AndroidUtilities.dp(3.0f));
            if (!this.mediaBackground && this.drawPinnedBottom) {
                dp -= AndroidUtilities.dp(6.0f);
            }
            backgroundDrawableLeft = getBackgroundDrawableLeft();
        }
        return backgroundDrawableLeft + dp;
    }

    public int getBackgroundDrawableTop() {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        int dp = ((groupedMessagePosition == null || (groupedMessagePosition.flags & 4) != 0) ? 0 : 0 - AndroidUtilities.dp(3.0f)) + (this.drawPinnedTop ? 0 : AndroidUtilities.dp(1.0f));
        return (this.mediaBackground || !this.drawPinnedTop) ? dp : dp - AndroidUtilities.dp(1.0f);
    }

    public int getBackgroundDrawableBottom() {
        int i;
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        int i2 = 0;
        if (groupedMessagePosition != null) {
            i = (groupedMessagePosition.flags & 4) == 0 ? AndroidUtilities.dp(3.0f) + 0 : 0;
            if ((this.currentPosition.flags & 8) == 0) {
                i += AndroidUtilities.dp(this.currentMessageObject.isOutOwner() ? 3 : 4);
            }
        } else {
            i = 0;
        }
        boolean z = this.drawPinnedBottom;
        if (!z || !this.drawPinnedTop) {
            if (z) {
                i2 = AndroidUtilities.dp(1.0f);
            } else {
                i2 = AndroidUtilities.dp(2.0f);
            }
        }
        int backgroundDrawableTop = ((getBackgroundDrawableTop() + this.layoutHeight) - i2) + i;
        if (this.mediaBackground) {
            return backgroundDrawableTop;
        }
        if (this.drawPinnedTop) {
            backgroundDrawableTop += AndroidUtilities.dp(1.0f);
        }
        return this.drawPinnedBottom ? backgroundDrawableTop + AndroidUtilities.dp(1.0f) : backgroundDrawableTop;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawBackground(android.graphics.Canvas r16, int r17, int r18, int r19, int r20, boolean r21, boolean r22, boolean r23, int r24) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawBackground(android.graphics.Canvas, int, int, int, int, boolean, boolean, boolean, int):void");
    }

    public boolean hasNameLayout() {
        if (this.drawNameLayout && this.nameLayout != null) {
            return true;
        }
        if (this.drawForwardedName) {
            StaticLayout[] staticLayoutArr = this.forwardedNameLayout;
            if (staticLayoutArr[0] != null && staticLayoutArr[1] != null) {
                MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
                if (groupedMessagePosition == null) {
                    return true;
                }
                if (groupedMessagePosition.minY == 0 && groupedMessagePosition.minX == 0) {
                    return true;
                }
            }
        }
        return this.replyNameLayout != null || this.drawTopic;
    }

    public boolean isDrawNameLayout() {
        return this.drawNameLayout && this.nameLayout != null;
    }

    public boolean isDrawTopic() {
        return this.drawTopic;
    }

    public float getDrawTopicHeight() {
        if (this.topicButton != null) {
            return r0.height();
        }
        return 0.0f;
    }

    public boolean isAdminLayoutChanged() {
        return !TextUtils.equals(this.lastPostAuthor, this.currentMessageObject.messageOwner.post_author);
    }

    /* JADX WARN: Removed duplicated region for block: B:151:0x08a0  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x08fe  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0905  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x09ea  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0a0b  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0ac8  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0a63  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0a73  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0ad4  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x13cc  */
    /* JADX WARN: Removed duplicated region for block: B:378:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:599:0x0505  */
    /* JADX WARN: Removed duplicated region for block: B:600:0x04dd  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x04db  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0513  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawNamesLayout(android.graphics.Canvas r33, float r34) {
        /*
            Method dump skipped, instructions count: 5072
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawNamesLayout(android.graphics.Canvas, float):void");
    }

    public boolean hasCaptionLayout() {
        return this.captionLayout != null;
    }

    public boolean hasCommentLayout() {
        return this.drawCommentButton;
    }

    public StaticLayout getCaptionLayout() {
        return this.captionLayout;
    }

    public void setDrawSelectionBackground(boolean z) {
        if (this.drawSelectionBackground != z) {
            this.drawSelectionBackground = z;
            invalidate();
        }
    }

    public boolean isDrawingSelectionBackground() {
        return this.drawSelectionBackground || this.isHighlightedAnimated || this.isHighlighted;
    }

    public float getHighlightAlpha() {
        int i;
        if (this.drawSelectionBackground || !this.isHighlightedAnimated || (i = this.highlightProgress) >= 300) {
            return 1.0f;
        }
        return i / 300.0f;
    }

    public void setCheckBoxVisible(boolean z, boolean z2) {
        MessageObject.GroupedMessages groupedMessages;
        MessageObject.GroupedMessages groupedMessages2;
        if (z) {
            CheckBoxBase checkBoxBase = this.checkBox;
            if (checkBoxBase == null) {
                CheckBoxBase checkBoxBase2 = new CheckBoxBase(this, 21, this.resourcesProvider);
                this.checkBox = checkBoxBase2;
                if (this.attachedToWindow) {
                    checkBoxBase2.onAttachedToWindow();
                }
            } else {
                checkBoxBase.setResourcesProvider(this.resourcesProvider);
            }
        }
        if (z && (((groupedMessages = this.currentMessagesGroup) != null && groupedMessages.messages.size() > 1) || ((groupedMessages2 = this.groupedMessagesToSet) != null && groupedMessages2.messages.size() > 1))) {
            CheckBoxBase checkBoxBase3 = this.mediaCheckBox;
            if (checkBoxBase3 == null) {
                CheckBoxBase checkBoxBase4 = new CheckBoxBase(this, 21, this.resourcesProvider);
                this.mediaCheckBox = checkBoxBase4;
                checkBoxBase4.setUseDefaultCheck(true);
                if (this.attachedToWindow) {
                    this.mediaCheckBox.onAttachedToWindow();
                }
            } else {
                checkBoxBase3.setResourcesProvider(this.resourcesProvider);
            }
        }
        if (this.checkBoxVisible == z) {
            if (z2 == this.checkBoxAnimationInProgress || z2) {
                return;
            }
            this.checkBoxAnimationProgress = z ? 1.0f : 0.0f;
            invalidate();
            return;
        }
        this.checkBoxAnimationInProgress = z2;
        this.checkBoxVisible = z;
        if (z2) {
            this.lastCheckBoxAnimationTime = SystemClock.elapsedRealtime();
        } else {
            this.checkBoxAnimationProgress = z ? 1.0f : 0.0f;
        }
        invalidate();
    }

    public void setChecked(boolean z, boolean z2, boolean z3) {
        CheckBoxBase checkBoxBase = this.checkBox;
        if (checkBoxBase != null) {
            checkBoxBase.setChecked(z2, z3);
        }
        CheckBoxBase checkBoxBase2 = this.mediaCheckBox;
        if (checkBoxBase2 != null) {
            checkBoxBase2.setChecked(z, z3);
        }
        this.backgroundDrawable.setSelected(z2, z3);
    }

    public void setLastTouchCoords(float f, float f2) {
        this.lastTouchX = f;
        this.lastTouchY = f2;
        this.backgroundDrawable.setTouchCoords(f + getTranslationX(), this.lastTouchY);
    }

    public MessageBackgroundDrawable getBackgroundDrawable() {
        return this.backgroundDrawable;
    }

    public Theme.MessageDrawable getCurrentBackgroundDrawable(boolean z) {
        if (z) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            boolean z2 = groupedMessagePosition != null && (groupedMessagePosition.flags & 8) == 0 && this.currentMessagesGroup.isDocuments && !this.drawPinnedBottom;
            if (this.currentMessageObject.isOutOwner()) {
                if (!this.mediaBackground && !this.drawPinnedBottom && !z2) {
                    this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOut");
                } else {
                    this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOutMedia");
                }
            } else if (!this.mediaBackground && !this.drawPinnedBottom && !z2) {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgIn");
            } else {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgInMedia");
            }
        }
        this.currentBackgroundDrawable.getBackgroundDrawable();
        return this.currentBackgroundDrawable;
    }

    private boolean shouldDrawCaptionLayout() {
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        MessageObject.GroupedMessages groupedMessages;
        MessageObject messageObject = this.currentMessageObject;
        return (messageObject.preview || ((groupedMessagePosition = this.currentPosition) != null && ((groupedMessages = this.currentMessagesGroup) == null || !groupedMessages.isDocuments || (groupedMessagePosition.flags & 8) != 0)) || this.transitionParams.animateBackgroundBoundsInner || (this.enterTransitionInProgress && messageObject.isVoice())) ? false : true;
    }

    public void drawCaptionLayout(Canvas canvas, boolean z, float f) {
        if (this.animatedEmojiStack != null && (this.captionLayout != null || this.transitionParams.animateOutCaptionLayout != null)) {
            this.animatedEmojiStack.clearPositions();
        }
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateReplaceCaptionLayout || transitionParams.animateChangeProgress == 1.0f) {
            drawCaptionLayout(canvas, this.captionLayout, z, f);
        } else {
            drawCaptionLayout(canvas, transitionParams.animateOutCaptionLayout, z, (1.0f - this.transitionParams.animateChangeProgress) * f);
            drawCaptionLayout(canvas, this.captionLayout, z, this.transitionParams.animateChangeProgress * f);
        }
        if (!z) {
            drawAnimatedEmojiCaption(canvas, f);
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.messageOwner != null && messageObject.isVoiceTranscriptionOpen()) {
            MessageObject messageObject2 = this.currentMessageObject;
            if (!messageObject2.messageOwner.voiceTranscriptionFinal && TranscribeButton.isTranscribing(messageObject2)) {
                invalidate();
            }
        }
        if (this.isRoundVideo) {
            this.reactionsLayoutInBubble.drawServiceShaderBackground = 1.0f - getVideoTranscriptionProgress();
        }
        if (z) {
            return;
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i = groupedMessagePosition.flags;
            if ((i & 8) == 0 || (i & 1) == 0) {
                return;
            }
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble.isSmall) {
            return;
        }
        if (reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f) {
            applyServiceShaderMatrix();
        }
        if (getAlpha() != 1.0f) {
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(0.0f, 0.0f, getWidth(), getHeight());
            canvas.saveLayerAlpha(rectF, (int) (getAlpha() * 255.0f), 31);
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble2.drawServiceShaderBackground > 0.0f || !this.transitionParams.animateBackgroundBoundsInner || this.currentPosition != null || this.isRoundVideo) {
            TransitionParams transitionParams2 = this.transitionParams;
            reactionsLayoutInBubble2.draw(canvas, transitionParams2.animateChange ? transitionParams2.animateChangeProgress : 1.0f, null);
        } else {
            canvas.save();
            canvas.clipRect(0.0f, 0.0f, getMeasuredWidth(), getBackgroundDrawableBottom() + this.transitionParams.deltaBottom);
            ReactionsLayoutInBubble reactionsLayoutInBubble3 = this.reactionsLayoutInBubble;
            TransitionParams transitionParams3 = this.transitionParams;
            reactionsLayoutInBubble3.draw(canvas, transitionParams3.animateChange ? transitionParams3.animateChangeProgress : 1.0f, null);
            canvas.restore();
        }
        if (getAlpha() != 1.0f) {
            canvas.restore();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03ef  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x04f7  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0439  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0460  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03e1  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0578  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x05f4  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0613  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0617  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x06cf  */
    /* JADX WARN: Removed duplicated region for block: B:395:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:400:0x062c  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x05f9  */
    /* JADX WARN: Removed duplicated region for block: B:406:0x05e5  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x05b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawCaptionLayout(android.graphics.Canvas r28, android.text.StaticLayout r29, boolean r30, float r31) {
        /*
            Method dump skipped, instructions count: 2857
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawCaptionLayout(android.graphics.Canvas, android.text.StaticLayout, boolean, float):void");
    }

    public void drawProgressLoadingLink(Canvas canvas, int i) {
        updateProgressLoadingLink();
        ArrayList<LoadingDrawableLocation> arrayList = this.progressLoadingLinkDrawables;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        int themedColor = getThemedColor((messageObject == null || !messageObject.isOutOwner()) ? Theme.key_chat_linkSelectBackground : Theme.key_chat_outLinkSelectBackground);
        int i2 = 0;
        while (i2 < this.progressLoadingLinkDrawables.size()) {
            LoadingDrawableLocation loadingDrawableLocation = this.progressLoadingLinkDrawables.get(i2);
            if (loadingDrawableLocation.blockNum == i) {
                LoadingDrawable loadingDrawable = loadingDrawableLocation.drawable;
                loadingDrawable.setColors(Theme.multAlpha(themedColor, 0.85f), Theme.multAlpha(themedColor, 2.0f), Theme.multAlpha(themedColor, 3.5f), Theme.multAlpha(themedColor, 6.0f));
                loadingDrawable.draw(canvas);
                invalidate();
                if (loadingDrawable.isDisappeared()) {
                    this.progressLoadingLinkDrawables.remove(i2);
                    i2--;
                }
            }
            i2++;
        }
    }

    public void updateProgressLoadingLink() {
        StaticLayout staticLayout;
        ArrayList<MessageObject.TextLayoutBlock> arrayList;
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate == null) {
            return;
        }
        if (!chatMessageCellDelegate.isProgressLoading(this, 1)) {
            this.progressLoadingLink = null;
            ArrayList<LoadingDrawableLocation> arrayList2 = this.progressLoadingLinkDrawables;
            if (arrayList2 == null || arrayList2.isEmpty()) {
                return;
            }
            for (int i = 0; i < this.progressLoadingLinkDrawables.size(); i++) {
                LoadingDrawableLocation loadingDrawableLocation = this.progressLoadingLinkDrawables.get(i);
                if (!loadingDrawableLocation.drawable.isDisappearing()) {
                    loadingDrawableLocation.drawable.disappear();
                }
            }
            return;
        }
        CharacterStyle progressLoadingLink = this.delegate.getProgressLoadingLink(this);
        if (progressLoadingLink == this.progressLoadingLink) {
            return;
        }
        this.progressLoadingLink = progressLoadingLink;
        LoadingDrawable loadingDrawable = this.progressLoadingLinkCurrentDrawable;
        if (loadingDrawable != null) {
            loadingDrawable.disappear();
            this.progressLoadingLinkCurrentDrawable = null;
        }
        LoadingDrawable loadingDrawable2 = new LoadingDrawable();
        this.progressLoadingLinkCurrentDrawable = loadingDrawable2;
        loadingDrawable2.setAppearByGradient(true);
        LinkPath linkPath = new LinkPath(true);
        this.progressLoadingLinkCurrentDrawable.usePath(linkPath);
        this.progressLoadingLinkCurrentDrawable.setRadiiDp(5.0f);
        LoadingDrawableLocation loadingDrawableLocation2 = new LoadingDrawableLocation(this);
        loadingDrawableLocation2.drawable = this.progressLoadingLinkCurrentDrawable;
        loadingDrawableLocation2.blockNum = -3;
        if (this.progressLoadingLinkDrawables == null) {
            this.progressLoadingLinkDrawables = new ArrayList<>();
        }
        this.progressLoadingLinkDrawables.add(loadingDrawableLocation2);
        if (this.progressLoadingLink != null) {
            MessageObject messageObject = this.currentMessageObject;
            int max = Math.max(0, (messageObject == null || (arrayList = messageObject.textLayoutBlocks) == null) ? 0 : arrayList.size());
            for (int i2 = -2; i2 < max; i2++) {
                float f = 0.0f;
                if (i2 == -2) {
                    staticLayout = this.descriptionLayout;
                } else if (i2 == -1) {
                    staticLayout = this.captionLayout;
                } else {
                    StaticLayout staticLayout2 = this.currentMessageObject.textLayoutBlocks.get(i2).textLayout;
                    f = this.currentMessageObject.textLayoutBlocks.get(i2).textYOffset;
                    staticLayout = staticLayout2;
                }
                if (staticLayout != null && (staticLayout.getText() instanceof Spanned)) {
                    Spanned spanned = (Spanned) staticLayout.getText();
                    CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(0, spanned.length(), CharacterStyle.class);
                    if (characterStyleArr != null) {
                        int i3 = 0;
                        while (true) {
                            if (i3 >= characterStyleArr.length) {
                                break;
                            }
                            if (characterStyleArr[i3] == this.progressLoadingLink) {
                                loadingDrawableLocation2.blockNum = i2;
                                break;
                            }
                            i3++;
                        }
                    }
                    if (loadingDrawableLocation2.blockNum == i2) {
                        linkPath.rewind();
                        int spanStart = spanned.getSpanStart(this.progressLoadingLink);
                        int spanEnd = spanned.getSpanEnd(this.progressLoadingLink);
                        linkPath.setCurrentLayout(staticLayout, spanStart, f);
                        staticLayout.getSelectionPath(spanStart, spanEnd, linkPath);
                        this.progressLoadingLinkCurrentDrawable.updateBounds();
                        return;
                    }
                }
            }
        }
    }

    public boolean needDrawTime() {
        return !this.forceNotDrawTime;
    }

    public boolean shouldDrawTimeOnMedia() {
        int i = this.overideShouldDrawTimeOnMedia;
        if (i != 0) {
            return i == 1;
        }
        if (!this.mediaBackground || this.captionLayout != null) {
            return false;
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        return reactionsLayoutInBubble.isEmpty || reactionsLayoutInBubble.isSmall || this.currentMessageObject.isAnyKindOfSticker() || this.currentMessageObject.isRoundVideo();
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0126  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawTime(android.graphics.Canvas r17, float r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawTime(android.graphics.Canvas, float, boolean):void");
    }

    private void drawTimeInternal(Canvas canvas, float f, boolean z, float f2, StaticLayout staticLayout, float f3, boolean z2) {
        int i;
        float f4;
        float f5;
        String str;
        char c;
        char c2;
        int i2;
        int i3;
        boolean z3;
        float f6;
        char c3;
        float dp;
        boolean z4;
        Paint themedPaint;
        float f7;
        int dp2;
        boolean z5;
        float imageY2;
        int i4;
        float f8;
        int i5;
        int i6;
        float f9;
        float currentWidth;
        int i7;
        if (((!this.drawTime || this.groupPhotoInvisible) && shouldDrawTimeOnMedia()) || staticLayout == null) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if ((!messageObject.deleted || this.currentPosition == null) && (i = messageObject.type) != 16) {
            if (i == 5) {
                TextPaint textPaint = Theme.chat_timePaint;
                int themedColor = getThemedColor(Theme.key_chat_serviceText);
                if (isDrawSelectionBackground()) {
                    i7 = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_inTimeSelectedText;
                } else {
                    i7 = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeText : Theme.key_chat_inTimeText;
                }
                textPaint.setColor(ColorUtils.blendARGB(themedColor, getThemedColor(i7), getVideoTranscriptionProgress()));
            } else if (shouldDrawTimeOnMedia()) {
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_serviceText));
                } else {
                    Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_mediaTimeText));
                }
            } else if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_timePaint.setColor(getThemedColor(z2 ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
            } else {
                Theme.chat_timePaint.setColor(getThemedColor(z2 ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
            }
            float f10 = getTransitionParams().animateDrawingTimeAlpha ? getTransitionParams().animateChangeProgress * f : f;
            if (f10 != 1.0f) {
                Theme.chat_timePaint.setAlpha((int) (r0.getAlpha() * f10));
            }
            canvas.save();
            if (this.drawPinnedBottom && !shouldDrawTimeOnMedia()) {
                canvas.translate(0.0f, AndroidUtilities.dp(2.0f));
            }
            float f11 = this.layoutHeight;
            TransitionParams transitionParams = this.transitionParams;
            if (transitionParams.animateBackgroundBoundsInner) {
                f11 += transitionParams.deltaBottom;
            }
            if (transitionParams.shouldAnimateTimeX) {
                float f12 = transitionParams.animateFromTimeX;
                float f13 = transitionParams.animateChangeProgress;
                f4 = (f12 * (1.0f - f13)) + (this.timeX * f13);
            } else {
                f4 = f2;
            }
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages == null || !groupedMessages.transitionParams.backgroundChangeBounds) {
                f5 = f2;
            } else {
                f11 -= getTranslationY();
                float f14 = this.currentMessagesGroup.transitionParams.offsetRight;
                f5 = f2 + f14;
                f4 += f14;
            }
            if (this.drawPinnedBottom && shouldDrawTimeOnMedia()) {
                f11 += AndroidUtilities.dp(1.0f);
            }
            float f15 = f11;
            TransitionParams transitionParams2 = this.transitionParams;
            boolean z6 = transitionParams2.animateBackgroundBoundsInner;
            if (z6) {
                float f16 = this.animationOffsetX;
                f5 += f16;
                f4 += f16;
            }
            float f17 = f5;
            ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
            if (reactionsLayoutInBubble.isSmall) {
                if (z6 && transitionParams2.deltaRight != 0.0f) {
                    currentWidth = reactionsLayoutInBubble.getCurrentWidth(1.0f);
                } else {
                    currentWidth = reactionsLayoutInBubble.getCurrentWidth(transitionParams2.animateChangeProgress);
                }
                f4 += currentWidth;
            }
            if (this.transitionParams.animateEditedEnter) {
                f4 -= this.transitionParams.animateEditedWidthDiff * (1.0f - this.transitionParams.animateChangeProgress);
            }
            float f18 = f4;
            if (shouldDrawTimeOnMedia()) {
                int i8 = -(this.drawCommentButton ? AndroidUtilities.dp(41.3f) : 0);
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    themedPaint = getThemedPaint("paintChatActionBackground");
                } else {
                    themedPaint = getThemedPaint("paintChatTimeBackground");
                }
                int alpha = themedPaint.getAlpha();
                Theme.chat_timePaint.setAlpha((int) (this.timeAlpha * 255.0f * f10));
                MessageObject messageObject2 = this.currentMessageObject;
                if (messageObject2 == null || messageObject2.type != 4) {
                    f7 = f10;
                } else {
                    float currentAlpha = this.photoImage.isCrossfadingWithOldImage() ? 1.0f : this.photoImage.getCurrentAlpha();
                    if (!this.photoImage.hasNotThumb()) {
                        currentAlpha = 0.0f;
                    }
                    f7 = AndroidUtilities.lerp(0.35f, 1.0f, currentAlpha);
                }
                themedPaint.setAlpha((int) (alpha * this.timeAlpha * f7));
                int i9 = this.documentAttachType;
                if (i9 != 7 && i9 != 6 && this.currentMessageObject.type != 19) {
                    int[] roundRadius = this.photoImage.getRoundRadius();
                    dp2 = Math.min(AndroidUtilities.dp(8.0f), Math.max(roundRadius[2], roundRadius[3]));
                    z5 = SharedConfig.bubbleRadius >= 10;
                } else {
                    dp2 = AndroidUtilities.dp(4.0f);
                    z5 = false;
                }
                float dp3 = f17 - AndroidUtilities.dp(z5 ? 6.0f : 4.0f);
                if (this.documentAttachType == 7) {
                    imageY2 = f15 - ((AndroidUtilities.dp(this.drawPinnedBottom ? 4.0f : 5.0f) + this.reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress)) * (1.0f - getVideoTranscriptionProgress()));
                } else {
                    imageY2 = this.photoImage.getImageY2() + this.additionalTimeOffsetY;
                }
                float f19 = imageY2;
                float dp4 = f19 - AndroidUtilities.dp(23.0f);
                float max = Math.max(AndroidUtilities.dp(17.0f), Theme.chat_timePaint.getTextSize() + AndroidUtilities.dp(5.0f));
                RectF rectF = this.rect;
                float f20 = dp3 + f3;
                if (z5) {
                    i4 = 12;
                    z3 = z5;
                } else {
                    z3 = z5;
                    i4 = 8;
                }
                if (this.currentMessageObject.isOutOwner()) {
                    f8 = f19;
                    i5 = (this.currentMessageObject.type == 19 ? 4 : 0) + 20;
                } else {
                    f8 = f19;
                    i5 = 0;
                }
                rectF.set(dp3, dp4, f20 + AndroidUtilities.dp(i4 + i5), max + dp4);
                applyServiceShaderMatrix();
                float f21 = dp2;
                canvas.drawRoundRect(this.rect, f21, f21, themedPaint);
                if (themedPaint == getThemedPaint("paintChatActionBackground") && hasGradientService()) {
                    int alpha2 = Theme.chat_actionBackgroundGradientDarkenPaint.getAlpha();
                    Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha((int) (alpha2 * this.timeAlpha * f7));
                    canvas.drawRoundRect(this.rect, f21, f21, Theme.chat_actionBackgroundGradientDarkenPaint);
                    Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha(alpha2);
                }
                themedPaint.setAlpha(alpha);
                float f22 = -staticLayout.getLineLeft(0);
                if (this.reactionsLayoutInBubble.isSmall) {
                    updateReactionLayoutPosition();
                    this.reactionsLayoutInBubble.draw(canvas, this.transitionParams.animateChangeProgress, null);
                }
                if ((!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup) && (this.currentMessageObject.messageOwner.flags & 1024) == 0 && this.repliesLayout == null && !this.isPinned) {
                    i6 = i8;
                    str = "paintChatTimeBackground";
                    c = 4;
                    c2 = 7;
                } else {
                    float lineWidth = f22 + (this.timeWidth - staticLayout.getLineWidth(0));
                    ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
                    if (reactionsLayoutInBubble2.isSmall && !reactionsLayoutInBubble2.isEmpty) {
                        lineWidth -= reactionsLayoutInBubble2.width;
                    }
                    float f23 = lineWidth;
                    int createStatusDrawableParams = this.transitionParams.createStatusDrawableParams();
                    int i10 = this.transitionParams.lastStatusDrawableParams;
                    if (i10 >= 0 && i10 != createStatusDrawableParams && !this.statusDrawableAnimationInProgress) {
                        createStatusDrawableAnimator(i10, createStatusDrawableParams, z);
                    }
                    boolean z7 = this.statusDrawableAnimationInProgress;
                    if (z7) {
                        createStatusDrawableParams = this.animateToStatusDrawableParams;
                    }
                    boolean z8 = (createStatusDrawableParams & 4) != 0;
                    boolean z9 = (createStatusDrawableParams & 8) != 0;
                    if (z7) {
                        int i11 = this.animateFromStatusDrawableParams;
                        boolean z10 = (i11 & 4) != 0;
                        boolean z11 = (i11 & 8) != 0;
                        float f24 = i8;
                        c = 4;
                        float f25 = f10;
                        f9 = f23;
                        i6 = i8;
                        c2 = 7;
                        str = "paintChatTimeBackground";
                        drawClockOrErrorLayout(canvas, z10, z11, f15, f25, f24, f17, 1.0f - this.statusDrawableProgress, z2);
                        drawClockOrErrorLayout(canvas, z8, z9, f15, f25, f24, f17, this.statusDrawableProgress, z2);
                        if (!this.currentMessageObject.isOutOwner()) {
                            if (!z10 && !z11) {
                                drawViewsAndRepliesLayout(canvas, f15, f10, f24, f17, 1.0f - this.statusDrawableProgress, z2);
                            }
                            if (!z8 && !z9) {
                                drawViewsAndRepliesLayout(canvas, f15, f10, f24, f17, this.statusDrawableProgress, z2);
                            }
                        }
                    } else {
                        f9 = f23;
                        str = "paintChatTimeBackground";
                        c = 4;
                        c2 = 7;
                        i6 = i8;
                        if (!this.currentMessageObject.isOutOwner() && !z8 && !z9) {
                            drawViewsAndRepliesLayout(canvas, f15, f10, i6, f17, 1.0f, z2);
                        }
                        drawClockOrErrorLayout(canvas, z8, z9, f15, f10, i6, f17, 1.0f, z2);
                    }
                    if (this.currentMessageObject.isOutOwner()) {
                        drawViewsAndRepliesLayout(canvas, f15, f10, i6, f17, 1.0f, z2);
                    }
                    TransitionParams transitionParams3 = this.transitionParams;
                    transitionParams3.lastStatusDrawableParams = transitionParams3.createStatusDrawableParams();
                    if (z8 && z && getParent() != null) {
                        ((View) getParent()).invalidate();
                    }
                    f22 = f9;
                }
                canvas.save();
                float f26 = f18 + f22;
                this.drawTimeX = f26;
                float dp5 = (f8 - AndroidUtilities.dp(7.3f)) - staticLayout.getHeight();
                this.drawTimeY = dp5;
                canvas.translate(f26, dp5);
                staticLayout.draw(canvas);
                canvas.restore();
                Theme.chat_timePaint.setAlpha(255);
                i3 = i6;
            } else {
                str = "paintChatTimeBackground";
                c = 4;
                c2 = 7;
                if (this.currentMessageObject.isSponsored()) {
                    i2 = -AndroidUtilities.dp(48.0f);
                    if (this.hasNewLineForTime) {
                        i2 -= AndroidUtilities.dp(16.0f);
                    }
                } else {
                    i2 = -(this.drawCommentButton ? AndroidUtilities.dp(43.0f) : 0);
                }
                int i12 = i2;
                float f27 = -staticLayout.getLineLeft(0);
                if (this.reactionsLayoutInBubble.isSmall) {
                    updateReactionLayoutPosition();
                    this.reactionsLayoutInBubble.draw(canvas, this.transitionParams.animateChangeProgress, null);
                }
                if ((ChatObject.isChannel(this.currentChat) && !this.currentChat.megagroup) || (this.currentMessageObject.messageOwner.flags & 1024) != 0 || this.repliesLayout != null || this.transitionParams.animateReplies || this.isPinned || this.transitionParams.animatePinned) {
                    float lineWidth2 = f27 + (f3 - staticLayout.getLineWidth(0));
                    ReactionsLayoutInBubble reactionsLayoutInBubble3 = this.reactionsLayoutInBubble;
                    if (reactionsLayoutInBubble3.isSmall && !reactionsLayoutInBubble3.isEmpty) {
                        lineWidth2 -= reactionsLayoutInBubble3.width;
                    }
                    float f28 = lineWidth2;
                    int createStatusDrawableParams2 = this.transitionParams.createStatusDrawableParams();
                    int i13 = this.transitionParams.lastStatusDrawableParams;
                    if (i13 >= 0 && i13 != createStatusDrawableParams2 && !this.statusDrawableAnimationInProgress) {
                        createStatusDrawableAnimator(i13, createStatusDrawableParams2, z);
                    }
                    boolean z12 = this.statusDrawableAnimationInProgress;
                    if (z12) {
                        createStatusDrawableParams2 = this.animateToStatusDrawableParams;
                    }
                    boolean z13 = (createStatusDrawableParams2 & 4) != 0;
                    boolean z14 = (createStatusDrawableParams2 & 8) != 0;
                    if (z12) {
                        int i14 = this.animateFromStatusDrawableParams;
                        boolean z15 = (i14 & 4) != 0;
                        boolean z16 = (i14 & 8) != 0;
                        float f29 = i12;
                        float f30 = f10;
                        drawClockOrErrorLayout(canvas, z15, z16, f15, f30, f29, f17, 1.0f - this.statusDrawableProgress, z2);
                        drawClockOrErrorLayout(canvas, z13, z14, f15, f30, f29, f17, this.statusDrawableProgress, z2);
                        if (!this.currentMessageObject.isOutOwner()) {
                            if (!z15 && !z16) {
                                drawViewsAndRepliesLayout(canvas, f15, f10, f29, f17, 1.0f - this.statusDrawableProgress, z2);
                            }
                            if (!z13 && !z14) {
                                drawViewsAndRepliesLayout(canvas, f15, f10, f29, f17, this.statusDrawableProgress, z2);
                            }
                        }
                    } else {
                        if (!this.currentMessageObject.isOutOwner() && !z13 && !z14) {
                            drawViewsAndRepliesLayout(canvas, f15, f10, i12, f17, 1.0f, z2);
                        }
                        drawClockOrErrorLayout(canvas, z13, z14, f15, f10, i12, f17, 1.0f, z2);
                    }
                    if (this.currentMessageObject.isOutOwner()) {
                        drawViewsAndRepliesLayout(canvas, f15, f10, i12, f17, 1.0f, z2);
                    }
                    TransitionParams transitionParams4 = this.transitionParams;
                    transitionParams4.lastStatusDrawableParams = transitionParams4.createStatusDrawableParams();
                    if (z13 && z && getParent() != null) {
                        ((View) getParent()).invalidate();
                    }
                    f27 = f28;
                }
                canvas.save();
                if (this.transitionParams.animateEditedEnter) {
                    TransitionParams transitionParams5 = this.transitionParams;
                    if (transitionParams5.animateChangeProgress != 1.0f) {
                        if (transitionParams5.animateEditedLayout != null) {
                            canvas.translate(f18 + f27, ((f15 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 7.5f : 6.5f)) - staticLayout.getHeight()) + i12);
                            int alpha3 = Theme.chat_timePaint.getAlpha();
                            Theme.chat_timePaint.setAlpha((int) (alpha3 * this.transitionParams.animateChangeProgress));
                            this.transitionParams.animateEditedLayout.draw(canvas);
                            Theme.chat_timePaint.setAlpha(alpha3);
                            this.transitionParams.animateTimeLayout.draw(canvas);
                        } else {
                            int alpha4 = Theme.chat_timePaint.getAlpha();
                            canvas.save();
                            float f31 = i12;
                            canvas.translate(this.transitionParams.animateFromTimeX + f27, ((f15 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 7.5f : 6.5f)) - staticLayout.getHeight()) + f31);
                            float f32 = alpha4;
                            Theme.chat_timePaint.setAlpha((int) ((1.0f - this.transitionParams.animateChangeProgress) * f32));
                            this.transitionParams.animateTimeLayout.draw(canvas);
                            canvas.restore();
                            canvas.translate(f18 + f27, ((f15 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 7.5f : 6.5f)) - staticLayout.getHeight()) + f31);
                            Theme.chat_timePaint.setAlpha((int) (f32 * this.transitionParams.animateChangeProgress));
                            staticLayout.draw(canvas);
                            Theme.chat_timePaint.setAlpha(alpha4);
                        }
                        canvas.restore();
                        i3 = i12;
                        z3 = false;
                    }
                }
                float f33 = f18 + f27;
                this.drawTimeX = f33;
                float dp6 = ((f15 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 7.5f : 6.5f)) - staticLayout.getHeight()) + i12;
                this.drawTimeY = dp6;
                canvas.translate(f33, dp6);
                staticLayout.draw(canvas);
                canvas.restore();
                i3 = i12;
                z3 = false;
            }
            if (this.currentMessageObject.isOutOwner()) {
                int createStatusDrawableParams3 = this.transitionParams.createStatusDrawableParams();
                int i15 = this.transitionParams.lastStatusDrawableParams;
                if (i15 >= 0 && i15 != createStatusDrawableParams3 && !this.statusDrawableAnimationInProgress) {
                    createStatusDrawableAnimator(i15, createStatusDrawableParams3, z);
                }
                if (this.statusDrawableAnimationInProgress) {
                    createStatusDrawableParams3 = this.animateToStatusDrawableParams;
                }
                boolean z17 = (createStatusDrawableParams3 & 1) != 0;
                boolean z18 = (createStatusDrawableParams3 & 2) != 0;
                boolean z19 = (createStatusDrawableParams3 & 4) != 0;
                boolean z20 = (createStatusDrawableParams3 & 8) != 0;
                if (this.transitionYOffsetForDrawables != 0.0f) {
                    canvas.save();
                    canvas.translate(0.0f, this.transitionYOffsetForDrawables);
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (this.statusDrawableAnimationInProgress) {
                    int i16 = this.animateFromStatusDrawableParams;
                    boolean z21 = (i16 & 1) != 0;
                    boolean z22 = (i16 & 2) != 0;
                    boolean z23 = (i16 & 4) != 0;
                    boolean z24 = (i16 & 8) != 0;
                    if (!z23 && z22 && z18 && !z21 && z17) {
                        f6 = 0.0f;
                        c3 = 5;
                        drawStatusDrawable(canvas, z17, z18, z19, z20, f10, z3, i3, f15, this.statusDrawableProgress, true, z2);
                    } else {
                        f6 = 0.0f;
                        c3 = 5;
                        float f34 = i3;
                        boolean z25 = z22;
                        boolean z26 = z23;
                        boolean z27 = z24;
                        float f35 = f10;
                        boolean z28 = z3;
                        drawStatusDrawable(canvas, z21, z25, z26, z27, f35, z28, f34, f15, 1.0f - this.statusDrawableProgress, false, z2);
                        drawStatusDrawable(canvas, z17, z18, z19, z20, f35, z28, f34, f15, this.statusDrawableProgress, false, z2);
                    }
                } else {
                    f6 = 0.0f;
                    c3 = 5;
                    drawStatusDrawable(canvas, z17, z18, z19, z20, f10, z3, i3, f15, 1.0f, false, z2);
                }
                if (z4) {
                    canvas.restore();
                }
                TransitionParams transitionParams6 = this.transitionParams;
                transitionParams6.lastStatusDrawableParams = transitionParams6.createStatusDrawableParams();
                if (z && z19 && getParent() != null) {
                    ((View) getParent()).invalidate();
                }
            } else {
                f6 = 0.0f;
                c3 = 5;
            }
            canvas.restore();
            if (this.unlockLayout != null) {
                if (this.unlockX == f6 || this.unlockY == f6) {
                    calculateUnlockXY();
                }
                this.unlockSpoilerPath.rewind();
                RectF rectF2 = AndroidUtilities.rectTmp;
                rectF2.set(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageX2(), this.photoImage.getImageY2());
                int[] roundRadius2 = this.photoImage.getRoundRadius();
                float[] fArr = this.unlockSpoilerRadii;
                float f36 = roundRadius2[0];
                fArr[1] = f36;
                fArr[0] = f36;
                float f37 = roundRadius2[1];
                fArr[3] = f37;
                fArr[2] = f37;
                float f38 = roundRadius2[2];
                fArr[c3] = f38;
                fArr[c] = f38;
                float f39 = roundRadius2[3];
                fArr[c2] = f39;
                fArr[6] = f39;
                this.unlockSpoilerPath.addRoundRect(rectF2, fArr, Path.Direction.CW);
                canvas.save();
                canvas.clipPath(this.unlockSpoilerPath);
                this.unlockSpoilerPath.rewind();
                rectF2.set(this.unlockX - AndroidUtilities.dp(12.0f), this.unlockY - AndroidUtilities.dp(8.0f), this.unlockX + Theme.chat_msgUnlockDrawable.getIntrinsicWidth() + AndroidUtilities.dp(14.0f) + this.unlockLayout.getWidth() + AndroidUtilities.dp(12.0f), this.unlockY + this.unlockLayout.getHeight() + AndroidUtilities.dp(8.0f));
                this.unlockSpoilerPath.addRoundRect(rectF2, AndroidUtilities.dp(32.0f), AndroidUtilities.dp(32.0f), Path.Direction.CW);
                canvas.clipPath(this.unlockSpoilerPath, Region.Op.DIFFERENCE);
                this.unlockSpoilerEffect.setColor(ColorUtils.setAlphaComponent(-1, (int) (Color.alpha(-1) * 0.325f)));
                this.unlockSpoilerEffect.setBounds((int) this.photoImage.getImageX(), (int) this.photoImage.getImageY(), (int) this.photoImage.getImageX2(), (int) this.photoImage.getImageY2());
                this.unlockSpoilerEffect.draw(canvas);
                invalidate();
                canvas.restore();
                canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), (int) (this.unlockAlpha * 255.0f), 31);
                int alpha5 = Theme.chat_timeBackgroundPaint.getAlpha();
                Theme.chat_timeBackgroundPaint.setAlpha((int) (alpha5 * 0.7f));
                canvas.drawRoundRect(rectF2, AndroidUtilities.dp(32.0f), AndroidUtilities.dp(32.0f), Theme.chat_timeBackgroundPaint);
                Theme.chat_timeBackgroundPaint.setAlpha(alpha5);
                canvas.translate(this.unlockX + AndroidUtilities.dp(4.0f), this.unlockY);
                Drawable drawable = Theme.chat_msgUnlockDrawable;
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), Theme.chat_msgUnlockDrawable.getIntrinsicHeight());
                Theme.chat_msgUnlockDrawable.draw(canvas);
                canvas.translate(AndroidUtilities.dp(6.0f) + Theme.chat_msgUnlockDrawable.getIntrinsicWidth(), f6);
                this.unlockLayout.draw(canvas);
                canvas.restore();
                if (this.videoInfoLayout != null && this.photoImage.getVisible() && this.imageBackgroundSideColor == 0) {
                    int i17 = SharedConfig.bubbleRadius;
                    if (i17 > 2) {
                        dp = AndroidUtilities.dp(i17 - 2);
                        z3 = SharedConfig.bubbleRadius >= 10;
                    } else {
                        dp = AndroidUtilities.dp(i17);
                    }
                    int imageX = (int) (this.photoImage.getImageX() + AndroidUtilities.dp(9.0f));
                    int imageY = (int) (this.photoImage.getImageY() + AndroidUtilities.dp(6.0f));
                    this.rect.set(imageX - AndroidUtilities.dp(4.0f), imageY - AndroidUtilities.dp(1.5f), this.durationWidth + imageX + AndroidUtilities.dp(4.0f) + AndroidUtilities.dp(z3 ? 2.0f : 0.0f), this.videoInfoLayout.getHeight() + imageY + AndroidUtilities.dp(1.5f));
                    canvas.drawRoundRect(this.rect, dp, dp, getThemedPaint(str));
                    canvas.save();
                    canvas.translate(imageX + (z3 ? 2 : 0), imageY);
                    this.videoInfoLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createStatusDrawableAnimator(int i, int i2, final boolean z) {
        boolean z2 = false;
        boolean z3 = (i2 & 1) != 0;
        boolean z4 = (i2 & 2) != 0;
        boolean z5 = (i & 1) != 0;
        boolean z6 = (i & 2) != 0;
        if (!((i & 4) != 0) && z6 && z4 && !z5 && z3) {
            z2 = true;
        }
        if (!this.transitionParams.messageEntering || z2) {
            this.statusDrawableProgress = 0.0f;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.statusDrawableAnimator = ofFloat;
            if (z2) {
                ofFloat.setDuration(220L);
            } else {
                ofFloat.setDuration(150L);
            }
            this.statusDrawableAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.animateFromStatusDrawableParams = i;
            this.animateToStatusDrawableParams = i2;
            this.statusDrawableAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ChatMessageCell.this.lambda$createStatusDrawableAnimator$15(z, valueAnimator);
                }
            });
            this.statusDrawableAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.11
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    int createStatusDrawableParams = ChatMessageCell.this.transitionParams.createStatusDrawableParams();
                    if (ChatMessageCell.this.animateToStatusDrawableParams == createStatusDrawableParams) {
                        ChatMessageCell.this.statusDrawableAnimationInProgress = false;
                        ChatMessageCell.this.transitionParams.lastStatusDrawableParams = ChatMessageCell.this.animateToStatusDrawableParams;
                    } else {
                        ChatMessageCell chatMessageCell = ChatMessageCell.this;
                        chatMessageCell.createStatusDrawableAnimator(chatMessageCell.animateToStatusDrawableParams, createStatusDrawableParams, z);
                    }
                }
            });
            this.statusDrawableAnimationInProgress = true;
            this.statusDrawableAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createStatusDrawableAnimator$15(boolean z, ValueAnimator valueAnimator) {
        this.statusDrawableProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
        if (!z || getParent() == null) {
            return;
        }
        ((View) getParent()).invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawClockOrErrorLayout(android.graphics.Canvas r7, boolean r8, boolean r9, float r10, float r11, float r12, float r13, float r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawClockOrErrorLayout(android.graphics.Canvas, boolean, boolean, float, float, float, float, float, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0482  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0257  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x03cf  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0400  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0409  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x043e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawViewsAndRepliesLayout(android.graphics.Canvas r25, float r26, float r27, float r28, float r29, float r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 1214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawViewsAndRepliesLayout(android.graphics.Canvas, float, float, float, float, float, boolean):void");
    }

    private void drawStatusDrawable(Canvas canvas, boolean z, boolean z2, boolean z3, boolean z4, float f, boolean z5, float f2, float f3, float f4, boolean z6, boolean z7) {
        float imageY2;
        int dp;
        int dp2;
        Drawable themedDrawable;
        Drawable drawable;
        int i;
        boolean z8 = (f4 == 1.0f || z6) ? false : true;
        float f5 = (f4 * 0.5f) + 0.5f;
        float f6 = z8 ? f * f4 : f;
        if (this.documentAttachType == 7) {
            imageY2 = f3 - ((AndroidUtilities.dp(this.drawPinnedBottom ? 4.0f : 5.0f) + this.reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress)) * (1.0f - getVideoTranscriptionProgress()));
        } else {
            imageY2 = this.photoImage.getImageY2() + this.additionalTimeOffsetY;
        }
        float dp3 = imageY2 - AndroidUtilities.dp(8.5f);
        if (z3) {
            MsgClockDrawable msgClockDrawable = Theme.chat_msgClockDrawable;
            if (shouldDrawTimeOnMedia()) {
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    i = getThemedColor(Theme.key_chat_serviceText);
                    BaseCell.setDrawableBounds(msgClockDrawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 24.0f : 22.0f)) - msgClockDrawable.getIntrinsicWidth(), (dp3 - msgClockDrawable.getIntrinsicHeight()) + f2);
                    msgClockDrawable.setAlpha((int) (this.timeAlpha * 255.0f * f6));
                } else {
                    i = getThemedColor(Theme.key_chat_mediaSentClock);
                    BaseCell.setDrawableBounds(msgClockDrawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 24.0f : 22.0f)) - msgClockDrawable.getIntrinsicWidth(), (dp3 - msgClockDrawable.getIntrinsicHeight()) + f2);
                    msgClockDrawable.setAlpha((int) (f6 * 255.0f));
                }
            } else {
                int themedColor = getThemedColor(Theme.key_chat_outSentClock);
                BaseCell.setDrawableBounds(msgClockDrawable, (this.layoutWidth - AndroidUtilities.dp(18.5f)) - msgClockDrawable.getIntrinsicWidth(), ((f3 - AndroidUtilities.dp(8.5f)) - msgClockDrawable.getIntrinsicHeight()) + f2);
                msgClockDrawable.setAlpha((int) (f6 * 255.0f));
                i = themedColor;
            }
            msgClockDrawable.setColor(i);
            if (z8) {
                canvas.save();
                canvas.scale(f5, f5, msgClockDrawable.getBounds().centerX(), msgClockDrawable.getBounds().centerY());
            }
            msgClockDrawable.draw(canvas);
            msgClockDrawable.setAlpha(255);
            if (z8) {
                canvas.restore();
            }
            invalidate();
        }
        float f7 = 9.0f;
        if (z2) {
            if (shouldDrawTimeOnMedia()) {
                if (z6) {
                    canvas.save();
                }
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    drawable = getThemedDrawable("drawableMsgStickerCheck");
                    if (z) {
                        if (z6) {
                            canvas.translate(AndroidUtilities.dp(4.8f) * (1.0f - f4), 0.0f);
                        }
                        BaseCell.setDrawableBounds(drawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 28.3f : 26.3f)) - drawable.getIntrinsicWidth(), (dp3 - drawable.getIntrinsicHeight()) + f2);
                    } else {
                        BaseCell.setDrawableBounds(drawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 23.5f : 21.5f)) - drawable.getIntrinsicWidth(), (dp3 - drawable.getIntrinsicHeight()) + f2);
                    }
                    drawable.setAlpha((int) (this.timeAlpha * 255.0f * f6));
                } else {
                    if (z) {
                        if (z6) {
                            canvas.translate(AndroidUtilities.dp(4.8f) * (1.0f - f4), 0.0f);
                        }
                        BaseCell.setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 28.3f : 26.3f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (dp3 - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight()) + f2);
                    } else {
                        BaseCell.setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(z5 ? 23.5f : 21.5f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (dp3 - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight()) + f2);
                    }
                    Theme.chat_msgMediaCheckDrawable.setAlpha((int) (this.timeAlpha * 255.0f * f6));
                    drawable = Theme.chat_msgMediaCheckDrawable;
                }
                if (z8) {
                    canvas.save();
                    canvas.scale(f5, f5, drawable.getBounds().centerX(), drawable.getBounds().centerY());
                }
                drawable.draw(canvas);
                if (z8) {
                    canvas.restore();
                }
                if (z6) {
                    canvas.restore();
                }
                drawable.setAlpha(255);
            } else {
                if (z6) {
                    canvas.save();
                }
                if (z) {
                    if (z6) {
                        canvas.translate(AndroidUtilities.dp(4.0f) * (1.0f - f4), 0.0f);
                    }
                    themedDrawable = getThemedDrawable(z7 ? "drawableMsgOutCheckReadSelected" : "drawableMsgOutCheckRead");
                    BaseCell.setDrawableBounds(themedDrawable, (this.layoutWidth - AndroidUtilities.dp(22.5f)) - themedDrawable.getIntrinsicWidth(), ((f3 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 9.0f : 8.0f)) - themedDrawable.getIntrinsicHeight()) + f2);
                } else {
                    themedDrawable = getThemedDrawable(z7 ? "drawableMsgOutCheckSelected" : "drawableMsgOutCheck");
                    BaseCell.setDrawableBounds(themedDrawable, (this.layoutWidth - AndroidUtilities.dp(18.5f)) - themedDrawable.getIntrinsicWidth(), ((f3 - AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 9.0f : 8.0f)) - themedDrawable.getIntrinsicHeight()) + f2);
                }
                themedDrawable.setAlpha((int) (f6 * 255.0f));
                if (z8) {
                    canvas.save();
                    canvas.scale(f5, f5, themedDrawable.getBounds().centerX(), themedDrawable.getBounds().centerY());
                }
                themedDrawable.draw(canvas);
                if (z8) {
                    canvas.restore();
                }
                if (z6) {
                    canvas.restore();
                }
                themedDrawable.setAlpha(255);
            }
        }
        if (z && BuildVars.isShowReadStatus) {
            if (shouldDrawTimeOnMedia()) {
                Drawable themedDrawable2 = this.currentMessageObject.shouldDrawWithoutBackground() ? getThemedDrawable("drawableMsgStickerHalfCheck") : Theme.chat_msgMediaHalfCheckDrawable;
                BaseCell.setDrawableBounds(themedDrawable2, (this.layoutWidth - AndroidUtilities.dp(z5 ? 23.5f : 21.5f)) - themedDrawable2.getIntrinsicWidth(), (dp3 - themedDrawable2.getIntrinsicHeight()) + f2);
                themedDrawable2.setAlpha((int) (this.timeAlpha * 255.0f * f6));
                if (z8 || z6) {
                    canvas.save();
                    canvas.scale(f5, f5, themedDrawable2.getBounds().centerX(), themedDrawable2.getBounds().centerY());
                }
                themedDrawable2.draw(canvas);
                if (z8 || z6) {
                    canvas.restore();
                }
                themedDrawable2.setAlpha(255);
            } else {
                Drawable themedDrawable3 = getThemedDrawable(z7 ? "drawableMsgOutHalfCheckSelected" : "drawableMsgOutHalfCheck");
                float dp4 = (this.layoutWidth - AndroidUtilities.dp(18.0f)) - themedDrawable3.getIntrinsicWidth();
                if (!this.pinnedBottom && !this.pinnedTop) {
                    f7 = 8.0f;
                }
                BaseCell.setDrawableBounds(themedDrawable3, dp4, ((f3 - AndroidUtilities.dp(f7)) - themedDrawable3.getIntrinsicHeight()) + f2);
                themedDrawable3.setAlpha((int) (f6 * 255.0f));
                if (z8 || z6) {
                    canvas.save();
                    canvas.scale(f5, f5, themedDrawable3.getBounds().centerX(), themedDrawable3.getBounds().centerY());
                }
                themedDrawable3.draw(canvas);
                if (z8 || z6) {
                    canvas.restore();
                }
                themedDrawable3.setAlpha(255);
            }
        }
        if (z4) {
            if (shouldDrawTimeOnMedia()) {
                dp = this.layoutWidth - AndroidUtilities.dp(34.5f);
                dp2 = AndroidUtilities.dp(26.5f);
            } else {
                dp = this.layoutWidth - AndroidUtilities.dp(32.0f);
                dp2 = AndroidUtilities.dp((this.pinnedBottom || this.pinnedTop) ? 22.0f : 21.0f);
            }
            float f8 = (f3 - dp2) + f2;
            this.rect.set(dp, f8, AndroidUtilities.dp(14.0f) + dp, AndroidUtilities.dp(14.0f) + f8);
            int alpha = Theme.chat_msgErrorPaint.getAlpha();
            Theme.chat_msgErrorPaint.setAlpha((int) (alpha * f6));
            canvas.drawRoundRect(this.rect, AndroidUtilities.dp(1.0f), AndroidUtilities.dp(1.0f), Theme.chat_msgErrorPaint);
            Theme.chat_msgErrorPaint.setAlpha(alpha);
            BaseCell.setDrawableBounds(Theme.chat_msgErrorDrawable, dp + AndroidUtilities.dp(6.0f), f8 + AndroidUtilities.dp(2.0f));
            Theme.chat_msgErrorDrawable.setAlpha((int) (f6 * 255.0f));
            if (z8) {
                canvas.save();
                canvas.scale(f5, f5, Theme.chat_msgErrorDrawable.getBounds().centerX(), Theme.chat_msgErrorDrawable.getBounds().centerY());
            }
            Theme.chat_msgErrorDrawable.draw(canvas);
            Theme.chat_msgErrorDrawable.setAlpha(255);
            if (z8) {
                canvas.restore();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:628:0x11af, code lost:
    
        if (r2 == 2) goto L636;
     */
    /* JADX WARN: Code restructure failed: missing block: B:869:0x1693, code lost:
    
        if (r1.revealingMediaSpoilers != false) goto L891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:877:0x16ae, code lost:
    
        if (r27.radialProgress.getIcon() != 4) goto L891;
     */
    /* JADX WARN: Removed duplicated region for block: B:1022:0x1aa1  */
    /* JADX WARN: Removed duplicated region for block: B:1029:0x1ac0  */
    /* JADX WARN: Removed duplicated region for block: B:1032:0x1b14  */
    /* JADX WARN: Removed duplicated region for block: B:1069:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1081:0x18c0  */
    /* JADX WARN: Removed duplicated region for block: B:1086:0x17eb  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0a55  */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0a84  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0a91  */
    /* JADX WARN: Removed duplicated region for block: B:472:0x0e58  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0f00  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:632:0x1497  */
    /* JADX WARN: Removed duplicated region for block: B:638:0x14d1  */
    /* JADX WARN: Removed duplicated region for block: B:655:0x1526  */
    /* JADX WARN: Removed duplicated region for block: B:659:0x1537  */
    /* JADX WARN: Removed duplicated region for block: B:668:0x14e8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:674:0x14b0  */
    /* JADX WARN: Removed duplicated region for block: B:859:0x172b  */
    /* JADX WARN: Removed duplicated region for block: B:866:0x168b  */
    /* JADX WARN: Removed duplicated region for block: B:880:0x16b4  */
    /* JADX WARN: Removed duplicated region for block: B:891:0x1723  */
    /* JADX WARN: Removed duplicated region for block: B:934:0x17d7  */
    /* JADX WARN: Removed duplicated region for block: B:938:0x17f9  */
    /* JADX WARN: Removed duplicated region for block: B:963:0x18c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void drawOverlays(android.graphics.Canvas r28) {
        /*
            Method dump skipped, instructions count: 7147
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.drawOverlays(android.graphics.Canvas):void");
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public int getObserverTag() {
        return this.TAG;
    }

    public MessageObject getMessageObject() {
        MessageObject messageObject = this.messageObjectToSet;
        return messageObject != null ? messageObject : this.currentMessageObject;
    }

    public TLRPC$Document getStreamingMedia() {
        int i = this.documentAttachType;
        if (i == 4 || i == 7 || i == 2) {
            return this.documentAttach;
        }
        return null;
    }

    public boolean drawPinnedBottom() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && groupedMessages.isDocuments) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition == null || (groupedMessagePosition.flags & 8) == 0) {
                return true;
            }
            return this.pinnedBottom;
        }
        return this.pinnedBottom;
    }

    public float getVideoTranscriptionProgress() {
        MessageObject messageObject;
        if (this.transitionParams == null || (messageObject = this.currentMessageObject) == null || !messageObject.isRoundVideo()) {
            return 1.0f;
        }
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateDrawBackground) {
            return this.drawBackground ? 1.0f : 0.0f;
        }
        if (this.drawBackground) {
            return transitionParams.animateChangeProgress;
        }
        return 1.0f - transitionParams.animateChangeProgress;
    }

    public boolean drawPinnedTop() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && groupedMessages.isDocuments) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition == null || (groupedMessagePosition.flags & 4) == 0) {
                return true;
            }
            return this.pinnedTop;
        }
        return this.pinnedTop;
    }

    public boolean isPinnedBottom() {
        return this.pinnedBottom;
    }

    public boolean isPinnedTop() {
        return this.pinnedTop;
    }

    public MessageObject.GroupedMessages getCurrentMessagesGroup() {
        return this.currentMessagesGroup;
    }

    public MessageObject.GroupedMessagePosition getCurrentPosition() {
        return this.currentPosition;
    }

    public int getLayoutHeight() {
        return this.layoutHeight;
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
        if (chatMessageCellDelegate2 != null && chatMessageCellDelegate2.onAccessibilityAction(i, bundle)) {
            return false;
        }
        if (i == 16) {
            int iconForCurrentState = getIconForCurrentState();
            if (iconForCurrentState != 4 && iconForCurrentState != 5) {
                didPressButton(true, false);
            } else if (this.currentMessageObject.type == 16) {
                this.delegate.didPressOther(this, this.otherX, this.otherY);
            } else {
                didClickedImage();
            }
            return true;
        }
        if (i == org.telegram.messenger.R.id.acc_action_small_button) {
            didPressMiniButton(true);
        } else if (i == org.telegram.messenger.R.id.acc_action_msg_options) {
            ChatMessageCellDelegate chatMessageCellDelegate3 = this.delegate;
            if (chatMessageCellDelegate3 != null) {
                if (this.currentMessageObject.type == 16) {
                    chatMessageCellDelegate3.didLongPress(this, 0.0f, 0.0f);
                } else {
                    chatMessageCellDelegate3.didPressOther(this, this.otherX, this.otherY);
                }
            }
        } else if (i == org.telegram.messenger.R.id.acc_action_open_forwarded_origin && (chatMessageCellDelegate = this.delegate) != null) {
            TLRPC$Chat tLRPC$Chat = this.currentForwardChannel;
            if (tLRPC$Chat != null) {
                chatMessageCellDelegate.didPressChannelAvatar(this, tLRPC$Chat, this.currentMessageObject.messageOwner.fwd_from.channel_post, this.lastTouchX, this.lastTouchY);
            } else {
                TLRPC$User tLRPC$User = this.currentForwardUser;
                if (tLRPC$User != null) {
                    chatMessageCellDelegate.didPressUserAvatar(this, tLRPC$User, this.lastTouchX, this.lastTouchY);
                } else if (this.currentForwardName != null) {
                    chatMessageCellDelegate.didPressHiddenForward(this);
                }
            }
        }
        if ((this.currentMessageObject.isVoice() || this.currentMessageObject.isRoundVideo() || (this.currentMessageObject.isMusic() && MediaController.getInstance().isPlayingMessage(this.currentMessageObject))) && this.seekBarAccessibilityDelegate.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    public void setAnimationRunning(boolean z, boolean z2) {
        this.animationRunning = z;
        if (z) {
            this.willRemoved = z2;
        } else {
            this.willRemoved = false;
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 9 || motionEvent.getAction() == 7) {
            for (int i = 0; i < this.accessibilityVirtualViewBounds.size(); i++) {
                if (this.accessibilityVirtualViewBounds.valueAt(i).contains(x, y)) {
                    int keyAt = this.accessibilityVirtualViewBounds.keyAt(i);
                    if (keyAt == this.currentFocusedVirtualView) {
                        return true;
                    }
                    this.currentFocusedVirtualView = keyAt;
                    sendAccessibilityEventForVirtualView(keyAt, LiteMode.FLAG_CHAT_SCALE);
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 10) {
            this.currentFocusedVirtualView = 0;
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.view.View
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return new MessageAccessibilityNodeProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAccessibilityEventForVirtualView(int i, int i2) {
        sendAccessibilityEventForVirtualView(i, i2, null);
    }

    private void sendAccessibilityEventForVirtualView(int i, int i2, String str) {
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
            obtain.setPackageName(getContext().getPackageName());
            obtain.setSource(this, i);
            if (str != null) {
                obtain.getText().add(str);
            }
            if (getParent() != null) {
                getParent().requestSendAccessibilityEvent(this, obtain);
            }
        }
    }

    public static Point getMessageSize(int i, int i2) {
        return getMessageSize(i, i2, 0, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static org.telegram.ui.Components.Point getMessageSize(int r3, int r4, int r5, int r6) {
        /*
            if (r6 == 0) goto L4
            if (r5 != 0) goto L50
        L4:
            boolean r5 = org.telegram.messenger.AndroidUtilities.isTablet()
            r6 = 1060320051(0x3f333333, float:0.7)
            if (r5 == 0) goto L16
            int r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide()
        L11:
            float r5 = (float) r5
            float r5 = r5 * r6
            int r5 = (int) r5
            goto L35
        L16:
            if (r3 < r4) goto L2a
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r6 = r5.x
            int r5 = r5.y
            int r5 = java.lang.Math.min(r6, r5)
            r6 = 1115684864(0x42800000, float:64.0)
            int r6 = org.telegram.messenger.AndroidUtilities.dp(r6)
            int r5 = r5 - r6
            goto L35
        L2a:
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r5.x
            int r5 = r5.y
            int r5 = java.lang.Math.min(r0, r5)
            goto L11
        L35:
            r6 = 1120403456(0x42c80000, float:100.0)
            int r6 = org.telegram.messenger.AndroidUtilities.dp(r6)
            int r6 = r6 + r5
            int r0 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
            if (r5 <= r0) goto L46
            int r5 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
        L46:
            int r0 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
            if (r6 <= r0) goto L50
            int r6 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
        L50:
            float r3 = (float) r3
            float r5 = (float) r5
            float r0 = r3 / r5
            float r1 = r3 / r0
            int r1 = (int) r1
            float r4 = (float) r4
            float r0 = r4 / r0
            int r0 = (int) r0
            r2 = 1125515264(0x43160000, float:150.0)
            if (r1 != 0) goto L63
            int r1 = org.telegram.messenger.AndroidUtilities.dp(r2)
        L63:
            if (r0 != 0) goto L69
            int r0 = org.telegram.messenger.AndroidUtilities.dp(r2)
        L69:
            if (r0 <= r6) goto L72
            float r3 = (float) r0
            float r4 = (float) r6
            float r3 = r3 / r4
            float r4 = (float) r1
            float r4 = r4 / r3
            int r1 = (int) r4
            goto L88
        L72:
            r6 = 1123024896(0x42f00000, float:120.0)
            int r2 = org.telegram.messenger.AndroidUtilities.dp(r6)
            if (r0 >= r2) goto L87
            int r6 = org.telegram.messenger.AndroidUtilities.dp(r6)
            float r0 = (float) r6
            float r4 = r4 / r0
            float r3 = r3 / r4
            int r4 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r4 >= 0) goto L88
            int r1 = (int) r3
            goto L88
        L87:
            r6 = r0
        L88:
            org.telegram.ui.Components.Point r3 = new org.telegram.ui.Components.Point
            float r4 = (float) r1
            float r5 = (float) r6
            r3.<init>(r4, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.getMessageSize(int, int, int, int):org.telegram.ui.Components.Point");
    }

    public StaticLayout getDescriptionlayout() {
        return this.descriptionLayout;
    }

    public void setSelectedBackgroundProgress(float f) {
        this.selectedBackgroundProgress = f;
        invalidate();
    }

    public int computeHeight(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages) {
        this.photoImage.setIgnoreImageSet(true);
        this.avatarImage.setIgnoreImageSet(true);
        this.replyImageReceiver.setIgnoreImageSet(true);
        this.locationImageReceiver.setIgnoreImageSet(true);
        if (groupedMessages != null && groupedMessages.messages.size() != 1) {
            int i = 0;
            for (int i2 = 0; i2 < groupedMessages.messages.size(); i2++) {
                MessageObject messageObject2 = groupedMessages.messages.get(i2);
                MessageObject.GroupedMessagePosition groupedMessagePosition = groupedMessages.positions.get(messageObject2);
                if (groupedMessagePosition != null && (groupedMessagePosition.flags & 1) != 0) {
                    setMessageContent(messageObject2, groupedMessages, false, false);
                    i += this.totalHeight + this.keyboardHeight;
                }
            }
            return i;
        }
        setMessageContent(messageObject, groupedMessages, false, false);
        this.photoImage.setIgnoreImageSet(false);
        this.avatarImage.setIgnoreImageSet(false);
        this.replyImageReceiver.setIgnoreImageSet(false);
        this.locationImageReceiver.setIgnoreImageSet(false);
        return this.totalHeight + this.keyboardHeight;
    }

    public void shakeView() {
        PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(0.2f, 3.0f), Keyframe.ofFloat(0.4f, -3.0f), Keyframe.ofFloat(0.6f, 3.0f), Keyframe.ofFloat(0.8f, -3.0f), Keyframe.ofFloat(1.0f, 0.0f));
        Keyframe ofFloat = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe ofFloat2 = Keyframe.ofFloat(0.5f, 0.97f);
        Keyframe ofFloat3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, ofFloat, ofFloat2, ofFloat3);
        PropertyValuesHolder ofKeyframe3 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, ofFloat, ofFloat2, ofFloat3);
        AnimatorSet animatorSet = new AnimatorSet();
        this.shakeAnimation = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofPropertyValuesHolder(this, ofKeyframe), ObjectAnimator.ofPropertyValuesHolder(this, ofKeyframe2), ObjectAnimator.ofPropertyValuesHolder(this, ofKeyframe3));
        this.shakeAnimation.setDuration(500L);
        this.shakeAnimation.start();
    }

    private void cancelShakeAnimation() {
        AnimatorSet animatorSet = this.shakeAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.shakeAnimation = null;
            setScaleX(1.0f);
            setScaleY(1.0f);
            setRotation(0.0f);
        }
    }

    public void setSlidingOffset(float f) {
        if (this.slidingOffsetX != f) {
            this.slidingOffsetX = f;
            updateTranslation();
        }
    }

    public void setAnimationOffsetX(float f) {
        if (this.animationOffsetX != f) {
            this.animationOffsetX = f;
            updateTranslation();
        }
    }

    private void updateTranslation() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        setTranslationX(this.slidingOffsetX + this.animationOffsetX + (!messageObject.isOutOwner() ? this.checkBoxTranslation : 0));
    }

    public float getNonAnimationTranslationX(boolean z) {
        boolean z2;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.isOutOwner()) {
            if (z && ((z2 = this.checkBoxVisible) || this.checkBoxAnimationInProgress)) {
                this.checkBoxTranslation = (int) Math.ceil((z2 ? CubicBezierInterpolator.EASE_OUT : CubicBezierInterpolator.EASE_IN).getInterpolation(this.checkBoxAnimationProgress) * AndroidUtilities.dp(35.0f));
            }
            return this.slidingOffsetX + this.checkBoxTranslation;
        }
        return this.slidingOffsetX;
    }

    public float getSlidingOffsetX() {
        return this.slidingOffsetX;
    }

    public boolean willRemovedAfterAnimation() {
        return this.willRemoved;
    }

    public float getAnimationOffsetX() {
        return this.animationOffsetX;
    }

    @Override // android.view.View
    public void setTranslationX(float f) {
        super.setTranslationX(f);
    }

    public SeekBar getSeekBar() {
        return this.seekBar;
    }

    public SeekBarWaveform getSeekBarWaveform() {
        return this.seekBarWaveform;
    }

    private class MessageAccessibilityNodeProvider extends AccessibilityNodeProvider {
        private Path linkPath;
        private Rect rect;
        private RectF rectF;

        private MessageAccessibilityNodeProvider() {
            this.linkPath = new Path();
            this.rectF = new RectF();
            this.rect = new Rect();
        }

        private class ProfileSpan extends ClickableSpan {
            private TLRPC$User user;

            public ProfileSpan(TLRPC$User tLRPC$User) {
                this.user = tLRPC$User;
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                if (ChatMessageCell.this.delegate != null) {
                    ChatMessageCell.this.delegate.didPressUserAvatar(ChatMessageCell.this, this.user, 0.0f, 0.0f);
                }
            }
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            boolean z;
            int i2;
            String str;
            String formatShortNumber;
            int i3;
            String str2;
            boolean z2;
            CharSequence charSequence;
            long j;
            CharSequence charSequence2;
            CharSequence charSequence3;
            CharSequence charSequence4;
            int i4;
            boolean z3;
            TLRPC$MessagePeerReaction tLRPC$MessagePeerReaction;
            int i5;
            String str3;
            String string;
            String string2;
            AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo;
            int[] iArr = {0, 0};
            ChatMessageCell.this.getLocationOnScreen(iArr);
            if (i == -1) {
                AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain(ChatMessageCell.this);
                ChatMessageCell.this.onInitializeAccessibilityNodeInfo(obtain);
                boolean z4 = ChatMessageCell.this.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.isOut() && !ChatMessageCell.this.currentMessageObject.scheduled && ChatMessageCell.this.currentMessageObject.isUnread();
                boolean z5 = ChatMessageCell.this.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.isContentUnread();
                if (ChatMessageCell.this.currentMessageObject != null) {
                    charSequence = "\n";
                    j = ChatMessageCell.this.currentMessageObject.loadedFileSize;
                } else {
                    charSequence = "\n";
                    j = 0;
                }
                ChatMessageCell chatMessageCell = ChatMessageCell.this;
                if (chatMessageCell.accessibilityText == null || chatMessageCell.accessibilityTextUnread != z4 || ChatMessageCell.this.accessibilityTextContentUnread != z5 || ChatMessageCell.this.accessibilityTextFileSize != j) {
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    ChatMessageCell chatMessageCell2 = ChatMessageCell.this;
                    if (chatMessageCell2.isChat && chatMessageCell2.currentUser != null && !ChatMessageCell.this.currentMessageObject.isOut()) {
                        spannableStringBuilder.append((CharSequence) UserObject.getUserName(ChatMessageCell.this.currentUser));
                        spannableStringBuilder.setSpan(new ProfileSpan(ChatMessageCell.this.currentUser), 0, spannableStringBuilder.length(), 33);
                        spannableStringBuilder.append('\n');
                    }
                    if (ChatMessageCell.this.drawForwardedName) {
                        int i6 = 0;
                        while (i6 < 2) {
                            if (ChatMessageCell.this.forwardedNameLayout[i6] != null) {
                                spannableStringBuilder.append(ChatMessageCell.this.forwardedNameLayout[i6].getText());
                                spannableStringBuilder.append(i6 == 0 ? " " : charSequence);
                            }
                            i6++;
                        }
                    }
                    if (ChatMessageCell.this.documentAttach != null && ChatMessageCell.this.documentAttachType == 1) {
                        String attachFileName = FileLoader.getAttachFileName(ChatMessageCell.this.documentAttach);
                        if (attachFileName.indexOf(46) != -1) {
                            spannableStringBuilder.append((CharSequence) LocaleController.formatString(org.telegram.messenger.R.string.AccDescrDocumentType, attachFileName.substring(attachFileName.lastIndexOf(46) + 1).toUpperCase(Locale.ROOT)));
                        }
                    }
                    if (!TextUtils.isEmpty(ChatMessageCell.this.currentMessageObject.messageText)) {
                        spannableStringBuilder.append(ChatMessageCell.this.currentMessageObject.messageText);
                    }
                    if (ChatMessageCell.this.documentAttach == null || !((ChatMessageCell.this.documentAttachType == 1 || ChatMessageCell.this.documentAttachType == 2 || ChatMessageCell.this.documentAttachType == 4) && ChatMessageCell.this.buttonState == 1 && ChatMessageCell.this.loadingProgressLayout != null)) {
                        charSequence2 = ", ";
                        charSequence3 = charSequence;
                    } else {
                        charSequence3 = charSequence;
                        spannableStringBuilder.append(charSequence3);
                        boolean isSending = ChatMessageCell.this.currentMessageObject.isSending();
                        charSequence2 = ", ";
                        spannableStringBuilder.append((CharSequence) LocaleController.formatString(isSending ? "AccDescrUploadProgress" : "AccDescrDownloadProgress", isSending ? org.telegram.messenger.R.string.AccDescrUploadProgress : org.telegram.messenger.R.string.AccDescrDownloadProgress, AndroidUtilities.formatFileSize(ChatMessageCell.this.currentMessageObject.loadedFileSize), AndroidUtilities.formatFileSize(ChatMessageCell.this.lastLoadingSizeTotal)));
                    }
                    if (!ChatMessageCell.this.currentMessageObject.isMusic()) {
                        charSequence4 = charSequence2;
                        if (ChatMessageCell.this.currentMessageObject.isVoice() || ChatMessageCell.this.isRoundVideo) {
                            spannableStringBuilder.append(charSequence4);
                            spannableStringBuilder.append((CharSequence) LocaleController.formatDuration(ChatMessageCell.this.currentMessageObject.getDuration()));
                            spannableStringBuilder.append(charSequence4);
                            if (ChatMessageCell.this.currentMessageObject.isContentUnread()) {
                                spannableStringBuilder.append((CharSequence) LocaleController.getString("AccDescrMsgNotPlayed", org.telegram.messenger.R.string.AccDescrMsgNotPlayed));
                            } else {
                                spannableStringBuilder.append((CharSequence) LocaleController.getString("AccDescrMsgPlayed", org.telegram.messenger.R.string.AccDescrMsgPlayed));
                            }
                        }
                    } else {
                        spannableStringBuilder.append(charSequence3);
                        spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrMusicInfo", org.telegram.messenger.R.string.AccDescrMusicInfo, ChatMessageCell.this.currentMessageObject.getMusicAuthor(), ChatMessageCell.this.currentMessageObject.getMusicTitle()));
                        charSequence4 = charSequence2;
                        spannableStringBuilder.append(charSequence4);
                        spannableStringBuilder.append((CharSequence) LocaleController.formatDuration(ChatMessageCell.this.currentMessageObject.getDuration()));
                    }
                    if (ChatMessageCell.this.lastPoll != null) {
                        spannableStringBuilder.append(charSequence4);
                        spannableStringBuilder.append((CharSequence) ChatMessageCell.this.lastPoll.question);
                        spannableStringBuilder.append(charSequence4);
                        if (!ChatMessageCell.this.pollClosed) {
                            if (ChatMessageCell.this.lastPoll.quiz) {
                                if (ChatMessageCell.this.lastPoll.public_voters) {
                                    string = LocaleController.getString("QuizPoll", org.telegram.messenger.R.string.QuizPoll);
                                } else {
                                    string = LocaleController.getString("AnonymousQuizPoll", org.telegram.messenger.R.string.AnonymousQuizPoll);
                                }
                            } else if (ChatMessageCell.this.lastPoll.public_voters) {
                                string = LocaleController.getString("PublicPoll", org.telegram.messenger.R.string.PublicPoll);
                            } else {
                                string = LocaleController.getString("AnonymousPoll", org.telegram.messenger.R.string.AnonymousPoll);
                            }
                        } else {
                            string = LocaleController.getString("FinalResults", org.telegram.messenger.R.string.FinalResults);
                        }
                        spannableStringBuilder.append((CharSequence) string);
                    }
                    if (ChatMessageCell.this.documentAttach != null) {
                        if (ChatMessageCell.this.documentAttachType == 4) {
                            spannableStringBuilder.append(charSequence4);
                            spannableStringBuilder.append((CharSequence) LocaleController.formatDuration(ChatMessageCell.this.currentMessageObject.getDuration()));
                        }
                        if (ChatMessageCell.this.buttonState == 0 || ChatMessageCell.this.documentAttachType == 1) {
                            spannableStringBuilder.append(charSequence4);
                            spannableStringBuilder.append((CharSequence) AndroidUtilities.formatFileSize(ChatMessageCell.this.documentAttach.size));
                        }
                    }
                    if (!ChatMessageCell.this.currentMessageObject.isVoiceTranscriptionOpen()) {
                        if (MessageObject.getMedia(ChatMessageCell.this.currentMessageObject.messageOwner) != null && !TextUtils.isEmpty(ChatMessageCell.this.currentMessageObject.caption)) {
                            spannableStringBuilder.append(charSequence3);
                            spannableStringBuilder.append(ChatMessageCell.this.currentMessageObject.caption);
                        }
                    } else {
                        spannableStringBuilder.append(charSequence3);
                        spannableStringBuilder.append(ChatMessageCell.this.currentMessageObject.getVoiceTranscription());
                    }
                    if (ChatMessageCell.this.currentMessageObject.isOut()) {
                        if (!ChatMessageCell.this.currentMessageObject.isSent()) {
                            if (!ChatMessageCell.this.currentMessageObject.isSending()) {
                                if (ChatMessageCell.this.currentMessageObject.isSendError()) {
                                    spannableStringBuilder.append(charSequence3);
                                    spannableStringBuilder.append((CharSequence) LocaleController.getString("AccDescrMsgSendingError", org.telegram.messenger.R.string.AccDescrMsgSendingError));
                                }
                            } else {
                                spannableStringBuilder.append(charSequence3);
                                spannableStringBuilder.append((CharSequence) LocaleController.getString("AccDescrMsgSending", org.telegram.messenger.R.string.AccDescrMsgSending));
                                float progress = ChatMessageCell.this.radialProgress.getProgress();
                                if (progress > 0.0f) {
                                    spannableStringBuilder.append((CharSequence) Integer.toString(Math.round(progress * 100.0f))).append((CharSequence) "%");
                                }
                            }
                        } else {
                            spannableStringBuilder.append(charSequence3);
                            if (ChatMessageCell.this.currentMessageObject.scheduled) {
                                spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrScheduledDate", org.telegram.messenger.R.string.AccDescrScheduledDate, ChatMessageCell.this.currentTimeString));
                            } else {
                                spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrSentDate", org.telegram.messenger.R.string.AccDescrSentDate, LocaleController.getString("TodayAt", org.telegram.messenger.R.string.TodayAt) + " " + ((Object) ChatMessageCell.this.currentTimeString)));
                                spannableStringBuilder.append(charSequence4);
                                if (ChatMessageCell.this.currentMessageObject.isUnread()) {
                                    i5 = org.telegram.messenger.R.string.AccDescrMsgUnread;
                                    str3 = "AccDescrMsgUnread";
                                } else {
                                    i5 = org.telegram.messenger.R.string.AccDescrMsgRead;
                                    str3 = "AccDescrMsgRead";
                                }
                                spannableStringBuilder.append((CharSequence) LocaleController.getString(str3, i5));
                            }
                        }
                    } else {
                        spannableStringBuilder.append(charSequence3);
                        spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrReceivedDate", org.telegram.messenger.R.string.AccDescrReceivedDate, LocaleController.getString("TodayAt", org.telegram.messenger.R.string.TodayAt) + " " + ((Object) ChatMessageCell.this.currentTimeString)));
                    }
                    if (ChatMessageCell.this.getRepliesCount() > 0 && !ChatMessageCell.this.hasCommentLayout()) {
                        spannableStringBuilder.append(charSequence3);
                        spannableStringBuilder.append((CharSequence) LocaleController.formatPluralString("AccDescrNumberOfReplies", ChatMessageCell.this.getRepliesCount(), new Object[0]));
                    }
                    if (ChatMessageCell.this.currentMessageObject.messageOwner.reactions != null && ChatMessageCell.this.currentMessageObject.messageOwner.reactions.results != null) {
                        String str4 = "";
                        if (ChatMessageCell.this.currentMessageObject.messageOwner.reactions.results.size() == 1) {
                            TLRPC$ReactionCount tLRPC$ReactionCount = ChatMessageCell.this.currentMessageObject.messageOwner.reactions.results.get(0);
                            TLRPC$Reaction tLRPC$Reaction = tLRPC$ReactionCount.reaction;
                            String str5 = tLRPC$Reaction instanceof TLRPC$TL_reactionEmoji ? ((TLRPC$TL_reactionEmoji) tLRPC$Reaction).emoticon : "";
                            int i7 = tLRPC$ReactionCount.count;
                            if (i7 == 1) {
                                spannableStringBuilder.append(charSequence3);
                                if (ChatMessageCell.this.currentMessageObject.messageOwner.reactions.recent_reactions == null || ChatMessageCell.this.currentMessageObject.messageOwner.reactions.recent_reactions.size() != 1 || (tLRPC$MessagePeerReaction = ChatMessageCell.this.currentMessageObject.messageOwner.reactions.recent_reactions.get(0)) == null) {
                                    z3 = false;
                                } else {
                                    TLRPC$User user = MessagesController.getInstance(ChatMessageCell.this.currentAccount).getUser(Long.valueOf(MessageObject.getPeerId(tLRPC$MessagePeerReaction.peer_id)));
                                    z3 = UserObject.isUserSelf(user);
                                    if (user != null) {
                                        str4 = UserObject.getFirstName(user);
                                    }
                                }
                                if (z3) {
                                    spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrYouReactedWith", org.telegram.messenger.R.string.AccDescrYouReactedWith, str5));
                                } else {
                                    spannableStringBuilder.append((CharSequence) LocaleController.formatString("AccDescrReactedWith", org.telegram.messenger.R.string.AccDescrReactedWith, str4, str5));
                                }
                            } else if (i7 > 1) {
                                spannableStringBuilder.append(charSequence3);
                                spannableStringBuilder.append((CharSequence) LocaleController.formatPluralString("AccDescrNumberOfPeopleReactions", tLRPC$ReactionCount.count, str5));
                            }
                        } else {
                            spannableStringBuilder.append((CharSequence) LocaleController.getString("Reactions", org.telegram.messenger.R.string.Reactions)).append((CharSequence) ": ");
                            int size = ChatMessageCell.this.currentMessageObject.messageOwner.reactions.results.size();
                            for (int i8 = 0; i8 < size; i8++) {
                                TLRPC$ReactionCount tLRPC$ReactionCount2 = ChatMessageCell.this.currentMessageObject.messageOwner.reactions.results.get(i8);
                                TLRPC$Reaction tLRPC$Reaction2 = tLRPC$ReactionCount2.reaction;
                                spannableStringBuilder.append((CharSequence) (tLRPC$Reaction2 instanceof TLRPC$TL_reactionEmoji ? ((TLRPC$TL_reactionEmoji) tLRPC$Reaction2).emoticon : "")).append((CharSequence) " ").append((CharSequence) (tLRPC$ReactionCount2.count + ""));
                                if (i8 + 1 < size) {
                                    spannableStringBuilder.append(charSequence4);
                                }
                            }
                            spannableStringBuilder.append(charSequence3);
                        }
                    }
                    if ((ChatMessageCell.this.currentMessageObject.messageOwner.flags & 1024) != 0) {
                        spannableStringBuilder.append(charSequence3);
                        i4 = 0;
                        spannableStringBuilder.append((CharSequence) LocaleController.formatPluralString("AccDescrNumberOfViews", ChatMessageCell.this.currentMessageObject.messageOwner.views, new Object[0]));
                    } else {
                        i4 = 0;
                    }
                    spannableStringBuilder.append(charSequence3);
                    for (final CharacterStyle characterStyle : (CharacterStyle[]) spannableStringBuilder.getSpans(i4, spannableStringBuilder.length(), ClickableSpan.class)) {
                        int spanStart = spannableStringBuilder.getSpanStart(characterStyle);
                        int spanEnd = spannableStringBuilder.getSpanEnd(characterStyle);
                        spannableStringBuilder.removeSpan(characterStyle);
                        spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Cells.ChatMessageCell.MessageAccessibilityNodeProvider.1
                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                CharacterStyle characterStyle2 = characterStyle;
                                if (!(characterStyle2 instanceof ProfileSpan)) {
                                    if (ChatMessageCell.this.delegate != null) {
                                        ChatMessageCell.this.delegate.didPressUrl(ChatMessageCell.this, characterStyle, false);
                                        return;
                                    }
                                    return;
                                }
                                ((ProfileSpan) characterStyle2).onClick(view);
                            }
                        }, spanStart, spanEnd, 33);
                    }
                    ChatMessageCell chatMessageCell3 = ChatMessageCell.this;
                    chatMessageCell3.accessibilityText = spannableStringBuilder;
                    chatMessageCell3.accessibilityTextUnread = z4;
                    ChatMessageCell.this.accessibilityTextContentUnread = z5;
                    ChatMessageCell.this.accessibilityTextFileSize = j;
                }
                int i9 = Build.VERSION.SDK_INT;
                if (i9 < 24) {
                    obtain.setContentDescription(ChatMessageCell.this.accessibilityText.toString());
                } else {
                    obtain.setText(ChatMessageCell.this.accessibilityText);
                }
                obtain.setEnabled(true);
                if (i9 >= 19 && (collectionItemInfo = obtain.getCollectionItemInfo()) != null) {
                    obtain.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(collectionItemInfo.getRowIndex(), 1, 0, 1, false));
                }
                if (i9 >= 21) {
                    obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(org.telegram.messenger.R.id.acc_action_msg_options, LocaleController.getString("AccActionMessageOptions", org.telegram.messenger.R.string.AccActionMessageOptions)));
                    int iconForCurrentState = ChatMessageCell.this.getIconForCurrentState();
                    if (iconForCurrentState == 0) {
                        string2 = LocaleController.getString("AccActionPlay", org.telegram.messenger.R.string.AccActionPlay);
                    } else if (iconForCurrentState == 1) {
                        string2 = LocaleController.getString("AccActionPause", org.telegram.messenger.R.string.AccActionPause);
                    } else if (iconForCurrentState == 2) {
                        string2 = LocaleController.getString("AccActionDownload", org.telegram.messenger.R.string.AccActionDownload);
                    } else if (iconForCurrentState == 3) {
                        string2 = LocaleController.getString("AccActionCancelDownload", org.telegram.messenger.R.string.AccActionCancelDownload);
                    } else if (iconForCurrentState == 5) {
                        string2 = LocaleController.getString("AccActionOpenFile", org.telegram.messenger.R.string.AccActionOpenFile);
                    } else {
                        string2 = ChatMessageCell.this.currentMessageObject.type == 16 ? LocaleController.getString("CallAgain", org.telegram.messenger.R.string.CallAgain) : null;
                    }
                    obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, string2));
                    obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(32, LocaleController.getString("AccActionEnterSelectionMode", org.telegram.messenger.R.string.AccActionEnterSelectionMode)));
                    if (ChatMessageCell.this.getMiniIconForCurrentState() == 2) {
                        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(org.telegram.messenger.R.id.acc_action_small_button, LocaleController.getString("AccActionDownload", org.telegram.messenger.R.string.AccActionDownload)));
                    }
                } else {
                    obtain.addAction(16);
                    obtain.addAction(32);
                }
                if ((ChatMessageCell.this.currentMessageObject.isVoice() || ChatMessageCell.this.currentMessageObject.isRoundVideo() || ChatMessageCell.this.currentMessageObject.isMusic()) && MediaController.getInstance().isPlayingMessage(ChatMessageCell.this.currentMessageObject)) {
                    ChatMessageCell.this.seekBarAccessibilityDelegate.onInitializeAccessibilityNodeInfoInternal(obtain);
                }
                if (ChatMessageCell.this.useTranscribeButton && ChatMessageCell.this.transcribeButton != null) {
                    obtain.addChild(ChatMessageCell.this, 493);
                }
                if (i9 < 24) {
                    ChatMessageCell chatMessageCell4 = ChatMessageCell.this;
                    if (chatMessageCell4.isChat && chatMessageCell4.currentUser != null && !ChatMessageCell.this.currentMessageObject.isOut()) {
                        obtain.addChild(ChatMessageCell.this, 5000);
                    }
                    if (ChatMessageCell.this.currentMessageObject.messageText instanceof Spannable) {
                        Spannable spannable = (Spannable) ChatMessageCell.this.currentMessageObject.messageText;
                        int i10 = 0;
                        for (CharacterStyle characterStyle2 : (CharacterStyle[]) spannable.getSpans(0, spannable.length(), ClickableSpan.class)) {
                            obtain.addChild(ChatMessageCell.this, i10 + 2000);
                            i10++;
                        }
                    }
                    if ((ChatMessageCell.this.currentMessageObject.caption instanceof Spannable) && ChatMessageCell.this.captionLayout != null) {
                        Spannable spannable2 = (Spannable) ChatMessageCell.this.currentMessageObject.caption;
                        int i11 = 0;
                        for (CharacterStyle characterStyle3 : (CharacterStyle[]) spannable2.getSpans(0, spannable2.length(), ClickableSpan.class)) {
                            obtain.addChild(ChatMessageCell.this, i11 + BannerConfig.LOOP_TIME);
                            i11++;
                        }
                    }
                }
                Iterator it = ChatMessageCell.this.botButtons.iterator();
                int i12 = 0;
                while (it.hasNext()) {
                    obtain.addChild(ChatMessageCell.this, i12 + 1000);
                    i12++;
                }
                if (ChatMessageCell.this.hintButtonVisible && ChatMessageCell.this.pollHintX != -1 && ChatMessageCell.this.currentMessageObject.isPoll()) {
                    obtain.addChild(ChatMessageCell.this, 495);
                }
                Iterator it2 = ChatMessageCell.this.pollButtons.iterator();
                int i13 = 0;
                while (it2.hasNext()) {
                    obtain.addChild(ChatMessageCell.this, i13 + 500);
                    i13++;
                }
                if (ChatMessageCell.this.drawInstantView && !ChatMessageCell.this.instantButtonRect.isEmpty()) {
                    obtain.addChild(ChatMessageCell.this, 499);
                }
                if (ChatMessageCell.this.commentLayout != null) {
                    obtain.addChild(ChatMessageCell.this, 496);
                }
                if (ChatMessageCell.this.drawSideButton == 1 || ChatMessageCell.this.drawSideButton == 2) {
                    obtain.addChild(ChatMessageCell.this, 498);
                }
                ChatMessageCell chatMessageCell5 = ChatMessageCell.this;
                if (chatMessageCell5.replyNameLayout != null) {
                    obtain.addChild(chatMessageCell5, 497);
                }
                if (ChatMessageCell.this.forwardedNameLayout[0] != null && ChatMessageCell.this.forwardedNameLayout[1] != null) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(org.telegram.messenger.R.id.acc_action_open_forwarded_origin, LocaleController.getString("AccActionOpenForwardedOrigin", org.telegram.messenger.R.string.AccActionOpenForwardedOrigin)));
                    } else {
                        obtain.addChild(ChatMessageCell.this, 494);
                    }
                }
                if (ChatMessageCell.this.drawSelectionBackground || ChatMessageCell.this.getBackground() != null) {
                    obtain.setSelected(true);
                }
                return obtain;
            }
            AccessibilityNodeInfo obtain2 = AccessibilityNodeInfo.obtain();
            obtain2.setSource(ChatMessageCell.this, i);
            obtain2.setParent(ChatMessageCell.this);
            obtain2.setPackageName(ChatMessageCell.this.getContext().getPackageName());
            if (i == 5000) {
                if (ChatMessageCell.this.currentUser == null) {
                    return null;
                }
                obtain2.setText(UserObject.getUserName(ChatMessageCell.this.currentUser));
                this.rect.set((int) ChatMessageCell.this.nameX, (int) ChatMessageCell.this.nameY, (int) (ChatMessageCell.this.nameX + ChatMessageCell.this.nameWidth), (int) (ChatMessageCell.this.nameY + (ChatMessageCell.this.nameLayout != null ? ChatMessageCell.this.nameLayout.getHeight() : 10)));
                obtain2.setBoundsInParent(this.rect);
                if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null) {
                    ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                }
                this.rect.offset(iArr[0], iArr[1]);
                obtain2.setBoundsInScreen(this.rect);
                obtain2.setClassName("android.widget.TextView");
                obtain2.setEnabled(true);
                obtain2.setClickable(true);
                obtain2.setLongClickable(true);
                obtain2.addAction(16);
                obtain2.addAction(32);
            } else if (i >= 3000) {
                if (!(ChatMessageCell.this.currentMessageObject.caption instanceof Spannable) || ChatMessageCell.this.captionLayout == null) {
                    return null;
                }
                Spannable spannable3 = (Spannable) ChatMessageCell.this.currentMessageObject.caption;
                ClickableSpan linkById = getLinkById(i, true);
                if (linkById == null) {
                    return null;
                }
                int[] realSpanStartAndEnd = ChatMessageCell.this.getRealSpanStartAndEnd(spannable3, linkById);
                obtain2.setText(spannable3.subSequence(realSpanStartAndEnd[0], realSpanStartAndEnd[1]).toString());
                ChatMessageCell.this.captionLayout.getText().length();
                ChatMessageCell.this.captionLayout.getSelectionPath(realSpanStartAndEnd[0], realSpanStartAndEnd[1], this.linkPath);
                this.linkPath.computeBounds(this.rectF, true);
                Rect rect = this.rect;
                RectF rectF = this.rectF;
                rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                this.rect.offset((int) ChatMessageCell.this.captionX, (int) ChatMessageCell.this.captionY);
                obtain2.setBoundsInParent(this.rect);
                if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null) {
                    ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                }
                this.rect.offset(iArr[0], iArr[1]);
                obtain2.setBoundsInScreen(this.rect);
                obtain2.setClassName("android.widget.TextView");
                obtain2.setEnabled(true);
                obtain2.setClickable(true);
                obtain2.setLongClickable(true);
                obtain2.addAction(16);
                obtain2.addAction(32);
            } else if (i >= 2000) {
                if (!(ChatMessageCell.this.currentMessageObject.messageText instanceof Spannable)) {
                    return null;
                }
                Spannable spannable4 = (Spannable) ChatMessageCell.this.currentMessageObject.messageText;
                ClickableSpan linkById2 = getLinkById(i, false);
                if (linkById2 == null) {
                    return null;
                }
                int[] realSpanStartAndEnd2 = ChatMessageCell.this.getRealSpanStartAndEnd(spannable4, linkById2);
                obtain2.setText(spannable4.subSequence(realSpanStartAndEnd2[0], realSpanStartAndEnd2[1]).toString());
                Iterator<MessageObject.TextLayoutBlock> it3 = ChatMessageCell.this.currentMessageObject.textLayoutBlocks.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        z2 = true;
                        break;
                    }
                    MessageObject.TextLayoutBlock next = it3.next();
                    int length = next.textLayout.getText().length();
                    int i14 = next.charactersOffset;
                    if (i14 <= realSpanStartAndEnd2[0] && length + i14 >= realSpanStartAndEnd2[1]) {
                        next.textLayout.getSelectionPath(realSpanStartAndEnd2[0] - i14, realSpanStartAndEnd2[1] - i14, this.linkPath);
                        this.linkPath.computeBounds(this.rectF, true);
                        Rect rect2 = this.rect;
                        RectF rectF2 = this.rectF;
                        rect2.set((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
                        this.rect.offset(0, (int) next.textYOffset);
                        this.rect.offset(ChatMessageCell.this.textX, ChatMessageCell.this.textY);
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z2 = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                    }
                }
                obtain2.setClassName("android.widget.TextView");
                obtain2.setEnabled(z2);
                obtain2.setClickable(z2);
                obtain2.setLongClickable(z2);
                obtain2.addAction(16);
                obtain2.addAction(32);
            } else if (i >= 1000) {
                int i15 = i - 1000;
                if (i15 >= ChatMessageCell.this.botButtons.size()) {
                    return null;
                }
                BotButton botButton = (BotButton) ChatMessageCell.this.botButtons.get(i15);
                obtain2.setText(botButton.title.getText());
                obtain2.setClassName("android.widget.Button");
                obtain2.setEnabled(true);
                obtain2.setClickable(true);
                obtain2.addAction(16);
                this.rect.set(botButton.x, botButton.y, botButton.x + botButton.width, botButton.y + botButton.height);
                this.rect.offset(ChatMessageCell.this.currentMessageObject.isOutOwner() ? (ChatMessageCell.this.getMeasuredWidth() - ChatMessageCell.this.widthForButtons) - AndroidUtilities.dp(10.0f) : ChatMessageCell.this.backgroundDrawableLeft + AndroidUtilities.dp(ChatMessageCell.this.mediaBackground ? 1.0f : 7.0f), ChatMessageCell.this.layoutHeight);
                obtain2.setBoundsInParent(this.rect);
                if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null) {
                    ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                }
                this.rect.offset(iArr[0], iArr[1]);
                obtain2.setBoundsInScreen(this.rect);
            } else {
                if (i >= 500) {
                    int i16 = i - 500;
                    if (i16 >= ChatMessageCell.this.pollButtons.size()) {
                        return null;
                    }
                    PollButton pollButton = (PollButton) ChatMessageCell.this.pollButtons.get(i16);
                    StringBuilder sb = new StringBuilder(pollButton.title.getText());
                    if (ChatMessageCell.this.pollVoted) {
                        obtain2.setSelected(pollButton.chosen);
                        sb.append(", ");
                        sb.append(pollButton.percent);
                        sb.append("%");
                        if (ChatMessageCell.this.lastPoll != null && ChatMessageCell.this.lastPoll.quiz && (pollButton.chosen || pollButton.correct)) {
                            sb.append(", ");
                            if (pollButton.correct) {
                                i3 = org.telegram.messenger.R.string.AccDescrQuizCorrectAnswer;
                                str2 = "AccDescrQuizCorrectAnswer";
                            } else {
                                i3 = org.telegram.messenger.R.string.AccDescrQuizIncorrectAnswer;
                                str2 = "AccDescrQuizIncorrectAnswer";
                            }
                            sb.append(LocaleController.getString(str2, i3));
                        }
                    } else {
                        obtain2.setClassName("android.widget.Button");
                    }
                    obtain2.setText(sb);
                    obtain2.setEnabled(true);
                    obtain2.addAction(16);
                    int i17 = pollButton.y + ChatMessageCell.this.namesOffset;
                    int dp = ChatMessageCell.this.backgroundWidth - AndroidUtilities.dp(76.0f);
                    Rect rect3 = this.rect;
                    int i18 = pollButton.x;
                    rect3.set(i18, i17, dp + i18, pollButton.height + i17);
                    obtain2.setBoundsInParent(this.rect);
                    if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null) {
                        ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                    }
                    z = true;
                    this.rect.offset(iArr[0], iArr[1]);
                    obtain2.setBoundsInScreen(this.rect);
                    obtain2.setClickable(true);
                } else {
                    z = true;
                    if (i == 495) {
                        obtain2.setClassName("android.widget.Button");
                        obtain2.setEnabled(true);
                        obtain2.setText(LocaleController.getString("AccDescrQuizExplanation", org.telegram.messenger.R.string.AccDescrQuizExplanation));
                        obtain2.addAction(16);
                        this.rect.set(ChatMessageCell.this.pollHintX - AndroidUtilities.dp(8.0f), ChatMessageCell.this.pollHintY - AndroidUtilities.dp(8.0f), ChatMessageCell.this.pollHintX + AndroidUtilities.dp(32.0f), ChatMessageCell.this.pollHintY + AndroidUtilities.dp(32.0f));
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 499) {
                        obtain2.setClassName("android.widget.Button");
                        obtain2.setEnabled(true);
                        if (ChatMessageCell.this.instantViewLayout != null) {
                            obtain2.setText(ChatMessageCell.this.instantViewLayout.getText());
                        }
                        obtain2.addAction(16);
                        ChatMessageCell.this.instantButtonRect.round(this.rect);
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 498) {
                        obtain2.setClassName("android.widget.ImageButton");
                        obtain2.setEnabled(true);
                        ChatMessageCell chatMessageCell6 = ChatMessageCell.this;
                        if (chatMessageCell6.isOpenChatByShare(chatMessageCell6.currentMessageObject)) {
                            obtain2.setContentDescription(LocaleController.getString("AccDescrOpenChat", org.telegram.messenger.R.string.AccDescrOpenChat));
                        } else {
                            obtain2.setContentDescription(LocaleController.getString("ShareFile", org.telegram.messenger.R.string.ShareFile));
                        }
                        obtain2.addAction(16);
                        this.rect.set((int) ChatMessageCell.this.sideStartX, (int) ChatMessageCell.this.sideStartY, ((int) ChatMessageCell.this.sideStartX) + AndroidUtilities.dp(40.0f), ((int) ChatMessageCell.this.sideStartY) + AndroidUtilities.dp(32.0f));
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 497) {
                        obtain2.setEnabled(true);
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(LocaleController.getString("Reply", org.telegram.messenger.R.string.Reply));
                        sb2.append(", ");
                        StaticLayout staticLayout = ChatMessageCell.this.replyNameLayout;
                        if (staticLayout != null) {
                            sb2.append(staticLayout.getText());
                            sb2.append(", ");
                        }
                        StaticLayout staticLayout2 = ChatMessageCell.this.replyTextLayout;
                        if (staticLayout2 != null) {
                            sb2.append(staticLayout2.getText());
                        }
                        obtain2.setContentDescription(sb2.toString());
                        obtain2.addAction(16);
                        Rect rect4 = this.rect;
                        ChatMessageCell chatMessageCell7 = ChatMessageCell.this;
                        int i19 = chatMessageCell7.replyStartX;
                        int i20 = chatMessageCell7.replyStartY;
                        int max = Math.max(chatMessageCell7.replyNameWidth, ChatMessageCell.this.replyTextWidth) + i19;
                        ChatMessageCell chatMessageCell8 = ChatMessageCell.this;
                        rect4.set(i19, i20, max, chatMessageCell8.replyStartY + ((int) chatMessageCell8.replyHeight));
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 494) {
                        obtain2.setEnabled(true);
                        StringBuilder sb3 = new StringBuilder();
                        if (ChatMessageCell.this.forwardedNameLayout[0] != null && ChatMessageCell.this.forwardedNameLayout[1] != null) {
                            int i21 = 0;
                            while (i21 < 2) {
                                sb3.append(ChatMessageCell.this.forwardedNameLayout[i21].getText());
                                sb3.append(i21 == 0 ? " " : "\n");
                                i21++;
                            }
                        }
                        obtain2.setContentDescription(sb3.toString());
                        obtain2.addAction(16);
                        int min = (int) Math.min(ChatMessageCell.this.forwardNameX - ChatMessageCell.this.forwardNameOffsetX[0], ChatMessageCell.this.forwardNameX - ChatMessageCell.this.forwardNameOffsetX[1]);
                        this.rect.set(min, ChatMessageCell.this.forwardNameY, ChatMessageCell.this.forwardedNameWidth + min, ChatMessageCell.this.forwardNameY + ChatMessageCell.this.forwardHeight);
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 496) {
                        obtain2.setClassName("android.widget.Button");
                        obtain2.setEnabled(true);
                        int repliesCount = ChatMessageCell.this.getRepliesCount();
                        if (ChatMessageCell.this.currentMessageObject != null && !ChatMessageCell.this.currentMessageObject.shouldDrawWithoutBackground() && !ChatMessageCell.this.currentMessageObject.isAnimatedEmoji()) {
                            if (ChatMessageCell.this.isRepliesChat) {
                                formatShortNumber = LocaleController.getString("ViewInChat", org.telegram.messenger.R.string.ViewInChat);
                            } else {
                                formatShortNumber = repliesCount == 0 ? LocaleController.getString("LeaveAComment", org.telegram.messenger.R.string.LeaveAComment) : LocaleController.formatPluralString("CommentsCount", repliesCount, new Object[0]);
                            }
                        } else {
                            formatShortNumber = (ChatMessageCell.this.isRepliesChat || repliesCount <= 0) ? null : LocaleController.formatShortNumber(repliesCount, null);
                        }
                        if (formatShortNumber != null) {
                            obtain2.setText(formatShortNumber);
                        }
                        obtain2.addAction(16);
                        this.rect.set(ChatMessageCell.this.commentButtonRect);
                        obtain2.setBoundsInParent(this.rect);
                        if (ChatMessageCell.this.accessibilityVirtualViewBounds.get(i) == null || !((Rect) ChatMessageCell.this.accessibilityVirtualViewBounds.get(i)).equals(this.rect)) {
                            ChatMessageCell.this.accessibilityVirtualViewBounds.put(i, new Rect(this.rect));
                        }
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    } else if (i == 493) {
                        obtain2.setClassName("android.widget.Button");
                        obtain2.setEnabled(true);
                        if (ChatMessageCell.this.currentMessageObject.isVoiceTranscriptionOpen()) {
                            i2 = org.telegram.messenger.R.string.AccActionCloseTranscription;
                            str = "AccActionCloseTranscription";
                        } else {
                            i2 = org.telegram.messenger.R.string.AccActionOpenTranscription;
                            str = "AccActionOpenTranscription";
                        }
                        obtain2.setText(LocaleController.getString(str, i2));
                        obtain2.addAction(16);
                        if (ChatMessageCell.this.transcribeButton != null) {
                            this.rect.set((int) ChatMessageCell.this.transcribeX, (int) ChatMessageCell.this.transcribeY, (int) (ChatMessageCell.this.transcribeX + ChatMessageCell.this.transcribeButton.width()), (int) (ChatMessageCell.this.transcribeY + ChatMessageCell.this.transcribeButton.height()));
                        }
                        obtain2.setBoundsInParent(this.rect);
                        z = true;
                        this.rect.offset(iArr[0], iArr[1]);
                        obtain2.setBoundsInScreen(this.rect);
                        obtain2.setClickable(true);
                    }
                }
                obtain2.setFocusable(z);
                obtain2.setVisibleToUser(z);
                return obtain2;
            }
            z = true;
            obtain2.setFocusable(z);
            obtain2.setVisibleToUser(z);
            return obtain2;
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, Bundle bundle) {
            if (i == -1) {
                ChatMessageCell.this.performAccessibilityAction(i2, bundle);
            } else if (i2 == 64) {
                ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, LiteMode.FLAG_CHAT_SCALE);
            } else {
                if (i2 == 16) {
                    if (i == 5000) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCellDelegate chatMessageCellDelegate = ChatMessageCell.this.delegate;
                            ChatMessageCell chatMessageCell = ChatMessageCell.this;
                            chatMessageCellDelegate.didPressUserAvatar(chatMessageCell, chatMessageCell.currentUser, 0.0f, 0.0f);
                        }
                    } else if (i >= 3000) {
                        ClickableSpan linkById = getLinkById(i, true);
                        if (linkById != null) {
                            ChatMessageCell.this.delegate.didPressUrl(ChatMessageCell.this, linkById, false);
                            ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, 1);
                        }
                    } else if (i >= 2000) {
                        ClickableSpan linkById2 = getLinkById(i, false);
                        if (linkById2 != null) {
                            ChatMessageCell.this.delegate.didPressUrl(ChatMessageCell.this, linkById2, false);
                            ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, 1);
                        }
                    } else if (i >= 1000) {
                        int i3 = i - 1000;
                        if (i3 >= ChatMessageCell.this.botButtons.size()) {
                            return false;
                        }
                        BotButton botButton = (BotButton) ChatMessageCell.this.botButtons.get(i3);
                        if (ChatMessageCell.this.delegate != null && botButton.button != null) {
                            ChatMessageCell.this.delegate.didPressBotButton(ChatMessageCell.this, botButton.button);
                        }
                        ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, 1);
                    } else if (i >= 500) {
                        int i4 = i - 500;
                        if (i4 >= ChatMessageCell.this.pollButtons.size()) {
                            return false;
                        }
                        PollButton pollButton = (PollButton) ChatMessageCell.this.pollButtons.get(i4);
                        if (ChatMessageCell.this.delegate != null) {
                            ArrayList<TLRPC$TL_pollAnswer> arrayList = new ArrayList<>();
                            arrayList.add(pollButton.answer);
                            ChatMessageCell.this.delegate.didPressVoteButtons(ChatMessageCell.this, arrayList, -1, 0, 0);
                        }
                        ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, 1);
                    } else if (i == 495) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCell.this.delegate.didPressHint(ChatMessageCell.this, 0);
                        }
                    } else if (i == 499) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCellDelegate chatMessageCellDelegate2 = ChatMessageCell.this.delegate;
                            ChatMessageCell chatMessageCell2 = ChatMessageCell.this;
                            chatMessageCellDelegate2.didPressInstantButton(chatMessageCell2, chatMessageCell2.drawInstantViewType);
                        }
                    } else if (i == 498) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCell.this.delegate.didPressSideButton(ChatMessageCell.this);
                        }
                    } else if (i == 497) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCell chatMessageCell3 = ChatMessageCell.this;
                            if ((!chatMessageCell3.isThreadChat || chatMessageCell3.currentMessageObject.getReplyTopMsgId() != 0) && ChatMessageCell.this.currentMessageObject.hasValidReplyMessageObject()) {
                                ChatMessageCellDelegate chatMessageCellDelegate3 = ChatMessageCell.this.delegate;
                                ChatMessageCell chatMessageCell4 = ChatMessageCell.this;
                                chatMessageCellDelegate3.didPressReplyMessage(chatMessageCell4, chatMessageCell4.currentMessageObject.getReplyMsgId());
                            }
                        }
                    } else if (i == 494) {
                        if (ChatMessageCell.this.delegate != null) {
                            if (ChatMessageCell.this.currentForwardChannel != null) {
                                ChatMessageCellDelegate chatMessageCellDelegate4 = ChatMessageCell.this.delegate;
                                ChatMessageCell chatMessageCell5 = ChatMessageCell.this;
                                chatMessageCellDelegate4.didPressChannelAvatar(chatMessageCell5, chatMessageCell5.currentForwardChannel, ChatMessageCell.this.currentMessageObject.messageOwner.fwd_from.channel_post, ChatMessageCell.this.lastTouchX, ChatMessageCell.this.lastTouchY);
                            } else if (ChatMessageCell.this.currentForwardUser != null) {
                                ChatMessageCellDelegate chatMessageCellDelegate5 = ChatMessageCell.this.delegate;
                                ChatMessageCell chatMessageCell6 = ChatMessageCell.this;
                                chatMessageCellDelegate5.didPressUserAvatar(chatMessageCell6, chatMessageCell6.currentForwardUser, ChatMessageCell.this.lastTouchX, ChatMessageCell.this.lastTouchY);
                            } else if (ChatMessageCell.this.currentForwardName != null) {
                                ChatMessageCell.this.delegate.didPressHiddenForward(ChatMessageCell.this);
                            }
                        }
                    } else if (i == 496) {
                        if (ChatMessageCell.this.delegate != null) {
                            ChatMessageCell chatMessageCell7 = ChatMessageCell.this;
                            if (chatMessageCell7.isRepliesChat) {
                                chatMessageCell7.delegate.didPressSideButton(ChatMessageCell.this);
                            } else {
                                chatMessageCell7.delegate.didPressCommentButton(ChatMessageCell.this);
                            }
                        }
                    } else if (i == 493 && ChatMessageCell.this.transcribeButton != null) {
                        ChatMessageCell.this.transcribeButton.onTap();
                    }
                } else if (i2 == 32) {
                    ClickableSpan linkById3 = getLinkById(i, i >= 3000);
                    if (linkById3 != null) {
                        ChatMessageCell.this.delegate.didPressUrl(ChatMessageCell.this, linkById3, true);
                        ChatMessageCell.this.sendAccessibilityEventForVirtualView(i, 2);
                    }
                }
            }
            return true;
        }

        private ClickableSpan getLinkById(int i, boolean z) {
            if (i == 5000) {
                return null;
            }
            if (z) {
                int i2 = i - 3000;
                if (!(ChatMessageCell.this.currentMessageObject.caption instanceof Spannable) || i2 < 0) {
                    return null;
                }
                Spannable spannable = (Spannable) ChatMessageCell.this.currentMessageObject.caption;
                ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(0, spannable.length(), ClickableSpan.class);
                if (clickableSpanArr.length <= i2) {
                    return null;
                }
                return clickableSpanArr[i2];
            }
            int i3 = i - 2000;
            if (!(ChatMessageCell.this.currentMessageObject.messageText instanceof Spannable) || i3 < 0) {
                return null;
            }
            Spannable spannable2 = (Spannable) ChatMessageCell.this.currentMessageObject.messageText;
            ClickableSpan[] clickableSpanArr2 = (ClickableSpan[]) spannable2.getSpans(0, spannable2.length(), ClickableSpan.class);
            if (clickableSpanArr2.length <= i3) {
                return null;
            }
            return clickableSpanArr2[i3];
        }
    }

    public void setImageCoords(float f, float f2, float f3, float f4) {
        this.photoImage.setImageCoords(f, f2, f3, f4);
        int i = this.documentAttachType;
        if (i == 4 || i == 2) {
            this.videoButtonX = (int) (this.photoImage.getImageX() + AndroidUtilities.dp(8.0f));
            int imageY = (int) (this.photoImage.getImageY() + AndroidUtilities.dp(8.0f));
            this.videoButtonY = imageY;
            RadialProgress2 radialProgress2 = this.videoRadialProgress;
            int i2 = this.videoButtonX;
            radialProgress2.setProgressRect(i2, imageY, AndroidUtilities.dp(24.0f) + i2, this.videoButtonY + AndroidUtilities.dp(24.0f));
            this.buttonX = (int) (f + ((this.photoImage.getImageWidth() - AndroidUtilities.dp(48.0f)) / 2.0f));
            int imageY2 = (int) (this.photoImage.getImageY() + ((this.photoImage.getImageHeight() - AndroidUtilities.dp(48.0f)) / 2.0f));
            this.buttonY = imageY2;
            RadialProgress2 radialProgress22 = this.radialProgress;
            int i3 = this.buttonX;
            radialProgress22.setProgressRect(i3, imageY2, AndroidUtilities.dp(48.0f) + i3, this.buttonY + AndroidUtilities.dp(48.0f));
        }
    }

    @Override // android.view.View
    public float getAlpha() {
        if (this.ALPHA_PROPERTY_WORKAROUND) {
            return this.alphaInternal;
        }
        return super.getAlpha();
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        if ((f == 1.0f) != (getAlpha() == 1.0f)) {
            invalidate();
        }
        if (this.ALPHA_PROPERTY_WORKAROUND) {
            this.alphaInternal = f;
            invalidate();
        } else {
            super.setAlpha(f);
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if ((groupedMessagePosition != null && (groupedMessagePosition.minY != 0 || groupedMessagePosition.minX != 0)) || ((this.enterTransitionInProgress && !this.currentMessageObject.isVoice()) || this.replyNameLayout == null || this.replyTextLayout == null)) {
            MessageObject.GroupedMessagePosition groupedMessagePosition2 = this.currentPosition;
            if (groupedMessagePosition2 != null) {
                int i = groupedMessagePosition2.flags;
                if ((i & 8) == 0 || (i & 1) == 0) {
                    return;
                }
            }
            if (this.reactionsLayoutInBubble.isSmall) {
                return;
            }
        }
        invalidate();
    }

    public int getCurrentBackgroundLeft() {
        Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
        if (messageDrawable == null) {
            return 0;
        }
        int i = messageDrawable.getBounds().left;
        if (this.currentMessageObject.isOutOwner() || this.transitionParams.changePinnedBottomProgress == 1.0f) {
            return i;
        }
        boolean z = this.isRoundVideo;
        if ((!z && this.mediaBackground) || this.drawPinnedBottom) {
            return i;
        }
        if (z) {
            return (int) (i - (AndroidUtilities.dp(6.0f) * getVideoTranscriptionProgress()));
        }
        return i - AndroidUtilities.dp(6.0f);
    }

    public int getCurrentBackgroundRight() {
        Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
        if (messageDrawable == null) {
            return getWidth();
        }
        int i = messageDrawable.getBounds().right;
        if (!this.currentMessageObject.isOutOwner() || this.transitionParams.changePinnedBottomProgress == 1.0f) {
            return i;
        }
        boolean z = this.isRoundVideo;
        if ((!z && this.mediaBackground) || this.drawPinnedBottom) {
            return i;
        }
        if (z) {
            return (int) (i + (AndroidUtilities.dp(6.0f) * getVideoTranscriptionProgress()));
        }
        return i + AndroidUtilities.dp(6.0f);
    }

    public TransitionParams getTransitionParams() {
        return this.transitionParams;
    }

    public int getTopMediaOffset() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.type != 14) {
            return 0;
        }
        return this.mediaOffsetY + this.namesOffset;
    }

    public int getTextX() {
        return this.textX;
    }

    public int getTextY() {
        return this.textY;
    }

    public boolean isPlayingRound() {
        return this.isRoundVideo && this.isPlayingRound;
    }

    public int getParentWidth() {
        int i;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            messageObject = this.messageObjectToSet;
        }
        return (messageObject == null || !messageObject.preview || (i = this.parentWidth) <= 0) ? AndroidUtilities.displaySize.x : i;
    }

    public class TransitionParams {
        public boolean animateBackgroundBoundsInner;
        boolean animateBotButtonsChanged;
        private boolean animateButton;
        public boolean animateChange;
        private int animateCommentArrowX;
        private boolean animateCommentDrawUnread;
        private int animateCommentUnreadX;
        private float animateCommentX;
        private boolean animateComments;
        private StaticLayout animateCommentsLayout;
        public boolean animateDrawBackground;
        private boolean animateDrawCommentNumber;
        public boolean animateDrawingTimeAlpha;
        private boolean animateEditedEnter;
        private StaticLayout animateEditedLayout;
        private int animateEditedWidthDiff;
        int animateForwardNameWidth;
        float animateForwardNameX;
        public boolean animateForwardedLayout;
        public int animateForwardedNamesOffset;
        private float animateFromButtonX;
        private float animateFromButtonY;
        public float animateFromReplyY;
        public float animateFromRoundVideoDotY;
        public float animateFromTextY;
        public int animateFromTimeX;
        public float animateFromTimeXPinned;
        private float animateFromTimeXReplies;
        private float animateFromTimeXViews;
        public boolean animateLocationIsExpired;
        boolean animateMessageText;
        private float animateNameX;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateOutAnimateEmoji;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateOutAnimateEmojiReply;
        private StaticLayout animateOutCaptionLayout;
        private ArrayList<MessageObject.TextLayoutBlock> animateOutTextBlocks;
        private float animateOutTextXOffset;
        private boolean animatePinned;
        public boolean animatePlayingRound;
        public boolean animateRadius;
        boolean animateReplaceCaptionLayout;
        private boolean animateReplies;
        private StaticLayout animateRepliesLayout;
        private StaticLayout animateReplyTextLayout;
        public float animateReplyTextOffset;
        public boolean animateRoundVideoDotY;
        private boolean animateShouldDrawMenuDrawable;
        private boolean animateShouldDrawTimeOnMedia;
        private boolean animateSign;
        public boolean animateText;
        private StaticLayout animateTimeLayout;
        private int animateTimeWidth;
        public float animateToImageH;
        public float animateToImageW;
        public float animateToImageX;
        public float animateToImageY;
        public int[] animateToRadius;
        private int animateTotalCommentWidth;
        public boolean animateUseTranscribeButton;
        private StaticLayout animateViewsLayout;
        public float captionFromX;
        public float captionFromY;
        public float deltaBottom;
        public float deltaLeft;
        public float deltaRight;
        public float deltaTop;
        public boolean drawPinnedBottomBackground;
        public boolean ignoreAlpha;
        public boolean imageChangeBoundsTransition;
        public int lastBackgroundLeft;
        public int lastBackgroundRight;
        private float lastButtonX;
        private float lastButtonY;
        private int lastCommentArrowX;
        private boolean lastCommentDrawUnread;
        private StaticLayout lastCommentLayout;
        private int lastCommentUnreadX;
        private float lastCommentX;
        private int lastCommentsCount;
        public boolean lastDrawBackground;
        private boolean lastDrawCommentNumber;
        public StaticLayout lastDrawDocTitleLayout;
        public StaticLayout lastDrawInfoLayout;
        public float lastDrawLocationExpireProgress;
        public String lastDrawLocationExpireText;
        public float lastDrawReplyY;
        public float lastDrawRoundVideoDotY;
        public boolean lastDrawTime;
        private StaticLayout lastDrawingCaptionLayout;
        public float lastDrawingCaptionX;
        public float lastDrawingCaptionY;
        private boolean lastDrawingEdited;
        public float lastDrawingImageH;
        public float lastDrawingImageW;
        public float lastDrawingImageX;
        public float lastDrawingImageY;
        private ArrayList<MessageObject.TextLayoutBlock> lastDrawingTextBlocks;
        public float lastDrawingTextY;
        public boolean lastDrawnForwardedName;
        public StaticLayout lastDrawnReplyTextLayout;
        int lastForwardNameWidth;
        float lastForwardNameX;
        public int lastForwardedNamesOffset;
        private boolean lastIsPinned;
        private boolean lastIsPlayingRound;
        public boolean lastLocatinIsExpired;
        private int lastRepliesCount;
        private StaticLayout lastRepliesLayout;
        public int lastReplyTextXOffset;
        private boolean lastShouldDrawMenuDrawable;
        private boolean lastShouldDrawTimeOnMedia;
        private String lastSignMessage;
        public float lastTextXOffset;
        private StaticLayout lastTimeLayout;
        private int lastTimeWidth;
        public int lastTimeX;
        public float lastTimeXPinned;
        private float lastTimeXReplies;
        private float lastTimeXViews;
        public int lastTopOffset;
        private int lastTotalCommentWidth;
        public boolean lastUseTranscribeButton;
        private int lastViewsCount;
        private StaticLayout lastViewsLayout;
        public boolean messageEntering;
        private boolean moveCaption;
        public boolean shouldAnimateTimeX;
        public float toDeltaLeft;
        public float toDeltaRight;
        public boolean transformGroupToSingleMessage;
        public boolean updatePhotoImageX;
        public boolean wasDraw;
        public int[] imageRoundRadius = new int[4];
        public float captionEnterProgress = 1.0f;
        public float changePinnedBottomProgress = 1.0f;
        public Rect lastDrawingBackgroundRect = new Rect();
        public float animateChangeProgress = 1.0f;
        private ArrayList<BotButton> lastDrawBotButtons = new ArrayList<>();
        private ArrayList<BotButton> transitionBotButtons = new ArrayList<>();
        public int lastStatusDrawableParams = -1;
        public StaticLayout[] lastDrawnForwardedNameLayout = new StaticLayout[2];
        public StaticLayout[] animatingForwardedNameLayout = new StaticLayout[2];

        public boolean supportChangeAnimation() {
            return true;
        }

        public TransitionParams() {
        }

        public void recordDrawingState() {
            this.wasDraw = true;
            this.lastDrawingImageX = ChatMessageCell.this.photoImage.getImageX();
            this.lastDrawingImageY = ChatMessageCell.this.photoImage.getImageY();
            this.lastDrawingImageW = ChatMessageCell.this.photoImage.getImageWidth();
            this.lastDrawingImageH = ChatMessageCell.this.photoImage.getImageHeight();
            System.arraycopy(ChatMessageCell.this.photoImage.getRoundRadius(), 0, this.imageRoundRadius, 0, 4);
            if (ChatMessageCell.this.currentBackgroundDrawable != null) {
                this.lastDrawingBackgroundRect.set(ChatMessageCell.this.currentBackgroundDrawable.getBounds());
            }
            this.lastDrawingTextBlocks = ChatMessageCell.this.currentMessageObject.textLayoutBlocks;
            this.lastDrawingEdited = ChatMessageCell.this.edited;
            this.lastDrawingCaptionX = ChatMessageCell.this.captionX;
            this.lastDrawingCaptionY = ChatMessageCell.this.captionY;
            this.lastDrawingCaptionLayout = ChatMessageCell.this.captionLayout;
            this.lastDrawBotButtons.clear();
            if (!ChatMessageCell.this.botButtons.isEmpty()) {
                this.lastDrawBotButtons.addAll(ChatMessageCell.this.botButtons);
            }
            if (ChatMessageCell.this.commentLayout != null) {
                this.lastCommentsCount = ChatMessageCell.this.getRepliesCount();
                this.lastTotalCommentWidth = ChatMessageCell.this.totalCommentWidth;
                this.lastCommentLayout = ChatMessageCell.this.commentLayout;
                this.lastCommentArrowX = ChatMessageCell.this.commentArrowX;
                this.lastCommentUnreadX = ChatMessageCell.this.commentUnreadX;
                this.lastCommentDrawUnread = ChatMessageCell.this.commentDrawUnread;
                this.lastCommentX = ChatMessageCell.this.commentX;
                this.lastDrawCommentNumber = ChatMessageCell.this.drawCommentNumber;
            }
            this.lastRepliesCount = ChatMessageCell.this.getRepliesCount();
            this.lastViewsCount = ChatMessageCell.this.getMessageObject().messageOwner.views;
            this.lastRepliesLayout = ChatMessageCell.this.repliesLayout;
            this.lastViewsLayout = ChatMessageCell.this.viewsLayout;
            ChatMessageCell chatMessageCell = ChatMessageCell.this;
            this.lastIsPinned = chatMessageCell.isPinned;
            this.lastSignMessage = chatMessageCell.lastPostAuthor;
            this.lastDrawBackground = ChatMessageCell.this.drawBackground;
            this.lastUseTranscribeButton = ChatMessageCell.this.useTranscribeButton;
            this.lastButtonX = ChatMessageCell.this.buttonX;
            this.lastButtonY = ChatMessageCell.this.buttonY;
            this.lastDrawTime = !ChatMessageCell.this.forceNotDrawTime;
            this.lastTimeX = ChatMessageCell.this.timeX;
            this.lastTimeLayout = ChatMessageCell.this.timeLayout;
            this.lastTimeWidth = ChatMessageCell.this.timeWidth;
            this.lastShouldDrawTimeOnMedia = ChatMessageCell.this.shouldDrawTimeOnMedia();
            this.lastTopOffset = ChatMessageCell.this.getTopMediaOffset();
            this.lastShouldDrawMenuDrawable = ChatMessageCell.this.shouldDrawMenuDrawable();
            this.lastLocatinIsExpired = ChatMessageCell.this.locationExpired;
            this.lastIsPlayingRound = ChatMessageCell.this.isPlayingRound;
            this.lastDrawingTextY = ChatMessageCell.this.textY;
            int unused = ChatMessageCell.this.textX;
            this.lastDrawnForwardedNameLayout[0] = ChatMessageCell.this.forwardedNameLayout[0];
            this.lastDrawnForwardedNameLayout[1] = ChatMessageCell.this.forwardedNameLayout[1];
            this.lastDrawnForwardedName = ChatMessageCell.this.currentMessageObject.needDrawForwarded();
            this.lastForwardNameX = ChatMessageCell.this.forwardNameX;
            this.lastForwardedNamesOffset = ChatMessageCell.this.namesOffset;
            this.lastForwardNameWidth = ChatMessageCell.this.forwardedNameWidth;
            this.lastBackgroundLeft = ChatMessageCell.this.getCurrentBackgroundLeft();
            this.lastBackgroundRight = ChatMessageCell.this.currentBackgroundDrawable.getBounds().right;
            this.lastTextXOffset = ChatMessageCell.this.currentMessageObject.textXOffset;
            ChatMessageCell chatMessageCell2 = ChatMessageCell.this;
            this.lastDrawnReplyTextLayout = chatMessageCell2.replyTextLayout;
            this.lastReplyTextXOffset = chatMessageCell2.replyTextOffset;
            ChatMessageCell.this.reactionsLayoutInBubble.recordDrawingState();
            if (ChatMessageCell.this.replyNameLayout != null) {
                this.lastDrawReplyY = r0.replyStartY;
            } else {
                this.lastDrawReplyY = 0.0f;
            }
        }

        public void recordDrawingStatePreview() {
            this.lastDrawnForwardedNameLayout[0] = ChatMessageCell.this.forwardedNameLayout[0];
            this.lastDrawnForwardedNameLayout[1] = ChatMessageCell.this.forwardedNameLayout[1];
            this.lastDrawnForwardedName = ChatMessageCell.this.currentMessageObject.needDrawForwarded();
            this.lastForwardNameX = ChatMessageCell.this.forwardNameX;
            this.lastForwardedNamesOffset = ChatMessageCell.this.namesOffset;
            this.lastForwardNameWidth = ChatMessageCell.this.forwardedNameWidth;
        }

        /* JADX WARN: Removed duplicated region for block: B:103:0x03bf  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x045d  */
        /* JADX WARN: Removed duplicated region for block: B:129:0x0472  */
        /* JADX WARN: Removed duplicated region for block: B:133:0x04c4  */
        /* JADX WARN: Removed duplicated region for block: B:136:0x04d0  */
        /* JADX WARN: Removed duplicated region for block: B:139:0x04dc  */
        /* JADX WARN: Removed duplicated region for block: B:142:0x04ec  */
        /* JADX WARN: Removed duplicated region for block: B:145:0x04fb  */
        /* JADX WARN: Removed duplicated region for block: B:150:0x0533  */
        /* JADX WARN: Removed duplicated region for block: B:153:0x0540  */
        /* JADX WARN: Removed duplicated region for block: B:164:0x0573  */
        /* JADX WARN: Removed duplicated region for block: B:172:0x0478  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x03c6  */
        /* JADX WARN: Removed duplicated region for block: B:190:0x032b  */
        /* JADX WARN: Removed duplicated region for block: B:193:0x0331  */
        /* JADX WARN: Removed duplicated region for block: B:210:0x037a  */
        /* JADX WARN: Removed duplicated region for block: B:218:0x02d1  */
        /* JADX WARN: Removed duplicated region for block: B:234:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00c8  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x0107  */
        /* JADX WARN: Removed duplicated region for block: B:70:0x022f  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x023e  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x024b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean animateChange() {
            /*
                Method dump skipped, instructions count: 1414
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.TransitionParams.animateChange():boolean");
        }

        public void onDetach() {
            this.wasDraw = false;
        }

        public void resetAnimation() {
            this.animateChange = false;
            this.animatePinned = false;
            this.animateBackgroundBoundsInner = false;
            this.deltaLeft = 0.0f;
            this.deltaRight = 0.0f;
            this.deltaBottom = 0.0f;
            this.deltaTop = 0.0f;
            this.toDeltaLeft = 0.0f;
            this.toDeltaRight = 0.0f;
            if (this.imageChangeBoundsTransition && this.animateToImageW != 0.0f && this.animateToImageH != 0.0f) {
                ChatMessageCell.this.photoImage.setImageCoords(this.animateToImageX, this.animateToImageY, this.animateToImageW, this.animateToImageH);
            }
            if (this.animateRadius) {
                ChatMessageCell.this.photoImage.setRoundRadius(this.animateToRadius);
            }
            this.animateToImageX = 0.0f;
            this.animateToImageY = 0.0f;
            this.animateToImageW = 0.0f;
            this.animateToImageH = 0.0f;
            this.imageChangeBoundsTransition = false;
            this.changePinnedBottomProgress = 1.0f;
            this.captionEnterProgress = 1.0f;
            this.animateRadius = false;
            this.animateChangeProgress = 1.0f;
            this.animateMessageText = false;
            this.animateOutTextBlocks = null;
            this.animateEditedLayout = null;
            this.animateTimeLayout = null;
            this.animateEditedEnter = false;
            this.animateReplaceCaptionLayout = false;
            this.transformGroupToSingleMessage = false;
            this.animateOutCaptionLayout = null;
            AnimatedEmojiSpan.release(ChatMessageCell.this, this.animateOutAnimateEmoji);
            this.animateOutAnimateEmoji = null;
            this.moveCaption = false;
            this.animateDrawingTimeAlpha = false;
            this.transitionBotButtons.clear();
            this.animateButton = false;
            this.animateReplyTextLayout = null;
            this.animateReplies = false;
            this.animateRepliesLayout = null;
            this.animateComments = false;
            this.animateCommentsLayout = null;
            this.animateViewsLayout = null;
            this.animateShouldDrawTimeOnMedia = false;
            this.animateShouldDrawMenuDrawable = false;
            this.shouldAnimateTimeX = false;
            this.animateDrawBackground = false;
            this.animateSign = false;
            this.animateDrawingTimeAlpha = false;
            this.animateLocationIsExpired = false;
            this.animatePlayingRound = false;
            this.animateText = false;
            this.animateForwardedLayout = false;
            StaticLayout[] staticLayoutArr = this.animatingForwardedNameLayout;
            staticLayoutArr[0] = null;
            staticLayoutArr[1] = null;
            this.animateRoundVideoDotY = false;
            ChatMessageCell.this.reactionsLayoutInBubble.resetAnimation();
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:14:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0077  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int createStatusDrawableParams() {
            /*
                r7 = this;
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isOutOwner()
                r1 = 8
                r2 = 4
                r3 = 1
                r4 = 0
                if (r0 == 0) goto L7a
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isSending()
                if (r0 != 0) goto L65
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isEditing()
                if (r0 == 0) goto L2a
                goto L65
            L2a:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isSendError()
                if (r0 == 0) goto L3b
                r0 = 0
                r3 = 0
                r5 = 0
                r6 = 1
                goto L69
            L3b:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isSent()
                if (r0 == 0) goto L61
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.scheduled
                if (r0 != 0) goto L5f
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isUnread()
                if (r0 != 0) goto L5f
                r0 = 1
                goto L63
            L5f:
                r0 = 0
                goto L63
            L61:
                r0 = 0
                r3 = 0
            L63:
                r5 = 0
                goto L68
            L65:
                r0 = 0
                r3 = 0
                r5 = 1
            L68:
                r6 = 0
            L69:
                if (r3 == 0) goto L6d
                r3 = 2
                goto L6e
            L6d:
                r3 = 0
            L6e:
                r0 = r0 | r3
                if (r5 == 0) goto L72
                goto L73
            L72:
                r2 = 0
            L73:
                r0 = r0 | r2
                if (r6 == 0) goto L77
                goto L78
            L77:
                r1 = 0
            L78:
                r0 = r0 | r1
                return r0
            L7a:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isSending()
                if (r0 != 0) goto L94
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isEditing()
                if (r0 == 0) goto L93
                goto L94
            L93:
                r3 = 0
            L94:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.ui.Cells.ChatMessageCell.access$800(r0)
                boolean r0 = r0.isSendError()
                if (r3 == 0) goto La1
                goto La2
            La1:
                r2 = 0
            La2:
                if (r0 == 0) goto La5
                goto La6
            La5:
                r1 = 0
            La6:
                r0 = r2 | r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.TransitionParams.createStatusDrawableParams():int");
        }
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    private Drawable getThemedDrawable(String str) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        Drawable drawable = resourcesProvider != null ? resourcesProvider.getDrawable(str) : null;
        return drawable != null ? drawable : Theme.getThemeDrawable(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Paint getThemedPaint(String str) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        Paint paint = resourcesProvider != null ? resourcesProvider.getPaint(str) : null;
        return paint != null ? paint : Theme.getThemePaint(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasGradientService() {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        return resourcesProvider != null ? resourcesProvider.hasGradientService() : Theme.hasGradientService();
    }
}
