package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class AssetDataSource extends BaseDataSource {
    private final AssetManager assetManager;
    private long bytesRemaining;
    private InputStream inputStream;
    private boolean opened;
    private Uri uri;

    public static final class AssetDataSourceException extends DataSourceException {
        public AssetDataSourceException(Throwable th, int i) {
            super(th, i);
        }
    }

    public AssetDataSource(Context context) {
        super(false);
        this.assetManager = context.getAssets();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws AssetDataSourceException {
        try {
            Uri uri = dataSpec.uri;
            this.uri = uri;
            String str = (String) Assertions.checkNotNull(uri.getPath());
            if (str.startsWith("/android_asset/")) {
                str = str.substring(15);
            } else if (str.startsWith("/")) {
                str = str.substring(1);
            }
            transferInitializing(dataSpec);
            InputStream open = this.assetManager.open(str, 1);
            this.inputStream = open;
            if (open.skip(dataSpec.position) < dataSpec.position) {
                throw new AssetDataSourceException(null, 2008);
            }
            long j = dataSpec.length;
            if (j != -1) {
                this.bytesRemaining = j;
            } else {
                long available = this.inputStream.available();
                this.bytesRemaining = available;
                if (available == 2147483647L) {
                    this.bytesRemaining = -1L;
                }
            }
            this.opened = true;
            transferStarted(dataSpec);
            return this.bytesRemaining;
        } catch (AssetDataSourceException e) {
            throw e;
        } catch (IOException e2) {
            throw new AssetDataSourceException(e2, e2 instanceof FileNotFoundException ? 2005 : 2000);
        }
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) throws AssetDataSourceException {
        if (i2 == 0) {
            return 0;
        }
        long j = this.bytesRemaining;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i2 = (int) Math.min(j, i2);
            } catch (IOException e) {
                throw new AssetDataSourceException(e, 2000);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.inputStream)).read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        long j2 = this.bytesRemaining;
        if (j2 != -1) {
            this.bytesRemaining = j2 - read;
        }
        bytesTransferred(read);
        return read;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return this.uri;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws AssetDataSourceException {
        this.uri = null;
        try {
            try {
                InputStream inputStream = this.inputStream;
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new AssetDataSourceException(e, 2000);
            }
        } finally {
            this.inputStream = null;
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        }
    }
}
