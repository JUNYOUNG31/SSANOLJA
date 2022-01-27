package com.ssafy.backend.domain.spyfall.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpyfallRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallRuleId;

    @Column(length = 45)
    private String spyfallGametime;

    @Column(length = 45)
    private String spyfallQuestionTime;

    private Integer spyfallPersonnelMin;

    private Integer spyfallPersonnelMax;

}
