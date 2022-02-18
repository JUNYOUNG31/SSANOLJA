package com.ssanolja.backend.api.response;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpyfallRes implements GameRes{

    @Builder.Default
    private Map<String, String> jobs = new HashMap<String, String>(); // 닉네임, 직업

    private String place;

}
