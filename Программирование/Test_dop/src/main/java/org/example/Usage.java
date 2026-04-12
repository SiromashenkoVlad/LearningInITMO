package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Usage(
        @JsonProperty("prompt_tokens") int promptTokens,
        @JsonProperty("completion_tokens") int completionTokens,
        @JsonProperty("total_tokens") int totalTokens,
        @JsonProperty("cost") double cost,
        @JsonProperty("is_byok") boolean isByok,
        @JsonProperty("prompt_tokens_details") PromptTokensDetails promptTokensDetails,
        @JsonProperty("cost_details") CostDetails costDetails,
        @JsonProperty("completion_tokens_details") CompletionTokensDetails completionTokensDetails
) {}