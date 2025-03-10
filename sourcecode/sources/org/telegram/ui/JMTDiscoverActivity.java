package org.telegram.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.XPopup;
import com.youth.banner.Banner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$JMT_jConfigurationBanner;
import org.telegram.tgnet.TLRPC$JMT_response_link;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.JMTExclusivePopup;

/* loaded from: classes3.dex */
public class JMTDiscoverActivity extends BaseFragment {
    public static JMTDiscoverActivity activity;
    private DiscoverAdapter adapter;
    private Banner banner;
    private Context mContext;
    private List<TLRPC$JMT_response_link> mList;
    private RecyclerView recyclerView;

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        activity = this;
        this.mContext = context;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("JMTDiscover", R.string.JMTDiscover));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.JMTDiscoverActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                JMTDiscoverActivity.this.finishFragment();
            }
        });
        if (BuildVars.isShowExclusiveChannel) {
            this.actionBar.createMenu().addItem(0, LocaleController.getString("JMTExclusiveChannel", R.string.JMTExclusiveChannel)).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTDiscoverActivity.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    new XPopup.Builder(context).autoOpenSoftInput(Boolean.TRUE).asCustom(new JMTExclusivePopup(context, new JMTExclusivePopup.OnSuccessListener() { // from class: org.telegram.ui.JMTDiscoverActivity.2.1
                        @Override // org.telegram.ui.JMTExclusivePopup.OnSuccessListener
                        public void onSuccess(String str) {
                            JMTDiscoverActivity.this.presentFragment(new JMTDiscoverWebActivity(str, 0, ImageLocation.getForPath(BuildVars.exclusiveChannelImageUrl)));
                        }
                    })).show();
                }
            });
        }
        FrameLayout frameLayout = new FrameLayout(context);
        this.fragmentView = frameLayout;
        View inflate = View.inflate(context, R.layout.activity_jmt_discover, null);
        this.banner = (Banner) inflate.findViewById(R.id.bb_banner);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_list);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ArrayList arrayList = new ArrayList();
        this.mList = arrayList;
        DiscoverAdapter discoverAdapter = new DiscoverAdapter(context, arrayList);
        this.adapter = discoverAdapter;
        this.recyclerView.setAdapter(discoverAdapter);
        frameLayout.addView(inflate, LayoutHelper.createFrame(-1, -1.0f));
        return this.fragmentView;
    }

    /* renamed from: org.telegram.ui.JMTDiscoverActivity$3, reason: invalid class name */
    class AnonymousClass3 implements RequestDelegate {
        AnonymousClass3() {
        }

        /* renamed from: org.telegram.ui.JMTDiscoverActivity$3$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            final /* synthetic */ TLRPC$TL_error val$error;
            final /* synthetic */ TLObject val$response;

            AnonymousClass1(TLRPC$TL_error tLRPC$TL_error, TLObject tLObject) {
                this.val$error = tLRPC$TL_error;
                this.val$response = tLObject;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (this.val$error == null) {
                    TLObject tLObject = this.val$response;
                    if (tLObject instanceof TLRPC$Vector) {
                        ArrayList arrayList = new ArrayList();
                        Iterator<Object> it = ((TLRPC$Vector) tLObject).objects.iterator();
                        while (it.hasNext()) {
                            Object next = it.next();
                            if (next instanceof TLRPC$JMT_jConfigurationBanner) {
                                arrayList.add((TLRPC$JMT_jConfigurationBanner) next);
                            }
                        }
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        JMTDiscoverActivity.this.banner.setAdapter(new JMTBannerImageAdapter<TLRPC$JMT_jConfigurationBanner>(arrayList) { // from class: org.telegram.ui.JMTDiscoverActivity.3.1.1
                            @Override // com.youth.banner.holder.IViewHolder
                            public void onBindView(JMTBannerImageHolder jMTBannerImageHolder, final TLRPC$JMT_jConfigurationBanner tLRPC$JMT_jConfigurationBanner, int i, int i2) {
                                final ImageLocation forPhoto = ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(tLRPC$JMT_jConfigurationBanner.photo.sizes, AndroidUtilities.getPhotoSize()), tLRPC$JMT_jConfigurationBanner.photo);
                                jMTBannerImageHolder.imageView.setImage(forPhoto, "720_720", (Drawable) null, (Object) null);
                                jMTBannerImageHolder.imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTDiscoverActivity.3.1.1.1
                                    @Override // android.view.View.OnClickListener
                                    public void onClick(View view) {
                                        WebView webView = DialogsActivity.discoverWebView;
                                        if (webView != null) {
                                            webView.removeAllViews();
                                            DialogsActivity.discoverWebView.destroy();
                                            DialogsActivity.discoverWebView = null;
                                        }
                                        JMTDiscoverActivity.this.presentFragment(new JMTDiscoverWebActivity(tLRPC$JMT_jConfigurationBanner.url, 0, forPhoto));
                                    }
                                });
                            }
                        });
                        JMTDiscoverActivity.this.banner.setVisibility(0);
                    }
                }
            }
        }

        @Override // org.telegram.tgnet.RequestDelegate
        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new AnonymousClass1(tLRPC$TL_error, tLObject));
        }
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$JMT_getBanners
            public static int constructor = -1803797834;

            @Override // org.telegram.tgnet.TLObject
            public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
                TLRPC$Vector tLRPC$Vector = new TLRPC$Vector();
                int readInt32 = abstractSerializedData.readInt32(z);
                for (int i2 = 0; i2 < readInt32; i2++) {
                    TLRPC$JMT_jConfigurationBanner TLdeserialize = TLRPC$JMT_jConfigurationBanner.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize == null) {
                        return tLRPC$Vector;
                    }
                    tLRPC$Vector.objects.add(TLdeserialize);
                }
                return tLRPC$Vector;
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
            }
        }, new AnonymousClass3());
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(new TLObject() { // from class: org.telegram.tgnet.TLRPC$JMT_request_link
            public static int constructor = -1444484253;

            @Override // org.telegram.tgnet.TLObject
            public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
                TLRPC$Vector tLRPC$Vector = new TLRPC$Vector();
                int readInt32 = abstractSerializedData.readInt32(z);
                for (int i2 = 0; i2 < readInt32; i2++) {
                    TLRPC$JMT_response_link TLdeserialize = TLRPC$JMT_response_link.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize == null) {
                        return tLRPC$Vector;
                    }
                    tLRPC$Vector.objects.add(TLdeserialize);
                }
                return tLRPC$Vector;
            }

            @Override // org.telegram.tgnet.TLObject
            public void serializeToStream(AbstractSerializedData abstractSerializedData) {
                abstractSerializedData.writeInt32(constructor);
            }
        }, new RequestDelegate() { // from class: org.telegram.ui.JMTDiscoverActivity.4
            @Override // org.telegram.tgnet.RequestDelegate
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.JMTDiscoverActivity.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TLRPC$TL_error tLRPC$TL_error2 = tLRPC$TL_error;
                        if (tLRPC$TL_error2 == null) {
                            TLObject tLObject2 = tLObject;
                            if (tLObject2 instanceof TLRPC$Vector) {
                                JMTDiscoverActivity.this.mList.clear();
                                Iterator<Object> it = ((TLRPC$Vector) tLObject2).objects.iterator();
                                while (it.hasNext()) {
                                    Object next = it.next();
                                    if (next instanceof TLRPC$JMT_response_link) {
                                        JMTDiscoverActivity.this.mList.add((TLRPC$JMT_response_link) next);
                                    }
                                }
                                JMTDiscoverActivity.this.adapter.notifyDataSetChanged();
                                return;
                            }
                            return;
                        }
                        if (tLRPC$TL_error2.text != null) {
                            Toast.makeText(JMTDiscoverActivity.this.mContext, tLRPC$TL_error.text, 0).show();
                        }
                    }
                });
            }
        });
        return super.onFragmentCreate();
    }

    @Override // org.telegram.ui.ActionBar.BaseFragment
    public void onFragmentClosed() {
        activity = null;
        super.onFragmentClosed();
    }

    class DiscoverAdapter extends RecyclerView.Adapter<DiscoverHolder> {
        private Context mContext;
        private List<TLRPC$JMT_response_link> mList;

        public DiscoverAdapter(Context context, List<TLRPC$JMT_response_link> list) {
            this.mContext = context;
            this.mList = list;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public DiscoverHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new DiscoverHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_discover, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(DiscoverHolder discoverHolder, int i) {
            final TLRPC$JMT_response_link tLRPC$JMT_response_link = this.mList.get(i);
            final ImageLocation forPhoto = ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(tLRPC$JMT_response_link.cover.sizes, AndroidUtilities.getPhotoSize()), tLRPC$JMT_response_link.cover);
            discoverHolder.bivIcon.setImage(forPhoto, "40_40", (Drawable) null, (Object) null);
            discoverHolder.tvTitle.setText(tLRPC$JMT_response_link.title);
            discoverHolder.llItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.JMTDiscoverActivity.DiscoverAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    WebView webView = DialogsActivity.discoverWebView;
                    if (webView != null) {
                        webView.removeAllViews();
                        DialogsActivity.discoverWebView.destroy();
                        DialogsActivity.discoverWebView = null;
                    }
                    JMTDiscoverActivity jMTDiscoverActivity = JMTDiscoverActivity.this;
                    TLRPC$JMT_response_link tLRPC$JMT_response_link2 = tLRPC$JMT_response_link;
                    jMTDiscoverActivity.presentFragment(new JMTDiscoverWebActivity(tLRPC$JMT_response_link2.url, tLRPC$JMT_response_link2.vpnPort, forPhoto));
                }
            });
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mList.size();
        }

        class DiscoverHolder extends RecyclerView.ViewHolder {
            BackupImageView bivIcon;
            LinearLayout llItem;
            TextView tvTitle;
            View vDivider;

            private DiscoverHolder(DiscoverAdapter discoverAdapter, View view) {
                super(view);
                this.llItem = (LinearLayout) view.findViewById(R.id.ll_item);
                BackupImageView backupImageView = (BackupImageView) view.findViewById(R.id.biv_icon);
                this.bivIcon = backupImageView;
                backupImageView.setRoundRadius(AndroidUtilities.dp(20.0f));
                TextView textView = (TextView) view.findViewById(R.id.tv_title);
                this.tvTitle = textView;
                textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                View findViewById = view.findViewById(R.id.v_divider);
                this.vDivider = findViewById;
                findViewById.setBackgroundColor(Theme.getColor(Theme.key_divider));
            }
        }
    }
}
