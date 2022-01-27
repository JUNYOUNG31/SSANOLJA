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
public class FakeArtistJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fakeArtistJobId;

    @Column(nullable = false, length = 45)
    private String artistJob;
}
