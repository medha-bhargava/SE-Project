package com.muj.se.codeerrordetection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorItem {

    @JsonProperty("message")
    private String message;

    @JsonProperty("lineNumber")
    private int lineNumber;
}
