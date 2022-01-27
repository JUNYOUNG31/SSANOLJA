package com.ssafy.backend.domain.telestation.entity;

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
public class TelestationVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer telestationVoteId;

    @Column(nullable = false)
    private Integer best;

    @Column(nullable = false)
    private Integer worst;
}
