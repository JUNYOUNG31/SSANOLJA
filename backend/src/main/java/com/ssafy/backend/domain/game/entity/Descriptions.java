package com.ssafy.backend.domain.game.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "descriptions")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Descriptions {

    @Id
    @Column(name = "descriptions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer descriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_list_id")
    private GameIndex gameIndex;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name="order")
    private int order;

}
