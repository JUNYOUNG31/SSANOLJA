package com.ssanolja.backend.db.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spyfalls")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spyfall {

    @Id
    @Column(name = "spyfalls_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "spyfall_jobs_id")
    private SpyfallJob spyfallJob;

    @ManyToOne
    @JoinColumn(name = "games_id")
    private Game game;
}
