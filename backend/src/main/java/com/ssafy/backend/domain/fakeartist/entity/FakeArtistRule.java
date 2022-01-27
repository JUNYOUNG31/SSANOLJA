package com.ssafy.backend.domain.fakeartist.entity;

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
public class FakeArtistRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fakeArtistRuleId;

    @Column(nullable = false)
    private Integer fakeArtistPersonnelMin;

    @Column(nullable = false)
    private Integer fakeArtistPersonnelMax;


    private Integer fakeArtistArtTime;


}
