package com.ssafy.backend.domain.spyfall.entity;


import com.ssafy.backend.domain.game.entity.PlayGame;
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
    @JoinColumn(name = "play_games_id")
    private PlayGame playGame;


    @ManyToOne
    @JoinColumn(name = "spyfall_jobs_id")
    private SpyfallJob spyfallJob;

    @Column(name = "user_nickname")
    private String userNickname;
}
