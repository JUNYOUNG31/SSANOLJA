package com.ssanolja.backend.api.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRuleReq {

    @NotNull
    private Integer personnel;

    @NotNull
    private String selectedGame;
}