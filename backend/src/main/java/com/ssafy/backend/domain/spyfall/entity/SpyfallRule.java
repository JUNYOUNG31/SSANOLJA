package com.ssafy.backend.domain.spyfall.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spyfall_rules")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpyfallRule {

    @Id
    @Column(name = "spyfall_rules_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallRuleId;

    @Column(name = "play_time")
    private Integer playTime;

    @Column(name = "vote_time")
    private Integer voteTime;

    @Column(name = "min_people")
    private Integer minPeople;

    @Column(name = "max_people")
    private Integer maxPeople;
}
