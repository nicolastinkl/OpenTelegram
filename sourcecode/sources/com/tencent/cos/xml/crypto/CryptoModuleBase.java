package com.tencent.cos.xml.crypto;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.common.Range;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.model.object.UploadPartRequest;
import com.tencent.cos.xml.model.object.UploadPartResult;
import com.tencent.cos.xml.s3.Base64;
import com.tencent.cos.xml.utils.DigestUtils;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.core.util.QCloudUtils;
import com.tencentcloudapi.kms.v20190118.models.GenerateDataKeyRequest;
import com.tencentcloudapi.kms.v20190118.models.GenerateDataKeyResponse;
import j$.util.DesugarCollections;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;

/* loaded from: classes.dex */
public abstract class CryptoModuleBase implements CryptoModule {
    protected static final int DEFAULT_BUFFER_SIZE = 2048;
    private static final boolean IS_MULTI_PART = true;
    protected final ContentCryptoScheme contentCryptoScheme;

    /* renamed from: cos, reason: collision with root package name */
    protected final CosXmlSimpleService f18cos;
    protected final COSCryptoScheme cryptoScheme;
    protected final EncryptionMaterialsProvider kekMaterialsProvider;
    protected final QCLOUDKMS kms;
    protected final Map<String, MultipartUploadCryptoContext> multipartUploadContexts = DesugarCollections.synchronizedMap(new HashMap());

    abstract CipherLite cipherLiteForNextPart(MultipartUploadCryptoContext multipartUploadCryptoContext);

    protected abstract long ciphertextLength(long j);

    abstract long computeLastPartSize(UploadPartRequest uploadPartRequest);

    abstract MultipartUploadCryptoContext newUploadContext(InitMultipartUploadRequest initMultipartUploadRequest, ContentCryptoMaterial contentCryptoMaterial);

    protected CryptoModuleBase(QCLOUDKMS qcloudkms, CosXmlSimpleService cosXmlSimpleService, QCloudCredentialProvider qCloudCredentialProvider, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this.kekMaterialsProvider = encryptionMaterialsProvider;
        this.f18cos = cosXmlSimpleService;
        COSCryptoScheme from = COSCryptoScheme.from();
        this.cryptoScheme = from;
        this.contentCryptoScheme = from.getContentCryptoScheme();
        this.kms = qcloudkms;
    }

