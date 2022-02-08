package com.ssanolja.backend.api.request;

import com.ssanolja.backend.api.response.GameRes;
import com.ssanolja.backend.db.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TelestationReq implements GameRes {

    private String userNickname;
    private String keyword;
    private String roomCode;
    private Integer drawingOrder;;
    private Integer personnel;

}
