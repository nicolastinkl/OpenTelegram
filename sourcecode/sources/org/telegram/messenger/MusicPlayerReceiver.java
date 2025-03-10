package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

/* loaded from: classes3.dex */
public class MusicPlayerReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        KeyEvent keyEvent;
        if (intent.getAction().equals("android.intent.action.MEDIA_BUTTON")) {
            if (intent.getExtras() == null || (keyEvent = (KeyEvent) intent.getExtras().get("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) {
                return;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (MediaController.getInstance().isMessagePaused()) {
                    MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
                    return;
                } else {
                    MediaController.getInstance().lambda$startAudioAgain$7(MediaController.getInstance().getPlayingMessageObject());
                    return;
                }
            }
            if (keyCode == 87) {
                MediaController.getInstance().playNextMessage();
                return;
            }
            if (keyCode == 88) {
                MediaController.getInstance().playPreviousMessage();
                return;
            } else if (keyCode == 126) {
                MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
                return;
            } else {
                if (keyCode != 127) {
                    return;
                }
                MediaController.getInstance().lambda$startAudioAgain$7(MediaController.getInstance().getPlayingMessageObject());
                return;
            }
        }
        String action = intent.getAction();
        action.hashCode();
        switch (action) {
            case "org.telegram.android.musicplayer.close":
                MediaController.getInstance().cleanupPlayer(true, true);
                break;
            case "org.telegram.android.musicplayer.pause":
            case "android.media.AUDIO_BECOMING_NOISY":
                MediaController.getInstance().lambda$startAudioAgain$7(MediaController.getInstance().getPlayingMessageObject());
                break;
            case "org.telegram.android.musicplayer.next":
                MediaController.getInstance().playNextMessage();
                break;
            case "org.telegram.android.musicplayer.play":
                MediaController.getInstance().playMessage(MediaController.getInstance().getPlayingMessageObject());
                break;
            case "org.telegram.android.musicplayer.previous":
                MediaController.getInstance().playPreviousMessage();
                break;
        }
    }
}
