package client.aiMod.communicateWithModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Choice(
        @JsonProperty("index") int index,
        @JsonProperty("logprobs") Object logprobs,
        @JsonProperty("finish_reason") String finishReason,
        @JsonProperty("native_finish_reason") String nativeFinishReason,
        @JsonProperty("message") MessageFromModel messageFromModel
) {}