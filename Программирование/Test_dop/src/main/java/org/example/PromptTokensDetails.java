package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PromptTokensDetails(
        @JsonProperty("cached_tokens") int cachedTokens,
        @JsonProperty("cache_write_tokens") int cacheWriteTokens,
        @JsonProperty("audio_tokens") int audioTokens,
        @JsonProperty("video_tokens") int videoTokens
) {}