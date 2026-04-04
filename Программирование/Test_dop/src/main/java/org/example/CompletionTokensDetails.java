package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompletionTokensDetails(
        @JsonProperty("reasoning_tokens") int reasoningTokens,
        @JsonProperty("image_tokens") int imageTokens,
        @JsonProperty("audio_tokens") int audioTokens
) {}