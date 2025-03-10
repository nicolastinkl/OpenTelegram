package com.tencent.cos.xml.transfer;

import android.content.Context;
import android.net.Uri;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.model.object.CopyObjectRequest;
import com.tencent.cos.xml.model.object.GetObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.tag.UrlUploadPolicy;
import com.tencent.cos.xml.transfer.COSXMLTask;
import java.io.InputStream;
import java.net.URL;

/* loaded from: classes.dex */
public class TransferManager {
    protected CosXmlSimpleService cosXmlService;
    protected TransferConfig transferConfig;

    public TransferManager(CosXmlSimpleService cosXmlSimpleService, TransferConfig transferConfig) {
        if (cosXmlSimpleService == null) {
            throw new IllegalArgumentException("CosXmlService is null");
        }
        if (transferConfig == null) {
            throw new IllegalArgumentException("TransferConfig is null");
        }
        this.cosXmlService = cosXmlSimpleService;
        this.transferConfig = transferConfig;
    }

    public COSXMLUploadTask upload(PutObjectRequest putObjectRequest, String str) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, putObjectRequest, str);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, String str3, String str4) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, str3, str4);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, Uri uri, String str3) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, uri, str3);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, URL url, String str3) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, url, str3);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, URL url, UrlUploadPolicy urlUploadPolicy, String str3) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, null, str, str2, url, urlUploadPolicy, str3);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, byte[] bArr) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, bArr);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, InputStream inputStream) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, inputStream);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.forceSimpleUpload = transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLUploadTask upload(String str, String str2, String str3, String str4, COSXMLTask.OnSignatureListener onSignatureListener) {
        COSXMLUploadTask cOSXMLUploadTask = new COSXMLUploadTask(this.cosXmlService, (String) null, str, str2, str3, str4);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLUploadTask.multiUploadSizeDivision = transferConfig.divisionForUpload;
        cOSXMLUploadTask.sliceSize = transferConfig.sliceSizeForUpload;
        cOSXMLUploadTask.setOnSignatureListener(onSignatureListener);
        cOSXMLUploadTask.forceSimpleUpload = this.transferConfig.isForceSimpleUpload();
        cOSXMLUploadTask.upload();
        return cOSXMLUploadTask;
    }

    public COSXMLDownloadTask download(Context context, String str, String str2, String str3) {
        return download(context, str, str2, str3, null);
    }

    public COSXMLDownloadTask download(Context context, String str, String str2, String str3, String str4) {
        COSXMLDownloadTask cOSXMLDownloadTask = new COSXMLDownloadTask(context, this.cosXmlService, null, str, str2, str3, str4);
        cOSXMLDownloadTask.download();
        return cOSXMLDownloadTask;
    }

    public COSXMLDownloadTask download(Context context, GetObjectRequest getObjectRequest) {
        COSXMLDownloadTask cOSXMLDownloadTask = new COSXMLDownloadTask(context, this.cosXmlService, getObjectRequest);
        cOSXMLDownloadTask.download();
        return cOSXMLDownloadTask;
    }

    public COSXMLDownloadTask download(Context context, String str, String str2, String str3, String str4, COSXMLTask.OnSignatureListener onSignatureListener) {
        COSXMLDownloadTask cOSXMLDownloadTask = new COSXMLDownloadTask(context, this.cosXmlService, null, str, str2, str3, str4);
        cOSXMLDownloadTask.setOnSignatureListener(onSignatureListener);
        cOSXMLDownloadTask.download();
        return cOSXMLDownloadTask;
    }

    public COSXMLCopyTask copy(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct) {
        COSXMLCopyTask cOSXMLCopyTask = new COSXMLCopyTask(this.cosXmlService, null, str, str2, copySourceStruct);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLCopyTask.multiCopySizeDivision = transferConfig.divisionForCopy;
        cOSXMLCopyTask.sliceSize = transferConfig.sliceSizeForCopy;
        cOSXMLCopyTask.copy();
        return cOSXMLCopyTask;
    }

    public COSXMLCopyTask copy(CopyObjectRequest copyObjectRequest) {
        COSXMLCopyTask cOSXMLCopyTask = new COSXMLCopyTask(this.cosXmlService, copyObjectRequest);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLCopyTask.multiCopySizeDivision = transferConfig.divisionForCopy;
        cOSXMLCopyTask.sliceSize = transferConfig.sliceSizeForCopy;
        cOSXMLCopyTask.copy();
        return cOSXMLCopyTask;
    }

    public COSXMLCopyTask copy(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct, COSXMLTask.OnSignatureListener onSignatureListener) {
        COSXMLCopyTask cOSXMLCopyTask = new COSXMLCopyTask(this.cosXmlService, null, str, str2, copySourceStruct);
        TransferConfig transferConfig = this.transferConfig;
        cOSXMLCopyTask.multiCopySizeDivision = transferConfig.divisionForCopy;
        cOSXMLCopyTask.sliceSize = transferConfig.sliceSizeForCopy;
        cOSXMLCopyTask.setOnSignatureListener(onSignatureListener);
        cOSXMLCopyTask.copy();
        return cOSXMLCopyTask;
    }

    public CosXmlSimpleService getCosXmlService() {
        return this.cosXmlService;
    }
}
