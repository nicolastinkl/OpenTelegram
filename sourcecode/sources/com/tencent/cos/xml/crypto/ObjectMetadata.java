package com.tencent.cos.xml.crypto;

import android.text.TextUtils;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class ObjectMetadata {
    private static final long serialVersionUID = 1;
    private Date expirationTime;
    private String expirationTimeRuleId;
    private Date httpExpiresDate;
    private boolean isDeleteMarker;
    private Map<String, Object> metadata;
    private Boolean ongoingRestore;
    private Date restoreExpirationTime;
    private Map<String, String> userMetadata;

    public Map<String, String> getUserMetadata() {
        return this.userMetadata;
    }

    public void setUserMetadata(Map<String, String> map) {
        this.userMetadata = map;
    }

    public void setHeader(String str, Object obj) {
        this.metadata.put(str, obj);
    }

    public void addUserMetadata(String str, String str2) {
        this.userMetadata.put(str, str2);
    }

    public Map<String, Object> getRawMetadata() {
        return Collections.unmodifiableMap(this.metadata);
    }

    public Object getRawMetadataValue(String str) {
        return this.metadata.get(str);
    }

    public Date getLastModified() {
        return (Date) this.metadata.get(Headers.LAST_MODIFIED);
    }

    public void setLastModified(Date date) {
        this.metadata.put(Headers.LAST_MODIFIED, date);
    }

    public long getContentLength() {
        Long l = (Long) this.metadata.get(Headers.CONTENT_LENGTH);
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    public long getInstanceLength() {
        int lastIndexOf;
        String str = (String) this.metadata.get(Headers.CONTENT_RANGE);
        if (str != null && (lastIndexOf = str.lastIndexOf("/")) >= 0) {
            return Long.parseLong(str.substring(lastIndexOf + 1));
        }
        return getContentLength();
    }

    public void setContentLength(long j) {
        this.metadata.put(Headers.CONTENT_LENGTH, Long.valueOf(j));
    }

    public String getContentType() {
        return (String) this.metadata.get(Headers.CONTENT_TYPE);
    }

    public void setContentType(String str) {
        this.metadata.put(Headers.CONTENT_TYPE, str);
    }

    public String getContentLanguage() {
        return (String) this.metadata.get(Headers.CONTENT_LANGUAGE);
    }

    public void setContentLanguage(String str) {
        this.metadata.put(Headers.CONTENT_LANGUAGE, str);
    }

    public String getContentEncoding() {
        return (String) this.metadata.get("Content-Encoding");
    }

    public void setContentEncoding(String str) {
        this.metadata.put("Content-Encoding", str);
    }

    public String getCacheControl() {
        return (String) this.metadata.get("Cache-Control");
    }

    public void setCacheControl(String str) {
        this.metadata.put("Cache-Control", str);
    }

    public void setContentMD5(String str) {
        if (str == null) {
            this.metadata.remove(Headers.CONTENT_MD5);
        } else {
            this.metadata.put(Headers.CONTENT_MD5, str);
        }
    }

    public String getContentMD5() {
        return (String) this.metadata.get(Headers.CONTENT_MD5);
    }

    public void setContentDisposition(String str) {
        this.metadata.put("Content-Disposition", str);
    }

    public String getContentDisposition() {
        return (String) this.metadata.get("Content-Disposition");
    }

    public String getETag() {
        return (String) this.metadata.get(Headers.ETAG);
    }

    public String getVersionId() {
        return (String) this.metadata.get(Headers.COS_VERSION_ID);
    }

    public String getServerSideEncryption() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    public void setServerSideEncryption(String str) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION, str);
    }

    public void setSecurityToken(String str) {
        this.metadata.put(Headers.SECURITY_TOKEN, str);
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Date date) {
        this.expirationTime = date;
    }

    public void setHttpExpiresDate(Date date) {
        this.httpExpiresDate = date;
    }

    public Date getHttpExpiresDate() {
        return this.httpExpiresDate;
    }

    public Date getRestoreExpirationTime() {
        return this.restoreExpirationTime;
    }

    public void setRestoreExpirationTime(Date date) {
        this.restoreExpirationTime = date;
    }

    public void setOngoingRestore(boolean z) {
        this.ongoingRestore = Boolean.valueOf(z);
    }

    public Boolean getOngoingRestore() {
        return this.ongoingRestore;
    }

    public boolean isDeleteMarker() {
        return this.isDeleteMarker;
    }

    public void setDeleteMarker(boolean z) {
        this.isDeleteMarker = z;
    }

    public String getUserMetaDataOf(String str) {
        Map<String, String> map = this.userMetadata;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public ObjectMetadata() {
        this.userMetadata = new HashMap();
        this.metadata = new HashMap();
    }

    public ObjectMetadata(Map<String, List<String>> map) {
        this.userMetadata = new HashMap();
        this.metadata = new HashMap();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            String str = null;
            if (value != null && !value.isEmpty()) {
                str = value.get(0);
            }
            if (!TextUtils.isEmpty(key) && str != null) {
                if (key.startsWith(Headers.COS_USER_METADATA_PREFIX)) {
                    this.userMetadata.put(key, str);
                } else {
                    this.metadata.put(key, str);
                }
            }
        }
    }

    private ObjectMetadata(ObjectMetadata objectMetadata) {
        this.userMetadata = objectMetadata.userMetadata == null ? null : new HashMap(objectMetadata.userMetadata);
        this.metadata = objectMetadata.metadata != null ? new HashMap(objectMetadata.metadata) : null;
        this.expirationTime = objectMetadata.expirationTime;
        this.expirationTimeRuleId = objectMetadata.expirationTimeRuleId;
        this.httpExpiresDate = objectMetadata.httpExpiresDate;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ObjectMetadata m154clone() {
        return new ObjectMetadata(this);
    }

    public String getSSEAlgorithm() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION);
    }

    public void setSSEAlgorithm(String str) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION, str);
    }

    public String getSSECustomerAlgorithm() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM);
    }

    public void setSSECustomerAlgorithm(String str) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM, str);
    }

    public String getSSECustomerKeyMd5() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5);
    }

    public void setSSECustomerKeyMd5(String str) {
        this.metadata.put(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5, str);
    }

    public String getStorageClass() {
        Object obj = this.metadata.get("x-cos-storage-class");
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public String getSSECOSKmsKeyId() {
        return (String) this.metadata.get(Headers.SERVER_SIDE_ENCRYPTION_QCLOUD_KMS_KEYID);
    }

    public String getCrc64Ecma() {
        return (String) this.metadata.get(Headers.COS_HASH_CRC64_ECMA);
    }
}