    protected CryptoModuleBase(CosXmlSimpleService cosXmlSimpleService, QCloudCredentialProvider qCloudCredentialProvider, EncryptionMaterialsProvider encryptionMaterialsProvider) {
        this.kekMaterialsProvider = encryptionMaterialsProvider;
        this.f18cos = cosXmlSimpleService;
        COSCryptoScheme from = COSCryptoScheme.from();
        this.cryptoScheme = from;
        this.contentCryptoScheme = from.getContentCryptoScheme();
        this.kms = null;
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public PutObjectResult putObjectSecurely(PutObjectRequest putObjectRequest) throws CosXmlClientException, CosXmlServiceException {
        ContentCryptoMaterial createContentCryptoMaterial = createContentCryptoMaterial(putObjectRequest);
        PutObjectRequest wrapWithCipher = wrapWithCipher(putObjectRequest, createContentCryptoMaterial);
        putObjectRequest.setMetadata(updateMetadataWithContentCryptoMaterial(putObjectRequest.getMetadata(), createContentCryptoMaterial));
        return this.f18cos.putObject(wrapWithCipher);
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public InitMultipartUploadResult initMultipartUploadSecurely(InitMultipartUploadRequest initMultipartUploadRequest) throws CosXmlClientException, CosXmlServiceException {
        cipherInitMultipartUploadRequest(initMultipartUploadRequest);
        ContentCryptoMaterial createContentCryptoMaterial = createContentCryptoMaterial(initMultipartUploadRequest);
        ObjectMetadata metadata = initMultipartUploadRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        initMultipartUploadRequest.setMetadata(updateMetadataWithContentCryptoMaterial(metadata, createContentCryptoMaterial));
        InitMultipartUploadResult initMultipartUpload = this.f18cos.initMultipartUpload(initMultipartUploadRequest);
        this.multipartUploadContexts.put(initMultipartUpload.initMultipartUpload.uploadId, newUploadContext(initMultipartUploadRequest, createContentCryptoMaterial));
        return initMultipartUpload;
    }

    public boolean hasMultipartUploadContext(String str) {
        return this.multipartUploadContexts.containsKey(str);
    }

    public MultipartUploadCryptoContext getCryptoContext(String str) {
        return this.multipartUploadContexts.get(str);
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public UploadPartResult uploadPartSecurely(UploadPartRequest uploadPartRequest) throws CosXmlClientException, CosXmlServiceException {
        int blockSizeInBytes = this.contentCryptoScheme.getBlockSizeInBytes();
        boolean isLastPart = uploadPartRequest.isLastPart();
        String uploadId = uploadPartRequest.getUploadId();
        boolean z = 0 == uploadPartRequest.getFileLength() % ((long) blockSizeInBytes);
        if (!isLastPart && !z) {
            throw CosXmlClientException.internalException("Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size (" + blockSizeInBytes + ") with the exception of the last part.");
        }
        MultipartUploadCryptoContext multipartUploadCryptoContext = this.multipartUploadContexts.get(uploadId);
        if (multipartUploadCryptoContext == null) {
            throw CosXmlClientException.internalException("No client-side information available on upload ID " + uploadId);
        }
        multipartUploadCryptoContext.beginPartUpload(uploadPartRequest.getPartNumber());
        try {
            uploadPartRequest.setInputStream(newMultipartCOSCipherInputStream(uploadPartRequest, cipherLiteForNextPart(multipartUploadCryptoContext)));
            uploadPartRequest.setSrcPath(null);
            uploadPartRequest.setFileOffset(0L);
            if (isLastPart) {
                long computeLastPartSize = computeLastPartSize(uploadPartRequest);
                if (computeLastPartSize > -1) {
                    uploadPartRequest.setFileContentLength(computeLastPartSize);
                }
                if (multipartUploadCryptoContext.hasFinalPartBeenSeen()) {
                    throw CosXmlClientException.internalException("This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part.");
                }
            }
            UploadPartResult uploadPart = this.f18cos.uploadPart(uploadPartRequest);
            if (isLastPart) {
                multipartUploadCryptoContext.setHasFinalPartBeenSeen(true);
            }
            return uploadPart;
        } finally {
            multipartUploadCryptoContext.endPartUpload();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00be  */
    @Override // com.tencent.cos.xml.crypto.CryptoModule
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void uploadPartAsyncSecurely(com.tencent.cos.xml.model.object.UploadPartRequest r12, com.tencent.cos.xml.listener.CosXmlResultListener r13) {
        /*
            r11 = this;
            boolean r0 = r12.isLastPart()
            r1 = 1
            r2 = 0
            com.tencent.cos.xml.crypto.ContentCryptoScheme r3 = r11.contentCryptoScheme     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            int r3 = r3.getBlockSizeInBytes()     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            java.lang.String r4 = r12.getUploadId()     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            long r5 = r12.getFileLength()     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            long r7 = (long) r3     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            long r5 = r5 % r7
            r7 = 0
            int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r9 != 0) goto L1e
            r5 = 1
            goto L1f
        L1e:
            r5 = 0
        L1f:
            if (r0 != 0) goto L3f
            if (r5 == 0) goto L24
            goto L3f
        L24:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            r4.<init>()     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            java.lang.String r5 = "Invalid part size: part sizes for encrypted multipart uploads must be multiples of the cipher block size ("
            r4.append(r5)     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            r4.append(r3)     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            java.lang.String r3 = ") with the exception of the last part."
            r4.append(r3)     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            com.tencent.cos.xml.exception.CosXmlClientException r3 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r3)     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            throw r3     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
        L3f:
            java.util.Map<java.lang.String, com.tencent.cos.xml.crypto.MultipartUploadCryptoContext> r3 = r11.multipartUploadContexts     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            java.lang.Object r3 = r3.get(r4)     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            com.tencent.cos.xml.crypto.MultipartUploadCryptoContext r3 = (com.tencent.cos.xml.crypto.MultipartUploadCryptoContext) r3     // Catch: java.lang.Throwable -> La5 com.tencent.cos.xml.exception.CosXmlClientException -> La7
            if (r3 == 0) goto L8f
            int r4 = r12.getPartNumber()     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r3.beginPartUpload(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            com.tencent.cos.xml.crypto.CipherLite r4 = r11.cipherLiteForNextPart(r3)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            com.tencent.cos.xml.crypto.CipherLiteInputStream r4 = r11.newMultipartCOSCipherInputStream(r12, r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r12.setInputStream(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r12.setSrcPath(r2)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r12.setFileOffset(r7)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            if (r0 == 0) goto L7e
            long r4 = r11.computeLastPartSize(r12)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r6 = -1
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 <= 0) goto L70
            r12.setFileContentLength(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
        L70:
            boolean r4 = r3.hasFinalPartBeenSeen()     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            if (r4 != 0) goto L77
            goto L7e
        L77:
            java.lang.String r4 = "This part was specified as the last part in a multipart upload, but a previous part was already marked as the last part.  Only the last part of the upload should be marked as the last part."
            com.tencent.cos.xml.exception.CosXmlClientException r4 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            throw r4     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
        L7e:
            com.tencent.cos.xml.CosXmlSimpleService r4 = r11.f18cos     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r4.uploadPartAsync(r12, r13)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r3.endPartUpload()
            goto Lb2
        L87:
            r12 = move-exception
            r2 = r3
            goto Lbc
        L8a:
            r4 = move-exception
            r10 = r4
            r4 = r3
            r3 = r10
            goto La9
        L8f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r5.<init>()     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            java.lang.String r6 = "No client-side information available on upload ID "
            r5.append(r6)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            r5.append(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            java.lang.String r4 = r5.toString()     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            com.tencent.cos.xml.exception.CosXmlClientException r4 = com.tencent.cos.xml.exception.CosXmlClientException.internalException(r4)     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
            throw r4     // Catch: java.lang.Throwable -> L87 com.tencent.cos.xml.exception.CosXmlClientException -> L8a
        La5:
            r12 = move-exception
            goto Lbc
        La7:
            r3 = move-exception
            r4 = r2
        La9:
            r13.onFail(r12, r3, r2)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto Lb1
            r4.endPartUpload()
        Lb1:
            r3 = r4
        Lb2:
            if (r0 == 0) goto Lb9
            if (r3 == 0) goto Lb9
            r3.setHasFinalPartBeenSeen(r1)
        Lb9:
            return
        Lba:
            r12 = move-exception
            r2 = r4
        Lbc:
            if (r2 == 0) goto Lc1
            r2.endPartUpload()
        Lc1:
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.crypto.CryptoModuleBase.uploadPartAsyncSecurely(com.tencent.cos.xml.model.object.UploadPartRequest, com.tencent.cos.xml.listener.CosXmlResultListener):void");
    }

    public final CipherLiteInputStream newMultipartCOSCipherInputStream(UploadPartRequest uploadPartRequest, CipherLite cipherLite) throws CosXmlClientException {
        InputStream openInputStream;
        String srcPath = uploadPartRequest.getSrcPath();
        Uri uri = uploadPartRequest.getUri();
        try {
            if (!TextUtils.isEmpty(srcPath)) {
                openInputStream = new ResettableInputStream(srcPath);
            } else {
                openInputStream = (uri == null || ContextHolder.getAppContext() == null) ? null : ContextHolder.getAppContext().getContentResolver().openInputStream(uri);
            }
            InputSubstream inputSubstream = new InputSubstream(openInputStream, uploadPartRequest.getFileOffset(), uploadPartRequest.getFileContentLength(), uploadPartRequest.isLastPart());
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(inputSubstream, cipherLite, 2048, true, uploadPartRequest.isLastPart());
            }
            return new RenewableCipherLiteInputStream(inputSubstream, cipherLite, 2048, true, uploadPartRequest.isLastPart());
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    @Override // com.tencent.cos.xml.crypto.CryptoModule
    public CompleteMultiUploadResult completeMultipartUploadSecurely(CompleteMultiUploadRequest completeMultiUploadRequest) throws CosXmlClientException, CosXmlServiceException {
        String uploadId = completeMultiUploadRequest.getUploadId();
        MultipartUploadCryptoContext multipartUploadCryptoContext = this.multipartUploadContexts.get(uploadId);
        if (multipartUploadCryptoContext != null && !multipartUploadCryptoContext.hasFinalPartBeenSeen()) {
            throw CosXmlClientException.internalException("Unable to complete an encrypted multipart upload without being told which part was the last.  Without knowing which part was the last, the encrypted data in COS is incomplete and corrupt.");
        }
        CompleteMultiUploadResult completeMultiUpload = this.f18cos.completeMultiUpload(completeMultiUploadRequest);
        this.multipartUploadContexts.remove(uploadId);
        return completeMultiUpload;
    }

    protected final ObjectMetadata updateMetadataWithContentCryptoMaterial(ObjectMetadata objectMetadata, ContentCryptoMaterial contentCryptoMaterial) throws CosXmlClientException {
        if (objectMetadata == null) {
            objectMetadata = new ObjectMetadata();
        }
        try {
            return contentCryptoMaterial.toObjectMetadata(objectMetadata);
        } catch (JSONException e) {
            throw CosXmlClientException.internalException(e.getMessage());
        }
    }

    protected final ContentCryptoMaterial createContentCryptoMaterial(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        return newContentCryptoMaterial(this.kekMaterialsProvider, null, cosXmlRequest);
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider encryptionMaterialsProvider, Map<String, String> map, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        EncryptionMaterials encryptionMaterials = encryptionMaterialsProvider.getEncryptionMaterials(map);
        if (encryptionMaterials == null) {
            return null;
        }
        return buildContentCryptoMaterial(encryptionMaterials, provider, cosXmlRequest);
    }

    private ContentCryptoMaterial newContentCryptoMaterial(EncryptionMaterialsProvider encryptionMaterialsProvider, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        EncryptionMaterials encryptionMaterials = encryptionMaterialsProvider.getEncryptionMaterials();
        if (encryptionMaterials == null) {
            throw CosXmlClientException.internalException("No material available from the encryption material provider");
        }
        return buildContentCryptoMaterial(encryptionMaterials, provider, cosXmlRequest);
    }

    private ContentCryptoMaterial buildContentCryptoMaterial(EncryptionMaterials encryptionMaterials, Provider provider, CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        byte[] bArr = new byte[this.contentCryptoScheme.getIVLengthInBytes()];
        this.cryptoScheme.getSecureRandom().nextBytes(bArr);
        if (encryptionMaterials.isKMSEnabled()) {
            Map<String, String> mergeMaterialDescriptions = ContentCryptoMaterial.mergeMaterialDescriptions(encryptionMaterials, cosXmlRequest);
            GenerateDataKeyRequest generateDataKeyRequest = new GenerateDataKeyRequest();
            try {
                generateDataKeyRequest.setEncryptionContext(JSONUtils.toJsonString(mergeMaterialDescriptions));
                generateDataKeyRequest.setKeyId(encryptionMaterials.getCustomerMasterKeyId());
                generateDataKeyRequest.setKeySpec(this.contentCryptoScheme.getKeySpec());
                GenerateDataKeyResponse generateDataKey = this.kms.generateDataKey(generateDataKeyRequest);
                return ContentCryptoMaterial.wrap(new SecretKeySpec(Base64.decode(generateDataKey.getPlaintext()), this.contentCryptoScheme.getKeyGeneratorAlgorithm()), bArr, this.contentCryptoScheme, provider, new KMSSecuredCEK(generateDataKey.getCiphertextBlob().getBytes(), mergeMaterialDescriptions));
            } catch (JSONException unused) {
                throw CosXmlClientException.internalException("generate datakey request set encryption context got json processing exception");
            }
        }
        return ContentCryptoMaterial.create(generateCEK(encryptionMaterials, provider), bArr, encryptionMaterials, this.cryptoScheme, provider, this.kms, cosXmlRequest);
    }

    protected final SecretKey generateCEK(EncryptionMaterials encryptionMaterials, Provider provider) throws CosXmlClientException {
        KeyGenerator keyGenerator;
        boolean z;
        String keyGeneratorAlgorithm = this.contentCryptoScheme.getKeyGeneratorAlgorithm();
        try {
            if (provider == null) {
                keyGenerator = KeyGenerator.getInstance(keyGeneratorAlgorithm);
            } else {
                keyGenerator = KeyGenerator.getInstance(keyGeneratorAlgorithm, provider);
            }
            keyGenerator.init(this.contentCryptoScheme.getKeyLengthInBits(), this.cryptoScheme.getSecureRandom());
            KeyPair keyPair = encryptionMaterials.getKeyPair();
            if (keyPair == null || this.cryptoScheme.getKeyWrapScheme().getKeyWrapAlgorithm(keyPair.getPublic()) != null) {
                z = false;
            } else {
                Provider provider2 = keyGenerator.getProvider();
                z = "BC".equals(provider2 == null ? null : provider2.getName());
            }
            SecretKey generateKey = keyGenerator.generateKey();
            if (z && generateKey.getEncoded()[0] == 0) {
                for (int i = 0; i < 10; i++) {
                    SecretKey generateKey2 = keyGenerator.generateKey();
                    if (generateKey2.getEncoded()[0] != 0) {
                        return generateKey2;
                    }
                }
                throw CosXmlClientException.internalException("Failed to generate secret key");
            }
            return generateKey;
        } catch (NoSuchAlgorithmException e) {
            throw CosXmlClientException.internalException("Unable to generate envelope symmetric key:" + e.getMessage());
        }
    }

    private InputStream openInputStream(PutObjectRequest putObjectRequest) throws IOException {
        String srcPath = putObjectRequest.getSrcPath();
        Uri uri = putObjectRequest.getUri();
        if (srcPath != null) {
            return new FileInputStream(srcPath);
        }
        if (uri == null || ContextHolder.getAppContext() == null) {
            return null;
        }
        return ContextHolder.getAppContext().getContentResolver().openInputStream(uri);
    }

    protected final PutObjectRequest wrapWithCipher(PutObjectRequest putObjectRequest, ContentCryptoMaterial contentCryptoMaterial) throws CosXmlClientException {
        ObjectMetadata metadata = putObjectRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        String contentMD5 = metadata.getContentMD5();
        if (TextUtils.isEmpty(contentMD5)) {
            try {
                InputStream openInputStream = openInputStream(putObjectRequest);
                if (openInputStream != null) {
                    contentMD5 = DigestUtils.getCOSMd5(openInputStream, 0L, -1L);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (contentMD5 != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, contentMD5);
        }
        metadata.setContentMD5(null);
        long plaintextLength = plaintextLength(putObjectRequest, metadata);
        if (plaintextLength >= 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(plaintextLength));
            metadata.setContentLength(ciphertextLength(plaintextLength));
        }
        putObjectRequest.setMetadata(metadata);
        putObjectRequest.setInputStream(newCOSCipherLiteInputStream(putObjectRequest, contentCryptoMaterial, plaintextLength));
        putObjectRequest.setSrcPath(null);
        return putObjectRequest;
    }

    protected final InitMultipartUploadRequest cipherInitMultipartUploadRequest(InitMultipartUploadRequest initMultipartUploadRequest) {
        ObjectMetadata metadata = initMultipartUploadRequest.getMetadata();
        if (metadata == null) {
            return initMultipartUploadRequest;
        }
        if (metadata.getContentMD5() != null) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_MD5, metadata.getContentMD5());
        }
        metadata.setContentMD5(null);
        if (metadata.getContentLength() != 0) {
            metadata.addUserMetadata(Headers.UNENCRYPTED_CONTENT_LENGTH, Long.toString(metadata.getContentLength()));
            metadata.setContentLength(0L);
        }
        initMultipartUploadRequest.setMetadata(metadata);
        return initMultipartUploadRequest;
    }

    private CipherLiteInputStream newCOSCipherLiteInputStream(PutObjectRequest putObjectRequest, ContentCryptoMaterial contentCryptoMaterial, long j) throws CosXmlClientException {
        try {
            InputStream openInputStream = openInputStream(putObjectRequest);
            if (j > -1) {
                openInputStream = new LengthCheckInputStream(openInputStream, j, false);
            }
            CipherLite cipherLite = contentCryptoMaterial.getCipherLite();
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(openInputStream, cipherLite, 2048);
            }
            return new RenewableCipherLiteInputStream(openInputStream, cipherLite, 2048);
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    public CipherLiteInputStream newCOSCipherLiteInputStream(PutObjectRequest putObjectRequest, CipherLite cipherLite) throws CosXmlClientException {
        try {
            InputStream openInputStream = openInputStream(putObjectRequest);
            if (cipherLite.markSupported()) {
                return new CipherLiteInputStream(openInputStream, cipherLite, 2048);
            }
            return new RenewableCipherLiteInputStream(openInputStream, cipherLite, 2048);
        } catch (Exception e) {
            throw CosXmlClientException.internalException("Unable to create cipher input stream: " + e.getMessage());
        }
    }

    protected final long plaintextLength(PutObjectRequest putObjectRequest, ObjectMetadata objectMetadata) {
        Context appContext;
        String srcPath = putObjectRequest.getSrcPath();
        Uri uri = putObjectRequest.getUri();
        if (!TextUtils.isEmpty(srcPath)) {
            return new File(srcPath).length();
        }
        if (uri == null || (appContext = ContextHolder.getAppContext()) == null) {
            return -1L;
        }
        return QCloudUtils.getUriContentLength(uri, appContext.getContentResolver());
    }

    public final COSCryptoScheme getCOSCryptoScheme() {
        return this.cryptoScheme;
    }

    static Range getAdjustedCryptoRange(Range range) {
        if (range == null) {
            return null;
        }
        return new Range(getCipherBlockLowerBound(range.getStart()), range.getEnd() != -1 ? getCipherBlockUpperBound(range.getEnd()) : -1L);
    }

    static long[] getAdjustedCryptoRange(long[] jArr) {
        if (jArr == null || jArr[0] > jArr[1]) {
            return null;
        }
        return new long[]{getCipherBlockLowerBound(jArr[0]), getCipherBlockUpperBound(jArr[1])};
    }

    private static long getCipherBlockLowerBound(long j) {
        long j2 = (j - (j % 16)) - 16;
        if (j2 < 0) {
            return 0L;
        }
        return j2;
    }

    private static long getCipherBlockUpperBound(long j) {
        long j2 = j + (16 - (j % 16)) + 16;
        if (j2 < 0) {
            return Long.MAX_VALUE;
        }
        return j2;
    }
}
