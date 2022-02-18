package com.ssanolja.backend.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RuleRes {

    private final Integer playTime;
    private final Integer drawingTime;
    private final Integer meetingTime;
    private final Integer textingTime;
    private final Integer voteTime;
}
