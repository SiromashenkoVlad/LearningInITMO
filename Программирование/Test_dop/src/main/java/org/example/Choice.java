package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(
        @JsonProperty("index") int index,
        @JsonProperty("logprobs") Object logprobs, // 👈 важно!
        @JsonProperty("finish_reason") String finishReason,
        @JsonProperty("native_finish_reason") String nativeFinishReason,
        @JsonProperty("message") Message message
) {}