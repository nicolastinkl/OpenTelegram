package org.telegram.messenger;

/* loaded from: classes3.dex */
public final /* synthetic */ class MessagesController$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ MessagesController f$0;

    public /* synthetic */ MessagesController$$ExternalSyntheticLambda16(MessagesController messagesController) {
        this.f$0 = messagesController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.removePromoDialog();
    }
}
