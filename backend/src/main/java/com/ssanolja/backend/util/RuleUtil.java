package com.ssanolja.backend.util;

import lombok.Getter;

@Getter
public enum RuleUtil {
    SPYFALL(1,480,0,0, 0,30,3,8),
    FAKEARTIST(2,0,5,60, 20,20,3,8),
    TELESTATION(3,0,120,0,30, 30,1,8);

    private final int gameType;
    private final Integer playTime;
    private final Integer drawingTime;
    private final Integer meetingTime;
    private final Integer textingTime;
    private final Integer voteTime;
    private final Integer minPeople;
    private final Integer maxPeople;

    RuleUtil(int gameType, Integer playTime, Integer drawingTime, Integer meetingTime, Integer textingTime, Integer voteTime, Integer minPeople, Integer maxPeople) {
        this.gameType = gameType;
        this.playTime = playTime;
        this.drawingTime = drawingTime;
        this.meetingTime = meetingTime;
        this.textingTime = textingTime;
        this.voteTime = voteTime;
        this.minPeople = minPeople;
        this.maxPeople = maxPeople;
    }
}