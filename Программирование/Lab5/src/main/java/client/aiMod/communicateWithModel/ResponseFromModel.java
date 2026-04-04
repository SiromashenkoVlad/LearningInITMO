package client.aiMod.communicateWithModel;

import client.aiMod.AiStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.requests.Request;

public record ResponseFromModel (
        @JsonProperty("status") AiStatus status,
        @JsonProperty("answer") String answer,
        @JsonProperty("request") Request request
        ){}
