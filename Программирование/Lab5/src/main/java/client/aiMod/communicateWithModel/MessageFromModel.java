package client.aiMod.communicateWithModel;


import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageFromModel(
        @JsonProperty("role") String role,
        @JsonProperty("content") String content,
        @JsonProperty("refusal") String refusal,
        @JsonProperty("reasoning") String reasoning
) {}