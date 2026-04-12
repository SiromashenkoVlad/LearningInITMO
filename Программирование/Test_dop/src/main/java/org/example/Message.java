package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
        @JsonProperty("role") String role,
        @JsonProperty("content") String content,
        @JsonProperty("refusal") String refusal,
        @JsonProperty("reasoning") String reasoning
) {
    public Message(String rl, String content){
        this(rl, content, null, null);
    }
}