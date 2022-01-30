package com.ssafy.backend.domain.game.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "game_indexes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameIndex {

    @Id
    @Column(name = "game_indexes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameIndexId;

    @Column(name = "game_name")
    private String gameName;

}
