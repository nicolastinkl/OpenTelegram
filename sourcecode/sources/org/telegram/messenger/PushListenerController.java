package org.telegram.messenger;

import android.os.SystemClock;
import android.text.TextUtils;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CountDownLatch;
import org.telegram.messenger.PushListenerController;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_help_saveAppLog;
import org.telegram.tgnet.TLRPC$TL_inputAppEvent;
import org.telegram.tgnet.TLRPC$TL_jsonNull;
import org.telegram.tgnet.TLRPC$TL_updates;

/* loaded from: classes3.dex */
public class PushListenerController {
    public static final int NOTIFICATION_ID = 1;
    public static final int PUSH_TYPE_FIREBASE = 2;
    public static final int PUSH_TYPE_HUAWEI = 13;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public interface IPushListenerServiceProvider {
        String getLogTitle();

        int getPushType();

        boolean hasServices();

        void onRequestPushToken();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PushType {
    }

    public static void sendRegistrationToServer(final int i, final String str) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.lambda$sendRegistrationToServer$3(str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRegistrationToServer$3(final String str, final int i) {
        boolean z;
        ConnectionsManager.setRegId(str, i, SharedConfig.pushStringStatus);
        if (str == null) {
            return;
        }
        if (SharedConfig.pushStringGetTimeStart == 0 || SharedConfig.pushStringGetTimeEnd == 0 || (SharedConfig.pushStatSent && TextUtils.equals(SharedConfig.pushString, str))) {
            z = false;
        } else {
            SharedConfig.pushStatSent = false;
            z = true;
        }
        SharedConfig.pushString = str;
        SharedConfig.pushType = i;
        for (final int i2 = 0; i2 < 1; i2++) {
            UserConfig userConfig = UserConfig.getInstance(i2);
            userConfig.registeredForPush = false;
            userConfig.saveConfig(false);
            if (userConfig.getClientUserId() != 0) {
                if (z) {
                    String str2 = i == 2 ? "fcm" : "hcm";
                    TLRPC$TL_help_saveAppLog tLRPC$TL_help_saveAppLog = new TLRPC$TL_help_saveAppLog();
                    TLRPC$TL_inputAppEvent tLRPC$TL_inputAppEvent = new TLRPC$TL_inputAppEvent();
                    tLRPC$TL_inputAppEvent.time = SharedConfig.pushStringGetTimeStart;
                    tLRPC$TL_inputAppEvent.type = str2 + "_token_request";
                    tLRPC$TL_inputAppEvent.peer = 0L;
                    tLRPC$TL_inputAppEvent.data = new TLRPC$TL_jsonNull();
                    tLRPC$TL_help_saveAppLog.events.add(tLRPC$TL_inputAppEvent);
                    TLRPC$TL_inputAppEvent tLRPC$TL_inputAppEvent2 = new TLRPC$TL_inputAppEvent();
                    tLRPC$TL_inputAppEvent2.time = SharedConfig.pushStringGetTimeEnd;
                    tLRPC$TL_inputAppEvent2.type = str2 + "_token_response";
                    tLRPC$TL_inputAppEvent2.peer = SharedConfig.pushStringGetTimeEnd - SharedConfig.pushStringGetTimeStart;
                    tLRPC$TL_inputAppEvent2.data = new TLRPC$TL_jsonNull();
                    tLRPC$TL_help_saveAppLog.events.add(tLRPC$TL_inputAppEvent2);
                    ConnectionsManager.getInstance(i2).sendRequest(tLRPC$TL_help_saveAppLog, new RequestDelegate() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda8
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                            PushListenerController.lambda$sendRegistrationToServer$1(tLObject, tLRPC$TL_error);
                        }
                    });
                    z = false;
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PushListenerController.lambda$sendRegistrationToServer$2(i2, i, str);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRegistrationToServer$1(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.lambda$sendRegistrationToServer$0(TLRPC$TL_error.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRegistrationToServer$0(TLRPC$TL_error tLRPC$TL_error) {
        if (tLRPC$TL_error != null) {
            SharedConfig.pushStatSent = true;
            SharedConfig.saveConfig();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRegistrationToServer$2(int i, int i2, String str) {
        MessagesController.getInstance(i).registerForPush(i2, str);
    }

    public static void processRemoteMessage(int i, final String str, final long j) {
        final String str2 = i == 2 ? "FCM" : "HCM";
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d(str2 + " PRE START PROCESSING");
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.lambda$processRemoteMessage$8(str2, str, j);
            }
        });
        try {
            countDownLatch.await();
        } catch (Throwable unused) {
        }
        if (BuildVars.DEBUG_VERSION) {
            FileLog.d("finished " + str2 + " service, time = " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$processRemoteMessage$8(final String str, final String str2, final long j) {
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d(str + " PRE INIT APP");
        }
        ApplicationLoader.postInitApplication();
        if (BuildVars.LOGS_ENABLED) {
            FileLog.d(str + " POST INIT APP");
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                PushListenerController.lambda$processRemoteMessage$7(str, str2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x04d3, code lost:
    
        if (r4 > r3.intValue()) goto L194;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:280:0x0bca. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:142:0x1f3d A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x055f A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x058d A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x05f1 A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x061c A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:234:0x1e3a A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x1efe A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x2043  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x205a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x2053  */
    /* JADX WARN: Removed duplicated region for block: B:867:0x1e22  */
    /* JADX WARN: Removed duplicated region for block: B:874:0x05bf A[Catch: all -> 0x1f4e, TryCatch #6 {all -> 0x1f4e, blocks: (B:133:0x02ee, B:135:0x02fd, B:138:0x0320, B:139:0x0355, B:142:0x1f3d, B:143:0x1f42, B:146:0x0330, B:148:0x033d, B:149:0x0350, B:150:0x0347, B:151:0x0368, B:154:0x037a, B:155:0x038d, B:157:0x0390, B:159:0x039c, B:161:0x03b9, B:162:0x03e4, B:164:0x03ec, B:165:0x0404, B:167:0x0407, B:169:0x0423, B:171:0x043a, B:172:0x0465, B:174:0x046b, B:176:0x0473, B:177:0x047b, B:179:0x0483, B:181:0x049a, B:183:0x04b0, B:184:0x04cf, B:187:0x04f0, B:190:0x04f8, B:193:0x0501, B:199:0x0529, B:201:0x0531, B:204:0x053c, B:206:0x0545, B:209:0x0553, B:211:0x055f, B:213:0x0570, B:215:0x057c, B:217:0x058d, B:219:0x0593, B:222:0x05ed, B:224:0x05f1, B:225:0x0616, B:227:0x061c, B:230:0x1e32, B:234:0x1e3a, B:237:0x1e49, B:239:0x1e54, B:241:0x1e5d, B:242:0x1e64, B:244:0x1e6c, B:245:0x1e99, B:247:0x1ea5, B:252:0x1edb, B:254:0x1efe, B:255:0x1f10, B:257:0x1f18, B:261:0x1f22, B:266:0x1eb5, B:268:0x1ec3, B:269:0x1ecf, B:272:0x1e80, B:273:0x1e8c, B:275:0x0635, B:276:0x0639, B:282:0x0bd3, B:284:0x1e0b, B:287:0x0bdf, B:290:0x0bfb, B:293:0x0c20, B:295:0x0c37, B:298:0x0c51, B:300:0x0c6a, B:301:0x0c81, B:304:0x0c9b, B:306:0x0cb4, B:307:0x0ccb, B:310:0x0ce5, B:312:0x0cfe, B:313:0x0d15, B:316:0x0d2f, B:318:0x0d48, B:319:0x0d5f, B:322:0x0d79, B:324:0x0d92, B:325:0x0da9, B:328:0x0dc3, B:330:0x0ddc, B:331:0x0df8, B:334:0x0e17, B:336:0x0e30, B:337:0x0e4c, B:340:0x0e6b, B:342:0x0e84, B:343:0x0ea0, B:346:0x0ebf, B:348:0x0ed8, B:349:0x0eef, B:352:0x0f09, B:354:0x0f0d, B:356:0x0f15, B:357:0x0f2c, B:359:0x0f40, B:361:0x0f44, B:363:0x0f4c, B:364:0x0f68, B:365:0x0f7f, B:367:0x0f83, B:369:0x0f8b, B:370:0x0fa2, B:373:0x0fbc, B:375:0x0fd5, B:376:0x0fec, B:379:0x1006, B:381:0x101f, B:382:0x1036, B:385:0x1050, B:387:0x1069, B:388:0x1080, B:391:0x109a, B:393:0x10b3, B:394:0x10ca, B:397:0x10e4, B:399:0x10fd, B:400:0x1114, B:403:0x112e, B:405:0x1147, B:406:0x1163, B:407:0x117a, B:410:0x1197, B:411:0x11c8, B:412:0x11f7, B:413:0x1226, B:414:0x1255, B:415:0x1288, B:416:0x12a5, B:417:0x12c2, B:418:0x12df, B:419:0x12fc, B:420:0x1319, B:421:0x1336, B:422:0x1352, B:423:0x136e, B:424:0x138f, B:425:0x13ab, B:426:0x13bd, B:427:0x13d9, B:428:0x13f5, B:429:0x1411, B:433:0x143b, B:434:0x1462, B:435:0x148c, B:436:0x14b1, B:437:0x14d6, B:438:0x14fb, B:439:0x1525, B:440:0x154f, B:441:0x1579, B:442:0x159e, B:444:0x15a8, B:446:0x15b0, B:450:0x15e5, B:451:0x1617, B:452:0x163c, B:453:0x1661, B:454:0x1685, B:455:0x16a9, B:456:0x16cd, B:457:0x16f2, B:458:0x170b, B:459:0x1736, B:460:0x175f, B:461:0x1788, B:462:0x17b1, B:463:0x17e1, B:464:0x1801, B:465:0x1821, B:466:0x1841, B:467:0x1861, B:468:0x1886, B:469:0x18ab, B:470:0x18d0, B:471:0x18f0, B:473:0x18fa, B:475:0x1902, B:476:0x1932, B:477:0x194b, B:478:0x196b, B:479:0x198a, B:480:0x19a9, B:481:0x19c8, B:482:0x19e9, B:483:0x1a03, B:484:0x1a2d, B:485:0x1a55, B:486:0x1a7d, B:487:0x1aa6, B:488:0x1ad3, B:489:0x1af8, B:490:0x1b1a, B:491:0x1b3f, B:492:0x1b5f, B:493:0x1b7f, B:494:0x1b9f, B:495:0x1bc4, B:496:0x1be9, B:497:0x1c0e, B:498:0x1c2e, B:500:0x1c38, B:502:0x1c40, B:503:0x1c70, B:504:0x1c89, B:505:0x1ca9, B:506:0x1cc9, B:507:0x1ce3, B:508:0x1d03, B:509:0x1d23, B:510:0x1d43, B:511:0x1d63, B:512:0x1d83, B:513:0x1da1, B:514:0x1dc5, B:515:0x1de4, B:516:0x063e, B:519:0x064a, B:522:0x0656, B:525:0x0662, B:528:0x066e, B:531:0x067a, B:534:0x0686, B:537:0x0692, B:540:0x069e, B:543:0x06aa, B:546:0x06b6, B:549:0x06c2, B:552:0x06ce, B:555:0x06da, B:558:0x06e6, B:561:0x06f2, B:564:0x06fe, B:567:0x070a, B:570:0x0716, B:573:0x0722, B:576:0x072d, B:579:0x0739, B:582:0x0745, B:585:0x0751, B:588:0x075d, B:591:0x0769, B:594:0x0775, B:597:0x0781, B:600:0x078d, B:603:0x0799, B:606:0x07a4, B:609:0x07b0, B:612:0x07bc, B:615:0x07c8, B:618:0x07d4, B:621:0x07e0, B:624:0x07ec, B:627:0x07f8, B:630:0x0804, B:633:0x0810, B:636:0x081c, B:639:0x0828, B:642:0x0834, B:645:0x0840, B:648:0x084c, B:651:0x0858, B:654:0x0864, B:657:0x0870, B:660:0x087c, B:663:0x0887, B:666:0x0893, B:669:0x089f, B:672:0x08ab, B:675:0x08b7, B:678:0x08c3, B:681:0x08cf, B:684:0x08db, B:687:0x08e7, B:690:0x08f3, B:693:0x08ff, B:696:0x090b, B:699:0x0917, B:702:0x0923, B:705:0x092f, B:708:0x093b, B:711:0x0947, B:714:0x0953, B:717:0x095f, B:720:0x096b, B:723:0x0977, B:726:0x0983, B:729:0x098f, B:732:0x099a, B:735:0x09a6, B:738:0x09b2, B:741:0x09bd, B:744:0x09c9, B:747:0x09d5, B:750:0x09e1, B:753:0x09ed, B:756:0x09f9, B:759:0x0a05, B:762:0x0a11, B:765:0x0a1d, B:768:0x0a29, B:771:0x0a35, B:774:0x0a41, B:777:0x0a4d, B:780:0x0a59, B:783:0x0a64, B:786:0x0a70, B:789:0x0a7c, B:792:0x0a88, B:795:0x0a94, B:798:0x0aa0, B:801:0x0aac, B:804:0x0ab8, B:807:0x0ac4, B:810:0x0ad0, B:813:0x0adc, B:816:0x0ae8, B:819:0x0af3, B:822:0x0aff, B:825:0x0b0b, B:828:0x0b16, B:831:0x0b22, B:834:0x0b2e, B:837:0x0b3a, B:840:0x0b46, B:843:0x0b51, B:846:0x0b5c, B:849:0x0b67, B:852:0x0b72, B:855:0x0b7d, B:858:0x0b88, B:861:0x0b93, B:864:0x0b9e, B:871:0x05b4, B:874:0x05bf, B:881:0x05d8, B:893:0x04e5), top: B:128:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:884:0x0579  */
    /* JADX WARN: Type inference failed for: r14v25 */
    /* JADX WARN: Type inference failed for: r14v28 */
    /* JADX WARN: Type inference failed for: r14v31 */
    /* JADX WARN: Type inference failed for: r14v34 */
    /* JADX WARN: Type inference failed for: r14v35 */
    /* JADX WARN: Type inference failed for: r14v36 */
    /* JADX WARN: Type inference failed for: r14v52 */
    /* JADX WARN: Type inference failed for: r14v55 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$processRemoteMessage$7(java.lang.String r52, java.lang.String r53, long r54) {
        /*
            Method dump skipped, instructions count: 9040
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.PushListenerController.lambda$processRemoteMessage$7(java.lang.String, java.lang.String, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$processRemoteMessage$4(int i, TLRPC$TL_updates tLRPC$TL_updates) {
        MessagesController.getInstance(i).processUpdates(tLRPC$TL_updates, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$processRemoteMessage$5(int i) {
        if (UserConfig.getInstance(i).getClientUserId() != 0) {
            UserConfig.getInstance(i).clearConfig();
            MessagesController.getInstance(i).performLogout(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$processRemoteMessage$6(int i) {
        LocationController.getInstance(i).setNewLocationEndWatchTime();
    }

    private static String getReactedText(String str, Object[] objArr) {
        str.hashCode();
        switch (str) {
            case "CHAT_REACT_CONTACT":
                return LocaleController.formatString("PushChatReactContact", R.string.PushChatReactContact, objArr);
            case "REACT_GEOLIVE":
                return LocaleController.formatString("PushReactGeoLocation", R.string.PushReactGeoLocation, objArr);
            case "CHAT_REACT_NOTEXT":
                return LocaleController.formatString("PushChatReactNotext", R.string.PushChatReactNotext, objArr);
            case "REACT_NOTEXT":
                return LocaleController.formatString("PushReactNoText", R.string.PushReactNoText, objArr);
            case "CHAT_REACT_INVOICE":
                return LocaleController.formatString("PushChatReactInvoice", R.string.PushChatReactInvoice, objArr);
            case "REACT_CONTACT":
                return LocaleController.formatString("PushReactContect", R.string.PushReactContect, objArr);
            case "CHAT_REACT_STICKER":
                return LocaleController.formatString("PushChatReactSticker", R.string.PushChatReactSticker, objArr);
            case "REACT_GAME":
                return LocaleController.formatString("PushReactGame", R.string.PushReactGame, objArr);
            case "REACT_POLL":
                return LocaleController.formatString("PushReactPoll", R.string.PushReactPoll, objArr);
            case "REACT_QUIZ":
                return LocaleController.formatString("PushReactQuiz", R.string.PushReactQuiz, objArr);
            case "REACT_TEXT":
                return LocaleController.formatString("PushReactText", R.string.PushReactText, objArr);
            case "REACT_INVOICE":
                return LocaleController.formatString("PushReactInvoice", R.string.PushReactInvoice, objArr);
            case "CHAT_REACT_DOC":
                return LocaleController.formatString("PushChatReactDoc", R.string.PushChatReactDoc, objArr);
            case "CHAT_REACT_GEO":
                return LocaleController.formatString("PushChatReactGeo", R.string.PushChatReactGeo, objArr);
            case "CHAT_REACT_GIF":
                return LocaleController.formatString("PushChatReactGif", R.string.PushChatReactGif, objArr);
            case "REACT_STICKER":
                return LocaleController.formatString("PushReactSticker", R.string.PushReactSticker, objArr);
            case "CHAT_REACT_AUDIO":
                return LocaleController.formatString("PushChatReactAudio", R.string.PushChatReactAudio, objArr);
            case "CHAT_REACT_PHOTO":
                return LocaleController.formatString("PushChatReactPhoto", R.string.PushChatReactPhoto, objArr);
            case "CHAT_REACT_ROUND":
                return LocaleController.formatString("PushChatReactRound", R.string.PushChatReactRound, objArr);
            case "CHAT_REACT_VIDEO":
                return LocaleController.formatString("PushChatReactVideo", R.string.PushChatReactVideo, objArr);
            case "CHAT_REACT_GEOLIVE":
                return LocaleController.formatString("PushChatReactGeoLive", R.string.PushChatReactGeoLive, objArr);
            case "REACT_AUDIO":
                return LocaleController.formatString("PushReactAudio", R.string.PushReactAudio, objArr);
            case "REACT_PHOTO":
                return LocaleController.formatString("PushReactPhoto", R.string.PushReactPhoto, objArr);
            case "REACT_ROUND":
                return LocaleController.formatString("PushReactRound", R.string.PushReactRound, objArr);
            case "REACT_VIDEO":
                return LocaleController.formatString("PushReactVideo", R.string.PushReactVideo, objArr);
            case "REACT_DOC":
                return LocaleController.formatString("PushReactDoc", R.string.PushReactDoc, objArr);
            case "REACT_GEO":
                return LocaleController.formatString("PushReactGeo", R.string.PushReactGeo, objArr);
            case "REACT_GIF":
                return LocaleController.formatString("PushReactGif", R.string.PushReactGif, objArr);
            case "CHAT_REACT_GAME":
                return LocaleController.formatString("PushChatReactGame", R.string.PushChatReactGame, objArr);
            case "CHAT_REACT_POLL":
                return LocaleController.formatString("PushChatReactPoll", R.string.PushChatReactPoll, objArr);
            case "CHAT_REACT_QUIZ":
                return LocaleController.formatString("PushChatReactQuiz", R.string.PushChatReactQuiz, objArr);
            case "CHAT_REACT_TEXT":
                return LocaleController.formatString("PushChatReactText", R.string.PushChatReactText, objArr);
            default:
                return null;
        }
    }

    private static void onDecryptError() {
        for (int i = 0; i < 1; i++) {
            if (UserConfig.getInstance(i).isClientActivated()) {
                ConnectionsManager.onInternalPushReceived(i);
                ConnectionsManager.getInstance(i).resumeNetworkMaybe();
            }
        }
        countDownLatch.countDown();
    }

    public static final class GooglePushListenerServiceProvider implements IPushListenerServiceProvider {
        public static final GooglePushListenerServiceProvider INSTANCE = new GooglePushListenerServiceProvider();
        private Boolean hasServices;

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public String getLogTitle() {
            return "Google Play Services";
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public int getPushType() {
            return 2;
        }

        private GooglePushListenerServiceProvider() {
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public void onRequestPushToken() {
            String str = SharedConfig.pushString;
            if (!TextUtils.isEmpty(str)) {
                if (BuildVars.DEBUG_PRIVATE_VERSION && BuildVars.LOGS_ENABLED) {
                    FileLog.d("FCM regId = " + str);
                }
            } else if (BuildVars.LOGS_ENABLED) {
                FileLog.d("FCM Registration not found.");
            }
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.PushListenerController$GooglePushListenerServiceProvider$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PushListenerController.GooglePushListenerServiceProvider.this.lambda$onRequestPushToken$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestPushToken$1() {
            try {
                SharedConfig.pushStringGetTimeStart = SystemClock.elapsedRealtime();
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener() { // from class: org.telegram.messenger.PushListenerController$GooglePushListenerServiceProvider$$ExternalSyntheticLambda0
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public final void onComplete(Task task) {
                        PushListenerController.GooglePushListenerServiceProvider.this.lambda$onRequestPushToken$0(task);
                    }
                });
            } catch (Throwable th) {
                FileLog.e(th);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRequestPushToken$0(Task task) {
            SharedConfig.pushStringGetTimeEnd = SystemClock.elapsedRealtime();
            if (!task.isSuccessful()) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.d("Failed to get regid");
                }
                SharedConfig.pushStringStatus = "__FIREBASE_FAILED__";
                PushListenerController.sendRegistrationToServer(getPushType(), null);
                return;
            }
            String str = (String) task.getResult();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            PushListenerController.sendRegistrationToServer(getPushType(), str);
        }

        @Override // org.telegram.messenger.PushListenerController.IPushListenerServiceProvider
        public boolean hasServices() {
            if (this.hasServices == null) {
                try {
                    this.hasServices = Boolean.valueOf(GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ApplicationLoader.applicationContext) == 0);
                } catch (Exception e) {
                    FileLog.e(e);
                    this.hasServices = Boolean.FALSE;
                }
            }
            return this.hasServices.booleanValue();
        }
    }
}
