package com.muj.se.codeerrordetection.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {

    @JsonProperty("language")
    private String language;

    @JsonProperty("code")
    private String content;
}
