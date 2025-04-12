package com.muj.se.codeerrordetection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    @JsonProperty("hasSyntaxErrors")
    private boolean hasSyntaxErrors;

    @JsonProperty("syntaxErrors")
    private List<ErrorItem> syntaxErrorList;

    @JsonProperty("hasLogicalErrors")
    private boolean hasLogicalErrors;

    @JsonProperty("logicalErrors")
    private List<ErrorItem> logicalErrorList;
}
