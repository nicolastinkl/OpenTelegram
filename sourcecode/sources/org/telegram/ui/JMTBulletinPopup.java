package org.telegram.ui;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;

/* loaded from: classes3.dex */
public class JMTBulletinPopup extends CenterPopupView {
    private String content;

    public JMTBulletinPopup(Context context, String str) {
        super(context);
        this.content = str;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    protected int getImplLayoutId() {
        return R.layout.jmt_bulletin_popup;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    protected void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.iv_close_bg);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv_close);
        ((TextView) findViewById(R.id.tv_bulletin_title)).setText(LocaleController.getString("JMTBulletinTitle", R.string.JMTBulletinTitle));
        TextView textView = (TextView) findViewById(R.id.tv_bulletin_content);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText(this.content);
        Button button = (Button) findViewById(R.id.btn_bulletin_confirm);
        button.setText(LocaleController.getString("JMTBulletinKnow", R.string.JMTBulletinKnow));
        imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTBulletinPopup.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTBulletinPopup.this.dismiss();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTBulletinPopup.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTBulletinPopup.this.dismiss();
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTBulletinPopup.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JMTBulletinPopup.this.dismiss();
            }
        });
    }
}
