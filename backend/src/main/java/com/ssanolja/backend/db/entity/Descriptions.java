package com.ssanolja.backend.db.entity;

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

    @Column(name = "game_num")
    private Integer gameNum;

    @Column(name = "description")
    private String description;

    @Column(name = "order")
    private int order;

}
