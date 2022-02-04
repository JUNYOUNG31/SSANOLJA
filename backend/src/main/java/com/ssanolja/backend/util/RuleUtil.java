package com.ssanolja.backend.util;

import lombok.Getter;

@Getter
public enum RuleUtil {
    SPYFALL(1,0,0,0,0,3,8),
    FAKEARTIST(2,0,0,0,0,3,8),
    TELESTATION(3,0,0,0,0,3,8);

    private final Integer gameType;
    private final Integer playTime;
    private final Integer drawingTime;
    private final Integer meetingTime;
    private final Integer voteTime;
    private final Integer minPeople;
    private final Integer maxPeople;

    RuleUtil(Integer gameType, Integer playTime, Integer drawingTime, Integer meetingTime, Integer voteTime, Integer minPeople, Integer maxPeople) {
        this.gameType = gameType;
        this.playTime = playTime;
        this.drawingTime = drawingTime;
        this.meetingTime = meetingTime;
        this.voteTime = voteTime;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
    }
}
