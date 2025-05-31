package com.affles.watchout.server.domain.disaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaturalDisasterApiResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("hasNextPage")
    private boolean hasNextPage;

    @JsonProperty("page")
    private int page;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("result")
    private NaturalDisasterEventResponse[] result;
}