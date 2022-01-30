package com.ssafy.backend.domain.spyfall.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spyfall_jobs")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpyfallJob {

    @Id
    @Column(name = "spyfall_jobs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallJobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spyfall_places_id", nullable = false)
    private SpyfallPlace spyfallPlace;

    @Column(name = "job_name", nullable = false)
    private String jobName;
}
