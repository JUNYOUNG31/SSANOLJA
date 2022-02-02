package com.ssafy.backend.domain.spyfall.entity;


import com.ssafy.backend.domain.game.entity.Game;
import com.ssafy.backend.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spyfalls")
@Getter
@Setter
@ToString
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
