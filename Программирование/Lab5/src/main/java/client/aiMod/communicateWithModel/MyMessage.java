package client.aiMod.communicateWithModel;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MyMessage(
        @JsonProperty("role") String role,
        @JsonProperty("content") String content,
        @JsonProperty("refusal") String refusal,
        @JsonProperty("reasoning") String reasoning


) {
    public MyMessage(String rl, String content){
        this(rl, content, null, null);
    }

}
