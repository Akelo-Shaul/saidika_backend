package com.shaul.saidikaV3.requestModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Body {
    @JsonProperty("stkCallback")
    private stkCallback stkCallback;
}

