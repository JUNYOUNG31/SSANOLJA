package com.ssafy.backend.domain.game.entity;

import com.ssafy.backend.domain.room.entity.Room;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    @Id
    @Column(name = "games_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playGameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rooms_id", nullable = false)
    private Room room;

}
