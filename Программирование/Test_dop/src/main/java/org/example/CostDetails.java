package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CostDetails(
        @JsonProperty("upstream_inference_cost") double upstreamInferenceCost,
        @JsonProperty("upstream_inference_prompt_cost") double upstreamInferencePromptCost,
        @JsonProperty("upstream_inference_completions_cost") double upstreamInferenceCompletionsCost
) {}