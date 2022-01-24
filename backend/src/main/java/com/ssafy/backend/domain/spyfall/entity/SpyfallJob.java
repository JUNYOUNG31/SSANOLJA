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
public class SpyfallJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallJobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spyfall_place_id")
    private SpyfallPlace spyfallPlace;

    @Column(length = 45, nullable = false)
    private String spyfallJob;
}
