package com.ssafy.backend.domain.spyfall.entity;

import com.ssafy.backend.domain.room.entity.Room;
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
public class Spyfall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spyfallId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomDetail roomDetail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spyfall_job_id")
    private SpyfallJob spyfallJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spyfall_place_id")
    private SpyfallPlace spyfallPlace;

    @Column(length = 45)
    private String qna;

    @Column(nullable = false)
    private Integer vote;
}
