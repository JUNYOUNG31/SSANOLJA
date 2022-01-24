package com.ssafy.backend.domain.game.entity;

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
public class GameDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameDescriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game gameId;

    @Column(length = 1000)
    private String descriptionImg;

    @Column(nullable = false)
    private Integer gameOrder;
}
