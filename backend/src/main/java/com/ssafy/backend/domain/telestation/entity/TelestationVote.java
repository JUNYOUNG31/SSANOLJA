package com.ssafy.backend.domain.telestation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "telestation_vote")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TelestationVote {

    @Id
    @Column(name = "telestation_vote_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer telestationVoteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telestations_id")
    private Telestation telestation;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "best")
    private Integer best;

    @Column(name = "worst")
    private Integer worst;
}
