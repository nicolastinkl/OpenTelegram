package org.telegram.tgnet;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import com.tencent.qimei.n.b;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;

/* loaded from: classes3.dex */
public abstract class TLRPC$ChatPhoto extends TLObject {
    public int dc_id;
    public int flags;
    public boolean has_video;
    public TLRPC$FileLocation photo_big;
    public long photo_id;
    public TLRPC$FileLocation photo_small;
    public BitmapDrawable strippedBitmap;
    public byte[] stripped_thumb;

    public static TLRPC$ChatPhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLdeserialize(abstractSerializedData, i, z, true);
    }

    public static TLRPC$ChatPhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z, boolean z2) {
        TLRPC$ChatPhoto tLRPC$ChatPhoto;
        switch (i) {
            case -770990276:
                tLRPC$ChatPhoto = new TLRPC$TL_chatPhoto() { // from class: org.telegram.tgnet.TLRPC$TL_chatPhoto_layer126
                    public static int constructor = -770990276;

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void readParams(AbstractSerializedData abstractSerializedData2, boolean z3) {
                        int readInt32 = abstractSerializedData2.readInt32(z3);
                        this.flags = readInt32;
                        this.has_video = (readInt32 & 1) != 0;
                        this.photo_small = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.photo_big = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.dc_id = abstractSerializedData2.readInt32(z3);
                    }

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                        abstractSerializedData2.writeInt32(constructor);
                        int i2 = this.has_video ? this.flags | 1 : this.flags & (-2);
                        this.flags = i2;
                        abstractSerializedData2.writeInt32(i2);
                        this.photo_small.serializeToStream(abstractSerializedData2);
                        this.photo_big.serializeToStream(abstractSerializedData2);
                        abstractSerializedData2.writeInt32(this.dc_id);
                    }
                };
                break;
            case 476978193:
                tLRPC$ChatPhoto = new TLRPC$TL_chatPhoto();
                break;
            case 935395612:
                tLRPC$ChatPhoto = new TLRPC$ChatPhoto() { // from class: org.telegram.tgnet.TLRPC$TL_chatPhotoEmpty
                    public static int constructor = 935395612;

                    @Override // org.telegram.tgnet.TLObject
                    public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                        abstractSerializedData2.writeInt32(constructor);
                    }
                };
                break;
            case 1197267925:
                tLRPC$ChatPhoto = new TLRPC$TL_chatPhoto() { // from class: org.telegram.tgnet.TLRPC$TL_chatPhoto_layer115
                    public static int constructor = 1197267925;

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void readParams(AbstractSerializedData abstractSerializedData2, boolean z3) {
                        this.photo_small = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.photo_big = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.dc_id = abstractSerializedData2.readInt32(z3);
                    }

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                        abstractSerializedData2.writeInt32(constructor);
                        this.photo_small.serializeToStream(abstractSerializedData2);
                        this.photo_big.serializeToStream(abstractSerializedData2);
                        abstractSerializedData2.writeInt32(this.dc_id);
                    }
                };
                break;
            case 1200680453:
                tLRPC$ChatPhoto = new TLRPC$TL_chatPhoto() { // from class: org.telegram.tgnet.TLRPC$TL_chatPhoto_layer127
                    public static int constructor = 1200680453;

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void readParams(AbstractSerializedData abstractSerializedData2, boolean z3) {
                        int readInt32 = abstractSerializedData2.readInt32(z3);
                        this.flags = readInt32;
                        this.has_video = (readInt32 & 1) != 0;
                        this.photo_small = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.photo_big = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        if ((this.flags & 2) != 0) {
                            this.stripped_thumb = abstractSerializedData2.readByteArray(z3);
                            if (Build.VERSION.SDK_INT >= 21) {
                                try {
                                    this.strippedBitmap = new BitmapDrawable(ImageLoader.getStrippedPhotoBitmap(this.stripped_thumb, b.a));
                                } catch (Throwable th) {
                                    FileLog.e(th);
                                }
                            }
                        }
                        this.dc_id = abstractSerializedData2.readInt32(z3);
                    }

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                        abstractSerializedData2.writeInt32(constructor);
                        int i2 = this.has_video ? this.flags | 1 : this.flags & (-2);
                        this.flags = i2;
                        abstractSerializedData2.writeInt32(i2);
                        this.photo_small.serializeToStream(abstractSerializedData2);
                        this.photo_big.serializeToStream(abstractSerializedData2);
                        if ((this.flags & 2) != 0) {
                            abstractSerializedData2.writeByteArray(this.stripped_thumb);
                        }
                        abstractSerializedData2.writeInt32(this.dc_id);
                    }
                };
                break;
            case 1632839530:
                tLRPC$ChatPhoto = new TLRPC$TL_chatPhoto() { // from class: org.telegram.tgnet.TLRPC$TL_chatPhoto_layer97
                    public static int constructor = 1632839530;

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void readParams(AbstractSerializedData abstractSerializedData2, boolean z3) {
                        this.photo_small = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                        this.photo_big = TLRPC$FileLocation.TLdeserialize(abstractSerializedData2, abstractSerializedData2.readInt32(z3), z3);
                    }

                    @Override // org.telegram.tgnet.TLRPC$TL_chatPhoto, org.telegram.tgnet.TLObject
                    public void serializeToStream(AbstractSerializedData abstractSerializedData2) {
                        abstractSerializedData2.writeInt32(constructor);
                        this.photo_small.serializeToStream(abstractSerializedData2);
                        this.photo_big.serializeToStream(abstractSerializedData2);
                    }
                };
                break;
            default:
                tLRPC$ChatPhoto = null;
                break;
        }
        if (tLRPC$ChatPhoto == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatPhoto", Integer.valueOf(i)));
        }
        if (tLRPC$ChatPhoto != null) {
            tLRPC$ChatPhoto.readParams(abstractSerializedData, z);
        }
        return tLRPC$ChatPhoto;
    }
}
