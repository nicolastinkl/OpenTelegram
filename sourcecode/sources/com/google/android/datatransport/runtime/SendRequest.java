package com.google.android.datatransport.runtime;

import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.runtime.AutoValue_SendRequest;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes.dex */
abstract class SendRequest {

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract SendRequest build();

        abstract Builder setEncoding(Encoding encoding);

        abstract Builder setEvent(Event<?> event);

        abstract Builder setTransformer(Transformer<?, byte[]> transformer);

        public abstract Builder setTransportContext(TransportContext transportContext);

        public abstract Builder setTransportName(String str);
    }

    public abstract Encoding getEncoding();

    abstract Event<?> getEvent();

    abstract Transformer<?, byte[]> getTransformer();

    public abstract TransportContext getTransportContext();

    public abstract String getTransportName();

    SendRequest() {
    }

    public byte[] getPayload() {
        return getTransformer().apply(getEvent().getPayload());
    }

    public static Builder builder() {
        return new AutoValue_SendRequest.Builder();
    }
}
