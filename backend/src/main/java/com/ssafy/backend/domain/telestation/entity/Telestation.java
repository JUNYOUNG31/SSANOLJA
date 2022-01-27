package com.ssafy.backend.domain.telestation.entity;

import com.ssafy.backend.domain.room.entity.RoomDetail;
import com.ssafy.backend.domain.user.entity.User;
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
public class Telestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer telestationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomDetail roomDetail;

    @Column(length = 45)
    private String startKeyword;

    private Integer order;

    @Column(length = 1000)
    private String playData;

}
