package com.ssanolja.backend.db.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "spyfall_places")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SpyfallPlace {

    @Id
    @Column(name = "spyfall_places_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallPlaceId;


    @Column(name="place_name")
    private String placeName;
}
