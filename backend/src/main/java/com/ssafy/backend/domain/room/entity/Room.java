package com.ssafy.backend.domain.room.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @Column(name="rooms_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    @Column(name = "room_code", nullable = false)
    private String roomCode;
}
