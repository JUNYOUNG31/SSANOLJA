package com.ssanolja.backend.api.request;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartGameReq {

    @NotNull
    private List<String> users;

    @NotNull
    private String roomCode;

    @NotNull
    private String selectedGame;
}
