package com.ssanolja.backend.api.response;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelestationRes implements GameRes {
//game id 추가

    private Integer playGameId;

}
