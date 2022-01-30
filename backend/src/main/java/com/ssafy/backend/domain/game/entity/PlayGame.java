package com.ssafy.backend.domain.game.entity;

import com.ssafy.backend.domain.room.entity.Room;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "play_games")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlayGame {

    @Id
    @Column(name = "play_games_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playGameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rooms_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_indexes_id", nullable = false)
    private GameIndex gameIndex;
}
