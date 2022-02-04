package com.ssanolja.backend.db.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "telestation_rules")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TelestationRule {


    @Id
    @Column(name = "telestation_rules_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer telestationRuleId;

    @Column(name = "drawing_time")
    private Integer drawingTime;

    @Column(name = "texting_time")
    private Integer textingTime;

    @Column(name = "vote_time")
    private Integer voteTime;

    @Column(name = "min_people")
    private Integer minPeople;

    @Column(name = "max_people")
    private Integer maxPeople;
}
