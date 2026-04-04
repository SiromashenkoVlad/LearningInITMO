package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ChatResponse(
        @JsonProperty("id") String id,
        @JsonProperty("object") String object,
        @JsonProperty("created") long created,
        @JsonProperty("model") String model,
        @JsonProperty("provider") String provider,
        @JsonProperty("system_fingerprint") String systemFingerprint,
        @JsonProperty("choices") List<Choice> choices,
        @JsonProperty("usage") Usage usage
) {}